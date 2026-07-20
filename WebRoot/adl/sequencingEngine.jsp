<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.sql.*,java.util.*,org.adl.util.*"%>

<%
	//课程是否完成
	boolean courseComplete = true;
	//菜单请求
	boolean wasAMenuRequest = false;
	//下一页
	boolean wasANextRequest = false;
	//上一页
	boolean wasAPrevRequest = false;
	//第一次会话
	boolean wasFirstSession = false;
	boolean empty_block = false;
	
	//  The type of controls shown
	String control = new String();
	//  The next item that will be launched
	String nextItemToLaunch = new String();
	//  The type of button request if its a button request
	String buttonType = new String();
	//  whether the launched unit is a sco or an asset
	String type = new String();
	// is the item a block with no content
	String item_type = new String();
	//is the identifier column 
	String identifier = new String();

	// The courseID is passed as a parameter on initial launch of a course
	String courseID = (String) request.getParameter("courseID");
	//  Get the requested sco if its a menu request
	String requestedSCO = (String) request.getParameter("scoID");
	//  Get the button that was pushed if its a button request
	buttonType = (String) request.getParameter("button");

	// Set boolean for the type of navigation request
	if ((!(requestedSCO == null)) && (!requestedSCO.equals(""))) {
		wasAMenuRequest = true;
	}else if ((!(buttonType == null)) && (buttonType.equals("next"))) {
		wasANextRequest = true;
	}else if ((!(buttonType == null)) && (buttonType.equals("prev"))) {
		wasAPrevRequest = true;
	}else {
		//会话中第一次加载课程
		wasFirstSession = true;
	}
	//如果课程没有被加载
	if (courseID != null) {
		session.setAttribute("COURSEID", courseID);
	}else{ //如果课程没有被初始化加载，从session中获取数据
		courseID = (String) session.getAttribute("COURSEID");
	}

	try {
		Connection conn;
		PreparedStatement stmtSelectUserSCO;
		PreparedStatement stmtUpdateUserSCO;
		PreparedStatement stmtSelectCourse;
		PreparedStatement stmtSelectItemInfo;
		PreparedStatement stmtGetTypeUserSCO;

		//获取用户sco和asset信息
		String sqlSelectUserSCO = "SELECT * FROM SC_USERSCOINFO WHERE UserID = ? AND CourseID = ? ORDER BY Sequence";

		//从特定的sco中获取单个用户的类型信息
		String sqlGetTypeUserSco = "SELECT Type FROM SC_USERSCOINFO WHERE UserID = ? AND CourseID = ? AND SCOID = ?";

		//更新sco和asset信息 (课程状态)
		String sqlUpdateUserSCO = "Update SC_USERSCOINFO set LessonStatus = ? WHERE SCOID = ? AND CourseID = ?";

		//获取课程信息
		String sqlSelectCourse = "SELECT * FROM SC_COURSEINFO WHERE CourseID = ?";
		String sqlInsertUserCourse = "INSERT INTO SC_USERCOURSEINFO (UserID, CourseID) VALUES(?,?)";
		String sqlDeleteUserCourse = "DELETE FROM SC_USERCOURSEINFO WHERE UserID = ? AND CourseID = ?";
		String sqlSelectItemInfo = "SELECT * FROM SC_ITEMINFO WHERE CourseID = ?";

		conn = JdbcUtils.getConnection();

		stmtSelectUserSCO = conn.prepareStatement(sqlSelectUserSCO);
		stmtGetTypeUserSCO = conn.prepareStatement(sqlGetTypeUserSco);
		stmtUpdateUserSCO = conn.prepareStatement(sqlUpdateUserSCO);
		stmtSelectCourse = conn.prepareStatement(sqlSelectCourse);
		stmtSelectItemInfo = conn.prepareStatement(sqlSelectItemInfo);

		//获取用户ID
		String userID = String.valueOf(session.getAttribute("userId"));

		//查询课程信息
		ResultSet courseInfo = null;
		synchronized (stmtSelectCourse) {
			stmtSelectCourse.setString(1, courseID);
			courseInfo = stmtSelectCourse.executeQuery();
		}

		//移动到第一条记录
		while (courseInfo.next()){
			//获取控制数据
			control = courseInfo.getString("Control");
			session.setAttribute("control", control);
		}

		//  Get the session exit flag to see if its a logout request
		String exitFlag = (String) session.getAttribute("EXITFLAG");
		System.out.println("--------userID:"+userID+"----courseID:"+courseID);
		if (exitFlag != null && exitFlag.equals("true")){
			//  It is a logout, so redirect to the logout page
			session.removeAttribute("EXITFLAG");
			response.sendRedirect("logout.jsp");
		}else{ // It is a navigation request
			//  Get the users record of the course items
			ResultSet userSCORS = null;
			synchronized (stmtSelectUserSCO) {
				stmtSelectUserSCO.setString(1, userID);
				stmtSelectUserSCO.setString(2, courseID);
				userSCORS = stmtSelectUserSCO.executeQuery();
			}
			// Initialize variables that help with sequencing
			String scoID = new String();
			String lessonStatus = new String();
			String launch = new String();

			//处理用户点击请求
			System.out.println("wasAMenuRequest:"+wasAMenuRequest);
			if (wasAMenuRequest){
				ResultSet MenuInfo = null;
				synchronized (stmtSelectItemInfo) {
					stmtSelectItemInfo.setString(1, courseID);
					MenuInfo = stmtSelectItemInfo.executeQuery();
				}
				// Move into the first record in the record set
				while (MenuInfo.next()) {
					//  Get the TYPE column
					item_type = MenuInfo.getString("Type");
					identifier = MenuInfo.getString("Identifier");
					
					// the item is not an asset or sco, it is a contain block
					//if  ((item_type.equals("")) && ( identifier.equals(requestedSCO)))    //modify by luocw
					if ((item_type == null) && (identifier.equals(requestedSCO))) {
						// Launch the next sco or item that is the first child
						// of the block item.
						MenuInfo.next();
						requestedSCO = MenuInfo.getString("Identifier");
						empty_block = true;
					}
					if (empty_block)
						break;
				}

				// Handle appropriately for a menu request
				//  Get the last sco id that was taken
				String lastScoID = (String) session.getAttribute("SCOID");

				//  Loop through to find the next one to launch
				while (userSCORS.next()){
					scoID = userSCORS.getString("SCOID");
					lessonStatus = userSCORS.getString("LessonStatus");
					launch = userSCORS.getString("Launch");
					type = userSCORS.getString("Type");

					if (requestedSCO.equals(scoID)) {
						nextItemToLaunch = launch;
						courseComplete = false;
						session.setAttribute("SCOID", scoID);
						break;
					}
				}

				// insert the correct values in stmtUpdateUserSCO
				synchronized (stmtUpdateUserSCO) {
					stmtUpdateUserSCO.setString(1, "completed");
					stmtUpdateUserSCO.setString(2, scoID);
					stmtUpdateUserSCO.setString(3, courseID);
				}
				// If it is an asset, execute the query, marking the asset as completed.
				if ((!(type == null)) && type.equals("asset")) {
					stmtUpdateUserSCO.executeUpdate();
				}

				
			}else{		//处理下一页，上一页，第一次加载或者是自动的请求
				System.out.println("wasFirstSession:"+wasFirstSession+",control:"+control);
				if (wasFirstSession || (("auto").equals(control))) {				//auto或者第一次的请求
					//  Launch the first item that is not in a completed state
					while (userSCORS.next()) {
						scoID = userSCORS.getString("SCOID");
						System.out.println("userSCORS.next()--scoID:"+scoID);
						
						lessonStatus = userSCORS.getString("LessonStatus");
						launch = userSCORS.getString("Launch");
						type = userSCORS.getString("Type");
						// Set nextItemToLaunch to the next incomplete sco or asset
						if (!(("completed").equalsIgnoreCase(lessonStatus))
								&& !(("passed").equalsIgnoreCase(lessonStatus))
								&& !(("failed").equalsIgnoreCase(lessonStatus))) {
							nextItemToLaunch = launch;
							courseComplete = false;
							session.setAttribute("SCOID", scoID);
							break;
						}
					}
				}else if (wasANextRequest){//下一页
					// Handle the next request
					//  Get the last sco id that was taken
					String lastScoID = (String) session.getAttribute("SCOID");
					//  Boolean to trigger the correct sco to launch
					boolean timeToLaunch = false;

					//  Loop through to find the next one to launch
					while (userSCORS.next()) {
						// Launch the next sequential sco
						scoID = userSCORS.getString("SCOID");
						lessonStatus = userSCORS.getString("LessonStatus");
						launch = userSCORS.getString("Launch");
						type = userSCORS.getString("Type");
						if (timeToLaunch) {

							nextItemToLaunch = launch;
							courseComplete = false;
							session.setAttribute("SCOID", scoID);
							break;
						}

						if (lastScoID.equals(scoID)) {
							timeToLaunch = true;
						}
					}
					//插入当前值到stmtUpdateUserSCO表
					synchronized (stmtUpdateUserSCO) {
						stmtUpdateUserSCO.setString(1, "completed");
						stmtUpdateUserSCO.setString(2, scoID);
						stmtUpdateUserSCO.setString(3, courseID);
					}
					// Execute the query, marking the asset as completed.

					if ((!(type == null)) && type.equals("asset")) {
						stmtUpdateUserSCO.executeUpdate();
					}
					//  Ends if its a next request
				}else if (wasAPrevRequest){//上一页
					// Handle the previous request
					String lastScoID = (String) session.getAttribute("SCOID");
					String prevScoID = new String();
					String prevScoLaunch = new String();
					boolean timeToLaunch = false;
					int count = 0;

					while (userSCORS.next()) {
						if (timeToLaunch) {
							// Launch the previous sequential sco or asset
							nextItemToLaunch = prevScoLaunch;
							courseComplete = false;
							session.setAttribute("SCOID", prevScoID);
							break;
						}
						//  Get the previous scoID and launch
						prevScoID = scoID;
						prevScoLaunch = launch;

						//  Get the new info
						scoID = userSCORS.getString("SCOID");
						lessonStatus = userSCORS.getString("LessonStatus");
						launch = userSCORS.getString("Launch");
						type = userSCORS.getString("Type");
						count++;//the first time through the loop, check to see if
						// the request was made by the first sco in the course
						if (lastScoID.equals(scoID)) {
							if (count == 1) {
								prevScoID = scoID;
								prevScoLaunch = launch;
							}
							timeToLaunch = true;
						}//end if
					}//end while

					if (!userSCORS.next()) {// Launch the previous sequential sco or asset
						nextItemToLaunch = prevScoLaunch;
						courseComplete = false;
						session.setAttribute("SCOID", prevScoID);
					}

				}

			} 

		}

		//  If the course is complete redirect to the course
		//  complete page
		if (courseComplete) {
			session.removeAttribute("COURSEID");
			response.sendRedirect("courseComplete.jsp");
		} else {
%>

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
<meta http-equiv="refresh" content="3; url=<%=nextItemToLaunch%>">

<script language="JAVASCRIPT">
         function initLMSFrame(){
            // Set the type of control for the course in the LMS Frame 
            if ( window.opener == null ){
               window.top.frames[0].document.forms[0].control.value = "<%=control%>";
            }else{//  Set up control type in the window opener (if its auto mode)
               // The sequencingEngine.jsp file runs in the opened window if it is auto
               // mode so special cases exist
               window.opener.top.frames[0].document.forms[0].control.value = "<%=control%>";
            }
         }
      </script>
</HEAD>

<body bgcolor="#FFFFFF" onload="initLMSFrame()">
<%
	//  If control is not auto. work in this window. 
			if (!("auto").equals(control)) {
%>
<script language="javascript">
               // Hide the next and previous buttons if it is of type "choice".
               var ctrl = "<%=control%>";
               
               if (ctrl == "choice"){ 
                  window.top.frames[0].document.forms[0].next.style.visibility = "hidden";
                  window.top.frames[0].document.forms[0].previous.style.visibility = "hidden";
                  window.top.frames[0].document.forms[0].quit.style.visibility = "visible";
               }else{
                  // Make the buttons visible
                  window.top.frames[0].document.forms[0].next.style.visibility = "visible";
                  window.top.frames[0].document.forms[0].previous.style.visibility = "visible";
                  window.top.frames[0].document.forms[0].quit.style.visibility = "visible";
               }
            </script>


<p><font size="4"> 请稍候.... </font></p>
</body>
</html>
<%
	} // Ends if its not auto sequencing... then configure controls
%>

<%
	} // Ends else display the please wait page

	} catch (Exception e) {
		out.println("!! 出现错误: " + e + " !!");
		e.printStackTrace();
	}
%>