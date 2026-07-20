<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<title>会员中心-会员注册</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<SCRIPT type="text/javascript">
			function myload(){
				if("${elmessage}"!=""){
				alert( "${elmessage}!");
				}
			
			}
		
		</SCRIPT>
	</HEAD>
	<BODY onLoad="myload();" style="text-align: center;">
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="数值设置" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">兑换设置</span>

			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<s:form action="scorechange_set" method="post" theme="simple"
				name="department_info" id="department_info">
				<table border="0" width="60%" cellpadding="2" cellspacing="2"
					bgcolor="#EBEBEB">
					<tr>
						<td align="center" >
							<strong>1积分</strong>
						</td>
						<td bgcolor="#FFFFFF" width="200">
							<s:textfield name="scoreset.score_2_dian"></s:textfield>点数
						</td>
					</tr>
					<tr>
						<td align="center" >
							<strong>1学分</strong>
						</td>
						<td bgcolor="#FFFFFF" width="200">
							<s:textfield name="scoreset.xfscore_2_score"></s:textfield>积分
						</td>
					</tr>
					<tr>
						<td width="120" align="center" >

						</td>
						<td >
							<label>
								<input type="submit" value="保存设置">
							</label>
						</td>
					</tr>
				</table>
			</s:form>
		</div>
</HTML>
