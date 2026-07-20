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
		<TITLE>中国食品安全培训网--管理端--用户管理</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
	</HEAD>
	<BODY style="height: 100%; width: 100%">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">用户管理</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="account_addInit.action?elUser.department.id=<s:property value="department.id"/>">添加用户</a>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<table width="100%">
				<tr>
					<td valign="top" width="120" id="tree_list_td">
						<wysLib:dep_list_aj rootAble="true"
							href="account_search.action?sub_department=1&elUser.valid2=0&department.id=" iname="department.id" itype="ra"></wysLib:dep_list_aj>				  
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
					</td>
					<td valign="top">
						<s:form action="account_search" method="post" theme="simple"
							name="department_info" id="department_info">
							<table border="0" width="100%" cellpadding="2" cellspacing="2"
								bgcolor="#EBEBEB">
							<!--	<tr>
									<td width="120" height="30" align="center" >
										搜索范围：
									</td>
									<td >
										<label>
											
											<select style="width: 300px" name="department.id"
												id="parentid">
												 wysLib:dep_select 
											</select>
										</label>
									</td>
								</tr>
								--><tr>
									<td width="120" height="30" align="center" >
										<strong>包含下属部门</strong>
									</td>
									<td >
										<label><s:hidden name="pN" value="0" />
											<s:hidden name="pS" value="10" />
											<input type="checkbox" name="sub_department"
												id="sub_department" value="1">
										</label>
									</td>
								</tr>
								<tr>
									<td width="120" height="30" align="center" >
										<strong>角色</strong>
									</td>
									<td >
										<label>
											<select name="role.id">
												<option value="0">
													请选择
												</option>
												<s:iterator value="roles">
													<option <s:if test="role.id==id">selected='selected'</s:if>
														value="<s:property value="id"/>">
														<s:property value="name" />
													</option>
												</s:iterator>
											</select>
										</label>
									</td>
								</tr>
								<tr>
									<td width="120" height="30" align="center" >
										<strong>用户名</strong>
									</td>
									<td >
										<label>
											<input type="text" name="elUser.username" id="username"
												value="">
										</label>
									</td>
								</tr>
								<tr>
									<td width="120" height="30" align="center" >
										<strong>姓名</strong>
									</td>
									<td >
										<label>
											<input type="text" name="elUser.realname" id="name" value="">
										</label>
									</td>
								</tr>
								<tr>
									<td width="120" height="30" align="center" >
										<strong>开通状态</strong>
									</td>
									<td >
										<label>
											<input type="radio" name="elUser.valid" value="true" checked="checked">开通
											<input type="radio" name="elUser.valid" value="false">关闭
											<input type="radio" name="elUser.valid" value="true" onClick="setNov();">全部
										</label>
									</td>
								</tr>
								<tr>
									<td width="120" height="50" align="center" >&nbsp;
										
									</td>
									<td >
										<table width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td width="50">
													<input class="textbg4" type="submit" value="搜 索">
												</td>
												<td width="20">&nbsp;
													
												</td>
												<td>
												<a href="account_addInit.action?elUser.department.id=<s:property value="department.id"/>" class="textbg5">添加用户</a>
												<a href="account_importBydepInit.action?elUser.department.id=<s:property value="department.id"/>"  class="textbg5">导入用户</a>
												
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</s:form>
					</td>
				</tr>
			</table>
			<script type="text/javascript">
				function setNov(){
					document.getElementById("elNov").value=1;
				}
			</script>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
