<%@page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import = "java.sql.*, java.util.*,org.adl.util.JdbcUtils" %>
<html>
<head>
   <title>ADL Sample RTE Clear Database</title>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <meta http-equiv="expires" content="Tue, 20 Aug 1999 01:00:00 GMT">
   <meta http-equiv="Pragma" content="no-cache">
</HEAD>
<body bgcolor="#FFFFFF">

<jsp:include page="gotoMenu.jsp" flush="true" />
<%!
   Connection conn;
   PreparedStatement stmtDeleteCourseInfo;
   PreparedStatement stmtUpdateApplicationData;
   PreparedStatement stmtDeleteItemInfo;
   PreparedStatement stmtDeleteUserscoInfo;
   PreparedStatement stmtDeleteUsercourseInfo;

   /***************************************************************************
   **
   ** Function:  jspInit()
   ** Input:   none
   ** Output:  conn, driverName and connectionURL are established.
   **          sqlDeleteCourseInfo is assigned the SQL query to delete
   **          everything in the CourseInfo table, which is then converted to
   **          a prepared statement and assigned to stmtDeleteCourseInfo.
   **
   ** Description:  This function sets the driverName and connectionURL
   **               variables and establishes the database connection.  The
   **               SQL string are also assigned and converted to a prepared
   **               statement.
   **
   ***************************************************************************/
   public void jspInit()
   {
      try
      {
         conn = JdbcUtils.getConnection();

         String sqlDeleteCourseInfo = "Delete FROM SC_COURSEINFO";
         String sqlUpdateApplicationData = "UPDATE SC_APPLICATIONDATA SET numberValue = '1' WHERE dataName = 'nextCourseID'";
         
         //补充清空清空 SC_ITEMINFO,SC_USERSCOINFO,SC_USERCOURSEINFO表
		 String sqlDeleteItemInfo = "Delete FROM SC_ITEMINFO";
		 String sqlDeleteUserscoInfo = "Delete FROM SC_USERSCOINFO";
		 String sqlDeleteUsercourseInfo = "Delete FROM SC_USERCOURSEINFO";

         stmtDeleteCourseInfo = conn.prepareStatement( sqlDeleteCourseInfo );
         stmtUpdateApplicationData = conn.prepareStatement( sqlUpdateApplicationData);
         stmtDeleteItemInfo = conn.prepareStatement( sqlDeleteItemInfo );
         stmtDeleteUserscoInfo = conn.prepareStatement( sqlDeleteUserscoInfo );
         stmtDeleteUsercourseInfo = conn.prepareStatement( sqlDeleteUsercourseInfo );
         
      }
      catch(SQLException e){
    	  
      }catch(Exception e){
    	  
      }
   }

   /*********************************************************************
   * Method: jspDestroy()
   * Input: none
   * Output: none
   *
   * Description: Closes the statement and the database connection.    
   *********************************************************************/
   public void jspDestroy()
   {
      try{
         stmtDeleteCourseInfo.close();
         stmtUpdateApplicationData.close();
         stmtDeleteItemInfo.close();
         stmtDeleteUserscoInfo.close();
         stmtDeleteUsercourseInfo.close();
         conn.close();
      }catch(Exception e){
    	  e.printStackTrace();
      }
   }
%>
<%
   //executes the query to delete all records in the CourseInfo table.
   try{
      stmtDeleteCourseInfo.executeUpdate();
      stmtUpdateApplicationData.executeUpdate();
      stmtDeleteItemInfo.executeUpdate();
      stmtDeleteUserscoInfo.executeUpdate();
      stmtDeleteUsercourseInfo.executeUpdate();
   }catch(Exception e){
      e.printStackTrace();   
   }
%>
<b> 数据库清理成功 </b>

</body>
</html>
