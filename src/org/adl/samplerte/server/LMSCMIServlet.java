/*******************************************************************************
 ** 
 ** Filename:  LMSCMIServlet.java
 **
 ** File Description:     
 **
 ** This class defines the LMSCMIServlet that is used to handle the server side 
 ** data model communication of the Sample RTE.
 **
 ** This servlet handles persistence of the AICC Data Model elements.
 ** Persistence is being handled via flat files and the 
 ** built in Java serialization mechanism rather than via a database.
 **
 ** This servlet works in conjunction with the LMS APIAdapter Applet in the
 ** org.adl.lms.client package that is part of this sample.
 **
 ** Author: ADL Technical Team
 **
 ** Contract Number:
 ** Company Name: CTC
 **
 ** Module/Package Name:
 ** Module/Package Description:
 **
 ** Design Issues:
 **
 ** Implementation Issues:
 ** Known Problems:
 ** Side Effects:
 **
 ** References: ADL SCORM
 **
/*******************************************************************************
 **
 ** Concurrent Technologies Corporation (CTC) grants you ("Licensee") a non-
 ** exclusive, royalty free, license to use, modify and redistribute this
 ** software in source and binary code form, provided that i) this copyright
 ** notice and license appear on all copies of the software; and ii) Licensee
 ** does not utilize the software in a manner which is disparaging to CTC.
 **
 ** This software is provided "AS IS," without a warranty of any kind.  ALL
 ** EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 ** IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NON-
 ** INFRINGEMENT, ARE HEREBY EXCLUDED.  CTC AND ITS LICENSORS SHALL NOT BE LIABLE
 ** FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 ** DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES.  IN NO EVENT WILL CTC  OR ITS
 ** LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 ** INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 ** CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 ** OR INABILITY TO USE SOFTWARE, EVEN IF CTC HAS BEEN ADVISED OF THE POSSIBILITY
 ** OF SUCH DAMAGES.
 **
 *******************************************************************************/

package org.adl.samplerte.server;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.adl.datamodels.SCODataManager;
import org.adl.datamodels.cmi.CMICore;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.DBConnection;

public class LMSCMIServlet extends HttpServlet {
	private static final Log logger = LogFactory.getLog(LMSCMIServlet.class);
	// These strings are being used to hold the location of the serialized core
	// data.
//	private String LMSSCODataFile = "\\LMSSampleDB\\User01\\Course01\\Lesson01\\scodata";
//	private String LMSUser01 = "\\LMSSampleDB\\User01\\Course01\\Lesson01";
	private String scoFile;
	private String userID;
	private String courseID;
	private String scoID;
	private boolean logoutFlag;
	private String classID;
	private SCODataManager scoData;
	private String courseid;
	
	// This controls display of log messages to the java console

	/***************************************************************************
	 * * * Method: doPost * Input: HttpServletRequest request,
	 * HttpServletResponse response * Output: none * * Description: * This
	 * method handles post messages to the servlet. This servlet will respond *
	 * to the following commands: * cmigetcat * cmiputcat * * A real LMS would
	 * probably want to handle each request as a seperate servlet, * but for the
	 * purpose of demonstrating a sample LMS it was easier to have a * single
	 * servlet. *
	 **************************************************************************/
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
//			HttpSession session = request.getSession(false);
			
			// Open the input stream and pull off the incomming command
			ObjectInputStream in = new ObjectInputStream(request
					.getInputStream());
			ObjectOutputStream out = new ObjectOutputStream(response
					.getOutputStream());
			String str = (String) in.readObject();
			String command = str.split("=-=",6)[0];
			userID = str.split("=-=",6)[1];
			scoID =  str.split("=-=",6)[2];
			courseID = str.split("=-=",6)[3];
			classID = str.split("=-=",6)[4];
			courseid= str.split("=-=",6)[5];
			scoFile = request.getSession().getServletContext().getRealPath("/")+ "/SampleRTEFiles/" + userID + "/" + classID+ "/" + courseID + "/"+ scoID;
			// Process the incomming command accordingly
			logger.error(str);
			if (command.equalsIgnoreCase("cmiputcat")) {
				logoutFlag = false;
				SCODataManager inSCOData = (SCODataManager) in.readObject();
				HandleData(inSCOData);
				if (logoutFlag == true) {
//					session.setAttribute("EXITFLAG", "true");
				} else {
//					session.removeAttribute("EXITFLAG");
				}
			} else if (command.equalsIgnoreCase("cmigetcat")) {
				FileInputStream fi = new FileInputStream(scoFile);
				ObjectInputStream file_in = new ObjectInputStream(fi);
				scoData = (SCODataManager) file_in.readObject();
				scoData.getCore().setSessionTime("00:00:00.0");

				file_in.close();
				out.writeObject(scoData);
			} else // invalid command sent, real LMS would handle this more
					// gracefully
			{
				String err_msg = "invalid command";
				out.writeObject(err_msg);
			}

			// Close the input and output streams
			in.close();
			out.close();
		} catch (Exception e) {
			logger.error("scorm学习错误！", e);
		}
	} // end doPost
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doGet(req, resp);
		this.doPost(req, resp);
	}
	/***************************************************************************
	 * * * Method: HandleData * Input: SCODataManager scoData * Output: none * *
	 * Description: * This method handles processing of the core data being sent
	 * from the lesson * to the LMS. The data needs to be processed and made
	 * persistant. * For this example LMS only the core data has been
	 * implemented. *
	 **************************************************************************/
	private void HandleData(SCODataManager inSCOData) {
		try {
			String lessonStatus = new String();
			String lessonExit = new String();
			String lessonEntry = new String();
			CMICore lmsCore = inSCOData.getCore();
			if (lmsCore.getExit().getValue().equalsIgnoreCase("logout")) {
				logoutFlag = true;
			}

			lessonStatus = lmsCore.getLessonStatus().getValue();
			lessonExit = lmsCore.getExit().getValue();
			lessonEntry = lmsCore.getEntry().getValue();
			inSCOData.setCore(lmsCore);
//			inSCOData.getCore().showData();

			// Write out the updated data to disk
			FileOutputStream fo = new FileOutputStream(scoFile);
			ObjectOutputStream out_file = new ObjectOutputStream(fo);
			out_file.writeObject(inSCOData);
			out_file.close();
			// .print("LMSCMIServlet updated LMS SCO data info.\n");

			// Now we need to update UserSCOInfo in DB
			Connection conn;
			PreparedStatement stmtUpdateUserSCO;
			PreparedStatement stmtSelectUserSCO;

			String sqlSelectUserSCO = "SELECT LessonStatus FROM SC_UserSCOInfo WHERE UserID = ? AND CourseID = ? AND SCOID = ? AND CLASSID=?";
			String sqlUpdateUserSCO = "UPDATE SC_UserSCOInfo SET LessonStatus = ?, Exit = ?, Entry = ? "
					+ "WHERE UserID = ? AND CourseID = ? AND SCOID = ? AND CLASSID=?";

//			String sqlSelectUserSCO = "SELECT * FROM SC_UserSCOInfo WHERE UserID = ? AND CourseID = ? AND SCOID = ?";

//			String driverName = "sun.jdbc.odbc.JdbcOdbcDriver";
//			String connectionURL = "jdbc:odbc:SampleRTE";

			//Class.forName(driverName).newInstance();
			conn = DBConnection.getConnection();//DriverManager.getConnection(connectionURL);

			stmtUpdateUserSCO = conn.prepareStatement(sqlUpdateUserSCO);
			stmtSelectUserSCO = conn.prepareStatement(sqlSelectUserSCO);
			ResultSet userSCORS = null;
			synchronized (stmtSelectUserSCO) {
				stmtSelectUserSCO.setString(1, userID);
				stmtSelectUserSCO.setString(2, courseID);
				stmtSelectUserSCO.setString(3, scoID);
				stmtSelectUserSCO.setString(4, classID);
				userSCORS = stmtSelectUserSCO.executeQuery();
			}
			if(userSCORS.next()){
				String ls = userSCORS .getString(1);
				if("passed".equals(ls)||"completed".equals(ls)||"browsed".equals(ls)){
					lessonStatus = ls;
				}
			}
			synchronized (stmtUpdateUserSCO) {
				stmtUpdateUserSCO.setString(1, lessonStatus);
				stmtUpdateUserSCO.setString(2, lessonExit);
				stmtUpdateUserSCO.setString(3, lessonEntry);
				stmtUpdateUserSCO.setString(4, userID);
				stmtUpdateUserSCO.setString(5, courseID);
				stmtUpdateUserSCO.setString(6, scoID);
				stmtUpdateUserSCO.setString(7, classID);
				stmtUpdateUserSCO.executeUpdate();
			}
			//设置课程是否学完
//			try {
//				PreparedStatement ps = conn.prepareStatement("call sc_cpage (?,?,?,?,?)");
//				ps.setInt(1, Integer.parseInt(userID));
//				ps.setInt(2,-1);
//				ps.setInt(3, Integer.parseInt(courseid));
//				ps.setInt(4, Integer.parseInt(classID));
//				ps.setInt(5, 0);
//				ps.executeUpdate();
//			} catch (Exception e) {
//				logger.error("培训班中学习失败", e);
//			}
			
//			ResultSet userSCORS = null;
//			synchronized (stmtSelectUserSCO) {
//				stmtSelectUserSCO.setString(1, userID);
//				stmtSelectUserSCO.setString(2, courseID);
//				stmtSelectUserSCO.setString(3, scoID);
//				userSCORS = stmtSelectUserSCO.executeQuery();
//			}
//			userSCORS.next();
			// String newStatus = userSCORS.getString( "LessonStatus" );
		} catch (Exception e) {
			logger.error("scorm学习处理数据失败", e);
		}
	} // end HandleData
}
