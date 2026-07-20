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
		<TITLE>通知-<s:property value="pop.popTitle" /></TITLE>
		<base href="<%=basePath%>" />
		<style type="text/css">
body {
	font-size: 13px;
	margin: 0px;
	padding: 0px;
	overflow: auto;
}

.top {
	background: url("images/pop/pop_top.jpg");
	width: 489px;
	height: 84px;
}

.content {
	background: url("images/pop/pop_bg.jpg");
	width: 489px;
	height: 425px;
}

.bottom {
	background: url("images/pop/pop_bottom.jpg");
	width: 489px;
	height: 41px;
}
</style>
	</HEAD>
	<BODY>
		<div class="top"></div>
		<div class="content">
			<h3 style="text-align: center; width: 489px;margin: 0px;padding-bottom: 10px;">
				<s:property value="pop.popTitle" />
			</h3>
			<div style="padding: 10px 0px 20px 30px;width:480px;height:350px;overflow-y:auto; ">
				<s:property escape="false" value="pop.popContent" />
			</div>
			<div style="width: 489;font-size:13px;font-weight:bolder;margin-top:10px; padding-right: 40px; text-align: right;">
				<s:date name="pop.createtime" format="yy年MM月dd日 HH:mm" />
			</div>
		</div>
		<div class="bottom"></div>
	
	</body>
</HTML>
