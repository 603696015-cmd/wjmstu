<%@ page language="java" pageEncoding="UTF-8"   %>
<%@ page language="java" pageEncoding="UTF-8"%>
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
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>证书</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<LINK href="elstudentman/zhengshu/gj.css" type=text/css rel=stylesheet>
		<SCRIPT language=JavaScript1.1
			src="elstudentman/zhengshu/commonscript.js"></SCRIPT>

		<STYLE type=text/css>
#apDiv1 {
	Z-INDEX: 1;
	LEFT: 11px;
	WIDTH: 1072px;
	POSITION: absolute;
	TOP: 16px;
	HEIGHT: 745px
}

#apDiv2 {
	Z-INDEX: 2;
	LEFT: 301px;
	WIDTH: 148px;
	POSITION: absolute;
	TOP: 359px;
	HEIGHT: 36px
}

#apDiv3 {
	Z-INDEX: 3;
	LEFT: 541px;
	WIDTH: 90px;
	POSITION: absolute;
	TOP: 355px;
	HEIGHT: 36px
}

#apDiv4 {
	Z-INDEX: 4;
	LEFT: 277px;
	WIDTH: 178px;
	POSITION: absolute;
	TOP: 597px;
	HEIGHT: 26px
}

#apDiv5 {
	Z-INDEX: 5;
	LEFT: 664px;
	WIDTH: 88px;
	POSITION: absolute;
	TOP: 589px;
	HEIGHT: 30px
}

#apDiv6 {
	Z-INDEX: 6;
	LEFT: 810px;
	WIDTH: 38px;
	POSITION: absolute;
	TOP: 589px;
	HEIGHT: 25px
}

#apDiv7 {
	Z-INDEX: 7;
	LEFT: 700px;
	WIDTH: 162px;
	POSITION: absolute;
	TOP: 486px;
	HEIGHT: 164px
}

@media Print {
	.Noprint {
		DISPLAY: none
	}
}

#apDiv8 {
	Z-INDEX: 8;
	LEFT: 510px;
	WIDTH: 43px;
	POSITION: absolute;
	TOP: 22px;
	HEIGHT: 21px
}
</STYLE>

		<META content="MSHTML 6.00.6000.21264" name=GENERATOR>
	</HEAD>
	<BODY>
		
<DIV id=apDiv1></DIV>
<DIV id=apDiv2><FONT 
style="FONT-WEIGHT: bold; FONT-SIZE: 24pt; COLOR: #000000; FONT-FAMILY: Verdana, 黑体, Helvetica, sans-serif"><s:property value="myClass.user.realname"/> </FONT></DIV>
<DIV id=apDiv3><FONT 
style="FONT-SIZE: 24pt; COLOR: #000000; FONT-FAMILY: Verdana, 黑体, Helvetica, sans-serif"><s:date name="myClass.endtime" format="yyyy"/></FONT></DIV>
<DIV id=apDiv4><FONT 
style="FONT-SIZE: 20pt; COLOR: #041b91; FONT-FAMILY: Verdana, 黑体, Helvetica, sans-serif"><s:date name="myClass.endtime" format="yyyyMMdd"/><s:property value="myClass.certificatenoStr"/></FONT></DIV>
<DIV id=apDiv5><FONT 
style="FONT-SIZE: 20pt; COLOR: #000000; FONT-FAMILY: Verdana, 黑体, Helvetica, sans-serif"><s:date name="myClass.endtime" format="yyyy"/></FONT></DIV>
<DIV id=apDiv6><FONT 
style="FONT-SIZE: 20pt; COLOR: #000000; FONT-FAMILY: Verdana, 黑体, Helvetica, sans-serif"><s:date name="myClass.endtime" format="MM"/> 
</FONT></DIV>
<DIV id=apDiv7><!--<IMG height=160 src="elstudentman/zhengshu/bjcezhang.gif" width=158>--></DIV>
<DIV id=apDiv8><INPUT class=Noprint onclick=javascript:print() type=button value=打印本证书></INPUT></DIV><IMG 
height=745 src="elstudentman/zhengshu/certImage.jpg" width=1072 border=0> 
	</BODY>
</HTML>
