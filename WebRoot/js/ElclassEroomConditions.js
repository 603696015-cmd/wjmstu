//=============ajaxÃ¥Â°ÂÃ¨Â£ÂÃÂ========================================
//var request = false;
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
	var request = false;
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
	this.component = component;
	//createRequest();
	request.open("POST", url, true);
	request.onreadystatechange = function(){
	if (request.readyState == 4) {
		document.getElementById(component).innerHTML = "";
		document.getElementById(component).innerHTML = request.responseText;
	}
	};
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;");
	request.send(param);
}
function action_cl() {
	if (request.readyState == 4) {
		document.getElementById(component).innerHTML = "";
		document.getElementById(component).innerHTML = request.responseText;
	}
} 
//=============发送消息 -- 按培训班========================================
/*
function searchElclassUser(){ 
     width=650;
	 height=500;
  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
	 var rv = window.showModalDialog("mess_sendElclassUserList.action?x="+Math.random(),null,sFeature);  
	 if(null==rv){
	 		alert('您没有选择培训班');
	 }else{ 
	 	if(rv[0]<=0)  		alert('您没有选择培训班');
	 	for(var i = 0 ;i <rv.length ; i++){ 
	 		addElclassUserinfo(rv[i]);
	 	}
	 }
} 
function addElclassUserinfo(id){  
	var _d = document.createElement("<span>");
	_d.id = "_pxb_u"+id;
	_d.style.width="170px";
	_d.style.height="14px";
	_d.style.background="#dddfff";
	_d.style.float="left";
	_d.style.border="solid buttonface 1px";
	 
	if(!document.getElementById("_pxb_u"+id)){
		action("mess_getElclassUserInfo.action", "elClass.id="+id+"&input_name=elClasss.id",_d.id); 
		if(document.getElementById("PXB").innerHTML != null){ 
			document.getElementById("PXB").style.display='block'; 
		}   
		document.getElementById("PXB").appendChild(_d);	 
		if(document.getElementById("PXB_")){  //编辑用 
			document.getElementById("PXB_").style.display='none';//清空Div 
		} 
	}
}
*/
function searchElclassUser(){ 
     width=650;
	 height=500;
  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
	 var rv = window.showModalDialog("mess_sendElclassUserList.action?x="+Math.random(),null,sFeature);  
	 if(null==rv){
	 		alert('您没有选择培训班');
	 }else{ 
	 	if(rv[0]<=0)  		alert('您没有选择培训班');
	 	for(var i = 0 ;i <rv.length ; i++){
	 		if($("#_pxb_u"+rv[i]).length<=0)
	 		addElclassUserinfo(rv[i]);
	 	}
	 }
} 
function addElclassUserinfo(id){  
	var _d = $("<span>");
	$(_d).attr("id","_pxb_u"+id) ;
	$(_d).css("width","180px");
	$(_d).css("height","14px");
	$(_d).css("background","#dddfff");
	$(_d).css("float","left");
	$(_d).css("border","solid buttonface 1px");
	$.ajax(
	{	async:false,
		type:"post",
	    url:"mess_getElclassUserInfo.action",
	    data:{"elClass.id":id,"input_name": "elClasss.id","x":Math.random()},
		success:function(data){
			var jsondata = eval("("+data+")");
			$(_d).html('<input type="hidden" name="'+jsondata.input_name+'" value="'+jsondata.id+'">'+
				'<label style="width:150px;float:left;">'+
				'名称：'+jsondata.title+'</label><a style="cursor: hand; float: right; width: 14px; height: 14px;" href="" onclick="javascript:deleteElclassUserinfo(this,'+jsondata.id+');return false;">X</a>');
			//$(_d).html(data);
	 }});
	$("#PXB").append(_d);
	if($("#PXB").html()!=""){
		$("#PXB").css("display","block");
	}
}
function deleteElclassUserinfo(obj,id){
	document.getElementById('PXB').removeChild(document.getElementById("_pxb_u"+id));
	if(!document.getElementById("PXB").innerHTML){
		document.getElementById("PXB").style.display='none'; 
		if(document.getElementById("PXB_")){  //编辑用
			document.getElementById("PXB_").style.display='block';//清空Div 
		}
	} 
}
//=============发送消息 -- 按考场========================================
/*
function searchExamRoomUser(){
     width=650;
	 height=500;
  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
	 var rv =  window.showModalDialog("mess_sendExamRoomUserList.action?x="+Math.random(),null,sFeature);   
	 if(null==rv){
	 		alert('您没有选择考场');
	 }else{
	 	if(rv[0]<=0)  		alert('您没有选择考场'); 
	 	for(var i = 0 ;i <rv.length ; i++){
	 		addExamRoomUserinfo(rv[i]);
	 	}
	 }
} 
function addExamRoomUserinfo(id){
	var _d = document.createElement("<span>");
	_d.id = "_kc_u"+id;
	_d.style.width="170px";
	_d.style.height="14px";
	_d.style.background="#dddfff";
	_d.style.float="left";
	_d.style.border="solid buttonface 1px";  
	 
	if(!document.getElementById("_kc_u"+id)){   
		action("mess_getExamRoomUserInfo.action", "examRoom.id="+id+"&input_name=examRooms.id",_d.id);
		if(document.getElementById("KC").innerHTML != null){
			document.getElementById("KC").style.display='block';
		} 
		document.getElementById("KC").appendChild(_d);	 
		if(document.getElementById("KC_")){  //编辑用
			document.getElementById("KC_").style.display='none';//清空Div 
		}
	}
}
*/
function searchExamRoomUser(){
     width=650;
	 height=500;
  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
	 var rv =  window.showModalDialog("mess_sendExamRoomUserList.action?x="+Math.random(),null,sFeature);   
	 if(null==rv){
	 		alert('您没有选择考场');
	 }else{
	 	if(rv[0]<=0)  		alert('您没有选择考场'); 
	 	for(var i = 0 ;i <rv.length ; i++){
	 		if($("#_kc_u"+rv[i]).length <=0)
	 		addExamRoomUserinfo(rv[i]);
	 	}
	 }
} 
function addExamRoomUserinfo(id){
	var _d = $("<span>");
	$(_d).attr("id","_kc_u"+id) ;
	$(_d).css("width","180px");
	$(_d).css("height","14px");
	$(_d).css("background","#dddfff");
	$(_d).css("float","left");
	$(_d).css("border","solid buttonface 1px");
	$.ajax(
	{	async:false,  //   
		type:"post",   
	    url:"mess_getExamRoomUserInfo.action",   
	    data:{"examRoom.id":id,"input_name": "examRooms.id","x":Math.random()},   
		success:function(data){
			var jsondata = eval("("+data+")");
			$(_d).html('<input type="hidden" name="'+jsondata.input_name+'" value="'+jsondata.id+'">'+
				'<label style="width:150px;float:left;">'+
				'名称：'+jsondata.title+'</label><a style="cursor: hand; float: right; width: 14px; height: 14px;" href="" onclick="javascript:deleteExamRoomUserinfo(this,'+jsondata.id+');return false;">X</a>');
	 }});
	$("#KC").append(_d);
	if($("#KC").html()!=""){
		$("#KC").css("display","block");
	}
}
function deleteExamRoomUserinfo(obj,id){ 
	document.getElementById('KC').removeChild(document.getElementById("_kc_u"+id));
	if(!document.getElementById("KC").innerHTML){
		document.getElementById("KC").style.display='none'; 
		if(document.getElementById("KC_")){ //编辑用 
			document.getElementById("KC_").style.display='block';//还原Div 
		}  
	}  
}
//=============发送消息 --按部门========================================
function searchDEPUser(){
     width=650;
	 height=500;
  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
	 var rv =  window.showModalDialog("mess_sendDEPUserList.action?x="+Math.random(),null,sFeature);   
	 if(null==rv){
	 		alert('您没有选择部门');
	 }else{
	 	if(rv[0]<=0)  		alert('您没有选择部门'); 
	 	for(var i = 0 ;i <rv.length ; i++){
	 		addDEPUserinfo(rv[i]);
	 	}
	 }
} 
function addDEPUserinfo(id){
	var _d = document.createElement("<span>");
	_d.id = "_bm_u"+id;
	_d.style.width="250px";
	_d.style.height="14px";
	_d.style.background="#dddfff";
	_d.style.float="left";
	_d.style.border="solid buttonface 1px";  
	
	if(!document.getElementById("_bm_u"+id)){ 
		action("mess_getDEPUserInfo.action", "department.id="+id+"&input_name=departments.id",_d.id);  
		document.getElementById("BM").appendChild(_d);	 
	}
}  
function deleteDEPUserinfo(obj,id){
	document.getElementById('BM').removeChild(document.getElementById("_bm_u"+id));
}