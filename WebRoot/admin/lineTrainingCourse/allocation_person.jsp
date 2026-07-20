<%@ page language="java" pageEncoding="UTF-8"%>
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
		<TITLE>五矿发展员工职业发展系统--管理端--学员添加</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/pageutil.js"></script>
		<link rel="StyleSheet" href="js/tree/dtree.css" type="text/css" />
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
</style>
		<script type="text/javascript" src="js/tree/dtree.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript">
			function myload(){
				if("${elmessage}" != ""){
					alert("${elmessage}");
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
		</script>
	</HEAD>
	<body onLoad="myload();">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="分配人员" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		

		<s:form action="allocation_person.action" method="post"
			name="acc_list" theme="simple" id="acc_list">
			<input type="hidden" name="lineTrainingCourse.id" value="<s:property value='lineTrainingCourse.id'/>"/>
			<s:hidden name="pN" id="pageNow" />
			<s:hidden name="pS" />
			<s:hidden name="optype" />
			<s:hidden name="department.id" />
			<!-- <input type="radio" name="DBMethods" value="0" id="DBM"
				onclick="DistributionMethods(0)"
				<s:if test="DBMethods == 0">checked="checked"</s:if> /> 按用户信息查询
			<input type="radio" name="DBMethods" value="1" id="DBM"
				onclick="DistributionMethods(1)"
				<s:if test="DBMethods == 1">checked="checked"</s:if> /> 按培训班成绩查询
			<input type="radio" name="DBMethods" value="2" id="DBM"
				onclick="DistributionMethods(2)"
				<s:if test="DBMethods == 2">checked="checked"</s:if> /> 按考场成绩查询
			<input type="radio" name="DBMethods" value="3" id="DBM"
				onclick="DistributionMethods(3)"
				<s:if test="DBMethods == 3">checked="checked"</s:if> /> 按考场试卷成绩查询
			<a style="margin-left:300px" href="javascript:eroomEpWriteUser('<s:property value="examRoom.id" />','<s:property value="examPaper.id" />');" class="textbg6">学员导入</a> -->
			<div id="toUserInfo" style="display: block">
				<table align="center" cellpadding="1" cellspacing="1" width="1000">
					<tr>
						<td>
							<%-- 
						<wysLib:BasetName btid="4" />： 
								<s:select name="elUser.gangwei" cssClass="g-select"
										list="gangweis" listKey="id" key="2" listValue="basevalue"  headerValue="全部" headerKey="0" />
						 --%>
						</td>
					<tr>
						<td>
							姓名：
							<input name="elUser.realname"
								value="<s:property value="elUser.realname"/>"
								id="elUser.realname">
						</td>
						<td>
							用户名：
							<input name="elUser.username"
								value="<s:property value="elUser.username"/>"
								id="elUser.username">
						</td>
						<td>
							性别：
							<select name="elUser.sex">
								<option value="" selected="selected">
									全部
								</option>
								<option value="男"
									<s:if test="elUser.sex==\"男\"">selected='selected'</s:if>>
									男
								</option>
								<option value="女"
									<s:if test="elUser.sex==\"女\"">selected='selected'</s:if>>
									女
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							包含下级节点：
							<input type="checkbox" name="sub_department"
								<s:if test="sub_department==1">checked="checked"</s:if>
								id="sub_department" value="1" />

						</td>
						<td>
						</td>
						<td>
						</td>
						<td>
						</td>
						<td>
							<input id="find" name="find" type="submit" value="搜索"><%-- 点搜索后应该初始化分页 --%>
						</td>
					</tr>
				</table>
			</div>
			<table align="center" cellpadding="1" cellspacing="1" width="1000" >
				<tr>
					<td valign="top" width="200">
						<wysLib:dep_list_aj rootAble="true" href="allocation_person.action?sub_department=1&lineTrainingCourse.id=${lineTrainingCourse.id}&department.id="
							iname="department.id"></wysLib:dep_list_aj>
					</td>
					<td>
						<table align="center" cellpadding="1" cellspacing="1" width="800">
							<tr>
								<th width="20">
								</th>
								<th>
									学号<s:property value="lineTrainingCourse.id"/>
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
									年龄
								</th>
								<th>
									分配
								</th>
								<th>
									来源
								</th>
							</tr>
							<tbody onMouseOut="changeback()" onMouseOver="changeto()" id="data_list">
								<s:iterator value="elUsers" status="status">
									<tr>
										<td width="20" height="20" align="center">
											<input type="checkbox" name="id" value="<s:property value="id"/>" />
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
											<s:property value="age" />
										</td>
										<td height="20" align="center">
											<s:if test="!introom">
											未分配
										</s:if>
											<s:else>
												<font color="red"> 已分配</font>
											</s:else>
										</td>
										<td height="20" align="center">
											<s:if test="joinwayInt == 0">
												分配
											</s:if>
											<s:if test="joinwayInt == 1">
												报名
											</s:if>
											<s:if test="joinwayInt == 2">
												--
											</s:if>
										</td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
					</td>
				</tr>
			</table>
		</s:form>
		<div style="margin-bottom:20px;text-align:center;">
			<div id="page_div">
			<wysLib:page></wysLib:page>
			</div>
			<a href="javascript:select_All()" />全选</a>
			<a href="javascript:select_Fan()" />反选</a>
			<a href="javascript:select_Bux()" />全不选</a>
			<br>
			<input value="添加到当前培训班中" type="button" onClick="add_to_lineTrainigCourseAssign('notAll');">
			<input value="移除出当前培训班" type="button" onClick="remove_from_lineTrainingCourseAssign();">
			<input value="返回我发布的培训列表" type="button"
				onclick="javascript:history.go(-1);">
			<input value="分配给全部搜索结果" type="button" onClick="add_to_lineTrainigCourseAssign('all');" />
			<br>
			<form action="option_in_allocation.action" method="post" name="option_in_allocation">
				<input type="hidden" name="lineTrainingCourse.id" value="<s:property value='lineTrainingCourse.id'/>"/>
				<input type="hidden" name="userIds" id="userIds"/>
				<input type="hidden" name="option" id="option"/>
				<input type="hidden" name="sub_department" value="<s:property value='sub_department'/>"/>
			</form>
			<script type="text/javascript" >
				//添加到当前培训班中
				function add_to_lineTrainigCourseAssign(type){
					if(confirm("确定添加这些人员？")){
						var checkObj = document.getElementsByName("id");
					    var billIDs = "";
					    if(type == 'all'){
					    	for (i = 0; i < checkObj.length; i++) {
					    		if(i == checkObj.length-1){
					    			billIDs += checkObj[i].value;
					    		}else{
					    			billIDs += checkObj[i].value + ",";
					    		}
							}
							
							var userIds = document.getElementById("userIds");
					    	document.getElementById("option").value="add";
					    	
					    	//判断当前分配的人员中有无已经分配的
					    	var returnValue = check_userIds(type,'<s:property value='lineTrainingCourse.id'/>',billIDs);
					    	if(returnValue != ""){
					    		if(billIDs != returnValue){
						    		alert("您要分配的学员中包含已经分配的学员,请重新选择!");
						    		return ;
						    	}
					    	}else{
					    		alert("您要分配的学员中包含已经分配的学员,请重新选择!");
					    		return ;
					    	}
						    	
					    	userIds.value = billIDs;
					    	option_in_allocation.submit();
					    }else{
					    	for (i = 0; i < checkObj.length; i++) {
								if (checkObj[i].checked) {
								    if(billIDs!="")billIDs+=",";
									billIDs += checkObj[i].value;
								}
							 }
							if(billIDs==""){
							  alert("请至少选择一个复选框！");
							  return ;
						    }else{
						    	var userIds = document.getElementById("userIds");
						    	
						    	//判断当前分配的人员中有无已经分配的
						    	var returnValue = check_userIds(type,'<s:property value='lineTrainingCourse.id'/>',billIDs);
						    	if(returnValue != ""){
						    		if(billIDs != returnValue){
							    		alert("您要分配的学员中包含已经分配的学员,请重新选择!");
							    		return ;
							    	}
						    	}else{
						    		alert("您要分配的学员中包含已经分配的学员,请重新选择!");
						    		return ;
						    	}
						    	
						    	document.getElementById("option").value="add";
						    	userIds.value = billIDs;
						    	option_in_allocation.submit();
						    }
					    }
			 		}
				}
				
				function check_userIds(type,line_training_course_id,userIds){
					var returnValue = "";
					$.ajax({
						  type: 'POST',
						  url: "check_userIds.action",
						  data: {line_training_course_id:line_training_course_id,type:type,userIds:userIds},
						  async:false,//同步
						  success: function(data){
						  	if(eval("("+data+")").message != "0"){
						  		returnValue = eval("("+data+")").message;
						  	}else{
						  		returnValue = "";
						  	}
						  }
					});
					userIds = returnValue;
					return userIds;
				}
				
				function remove_from_lineTrainingCourseAssign(){
					if(confirm("确定移除这些人员？")){
						var checkObj = document.getElementsByName("id");
					    var billIDs = "";
					    for (i = 0; i < checkObj.length; i++) {
							if (checkObj[i].checked) {
							    if(billIDs!="")billIDs+=",";
								billIDs += checkObj[i].value;
							}
						 }
						if(billIDs==""){
						  alert("请至少选择一个复选框！");
						  return ;
					    }else{
					    	var userIds = document.getElementById("userIds");
					    	document.getElementById("option").value="remove";
					    	userIds.value = billIDs;
					    	
					    	
					    	option_in_allocation.submit();
					    }
			 		}
				}
			</script>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
