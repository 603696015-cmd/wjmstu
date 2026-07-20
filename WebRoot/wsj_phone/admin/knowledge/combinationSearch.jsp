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
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
</style>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script>
		 	function page(i){
		 		document.getElementById("pageNow").value=i;
		 		klform.submit();
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
		<div style="margin-top: 0px; text-align: center;">
			<table width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<td valign="top" width="200px;" id="tree_list_td">
						<%
							String url = "combinationSearch.action?kltype.id=";
						%>
						<wysLib:kltype_list href="<%=url%>" itype="001" rootAble="true"></wysLib:kltype_list>
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
					</td>
					<td valign="top">
						<s:if test="knowledges.size!=0">
							<table width="100%" align="center" cellpadding="2"
								cellspacing="1">
								<tr>
									<th height="30" align="center">
										知识名称
									</th>
									<th width="70" height="30" align="center">
										创建者
									</th>
									<th width="110" height="30" align="center">
										创建时间
									</th>
									<!--
									<th height="30" align="center" >
										修改时间
									</th>
									-->
									<th width="150" height="30" align="center">
										知识类别
									</th>
									<th width="40">
									</th>
									<th width="40">
									</th>
								</tr>
								<tbody onMouseOut="changeback()" onMouseOver="changeto()">
									<s:iterator value="knowledges">
										<tr>
											<td height="30" style="padding-left: 8px; color: blue;"
												align="left">
												<s:property value="title" />
											</td>
											<td width="70" height="30" align="center">
												<s:property value="owner.realname" />
											</td>
											<td width="110" height="30" align="center">
												<s:date name="createtime" format="yyyy-MM-dd HH:mm:ss" />
											</td>
											<!--
										<td height="30" align="center" >
											<s:date name="modifytime" format="yyyy-MM-dd HH:mm:ss" />
										</td>
										-->
											<td width="150" height="30" align="center">
												<s:property value="kltype.name" />
											</td>
											<td width="40" align="center" bgcolor="#FFFFFF">
												<a
													href="knowledge_alterInit.action?knowledge.id=<s:property value="id"/>" class="textbg4">编辑</a>
											</td>
											<td width="40" align="center" bgcolor="#FFFFFF">
												<a onClick="return confirm('确定删除？')"
													href="knowledge_delete.action?knowledge.id=<s:property value="id"/>&pN=
											<s:property value="pN"/>&pS=<s:property value="pS"/>" class="textbg4">删除</a>
											</td>
										</tr>
									</s:iterator>
								</tbody>
							</table>
							<form action="combinationSearch.action" name="klform"
								method="post">
								<s:hidden name="pN" id="pageNow"></s:hidden>
								<s:hidden name="pS"></s:hidden>

							</form>
							<wysLib:page></wysLib:page>
						</s:if>
						<s:else>
							无搜索结果！
						</s:else><br/>
						<a href="combinationSearchknowledgeInit.action" class="textbg4" style="width:80px">返回</a>
					</td>
				</tr>
			</table>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
