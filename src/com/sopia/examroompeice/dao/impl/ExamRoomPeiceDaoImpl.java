package com.sopia.examroompeice.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.sopia.classman.entities.ELClassRegistration;
import com.sopia.classman.entities.ElClType;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.StringUtil;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.dao.impl.EroomDaoImpl;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseType;
import com.sopia.courseman.entities.EroomLib;
import com.sopia.courseman.entities.EroomRegistration;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.entities.ELUser;
import com.sopia.elclasspeice.entities.ElClassPeice;
import com.sopia.examroompeice.dao.ExamRoomPeiceDao;
import com.sopia.examroompeice.entities.ExamRoomPeice;
import com.sopia.peice.dao.impl.PeiceDaoImpl;

public class ExamRoomPeiceDaoImpl implements ExamRoomPeiceDao {
	private static final Log logger = LogFactory.getLog(ExamRoomPeiceDaoImpl.class);
	
	public int getSessionValue(String key) {
		HttpServletRequest requset = ServletActionContext.getRequest();
		HttpSession session = requset.getSession();
		return  Integer.parseInt(session.getAttribute(key).toString());
	}



	public void examRoomPeice_Submit(int examroomid) throws ElException {
		PreparedStatement ps=null;
		ResultSet rs=null;
		Connection ct=null;
		int flag=0;
		try {//查询价格表里有没有该培训班的价格信息
			ct = DBConnection.getConnection();
			String sql="select count(1) from exam_room_price where examroomid=?";
			ps=ct.prepareStatement(sql);
			ps.setInt(1, examroomid);
			rs=ps.executeQuery();
			 if(rs.next()) {
				flag=rs.getInt(1);
			}
			 rs.close();
			 ps.close();
			 
			 try {
				 if(flag==0){
						ct = DBConnection.getConnection();
						sql="insert into exam_room_price(examroomid,status) values(?,3)";
						ps=ct.prepareStatement(sql);
						ps.setInt(1, examroomid);
						ps.executeUpdate();
				 }else{
					 	ct = DBConnection.getConnection();
						sql="update exam_room_price set status=3 where examroomid=?";
						ps=ct.prepareStatement(sql);
						ps.setInt(1, examroomid);
						ps.executeUpdate();
				 }
				
			} catch (Exception e) {
					logger.error("查询价格失败！", e);
					throw new ElException(e);
			} finally {
					DBConnection.closeConnectInfo(ct, ps, rs);
			}
		} catch (Exception e) {
			logger.error("申请价格审核失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public void examRoomPeice_audit(int examroomid, int userid, int setstatus)
			throws ElException {
		PreparedStatement ps=null;
		ResultSet rs=null;
		Connection ct=null;
		int flag=0;
		try {//查询价格表里有没有该课程的价格信息
			ct = DBConnection.getConnection();
			String sql="select count(1) from exam_room_price where examroomid=?";
			ps=ct.prepareStatement(sql);
			ps.setInt(1, examroomid);
			rs=ps.executeQuery();
			 if(rs.next()) {
				flag=rs.getInt(1);
			}
			 rs.close();
			 ps.close();
			 
			 try {
				 if(flag==0){
						ct = DBConnection.getConnection();
						sql="insert into exam_room_price(examroomid,status,userid) values(?,?,?)";
						ps=ct.prepareStatement(sql);
						ps.setInt(1, examroomid);
						ps.setInt(2, setstatus);
						ps.setInt(3, userid);
						ps.executeUpdate();
				 }else{
					 	ct = DBConnection.getConnection();
						sql="update exam_room_price set status=?,userid=? where examroomid=?";
						ps=ct.prepareStatement(sql);
						ps.setInt(1, setstatus);
						ps.setInt(2, userid);
						ps.setInt(3, examroomid);
						ps.executeUpdate();
				 }
				
			} catch (Exception e) {
					logger.error("查询价格失败！", e);
					throw new ElException(e);
			} finally {
					DBConnection.closeConnectInfo(ct, ps, rs);
			}
		} catch (Exception e) {
			logger.error("价格审核失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public void examRoomPeice_change(float peicevale, int elclassid,
			int peicetype, int userid) throws ElException {
		PreparedStatement ps=null;
		ResultSet rs=null;
		Connection ct=null;
		int flag=0;
		String peic = "";
		try {//查询价格表里有没有该课程的价格信息
			ct = DBConnection.getConnection();
			String sql="select count(1) from exam_room_price where examroomid=?";
			ps=ct.prepareStatement(sql);
			ps.setInt(1, elclassid);
			rs=ps.executeQuery();
			 if(rs.next()) {
				flag=rs.getInt(1);
			}
			 rs.close();
			 ps.close();
			try{
				if(flag==0){//如果没有，则执行增加记录语句
					if(peicetype==1){ 
						peic = "examroomnowPrice"; 
					}else{ 
						peic = "examroomoldPrice";
					}
					sql="insert into exam_room_price(examroomid,"+peic+",status,userid) values(?,?,?,?)"; 
					ps=ct.prepareStatement(sql); 
					ps.setInt(1, elclassid);
					ps.setFloat(2, peicevale); 
					ps.setInt(3,4);
					ps.setInt(4, userid);
					ps.executeUpdate();
					
				}else{//否则执行修改价格信息
					if(peicetype==1){ 
						peic = "examroomnowPrice"; 
					}else{ 
						peic = "examroomoldPrice";
					}
					sql="select status from exam_room_price where examroomid=?";

					ps=ct.prepareStatement(sql);
					ps.setInt(1, elclassid);
					rs=ps.executeQuery();
					rs.next();
					if(rs.getInt(1)==1){
						sql="update exam_room_price set "+peic+"=? where examroomid=?";
					}
					else{
						sql="update exam_room_price set "+peic+"=?,status=4 where examroomid=?";
					}
					
				
					ps=ct.prepareStatement(sql);
					ps.setFloat(1, peicevale);
					ps.setInt(2, elclassid);
					ps.executeUpdate();
				}
				}catch(Exception e){
					logger.error("修改价格失败！", e);
					throw new ElException(e);
				}finally {
					DBConnection.closeConnectInfo(ct, ps, rs);
				}
	
		} catch (Exception e) {
			logger.error("查询价格失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	
		
	}

	public List<ExamRoomPeice> getExamRoomList(ElNode eroomLibTree,
			ExamRoom examRoom, int sublibs, String status, String sqlW,
			int pageNow, int pageSize, String name, String userid, int dprice,
			int role) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoomPeice> ers = new ArrayList<ExamRoomPeice>();
		String sql = "";
		try {
			if (name == null)
				name = "";
			else
				name = name.trim(); 
			//课程定价条件
			if(dprice == 1){//未定价课程
				sqlW = sqlW + " and  elcp.status is null ";
			}
			if(dprice == 2){//审核中
				sqlW = sqlW + " and  elcp.status = 3";
			}
			if(dprice == 3){//审核通过
				sqlW = sqlW + " and  elcp.status = 1";
			}
			if(dprice == 4){//已定价
				sqlW = sqlW + " and  elcp.status = 4";
			}
		
			ct = DBConnection.getConnection();
			boolean consub = sublibs == 1 ? true : false;
			List<Object> params = new ArrayList<Object>();
			
			StringBuffer basesql = new StringBuffer(
					"select * from (select t.* ,rownum rn from (select er.id, er.createrid, er.title, er.begintime, er.endtime,er.location,er.passgrade," +
					"er.erlibid ,erlib.name erbname,er.type,er.valid,c.name cname,er.courseid,er.avalid,er.uvalid,er.classid," +
					"er.svalid,er.isApplication,eu.realname,er.jingzhong,er.depname,erp.examroomnowprice,erp.examroomoldprice,erp.status erpstatus,er.pwdtime   " +
					"from exam_room er inner join ("
							+ ((ElNodeSQL) SpringContextUtil
									.getBean("elnodesql")).generateSQLByTree(
									"eroom_lib", eroomLibTree, consub)
							+ ") erlib on erlib.id=er.erlibid left join course c on c.id = er.courseid " +
							"left join eluser eu on er.createrid=eu.id left join study_room sr on sr.roomid=er.id "
							+ "  left join exam_room_price erp on erp.examroomid=er.id "
							+ " left join eroom_registration erg on er.id=erg.eroomid where 1=1  "
							+ sqlW);
			this.checkParams(basesql, params, examRoom);
//			basesql.append(" group by er.id ,er.createrid,er.title,er.begintime,er.endtime,er.location,er.passgrade," +
//							"er.erlibid,erlib.name,er.type,er.valid,c.name,er.courseid,er.avalid,er.uvalid,er.classid," +
//							"er.svalid,er.isApplication,eu.realname,er.jingzhong,er.depname ,erp.examroomnowprice,erp.examroomoldprice,erp.status,er.pwdtime  order by er.begintime desc) t where rownum<=?) where rn>=?");
//			basesql.append(" and er.createrid= ? ) t where rownum<=?) where rn>=?");
			basesql.append("  ) t where rownum<=?) where rn>=?");
			ps = ct.prepareStatement(basesql.toString());
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 1, params.get(i));
			}
//			ps.setString(params.size() + 1, userid);
//			ps.setInt(params.size() + 2, pageNow);
//			ps.setInt(params.size() + 3, pageSize);
			ps.setInt(params.size() + 1, pageNow);
			ps.setInt(params.size() + 2, pageSize);
			rs = ps.executeQuery();
			ELUser user = null;
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(3));
				user = new ELUser(rs.getInt("createrid"), rs
						.getString("realname"));
				er.setCreater(user);
				// er.setSupervisorrealname(getSupervisorrealname(rs.getInt(1)));
				// er.setBegintime(rs.getTimestamp(3));
				// er.setEndtime(rs.getTimestamp(4));
				// er.setLocation(rs.getString(5));
				// er.setPassgrade(rs.getFloat(6));
				// er.setEroomLib(new EroomLib(rs.getInt(7), rs.getString(8)));
				// er.setType(rs.getInt(9));
				// er.setValid(rs.getInt(10));
				// er.setEpsize(rs.getInt(11));
				// er.setCourse(new Course(rs.getInt(13), rs.getString(12)));
				er.setBegintime(rs.getTimestamp(4));
				er.setEndtime(rs.getTimestamp(5));
				er.setLocation(rs.getString(6));
				er.setPassgrade(rs.getFloat(7));
				er.setEroomLib(new EroomLib(rs.getInt(8), rs.getString(9)));
				er.setType(rs.getInt(10));
				er.setValid(rs.getInt(11));
				// er.setEpsize(rs.getInt(12));
				er.setEpsize(1);
				er.setAvalid(rs.getInt(14));
				er.setUvalid(rs.getInt(15));
				er.setClassid(rs.getInt(16));
				er.setCourse(new Course(rs.getInt(14), rs.getString(12)));
				// er.setUsersize(this.getExamAllStudy(er.getId()));
//				er.setUsersize(rs.getInt(19));
				er.setSvalid(rs.getInt(17));
				er.setIsApplication(rs.getInt(18));
				er.setPlanNumber(getEroomPlanNumber(rs.getInt(1)));
				er.setJingzhong(rs.getString(20));
				er.setDepName(rs.getString(21));
				er.setPwdtime(rs.getTimestamp(25));
				ExamRoomPeice erp = new ExamRoomPeice();
				erp.setExamroomnowPrice(rs.getFloat(22));
				erp.setExamroomoldPrice(rs.getFloat(23));
				erp.setStatus(rs.getInt(24));
				erp.setExamRoom(er);
				ers.add(erp);
				// er.getCreater().getUsername();
			}
		} catch (Exception e) {
			logger.error("获取考场集合信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}

	public List<ExamRoomPeice> getMyAll(EroomLib eroomLib, int type,
			String name, String status, String userid, int dprice, int role,
			int pageNow, int pageSize) throws ElException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getMyAllSize(ElNode eroomLibTree,ExamRoom examRoom,int sublibs, String status,String sqlw, int pageNow, 
			int pageSize, String name, String userid, int dprice, int role)
			throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null; 
		String sql = "";
		int size = 0 ;
		List<Object> params = new ArrayList<Object>();
		try {
			
			boolean consub = sublibs == 1 ? true : false;
			ct = DBConnection.getConnection();
			sql="select count(*)"
			     +"from exam_room er inner join ("
					+ ((ElNodeSQL) SpringContextUtil
							.getBean("elnodesql")).generateSQLByTree(
							"eroom_lib", eroomLibTree, consub)
					+ ") erlib on erlib.id=er.erlibid left join course c on c.id = er.courseid " +
					"left join eluser eu on er.createrid=eu.id left join study_room sr on sr.roomid=er.id "
					+ "  left join exam_room_price erp on erp.examroomid=er.id "
					+ " left join eroom_registration erg on er.id=erg.eroomid where 1=1  "
//					+ sqlw+" and er.createrid= ? and er.valid not in (9) and er.classid = -1" ;
					+ sqlw+"  and er.valid not in (9) and er.classid = -1" ;
			ps = ct.prepareStatement(sql);
//			ps.setString(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				size = rs.getInt(1);
			} 
		} catch (Exception e) {
			logger.error("获取列表大小失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		return size;
	}
	
	
	
	/**
	 * 检测参数
	 */
	private void checkParams(StringBuffer sql, List<Object> params,
			ExamRoom examRoom) {
		if (examRoom != null) {
			if (examRoom.getTitle() != null && !"".equals(examRoom.getTitle())) {
				// sql.append(" and er.title like '%"+examRoom.getTitle()+"%'");
				sql.append(" and er.title like ?");
				params.add("%" + StringUtil.toLikeStr(examRoom.getTitle())
						+ "%");
			}
			if (examRoom.getCreater() != null
					&& !"".equals(examRoom.getCreater().getRealname())) {
				sql.append(" and eu.realname like ?");
				params.add("%"
						+ StringUtil.toLikeStr(examRoom.getCreater()
								.getRealname()) + "%");
			}
			if (examRoom.getValid() != -1) {
				// sql.append(" and er.valid="+examRoom.getValid());
				sql.append(" and er.valid=?");
				params.add(examRoom.getValid());
			}
			if (examRoom.getSvalid() != -1) {
				// sql.append(" and er.svalid="+examRoom.getSvalid());
				sql.append(" and er.svalid=?");
				params.add(examRoom.getSvalid());
			}
			if (examRoom.getBegintime() != null) {
				// sql.append(" and er.begintime >= to_date('"+ new
				// SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				// .format(examRoom.getBegintime())+ "','yyyy-MM-dd
				// HH24:mi:ss')");
				sql.append(" and er.begintime >=?");
				params.add(examRoom.getBegintime());
			}
			if (examRoom.getEndtime() != null) {
				// sql.append(" and er.endtime <= to_date('"+ new
				// SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				// .format(examRoom.getEndtime())+ "','yyyy-MM-dd
				// HH24:mi:ss')");
				sql.append(" and er.endtime <=?");
				params.add(examRoom.getEndtime());
			}
			if (examRoom.getClassid() == -1) {
				sql.append(" and er.classid=-1");
			} else if (examRoom.getClassid() == 0) {
				sql.append(" and er.classid=0");
			} else if (examRoom.getClassid() == 1) {
				sql.append(" and er.classid>0");
			}
		} else {
			sql.append(" and er.classid=-1");
		}
	}
	
	
	
	/**
	 * 获取考场计划招收人数
	 * 
	 * @param erid
	 * @return
	 * @throws ElException
	 */
	public int getEroomPlanNumber(int erid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int number = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select planrecruitstudents from eroom_registration where eroomid = ?");
			ps.setInt(1, erid);
			rs = ps.executeQuery();
			if (rs.next())
				number = rs.getInt(1);
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return number;
	}

	
	
//-----------------------------shopping----------------------------------------------------
	public List<EroomLib> getexamroomerjijiedian() throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<EroomLib>  list = new ArrayList<EroomLib>();
		try {
			String  sql =" select erl.id,erl.name  from eroom_lib erl  where  erl.parentid=1 and erl.status not in (1)  ";
			ct = DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				EroomLib  cut = new  EroomLib();
				cut.setId(rs.getInt(1));
				cut.setName(rs.getString(2));
				list.add(cut);
			}
			return list;
		} catch (Exception e) {
			logger.error("加载二级目录失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		
	}  
	
	/**
	 * 获取全部已开通的考场信息（去掉已删除的）
	 * 
	 * @return
	 * @throws ElException
	 */

	public List<ExamRoom> getApplyForexamRoom(EroomLib tree, int cltid,
			ExamRoom examRoom, int role, String sqlw, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> classlist = new ArrayList<ExamRoom>();
		try {
			String x = Integer.toString(cltid);
			String ids = eroomLibById(tree, cltid);
			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
				// ,当角色不为1时ids的只有一个根节点时也不截取
				ids = cltid == 1 ? ids.substring(x.length() + 1, ids.length())
						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
			if (cltid == 1) {
				ids = "1," + ids;
			}
			
			String ClassConditions = "";
			if (examRoom != null) {
				if (examRoom.getTitle() != null && !examRoom.getTitle().equals("")) {
					ClassConditions = ClassConditions + " and elc.title like '%"
							+ examRoom.getTitle() + "%'";
				}
			}
			ct = DBConnection.getConnection();
			String sql ="select * from (select t.*, rownum rn from ( "
						+ "select elc.id,elc.title,elc.description,elc.pwdtime,elc.begintime,elc.endtime, "
						+ " clt.id cltid "
						+ "from exam_room elc,eroom_lib clt ,exam_room_price erp "
						+ "where elc.erlibid = clt.id  and elc.valid in (5)  and erp.examroomid=elc.id(+)  and erp.status = 1 and clt.id in("+ids+") "
					    + ClassConditions + sqlw +
					// "and elr.registrationStartTime < sysdate and
					// elr.registrationStopTime > sysdate" +
					")t where rownum <= ? ) where rn>=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamRoom elc = new ExamRoom();
				elc.setId(rs.getInt(1));
				elc.setTitle(rs.getString(2));
				elc.setDescription(rs.getString(3));
				elc.setPwdtime(rs.getTimestamp(4));
				elc.setBegintime(rs.getTimestamp(5));
				elc.setEndtime(rs.getTimestamp(6));
//				elc.setMainimg(rs.getString(8));
				elc.setEroomLib(new EroomLib(rs.getInt("cltid")));
//				elc.setCltype(new ElClType(rs.getInt("cltid")));
				ELClassRegistration elR = new ELClassRegistration();
				elc.setElRegistration(elR);
				classlist.add(elc);
			}
		} catch (Exception e) {
			logger.error("可申请培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return classlist;
	}
	
	
	/**
	 * 查询出从ctid开始的有权的课程类型ID
	 * 
	 * @author heiweicheng
	 * @param ctypeTree
	 * @param ctid
	 * @return
	 */
	private String eroomLibById(EroomLib  eroomLib, int ctid) {
		if (eroomLib != null) {
			if (eroomLib.getId() != ctid) {
				eroomLib = EroomLibById(eroomLib.getChild(), ctid);
			}
			if (eroomLib.getChild() != null) {
				return createEroomLibId(eroomLib.getChild(), eroomLib.getId());
			}
			return String.valueOf(eroomLib.getId());
		} else {
			return null;
		}
	}
	
	/**
	 * 如果不是跟节点开始 要找出开始节点
	 * 
	 * @author heiweicheng
	 * @param listType
	 * @param ctid
	 * @return
	 */
	private EroomLib EroomLibById(List<EroomLib> listType, int ctid) {
		EroomLib eroomLibType = null;
		for (EroomLib type : listType) {
			if (type.getId() != ctid) {
				eroomLibType = EroomLibById(type.getChild(), ctid);
				if (eroomLibType != null) {
					return eroomLibType;
				}
			} else {
				eroomLibType = type;
				return eroomLibType;
			}
		}
		return eroomLibType;
	}
	
	/**
	 * 构建有权的课程类型ID
	 * 
	 * @author heiweicheng
	 * @param ctypeTree
	 * @return
	 */
	private String createEroomLibId(List<EroomLib> listType, int id) {
		String ids = id + "";
		for (EroomLib type : listType) {
			ids = ids + "," + createEroomLibId(type.getChild(), type.getId());
		}
		return ids;
	}

	
	
	public ExamRoomPeice getApplyForeExamRoomPeiceById(int examroomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ExamRoomPeice erp = new ExamRoomPeice();
		ExamRoom elc = new ExamRoom();
		try {
			ct = DBConnection.getConnection();
			String sql = "select er.id,er.courseid,er.createrid,er.title,er.description,er.location,er.begintime," +
					"er.endtime,er.iscommon,er.passgrade,er.score,er.pwdtime,er.valid,erp.examroomnowprice," +
					"erp.examroomoldprice ,elu.realname,elb.name,er.erlibid," +
					" elr.planRecruitStudents,elr.registrationStartTime,elr.registrationStopTime,elr.startAge,elr.stopAge,elr.sex,"+
			         " elr.jingzhong,elr.dishi,elr.zhiwu,elr.zhiji,elr.gangwei,elr.treeType,elr.examroomIds,elr.elclassIds ,"+
			         " elr.classScreeningWay,elr.eroomScreeningWay,er.mainimg,elr.isAudit,er.depname,er.jingzhong,elr.isselectep,elr.examepids  "+
					" from exam_room er,exam_room_price erp ,eluser elu,eroom_lib elb,Eroom_registration elr " +
					"where er.erlibid=elb.id(+) and er.id(+)=erp.examroomid and er.createrid=elu.id(+) and er.id = elr.eroomid(+) " 
					+"and  er.id=?";
			
//			"select  er.ID,er.TITLE,er.DESCRIPTION,er.BEGINTIME,er.ENDTIME,er.CREATERID,er.ERLIBID,er.iscommon,"
//			+ "eu.realname,elib.name,elr.planRecruitStudents,elr.registrationStartTime,elr.registrationStopTime,elr.startAge,elr.stopAge,elr.sex,"
//			+ "elr.jingzhong,elr.dishi,elr.zhiwu,elr.zhiji,elr.gangwei,elr.treeType,elr.examroomIds,elr.elclassIds "
//			+ ",elr.classScreeningWay,elr.eroomScreeningWay,er.mainimg,elr.isAudit,er.depname,er.jingzhong,elr.isselectep,elr.examepids,er.location "
//			+ "from exam_room er,Eroom_registration elr,eroom_lib el ,eluser eu,eroom_lib elib "
//			+ "where er.id = elr.eroomid and elib.id= er.ERLIBID and er.erlibid = el.id  and er.valid !=9  "
//			+ "and er.ISAPPLICATION =1 and eu.id=createrid and er.id=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, examroomid);
			logger.info(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				EroomRegistration elR = new EroomRegistration();
				EroomLib elb = new EroomLib();
				Course course = new Course();
				course.setId(rs.getInt(2));
				elb.setName(rs.getString(18));
				elc.setId(rs.getInt(1));
				elc.setCourse(course);
				elc.setTitle(rs.getString(4));
				elc.setDescription(rs.getString(5));
				elc.setLocation(rs.getString(6));
				elc.setBegintime(rs.getTimestamp(7));
				elc.setEndtime(rs.getTimestamp(8));
				elc.setPwdtime(rs.getTimestamp(12));
				elc.setValid(rs.getInt(13));
				erp.setExamroomnowPrice(rs.getFloat(14));
				erp.setExamroomoldPrice(rs.getFloat(15));
				erp.setExamRoom(elc);
				ELUser user = new ELUser();
				user.setId(rs.getInt(3));
				user.setRealname(rs.getString(16));
				elb.setName(rs.getString(17));
//				user.setId(rs.getInt(17));
				elb.setId(rs.getInt(18));
				elc.setCreater(user);
				elc.setEroomLib(elb);
				elR.setPlanRecruitStudents(rs.getInt(19));
				elR.setRegistrationStartTime(rs.getTimestamp(20));
				elR.setRegistrationStopTime(rs.getTimestamp(21));
				elR.setStartAge(rs.getInt(22));
				elR.setStopAge(rs.getInt(23));
				elR.setSex(rs.getString(24));
				elR.setJingzhong(rs.getString(25));
				elR.setDishi(rs.getString(26));
				elR.setZhiwu(rs.getString(27));
				elR.setZhiji(rs.getString(28));
				elR.setGangwei(rs.getString(29));
				elR.setTreeType(rs.getString(30));
//				elR.setClassParasstr(rs.getString(24));
				elR.setClassScreeningWay(rs.getInt(33));
				elR.setEroomScreeningWay(rs.getInt(34));
				elc.setErRegistration(elR);
				elc.setMainimg(rs.getString(35));
				elR.setIsAudit(rs.getInt(36));// 是否需要审核
				elc.setDepName(rs.getString(37));
				elc.setJingzhong(rs.getString(38));
				elR.setIsselectep(rs.getInt(39));
//				elR.setErepParasstr(rs.getString(32));
//				elc.setLocation(rs.getString(40));
//				elc.setName(rs.getString(2));
//				elc.setDescription(rs.getString(3));
//				elc.setCertificatename(rs.getString(4));
//				elc.setCreatetime(rs.getTimestamp(5));
//				elc.setStarttime(rs.getTimestamp(6));
//				elc.setFinishtime(rs.getTimestamp(7));
//				
//				ELUser user = new ELUser(rs.getInt(9), rs.getString(8));
//				ElClType elt = new ElClType(rs.getInt(10), rs.getString(11));	
//				ElClassPeice elclasspeice = new ElClassPeice(rs.getInt(13));
//				elclasspeice.setElclassnowPrice(rs.getFloat(14));
//				elc.setMainimg(rs.getString(12));
//				elc.setPrice(elclasspeice);
//				elc.setCreater(user);
//				elc.setCltype(elt);
			}
		} catch (Exception e) {
			logger.error("可申请培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return erp;
	}

	

	/**
	 * 获取全部已开通的培训班列表大小（去掉已删除的）
	 * 
	 * @return
	 * @throws ElException
	 */
	public int getApplyForeElclasssize(EroomLib tree, int cltid,
			ExamRoom examRoom, int role, String sqlw)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String x = Integer.toString(cltid);
			String ids = eroomLibById(tree, cltid);
			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
				// ,当角色不为1时ids的只有一个根节点时也不截取
				ids = cltid == 1 ? ids.substring(x.length() + 1, ids.length())
						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
			if (cltid == 1) {
				ids = "1," + ids;
			}
			// if(elclass!=null){
			// if(
			// elclass.getName()!=null&&!elclass.getName().equals("")){//培训名称
			// sqls+= " and cl.name like '%"+elclass.getName()+"%'";
			// }
			// if(elclass.getStatus()!=-1){//考场状态
			// sqls+=" and cl.status="+elclass.getStatus();
			// }
			// if(elclass.getBegintime()!=null){
			// sqls+=" and cl.STARTTIME >= to_date('"+ new
			// SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
			// .format(elclass.getBegintime())+ "','yyyy-MM-dd HH24:mi:ss')";
			// }
			// if(elclass.getEndtime()!=null){
			// sqls+=" and cl.FINISHTIME <= to_date('"+ new
			// SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
			// .format(elclass.getEndtime())+ "','yyyy-MM-dd HH24:mi:ss')";
			// }
			// }
			String ClassConditions = "";
			if (examRoom != null) {
				if (examRoom.getTitle() != null && !examRoom.getTitle().equals("")) {
					ClassConditions = ClassConditions + " and elc.title like '%"
							+ examRoom.getTitle() + "%'";
				}
			}
			ct = DBConnection.getConnection();
			String sql = "select count(1) from ( "
					+ "select elc.id,elc.title,elc.description,elc.pwdtime,elc.begintime,elc.endtime, "
					+ " elc.mainimg,clt.id cltid "
					+ "from exam_room elc,eroom_lib clt,exam_room_price erp  "
					+ "where  elc.erlibid = clt.id and elc.valid in (5) and erp.examroomid=elc.id(+)  and erp.status in (1) and clt.id in("+ids+") "
					+ ClassConditions + sqlw +
					// "and elr.registrationStartTime < sysdate and
					// elr.registrationStopTime > sysdate" +
					")";
			
			ps = ct.prepareStatement(sql);
			
			rs = ps.executeQuery();
			rs.next();
			return  rs.getInt(1);
		} catch (Exception e) {
			logger.error("可全部培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}



	public boolean checkUserRoom(int roomid, int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean flag = false;
		try {
			ct = DBConnection.getConnection();
			String sql = "select count(1) from study_room where userid=? and roomid=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, roomid);
			rs = ps.executeQuery();
			rs.next();
			if (rs.getInt(1) > 0) {

				flag = true;
			}
			return flag;

		} catch (Exception e) {
			logger.error("判断学生是否购买过这门考场 失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	
	
}
