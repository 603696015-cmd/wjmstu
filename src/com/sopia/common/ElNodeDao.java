package com.sopia.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 树状节点数据处理
 * @author Administrator
 *
 */
public class ElNodeDao {
	
	/**
	 * 
	 * 它 做两件事：1.设置父级节点的左右值
	 * 2.获取将要添加的节点的lid rid.
	 * @param ct
	 * @param node
	 * @param table
	 * @param condition
	 * @throws Exception
	 */
//	protected void addNode(Connection ct, ElNode node, String table,
//			String condition) throws Exception {
//		// 父节点信息
//		// 查找当前插入节点的父节点的lft值
//		ElNode parent = node.getParent();
//		Statement st = ct.createStatement();
//		ResultSet rs = st.executeQuery("select lid from " + table
//				+ " where id = " + parent.getId() + " and " + condition);
//		if (rs.next()) {
//			parent.setLid(rs.getInt(1));
//		}
//		rs.close();
//		st.close();
//		st=null;
//		
//		// 将树形结构中所有大于父节点左值的左节点+2
//		String sqll = "update " + table + " set lid = lid+2 where lid > "
//				+ parent.getLid() + " and " + condition;
//		st = ct.createStatement();
//		st.executeUpdate(sqll);
//		st.close();
//		st=null;
//		// 将树形结构中所有大于父节点左值的右节点+2
//		String sqlr = "update " + table + " set rid = rid +2 where rid>"
//				+ parent.getLid() + " and " + condition;
//		st = ct.createStatement();
//		st.executeUpdate(sqlr);
//		st.close();
//		st=null;
//		// 定位自己的左值(父节点左值+1)和右值(父节点左值+2)
//		node.setLid(parent.getLid() + 1);
//		node.setRid(parent.getLid() + 2);
//	}

	/**
	 * 修改节点：主要做 更新相关左右值
	 * @param ct
	 * @param node
	 * @param table
	 * @param condition
	 * @throws Exception
	 */
//	protected void alterNode(Connection ct, ElNode node, String table,
//			String condition) throws Exception {
//		ElNode parent = node.getParent();
//		Statement st = ct.createStatement();
//		String sql = "select lid,rid from " + table + " where id ="
//				+ node.getId();
//		ResultSet rs = st.executeQuery(sql);
//		int lid = 0;
//		int rid = 0;
//		int span = 0;
//		if (rs.next()) {
//			lid = rs.getInt(1);
//			rid = rs.getInt(2);
//			span = rid - lid + 1;
//			rs.close();
//		}
//		st.close();
//		st = null;
//		if (lid <= 0)
//			return;
//		// 获得当前父节点左位置
//		st = ct.createStatement();
//		int plid = 0;
//		if(parent!=null){
//			rs = st.executeQuery("select lid from " + table + " where id ="
//					+ parent.getId());
//			if (rs.next()) {
//				plid = rs.getInt(1);
//			}
//		}
//		
//		rs.close();
//		st.close();
//		st = null;
//		if (plid <= 0)
//			return;
//		// 先空出位置
//		String sqll = "update " + table + " set lid = lid +" + span
//				+ " where lid>" + plid + " and " + condition;
//		st = ct.createStatement();
//		st.executeUpdate(sqll);
//		st.close();
//		st = null;
//
//		String sqlr = "update " + table + " set rid = rid +" + span
//				+ " where rid>" + plid + " and " + condition;
//		st = ct.createStatement();
//		st.executeUpdate(sqlr);
//		st.close();
//		st = null;
//		// 再调整自己
//		sql = "select lid,rid from " + table + " where id =" + node.getId();
//		st = ct.createStatement();
//		rs = st.executeQuery(sql);
//		lid = 0;
//		if (rs.next()) {
//			lid = rs.getInt(1);
//			rid = rs.getInt(2);
//		}
//		rs.close();
//		st.close();
//		st = null;
//		if (lid <= 0)
//			return;
//		int offset = plid - lid + 1;
//		sqll = "update " + table + " set lid = lid +" + offset + ",rid = rid+"
//				+ offset + " where " + "lid between " + lid + " and " + rid
//				+ " and " + condition;
//		st = ct.createStatement();
//		st.executeUpdate(sqll);
//		st.close();
//		st = null;
//		// 最后删除（清空位置）
//		sqll = "update " + table + " set lid = lid-" + span + " where lid>"
//				+ rid + " and " + condition;
//		st = ct.createStatement();
//		st.executeUpdate(sqll);
//		st.close();
//		st = null;
//		sqll = "update " + table + " set rid = rid-" + span + " where rid>"
//				+ rid + " and " + condition;
//		st = ct.createStatement();
//		st.executeUpdate(sqll);
//		st.close();
//		st = null;
//	}

	/**
	 * 删除节点。主要做更新左右值
	 * @param ct
	 * @param node
	 * @param table
	 * @param condition
	 * @throws Exception
	 */
//	public void deleteNode(Connection ct, ElNode node, String table,
//			String condition) throws Exception {
//		// 查找要删除的节点的左值
//		Statement st = ct.createStatement();
//		String sql = "select lid from " + table + " where id=" + node.getId();
//		ResultSet rs = st.executeQuery(sql);
//		int lid = 0;
//		if (rs.next()) {
//			lid = rs.getInt(1);
//		}
//		rs.close();
//		st.close();
//		st = null;
//		// 将所有大于删除节点左值的rgt都-2
//		if (lid > 0) {
//
//			sql = "update " + table + " set lid = lid-2 where lid >" + lid
//					+ " and " + condition;
//			st = ct.createStatement();
//			st.executeUpdate(sql);
//			st.close();
//			st = null;
//
//			sql = "update " + table + " set rid = rid-2 where rid >" + lid
//					+ " and " + condition;
//			st = ct.createStatement();
//			st.executeUpdate(sql);
//			st.close();
//			st = null;
//			// ps.executeUpdate("delete from dbtree where id = "+node.getId());
//			// 修改下级节点的parentid
//		}
//	}
}
