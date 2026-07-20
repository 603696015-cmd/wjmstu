<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.pfms.entities.ProductType"%>
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
		<s:form action="productType_add" method="post" name="catalog_info"
			theme="simple" onsubmit="return doSubmit();">
			<!-- <input type="hidden" value="0" name="ptype.isshared" /> -->
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
						上级类别<%
						ProductType uu = ((ProductType) request
										.getAttribute("ptype"));
								String xx = "0";
								xx = uu == null ? "1" : uu.getParent() == null ? "1" : uu
										.getParent().getId()
										+ "";
						%>
					</td>
					<td >
						<label>
							<wysLib:productTypeTree did="0" iname="ptype.parent.id"
									itype="ra_2no" ivalue="<%=xx%>" iid="${ptype.id}" ></wysLib:productTypeTree>
						</label>
					</td>
				</tr>
				<!-- <tr>
					<td width="120" height="30" align="center" >
						上级栏目
					</td>
					<td >
						<label>
							<select name="ptype.parent.id" id="parentid">
								<wysLib:productTypeSelect rootAble="true"></wysLib:productTypeSelect>
							</select>
						</label>
					</td>
				</tr> -->
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
