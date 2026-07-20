<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import = "java.util.*" %>

<%
//  Booleans for a completed course and request type
boolean courseComplete = true;
boolean wasAMenuRequest = false;
boolean wasANextRequest = false;
boolean wasAPrevRequest = false;
boolean wasFirstSession = false;
boolean empty_block = false;

//  The type of controls shown
String control = new String();
//  The type of button request if its a button request
String buttonType = new String();
String request_type = new String();


//获取课程ID
String requestedSCO = (String)request.getParameter( "scoID" );
//  Get the button that was pushed if its a button request
buttonType = (String)request.getParameter( "button" );

// Set boolean for the type of navigation request
if ((!(requestedSCO == null)) && (! requestedSCO.equals(""))){
   request_type = "sequencingEngine.jsp?scoID="+requestedSCO;
}else if((! (buttonType == null) ) && ( buttonType.equals("next"))){
   request_type = "sequencingEngine.jsp?button=next";
}else if ( (! (buttonType == null) ) && ( buttonType.equals("prev"))){
   request_type = "sequencingEngine.jsp?button=prev";
}%>


<!-- ****************************************************************
**   Build the html 'please wait' page that sets the client side 
**   variables and refreshes to the appropriate course page
*******************************************************************-->  
<html>
   <head>
   <title>Sample Run-Time Environment - Sequencing Engine</title>
   <!-- **********************************************************
   **  This value is determined by the JSP database queries
   **  that are located above in this file
   **  Refresh the html page to the next item to launch  
   ***************************************************************-->
   <meta http-equiv="refresh" content="2; url=<%=request_type%>">
</HEAD>
<body><br>

            <p></p>
         </body>
      </html> 