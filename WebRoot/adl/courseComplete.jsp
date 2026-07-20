<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>Sample Run-Time Environment - Course Complete</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="JavaScript">
     
   function handleAuto()
   {
      window.opener.top.frames[2].location.href = "LMSMenu.jsp";
      window.close();
   }
   <!--

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
   function MM_reloadPage(init)
   { //reloads the window if Nav4 resized
      if (init==true) with (navigator)
      {
         if ((appName=="Netscape")&&(parseInt(appVersion)==4))
         {
            document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage;
         }
      }
      else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
   }
   MM_reloadPage(true);

   // -->
   </script>
</HEAD>

<body bgcolor="#FFFFFF">

<%
	String userid = String.valueOf(session.getAttribute("userId"));
	String admin = (String) session.getAttribute("RTEADMIN");
	String contrl = (String) session.getAttribute("CONTROL");
%>
<script language="JavaScript">
var scoWinType = typeof(window.opener)

if ( scoWinType != "undefined" && scoWinType != "unknown" ) 
{  
   ctrl = window.opener.top.frames[0].document.forms[0].control.value;
}
else
{
   ctrl = window.top.frames[0].document.forms[0].control.value;
}

if (ctrl == "auto"){
   document.writeln("<p><a href='javascript:handleAuto();'>返回主菜单</a></p>");
}else{
   document.writeln("<p><a href='LMSMenu.jsp'>返回主菜单</a></p>");
}

window.top.frames[0].document.forms[0].next.style.visibility = "hidden";
window.top.frames[0].document.forms[0].previous.style.visibility = "hidden";
//window.top.frames[0].document.forms[0].logout.disabled = false;
window.parent.frames[1].document.location.href = "code.jsp";
window.top.frames[0].document.forms[0].quit.style.visibility = "hidden";


</script>




<DIV id=step_1>
<p><font size="4">你已经完成了此课程</font></p>
</DIV>
<p>&nbsp;</p>
</body>
</html>
