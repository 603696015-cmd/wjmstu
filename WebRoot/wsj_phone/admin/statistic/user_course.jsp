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
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="课程列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">学员课程学习情况一览表</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<s:if test="myCourses.size==0"><span style="color:red;"><table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="50%" align="right"><img src="http://ico.ooopic.com/iconset01/ose/gif/60790.gif"/></td>
    <td width="50%" height="100"><font size="+1" color="#FF0000">当前用户没有需要学习的课程!</font></td>
  </tr>
</table>
</span></s:if>
			<s:else>
				<table width="100%" align="center" cellpadding="1" cellspacing="1">
					<caption>
						学员课程列表
					</caption>
					<tr>
						<td width="200" height="30" align="center" >
							课程名称						</td>
						<td width="150" height="30" align="center" >
							创建者						</td>
						<td width="150" height="30" align="center" >
							所在培训班						</td>
						<td width="120" height="30" align="center" >
							课程讲师						</td>
						<!-- <td height="30" align="center" >
							课程学分
						</td>
						<td height="30" align="center" >
							学习类型
						</td> -->
						<td width="180" height="30" align="center" >
							时长/已学						</td>
						<td width="100" height="30" align="center" >
							学习进度						</td>
						
						<!---  2011.12.07 当前公安版本不用此功能
						<td height="30" align="center" >
							已获学分
						</td>
						-->
						<td width="50" height="30" align="center" >
							成绩						</td>
					</tr>
					<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
					<s:iterator value="myCourses">
						<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
								<s:property value="course.name" />
						  </td>
							<td height="30" align="center" >
								<s:property value="course.creater.realname" />
							</td>
							<td height="30" align="center" >
								<s:property value="course.className" />
							</td>
							<td height="30" align="center" >
								<s:property value="course.teacherName" />
							</td>
							<!-- <td height="30" align="center" >
								<s:property value="course.credit" />
							</td>
							<td height="30" align="center" >
								<s:property
										value="statusName" />
							</td> -->
							<td height="30" align="center" >
								<s:property value="course.during" />
								分钟 /
								<s:property value="passtime" />
								分钟（<s:property value="processStr" />%）
							</td>
							<td height="30" align="left" >
								<div style="border: 1px dotted #FF6633;"> <IMG height=14 
                  src="images/jd.gif" width="<s:property value="processStr" />%"></div>
						  </td>
							<!-- 2011.12.07 当前公安版本不用此功能
							<td height="30" align="center" >
								<s:property value="myCredit" />
							</td>
							-->
							<td height="30" align="center" >
								<s:property value="myExamPaper.myScore" />
								<!-- 2011.12.07 当前公安版本不用此功能
								/<s:property value="myExamPaper.ispassed" />
								/<s:property value="myExamPaper.id" />
								-->
							</td>
						</tr>
					</s:iterator>  </tbody>
			  </table>
			</s:else>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
