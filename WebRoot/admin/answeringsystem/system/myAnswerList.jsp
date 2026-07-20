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
		<TITLE>五矿发展员工职业发展系统--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
	    <style type="text/css">
<!--
.STYLE1 {
	color: #FF0000;
	font-size: 18px;
}
-->
        </style>
</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="回复列表" /></div>
			</li>
		</ul>
		<form action="" name="answers" method="post">
		<s:if test="answers.size==0">
			<div class="STYLE1" style="width: 100%;height:30px; line-height:30px; text-align: center; margin-top: 30px; background:#F4F4F4; border:1px solid #D1E4F5;">				您还没有回复!			</div>
		</s:if>
		<s:else>
			<table width="100%" align="center" cellpadding="2" cellspacing="1" >
				<tr>
					<th>
						问题标题
					</th>
					<th>
						回复内容
					</th>
					<th>
						回复者
					</th>
					<th>
						回复时间
					</th>
					<th>
						回复状态
					</th>
					<th></th>
				</tr>
				<s:iterator value="answers">
					<tr>
						<td height="30" align="left" style="padding-left:8px;color:blue;">
							<s:if test="ques.name.length()>=20">
								<s:property value="ques.name.substring(0,20)" />...
							</s:if>
							<s:else>
								<s:property value="ques.name" />
							</s:else>
						</td>
						<td align="center" >
							${answerContent }
						</td>
						<td align="center" >
							<s:property value="answerUser.realname" />
						</td>
						<td align="center" >
							<s:date name="answerTime" format="yyyy-MM-dd HH:mm" />
						</td>
						<td align="center" >
							<s:property value="status_" />
						</td>
						<td align="center" >
						<a target="_blank" href="ques_index_view.action?ques.id=<s:property value="ques.id"/>">预览</a>
						</td>
					</tr>
				</s:iterator>
		  </table>
			<div style="width: 100%; text-align: center; margin-top: 10px;">
				<wysLib:page></wysLib:page>
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
