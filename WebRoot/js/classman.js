//=============ajax封装========================================
var request = false;
var component = null;
function createRequest() {
	if (window.ActiveXObject) {
		request = new ActiveXObject("Microsoft.XMLHTTP");
	} else {
		if (window.XMLHttpRequest) {
			request = new XMLHttpRequest();
		}
	}
	if (!request) {
		alert("Error initializing XMLHttpRequest!");
	}
}
function action(url, param, component) {
	this.component = component;
	createRequest();
	request.open("POST", url, true);
	request.onreadystatechange = action_cl;
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;");
	request.send(param);
}
function action_cl() {
	if (request.readyState == 4) {
		document.getElementById(component).innerHTML = "";
		document.getElementById(component).innerHTML = request.responseText;
	}
}
//=============ajax封装结束========================================
//学分设置
var credit = null;
var courseid =null;
var id = null;
function credit_alter( comp) {
	param = "elclass.id=" + document.getElementById("classid").value + "&course.id=" + courseid + "&course.credit=" + document.getElementById(id).value;
	action("elclass_course_credit_alter.action",param, comp);
	
	var obj = document.getElementById(id);
	var credit_button = document.getElementById("credit_alter_success");
	credit_button.style.display = "block";
	var left = (obj.offsetLeft + obj.clientWidth);
	var top = (obj.offsetTop);
	while (obj = obj.offsetParent) {
		left += obj.offsetLeft;
		top += obj.offsetTop;
	}
	credit_button.style.left =left;
	credit_button.style.top =top;
}
function showAlterButton(id1,cid ) {
	var obj = document.getElementById(id1);
	var credit_button = document.getElementById("credit_button");
	credit_button.style.display = "block";
	var left = (obj.offsetLeft + obj.clientWidth);
	var top = (obj.offsetTop);
	while (obj = obj.offsetParent) {
		left += obj.offsetLeft;
		top += obj.offsetTop;
	}
	credit_button.style.left =left;
	credit_button.style.top =top;
	id = id1;
	courseid= cid; 
	document.getElementById("credit_alter_success").style.display = "none";
}
function unShowAlterButton(obj) {
	document.getElementById("credit_button").style.display = "none";
}

