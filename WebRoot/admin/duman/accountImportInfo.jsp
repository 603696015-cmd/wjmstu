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
		<TITLE>用户批量导入</TITLE>
		<base href="<%=basePath%>" />
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
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
		<script type="text/javascript">
			function doSubmit(){
				document.impForm.submit();
			}
			function returnImp(){
				var depid="<s:property value="elUser.department.id" />";
				if(depid>0){
					document.impForm.action="account_importBydepInit.action";
				}else{
					document.impForm.action="account_importInit.action";
				}
				document.impForm.submit();
			}
		</script>
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="账号导入" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; margin-left: 300px;">
			<br>
			<form action="account_import.action" enctype="multipart/form-data"
				method="post" name="impForm">
				<input type="hidden" name="elUser.department.id" value="<s:property value="elUser.department.id"/>">
				<input type="hidden" name="stFileName" value="<s:property value="stFileName"/>">
				<div style="font-size: 13px; font-weight: bold; color: red; line-height: 30px;">
					${elmessage}
				</div>
				<a href="javascript:doSubmit();" class="textbg">确认导入</a>
				<a href="javascript:returnImp();" class="textbg">取  消</a>
				&nbsp;
			</form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
<!--		 系统管理---用户与部门---用户批量导入(jsp页面)    	  -->
