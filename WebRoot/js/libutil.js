function searchUserInit(_id,input_name,comp){
     width=800;
	 height=450;
  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
	 var rv =  window.showModalDialog("mess_sendUserlist.action?sub_department=1&x="+Math.random(),null,sFeature);
	 if(null==rv){
	 	alert('您没有选择用户！');
	 }else{
	 	if(rv[0]<=0)  	alert('您没有选择用户！');
	 	for(var i = 0 ;i <rv.length ; i++){
	 	if(!checkHasUser(_id,rv[i]))
	 		addUserinfo(_id,rv[i],input_name);
	 		//var nouser="nouser"+_id;
	 		//document.getElementById(nouser).value=nouser;
	 	}
	 }
}
function addUserinfo(_id,id,inputname){
	var _d = document.createElement("<span>");
	_d.id = "_d_u"+_id+id;
	_d.style.width="110px";
	_d.style.height="14px";
	_d.style.background="#dddfff";
	_d.style.float="left";
	_d.style.border="solid buttonface 1px";
	$.post("mess_getUserInfo.action", {
		"elUser.id":id,
		"input_name":inputname, 
		"x":Math.random
		}, 
		function (data) {
			$("#"+_d.id).html(data);
		}); 
	document.getElementById(_id).appendChild(_d);
	
}
function checkHasUser(_id,id){
	if( document.getElementById("_d_u"+_id+id)) return true;
	/*var childs = document.getElementById(_id).getElemengtsByTagName("span");
	for(var i = 0 ;i <childs.length;i++){
		if(childs[i].id == "_d_u"+_id+id)
		return true;
	}*/
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

function deleteUserinfo(obj,id){
	obj.parentNode.parentNode.removeChild(obj.parentNode);
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
