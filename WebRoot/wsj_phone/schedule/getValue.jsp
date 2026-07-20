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
		<TITLE>选择数据表</TITLE> 
		<base target="_self" href="<%=basePath%>" >
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript">
			function page(i){
				document.getElementById("pageNow").value=i;
				searchRelate.submit();
			}
			
			function doSubmit(){
				var value =$(":radio:checked").val(); 
				if(typeof(value) == 'undefined'){
					alert("请选择!!!");
					return ;
				}
				window.returnValue = value;
				window.close();
			}
		</script>
	</HEAD>
	<body>
		<div style="margin-left: 20px;">
				<wysLib:showlist_select></wysLib:showlist_select>
				<input type="button" style="margin-left:260px" value="确&nbsp;认" onclick="doSubmit();" class="textbg4"/>
				<center>
					<s:if test="count != 0">
						<wysLib:page></wysLib:page>
					</s:if>
				</center>
		</div>
		
	
	</body>
</HTML>
