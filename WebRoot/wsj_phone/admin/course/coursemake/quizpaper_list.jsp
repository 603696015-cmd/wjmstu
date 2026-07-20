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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="已添加试卷列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">考试管理 </span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="quizpaper_addSearchInit.action?course.id=<s:property value="course.id"/>">
					添加考试</a>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<div style="font-weight: bold; font-size: 24px;">
				当前课程：
				<s:property value="course.name" />
			</div>
			<s:if test="quizPapers.size==0">
														该门课程还没有添加考试..<br>
			</s:if>
			<a
				href="quizpaper_addSearchInit.action?course.id=<s:property value="course.id"/>" class="textbg">
				添加考试 </a>
			<s:else>
				<s:form action="quizpaper_delete" method="post" name="course_info"
					theme="simple" id="course_info">
					<table width="96%" align="center" cellspacing="2">
						<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">&nbsp;
								
							</td>
							<td height="30" align="center" >
								考试标题
							</td>
							<td height="30" align="center" >
								题目总分
							</td>
							<td height="30" align="center" >
								创建时间
							</td>
							<td height="30" align="center" >
								修改时间
							</td>
							<td height="30" align="center" >
								出题方式
							</td>
							<td height="30" align="center" >&nbsp;
								
							</td>
							<td height="30" align="center" >&nbsp;
								
							</td>
							<td height="30" align="center" >&nbsp;
								
							</td>
						</tr>
						<s:iterator value="quizPapers">
							<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
									<input type="checkbox" name="quizPapers.id"
										value="<s:property value="id"/>">
								</td>
								<td height="30" align="center" >
									<s:property value="examPaper.title" />
								</td>
								<td height="30" align="center" >
									<s:property value="examPaper.ep_tscore" />
								</td>
								<td height="30" align="center" >
									<s:date name="examPaper.createtime" format="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td height="30" align="center" >
									<s:date name="examPaper.modifytime" format="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td height="30" align="center" >
									<s:if test="examPaper.random">随机</s:if>
									<s:else>手工</s:else>
								</td>
								<td height="30" align="center" >
									<a
										href="exampaper_preview.action?examPaper.id=<s:property value="examPaper.id"/>"
										target="_blank">预览</a>
								</td>
								<td height="30" align="center" ></td>
								<td height="30" align="center" ></td>
							</tr>
						</s:iterator>
					</table>
					<br>
					<s:hidden name="course.id"></s:hidden>
					<input type="submit" value="删除">
				</s:form>
			</s:else>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
