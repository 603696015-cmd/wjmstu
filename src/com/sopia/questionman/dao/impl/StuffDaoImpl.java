package com.sopia.questionman.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeDao;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.ElQuerySql;
import com.sopia.common.StringUtil;
import com.sopia.common.SystemConfOp;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.dao.DepartmentDao;
import com.sopia.duman.dao.UserDao;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.QuestionConstants;
import com.sopia.questionman.dao.StuffDao;
import com.sopia.questionman.entities.StuffLib;
import com.sopia.questionman.entities.StuffQuery;

public class StuffDaoImpl extends ElNodeDao implements StuffDao {
	private static final Log logger = LogFactory.getLog(StuffDaoImpl.class);
	
	public int addQstuff(StuffLib qs) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int id = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.STUFF_ADD));
			ps.setString(1, qs.getTitle());
			ps.setString(2, qs.getDescription());
			ps.setString(3, qs.getFileext());
			ps.setInt(4, qs.getOwner().getId());
			ps.setTimestamp(5, new Timestamp(new Date().getTime()));
			ps.setLong(6, qs.getLength());
			ps.setInt(7, qs.getType());
			ps.setInt(8, qs.getParent().getId());
			ps.setString(9, qs.getKey());
			ps.setString(10, qs.getFileinfo());
			ps.setString(11, qs.getStuffpic());
			ps.setInt(12, qs.getStuffhot());
			ps.setInt(13, qs.getFromchange());
			ps.executeUpdate();
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("SELECT IDENT_CURRENT('question_stuff') AS id");
				rs = ps.executeQuery();

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("select question_stuff_sequence.currval from dual ");
				rs = ps.executeQuery();
			}else{
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			if (rs.next()) {
				id = rs.getInt(1);
				qs.setId(id);
			}
		} catch (Exception e) {
			logger.error("获取试题出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;
	}

	public StuffLib getStuffbyId(int id, int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		StuffLib qs = new StuffLib();
		try {
			ct = DBConnection.getConnection();
			if (userid != 0) {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(QuestionConstants.STUFF_QUERY_BYUID));
				ps.setInt(1, userid);
				ps.setInt(2, id);
			} else {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(QuestionConstants.STUFF_QUERY_BYID));
				ps.setInt(1, id);
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				qs = new StuffLib(rs.getInt(1), rs.getString(2));
				qs.setDescription(rs.getString(3));
				qs.setFileext(rs.getString(4));
				qs.setModifytime(rs.getTimestamp(5));
				qs.setCreatetime(rs.getTimestamp(6));
				qs.setLength(rs.getLong(7));
				// long length = 0;
				// try {
				// length = new Long(rs.getString(7));
				// } catch (Exception e) {
				// }
				// qs.setLength(length);
				qs.setType(rs.getInt(8));
				qs.setShared(rs.getInt(11));
				qs.setParent(new StuffLib(rs.getInt(9), rs.getString(10)));
				qs.setOwner(new ELUser(rs.getInt(12)));
				//,qs.fileinfo,qs.stuffpic,qs.stuffhot  
				qs.setFileinfo(rs.getString(13));
				qs.setStuffpic(rs.getString(14));
				qs.setStuffhot(rs.getInt(15));

			}
		} catch (Exception e) {
			logger.error("获取资料列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return qs;
	}
	public boolean checkStuffidisGrant(int id, int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean b = false;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from question_stuff_use_type where stuffid = ? and userid = ?");

			ps.setInt(1, id);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				b = true;
			}
		} catch (Exception e) {
			logger.error("获取资料出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return b ;
	}
	/**
	 * 判断地址是否本机
	 * @param url
	 * @param contextPath
	 * @return
	 * @throws ElException
	 */
	public boolean checkUrlIsLocal(String url,String contextPath,String serverName) throws ElException {
		String port=SystemConfOp.getValue(ElConstants.SYSTEM_CONF_HTTP_PORT);
		if(("http://localhost:"+port+contextPath).equals(url)){
			return true;
		}else if(("http://127.0.0.1:"+port+contextPath).equals(url)){
			return true;
		}else if(("http://"+serverName+":"+port+contextPath).equals(url)){
			return true;
		}
		return false;
	}
	
	/**
	 * 获取素材的信息以及所有父信息
	 * @param id
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public StuffLib getStuffbyId2(int id, int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		StuffLib qs = new StuffLib();
		try {
			ct = DBConnection.getConnection();
			if (userid != 0) {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(QuestionConstants.STUFF_QUERY_BYUID));
				ps.setInt(1, userid);
				ps.setInt(2, id);
			} else {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(QuestionConstants.STUFF_QUERY_BYID));
				ps.setInt(1, id);
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				qs = new StuffLib(rs.getInt(1), rs.getString(2));
				qs.setDescription(rs.getString(3));
				qs.setFileext(rs.getString(4));
				qs.setModifytime(rs.getTimestamp(5));
				qs.setCreatetime(rs.getTimestamp(6));
				qs.setLength(rs.getLong(7));
				// long length = 0;
				// try {
				// length = new Long(rs.getString(7));
				// } catch (Exception e) {
				// }
				// qs.setLength(length);
				qs.setType(rs.getInt(8));
				qs.setShared(rs.getInt(11));
				if(rs.getInt(9)>0){
					qs.setParent(getStuffbyId2(rs.getInt(9),0));
				}else{
					qs.setParent(new StuffLib(rs.getInt(9), rs.getString(10)));
				}
				//qs.setParent(new StuffLib(rs.getInt(9), rs.getString(10)));
				qs.setOwner(new ELUser(rs.getInt(12)));
				qs.setFileinfo(rs.getString(13));
				qs.setStuffpic(rs.getString(14));
				qs.setStuffhot(rs.getInt(15));
				qs.setFromchange(rs.getInt(16));


			}
		} catch (Exception e) {
			logger.error("获取资料列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return qs;
	}

	public void setStuffParent(StuffLib stuffLib, List<StuffLib> list)
			throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		if (null != list)
			for (int i = 0; i < list.size(); i++) {
				if (stuffLib.getId() == list.get(i).getId())
					return;
			}
		else
			return;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select p.id,p.title from question_stuff q left join question_stuff p on q.parentid = p.id  where q.id = ?");

			ps.setInt(1, stuffLib.getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				stuffLib.setParent(new StuffLib(rs.getInt(1), rs.getString(2)));
				if (stuffLib.getParent().getId() != 0) {
					setStuffParent(stuffLib.getParent(), list);
				} else
					stuffLib.setParent(new StuffLib(0, "根"));
			}
		} catch (Exception e) {
			logger.error("获取资料出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void setStuffParent(StuffLib stuffLib) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select p.id,p.title from question_stuff q left join question_stuff p on q.parentid = p.id  where q.id = ?");

			ps.setInt(1, stuffLib.getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				stuffLib.setParent(new StuffLib(rs.getInt(1), rs.getString(2)));
				if (stuffLib.getParent().getId() != 0) {
					setStuffParent(stuffLib.getParent());
				} else
					stuffLib.setParent(new StuffLib(0, "根"));
			}
		} catch (Exception e) {
			logger.error("获取资料出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 在前面插入父id路径
	 * @param stuffId
	 * @param stuff_path
	 * @throws ElException
	 */
	private void insertPath(int stuffId,StringBuffer stuff_path) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			//查出父节点对象
			ps = ct.prepareStatement("select p.id,p.title from question_stuff q left join question_stuff p on q.parentid = p.id  where q.id = ?");
			ps.setInt(1, stuffId);
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getInt(1) != 0) {
					stuff_path.insert(0,rs.getInt(1)+"/");
					insertPath(rs.getInt(1),stuff_path);
				}
			}
			//stuff_path.insert(0, "/");
		} catch (Exception e) {
			logger.error("设置资源的路径出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 设置资源的路径
	 * @param stuffLib
	 * @return
	 * @throws ElException
	 */
	public String setStuffPath(int stuffId) throws ElException {
		StringBuffer stuff_path=new StringBuffer(stuffId+"");
		this.insertPath(stuffId, stuff_path);
		stuff_path.insert(0, "/");
		return stuff_path.toString();
	}

	public StuffLib getStuffFolderTree() throws ElException {
		StuffLib q = new StuffLib(0, "根");
		q.setChilds(listFolder(0, 1));
		return q;
	}
	/**
	 * 获取资源树所有id
	 * @param stuffTree
	 * @return
	 * @throws ElException
	 */
	public String getStuffIds(StuffLib stuffTree) throws ElException {
		StringBuffer stuffIds=new StringBuffer("");
		if(stuffTree!=null&&stuffTree.getChilds()!=null){
			for (int i = 0; i < stuffTree.getChilds().size(); i++) {
				stuffIds.append(stuffTree.getChilds().get(i).getId()+",");//添加到string
				getStuffIds(stuffTree.getChilds().get(i),stuffIds);
			}
			if(stuffIds.indexOf(",")>0){
				stuffIds.deleteCharAt(stuffIds.length()-1);
			}
		}
		return stuffIds.toString();
	}
	/**
	 * 递归获取资源树所有id
	 * @param stuffChilds
	 * @param stuffIds
	 * @throws ElException
	 */
	private void getStuffIds(StuffLib stuffChilds,StringBuffer stuffIds) throws ElException {
		if(stuffChilds!=null&&stuffChilds.getChilds()!=null){
			for (int i = 0; i < stuffChilds.getChilds().size(); i++) {
				stuffIds.append(stuffChilds.getChilds().get(i).getId()+",");//添加到string
				getStuffIds(stuffChilds.getChilds().get(i),stuffIds);
			}
		}
	}

	public StuffLib getStuffFolderTree(int userid) throws ElException {
		//StuffLib q = new StuffLib(0, "被分配的文件夹");
		StuffLib q = new StuffLib(ElConstants.USER_OP_LIB, "被分配的文件夹");
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<StuffLib> qss = new ArrayList<StuffLib>();
		try {
			ct = DBConnection.getConnection();

			ps = ct
					.prepareStatement("select qs.id,qs.title,qs.description,qs.fileext,qs.modifytime,qs.createtime,qs.length ,qs.type,qs.onwer from question_stuff qs left join QUESTION_STUFF_USE_TYPE qsu on qsu.stuffid = qs.id where qsu.userid = ? and qs.type = 5 order by qs.createtime desc");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				StuffLib qs = new StuffLib(rs.getInt(1), rs.getString(2));
				qs.setDescription(rs.getString(3));
				qs.setFileext(rs.getString(4));
				qs.setModifytime(rs.getTimestamp(5));
				qs.setCreatetime(rs.getTimestamp(6));
				qs.setLength(rs.getLong(7));
				qs.setType(rs.getInt(8));
				qs.setLevel(1);
				if (qs.getType() == 5) {
					qs.setChilds(listFolder(qs.getId(), 2));
				}
				qs.setOwner(new ELUser(rs.getInt(9)));
				qs.setParent(q);
				qss.add(qs);
			}
		} catch (Exception e) {
			logger.error("获取资料出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		q.setChilds(qss);
		return q;
	}

	public List<StuffLib> listFolder(int parentid, int level)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<StuffLib> qss = new ArrayList<StuffLib>();
		try {
			ct = DBConnection.getConnection();

			ps = ct
					.prepareStatement("select qs.id,qs.title,qs.description,qs.fileext,qs.modifytime,qs.createtime,qs.length ,qs.type,qs.onwer from question_stuff qs where qs.parentid = ? and qs.type = 5 order by qs.createtime desc");
			ps.setInt(1, parentid);
			rs = ps.executeQuery();
			while (rs.next()) {
				StuffLib qs = new StuffLib(rs.getInt(1), rs.getString(2));
				qs.setDescription(rs.getString(3));
				qs.setFileext(rs.getString(4));
				qs.setModifytime(rs.getTimestamp(5));
				qs.setCreatetime(rs.getTimestamp(6));
				qs.setLength(rs.getLong(7));
				qs.setType(rs.getInt(8));
				qs.setLevel(level);
				if (qs.getType() == 5) {
					qs.setChilds(listFolder(qs.getId(), level + 1));
				}
				qs.setOwner(new ELUser(rs.getInt(9)));
				qs.setParent(new StuffLib(parentid, ""));
				qss.add(qs);
			}
		} catch (Exception e) {
			logger.error("获取资料出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return qss;
	}

	public StuffLib listFolderShared() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<StuffLib> qss = new ArrayList<StuffLib>();
		StuffLib q = new StuffLib(0, "共享文件夹");
		try {
			ct = DBConnection.getConnection();

			ps = ct
					.prepareStatement("select qs.id,qs.title,qs.description,qs.fileext,qs.modifytime,qs.createtime,qs.length ,qs.type,qs.onwer from question_stuff qs where qs.shared = 1 and qs.type = 5 order by qs.createtime desc");
			rs = ps.executeQuery();
			while (rs.next()) {
				StuffLib qs = new StuffLib(rs.getInt(1), rs.getString(2));
				qs.setDescription(rs.getString(3));
				qs.setFileext(rs.getString(4));
				qs.setModifytime(rs.getTimestamp(5));
				qs.setCreatetime(rs.getTimestamp(6));
				qs.setLength(rs.getLong(7));
				qs.setType(rs.getInt(8));
				qs.setLevel(1);
				if (qs.getType() == 5) {
					qs.setChilds(listFolder(qs.getId(), 2));
				}
				qs.setOwner(new ELUser(rs.getInt(9)));
				qs.setParent(new StuffLib());
				qss.add(qs);
			}
		} catch (Exception e) {
			logger.error("获取资料出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		q.setChilds(qss);
		return q;
	}

	public List<StuffLib> getStuffs(StuffLib stuff, int userid, int pageNow,
			int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<StuffLib> qss = new ArrayList<StuffLib>();
		try {
			ct = DBConnection.getConnection();
			String name = (stuff == null) ? "" : stuff.getTitle() == null ? ""
					: stuff.getTitle().trim();
			int type = (stuff == null) ? 0 : stuff.getType();
			boolean bytype = type == 0 ? false : true;
			if (!bytype) {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(QuestionConstants.STUFF_QUERY_LIST_BYNAME));
				ps.setInt(1, userid);
				ps.setString(2, "%" + name + "%");
				ps.setInt(3, pageNow);
				ps.setInt(4, pageSize);
			} else {
				ps = ct
						.prepareStatement(ElQuerySql
								.getSQL(QuestionConstants.STUFF_QUERY_LIST_BYNAME_TYPE));
				ps.setInt(1, userid);
				ps.setString(2, "%" + name + "%");
				ps.setInt(3, type);
				ps.setInt(4, pageNow);
				ps.setInt(5, pageSize);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				StuffLib qs = new StuffLib(rs.getInt(1), rs.getString(2));
				qs.setDescription(rs.getString(3));
				qs.setFileext(rs.getString(4));
				qs.setModifytime(rs.getTimestamp(5));
				qs.setCreatetime(rs.getTimestamp(6));
				// long length = 0;
				// try {
				// length = new Long(rs.getString(7));
				// } catch (Exception e) {
				// }
				// qs.setLength(length);
				qs.setLength(rs.getLong(7));
				// qs.setType(rs.getInt(8));
				qss.add(qs);
			}
		} catch (Exception e) {
			logger.error("获取资料出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return qss;
	}

	public List<StuffLib> listStuffs(StuffLib stuff, String order, String ot)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<StuffLib> qss = new ArrayList<StuffLib>();
		try {
			ct = DBConnection.getConnection();
			
			
			int parentid = (stuff == null) ? 0 // : stuff.getParent() == null ?
					// 0
					: stuff.getId();
			if (order == null) {
				order = "qs.createtime ";
			} else {
				order = "qs.title ";
			}
			if (ot == null)
				ot = "asc";
			else if (ot.equals("up"))
				ot = "asc";
			else
				ot = "desc";
			
			ps = ct
					.prepareStatement("select qs.id,qs.title,qs.description,qs.fileext,qs.modifytime,qs.createtime,qs.length ,qs.type,qs.onwer,qs.status from question_stuff qs where qs.parentid = ?  order by qs.type desc,"
							+ order + " " + ot);
			ps.setInt(1, parentid);
			rs = ps.executeQuery();
			while (rs.next()) {
				StuffLib qs = new StuffLib(rs.getInt(1), rs.getString(2));
				qs.setDescription(rs.getString(3));
				qs.setFileext(rs.getString(4));
				qs.setModifytime(rs.getTimestamp(5));
				qs.setCreatetime(rs.getTimestamp(6));
				qs.setLength(rs.getLong(7));
				qs.setType(rs.getInt(8));
				qs.setOwner(new ELUser(rs.getInt(9)));
				qs.setStatus(rs.getInt(10));
				qss.add(qs);
			}
		} catch (Exception e) {
			logger.error("获取资料出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return qss;
	}
	/**
	 * 查询文件中的所有指定图片资源
	 * @param stuff
	 * @param order
	 * @param ot
	 * @param imgfexts
	 * @return
	 * @throws ElException
	 */
	public List<StuffLib> listStuffs(StuffLib stuff, String order, String ot,String imgfexts)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<StuffLib> qss = new ArrayList<StuffLib>();
		String[] sqlw=new String[0];
		if(imgfexts!=null&&!"".equals(imgfexts)){
			sqlw=imgfexts.split(",");
		}
		String sqlw_="";
		if(sqlw!=null&&!"".equals(sqlw)){
			sqlw_="and (";
			for (int i = 0; i < sqlw.length; i++) {
				if(i==0){
					sqlw_+=" fileext='"+sqlw[i]+"' ";
				}else{
					sqlw_+=" or fileext='"+sqlw[i]+"' ";
				}
			}
			sqlw_+=")";
		}
		try {
			ct = DBConnection.getConnection();
			int parentid = (stuff == null) ? 0 // : stuff.getParent() == null ?
					// 0
					: stuff.getId();
			if (order == null) {
				order = "qs.createtime ";
			} else {
				order = "qs.title ";
			}
			if (ot == null)
				ot = "asc";
			else if (ot.equals("up"))
				ot = "asc";
			else
				ot = "desc";
		
			ps = ct
					.prepareStatement("select qs.id,qs.title,qs.description,qs.fileext,qs.modifytime,qs.createtime,qs.length ,qs.type,qs.onwer from question_stuff qs where qs.parentid = ? "+sqlw_+" order by qs.type desc,"
							+ order + " " + ot);
			ps.setInt(1, parentid);
			rs = ps.executeQuery();
			while (rs.next()) {
				StuffLib qs = new StuffLib(rs.getInt(1), rs.getString(2));
				qs.setDescription(rs.getString(3));
				qs.setFileext(rs.getString(4));
				qs.setModifytime(rs.getTimestamp(5));
				qs.setCreatetime(rs.getTimestamp(6));
				qs.setLength(rs.getLong(7));
				qs.setType(rs.getInt(8));
				qs.setOwner(new ELUser(rs.getInt(9)));
				qss.add(qs);
			}
		} catch (Exception e) {
			logger.error("获取资料出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return qss;
	}
	/**
	 * 获取资源在文件夹中的位置
	 * @param stuffId
	 * @param imgfexts
	 * @return
	 * @throws ElException
	 */
	public int getStuffLoca(int stuffId,int stuffpId,String imgfexts) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String[] sqlw=new String[0];
			if(imgfexts!=null&&!"".equals(imgfexts)){
				sqlw=imgfexts.split(",");
			}
			String sqlw_="";
			if(sqlw!=null&&!"".equals(sqlw)){
				sqlw_="and (";
				for (int i = 0; i < sqlw.length; i++) {
					if(i==0){
						sqlw_+=" fileext='"+sqlw[i]+"' ";
					}else{
						sqlw_+=" or fileext='"+sqlw[i]+"' ";
					}
				}
				sqlw_+=")";
			}
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select rn from (select t.*,rownum rn from ("+
					" select * from question_stuff qs where qs.parentid=? "+sqlw_+" order by qs.title desc"+
					" ) t where rownum<=10000 ) where rn>=0 and id=?");
			ps.setInt(1, stuffpId);
			ps.setInt(2, stuffId);
			rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取资料出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<StuffLib> listMyStuffs(StuffLib stuff, int userid)
			throws ElException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setStuffShared(int stuffid, int shared) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update question_stuff set shared = ? where id = ?");
			ps.setInt(1, shared);
			ps.setInt(2, stuffid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 修改文件夹大小
	 */
	public void setStuffsize(int stuffid, long size) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update question_stuff set modifytime=sysdate,length = ? where id = ?");
			ps.setLong(1, size);
			ps.setInt(2, stuffid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public long getStuffParentSize(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		long s = 0l;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select p.id,p.length from question_stuff p left join question_stuff c on c.parentid =p.id where c.id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getInt(1) == 0)
					s = -1l;
				else
					s = rs.getLong(2);
			}
		} catch (Exception e) {
			logger.error("获取文件夹父亲大小！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return s;
	}

	public long getStuffChildsSize(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		long s = 0l;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select sum(length) from question_stuff where parentid = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				if(id==0)
					s  = -1l;
				else
				s = rs.getLong(1);
			}
		} catch (Exception e) {
			logger.error("获取文件实际大小失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return s;
	}
	public int getStuffOpStatus(int stuffid, int userid, int roleid,int type)
			throws ElException {//type 1 检测创建文件夹。2 设置文件夹大小
		StuffLib qstuff = getStuffbyId(stuffid, 0);
		
		if(type==1&&(qstuff.getId()==0)&&roleid!=1){
			return 1;//非超级管理员不能再根目录创建文件夹。	
		}
		if(type==2){
			if(qstuff.getOwner()!=null&&qstuff.getOwner().getId()==userid){
				return 0;
			}else
			{
				return 2;
			}
		}
		return 0;
	}
	public int getStuffsCount(StuffLib stuff, int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String name = (stuff == null) ? "" : stuff.getTitle() == null ? ""
					: stuff.getTitle().trim();
			int type = (stuff == null) ? 0 : stuff.getType();
			boolean bytype = type == 0 ? false : true;
			if (!bytype) {
				ps = ct
						.prepareStatement(ElQuerySql
								.getSQL(QuestionConstants.STUFF_QUERY_LIST_BYNAME_SIZE));
				ps.setInt(1, userid);
				ps.setString(2, "%" + name + "%");
			} else {
				ps = ct
						.prepareStatement(ElQuerySql
								.getSQL(QuestionConstants.STUFF_QUERY_LIST_BYNAME_TYPE_SIZE));
				ps.setInt(1, userid);
				ps.setString(2, "%" + name + "%");
				ps.setInt(3, type);
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取资料出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public void addStuffOpusers(int userid, int stuffid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into question_stuff_use_type(userid,stuffid) values(?,?)");
			ps.setInt(1, userid);
			ps.setInt(2, stuffid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public boolean checkStuffOpUsers(int userid, int stuffid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from question_stuff_use_type where userid = ? and stuffid = ?");
			ps.setInt(1, userid);
			ps.setInt(2, stuffid);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;

	}

	public void deleteStuffOpusers(int userid, int stuffid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("delete from question_stuff_use_type where userid = ? and stuffid = ?");
			ps.setInt(1, userid);
			ps.setInt(2, stuffid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public List<ELUser> getStuffOpUsers(int stuffid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> us = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select eu.id,eu.realname,eu.username from question_stuff_use_type du left join eluser eu on eu.id = du.userid where du.stuffid = ?");
			ps.setInt(1, stuffid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser user = new ELUser(rs.getInt(1), rs.getString(2));
				if (user.getRealname() == null || "".equals(user.getRealname()))
					user.setRealname(rs.getString(3));
				us.add(user);
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return us;
	}

	public void alter(StuffLib qs) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.STUFF_ALTER));
			ps.setString(1, qs.getTitle());
			ps.setString(2, qs.getDescription());
			ps.setInt(3, qs.getType());
			ps.setInt(4, qs.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("修改资料出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void deleteQs(int id, int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			if (userid != 0) {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(QuestionConstants.STUFF_DELETE));
				ps.setInt(1, id);
				ps.setInt(2, userid);
			} else {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(QuestionConstants.STUFF_DELETE_BYID));
				ps.setInt(1, id);

			}
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("修改删除出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 删除用户可使用的权限
	 */
	public void deleteStuffUseusers(int userId) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" delete from question_stuff_use_type where userid= ?");
			ps.setInt(1, userId);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除用户权限出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public void checkStuffParams(StringBuffer sql,StuffQuery stuffQuery,Vector<Object> params){
		String[] keys = null;
		String sqlAppend = "";
		if(stuffQuery!=null){
			if(stuffQuery.getTitle()!=null&&!"".equals(stuffQuery.getTitle().trim())){
				sql.append(" and title like ? ");
				params.add("%"+StringUtil.toLikeStr(stuffQuery.getTitle().trim())+"%");
			}
			if(stuffQuery.getStuffSizeStart()>0){
				sql.append(" and length>? ");
				params.add(stuffQuery.getStuffSizeStart()*1024*1024);
			}
			if(stuffQuery.getStuffSizeEnd()>0){
				sql.append(" and length<? ");
				params.add(stuffQuery.getStuffSizeEnd()*1024*1024);
			}
			if(stuffQuery.getCreateTimeStart()!=null){
				sql.append(" and createtime>?");
				params.add(stuffQuery.getCreateTimeStart());
			}
			if(stuffQuery.getCreateTimeEnd()!=null){
				sql.append(" and createtime<?");
				params.add(stuffQuery.getCreateTimeEnd());
			}
			if(stuffQuery.getModifyTimeStart()!=null){
				sql.append(" and modifytime>?");
				params.add(stuffQuery.getModifyTimeStart());
			}
			if(stuffQuery.getModifyTimeEnd()!=null){
				sql.append(" and modifytime<?");
				params.add(stuffQuery.getModifyTimeEnd());
			}
			if(stuffQuery.getStuffExt()!=null&&!"".equals(stuffQuery.getStuffExt().trim())){
				sql.append(" and fileext in ("+"'"+stuffQuery.getStuffExt()+"'"+")");
			//	params.add(stuffQuery.getStuffExt().trim());
			}
			if(stuffQuery.getParentid()>0){
				sql.append(" and parentid=?");
				params.add(stuffQuery.getParentid());
			}
			if(stuffQuery.getKey()!=null&&!stuffQuery.getKey().equals("")){
				keys = stuffQuery.getKey().split(" ");
				if(keys!=null && keys.length>0){
					sqlAppend = sqlAppend + " and ";
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
				sql.append(sqlAppend);
				params.add(stuffQuery.getKey().trim());
			}
		}
	}
	
	/**
	 * 搜索资源
	 * @param stuffIds
	 * @param stuffQuery
	 * @return
	 * @throws ElException
	 */
	public List<StuffLib> listSeachStuffs(String stuffIds,StuffQuery stuffQuery) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<StuffLib> qss = new ArrayList<StuffLib>();
		String tempSql="";
		if(stuffIds!=null){
			tempSql=" where id in("+stuffIds+") or parentid in("+stuffIds+") ";
		}
		try {
			Vector<Object> params=new Vector<Object>();
			StringBuffer sql=new StringBuffer("select * from ("+
					" select qs.id,qs.title,qs.description,qs.fileext,qs.modifytime,qs.createtime,qs.length ,qs.type,qs.onwer,qs.status,qs.key,qs.parentid from question_stuff qs " +
					tempSql+"  order by modifytime desc"+
					" ) t where 1=1");
			this.checkStuffParams(sql, stuffQuery, params);
			ct = DBConnection.getConnection();
			
			int stop = params.size();
			if(stuffQuery!=null&&stuffQuery.getKey()!=null&&!stuffQuery.getKey().equals("")){
				stop--;
			}
			ps = ct.prepareStatement(sql.toString());
			for (int i = 0; i < stop; i++) {
				ps.setObject(i+1, params.get(i));
			}
			System.out.println(sql.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				StuffLib qs = new StuffLib(rs.getInt(1), rs.getString(2));
				qs.setDescription(rs.getString(3));
				qs.setFileext(rs.getString(4));
				qs.setModifytime(rs.getTimestamp(5));
				qs.setCreatetime(rs.getTimestamp(6));
				qs.setLength(rs.getLong(7));
				qs.setType(rs.getInt(8));
				qs.setOwner(new ELUser(rs.getInt(9)));
				qs.setStuff_path(this.setStuffPath(qs.getId()));
				qs.setStatus(rs.getInt(10));
				qs.setKey(rs.getString(11));
				qss.add(qs);
			}
		} catch (Exception e) {
			logger.error("搜索资源出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return qss;
	}
	
	public List<StuffLib> listSeachStuffs(String stuffIds,StuffQuery stuffQuery,int pagenow,int pagesize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<StuffLib> qss = new ArrayList<StuffLib>();
		String tempSql="";
		if(stuffIds!=null){
			tempSql=" where id in("+stuffIds+") or parentid in("+stuffIds+") ";
		}
		try {
			Vector<Object> params=new Vector<Object>();
			StringBuffer sql=new StringBuffer("select * from (select t.*,rownum rn from ("+
					" select qs.id,qs.title,qs.description,qs.fileext,qs.modifytime,qs.createtime,qs.length ,qs.type,qs.onwer,qs.status,qs.key,qs.parentid from question_stuff qs " +
					tempSql+""+
					"  where 1=1 ");
			this.checkStuffParams(sql, stuffQuery, params);
			ct = DBConnection.getConnection();
			
			int stop = params.size();
			if(stuffQuery!=null&&stuffQuery.getKey()!=null&&!stuffQuery.getKey().equals("")){
				stop--;
			}
			sql.append(" order by modifytime desc)t where rownum <=?)where rn>=?");
			ps = ct.prepareStatement(sql.toString());
			for (int i = 0; i < stop; i++) {
				ps.setObject(i+1, params.get(i));
			}
			ps.setInt(stop+1, pagenow);
			ps.setInt(stop+2, pagesize);
			System.out.println(sql.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				StuffLib qs = new StuffLib(rs.getInt(1), rs.getString(2));
				qs.setDescription(rs.getString(3));
				qs.setFileext(rs.getString(4));
				qs.setModifytime(rs.getTimestamp(5));
				qs.setCreatetime(rs.getTimestamp(6));
				qs.setLength(rs.getLong(7));
				qs.setType(rs.getInt(8));
				qs.setOwner(new ELUser(rs.getInt(9)));
				qs.setStuff_path(this.setStuffPath(qs.getId()));
				qs.setStatus(rs.getInt(10));
				qs.setKey(rs.getString(11));
				qss.add(qs);
			}
		} catch (Exception e) {
			logger.error("搜索资源出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return qss;
	}
	public int listSeachStuffsSize(String stuffIds,StuffQuery stuffQuery) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String tempSql="";
		if(stuffIds!=null){
			tempSql=" where id in("+stuffIds+") or parentid in("+stuffIds+") ";
		}
		try {
			Vector<Object> params=new Vector<Object>();
			StringBuffer sql=new StringBuffer("select count(*) from ("+
					" select qs.id,qs.title,qs.description,qs.fileext,qs.modifytime,qs.createtime,qs.length ,qs.type,qs.onwer,qs.status,qs.key,qs.parentid from question_stuff qs " +
					tempSql+"  order by modifytime desc"+
					" ) t where 1=1");
			this.checkStuffParams(sql, stuffQuery, params);
			ct = DBConnection.getConnection();
			
			int stop = params.size();
			if(stuffQuery!=null&&stuffQuery.getKey()!=null&&!stuffQuery.getKey().equals("")){
				stop--;
			}
			ps = ct.prepareStatement(sql.toString());
			for (int i = 0; i < stop; i++) {
				ps.setObject(i+1, params.get(i));
			}
			System.out.println(sql.toString());
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("搜索资源出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	
	/**
	 * 检测用户是否具有删除资源的权限（判断该用户所管理的部门是否包含资源创建者的所在部门）
	 * @param userId
	 * @param qstuff
	 * @throws ElException
	 */
	public boolean checkUserIsdelStuff(int userid,StuffLib qstuff) throws ElException {
		//获取用户管理的部门树
		DepartmentDao departmentDao=(DepartmentDao)SpringContextUtil.getBean("departmentDao");
		Department depTree = departmentDao.getDepTree_level1(userid, "op", -1,true);
		//获取资源创建者的信息
		UserDao userDao=(UserDao)SpringContextUtil.getBean("userDao");
		ELUser eluser=userDao.getUserById(qstuff.getOwner().getId());
		ElNodeSQL nodeSql=(ElNodeSQL)SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL);
		if(nodeSql.checkNode(eluser.getDepartment().getId(), depTree, "department")){
			return true;
		}
		return false;
	}
	/**
	 * 检测用户是否具有创建文件夹的权限
	 * @param userid
	 * @param qstuff
	 * @return
	 * @throws ElException
	 */
	public boolean checkUserIsaddStuff(int userid,int stuffId) throws ElException {
		StuffLib stuffTree=this.getStuffFolderTree(userid);
		for (int i = 0; i < stuffTree.getChilds().size(); i++) {
			StuffLib tempStuff=stuffTree.getChilds().get(i);
			if(tempStuff.getId()==stuffId){
				return true;
			}else{
				if(tempStuff.getChilds()!=null&&tempStuff.getChilds().size()>0){
					if(checkUserIsaddStuffChild(stuffTree,stuffId)){
						return true;
					}
				}
			}
		}
		return false;
	}
	/**
	 * 递归判断用户是否具有创建文件夹的权限
	 * @param stuffTree
	 * @param stuff
	 * @return
	 * @throws ElException
	 */
	public boolean checkUserIsaddStuffChild(StuffLib stuffTree,int stuffId) throws ElException {
		for (int i = 0; i < stuffTree.getChilds().size(); i++) {
			StuffLib tempStuff=stuffTree.getChilds().get(i);
			if(tempStuff.getId()==stuffId){
				return true;
			}else{
				if(tempStuff.getChilds()!=null&&tempStuff.getChilds().size()>0){
					if(checkUserIsaddStuffChild(tempStuff,stuffId)){
						return true;
					}
				}
			}
		}
		return false;
	}
	/**
	 * 检测资源有没有被共享
	 * @param qstuff
	 * @return
	 * @throws ElException
	 */
	public boolean checkStuffIsShared(int qstuffId) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id,shared from question_stuff where id=?");
			ps.setInt(1, qstuffId);
			rs = ps.executeQuery();
			if(rs.next()) {
				if(rs.getInt(2)==1){
					return true;
				}else{
					return checkStuffParentShared(qstuffId);
				}
			}
		} catch (Exception e) {
			logger.error("检测资源有没有被共享出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}
	/**
	 * 检测资源的父节点有没有被共享
	 * @param qstuff
	 * @return
	 * @throws ElException
	 */
	private boolean checkStuffParentShared(int qstuffId) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id,shared from question_stuff where id in(select parentid from question_stuff qsf where id=?)");
			ps.setInt(1, qstuffId);
			rs = ps.executeQuery();
			if(rs.next()) {
				if(rs.getInt(2)==1){
					return true;
				}else{
					return checkStuffParentShared(rs.getInt(1));
				}
			}
		} catch (Exception e) {
			logger.error("检测资源的父节点有没有被共享出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}
	/**
	 * 设置资源的所有子节点的共享状态
	 * @param qstuffId
	 * @return
	 * @throws ElException
	 */
	public void updateStuffChildShared(int qstuffId,int status) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id from question_stuff where parentid=?");
			ps.setInt(1, qstuffId);
			rs = ps.executeQuery();
			while(rs.next()) {
				updateStuffChildShared(rs.getInt(1), status);
			}
			setStuffShared(qstuffId, status);
		} catch (Exception e) {
			logger.error("设置资源的所有子节点的共享状态出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	/**
	 * 更新素材的状态
	 * @param qstuffId
	 * @param status
	 * @throws ElException
	 */
	public void updateStuffStatus(int qstuffId,int status) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update question_stuff set status=? where id=?");
			ps.setInt(1, status);
			ps.setInt(2, qstuffId);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新素材的状态出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public int checkStuffForMonth(String month,int eluserid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int id = -1;
		StuffLib qstuff = null;
		try {
			ct = DBConnection.getConnection();
			sql = "select id from question_stuff where title = '" + month + "'";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()){
				id = rs.getInt(1);
			}else {
				qstuff = new StuffLib();
				qstuff.setTitle(month);
				qstuff.setOwner(new ELUser(eluserid));
				qstuff.setLength(100 * 1024 * 1024L);
				qstuff.setParent(new StuffLib(0));
				qstuff.setType(5);
				id = this.addQstuff(qstuff);
			}

		} catch (Exception e) {
			logger.error("没有节点，创建节点；有节点，获取节点失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return id;
	}

	public List<StuffLib> listStuffs(StuffLib stuff, String form)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		List<StuffLib> stuffs = new ArrayList<StuffLib>();
		try {
			ct = DBConnection.getConnection();
			sql = "select * from (select t.*,rownum rn from(select * from QUESTION_STUFF where fileext in ("+form+") order by id desc)t where rownum<=3) where rn>=0";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				StuffLib stl = new StuffLib();
				stl.setId(rs.getInt(1));
				stl.setTitle(rs.getString(2));
				stl.setDescription(rs.getString(3));
				stl.setFileext(rs.getString(4));
				stl.setOwner(new ELUser(rs.getInt(5)));
				stl.setCreatetime(rs.getDate(7));
				stl.setKey(rs.getString("key"));
				stl.setFileinfo(rs.getString("fileinfo"));
				stl.setStuffpic(rs.getString("stuffpic"));
				stuffs.add(stl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return stuffs;
	}

	public List<StuffLib> listStuffs(int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<StuffLib> stuffs = new ArrayList<StuffLib>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from (select t.*,rownum rn from (select * from QUESTION_STUFF order by id desc)t where rownum<=?) where rn>=?");
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while(rs.next()){
				StuffLib stl = new StuffLib();
				stl.setId(rs.getInt(1));
				stl.setTitle(rs.getString(2));
				stl.setDescription(rs.getString(3));
				stl.setFileext(rs.getString(4));
				stl.setOwner(new ELUser(rs.getInt(5)));
				stl.setCreatetime(rs.getDate(7));
				stl.setKey(rs.getString("key"));
				stl.setFileinfo(rs.getString("fileinfo"));
				stl.setStuffpic(rs.getString("stuffpic"));
				stuffs.add(stl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return stuffs;
	}

	public int getStuffId(int id, int num,String form) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			if(num==1){
				sql = "select * from ( select t.*,rownum rn from (select id from QUESTION_STUFF where id < ? and fileext in ("+form+") order by id desc)t where rownum>=1) where rn<=1";
			}else{
				sql = "select * from ( select t.*,rownum rn from (select id from QUESTION_STUFF where id > ? and fileext in ("+form+") order by id asc)t where rownum>=1) where rn<=1";
			}
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void updateStuffJpg(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(QuestionConstants.STUFF_ALTER_JPG));
			ps.setInt(1, 1);
			ps.setInt(2, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新stuff的generatejpg字段！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public List<Map<String, Object>> listJpgIds() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql.getSQL(QuestionConstants.STUFF_QUERY_LIST_JPG));
			rs = ps.executeQuery();
			while(rs.next()){
				map = new HashMap<String, Object>();
				map.put("id", rs.getInt(1));
				map.put("title", rs.getString(2));
				list.add(map);
			}
				
		} catch (Exception e) {
			logger.error("获取所有转换缩略图的office文件在数据库中的id和title！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	public int getFromchange(int stuffid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int fromchange = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql.getSQL(QuestionConstants.STUFF_QUERY_FROMCHANGE_BY_ID));
			ps.setInt(1, stuffid);
			rs = ps.executeQuery();
			if(rs.next()){
				fromchange = rs.getInt(1);
			}
				
		} catch (Exception e) {
			logger.error("根据stuffid获取fromchange属性！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fromchange;
	}
}
