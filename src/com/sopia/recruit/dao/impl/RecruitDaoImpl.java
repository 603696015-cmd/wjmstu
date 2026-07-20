package com.sopia.recruit.dao.impl;

import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import oracle.sql.BLOB;
import oracle.sql.CLOB;

import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.OracleBlob;
import com.sopia.duman.entities.ELUser;
import com.sopia.recruit.dao.RecruitDao;
import com.sopia.recruit.entities.Experience;
import com.sopia.recruit.entities.Language;
import com.sopia.recruit.entities.Recruit;

public class RecruitDaoImpl implements RecruitDao{

	public int addRecruit(Recruit recruit,ELUser elUser) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int id = 0;
		try {
			ct = DBConnection.getConnection();
			String sql = "insert into resume(userid,NAME,SEX,SHENGRI,CANJIAGONGZUOSHIJIAN,HUNYINZHUANGKUANG,ZHENGJIANLEIXING,ZHENGJIANHAO," +
					" HAIWAIGONGZUOJINGLI,ZHENGZHIMIANMAO,HUKOU,JUZHUCHENGSHI,DIZHI,YOUBIAN,LIANXIFANGSHI,DIANHUA,YOUXIANG,GERENZHUYE,BIAOTI,NEIRONG," +
					" GONGZUOXINGZHI,GONGZUODIDIAN,CONGSHIHANGYE,CONGSHIZHIYE,YUEXIN,STATUS) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,empty_blob(),?,?,?,?,?,?)";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, elUser.getId());
			ps.setString(2, elUser.getRealname());
			ps.setString(3, elUser.getSex());
			ps.setDate(4, elUser.getShengri());
			ps.setDate(5, elUser.getCanjiagongzuoshijian());
			ps.setString(6, recruit.getHunyinzhuangkuang());
			ps.setString(7, elUser.getZhengjianleixing());
			ps.setString(8, elUser.getShenfenzheng());
			ps.setString(9, recruit.getHaiwaigongzuojingli());
			ps.setString(10, elUser.getZhengzhi());
			ps.setString(11, recruit.getHukou());
			ps.setString(12, recruit.getJuzhuchengshi());
			ps.setString(13, elUser.getAddress());
			ps.setString(14, recruit.getYoubian());
			ps.setString(15, recruit.getLianxifangshi());
			ps.setString(16, elUser.getMovephone());
			ps.setString(17, elUser.getEmail());
			ps.setString(18, recruit.getGerenzhuye());
			ps.setString(19, recruit.getBiaoti());
		//	ps.setString(20, recruit.getNeirong());
			ps.setString(20, recruit.getXingzhi());
			ps.setString(21, recruit.getDidian());
			ps.setString(22, recruit.getHangye());
			ps.setString(23, recruit.getZhiye());
			ps.setString(24, recruit.getYuexin());
			ps.setInt(25, recruit.getStatus());
			ps.executeUpdate();
			OracleBlob setblob = new OracleBlob(ct,"resume_sequence","resume","id","neirong",recruit.getNeirong(),"添加新闻失败");
			setblob.addContent(); 
			ps = ct.prepareStatement("select resume_sequence.currval from dual");
			rs = ps.executeQuery();
			
			if(rs.next()){
				id = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;
	}

	public void addExpenrience(Experience experience) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "insert into experience(userid,RESUMEID,COMPANYNAME,XINGZHI,GUIMO,HANGYELEIBIE,BUMEN,ZHIWEILEIBIE,ZHIYEMINGCHENG,WORKSTARTDATE,WORKENDDATE," +
					" ZHIWEIYUEXIN,MIAOSHU) values(?,?,?,?,?,?,?,?,?,?,?,?,empty_blob())";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, experience.getUserid());
			ps.setInt(2, experience.getResumeid());
			ps.setString(3, experience.getCompanyname());
			ps.setString(4, experience.getXingzhi());
			ps.setString(5, experience.getGuimo());
			ps.setString(6, experience.getHangyeleibie());
			ps.setString(7, experience.getBumen());
			ps.setString(8, experience.getZhiweileibie());
			ps.setString(9, experience.getZhiyemingcheng());
			ps.setDate(10, experience.getWorkstartdate());
			ps.setDate(11, experience.getWorkenddate());
			ps.setString(12, experience.getZhiweiyuexin());
			ps.executeUpdate();
			OracleBlob setblob = new OracleBlob(ct,"experience_sequence","experience","id","miaoshu",experience.getMiaoshu(),"添加新闻失败");
			setblob.addContent(); 
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void addLanguage(Language language) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "insert into language(userid,resumeid,name,read,speak) values(?,?,?,?,?)";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, language.getUserid());
			ps.setInt(2, language.getResumeid());
			ps.setString(3, language.getName());
			ps.setString(4, language.getRead());
			ps.setString(5, language.getSpeak());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public Recruit getRecruitById(int recruitId) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Recruit re = new Recruit();
		ELUser el = new ELUser();
		try {
			ct = DBConnection.getConnection();
			String sql = "select re.id,re.name,re.sex,re.shengri,re.canjiagongzuoshijian,re.hunyinzhuangkuang,re.zhengjianleixing,re.zhengjianhao,re.haiwaigongzuojingli," +
					" re.zhengzhimianmao,re.hukou,re.juzhuchengshi,re.dizhi,re.youbian,re.lianxifangshi,re.dianhua,re.youxiang,re.gerenzhuye,re.biaoti,re.neirong,re.gongzuoxingzhi," +
					" re.gongzuodidian,re.congshihangye,re.congshizhiye,re.yuexin,re.status,re.school,re.startdate,re.enddate,re.xueli " +
					" from resume re where re.id=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, recruitId);
			rs = ps.executeQuery();
			if(rs.next()){
				re.setId(rs.getInt(1));
				el.setRealname(rs.getString(2));
				el.setSex(rs.getString(3));
				el.setShengri(rs.getDate(4));
				el.setCanjiagongzuoshijian(rs.getDate(5));
				re.setHunyinzhuangkuang(rs.getString(6));
				el.setZhengjianleixing(rs.getString(7));
				el.setShenfenzheng(rs.getString(8));
				re.setHaiwaigongzuojingli(rs.getString(9));
				el.setZhengzhi(rs.getString(10));
				re.setHukou(rs.getString(11));
				re.setJuzhuchengshi(rs.getString(12));
				el.setAddress(rs.getString(13));
				re.setYoubian(rs.getString(14));
				re.setLianxifangshi(rs.getString(15));
				el.setMovephone(rs.getString(16));
				el.setEmail(rs.getString(17));
				re.setGerenzhuye(rs.getString(18));
				re.setBiaoti(rs.getString(19));
				re.setNeirong(new OracleBlob().getContent(rs.getBlob(20)));
				re.setXingzhi(rs.getString(21));
				re.setDidian(rs.getString(22));
				re.setHangye(rs.getString(23));
				re.setZhiye(rs.getString(24));
				re.setYuexin(rs.getString(25));
				re.setStatus(rs.getInt(26));
				re.setSchool(rs.getString(27));
				re.setStartdate(rs.getDate(28));
				re.setEnddate(rs.getDate(29));
				re.setXueli(rs.getString(30));
				re.setElUser(el);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return re;
	}

	public void addSchool(Recruit recruit) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "update resume set school=?,startdate=?,enddate=?,xueli=? where id=?";
			ps = ct.prepareStatement(sql);
			ps.setString(1, recruit.getSchool());
			ps.setDate(2, recruit.getStartdate());
			ps.setDate(3, recruit.getEnddate());
			ps.setString(4, recruit.getXueli());
			ps.setInt(5, recruit.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<Language> getLanguageByReid(int recruitid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Language> lans = new ArrayList<Language>();
		try {
			ct = DBConnection.getConnection();
			String sql = "select name,read,speak from language where resumeid=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, recruitid);
			rs = ps.executeQuery();
			while(rs.next()){
				Language la = new Language();
				la.setName(rs.getString(1));
				la.setRead(rs.getString(2));
				la.setSpeak(rs.getString(3));
				lans.add(la);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return lans;
	}

	public List<Experience> getExperienceByReid(int recruitid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Experience> expes = new ArrayList<Experience>();
		try {
			ct = DBConnection.getConnection();
			String sql = "select id,COMPANYNAME,XINGZHI,GUIMO,HANGYELEIBIE,BUMEN,ZHIWEILEIBIE,ZHIYEMINGCHENG,WORKSTARTDATE,WORKENDDATE,ZHIWEIYUEXIN,MIAOSHU from experience where resumeid=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, recruitid);
			rs = ps.executeQuery();
			while(rs.next()){
				Experience exp = new Experience();
				exp.setId(rs.getInt(1));
				exp.setCompanyname(rs.getString(2));
				exp.setXingzhi(rs.getString(3));
				exp.setGuimo(rs.getString(4));
				exp.setHangyeleibie(rs.getString(5));
				exp.setBumen(rs.getString(6));
				exp.setZhiweileibie(rs.getString(7));
				exp.setZhiyemingcheng(rs.getString(8));
				exp.setWorkstartdate(rs.getDate(9));
				exp.setWorkenddate(rs.getDate(10));
				exp.setZhiweiyuexin(rs.getString(11));
				exp.setMiaoshu(rs.getString(12));
				expes.add(exp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return expes;
	}

	public void alterUserInfo(ELUser eluser, Recruit recruit)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "update resume set name=?,sex=?,shengri=?,canjiagongzuoshijian=?,hunyinzhuangkuang=?,zhengjianleixing=?,zhengjianhao=?,haiwaigongzuojingli=?," +
					" zhengzhimianmao=?,hukou=?,juzhuchengshi=?,dizhi=?,youbian=?,lianxifangshi=?,dianhua=?,youxiang=?,gerenzhuye=? where id=?";
			ps = ct.prepareStatement(sql);
			ps.setString(1, eluser.getRealname());
			ps.setString(2, eluser.getSex());
			ps.setDate(3, eluser.getShengri());
			ps.setDate(4, eluser.getCanjiagongzuoshijian());
			ps.setString(5, recruit.getHunyinzhuangkuang());
			ps.setString(6, eluser.getZhengjianleixing());
			ps.setString(7, eluser.getShenfenzheng());
			ps.setString(8, recruit.getHaiwaigongzuojingli());
			ps.setString(9, eluser.getZhengzhi());
			ps.setString(10, recruit.getHukou());
			ps.setString(11, recruit.getJuzhuchengshi());
			ps.setString(12, eluser.getAddress());
			ps.setString(13, recruit.getYoubian());
			ps.setString(14, recruit.getLianxifangshi());
			ps.setString(15, eluser.getMovephone());
			ps.setString(16, eluser.getEmail());
			ps.setString(17, recruit.getGerenzhuye());
			ps.setInt(18, recruit.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void alterWorkInfo(Recruit recruit) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "update resume set gongzuoxingzhi=?,congshizhiye=?,congshihangye=?,gongzuodidian=?,yuexin=?,status=? where id=?";
			ps = ct.prepareStatement(sql);
			ps.setString(1, recruit.getXingzhi());
			ps.setString(2, recruit.getZhiye());
			ps.setString(3, recruit.getHangye());
			ps.setString(4, recruit.getDidian());
			ps.setString(5, recruit.getYuexin());
			ps.setInt(6, recruit.getStatus());
			ps.setInt(7, recruit.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void alterAssessInfo(Recruit recruit) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "update resume set biaoti=?, neirong = empty_blob() where id=?";
			ps = ct.prepareStatement(sql);
			ps.setString(1, recruit.getBiaoti());
			ps.setInt(2, recruit.getId());
			ps.executeUpdate();
			OracleBlob setblob = new OracleBlob("resume","id",recruit.getId()+"","neirong",recruit.getNeirong(),"修改新闻失败",ct);
			setblob.updateContent(); 
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public Experience getExperienceByid(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Experience exp = new Experience();
		try {
			ct = DBConnection.getConnection();
			String sql = "select id,COMPANYNAME,XINGZHI,GUIMO,HANGYELEIBIE,BUMEN,ZHIWEILEIBIE,ZHIYEMINGCHENG,WORKSTARTDATE,WORKENDDATE,ZHIWEIYUEXIN,MIAOSHU from experience where id=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs.next()){
				exp.setId(rs.getInt(1));
				exp.setCompanyname(rs.getString(2));
				exp.setXingzhi(rs.getString(3));
				exp.setGuimo(rs.getString(4));
				exp.setHangyeleibie(rs.getString(5));
				exp.setBumen(rs.getString(6));
				exp.setZhiweileibie(rs.getString(7));
				exp.setZhiyemingcheng(rs.getString(8));
				exp.setWorkstartdate(rs.getDate(9));
				exp.setWorkenddate(rs.getDate(10));
				exp.setZhiweiyuexin(rs.getString(11));
				exp.setMiaoshu(new OracleBlob().getContent(rs.getBlob(12)));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return exp;
	}

	public void alterExperienceByid(Experience exp) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "update experience set COMPANYNAME=?,XINGZHI=?,GUIMO=?,HANGYELEIBIE=?,BUMEN=?,ZHIWEILEIBIE=?,ZHIYEMINGCHENG=?,WORKSTARTDATE=?,WORKENDDATE=?,ZHIWEIYUEXIN=?,miaoshu=empty_blob() where id=?";
			ps = ct.prepareStatement(sql);
			ps.setString(1, exp.getCompanyname());
			ps.setString(2, exp.getXingzhi());
			ps.setString(3, exp.getGuimo());
			ps.setString(4, exp.getHangyeleibie());
			ps.setString(5, exp.getBumen());
			ps.setString(6, exp.getZhiweileibie());
			ps.setString(7, exp.getZhiyemingcheng());
			ps.setDate(8, exp.getWorkstartdate());
			ps.setDate(9, exp.getWorkenddate());
			ps.setString(10, exp.getZhiweiyuexin());
			ps.setInt(11, exp.getId());
			ps.executeUpdate();
			OracleBlob setblob = new OracleBlob("experience","id",exp.getId()+"","miaoshu",exp.getMiaoshu(),"修改新闻失败",ct);
			setblob.updateContent(); 
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void addWorkExp(Experience experience)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "insert into experience(userid,RESUMEID,COMPANYNAME,XINGZHI,GUIMO,HANGYELEIBIE,BUMEN,ZHIWEILEIBIE,ZHIYEMINGCHENG,WORKSTARTDATE,WORKENDDATE," +
			" ZHIWEIYUEXIN,MIAOSHU) values(?,?,?,?,?,?,?,?,?,?,?,?,empty_blob())";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, experience.getUserid());
			ps.setInt(2, experience.getResumeid());
			ps.setString(3, experience.getCompanyname());
			ps.setString(4, experience.getXingzhi());
			ps.setString(5, experience.getGuimo());
			ps.setString(6, experience.getHangyeleibie());
			ps.setString(7, experience.getBumen());
			ps.setString(8, experience.getZhiweileibie());
			ps.setString(9, experience.getZhiyemingcheng());
			ps.setDate(10, experience.getWorkstartdate());
			ps.setDate(11, experience.getWorkenddate());
			ps.setString(12, experience.getZhiweiyuexin());
			ps.executeUpdate();
			OracleBlob setblob = new OracleBlob(ct,"experience_sequence","experience","id","miaoshu",experience.getMiaoshu(),"添加新闻失败");
			setblob.addContent(); 	
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void alterSchool(Recruit recruit) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	

}
