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
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/libutil.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
					<script type="text/javascript">
		function deleteUserinfo(obj,id,optype){
			if(window.confirm("确定删除？")){
			ntypeid = <s:property value="ptype.id"/> ;
			$.post("productType_delete_user.action", {
				"elUser.id":id,
				"ptype.id":ptypeid,
				"optype":optype, 
				"x":Math.random
				}, 
				function (data) {
					alert('删除成功');
				});
			obj.parentNode.parentNode.removeChild(obj.parentNode);
			}
		}
		function doSubmit(){
			var title=document.getElementById("ptypeName");
			title=title.value.replace(/(\s*$)/g, "");
			if(title==""){
				alert("所属栏目名称不能为空!");
				return false;
			}
			return true;
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="产品所属栏目修改" /></div>
			</li>
		</ul>
		<s:form action="productType_alter.action" method="post" name="catalog_info"
			theme="simple" onsubmit="return doSubmit();">
			<!-- <input type="hidden" value="0" name="ntype.isshared" /> -->
			<table width="100%" cellpadding="2" cellspacing="1" >
				<tr>
					<td width="120" height="30" align="center" >
						栏目名称
					</td>
					<td >
						<label>
							<s:textfield name="ptype.name" id="ptypeName" size="60" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="center" >
						栏目介绍
					</td>
					<td >
						<label>
							<s:textarea name="ptype.description" cols="60" rows="7" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="center" >
						上级栏目
					</td>
					<td >
						<s:if test="ptype.parent.id==0">
							根节点没有父节点
							<s:hidden name="ptype.parent.id" />
						</s:if>
						<s:else>
							<label>
								<select name="ptype.parent.id" id="parentid">
									<wysLib:productTypeSelect  selectid="${ptype.parent.id}" rootAble="true" ></wysLib:productTypeSelect>
								</select>
							</label>
						</s:else>
					</td>
				</tr>
				<tr>
					<td width="120" height="50" align="center" >&nbsp;
						
					</td>
					<td >
						<s:hidden name="ptype.id"></s:hidden>
						<input type="submit" value="确认修改">
					</td>
				</tr>
		  </table>
			<br>
		</s:form>
	
	</body>
</HTML>
