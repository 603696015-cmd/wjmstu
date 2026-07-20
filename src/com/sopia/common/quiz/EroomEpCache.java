package com.sopia.common.quiz;

import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.dao.EroomDao;
import com.sopia.questionman.dao.ExamPaperDao;
import com.sopia.questionman.entities.ExamPaper;

/**
 * @author wys 考场试卷缓存。
 */
public class EroomEpCache implements Runnable {
	private static final Log logger = LogFactory.getLog(EroomEpCache.class);
	private static HashMap<Integer, HashMap<Integer, ExamPaper>> roomexampapers = new HashMap<Integer, HashMap<Integer, ExamPaper>>();

	/**
	 * 设置试卷
	 * 
	 * @param room_epid
	 */
	// public static void setExamPaper(String room_epid){
	// try {
	// ExamPaper ep =
	// ((ExamPaperDao)SpringContextUtil.getBean("examPaperDao")).getEPAllInfoById(getEpId(room_epid));
	// exampapers.put(room_epid, ep);
	// } catch (Exception e) {
	// logger.error("缓存试卷错误",e);
	// }
	// }
	/**
	 * 获取试卷
	 * 
	 * @param room_epid
	 * @return
	 */
	public static ExamPaper getExamPaper(int roomid, int epid) {
		ExamPaper ep = null;
		try {
			if (roomexampapers == null) {
				roomexampapers = new HashMap<Integer, HashMap<Integer, ExamPaper>>();
			}
			if (roomexampapers.get(roomid) == null) {// 没有考场
				HashMap<Integer, ExamPaper> eps = new HashMap<Integer, ExamPaper>();
				roomexampapers.put(roomid, eps);
			}
			if (roomexampapers.get(roomid).get(epid) == null) {// 考场没有试卷
				ep = ((ExamPaperDao) SpringContextUtil.getBean("examPaperDao"))
						.getEPAllInfoById(epid);
				roomexampapers.get(roomid).put(epid, ep);
			}
			ep = roomexampapers.get(roomid).get(epid);
		} catch (Exception e) {
			logger.error("获取缓存试卷错误", e);
		}
		return ep;
	}
	public static void init() {
//		new Thread(new EroomEpCache()).start();
	}
	public static void refresh(int roomid){
		try {
			roomexampapers.remove(roomid);
		} catch (Exception e) {
			logger.error("刷新考场试卷失败", e);
		}
	}
	public void run() {
		// 删除过期的考场
		try {
			while (true) {
				if (roomexampapers != null) {
					Iterator<Integer> its = roomexampapers.keySet().iterator();//entrySet().iterator();
					if (its.hasNext()) {
						Integer id = its.next();
						if(((EroomDao)SpringContextUtil.getBean("eroomDao")).checkEroomIsTimeOut(id)){
							roomexampapers.remove(id);
						}
					}
				}
				Thread.sleep(900 * 1000);
			}
		} catch (Exception e) {
			logger.error("删除过期考场试卷失败", e);
		}
	}
	public static int roomcount(){
		return roomexampapers==null?0:roomexampapers.size();
	}
	public static int exampapercount(){
		int s = 0;
		if(roomcount()!=0){
			if(roomexampapers!=null){
				Iterator<Integer> rooms = roomexampapers.keySet().iterator();
				while (rooms.hasNext()) {
					int i =rooms.next();
					s+=roomexampapers.get(i).size();
				}
			}
		}
		return s;
	}
	/**
	 * 获取试卷id
	 * 
	 * @param room_epid
	 * @return
	 */
	// public static int getEpId(String room_epid){
	// int id = 0;
	// try {
	// String epid=room_epid.split("_")[1];
	// id= Integer.parseInt(epid);
	// } catch (Exception e) {
	// }
	// return id;
	// }
	/**
	 * 获取考场id
	 * 
	 * @param room_epid
	 * @return
	 */
	// public static int getRoomEpId(String room_epid){
	// int id = 0;
	// try {
	// String epid=room_epid.split("_")[0];
	// id= Integer.parseInt(epid);
	// } catch (Exception e) {
	// }
	// return id;
	// }
}
