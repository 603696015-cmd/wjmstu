package com.sopia.knowledgeManage;

public class KnowledgeManageConstants {
	
	public static final String VIEW_TABLE = "viewcompetence";
	public static final String UPDATE_TABLE = "updatecompetence";
	public static final String COPY_TABLE = "copycompetence";
	public static final String DELETE_TABLE = "deletecompetence";
	public static final String DOWNLOAD_TABLE = "downloadcompetence";
	
	public static final int VIEW_COMPETENCETYPE = 1;
	public static final int UPDATE_COMPETENCETYPE = 2;
	public static final int DELETE_COMPETENCETYPE = 3;
	public static final int COPY_COMPETENCETYPE = 4;
	public static final int DOWNLOAD_COMPETENCETYPE = 5;
	
//	public String getStatus_(){//数据状态中文标示
//		if(status == 0){
//			return "已创建";
//		}
//		if(status == 2){return "修改等待中";}
//		if(status == 3){return "删除等待中";}
//		if(status == 5){return "初审等待中";}
//		if(status == 6){return "初审通过";}
//		if(status == 7){return "初审不通过";}
//		if(status == 8){return "终审等待中";}
//		if(status == 9){return "终审通过";}
//		if(status == 10){return "终审不通过";}
//		return "";
//	}
	public static final int STATUS_0 = 0;//已创建
	public static final int STATUS_2 = 2;//修改等待中
	public static final int STATUS_3 = 3;//删除等待中
	public static final int STATUS_5 = 5;//初审等待中
	public static final int STATUS_6 = 6;//初审通过
	public static final int STATUS_7 = 7;//初审不通过
	public static final int STATUS_8 = 8;//终审等待中
	public static final int STATUS_9 = 9;//终审通过
	public static final int STATUS_10 = 10;//终审不通过
	public static final int STATUS_ALL = -1;//所有状态

}
