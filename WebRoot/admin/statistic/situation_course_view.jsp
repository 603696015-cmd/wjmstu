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
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript">
			function updateXX(elclassid,courseid){
				alert(elclassid);
				alert(courseid);
				if(window.confirm("是否选修该课程")){
					updatexx.href="myelclass_view.action?elclass.id="+elclassid+"&course.id="+courseid+"&str=updatexx";
				}
			}
		</script>
		<style type="text/css">
<!--
.STYLE1 {
	color: #FF0000
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="查看详情" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">培训班详情</span>
			</li>
			<li class="sep">
			</li>-->
		</ul>
		<br>
		<table align="center" width="100%" cellpadding="1" cellspacing="1">
			<caption>
				必修课
			</caption>
			<tr>
				<th width="200">
					课程名称
				</th>
				<th width="60">
					创建者
				</th>
				<th width="80">
					开始时间
				</th>
				<th width="80">
					结束时间
				</th>
				<th width="80">

					时长/已学
					<!--/学分-->
				</th>
				<th width="70">
					结业条件
				</th>
				<th width="60">
					课程学分
				</th>
				<th width="60">
					学员人数
				</th>

			</tr>
			<tbody onMouseOut="changeback()" onMouseOver="changeto()">
				<s:set name="btotalscore" value="0f"></s:set>
				<s:set name="btotalcredit" value="0.0f"></s:set>
				<s:iterator value="myClass.myCourseB">
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
							<s:property value="course.name" />
						</td>
						<td width="60">
							<s:property value="course.creater.realname" />
						</td>
						<td width="80">
							<s:date name="course.roomstart" format="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td width="80">
							<s:date name="course.roomend" format="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td width="80" height="30" align="center" >
							<s:property value="course.during" />
							分钟 /
							<s:property value="passtime" />
							分钟
							<!--/
						<s:property value="course.credit" />-->
						</td>
						<td width="70">
							<s:if test="course.getcredit == 1">
								学完						</s:if>
							<s:elseif test="course.getcredit == 2">
								考过						</s:elseif>
							<s:elseif test="course.getcredit == 3">
								学完且考过						</s:elseif>
							<s:else>
								学完						</s:else>
						</td>
						<td width="60">
							<s:property value="course.setcredit" />
						</td>
						<td width="60">
							<a href="#">查看</a>
						</td>
					</tr>
				</s:iterator>
				<tr>
					<td width="200" height="30" align="center" >
						合计
					</td>
					<td height="30" colspan="14" align="center" >
						总学分
						<s:property value="#btotalcredit" />
						<span class="STYLE1"> ** </span>我的学分
						<s:property value="#btotalscore" />
					</td>
				</tr>
			</tbody>
		</table>
		<Br>
		<table align="center" width="100%" cellpadding="1" cellspacing="1">
			<caption>
				选修课
			</caption>
			<tr>
				<th width="200">
					课程名称
				</th>
				<th width="60">
					创建者
				</th>
				<th width="80">
					开始时间
				</th>
				<th width="80">
					结束时间
				</th>
				<th width="80">

					时长/已学
					<!--/学分-->
				</th>
				<th width="70">
					结业条件
				</th>
				<th width="60">
					课程学分
				</th>
				<th width="60">
					学员人数
				</th>

			</tr>
			<tbody onMouseOut="changeback()" onMouseOver="changeto()">
				<s:set name="xtotalscore" value="0"></s:set>
				<s:set name="xtotalcredit" value="0"></s:set>
				<s:iterator value="myClass.myCourseX">
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
							<s:property value="course.name" />
						</td>
						<td width="60">
							<s:property value="course.creater.realname" />
						</td>
						<td width="80">
							<s:date name="course.roomstart" format="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td width="80">
							<s:date name="course.roomend" format="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td width="80" height="30" align="center" >
							<s:property value="course.during" />
							分钟 /
							<s:property value="passtime" />
							分钟
							<!--/
						<s:property value="course.credit" />-->
						</td>
						<td width="70">
							<s:if test="course.getcredit == 1">
								学完						</s:if>
							<s:elseif test="course.getcredit == 2">
								考过						</s:elseif>
							<s:elseif test="course.getcredit == 3">
								学完且考过						</s:elseif>
							<s:else>
								学完						</s:else>
						</td>
						<td width="60">
							<s:property value="course.setcredit" />
						</td>
						<td width="60">
							<a href="#">查看</a>
						</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
	</body>
</HTML>
