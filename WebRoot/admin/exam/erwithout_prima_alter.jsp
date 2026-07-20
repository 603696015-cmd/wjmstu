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
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter  
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/assist.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/libutil.js"></script>
		<script type="text/javascript">
		function deleteUserinfo(obj,id,optype){
			if(window.confirm("确定删除？")){
			erid = <s:property value="examRoom.id"/> ;
			$.post("eroom_delete_user.action", {
				"elUser.id":id,
				"examRoom.id":erid,
				"optype":optype, 
				"x":Math.random
				}, 
				function (data) {
					alert('删除成功');
				});
			obj.parentNode.parentNode.removeChild(obj.parentNode);
			}
		}
		function deleteEps(obj,id ){
			if(window.confirm("确定删除？")){
			erid = <s:property value="examRoom.id"/> ;
			$.post("eroom_delete_ep.action", {
				"examPaper.id":id,
				"examRoom.id":erid,
				"x":Math.random
				}, 
				function (data) {
					alert('删除成功');
				});
			obj.parentNode.parentNode.removeChild(obj.parentNode);
			}
		}
		var epcount=0;
			function erep_addinit(){
				var eps =  document.createElement("div");
				eps.id ="eps_"+epcount;
				epsStr = "<span style='color:red'>试卷：</span><span style=\"width:150px;\" id=\"eptitle"+epcount+"\"></span>"+
				" <input type=\"hidden\" id=\"epid"+epcount+"\" name=\"examPapers["+epcount+"].id\" value=\"\"/>"+
				" <a href=\"javascript:erep_add("+epcount+");\">选择试卷</a>达标线：<input type='text' size='3' value='60' name=\"examPapers["+epcount+"].passgrade\">"+
				"可查看答卷<input type='checkbox' name='examPapers["+epcount+"].stuview' value='1' />"+
				"<lable name=\"prac\">"+
				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style='color:red'>练习</span>：<span style=\"width: 150px;\" id=\"eptitle_lx_"+epcount+"\"></span>"+
				"<input type=\"hidden\" id=\"epid_lx_"+epcount+"\" name=\"examPapers["+epcount+"].prac.id\" />"+
				"<a href='#' onclick=\"javascript:erep_add('_lx_"+epcount+"');return false;\">选择练习</a> "+
				"次数：<input type=\"text\" style=\"width: 40px;\" name=\"examPapers["+epcount+"].practimes\" value=\"0\">"+
				"最低分：<input type=\"text\" style=\"width: 40px;\" name=\"examPapers["+epcount+"].pracscore\" value=\"0\">"+
				"</lable>"+
				"<a style=\"cursor: pointer; width: 14px; height: 14px;\" onclick=\"javascript:erep_del(this,"+epcount+" );return false;\">X</a>" ;
				eps.innerHTML= epsStr;
				epcount++;	//href=\"\"
				document.getElementById("eps_div").appendChild(eps);		
			}
			function erep_del(obj,id ){
				if(window.confirm("确定删除？")){
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
				 }
			}
			function _onsubmit(){
				if($("#eroom_title").val()==''){
					alert("标题不要为空");
					$("#eroom_title").focus();
					return false; 
				}if($("#eroom_begintime").val()==''){
					alert("开始时间不要为空");
					$("#eroom_begintime").focus();
					return false; 
				}if($("#eroom_endtime").val()==''){
					alert("结束时间不要为空");
					$("#eroom_endtime").focus();
					return false; 
				}
				//$("#id").children()是 jquery 选择id为 id的下级元素集合，.length 就是 元素的数量。
				if($("#invigilators").children().length==0){
					alert("监考员 不能为空，请选择！");
					return false;
				}
				if($("#epid").val()==''){
					alert("请选择试卷");
					$("#epid").focus();
					return false; 
				}
				
				return true;
			}
		</script>
		<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="详情概览" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">编辑考场</span>
			</li>
			<s:if test="optype!='valid'">
				<!--<li class="sep">
				</li>
				<li>
					 <a style="cursor: hand"
						onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
						onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
						href="examroomwithoutcourse_list.action">一般考试管理</a> 
				</li>-->
			</s:if>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<label style="font-size: 16px;">
				一般考试管理
			</label>
			<br>
			<s:form theme="simple" id="form_exam_add" name="form_exam_add"
				method="post" action="erwithout_prima_alter"
				onsubmit="return _onsubmit();">
				<span style="color: #ff0000;"></span>
				<table width="950" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr>
						<td width="130" height="30" align="center" >
							考场标题
						</td>
						<td >
							<label>
								<s:textfield name="examRoom.title" size="60" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="130" align="center" >
							所属考场库
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
						<td width="130" height="30" align="center" >
							考场说明
						</td>
						<td >
							<label>
								<s:textarea name="examRoom.description" cols="60" rows="7" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="130" height="30" align="center" >
							考试地点
						</td>
						<td >
							<label>
								<s:textfield name="examRoom.location" size="60" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="130" height="30" align="center" >
							监考人员
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
						<td width="130" height="30" align="center" >
							阅卷人员
						</td>
						<td >
							<div id="appraises">
								<s:iterator value="examRoom.appraises">
									<span
										style="width: 110px; height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
										<label style="width: 80px; float: left;">
											<s:property value="realname" />
										</label> <a
										style="cursor: hand; float: right; width: 14px; height: 14px;"
										href=""
										onclick="javascript:deleteUserinfo(this,<s:property value="id"/>,'rappraises');return false;">X</a>
									</span>
								</s:iterator>
							</div>
							<a href=""
								onclick="searchUserInit('appraises','examRoom.appraises.id'); return false;" class="textbg4">添加</a>
						</td>
					</tr>
					<tr>
						<td width="130" height="30" align="center" >
							复核人员
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
						<td width="130" height="30" align="center" >
							考试时间
						</td>
						<td >
							<label>
								考场开始时间
								<input style="height:35px;" class="Wdate" name="examRoom.begintime" type="text"
									onclick="setday(this)"
									value="<s:date name="examRoom.begintime" format="yyyy-MM-dd HH:mm:ss"/>"
									id="eroom_begintime" />
							</label>
							<br />
							<label>
								考场结束时间
								<input style="height:35px;" class="Wdate" name="examRoom.endtime" id="eroom_endtime"
									type="text" onClick="setday(this)"
									value="<s:date name="examRoom.endtime" format="yyyy-MM-dd HH:mm:ss"/>" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="130" height="30" align="center" >
							考场类型
						</td>
						<td >
							<label>
								<input type="radio"
									<s:if test="examRoom.type==0">checked="checked"</s:if>
									name="examRoom.type" value="0">分配式
								<input type="radio"
									<s:if test="examRoom.type==1">checked="checked"</s:if>
									name="examRoom.type" value="1">选拔式
								<input type="radio"
									<s:if test="examRoom.type==2">checked="checked"</s:if>
									name="examRoom.type" value="2">	可申请
							</label>

						</td>
					</tr>
					<tr>
						<td width="130" height="30" align="center" >
							通过成绩：
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
						<td width="130" height="30" align="center" >
							学分
						</td>
						<td >
							<label>
								<input type="text" style="width: 40px;"
									value="<s:property value="examRoom.score"/>"
									name="examRoom.score">
							</label>
						</td>
					</tr>
					<tr>
						<td width="130" height="30" align="center" >
							所用试卷
						</td>
						<td >
							<div id="eps_div">
								<s:iterator value="examRoom.exampapers" status="epsst">
									<div id="eps_<s:property value="#epsst.index"/>">
										<span style='color:red'>试卷：</span><span style="width:150px;"
											id="eptitle__<s:property value="#epsst.index"/>"><s:property
												value="title" /> </span>
										<input type="hidden"
											id="epid__<s:property value="#epsst.index"/>"
											name="examPapers.id" value="<s:property value="id"/>" />
										<a
											href="javascript:erep_add('__<s:property value="#epsst.index"/>');" class="textbg6">选择试卷</a>达标线：<input type='text' size="3" value='<s:property value="passgrade"/>'
											name="examPapers[<s:property value="#epsst.index"/>].passgrade">可查看答卷<input type="checkbox" name="examPapers[<s:property value="#epsst.index"/>].stuview" <s:if test="stuview==1">checked='checked'</s:if> value="1" />
										<lable name="prac"></br>

<span style="color:red;">练习</span>：<span style="width: 150px;"
											id="eptitle_lx_<s:property value="#epsst.index"/>"><s:property
												value="prac.title" />
										</span>
										<input type="hidden"
											id="epid_lx_<s:property value="#epsst.index"/>"
											name="examPapers[<s:property value="#epsst.index"/>].prac.id" value="<s:property value="prac.id"/>"/>
										<a href='#'
											onclick="javascript:erep_add('_lx_<s:property value="#epsst.index"/>');return false;" class="textbg6" >选择练习</a>
										次数：<input type="text" style="width: 40px;"
											name="examPapers[<s:property value="#epsst.index"/>].practimes"
											value="<s:property value="practimes"/>">
										最低分：<input type="text" style="width: 40px;"
											name="examPapers[<s:property value="#epsst.index"/>].pracscore"
											value="<s:property value="pracscore"/>">
										</lable>
										<a style="cursor: pointer; width: 14px; height: 14px;" href=""
											onclick="javascript:deleteEps(this,<s:property value="id"/>);return false;">X</a>
										<script type="text/javascript">epcount++;</script>
									</div>
								</s:iterator>
							</div>
							<a href="" onClick="erep_addinit(); return false;" class="textbg">添加试卷</a>(<span style="color:red">注意：只有考场类型是选拔式的，练习添加才有效,其他情况不需设置练习部分</span>)
						</td>
					</tr>
					<tr>
						<td width="130" height="50" align="center" >
							&nbsp;
							<s:hidden name="optype"></s:hidden>
							<s:hidden name="course.id"></s:hidden>
							<s:hidden name="examRoom.valid"></s:hidden>
							<s:hidden name="examRoom.id"></s:hidden>
						</td>
						<td > 
							<s:if test="examRoom.valid == 1">
								<s:hidden name="examRoom.avalid" value="1"></s:hidden>
								<span style="color:red;">考场已开通,不能再修改考场信息! 如果需要修改。 请申请！</span><br>
								<input type="button" name="" onClick="RoomAlert()" value="申请修改信息">
							</s:if>
							<s:else>
								<s:if test="examRoom.avalid == 1">
									<span style="color:red">正在修改申请审核中, 请耐心等待！</span>
								</s:if><s:else>
								<input type="submit" name="button2" id="button2" value="确认修改" />
								</s:else>
							</s:else>
						</td>
					</tr>
				</table>
			</s:form>
			<SCRIPT type="text/javascript">
				function  RoomAlert(){
					if(window.confirm("确定申请修改吗？")){ 
						form_exam_add.action="examroom_audit.action";
						form_exam_add.submit();
					}
				}
			</SCRIPT> 
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
