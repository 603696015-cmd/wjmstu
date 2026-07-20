<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="depSelect" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<title>用户列表</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="csses/member.css">
		<link rel="stylesheet" type="text/css" href="csses/common.css">
		
		<script type="text/javascript">
			function selectAll(){
				var chs = document.getElementsByName("mems.mem_id");
				for(var i =0;i<chs.length;++i)
				{
					chs[i].checked="true";
				}
			}
			function unSelectAll(){
				var chs = document.getElementsByName("mems.mem_id");
				for(var i =0;i<chs.length;++i)
				{
					chs[i].checked="";
				}
			}
			function fanSelect (){
				var chs = document.getElementsByName("mems.mem_id");
				for(var i =0;i<chs.length;++i)
				{
					chs[i].checked=!chs[i].checked;
				}
			}
		</script>
	</HEAD>

	<body style="margin-bottom:100px;">
		<div class="operateMenu">
			<div class="operate1">
				消息群发
			</div>
		</div>
		<div class="operateLine"></div>
		<s:form action="mess_groupsend.action" method="post" theme="simple">
			<table width="70%" align="center" cellpadding="2" cellspacing="2"
				>
				<tr>
					<td height="20" align="center" >
						消息标题
					</td>
					<td height="20" align="left" >
						<input name="mess.mess_title" type="text" id="title" size="40" />
					</td>

				</tr>
				<tr>
					<td height="20" align="center" >
						消息标题
					</td>
					<td height="20" align="left" >
						<textarea name="mess.mess_content" cols="50" rows="10">消息内容</textarea>
					</td>

				</tr>
				<tr>
					<td height="20" align="center" >
						收件人
					</td>
					<td >
						<table width="100%" align="center" border="0" cellpadding="2"
							cellspacing="2" >
							<tr>
								<td height="20" align="center" ></td>
								<td height="20" align="center" >
									用户
								</td>
								<td height="20" align="center" >
									姓名
								</td>
								<td height="20" align="center" >
									编号
								</td>
								<td height="20" align="center" >
									部门
								</td>
								<td height="20" align="center" >
									角色
								</td>
								<td height="20" align="center" >
									电子邮箱
								</td>
							</tr>
							<s:iterator value="mems">
								<tr>
									<td height="16" align="center" >
										<input type="checkbox" name="mems.mem_id"
											value="<s:property value="mem_id" />">
									</td>
									<td height="16" align="center" >
										<s:property value="mem_un" />
									</td>
									<td height="16" align="center" >
										<s:property value="mem_name" />
									</td>
									<td height="16" align="center" >
										<s:property value="mem_no" />
									</td>
									<td height="16" align="center" >
										<s:property value="dep.dep_name" />
									</td>
									<td height="16" align="center" >
									</td>
									<td height="16" align="center" >
										<s:property value="mem_Email" />
									</td>
								</tr>
							</s:iterator>
						</table>
					</td>
				</tr>
				<tr>
					<td height="20" align="center" >
					</td>
					<td height="20" align="left" >
						<input type="button" onclick="selectAll();" value="全选">
						<input type="button" onclick="unSelectAll();"  value="全不选">
						<input type="button" onclick="fanSelect ();"  value="反选">
						<input type="submit" value="发送">
					</td>
				</tr>
			</table>
			<div style="margin-bottom:300px;">&nbsp;</div>
		</s:form>

	
	</body>
</html>
