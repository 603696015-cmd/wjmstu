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
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<TITLE>中国食品安全培训网--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system003.css" />
		<link rel="stylesheet" type="text/css" href="css/manage003.css" />
        <link href="css/listlable.css" type="text/css" rel="stylesheet">
		<script type="text/javascript" src="js/message.js"></script>
	    <style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
        </style>
</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%;background-color:#F8FCFE;}
</style>
		<form action="upTopicValid.action" method="post" name="topicForm">
			<s:hidden name="topic.id" id="topicId"/>
			<s:hidden name="topicOp"  id="topicOp"/>
		</form>
<!--		<ul class="nav">-->
<!--			<li>-->
<!--				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="回复列表" /></div>-->
<!--			</li>-->
<!--			<li>-->
<!--				<span style="font-weight: bold;">我的帖子</span>-->
<!--			</li>-->
<!--		</ul>-->
		<form action="myTopicList.action" name="flbform" method="post">
		<s:hidden name="pN" id="pageNow"></s:hidden>
		<s:hidden name="pS"></s:hidden>
		<s:if test="topics.size==0">
			<div class="STYLE1" style="width: 100%; height:40px; line-height:40px; text-align: center; margin-top: 30px;background-color:#F4F4F4; border:1px solid #C1EBFF;">
				您还没有回复</div>
		</s:if>
		<s:else>
			<table width="100%" align="center" cellpadding="2" cellspacing="1" style="background-color:#D1E4F5;" >
				<tr>
					<th bgcolor="#F8FCFE">
						回复内容
				  </th>
					<th bgcolor="#F8FCFE">
						回复者
				  </th>
					<th bgcolor="#F8FCFE">
						回复时间
				  </th>
					<th bgcolor="#F8FCFE">
						查看
				  </th>
				</tr>
				<s:iterator value="topics">
					<tr>
						<td height="30" align="center"  width="300"style="padding-left:8px;color:blue;">
							<s:property value="content" />
						</td>
						<td align="center" >
							<s:property value="creater.realname" />
						</td>
						<td align="center" >
							<s:date name="createtime" format="yyyy-MM-dd hh:mm" />
						</td>
						<td align="center" >
							<a target="blank" href="forumView.action?forum.id=<s:property value="id"/>&pN=0&pS=10">查看</a>
						</td>
					</tr>
				</s:iterator>
		  </table>
			<div style="width: 100%; text-align: center; margin-top: 10px;">
			  <wysLib:page_cisco></wysLib:page_cisco>
<!--				<wysLib:page></wysLib:page>-->
			</div>
			
		</s:else>
		</form>
		<script type="text/javascript">
			function page(i){
				document.getElementById("pageNow").value=i;
				flbform.action="myTopicList.action";
				flbform.submit();
			}
			function fsearch( ){
				flbform.action="myTopicList.action";
				document.getElementById("pageNow").value=0;
				flbform.submit();
			}
		</script>
	</body>
</HTML>
