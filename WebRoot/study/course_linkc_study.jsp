<%@ page language="java" pageEncoding="UTF-8"%>
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
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<base href="<%=basePath%>">
		<TITLE>学习课程（外部）--<s:property value="course.name" />
		</TITLE>
		<LINK href="css/bofang2.css" type=text/css rel=stylesheet>
		<script type="text/javascript" src="js/course.js"></script>
		<script type="text/javascript" src="js/flexpaper.js"></script> 
		<script type="text/javascript" src="js/flexpaper_handlers.js"></script>
		<script type="text/javascript" src="js/study.js"></script>
<STYLE type=text/css>.jiangyi {
	PADDING-RIGHT: 8px; PADDING-LEFT: 8px; FONT-SIZE: 12px; PADDING-BOTTOM: 8px; PADDING-TOP: 8px; BACKGROUND-COLOR: #ffffff
}
.STYLE5 {
	COLOR: #ff0000
}
#menubox {
	BORDER-RIGHT: #26517b 0px solid; BORDER-TOP: #26517b 0px solid; BACKGROUND: #ffffff; MARGIN: 0px; BORDER-LEFT: #26517b 0px solid; WIDTH: 180px; BORDER-BOTTOM: #26517b 0px solid; HEIGHT: auto
}
BODY {
	MARGIN: 0px
}
.STYLE8 {
	FONT-WEIGHT: bold; FONT-SIZE: 14px
}
A {
	FONT-SIZE: 12px
}
.STYLE12 {
	FONT-SIZE: 12px; COLOR: #ffffff
}
</STYLE>
	</HEAD>
	<BODY onload="saveStudyinfo();document.getElementById('course_content').contentWindow.main_goto_page(<s:property value="myCPage.passtime"/>)" style="overflow: visible; padding: 0px; margin: 0px">
	<div style="position: absolute;border:solid 1px buttonface; width:400;height:300px;background: white;z-index: 300;display: none;" id="noteadd">
											<div style="width: 100%;background: #eeddaa"><span style="width:380">做笔记</span><span style="cursor:hand;width:15px;" onclick="closediv('noteadd')">X</span> </div>	
											<div style="width: 100%;height:100%" id="noteaddcontent"></div>
											</div>
											<div style="position: absolute;border:solid 1px buttonface; width:600;height:400px;background: white;z-index: 301;display: none;" id="notelist">
											<div style="width: 100%;background: #eeddaa"><span style="width:580">查看笔记</span><span style="cursor:hand;width:15px;" onclick="closediv('notelist')">X</span> </div>	
											<div style="width: 100%;height:100%" id="notelistcontent"></div>
											</div> 
	 <div id="message" style="display: none;"></div><SCRIPT type="text/javascript">
		querytime = <s:property value="coursePage.queryTime"/>;
		passtime = <s:property value="myCPage.passtime"/>;
		cpasstime = <s:property value="myCourse.passtime"/>;
		cpid=<s:property value="coursePage.id"/>;
		ispassed = <s:property value="myCPage.passed"/>;
		cid =<s:property value="course.id"/>;
		</SCRIPT> 
	<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
  <TBODY>
  <TR>
    <TD vAlign=top height=68>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR>
          <TD>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR>
                <TD vAlign=bottom align=middle 
                background=images/img/bfz_r1_c11.jpg>
                  <TABLE height=25 cellSpacing=0 cellPadding=0 width="100%">
                    <TBODY>
                    <TR>
                      <TD class=bt vAlign=center align=middle><s:property value="course.name" /></TD>
                      <TD vAlign=center align=middle width=450><A class=dh target="_blank" 
                        href="course_tandsp.action?course.id=${course.id }&course_sourse=0">讲师简介</A><SPAN 
                        class=STYLE12> &gt;&gt;</SPAN> <A class=dh target="_blank" 
                        href="course_tandsp.action?course.id=${course.id }&course_sourse=1">教学计划</A><SPAN 
                        class=STYLE12> &gt;&gt;</SPAN> <A class=dh target="_blank" 
                        href="practice_listInit.action?course.id=${course.id }">练习中心</A><SPAN 
                        class=STYLE12> &gt;&gt;</SPAN> <A class=dh 
                        style="cursor: hand" onclick="javascript:cnote_add_link	('${course.id }',this)">做笔记</A><SPAN 
                        class=STYLE12> &gt;&gt; </SPAN><A class=dh 
                         style="cursor: hand" onclick="javascript:cnote_list_link('${course.id }',this)">查看笔记</A> 
                        <SPAN class=STYLE12>&gt;&gt;</SPAN> <A class=dh 
                        href="study.action">学习中心</A></TD></TR></TBODY></TABLE></TD></TR>
              <TR>
                <TD background=images/img/t-5.jpg height=13><IMG height=13 
                  src="images/img/t-52.jpg" 
        width=180></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
		<iframe width="100%" height="100%" frameborder="0" id="course_content"
			src="http://<s:property value="cserver.url"/><s:property value="coursePage.page_url_"/>">
		</iframe>
	</BODY>
</HTML>
