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
		<link rel="stylesheet" type="text/css" href="css/system003.css" />
		<link rel="stylesheet" type="text/css" href="css/manage003.css" />
        <link href="css/listlable.css" type="text/css" rel="stylesheet">
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%; background-color:#F8FCFE;}
</style>
		<ul class="nav">
			<li>
				<div style="color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="交流文章列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">我的帖子</span>
			</li>-->
		</ul>
		<form action="forum_list_byblockid.action" name="flbform" method="post">
		<div style="width: 100%; height:30px; line-height:30px; text-align: center;  padding-top:7px;background-color:#F8FCFE; border:1px solid #C1EBFF;">
			<s:hidden name="pN" id="pageNow"></s:hidden>
			<s:hidden name="pS"></s:hidden>
			<input type="text" name="forum.title" value="<s:property value="forum.title"/>">
			<input type="button" onClick="fsearch();" value="搜索" class="textbg4">
		</div>
		<s:if test="forums.size==0">
			<div style="width: 100%; text-align: center; margin-top: 30px;">
				<span style="color:red;">您还没有文章</span>
			</div>
		</s:if>
		<s:else>
		
			<table width="100%" align="center" cellpadding="2" cellspacing="1" style="background-color:#D1E4F5;">
				<tr>
					<th>
						名称
					</th>
					<th>
						版块
					</th>
					<th>
						发布时间
					</th>
					<th>
						修改时间
					</th>
					<th>
						回帖数
					</th>
					<th>
						浏览数
					</th>
						<th>
					</th>
				</tr>
				<s:iterator value="forums">
					<tr>
						<td height="30" align="left" width="360" style="padding-left:8px;color:blue;">
							<img src="images/iconred.gif" width="4" height="6" /> &nbsp;&nbsp;[<s:property value="hotName"/>]<s:property value="title" />
						</td>
						<td align="center" >
							<s:property value="fblock.title" />
						</td>
						<td align="center" >
							<s:date name="createtime" format="yyyy-MM-dd" />
						</td>
						<td align="center" >
							<s:date name="modifytime" format="yyyy-MM-dd" />
						</td>
						<td align="center" >
							<s:property value="receipttime" />
						</td>
						<td align="center" >
							<s:property value="readtime" />
						</td>
						<td align="center" >
						<a href="forum_alterInit.action?forum.id=<s:property value="id"/>">修改</a>
						<a href="forum_deletebyuid.action?forum.id=<s:property value="id"/>" onclick="return confirm('确定删除？')">删除</a>
						</td>
					</tr>
				</s:iterator>
			</table>
			<div style="width: 100%; text-align: center; margin-top: 10px;">
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
