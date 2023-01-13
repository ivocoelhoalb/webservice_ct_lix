var X = XLSX;
var markers = [];
var myMarkers = [];

var XW = {
	/* worker message */
	msg : 'xlsx',
	/* worker scripts */
	rABS : 'js/xlsx_xlsxworker2.js',
	norABS : 'xlsxworker1.js',
	noxfer : 'xlsxworker.js'
};

var rABS = typeof FileReader !== "undefined"
		&& typeof FileReader.prototype !== "undefined"
		&& typeof FileReader.prototype.readAsBinaryString !== "undefined";

function handleFile(e) {

	console.log("handleFile");

	var rABS = true;
	var use_worker = true;
	var files = e.target.files;
	var f = files[0];
	{
		var reader = new FileReader();
		var name = f.name;
		reader.onload = function(e) {
			if (typeof console !== 'undefined')
				console.log("onload", new Date(), rABS, use_worker);
			var data = e.target.result;
			if (use_worker) {
				xw(data, process_wb);
			} else {
				var wb;
				if (rABS) {
					wb = X.read(data, {
						type : 'binary'
					});
				} else {
					var arr = fixdata(data);
					wb = X.read(btoa(arr), {
						type : 'base64'
					});
				}
				process_wb(wb);
			}
		};
		if (rABS)
			reader.readAsBinaryString(f);
		else
			reader.readAsArrayBuffer(f);
	}

}

function ab2str(data) {
	var o = "", l = 0, w = 10240;
	for (; l < data.byteLength / w; ++l)
		o += String.fromCharCode.apply(null, new Uint16Array(data.slice(l * w,
				l * w + w)));
	o += String.fromCharCode.apply(null, new Uint16Array(data.slice(l * w)));
	return o;
}

function xw(data, cb) {
	transferable = true;
	if (transferable)
		xw_xfer(data, cb);
	else
		xw_noxfer(data, cb);
}

function xw_xfer(data, cb) {
	var worker = new Worker(rABS ? XW.rABS : XW.norABS);
	worker.onmessage = function(e) {
		switch (e.data.t) {
		case 'ready':
			break;
		case 'e':
			console.error(e.data.d);
			break;
		default:
			xx = ab2str(e.data).replace(/\n/g, "\\n").replace(/\r/g, "\\r");
			console.log("done");
			cb(JSON.parse(xx));
			break;
		}
	};
	if (rABS) {
		var val = s2ab(data);
		worker.postMessage(val[1], [ val[1] ]);
	} else {
		worker.postMessage(data, [ data ]);
	}
}

function s2ab(s) {
	var b = new ArrayBuffer(s.length * 2), v = new Uint16Array(b);
	for (var i = 0; i != s.length; ++i)
		v[i] = s.charCodeAt(i);
	return [ v, b ];
}

function to_json(workbook) {
	var result = {};
	workbook.SheetNames
			.forEach(function(sheetName) {
				var roa = X.utils
						.sheet_to_row_object_array(workbook.Sheets[sheetName]);
				if (roa.length > 0) {
					result[sheetName] = roa;
				}
			});
	return result;
}

function process_wb(wb) {
	try {

		var imported = to_json(wb).Plan1;

		for (var i = 0; i < imported.length; i++) {

			var name = imported[i].name;

			var d1 = imported[i].D1.replace('S', '');
			var m1 = imported[i].M1;
			var s1 = imported[i].S1;

			var d2 = imported[i].D2.replace('W', '');
			var m2 = imported[i].M2;
			var s2 = imported[i].S2;

			var lat = ConvertDMSToDD(d1, m1, s1, 'S');
			var lng = ConvertDMSToDD(d2, m2, s2, 'W');

			myMarkers.push({
				latitude : lat,
				longitude : lng,
				nome : name
			});
		}

		drop();

	} catch (err) {

		alert("Não foi possível importar os alertas.")
	}
}

function ConvertDMSToDD(degrees, minutes, seconds, direction) {
	var dd = Number(degrees) + Number(minutes)
	/60 + Number(seconds)/(60 * 60);

	if (direction == "S" || direction == "W") {
		dd = dd * -1;
	} // Don't do anything for N or E
	return dd;
}

function drop() {
	clearMarkers();
	for (var i = 0; i < myMarkers.length; i++) {
		addMarkerWithTimeout(myMarkers[i], i * 50);
	}
}

function addMarkerWithTimeout(myMarker, timeout) {
	window.setTimeout(function() {
		var infowindow = new google.maps.InfoWindow({
			content : myMarker.nome
		});

		
		markers.push(new google.maps.Marker({
											  position : new google.maps.LatLng(myMarker.latitude,
											  myMarker.longitude),
											  map : map,
											  lable : myMarker.nome,
											  animation : google.maps.Animation.DROP
											}).addListener('click', function() {infowindow.open(map, this);}));
												
		
	}, timeout);
}

function clearMarkers() {
	for (var i = 0; i < markers.length; i++) {
		markers[i].setMap(null);
	}
	markers = [];
}
























