package com.sopia.pfms.action;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.sopia.BaseAction;
import com.sopia.common.PfmsUtil;
import com.sopia.common.ElException;
import com.sopia.common.J2EEFileUtil;
import com.sopia.common.UserExcelUtil;
import com.sopia.common.office.ExcelOutPut;
import com.sopia.duman.entities.ELUser;
import com.sopia.pfms.dao.DamageMemberDao;
import com.sopia.pfms.entities.DamageMember;

public class DamageMemberAction extends BaseAction{
	
	private DamageMemberDao damageMemberDao;
	private DamageMember damageMember;
	private List<DamageMember> damageMemberList = new ArrayList<DamageMember>();
	private int count;
	private int id;
	
	private File batchImport;
	private String batchImportFileName;
	
	private Timestamp starttime;
	private Timestamp endtime;
	
	private boolean exprot;
	
	private String damageMemberIds;

	public String getDamageMemberIds() {
		return damageMemberIds;
	}
	public void setDamageMemberIds(String damageMemberIds) {
		this.damageMemberIds = damageMemberIds;
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
	public boolean isExprot() {
		return exprot;
	}
	public void setExprot(boolean exprot) {
		this.exprot = exprot;
	}
	public String getBatchImportFileName() {
		return batchImportFileName;
	}
	public void setBatchImportFileName(String batchImportFileName) {
		this.batchImportFileName = batchImportFileName;
	}
	public File getBatchImport() {
		return batchImport;
	}
	public void setBatchImport(File batchImport) {
		this.batchImport = batchImport;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<DamageMember> getDamageMemberList() {
		return damageMemberList;
	}
	public void setDamageMemberList(List<DamageMember> damageMemberList) {
		this.damageMemberList = damageMemberList;
	}
	public DamageMember getDamageMember() {
		return damageMember;
	}
	public void setDamageMember(DamageMember damageMember) {
		this.damageMember = damageMember;
	}
	public DamageMemberDao getDamageMemberDao() {
		return damageMemberDao;
	}
	public void setDamageMemberDao(DamageMemberDao damageMemberDao) {
		this.damageMemberDao = damageMemberDao;
	}
	public String showDamageMemberAddView() throws ElException{
		return "success";
	}
	
	
	public String addDamageMember() throws ElException{
		damageMemberDao.addDamageMember(damageMember);
		return "success";
	}
	
	public String damageMemberList() throws ElException{
		if(exprot == true){//导出
			getResponse().reset(); 		
			getResponse().setHeader("Content-disposition","attachment; filename=damageMember.xls"); 
			getResponse().setContentType("application/vnd.ms-excel");  
			damageMemberList = damageMemberDao.damageMemberList(null, null,damageMember,null,null);
			try {
				String titles[] = {"姓名","性别","身份证","出生日期"	,"工作单位",	"籍贯","创建时间","图片"};
				String attrs[]= {"name","sex","personId","birthday","workCompany","hometown","fabushijian","picture"};
				new ExcelOutPut().writeExcel("定损员表",getResponse().getOutputStream(),titles,
						DamageMember.class.getName(), damageMemberList, attrs);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}else{
			count = damageMemberDao.getCount(damageMember,starttime,endtime);
			damageMemberList = damageMemberDao.damageMemberList(this.getPageSize(), this.getPageNow(),damageMember,starttime,endtime);
		}
		return "success";
	}
	
	public String showDamageMemberView() throws ElException{
		damageMember = damageMemberDao.showDamageMemberView(id);
		return "success";
	}
	
	public String updateDamageMember() throws ElException{
		damageMemberDao.updateDamageMember(damageMember);
		return "success";
	}
	
	public String deleteDamageMember() throws ElException{
		if(damageMemberIds!=null){
			String[] damageMemberIdses=damageMemberIds.split(",");
			for (int i = 0; i < damageMemberIdses.length; i++) {
				damageMemberDao.deleteDamageMember(Integer.parseInt(damageMemberIdses[i]));
			}
		}
//		damageMemberDao.deleteDamageMember(id);
		return "success";
	}
	
	public String damageMemberBatchImport() throws ElException{
		System.out.println(batchImport);
		if (null != batchImport) {
			if (!J2EEFileUtil.getExtention(batchImportFileName).toLowerCase().equals(
					"xls")) {
				setElmessage("您需要导入的文件格式不正确，请重新选择！");
				return "failure";
			}
			if (batchImport.length() > 10 * 1024 * 1024) {
				setElmessage("您上传的文件过大！");
				return "failure";
			} else {
				List<DamageMember> damageMemberList = new PfmsUtil().writeDamageMembers(batchImport,batchImportFileName);
//				for(DamageMember dm:damageMemberList){
//					damageMemberDao.addDamageMember(dm);
//				}
				
			}
		} else {
			setElmessage("请输入上传文件");
			return "failure";
		}
		return "account_import";
	}

}
