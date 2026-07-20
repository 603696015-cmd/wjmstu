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
		<script type="text/javascript" src="js/forum.js"></script>
		<script type="text/javascript">
			function doSubmit(){
				var titleObj=document.getElementById("klibName");
				var title=titleObj.value.replace(/(\s*$)/g, "");
				if(title==""){
					alert("知识类别名称不能为空!");
					titleObj.focus();
					return false;
				}
				return true;
			}
		</script>
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
	</HEAD>
	<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="知识类别" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">知识库添加</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="knowledgetype_list.action">知识库管理</a>
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
			<s:form action="knowledgetype_add" method="post" name="course_info"
				theme="simple" id="course_info" onsubmit="return doSubmit();">
				<input type="hidden" value="0" name="kltype.isshared" />
				<table cellpadding="1" width="100%" cellspacing="1">
					<tr>
						<td width="160" height="30" align="right">
							<span class="neededitem">*</span>知识类别名称：
						</td>
						<td>
							&nbsp;<label>
								<input name="kltype.name" type="text" id="klibName" value=""
									size="60">
							</label>
						</td>
					</tr>
					<tr>
						<td height="30" align="right">
							知识类别介绍：
						</td>
						<td>
							&nbsp;<label>
								<textarea name="kltype.description" cols="60" rows="7"></textarea>
							</label>
						</td>
					</tr>

					<tr>
						<td height="30" align="right">
							<span class="neededitem">*</span>上级类别：
						</td>
						<td>
							&nbsp;<label>
								<select name="kltype.parent.id" id="catalog">
									<wysLib:kltype_select selectid="${kltype.parent.id}"></wysLib:kltype_select>
								</select>
							</label>
						</td>
					</tr>
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
					<!-- tr>
				<td align="center" >
					类别管理员
				</td>
				<td align="center" >
				<span id="realname" style="width:200px;"></span>
					<input type="hidden" id="userid" name="kltype.manager.id" value="0"/> <a href="javascript:searchUsers();">选择</a>
				</td>
			</tr-->
					<tr>
						<td height="50" align="center">
						</td>
						<td>
							&nbsp;<input type="hidden" id="userid" name="kltype.manager.id"
								value="0" />
							<input name="submit" type="submit" class="textbg6" value="确认添加" />
							<a href="knowledgetype_list.action" class=textbg6>取消</a>
						</td>
					</tr>
				</table>
			</s:form>
		</div>
		<!-- 内容 -->

	
	</body>
</HTML>
