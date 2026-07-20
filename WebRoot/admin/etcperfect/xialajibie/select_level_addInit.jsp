<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>五矿发展员工职业发展系统--管理端--</title>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
			function _onsubmit(){
				if($("#selectLevel.name").val()==''){
					alert("请填写下拉选项名称！");
					$("#selectLevel.name").focus();
					return false;
				}
				var bh=document.getElementById("selectLevel.bh");
				if(bh.value==""){
					alert("请填写部门编号！");
					$("#selectLevel.bh").focus();
					return false;
				}
				var ts=/^[\d]{1,}$/;
				if(!ts.test(bh.value)){
					alert("下拉选项编号只能是数字！");
					$("#selectLevel.bh").focus();
					return false;
				}
				var depId=$("input[name='selectLevel.parent.id']:checked").val();
				if(depId==undefined){
					alert("请选择上级部门！");
					return false;
				}
				return window.confirm("确定信息填写无误？");
			}
		</script>
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
	</HEAD>
	<body>

		<!-- 页面 -->
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
		
		<s:form action="select_level_add.action" method="post" theme="simple"
			name="department_info" id="department_info"
			onsubmit="return _onsubmit();">
			<table style="margin-top: 3px;" border="0" align="left"
				cellpadding="1" cellspacing="1" width="600px" bgcolor="#EBEBEB">
				<tr>
					<td width="120" height="30" align="right">
						<span class="neededitem">*</span>下拉选项：
					</td>
					<td>
						<label>
							<s:textfield id="selectLevel.name" name="selectLevel.name" size="60" />
						</label>
					</td>
				</tr>
				<tr>
					<td height="80" align="right">
						详细说明：
					</td>
					<td>
						<label>
							<s:textarea name="selectLevel.description" cols="45" rows="5" />
						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="right">
						<span class="neededitem">*</span>上级下拉选项：
					</td>
					<td>
						<label>
						<wysLib:select_list_aj rootAble="true" iname="selectLevel.parent.id" itype="ra"></wysLib:select_list_aj>
							<script type="text/javascript">
							w0.setValues([new DEP(<s:property value="selectLevel.id"/>,<s:property value="selectLevel.lid"/>,<s:property value="selectLevel.rid"/>)]);
						</script>	
						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="right">
						<span class="neededitem">*</span>编号：
					</td>
					<td>
						<label>
							<s:textfield name="selectLevel.bh" id="selectLevel.bh" />
							<span style="color: red;"><s:property value="elmessage" />
							</span>
						</label>
					</td>
				</tr>
				<tr>
					<td height="50" align="center">
					</td>
					<td>
						<input name="submit" type="submit" class="textbg4" value="添加" />
							<input type="button" class="textbg4" style="width:100px;" onclick="document.location='list_selectLevel.action'" value="返回列表" />
					</td>
				</tr>
			</table>
		</s:form>
	</body>
</html>