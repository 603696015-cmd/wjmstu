function searchElclassUser(){ 
     width=650;
	 height=500;
  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
	 var rv = window.showModalDialog("mess_sendElclassUserList.action?x="+Math.random(),null,sFeature);  
	 if(null==rv){
	 		alert('您没有选择培训班');
	 }else{ 
	 	if(rv[0]<=0)
			alert('您没有选择培训班');
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
var eridx = 0;
function addExamRoomUserinfo(id){
	if($("#_kc_u"+id).length==0){
		var _d = $("<div>");
		$(_d).attr("id","_kc_u"+id) ;
		$(_d).css("width","100%");
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
			'<input type="hidden" name="erRegistration.erParas['+eridx+'].examRoom.id" value="'+jsondata.id+'">'+
			'<label style="width:260px;float:left;text-align:left;">'+
			'名称：'+jsondata.title+'</label>'+
			'<label style="float:left;text-align:left;">'+
			'是否通过：<select name="erRegistration.erParas['+eridx+'].isPassed">'+
			'<option value="-1">全部</option>'+
			'<option value="1">是</option>'+
			'<option value="2">否</option>'+
			'</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
			'考场成绩：<select name="erRegistration.erParas['+eridx+'].examScoreTerm">'+
			'<option value=">=">>=</option>'+
			'<option value=">">></option>'+
			'<option value="=">=</option>'+
			'<option value="<"><</option>'+
			'<option value="<="><=</option>'+
			'</select><input type="text" maxlength="4" style="width:30px;" name="erRegistration.erParas['+eridx+'].examScore" value="0" />'+
			'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
			'<select name="erRegistration.erParas['+eridx+'].linkTerm">'+
			'<option value="or">or</option>'+
			'<option value="and">and</option>'+
			'</select>'+
			'</label>'+
			'<a style="cursor:hand;float:right;width:14px;height:14px;" href="" onclick="javascript:deleteExamRoomUserinfo(this,'+jsondata.id+');return false;">X</a>');
		 }});
		$("#KC").append(_d);
		eridx ++;
		if($("#KC").html()!=""){
			$("#KC").css("display","block");
		}
	}
}
function addExamRoomUserinfo1(id){
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
function deleteClassUserinfo(obj,id){ 
	document.getElementById('PXB').removeChild(document.getElementById("_pxb_u"+id));
	if(!document.getElementById("PXB").innerHTML){
		document.getElementById("PXB").style.display='none'; 
		if(document.getElementById("PXB_")){ //编辑用 
			document.getElementById("PXB_").style.display='block';//还原Div 
		}  
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
			$(_d).css("width","100%");
			$(_d).css("height","14px");
			$(_d).css("background","#edefff");
			//$(_d).css("float","left");
			$(_d).css("border","solid #fff 1px");
			epHtml='<input type="hidden" name="erRegistration.erepParas['+epindex_+'].examRoom.id" value="'+jsondata.eroomEps[i].erid+'">'+
					'<input type="hidden" name="erRegistration.erepParas['+epindex_+'].examPaper.id" value="'+jsondata.eroomEps[i].epid+'">'+
					'<label style="width:150px;float:left;text-align:left;">'+
					'考场名称：<label style="font-weight:600;">'+jsondata.eroomEps[i].ertitle+'</label></label>'+
					'<label style="width:150px;float:left;text-align:left;">'+
					'试卷名称：<label style="font-weight:600;">'+jsondata.eroomEps[i].eptitle+'</label></label>'+
					'<label style="float:left;text-align:left;margin-left:30px;">'+
					'是否通过：<select name="erRegistration.erepParas['+epindex_+'].isPassed">'+
					'<option value="-1">全部</option>'+
					'<option value="1">是</option>'+
					'<option value="2">否</option>'+
					'</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
					'考试次数：<select name="erRegistration.erepParas['+epindex_+'].examCountTerm">'+
					'<option value=">=">>=</option>'+
					'<option value=">">></option>'+
					'<option value="=">=</option>'+
					'<option value="<"><</option>'+
					'<option value="<="><=</option>'+
					'</select><input type="text" maxlength="4" style="width:30px;" name="erRegistration.erepParas['+epindex_+'].examCount" value="0" />'+
					'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
					'<br/>平均分：<select name="erRegistration.erepParas['+epindex_+'].avgScoreTerm">'+
					'<option value=">=">>=</option>'+
					'<option value=">">></option>'+
					'<option value="=">=</option>'+
					'<option value="<"><</option>'+
					'<option value="<="><=</option>'+
					'</select><input type="text" maxlength="4" style="width:30px;" name="erRegistration.erepParas['+epindex_+'].avgScore" value="0" />'+
					'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
					'最高分：<select name="erRegistration.erepParas['+epindex_+'].maxScoreTerm">'+
					'<option value=">=">>=</option>'+
					'<option value=">">></option>'+
					'<option value="=">=</option>'+
					'<option value="<"><</option>'+
					'<option value="<="><=</option>'+
					'</select><input type="text" maxlength="4" style="width:30px;" name="erRegistration.erepParas['+epindex_+'].maxScore" value="0" />'+
					'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
					'<select name="erRegistration.erepParas['+epindex_+'].linkTerm">'+
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
var clindex_=-1;
function addElclassUserinfo(id){
	clindex_++;
	var _d = $("<div>");
	$(_d).attr("id","_pxb_u"+id) ;
	$(_d).css("width","100%");
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
		'<input type="hidden" name="erRegistration.classParas['+clindex_+'].elClass.id" value="'+jsondata.id+'">'+
		'<label style="width:260px;float:left;text-align:left;">'+
		'名称：'+jsondata.title+'</label>'+
		'<label style="float:left;text-align:left;">'+
		'是否通过：<select name="erRegistration.classParas['+clindex_+'].isPassed">'+
		'<option value="-1">全部</option>'+
		'<option value="1">是</option>'+
		'<option value="2">否</option>'+
		'</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
		'总学分：<input type="text" maxlength="4" style="width:30px;" name="erRegistration.classParas['+clindex_+'].sumScoreStart" value="0" />'+
		'&nbsp;&nbsp;~&nbsp;&nbsp;<input type="text" maxlength="4" style="width:30px;" name="erRegistration.classParas['+clindex_+'].sumScoreEnd" value="100" />'+
		'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
		'<br/>必修总学分：<input type="text" maxlength="4" style="width:30px;" name="erRegistration.classParas['+clindex_+'].bsumScoreStart" value="0" />'+
		'&nbsp;&nbsp;~&nbsp;&nbsp;<input type="text" maxlength="4" style="width:30px;" name="erRegistration.classParas['+clindex_+'].bsumScoreEnd" value="100" />'+
		'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
		'选修总学分：<input type="text" maxlength="4" style="width:30px;" name="erRegistration.classParas['+clindex_+'].xsumScoreStart" value="0" />'+
		'&nbsp;&nbsp;~&nbsp;&nbsp;<input type="text" maxlength="4" style="width:30px;" name="erRegistration.classParas['+clindex_+'].xsumScoreEnd" value="100" />'+
		'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
		'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
		'<select name="erRegistration.classParas['+clindex_+'].linkTerm">'+
		'<option value="or">or</option>'+
		'<option value="and">and</option>'+
		'</select>'+
		'</label>'+
		'<a style="cursor:hand;float:right;width:14px;height:14px;" href="" onclick="javascript:deleteClassUserinfo(this,'+jsondata.id+');return false;">X</a>');
	 }});
	$("#PXB").append(_d);
	if($("#PXB").html()!=""){
		$("#PXB").css("display","block");
	}
}

		
		