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
			ntypeid = <s:property value="ntype.id"/> ;
			$.post("newstype_delete_user.action", {
				"elUser.id":id,
				"ntype.id":ntypeid,
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
			var title=document.getElementById("ntypeName");
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
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="新闻栏目修改" /></div>
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
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<s:form action="newstype_alter" method="post" name="catalog_info"
			theme="simple" onsubmit="return doSubmit();">
			<input type="hidden" value="0" name="ntype.isshared" />
			<table width="600" cellpadding="2" cellspacing="1" >
				<tr>
					<td width="120" height="30" align="right" >
						<span class="neededitem">*</span>栏目名称：
					</td>
					<td >
						<label>
							<s:textfield name="ntype.name" id="ntypeName" size="60" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="right" >
						栏目介绍：
					</td>
					<td >
						<label>
							<s:textarea name="ntype.description" cols="60" rows="7" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="right" >
						<span class="neededitem">*</span>上级栏目：
					</td>
					<td >
						<s:if test="ntype.parent.id==0">
							根节点没有父节点
							<s:hidden name="ntype.parent.id" />
						</s:if>
						<s:else>
							<label>
								<select name="ntype.parent.id" id="parentid">
									<wysLib:newsTypeSelect  selectid="${ntype.parent.id}" rootAble="true" ></wysLib:newsTypeSelect>
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
							<s:iterator value="ntype.opusers">
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
								onclick="searchUserInit('can_op','ntype.opusers.id'); return false;">授权</a>
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
							<s:iterator value="ntype.useusers">
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
								onclick="searchUserInit('can_use','ntype.useusers.id'); return false;">授权</a>
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
						<s:select list="#{0:'不共享',1:'共享'}"  name="ntype.isshared" id="ntype.isshared" ></s:select>
						</label>
					</td>
				</tr>
				--%>
				<tr>
					<td width="120" height="50" align="center" >&nbsp;
						
					</td>
					<td >
						<s:hidden name="ntype.id"></s:hidden>
						<input type="submit" class="textbg4" style="width:80px" value="确认修改">
						<input class="textbg6" type="button" onClick="document.location='newstype_view.action?ntype.id=<s:property value="ntype.id"/>'" value="取消">
					</td>
				</tr>
		  </table>
			<br>
		</s:form>
	
	</body>
</HTML>
