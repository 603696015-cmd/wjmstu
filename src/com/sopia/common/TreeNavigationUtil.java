package com.sopia.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.sopia.classman.dao.impl.ElClTypeDaoImpl;
import com.sopia.duman.entities.SimpleNode;

public class TreeNavigationUtil {
	private static final Log logger = LogFactory.getLog(ElClTypeDaoImpl.class);
	private List<SimpleNode> simpleNodes;
	
	public List<SimpleNode> getSimpleNodes() {
		return simpleNodes;
	}

	public void setSimpleNodes(List<SimpleNode> simpleNodes) {
		this.simpleNodes = simpleNodes;
	}

	/**
	 * 根据id获取相应的表数据
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public SimpleNode getSimpleNodeId(int id,String tabName) throws ElException{
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		SimpleNode simpleNode=null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id,name,parentid from "+tabName+" where id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				simpleNode=new SimpleNode();
				simpleNode.setId(rs.getInt("id"));
				simpleNode.setName(rs.getString("name"));
				simpleNode.setParent(new SimpleNode(rs.getInt("parentid")));
			}
		} catch (Exception e) {
			logger.error("根据id获取相应的表数据失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return simpleNode;
	}
	
	/**
	 * 根据funccode获取功能信息
	 * @param funccode
	 * @return
	 * @throws ElException
	 */
	public List<SimpleNode> getsimpleNodes(int id,String tabName) throws ElException{
		SimpleNode simpleNode=this.getSimpleNodeId(id,tabName);
		this.simpleNodes=new ArrayList<SimpleNode>();
		this.getSimpleNode(simpleNode,tabName);//填充list
		return this.simpleNodes;
	}
	
	/**
	 * 根据elFunc查找功能信息
	 * @param funccode
	 * @return
	 * @throws ElException
	 */
	public void getSimpleNode(SimpleNode simpleNode,String tabName) throws ElException{
		if(simpleNode==null||simpleNode.getParent()==null||simpleNode.getParent().getId()==0){
			//查找完毕
			this.simpleNodes.add(simpleNode);
		}else{
			this.simpleNodes.add(simpleNode);
			simpleNode=this.getSimpleNodeId(simpleNode.getParent().getId(),tabName);
			this.getSimpleNode(simpleNode,tabName);
		}
		//return elFunc;
	}
}
