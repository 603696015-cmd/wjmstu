package com.sopia.shebeipinggu.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeDao;
import com.sopia.duman.entities.ELUser;
import com.sopia.shebeipinggu.dao.PG_PolicyDao;
import com.sopia.pfms.entities.Policy;
import com.sopia.pfms.entities.PolicyLib;
import com.sopia.pfms.entities.ProductType;

public class PG_PolicyDaoImpl extends ElNodeDao implements PG_PolicyDao { 
	private static final Log logger = LogFactory.getLog(PG_PolicyDaoImpl.class);

	public PolicyLib getPolicyLibTree(int from, int stop, boolean constop) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		PolicyLib ntype = null;
		try {
			if (from == 0) {
				ntype = getPolicyLibRoot();
			} else {
				ntype = getPolicyLibByid(from);
			}
			ct = DBConnection.getConnection();
			ntype.setChild(getNtChilds(ct, ntype.getId(), stop, constop, 0));
		} catch (Exception e) {
			logger.error("栏目树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ntype;
	}
	public PolicyLib getPolicyLibRoot() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		PolicyLib nt = new PolicyLib();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id,name from POLICYLIB where parentid = ?");
			ps.setInt(1, 0);
			rs = ps.executeQuery();
			if (rs.next()) {
				nt.setId(rs.getInt(1));
				nt.setName(rs.getString(2));
			}
		} catch (Exception e) {
			logger.error("获取根栏目失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return nt;
	}
	
	public PolicyLib getPolicyLibByid(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		PolicyLib nt = new PolicyLib();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select n.id,n.name,n.description,np.id,np.name,n.isshared  from POLICYLIB n left join POLICYLIB np on  n.parentid =np.id where  n.id = ? ");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				nt.setId(rs.getInt(1));
				nt.setName(rs.getString(2));
				nt.setDescription(rs.getString(3));
				nt.setParent(new PolicyLib(rs.getInt(4), rs.getString(5)));
				nt.setIsshared(rs.getInt(6));
			}
		} catch (Exception e) {
			logger.error("获取栏目失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return nt;
	}
	
	private List<PolicyLib> getNtChilds(Connection ct, int from, int stop,
			boolean containStop, int level) throws Exception {
		List<PolicyLib> deps = new ArrayList<PolicyLib>();
		PreparedStatement ps = ct.prepareStatement("select id,name from POLICYLIB where parentid = ?");
		ps.setInt(1, from);
		ResultSet rstemp = ps.executeQuery();
		level++;
		while (rstemp.next()) {
			PolicyLib dep = new PolicyLib(rstemp.getInt(1), rstemp.getString(2)); 
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
	
	 
	public PolicyLib getPolicyLibTree(int userid, String op, int stopid,boolean containStop) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Connection ct = null;  
		PolicyLib dep = new PolicyLib(ElConstants.USER_OP_LIB, "可操作栏目树");
		dep.setLevel(0);
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select ctypeid from POLICYLIB_USE_type where userid = ?");  
			ps.setInt(1, userid);
			rs = ps.executeQuery();       
			List<PolicyLib> list = new ArrayList<PolicyLib>(); 
			while (rs.next()) {
				int newsid = rs.getInt(1);
				if (newsid == stopid && !containStop) {
				} else {
					PolicyLib Policys = getPolicyLibTree(newsid, stopid, containStop,1);
					Policys.setParent(dep);
					list.add(Policys); 
				}  
			}  
				dep.setChild(list);  	 
		} catch (Exception e) {
			logger.error("查看栏目树出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}

	private PolicyLib getPolicyLibTree (int from, int stop, boolean containStop, int level) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		PolicyLib pltype = null;
		try {
			pltype = getPolicyLibByid(from);
			pltype.setLevel(level);
			ct = DBConnection.getConnection();
			pltype.setChild(getNtChilds(ct, pltype.getId(), stop, containStop,
					level));
		} catch (Exception e) {
			logger.error("栏目树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return pltype;
	}
	
	public PolicyLib getPtypeByid(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		PolicyLib pt = new PolicyLib();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from POLICYLIB where id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				pt.setId(rs.getInt("id"));
				pt.setName(rs.getString("name"));
				pt.setDescription(rs.getString("description"));
				ps = ct.prepareStatement("select id as pid,name as planmu from POLICYLIB where id=?");
				ps.setInt(1, rs.getInt("parentid"));
				rs = ps.executeQuery();
				if (rs.next()){
					pt.setParent(new ProductType(rs.getInt("pid"), rs.getString("planmu")));
				} 
			}
		} catch (Exception e) {
			logger.error("获取栏目失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return pt;
	}
	
	public List<ELUser> getOpUsers( int typeid)	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> us = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select eu.id,eu.realname,eu.username from policylib_use_type du left join eluser eu on eu.id = du.userid where du.CTYPEID = ?");
			ps.setInt(1, typeid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser user = new ELUser(rs.getInt(1), rs.getString(2));
				if (user.getRealname() == null || "".equals(user.getRealname()))
					user.setRealname(rs.getString(3));
				us.add(user);
			}
		} catch (Exception e) {
			logger.error("查询所属栏目管理权限或使用权限的用户出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return us;
	}
	
	public void addPolicyLib(PolicyLib ptype) throws ElException { 
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null; 
		List<PolicyLib> listType = new ArrayList<PolicyLib>();
		try {
			ct = DBConnection.getConnection(); 
			ps=ct.prepareStatement("insert into policylib(name,description,parentid,lid,rid,isshared) values(?,?,?,?,?,?)");
			ps.setString(1, ptype.getName());
			ps.setString(2, ptype.getDescription());
			ps.setInt(3, ptype.getParent().getId());
			ps.setInt(4, ptype.getLid());
			ps.setInt(5, ptype.getRid());
			ps.setInt(6, ptype.getIsshared());
			ps.executeUpdate();
			String sql="select id,name,parentid,isshared from policylib";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				PolicyLib type=new PolicyLib();
				type.setId(rs.getInt(1));
				type.setName(rs.getString(2));
				type.setParent(new ElNode(rs.getInt(3)));
				type.setIsshared(rs.getInt(4));
				listType.add(type);
			}
			if(ptype.getIsshared()!=null&&ptype.getIsshared()==1){
				String ids = createSharedId(listType,ptype.getParent().getId(),"");
				if(ids!=null&&!"".equals(ids)){
					ps = ct.prepareStatement("update policylib set isshared=1 where id in ("+ids+")");
					ps.executeUpdate();
				}
			}
		} catch (Exception e) {
			logger.error("新闻公告栏目添加失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 找到所有父节点的ID 
	 * @return
	 * @throws ElException
	 */
	private String createSharedId(List<PolicyLib> listType,int parentid,String ids)throws ElException {
		if(parentid==0){
			return ids;
		}
		if(!ids.equals("")){
			ids+=",";
		}
		for(PolicyLib type:listType){
			if(type.getId()==parentid){
				ids+=type.getId();
				return createSharedId(listType,type.getParent().getId(),ids);
			}
		}
		return "";
	}
	
	public void alterpolicylib(PolicyLib ptype) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "update policylib set name=?,description=?,parentid=? where id=?";
			ps = ct.prepareStatement(sql);
			ps.setString(1, ptype.getName());
			ps.setString(2, ptype.getDescription());
			ps.setInt(3, ptype.getParent().getId());
			ps.setInt(4, ptype.getId());
			ps.executeUpdate();
			 
		} catch (Exception e) {
			logger.error("所属栏目修改失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		} 
	}
	public void deletePolicylibAndSub(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			List<Integer> typelist=this.getTypes(ct,id);
			for (int i = 0; i < typelist.size(); i++) {
				this.deletePolicyByTypeid(ct,typelist.get(i));
				this.deletePtype(typelist.get(i));
			}
		} catch (Exception e) {
			logger.error("删除保单类别失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		} 
	}
	public List<Integer> getTypes(Connection ct,int id) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Integer>  list=new ArrayList<Integer>();
		try {
			ps = ct.prepareStatement("select id from policylib where id=?");
			ps.setInt(1, id);
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
	public void deletePolicyByTypeid(Connection ct,int typeid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = ct.prepareStatement("delete from Policy where libid=?");
			ps.setInt(1, typeid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("根据所属栏目删除产品出错！", e);
			throw new ElException(e);
		}
	}
	public void deletePtype(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			PolicyLib ptype = getPtypeByid(id);
			sql = "delete from policylib where id=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, ptype.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除产品所属栏目失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		} 
	}
	
	public void updatePolicyTypeParentid(int pid, int npid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update policylib set parentid=? where parentid=? ");
			ps.setInt(1, npid);
			ps.setInt(2, pid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新产品所属栏目的父节点出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		} 
	}
	
	public void updatePolicyParentid(int pid, int npid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update policy set libid=? where libid=? ");
			ps.setInt(1, npid);
			ps.setInt(2, pid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新产品所属栏目id出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		} 
	} 
	
	//上面 暂无试用  树以删除
	
	public int addPolicy(Policy policy) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	  
		String sql = ""; 
		int id = 0 ;
		try {  
			sql = "insert into policy_pg (LIBID,COMMODITYID,IC_TABLENAME,IC_U_ID,SUBMITTIME,createid) values (?,?,?,?,?,?)";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);  
			ps.setInt(1, policy.getLibId());
			ps.setInt(2, policy.getCommodityId());
			ps.setString(3, policy.getIC_TABLENAME());
			ps.setInt(4, policy.getIC_U_ID());
			ps.setTimestamp(5, new Timestamp(System.currentTimeMillis())); 
			ps.setInt(6, policy.getCreateId().getId());
			ps.executeUpdate();  
		} catch (Exception e) {
			logger.error("增加险种失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;
	};
	
	public int alterPolicy(Policy policy) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	  
		String sql = ""; 
		int id = 0 ;
		try {  
			sql = "update policy set  starttime = ? where id = ?";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);  
			ps.setTimestamp(1, new Timestamp(System.currentTimeMillis())); 
			ps.setInt(2, policy.getId()); 
			ps.executeUpdate();  
		} catch (Exception e) {
			logger.error("增加险种失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;
	};
	
	public List<Policy> getPolicyList(Policy policy,int nid,ProductType ptypeTree,boolean isCreate, int pageNow, int pageSize) throws ElException{ 
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	
		List<Policy> Policys = new ArrayList<Policy>();
		String sql = ""; 
		String sqlW = ""; 
		try { 
			if(isCreate){
				sqlW = " and p.createid = "+policy.getCreateId().getId();
			} 
			sql = "select * from (select t.*, rownum rn from (" +
					" select p.id,pb.name,p.valid,p.submittime,p.starttime ,p.IC_tablename,p.Ic_u_Id,p.scanning ,bpl.lanmu " +
					"  ,eu.realname,eu.id euid,pf.huiyuandanwei" +
					" from policy_pg p left join" +
					" eluser eu on p.createid = eu.id left join  baoxian_product_lanmu bpl on p.libid = bpl.id left join " +
					" product_baoxian_pg pb  on p.commodityid = pb.id left join pfmsuser pf on p.createid = pf.userid " +
					" where   bpl.id in ("+createPerTypeId(ptypeTree,nid)+") "+sqlW+" order by submittime desc )  t where rownum <= ?) where rn >=? ";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);  
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize); 
			rs = ps.executeQuery();
			while (rs.next()) {
				Policy p = new Policy(rs.getInt(1));
				p.setCommodityName(rs.getString(2));
				p.setValid(rs.getInt(3)); 
				p.setSubmitTime(rs.getTimestamp(4));
				p.setStartTime(rs.getTimestamp(5)); 
				p.setIC_TABLENAME(rs.getString(6));
				p.setIC_U_ID(rs.getInt(7)); 
				p.setScanning(rs.getString(8)); 
				p.setLibName(rs.getString(9));
				p.setCreateId(new ELUser(rs.getInt(11),rs.getString(10)));
				p.setHuiyuandanwei(rs.getString(12));
				Policys.add(p);
			}  
		} catch (Exception e) {
			logger.error("查询保单列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return Policys;
	};
	
	public int getPolicyListSize(Policy policy,int nid,ProductType ptypeTree,boolean isCreate) throws ElException{ 
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	 
		String sql = "";
		String sqlW = "";
		int size = 0;
		try { 
			if(isCreate){
				sqlW = " and p.createid = "+policy.getCreateId().getId();
			} 
			sql = "select count(p.id) from policy_pg p left join" +
					"  eluser eu on p.createid = eu.id left join  baoxian_product_lanmu bpl on p.libid = bpl.id left join " +
					" product_baoxian_pg pb  on p.commodityid = pb.id left join pfmsuser pf on p.createid = pf.userid " +
					" where bpl.id in  ("+createPerTypeId(ptypeTree,nid)+")"+sqlW;
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);  
			rs = ps.executeQuery();
			if (rs.next()) {
				size = rs.getInt(1);
			}  
		} catch (Exception e) {
			logger.error("查询保单列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	};
	
	public void alterPolicyValid(int policyId , int valid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "update policy_pg set valid=? where id=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, valid);
			ps.setInt(2, policyId); 
			ps.executeUpdate();
			 
		} catch (Exception e) {
			logger.error("修改保单状态失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		} 
	}
	
	public Policy getPolicyById(int id) throws ElException{ 
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	 
		String sql = ""; 
		Policy p = new Policy();
		try { 
			sql = " select p.id,pb.name,p.valid,p.submittime,p.starttime ,p.IC_tablename,p.Ic_u_Id,p.commodityId from policy_pg p left join" +
					" eluser eu on p.createid = eu.id left join  baoxian_product_lanmu bpl on p.libid = bpl.id left join " +
					" product_baoxian pb  on p.commodityid = pb.id " +
					" where  p.id = ?";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql); 
			ps.setInt(1, id); 
			rs = ps.executeQuery();
			if (rs.next()) { 
				p.setId(rs.getInt(1));
				p.setCommodityName(rs.getString(2));
				p.setValid(rs.getInt(3));
				p.setSubmitTime(rs.getTimestamp(4));
				p.setStartTime(rs.getTimestamp(5)); 
				p.setIC_TABLENAME(rs.getString(6));
				p.setIC_U_ID(rs.getInt(7)); 
				p.setCommodityId(rs.getInt(8)); 
			}  
		} catch (Exception e) {
			logger.error("查询保单失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return p;
	};
	 
	public void updateScanning(int id, String scanning) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update policy set scanning = ? where id = ?");
			ps.setString(1, scanning);
			ps.setInt(2, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更扫描件出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	
	/**
	 * 查询出从ptid开始的有权的栏目类型ID
	 * @param ptypeTree
	 * @param ptid
	 * @return
	 */
	private String createPerTypeId(ProductType ptypeTree, int ptid){
		if(ptypeTree!=null){
			if(ptypeTree.getId()!=ptid){
				ptypeTree = getCourseTypeById(ptypeTree.getChild(),ptid,ptypeTree);
			}
			if(ptypeTree!=null&&ptypeTree.getChild()!=null){
				return createTypeId(ptypeTree.getChild(),ptypeTree.getId());
			}
			return String.valueOf(ptypeTree!=null?ptypeTree.getId():"0");
		}else{
			return null;
		}
	}
	
	/**
	 * 如果不是跟节点开始 要找出开始节点
	 * @param listType
	 * @param ptid
	 * @return
	 */
	private ProductType getCourseTypeById(List<ProductType> listType,int ptid,ProductType ptypeTree){
		ProductType  productType=null;
		for(ProductType type:listType){
			if(type.getId()!=ptid){
				productType = getCourseTypeById(type.getChild(),ptid,ptypeTree);
				if(productType!=null){
					return productType;
				}
			}else{
				return type;
			}
		}
		return productType;
	}
	
	/**
	 * 构建有权的栏目类型ID
	 * @param ptypeTree
	 * @return
	 */
	private String createTypeId(List<ProductType> listType,int id){
		String ids=id+"";
		for(ProductType type:listType){
			ids=ids+","+createTypeId(type.getChild(),type.getId());
		}
		return ids;
	}
}
