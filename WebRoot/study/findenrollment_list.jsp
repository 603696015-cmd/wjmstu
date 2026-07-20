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
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<TITLE>五矿发展员工职业发展系统--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
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
		<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="查看详情" />
				</div>
			</li>
		</ul>
		<!-- 证书 -->
		<table width="100%" align="center" cellpadding="2" cellspacing="1">
			<caption>
				证书
			</caption>
			<tr>
				<th align="center">
					培训班名称
				</th>
				<th align="center">
					证书名称
				</th>
				<th align="center">
					我的证书
				</th>
				<!-- 
				<th align="center">
					学习详情
				</th>
				 -->
			</tr>
			<tbody onMouseOut="changeback()" onMouseOver="changeto()">
				<s:iterator value="myClasses">
					<tr>
						<td align="center">
							<s:property value="elClass.name" />
						</td>
						<s:if test="passed">

							<td align="center">
								<s:property value="elClass.certificatename" />
							</td>
							<td align="center">
								<a target="_blank"
									href="mydiploma_view.action?elclass.id=<s:property value="elClass.id"/>"
									class=textbg4>查 看</a>
							</td>

						</s:if>
						<s:else>
							<td align="center" colspan="2">
								还没能获得证书
							</td>
						</s:else>
					</tr>
				</s:iterator>
			</tbody>
	</table>
		<Br>
		<br>
		<!-- 考试成绩 -->
		<!-- 内容 -->
		<!-- 
		<div style="margin-top: 0px; text-align: center;">
			<table width="76%" align="center" cellspacing="1" cellpadding="1">
				<caption>
					考试列表
				</caption>
				<tr>
					<td height="30" style="padding-left: 8px; color: blue;"
						align="left">
						考场名
					</td>
					<td height="30" align="center">
						考场结束时间
					</td>
					<td height="30" align="center">
						成绩
					</td>
				</tr>
				<tbody onMouseOut="changeback()" onMouseOver="changeto()">
					<s:iterator value="myrooms">
						<tr>
							<td height="30" style="padding-left: 8px; color: blue;"
								align="left">
								<s:property value="examroom.title" />
							</td>
							<td height="30" align="center">
								<s:date name="examroom.endtime" format="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td height="30" align="center">
								<s:property value="myScore" />
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<br>
			<br>
			<table width="76%" align="center" cellpadding="2" cellspacing="1">
				<caption>
					线下培训记录
				</caption>
				<tr>
					<th align="center">
						培训名称
					</th>
					<th align="center">
						培训时间
					</th>
					<th align="center">
						证书名称
					</th>
				</tr>
				<tbody onMouseOut="changeback()" onMouseOver="changeto()">
					<s:iterator value="listLineTrainrecord">
						<tr>
							<td align="center">
								<s:property value="trainname" />
							</td>
							<td align="center">
								<s:date name="trainstarttime" format="yyyy-MM-dd" />
								到
								<s:date name="trainendtime" format="yyyy-MM-dd" />
							</td>
							<td align="center">
								<s:property value="certificate" />
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		</div>
		 -->
	</body>
</HTML>
