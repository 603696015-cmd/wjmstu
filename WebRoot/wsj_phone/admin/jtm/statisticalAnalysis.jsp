<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.duman.entities.Department"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
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
		<TITLE>测评进度</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/tree/dep.js"></script>
		<script type="text/javascript">
			function grantManage(userId,roleId){
				if(roleId==1){
					alert('超级管理员，拥有所有权限，不需要赋权！！！');
					return;
				}
				document.location.href="showUserGrant.action?elUser.id="+userId;
			}
			function init(){
				document.getElementById("depId").name="department.id";
			}
		</script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
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
	</HEAD>
	<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="测评进度" />
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
		<div style="margin-top: 0px; text-align: center;">
			<script type="text/javascript"> 
				
			 	function page(i){ 
			 		document.getElementById("pageNow").value=i;
			 		acc_list.submit();
			 	}
			 	
			 	function search(){
			 		document.getElementById('pageNow').value=0;
			 		acc_list.submit();
			 	}
			 	
			</script>
			<s:form action="statisticalAnalysis.action" method="post" name="acc_list"
				theme="simple">
				<table width="100%" cellpadding="1" cellspacing="1">
					<tr>
						<td valign="top" width="200" id="tree_list_td">
							<%
								Department dep = (Department) request
											.getAttribute("department");
									String depid = dep.getId() + "";
							%>
							<wysLib:dep_list_aj rootAble="true"
								href="statisticalAnalysis.action?sub_department=1&elUser.valid2=0&department.id="
								iname="department.idd" ivalue="<%=depid%>"></wysLib:dep_list_aj>
							<script type="text/javascript">
								w0.setValues([new DEP(<s:property value="department.id"/>,<s:property value="department.lid"/>,<s:property value="department.rid"/>)]);
							</script>
						</td>
						<td valign="middle" width="5px;" style="padding: 0px">
							<img src="images/leftmenu/main_55.gif" style="cursor: hand"
								onclick="changeTreeDisplay(this)" />
						</td>
						<td valign="top">
							<s:hidden name="pN" id="pageNow" />
							<s:hidden name="pS" />
							<s:hidden name="elUser.email" />
							<s:hidden name="department.id" />
							<div style="text-align: center;">
								角色：
								<select name="elUser.role.id">
									<option value="0">
										请选择
									</option>
									<s:iterator value="roles">
										<option <s:if test="elUser.role.id==id">selected='selected'</s:if>
											value="<s:property value="id"/>">
											<s:property value="name" />
										</option>
									</s:iterator>
								</select>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 用户名：
								<s:textfield name="elUser.username" />
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 姓名：
								<s:textfield name="elUser.realname" />
								&nbsp;&nbsp;&nbsp;
								<br />
								开通状态：
								<label>
									<s:radio list="#{1:'开通',2:'关闭',0:'全部'}" name="elUser.valid2"
										value="elUser.valid2" />
								</label>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 包含下属部门：
								<label>
									<input type="checkbox" name="sub_department"
										<s:if test="sub_department==1">checked="checked"</s:if>
										id="sub_department" value="1">
								</label>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input class="textbg4" onClick="search();"
									type="button" style="width:45px" value="搜 索">
							</div>

							<table align="center" cellpadding="1" cellspacing="1"
								width="100%" height="100%">
								<tr>
									<th width="100">
										用户名
									</th>
									<th width="100">
										姓名
									</th>
									<th width="100">
										单位/部门
									</th>
									<th width="100">
										角色
									</th>
									<th width="50">
										性别
									</th>
									<th width="50">
										年龄
									</th>
									<th width="50">
										学历
									</th>
									<th width="50">
										专业
									</th>
									<th width="50">
										测评进度
									</th>
								</tr>
								<tbody onMouseOut="changeback()" onMouseOver="changeto()">
									<s:iterator value="elUsers">
										<tr>
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
												<s:property value="educationName" />
											</td>
											<td height="20" align="center">
												<s:property value="specialty" />
											</td>
											<td height="20" align="center">
												<s:if test="cepingjinduName=='已完成'">
													<span style='color:red' ><s:property value="cepingjinduName" /></span>
												</s:if>
												<s:else>
													<s:property value="cepingjinduName" />
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
			<wysLib:page></wysLib:page>
		</div>
		<!-- 内容 -->
	
	</body>	
</HTML>
