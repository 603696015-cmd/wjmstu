package com.sopia.lable.tags;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts2.ServletActionContext;

import com.sopia.common.ElTag;
import com.sopia.courseman.entities.CourseType;

import com.sopia.forumman.entities.ForumBlockType;
import com.sopia.lable.common.LableCommon;
import com.sopia.lable.dao.CustomLableDao;
import com.sopia.lable.dao.ModeBindDao;
import com.sopia.lable.dao.impl.CustomLableDaoImpl;
import com.sopia.lable.dao.impl.ModeBindDaoImpl;
import com.sopia.lable.entites.Geturl;
import com.sopia.lable.entites.Lable;
import com.sopia.lable.entites.Mode;
import com.sopia.lable.entites.TableField;
import com.sopia.lable.entites.TreeNode;

public class zidingyiloop extends TagSupport {
	private static final long serialVersionUID = 3119679319963664116L;
	private String  lablename;
	private int    xunhuan;
	private String  setnull;
	private boolean switches;
	private boolean include;
	private boolean constraint;
	
	
	public boolean isConstraint() {
		return constraint;
	}

	public void setConstraint(boolean constraint) {
		this.constraint = constraint;
	}

	public boolean isInclude() {
		return include;
	}

	public void setInclude(boolean include) {
		this.include = include;
	}

	public boolean isSwitches() {
		return switches;
	}

	public void setSwitches(boolean switches) {
		this.switches = switches;
	}

	public String getSetnull() {
		return setnull;
	}

	public void setSetnull(String setnull) {
		this.setnull = setnull;
	}


	public String getLablename() {
		return lablename;
	}

	public void setLablename(String lablename) {
		this.lablename = lablename;
	}

	public int getXunhuan() {
		return xunhuan;
	}

	public void setXunhuan(int xunhuan) {
		this.xunhuan = xunhuan;
	}

	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			Mode m = (Mode) request.getAttribute("mode");
			Geturl gurl = (Geturl) request.getAttribute("geturl");
			CustomLableDao c = new CustomLableDaoImpl();
			ModeBindDao   	mDao=new ModeBindDaoImpl();
				if(getLablename()!=null&&!"".equals(getLablename())){
					Lable l = new Lable();
					l.setName(getLablename());
					l=c.lable_getlablesqllable("lable_circulation", l);
					if(l!=null){
					String orderstr=l.getOrder();
						if(l.getOrder().indexOf(".")!=-1){
							//如果存在字段信息，则去掉最后一个逗号
							orderstr=LableCommon.lablecommon_getorder(l.getOrder(),","," ");
						}
						//得到sql语句
						String  sqlstr=LableCommon.lablecommon_getsql(l.getTablestr(),l.getFieldstr(),l.getSqlCondition(),orderstr,l.getPageSize());
						if(isSwitches()){//如果接受参数
							if(m==null){
								out.println("当前页面不属于绑定页面");
								 return TagSupport.SKIP_BODY;
							}
							if(m.getBindtypeid()==2){
								if(isInclude()&&m.getTypebindId()!=1){//如果是包括下级并且不是根节点
									if(sqlstr.indexOf(m.getTypetableName())==-1){
										out.println("当前类别不匹配");
										 return TagSupport.SKIP_BODY;
									}
									TreeNode t= mDao.epLibTree(m.getTypebindId(),m.getTypetableName(),-1,true);
									String str=mDao.createPerTypeId(t,t.getId());
									str = " "+m.getTypetableName()+".id in ("+str+") ";								
									sqlstr=LableCommon.getchuanzhisql(sqlstr,str);
								}else if(!isInclude()){//不包括下级
									sqlstr=LableCommon.getchuanzhisql(sqlstr," "+m.getTypetableName()+".id in ("+m.getTypebindId()+") ");
								}
							}else if(m.getBindtypeid()==3){//内容页传参数
								
								if(sqlstr.indexOf(m.getTableName())==-1){
									out.println("内容模块不匹配");
									return TagSupport.SKIP_BODY;
								}
								if(isConstraint()){//如果强制获取类别参数
									int typefieldid=mDao.gettypeidformode(gurl.getContentid(), m.getTableName(), m.getTypefield());
									if(typefieldid<=0){
											out.println("类别错误");
											 return TagSupport.SKIP_BODY;
									}
									if(isInclude()&&typefieldid!=1){//如果是包括下级并且不是根节点
										if(sqlstr.indexOf(m.getTypetableName())==-1){
											out.println("当前类别不匹配");
											 return TagSupport.SKIP_BODY;
										}
										TreeNode t= mDao.epLibTree(typefieldid,m.getTypetableName(),-1,true);
										String str=mDao.createPerTypeId(t,t.getId());
										str = " "+m.getTypetableName()+".id in ("+str+") ";								
										sqlstr=LableCommon.getchuanzhisql(sqlstr,str);
									}else if(!isInclude()){//不包括下级
										sqlstr=LableCommon.getchuanzhisql(sqlstr," "+m.getTypetableName()+".id in ("+typefieldid+") ");
									}
									
								}else{//自动获取参数内容ID参数
									sqlstr=LableCommon.getchuanzhisql(sqlstr," "+m.getTableName()+"."+m.getKey()+"="+gurl.getContentid()+" ");
								}
								
							}
						}
					
					//得到查询字段信息
					String arr[] = l.getFieldstr().split("-");
					List<TableField>  list=c.lable_getTableFieldByField(arr);
					List<Map<String,Object>> listMap =null;
					if(!sqlstr.equals("")){
						listMap=  c.getMap(list, sqlstr,null);
					}
					
					writeChilds(out,listMap,l);
					}
				}
				

			System.out.println(getLablename());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return TagSupport.SKIP_BODY;
	}

	public void writeChilds(JspWriter out, List<Map<String,Object>> obj,Lable l) throws Exception {
		List<Map<String,Object>> listMap  =  obj;
		String biaoqianti=l.getLable();
		int count=getXunhuan();
		 boolean flag=true;
		 if(count==0) flag=false;
		 if(listMap!=null){
				for (Map map : listMap) {
					out.println(LableCommon.lablecommon_getlable(map,biaoqianti));
					
					if(flag){
						count--;
						if(count==0) break;
					}
				}
				
				
		 }else{
			 if(getSetnull()!=null&&!"".equals(getSetnull())){
				 
				 out.println(getSetnull());
			 }else{
				 out.println("&nbsp");
			 }
			 
		 }
	
		
	}
}
