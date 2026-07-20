package com.sopia.lineTrainingCourse.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.lineTrainingCourse.dao.LineTrainingCourseTreeDao;
import com.sopia.lineTrainingCourse.entities.TrainTypeTree;
import com.sopia.pfms.entities.ProductType;

public class LineTrainingCourseTreeDaoImpl implements LineTrainingCourseTreeDao {
	private static final Log logger = LogFactory.getLog(LineTrainingCourseTreeDaoImpl.class);
	
	public TrainTypeTree getTraintypeTree(int from, int stop, boolean constop) 
		throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		TrainTypeTree traintype = null;
		try {
			if (from == 0) {
				traintype = getTraintypeRoot();
			} else {
				traintype = getTraintypeByid(from);
			}
			ct = DBConnection.getConnection();
			traintype.setChild(getNtChilds(ct, traintype.getId(), stop, constop, 0));
		} catch (Exception e) {
			logger.error("栏目树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return traintype;
	}
	
	public TrainTypeTree getTraintypeRoot() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		TrainTypeTree tt = new TrainTypeTree();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from line_training_type where parentid=?");
			ps.setInt(1, 0);
			rs = ps.executeQuery();
			if (rs.next()) {
				tt.setId(rs.getInt(1));
				tt.setName(rs.getString(2));
				tt.setLid(rs.getInt("lid"));
				tt.setRid(rs.getInt("rid"));
			}
		} catch (Exception e) {
			logger.error("获取根栏目失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return tt;
	}
	
	public TrainTypeTree getTraintypeByid(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		TrainTypeTree tt = new TrainTypeTree();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from line_training_type where id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				tt.setId(rs.getInt(1));
				tt.setName(rs.getString(2));
				tt.setDescription(rs.getString(3));
				tt.setParent(new TrainTypeTree(rs.getInt(1), rs.getString(2)));
				tt.setIsshared(rs.getInt(6));
				ps = ct.prepareStatement("select id as pid,name as pname from line_training_type where id=?");
				ps.setInt(1, rs.getInt("parentid"));
				rs = ps.executeQuery();
				if (rs.next()){
					tt.setParent(new ProductType(rs.getInt("pid"), rs.getString("pname")));
				}
			}
		} catch (Exception e) {
			logger.error("获取栏目失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return tt;
	}
	
	public List<TrainTypeTree> getNtChilds(Connection ct, int from, int stop,
			boolean containStop, int level) throws Exception {
		List<TrainTypeTree> deps = new ArrayList<TrainTypeTree>();
		PreparedStatement ps = ct.prepareStatement("select * from line_training_type where parentid=?");
		ps.setInt(1, from);
		ResultSet rstemp = ps.executeQuery();
		level++;
		while (rstemp.next()) {
			TrainTypeTree dep = new TrainTypeTree(rstemp.getInt(1), rstemp.getString(2));
			// dep.setParent(new KnowledgeType(rstemp.getInt(4)));
			dep.setLevel(level);
			dep.setParent(new ElNode(from));
			if (dep.getId() != stop)
				dep.setChild(getNtChilds(ct, dep.getId(), stop, containStop,
						level));
			if (!containStop && dep.getId() == stop) {

			} else
				deps.add(dep);
		}
		ps.close();
		rstemp.close();
		return deps;
	}

	public TrainTypeTree getPtypeLibById(int id) throws ElException {
		TrainTypeTree productType = new TrainTypeTree();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from line_training_type where id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				productType.setId(rs.getInt(1));
				productType.setName(rs.getString(2));
				productType.setDescription(rs.getString(3));
				productType.setParent(new ProductType(rs.getInt(4), rs.getString(5)));
				productType.setLid(rs.getInt(5));
				productType.setRid(rs.getInt(6));
			}
		} catch (Exception e) {
			logger.error("获取所属栏目失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return productType;
	}

	public void addTraintype(TrainTypeTree ptype) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null; 
		String sql = "insert into line_training_type (id,name,description,parentid,lid,rid) " +
						" values(line_training_type_sequence.nextval,?,?,?,?,?)";
		List<TrainTypeTree> listType = new ArrayList<TrainTypeTree>();
		try {
			ct = DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			ps.setString(1, ptype.getName());
			ps.setString(2, ptype.getDescription());
			ps.setInt(3, ptype.getParent().getId());
			ps.setInt(4, ptype.getLid());
			ps.setInt(5, ptype.getRid());
//			ps.setInt(6, ptype.getIsshared());
			ps.executeUpdate();
			sql="select id,name,parentid from line_training_type";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				TrainTypeTree type=new TrainTypeTree();
				type.setId(rs.getInt("id"));
				type.setName(rs.getString("name"));
				type.setParent(new ElNode(rs.getInt("parentid")));
//				type.setIsshared(rs.getInt(4));
				listType.add(type);
			}
//			if(ptype.getIsshared()!=null&&ptype.getIsshared()==1){
//				String ids = createSharedId(listType,ptype.getParent().getId(),"");
//				if(ids!=null&&!"".equals(ids)){
//					ps = ct.prepareStatement("update newstype set isshared=1 where id in ("+ids+")");
//					ps.executeUpdate();
//				}
//			}
		} catch (Exception e) {
			logger.error("线下培训类别添加失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public void alterTrainType(TrainTypeTree ptype) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "update line_training_type set name=?,description=?,parentid=? where id=?";
			ps = ct.prepareStatement(sql);
			ps.setString(1, ptype.getName());
			ps.setString(2, ptype.getDescription());
			ps.setInt(3, ptype.getParent().getId());
			ps.setInt(4, ptype.getId());
			ps.executeUpdate();
			
//			if(ntype.getIsshared()!=null&&ntype.getIsshared()==1){
//				updateParentShared(ntype.getId());
//			}
			
		} catch (Exception e) {
			logger.error("类别修改失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	public void updateProductTypeParentid(int pid, int npid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update line_training_type set parentid=? where id=? ");
			ps.setInt(1, npid);
			ps.setInt(2, pid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新类别的父节点出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	public void updateProductParentid(int pid, int npid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update line_training_course set train_type_id=? where train_type_id=? ");
			ps.setInt(1, npid);
			ps.setInt(2, pid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新培训班类别id出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	public void deletePtype(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			TrainTypeTree ptype = getTraintypeByid(id);
			sql = "delete from line_training_type where id=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, ptype.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除类别失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	public void deleteProductTypeAndSub(int id,int pid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			List<Integer> typelist=this.getTypes(ct,id,pid);
			for (int i = 0; i < typelist.size(); i++) {
				this.deleteProductByTypeid(ct,typelist.get(i));
				this.deletePtype(typelist.get(i));
			}
		} catch (Exception e) {
			logger.error("删除类别失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	public void deleteProductByTypeid(Connection ct,int typeid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = ct.prepareStatement("delete from line_training_course where train_type_id=?");
			ps.setInt(1, typeid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("根据类别删除培训班出错！", e);
			throw new ElException(e);
		}
	}
	
	public List<Integer> getTypes(Connection ct,int id,int pid) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Integer>  list=new ArrayList<Integer>();
		try {
			ps = ct.prepareStatement("select id from line_training_type where parentid=?");
			ps.setInt(1, pid);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getInt(1));
			}
		} catch (Exception e) {
			logger.error("获取树的id失败！", e);
			throw new ElException(e);
		}
		return list;
	}

}
