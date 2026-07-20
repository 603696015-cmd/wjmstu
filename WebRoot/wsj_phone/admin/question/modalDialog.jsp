<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>素材删除操作</title>
		<base href="<%=basePath%>" />
		<script type="text/javascript">
			function del(obj){
				if(obj==1){
					window.returnValue = "1";
					window.close();
				}
				if(obj==2){
					window.returnValue = "2";
					window.close();
				}
				if(obj==3){
					window.returnValue = "3";
					window.close();
				}
			}
		</script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<div style="text-align:center;font-size:12px;margin:5px;">
			&nbsp;&nbsp;&nbsp;&nbsp;确认真删除？（服务器里对应的文件或文件夹都将被删除）
		</div>
		<br />
		<div style="text-align:center;">
			<input type="button" value="真删除" onclick="del(1);" />&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="假删除" onclick="del(2);" />&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="取消" onclick="del(3);" />
		</div>
	
	</body>
</html>
