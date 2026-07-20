package com.sopia.openmeetings;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.common.SystemConfOp;

public class OmUtil {
	private static final Log logger = LogFactory.getLog(OmDaoImpl.class);

	public static Connection getConnection() throws ElException {
		Connection ct = null;
		FileInputStream fis=null;
		try {
			Properties ps = new Properties();

			// String path = Thread
			// .currentThread()
			// .getContextClassLoader()
			// .getResource("com/sopia/common/configs/database.properties")
			// .toURI().toString();
			String path = SystemConfOp.getPath()
					+ "/"
					+ SystemConfOp
							.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE)
					+ "_database.properties";
			fis=new FileInputStream(path);
			ps.load(fis);
			Class.forName(ps.getProperty("openmeetings.driverClass"));
			ct = DriverManager.getConnection(ps
					.getProperty("openmeetings.jdbcUrl"), ps
					.getProperty("openmeetings.user"), ps
					.getProperty("openmeetings.password"));
		} catch (Exception e) {
			logger.error("xx",e);
			throw new ElException("创建连接视频会议出错！");
		}finally{
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("创建连接视频会议流关闭出错！",e);
			}
		}
		return ct;
	}

	public static void closeConnectInfo(Connection ct, PreparedStatement ps,
			ResultSet rs) throws ElException {
		closeRs(rs);
		closePs(ps);
		closeCt(ct);
	}

	public static void closeRs(ResultSet rs) throws ElException {
		if (null != rs) {
			try {
				rs.close();
				rs = null;
			} catch (Exception e) {
				throw new ElException("关闭数据集合失败!", e);
			}
		}
	}

	public static void closeCt(Connection ct) throws ElException {
		if (null != ct) {
			try {
				ct.close();
				ct = null;
			} catch (Exception e) {
				throw new ElException("关闭数据库连接失败!", e);
			}
		}
	}

	public static void closePs(PreparedStatement ps) throws ElException {
		if (null != ps) {
			try {
				ps.close();
				ps = null;
			} catch (Exception e) {
				throw new ElException("关闭语句连接失败!", e);
			}
		}
	}

	public static String getSID() throws ElException {
		InputStream l_urlStream=null;
		java.io.BufferedReader l_reader=null;
		InputStreamReader isr=null;
		try {
			java.net.URL l_url = new java.net.URL(SystemConfOp
					.getValue("openmeetings.url")
					+ "/MethodGateway?service=userservice&method=getSession");
			java.net.HttpURLConnection l_connection = (java.net.HttpURLConnection) l_url
					.openConnection();
			l_connection.connect();
			l_urlStream = l_connection.getInputStream();
			isr=new java.io.InputStreamReader(l_urlStream);
			l_reader = new java.io.BufferedReader(isr);
			String sCurrentLine = " ";
			String sTotalString = " ";
			while ((sCurrentLine = l_reader.readLine()) != null)

			{
				sTotalString += sCurrentLine;
			}
			sTotalString = sTotalString.substring(sTotalString
					.indexOf("<session__id>") + 13, sTotalString
					.indexOf("</session__id>"));
			return sTotalString;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("xx",e);
			throw new ElException("获取sid出错！");
		}finally{
			try {
				if(l_reader!=null){
					l_reader.close();
				}
				if(isr!=null){
					isr.close();
				}
				if(l_urlStream!=null){
					l_urlStream.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error ("getSID方法中流关闭失败",e);
			}
		}
	}

	/**登陆系统
	 * @param username
	 * @param password
	 * @param sid
	 * @return
	 */
	public static String setUser(String username, String password, String sid) {
		try {
			java.net.URL l_url = new java.net.URL(
					SystemConfOp.getValue("openmeetings.url")
							+ "/MethodGateway?service=userservice&method=loginUser&SID="
							+ sid + "&username=" + username + "&userpass="
							+ password);
			java.net.HttpURLConnection l_connection = (java.net.HttpURLConnection) l_url
					.openConnection();
			l_connection.connect();
			InputStream l_urlStream = l_connection.getInputStream();
			java.io.BufferedReader l_reader = new java.io.BufferedReader(
					new java.io.InputStreamReader(l_urlStream));
			String sCurrentLine = " ";
			String sTotalString = " ";
			while ((sCurrentLine = l_reader.readLine()) != null)

			{
				sTotalString += sCurrentLine;
			}
			sTotalString = sTotalString.substring(sTotalString
					.indexOf("<long>") + 6, sTotalString.indexOf("</long>"));
			return sTotalString;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}

	// public static void main(String[] args) {
	// OmUtil ou = new OmUtil();
	// String sid = ou.getSID();
	// String res = ou.setUser("admin", "1",
	// sid);//res返回值为1说明你的账号具有管理员权限，否则获取不到!
	//		
	// String hash = ou.getSecureHashHash("test1", "testfirstname2", sid, 1);
	// }
	public static String getSecureHashHash(String username, String realname,
			String sid, int roomid, String usertype,int moderator) throws ElException {
		InputStream l_urlStream=null;
		java.io.BufferedReader l_reader=null;
		InputStreamReader isr=null;
		try {
			// service=userservice&method=setUserObjectAndGenerateRoomHash&SID=6a875b01f7eaeaa334e84458d29793e0&username=jglee&firstname=JG&lastname=LEE&profilePictureUrl=&email=jglee@nchc.org.tw&externalUserId=&externalUserType=&room_id=9&becomeModeratorAsInt=1&showAudioVideoTestAsInt=1
			java.net.URL l_url = new java.net.URL(
					SystemConfOp.getValue("openmeetings.url")
							+ "/MethodGateway?service=userservice&method=setUserObjectAndGenerateRoomHash&"
							+ "SID="
							+ sid
							+ "&username="
							+ URLEncoder.encode(username,"UTF-8")
							+ "&firstname="
							+  URLEncoder.encode(realname,"UTF-8")
							+ "&lastname=("
							+ URLEncoder.encode(username,"UTF-8")
							+ ")&profilePictureUrl=&email=&"
							+ "externalUserId=0&externalUserType="
							+ usertype
							+ "&room_id="
							+ roomid
							+ "&becomeModeratorAsInt="+moderator+"&showAudioVideoTestAsInt=1");
			java.net.HttpURLConnection l_connection = (java.net.HttpURLConnection) l_url
					.openConnection();
			l_connection.connect();
			l_urlStream = l_connection.getInputStream();
			isr=new java.io.InputStreamReader(l_urlStream);
			l_reader = new java.io.BufferedReader(isr);
			String sCurrentLine = "";
			String sTotalString = "";
			while ((sCurrentLine = l_reader.readLine()) != null)
			{
				sTotalString += sCurrentLine;
			}
			sTotalString = sTotalString
					.substring(sTotalString.indexOf("<string>") + 8,
							sTotalString.indexOf("</string>"));
			return sTotalString;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getSecureHashHash失败", e);
			throw new ElException(e);
		}finally{
			try {
				if(l_reader!=null){
					l_reader.close();
				}
				if(isr!=null){
					isr.close();
				}
				if(l_urlStream!=null){
					l_urlStream.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error ("getSecureHashHash方法中流关闭失败",e);
			}
		}
		//return "";
	}
}
