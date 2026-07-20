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
		<title>添加日志</title>
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
		<script type="text/javascript">
			function check(){
				if(document.getElementById("col.column_name").value == ""){
					alert("名称不能为空,请填写");
					document.getElementById("col.column_name").focus();
					return false;
				}
				
				var radios = document.getElementsByName("col.column_type");
				var ra_val = "";
				if(radios!=undefined){
					for(var i=0;i<radios.length;i++){
						if(radios[i].checked){
							ra_val = radios[i].value;
						}
					}
				}
				if(ra_val!=""){
					if(ra_val == "varchar2"){
						if(document.getElementById("col.format").value == ""){
							alert("设置字符串必须要有长度,请填写");
							document.getElementById("col.format").focus();
							return false;
						}
						if(document.getElementById("col.format").value!="" && !isNaN(document.getElementById("col.format")) ){
							alert("设置字符串必须为数字,请填写");
							document.getElementById("col.format").focus();
							return false;
						}
						if(document.getElementById("col.format").value!="" && document.getElementById("col.format").value.length>2000){
							alert("设置字符串必须长度不能超过2000,请填写");
							document.getElementById("col.format").focus();
							return false;
						}
					}else if(ra_val == "number"){
						if(document.getElementById("col.format").value!=""){
							alert("设置数字的时候,不能填写");
							document.getElementById("col.format").focus();
							return false;
						}
					}else if(ra_val == "date"){
						if(document.getElementById("col.format").value!=""){
							alert("设置时间格式不能为空,请填写,格式如'yyyy-MM-dd'");
							document.getElementById("col.format").focus();
							return false;
						}
					}
				}
			}
			
		</script>
	</HEAD>
	<script type="text/javascript">
  </script>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td valign="middle" class="tablequiz">
					<ul class="nav">
						<li>
							<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
								<wysLib:Navigation ivalue="自定义字段添加" />
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
		<form action="addUserDemoColumn.action" method="post" name="column_info" onSubmit="return check();">
			<input type="hidden" name="col.type" value = 3 />
		<div style="margin-top: 0px; text-align: center;">
			<table cellpadding='1' cellspacing='1' width='1000'>
				<tr>
					<td align=center>字段类型</td>
					<td align=center>自定义字段</td>
				</tr>
				<tr>
					<td align=center>列名</td>
					<td align=center><input type="text" name="col.column_name" id="col.column_name" /></td>
				</tr>
				<tr>
					<td align=center>描述</td>
					<td align=center><input type="text" name="col.description"  /></td>
				</tr>
				<tr>
					<td align=center>字段类型</td>
					<td align=center>
						字符串:<input type="radio" name="col.column_type" value="varchar2" checked />
						数字:<input type="radio" name="col.column_type" value="number"  />
						时间:<input type="radio" name="col.column_type" value="date" onclick="select(3);"  />
						<div id="more">
							字符串长度设置或时间格式设置：<input type="text" name="col.format" id="col.format"  />
						</div>
					</td>
				</tr>
				<tr>
					<td align=center>字段类型</td>
					<td align=center>
						<select name="col.show_page_type" onchange="this.value=this.options[this.selectedIndex].value;">
							<option value=0 selected>文本框</option>
							<option value=1>单选</option>
							<option value=2>复选</option>
							<option value=3>下拉选项</option>
							<option value=4>大文本</option>
							<option value=5>数字</option>
							<option value=6>图片</option>
							<option value=7>附件</option>
							<option value=8>编辑器</option>
							<option value=9>视频</option>
						</select>
					</td>
				</tr>
				<tr>
					<td align=center>添加页是否显示</td>
					<td align=center>
						是：<input type="radio" name="col.show_add" value=1 checked />
						否：<input type="radio" name="col.show_add" value=0 />
					</td>
				</tr>
				<tr>
					<td align=center>修改页是否显示</td>
					<td align=center>
						是：<input type="radio" name="col.show_update" value=1 checked />
						否：<input type="radio" name="col.show_update" value=0 />
					</td>
				</tr>
				<tr>
					<td align=center>查看页是否显示</td>
					<td align=center>
						是：<input type="radio" name="col.show_view" value=1 checked />
						否：<input type="radio" name="col.show_view" value=0 />
					</td>
				</tr>
				<tr>
					<td align=center>注册页是否显示</td>
					<td align=center>
						是：<input type="radio" name="col.show_register" value=1 checked />
						否：<input type="radio" name="col.show_register" value=0 />
					</td>
				</tr>
				<tr>
					<td align=center>个人修改页是否显示</td>
					<td align=center>
						是：<input type="radio" name="col.show_user_update" value=1 checked />
						否：<input type="radio" name="col.show_user_update" value=0 />
					</td>
				</tr>
				<tr>
					<td align=center>个人查看页是否显示</td>
					<td align=center>
						是：<input type="radio" name="col.show_user_view" value=1 checked />
						否：<input type="radio" name="col.show_user_view" value=0 />
					</td>
				</tr>
				<tr>
					<td align=center>列表页是否显示</td>
					<td align=center>
						是：<input type="radio" name="col.show_list" value=1 checked />
						否：<input type="radio" name="col.show_list" value=0 />
					</td>
				</tr>
				<tr>
					<td align=center>是否必填</td>
					<td align=center>
						是：<input type="radio" name="col.need" value=1 checked />
						否：<input type="radio" name="col.need" value=0 />
					</td>
				</tr>
			</table>
		</div>
		<div style="margin-top: 0px; text-align: center;">
			<input name="submit" type="submit" value="确认添加" class=textbg6 />
		</div>
		</form>
	
	</body>
</html>