package com.sopia.pfms.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.common.CheckHtml;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.ElQuerySql;
import com.sopia.common.OracleBlob;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.CourseConstants;
import com.sopia.courseman.entities.EroomLib;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.Typelrid;
import com.sopia.newsandmess.NmConstants;
import com.sopia.newsandmess.entities.News;
import com.sopia.newsandmess.entities.NewsType;
import com.sopia.pfms.dao.ProductDao;
import com.sopia.pfms.entities.PfmsUser;
import com.sopia.pfms.entities.Product;
import com.sopia.pfms.entities.ProductType;
import com.sopia.pfms.entities.Shenhezhuangtai;
import com.sopia.pfms.entities.Suoshulanmu;

public class ProductDaoImpl implements ProductDao{
	private static final Log logger = LogFactory.getLog(ProductDaoImpl.class);


	public void deleteChanpin(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(
					"delete from product where id=?"
					);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除产品失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public Product showChanpin(int id,int showType) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Product product = new Product();
		String jianjie = "" ;
		try {
			ct = DBConnection.getConnection();
			String sql = "select p.*,pl.lanmu,pl.id as plid,ps.shenhezhuangtai as psshenhezhuangtai,e.username,pu.dianpuname,sysdate from product p " +
					"join product_lanmu pl on p.suoshulanmu=pl.id " +
					"join eluser e on p.userid=e.id " + 
					" join pfmsuser pu on p.userid=pu.userid " + 
					"join product_shenhezhuangtai ps on p.shenhezhuangtai=ps.id " +
					"where p.id=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				Suoshulanmu lanmu = new Suoshulanmu();
				Shenhezhuangtai shzt = new Shenhezhuangtai();
				
				PfmsUser pfmsUser = new PfmsUser();
				pfmsUser.setDianpuName(rs.getString("dianpuname"));
				
				
				lanmu.setLanmu(rs.getString("lanmu"));
				shzt.setShenhezhuangtai(rs.getString("psshenhezhuangtai"));
				product.setId(rs.getInt("id"));
				product.setUserId(rs.getInt("userid"));
				product.setName(rs.getString("name"));
				product.setJieshao(rs.getString("jieshao"));
				product.setSuoshulanmu(rs.getInt("plid"));
				product.setKey(rs.getString("key"));
				product.setChanpinbianhao(rs.getString("chanpinbianhao"));
//				product.setProductCompanyName(rs.getString("productcompanyname"));
				product.setShichangjia(rs.getDouble("shichangjia"));
				product.setHuiyuanjia(rs.getDouble("huiyuanjia"));
				product.setFabuzhe(rs.getString("username"));
				product.setFabushijian(rs.getTimestamp("fabushijian"));
				product.setXiugaishijian(rs.getTimestamp("xiugaishijian"));
				product.setXiugaizhe(rs.getString("xiugaizhe"));
				product.setChanpintupian(rs.getString("chanpintupian"));
				product.setShuliang(rs.getInt("shuliang"));
				product.setBaojingshu(rs.getInt("baojingshu"));
				product.setDianjishubenyue(rs.getInt("dianjishubenyue"));
				product.setDianjishubenzhou(rs.getInt("dianjishubenzhou"));
				product.setDianjishujinri(rs.getInt("dianjishujinri"));
				product.setDianjishuzongji(rs.getInt("dianjishuzongji"));
				product.setShangpinxinghao(rs.getString("shangpinxinghao"));
				product.setShangpinguige(rs.getString("shangpinguige"));
				product.setShengchanshang(rs.getString("shengchanshang"));
				product.setShangpinshangbiao(rs.getString("shangpinshangbiao"));
				product.setShenhezhuangtai(rs.getInt("shenhezhuangtai"));
				
				if(showType == 1){
					product.setJianjie(new OracleBlob().getContent(rs.getBlob("jianjie")));
				}else{
					jianjie = new OracleBlob().getContent(rs.getBlob("jianjie"));
					jianjie = CheckHtml.getString(jianjie);
					product.setJianjie((jianjie.length() > 200) ? jianjie.substring(0, 198)+ "......" : jianjie);
				}
				
				product.setLanmu(lanmu);
				product.setShenhezhuangtai_entity(shzt);
				product.setPfmsUser(pfmsUser);
				return product;
			}
		} catch (Exception e) {
			logger.error("显示产品失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return product;
	}

	public void updateChanpin(int roleId,Product product,int shenhezhuangtai,boolean is_product_fabu_can_alter) throws ElException {
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
				"update product set " +
					"name = ?,jieshao=?,suoshulanmu=?,key=?,chanpinbianhao=?," +
					"shichangjia=?,huiyuanjia=?,fabuzhe=?," +
					"shuliang=?,baojingshu=?,dianjishujinri=?,dianjishubenzhou=?," +
					"dianjishubenyue=?,dianjishuzongji=?,shangpinxinghao=?,shangpinguige=?,shengchanshang=?," +
					"shangpinshangbiao=?,chanpintupian=? " + sqlAppend +
					" where id=?";
			
			ps = ct.prepareStatement(sql);
			ps.setString(1, product.getName());
			ps.setString(2, product.getJieshao());
			ps.setInt(3, product.getSuoshulanmu());
			ps.setString(4, product.getKey());
			ps.setString(5, product.getChanpinbianhao());
			
			ps.setDouble(6, product.getShichangjia());
			ps.setDouble(7, product.getHuiyuanjia());
			ps.setString(8, product.getFabuzhe());
			
			ps.setInt(9, product.getShuliang());
			ps.setInt(10, product.getBaojingshu());
			ps.setInt(11, product.getDianjishujinri());
			ps.setInt(12, product.getDianjishubenzhou());
			
			ps.setInt(13, product.getDianjishubenyue());
			ps.setInt(14, product.getDianjishuzongji());
			ps.setString(15, product.getShangpinxinghao());
			ps.setString(16, product.getShangpinguige());
			ps.setString(17, product.getShengchanshang());
			
			ps.setString(18, product.getShangpinshangbiao());
			ps.setString(19, product.getChanpintupian());
			
			if(is_product_fabu_can_alter == true){
				ps.setInt(20,1);//设置为1，修改后将其他状态变更为已创建状态
				ps.setInt(21, product.getId());
			}else{
				ps.setInt(20, product.getId());
			}
			
			ps.executeUpdate();
			
			
			ps = ct.prepareStatement("update product SET jianjie = empty_blob() WHERE id = ?"); 
			ps.setInt(1, product.getId());
			ps.executeUpdate();
			OracleBlob setblob = new OracleBlob("product","id",product.getId()+"","jianjie",product.getJianjie(),"修改计划失败",ct);
			setblob.updateContent();
		} catch (Exception e) {
			logger.error("修改计划失败！", e);
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
			String sql = "select e.role from product p join eluser e on p.userid=e.id where e.id=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				roleId = rs.getInt("role");
			}
		} catch (Exception e) {
			logger.error("显示产品失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return roleId;
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


	public List<Suoshulanmu> suoshulanmuList() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		List<Suoshulanmu> suoshulanmuList = new ArrayList<Suoshulanmu>();
		try {
			ct = DBConnection.getConnection();
			sql = "select * from product_lanmu";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Suoshulanmu lanmu = new Suoshulanmu();
				lanmu.setId(rs.getInt("id"));
				lanmu.setLanmu(rs.getString("lanmu"));
				suoshulanmuList.add(lanmu);
			}
		} catch (Exception e) {
			logger.error("产品所属栏目查询失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return suoshulanmuList;
	}

	public void addProduct(boolean is_product_sh,Product product,ELUser elUser,int ptype_parent_id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "insert into product (id,name,jieshao,suoshulanmu,key," +
			"shichangjia,huiyuanjia,fabuzhesuozaidanwei," +
			"shuliang,baojingshu,shangpinxinghao,shangpinguige,shengchanshang," +
			"shangpinshangbiao,fabushijian,userid,jianjie,shenhezhuangtai,fabuzhe," +
			"dianneituijian,chanpintupian,zhengzhantuijian)" +
			" values (product_sequence.nextval,?,?,?,?," +
			"?,?,?," +
			"?,?,?,?,?," +
			"?,sysdate,?,empty_blob(),?,?,?,?,?)";
			ps = ct.prepareStatement(sql);
			ps.setString(1, product.getName());
			ps.setString(2, product.getJieshao());
			ps.setInt(3, ptype_parent_id);
			ps.setString(4, product.getKey());
			
			ps.setDouble(5, product.getShichangjia());
			ps.setDouble(6, product.getHuiyuanjia());
			ps.setString(7, product.getFabuzhesuozaidanwei());
			
			ps.setInt(8, product.getShuliang());
			ps.setInt(9, product.getBaojingshu());
			ps.setString(10, product.getShangpinxinghao());
			ps.setString(11, product.getShangpinguige());
			ps.setString(12, product.getShengchanshang());
			
			ps.setString(13, product.getShangpinshangbiao());
			ps.setInt(14, elUser.getId());
			if(is_product_sh){//需要审核
				ps.setInt(15, 1);	//1为已创建状态
			}else{//不需要审核
				ps.setInt(15, 2);	//2为审核通过状态
			}
			ps.setString(16,elUser.getUsername());
			ps.setString(17,product.getDianneituijian());
			ps.setString(18, product.getChanpintupian());
			ps.setString(19, "普通");
			ps.executeUpdate();

			OracleBlob setblob = new OracleBlob(ct,"product_sequence","product","id","jianjie",product.getJianjie(),"添加产品失败");
			setblob.addContent(); 
		} catch (Exception e) {
			logger.error("添加产品失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public void updateShenhezhuangtai(int roleId,int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
//			if(roleId == 1){
				sql = 
					"update product set " +
						"shenhezhuangtai = ?" + 
						" where id=?";
//			}
			
			ps = ct.prepareStatement(sql);
			ps.setInt(1, 2);
			ps.setInt(2, id);
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("修改审核状态失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	
	public void updateShenhezhuangtaiNotPass(int roleId, int id)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
//			if(roleId == 1){
				sql = 
					"update product set " +
						"shenhezhuangtai = ?" + 
						" where id=?";
//			}
			
			ps = ct.prepareStatement(sql);
			ps.setInt(1, 3);
			ps.setInt(2, id);
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("修改审核状态失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
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
				ptype = getPtypeByid(from);
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
	
	private List<ProductType> getPtChilds(Connection ct, int from, int stop,
			boolean containStop, int level) throws Exception {
		List<ProductType> deps = new ArrayList<ProductType>();
		String sql = "select * from product_lanmu where parentid=?";
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
			ps = ct.prepareStatement("select * from product_lanmu where parentid=?");
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
	
	public ProductType getPtypeByid(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ProductType pt = new ProductType();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from product_lanmu where id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				pt.setId(rs.getInt("id"));
				pt.setName(rs.getString("lanmu"));
				pt.setDescription(rs.getString("description"));
				ps = ct.prepareStatement("select id as pid,lanmu as planmu from product_lanmu where id=?");
				ps.setInt(1, rs.getInt("parentid"));
				rs = ps.executeQuery();
				if (rs.next()){
					pt.setParent(new ProductType(rs.getInt("pid"), rs.getString("planmu")));
				}
//				pt.setIsshared(rs.getInt(6));
			}
		} catch (Exception e) {
			logger.error("获取栏目失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return pt;
	}
	
	public void updateAllProductShenhezhuangtai() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "update product set shenhezhuangtai = 2" ;
			ps = ct.prepareStatement(sql);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("修改审核状态失败！", e);
			try {
				throw new ElException(e);
			} catch (ElException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				DBConnection.closeConnectInfo(ct, ps, rs);
			} catch (ElException e) {
				e.printStackTrace();
			}
		}
		
	}

	
	public List<Product> getProductByUidByPerOrShar(String type,ElNode productLibTree,int sublibs,String userid, int nid,
			ProductType ptypeTree,Integer status, int pageNow, int pageSize,Product product,Date starttime,Date endtime) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		Connection ct1 = null;
		List<Product> productList = new ArrayList<Product>();
		String sql = "";
		String sql1 = "";
		String sqlAppend = "";
		String[] keys ;
		String jianjie = "";
		try {
			//判断是前台还是后台产品查询
			if(type != null  && type.equals("front")){
				sqlAppend = sqlAppend + " and p.shenhezhuangtai = 2 ";
			}
			
//			if(is_product_sh){
//				sqlAppend = sqlAppend + " and p.shenhezhuangtai = 2 " ;//需要审核
//			}else{
//				this.updateAllProductShenhezhuangtai();//不需要审核下,更新所有产品的审核状态为审核通过
//			}
			
			if(userid != null  && !userid.equals("")){
				sqlAppend = sqlAppend + " and userid="+userid+" ";
			}
			
			if(product != null){
				if(product.getShenhezhuangtai() != 0)
						sqlAppend = sqlAppend + " and p.shenhezhuangtai = '" + product.getShenhezhuangtai() + "'";
				if(product.getName() != null)
					if(!product.getName().equals(""))
						sqlAppend = sqlAppend + " and name like '%" + product.getName() + "%'";
				if(product.getShengchanshang() != null)
					if(!product.getShengchanshang().equals(""))
						sqlAppend = sqlAppend + " and shengchanshang like '%" + product.getShengchanshang() + "%'";
				if(product.getZhengzhantuijian() != null)
					if(!product.getZhengzhantuijian().equals(""))
						sqlAppend = sqlAppend + " and zhengzhantuijian like '%" + product.getZhengzhantuijian() + "%'";
				if(product.getKey() != null)
					if(!product.getKey().equals("")){
						sqlAppend = sqlAppend + " and ";
						keys = product.getKey().split(" ");
						for(int i=0;i<keys.length;i++){
							if(i == 0){
								if(keys.length==1){
									sqlAppend = sqlAppend + "  (key like '%" + keys[i] + "%') ";
								}else{
									sqlAppend = sqlAppend + "  (key like '%" + keys[i] + "%' ";
								}
							}else if(i == keys.length - 1){
								sqlAppend = sqlAppend + " or key like '%" + keys[i] + "%') ";
							}else{
								sqlAppend = sqlAppend + " or key like '%" + keys[i] + "%' ";
							}
							
						}
					}
			}
			if(starttime != null)
				sqlAppend  = sqlAppend + " and to_char(fabushijian,'yyyy-MM-dd HH:mm:ss') > '" + starttime+"'";
			if(endtime != null)
				sqlAppend  = sqlAppend + " and to_char(fabushijian,'yyyy-MM-dd HH:mm:ss') < '" + endtime+"'";
			
				
			ct = DBConnection.getConnection();
			
			boolean consub = sublibs == 1 ? true : false;
			sql = "select * from (select t.*,rownum rn from (select p.id,p.userid,p.chanpintupian,p.name,p.jianjie,p.productcompanyname,p.shenhezhuangtai,p.shichangjia,p.huiyuanjia," +
					"p.fabushijian,p.shengchanshang,p.suoshulanmu ,pl.lanmu,elu.realname,ps.shenhezhuangtai as psshenhezhuangtai,pfu.province_city_county,elu.depid,elu.id as eluid  from product p " +
					"inner join product_lanmu pl on p.suoshulanmu = pl.id " +
					"inner join eluser elu on p.userid=elu.id " +
					"inner join product_shenhezhuangtai ps on p.shenhezhuangtai=ps.id " +
					" inner join pfmsuser pfu on p.userid=pfu.userid " +
					"inner join ("+((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).generateSQLByTree("product_lanmu", productLibTree, consub)+") clt on p.suoshulanmu = clt.id"+
					" where 1=1 " +sqlAppend+
					" order by p.fabushijian desc) t where rownum <=?) where rn>=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
				
			rs = ps.executeQuery();
			while (rs.next()) {
				product = new Product();
				product.setId(rs.getInt("id"));
				product.setUserId(rs.getInt("userid"));
				product.setName(rs.getString("name"));
				product.setShichangjia(rs.getDouble("shichangjia"));
				product.setHuiyuanjia(rs.getDouble("huiyuanjia"));
				product.setFabushijian(rs.getTimestamp("fabushijian"));
				product.setShengchanshang(rs.getString("shengchanshang"));
				product.setShenhezhuangtai(rs.getInt("shenhezhuangtai"));
				product.setProductCompanyName(rs.getString("productcompanyname"));
				product.setChanpintupian(rs.getString("chanpintupian"));
				
				if(type != null  && type.equals("front")){
					jianjie = new OracleBlob().getContent(rs.getBlob("jianjie"));
					jianjie = CheckHtml.getString(jianjie);
					product.setJianjie((jianjie.length() > 200) ? jianjie.substring(0, 198)+ "..." : jianjie);
				}else{
					product.setJianjie(new OracleBlob().getContent(rs.getBlob("jianjie")));
				}
				
				product.setPtype(new ProductType(rs.getInt("id"),rs.getString("lanmu")));
				ELUser user= new ELUser();
				user.setRealname(rs.getString("realname"));
				PfmsUser pfmsUser = new PfmsUser();
				
//				ct1 = DBConnection.getConnection();
//				//rs.getString("depid")=>
//				sql1 = "select parentid from department d where id=" + rs.getInt("depid");
//				ps1 = ct1.prepareStatement(sql1);
//				rs1 = ps1.executeQuery();
//				while(rs1.next()){
//					
//				}
				pfmsUser.setProvince_city_county(rs.getString("province_city_county"));
				user.setPfmsUser(pfmsUser);
				product.setPfmsUser(pfmsUser);
				//news.setContent(new OracleBlob().getContent(rs.getBlob(9)));
				Suoshulanmu lanmu = new Suoshulanmu();
				lanmu.setLanmu(rs.getString("lanmu"));
				product.setLanmu(lanmu);
				Shenhezhuangtai shzt = new Shenhezhuangtai();
				shzt.setShenhezhuangtai(rs.getString("psshenhezhuangtai"));
				product.setShenhezhuangtai_entity(shzt);
				productList.add(product);
			}
		} catch (Exception e) {
			logger.error("我的产品列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return productList;
	}
	
	public int getProductCountByUidByPerOrShar(String type,ElNode productLibTree,int sublibs,String userid, int nid,
			ProductType ptypeTree,Integer status,int pageNow, int pageSize,Product product,Date starttime,Date endtime) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		String sql = "";
		String sqlAppend = "";
		String[] keys ;
		try {
			//判断是前台还是后台产品查询
			if(type != null  && type.equals("front")){
				sqlAppend = sqlAppend + " and p.shenhezhuangtai = 2 ";
			}
//			if(is_product_sh)
//				sqlAppend = sqlAppend + " and p.shenhezhuangtai = 2 " ;
			if(userid != null && !userid.equals("")){
				sqlAppend = sqlAppend + " and userid =  "+ userid + " ";
			}
			
			if(product != null){
				if(product.getShenhezhuangtai() != 0)
					sqlAppend = sqlAppend + " and p.shenhezhuangtai = '" + product.getShenhezhuangtai() + "'";
				if(product.getName() != null)
					if(!product.getName().equals(""))
						sqlAppend = sqlAppend + " and name like '%" + product.getName() + "%'";
				if(product.getShengchanshang() != null)
					if(!product.getShengchanshang().equals(""))
						sqlAppend = sqlAppend + " and shengchanshang like '%" + product.getShengchanshang() + "%'";
				if(product.getZhengzhantuijian() != null)
					if(!product.getZhengzhantuijian().equals(""))
						sqlAppend = sqlAppend + " and zhengzhantuijian like '%" + product.getZhengzhantuijian() + "%'";
				if(product.getKey() != null)
					if(!product.getKey().equals("")){
						sqlAppend = sqlAppend + " and ";
						keys = product.getKey().split(" ");
						for(int i=0;i<keys.length;i++){
							if(i == 0){
								if(keys.length==1){
									sqlAppend = sqlAppend + "  (key like '%" + keys[i] + "%') ";
								}else{
									sqlAppend = sqlAppend + "  (key like '%" + keys[i] + "%' ";
								}
							}else if(i == keys.length - 1){
								sqlAppend = sqlAppend + " or key like '%" + keys[i] + "%') ";
							}else{
								sqlAppend = sqlAppend + " or key like '%" + keys[i] + "%' ";
							}
							
						}
					}
			}
			if(starttime != null)
				sqlAppend  = sqlAppend + " and to_char(fabushijian,'yyyy-MM-dd HH:mm:ss') > '" + starttime+"'";
			if(endtime != null)
				sqlAppend  = sqlAppend + " and to_char(fabushijian,'yyyy-MM-dd HH:mm:ss') < '" + endtime+"'";
			ct = DBConnection.getConnection();
			boolean consub = sublibs == 1 ? true : false;
			sql = "select count(*) from product p " +
			" inner join product_lanmu pl on p.suoshulanmu = pl.id " +
			" inner join eluser elu on p.userid=elu.id " +
			" inner join product_shenhezhuangtai ps on p.shenhezhuangtai=ps.id " +
			" inner join pfmsuser pfu on p.userid=pfu.userid " +
			"inner join ("+((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).generateSQLByTree("product_lanmu", productLibTree, consub)+") clt on p.suoshulanmu = clt.id "+
			" where 1=1 " + sqlAppend;
			ps = ct.prepareStatement(sql);
			
			rs = ps.executeQuery();
			if(rs.next())
				size = rs.getInt(1);
		} catch (Exception e) {
			logger.error("我的产品列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}
	
	public List<Product> getFrontProductList( int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Product> productList = new ArrayList<Product>();
		String sql = "";
		try {
				
			ct = DBConnection.getConnection();
			
			sql = "select * from (select t.*,rownum rn from (select p.* from product p " +
					" where p.shenhezhuangtai=2 and p.zhengzhantuijian='推荐' "+
					" order by p.fabushijian desc) t where rownum <=?) where rn>=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
				
			rs = ps.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt("id"));
				product.setUserId(rs.getInt("userid"));
				product.setName(rs.getString("name"));
				product.setChanpintupian(rs.getString("chanpintupian"));
//				product.setShichangjia(rs.getDouble("shichangjia"));
//				product.setHuiyuanjia(rs.getDouble("huiyuanjia"));
//				product.setFabushijian(rs.getTimestamp("fabushijian"));
//				product.setShengchanshang(rs.getString("shengchanshang"));
//				product.setShenhezhuangtai(rs.getInt("shenhezhuangtai"));
//				product.setProductCompanyName(rs.getString("productcompanyname"));
//				product.setChanpintupian(rs.getString("chanpintupian"));
//				
//				product.setPtype(new ProductType(rs.getInt("id"),rs.getString("lanmu")));
//				ELUser user= new ELUser();
//				user.setRealname(rs.getString("realname"));
//				PfmsUser pfmsUser = new PfmsUser();
//				pfmsUser.setProvince_city_county(rs.getString("province_city_county"));
//				user.setPfmsUser(pfmsUser);
//				product.setPfmsUser(pfmsUser);
//				//news.setContent(new OracleBlob().getContent(rs.getBlob(9)));
//				Suoshulanmu lanmu = new Suoshulanmu();
//				lanmu.setLanmu(rs.getString("lanmu"));
//				product.setLanmu(lanmu);
//				Shenhezhuangtai shzt = new Shenhezhuangtai();
//				shzt.setShenhezhuangtai(rs.getString("psshenhezhuangtai"));
//				product.setShenhezhuangtai_entity(shzt);
				productList.add(product);
			}
		} catch (Exception e) {
			logger.error("我的产品列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return productList;
	}

	public int getAllProductCountByUidByPerOrShar(ElNode productLibTree,int sublibs, int nid,
			ProductType ptypeTree, Integer status, int pageNow, int pageSize,
			Product product, Date starttime, Date endtime) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		String sql = "";
		String sqlAppend = "";
		String[] keys ;
		try {
			if(product != null){
				if(product.getShenhezhuangtai() != 0)
					sqlAppend = sqlAppend + " and p.shenhezhuangtai = '" + product.getShenhezhuangtai() + "'";
				if(product.getName() != null)
					if(!product.getName().equals(""))
						sqlAppend = sqlAppend + " and p.name like '%" + product.getName() + "%'";
				if(product.getShengchanshang() != null)
					if(!product.getShengchanshang().equals(""))
						sqlAppend = sqlAppend + " and p.shengchanshang like '%" + product.getShengchanshang() + "%'";
				if(product.getZhengzhantuijian() != null)
					if(!product.getZhengzhantuijian().equals(""))
						sqlAppend = sqlAppend + " and zhengzhantuijian like '%" + product.getZhengzhantuijian() + "%'";
				if(product.getKey() != null)
					if(!product.getKey().equals("")){
						sqlAppend = sqlAppend + " and ";
						keys = product.getKey().split(" ");
						for(int i=0;i<keys.length;i++){
							if(i == 0){
								if(keys.length==1){
									sqlAppend = sqlAppend + "  (key like '%" + keys[i] + "%') ";
								}else{
									sqlAppend = sqlAppend + "  (key like '%" + keys[i] + "%' ";
								}
							}else if(i == keys.length - 1){
								sqlAppend = sqlAppend + " or key like '%" + keys[i] + "%') ";
							}else{
								sqlAppend = sqlAppend + " or key like '%" + keys[i] + "%' ";
							}
							
						}
					}
				if(product.getPfmsUser() != null)
					if(product.getPfmsUser().getUser() != null)
						if(product.getPfmsUser().getUser().getUsername() != null){
							if(!product.getPfmsUser().getUser().getUsername().equals(""))
								sqlAppend = sqlAppend + "  and elu.username like '%" + product.getPfmsUser().getUser().getUsername() +"%' ";
							if(!product.getPfmsUser().getUser().getRealname().equals(""))
								sqlAppend = sqlAppend + "  and elu.realname like '%" + product.getPfmsUser().getUser().getRealname() +"%' ";
						}
			}
			if(starttime != null)
				sqlAppend  = sqlAppend + " and to_char(fabushijian,'yyyy-MM-dd HH:mm:ss') > '" + starttime+"'";
			if(endtime != null)
				sqlAppend  = sqlAppend + " and to_char(fabushijian,'yyyy-MM-dd HH:mm:ss') < '" + endtime+"'";
			ct = DBConnection.getConnection();
			boolean consub = sublibs == 1 ? true : false;
			sql = "select count(*) from product p " +
			" inner join ("+((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).generateSQLByTree("product_lanmu", productLibTree, consub)+") clt on p.suoshulanmu = clt.id"+
			" join product_shenhezhuangtai ps on p.shenhezhuangtai=ps.id " +
			" inner join eluser elu on p.userid=elu.id " +
			" where 1=1 " + sqlAppend;
			ps = ct.prepareStatement(sql);
			
			rs = ps.executeQuery();
			if(rs.next())
				size = rs.getInt(1);
		} catch (Exception e) {
			logger.error("我的全部产品列表失败", e);
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
	
	public List<ELUser> getOpUsers( int typeid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> us = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select eu.id,eu.realname,eu.username from product_op_type du left join eluser eu on eu.id = du.userid where du.ptypeid = ?");
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

	public void addProducttype(ProductType ptype) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null; 
		String sql = "insert into product_lanmu(id,lanmu,description,parentid,lid,rid) " +
						" values(product_lanmu_sequence.nextval,?,?,?,?,?)";
//		List<ProductType> listType = new ArrayList<ProductType>();
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
//			sql="select id,lanmu,parentid from product_lanmu";
//			ps = ct.prepareStatement(sql);
//			rs = ps.executeQuery();
//			while(rs.next()){
//				ProductType type=new ProductType();
//				type.setId(rs.getInt("id"));
//				type.setName(rs.getString("lanmu"));
//				type.setParent(new ElNode(rs.getInt("parentid")));
////				type.setIsshared(rs.getInt(4));
//				listType.add(type);
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
			sql = "update product_lanmu set lanmu=?,description=?,parentid=? where id=?";
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
			logger.error("所属栏目修改失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public void deleteProductTypeAndSub(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			List<Integer> typelist=this.getTypes(ct,id);
			for (int i = 0; i < typelist.size(); i++) {
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
	
	public List<Integer> getTypes(Connection ct,int id) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Integer>  list=new ArrayList<Integer>();
		try {
			ps = ct.prepareStatement("select id from product_lanmu where id=?");
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
	
	public void deleteProductByTypeid(Connection ct,int typeid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = ct.prepareStatement("delete from product where suoshulanmu=?");
			ps.setInt(1, typeid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("根据所属栏目删除产品出错！", e);
			throw new ElException(e);
		}
	}

	/**
	 * 删除产品所属栏目类别
	 */
	public void deletePtype(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			ProductType ptype = getPtypeByid(id);
			sql = "delete from product_lanmu where id=?";
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


	/**
	 * 更新产品所属栏目的类别id
	 * @param pid	3
	 * @param npid	1
	 * @throws ElException
	 */
	public void updateProductParentid(int pid, int npid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update product set suoshulanmu=? where suoshulanmu=? ");
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

	/**
	 * 更新产品的父节点
	 * @param pid
	 * @param npid
	 * @throws ElException
	 */
	public void updateProductTypeParentid(int pid, int npid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update product_lanmu set parentid=? where parentid=? ");
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
	public ProductType getPtypeLibById(int id) throws ElException {
		ProductType productType = new ProductType();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from product_lanmu where id=?");
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
	
	public List<Product> getAllProductByUidByPerOrShar(ElNode productLibTree,int sublibs, int nid,
			ProductType ptypeTree, Integer status, int pageNow, int pageSize,
			Product product, Date starttime, Date endtime) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Product> productList = new ArrayList<Product>();
		String sql = "";
		String sqlAppend = "";
		String[] keys ;
		try {
			if(product != null){
				if(product.getShenhezhuangtai() != 0)
					sqlAppend = sqlAppend + " and p.shenhezhuangtai = '" + product.getShenhezhuangtai() + "'";
				if(product.getName() != null)
					if(!product.getName().equals(""))
						sqlAppend = sqlAppend + " and p.name like '%" + product.getName() + "%'";
				if(product.getShengchanshang() != null)
					if(!product.getShengchanshang().equals(""))
						sqlAppend = sqlAppend + " and p.shengchanshang like '%" + product.getShengchanshang() + "%'";
				if(product.getZhengzhantuijian() != null)
					if(!product.getZhengzhantuijian().equals(""))
						sqlAppend = sqlAppend + " and zhengzhantuijian like '%" + product.getZhengzhantuijian() + "%'";
				if(product.getKey() != null)
					if(!product.getKey().equals("")){
						sqlAppend = sqlAppend + " and ";
						keys = product.getKey().split(" ");
						for(int i=0;i<keys.length;i++){
							if(i == 0){
								if(keys.length==1){
									sqlAppend = sqlAppend + "  (key like '%" + keys[i] + "%') ";
								}else{
									sqlAppend = sqlAppend + "  (key like '%" + keys[i] + "%' ";
								}
							}else if(i == keys.length - 1){
								sqlAppend = sqlAppend + " or key like '%" + keys[i] + "%') ";
							}else{
								sqlAppend = sqlAppend + " or key like '%" + keys[i] + "%' ";
							}
							
						}
					}
				if(product.getPfmsUser() != null)
					if(product.getPfmsUser().getUser() != null)
						if(product.getPfmsUser().getUser().getUsername() != null){
							if(!product.getPfmsUser().getUser().getUsername().equals(""))
								sqlAppend = sqlAppend + "  and elu.username like '%" + product.getPfmsUser().getUser().getUsername() +"%' ";
							if(!product.getPfmsUser().getUser().getRealname().equals(""))
								sqlAppend = sqlAppend + "  and elu.realname like '%" + product.getPfmsUser().getUser().getRealname() +"%' ";
						}
			}
			if(starttime != null)
				sqlAppend  = sqlAppend + " and to_char(fabushijian,'yyyy-MM-dd HH:mm:ss') > '" + starttime+"'";
			if(endtime != null)
				sqlAppend  = sqlAppend + " and to_char(fabushijian,'yyyy-MM-dd HH:mm:ss') < '" + endtime+"'";
			
				
			ct = DBConnection.getConnection();
//			sql = 
//				"select * from (select t.*,rownum rn from(select p.id,p.name,p.shenhezhuangtai,p.shichangjia,p.huiyuanjia," +
//				"p.fabuzhe,p.fabuzhesuozaidanwei,p.fabushijian,p.shengchanshang,p.suoshulanmu ,pl.lanmu,ps.shenhezhuangtai as psshenhezhuangtai  from product p,product_lanmu pl,product_shenhezhuangtai ps " +
//				"where p.suoshulanmu = pl.id  and p.shenhezhuangtai=ps.id " + sqlAppend + 
//				"and pl.id in ("+createPerTypeId(ptypeTree,nid)+") " +
//						"order by p.fabushijian desc) t where rownum <=?)where rn>=?" ;
			boolean consub = sublibs == 1 ? true : false;
			sql = "select * from (select t.*,rownum rn from (select p.id,p.name,p.fabuzhe,p.fabuzhesuozaidanwei,p.shenhezhuangtai,p.shichangjia,p.huiyuanjia," +
					"p.fabushijian,p.shengchanshang,p.suoshulanmu ,p.zhengzhantuijian,pl.lanmu,elu.username,elu.realname,ps.shenhezhuangtai as psshenhezhuangtai  from product p " +
					"inner join product_lanmu pl on p.suoshulanmu = pl.id " +
					"inner join eluser elu on p.userid=elu.id " +
					"inner join product_shenhezhuangtai ps on p.shenhezhuangtai=ps.id " +
					"inner join ("+((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).generateSQLByTree("product_lanmu", productLibTree, consub)+") clt on p.suoshulanmu = clt.id"+
					" where 1=1 " +sqlAppend+
					" order by p.fabushijian desc) t where rownum <=?) where rn>=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
				
			rs = ps.executeQuery();
			while (rs.next()) {
				product = new Product();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setShichangjia(rs.getDouble("shichangjia"));
				product.setHuiyuanjia(rs.getDouble("huiyuanjia"));
				product.setFabuzhe(rs.getString("fabuzhe"));
				product.setFabuzhesuozaidanwei(rs.getString("fabuzhesuozaidanwei"));
				product.setFabushijian(rs.getTimestamp("fabushijian"));
				product.setShengchanshang(rs.getString("shengchanshang"));
				product.setShenhezhuangtai(rs.getInt("shenhezhuangtai"));
				product.setZhengzhantuijian(rs.getString("zhengzhantuijian"));
				product.setPtype(new ProductType(rs.getInt("id"),rs.getString("lanmu")));
//				ELUser user= new ELUser();
//				user.setRealname(rs.getString("realname"));
				//news.setContent(new OracleBlob().getContent(rs.getBlob(9)));
				Suoshulanmu lanmu = new Suoshulanmu();
				lanmu.setLanmu(rs.getString("lanmu"));
				product.setLanmu(lanmu);
				Shenhezhuangtai shzt = new Shenhezhuangtai();
				shzt.setShenhezhuangtai(rs.getString("psshenhezhuangtai"));
				product.setShenhezhuangtai_entity(shzt);
				productList.add(product);
			}
		} catch (Exception e) {
			logger.error("我的全部产品列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return productList;
	}


}
