package com.sopia.newversion.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.duman.dao.impl.FuncDaoImpl;
import com.sopia.duman.entities.ElFunc;
import com.sopia.newversion.dao.NewVersionDao;

public class NewVersionDaoImpl implements NewVersionDao {
	private static final Log logger = LogFactory.getLog(NewVersionDaoImpl.class);

	public List<ElFunc> getMenus(int isFromAdmin,int qiantaifunc_parentid,int parentid, int roleid) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElFunc> efs = new ArrayList<ElFunc>();
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			if(isFromAdmin == 1){
//				sql = "(select ef.id,ef.funccode,ef.name,ef.params,ef.target,ef.description,ef.dyimg,ef.bgimg,ef.linkimg from elfunc ef,elrolefunc ref where (ef.parentid != ? and ef.parentid!=?) "
//					+ "and ef.id=ref.funcid and ref.roleid =? and ef.funccode!='index' and ef.funccode!='courseman' )  union "
//					+ "(select ef1.id,ef1.funccode,ef1.name,ef1.params,ef1.target,ef1.description,ef1.dyimg,ef1.bgimg,ef1.linkimg from elfunc ef1,eluserfunc ref1 where (ef1.parentid != ? and ef1.parentid!=?) "
//					+ "and ef1.id=ref1.funcid and ref1.userid =? and ef1.funccode!='index' and ef1.funccode!='courseman' ) order by description asc";
				sql = "select ef.id,ef.funccode,ef.name,ef.params,ef.target,ef.description,ef.dyimg,ef.bgimg,ef.linkimg from elfunc ef,elrolefunc ref where (ef.parentid != ? and ef.parentid!=?) "
					+ "and ef.id=ref.funcid and ref.roleid =? and ef.funccode!='index' and ef.funccode!='courseman'    order by description asc";
				ps = ct.prepareStatement(sql);
				ps.setInt(1, parentid);
				ps.setInt(2, qiantaifunc_parentid);
				ps.setInt(3, roleid);
//				ps.setInt(4, parentid);
//				ps.setInt(5, qiantaifunc_parentid);
//				ps.setInt(6, userid);
			}else{
//				sql = "(select ef.id,ef.funccode,ef.name,ef.params,ef.target,ef.description,ef.dyimg,ef.bgimg,ef.linkimg from elfunc ef,elrolefunc ref where ef.parentid = ? "
//					+ "and ef.id=ref.funcid and ref.roleid =? and ef.funccode!='index' and ef.funccode!='courseman' )  union "
//					+ "(select ef1.id,ef1.funccode,ef1.name,ef1.params,ef1.target,ef1.description,ef1.dyimg,ef1.bgimg,ef1.linkimg from elfunc ef1,eluserfunc ref1 where ef1.parentid = ? "
//					+ "and ef1.id=ref1.funcid and ref1.userid =? and ef1.funccode!='index' and ef1.funccode!='courseman' ) order by description asc";
				sql = "select ef.id,ef.funccode,ef.name,ef.params,ef.target,ef.description,ef.dyimg,ef.bgimg,ef.linkimg from elfunc ef,elrolefunc ref where ef.parentid = ? "
					+ "and ef.id=ref.funcid and ref.roleid =? and ef.funccode!='index' and ef.funccode!='courseman'  order by description asc";
				ps = ct.prepareStatement(sql);
				ps.setInt(1, parentid);
				ps.setInt(2, roleid);
//				ps.setInt(3, parentid);
//				ps.setInt(4, userid);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				ElFunc ef = new ElFunc();
				ef.setId(rs.getInt(1));
				ef.setFunccode(rs.getString(2));
				ef.setName(rs.getString(3));
				ef.setParams(rs.getString(4));
				ef.setTarget(rs.getString(5));
				ef.setDescription(rs.getString(6));
				ef.setDyimg(rs.getString(7));
				ef.setBgimg(rs.getString(8));
				ef.setLinkimg(rs.getString(9));
				//获取子节点
				//获取子节点满足条件：被分配的子节点
				ef.setChild(new FuncDaoImpl().listChildFunc_cisco(ef.getId(),roleid));
				efs.add(ef);
			}
		} catch (Exception e) {
			logger.error("中间部分显示所有该用户的三级有权菜单失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return efs;
	}

}
