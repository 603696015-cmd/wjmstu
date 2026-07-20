<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>"/>
		<META http-equiv=Page-Enter  
			content=RevealTrans(Duration=0.5,Transition=14)/>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/assist.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/libutil.js"></script>
		<script type="text/javascript" src="js/erwithoutop.js"></script>
		<script type="text/javascript" src="js/inputcheck.js"></script>
		<script type="text/javascript" src="js/basedataop.js"></script>
		<script type="text/javascript">
		function recovery(id,erid,index){
				//alert("nihao");
				//return false;
				$.post("eroom_huifu_ep.action", {
				"examPaper.id":id,
				"examRoom.id":erid,
				"x":Math.random
				},
				function (data) {
					//alert('恢复成功');
				});
				//$("#a_erdel_"+index).attr("readonly","");
				//$("#b_erdel_"+index).attr("disabled","");
				//$("#c_erdel_"+index).attr("disabled","");
				//var checkArray=$("#eps_div").find("input");
				var checkArray=$("#eps_"+index).find("input");
				//alert(checkArray.length);
				for(var i=0;i<checkArray.length;i++){
					if($(checkArray[i]).attr("type")=="checkbox"||$(checkArray[i]).attr("type")=="radio"){
						$(checkArray[i]).attr("disabled","");
					}else if($(checkArray[i]).attr("type")=="text"){
						$(checkArray[i]).attr("readonly","");
					}
				}
				$("#d_erdel_"+index).css("display","inline");
				$("#e_erdel_"+index).html("");
			}
		function moveEroomep(epid,sortid,manner){
			document.getElementById("erpid").value=epid;
			document.getElementById("epSortid").value=sortid;
			document.getElementById("manner").value=manner;
			eroomepForm.submit();
		}
		function deleteEps(obj,id,islx,index){
				if(window.confirm("确定将此试卷从本考场中删除？")){
					erid = <s:property value="examRoom.id"/> ;
					if(islx==1){
						$.post("eroom_delete_ep.action", {
							"examPaper.id":id,
							"examRoom.id":erid,
							"x":Math.random
							}, 
							function (data) {
								//alert('删除成功');
							});
						//obj.parentNode.parentNode.removeChild(obj.parentNode);
						//alert($("#d_erdel_"+index).css("display"));
						//$("#a_erdel_"+index).attr("readonly","true");
						//$("#b_erdel_"+index).attr("disabled","false");
						//$("#c_erdel_"+index).attr("disabled","false");
						//试卷信息的disabled
						//var checkArray=$("#eps_div").find("input");
						var checkArray=$("#eps_"+index).find("input");
						//alert(checkArray.length);
						for(var i=0;i<checkArray.length;i++){
							if($(checkArray[i]).attr("type")=="checkbox"||$(checkArray[i]).attr("type")=="radio"){
								$(checkArray[i]).attr("disabled","false");
							}else if($(checkArray[i]).attr("type")=="text"){
								$(checkArray[i]).attr("readonly","true");
							}
						}
						$("#d_erdel_"+index).css("display","none");
						var _span=$("<a>");
						_span.html("恢复");
						_span.attr("class","textbg4");
						_span.attr("href","javascript:;");
						_span.click(function(){recovery(id,erid,index);});
						//_span.click(recovery(id,erid,index));
						$("#e_erdel_"+index).html(_span);
						//obj.parentNode.parentNode.removeChild(obj.parentNode);
					}else{
						$.post("eroom_delete_eplx.action", {
							"examPaper.id":id,
							"examRoom.id":erid,
							"x":Math.random
							}, 
							function (data) {
								alert('删除成功');
							});
					obj.parentNode.removeChild(obj);
					}
					//obj.parentNode.parentNode.removeChild(obj.parentNode);
				}
			}
		function showpwd(i){
				if(i==0){
					$("#pwd_div").css("display","none");
				}else{
					$("#pwd_div").css("display","block");
				}
			}
			function _onsubmit(){
				
				if($("#eroom_title").val()==''){
					alert("标题不能为空");
					$("#eroom_title").focus();
					return false; 
				} 
				
				var ty=0;
				if(ty==0){
					if(document.getElementById("invigilators").innerHTML ==''){
						alert("监考人员不能为空");
						return false;
					}if(document.getElementById("appraises").innerHTML==''){
						alert("阅卷人员不能为空");
						return false;
					}
				}
				if(document.getElementById("passgrade").value==''){
					alert("通过的成绩不能为空");
					return false;
				}
				if(!checkFloat($("#passgrade").val())){
					alert("通过的成绩不能为非数字");
					return false;
				}
				if(parseInt($("#passgrade").val())>100||parseInt($("#passgrade").val())<0){
					alert("通过的成绩不能大于100或小于0");
					return false;
				}
				var epidsc = 0;
				var epidsiii=0;
				$("input[type='hidden']").each(function(){
					if(undefined!=$(this).attr("epids")&&$(this).attr("epids")=='1'){
						epidsc++;
						if($(this).val()==''||$(this).val()=='undefined'){
							epidsiii++;
							return false;
						}
					} 
				});
				if(epidsc<=0){
					alert("请添加试卷");
					return false;
				}
				if(epidsiii>0){
					alert("请选择试卷");
					return false;
				}
				if($("#epid").val()==''){
					alert("请选择试卷");
					$("#epid").focus();
					return false; 
				}
				return true;
			}
			
			
			//在阅卷人员列表中加上阅卷组长
   function searchUserInit2(_id,input_name,comp){
     width=800;
	 height=450;
  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
	 var rv =  window.showModalDialog("mess_sendUserlist.action?sub_department=1&x="+Math.random(),null,sFeature); 
	 if(null==rv){
	 	alert('您没有选择用户！');
	 }else{
	 	if(rv[0]<=0)  	alert('您没有选择用户！');
	 	for(var i = 0 ;i <rv.length ; i++){
	 		if(!checkHasUser(_id,rv[i])){
		 		addUserinfo2(_id,rv[i],input_name);
		 		//var nouser="nouser"+_id;
		 		//document.getElementById(nouser).value=nouser;
	 		}
	 	}
	 	var chkzus=document.getElementsByName("examRoom.appr_header.id");
	 	chkzus[0].checked=true;
	 }
    }

  function addUserinfo2(_id,id,inputname){
	var _d = document.createElement("<span>");
	_d.id = "_d_u"+_id+id;
	_d.style.width="110px";
	_d.style.height="14px";
	_d.style.background="#dddfff";
	_d.style.float="left";
	_d.style.border="solid buttonface 1px";
	/*
	$.post("mess_getZuZhang.action", {
		"elUser.id":id,
		"input_name":inputname, 
		"x":Math.random
		}, 
		function (data) {
			$("#"+_d.id).html(data);
		});
	*/
	$.ajax({
		async:false,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
		type:"post",
		url:"mess_getZuZhang.action",data:{"elUser.id":id,"input_name":inputname,"x":Math.random},
		success:function (data) {
		   $(_d).html(data);
		}
	}); 
	
	document.getElementById(_id).appendChild(_d);
	//alert(document.getElementById(_id).firstChild.innerHTML);  
	
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
			
			var epcount=parseInt("<s:property value="examRoom.exampapers.size" />");
			function erep_addinit(){
				var eps =  document.createElement("div");
				eps.id ="eps_"+epcount;
				epsStr = "<span style='color:red'>试卷：</span><span style=\"width:28px;\" id=\"epts"+epcount+"\"></span>分&nbsp;&nbsp;&nbsp;<span style=\"width:150px;\" id=\"eptitle"+epcount+"\"></span>"+
				" <input type=\"hidden\" id=\"epid"+epcount+"\" name=\"examPapers["+epcount+"].id\" epids='1' value=\"\"/>"+
				" <a href=\"javascript:erep_add("+epcount+");\" class='textbg6'>选择试卷</a>达标线：<input type='text' value='60' size=3 name=\"examPapers["+epcount+"].passgrade\"/>%"+
				//"可查看答卷<input type='checkbox' name='examPapers["+epcount+"].stuview' value='1' />"+
				"&nbsp;&nbsp;可考次数：<input type='text' value='1' size='2' name=\"examPapers["+epcount+"].quizcount\"/>"+
				"<br/>"+
				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
				"<span style='color:red'>成绩取：</span><input name='examPapers["+epcount+"].passmanner' type='radio'	value='1'/> 平均分"+
				"<input name='examPapers["+epcount+"].passmanner' type='radio'	value='0' checked='checked'/> 最高分"+
				"&nbsp;&nbsp;<span style='color:red'>答卷可看</span><input type='checkbox' name='examPapers["+epcount+"].quizlook' value='1' />"+
				"&nbsp;&nbsp;<span style='color:red'>成绩可看</span><input type='checkbox' name='examPapers["+epcount+"].scorelook' value='1' />"+
				"&nbsp;&nbsp;&nbsp;&nbsp; <a style=\"cursor: pointer; width: 14px; height: 14px;color:red\" class='textbg4' onclick=\"javascript:erep_del(this,"+epcount+" );return false;\">X</a>";
				/*
				"<lable name=\"prac\">"+
				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style='color:red'><p/>练习：</span><span style=\"width:25px;\" id=\"epts_lx_"+epcount+"\"></span>分&nbsp;&nbsp;&nbsp;<span style=\"width: 150px;\" id=\"eptitle_lx_"+epcount+"\"></span>"+
				"<input type=\"hidden\" id=\"epid_lx_"+epcount+"\" name=\"examPapers["+epcount+"].prac.id\" />"+
				"&nbsp;&nbsp;&nbsp;<a href='#' onclick=\"javascript:erep_add('_lx_"+epcount+"');return false;\" class='textbg6'>选择练习</a> "+
				"次数：<input type=\"text\" style=\"width: 40px;\" name=\"examPapers["+epcount+"].practimes\" value=\"0\">"+
				"最低分：<input type=\"text\" style=\"width: 40px;\" name=\"examPapers["+epcount+"].pracscore\" value=\"0\">"+
				"</lable>"+
				"<a style=\"cursor: pointer; width: 14px; height: 14px;color:red\" onclick=\"javascript:erep_del(this,"+epcount+" );return false;\">X</a>" ;
				*/
				eps.innerHTML= epsStr;
				epcount++;	//href=\"\"
				document.getElementById("eps_div").appendChild(eps);		
			}
			function erep_del(obj,id ){
				if(window.confirm("确定将此试卷从考场中删除？")){
				 epcount--
				obj.parentNode.parentNode.removeChild(obj.parentNode);
				}
			}
			function erep_add(id){
				 width=600;
				 height=400;
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv =  window.showModalDialog("assist_survey_epsearchInit.action?"+Math.random(),null,sFeature);
				 if(null==rv){
				 	alert('您没有选择试卷！'); 
				 }else{
				 	if(rv[0]<=0)  	alert('您没有选择试卷！');
				 	document.getElementById("eptitle"+id).innerHTML=rv[1];
				 	document.getElementById("epid"+id).value=rv[0];
				 	document.getElementById("epts"+id).innerHTML=rv[2];  
				 }
			}
	</script>
	</HEAD>
	<body onLoad="loadData('<s:property value="erRegistration.examRoomIds" />','<s:property value="erRegistration.elclassIds" />');">
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
		</ul>
		<!-- 内容 -->
		<s:form action="eroomEpsort" method="post" name="eroomepForm">
			<s:hidden name="examRoom.id" />
			<s:hidden name="examPaper.id" id="erpid" />
			<s:hidden name="examPaper.sortid" id="epSortid" />
			<s:hidden name="manner" id="manner" />
		</s:form>
		<div style="margin-top:20px;text-align:center;">
			<label style="font-size: 16px;">
				一般考试管理<a href="javascript:_onsubmit();"></a>
			</label>
			<br>
			<s:form theme="simple" id="form_exam_add" name="form_exam_add"  
				method="post" action="practicepaper_alterSearch_room.action"
				onsubmit="return _onsubmit();">
				<span style="color: #ff0000;"></span>
				<table width="100%" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr>
						<td width="130" height="30" align="right" >
							<span class="neededitem">*</span>考场标题：
						</td>
						<td >
							<label>
								<s:textfield name="examRoom.title" id="title" size="60" />
							</label>
						</td>
					</tr>
					<tr>
						<td align="right" >
							<span class="neededitem">*</span>所属考场库：
						</td>
						<td bgcolor="#FFFFFF" colspan="3">
							<label>
								<select name="examRoom.eroomLib.id" id="er_erlid">
									<wysLib:eroomLibSelect selectid="${examRoom.eroomLib.id}"></wysLib:eroomLibSelect>
								</select>
							</label>
						</td>
					</tr>
					<tr>
						<td height="30" align="right" >
							考场说明：
						</td>
						<td >
							<label>
								<s:textarea name="examRoom.description" cols="60" rows="7" />
							</label>
						</td>
					</tr> 
					<tr>
						<td height="30" align="right" >
							考场图片：
						</td>
						<td >
							<label>
								<s:textfield name="examRoom.mainimg" id="pic" size="60" />
								<a href="javascript:setUrl('pic');" class="textbg4" style="width:80px">浏览资源库</a>
							</label>
						</td>
					</tr>
					<tr>
						<td height="30" align="right" >
							考试地点：
						</td>
						<td >
							<label>
								<s:textfield name="examRoom.location" size="60" />
							</label>
						</td>
					</tr>
					<tr>
						<td height="30" align="right" >
							<span class="neededitem">*</span>监考人员：
						</td>
						<td >
							<div id="invigilators"> 
								<s:iterator value="examRoom.invigilators">
									<span
										style="width: 110px; height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
										<label style="width: 80px; float: left;">
											<s:property value="realname" />
										</label> <a
										style="cursor: hand; float: right; width: 14px; height: 14px;"
										href=""
										onclick="javascript:deleteUserinfo(this,<s:property value="id"/>,'rinvigilators');return false;">X</a>
									</span>
								</s:iterator>
							</div>
							<a href=""
								onclick="searchUserInit('invigilators','examRoom.invigilators.id'); return false;" class="textbg4">添加</a>
						</td>
					</tr>
					<tr>
						<td height="30" align="right" >
							<span class="neededitem">*</span>阅卷人员：
						</td>
						<td >
							<div id="appraises">
							
							
							<s:iterator value="examRoom.appraises">
									<span
										style="width: 110px; height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
     									<!-- 设置一个隐藏域，用于保存原来的数据，提交表单的时候一块提交过去 -->
     									<input type="hidden" name="examRoom.appraises.id" value="<s:property value="id" />" /> 
										<!-- 必须给radio一个name属性并赋值，radio才可以进行操作选中是否 -->
										<input type="radio" name="examRoom.appr_header.id" value="<s:property value="id" />" <s:if test="isLeader==1">checked="checked"</s:if> /> 							 
											<s:property value="realname" />
										<a
										style="cursor: hand; float: right; width: 14px; height: 14px;"
										href=""
										onclick="javascript:deleteUserinfo(this,<s:property value="id"/>,'rappraises');return false;">X</a>
									</span>
								</s:iterator>
								
								
								
							
							</div>
							<a href=""
								onclick="searchUserInit2('appraises','examRoom.appraises.id'); return false;" class="textbg4">添加</a>
						</td>
					</tr>
					<tr>
						<td height="30" align="right" >
							复核人员：
						</td>
						<td >
							<div id="valids">
								<s:iterator value="examRoom.valids">
									<span
										style="width: 110px; height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
										<label style="width: 80px; float: left;">
											<s:property value="realname" />
										</label> <a
										style="cursor: hand; float: right; width: 14px; height: 14px;"
										href=""
										onclick="javascript:deleteUserinfo(this,<s:property value="id"/>,'valids');return false;">X</a>
									</span>
								</s:iterator>
							</div>
							<a href=""
								onclick="searchUserInit('valids','examRoom.valids.id'); return false;" class="textbg4">添加</a>
						</td>
					</tr>
					<tr>
						<td height="30" align="right" >
							<span class="neededitem">*</span>通过成绩：
						</td>
						<td >
							<label>
								<input type="text" style="width: 40px;"
									name="examRoom.passgrade" id="passgrade"
									value="<s:property value="examRoom.passgrade"/>">
								%
							</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<span class="neededitem">*</span>设定密码：
						</td>
						<td>
							<input type="radio" onclick="showpwd(0)" name="examRoom.pwdneed" <s:if test="examRoom.pwdneed==0">checked="checked"</s:if> value="0"/>不需要
							<input type="radio" onclick="showpwd(1)" name="examRoom.pwdneed" <s:if test="examRoom.pwdneed==1">checked="checked"</s:if> value="1"/>需要
							<div id="pwd_div"  <s:if test="examRoom.pwdneed==0">style="display: none;"</s:if>>密码:<input type="text" value="<s:property value="examRoom.pwd"/>" name="examRoom.pwd" />&nbsp;有效期至:<input type="text" name="examRoom.pwdtime" value="<s:date name="examRoom.pwdtime" format="yyyy-MM-dd HH:mm:ss"/>" onclick="setday(this)" /> </div>
							<div>试卷缓存:<input type="radio" <s:if test="examRoom.cacheepsize==1">checked="checked"</s:if> name="examRoom.cacheepsize" value="1"/>是<input type="radio" <s:if test="examRoom.cacheepsize==-1">checked="checked"</s:if> name="examRoom.cacheepsize" value="-1"/>否<!-- 试卷缓存数量:<input type="text" size="4" name="examRoom.cacheepsize" value="<s:property value="examRoom.cacheepsize"/>"/> -->
							&nbsp;&nbsp;<input <s:if test="examRoom.cacheeprefresh==1">checked="checked"</s:if> type="checkbox" value="1" name="examRoom.cacheeprefresh"/>密码改变时刷新缓存
							&nbsp;&nbsp;<input <s:if test="examRoom.epqsort==1">checked="checked"</s:if> type="checkbox" value="1" name="examRoom.epqsort"/>试卷中试题随机排序
							</div>
							
						</td>
					</tr>
					<tr>
						<td height="30" align="right" >
							<span class="neededitem">*</span>所用试卷：
						</td>
						<td >
							<div id="eps_div">
								<s:iterator value="examRoom.exampapers" status="epsst">
									<div id="eps_<s:property value="#epsst.index"/>"> 
										<span style='color:red'>试卷：</span>
										<span style="width:150px;" id="epts__<s:property value="#epsst.index"/>" ><s:property value="ep_tscore" /></span>
										<span style="width:150px;" id="eptitle__<s:property value="#epsst.index"/>"><s:property value="title" /> </span>
										<input type="hidden" epids='1'
											id="epid__<s:property value="#epsst.index"/>"
											name="examPapers.id" value="<s:property value="id"/>" />
										<%-- <a href="javascript:erep_add('__<s:property value="#epsst.index"/>');" class="textbg6">选择试卷</a> --%>
										达标线：<input id="a_erdel_<s:property value="#epsst.index"/>" type='text' size="3" value='<s:property value="passgrade"/>' 
											name="examPapers[<s:property value="#epsst.index"/>].passgrade"  <s:if test="status==1">readonly="readonly"</s:if> />%&nbsp;&nbsp;&nbsp;<%-- 可查看答卷<input type="checkbox" name="examPapers[<s:property value="#epsst.index"/>].stuview" <s:if test="stuview==1">checked='checked'</s:if> value="1" /> --%>
										 &nbsp;&nbsp;可考次数：<input type="text" <s:if test="status==1">readonly="readonly"</s:if> value="<s:property value="quizcount"/>" size="2" name="examPapers[<s:property value="#epsst.index"/>].quizcount"/> 
				 <br/> 
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
				<span style='color:red'>成绩取：</span><input name="examPapers[<s:property value="#epsst.index"/>].passmanner" type="radio" <s:if test="status==1">disabled="disabled"</s:if>	<s:if test="passmanner==1">checked="checked"</s:if>value="1"/> 平均分 
				<input name="examPapers[<s:property value="#epsst.index"/>].passmanner" type="radio" <s:if test="status==1">disabled="disabled"</s:if> value="0" <s:if test="passmanner==0">checked="checked"</s:if>/> 最高分 
				<span style='color:red'>答卷可看</span><input id="b_erdel_<s:property value="#epsst.index"/>" type="checkbox" name="examPapers[<s:property value="#epsst.index"/>].quizlook" <s:if test="quizlook==1">checked='checked'</s:if> <s:if test="status==1">disabled="disabled"</s:if> value="1" />
											<span style='color:red'>成绩可看</span><input id="c_erdel_<s:property value="#epsst.index"/>" type="checkbox" name="examPapers[<s:property value="#epsst.index"/>].scorelook" <s:if test="scorelook==1">checked='checked'</s:if> <s:if test="status==1">disabled="disabled"</s:if> value="1" />
											<span id="d_erdel_<s:property value="#epsst.index"/>" style="<s:if test="status==1">display:none;</s:if>" >
											&nbsp;&nbsp;&nbsp;&nbsp; <a class="textbg4" style="cursor:pointer;width:14px;height:14px;color:red;" href=""
												onclick="javascript:deleteEps(this,<s:property value="id"/>,1,'<s:property value="#epsst.index"/>');return false;">X</a>
											</span>
											<span id="e_erdel_<s:property value="#epsst.index"/>" >
												<s:if test="status==1"><a class="textbg4" href="javascript:;" onClick="recovery('<s:property value="id"/>','<s:property value="examRoom.id"/>','<s:property value="#epsst.index"/>');">恢复</a></s:if>
											</span>
											<span style="margin-left:280px;">
												<s:if test="sortid>1">
													<a class="textbg4" href="javascript:moveEroomep('<s:property value="id" />','<s:property value="sortid" />',1);">上移</a>
												</s:if>
												<s:if test="sortid<examRoom.exampapers.size">
													<a class="textbg4" href="javascript:moveEroomep('<s:property value="id" />','<s:property value="sortid" />',2);">下移</a>
												</s:if>
											</span>
									</div>
								</s:iterator>
							</div>
							<a href="" onClick="erep_addinit(); return false;" class="textbg5">添加试卷</a><!-- (<span style="color:red">注意：只有考场类型是选拔式的，练习添加才有效,其他情况不需设置练习部分</span>) -->
							<span style="color:red;">说明：被删除的试卷不可分配人员，已分配的学员不可再考，已考的分数不作数。</span>
						</td>
					</tr>
				<tr>
					<td align="right">
						<span class="neededitem">*</span>自动分配：
					</td>
					<td>
						<input type="radio" name="examRoom.autoAssign" value=1 
									 <s:if test="examRoom.autoAssign==1">
								 checked="checked"</s:if>
								/>
							是
						<input type="radio" name="examRoom.autoAssign" value=0 
							 <s:if test="examRoom.autoAssign==0">
						 		checked="checked"</s:if>
						 />
							否
					</td>
				</tr>
					<tr>
						<td width="130" height="50" align="center" >
							&nbsp;
							<s:hidden name="course.id" />
							<s:hidden name="examRoom.iscommon" value="0"></s:hidden>
							<s:hidden name="examRoom.valid" value="5"></s:hidden>
							<s:hidden name="examRoom.classid" value="0"></s:hidden>
							<s:hidden name="examRoom.depName" value=""></s:hidden>
							<s:hidden name="examRoom.jingzhong" value=""></s:hidden>
							<s:hidden name="examRoom.isApplication" value="2"></s:hidden>
							<input type="hidden" name="examRoom.cpid" value="<s:property value='pracPaper.cpage.id' />" />
							<s:hidden name="examRoom.id" />
							<s:hidden name="pracPaper.cpage.id" />
						</td>
						<td >
							<input type="submit" style="color: red;border: none;" name="button2" id="button2" class="textbg5" value="确认修改" />
						</td>
					</tr>
				</table>
			</s:form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>