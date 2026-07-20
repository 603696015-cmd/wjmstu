<%@ page language="java" pageEncoding="UTF-8"   %>
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
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>扬州专业技术人员继续教育网--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<li>
				<span style="font-weight: bold;">图书栏目添加</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="booktype_list.action">图书栏目管理</a>

			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<s:form action="booktype_add" method="post" name="catalog_info"
			theme="simple">
			<table width="100%" cellpadding="2" cellspacing="1" bgcolor="#D1E4F5">
				<tr>
					<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
						栏目名称
					</td>
					<td bgcolor="#F8FCFE" >
						<label>
							<s:textfield name="btype.name" id="name" size="60" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
						栏目介绍
					</td>
					<td bgcolor="#F8FCFE" >
						<label>
							<s:textarea name="btype.description" cols="60" rows="7" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
						上级栏目
					</td>
					<td bgcolor="#F8FCFE" >
						<label>
							<select name="btype.parent.id" id="parentid">
								<wysLib:bTypeSelect></wysLib:bTypeSelect>
							</select>
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" height="50" align="center" bgcolor="#F8FCFE" >&nbsp;
						
					</td>
					<td bgcolor="#F8FCFE" >
						<input type="submit" value="确认添加">
					</td>
				</tr>
			</table>
			<br>
		</s:form>
	
	</body>
</HTML>
