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
<style>
body,div,ul,li,img,a,h2,h1,h3,p,input,form,dl,dt,dd {margin:0;padding:0;border:0;}
.STYLE1 {
	font-size: 12px;
	color: #FF0000;
}
body {
	background-color: #BBD8FF;
}
.STYLE2 {
	font-size: 12px;
	color:black;
}
</style>
<STYLE type=text/css>
BODY {
	FONT-SIZE: 12px;
	WORD-BREAK: break-all;
	HEIGHT: 100%;
	WORD-WRAP: break-word;
	MARGIN: 0px
}

.bt {
	FONT-SIZE: 23px;
	font-weight: bolder; COLOR : #ffffff;
	LINE-HEIGHT: 26px;
	FONT-FAMILY: "黑体";
	COLOR: #ffffff;
}

.STYLE5 {
	color: #0000FF;
	font-size: 12px;
}

A.LI:link {
	FONT-SIZE: 12px;
	COLOR: #ff0000;
	TEXT-DECORATION: none
}

A.LI:visited {
	FONT-SIZE: 12px;
	COLOR: #ff0000;
	TEXT-DECORATION: none
}

A.LI:hover {
	FONT-SIZE: 12px;
	COLOR: #ff0000;
	TEXT-DECORATION: underline
}

A.LI:active {
	FONT-SIZE: 12px;
	COLOR: #ff0000;
	TEXT-DECORATION: underline
}

.leibie {
	PADDING-RIGHT: 20px;
	FONT-SIZE: 12px;
	OVERFLOW: auto;
	WIDTH: 100%;
	PADDING-TOP: 0px;
	TEXT-ALIGN: right
}

.unnamed1 {
	FONT-SIZE: 12px;
	LINE-HEIGHT: 24px;
	FONT-FAMILY: "宋体"
}

.STYLE8 {
	FONT-WEIGHT: bold;
	FONT-SIZE: 14px
}

.STYLE10 {
	FONT-SIZE: 12px;
	LINE-HEIGHT: 24px
}
</STYLE>
	<head>
		<META http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>" target="_self" />
		<title>课程学习页--</title>
		
		
		<link href="css/study_csp.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/course.js"></script>
		<script type="text/javascript" src="js/CourseStudy.js"></script>
		<script type="text/javascript" src="js/flexpaper.js"></script> 
		<script type="text/javascript" src="js/flexpaper_handlers.js"></script>
		<script type="text/javascript" src="js/cvideo/cvideoWrite.js"></script>
		<script type="text/javascript" src="js/jquery-1.2.6.js"></script>
		<script type="text/javascript" src="js/jquery.funkyUI.js"></script>

		<script type="text/javascript">
			var _cpst;
			var needsetCp = true;
			var pass = <s:property value="myCPage.passed"/>;
			var passtime = <s:property value="myCPage.passtime"/>;
			var type = <s:property value="coursePage.type"/>;
			var pageUrl='<s:property value="coursePage.page_url_Encoder"/>';
			var classid = <s:property value="course.classid"/>;
			var courseid=<s:property value="course.id"/>;
			var coursePageid = <s:property value="coursePage.id"/>;
			var during=<s:property value="myCPage.cpage.during"/>;
			var queryTime=<s:property value="coursePage.queryTime"/>;
			var passtime2=<s:property value="myCPage.passtime2"/>;
			var studyCourseRecordId=<s:property value="studyCourseRecordId"/>;
			var passed2 = <s:property value="myCPage.passed2"/>;
			var studyinfo_time = <s:property value="#session.studyinfo_time"/>;
			
			
			window.onbeforeunload=function(){
				if(<s:property value="coursePage.getcredit"/>!=1){
					if(_cpst.passtime2>=<s:property value="myCPage.cpage.during"/>*60){
						if(_cpst.passed2==0){
							alert("你还有考试没过！");
						}
					}
				}
				//window.event.returnValue="确定退出本次学习？";
			}
			window.onunload=function(){
				_cpst.exitStudy(); 
			}
			
			
			function study_record(){
				 width=760;
				 height=426;
				 //var cpageid="<s:property value='coursePage.id'/>";
				 var courseId="<s:property value='course.id'/>";
				 var classid="<s:property value='course.classid'/>";
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				  window.showModalDialog("displayStudyCpageInfo.action?course.id="+courseId+"&course.classid="+classid+"&x="+Math.random(),null,sFeature); 
			}
			
		
			
			
		</script>
	</HEAD>
	<body  onLoad="myload('<s:property value="status"/>');" style="background-color: #BBD8FF;">
	<div id="overlay"></div>

<div id="div" style="background:#BCD8FD;width: 761px;height:426px; position:absolute;left:50%;top:50%;margin-left:-380px;margin-top:-210px;border: 1px dashed #666; display:none;">
<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
   	  <td width="84%" align="left" ><strong>本章小节</strong></td>
        <td align="right"><a href="#" onClick="document.getElementById('div').style.display=(document.getElementById('div').style.display=='none')?'':'none';return false;"/><strong>关闭</strong></a></td>
  </tr>
</table>
<hr/>
	<ul>
			<s:iterator value="myCPages" id="cp">
				<LI style="font-size: 14px; list-style-type:disc; margin-left:20px;">
					
					&nbsp;<A style="text-decoration:underline;" href="course_study.action?coursePage.id=<s:property value="cpage.id" />&course.id=<s:property value="course.id"/>&course.classid=<s:property value="course.classid"/>">
						<s:property value="cpage.title" /> </A>
					
				</LI>
			</s:iterator>
	<%-- <s:iterator value="myCPages" status="statu">
		<tr>
			<td width="60" height="30" align="center" >
				<s:property value="#statu.count"/>								</td>
			<td height="30" align="center" >
				<a style="color:#cc0099;" href="course_study.action?coursePage.id=<s:property value="cpage.id" />&course.id=<s:property value="course.id"/>&course.classid=<s:property value="course.classid"/>">
					<s:property value="cpage.title"/>
				</a>								</td>
	</tr>
	</s:iterator>
	--%>
</div>

<div id="div1" style="background:#BCD8FD;width: 761px;height:426px; position:absolute;left:50%;top:50%;margin-left:-380px;margin-top:-210px;border: 1px dashed #CCCCCC; display:none;">
<table border="0" cellpadding="0" cellspacing="0" width="100%" >
	<tr>
   	  <td width="84%" align="left"><strong>课程简介</strong></td>
        <td align="right"><a href="#" onClick="document.getElementById('div1').style.display=(document.getElementById('div1').style.display=='none')?'':'none';return false;"/><strong>关闭</strong></a></td>
  </tr>
</table>
<hr/>
	
	<s:property value='course.description'/>
</div>

<!--<input size="20" type="button" onclick="document.getElementById('div').style.display=(document.getElementById('div').style.display=='none')?'':'none'"  value="button" />-->

	<p>&nbsp;</p>
	    <p>&nbsp;</p>
	    <table width="977" height="557" border="0" align="center" cellpadding="0" cellspacing="0" background="images/images1108/xuexibg.jpg">
          <tr>
            <td height="48" valign="top" style="padding-left:590px;padding-top:20px;color:white;font-size:16px;font-weight:bold;"><s:property value="course.name"/></td>
          </tr>
          <tr>
            <td><table width="977" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="103" height="444" valign="bottom"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="74" align="center" valign="middle">
                      <a  href="" onClick="document.getElementById('div').style.display=(document.getElementById('div').style.display=='none')?'':'none';return false;" ><img src="images/images1108/xuexi01.jpg" width="60" height="62"></a>
                      </td>
                    </tr>
                    <tr>
                      <td height="74" align="center" valign="middle">
                      <a href="study_kcjy.action?coursePage.id=<s:property value='coursePage.id'/>" target="_blank"><img src="images/images1108/xuexi02.jpg" width="60" height="62"></a>
                      </td>
                    </tr>
                    <tr>
                      <td height="74" align="center" valign="middle">
                      <a href="practice_listInit.action?course.id=<s:property value='course.id'/>" target="_blank"> <img src="images/images1108/xuexi03.jpg" width="60" height="62"></a>
                      </td>
                    </tr>
                    <tr>
                      <td height="74" align="center" valign="middle">
                       <a href="forumListByBlockid.action?fblock.id=<s:property value='course.forumid'/>" target="_blank"><img src="images/images1108/xuexi04.jpg" width="60" height="62"></a>
                      </td>
                    </tr>
                    <tr>
                      <td height="74" align="center" valign="middle">
                      <a href="course_study_notelist.action?course.id=<s:property value='course.id'/>&elclass.id=<s:property value='course.classid'/>" target="_blank"> <img src="images/images1108/xuexi05.jpg" width="60" height="62"></a>
                      </td>
                    </tr>
                    <tr>
                      <td height="74" align="center" valign="middle">
                      <a href="#" onClick="study_record();return false;"> <img src="images/images1108/xuexi06.jpg" width="60" height="62"></a>
                      </td>
                    </tr>
                </table></td>
                <td align="center" valign="top" style="padding-top:12px;">
<!--                <div style="height:426px;width: 761px;" id="page_file"></div>-->
					<div id="ifr" style="height:426px;width: 761px;"> </div>
                </td>
                <td width="105"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="74" align="center" valign="middle">
                       <a href="knowledge_center_list.action" target="_blank"><img src="images/images1108/xuexi07.jpg" width="60" height="62"></a>
                      </td>
                    </tr>
                    <tr>
                      <td height="74" align="center" valign="middle">
                      <a href="quizpaperinit.action?classid=<s:property value='course.classid'/>&myroom.examroom.id=<s:property value="id" />&Return=list" target="_blank"><img src="images/images1108/xuexi08.jpg" width="60" height="62"></a>
                      </td>
                    </tr>
                    <tr>
                      <td height="74" align="center" valign="middle">
                      <a  href="" onClick="document.getElementById('div1').style.display=(document.getElementById('div1').style.display=='none')?'':'none';return false;" ><img src="images/images1108/xuexi09.jpg" width="60" height="62"></a>
                      </td>
                    </tr>
                    <tr>
                      <td height="74" align="center" valign="middle">
                       <a href="forumListByBlockid.action?fblock.id=<s:property value='course.forumid'/>" target="_blank"> <img src="images/images1108/xuexi10.jpg" width="61" height="62"></a>
                      </td>
                    </tr>
                    <tr>
                      <td height="74" align="center" valign="middle">
<!--                      <a href="getCourseIndexview.action?course.id=<s:property value='course.id'/>&ctype=1" target="_blank"> <img src="images/images1108/xuexi11.jpg" width="60" height="62"></a>-->
                     <a href="forumListByBlockid.action?fblock.id=<s:property value='course.forumid'/>" target="_blank"><img src="images/images1108/xuexi11.jpg" width="60" height="62"></a>
                      </td>
                    </tr>
                    <tr>
                      <td height="74" align="center" valign="middle">
                      <a href=""> <img src="images/images1108/xuexi12.jpg" width="60" height="62"></a>
                      </td>
                    </tr>
                </table></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td height="65" valign="bottom" >
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="120" height="55" align="right"><span class="STYLE1">本节学习进度</span></td>
    <td width="200"><div style="overflow:hidden;width: 200px; height:20px;background: buttonface; text-align: left; margin:0px auto;"
										id="processDiv3">
									</div></td>
    <td align="left" style="padding-left:15px;"><span id="timer3" class="STYLE2">加载中..</span><span  class="STYLE2">，</span><span id="timer2" class="STYLE2">加载中..</span><span class="STYLE2"></span></td>
    <td width="100">&nbsp;</td>
  </tr>
</table>

			
			</td>
          </tr>
    </table>
	    <p>&nbsp;</p>
	    <p>&nbsp;</p>
	</body>
</html>
                     