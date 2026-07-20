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
				var bh=document.getElementById("klTree.bh");
				if(bh.value==""){
					alert("请填写自定义标签类别编号！");
					$("#klTree.bh").focus();
					return false;
				}
				var ts=/^[\d]{1,}$/;
				if(!ts.test(bh.value)){
					alert("自定义标签类别编号只能是数字！");
					$("#klTree.bh").focus();
					return false;
				}
				var depId=$("input[name='klTree.parent.id']:checked").val();
				if(depId==undefined){
					alert("请选择上级自定义标签类别！");
					return false;
				}
				return window.confirm("确定信息填写无误？");
			}
		</script>
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
	</head>
	<body>

		<!-- 页面 -->
		<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="" />
				</div>
			</li>
		</ul>
		<s:form action="addLableTree.action" method="post" theme="simple"
			name="lableTree_info" id="lableTree_info"
			onsubmit="return _onsubmit();">
			<table style="margin-top: 3px;" border="0" align="left"
				cellpadding="1" cellspacing="1" width="600px" bgcolor="#EBEBEB">
				<tr>
					<td width="120" height="30" align="right">
						<span class="neededitem">*</span>自定义标签类别名称：
					</td>
					<td>
						<label>
							<s:textfield id="klTree.name" name="klTree.name" size="60" />
						</label>
					</td>
				</tr>
				<tr>
					<td height="80" align="right">
						详细说明：
					</td>
					<td>
						<label>
							<s:textarea name="klTree.description" cols="45" rows="5" />
						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="right">
						<span class="neededitem">*</span>上级类别：
					</td>
					<td>
						<label>
						<wysLib:lableTree_list_aj rootAble="true" iname="klTree.parent.id" itype="ra"></wysLib:lableTree_list_aj>
							<script type="text/javascript">
							w0.setValues([new DEP(<s:property value="klTree.id"/>,<s:property value="klTree.lid"/>,<s:property value="klTree.rid"/>)]);
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
							<s:textfield name="klTree.bh" id="lTree.bh" />
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
							<input type="button" class="textbg4" style="width:100px;" onclick="document.location='list_lableTree.action'" value="返回列表" />
					</td>
				</tr>
			</table>
		</s:form>
	</body>
</html>