package com.sopia.openmeetings;

import java.util.List;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.common.SystemConfOp;
import com.sopia.courseman.entities.Course;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;

public class OmAction extends BaseAction {
	private Rooms room;
	private OmDao omDao;
	private List<Rooms> rooms;
	private ELUser elUser;
	private int sub_department;
	private List<ELUser> elUsers;
	private Department depTree;
	private Department department;
	private Course course;

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public List<Rooms> getRooms() {
		return rooms;
	}

	public void setRooms(List<Rooms> rooms) {
		this.rooms = rooms;
	}

	public Rooms getRoom() {
		return room;
	}

	public void setRoom(Rooms room) {
		this.room = room;
	}

	public String addOmRoomInit() throws ElException {

		return "addOmRoom";
	}

	public String addOmRoom() throws ElException {
		omDao.addOmRoom(room);
		return "listOmRoom";
	}

	public String listOmRoom() throws ElException {
		rooms = omDao.listOmRoom(room.getRoomtype());
		return "listOmRoom";
	}

	public String om_assign_list() throws ElException {
		elUsers = omDao.getRoomsUser(room.getId());
		return "om_assign_list";
	}

	public String om_assign_searchInit() throws ElException {
		room = omDao.getOmRoom(room.getId());
		depTree = departmentDao.getDepTree(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);

		return "om_assign_search";
	}

	public String om_assign_search() throws ElException {
		room = omDao.getOmRoom(room.getId());
		// getPageSize() = getPageSize() == 0 ? 10 : getPageSize();
//		elUsers = userDao.getUserByDepId(department.getId(), sub_department,
//				elUser, getPageNow(), getPageSize());
//		if (elUsers != null) {
//			for (int i = 0; i < elUsers.size(); i++) {
//				if (omDao.checkZUserInTr(elUsers.get(i).getId(), room.getId())) {
//					elUsers.get(i).setIntroom(true);
//				}
//			}
//		}
//		count = userDao.getUserByDepIdSize(department.getId(), sub_department,
//				elUser);
//		depTree = departmentDao.getDepTree(
//				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1, true);

		return "om_assign_search_list";
	}

	public String om_assign() throws ElException {
		room = omDao.getOmRoom(room.getId());
		if (null != elUsers) {
			for (int i = 0; i < elUsers.size(); i++) {
				if (!omDao.checkZUserInTr(elUsers.get(i).getId(), room.getId())) {
					omDao.assign2users(elUsers.get(i).getId(), room.getId(),
							room.getRoomtype());
				}
			}
		}
		return "om_assign_list";
	}

	public String om_assign_delete() throws ElException {
		room = omDao.getOmRoom(room.getId());
		omDao.unassign2users(elUser.getId(), room.getId());
		return "om_assign_list";
	}

	public String listMyOmRooms() throws ElException {
		rooms = omDao.listMyRooms(
				getSessionIntValue(ElConstants.SESSION_USERID), room
						.getRoomtype());
		return "listMyOmRooms";
	}

	public String intoRoom() throws ElException {
		room = omDao.getOmRoom(room.getId());

		elUser = userDao
				.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		String sid = OmUtil.getSID();
		String revalue = OmUtil.setUser(SystemConfOp.getValue(ElConstants.OPENMEETINGS_ADMIN_USER), SystemConfOp.getValue(ElConstants.OPENMEETINGS_ADMIN_PWD), sid);
		// String userid =omDao.getUserByLogin(elUser.getUsername());
		System.out.println(revalue);
		int moderator = 0;
		if (course != null
//				&& course.getTeacherId() != null
				&& course.getTeacherId() == getSessionIntValue(ElConstants.SESSION_USERID)) {
			moderator = 1;
//					+ course.getCreater().getId()+"--"+(course.getCreater().getId() == getSessionIntValue(ElConstants.SESSION_USERID)));
		}
		String secureHash = OmUtil.getSecureHashHash(elUser.getUsername(),
				elUser.getRealname(), sid, room.getId(), "", moderator);
		getRequest().setAttribute(
				"url",
				SystemConfOp.getValue(ElConstants.OPENMEETINGS_URL) + "/?secureHash="
						+ secureHash);
		return "intoRoom";
	}

	public OmDao getOmDao() {
		return omDao;
	}

	public void setOmDao(OmDao omDao) {
		this.omDao = omDao;
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
}
