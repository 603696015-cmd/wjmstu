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
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="editor/fckeditor.js"></script>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((   this .   sectionRowIndex %   2 ==   0)
		? 
		 "#ffffff" :   "#f4f4f4" )
}
</style>
	</HEAD>
	<body onLoad="myload();">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td valign="middle" class="tablequiz">
					<ul class="nav">
						<li>
							<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
								<wysLib:Navigation ivalue="" />
							</div>
						</li>
					</ul>
				</td>
				<td width="120" valign="middle" class="tablequiz">
					<A id=quit href="javascript:window.parent.full_screen(false);"
						class="textbg6" style="display: none">退出全屏</A>
				</td>
			</tr>
		</table>

			<table width="980px" cellpadding="1" align="center" cellspacing="1"
				bgcolor="#ECEDEB">
				<tr>
					<td align="right" bgcolor="#FFFFFF">
						<span class="neededitem">*</span>问题标题：
					</td>
					<td bgcolor="#FFFFFF">
						<s:property value="ques.name"/>
					</td>
				</tr>
				<tr>
					<td align="right" bgcolor="#FFFFFF">
						<span class="neededitem">*</span>所属类别：
					</td>
					<td bgcolor="#FFFFFF">
						<label>
							<s:property value="ques.answeringType.name"/>
						</label>
					</td>
				</tr>
				<tr>
					<td align="right" bgcolor="#FFFFFF">
						有效期：
					</td>
					<td bgcolor="#FFFFFF">
						<label>
							<s:date name="ques.validTime" format="yyyy-MM-dd HH:mm"></s:date>
						</label>
					</td>
				</tr>
				<tr>
					<td align="right" bgcolor="#FFFFFF">
						指定回答人：
					</td>
					<td bgcolor="#FFFFFF">
						<label>
							<div id="users_div_1_1">
								<s:if test="ques.answerUsers.size()!=0">
									<table id=userids_table_1 border='0'  cellspacing='1' cellpadding='1'>
										<tr>
											<s:iterator value="ques.answerUsers" status="status">
												<td id=userids_td_1_<s:property value="#status.index" />>
													<s:property value="realname" /><a href="javascript:removeByUserid(<s:property value="realname" />,1,<s:property value="#status.index" />);"></a>&nbsp;&nbsp;
												</td>
											</s:iterator>
										</tr>
									</table>
								</s:if>
							</div>
						</label>
					</td>
				</tr>
				<tr>
					<td align="right" bgcolor="#FFFFFF">
						问题内容：
					</td>
					<td bgcolor="#FFFFFF">
						<label>
							${ques.content }
						</label>
					</td>
				</tr>
			</table>
	</body>
</HTML>
