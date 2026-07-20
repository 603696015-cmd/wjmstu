package com.sopia.shebeipinggu.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.CheckHtml;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.OracleBlob;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.entities.ELUser;
import com.sopia.pfms.dao.InsureDao;
import com.sopia.pfms.entities.BaoxianProduct;
import com.sopia.pfms.entities.ProductType;
import com.sopia.pfms.entities.Shenhezhuangtai;
import com.sopia.pfms.entities.Suoshulanmu;

public class PG_InsureDaoImpl implements InsureDao {
	private static final Log logger = LogFactory.getLog(PG_InsureDaoImpl.class);

	public List<BaoxianProduct> getBaoxianProductListByPage(
			int pageNow, int pageSize,BaoxianProduct baoxianProduct,Timestamp starttime,Timestamp endtime) throws ElException {
		List<BaoxianProduct> baoxianProductList = new ArrayList<BaoxianProduct>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlAppend = "";
		String jianjie = "";
		
		try {
			ct = DBConnection.getConnection();
			
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
			}
			if(starttime != null)
				sqlAppend  = sqlAppend + " and to_char(fabushijian,'yyyy-MM-dd HH:mm:ss') > '" + starttime+"'";
			if(endtime != null)
				sqlAppend  = sqlAppend + " and to_char(fabushijian,'yyyy-MM-dd HH:mm:ss') < '" + endtime+"'";
			
			sql = 
				"select * from (select t.*,rownum rn from(select p.id,p.name,p.shenhezhuangtai,p.shichangjia,p.huiyuanjia," +
				"p.fabushijian,p.suoshulanmu ,p.jianjie,p.chanpinliangdian,p.fuwurexian,p.logo,pl.lanmu,elu.realname,ps.shenhezhuangtai as psshenhezhuangtai  " +
				"from product_baoxian p " +
				" join baoxian_product_lanmu pl on p.suoshulanmu = pl.id "+
				" join eluser elu on p.userid=elu.id "+
				" join product_shenhezhuangtai ps on p.shenhezhuangtai=ps.id "+
				" inner join ("+((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).generateSQLByTree("baoxian_product_lanmu", baoxianProduct.getPtype(), true)+") clt on p.suoshulanmu = clt.id "+
				" where p.shenhezhuangtai = 2 " + sqlAppend + 
						" order by p.fabushijian desc) t where rownum <=?)where rn>=?" ;
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
				
			rs = ps.executeQuery();
			while (rs.next()) {
				baoxianProduct = new BaoxianProduct();
				baoxianProduct.setId(rs.getInt("id"));
				baoxianProduct.setName(rs.getString("name"));
				baoxianProduct.setShichangjia(rs.getDouble("shichangjia"));
				baoxianProduct.setHuiyuanjia(rs.getDouble("huiyuanjia"));
				baoxianProduct.setFabushijian(rs.getTimestamp("fabushijian"));
				baoxianProduct.setShenhezhuangtai(rs.getInt("shenhezhuangtai"));
				jianjie = new OracleBlob().getContent(rs.getBlob("jianjie"));
				jianjie = CheckHtml.getString(jianjie);
				baoxianProduct.setJianjie((jianjie.length() > 200) ? jianjie.substring(0, 198)+ "..." : jianjie);
//				baoxianProduct.setJianjie(new OracleBlob().getContent(rs.getBlob("jianjie")));
				baoxianProduct.setChanpinliangdian(rs.getString("chanpinliangdian"));
				baoxianProduct.setFuwurexian(rs.getString("fuwurexian"));
				baoxianProduct.setLogo(rs.getString("logo"));
				baoxianProduct.setPtype(new ProductType(rs.getInt("id"),rs.getString("lanmu")));
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

	public int getBaoxianProductCount(BaoxianProduct baoxianProduct,Timestamp starttime,Timestamp endtime) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlAppend = "";
		int size = 0;
		
		try {
			ct = DBConnection.getConnection();
			
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
			}
			if(starttime != null)
				sqlAppend  = sqlAppend + " and to_char(fabushijian,'yyyy-MM-dd HH:mm:ss') > '" + starttime+"'";
			if(endtime != null)
				sqlAppend  = sqlAppend + " and to_char(fabushijian,'yyyy-MM-dd HH:mm:ss') < '" + endtime+"'";
			
			sql = 
				"select count(*) " + 
				" from product_baoxian p " +
				" join baoxian_product_lanmu pl on p.suoshulanmu = pl.id "+
				" join eluser elu on p.userid=elu.id "+
				" join product_shenhezhuangtai ps on p.shenhezhuangtai=ps.id "+
				" inner join ("+((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).generateSQLByTree("baoxian_product_lanmu", baoxianProduct.getPtype(), true)+") clt on p.suoshulanmu = clt.id "+
				" where p.shenhezhuangtai = 2  " + sqlAppend + 
						" order by p.fabushijian desc" ;
			ps = ct.prepareStatement(sql);
				
			rs = ps.executeQuery();
			if (rs.next()) 
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
