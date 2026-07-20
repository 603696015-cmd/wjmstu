package com.sopia.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.dao.DepartmentDao;
import com.sopia.duman.entities.Department;

/**
 * 根据树结构，生成条件语句，调用类
 * 
 * @author Administrator
 * 
 */
public class ElNodeSQL {
	private static final Log logger = LogFactory.getLog(ElNodeSQL.class);

	@SuppressWarnings("unchecked")
	/**
	 * 根据部门树来获取部门的查询条件
	 */
	public static String getWhereSql(Department tree) {
		StringBuffer whereSql = new StringBuffer();
		if (tree != null) {
			if (tree.getId() == -2) {
				whereSql.append("(");
				for (int i = 0; i < tree.getChild().size(); i++) {
					if (i == 0) {
						whereSql.append(" (dep.lid>="
								+ tree.getChild().get(i).getLid()
								+ " and dep.rid<="
								+ tree.getChild().get(i).getRid() + ")");
					} else {
						whereSql.append(" or (dep.lid>="
								+ tree.getChild().get(i).getLid()
								+ " and dep.rid<="
								+ tree.getChild().get(i).getRid() + ")");
					}
				}
				whereSql.append(")");
			} else {
				whereSql.append(" (dep.lid>=" + tree.getLid()
						+ " and dep.rid<=" + tree.getRid() + ")");
			}
		}
		return whereSql.toString();
	}
	/**
	 * 根据部门树来获取部门的查询条件
	 * @param tree
	 * @param alias 别名
	 * @return
	 */
	public static String getWhereSql(ElNode tree,String alias,int consub) {
		StringBuffer whereSql = new StringBuffer();
		if (tree != null) {
			if (tree.getId() == -2) {
				whereSql.append("(");
				for (int i = 0; i < tree.getNchild().size(); i++) {
					if (i == 0) {
						if(consub==1){
							whereSql.append(" ("+alias+".lid>="
									+ tree.getNchild().get(i).getLid()
									+ " and "+alias+".rid<="
									+ tree.getNchild().get(i).getRid() + ")");
						}else{
							whereSql.append(" ("+alias+".id="+ tree.getId()+ ")");
						}
					} else {
						if(consub==1){
							whereSql.append(" or ("+alias+".lid>="
									+ tree.getNchild().get(i).getLid()
									+ " and "+alias+".rid<="
									+ tree.getNchild().get(i).getRid() + ")");
						}else{
							whereSql.append(" or ("+alias+".id="+ tree.getId()+ ")");
						}
					}
				}
				whereSql.append(")");
			} else {
				if(consub==1){
					whereSql.append(" ("+alias+".lid>=" + tree.getLid()
							+ " and "+alias+".rid<=" + tree.getRid() + ")");
				}else{
					whereSql.append(" ("+alias+".id="+ tree.getId()+ ")");
				}
			}
		}
		return whereSql.toString();
	}

	// /**
	// * 根据角色和用户来获取部门的查询条件
	// * @param roleid
	// * @param userid
	// * @return
	// */
	// public static String getWhereSql(int roleid,int userid){
	// StringBuffer whereSql=new StringBuffer();
	// DepartmentDao
	// departmentDao=(DepartmentDao)SpringContextUtil.getBean("departmentDao");
	// Department tree=null;
	// try {
	// if (roleid == 1) {
	// tree = departmentDao.getDepTree_level1(1, -1,true);
	// } else {
	// tree = departmentDao.getDepTree_level1(userid, "op", -1,true);
	// }
	// if (tree != null) {
	// if (tree.getId() == -2) {
	// whereSql.append("(");
	// for (int i = 0; i < tree.getChild().size(); i++) {
	// if(i==0){
	// whereSql.append(" (dep.lid>="+tree.getChild().get(i).getLid()+" and
	// dep.rid<="+tree.getChild().get(i).getRid()+")");
	// }else{
	// whereSql.append(" or (dep.lid>="+tree.getChild().get(i).getLid()+" and
	// dep.rid<="+tree.getChild().get(i).getRid()+")");
	// }
	// }
	// whereSql.append(")");
	// } else {
	// whereSql.append(" (dep.lid>="+tree.getLid()+" and
	// dep.rid<="+tree.getRid()+")");
	// }
	// }
	// } catch (ElException e) {
	// // TODO: handle exception
	// logger.error("根据角色和用户来获取部门的查询条件错误！");
	// }
	// return whereSql.toString();
	// }
	/**
	 * 根据部门id来获取部门的查询条件
	 * 
	 * @param roleid
	 * @param userid
	 * @return
	 */
	public static String getWhereSql(int depid, int userid, String tName) {
		StringBuffer whereSql = new StringBuffer();
		DepartmentDao departmentDao = (DepartmentDao) SpringContextUtil
				.getBean("departmentDao");
		Department tree = null;
		try {
			if (depid == -2) {
				tree = departmentDao.getDepTree_level1(userid, "op", -1, true);
			} else {
				//tree = departmentDao.getDepTree_level1(depid, -1, true);
				tree = departmentDao.getDepById(depid);
			}
			if (tree != null) {
				if (tree.getId() == -2) {
					whereSql.append("(");
					for (int i = 0; i < tree.getChild().size(); i++) {
						if (i == 0) {
							whereSql.append(" (" + tName + ".lid>="
									+ tree.getChild().get(i).getLid() + " and "
									+ tName + ".rid<="
									+ tree.getChild().get(i).getRid() + ")");
						} else {
							whereSql.append(" or (" + tName + ".lid>="
									+ tree.getChild().get(i).getLid() + " and "
									+ tName + ".rid<="
									+ tree.getChild().get(i).getRid() + ")");
						}
					}
					whereSql.append(")");
				} else {
					whereSql.append(" (" + tName + ".lid>=" + tree.getLid()
							+ " and " + tName + ".rid<=" + tree.getRid() + ")");
				}
			}
		} catch (ElException e) {
			// TODO: handle exception
			logger.error("根据角色和用户来获取部门的查询条件错误！",e);
		}
		return whereSql.toString();
	}
	
	public static String getWhereSql_use(int depid, int userid, String tName) {
		StringBuffer whereSql = new StringBuffer();
		DepartmentDao departmentDao = (DepartmentDao) SpringContextUtil
				.getBean("departmentDao");
		Department tree = null;
		try {
			if (depid == -2) {
				tree = departmentDao.getDepTree_level1(userid, "use", -1, true);
			} else {
				//tree = departmentDao.getDepTree_level1(depid, -1, true);
				tree = departmentDao.getDepById(depid);
			}
			if (tree != null) {
				if (tree.getId() == -2) {
					whereSql.append("(");
					for (int i = 0; i < tree.getChild().size(); i++) {
						if (i == 0) {
							whereSql.append(" (" + tName + ".lid>="
									+ tree.getChild().get(i).getLid() + " and "
									+ tName + ".rid<="
									+ tree.getChild().get(i).getRid() + ")");
						} else {
							whereSql.append(" or (" + tName + ".lid>="
									+ tree.getChild().get(i).getLid() + " and "
									+ tName + ".rid<="
									+ tree.getChild().get(i).getRid() + ")");
						}
					}
					whereSql.append(")");
				} else {
					whereSql.append(" (" + tName + ".lid>=" + tree.getLid()
							+ " and " + tName + ".rid<=" + tree.getRid() + ")");
				}
			}
		} catch (ElException e) {
			// TODO: handle exception
			logger.error("根据角色和用户来获取部门的查询条件错误！",e);
		}
		return whereSql.toString();
	}

	/**
	 * Description: 传入基础查询语句，树的表名字，树，是否包含下级 生成查询集合的sql 语句。
	 * 
	 * @Version1.0 2012-6-18 下午03:48:48 by 闻益舜（wenyishun110@163.com）创建
	 * @param sql
	 * @param tablename
	 * @param tree
	 * @param consub
	 * @return
	 */
	public String generateSQLByTree(String tablename, ElNode tree,
			boolean consub) {
		if (tree == null) {
			return "select * from " + tablename + " where 1!=1";
		}
		String sql = "select * from " + tablename;
		StringBuffer sqls = new StringBuffer();
		if (tree.getId() > 0) {
			sqls.append(sql);
			sqls.append(" where");
			if (consub)
				sqls.append(" lid>=" + tree.getLid() + " and rid<= "
						+ tree.getRid());
			else
				sqls.append(" id=" + tree.getId());

		} else {
			if (tree.getNchild() == null || tree.getNchild().size() <= 0) {
				return "select * from " + tablename + " where 1!=1";
			}
			for (int i = 0; i < tree.getNchild().size(); i++) {
				ElNode e = tree.getNchild().get(i);
				if (i == 0) {
					sqls.append(sql);
					sqls.append(" where");
					if (consub)
						sqls.append(" lid>=" + e.getLid() + " and rid<= "
								+ e.getRid());
					else
						sqls.append(" id=" + e.getId());
					sqls.append("\n");
				} else {
					sqls.append(" union ");
					sqls.append(sql);
					sqls.append(" where");
					if (consub)
						sqls.append(" lid>=" + e.getLid() + " and rid<= "
								+ e.getRid());
					else
						sqls.append(" id=" + e.getId());
					sqls.append("\n");

				}
			}
		}
		return sqls.toString();
	}
	
	public String generateSQLByTree2(String tablename, ElNode tree,
			boolean consub) {
		if (tree == null) {
			return "select id from " + tablename + " where 1!=1";
		}
		String sql = "select id from " + tablename;
		StringBuffer sqls = new StringBuffer();
		if (tree.getId() > 0) {
			sqls.append(sql);
			sqls.append(" where");
			if (consub)
				sqls.append(" lid>=" + tree.getLid() + " and rid<= "
						+ tree.getRid());
			else
				sqls.append(" id=" + tree.getId());

		} else {
			if (tree.getNchild() == null || tree.getNchild().size() <= 0) {
				return "select id from " + tablename + " where 1!=1";
			}
			for (int i = 0; i < tree.getNchild().size(); i++) {
				ElNode e = tree.getNchild().get(i);
				if (i == 0) {
					sqls.append(sql);
					sqls.append(" where");
					if (consub)
						sqls.append(" lid>=" + e.getLid() + " and rid<= "
								+ e.getRid());
					else
						sqls.append(" id=" + e.getId());
					sqls.append("\n");
				} else {
					sqls.append(" union ");
					sqls.append(sql);
					sqls.append(" where");
					if (consub)
						sqls.append(" lid>=" + e.getLid() + " and rid<= "
								+ e.getRid());
					else
						sqls.append(" id=" + e.getId());
					sqls.append("\n");

				}
			}
		}
		return sqls.toString();
	}
	
	/**
	 * 根据类别id获取该表的查询sql
	 * @param tableName 表名
	 * @param id
	 * @param consub 是否包含下级
	 * @return
	 * @throws ElException
	 */
	public String generateSQLById(String tableName, int id,boolean consub) throws ElException {
		if (id <=0) {
			return "select * from " + tableName + " where 1!=1";
		}
		StringBuffer sqls = new StringBuffer("select * from " + tableName);
		//根据类型id获取他的左右值
		ElNode node=this.getTypeLRid(id, tableName);
		if(consub){
			sqls.append(" where lid>="+node.getLid()+" and rid<="+node.getRid());
		}else{
			sqls.append(" where id="+id);
		}
		return sqls.toString();
	}

//	private String generatePSByTreeBaseSql(String basesql, String alias,
//			boolean consub) {
//		StringBuffer sqls = new StringBuffer();
//		sqls.append(basesql);
//		if (consub)
//			sqls.append(" " + alias + ".lid>= ? and " + alias + ".rid<=? ");
//		else
//			sqls.append(" " + alias + ".id= ? ");
//		return sqls.toString();
//	}

	/**
	 * Description: 带分页的preparedStatement 语句的 组装
	 * 
	 * @Version1.0 2012-6-20 上午09:06:03 by 闻益舜（wenyishun110@163.com）创建
	 * @param beginSql
	 * @param sql
	 * @param endSql
	 * @param alias
	 * @param tree
	 * @param consub
	 * @param params
	 * @param ct
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
//	public PreparedStatement generatePSByTree(String beginSql, String sql,
//			String endSql, String alias, ElNode tree, boolean consub,
//			List<Object> params, Connection ct, int pageNow, int pageSize)
//			throws Exception {
//		PreparedStatement ps = null;
//		if (tree == null) {
//			return ps;
//		}
//		int paramlength = 1;
//		if (tree.getId() > 0) {
//			String sql_ = generatePSByTreeBaseSql(sql, alias, consub);
//
//			ps = ct.prepareStatement(beginSql + sql_ + endSql);
//			if (params != null)
//				for (int i = 0; i < params.size(); i++) {
//					setParam(ps, paramlength, params.get(i));
//					paramlength++;
//				}
//			if (consub) {
//				setParam(ps, paramlength, tree.getLid());
//				paramlength++;
//				setParam(ps, paramlength, tree.getRid());
//				paramlength++;
//			} else {
//				setParam(ps, paramlength, tree.getId());
//				paramlength++;
//			}
//
//		} else {
//			StringBuffer sqls = new StringBuffer();
//			if (tree.getNchild() == null || tree.getNchild().size() <= 0) {
//				return ps;
//			}
//			// 生成语句
//			sqls.append(beginSql);
//			for (int i = 0; i < tree.getNchild().size(); i++) {
//				if (i == 0) {
//					sqls.append(generatePSByTreeBaseSql(sql, alias, consub));
//					sqls.append("\n");
//				} else {
//					sqls.append(" union ");
//					sqls.append(generatePSByTreeBaseSql(sql, alias, consub));
//					sqls.append("\n");
//				}
//			}
//			sqls.append(endSql);
//			ps = ct.prepareStatement(sqls.toString());
//			// 设置参数
//			for (int i = 0; i < tree.getNchild().size(); i++) {
//				ElNode e = tree.getNchild().get(i);
//				if (params != null)
//					for (int j = 0; j < params.size(); j++) {
//						setParam(ps, paramlength, params.get(j));
//						paramlength++;
//					}
//				if (consub) {
//					setParam(ps, paramlength, e.getLid());
//					paramlength++;
//					setParam(ps, paramlength, e.getRid());
//					paramlength++;
//				} else {
//					setParam(ps, paramlength, e.getId());
//					paramlength++;
//				}
//			}
//		}
//		setParam(ps, paramlength, pageNow);
//		paramlength++;
//		setParam(ps, paramlength, pageSize);
//		return ps;
//	}

	/**
	 * Description: 不带分页的ps语句的组装。
	 * 
	 * @Version1.0 2012-6-20 上午09:06:28 by 闻益舜（wenyishun110@163.com）创建
	 * @param beginSql
	 *            开头语句（视具体情况输入）
	 * @param sql//基础原语句，联合
	 *            树的左右id的语句。
	 * @param endSql
	 *            结束语句（视具体情况输入）
	 * @param alias
	 *            左右id的表的别名
	 * @param tree
	 *            树：需要带齐左右id
	 * @param consub
	 *            是否包含下级，
	 * @param params
	 *            源语句的参数列表，注意顺序。
	 * @param ct
	 *            数据库连接
	 * @return
	 * @throws Exception
	 */
//	public PreparedStatement generatePSByTree(String beginSql, String sql,
//			String endSql, String alias, ElNode tree, boolean consub,
//			List<Object> params, Connection ct) throws Exception {
//		PreparedStatement ps = null;
//		if (tree == null) {
//			return ps;
//		}
//		int paramlength = 1;
//		if (tree.getId() > 0) {
//			String sql_ = generatePSByTreeBaseSql(sql, alias, consub);
//
//			ps = ct.prepareStatement(beginSql + sql_ + endSql);
//			if (params != null)
//				for (int i = 0; i < params.size(); i++) {
//					setParam(ps, paramlength, params.get(i));
//					paramlength++;
//				}
//			if (consub) {
//				setParam(ps, paramlength, tree.getLid());
//				paramlength++;
//				setParam(ps, paramlength, tree.getRid());
//				paramlength++;
//			} else {
//				setParam(ps, paramlength, tree.getId());
//				paramlength++;
//			}
//
//		} else {
//			StringBuffer sqls = new StringBuffer();
//			if (tree.getNchild() == null || tree.getNchild().size() <= 0) {
//				return ps;
//			}
//			// 生成语句
//			sqls.append(beginSql);
//			for (int i = 0; i < tree.getNchild().size(); i++) {
//				if (i == 0) {
//					sqls.append(generatePSByTreeBaseSql(sql, alias, consub));
//					sqls.append("\n");
//				} else {
//					sqls.append(" union ");
//					sqls.append(generatePSByTreeBaseSql(sql, alias, consub));
//					sqls.append("\n");
//				}
//			}
//			sqls.append(endSql);
//			ps = ct.prepareStatement(sqls.toString());
//			// 设置参数
//			for (int i = 0; i < tree.getNchild().size(); i++) {
//				ElNode e = tree.getNchild().get(i);
//				if (params != null)
//					for (int j = 0; j < params.size(); j++) {
//						setParam(ps, paramlength, params.get(j));
//						paramlength++;
//					}
//				if (consub) {
//					setParam(ps, paramlength, e.getLid());
//					paramlength++;
//					setParam(ps, paramlength, e.getRid());
//					paramlength++;
//				} else {
//					setParam(ps, paramlength, e.getId());
//					paramlength++;
//				}
//			}
//		}
//		return ps;
//	}

	/**
	 * Description: 设置preparedStatement 的参数集。
	 * 
	 * @Version1.0 2012-6-21 上午09:20:33 by 闻益舜（wenyishun110@163.com）创建
	 * @param ps
	 * @param index
	 * @param object
	 * @throws Exception
	 */
//	private void setParam(PreparedStatement ps, int index, Object object)
//			throws Exception {
//		if (object instanceof String) {
//			ps.setString(index, object.toString());
//			return;
//		}
//		if (object instanceof java.sql.Date) {
//			ps.setDate(index, (java.sql.Date) object);
//			return;
//		}
//		if (object instanceof Integer) {
//			ps.setInt(index, (Integer) object);
//			return;
//		}
//		if (object instanceof Long) {
//			ps.setLong(index, (Long) object);
//			return;
//		}
//		if (object instanceof Double) {
//			ps.setDouble(index, (Double) object);
//			return;
//		}
//		if (object instanceof Float) {
//			ps.setFloat(index, (Float) object);
//			return;
//		}
//		if (object instanceof Timestamp) {
//			ps.setTimestamp(index, (Timestamp) object);
//			return;
//		}
//		ps.setObject(index, object);
//	}

	/**
	 * Description:更新输入的表名（tablename)的表的左右id，采用存储过程实现，在各种树结构的功能中（增加，删除，修改操作的）action中做调用。
	 * 
	 * @Version1.0 2012-6-20 上午09:08:55 by 闻益舜（wenyishun110@163.com）创建
	 * @param tablename
	 * @throws ElException
	 */
	public void updatetlrid(String tablename) throws ElException {

		PreparedStatement ps = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("call updatetlrid(?) ");
			ps.setString(1, tablename);
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			logger.error("更新表：“" + tablename + "”出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, null);
		}
	}

//	public static void main(String[] args) {
		// int ii = 2;
		// Object i = ii;
		// Object[] objs = { "sdf", 1, 2, new Date() };
//
//		ElNode tree = new ElNode(1);
//		tree.setLid(1);
//		tree.setRid(8);
//		List<ElNode> s = new ArrayList<ElNode>();
//		ElNode c1 = new ElNode(1);
//		c1.setLid(2);
//		c1.setRid(3);
//		ElNode c2 = new ElNode(2);
//		c2.setLid(4);
//		c2.setRid(5);
//		ElNode c3 = new ElNode(3);
//		c3.setLid(6);
//		c3.setRid(7);
//		s.add(c1);
//		s.add(c2);
//		s.add(c3);
//		tree.setNchild(s);
//		System.out
//				.println(new ElNodeSQL().generateSQLByTree(
//						"department",tree, true));
//
//	}
	
	/**
	 * Description: 检查节点nodeid是否在tree范围内 
	* @Version1.0 2012-7-6 下午05:44:29 by 闻益舜（wenyishun110@163.com）创建
	 * @param nodeid
	 * @param tree
	 * @param libtablename
	 * @return
	 * @throws ElException
	 */
	public boolean checkNode(int nodeid,  ElNode tree,String libtablename) throws ElException{

		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		boolean b = false;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select count(id) from ("+generateSQLByTree(libtablename, tree, true)+") where id = ?");
			ps.setInt(1, nodeid);
			rs= ps.executeQuery();
			if(rs.next()){
				if(rs.getInt(1)>0){
					b = true;
				}
			}
		} catch (Exception e) {
			logger.error("检查节点权限出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, null);
		}
		return b;
	}
	/**
	 * Description: 检查pnodeid是否是nodeid的自己或其子节点 
	* @Version1.0 2012-7-6 下午05:49:28 by 闻益舜（wenyishun110@163.com）创建
	 * @param nodeid
	 * @param pnodeid
	 * @param libtablename
	 * @return
	 * @throws ElException
	 */
	public boolean checkNodeisChild(int nodeid,  int pnodeid,String libtablename) throws ElException{
		boolean b = false;
		try {	
			ElNode n = getTypeLRid(nodeid, libtablename);
			ElNode pn = getTypeLRid(pnodeid, libtablename);
			if(n.getLid()<=pn.getLid()&&pn.getRid()<=n.getRid()){
				b = true;
			}
		} catch (Exception e) {
			logger.error("检查节点权限出错！", e);
			throw new ElException(e);
		}
		return b;
	}
	/**
	 * 获取类别的左右id
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public ElNode getTypeLRid(int id,String tableName) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ElNode node = new ElNode();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id,lid,rid from "+tableName+" dep where id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				node = new ElNode(rs.getInt(1));
				node.setLid(rs.getInt(2));
				node.setRid(rs.getInt(3));
			}
		} catch (Exception e) {
			logger.error("获取类别的左右id出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return node;
	}
	
	/**
	 * taomingke
	 * @param tablename
	 * @param tree
	 * @param consub
	 * @return
	 */
	public String generateSQLByTree_(String tablename, ElNode tree,
			boolean consub) {
		if (tree == null) {
			return "select * from " + tablename + " where 1!=1";
		}
		String sql = "select * from " + tablename;
		StringBuffer sqls = new StringBuffer();
		if (tree.getId() > 0) {
			sqls.append(sql);
			sqls.append(" where");
			if (consub)
				sqls.append(" lid>=" + tree.getLid() + " and rid<= "
						+ tree.getRid());
			else
				sqls.append(" id=" + tree.getId());

		} else {
			if (tree.getNchild() == null || tree.getNchild().size() <= 0) {
				return "select * from " + tablename + " where 1!=1";
			}
			for (int i = 0; i < tree.getNchild().size(); i++) {
				ElNode e = tree.getNchild().get(i);
				if (i == 0) {
					sqls.append(sql);
					sqls.append(" where");
					if (consub)
						sqls.append(" lid>=" + e.getLid() + " and rid<= "
								+ e.getRid());
					else
						sqls.append(" id=" + e.getId());
					sqls.append("\n");
				} else {
					sqls.append(" union ");
					sqls.append(sql);
					sqls.append(" where");
					if (consub)
						sqls.append(" lid>=" + e.getLid() + " and rid<= "
								+ e.getRid());
					else
						sqls.append(" id=" + e.getId());
					sqls.append("\n");

				}
			}
		}
		return sqls.toString();
	}
}
