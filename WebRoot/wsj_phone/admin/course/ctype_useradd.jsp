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
		<script type="text/javascript" src="js/menu.js"></script>
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<script type="text/javascript" src="eltree/dtree.js"></script>
	</HEAD>
	<BODY style="height: 100%; width: 100%">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="用户列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">授权管理</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="coursetype_view.action?ctype.id=${ctype.id }">查看类别</a>
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
			<script type="text/javascript">
			 	function page(i){
			 		acc_list.action = "ctype_useraddInit.action";
					document.getElementById("pageNow").value=i;
			 		acc_list.submit();
			 	}
				 function search_( ){
			 		acc_list.action = "ctype_useraddInit.action";
			 		acc_list.submit();
			 	}
			 	function addctu(){
			 		acc_list.action = "ctype_useradd.action";
			 		acc_list.submit();
			 	}
			</script>
			<wysLib:page></wysLib:page>
			<s:form action="ctype_useraddInit" method="post" theme="simple"
				name="acc_list" id="acc_list">
				<table width="100%">
					<tr>
						<td valign="top" width="360px;" id="tree_list_td">
							<s:hidden name="ctype.id"></s:hidden>
							<table border="0" width="100%" cellpadding="1" cellspacing="1"
								bgcolor="#EBEBEB">
								<tr>
									<td align="center" >
										搜索范围：
									</td>
									<td >
										<label>
											<select name="department.id" id="parentid">
												<wysLib:dep_select />
											</select>
											<s:hidden name="pN" value="0" />
										<s:hidden name="pS" value="10" />
										</label>
									</td>
								</tr>
								<tr>
									<td align="center" >
										<strong>包含下属部门</strong>
									</td>
									<td >
										<label>
											<input type="checkbox" name="sub_department"
												id="sub_department" value="1"
												<s:if test="sub_department==1">checked='checked'</s:if>>
										</label>
									</td>
								</tr>
								<tr>
									<td align="center" >
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
									<td align="center" >
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
									<td align="center" >
										<strong>姓名</strong>
									</td>
									<td >
										<label>
											<input type="text" name="elUser.realname" id="name" value="">
										</label>
									</td>
								</tr>
								<tr>
									<td align="center" >&nbsp;
										
									</td>
									<td >
										<input type="button" onClick="search_()" value="搜索">
									</td>
								</tr>
							</table>
						</td>
						<td valign="middle" width="5px;" style="padding: 0px">
							<img src="images/leftmenu/main_55.gif" style="cursor: hand"
								onclick="changeTreeDisplay(this)" />
						</td>
						<td valign="top">
							<table align="center" cellpadding="2" cellspacing="2"
								width="100%" height="100%" >
								<tr>
									<th>
									</th>
									<th>
										用户名
									</th>
									<th>
										姓名
									</th>
									<th>
										单位/部门
									</th>
									<th>
										角色
									</th>
								</tr>
								<s:iterator value="elUsers">
									<tr>
										<td height="20" align="center">
											<s:if test="introom">
											已经添加
										</s:if>
											<s:else>
												<input type="checkbox" value="<s:property value="id" />"
													name="elUsers.id">
											</s:else>
										</td>
										<td height="20" align="center">
											<s:property value="username" />
										</td>
										<td height="20" align="center">
											<s:property value="realname" />
										</td>
										<td height="20" align="center">
											<s:property value="department.name" />
										</td>
										<td height="20" align="center">
											<s:property value="role.name" />
										</td>
									</tr>
								</s:iterator>
							</table>
							<input type="button" onClick="addctu()" value="授权到该类别">
						</td>
					</tr>
				</table>
			</s:form>

		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
