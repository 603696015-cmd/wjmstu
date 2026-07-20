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
		<TITLE>中国食品安全培训网--管理端--</TITLE>
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
			ptypeid = <s:property value="ptype.id"/> ;
			$.post("newstype_delete_user.action", {
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
				alert("新闻栏目名称不能为空!");
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="保单列表修改" /></div>
			</li> 
		</ul>
		<s:form action="policyLib_alter" method="post" name="catalog_info"
			theme="simple" onsubmit="return doSubmit();">
			<input type="hidden" value="0" name="ptype.isshared" />
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
									<wysLib:policyTypeSelect  selectid="${ptype.parent.id}" rootAble="true" ></wysLib:policyTypeSelect>
								</select>
							</label>
						</s:else>
					</td>
				</tr>
				<%-- 
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
								
								<s:if test="#session.userId==1">
								 <a style="cursor: hand; float: right; width: 14px; height: 14px;"
									href=""
									onclick="javascript:deleteUserinfo(this,<s:property value="id"/>,'NEWSTYPE_OP_TYPE');return false;">X</a>
								</s:if>
								<s:else>
									 <a style="cursor: hand; float: right; width: 14px; height: 14px;"
									href="javascript:alert('您没有这个权限，请与系统管理员联系!!!');" >X</a>
								</s:else>
								</span>
							</s:iterator>
							</div>
							<s:if test="#session.userId==1">
							<a href=""
								onclick="searchUserInit('can_op','ptype.opusers.id'); return false;">授权</a>
								</s:if>
								<s:else>
							<a href="javascript:alert('您没有这个权限，请与系统管理员联系!!!');">授权</a>
								</s:else>
						</td>
			  </tr>
						<tr>
						<td width="120" align="center" >
							可使用人员：
						</td>
						<td >
							<div id="can_use">
							<s:iterator value="ptype.useusers">
								<span
									style="height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
									<label style="float: left;">
										<s:property value="realname" />
								</label> <span class="STYLE1">＊</span> 
								<s:if test="#session.userId==1">
								<a
									style="cursor: hand; float: right; width: 14px; height: 14px;"
									href=""
									onclick="javascript:deleteUserinfo(this,<s:property value="id"/>,'NEWSTYPE_USE_TYPE');return false;">X</a>
								</s:if>
								<s:else>
									 <a style="cursor: hand; float: right; width: 14px; height: 14px;"
									href="javascript:alert('您没有这个权限，请与系统管理员联系!!!');" >X</a>
								</s:else>
								</span>
							</s:iterator>
							</div>
							<s:if test="#session.userId==1">
							<a href=""
								onclick="searchUserInit('can_use','ptype.useusers.id'); return false;">授权</a>
							</s:if>
							<s:else>
							<a href="javascript:alert('您没有这个权限，请与系统管理员联系!!!');">授权</a>
								</s:else>
						</td>
					</tr>
					 --%>
					<%-- 
				<tr>
					<td width="120" height="30" align="center" >
						是否共享节点
					</td>
					<td >
						<label>
						<s:select list="#{0:'不共享',1:'共享'}"  name="ptype.isshared" id="ptype.isshared" ></s:select>
						</label>
					</td>
				</tr>
				--%>
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
