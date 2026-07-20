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
		<script type="text/javascript" src="js/message.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="课程列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">待审核的选课</span>
			</li>
			<!--<li class="sep">
			</li>
				<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="coursetype_list.action">课程类别管理</a>
			</li>
			
		--></ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px;text-align: center;">
			<table width="100%">
				<tr>
					<td width="200px" valign="top" id="tree_list_td">
						<wysLib:ctypeTree rootAble="true"
							href="course_selectlist.action?course.name=&course_sourse=${course_sourse}&ctype.id=" />
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
					</td><td valign="top" height="300px;">
			<s:if test="mycourses.size==0"> 没有需要审核的选课</s:if>
			<s:else>
				<table width="90%" align="center" cellpadding="2" cellspacing="2"
					bgcolor="#EBEBEB">
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
							姓名
						</td>
						<td height="30" align="center" >
							用户名
						</td>
						<td height="30" align="center" >
							部门
						</td>
						<td height="30" align="center" >
							申请课程
						</td>
						<td height="30" align="center" >
							课程创建者
						</td>
						<td height="30" align="center" >
							课程类别
						</td>
						<!-- td height="30" align="center" >
							申请人
						</td-->
						<td height="30" align="center" >
							申请时间
						</td>
						<td  height="30" align="center" >
										开始/结束时间
							</td>
							<td height="30" align="center" >
								讲师姓名
							</td>
						<td height="30" align="center" bgcolor="#FFFFFF" colspan="2">
							 审核
						</td>
					</tr>
					<s:iterator value="mycourses">
						<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
								<s:property value="user.username" />
							</td>
							<td height="30" align="center" >
								<s:property value="user.realname" />
							</td>
							<td height="30" align="center" >
								<s:property value="department.name" />
							</td>
							<td height="30" align="center" >
								<s:property value="course.name" />
							</td>
							<td height="30" align="center" >
								<s:property value="course.creater.realname" />
							</td>
							<td height="30" align="center" >
								<s:property value="course.ctype.name" />
							</td>
							<!--td height="30" align="center" >
								<s:property value="user.realname" />
							</td-->
							<td height="30" align="center" >
							<s:date name="applyDate" format="yyyy-MM-dd"/>
							</td>
								<td align="center" >
										<s:if test="islink==4"><s:date name="roomstart" format="yyyy-MM-dd HH:mm:ss" />
										-<br/><s:date name="roomend" format="yyyy-MM-dd HH:mm:ss" />
										</s:if>
										<s:else>
										--
										</s:else>
									</td>
									 <td align="center" >
														<s:if test="islink==4">
														  <s:property value="teacherName" />
														</s:if>
														<s:else>
														--
														</s:else>
													</td>
							<td height="30" align="center" >
							 	<a href="setSelectedCoruse.action?status=3&pN=<s:property value="pN"/>&pS=10&mycourse.course.id=<s:property value="course.id"/>&mycourse.user.id=<s:property value="user.id"/>&mycourse.status=1">通过</a>
							</td>
							<td height="30" align="center" >
								<a href="setSelectedCoruse.action?status=3&pN=<s:property value="pN"/>&pS=10&mycourse.course.id=<s:property value="course.id"/>&mycourse.user.id=<s:property value="user.id"/>&mycourse.status=2">不通过</a>
							</td>
						</tr>
					</s:iterator>
				</table>
				<!--TODO  -->
				
			</s:else></td></tr></table><wysLib:page></wysLib:page>
			<SCRIPT type="text/javascript">
				function page(i){
					document.location.href="course_selectlist.action?pS=10&pN="+i+"&status=3";
				}
			
			</SCRIPT>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
