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
	<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />

		<META http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<TITLE>中国食品安全培训网--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="wsj_phone/css/system003.css" />
		<link rel="stylesheet" type="text/css" href="wsj_phone/css/manage003.css" />
        <link href="wsj_phone/css/listlable.css" type="text/css" rel="stylesheet">
		<script type="text/javascript" src="js/message.js"></script>
	    <style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
        </style>
</HEAD>
	<BODY>
	
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%;background-color:#F8FCFE;}
</style>
		<form action="upTopicValid.action" method="post" name="topicForm">
			<s:hidden name="topic.id" id="topicId"/>
			<s:hidden name="topicOp"  id="topicOp"/>
		</form>
		<!--<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="回复列表" /></div>
			</li>
			<li>
				<span style="font-weight: bold;">我的帖子</span>
			</li>
		</ul>-->
		<form action="forum_list_byblockid.action" name="flbform" method="post">
		<s:if test="topics.size==0">
			<div class="STYLE1" style="width: 320px; height:40px; line-height:40px; text-align: center; margin-top: 30px;background-color:#F4F4F4; border:1px solid #C1EBFF;">
				您还没有回复</div>
		</s:if>
		<s:else>
			<table width="320" border="0" align="left" cellpadding="0" cellspacing="1" style="background-color:#D1E4F5;" >
				<tr>
					<td align="center" bgcolor="#F8FCFE">
						帖子名称
					</td>
					<td align="center" bgcolor="#F8FCFE">
						回复时间
			    </td>
					<td bgcolor="#F8FCFE"></td>
				</tr>
				<s:iterator value="topics">
					<tr>
						<td align="left" style="padding-left:8px;color:blue;">
							<img src="images/iconred.gif" width="4" height="6" /> &nbsp;&nbsp;&nbsp;<s:property value="forum.title" />
						</td>
						<td align="center" >
							<s:date name="createtime" format="yyyy-MM-dd hh:mm" />
						</td>
						<td align="center" >
						<a target="blank" href="forumView.action?forum.id=<s:property value="forum.id"/>&pN=0&pS=10">预览</a>
						<s:if test="valid!=2">
							<a href="javascript:doSubmit_3('<s:property value="id"/>','<s:property value="pN"/>','<s:property value="pS"/>',1);">申请删除</a>
						</s:if>
						</td>
					</tr>
				</s:iterator>
		  </table>
			<div style="width:320px; text-align: center; margin-top: 10px;">
			  <wysLib:page_cisco></wysLib:page_cisco>
		  </div>
			
		</s:else>
		</form>
		<script type="text/javascript">
			function page(i){
				document.getElementById("pageNow").value=i;
				flbform.action="forum_list_byuid.action";
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
