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
		<TITLE>试题导出箱</TITLE>
		<base target="_self" href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((   this .   sectionRowIndex %   2 ==   0)
		? 
		 "#ffffff" :   "#f4f4f4" )
}
</style>
	</HEAD>
	<body>
		<div id="div"></div>
		<div style="margin-left: 20px;">
			<ul class="nav">
				<li>
					<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
						<wysLib:Navigation ivalue="试题导出箱" />
					</div>
				</li>
			</ul>
			<form action="myForm.action" method="post" name="myForm">

				<table width="95%" align="center" cellpadding="1" cellspacing="1">
					<caption>
						试题导出箱
					</caption>
					<tr>
						<th height="30" align="center">
							&nbsp;
						</th>
						<th height="30" align="center">
							题干
						</th>
						<th height="30" align="center">
							题目类型
						</th>
						<th height="30" align="center">
							所属题库
						</th>
						<th height="30" align="center">
							状态
						</th>
					</tr>
					<tbody onMouseOut="changeback()" onMouseOver="changeto()">
						<s:iterator value="questions">
							<tr>
								<td height="30" style="padding-left: 8px; color: blue;"
									align="left">
									<input type="checkbox" name="questions.id"
										value="<s:property value="id"/>">
								</td>
								<td height="30" align="center">
									<a name="tdTitle" title="<s:property value="title" />"
										target="_blank"> <s:property value="title" /> </a>
								</td>
								<td height="30" align="center">
									<s:property value="qtypeName" />
								</td>
								<td height="30" align="center">
									<s:property value="qlib.name" />
								</td>
								<td height="30" align="center">
									<s:property value="statusName" />
								</td>
							</tr>
						</s:iterator>
					</tbody>
					<s:hidden name="exprot" id="exprot" />
					<s:hidden name="pN" id="pN" />
					<s:hidden name="pS" />
				</table>
			</form>
			<div style="text-align: center;">
				<wysLib:page></wysLib:page>
			</div>
			<table width="95%" align="center" cellpadding="2" cellspacing="1">
				<tr>
					<td>
						<s:if test="questions != null">
							<input class=textbg4 style="height: 35px; width: 60px;"
								type="button" onclick="toDeletImpExcel()" value="删 除">
							<input class=textbg4 style="height: 35px; width: 60px;"
								type="button" onclick="toLeerenImpExcel()" value="清 空">
							<input class=textbg6 style="height: 35px; width: 100px;"
								type="button" value="导出结构试题" onclick="toexcel(true);">
						</s:if>
						<input class=textbg6 style="height: 35px; width: 100px;"
							type="button" value="返回试题列表"
							onclick="document.location='question_list.action'">
					</td>
				</tr>
			</table>

			<script type="text/javascript">   

										function toDeletImpExcel(SelectExprot) { 
											myForm.action = "question_delete_impExecel.action";  
											myForm.submit();
										} 
										function toLeerenImpExcel(SelectExprot) { 
											myForm.action = "question_delete_impExecel.action?leeren=true&questions.id=0";  
											myForm.submit();
										} 
										function toexcel(exprot) { 
											myForm.action ="question_select_impExecelInit.action";
											document.getElementById("exprot").value=exprot;
											myForm.submit();
										} 
										function page(i){
											myForm.action = "question_select_impExecelInit.action";
											document.getElementById("pN").value=i;
											myForm.submit();
										}
										function titleLimit(){ 
											var obj = document.getElementsByName("tdTitle");
											for(var i = 0 ;i <obj.length;++i){
												hiddenTitle(i);
											}
										}
										function showTitle(i){
											var obj = document.getElementsByName("tdTitle");
											for(var i = 0 ;i <obj.length;++i){
												if(i==j){
												obj[i].innerHTML = obj[i].title; 
												// +"<a href=\"javascript:hiddenTitle("+i+")\">隐藏</a>" ;
												}
											}
										}
										function hiddenTitle(j){
											var obj = document.getElementsByName("tdTitle");
											for(var i = 0 ;i <obj.length;++i){
												if(i==j){
													if(obj[i].title.length>20) 
														obj[i].innerHTML = obj[i].title.substring(0,15)+"... " ;
												}
											} 
										}
										titleLimit();

								</script>

			<!-- <button value="did" onclick="javascript:d1.oAll(true);"></button> -->
		</div>

	</body>
</HTML>
