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
		<TITLE>中国食品安全培训网--管理端--显示岗位信息</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/menu.js"></script>
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<script type="text/javascript" src="eltree/dtree.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/submit.js"></script>
		<script type="text/javascript">
		function deleteUserinfo(obj,id,optype){
			if(window.confirm("确定删除？")){
			depid = <s:property value="station.id"/> ;
			$.post("dep_delete_user.action", {
				"elUser.id":id,
				"station.id":staid,
				"optype":optype, 
				"x":Math.random
				}, 
				function (data) {
					alert('删除成功');
				});
			obj.parentNode.parentNode.removeChild(obj.parentNode);
			}
		}
		function setMess(){
			if('<s:property value="sub_operate"/>'==1){
				alert("更新成功");
				return ;
			}
			if('<s:property value="sub_operate"/>'==-2){
				alert("该岗位编号不合法！");
				return ;
			}
			if('<s:property value="sub_operate"/>'==-1){
				alert("该岗位编号为空！");
				return ;
			}
		}
		</script>
		<style type="text/css">
<!--
.STYLE1 {
	color: #FF0000
}
-->
</style>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((   this .   sectionRowIndex %   2 ==   0)
		? 
		 "#ffffff" :   "#f4f4f4" )
}
</style>
	</HEAD>
	<BODY onLoad="setMess();">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<table width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<td valign="top" width="120" id="tree_list_td">
						<wysLib:st_list_aj href="sta_view.action?station.id="
							rootAble="true"></wysLib:st_list_aj>
						<script type="text/javascript">
							w0.setValues([new ST(<s:property value="station.id"/>,<s:property value="station.lid"/>,<s:property value="station.rid"/>)]);
						</script>
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
					</td>
					<td>
						<table border="0" width="100%" height="100%" cellpadding="1"
							cellspacing="1">
							<tr>
								<td width="120" height="30" align="right">
									岗位名称：
								</td>
								<td style="padding-left: 8px;" align="left">
									<label>
										<s:property value="station.name" />
									</label>
								</td>
							</tr>
							<tr>
								<td width="120" height="30" align="right">
									详细说明：
								</td>
								<td style="padding-left: 8px;" align="left">
									<label>
										<s:property value="station.description" />
									</label>
								</td>
							</tr>
							<tr>
								<td width="120" height="30" align="right">
									联系电话：
								</td>
								<td style="padding-left: 8px;" align="left">
									<label>
										<s:property value="station.phone" />
									</label>
								</td>
							</tr>
							<tr>
								<td width="120" height="30" align="right">
									地 址：
								</td>
								<td style="padding-left: 8px;" align="left">
									<label>
										<s:property value="station.address" />
									</label>
								</td>
							</tr>
							<tr>
								<td width="120" height="30" align="right">
									邮政编码：
								</td>
								<td style="padding-left: 8px;" align="left">
									<label>
										<s:property value="station.postalcode" />
									</label>
								</td>
							</tr>
							<tr>
								<td width="120" height="30" align="right">
									传 真：
								</td>
								<td style="padding-left: 8px;" align="left">
									<label>
										<s:property value="station.fax" />
									</label>
								</td>
							</tr>
							<tr>
								<td width="120" height="30" align="right">
									电子邮箱：
								</td>
								<td style="padding-left: 8px;" align="left">
									<label>
										<s:property value="station.email" />
									</label>
								</td>
							</tr>
							<tr>
								<td width="120" height="30" align="right">
									岗位编号：
								</td>
								<td style="padding-left: 8px;" align="left">
									<label>
										<s:property value="station.bh" />
									</label>
								</td>
							</tr>
							<tr>
								<td width="120" height="30" align="right">
									可管理人员：
								</td>
								<td style="padding-left: 8px;" align="left">
									<div>
										<s:iterator value="station.opusers">
											<span
												style="height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
												<label style="float: left;">
													<s:property value="realname" />
												</label> <span class="STYLE1">＊</span> <!--<a
												style="cursor: hand; float: right; width: 14px; height: 14px;"
												href=""
												onclick="javascript:deleteUserinfo(this,<s:property value="id"/>,'op');return false;">X</a>-->
											</span>
										</s:iterator>
									</div>
								</td>
							</tr>
								<tr>
								<td width="120" height="30" align="right">
									是否二级页面：
								</td>
								<td style="padding-left: 8px;" align="left">
									<label>
										<s:if test="station.issp==1">
											是
										</s:if><s:else>
											否
										</s:else>
									</label>
								</td>
							</tr>
							<!--<tr>
								<td width="120" height="30" align="center">
									可使用人员：
								</td>
								<td style="padding-left: 8px;" align="left">
									<div id="">
										<s:iterator value="department.useusers">
											<span
												style="height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
												<label style="float: left;">
													<s:property value="realname" />
												</label> <span class="STYLE1">＊</span> <a
												style="cursor: hand; float: right; width: 14px; height: 14px;"
												href=""
												onclick="javascript:deleteUserinfo(this,<s:property value="id"/>,'use');return false;">X</a>
											</span>
										</s:iterator>
									</div>
								</td>
							</tr>
							-->
							<tr>
								<td height="50" align="left" bgcolor="#FFFFFF">
								</td>
								<td height="50" align="left" bgcolor="#FFFFFF">
									<a class="textbg4"
										href="sta_alterInit.action?station.id=<s:property value="station.id" />"
										>编 辑</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

									<s:if test="#session.roleid==1">
										<a class="textbg4"
											href="sta_deleteInit.action?station.id=<s:property value="station.id" />"
											>删 除</a>
									</s:if>
									<s:else>
										<a class="textbg4" href="javascript:alert('您没有删除权限，如确需删除，请与系统管理员联系.');"
											class="textbg2">删 除</a>
									</s:else>
								<!-- 		<input type="button" class="textbg4" style="width:100px;" onClick="document.location='station_list.action'" value="返回列表" /> -->
								<!--	<a
										href="station_addInit.action?station.id=<s:property value="station.id" />"
										class="textbg4" style="width:80px;">添加岗位</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-->
										
							
								<!-- 	
							  	    <a
										href="sta_elclass_addInit.action?staid=<s:property value="station.id" />"
										class="textbg4" style="width:100px;">岗位课管理</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; --> 
										
								<!-- 	
									<a
										href="sta_addCourseInit.action?station.id=<s:property value="station.id" />&classid=-2"
										class="textbg4" style="width:100px;">添加必修课程</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<a
										href="sta_addCourseInit.action?station.id=<s:property value="station.id" />&classid=-3"
										class="textbg4" style="width:100px;">添加选修课程</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; --> 
									<a
										href="peoplePostInit.action?st.id=<s:property value="station.id" />"
										class="textbg4" style="width:100px;">人岗匹配</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
							</tr>
							

						</table>
					</td>
				</tr>
			</table>

		</div>
	
	</body>
</HTML>
