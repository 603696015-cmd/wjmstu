package com.sopia.lable.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElQuerySql;
import com.sopia.common.OracleBlob;
import com.sopia.forumman.dao.impl.ForumAdminDaoImpl;
import com.sopia.lable.dao.ModeBindDao;
import com.sopia.lable.entites.Mode;
import com.sopia.lable.entites.Template;
import com.sopia.lable.entites.TreeNode;
import com.sopia.questionman.QuestionConstants;
import com.sopia.questionman.entities.ExamPaperLib;

public class ModeBindDaoImpl  implements  ModeBindDao{
	private static final Log logger = LogFactory.getLog(ForumAdminDaoImpl.class);

	public List<Mode> Mode_getallmode(int pageNow,int pageSize) throws ElException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	
		List<Mode>  modeList = null;
		
		try{			
			String sql =" select * from (select t.*, rownum rn from ( select mi.id,mi.name, mi.tablename,mi.typename  as  mitypename,(1) as qubie , (select mu.filename from mode_bind mb left join mode_updload mu " +
			"on mu.id = mb.bindpage where mb.bindtype = 1 and mb.modetype = 1 and mb.modeid = mi.id),(select mu.filename from mode_bind mb " +
			"left join mode_updload mu on mu.id = mb.bindpage where mb.bindtype = 3 and mb.modetype = 1 and mb.modeid = mi.id) from mode_info mi "+
			"union all "+
			"select mi.id,mi.modulename,mi.tablename,('无') as  mitypename,(2) as qubie,(select mu.filename from mode_bind mb left join mode_updload mu on mu.id = mb.bindpage" +
			" where mb.bindtype = 1 and mb.modetype = 2 and mb.modeid = mi.id),(select mu.filename from mode_bind mb left join mode_updload mu " +
			" on mu.id = mb.bindpage where mb.bindtype = 3 and mb.modetype = 2  and mb.modeid = mi.id)  from TB_MODULE_MANAGE mi " +
			" )t where rownum <= ? ) where rn>=?  ";
				ct= DBConnection.getConnection();
				ps=ct.prepareStatement(sql);	
				ps.setInt(1,pageNow );
				ps.setInt(2, pageSize);
				rs=ps.executeQuery();	
				while(rs.next()){
					if(modeList==null){
						modeList= new ArrayList<Mode>();
					}
					Mode  m = new Mode();
					m.setId(rs.getInt(1));
					m.setName(rs.getString(2));
					m.setTableName(rs.getString(3));
					m.setTypeid(rs.getInt(5));
					if(rs.getInt(5)==1){
						m.setTypetableName(rs.getString(4));
					}
					m.setModeJspName(rs.getString(6));
					m.setModeContentJspName(rs.getString(7));
					modeList.add(m);
				}
				return  modeList;
		} catch (Exception e) {
			logger.error("查询系统模块和自定义模块 及绑定信息失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}
	public int Mode_getallmodecount() throws ElException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	
		int a = 0;
		
		try{			
				String sql =" select sum(c) from ( select count(1) as c from mode_info mi "+
						" union all " +
						" select count(1) as c from TB_MODULE_MANAGE mi) ";
				ct= DBConnection.getConnection();
				ps=ct.prepareStatement(sql);	
				rs=ps.executeQuery();	
				if(rs.next())  a = rs.getInt(1);
				return  a;
		} catch (Exception e) {
			logger.error("查询系统模块和自定义模块 及绑定信息失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}
	public  Mode   Mode_getmodebyIDandType(int modeid,int modetype,int type) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	
		Mode  mode =null;
		try{
			String  sql="";
		if(modetype==2){
			sql ="select mi.id,mi.modulename,mu.filename,mb.id,mi.tablename,mb.bindpage from TB_MODULE_MANAGE mi left join mode_bind mb on  " +
			" mi.id=mb.modeid left join  mode_updload  mu  on mu.id=mb.bindpage  where  mi.id=? and mb.bindtype=? and mb.modetype=? ";
		}else{
			sql="select mi.id,mi.name,mu.filename,mb.id,mi.tablename,mb.bindpage from mode_info mi left join mode_bind mb on  " +
			" mi.id=mb.modeid left join  mode_updload  mu  on mu.id=mb.bindpage  where  mi.id=? and mb.bindtype=? and mb.modetype=? ";
		}
		
		ct= DBConnection.getConnection();
		ps=ct.prepareStatement(sql);
		ps.setInt(1,modeid );
		ps.setInt(2, type);
		ps.setInt(3, modetype);
		rs=ps.executeQuery();	
		if(rs.next())  {
			mode = new Mode();
			mode.setId(rs.getInt(1));
			mode.setName(rs.getString(2));
			mode.setModeJspName(rs.getString(3));
			mode.setBindid(rs.getInt(4));
			mode.setTableName(rs.getString(5));
			mode.setBindtypeid(type);
			mode.setTypeid(modetype);
			mode.setModeJspid(rs.getInt(6));
		}else{
			//查询模块表 ，是否存在该模块
			if(modetype==2){
				sql="select id,modulename,tablename from TB_MODULE_MANAGE where  id=? ";
			}else{
				sql="select id,name,tablename from mode_info where  id=? ";
				
			}
			ps=ct.prepareStatement(sql);
			ps.setInt(1, modeid);
			rs=ps.executeQuery();	
			if(rs.next()){
				mode = new Mode();
				mode.setId(rs.getInt(1));
				mode.setName(rs.getString(2));
				mode.setTableName(rs.getString(3));
				sql="insert into mode_bind(bindtype,modeid,modetype) values(?,?,?) ";
				ps=ct.prepareStatement(sql);
				ps.setInt(1, type);
				ps.setInt(2, modeid);
				ps.setInt(3, modetype);
				mode.setBindtypeid(type);
				mode.setTypeid(modetype);
				ps.executeUpdate();	
				ps = ct.prepareStatement("select mode_bind_SEQUENCE.currval from dual ");
				rs = ps.executeQuery();  
				if (rs.next()){
					mode.setBindid(rs.getInt(1)); 
				}				
			}
		}
		return   mode;
		} catch (Exception e) {
			logger.error("查询绑定信息失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	
		
	}
	public  List<Template> mode_modepageList(int pageNow,int pageSize,String filename) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	
		List<Template>  tList=null;
		try{
		
		String  sql ="select * from ( select t.* , rownum rn from( select id, filename,truefilename from mode_updload where truefilename in ("+filename+") )t where rownum <= ? ) where rn >= ?";
		ct= DBConnection.getConnection();
		ps=ct.prepareStatement(sql);	
		ps.setInt(1,  pageNow);
		ps.setInt(2,  pageSize);
		rs=ps.executeQuery();	
		while(rs.next())  {
			if(tList==null) tList = new ArrayList<Template>();
			Template  t= new Template();
			t.setId(rs.getInt(1));
			t.setName(rs.getString(2));
			t.setTrueName(rs.getString(3));
			tList.add(t);
			}
		return   tList;
		} catch (Exception e) {
			logger.error("查询模板信息失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	
		
		
		
	}
	public  int mode_modepageListcount(String filename) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	
		int a=0;
		try{
		
			String  sql ="select count(1) from( select id, filename,truefilename from mode_updload where truefilename in ("+filename+")) ";
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);	
			rs=ps.executeQuery();	
			if(rs.next()) a = rs.getInt(1);
			return   a;
		} catch (Exception e) {
			logger.error("查询模板信息失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public void mode_updmode_bind(int tid,int bid) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	
		try{
			String  sql ="update  mode_bind set bindpage=? where id=?";
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			ps.setInt(1,  tid);
			ps.setInt(2,  bid);
			ps.executeUpdate();	
			
		} catch (Exception e) {
			logger.error("修改模板信息失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	public void mode_removemode_bind(int bid) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	
		try{
			String  sql ="update  mode_bind set bindpage='' where id=?";
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			ps.setInt(1,  bid);
			ps.executeUpdate();	
			
		} catch (Exception e) {
			logger.error("修改模板信息失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	/**
	 * 获取所有节点
	 * @param id
	 * @param tableName
	 * @param stopid
	 * @param isContainStop
	 * @return
	 * @throws ElException
	 */
	public TreeNode epLibTree(int id, String tableName , int stopid,
			boolean isContainStop) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		TreeNode epl = null;
		if (id == 0)
			epl = getEPLRoot(tableName);
		else
			epl = getEpLById(id,tableName);

		try {
			ct = DBConnection.getConnection();
			epl.setChildren(listEplById(epl.getId(), tableName, stopid,
					isContainStop, 0, ct));
		} catch (Exception e) {
			logger.error("获取类别出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return epl;
	}
	public TreeNode getEPLRoot(String tableName) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		TreeNode epl = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(" select id , name ,parentid  from "+tableName+" where parentid = ?  order by id");
			ps.setInt(1, 0);
			rs = ps.executeQuery();
			if (rs.next()) {
				epl = new TreeNode(rs.getInt(1), rs.getString(2));
				epl.setParent(new TreeNode(rs.getInt(3)));
				// return epl;
			}
			rs.close();
		} catch (Exception e) {
			logger.error("获取类别出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return epl;
	}
	public TreeNode getEpLById(int id,String tableName) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
//			ps = ct.prepareStatement(ElQuerySql
//					.getSQL(QuestionConstants.EPLIB_QUERY_BYIDANDUID));
			ps = ct.prepareStatement("select el.id,el.name,el.parentid,elp.name  " +
					" from "+tableName+" el left join  "+tableName+" elp on el.parentid=elp.id and elp.status!=1 where el.id =? and el.status!=1");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				TreeNode exLib = new TreeNode(rs.getInt(1), rs
						.getString(2));
				exLib
						.setParent(new TreeNode(rs.getInt(3), rs
								.getString(4)));
				return exLib;
			}
		} catch (Exception e) {
			logger.error("获取类别出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return null;
	}
	private List<TreeNode> listEplById(int parentid, String  tableName,
			int stopid, boolean isContainStop, int level, Connection ct)
			throws Exception {
		List<TreeNode> qls = new ArrayList<TreeNode>();
//		PreparedStatement pstemp = ct.prepareStatement(ElQuerySql
//				.getSQL(QuestionConstants.EPLIB_QUERY_BYPARENTIDANDUID));
		PreparedStatement pstemp = ct.prepareStatement("select id,name ,parentid from "+tableName+" where parentid = ? and status!=1 order by id");
		pstemp.setInt(1, parentid);
		ResultSet rstemp = pstemp.executeQuery();
		level++;
		while (rstemp.next()) {
			TreeNode epl = new TreeNode(rstemp.getInt(1), rstemp
					.getString(2));
			epl.setParent(new TreeNode(rstemp.getInt(3)));
			epl.setLevel(level);
			if (epl.getId() != stopid)
				epl.setChildren(listEplById(epl.getId(), tableName, stopid,
						isContainStop, level, ct));
			if (!isContainStop && epl.getId() == stopid) {

			} else
				qls.add(epl);
		}
		rstemp.close();
		pstemp.close();
		return qls;
	}
	public  Mode  Mode_getmodebyID(int id) throws ElException{
		Mode  m = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String	sql="select mi.id,mi.name,mi.typename,mi.tablename,mi.key ,mi.typeidfield from mode_info mi  where  mi.id=? ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				m = new Mode();
				m.setId(rs.getInt(1));
				m.setName(rs.getString(2));
				m.setTypetableName(rs.getString(3));
				m.setTableName(rs.getString(4));
				m.setKey(rs.getString(5));
				m.setTypefield(rs.getString(6));
				return m;
			}
		} catch (Exception e) {
			logger.error("获取模块信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return null;
		
	}
	public  Mode  Mode_getTypebindbyID(int modeid,int typeid) throws ElException{
		Mode  m = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String	sql="select mi.id,mi.name,mu.filename,mb.id,mi.typename,mb.bindtypestatus,mi.tablename,mb.bindpage from mode_info mi left join mode_bind mb on  " +
			" mi.id=mb.modeid left join  mode_updload  mu  on mu.id=mb.bindpage  where  mi.id=? and mb.bindtype=2 and mb.modetype=1 and mb.bindtypeid=? ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, modeid);
			ps.setInt(2, typeid);
			rs = ps.executeQuery();
			if (rs.next()) {
				m = new Mode();
				m.setId(rs.getInt(1));
				m.setName(rs.getString(2));
				m.setModeJspName(rs.getString(3));
				m.setBindid(rs.getInt(4));
				m.setTypetableName(rs.getString(5));
				m.setBindtypestatus(rs.getInt(6));
				m.setTableName(rs.getString(7));
				m.setModeJspid(rs.getInt(8));
				return m;
			}else{//如果没有信息
				sql="select id,name,typename,tablename from mode_info where  id=? ";
				ps = ct.prepareStatement(sql);
				ps.setInt(1, modeid);
				rs = ps.executeQuery();
				if(rs.next()){//如果存在该模块
					m = new Mode();
					m.setId(rs.getInt(1));
					m.setName(rs.getString(2));
					m.setTypetableName(rs.getString(3));
					m.setTableName(rs.getString(4));
					m.setBindtypestatus(1);
					sql="insert into mode_bind(bindtype,modeid,modetype,bindtypeid,bindtypestatus) values(2,?,1,?,1) ";
					ps=ct.prepareStatement(sql);
					ps.setInt(1, modeid);
					ps.setInt(2, typeid);
					ps.executeUpdate();	
					ps = ct.prepareStatement("select mode_bind_SEQUENCE.currval from dual ");
					rs = ps.executeQuery();  
					if (rs.next()){
						m.setBindid(rs.getInt(1)); 
					}	
					return  m;
				}
			}
		} catch (Exception e) {
			logger.error("获取类别绑定信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return null;
		
	}
	
	public   boolean   Mode_checktype(int  typeid,String tableName) throws ElException{
		boolean  falg=false;
		Mode  m = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String	sql="select count(1) from "+tableName+" mi  where  id=? ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, typeid);
			rs = ps.executeQuery();
			if (rs.next()) {
				if(rs.getInt(1)>0){
					falg=  true;
				}
			}
		} catch (Exception e) {
			  falg=false;
			logger.error("获取类别信息出错！", e);
			throw new ElException(e);
			 
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		return   falg;
	}
	public  void  Mode_updtypeextend(int  extendtype,int bid) throws ElException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	
		try{
			String  sql ="update  mode_bind set bindtypestatus=? where id=?";
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			ps.setInt(1,  extendtype);
			ps.setInt(2,  bid);
			ps.executeUpdate();	
		} catch (Exception e) {
			logger.error("修改类别继承失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public  String  Mode_getpage(int bindtype,int modeid,int modetype) throws ElException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	
		try{
			//mb.bindtype 绑定类型 mb.modeid模块ID   modetype模块类型
			String  sql =" select  truefilename from   mode_bind  mb left join  mode_updload mu  on mu.id=mb.bindpage where  mb.bindtype=? and " +
					" mb.modeid=? and modetype=? ";
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			ps.setInt(1,  bindtype);
			ps.setInt(2,  modeid);
			ps.setInt(3,  modetype);
			rs=ps.executeQuery();	
			if(rs.next()){
				return  rs.getString(1);
			}
		} catch (Exception e) {
			logger.error("修改类别继承失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return  null;
	}
public  String  Mode_gettypepage(int modeid,int bindtypeid) throws ElException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	
		try{
			// mb.modeid模块ID   bindtypeid 类别ID
			String  sql =" select  truefilename from   mode_bind  mb left join  mode_updload mu  on mu.id=mb.bindpage where  mb.bindtype=2 and " +
					" mb.modeid=? and modetype=1 and bindtypeid=? ";
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			ps.setInt(1,  modeid);
			ps.setInt(2,  bindtypeid);

			rs=ps.executeQuery();	
			if(rs.next()){
				return  rs.getString(1);
			}
		} catch (Exception e) {
			logger.error("修改类别继承失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return  null;
	}
	public boolean checknode(String tableName,int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean falg=false;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(" select count(1) from "+tableName+" where  id=? ");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			rs.next();
			falg=rs.getInt(1)>0?true:false;
			
		} catch (Exception e) {
			falg=false;
			logger.error("获取类别出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return  falg;
	}
	public  List<TreeNode>   Mode_getnodeallparent(String tableName,int id ) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<TreeNode> list=null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(" select parentid from "+tableName+" where  id=? and status!=1");
			while (id!=1) {
				ps.setInt(1, id);
				rs = ps.executeQuery();
				if(rs.next()){
					if(list==null) list = new ArrayList<TreeNode>();
					TreeNode  n = new TreeNode();
					n.setId(rs.getInt(1));
					list.add(n);
					id=rs.getInt(1);
				}
				
			}
			
		} catch (Exception e) {
			logger.error("获取类别出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return  list;
	}
	public  String  Mode_getentendtypepage(int modeid,int bindtypeid) throws ElException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	
		try{
			// mb.modeid模块ID   bindtypeid 类别ID
			String  sql =" select  truefilename from   mode_bind  mb left join  mode_updload mu  on mu.id=mb.bindpage where  mb.bindtype=2 and " +
					" mb.modeid=? and modetype=1 and bindtypeid=? and bindtypestatus=1  ";
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			ps.setInt(1,  modeid);
			ps.setInt(2,  bindtypeid);
			rs=ps.executeQuery();	
			if(rs.next()){
				return  rs.getString(1);
			}
		} catch (Exception e) {
			logger.error("查询类别继承失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return  null;
	}
	/**
	 * 模板文件表
	 * @param truename
	 * @param oldname
	 * @throws ElException
	 */
	public  void Mode_modeupload(String  truename,String oldname) throws ElException{

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	
		try{
			String  sql = "select count(1) from mode_updload where truefilename=?";
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			ps.setString(1,  truename);
			rs = ps.executeQuery();
			rs.next();
			if(rs.getInt(1)==0){
				 sql =" insert  into  mode_updload(filename,truefilename)  values(?,?)  ";
					ps=ct.prepareStatement(sql);
					ps.setString(1,  oldname);
					ps.setString(2,  truename);
					ps.executeUpdate();	
				
			}
			 
		} catch (Exception e) {
			logger.error("插入失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public String createPerTypeId(TreeNode treeNode, int ptid){
		if(treeNode!=null){
			if(treeNode.getId()!=ptid){
				treeNode = getnodeTypeById(treeNode.getChildren(),ptid,treeNode);
			}
			if(treeNode!=null&&treeNode.getChildren()!=null){
				return createTypeId(treeNode.getChildren(),treeNode.getId());
			}
			return String.valueOf(treeNode!=null?treeNode.getId():"0");
		}else{
			return null;
		}
	}
	private TreeNode getnodeTypeById(List<TreeNode> listType,int ptid,TreeNode ptypeTree){
		TreeNode  productType=null;
		for(TreeNode type:listType){
			if(type.getId()!=ptid){
				productType = getnodeTypeById(type.getChildren(),ptid,ptypeTree);
				if(productType!=null){
					return productType;
				}
			}else{
				return type;
			}
		}
		return productType;
	}
	private String createTypeId(List<TreeNode> listType,int id){
		String ids=id+"";
		for(TreeNode type:listType){
			ids=ids+","+createTypeId(type.getChildren(),type.getId());
		}
		return ids;
	}
	public  int  gettypeidformode(int  id,String  tablename,String  field) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	
		try{
			String  sql =" select "+field+"  from  "+tablename+"  where  id=?  ";
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			ps.setInt(1,  id);
			rs=ps.executeQuery();
			return rs.next()?rs.getInt(1):0;
		} catch (Exception e) {
			logger.error("查询当前内容的类别ID失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}	
	}
	public  Template Mode_modeupload(int  id) throws ElException{

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	
		try{
			String  sql =" select  filename,truefilename from  mode_updload where  id =?  ";
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			ps.setInt(1,  id);
			rs = ps.executeQuery();	
			if(rs.next()){
				Template t = new Template();
				t.setName(rs.getString(1));
				t.setTrueName(rs.getString(2));
				return t;
			}
			
		} catch (Exception e) {
			logger.error("插入失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return null;
	}
	public void intoModeInfo(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	
		try{
			String  sql =" {call intoModeInfo(?)}  ";
			ct= DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			ps.setInt(1,  id);
			ps.executeUpdate();	
			
		} catch (Exception e) {
			logger.error("插入失败 ", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
}
