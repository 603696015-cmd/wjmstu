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
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="确认删除" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">删除试卷库信息 </span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="exampaperLib_view.action?examPaperLib.id=<s:property value="examPaperLib.id"/>">查看试卷库信息
				</a>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="exampaperLib_alterInit.action?examPaperLib.id=<s:property value="examPaperLib.id"/>">编辑试卷库信息
				</a>
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
			<s:form action="exampaperLib_delete" method="post" theme="simple">
				<table width="700px" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr>
						<td width="150" height="30" align="center" >
							<input type="hidden" name="id" id="id" value="6">
							确认要删除的目录
						</td>
						<td width="300" >
							<label>
								<s:property value="examPaperLib.name" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="150" height="30" align="center" >
							所属试卷及子目录
						</td>
						<td width="300" >
							<input type="radio" name="sub_operate" checked="checked" value="0">
							<label>
								并入上级试卷库
							</label>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="sub_operate" value="1">
							<label>
								与本试卷库同时删除
							</label>
						</td>
					</tr>
					<tr>
						<td width="150" height="50" align="center" >
							<s:hidden name="examPaperLib.id" />
						</td>
						<td width="300" >
							<input name="submit" class="textbg" style="border: none;color: red;" type="submit" value="确认删除" />
							<input class="textbg" style="border: none;" type="button" onClick="document.location='exampaperLib_view.action?examPaperLib.id=${examPaperLib.id }'" value="取消" />
						</td>
					</tr>
				</table>
			</s:form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
