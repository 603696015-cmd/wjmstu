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
		<title>中国食品安全培训网</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<meta content="MSHTML 6.00.2900.5921" name=GENERATOR />
		<LINK rel=stylesheet type=text/css
			href="search/main.css">
		<LINK rel=stylesheet type=text/css
			href="search/exam.css">

		<STYLE type=text/css>
.STYLE1 {
	COLOR: #ffffff
}

.STYLE2 {
	FONT-SIZE: 14px;
	FONT-WEIGHT: bold
}
A {
	TEXT-DECORATION: none
}

A:hover {
	COLOR: #ff00ff
}

A:link {
	COLOR: #205064
}

A:visited {
	COLOR: #205064
}

BODY {
	SCROLLBAR-ARROW-COLOR: #ff0000;
	SCROLLBAR-FACE-COLOR: #f7fef6;
	SCROLLBAR-DARKSHADOW-COLOR: #cdcbe2;
	FONT-FAMILY: 宋体;
	BACKGROUND: url(Skin/2/bg01.gif) center 50%;
	SCROLLBAR-HIGHLIGHT-COLOR: #b4c4d4;
	COLOR: #000000;
	SCROLLBAR-SHADOW-COLOR: #b4c4d4;
	FONT-SIZE: 9pt;
	SCROLLBAR-TRACK-COLOR: #f7fef6;
	TEXT-DECORATION: none;
	SCROLLBAR-3DLIGHT-COLOR: #f7fef6
}

TD {
	LINE-HEIGHT: 150%;
	FONT-FAMILY: 宋体;
	COLOR: #003366;
	FONT-SIZE: 9pt
}

INPUT {
	BORDER-BOTTOM: #b2c2d7 1px solid;
	BORDER-LEFT: #b2c2d7 1px solid;
	BACKGROUND-COLOR: #dfe6ee;
	HEIGHT: 18px;
	COLOR: #205064;
	FONT-SIZE: 9pt;
	BORDER-TOP: #b2c2d7 1px solid;
	BORDER-RIGHT: #b2c2d7 1px solid
}

.InputMichael {
	BORDER-BOTTOM: #082d71 0px solid;
	BORDER-LEFT: #082d72 0px solid;
	BACKGROUND-COLOR: #f7f9fd;
	HEIGHT: 21px;
	COLOR: #082d71;
	FONT-SIZE: 9pt;
	BORDER-TOP: #082d71 0px solid;
	BORDER-RIGHT: #082d71 0px solid
}

BUTTON {
	HEIGHT: 20px;
	FONT-SIZE: 9pt
}

SELECT {
	HEIGHT: 20px;
	FONT-SIZE: 9pt
}

.border {
	BORDER-BOTTOM: #ffffff 1px solid;
	BORDER-LEFT: #ffffff 1px solid;
	BACKGROUND: #edf1f6;
	BORDER-TOP: #ffffff 1px solid;
	BORDER-RIGHT: #ffffff 1px solid
}

.border2 {
	BORDER-BOTTOM: #b2c2d7 1px solid;
	BACKGROUND: #dfe6ee
}

.title_txt {
	BACKGROUND: #000000
}

.title {
	BACKGROUND: url(Skin/2/title_left.gif);
	HEIGHT: 22px
}

.tdbg {
	BACKGROUND: #dfe6ee
}

.txt_css {
	BACKGROUND: url(Skin/2/txt_css.gif);
	HEIGHT: 36px
}

.title_lefttxt {
	FILTER: DropShadow(Color =   #ffffff, OffX =   1, OffY =   1, Positive =   1)
		;
	COLOR: #333333
}

.title_left {
	BACKGROUND: url(Skin/2/title_left.gif);
	HEIGHT: 22px
}

.tdbg_left {
	BACKGROUND: #dfe6ee
}

.title_left2 {
	BACKGROUND: #dfe6ee
}

.tdbg_left2 {
	
}

.tdbg_leftall {
	BACKGROUND: #dfe6ee;
	BORDER-RIGHT: #b2c2d7 1px solid
}

.title_maintxt {
	FILTER: DropShadow(Color =   #ffffff, OffX =   1, OffY =   1, Positive =   1)
		;
	COLOR: #000000
}

.title_main {
	BACKGROUND: url(Skin/2/title_main.gif);
	HEIGHT: 22px
}

.tdbg_main {
	LINE-HEIGHT: 100%;
	BACKGROUND: url(Skin/2/tdbg_main2.GIF)
}

.title_main2 {
	BACKGROUND: url(Skin/2/maintop.gif);
	HEIGHT: 202px
}

.tdbg_main2 {
	BACKGROUND: url(Skin/2/tdbg_main3.GIF);
	HEIGHT: 27px
}

.tdbg_mainall {
	BACKGROUND: url(Skin/2/kt01-p1.GIF)
}

.title_righttxt {
	FILTER: DropShadow(Color =   #ffffff, OffX =   1, OffY =   1, Positive =   1)
		;
	COLOR: #333333
}

.title_right {
	BACKGROUND: url(Skin/2/title_left.gif);
	HEIGHT: 22px
}

.tdbg_right {
	BACKGROUND: #ffffff
}

.title_right2 {
	BACKGROUND: url(Skin/2/title_main1.gif);
	HEIGHT: 22px
}

.tdbg_right2 {
	BACKGROUND: url(Skin/2/title_main2.gif);
	HEIGHT: 22px
}

.tdbg_rightall {
	BACKGROUND: #b4c4d4
}

.topborder {
	BACKGROUND-IMAGE: url(Skin/2/topborder.gif)
}

.nav_top {
	BACKGROUND-IMAGE: url(Skin/2/nav_top.gif);
	HEIGHT: 25px
}

.nav_main {
	LINE-HEIGHT: 150%;
	BACKGROUND: url(Skin/2/nav_main.jpg);
	HEIGHT: 125px
}

.nav_bottom {
	BACKGROUND-IMAGE: url(Skin/2/bottombg_2.gif)
}

.nav_menu {
	BACKGROUND: url(Skin/2/nav_menu.gif);
	HEIGHT: 24px
}

.menu {
	BORDER-BOTTOM: 1px;
	BORDER-LEFT: 1px;
	BACKGROUND-COLOR: #b2c2d7;
	WIDTH: 90%;
	BORDER-TOP: 1px;
	BORDER-RIGHT: 1px
}

TD.MenuBody {
	BACKGROUND-COLOR: #f6f6f6
}

A.linkStyle:link {
	FILTER: dropshadow(color =   #ffffff, offx =   2, offy =   2, positive =   true)
		;
	FONT-VARIANT: normal;
	COLOR: #003366;
	FONT-SIZE: 12px;
	FONT-WEIGHT: normal;
	TEXT-DECORATION: none
}

A.linkStyle:active {
	FILTER: dropshadow(color =   #ffffff, offx =   2, offy =   2, positive =   true)
		;
	FONT-VARIANT: normal;
	COLOR: #003366;
	FONT-SIZE: 12px;
	FONT-WEIGHT: normal;
	TEXT-DECORATION: none
}

A.linkStyle:visited {
	FILTER: dropshadow(color =   #ffffff, offx =   2, offy =   2, positive =   true)
		;
	FONT-VARIANT: normal;
	COLOR: #003366;
	FONT-SIZE: 12px;
	FONT-WEIGHT: normal;
	TEXT-DECORATION: none
}

A.linkStyle:hover {
	FILTER: dropshadow(color =   #ffffff, offx =   2, offy =   2, positive =   true)
		;
	FONT-VARIANT: normal;
	COLOR: #003366;
	FONT-SIZE: 12px;
	FONT-WEIGHT: normal;
	TEXT-DECORATION: underline
}

A.linkClass:link {
	FILTER: dropshadow(color =   #ffffff, offx =   2, offy =   2, positive =   true)
		;
	FONT-VARIANT: normal;
	COLOR: #ffffff;
	FONT-SIZE: 12px;
	FONT-WEIGHT: normal;
	TEXT-DECORATION: none
}

A.linkClass:active {
	FILTER: dropshadow(color =   #ffffff, offx =   2, offy =   2, positive =   true)
		;
	FONT-VARIANT: normal;
	COLOR: #ffffff;
	FONT-SIZE: 12px;
	FONT-WEIGHT: normal;
	TEXT-DECORATION: none
}

A.linkClass:visited {
	FILTER: dropshadow(color =   #ffffff, offx =   2, offy =   2, positive =   true)
		;
	FONT-VARIANT: normal;
	COLOR: #ffffff;
	FONT-SIZE: 12px;
	FONT-WEIGHT: normal;
	TEXT-DECORATION: none
}

A.linkClass:hover {
	FILTER: dropshadow(color =   #ffffff, offx =   2, offy =   2, positive =   true)
		;
	FONT-VARIANT: normal;
	COLOR: #ff0000;
	FONT-SIZE: 12px;
	FONT-WEIGHT: normal;
	TEXT-DECORATION: none
}
</STYLE>
	<SCRIPT type="text/javascript">
	    window.onload=function(){
		   //if (window.screen){ 
			//	window.moveTo(0, 0);
		   // 	window.resizeTo(screen.availWidth,screen.availHeight);
		   // }
	    }
	</SCRIPT>
	</HEAD>
	<BODY style="background:#73a2ce">
		<P>
			&nbsp;
		</P>
		<P>
			&nbsp;
		</P>
		<TABLE border=1 cellSpacing=0 cellPadding=20 width=700 align=center>
			<TBODY>
				<TR>
					<TD bgColor=#205e8e>
						<TABLE border=0 cellSpacing=0 cellPadding=5 width="100%">
							<TBODY>
							<TR>
									<TD bgColor=#ffffff align="center">
										<h3> ${questionart.title }</h3>
									</TD>
								</TR>
								<TR>
									<TD bgColor=#ffffff align="left">
										内容： ${questionart.content }
									</TD>
								</TR>
							</TBODY>
						</TABLE>
						<BR>
						<TABLE border=0 cellSpacing=0 cellPadding=10 width=340
							bgColor=#ff9900 align=center>
							<TBODY>
								<TR>
									<TD width=334>
										<DIV align=center>
											<P class="STYLE1 STYLE2" align=center>
												你已确定上述内容为此题答案,请点击"关闭"按钮，完成该题操作
											</P>
											<P>
												<INPUT onclick=window.parent.closeFrame(); value=关闭 type=button name=Submit>
											</P>
										</DIV>
									</TD>
								</TR>
							</TBODY>
						</TABLE>
						<BR>
					</TD>
				</TR>
				<TR>
					<TD height=2></TD>
				</TR>
			</TBODY>
		</TABLE>
	
	</body>
</html>
