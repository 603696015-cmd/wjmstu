<%@ page language="java" pageEncoding="UTF-8"   %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<TITLE>预览外部课程--<s:property value="course.name" />
		</TITLE>
		<link href="css/study_course.css" type=text/css rel=stylesheet>
		<script type="text/javascript" src="js/course.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/flexpaper.js"></script> 
		<script type="text/javascript" src="js/flexpaper_handlers.js"></script>
		
		<link href="css/study_wbkc.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript">function hiddenCat(){
				var cat1=document.getElementById("cat");
				if(cat1.style.display=="block"){
					cat1.style.display="none";
				}
				
			}
			function showCat(obj){
			var cat1=document.getElementById("cat");
			if(cat1.style.display=="block"){
				cat1.style.display="none";
			}else
				cat1.style.display="block";
				var left = (obj.offsetLeft + obj.clientWidth);
				var top = (obj.offsetTop);
				while (obj = obj.offsetParent) {
					left += obj.offsetLeft;
					top += obj.offsetTop;
				}
			cat1.style.left = left;
			cat1.style.top = top+10;
		}</script>
	</HEAD>
	<body style="height: 100%;width:100%;margin: 0px;overflow: auto;">
	<div
			style="border: solid 1px buttonface; z-index: 1000; position: absolute; background: #ffffff; width: 300; height: 300px; overflow: auto; display: none"
			id="cat">
			<div style="width: 100%; height: 20px;">
				<a href="#" style="float: right;" onclick='hiddenCat();return false;'>关闭</a>
			</div>
			<ul style="margin: 10px 20px;">
				<s:iterator value="coursePages">
					<li style="font-size: 14px;">
					<s:if test="property==1">&nbsp;&nbsp;&nbsp;&nbsp;</s:if>
							<a
								href="course_preview_biaozhun.action?coursePage.id=<s:property value="id" />&course.id=<s:property value="course.id"/>">
								<s:property value="title" />
							</a>
							<!-- <img src="img/studied.gif" width="15" height="13"> -->
					</li>
				</s:iterator>
			</ul>
		</div>
		<table height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
			<tbody>
				<tr>
					<td vAlign=top height=38>
						<table cellSpacing=0 cellPadding=0 width="100%" border=0>
							<tbody>
								<tr>
									<td>
										<table cellSpacing=0 cellPadding=0 width="100%" border=0>
											<tbody>
												<tr>
													<td vAlign=bottom align="center"
														background=images/img/bfz_r1_c11.jpg>
														<table height=25 cellSpacing=0 cellPadding=0 width="100%">
															<tbody>
																<tr>
																	<td class=bt vAlign=center align=middle>
																		<s:property value="course.name" />
																	<td>
																	<td vAlign="middle" align="left" width=470
																		style="font-size: 12px;">
																		<a class=dh href="#" style="cursor: hand"
																			onclick="javascript:showCat(this);return false;">目录</a>
																	<td>
																</tr>
															</tbody>
														</table>
													<td>
												</tr>
												<tr>
													<td background=images/img/t-5.jpg height=13><img height=13 src="images/img/t-52.jpg" width=180 /><td>
												</tr>
											</tbody>
										</table>
									<td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td width="100%" height="100%">
						<iframe width="100%" height="100%" frameborder="0"
							id="course_content"
							src="<s:property value="coursePage.page_url_"/>">
						</iframe>
					</td>
				</tr>
			</tbody>
		</table>
	
	</body>
</html>
