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
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript">
			function doSub(qid){
				qForm.action="exam_question_info.action";
				document.getElementById("qid").value=qid;
				qForm.submit();
			}
			function page(i){
				document.getElementById("pageNow").value=i;
				qForm.submit();
			}
		</script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="练习错题排行" /></div>
			</li>
		</ul>

				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
				<!-- 内容 -->
		<s:form action="exam_question_list" name="qForm" method="post">
			<s:hidden name="question.id" id="qid" />
			<s:hidden name="question.qlib.id" />
			<s:hidden name="question.title" />
			<s:hidden name="question.qtype" />
			<s:hidden name="pN" id="pageNow"/>
			<s:hidden name="pS" id="pS" value="10"/>
		</s:form>
		<div style="margin-top: 0px;">
			<div style="text-align:center;">
				<table width="100%" align="center" cellpadding="1" cellspacing="1"
					>
					<tr>
						<%--  <th></th> --%>
						<th width="150" height="30" align="center" >
							题干						</th>
						<th width="120" height="30" align="center" >
							答题人次						</th>
						<th width="200" height="30" align="center" >
							答对人次						</th>
						<th width="120" height="30" align="center" >
							答错人次						</th>
						<th width="120" height="30" align="center" >
							答错率						</th>
						<s:if test="question.qtype==1||question.qtype==2||question.qtype==-1">
					   		<th width="220" height="30" align="center" >&nbsp;</th>
					    </s:if>
					</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
					<s:set name="qtype" value="question.qtype" />
					<s:iterator value="questionRankings">
						<tr>
							<td height="30" align="center" >
								<s:property value="question.title" />
							</td>
							<td height="30" align="center" >
								<s:property value="answerCount" />
							</td>
							<td height="30" align="center" >
								<s:property value="answerTo" />
							</td>
							<td height="30" align="center" >
								<s:property value="answerWrong" />
							</td>
							<td height="30" align="center" >
								<s:property value="answerWrongRate_" />%
							</td>
							<s:if test="#request.qtype==1||#request.qtype==2||#request.qtype==-1">
							<td height="30" align="center" >
								<s:if test="question.qtype==1||question.qtype==2">
									<a href="javascript:doSub('<s:property value="question.id"/>');" class="textbg6">查看详情</a>
								</s:if>
						    </td>
						  </s:if>
						</tr>
					</s:iterator></tbody>
			  </table>
			  <wysLib:page></wysLib:page>
			</div>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
					