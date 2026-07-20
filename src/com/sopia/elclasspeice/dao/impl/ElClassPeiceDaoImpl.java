package com.sopia.elclasspeice.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.sopia.ElConstants;
import com.sopia.classman.entities.ElClType;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.StringUtil;
import com.sopia.common.logger.ElLogger;
import com.sopia.common.logger.ElLoggerConstants;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.entities.ELUser;
import com.sopia.elclasspeice.dao.ElClassPeiceDao;
import com.sopia.elclasspeice.entities.ElClassPeice;
import com.sopia.peice.dao.impl.PeiceDaoImpl;

public class ElClassPeiceDaoImpl implements ElClassPeiceDao {
	private static final Log logger = LogFactory.getLog(PeiceDaoImpl.class);
	public int getSessionValue(String key) {
		HttpServletRequest requset = ServletActionContext.getRequest();
		HttpSession session = requset.getSession();
		return  Integer.parseInt(session.getAttribute(key).toString());
	}
	
	/**
	 * 获取培训班list
	 * @param tree
	 * @param elclass
	 * @param sublibs
	 * @param status
	 * @param sqlw
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ElClassPeice> getClassList(ElNode tree,ElClass elclass,int sublibs, String status,String sqlw, int pageNow, 
			int pageSize, String name, String userid, int dprice, int role ) throws ElException 
			{
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		String sql = "";
		String sqlW = "";
		List<ElClassPeice> cls = new ArrayList<ElClassPeice>();
		try {
			if (name == null)
				name = "";
			else
				name = name.trim(); 
			logger.debug(name);
			//课程定价条件
			if(dprice == 1){//未定价课程
				sqlW = sqlW + " and  elcp.status is null ";
			}
			if(dprice == 2){//审核中
				sqlW = sqlW + " and  elcp.status = 3";
			}
			if(dprice == 3){//审核通过
				sqlW = sqlW + " and  elcp.status = 1";
			}
			if(dprice == 4){//已定价
				sqlW = sqlW + " and  elcp.status = 4";
			}
		
			ct = DBConnection.getConnection();
			boolean consub = sublibs == 1 ? true : false;
			List<Object> params = new ArrayList<Object>();
			StringBuffer buffer = new StringBuffer(); 
			buffer.append("select * from (select t.*, rownum rn from (" +
					"select cl.id, cl.name, cl.certificatename, cl.cltype, cl.optionalcredit, cl.status cstatus ," +
					" clt.name cltname,u.realname,cl.createtime , cl.astauts ,cl.starttime,cl.finishtime," +
					" sc.usercount classsize,cl.isApplication,elr.planrecruitstudents,cl.depName,cl.jingzhong ,elcp.elclassnowprice,elcp.elclassoldprice ,elcp.status, u.id  userid   " +
					" from elclass cl inner join ("+((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).generateSQLByTree("elclasstype", tree, consub)+") clt on cl.cltype = clt.id " +
							"left join eluser u on cl.creater = u.id " +
							"left join department dep on u.depid = dep.id " +
							"left join (select classid,count(userid) usercount from study_class group by classid) sc on sc.classid=cl.id " +
							"left join elclass_registration elr on elr.classid=cl.id" +
							" left join elclass_price elcp on elcp.elclassid=cl.id "+
					"  where cl.status in("+status+")    ");
			this.checkClassParam(buffer, params, elclass);
//			buffer.append(" and cl.creater=? order by cl.createtime desc) t where rownum <= ?) where rn >= ? ");
			buffer.append("  order by cl.createtime desc) t where rownum <= ?) where rn >= ? ");
			ps = ct.prepareStatement(buffer.toString());
			logger.debug(buffer.toString());
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i+1, params.get(i));
			}
//			ps.setString(params.size()+1, userid);
//			ps.setInt(params.size()+2, pageNow);
//			ps.setInt(params.size()+3, pageSize);
			ps.setInt(params.size()+1, pageNow);
			ps.setInt(params.size()+2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				ElClass cl = new ElClass(id, rs.getString(2));
				cl.setCertificatename(rs.getString(3));
				cl.setCltype(new ElClType(rs.getInt(4), rs.getString(7)));
				cl.setOptionalcredit(rs.getInt(5));
				cl.setStatus(rs.getInt(6));
				cl.setAstatus(rs.getInt(10));
				ElClType clType=new ElClType();
				clType.setName(rs.getString(7));
				cl.setCltype(clType);
				ELUser user=new ELUser();
				user.setRealname(rs.getString(8));
				user.setId(rs.getInt(21));
				cl.setCreater(user);
				cl.setCreatetime(rs.getTimestamp(9));
				cl.setStarttime(rs.getTimestamp(11));
				cl.setFinishtime(rs.getTimestamp(12)); 
				cl.setClassSize(rs.getInt(13));
				cl.setIsApplication(rs.getInt(14));
				cl.setPlanNumber(rs.getInt(15));
				//cl.setIsUvalid(checkElclassIsUvalid("valids", id)?"true":"false");//???
				cl.setDepName(rs.getString(16));
				cl.setJingzhong(rs.getString(17));
				ElClassPeice elcp = new ElClassPeice();
				elcp.setElClass(cl);
//				cl.setElclasspeice(elcp);
				elcp.setElclassnowPrice(rs.getFloat(18));
				elcp.setElclassoldPrice(rs.getFloat(19));
				elcp.setStatus(rs.getInt(20));
				//cl.setStudentCount(rs.getInt(13));
//				if(isOpen(id)) {
//				cl.setOperation(1);//考场全部开通
//				}else{						
//				if(checkuserClassEroomOperation(id, sqlw))
//						cl.setOperation(2);//有可操作
//					else
//						cl.setOperation(3);//无可操作
//				}
//				cl.setExamRooms(getClassRooms(cl.getId()));
			cls.add(elcp);
			}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS, ElLoggerConstants.LOG_TYPE_GET, 
			"获取培训班列表失败!失败方法：getClassList 失败原因："+new ElException(e));
			logger.error("获取培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cls;
	
		}
	
	
	
	
	
	
	

	public void elClassPeice_Submit(int elclassid) throws ElException {
		PreparedStatement ps=null;
		ResultSet rs=null;
		Connection ct=null;
		int flag=0;
		try {//查询价格表里有没有该培训班的价格信息
			ct = DBConnection.getConnection();
			String sql="select count(1) from elclass_price where elclassid=?";
			ps=ct.prepareStatement(sql);
			ps.setInt(1, elclassid);
			rs=ps.executeQuery();
			 if(rs.next()) {
				flag=rs.getInt(1);
			}
			 rs.close();
			 ps.close();
			 
			 try {
				 if(flag==0){
						ct = DBConnection.getConnection();
						sql="insert into elclass_price(elclassid,status) values(?,3)";
						ps=ct.prepareStatement(sql);
						ps.setInt(1, elclassid);
						ps.executeUpdate();
				 }else{
					 	ct = DBConnection.getConnection();
						sql="update elclass_price set status=3 where elclassid=?";
						ps=ct.prepareStatement(sql);
						ps.setInt(1, elclassid);
						ps.executeUpdate();
				 }
				
			} catch (Exception e) {
					logger.error("查询价格失败！", e);
					throw new ElException(e);
			} finally {
					DBConnection.closeConnectInfo(ct, ps, rs);
			}
		} catch (Exception e) {
			logger.error("申请价格审核失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
		
	

	

	public void elClassPeice_change(float peicevale, int elclassid,
			int peicetype,int userid) throws ElException {
		// TODO Auto-generated method stub
			PreparedStatement ps=null;
			ResultSet rs=null;
			Connection ct=null;
			int flag=0;
			String peic = "";
			try {//查询价格表里有没有该课程的价格信息
				ct = DBConnection.getConnection();
				String sql="select count(1) from elclass_price where elclassid=?";
				ps=ct.prepareStatement(sql);
				ps.setInt(1, elclassid);
				rs=ps.executeQuery();
				 if(rs.next()) {
					flag=rs.getInt(1);
				}
				 rs.close();
				 ps.close();
				try{
					if(flag==0){//如果没有，则执行增加记录语句
						if(peicetype==1){ 
							peic = "elclassnowPrice"; 
						}else{ 
							peic = "elclassoldPrice";
						}
						sql="insert into elclass_price(elclassid,"+peic+",status,userid) values(?,?,?,?)"; 
						ps=ct.prepareStatement(sql); 
						ps.setInt(1, elclassid);
						ps.setFloat(2, peicevale); 
						ps.setInt(3,4);
						ps.setInt(4, userid);
						ps.executeUpdate();
						
					}else{//否则执行修改价格信息
						if(peicetype==1){ 
							peic = "elclassnowPrice"; 
						}else{ 
							peic = "elclassoldPrice";
						}
						sql="select status from elclass_price where elclassid=?";
	
						ps=ct.prepareStatement(sql);
						ps.setInt(1, elclassid);
						rs=ps.executeQuery();
						rs.next();
						if(rs.getInt(1)==1){
							sql="update elclass_price set "+peic+"=? where elclassid=?";
						}
						else{
							sql="update elclass_price set "+peic+"=?,status=4 where elclassid=?";
						}
						
					
						ps=ct.prepareStatement(sql);
						ps.setFloat(1, peicevale);
						ps.setInt(2, elclassid);
						ps.executeUpdate();
					}
					}catch(Exception e){
						logger.error("修改价格失败！", e);
						throw new ElException(e);
					}finally {
						DBConnection.closeConnectInfo(ct, ps, rs);
					}
		
			} catch (Exception e) {
				logger.error("查询价格失败！", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}
		
	}

	public List<ElClassPeice> getMyAll(ElClType elcltypeTree, int type,
			String name, String status, String userid, int dprice, int role,
			int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElClassPeice> css = new ArrayList<ElClassPeice>();
		String sql = "";
		String sqlW = "";
		try {
			if (name == null)
				name = "";
			else
				name = name.trim(); 
			//课程定价条件
			if(dprice == 1){//未定价课程
				sqlW = sqlW + " and  elcp.status is null ";
			}
			if(dprice == 2){//审核中
				sqlW = sqlW + " and  elcp.status = 3";
			}
			if(dprice == 3){//审核通过
				sqlW = sqlW + " and  elcp.status = 1";
			}
			if(dprice == 4){//已定价
				sqlW = sqlW + " and  elcp.status = 4";
			}

			List<Object> params = new ArrayList<Object>();
			StringBuffer buffer = new StringBuffer(); 
			buffer.append("select * from (select t.*, rownum rn from (" +
					"select cl.id, cl.name, cl.certificatename, cl.cltype, cl.optionalcredit, cl.status," +
					" clt.name cltname,u.realname,cl.createtime , cl.astauts ,cl.starttime,cl.finishtime," +
					" sc.usercount classsize,cl.isApplication,elr.planrecruitstudents,cl.depName,cl.jingzhong ,elcp.elclassnowprice,elcp.elclassoldprice" +
					" from elclass cl inner join ("+((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).generateSQLByTree("elclasstype", elcltypeTree, true)+") clt on cl.cltype = clt.id " +
							"left join eluser u on cl.creater = u.id " +
							"left join department dep on u.depid = dep.id " +
							"left join (select classid,count(userid) usercount from study_class group by classid) sc on sc.classid=cl.id " +
							"left join elclass_registration elr on elr.classid=cl.id" +
							" left join elclass_price elcp on elcp.elclassid=cl.id "+
							" and c.status not in ( "+status+" ) and c.name like ? "+sqlW+
							" order by c.createtime desc )t  where rownum <= ? ) where rn>=?"); 
			ps = ct.prepareStatement(buffer.toString());
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i+1, params.get(i));
			}
			ps.setInt(params.size()+1, pageNow);
			ps.setInt(params.size()+2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				ElClass cl = new ElClass(id, rs.getString(2));
				cl.setCertificatename(rs.getString(3));
				cl.setCltype(new ElClType(rs.getInt(4), rs.getString(7)));
				cl.setOptionalcredit(rs.getInt(5));
				cl.setStatus(rs.getInt(6));
				cl.setAstatus(rs.getInt(10));
				ElClType clType=new ElClType();
				clType.setName(rs.getString(7));
				cl.setCltype(clType);
				ELUser user=new ELUser();
				user.setRealname(rs.getString(8));
				cl.setCreater(user);
				cl.setCreatetime(rs.getTimestamp(9));
				cl.setStarttime(rs.getTimestamp(11));
				cl.setFinishtime(rs.getTimestamp(12)); 
				cl.setClassSize(rs.getInt(13));
				cl.setIsApplication(rs.getInt(14));
				cl.setPlanNumber(rs.getInt(15));
			//	cl.setIsUvalid(checkElclassIsUvalid("valids", id)?"true":"false");//???
				cl.setDepName(rs.getString(16));
				cl.setJingzhong(rs.getString(17));
				ElClassPeice elcp = new ElClassPeice();
				elcp.setElClass(cl);
				elcp.setElclassnowPrice(rs.getFloat(18));
				elcp.setElclassoldPrice(rs.getFloat(19));
				//cl.setStudentCount(rs.getInt(13));
//				if(isOpen(id)) {
//					cl.setOperation(1);//考场全部开通
//				}else{						
//					if(checkuserClassEroomOperation(id, sqlw))
//						cl.setOperation(2);//有可操作
//					else
//						cl.setOperation(3);//无可操作
//				}
//				cl.setExamRooms(getClassRooms(cl.getId()));
				css.add(elcp);
			}
		} catch (Exception e) {
//			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
//					ElLoggerConstants.LOG_MOD_CLASS, ElLoggerConstants.LOG_TYPE_GET, 
//			"获取培训班列表失败!失败方法：getClassList 失败原因："+new ElException(e));
//			logger.error("获取培训班列表失败！", e);
//			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return css;
	}

	public int getMyAllSize(ElClType elcltypeTree, int typeid, String name,
			String status, String userid, int dprice, int role)
			throws ElException {
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection ct = null; 
			String sql = "";
			String sqlW = "";
			int size = 0 ;
			try {
				if (name == null)
					name = "";
				else
					name = name.trim(); 
				if(dprice == 1){//未定价课程
					sqlW = sqlW + " and  elcp.status is null ";
				}
				if(dprice == 2){//审核中
					sqlW = sqlW + " and  elcp.status = 3";
				}
				if(dprice == 3){//审核通过
					sqlW = sqlW + " and  elcp.status = 1";
				}
				if(dprice == 4){//已定价
					sqlW = sqlW + " and  elcp.status = 4";
				}
	
				ct = DBConnection.getConnection();
				sql="select count(c.id) " +
					" from ( elclass c left join elclass_price elcp on " +
					" c.id=elcp.elclassid ) " +
					" left join eluser eu on eu.id=c.creater ,("
					+ ((ElNodeSQL) SpringContextUtil
							.getBean("elnodesql"))
							.generateSQLByTree("elclasstype",
									elcltypeTree, true) + ")ct "+
//					" where  c.creater = ? and ct.id =c.cltype "+
					" where   ct.id =c.cltype "+
					" and c.status  in ( "+status+") and c.name like ? "+sqlW+
					" order by c.createtime desc"; 
				ps = ct.prepareStatement(sql);
//				ps.setInt(1,Integer.valueOf(userid));
//				ps.setString(2, "%"+name+"%"); 
				ps.setString(1, "%"+name+"%"); 
				rs = ps.executeQuery();
				if (rs.next()) {
					size = rs.getInt(1);
				} 
			} catch (Exception e) {
				logger.error("获取列表大小失败！", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}
			
			return size;
	}
	
	
	/**
	 * 检测参数
	 * @param sql
	 * @param params
	 * @param elclass
	 */
	public void checkClassParam(StringBuffer sql,List<Object> params,ElClass elclass){
		if(elclass!=null){
			if( elclass.getName()!=null&&!elclass.getName().equals("")){//培训名称
				sql.append(" and cl.name like ? escape '/'");
				params.add("%"+StringUtil.toLikeStr(elclass.getName())+"%");
			}
			if(elclass.getOwner()!=null){
				if(elclass.getOwner().getUsername()!=null&&!elclass.getOwner().getUsername().equals("")){//培训名称
					sql.append(" and u.username like ?");
					params.add("%"+StringUtil.toLikeStr(elclass.getOwner().getUsername())+"%");
				}
				if(elclass.getOwner().getRealname()!=null&&!elclass.getOwner().getRealname().equals("")){//培训名称
					sql.append(" and u.realname like ?");
					params.add("%"+StringUtil.toLikeStr(elclass.getOwner().getRealname())+"%");
				}
			}
			if(elclass.getStatus()!=-1){//考场状态
				sql.append(" and cl.status=?");
				params.add(elclass.getStatus());
			}
			if(elclass.getBegintime()!=null){
//				sql.append(" and cl.STARTTIME >= to_date('"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//				.format(elclass.getBegintime())+ "','yyyy-MM-dd HH24:mi:ss')");
				sql.append(" and cl.STARTTIME >= ?");
				params.add(elclass.getBegintime());
			}
			if(elclass.getEndtime()!=null){
//				sql.append(" and cl.FINISHTIME <= to_date('"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//				.format(elclass.getEndtime())+ "','yyyy-MM-dd HH24:mi:ss')");
				sql.append(" and cl.FINISHTIME <= ?");
				params.add(elclass.getEndtime());
			}
			if(elclass.getElRegistration()!=null&&elclass.getElRegistration().getIsAudit()==1){
				sql.append(" and elr.isAudit=1 ");
			}
		}
	}

	
	
	
//	public int getClassListSize(ElNode tree,ElClass elclass,int sublibs, String status)
//		throws ElException {
//		PreparedStatement ps = null;
//		Connection ct = null;
//		ResultSet rs = null;
//		try { 
//			ct = DBConnection.getConnection();
//			boolean consub = sublibs == 1 ? true : false;
//			List<Object> params = new ArrayList<Object>();
//			StringBuffer buffer = new StringBuffer(); 
//			buffer.append("select count(cl.id) " +
//					" from elclass cl inner join ("+((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).generateSQLByTree("elclasstype", tree, consub)+") clt on cl.cltype = clt.id " +
//					" left join eluser u on cl.creater=u.id " +
//					" left join elclass_registration elr on elr.classid=cl.id "+
//					"  where cl.status in("+status+") ");
//			this.checkClassParam(buffer, params, elclass);
//			ps = ct.prepareStatement(buffer.toString());   
//			for (int i = 0; i < params.size(); i++) {
//				ps.setObject(i+1, params.get(i));
//			}
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				return rs.getInt(1);
//			}
//		
//		} catch (Exception e) {
//			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
//					ElLoggerConstants.LOG_MOD_CLASS, ElLoggerConstants.LOG_TYPE_GET, 
//			"获取培训班列表数量失败!失败方法：getClassListSize 失败原因："+new ElException(e));
//			logger.error("获取培训班列表数量失败！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return 0;
//		}
	
	
	public void elClassPeice_audit(int elclassid, int userid,int setstatus) throws ElException {
		// TODO Auto-generated method stub
		PreparedStatement ps=null;
		ResultSet rs=null;
		Connection ct=null;
		int flag=0;
		try {//查询价格表里有没有该课程的价格信息
			ct = DBConnection.getConnection();
			String sql="select count(1) from elclass_price where elclassid=?";
			ps=ct.prepareStatement(sql);
			ps.setInt(1, elclassid);
			rs=ps.executeQuery();
			 if(rs.next()) {
				flag=rs.getInt(1);
			}
			 rs.close();
			 ps.close();
			 
			 try {
				 if(flag==0){
						ct = DBConnection.getConnection();
						sql="insert into elclass_price(elclassid,status,userid) values(?,?,?)";
						ps=ct.prepareStatement(sql);
						ps.setInt(1, elclassid);
						ps.setInt(2, setstatus);
						ps.setInt(3, userid);
						ps.executeUpdate();
				 }else{
					 	ct = DBConnection.getConnection();
						sql="update elclass_price set status=?,userid=? where elclassid=?";
						ps=ct.prepareStatement(sql);
						ps.setInt(1, setstatus);
						ps.setInt(2, userid);
						ps.setInt(3, elclassid);
						ps.executeUpdate();
				 }
				
			} catch (Exception e) {
					logger.error("查询价格失败！", e);
					throw new ElException(e);
			} finally {
					DBConnection.closeConnectInfo(ct, ps, rs);
			}
		} catch (Exception e) {
			logger.error("价格审核失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

}
