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
		<script type="text/javascript" src="js/offline.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
			function myload(){
			<s:iterator value="elUsers">
			addUserinfo(<s:property value="id"/>) ;
			</s:iterator>
			}
			var offid = <s:property value="offline.id"/>;
			function deleteUserinfo(obj,id,optype){
			if(window.confirm("确定删除？")){
			$.post( 
				"offline_deleteuser.action", {
				"elUser.id":id, 
				"offline.id":offid, 
				"x":Math.random
				}, 
				function (data) {
					alert('删除成功');
				});
			obj.parentNode.parentNode.removeChild(obj.parentNode);
			}
		}
		</script>
	</HEAD>
	<BODY onLoad="myload();">
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
				<span style="font-weight: bold;">活动查看</span>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px">
				<div id="message" style="text-align: center;"><s:property value="elmessage" /></div>
				<table cellspacing=1 cellpadding=2 width="700px;" align=center>
					<tbody>
						<tr>
							<td height="30" align=center>
								活动名称							</td>
							<td align=center colspan="3">
								<s:property value="offline.name" />
						  </td>
						</tr>
						<tr>
							<td height="30" align=center>
								活动简介							</td>
							<td align=center colspan="3">
								<s:property value="offline.description" />
						  </td>
						</tr>
						<tr>
							<td height="30" align=center>
								开始时间							</td>
							<td align=center>
								<s:date name="offline.begintime" format="yyyy-MM-dd HH:mm:ss" />
						  </td>
							<td align=center>
								结束时间							</td>
							<td align=center>
								<s:date name="offline.endtime" format="yyyy-MM-dd HH:mm:ss" />
						  </td>
						</tr>
						<tr>
							<td height="30" align=center>
								时长							</td>
							<td align=center colspan="3">
								<s:property value="offline.during" />
						  </td>
						</tr>
						<tr>
							<td height="30" align=center>
								学时							</td>
							<td align=center colspan="3">
								<s:property value="offline.xueshi" />
						  </td>
						</tr>
						<tr>
							<td height="30" align=center>
								学分							</td>
							<td align=center colspan="3">
								<s:property value="offline.score" />
						  </td>
						</tr>
						<tr>
							<td width="100px" height="30" align=center>
								参与人员							</td>
							<td align=center colspan="3">
							<div id="d_userlist"></div>
						  </td>
						</tr>
					</tbody>
		  </table>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
