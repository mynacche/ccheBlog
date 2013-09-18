function callServer(url, method, data, sync, statechange) {
	xmlHttp = getXmlHttp();
	xmlHttp.onreadystatechange = statechange;
	xmlHttp.open(method, url, sync);
	xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded"); 
	xmlHttp.send(data);
}

function ajax(param) {


	xmlHttp = getXmlHttp();
	xmlHttp.onreadystatechange = function(){
		
		if(xmlHttp.readyState == 4){
			param.success();
		}
			
	};
	xmlHttp.open(param.method, param.url, param.sync);
	param.beforesend(); 
	xmlHttp.send(param.data);
}

function $(id){
	
	return document.getElementById(id);
}

function getXmlHttp() {
	var xmlHttp = null;
	try {
		xmlHttp = new XMLHttpRequest();
	} catch (e) {
		try {
			xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	}
	return xmlHttp;
}
function parseParams(str){
	
	return str.replace("&","&amp;");
}