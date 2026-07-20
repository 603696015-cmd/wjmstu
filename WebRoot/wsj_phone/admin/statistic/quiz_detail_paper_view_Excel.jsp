<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@ page contentType="application/msexcel"%>
<%
	//就是靠这一行，让前端浏览器以为接收到一个excel档 
	response.setHeader("Content-disposition",
			"attachment; filename=ExportExampaperComparison.xls");
%>
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
		<base href="<%=basePath%>">
	</HEAD>
	<body>
			<table width="100%" align="center" border="1"
				bgcolor="#EBEBEB">
				<tr>
					<td height="30" colspan="13" align="left">
						考核总人数：
						<b><s:property value="examRoom.userSize" />
						</b> 缺考人数：
						<b><s:property value="examRoom.usersize" />
						</b>
					</td>
				</tr>
				<tr>
					<th height="30" width="150px;" align="center">
						试卷名称
					</th>
					<th height="30" width="150px;" align="center">
						应考人数
					</th>
					<th height="30" width="150px;" align="center">
						缺考人数
					</th>
					<th height="30" align="center">
						60分以下人数
					</th>
					<th height="30" align="center">
						60-69分
					</th>
					<th height="30" align="center">
						70-79分
					</th>
					<th height="30" align="center">
						80-89分
					</th>
					<th height="30" align="center">
						90分以上
					</th>
					<th height="30" align="center">
						60分以下人数比例
					</th>
					<th height="30" align="center">
						60-69分
					</th>
					<th height="30" align="center">
						70-79分
					</th>
					<th height="30" align="center">
						80-89分
					</th>
					<th height="30" align="center">
						90分以上
					</th>
				</tr>
				<tbody onMouseOut="changeback()" onMouseOver="changeto()">
					<s:iterator value="myExamPapers" status="ermst">
						<tr>
							<td height="30" style="padding-left: 8px; color: blue;"
								align="left">
								<s:property value="examPaper.title" />
							</td>
							<td height="30" align="center" bgcolor="#FFFFFF"
								style="color: #CC0099;">
								<s:property value="yksize" />
							</td>
							<td height="30" align="center" bgcolor="#FFFFFF"
								style="color: #CC0099;">
								<s:property value="qksize" />
							</td>
							<td height="30" align="center" bgcolor="#FFFFFF"
								style="color: #CC0099;">
								<s:property value="pass_6" />
							</td>
							<td align="center">
								<s:property value="pass6_7" />
							</td>
							<td align="center">
								<s:property value="pass7_8" />

							</td>
							<td height="30" align="center">
								<s:property value="pass8_9" />
							</td>
							<td height="30" align="center">
								<s:property value="pass9_" />
							</td>
							<td height="30" align="center" bgcolor="#FFFFFF"
								style="color: #CC0099;">
								<s:property value="pass_6_ps" />
								%
							</td>
							<td align="center">
								<s:property value="pass6_7_ps" />
								%
							</td>
							<td align="center">
								<s:property value="pass7_8_ps" />
								%

							</td>
							<td height="30" align="center">
								<s:property value="pass8_9_ps" />
								%
							</td>
							<td height="30" align="center">
								<s:property value="pass9__ps" />
								%
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		<!-- 内容 -->
	
	</body>
</HTML>
