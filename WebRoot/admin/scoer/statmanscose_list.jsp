<%@ page language="java" pageEncoding="UTF-8"%>
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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<TITLE>我的图书管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
		
		</script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>

		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="所有订单列表" /></div>
			</li>
			<!--<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="news_addInit.action">新闻公告添加</a>

			</li>-->
		</ul>

		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<table width="100%">
				<tr>
				<td valign="top" width="120" id="tree_list_td">

							<wysLib:dep_list_aj rootAble="true" href="getstudentcouse.action?sub_department=1&deptid="></wysLib:dep_list_aj>
							<script type="text/javascript">
								w0.setValues([new DEP(<s:property value="department.id"/>,<s:property value="department.lid"/>,<s:property value="department.rid"/>)]);
							</script>
						</td>
				<td valign="top">
		<s:form action="getstudentcouse.action" method="post" name="assignSearch_assignment" theme="simple">	
			<table width="100%"> 
				<tr>
									<td>订购者姓名</td>
									<td><s:textfield name="elUser.realname" theme="simple"/>
				           </td>
				           <td>性 别</td>
									<td> <select name="elUser.sex">
					      <option value=""></option>
				       	  <option value="男">男</option>
				          <option value="女">女</option>
						</select>
				           </td>
				 
				    <td>是否包含下级部门：<label>
									<input type="checkbox" name="sub_department"
										<s:if test="sub_department==1">checked="checked"</s:if>
										id="sub_department" value="1">
								</label></td>
								<td><input type="submit" value="搜索" /></td>
				</tr>
			
			</table>
			</s:form>
						<s:if test="lu.size==0"><h3 align="center" style="margin-top:10px;">没有信息</h3></s:if>
						<s:else>
							<table width="100%" align="center" cellpadding="2"
								cellspacing="2" bgcolor="#EBEBEB">
								<tr>
									<th width="260" height="30" align="center" >
										学员姓名									</th>
									<th width="260" height="30" align="center" >
										部门									</th>
										<th width="100" height="30" align="center" >
										性别									</th>
											<th width="100" height="30" align="center" >
										年龄									</th>
											
									<th width="90" height="30" align="center" >
										学分数									</th>
									<th width="70" height="30" align="center" >
										详情								</th>
								</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
								<s:iterator value="lu">
									<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
											<s:property value="realname" />
									  </td>
										<td height="30" align="center" >
											<s:property value="department.name" />
										</td>
										<td height="30" align="center" >
											<s:property value="sex" />
										</td>
										<td height="30" align="center" >
											
											<s:property value="age" />
										</td>
										<td height="30" align="center" >
											
											<s:property value="btotalscore" />
										</td>
										<td width="70" height="30" align="center" >
											<a style="cursor:pointer;"  href="getstudentcousebyuserid.action?my=1&elUser.id=<s:property value="id" />"  class="textbg6">查看</a>

									  </td>
									</tr>
								</s:iterator></tbody>
						  </table>
						</s:else>
					</td>
				</tr>
			</table>
			<%-- 
			<form action="newsManage_list.action" method="post" name="nlist">
				<s:hidden name="ntype.id" />
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
			</form>
			 --%>
			<script type="text/javascript">
			function page(i){
				document.getElementById("pageNow1").value=i;
				assignUser.submit();
			}
		
		</script>
			<wysLib:page></wysLib:page>
				<s:form action="getstudentcouse.action" method="post" name="assignUser">
				<s:hidden name="pN" id="pageNow1" />
				<s:hidden name="pS" />
				<s:hidden name="deptid" />
				<s:hidden name="sub_department" />
				<s:hidden name="elUser.realname" />
				<s:hidden name="elUser.sex" />
			</s:form>
		</div>
	</BODY>
</HTML>
										   