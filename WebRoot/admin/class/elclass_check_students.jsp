<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.classman.entities.ElClass"%>
<%@page import="com.sopia.duman.entities.Department"%>
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
		<TITLE>分配学员</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/tree/depuserlist.js"></script>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
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
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="查看学员" />
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
		<s:form action="elclass_check_students.action" method="post"
			name="assignSearch_assignment" theme="simple">
			<div style="margin-top: 0px; text-align: center;">
				<s:hidden name="pN" id="pageNow2" />
				<s:hidden name="pS" />
				<s:hidden name="elclass.id" />
				<s:hidden name="examRoom.id" />
				<s:hidden name="examPaper.id" />
				<s:hidden name="department.id" />
				<table width="100%" cellpadding="1" cellspacing="1">
					<tr>
						<td colspan="2">
							<table align="center" cellpadding="1" cellspacing="1"
								width="100%">
								<tr>
									<td>
										<%-- 
						<wysLib:BasetName btid="4" />： 
								<s:select name="elUser.gangwei" cssClass="g-select"
										list="gangweis" listKey="id" key="2" listValue="basevalue"  headerValue="全部" headerKey="0" />
						 --%>
									</td>
									<td>
										<wysLib:BasetName btid="5" />
										：
										<s:select name="elUser.dishi" cssClass="g-select"
											list="dishis" listKey="id" listValue="basevalue"
											headerValue="全部" headerKey="0" />
									</td>
									<td>
										<wysLib:BasetName btid="3" />
										：
										<s:select name="elUser.zhiji" cssClass="g-select"
											list="zhijis" listKey="id" listValue="basevalue"
											headerValue="全部" headerKey="0" />
									</td>
									<td>
										<wysLib:BasetName btid="2" />
										：
										<s:select name="elUser.zhiwu" cssClass="g-select"
											list="zhiwus" listKey="id" listValue="basevalue"
											headerValue="全部" headerKey="0" />
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
											name="elUser.shengri" onclick="setday(this)">
									</td>
									<td>
										生日结束时间:
										<input type="text" size="16"
											value="<s:date format="yyyy-MM-dd" name="elUser.shengri_end"/>"
											name="elUser.shengri_end" onclick="setday(this)">
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
										搜索包含下级部门：
										<input type="checkbox" name="sub_department"
											<s:if test="sub_department==1">checked="checked"</s:if>
											id="sub_department" value="1" />
									</td>
									<td>
										是否已分配：
										<select name="elUser.isAssign">
											<option value="-1"
												<s:if test="elUser.isAssign==-1">selected="selected"</s:if>>
												全部
											</option>
											<option value="0"
												<s:if test="elUser.isAssign==0">selected="selected"</s:if>>
												是
											</option>
											<option value="1"
												<s:if test="elUser.isAssign==1">selected="selected"</s:if>>
												否
											</option>
										</select>
									</td>
									<td>
									</td>
									<td>
									</td>
									<td>
										<input id="find" name="find" type="button" onClick="doForm();"
											value="搜索">
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td valign="top" bgcolor="#FFFFFF">
							<%
								ElClass elclass = (ElClass) request.getAttribute("elclass");
									String url = "elclass_check_students.action?elclass.id="
											+ elclass.getId() + "&sub_department=1&department.id=";
							%>
							<wysLib:dep_list_aj rootAble="true" href="<%=url%>"
								iname="department.idd"></wysLib:dep_list_aj>
						</td>
						<td align="left" valign="top" bgcolor="#FFFFFF">
							<s:if test="elusers.size==0">当前还没有分配学员</s:if>
							<s:else>
								<table width="100%" align="center" cellpadding="1"
									cellspacing="1" bgcolor="#EBEBEB">
									<tr>
										<td height="30" align="center" bgcolor="#66CCFF">
											姓名
										</td>
										<td height="30" align="center" bgcolor="#66CCFF">
											性别
										</td>
										<td height="30" align="center" bgcolor="#66CCFF">
											账号
										</td>
										<td height="30" align="center" bgcolor="#66CCFF">
											部门
										</td>
										<td height="30" align="center" bgcolor="#66CCFF">
											<wysLib:BasetName btid="1" />
										</td>
										<td height="30" align="center" bgcolor="#66CCFF">
											年龄
										</td>
										<td height="30" align="center" bgcolor="#66CCFF">
											角色
										</td>
										<td height="30" align="center" bgcolor="#66CCFF">
											分配
										</td>
										<td height="30" align="center" bgcolor="#66CCFF">
											参加方式
										</td>
									</tr>
									<s:if test="elusers.size==0">
										<TR>
											<TD align="center" colspan="4">
												当前还没有分配学员
											</TD>
										</TR>
									</s:if>
									<s:else>
										<s:iterator value="elusers">
											<tr>
												<td height="30" style="color: blue;" align="center">
													<s:property value="realname" />
												</td>
												<td height="30" align="center">
													<s:property value="sex" />
												</td>
												<td height="30" align="center">
													<s:property value="username" />
												</td>
												<td height="30" align="center">
													<s:property value="department.name" />
												</td>
												<td height="30" align="center">
													<s:property value="jingzhong_" />
												</td>
												<td height="30" align="center">
													<s:property value="age" />
												</td>
												<td height="30" align="center">
													<s:property value="role.name" />
												</td>
												<td height="30" align="center">
													<s:property value="isAssign" />
												</td>
												<td height="30" align="center">
													<s:property value="joinway" />
												</td>

											</tr>
										</s:iterator>
									</s:else>
								</table>
							</s:else>
						</td>
					</tr>
				</table>
				<wysLib:page></wysLib:page>
			</div>
		</s:form>
		<form action="elclass_assign2userInit.action" method="post"
			name="course_assignment">
			<s:hidden name="deptid" />
			<s:hidden name="elclass.id" />
			<s:hidden name="elUser.sex" />
			<s:hidden name="elUser.realname" />
			<s:hidden name="elUser.username" />
			<s:hidden name="elUser.jingzhong" />
			<s:hidden name="starttime" />
			<s:hidden name="endtime" />
			<s:hidden name="elUser.isAssign" />
			<s:hidden name="userids" id="userids"></s:hidden>
		</form>
		<div style="text-align: center;">
			<s:if test="Return=='assign'">
		<a href="elclass_assignlist2.action" class="textbg4" style="width:100px">返回分配列表</a>
		</s:if>
		<s:if test="Return=='ash'">
		<a href="elclass_primash_list.action" class="textbg4" style="width:100px">返回申请列表</a>
		</s:if>
		<s:if test="Return=='aal'">
		<a href="elclass_applyAlter_list.action" class="textbg4" style="width:100px">返回修改列表</a>
		</s:if>
		<s:if test="Return=='alsh'">
		<a href="elclass_alter_list.action" class="textbg4" style="width:100px">返回处理列表</a>
		</s:if>
		<s:if test="Return=='adl'">
			<a href="elclass_applyDelete_list.action" class="textbg4" style="width:100px">返回删除列表</a>
		</s:if>
		<s:if test="Return=='adla'">
			<a href="elclass_delete_apply_list.action" class="textbg4" style="width:120px">返回处理删除列表</a>
		</s:if>
		<s:if test="Return=='csc'">
			<a href="combinationSearchClass.action" class="textbg4" style="width:120px">返回处理搜索列表</a>
		</s:if>
		</div>
		<!-- 内容 -->
		<script>
				function page(i) {
					document.getElementById("pageNow2").value=i;
					//acc_list.submit();
					assignSearch_assignment.submit();
				}
				function doForm(){
					document.getElementById("pageNow2").value=0;
					assignSearch_assignment.submit();
				}
			</script>
		<form action="elclass_check_students.action" method="post"
			name="acc_list">
			<s:hidden name="pN" id="pageNow" />
			<s:hidden name="pS" />
			<s:hidden name="deptid" />
			<s:hidden name="elclass.id" />
			<s:hidden name="examRoom.id" />
			<s:hidden name="examPaper.id" />
			<s:hidden name="cltype.id" />
			<s:hidden name="elUser.sex" />
			<s:hidden name="elUser.realname" />
			<s:hidden name="elUser.username" />
			<s:hidden name="elUser.jingzhong" />
			<s:hidden name="starttime" />
			<s:hidden name="endtime" />
			<s:hidden name="elUser.isAssign" />
		</form>
	</BODY>
</HTML>
