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
		<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="试卷列表页" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">添加练习 </span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="practicepaper_list.action?course.id=<s:property value="course.id"/>">练习管理
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
			<form action="practicepaper_addInit.action" method="post"
				name="acc_list">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<s:hidden name="pracPaper.skipable" id="ppsa"></s:hidden>
				<s:hidden name="pracPaper.cpage.id"></s:hidden>
				<s:hidden name="sublibs"></s:hidden>
				<s:hidden name="course.id"></s:hidden>
				<input type="hidden" value="<s:property value="course.id"/>"
					name="pracPaper.course.id" />
				<s:hidden name="examPaper.title"></s:hidden>
				<s:hidden name="examPaper.epl.id"></s:hidden>
			</form>
			<script type="text/javascript">
				function page(i){
					acc_list.action="practicepaper_addInit.action";
					document.getElementById("pageNow").value=i;
					acc_list.submit();
				}
				</script>
			<div style="font-weight: bold; font-size: 24px;">
				当前课程：
				<s:property value="course.name" />
				<s:if test="coursePage.title==''||coursePage.title==null"></s:if>
				<s:else>(章节：<s:property value="coursePage.title" />)</s:else>
			</div>
			<s:if test="exampapers.size==0">没有符合条件的试卷..<br>
				<a href="javascript:history.back(-1)"> 返回 </a>
			</s:if>
			<s:else>
				<s:form action="practicepaper_add" method="post" name="course_info"
					theme="simple" id="practicepaper_add">
					<table width="96%" align="center" cellspacing="1">
						<tr>
							<th align="center" >&nbsp;
								
							</th>
							<th align="center" >
								试卷标题
							</th>
							<th align="center" >
								所属试卷库
							</th>
							<th align="center" >
								试卷时长
							</th>
							<!--<th align="center" >
								出题方式
							</th>
							-->
							<th align="center" >
								创建时间
							</th>
							<th align="center" >
								修改时间
							</th>
							<th align="center" >&nbsp;
								
							</th>
						</tr>
						<tbody onMouseOut="changeback()" onMouseOver="changeto()">
							<s:iterator value="exampapers">
								<tr>
									<td align="center" >
										<s:if test="courseHasEp">已经添加</s:if>
										<s:else>
											<input type="radio" name="pracPaper.examPaper.id"
												value="<s:property value="id"/>">
											<!--<input type="checkbox" name="exampapers.id"
											value="<s:property value="id"/>">
									-->
										</s:else>
									</td>
									<td height="30" align="center" >
										<s:property value="title" />								  </td>
									<td align="center" >
										<s:property value="epl.name" />
									</td>
									<td align="center" >
										<s:property value="during" />
									</td>
									<!--
									<td align="center" >
										<s:if test="random">随机</s:if>
										<s:else>手工</s:else>
									</td>
									-->
									<td align="center" >
										<s:date format="yyyy-MM-dd HH:mm:ss" name="createtime" />
									</td>
									<td align="center" >
										<s:date format="yyyy-MM-dd HH:mm:ss" name="modifytime" />
									</td>
									<td align="center" >
										<a target="_blank"
											href="exampaper_preview.action?examPaper.id=<s:property value="id" />">预览</a>
									</td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
					<br>
					<!--
					共<s:property value="exampapers.size" />条试卷<br>
					-->
					<wysLib:page></wysLib:page>
					<br>
					<s:hidden name="pN"></s:hidden>
					<s:hidden name="pS"></s:hidden>
					<s:hidden name="pracPaper.skipable" id="ppsa"></s:hidden>
					<s:hidden name="pracPaper.cpage.id"></s:hidden>
					<s:hidden name="sublibs"></s:hidden>
					<s:hidden name="course.id"></s:hidden>
					<input type="hidden" value="<s:property value="course.id"/>"
						name="pracPaper.course.id" />
					<s:hidden name="examPaper.title"></s:hidden>
					<s:hidden name="examPaper.epl.id"></s:hidden>
					<input type="button" onClick="showpracticeAdd();" value="添加到当前课程练习" />
				</s:form>
			</s:else>
		</div>
		<!-- 内容 -->

	</BODY>
</HTML>
