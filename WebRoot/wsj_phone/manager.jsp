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
		<TITLE>快速通道</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/manage2.css" />
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<script type="text/javascript" src="js/message.js"></script>
	</HEAD>
	<BODY>
		<ul class="nav" style="text-align: left;">
			<li>
				<span style="font-weight: bold;">快速通道</span>
			</li>
		</ul>
		<TABLE style="margin-top:20px;" width="95%" border=0 align=center class="splittd">
			<TR>
				<!--<TD align=center width="10%">
					<IMG src="images/rz.gif">				</TD>
				<TD width="40%" height=60 align="left" valign="top">
					<DIV class=contenttitle>
						<A href="dep_list.action">部门管理</A>
					</DIV>
					<DIV class=Contenttips>
						<SPAN>添加、删除、修改部门信息，指定管理员</SPAN>
					</DIV>
				</TD>
				--><TD align=center width="10%">
					<IMG src="images/rj.gif" width="44" height="40">				</TD>
				<TD width="40%" height=60 align="left" valign="top">
					<DIV class=contenttitle>
						<A href="account_searchInit.action">用户管理</A>
					</DIV>
					<DIV class=Contenttips>
						<SPAN>添加、删除、修改本部门个人学员，设定初始密码</SPAN>
					</DIV>
				</TD>
				<TD align=center width="10%">
					<IMG src="images/tg.gif">				</TD>
				<TD width="40%" height=60 align="left" valign="top">
					<DIV class=contenttitle>
						<A href="quiz_searchlist.action?examRoom.eroomLib.id=1&sub_department=1&department.id=1">考试统计</A>					</DIV>
					<DIV class=Contenttips>各考场成绩概况、分数段分布状况、成绩详情</DIV>
				</TD>
			</TR>
	</TABLE>
		<TABLE width="95%" border=0 align=center class="splittd">
			<TR>
				<TD align=center width="10%">
					<IMG src="images/tp.gif" width="44" height="40">				</TD>
				<TD width="40%" height=60 align="left" valign="top">
					<DIV class=contenttitle>
						<A href="question_listInit.action?null">试题管理</A>					</DIV>
					<DIV class=Contenttips>
						<SPAN>试题的增删改，试题批量导入，试题库导出</SPAN>
					</DIV>
				</TD>
				<TD align=center width="10%">
					<IMG src="images/rz.gif">				</TD>
				<TD width="40%" height=60 align="left" valign="top">
					<DIV class=contenttitle>
						<A href="exampaper_listInit.action?null">试卷管理</A>					</DIV>
					<DIV class=Contenttips>
					试卷的增删改，修改试卷评分规则与抽题规则</DIV>
				</TD>
			</TR>
	</TABLE>
		<TABLE width="95%" border=0 align=center class="splittd">
			<TR>
				<TD align=center width="10%">
					<IMG src="images/tg.gif">				</TD>
				<TD width="40%" height=60 align="left" valign="top">
					<DIV class=contenttitle>
						<A href="examprac_list.action?null">练习管理</A>					</DIV>
					<DIV class=Contenttips>
						<SPAN>练习试卷、</SPAN>
					练习人员、练习时间段的设置</DIV>
				</TD>
				<TD align=center width="10%">
					<IMG src="images/tg.gif">				</TD>
				<TD width="40%" height=60 align="left" valign="top">
					<DIV class=contenttitle>
						<A href="examroomwithoutcourse_list.action?null">考场管理</A>					</DIV>
					<DIV class=Contenttips>
						<SPAN>考场增删改、复核、审核，挑试卷、安排人员</SPAN>					</DIV>
				</TD>
			</TR>
	</TABLE><!--
		<TABLE width="95%" border=0 align=center class="splittd">
			<TR>
				<TD align=center width="10%">
					<IMG src="images/tg.gif">				</TD>
				<TD width="40%" height=60 align="left" valign="top">
					<DIV class=contenttitle>
						<A href="course_searchInit.action">课程统计</A>
					</DIV>
					<DIV class=Contenttips>
						<SPAN>单门课程学员情况、学习进度、考试情况分项统计</SPAN>
					</DIV>
				</TD>
				<TD align=center width="10%">
					<IMG src="images/tg.gif">				</TD>
				<TD width="40%" height=60 align="left" valign="top">
					<DIV class=contenttitle>
						<A href="sim_searchInit.action">模拟考试统计</A>
					</DIV>
					<DIV class=Contenttips>
						<SPAN>单个模拟考试成绩概况、分数段分布、成绩详情等</SPAN>
					</DIV>
				</TD>
			</TR>
	</TABLE>

	-->
	</body>
</html>
