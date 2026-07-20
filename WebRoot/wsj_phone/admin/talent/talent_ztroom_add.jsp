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
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/assist.js"></script>
		<script type="text/javascript" src="js/talent.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<li>
				<span style="font-weight: bold;">主观评价场次添加</span>
			</li>
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<form action="talent_ztroom_add.action" method="post">
			<table width="60%" cellpadding="1" cellspacing="1">
				<caption>
					主观评价场次添加
				</caption>
				<tr>
							<td>
								<strong>标题</strong>
							</td>
							<td height="30" align="left" >
								<label>
									<input type="text" name="ztroom.title" size="30"
										value="" />
								</label>
							</td>
						</tr>
						<tr>
							<td>
								<strong>描述</strong>
							</td>
							<td height="30" align="left" >
								<label>
								<textarea name="ztroom.description" cols="40" rows="5"></textarea>
								</label>
							</td>
						</tr>
						<tr>
							<td>
								<strong>开始结束时间</strong>
							</td>
							<td height="30" align="left" >
								<label>
								<input type="text" name="ztroom.begintime" onclick='setday(this)'  />
								到<input type="text" name="ztroom.endtime" onclick='setday(this)'  />
								</label>
							</td>
						</tr>
						<tr>
							<td>
								<strong>测评指标</strong>
							</td>
							<td height="30" align="left" >
								<div id="trnorms" style="width: 100%;">
									
								</div>
								<script type="text/javascript">
									addNorms();
								</script>
								<input type="button" value="增加" onClick="addNorms();"/>
							</td>
						</tr>
						<tr>
							<td width="120" height="50" align="center" >
							</td>
							<td height="50" align="left" >
								<input type="submit" value="确认添加">
							</td>
						</tr>
					</table>
					</form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
