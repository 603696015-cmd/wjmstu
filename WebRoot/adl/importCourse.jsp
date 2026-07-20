<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>Sample Run-Time Environment - Import Course</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="JavaScript">

   /****************************************************************************
   **
   ** Function:  MM_reloadPage()
   ** Input:   init - boolean
   ** Output:  boolean
   **
   ** Description:  This function reloads the window if Nav4 is resized
   **
   ** Issues:  This method is not in use in Version 1.2.2 due to the lack of
   **          Netscape support.
   **
   ***************************************************************************/
   function MM_reloadPage( init ){ 
      if (init == true) with (navigator)
      {               
         if ( (appName == "Netscape") && (parseInt(appVersion) == 4) )
         {
            document.MM_pgW = innerWidth;
            document.MM_pgH = innerHeight;
            onresize = MM_reloadPage;
         }
      }
      else if (innerWidth != document.MM_pgW || innerHeight != document.MM_pgH)
      {
         location.reload();
      }
   }
   MM_reloadPage(true);
   
   /****************************************************************************
   **
   ** Function:  checkValues()
   ** Input:   none
   ** Output:  boolean
   **
   ** Description:  This function ensures that there are values in each text
   **               box before submitting
   **
   ***************************************************************************/
   function checkValues()
   {
      if ( courseInfo.coursename.value == "" || courseInfo.coursezipfile.value == "" ){
         alert( "请输入课程名称或者浏览课程文件" );
         return false;
      }
      
      courseInfo.theZipFile.value = courseInfo.coursezipfile.value;
      return true;
   }
   
   /****************************************************************************
   **
   ** Function:  newWindow()
   ** Input:   pageName
   ** Output:  none
   **
   ** Description:  This function opens the help window
   **
   ***************************************************************************/
   function newWindow( pageName ){
      window.open(pageName, 'Help', 
      "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,width=500,height=500");
   }
   
   </script>
</HEAD>

<body bgcolor="#FFFFFF">

<jsp:include page="gotoMenu.jsp" flush="true" />


<form method="post" action="LMSCourseImport.jsp" name="courseInfo" onSubmit="return checkValues()" enctype="multipart/form-data">

<p><font face="tahoma" size="3"><b> 课程导入 </b></font></p>

<table width="550" border="0" align="left">
	<tr>
		<td bgcolor="#5E60BD" colspan="2">
			<font face="tahoma" size="2" color="#ffffff"><b> &nbsp;请填写下面的课程信息: </b></font>
		</td>
	</tr>
	<tr>
		<td><font face="tahoma" size="2"> 请输入导入的课程名称: </font></td>
	</tr>
	<tr>
		<td width="49%">
			<input id="coursename" name="coursename" type=text>
		</td>
	</tr>
	<tr>
		<td width="51%">&nbsp;</td>
	</tr>
	<tr>
		<td><font face="tahoma" size="2"> 选择后缀名为ZIP格式的课程文件进行导入:: </font></td>
	</tr>
	<tr>
		<td width="49%"><input id="coursezipfile" name="coursezipfile" type=file></td>
	</tr>
	<tr>
		<td width="51%">&nbsp;</td>
	</tr>
	<tr>
		<td><font face="tahoma" size="2"> 选择当前课程的导航类型. <br>
		<i>详见'帮助'</i> </font></td>
	</tr>
	<tr>
		<td>顺序(Flow)&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" name="controltype" value="flow" checked></td>
	</tr>
	<tr>
		<td>选择(Choice)&nbsp; <input type="radio" name="controltype" value="choice"></td>
	</tr>

	<tr>
		<td width="100%" colspan="2">&nbsp;</td>
	</tr>
	<tr>
		<td width="100%" colspan="2"><input type="submit" name="Submit" value="提交"></td>
	</tr>
	<tr>
		<td><br>
		<a href="javascript:newWindow('importHelp.htm');">帮助!</a></td>
	</tr>
</table>

<input type=hidden name="theManifest">
<input type=hidden name="theZipFile">
</form>

</body>
</html>