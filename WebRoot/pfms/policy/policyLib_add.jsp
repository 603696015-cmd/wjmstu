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
		<script type="text/javascript">
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
	</HEAD>
	<body>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="填写栏目基本信息" /></div>
			</li> 
		</ul>
		<s:form action="policyLib_add" method="post" name="catalog_info"
			theme="simple" onsubmit="return doSubmit();"> 
			<input type="hidden" value="0" name="ptype.isshared" />
			<table width="60%" cellpadding="2" cellspacing="1" >
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
						<label>
							<select name="ptype.parent.id" id="parentid">
								<wysLib:policyTypeSelect rootAble="true"></wysLib:policyTypeSelect>
							</select>
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" height="50" align="center" >&nbsp;
						
					</td>
					<td >
						<input class="textbg6" type="submit" value="确认添加">
					</td>
				</tr>
			</table>
			<br>
		</s:form>
	</body>
</HTML>
