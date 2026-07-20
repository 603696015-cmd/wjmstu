<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.common.SystemConfOp"%>
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
		<base target="_self">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<TITLE>文档预览</TITLE>
		<META http-equiv=Page-Entercontent=RevealTrans(Duration=0.5,Transition=14)>
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			<!--tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#ffffff")} -->
		</style>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/course.js"></script>
		<script type="text/javascript" src="js/flexpaper.js"></script> 
		<script type="text/javascript" src="js/flexpaper_handlers.js"></script>
		
		<link type='text/css' rel='stylesheet' href='js/liquidcarousel/css/style.css' />
		<link type='text/css' rel='stylesheet' href='js/liquidcarousel/css/liquidcarousel.css' />
		<script type="text/javascript" src="js/liquidcarousel/js/jquery.liquidcarousel.pack.js"></script>
		
		<script type="text/javascript">
			function init(fileext){
				var stuffPath="<s:property value="qstuff.stuff_path"/>";
				var fromchange = <s:property value="qstuff.fromchange"/>;
				if(fileext=="flv"||fileext=="mp4"){
					_cvideo = new CourseVideo(2,stuffPath,1,fromchange);
					_cvideo.show("flvcontent");
					//getCpage(<s:property value="coursePages[0].id" />);
				}else {
					_cvideo = new CourseVideo(1,stuffPath, 60*60,fromchange);
					_cvideo.show("swfcontent");
					if($("#swfcontent").html()==""){
						$("#swfcontent").html("此资源不可预览");
						$("#swfcontent").css("margin-top","100px");
					}
				}
			}
		</script>
		<script type="text/javascript">
			$(document).ready(function() {
				$('#liquid1').liquidcarousel({height:129, duration:100, hidearrows:false});
			});
		</script>
	</HEAD>
	<body onload="init('<s:property value="qstuff.fileext" />');">
		<form action="null" method="post" name="myForm"></form>
		<div align="center" >
			<s:if test="qstuff.fileext=='jpg'||qstuff.fileext=='jpeg'||qstuff.fileext=='gif'||qstuff.fileext=='png'||qstuff.fileext=='tiff'">
				<img src="<s:property value="qstuff.stuff_path" />" />
			</s:if>
			<s:else>
				<s:if test="qstuff.fileext=='flv'||qstuff.fileext=='mp4'">
					<div id="flvcontent"></div>
				</s:if>
				<s:else >
					<div id="swfcontent"></div>
				</s:else>
			</s:else>
			<%-- 
			<s:elseif test="qstuff.fileext=='doc'||qstuff.fileext=='xls'
			||qstuff.fileext=='ppt'||qstuff.fileext=='pdf'||qstuff.fileext=='swf'
			||qstuff.fileext=='flv'||qstuff.fileext=='wmv'||qstuff.fileext=='avi'
			||qstuff.fileext=='mpeg'||qstuff.fileext=='mpg'">
				<div id="swfcontent"></div>
			</s:elseif>
			 --%>
			
		</div>
		
		<div id="liquid1" class="liquid">
			<span class="previous"></span>
			<div class="wrapper">
				<ul>
					<s:iterator value="stuffOfficeJpgs">
						<li><a  href="question_stuffPreview.action?qstuff.id=<s:property value="id"/>" ><img src="elstuffs/<s:property value="qstuff.parent.id"/>/<s:property value="folder"/>/<s:property value="nameSmall"/>" width="88" height="126" alt="<s:property value="title" />" border="0" /></a></li>
					</s:iterator>
				</ul>
			</div>
			<span class="next"></span>
		</div>
		
	</BODY>
</HTML>