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
		<script type="text/javascript">
			function doSubmit(){
				var basevalue=document.getElementById("name");
				basevalue.value = basevalue.value.replace(/^\s+|\s+$/g,"");//去除2头空格
				if(basevalue.value==""){
					alert("名称不能为空！");
					return false;
				}
				
				return true;
			}
		</script>
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
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="基础类别添加" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<s:form action="eluser_addBaseType.action" method="post" theme="simple"
			onsubmit="return doSubmit();">
			<table width="100%" cellpadding="2" cellspacing="1">
				
				<tr>
					<td width="120" height="30" align="center">
						<span class="neededitem">*</span>名称：
					</td>
					<td>
						&nbsp;<label>
							<s:textfield name="bdt.name" id="name" />
						</label>
					</td>
				</tr>
				
				<tr>
					<td width="120" height="30" align="center">
						描述：
					</td>
					<td>
						&nbsp;<s:textarea name="bdt.remack" cols="60" rows="7"></s:textarea>
					</td>
				</tr>
				<tr>
					<td width="120" height="50" align="center">&nbsp;
						

					</td>
					<td>
						&nbsp;<input class="textbg6" type="submit" value="确认添加">
						&nbsp;&nbsp;
						<input class="textbg6"
							onclick="document.location='eluser_BaseTypeList.action'"
							type="button" value="取消">
					</td>
				</tr>
			</table>
			<br>
		</s:form>
	</body>
</HTML>
