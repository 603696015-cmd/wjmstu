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
	<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="试卷列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">试卷评阅 </span>
			</li>-->
		</ul>
		<div style="margin-top: 40px; text-align: center;">
			<script>
													function page(i){
														document.getElementById("pageNow").value=i;
														epreadform.action="exampaperreadlist.action";
														epreadform.submit();
													}
													function reQuiz( ){
														epreadform.action="requiz.action";
														epreadform.submit();
													}
												</script>
			<!-- 内容 -->
			<form action="exampaperreadlist.action" name="epreadform"
				method="post">
				<s:hidden id="pageNow" name="pN"></s:hidden>
				<s:hidden id="pageSize" name="pS"></s:hidden>
				<s:hidden name="examRoom.id"></s:hidden>
				<table width="100%" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr>
						<th width="40" height="30" align="center" >
							排名						</th>
						<th width="100" height="30" align="center" >
							姓名						</th>
						<th width="150" height="30" align="center" >
							用户名						</th>
						<th width="60" height="30" align="center" >
							试卷数量						</th>
						<th width="40" height="30" align="center" >
							成绩						</th>
						<!--<th height="30" align="center" >
							是否及格
						</th>
						--><s:iterator value="examRoom.myrooms[0].myExamPapers">
							<th height="30" align="center" style="color: red;"
								>
								<s:property value="examPaper.title" />
							</th>
						</s:iterator>
					</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
					<s:iterator value="examRoom.myrooms" status="ermst">
						<tr>
							<td width="40" height="30" align="center" >
								<s:property value="#ermst.index+1" />
						  </td>
							<td width="100" height="30" align="center" >
								<s:property value="tester.realname" />
						  </td>
							<td width="150" height="30" align="center" >
								<s:property value="tester.username" />
						  </td>
							<td width="60" height="30" align="center" >
								<s:property value="epsize" />
								个
						  </td>
							<td width="40" height="30" align="center" >
								<s:property value="myScore" />
						  </td>
							<!--<td height="30" align="center" >
								<s:property value="epsize" />
								个
								<a
									href="quiz_paper_detail_view.action?elUser.id=<s:property value="tester.id"/>&examRoom.id=<s:property value="examRoom.id"/>">查看详情</a>
							</td>
							<td align="center" >
								<s:if test="myScore>=60">及格</s:if>
								<s:else>不及格</s:else>
							</td>
							--><s:set name="userid" value="tester.id"></s:set>
							<s:iterator value="myExamPapers">
								<td height="30" align="center" style="color: red;"
									>
									<s:if test="id==0">未分配</s:if>
									<s:else>
										<s:property value="myScore" />
										/
										<!-- <a
											href="quizpaper_view.action?elUser.id=<s:property value="#userid"/>&myExamPaper.id=<s:property value="id"/>"
											target=_blank>查看</a>/ -->
											<a
											href="exampaper_read.action?myExamPaper.id=<s:property value="id"/>"
											target=_blank class=textbg4>阅 卷</a>
									</s:else>
								</td>
							</s:iterator>
						</tr>
					</s:iterator></tbody>
			  </table>
				<!--<wysLib:page></wysLib:page>
			--></form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
