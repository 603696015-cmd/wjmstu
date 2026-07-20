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
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript">
			function doSubmit(){
				var name=document.getElementById("typeName").value;
				if(name==""){
					alert("请输入版面类别名称！");
					return false;
				}
			}
		</script>
	</HEAD>
	<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="填写版面分类信息" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">添加新版面类别</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="forum_blocktype_list.action">版面类别列表</a>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<form action="forum_blocktype_add.action" method="post" onSubmit="return doSubmit();">
		<table width="100%" align="center" cellpadding="0" cellspacing="1"
			>
			<tr>
				<td height="30" align="right" >
					<span class="neededitem">*</span>名称：				</td>
				<td align="left" >
					<input name="fbtype.name" size="30" type="text" id="typeName" style="margin-left:5px;">
				</td>
			</tr>
			<tr>
				<td align="right" >
					描述：
				</td>
				<td align="left" >
					<textarea rows="6" cols="40" name="fbtype.description" style="margin-left:5px;"></textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center" height="40px">
					<input type="submit" value="提交" class="textbg4"/>
						<input type="button" class="textbg4"
							onclick="document.location='forum_blocktype_list.action';" value="取消" />
					
				</td>
			</tr>
		</table>
		</form>
	</body>
</HTML>
