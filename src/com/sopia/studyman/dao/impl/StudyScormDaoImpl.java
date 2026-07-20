package com.sopia.studyman.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.adl.parsers.dom.ADLOrganizations;
import org.adl.samplerte.util.RTEFileHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.courseman.entities.ScormCourse;
import com.sopia.studyman.dao.StudyScormDao;

public class StudyScormDaoImpl implements StudyScormDao {
	private static final Log logger = LogFactory
			.getLog(StudyScormDaoImpl.class);

	public void registerCourse(String userID, String courseID,String classID)
			throws ElException {
		try {
			Connection conn;
			PreparedStatement stmtSelectCourse;
			PreparedStatement stmtSelectUserCourse;
			PreparedStatement stmtInsertUserCourse;
			String sqlSelectUserCourse = "SELECT * FROM SC_UserCourseInfo WHERE UserID = ? AND CourseID = ? AND CLASSID=?";
			String sqlSelectCourse = "SELECT * FROM SC_CourseInfo";
			String sqlInsertUserCourse = "INSERT INTO SC_UserCourseInfo (UserID, CourseID,CLASSID) VALUES(?,?,?)";
			conn = DBConnection.getConnection();
			stmtSelectCourse = conn.prepareStatement(sqlSelectCourse);
			stmtSelectUserCourse = conn.prepareStatement(sqlSelectUserCourse);
			stmtInsertUserCourse = conn.prepareStatement(sqlInsertUserCourse);
			String theWebPath = ServletActionContext.getServletContext().getRealPath("/");
	
			RTEFileHandler fileHandler = new RTEFileHandler(theWebPath);
			ResultSet userCourseRS = null;
			synchronized (stmtSelectUserCourse) {
				stmtSelectUserCourse.setString(1, userID);
				stmtSelectUserCourse.setString(2, courseID);
				stmtSelectUserCourse.setString(3, classID);
				userCourseRS = stmtSelectUserCourse.executeQuery();
			}

			if (userCourseRS.next() == false) {
				synchronized (stmtInsertUserCourse) {
					stmtInsertUserCourse.setString(1, userID);
					stmtInsertUserCourse.setString(2, courseID);
					stmtInsertUserCourse.setString(3, classID);
					stmtInsertUserCourse.executeUpdate();
				}
				fileHandler.setUserID(userID);
				fileHandler.setCourseID(courseID);
				fileHandler.setClassID(classID);
				fileHandler.initializeCourseFiles();
				String courseSeqFile = theWebPath + "/CourseImports/"
						+ courseID + "/sequence.obj";
				FileInputStream istream = new FileInputStream(courseSeqFile);
				ObjectInputStream ois = new ObjectInputStream(istream);
				ADLOrganizations sequenceObj = (ADLOrganizations) ois
						.readObject();
				istream.close();
				String sequencingFilePath = theWebPath + "/CourseImports/"
				+ courseID + "/" + classID ;
				File sequencingFileDir = new File(sequencingFilePath);
				if(!sequencingFileDir.exists()){
					sequencingFileDir.mkdirs();
				}
				String sequencingFileName = sequencingFilePath+ "/sequence." + userID;
				
				java.io.File userSequence = new java.io.File(sequencingFileName);
				FileOutputStream ostream = new FileOutputStream(userSequence);
				ObjectOutputStream oos = new ObjectOutputStream(ostream);
				oos.writeObject(sequenceObj);
				oos.flush();
				oos.close();
			}
			stmtSelectCourse.close();
			stmtSelectUserCourse.close();
			stmtInsertUserCourse.close();
		} catch (Exception e) {
			logger.error("注册scorm课程出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(null, null, null);
		}
	}

	public ScormCourse intoCourse(String courseID, String userID,String classID,
			String requestedSCO,String nowSCO, String buttonType) throws ElException {
		ScormCourse sc = new ScormCourse();
		sc.setCourseid(courseID);
		sc.setUserid(userID);
		sc.setClassid(classID);
		System.out.println("courseID="+courseID);
		System.out.println("userID="+userID);
		System.out.println("classID="+classID);
		System.out.println("requestedSCO="+requestedSCO);
		System.out.println("nowSCO="+nowSCO);
		System.out.println("buttonType="+buttonType);
		try {
			boolean courseComplete = true;// 课程是否完毕
			boolean wasAMenuRequest = false;// 是一个菜单请求
			boolean wasANextRequest = false;// 是下一个
			boolean wasAPrevRequest = false;// 是上一个
			boolean wasFirstSession = false;// 是第一次
			boolean empty_block = false;// 空块？
			String control = new String();// 控制
			// String nextItemToLaunch = new String();// 当前要加载的页面
			String type = new String();// 需要加载的是一个sco或asset
			// is the item a block with no content
			String item_type = new String();// 课程清单类型
			// is the identifier column
			String identifier = new String();// 标志符
			// Set boolean for the type of navigation request
			if ((!(requestedSCO == null)) && (!requestedSCO.equals(""))) {
				wasAMenuRequest = true;
			} else if ((!(buttonType == null)) && (buttonType.equals("next"))) {
				wasANextRequest = true;
			} else if ((!(buttonType == null)) && (buttonType.equals("prev"))) {
				wasAPrevRequest = true;
			} else {
				// First launch of the course in this session.
				wasFirstSession = true;
			}
			// Prepare the database connection and statements
			Connection conn;
			PreparedStatement stmtSelectUserSCO;
			PreparedStatement stmtUpdateUserSCO;
			PreparedStatement stmtSelectCourse;
			PreparedStatement stmtSelectItemInfo;
			// Get a users sco and asset information
			String sqlSelectUserSCO = "SELECT * FROM SC_UserSCOInfo WHERE UserID = ? AND CourseID = ? AND CLASSID=? ORDER BY Sequence";
			// Get the type info from a specific sco by a user
//			String sqlGetTypeUserSco = "SELECT Type FROM SC_UserSCOInfo WHERE UserID = ? AND CourseID = ? AND SCOID = ?";
			// Update sco and asset information (LessonStatus)
			String sqlUpdateUserSCO = "Update SC_UserSCOInfo set LessonStatus = ? WHERE SCOID = ? AND CourseID = ? AND CLASSID=?";
			// Get the course information
			String sqlSelectCourse = "SELECT * FROM SC_CourseInfo WHERE CourseID = ?";
			// String sqlInsertUserCourse = "INSERT INTO SC_UserCourseInfo
			// (UserID, CourseID) VALUES(?,?)";
			// String sqlDeleteUserCourse = "DELETE FROM SC_UserCourseInfo WHERE
			// UserID = ? AND CourseID = ?";
			String sqlSelectItemInfo = "SELECT * FROM SC_ItemInfo WHERE CourseID = ?";
			conn = DBConnection.getConnection();
			stmtSelectUserSCO = conn.prepareStatement(sqlSelectUserSCO);
//			stmtGetTypeUserSCO = conn.prepareStatement(sqlGetTypeUserSco);
			stmtUpdateUserSCO = conn.prepareStatement(sqlUpdateUserSCO);
			stmtSelectCourse = conn.prepareStatement(sqlSelectCourse);
			stmtSelectItemInfo = conn.prepareStatement(sqlSelectItemInfo);
			// Execute the course info database query
			ResultSet courseInfo = null;
			synchronized (stmtSelectCourse) {
				stmtSelectCourse.setString(1, courseID);
				courseInfo = stmtSelectCourse.executeQuery();
			}
			// Move into the first record in the record set
			if (courseInfo.next()) {
				// Get the CONTROL column
				control = courseInfo.getString("Control");
				sc.setControl(control);
			}
			// Get the users record of the course items
			ResultSet userSCORS = null;
			synchronized (stmtSelectUserSCO) {
				stmtSelectUserSCO.setString(1, userID);
				stmtSelectUserSCO.setString(2, courseID);
				stmtSelectUserSCO.setString(3, classID);
				userSCORS = stmtSelectUserSCO.executeQuery();
			}
			// Initialize variables that help with sequencing
			String scoID = new String();
			String lessonStatus = new String();
			String launch = new String();

			// If the user selected a menu option, handle appropriately
			if (wasAMenuRequest) {
				ResultSet MenuInfo = null;
				synchronized (stmtSelectItemInfo) {
					stmtSelectItemInfo.setString(1, courseID);
					MenuInfo = stmtSelectItemInfo.executeQuery();
				}
				// Move into the first record in the record set
				while (MenuInfo.next()) {
					// Get the TYPE column
					item_type = MenuInfo.getString("Type");
					identifier = MenuInfo.getString("Identifier");
					// the item is not an asset or sco, it is a contain
					// block
					if (("".equals(item_type))
							&& (identifier.equals(requestedSCO))) {
						// Launch the next sco or item that is the first
						// child
						// of the block item.
						MenuInfo.next();
						requestedSCO = MenuInfo.getString("Identifier");
						empty_block = true;
					}
					if (empty_block)
						break;
				}
				// Handle appropriately for a menu request
				// Get the last sco id that was taken
				// Loop through to find the next one to launch
				while (userSCORS.next()) {
					scoID = userSCORS.getString("SCOID");
					lessonStatus = userSCORS.getString("LessonStatus");
					launch = userSCORS.getString("Launch");
					type = userSCORS.getString("Type");
					if (requestedSCO.equals(scoID)) {
						sc.setLaunch(launch);
						courseComplete = false;
						sc.setScoid(scoID);
						break;
					}
				}
				// insert the correct values in stmtUpdateUserSCO
				synchronized (stmtUpdateUserSCO) {
					stmtUpdateUserSCO.setString(1, "completed");
					stmtUpdateUserSCO.setString(2, scoID);
					stmtUpdateUserSCO.setString(3, courseID);
				}
				// If it is an asset, execute the query, marking the asset
				// as completed.
				if ((!(type == null)) && type.equals("asset")) {
					stmtUpdateUserSCO.executeUpdate();
				}
			} else // It was a next request, previous request, or first
			// launch of session (or auto)
			{
				// If its auto or first session
				if (wasFirstSession || (control.equals("auto"))) {
					// Launch the first item that is not in a completed
					// state
					while (userSCORS.next()) {
						scoID = userSCORS.getString("SCOID");
						lessonStatus = userSCORS.getString("LessonStatus");
						launch = userSCORS.getString("Launch");
						type = userSCORS.getString("Type");
						// Set nextItemToLaunch to the next incomplete sco
						// or asset
						if (!(lessonStatus.equalsIgnoreCase("completed"))
								&& !(lessonStatus.equalsIgnoreCase("passed"))
								&& !(lessonStatus.equalsIgnoreCase("failed"))) {
							sc.setLaunch(launch);
							courseComplete = false;
							sc.setScoid(scoID);
							break;
						}
					}
				} // Ends if it was the first time in for the session
				else if (wasANextRequest)// Its a next request
				{
					// Handle the next request
					// Get the last sco id that was taken
					String lastScoID = nowSCO;
					// Boolean to trigger the correct sco to launch
					boolean timeToLaunch = false;
					// Loop through to find the next one to launch
					while (userSCORS.next()) {
						// Launch the next sequential sco
						scoID = userSCORS.getString("SCOID");
						lessonStatus = userSCORS.getString("LessonStatus");
						launch = userSCORS.getString("Launch");
						type = userSCORS.getString("Type");
						if (timeToLaunch) {
							sc.setLaunch(launch);
							courseComplete = false;
							sc.setScoid(scoID);
							break;
						}

						if (lastScoID.equals(scoID)) {
							timeToLaunch = true;
						}
					}
					// insert the correct values in stmtUpdateUserSCO
					synchronized (stmtUpdateUserSCO) {
						stmtUpdateUserSCO.setString(1, "completed");
						stmtUpdateUserSCO.setString(2, scoID);
						stmtUpdateUserSCO.setString(3, courseID);
					}
					// Execute the query, marking the asset as completed.

					if ((!(type == null)) && type.equals("asset")) {
						stmtUpdateUserSCO.executeUpdate();
					}

				} // Ends if its a next request
				else if (wasAPrevRequest)// Its a previous request
				{
					// Handle the previous request
					String lastScoID = nowSCO;
					String prevScoID = new String();
					String prevScoLaunch = new String();
					boolean timeToLaunch = false;
					int count = 0;
					while (userSCORS.next()) {
						if (timeToLaunch) {
							// Launch the previous sequential sco or asset
							sc.setLaunch(prevScoLaunch);
							courseComplete = false;
							sc.setScoid(prevScoID);
							break;
						}
						// Get the previous scoID and launch
						prevScoID = scoID;
						prevScoLaunch = launch;
						// Get the new info
						scoID = userSCORS.getString("SCOID");
						lessonStatus = userSCORS.getString("LessonStatus");
						launch = userSCORS.getString("Launch");
						type = userSCORS.getString("Type");
						count++;// the first time through the loop, check to
						// see if
						// the request was made by the first sco in the
						// course
						if (scoID.equals(lastScoID)) {
							if (count == 1) {
								prevScoID = scoID;
								prevScoLaunch = launch;
							}
							timeToLaunch = true;
						}// end if
					}// end while
					if (!userSCORS.next()) {// Launch the previous
						// sequential sco or asset
						sc.setLaunch(prevScoLaunch);
						courseComplete = false;
						sc.setScoid(prevScoID);
					}
				}// end previous
			} // Ends if it was a button request
			// If the course is complete redirect to the course
			// complete page
			// sc.setLaunch(launch);
			//获取目录。
//			String sqlSelectUserSCO1 = "SELECT ci.identifier,ci.type,ci.title,ci.launch,sus.lessonstatus FROM (select * from sc_iteminfo where (type='sco' or type='asset') and CourseID = ?) ci left join SC_UserSCOInfo sus on ci.identifier = sus.scoid WHERE sus.UserID = ? AND sus.CourseID = ?  AND sus.CLASSID=? ORDER BY sus.Sequence";
			String sqlSelectUserSCO1 = "SELECT ci.identifier,ci.type,ci.title,ci.launch,sus.lessonstatus FROM (select * from sc_iteminfo where (type='sco' or type='asset') and CourseID = ?) ci "+
			"left join (select * from SC_UserSCOInfo where userid = ? AND CourseID = ?  AND CLASSID=?) sus  on ci.identifier = sus.scoid order by ci.sequence";
			PreparedStatement ps = conn.prepareStatement(sqlSelectUserSCO1);
			ps.setString(1, courseID);
			ps.setString(2, userID);
			ps.setString(3, courseID);
			ps.setString(4, classID);
			ResultSet rs = ps.executeQuery();
			List<ScormCourse> list = new ArrayList<ScormCourse>();
			while (rs.next()) {
				ScormCourse c = new ScormCourse()	;
				c.setScoid(rs.getString(1));
				c.setType(rs.getString(2));
				c.setTitle(rs.getString(3));
				c.setLaunch(rs.getString(4));
				c.setLessonStatus(rs.getString(5));
				list.add(c);
			}
			rs.close();
			rs = null;
			sc.setScoList(list);
			sc.setLessonStatus(lessonStatus);
			if(courseComplete&&(sc.getLaunch()==null||"".equals(sc.getLaunch().trim()))){
				if(list.size()>0){
					sc.setLaunch(list.get(0).getLaunch());
					sc.setScoid(list.get(0).getScoid());
				}
			}
			if(list!=null)
			for (int i = 0; i < list.size(); i++) {
				String scoid = list.get(i).getScoid();
				scoid= scoid==null?"":scoid;
				if(i==0&&scoid.equals(sc.getScoid())){
					sc.setHaspreSco(true);
				}
				if((i==list.size()-1)&&scoid.equals(sc.getScoid())){
					sc.setHasnextSco(true);
				}
			}
			
			sc.setCourseComplete(courseComplete + "");
			// Ends else display the please wait page
		} catch (Exception e) {
			logger.error("注册scorm课程出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(null, null, null);
		}
		return sc;
	}
}
