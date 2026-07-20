package com.sopia;


public class SystemInit {
	public static void main(String[] args) {
		try {
			//培训班类别初始化
			/*ElClTypeDao elClTypeDao = new ElClTypeDaoImpl();
			ElClType cltype =new ElClType(0,"所有培训班");
			cltype.setParent(new ElClType(0));
			elClTypeDao.addCltype(cltype);
			ELUser user = new ELUser(1);
			//企业初始化
			Company company = new Company(0,"苏柏亚科技");
			company .setManager(user);
			DepartmentDao depDao = new DepartmentDaoImpl();
			depDao.addCompany(company);
			//总部初始化
			Department dep = new Department(0,"总部");
			dep.setManager(user);
			dep.setParent(new Department(0));
			dep.setCompany(new Company(1));
			depDao.addDep(dep);*/
			//课程类别初始化
		/*	CourseType ct = new CourseType(0,"所有课程");
			ct.setParent(new CourseType(0));
			CourseTypeDao ctdao  =new CourseTypeDaoImpl();
			ctdao.addCtype(ct);
			*/
//			QandEplibDao qeldao = new QandEplibDaoImpl();
			/*QuestionLib qlib  = new QuestionLib(0,"我的试题根目录");
			qlib.setElUser(new ELUser());
			qlib.setParent(new QuestionLib(0));
			qeldao.addQuestionLib(qlib);
			ExamPaperLib eplib = new ExamPaperLib(0,"我的试卷根目录");
			eplib.setElUser(new ELUser(1));
			eplib.setParent(new ExamPaperLib(0));
			qeldao.addepLib(eplib);*/
			
		} catch (Exception e) {
			
		}
	}
}
