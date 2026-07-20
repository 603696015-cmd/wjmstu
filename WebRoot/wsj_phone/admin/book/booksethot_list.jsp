<%@ page language="java" pageEncoding="UTF-8"   %>
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
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<li>
				<span style="font-weight: bold;">图书推荐</span>
			</li>
		</ul> 
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		

		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<!--<table width="100%">
			<tr>
				<td valign="top" width="200" id="tree_list_td"> 	 
		</td>
				<td valign="middle" width="5px;" style="padding: 0px" >
							<img src="images/leftmenu/main_55.gif" style="cursor: hand"
										onclick="changeTreeDisplay(this)" />
					</td>
				<td valign="top">	-->
			<s:if test="bookes.size==0">没有符合条件的图书</s:if>
			<s:else>
				<table width="100%" align="center" cellpadding="2" cellspacing="2"
					bgcolor="#EBEBEB">
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
							图书名称
						</td>
						<td height="30" align="center" >
							创建者
						</td>
						<td height="30" align="center" >
							发布时间
						</td>
						<td height="30" align="center" >
							公告栏目
						</td>
						<td >
						</td>
					</tr>
					<s:iterator value="books">
						<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
								<s:property value="title" />
							</td>
							<td height="30" align="center" >
								我
							</td>
							<td height="30" align="center" >
								<s:date name="releasetime" format="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td height="30" align="center" >
								<s:property value="ntype.name" />
							</td>
							<td bgcolor="#FFFFFF" align="center">
								<s:if test="hot==0">
									<a href="booksethot.action?book.id=<s:property value="id"/>&book.hot=1&pN=${pN}&pS=${pS}">推荐</a>
								</s:if>
								<s:if test="hot==1">
									<a href="booksethot.action?book.id=<s:property value="id"/>&book.hot=0&pN=${pN}&pS=${pS}">取消</a>
								</s:if>
							</td>
						</tr>
					</s:iterator>
				</table>
			</s:else>
			<!--</td>
			</tr>
		</table>
		-->
			<form action="booksethot_list.action" method="post" name="nlist">
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
