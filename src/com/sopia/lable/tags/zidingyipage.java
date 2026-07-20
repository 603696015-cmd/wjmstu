package com.sopia.lable.tags;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sopia.common.ElTag;

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

public class zidingyipage extends TagSupport {
	private static final long serialVersionUID = 3119679319963664116L;
	private String  lablename;

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



	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			CustomLableDao c = new CustomLableDaoImpl();
			ModeBindDao   	mDao=new ModeBindDaoImpl();
			Mode m = (Mode) request.getAttribute("mode");
			Geturl gurl = (Geturl) request.getAttribute("geturl");
				if(getLablename()!=null&&!"".equals(getLablename())){
					Lable l = new Lable();
					
					l=c.lable_getlablesqllablesql("lable_circulation",getLablename());
					if(l!=null){
					//得到总页数信息
					if(isSwitches()){//如果接受参数
						if(m==null){
							out.println("当前页面不属于绑定页面");
							 return TagSupport.SKIP_BODY;
						}
						if(m.getBindtypeid()==2){
							if(isInclude()&&m.getTypebindId()!=1){//如果是包括下级且绑定当前类别并且不是根节点
								if(l.getSql().indexOf(m.getTypetableName())==-1){
									out.println("当前类别不匹配");
									 return TagSupport.SKIP_BODY;
								}
								TreeNode t= mDao.epLibTree(m.getTypebindId(),m.getTypetableName(),-1,true);
								String str=mDao.createPerTypeId(t,t.getId());
								str = " "+m.getTypetableName()+".id in ("+str+") ";
								l.setSql(LableCommon.getchuanzhisql(l.getSql(),str));
							}else if(!isInclude()){//不包括下级
								l.setSql(LableCommon.getchuanzhipagesql(l.getSql()," "+m.getTypetableName()+".id in ("+m.getTypebindId()+") "));
							}
						}else if(m.getBindtypeid()==3&&constraint){
							//如果属于内容绑定页，且打开强制获取类别参数
							int typefieldid=mDao.gettypeidformode(gurl.getContentid(), m.getTableName(), m.getTypefield());
							if(typefieldid<=0){
									out.println("类别错误");
									 return TagSupport.SKIP_BODY;		
							}
							if(isInclude()&&typefieldid!=1){//如果是包括下级并且不是根节点
								if(l.getSql().indexOf(m.getTypetableName())==-1){
									out.println("当前类别不匹配");
									 return TagSupport.SKIP_BODY;
								}
								TreeNode t= mDao.epLibTree(typefieldid,m.getTypetableName(),-1,true);
								String str=mDao.createPerTypeId(t,t.getId());
								str = " "+m.getTypetableName()+".id in ("+str+") ";								
								l.setSql(LableCommon.getchuanzhisql(l.getSql(),str));
							}else if(!isInclude()){//不包括下级
								l.setSql(LableCommon.getchuanzhisql(l.getSql()," "+m.getTypetableName()+".id in ("+typefieldid+") "));
							}
						}
					}
					String arr[] = l.getFieldstr().split("-");
					List<TableField>  list=c.lable_getTableFieldByField(arr);
					List<Map> listMap =  c.getpageMap(list, l.getSql(),l.getPageSize(),1);
					String sqlcount=LableCommon.lablecommon_pagegetcountsql(l.getSql());	
					int count =c.lable_getsqlsagecount(sqlcount);
					int page=0;
					if(count%l.getPageSize()==0){
						page=count/l.getPageSize();
					}else{
						page=count/l.getPageSize()+1;
					}
					writeChilds(out,listMap,count,page,l.getLable(),l.getSql());
					}
				}
			

			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return TagSupport.SKIP_BODY;
	}

	public void writeChilds(JspWriter out, List<Map> obj,int count,int page,String biaoqianti,String sql) throws Exception {
		List<Map> listMap  = (List<Map>) obj;

		 if(listMap!=null){
			 Random random = new Random();
			 int aaaa= random.nextInt(999999-0+1)+0;
			 String lname= aaaa+getLablename();
			 System.out.println(lname);
			 out.println("<table id='"+lname+"' width='100%' ><tr><td valign='top'>");
			 out.println("<table   width='100%'>");
				for (Map map : listMap) {
					out.println(LableCommon.lablecommon_getlable(map,biaoqianti));
				}
				out.println("</table></td></tr>" );

				
				out.print("<tr><td valign='baseline'><table width='100%'><tr><td>");
				
				out.println("[首页]");
				out.print("[上一页]");
				if (page > 0) {
					out.print("<select  onchange='lablepage("+aaaa+",this.options[this.selectedIndex].value)'>");
					for (int i = 1; i <= page; i++) {
						if(i==1)
						out.println("<option value='" + i + "' selected='selected'>" + i 
								+ "</option>");
						else{
							out.println("<option value='" + i + "'>" + i
									+ "</option>");
						}

					}
					out.println("</select> ");
				}
				
				

				
				if ( page >1) {
					out.print("<a   style='cursor: hand' href='javascript:lablepage(");
					out.print(""+aaaa+","+2+" )'>[下一页]</a>");
				
					out.print("<a style='cursor: hand' href='javascript:lablepage("+aaaa+","
							+ page + ")'>[末页]</a>");
				} else {
					out.print("[下一页]");
					out.print("[末页]");
				}
				
				out.print("<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>共</b>" + count
						+ "<b>条</b></span>");
				out.print("<input type='hidden' id='"+aaaa+"' value='"+getLablename()+"' title='"+sql+"' />");
				out.print("</td></tr></table></td></tr></table>");

				
		 }else{
			 
 if(getSetnull()!=null&&!"".equals(getSetnull())){
				 
				 out.println(getSetnull());
			 }else{
				 out.println("&nbsp");
			 }
		 }
		
	
		
	}
}
