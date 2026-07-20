<%@ page language="java" pageEncoding="UTF-8"   %>
<%@page import="com.sopia.classman.entities.ElClass"%>
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
		<script type="text/javascript" src="js/message.js"></script>
		
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<script type="text/javascript" src="js/tree/depuserlist.js"></script>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="审核学员" /></div>
			</li>
				<!--<li>
					 审核学员
				</li>  -->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
			
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
		<form action="elclass_check_students.action" method="post" name="assignSearch_assignment">
			<s:hidden name="elclass.id" />
				<s:hidden name="examRoom.id" />
				<s:hidden name="examPaper.id" />
			<table width="100%">
				<tr>
				  <td>部门:</td>
				   <td><select style="width: 100%" name="deptid" id="deptid">
								<wysLib:dep_select />
							</select></td>
				
				      <td>性别：</td>
				      <td><select name="elUser.sex">
				      <option value=""></option>
			        <option value="男">男</option>
			        <option value="女">女</option>
			</select></td>
			   <td>是否已分配</td>
				         <td>
				           <select name="elUser.isAssign">
				               <option></option>
				               <option value="0">是</option>
				               <option value="1">否</option>
				           </select>
				         </td>
				</tr>
				<tr>
				   <td> 姓名：</td>
				    <td><input name="elUser.realname" id="elUser.realname"></td>
				     <td>账号：</td>
				      <td><input name="elUser.username" id="elUser.username"></td>
				       <td><wysLib:BasetName btid="1" />：</td>
				        <td><input name="elUser.jingzhong" id="elUser.jingzhong"></td>
				</tr>
				<tr>
				   <td>年龄段开始时间:</td>
				     <td><input type="text" size="16" name="starttime" onClick="setday(this)"></td>
				       <td>年龄段结束时间:</td>
				         <td> <input type="text" size="16" name="endtime" onClick="setday(this)"></td>
				         <td colspan="2">
				           	 <input id="find" name="find" type="submit" value="搜索">
				         </td>
				</tr>
			</table>
			</form>
		<table width="100%">
			<tr>
			<td valign="top" width="150px;">
			<%
			   ElClass elclass=(ElClass)request.getAttribute("elclass");
			   String url="elclass_Modify_applicationInit.action?elclass.id="+elclass.getId()+"&deptid=";
			 %>
			<wysLib:dep_list_f rootAble="true" href="<%=url%>"></wysLib:dep_list_f></td>
			<td valign="top" align="left">
		<s:if test="elusers.size==0">当前还没有分配学员</s:if>
			<s:else>
				<table width="90%" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						姓名
						</td>
						 <td height="30" align="center" >
						性别
						</td>
					   <td height="30" align="center" >
							账号
						</td>
					    <td height="30" align="center" >
							部门
						</td>
						<td height="30" align="center" >
							<wysLib:BasetName btid="1" />
						</td>
						<td height="30" align="center" >
							年龄
						</td>
						<td height="30" align="center" >
							角色
						</td>
						<td height="30" align="center" >
							分配
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
							<s:if test=""></s:if>
							<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
							      <s:property value="realname" /> 
						       </td>
						         <td height="30" align="center" >
							      <s:property value="sex" />
						       </td>
                                <td height="30" align="center" >
									<s:property value="username" />
								</td>
                                <td height="30" align="center" >
									<s:property value="department.name" />
								</td>
								<td height="30" align="center" >
									<s:property value="jingzhong_" />
								</td>
								<td height="30" align="center" >
									<s:property value="age" />
								</td>
								<td height="30" align="center" >
									<s:property value="role.name" />
								</td>
								<td height="30" align="center" >
									<s:property value="isAssign" />
								</td>
								
							</tr>
						</s:iterator>
					</s:else>
				</table>
			</s:else></td></tr></table><wysLib:page></wysLib:page> 
		</div>
			<form action="elclass_Modify_applicationInit.action" method="post" name="course_assignment">
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
	     <s:form action="elclass_sh_apply" method="post" theme="simple" name="sh">  
		     <table width="100%">
			    <!--  <tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
					  申请标题：
					</td>
					<td height="30" align="center" >
					 <s:textarea name="ecAudit.title" cols="30" rows="1"  ></s:textarea> 
					</td>
				</tr> -->
			     <tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
					  备注详情：
					</td>
					<td height="30" align="center" >
					  <s:if test="ecAudit.replycontent != null">
						<textarea name="ecAudit.replycontent" cols="60" rows="7" disabled="disabled">
						 <s:property value="ecAudit.replycontent"/></textarea> <br>
					</s:if>
					<s:textarea name="ecAudit.content" cols="60" rows="7"></s:textarea> 
					<s:hidden name="elclass.id" />  
					<s:hidden name="ecAudit.status" value="2"/>   
					</td>
				</tr>
			     <tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left"> 
					</td>
					<td height="30" align="center" > 
					<s:if test="stutus != 4">					
						<s:submit value="申请审核"></s:submit>
					</s:if><s:else>
						<span>修改申请审核中..请耐心等待..</span>
					</s:else>
					</td>
				</tr>
		     </table>
	     </s:form>
		<!-- 内容 -->
	
	</body><script>
				function page(i) {
					document.getElementById("pageNow").value=i;
					acc_list.submit();
				}
			</script>
		<form action="elclass_Modify_applicationInit.action" method="post" name="acc_list">
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
</HTML>
