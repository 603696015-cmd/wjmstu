var t_DiglogX, t_DiglogY, t_DiglogW, t_DiglogH;
function StrCode(str) {
	if (encodeURIComponent) {
		return encodeURIComponent(str);
	}
	if (escape) {
		return escape(str);
	}
}
function Browser() {
	var ua, s, i;
	this.isIE = false;
	this.isNS = false;
	this.isOP = false;
	this.isSF = false;
	ua = navigator.userAgent.toLowerCase();
	s = "opera";
	if ((i = ua.indexOf(s)) >= 0) {
		this.isOP = true;
		return;
	}
	s = "msie";
	if ((i = ua.indexOf(s)) >= 0) {
		this.isIE = true;
		return;
	}
	s = "netscape6/";
	if ((i = ua.indexOf(s)) >= 0) {
		this.isNS = true;
		return;
	}
	s = "gecko";
	if ((i = ua.indexOf(s)) >= 0) {
		this.isNS = true;
		return;
	}
	s = "safari";
	if ((i = ua.indexOf(s)) >= 0) {
		this.isSF = true;
		return;
	}
}
function DialogShow(showdata, ow, oh, w, h) {
	var objDialog = document.getElementById("DialogMove");
	if (!objDialog) {
		objDialog = document.createElement("div");
	}
	t_DiglogW = ow;
	t_DiglogH = oh;
	DialogLoc();
	objDialog.id = "DialogMove";
	var oS = objDialog.style;
	oS.display = "block";
	oS.top = t_DiglogY + "px";
	oS.left = t_DiglogX + "px";
	oS.margin = "0px";
	oS.padding = "0px";
	oS.width = w + "px";
	oS.height = h + "px";
	oS.position = "absolute";
	oS.zIndex = "5";
	oS.background = "#FFF";
	oS.border = "solid buttonface 1px";
	objDialog.innerHTML = showdata;
	document.body.appendChild(objDialog);
}
function DialogHide() {
	ScreenClean();
	var objDialog = document.getElementById("DialogMove");
	if (objDialog) {
		objDialog.style.display = "none";
	}
}
function DialogLoc() {
	var dde = document.documentElement;
	if (window.innerWidth) {
		var ww = window.innerWidth;
		var wh = window.innerHeight;
		var bgX = window.pageXOffset;
		var bgY = window.pageYOffset;
	} else {
		var ww = dde.offsetWidth;
		var wh = dde.offsetHeight;
		var bgX = dde.scrollLeft;
		var bgY = dde.scrollTop;
	}
	t_DiglogX = (bgX + ((ww - t_DiglogW) / 2));
	t_DiglogY = (bgY + ((wh - t_DiglogH) / 2));
}
function ScreenConvert() {
	var browser = new Browser();
	var objScreen = document.getElementById("ScreenOver");
	if (!objScreen) {
		var objScreen = document.createElement("div");
	}
	var oS = objScreen.style;
	objScreen.id = "ScreenOver";
	oS.display = "block";
	oS.top = oS.left = oS.margin = oS.padding = "0px";
	if (document.body.clientHeight) {
		var wh = document.body.clientHeight + "px";
	} else {
		if (window.innerHeight) {
			var wh = window.innerHeight + "px";
		} else {
			var wh = "100%";
		}
	}
	oS.width = "100%";
	oS.height = wh;
	oS.position = "absolute";
	oS.zIndex = "3";
	if ((!browser.isSF) && (!browser.isOP)) {
		oS.background = "#cccccc";
	} else {
		oS.background = "#cccccc";
	}
	oS.filter = "alpha(opacity=50)";
	oS.opacity = 40 / 100;
	oS.MozOpacity = 40 / 100;
	document.body.appendChild(objScreen);
	var allselect = document.getElementsByTagName("select");
	for (var i = 0; i < allselect.length; i++) {
		allselect[i].style.visibility = "hidden";
	}
}
function ScreenClean() {
	var objScreen = document.getElementById("ScreenOver");
	if (objScreen) {
		objScreen.style.display = "none";
	}
	var allselect = document.getElementsByTagName("select");
	for (var i = 0; i < allselect.length; i++) {
		allselect[i].style.visibility = "visible";
	}
}
function dialogShow(title,content){
	ScreenConvert();
	var ShowDiv = "<div style=\"text-align:center;\"><div style='width:100%;text-align:right;background:#51b2f6'><b style='float:left;'>"+title+"</b><a href='javascript:DialogHide();'>关闭</a></div><div style='width:100%;height:100;text-align:left;clear:both;'>" 
	+ content + "</div></div>";
	DialogShow(ShowDiv, 450, 320, 400, 300);
}
/*function Demo(string) {
	ScreenConvert();
	var ShowDiv = "<div style=\"padding:10px;\">" + string + " <br /><br /><input type=\"button\" onclick=\"DialogHide();\" value=\" \u5173\u95ed \"></div>";
	DialogShow(ShowDiv, 250, 120, 300, 100);
}*/

