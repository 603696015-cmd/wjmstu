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
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>五矿发展员工职业发展系统</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/libutil.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>

		<script type="text/javascript">
			function deleteUserinfo(obj,id,optype){
				if(window.confirm("确定删除？")){
				cltypeid = <s:property value="cltype.id"/> ;
				$.post("cltype_delete_user.action", {
					"elUser.id":id,
					"cltype.id":cltypeid,
					"optype":optype, 
					"x":Math.random
					}, 
					function (data) {
						alert('删除成功');
					});
				obj.parentNode.parentNode.removeChild(obj.parentNode);
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
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
</style>
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="培训班类别简介" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<table style="margin-top: 4px;" width="700" cellpadding="1"
			cellspacing="1">
			<tr>
				<td width="200px" valign="top" id="tree_list_td">
					<wysLib:clTypeTree href="cltype_view.action?cltype.id="
						rootAble="true" did="0" />
				</td>
				<td valign="middle" width="5px;" style="padding: 0px">
					<img src="images/leftmenu/main_55.gif" style="cursor: hand"
						onclick="changeTreeDisplay(this)" />
				</td>
				<td valign="top" align="left">
					<table width="100%" cellpadding="1" cellspacing="1">
						<tr>
							<td width="120" height="30" align="right">
								类别名称：
							</td>
							<td>
								<label>
									<s:property value="cltype.name" />
								</label>
							</td>
						</tr>
						<tr>
							<td width="120" height="30" align="right">
								类别介绍：
							</td>
							<td>
								<label>
									<s:property value="cltype.description" />
								</label>
							</td>
						</tr>
						<tr>
							<td width="120" height="30" align="right">
								上级类别：
							</td>
							<td>
								<label>
									<s:property value="cltype.parent.name" />
								</label>
							</td>
						</tr>

						<tr>
							<td width="120" align="right">
								可管理人员：
							</td>
							<td>
								<div id="can_op">
									<s:iterator value="cltype.opusers">
										<span
											style="height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
											<label style="float: left;">
												<s:property value="realname" />
											</label> <span class="STYLE1">＊</span> </span>
									</s:iterator>
								</div>
							</td>
						</tr>
						<!--<tr>
							<td width="120" align="center" >
								可使用人员：
							</td>
							<td >
								<div id="can_use">
									<s:iterator value="cltype.useusers">
										<span
											style="height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
											<label style="float: left;">
												<s:property value="realname" />
											</label> <span class="STYLE1">＊</span><!--<a
											style="cursor: hand; float: right; width: 14px; height: 14px;"
											href=""
											onclick="javascript:deleteUserinfo(this,<s:property value="id"/>,'CLASS_USE_TYPE');return false;">X</a>-- 
									</s:iterator>
								</div>
							</td>
						</tr>-->
						<tr>
							<td width="120" height="50" align="center">
								&nbsp;

							</td>
							<td>
								<!--<a
									href="cltype_alterInit.action?cltype.id=<s:property value="cltype.id"/>">修改</a>-->
								<a
									href="cltype_alterInit.action?cltype.id=<s:property value="cltype.id"/>"
									class=textbg>编 辑</a>
								<s:if test="#session.roleid==1">
									<a
										href="cltype_deleteInit.action?cltype.id=<s:property value="cltype.id" />"
										class=textbg>删 除</a>
								</s:if>
								<s:else>
									<a href="javascript:alert('您没有删除的权限，请与系统管理员联系.');"
										class="textbg">删 除</a>
								</s:else>
								<a href="cltype_list.action" class=textbg>返回班类别</a>
								<a href="cltype_addInit.action" class=textbg>添加类别</a>
								<a href="elclass_alllist.action?sublibs=1&cltype.id=<s:property value="cltype.id" />" class=textbg>培训班列表</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</HTML>
