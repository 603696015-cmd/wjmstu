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
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<TITLE>选班结果</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)> 
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="选课程结果" /></div>
			</li> 
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<table width="100%" align="center" cellspacing="1" cellpadding="1">
				<caption>
					选课程结果
				</caption>
				<tr>
					<th width="200" height="30" align="center" >
						课程名称					</th>
					<th width="150" height="30" align="center" >
						创建者					</th> 
					<th width="80" height="30" align="center" >
						报名结果</th> 
					<th width="80" height="30" align="center" >
						课程详情</th>
				</tr>
				<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
				<s:iterator value="courses">
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
							<s:property value="name" />
						</td>
						<td height="30" align="center" >
							<s:property value="creater.realname" />				  
						</td> 
						<td height="30" align="center" > 
							<s:if test="status == 5">审核通过</s:if><s:else>
								<s:if test="isjoin == 'false'">不通过</s:if>	 
								<s:else>审核中</s:else>
							</s:else>	
						</td> 
						<td width="120" height="30" align="center" >
							<a href="course_preview.action?course.id=<s:property value="id" />" target="_blank" class="textbg">查看详情</a>						</td>
					</tr>
				</s:iterator></tbody>
		  </table>
		  <form action="choose_class_results.action" name="erform" method="post"> 
					<s:hidden name="pN" id="pageNow"> 
					</s:hidden>
					<s:hidden name="pS">
					</s:hidden>
				</form>
				<script>
				function page(i){
					document.getElementById("pageNow").value=i;
					erform.submit();
				}
				</script>			  
				<wysLib:page></wysLib:page>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
