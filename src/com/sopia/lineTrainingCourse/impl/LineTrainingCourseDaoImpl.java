package com.sopia.lineTrainingCourse.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.CheckHtml;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.ElQuerySql;
import com.sopia.common.OracleBlob;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.DUConstants;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElRole;
import com.sopia.lineTrainingCourse.dao.LineTrainingCourseDao;
import com.sopia.lineTrainingCourse.entities.LineTrainingCourse;
import com.sopia.lineTrainingCourse.entities.LineTrainingCourseAssign;
import com.sopia.lineTrainingCourse.entities.TrainType;
import com.sopia.lineTrainingCourse.entities.TrainTypeTree;
import com.sopia.pfms.entities.PfmsUser;
import com.sopia.pfms.entities.Product;
import com.sopia.pfms.entities.ProductType;
import com.sopia.pfms.entities.Shenhezhuangtai;
import com.sopia.pfms.entities.Suoshulanmu;
import com.sopia.pfms.impl.ProductDaoImpl;
import com.sopia.questionman.entities.StuffLib;

public class LineTrainingCourseDaoImpl implements LineTrainingCourseDao {
	private static final Log logger = LogFactory.getLog(LineTrainingCourseDaoImpl.class);

	public List<LineTrainingCourse> lineTrainingCourseList(String frontOrBack,Department dep, LineTrainingCourse lineTrainingCourse,int userid,int pageNow, int pageSize ,Timestamp starttime,Timestamp endtime) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<LineTrainingCourse> lineTrainingCourses = new ArrayList<LineTrainingCourse>();
		String sql = "";
		String sqlAppend = "";
		String tree = "";
		String jianjie = "";
		try {
			ct = DBConnection.getConnection();
			if(frontOrBack != null && frontOrBack.equals("front")){
				sqlAppend = sqlAppend + " and ltc.is_open = 1 ";
				tree = sqlAppend + "  join ("+
				((ElNodeSQL) SpringContextUtil.getBean("elnodesql")).generateSQLByTree("line_training_type", lineTrainingCourse.getPtype(), true)+
				" ) ltt1 on ltc.train_type_id = ltt1.id " ;
			}else{
				tree = " join ("+
				((ElNodeSQL) SpringContextUtil.getBean("elnodesql")).generateSQLByTree("department", dep, true)+
				" ) dep on depid = dep.id " ;
			}
			if(userid != 0 ){
				sqlAppend = sqlAppend + " and userid=? ";
			}
			if(lineTrainingCourse != null){
				if(lineTrainingCourse.getName() != null && !lineTrainingCourse.getName().equals(""))
					sqlAppend = sqlAppend + " and ltc.name like '%" + lineTrainingCourse.getName() + "%'";
				if(lineTrainingCourse.getTrain_type_id()  > 0)
					sqlAppend = sqlAppend + " and ltc.train_type_id = '" + lineTrainingCourse.getTrain_type_id() + "'";
			}
			
			if(starttime != null)
				sqlAppend  = sqlAppend + " and to_char(createtime,'yyyy-MM-dd HH:mm:ss') > '" + starttime+"'";
			if(endtime != null)
				sqlAppend  = sqlAppend + " and to_char(createtime,'yyyy-MM-dd HH:mm:ss') < '" + endtime+"'";
			
			
			sql = "select * from (select t.*,rownum rn from (select ltc.*,e.depid,ltt.name as lttname from line_training_course ltc " +
					" join line_training_type ltt on ltc.train_type_id=ltt.id " +
					" left join eluser e on ltc.userid=e.id " + tree+
					" where 1=1 " + sqlAppend + 
					" order by ltc.createtime desc ) t where rownum <=?) where rn>=?";
			ps = ct.prepareStatement(sql);
			
			if(userid != 0 ){
				ps.setInt(1, userid);
				ps.setInt(2, pageNow);
				ps.setInt(3, pageSize);
			}else{
				ps.setInt(1, pageNow);
				ps.setInt(2, pageSize);
			}
			
				
			rs = ps.executeQuery();
			while (rs.next()) {
				lineTrainingCourse = new LineTrainingCourse();
				lineTrainingCourse.setId(rs.getInt("id"));
				lineTrainingCourse.setName(rs.getString("name"));
				lineTrainingCourse.setCreatetime(rs.getTimestamp("createtime"));
				lineTrainingCourse.setTrain_begintime(rs.getTimestamp("train_begintime"));
				lineTrainingCourse.setTrain_endtime(rs.getTimestamp("train_endtime"));
				lineTrainingCourse.setContact(rs.getString("contact"));
				lineTrainingCourse.setFee_price(rs.getDouble("fee_price"));
				lineTrainingCourse.setIs_open(rs.getInt("is_open"));
				lineTrainingCourse.setCredit(rs.getDouble("credit"));
				lineTrainingCourse.setHas_signed_number(rs.getInt("has_signed_number"));
				lineTrainingCourse.setPerson_number_plan(rs.getInt("person_number_plan"));
				lineTrainingCourse.setPicture(rs.getString("picture"));
				
				jianjie = new OracleBlob().getContent(rs.getBlob("jianjie"));
				jianjie = CheckHtml.getString(jianjie);
				lineTrainingCourse.setJianjie((jianjie.length() > 126) ? jianjie.substring(0, 126)+ "..." : jianjie);
				TrainType tt = new TrainType();
				tt.setId(rs.getInt("train_type_id"));
				tt.setName(rs.getString("lttname"));
				lineTrainingCourse.setTrainType(tt);
				lineTrainingCourses.add(lineTrainingCourse);
			}
		} catch (Exception e) {
			logger.error("我的培训列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return lineTrainingCourses;
	}

	public int lineTrainingCourseListSize(String frontOrBack,Department dep,
			LineTrainingCourse lineTrainingCourse, int userid,Timestamp starttime,Timestamp endtime)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		String sql = "";
		String sqlAppend = "";
		String tree = "";
		try {
			ct = DBConnection.getConnection();
			if(frontOrBack != null && frontOrBack.equals("front")){
				sqlAppend = sqlAppend + " and ltc.is_open = 1 ";
				tree = sqlAppend + "  join ("+
				((ElNodeSQL) SpringContextUtil.getBean("elnodesql")).generateSQLByTree("line_training_type", lineTrainingCourse.getPtype(), true)+
				" ) ltt1 on ltc.train_type_id = ltt1.id " ;
			}else{
				tree = " join ("+
				((ElNodeSQL) SpringContextUtil.getBean("elnodesql")).generateSQLByTree("department", dep, true)+
				" ) dep on depid = dep.id " ;
			}
			
			if(userid != 0 ){
				sqlAppend = sqlAppend + " and userid=? ";
			}
			
			if(lineTrainingCourse != null){
				if(lineTrainingCourse.getName() != null && !lineTrainingCourse.getName().equals(""))
					sqlAppend = sqlAppend + " and ltc.name like '%" + lineTrainingCourse.getName() + "%'";
				if(lineTrainingCourse.getTrain_type_id() >0)
					sqlAppend = sqlAppend + " and ltc.train_type_id = '" + lineTrainingCourse.getTrain_type_id() + "'";
			}
			
			if(starttime != null)
				sqlAppend  = sqlAppend + " and to_char(createtime,'yyyy-MM-dd HH:mm:ss') > '" + starttime+"'";
			if(endtime != null)
				sqlAppend  = sqlAppend + " and to_char(createtime,'yyyy-MM-dd HH:mm:ss') < '" + endtime+"'";
			
			
			sql = "select count(1) from line_training_course ltc " +
					" join line_training_type ltt on ltc.train_type_id=ltt.id " +
					" left join eluser e on ltc.userid=e.id " +tree+
//					" join ("+
//					((ElNodeSQL) SpringContextUtil.getBean("elnodesql")).generateSQLByTree("department", dep, true)+
//					" ) dep on depid = dep.id " +
					" where 1=1 " + sqlAppend +
					" order by ltc.createtime desc ";
			ps = ct.prepareStatement(sql);
			if(userid != 0 ){
				ps.setInt(1, userid);
			}
			
			rs = ps.executeQuery();
			if(rs.next())
				size = rs.getInt(1);
		} catch (Exception e) {
			logger.error("我的培训列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	public List<TrainType> getTrainTypes() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		List<TrainType> trainTypes = new ArrayList<TrainType>();
		try {
			ct = DBConnection.getConnection();
			sql = "select * from line_training_type";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				TrainType trainType = new TrainType();
				trainType.setId(rs.getInt("id"));
				trainType.setName(rs.getString("name"));
				trainTypes.add(trainType);
			}
		} catch (Exception e) {
			logger.error("培训类别查询失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return trainTypes;
	}

	public void addLineTrainingCourse(boolean line_training_course_add_need_sh,int userid,
			LineTrainingCourse lineTrainingCourse) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int returnId = 0;
		try {
			ct = DBConnection.getConnection();
			sql = "insert into line_training_course " +
					" (id,name,person_number_plan,createtime,place,train_begintime," +
					"  train_endtime,sign_begintime,sign_endtime,key,sign_table_name," +
					"  fee_price,train_type_id,contact,contact_name,picture," +
					"  jianjie,is_open,credit,stuff_id,userid) values " +
					"  (line_training_course_sequence.nextval,?,?,?,?,?," +
					"   ?,?,?,?,?," +
					"   ?,?,?,?,?," +
					"   empty_blob(),?,?,?,?)";
			ps = ct.prepareStatement(sql);
			
			ps.setString(1,lineTrainingCourse.getName());
			ps.setInt(2,lineTrainingCourse.getPerson_number_plan());
			ps.setTimestamp(3,lineTrainingCourse.getCreatetime());
			ps.setString(4,lineTrainingCourse.getPlace());
			ps.setTimestamp(5,lineTrainingCourse.getTrain_begintime());
			ps.setTimestamp(6,lineTrainingCourse.getTrain_endtime());
			ps.setTimestamp(7,lineTrainingCourse.getSign_begintime());
			ps.setTimestamp(8,lineTrainingCourse.getSign_endtime());
			ps.setString(9,lineTrainingCourse.getKey());
			ps.setString(10,lineTrainingCourse.getSign_table_name());
			ps.setDouble(11,lineTrainingCourse.getFee_price());
			ps.setInt(12,lineTrainingCourse.getTrain_type_id());
			ps.setString(13,lineTrainingCourse.getContact());
			ps.setString(14,lineTrainingCourse.getContact_name());
			ps.setString(15,lineTrainingCourse.getPicture());
			
			if(line_training_course_add_need_sh){//添加培训需要审核
				ps.setInt(16,0);//开通状态为0
			}else{
				ps.setInt(16,1);//开通状态为1
			}
			
			ps.setDouble(17, lineTrainingCourse.getCredit());
			ps.setInt(18,lineTrainingCourse.getStuff_id());
			ps.setInt(19,userid);
			
			ps.executeUpdate();
			
			OracleBlob setblob = new OracleBlob(ct,"line_training_course_sequence","line_training_course","id","jianjie",lineTrainingCourse.getJianjie(),"添加培训失败");
			setblob.addContent();
			
//			sql = "select line_training_course_sequence.nextval from dual";
//			ps = ct.prepareStatement(sql);
//			rs = ps.executeQuery();
//			if(rs.next())
//				returnId = rs.getInt(1);
		} catch (Exception e) {
			logger.error("添加培训失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public LineTrainingCourse getLineTrainingCourseById(String type,int id)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String jianjie = "";
		try {
			ct = DBConnection.getConnection();
			
			sql = "select ltc.*,ltt.name as lttname,e.realname,qs.parentid from line_training_course ltc " +
					" join line_training_type ltt on ltc.train_type_id=ltt.id " +
					" join eluser e on e.id=ltc.userid " + 
					" left join question_stuff qs on ltc.stuff_id=qs.id "+
					" where ltc.id=? " ;
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
				
			rs = ps.executeQuery();
			while (rs.next()) {
				LineTrainingCourse  lineTrainingCourse = new LineTrainingCourse();
				lineTrainingCourse = new LineTrainingCourse();
				lineTrainingCourse.setId(rs.getInt("id"));
				lineTrainingCourse.setName(rs.getString("name"));
				lineTrainingCourse.setPerson_number_plan(rs.getInt("person_number_plan"));
				lineTrainingCourse.setCreatetime(rs.getTimestamp("createtime"));
				lineTrainingCourse.setPlace(rs.getString("place"));
				lineTrainingCourse.setPicture(rs.getString("picture"));
				lineTrainingCourse.setTrain_begintime(rs.getTimestamp("train_begintime"));
				lineTrainingCourse.setTrain_endtime(rs.getTimestamp("train_endtime"));
				lineTrainingCourse.setSign_begintime(rs.getTimestamp("sign_begintime"));
				lineTrainingCourse.setSign_endtime(rs.getTimestamp("sign_endtime"));
				lineTrainingCourse.setKey(rs.getString("key"));
				lineTrainingCourse.setSign_table_name(rs.getString("sign_table_name"));
				lineTrainingCourse.setTrain_type_id(rs.getInt("train_type_id"));
				lineTrainingCourse.setContact(rs.getString("contact"));
				lineTrainingCourse.setContact_name(rs.getString("contact_name"));
				lineTrainingCourse.setFee_price(rs.getDouble("fee_price"));
				lineTrainingCourse.setIs_open(rs.getInt("is_open"));
				lineTrainingCourse.setCredit(rs.getDouble("credit"));
				lineTrainingCourse.setUserId(rs.getInt("userid"));
				lineTrainingCourse.setHas_signed_number(rs.getShort("has_signed_number"));
				
				lineTrainingCourse.setIsPastDue(rs.getInt("ispassdue"));
				lineTrainingCourse.setStuff_id(rs.getInt("stuff_id"));
				
//				if(type != null && type.equals("front")){
//					jianjie = new OracleBlob().getContent(rs.getBlob("jianjie"));
//					jianjie = CheckHtml.getString(jianjie);
//					lineTrainingCourse.setJianjie((jianjie.length() > 200) ? jianjie.substring(0, 198)+ "......" : jianjie);
//				}else{
					jianjie = new OracleBlob().getContent(rs.getBlob("jianjie"));
					jianjie = CheckHtml.getString(jianjie);
					lineTrainingCourse.setJianjie(jianjie);
//				}
				
				TrainType tt = new TrainType();
				tt.setId(rs.getInt("train_type_id"));
				tt.setName(rs.getString("lttname"));
				
				TrainTypeTree ptype = new TrainTypeTree();
				ptype.setName(rs.getString("lttname"));
				
				ELUser elUser = new ELUser();
				elUser.setRealname(rs.getString("realname"));
				
				StuffLib stuff = new StuffLib();
				stuff.setParent(new StuffLib(rs.getInt("parentid"),""));
				lineTrainingCourse.setElUser(elUser);
				lineTrainingCourse.setTrainType(tt);
				lineTrainingCourse.setPtype(ptype);
				lineTrainingCourse.setStuff(stuff);
				return lineTrainingCourse;
			}
		} catch (Exception e) {
			logger.error("根据id查找培训失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return null;
	}

	public void updateLineTrainingCourseById(LineTrainingCourse lineTrainingCourse) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlAppend = "";
		try {
			ct = DBConnection.getConnection();
			if(lineTrainingCourse.getStuff_id() != 0){
				sqlAppend = sqlAppend + " ,stuff_id=? ";
			}
			
			sql = "update line_training_course set " +
					" name=?,person_number_plan=?,createtime=?,place=?,train_begintime=?," +
					" train_endtime=?,sign_begintime=?,sign_endtime=?,key=?,sign_table_name=?," +
					" train_type_id=?,contact=?,contact_name=?,fee_price=?,is_open=?,credit=?,picture=?" + sqlAppend+
					" where id=?";
			ps = ct.prepareStatement(sql);
			ps.setString(1, lineTrainingCourse.getName());
			ps.setInt(2, lineTrainingCourse.getPerson_number_plan());
			ps.setTimestamp(3, lineTrainingCourse.getCreatetime());
			ps.setString(4, lineTrainingCourse.getPlace());
			ps.setTimestamp(5, lineTrainingCourse.getTrain_begintime());
			ps.setTimestamp(6, lineTrainingCourse.getTrain_endtime());
			ps.setTimestamp(7, lineTrainingCourse.getSign_begintime());
			ps.setTimestamp(8, lineTrainingCourse.getSign_endtime());
			ps.setString(9, lineTrainingCourse.getKey());
			ps.setString(10, lineTrainingCourse.getSign_table_name());
			ps.setInt(11, lineTrainingCourse.getTrain_type_id());
			ps.setString(12, lineTrainingCourse.getContact());
			ps.setString(13, lineTrainingCourse.getContact_name());
			ps.setDouble(14, lineTrainingCourse.getFee_price());
			ps.setInt(15, lineTrainingCourse.getIs_open());
			ps.setDouble(16, lineTrainingCourse.getCredit());
			ps.setString(17, lineTrainingCourse.getPicture());
			
			if(lineTrainingCourse.getStuff_id() != 0){
				ps.setInt(18, lineTrainingCourse.getStuff_id());
				ps.setInt(19, lineTrainingCourse.getId());
			}else{
				ps.setInt(18, lineTrainingCourse.getId());
			}
			
			
			ps.executeUpdate();
			
			ps = ct.prepareStatement("update line_training_course SET jianjie = empty_blob() WHERE id = ?"); 
			ps.setInt(1, lineTrainingCourse.getId());
			ps.executeUpdate();
			OracleBlob setblob = new OracleBlob("line_training_course","id",lineTrainingCourse.getId()+"","jianjie",lineTrainingCourse.getJianjie(),"修改计划失败",ct);
			setblob.updateContent();
		} catch (Exception e) {
			logger.error("根据id修改培训失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public boolean checkIs_open(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select is_open from line_training_course where id = ?";
			
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				if(rs.getInt("is_open") == 1){//已开通
					return true;
				}
			}
			
		} catch (Exception e) {
			logger.error("删除前查看开通状态失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public void deleteLineTrainingCourse(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(
					"delete from line_training_course where id=?"
					);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除培训失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void openLineTrainingCourse(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			
			sql = "update line_training_course set is_open = 1 where id=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("开通培训失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public void NotOpenLineTrainingCourse(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			
			sql = "update line_training_course set is_open = 0 where id=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("取消开通培训失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public String signByPerson(String option,LineTrainingCourse lineTrainingCourse, int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String message = "";
		try {
			ct = DBConnection.getConnection();
			
			//报名时自动+1
			int has_signed_number = 0;
			sql = "select has_signed_number from line_training_course where id=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, lineTrainingCourse.getId());
			rs = ps.executeQuery();
			while(rs.next())
				has_signed_number = rs.getInt(1);
			
			int size = 0;
			sql = "select person_number_plan from line_training_course where id=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, lineTrainingCourse.getId());
			rs = ps.executeQuery();
			while(rs.next())
				size = rs.getInt(1);
			
			if(has_signed_number < size){
				sql = "update line_training_course set has_signed_number=? where id=?";
				ps = ct.prepareStatement(sql);
				ps.setInt(1, has_signed_number + 1);
				ps.setInt(2, lineTrainingCourse.getId());
				ps.executeUpdate();
				
				sql = "insert into line_training_course_assign " +
				" (id,line_training_course_id,is_get_certificate,score,pay_status," +
				"  approval_status,allocation_type,userid) values " +
				" (line_course_assign_sequence.nextval,?,?,?,?,?,?,?)";
				ps = ct.prepareStatement(sql);
				ps.setInt(1, lineTrainingCourse.getId());
				ps.setInt(2, 0);
				ps.setInt(3, 0);
				ps.setInt(4, 0);
				ps.setInt(5, 0);
				if(option != null ){
					ps.setInt(6, 0);//管理员后台添加
				}else{
					ps.setInt(6, 1);//用户前台申请
				}
				ps.setInt(7, userid);
				ps.executeUpdate();
			}else{
				message = "人数已满,不能在继续分配!";
			}
		} catch (Exception e) {
			logger.error("分配人员失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return message;
		
	}
	

	public List<LineTrainingCourseAssign> getAssignList(Department dep,
			LineTrainingCourse lineTrainingCourse,LineTrainingCourseAssign assign, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<LineTrainingCourseAssign> assignList = new ArrayList<LineTrainingCourseAssign>();
		String sql = "";
		String sqlAppend = "";
		try {
			ct = DBConnection.getConnection();
			if(assign != null){
				if(assign.getElUser() != null){
					if(assign.getElUser().getUsername() != null && !assign.getElUser().getUsername().equals(""))
						sqlAppend = sqlAppend + " and e.username like '%" + assign.getElUser().getUsername() + "%'";
					if(assign.getElUser().getRealname() != null && !assign.getElUser().getRealname().equals(""))
						sqlAppend = sqlAppend + " and e.realname like '%" + assign.getElUser().getRealname() + "%'";
				}
				if(assign.getPay_status() != -1){
					sqlAppend = sqlAppend + " and ltca.pay_status = '" + assign.getPay_status() + "'";
				}
				
			}
			
			sql = "select * from (select t.*,rownum rn from (select ltca.*,e.realname,e.username,d.name as dname,e.sex,elr.name as elrname " +
					" from line_training_course_assign ltca " +
					" join eluser e on ltca.userid=e.id " +
					" join department d on e.depid=d.id "+
					" join elrole elr on e.role=elr.id "+
					" join ("+
					((ElNodeSQL) SpringContextUtil.getBean("elnodesql")).generateSQLByTree("department", dep, true)+
					" ) dep on depid = dep.id " +
					" where 1=1 and ltca.line_training_course_id=? " + sqlAppend + 
					" ) t where rownum <=?) where rn>=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, lineTrainingCourse.getId());
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				assign = new LineTrainingCourseAssign();
				ELUser elUser = new ELUser();
				elUser.setRealname(rs.getString("realname"));
				elUser.setUsername(rs.getString("username"));
				elUser.setSex(rs.getString("sex"));
				Department department = new Department();
				department.setName(rs.getString("dname"));
				ElRole role = new ElRole();
				role.setName(rs.getString("elrname"));
				assign.setId(rs.getInt("id"));
				assign.setLine_training_course_id(rs.getInt("line_training_course_id"));
				assign.setIs_get_certificate(rs.getInt("is_get_certificate"));
				assign.setScore(rs.getDouble("score"));
				assign.setPay_status(rs.getInt("pay_status"));
				assign.setAccessory(rs.getString("accessory"));
				assign.setAllocation_type(rs.getInt("allocation_type"));
				assign.setApproval_status(rs.getInt("approval_status"));
				assign.setUserId(rs.getInt("userid"));
				elUser.setDepartment(department);
				elUser.setRole(role);
				assign.setElUser(elUser);
				assignList.add(assign);
			}
		} catch (Exception e) {
			logger.error("我的分配列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return assignList;
	}

	public int getAssignListSize(Department dep,LineTrainingCourse lineTrainingCourse,LineTrainingCourseAssign assign)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		String sql = "";
		String sqlAppend = "";
		try {
			ct = DBConnection.getConnection();
			
			if(assign != null){
				if(assign.getElUser() != null){
					if(assign.getElUser().getUsername() != null && !assign.getElUser().getUsername().equals(""))
						sqlAppend = sqlAppend + " and e.username like '%" + assign.getElUser().getUsername() + "%'";
					if(assign.getElUser().getRealname() != null && !assign.getElUser().getRealname().equals(""))
						sqlAppend = sqlAppend + " and e.realname like '%" + assign.getElUser().getRealname() + "%'";
				}
				if(assign.getPay_status() != -1){
					sqlAppend = sqlAppend + " and ltca.pay_status = '" + assign.getPay_status() + "'";
				}
				
			}
			
			sql = "select count(1) from line_training_course_assign ltca " +
			" join eluser e on ltca.userid=e.id " +
			" join department d on e.depid=d.id "+
			" join elrole elr on e.role=elr.id "+
			" join ("+
			((ElNodeSQL) SpringContextUtil.getBean("elnodesql")).generateSQLByTree("department", dep, true)+
			" ) dep on depid = dep.id " +
			" where 1=1 and ltca.line_training_course_id=? " + sqlAppend;
			ps = ct.prepareStatement(sql);
			ps.setInt(1, lineTrainingCourse.getId());
			rs = ps.executeQuery();
			if(rs.next())
				size = rs.getInt(1);
		} catch (Exception e) {
			logger.error("我的分配列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	public void option_in_shenhePage(LineTrainingCourseAssign assign,String fieldName,int status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "update line_training_course_assign set " + fieldName + " = ? where id = ?" ;
			
			ps = ct.prepareStatement(sql);
			ps.setInt(1, status);
			ps.setInt(2, assign.getId());
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("审核页面中操作失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public int getAssignListSize_result_entry(Department dep,
			LineTrainingCourse lineTrainingCourse,
			LineTrainingCourseAssign assign) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		String sql = "";
		String sqlAppend = "";
		try {
			ct = DBConnection.getConnection();
			
			if(assign != null){
				if(assign.getElUser() != null){
					if(assign.getElUser().getRealname() != null && !assign.getElUser().getRealname().equals(""))
						sqlAppend = sqlAppend + " and e.realname like '%" + assign.getElUser().getRealname() + "%'";
				}
				if(assign.getIs_get_certificate() != -1){
					sqlAppend = sqlAppend + " and ltca.is_get_certificate = '" + assign.getIs_get_certificate() + "'";
				}
				
			}
			
			sql = "select count(1) " +
					" from line_training_course_assign ltca " +
					" join line_training_course ltc on ltca.line_training_course_id=ltc.id "+
					" join eluser e on ltca.userid=e.id " +
					" join ("+
					((ElNodeSQL) SpringContextUtil.getBean("elnodesql")).generateSQLByTree("department", dep, true)+
					" ) dep on depid = dep.id " +
					" where 1=1 and ltca.approval_status=1 and ltca.line_training_course_id=? " + sqlAppend;
			ps = ct.prepareStatement(sql);
			ps.setInt(1, lineTrainingCourse.getId());
			rs = ps.executeQuery();
			if(rs.next())
				size = rs.getInt(1);
		} catch (Exception e) {
			logger.error("我的我的结果录入列表失败列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	public List<LineTrainingCourseAssign> getAssignList_result_entry(
			Department dep, LineTrainingCourse lineTrainingCourse,
			LineTrainingCourseAssign assign, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<LineTrainingCourseAssign> assignList = new ArrayList<LineTrainingCourseAssign>();
		String sql = "";
		String sqlAppend = "";
		try {
			ct = DBConnection.getConnection();
			if(assign != null){
				if(assign.getElUser() != null){
					if(assign.getElUser().getRealname() != null && !assign.getElUser().getRealname().equals(""))
						sqlAppend = sqlAppend + " and e.realname like '%" + assign.getElUser().getRealname() + "%'";
				}
				if(assign.getIs_get_certificate() != -1){
					sqlAppend = sqlAppend + " and ltca.is_get_certificate = '" + assign.getIs_get_certificate() + "'";
				}
				
			}
			
			sql = "select * from (select t.*,rownum rn from (select ltca.*,e.realname,ltc.credit " +
					" from line_training_course_assign ltca " +
					" join line_training_course ltc on ltca.line_training_course_id=ltc.id "+
					" join eluser e on ltca.userid=e.id " +
					" join ("+
					((ElNodeSQL) SpringContextUtil.getBean("elnodesql")).generateSQLByTree("department", dep, true)+
					" ) dep on depid = dep.id " +
					" where 1=1 and ltca.approval_status = 1 and ltca.line_training_course_id=? " + sqlAppend + 
					" ) t where rownum <=?) where rn>=?";
			
			ps = ct.prepareStatement(sql);
			ps.setInt(1, lineTrainingCourse.getId());
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				assign = new LineTrainingCourseAssign();
				lineTrainingCourse = new LineTrainingCourse();
				lineTrainingCourse.setCredit(rs.getDouble("credit"));
				ELUser elUser = new ELUser();
				elUser.setRealname(rs.getString("realname"));
				assign.setAccessory(rs.getString("accessory"));
				assign.setId(rs.getInt("id"));
				assign.setIs_get_certificate(rs.getInt("is_get_certificate"));
				assign.setLine_training_course_id(rs.getInt("line_training_course_id"));
				assign.setScore(rs.getDouble("score"));
				assign.setUserId(rs.getInt("userid"));
				assign.setLineTrainingCourse(lineTrainingCourse);
				assign.setElUser(elUser);
				assignList.add(assign);
			}
		} catch (Exception e) {
			logger.error("我的结果录入列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return assignList;
	}

	public void changeScore(int id, double score) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "update line_training_course_assign set score=? where id=?";
			
			ps = ct.prepareStatement(sql);
			ps.setDouble(1, score);
			ps.setInt(2, id);
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("修改分数失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public void changeCredit(int id, double credit) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "update line_training_course set credit=? where id=?";
			
			ps = ct.prepareStatement(sql);
			ps.setDouble(1, credit);
			ps.setInt(2, id);
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("修改学分失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void change_is_get_certificate(int change_is_get_certificate, int id)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "update line_training_course_assign set is_get_certificate=? where id=?";
			
			ps = ct.prepareStatement(sql);
			ps.setInt(1, change_is_get_certificate);
			ps.setInt(2, id);
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("修改是否获证失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public List<LineTrainingCourse> personal_lineTrainingCourseList(
			LineTrainingCourse lineTrainingCourse, int userid, int pageNow,
			int pageSize, Timestamp starttime, Timestamp endtime)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<LineTrainingCourse> lineTrainingCourses = new ArrayList<LineTrainingCourse>();
		String sql = "";
		String sqlAppend = "";
		String jianjie = "";
		try {
			ct = DBConnection.getConnection();
			if(lineTrainingCourse != null){
				if(lineTrainingCourse.getName() != null && !lineTrainingCourse.getName().equals(""))
					sqlAppend = sqlAppend + " and ltc.name like '%" + lineTrainingCourse.getName() + "%'";
				if(lineTrainingCourse.getTrain_type_id() != -1)
					sqlAppend = sqlAppend + " and ltc.train_type_id = '" + lineTrainingCourse.getTrain_type_id() + "'";
			}
			
			if(starttime != null)
				sqlAppend  = sqlAppend + " and to_char(createtime,'yyyy-MM-dd HH:mm:ss') > '" + starttime+"'";
			if(endtime != null)
				sqlAppend  = sqlAppend + " and to_char(createtime,'yyyy-MM-dd HH:mm:ss') < '" + endtime+"'";
			
			
			sql = "select * from (select t.*,rownum rn from (select ltc.*,ltt.name as lttname,ltca.is_get_certificate,ltca.approval_status " +
					" from line_training_course ltc " +
					" join line_training_type ltt on ltc.train_type_id=ltt.id " +
//					" join eluser e on ltc.userid=e.id " +
					" join line_training_course_assign ltca on ltc.id=ltca.line_training_course_id "+
					" where ltca.userid = ? " + sqlAppend + 
					" order by ltc.createtime desc ) t where rownum <=?) where rn>=?";
			ps = ct.prepareStatement(sql);
			
			ps.setInt(1, userid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			
				
			rs = ps.executeQuery();
			while (rs.next()) {
				lineTrainingCourse = new LineTrainingCourse();
				lineTrainingCourse.setId(rs.getInt("id"));
				lineTrainingCourse.setName(rs.getString("name"));
				lineTrainingCourse.setCreatetime(rs.getTimestamp("createtime"));
				lineTrainingCourse.setTrain_begintime(rs.getTimestamp("train_begintime"));
				lineTrainingCourse.setTrain_endtime(rs.getTimestamp("train_endtime"));
				lineTrainingCourse.setContact(rs.getString("contact"));
				lineTrainingCourse.setContact_name(rs.getString("contact_name"));
				lineTrainingCourse.setPlace(rs.getString("place"));
				lineTrainingCourse.setFee_price(rs.getDouble("fee_price"));
				lineTrainingCourse.setCredit(rs.getDouble("credit"));
				TrainType tt = new TrainType();
				tt.setId(rs.getInt("train_type_id"));
				tt.setName(rs.getString("lttname"));
				lineTrainingCourse.setTrainType(tt);
				
				LineTrainingCourseAssign assign = new LineTrainingCourseAssign();
				assign.setApproval_status(rs.getInt("approval_status"));
				assign.setIs_get_certificate(rs.getInt("is_get_certificate"));
				lineTrainingCourse.setAssign(assign);
				
				lineTrainingCourses.add(lineTrainingCourse);
			}
		} catch (Exception e) {
			logger.error("我的培训列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return lineTrainingCourses;
	}

	public int personal_lineTrainingCourseListSize(
			LineTrainingCourse lineTrainingCourse, int userid,
			Timestamp starttime, Timestamp endtime) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		String sql = "";
		String sqlAppend = "";
		try {
			ct = DBConnection.getConnection();
			if(lineTrainingCourse != null){
				if(lineTrainingCourse.getName() != null && !lineTrainingCourse.getName().equals(""))
					sqlAppend = sqlAppend + " and ltc.name like '%" + lineTrainingCourse.getName() + "%'";
				if(lineTrainingCourse.getTrain_type_id() != 0)
					sqlAppend = sqlAppend + " and ltc.train_type_id = '" + lineTrainingCourse.getTrain_type_id() + "'";
			}
			
			if(starttime != null)
				sqlAppend  = sqlAppend + " and to_char(createtime,'yyyy-MM-dd HH:mm:ss') > '" + starttime+"'";
			if(endtime != null)
				sqlAppend  = sqlAppend + " and to_char(createtime,'yyyy-MM-dd HH:mm:ss') < '" + endtime+"'";
			
			
			sql = "select count(1) " +
					" from line_training_course ltc " +
					" join line_training_type ltt on ltc.train_type_id=ltt.id " +
//					" join eluser e on ltc.userid=e.id " +
					" join line_training_course_assign ltca on ltc.id=ltca.line_training_course_id "+
					" where ltca.userid = ? " + sqlAppend + 
					" order by ltc.createtime desc";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			
			rs = ps.executeQuery();
			if(rs.next())
				size = rs.getInt(1);
		} catch (Exception e) {
			logger.error("我的培训列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	public LineTrainingCourse getLineTrainingCourseById_personal(int userid,int id)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			
			sql = "select ltc.*,ltt.name as lttname,e.realname,ltca.score,ltca.is_get_certificate,ltca.id as ltcaid,ltca.line_training_course_id,ltca.userid as ltcauserid,ltca.accessory " +
					" from line_training_course ltc " +
					" join line_training_type ltt on ltc.train_type_id=ltt.id " +
					" join line_training_course_assign ltca on ltca.line_training_course_id=ltc.id "+
					" join eluser e on e.id=ltca.userid " +
					" where ltc.id=? and ltca.userid=? " ;
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setInt(2, userid);
				
			rs = ps.executeQuery();
			while (rs.next()) {
				LineTrainingCourse  lineTrainingCourse = new LineTrainingCourse();
				
				ELUser elUser = new ELUser();
				elUser.setRealname(rs.getString("realname"));
				LineTrainingCourseAssign assign = new LineTrainingCourseAssign();
				assign.setIs_get_certificate(rs.getInt("is_get_certificate"));
				assign.setScore(rs.getDouble("score"));
				assign.setUserId(rs.getInt("ltcauserid"));
				assign.setAccessory(rs.getString("accessory"));
				assign.setId(rs.getInt("ltcaid"));
				assign.setLine_training_course_id(rs.getInt("line_training_course_id"));
				assign.setElUser(elUser);
				
				lineTrainingCourse.setCredit(rs.getDouble("credit"));
				lineTrainingCourse.setAssign(assign);
				return lineTrainingCourse;
			}
		} catch (Exception e) {
			logger.error("根据id查找培训失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return null;
	}

	public void updateLineTrainingCourseAssignById(
			LineTrainingCourseAssign assign) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "update line_training_course_assign set accessory=? where id=?";
			
			ps = ct.prepareStatement(sql);
			ps.setString(1, assign.getAccessory());
			ps.setInt(2, assign.getId());
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("修改相关附件！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public List<Integer> check_is_signed(int assign_id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Integer> userIdList = new ArrayList<Integer>();
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select userid from line_training_course_assign where line_training_course_id=?";
			
			ps = ct.prepareStatement(sql);
			ps.setInt(1, assign_id);
			rs = ps.executeQuery();
			while(rs.next()){
				userIdList.add(rs.getInt(1));
			}
			
		} catch (Exception e) {
			logger.error("查找分配人员失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return userIdList;
	}

	public List<LineTrainingCourseAssign> getCredit_get(int line_training_course_id)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<LineTrainingCourseAssign> assignList = new ArrayList<LineTrainingCourseAssign>();
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			
			sql = "select ltc.credit,ltca.is_get_certificate " +
					" from line_training_course_assign ltca " +
					" join line_training_course ltc on ltca.line_training_course_id=ltc.id " +
					" where  ltca.line_training_course_id=? and ltca.approval_status=1 ";
			
			ps = ct.prepareStatement(sql);
			ps.setInt(1, line_training_course_id);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				LineTrainingCourseAssign assign = new LineTrainingCourseAssign();
				LineTrainingCourse lineTrainingCourse = new LineTrainingCourse();
				lineTrainingCourse.setCredit(rs.getDouble("credit"));
				assign.setIs_get_certificate(rs.getInt("is_get_certificate"));
				assign.setLineTrainingCourse(lineTrainingCourse);
				assignList.add(assign);
			}
		} catch (Exception e) {
			logger.error("我的结果录入列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return assignList;
	}

	public List<ELUser> getDistributionStudents(LineTrainingCourse lineTrainingCourse,Department depTree, int depid,
			ELUser eu, int role, int pageNow, int pageSize) throws ElException {
		List<ELUser> eus = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;

		String LidRid = " and ";
		String depids = "";
		int x = 1;
		try {
			String username = "";
			String realname = "";
			String sex = "";
			String con = "";
			if (null != eu) {
				if (null != eu.getUsername()) {
					username = eu.getUsername().trim();
				}
				if (null != eu.getRealname()) {
					realname = eu.getRealname().trim();
				}
				if (null != eu.getSex()) {
					sex = eu.getSex().trim();
				}
//				if (null != eu.getJingzhong() && !eu.getJingzhong().equals("0")) {
//					con = con + " and eu.jingzhong = '"
//							+ eu.getJingzhong().trim() + "' ";
//				}
//				if (null != eu.getDishi() && !eu.getDishi().equals("0")) {
//					con = con + " and eu.dishi = '" + eu.getDishi().trim()
//							+ "' ";
//				}
//				if (null != eu.getZhiji() && !eu.getZhiji().equals("0")) {
//					con = con + " and eu.zhiji = '" + eu.getZhiji().trim()
//							+ "' ";
//				}
//				if (null != eu.getZhiwu() && !eu.getZhiwu().equals("0")) {
//					con = con + " and eu.zhiwu = '" + eu.getZhiwu().trim()
//							+ "' ";
//				}
				if (eu.getJingzhong()>0) {
					con=con + " and eu.jingzhong = "
							+ eu.getJingzhong();
				}
				if (eu.getDishi()>0) {
					con=con + " and eu.dishi = "
							+ eu.getDishi();
				}
				if (eu.getZhiji()>0) {
					con=con + " and eu.zhiji = "
							+ eu.getZhiji();
				}
				if (eu.getZhiwu()>0) {
					con=con + " and eu.zhiwu = "
							+ eu.getZhiwu();
				}
				if (null != eu.getGangwei() && !eu.getGangwei().equals("0")) {
					con = con + " and eu.gangwei = '" + eu.getGangwei().trim()
							+ "' ";
				}
				if (eu.getShengri() != null)
					con = con
							+ " and eu.shengri >=to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(eu.getShengri())
							+ "','yyyy-MM-dd HH24:mi:ss') ";
				if (eu.getShengri_end() != null)
					con = con
							+ " and eu.shengri <= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(eu.getShengri_end())
							+ "','yyyy-MM-dd HH24:mi:ss')  ";
			}

			ct = DBConnection.getConnection();
			Department dep = new Department();
			if (depTree.getId() == -2) {
				for (int i = 0; i < depTree.getChild().size(); i++) {
					if (depids.equals("")) {
						depids = depids + depTree.getChild().get(i).getId();
					} else {
						depids = depids + ","
								+ depTree.getChild().get(i).getId();
					}
				}
				ps = ct
						.prepareStatement("select lid,rid from DEPARTMENT where id in ("
								+ depids + ")");
				rs = ps.executeQuery();
				while (rs.next()) {
					if (depTree.getChild().size() == 1) {
						LidRid = LidRid + "  dep.lid >= " + rs.getInt(1)
								+ " and  dep.rid <= " + rs.getInt(2);
					} else {
						if (depTree.getChild().size() > 1
								&& depTree.getChild().size() != x && x > 1) {// 中间不用加
							LidRid = LidRid + " or (dep.lid >= " + rs.getInt(1)
									+ " and  dep.rid <= " + rs.getInt(2) + ")";
						} else if (depTree.getChild().size() == x) {// 结束前面加 ）
							LidRid = LidRid + "  or (dep.lid >= "
									+ rs.getInt(1) + " and  dep.rid <= "
									+ rs.getInt(2) + "))";
						} else {// 开始前面加 （
							LidRid = LidRid + "  ( (dep.lid >= " + rs.getInt(1)
									+ " and  dep.rid <= " + rs.getInt(2) + ")";
						}
					}
					x++;
				}
			} else {
				// 获取 部门的左右值
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
				ps.setInt(1, depTree.getId());
				rs = ps.executeQuery();
				if (rs.next()) {
					dep.setId(rs.getInt(1));
					dep.setLid(rs.getInt(2));
					dep.setRid(rs.getInt(3));
					LidRid = LidRid + " dep.lid>=" + rs.getInt(2)
							+ " and dep.rid<= " + rs.getInt(3);
				}
			}
			ps.close();
			rs.close();

			String lower = "";
			if (depTree.isLower()) {// 包含下级
				if (depTree.getId() == -2) {
					lower = LidRid;
				} else {
					lower = " and dep.lid >= " + dep.getLid()
							+ " and dep.rid<= " + dep.getRid();
				}
			} else {
				if (depTree.getId() == -2) {
					lower = LidRid;
				} else {
					lower = " and dep.id = " + depTree.getId();
				}
			}
			String sql = "";
			sql = "select * from (select t1.*,rownum rn from( select eu.id euid,eu.username, eu.realname,eu.role,dep.id depid,dep.name depname,eu.valid,er.name ername,eu.sex,eu.jingzhong,eu.shengri,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_  from ELUSER eu left join  DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id = eu.role " 
//					+ " left join line_training_course_assign ltca on eu.id=ltca.userid "
					+ "where eu.username like ? and eu.realname like ? and eu.sex like ? "
					+ con + lower + " )t1 where rownum <=? ) where rn >=?";
			ps = ct.prepareStatement(sql);
			ps.setString(1, "%" + username + "%");
			ps.setString(2, "%" + realname + "%");
			ps.setString(3, "%" + sex + "%");
			ps.setInt(4, pageNow);
			ps.setInt(5, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser elUser = new ELUser();
				elUser.setId(rs.getInt(1));
				elUser.setUsername(rs.getString(2));
				elUser.setRealname(rs.getString(3));
				elUser.setRole(new ElRole(rs.getInt(4), rs.getString(8)));
				elUser.setDepartment(new Department(rs.getInt(5), rs
						.getString(6)));
				elUser.setValid(rs.getBoolean(7));
				elUser.setSex(rs.getString(9));
				elUser.setJingzhong(rs.getInt(10));
				elUser.setShengri(rs.getDate(11));
				elUser.setAge(rs.getInt(12));
//				if(rs.getInt("line_training_course_id") == lineTrainingCourse.getId()){
//					elUser.setIntroom(true);
//				}
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

	public int getDistributionStudentsCount(LineTrainingCourse lineTrainingCourse,Department depTree, int depid,
			ELUser eu, int role) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int b = 0;
		String LidRid = " and ";
		String depids = "";
		int x = 1;
		try {
			String username = "";
			String realname = "";
			String sex = "";
			String con = "";
			if (null != eu) {
				if (null != eu.getUsername()) {
					username = eu.getUsername().trim();
				}
				if (null != eu.getRealname()) {
					realname = eu.getRealname().trim();
				}
				if (null != eu.getSex()) {
					sex = eu.getSex().trim();
				}
//				if (null != eu.getJingzhong() && !eu.getJingzhong().equals("0")) {
//					con = con + " and eu.jingzhong = '"
//							+ eu.getJingzhong().trim() + "' ";
//				}
//				if (null != eu.getDishi() && !eu.getDishi().equals("0")) {
//					con = con + " and eu.dishi = '" + eu.getDishi().trim()
//							+ "' ";
//				}
//				if (null != eu.getZhiji() && !eu.getZhiji().equals("0")) {
//					con = con + " and eu.zhiji = '" + eu.getZhiji().trim()
//							+ "' ";
//				}
//				if (null != eu.getZhiwu() && !eu.getZhiwu().equals("0")) {
//					con = con + " and eu.zhiwu = '" + eu.getZhiwu().trim()
//							+ "' ";
//				}
				if (eu.getJingzhong()>0) {
					con=con + " and eu.jingzhong = "
							+ eu.getJingzhong();
				}
				if (eu.getDishi()>0) {
					con=con + " and eu.dishi = "
							+ eu.getDishi();
				}
				if (eu.getZhiji()>0) {
					con=con + " and eu.zhiji = "
							+ eu.getZhiji();
				}
				if (eu.getZhiwu()>0) {
					con=con + " and eu.zhiwu = "
							+ eu.getZhiwu();
				}
				if (null != eu.getGangwei() && !eu.getGangwei().equals("0")) {
					con = con + " and eu.gangwei = '" + eu.getGangwei().trim()
							+ "' ";
				}
				if (eu.getShengri() != null)
					con = con
							+ " and eu.shengri >=to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(eu.getShengri())
							+ "','yyyy-MM-dd HH24:mi:ss') ";
				if (eu.getShengri_end() != null)
					con = con
							+ " and eu.shengri <= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(eu.getShengri_end())
							+ "','yyyy-MM-dd HH24:mi:ss')  ";
			}

			ct = DBConnection.getConnection();
			Department dep = new Department();
			if (depTree.getId() == -2) {
				for (int i = 0; i < depTree.getChild().size(); i++) {
					if (depids.equals("")) {
						depids = depids + depTree.getChild().get(i).getId();
					} else {
						depids = depids + ","
								+ depTree.getChild().get(i).getId();
					}
				}
				ps = ct
						.prepareStatement("select lid,rid from DEPARTMENT where id in ("
								+ depids + ")");
				rs = ps.executeQuery();
				while (rs.next()) {
					if (depTree.getChild().size() == 1) {
						LidRid = LidRid + "  dep.lid >= " + rs.getInt(1)
								+ " and  dep.rid <= " + rs.getInt(2);
					} else {
						if (depTree.getChild().size() > 1
								&& depTree.getChild().size() != x && x > 1) {// 中间不用加
							LidRid = LidRid + " or (dep.lid >= " + rs.getInt(1)
									+ " and  dep.rid <= " + rs.getInt(2) + ")";
						} else if (depTree.getChild().size() == x) {// 结束前面加 ）
							LidRid = LidRid + "  or (dep.lid >= "
									+ rs.getInt(1) + " and  dep.rid <= "
									+ rs.getInt(2) + "))";
						} else {// 开始前面加 （
							LidRid = LidRid + "  ( (dep.lid >= " + rs.getInt(1)
									+ " and  dep.rid <= " + rs.getInt(2) + ")";
						}
					}
					x++;
				}
			} else {
				// 获取 部门的左右值
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
				ps.setInt(1, depTree.getId());
				rs = ps.executeQuery();
				if (rs.next()) {
					dep.setId(rs.getInt(1));
					dep.setLid(rs.getInt(2));
					dep.setRid(rs.getInt(3));
					LidRid = LidRid + "  dep.lid>=" + rs.getInt(2)
							+ " and dep.rid<= " + rs.getInt(3);
				}
			}
			ps.close();
			rs.close();

			String lower = "";
			if (depTree.isLower()) {// 包含下级
				if (depTree.getId() == -2) {
					lower = LidRid;
				} else {
					lower = " and dep.lid >= " + dep.getLid()
							+ " and dep.rid<= " + dep.getRid();
				}
			} else {
				if (depTree.getId() == -2) {
					lower = LidRid;
				} else {
					lower = " and dep.id = " + depTree.getId();
				}
			}
			String sql = "";
			sql = "select count(*) from ELUSER eu left join  DEPARTMENT dep on eu.depid = dep.id left join elrole er on er.id = eu.role "
//					+ " left join line_training_course_assign ltca on eu.id=ltca.userid " 
					+ "where eu.username like ? and eu.realname like ? and eu.sex like ?  "
					+ con + lower + "";
			ps = ct.prepareStatement(sql);
			ps.setString(1, "%" + username + "%");
			ps.setString(2, "%" + realname + "%");
			ps.setString(3, "%" + sex + "%");
			rs = ps.executeQuery();
			if (rs.next()) {
				b = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("用户列表搜索失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return b;
	}

	public String removePerson(String option, LineTrainingCourse lineTrainingCourse,
			int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String message  = "";
		try {
			ct = DBConnection.getConnection();
			sql = "delete from  line_training_course_assign  where line_training_course_id=? and userid=? ";
			
			ps = ct.prepareStatement(sql);
			ps.setInt(1, lineTrainingCourse.getId());
			ps.setInt(2, userid);
			ps.executeUpdate();
			
			
			//移除时自动-1
			int has_signed_number = 0;
			sql = "select has_signed_number from line_training_course where id=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, lineTrainingCourse.getId());
			rs = ps.executeQuery();
			while(rs.next())
				has_signed_number = rs.getInt(1);
			
			if(has_signed_number >0){
				sql = "update line_training_course set has_signed_number=? where id=?";
				ps = ct.prepareStatement(sql);
				ps.setInt(1, has_signed_number - 1);
				ps.setInt(2, lineTrainingCourse.getId());
				ps.executeUpdate();
			}else{
				message = "该培训班没有分配的学员,不能移除!";
			}
			
			
			
		} catch (Exception e) {
			logger.error("移除人员失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return message;
	}

	public int check_joinWay(int courseid, int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select allocation_type from line_training_course_assign where line_training_course_id=? and userid=? ");
			ps.setInt(1, courseid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取学员的分配方式出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 2;
	}

	public boolean check_signed(int courseid, int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from line_training_course_assign where line_training_course_id=? and userid=? ");
			ps.setInt(1, courseid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			logger.error("检测该学员是否被分配！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}
	

}
