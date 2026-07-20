package com.sopia.studyman.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.ElQuerySql;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.DUConstants;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.studyman.dao.LineTrainRecordDao;
import com.sopia.studyman.entities.LineTrainRecord;
import com.sopia.studyman.entities.LineTrainRecordStuff;
import com.sopia.studyman.entities.Schoolrolls;

/**
 * 线下培训
 * @author jiahaijiang
 */
public class LineTrainRecordDaoIImpl implements LineTrainRecordDao{
	
	private static final Log logger = LogFactory.getLog(StudyClassDaoImpl.class);
	
	public void deleteRecord(String ids) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from linetrainrecord where trainid in ("+ids+")");
			rs = ps.executeQuery();
		} catch (Exception e) {
			logger.error("删除线下培训失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public LineTrainRecord findRecordByIds(Integer trainid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		LineTrainRecord record=null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select trainid,trainname,submittime,certificate,trainstarttime,trainendtime,trainlength,state,remark,createuserid from linetrainrecord where trainid =?");
			ps.setInt(1, trainid);
			rs = ps.executeQuery();
			rs.next();
			record=new LineTrainRecord();
			record.setTrainid(rs.getInt(1));
			record.setTrainname(rs.getString(2));
			record.setSubmittime(rs.getDate(3));
			record.setCertificate(rs.getString(4));
			record.setTrainstarttime(rs.getDate(5));
			record.setTrainendtime(rs.getDate(6));
			record.setTrainlength(rs.getString(7));
			record.setState(rs.getInt(8));
			record.setRemark(rs.getString(9));
			record.setCreateuserid(rs.getInt(10));
		} catch (Exception e) {
			logger.error("查询线下培训失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return record;
	}

	public List<LineTrainRecord> findRecordList(Integer userid,Integer state) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<LineTrainRecord> recordList=new ArrayList<LineTrainRecord>();
		try {
			ct = DBConnection.getConnection();
			String sql="select l.trainid,l.trainname,l.submittime,l.certificate,l.trainstarttime,l.trainendtime," +
					" l.trainlength,l.state,l.remark,l.createuserid,e.realname from linetrainrecord l left join eluser e on l.createuserid=e.id";
			if(userid!=null){
				sql=sql+" where createuserid="+userid;
				if(state!=null){
					sql=sql+" and state="+state;
				}
			}else if(state!=null){
				sql=sql+" where state="+state;
			}
			
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				LineTrainRecord record=new LineTrainRecord();
				record.setTrainid(rs.getInt(1));
				record.setTrainname(rs.getString(2));
				record.setSubmittime(rs.getDate(3));
				record.setCertificate(rs.getString(4));
				record.setTrainstarttime(rs.getDate(5));
				record.setTrainendtime(rs.getDate(6));
				record.setTrainlength(rs.getString(7));
				record.setState(rs.getInt(8));
				record.setRemark(rs.getString(9));
				record.setCreateuserid(rs.getInt(10));
				record.setCreatename(rs.getString(11));
				recordList.add(record);
			}
		} catch (Exception e) {
			logger.error("查询线下培训失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return recordList;
	}
	
	public int findRecordListCount(Integer userid,Integer state) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql="select count(*) from linetrainrecord l left join eluser e on l.createuserid=e.id";
			if(userid!=null){
				sql=sql+" where createuserid="+userid;
				if(state!=null){
					sql=sql+" and state="+state;
				}
			}else if(state!=null){
				sql=sql+" where state="+state;
			}
			
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			logger.error("查询线下培训失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	
	public LineTrainRecord saveUpdateRecord(LineTrainRecord record) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			//如果不为空 进行修改操作
			if(record.getTrainid()==null){
				ps = ct.prepareStatement("select linetrain_sequence.nextval from dual");
				rs = ps.executeQuery();
				if (rs.next()) {
				    record.setTrainid(rs.getInt(1));
				}
				record.setState(1);
				ps = ct.prepareStatement("insert into linetrainrecord(trainid,trainname,submittime,certificate,trainstarttime,trainendtime,trainlength,state,remark,createuserid) values(?,?,?,?,?,?,?,?,?,?)");
				ps.setInt(1, record.getTrainid());
				ps.setString(2, record.getTrainname());
				ps.setDate(3, new Date(System.currentTimeMillis()));
				ps.setString(4, record.getCertificate());
				ps.setDate(5, record.getTrainstarttime());
				ps.setDate(6, record.getTrainendtime());
				ps.setString(7, record.getTrainlength());
				ps.setInt(8, record.getState());
				ps.setString(9, record.getRemark());
				ps.setInt(10, record.getCreateuserid());
			}else{
				ps = ct.prepareStatement("update linetrainrecord set trainname=?,submittime=?,certificate=?,trainstarttime=?,trainendtime=?,trainlength=?,state=?,remark=?,createuserid=? where trainid=?");
				ps.setString(1, record.getTrainname());
				ps.setDate(2, new Date(System.currentTimeMillis()));
				ps.setString(3, record.getCertificate());
				ps.setDate(4, record.getTrainstarttime());
				ps.setDate(5, record.getTrainendtime());
				ps.setString(6, record.getTrainlength());
				ps.setInt(7, record.getState());
				ps.setString(8, record.getRemark());
				ps.setInt(9, record.getCreateuserid());
				ps.setInt(10, record.getTrainid());
			}
			rs = ps.executeQuery();
		} catch (Exception e) {
			logger.error("跟新状态失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return record;
	}
	/**
	 * 添加或者修改线下培训记录附件
	 * @param recordStuff
	 * @return
	 * @throws ElException
	 */
	public LineTrainRecordStuff saveUpdateRecordStuff(LineTrainRecordStuff recordStuff) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			//如果不为空 进行修改操作
			if(recordStuff.getId()==null){
				ps = ct.prepareStatement("select linetrainrecord_stuff_sequence.nextval from dual");
				rs = ps.executeQuery();
				if (rs.next()) {
					recordStuff.setId(rs.getInt(1));
				}
				ps = ct.prepareStatement("insert into linetrainrecord_stuff(id,trainid,title,stuffaddr) values(?,?,?,?)");
				ps.setInt(1, recordStuff.getId());
				ps.setInt(2, recordStuff.getLineTrainRecord().getTrainid());
				ps.setString(3, recordStuff.getTitle());
				ps.setString(4, recordStuff.getId()+"."+recordStuff.getStuffAddr());
			}else{
				ps = ct.prepareStatement("update linetrainrecord_stuff set title=?,stuffaddr=? where id=?");
				ps.setString(1, recordStuff.getTitle());
				ps.setString(2, recordStuff.getId()+"."+recordStuff.getStuffAddr());
				ps.setInt(3, recordStuff.getId());
			}
			recordStuff.setStuffAddr(recordStuff.getId()+"."+recordStuff.getStuffAddr());
			rs = ps.executeQuery();
		} catch (Exception e) {
			logger.error("跟新状态失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return recordStuff;
	}
	/**
	 * 根据培训记录id获取附件集合
	 * @param trainid
	 * @return
	 * @throws ElException
	 */
	public List<LineTrainRecordStuff> listRecordStuffByTrainid(int trainid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<LineTrainRecordStuff> lineTrainRecordStuffs=new ArrayList<LineTrainRecordStuff>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id,trainid,title,stuffaddr from linetrainrecord_stuff where trainid=?");
			ps.setInt(1, trainid);
			rs = ps.executeQuery();
			LineTrainRecordStuff lineTrainRecordStuff=null;
			while(rs.next()){
				lineTrainRecordStuff=new LineTrainRecordStuff();
				lineTrainRecordStuff.setId(rs.getInt(1));
				lineTrainRecordStuff.setTitle(rs.getString(3));
				lineTrainRecordStuff.setStuffAddr(rs.getString(4));
				lineTrainRecordStuffs.add(lineTrainRecordStuff);
			}
		} catch (Exception e) {
			logger.error("根据培训记录id获取附件集合失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return lineTrainRecordStuffs;
	}
	/**
	 * 删除线下培训记录附件
	 * @param id
	 * @throws ElException
	 */
	public void deleteLineTrainRecordStuffById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from linetrainrecord_stuff where id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
		} catch (Exception e) {
			logger.error("删除线下培训记录附件失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 根据id获取线下培训记录附件
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public LineTrainRecordStuff getLineTrainRecordStuffById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		LineTrainRecordStuff lineTrainRecordStuff=new LineTrainRecordStuff();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id,trainid,title,stuffaddr from linetrainrecord_stuff where id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while(rs.next()){
				lineTrainRecordStuff.setId(rs.getInt(1));
				lineTrainRecordStuff.setLineTrainRecord(new LineTrainRecord(rs.getInt(2)));
				lineTrainRecordStuff.setTitle(rs.getString(3));
				lineTrainRecordStuff.setStuffAddr(rs.getString(4));
			}
		} catch (Exception e) {
			logger.error("根据id获取线下培训记录附件失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return lineTrainRecordStuff;
	}

	public void updateState(String trainids, Integer state) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update linetrainrecord set state = ? where trainid in ("+trainids+")");
			ps.setInt(1, state);
			rs = ps.executeQuery();
		} catch (Exception e) {
			logger.error("跟新状态失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<LineTrainRecord> findManagementRecordList(ElNode tree,
			Integer state,int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<LineTrainRecord> recordList=new ArrayList<LineTrainRecord>();
		try {
			ct = DBConnection.getConnection();
//			ps = ct.prepareStatement(ElQuerySql.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
//			ps.setInt(1, deptid);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				dep.setId(rs.getInt(1));
//				dep.setLid(rs.getInt(2));
//				dep.setRid(rs.getInt(3));
//			}
//			ps.close();
//			rs.close();
			StringBuffer buffer =new StringBuffer();
			buffer.append("select * from (select t1.*,rownum rn from(")
			.append(" select l.trainid,l.trainname,l.submittime,l.certificate,l.trainstarttime,l.trainendtime,")
			.append(" l.trainlength,l.state,l.remark,l.createuserid,e.realname from linetrainrecord l left join eluser e on l.createuserid=e.id")
			.append(" left join department dep on e.depid = dep.id ")
			.append("  where l.state=? and "+ElNodeSQL.getWhereSql(tree, "dep", 1)+" )t1 where rownum <=? ) where rn >=?");
			ps = ct.prepareStatement(buffer.toString());
			ps.setInt(1, state);
			//ps.setInt(2, dep.getLid());
			//ps.setInt(3, dep.getRid());
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while(rs.next()){
				LineTrainRecord record=new LineTrainRecord();
				record.setTrainid(rs.getInt(1));
				record.setTrainname(rs.getString(2));
				record.setSubmittime(rs.getDate(3));
				record.setCertificate(rs.getString(4));
				record.setTrainstarttime(rs.getDate(5));
				record.setTrainendtime(rs.getDate(6));
				record.setTrainlength(rs.getString(7));
				record.setState(rs.getInt(8));
				record.setRemark(rs.getString(9));
				record.setCreateuserid(rs.getInt(10));
				record.setCreatename(rs.getString(11));
				recordList.add(record);
			}
		} catch (Exception e) {
			logger.error("查询审核列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return recordList;
	}

	public int findManagementRecordListSize(ElNode tree,
			Integer state) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
//			ps = ct.prepareStatement(ElQuerySql.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
//			ps.setInt(1, deptid);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				dep.setId(rs.getInt(1));
//				dep.setLid(rs.getInt(2));
//				dep.setRid(rs.getInt(3));
//			}
//			ps.close();
//			rs.close();
			
			StringBuffer buffer =new StringBuffer();
			buffer.append("select count(*) from linetrainrecord l left join eluser e on l.createuserid=e.id")
			.append(" left join department dep on e.depid = dep.id ")
			.append("  where l.state=? and "+ElNodeSQL.getWhereSql(tree, "dep", 1)+" ");
			ps = ct.prepareStatement(buffer.toString());
			ps.setInt(1, state);
			//ps.setInt(2, dep.getLid());
			//ps.setInt(3, dep.getRid());
			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (Exception e) {
			logger.error("查询审核列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	/**
	 * 学籍查询
	 * @param deptid
	 * @return
	 * @throws ElException
	 */
	public List<Schoolrolls> getSchoolrollsList(Integer deptid, int pN,int pS)throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Schoolrolls> list=new ArrayList<Schoolrolls>();
		try {
			Department dep = new Department();
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, deptid);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}
			ps.close();
			rs.close();
			
			StringBuffer buffer =new StringBuffer();
			buffer.append("select * from (select t.*, rownum rn from  (select eu.id,eu.username,eu.realname,dept.name,count(sc.classid),exam.examcount,line.linecount from ELUSER eu left join ( select * from study_class where status=2) sc on eu.id=sc.userid ")
			.append("  left join DEPARTMENT dept on eu.depid=dept.id ")
			.append(" left join (SELECT count(sr.userid) examcount,sr.userid FROM (SELECT *")
			.append(" FROM exam_room WHERE iscommon = 1) er LEFT JOIN study_room sr ON er.ID =sr.roomid")
			.append(" LEFT JOIN (SELECT * FROM study_quizinfo WHERE userid = 1) sqi ON sqi.roomid =sr.roomid")
			.append(" WHERE sr.userid = 1 AND er.valid = 1 and sr.myscore>=60 group by sr.userid ) exam on eu.ID=exam.userid")
			.append(" left join (select count(*) linecount,CREATEUSERID from linetrainrecord where state=2 group by CREATEUSERID) line on eu.ID=line.CREATEUSERID")
			.append("  where  dept.lid >=? and dept.rid<=?   group by eu.id,eu.username,eu.realname,dept.name,exam.examcount,line.linecount )t where rownum <= ? ) where rn>=?");
			ps = ct.prepareStatement(buffer.toString());
			ps.setInt(1, dep.getLid());
			ps.setInt(2, dep.getRid());
			ps.setInt(3, pN);
			ps.setInt(4, pS);
			rs = ps.executeQuery();
			while(rs.next()){
				Schoolrolls schoolrolls = new Schoolrolls();
				schoolrolls.setId(rs.getInt(1));
				schoolrolls.setUsername(rs.getString(2));
				schoolrolls.setRealname(rs.getString(3));
				schoolrolls.setDeptname(rs.getString(4));
				schoolrolls.setCompleteClass(rs.getInt(5));
				schoolrolls.setCompleteExam(rs.getInt(6));
				schoolrolls.setCompleteLineTrain(findRecordListCount(rs.getInt(1),4));
				list.add(schoolrolls);
			}
			
		} catch (Exception e) {
			logger.error("查询审核列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}
	public List<Schoolrolls> getSchoolrollsList(ElNode tree,ELUser eluser,int sublibs, int pN,int pS)throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Schoolrolls> list=new ArrayList<Schoolrolls>();
		try {
			ct = DBConnection.getConnection();
			
			/*StringBuffer buffer =new StringBuffer();
			buffer.append("select * from (select t.*, rownum rn from  (select eu.id,eu.username,eu.realname,dept.name,count(sc.classid),exam.examcount,line.linecount from ELUSER eu left join ( select * from study_class where status=2) sc on eu.id=sc.userid ")
			.append("  left join DEPARTMENT dept on eu.depid=dept.id ")
			.append(" left join (SELECT count(sr.userid) examcount,sr.userid FROM (SELECT *")
			.append(" FROM exam_room WHERE iscommon = 1) er LEFT JOIN study_room sr ON er.ID =sr.roomid")
			.append(" LEFT JOIN (SELECT * FROM study_quizinfo WHERE userid = 1) sqi ON sqi.roomid =sr.roomid")
			.append(" WHERE sr.userid = 1 AND er.valid = 1 and sr.myscore>=60 group by sr.userid ) exam on eu.ID=exam.userid")
			.append(" left join (select count(*) linecount,CREATEUSERID from linetrainrecord where state=2 group by CREATEUSERID) line on eu.ID=line.CREATEUSERID")
			.append("  where     group by eu.id,eu.username,eu.realname,dept.name,exam.examcount,line.linecount )t where rownum <= ? ) where rn>=?");
			ps = ct.prepareStatement(buffer.toString());
			*/
			//String sqlstr="select * from (select t.*, rownum rn from  (select eu.id,eu.username,eu.realname,dept.name,count(sc.classid),exam.examcount,line.linecount from ELUSER eu left join ( select * from study_class where status=2) sc on eu.id=sc.userid   left join DEPARTMENT dept on eu.depid=dept.id  left join (SELECT count(sr.userid) examcount,sr.userid FROM (SELECT * FROM exam_room WHERE iscommon = 1) er LEFT JOIN study_room sr ON er.ID =sr.roomid LEFT JOIN (SELECT * FROM study_quizinfo WHERE userid = 1) sqi ON sqi.roomid =sr.roomid WHERE sr.userid = 1 AND er.valid = 1 and sr.myscore>=60 group by sr.userid ) exam on eu.ID=exam.userid left join (select count(*) linecount,CREATEUSERID from linetrainrecord where state=2 group by CREATEUSERID) line on eu.ID=line.CREATEUSERID  where dept.lid >=? and dept.rid<=? ";
			String sqlstr="select * from (select t.*, rownum rn from  (select eu.id,eu.username,eu.realname,dept.name,count(sc.classid),exam.examcount,line.linecount from ELUSER eu left join ( select * from study_class where certificateno is not null) sc on eu.id=sc.userid left join DEPARTMENT dept on eu.depid=dept.id  left join (SELECT count(sr.userid) examcount,sr.userid FROM (SELECT * FROM exam_room WHERE iscommon = 1) er LEFT JOIN study_room sr ON er.ID =sr.roomid WHERE sr.ispassed=1 group by sr.userid ) exam on eu.ID=exam.userid left join (select count(*) linecount,CREATEUSERID from linetrainrecord where state=2 group by CREATEUSERID) line on eu.ID=line.CREATEUSERID  where "+ElNodeSQL.getWhereSql(tree, "dept",sublibs)+" ";
			if(eluser==null)
				sqlstr+="";
			else{
				sqlstr+=eluser.getSex()==null?"":eluser.getSex().equals("")?"":" and eu.sex ='"+eluser.getSex()+"'";
				sqlstr+=eluser.getRealname()==null?"":" and eu.realname like '%"+eluser.getRealname()+"%'";
				sqlstr+=eluser.getUsername()==null?"":" and eu.username like '%"+eluser.getUsername()+"%'";
				//sqlstr+=eluser.getJingzhong()==null?"":" and eu.jingzhong like '%"+eluser.getJingzhong()+"%'";
				//sqlstr+=eluser.getJingzhong()==null||"0".equals(eluser.getJingzhong())?"":" and eu.jingzhong="+eluser.getJingzhong();
				sqlstr+=eluser.getJingzhong()<=0?"":" and eu.jingzhong="+eluser.getJingzhong();
				//sqlstr+=(eluser.getShengri()==null&&eluser.getShengri_end()==null)?"":" and to_date(to_char(eu.shengri,'yyyy-mm-dd'),'yyyy-mm-dd') between to_date('"+eluser.getShengri()+"','yyyy-mm-dd') and to_date('"+eluser.getShengri_end()+"','yyyy-mm-dd')";
//				sqlstr+=eluser.getShengri()==null||eluser.getShengri_end()==null?"":" and eu.shengri >= to_date('"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//				.format(eluser.getShengri())+ "','yyyy-MM-dd HH24:mi:ss')  "+" and eu.shengri <= to_date('"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//				.format(eluser.getShengri_end())
//				+ "','yyyy-MM-dd HH24:mi:ss')  ";
				sqlstr+=eluser.getShengri()==null?"":" and eu.shengri >= to_date('"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(eluser.getShengri())+ "','yyyy-MM-dd HH24:mi:ss')  ";
				sqlstr+=eluser.getShengri_end()==null?"":" and eu.shengri <= to_date('"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(eluser.getShengri_end())
				+ "','yyyy-MM-dd HH24:mi:ss')  ";
			}
			sqlstr+=" group by eu.id,eu.username,eu.realname,dept.name,exam.examcount,line.linecount )t where rownum <= ? ) where rn>=?";
			ps=ct.prepareStatement(sqlstr);
			//ps.setInt(1, dep.getLid());
			//ps.setInt(2, dep.getRid());
			ps.setInt(1, pN);
			ps.setInt(2, pS);
			rs = ps.executeQuery();
			while(rs.next()){
				Schoolrolls schoolrolls = new Schoolrolls();
				schoolrolls.setId(rs.getInt(1));
				schoolrolls.setUsername(rs.getString(2));
				schoolrolls.setRealname(rs.getString(3));
				schoolrolls.setDeptname(rs.getString(4));
				schoolrolls.setCompleteClass(rs.getInt(5));
				schoolrolls.setCompleteExam(rs.getInt(6));
				schoolrolls.setCompleteLineTrain(findRecordListCount(rs.getInt(1),4));
				list.add(schoolrolls);
			}
			
		} catch (Exception e) {
			logger.error("查询审核列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}
	public int getSchoolrollsSize(ElNode tree,ELUser eluser,int sublibs)throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			
			/*StringBuffer buffer =new StringBuffer();
			buffer.append("select * from (select t.*, rownum rn from  (select eu.id,eu.username,eu.realname,dept.name,count(sc.classid),exam.examcount,line.linecount from ELUSER eu left join ( select * from study_class where status=2) sc on eu.id=sc.userid ")
			.append("  left join DEPARTMENT dept on eu.depid=dept.id ")
			.append(" left join (SELECT count(sr.userid) examcount,sr.userid FROM (SELECT *")
			.append(" FROM exam_room WHERE iscommon = 1) er LEFT JOIN study_room sr ON er.ID =sr.roomid")
			.append(" LEFT JOIN (SELECT * FROM study_quizinfo WHERE userid = 1) sqi ON sqi.roomid =sr.roomid")
			.append(" WHERE sr.userid = 1 AND er.valid = 1 and sr.myscore>=60 group by sr.userid ) exam on eu.ID=exam.userid")
			.append(" left join (select count(*) linecount,CREATEUSERID from linetrainrecord where state=2 group by CREATEUSERID) line on eu.ID=line.CREATEUSERID")
			.append("  where     group by eu.id,eu.username,eu.realname,dept.name,exam.examcount,line.linecount )t where rownum <= ? ) where rn>=?");
			ps = ct.prepareStatement(buffer.toString());
			*/
			//String sqlstr="select count(*) from (select t.*, rownum rn from  (select eu.id,eu.username,eu.realname,dept.name,count(sc.classid),exam.examcount,line.linecount from ELUSER eu left join ( select * from study_class where status=2) sc on eu.id=sc.userid   left join DEPARTMENT dept on eu.depid=dept.id  left join (SELECT count(sr.userid) examcount,sr.userid FROM (SELECT * FROM exam_room WHERE iscommon = 1) er LEFT JOIN study_room sr ON er.ID =sr.roomid LEFT JOIN (SELECT * FROM study_quizinfo WHERE userid = 1) sqi ON sqi.roomid =sr.roomid WHERE sr.userid = 1 AND er.valid = 1 and sr.myscore>=60 group by sr.userid ) exam on eu.ID=exam.userid left join (select count(*) linecount,CREATEUSERID from linetrainrecord where state=2 group by CREATEUSERID) line on eu.ID=line.CREATEUSERID  where dept.lid >=? and dept.rid<=? ";
			String sqlstr="select count(*) from (select t.*, rownum rn from  (select eu.id,eu.username,eu.realname,dept.name,count(sc.classid),exam.examcount,line.linecount from ELUSER eu left join ( select * from study_class where status=2) sc on eu.id=sc.userid   left join DEPARTMENT dept on eu.depid=dept.id  left join (SELECT count(sr.userid) examcount,sr.userid FROM (SELECT * FROM exam_room WHERE iscommon = 1) er LEFT JOIN study_room sr ON er.ID =sr.roomid LEFT JOIN (SELECT * FROM study_quizinfo WHERE userid = 1) sqi ON sqi.roomid =sr.roomid WHERE sr.userid = 1 AND er.valid = 1 and sr.myscore>=60 group by sr.userid ) exam on eu.ID=exam.userid left join (select count(*) linecount,CREATEUSERID from linetrainrecord where state=2 group by CREATEUSERID) line on eu.ID=line.CREATEUSERID  where "+ElNodeSQL.getWhereSql(tree, "dept",sublibs)+" ";
			if(eluser==null)
				sqlstr+="";
			else{
				sqlstr+=eluser.getSex()==null?"":eluser.getSex().equals("")?"":" and eu.sex ='"+eluser.getSex()+"'";
				sqlstr+=eluser.getRealname()==null?"":" and eu.realname like '%"+eluser.getRealname()+"%'";
				sqlstr+=eluser.getUsername()==null?"":" and eu.username like '%"+eluser.getUsername()+"%'";
				//sqlstr+=eluser.getJingzhong()==null?"":" and eu.jingzhong like '%"+eluser.getJingzhong()+"%'";
				//sqlstr+=eluser.getJingzhong()==null||"0".equals(eluser.getJingzhong())?"":" and eu.jingzhong="+eluser.getJingzhong();
				sqlstr+=eluser.getJingzhong()<=0?"":" and eu.jingzhong="+eluser.getJingzhong();
				//sqlstr+=(eluser.getShengri()==null&&eluser.getShengri_end()==null)?"":" and to_date(to_char(eu.shengri,'yyyy-mm-dd'),'yyyy-mm-dd') between to_date('"+eluser.getShengri()+"','yyyy-mm-dd') and to_date('"+eluser.getShengri_end()+"','yyyy-mm-dd')";
//				sqlstr+=eluser.getShengri()==null||eluser.getShengri_end()==null?"":" and eu.shengri >= to_date('"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//				.format(eluser.getShengri())+ "','yyyy-MM-dd HH24:mi:ss')  "+" and eu.shengri <= to_date('"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//				.format(eluser.getShengri_end())
//				+ "','yyyy-MM-dd HH24:mi:ss')  ";
				sqlstr+=eluser.getShengri()==null?"":" and eu.shengri >= to_date('"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(eluser.getShengri())+ "','yyyy-MM-dd HH24:mi:ss')  ";
				sqlstr+=eluser.getShengri_end()==null?"":" and eu.shengri <= to_date('"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(eluser.getShengri_end())
				+ "','yyyy-MM-dd HH24:mi:ss')  ";
			}
			sqlstr+=" group by eu.id,eu.username,eu.realname,dept.name,exam.examcount,line.linecount )t)";
			ps=ct.prepareStatement(sqlstr);
			//ps.setInt(1, dep.getLid());
			//ps.setInt(2, dep.getRid());
			rs = ps.executeQuery();
			if(rs.next())
				return rs.getInt(1);
			
		} catch (Exception e) {
			logger.error("查询审核列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	/**
	 * 学籍查询
	 * @param deptid
	 * @return
	 * @throws ElException
	 */
	public int getSchoolrollsListSize(Integer deptid)throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			Department dep = new Department();
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, deptid);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}
			ps.close();
			rs.close();
			
			StringBuffer buffer =new StringBuffer();
			buffer.append("select count(*) from (SELECT eu.id from ELUSER eu left join ( select * from study_class where status=2) sc on eu.id=sc.userid ")
			.append("  left join DEPARTMENT dept on eu.depid=dept.id ")
			.append(" left join (SELECT count(sr.userid) examcount,sr.userid FROM (SELECT *")
			.append(" FROM exam_room WHERE iscommon = 1) er LEFT JOIN study_room sr ON er.ID =sr.roomid")
			.append(" LEFT JOIN (SELECT * FROM study_quizinfo WHERE userid = 1) sqi ON sqi.roomid =sr.roomid")
			.append(" WHERE sr.userid = 1 AND er.valid = 1 and sr.myscore>=60 group by sr.userid ) exam on eu.ID=exam.userid")
			.append(" left join (select count(*) linecount,CREATEUSERID from linetrainrecord where state=2 group by CREATEUSERID) line on eu.ID=line.CREATEUSERID")
			.append("  where  dept.lid >=? and dept.rid<=?   group by eu.id,eu.username,eu.realname,dept.name,exam.examcount,line.linecount)");
			ps = ct.prepareStatement(buffer.toString());
			ps.setInt(1, dep.getLid());
			ps.setInt(2, dep.getRid());
			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);
			
		} catch (Exception e) {
			logger.error("查询审核列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public  void  lineTrainsetcredit(int credit,int trainid) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct=DBConnection.getConnection();
			String sql="  update linetrainrecord set credit =? where trainid=? ";
			ps=ct.prepareStatement(sql);
			ps.setInt(1, credit);
			ps.setInt(2, trainid);
			ps.executeUpdate();
		
		} catch (Exception e) {
			logger.error("查询审核列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	/**
	 * 查看培训记录里的总学分显示
	 * @param tree
	 * @param state
	 * @param pageNow
	 * @param pageSize
	 * @param name
	 * @param username
	 * @param start
	 * @param end
	 * @param peixun
	 * @return
	 * @throws ElException
	 */
	public int findMyRecordallcredit(int  userid ) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			Department dep = new Department();
			ct = DBConnection.getConnection();
//			ps = ct.prepareStatement(ElQuerySql.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
//			ps.setInt(1, deptid);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				dep.setId(rs.getInt(1));
//				dep.setLid(rs.getInt(2));
//				dep.setRid(rs.getInt(3));
//			}
//			ps.close();
//			rs.close();
		
			StringBuffer buffer =new StringBuffer();
			buffer
			.append(" select ")
			.append(" sum(l.credit)  from linetrainrecord l left join eluser e on l.createuserid=e.id")
			.append(" left join department dep on e.depid = dep.id ")
			.append("  where e.id=? ");
			ps = ct.prepareStatement(buffer.toString());
			ps.setInt(1, userid);
			//ps.setInt(2, dep.getLid());
			//ps.setInt(3, dep.getRid());
			rs = ps.executeQuery();
			rs.next();
				return  rs.getInt(1);
			
		} catch (Exception e) {
			logger.error("查询审核列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

}
