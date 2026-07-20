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
		<TITLE>五矿发展员工职业发展系统--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<link rel="StyleSheet" href="js/tree/dtree.css" type="text/css" />
		<script type="text/javascript" src="js/tree/depuserlist.js"></script>
	
	<script type="text/javascript" src="js/cexampaper.js"></script>
	<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="新闻列表页" /></div>
			</li>
			<li>
			<span style="font-weight: bold;">分配培训班到学员</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="elclass_assign2depInit.action?elclass.id=<s:property value="elclass.id" />">分配给部门</a>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<form action="" method="post" name="class_assignment"
			id="class_assignment">
			<script type="text/javascript">
			
function elclass_assign2user_add( ){
	if(window.confirm("确定分配？！")){
	class_assignment.action="elclass_assign2user_add.action";
	class_assignment.submit();
	}
}
function elclass_assign2user_delete(){
	if(window.confirm("确定删除分配？！")){
	class_assignment.action="elclass_assign2user_delete.action"
	class_assignment.submit();
	}
}
</script>
			<input type="hidden" name="source" id="source" value="assignment">
			<table width="90%" border="1" align="center">
				<tr>
					<td width="50%">
						已分配学员
					</td>
					<td></td>
					<td width="50%">
						可分配学员
					</td>
				</tr>
				<tr>
					<td valign="top">
						<wysLib:depuserlist attrname="assignedDep" inputname="assignedUsers.id"></wysLib:depuserlist>
						<!--<select name="assignedUsers.id" id="class_user_list" size="40"
							style="width: 100%" multiple="multiple"
							onclick="switch_candidate();">
							<s:iterator value="assignedUsers">
							<option value="<s:property value="id"/>">
								<s:property value="realname"/> 
							</option>
							</s:iterator>
						</select>
					--></td>
					<td align="center">
						<input id="submit_add" name="submit_add" type="button"
							value="<< 添加" onclick="elclass_assign2user_add()">
						<br>
						<input id="submit_delete" name="submit_delete" type="button"
							value="删除 >>" onclick="elclass_assign2user_delete()">
					</td>
					<td valign="top">
						<wysLib:depuserlist attrname="canAssignDep" inputname="canAssignUsers.id"></wysLib:depuserlist>
						<!--<select name="canAssignUsers.id" id="candidate_user_list" size="40"
							style="width: 100%" multiple="multiple" onclick="switch_class();">
							<s:iterator value="canAssignUsers">
							<option value="<s:property value="id"/>">
								<s:property value="realname"/> 
							</option>
							</s:iterator>
						</select>
						--><s:hidden name="elclass.id" />
					</td>
				</tr>
			</table>
			<br>
		</form>
	</body>
</HTML>
