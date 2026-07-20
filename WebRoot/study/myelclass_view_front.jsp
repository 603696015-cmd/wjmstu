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
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>个人中心培训学心详情页</title>
		<meta name="keywords" content="OA,OA办公系统,OA系统" />
		<meta name="description"
			content="通达OA系统代表了协同OA的先进理念,是中国用户群最广泛的OA软件,协同OA软件行业唯一央企团队研发,多次摘取国内OA软件金奖,拥有300万终端OA用户,十年研发铸就成熟OA产品" />
		
		<link href="css/style2013.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript">
		function disNopassInfo(classid){
				width=420;
				height=360;
			   	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				window.showModalDialog("classNoPassRemack.action?elclass.id="+classid+"&x="+Math.random(),null,sFeature);
			}
			
		</script>

		<style type="text/css">
<!--
.STYLE3 {
	color: #FFFFFF
}
-->
</style>
	</HEAD>
	<body>
		<!--<table width="960" border="0" align="center" cellpadding="0"
				cellspacing="0" style="margin-top: 15px;">
				<tr>
					<td height="40" background="images/bg002.jpg"
						style="padding-left: 30px;">
						<span class="yellow">1.在线学习</span>
					</td>
					<td width="300" align="right" background="images/bg002.jpg"
						style="padding-right: 10px;">
						应完成<s:property value="map.zong_xueshi" />学时，已完成<s:property value="map.learned_xueshi" />学时，学习总进度:
					</td>
					<td width="183" background="images/bg002.jpg">
						<div
							style="width: 170px; BORDER-BOTTOM: #ff6633 1px dotted; BORDER-LEFT: #ff6633 1px dotted; BORDER-TOP: #ff6633 1px dotted; BORDER-RIGHT: #ff6633 1px dotted">
							<img src="images/jd.gif" width="<s:property value="map.process" />%" height="14" />
						</div>
					</td>
				</tr>
			</table>
			-->
			<table width="800" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="50">&nbsp;</td>
  </tr>
</table>

			<table width="900" border="0" align="center" cellpadding="5"
				cellspacing="1" bgcolor="#CFDBE2">
				<tr>
					<td height="50" align="center" valign="middle" bgcolor="#EBF7FC">&nbsp;
						
					</td>
					<td align="center" valign="middle" bgcolor="#EBF7FC">
					课程名称</td>
					<td width="110" align="center" bgcolor="#EBF7FC">
						时长/完成
					</td>
					<td width="60" align="center" bgcolor="#EBF7FC">
						进 度
					</td>
					<td height="40" align="center" bgcolor="#EBF7FC">
						进度条
					</td>
					<td align="center" bgcolor="#EBF7FC">
						学习
					</td>
				</tr>
				<s:iterator value="studyCourseList">
				<tr>
					<td width="30" height="50" align="center" valign="middle"
						bgcolor="#F8FCFE">
						<img src="images/iconred.gif" width="4" height="6" />
					</td>
					<td valign="middle" bgcolor="#F8FCFE">
						<a href="javascript:void(0);" style="padding-left: 15px;" class="zc01"><s:property value="course.name" /></a>
					</td>
					<td width="110" align="center" bgcolor="#F8FCFE">
						<s:property value="course.during" />分钟/<s:property value="passtime" />分钟
					</td>
					<td width="60" align="center" bgcolor="#F8FCFE">
						<s:property value="process" />%
					</td>
					<td width="120" height="40" align="left" bgcolor="#F8FCFE">
						<div
							style="BORDER-BOTTOM: #ff6633 1px dotted; BORDER-LEFT: #ff6633 1px dotted; BORDER-TOP: #ff6633 1px dotted; BORDER-RIGHT: #ff6633 1px dotted">
							<img src="images/jd.gif" width="<s:property value="process" />%" height="14" />
						</div>
					</td>
					<td width="60" align="center" bgcolor="#F8FCFE">
						<a href="course_study.action?course.id=<s:property value="course.id" />&coursePage.id=<s:property value="firstCpid" />&classid=<s:property value="course.classid" />" target="_blank">
							<img src="images/xtb021.gif" width="28" height="27" />
						</a>
					</td>
				</tr>
				</s:iterator>
	</table>
			
			<p>&nbsp;
				
			</p>
			<p>&nbsp;
				
			</p><p>&nbsp;
				
			</p>
			<p>&nbsp;
				
			</p><p>&nbsp;
				
			</p>
			<p>&nbsp;
				
			</p><p>&nbsp;
				
			</p>
			<p>&nbsp;
				
			</p>
	</body>
</html>
