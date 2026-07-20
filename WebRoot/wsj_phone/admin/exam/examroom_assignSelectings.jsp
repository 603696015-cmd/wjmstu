<%@
page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.courseman.entities.ExamRoom"%>
<%@page import="com.sopia.questionman.entities.ExamPaper"%>
<%@page import="com.sopia.courseman.entities.Course"%>
<%@page import="com.sopia.duman.entities.Department"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<TITLE>中国食品安全培训网--管理端--学员添加</TITLE> 
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<link rel="StyleSheet" href="js/tree/dtree.css" type="text/css" />
		<script type="text/javascript" src="js/tree/dtree.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
	<form name="ab_list" action="examroom_assignwc.action" method="post"> 
			<s:hidden name="examPaper.id" />
			<s:hidden name="examRoom.id" />
			<s:hidden name="department.id" />
	</form>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="考生增减" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">选拨分配人员</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<h3 style="width: 100%; text-align: center;">
			考场【
			  <s:property value="examRoom.title" />
			】中试卷【
			<s:property value="examPaper.title" />
			】的考生列表
		</h3>
		<s:hidden name="elUser.username" />
		<s:hidden name="elUser.email" />
		<s:hidden name="elUser.realname" />
		<s:hidden name="department.id" />

		<s:form action="examroom_assignSelectings.action" method="post" name="acc_list" theme="simple">
			<s:hidden name="pN" id="pageNow" />
			<s:hidden name="pS" />
			<s:hidden name="optype" />
			<s:hidden name="examPaper.id" />
			<s:hidden name="examRoom.id" />
			<s:hidden name="course.id" />  
			<table align="center" cellpadding="1" cellspacing="1" width="1000"
				>
				<tr>
					<td>
						<%-- 
						<wysLib:BasetName btid="4" />： 
								<s:select name="elUser.gangwei" cssClass="g-select"
										list="gangweis"  listKey="id" key="2"  listValue="basevalue"  headerValue="全部" headerKey="0" />
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
				<tr>
					<td> 
						姓名：<input name="elUser.realname"
							value="<s:property value="elUser.realname"/>"
							id="elUser.realname">
					</td>
					<td>
						账号：
						<input name="elUser.username"
							value="<s:property value="elUser.username"/>"
							id="elUser.username">
					</td>
					<td>
						生日开始时间:
						<input type="text" size="16"
							value="<s:date format="yyyy-MM-dd" name="elUser.shengri"/>"
							name="elUser.shengri" onclick="setday(this)">
					</td>
					<td>
						生日结束时间:
						<input type="text" size="16"
							value="<s:date format="yyyy-MM-dd" name="elUser.shengri_end"/>"
							name="elUser.shengri_end" onclick="setday(this)">
					</td> 
					<td>
						性别： 
						<select name="elUser.sex">
							<option value="" selected="selected">
								全部
							</option>
							<option value="男"
								<s:if test="elUser.sex==\"男\"">selected='selected'</s:if>>
								男
							</option>
							<option value="女"
								<s:if test="elUser.sex==\"女\"">selected='selected'</s:if>>
								女
							</option>
						</select>
					</td>
				</tr> 
				<tr>
					<td> 
						搜索包含下级部门： 
						<input type="checkbox" name="sub_department"	<s:if test="sub_department==1">checked="checked"</s:if>	id="sub_department" value="1"/>
						
					</td>
					<td>
					</td>
					<td>
					</td>
					<td>
					</td>
					<td> 
						<input id="find" name="find" type="submit" value="搜索">
					</td>
				</tr>
			</table>
			<%
				ExamRoom uu = ((ExamRoom) request.getAttribute("examRoom"));
				String erid = "0";
				erid = uu == null ? "0" : uu.getId() + "";
				ExamPaper uu1 = ((ExamPaper) request.getAttribute("examPaper"));
				String epid = "0";
				epid = uu1 == null ? "0" : uu1.getId() + "";
				Course c1 = ((Course) request.getAttribute("course"));
				String cid = "0";
				cid = c1 == null ? "0" : c1.getId() + "";
				String x = "examroom_assignSearchlist.action?sub_department=1&examPaper.id=" + epid
						+ "&examRoom.id=" + erid + "&course.id=" + cid
						+ "&department.id="; 
			%>
			<table align="center" cellpadding="1" cellspacing="1" width="1000"
				>
				<tr>
					<td valign="top" width="200">
						<wysLib:dep_list_aj rootAble="true"
							href="<%=x%>" iname="department.id"></wysLib:dep_list_aj>	
					</td>
					<td>
						<table align="center" cellpadding="1" cellspacing="1" width="800"
							>
							<tr>
								<th>
								</th>
								<th>
									学号
								</th>
								<th>
									姓名
								</th>
								<th>
									部门
								</th>
								<th>
									角色
								</th>
								<th>
									性别
								</th>
								<th>
									工种
								</th>
								<th>
									年龄
								</th>
								<th>
								</th>
							</tr>
							<s:iterator value="elusers">
								<tr>
									<td height="20" align="center">
										<input type="checkbox" name="canAssignUsers.id"
											value="<s:property value="id"/>" />
									</td>
									<td height="20" align="center">
										<s:property value="username" />
									</td>
									<td height="20" align="center">
										<s:property value="realname" />
									</td>
									<td height="20" align="center">
										<s:property value="department.name" />
									</td>
									<td height="20" align="center">
										<s:property value="role.name" />
									</td>
									<td height="20" align="center">
										<s:property value="sex" />
									</td>
									<td height="20" align="center">
										<s:property value="jingzhong_" />
									</td>
									<td height="20" align="center">
										<s:property value="age" />
									</td>
									<td height="20" align="center">
										<s:if test="!introom">
											未分配
										</s:if>
										<s:else>
											<font color="red"> 已分配</font>
										</s:else>
									</td>
								</tr>
							</s:iterator>
						</table>
					</td>
				</tr>
			</table>
		</s:form>
		<div style="margin-bottom: 20px; text-align: center;">
			<script type="text/javascript">
			 	function page(i){
			 		acc_list.action=  "examroom_assignSelectingsList.action";
			 		document.getElementById("pageNow").value=i;
			 		acc_list.submit();
			 	}
			 	function backSearch(){
			 		acc_list.action=  "examroom_assignwcSearchInit.action";
			 		acc_list.submit();
			 	}
			 	function assign2user(){
			 		if(confirm("确定添加这些人员？")){
				 		acc_list.action=  "examroom_assignwcSelectings.action";
				 		acc_list.submit();
			 		}
			 	}
			 	function unassign2user(){
			 		if(confirm("确定移除这些人员？")){
				 		acc_list.action=  "examroom_unassignwc.action";
				 		acc_list.submit();
			 		}
			 	}
			 	function assign2users(){
			 		if(confirm("确定分配所有人员？")){
				 		ab_list.action= "examroom_assignwcs.action";
				 		ab_list.submit();
			 		}
			 	}
			</script>
			<wysLib:page></wysLib:page>
			<script type="text/javascript">
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
					</script>
			<a href="javascript:select_All()" />全选</a>
			<a href="javascript:select_Fan()" />反选</a>
			<a href="javascript:select_Bux()" />全不选</a>
			<br>
			<input value="添加到当前考场中" type="button" onClick="assign2user()">
			<!--<input value="移除出当前考场" type="button" onclick="unassign2user()">
			<input value="重新搜索" type="button" onclick="backSearch()">
			<input value="返回考场试卷列表" type="button"
				onclick="document.location.href='examroom_assignwcInit.action?examRoom.id=<s:property value="examRoom.id"/>'">
			<input value="分配给全部搜索结果" type="button" onclick="assign2users()">
			-->
			<br>
		</div>
		<!-- 内容 -->

	
	</body>
</HTML>
