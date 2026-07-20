<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.common.ExportWord"%> 
<%@page import="java.util.List"%>
<%@page import="com.sopia.questionman.entities.Question"%>
<%@page import="java.util.ArrayList"%>  
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%> 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<% 
	//靠这下面代码，让前端浏览器接收到一个excel档 　 
	List questionlist = (List)request.getAttribute("questionlist"); 
	int qlbid = ((Integer)request.getAttribute("qlbid")).intValue(); 
	response.reset(); 		
	response.setHeader("Content-disposition","attachment; filename=question_exportExcel.xls"); 
	response.setContentType("application/vnd.ms-excel");  
	ExportWord.writeExcel(response.getOutputStream(),questionlist,qlbid);  
 	out.clear();
	out=pageContext.pushBody();

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
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<!-- 内容  导出换方法 ， 下面内容是无用的 -->
		<!-- <div style="margin-top: 40px; text-align: center;">
			<table width="100%" align="center" cellpadding="1" cellspacing="1">
				<tr>
					<th height="30" align="center" >
						题型
					</th>
					<th height="30" align="center" >
						试题名称
					</th>
					<th height="30" align="center" >
						题干内容
					</th>
					<th height="30" align="center" >
						题支(选择题选项)
					</th>
					<th height="30" align="center" >
						答案
					</th>
					<th height="30" align="center" >
						解析
					</th>
					<th height="30" align="center" >
						难度
					</th>
					<th height="30" align="center" >
						参考分值
					</th>
					<th height="30" align="center" >
						所属题库
					</th>
				</tr>
				<s:iterator value="questionlist">
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
							<s:property value="qtypeName" />
						</td>
						<td height="30" align="center" >
							<s:property value="title" />
						</td>
						<td height="30" align="center" >
							<s:property value="content" />
						</td>
						<td height="30" align="center" >
							<s:property value="testsupport" />
						</td>
						<td height="30" align="center" >
							<s:property value="answer" />
						</td>
						<td height="30" align="center" >
							<s:property value="qexplain" />
						</td>
						<td height="30" align="center" >
							<s:property value="qlevel" />
						</td>
						<td height="30" align="center" >
							<s:property value="oldrulestring" />
						</td>
						<td height="30" align="center" >
							<s:property value="qlib.name" />
						</td>
					</tr>
				</s:iterator>
			</table>
		</div> -->
		<!-- 内容 -->
	
	</body>
</HTML>
