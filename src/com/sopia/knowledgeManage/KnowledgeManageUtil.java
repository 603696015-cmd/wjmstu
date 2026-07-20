package com.sopia.knowledgeManage;

import java.util.List;

import com.sopia.duman.entities.Department;

public class KnowledgeManageUtil {
	public static boolean checkDepidIsIn(String departments, int depid) {
		boolean flag = true;
		String[] array = null;
		if (departments != null && !departments.equals("")) {
			array = departments.split(",");
			if (array != null) {
				for (int i = 0; i < array.length; i++) {
					if (depid == Integer.parseInt(array[i])) {
						flag = false;
						break;
					}
				}
			}
		}
		return flag;

	}
	
	public static boolean checkUseridIsIn(String[] array,int userid){
		boolean flag = false;
		if(array!=null){
			for(int i=0;i<array.length;i++){
				if(Integer.parseInt(array[i]) == userid){
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	public static String getCompetenceTableByCompetenceType(int type) {
		String competenceTable = "";
		switch (type) {
		case 1:
			competenceTable = KnowledgeManageConstants.VIEW_TABLE;
			break;
		case 2:
			competenceTable = KnowledgeManageConstants.UPDATE_TABLE;
			break;
		case 3:
			competenceTable = KnowledgeManageConstants.DELETE_TABLE;
			break;
		case 4:
			competenceTable = KnowledgeManageConstants.COPY_TABLE;
			break;
		case 5:
			competenceTable = KnowledgeManageConstants.DOWNLOAD_TABLE;
			break;
		}
		return competenceTable;
	}
	
	
	public static String getSqlByDeps(List<Department> deps) {
		String sql = " ";
		if(deps!=null&&deps.size()>0){
			for(int i=0;i<deps.size();i++){
				if(i == 0){
					sql += "select id from department where lid>="+deps.get(i).getLid()+" and rid<="+deps.get(i).getRid() + " ";
				}else{
					sql += " union " + 
							" select id from department where lid>= "+deps.get(i).getLid() + " and rid<=" + deps.get(i).getRid() + " ";
				}
			}
		}
		return sql;
	}

}
