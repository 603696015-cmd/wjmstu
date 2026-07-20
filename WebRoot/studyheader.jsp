<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
  <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>头</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<STYLE type=text/css>
#IssueList {
	MARGIN: 0px;
	OVERFLOW: hidden;
	WIDTH: 320px;
	COLOR: #333333;
	LIST-STYLE-TYPE: none;
	HEIGHT: 24px;
	padding: 0px;
	padding-top:4px;
}
#IssueList LI {
	FONT-SIZE: 13px; LINE-HEIGHT: 20px
}
#apDiv1 {
	Z-INDEX: 1; LEFT: 104px; WIDTH: 835px; POSITION: absolute; TOP: 1242px; HEIGHT: 48px
}
#apDiv_hjl {
	Z-INDEX: 1; LEFT: 127px; VISIBILITY: hidden; WIDTH: 849px; POSITION: absolute; TOP: 1240px; HEIGHT: 51px
}
.bai13 {
	FONT-WEIGHT: normal; FONT-SIZE: 13px; COLOR: #ffffff; LINE-HEIGHT: 24px; FONT-FAMILY: "宋体"; TEXT-ALIGN: left; TEXT-DECORATION: none
}
.bai13 A {
	FONT-WEIGHT: normal; FONT-SIZE: 13px; COLOR: #ffffff; LINE-HEIGHT: 24px; FONT-FAMILY: "宋体"; TEXT-DECORATION: none
}
.bai13 A:link {
	FONT-WEIGHT: normal; FONT-SIZE: 13px; COLOR: #ffffff; LINE-HEIGHT: 24px; FONT-FAMILY: "宋体"; TEXT-DECORATION: none
}
.bai13 A:hover {
	COLOR: #ffffff; TEXT-DECORATION: underline
}
</STYLE>
	
	</HEAD>

	<body>
		<div id="header">
			<div class="logo">
				<a href="#" target="_blank"><img src="images/name2.jpg"/></a>
			</div>
			<div id="info_bar">
				用户名：
				<strong class="font_arial white"><s:property
						value="#session.username" /> </strong>，角色：
				<s:property value="#session.roleName" />
				|
				<a href="logout.action" class="white">退出登录</a> |
				<a href="index.action" target="_blank" class="white">网站首页</a>
			</div>
			<div id="menu">
				<ul>
				<li><DIV id=IssueList>
               <s:iterator value="zxNotices">
               <TABLE width="98%" 
                  border=0 cellPadding=0 cellSpacing=0 class=lan13>
                <TBODY>
                  <TR>
                    <TD height=20 align="left" class="bai13"><A  style="background: none;" href="newsIndexView.action?news.id=<s:property value="id"/>" 
                        target=_blank><s:property value="title" /></A> </TD>
                  </TR>
                </TBODY>
				  </TABLE>
			   </s:iterator>
               	</DIV></li>
					<li>
						<a href="study.action" id="menu_student" title="学习中心"><span>学习中心</span>
						</a>
					</li>
					<s:if test="#session.role!=4">
					<li>
						<a href="commonman.action" id="menu_common"  title="管理中心"><span>管理中心</span>
						</a>
					</li>
					</s:if>
					<!--<li>
						<a href="questionman.action" id="menu_question" title="题库管理"><span>题库管理</span>
						</a>
					</li>
					<li>
						<a title="知识管理" id="menu_knowledge" href="knowledgeman.action"><span>知识管理</span> </a>
					</li>
					<li>
						<a title="资源管理" id="menu_stuff"  href="stuffman.action"><span>素材管理</span> </a>
					</li>
					<li>
						<a href="../sopia_forum/forums/list.page" target="_blank" title="模块管理"><span>论坛</span> </a>
					</li>
					
				--></ul>
			</div>
		</div>
		 <SCRIPT type=text/javascript>
<!--
(function(ul, delay, speed, lineHeight) { 
var slideBox = (typeof ul == 'string')?document.getElementById(ul):ul; 
var delay = delay||1000; 
var speed=speed||20; 
var lineHeight = lineHeight||20; 
var tid = null, pause = false; 
var start = function() { 
tid=setInterval(slide, speed); 
    } 
var slide = function() { 
     if (pause) return; 
     slideBox.scrollTop += 2; 
     if (slideBox.scrollTop % lineHeight == 0) { 
         clearInterval(tid); 
         slideBox.appendChild(slideBox.getElementsByTagName('table')[0]); 
         slideBox.scrollTop = 0; 
         setTimeout(start, delay); 
        } 
    } 
    slideBox.onmouseover=function(){pause=true;} 
    slideBox.onmouseout=function(){pause=false;} 
    setTimeout(start, 2000); 

})('IssueList', 2000, 32, 20);//停留时间，相对速度（越小越快）,每次滚动多少，最好和Li的Line-height一致。 
-->
</SCRIPT> 
	</body>
</html>
