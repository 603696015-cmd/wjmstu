<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
	<HEAD> 
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<TITLE>查看笔记</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/course.js"></script>
		<script type="text/javascript">
		 function aa()
			  {
			      var str = document.getElementById("notecontent").value;
			      var lab = document.getElementById('notecontent_c');
			      lab.innerHTML = "当前字符数量"+ str.replace(/[^\x00]/g,"**").length/2
		         // if(str.replace(/[^\x00]/g,"**").length/2 > 63)
		         // {
		            //lab.innerHTML = "(63个字)";
		         //  alert('输入的字符数不能超过63');
		         // } 
			  }
		</script>
	</HEAD>
	<BODY style="">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="撰写笔记" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">做笔记 </span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/textbg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url(images/textbg.gif)';this.style.borderStyle='none'"
					href="course_study_notelist.action?course.id=<s:property value="course.id"/>" class="textbg6">查看笔记</a>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<form action="course_study_noteAdd.action" method="post">
			<table width="100%" align="center" cellpadding="2" cellspacing="1"
				>
				<caption>
					课程:
					<s:property value="course.name" />
					的笔记
				</caption>
				<tr>
					<td align="center" bgcolor="#FFFFFF" style="font: 11px;">
						标题：
					</td>
					<td align="center" >
						<input type="text" name="cnote.title" style="width: 440px;" />
					</td>
				</tr>
				<tr>
					<td align="center" bgcolor="#FFFFFF" style="font: 11px;">
						内容：
					</td>
					<td align="center" >
						<textarea id='notecontent' onKeyUp="aa()" name="cnote.content"
							style="width: 440px; height: 145px;"></textarea>
							<span id="notecontent_c"></span>
					</td>
				</tr>
				<tr>
					<td align="center" >
					</td>
					<td align="center" >
						<s:hidden name="course.id"></s:hidden>
						<input type="hidden" name="cnote.course.id"
							value="<s:property value="course.id"/>">
						<input type="submit" onClick="" class="textbg3" value="保　存">
					</td>
				</tr>
			</table>
		</form>
	</BODY>
</HTML>
