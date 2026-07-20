<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.common.NewSystemConfOp"%>
<%@page import="com.sopia.ElConstants"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="newversionLib" uri="/WEB-INF/newversionLib.tld"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>新版个人中心</title>
		
		
	<style type="text/css">
<!--
body {
	background-color: #ECF5FF;
}
-->
</style></HEAD>
<script type="text/javascript">
		function enterEroom(erid,epid,classid){
				var mw = window.open("quizpaperinit_byepid.action?examRoom.id="+erid+"&examPaper.id="+epid+"&myClass.elClass.id="+classid+"&course.id=&coursePage.id=&atetime="+new Date(),"course_exam_5","toolbar=no,fullscreen=1,location=no,directories=no,menubar=no,scrollbars=yes,resizable=no,status=no");
				if (window.screen){ 
					mw.moveTo(0, 0);
					mw.resizeTo(screen.availWidth,screen.availHeight);
				}
				
				setdisable();
}

      function nopass(){
      	alert('还未获得证书!');
      	return false;
      }
		</script>
	<body>
	
	<table width="245" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="50">&nbsp;</td>
      </tr>
      <tr>
        <td height="75" align="center" background="images/sdimages/button.png">
        <a target="_blank" href="course_study.action?course.id=<s:property value='courseid_sd'/>&coursePage.id=-1&classid=<s:property value='classid_sd'/>" style="font-size:30px;color:white;font-weight:bold;font-family:微软雅黑;">在线学习</a></td>
      </tr>
      <tr>
        <td height="75">&nbsp;</td>
      </tr>
      <tr>
        <td height="75" align="center" background="images/sdimages/button.png">
<!--        <a  target="_blank"  href="quizpaperinit_sd.action?course.id=<s:property value='courseid_sd'/>&myroom.examroom.id=<s:property value='eroomid_sd' />&Return=list" style="font-size:30px;color:white;font-weight:bold;font-family:微软雅黑;">在线考试</a></td>-->
      <a target="_blank" style="font-size:30px;color:white;font-weight:bold;font-family:微软雅黑;"	href='quizpaperinit.action?course.id=<s:property value='courseid_sd'/>&course.getcredit=3&course.firstLearn=1&myroom.examroom.id=<s:property value='eroomid_sd' />&iscommon=0&elclass.id=<s:property value='classid_sd'/>'>在线考试</a></td>
      </tr>
      <tr>
        <td height="75">&nbsp;</td>
      </tr>
      <tr>
     
        <td height="75" align="center" background="images/sdimages/button.png">
         <s:if test="step==3">
        <a target="_blank"  href="mydiploma_view.action?elclass.id=<s:property value='classid_sd'/>" style="font-size:30px;color:white;font-weight:bold;font-family:微软雅黑;">查看证书</a>
        </s:if>
        <s:else>
        	 <a target=""  href="" onclick="nopass();return false;" style="font-size:30px;color:white;font-weight:bold;font-family:微软雅黑;">查看证书</a>
        </s:else>
        </td>
      </tr>
    </table>
	
	</body>
</html>
