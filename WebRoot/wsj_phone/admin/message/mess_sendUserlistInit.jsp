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
		<base href="<%=basePath%>" target="_self">
		<TITLE>中国食品安全培训网--管理端-学员管理</TITLE>
		<META http-equiv=Pragma content=no-cache>
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)> 
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<LINK href="member/css.css" type=text/css rel=stylesheet>
		<LINK href="member/css2.css" type=text/css rel=stylesheet>
		<LINK href="css/tscss.css" type=text/css rel=stylesheet>
		<LINK href="exam_css/houtai.css" type=text/css rel=stylesheet>
	</HEAD>
	<BODY style="height: 100%; width: 100%;margin: 0px">
	<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<!--整个页面的顶部-->
		<!-- 内容 -->
		<form action="mess_sendUserlist.action" method="post">
			<table width="100%" border="0" align="left" cellpadding="1"
				cellspacing="1" bgcolor="#66CCFF" style="font: 11">
				<tr>
					<td width="120" height="30" align="center" >
						所属部门：
					</td>
					<td >
						<s:hidden name="pN" value="0" id="pageNow"/>
						<s:hidden name="pS" value="15" id="pageSize"/>
						<label>
							<select style="width: 300px" name="department.id" id="departmentid">
								<wysLib:dep_select />
							</select>
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="center" >
						<strong>包含下属部门</strong>
					</td>
					<td >
						<label>
							<input type="checkbox" name="sub_department" id="sub_department"
								value="1" checked="checked">
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="center" >
						<strong>用户号</strong>
					</td>
					<td >
						<label>
							<input type="text" name="elUser.username" id="username" >
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="center" >
						<strong>姓　名</strong>
					</td>
					<td >
						<label>
							<input type="text" name="elUser.realname" id="name" >
						</label>
					</td>
				</tr>
			<!-- 	<tr>
					<td width="120" height="30" align="center" >
						<strong>电子邮箱</strong>
					</td>
					<td >
						<label>
							<input type="text" name="elUser.email" id="email" >
						</label>
					</td>
				</tr>	 -->
				<tr>
				  <td width="120" height="50" align="center" >&nbsp;
						
				  </td>
					<td >
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="50">
								
									<input type="submit" value="搜索">
								</td>
								<td width="20">&nbsp;
									
							  </td>
							</tr>
						</table>
					</td>
				</tr>
		  </table>
	</form>
	
	</body>
</HTML>
