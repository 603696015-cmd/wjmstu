package com.sopia.schedule.tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.common.SystemConfOp;
import com.sopia.duman.entities.Department;
import com.sopia.lable.common.LableCommon;
import com.sopia.lable.dao.CustomLableDao;
import com.sopia.lable.dao.impl.CustomLableDaoImpl;
import com.sopia.lable.entites.Lable;
import com.sopia.lable.entites.TableField;
import com.sopia.schedule.JisuanzuUtil;
import com.sopia.schedule.OperatorUtil;
import com.sopia.schedule.ScheduleUtil;
import com.sopia.schedule.dao.CustomReportsDao;
import com.sopia.schedule.dao.TagsDao;
import com.sopia.schedule.dao.impl.CustomReportsDaoImpl;
import com.sopia.schedule.dao.impl.TagsDaoImpl;
import com.sopia.schedule.entities.CustomReport;
import com.sopia.schedule.entities.CustomReportJSZ;
import com.sopia.schedule.entities.Tags;
/**
 * 加解析自定义报表标签
 * @author taomingke
 *
 */
public class CustomReportZDY extends TagSupport{
	private CustomReport customReport;
	private List<CustomReportJSZ> customReportJSZList;
	private Department department;
	private CustomReportsDao customReportsDao = new CustomReportsDaoImpl();
	private CustomLableDao customLableDao = new CustomLableDaoImpl();
	private TagsDao tagsDao = new TagsDaoImpl();
	private int pN;
	private int pS;
	private Map<String,String> orderColumnnameMap;//排序列
	private Map<String,String> searchMap;//搜索标签

	@SuppressWarnings("unchecked")
	public int doStartTag()
	{
		try
		{
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			
			customReport = (CustomReport)request.getAttribute("customReport");
			customReportJSZList = (List<CustomReportJSZ>)request.getAttribute("customReportJSZList");
			department = (Department)request.getAttribute("department");
			orderColumnnameMap = (Map<String,String>)request.getAttribute("orderColumnnameMap");
			searchMap = (Map<String,String>)request.getAttribute("searchMap");
			if (null != request.getAttribute("pN"))
				pN = (Integer) request.getAttribute("pN");
			if (null != request.getAttribute("pS"))
				pS = (Integer) request.getAttribute("pS");
			
			String sql = "";
			String[] sqlgroup_array = null; 
			if(customReport != null){
				String sqlgroup = customReport.getGroupby_();
				if(sqlgroup != null && !sqlgroup.equals("")){
					sqlgroup_array = sqlgroup.split(",");
				}
				
				sql = customReport.getSql();
				if(orderColumnnameMap != null ){//有排序列
					if(orderColumnnameMap.get("orderColumnname")!=null&&!orderColumnnameMap.get("orderColumnname").equals("")
							&&!orderColumnnameMap.get("orderColumnname").contains(".")){
						sql = ScheduleUtil.addOrderColumnnameFromsql(sql,orderColumnnameMap,1);
					}
				}
			}
			
			//得到查询字段信息
			String arr[] = ScheduleUtil.lablecommon_gettablefield(customReport.getTablefield().split("-"),sqlgroup_array);
			List<TableField>  list=customReportsDao.getTableFieldByField(arr);
			
			if(customReportJSZList != null && customReportJSZList.size()>0){
				//添加统计字段信息
				list = ScheduleUtil.lablecommon_addtongjifield(customReportJSZList,list);
			}
			
			//搜索标签
			//获取普通字段和统计字段的list、然后根据前台获取的参数参考list中有没有字段信息，有的话讲list中的value赋值为前台的value
			//searchMap
			
			//显示搜索
			if(customReport.getShowsearch()==1){
				writeChilds_searchhtml(out,customReport.getSearchhtml(),customReport.getSearchhtmlfield(),list);
			}
			
			List<Map<String,Object>> listMap =null;
			if(!sql.equals("")){
				if(department != null && department.getId()!=0){
					//将部门权限加入到sql中
					sql = ScheduleUtil.addSqlDepartment(sql,department,customReport.getTableinfo().split("-"));
				}
				//添加搜索条件	但是不处理带total操作符的统计字段
				if(searchMap!=null&&searchMap.size()>0){
					sql = ScheduleUtil.addSqlSearchCondition(sql,searchMap,list,customReport.getSearchtype());
				}

				//查出表内字段和值
				if(customReport.getPageSize() ==0){
					listMap=  customLableDao.getMap(list, sql,customReport);
				}else{
					listMap=  customReportsDao.getMap(list, sql,getPageNow(),getPageSize(),customReport);
				}
			}
			
			if(listMap != null && listMap.size()>0 && customReportJSZList != null && customReportJSZList.size()>0){
				
				
				//相关统计字段查询添加where
				if(searchMap!=null&&searchMap.size()>0){
					sql = ScheduleUtil.addsqlSearchConditionByRelateSearch(customReportJSZList,sql,searchMap,customReport.getTableinfo().split("-")[0]);
					if(customReport.getPageSize() ==0){
						listMap=  customLableDao.getMap(list, sql,customReport);
					}else{
						listMap=  customReportsDao.getMap(list, sql,getPageNow(),getPageSize(),customReport);
					}
				}
				listMap = this.addTotalOrRelate(listMap,customReportJSZList,customReport);
				
				
				//如果有TOTAL,重新查询数据库获取listMap
				if(listMap!=null&&listMap.size()>0){
					if(listMap.get(0) != null){
						String ids = "";
						for(CustomReportJSZ customReportJSZ:customReportJSZList){
							if(customReportJSZ.getType() == 1 && customReportJSZ.getRelatetype()==1){//表内统计字段
								//sum(GZJH_ZWPF)/total(GZJH_ZWPF)
								if((searchMap.get(customReportJSZ.getFormula())!=null&&!searchMap.get(customReportJSZ.getFormula()).equals(""))||
										(searchMap.get(customReportJSZ.getFormula()+"_")!=null&&!searchMap.get(customReportJSZ.getFormula()+"_").equals(""))){
									for(String key:listMap.get(0).keySet()){
										if(key.indexOf(OperatorUtil.TOTAL_OPERATOR.toUpperCase())!=-1){
											if(listMap.get(0).get(key)!=null&&!(String.valueOf(listMap.get(0).get(key))).equals("")&&customReportJSZ.getFormula()!=null&&!customReportJSZ.getFormula().equals("")){
												sql = new TagsDaoImpl().returnIds_total(customReport.getSql(),customReportJSZ, searchMap, customReport.getTableinfo().split("-")[0],listMap.get(0));
												
												if(customReport.getPageSize() ==0){
													listMap=  customLableDao.getMap(list, sql,customReport);
												}else{
													listMap=  customReportsDao.getMap(list, sql,getPageNow(),getPageSize(),customReport);
												}
												listMap = this.addTotalOrRelate(listMap,customReportJSZList,customReport);
											}
										}
									}
								}
							}
						}
						
						for(String key:listMap.get(0).keySet()){
							if(key.indexOf(OperatorUtil.TOTAL_OPERATOR.toUpperCase())!=-1){
								String formula = orderColumnnameMap.get("orderColumnname");
								String realordercolumn = "";
								if(formula != null && !formula.equals("")){//搜索列
									//将sum()/total()=>sum()/338.0
									int index = formula.indexOf(OperatorUtil.TOTAL_OPERATOR);
									if(index !=-1){
										String before = formula.substring(0,index);
										String end = formula.substring(index,formula.length());//total()
										//total()=>338
										end  = end.replace(end.substring(0,end.indexOf(")")+1), String.valueOf(listMap.get(0).get(key)));
										realordercolumn = before + end;
										orderColumnnameMap.put("orderColumnname", realordercolumn);
										
										sql = ScheduleUtil.addOrderColumnnameFromsql(sql,orderColumnnameMap,2);
										
										
										if(customReport.getPageSize() ==0){
											listMap=  customLableDao.getMap(list, sql,customReport);
										}else{
											listMap=  customReportsDao.getMap(list, sql,getPageNow(),getPageSize(),customReport);
										}
										listMap = this.addTotalOrRelate(listMap,customReportJSZList,customReport);
									}
								}
							}
						}
					}
				}
			}
			
			int page=0;
			int count=0;
			//获取数据count
			String sqlcount=LableCommon.lablecommon_pagegetcountsql(sql);
			if(customReport.getPageSize() !=0){
				count = customLableDao.lable_getsqlsagecount(sqlcount);
				if(count%customReport.getPageSize()==0){
					page=count/customReport.getPageSize();
				}else{
					page=count/customReport.getPageSize()+1;
				}
			}
			
			//显示列表
			writeChilds(out,listMap,customReport,count,page,customReportJSZList);
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return TagSupport.SKIP_BODY;
	}
	
	public List<Map<String,Object>> addTotalOrRelate(List<Map<String,Object>> listMap,List<CustomReportJSZ> customReportJSZList,CustomReport customReport) throws ElException{
		if(listMap!=null){
			for(Map map:listMap){
				int count = 0;
				double sum = 0;
				String value = "";
				for(CustomReportJSZ customReportJSZ:customReportJSZList){
					if(customReportJSZ.getFormula() != null && !customReportJSZ.getFormula().equals("")){//total(GZJH_ZWPF)=>TOTALGZJH.GZJH_ZWPF
						if(!customReportJSZ.getFormula().contains(".")){
							int index = customReportJSZ.getFormula().indexOf(OperatorUtil.TOTAL_OPERATOR);
							String formula = "";
							if(index!=-1){
								formula = OperatorUtil.getFormatFormula(customReportJSZ.getFormula(),OperatorUtil.TOTAL_OPERATOR);//sum(GZJH_ZWPF)
								double d = new TagsDaoImpl().getTotalValueByColumn(formula,OperatorUtil.getTablenameByFormula(customReportJSZ.getFormula(),1));
								map.put(OperatorUtil.replaceOperatorByTotal(formula,OperatorUtil.TOTAL_OPERATOR,1), d);//TOTALGZJH.GZJH_ZWPF
							}
						}else{//带相关的统计
							if(customReport.getGroupby_()==null||customReport.getGroupby_().equals("")){
								if(customReport.getTableinfo()!=null&&!customReport.getTableinfo().equals("")){
									String id = (String)map.get("id");
									if(customReportJSZ.getRelatetype() == 1){//统计count
										//XMDA.XMDA_KH
										String tablename = customReportJSZ.getFormula().substring(0,customReportJSZ.getFormula().indexOf("."));
										String columnname = customReportJSZ.getFormula().replace(tablename+".","");
										count = customReportsDao.queryCountByTableAndColumn(tablename,columnname,id);
										map.put(customReportJSZ.getFormula(), count);
									}else if(customReportJSZ.getRelatetype() == 2){//统计sum
										//SK.SK_SKJE
										String tablename = customReportJSZ.getFormula().substring(0,customReportJSZ.getFormula().indexOf("."));
										String columnname = customReportJSZ.getFormula().replace(tablename+".","");
										sum = customReportsDao.querySumByTableAndColumn(tablename,customReportJSZ.getRelatecolumnname(),columnname,id);
										map.put(customReportJSZ.getFormula(), sum);
									}else if(customReportJSZ.getRelatetype() == 3){//将统计字段再进行统计
									}
								}
							}
							if(customReport.getGroupby_()==null||customReport.getGroupby_().equals("")){
								if(customReport.getTableinfo()!=null&&!customReport.getTableinfo().equals("")){
									if(customReportJSZ.getRelatetype() == 3){//将统计字段再进行统计
										String formula = customReportJSZ.getFormula();
										//获取value
										for(Object key:map.keySet()){
											if(formula.indexOf(((String)key))!=-1){
												formula = formula.replace((String)key, String.valueOf(map.get((String)key)));
											}
										}
										value = JisuanzuUtil.computeString(formula);
										map.put(customReportJSZ.getFormula(), value);
									}
								}
							}
						}
					}
				}
			}
		}
		return listMap;
	}
	
	//输出搜索标签
	public void writeChilds_searchhtml(JspWriter out,String searchhtml,String searchhtmlfield,List<TableField>  fieldlist) throws IOException{
		/**
		 *  <tr>
				<TD vAlign=center align=middle width=120 bgColor=#ffffff rowSpan=100>
					<INPUT class=btn1_mouseout onMouseOver="this.className='btn1_mouseover'" onMouseOut="this.className='btn1_mouseout'" onclick=searchhtml(); type=button value=开始搜索>
				</TD>
			</tr>
			<tr>
			    <td align='center'>@searchlable#GZJH.GZJH_JHZQ#searchlable^</td>
			    <td align='center'>@searchlable#GZJH.GZJH_JHXZ#searchlable^</td>
			    <td align='center'>@searchlable#GZJH.GZJH_JHMC#searchlable^</td>
			</tr>
			<tr>
			    <td>@searchlable#GZJH_ZWPF+GZJH_LDPF+GZJH_BMPF#searchlable^</td>
			</tr>
		 */
		if(searchhtml != null && !searchhtml.equals("")){
			out.println("<table width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"5\">");
			out.println("<tbody onMouseOut=\"changeback()\" onMouseOver=\"changeto()\">");
			out.println("<input type='hidden' name='customReport.id' value="+customReport.getId()+" />");
			//将搜索标签解析成标准html
			searchhtml = ScheduleUtil.resolveSearchhtmlToHtml(searchhtml,searchhtmlfield,fieldlist,out);
			
			out.println(searchhtml);
			out.println("</tbody>");
			out.println("</table>");
		}
	}
	
	//输出列表
	public void writeChilds(JspWriter out, List<Map<String,Object>> obj,CustomReport customReport,int count,int page,List<CustomReportJSZ> customReportJSZList) throws Exception {
		out.println("<table width=\"100%\" align='center' cellpadding='1' cellspacing='1'>");
		List<Map<String,Object>> listMap  = obj;
		String biaoqianti=customReport.getLable();
		
		//表头
		out.println("<tr>");
		out.println(ScheduleUtil.formatHeaderByLable(biaoqianti));//表中的表头
		out.println(ScheduleUtil.lablecommon_getlable_jisuanze(customReportJSZList));//计算组的表头
		out.println("</tr>");
		
		out.println("<tbody onMouseOut='changeback()' onMouseOver='changeto()'>");
		if(listMap!=null){
				for (Map map : listMap) {
					out.println(ScheduleUtil.lablecommon_getlable(map,biaoqianti,customReportJSZList));//表中的值
				}
				out.println("</tbody>");
				out.println("</table>");
				if(count !=0){
					writePage(out,count,page);
				}
		}else{
			out.println("<div><center>暂无数据</center></div>");
		}
	}
	
	/**
	 * 分页标签
	 * @param out
	 * @param count
	 * @param pageCount
	 * @throws Exception
	 */
	public void writePage(JspWriter out,int count,int pageCount) throws Exception{
		out.println("<center>");
		out.print("<div style='margin-top:10px;'>");
		if (pN > 0) {
			out.print("<a style='cursor: hand' href='javascript:page("
					+ (0) + ")'>[首页]</a>");
			out.print("<a style='cursor: hand' href='javascript:page("
					+ (pN - 1) + ")'>[上一页]</a>");
		} else {
			out.print("[首页]");
			out.print("[上一页]");
		}
		if (pageCount > 0) {
			out
					.print("<select  onchange='page(this.options[this.selectedIndex].value)'>");
			for (int i = 0; i < pageCount; i++) {
				if(pN==i)
				out.println("<option value='" + i + "' selected='selected'>" + (i + 1)
						+ "</option>");
				else{
					out.println("<option value='" + i + "'>" + (i + 1)
							+ "</option>");
				}

			}
			out.println("</select> ");
		}
		if (pN < pageCount - 1) {
			out.print("<a style='cursor: hand' href='javascript:page("
					+ (pN + 1) + ")'>[下一页]</a>");
			out.print("<a style='cursor: hand' href='javascript:page("
					+ (pageCount - 1) + ")'>[末页]</a>");
		} else {
			out.print("[下一页]");
			out.print("[末页]");
		}
		out.print("<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>共</b>" + count
				+ "<b>条</b></span>");
		out.print("</div>");
		out.println("</center>");
	}
	
	public int getPageSize() {
		int pageend = 0;
		pageend = getPN() * getPS() + 1;
		return pageend;
	}
	
	public int getPageNow() {
		int pagebegin = 0;
		pagebegin = getPN() * getPS() + getPS();
		return pagebegin;
	}

	
	public CustomReport getCustomReport() {
		return customReport;
	}

	public void setCustomReport(CustomReport customReport) {
		this.customReport = customReport;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}


	public CustomReportsDao getCustomReportsDao() {
		return customReportsDao;
	}


	public void setCustomReportsDao(CustomReportsDao customReportsDao) {
		this.customReportsDao = customReportsDao;
	}

	public int getPN() {
		return pN;
	}

	public void setPN(int pn) {
		pN = pn;
	}

	public int getPS() {
		return pS;
	}

	public void setPS(int ps) {
		pS = ps;
	}

	public Map<String, String> getOrderColumnnameMap() {
		return orderColumnnameMap;
	}

	public void setOrderColumnnameMap(Map<String, String> orderColumnnameMap) {
		this.orderColumnnameMap = orderColumnnameMap;
	}

	public Map<String, String> getSearchMap() {
		return searchMap;
	}

	public void setSearchMap(Map<String, String> searchMap) {
		this.searchMap = searchMap;
	}






}
