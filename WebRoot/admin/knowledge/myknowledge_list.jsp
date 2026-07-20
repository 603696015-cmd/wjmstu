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
		<link rel="stylesheet" type="text/css" href="css/system01.css" />
		<link rel="stylesheet" type="text/css" href="css/manage01.css" />
        <link href="css/listlable.css" type="text/css" rel="stylesheet">
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((   this .   sectionRowIndex %   2 ==   0)
		? 
				 "#ffffff" :   "#f4f4f4" )
}
</style>
		<script>
			function page(i){
				//document.location.href="myknowledge_list.action?pS=<s:property value="pS"/>&pN="+i
				document.getElementById("pageNow").value=i;
				document.klform.submit();
			}
		</script>
	</HEAD>
	<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="知识文章列表" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">我的知识</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="knowledge_addInit.action">添加知识</a>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top:0px; text-align: center;">
			<table width="100%" cellpadding="1" cellspacing="1" >
				<tr>
					<td valign="top" id="tree_list_td" width="200px"> 
						<%
							String url = "myknowledge_list.action?subdep=1&kltype.id=";
						%>
						<wysLib:kltype_list href="<%=url%>" itype="001" rootAble="true"></wysLib:kltype_list>
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
					</td>
					<td valign="top">
						<form action="myknowledge_list.action" name="klform" method="post" style="margin-top:8px; margin-left:8px;">
							<s:hidden name="pN" id="pageNow"></s:hidden>
							<s:hidden name="pS"></s:hidden>
							<s:hidden name="kltype.id"></s:hidden>
							知识名称 :
							<input type="text" name="knowledge.title"
								value="<s:property value="knowledge.title" />">
								
							<input type="submit"
								onClick="document.getElementById('pageNow').value=0;" class="textbg4" value="搜索">
								
							<input type="button" 
							 	onClick="document.location='knowledgetype_list.action'"   style="width:80px;" class="textbg4" value="资源类别">
								
						</form>
						<s:if test="knowledges.size!=0">
							<table width="100%" align="center" cellpadding="1"
								cellspacing="1" bgcolor="#CFDBE2">
								<tr>
									<th height="30" align="center">
										知识名称
									</th>
									<th width="110" height="30" align="center">
										创建时间
									</th>
									<!--<th height="30" align="center" >
						修改时间
					</th>-->
									<th width="150" height="30" align="center">
										知识类别
									</th>
									<th width="50">
									</th>
									<th width="50">
									</th>
								</tr>
								<tbody onMouseOut="changeback()" onMouseOver="changeto()">
									<s:iterator value="knowledges">
										<tr>
											<td width="200" height="30"
												align="left" style="padding-left: 8px; color: blue;">
												<img src="images/iconred.gif" width="4" height="6" /> &nbsp;&nbsp;<s:property value="title" />
											</td>
											<td width="100" height="30" align="center">
												<s:date name="createtime" format="yyyy-MM-dd HH:mm:ss" />
											</td>
											<!--<td height="30" align="center" >
							<s:date name="modifytime" format="yyyy-MM-dd HH:mm:ss" />
						</td>-->
											<td width="150" height="30" align="center">
												<s:property value="kltype.name" />
											</td>
											<td width="40" align="center">
												<a class="textbg4"
													href="knowledge_alterInit.action?knowledge.id=<s:property value="id"/>">编辑</a>
											</td>
											<td width="40" align="center">
												<a onClick="return confirm('确定删除？')"
													href="knowledge_delete.action?knowledge.id=<s:property value="id"/>&pN=
							<s:property value="pN"/>&pS=<s:property value="pS"/>" class="textbg4">删除</a>
											</td>
										</tr>
									</s:iterator>
								</tbody>
							</table>
							<wysLib:page_cisco></wysLib:page_cisco>
						</s:if>
						<s:else>
				您还没创建知识呢！
			</s:else>
											
					</td>
				</tr>
				</table>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
