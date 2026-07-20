<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>大文件上传</title>
		<base href="<%=basePath%>" />
		<script type="text/javascript">
	   function a(){
          var path=document.getElementById("myfile").value;
        var returnVal= document.getElementById("hid").value;
        returnVal=path;
        window.returnValue=returnVal;
        window.close();
        }
		</script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<div style="text-align:center;font-size:12px;margin:5px;">
			&nbsp;&nbsp;&nbsp;&nbsp;大文件上传，支持断点续传
		</div>
		<br />
		<div style="text-align:center;">
			 <form action="question_ftpupload.action" method="post" enctype="multipart/form-data">  
        <table width="100%">  
               <tr>
                <td>上传文件:</td>  
                <td><input type="file" id="myfile" name="myFile" onchange="a()" />
      
                <input type="hidden" id ="hid" name="fileName" value="" />
                </td>  
            </tr>  
           
        </table>  
      </form>  
		</div>
	</body>
</html>
