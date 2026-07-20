<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>素材重命名</title>
		<base href="<%=basePath%>" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
			function init(){
				$("#qstuffTitle").val(window.dialogArguments.title);
				$("#qstuffTitle").select();
			}
			/*
			function doSubmit(){
				var id=window.dialogArguments.id;
				var title=$("#qstuffTitle").val();
				$.ajax({
					type:"POST",
					url:"qstuffRename.action",
					data:"qstuff.id="+id+"&qstuff.title="+title
				});
				window.returnValue="true";
				window.close();
			}
			*/
			function doSubmit(){
				//var id=window.dialogArguments.id;
				var title=$("#qstuffTitle").val();
				if($.trim(title)==""){
					alert("名称不能为空！");
				}
				window.returnValue=title;
				window.close();
			}
		</script>
	</HEAD>
	<body onload="init();">
		<div style="text-align:center;font-size:12px;">
				<input type="text" name="qstuff.title" size="20" id="qstuffTitle" />
				<input type="button" value="确认" onclick="doSubmit();" />
		</div>
	
	</body>
</html>
