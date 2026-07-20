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
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
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
	</HEAD>
	<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="回复列表页" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">审核文章</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<div style="width: 100%; text-align: center; margin-top: 3px;">
			
		</div>
		<table width="100%" cellpadding="1" cellspacing="1">
			<tr>
				<td width="200px;" valign="top" id="tree_list_td">
					<s:if test="fbtypes.size==0">
						<div style="width: 100%; text-align: center; margin-top: 3px;">
							当前没有版面类别
						</div>
					</s:if>
					<s:else>
						<s:set name="fbtsize" value="fbtypes.size"></s:set>
						<table width="100%" align="center" cellpadding="1" cellspacing="1">
							<tr>
								<td height="30" align="left"
									style="padding-left: 8px; color: blue;">
									<!--<a
											href="forum_list_byblockid.action?forum.title=&pN=0&pS=10&fblock.id=0">全部</a>
									-->
									<a href="TopicList.action?pN=0&pS=10">全部</a>
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
												href="TopicList.action?fblock.id=<s:property value="id"/>&pN=0&pS=10">
												<s:property value="title" /> </a>
										</td>
									</tr>
								</s:iterator>
							</s:iterator>
						</table>
					</s:else>
				</td>
				<td valign="top" id="tree_list_td">

					<form action="upTopicValid.action" method="post" name="topicForm">
						<s:hidden name="topic.id" id="topicId" />
						<s:hidden name="topicOp" id="topicOp" />
						<s:hidden name="topic.forum.id" id="forumId" />
						<s:hidden name="topic.forum.creater.id" id="createrid"/>
						<s:hidden name="topic.forum.title" id="forumTitle" />
					</form>

					<form action="forum_list_byblockid.action" name="flbform"
						method="post">
						<s:hidden name="pN" id="pageNow"></s:hidden>
						<s:hidden name="pS"></s:hidden>
						<s:if test="topics.size==0">
							<div style="width: 100%; text-align: center; margin-top: 30px;">
								您还没有回复
							</div>
						</s:if>
						<s:else>
							<table width="100%" align="center" cellpadding="2"
								cellspacing="1">
								<tr>
									<th width="160px">
										帖子名称
									</th>
									<th width="200px">
										回复内容
									</th>
									<th width="100">
										回复者
									</th>
									<th width="110">
										回复时间
									</th>
									<th width="80">
										回复状态
									</th>
									<th width="140"></th>
								</tr>
								<tbody onMouseOut="changeback()" onMouseOver="changeto()">
									<s:iterator value="topics">
										<tr>
											<td height="30" align="left"
												style="padding-left: 8px; color: blue;">
												<s:property value="forum.title" />
											</td>
											<td align="center">
												<s:property value="content" />
											</td>
											<td width="100" align="center">
												<s:property value="creater.realname" />
											</td>
											<td width="110" align="center">
												<s:date name="createtime" format="yyyy-MM-dd hh:mm" />
											</td>
											<td width="80" align="center">
												<s:if test="valid==0">
													审核中
												</s:if>
												<s:if test="valid==1">
													已发布
												</s:if>
												<s:if test="valid==2">
													删除等待中
												</s:if>
											</td>
											<td width="140" align="center">
												<a target="blank"
													href="forumView.action?forum.id=<s:property value="forum.id"/>&pN=0&pS=10"
													class="textbg4">预览</a>
												<s:if test="valid==1">
													<a
														href="javascript:void(0);"
														class="textbg4" onClick="return doSubmit_3('<s:property value="id"/>','<s:property value="pN"/>','<s:property value="pS"/>',3);">删除</a>
												</s:if>
												<s:if test="valid==0||valid==2">
													<a
														href="javascript:void(0);" onClick="return doSubmit_4('<s:property value="forum.id"/>','<s:property value="id"/>','<s:property value="pN"/>','<s:property value="pS"/>',2,'<s:property value="forum.creater.id"/>','<s:property value="forum.title"/>');"
														class="textbg4">通过</a>
													<a
														href="javascript:void(0);" onClick="return doSubmit_4('<s:property value="forum.id"/>','<s:property value="id"/>','<s:property value="pN"/>','<s:property value="pS"/>',3,'<s:property value="forum.creater.id"/>','<s:property value="forum.title"/>');"
														class="textbg4">删除</a>
												</s:if>
											</td>
										</tr>
									</s:iterator>
								</tbody>
							</table>
							<div style="width: 100%; text-align: center; margin-top: 10px;">
								<wysLib:page></wysLib:page>
							</div>
						</s:else>
					</form>
				</td>
			</tr>
		</table>
		<script type="text/javascript">
			function page(i){
				document.getElementById("pageNow").value=i;
				flbform.action="TopicList.action";
				flbform.submit();
			}
			function fsearch( ){
				flbform.action="forum_list_byuid.action";
				document.getElementById("pageNow").value=0;
				flbform.submit();
			}
		</script>
	</body>
</HTML>
