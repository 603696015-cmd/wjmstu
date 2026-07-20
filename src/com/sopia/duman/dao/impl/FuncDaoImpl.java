package com.sopia.duman.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElQuerySql;
import com.sopia.duman.DUConstants;
import com.sopia.duman.dao.FuncDao;
import com.sopia.duman.entities.ElFunc;
import com.sopia.newversion.NewVersionConstants;
import com.sopia.schedule.TagsUtil;
import com.sopia.schedule.dao.impl.ModuleManageDaoImpl;
import com.sopia.schedule.dao.impl.TagsDaoImpl;

public class FuncDaoImpl implements FuncDao {
	private static final Log logger = LogFactory.getLog(FuncDaoImpl.class);

	public HashMap<String, ElFunc> listFuncNavs() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		HashMap<String, ElFunc> hm = new HashMap<String, ElFunc>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id,funccode,name,params,parentid from elfunc order by description asc");
			rs = ps.executeQuery();
			while (rs.next()) {
				ElFunc ef = new ElFunc();
				ef.setId(rs.getInt(1));
				ef.setFunccode(rs.getString(2));
				ef.setName(rs.getString(3));
				ef.setParams(rs.getString(4));
				ef.setParent(new ElFunc(rs.getInt(5)));
				setElfuncParent(ef);
				hm.put(ef.getFunccode(), ef);
			}
			rs.close();
			rs = null;
		} catch (Exception e) {
			logger.error("获取功能菜单失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return hm;
	}

	private void setElfuncParent(ElFunc ef) throws ElException {
		if (ef != null && ef.getParent() != null && ef.getParent().getId() != 0) {
			ef.setParent(getFuncById(ef.getParent().getId()));
			setElfuncParent(ef.getParent());
		}

	}

	public ElFunc getFuncById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ElFunc f = new ElFunc();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.USER_FUNC_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				f.setId(rs.getInt(1));
				f.setFunccode(rs.getString(2));
				f.setName(rs.getString(3));
				f.setDescription(rs.getString(4));
				f.setParent(new ElFunc());
				f.getParent().setId(rs.getInt(5));
				f.setNeedCheck(rs.getBoolean(6));
				f.setParams(rs.getString(7));
				f.setTarget(rs.getString(8));

			}
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return f;
	}

	// 20120107------------陶铭科
	public ElFunc getElFuncByTableNameAndParams(String tablename,
			String actionName, Integer rx) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ElFunc f = new ElFunc();
		ElFunc f_ = null;
		String sql = "";
		int parentid = 0;

		String sql1 = "";
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		String ids = "";
		try {
			ct = DBConnection.getConnection();

			if (actionName.equals("myContactTags")) {
				if (rx != null && rx == 1) {
					sql1 = "select id, funccode,name,params,parentid  from elfunc where funccode = '"
							+ actionName
							+ "' and params like 'tablename="
							+ tablename + "%'";
					ps1 = ct.prepareStatement(sql1);
					rs1 = ps1.executeQuery();
					while (rs1.next()) {
						if (rs1.getString("params") != null
								&& !rs1.getString("params").equals("")) {
							if (rs1.getString("params").indexOf("&") > 0) {
								ids = String.valueOf(rs1.getInt("id"));
							}
						}
					}

					sql = "select id, funccode,name,params,parentid  from elfunc where id in ("
							+ ids + ") ";
				} else {
					sql = "select id, funccode,name,params,parentid  from elfunc where funccode = '"
							+ actionName
							+ "' and params = 'tablename="
							+ tablename + "'";
				}
			} else if (actionName.equals("finalsearchContactTags")) {
				sql = "select id, funccode,name,params,parentid  from elfunc where funccode = '"
					+ TagsUtil.formatactionName(actionName)
						+ "' and params like 'tablename="
						+ tablename + "%'";
			} else if (actionName.equals("searchContactTags")) {
				sql = "select id, funccode,name,params,parentid  from elfunc where funccode = '"
					+ TagsUtil.formatactionName(actionName)
						+ "' and params = 'tablename="
						+ tablename
						+ "'";
			} else if (actionName.equals("addContactTagsInit")) {
				sql = "select id, funccode,name,params,parentid  from elfunc where funccode = '"
					+ TagsUtil.formatactionName(actionName)
						+ "' and params = 'tablename="
						+ tablename
						+ "'";
			} else if (actionName.equals("customAuditManageInit")) {
				sql = "select id, funccode,name,params,parentid  from elfunc where funccode = '"
						+ actionName
						+ "' and params = 'tablename="
						+ tablename
						+ "'";
			} else if (actionName.equals("customAuditListContactTags")) {
				sql = "select id, funccode,name,params,parentid  from elfunc where funccode = '"
					+ TagsUtil.formatactionName(actionName)
						+ "' and params = 'tablename="
						+ tablename
						+ "'";
			} else if (actionName.equals("myPassSearchContactTags")) {
				{
					sql = "select id, funccode,name,params,parentid  from elfunc where funccode = '"
							+ actionName
							+ "' and params = 'tablename="
							+ tablename + "'";
				}

			} else if(actionName.equals("customReportZDYInit")){
				sql = "select id, funccode,name,params,parentid  from elfunc where funccode = '"
					+ actionName
					+ "' and params = 'resultPage="
					+ tablename + "'";
			}else if(actionName.equals("addContactTagsInitZDY")){
				sql = "select id, funccode,name,params,parentid  from elfunc where funccode = '"
					+ TagsUtil.formatactionName(actionName)
					+ "' and params = 'tablename="
					+ tablename
					+ "'";
			}else if(actionName.equals("dataAllocationInit")){
				sql = "select id, funccode,name,params,parentid  from elfunc where funccode = '"
					+ TagsUtil.formatactionName(actionName)
					+ "' and params = 'tablename="
					+ tablename
					+ "'";
			}else if(actionName.equals("myGetDataAllocationInit")){
				sql = "select id, funccode,name,params,parentid  from elfunc where funccode = '"
					+ actionName
					+ "' and params = 'tablename="
					+ tablename
					+ "'";
			}else if(actionName.equals("dataApplicationInit")){
				sql = "select id, funccode,name,params,parentid  from elfunc where funccode = '"
					+ TagsUtil.formatactionName(actionName)
					+ "' and params = 'tablename="
					+ tablename
					+ "'";
			}

			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			// 向上找parent
			if (rs.next()) {
				f.setId(rs.getInt("id"));
				f.setFunccode(rs.getString("funccode"));
				f.setName(rs.getString("name"));
				parentid = rs.getInt("parentid");
				f.setParent(new ElFunc(rs.getInt("parentid")));

				if (f.getParent() != null && f.getParent().getId() != 0) {
					f_ = this.getById(parentid);

				}
				f.setParent(f_);

				if (f.getParent().getParent() != null
						&& f.getParent().getParent().getId() != 0) {
					f_ = this.getById(f.getParent().getParent().getId());
				}
				f.getParent().setParent(f_);

			}
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return f;
	}

	public ElFunc getById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ElFunc f = new ElFunc();
		String sql = "";
		try {
			ct = DBConnection.getConnection();

			sql = "select id, funccode,name,params,parentid from elfunc where id="
					+ id;
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				f.setId(rs.getInt("id"));
				f.setFunccode(rs.getString("funccode"));
				f.setName(rs.getString("name"));
				f.setParent(new ElFunc(rs.getInt("parentid")));
			}
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return f;
	}

	public ElFunc getElFuncByTableNameForViewOrUpdate(String tablename,
			String actionName) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ElFunc f = new ElFunc();
		String sql = "";
		ElFunc f_ = null;
		int parentid = 0;
		String value = "";
		if (actionName.equals("updateContactTagsInit")) {
			value = "修改";
		}else if(actionName.equals("updateContactTagsInitZDY")){
			value = "自定义修改";
		}else if(actionName.equals("viewContactTags")){
			value = "查看";
		}else if(actionName.equals("viewContactTagsZDY")){
			value = "自定义查看";
		}else if(actionName.equals("data_view")){
			value = "查看";
		}else if(actionName.equals("data_learn")){
			value = "学习";
		}
		try {
			ct = DBConnection.getConnection();

			sql = "select * from elfunc  where params = 'tablename="
					+ tablename + "'";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				f.setId(rs.getInt("id"));
				f.setName(value+ new TagsDaoImpl().getModuleNameByTablename(tablename));
				parentid = rs.getInt("parentid");
				f.setParent(new ElFunc(rs.getInt("parentid")));

				if (f.getParent() != null && f.getParent().getId() != 0) {
					f_ = this.getById(parentid);

				}
				f.setParent(f_);

				if (f.getParent().getParent() != null
						&& f.getParent().getParent().getId() != 0) {
					f_ = this.getById(f.getParent().getParent().getId());
				}
				f.getParent().setParent(f_);
			}
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return f;
	}

	public List<ElFunc> listChildFunc(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElFunc> child = new ArrayList<ElFunc>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id,funccode,name,params,parentid,dyimg,bgimg,linkimg from elfunc where parentid=? order by description asc  ");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				ElFunc ef = new ElFunc();
				ef.setId(rs.getInt(1));
				ef.setFunccode(rs.getString(2));
				ef.setName(rs.getString(3));
				ef.setParams(rs.getString(4));
				ef.setParent(new ElFunc(rs.getInt(5)));
				ef.setDyimg(rs.getString(6));
				ef.setBgimg(rs.getString(7));
				ef.setLinkimg(rs.getString(8));
				child.add(ef);
			}
			rs.close();
			rs = null;
		} catch (Exception e) {
			logger.error("获取功能菜单失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return child;
	}
	
	public List<ElFunc> listChildFunc_cisco(int id,int roleid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElFunc> child = new ArrayList<ElFunc>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select e.id,e.funccode,e.name,e.params,e.parentid,e.dyimg,e.bgimg,e.linkimg,e.target from elfunc e,elrolefunc el " +
					"	where e.id=el.funcid and e.parentid=? and e.parentid!=1 and el.roleid=? order by description asc  ");
			ps.setInt(1, id);
			ps.setInt(2, roleid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ElFunc ef = new ElFunc();
				ef.setId(rs.getInt(1));
				ef.setFunccode(rs.getString(2));
				ef.setName(rs.getString(3));
				ef.setParams(rs.getString(4));
				ef.setParent(new ElFunc(rs.getInt(5)));
				ef.setDyimg(rs.getString(6));
				ef.setBgimg(rs.getString(7));
				ef.setLinkimg(rs.getString(8));
				ef.setTarget(rs.getString(9));
				child.add(ef);
			}
			rs.close();
			rs = null;
		} catch (Exception e) {
			logger.error("获取功能菜单失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return child;
	}

	public int getCountRemoveUserCenter(int userid,int roleid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int count = 0;
		try {
			ct = DBConnection.getConnection();

			sql = "select count(1) from (" +
					"(select ef.id,ef.funccode,ef.name,ef.params,ef.target,ef.description,ef.dyimg,ef.bgimg,ef.linkimg from elfunc ef,elrolefunc ref where (ef.parentid != ? and ef.parentid!=?) "
				+ "and ef.id=ref.funcid and ref.roleid =? and ef.funccode!='index' and ef.funccode!='courseman' )  union "
				+ "(select ef1.id,ef1.funccode,ef1.name,ef1.params,ef1.target,ef1.description,ef1.dyimg,ef1.bgimg,ef1.linkimg from elfunc ef1,eluserfunc ref1 where (ef1.parentid != ? and ef1.parentid!=?) "
				+ "and ef1.id=ref1.funcid and ref1.userid =? and ef1.funccode!='index' and ef1.funccode!='courseman' ) order by description asc ) ";;
			ps = ct.prepareStatement(sql);
			ps.setInt(1, NewVersionConstants.GENRENZHONGXIN_ID);
			ps.setInt(2, NewVersionConstants.QITAIYEMIAN_FUNC_ID);
			ps.setInt(3, roleid);
			ps.setInt(4, NewVersionConstants.GENRENZHONGXIN_ID);
			ps.setInt(5, NewVersionConstants.QITAIYEMIAN_FUNC_ID);
			ps.setInt(6, userid);
			rs = ps.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("检查除个人中心外用户是否有其他功能菜单失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}

	// -------------陶铭科
}
