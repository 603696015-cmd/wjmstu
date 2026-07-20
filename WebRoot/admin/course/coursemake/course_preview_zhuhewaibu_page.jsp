<%@ page language="java" pageEncoding="UTF-8"   %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>"  />
		<title>预览外部课程--<s:property value="course.name" />
		</title>
		<script type="text/javascript" src="js/_wys_menu.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<link href="css/_wys_menu.css" type=text/css rel=stylesheet />
		<script type="text/javascript">
			function myload(){
				var _cat = new Wys_MENU("_cata","课程目录","xxxs") ;
				_cat.show();
			}
		</script>
		<style type="text/css">
			body {
				margin: 0px;
			}
		</style>
	</HEAD>
	<body onload="myload();">
		<iframe width="100%" style="width: 100%; height: 100%;" height="100%"
			frameborder="0" src="<s:property value="coursePage.page_url_"/>">
		</iframe>
	</body>
</html>
