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
</style>
	<head>
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<base href="<%=basePath%>" target="_self" />
		<title>学习课程--<s:property value="course.name" /></title>
		
		<link href="css/study_csp.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/course.js"></script>
		<script type="text/javascript" src="js/CourseStudy.js"></script>
		<script type="text/javascript" src="js/flexpaper.js"></script> 
		<script type="text/javascript" src="js/flexpaper_handlers.js"></script>
		<script type="text/javascript" src="js/cvideo/cvideoWrite.js"></script>
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
		</script>
	</HEAD>
	<body onLoad="myload('<s:property value="status"/>');">
		<table width="1017" height="583" border="0" align="center" cellpadding="0" cellspacing="0" id="__01">
	<tr>
		<td rowspan="11">
			<img src="images/images1106/jichu_01.jpg" width="15" height="645" alt="">
			</td>
		<td colspan="6">
			<img src="images/images1106/jichu_02.jpg" width="984" height="49" alt=""></td>
		<td rowspan="11">
			<img src="images/images1106/jichu_03.jpg" width="17" height="648" alt=""></td>
		<td>
			<img src="images/images1106/分隔符.gif" width="1" height="49" alt=""></td>
	</tr>
	<tr>
		<td colspan="2" rowspan="5">
			<img src="images/images1106/jichu_04.jpg" width="19" height="286" alt=""></td>
		<td>
			<a href="" onclick=""><img src="images/images1106/jichu_05.jpg" width="74" height="66" alt="" onclick=""></a>
			</td>
	  <td width="799" height="452" rowspan="8" valign="top" background="images/images1106/jichu_06.jpg" style="padding-top:14px;"><table border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td width="761px" height="500px" valign="middle" align="center">
			
				<div style="height:499px;width: 760px;" id="page_file"></div>
				
</td>
          </tr>
        </table></td>
		<td rowspan="2">
			<img src="images/images1106/jichu_07.jpg" width="73" height="72" alt=""></td>
		<td rowspan="8">
			<img src="images/images1106/jichu_08.jpg" width="19" height="517" alt=""></td>
		<td>
			<img src="images/images1106/分隔符.gif" width="1" height="66" alt=""></td>
	</tr>
	<tr>
		<td rowspan="2">
			<img src="images/images1106/jichu_09.jpg" width="74" height="78" alt=""></td>
		<td>
			<img src="images/images1106/分隔符.gif" width="1" height="6" alt=""></td>
	</tr>
	<tr>
		<td rowspan="2">
			<img src="images/images1106/jichu_10.jpg" width="73" height="77" alt=""></td>
		<td>
			<img src="images/images1106/分隔符.gif" width="1" height="72" alt=""></td>
	</tr>
	<tr>
		<td rowspan="2">
			<img src="images/images1106/jichu_11.jpg" width="74" height="79" alt=""></td>
		<td>
			<img src="images/images1106/分隔符.gif" width="1" height="5" alt=""></td>
	</tr>
	<tr>
		<td>
			<img src="images/images1106/jichu_12.jpg" width="73" height="74" alt=""></td>
		<td>
			<img src="images/images1106/分隔符.gif" width="1" height="74" alt=""></td>
	</tr>
	<tr>
		<td rowspan="3">
			<img src="images/images1106/jichu_13.jpg" width="11" height="231" alt=""></td>
		<td colspan="2">
			<img src="images/images1106/jichu_14.jpg" width="82" height="78" alt=""></td>
		<td>
			<img src="images/images1106/jichu_15.jpg" width="73" height="78" alt=""></td>
		<td>
			<img src="images/images1106/分隔符.gif" width="1" height="78" alt=""></td>
	</tr>
	<tr>
		<td rowspan="2">
			<img src="images/images1106/jichu_16.jpg" width="8" height="151" alt=""></td>
		<td>
			<img src="images/images1106/jichu_17.jpg" width="74" height="75" alt=""></td>
		<td>
			<img src="images/images1106/jichu_18.jpg" width="73" height="75" alt=""></td>
		<td>
			<img src="images/images1106/分隔符.gif" width="1" height="75" alt=""></td>
	</tr>
	<tr>
		<td>
			<img src="images/images1106/jichu_19.jpg" width="74" height="76" alt=""></td>
		<td>
			<img src="images/images1106/jichu_20.jpg" width="73" height="76" alt=""></td>
		<td>
			<img src="images/images1106/分隔符.gif" width="1" height="76" alt=""></td>
	</tr>
	<tr>
		<td colspan="6">
			<img src="images/images1106/jichu_21.jpg" width="984" height="55" alt=""></td>
		<td>
			<img src="images/images1106/分隔符.gif" width="1" height="55" alt=""></td>
	</tr>
	<tr>
		<td colspan="6">
			<img src="images/images1106/jichu_22.jpg" width="984" height="27" alt=""></td>
		<td>
			<img src="images/images1106/分隔符.gif" width="1" height="27" alt=""></td>
	</tr>
</table>
	</body>
</html>
