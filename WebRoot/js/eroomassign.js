function deleteClassInfo(obj,id,optype){
	if(window.confirm("确定删除？")){ 
		obj.parentNode.parentNode.removeChild(obj.parentNode);
		document.getElementById("PXB").style.display="block";
	}
}		

function deleteEroomInfo(obj,id,optype){
	if(window.confirm("确定删除？")){ 
		obj.parentNode.parentNode.removeChild(obj.parentNode);
		document.getElementById("PXB").style.display="block";
	}
}
var toc=0;
function DistributionMethods(type){
	var toUserInfo = document.getElementById("toUserInfo");
	var toClassInfo = document.getElementById("toClassInfo");
	var toEroomInfo = document.getElementById("toEroomInfo");
	var toEroomEpInfo = document.getElementById("toEroomEpInfo");
	if(type == 0){ 
		toUserInfo.style.display="block";
		toClassInfo.style.display="none";
		toEroomInfo.style.display="none";
		toEroomEpInfo.style.display="none";
		toc=0;
	}else if(type == 1){ 
		toUserInfo.style.display="none";
		toClassInfo.style.display="block";
		toEroomInfo.style.display="none";
		toEroomEpInfo.style.display="none";
		toc=3;
	}else if(type == 2){ 
		toUserInfo.style.display="none";
		toClassInfo.style.display="none";
		toEroomInfo.style.display="block";
		toEroomEpInfo.style.display="none";
		toc=1;
	}else if(type == 3){ 
		toUserInfo.style.display="none";
		toClassInfo.style.display="none";
		toEroomInfo.style.display="none";
		toEroomEpInfo.style.display="block";
		toc=2;
	}else{
		alert("未知类型！");
	} 
}
var clindex_=-1;
function addElclassUserinfo(id){
	clindex_++;
	var _d = $("<div>");
	$(_d).attr("id","_pxb_u"+id) ;
	$(_d).css("width","998px");
	$(_d).css("height","14px");
	$(_d).css("background","#edefff");
	$(_d).css("float","left");
	$(_d).css("border","solid #fff 1px");
	$.ajax(
	{	async:false,  //   
		type:"post",   
	    url:"mess_getElclassUserInfo.action",   
	    data:{"elClass.id":id,"input_name": "elClasss.id","x":Math.random()},   
		success:function(data){
				var jsondata = eval("("+data+")");
	$(_d).html(
		'<input type="hidden" name="classPara['+clindex_+'].elClass.id" value="'+jsondata.id+'">'+
		'<label style="width:260px;float:left;text-align:left;">'+
		'名称：'+jsondata.title+'</label>'+
		'<label style="float:left;text-align:left;">'+
		'总学分：<input type="text" maxlength="4" style="width:30px;" name="classPara['+clindex_+'].sumScoreStart" value="0" />'+
		'&nbsp;&nbsp;~&nbsp;&nbsp;<input type="text" maxlength="4" style="width:30px;" name="classPara['+clindex_+'].sumScoreEnd" value="100" />'+
		'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
		'必修总学分：<input type="text" maxlength="4" style="width:30px;" name="classPara['+clindex_+'].bsumScoreStart" value="0" />'+
		'&nbsp;&nbsp;~&nbsp;&nbsp;<input type="text" maxlength="4" style="width:30px;" name="classPara['+clindex_+'].bsumScoreEnd" value="100" />'+
		'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
		'选修总学分：<input type="text" maxlength="4" style="width:30px;" name="classPara['+clindex_+'].xsumScoreStart" value="0" />'+
		'&nbsp;&nbsp;~&nbsp;&nbsp;<input type="text" maxlength="4" style="width:30px;" name="classPara['+clindex_+'].xsumScoreEnd" value="100" />'+
		'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
		'是否通过：<select name="classPara['+clindex_+'].isPassed">'+
		'<option value="-1">全部</option>'+
		'<option value="1">是</option>'+
		'<option value="2">否</option>'+
		'</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
		'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
		'<select name="classPara['+clindex_+'].linkTerm">'+
		'<option value="or">or</option>'+
		'<option value="and">and</option>'+
		'</select>'+
		'</label>'+
		'<a style="cursor:hand;float:right;width:14px;height:14px;" href="" onclick="javascript:deleteExamRoomUserinfo(this,'+jsondata.id+');return false;">X</a>');
	 }});
	$("#PXB").append(_d);
	if($("#PXB").html()!=""){
		$("#PXB").css("display","block");
	}
}

var index_=-1;
function addExamRoomUserinfo(id){
	index_++;
	var _d = $("<div>");
	$(_d).attr("id","_kc_u"+id) ;
	$(_d).css("width","998px");
	$(_d).css("height","14px");
	$(_d).css("background","#edefff");
	$(_d).css("float","left");
	$(_d).css("border","solid #fff 1px");
	$.ajax(
	{	async:false,  //   
		type:"post",   
	    url:"mess_getExamRoomUserInfo.action",   
	    data:{"examRoom.id":id,"input_name": "examRooms.id","x":Math.random()},   
		success:function(data){
				var jsondata = eval("("+data+")");
	$(_d).html(
		//'<input type="hidden" name="'+jsondata.input_name+'" value="'+jsondata.id+'">'+
		'<input type="hidden" name="erParas['+index_+'].examRoom.id" value="'+jsondata.id+'">'+
		'<label style="width:260px;float:left;text-align:left;">'+
		'名称：'+jsondata.title+'</label>'+
		'<label style="float:left;text-align:left;">'+
		'是否通过：<select name="erParas['+index_+'].isPassed">'+
		'<option value="-1">全部</option>'+
		'<option value="1">是</option>'+
		'<option value="2">否</option>'+
		'</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
		'考场成绩：<select name="erParas['+index_+'].examScoreTerm">'+
		'<option value=">=">>=</option>'+
		'<option value=">">></option>'+
		'<option value="=">=</option>'+
		'<option value="<"><</option>'+
		'<option value="<="><=</option>'+
		'</select><input type="text" maxlength="4" style="width:30px;" name="erParas['+index_+'].examScore" value="0" />'+
		'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
		'<select name="erParas['+index_+'].linkTerm">'+
		'<option value="or">or</option>'+
		'<option value="and">and</option>'+
		'</select>'+
		'</label>'+
		'<a style="cursor:hand;float:right;width:14px;height:14px;" href="" onclick="javascript:deleteExamRoomUserinfo(this,'+jsondata.id+');return false;">X</a>');
	 }});
	$("#KC").append(_d);
	if($("#KC").html()!=""){
		$("#KC").css("display","block");
	}
}

function searchExamRoomEpUser(){
     width=650;
	 height=500;
  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
	 var rv =  window.showModalDialog("mess_sendExamRoomUserList.action?x="+Math.random(),null,sFeature);   
	 if(null==rv){
	 		alert('您没有选择考场');
	 }else{
	 	if(rv[0]<=0)  		alert('您没有选择考场'); 
	 	for(var i = 0 ;i <rv.length ; i++){
	 		if($("#_kcep_u"+rv[i]).length <=0)
	 		addExamRoomEpUserinfo(rv[i]);
	 	}
	 }
}
var epindex_=-1;
function addExamRoomEpUserinfo(id){
	$.ajax(
	{	async:false,  //   
		type:"post",   
	    url:"mess_getExamRoomEpUserInfo.action",   
	    data:{"examRoom.id":id,"input_name": "examRooms.id","x":Math.random()},   
		success:function(data){
		var jsondata = eval("("+data+")");
		//alert(jsondata.eroomEps.length);
		var epHtml="";
		for(var i=0;i<jsondata.eroomEps.length;i++){
			if($("#_kcep_u_"+jsondata.eroomEps[i].epid+"_"+id).length >0){
				continue;
			}
			epindex_++;
			var _d = $("<div>");
			$(_d).attr("id","_kcep_u_"+jsondata.eroomEps[i].epid+"_"+id) ;
			$(_d).css("width","998px");
			$(_d).css("height","14px");
			$(_d).css("background","#edefff");
			//$(_d).css("float","left");
			$(_d).css("border","solid #fff 1px");
			epHtml='<input type="hidden" name="erepParas['+epindex_+'].examRoom.id" value="'+jsondata.eroomEps[i].erid+'">'+
					'<input type="hidden" name="erepParas['+epindex_+'].examPaper.id" value="'+jsondata.eroomEps[i].epid+'">'+
					'<label style="width:150px;float:left;text-align:left;">'+
					'考场名称：<label style="font-weight:600;">'+jsondata.eroomEps[i].ertitle+'</label></label>'+
					'<label style="width:150px;float:left;text-align:left;">'+
					'试卷名称：<label style="font-weight:600;">'+jsondata.eroomEps[i].eptitle+'</label></label>'+
					'<label style="float:left;text-align:left;margin-left:30px;">'+
					'是否通过：<select name="erepParas['+epindex_+'].isPassed">'+
					'<option value="-1">全部</option>'+
					'<option value="1">是</option>'+
					'<option value="2">否</option>'+
					'</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
					'考试次数：<select name="erepParas['+epindex_+'].examCountTerm">'+
					'<option value=">=">>=</option>'+
					'<option value=">">></option>'+
					'<option value="=">=</option>'+
					'<option value="<"><</option>'+
					'<option value="<="><=</option>'+
					'</select><input type="text" maxlength="4" style="width:30px;" name="erepParas['+epindex_+'].examCount" value="0" />'+
					'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
					'平均分：<select name="erepParas['+epindex_+'].avgScoreTerm">'+
					'<option value=">=">>=</option>'+
					'<option value=">">></option>'+
					'<option value="=">=</option>'+
					'<option value="<"><</option>'+
					'<option value="<="><=</option>'+
					'</select><input type="text" maxlength="4" style="width:30px;" name="erepParas['+epindex_+'].avgScore" value="0" />'+
					'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
					'最高分：<select name="erepParas['+epindex_+'].maxScoreTerm">'+
					'<option value=">=">>=</option>'+
					'<option value=">">></option>'+
					'<option value="=">=</option>'+
					'<option value="<"><</option>'+
					'<option value="<="><=</option>'+
					'</select><input type="text" maxlength="4" style="width:30px;" name="erepParas['+epindex_+'].maxScore" value="0" />'+
					'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
					'<select name="erepParas['+epindex_+'].linkTerm">'+
					'<option value="or">or</option>'+
					'<option value="and">and</option>'+
					'</select>'+
					'</label>'+
					'<a style="cursor:hand;float:right;width:14px;height:14px;" href="" onclick="javascript:deleteExamRoomEpUserinfo(this);return false;">X</a>';
					$(_d).html(epHtml);
					$("#KCEP").append(_d);
		}
	//$(_d).html(epHtml);
	 }});
	//$("#KCEP").append(_d);
	if($("#KCEP").html()!=""){
		$("#KCEP").css("display","block");
	}
}
function deleteExamRoomEpUserinfo(obj){
	$($(obj).parent()).remove();
	if(!document.getElementById("KCEP").innerHTML){
		document.getElementById("KCEP").style.display='none';
	}
}
function select_All(){
	var cks= document.getElementsByName("canAssignUsers.id");
	for(var i = 0 ; i < cks.length; i++){
		cks[i].checked= true;
	}
}
function select_Fan(){
	var cks= document.getElementsByName("canAssignUsers.id");
	for(var i = 0 ; i < cks.length; i++){
		cks[i].checked= !cks[i].checked;
	}
}
function select_Bux(){
	var cks= document.getElementsByName("canAssignUsers.id");
	for(var i = 0 ; i < cks.length; i++){
		cks[i].checked= false;
	}
}
function page(i){
	acc_list.action=  "examroom_assignSearchlist.action";
	document.getElementById("pageNow").value=i;
	acc_list.submit();
}
function backSearch(){
	acc_list.action=  "examroom_assignwcSearchInit.action";
	acc_list.submit();
}
function assign2user(){
	if(confirm("确定添加这些人员？")){
		if(toc==0){
			acc_list.action=  "examroom_assignwc.action";
			acc_list.submit();
		}else if(toc==3){
			var pa1 = $("#acc_list").serialize();
			$.post("examroom_assignwc.action", pa1+"&ajax=1&x="+Math.random(), function (data) {
				seachOnClassPage($("#pageNow").val());
			});
		}else{
			//ajax
			//alert("ccddee");
			var pa1 = $("#acc_list").serialize();
			$.post("examroom_assignwc.action", pa1+"&ajax=1&x="+Math.random(), function (data) {
				seachOnEroomPage($("#pageNow").val());
			});
		}
	}
}
function unassign2user(){
	if(confirm("确定移除这些人员？")){
		if(toc==0){
			acc_list.action="examroom_unassignwc.action";
			acc_list.submit();
		}else if(toc==3){
			var pa1 = $("#acc_list").serialize();
			$.post("examroom_unassignwc.action", pa1+"&ajax=1&x="+Math.random(), function (data) {
				seachOnClassPage($("#pageNow").val());
			});
		}else{
			//ajax
			//alert("ccddee");
			var pa1 = $("#acc_list").serialize();
			$.post("examroom_unassignwc.action", pa1+"&ajax=1&x="+Math.random(), function (data) {
				seachOnEroomPage($("#pageNow").val());
			});
		}
	}
}
function assign2users(){
	//alert($("#queryManner").val());
	if(confirm("确定分配所有人员？")){
		//ab_list.action= "examroom_assignwcs.action";
		//ab_list.submit();
		if(toc==0){
			acc_list.action= "examroom_assignwcs.action";
			acc_list.submit();
		}else if(toc==3){
			$("#queryManner").val(3);
			var pa1 = $("#acc_list").serialize();
			$.post("examroom_assignwcsAjax.action", pa1+"&ajax=1&x="+Math.random(), function (data) {
				seachOnClassPage($("#pageNow").val());
			});
		}else{
			//ajax
			//alert("ccddee");
			var pa1 = $("#acc_list").serialize();
			$.post("examroom_assignwcsAjax.action", pa1+"&ajax=1&x="+Math.random(), function (data) {
				seachOnEroomPage($("#pageNow").val());
			});
		}
	}
}
function seachOnEroom(queryManner){
	//acc_list.action= "examroom_seachUser.action";
	//acc_list.submit();
	$("#queryManner").val(queryManner);
	seachOnEroomPage(0);
}
function seachOnEroomPage(pn){
$("#pageNow").attr("value",pn);
var pa1 = $("#acc_list").serialize();
	//alert(pa1);

$.post("examroom_seachUser.action", pa1+"&x="+Math.random(), function (data) {
	if(data=='err1'){
		alert("没有选择考场");
	}else{
		var jsondata = eval("("+data+")");
		var cnt =jsondata.count;
		var ulist = jsondata.users;
		//alert(cnt);
		$("#page_div").html(getPageDiv(cnt,pn,10))
		var dls = $("#data_list").find("tr");
		for(var i =0;i<dls.length;i++){
			$(dls[i]).remove();
		}
		for(var i = 0;i<ulist.length;i++){
			var tr = $("<tr>");
			//var x = (ulist[i].joinwayInt==0||ulist[i].joinwayInt==2)?'<input type="checkbox" name="canAssignUsers.id" value="'+ulist[i].id+'" />':"";
			var x = '<input type="checkbox" name="canAssignUsers.id" value="'+ulist[i].id+'" />';
			var uAssign=(ulist[i].assign=='已分配')?'<font color="red">已分配</font>':"未分配";
			tr.append('<td width="20" height="20" align="center">'+x +
						//<s:if test="joinwayInt==0||joinwayInt==2">
						//	<input type="checkbox" name="canAssignUsers.id"
						//		value="<s:property value="id"/>" />
						//</s:if>
					'</td><td height="30" align="center">'+
						ulist[i].username+
					'</td><td height="20" align="center">'+
						ulist[i].realname +
					'</td> <td height="20" align="center">'+
						ulist[i].depname+
					'</td> <td height="20" align="center">'+
						ulist[i].rolename+
					'</td> <td height="20" align="center">'+
						ulist[i]. sex +
					'</td> <td height="20" align="center">'+
					 ulist[i].jz +
					'</td> <td height="20" align="center">'+
						ulist[i].age  +
					'</td> <td height="20" align="center">'+
						uAssign+
					'</td> <td height="20" align="center">'+
						ulist[i].joinway+
					'</td>' );
			$("#data_list").append(tr);
		}
	}
});
}
function seachOnClassPage(pn){
 	$("#pageNow").attr("value",pn);
 	var pa1 = $("#acc_list").serialize();
	$.post("elclass_seachUser.action", pa1+"&x="+Math.random(), function (data) {
		if(data=='err1'){
			alert("没有选择培训班");
		}else{
			var jsondata = eval("("+data+")");
			var cnt =jsondata.count;
			var ulist = jsondata.users;
			//alert(cnt);
			$("#page_div").html(getClassPageDiv(cnt,pn,10))
			var dls = $("#data_list").find("tr");
			for(var i =0;i<dls.length;i++){
				$(dls[i]).remove();
			}
			for(var i = 0;i<ulist.length;i++){
				var tr = $("<tr>");
				//var x = (ulist[i].joinwayInt==0||ulist[i].joinwayInt==2)?'<input type="checkbox" name="canAssignUsers.id" value="'+ulist[i].id+'" />':"";
				var x = '<input type="checkbox" name="canAssignUsers.id" value="'+ulist[i].id+'" />';
				var uAssign=(ulist[i].assign=='已分配')?'<font color="red">已分配</font>':"未分配";
				tr.append('<td width="20" height="20" align="center">'+x +
							//<s:if test="joinwayInt==0||joinwayInt==2">
							//	<input type="checkbox" name="canAssignUsers.id"
							//		value="<s:property value="id"/>" />
							//</s:if>
						'</td><td height="30" align="center">'+
							ulist[i].username+
						'</td><td height="20" align="center">'+
							ulist[i].realname +
						'</td> <td height="20" align="center">'+
							ulist[i].depname+
						'</td> <td height="20" align="center">'+
							ulist[i].rolename+
						'</td> <td height="20" align="center">'+
							ulist[i]. sex +
						'</td> <td height="20" align="center">'+
						 ulist[i].jz +
						'</td> <td height="20" align="center">'+
							ulist[i].age  +
						'</td> <td height="20" align="center">'+
							uAssign+
						'</td> <td height="20" align="center">'+
							ulist[i].joinway+
						'</td>' );
				$("#data_list").append(tr);
			}
		}
	});
}
function doForm(){
	$("#pageNow").val(0);
	acc_list.submit();
}
function eroomEpWriteUser(roomid,epid){
	document.location.href="eroomEpWriteUserInit.action?examRoom.id="+roomid+"&examPaper.id="+epid;
}
function eroomEpdeleteUser(roomid,epid){
	document.location.href="eroomEpDeleteUserInit.action?examRoom.id="+roomid+"&examPaper.id="+epid;
}
function sh0( ){
    if(window.confirm("确定创建完成？"))
    	document.location='examroom_valid.action?examRoom.id=${examRoom.id }'
}
function sh0(roomid ){
    if(window.confirm("确定创建完成？"))
    	document.location='examroom_valid.action?examRoom.id='+roomid;
}