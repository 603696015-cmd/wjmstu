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
		<script type="text/javascript" src="editor/fckeditor.js"></script>
		<SCRIPT type="text/javascript">
			function myload(){
				if("${elmessage}"!=""){
				alert( "${elmessage}!");
				}
				var oFCKeditor = new FCKeditor('content') ;
				oFCKeditor.BasePath = "editor/" ;
				oFCKeditor.Height = 400;
				oFCKeditor.Width = 980;
				oFCKeditor.ReplaceTextarea();
			}
		
		</SCRIPT>
	</HEAD>
	<body onLoad="myload();">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="修改信息" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">首页信息设置</span>

			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<s:form action="system_alter" method="post" theme="simple"
				name="department_info" id="department_info">
				<table border="0" align="center" width="90%" cellpadding="2" cellspacing="2"
					bgcolor="#EBEBEB">
					<tr>
						<td width="200px" align="center" >
							<strong>信息类别</strong>
							<!--<s:hidden name="sysconf.type" />
							<s:if test="sysconf.type==1">管理员帮助</s:if>

							<s:if test="sysconf.type==2">讲师帮助</s:if>
							<s:if test="sysconf.type==3">学员帮助</s:if>

							-->
							</td><td>
							<select name="sysconf.type">
							<option <s:if test="sysconf.type==4">selected='selected'</s:if>
								value="1">
								个人中心
							</option>
							<option <s:if test="sysconf.type==5">selected='selected'</s:if>
								value="2">
								课程中心
							</option>
							<option <s:if test="sysconf.type==6">selected='selected'</s:if>
								value="3">
								在线考试
							</option>
							<option <s:if test="sysconf.type==7">selected='selected'</s:if>
								value="4">
								学习通知
							</option>
							<option <s:if test="sysconf.type==8">selected='selected'</s:if>
								value="5">
								积分办法
							</option>
							<option <s:if test="sysconf.type==8">selected='selected'</s:if>
								value="6">
								联系我们
							</option><option <s:if test="sysconf.type==8">selected='selected'</s:if>
								value="7">
								  学习帮助 
							</option></select>
						</td>
						<td bgcolor="#FFFFFF" width="200">
							
							<input type="submit" value="保存设置">
						</td>
					</tr>
				</table>
				<div style="text-align: center; width: 100%">
					<label>
						<s:textarea name="sysconf.content" id="content"
							cssStyle="width: 90%; height: 540px;; visibility: hidden;" />
					</label>
				</div>
			</s:form>
		</div>
</HTML>
