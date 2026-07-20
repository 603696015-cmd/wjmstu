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
		<TITLE>用户授权管理</TITLE> 
		<base target="_self" href="<%=basePath%>" >
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
			function doSubmit(treeType){
				//1.获取所有被选中的节点
				var arrayBh=document.getElementsByName("chkName");
				//alert(arrayBh.length);
				var bh="";
				var arr = new Array();    
				var x =0;
				var bmid = 0;
				for(var i=0; i<arrayBh.length;i++){   
					/*if(arrayBh[i].checked==true){  
						arr[x++]=arrayBh[i].alt;  
						if(x == 1){  
							bmid = arrayBh[i].value;
						}else{ 
						  	bmid = bmid +"-"+ arrayBh[i].value ;  	
						}
				 	} */
				 	if(arrayBh[i].checked==true){ 
				 		arr[x++] = arrayBh[i].value;
				 	} 	
				}
				//arr[x++] = bmid; 
				//document.myForm.submit();
				window.returnValue = arr;  
				window.close();
				//setTimeout(window.close(),2000);
			} 
		</script>
	</HEAD>
	<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
	<body>
		<div id="div"></div>
		<div style="margin-left: 20px;">
			<form action="optionDep.action" method="post" name="myForm">
				<!-- 部门树 -->
					<wysLib:dep_list_aj did="0" itype="cb" treeType="depl" iname="chkName" /> 
					<script type="text/javascript">
					w0.setValues([<s:iterator value="assignedDeps" status="depst">new DEP(<s:property value="id"/>,<s:property value="lid"/>,<s:property value="rid"/>)<s:if test="(assignedDeps.size-1)!=#depst.index">,</s:if></s:iterator>]);
					</script>
					<input type="button" style="margin-left:260px" value="确&nbsp;&nbsp;认" onclick="doSubmit('depl');" />
			</form>
			
		<!-- 	<button value="did" onclick="javascript:d1.oAll(true);"></button> -->
		</div>
	
	</body>
</HTML>
