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
		<TITLE>五矿发展员工职业发展系统--管理端--显示下拉选项信息</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/menu.js"></script>
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<script type="text/javascript" src="eltree/dtree.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/submit.js"></script>
		<script type="text/javascript">
		function deleteUserinfo(obj,id,optype){
			if(window.confirm("确定删除？")){
			depid = <s:property value="department.id"/> ;
			$.post("dep_delete_user.action", {
				"elUser.id":id,
				"department.id":depid,
				"optype":optype, 
				"x":Math.random
				}, 
				function (data) {
					alert('删除成功');
				});
			obj.parentNode.parentNode.removeChild(obj.parentNode);
			}
		}
		function setMess(){
			if('<s:property value="sub_operate"/>'==1){
				alert("更新成功");
				return ;
			}
			if('<s:property value="sub_operate"/>'==-2){
				alert("该部门编号不合法！");
				return ;
			}
			if('<s:property value="sub_operate"/>'==-1){
				alert("该部门编号为空！");
				return ;
			}
		}
		</script>
		<style type="text/css">
<!--
.STYLE1 {
	color: #FF0000
}
-->
</style>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((   this .   sectionRowIndex %   2 ==   0)
		? 
		 "#ffffff" :   "#f4f4f4" )
}
</style>
	</HEAD>
	<BODY onLoad="setMess();">
		<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="" />
				</div>
			</li>
		</ul>
		<div style="margin-top: 0px;">
			<table width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<td valign="top" width="120" id="tree_list_td">
						<wysLib:knowledgeTree_list_aj href="viewKnowledgeTree.action?klTree.id="
							rootAble="true"></wysLib:knowledgeTree_list_aj>
						<script type="text/javascript">
							w0.setValues([new DEP(<s:property value="klTree.id"/>,<s:property value="klTree.lid"/>,<s:property value="klTree.rid"/>)]);
						</script>
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
					</td>
					<td>
						<table border="0" width="100%" height="100%" cellpadding="1"
							cellspacing="1">
							<tr>
								<td width="120" height="30" align="right">
									知识类别名称：
								</td>
								<td style="padding-left: 8px;" align="left">
									<label>
										<s:property value="klTree.name" />
									</label>
								</td>
							</tr>
							<tr>
								<td width="120" height="30" align="right">
									详细说明：
								</td>
								<td style="padding-left: 8px;" align="left">
									<label>
										<s:property value="klTree.description" />
									</label>
								</td>
							</tr>
							<tr>
								<td width="120" height="30" align="right">
									知识类别编号：
								</td>
								<td style="padding-left: 8px;" align="left">
									<label>
										<s:property value="klTree.bh" />
									</label>
								</td>
							</tr>
							<tr>
								<td width="120" height="30" align="right">
									可管理人员：
								</td>
								<td style="padding-left: 8px;" align="left">
									<div>
										<s:iterator value="klTree.opusers">
											<span
												style="height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
												<label style="float: left;">
													<s:property value="realname" />
												</label> <span class="STYLE1">＊</span> 
												
											</span>
										</s:iterator>
									</div>
								</td>
							<tr>
								<td height="50" align="left" bgcolor="#FFFFFF">
								</td>
								<td height="50" align="left" bgcolor="#FFFFFF">
									<a class="textbg4"
										href="alterKnowledgeTreeInit.action?klTree.id=<s:property value="klTree.id" />"
										>编 辑</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

									<s:if test="#session.roleid==1">
										<a class="textbg4"
											href="deleteKnowledgeTreeInit.action?klTree.id=<s:property value="klTree.id" />"
											>删 除</a>
									</s:if>
									<s:else>
										<a class="textbg4" href="javascript:alert('您没有删除权限，如确需删除，请与系统管理员联系.');"
											class="textbg2">删 除</a>
									</s:else>
										<input type="button" class="textbg4" style="width:100px;" onClick="document.location='listKnowledgeTree.action'" value="返回列表" />
								</td>
							</tr>
							

						</table>
					</td>
				</tr>
			</table>

		</div>
	</BODY>
</HTML>
