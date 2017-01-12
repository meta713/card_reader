var serialPort = require("serialport")
serial_data = [];
RCS620S_DEFAULT_TIMEOUT = 1000;
RCS620S_MAX_RW_RESPONSE_LEN = 265;

var sp = new serialPort.SerialPort('/dev/tty.usbmodem1421', {
	baudrate: 115200,
	parser:serialPort.parsers.readline("\n")
});


function writeSerial(data){
	for (var i in data){
		sp.write(data[i]);
		console.log(data[i]);
	}
	console.log('\n');
}


function flushSerial(){
	sp.flush();
	serial_data = [];
}


function cancel(){
	console.log('cancel');
	setTimeout(function(){
		writeSerial([0x00,0x00,0xff,0x00,0xff,0x00]);
        }, 1);
	flushSerial();
}


function calcDCS(data){
	sum = 0;
	for (var i in data){
		sum += data[i];
	}
	var result = new Uint8Array([- (sum & 0xff)]);

	console.log('calcDCS=' + result[0]);
	return result[0];
}

function readSerial(data, len){
	var nread = 0; 
	var d = new Date();
	var t0 = d.getTime();

	while(nread < len){
		if(checkTimeout(t0)){
			return false;
		}

		if(serial_data.length > 0){
			data.push = serial_data.pop();
			nread++;
		}
	}

	return true;
}


function checkTimeout(t0){
	var d = new Date();
	var t = d.getTime();
	if ((t - t0) >= RCS620S_DEFAULT_TIMEOUT){
		return true;
	}
	return false;
}

function rwCommand(command, response){
	var response_len = 0;
	var bitcalc = new Uint8Array(1);
	console.log('rwCommand');

	flushSerial();

	var dcs = calcDCS(command);
  
	var buf = [0x00];
	buf.push(0x00);
	buf.push(0xff);

	if(command.length <= 255){
		buf.push(command.length);
		bitcalc[0] = -buf[3];
		buf.push(bitcalc[0]);
		writeSerial(buf);
	}else{
		buf.push(0xff);
		buf.push(0xff);
		buf.push((command.length >> 8) & 0xff);
		buf.push((commandLen >> 0) & 0xff);
		bitcalc[0] = - (buf[5] + buf[6]);
		buf.push(bitcalc[0]);
		writeSerial(buf);
	}

	writeSerial(command);

	buf = [];
	buf.push(dcs);
	buf.push(0x00);

	writeSerial(buf);
  
	//receive an ACK
	buf = [];
	var ret = readSerial(buf, 6);
	//var ff = [0x00, 0x00, 0xff, 0x00, 0xff, 0x00];
	if(!ret || buf.toSring() ==  [0x00, 0x00, 0xff, 0x00, 0xff, 0x00].toString()){
		console.log('receive an ACK cancel');
		cancel();
		return 0;
	}

	//receive a response
	buf = [];
	ret = readSerial(buf, 5);
	if(!ret){
		cancel();
		return 0;
	}else if(buf.toSring() ==  [0x00, 0x00, 0xff].toString()){
		return 0;
	}

	if((buf[3] == '0xff') && (buf[4] == '0xff')){
		buf.length = 5;
		ret = readSerial(buf, 3);
		if(!ret || (((buf[5] + buf[6] + buf[7]) && 0xff) != 0)){
			return 0;
		}
	}else{
		if(((buf[3] + buf[4]) & 0xff) != 0){
			return 0;
		}
	}

	if(response_len > RCS620S_MAX_RW_RESPONSE_LEN){
		return 0;
	}

	ret = readSerial(response, response_len);
	if(!ret){
		cancel();
		return 0;
	}

	dcs = calcDCS(response, response_len);

	ret = readSerial(response, 2);
	if(!ret || (buf[0] != dcs) || (buf[1] != 0x00)){
		cancel();
		return 0;
	}

	return 1;
}



// the open event will always be emitted
sp.on('open', function(){
	// open logic
	//3秒間スリープ

	//var time_id = setTimeout(function (){
	//	sp.write('a');
	//	//setInterval(function (){ sp.write('a'); }, 100);
	//}, 6000);
	
	var response = [];
	setTimeout(function(){
		rwCommand([0xd4, 0x32, 0x05, 0x00, 0x00, 0x00], response)
		}, 6000);

	console.log('response received: ' + response);

});


sp.on('data', function(data){
	serial_data.push(data);
	console.log('data received: ' + serial_data.length);
});

