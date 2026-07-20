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
		<base href="<%=basePath%>" target="_self" >
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
			function doSubmit(){
				if($("#chksub").attr("checked")==true){
					$("#sublibs").val(1);
				}else{
					$("#sublibs").val(0);
				}
				document.forms[0].submit();
			}
		</script>
	</HEAD>
	<body>
		<!-- 内容 -->
		    <div style="margin-top:40px;text-align:left;">
		    	<s:form action="questionCountInfo.action" method="post" name="myForm">
				  	<s:hidden name="question.qtype" />
				  	<s:hidden name="sublibs" id="sublibs"/>
			    	是否包含下级节点：<input type="checkbox" id="chksub" onclick="doSubmit();" 
			    	 <s:if test="sublibs==1">checked="checked"</s:if> 
			    	  />
		    	</s:form>
				<wysLib:qlibtree />
			</div>
		<!-- 内容 -->
	
	</body>
</HTML>
