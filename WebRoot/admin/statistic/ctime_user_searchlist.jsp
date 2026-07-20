<%@ page language="java" pageEncoding="UTF-8"%>
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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript">
		 	function page(i){
		 		search_list.action="ctime_user_searchlist.action";
		 		document.getElementById("pageNow").value=i;
		 		search_list.submit();
		 	}
		 	function toDetail(){
			 	search_list.action="ctime_detail_searchlist.action";
			 	search_list.submit();
		 	}
		 	 function toexcel(){
		 		search_list.action="ctime_user_list.action";
		 		search_list.submit();
		 	}
		</script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="学员列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">学时概况</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="javascript:toDetail();">学时详情</a>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
		<table width="100%" cellpadding="1" cellspacing="1">
			<tr>
				<td valign="top" width="120" id="tree_list_td">
					<%
						Department dep = (Department) request
									.getAttribute("department");
							String depid = dep.getId() + "";
					%>
					<wysLib:dep_list_aj rootAble="true"
						href="ctime_user_searchlist.action?sub_department=1&department.id="
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
					<s:form action="ctime_user_searchlist" method="post" name="search_list" theme="simple">
						<s:hidden name="pN" id="pageNow" />
						<s:hidden name="pS" />
						<s:hidden name="department.id" />
						<div style="text-align: center;">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 用户名：
							<s:textfield name="elUser.username" />
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 姓名：
							<s:textfield name="elUser.realname" />
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 包含下属部门：
							<label>
								<input type="checkbox" name="sub_department"
									<s:if test="sub_department==1">checked="checked"</s:if>
									id="sub_department" value="1">
							</label>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input class="textbg4"
								onclick="document.getElementById('pageNow').value=0;"
								type="submit" value="搜 索">
						</div>
					</s:form>
					<table width="100%" align="center" cellpadding="1" cellspacing="1">
						<tr>
						  <th align="center" >姓名</th>
							<th align="center" >
								账号					</th>
							<th align="center" >
								部门					</th>
							<th align="center" >
								课程总时长					</th>
							<th align="center" >
								已学总时长					</th>
							<!-- <th align="center" >
								总学分					</th>
							<th align="center" >
								已获学分					</th> -->
						</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
						<s:iterator value="elUsers" status="st">
							<tr>
							  <td height="30" align="center" style="color:#CC0099;"><s:property value="realname" /></td>
								<td align="center" >
									<s:property value="username" />						</td>
								<td align="center" >
									<s:property value="department.name" />						</td>
								<td align="center" >
								<s:property value="ct_time" />						</td>
								<td align="center" >
								<s:property value="xx_time" />						</td>
								<!-- <td align="center" >
								<s:property value="ct_credit" />						</td>
								<td align="center" >
								<s:property value="xx_credit" />						</td> -->
							</tr>
						</s:iterator></tbody>
				  </table>
				  </td>
				</tr>
			</table>
			<br>
			<wysLib:page></wysLib:page>
			<a href="javascript:toDetail();" class=textbg>查看学时详情</a> 　 <a target="" href="javascript:toexcel();" class=textbg>导出当前列表</a>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
