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
		<TITLE></TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/libutil.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
			function delEpl(ptypeId){
				document.getElementById("ptypeId").value=ptypeId;
				delEp.submit();
			}
		</script>
				
	  <style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
        </style>
</HEAD>
	<body>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="产品所说栏目简介" /></div>
			</li>
		</ul>
		<table width="100%">
			<tr>
			<td valign="top" width="200px" id="tree_list_td"><wysLib:productTypeTree href="productType_view.action?ptype.id=" rootAble="true"></wysLib:productTypeTree>
		</td>
			<td valign="middle" width="5px;" style="padding: 0px" >
							<img src="images/leftmenu/main_55.gif" style="cursor: hand"
										onclick="changeTreeDisplay(this)" />
			  </td>
			<td valign="top"><table width="100%" cellpadding="2" cellspacing="1" >
				<tr>
					<td width="120" height="30" align="center" >
						栏目名称
					</td>
					<td >
						<label>
							<s:property value="ptype.name" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="center" >
						栏目介绍
					</td>
					<td >
						<label>
							<s:property value="ptype.description"/>
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="center" >
						上级栏目
					</td>
					<td >
						<label>
							<s:property value="ptype.parent.name" />
						</label>
					</td>
				</tr>
							<tr>
						<td width="120" align="center" >
							可管理人员：
						</td>
						<td >
							<div id="can_op">
							<s:iterator value="ptype.opusers">
								<span
									style="height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
									<label style="float: left;">
										<s:property value="realname" />
								</label> <span class="STYLE1">＊</span> 
								</span>
							</s:iterator>
							</div>
						</td>
					</tr>
				<tr>
					<td width="120" height="50" align="center" >&nbsp;
						
					</td>
					<td >
						<a href="productType_alterInit.action?ptype.id=<s:property value="ptype.id"/>" class="textbg4">修 改</a>
						<br />
						<s:form method="post" name="delEp" action="productType_delete.action">
						<s:hidden name="ptype.id" id="ptypeId" />
							<s:if test="#session.roleid==1">
								<strong> 产品栏目与子栏目操作</strong> 并入上级类别
								<input type="radio" name="productIsDel" checked="checked" value="1">
								&nbsp;&nbsp;
								<label>
									与本类别同时删除
								<input type="radio" name="productIsDel" value="2">
								</label>
								<a onclick="return window.confirm('确定删除？');" href="javascript:delEpl('<s:property value="ptype.id"/>');" class="textbg4">删 除</a>
							</s:if>
							<s:else>
								<a href="javascript:alert('您没有删除的权限，请与系统管理员联系.');" class="textbg4">删 除</a>
							</s:else>
						</s:form>
					</td>
				</tr>
				
			</table></td>
			</tr>
	</table>
			
	</body>
</HTML>
