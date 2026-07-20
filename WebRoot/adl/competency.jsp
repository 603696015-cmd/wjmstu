<%@page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import = "java.sql.*, java.util.*, java.io.*,org.adl.util.JdbcUtils" %>
<HTML>
<HEAD>
   <TITLE>ADL Sample RTE Display Competency Records</TITLE>
   <META HTTP-EQUIV-equiv="Content-Type" CONTENT="text/html; charset=UTF-8">
   <SCRIPT LANGUAGE="JavaScript">
   <!--
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

<BODY BGCOLOR="#FFFFFF">
   <jsp:include page="gotoMenu.jsp" flush="true" />
   <TABLE WIDTH="458" BORDER="0">
      <%!
         Connection conn;
         PreparedStatement stmtSelectComp;
          
         /*********************************************************************
         * Method: jspInit()
         * Input: none
         * Output: conn and stmtSelectComp are given new values
         *
         * Description: This function sets the driverName and connectionURL
         *              variables and establishes the database connection.  The
         *              SQL string is also assigned and converted to a prepared
         *              statement.
         *********************************************************************/
         public void jspInit()
         {
            try
            {
               conn = JdbcUtils.getConnection();

               String sqlSelectComp = "SELECT * FROM SC_COMPETENCY";
               stmtSelectComp = conn.prepareStatement( sqlSelectComp );
            }
            catch(SQLException e){}
            catch(Exception e){}
         }
  
         /*********************************************************************
         * Method: jspDestroy()
         * Input: none
         * Output: none
         *
         * Description: Closes statements and the database connection.    
         *********************************************************************/
         public void jspDestroy()
         {
            try
            {
               stmtSelectComp.close();
               conn.close();
            }
            catch(SQLException e) {}
         }
      %>
       
      <%
         try
         {
            ResultSet compRS;
            String compID = null;
            String passFail = null;

            compRS = stmtSelectComp.executeQuery();

            // Checks to see if the result set (compRS) contains anything
            // and Prints column headers if so.
            if( compRS.next() )
            {
               %>
               <TR>
                  <TD>
                     <B><U>Competency ID</U></B>
                  </TD>
                  <TD>
                     <B><U>Results</U></B>
                  </TD>
                  <TD>
                     <B><U>Update</U></B>
                  </TD>
                  <TD>
                     <B><U>Delete</U></B>
                  </TD>
               </TR>
               <%
            }
            
            // Loops through the result set (compRS) and outputs the
            // information in it's corresponding column.
            while( compRS.next() )
            {
               compID = compRS.getString("CompID");
               passFail = compRS.getString("PassFail");

               %>
               <TR>
                  <TD>
                     <%=compID%>
                  </TD>
                  <TD>
                     <%=passFail%>
                  </TD>
                  <TD>
                     <A HREF="updateComp.jsp?ID=<%=compID%>">Update</a>
                  </TD>
                  <TD>
                     <A HREF="deleteComp.jsp?ID=<%=compID%>">Delete</A>
                  </TD>
               </TR>
               <%
            }
         }
         catch(Exception e){}
      %>
   </TABLE>
   <BR>
   <A HREF="addComp.jsp">Add Competency Record</A>
</BODY>
</HTML>
