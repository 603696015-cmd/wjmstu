package com.sopia.talentman.action;

import java.util.List;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElRole;
import com.sopia.studyman.entities.MyKTRoomC;
import com.sopia.studyman.entities.MyZTRoom;
import com.sopia.talentman.dao.TalentDao;
import com.sopia.talentman.entities.KTRoom;
import com.sopia.talentman.entities.KTRoomColl;
import com.sopia.talentman.entities.ZTRoom;

public class TalentAction extends BaseAction {
	private List<ELUser> experts;
	private TalentDao talentDao;
	private KTRoomColl troomcoll;
	private KTRoom troom;
	private List<KTRoomColl> troomcolls;
	private List<ELUser> assignedUsers;
	private ELUser elUser;
	private int sub_department;
	private List<ELUser> elUsers;
	private Department depTree;
	private Department department;
	private List<MyZTRoom> mytrooms;
	private MyZTRoom mytroom;
	private ZTRoom ztroom;
	private List<ZTRoom> ztrooms;
	private List<MyZTRoom> myztrooms;
	private List<MyKTRoomC> myktrooms;
	private MyKTRoomC myktroomc;
	private ElRole role;
	
	public ElRole getRole() {
		return role;
	}

	public void setRole(ElRole role) {
		this.role = role;
	}

	public List<MyZTRoom> getMyztrooms() {
		return myztrooms;
	}

	public void setMyztrooms(List<MyZTRoom> myztrooms) {
		this.myztrooms = myztrooms;
	}

	public List<MyKTRoomC> getMyktrooms() {
		return myktrooms;
	}

	public void setMyktrooms(List<MyKTRoomC> myktrooms) {
		this.myktrooms = myktrooms;
	}

	public ZTRoom getZtroom() {
		return ztroom;
	}

	public void setZtroom(ZTRoom ztroom) {
		this.ztroom = ztroom;
	}

	public List<MyZTRoom> getMytrooms() {
		return mytrooms;
	}

	public void setMytrooms(List<MyZTRoom> mytrooms) {
		this.mytrooms = mytrooms;
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

	public List<ELUser> getElUsers() {
		return elUsers;
	}

	public void setElUsers(List<ELUser> elUsers) {
		this.elUsers = elUsers;
	}

	public Department getDepTree() {
		return depTree;
	}

	public void setDepTree(Department depTree) {
		this.depTree = depTree;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public List<ELUser> getAssignedUsers() {
		return assignedUsers;
	}

	public void setAssignedUsers(List<ELUser> assignedUsers) {
		this.assignedUsers = assignedUsers;
	}

	public String talent_expert_list() throws ElException {
//			getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
			int roleid = 6;
			int depid = department ==null? 1:department.getId();
			if(roleid==0){
//			elUsers = userDao.getUserByDepId(depid,
//					sub_department, elUser, getPageNow(), getPageSize());
//			count = userDao.getUserByDepIdSize(depid,
//					sub_department, elUser);
			}
			else{
				elUsers = userDao.getUserByDepId(depid,
						sub_department, elUser,roleid, getPageNow(), getPageSize());
				count = userDao.getUserByDepIdSize(depid,
						sub_department, elUser,roleid);
				
			}
		return "talent_expert_list";
	}
	
	public String talent_expert_search() throws ElException {
		depTree = departmentDao.getDepTree(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		return "talent_expert_search";
	}

	public String talent_expert_addInit() throws ElException {

		return "talent_expert_add";
	}

	public String talent_expert_add() throws ElException {

		return "talent_expert_list";
	}

	public String talent_roomcollect_addInit() throws ElException {

		return "talent_roomcollect_add";
	}

	public String talent_roomcollect_add() throws ElException {
		if (null != troomcoll) {
			troomcoll.setCreater(new ELUser(
					getSessionIntValue(ElConstants.SESSION_USERID)));
			talentDao.addTRoomColl(troomcoll);
		}
		return "talent_roomcollect_list";
	}

	public String talent_roomcollect_alterInit() throws ElException {
		troomcoll = talentDao.getTRCbyId(troomcoll.getId());
		return "talent_roomcollect_alter";
	}

	public String talent_roomcollect_alter() throws ElException {
		talentDao.alterTRoomColl(troomcoll);
		return "talent_troom_list";
	}

	public String talent_roomcollect_list() throws ElException {
//		getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
		troomcolls = talentDao.listTroomColls(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(), getPageSize());
		count = talentDao
				.listTroomCollsSize(getSessionIntValue(ElConstants.SESSION_USERID));

		return "talent_roomcollect_list";
	}

	public String talent_troom_list() throws ElException {
		troomcoll = talentDao.getTRCbyId(troomcoll.getId());
		troomcoll.setTrooms(talentDao.listTroomByTRCId(troomcoll.getId()));
		return "talent_troom_list";
	}

	public String talent_troom_addInit() throws ElException {
		troomcoll = talentDao.getTRCbyId(troomcoll.getId());
		return "talent_troom_add";
	}

	public String talent_troom_add() throws ElException {
		troomcoll = talentDao.getTRCbyId(troomcoll.getId());
		talentDao.addTRoom(troom);
		return "talent_troom_list";
	}

	public String talent_troom_alterInit() throws ElException {
		troomcoll = talentDao.getTRCbyId(troomcoll.getId());
		troom = talentDao.getTRoomById(troom.getId());
		return "talent_troom_alter";
	}

	public String talent_troom_alter() throws ElException {
		talentDao.alterTRoom(troom);
		return "talent_troom_list";
	}

	public String talent_troom_assign_list() throws ElException {
		troomcoll = talentDao.getTRCbyId(troomcoll.getId());
		// troom = talentDao.getTRoomById(troom.getId());
//		getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
		assignedUsers = talentDao
				.listAssignUsers(troomcoll.getId(), getPageNow(), getPageSize());
		count = talentDao.listAssignUsersSize(troomcoll.getId());
		return "talent_troom_assign_list";
	}

	public String talent_troom_assign_searchInit() throws ElException {
		troomcoll = talentDao.getTRCbyId(troomcoll.getId());
		// troom = talentDao.getTRoomById(troom.getId());
		depTree = departmentDao.getDepTree(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		return "talent_troom_assign_search";
	}

	public String talent_troom_assign_search() throws ElException {
		troomcoll = talentDao.getTRCbyId(troomcoll.getId());
		// troom = talentDao.getTRoomById(troom.getId());
//		getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
//		elUsers = userDao.getUserByDepId(department.getId(),
//				sub_department, elUser, getPageNow(), getPageSize());
		if (elUsers != null) {
			for (int i = 0; i < elUsers.size(); i++) {
				if (talentDao.checkUserInTr(elUsers.get(i).getId(),
						troomcoll.getId())) {
					elUsers.get(i).setIntroom(true);
				}
			}
		}
//		count = userDao.getUserByDepIdSize(department.getId(),
//				sub_department, elUser);
		depTree = departmentDao.getDepTree(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		return "talent_troom_assign_list";
	}

	public String talent_troom_assign_add() throws ElException {
		if (elUsers != null) {
			for (int i = 0; i < elUsers.size(); i++) {
				if (!talentDao.checkUserInTr(elUsers.get(i).getId(),
						troomcoll.getId())) {
					talentDao.troomAssign2User(elUsers.get(i).getId(),
							troomcoll.getId());
				}
			}
		}
		troomcoll = talentDao.getTRCbyId(troomcoll.getId());
		// troom = talentDao.getTRoomById(troom.getId());
//		getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
//		elUsers = userDao.getUserByDepId(department.getId(),
//				sub_department, elUser, getPageNow(), getPageSize());
		if (elUsers != null) {
			for (int i = 0; i < elUsers.size(); i++) {
				if (talentDao.checkUserInTr(elUsers.get(i).getId(),
						troomcoll.getId())) {
					elUsers.get(i).setIntroom(true);
				}
			}
		}
//		count = userDao.getUserByDepIdSize(department.getId(),
//				sub_department, elUser);
		depTree = departmentDao.getDepTree(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		return "talent_troom_assign_add";
	}
	/**
	 * 客观评价统计
	 * @return
	 * @throws ElException
	 */
	public String talent_room_statInit()throws ElException{
//		company = departmentDao
//		.getCompanyById(getSessionIntValue(ElConstants.SESSION_MYCOMPANY));
//		depTree = departmentDao.getDepTree(
//				getSessionIntValue(ElConstants.SESSION_MYCOMPANY),
//				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
//		getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
		troomcolls = talentDao.listTroomColls(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(), getPageSize());
		count = talentDao
				.listTroomCollsSize(getSessionIntValue(ElConstants.SESSION_USERID));

		return "talent_room_statInit";
	}
	public String talent_room_statlist()throws ElException{
		
//		getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
//		String rtitle = troomcoll==null?"":troomcoll.getTitle();
		myktrooms = talentDao.listStatKtroom ( troomcoll.getId() );

		return "talent_room_statlist";
	}
	public String talent_room_statview()throws ElException{
		
//		getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
//		String rtitle = troomcoll==null?"":troomcoll.getTitle();
//		myktrooms = talentDao.listStatKtroom ( troomcoll.getId() );
		myktroomc = talentDao.getMkTroomByTid(troomcoll.getId(),
				elUser.getId());
		myktroomc.setMyktrooms(talentDao.listMkTroomByTid(troomcoll.getId(),
				elUser.getId()));
		
		return "talent_room_statview";
	}
	public String talent_troom_assign_delete() throws ElException {
		if (elUser != null) {
			talentDao.troomUAssign2User(elUser.getId(), troomcoll.getId());
		}
		return "talent_troom_assign_list";
	}

	public String talent_searchInit() throws ElException {
		depTree = departmentDao.getDepTree(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);

		return "talent_search";
	}

	public String talent_search() throws ElException {

		mytrooms = talentDao.listMyTroomsByUandT(department.getId(),
				sub_department, elUser, troom, getPageNow(), getPageSize());
		count = talentDao.listMyTroomsByUandTSize(department.getId(),
				sub_department, elUser, troom);
		return "talent_searchlist";
	}

	public String talent_troom_result_view() throws ElException {
		troom = talentDao.getTRoomById(troom.getId());
		mytroom = talentDao.getMyTRoomByUidAndTRid(elUser.getId(), troom
				.getId());
		elUser = userDao.getUserById(elUser.getId());
		return "talent_troom_result_view";
	}

	/**
	 * 主观场次管理
	 * 
	 * @return
	 */
	public String talent_ztroom_addInit() throws ElException {

		return "talent_ztroom_add";
	}

	public String talent_ztroom_add() throws ElException {
		if (null != ztroom) {
			ztroom.setCreater(new ELUser(
					getSessionIntValue(ElConstants.SESSION_USERID)));
			talentDao.addZtroom(ztroom);
		}

		return "talent_ztroom_list";
	}

	public String talent_ztroom_list() throws ElException {
//		getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
		ztrooms = talentDao.listZtroomByUid(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(), getPageSize());
		count = talentDao
				.listZtroomByUid(getSessionIntValue(ElConstants.SESSION_USERID));

		return "talent_ztroom_list";
	}

	public String talent_ztroom_alterInit() throws ElException {
		ztroom = talentDao.getZtroomById(ztroom.getId());
		return "talent_ztroom_alter";
	}

	public String talent_ztroom_alter() throws ElException {
		talentDao.alterZtroom(ztroom);

		return "talent_ztroom_list";
	}
	public String talent_ztroom_assign_list() throws ElException {
//		getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
		assignedUsers = talentDao
				.listAssignZUsers(ztroom.getId(), getPageNow(), getPageSize());
		count = talentDao.listAssignZUsersSize(ztroom.getId());
		return "talent_ztroom_assign_list";
	}
	public String talent_ztroom_assign_searchInit() throws ElException {
		ztroom = talentDao.getZtroomById(ztroom.getId());
		depTree = departmentDao.getDepTree(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		return "talent_ztroom_assign_search";
	}

	public String talent_ztroom_assign_search() throws ElException {
		ztroom = talentDao.getZtroomById(ztroom.getId());
//		getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
//		elUsers = userDao.getUserByDepId(department.getId(),
//				sub_department, elUser, getPageNow(), getPageSize());
		if (elUsers != null) {
			for (int i = 0; i < elUsers.size(); i++) {
				if (talentDao.checkZUserInTr(elUsers.get(i).getId(),
						ztroom.getId())) {
					elUsers.get(i).setIntroom(true);
				}
			}
		}
//		count = userDao.getUserByDepIdSize(department.getId(),
//				sub_department, elUser);
		depTree = departmentDao.getDepTree(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		return "talent_ztroom_assign_list";
	}

	public String talent_ztroom_assign_add() throws ElException {
		if (elUsers != null) {
			for (int i = 0; i < elUsers.size(); i++) {
				if (!talentDao.checkZUserInTr(elUsers.get(i).getId(),
						ztroom.getId())) {
					talentDao.ztroomAssign2User(elUsers.get(i).getId(),
							ztroom.getId());
				}
			}
		}
		// troom = talentDao.getTRoomById(troom.getId());
//		getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
//		elUsers = userDao.getUserByDepId(department.getId(),
//				sub_department, elUser, getPageNow(), getPageSize());
		if (elUsers != null) {
			for (int i = 0; i < elUsers.size(); i++) {
				if (talentDao.checkZUserInTr(elUsers.get(i).getId(),
						ztroom.getId())) {
					elUsers.get(i).setIntroom(true);
				}
			}
		}
//		count = userDao.getUserByDepIdSize(department.getId(),
//				sub_department, elUser);
		depTree = departmentDao.getDepTree(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);
		return "talent_ztroom_assign_add";
	}
	public String talent_ztroom_statInit() throws ElException {
//		getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
		ztrooms = talentDao.listZtroomByUid(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(), getPageSize());
		count = talentDao
				.listZtroomByUid(getSessionIntValue(ElConstants.SESSION_USERID));

		return "talent_ztroom_statInit";
	}
	public String talent_ztroom_statlist() throws ElException {
//		getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
		myztrooms = talentDao.listMyZtroomStat(
				getSessionIntValue(ElConstants.SESSION_USERID));

		return "talent_ztroom_statlist";
	}
	public String talent_ztroom_statview() throws ElException {
//		getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
		ztrooms = talentDao.listZtroomByUid(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(), getPageSize());
		count = talentDao
				.listZtroomByUid(getSessionIntValue(ElConstants.SESSION_USERID));

		return "talent_ztroom_statview";
	}
	public String talent_ztroom_assign_delete() throws ElException {
		if (elUser != null) {
			talentDao.ztroomUAssign2User(elUser.getId(), ztroom.getId());
		}
		return "talent_ztroom_assign_list";
	}
	public List<ELUser> getExperts() {
		return experts;
	}

	public void setExperts(List<ELUser> experts) {
		this.experts = experts;
	}

	public TalentDao getTalentDao() {
		return talentDao;
	}

	public void setTalentDao(TalentDao talentDao) {
		this.talentDao = talentDao;
	}

	public KTRoomColl getTroomcoll() {
		return troomcoll;
	}

	public void setTroomcoll(KTRoomColl troomcoll) {
		this.troomcoll = troomcoll;
	}

	public KTRoom getTroom() {
		return troom;
	}

	public void setTroom(KTRoom troom) {
		this.troom = troom;
	}

	public List<KTRoomColl> getTroomcolls() {
		return troomcolls;
	}

	public void setTroomcolls(List<KTRoomColl> troomcolls) {
		this.troomcolls = troomcolls;
	}

	public MyZTRoom getMytroom() {
		return mytroom;
	}

	public void setMytroom(MyZTRoom mytroom) {
		this.mytroom = mytroom;
	}

	public List<ZTRoom> getZtrooms() {
		return ztrooms;
	}

	public void setZtrooms(List<ZTRoom> ztrooms) {
		this.ztrooms = ztrooms;
	}

	public MyKTRoomC getMyktroomc() {
		return myktroomc;
	}

	public void setMyktroomc(MyKTRoomC myktroomc) {
		this.myktroomc = myktroomc;
	}

}
