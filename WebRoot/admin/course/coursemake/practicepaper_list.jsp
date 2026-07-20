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
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
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
		<script type="text/javascript" src="js/cexampaper.js"></script>
		
		<script type="text/javascript">
			function isAdd(courseid,pcid){
				var obj="<s:property value='pracPapers'/>";
				if(obj!="[]"&&pcid!=0){
					alert("章节只能添加1个练习！");
					return;
				}
				//practicepaper_addSearchInit.action?course.id=<s:property value="course.id"/>&pracPaper.cpage.id=<s:property value="pracPaper.cpage.id"/>
				document.myForm.submit();
				//return true;
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
					<wysLib:Navigation ivalue="练习列表页" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">练习管理 </span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="practicepaper_addSearchInit.action?course.id=<s:property value="course.id"/>&pracPaper.cpage.id=<s:property value="pracPaper.cpage.id"/>">
					添加练习 </a>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<s:form action="practicepaper_addSearchInit.action" method="post"
			name="myForm">
			<s:hidden name="course.id" />
			<s:hidden name="pracPaper.cpage.id" />
		</s:form>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<div style="font-weight: bold; font-size: 24px;">
				当前课程：
				<s:property value="course.name" />
				<s:if test="coursePage.title==''||coursePage.title==null"></s:if>
				<s:else>(章节：<s:property value="coursePage.title" />)</s:else>
			</div>
			<s:if test="pracPapers.size==0">
				该门课程还没有添加练习..<br>
			</s:if>
			<s:else>
				<s:form action="practicepaper_delete" method="post"
					name="course_info" theme="simple" id="course_info">
					<table width="96%" align="center" cellspacing="2">
						<tr>
							<th width="20" height="30" align="center">
								&nbsp;
							</th>
							<th height="30" align="center">
								练习标题
							</th>
							<!-- 
							<th height="30" align="center" >
								题目数量
							</th> -->
							<th height="30" align="center">
								练习所用试卷
							</th>
							<th width="70" height="30" align="center">
								题目总分
							</th>
							<th width="60" height="30" align="center">
								达标分
							</th>
							<th width="110" height="30" align="center">
								创建时间
							</th>
							<!--<th width="110" height="30" align="center" >
								修改时间							</th>-->
							<th width="60" height="30" align="center">
								&nbsp;
							</th>
							<!-- 
							<th height="30" align="center" >&nbsp;
								
							</th>
							<th height="30" align="center" >&nbsp;
								
							</th>
							 -->
						</tr>
						<tbody onMouseOut="changeback()" onMouseOver="changeto()">
							<s:iterator value="pracPapers" status="ppSt" id="ppId">
								<tr>
									<td width="20" height="30" align="left"
										style="padding-left: 8px; color: blue;">
										<input type="checkbox" name="pracPapers.id"
											value="<s:property value="id"/>">
									</td>
									<td height="30" align="center">
										<s:property value="title" />
									</td>
									<td height="30" align="center">
										<s:property value="examPaper.title" />
									</td>
									<!-- <td height="30" align="center" >
									<s:property value="examPaper.ep_tcount" />(1)
								</td> -->
									<td width="70" height="30" align="center">
										<s:property value="examPaper.ep_tscore" />
									</td>
									<td width="60" height="30" align="center">
										<s:property value="passgrade" />
									</td>
									<td width="110" height="30" align="center">
										<s:date name="examPaper.createtime"
											format="yyyy-MM-dd HH:mm:ss" />
									</td>
									<!--<td width="110" height="30" align="center" >
									<s:date name="examPaper.modifytime" format="yyyy-MM-dd HH:mm:ss" />
							  </td>-->
									<td width="60" height="30" align="center">
										<a
											href="exampaper_preview.action?examPaper.id=<s:property value="examPaper.id"/>"
											target="_blank" class="textbg4">预 览</a>
									</td>
									<!-- 
								<td height="30" align="center" ><s:if test="sortid!=1"><a href="parcticepaper_upsort.action?pracPaper.id=<s:property value="id"/>" class="textbg4">上 移</a></s:if></td>
								<td height="30" align="center" ><s:if test="#ppId.sortid!=(pracPapers.size)"><a href="parcticepaper_downsort.action?pracPaper.id=<s:property value="id"/>" class="textbg4">下 移</a></s:if></td>
								 -->
								</tr>
							</s:iterator>
						</tbody>
					</table>
					<br>
					<s:hidden name="course.id"></s:hidden>
					<s:hidden name="pracPaper.course.id"></s:hidden>
					<s:hidden name="pracPaper.cpage.id"></s:hidden>
					<input class="textbg6" type="submit" value="删除">
				</s:form>
			</s:else>
			<br/>
			<a style="color: red;"
				href="javascript:isAdd('<s:property value="course.id"/>','<s:property value="pracPaper.cpage.id"/>');"
				class="textbg"> 添加练习 </a>
			<a href="course_view.action?course.id=${course.id}" class="textbg">返回制作首页</a>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
