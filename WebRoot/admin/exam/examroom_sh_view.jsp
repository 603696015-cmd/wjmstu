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
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
</style>
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
				if($("#epid").val()==''){
					alert("请选择试卷");
					$("#epid").focus();
					return false; 
				}
				
				return true;
			}
		</script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="详情概览" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">审核考场查看</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<span style="color: #ff0000;"></span>
			<table width="90%" align="center" cellpadding="1" cellspacing="1"
				bgcolor="#EBEBEB">
				<tr>
					<td width="160" height="30" align="center">
						考场标题
					</td>
					<td>
						<label>
							<s:property value="examRoom.title" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="160" align="center">
						所属试卷库
					</td>
					<td bgcolor="#FFFFFF" colspan="3">
						<label>
							${examRoom.eroomLib.name}

						</label>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center">
						考场说明
					</td>
					<td>
						<label>
							<s:property value="examRoom.description" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center">
						考试地点
					</td>
					<td>
						<label>
							<s:property value="examRoom.location" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center">
						监考人员
					</td>
					<td>
						<div id="invigilators">
							<s:iterator value="examRoom.invigilators">
								<span
									style="width: 110px; height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
									<label style="width: 80px; float: left;">
										<s:property value="realname" />
									</label> </span>
							</s:iterator>
						</div>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center">
						阅卷人员
					</td>
					<td>
						<div id="appraises">
							<s:iterator value="examRoom.appraises">
								<span
									style="width: 110px; height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
									<label style="width: 80px; float: left;">
										<s:property value="realname" />
									</label> </span>
							</s:iterator>
						</div>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center">
						复核人员
					</td>
					<td>
						<div id="valids">
							<s:iterator value="examRoom.valids">
								<span
									style="width: 110px; height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
									<label style="width: 80px; float: left;">
										<s:property value="realname" />
									</label> </span>
							</s:iterator>
						</div>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center">
						考试时间
					</td>
					<td>
						<label>
							考场开始时间
							<s:date name="examRoom.begintime" format="yyyy-MM-dd HH:mm:ss" />
						</label>
						<br />
						<label>
							考场结束时间
							<s:date name="examRoom.endtime" format="yyyy-MM-dd HH:mm:ss" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center">
						考场类型
					</td>
					<td>
						<label>
							<s:property value="examRoom.typeName" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center">
						绑定mac地址：
					</td>
					<td>
						<s:if test="examRoom.isMacBand==1">
										是
									</s:if>
						<s:if test="examRoom.isMacBand==0">
										否
									</s:if>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center">
						限&nbsp;定&nbsp;ip&nbsp;段：
					</td>
					<td>
						<s:if test="examRoom.isIpLimit==1">
										是
									</s:if>
						<s:if test="examRoom.isIpLimit==0">
										否
									</s:if>
					</td>
				</tr>
				<s:if test="examRoom.isIpLimit==1">
					<tr>
						<td colspan="2">
							<div>
								<div style="float: left">
									<s:iterator id="ipStrat" value="#request.ipStratList"
										status="statu">
										<div>
											开&nbsp;始&nbsp;ip：
											<s:property value='ipStrat' />
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</div>
									</s:iterator>
								</div>
								<div>
									<s:iterator id="ipEnd" value="#request.ipEndList"
										status="statu">
										<div>
											结&nbsp;束&nbsp;ip：
											<s:property value='ipEnd' />
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</div>
									</s:iterator>
								</div>
							</div>
						</td>
					</tr>
				</s:if>
				<tr>
					<td width="160" height="30" align="center">
						通过成绩：
					</td>
					<td>
						<label>
							<s:property value="examRoom.passgrade" />
							%
						</label>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center">
						学分
					</td>
					<td>
						<label>
							<s:property value="examRoom.score" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center">
						所用试卷
					</td>
					<td>
						<div id="eps_div">
							<s:iterator value="examRoom.exampapers" status="epsst">
								<div>
									<span style="width: 250"> <s:property value="title" />
									</span>
									<span style=""><s:if test="shouldpass==1">需达标</s:if>
										<s:if test="shouldpass==0">无需达标</s:if> </span>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<a target="_blank" target="top"
										href="exampaper_details.action?examPaper.id=<s:property value='id' />"
										class=textbg4>预 览</a> 达标线：
									<b><s:property value="passgrade" /></b>
									%&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;可考次数：
									<b><s:property value="quizcount" /></b>
									成绩取：
									<b><s:if test="passmanner==1"> 平均分</s:if> 
									<s:if test="passmanner==0">最高分 </s:if></b>
									答卷可看：
									<b><s:if test="quizlook==1">是</s:if>
									<s:if test="quizlook==0">否</s:if></b>
									成绩可看：
									<b><s:if test="scorelook==1">是</s:if>
									<s:if test="scorelook==0">否</s:if></b>
									<%-- 
									<lable name="prac">	
									<span style='color: red'>练习</span>：
									<span style="width: 150px;"><s:property
											value="prac.title" /> </span> 次数：
									<s:property value="practimes" />
									最低分
									<s:property value="pracscore" />
									</lable>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp;
									<s:if test="prac.id!=0">
												<a target="_blank" target="top"
													href="exampaper_details.action?examPaper.id=<s:property value='prac.id' />" class=textbg4>预 览</a>	
									</s:if>
									 --%>
								</div>
							</s:iterator>
						</div>
					</td>
				</tr>
				<s:if test="examRoom.type==1">
					<tr>
						<td width="160" height="30" align="center">
							选拔人员
						</td>
						<td>
							<div id="selectings">
								<s:iterator value="examRoom.selectings">
									<span
										style="width: 110px; height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
										<label style="width: 80px; float: left;">
											<s:property value="realname" />
										</label> </span>
								</s:iterator>
							</div>
						</td>
					</tr>
				</s:if>
				<tr>
					<td width="160" height="50" align="center">
						考生列表
					</td>
					<td>
						<s:if test="myrooms.size==0">
							<br>
						暂无考生
						</s:if>
						<s:else>
							<table width="96%" align="center" cellspacing="1" cellpadding="1">
								<caption>
									考生列表
								</caption>
								<tr>
									<td height="30" style="padding-left: 8px; color: blue;"
										align="left">
										姓名/账号
									</td>
									<td height="30" align="center">
										部门
									</td>
									<td height="30" align="center">
										试卷数量
									</td>
								</tr>
								<s:iterator value="myrooms">
									<tr>
										<td height="30" style="padding-left: 8px; color: blue;"
											align="left">
											<s:property value="tester.realname" />
											/
											<s:property value="tester.username" />
										</td>
										<td height="30" align="center">
											<s:property value="tester.department.name" />
										</td>
										<td height="30" align="center">
											<s:property value="epsize" />
										</td>
									</tr>
								</s:iterator>
							</table>
							<wysLib:page></wysLib:page>
						</s:else>
					</td>
				</tr>
				<tr>
					<td width="160" align="center">
						人员审查状态
					</td>
					<td>
						<font color="<s:if test="examRoom.uvalid==1">red</s:if>"><s:property
								value="examRoom.uvalidName" /> </font>

					</td>
				</tr>
				<s:if test="examRoom.isApplication == 1">
					<tr>
						<td height="30" align="center" bgcolor="#E6F9F9">
							是否可申请：
						</td>
						<td height="30">
							<table width="95%" cellpadding="2" cellspacing="1">
								<tr>
									<td width="100" align="center" bgcolor="#E6F9F9">
										计划招收学员:
									</td>
									<td>
										<s:property value="erRegistration.PlanRecruitStudents"
											 />
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<span style="color: red">申请条件</span>
									</td>
								</tr>
								<tr>
									<td width="100" align="center" bgcolor="#E6F9F9">
										报名时间段:
									</td>
									<td>
										<s:date name="erRegistration.RegistrationStartTime"
											format="yyyy-MM-dd HH:mm:ss" />
										～
										<s:date name="erRegistration.RegistrationStopTime"
											format="yyyy-MM-dd HH:mm:ss" />
									</td>
								</tr>
								<tr>
									<td width="100" align="center" bgcolor="#E6F9F9">
										年龄段:
									</td>
									<td>
										<s:property value="erRegistration.StartAge" />
										～
										<s:property value="erRegistration.StopAge" />
									</td>
								</tr>
								<tr>
									<td width="100" align="center" bgcolor="#E6F9F9">
										性别:
									</td>
									<td>
										<s:property value="erRegistration.sex" />
									</td>
								</tr>
								<tr>
									<td width="100" align="center" bgcolor="#E6F9F9">
										部门:
									</td>
									<td>
										<s:property value="erRegistration.treeTypeName" />
									</td>
								</tr>
								<tr>
									<td width="100" align="center" bgcolor="#E6F9F9">
										<wysLib:BasetName btid="1" />
										:
									</td>
									<td>
										<s:property value="erRegistration.jingzhongName" />
									</td>
								</tr>
								<tr>
									<td width="100" align="center" bgcolor="#E6F9F9">
										<wysLib:BasetName btid="5" />
										:
									</td>
									<td>
										<s:property value="erRegistration.dishiName" />
									</td>
								</tr>
								<tr>
									<td width="100" align="center" bgcolor="#E6F9F9">
										<wysLib:BasetName btid="2" />
										:
									</td>
									<td>
										<s:property value="erRegistration.zhiwuName" />
									</td>
								</tr>
								<tr>
									<td width="100" align="center" bgcolor="#E6F9F9">
										<wysLib:BasetName btid="3" />
										:
									</td>
									<td>
										<s:property value="erRegistration.zhijiName" />
									</td>
								</tr>
								<%-- 
								<tr>
									<td width="100" align="center" bgcolor="#E6F9F9">
										<wysLib:BasetName btid="4" />:
									</td>
									<td>
											<s:property value="erRegistration.gangweiName"/> 
									</td>
								</tr>
								 --%>
								<tr>
									<td width="100" align="center" bgcolor="#E6F9F9">
										考场:
									</td>
									<td>
										<SELECT name="erRegistration.eroomScreeningWay">
											<option value="0"
												<s:if test="erRegistration.eroomScreeningWay == 0 ">selected="selected"</s:if>>
												全部
											</option>
											<option value="1"
												<s:if test="erRegistration.eroomScreeningWay == 1 ">selected="selected"</s:if>>
												通过
											</option>
											<option value="2"
												<s:if test="erRegistration.eroomScreeningWay == 2 ">selected="selected"</s:if>>
												不通过
											</option>
										</SELECT>
										<s:property value="erRegistration.examroomNam" />
									</td>
								</tr>
								<tr>
									<td width="100" align="center" bgcolor="#E6F9F9">
										培训班:
									</td>
									<td>
										<SELECT name="erRegistration.classScreeningWay">
											<option value="0"
												<s:if test="erRegistration.classScreeningWay == 0 ">selected="selected"</s:if>>
												全部
											</option>
											<option value="1"
												<s:if test="erRegistration.classScreeningWay == 1 ">selected="selected"</s:if>>
												通过
											</option>
											<option value="2"
												<s:if test="erRegistration.classScreeningWay == 2 ">selected="selected"</s:if>>
												不通过
											</option>
										</SELECT>
										<s:property value="erRegistration.elclassName" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</s:if>
				<tr>
					<td width="160" height="50" align="center">
						&nbsp;
						<s:hidden name="optype"></s:hidden>
						<s:hidden name="course.id"></s:hidden>
						<s:hidden name="examRoom.id"></s:hidden>
						<script type="text/javascript">
								function sh_p(){
									if(window.confirm("确定申请审核？"))
										document.location.href="examroom_sh_p.action?examRoom.id=<s:property value="examRoom.id"/>&examRoom.valid=1"
								}  
								function page(i){
										document.location.href="examroom_sh_view.action?examRoom.id=<s:property value="examRoom.id"/>&pS=<s:property value="pS"/>&pN="+i
								}
							</script>
					</td>
					<td>
					</td>
				</tr>
			</table>
			<br />
			<s:if test="Return=='alter'">
			<a href="examroom_alter_list.action" class="textbg4" style="width:120px">返回修改申请列表</a>
			</s:if>
			<s:if test="Return=='del'">
			<a href="examroom_application_delete.action" class="textbg4" style="width:120px">返回删除申请列表</a>
			</s:if>
			<br />
			<br />
			<br />
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
