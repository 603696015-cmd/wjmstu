<%@ page language="java" pageEncoding="UTF-8"   %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath(); 
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<HTML>
	<HEAD>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>扬州专业技术人员继续教育网--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/menu.js"></script>
	<script type="text/javascript" src="js/cexampaper.js"></script> 
		<script type="text/javascript" src="js/libutil.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="培训班列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">可分配培训班</span>
			</li>-->
		</ul>
		<table width="1200">
			<tr>
				<td valign="top" id="tree_list_td">
					<wysLib:clTypeTree   href="elclass_assignlist2.action?sublibs=1&cltype.id=" rootAble="true" />
			  </td>
				<td valign="middle" width="5px;" style="padding: 0px">
					<img src="images/leftmenu/main_55.gif" style="cursor: hand" onClick="changeTreeDisplay(this)" />
				</td>
				<td valign="top">
					<s:form action="shopping_elclass_assignlist2" name="myclist" theme="simple">
						<s:hidden name="pN" id="pageNow"></s:hidden>
						<s:hidden name="pS"></s:hidden> 
						<s:hidden name="cltype.id"></s:hidden>
					<table width="100%" align="center" cellspacing="1" cellpadding="1">
						<tr>
							<td> 
								包含下级节点: 
								<input type="checkbox" name="sublibs" 
								<s:if test="sublibs==1">checked='checked'</s:if>
								 value="1">
							</td>
							<td>
								培训班名称:
								<input type="text" name="elClass.name"
									value="<s:property value="elClass.name"/>">
							</td>
							<td>
								时间段范围&nbsp;从
								<input type="text" onclick=setday(this)
									name="elClass.begintime"
									value="<s:date name="elClass.begintime" format="yyyy-MM-dd HH:mm"/>" readonly>
								&nbsp;到&nbsp;
								<input type="text" onclick=setday(this) name="elClass.endtime"
									value="<s:date name="elClass.endtime" format="yyyy-MM-dd HH:mm"/>" readonly>
							</td>
							<td> 
								状态:
								<s:select theme="simple"  headerValue="全部" headerKey="-1"
									list="#{0:'制作中',1:'申请等待中',2:'待修改',3:'审核等待中',4:'审核不通过',5:'已开通',6:'修改等待中',7:'修改中',8:'删除等待中',9:'已删除'}"
									name="elClass.status" value="elClass.status" />
							</td> 
							<td>
								<s:submit value="搜索"></s:submit>
							</td>
						</tr> 
					</table>  
					</s:form>

					<s:if test="elclasses.size==0">没有符合条件的培训班</s:if>
					<s:else>
						<table height="100%" align="center" cellpadding="1"
							cellspacing="1" > 
							<tr>
								<th width="200" align="center" >
									培训班名称				</th>
								<th width="60" align="center" >类型</th>
								<th width="100" align="center" >
									类别				</th>
								<th width="70" align="center" >
									创建者				</th>
								<th width="120" align="center" >
									课程数量				</th>
								<th width="120" align="center" >
									培训班价格				</th>	
								<th width="120" align="center" >
									创建时间				</th>
								<th width="120" align="center" >
									开始时间				</th>
								<th width="120" align="center" >
									结束时间				</th>
								<th width="100" align="center" >
									审核状态				</th>
								<th width="120" align="center" >
									已报(计划)人数				</th>
								<th width="100" align="center" >
									考场待操作				</th>
								<th width="300" align="center" >&nbsp;</th> 
							</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
							<s:iterator value="elclasses">
								<tr>
				<td style="padding-left:8px;color:blue;" width="200" align="left"> 
					
					<s:property value="name"/></td>
				<td width="60" align="center" ><s:if test="isApplication == 1">
						<SPAN style="color:red">申请</SPAN>					</s:if><s:else>
						<SPAN style="color:gray">分配</SPAN>
					</s:else></td>
				<td width="100" align="center" >
					<s:property value="cltype.name"/>				</td>
				<td width="70" align="center" >
					<s:property value="creater.realname" />				</td>
				<td width="70" align="center" >
					<s:property value="bxCount" />				</td>
				<td width="70" align="center" >
					<s:property value="price" />				</td>		
				<td width="120" align="center" > 
					<s:date format="yyyy-MM-dd hh:mm:ss" name="createtime"/>				</td>
				<td width="120" align="center" >
					<s:date format="yyyy-MM-dd hh:mm:ss" name="starttime"/> 
				<td width="120" align="center" >
					<s:date format="yyyy-MM-dd hh:mm:ss" name="finishtime"/>							  </td>
				<td width="100" align="center" style="color:green;">
					<s:property value="statusName"/>				</td>
				<td width="120" align="center" >
					<s:property value="classSize"/>	  
									<s:if test="isApplication == 1">
										<span style="color:red">(<s:property value="planNumber"/>)</span>									</s:if>				</td>
									<td width="100" align="center" >
										<s:if test="operation == 1">
											<span style="color:green">全开通</span>										</s:if>
										<s:if test="operation == 2">
											<span style="color:red">有待操作</span>										</s:if>
										<s:if test="operation == 3">
											无待操作										</s:if>								  </td>
              					  <td width="300" align="left" >
											<a href="elclass_details_sh.action?elclassId=<s:property value="id" />&PageStatus=0&PageStatusint=2" class=textbg4>详情</a>
											<a href="shopping_elclass_check_students.action?sub_department=1&elclass.id=<s:property value="id"/>&elUser.isAssign=0" class="textbg6">待订学员</a>
											<a href="shopping_elclass_assign2userInit.action?sub_department=1&elclass.id=<s:property value="id" />" class="textbg4">分配</a>	
							        <s:if test="status == 0 || status == 2"> 
													
											<!-- <a href="elclass_assign2depInit.action?elclass.id=<s:property value="id" />" class="textbg">分配部门</a> -->
											<s:if test="isUvalid == 'false'">
												<a style="cursor:pointer;"  onClick="sh(<s:property value="id"/>, 1);" class="textbg6">创建完成</a>											</s:if> 
									</s:if>								</td>
								</tr>
							</s:iterator></tbody>
					  </table>
					</s:else>
				</td>
			</tr>
	</table>
							<form action="elclass_sh.action" name="elclass_sh" method="post">
								<s:hidden name="elclass.id" id="elclass.id"></s:hidden>
								<s:hidden name="status" id="status"></s:hidden>
								<s:hidden name="Return" id="Return" value="elclass_assignlist2"></s:hidden>
							</form>  
		<wysLib:page></wysLib:page> 
							<script>   
								function sh(id,status){
								    document.getElementById("elclass.id").value=id;
								    document.getElementById("status").value=status; 
								 	if(status==1 && window.confirm("确定创建完成？")){
								 		document.forms.elclass_sh.submit();
								 	} 
								}   
								function page(i) {
									document.getElementById("pageNow").value=i;
									myclist.submit();
								} 
							</script> 
	
	</body>
</HTML>
