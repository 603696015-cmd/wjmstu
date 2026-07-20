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
		<TITLE>中国食品安全培训网--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
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
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/libutil.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
		function deleteUserinfo(obj,id,optype){
			if(window.confirm("确定删除？")){
			ntypeid = <s:property value="ntype.id"/> ;
			$.post("newstype_delete_user.action", {
				"elUser.id":id,
				"ntype.id":ntypeid,
				"optype":optype, 
				"x":Math.random
				}, 
				function (data) {
					alert('删除成功');
				});
			obj.parentNode.parentNode.removeChild(obj.parentNode);
			}
		}
		function delEpl(ntypeId){
			document.getElementById("ntypeId").value=ntypeId;
			delEp.submit();
		}
		</script>

		<style type="text/css">
<!--
.STYLE1 {
	color: #FF0000
}
-->
</style>
	</HEAD>
	<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="新闻栏目简介" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">新闻公告栏目添加</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="newstype_list.action">新闻公告栏目管理</a>

			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<table width="100%" cellpadding="1" cellspacing="1">
			<tr>
				<td valign="top" width="200px" id="tree_list_td">
					<wysLib:newsTypeTree href="newstype_view.action?ntype.id="
						rootAble="true"></wysLib:newsTypeTree>
				</td>
				<td valign="middle" width="5px;" style="padding: 0px">
					<img src="images/leftmenu/main_55.gif" style="cursor: hand"
						onclick="changeTreeDisplay(this)" />
				</td>
				<td valign="top">
					<table width="100%" cellpadding="1" cellspacing="1">
						<tr>
							<td width="120" height="30" align="right">
								栏目名称：
							</td>
							<td>
								<label>
									<s:property value="ntype.name" />
								</label>
							</td>
						</tr>
						<tr>
							<td width="120" height="30" align="right">
								栏目介绍：
							</td>
							<td>
								<label>
									<s:property value="ntype.description" />
								</label>
							</td>
						</tr>
						<tr>
							<td width="120" height="30" align="right">
								上级栏目：
							</td>
							<td>
								<label>
									<s:property value="ntype.parent.name" />
								</label>
							</td>
						</tr>
						<tr>
							<td width="120" align="right">
								可管理人员：
							</td>
							<td>
								<div id="can_op">
									<s:iterator value="ntype.opusers">
										<span
											style="height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
											<label style="float: left;">
												<s:property value="realname" />
											</label> <span class="STYLE1">＊</span> <!--<a
									style="cursor: hand; float: right; width: 14px; height: 14px;"
									href=""
									onclick="javascript:deleteUserinfo(this,<s:property value="id"/>,'NEWSTYPE_OP_TYPE');return false;">X</a>-->
										</span>
									</s:iterator>
								</div>
							</td>
						</tr>
						<!-- <tr>
						<td width="120" align="center" >
							可使用人员：
						</td>
						<td >
							<div id="can_use">
							<s:iterator value="ntype.useusers">
								<span
									style="height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
									<label style="float: left;">
										<s:property value="realname" />
								</label> <span class="STYLE1">＊</span>  !--<a
									style="cursor: hand; float: right; width: 14px; height: 14px;"
									href=""
									onclick="javascript:deleteUserinfo(this,<s:property value="id"/>,'NEWSTYPE_USE_TYPE');return false;">X</a>-- 
								</span>
							</s:iterator>
							</div>
						</td>
					</tr> -->
						<tr>
							<td width="120" height="50" align="center">&nbsp;
								

							</td>
							<td>
								<a
									href="newstype_alterInit.action?ntype.id=<s:property value="ntype.id"/>"
									class="textbg4">修 改</a>
									  <input class="textbg6" type="button" onClick="document.location='newstype_list.action'" value="返回类别">
								      <input class="textbg6" type="button" onClick="document.location='newsManage_list.action?ntype.id=<s:property value="ntype.id"/>'" value="新闻列表">
								<br />
								<s:form method="post" name="delEp"
									action="newstype_delete.action">
									<s:hidden name="ntype.id" id="ntypeId" />
									<s:if test="#session.roleid==1">
										<strong> 下属试卷库与子类别操作</strong>
										<input type="radio" name="newsIsDel" checked="checked"
											value="1"> 并入上级类别
								&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" name="newsIsDel" value="2">与本类别同时删除
								<a onClick="return window.confirm('确定删除？');"
											href="javascript:delEpl('<s:property value="ntype.id"/>');"
											class="textbg4">删 除</a>
										<%-- 
									<a href="newstype_delete.action?ntype.id=<s:property value="ntype.id"/>" onclick="javascript:return window.confirm('确定删除？')" class="textbg4">删 除</a>
								 --%>
									</s:if>
									<s:else>
										<a href="javascript:alert('您没有删除的权限，请与系统管理员联系.');"
											class="textbg4">删 除</a>
									</s:else>
								</s:form>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

	
	</body>
</HTML>
