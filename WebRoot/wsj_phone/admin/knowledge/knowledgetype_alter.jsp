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
		<TITLE>课程类别管理</TITLE>
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
	background-color: expression((       this .       sectionRowIndex %       2 ==       0)
		?   
		   "#ffffff" :       "#f4f4f4" )
}
</style>
		<script type="text/javascript" src="js/forum.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/libutil.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
		function deleteUserinfo(obj,id,optype){
			if(window.confirm("确定删除？")){
			ctypeid = <s:property value="kltype.id"/> ;
			$.post("knowledgetype_delete_user.action", {
				"elUser.id":id,
				"kltype.id":ctypeid,
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
			var titleObj=document.getElementById("klibName");
			var title=titleObj.value.replace(/(\s*$)/g, "");
			if(title==""){
				alert("知识类别名称不能为空!");
				titleObj.focus();
				return false;
			}
			var kparid="<s:property value="kltype.parent.id" />";
			var kid="<s:property value="kltype.id" />";
			if(kparid==0&&kid!=1){
				alert("请选择上级类别!");
				return false;
			}
			return true;
		}
		</script>
		<style type="text/css">
<!--
.STYLE1 {
	color: #FF0000
}
-->
</style>
	</HEAD>
	<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="知识类别修改" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">知识库修改</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="knowledgetype_list.action">知识库管理</a>
			</li>
			<li>
				<a style="cursor: hand" 
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="knowledgetype_deleteInit.action?kltype.id=<s:property value='kltype.id'/>"/>知识库删除</a>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<s:form action="knowledgetype_alter" method="post" name="course_info"
				theme="simple" id="course_info" onsubmit="return doSubmit();">
				<input type="hidden" value="0" name="kltype.isshared" />
				<table width="600" cellpadding="1" cellspacing="1">
					<tr>
						<td width="160" height="30" align="right">
							<span class="neededitem">*</span>知识类别名称：
							<s:hidden name="kltype.id"></s:hidden>
						</td>
						<td>
							<label>
								<s:textfield name="kltype.name" id="klibName" size="60" />
							</label>
						</td>
					</tr>
					<tr>
						<td height="30" align="right">
							知识类别介绍：
						</td>
						<td>
							<label>
								<s:textarea name="kltype.description" cols="60" rows="7"></s:textarea>
							</label>
						</td>
					</tr>

					<tr>
						<td height="30" align="right">
							<span class="neededitem">*</span>上级类别：
						</td>
						<td>
							<label>
								<s:if test="kltype.parent.id==0">
									根节点没有父节点
									<s:hidden name="kltype.parent.id" />
								</s:if>
								<s:else>
									<select name="kltype.parent.id" id="catalog">
										<wysLib:kltype_select selectid="${kltype.parent.id}"></wysLib:kltype_select>
									</select>
								</s:else>
							</label>
						</td>
					</tr>
					<!--<tr>
						<td width="120" align="center" >
							可管理人员：
						</td>
						<td >
							<div id="can_op">
							<s:iterator value="kltype.opusers">
								<span
									style="height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
									<label style="float: left;">
										<s:property value="realname" />
								</label> <span class="STYLE1">＊</span>
								<s:if test="#session.userId==1">
								 <a
									style="cursor: hand; float: right; width: 14px; height: 14px;"
									href=""
									onclick="javascript:deleteUserinfo(this,<s:property value="id"/>,'knowledge_op_type');return false;">X</a>
									
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
								onclick="searchUserInit('can_op','kltype.opusers.id'); return false;">授权</a>
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
							<s:iterator value="kltype.useusers">
								<span
									style="height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
									<label style="float: left;">
										<s:property value="realname" />
								</label> <span class="STYLE1">＊</span> 
								<!--<s:if test="#session.userId==1">
								<a
									style="cursor: hand; float: right; width: 14px; height: 14px;"
									href=""
									onclick="javascript:deleteUserinfo(this,<s:property value="id"/>,'knowledge_use_type');return false;">X</a>
								</s:if>
								<s:else>
									 <a style="cursor: hand; float: right; width: 14px; height: 14px;"
									href="javascript:alert('您没有这个权限，请与系统管理员联系!!!');" >X</a>
								</s:else>-- 
								</span>
							</s:iterator>
							</div>
							<!--<s:if test="#session.roleid==1">
							<a href=""
								onclick="searchUserInit('can_use','kltype.useusers.id'); return false;">授权</a>
							</s:if>
							<s:else>
							<a href="javascript:alert('您没有这个权限，请与系统管理员联系!!!');">授权</a>
								</s:else>
						</td>
					</tr>-->
					<%-- 
					<tr>
					<td width="120" height="30" align="center" >
						是否共享节点
					</td>
					<td >
						<label>
						<s:select list="#{0:'不共享',1:'共享'}"  name="kltype.isshared" id="kltype.isshared" ></s:select>
						</label>
					</td>
				</tr>
				--%>

					<!--  tr>
				<td align="center" >
					类别管理员
				</td>
				<td align="center" >
				<span id="realname" style="width:200px;"><s:property value="kltype.manager.realname"/></span>
					<input type="hidden" id="userid" name="kltype.manager.id" value="${kltype.manager.id }"/> <a href="javascript:searchUsers();">选择</a>
				</td>
			</tr-->
					<!--<tr>
						<td width="160" height="30" align="center" >
							能使用该类别的部门：
						</td>
						<td >
							<label>
								wysLib:kltypedep_listwysLib:kltypedep_list 
							</label>
						</td>
					</tr>

					-->
					<tr>
						<td></td>
						<td height="50" align="left">
							<input type="hidden" id="userid" name="kltype.manager.id"
								value="${kltype.manager.id }" />
							<input name="submit" type="submit" class="textbg6" value="确认修改" />
							&nbsp;&nbsp;&nbsp;&nbsp;

							<a
								href="knowledgetype_deleteInit.action?kltype.id=<s:property value='kltype.id'/>"
								class=textbg6>删除</a>&nbsp;&nbsp;&nbsp;&nbsp;
							<a style="width: 110px;" href="knowledgetype_list.action"
								class=textbg6>返回知识类别</a>
								
							<a  style="width: 110px;" href="myknowledge_list.action?kltype.id=<s:property value='kltype.id'/>" class="textbg6">知识文章列表</a>
								
								

						</td>
					</tr>
				</table>
			</s:form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
