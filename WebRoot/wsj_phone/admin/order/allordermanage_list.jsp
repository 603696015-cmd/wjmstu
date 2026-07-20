<%@ page language="java" pageEncoding="UTF-8"%>
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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<TITLE>我的图书管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
		
		</script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>

		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="所有订单列表" /></div>
			</li>
			<!--<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="news_addInit.action">新闻公告添加</a>

			</li>-->
		</ul>

		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<table width="100%">
				<tr>
				<td valign="top" width="120" id="tree_list_td">

							<wysLib:dep_list_aj rootAble="true" href="order_allorderlistinit.action?deptid="></wysLib:dep_list_aj>
							<script type="text/javascript">
								w0.setValues([new DEP(<s:property value="department.id"/>,<s:property value="department.lid"/>,<s:property value="department.rid"/>)]);
							</script>
						</td>
				<td valign="top">
		<s:form action="order_allorderlistinit" method="post" name="assignSearch_assignment" theme="simple">	
			<table width="100%"> 
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<tr>
				
				   <td>  订单号：</td>
				     <td><s:textfield name="orderid" theme="simple"/></td>
				      <td>订单状态 
				         <!-- <select name="corder.sstatus">
				               <option></option>
				               <option value="0">已提交</option>
				               <option value="3">已支付</option>
				           </select> -->  
				           <s:select theme="simple"  headerValue="全部" headerKey="1"
									list="#{0:'已订购',2:'已支付',3:'已发货',4:'已收货'}"
									name="orderstatus" value="orderstatus" theme="simple"/></td>
									<td>订购者姓名</td>
									<td><s:textfield name="elUser.realname" theme="simple"/>
				           </td>
				 
				    <td>是否包含下级部门：<label>
									<input type="checkbox" name="sub_department"
										<s:if test="sub_department==1">checked="checked"</s:if>
										id="sub_department" value="1">
								</label></td>
									<td>角色：</td>
									<td><s:select  name="elUser.role.id" list="roles" listKey="id" listValue="name" theme="simple" headerKey="0"
					 headerValue="请选择" /></td>
				</tr>
				<tr>
				   <td>订购时间  开始时间:</td>
				     <td> 
					<input name='start'
						value="<s:date name="start" format="yyyy-MM-dd" />"
						onclick='setday(this)' readonly="readonly"/>
					 </td>
				       <td>结束时间:</td>
				         <td>
				         	<input name='end'value="<s:date name="end" format="yyyy-MM-dd"/>" onclick='setday(this)' readonly="readonly"/>
						</td>
						<td>订购者账号</td>
									<td><s:textfield name="elUser.username" theme="simple"/></td>
				         <td colspan="2">
				           	 <input id="find" name="find" type="button" value="搜索" onClick="newsSubmit();" >
				         </td>
				</tr>
			</table>
			</s:form>
						<s:if test="listo.size==0"><h3 align="center" style="margin-top:10px;">没有订单信息</h3></s:if>
						<s:else>
							<table width="100%" align="center" cellpadding="2"
								cellspacing="2" bgcolor="#EBEBEB">
								<tr>
									<th width="260" height="30" align="center" >
										订单号									</th>
									<th width="80" height="30" align="center" >
										订购时间									</th>
										<th width="100" height="30" align="center" >
										订购者姓名									</th>
											<th width="100" height="30" align="center" >
										订购者账号									</th>
											<th width="100" height="30" align="center" >
										部门名称									</th>
											<th width="100" height="30" align="center" >
										角色									</th>
									<th width="90" height="30" align="center" >
										订单总价									</th>
									<th width="80" height="30" align="center" >
										支付时间								</th>
									<th width="70" height="30" align="center" >
										状态									</th>
									<th width="70" height="30" align="center" >
										详情								</th>
								</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
								<s:iterator value="listo">
									<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
											<s:property value="id" />
									  </td>
										<td width="80" height="30" align="center" >
									  <s:date name="orderdate" format="yyyy-MM-dd HH:mm" />									  </td>
										<td height="30" align="center" >
											
											<s:property value="user.realname" />
										</td>
										<td height="30" align="center" >
											
											<s:property value="user.username" />
										</td>
										<td height="30" align="center" >
											
											<s:property value="user.department.name" />
										</td>
										<td height="30" align="center" >
											
											<s:property value="user.role.name" />
										</td>
										<td height="30" align="center" >
											
											<s:property value="sumpeice" />
										</td>
										<td width="80" height="30" align="center" >
											<s:date name="buydate" format="yyyy-MM-dd HH:mm" />
									  </td>
										<td width="70" height="30" align="center" >
											<s:property value="Statusname" />
									  </td>
										<td width="70" height="30" align="center" >
											<a style="cursor:pointer;"  href="order_ordercommodity.action?orderid=<s:property value="id"/>&statusflag=1"  class="textbg4">查看</a>
											<s:if test="status==0" > 
											<a style="cursor:pointer;"  onClick="sh(<s:property value="id"/>);"  class="textbg4">删除</a> 
											</s:if>
									  </td>
									</tr>
								</s:iterator></tbody>
						  </table>
						</s:else>
					</td>
				</tr>
			</table>
			<%-- 
			<form action="newsManage_list.action" method="post" name="nlist">
				<s:hidden name="ntype.id" />
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
			</form>
			 --%>
			<script type="text/javascript">
			function page(i){
				document.getElementById("pageNow1").value=i;
				assignUser.submit();
			}
			function newsSubmit(){
				document.getElementById("pageNow").value=0;
				assignSearch_assignment.submit();
			}
			function sh(id){
									if(window.confirm('确定删除？')){
									    document.getElementById("btype.id").value=id;
									    document.getElementById("dstatus").value=1; 
									 	assignSearch_assignment.action="bookinfo_dele.action";
									 	assignSearch_assignment.submit();
								 	}
								} 
		</script>
			<wysLib:page></wysLib:page>
				<s:form action="order_allorderlistinit" method="post" name="assignUser">
				<s:hidden name="pN" id="pageNow1" />
				<s:hidden name="pS" />
				<s:hidden name="deptid" />
				<s:hidden name="sub_department" />
				<s:hidden name="elUser.username" />
				<s:hidden name="elUser.realname" />
				<s:hidden name="elUser.role.id" />
				<s:hidden name="elUser.role.id" />
				<s:hidden name="orderid" />
				<s:hidden name="orderstatus" />
			</s:form>
		</div>
	
	</body>
</HTML>
										   