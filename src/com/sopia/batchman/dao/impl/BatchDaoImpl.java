package com.sopia.batchman.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
 
import com.sopia.ElConstants;
import com.sopia.batchman.BatchConstants;
import com.sopia.batchman.dao.BatchDao;
import com.sopia.batchman.entities.Batch;
import com.sopia.batchman.entities.Flow;
import com.sopia.classman.entities.ElClType;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElQuerySql; 
import com.sopia.common.SystemConfOp;
import com.sopia.duman.entities.ELUser;

/**
 * 培训批次DAO实现类
 * 
 * @author luocw
 *
 */
public class BatchDaoImpl implements BatchDao {
	
	private static final Log logger = LogFactory.getLog(BatchDaoImpl.class);

	public void addBatch(Batch batch) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into batch ( name, description,creater) values ( ?,?,? )");
			ps.setString(1, batch.getName());
			ps.setString(2, batch.getDescription());
			ps.setInt(3, batch.getCreater().getId());
			ps.executeUpdate();
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("SELECT IDENT_CURRENT('batch') AS id");
				rs = ps.executeQuery();

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("select batch_sequence.currval from dual ");
				rs = ps.executeQuery();
			} else {
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			if (rs.next())
				batch.setId(rs.getInt(1));
		} catch (Exception e) { 
			logger.error("新增培训班批次失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public void updateBatch(Batch batch) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql.getSQL(BatchConstants.BATCH_MODIFY));
			ps.setString(1, batch.getName());
			ps.setString(2, batch.getDescription());
			ps.setInt(3, batch.getId());
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("新增培训班批次失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public List<Batch> getBatchList(String name, int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<Batch> list = new ArrayList<Batch>();
		try {
			name = name == null ? "" : name.trim();
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql.getSQL(BatchConstants.BATCH_LIST));
			ps.setString(1, "%" + name + "%");
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Batch batch = new Batch(rs.getInt(1), rs.getString(2));
				batch.setDescription(rs.getString(3));
				list.add(batch);
			}
		} catch (Exception e) {
			logger.error("获取培训批次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	public int getBatchListSize(String name) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		int count = 0;
		try {
			name = name == null ? "" : name.trim();
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql.getSQL(BatchConstants.BATCH_LIST_SIZE));
			ps.setString(1, "%" + name + "%");
			rs = ps.executeQuery();
			rs = ps.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取培训批次列表大小失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}

	public Batch getBatchById(int id) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		Batch batch = new Batch();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select b.id,b.name,b.description,eu.id,eu.realname from batch b left join eluser eu on eu.id = b.creater where b.id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				batch = new Batch(rs.getInt(1), rs.getString(2));
				batch.setDescription(rs.getString(3));
				batch.setCreater(new ELUser(rs.getInt(4),rs.getString(5)));
			}
		} catch (Exception e) {
			logger.error("获取培训批次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return batch;
	}

	public void deleteBatch(int id) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql.getSQL(BatchConstants.BATCH_DEL_ID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			rs.close();
			
			ps = ct.prepareStatement(ElQuerySql.getSQL(BatchConstants.BATCH_CLASS_RELATION_DEL));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
			
		} catch (Exception e) {
			logger.error("删除培训批次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public List<ElClass> getBatchElclass(int id) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<ElClass> list = new ArrayList<ElClass>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql.getSQL(BatchConstants.BATCH_ELCLASS));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				ElClass elclass = new ElClass(rs.getInt(1), rs.getString(2));
				elclass.setCertificatename(rs.getString(3));
				elclass.setCltype(new ElClType(rs.getInt(4), rs.getString(7)));
				elclass.setOptionalcredit(rs.getInt(5));
				elclass.setStatus(rs.getInt(6));
				list.add(elclass);
			}
		} catch (Exception e) {
			logger.error("获取培训批次 中培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	public void addBatchClass(int batchId, int classId) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql.getSQL(BatchConstants.BATCH_CLASS_ADD));
			ps.setInt(1, batchId);
			ps.setInt(2, classId);
			rs = ps.executeQuery();
			
		} catch (Exception e) {
			logger.error("添加培训批次和培训班关联失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	public boolean checkBatchClass(int batchId, int classId) throws ElException {
		boolean b = false;
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select count(*) from batch_class where batchid =? and classid=?");
			ps.setInt(1, batchId);
			ps.setInt(2, classId);
			rs = ps.executeQuery();
			if(rs.next()){
				if(rs.getInt(1)>0)
					b =true;
			}
		} catch (Exception e) {
			logger.error("添加培训批次和培训班关联失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return b;
	}
	public List<Batch> getBatchStatList(String name, int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<Batch> list = new ArrayList<Batch>();
		try {
			name = name == null ? "" : name.trim();
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql.getSQL(BatchConstants.BATCH_STAT_LIST));
			ps.setString(1, "%" + name + "%");
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Batch batch = new Batch(rs.getInt(1), rs.getString(2));
				batch.setDescription(rs.getString(3));
				list.add(batch);
			}
		} catch (Exception e) {
			logger.error("获取培训批次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	public int getBatchStatListSize(String name) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		int count = 0;
		try {
			name = name == null ? "" : name.trim();
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql.getSQL(BatchConstants.BATCH_STAT_LIST_SIZE));
			ps.setString(1, "%" + name + "%");
			rs = ps.executeQuery();
			rs = ps.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取培训批次列表大小失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}

	public List<ElClass> getBatchElclassState(int batchId) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElClass> cls = new ArrayList<ElClass>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql.getSQL(BatchConstants.BATCH_CLASS_STAT));
			ps.setInt(1, batchId);
			rs = ps.executeQuery();
			while (rs.next()) {
				ElClass c = new ElClass(rs.getInt(1), rs.getString(2));
				c.setCreatetime(rs.getDate(3));
				c.setUserPassedCount(rs.getInt(4));
				c.setUserCount(rs.getInt(5));
				cls.add(c);
			}
		} catch (Exception e) {
			logger.error("获取统计培训批次中的培训班！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cls;
	}

	public void delBatchClass(int batchId, int elClassId) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql.getSQL(BatchConstants.BATCH_CLASS_DEL));
			ps.setInt(1, batchId);
			ps.setInt(2, elClassId);
			rs = ps.executeQuery();
			
		} catch (Exception e) {
			logger.error("删除培训批次和培训班关联失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	//流量统计
	public Flow getFlow()throws ElException{
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		Flow flow=new Flow();
		aa();
		try {
			
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from flow_statistics");
			rs = ps.executeQuery();
			if(rs.next()){
				flow.setHomevisit(rs.getInt(1));
				flow.setTotalusers(rs.getInt(2));
				flow.setCurrentonline(rs.getInt(3));
				flow.setTotalcourse(rs.getInt(4));
				flow.setTotalknowledge(rs.getInt(5));
			}
			return flow;
		} catch (Exception e) {
			logger.error("删除培训批次和培训班关联失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public void aa()throws ElException{
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		int totalusers=0;
		int totalcourse=0;
		int totalknowledge=0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
			.prepareStatement("select count(*) from eluser");
			rs = ps.executeQuery();
			if (rs.next())
				totalusers=rs.getInt(1);
			ps.close();
			rs.close();
			ps = ct
			.prepareStatement("select count(*) from course");
			rs = ps.executeQuery();
			if (rs.next())
				totalcourse=rs.getInt(1);
			ps.close();
			rs.close();
			ps = ct
			.prepareStatement("select count(*) from knowledge");
			rs = ps.executeQuery();
			if (rs.next())
				totalknowledge=rs.getInt(1);
			ps.close();
			rs.close();
			String sqlstr="update flow_statistics set totalUsers="+totalusers+" , TOTALCOURSE="+totalcourse+" , totalKnowledge="+totalknowledge;
			ps = ct.prepareStatement(sqlstr);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除培训批次和培训班关联失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public List<Batch> listBatchs(int userid, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<Batch> list = new ArrayList<Batch>();
		try {
			ct = DBConnection.getConnection();
			if(userid>0){
				ps = ct.prepareStatement("select * from (select t.*, rownum rn from (select id,name,description from batch where creater=? order by id) t where rownum <= ?) where rn >= ?");
				ps.setInt(1, userid);
				ps.setInt(2, pageNow);
				ps.setInt(3, pageSize);
			}else{
				ps = ct.prepareStatement("select * from (select t.*, rownum rn from (select id,name,description from batch order by id) t where rownum <= ?) where rn >= ?");
				ps.setInt(1, pageNow);
				ps.setInt(2, pageSize);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				Batch batch = new Batch(rs.getInt(1), rs.getString(2));
				batch.setDescription(rs.getString(3));
				list.add(batch);
			}
		} catch (Exception e) {
			logger.error("获取培训批次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}
	public int listBatchssize(int userid) throws ElException {

		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		int c = 0;
		try {
			ct = DBConnection.getConnection();
			if(userid>0){
				ps = ct.prepareStatement(" select count(id) from batch where creater=? order by id ");
				ps.setInt(1, userid);
			}else{
				ps = ct.prepareStatement(" select count(id) from batch order by id ");
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				c = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取培训批次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return c;
	}
	
}
