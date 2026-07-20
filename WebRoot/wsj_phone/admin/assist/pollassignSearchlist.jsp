<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.assistman.entities.Poll"%>
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
		<TITLE>中国食品安全培训网--管理端--学员添加</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
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
	background-color: expression((     this .     sectionRowIndex %     2 ==     0)
		?  
		  "#ffffff" :     "#f4f4f4" )
}
</style>
		<script type="text/javascript" src="js/tree/dtree.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript">
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
		<script type="text/javascript">
			 	function page(i){
			 		acc_list.action=  "pollassignSearchlist.action";
			 		document.getElementById("pageNow").value=i;
			 		acc_list.submit();
			 	}
			 	function assignuser(){
			 		if(confirm("确定添加这些人员？")){
			 			acc_list.action=  "pollassign.action";
			 			acc_list.submit();
			 		}
			 	}
			 	function unassignuser(){
			 		if(confirm("确定移除这些人员？")){
			 			acc_list.action="pollunassign.action";
			 			acc_list.submit();
			 		}
			 	}
			 	function assignusers(){
			 		if(confirm("确定分配给所有人员？")){
			 			acc_list.action= "pollassignAll.action";
			 			acc_list.submit();
			 		}
			 	}
				function doForm(){
					$("#pageNow").val(0);
					acc_list.submit();
				}
			</script>
	</HEAD>
	<BODY>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="投票分配人员" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<h3 style="width: 100%; margin-top: 15px; text-align: center;">
			投票【
			<s:property value="poll.title" />
			】分配考生列表
		</h3>
		<s:form action="pollassignSearchlist" method="post"
			name="acc_list" theme="simple" id="acc_list">
			<s:hidden name="poll.id" />
			<s:hidden name="pN" id="pageNow" />
			<s:hidden name="pS" />
			<s:hidden name="department.id" />
			<div id="toUserInfo" style="display: block">
				<table align="center" cellpadding="1" cellspacing="1" width="1000">
					<tr>
						<td>
						</td>
						<td>
							<wysLib:BasetName btid="5" />
							：
							<s:select name="elUser.dishi" cssClass="g-select" list="dishis"
								listKey="id" listValue="basevalue" headerValue="全部"
								headerKey="0" />
						</td>
						<td>
							<wysLib:BasetName btid="3" />
							：
							<s:select name="elUser.zhiji" cssClass="g-select" list="zhijis"
								listKey="id" listValue="basevalue" headerValue="全部"
								headerKey="0" />
						</td>
						<td>
							<wysLib:BasetName btid="2" />
							：
							<s:select name="elUser.zhiwu" cssClass="g-select" list="zhiwus"
								listKey="id" listValue="basevalue" headerValue="全部"
								headerKey="0" />
						</td>
						<td>
							<wysLib:BasetName btid="1" />
							：
							<s:select name="elUser.jingzhong" cssClass="g-select"
								list="jingzhongs" listKey="id" listValue="basevalue"
								headerValue="全部" headerKey="0" />
						</td>
					<tr>
						<td>
							姓名：
							<input name="elUser.realname"
								value="<s:property value="elUser.realname"/>"
								id="elUser.realname">
						</td>
						<td>
							账号：
							<input name="elUser.username"
								value="<s:property value="elUser.username"/>"
								id="elUser.username">
						</td>
						<td>
							生日开始时间:
							<input type="text" size="16"
								value="<s:date format="yyyy-MM-dd" name="elUser.shengri"/>"
								name="elUser.shengri" onclick="setday(this)" readonly="readonly">
						</td>
						<td>
							生日结束时间:
							<input type="text" size="16"
								value="<s:date format="yyyy-MM-dd" name="elUser.shengri_end"/>"
								name="elUser.shengri_end" onclick="setday(this)"
								readonly="readonly">
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
							<input id="find" class="textbg4" name="find" type="button"
								onclick="doForm();" value="搜索">
							<%-- 点搜索后应该初始化分页 --%>
						</td>
					</tr>
				</table>
			</div>
			<%
					Poll poll=(Poll)request.getAttribute("poll");
					String x = "pollassignSearchlist.action?sub_department=1&poll.id="+poll.getId()+"&department.id=";
			%>
			<table align="center" cellpadding="1" cellspacing="1" width="1000">
				<tr>
					<td valign="top" width="200">
						<wysLib:dep_list_aj rootAble="true" href="<%=x %>"
							iname="department.id"></wysLib:dep_list_aj>
						<script type="text/javascript">
							w0.setValues([new DEP(<s:property value="department.id"/>,<s:property value="department.lid"/>,<s:property value="department.rid"/>)]);
						</script>
					</td>
					<td>
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
							</tr>
							<tbody onMouseOut="changeback()" onMouseOver="changeto()"
								id="data_list">
								<s:iterator value="elusers">
									<tr>
										<td width="20" height="20" align="center">
											<%-- 	<s:if test="joinwayInt==0||joinwayInt==2"> --%>
											<input type="checkbox" name="canAssignUsers.id"
												value="<s:property value="id"/>" />
											<%-- </s:if> --%>
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
											<s:if test="!introom">
												未分配
											</s:if>
											<s:else>
												<font color="red"> 已分配</font>
											</s:else>
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
			<a href="javascript:select_Bux()" class="textbg4" style="width:50px" />全不选</a>
			<div style="margin-top:5px;"></div>
			<a href="javascript:assignuser();" class="textbg6" />确认分配</a>
			<a href="javascript:unassignuser();" class="textbg6" />取消分配</a>
			<a href="javascript:assignusers();" class="textbg4" style="width:135px;" />分配给全部搜索结果</a>
			<br />
			<br />
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>