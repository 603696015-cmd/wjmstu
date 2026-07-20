<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.newsandmess.entities.NewsType"%>
<%@page import="com.sopia.newsandmess.entities.News"%>
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
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="新闻列表页" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">我发布的新闻公告</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="news_addInit.action">新闻公告添加</a>

			</li>-->
		</ul>

		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<table width="100%">
				<tr>
					<td valign="top" width="100" id="tree_list_td">
					<s:if test="#request.csstr!=null">
						<wysLib:newsTypeTree href="combinationSearchNews.action?news.ntype.id="
							rootAble="true"></wysLib:newsTypeTree>
					</s:if>
					<s:else>
						<wysLib:newsTypeTree href="news_list.action?ntype.id="
							rootAble="true"></wysLib:newsTypeTree>
				  </s:else></td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
					</td>
					<td valign="top">
							<form action="news_list.action" method="post">
								<div>
									新闻名称&nbsp;<input type="text" name="news.title" value="<s:property value="news.title"/>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									栏目&nbsp;
									<!--<s:select theme="simple" headerKey="-1" headerValue="全部" list="#request.newsTypeList" listKey="id" listValue="name" name="news.releasetime" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
									<select name="news.ntype.id" id="parentid"> 
										<wysLib:newsTypeSelect></wysLib:newsTypeSelect>
									</select>
									
									创建者&nbsp;<input type="text" name="news.owner.realname" value="<s:property value="news.owner.realname"/>">&nbsp;&nbsp;&nbsp;
									<input type="submit" value="搜索" />
								</div> 
							</form>
						<s:if test="newses.size==0"><h3 align="center" style="margin-top:10px;">没有搜到新闻公告</h3></s:if>
						<s:else>
							<table width="100%" align="center" cellpadding="2"
								cellspacing="2" bgcolor="#EBEBEB">
								<tr>
									<th width="300" height="30" align="center" >
										新闻标题									</th>
									<th width="100" height="30" align="center" >
										创建者									</th>
									<th width="90" height="30" align="center" >
										发布时间									</th>
									<th width="80" height="30" align="center" >
										所属栏目									</th>
									<th width="30" height="30" align="center" >
										状态									</th>
									<th width="50" >									</th>
									<th width="50" >									</th>
									<th width="50" >									</th>
									<th width="50" >									</th>
								</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
								<s:iterator value="newses">
									<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
											<s:property value="title" />
										</td>
										<td height="30" align="center" >
												<s:property value="owner.realname" />
										</td>
										<td height="30" align="center" >
											<s:date name="releasetime" format="yyyy-MM-dd HH:mm" />
										</td>
										<td height="30" align="center" >
											<s:property value="ntype.name" />
										</td>
										<td height="30" align="center" >
											<s:property value="hotName" />
										</td>
										<td bgcolor="#FFFFFF" align="center">
											<a href="news_view.action?news.id=<s:property value="id"/>" class="textbg4">查 看</a>
										</td>
										<td bgcolor="#FFFFFF" align="center">
											<a
												href="news_alterInit.action?news.id=<s:property value="id"/>" class="textbg4">编 辑</a>
										</td>
										<td bgcolor="#FFFFFF" align="center">
											<a onClick="return confirm('确定删除？')"
												href="news_delete.action?news.id=<s:property value="id"/>&pN=<s:property value="pN"/>
											&pS=10" class="textbg4">删除</a>
										</td>
										<td bgcolor="#FFFFFF" align="center">
										    <s:if test="status==1">
										        <!--<a href="update_status.action?news.id=<s:property value="id"/>&news.title=<s:property value="title" />&pN=<s:property value="pN"/>&pS=10&status=2">申请审核</a>
										    -->
										    <a href="news_list.action?pN=0&pS=10&news.id=<s:property value="id"/>&news.title=<s:property value="title" />&status=2&updatestatus=updatestatus">申请审核</a>
										    </s:if>
										     <s:elseif test="status==4">
										        <a href="update_status.action?news.id=<s:property value="id"/>&pN=<s:property value="pN"/>&pS=10&status=2">申请审核(审核不通过)</a>
										    </s:elseif>
											<s:elseif test="status==3">
												已审核
											</s:elseif>
											<s:else>
											   审核中
											</s:else>
										</td>
									</tr>
								</s:iterator></tbody>
							</table>
						</s:else>
					</td>
				</tr>
			</table>
			<form action="news_list.action" method="post" name="nlist">
				<s:hidden name="ntype.id" />
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />

			</form>
			<script type="text/javascript">
			function page(i){
				document.getElementById("pageNow").value=i;
				nlist.submit();
			}
		</script>
			<wysLib:page></wysLib:page>
		</div>
		<!-- 内容 -->

	
	</body>
</HTML>
