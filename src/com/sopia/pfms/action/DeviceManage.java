package com.sopia.pfms.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.common.SystemConfOp;
import com.sopia.duman.entities.ELUser;
import com.sopia.pfms.dao.IndexDao;
import com.sopia.pfms.dao.SheBeiDao;
import com.sopia.pfms.entities.SheBei;
import com.sopia.pfms.entities.Shebeileixing;
import com.sopia.pfms.entities.Shenhezhuangtai;
import com.sopia.pfms.entities.Toubaozhuangtai;

public class DeviceManage extends BaseAction {
	private SheBeiDao sheBeiDao;
	private IndexDao indexDao;
	private List<SheBei> shebeilist = new ArrayList<SheBei>();
	private List<Shebeileixing> shebeileixingList = new ArrayList<Shebeileixing>();
	private List<Toubaozhuangtai> toubaozhuangtaiList = new ArrayList<Toubaozhuangtai>();
	private List<Shenhezhuangtai> shenhezhuangtaiList = new ArrayList<Shenhezhuangtai>();
	private SheBei shebei;
	private ELUser elUser;
	private int count;
	private int start;
	private int size;
	
	private int id;
	
	private boolean is_shebei_sh = true;//设备发布是否需要审核
	private int product_sh;
	
	private String delete_inallList;
	private int update_inallList;
	private int updateType;
	
	private Timestamp starttime;
	private Timestamp endtime;
	
	private boolean is_product_fabu_can_alter;//产品发布后是否允许修改
	
	private String shebeiIds;
	
	private String check_json_result;//检验删除的产品审核状态是否是2的返回值
	
	private String areaList;
	
	
	
//	private int type;// 区分查询全部还是查询当前用户
	
	
//	
//	public int getType() {
//		return type;
//	}
//
//	public void setType(int type) {
//		this.type = type;
//	}

	public List<Shebeileixing> getShebeileixingList() {
		return shebeileixingList;
	}

	public void setShebeileixingList(List<Shebeileixing> shebeileixingList) {
		this.shebeileixingList = shebeileixingList;
	}

	public List<Toubaozhuangtai> getToubaozhuangtaiList() {
		return toubaozhuangtaiList;
	}

	public void setToubaozhuangtaiList(List<Toubaozhuangtai> toubaozhuangtaiList) {
		this.toubaozhuangtaiList = toubaozhuangtaiList;
	}

	public List<Shenhezhuangtai> getShenhezhuangtaiList() {
		return shenhezhuangtaiList;
	}

	public void setShenhezhuangtaiList(List<Shenhezhuangtai> shenhezhuangtaiList) {
		this.shenhezhuangtaiList = shenhezhuangtaiList;
	}

	public SheBeiDao getSheBeiDao() {
		return sheBeiDao;
	}

	public void setSheBeiDao(SheBeiDao sheBeiDao) {
		this.sheBeiDao = sheBeiDao;
	}

	public List<SheBei> getShebeilist() {
		return shebeilist;
	}

	public void setShebeilist(List<SheBei> shebeilist) {
		this.shebeilist = shebeilist;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public SheBei getShebei() {
		return shebei;
	}
	
	public void setShebei(SheBei shebei) {
		this.shebei = shebei;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String deviceList() throws ElException{
		size = getPageNow();
		start =  getPageSize();
		toubaozhuangtaiList = sheBeiDao.toubaozhuangtaiList();
		shenhezhuangtaiList = sheBeiDao.shenhezhuangtaiList();
		
		is_product_fabu_can_alter = SystemConfOp.getBooleanValue(ElConstants.PRODUCT_FABU_CAN_ALTER);
		is_shebei_sh = SystemConfOp.getBooleanValue(ElConstants.SHEBEI_NEED_SH);
		
		shebeilist = sheBeiDao.shebeilist(is_shebei_sh,start,size,getSessionIntValue(ElConstants.SESSION_USERID),shebei,starttime,endtime);
		count = sheBeiDao.getCount(is_shebei_sh,getSessionIntValue(ElConstants.SESSION_USERID),shebei,starttime,endtime);
		return "success";
	}
	
	public String allShebeiList() throws ElException{
		size = getPageNow();
		start =  getPageSize();
		toubaozhuangtaiList = sheBeiDao.toubaozhuangtaiList();
		shenhezhuangtaiList = sheBeiDao.shenhezhuangtaiList();
		shebeilist = sheBeiDao.shebeilist(start,size,shebei,starttime,endtime);
		is_product_fabu_can_alter = SystemConfOp.getBooleanValue(ElConstants.PRODUCT_FABU_CAN_ALTER);
		is_shebei_sh = SystemConfOp.getBooleanValue(ElConstants.SHEBEI_NEED_SH);
		count = sheBeiDao.getCount(shebei,starttime,endtime);
		return "success";
	}
	
	public String shenheShebei() throws ElException{
		int roleId = sheBeiDao.getRoleId(getSessionIntValue(ElConstants.SESSION_USERID));
		sheBeiDao.shenheShebei(roleId,id);
		return "success";
	}
	
	public String shenheShebeiNotPass() throws ElException{
		int roleId = sheBeiDao.getRoleId(getSessionIntValue(ElConstants.SESSION_USERID));
		sheBeiDao.shenheShebeiNotPass(roleId,id);
		return "success";
	}
	
	public String addShebeiView() throws ElException{
		toubaozhuangtaiList = sheBeiDao.toubaozhuangtaiList();
		//获取省市县	edone_area
		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();
		areaList = gson.toJson(indexDao.areaList(null,null));
		return "success";
	}
	
	public String addShebei() throws ElException{
		is_shebei_sh = SystemConfOp.getBooleanValue(ElConstants.SHEBEI_NEED_SH);
		if(is_shebei_sh){
			this.product_sh = 1;//需要审核
		}else{
			this.product_sh = 2;//不需要审核
		}
		elUser = sheBeiDao.getELUser(getSessionIntValue(ElConstants.SESSION_USERID));
		sheBeiDao.addShebei(is_shebei_sh,shebei,elUser);
		return "success";
	}
	
	public String deleteShebei() throws ElException{
		if(shebeiIds!=null){
			String[] shebeiIdses=shebeiIds.split(",");
			for (int i = 0; i < shebeiIdses.length; i++) {
				sheBeiDao.deleteShebei(Integer.parseInt(shebeiIdses[i]));
			}
		}else{
			if(id != 0)
				sheBeiDao.deleteShebei(id);
		}
		if(delete_inallList != null){
			if(delete_inallList.equals("all")){
				return "allShebeiList";
			}
		}
		return "deviceList";
	}
	
	public String showShebei() throws ElException{
		shebei = sheBeiDao.showShebei(id);
		return "success";
	}
	
	public String updateShebeiView() throws ElException{
		toubaozhuangtaiList = sheBeiDao.toubaozhuangtaiList();
		shebei = sheBeiDao.showShebei(id);
		if(new Integer(update_inallList) != null){
			if(update_inallList == 1)
				updateType = 1;
		}
		return "success";
	}
	
	public String updateShebei() throws ElException{
		//修改操作时，如果开关处于审核后的产品允许修改状态，则修改通过审核产品后，其审核状态改变
		sheBeiDao.updateShebei(shebei,
				getSessionIntValue(ElConstants.SESSION_USERID),
				sheBeiDao.showShebei(shebei.getId()).getShenhezhuangtai(),
				SystemConfOp.getBooleanValue(ElConstants.PRODUCT_FABU_CAN_ALTER));
		if(new Integer(updateType) != null){
			if(updateType == 1)
				return "updateShebei_success_in_allList";
		}
		return "updateShebei_success_in_list";
	}
	
	public String checkShztBeforeDelete() throws ElException{
		boolean result = false;
		if(id != 0 ){
			result = sheBeiDao.checkShzt(id,"shebei");//true	审核状态2，不可删除
		}
		
		check_json_result = String.valueOf(result);
		
		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":" + check_json_result + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean isIs_shebei_sh() {
		return is_shebei_sh;
	}

	public void setIs_shebei_sh(boolean is_shebei_sh) {
		this.is_shebei_sh = is_shebei_sh;
	}


	public String getDelete_inallList() {
		return delete_inallList;
	}

	public void setDelete_inallList(String delete_inallList) {
		this.delete_inallList = delete_inallList;
	}

	public ELUser getElUser() {
		return elUser;
	}

	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}

	public Timestamp getStarttime() {
		return starttime;
	}

	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}

	public Timestamp getEndtime() {
		return endtime;
	}

	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}

	public int getUpdate_inallList() {
		return update_inallList;
	}

	public void setUpdate_inallList(int update_inallList) {
		this.update_inallList = update_inallList;
	}

	public int getUpdateType() {
		return updateType;
	}

	public void setUpdateType(int updateType) {
		this.updateType = updateType;
	}

	public int getProduct_sh() {
		return product_sh;
	}

	public void setProduct_sh(int product_sh) {
		this.product_sh = product_sh;
	}

	public boolean isIs_product_fabu_can_alter() {
		return is_product_fabu_can_alter;
	}

	public void setIs_product_fabu_can_alter(boolean is_product_fabu_can_alter) {
		this.is_product_fabu_can_alter = is_product_fabu_can_alter;
	}

	public String getShebeiIds() {
		return shebeiIds;
	}

	public void setShebeiIds(String shebeiIds) {
		this.shebeiIds = shebeiIds;
	}

	public String getCheck_json_result() {
		System.out.println("====into get====");
		return check_json_result;
	}

	public void setCheck_json_result(String check_json_result) {
		this.check_json_result = check_json_result;
	}

	public IndexDao getIndexDao() {
		return indexDao;
	}

	public void setIndexDao(IndexDao indexDao) {
		this.indexDao = indexDao;
	}

	public String getAreaList() {
		return areaList;
	}

	public void setAreaList(String areaList) {
		this.areaList = areaList;
	}


}
