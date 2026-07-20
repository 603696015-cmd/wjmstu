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
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<base href="<%=basePath%>" />
		<title>五矿发展员工职业发展系统--在线练习--<s:property value="examprac.title"/></title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/examprac.js"></script>
		<script type="text/javascript" src="js/ajaxfileupload.js"></script>
		<link rel="stylesheet" href="css/quiz.css" type="text/css" />
		<script type="text/javascript">
		var quizpaper =null;
		function myload()
		{ 
		 document.body.oncontextmenu=function(){return false;}; 
		 document.body.ondragstart=function(){return false;}; 
		 document.body.onselectstart=function(){return false;};
		 document.body.onbeforecopy=function(){return false;};
		 document.body.onselect=function(){document.selection.empty();}; 
		 document.body.oncopy=function(){document.selection.empty();}; 
		// document.body.onmouseup=function(){document.selection.empty();};
		quizpaper=new QuizPaper(<s:property value="examPaper.epBlocks.size"/>);
		}
		 
		</script>
	</HEAD>
	<body onload="myload();">
		<!--<div class="menu" id="mymenu">
			<div style="color: green;">
				答题剩余时间
			</div>
			<div id="examtime">
				加载中..
			</div>
			<div class="opbutton">
				<input class="inputover"
					onclick="if(confirm('确定交卷？')) quizpaper.submit();" type="button"
					value="交卷" />
				<input class="inputover" onclick="quizpaper.save();" type="button"
					value="保存" />
			</div>
		</div>
		--><div class="main">
			<div style="width: 195px; float: left">
				<img src="images/exam/d_3.jpg" border="0" width="195" height="92" />
			</div>
			<div
				style="background-image: url('images/exam/d_1_bg.jpg'); height: 92px; width: 552px; float: left; vertical-align: bottom; text-align: center">
				<div style="margin-top: 50px; overflow: hidden;">
					考试时间：
					<s:property value="examPaper.during" />
					分钟 &nbsp;&nbsp;考生：
					<s:property value="#session.realname" />
					&nbsp;&nbsp;总分：
					<s:property value="examPaper.ep_tscore" />
					分&nbsp;&nbsp;客观题总分：
					<s:property value="examPaper.ep_kscore" />
					&nbsp;&nbsp;主观题总分：
					<s:property value="examPaper.ep_tscore-examPaper.ep_kscore" />
				</div>
			</div>
			<div style="height: 92px; float: right; width: 81px;">
				<img src="images/exam/d_2.jpg" width="81" height="92" />
			</div>
			<div class="contentcenter">
				<div class="title">
					<s:property value="examPaper.title" />
				</div>
				<br />
				<div
					style="padding: 2px; border: 0px; width: 788px; margin: 4px 8 4 8; margin-top: 0px; padding-left: 10px; font-size: 12px;">
					<s:property value="examPaper.description" />
					<br />
					<hr width="760" size="1" noshade="noshade" class="line" />
				</div>
				<div>
					<p align="center">
						<input type="button" id="b_t_a" class="inputover" value="全部试题"
							onclick="quizpaper.showAllBlocks()" />
						<s:iterator value="examPaper.epBlocks" status="stepb">
							<input type="button" id="b_t_<s:property value="#stepb.index"/>"
								class="input" value="<s:property value="title" />"
								onclick="quizpaper.showBlocks(<s:property value="#stepb.index"/>)" />
						</s:iterator>
					</p>
				</div>
			</div>
			<form action="examprac_submit.action" method="post" name="quizform"
				id="quizform">
				<wysLib:examPrac></wysLib:examPrac>
				<s:hidden name="examPaper.id" />
				<div>
					<p align="center">
						<input type="button" id="b_t2_a" class="inputover" value="全部试题"
							onclick="quizpaper.showAllBlocks()" />
						<s:iterator value="examPaper.epBlocks" status="stepb2">
							<input type="button"
								id="b_t2_<s:property value="#stepb2.index"/>" class="input"
								value="<s:property value="title" />"
								onclick="quizpaper.showBlocks(<s:property value="#stepb2.index"/>)" />
						</s:iterator>
					</p>
				</div>
				<br />
				<div align="center">
					<input type="button" value="确认交卷"
						onclick="if(confirm('确定交卷？')) quizform.submit();"
						class="regbutton3" />
				</div>
			</form>
		</div>
	</body>
</html>