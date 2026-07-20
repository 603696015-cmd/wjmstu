package com.sopia.mock.action;

import java.util.ArrayList;
import java.util.List;

import sync.main.Task;
import sync.thread.ThreadPool;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.dao.DepartmentDao;
import com.sopia.duman.dao.impl.DepartmentDaoImpl;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.dao.ExamPaperDao;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.questionman.entities.ExamPaperLib;

public class MockExamAction extends BaseAction{

	/**
	 * 锟皆撅拷锟斤拷锟斤拷锟�
	 */
	private ExamPaperLib eplTree;
	
	private ExamPaperLib examTypes;
	
	private ExamPaperLib examPaperLib;
	
	/**
	 * 锟皆撅拷锟饺★拷锟捷诧拷
	 */
	private ExamPaperDao examPaperDao;
	
	private ExamPaper examPaper;
	
	private List<ExamPaper> examPapers;
	
	private Department depTree;
	
	
	private int sublibs;
	
	public ExamPaperLib getEplTree() {
		return eplTree;
	}



	public void setEplTree(ExamPaperLib eplTree) {
		this.eplTree = eplTree;
	}



	public ExamPaperLib getExamPaperLib() {
		return examPaperLib;
	}



	public void setExamPaperLib(ExamPaperLib examPaperLib) {
		this.examPaperLib = examPaperLib;
	}



	public ExamPaperDao getExamPaperDao() {
		return examPaperDao;
	}



	public void setExamPaperDao(ExamPaperDao examPaperDao) {
		this.examPaperDao = examPaperDao;
	}



	public ExamPaper getExamPaper() {
		return examPaper;
	}



	public void setExamPaper(ExamPaper examPaper) {
		this.examPaper = examPaper;
	}



	public List<ExamPaper> getExamPapers() {
		return examPapers;
	}



	public void setExamPapers(List<ExamPaper> examPapers) {
		this.examPapers = examPapers;
	}



	public int getSublibs() {
		return sublibs;
	}



	public void setSublibs(int sublibs) {
		this.sublibs = sublibs;
	}



	public ExamPaperLib getExamTypes() {
		return examTypes;
	}



	public void setExamTypes(ExamPaperLib examTypes) {
		this.examTypes = examTypes;
	}

	public Department getDepTree() {
		return depTree;
	}



	public void setDepTree(Department depTree) {
		this.depTree = depTree;
	}



	/**
	 *  鍚屾鏁版嵁
	 *  @author dongke
	 *  @return
	 */
	public String mockExamList() throws ElException{
		/*eplTree = examPaperDao.epLibTree(0, 0, -1,true);//锟皆撅拷锟斤拷
		ExamPaperLib epl = null;
		if (examPaper == null || examPaper.getEpl() == null
				|| examPaper.getEpl().getId() <= 0) {
			epl = eplTree;
			// sublibs = 1;
		} else {
			if (getSessionIntValue(ElConstants.SESSION_ROLE) != 1
					&& !((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
							.checkNode(examPaper.getEpl().getId(), eplTree,
									"exampaperlib")) {
				setElmessage("锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷权锟斤拷锟斤拷锟侥节点！");
				return "error";
			}
			epl = examPaperDao.getEpLById(examPaper.getEpl().getId());
		}
		sublibs = examPaper == null ? 1 : sublibs;
		
		//
		examTypes = examPaperDao.epLibTree(200, 0, -1, true);
		
		examPapers = examPaperDao.listEpsByEplId(epl, sublibs, examPaper,
				getPageNow(), getPageSize(), 1);
		count = examPaperDao.listEpsByEpIdSize(epl, sublibs, examPaper, 1);*/
		
		/*String strJson = Test.jsonToString();
		List<Department> arr = new ArrayList<Department>();
		List<Department> result = new ArrayList<Department>();
		arr = Test.jsonToArrayList(strJson, Department.class);
		int count = 0;
		for (int i = 0; i < arr.size(); i++) {
			if(arr.get(i).getDiji().equals("")){
				result.add(arr.get(i));
			}
		}
		
		DepartmentDao departmentDao = new DepartmentDaoImpl();
		ElNodeSQL elSql = new ElNodeSQL();
		for (Department department : result) {
			com.sopia.duman.entities.Department dep1 = new com.sopia.duman.entities.Department();
			if (dep1.getParent() == null) {
				// 鍥犱负ajax鏍戞湁鐐圭己闄�
				dep1.setParent(new ElNode(1));
			}
			if(dep1.getManager() == null){
				dep1.setManager(new ELUser(0));
			}
			dep1.setName(department.getShengJiName());
			dep1.setBh(department.getQuHuaDaiMa());
			try {
				departmentDao.addDep(dep1);
				elSql
				.updatetlrid("department");
			} catch (ElException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}*/
		depTree = departmentDao.getDepTree_level1(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
				true);
		ThreadPool threadPool = new ThreadPool();
		for (Department dep : depTree.getChild()) {
			//杩涜鎵ц浠诲姟鍚屾鏁版嵁
			threadPool.execute(new Task(dep.getName(),dep.getId()));
		}
		
		return "mockpapger_list";
	}
}
