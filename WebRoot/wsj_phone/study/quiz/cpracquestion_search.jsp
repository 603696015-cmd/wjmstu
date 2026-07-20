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
		<LINK 
rel=stylesheet type=text/css href="search/main.css"><LINK rel=stylesheet 
type=text/css href="search/exam.css"><LINK rel=stylesheet type=text/css 
href="search/css4.css"><LINK rel=stylesheet type=text/css 
href="search/menu1.css">
<STYLE type=text/css>BODY {
	LINE-HEIGHT: 17px; FONT-SIZE: 12px
}
TD {
	LINE-HEIGHT: 17px; FONT-SIZE: 12px
}
A:link {
	FONT-FAMILY: ; COLOR: blue; TEXT-DECORATION: none
}
A:visited {
	FONT-FAMILY: ; COLOR: blue; TEXT-DECORATION: none
}
A:active {
	FONT-FAMILY: ; COLOR: #004080; TEXT-DECORATION: none
}
A:hover {
	COLOR: #ef9c00; TEXT-DECORATION: none
}
MARQUEE {
	COLOR: #ff0000; FONT-WEIGHT: bold
}
.STYLE1 {
	COLOR: #0000ff
}
.STYLE2x {
	LINE-HEIGHT: 1.3em; COLOR: #ffffff; FONT-SIZE: 21px; FONT-WEIGHT: bold
}
.STYLE2xx {
	COLOR: #000000; FONT-SIZE: 12px
}
.STYLE3 {
	COLOR: #ff0000; FONT-SIZE: 14px; FONT-WEIGHT: bold
}
</STYLE>
		
	</HEAD>
	<BODY oncontextmenu='return false' ondragstart='return false' onselectstart ='return false' onselect='document.selection.empty()' oncopy='document.selection.empty()' onbeforecopy='return false' class=ex4 leftMargin=0 topMargin=0
		bgColor=#ffffff>
		<DIV align=center>
			<TABLE border=0 cellSpacing=2 cellPadding=0 width=778>
				<TBODY>
					<TR>
						<TD class=list1 height=50 align=middle>
							<div style="text-align:center;line-height:30px;">
								<font style="font-size:14px;">
									  姓名：<font color="red"><s:property value="#session.realname" /></font>&nbsp;&nbsp;
									  身份证号码：<font color="red"><s:property value="#session.shenfenzheng" /></font>&nbsp;&nbsp;
									  部门：<font color="red"><s:property value="#session.myDepName" /></font>
								</font>
							</div>
						</TD>
					</TR>
					<TR>
						<TD align=left>
							<!--<a href="joinexam.asp"  class="link1">确认返回</a>-->
							<BR>
							<TABLE border=1 cellSpacing=0 borderColor=#cccccc cellPadding=5
								width="100%" bgColor=#ff9900>
								<TBODY>
									<TR>
										<TD bgColor=#ff0000> 
											<SPAN class=STYLE2x>考试要求：请搜索以下标题的文章<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请搜索标题为：<s:property value="question.answers[0]"/></SPAN>
										</TD> 
									</TR>
								</TBODY>
							</TABLE>
						</TD>
					</TR>
					<TR>
						<TD align=middle>
							<HR>
						</TD>
					</TR>
				</TBODY>
			</TABLE>
			<STYLE type=text/css>
				.search {
					BACKGROUND-IMAGE: url(new_images/search_backgroud.gif);
					BORDER-BOTTOM: #333333 1px solid;
					BORDER-LEFT: #333333 1px solid;
					BORDER-TOP: #333333 1px solid;
					BORDER-RIGHT: #333333 1px solid
				}
				</STYLE>

			<TABLE id=Table_01 border=0 cellSpacing=0 cellPadding=0 width=801
				align=center height=1560>
				<TBODY>
					<TR>
						<TD colSpan=4>
							<IMG alt="" src="search/newsearch_01.gif" width=801
								height=176>
						</TD>
					</TR>
					<TR>
						<TD rowSpan=2>
							<IMG alt="" src="search/newsearch_02.gif" width=202
								height=1384>
						</TD>
						<TD>
							<IMG alt="" src="search/newsearch_03.gif" width=107
								height=35>
						</TD>
						<TD>
							<FORM id=frmMySearch method=post name=frmMySearch
								action=cpracquestion_search.action>
								<TABLE border=0 cellSpacing=0 cellPadding=0 width=309>
									<TBODY>
										<TR align=left>
											<TD vAlign=bottom>
												<INPUT class=search  onpaste="return false;" size=25 name=questionart.title>
													<s:hidden name="question.id" />
													<s:hidden name="question.qtype" />
													<s:hidden name="question.epblock.id" />
													<s:hidden name="myExamPaper.id" />
													<!-- <s:hidden name="question.answer" /> -->
													<INPUT id=btnGo value=" : 搜索 :" type=submit name=btnGo>
											</TD>
										</TR>
									</TBODY>
								</TABLE>
							</FORM>
						</TD>
						<TD rowSpan=2>
							<IMG alt="" src="search/newsearch_05.gif" width=183
								height=1384>
						</TD>
					</TR>
					<TR>
						<TD colSpan=2>
							<IMG alt="" src="search/newsearch_06.gif" width=416
								height=1349>
						</TD>
					</TR>
				</TBODY>
			</TABLE>
		</DIV>
	
	</body>
</html>
