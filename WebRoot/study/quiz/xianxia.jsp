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
		<TITLE>五矿发展员工职业发展系统--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<script type="text/javascript" src="eltree/dtree.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<body>
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
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="角色管理" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
	<div style="font-size: 13px;text-align:center; margin-top: 0px;">

			<table align="center" cellpadding="1" cellspacing="1" width="93%">
				<tr>
					<th height="34">
						考场名称					</th>
					<th width="690"><s:property value="examRoom.title"/></th>
				</tr>
				<tr>
					<th height="34">
						课程名称</th>
					<th width="690"><s:property value="examRoom.course.name"/></th>
				</tr>
				<tr>
					<th height="34">
						培训班名称					</th>
					<th width="690"><s:property value="elclass.name" /></th>
				</tr>
				<tr>
					<th height="34">
						考试地址</th>
					<th width="690"><s:property value="examRoom.location"/></th>
				</tr>
				<tr>
					<th height="34">
						考试时间</th>
					<th width="690"><s:date name="examRoom.begintime" format="yyyy-MM-dd HH:mm:ss"/></th>
				</tr>
				<tr>
					<th height="34">
						成绩</th>
					<th width="690"><s:property value="myroom.myScore"/></th>
				</tr>
				<tr>
					<th height="34">
						是否通过</th>
					<th width="690">
						<s:if test="myroom.ispassed==1">
							是
						</s:if>
						<s:else>
							否
						</s:else>
					</th>
				</tr>
				
	  </table>
	    <br/>
	    <SCRIPT type="text/javascript">if("${elmessage}"!='null'&&"${elmessage}"!='')
				 alert("${elmessage}!");
		</SCRIPT>
	</div>
		</body>
</HTML>
