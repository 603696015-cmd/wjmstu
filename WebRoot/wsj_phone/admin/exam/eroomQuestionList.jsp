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
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript">
			function setStudyQuestionScore(qid){
				document.getElementById("qid").value=qid;
				var score=document.getElementById(qid+"_score").value;
				document.getElementById("score").value=score;
				erForm.action="setStudyQuestionScore.action";
				if(score==""){
					alert("请输入分值！");
					return;
				}
				erForm.submit();
			}
			function page(i){
				document.getElementById("pageNow").value=i;
				erForm.submit();
			}
		</script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="考生增减" /></div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<s:form action="eroomQuestionList" method="post" name="erForm">
			<s:hidden name="pN" id="pageNow" />
			<s:hidden name="pS" />
			<s:hidden name="examRoom.id" />
			<s:hidden name="question.title" />
			<s:hidden name="question.id" id="qid" />
			<s:hidden name="question.myScore" id="score" />
		</s:form>
		<div style="margin-top: 0px;">
			<div style="text-align: center;">
				<h3>
					考场【
					<s:property value="examRoom.title" />
					】中所用到的题目
			  </h3>
				<table width="850px" align="center" cellpadding="1" cellspacing="1">
					<tr>
						<td height="30" align="center" >
							题干
						</td>
						<td height="30" width="80" align="center" >
							题型
						</td>
						<td height="30" width="100" align="center" >
							题库
						</td>
						<td height="30" align="center" >
							所属材料题
						</td>
						<td height="30" width="100" align="center" >
							加分
						</td>
						<td height="30" width="100"></td>
					</tr>
					<s:if test="questions.size==0">
						<tr><td colspan="6" align="center" style="font-size:14px;">没有搜到“<s:property value="question.title"/>”相关的题目</td></tr>
					</s:if>
					<s:iterator value="questions">
						<tr>
							<td height="30" align="center" >
								<s:property value="title" />
							</td>
							<td height="30" align="center" >
								<s:property value="qtypeName" />
							</td>
							<td height="30" align="center" >
								<s:property value="qlib.name" />
							</td>
							<td height="30" align="center" >
								<s:if test="parent.id==0">--</s:if>
								<s:else>
									<s:property value="parent.title" />
								</s:else>
							</td>
							<td height="30" align="center">
								<input name="<s:property value="id" />_score" size="6" value="0" />
							</td>
							<td height="30" align="center">
								<a href="javascript:setStudyQuestionScore('<s:property value="id" />');" class="textbg6">确认加分</a>
							</td>
						</tr>
					</s:iterator> 
				</table>
				<wysLib:page></wysLib:page>
				<a class="textbg4" href="exampaperreadlist.action?examRoom.id=<s:property value="examRoom.id" />">返回</a>	
			</div>
		</div><br>
		<!-- 内容 -->
	
	</body>
</HTML>
