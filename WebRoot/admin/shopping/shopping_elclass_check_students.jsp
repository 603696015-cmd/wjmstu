<%@ page language="java" pageEncoding="UTF-8"   %>
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
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
			<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="查看学员" /></div>
			</li>
				<!--<li>
					 分配学员
				</li>  -->
		</ul>
		<!-- 内容 -->
		<s:form action="shopping_elclass_check_students.action" method="post" name="assignSearch_assignment"  theme="simple">
		<div style="margin-top: 0px; text-align: center;">
			<s:hidden name="pN" id="pageNow2" />
			<s:hidden name="pS" />
			<s:hidden name="elclass.id" />
			<s:hidden name="examRoom.id" />
			<s:hidden name="examPaper.id" />
			<s:hidden name="department.id" />
			<s:hidden name="elUser.isAssign" />
			
		<table width="100%">
			<tr>
				<td colspan="2">
			 <table align="center" cellpadding="1" cellspacing="1" width="100%"  
				>
				<tr>
					<td>
						<%-- 
						<wysLib:BasetName btid="4" />： 
								<s:select name="elUser.gangwei" cssClass="g-select"
										list="gangweis" listKey="id" key="2" listValue="basevalue"  headerValue="全部" headerKey="0" />
						 --%> 
					</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				<tr>
					<td> 
						姓名：<input name="elUser.realname"
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
							name="elUser.shengri" onclick="setday(this)" readonly>
					</td>
					<td>
						生日结束时间:
						<input type="text" size="16"
							value="<s:date format="yyyy-MM-dd" name="elUser.shengri_end"/>"
							name="elUser.shengri_end" onclick="setday(this)" readonly>
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
						<input type="checkbox" name="sub_department"	<s:if test="sub_department==1">checked="checked"</s:if>	id="sub_department" value="1"/>
					</td>
					<td>
						
					</td>
					<td>
					</td>
					<td>
					</td>
					<td> 
						<input id="find" name="find" type="button" onClick="doForm();" value="搜索">
					</td>
				</tr>
			</table>
				</td>
			</tr>
			<tr>
			<td valign="top" bgcolor="#FFFFFF">
			<%
			   ElClass elclass=(ElClass)request.getAttribute("elclass");  
			   String url="elclass_check_students.action?elclass.id="+elclass.getId()+"&sub_department=1&department.id=";
			 %>
			<wysLib:dep_list_aj rootAble="true"
							href="<%=url%>" iname="department.idd" ></wysLib:dep_list_aj>			</td>
			<td align="left" valign="top" bgcolor="#FFFFFF">
		<s:if test="elusers.size==0">当前还没有分配学员</s:if>
			<s:else>
				<table width="100%" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr>
					<td height="30" align="center" bgcolor="#66CCFF">
										</td>
						<td height="30" align="center" bgcolor="#66CCFF">
						姓名						</td>
						 <td height="30" align="center" bgcolor="#66CCFF" >
						性别						</td>
					   <td height="30" align="center" bgcolor="#66CCFF" >
							账号					  </td>
					    <td height="30" align="center" bgcolor="#66CCFF" >
							部门						</td>
						<td height="30" align="center" bgcolor="#66CCFF" >
							<wysLib:BasetName btid="1" />
					  </td>
						<td height="30" align="center" bgcolor="#66CCFF" >
							年龄						</td>
						<td height="30" align="center" bgcolor="#66CCFF" >
							角色						</td>
						<td height="30" align="center" bgcolor="#66CCFF" >
							分配						</td>
						<td height="30" align="center" bgcolor="#66CCFF" >
							参加方式						</td>
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
							<td height="30" style="padding-left:8px;color:blue;" align="left">
							     <input type="checkbox" value="<s:property value="id"/>" name="id"> 
						       </td>
						<td height="30" style="color:blue;" align="center">
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
								<td height="30" align="center" >
									<s:property value="joinway" />
								</td>
								
							</tr>
						</s:iterator>
					</s:else>
			  </table> 
		  </s:else></td></tr>
		  
		  <tr>
		  <td></td>
		  <td align="center"><s:if test="elusers.size!=0"><a href="ClassOrderPreview.action?sub_department=1&elclass.id=<s:property value="elclass.id" />&elUser.isAssign=0" class="textbg">确认订购</a></s:if>
		  <a href="shopping_elclass_assign2userInit.action?sub_department=1&elclass.id=<s:property value="elclass.id" />" class="textbg4">重新分配</a>	
		  </td>

		  </tr>
					
		  </table>
			<wysLib:page></wysLib:page>
		</div>
		
			</s:form> 
				<a href="javascript:select_All()" />全选</a>
					<a href="javascript:select_Fan()" />反选</a>
					<a href="javascript:select_Bux()" />全不选</a>
						<br>
			<input value="取消分配" type="button" onClick="unassign()">
			<form action="elclass_assign2userInit.action" method="post" name="course_assignment">
				<s:hidden name="deptid" />
				<s:hidden name="elclass.id" />
				<s:hidden name="elUser.sex" />
				<s:hidden name="elUser.realname" />
				<s:hidden name="elUser.username" />
				<s:hidden name="starttime" />
				<s:hidden name="endtime" />
				<s:hidden name="elUser.isAssign" />
				<s:hidden name="userids" id="userids"></s:hidden>
	     </form>
		<!-- 内容 -->
	</BODY><script>
		function select_All(){
							var cks= document.getElementsByName("id");
							for(var i = 0 ; i < cks.length; i++){
								cks[i].checked= true;
							}
						}
						function select_Fan(){
							var cks= document.getElementsByName("id");
							for(var i = 0 ; i < cks.length; i++){
								cks[i].checked= !cks[i].checked;
							}
						}
						function select_Bux(){
							var cks= document.getElementsByName("id");
							for(var i = 0 ; i < cks.length; i++){
								cks[i].checked= false;
							}
						}
							function unassign(){
						  if(window.confirm("确定取消分配？！")){
						     var checkObj = document.getElementsByName("id");
							    var billIDs = "";
							    for (i = 0; i < checkObj.length; i++) {
									if (checkObj[i].checked) {
									    if(billIDs!="")billIDs+=",";
										billIDs += checkObj[i].value;
									}
								 }
								if(billIDs==""){
								  alert("请选择要取消分配的记录！");
								  return ;
							    }
							  var userids = document.getElementById("userids");
						      userids.value=billIDs;
							  course_assignment.action="shopping_elclass_dele_students.action";
							  course_assignment.submit();
							}
						}
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
		<form action="elclass_check_students.action" method="post" name="acc_list">
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
