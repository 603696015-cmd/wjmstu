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

function searchUserInit(comp){
	document.getElementById("messUserF").style.display="block";
	action("mess_sendUserlistInit.action", null, comp);
}
function searchUser(comp){
	var param = "elUser.email="+document.getElementById("email").value
	+"&elUser.realname="+document.getElementById("name").value
	+"&elUser.username="+document.getElementById("username").value
	+"&department.id="+document.getElementById("departmentid").value
	+"&company.id="+document.getElementById("companyid").value
	+"&pN="+document.getElementById("pageNow").value
	+"&pS="+document.getElementById("pageSize").value;
	if(document.getElementById("sub_department").checked== true ) param+= "&sub_department="+document.getElementById("sub_department").value;
	action("mess_sendUserlist.action", param, comp);
}
function page(i){
 		searchUser1('messUser',i);
 		}
function searchUser1(comp,i){
	var param = "elUser.email="+document.getElementById("email").value
	+"&elUser.realname="+document.getElementById("name").value
	+"&elUser.username="+document.getElementById("username").value
	+"&sub_department="+document.getElementById("sub_department").value
	+"&department.id="+document.getElementById("departmentid").value
	+"&company.id="+document.getElementById("companyid").value
	+"&pN="+i
	+"&pS="+document.getElementById("pageSize").value;
	action("mess_sendUserlist.action", param, comp);
}
function setRec(obj){
	
	document.getElementById("recivers").value+= obj.value+";";
}
///http://localhost:8088/elearning/mess_sendUserlist.action?elUser.email=&elUser.realname=&elUser.username=&department.id=0&company.id=1&pN=0&pS=10&sub_department=1