<%@page contentType="text/html; charset=UTF-8"%>
<html>
<head>
   <title>添加用户</title>
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
   function MM_reloadPage(init)
   { 
      if (init==true) with (navigator)
      {
         if ( (appName=="Netscape")&&(parseInt(appVersion)==4) )
         {
            document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage;
         }
      }
      else if ( (innerWidth!=document.MM_pgW) || (innerHeight!=document.MM_pgH) ) 
      {
         location.reload();
      }
   }
   //MM_reloadPage( true );

   /****************************************************************************
   **
   ** Function:  checkData()
   ** Input:   none
   ** Output:  boolean
   **
   ** Description:  This function ensures that there are values in each text
   **               box before submitting
   **
   ***************************************************************************/   
   function checkData() 
   {
      if ( newUser.userID.value == "" || newUser.firstName.value == "" || 
           newUser.lastName.value == "" || newUser.password.value == "" || 
           newUser.cPassword.value == "" )
      {
         alert ( "请输入字段值!!" );
         return false;
      }

      if ( newUser.password.value != newUser.cPassword.value)
      {
         alert ( "两次密码输入不一致!!" );
         return false;
      }

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
   function newWindow(pageName)
   {
      window.open(pageName, 'Help', 
      "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,width=500,height=500");
   }
   
   </script>
</HEAD>

<body bgcolor="#FFFFFF">

<jsp:include page="gotoMenu.jsp" flush="true" />

<%
  
String newUserError = (String)session.getAttribute( "NEWUSERERROR" );
if ( newUserError != null )
{
   if ( newUserError.equals( "dupid" ))
   {
%>
      <h2>The following error was caught</h2>
      <p>User ID already exists, please choose another ID</p>
<%
      session.removeAttribute( "NEWUSERERROR" );
   }
%>
<%
}
%>
    
<%
String userID = (String) session.getAttribute( "NEWUSERID" );
String firstName = (String) session.getAttribute( "NEWUSERFN" );
String lastName = (String) session.getAttribute( "NEWUSERLN" );


%>
   
<p>
<font face="tahoma" size="3"><b>
   Add a New User
</b></font>
</p>
    
<form method="post" action="processNewUser.jsp" name="newUser" onSubmit="return checkData()">
   <table width="450" border="0" align="left">
      <tr>
         <td bgcolor="#5E60BD" colspan="2">
            <font face="tahoma" size="2" color="#ffffff"><b>
               &nbsp;请输入用户信息:
            </font>
         </td>
      </tr>
      <tr>
         <td width="37%">
           	 用户ID:
         </td>
         <td width="63%">
            <%
            
            if ( userID != null ){ 
            %>
               <input type="text" name="userID" value="<%= userID %>">
            <%}else{%>
               <input type="text" name="userID"> 
            <%}%>
         </td>
      </tr>
      <tr>
         <td width="37%">姓名:</td>
            <td width="63%">
            <%
               if ( firstName != null ){
            %>
                  <input type="text" name="firstName" value="<%= firstName %>">
               <%}else{%>
                  <input type="text" name="firstName">  
               <%
               }
               %>
           </td>
        </tr>
        <tr>
          <td width="37%">名:</td>
             <td width="63%">
             <%
             if ( lastName != null ){
             %>
                <input type="text" name="lastName" value="<%= lastName %>">
             <%}else{%> 
                <input type="text" name="lastName">
             <%}%>
             </td>
         </tr>
         <tr>
             <td width="37%">
               	 密码:
             </td>
             <td width="63%">
                 <input type="password" name="password">
             </td>
         </tr>
         <tr>
             <td width="37%">
                	密码确认:
             </td>
             <td width="63%">
                 <input type="password" name="cPassword">
             </td>
         </tr>
         <tr>
             <td width="37%">
                	是否管理员:
             </td>
             <td width="63%">
                 <select name="admin">
                     <option>否</option> <option>是</option>
                 </select>    
             </td>
         </tr>
         <tr>
             <td width="37%">
                &nbsp;
             </td>
             <td width="63%">
                &nbsp;
             </td>
         </tr>
         <tr>
            <td colspan="2" align="center">     
               <input type="submit" name="Submit" value="提交">
            </td>
         </tr>
         <tr>
            <td colspan="2">
               <br><br>      
               <a href="javascript:newWindow('newUserHelp.htm');">帮助!</a>
            </td>
         </tr>
     </table>
</form>
<p>
&nbsp;
</p>
</body>
</html>
