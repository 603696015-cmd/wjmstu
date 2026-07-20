package com.sopia.newsandmess.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElQuerySql;
import com.sopia.common.OracleBlob;
import com.sopia.common.SendMail;
import com.sopia.common.SendMsgUtil;
import com.sopia.common.logger.ElLogger;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.DUConstants;
import com.sopia.duman.dao.UserDao;
import com.sopia.duman.dao.impl.UserDaoImpl;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.newsandmess.NmConstants;
import com.sopia.newsandmess.dao.MessageDao;
import com.sopia.newsandmess.entities.Message;
import com.sopia.newsandmess.entities.Pop;
import com.sopia.questionman.dao.StuffDao;
import com.sopia.questionman.entities.StuffLib;

public class MessageDaoImpl implements MessageDao {
	private static final Log logger = LogFactory.getLog(MessageDaoImpl.class);

	public void messSend(Message mess, ELUser from, List<ELUser> to)
			throws ElException {
		for (int i = 0; i < to.size(); i++) {
			mess.setMess_from(from);
			mess.setMess_to(to.get(i));
			insertMess(mess);
			
		}
	} 
	
	//插入带有附件的消息
	public void messSend1(Message mess, ELUser from, List<ELUser> to,String staddr[],String sttitle[])
		throws ElException {
		int mess_id = 0;
		for (int i = 0; i < to.size(); i++) {
			mess.setMess_from(from);
			mess.setMess_to(to.get(i));
			mess_id = insertMess(mess);
			if (null != staddr) {
				for (int j = 0; j < staddr.length; j++) {
					String title = sttitle[j]==null||"".equals(sttitle[j].trim())?staddr[j].substring(staddr[j].lastIndexOf("/")+1):sttitle[j];
					this.addKstuff(staddr[j], mess_id ,title);
				}
			}
			
		}
	} 

	public int insertMess(Message mess) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into MESSAGE(mess_title,"
					+ "mess_content,mess_from,mess_to,mess_time)"
					+ "values(?,empty_blob(),?,?,?)");
			ps.setString(1, mess.getMess_title());
//			ps.setString(2, mess.getMess_content());
			ps.setInt(2, mess.getMess_from().getId());
			ps.setInt(3, mess.getMess_to().getId());
			ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			ps.executeUpdate();
			
			OracleBlob setblob = new OracleBlob(ct,"mess_sequence","message","mess_id","mess_content",mess.getMess_content(),"添加消息失败");
			setblob.addContent(); 
			
//			rs.close();
//			rs =null;
			ps.close();
			ps = null;
			ps = ct.prepareStatement("select mess_sequence.currval from dual ");
			rs = ps.executeQuery();  
			if (rs.next()){
				mess.setMess_id(rs.getInt(1)); 
			}
		} catch (Exception e) {
			logger.error("消息发送失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mess.getMess_id();
	}
	
	/**
	 * 更新短消息为已回复
	 * @param id
	 * @throws ElException
	 */
	public void updateMessReply(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update message set isreply=1 where mess_id=?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新短消息为已回复失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void insertMess2(Message mess) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into MESSAGE(mess_title,"
					+ "mess_content,mess_from,mess_to,mess_time,auditType,forumid)"
					+ "values(?,empty_blob(),?,?,?,?,?)");
			ps.setString(1, mess.getMess_title());
			ps.setInt(2, mess.getMess_from().getId());
			ps.setInt(3, mess.getMess_to().getId());
			ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			ps.setInt(5, mess.getAuditType());
			ps.setInt(6, mess.getForumid());
			ps.executeUpdate();
			
			OracleBlob setblob = new OracleBlob(ct,"mess_sequence","message","mess_id","mess_content",mess.getMess_content(),"添加消息失败");
			setblob.addContent(); 
		} catch (Exception e) {
			logger.error("消息发送失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 在审核通过，不通过时发送短消息
	 * 
	 * @param title
	 *            操作的标题
	 * @param opMod
	 *            操作模块
	 * @param opType
	 *            操作类型
	 * @param messFromId
	 *            消息发送者id
	 * @param messType
	 *            消息类型
	 * @throws ElException
	 */
	public boolean insertMessInApply(String title, int opMod, int opType,
			int messFromId, int messType) throws ElException {
		ELUser elUser = new UserDaoImpl().getUserById(messFromId);
		String modTitle = "";
		String typeTitle = "";
		String messTitle = "";
		if (opMod == 1) {
			modTitle = "新闻";
		} else if (opMod == 6) {
			modTitle = "课程";
		} else if (opMod == 7) {
			modTitle = "培训班";
		} else if (opMod == 8) {
			modTitle = "考场";
		}else if(opMod == 3){
			modTitle = "帖子";
		}
		if (messType == 1) {
			typeTitle = "初审,通过了!!!";
			messTitle = "初审通过短消息！";
		} else if (messType == 2) {
			typeTitle = "初审,不通过!!!";
			messTitle = "初审不通过短消息！";
		} else if (messType == 3) {
			typeTitle = "终审,通过了!!!";
			messTitle = "终审通过短消息！";
		} else if (messType == 4) {
			typeTitle = "终审,不通过!!!";
			messTitle = "终审不通过短消息！";
		} else if(messType == 5){
			typeTitle = "终审,不通过!!!";
			messTitle = "帖子回复短消息！";
		}
		Message message = new Message();
		ELUser mess_to = null;
		message.setMess_title(messTitle);
		if(opMod == 3){ 
			message.setMess_content(elUser.getRealname()+"您好，您发布的提问"+"[" + title + "]已经有人进行回答了");
			mess_to = new ELUser(elUser.getId());
		}else{
			message.setMess_content("你申请的" + modTitle + "[" + title + "]得到["
					+ elUser.getRealname() + "]的" + typeTitle);
			mess_to = new ElLogger().getSyslogInUser(opMod, opType, title);
			if (mess_to == null) {
				message.setMess_title("短消息发送失败！");
				message.setMess_content("您审核的" + modTitle + "[" + title
						+ "]找不到申请审核者，原因可能是由于在审核中对审核的内容做过修改！！！");
				message.setMess_to(new ELUser(messFromId));
				this.insertMess(message);
				return false;
			}
		}
		message.setMess_time(new Date());
		message.setMess_from(new ELUser(messFromId));
		
		// if(messType==1||messType==2){
		// mess_to=new ElLogger().getSyslogInUser(opMod, opType, title);
		// }else if(messType==3||messType==4){
		// mess_to=new ElLogger().getSyslogInUser(opMod, opType, title);
		// }
		
		message.setMess_to(mess_to);
		// this.insertMess(message);
		if (modTitle.equals("考场")) {
			message.setAuditType(1);
		} else if (modTitle.equals("培训班")) {
			message.setAuditType(2);
		}else if (modTitle.equals("帖子")) {
			message.setAuditType(3);
		}
		this.insertMess2(message);
		return true;
	}
	//帖子回复
	public boolean insertMessInApply(String title, int opMod, int opType,
			int messFromId, int messType,int forumid) throws ElException {
		ELUser elUser = new UserDaoImpl().getUserById(messFromId);
		String modTitle = "";
		String typeTitle = "";
		String messTitle = "";
		if (opMod == 1) {
			modTitle = "新闻";
		} else if (opMod == 6) {
			modTitle = "课程";
		} else if (opMod == 7) {
			modTitle = "培训班";
		} else if (opMod == 8) {
			modTitle = "考场";
		}else if(opMod == 3){
			modTitle = "帖子";
		}
		if (messType == 1) {
			typeTitle = "初审,通过了!!!";
			messTitle = "初审通过短消息！";
		} else if (messType == 2) {
			typeTitle = "初审,不通过!!!";
			messTitle = "初审不通过短消息！";
		} else if (messType == 3) {
			typeTitle = "终审,通过了!!!";
			messTitle = "终审通过短消息！";
		} else if (messType == 4) {
			typeTitle = "终审,不通过!!!";
			messTitle = "终审不通过短消息！";
		} else if(messType == 5){
			typeTitle = "终审,不通过!!!";
			messTitle = "帖子回复短消息！";
		}
		Message message = new Message();
		ELUser mess_to = null;
		message.setMess_title(messTitle);
		if(opMod == 3){ 
			message.setMess_content(elUser.getRealname()+"您好，您发布的提问"+"[" + title + "]已经有人进行回答了");
			message.setForumid(forumid);
			mess_to = new ELUser(elUser.getId());
		}else{
			message.setMess_content("你申请的" + modTitle + "[" + title + "]得到["
					+ elUser.getRealname() + "]的" + typeTitle);
			mess_to = new ElLogger().getSyslogInUser(opMod, opType, title);
			if (mess_to == null) {
				message.setMess_title("短消息发送失败！");
				message.setMess_content("您审核的" + modTitle + "[" + title
						+ "]找不到申请审核者，原因可能是由于在审核中对审核的内容做过修改！！！");
				message.setMess_to(new ELUser(messFromId));
				this.insertMess(message);
				return false;
			}
		}
		message.setMess_time(new Date());
		message.setMess_from(new ELUser(messFromId));
		
		// if(messType==1||messType==2){
		// mess_to=new ElLogger().getSyslogInUser(opMod, opType, title);
		// }else if(messType==3||messType==4){
		// mess_to=new ElLogger().getSyslogInUser(opMod, opType, title);
		// }
		
		message.setMess_to(mess_to);
		// this.insertMess(message);
		if (modTitle.equals("考场")) {
			message.setAuditType(1);
		} else if (modTitle.equals("培训班")) {
			message.setAuditType(2);
		}else if (modTitle.equals("帖子")) {
			message.setAuditType(3);
		}
		this.insertMess2(message);
		return true;
	}
	

	/**
	 * /** 在审核通过，不通过时发送短消息
	 * 
	 * @param title
	 *            操作的标题
	 * @param opMod
	 *            操作模块（6课程 , 7培训班 ,8考场）
	 * @param messFromId
	 *            消息发送者id
	 * @param elusers
	 *            接收者集合
	 * @param start
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @param id
	 *            id
	 * @return
	 * @throws ElException
	 */
	public boolean insertMessInUser(String title, int opMod, int messFromId,
			List<ELUser> elusers, Timestamp start, Timestamp end, int id)
			throws ElException {
		ELUser elUser = new UserDaoImpl().getUserById(messFromId);
		String modTitle = "";
		String messTitle = "";
		String url = "";
		if (opMod == 6) {
			modTitle = "课程";
		} else if (opMod == 7) {
			modTitle = "培训班";
			url = "myelclass_view.action?elclass.id=" + id;
		} else if (opMod == 8) {
			modTitle = "考场";
			url = "quizpaperinit.action?myroom.examroom.id=" + id;
		}
		messTitle = "您参加的" + modTitle + " （" + title + "）开通了";
		Message message = new Message();
		message.setMess_title(messTitle);
		message
				.setMess_content("该" + modTitle + "[" + title + "]得到["
						+ elUser.getRealname() + "]的开通,您可以进入[" + title + "]"
						+ modTitle);
		message.setMess_content("您参加的" + modTitle + "（" + title + "）经管理员（"
				+ elUser.getRealname() + "）审核通过，现已开通。" + modTitle + "（" + title
				+ "）的有效期是" + start + "至" + end + "。" + "<a href=" + url
				+ " class='textbg2'>查看详情。</a>");
		message.setMess_time(new Date());
		message.setMess_from(new ELUser(messFromId));
		for (ELUser u : elusers) { // 多个员
			message.setMess_to(u);
			this.insertMess(message);
		}
		return true;
	}

	public int messToCount(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(*) from MESSAGE m where m.mess_to = ?  and m.mess_from !=0 and m.recDel=0  ");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			logger.error("消息数量！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<Message> listMessTo(int id, int pageNow, int pageSize)
			throws ElException {
		List<Message> messes = new ArrayList<Message>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
//			ps = ct.prepareStatement(ElQuerySql
//					.getSQL(NmConstants.MESS_TO_LIST));
			ps = ct.prepareStatement("select * from (select t.*,rownum rn from (" +
					"select m.mess_id,m.mess_title,m.mess_content,m.is_read,m.mess_time, f.id,f.realname,m.isreply from MESSAGE m " +
					" left join ElUser f on  m.mess_from = f.id where m.mess_to = ? and m.mess_from !=0 and m.recDel=0 order by m.mess_time desc, m.is_read desc" +
					") t where rownum <=?)where rn>=?");
			ps.setInt(1, id);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Message mess = new Message();
				mess.setMess_id(rs.getInt(1));
				mess.setMess_title(rs.getString(2));
				mess.setMess_content(rs.getString(3));
				mess.setIs_read(rs.getBoolean(4));
				mess.setMess_time(rs.getTimestamp(5));
				ELUser from = new ELUser(rs.getInt(6), rs.getString(7));
				mess.setMess_from(from);
				mess.setIsreply(rs.getInt(8));
				messes.add(mess);
			}
		} catch (Exception e) {
			logger.error("收件箱！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return messes;
	}

	public int messFromCount(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(*) from MESSAGE m where m.mess_from = ? and sendDel= 0 ");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			logger.error("发件箱数量！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<Message> listMessFrom(int id, int pageNow, int pageSize)
			throws ElException {
		List<Message> messes = new ArrayList<Message>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(NmConstants.MESS_FROM_LIST));

			ps.setInt(1, id);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);

			rs = ps.executeQuery();
			while (rs.next()) {
				Message mess = new Message();
				mess.setMess_id(rs.getInt(1));
				mess.setMess_title(rs.getString(2));
				mess.setMess_content(rs.getString(3));
				mess.setIs_read(rs.getBoolean(4));
				mess.setMess_time(rs.getTimestamp(5));
				ELUser to = new ELUser(rs.getInt(6), rs.getString(7));
				mess.setMess_to(to);
				messes.add(mess);
			}
		} catch (Exception e) {
			logger.error("收件箱！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return messes;
	}

	public void messDelete(int mess_id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from MESSAGE  where mess_id = ?");
			ps.setInt(1, mess_id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public Message getMessById(int mess_id) throws ElException {
		Message mess = new Message();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select m.mess_id,m.mess_title,m.mess_content,m.is_read,"
							+ "m.mess_time,f.id,f.realname,t.id,t.realname,m.auditType,m.forumid from MESSAGE m "
							+ "left join ElUser f on m.mess_from = f.id "
							+ "left join ElUser t on m.mess_to = t.id "
							+ "where m.mess_id = ? ");
			ps.setInt(1, mess_id);
			rs = ps.executeQuery();
			if (rs.next()) {
				mess.setMess_id(rs.getInt(1));
				mess.setMess_title(rs.getString(2));
				mess.setMess_content(new OracleBlob().getContent(rs.getBlob(3)));
				mess.setIs_read(rs.getBoolean(4));
				mess.setMess_time(rs.getTimestamp(5));
				ELUser from = new ELUser(rs.getInt(6), rs.getString(7));
				ELUser to = new ELUser(rs.getInt(8), rs.getString(9));
				mess.setMess_to(to);
				mess.setMess_from(from);
				mess.setAuditType(rs.getInt("auditType"));
				mess.setForumid(rs.getInt("forumid"));
			}
			DBConnection.closeConnectInfo(ct, ps, rs);
			
			List<StuffLib> stuffs = new ArrayList<StuffLib>();
			StuffLib stuff = null;
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from message_stuff where messageid=?");
			ps.setInt(1, mess_id);
			rs = ps.executeQuery();
			while(rs.next()){
				stuff = new StuffLib();
				stuff.setTitle(rs.getString("title"));
				stuff.setStuff_path(rs.getString("stuffaddr"));
				stuffs.add(stuff);
			}
			mess.setStuffs(stuffs);
		} catch (Exception e) {
			logger.error("得到消息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mess;
	}

	public boolean checkRecDel(int mess_id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select recDel from MESSAGE m where m.mess_id = ? ");
			ps.setInt(1, mess_id);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getBoolean(1);
			}
		} catch (Exception e) {
			logger.error("检测收件删除！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public boolean checkSendDel(int mess_id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select sendDel from MESSAGE m where m.mess_id = ? ");
			ps.setInt(1, mess_id);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getBoolean(1);
			}
		} catch (Exception e) {
			logger.error("检测发件删除！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public void deleteRec(int mess_id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update MESSAGE set recDel = 1 where mess_id = ?");
			ps.setInt(1, mess_id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("设置收件删除！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void deleteSend(int mess_id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update MESSAGE set sendDel = 1 where mess_id = ?");
			ps.setInt(1, mess_id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("设置发件删除！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void setIsRead(int mess_id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update MESSAGE set is_read = 1 where mess_id = ?");
			ps.setInt(1, mess_id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("设置已读！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<Message> listMessNew(int mem_id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Message> messes = new ArrayList<Message>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(NmConstants.MESS_NEW_LIST));
			ps.setInt(1, mem_id);
			rs = ps.executeQuery();
			while (rs.next()) {
				Message mess = new Message();
				mess.setMess_id(rs.getInt(1));
				mess.setMess_title(rs.getString(2));
				mess.setMess_content(rs.getString(3));
				mess.setIs_read(rs.getBoolean(4));
				mess.setMess_time(rs.getTimestamp(5));
				ELUser from = new ELUser(rs.getInt(6), rs.getString(7));
				mess.setMess_from(from);
				messes.add(mess);
			}
		} catch (Exception e) {
			logger.error("最新未读消息！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return messes;
	}

	/**
	 * 获取所有消息（分页）
	 * 
	 * @param mem_id
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Message> listMessNew(int mem_id, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Message> messes = new ArrayList<Message>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" select * from (select t.*,rownum rn from (select m.mess_id,m.mess_title,m.mess_content,m.is_read,m.mess_time, t.id,t.realname  from MESSAGE m left join ElUser t on m.mess_from = t.id where m.mess_to = ? and recdel=0 and is_read=0 order by mess_time desc) t where rownum<=?) where rn>=?");
			ps.setInt(1, mem_id);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			String messTitle = "";
			while (rs.next()) {
				messTitle = rs.getString(2);
				if (messTitle != null && messTitle.length() > 11) {
					messTitle = messTitle.substring(0, 11) + "...";
				}
				Message mess = new Message();
				mess.setMess_id(rs.getInt(1));
				mess.setMess_title(messTitle);
				mess.setMess_content(rs.getString(3));
				mess.setIs_read(rs.getBoolean(4));
				mess.setMess_time(rs.getTimestamp(5));
				ELUser from = new ELUser(rs.getInt(6), rs.getString(7));
				mess.setMess_from(from);
				messes.add(mess);
			}
		} catch (Exception e) {
			logger.error("获取所有消息（分页）出错!", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return messes;
	}
	
	/**
	 * 获取所有消息（分页）
	 * 
	 * @param mem_id
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Message> listMessNewAll(int mem_id, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Message> messes = new ArrayList<Message>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" select * from (select t.*,rownum rn from (select m.mess_id,m.mess_title,m.mess_content,m.is_read,m.mess_time, t.id,t.realname  from MESSAGE m left join ElUser t on m.mess_from = t.id where m.mess_to = ? and recdel=0 order by mess_time desc) t where rownum<=?) where rn>=?");
			ps.setInt(1, mem_id);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			String messTitle = "";
			while (rs.next()) {
				messTitle = rs.getString(2);
				if (messTitle != null && messTitle.length() > 11) {
					messTitle = messTitle.substring(0, 11) + "...";
				}
				Message mess = new Message();
				mess.setMess_id(rs.getInt(1));
				mess.setMess_title(messTitle);
				mess.setMess_content(rs.getString(3));
				mess.setIs_read(rs.getBoolean(4));
				mess.setMess_time(rs.getTimestamp(5));
				ELUser from = new ELUser(rs.getInt(6), rs.getString(7));
				mess.setMess_from(from);
				messes.add(mess);
			}
		} catch (Exception e) {
			logger.error("获取所有消息（分页）出错!", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return messes;
	}

	/**
	 * 获取所有消息所有数量
	 * 
	 * @param mem_id
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public int listMessNewCount(int mem_id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" select count(*) from MESSAGE m left join ElUser t on m.mess_from = t.id where m.mess_to = ? ");
			ps.setInt(1, mem_id);
			rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取所有消息数量出错!", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 获取未读短消息数量
	 * 
	 * @param mem_id
	 * @return
	 * @throws ElException
	 */
	public int getMessNoCount(int mem_id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" select count(*) from message where is_read=0 and mess_to=? and recdel=0");
			ps.setInt(1, mem_id);
			rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取未读短消息数量出错!", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 获取已读短消息数量
	 * 
	 * @param mem_id
	 * @return
	 * @throws ElException
	 */
	public int getMessYesCount(int mem_id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(*) from message where is_read=1 and mess_to=? and recdel=0");
			ps.setInt(1, mem_id);
			rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取已读短消息数量出错!", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<ELUser> getUserByDepId(int depid, int subdep, ELUser eu,
			int pageNow, int pageSize) throws ElException {
		List<ELUser> eus = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String username = "";
			String realname = "";
			if (null != eu) {
				if (null != eu.getUsername())
					username = eu.getUsername().trim();
				if (null != eu.getRealname())
					realname = eu.getRealname().trim();
			}
			ct = DBConnection.getConnection();
			if (subdep == ElConstants.SUBOP_YES) {
				Department dep = new Department();
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
				ps.setInt(1, depid);
				rs = ps.executeQuery();
				if (rs.next()) {
					dep.setId(rs.getInt(1));
					dep.setLid(rs.getInt(2));
					dep.setRid(rs.getInt(3));
				}
				ps.close();
				rs.close();
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(NmConstants.MESS_USER_SUBS_BYDEPIDANDOS));
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setInt(3, dep.getLid());
				ps.setInt(4, dep.getRid());
				ps.setInt(5, pageNow);
				ps.setInt(6, pageSize);
			} else {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(NmConstants.MESS_USER_BYDEPIDANDOS));
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setInt(3, depid);
				ps.setInt(4, pageNow);
				ps.setInt(5, pageSize);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser elUser = new ELUser();
				elUser.setId(rs.getInt(1));
				elUser.setUsername(rs.getString(2));
				elUser.setRealname(rs.getString(3));
				// TODO 消息里的角色
				elUser.setDepartment(new Department(rs.getInt(8), rs
						.getString(9)));

				elUser.setValid(rs.getBoolean(10));
				eus.add(elUser);
			}
		} catch (Exception e) {
			logger.error("用户列表搜索失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eus;
	}

	public int getUserByDepIdSize(int depid, int subdep, ELUser eu)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String username = "";
			String realname = "";
			if (null != eu) {
				if (null != eu.getUsername())
					username = eu.getUsername().trim();
				if (null != eu.getRealname())
					realname = eu.getRealname().trim();
			}
			ct = DBConnection.getConnection();
			if (subdep == ElConstants.SUBOP_YES) {
				Department dep = new Department();
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
				ps.setInt(1, depid);
				rs = ps.executeQuery();
				if (rs.next()) {
					dep.setId(rs.getInt(1));
					dep.setLid(rs.getInt(2));
					dep.setRid(rs.getInt(3));
				}
				ps.close();
				rs.close();
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(NmConstants.MESS_USER_SUBS_BYDEPIDANDOS_SIZE));
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setInt(3, dep.getLid());
				ps.setInt(4, dep.getRid());
			} else {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(NmConstants.MESS_USER_BYDEPIDANDOS_SIZE));
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setInt(3, depid);
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("用户列表搜索失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 最新消息数
	 * 
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int getNewMesscount(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(*) from MESSAGE m left join ElUser t on m.mess_from = t.id where m.mess_to = ? and m.recDel=0 and m.is_read=0 ");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("通过用户id查询用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;

	}
	
	/**
	 * 添加弹窗信息
	 * @param pop
	 * @throws ElException
	 */
	public void addPop(Pop pop,String users) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			//ps = ct.prepareStatement("insert into popInfo(poptitle,popcontent,createtime,creater,sendmanner,sendvalue,status) values(?,?,?,?,?,?,?)");
			ps = ct.prepareStatement("insert into popInfo(poptitle,createtime,creater,sendmanner,sendvalue,status,popcontent) values(?,?,?,?,?,?,empty_blob())");
			ps.setString(1, pop.getPopTitle());
			//ps.setString(2, pop.getPopContent());
			//ps.setTimestamp(3, pop.getCreatetime());
			ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.setInt(3, pop.getCreate().getId());
			ps.setInt(4, pop.getSendmanner());
			ps.setString(5, pop.getSendvalue());
			ps.setInt(6, 0);
			ps.executeUpdate();
			OracleBlob setblob = new OracleBlob(ct,"popInfo_sequence","popInfo","id","popcontent",pop.getPopContent(),"添加弹窗信息失败");
			setblob.addContent();
			String[] str=new String[]{users};
			if(!"".equals(users)&&users.indexOf(",")!=-1){
				str=users.split(",");
			}
			ps.close();
			//获取popid
			ps=ct.prepareStatement("select popInfo_sequence.Currval from dual");
			rs=ps.executeQuery();
			if(rs.next()){
				pop.setId(rs.getInt(1));
			}
			if(null!=users&&!"".equals(users)){
				for (int i = 0; i < str.length; i++) {
					this.addPop_user(pop.getId(), Integer.parseInt(str[i]));
				}
			}
			//然后添加到弹窗-value表
			String[] sendValues=pop.getSendvalue().split(",");
			for (int i = 0; i < sendValues.length; i++) {
				this.addPop_sendvalue(pop.getId(), pop.getSendmanner(), Integer.parseInt(sendValues[i]));
			}
		} catch (Exception e) {
			logger.error("添加弹窗信息败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 添加弹窗-值（用户，部门，考场，培训班）
	 * @param popid
	 * @param sendmanner
	 * @param value
	 * @throws ElException
	 */
	private void addPop_sendvalue(int popid,int sendmanner,int value) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql="";
			if(sendmanner==0){
				sql="insert into pop_eluser(popid,userid) values(?,?)";
			}else if(sendmanner==1){
				sql="insert into pop_department(popid,depid) values(?,?)";
			}else if(sendmanner==2){
				sql="insert into pop_room(popid,roomid) values(?,?)";
			}else{
				sql="insert into pop_class(popid,classid) values(?,?)";
			}
			ps = ct.prepareStatement(sql);
			ps.setInt(1, popid);
			ps.setInt(2, value);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加弹窗用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 获取用户所分配给他的弹窗信息并添加到弹窗学员表
	 * @param userid
	 * @throws ElException
	 */
	public void listSetUserInPop(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select p1.id,p1.poptitle from (select * from popinfo  where sendmanner = 0 and status = 1) p1 where "+
				" exists ( select u.id from eluser u where u.id = ? and u.id in (select userid from  pop_eluser ppr where ppr.popid = p1.id )) "+
				" union"+
				" select p1.id,p1.poptitle from (select * from popinfo  where sendmanner = 1 and status = 1) p1 where"+ 
				" exists (select id from department dep where  (select lid from department d left join eluser u on d.id = u.depid where u.id = ?) >=dep.lid and "+
				" (select  rid from department d left join eluser u on d.id = u.depid where u.id = ?) <=dep.rid and dep.id in (select depid from  pop_department ppr where ppr.popid = p1.id ))"+
				" union"+
				" select p1.id,p1.poptitle from (select * from popinfo  where sendmanner = 2 and status = 1) p1 where "+
				" exists (select roomid from study_room sr where sr.userid=? and sr.roomid in (select roomid from  pop_room ppr where ppr.popid = p1.id ))"+
				" union"+
				" select p1.id,p1.poptitle from (select * from popinfo  where sendmanner = 3 and status = 1) p1 where "+
				" exists (select classid from study_class sc where sc.userid=? and sc.classid in (select classid from pop_class ppr where ppr.popid = p1.id ))");
			ps.setInt(1, userid);
			ps.setInt(2, userid);
			ps.setInt(3, userid);
			ps.setInt(4, userid);
			ps.setInt(5, userid);
			rs=ps.executeQuery();
			while(rs.next()){
				//判断该弹窗是否已经添加到pop_user表中
				if(!this.checkPop_user(rs.getInt(1), userid)){
					this.addPop_user(rs.getInt(1), userid);//添加弹窗学员信息
				}
			}
		} catch (Exception e) {
			logger.error("获取用户所分配给他的弹窗信息并添加到弹窗学员表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 检测该弹窗是否已经添加进去
	 * @param popid
	 * @param userid
	 * @throws ElException
	 */
	public boolean checkPop_user(int popid,int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select popid from pop_user where popid=? and userid=?");
			ps.setInt(1, popid);
			ps.setInt(2, userid);
			rs=ps.executeQuery();
			if(rs.next()){
				return true;
			}
		} catch (Exception e) {
			logger.error("检测该弹窗是否已经添加进去失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}
	/**
	 * 添加弹窗用户
	 * @param popid
	 * @param userid
	 * @throws ElException
	 */
	public void addPop_user(int popid,int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into pop_user(popid,userid) values(?,?)");
			ps.setInt(1, popid);
			ps.setInt(2, userid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加弹窗用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 把集合转换成字符串
	 * @param objList
	 * @return
	 */
	public String getStrToList(List<Department> objList,String split){
		String result="";
		for (int i = 0; i < objList.size(); i++) {
			if(!result.equals("")){
				result+=split;
			}
			result+=objList.get(i).getId();
		}
		return result;
	}
	/**
	 * 把集合转换成字符串
	 * @param objList
	 * @return
	 */
	public String getStrToList2(List<ExamRoom> objList,String split){
		String result="";
		for (int i = 0; i < objList.size(); i++) {
			if(!result.equals("")){
				result+=split;
			}
			result+=objList.get(i).getId();
		}
		return result;
	}
	/**
	 * 把集合转换成字符串
	 * @param objList
	 * @return
	 */
	public String getStrToList3(List<ElClass> objList,String split){
		String result="";
		for (int i = 0; i < objList.size(); i++) {
			if(!result.equals("")){
				result+=split;
			}
			result+=objList.get(i).getId();
		}
		return result;
	}
	/**
	 * 把集合转换成字符串
	 * @param objList
	 * @return
	 */
	public String getStrToList4(List<ELUser> objList,String split){
		String result="";
		for (int i = 0; i < objList.size(); i++) {
			if(!result.equals("")){
				result+=split;
			}
			result+=objList.get(i).getId();
		}
		return result;
	}
	/**
	 * 获取我发布的弹窗信息
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Pop> getMyPopList(int userid,Pop tpop, int pageNow, int pageSize)
	throws ElException {
		List<Pop> pops = new ArrayList<Pop>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		StringBuffer sb=new StringBuffer();
		if(tpop!=null){
			if(tpop.getSendmanner()==0){
				sb.append(" and pop.sendmanner=0");
			}else if(tpop.getSendmanner()==1){
				sb.append(" and pop.sendmanner=1");
			}
			if(tpop.getStatus()==0){
				sb.append(" and pop.status=0");
			}else if(tpop.getStatus()==1){
				sb.append(" and pop.status=1");
			}
		}else{
			tpop=new Pop();
			tpop.setPopTitle("");
		}
		try {
			ct = DBConnection.getConnection();
			//ps = ct.prepareStatement("select * from (select t.*, rownum rn from (select pop.id popid,pop.poptitle,pop.createtime,pop.sendmanner,pop.status,eu.id euid,eu.realname from popInfo pop left join eluser eu on pop.creater=eu.id where creater=?) t where rownum <= ?) where rn >= ?  ");
			ps = ct.prepareStatement("select * from (select t.*, rownum rn from (select pop.id popid,pop.poptitle,pop.createtime,pop.sendmanner,pop.status,eu.id euid,eu.realname from popInfo pop left join eluser eu on pop.creater=eu.id where creater=? and pop.poptitle like ? "+sb.toString()+" ) t where rownum <= ?) where rn >= ?  ");
			ps.setInt(1, userid);
			ps.setString(2, "%"+tpop.getPopTitle()+"%");
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			Pop pop=null;
			while (rs.next()) {
				pop = new Pop();
				pop.setId(rs.getInt("popid"));
				pop.setPopTitle(rs.getString("poptitle"));
				pop.setCreatetime(rs.getTimestamp("createtime"));
				pop.setSendmanner(rs.getInt("sendmanner"));
				pop.setStatus(rs.getInt("status"));
				pop.setCreate(new ELUser(rs.getInt("euid"),rs.getString("realname")));
				pops.add(pop);
			}
		} catch (Exception e) {
			logger.error("获取我发布的弹窗信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return pops;
	}
	
	/**
	 * 获取我发布的弹窗信息数量
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public int getMyPopListCount(int userid,Pop tpop) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		StringBuffer sb=new StringBuffer();
		if(tpop!=null){
			if(tpop.getSendmanner()==0){
				sb.append(" and pop.sendmanner=0");
			}else if(tpop.getSendmanner()==1){
				sb.append(" and pop.sendmanner=1");
			}
			if(tpop.getStatus()==0){
				sb.append(" and pop.status=0");
			}else if(tpop.getStatus()==1){
				sb.append(" and pop.status=1");
			}
		}else{
			tpop=new Pop();
			tpop.setPopTitle("");
		}
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select count(pop.id) from popInfo pop where creater=? and pop.poptitle like ? "+sb.toString());
			ps.setInt(1, userid);
			ps.setString(2, "%"+tpop.getPopTitle()+"%");
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取我发布的弹窗信息数量出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	
	/**
	 * 获取所有弹窗信息
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Pop> getPopList(Pop tpop, int pageNow, int pageSize) throws ElException {
		List<Pop> pops = new ArrayList<Pop>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		StringBuffer sb=new StringBuffer();
		if(tpop!=null){
			if(tpop.getSendmanner()==0){
				sb.append(" and pop.sendmanner=0");
			}else if(tpop.getSendmanner()==1){
				sb.append(" and pop.sendmanner=1");
			}
			if(tpop.getStatus()==0){
				sb.append(" and pop.status=0");
			}else if(tpop.getStatus()==1){
				sb.append(" and pop.status=1");
			}
		}else{
			tpop=new Pop();
			tpop.setPopTitle("");
		}
		try {
			ct = DBConnection.getConnection();
			//ps = ct.prepareStatement("select * from (select t.*, rownum rn from (select pop.id popid,pop.poptitle,pop.createtime,pop.sendmanner,pop.status,eu.id euid,eu.realname from popInfo pop left join eluser eu on pop.creater=eu.id where creater=?) t where rownum <= ?) where rn >= ?  ");
			ps = ct.prepareStatement("select * from (select t.*, rownum rn from (select pop.id popid,pop.poptitle,pop.createtime,pop.sendmanner,pop.status,eu.id euid,eu.realname from popInfo pop left join eluser eu on pop.creater=eu.id where pop.poptitle like ? "+sb.toString()+" ) t where rownum <= ?) where rn >= ?  ");
			ps.setString(1, "%"+tpop.getPopTitle()+"%");
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			Pop pop=null;
			while (rs.next()) {
				pop = new Pop();
				pop.setId(rs.getInt("popid"));
				pop.setPopTitle(rs.getString("poptitle"));
				pop.setCreatetime(rs.getTimestamp("createtime"));
				pop.setSendmanner(rs.getInt("sendmanner"));
				pop.setStatus(rs.getInt("status"));
				pop.setCreate(new ELUser(rs.getInt("euid"),rs.getString("realname")));
				pops.add(pop);
			}
		} catch (Exception e) {
			logger.error("获取所有弹窗信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return pops;
	}
	
	/**
	 * 获取所有弹窗信息数量
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public int getPopListCount(Pop tpop) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		StringBuffer sb=new StringBuffer();
		if(tpop!=null){
			if(tpop.getSendmanner()==0){
				sb.append(" and pop.sendmanner=0");
			}else if(tpop.getSendmanner()==1){
				sb.append(" and pop.sendmanner=1");
			}
			if(tpop.getStatus()==0){
				sb.append(" and pop.status=0");
			}else if(tpop.getStatus()==1){
				sb.append(" and pop.status=1");
			}
		}else{
			tpop=new Pop();
			tpop.setPopTitle("");
		}
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select count(pop.id) from popInfo pop where pop.poptitle like ? "+sb.toString());
			ps.setString(1, "%"+tpop.getPopTitle()+"%");
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取所有弹窗信息数量出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	/**
	 * 根据id删除弹窗信息
	 * @param popid
	 * @throws ElException
	 */
	public void deletePopById(int popid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from popInfo where id=?");
			ps.setInt(1, popid);
			ps.executeUpdate();
			//然后删除弹窗学员
			this.deletePopUser(popid);
		} catch (Exception e) {
			logger.error("根据id删除弹窗信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 更新弹窗信息的状态
	 * @param popid
	 * @param status
	 * @throws ElException
	 */
	public void updatePopStatus(int popid,int status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update popInfo set status=? where id=?");
			ps.setInt(1, status);
			ps.setInt(2, popid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新弹窗信息的状态出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 根据id获取弹窗信息
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public Pop getPopById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Pop pop=null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select pop.id popid,pop.poptitle,pop.popcontent,pop.createtime,pop.sendmanner,pop.sendvalue,pop.status from popInfo pop where id=? ");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				pop = new Pop();
				pop.setId(rs.getInt("popid"));
				pop.setPopTitle(rs.getString("poptitle"));
				pop.setPopContent(new OracleBlob().getContent(rs.getBlob("popcontent")));
				pop.setCreatetime(rs.getTimestamp("createtime"));
				pop.setSendmanner(rs.getInt("sendmanner"));
				pop.setSendvalue(rs.getString("sendvalue"));//还要转值
				pop.setSendvalueName(this.getSendvalueName(pop.getSendmanner(), pop.getSendvalue()));
				pop.setStatus(rs.getInt("status"));
			}
		} catch (Exception e) {
			logger.error("根据id获取弹窗信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return pop;
	}
	/**
	 * 根据popid删除该弹窗所有学员
	 * @param popid
	 * @throws ElException
	 */
	public void deletePopUser(int popid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from pop_user where popid=?");
			ps.setInt(1, popid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("根据popid删除该弹窗所有学员出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public String getSendvalueName(int sendmanner,String sendvalue) throws ElException {
		switch (sendmanner) {
		case 0:
			return this.getUserNameInId(sendvalue);
		case 1:
			return this.getDepNameInId(sendvalue);
		case 2:
			return this.getEroomNameInId(sendvalue);
		case 3:
			return this.getClassNameInId(sendvalue);
		default:
			return "";
		}
	}
	
	/**
	 * 把ids转换成user名称
	 * @param ids
	 * @return
	 * @throws ElException
	 */
	public String getUserNameInId(String ids) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String values = "";
		if(ids==null||"".equals(ids)){
			return "";
		}
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select realname from eluser where id in("+ids+") "); 
			rs = ps.executeQuery(); 
			while (rs.next()){
				if(values.equals("") ){
					values = rs.getString("realname");
				}else{					
					values = values +","+rs.getString("realname"); 
				}
			}
		} catch (Exception e) {
			logger.error("把ids转换成user名称出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return values;
	}
	/**
	 * 把ids转换成部门名称
	 * @param ids
	 * @return
	 * @throws ElException
	 */
	public String getDepNameInId(String ids) throws ElException {
		PreparedStatement ps = null; 
		ResultSet rs = null;
		Connection ct = null;
		String values = "";
		if(ids==null||"".equals(ids)){
			return "";
		}
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select name from department where id in("+ids+") "); 
			rs = ps.executeQuery(); 
			while (rs.next()){
				if(values.equals("") ){
					values = rs.getString("name");
				}else{					
					values = values +","+rs.getString("name"); 
				}
			}
		} catch (Exception e) {
			logger.error("把ids转换成部门名称出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return values;
	}
	
	/**
	 * 把ids转换成培训班名称
	 * @param ids
	 * @return
	 * @throws ElException
	 */
	public String getClassNameInId(String ids) throws ElException {
		PreparedStatement ps = null; 
		ResultSet rs = null;
		Connection ct = null;
		String values = "";
		if(ids==null||"".equals(ids)){
			return "";
		}
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select name from elclass where id in("+ids+") "); 
			rs = ps.executeQuery(); 
			while (rs.next()){
				if(values.equals("") ){
					values = rs.getString("name");
				}else{					
					values = values +","+rs.getString("name"); 
				}
			}
		} catch (Exception e) {
			logger.error("把ids转换成培训班名称出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return values;
	}
	
	/**
	 * 把ids转换成考场名称
	 * @param ids
	 * @return
	 * @throws ElException
	 */
	public String getEroomNameInId(String ids) throws ElException {
		PreparedStatement ps = null; 
		ResultSet rs = null;
		Connection ct = null;
		String values = "";
		if(ids==null||"".equals(ids)){
			return "";
		}
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select title from exam_room where id in("+ids+") "); 
			rs = ps.executeQuery(); 
			while (rs.next()){
				if(values.equals("") ){
					values = rs.getString("title");
				}else{					
					values = values +","+rs.getString("title"); 
				}
			}
		} catch (Exception e) {
			logger.error("把ids转换成考场名称出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return values;
	}
	/**
	 * 更新弹窗信息
	 * @param pop
	 * @throws ElException
	 */
	public void update_pop(Pop pop) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update popInfo set poptitle=?,popcontent=empty_blob() where id=?");
			ps.setString(1, pop.getPopTitle());
			ps.setInt(2, pop.getId());
			ps.executeUpdate();
			OracleBlob setblob = new OracleBlob("popInfo","id",pop.getId()+"","popcontent",pop.getPopContent(),"修改弹窗信息失败",ct);
			setblob.updateContent(); 
		} catch (Exception e) {
			logger.error("更新弹窗信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	/**
	 * 获取某学员的所有弹窗信息
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public String getUserPopList(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String popIds="";
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select pu.popid from pop_user pu left join popInfo pop on pu.popid=pop.id where userid=? and pop.status=1");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				if("".equals(popIds)){
					popIds=rs.getString("popid");
				}else{
					popIds+=","+rs.getString("popid");
				}
			}
		} catch (Exception e) {
			logger.error("获取所有弹窗信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return popIds;
	}
	/**
	 * 发送短信
	 * @param msg
	 * @param elUsers
	 * @throws ElException
	 */
	public String sendMsg(Message msg,List<ELUser> elUsers) throws ElException {
		UserDao userDao=((UserDao)SpringContextUtil.getBean("userDao"));
		Matcher matcher = null;
		Pattern pattern=Pattern.compile("[\\d]{9}");
		StringBuffer movePhones=new StringBuffer("");
		for (int i = 0; i < elUsers.size(); i++) {
			ELUser elUser=userDao.getUserById(elUsers.get(i).getId());
			if(elUser!=null&&elUser.getMovephone()!=null&&!"".equals(elUser.getMovephone().trim())){
				matcher = pattern.matcher(elUser.getMovephone().trim());
				if(matcher.find()){
					//可以发送
					movePhones.append(elUser.getMovephone().trim()+";");
				}
			}
		}
		if(movePhones.indexOf(";")>0){
			movePhones.deleteCharAt(movePhones.length()-1);
		}
		//调用短信帮助类
		if(!movePhones.toString().equals("")){
			return new SendMsgUtil().sendMsg(movePhones.toString(), msg.getMess_content());
		}else{
			return "接收人的手机号码都有误！";
		}
	}
	/**
	 * 发送邮件
	 * @param msg
	 * @param elUsers
	 * @throws ElException
	 * @throws AddressException
	 */
	public String sendEmail(Message msg,List<ELUser> elUsers) throws ElException, AddressException {
		UserDao userDao=((UserDao)SpringContextUtil.getBean("userDao"));
//		UserDao userDao=new UserDaoImpl();
		Matcher matcher = null;
		Pattern pattern=Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		List<InternetAddress> addresss=new ArrayList<InternetAddress>();
		for (int i = 0; i < elUsers.size(); i++) {
			ELUser elUser=userDao.getUserById(elUsers.get(i).getId());
//			ELUser elUser=elUsers.get(i);
			if(elUser!=null&&elUser.getEmail()!=null&&!"".equals(elUser.getEmail().trim())){
				matcher = pattern.matcher(elUser.getEmail().trim());
				if(matcher.find()){
					//检索看看是否重复
					if(!addresss.contains(new InternetAddress(elUser.getEmail().trim()))){
						//可以发送
						addresss.add(new InternetAddress(elUser.getEmail().trim()));
					}
				}
			}
		}
		for (int i = 0; i < addresss.size(); i++) {
			System.out.println("发送号:"+addresss.get(i));
		}
		//调用邮件帮助类
		if(addresss.size()>0){
			//设置邮件的附件名
			this.setMessEmailFilename(msg);
			return new SendMail().massMail(addresss, msg);
		}else{
			return "接收人的邮件地址都有误！";
		}
	}
	/**
	 * 设置邮件的附件名称
	 * @param msg
	 * @throws ElException
	 */
	public void setMessEmailFilename(Message msg) throws ElException {
		if(msg!=null&&msg.getEmailFile()!=null){
			StuffDao stuffDao=(StuffDao)SpringContextUtil.getBean("stuffDao");
			String[] fileName=new String[msg.getEmailFile().length];
			for (int i = 0; i < msg.getEmailFile().length; i++) {
				String tempStr=msg.getEmailFile()[i];
				int id=Integer.parseInt(tempStr.substring(tempStr.lastIndexOf("/")+1,tempStr.lastIndexOf(".")));
				System.out.println(id);
				fileName[i]=stuffDao.getStuffbyId(id, 0).getTitle();
			}
			msg.setEmailFilename(fileName);
		}
	}
	
	/**
	 * 消息增加附件
	 * @param addr
	 * @param Newsid
	 * @param title
	 * @throws ElException
	 */
	public void addKstuff(String addr, int messageid, String title)
	throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
			.prepareStatement("insert into message_stuff(stuffaddr,messageid,title) values(?,?,?)");
			ps.setString(1, addr);
			ps.setInt(2, messageid);
			ps.setString(3, title);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("获取网页中的sortid！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
//	public static void main(String[] args) throws AddressException, ElException {
//		List<ELUser> users=new ArrayList<ELUser>();
//		ELUser user=new ELUser();
//		user.setEmail("630334464@qq.com");
//		users.add(user);
//		ELUser user2=new ELUser();
//		user2.setEmail("630334464@qq.com");
//		users.add(user2);
//		Message msg=new Message();
//		msg.setMess_title("这是标题！");
//		msg.setMess_content("");
//		msg.setSendmanner(1);
//		new MessageDaoImpl().sendEmail(msg, users);
//	}
}
