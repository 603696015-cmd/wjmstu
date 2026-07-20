<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
 <%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>   
 <%@ taglib prefix="fmt" 
           uri="http://java.sun.com/jsp/jstl/fmt" %>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta charset="UTF-8">
		<title>HSK</title>
		<link rel="stylesheet" href="simulation/css/bootstrap.min.css">
		<link rel="stylesheet" href="simulation/css/common.css">
	</head>
	<body>
		<div class="e-hsk">
			<h3 class="e-hsk-topic">汉语水平考试HSK网模拟考试</h3>
			<div class="e-hsk-dg">
				<p class="e-hsk-title">HSK大纲卷</p>
				<div class="row">
					<c:forEach items="${examPapers }" var="item">
						<div class="col-md-2">
							<div class="e-hsk-type"><a href="simulation_login.action?examId=${item.id }">${item.title}</a></div>
						</div>
					</c:forEach>
				
				</div>
			</div>
			<div class="e-hsk-zt">
				<p class="e-hsk-title">HSK真题集</p>
				<div class="e-hsk-cont">
					<ul>
						<li>
							<p class="e-hsk-title-gray">HSK一级</p>
							<div class="e-hsk-dot">
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
							</div>
						</li>
						<li>
							<p class="e-hsk-title-gray">HSK一级</p>
							<div class="e-hsk-dot">
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
							</div>
						</li>
						<li style="margin: 0;">
							<p class="e-hsk-title-gray">HSK一级</p>
							<div class="e-hsk-dot">
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
							</div>
						</li>
					</ul>
					<ul>
						<li>
							<p class="e-hsk-title-gray">HSK一级</p>
							<div class="e-hsk-dot">
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
							</div>
						</li>
						<li>
							<p class="e-hsk-title-gray">HSK一级</p>
							<div class="e-hsk-dot">
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
							</div>
						</li>
						<li style="margin: 0;">
							<p class="e-hsk-title-gray">HSK一级</p>
							<div class="e-hsk-dot">
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
								<a href="###">H11115</a>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</body>
</html>
