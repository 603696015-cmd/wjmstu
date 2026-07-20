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
		<TITLE>部门管理</TITLE> 
		<base target="_self" href="<%=basePath%>" >
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/tree/dep.js"></script>
		<script type="text/javascript">
			function doSubmit(treeType){
				//1.获取所有被选中的节点
				var arrayBh=document.getElementsByName("departments.id");
				var bh="";
				var arr = new Array();    
				var x =0;
				var bmid = 0;
				for(var i=0; i<arrayBh.length;i++){   
					if(arrayBh[i].checked==true){  
						arr[x++]=arrayBh[i].value;  
				 	} 
				}  
				window.returnValue = arr;  
				window.close();
				//setTimeout(window.close(),2000);
			} 
		</script>
	</HEAD>
	<body>
		<div id="div"></div>
		<div style="margin-left: 20px;">
			<form action="optionDep.action" method="post" name="myForm">
				<!-- 部门树 -->
					<%--  <wysLib:dep_list did="1" itype="cb_2" treeType="depl" iname="depl" /> --%>
					<wysLib:dep_list_aj rootAble="false" iname="departments.id" itype="cb" />
					<input type="button" style="margin-left:260px" value="确&nbsp;&nbsp;认" onclick="doSubmit('depl');" />
			<form>
			
		<!-- 	<button value="did" onclick="javascript:d1.oAll(true);"></button> -->
		</div>
	</body>
</HTML>
