package com.sopia.newsandmess.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.AddressException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.classman.dao.ClassDao;
import com.sopia.classman.dao.ElClTypeDao;
import com.sopia.classman.entities.ElClType;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.ElException;
import com.sopia.common.SystemConfOp;
import com.sopia.common.logger.ElLogger;
import com.sopia.common.logger.ElLoggerConstants;
import com.sopia.courseman.dao.EroomDao;
import com.sopia.courseman.entities.EroomLib;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.dao.RoleDao;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElRole;
import com.sopia.duman.entities.Station;
import com.sopia.newsandmess.dao.MessageDao;
import com.sopia.newsandmess.entities.Message;
import com.sopia.newsandmess.entities.Pop;
import com.sopia.questionman.entities.ExamPaper;

public class MessageAction extends BaseAction {
	private static final Log logger = LogFactory.getLog(MessageAction.class);
	private Department depTree;
	private List<ELUser> elUsers;
	private Department department;
	private List<Department> departments;
	private ELUser elUser;
	private int sub_department;
	private Message mess;
	private List<Message> messs;
	private MessageDao messageDao;
	private int deleteType;	
	//��ѵ��
	private ElClTypeDao elClTypeDao;
	private ElClType cltypeTree;
	private List<ElClass> elclasses; 
	private List<ElClass> elClasss;
	private ClassDao classDao;  
	private ElClass elClass;
	private ElClType cltype;
	private List<ElRole> roles;
	private RoleDao roleDao;
	
	//���� 
	private EroomLib eroomLibTree;
	private EroomLib eroomLib;
	private EroomDao eroomDao;
	private List<ExamRoom> examRooms;
	private ExamRoom examRoom;
	
	//����
	private List treeAllId;
	private String searbm =""; 

	private int messageType; 
	private List<ExamPaper> examPapers;
	
	private Pop pop;
	private List<Pop> pops;
	private Station station;
	private Station stTree;
	
	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public Station getStTree() {
		return stTree;
	}

	public void setStTree(Station stTree) {
		this.stTree = stTree;
	}

	public List<Pop> getPops() {
		return pops;
	}

	public void setPops(List<Pop> pops) {
		this.pops = pops;
	}

	public Pop getPop() {
		return pop;
	}

	public void setPop(Pop pop) {
		this.pop = pop;
	}

	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	public int getDeleteType() {
		return deleteType;
	}

	public void setDeleteType(int deleteType) {
		this.deleteType = deleteType;
	}

	public MessageDao getMessageDao() {
		return messageDao;
	}

	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Department getDepTree() {
		return depTree;
	}

	public void setDepTree(Department depTree) {
		this.depTree = depTree;
	}

	public List<ELUser> getElUsers() {
		return elUsers;
	}

	public void setElUsers(List<ELUser> elUsers) {
		this.elUsers = elUsers;
	}

	public String mess_sendUserlistInit() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = new Department();
			depTree.setChild(new ArrayList<Department>());
			depTree.getChild().add(
					departmentDao.getDepTree(
							getSessionIntValue(ElConstants.SESSION_USERID),
							"op", -1, true));
			if(depTree.getChild().get(0).getChild().size() == 0  && getSessionIntValue(ElConstants.SESSION_ROLE) != 1){ 
				setElmessage("û�пɲ����Ĳ������");
				return "error";
			}
		}
		return "mess_sendUserlistInit";
	}
	public String mess_sendUserlist() throws ElException {
		if(SystemConfOp.getBooleanValue(ElConstants.SYSTEM_CONF_IS_RECEIVE_BY_JUDGE)){//��Ҫ����Ȩ���ж�
			if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
				depTree = departmentDao.getDepTree_level1(
						getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
						true);
			else {
				depTree = departmentDao.getDepTree_level1(
						getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
						true);
			}
		}else{//����ҪȨ���ж�
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		}
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYSTATION), -1,
					true);
		else {
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if (station == null || station.getId() <= 0) {
			sub_department = 1;
			station = stTree;
		} else
			station = stationDao.getStById(station.getId());
		if(department==null||department.getId()<=0){
			department = depTree;
		}else
			department  = departmentDao.getDepById(department.getId());
		setPS(15);
		elUsers = userDao.listUsers(department,station, sub_department, elUser, getPageNow(), getPageSize());
		count = userDao.listUsersSize(department,station, sub_department, elUser);
		roles = roleDao.listRoles();
		
		return "mess_sendUserlist"; 
	}
//	public String mess_sendUserlist() throws ElException {
//		if (department.getId() == -2
//				&& getSessionIntValue(ElConstants.SESSION_ROLE) != 1) {
//			elUsers = userDao.getUserByUserId(
//					getSessionIntValue(ElConstants.SESSION_USERID), elUser,
//					getPageNow(), getPageSize());
//			count = userDao.getUserByUserIdSize(
//					getSessionIntValue(ElConstants.SESSION_USERID), elUser);
//		} else {
//			elUsers = userDao.getUserByDepId(department.getId(),
//					sub_department, elUser, getPageNow(), getPageSize());
//			count = userDao.getUserByDepIdSize(department.getId(),
//					sub_department, elUser);
//		}
//		return "mess_sendUserlist"; 
//	}

	public String mess_groupsend() throws ElException { 
		elUser = userDao
				.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		
		String staddr[] = getRequest().getParameterValues("message.stuffs.description");
		String sttitle[] = getRequest().getParameterValues("message.stuffs.title");
		
		
	    List user = new ArrayList();
	    List<ELUser> users = new ArrayList<ELUser>();
		switch (messageType) {
		case 0://����Ա
			messageDao.messSend1(mess, elUser, elUsers,staddr,sttitle);
			break;
		case 1://������
			for(int i = 0 ; i< departments.size(); i++){
				elUsers = userDao.getUserByDepId(departments.get(i).getId());
				for(int x = 0 ;x < elUsers.size();x++){	
					if(!user.contains(elUsers.get(x).getId())){
						user.add(elUsers.get(x).getId());
						users.add(elUsers.get(x));
					}
				}
			}   
			messageDao.messSend1(mess, elUser, users,staddr,sttitle);
			break;
		case 2://������
			for(int i = 0 ; i< examRooms.size(); i++){
				elUsers = eroomDao.getEluserByExamRoomId(examRooms.get(i).getId());
				for(int x = 0 ;x < elUsers.size();x++){	
					if(!user.contains(elUsers.get(x).getId())){
						user.add(elUsers.get(x).getId());
						users.add(elUsers.get(x));
					}
				}
			}   
			messageDao.messSend(mess, elUser, users);	
			break;
		case 3://����ѵ�� 
			for(int i = 0 ; i< elClasss.size(); i++){
				elUsers = classDao.getelClassUser(elClasss.get(i).getId());
				for(int x = 0 ;x < elUsers.size();x++){	
					if(!user.contains(elUsers.get(x).getId())){
						user.add(elUsers.get(x).getId());
						users.add(elUsers.get(x));
					}
				}
			}   
			messageDao.messSend(mess, elUser, users);	
			break; 
		default:
			setElmessage("�����ռ�����д����ȷ");
			return "error"; 
		}
		
		return "mess_sendsuccess";
	}
	
	/**
	 * ��ӵ�����Ϣ��ʼ��
	 */
	public String pop_addInit() throws ElException {
		return "pop_add";
	}
	
	public String pop_add() throws ElException {
		//elUser = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));//������
		pop.setCreate(new ELUser(getSessionIntValue(ElConstants.SESSION_USERID)));
		pop.setSendmanner(messageType);
		List<Integer> user = new ArrayList<Integer>();
		List<ELUser> users = new ArrayList<ELUser>();
		int n=0;
		switch (messageType) {
		case 0://����Ա
			//messageDao.messSend(mess, elUser, elUsers);
			//pop.setSendvalue(messageDao.getStrToList4(elUsers, ","));
			users=elUsers;
			n=1;
			break;
		case 1://������
			for(int i = 0 ; i< departments.size(); i++){
				elUsers = userDao.getUserByDepId(departments.get(i).getId());
				for(int x = 0 ;x < elUsers.size();x++){	
					if(!user.contains(elUsers.get(x).getId())){
						user.add(elUsers.get(x).getId());
						users.add(elUsers.get(x));
					}
				}
			}
			pop.setSendvalue(messageDao.getStrToList(departments, ","));
			//messageDao.messSend(mess, elUser, users);
			break;
		case 2://������
			for(int i = 0 ; i< examRooms.size(); i++){
				elUsers = eroomDao.getEluserByExamRoomId(examRooms.get(i).getId());
				for(int x = 0 ;x < elUsers.size();x++){	
					if(!user.contains(elUsers.get(x).getId())){
						user.add(elUsers.get(x).getId());
						users.add(elUsers.get(x));
					}
				}
			}
			pop.setSendvalue(messageDao.getStrToList2(examRooms, ","));
			//messageDao.messSend(mess, elUser, users);	
			break;
		case 3://����ѵ�� 
			for(int i = 0 ; i< elClasss.size(); i++){
				elUsers = classDao.getelClassUser(elClasss.get(i).getId());
				for(int x = 0 ;x < elUsers.size();x++){	
					if(!user.contains(elUsers.get(x).getId())){
						user.add(elUsers.get(x).getId());
						users.add(elUsers.get(x));
					}
				}
			}
			pop.setSendvalue(messageDao.getStrToList3(elClasss, ","));
			//messageDao.messSend(mess, elUser, users);	
			break; 
		default:
			setElmessage("���󣺵���ѧԱ��д����ȷ");
			return "error"; 
		}
		String usersStr=messageDao.getStrToList4(users, ",");
		if(n==1){
			pop.setSendvalue(usersStr);
		}
		messageDao.addPop(pop, usersStr);
		return "pop_mylist";
	}
	
	public String pop_alterInit() throws ElException {
		pop=messageDao.getPopById(pop.getId());
		return "pop_alter";
	}
	
	public String pop_user() throws ElException {
		pop=messageDao.getPopById(pop.getId());
		return "pop_user";
	}
	
	public String pop_alter() throws ElException {
		messageDao.update_pop(pop);
		return "pop_mylist";
	}
	
	public String pop_mylist() throws ElException {
		pops=messageDao.getMyPopList(getSessionIntValue(ElConstants.SESSION_USERID),pop, getPageNow(), getPageSize());
		count=messageDao.getMyPopListCount(getSessionIntValue(ElConstants.SESSION_USERID),pop);
		return "pop_mylist";
	}
	
	public String pop_list() throws ElException {
		pops=messageDao.getPopList(pop, getPageNow(), getPageSize());
		count=messageDao.getPopListCount(pop);
		return "pop_list";
	}
	
	public String pop_del() throws ElException {
		messageDao.deletePopById(pop.getId());
		String pageResult=getRequest().getParameter("pageResult");
		if(pageResult!=null&&"pop_list".equals(pageResult)){
			return "pop_list";
		}
		return "pop_mylist";
	}
	
	public String pop_dels() throws ElException {
		String popIds=getRequest().getParameter("popIds");
		String[] popIdArray=null;
		if(popIds!=null){
			popIdArray=popIds.split(",");
			for (int i = 0; i < popIdArray.length; i++) {
				messageDao.deletePopById(Integer.parseInt(popIdArray[i]));
			}
		}
		String pageResult=getRequest().getParameter("pageResult");
		if(pageResult!=null&&"pop_list".equals(pageResult)){
			return "pop_list";
		}
		return "pop_mylist";
	}
	
	public String pop_setStatus() throws ElException {
		messageDao.updatePopStatus(pop.getId(), pop.getStatus());
		String pageResult=getRequest().getParameter("pageResult");
		if(pageResult!=null&&"pop_list".equals(pageResult)){
			return "pop_list";
		}
		return "pop_mylist";
	}
	
	public String pop_setPops() throws ElException {
		String popIds=getRequest().getParameter("popIds");
		String[] popIdArray=null;
		if(popIds!=null){
			popIdArray=popIds.split(",");
			for (int i = 0; i < popIdArray.length; i++) {
				messageDao.updatePopStatus(Integer.parseInt(popIdArray[i]), pop.getStatus());
			}
		}
		String pageResult=getRequest().getParameter("pageResult");
		if(pageResult!=null&&"pop_list".equals(pageResult)){
			return "pop_list";
		}
		return "pop_mylist";
	}

	private String input_name;

	public String mess_getUserInfo() throws ElException {
		elUser = userDao.getUserById(elUser.getId());
		if (elUser.getRealname() == null || elUser.getRealname().equals(""))
			elUser.setRealname(elUser.getUsername());
		return "mess_getUserInfo";
	}
	// ���ľ���Ա�б��м����ľ��鳤
	public String mess_getZuZhang() throws ElException {
		elUser = userDao.getUserById(elUser.getId());
		if (elUser.getRealname() == null || elUser.getRealname().equals(""))
			elUser.setRealname(elUser.getUsername());
		return "mess_getZuZhang";
	}
	
	// ���ľ���Ա�б��м����ľ��鳤
	public String mess_sendZuZhang() throws ElException {
//		elUser = userDao.getUserById(elUser.getId());
//		if (elUser.getRealname() == null || elUser.getRealname().equals(""))
//			elUser.setRealname(elUser.getUsername());
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYSTATION), -1,
					true);
		else {
			stTree = stationDao.getStTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if(department==null||department.getId()<=0){
			department = depTree;
		}else
			department  = departmentDao.getDepById(department.getId());
		if (station == null || station.getId() <= 0) {
			sub_department = 1;
			station = stTree;
		} else
			station = stationDao.getStById(station.getId());
		setPS(15);
		elUsers = userDao.listUsers(department,station, sub_department, elUser, getPageNow(), getPageSize());
		count = userDao.listUsersSize(department,station, sub_department, elUser);
		roles = roleDao.listRoles();
		return "mess_sendZuZhang";
	}
	
	
	public String mess_getElclassUserInfo() throws ElException {
		elClass = classDao.getClassById(elClass.getId());
		if (elClass.getName() == null || elClass.getName().equals(""))
			elClass.setName(elClass.getName());
		printMsg("{'id':'"+elClass.getId()+"','title':'"+elClass.getName()+"','input_name':'"+input_name+"'}");
	//	return null;
		return "mess_getElclassUserInfo";
	}
	
	public String mess_getExamRoomUserInfo() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
//		if (examRoom.getTitle() == null || examRoom.getTitle().equals(""))
//			examRoom.setTitle(examRoom.getTitle());
		printMsg("{'id':'"+examRoom.getId()+"','title':'"+examRoom.getTitle()+"','input_name':'"+input_name+"'}");
		return null;
//		return "mess_getExamRoomUserInfo";
	}
	/**
	 * �������Ծ�������ʱ����ӿ������ص�json���
	 * @return
	 * @throws ElException
	 */
	public String mess_getExamRoomEpUserInfo() throws ElException {
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		examPapers =eroomDao.getEroomeps(examRoom.getId());
		//printMsg("{'id':'"+examRoom.getId()+"','title':'"+examRoom.getTitle()+"','input_name':'"+input_name+"'}");
		String jsons="";
		for (int i = 0; i < examPapers.size(); i++) {
			ExamPaper ep=examPapers.get(i);
			jsons+="{'epid':'"
			+ep.getId()+"','eptitle':'"
			+ep.getTitle()+"','erid':'"
			+examRoom.getId()+"','ertitle':'"
			+examRoom.getTitle()+"'},";
		}
		if (jsons.length() > 0){
			jsons = "[" + jsons.substring(0, jsons.length() - 1) + "]";
		}else{
			jsons = "[]";
		}
		printMsg("{'eroomEps':" + jsons + "}");
		return null;
//		return "mess_getExamRoomUserInfo";
	}
	
	public String mess_getExamRoomSQinfo() throws ElException {
		examPapers = eroomDao.getEroomeps(examRoom.getId()); 
		return "mess_getExamRoomSQinfo";
	}
	
	public String mess_getDEPUserInfo() throws ElException {
		department = departmentDao.getDepById(department.getId());
		if (department.getName() == null || department.getName().equals(""))
			department.setName(department.getName());
		return "mess_getDEPUserInfo";
	}

	public String mess_getUserInfoJson() throws ElException {
		elUser = userDao.getUserById(elUser.getId());
		if (elUser.getRealname() == null || elUser.getRealname().equals(""))
			elUser.setRealname(elUser.getUsername());
		try {
			getResponse().setContentType("text/html;charset=UTF-8");
			PrintWriter localPrintWriter = getResponse().getWriter();
			localPrintWriter.println("{\"elUser\":{\"id\":\"" + elUser.getId()
					+ "\",\"realname\":\"" + elUser.getRealname() + "\"}}");
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (Exception e) {
			logger.error("ajax ��ȡ��Ա��Ϣ����",e);
		}

		return null;
	}

	// public String mess_getUserInfoJson() throws ElException {
	// elUser = userDao.getUserById(elUser.getId());
	// if (elUser.getRealname() == null || elUser.getRealname().equals(""))
	// elUser.setRealname(elUser.getUsername());
	// return "success";
	// }
	public String messgroupsend() throws ElException {
		
		elUser = userDao
				.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		// String rescs = getRequest().getParameter("receivers");
		// String[] rescs1 = rescs.split(";");
		// elUsers = new ArrayList<ELUser>();
		// for (int i = 0; i < rescs1.length; i++) {
		// elUsers.add(new ELUser(new Integer(rescs1[i].split("_")[0])));
		// }
		 List user = new ArrayList();
		 List<ELUser> users = new ArrayList<ELUser>();
		switch (messageType) {
		case 0://����Ա
			messageDao.messSend(mess, elUser, elUsers);
			break;
		case 1://������
			for(int i = 0 ; i< departments.size(); i++){
				elUsers = userDao.getUserByDepId(departments.get(i).getId());
				for(int x = 0 ;x < elUsers.size();x++){	
					if(!user.contains(elUsers.get(x).getId())){
						user.add(elUsers.get(x).getId());
						users.add(elUsers.get(x));
					}
				}
			}   
			messageDao.messSend(mess, elUser, users);
			break;
		case 2://������
			for(int i = 0 ; i< examRooms.size(); i++){
				elUsers = eroomDao.getEluserByExamRoomId(examRooms.get(i).getId());
				for(int x = 0 ;x < elUsers.size();x++){	
					if(!user.contains(elUsers.get(x).getId())){
						user.add(elUsers.get(x).getId());
						users.add(elUsers.get(x));
					}
				}
			}   
			messageDao.messSend(mess, elUser, users);	
			break;
		case 3://����ѵ�� 
			for(int i = 0 ; i< elClasss.size(); i++){
				elUsers = classDao.getelClassUser(elClasss.get(i).getId());
				for(int x = 0 ;x < elUsers.size();x++){	
					if(!user.contains(elUsers.get(x).getId())){
						user.add(elUsers.get(x).getId());
						users.add(elUsers.get(x));
					}
				}
			}   
			messageDao.messSend(mess, elUser, users);	
			break; 
		default:
			setElmessage("�����ռ�����д����ȷ");
			return "error"; 
		}
		return "messsendsuccess";
	} 
	public String mess_Rec() throws ElException {
		elUser = userDao
				.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		getSession().setAttribute(ElConstants.SESSION_MYMESSAGE,
				messageDao.getNewMesscount(elUser.getId()));
		messs = messageDao.listMessTo(elUser.getId(), getPageNow(),
				getPageSize());
		count = messageDao.messToCount(elUser.getId());
		return "mess_Rec";
	}

	public String mess_SendBox() throws ElException {
		elUser = userDao
				.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		messs = messageDao.listMessFrom(elUser.getId(), getPageNow(),
				getPageSize());
		count = messageDao.messFromCount(elUser.getId());
		return "mess_SendBox";
	}
	
	public String mess_receiveBox() throws ElException {
		elUser = userDao
				.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		messs = messageDao.listMessTo(elUser.getId(), getPageNow(),
				getPageSize());
		count = messageDao.messToCount(elUser.getId());
		return "mess_receiveBox";
	}

	public String mess_delete() throws ElException {
		elUser = userDao
				.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		if (deleteType == 1) {
			if (messageDao.checkSendDel(mess.getMess_id()))
				messageDao.messDelete(mess.getMess_id());
			else {
				messageDao.deleteRec(mess.getMess_id());
			}
			messs = messageDao.listMessTo(elUser.getId(), getPageNow(),
					getPageSize());
			count = messageDao.messToCount(elUser.getId());
			setElmessage("��Ϣɾ��ɹ���");
			return "mess_Rec";
		} else if (deleteType == 2) {
			if (messageDao.checkRecDel(mess.getMess_id()))
				messageDao.messDelete(mess.getMess_id());
			else {
				messageDao.deleteSend(mess.getMess_id());
			}
			messs = messageDao.listMessFrom(elUser.getId(), getPageNow(),
					getPageSize());
			count = messageDao.messFromCount(elUser.getId());
			setElmessage("��Ϣɾ��ɹ���");
			return "mess_Send";
		}
		mess=messageDao.getMessById(mess.getMess_id());
		if(mess!=null){
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_MESSAGE,
				ElLoggerConstants.LOG_TYPE_DELETE,mess.getMess_title(),
				ElLoggerConstants.LOG_RES_SUCC,mess.getMess_id());
		}
		return "error";
	}

	public String mess_info() throws ElException {
		mess = messageDao.getMessById(mess.getMess_id());
		if (deleteType == 1) {
			messageDao.setIsRead(mess.getMess_id());
			// getSession().setAttribute(ElConstants.SESSION_MYMESSAGE,
			// messageDao.getNewMesscount(elUser.getId()));
		}
		return "mess_info";
	}

	public String mess_revertInit() throws ElException {
		mess = messageDao.getMessById(mess.getMess_id());
		return "mess_revertInit";
	}

	public String mess_revert() throws ElException {
		elUser = userDao
				.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		String staddr[] = getRequest().getParameterValues("message.stuffs.description");
		String sttitle[] = getRequest().getParameterValues("message.stuffs.title");
		mess.setMess_from(elUser);
		int id=mess.getMess_id();
//		messageDao.insertMess(mess);
		
		elUsers = new ArrayList<ELUser>();
		elUsers.add(mess.getMess_to());
		messageDao.messSend1(mess, mess.getMess_from(), elUsers,staddr,sttitle);
		
		messageDao.updateMessReply(id);
		
		return "mess_revert_success";
	}
 
	public String mess_sendElclassUserList() throws ElException {  
//		int typeid = cltype == null ? elClTypeDao.getCltypeRoot().getId(): cltype.getId();
//		int depid = getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT); 
		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
			cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		}else{
			cltypeTree = elClTypeDao.getClassLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
		if(elClass != null){			
			elClass.setStatus(5);
		}
//		elclasses = classDao.getClassesList(cltypeTree, depid, typeid, elClass,"5",getSessionIntValue(ElConstants.SESSION_ROLE),"3", getPageNow(), getPageSize());
//		count = classDao.getClassesSize(cltypeTree, depid, typeid, elClass,"5",getSessionIntValue(ElConstants.SESSION_ROLE));
		if (cltype == null || cltype.getId() <= 0) {
 			cltype = cltypeTree;
		} else {
			cltype = elClTypeDao.getClTypeById(cltype.getId());
		}
		elclasses = classDao.getClassList(cltype,elClass,1,"5","3", getPageNow(), getPageSize());
		count = classDao.getClassListSize(cltype,elClass,1,"5");
		return "mess_sendElclassUserList";
	}

	public String mess_sendExamRoomUserList() throws ElException {
//		int erid = eroomLib == null ? eroomDao.getEroomLibRoot().getId()
//				: eroomLib.getId() <= 0 ? eroomDao.getEroomLibRoot().getId()
//						: eroomLib.getId();
//		if (erid == 0) {
//			erid = 1;
//		}		
		if (examRoom != null) {
			examRoom.setValid(5);
		}else{
			examRoom=new ExamRoom();
			examRoom.setValid(5);
			examRoom.setClassid(-1);
		}
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			eroomLibTree = eroomDao.getEroomLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		else {
			eroomLibTree = eroomDao.getEroomLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		} 
//		examRooms = eroomDao.listMyDepExamRoom(eroomLibTree, -2,
//				getSessionIntValue(ElConstants.SESSION_ROLE),
//				" and er.valid not in (0,1,2,3,6,7,8,9,11)", examRoom,
//				getPageNow(), getPageSize());
//		count = eroomDao.listMyDepExamRoomSize(eroomLibTree, -2,
//				getSessionIntValue(ElConstants.SESSION_ROLE),
//				" and er.valid not in (0,1,2,3,6,7,8,9,11)", examRoom);
		if (eroomLib == null || eroomLib.getId() <= 0) {
			eroomLib = eroomLibTree;
		} else {
			eroomLib = eroomDao.getEroomLibById(eroomLib.getId());
		}
		examRooms = eroomDao.listExamRoom(eroomLib, 1,
				" and er.valid not in (0,1,2,3,6,7,8,9,11)", examRoom,
				getPageNow(), getPageSize());
		count = eroomDao.listExamRoomSize(eroomLib, 1,
				" and er.valid not in (0,1,2,3,6,7,8,9,11)", examRoom);
		return "mess_sendExamRoomUserList";
	}

	public String mess_sendDEPUserList() throws ElException {  
		//depTree = departmentDao.getDepTree_level1(1, -1,true);
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		treeAllId = new ArrayList();
		if(!searbm.equals("")){
			String[] searbms = searbm.split(",");
			for(int i = 0 ;i<searbms.length;i++){
				treeAllId.add(Integer.parseInt(searbms[i]));
			} 
		}
		return "mess_sendDEPUserList";
	}
	/**
	 * ���ŷ���
	 * @return
	 * @throws ElException
	 */
	public String msgSend() throws ElException {
		return "msgSend";
	}
	/**
	 * ���ŷ��ʹ���
	 * @return
	 * @throws ElException
	 */
	public String msgSendDo() throws ElException { 
		elUser = userDao
				.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		List<Integer> user = new ArrayList<Integer>();
		List<ELUser> users = new ArrayList<ELUser>();
		switch (messageType) {
		case 0://����Ա
			messageDao.messSend(mess, elUser, elUsers);
			users=elUsers;
			break;
		case 1://������
			for(int i = 0 ; i< departments.size(); i++){
				elUsers = userDao.getUserByDepId(departments.get(i).getId());
				for(int x = 0 ;x < elUsers.size();x++){	
					if(!user.contains(elUsers.get(x).getId())){
						user.add(elUsers.get(x).getId());
						users.add(elUsers.get(x));
					}
				}
			}
			messageDao.messSend(mess, elUser, users);
			break;
		case 2://������
			for(int i = 0 ; i< examRooms.size(); i++){
				elUsers = eroomDao.getEluserByExamRoomId(examRooms.get(i).getId());
				for(int x = 0 ;x < elUsers.size();x++){	
					if(!user.contains(elUsers.get(x).getId())){
						user.add(elUsers.get(x).getId());
						users.add(elUsers.get(x));
					}
				}
			}   
			messageDao.messSend(mess, elUser, users);	
			break;
		case 3://����ѵ�� 
			for(int i = 0 ; i< elClasss.size(); i++){
				elUsers = classDao.getelClassUser(elClasss.get(i).getId());
				for(int x = 0 ;x < elUsers.size();x++){	
					if(!user.contains(elUsers.get(x).getId())){
						user.add(elUsers.get(x).getId());
						users.add(elUsers.get(x));
					}
				}
			}
			messageDao.messSend(mess, elUser, users);	
			break;
		default:
			setElmessage("�����ռ�����д����ȷ");
			return "error";
		}
		//���Ͷ���
		String mes=messageDao.sendMsg(mess, users);
		setElmessage(mes);
		return "msgSendDo";
	}
	public String emailSendUserCount() throws ElException { 
		List<Integer> user = new ArrayList<Integer>();
		List<ELUser> users = new ArrayList<ELUser>();
		switch (messageType) {
		case 0://����Ա
			users=elUsers;
			break;
		case 1://������
			for(int i = 0 ; i< departments.size(); i++){
				elUsers = userDao.getUserByDepId(departments.get(i).getId());
				for(int x = 0 ;x < elUsers.size();x++){	
					if(!user.contains(elUsers.get(x).getId())){
						user.add(elUsers.get(x).getId());
						users.add(elUsers.get(x));
					}
				}
			}
			break;
		case 2://������
			for(int i = 0 ; i< examRooms.size(); i++){
				elUsers = eroomDao.getEluserByExamRoomId(examRooms.get(i).getId());
				for(int x = 0 ;x < elUsers.size();x++){	
					if(!user.contains(elUsers.get(x).getId())){
						user.add(elUsers.get(x).getId());
						users.add(elUsers.get(x));
					}
				}
			}   
			break;
		case 3://����ѵ�� 
			for(int i = 0 ; i< elClasss.size(); i++){
				elUsers = classDao.getelClassUser(elClasss.get(i).getId());
				for(int x = 0 ;x < elUsers.size();x++){	
					if(!user.contains(elUsers.get(x).getId())){
						user.add(elUsers.get(x).getId());
						users.add(elUsers.get(x));
					}
				}
			}
			break;
		default:
			setElmessage("�����ռ�����д����ȷ");
			return "error";
		}
		if(users!=null){
			printMsg("{'userCount':'"+users.size()+"'}");
		}else{
			printMsg("{'userCount':'0'}");
		}
		return null;
	}
	
	/**
	 * �ʼ�Ⱥ��
	 * @return
	 * @throws ElException
	 */
	public String emailSend() throws ElException {
		return "emailSend";
	}
	/**
	 * �ʼ�Ⱥ������
	 * @return
	 * @throws ElException
	 * @throws AddressException 
	 */
	public String emailSendDo() throws ElException, AddressException { 
		elUser = userDao
				.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		List<Integer> user = new ArrayList<Integer>();
		List<ELUser> users = new ArrayList<ELUser>();
		switch (messageType) {
		case 0://����Ա
//			messageDao.messSend(mess, elUser, elUsers);
			users=elUsers;
			break;
		case 1://������
			for(int i = 0 ; i< departments.size(); i++){
				elUsers = userDao.getUserByDepId(departments.get(i).getId());
				for(int x = 0 ;x < elUsers.size();x++){	
					if(!user.contains(elUsers.get(x).getId())){
						user.add(elUsers.get(x).getId());
						users.add(elUsers.get(x));
					}
				}
			}
//			messageDao.messSend(mess, elUser, users);
			break;
		case 2://������
			for(int i = 0 ; i< examRooms.size(); i++){
				elUsers = eroomDao.getEluserByExamRoomId(examRooms.get(i).getId());
				for(int x = 0 ;x < elUsers.size();x++){	
					if(!user.contains(elUsers.get(x).getId())){
						user.add(elUsers.get(x).getId());
						users.add(elUsers.get(x));
					}
				}
			}   
//			messageDao.messSend(mess, elUser, users);	
			break;
		case 3://����ѵ�� 
			for(int i = 0 ; i< elClasss.size(); i++){
				elUsers = classDao.getelClassUser(elClasss.get(i).getId());
				for(int x = 0 ;x < elUsers.size();x++){	
					if(!user.contains(elUsers.get(x).getId())){
						user.add(elUsers.get(x).getId());
						users.add(elUsers.get(x));
					}
				}
			}
//			messageDao.messSend(mess, elUser, users);	
			break;
		default:
			setElmessage("�����ռ�����д����ȷ��");
			return "error";
		}
		if(users==null||users.size()<=0){
			setElmessage("�ռ��˵�����Ϊ0��");
			return "error";
		}
		//�ж������Ϣ��������ٷ���
		String userSizestr=SystemConfOp.getValue(ElConstants.SYSTEM_CONF_EMAIL_UNAMES);
		if(userSizestr==null||"".equals(userSizestr)){
			setElmessage("�Ҳ��������ˣ��������ú÷����˵�������Ϣ��");
			return "error";
		}
		int userSize=userSizestr.split("&").length;//�����˵�����������˵�����˶������ȥ�ֱ����ʼ���
		String userPwdSizestr=SystemConfOp.getValue(ElConstants.SYSTEM_CONF_EMAIL_PWDS);
		if(userPwdSizestr==null||"".equals(userPwdSizestr)){
			setElmessage("�����˵�����δ��д���������ú÷����˵�������Ϣ��");
			return "error";
		}
		int userPwdSize=userPwdSizestr.split("&").length;//�����˵���������
		//������õ��û�������������Ƿ��Ӧ
		if(userSize!=userPwdSize){
			setElmessage("�����˵��û��������������Ӧ���ϣ��������ú÷����˵�������Ϣ��");
			return "error";
		}
		int sendCount=SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_EMAIL_SENDCOUNT);
		//������õ������Ϣ�Ƿ��֧�ַ��͵�����
		if(users.size()>userSize*sendCount){
			setElmessage("�ռ��˵����������˷����˵���������ÿ���˷��͵��������������ú���ز���");
			return "error";
		}
		//�����ʼ�
		String mes=messageDao.sendEmail(mess, users);
		setElmessage(mes);
		return "emailSendDo";
	}
	public List getTreeAllId() {
		return treeAllId;
	}

	public void setTreeAllId(List treeAllId) {
		this.treeAllId = treeAllId;
	}

	public String getSearbm() {
		return searbm;
	}

	public void setSearbm(String searbm) {
		this.searbm = searbm;
	}
	public ELUser getElUser() {
		return elUser;
	} 

	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}

	public int getSub_department() {
		return sub_department;
	}

	public void setSub_department(int sub_department) {
		this.sub_department = sub_department;
	}

	public String mess_send2groupInit() throws ElException {

		return "mess_send2group";
	}

	public String messsend2groupInit() throws ElException {

		return "messsend2group";
	}

	public Message getMess() {
		return mess;
	}

	public void setMess(Message mess) {
		this.mess = mess;
	}

	public List<Message> getMesss() {
		return messs;
	}

	public void setMesss(List<Message> messs) {
		this.messs = messs;
	}

	public String getInput_name() {
		return input_name;
	}

	public void setInput_name(String input_name) {
		this.input_name = input_name;
	}

	public ElClTypeDao getElClTypeDao() {
		return elClTypeDao;
	}

	public void setElClTypeDao(ElClTypeDao elClTypeDao) {
		this.elClTypeDao = elClTypeDao;
	}

	public ElClType getCltypeTree() {
		return cltypeTree;
	}

	public void setCltypeTree(ElClType cltypeTree) {
		this.cltypeTree = cltypeTree;
	}

	public List<ElClass> getElclasses() {
		return elclasses;
	}

	public void setElclasses(List<ElClass> elclasses) {
		this.elclasses = elclasses;
	}

	public ClassDao getClassDao() {
		return classDao;
	}

	public void setClassDao(ClassDao classDao) {
		this.classDao = classDao;
	}

	public ElClass getElClass() {
		return elClass;
	}

	public void setElClass(ElClass elClass) {
		this.elClass = elClass;
	}

	public ElClType getCltype() {
		return cltype;
	}

	public void setCltype(ElClType cltype) {
		this.cltype = cltype;
	}

	public EroomLib getEroomLibTree() {
		return eroomLibTree;
	}

	public void setEroomLibTree(EroomLib eroomLibTree) {
		this.eroomLibTree = eroomLibTree;
	}

	public EroomLib getEroomLib() {
		return eroomLib;
	}

	public void setEroomLib(EroomLib eroomLib) {
		this.eroomLib = eroomLib;
	}

	public EroomDao getEroomDao() {
		return eroomDao;
	}

	public void setEroomDao(EroomDao eroomDao) {
		this.eroomDao = eroomDao;
	}

	public List<ExamRoom> getExamRooms() {
		return examRooms;
	}

	public void setExamRooms(List<ExamRoom> examRooms) {
		this.examRooms = examRooms;
	}

	public ExamRoom getExamRoom() {
		return examRoom;
	}

	public void setExamRoom(ExamRoom examRoom) {
		this.examRoom = examRoom;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public List<ElClass> getElClasss() {
		return elClasss;
	}

	public void setElClasss(List<ElClass> elClasss) {
		this.elClasss = elClasss;
	}
 
	public List<ExamPaper> getExamPapers() {
		return examPapers;
	}

	public void setExamPapers(List<ExamPaper> examPapers) {
		this.examPapers = examPapers;
	}

	public List<ElRole> getRoles() {
		return roles;
	}

	public void setRoles(List<ElRole> roles) {
		this.roles = roles;
	}

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
}
