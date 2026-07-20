<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<base href="<%=basePath%>">
		<title>查看我的答卷-练习</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<style type="text/css">
body {
	width: 100%;
	margin: 0px;
	padding: 0px;
	font-size: 14px;
	font-family: Arial, Helvetica, sans-serif;
	text-align: center;
}

.main {
	width: 795px;
}

h3 {
	text-align: center;
	width: 100%;
	margin: 0px;
	font-size: 25px;
	padding: 10px 0px 10px 0px;
}
h4{margin: 5px 0px 2px 0px;}
.quizinfo {
	border: 2px solid #122333;
	text-align: left;
	width: 610px;
}

.quizinfo .left,.right {
	float: left;
	width: 285px;
	padding: 5px 0px 10px 5px;
}

.info {
	color: #444;
	font-weight: bolder;
}

.info_ul {
	list-style-type: none;
	margin: 0px;
}

.info_ul li {
	padding: 3px 0px 1px 0px;
	color: #888888;
}
.quiz_detail{
	border: 2px solid #122333;
	text-align: left;
	font-size:12px;
	width: 600px;
	padding: 5px;
}
.block_name{
	font-weight: bolder;
}
.block_desc{
	padding-left: 20px;
}
.question{padding-left:20px;}
.question .sort{width:22px;float:left;}
.question .content{padding-left:22px;}
.answer{color: green;}
div,p{margin: 0px;padding:0px;}
.answer{padding-top:8px;}
.bottom{margin:10px auto 10px auto;}
.bottom a{background: #ff9933;padding: 3px;}
</style>
	</HEAD>
	<body style=" background-image:url(images/paperviewall_bg.jpg);">
		<div class="main">
			<h3>
				考试情况查询
			</h3>
			<div class="quizinfo">
            <table width="100%" border="0" cellspacing="1" cellpadding="0">
  <tr bgcolor="#CCFFFF">
    <td width="50%"><span class="info">试卷名称：</span>
							<s:property value="examPaper.title" /></td>
    <td><span class="info">出卷人：</span><s:property value="examPaper.elUser.realname"/></td>
  </tr>
  <tr bgcolor="#FFFFFF">
    <td width="50%"><span class="info">练习时间始：</span>  <s:date name="myExamPaper.begintime" format="yyyy-MM-dd HH:mm:ss"/></td>
    <td><span class="info"> 练习时间止：</span> <s:date name="myExamPaper.endtime" format="yyyy-MM-dd HH:mm:ss"/></td>
  </tr>
  <tr bgcolor="#CCFFFF">
    <td width="50%"><span class="info">答题时间：</span>
							<s:property value="examPaper.during" />
							分钟</td>
    <td><span class="info"> 考生耗时：</span> <s:property value="myExamPaper.passTimeStr"/></td>
  </tr>
  <tr bgcolor="#FFFFFF">
    <td width="50%"><span class="info">试卷总分：</span>
							<s:property value="examPaper.ep_tscore" /></td>
    <td><span class="info"> 通过分数：</span> <s:property value="examPaper.passScore"/></td>
  </tr>
  <tr bgcolor="#CCFFFF">
    <td width="50%"><span class="info">考生姓名：</span>
							<s:property value="myExamPaper.tester.realname" /></td>
    <td><span class="info"> 考生成绩：</span>
		<s:property value="myExamPaper.myScore" /></td>
  </tr>
  <tr bgcolor="#FFFFFF">
    <td width="50%"><span class="info">客观题得分：</span><s:property value="examPaper.mepKscore" /></td>
    <td bgcolor="#FFFFFF"><span class="info"> 主观题得分：</span>
		<s:property value="examPaper.mepZscore" /></td>
  </tr>
  <tr bgcolor="#CCFFFF">
    <td width="50%"><span class="info">身份证号：</span><s:property value="myExamPaper.tester.shenfenzheng"/></td>
    <td><span class="info"> 通过考试：</span>
	    <s:if test="myExamPaper.ispassed==1">是</s:if><s:else>否</s:else></td>
  </tr>
</table>

				<div style="clear: both;"></div>
			</div>
			<h4>
				各大题得分情况
			</h4>
			<table width="600px" border="0" cellpadding="0" cellspacing="1" background="#000" bgcolor="#000" style="font-size: 12px;">
				<tr>
					<s:iterator value="examPaper.epBlocks" status="stepb">
						<td align="center">
						 <s:property value="title" /> 
						</td>
					</s:iterator>
				</tr>
				<tr>
					<s:iterator value="examPaper.epBlocks" status="stepb">
						<td align="center">
							 <s:property value="myscore" /> 
						</td>
					</s:iterator>
				</tr>
			</table>
			<h4>
				详细情况
	    </h4>
			<div class="quiz_detail">
				<wysLib:quizPaperallview></wysLib:quizPaperallview>
			</div>
			<div class="bottom"><a style="color:white;text-decoration: none;" href="javascript:window.close()" >关闭窗口</a>&nbsp;&nbsp;&nbsp;<a style="color:white;text-decoration: none;" href="javascript:window.print();">打&nbsp;&nbsp;&nbsp;印</a></div>
			<!-- 客观题总分：
					<s:property value="examPaper.ep_kscore" />
					客观题得分：
					<s:property value="examPaper.mepKscore" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 主观题总分：
					<s:property value="examPaper.ep_tscore-examPaper.ep_kscore" />
					主观题得分：
					<s:property value="examPaper.mepZscore" /> -->

		</div>
	</body>
</HTML>