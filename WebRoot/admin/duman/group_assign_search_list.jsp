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
		<TITLE>五矿发展员工职业发展系统--管理端--学员添加</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/menu.js"></script>
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<script type="text/javascript" src="eltree/dtree.js"></script>
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<li>
				<span style="font-weight: bold;">用户组分配</span>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<!--<table width="60%" cellpadding="1" cellspacing="1">
				<caption>
					场次信息
				</caption>
				<tr>
					<td>
						<strong>场次集标题</strong>
					</td>
					<td>
						<s:property value="troomcoll.title" />
					</td>
				</tr>
				<tr>
					<td align="left" >
						<strong>创建时间</strong>
					</td>
					<td align="left" >
						<s:date name="troomcoll.createtime" format="yyyy-MM-dd HH:mm:ss" />
					</td>
				</tr>
				<tr>
					<td>
						<strong>场次标题</strong>
					</td>
					<td align="left" >
						<label>
							<s:property value="troom.title" />
						</label>
					</td>
				</tr>
				<tr>
					<td>
						<strong>开始结束时间</strong>
					</td>
					<td align="left" >
						<label>
							<s:date name="troom.begintime" format="yyyy-MM-dd" />
							到
							<s:date name="troom.endtime" format="yyyy-MM-dd" />
						</label>
					</td>
				</tr>
				<tr>
					<td>
						<strong>试卷</strong>
					</td>
					<td align="left" >
						<label id="eptitle" style="width: 200px;">
							<s:property value="troom.exampaper.title" />
						</label>
					</td>
				</tr>
				<tr>
					<td>
						<strong>测评指标</strong>
					</td>
					<td align="left" >
						<div id="trnorms" style="width: 100%;">
							<s:iterator status="normosst" value="troom.norms">
								<s:property /> ，
									</s:iterator>
						</div>
					</td>
				</tr>
			</table>
			--><form action="talent_troom_assign_search.action" method="post" name="acc_list">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<s:hidden name="department.id" />
				<s:hidden name="sub_department" />
				<s:hidden name="elUser.username" />
				<s:hidden name="elUser.email" />
				<s:hidden name="elUser.realname" />
				<s:hidden name="group.id" />
				<s:hidden name="troomcoll.id" />
			<table align="center" cellpadding="1" cellspacing="1" width="100%"
				>
				<tr>
					<th>
					</th>
					<th>
						学号
					</th>
					<th>
						姓名
					</th>
					 <th>
						单位
					</th>
					<th>
						部门
					</th>
					<th>
						角色
					</th>
					<th>
						电子邮箱
					</th>
					<th>
						状态
					</th>
				</tr>
				<s:iterator value="elUsers">
					<tr>
						<td height="20" align="center">
							<s:if test="!introom">
							<input type="checkbox" name="elUsers.id" value="<s:property value="id"/>"/>
							</s:if>
							<s:else>
								已经添加
							</s:else>
						</td>
						<td height="20" align="center">
							<s:property value="username" />
						</td>
						<td height="20" align="center">
							<s:property value="realname" />
						</td>
						<!--<td height="20" align="center">
							<s:property value="userno" />
						</td>
						-->
						<td height="20" align="center">
							<s:property value="department.name" />
						</td>
						<td height="20" align="center">
							<s:property value="role.name" />
						</td>
						<td height="20" align="center">
							<s:property value="email" />
						</td>
						<td height="20" align="center">
							<s:property value="validName" />
						</td>
					</tr>
				</s:iterator>
			</table>
			</form>
			<div style="margin-bottom: 20px; text-align: center;">
			<script type="text/javascript">
			 	function page(i){
			 		acc_list.action=  "group_assign_search_list.action";
			 		document.getElementById("pageNow").value=i;
			 		acc_list.submit();
			 	}
			 	function backSearch(){
			 		acc_list.action=  "group_assign_search.action";
			 		acc_list.submit();
			 	}
			 	function assign2user(){
			 		acc_list.action=  "group_assign_add.action";
			 		acc_list.submit();
			 	}
			</script>
				<wysLib:page></wysLib:page>
				<br>
				<input value="添加到当前用户组中" type="button" onclick="assign2user()">
				<input value="重新搜索" type="button" onclick="backSearch()">
			 <br>
				</div>
		<!-- 内容 -->
		
	</BODY>
</HTML>
