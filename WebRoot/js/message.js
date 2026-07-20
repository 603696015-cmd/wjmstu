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
//=============ajax========================================

function searchUserInit(comp){
     width=800;
	 height=450;
  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
	 var rv =  window.showModalDialog("mess_sendUserlist.action?sub_department=1&x="+Math.random(),null,sFeature);
	 if(null==rv){
	 	alert('您没有选择用户');
	 }else{
	 	if(rv[0]<=0)  	alert('您没有选择用户');
	 	for(var i = 0 ;i <rv.length ; i++){
	 		addUserinfo(rv[i]);
	 	}
	 }
}

var isOk=0;
function searchUserInit2(comp){
     width=800;
	 height=450;
  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
	 var rv =  window.showModalDialog("mess_sendUserlist.action?sub_department=1&x="+Math.random(),null,sFeature);
	 if(null==rv){
	 	alert('您没有选择用户');
	 	isOk=0;
	 	return false;
	 }else{
	 	if(rv[0]<=0){
	 		alert('您没有选择用户');
	 		isOk=0;
	 		return false;
	 	}
	 	for(var i = 0 ;i <rv.length ; i++){
	 		addUserinfo(rv[i]);
	 	}
	 	isOk=1;
	 	return true;
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
	
	action("mess_getUserInfo.action", "elUser.id="+id+"&input_name=elUsers.id",_d.id);
	document.getElementById("d_userlist").appendChild(_d);
	
}
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

function deleteUserinfo(obj,id){
	document.getElementById('d_userlist').removeChild(document.getElementById("_d_u"+id)); 
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

function doSubmit_1(op,newsId){
				if(op==1){
					//é¢è§
					alert("nihao");
					nmList.action="/gdgat/newsIndexView.action?news.id="+newsId;
				}
				else if(op==2){
					//ç¼è¾
					document.getElementById("newsId").value=newsId;
					nmList.action="news_alterInit.action";
				}
				nmList.submit();
			}
			
function doSubmit_2(op,newsId,pN,pS,newsOp){
	if(op==1){
		//
		document.getElementById("newsId").value=newsId;
		document.getElementById("newsOp").value=newsOp;
		nmList.action="upNewsStatus.action?pN="+pN+"&pS="+pS;
	}
	nmList.submit();
}
function doSubmit_3(topicId,pN,pS,topicOp){
	if(window.confirm(topicOp==2?"确定通过此留言？":"确定删除此留言？")){
		document.getElementById("topicId").value=topicId;
		document.getElementById("topicOp").value=topicOp;
		topicForm.action="upTopicValid.action?pN="+pN+"&pS="+pS;
		topicForm.submit();
	}else
		return false;
}
function doSubmit_4(forumId,topicId,pN,pS,topicOp,createrid,title){
	if(window.confirm(topicOp==2?"确定通过此留言？":"确定删除此留言？")){
		document.getElementById("topicId").value=topicId;
		document.getElementById("topicOp").value=topicOp;
		document.getElementById("forumId").value=forumId;
		document.getElementById("createrid").value=createrid;
		document.getElementById("forumTitle").value=title;
		topicForm.action="upTopicValid.action?pN="+pN+"&pS="+pS;
		topicForm.submit();
	}else
		return false;
}
function doSubmit(){
	if(window.confirm("确认修改热度？")){
		var checkObj = document.getElementsByName("newId");
	    var newIdArray = "";
	    for (i = 0; i < checkObj.length; i++) {
			if (checkObj[i].checked) {
			    if(newIdArray!="")newIdArray+=",";
				newIdArray += checkObj[i].value;
			}
		 }
		if(newIdArray==""){
		  alert("请至少选中1条新闻！");
		  return ;
	    }
	    var newIds = document.getElementById("newIds");
	    newIds.value=newIdArray;
	    //alert(newIdArray);
	    //myForm.action="course_newassigntoUsers.action?status=0";
		myForm.submit();
	}
}
function doDelete(){
	if(window.confirm("确认申请删除？")){
		var checkObj = document.getElementsByName("newId");
	    var newIdArray = "";
	    for (i = 0; i < checkObj.length; i++) {
			if (checkObj[i].checked) {
			    if(newIdArray!="")newIdArray+=",";
				newIdArray += checkObj[i].value;
			}
		 }
		if(newIdArray==""){
		  alert("请至少选中1条新闻！");
		  return ;
	    }
	    var newIds = document.getElementById("newIds");
	    newIds.value=newIdArray;
	    //alert(newIdArray);
	    myForm.action="upNewsStatus.action?newsOp=9";
		myForm.submit();
	}
}
function doDel_(){
	if(window.confirm("确认删除？")){
		var checkObj = document.getElementsByName("newId");
	    var newIdArray = "";
	    for (i = 0; i < checkObj.length; i++) {
			if (checkObj[i].checked) {
			    if(newIdArray!="")newIdArray+=",";
				newIdArray += checkObj[i].value;
			}
		 }
		if(newIdArray==""){
		  alert("请至少选中1条新闻！");
		  return ;
	    }
	    var newIds = document.getElementById("newIds");
	    newIds.value=newIdArray;
	    //alert(newIdArray);
	    myForm.action="news_del.action";
		myForm.submit();
	}
}
function enter(id){
	window.open("quizpaper.action?myExamPaper.id="+id+"&datetime="+new Date(),"course_exam_5","toolbar=no,fullscreen=1,location=no,directories=no,menubar=no,scrollbars=yes,resizable=no,status=no");
}
function enterEroom(id){
	window.open("quizpaper.action?myExamPaper.id="+id+"&datetime="+new Date(),"course_exam_5","toolbar=no,fullscreen=1,location=no,directories=no,menubar=no,scrollbars=yes,resizable=no,status=no");
}
function isEroom(valid){
	if(valid !=5){
		alert("考场正在修改中，请等待!!!");
		return false;
	}
}
/*
function isEroom2(valid,svlid,isNor,type){
	//alert(valid); 
	//alert(svlid);
	//alert(isNor);
	//alert(type);
	if(isNor==0&&svalid ==5&&type==1){//选拔式
		return true;
	}
	if(isNor==1&&valid !=5){
		if(valid ==11){
			alert("考场已暂停，请等待!!!");
			return false;
		}else{
			alert("考场正在修改中，请等待!!!");
			return false;
		} 
	}
}
*/
function isEroom2(valid){
	//alert(valid); 
	if(valid !=5){
		if(valid ==11){
			alert("考场已暂停，请等待!!!");
			return false;
		}else{
			alert("考场正在修改中，请等待!!!");
			return false;
		} 
	}
}
function iselClass(valid){
	if(valid !=5){
		if(valid ==11){
			alert("培训班已暂停，请等待!!!");
			return false;
		}else{
			alert("培训班正在修改中，请等待!!!");
			return false;
		}
	}
}
//=============发送消息 -- 按培训班========================================
function searchElclassUser(){ 
     width=650;
	 height=500;
  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
	 var rv =  window.showModalDialog("mess_sendElclassUserList.action?x="+Math.random(),null,sFeature);   
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
	/*
	var _d = document.createElement("<span>");
	_d.id = "_pxb_u"+id;
	_d.style.width="170px";
	_d.style.height="14px";
	_d.style.background="#dddfff";
	_d.style.float="left";
	_d.style.border="solid buttonface 1px";  
	if(!document.getElementById("_pxb_u"+id)){
		alert(document.getElementById("_pxb_u"+id));
		alert(_d.id);
		action("mess_getElclassUserInfo.action", "elClass.id="+id+"&input_name=elClasss.id",_d.id);  
		document.getElementById("PXB").appendChild(_d);
		if(document.getElementById("PXB").innerHTML){
			document.getElementById("PXB").style.display='block';
		}
	}
	*/
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
	}
}
//=============发送消息 -- 按考场========================================
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
	//alert(document.getElementById("_kc_u"+id));
	//alert(_d.id);
	//if(!$("#_kc_u"+id)){   
		//alert(_d.id);
		//action("mess_getExamRoomUserInfo.action", "examRoom.id="+id+"&input_name=examRooms.id",_d.id);
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
		//alert($(_d).html());
		$("#KC").append(_d);
		if($("#KC").html()!=""){
			$("#KC").css("display","block");
		}	
		//if(document.getElementById("KC").innerHTML){
		//	document.getElementById("KC").style.display='block';
		//} 
	//}
}  
function deleteExamRoomUserinfo(obj,id){
	$($(obj).parent()).remove();
	//document.getElementById('KC').removeChild(document.getElementById("_kc_u"+id));
	if(!document.getElementById("KC").innerHTML){
		document.getElementById("KC").style.display='none';
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

//=====================分配学员查询==========================================
function DistributionMethods(type){

	var toUserInfo = document.getElementById("toUserInfo");
	var toClassInfo = document.getElementById("toClassInfo");
	var toEroomInfo = document.getElementById("toEroomInfo");
	
	if(type == 0){ 
		toUserInfo.style.display="block";
		toClassInfo.style.display="none";
		toEroomInfo.style.display="none";
	}else if(type == 1){ 
		toUserInfo.style.display="none";
		toClassInfo.style.display="block";
		toEroomInfo.style.display="none"; 
	}else if(type == 2){ 
		toUserInfo.style.display="none";
		toClassInfo.style.display="none";
		toEroomInfo.style.display="block";
	}else{
		alert("未知类型！");
	} 
}


function searchElclassUser_RCCX(){ 
     width=650;
	 height=500;
  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
	 var rv =  window.showModalDialog("mess_sendElclassUserList.action?x="+Math.random(),null,sFeature);   
	 if(null==rv){
	 		alert('您没有选择培训班');
	 }else{
	 	if(rv[0]<=0)  		alert('您没有选择培训班'); 
	 	for(var i = 0 ;i <rv.length ; i++){
	 		addElclassUserinfo_RCCX(rv[i]);
	 	}
	 }
} 
function addElclassUserinfo_RCCX(id){ 
	var _d = document.createElement("<span>");
	_d.id = "_pxb_u"+id;
	_d.style.width="170px";
	_d.style.height="14px";
	_d.style.background="#dddfff";
	_d.style.float="left";
	_d.style.border="solid buttonface 1px";   
	
	if(!document.getElementById("_pxb_u"+id)){ 
		action("mess_getElclassUserInfo.action", "elClass.id="+id+"&input_name=elClasss.id",_d.id);  
		if(!document.getElementById("PXB").innerHTML){ 
			document.getElementById("PXB").appendChild(_d);	 
			document.getElementById("PXB").style.display='block';
		} 
	}
} 