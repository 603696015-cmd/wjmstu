package com.sopia.schedule.dao;

import java.util.List;

import com.sopia.aqy.entities.TrainingStatus;
import com.sopia.common.ElException;
import com.sopia.duman.entities.Department;
import com.sopia.schedule.entities.AuditMark;
import com.sopia.schedule.entities.Client;
import com.sopia.schedule.entities.Clientlinkcontact;
import com.sopia.schedule.entities.Clientlinkman;
import com.sopia.schedule.entities.Contact;
import com.sopia.schedule.entities.Contactstuff;
import com.sopia.schedule.entities.LogStuff;
import com.sopia.schedule.entities.Logfile;

public interface ClientDao {
	
	/*
	 * 添加客户 
	 * 返回当前插入客户的id值
	 */
	public int insert_client(Client client) throws ElException;
	
	/*
	 * 添加联系人
	 */
	public void insert_clientlinkman_list(List<Clientlinkman> list,int clientid) throws ElException;
	
	/*
	 * get  client  by  id
	 */
	public Client get_client_by_id(int id) throws ElException;
	
	/*
	 * get clientlinkman by userid
	 */
	public List<Clientlinkman> get_clientlinkman_by_userid(int clientid) throws ElException;
	
	/*
	 * 通过 client 的 id删除  
	 */
	public void del_client_by_id(int id) throws ElException;
	
	/*
	 * 通过 clientid删除  clienlinkman
	 */
	public void del_clientlinkman_by_clientid(int clientid) throws ElException;
	//通过id删除clientlinkman
	public void del_clientlinkman_by_id(int id) throws ElException;
	
	
	
	/*
	 * 我添加的客户
	 */
	public List<Client> get_my_add_client(Client client,int pageNow,int pageSize) throws ElException;
	public int get_my_add_clientCount(Client client) throws ElException;
	
	/*
	 * update
	 */
	//获取某client的clientlinkman的id
	public List<Integer> get_ids_of_clientlinkman_by_clientid(int clientid)throws ElException;
	public void update_client(Client client) throws ElException;
	public void update_clientlinkman(Clientlinkman clm) throws ElException;
 	
	
	/*
	 * 查询客户
	 */
	public List<Client> get_client_by_dep(Client client,Department department,int pageNow,int pageSize) throws ElException;
	public int get_client_by_dep_count(Client client,Department department) throws ElException;
	
	/*
	 * 查询我负责的客户
	 */
	public List<Client> get_client_my_by_dep(Client client,Department department,int pageNow,int pageSize) throws ElException;
	public int get_client_my_by_dep_count(Client client,Department department) throws ElException;
	
	
	
	/*
	 *  客户联系行为  添加
	 */
	public  int insert_contact(Contact contact)  throws ElException;
	//联系人附件添加
	public  void insert_contact_stuff(Contactstuff contactstuff)  throws ElException;
	
	/*
	 * 我的联系行为
	 */
	public List<Contact> select_my_contact(Contact contact,int pageNow,int pageSize) throws  ElException;
	public int select_my_contact_count(Contact contact) throws  ElException;
	
	/*
	 * 删除联系行为
	 */
	public void delete_contact_by_id(int id) throws ElException;
	
	/*
	 * 通过id查询 联系行为
	 */
	public Contact get_contact_by_id(int id) throws ElException;
	
	/*
	 * 查询联系行为附件
	 */
	public List<Contactstuff> get_contact_stuff_list_by_contactid(int contactid) throws ElException;
	
	/*
	 * update contact
	 */
	public void update_contact_by_id(Contact contact) throws ElException;
	//根据contactid  删除 联系行为
	public void delete_contact_stuff_by_contactid(int contactid) throws ElException;
	//根据id删除联系行为  删一个
	public void delete_contact_stuff_by_id(int id) throws ElException;
	
	//根据id获得contactstuff
	public Contactstuff get_contactstuff_by_id(int id) throws ElException;
	
	/*
	 * search contact
	 */
	public List<Contact> get_contact_by_dep(Contact contact,Department department,int pageNow,int pageSize) throws ElException;
	public int get_contact_by_dep_count(Contact contact,Department department) throws ElException;
	
	//----------------------------------------------------------------------------------------------------------------
	/*
	 * 根据id删除客户联系 tb_clientlinkman_tags
	 */
	public void delete_contact_by_id_tags(int id) throws ElException;
	
	
	
	/*
	 * 审核通过/不通过
	 */
	public void verify_pass_contact_by_id(String tablename,int id,int status,int userid,int depid,AuditMark auditMark,String auditOrder) throws ElException;
	public void verify_no_pass_contact_by_id(String tablename,int id,int status,int userid,int depid,AuditMark auditMark,String auditOrder) throws ElException;
	
	
	/*
	 * 客户联系查询
	 * 按联系类型进行查询
	 * contacttypes  以==隔开
	 */
	public List<Clientlinkcontact> contact_client_search_by_contact_type(String contacttypes,Department department)  throws ElException;
	
	/*
	 * 客户联系查找相关客户
	 *  查找相关客户
	 *  tb_client
	 *  返回：客户id和客户name
	 */
	public List<Client> contact_get_relate_client() throws ElException;
	
//-------------wsj1118修改---------------------------------------------------------
	
	/**
	 * 更新培训状况
	 */
	public void updateTrainStatus() throws ElException ;
	
	
	public List<TrainingStatus> trainStatusList(TrainingStatus ts,
			int pageNow, int pageSize) throws ElException;
	  
	public int trainStatusListCount()
			throws ElException;
	
	public List<TrainingStatus> trainStatusPlan(TrainingStatus ts,
			int pageNow, int pageSize) throws ElException;
	  
	public int trainStatusPlanCount()
			throws ElException;
	
	/**
	 * 培训状况列表无证总人数
	 */
	public List<TrainingStatus> nocertificatenoList(TrainingStatus ts,
			int pageNow, int pageSize) throws ElException;
	
	  
	public int nocertificatenoListCount()
			throws ElException;
	
	/**
	 * 培训状况列表有证总人数
	 */
	public List<TrainingStatus> hascertificatenoList(TrainingStatus ts,
			int pageNow, int pageSize) throws ElException;
	  
	public int hascertificatenoListCount()
			throws ElException;
	
	/**
	 * 培训状况列表已缴费总人数
	 */
	public List<TrainingStatus> haspaymoneyList(TrainingStatus ts,
			int pageNow, int pageSize) throws ElException;
	  
	public int haspaymoneyListCount()
			throws ElException;
	
	/**
	 *已注册总人数
	 */
	public List<TrainingStatus> hasregisterList(TrainingStatus ts,
			int pageNow, int pageSize) throws ElException;
	  
	public int hasregisterListCount()
			throws ElException;
	
	
	/**
	 * 证书半年到期总人数
	 */
	public List<TrainingStatus> isSixMonthsList(TrainingStatus ts,
			int pageNow, int pageSize) throws ElException;
	  
	public int isSixMonthsListCount()
			throws ElException;
	
	/**
	 * 证书三个月到期总人数
	 */
	public List<TrainingStatus> isThreeMonthsList(TrainingStatus ts,
			int pageNow, int pageSize) throws ElException;
	  
	public int isThreeMonthsListCount()
			throws ElException;
	
	/**
	 * 证书一个月到期总人数
	 */
	public List<TrainingStatus> isOneMonthsList(TrainingStatus ts,
			int pageNow, int pageSize) throws ElException;
	  
	public int isOneMonthsListCount()
			throws ElException;
	
	/**
	 *证书半个月到期总人数
	 */
	public List<TrainingStatus> isHalfMonthsList(TrainingStatus ts,
			int pageNow, int pageSize) throws ElException;
	  
	public int isHalfMonthsListCount()
			throws ElException;
	
	/**
	 * 证书一周到期总人数
	 */
	public List<TrainingStatus> isOneWeekList(TrainingStatus ts,
			int pageNow, int pageSize) throws ElException;
	  
	public int isOneWeekListCount()
			throws ElException;
	
	/**
	 * 证书已过期总人数
	 */
	public List<TrainingStatus> isValidList(TrainingStatus ts,
			int pageNow, int pageSize) throws ElException;
	  
	public int isValidListCount()
			throws ElException;
	

	
}
