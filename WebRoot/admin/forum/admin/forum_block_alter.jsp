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
		<TITLE>五矿发展员工职业发展系统--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/forum.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/libutil.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
			function deleteUserinfo(obj,id,optype){
				if(window.confirm("确定删除？")){
				fblockid = <s:property value="fblock.id"/> ;
				$.post("fblock_delete_user.action", {
					"elUser.id":id,
					"fblock.id":fblockid,
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
				var name=document.getElementById("fblockTitle").value;
				if(name==""){
					alert("请输入版面名称！");
					return false;
				}
			}
			
			function select_all(){
				var luntanjibies = document.getElementsByName("fblock.luntanjibies");
				for(var i=0;i<luntanjibies.length;i++){
					luntanjibies[i].checked = true;
				}
			}
			
			function select_all_no(){
				var luntanjibies = document.getElementsByName("fblock.luntanjibies");
				for(var i=0;i<luntanjibies.length;i++){
					luntanjibies[i].checked = false;
				}
			}
		</script>
	</HEAD>
	<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="版块信息修改" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">编辑版面</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="forum_block_list.action">版面列表</a>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<form action="forum_block_alter.action" method="post" onSubmit="return doSubmit();">
		<input type="hidden" value="0" name="fblock.isshared" />
		<input type="hidden" name="fblock.manager.id" value="<s:property value='fblock.manager.id'/>"/>
		<table width="56%" align="center" cellpadding="1" cellspacing="1">
			<tr>
				<td align="right" >
					<span class="neededitem">*</span>名称：
				</td>
				<td align="left" >
					<input name="fblock.title" size="30" value="<s:property value="fblock.title"/>" type="text" id="fblockTitle">
				</td>
			</tr>
			<tr>
				<td align="right" >
					描述：
				</td>
				<td align="left" >
					<textarea rows="6" cols="40" name="fblock.description"><s:property value="fblock.description"/></textarea>
				</td>
			</tr>
			<tr>
				<td align="right" >
					<span class="neededitem">*</span>版面类别：
				</td>
				<td align="left" >
					<select name="fblock.fbtype.id" >
						<s:iterator value="fbtypes">
							<option <s:if test="id==fblock.fbtype.id">selected='selected'</s:if> value="<s:property value="id"/>"><s:property value="name"/></option>
						</s:iterator>
					</select>
				</td>
			</tr>
			<tr>
				<td align="right">
					<span class="neededitem">*</span>可使用会员级别：
				</td>
				<td align="left">
					<s:checkboxlist value="#request.checked" id="luntanjibiecheckbox" 
								  list="luntanjibies" name="fblock.luntanjibies" listKey="id" listValue="basevalue" theme="simple" />
								  <br>
								  <input type="button" value="全选" class="textbg6" onClick="select_all();"/>
								  <input type="button" value="全不选" class="textbg6" onClick="select_all_no();"/>
				</td>
			</tr>
			<!--<tr>
				<td align="center" >
					版主
				</td>
				<td align="center" >
				<span id="realname" style="width:200px;"><s:property value="fblock.manager.realname"/></span>
					<input type="hidden" id="userid" name="fblock.manager.id" value="<s:property value="fblock.manager.id"/>"/><a href="javascript:searchUsers();">选择</a>
				</td>
			</tr>-->
			
			
			<tr>
					<td align="right">
						可使用人员：
					</td>
					<td >
					<div id="can_use">
						<s:iterator value="fblock.useusers">
							<span style="height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
								<label style="float: left;"> 
									<s:property value="realname" />
								</label> <span style="color:red;">＊</span>
								<!--<s:if test="#session.userId==1">
								<a style="cursor: hand; float: right; width: 14px; height: 14px;" href=""
									onclick="javascript:deleteUserinfo(this,<s:property value="id"/>,'FBLOCK_USE_TYPE');return false;">X</a>
								</s:if>
								<s:else>
									 <a style="cursor: hand; float: right; width: 14px; height: 14px;"
									href="javascript:alert('您没有这个权限，请与系统管理员联系!!!');" >X</a>
								</s:else>-->
							</span>
						</s:iterator>
					</div>
					<!--<s:if test="#session.userId==1">
					<a href="" onClick="searchUserInit('can_use','fblock.useusers.id'); return false;">授权</a>
					</s:if>
					<s:else>
					<a href="javascript:alert('您没有这个权限，请与系统管理员联系!!!');">授权</a>
					</s:else>-->
				</td>
			</tr>
			<%-- 
			<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">是否为共享节点</td>
				<td >
					<label> 
						<s:select list="#{0:'不共享',1:'共享'}" name="fblock.isshared"></s:select> 
					</label>
				</td>
			</tr>
			--%>
			
			<tr>
				<td colspan="2" height="40px" align="center" >
					<s:hidden name="fblock.id"></s:hidden>
					<input type="submit" class="textbg4" value="提交"/>
					<input type="button" class="textbg4" onClick="document.location='forum_block_list.action';" value="取消"/>
				</td>
			</tr>
		</table>
		</form>
	</body>
</HTML>
