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
		<script type="text/javascript" src="js/forum.js"></script>
		<script type="text/javascript">
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
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="添加版面" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">添加新版面</span>
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
		
		<form action="forum_block_add.action" method="post"
			onsubmit="return doSubmit();">
			<table width="100%" align="center" cellpadding="5" cellspacing="1">
				<tr>
					<td width="150" height="40" align="right">
						<span class="neededitem">*</span>名称：					</td>
					<td height="40" align="left">
						<input name="fblock.title" size="30" type="text" id="fblockTitle">
				  </td>
				</tr>
				<tr>
					<td width="150" align="right">
						描述：					</td>
					<td align="left">
						<textarea rows="6" cols="40" name="fblock.description"></textarea>
					</td>
				</tr>
				<tr>
					<td width="150" height="40" align="right">
						<span class="neededitem">*</span>版面类别：					</td>
					<td height="40" align="left">
						<select name="fblock.fbtype.id">
							<s:iterator value="fbtypes">
								<option value="<s:property value="id"/>">
									<s:property value="name" />
								</option>
							</s:iterator>
						</select>
				  </td>
				</tr>
				<tr>
					<td width="150" height="40" align="right">
						<span class="neededitem">*</span>可使用会员级别：					</td>
					<td align="left" valign="middle">
						<s:checkboxlist  id="luntanjibiecheckbox" 
									  list="luntanjibies" name="fblock.luntanjibies" listKey="id" listValue="basevalue" theme="simple" />
									  <br>
									  <input type="button" value="全选" class="textbg6" onClick="select_all();"/>
				  <input type="button" value="全不选" class="textbg6" onClick="select_all_no();"/>				  </td>
				</tr>
				<!--<tr>
				<td align="center" >
					版主
				</td>
				<td align="center" >
				<span id="realname" style="width:200px;"></span>
					<input type="hidden" id="userid" name="fblock.manager.id" value="0"/> <a href="javascript:searchUsers();">选择</a>
				</td>
			</tr>-->
				<tr>
					<td colspan="2" height="40px" align="center">
						<input type="submit" class="textbg4" value="提交" />
						<input type="button" class="textbg4"
							onclick="document.location='forum_block_list.action';" value="取消" />

					</td>
				</tr>
		  </table>
		</form>
	
	</body>
</HTML>
