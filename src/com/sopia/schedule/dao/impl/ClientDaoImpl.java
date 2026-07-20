package com.sopia.schedule.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.aqy.entities.TrainingStatus;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.duman.entities.Department;
import com.sopia.schedule.dao.ClientDao;
import com.sopia.schedule.entities.AuditMark;
import com.sopia.schedule.entities.Client;
import com.sopia.schedule.entities.Clientlinkcontact;
import com.sopia.schedule.entities.Clientlinkman;
import com.sopia.schedule.entities.Contact;
import com.sopia.schedule.entities.Contactstuff;
import com.sopia.schedule.entities.LogStuff;
import com.sopia.schedule.entities.Logfile;

public class ClientDaoImpl implements ClientDao
{
	private static final Log logger = LogFactory.getLog(ClientDaoImpl.class);
	
	public int insert_client(Client client) throws ElException
	{
		int clientid=-1;
		PreparedStatement ps = null;
		 ResultSet rs = null;
		 Connection ct = null;
		 String sql ="";
		 try {
			 ct = DBConnection.getConnection();
			 sql = "insert into tb_client " +
			 		"( " +
			 		" name,tel,tax,url,email, " +
			 		" city,addr,postcode,clientfrom,companytype, " +
			 		" industry,mainbusiness,companysize,startbusiness,registeredcapital, " +
			 		" legal,bank,bankaccount,duty,createdate," +
			 		" superclient,remark,userid,principalid,status" +
			 		"	) " +
			 		" values (" +
			 		" ?,?,?,?,?, " +  
			 		" ?,?,?,?,?, " +
			 		" ?,?,?,to_date(?,'yyyy-mm-dd'),?, " +  //4:date
			 		" ?,?,?,?,to_date(?,'yyyy-mm-dd'), " +  //5:date
			 		" ?,?,?,?,? " +     //3:int 4:int   others:string
			 		" )" ;


		 	 ps = ct.prepareStatement(sql);
		 	 
			 ps.setString(1,client.getName());
			 ps.setString(2,client.getTel());
			 ps.setString(3,client.getTax());
			 ps.setString(4,client.getUrl());
			 ps.setString(5,client.getEmail() );
			 
			 ps.setString(6,client.getCity() );
			 ps.setString(7,client.getAddr() );
			 ps.setString(8,client.getPostcode());
			 ps.setString(9,client.getClientfrom());
			 ps.setString(10,client.getCompanytype());
			 
			 ps.setString(11,client.getIndustry());
			 ps.setString(12,client.getMainbusiness());
			 ps.setString(13,client.getCompanysize());
			 ps.setString(14,client.getStartbusiness());
			 ps.setString(15,client.getRegisteredcapital() );
			 
			 ps.setString(16,client.getLegal() );
			 ps.setString(17, client.getBank());
			 ps.setString(18,client.getBankaccount());
			 ps.setString(19,client.getDuty());
			 ps.setString(20,client.getCreatedate());
			 
			 ps.setString(21,client.getSuperclient());
			 ps.setString(22,client.getRemark());
			 ps.setInt(23,client.getUserid() );
			 client.setPrincipalid(client.getUserid());
			 ps.setInt(24,client.getPrincipalid() );
			 ps.setString(25,"已创建");

			 ps.executeUpdate();
			 
			 
			 sql="select tb_client_sequence.currval  from dual";
			 ps = ct.prepareStatement(sql);
			 rs=ps.executeQuery();
			 if(rs.next())
			 {
				 clientid=rs.getInt(1);
			 }
			
		 } catch (Exception e) {
			 logger.error("客户添加出错！", e);
			 throw new ElException(e);
		 } finally {
			 DBConnection.closeConnectInfo(ct, ps, rs);
		 }
		 return clientid;
	}
	
	
	public void insert_clientlinkman_list(List<Clientlinkman> list,int clientid) throws ElException
	{
		PreparedStatement ps = null;
		 ResultSet rs = null;
		 Connection ct = null;
		 String sql ="";
		 try {
			 ct = DBConnection.getConnection();
			 sql = "insert into tb_clientlinkman " +
			 		"( " +
			 		" type,name,sex,dep,duty, " +
			 		" task,worktel,phone,tax,emainl, "    +
			 		" hometel,msnqq,birthday,hobby,clientid "    +
			 		" ) " +
			 		" values( " +
			 		" ?,?,?,?,?, " +  
			 		" ?,?,?,?,?, " +
			 		" ?,?,to_date(?,'yyyy-mm-dd'),? ,?" +		//13:date 15:int others:string  
			 		" )" ;
			 		

		 	 ps = ct.prepareStatement(sql);
		
		 	 for(int i=0;i<list.size();i++)
		 	 {
		 		 ps.setString(1,list.get(i).getType());
				 ps.setString(2,list.get(i).getName());
				 ps.setString(3,list.get(i).getSex());
				 ps.setString(4,list.get(i).getDep());
				 ps.setString(5,list.get(i).getDuty());
				 
				 ps.setString(6,list.get(i).getTask() );
				 ps.setString(7,list.get(i).getWorktel() );
				 ps.setString(8,list.get(i).getPhone());
				 ps.setString(9,list.get(i).getTax());
				 ps.setString(10,list.get(i).getEmainl());
				 
				 ps.setString(11,list.get(i).getHometel());
				 ps.setString(12,list.get(i).getMsnqq());
				 ps.setString(13,list.get(i).getBirthday());
				 ps.setString(14,list.get(i).getHobby());
				 ps.setInt(15,clientid);

				 ps.executeUpdate();
		 	 }
		 	 
			
			
		 } catch (Exception e) {
			 logger.error("客户添加出错！", e);
			 throw new ElException(e);
		 } finally {
			 DBConnection.closeConnectInfo(ct, ps, rs);
		 }
	}
	
	public List<Client> get_my_add_client(Client client,int pageNow,int pageSize) throws ElException
	{
		List<Client> list= new ArrayList<Client>();
		Client cl;
		PreparedStatement ps = null;
		 ResultSet rs = null;
		 Connection ct = null;
		 String sqlBegin ="";
		 String sqlEnd="";
		 String sql="";
		 String sqlcount="";
		 
		 try {
			 ct = DBConnection.getConnection();
			 
			 sqlBegin ="SELECT * FROM (" +
		 		"SELECT A.*, ROWNUM RN " +
		 		"FROM (" +
		 		"select t.*,e.username from tb_client t,eluser e where t.userid=? and t.userid=e.id " ;
			 
		 	sqlEnd =	" order by t.id  ) A " +
		 		" WHERE ROWNUM <= ? " +
		 		" )WHERE RN >= ?";
			 
		 	if(client.getName()!=null&&!client.getName().equals(""))
		 	{
		 		sql += " and t.name='"+client.getName()+"' ";
		 	}
		 	if(client.getIndustry()!=null&&!client.getIndustry().equals(""))
		 	{
		 		sql += " and t.industry='"+client.getIndustry()+"' ";
		 	}
		 	if(client.getCreatedate()!=null&&!client.getCreatedate().equals(""))
		 	{
		 		if(client.getCreatedate().length()>12)
		 			sql += " and to_char(t.createdate,'yyyy-mm-dd hh24:mm:ss')=";
		 		else
		 			sql += " and to_char(t.createdate,'yyyy-mm-dd')=";
		 		
		 		sql += " '"+client.getCreatedate()+"' ";
		 	}
		 	
		 	
		 	 ps = ct.prepareStatement(sqlBegin+sql+sqlEnd);
		 	 ps.setInt(1, client.getUserid());
		 	 ps.setInt(2, pageNow);
			 ps.setInt(3, pageSize);
		 	 
		 	 rs=ps.executeQuery();
			 while (rs.next()) 
			 {
				cl= new Client();
				
				cl.setId(rs.getInt("id"));
				cl.setName(rs.getString("name"));
				cl.setTel(rs.getString(3));
				cl.setTax(rs.getString("tax"));
				cl.setUrl(rs.getString("url"));
				
				cl.setEmail(rs.getString("email"));
				cl.setCity(rs.getString("city"));
				cl.setAddr(rs.getString("addr"));
				cl.setPostcode(rs.getString("postcode"));
				cl.setClientfrom(rs.getString("clientfrom"));
				
				cl.setCompanytype(rs.getString("companytype"));
				cl.setIndustry(rs.getString("industry"));
				cl.setMainbusiness(rs.getString("mainbusiness"));
				cl.setCompanysize(rs.getString("companysize"));
				cl.setStartbusiness(rs.getString("startbusiness"));
				
				cl.setRegisteredcapital(rs.getString("registeredcapital"));
				cl.setLegal(rs.getString("legal"));
				cl.setBank(rs.getString("bank"));
				cl.setBankaccount(rs.getString("bankaccount"));
				cl.setDuty(rs.getString("duty"));
				
				cl.setCreatedate(rs.getString("createdate"));
				cl.setSuperclient(rs.getString("superclient"));
				cl.setRemark(rs.getString("remark"));
				cl.setStatus(rs.getString("status"));
				cl.setUsername(rs.getString("username"));
						
				list.add(cl);
			}
		 	 
			
			
		 } catch (Exception e) {
			 logger.error("客户添加出错！", e);
			 throw new ElException(e);
		 } finally {
			 DBConnection.closeConnectInfo(ct, ps, rs);
		 }
		 
		 return list;
	}
	
	public int get_my_add_clientCount(Client client) throws ElException
	{
		int count=0;
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
	 	String sqlBegin ="";
	 	String sql="";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 
			 sqlBegin="select count(*) from tb_client t,eluser e where t.userid=? and t.userid=e.id ";

			 
			 
			 	if(client.getName()!=null&&!client.getName().equals(""))
			 	{
			 		sql += " and t.name='"+client.getName()+"' ";
			 	}
			 	if(client.getIndustry()!=null&&!client.getIndustry().equals(""))
			 	{
			 		sql += " and t.industry='"+client.getIndustry()+"' ";
			 	}
			 	if(client.getCreatedate()!=null&&!client.getCreatedate().equals(""))
			 	{
			 		if(client.getCreatedate().length()>12)
			 			sql += " and to_char(t.createdate,'yyyy-mm-dd hh24:mm:ss')=";
			 		else
			 			sql += " and to_char(t.createdate,'yyyy-mm-dd')=";
			 		
			 		sql += " '"+client.getCreatedate()+"' ";
			 	}
			 	
			 
			 
			 ps = ct.prepareStatement(sqlBegin+sql);
			 
			 ps.setInt(1, client.getUserid());
			 
			 
			 rs=ps.executeQuery();
			 if (rs.next()) 
			 {
				 count=rs.getInt(1);
			 }
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("我的客户查询出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		
		return count;
	}
	
	public List<Client> get_my_principal_client(int principalid,Client client) throws ElException
	{
		List<Client> list= new ArrayList<Client>();
		return list;
	}
	
	
	public Client get_client_by_id(int id) throws ElException
	{
		Client cl= new Client();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
	 	String sqlBegin ="";
	 	String sql="";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 
			 sqlBegin="select  " +
			 		" id,name,tel,tax,url," +
			 		" email,city,addr,postcode,clientfrom " +
			 		",companytype,industry,mainbusiness,companysize,to_char(startbusiness,'yyyy-mm-dd') startbusiness " +
			 		",registeredcapital,legal,bank,bankaccount,duty " +
			 		",to_char(createdate,'yyyy-mm-dd') createdate,superclient,remark,userid,principalid " +
			 		",status " +
			 		" from tb_client where id=? ";

			 ps = ct.prepareStatement(sqlBegin+sql);
			 
			 ps.setInt(1, id);
			 
			 rs=ps.executeQuery();
			 if (rs.next()) 
			 {
				 cl.setId(rs.getInt("id"));
				cl.setName(rs.getString("name"));
				cl.setTel(rs.getString("tel"));
				cl.setTax(rs.getString("tax"));
				cl.setUrl(rs.getString("url"));
				
				cl.setEmail(rs.getString("email"));
				cl.setCity(rs.getString("city"));
				cl.setAddr(rs.getString("addr"));
				cl.setPostcode(rs.getString("postcode"));
				cl.setClientfrom(rs.getString("clientfrom"));
				
				cl.setCompanytype(rs.getString("companytype"));
				cl.setIndustry(rs.getString("industry"));
				cl.setMainbusiness(rs.getString("mainbusiness"));
				cl.setCompanysize(rs.getString("companysize"));
				cl.setStartbusiness(rs.getString("startbusiness"));
				
				cl.setRegisteredcapital(rs.getString("registeredcapital"));
				cl.setLegal(rs.getString("legal"));
				cl.setBank(rs.getString("bank"));
				cl.setBankaccount(rs.getString("bankaccount"));
				cl.setDuty(rs.getString("duty"));
				
				cl.setCreatedate(rs.getString("createdate"));
				cl.setSuperclient(rs.getString("superclient"));
				cl.setRemark(rs.getString("remark"));
				cl.setStatus(rs.getString("status"));
				//cl.setUsername(rs.getString("username"));
		 }
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("我的客户查询出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		
		return cl;
	}
	
	public List<Clientlinkman> get_clientlinkman_by_userid(int clientid) throws ElException
	{
		List<Clientlinkman> list = new ArrayList<Clientlinkman>();
		
		Clientlinkman  clm;
		
		PreparedStatement ps = null;
		 ResultSet rs = null;
		 Connection ct = null;
		 String sql="";
		 try {
			 ct = DBConnection.getConnection();
			 
			 sql=" select  " +
			 		" id,type,name,sex,dep, " +
			 		" duty,task,worktel,phone,tax, " +
			 		" emainl,hometel,msnqq,to_char(birthday,'yyyy-mm-dd') birthday,hobby " +
			 		" from tb_clientlinkman where clientid=?   order by id ";
		 	
		 	
		 	 ps = ct.prepareStatement(sql);
		 	 ps.setInt(1, clientid);
		 	 
		 	 rs=ps.executeQuery();
			 while (rs.next()) 
			 {
				clm= new Clientlinkman();
				
				clm.setId(rs.getInt("id"));
				clm.setType(rs.getString("type"));
				clm.setName(rs.getString("name"));
				clm.setSex(rs.getString("sex"));
				clm.setDep(rs.getString("dep"));
				
				clm.setDuty(rs.getString("duty"));
				clm.setTask(rs.getString("task"));
				clm.setWorktel(rs.getString("worktel"));
				clm.setPhone(rs.getString("phone"));
				clm.setTax(rs.getString("tax"));
					
				clm.setEmainl(rs.getString("emainl"));
				clm.setHometel(rs.getString("hometel"));
				clm.setMsnqq(rs.getString("msnqq"));
				clm.setBirthday(rs.getString("birthday"));
				clm.setHobby(rs.getString("hobby"));
				
				list.add(clm);
			}
		 	 
			
			
		 } catch (Exception e) {
			 logger.error("客户联系人查询出错！", e);
			 throw new ElException(e);
		 } finally {
			 DBConnection.closeConnectInfo(ct, ps, rs);
		 }
		
		
		
		return list;
	}
	
	public void del_client_by_id(int id) throws ElException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
	 	String sql ="";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 
			 sql="delete from tb_client where id=?";
			 
			 ps = ct.prepareStatement(sql);
			 
			 ps.setInt(1, id);
			
			 ps.executeUpdate();
			 
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("客户表删除出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public void del_clientlinkman_by_clientid(int clientid) throws ElException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
	 	String sql ="";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 
			 sql="delete from tb_clientlinkman where clientid=?";
			 
			 ps = ct.prepareStatement(sql);
			 
			 ps.setInt(1, clientid);
			
			 ps.executeUpdate();
			 
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("客户表联系人删除出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public List<Integer> get_ids_of_clientlinkman_by_clientid(int clientid)throws ElException
	{
		List<Integer> list= new ArrayList<Integer>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
	 	String sql ="";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 sql="select id from tb_clientlinkman where clientid=? ";
			 ps = ct.prepareStatement(sql);
			 ps.setInt(1, clientid);
			 rs=ps.executeQuery();
			 while(rs.next())
			 {
				 list.add(rs.getInt(1));
			 }
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("联系人查询id出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return  list;
		
	}
	public void update_client(Client client) throws ElException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
	 	String sql ="";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 sql="update  tb_client " +
			 		" set " +	
			 		
			 		" name=?, " +//1
			 		" tel=?, " +
			 		" tax=?, " +
			 		" url=?, " +
			 		" email=?, " +
			 		
			 		" city=?, " +//6
			 		" addr=?, " +
			 		" postcode=?, " +
			 		" clientfrom=?, " +
			 		" companytype=?, " +

			 		" industry=?, " +//11
			 		" mainbusiness=?, " +
			 		" companysize=?, " +
			 		" startbusiness=to_date(?,'yyyy-mm-dd hh24:mm:ss'), " +
			 		" registeredcapital=?, " +

			 		" legal=?, " +//16
			 		" bank=?, " +
			 		" bankaccount=?, " +
			 		" duty=?, " +
			 		" createdate=to_date(?,'yyyy-mm-dd hh24:mm:ss'), " +

			 		" superclient=?, " +//21
			 		" remark=?, " +
		//	 		" userid=?, " +
			 		" principalid=?, " +
			 		" status=? " +

			 		" where id=? ";
			 
			 ps = ct.prepareStatement(sql);
			 
			 ps.setString(1, client.getName());
			 ps.setString(2, client.getTel());
			 ps.setString(3, client.getTax());
			 ps.setString(4, client.getUrl());
			 ps.setString(5, client.getEmail());
			 
			 ps.setString(6, client.getCity());
			 ps.setString(7, client.getAddr());
			 ps.setString(8, client.getPostcode());
			 ps.setString(9, client.getClientfrom());
			 ps.setString(10, client.getCompanytype());
			 
			 ps.setString(11, client.getIndustry());
			 ps.setString(12, client.getMainbusiness());
			 ps.setString(13, client.getCompanysize());
			 ps.setString(14, client.getStartbusiness());
			 ps.setString(15, client.getRegisteredcapital());
			 
			 ps.setString(16, client.getLegal());
			 ps.setString(17, client.getBank());
			 ps.setString(18, client.getBankaccount());
			 ps.setString(19, client.getDuty());
			 ps.setString(20, client.getCreatedate());
			 
			 ps.setString(21, client.getSuperclient());
			 ps.setString(22, client.getRemark());
			 ps.setInt(23, client.getPrincipalid());
			 ps.setString(24, client.getStatus());
			 
			 ps.setInt(25, client.getId());
			 
			 
			 ps.executeUpdate();
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("联系人查询id出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public void update_clientlinkman(Clientlinkman clm) throws ElException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
	 	String sql ="";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 sql=" update tb_clientlinkman set "+
				 " type=?, " +
				 " 	name=?, " +
				 " 	 sex=?, " +
				 " 	 dep=?, " +
				 " 	 duty=?, " +
				 
				 " 	 task=?, " +
				 " 	 worktel=?, " +
				 " 	 phone=?, " +
				 " 	 tax=?, " +
				 " 	 emainl=?, " +
				 
				 " 	 hometel=?, " +
				 " 	 msnqq=?, " +
				 " 	 birthday=to_date(?,'yyyy-mm-dd hh24:mm:ss'), " +
				 " 	 hobby=? " +
				 " where id=? " ;
			 
			 
			 ps = ct.prepareStatement(sql);
			 
			 ps.setString(1, clm.getType());
			 ps.setString(2, clm.getName());
			 ps.setString(3, clm.getSex());
			 ps.setString(4, clm.getDep());
			 ps.setString(5, clm.getDuty());
			 
			 ps.setString(6, clm.getTask());
			 ps.setString(7, clm.getWorktel());
			 ps.setString(8, clm.getPhone());
			 ps.setString(9, clm.getTax());
			 ps.setString(10, clm.getEmainl());
			 
			 ps.setString(11, clm.getHometel());
			 ps.setString(12, clm.getMsnqq());
			 ps.setString(13, clm.getBirthday());
			 ps.setString(14, clm.getHobby());
			 
			 ps.setInt(15, clm.getId());
			 
			 
			 ps.executeUpdate();
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("联系人查询id出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public void del_clientlinkman_by_id(int id) throws ElException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
	 	String sql ="";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 
			 sql="delete from tb_clientlinkman where id=?";
			 
			 ps = ct.prepareStatement(sql);
			 
			 ps.setInt(1, id);
			
			 ps.executeUpdate();
			 
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("客户表联系人删除出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	
	public  int insert_contact(Contact contact)  throws ElException
	{
		 int currentid=-1;
		 PreparedStatement ps = null;
		 ResultSet rs = null;
		 Connection ct = null;
		 String sql ="";
		 try {
			 ct = DBConnection.getConnection();
			 sql = "insert into tb_contact " +
			 		"( theme,type,content,time,money,re_client,userid,createtime ) " +
			 		" values(" +
					" ?,?,?,to_date(?,'yyyy-mm-dd'),?, "+	//1			
					" ? ,?,sysdate"+	
			 		" ) ";

		 	 ps = ct.prepareStatement(sql);
		 	 
			 ps.setString(1,contact.getTheme());
			 ps.setString(2,contact.getType());
			 ps.setString(3,contact.getContent());
			 ps.setString(4,contact.getTime());
			 ps.setString(5, contact.getMoney());

			 ps.setString(6, contact.getRe_client());
			// ps.setString(7, contact.getCreatetime());
			 ps.setInt(7, contact.getUserid());

			 ps.executeUpdate();
			 
			 //---------------------
			 sql="select tb_contact_sequence.currval  from dual";
			 ps = ct.prepareStatement(sql);
			 rs=ps.executeQuery();
			 if(rs.next())
			 {
				 currentid=rs.getInt(1);
			 }

		 } catch (Exception e) {
			 logger.error("日志添加出错！", e);
			 throw new ElException(e);
		 } finally {
			 DBConnection.closeConnectInfo(ct, ps, rs);
		 }
		return currentid;
	}
	
	public  void insert_contact_stuff(Contactstuff contactstuff)  throws ElException
	{
		 PreparedStatement ps = null;
		 ResultSet rs = null;
		 Connection ct = null;
		 String sql ="";
		 try {
			 ct = DBConnection.getConnection();
			 sql = "insert into tb_contact_stuff " +
			 		" (contactid,stuffaddr,title) " +
			 		" values( " +
					" ?, "+	//1		logid		
					" ?, "+	//2		stuffaddr	
					" ? "+	//3		title	 	
			 		" ) ";

		 	 ps = ct.prepareStatement(sql);
		 	 
			 ps.setInt(1,contactstuff.getContactid());
			 ps.setString(2,contactstuff.getStuffaddr());
			 ps.setString(3,contactstuff.getTitle());

			 ps.executeUpdate();
			 
			 //---------------------

		 } catch (Exception e) {
			 logger.error("日志附件添加出错！", e);
			 throw new ElException(e);
		 } finally {
			 DBConnection.closeConnectInfo(ct, ps, rs);
		 }
	}
	
	
	public List<Contact> select_my_contact(Contact contact,int pageNow,int pageSize) throws  ElException
	{
		List<Contact> list= new ArrayList<Contact>();
		Contact cot;
		PreparedStatement ps = null;
		 ResultSet rs = null;
		 Connection ct = null;
		 String sqlBegin ="";
		 String sqlEnd="";
		 String sql="";
		 String sqlcount="";
		 try {
			 ct = DBConnection.getConnection();
			 
			 sqlBegin ="SELECT * FROM (" +
		 		"SELECT A.*, ROWNUM RN " +
		 		"FROM (" +
		 		"select t.id,t.theme,t.type,t.content,to_char(t.time,'yyyy-mm-dd') time,t.money,t.re_client,to_char(t.createtime,'yyyy-mm-dd') "+
		 		" from tb_contact t where t.userid=?  " ;
			 
		 	sqlEnd =	" order by t.id  ) A " +
		 		" WHERE ROWNUM <= ? " +
		 		" )WHERE RN >= ?";
			 
		 	if(contact.getTheme()!=null&&!contact.getTheme().equals(""))
		 	{
		 		//sql += " and t.theme='"+contact.getTheme()+"' ";
		 		sql += " and t.theme like '"+contact.getTheme()+"%' ";
		 	}
		 	if(contact.getType()!=null&&!contact.getType().equals(""))
		 	{
		 		sql += " and t.type='"+contact.getType()+"' ";
		 	}
		 	if(contact.getContent()!=null&&!contact.getContent().equals(""))
		 	{
		 		sql += " and t.content like '"+contact.getContent()+"' ";
		 	}
		 	
		 	if(contact.getBegintime()!=null&&!contact.getBegintime().equals(""))
		 	{
		 		if(contact.getEndtime()!=null&&!contact.getEndtime().equals(""))
		 		{
		 			sql += " and t.time>= to_date('"+contact.getBegintime()+"','yyyy-mm-dd hh24:mi:ss') " +
		 					" and t.time<= to_date('"+contact.getEndtime()+"','yyyy-mm-dd hh24:mi:ss')  " ;
		 		}
		 		else
		 		{
		 			sql += " and t.time>= to_date('"+contact.getBegintime()+"','yyyy-mm-dd hh24:mi:ss') ";
		 		}
		 	}
		 	else if(contact.getEndtime()!=null&&!contact.getEndtime().equals(""))
		 	{
		 		sql +=" and t.time<= to_date('"+contact.getEndtime()+"','yyyy-mm-dd hh24:mi:ss')  " ;
		 	}
		 	
//		 	if(client.getCreatedate()!=null&&!client.getCreatedate().equals(""))
//		 	{
//		 		if(client.getCreatedate().length()>12)
//		 			sql += " and to_char(t.createdate,'yyyy-mm-dd hh24:mm:ss')=";
//		 		else
//		 			sql += " and to_char(t.createdate,'yyyy-mm-dd')=";
//		 		
//		 		sql += " '"+client.getCreatedate()+"' ";
//		 	}
//		 	
		 	
		 	 ps = ct.prepareStatement(sqlBegin+sql+sqlEnd);
		 	 ps.setInt(1, contact.getUserid());
		 	 ps.setInt(2, pageNow);
			 ps.setInt(3, pageSize);
		 	 
		 	 rs=ps.executeQuery();
			 while (rs.next()) 
			 {
				 cot= new Contact();
				
				 	cot.setId(Integer.valueOf(rs.getString("id")));
					cot.setTheme(rs.getString("theme"));
					cot.setType(rs.getString("type"));
					cot.setContent(rs.getString("content"));
					cot.setTime(rs.getString("time"));
					
					cot.setMoney(rs.getString("money"));
					cot.setRe_client(rs.getString("re_client"));
					//cot.setCreatetime(rs.getString("createtime"));
			//		cot.setUsername(rs.getString("username"));
					
						
				list.add(cot);
			}
		 	 
			
			
		 } catch (Exception e) {
			 logger.error("客户添加出错！", e);
			 throw new ElException(e);
		 } finally {
			 DBConnection.closeConnectInfo(ct, ps, rs);
		 }
		 
		 return list;
	}
	public int select_my_contact_count(Contact contact) throws  ElException
	{
		int count=0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
	 	String sqlBegin ="";
	 	String sql="";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 
			 sqlBegin="select count(*) from tb_contact t,eluser e where t.userid=? and t.userid=e.id ";

			 	if(contact.getTheme()!=null&&!contact.getTheme().equals(""))
			 	{
			 		//sql += " and t.theme='"+contact.getTheme()+"' ";
			 		sql += " and t.theme like '"+contact.getTheme()+"%' ";
			 	}
			 	if(contact.getType()!=null&&!contact.getType().equals(""))
			 	{
			 		sql += " and t.type='"+contact.getType()+"' ";
			 	}
				if(contact.getContent()!=null&&!contact.getContent().equals(""))
			 	{
			 		sql += " and t.content like '"+contact.getContent()+"' ";
			 	}
			 	
			 	if(contact.getBegintime()!=null&&!contact.getBegintime().equals(""))
			 	{
			 		if(contact.getEndtime()!=null&&!contact.getEndtime().equals(""))
			 		{
			 			sql += " and t.time>= to_date('"+contact.getBegintime()+"','yyyy-mm-dd hh24:mi:ss') " +
			 					" and t.time<= to_date('"+contact.getEndtime()+"','yyyy-mm-dd hh24:mi:ss')  " ;
			 		}
			 		else
			 		{
			 			sql += " and t.time>= to_date('"+contact.getBegintime()+"','yyyy-mm-dd hh24:mi:ss') ";
			 		}
			 	}
			 	else if(contact.getEndtime()!=null&&!contact.getEndtime().equals(""))
			 	{
			 		sql +=" and t.time<= to_date('"+contact.getEndtime()+"','yyyy-mm-dd hh24:mi:ss')  " ;
			 	}
			 	
//			 
//			 	if(client.getName()!=null&&!client.getName().equals(""))
//			 	{
//			 		sql += " and t.name='"+client.getName()+"' ";
//			 	}
//			 	if(client.getIndustry()!=null&&!client.getIndustry().equals(""))
//			 	{
//			 		sql += " and t.industry='"+client.getIndustry()+"' ";
//			 	}
//			 	if(client.getCreatedate()!=null&&!client.getCreatedate().equals(""))
//			 	{
//			 		if(client.getCreatedate().length()>12)
//			 			sql += " and to_char(t.createdate,'yyyy-mm-dd hh24:mm:ss')=";
//			 		else
//			 			sql += " and to_char(t.createdate,'yyyy-mm-dd')=";
//			 		
//			 		sql += " '"+client.getCreatedate()+"' ";
//			 	}
//			 	
			 
			 
			 ps = ct.prepareStatement(sqlBegin+sql);
			 
			 ps.setInt(1, contact.getUserid());
			 
			 
			 rs=ps.executeQuery();
			 if (rs.next()) 
			 {
				 count=rs.getInt(1);
			 }
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("我的客户查询出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		
		return count;
	}
	
	/*
	 * 删除联系行为
	 */
	public void delete_contact_by_id(int id) throws ElException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
	 	String sql ="";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 
			 sql="delete from tb_contact where id=?";
			 
			 ps = ct.prepareStatement(sql);
			 
			 ps.setInt(1, id);
			
			 ps.executeUpdate();
			 
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("联系行为删除出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/*
	 * 通过id查询 联系行为
	 */
	public Contact get_contact_by_id(int id) throws ElException
	{
		Contact cot= new Contact();
		
		PreparedStatement ps = null;
		 ResultSet rs = null;
		 Connection ct = null;
		 String sql="";
		 try {
			 ct = DBConnection.getConnection();
			 
			 sql=" select id,theme,type,content,to_char(time,'yyyy-mm-dd') time,money,re_client" +
			 		" ,to_char(createtime,'yyyy-mm-dd') createtime " +
			 		" from tb_contact where id=? ";
			 
		 	
		 	 ps = ct.prepareStatement(sql);
		 	 ps.setInt(1, id);
		 	 rs=ps.executeQuery();
			 if (rs.next()) 
			 {
				
				 	cot.setId(Integer.valueOf(rs.getString("id")));
					cot.setTheme(rs.getString("theme"));
					cot.setType(rs.getString("type"));
					cot.setContent(rs.getString("content"));
					cot.setTime(rs.getString("time"));
					
					cot.setMoney(rs.getString("money"));
					cot.setRe_client(rs.getString("re_client"));
					cot.setCreatetime(rs.getString("createtime"));
			}
		 	 
			
			
		 } catch (Exception e) {
			 logger.error("联系行为查询出错！", e);
			 throw new ElException(e);
		 } finally {
			 DBConnection.closeConnectInfo(ct, ps, rs);
		 }
		return cot;
	}
	
	/*
	 * 查询联系行为附件
	 */
	public List<Contactstuff> get_contact_stuff_list_by_contactid(int contactid) throws ElException
	{
		List<Contactstuff>  list = new ArrayList<Contactstuff>();
		
		Contactstuff cs;
		
		PreparedStatement ps = null;
		 ResultSet rs = null;
		 Connection ct = null;
		 String sql="";
		 try {
			 ct = DBConnection.getConnection();
			 
			 sql=" select id,contactid,stuffaddr,title " +
			 		" from tb_contact_stuff where contactid=? ";
			 
		 	 ps = ct.prepareStatement(sql);
		 	 ps.setInt(1, contactid);
		 	 rs=ps.executeQuery();
			 while (rs.next()) 
			 {
				 cs= new Contactstuff();
			 	cs.setId(rs.getInt("id"));
			 	cs.setContactid(rs.getInt("contactid"));
			 	cs.setStuffaddr(rs.getString("stuffaddr"));
			 	cs.setTitle(rs.getString("title"));
			 	list.add(cs);
			}
		 } catch (Exception e) {
			 logger.error("联系行为查询出错！", e);
			 throw new ElException(e);
		 } finally {
			 DBConnection.closeConnectInfo(ct, ps, rs);
		 }
		return list;
	}
	

	/*
	 * update contact
	 */
	public void update_contact_by_id(Contact contact) throws ElException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
	 	String sql ="";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 
			 sql="update  tb_contact " +
			 		" set " +
		 		" theme=? ,type=? ,content=? ,  " +
		 		" time=to_date(?,'yyyy-mm-dd hh24:mi:ss') ,money=? ,re_client=?  " +
		 		" where id=? ";
		 
		 ps = ct.prepareStatement(sql);
		 
		 ps.setString(1, contact.getTheme());
		 ps.setString(2, contact.getType());
		 ps.setString(3, contact.getContent());
		 ps.setString(4, contact.getTime());
		 ps.setString(5, contact.getMoney());
		 
		 ps.setString(6, contact.getRe_client());
		 ps.setInt(7, contact.getId());
		 
		 ps.executeUpdate();
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("客户联系行为删除出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	//根据contactid  删除 联系行为
	public void delete_contact_stuff_by_contactid(int contactid) throws ElException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
	 	String sql ="";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 
			 sql=" delete from tb_contact_stuff where contactid=? ";
			 ps = ct.prepareStatement(sql);
			 ps.setInt(1, contactid);
			 ps.executeUpdate();
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("客户联系行为删除出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public void delete_contact_stuff_by_id(int id) throws ElException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
	 	String sql ="";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 
			 sql=" delete from tb_contact_stuff where id=? ";
			 ps = ct.prepareStatement(sql);
			 ps.setInt(1, id);
			 ps.executeUpdate();
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("客户联系行为删除出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	//根据id获得contactstuff
	public Contactstuff get_contactstuff_by_id(int id) throws ElException
	{
		Contactstuff cs = new Contactstuff();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
	 	String sql ="";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 
			 sql=" select * from tb_contact_stuff where id=? ";
			 ps = ct.prepareStatement(sql);
			 ps.setInt(1, id);
			 rs=ps.executeQuery();
			 if(rs.next())
			 {
				 cs.setId(rs.getInt("id"));
				 cs.setStuffaddr(rs.getString("stuffaddr"));
				 cs.setTitle(rs.getString("title"));
				 //cs.setContactid(contactid)
			 }
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("客户联系行为删除出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cs;
	}
	
	
//	select * from tb_contact where userid in 
//	(select id from eluser where   depid in
//	   (select id from department where lid >= 1 and rid <= 5000)   
//	  )
	public List<Contact> get_contact_by_dep(Contact contact,Department department,int pageNow,int pageSize) throws ElException
	{
		List<Contact> list=new ArrayList<Contact>();
		Contact con =null;//= new Schedule();
		//Schedule schedule=new Schedule();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sqlBegin="";
		String sqlEnd="";
	 	String sql ="";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 //sql="select * from tb_schedule where sc_userid in (select id from eluser where depid = ? and valid=1 )";		
			 sqlBegin="SELECT * FROM (SELECT A.*, ROWNUM RN " +
			 		" FROM ( " +
			 		" select " +
			 		" t.id,t.theme,t.type,t.content,to_char(t.time,'yyyy-mm-dd') time," +
			 		" t.money,t.re_client,to_char(t.createtime,'yyyy-mm-dd') createtime " +
			 		" from tb_contact t where userid in (select id from eluser where  " +
			 		"  depid in" +
			 		" (select id from department where lid >= ? and rid <= ?)   ) " ;
			 	sqlEnd= 		" order by t.id ) A " +
			 		" WHERE ROWNUM <= ? ) " +
			 		" WHERE RN >= ? ";
			 	
			 	
			 	
			 	if(contact.getTheme()!=null&&!contact.getTheme().equals(""))
			 	{
			 		//sql += " and t.theme='"+contact.getTheme()+"' ";
			 		sql += " and t.theme like '"+contact.getTheme()+"%' ";
			 	}
			 	if(contact.getType()!=null&&!contact.getType().equals(""))
			 	{
			 		sql += " and t.type='"+contact.getType()+"' ";
			 	}
				if(contact.getContent()!=null&&!contact.getContent().equals(""))
			 	{
			 		sql += " and t.content like '"+contact.getContent()+"%' ";
			 	}
			 	
			 	if(contact.getBegintime()!=null&&!contact.getBegintime().equals(""))
			 	{
			 		if(contact.getEndtime()!=null&&!contact.getEndtime().equals(""))
			 		{
			 			sql += " and t.time>= to_date('"+contact.getBegintime()+"','yyyy-mm-dd hh24:mi:ss') " +
			 					" and t.time<= to_date('"+contact.getEndtime()+"','yyyy-mm-dd hh24:mi:ss')  " ;
			 		}
			 		else
			 		{
			 			sql += " and t.time>= to_date('"+contact.getBegintime()+"','yyyy-mm-dd hh24:mi:ss') ";
			 		}
			 	}
			 	else if(contact.getEndtime()!=null&&!contact.getEndtime().equals(""))
			 	{
			 		sql +=" and t.time<= to_date('"+contact.getEndtime()+"','yyyy-mm-dd hh24:mi:ss')  " ;
			 	}
			 	
			 	
			 	
			 	
			 ps = ct.prepareStatement(sqlBegin+sql+sqlEnd);
			 ps.setInt(1, department.getLid());
			 ps.setInt(2, department.getRid());
			 ps.setInt(3, pageNow);
			 ps.setInt(4, pageSize);
			 
			 rs=ps.executeQuery();
			 
			 while (rs.next()) 
			 {
				 con = new Contact();
				 
				 con.setId(Integer.valueOf(rs.getString("id")));
					con.setTheme(rs.getString("theme"));
					con.setType(rs.getString("type"));
					con.setContent(rs.getString("content"));
					con.setTime(rs.getString("time"));
					
					con.setMoney(rs.getString("money"));
					con.setRe_client(rs.getString("re_client"));
					//contact.setCreatetime(rs.getString("createtime"));
				 
				 list.add(con);
			 }
	
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("联系行为查询出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}
	public int get_contact_by_dep_count(Contact contact,Department department) throws ElException
	{
		int count=0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sqlBegin="";
	 	String sql ="";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 //sql="select count(*) from tb_log where log_userid in (select id from eluser where lid >= ? and rid <= ? and valid=1 )";	
			 sqlBegin="select count(*) from tb_contact t where userid in " +
			 		" (" +
			 		"   select id from eluser where  depid in " +
			 		" ( select id from department where lid >= ? and rid <= ?)" +
			 		" ) ";
			 
			 if(contact.getTheme()!=null&&!contact.getTheme().equals(""))
			 	{
			 		//sql += " and t.theme='"+contact.getTheme()+"' ";
			 		sql += " and t.theme like '"+contact.getTheme()+"%' ";
			 	}
			 	if(contact.getType()!=null&&!contact.getType().equals(""))
			 	{
			 		sql += " and t.type='"+contact.getType()+"' ";
			 	}
				if(contact.getContent()!=null&&!contact.getContent().equals(""))
			 	{
			 		sql += " and t.content like '"+contact.getContent()+"%' ";
			 	}
			 	
			 	if(contact.getBegintime()!=null&&!contact.getBegintime().equals(""))
			 	{
			 		if(contact.getEndtime()!=null&&!contact.getEndtime().equals(""))
			 		{
			 			sql += " and t.time>= to_date('"+contact.getBegintime()+"','yyyy-mm-dd hh24:mi:ss') " +
			 					" and t.time<= to_date('"+contact.getEndtime()+"','yyyy-mm-dd hh24:mi:ss')  " ;
			 		}
			 		else
			 		{
			 			sql += " and t.time>= to_date('"+contact.getBegintime()+"','yyyy-mm-dd hh24:mi:ss') ";
			 		}
			 	}
			 	else if(contact.getEndtime()!=null&&!contact.getEndtime().equals(""))
			 	{
			 		sql +=" and t.time<= to_date('"+contact.getEndtime()+"','yyyy-mm-dd hh24:mi:ss')  " ;
			 	}
			 
			 
			 ps = ct.prepareStatement(sqlBegin+sql);
			 ps.setInt(1, department.getLid());
			 ps.setInt(2,department.getRid());
			 
			 rs=ps.executeQuery();
			 
			 if (rs.next()) 
			 {
				 count=rs.getInt(1);
			 }
	
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("联系行为COUNT查询出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}
	
	/*
	 * 查询客户
	 */
	public List<Client> get_client_my_by_dep(Client client,Department department,int pageNow,int pageSize) throws ElException
	{
		List<Client> list=new ArrayList<Client>();
		Client cl =null;//= new Schedule();
		//Schedule schedule=new Schedule();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sqlBegin="";
		String sqlEnd="";
	 	String sql ="";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 //sql="select * from tb_schedule where sc_userid in (select id from eluser where depid = ? and valid=1 )";		
			 sqlBegin="SELECT * FROM (SELECT A.*, ROWNUM RN " +
			 		" FROM ( " +
			 		" select " +
			 		" t.id,t.name,t.tel,t.tax,t.url, " +
			 		" t.email,t.city,t.addr,t.postcode,t.clientfrom, " +
			 		" t.companytype,t.industry,t.mainbusiness,t.companysize,to_char(t.startbusiness,'yyyy-mm-dd') startbusiness, " +
			 		" t.registeredcapital,t.legal,t.bank,t.bankaccount,t.duty, " +
			 		" to_char(t.createdate,'yyyy-mm-dd') createdate,t.superclient,t.remark,t.userid,t.principalid,t.status, e.username" +
			 		" from tb_client t ,eluser e where userid in (select id from eluser where  " +
			 		"  depid in" +
			 		" (select id from department where lid >= ? and rid <= ?)   ) " ;
			 sqlEnd= 			" and t.principalid="+client.getPrincipalid() +" "+
			 					" and t.userid= e.id order by t.id " +
			 			" ) A " +
			 		" WHERE ROWNUM <= ? ) " +
			 		" WHERE RN >= ? ";
			 	
			 	
			 	
			 	if(client.getName()!=null&&!client.getName().equals(""))
			 	{
			 		sql += " and t.name like '"+client.getName()+"%' ";
			 	}
			 	
			 	if(client.getIndustry()!=null&&!client.getIndustry().equals(""))
			 	{
			 		sql += " and t.industry='"+client.getIndustry()+"' ";
			 	}
//				if(contact.getContent()!=null&&!contact.getContent().equals(""))
//			 	{
//			 		sql += " and t.content like '"+contact.getContent()+"%' ";
//			 	}
//			 	
			 	if(client.getBegintime()!=null&&!client.getBegintime().equals(""))
			 	{
			 		if(client.getEndtime()!=null&&!client.getEndtime().equals(""))
			 		{
			 			sql += " and t.createdate>= to_date('"+client.getBegintime()+"','yyyy-mm-dd hh24:mi:ss') " +
			 					" and t.createdate<= to_date('"+client.getEndtime()+"','yyyy-mm-dd hh24:mi:ss')  " ;
			 		}
			 		else
			 		{
			 			sql += " and t.createdate>= to_date('"+client.getBegintime()+"','yyyy-mm-dd hh24:mi:ss') ";
			 		}
			 	}
			 	else if(client.getEndtime()!=null&&!client.getEndtime().equals(""))
			 	{
			 		sql +=" and t.createdate<= to_date('"+client.getEndtime()+"','yyyy-mm-dd hh24:mi:ss')  " ;
			 	}
			 	
			 	
			 	
			 	
			 ps = ct.prepareStatement(sqlBegin+sql+sqlEnd);
			 ps.setInt(1, department.getLid());
			 ps.setInt(2, department.getRid());
			 ps.setInt(3, pageNow);
			 ps.setInt(4, pageSize);
			 
			 rs=ps.executeQuery();
			 
			 while (rs.next()) 
			 {
				 cl= new Client();
					
					cl.setId(rs.getInt("id"));
					cl.setName(rs.getString("name"));
					cl.setTel(rs.getString(3));
					cl.setTax(rs.getString("tax"));
					cl.setUrl(rs.getString("url"));
					
					cl.setEmail(rs.getString("email"));
					cl.setCity(rs.getString("city"));
					cl.setAddr(rs.getString("addr"));
					cl.setPostcode(rs.getString("postcode"));
					cl.setClientfrom(rs.getString("clientfrom"));
					
					cl.setCompanytype(rs.getString("companytype"));
					cl.setIndustry(rs.getString("industry"));
					cl.setMainbusiness(rs.getString("mainbusiness"));
					cl.setCompanysize(rs.getString("companysize"));
					cl.setStartbusiness(rs.getString("startbusiness"));
					
					cl.setRegisteredcapital(rs.getString("registeredcapital"));
					cl.setLegal(rs.getString("legal"));
					cl.setBank(rs.getString("bank"));
					cl.setBankaccount(rs.getString("bankaccount"));
					cl.setDuty(rs.getString("duty"));
					
					cl.setCreatedate(rs.getString("createdate"));
					cl.setSuperclient(rs.getString("superclient"));
					cl.setRemark(rs.getString("remark"));
					cl.setStatus(rs.getString("status"));
					cl.setUsername(rs.getString("username"));
							
					list.add(cl);
			 }
	
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("联系行为查询出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}
	public int  get_client_my_by_dep_count(Client client,Department department) throws ElException
	{
		int count=0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sqlBegin="";
	 	String sql ="";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 //sql="select count(*) from tb_log where log_userid in (select id from eluser where lid >= ? and rid <= ? and valid=1 )";	
			 sqlBegin="select count(*) from tb_client t where userid in " +
			 		" (" +
			 		"   select id from eluser where  depid in " +
			 		" ( select id from department where lid >= ? and rid <= ?)" +
			 		" ) " +
			 		" and t.principalid="+client.getPrincipalid() +" " +
			 				" ";
			 
			 
				if(client.getName()!=null&&!client.getName().equals(""))
			 	{
			 		sql += " and t.name like '"+client.getName()+"%' ";
			 	}
			 
			 	
			 	if(client.getIndustry()!=null&&!client.getIndustry().equals(""))
			 	{
			 		sql += " and t.industry='"+client.getIndustry()+"' ";
			 	}
			 	
			 	if(client.getBegintime()!=null&&!client.getBegintime().equals(""))
			 	{
			 		if(client.getEndtime()!=null&&!client.getEndtime().equals(""))
			 		{
			 			sql += " and t.createdate>= to_date('"+client.getBegintime()+"','yyyy-mm-dd hh24:mi:ss') " +
			 					" and t.createdate<= to_date('"+client.getEndtime()+"','yyyy-mm-dd hh24:mi:ss')  " ;
			 		}
			 		else
			 		{
			 			sql += " and t.createdate>= to_date('"+client.getBegintime()+"','yyyy-mm-dd hh24:mi:ss') ";
			 		}
			 	}
			 	else if(client.getEndtime()!=null&&!client.getEndtime().equals(""))
			 	{
			 		sql +=" and t.createdate<= to_date('"+client.getEndtime()+"','yyyy-mm-dd hh24:mi:ss')  " ;
			 	}
			 
			 
			 ps = ct.prepareStatement(sqlBegin+sql);
			 ps.setInt(1, department.getLid());
			 ps.setInt(2,department.getRid());
			 
			 rs=ps.executeQuery();
			 
			 if (rs.next()) 
			 {
				 count=rs.getInt(1);
			 }
	
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("联系行为COUNT查询出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		return count;
		
	}
	
	
	/*
	 * 查询客户
	 */
	public List<Client> get_client_by_dep(Client client,Department department,int pageNow,int pageSize) throws ElException
	{
		List<Client> list=new ArrayList<Client>();
		Client cl =null;//= new Schedule();
		//Schedule schedule=new Schedule();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sqlBegin="";
		String sqlEnd="";
	 	String sql ="";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 //sql="select * from tb_schedule where sc_userid in (select id from eluser where depid = ? and valid=1 )";		
			 sqlBegin="SELECT * FROM (SELECT A.*, ROWNUM RN " +
			 		" FROM ( " +
			 		" select " +
			 		" t.id,t.name,t.tel,t.tax,t.url, " +
			 		" t.email,t.city,t.addr,t.postcode,t.clientfrom, " +
			 		" t.companytype,t.industry,t.mainbusiness,t.companysize,to_char(t.startbusiness,'yyyy-mm-dd') startbusiness, " +
			 		" t.registeredcapital,t.legal,t.bank,t.bankaccount,t.duty, " +
			 		" to_char(t.createdate,'yyyy-mm-dd') createdate,t.superclient,t.remark,t.userid,t.principalid,t.status, e.username" +
			 		" from tb_client t ,eluser e where userid in (select id from eluser where  " +
			 		"  depid in" +
			 		" (select id from department where lid >= ? and rid <= ?)   ) " ;
			 	sqlEnd= 		" and t.userid= e.id order by t.id ) A " +
			 		" WHERE ROWNUM <= ? ) " +
			 		" WHERE RN >= ? ";
			 	
			 	
			 	
			 	if(client.getName()!=null&&!client.getName().equals(""))
			 	{
			 		sql += " and t.name like '"+client.getName()+"%' ";
			 	}
			 	
			 	if(client.getIndustry()!=null&&!client.getIndustry().equals(""))
			 	{
			 		sql += " and t.industry='"+client.getIndustry()+"' ";
			 	}
//				if(contact.getContent()!=null&&!contact.getContent().equals(""))
//			 	{
//			 		sql += " and t.content like '"+contact.getContent()+"%' ";
//			 	}
//			 	
			 	if(client.getBegintime()!=null&&!client.getBegintime().equals(""))
			 	{
			 		if(client.getEndtime()!=null&&!client.getEndtime().equals(""))
			 		{
			 			sql += " and t.createdate>= to_date('"+client.getBegintime()+"','yyyy-mm-dd hh24:mi:ss') " +
			 					" and t.createdate<= to_date('"+client.getEndtime()+"','yyyy-mm-dd hh24:mi:ss')  " ;
			 		}
			 		else
			 		{
			 			sql += " and t.createdate>= to_date('"+client.getBegintime()+"','yyyy-mm-dd hh24:mi:ss') ";
			 		}
			 	}
			 	else if(client.getEndtime()!=null&&!client.getEndtime().equals(""))
			 	{
			 		sql +=" and t.createdate<= to_date('"+client.getEndtime()+"','yyyy-mm-dd hh24:mi:ss')  " ;
			 	}
			 	
			 	
			 	
			 	
			 ps = ct.prepareStatement(sqlBegin+sql+sqlEnd);
			 ps.setInt(1, department.getLid());
			 ps.setInt(2, department.getRid());
			 ps.setInt(3, pageNow);
			 ps.setInt(4, pageSize);
			 
			 rs=ps.executeQuery();
			 
			 while (rs.next()) 
			 {
				 cl= new Client();
					
					cl.setId(rs.getInt("id"));
					cl.setName(rs.getString("name"));
					cl.setTel(rs.getString(3));
					cl.setTax(rs.getString("tax"));
					cl.setUrl(rs.getString("url"));
					
					cl.setEmail(rs.getString("email"));
					cl.setCity(rs.getString("city"));
					cl.setAddr(rs.getString("addr"));
					cl.setPostcode(rs.getString("postcode"));
					cl.setClientfrom(rs.getString("clientfrom"));
					
					cl.setCompanytype(rs.getString("companytype"));
					cl.setIndustry(rs.getString("industry"));
					cl.setMainbusiness(rs.getString("mainbusiness"));
					cl.setCompanysize(rs.getString("companysize"));
					cl.setStartbusiness(rs.getString("startbusiness"));
					
					cl.setRegisteredcapital(rs.getString("registeredcapital"));
					cl.setLegal(rs.getString("legal"));
					cl.setBank(rs.getString("bank"));
					cl.setBankaccount(rs.getString("bankaccount"));
					cl.setDuty(rs.getString("duty"));
					
					cl.setCreatedate(rs.getString("createdate"));
					cl.setSuperclient(rs.getString("superclient"));
					cl.setRemark(rs.getString("remark"));
					cl.setStatus(rs.getString("status"));
					cl.setUsername(rs.getString("username"));
							
					list.add(cl);
			 }
	
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("联系行为查询出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}
	public int  get_client_by_dep_count(Client client,Department department) throws ElException
	{
		int count=0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sqlBegin="";
	 	String sql ="";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 //sql="select count(*) from tb_log where log_userid in (select id from eluser where lid >= ? and rid <= ? and valid=1 )";	
			 sqlBegin="select count(*) from tb_client t where userid in " +
			 		" (" +
			 		"   select id from eluser where  depid in " +
			 		" ( select id from department where lid >= ? and rid <= ?)" +
			 		" ) ";
			 
			 
				if(client.getName()!=null&&!client.getName().equals(""))
			 	{
			 		sql += " and t.name like '"+client.getName()+"%' ";
			 	}
			 
			 	
			 	if(client.getIndustry()!=null&&!client.getIndustry().equals(""))
			 	{
			 		sql += " and t.industry='"+client.getIndustry()+"' ";
			 	}
			 	
			 	if(client.getBegintime()!=null&&!client.getBegintime().equals(""))
			 	{
			 		if(client.getEndtime()!=null&&!client.getEndtime().equals(""))
			 		{
			 			sql += " and t.createdate>= to_date('"+client.getBegintime()+"','yyyy-mm-dd hh24:mi:ss') " +
			 					" and t.createdate<= to_date('"+client.getEndtime()+"','yyyy-mm-dd hh24:mi:ss')  " ;
			 		}
			 		else
			 		{
			 			sql += " and t.createdate>= to_date('"+client.getBegintime()+"','yyyy-mm-dd hh24:mi:ss') ";
			 		}
			 	}
			 	else if(client.getEndtime()!=null&&!client.getEndtime().equals(""))
			 	{
			 		sql +=" and t.createdate<= to_date('"+client.getEndtime()+"','yyyy-mm-dd hh24:mi:ss')  " ;
			 	}
			 
			 
			 ps = ct.prepareStatement(sqlBegin+sql);
			 ps.setInt(1, department.getLid());
			 ps.setInt(2,department.getRid());
			 
			 rs=ps.executeQuery();
			 
			 if (rs.next()) 
			 {
				 count=rs.getInt(1);
			 }
	
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("联系行为COUNT查询出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		return count;
		
	}
	
	///----------------------------------------------------------------------------------------------------
	/*
	 * 根据id删除客户联系 tb_clientlinkman_tags
	 */
	public void delete_contact_by_id_tags(int id) throws ElException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
	 	String sql ="";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 
			 sql="delete from tb_clientlinkman_tags where id=?";
			 
			 ps = ct.prepareStatement(sql);
			 
			 ps.setInt(1, id);
			
			 ps.executeUpdate();
			 
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("客户联系删除出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	/*
	 * 审核通过/不通过
	 */
	public void verify_pass_contact_by_id(String tablename,int id,int status,int userid,int depid,AuditMark auditMark,String auditOrder) throws ElException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
	 	String sql ="";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 
			 sql="update  " + tablename + 
			 		" set status=?,audituserid=?,audittime=?,auditdepid=? " +
			 		" where id=? ";
			 
			 ps = ct.prepareStatement(sql);
			 
			 ps.setInt(1, status);
			 ps.setInt(2, userid);
			 ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			 ps.setInt(4, depid);
			 ps.setInt(5, id);
			
			 ps.executeUpdate();
			 
			 
			 
			 sql = " insert into tb_audit_mark (moduleid,entityid,auditmark,time,status,ord) " +
				" values (?,?,?,?,?,?) ";
			ps = ct.prepareStatement(sql);
			ps.setString(1, auditMark.getModuleid());
			ps.setInt(2, auditMark.getEntityid());
			ps.setString(3, auditMark.getAudit_mark()==null?"":auditMark.getAudit_mark());
			ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			ps.setInt(5, status);
			
			ps.setString(6, "初审");
			ps.executeUpdate();
			 
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("审核通过客户联系出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public void verify_no_pass_contact_by_id(String tablename,int id,int status,int userid,int depid,AuditMark auditMark,String auditOrder) throws ElException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
	 	String sql ="";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 
			 sql="update  " + tablename + 
		 		" set status=?,audituserid=?,audittime=?,auditdepid=? " +
		 		" where id=? ";
			 
			 ps = ct.prepareStatement(sql);
			 
			 ps.setInt(1, status);
			 ps.setInt(2, userid);
			 ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			 ps.setInt(4, depid);
			 ps.setInt(5, id);
			
			 ps.executeUpdate();
			 
			 sql = " insert into tb_audit_mark (moduleid,entityid,auditmark,time,status,ord) " +
				" values (?,?,?,?,?,?) ";
			ps = ct.prepareStatement(sql);
			ps.setString(1, auditMark.getModuleid());
			ps.setInt(2, auditMark.getEntityid());
			ps.setString(3, auditMark.getAudit_mark()==null?"":auditMark.getAudit_mark());
			ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			ps.setInt(5, status);
			ps.setString(6, "初审");
			ps.executeUpdate();
			 
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("审核不通过客户联系出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	
	/*
	 * 客户联系查询
	 * 按联系类型进行查询
	 */
	public List<Clientlinkcontact> contact_client_search_by_contact_type(String contacttypes,Department department)  throws ElException
	{
		List<Clientlinkcontact> list = new ArrayList<Clientlinkcontact>();
		String type[]=contacttypes.split("==");
		Clientlinkcontact clc=null;
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
	 	String sql ="";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 
			 sql=" select count(tb_clientlinkman_tags_41),sum(tb_clientlinkman_tags_53) " +
				 "  from tb_clientlinkman_tags  " +
				 "  where tb_clientlinkman_tags_41=? and userid in " +//联系类型，如'报价'
				 "  ( 								" +
				 " 	      select id from eluser where  	  depid  in " +
				 " 	 (select id from department where lid >= ? and rid <= ?)    " +
				 " 	 )  ";
			 
			 ps = ct.prepareStatement(sql);
			 
			 for(int i=0;i<type.length;i++)
			 {
				 ps.setString(1,type[i] );
				 ps.setInt(2,department.getLid() );
				 ps.setInt(3,department.getRid() );
				 rs=ps.executeQuery();
				 if(rs.next())
				 {
					 clc = new Clientlinkcontact();
					 //String value=type[i]+"=="+rs.getInt(1)+"=="+rs.getInt(2);
					 clc.setType(type[i]);
					 clc.setNum(String.valueOf(rs.getInt(1)));
					 clc.setSum(String.valueOf(rs.getInt(2)));
					 list.add(clc);
				 }
				 
				 
			 }
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("审核不通过客户联系出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return  list;
	}
	
	
	/*
	 * 客户联系查找相关客户
	 *  查找相关客户
	 *  tb_client
	 *  返回：客户id和客户name
	 */
	public List<Client> contact_get_relate_client() throws ElException
	{
		List<Client> list = new ArrayList<Client>();
		PreparedStatement ps = null;
		Client client = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try
		{
			ct = DBConnection.getConnection();
			sql = " select id,name from tb_client  ";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				client = new Client();
				client.setId(rs.getInt("id"));
				client.setName(rs.getString("name"));
				client.setRemark(rs.getInt("id")+"=="+rs.getString("name"));
				list.add(client);
			}

		}
		catch (Exception e)
		{
			logger.error("审核不通过客户联系出错！", e);
			throw new ElException(e);
		}
		finally
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}
	//-------------------20131118wsj修改------------------------------------
	/**
	 * 更新培训状况
	 */
	public void updateTrainStatus()throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("{call updateTrainStatus}");
			ps.executeUpdate();
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 培训状况列表总人数
	 */
	public List<TrainingStatus> trainStatusList(TrainingStatus ts,
			int pageNow, int pageSize) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<TrainingStatus> trainStatuses = new ArrayList<TrainingStatus>();
		try {
			ct = DBConnection.getConnection();
			String sql = 
						"select p.id,\n" +
						"       p.status,\n" + 
						"       p.principalid,\n" + 
						"       p.pxzk_xm,\n" + 
						"       p.pxzk_xb,\n" + 
						"       p.pxzk_xl,\n" + 
						"       p.pxzk_sfzh,\n" + 
						"       p.pxzk_dwmc,\n" + 
						"       p.pxzk_sjh,\n" + 
						"       p.pxzk_kscj,\n" + 
						"       p.pxzk_sfzc,\n" + 
						"       p.pxzk_sfjf,\n" + 
						"       p.pxzk_sfyz,\n" + 
						"       p.pxzk_zsksrq,\n" + 
						"       p.pxzk_zsjsrj\n" + 
						"  from pxzk p";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
			}
		} catch (Exception e) {
			logger.error("我的新闻列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return trainStatuses;
		
	}
	  
	public int trainStatusListCount()
			throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "select count(*) from pxzk";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			logger.error("培训状况列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	
	/**
	 * 培训状况列表无证总人数
	 */
	public List<TrainingStatus> nocertificatenoList(TrainingStatus ts,
			int pageNow, int pageSize) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<TrainingStatus> trainStatuses = new ArrayList<TrainingStatus>();
		try {
			ct = DBConnection.getConnection();
			String sql = 
						"select * from (select t.*, rownum rn  " +
						"  from (select distinct(p.pxzk_sfzh),\n" + 
						"       p.pxzk_xm,\n" + 
						"       p.pxzk_xb,\n" + 
						"       p.pxzk_xl,\n" + 
						"       p.id,\n" + 
						"       p.pxzk_dwmc,\n" + 
						"       p.pxzk_sjh,\n" + 
						"       p.pxzk_kscj,\n" + 
						"       p.pxzk_sfzc,\n" + 
						"       p.pxzk_sfjf,\n" + 
						"       p.pxzk_sfyz,\n" + 
						"       p.pxzk_zsksrq,\n" + 
						"       p.pxzk_zsjsrj\n" + 
						"    from pxzk p\n" +
						"                 where p.pxzk_sfzh not in\n" + 
						"                       (select p.pxzk_sfzh\n" + 
						"                          from study_class sc, eluser eu, pxzk p\n" + 
						"                         where eu.id = sc.userid\n" + 
						"                           and p.pxzk_sfzh = eu.shenfenzheng\n" + 
						"                           and trunc(add_months(sc.applydate, 12) - sysdate) > 0\n" + 
						"                           and sc.certificateno is not null) ";

			if(ts!=null&&ts.getShenfenzhenghao()!=null&&ts.getShenfenzhenghao()!=""){
				sql+=" and p.pxzk_sfzh like '%"+ts.getShenfenzhenghao()+"%'";
			}
			if(ts!=null&&ts.getRealname()!=null&&ts.getRealname()!=""){
				sql+=" and p.pxzk_xm like '%"+ts.getRealname()+"%'";
			}
				  sql+=" ) t\n" + 
						"         where rownum <= ?)\n" + 
						" where rn >= ?";

			ps = ct.prepareStatement(sql);
			System.out.println(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				TrainingStatus trainStatus = new TrainingStatus();
				trainStatus.setId(rs.getInt(5));
				trainStatus.setRealname(rs.getString(2));
				trainStatus.setSex(rs.getString(3));
				trainStatus.setShenfenzhenghao(rs.getString(1));
				trainStatus.setMobliephone(rs.getString(7));
				trainStatus.setIsregister(rs.getString(9));
				trainStatus.setIspaymoney(rs.getString(10));
				trainStatus.setIscertificate(rs.getString(11));
				trainStatus.setCertificatestart(rs.getDate(12));
				trainStatus.setCertificateend(rs.getDate(13));
				trainStatuses.add(trainStatus);
				
			}
		} catch (Exception e) {
			logger.error("我的新闻列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return trainStatuses;
		
	}
	  
	public int nocertificatenoListCount()
			throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql =
						"select count(distinct(p.pxzk_sfzh))\n" +
						"    from pxzk p\n" +
						"                 where p.pxzk_sfzh not in\n" + 
						"                       (select p.pxzk_sfzh\n" + 
						"                          from study_class sc, eluser eu, pxzk p\n" + 
						"                         where eu.id = sc.userid\n" + 
						"                           and p.pxzk_sfzh = eu.shenfenzheng\n" + 
						"                           and trunc(add_months(sc.applydate, 12) - sysdate) > 0\n" + 
						"                           and sc.certificateno is not null) ";

			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			logger.error("培训状况无证列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
		
	}
	
	/**
	 * 培训状况列表有证总人数
	 */
	public List<TrainingStatus> hascertificatenoList(TrainingStatus ts,
			int pageNow, int pageSize) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<TrainingStatus> trainStatuses = new ArrayList<TrainingStatus>();
		try {
			ct = DBConnection.getConnection();
			String sql = 
						"select * from (select t.*, rownum rn  " +
						"  from (select distinct(p.pxzk_sfzh),\n" + 
						"       p.pxzk_xm,\n" + 
						"       p.pxzk_xb,\n" + 
						"       p.pxzk_xl,\n" + 
						"       p.id,\n" + 
						"       p.pxzk_dwmc,\n" + 
						"       p.pxzk_sjh,\n" + 
						"       p.pxzk_kscj,\n" + 
						"       p.pxzk_sfzc,\n" + 
						"       p.pxzk_sfjf,\n" + 
						"       p.pxzk_sfyz,\n" + 
						"       p.pxzk_zsksrq,\n" + 
						"       p.pxzk_zsjsrj\n" + 
						"  from study_class sc, eluser eu, pxzk p\n" + 
						" where eu.id = sc.userid\n" + 
						"   and p.pxzk_sfzh = eu.shenfenzheng\n" + 
						"   and trunc(add_months(sc.applydate,12)-sysdate)> 0 "+
						"   and sc.certificateno is  not null";
			if(ts!=null&&ts.getShenfenzhenghao()!=null){
				sql+=" and p.pxzk_sfzh like '%"+ts.getShenfenzhenghao()+"%'";
			}
			if(ts!=null&&ts.getRealname()!=null){
				sql+=" and p.pxzk_sfzh like '%"+ts.getRealname()+"%'";
			}
				  sql+=" ) t\n" + 
						"         where rownum <= ?)\n" + 
						" where rn >= ?";

			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				TrainingStatus trainStatus = new TrainingStatus();
				trainStatus.setId(rs.getInt(5));
				trainStatus.setRealname(rs.getString(2));
				trainStatus.setSex(rs.getString(3));
				trainStatus.setShenfenzhenghao(rs.getString(1));
				trainStatus.setMobliephone(rs.getString(7));
				trainStatus.setIsregister(rs.getString(9));
				trainStatus.setIspaymoney(rs.getString(10));
				trainStatus.setIscertificate(rs.getString(11));
				trainStatus.setCertificatestart(rs.getDate(12));
				trainStatus.setCertificateend(rs.getDate(13));
				trainStatuses.add(trainStatus);
				
			}
		} catch (Exception e) {
			logger.error("我的新闻列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return trainStatuses;
		
	}
	  
	public int hascertificatenoListCount()
			throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql =
						"select count(distinct(p.pxzk_sfzh))\n" +
						"  from study_class sc, eluser eu, pxzk p\n" + 
						" where eu.id = sc.userid\n" + 
						"   and p.pxzk_sfzh = eu.shenfenzheng\n" + 
						"   and trunc(add_months(sc.applydate,12)-sysdate)> 0 "+
						"   and sc.certificateno is not  null";

			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			logger.error("培训状况无证列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
		
	}
	
	/**
	 * 培训状况列表已缴费总人数
	 */
	public List<TrainingStatus> haspaymoneyList(TrainingStatus ts,
			int pageNow, int pageSize) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<TrainingStatus> trainStatuses = new ArrayList<TrainingStatus>();
		try {
			ct = DBConnection.getConnection();
			String sql = 
						"select * from (select t.*, rownum rn  " +
						"  from (select distinct(p.pxzk_sfzh),\n" + 
						"       p.pxzk_xm,\n" + 
						"       p.pxzk_xb,\n" + 
						"       p.pxzk_xl,\n" + 
						"       p.id,\n" + 
						"       p.pxzk_dwmc,\n" + 
						"       p.pxzk_sjh,\n" + 
						"       p.pxzk_kscj,\n" + 
						"       p.pxzk_sfzc,\n" + 
						"       p.pxzk_sfjf,\n" + 
						"       p.pxzk_sfyz,\n" + 
						"       p.pxzk_zsksrq,\n" + 
						"       p.pxzk_zsjsrj\n" + 
						"  from shoppingcart sp , eluser eu, pxzk p\n" + 
						" where eu.id = sp.userid\n" + 
						"   and p.pxzk_sfzh = eu.shenfenzheng\n" + 
						"   and sp.status= 1";
			if(ts!=null&&ts.getShenfenzhenghao()!=null){
				sql+=" and p.pxzk_sfzh like '%"+ts.getShenfenzhenghao()+"%'";
			}
			if(ts!=null&&ts.getRealname()!=null){
				sql+=" and p.pxzk_sfzh like '%"+ts.getRealname()+"%'";
			}
				  sql+=" ) t\n" + 
						"         where rownum <= ?)\n" + 
						" where rn >= ?";

			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				TrainingStatus trainStatus = new TrainingStatus();
				trainStatus.setId(rs.getInt(5));
				trainStatus.setRealname(rs.getString(2));
				trainStatus.setSex(rs.getString(3));
				trainStatus.setShenfenzhenghao(rs.getString(1));
				trainStatus.setMobliephone(rs.getString(7));
				trainStatus.setIsregister(rs.getString(9));
				trainStatus.setIspaymoney(rs.getString(10));
				trainStatus.setIscertificate(rs.getString(11));
				trainStatus.setCertificatestart(rs.getDate(12));
				trainStatus.setCertificateend(rs.getDate(13));
				trainStatuses.add(trainStatus);
				
			}
		} catch (Exception e) {
			logger.error("我的新闻列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return trainStatuses;
		
	}
	  
	public int haspaymoneyListCount()
			throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql =
						"select count(distinct(p.pxzk_sfzh))\n" +
						"  from shoppingcart sp, eluser eu, pxzk p\n" + 
						" where eu.id = sp.userid\n" + 
						"   and p.pxzk_sfzh = eu.shenfenzheng\n" + 
						"   and sp.status = 1";

			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			logger.error("培训状况无证列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
		
	}
	
	
	/**
	 * 培训状况列表已注册总人数
	 */
	public List<TrainingStatus> hasregisterList(TrainingStatus ts,
			int pageNow, int pageSize) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<TrainingStatus> trainStatuses = new ArrayList<TrainingStatus>();
		try {
			ct = DBConnection.getConnection();
			String sql = 
						"select * from (select t.*, rownum rn  " +
						"  from (select distinct(p.pxzk_sfzh),\n" + 
						"       p.pxzk_xm,\n" + 
						"       p.pxzk_xb,\n" + 
						"       p.pxzk_xl,\n" + 
						"       p.id,\n" + 
						"       p.pxzk_dwmc,\n" + 
						"       p.pxzk_sjh,\n" + 
						"       p.pxzk_kscj,\n" + 
						"       p.pxzk_sfzc,\n" + 
						"       p.pxzk_sfjf,\n" + 
						"       p.pxzk_sfyz,\n" + 
						"       p.pxzk_zsksrq,\n" + 
						"       p.pxzk_zsjsrj\n" + 
						"  from  eluser eu, pxzk p\n" + 
						" where lower(p.pxzk_sfzh)=eu.shenfenzheng";
			if(ts!=null&&ts.getShenfenzhenghao()!=null){
				sql+=" and p.pxzk_sfzh like '%"+ts.getShenfenzhenghao()+"%'";
			}
			if(ts!=null&&ts.getRealname()!=null){
				sql+=" and p.pxzk_sfzh like '%"+ts.getRealname()+"%'";
			}
				  sql+=" ) t\n" + 
						"         where rownum <= ?)\n" + 
						" where rn >= ?";

			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				TrainingStatus trainStatus = new TrainingStatus();
				trainStatus.setId(rs.getInt(5));
				trainStatus.setRealname(rs.getString(2));
				trainStatus.setSex(rs.getString(3));
				trainStatus.setShenfenzhenghao(rs.getString(1));
				trainStatus.setMobliephone(rs.getString(7));
				trainStatus.setIsregister(rs.getString(9));
				trainStatus.setIspaymoney(rs.getString(10));
				trainStatus.setIscertificate(rs.getString(11));
				trainStatus.setCertificatestart(rs.getDate(12));
				trainStatus.setCertificateend(rs.getDate(13));
				trainStatuses.add(trainStatus);
				
			}
		} catch (Exception e) {
			logger.error("我的新闻列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return trainStatuses;
		
	}
	  
	public int hasregisterListCount()
			throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql =
						"select count(distinct(p.pxzk_sfzh))\n" +
						"  from eluser eu,pxzk p\n" + 
						" where lower(p.pxzk_sfzh)=eu.shenfenzheng ";

			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			logger.error("培训状况无证列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
		
		
	}
	
	
    /**
     * 培训状况进度列表
     */
	public List<TrainingStatus> trainStatusPlan(TrainingStatus ts,
			int pageNow, int pageSize) throws ElException{
				return null;
				}
	  
	public int trainStatusPlanCount()
			throws ElException{
				return 0;
		
	}
	
	/**
	 * 证书半年到期总人数
	 */
	public List<TrainingStatus> isSixMonthsList(TrainingStatus ts,
			int pageNow, int pageSize) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<TrainingStatus> trainStatuses = new ArrayList<TrainingStatus>();
		try {
			ct = DBConnection.getConnection();
			String sql = 
						"select * from (select t.*, rownum rn  " +
						"  from (select distinct(p.pxzk_sfzh),\n" + 
						"       p.pxzk_xm,\n" + 
						"       p.pxzk_xb,\n" + 
						"       p.pxzk_xl,\n" + 
						"       p.id,\n" + 
						"       p.pxzk_dwmc,\n" + 
						"       p.pxzk_sjh,\n" + 
						"       p.pxzk_kscj,\n" + 
						"       p.pxzk_sfzc,\n" + 
						"       p.pxzk_sfjf,\n" + 
						"       p.pxzk_sfyz,\n" + 
						"       p.pxzk_zsksrq,\n" + 
						"       p.pxzk_zsjsrj,\n" +
						"       sc.applydate,   "+
						"       add_months(sc.applydate, 12) "+
						"  from study_class sc, eluser eu, pxzk p\n" + 
						" where eu.id = sc.userid\n" + 
						"   and p.pxzk_sfzh = eu.shenfenzheng\n" + 
						"   and sc.certificateno is not null\n" + 
						"   and trunc(months_between(add_months(sc.applydate, 12),\n" + 
						"             sysdate))<=6" +
						"    and trunc(months_between(add_months(sc.applydate, 12),\n" + 
						"             sysdate))>=3";
			if(ts!=null&&ts.getShenfenzhenghao()!=null){
				sql+=" and p.pxzk_sfzh like '%"+ts.getShenfenzhenghao()+"%'";
			}
			if(ts!=null&&ts.getRealname()!=null){
				sql+=" and p.pxzk_sfzh like '%"+ts.getRealname()+"%'";
			}
				  sql+=" ) t\n" + 
						"         where rownum <= ?)\n" + 
						" where rn >= ?";

			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				TrainingStatus trainStatus = new TrainingStatus();
				trainStatus.setId(rs.getInt(5));
				trainStatus.setRealname(rs.getString(2));
				trainStatus.setSex(rs.getString(3));
				trainStatus.setShenfenzhenghao(rs.getString(1));
				trainStatus.setMobliephone(rs.getString(7));
				trainStatus.setIsregister(rs.getString(9));
				trainStatus.setIspaymoney(rs.getString(10));
				trainStatus.setIscertificate(rs.getString(11));
				trainStatus.setCertificatestart(rs.getDate(12));
				trainStatus.setCertificateend(rs.getDate(13));
				trainStatus.setCertificatestart(rs.getDate(14));
				trainStatus.setCertificateend(rs.getDate(15));
				trainStatuses.add(trainStatus);
				
			}
		} catch (Exception e) {
			logger.error("我的新闻列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return trainStatuses;
	}
	  
	public int isSixMonthsListCount()
			throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql =
						"select count(*)\n" +
						"  from study_class sc, eluser eu, pxzk p\n" + 
						" where eu.id = sc.userid\n" + 
						"   and p.pxzk_sfzh = eu.shenfenzheng\n" + 
						"   and sc.certificateno is not null\n" + 
						"   and trunc(months_between(add_months(sc.applydate, 12),\n" + 
						"             sysdate))<=6" +
						"    and trunc(months_between(add_months(sc.applydate, 12),\n" + 
						"             sysdate))>=3";

			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			logger.error("培训状况无证列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
		
	}
	
	/**
	 * 证书三个月到期总人数
	 */
	public List<TrainingStatus> isThreeMonthsList(TrainingStatus ts,
			int pageNow, int pageSize) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<TrainingStatus> trainStatuses = new ArrayList<TrainingStatus>();
		try {
			ct = DBConnection.getConnection();
			String sql = 
						"select * from (select t.*, rownum rn  " +
						"  from (select distinct(p.pxzk_sfzh),\n" + 
						"       p.pxzk_xm,\n" + 
						"       p.pxzk_xb,\n" + 
						"       p.pxzk_xl,\n" + 
						"       p.id,\n" + 
						"       p.pxzk_dwmc,\n" + 
						"       p.pxzk_sjh,\n" + 
						"       p.pxzk_kscj,\n" + 
						"       p.pxzk_sfzc,\n" + 
						"       p.pxzk_sfjf,\n" + 
						"       p.pxzk_sfyz,\n" + 
						"       p.pxzk_zsksrq,\n" + 
						"       p.pxzk_zsjsrj,\n" +
						"       sc.applydate,   "+
						"       add_months(sc.applydate, 12) "+
						"  from study_class sc, eluser eu, pxzk p\n" + 
						" where eu.id = sc.userid\n" + 
						"   and p.pxzk_sfzh = eu.shenfenzheng\n" + 
						"   and sc.certificateno is not null\n" + 
						"   and trunc(months_between(add_months(sc.applydate, 12),\n" + 
						"             sysdate))<=3" +
						"    and trunc(months_between(add_months(sc.applydate, 12),\n" + 
						"             sysdate))>=1";
			if(ts!=null&&ts.getShenfenzhenghao()!=null){
				sql+=" and p.pxzk_sfzh like '%"+ts.getShenfenzhenghao()+"%'";
			}
			if(ts!=null&&ts.getRealname()!=null){
				sql+=" and p.pxzk_sfzh like '%"+ts.getRealname()+"%'";
			}
				  sql+=" ) t\n" + 
						"         where rownum <= ?)\n" + 
						" where rn >= ?";

			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				TrainingStatus trainStatus = new TrainingStatus();
				trainStatus.setId(rs.getInt(5));
				trainStatus.setRealname(rs.getString(2));
				trainStatus.setSex(rs.getString(3));
				trainStatus.setShenfenzhenghao(rs.getString(1));
				trainStatus.setMobliephone(rs.getString(7));
				trainStatus.setIsregister(rs.getString(9));
				trainStatus.setIspaymoney(rs.getString(10));
				trainStatus.setIscertificate(rs.getString(11));
				trainStatus.setCertificatestart(rs.getDate(12));
				trainStatus.setCertificateend(rs.getDate(13));
				trainStatus.setCertificatestart(rs.getDate(14));
				trainStatus.setCertificateend(rs.getDate(15));
				trainStatuses.add(trainStatus);
				
			}
		} catch (Exception e) {
			logger.error("我的新闻列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return trainStatuses;
	}
	  
	public int isThreeMonthsListCount()
			throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql =
						"select count(*)\n" +
						"  from study_class sc, eluser eu, pxzk p\n" + 
						" where eu.id = sc.userid\n" + 
						"   and p.pxzk_sfzh = eu.shenfenzheng\n" + 
						"   and sc.certificateno is not null\n" + 
						"   and trunc(months_between(add_months(sc.applydate, 12),\n" + 
						"             sysdate))<=3" +
						"    and trunc(months_between(add_months(sc.applydate, 12),\n" + 
						"             sysdate))>=1";

			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			logger.error("培训状况无证列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
		
	}
	
	/**
	 * 证书一个月到期总人数
	 */
	public List<TrainingStatus> isOneMonthsList(TrainingStatus ts,
			int pageNow, int pageSize) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<TrainingStatus> trainStatuses = new ArrayList<TrainingStatus>();
		try {
			ct = DBConnection.getConnection();
			String sql = 
						"select * from (select t.*, rownum rn  " +
						"  from (select distinct(p.pxzk_sfzh),\n" + 
						"       p.pxzk_xm,\n" + 
						"       p.pxzk_xb,\n" + 
						"       p.pxzk_xl,\n" + 
						"       p.id,\n" + 
						"       p.pxzk_dwmc,\n" + 
						"       p.pxzk_sjh,\n" + 
						"       p.pxzk_kscj,\n" + 
						"       p.pxzk_sfzc,\n" + 
						"       p.pxzk_sfjf,\n" + 
						"       p.pxzk_sfyz,\n" + 
						"       p.pxzk_zsksrq,\n" + 
						"       p.pxzk_zsjsrj,\n" +
						"       sc.applydate,   "+
						"       add_months(sc.applydate, 12) "+
						"  from study_class sc, eluser eu, pxzk p\n" + 
						" where eu.id = sc.userid\n" + 
						"   and p.pxzk_sfzh = eu.shenfenzheng\n" + 
						"   and sc.certificateno is not null\n" + 
						"   and trunc(months_between(add_months(sc.applydate, 12),\n" + 
						"             sysdate))<=1 " +
						"     and trunc(add_months(sc.applydate,12)-sysdate)>=15 ";
			if(ts!=null&&ts.getShenfenzhenghao()!=null){
				sql+=" and p.pxzk_sfzh like '%"+ts.getShenfenzhenghao()+"%'";
			}
			if(ts!=null&&ts.getRealname()!=null){
				sql+=" and p.pxzk_sfzh like '%"+ts.getRealname()+"%'";
			}
				  sql+=" ) t\n" + 
						"         where rownum <= ?)\n" + 
						" where rn >= ?";

			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				TrainingStatus trainStatus = new TrainingStatus();
				trainStatus.setId(rs.getInt(5));
				trainStatus.setRealname(rs.getString(2));
				trainStatus.setSex(rs.getString(3));
				trainStatus.setShenfenzhenghao(rs.getString(1));
				trainStatus.setMobliephone(rs.getString(7));
				trainStatus.setIsregister(rs.getString(9));
				trainStatus.setIspaymoney(rs.getString(10));
				trainStatus.setIscertificate(rs.getString(11));
				trainStatus.setCertificatestart(rs.getDate(12));
				trainStatus.setCertificateend(rs.getDate(13));
				trainStatus.setCertificatestart(rs.getDate(14));
				trainStatus.setCertificateend(rs.getDate(15));
				trainStatuses.add(trainStatus);
				
			}
		} catch (Exception e) {
			logger.error("我的新闻列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return trainStatuses;
	}
	  
	public int isOneMonthsListCount()
			throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql =
						"select count(*)\n" +
						"  from study_class sc, eluser eu, pxzk p\n" + 
						" where eu.id = sc.userid\n" + 
						"   and p.pxzk_sfzh = eu.shenfenzheng\n" + 
						"   and sc.certificateno is not null\n" + 
						"   and trunc(months_between(add_months(sc.applydate, 12),\n" + 
						"             sysdate))<=1 " +
						"     and trunc(add_months(sc.applydate,12)-sysdate)>=15";

			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			logger.error("培训状况无证列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
		
	}
	
	/**
	 *证书半个月到期总人数
	 */
	public List<TrainingStatus> isHalfMonthsList(TrainingStatus ts,
			int pageNow, int pageSize) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<TrainingStatus> trainStatuses = new ArrayList<TrainingStatus>();
		try {
			ct = DBConnection.getConnection();
			String sql = 
						"select * from (select t.*, rownum rn  " +
						"  from (select distinct(p.pxzk_sfzh),\n" + 
						"       p.pxzk_xm,\n" + 
						"       p.pxzk_xb,\n" + 
						"       p.pxzk_xl,\n" + 
						"       p.id,\n" + 
						"       p.pxzk_dwmc,\n" + 
						"       p.pxzk_sjh,\n" + 
						"       p.pxzk_kscj,\n" + 
						"       p.pxzk_sfzc,\n" + 
						"       p.pxzk_sfjf,\n" + 
						"       p.pxzk_sfyz,\n" + 
						"       p.pxzk_zsksrq,\n" + 
						"       p.pxzk_zsjsrj,\n" +
						"       sc.applydate,   "+
						"       add_months(sc.applydate, 12) "+
						"  from study_class sc, eluser eu, pxzk p\n" + 
						" where eu.id = sc.userid\n" + 
						"   and p.pxzk_sfzh = eu.shenfenzheng\n" + 
						"   and sc.certificateno is not null\n" + 
						"  and trunc(add_months(sc.applydate,12)-sysdate)<=15" +
						"   and trunc(add_months(sc.applydate,12)-sysdate)>=7 ";
			if(ts!=null&&ts.getShenfenzhenghao()!=null){
				sql+=" and p.pxzk_sfzh like '%"+ts.getShenfenzhenghao()+"%'";
			}
			if(ts!=null&&ts.getRealname()!=null){
				sql+=" and p.pxzk_sfzh like '%"+ts.getRealname()+"%'";
			}
				  sql+=" ) t\n" + 
						"         where rownum <= ?)\n" + 
						" where rn >= ?";

			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				TrainingStatus trainStatus = new TrainingStatus();
				trainStatus.setId(rs.getInt(5));
				trainStatus.setRealname(rs.getString(2));
				trainStatus.setSex(rs.getString(3));
				trainStatus.setShenfenzhenghao(rs.getString(1));
				trainStatus.setMobliephone(rs.getString(7));
				trainStatus.setIsregister(rs.getString(9));
				trainStatus.setIspaymoney(rs.getString(10));
				trainStatus.setIscertificate(rs.getString(11));
				trainStatus.setCertificatestart(rs.getDate(12));
				trainStatus.setCertificateend(rs.getDate(13));
				trainStatus.setCertificatestart(rs.getDate(14));
				trainStatus.setCertificateend(rs.getDate(15));
				trainStatuses.add(trainStatus);
				
			}
		} catch (Exception e) {
			logger.error("我的新闻列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return trainStatuses;
	}
	  
	public int isHalfMonthsListCount()
			throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql =
						"select count(*)\n" +
						"  from study_class sc, eluser eu, pxzk p\n" + 
						" where eu.id = sc.userid\n" + 
						"   and p.pxzk_sfzh = eu.shenfenzheng\n" + 
						"   and sc.certificateno is not null\n" + 
						"  and trunc(add_months(sc.applydate,12)-sysdate)<=15" +
						"    and trunc(add_months(sc.applydate,12)-sysdate)>=7";

			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			logger.error("培训状况无证列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
		
	}
	
	/**
	 * 证书一周到期总人数
	 */
	public List<TrainingStatus> isOneWeekList(TrainingStatus ts,
			int pageNow, int pageSize) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<TrainingStatus> trainStatuses = new ArrayList<TrainingStatus>();
		try {
			ct = DBConnection.getConnection();
			String sql = 
						"select * from (select t.*, rownum rn  " +
						"  from (select p.id,\n" + 
						"       p.pxzk_xm,\n" + 
						"       p.pxzk_xb,\n" + 
						"       p.pxzk_xl,\n" + 
						"       p.pxzk_sfzh,\n" + 
						"       p.pxzk_dwmc,\n" + 
						"       p.pxzk_sjh,\n" + 
						"       p.pxzk_kscj,\n" + 
						"       p.pxzk_sfzc,\n" + 
						"       p.pxzk_sfjf,\n" + 
						"       p.pxzk_sfyz,\n" + 
						"       p.pxzk_zsksrq,\n" + 
						"       p.pxzk_zsjsrj,\n" +
						"       sc.applydate,   "+
						"       add_months(sc.applydate, 12) "+
						"  from study_class sc, eluser eu, pxzk p\n" + 
						" where eu.id = sc.userid\n" + 
						"   and p.pxzk_sfzh = eu.shenfenzheng\n" + 
						"   and sc.certificateno is not null\n" + 
						"  and trunc(add_months(sc.applydate,12)-sysdate)<=7" +
						"   and trunc(add_months(sc.applydate,12)-sysdate)>=0 ";
			if(ts!=null&&ts.getShenfenzhenghao()!=null){
				sql+=" and p.pxzk_sfzh like '%"+ts.getShenfenzhenghao()+"%'";
			}
			if(ts!=null&&ts.getRealname()!=null){
				sql+=" and p.pxzk_sfzh like '%"+ts.getRealname()+"%'";
			}
				  sql+=" ) t\n" + 
						"         where rownum <= ?)\n" + 
						" where rn >= ?";

			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				TrainingStatus trainStatus = new TrainingStatus();
				trainStatus.setId(rs.getInt(1));
				trainStatus.setRealname(rs.getString(2));
				trainStatus.setSex(rs.getString(3));
				trainStatus.setShenfenzhenghao(rs.getString(5));
				trainStatus.setMobliephone(rs.getString(7));
				trainStatus.setIsregister(rs.getString(9));
				trainStatus.setIspaymoney(rs.getString(10));
				trainStatus.setIscertificate(rs.getString(11));
				trainStatus.setCertificatestart(rs.getDate(12));
				trainStatus.setCertificateend(rs.getDate(13));
				trainStatus.setCertificatestart(rs.getDate(14));
				trainStatus.setCertificateend(rs.getDate(15));
				trainStatuses.add(trainStatus);
				
			}
		} catch (Exception e) {
			logger.error("我的新闻列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return trainStatuses;
	}
	  
	public int isOneWeekListCount()
			throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql =
						"select count(*)\n" +
						"  from study_class sc, eluser eu, pxzk p\n" + 
						" where eu.id = sc.userid\n" + 
						"   and p.pxzk_sfzh = eu.shenfenzheng\n" + 
						"   and sc.certificateno is not null\n" + 
						"  and trunc(add_months(sc.applydate,12)-sysdate)<=7" +
						"   and trunc(add_months(sc.applydate,12)-sysdate)>=0";

			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			logger.error("培训状况无证列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
		
	}
	
	/**
	 * 证书已过期总人数
	 */
	public List<TrainingStatus> isValidList(TrainingStatus ts,
			int pageNow, int pageSize) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<TrainingStatus> trainStatuses = new ArrayList<TrainingStatus>();
		try {
			ct = DBConnection.getConnection();
			String sql = 
						"select * from (select t.*, rownum rn  " +
						"  from (select p.id,\n" + 
						"       p.pxzk_xm,\n" + 
						"       p.pxzk_xb,\n" + 
						"       p.pxzk_xl,\n" + 
						"       p.pxzk_sfzh,\n" + 
						"       p.pxzk_dwmc,\n" + 
						"       p.pxzk_sjh,\n" + 
						"       p.pxzk_kscj,\n" + 
						"       p.pxzk_sfzc,\n" + 
						"       p.pxzk_sfjf,\n" + 
						"       p.pxzk_sfyz,\n" + 
						"       p.pxzk_zsksrq,\n" + 
						"       p.pxzk_zsjsrj,\n" +
						"       sc.applydate,   "+
						"       add_months(sc.applydate, 12) "+
						"  from study_class sc, eluser eu, pxzk p\n" + 
						" where eu.id = sc.userid\n" + 
						"   and p.pxzk_sfzh = eu.shenfenzheng\n" + 
						"   and sc.certificateno is not null\n" + 
						"  and trunc(add_months(sc.applydate,12)-sysdate)< 0" ;
			if(ts!=null&&ts.getShenfenzhenghao()!=null){
				sql+=" and p.pxzk_sfzh like '%"+ts.getShenfenzhenghao()+"%'";
			}
			if(ts!=null&&ts.getRealname()!=null){
				sql+=" and p.pxzk_sfzh like '%"+ts.getRealname()+"%'";
			}
				  sql+=" ) t\n" + 
						"         where rownum <= ?)\n" + 
						" where rn >= ?";

			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				TrainingStatus trainStatus = new TrainingStatus();
				trainStatus.setId(rs.getInt(1));
				trainStatus.setRealname(rs.getString(2));
				trainStatus.setSex(rs.getString(3));
				trainStatus.setShenfenzhenghao(rs.getString(5));
				trainStatus.setMobliephone(rs.getString(7));
				trainStatus.setIsregister(rs.getString(9));
				trainStatus.setIspaymoney(rs.getString(10));
				trainStatus.setIscertificate(rs.getString(11));
				trainStatus.setCertificatestart(rs.getDate(12));
				trainStatus.setCertificateend(rs.getDate(13));
				trainStatus.setCertificatestart(rs.getDate(14));
				trainStatus.setCertificateend(rs.getDate(15));
				trainStatuses.add(trainStatus);
				
			}
		} catch (Exception e) {
			logger.error("我的新闻列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return trainStatuses;
	}
	  
	public int isValidListCount()
			throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql =
						"select count(*)\n" +
						"  from study_class sc, eluser eu, pxzk p\n" + 
						" where eu.id = sc.userid\n" + 
						"   and p.pxzk_sfzh = eu.shenfenzheng\n" + 
						"   and sc.certificateno is not null\n" + 
						"  and trunc(add_months(sc.applydate,12)-sysdate)< 0" ;

			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			logger.error("培训状况无证列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
		
	}
	

}

