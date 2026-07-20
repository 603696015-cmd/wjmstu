<%@ page language="java" pageEncoding="UTF-8"   %>
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
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>五矿发展员工职业发展系统--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/classman.js"></script>
		<STYLE type="text/css">
#credit_button {
	position: absolute;
	background: #ffffff;
	z-index: 100;
	width: 100px;
	height: 20px;
	text-align: center;
	vertical-align: middle;
	border: solid red 1px;
	padding: 3px 3px;
	display: none;
}

#credit_alter_success {
	position: absolute;
	background: #ffffff;
	z-index: 100;
	width: 100px;
	height: 20px;
	text-align: center;
	vertical-align: middle;
	border: solid red 1px;
	padding: 3px 3px;
	display: none;
}
</STYLE>
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="新闻列表页" /></div>
			</li>
			<li>
			<span style="font-weight: bold;">培训班课程学分设置</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="elclass_course.action?elclass.id=<s:property value="elclass.id"/>">管理培训班课程</a>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<div id="credit_button">
			<input type="button" onclick="credit_alter('credit_alter_success')"
				value="修改学分">
		</div>
		<div id="credit_alter_success"></div>
		<!-- 	<form action="elclass_course_credit_alter.action" method="post" name="class_course" id="class_course"> -->
		<br>
		<br>
		<table width="80%" align="center">
		<caption>必修课列表</caption>
			<tr>
				<td>
					课程名称
				</td>
				<td>
					课程类型
				</td> 
				<td>
					推荐学分
				</td>
				<td>
					学分
				</td>
			</tr>
			<s:iterator value="bxCourses">
				<tr>
					<td>
						<s:property value="name" />
					</td>
					<td>
						必修课
					</td>
					<td>
						<s:property value="defalutcredit" />
					</td>
					<td>
						<input type="text" id="c_<s:property value="id" />"
							onclick="showAlterButton('c_<s:property value="id" />',<s:property value="id" />)"
							onmouseover="showAlterButton('c_<s:property value="id" />',<s:property value="id" />)"
							onblur="unShowAlterButton()" size="4"
							value="<s:property value="credit" />">
					</td>
				</tr>
			</s:iterator>
		</table>
		<br>
		<br>
		<table width="80%" align="center">
		<caption>主修课列表</caption>
			<tr>
				<td>
					课程名称
				</td>
				<td>
					课程类型
				</td>
				<td>
					推荐学分
				</td>
				<td>
					学分
				</td>
			</tr>
			<s:iterator value="zxCourses">
				<tr>
					<td>
						<s:property value="name" />
					</td>
					<td>
						主修课
					</td>
					<td>
						<s:property value="defalutcredit" />
					</td>
					<td>
						<input type="text" id="c_<s:property value="id" />"
							onclick="showAlterButton('c_<s:property value="id" />',<s:property value="id" />)"
							onmouseover="showAlterButton('c_<s:property value="id" />',<s:property value="id" />)"
							onblur="unShowAlterButton()" size="4"
							value="<s:property value="credit" />">
					</td>
				</tr>
			</s:iterator>
		</table>
		<br>
		
		<table width="80%" align="center">
			<caption>必修课列表</caption>
			<tr>
				<td>
					课程名称
				</td>
				<td>
					课程类型
				</td>
				<td>
					推荐学分
				</td>
				<td>
					学分
				</td>
			</tr>
			<s:iterator value="xxCourses">
				<tr>
					<td>
						<s:property value="name" />
					</td>
					<td>
						选修课
					</td>
					<td>
						<s:property value="defalutcredit" />
					</td>
					<td>
						<input type="text" id="c_<s:property value="id" />"
							onclick="showAlterButton('c_<s:property value="id" />',<s:property value="id" />)"
							onmouseover="showAlterButton('c_<s:property value="id" />',<s:property value="id" />)"
							onblur="unShowAlterButton()" size="4"
							value="<s:property value="credit" />">
					</td>
				</tr>
			</s:iterator>
		</table>
		<input type="hidden" id="classid"
			value="<s:property value="elclass.id"/>" />
		<!--	<input type="submit" value="保存设置">  </form> -->
	</body>
</HTML>
