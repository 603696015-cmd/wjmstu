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
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
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
				<span style="font-weight: bold;">我的课程练习</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center; border:1px solid #C1EBFF;">
			<div style="width: 100%;text-align: center;border:1px solid #C1EBFF;">
			<s:if test="myCourses.size==0">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="48%" align="right"><img src="images/wrong.gif"/></td>
    <td><span style="color:red;">您当前没有需要学习的课程</span></td>
  </tr>
</table>

            </s:if>
			<s:else>
			</div>
				<table width="100%" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<caption>
						我的课程
					</caption>
					<tr>
						<th width="200" height="30" align="center" >
							课程名称						</th>
						<th width="150" height="30" align="center" >
							创建者						</th>
						<th width="100" height="30" align="center" >
							主讲教师						</th>
						<!--<th height="30" align="center" >
							学习类型
						</th>-->
						<th width="150" height="30" align="center" >
							总时间/已学时间						</th>
						<th width="100" height="30" align="center" >
							学习进度						</th>
						<th width="120" height="30" align="center" >
							练习中心						</th>
					</tr>
					<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
					<s:iterator value="myCourses">
						<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
								<s:property value="course.name" />
							</td>
							<td width="100" height="30" align="center" >
								<s:property value="course.creater.realname" />
						  </td>
							<td width="100" height="30" align="center" >
								<s:property value="course.teacherName" />
						  </td>
							<!--<td height="30" align="center" >
								<s:property value="statusName" />
							</td>-->
							<td width="150" height="30" align="center" >
								<s:property value="course.during" />
								分钟 /
								<s:property value="passtime" />
								分钟（<s:property value="processStr" />%）
						  </td>
							<td width="100" height="30" align="center" >
								<div style="border: 1px dotted #FF6633;"> <IMG height=14 
                  src="images/jd.gif" width="<s:property value="processStr" />%"></div>
						  </td>
							<td width="120" height="30" align="center" >
								<a target="_blank"
									href="practice_listInit.action?course.id=<s:property value="course.id"/>" class="textbg">练  习</a>							</td>
						</tr>
					</s:iterator></tbody>
			  </table>
				<form action="myprac_list.action" name="erform" method="post">
					<s:hidden name="pN" id="pageNow"> 
					</s:hidden>
					<s:hidden name="pS">
					</s:hidden>
				</form>
				<script>
				function page(i){
					document.getElementById("pageNow").value=i;
					erform.submit();
				}
				</script>
				<wysLib:page></wysLib:page>
			</s:else>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
