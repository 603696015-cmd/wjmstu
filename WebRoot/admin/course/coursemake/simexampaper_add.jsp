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
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/divdialog.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="试卷列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">添加模拟考试 </span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="simexampaper_list.action?course.id=<s:property value="course.id"/>">模拟考试管理
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
		<div style="margin-top: 0px; text-align: center;">
			<div style="font-weight: bold; font-size: 24px;">
				当前课程：
				<s:property value="course.name" />
			</div>
			<s:if test="exampapers.size==0">
														没有符合条件的试卷..<br>
				<a href="javascript:history.back(-1)"> 返回 </a>
			</s:if>
			<s:else>
				<s:form action="simexampaper_add"  method="post" name="course_info"
					theme="simple" id="simexampaper_add">
					<table width="96%" align="center" cellspacing="2">
						<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">&nbsp;
								
							</td>
							<td height="30" align="center" >
								试卷标题
							</td>
							<td height="30" align="center" >
								所属试卷库
							</td>
							<td height="30" align="center" >
								试卷时长
							</td>
							<td height="30" align="center" >
								出题方式
							</td>
							<td height="30" align="center" >
								创建时间
							</td>
							<td height="30" align="center" >
								修改时间
							</td>
							<td height="30" align="center" >&nbsp;
								
							</td>
							<td height="30" align="center" >&nbsp;
								
							</td>
						</tr>
						<s:iterator value="exampapers">
							<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
									<s:if test="courseHasEp">已经添加</s:if>
									<s:else>
										<input type="radio" name="simPaper.examPaper.id"
											value="<s:property value="id"/>">
									</s:else>
								</td>
								<td height="30" align="center" >
									 <s:property
											value="title" /> 
								</td>
								<td height="30" align="center" >
									<s:property value="epl.name" />
								</td>
								<td height="30" align="center" >
									<s:property value="during" />
								</td>
								<td height="30" align="center" >
									<s:if test="random">随机</s:if>
									<s:else>手工</s:else>
								</td>
								<td height="30" align="center" >
									<s:date format="yyyy-MM-dd HH:mm:ss" name="createtime" />
								</td>
								<td height="30" align="center" >
									<s:date format="yyyy-MM-dd HH:mm:ss" name="modifytime" />
								</td>
								<td height="30" align="center" >
									<a
										href="exampaper_view.action?examPaper.id=<s:property value="id" />">详情</a>
								</td>
								<td height="30" align="center" >
									<a target="_blank"
										href="exampaper_preview.action?examPaper.id=<s:property value="id" />">预览</a>
								</td>
							</tr>
						</s:iterator>
					</table>
					<br>
					共<s:property value="exampapers.size" />条试卷<br>
					<br>
					<s:hidden name="pN"></s:hidden>
					<s:hidden name="pS"></s:hidden>
					<s:hidden name="sublibs"></s:hidden>
					<s:hidden name="course.id"></s:hidden>
					<s:hidden name="examPaper.title"></s:hidden>
					<input type="hidden" name="simPaper.course.id" value="<s:property value="course.id"/>" />
					<input type="hidden" name="simPaper.begintime" id="sbegintime"/>
					<input type="hidden" name="simPaper.endtime" id="sendtime"/>
					<s:hidden name="examPaper.epl.id"></s:hidden>
					<input type="button" onClick="showsimpaperAdd();"  value="添加到当前课程模拟考试">
				</s:form>
			</s:else>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
