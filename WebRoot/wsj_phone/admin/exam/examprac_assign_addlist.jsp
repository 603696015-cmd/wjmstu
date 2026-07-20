<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.duman.entities.Department"%>
<%@page import="com.sopia.courseman.entities.Examprac"%>
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
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<script type="text/javascript" src="eltree/dtree.js"></script>
<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
</style>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="添加学员" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">练习分配人员</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="examprac_assign_list.action?examprac.id=<s:property value="examprac.id"/>">练习人员列表</a>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<h2 align="center" style="margin-top: 6px">
			为练习【
			<s:property value="examprac.title" />
			】添加考生
		</h2>
		<s:form action="examprac_assign_addlist.action" method="post" theme="simple"
			name="acc_list">
			<s:hidden name="examprac.id" />
			<table width="100%">
				<tr>
					<td valign="top" width="120" id="tree_list_td">
						<%
							Department dep = (Department) request.getAttribute("department");
							String depid = dep.getId() + "";
							Examprac epr= (Examprac)request.getAttribute("examprac");
							int pracid = 0;
							if(epr!=null)
								pracid = epr.getId();
							String url = "examprac_assign_addlist.action?examprac.id="+pracid+"&sub_department=1&elUser.valid2=0&department.id=";
						%>
						<wysLib:dep_list_aj rootAble="true"
							href="<%=url %>"
							iname="department.idd" ivalue="<%=depid%>"></wysLib:dep_list_aj>
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
					</td>
					<td valign="top">
					<s:hidden name="pN" id="pageNow" />
							<s:hidden name="pS" />
							<s:hidden name="elUser.email" />
							<s:hidden name="department.id" />
							<s:hidden name="exprot" id="exprot" />
							<div style="text-align: center;">
								角色：
								<select name="elUser.role.id">
									<option value="0">
										请选择
									</option>
									<s:iterator value="roles">
										<option <s:if test="elUser.role.id==id">selected='selected'</s:if>
											value="<s:property value="id"/>">
											<s:property value="name" />
										</option>
									</s:iterator>
								</select>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 用户名：
								<s:textfield name="elUser.username" />
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 姓名：
								<s:textfield name="elUser.realname" />
								&nbsp;&nbsp;&nbsp;
								<br />
								开通状态：
								<label>
									<s:radio list="#{1:'开通',2:'关闭',0:'全部'}" name="elUser.valid2"
										value="elUser.valid2" />
								</label>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 包含下属部门：
								<label>
									<input type="checkbox" name="sub_department"
										<s:if test="sub_department==1">checked="checked"</s:if>
										id="sub_department" value="1">
								</label>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input class="textbg4"
									onclick="document.getElementById('exprot').value='false';document.getElementById('pageNow').value=0;"
									type="submit" value="搜 索">
							</div>
						<table align="center" cellpadding="1" cellspacing="1" width="100%">
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
							</tr>
							<s:iterator value="elusers">
								<tr>
									<td height="30" align="center">
										<s:if test="!introom">
											<input type="checkbox" name="elusers.id"
												value="<s:property value="id"/>" />
										</s:if>
										<s:else>
								已经添加
							</s:else>
									</td>
									<td align="center">
										<s:property value="username" />
									</td>
									<td align="center">
										<s:property value="realname" />
									</td>
									<td align="center">
										<s:property value="department.name" />
									</td>
									<td align="center">
										<s:property value="role.name" />
									</td>
									<td align="center">
										<s:property value="email" />
									</td>
								</tr>
							</s:iterator>
						</table>
					</td>
				</tr>
			</table>
		</s:form>
		<div style="margin-bottom: 20px; text-align: center;">
			<script type="text/javascript">
			 	function page(i){
			 		acc_list.action=  "examprac_assign_addlist.action";
			 		document.getElementById("pageNow").value=i;
			 		acc_list.submit();
			 	}
			 	function backSearch(){
			 		document.location.href=  "examprac_assign_list.action?examprac.id=<s:property value="examprac.id"/>";
			 		//acc_list.submit();
			 	}
			 	function assign2user(){
			 		acc_list.action=  "examprac_assign_add.action";
			 		acc_list.submit();
			 	}
			 	function assign2users(){
			 		if(confirm("确定分配所有人员？")){
				 		acc_list.action= "examprac_assign_adds.action";
				 		acc_list.submit();
			 		}
			 	}
			</script>
			<wysLib:page></wysLib:page>
			<script type="text/javascript">
						function select_All(){
							var cks= document.getElementsByName("elusers.id");
							for(var i = 0 ; i < cks.length; i++){
								cks[i].checked= true;
							}
						}
						function select_Fan(){
							var cks= document.getElementsByName("elusers.id");
							for(var i = 0 ; i < cks.length; i++){
								cks[i].checked= !cks[i].checked;
							}
						}
						function select_Bux(){
							var cks= document.getElementsByName("elusers.id");
							for(var i = 0 ; i < cks.length; i++){
								cks[i].checked= false;
							}
						}
					</script>
			<a href="javascript:select_All()" class="textbg4">全选</a>
			<a href="javascript:select_Fan()" class="textbg4">反选</a>
			<a href="javascript:select_Bux()" class="textbg4" style="width:60px">全不选</a>
			<br>
			<input value="添加到当前练习" type="button" onClick="assign2user()" style="width:110px" class="textbg4"/>
			<input value="分配给全部结果" type="button" onClick="assign2users()" style="width:110px" class="textbg4"/>
			<input value="返回人员列表" type="button" onClick="backSearch()" style="width:110px" class="textbg4"/>
			<br>
		</div>
		<!-- 内容 -->

	
	</body>
</HTML>
