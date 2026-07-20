<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.duman.entities.Department"%>
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
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript">
		 	function page(i){
		 		document.getElementById("pageNow").value=i;
		 		search_list.submit();
		 	}
		</script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="学员列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">用户统计</span>
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
			<table width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<td valign="top" width="120" id="tree_list_td">
						<%
							Department dep = (Department) request
										.getAttribute("department");
								String depid = dep.getId() + "";
						%>
						<wysLib:dep_list_aj rootAble="true"
							href="user_searchlist.action?sub_department=1&department.id="
							iname="department.idd" ivalue="<%=depid%>"></wysLib:dep_list_aj>
						<script type="text/javascript">
							w0.setValues([new DEP(<s:property value="department.id"/>,<s:property value="department.lid"/>,<s:property value="department.rid"/>)]);
						</script>
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
					</td>
					<td valign="top">
						<s:form action="user_searchlist" method="post" name="search_list" theme="simple">
							<s:hidden name="pN" id="pageNow" />
							<s:hidden name="pS" />
							<s:hidden name="department.id" />
							<div style="text-align: center;">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 用户名：
								<s:textfield name="elUser.username" />
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 姓名：
								<s:textfield name="elUser.realname" />
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 包含下属部门：
								<label>
									<input type="checkbox" name="sub_department"
										<s:if test="sub_department==1">checked="checked"</s:if>
										id="sub_department" value="1">
								</label>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input class="textbg4"
									onclick="document.getElementById('pageNow').value=0;"
									type="submit" value="搜 索">
							</div>
						</s:form>
						<table width="100%" align="center" cellpadding="1" cellspacing="1">
							<tr>
								<th align="center" >
									排名								</th>
								<th align="center" >
									学号								</th>
								<th align="center" >
									姓名								</th>
								<th align="center" >
									部门								</th>
								<!-- <th align="center" >
									当前学分
								</th> -->
								<%-- 
								<th align="center" >
									基本信息
								</th>
								<th colspan=2 align="center" >
									课程数量
								</th>
								<th colspan=2 align="center" >
									选班数量
								</th>
								<th colspan=1 align="center" >
									学习轨迹
								</th>
								 --%>
								<th align="center" >
									学习概况								</th>
								<th align="center" >课程数量 </th>
								<th align="center" >学习轨迹</th>
								<!-- <th align="center" >
									开课情况
								</th>
								<th align="center" >
									开班情况
								</th>
								<th align="center" >
									考试通过率
								</th> -->
								<!--<th align="center" >
									考试统计
								</th>-->
							</tr>
							<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
							<s:iterator value="elUsers" status="st">
								<tr>
									<td height="30" align="center" >
										<s:property value="#st.index+1" />					  </td>
									<td align="center" >
										<s:property value="username" />									</td>
									<td align="center" >
										<s:property value="realname" />									</td>
									<td align="center" >
										<s:property value="department.name" />									</td>
									<!-- <td  align="center" >
										<s:property value="xx_credit" />
									</td> -->
									<%-- 
									<td align="center" >
										<!--<a href="user_view.action?elUser.id=<s:property value="id"/>">查看</a>
									-->
									
									<a href="account_view.action?elUser.id=<s:property value="id"/>" class="textbg4">查看</a>
									</td>
									<td  align="center" >
										<s:property value="courseSize" />
									</td>
									<td align="center" >
										<a href="user_course.action?elUser.id=<s:property value="id"/>" class="textbg4">查看</a>
									</td>
									<td  align="center" >
										<s:property value="classSize" />
									</td>
									<td align="center" >
										<a href="user_class.action?elUser.id=<s:property value="id"/>" class="textbg4">查看</a>
									</td>
									<td align="center" >
										<a href="statisticStudyLearnLocus.action?elUser.id=<s:property value="id"/>" class="textbg4">查看</a>
									</td>
									 --%>
									<td align="center" >
										<a href="studyOverviewInfo.action?elUser.id=<s:property value="id"/>" class="textbg4">查看</a>									</td>
									<td align="center" ><a href="user_course.action?elUser.id=<s:property value="id"/>" class="textbg4">查看</a></td>
									<td align="center" ><a href="statisticStudyLearnLocus.action?elUser.id=<s:property value="id"/>" class="textbg4">查看</a></td>
									<!--<td  align="center" >
										<a
											href="user_course_create.action?elUser.id=<s:property value="id"/>">查看</a>
									</td>
									<td align="center" >
										<a href="user_class_create.action?elUser.id=<s:property value="id"/>">查看</a>
									</td>
								 <td align="center" >
										 <s:property value="quizpassedper" />%
									</td> -->
									<!--<td align="center" >
										<a
											href="user_quizresult.action?elUser.id=<s:property value="id"/>">查看</a>
									</td>-->
								</tr>
							</s:iterator> </tbody>
					  </table>
				  </td>
				</tr>
			</table>
			<br>
			<wysLib:page></wysLib:page>
			
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
