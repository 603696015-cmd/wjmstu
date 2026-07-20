package com.sopia.newsandmess.dao;
import java.util.List;

import javax.mail.internet.AddressException;

import com.sopia.classman.entities.ElClass;
import com.sopia.common.ElException;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.newsandmess.entities.Message;
import com.sopia.newsandmess.entities.Pop;


public interface MessageDao {
	public void messSend(Message mess, ELUser from, List<ELUser> to)
	throws ElException ;
	public void messSend1(Message mess, ELUser from, List<ELUser> to,String staddr[],String sttitle[])
	throws ElException ;
	/**
	 * 消息添加
	 * @param mess
	 * @throws ElException
	 */
	public int insertMess(Message mess)throws ElException;
	/**
	 * 收件
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public List<Message> listMessTo(int id,int pageNow,int pageSize) throws ElException;
	/**
	 * 收件数
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public int messToCount(int id)throws ElException;
	/**
	 * 发件
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public List<Message> listMessFrom(int id,int pageNow,int pageSize) throws ElException;
	/**
	 * 发件数
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public int messFromCount(int id)throws ElException;
	/**
	 * 消息删除
	 * @param mess_id
	 * @throws ElException
	 */
	public void messDelete(int mess_id)throws ElException;
	/**
	 * 获取id消息
	 * @param mess_id
	 * @return
	 * @throws ElException
	 */
	public Message getMessById(int mess_id)throws ElException;
	
	/**
	 * 检测收件箱是否删除
	 * @param mess_id
	 * @return
	 * @throws ElException
	 */
	public boolean checkRecDel(int mess_id)throws ElException;
	/**
	 * 检测发件箱是否删除
	 * @param mess_id
	 * @return
	 * @throws ElException
	 */
	public boolean checkSendDel(int mess_id) throws ElException;
	/**
	 * 收件箱删除
	 * @param mess_id
	 * @throws ElException
	 */
	public void deleteRec(int mess_id) throws ElException;
	/**
	 * 发件箱删除
	 * @param mess_id
	 * @throws ElException
	 */
	public void deleteSend(int mess_id)throws ElException;
	/**
	 * 设置已读
	 * @param mess_id
	 * @throws ElException
	 */
	public void setIsRead(int mess_id)throws ElException;
	/**
	 * 新信息
	 * @param mem_id
	 * @return
	 * @throws ElException
	 */
	public List<Message> listMessNew(int mem_id)throws ElException;
	
	public int getUserByDepIdSize(int depid,int subdep,ELUser eu) throws ElException;
	public List<ELUser> getUserByDepId( int depid, int subdep,
			ELUser eu, int pageNow, int pageSize ) throws ElException ;
	public int getNewMesscount(int userid) throws ElException;
	/**
	 * 在审核通过，不通过时发送短消息
	 * @param title 操作的标题
	 * @param opMod 操作模块
	 * @param opType 操作类型
	 * @param messFromId 消息发送者id
	 * @param messType 消息类型
	 * @throws ElException
	 */
	public boolean insertMessInApply(String title,int opMod,int opType,int messFromId,int messType) throws ElException;
	/**
	 * 获取所有消息（分页）
	 * @param mem_id
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Message> listMessNew(int mem_id,int pageNow,int pageSize) throws ElException;
	
	public List<Message> listMessNewAll(int mem_id,int pageNow,int pageSize) throws ElException;
	/**
	 * 获取未读短消息数量
	 * @param mem_id
	 * @return
	 * @throws ElException
	 */
	public int getMessNoCount(int mem_id) throws ElException;
	/**
	 * 获取已读短消息数量
	 * @param mem_id
	 * @return
	 * @throws ElException
	 */
	public int getMessYesCount(int mem_id) throws ElException;
	public int listMessNewCount(int mem_id) throws ElException;
	/**
	 * 把集合转换成字符串
	 * @param objList
	 * @return
	 */
	public String getStrToList(List<Department> objList,String split);
	public String getStrToList2(List<ExamRoom> objList,String split);
	public String getStrToList3(List<ElClass> objList,String split);
	public String getStrToList4(List<ELUser> objList,String split);
	/**
	 * 添加弹窗信息
	 * @param pop
	 * @throws ElException
	 */
	public void addPop(Pop pop,String users) throws ElException;
	/**
	 * 获取我发布的弹窗信息
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Pop> getMyPopList(int userid,Pop pop, int pageNow, int pageSize)
	throws ElException;
	/**
	 * 获取我发布的弹窗信息数量
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public int getMyPopListCount(int userid,Pop tpop) throws ElException;
	/**
	 * 根据id删除弹窗信息
	 * @param popid
	 * @throws ElException
	 */
	public void deletePopById(int popid) throws ElException;
	/**
	 * 更新弹窗信息的状态
	 * @param popid
	 * @param status
	 * @throws ElException
	 */
	public void updatePopStatus(int popid,int status) throws ElException;
	/**
	 * 根据id获取弹窗信息
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public Pop getPopById(int id) throws ElException;
	/**
	 * 更新弹窗信息
	 * @param pop
	 * @throws ElException
	 */
	public void update_pop(Pop pop) throws ElException;
	/**
	 * 获取所有弹窗信息
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Pop> getPopList(Pop tpop, int pageNow, int pageSize) throws ElException;
	/**
	 * 获取所有弹窗信息数量
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public int getPopListCount(Pop tpop) throws ElException;
	/**
	 * 获取某学员的所有弹窗信息
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public String getUserPopList(int userid) throws ElException;
	/**
	 * 获取用户所分配给他的弹窗信息并添加到弹窗学员表
	 * @param userid
	 * @throws ElException
	 */
	public void listSetUserInPop(int userid) throws ElException;
	/**
	 * 发送短信
	 * @param msg
	 * @param elUsers
	 * @throws ElException
	 */
	public String sendMsg(Message msg,List<ELUser> elUsers) throws ElException;
	/**
	 * 发送邮件
	 * @param msg
	 * @param elUsers
	 * @throws ElException
	 */
	public String sendEmail(Message msg,List<ELUser> elUsers) throws ElException, AddressException;
	/**
	 * 更新短消息为已回复
	 * @param id
	 * @throws ElException
	 */
	public void updateMessReply(int id) throws ElException;
	
	/**
	 * 消息增加附件
	 * @param addr
	 * @param Newsid
	 * @param title
	 * @throws ElException
	 */
	public void addKstuff(String addr, int Newsid, String title) throws ElException;
}
