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
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="版面总览" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">文章管理</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<form action="forum_list_byblockid.action" name="flbform"
			method="post">
			<div style="width: 100%; text-align: center; margin-top: 3px;">
				<s:hidden name="pN" id="pageNow"></s:hidden>
				<s:hidden name="pS"></s:hidden>
				标题名称：<input type="text" name="forum.title"
					value="<s:property value="forum.title"/>">
				<input type="button" onClick="fsearch();" value="搜索" class="textbg4"/>
			</div>
			<table width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<td width="200px;" valign="top" id="tree_list_td">
						<s:if test="fbtypes.size==0">
							<div style="width: 100%; text-align: center; margin-top: 30px;">
								当前没有版面类别
							</div>
						</s:if>
						<s:else>
							<s:set name="fbtsize" value="fbtypes.size"></s:set>
							<table width="100%" align="center" cellpadding="2"
								cellspacing="1">
								<tr>
									<td height="30" align="left"
										style="padding-left: 8px; color: blue;">
										<a
											href="forum_list_byblockid.action?forum.title=&pN=0&pS=10&fblock.id=0">全部</a>
									</td>
								</tr>
								<s:iterator value="fbtypes" status="fbtst">
									<tr bgcolor="red">
										<th height="30" align="left"
											style="padding-left: 8px; color: blue;">
											<i><b><s:property value="name" /> </b> </i>
										</th>
									</tr>
									<s:set name="fblocksize" value="fblocks.size"></s:set>
									<s:iterator value="fblocks" status="fbs" id="fbsid">
										<tr>
											<td height="30" align="left"
												style="padding-left: 8px; color: blue;">
												<a
													href="forum_list_byblockid.action?forum.title=&pN=0&pS=10&fblock.id=<s:property value="#fbsid.id"/>"><s:property
														value="title" /> </a>
											</td>
										</tr>
									</s:iterator>
								</s:iterator>
							</table>
						</s:else>
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
					</td>
					<td valign="top">
						<s:if test="forums.size==0">
							<div style="width: 100%; text-align: center; margin-top: 30px;">
								没有符合条件的文章
							</div>
						</s:if>
						<s:else>
							<table width="100%" align="center" cellpadding="2"
								cellspacing="1">
								<tr>
									<th width="20">
									</th>
									<th align="center">
										标 题
									</th>
									<th width="120" align="center">
										版 块
									</th>
									<th width="100" align="center">
										发布者
									</th>
									<th width="110" align="center">
										发布时间
									</th>
									<th width="60" align="center">
										回帖数
									</th>
									<th width="40" align="center">
									</th>
								</tr>
								<tbody onMouseOut="changeback()" onMouseOver="changeto()">
									<s:iterator value="forums">
										<tr>
											<td width="20" height="30" align="left"
												style="padding-left: 8px; color: blue;">
												<input type="checkbox" name="forums.id"
													value="<s:property value="id"/>">
											</td>
											<td height="30" align="left"
												style="padding-left: 8px; color: blue;">
												[
												<s:property value="hotName" />
												]
												<s:property value="title" />
											</td>
											<td width="120" align="center">
												<s:property value="fblock.title" />
											</td>
											<td width="100" align="center">
												<s:property value="creater.realname" />
											</td>
											<td width="110" align="center">
												<s:date name="createtime" format="yyyy-MM-dd HH:mm:ss" />
											</td>
											<td width="60" align="center">
												<s:property value="receipttime" />
											</td>
											<td width="40" align="center">
												<a
													href="forumView.action?forum.id=<s:property value="id" />&pN=0&pS=10"
													target="_blank" class="textbg4"> 预览</a>
											</td>
										</tr>
									</s:iterator>
								</tbody>
							</table>
							<div style="width: 100%; text-align: left; margin-top: 10px;">
								<wysLib:page></wysLib:page>
								<br>
								<input type="button" onClick="forum_jhset();"
									style="width: 80px" class="textbg4" value="加为精华">
								<input type="button" onClick="deleteForum();" class="textbg4"
									value="删除">
							</div>

						</s:else>
					</td>
				</tr>

			</table>

		</form>
		<script type="text/javascript">
			function page(i){
				document.getElementById("pageNow").value=i;
				flbform.action="forum_list_byblockid.action";
				flbform.submit();
			}
			
			function deleteForum( ){
				if(window.confirm("确认删除吗？")){
					flbform.action="forum_delete.action";
					flbform.submit();
				}
			}
			function forum_jhset( ){
				flbform.action="forum_jhset.action";
				flbform.submit();
			}
			function fsearch( ){
				flbform.action="forum_list_byblockid.action";
				document.getElementById("pageNow").value=0;
				flbform.submit();
			}
		</script>
	</body>
</HTML>
