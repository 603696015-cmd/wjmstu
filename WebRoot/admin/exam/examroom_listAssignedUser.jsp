<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.courseman.entities.Course"%>
<%@page import="com.sopia.courseman.entities.ExamRoom"%>
<%@page import="com.sopia.questionman.entities.ExamPaper"%>
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
		<TITLE>分配学员</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>

		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<script type="text/javascript" src="js/tree/depuserlist.js"></script>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="考生分配(培训班)" /></div>
			</li>
			<!--<li>
				分配学员
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
			<s:if test="course.classid!=0">
			<div style="color:black;font-weight: bolder;font-size: 14px;">所属培训班：<s:property value="course.className" /></div>
			</s:if>
			<s:form action="examroom_assignSearchInit.action" method="post" theme="simple"
				name="assignSearch_assignment">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<s:hidden name="course.id" />
				<s:hidden name="examRoom.id" />
				<s:hidden name="examPaper.id" />
				<s:hidden name="deptid" />
				<s:hidden id="classid" name="course.classid" /> 
				<table width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<td>
						<%-- 
						<wysLib:BasetName btid="4" />： 
								<s:select name="elUser.gangwei" cssClass="g-select"
										list="gangweis" listKey="id" key="2" listValue="basevalue"  headerValue="全部" headerKey="0" />
						 --%> 
					</td>
					<td>
						<wysLib:BasetName btid="5" />：
								<s:select name="elUser.dishi" cssClass="g-select" list="dishis"
										listKey="id" listValue="basevalue" headerValue="全部" headerKey="0"/>
					</td>
					<td>
						<wysLib:BasetName btid="3" />： 
								<s:select name="elUser.zhiji" cssClass="g-select" list="zhijis"
										listKey="id" listValue="basevalue" headerValue="全部" headerKey="0"/> 
					</td>
					<td>
						<wysLib:BasetName btid="2" />： 
								<s:select name="elUser.zhiwu" cssClass="g-select" list="zhiwus"
										listKey="id" listValue="basevalue" headerValue="全部" headerKey="0"/> 
					</td>
					<td>
						<wysLib:BasetName btid="1" />： 
								<s:select name="elUser.jingzhong" cssClass="g-select"
										list="jingzhongs" listKey="id" listValue="basevalue" headerValue="全部" headerKey="0"/> 
					</td>
						<td> 
							性别：
							<select name="elUser.sex">
								<option value="" selected="selected">
									全部
								</option>
								<option value="男">
									男
								</option>
								<option value="女">
									女
								</option>
							</select>
						</td>
				</tr> 
					<tr>
						<td>
							培训班:
							<input name="cltype.name" id="cltype.name"> 
						</td>
						<td>
							姓名： 
							<input name="elUser.realname" id="elUser.realname">
						</td>
						<td>
							账号：
							<input name="elUser.username" id="elUser.username">
						</td>
						<td>
						</td>
						<td> 
						</td>
						<td> 
						</td>
					</tr>
					<tr>
						<td>
							年龄段开始时间:
						</td>
						<td>
							<input type="text" size="16" name="starttime"
								onclick="setday(this)">
						</td>
						<td>
							年龄段结束时间:
						</td>
						<td>
							<input type="text" size="16" name="endtime"
								onclick="setday(this)">
						</td>
						<td>
							是否已分配<s:if test="examRoom.uvalid==1 && examRoom.type == 1">/是否通过</s:if>
						</td>
						<td>
							<select name="elUser.isAssign">
								<option value="" selected="selected">
									全部
								</option>
								<option value="0">
									是
								</option>
								<option value="1">
									否
								</option>
							</select> 
							<s:hidden name="elUser.isQualified" />
							<s:if test="examRoom.uvalid==1 && examRoom.type == 1"> 
								<select name="elUser.isQualified">
									<option value="" selected="selected">
										全部
									</option>
									<option value="0">
										通过
									</option>
									<option value="1">
										未通过
									</option>
								</select>
							</s:if>
							<input id="find" name="find" type="submit" value="搜索"> 
						</td>
					</tr>
				</table>
			</s:form>
			<form action="examroom_assignSearchInit.action" method="post" name="course_assignment_selectClass">
				<s:hidden name="deptid" />
				<s:hidden name="course.id" />
				<s:hidden name="examRoom.id" />
				<s:hidden name="examPaper.id" />
				<s:hidden id="classid" name="course.classid" />
			</form>
			<form action="examroom_assignSearchInit.action" method="post" name="course_assignment">
				<s:hidden name="deptid" />
				<s:hidden name="course.id" />
				<s:hidden name="examRoom.id" />
				<s:hidden name="examPaper.id" />
				<s:hidden name="cltype.id" />
				<s:hidden name="elUser.sex" />
				<s:hidden name="elUser.realname" />
				<s:hidden name="elUser.username" />
				<s:hidden name="elUser.jingzhong" />
				<s:hidden name="starttime" />
				<s:hidden name="endtime" />
				<s:hidden name="elUser.isAssign" />
				<s:hidden name="elUser.isQualified" />
				<s:hidden name="course.classid" />
			<%-- 	<div>请选择该课程所对应的培训班：<s:select onchange="selectChange(this);" theme="simple" value="course.classid" headerKey="0" headerValue="无" name="course.classid" list="#request.classList" listKey="id" listValue="name"></s:select></div>  --%>
			</form>
			<script type="text/javascript">
				function selectChange(obj){
					var classid=obj.value;
					//alert(cc);
					document.getElementById("classid").value=classid;
					course_assignment_selectClass.submit();
				}
			</script>
			<table width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<td valign="top" width="150px;">
						<%
							Course course = (Course) request.getAttribute("course");
							ExamRoom examroom = (ExamRoom) request.getAttribute("examRoom");
							ExamPaper exampaper = (ExamPaper) request.getAttribute("examPaper");
							String url = "examroom_assignSearchInit.action?course.id="
									+ course.getId() + "&examRoom.id=" + examroom.getId()
									+ "&examPaper.id=" + exampaper.getId() + "&&course.classid="+examroom.getClassid()+"&deptid=";
						%>
						<wysLib:dep_list_aj rootAble="true" href="<%=url%>"></wysLib:dep_list_aj>
						<script type="text/javascript">
							w0.setValues([new DEP(<s:property value="department.id"/>,<s:property value="department.lid"/>,<s:property value="department.rid"/>)]);
						</script>
					</td>
					<td valign="top" align="left">
						<s:if test="elusers.size==0">当前还没有分配学员</s:if>
						<s:else>
							<table width="90%" align="center" cellpadding="1" cellspacing="1"
								bgcolor="#EBEBEB">
								<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
									</td>
									<td height="30" align="center" >
										姓名
									</td>
									<td height="30" align="center" >
										性别
									</td>
									<td height="30" align="center" >
										账号
									</td>
									<td height="30" align="center" >
										部门
									</td>
									<td height="30" align="center" >
										<wysLib:BasetName btid="1" />
									</td>

									<td height="30" align="center" >
										年龄
									</td>
									<td height="30" align="center" >
										角色
									</td>
									<td height="30" align="center" >
										分配
									</td>
									<s:if test="examRoom.uvalid==1 && examRoom.type == 1">
									<td height="30" align="center" >
										考生状态
									</td>
									</s:if>
								</tr>
								<s:if test="elusers.size==0">
									<TR>
										<TD align="center" colspan="4">
											当前还没有分配学员
										</TD>
									</TR>
								</s:if>
								<s:else>
									<s:iterator value="elusers">
										<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
												<input type="checkbox" value="<s:property value="id"/>"
													name="id">
											</td>
											<td height="30" align="center" >
												<s:property value="realname" />
											</td>
											<td height="30" align="center" >
												<s:property value="sex" />
											</td>
											<td height="30" align="center" >
												<s:property value="username" />
											</td>
											<td height="30" align="center" >
												<s:property value="department.name" />
											</td>
											<td height="30" align="center" >
												<s:property value="jingzhong_" />
											</td>
											<td height="30" align="center" >
												<s:property value="age" />	
											</td>
											<td height="30" align="center" >
												<s:property value="role.name" />
											</td>
											<td height="30" align="center" >
												<s:if test="isAssign == '已分配'">									
													<span style="color:red"><s:property value="isAssign" /></span>
												</s:if><s:else>
													<s:property value="isAssign" />
												</s:else>
											</td> 
											<s:if test="examRoom.uvalid==1 && examRoom.type == 1">
											<td height="30" align="center" > 
												<s:if test="0 != examPaper.practimes && 0.0 !=examPaper.pracscore">
													<s:if test="practimes >= examPaper.practimes && pracscore >=examPaper.pracscore">
														<span style="color:red">通过</span>
													</s:if><s:else>
														未通过
													</s:else> 
												</s:if>	<s:else>
														无规则
													</s:else> 
											</td>
											</s:if>
										</tr>
									</s:iterator>
								</s:else>
							</table>
						</s:else>
					</td>
				</tr>
			</table>
			<wysLib:page></wysLib:page>
			<script type="text/javascript">
						function select_All(){
							var cks= document.getElementsByName("id");
							for(var i = 0 ; i < cks.length; i++){
								cks[i].checked= true;
							}
						}
						function select_Fan(){
							var cks= document.getElementsByName("id");
							for(var i = 0 ; i < cks.length; i++){
								cks[i].checked= !cks[i].checked;
							}
						}
						function select_Bux(){
							var cks= document.getElementsByName("id");
							for(var i = 0 ; i < cks.length; i++){
								cks[i].checked= false;
							}
						}
						function assign(){
						 if(window.confirm("确定分配？！")){
							var checkObj = document.getElementsByName("id");
						    var billIDs = "";
						    for (i = 0; i < checkObj.length; i++) {
								if (checkObj[i].checked) {
								    if(billIDs!="")billIDs+=",";
									billIDs += checkObj[i].value;
								}
							 }
							if(billIDs==""){
							  alert("请选择要分配的记录！");
							  return ;
						    }
						    alert(billIDs);
							course_assignment.action="examroom_assignuser.action?userids="+billIDs;
							course_assignment.submit();
							}
						}
						function unassign(){
						  if(window.confirm("确定取消分配？！")){
						     var checkObj = document.getElementsByName("id");
							    var billIDs = "";
							    for (i = 0; i < checkObj.length; i++) {
									if (checkObj[i].checked) {
									    if(billIDs!="")billIDs+=",";
										billIDs += checkObj[i].value;
									}
								 }
								if(billIDs==""){
								  alert("请选择要取消分配的记录！");
								  return ;
							    }
							  course_assignment.action="examroom_unassignuser.action?userids="+billIDs;
							  course_assignment.submit();
							}
						}
						  function assignSearch(){
							course_assignment.action="examroom_assignSearchAll.action";
							course_assignment.submit();
						  }
					</script>
			<a href="javascript:select_Fan()" class="textbg4">反选</a>
			<a href="javascript:select_All()" class="textbg4">全选</a>
			<a href="javascript:select_Bux()" class="textbg4" style="width: 60px;">全不选</a>
			<s:if test="examRoom.valid != 1">
			<br/>
				<input style="color: red;border: none;" class="textbg" value="添加到考场" type="button" onClick="assign()">
				<input style="color: red;border: none;" class="textbg" value="移出考场" type="button" onClick="unassign()">
				<input style="color: red;border: none;" class="textbg" value="分配给全部" type="button" onClick="assignSearch()">
				<input class="textbg" style="border: none;" type="button"
					value="返回试卷列表"
					onclick="document.location='examroom_assignwcInit.action?examRoom.id=${examRoom.id}&course.id=${course.id }&course.classid=${course.classid}&Return=elclass_alterInit'" />	
				<input class="textbg" style="border: none;" type="button"
					value="返回绑定列表"
					onclick="document.location='examroom_choose_listbycInit.action?course.id=${course.id }&classId=${course.classid}&Return=elclass_alterInit'" />	
			</s:if>
			<s:else>
					<br/>
					<span style="color:red;">已审核</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="examroom_audit.action?examRoom.id=<s:property value="examRoom.id"/>" class="textbg6">申请修改</a> 
			</s:else>
			<br>
		</div> 
		<!-- 内容 -->
	</BODY>
	<script>
				function page(i) {
					document.getElementById("pageNow").value=i;
					assignSearch_assignment.submit();
				}
			</script>
			<SCRIPT type="text/javascript">
				if("${elmessage}"!='null'&&"${elmessage}"!='')
				 alert("${elmessage}!");
				 return;
		</SCRIPT>
	<form action="examroom_assignSearchInit.action" method="post"
		name="acc_list">
		<s:hidden name="pN" id="pageNow" />
		<s:hidden name="pS" />
		<s:hidden name="deptid" />
		<s:hidden name="course.id" />
		<s:hidden name="examRoom.id" />
		<s:hidden name="examPaper.id" />
		<s:hidden name="cltype.id" />
		<s:hidden name="elUser.sex" />
		<s:hidden name="elUser.realname" />
		<s:hidden name="elUser.username" />
		<s:hidden name="elUser.jingzhong" />
		<s:hidden name="starttime" />
		<s:hidden name="endtime" />
		<s:hidden name="elUser.isAssign" />
		<s:hidden name="elUser.isQualified" />
		<s:hidden name="course.classid" />
	</form>
</HTML>
