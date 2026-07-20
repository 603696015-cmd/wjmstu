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
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
		function deleteUserinfo(obj,id,optype){
			if(window.confirm("确定删除？")){
			depid = <s:property value="examPaperLib.id"/> ;
			$.post("eplib_delete_user.action", {
				"elUser.id":id,
				"examPaperLib.id":depid,
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
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="查看基本信息" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">查看试卷库信息 </span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="exampaperLib_alterInit.action?examPaperLib.id=<s:property value="examPaperLib.id"/>">编辑试卷库信息
				</a>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="exampaperLib_deleteInit.action?examPaperLib.id=<s:property value="examPaperLib.id"/>">删除试卷库信息
				</a>
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
			<table width="700" cellpadding="1" cellspacing="1">
				<tr>
					<td valign="top" width="200px" id="tree_list_td">
						<wysLib:elibtree itype="OP"
							href="exampaperLib_view.action?examPaperLib.id=" rootAble="true"></wysLib:elibtree>
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
					</td>
					<td valign="top">
						<table align="left" width="100%" cellpadding="1" cellspacing="1"
							bgcolor="#EBEBEB">
							<tr>
								<td width="120" height="30" align="right">
									目录名称：
								</td>
								<td>
									<label>
										<s:property value="examPaperLib.name" />
									</label>
								</td>
							</tr>
							<tr>
								<td width="120" height="80" align="right">
									目录说明：
								</td>
								<td>
									<label>
										<s:property value="examPaperLib.description" />
									</label>
								</td>
							</tr>
							<tr>
								<td width="120" height="30" align="right">
									上级目录：
								</td>
								<td>
									<label>
										<s:property value="examPaperLib.parent.name" />
									</label>
								</td>
							</tr>
							<tr>
								<td width="120" align="right">
									可管理人员：
								</td>
								<td>
									<div>
										<s:iterator value="examPaperLib.opusers">
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
						可使用人员：					</td>
					<td >
						<div>
							<s:iterator value="examPaperLib.useusers">
								<span
									style="height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
									<label style="float: left;">
										<s:property value="realname" />
											</label> <span class="STYLE1">＊</span> <!--<a
									style="cursor: hand; float: right; width: 14px; height: 14px;"
									href=""
									onclick="javascript:deleteUserinfo(this,<s:property value="id"/>,'use');return false;">X</a>-- 
								</span>							</s:iterator>
						</div>					</td>
				</tr>-->
							<tr>
								<td align="center">&nbsp;
									
								</td>
								<td>
									<a
										href="exampaperLib_alterInit.action?examPaperLib.id=<s:property value="examPaperLib.id"/>"
										class=textbg>编 辑 </a>
									<a
										href="exampaperLib_deleteInit.action?examPaperLib.id=<s:property value="examPaperLib.id"/>"
										class=textbg>删 除</a>
									<a href="exampaperLib_list.action" class=textbg>返回卷库类别</a>
									<a href="exampaperLib_addInit.action" class="textbg">添加试卷类别</a>
									
									<!--   返回当前类别（细看） -->
									<a href="exampaper_list.action?sublibs=1&examPaper.epl.id=<s:property value="examPaperLib.id"/>" class="textbg">试卷管理</a>
									
									<s:form method="post" name="delEp"
										action="exampaperLib_delete.action">
										<s:hidden name="examPaperLib.id" id="eplid" />
										<!--<s:if test="#session.roleid==1">
						<strong> 下属试卷库与子类别操作</strong> 并入上级类别
						<input type="radio" name="sub_operate" checked="checked" value="0">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<label>
							与本类别同时删除
						<input type="radio" name="sub_operate" value="1">
						</label>
					<%--
						<a  onclick="return window.confirm('确定删除？');" href="exampaperLib_delete.action?examPaperLib.id=<s:property value="examPaperLib.id"/>" class=textbg>删 除</a>
					 --%>
					 <a onclick="return window.confirm('确定删除？');" href="javascript:delEpl('<s:property value="examPaperLib.id"/>');" class=textbg>删 除</a>
				</s:if>
				<s:else>
					<a href="javascript:alert('您没有删除的权限，请与系统管理员联系.');" class="textbg4">删 除</a>
				</s:else>-->
									</s:form>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<script type="text/javascript">
				function delEpl(eplid){
					document.getElementById("eplid").value=eplid;
					delEp.submit();
				}
			</script>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
