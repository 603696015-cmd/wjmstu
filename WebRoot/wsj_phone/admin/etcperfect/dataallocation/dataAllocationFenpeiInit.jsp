<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.duman.entities.Department"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<TITLE>分配人员</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/tree/dtree.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript">
			function doForm(){
				document.getElementById("pageNow").value=0;
				acc_list.submit();
			}
			function load_(){
				if('${elmessage}' !=""){
					alert('${elmessage}');
				}
			}
			function page(i){ 
		 		document.getElementById("pageNow").value=i;
		 		acc_list.submit();
		 	}
		 	
		 	function select_All(){
				var cks= document.getElementsByName("canAssignUsers.id");
				for(var i = 0 ; i < cks.length; i++){
					cks[i].checked= true;
				}
			}
			function select_Fan(){
				var cks= document.getElementsByName("canAssignUsers.id");
				for(var i = 0 ; i < cks.length; i++){
					cks[i].checked= !cks[i].checked;
				}
			}
			function select_Bux(){
				var cks= document.getElementsByName("canAssignUsers.id");
				for(var i = 0 ; i < cks.length; i++){
					cks[i].checked= false;
				}
			}
			
			function addLearnTime(){
				 width=600;
				 height=400;
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv =  window.showModalDialog("addLearnTimeInit.action?x="+Math.random(),null,sFeature);
				 if(rv!=undefined&&rv!=""){
					 var bh=rv.split("==");
					 document.getElementById("dataAllocation.begintime").value=bh[0];
					 document.getElementById("dataAllocation.endtime").value=bh[1];
				 }
			}
			
			function assign2user(type){
				if(confirm("确认分配?")){
					var checkObj= document.getElementsByName("canAssignUsers.id");
					var billIDs = "";
				    for (i = 0; i < checkObj.length; i++) {
				    	if(type == 0){//分配
				    		if (checkObj[i].checked) {
							    if(billIDs!="")billIDs+=",";
								billIDs += checkObj[i].value;
							}
				    	}else if(type == 1){//分配给全部
						    if(billIDs!="")billIDs+=",";
							billIDs += checkObj[i].value;
				    	}
					 }
					if(billIDs==""){
					  alert("请至少选择一个复选框！");
					  return ;
				    }
				    //填写开始时间和结束时间
				    addLearnTime();
				    
				    if(!checkForm()){
				    	alert("开始时间和结束实际不能为空,请填写!!!");
				    	return;
				    }
					hh.action=  "dataAllocationFenpei.action";
					document.getElementById("userids").value = billIDs;
					hh.submit();
				}
			}
			
			function unassign2user(type){
				if(confirm("确认取消分配?")){
					var checkObj= document.getElementsByName("canAssignUsers.id");
					var billIDs = "";
				    for (i = 0; i < checkObj.length; i++) {
				    	if(type == 0){//取消分配
				    		if (checkObj[i].checked) {
							    if(billIDs!="")billIDs+=",";
								billIDs += checkObj[i].value;
							}
				    	}else if(type == 1){//取消分配给全部
						    if(billIDs!="")billIDs+=",";
							billIDs += checkObj[i].value;
				    	}
					 }
					if(billIDs==""){
					  alert("请至少选择一个复选框！");
					  return ;
				    }
					hh.action=  "dataAllocationUnFenpei.action";
					document.getElementById("userids").value = billIDs;
					hh.submit();
				}
			}
			
			function audit(type){
				if(confirm("确认审核通过?")){
					var checkObj= document.getElementsByName("canAssignUsers.id");
					var billIDs = "";
				    for (i = 0; i < checkObj.length; i++) {
				    	if(type == 0){
				    		if (checkObj[i].checked) {
							    if(billIDs!="")billIDs+=",";
								billIDs += checkObj[i].value;
							}
				    	}else if(type == 1){
						    if(billIDs!="")billIDs+=",";
							billIDs += checkObj[i].value;
				    	}
					 }
					if(billIDs==""){
					  alert("请至少选择一个复选框！");
					  return ;
				    }
				    //填写开始时间和结束时间
				    addLearnTime();
				    
				    if(!checkForm()){
				    	alert("开始时间和结束实际不能为空,请填写!!!");
				    	return;
				    }
					hh.action=  "dataAllocationAudit.action";
					document.getElementById("userids").value = billIDs;
					hh.submit();
				}
			}
			
			function unaudit(type){
				if(confirm("确认审核不通过?")){
					var checkObj= document.getElementsByName("canAssignUsers.id");
					var billIDs = "";
				    for (i = 0; i < checkObj.length; i++) {
				    	if(type == 0){
				    		if (checkObj[i].checked) {
							    if(billIDs!="")billIDs+=",";
								billIDs += checkObj[i].value;
							}
				    	}else if(type == 1){
						    if(billIDs!="")billIDs+=",";
							billIDs += checkObj[i].value;
				    	}
					 }
					if(billIDs==""){
					  alert("请至少选择一个复选框！");
					  return ;
				    }
					hh.action=  "dataAllocationUnAudit.action";
					document.getElementById("userids").value = billIDs;
					hh.submit();
				}
			}
			
			function checkForm(){
				if(document.getElementById("dataAllocation.begintime").value == "" || document.getElementById("dataAllocation.endtime").value == ""){
					return false;
				}else{
					return true;
				}
			}
		</script>
		<link rel="StyleSheet" href="js/tree/dtree.css" type="text/css" />
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((     this .     sectionRowIndex %     2 ==     0)
		?  
		  "#ffffff" :     "#f4f4f4" )
}
</style>
	</HEAD>
	<BODY onLoad="load_();">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="分配用户" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		
		<form action="dataAllocationFenpei.action"  method="post" name="hh" >
			<s:hidden name="id"></s:hidden>
			<s:hidden name="tablename"></s:hidden>
			<s:hidden name="moduleManage.id"></s:hidden>
			<input type="hidden" name="userids" id="userids" />
			<input type="hidden" name="dataAllocation.begintime" id="dataAllocation.begintime" value="" onClick="setday(this)">
			<input type="hidden" name="dataAllocation.endtime" id="dataAllocation.endtime" value="" onClick="setday(this)">
		</form>

		<s:form action="dataAllocationFenpeiInit.action" method="post"
			name="acc_list" theme="simple" id="acc_list">
			<s:hidden name="pN" id="pageNow"  />
			<s:hidden name="pS" />
			<s:hidden name="department.id" />
			<s:hidden name="tablename" />
			<s:hidden name="id" />
			<div id="toUserInfo" style="display: block">
				<table align="center" cellpadding="1" cellspacing="1" width="1000">
					<tr>
						<td>
							<wysLib:BasetName btid="5" />
							：
							<s:select name="eluser.dishi" cssClass="g-select" list="dishis"
								listKey="id" listValue="basevalue" headerValue="全部"
								headerKey="0" />
						</td>
						<td>
							<wysLib:BasetName btid="3" />
							：
							<s:select name="eluser.zhiji" cssClass="g-select" list="zhijis"
								listKey="id" listValue="basevalue" headerValue="全部"
								headerKey="0" />
						</td>
						<td>
							<wysLib:BasetName btid="2" />
							：
							<s:select name="eluser.zhiwu" cssClass="g-select" list="zhiwus"
								listKey="id" listValue="basevalue" headerValue="全部"
								headerKey="0" />
						</td>
						<td>
							<wysLib:BasetName btid="1" />
							：
							<s:select name="eluser.jingzhong" cssClass="g-select"
								list="jingzhongs" listKey="id" listValue="basevalue"
								headerValue="全部" headerKey="0" />
						</td>
						<td></td>
					<tr>
						<td>
							姓名：
							<input name="eluser.realname"
								value="<s:property value="eluser.realname"/>"
								id="eluser.realname">
						</td>
						<td>
							账号：
							<input name="eluser.username"
								value="<s:property value="eluser.username"/>"
								id="eluser.username">
						</td>
						<td>
							生日开始时间:
							<input type="text" size="16"
								value="<s:date format="yyyy-MM-dd" name="eluser.shengri"/>"
								name="eluser.shengri" onclick="setday(this)" readonly="readonly">
						</td>
						<td>
							生日结束时间:
							<input type="text" size="16"
								value="<s:date format="yyyy-MM-dd" name="eluser.shengri_end"/>"
								name="eluser.shengri_end" onclick="setday(this)"
								readonly="readonly">
						</td>
						<td></td>
					</tr>
					<tr>
						<td>
							包含下级节点：
							<input type="checkbox" name="sub_department"
								<s:if test="sub_department==1">checked="checked"</s:if>
								id="sub_department" value="1" />

						</td>
						<td>
							分配状态：
							<select name="eluser.isAllocated">
								<option value="3" selected="selected">
									全部
								</option>
								<option value="1"
									<s:if test="eluser.isAllocated==1">selected='selected'</s:if>>
									已分配
								</option>
								<option value="0"
									<s:if test="eluser.isAllocated==0">selected='selected'</s:if>>
									未分配
								</option>
							</select>
						</td>
						<td>
							审核状态：
							<select name="eluser.isApplicated">
								<option value="0" selected="selected">
									全部
								</option>
								<option value="2"
									<s:if test="eluser.isAllocated==2">selected='selected'</s:if>>
									已审核
								</option>
								<option value="1"
									<s:if test="eluser.isAllocated==4">selected='selected'</s:if>>
									未审核
								</option>
							</select>
						</td>
						<td>
							性别：
							<select name="eluser.sex">
								<option value="" selected="selected">
									全部
								</option>
								<option value="男"
									<s:if test="eluser.sex==\"男\"">selected='selected'</s:if>>
									男
								</option>
								<option value="女"
									<s:if test="eluser.sex==\"女\"">selected='selected'</s:if>>
									女
								</option>
							</select>
						</td>
						<td >
							<input id="find" class="textbg4" name="find" type="button"
								onclick="doForm();" value="搜索">
						</td>
					</tr>
				</table>
			</div>
			
			<table align="center" cellpadding="1" cellspacing="1" width="1000">
				<tr>
					<td valign="top" width="200">
						<%
								Department dep = (Department) request
											.getAttribute("department");
									String depid = dep.getId() + "";
								String tablename=(String)request.getAttribute("tablename");
								int sub_department = (Integer)request.getAttribute("sub_department");
								int id = (Integer)request.getAttribute("id");
								String url ="dataAllocationFenpeiInit.action?tablename="+tablename+"&id="+id+"&sub_department="+sub_department+"&department.id=";
							%>
						<wysLib:dep_list_aj rootAble="true" href="<%=url %>"
							iname="department.id"></wysLib:dep_list_aj>
						<script type="text/javascript">
							w0.setValues([new DEP(<s:property value="department.id"/>,<s:property value="department.lid"/>,<s:property value="department.rid"/>)]);
						</script>
					</td>
					<td valign="top">
						<table align="center" cellpadding="1" cellspacing="1" width="800">
							<tr>
								<th width="20">
								</th>
								<th>
									学号
								</th>
								<th>
									姓名
								</th>
								<th>
									部门
								</th>
								<th>
									角色
								</th>
								<th>
									性别
								</th>
								<th>
									工种
								</th>
								<th>
									年龄
								</th>
								<th>
									分配
								</th>
								<th>
									参加方式
								</th>
								<th>
									审核状态
								</th>
								<th>
									开始时间
								</th>
								<th>
									结束时间
								</th>
							</tr>
							<tbody onMouseOut="changeback()" onMouseOver="changeto()"
								id="data_list">
								<s:iterator value="elusers">
									<tr>
										<td width="20" height="20" align="center">
											<input type="checkbox" name="canAssignUsers.id"
												value="<s:property value="id"/>" />
										</td>
										<td height="30" align="center">
											<s:property value="username" />
										</td>
										<td height="20" align="center">
											<s:property value="realname" />
										</td>
										<td height="20" align="center">
											<s:property value="department.name" />
										</td>
										<td height="20" align="center">
											<s:property value="role.name" />
										</td>
										<td height="20" align="center">
											<s:property value="sex" />
										</td>
										<td height="20" align="center">
											<s:property value="jingzhong_" />
										</td>
										<td height="20" align="center">
											<s:property value="age" />
										</td>
										<td height="20" align="center">
											<s:if test="dataAllocation.allocationtype == 1">
												<span style="color:red">已分配</span>
											</s:if>
											<s:elseif test="dataAllocation.allocationtype == 2">
												<span style="color:red">已申请</span>
											</s:elseif>
											<s:else>
												未分配 未申请
											</s:else>
										</td>
										<td height="20" align="center">
											<s:if test="dataAllocation.allocationtype == 1">
												分配
											</s:if>
											<s:if test="dataAllocation.allocationtype == 2">
												申请
											</s:if>
										</td>
										<td height="20" align="center">
											<s:if test="dataAllocation.status == 1">
												已分配
											</s:if>
											<s:elseif test="dataAllocation.status == 2">
												<span style='color:red'>已审核</span>
											</s:elseif>
											<s:elseif test="dataAllocation.status == 3">
												未通过
											</s:elseif>
											<s:elseif test="dataAllocation.status == 4">
												未申请
											</s:elseif>
											<s:elseif test="dataAllocation.status == 5">
												审核中
											</s:elseif>
											<s:else>
												未审核
											</s:else>
										</td>
										<td height="20" align="center">
											<s:date name="dataAllocation.begintime" format="yyyy-MM-dd" />
										</td>
										<td height="20" align="center">
											<s:date name="dataAllocation.endtime" format="yyyy-MM-dd" />
										</td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
					</td>
				</tr>
			</table>
		</s:form>
		<div style="margin-bottom: 20px; text-align: center;">
			<div id="page_div">
				<wysLib:page></wysLib:page>
			</div>
			
			<a href="javascript:select_All()" class="textbg4" />全选</a>
			<a href="javascript:select_Fan()" class="textbg4" />反选</a>
			<a href="javascript:select_Bux()" class="textbg4" style="width: 50px" />全不选</a>
			<br />
			
			<input class="textbg" style="border: none; color: red"
				title="勾选上述列表中人员添加数据分配" value="分配" type="button"
				onClick="assign2user(0)">
			 
			<input class="textbg" style="border: none;; color: red" value="分配给全部搜索结果"
				title="将上述的搜索条件搜索出的人员添加数据分配" type="button"
				onClick="assign2users(1)" />
			
			<input class="textbg" style="border: none;; color: red"
				title="勾选上述列表中人员移出数据分配" value="取消分配" type="button"
				onClick="unassign2user(0)">
			<input class="textbg" style="border: none;; color: red"
				title="勾选上述列表中人员移出数据分配" value="取消全部分配" type="button"
				onClick="unassign2user(1)">
			<input class="textbg" style="border: none;; color: red"
				title="勾选上述列表中人员数据分配审核通过" value="审核通过" type="button"
				onClick="audit(0)">
			<input class="textbg" style="border: none;; color: red"
				title="将上述的搜索条件搜索出的人员数据分配审核通过" value="通过全部未审核" type="button"
				onClick="audit(1)">
			<input class="textbg" style="border: none;; color: red"
				title="勾选上述列表中人员数据分配审核不通过" value="审核不通过" type="button"
				onClick="unaudit(0)">
			<input class="textbg" style="border: none;; color: red"
				title="将上述的搜索条件搜索出的人员数据分配审核不通过" value="不通过全部已审核" type="button"
				onClick="unaudit(1)">
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
