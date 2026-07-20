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
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="考场列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">所有考试考场列表 </span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;"> 
			<s:if test="examRooms.size==0">
						没有考场。
			</s:if>
			<s:else>
			<table width="1050px" cellpadding="1" cellspacing="1">
					<tr>
						<td width="120" valign="top" id="tree_list_td">  
							<wysLib:eroomLibTree
								href="examroom_myalllist.action?eroomLib.id=" rootAble="true"></wysLib:eroomLibTree>					  </td>
						<td valign="middle" width="5px;" style="padding: 0px">
							<img src="images/leftmenu/main_55.gif" style="cursor: hand" onClick="changeTreeDisplay(this)" />
						</td>
						<td>
						<table width="100%" align="center" cellspacing="1 " cellpadding="1">
						<tr>
							<th width="200" height="30" align="center" >
								考场名称							</th>
							<th width="60" height="30" align="center" >
								创建者
							</th>
							<th width="120" height="30" align="center" >
								课程						</th>
							<th width="100" height="30" align="center" >
								监考老师							</th>
							<th width="130" height="30" align="center" >
								开始时间							</th>
							<th width="130" height="30" align="center" >
								结束时间							</th>
							<th width="120" height="30" align="center" >&nbsp;							</th>
						</tr>
						<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
						<s:iterator value="examRooms">
							<tr>
								<td height="30" align="center" bgcolor="#ECEDEB" style="font-weight:bold;">
									<s:property value="title" />
								</td>
								<td width="60" height="30" align="center" bgcolor="#ECEDEB" style="font-weight:bold;">
									<s:property value="creater.realname" />
								</td>
								<td width="120" height="30" align="center" >
									<s:property value="course.name" />
							  </td>
								<td width="100" height="30" align="center" >
									<s:if test="supervisorrealname!=null">
										<s:iterator value="supervisorrealname" var="str" status="st">
										<s:property value="str" />&nbsp;&nbsp; 
									</s:iterator>
									</s:if> 
							  </td>
								<td width="130" height="30" align="center" >
									<s:date name="begintime" format="yyyy-MM-dd HH:mm:ss" />
							  </td>
								<td width="130" height="30" align="center" >
									<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss" />
							  </td>
								<td width="160" height="30" align="center" >
								<s:if test="valid == 0">
									<s:if test="iscommon==1">
										<a         
											href="examroom_assignwcInit.action?examRoom.id=<s:property value="id"/>" class=textbg>分配学员</a>
									</s:if>
									<s:else>
										<s:if test="classid > 0 ">
											<a
											href="examroom_assignwcInit.action?examRoom.id=<s:property value="id"/>&course.id=<s:property value="course.id"/>&classid=<s:property value="classid"/>" class=textbg>分配学员</a>
										</s:if><s:else>										
											<a
											href="examroom_assignwcInit.action?examRoom.id=<s:property value="id"/>&course.id=<s:property value="course.id"/>" class=textbg>分配学员</a>
										</s:else>
									</s:else>
								</s:if> 
							  </td>
							</tr>
						  </s:iterator>
						  </tbody>
				 		</table>
				 		<wysLib:page></wysLib:page>
						</td>
					</tr>
			</table>
			</s:else> 
		</div>  
				<form action="examroom_myalllist.action" name="erform" method="post">
					<s:hidden name="pN" id="pageNow">
					</s:hidden>
					<s:hidden name="pS">
					</s:hidden>
					<s:hidden name="course.name">
					</s:hidden>
					<s:hidden name="course_sourse">
					</s:hidden>
				</form>
				<script>
				function page(i){
					document.getElementById("pageNow").value=i;
					erform.submit();
				}
			</script> 
	
	</body>
</HTML>
