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
		<TITLE>选择试题库</TITLE> 
		<base target="_self" href="<%=basePath%>" >
		<script type="text/javascript">
			function doSubmit(){
				//1.获取所有被选中的节点
				var arrayBh=document.getElementsByName("qlibName");
				//alert(arrayBh.length);
				var bh="";
				for(var i=0; i<arrayBh.length;i++){  
					if(arrayBh[i].checked==true){
						//alert(arrayBh[i].alt);
						bh=arrayBh[i].alt;
						break;
					}
				}
				//document.myForm.submit();
				window.returnValue = bh;
				window.close();
				//setTimeout(window.close(),2000);
			}
		</script>
	</HEAD>
	<body>
		<div id="div"></div>
		<div style="margin-left: 20px;">
			<form action="userRegister.action" method="post" name="myForm">
				<!-- 部门树 -->
					<wysLib:qlibtree did="1" iname="qlibName" itype="ra_1no" ivalue="1" />
					<input type="button" style="margin-left:260px" value="确&nbsp;&nbsp;认" onclick="doSubmit();" />
			</form>
		</div>
	
	</body>
</HTML>
