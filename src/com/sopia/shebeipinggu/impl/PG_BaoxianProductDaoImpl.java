package com.sopia.shebeipinggu.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.common.CheckHtml;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.OracleBlob;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.entities.ELUser;
import com.sopia.pfms.entities.BaoxianProduct;
import com.sopia.pfms.entities.InsuranceCategories;
import com.sopia.pfms.entities.ProductType;
import com.sopia.pfms.entities.Shenhezhuangtai;
import com.sopia.pfms.entities.Suoshulanmu;
import com.sopia.shebeipinggu.dao.PG_BaoxianProductDao;

public class PG_BaoxianProductDaoImpl implements PG_BaoxianProductDao{
	private static final Log logger = LogFactory.getLog(PG_BaoxianProductDaoImpl.class);
	
	private List<ProductType> getPtChilds(Connection ct, int from, int stop,
			boolean containStop, int level) throws Exception {
		List<ProductType> deps = new ArrayList<ProductType>();
		String sql = "select * from baoxian_product_lanmu where parentid=?";
		PreparedStatement ps = ct.prepareStatement(sql);
		ps.setInt(1, from);
		ResultSet rstemp = ps.executeQuery();
		level++;
		while (rstemp.next()) {
			ProductType dep = new ProductType(rstemp.getInt(1), rstemp.getString(2));
			// dep.setParent(new KnowledgeType(rstemp.getInt(4)));
			dep.setLevel(level);
			dep.setParent(new ElNode(from));
			if (dep.getId() != stop)
				dep.setChild(getPtChilds(ct, dep.getId(), stop, containStop,
						level));
			if (!containStop && dep.getId() == stop) {

			} else
				deps.add(dep);
		}
		ps.close();
		rstemp.close();
		return deps;
	}
	
	public ProductType getPtypeRoot() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ProductType pt = new ProductType();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from baoxian_product_lanmu where parentid=?");
			ps.setInt(1, 0);
			rs = ps.executeQuery();
			if (rs.next()) {
				pt.setId(rs.getInt(1));
				pt.setName(rs.getString(2));
				pt.setParent(new ProductType(rs.getInt("id"),rs.getString("lanmu")));
				pt.setLid(rs.getInt("lid"));
				pt.setRid(rs.getInt("rid"));
			}
		} catch (Exception e) {
			logger.error("获取根栏目失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return pt;
	}
	
	public ProductType getPtypeLibById(int id) throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		ProductType pt = new ProductType();
//		try {
//			ct = DBConnection.getConnection();
//			ps = ct.prepareStatement("select * from baoxian_product_lanmu where id=?");
//			ps.setInt(1, id);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				pt.setId(rs.getInt("id"));
//				pt.setName(rs.getString("lanmu"));
//				pt.setDescription(rs.getString("description"));
//				ps = ct.prepareStatement("select id as pid,lanmu as planmu from product_lanmu where id=?");
//				ps.setInt(1, rs.getInt("parentid"));
//				rs = ps.executeQuery();
//				if (rs.next()){
//					pt.setParent(new ProductType(rs.getInt("pid"), rs.getString("planmu")));
//				}
////				pt.setIsshared(rs.getInt(6));
//			}
//		} catch (Exception e) {
//			logger.error("获取栏目失败", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return pt;
		ProductType productType = new ProductType();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from baoxian_product_lanmu where id=?");
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


	public void deleteBaoxianProduct(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(
					"delete from product_baoxian_pg where id=?"
					);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除保险产品失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public int getRoleId(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int roleId = 0;
		try {
			ct = DBConnection.getConnection();
			String sql = "select e.role from product_baoxian_pg p join eluser e on p.userid=e.id where e.id=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				roleId = rs.getInt("role");
			}
		} catch (Exception e) {
			logger.error("获取roleId失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return roleId;
	}

	public BaoxianProduct showBaoxianProduct(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		BaoxianProduct baoxianProduct = new BaoxianProduct();
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select p.*,pl.lanmu,e.username,i.id as i_id,i.name as i_name from product_baoxian_pg p,baoxian_product_lanmu pl,eluser e,ic_manage_pinggu i where p.suoshulanmu=pl.id and p.userid=e.id and p.insuranceCategoryId=i.id and p.id=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				baoxianProduct = new BaoxianProduct();
				
				Suoshulanmu lanmu = new Suoshulanmu();
				lanmu.setLanmu(rs.getString("lanmu"));
				
				InsuranceCategories insuranceCategories =  new InsuranceCategories();
				insuranceCategories.setId(rs.getInt("i_id"));
				insuranceCategories.setName(rs.getString("i_name"));
				
				baoxianProduct.setId(rs.getInt("id"));
				baoxianProduct.setName(rs.getString("name"));
				baoxianProduct.setJianjie(new OracleBlob().getContent(rs.getBlob("jianjie")));
				baoxianProduct.setJieshao(rs.getString("jieshao"));
				baoxianProduct.setSuoshulanmu(rs.getInt("suoshulanmu"));
				baoxianProduct.setKey(rs.getString("key"));
				baoxianProduct.setChanpinbianhao(rs.getInt("chanpinbianhao"));
				baoxianProduct.setShichangjia(rs.getDouble("shichangjia"));
				baoxianProduct.setHuiyuanjia(rs.getDouble("huiyuanjia"));
				baoxianProduct.setFabuzhe(rs.getString("username"));
				baoxianProduct.setChanpintese(rs.getString("chanpintese"));
				
				baoxianProduct.setFabuzhesuozaidanwei(rs.getString("fabuzhesuozaidanwei"));
				baoxianProduct.setFabushijian(rs.getTimestamp("fabushijian"));
				baoxianProduct.setXiugaishijian(rs.getTimestamp("xiugaishijian"));
				baoxianProduct.setXiugaizhe(rs.getString("xiugaizhe"));
				baoxianProduct.setChanpintupian(rs.getString("chanpintupian"));
				baoxianProduct.setChanpinjiancheng(rs.getString("chanpinjiancheng"));
				baoxianProduct.setDianjishubenyue(rs.getInt("dianjishubenyue"));
				baoxianProduct.setDianjishubenzhou(rs.getInt("dianjishubenzhou"));
				baoxianProduct.setDianjishujinri(rs.getInt("dianjishujinri"));
				baoxianProduct.setDianjishuzongji(rs.getInt("dianjishuzongji"));
				baoxianProduct.setFuwurexian(rs.getString("fuwurexian"));
				
				baoxianProduct.setJutitiaokuan(rs.getString("jutitiaokuan"));
				baoxianProduct.setChuwaizeren(rs.getString("chuwaizeren"));
				baoxianProduct.setKehugaozhishu(rs.getString("kehugaozhishu"));
				baoxianProduct.setChanpinliangdian(rs.getString("chanpinliangdian"));
				baoxianProduct.setInsuranceCategoryId(rs.getInt("insuranceCategoryId"));
				baoxianProduct.setUserId(rs.getInt("userid"));
				baoxianProduct.setLanmu(lanmu);
				baoxianProduct.setInsuranceCategories(insuranceCategories);
				baoxianProduct.setLogo(rs.getString("logo"));
			}
		} catch (Exception e) {
			logger.error("显示保险产品失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return baoxianProduct;
	}

	public void updateBaoxianProduct(int roleId, BaoxianProduct baoxianProduct,int shenhezhuangtai,boolean is_product_fabu_can_alter)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlAppend = "";
		try {
			ct = DBConnection.getConnection();
			if(is_product_fabu_can_alter == true){
					sqlAppend = sqlAppend + " ,shenhezhuangtai=?,fabushijian=sysdate ";
			}
			
			sql = 
				"update product_baoxian_pg set " +
				"name = ?,jieshao=?,suoshulanmu=?,key=?," +
				"chanpinbianhao=?,shichangjia=?,huiyuanjia=?,fabuzhe=?,fabuzhesuozaidanwei=?," +
				"chanpintupian=?,dianjishujinri=?,dianjishubenzhou=?,dianjishubenyue=?," +
				"dianjishuzongji=?,fuwurexian=?,jutitiaokuan=?,chuwaizeren=?,kehugaozhishu=?," +
				"chanpinliangdian=?,insuranceCategoryId=?,logo=?" + sqlAppend + 
				" where id=?";
				
			ps = ct.prepareStatement(sql);
			ps.setString(1, baoxianProduct.getName());
			ps.setString(2, baoxianProduct.getJieshao());
			ps.setInt(3, baoxianProduct.getSuoshulanmu());
			ps.setString(4, baoxianProduct.getKey());
			ps.setInt(5, baoxianProduct.getChanpinbianhao());
			ps.setDouble(6, baoxianProduct.getShichangjia());
			ps.setDouble(7, baoxianProduct.getHuiyuanjia());
			ps.setString(8, baoxianProduct.getFabuzhe());
			ps.setString(9, baoxianProduct.getFabuzhesuozaidanwei());
			ps.setString(10, baoxianProduct.getChanpintupian());
			
			
			ps.setInt(11, baoxianProduct.getDianjishujinri());
			ps.setInt(12, baoxianProduct.getDianjishubenzhou());
			ps.setInt(13, baoxianProduct.getDianjishubenyue());
			ps.setInt(14, baoxianProduct.getDianjishuzongji());
			ps.setString(15, baoxianProduct.getFuwurexian());
			ps.setString(16, baoxianProduct.getJutitiaokuan());
			ps.setString(17, baoxianProduct.getChuwaizeren());
			ps.setString(18, baoxianProduct.getKehugaozhishu());
			ps.setString(19, baoxianProduct.getChanpinliangdian());
			ps.setInt(20, baoxianProduct.getInsuranceCategoryId());
			ps.setString(21, baoxianProduct.getLogo());
			
			if(is_product_fabu_can_alter == true){
				ps.setInt(22,1);//设置为1，修改后将其他状态变更为已创建状态
				ps.setInt(23, baoxianProduct.getId());
			}else{
				ps.setInt(22, baoxianProduct.getId());
			}
			
			ps.executeUpdate();
			
			
			ps = ct.prepareStatement("update product_baoxian_pg SET jianjie = empty_blob() WHERE id = ?"); 
			ps.setInt(1, baoxianProduct.getId());
			ps.executeUpdate();
			OracleBlob setblob = new OracleBlob("product_baoxian_pg","id",baoxianProduct.getId()+"","jianjie",baoxianProduct.getJianjie(),"修改计划失败",ct);
			setblob.updateContent();
		} catch (Exception e) {
			logger.error("修改计划失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		
	}

	public List<Suoshulanmu> suoshulanmuList() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		List<Suoshulanmu> suoshulanmuList = new ArrayList<Suoshulanmu>();
		try {
			ct = DBConnection.getConnection();
			sql = "select * from baoxian_product_lanmu";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Suoshulanmu lanmu = new Suoshulanmu();
				lanmu.setId(rs.getInt("id"));
				lanmu.setLanmu(rs.getString("lanmu"));
				suoshulanmuList.add(lanmu);
			}
		} catch (Exception e) {
			logger.error("保险产品所属栏目查询失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return suoshulanmuList;
	}

	public void addBaoxianProduct(boolean is_baoxian_product_sh,BaoxianProduct baoxianProduct,ELUser elUser,int ptype_parent_id)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "insert into product_baoxian_pg (id,name,jieshao,suoshulanmu,key," +
		"shichangjia,huiyuanjia,chanpintupian,fabuzhe," +
		"fuwurexian,jutitiaokuan,chuwaizeren,kehugaozhishu,fabushijian,userid,jianjie,shenhezhuangtai,chanpinjiancheng,chanpinliangdian,insuranceCategoryId,logo,chanpintese,zhengzhantuijian)" +
		" values (product_baoxian_pg_sequence.nextval,?,?,?,?," +
		"?,?,?,?," +
		"?,?,?,?,sysdate,?,empty_blob(),?,?,?,?,?,?,?)";
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			
			ps.setString(1, baoxianProduct.getName());
			ps.setString(2, baoxianProduct.getJieshao());
			ps.setInt(3, ptype_parent_id);
			ps.setString(4, baoxianProduct.getKey());
			
			ps.setDouble(5, baoxianProduct.getShichangjia());
			ps.setDouble(6, baoxianProduct.getHuiyuanjia());
			ps.setString(7, baoxianProduct.getChanpintupian());
			ps.setString(8, elUser.getUsername());
			
			ps.setString(9, baoxianProduct.getFuwurexian());
			ps.setString(10, baoxianProduct.getJutitiaokuan());
			ps.setString(11, baoxianProduct.getChuwaizeren());	
			ps.setString(12, baoxianProduct.getKehugaozhishu());
			ps.setInt(13, elUser.getId());
			if(is_baoxian_product_sh){
				ps.setInt(14, 1);
			}else{
				ps.setInt(14, 2);
			}
			ps.setString(15, baoxianProduct.getChanpinjiancheng());
			ps.setString(16, baoxianProduct.getChanpinliangdian());
			ps.setInt(17, baoxianProduct.getInsuranceCategoryId());
			ps.setString(18, baoxianProduct.getLogo());
			ps.setString(19, baoxianProduct.getChanpintese());
			ps.setString(20, "普通");
			
			ps.executeUpdate();
			
			OracleBlob setblob = new OracleBlob(ct,"product_baoxian_pg_sequence","product_baoxian_pg","id","jianjie",baoxianProduct.getJianjie(),"添加设备失败");
			setblob.addContent();
			
		} catch (Exception e) {
			logger.error("添加设备失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public ELUser getELUser(int id) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ELUser elUser  = new ELUser();
		try {
			ct = DBConnection.getConnection();
			String sql = "select id,username from eluser where id=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				elUser.setUsername(rs.getString("username"));
				elUser.setId(rs.getInt("id"));
			}
		} catch (Exception e) {
			logger.error("获取用户失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return elUser;
	}

	public void shenheBaoxianProduct(int roleId, int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			if(roleId == 1){
				sql = 
					"update product_baoxian_pg set " +
						"shenhezhuangtai = ?" + 
						" where id=?";
			}
			
			ps = ct.prepareStatement(sql);
			ps.setInt(1, 2);
			ps.setInt(2, id);
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("修改保险产品审核状态失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	public void shenheBaoxianProductNotPass(int roleId, int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			if(roleId == 1){
				sql = 
					"update product_baoxian_pg set " +
						"shenhezhuangtai = ?" + 
						" where id=?";
			}
			
			ps = ct.prepareStatement(sql);
			ps.setInt(1, 3);
			ps.setInt(2, id);
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("修改保险产品审核状态失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public List<BaoxianProduct> getAllBaoxianProductByUidByPerOrShar(int nid,
			ProductType ptypeTree, Integer status, int pageNow, int pageSize,
			BaoxianProduct baoxianProduct,Date starttime,Date endtime) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<BaoxianProduct> baoxianProductList = new ArrayList<BaoxianProduct>();
		String sql = "";
		String sqlAppend = "";
		try {
			if(baoxianProduct != null){
				if(baoxianProduct.getName() != null)
					if(!baoxianProduct.getName().equals(""))
						sqlAppend = sqlAppend + " and name like '%" + baoxianProduct.getName() + "%'";
				if(baoxianProduct.getFabuzhesuozaidanwei() != null)
					if(!baoxianProduct.getFabuzhesuozaidanwei().equals(""))
						sqlAppend = sqlAppend + " and fabuzhesuozaidanwei like '%" + baoxianProduct.getFabuzhesuozaidanwei() + "%'";
				if(baoxianProduct.getFabuzhe() != null)
					if(!baoxianProduct.getFabuzhe().equals(""))
						sqlAppend = sqlAppend + " and fabuzhe like '%" + baoxianProduct.getFabuzhe() + "%'";
				if(baoxianProduct.getShenhezhuangtai() != 0)
					sqlAppend = sqlAppend + " and p.shenhezhuangtai = '" + baoxianProduct.getShenhezhuangtai() + "'";
				if(baoxianProduct.getZhengzhantuijian() != null)
					if(!baoxianProduct.getZhengzhantuijian().equals(""))
						sqlAppend = sqlAppend + " and zhengzhantuijian like '%" + baoxianProduct.getZhengzhantuijian() + "%'";
			}
			if(starttime != null)
				sqlAppend  = sqlAppend + " and to_char(fabushijian,'yyyy-MM-dd HH:mm:ss') > '" + starttime+"'";
			if(endtime != null)
				sqlAppend  = sqlAppend + " and to_char(fabushijian,'yyyy-MM-dd HH:mm:ss') < '" + endtime+"'";
				
			ct = DBConnection.getConnection();
			sql = 
				"select * from (select t.*,rownum rn from(select p.id,p.name,p.shenhezhuangtai,p.shichangjia,p.huiyuanjia," +
				"p.fabuzhe,p.fabuzhesuozaidanwei,p.fabushijian,p.suoshulanmu,p.zhengzhantuijian,p.INSURANCECATEGORYID ,pl.lanmu,ps.shenhezhuangtai as psshenhezhuangtai  from product_baoxian_pg p,baoxian_product_lanmu pl,product_shenhezhuangtai ps " +
				"where p.suoshulanmu = pl.id  and p.shenhezhuangtai=ps.id " + sqlAppend + 
				"and pl.id in ("+createPerTypeId(ptypeTree,nid)+") " +
						"order by p.fabushijian desc) t where rownum <=?)where rn>=?" ;
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
				
			rs = ps.executeQuery();
			while (rs.next()) {
				baoxianProduct = new BaoxianProduct();
				Suoshulanmu lanmu = new Suoshulanmu();
				lanmu.setLanmu(rs.getString("lanmu"));
				Shenhezhuangtai shzt = new Shenhezhuangtai();
				shzt.setShenhezhuangtai(rs.getString("psshenhezhuangtai"));
				baoxianProduct.setId(rs.getInt("id"));
				baoxianProduct.setName(rs.getString("name"));
				baoxianProduct.setSuoshulanmu(rs.getInt("suoshulanmu"));
				baoxianProduct.setShichangjia(rs.getDouble("shichangjia"));
				baoxianProduct.setHuiyuanjia(rs.getDouble("huiyuanjia"));
				baoxianProduct.setFabuzhe(rs.getString("fabuzhe"));
				baoxianProduct.setFabuzhesuozaidanwei(rs.getString("fabuzhesuozaidanwei"));
				baoxianProduct.setFabushijian(rs.getTimestamp("fabushijian"));
				baoxianProduct.setShenhezhuangtai(rs.getInt("shenhezhuangtai"));
				baoxianProduct.setZhengzhantuijian(rs.getString("zhengzhantuijian"));
				baoxianProduct.setLanmu(lanmu);
				baoxianProduct.setShenhezhuangtai_entity(shzt);
				
				baoxianProduct.setInsuranceCategoryId(rs.getInt("INSURANCECATEGORYID"));
				baoxianProductList.add(baoxianProduct);
			}
		} catch (Exception e) {
			logger.error("全部保险产品列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return baoxianProductList;
	}

	public int getAllBaoxianProductCountByUidByPerOrShar(int nid,
			ProductType ptypeTree, Integer status, int pageNow, int pageSize,
			BaoxianProduct baoxianProduct,Date starttime,Date endtime) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		String sql = "";
		String sqlAppend = "";
		try {
			if(baoxianProduct != null){
				if(baoxianProduct.getName() != null)
					if(!baoxianProduct.getName().equals(""))
						sqlAppend = sqlAppend + " and name like '%" + baoxianProduct.getName() + "%'";
				if(baoxianProduct.getFabuzhesuozaidanwei() != null)
					if(!baoxianProduct.getFabuzhesuozaidanwei().equals(""))
						sqlAppend = sqlAppend + " and fabuzhesuozaidanwei like '%" + baoxianProduct.getFabuzhesuozaidanwei() + "%'";
				if(baoxianProduct.getFabuzhe() != null)
					if(!baoxianProduct.getFabuzhe().equals(""))
						sqlAppend = sqlAppend + " and fabuzhe like '%" + baoxianProduct.getFabuzhe() + "%'";
				if(baoxianProduct.getShenhezhuangtai() != 0)
					sqlAppend = sqlAppend + " and p.shenhezhuangtai = '" + baoxianProduct.getShenhezhuangtai() + "'";
				if(baoxianProduct.getZhengzhantuijian() != null)
					if(!baoxianProduct.getZhengzhantuijian().equals(""))
						sqlAppend = sqlAppend + " and zhengzhantuijian like '%" + baoxianProduct.getZhengzhantuijian() + "%'";
			}
			if(starttime != null)
				sqlAppend  = sqlAppend + " and to_char(fabushijian,'yyyy-MM-dd HH:mm:ss') > '" + starttime+"'";
			if(endtime != null)
				sqlAppend  = sqlAppend + " and to_char(fabushijian,'yyyy-MM-dd HH:mm:ss') < '" + endtime+"'";
			
			ct = DBConnection.getConnection();
			sql = "select count(*) from product_baoxian_pg p, " +
			" product_shenhezhuangtai ps, " +
			" baoxian_product_lanmu pl " +
			" where p.shenhezhuangtai=ps.id and p.suoshulanmu=pl.id  " + sqlAppend+
			" and pl.id in ("+createPerTypeId(ptypeTree,nid)+") " ;
			ps = ct.prepareStatement(sql);
			
			rs = ps.executeQuery();
			if(rs.next())
				size = rs.getInt(1);
		} catch (Exception e) {
			logger.error("全部保险产品列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}
	
	public void updateAllBaoxianProductShenhezhuangtai() throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = 
				"update product_baoxian_pg set " +
					"shenhezhuangtai = 2" ;
			
			ps = ct.prepareStatement(sql);
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("修改审核状态失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<BaoxianProduct> getBaoxianProductByUidByPerOrShar(boolean is_baoxian_product_sh,
			String userid, int nid, ProductType ptypeTree, Integer status,
			int pageNow, int pageSize, BaoxianProduct baoxianProduct,Date starttime,Date endtime)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<BaoxianProduct> baoxianProductList = new ArrayList<BaoxianProduct>();
		String sql = "";
		String sqlAppend = "";
		try {
//			if(is_baoxian_product_sh){
//				sqlAppend = sqlAppend + " and p.shenhezhuangtai = 2 " ;//需要审核
//			}else{
//				this.updateAllBaoxianProductShenhezhuangtai();//不需要审核下,更新所有产品的审核状态为审核通过
//			}
			if(baoxianProduct != null){
				if(baoxianProduct.getName() != null)
					if(!baoxianProduct.getName().equals(""))
						sqlAppend = sqlAppend + " and name like '%" + baoxianProduct.getName() + "%'";
				if(baoxianProduct.getShenhezhuangtai() != 0)
					sqlAppend = sqlAppend + " and p.shenhezhuangtai = '" + baoxianProduct.getShenhezhuangtai() + "'";
				
			}
			if(starttime != null)
				sqlAppend  = sqlAppend + " and to_char(fabushijian,'yyyy-MM-dd HH:mm:ss') > '" + starttime+"'";
			if(endtime != null)
				sqlAppend  = sqlAppend + " and to_char(fabushijian,'yyyy-MM-dd HH:mm:ss') < '" + endtime+"'";
				
			ct = DBConnection.getConnection();
//			sql = 
//				"select * from (select t.*,rownum rn from(select p.id,p.name,p.shenhezhuangtai,p.shichangjia,p.huiyuanjia," +
//				" p.fabushijian,p.suoshulanmu ,clt.lanmu,elu.realname,ps.shenhezhuangtai as psshenhezhuangtai " +
//				" from product_baoxian p " +
//				" join eluser elu on p.userid=elu.id " +
//				" join product_shenhezhuangtai ps on p.shenhezhuangtai=ps.id " +
//				" inner join ("+((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).generateSQLByTree("baoxian_product_lanmu", ptypeTree, true)+") clt on p.suoshulanmu = clt.id"+ 
//				" where  p.userid=? " + sqlAppend + 
//						" order by p.fabushijian desc) t where rownum <=?)where rn>=?" ;
			sql = 
				"select * from (select t.*,rownum rn from(select p.id,p.name,p.shenhezhuangtai,p.shichangjia,p.huiyuanjia," +
				" p.fabushijian,p.suoshulanmu,elu.realname,ps.shenhezhuangtai as psshenhezhuangtai,pl.lanmu " +
				" from product_baoxian_pg p " +
				" join eluser elu on p.userid=elu.id " +
				" join product_shenhezhuangtai ps on p.shenhezhuangtai=ps.id " +
				" join baoxian_product_lanmu pl on p.suoshulanmu=pl.id "+
//				" inner join ("+((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).generateSQLByTree("baoxian_product_lanmu", ptypeTree, true)+") clt on p.suoshulanmu = clt.id"+ 
				" where  p.userid=? and p.suoshulanmu in ("+createPerTypeId(ptypeTree,nid)+")" + sqlAppend + 
						" order by p.fabushijian desc) t where rownum <=?)where rn>=?" ;
			ps = ct.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(userid));
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
				
			rs = ps.executeQuery();
			while (rs.next()) {
				baoxianProduct = new BaoxianProduct();
				baoxianProduct.setId(rs.getInt("id"));
				baoxianProduct.setName(rs.getString("name"));
				baoxianProduct.setShichangjia(rs.getDouble("shichangjia"));
				baoxianProduct.setHuiyuanjia(rs.getDouble("huiyuanjia"));
				baoxianProduct.setFabushijian(rs.getTimestamp("fabushijian"));
				baoxianProduct.setShenhezhuangtai(rs.getInt("shenhezhuangtai"));
				
				ProductType p = new ProductType();
				p.setId(rs.getInt("id"));
				baoxianProduct.setPtype(p);
//				baoxianProduct.setPtype(new ProductType(rs.getInt("id"),rs.getString("lanmu")));
				ELUser user= new ELUser();
				user.setRealname(rs.getString("realname"));
				//news.setContent(new OracleBlob().getContent(rs.getBlob(9)));
				Suoshulanmu lanmu = new Suoshulanmu();
				lanmu.setLanmu(rs.getString("lanmu"));
				Shenhezhuangtai shzt = new Shenhezhuangtai();
				shzt.setShenhezhuangtai(rs.getString("psshenhezhuangtai"));
				baoxianProduct.setLanmu(lanmu);
				baoxianProduct.setShenhezhuangtai_entity(shzt);
				baoxianProductList.add(baoxianProduct);
			}
		} catch (Exception e) {
			logger.error("我的保险产品列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return baoxianProductList;
	}

	public int getBaoxianProductCountByUidByPerOrShar(boolean is_baoxian_product_sh,String userid, int nid,
			ProductType ptypeTree, Integer status, int pageNow, int pageSize,
			BaoxianProduct baoxianProduct,Date starttime,Date endtime) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		String sql = "";
		String sqlAppend = "";
		try {
//			if(is_baoxian_product_sh)
//				sqlAppend = sqlAppend + " and p.shenhezhuangtai = 2 " ;
			if(baoxianProduct != null){
				if(baoxianProduct.getName() != null)
					if(!baoxianProduct.getName().equals(""))
						sqlAppend = sqlAppend + " and name like '%" + baoxianProduct.getName() + "%'";
				if(baoxianProduct.getShenhezhuangtai() != 0)
					sqlAppend = sqlAppend + " and p.shenhezhuangtai = '" + baoxianProduct.getShenhezhuangtai() + "'";
				
			}
			if(starttime != null)
				sqlAppend  = sqlAppend + " and to_char(fabushijian,'yyyy-MM-dd HH:mm:ss') > '" + starttime+"'";
			if(endtime != null)
				sqlAppend  = sqlAppend + " and to_char(fabushijian,'yyyy-MM-dd HH:mm:ss') < '" + endtime+"'";
			
			ct = DBConnection.getConnection();
//			sql = "select count(*) " +
//					" from product_baoxian p " +
//					" join eluser elu on p.userid=elu.id " +
//					" join product_shenhezhuangtai ps on p.shenhezhuangtai=ps.id " +
//					" inner join ("+((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).generateSQLByTree("baoxian_product_lanmu", ptypeTree, true)+") clt on p.suoshulanmu = clt.id"+ 
//					" where  p.userid=? " + sqlAppend;
			sql = "select count(*) " +
			" from product_baoxian_pg p " +
			" join eluser elu on p.userid=elu.id " +
			" join product_shenhezhuangtai ps on p.shenhezhuangtai=ps.id " +
			" join baoxian_product_lanmu pl on p.suoshulanmu = pl.id "+
//			" inner join ("+((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).generateSQLByTree("baoxian_product_lanmu", ptypeTree, true)+") clt on p.suoshulanmu = clt.id"+ 
			" where  p.userid=? and p.suoshulanmu in ("+createPerTypeId(ptypeTree,nid)+")" + sqlAppend;
			ps = ct.prepareStatement(sql);
			
			ps.setInt(1, Integer.parseInt(userid));
			rs = ps.executeQuery();
			if(rs.next())
				size = rs.getInt(1);
		} catch (Exception e) {
			logger.error("我的保险产品列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
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
	
	
	public ProductType getProTypeTree(int from, int stop, boolean constop)
	throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		ProductType ptype = null;
		try {
			if (from == 0) {
				ptype = getPtypeRoot();
			} else {
				ptype = getPtypeLibById(from);
			}
			ct = DBConnection.getConnection();
			ptype.setChild(getPtChilds(ct, ptype.getId(), stop, constop, 0));
		} catch (Exception e) {
			logger.error("栏目树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ptype;
}

//	public List<ProductType> getPtChilds(Connection ct, int from, int stop,
//		boolean containStop, int level) throws Exception {
//		List<ProductType> deps = new ArrayList<ProductType>();
//		String sql = "select * from baoxian_product_lanmu where parentid=?";
//		PreparedStatement ps = ct.prepareStatement(sql);
//		ps.setInt(1, from);
//		ResultSet rstemp = ps.executeQuery();
//		level++;
//		while (rstemp.next()) {
//			ProductType dep = new ProductType(rstemp.getInt(1), rstemp.getString(2));
//			// dep.setParent(new KnowledgeType(rstemp.getInt(4)));
//			dep.setLevel(level);
//			dep.setParent(new ElNode(from));
//			if (dep.getId() != stop)
//				dep.setChild(getPtChilds(ct, dep.getId(), stop, containStop,
//						level));
//			if (!containStop && dep.getId() == stop) {
//		
//			} else
//				deps.add(dep);
//		}
//		ps.close();
//		rstemp.close();
//		return deps;
//	}

//	public ProductType getPtypeRoot() throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		ProductType pt = new ProductType();
//		try {
//			ct = DBConnection.getConnection();
//			ps = ct.prepareStatement("select * from baoxian_product_lanmu where parentid=?");
//			ps.setInt(1, 0);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				pt.setId(rs.getInt(1));
//				pt.setName(rs.getString(2));
//			}
//		} catch (Exception e) {
//			logger.error("获取根栏目失败", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return pt;
//	}

//	public ProductType getPtypeByid(int id) throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		ProductType pt = new ProductType();
//		try {
//			ct = DBConnection.getConnection();
//			ps = ct.prepareStatement("select * from baoxian_product_lanmu where id=?");
//			ps.setInt(1, id);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				pt.setId(rs.getInt(1));
//				pt.setName(rs.getString(2));
//				pt.setDescription(rs.getString(3));
//				pt.setParent(new ProductType(rs.getInt(4), rs.getString(5)));
//		//		pt.setIsshared(rs.getInt(6));
//			}
//		} catch (Exception e) {
//			logger.error("获取栏目失败", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return pt;
//	}

	public void addProducttype(ProductType ptype) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null; 
		String sql = "insert into baoxian_product_lanmu(id,lanmu,description,parentid) " +
						" values(baoxian_product_lanmu_sequence.nextval,?,?,?)";
		List<ProductType> listType = new ArrayList<ProductType>();
		try {
			ct = DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			ps.setString(1, ptype.getName());
			ps.setString(2, ptype.getDescription());
			ps.setInt(3, ptype.getParent().getId());
//			ps.setInt(4, ptype.getLid());
//			ps.setInt(5, ptype.getRid());
//			ps.setInt(6, ptype.getIsshared());
			ps.executeUpdate();
			sql="select id,lanmu,parentid from baoxian_product_lanmu";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				ProductType type=new ProductType();
				type.setId(rs.getInt("id"));
				type.setName(rs.getString("lanmu"));
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
			logger.error("产品所属栏目添加失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public void alterProductType(ProductType ptype) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "update baoxian_product_lanmu set lanmu=?,description=?,parentid=? where id=?";
//			alterNode(ct, ntype, "newstype", "1=1");
			ps = ct.prepareStatement(sql);
			ps.setString(1, ptype.getName());
			ps.setString(2, ptype.getDescription());
			ps.setInt(3, ptype.getParent().getId());
//			ps.setInt(4, ptype.getIsshared());
			ps.setInt(4, ptype.getId());
			ps.executeUpdate();
			
//			if(ntype.getIsshared()!=null&&ntype.getIsshared()==1){
//				updateParentShared(ntype.getId());
//			}
			
		} catch (Exception e) {
			logger.error("所属栏目修改失败", e);
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
			ps = ct.prepareStatement("select id from baoxian_product_lanmu where id=?");
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

	public void deleteProductTypeAndSub(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
//			Typelrid typelrid=this.getLidRid(ct,id, "newstype");
			List<Integer> typelist=this.getTypes(ct,id);
			for (int i = 0; i < typelist.size(); i++) {
				//System.out.println("delId:"+typelist.get(i));
				//根据id删除类别以及类别下的资源(先删资源)
				this.deleteProductByTypeid(ct,typelist.get(i));
				this.deletePtype(typelist.get(i));
			}
		} catch (Exception e) {
			logger.error("删除新闻类别失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	public void deleteProductByTypeid(Connection ct,int typeid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = ct.prepareStatement("delete from product_baoxian_pg where suoshulanmu=?");
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
			ProductType ptype = getPtypeLibById(id);
			sql = "delete from baoxian_product_lanmu where id=?";
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

	public List<ELUser> getOpUsers(int typeid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> us = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select eu.id,eu.realname,eu.username from baoxian_product_op_type du left join eluser eu on eu.id = du.userid where du.ptypeid = ?");
			ps.setInt(1, typeid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser user = new ELUser(rs.getInt(1), rs.getString(2));
				if (user.getRealname() == null || "".equals(user.getRealname()))
					user.setRealname(rs.getString(3));
				us.add(user);
			}
		} catch (Exception e) {
			logger.error("查询产品所属栏目管理权限或使用权限的用户出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return us;
	}

	public void updateProductParentid(int pid, int npid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update product_baoxian_pg set suoshulanmu=? where suoshulanmu=? ");
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

	public void updateProductTypeParentid(int pid, int npid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update baoxian_product_lanmu set parentid=? where parentid=? ");
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

	public List<Shenhezhuangtai> shenhezhuangtaiList() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		List<Shenhezhuangtai> suoshulanmuList = new ArrayList<Shenhezhuangtai>();
		try {
			ct = DBConnection.getConnection();
			sql = "select * from product_shenhezhuangtai";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Shenhezhuangtai shzt = new Shenhezhuangtai();
				shzt.setId(rs.getInt("id"));
				shzt.setShenhezhuangtai(rs.getString("shenhezhuangtai"));
				suoshulanmuList.add(shzt);
			}
		} catch (Exception e) {
			logger.error("审核状态查询失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return suoshulanmuList;
	}

	public BaoxianProduct getBaoxianProductByid(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		BaoxianProduct baoxianProduct = new BaoxianProduct();
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select p.*,ic.name as icname,ic.id as icid,ic.tablename as ictablename  from product_baoxian_pg p join ic_manage_pinggu ic on p.insurancecategoryid=ic.id where p.id=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				baoxianProduct.setId(rs.getInt("id"));
				baoxianProduct.setName(rs.getString("name"));
				baoxianProduct.setLogo(rs.getString("logo"));
				baoxianProduct.setJianjie(new OracleBlob().getContent(rs.getBlob("jianjie")));
				baoxianProduct.setChanpinliangdian(rs.getString("chanpinliangdian"));
				baoxianProduct.setShichangjia(rs.getDouble("shichangjia"));
				baoxianProduct.setHuiyuanjia(rs.getDouble("huiyuanjia"));
				baoxianProduct.setChanpintese(rs.getString("chanpintese"));
				baoxianProduct.setJutitiaokuan(rs.getString("jutitiaokuan"));
				baoxianProduct.setKehugaozhishu(rs.getString("kehugaozhishu"));
				baoxianProduct.setChuwaizeren(rs.getString("chuwaizeren"));
				baoxianProduct.setJieshao(rs.getString("jieshao"));
				Suoshulanmu lanmu = new Suoshulanmu(rs.getInt("suoshulanmu"));
				baoxianProduct.setLanmu(lanmu);
				InsuranceCategories insuranceCategories = new InsuranceCategories();
				insuranceCategories.setId(rs.getInt("icid"));
				insuranceCategories.setName(rs.getString("icname"));
				insuranceCategories.setTableName(rs.getString("ictablename"));
				baoxianProduct.setInsuranceCategories(insuranceCategories);
				
			}
		} catch (Exception e) {
			logger.error("查看信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return baoxianProduct;
	}

	public InsuranceCategories getInsuranceCategoryByid(int id)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		InsuranceCategories insuranceCategories = new InsuranceCategories();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from ic_manage_pinggu where id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				insuranceCategories.setId(rs.getInt("id"));
				insuranceCategories.setName(rs.getString("name"));
				insuranceCategories.setDemourl(rs.getString("demourl"));
				insuranceCategories.setCreateTime(rs.getTimestamp("createtime"));
				insuranceCategories.setDescription(rs.getString("description"));
				insuranceCategories.setTableName(rs.getString("tablename"));
			}
		} catch (Exception e) {
			logger.error("查看信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return insuranceCategories;
	}

	public ProductType getProTypeLibTree(int userid, String op, int stopid,
			boolean containStop) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Connection ct = null; 
//		KnowledgeType dep = op.equals("op") ? new KnowledgeType(0, "可操作的资源库")
//				: new KnowledgeType(0, "可使用的资源库");
		ProductType ptype = new ProductType(ElConstants.USER_OP_LIB, "可操作的保险产品所属栏目");
		ptype.setLevel(0);
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select ptypeid from baoxian_product_" +op+ "_type where userid = ?");  
			ps.setInt(1, userid);
			rs = ps.executeQuery();       
			List<ProductType> list = new ArrayList<ProductType>(); 
			while (rs.next()) {
				int ptypeid = rs.getInt(1);
				if (ptypeid == stopid && !containStop) {
				} else {
					ProductType depc = getKnowledgeLibTree(ptypeid, stopid, containStop,1);
					depc.setParent(ptype);
					list.add(depc); 
				}  
			}  
			ptype.setChild(list);  	 
		} catch (Exception e) {
			logger.error("查看资源库出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ptype;
	}
	
	private ProductType getKnowledgeLibTree(int from, int stop, boolean containStop, int level) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		ProductType pro = null;
		try {
			pro = getPtypeLibById(from);
			pro.setLevel(level);
			ct = DBConnection.getConnection();
			pro.setChild(getPtChilds(ct,pro.getId(), stop, containStop, level)); 
		} catch (Exception e) {
			logger.error("资源库树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return pro;
	}

	public void addOpusers(String type, int userid, int ptypeid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into " + type
					+ "(userid,ptypeid) values(?,?)");
			ps.setInt(1, userid);
			ps.setInt(2, ptypeid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加权限出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public boolean checkOpUsers(String type, int userid, int ptypeid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from " + type
					+ " where userid = ? and ptypeid = ?");
			ps.setInt(1, userid);
			ps.setInt(2, ptypeid);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("查询权限出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public void change_tuijian(int change_id, String select_tuijian,String table)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			if(table.equals("pfmsuser")){
				ps = ct.prepareStatement("update pfmsuser set tuijian=? where userid=?");
			}else if(table.equals("product_baoxian_pg")){
				ps = ct.prepareStatement("update product_baoxian_pg set zhengzhantuijian=? where id=?");
			}else if(table.equals("product")){
				ps = ct.prepareStatement("update product set zhengzhantuijian=? where id=?");
			}
			
			ps.setString(1, select_tuijian);
			ps.setInt(2, change_id);
			rs = ps.executeQuery();
		} catch (Exception e) {
			logger.error("修改整站推荐状态出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public List<BaoxianProduct> getSixFrontBaoxianProductList(int pageNow, int pageSize) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<BaoxianProduct> baoxianProductList = new ArrayList<BaoxianProduct>();
		String sql = "";
		String jianjie = "";
		try {
				
			ct = DBConnection.getConnection();
			
			sql = "select * from (select t.*,rownum rn from (select p.*,pl.lanmu from product_baoxian_pg p " +
					" join baoxian_product_lanmu pl on p.suoshulanmu = pl.id " +
			" where p.shenhezhuangtai=2 and p.zhengzhantuijian='推荐' "+
			" order by p.fabushijian desc) t where rownum <=?) where rn>=?";
			
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
				
			rs = ps.executeQuery();
			while (rs.next()) {
				BaoxianProduct baoxianProduct = new BaoxianProduct();
				baoxianProduct.setId(rs.getInt("id"));
				baoxianProduct.setName(rs.getString("name"));
				baoxianProduct.setFabushijian(rs.getTimestamp("fabushijian"));
				baoxianProduct.setChanpintupian(rs.getString("chanpintupian"));
				baoxianProduct.setShichangjia(rs.getDouble("shichangjia"));
				baoxianProduct.setHuiyuanjia(rs.getDouble("huiyuanjia"));
				baoxianProduct.setLogo(rs.getString("logo"));
				jianjie = new OracleBlob().getContent(rs.getBlob("jianjie"));
				jianjie = CheckHtml.getString(jianjie);
				baoxianProduct.setJianjie((jianjie.length() > 200) ? jianjie.substring(0, 198)+ "..." : jianjie);
//				baoxianProduct.setJianjie(new OracleBlob().getContent(rs.getBlob("jianjie")));
				baoxianProduct.setChanpinliangdian(rs.getString("chanpinliangdian"));
				baoxianProduct.setFuwurexian(rs.getString("fuwurexian"));
				Suoshulanmu lanmu = new Suoshulanmu();
				lanmu.setLanmu(rs.getString("lanmu"));
				baoxianProduct.setLanmu(lanmu);
				baoxianProductList.add(baoxianProduct);
			}
		} catch (Exception e) {
			logger.error("我的保险产品列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return baoxianProductList;
	}
	
	public List<BaoxianProduct> getFrontBaoxianProductList(BaoxianProduct baoxianProduct,int pageNow, int pageSize) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<BaoxianProduct> baoxianProductList = new ArrayList<BaoxianProduct>();
		String sql = "";
		String sqlAppend = "";
		String jianjie = "";
		try {
				
			ct = DBConnection.getConnection();
			if(baoxianProduct != null){
				if(baoxianProduct.getName() != null && !baoxianProduct.getName().equals(""))
					sqlAppend = sqlAppend + " and p.name like '%"+baoxianProduct.getName()+"%' ";
			}
			
			sql = "select * from (select t.*,rownum rn from (select p.*,pl.lanmu from product_baoxian_pg p " +
					" join baoxian_product_lanmu pl on p.suoshulanmu = pl.id " +
					" inner join ("+((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).generateSQLByTree("baoxian_product_lanmu", baoxianProduct.getPtype(), true)+") clt on p.suoshulanmu = clt.id "+
			" where p.shenhezhuangtai=2  "+sqlAppend+
			" order by p.fabushijian desc) t where rownum <=?) where rn>=?";
			
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
				
			rs = ps.executeQuery();
			while (rs.next()) {
				baoxianProduct = new BaoxianProduct();
				baoxianProduct.setId(rs.getInt("id"));
				baoxianProduct.setName(rs.getString("name"));
				baoxianProduct.setFabushijian(rs.getTimestamp("fabushijian"));
				baoxianProduct.setChanpintupian(rs.getString("chanpintupian"));
				baoxianProduct.setShichangjia(rs.getDouble("shichangjia"));
				baoxianProduct.setHuiyuanjia(rs.getDouble("huiyuanjia"));
				baoxianProduct.setLogo(rs.getString("logo"));
				jianjie = new OracleBlob().getContent(rs.getBlob("jianjie"));
				jianjie = CheckHtml.getString(jianjie);
				baoxianProduct.setJianjie((jianjie.length() > 200) ? jianjie.substring(0, 198)+ "..." : jianjie);
//				baoxianProduct.setJianjie(new OracleBlob().getContent(rs.getBlob("jianjie")));
				baoxianProduct.setChanpinliangdian(rs.getString("chanpinliangdian"));
				baoxianProduct.setFuwurexian(rs.getString("fuwurexian"));
				Suoshulanmu lanmu = new Suoshulanmu();
				lanmu.setLanmu(rs.getString("lanmu"));
				baoxianProduct.setLanmu(lanmu);
				baoxianProductList.add(baoxianProduct);
			}
		} catch (Exception e) {
			logger.error("我的保险产品列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return baoxianProductList;
	}

	public int getFrontBaoxianProductCount() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int size = 0;
		try {
				
			ct = DBConnection.getConnection();
			
			sql = "select count(1) from product_baoxian_pg p where p.shenhezhuangtai=2 and p.zhengzhantuijian='推荐' order by p.fabushijian desc";
			
			ps = ct.prepareStatement(sql);
				
			rs = ps.executeQuery();
			if(rs.next())
				size = rs.getInt(1);
		} catch (Exception e) {
			logger.error("我的保险产品列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}
	
	public int getChushenCount() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int size = 0;
		try {
				
			ct = DBConnection.getConnection();
			
			sql = "select count(p.id) from policy p left join" +
			"  eluser eu on p.createid = eu.id left join  baoxian_product_lanmu bpl on p.libid = bpl.id left join " +
			" product_baoxian_pg pb  on p.commodityid = pb.id left join pfmsuser pf on p.createid = pf.userid " ;
			
			ps = ct.prepareStatement(sql);
				
			rs = ps.executeQuery();
			if(rs.next())
				size = rs.getInt(1);
		} catch (Exception e) {
			logger.error("我的保险产品列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}
	
	public int getZhongshenCount() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int size = 0;
		try {
				
			ct = DBConnection.getConnection();
			
			sql = "select count(p.id) from policy p left join" +
			"  eluser eu on p.createid = eu.id left join  baoxian_product_lanmu bpl on p.libid = bpl.id left join " +
			" product_baoxian_pg pb  on p.commodityid = pb.id left join pfmsuser pf on p.createid = pf.userid " ;
			
			ps = ct.prepareStatement(sql);
				
			rs = ps.executeQuery();
			if(rs.next())
				size = rs.getInt(1);
		} catch (Exception e) {
			logger.error("我的保险产品列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}


	public int getFrontbaoxianProductListSize(BaoxianProduct baoxianProduct,int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int size = 0;
		try {
				
			ct = DBConnection.getConnection();
			
			sql = "select count(1) from product_baoxian_pg p " +
					" inner join ("+((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).generateSQLByTree("baoxian_product_lanmu", baoxianProduct.getPtype(), true)+") clt on p.suoshulanmu = clt.id "+
					" where p.shenhezhuangtai=2 " ;
			
			ps = ct.prepareStatement(sql);
				
			rs = ps.executeQuery();
			if(rs.next())
				size = rs.getInt(1);
		} catch (Exception e) {
			logger.error("我的保险产品列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

}
