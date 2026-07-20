package com.sopia.shebeipinggu.impl;
   
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.DBConnection;
import com.sopia.common.ElException; 
import com.sopia.common.OracleBlob;
import com.sopia.common.BeanGenerator.BeanSqlSplice;
import com.sopia.common.BeanGenerator.CglibBean;
import com.sopia.common.BeanGenerator.TableCreateBean;
import com.sopia.duman.entities.ELUser;
import com.sopia.pfms.entities.IC_column_qiuji_qiuhe;
import com.sopia.pfms.entities.RelateColumnInformation;
import com.sopia.pfms.entities.RelateTable;
import com.sopia.pfms.entities.TableColumn;
import com.sopia.schedule.entities.ModuleManage;
import com.sopia.shebeipinggu.dao.PG_InsuranceCategoriesDao;
import com.sopia.pfms.entities.InsuranceCategories;

public class PG_InsuranceCategoriesDaoImpl implements PG_InsuranceCategoriesDao { 
	private static final Log logger = LogFactory.getLog(PG_InsuranceCategoriesDaoImpl.class);
	
	public List<InsuranceCategories> getICList(InsuranceCategories ic, int pageNow, int pageSize) throws ElException{ 
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	
		List<InsuranceCategories> IClist = new ArrayList<InsuranceCategories>();
		String sql = "";
		String sqlW = "";
		try {
			if(ic != null){
				if(ic.getName() != null &&!ic.getName().equals(""))
					sqlW = sqlW + " and ic.name like '%"+ic.getName()+"%'";
				if(ic.getTableName() != null && !ic.getTableName().equals(""))
					sqlW = sqlW + " and ic.tablename like '%"+ic.getTableName()+"%'";
				if(ic.getFounder() != null && !ic.getFounder().getRealname().equals(""))
					sqlW = sqlW + " and el.realname like '%"+ic.getFounder().getRealname()+"%'";
			}
			sql = "select * from (select t.*, rownum rn from (select ic.id , ic.name , ic.tablename, ic.founder, ic.createtime ,el.realname,ic.demourl,ic.democss,ic.read_auto_toubaoren,ic.read_auto_beibaoren,ic.read_auto_biaodi " +
					"from IC_MANAGE_PINGGU ic ,eluser el where ic.founder = el.id "+sqlW+" order by createtime desc) t where rownum <= ?) where rn >= ? ";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql); 
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				InsuranceCategories IC = new InsuranceCategories(rs.getInt(1));
				IC.setName(rs.getString(2));
				IC.setTableName(rs.getString(3));
				IC.setFounder(new ELUser(rs.getInt(4),rs.getString(6)));
				IC.setCreateTime(rs.getTimestamp(5)); 
				IC.setDemourl(rs.getString(7));
				IC.setDemocss(rs.getString(8));
				IC.setRead_auto_toubaoren(rs.getString(9));
				IC.setRead_auto_beibaoren(rs.getString(10));
				IC.setRead_auto_biaodi(rs.getString(11));
				IClist.add(IC);
			}  
		} catch (Exception e) {
			logger.error("查询险种列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return IClist;
	};
	
	public int getICListSize(InsuranceCategories ic) throws ElException{ 
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	 
		int size = 0;
		String sql = "";
		String sqlW = "";
		try {
			if(ic != null){
				if(ic.getName() != null && !ic.getName().equals(""))
					sqlW = sqlW + " and ic.name like '%"+ic.getName()+"%'";
				if(ic.getTableName() != null && !ic.getTableName().equals(""))
					sqlW = sqlW + " and ic.tablename like '%"+ic.getTableName()+"%'";
				if(ic.getFounder() != null && !ic.getFounder().getRealname().equals(""))
					sqlW = sqlW + " and el.realname like '%"+ic.getFounder().getRealname()+"%'";
			}
			sql = "select count(ic.id)" +
					"from IC_MANAGE_PINGGU ic ,eluser el where ic.founder = el.id "+ sqlW;
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql); 
			rs = ps.executeQuery();
			if (rs.next()) { 
				size = rs.getInt(1);
			}  
		} catch (Exception e) {
			logger.error("查询险种Size失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	};
	
	public boolean CheckIC(InsuranceCategories ic) throws ElException{ 
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	  
		String sql = ""; 
		boolean check = false ;
		try { 
			sql = "select * from IC_MANAGE_PINGGU ic where ic.tablename = ?";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);  
			ps.setString(1, ic.getTableName_());
			rs = ps.executeQuery();
			if (rs.next()) { 
				check = true;
			}  
		} catch (Exception e) {
			logger.error("验证险种是否存在失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return check;
	}; 
	
	public InsuranceCategories getByICId(int id) throws ElException{ 
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	  
		String sql = "";  
		InsuranceCategories IC = new InsuranceCategories();
		try { 
			sql = "select ic.id , ic.name , ic.tablename, ic.founder, ic.createtime ,el.realname,ic.read_auto_toubaoren,ic.read_auto_beibaoren,ic.read_auto_biaodi" +
				  " from IC_MANAGE_PINGGU ic ,eluser el where ic.founder = el.id and ic.id = ? ";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);  
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {  
				IC.setId(rs.getInt(1));
				IC.setName(rs.getString(2));
				IC.setTableName(rs.getString(3));
				IC.setFounder(new ELUser(rs.getInt(4),rs.getString(6)));
				IC.setCreateTime(rs.getTimestamp(5));
				IC.setRead_auto_toubaoren(rs.getString(7));
				IC.setRead_auto_beibaoren(rs.getString(8));
				IC.setRead_auto_biaodi(rs.getString(9));
				
			}  
		} catch (Exception e) {
			logger.error("根据id获取险种管理信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return IC;
	};
	
	public InsuranceCategories getByICTableName(String name) throws ElException{ 
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	  
		String sql = "";  
		InsuranceCategories IC = new InsuranceCategories();
		try { 
			sql = "select ic.id , ic.name , ic.tablename, ic.founder, ic.createtime ,el.realname,read_auto_biaodi,read_auto_toubaoren,read_auto_beibaoren" +
				  " from IC_MANAGE_PINGGU ic ,eluser el where ic.founder = el.id and ic.tablename = ? ";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);  
			ps.setString(1, name);
			rs = ps.executeQuery();
			if (rs.next()) {  
				IC.setId(rs.getInt(1));
				IC.setName(rs.getString(2));
				IC.setTableName(rs.getString(3));
				IC.setFounder(new ELUser(rs.getInt(4),rs.getString(6)));
				IC.setCreateTime(rs.getTimestamp(5));  
				IC.setRead_auto_biaodi(rs.getString("read_auto_biaodi"));
				IC.setRead_auto_toubaoren(rs.getString("read_auto_toubaoren"));
				IC.setRead_auto_beibaoren(rs.getString("read_auto_beibaoren"));
			}  
		} catch (Exception e) {
			logger.error("根据id获取险种管理信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return IC;
	};
	
	public int addIC(InsuranceCategories ic) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	  
		String sql = ""; 
		int id = 0 ;
		try {  
			sql = "insert into IC_MANAGE_PINGGU (NAME,TABLENAME,DESCRIPTION,FOUNDER,CREATETIME,read_auto_toubaoren,read_auto_beibaoren,read_auto_biaodi) values (?,?,?,?,?,?,?,?)";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);  
			ps.setString(1, ic.getName());
			ps.setString(2, ic.getTableName_());
			ps.setString(3, ic.getDescription());
			ps.setInt(4, ic.getFounder().getId());
			ps.setTimestamp(5, new Timestamp(System.currentTimeMillis())); 
			ps.setString(6, ic.getRead_auto_toubaoren());
			ps.setString(7, ic.getRead_auto_beibaoren());
			ps.setString(8, ic.getRead_auto_biaodi());
			ps.executeUpdate(); 
			ps = ct.prepareStatement("select IC_MANAGE_PINGGU_sequence.currval from dual ");
			rs = ps.executeQuery();  
			if (rs.next()){
				id =  rs.getInt(1); 
			}
		} catch (Exception e) {
			logger.error("增加险种失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;
	};
	
	public int addOrUpdateIC_U_Date(String sql,String tableName,String typeOrId,String blob) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	  
		int id = 0 ;
		try {   
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);     
			ps.executeUpdate(); 
			if(typeOrId.equals("add")){ //新增才查寻
				ps = ct.prepareStatement("select "+tableName+"_sequence.currval from dual ");
				rs = ps.executeQuery();  
				if (rs.next()){
					id =  rs.getInt(1); 
				}
			}else{
				id = Integer.parseInt(typeOrId);
			} 
			
        	//更新BOLB字段
			if(blob.length() > 0){
				String [] blobs = blob.split("-blob-"); 
	        	OracleBlob setblob = new OracleBlob(tableName,"id",id+"",blobs[0],blobs[1],"修改"+tableName+"表BOLB失败",ct);
				setblob.updateContent();
			}
			
		} catch (Exception e) {
			logger.error("增加险种数据失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;
	};
	
	public void IC_U_Delete(InsuranceCategories ic) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection(); 
			ps = ct.prepareStatement("delete from IC_MANAGE_PINGGU where id = ? ");
			ps.setInt(1, ic.getId());  
			ps.executeUpdate();
			//删除表
			ps = ct.prepareStatement("drop table "+ic.getTableName()); 
			ps.executeUpdate();
			//删除Sequence
			ps = ct.prepareStatement("drop sequence "+ic.getTableName()+"_sequence"); 
			ps.executeUpdate();
			//删除Trigger
//			ps = ct.prepareStatement("drop trigger AUTO_ID_"+ic.getTableName()); 
//			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除险种出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public void updateDemoORCss(int id, String demoName,String urlORcss) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String tableName = urlORcss.equals("url") ? "DEMOURL" : "DEMOCSS";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update IC_MANAGE_PINGGU set "+tableName+"=? where id = ?");
			ps.setString(1, demoName);
			ps.setInt(2, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新模板出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public void createIC_Date(BeanSqlSplice ic_date)throws ElException{  
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	 
		try {   
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("call createtable_pinggu (?)");  
			ps.setString(1, ic_date.getTableName()); 
			ps.executeUpdate();   
		} catch (Exception e) {
			logger.error("增加险种详情表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		} 
	};
	
	public void addIC_DateInit(BeanSqlSplice ic_date) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	 
		try {   
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ic_date.getAddSQL_());  
			ps.executeUpdate();  
		} catch (Exception e) {
			logger.error("增加险种详情表初始化数据失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		} 
	};
	 
	public List<TableCreateBean> getByIC_U_tableName(String tableName) throws ElException{ 
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	  
		String sql = "";  
		List<TableCreateBean> TCB = new ArrayList<TableCreateBean>();
		try { 
			sql = " select  ucc.column_name,ucc.comments,utc.data_type,utc.data_length " +
				  " from   user_col_comments ucc left join user_tab_columns utc on ucc.COLUMN_NAME = " +
				  " utc.COLUMN_NAME where   ucc.table_name = utc.table_name and  utc.table_name=?";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);  
			ps.setString(1, tableName);
			rs = ps.executeQuery();
			while (rs.next()) {  
				TableCreateBean tcb = new TableCreateBean(tableName); 
				tcb.setColumn_name(rs.getString(1));
				tcb.setColumn_comments(rs.getString(2));
				tcb.setData_type(rs.getString(3));
				tcb.setData_length(rs.getInt(4));
				TCB.add(tcb);
			}  
		} catch (Exception e) {
			logger.error("根据表名获取表结构失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return TCB;
	};
	
	public TableCreateBean getTSB(String tablename,String columnname) throws ElException{ 
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	  
		String sql = "";  
		TableCreateBean tcb = null;
		try { 
			sql = " select  ucc.column_name,ucc.comments,utc.data_type,utc.data_length " +
				  " from   user_col_comments ucc left join user_tab_columns utc on ucc.COLUMN_NAME = " +
				  " utc.COLUMN_NAME where   ucc.table_name = utc.table_name and  utc.table_name=? and utc.column_name=?";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);  
			ps.setString(1, tablename);
			ps.setString(2, columnname);
			rs = ps.executeQuery();
			if (rs.next()) {  
				tcb = new TableCreateBean(tablename); 
				tcb.setColumn_name(rs.getString(1));
				tcb.setColumn_comments(rs.getString(2));
				tcb.setData_type(rs.getString(3));
				tcb.setData_length(rs.getInt(4));
			}  
		} catch (Exception e) {
			logger.error("根据列名表名获取表结构失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return tcb;
	}; 
	
	public List<TableCreateBean> getByIC_U_tableNameAndValue(String tableName ,int id) throws ElException{ 
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	  
		String sql = "";  
		List<TableCreateBean> TCB = new ArrayList<TableCreateBean>();
		try { 
			sql = " select  ucc.column_name,ucc.comments,utc.data_type,utc.data_length " +
				  " from   user_col_comments ucc left join user_tab_columns utc on ucc.COLUMN_NAME = " +
				  " utc.COLUMN_NAME where   ucc.table_name = utc.table_name and  utc.table_name=?";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);  
			ps.setString(1, tableName);
			rs = ps.executeQuery();
			while (rs.next()) {  
				TableCreateBean tcb = new TableCreateBean(tableName); 
				tcb.setColumn_name(rs.getString(1));
				tcb.setColumn_comments(rs.getString(2));
				tcb.setData_type(rs.getString(3));
				tcb.setData_length(rs.getInt(4)); 
				tcb.setData_value(getIC_U_ByIdValue(tableName, rs.getString(1), id,rs.getString(3)));
				TCB.add(tcb);
			}  
		} catch (Exception e) {
			logger.error("根据表名获取表结构及表某ID数据失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return TCB;
	};
	 
	public String getIC_U_ByIdValue(String tableName , String Column_Name, int id ,String Data_type) throws ElException{ 
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	  
		String sql = "";  
		try { 
			sql = " select "+Column_Name+" from "+tableName+"  where id = ?";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);  
			ps.setInt(1, id);  
			rs = ps.executeQuery();
			if (rs.next()) {  
				if(Data_type.equals("TIMESTAMP(6)")){
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式，不显示毫秒  
					return df.format(rs.getTimestamp(1)); 
				}if(Data_type.equals("BLOB")){ 
					return new OracleBlob().getContent(rs.getBlob(1)); 
				}else{  
					return rs.getString(1);
				}
			}else{ 
				return " ";
			}  
		} catch (Exception e) {
			logger.error("获取id某列数据失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}; 
	
	public boolean CheckIC_U_Column_Blob(String tableName) throws ElException{ 
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	  
		String sql = ""; 
		boolean check = false ;
		try { 
			sql = "select DATA_type from user_tab_columns where table_name = ?  and  DATA_type ='BLOB'";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);  
			ps.setString(1, tableName); 
			rs = ps.executeQuery();
			if (rs.next()) { 
				check = true;
			}  
		} catch (Exception e) {
			logger.error("验证险种详情表列是否存在失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return check;
	}; 
	
	public boolean CheckIC_U_Column(String tableName , String Column_Name) throws ElException{ 
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	  
		String sql = ""; 
		boolean check = false ;
		try { 
			sql = " select column_name from   user_col_comments where table_name = ? and column_name =?";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);  
			ps.setString(1, tableName);
			ps.setString(2, Column_Name);
			rs = ps.executeQuery();
			if (rs.next()) { 
				check = true;
			}  
		} catch (Exception e) {
			logger.error("验证险种详情表列是否存在失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return check;
	}; 
	
	public void addIC_U_Column(BeanSqlSplice bcsql) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	 
		try {   
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(bcsql.getAddColumnSQL_()); //增加列
			ps.executeUpdate(); 
			ps = ct.prepareStatement(bcsql.getAddColumnSQL_comment());//增加注释  保存  页面显示名称==页面显示方式
			ps.executeUpdate();   
			
			System.out.println(bcsql.getAddColumnSQL_qiuji_qiuhe());
			ps = ct.prepareStatement(bcsql.getAddColumnSQL_qiuji_qiuhe());//添加字段是否求积求和
			ps.executeUpdate(); 
			
			if(bcsql.getRelateColumnName() != null && !bcsql.getRelateColumnName().equals("")
					&& bcsql.getRelateTableName() != null && !bcsql.getRelateTableName().equals("")){
				ps = ct.prepareStatement(bcsql.getAddColumnRelateInfo()); //增加字段关联信息
				ps.executeUpdate();
			}
		} catch (Exception e) {
			logger.error("险种详情信息增加列失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		} 
	};
	
	
	
	
	public CglibBean getByIC_U_ByTableName(String tableName) throws ElException{ 
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	  
		String sql = "";  
		TableCreateBean TCB = new TableCreateBean(tableName);
		//创建Bean
		CglibBean bean = null;
		try {  
			bean = TCB.createBean();
			sql = "select * from "+tableName;
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);  
			ps.setString(1, tableName);
			rs = ps.executeQuery();
			if (rs.next()) {  
		        // 给 Bean 设置值   
				for(int i = 0 ; TCB.getTSBs_().size() > i ; i++){  
					if(TCB.getTSBs_().get(i).data_type.equals("NUMBER")) 
						bean.setValue(TCB.getTSBs_().get(i).getColumn_name(), rs.getInt(i));  
					if(TCB.getTSBs_().get(i).data_type.equals("VARCHAR2"))
						bean.setValue(TCB.getTSBs_().get(i).getColumn_name(), rs.getString(i));  
					if(TCB.getTSBs_().get(i).data_type.equals("DATE"))
						bean.setValue(TCB.getTSBs_().get(i).getColumn_name(), rs.getTimestamp(i));  
				} 
			}  
		} catch (Exception e) {
			logger.error("根据险种表详情失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return bean;
	} 
	
	public int addIC_U_(InsuranceCategories ic) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	  
		String sql = ""; 
		int id = 0 ;
		try {  
			sql = "insert into IC_MANAGE_PINGGU (NAME,TABLENAME,DESCRIPTION,FOUNDER,CREATETIME) values (?,?,?,?,?)";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);  
			ps.setString(1, ic.getName());
			ps.setString(2, ic.getTableName_());
			ps.setString(3, ic.getDescription());
			ps.setInt(4, ic.getFounder().getId());
			ps.setTimestamp(5, new Timestamp(System.currentTimeMillis())); 
			ps.executeUpdate(); 
			ps = ct.prepareStatement("select IC_MANAGE_PINGGU_sequence.currval from dual ");
			rs = ps.executeQuery();  
			if (rs.next()){
				id =  rs.getInt(1); 
			}
		} catch (Exception e) {
			logger.error("增加险种失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;
	}

	@SuppressWarnings("unchecked")
	public List searchRelateTables(String tablename,String type, int pageNow,
			int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	  
		String sql = "";  
		List tables = new ArrayList();
		try { 
			if(type != null ){
				if(type.equals("biaodi")){
					sql = "select b.*,rn from (" +
					"select a.*,rownum rn from(" +
						"SELECT * FROM USER_TAB_COMMENTS where TABLE_NAME in (select tablename from TB_MODULE_MANAGE)) a " +
						"where rownum<=?) b " +
					"where rn>=? ";
				}else{
					sql = "SELECT * FROM USER_TAB_COMMENTS where TABLE_NAME IN ('ELUSER','PFMSUSER')";
				}
			}else{//模块间计算
				sql = "select b.*,rn from (" +
				"select a.*,rownum rn from(" +
					"SELECT * FROM tb_module_manage where tablename != '"+tablename+"') a " +
					"where rownum<=?) b " +
				"where rn>=? ";
			}
//			if(type != null && type.equals("biaodi")){
//				sql = "select b.*,rn from (" +
//						"select a.*,rownum rn from(" +
//							"SELECT * FROM USER_TAB_COMMENTS where TABLE_NAME in (select tablename from TB_MODULE_MANAGE)) a " +
//							"where rownum<=?) b " +
//						"where rn>=? ";
//			}else{
//				sql = "SELECT * FROM USER_TAB_COMMENTS where TABLE_NAME IN ('ELUSER','PFMSUSER')";
//			}
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);  
			if(type != null){
				if(type.equals("biaodi")){
					ps.setInt(1, pageNow);
					ps.setInt(2, pageSize);
				}
			}else{//模块间计算
				ps.setInt(1, pageNow);
				ps.setInt(2, pageSize);
			}
			rs = ps.executeQuery();
			while (rs.next()) {  
				if(type != null){
					RelateTable table = new RelateTable(); 
					table.setComments(rs.getString("COMMENTS"));
					table.setTableName(rs.getString("TABLE_NAME"));
					tables.add(table);
				}else{//模块间计算
					ModuleManage mm = new ModuleManage();
					mm.setId(rs.getInt("id"));
					mm.setModulename(rs.getString("modulename"));
					mm.setRemark(rs.getString("remark"));
					mm.setTablename(rs.getString("tablename"));
					tables.add(mm);
				}
			}  
		} catch (Exception e) {
			logger.error("获取表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return tables;
	}

	public int searchRelateTablesSize(String type) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	 
		int size = 0;
		String sql = "";
		try {
			if( type != null){
				if(type.equals("biaodi")){
					sql = "SELECT count(1) FROM USER_TAB_COMMENTS where TABLE_NAME in (select tablename from TB_MODULE_MANAGE)";
				}
			}else{
				sql = "SELECT count(1) FROM tb_module_manage ";
			}
			
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql); 
			rs = ps.executeQuery();
			if (rs.next()) { 
				size = rs.getInt(1);
			}  
		} catch (Exception e) {
			logger.error("查询表Size失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	public RelateTable getRelateTableByTableName(String tableName)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	 
		String sql = "";
		RelateTable table;
		List<TableColumn> columns = new ArrayList<TableColumn>();
		try {
			ct = DBConnection.getConnection();
			table = new RelateTable();
			sql = "SELECT * FROM USER_TAB_COMMENTS where TABLE_NAME = ?";
			ps = ct.prepareStatement(sql); 
			ps.setString(1, tableName.toUpperCase());
			rs = ps.executeQuery();
			if(rs.next()){
				table.setComments(rs.getString("COMMENTS"));
				table.setTableName(rs.getString("TABLE_NAME"));
			}
			if(table.getTableName() != null && !table.getTableName().equals("")){
				sql = "SELECT COLUMN_NAME,DATA_TYPE,DATA_LENGTH FROM user_tab_columns WHERE TABLE_NAME = ?";
				ps = ct.prepareStatement(sql); 
				ps.setString(1, tableName.toUpperCase());
				rs = ps.executeQuery();
				while(rs.next()){
					TableColumn column = new TableColumn();
					column.setColumnName(rs.getString("COLUMN_NAME"));
					column.setDateLength(rs.getString("DATA_LENGTH"));
					column.setDateType(rs.getString("DATA_TYPE"));
					columns.add(column);
				}
				table.setColumns(columns);
			}
		} catch (Exception e) {
			logger.error("根据表名获取列失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return table;
	}

	public List<RelateColumnInformation> getrelateColumns(String tableName) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	  
		String sql = "";  
		List<RelateColumnInformation> relateColumns = new ArrayList<RelateColumnInformation>();
		try { 
			sql = " select * from ic_column_relate where table_name = ?";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);  
			ps.setString(1, tableName);
			rs = ps.executeQuery();
			while (rs.next()) {  
				RelateColumnInformation relateColumn = new RelateColumnInformation();
				relateColumn.setId(rs.getShort("id"));
				relateColumn.setTableName(rs.getString("table_name").toUpperCase());
				relateColumn.setColumnName(rs.getString("column_name").toUpperCase());
				relateColumn.setRelateTableName(rs.getString("relate_table_name").toUpperCase());
				relateColumn.setRelateColumnName(rs.getString("relate_column_name").toUpperCase());
				relateColumns.add(relateColumn);
			}  
		} catch (Exception e) {
			logger.error("根据表名获取关联字段数据失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return relateColumns;
	}

	public String getrelateColumnValueByRelateColumnName(int id,String RelateTableName,
			String relateColumnName) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	  
		String sql = "";  
		String returnValue = "";
		try { 
			sql = " select "+relateColumnName+" from "+RelateTableName+" where id = ?";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);  
//			ps.setString(1, relateColumnName);
//			ps.setString(2, RelateTableName);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs.next())
				returnValue = rs.getString(1);
		} catch (Exception e) {
			logger.error("根据表名和字段名和id获取字段值失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return returnValue;
	}

	public List<IC_column_qiuji_qiuhe> getQiujiColumns(String tableName) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	  
		String sql = "";  
		List<IC_column_qiuji_qiuhe> iC_column_qiuji_qiuhes = new ArrayList<IC_column_qiuji_qiuhe>();
		try { 
			sql = " select * from ic_column_qiuji_qiuhe where TABLE_NAME = ? order by qiuji_column_name";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);  
			ps.setString(1, tableName);
			rs = ps.executeQuery();
			while (rs.next()) {  
				IC_column_qiuji_qiuhe iC_column_qiuji_qiuhe = new IC_column_qiuji_qiuhe();
				iC_column_qiuji_qiuhe.setColumnName(rs.getString("column_name"));
				iC_column_qiuji_qiuhe.setTableName(rs.getString("table_name"));
				iC_column_qiuji_qiuhe.setIs_qiuhe(rs.getInt("is_qiuhe"));
				iC_column_qiuji_qiuhe.setIs_qiuji(rs.getInt("is_qiuji"));
				iC_column_qiuji_qiuhe.setIs_zuoweihe(rs.getInt("is_zuoweihe"));
				iC_column_qiuji_qiuhe.setIs_zuoweiji(rs.getInt("is_zuoweiji"));
				iC_column_qiuji_qiuhe.setQiujiColumnName(rs.getString("qiuji_column_name"));
				iC_column_qiuji_qiuhe.setQiuheColumnName(rs.getString("qiuhe_column_name"));
				iC_column_qiuji_qiuhe.setFrom_entity(rs.getInt("from_entity"));
				iC_column_qiuji_qiuhes.add(iC_column_qiuji_qiuhe);
				
			}  
		} catch (Exception e) {
			logger.error("根据表名获取求积关联信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return iC_column_qiuji_qiuhes;
	}

	public String checkColumnNameIsQiuji(String columnName, String tablename)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	  
		String sql = "";  
		String returnValue = "";
		try { 
			sql = " select qiuji_column_name from ic_column_qiuji_qiuhe where table_name = ? and column_name = ?";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);  
			ps.setString(1, tablename);
			ps.setString(2, columnName);
			rs = ps.executeQuery();
			if(rs.next())
				returnValue = rs.getString(1);
		} catch (Exception e) {
			logger.error("根据表查找求积关联字段失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return returnValue;
	}

	public void changeValueByCname(String columnName, String tableName,
			String changeValue) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	  
		String sql = "";  
		try { 
			sql = " update user_col_comments set comments = ? where column_name = ? AND table_name = ? ";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);  
			ps.setString(1, changeValue);
			ps.setString(2, columnName);
			ps.setString(3, tableName);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("修改字段页面显示和范围失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public void updateColumn(String tablename, String columnname,String value)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;	  
		String sql = "";  
//		String[] columns = columnname.indexOf(",")>=0?columnname.split(","):null;
		
		
		try { 
			ct = DBConnection.getConnection();
			sql = " COMMENT ON COLUMN "+tablename+ "." +columnname+" IS '"+value+"' " ;
			ps = ct.prepareStatement(sql);  
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("修改字段页面显示和范围失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	};
}
