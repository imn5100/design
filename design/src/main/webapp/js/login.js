$(document).ready(function() {
	$("#myPage").hide();
	if ($.cookie("user")) {
		$("#login").attr("href", "javascript:logout()");
		$("#login").text("注销-LOGOUT");
		$("#myPage").show();
	}
});
function logout() {
	if (confirm("注销后将无法保存个人数据，是否要注销？")) {
		window.location = "logout"
	} else {
	}
}

function dealData(name, data) {
	return '"' + name + '":' + '"' + $.trim(valueReplace(data)) + '"';
}

function addCommonParameter(param) {
	var mydata = '{"timeStamp":"' + new Date().getTime() + '"';

	if (param != null && param != "") {
		mydata += "," + param;
	}

	mydata += '}';

	return mydata;
}
function toStr(params) {
	var str = ""
	for (var i = 0; i < arguments.length; i++) {
		if (i != arguments.length - 1)
			str = str + arguments[i] + ",";
		else
			str = str + arguments[i];
	}
	return str;
}

function valueReplace(v) {
	v = String(v).replace(new RegExp('(["\"])', 'g'), "\\\"");
	v = v.replace("\r", "\\r").replace("\n","\\n").replace("\r\n", "\\r\\n");
	return v;
}
function GetRequest() {
	   var url = location.search; //获取url中"?"符后的字串
	   var theRequest = new Object();
	   if (url.indexOf("?") != -1) {
	      var str = url.substr(1);
	      strs = str.split("&");
	      for(var i = 0; i < strs.length; i ++) {
	         theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
	      }
	   }
	   return theRequest;
}