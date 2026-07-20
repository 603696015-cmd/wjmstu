function searchUserInit(comp){
     width=800;
	 height=450;
  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
	 var rv =  window.showModalDialog("mess_sendUserlist.action?sub_department=1&x="+Math.random(),null,sFeature);
	 if(null==rv){
	 	alert('您没有选择用户！');
	 }else{
	 	if(rv[0]<=0)  	alert('您没有选择用户！');
	 	for(var i = 0 ;i <rv.length ; i++){
	 		addUserinfo(rv[i]);
	 	}
	 }
}
function addUserinfo(id){
	var _d = document.createElement("<span>");
	_d.id = "_d_u"+id;
	_d.style.width="110px";
	_d.style.height="14px";
	_d.style.background="#dddfff";
	_d.style.float="left";
	_d.style.border="solid buttonface 1px";
	$.post("mess_getUserInfo.action", {
		"elUser.id":id, 
		"input_name":"elUsers.id", 
		"x":Math.random
		}, 
		function (data) {
			$("#"+_d.id).html(data);
		});
	document.getElementById("d_userlist").appendChild(_d);
}
/*
function deleteUserinfo(id){
	if(offid!=0)
	$.post("offline_deleteuser.action", {
		"elUser.id":id, 
		"offline.id":offid, 
		"x":Math.random
		}, 
		function (data) {
			$("#message").html("删除成功");
			document.getElementById('d_userlist').removeChild(document.getElementById("_d_u"+id));
		});
}*/
function checkHasUser(id){
	var childs = document.getElementById("d_userlist").getElemengtsByTagName("span");
	for(var i = 0 ;i <childs.length;i++){
		if(childs[i].id == "_d_u"+id)
		return true;
	}
	return false;

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
