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
		<TITLE>中国食品安全培训网--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/libutil.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
					<script type="text/javascript">
	
		function doSubmit(){
			var title=document.getElementById("nstyleName");
			title=title.value.replace(/(\s*$)/g, "");
			if(title==""){
				alert("新闻类型名称不能为空!");
				return false;
			}
			return true;
		}
		</script>
	  <style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
        </style>
</HEAD>
	<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="新闻栏目修改" /></div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<s:form action="newsstyle_alter" method="post" name="catalog_info"
			theme="simple" onsubmit="return doSubmit();">
			<input type="hidden" value="0" name="ntype.isshared" />
			<table width="600" cellpadding="2" cellspacing="1" >
				<tr>
					<td width="120" height="30" align="right" >
						<span class="neededitem">*</span>类型名称：
					</td>
					<td >
						<label>
							<s:textfield name="nstyle.name" id="nstyleName" size="60" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="right" >
						类型介绍：
					</td>
					<td >
						<label>
							<s:textarea name="nstyle.description" cols="60" rows="7" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" height="50" align="center" >&nbsp;
						
					</td>
					<td >
						<s:hidden name="nstyle.id"></s:hidden>
						<input type="submit" class="textbg4" style="width:80px" value="确认修改">
						<input class="textbg6" type="button" onClick="document.location='newsstyle_list.action?nstyle.id=<s:property value="nstyle.id"/>'" value="取消">
					</td>
				</tr>
		  </table>
			<br>
		</s:form>
	
	</body>
</HTML>
