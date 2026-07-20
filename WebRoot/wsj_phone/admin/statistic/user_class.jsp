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
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="培训班列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">学员培训班学习情况一览表</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<table width="100%" align="center" cellpadding="2" cellspacing="1">
				<tr>
					<td width="200" height="30" rowspan=2 align="center" bgcolor="#86D8FF" >
						培训班名称					</td>
					<td width="150" height="30" rowspan=2 align="center" bgcolor="#86D8FF" >
						创建者					</td>
					<td height="30" colspan=2 align="center" bgcolor="#86D8FF" >
						必修课（数量）					</td>
					<td height="30" colspan=2 align="center" bgcolor="#86D8FF" >
						选修课（学分）					</td>
					<td width="100" height="30" rowspan=2 align="center" bgcolor="#86D8FF" >
						加入时间					</td>
					<td width="100" height="30" rowspan=2 align="center" bgcolor="#86D8FF" >
						结业时间					</td>
					<td width="100" height="30" rowspan=2 align="center" bgcolor="#86D8FF" >
						学习轨迹					</td>

				</tr>
				<tr>
					<td width="60" height="30" align="center" bgcolor="#86D8FF" >
						要求					</td>
					<td width="60" height="30" align="center" bgcolor="#86D8FF" >
						已修					</td>
					<td width="60" height="30" align="center" bgcolor="#86D8FF" >
						要求					</td>
					<td width="60" height="30" align="center" bgcolor="#86D8FF" >
						已修					</td>
				</tr>
				<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
				<s:iterator value="myClasses">
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
							<s:property value="elClass.name" />
						</td>
						<td height="30" align="center" >
							<s:property value="elClass.creater.realname" />
						</td>
						<td height="30" align="center" >
							<s:property value="elClass.bxCount" />
						</td>
						<td height="30" align="center" >
								<s:property value="bxCount" />
						</td>
						<td height="30" align="center" >
							<s:property value="elClass.optionalcredit" />
						</td>
						<td height="30" align="center" >
							<s:property value="xxCredit" />
						</td>
						<td height="30" align="center" >
							<s:date name="begintime" format="yyyy-MM-dd"/>
						</td>
						<td height="30" align="center" >
							 	<s:date name="endtime" format="yyyy-MM-dd"/>
								<!--(<s:property value="statusName"/>)-->
						</td>
						<td align="center" >
							<a href="statisticStudyLearnLocus.action?elUser.id=<s:property value="elUser.id" />&course.classid=<s:property value="elClass.id" />" class="textbg4">查看</a>
						</td>
					</tr>
				</s:iterator>  </tbody>
		  </table>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
