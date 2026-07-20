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
		<TITLE>中国食品安全培训网--管理端--学员添加</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript">
		function showtree(flag){
					var tree = document.getElementById("tree_list_td");
					var img = document.getElementById("showimg");
					if(flag){
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(false);" class="textbg5">隐藏部门</a>';
					}
					else{
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(true);" class="textbg5">显示部门</a>';
					}
				}
		</script>
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="学员列表" /></div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<br>
			<form action="course_notequerylist.action" method="post" name="acc_list">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<s:hidden name="department.id" />
				<s:hidden name="sub_department" />
				<s:hidden name="elUser.username" />
				<s:hidden name="elUser.email" />
				<s:hidden name="elUser.realname" />

			</form>
			<table width="100%">
			<tr>
				
						
				<td valign="top" width="120" id="tree_list_td" style="display:none">
					<wysLib:dep_list_f rootAble="true"
								href="course_notequerylist.action?sub_department=1&department.id="></wysLib:dep_list_f>
				</td>
				<td valign="middle" width="5px;" style="padding: 0px">
					<img src="images/leftmenu/main_55_1.gif" style="cursor: hand"
						onclick="changeTreeDisplay(this)" id="showimg"/>
				</td>
						
						
				<td valign="top" align="left">
					
					<s:form action="course_notequerylist" method="post" theme="simple"
						name="department_info" id="department_info">
					<table border="0" width="100%" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
							
					<tr>
					  <td width="100" rowspan="3" align="center" ><div style="text-align: left;" id="showtree">
						<a href="javascript:showtree(true);" class="textbg5">显示部门</a>
					</div></td>
						<td width="120" height="30" align="center" >
							所属部门：
						</td>
						<td >
							<label>
								<select style="width: 100px" name="department.id" id="parentid">
									<wysLib:dep_select />
								</select>
							</label>
						</td>
						<td width="120" height="30" align="center" >
							包含下属部门<strong>：</strong>
						</td>
						<td >
							<label>
							<s:hidden name="pN" value="0" />
							<s:hidden name="pS" value="10" />
								<input type="checkbox" name="sub_department" <s:if test="sub_department==1">checked="checked"</s:if> id="sub_department"
									value="1">
							</label>
						</td>
					</tr>
					<tr>
					  <td width="120" height="30" align="center" >
						  学号<strong>：</strong>
						</td>
						<td >
							<label>
							<s:textfield name="elUser.username"></s:textfield>
								<!-- input type="text" name="elUser.username" id="username" value=""-->
							</label>
						</td>
							<td width="120" height="30" align="center" >
							姓名<strong>：</strong>
					    </td>
						<td >
							<label>
							<s:textfield name="elUser.realname"></s:textfield>
								<!-- input type="text" name="elUser.realname" id="name" value=""-->
							</label>
						</td>
					</tr>
					<tr>
					  <td width="120" height="30" align="center" >
					    电子邮箱 </td>
						<td >
							<label>
							    <s:textfield name="elUser.email"></s:textfield>
								<!--input type="text" name="elUser.email" id="email" value=""-->
							</label>
						</td>
						<td width="50" colspan="2">
										<input type="submit" value="搜索">
					  </td>
					</tr>
				</table>
		  </s:form>
		<s:if test="courses.size==0">没有数据</s:if>
			<s:else>
		<table width="100%" height="100%" align="center" cellpadding="2" cellspacing="2"
				>
				<tr>
					<th>
						用户名
					</th>
					<th>
						姓名
					</th>
					<!--<th>
						编号
					</th>
					<th>
						单位
					</th>-->
					<th>
						部门
					</th>
					<!--<th>
						角色
					</th>
					<th>
						电子邮箱
					</th>-->
					<th width="120">&nbsp;					</th>
				</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
				<s:iterator value="elUsers">
					<tr>
						<td height="20" align="center">
							<s:property value="username" />
						</td>
						<td height="20" align="center">
							<s:property value="realname" />
						</td>
						<!--<td height="20" align="center">
							<s:property value="userno" />
						</td>
						<td height="20" align="center">
							<s:property value="company.name" />
						</td>-->
						<td height="20" align="center">
							<s:property value="department.name" />
						</td>
						<!--<td height="20" align="center">
							<s:property value="roleName" />
						</td>
						<td height="20" align="center">
							<s:property value="email" />
						</td>-->
						<td width="120" height="20" align="center">
							<a style="cursor: hand" onClick="notequery(<s:property value="id"/>,'<s:property value="realname"/>')" class=textbg5>查看笔记</a>
					  </td>
					</tr>
				</s:iterator></tbody>
			</table>	</s:else>
			</td>
			</tr>
			</table>
			<form action="course_notequery.action" method="post" name="course_notequery">
			    <s:hidden name="elUser.id" id="elUser.id"/>
				<s:hidden name="elUser.realname" id="queryrealname"/>
			</form>
			<script type="text/javascript">
			    function notequery(id,realname){
			      document.getElementById("elUser.id").value=id;
			      document.getElementById("queryrealname").value=realname;
			      document.forms.course_notequery.submit();
			    }
			 	function page(i){
			 		document.getElementById("pageNow").value=i;
			 		acc_list.submit();
			 	}
			</script>
			<wysLib:page></wysLib:page>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
