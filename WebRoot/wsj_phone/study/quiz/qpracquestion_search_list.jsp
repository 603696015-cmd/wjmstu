<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<!-- saved from url=(0042)http://www.dnzs123.com/dazi/online_zh.html -->
<HTML xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>" />
		<title>中国食品安全培训网-练习</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<meta content="MSHTML 6.00.2900.5921" name=GENERATOR />
		<STYLE type=text/css>
BODY {
	LINE-HEIGHT: 17px;
	FONT-SIZE: 12px
}

TD {
	LINE-HEIGHT: 17px;
	FONT-SIZE: 12px
}

A:link {
	FONT-FAMILY: ;
	COLOR: blue;
	TEXT-DECORATION: none
}

A:visited {
	FONT-FAMILY: ;
	COLOR: blue;
	TEXT-DECORATION: none
}

A:active {
	FONT-FAMILY: ;
	COLOR: #004080;
	TEXT-DECORATION: none
}

A:hover {
	COLOR: #ef9c00;
	TEXT-DECORATION: none
}

MARQUEE {
	COLOR: #ff0000;
	FONT-WEIGHT: bold
}

.STYLE1 {
	COLOR: #0000ff
}

.STYLE2x {
	LINE-HEIGHT: 1.3em;
	COLOR: #ffffff;
	FONT-SIZE: 21px;
	FONT-WEIGHT: bold
}

.STYLE2xx {
	COLOR: #000000;
	FONT-SIZE: 12px
}

.STYLE3 {
	COLOR: #ff0000;
	FONT-SIZE: 14px;
	FONT-WEIGHT: bold
}
</STYLE>

	</HEAD>
	<body leftmargin="0" topmargin="0" class="ex4"
		 oncontextmenu='return false' ondragstart='return false' onselectstart ='return false' onselect='document.selection.empty()' oncopy='document.selection.empty()' onbeforecopy='return false'>
		<div align="center">
			<table width="778" border="0" cellspacing="2" cellpadding="0">
				<tr>
					<td height="50" align="center" class="list1">
						<strong><font color="#000000">姓名：<s:property
									value="#session.realname" />(<s:property
									value="#session.username" />)&nbsp;&nbsp;&nbsp; </font> </strong>
					</td>
				</tr>
				<tr>
					<td align="left">
						<!--<a href="joinexam.asp"  class="link1">确认返回</a>-->
						<br>
						<table width="100%" border="1" cellpadding="5" cellspacing="0"
							bordercolor="#CCCCCC" bgcolor="#FF9900">
							<tr>
								<td bgcolor="#FF0000">
									<span class="STYLE2x">考试要求：请搜索以下标题的文章<br>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;标题为：<s:property
											value="question.answers[0]" /> </span>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td align="center">
						<hr>
					</td>
				</tr>
				<tr>
					<td height="18" align="right" bgcolor="#EEEEEE">
						<div>
							<img src="images/topserarh.JPG">
							中国公安信息网搜索引擎为您找到共
							<strong style="Color: red"><span id="countaa"><s:property value="questionarts.size"/></span> </strong>
							条有效记录
						</div>
					</td>
				</tr>
				<tr>
					<td align="center">
						<br>
						<div align="left">
							<center>
								<p class="STYLE3">
									请在下面列表中，点击与考题相关的文章以确定该题答案
								</p>
								<form action="qpracquestioninit.action" name="q_form"
									method="post">
									<s:hidden name="question.id" />
									<s:hidden name="question.qtype" />
									<s:hidden name="question.epblock.id" />
									<s:hidden name="examRoom.id" />
									<s:hidden name="myExamPaper.id" />
									<s:hidden name="questionart.id" id="qarid"></s:hidden>
									<s:hidden name="question.stuAnswers" id="myan"></s:hidden>
									<input type="submit" name="Submit" value="返回搜索">
									<script type="text/javascript">
										function setAnswer(id,title){
											if( window.confirm('确定选择“'+title+"‘为答案？")){
												document.getElementById("myan").value=title;
												document.getElementById("qarid").value=id;
												q_form.action="qpracquestion_submit.action";
												q_form.submit();
											}
										}
									</script>
								</form>
							</center>
						</div>
					</td>
				</tr>
				<tr>
					<td align="center">
						<br>
						<div align="left">
							查找关键字："
							<s:property value="questionart.title" />
							"
						</div>
					</td>
				</tr>
				<s:iterator value="questionarts" status="st">
					<tr>
						<td>
							<div align="left">
								<br>
								<a href="" onclick="setAnswer(<s:property value="id"/>,'<s:property value="title"/>');return false;" class="link1" target="_self"><font class="baidu">
										<s:property value="title" /></font> </a>
								<br>
								<font class="STYLE2xx"><s:property value="qexplain" escape="false"/> </font>
							</div>
						</td>
					</tr>
				</s:iterator>
				<tr>
					<td>
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td bgcolor="#999999" height="1"></td>
							</tr>
							<tr>
								<td valign="middle" height=30 align="center" class="STYLE2xx">
									搜索引擎管理单位：公安部信息中心 | 联系电话：(010)65203893 (9111)3893
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<form action="qpracquestion_search.action" name="q_search" method="post">
				<s:hidden name="pN" id="pageNow"></s:hidden>
                <s:hidden name="pS"> </s:hidden>
                <s:hidden name="question.id" />
                <s:hidden name="questionart.title" />
				<s:hidden name="question.qtype" />
				<s:hidden name="question.epblock.id" />
				<s:hidden name="examRoom.id" />
				<s:hidden name="myExamPaper.id" />
			</form>
			<script>
				function page(i){
					document.getElementById("pageNow").value=i;
					q_search.submit();
				}
			  </script>
			<div align="center"><wysLib:page></wysLib:page></div>
		</DIV>
	
	</body>
</html>
