package com.sopia.talentman.dao;

import java.util.List;

import com.sopia.common.ElException;
import com.sopia.duman.entities.ELUser;
import com.sopia.studyman.entities.MyExamPaper;
import com.sopia.studyman.entities.MyKTRoom;
import com.sopia.studyman.entities.MyKTRoomC;
import com.sopia.studyman.entities.MyZTRoom;
import com.sopia.talentman.entities.KTRoom;
import com.sopia.talentman.entities.KTRoomColl;
import com.sopia.talentman.entities.ZTRoom;

public interface TalentDao {
	public List<ELUser> listExpert(ELUser user) throws ElException;

	// 场次集管理
	public void alterTRoomColl(KTRoomColl troomcoll) throws ElException;

	public void addTRoomColl(KTRoomColl troomcoll) throws ElException;

	public List<KTRoomColl> listTroomColls(int creater, int pageNow,
			int pageSize) throws ElException;

	public int listTroomCollsSize(int creater) throws ElException;

	public List<KTRoom> listTroomByTRCId(int id) throws ElException;

	public KTRoomColl getTRCbyId(int id) throws ElException;

	public void addTRoom(KTRoom room) throws ElException;

	public KTRoom getTRoomById(int id) throws ElException;

	public void alterTRoom(KTRoom room) throws ElException;

	// 主观
	public void addZtroom(ZTRoom ztroom) throws ElException;

	public List<ZTRoom> listZtroomByUid(int userid, int pageNow, int pageSize)
			throws ElException;

	public int listZtroomByUid(int userid) throws ElException;

	public void alterZtroom(ZTRoom ztroom) throws ElException;

	public ZTRoom getZtroomById(int id) throws ElException;

	public List<ELUser> listAssignZUsers(int zrid, int pageNow, int pageSize)
			throws ElException;

	public int listAssignZUsersSize(int zrid) throws ElException;

	public boolean checkZUserInTr(int userid, int trid) throws ElException;

	public void ztroomAssign2User(int userid, int trid) throws ElException;

	public void ztroomUAssign2User(int userid, int trid) throws ElException;

	public List<MyZTRoom> listMyZtroomByStuId(int userid, int pageNow,
			int pageSize) throws ElException;

	public int listMyZTroomByStuIdSize(int userid) throws ElException;

	// 主观
	public List<ELUser> listAssignUsers(int rid, int pageNow, int pageSize)
			throws ElException;

	public int listAssignUsersSize(int rid) throws ElException;

	public boolean checkUserInTr(int userid, int trid) throws ElException;

	public void troomAssign2User(int userid, int trid) throws ElException;

	public void troomUAssign2User(int userid, int trid) throws ElException;

	public List<MyKTRoomC> listTroomByUid(int userid, int pageNow, int pageSize)
			throws ElException;

	public int listTroomByUidSize(int userid) throws ElException;

	public List<ELUser> listTSByTRid(int trid, int userid) throws ElException;

	public List<ELUser> listXJByTRid(int trid, int userid) throws ElException;

	public MyKTRoomC getMkTroomByTid(int id, int userid) throws ElException;
	public MyExamPaper getKtroomPaper(int userid, int qtroomid)
	throws ElException;
	public List<MyKTRoom> listMkTroomByTid(int id, int userid)
			throws ElException;

	public void evalTroom(MyZTRoom myZTRoom) throws ElException;

	public boolean checkevalTroom(MyZTRoom myZTRoom) throws ElException;

	public void alterevalTroom(MyZTRoom myZTRoom) throws ElException;

	public MyZTRoom getMZTroomByTETId(MyZTRoom myZTRoom) throws ElException;

	public void evalquizsave(MyExamPaper examPaper) throws ElException;

	public void evalquizsubmit(MyExamPaper examPaper) throws ElException;

	public void intoTroomEp(int uid, int troomid) throws ElException;

	public boolean hasInTRoom(int uid, int troomid) throws ElException;

	public MyZTRoom getMyTRoomByUidAndTRid(int uid, int trid)
			throws ElException;

	public List<MyZTRoom> listMyTroomsByUandT(int depid, int subdep, ELUser eu,
			KTRoom troom, int pageNow, int pageSize) throws ElException;

	public int listMyTroomsByUandTSize(int depid, int subdep, ELUser eu,
			KTRoom troom) throws ElException;

	
	public List<MyKTRoomC> listStatKtroom(int trid) throws ElException;
	public List<MyZTRoom> listMyZtroomStat(int trid) throws ElException ;
}
