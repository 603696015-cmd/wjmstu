<%@page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import = "java.sql.*, java.util.*, java.io.*,org.adl.util.JdbcUtils" %>
<HTML>
<HEAD>
   <TITLE>ADL Sample RTE Delete Competency Record</TITLE>
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
   <%!
      Connection conn;
      PreparedStatement stmtDeleteComp;

      /*********************************************************************
      * Method: jspInit()
      * Input: none
      * Output: conn and stmtDeleteComp are given new values
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
            
            String sqlDeleteComp = "DELETE FROM SC_COMPETENCY WHERE CompID = ?";
            stmtDeleteComp = conn.prepareStatement( sqlDeleteComp );
         }catch (SQLException e){
        	 e.printStackTrace();
         }catch (Exception e){
        	 e.printStackTrace();
         }
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
            stmtDeleteComp.close();
            conn.close();
         }
         catch(Exception e) {}
         
      }
   %>

   <%
      // The competency ID is passed into stmtDeleteComp and the statement is
      // executed, deleting the competency record.
      try
      {
         String compID = request.getParameter( "ID" );

         /*********************************************************************
         * Method: synchronized()
         * Input: stmtDeleteComp
         * Output: stmtDeleteComp
         *
         * Description: Inserts the value of the variable 'compID' into the
         *              prepared statement 'stmtDeleteComp'.
         *********************************************************************/
         synchronized( stmtDeleteComp )
         {
            stmtDeleteComp.setString(1, compID);
         }
         stmtDeleteComp.executeUpdate();
      }
      catch (Exception e) {e.printStackTrace();}
   %>
   <CENTER><B>Competency record successfully deleted.</B></CENTER>
</BODY>
</HTML>
