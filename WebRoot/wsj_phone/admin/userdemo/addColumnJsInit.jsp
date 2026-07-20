<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="userdemoLib" uri="/WEB-INF/userdemoLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<META http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>添加JS验证</title>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
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
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<script type="text/javascript">
		function check(){
			var checks = document.getElementsByName("check");
			var ids = "";
			if(checks!=undefined){
				for(var i=0;i<checks.length;i++){
					if(checks[i].checked){
						ids += checks[i].value + ",";
					}
				}
			}
			
			if(ids == "" ){
				alert("请至少选择一个JS复选框");
				return false;
			}
			ids = ids.substring(0,ids.lastIndexOf(","));
			document.getElementByid("elUserJs.check_js_type").value = ids ; 
			
			var flag = false;
			var checkboxes = document.getElementsByName("checkbox");
			if(checkboxes!=undefined){
				for(var i=0;i<checkboxes.length;i++){
					if(checkboxes[i].checked){
						flag = true;
					}
				}
			}
			if(!flag){
				alert("请至少选择一个页面复选框");
			}
		}
  		
  </script>
	<body >
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td valign="middle" class="tablequiz">
					<ul class="nav">
						<li>
							<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
								<wysLib:Navigation ivalue="添加JS验证" />
							</div>
						</li>

					</ul>
				</td>
				<td width="120" valign="middle" class="tablequiz">
					<A id=quit href="javascript:window.parent.full_screen(false);"
						class="textbg6" style="display: none">退出全屏</A>
				</td>
			</tr>
		</table>
		<!-- 内容 -->
		<form action="addColumnJs.action" method="post" name="jj" onSubmit="return check();">
		<div style="margin-top: 0px; text-align: center;">
			<s:if test="jsTypes!=null&&jsTypes.size()>0">
				<table cellpadding='1' cellspacing='1' width='100%'>
					<caption>"<s:property value="elUserJs.column_name" />"字段<s:property value="elUserJs.show_typeName" />验证规则</caption>
					<tr><th></th><th>编号</th><th>名称</th><th>描述</th><th>是否合作</th><th>长度</th><th>长度</th><th>长度</th></tr>
					<s:iterator value="jsTypes">
						<tr>
							<td><input type='checkbox' name="check" value="<s:property value="id" />" /></td>
							<td align=center><s:property value="id" /></td>
							<td align=center><s:property value="name" /></td>
							<td align=center><s:property value="description" /></td>
							<td align=center><s:property value="team" /></td>
							<td align=center><s:property value="llength" /></td>
							<td align=center><s:property value="clength" /></td>
							<td align=center><s:property value="rlength" /></td>
						</tr>
					</s:iterator>
				</table>
			</s:if>
			<s:else>
				暂无JS验证
			</s:else>
		</div>
		<div style="margin-top: 0px; text-align: center;">
			<input name="submit" type="submit" value="确认修改" class=textbg6 />
			<input type="hidden" name="elUserJs.check_js_type" id="elUserJs.check_js_type" />
			<s:hidden name="elUserJs.column_name"></s:hidden>
			<s:hidden name="elUserJs.show_type"></s:hidden>
		</div>
		</form>
	
	</body>
</html>