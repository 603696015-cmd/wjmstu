<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.courseman.entities.Course"%>
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
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((   this .   sectionRowIndex %   2 ==   0)
		? 
		 "#ffffff" :   "#f4f4f4" )
}
</style>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="学员列表" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">课程学员列表</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="course_stat_view.action?course.id=<s:property value="course.id"/>">基本信息</a>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center; ">
			<s:form action="course_user_list" method="post" theme="simple"
				name="eee">
				<input type="hidden" name="pN" value="0" id="pageNow2">
				<input type="hidden" name="pS" value="10">
				<s:hidden name="course.id"></s:hidden>
						姓名：
						<s:textfield name="elUser.realname"></s:textfield>
						与岗位关系：<select name="sta">
									<option value="0">全部</option>
									<option value="-2" >岗位必修</option>
									<option value="-3">岗位选修</option>
									<option value="-4">测评课程</option>
									<option value="-5">其他课程</option>
								</select>
					<!-- 	
						<s:select theme="simple" list="#{1:'岗位必修',2:'岗位选修',3:'测评课程',4:'其他课程'}"
								 headerKey="0" headerValue="全部" name="course.sta" listKey="id" listValue="value" >
								</s:select> -->
						所属培训班：<s:select theme="simple" value="course.classid"
					headerKey="-1" headerValue="全部" name="course.classid"
					list="#request.classList" listKey="id" listValue="name"></s:select>
						考场名称：<!-- <s:textfield name="course.eroom.title"></s:textfield> -->
				<s:select theme="simple" value="course.eroom.title" headerKey=""
					headerValue="全部" name="course.eroom.title" 
					list="#request.eroomList" listKey="title" listValue="title" />
				<s:submit value="搜索" cssClass="textbg4"></s:submit> &nbsp;&nbsp;&nbsp;
						<input type="button" value="导出" class="textbg4" onClick="toexcel()" />
			</s:form>
			<s:if test="courses.size==0">没有符合条件的课程</s:if>
			<s:else>
				<table width="100%" cellpadding="1" cellspacing="1">
					<tr>
						<th width="100" height="30" align="center">
							姓名
						</th>
						<th width="80" align="center">
							账号
						</th>
						<th width="100" height="30" align="center">
							所属培训班
						</th>
						<th width="150" height="30" align="center">
							考场信息
						</th>
						<th width="150" height="30" align="center">
							部门
						</th>
						<th width="130" height="30" align="center">
							时长/已学
						</th>
						<th width="70" height="30" align="center">
							学习进度
						</th>
						<th width="70" height="30" align="center">
							实际学习
						</th>
						<!--<th height="30" align="center" >
						已获学分
					</th>-->
						<th width="70" height="30" align="center">
							成绩
						</th>
						<th width="70" height="30" align="center">
							是否及格
						</th>
						<th width="70" height="30" align="center">
							结业方式
						</th>
						<th width="70" height="30" align="center">
							是否结业
						</th>
					</tr>
					<tbody onMouseOut="changeback()" onMouseOver="changeto()">
						<s:iterator value="myCourses">
							<tr>
								<td height="30" align="center" bgcolor="#FFFFFF"
									style="color: #CC0099;">
									<s:property value="user.realname" />
								</td>
								<td align="center">
									<s:property value="user.username" />
								</td>
								<s:if test="className!=null">
									<td width="100" height="30" align="center">
										<s:property value="className" />
									</td>
								</s:if>
								<s:else>
									<td width="100" height="30" align="center">
										单独分配而来
									</td>
								</s:else>
								<td height="30" align="center">
									<s:property value="examRoom.title" />
								</td>
								<td width="150" height="30" align="center">
									<s:property value="user.department.name" />
								</td>
								<td width="130" height="30" align="center">
									<s:property value="course.during" />
									分钟 /
									<s:property value="passtime" />
									分钟 （
									<s:property value="processStr" />
									%）
								</td>
								<td width="70" height="30" align="left">
									<div style="border: 1px dotted #FF6633;">
										<IMG height=14 src="images/jd.gif"
											width="<s:property value="processStr" />%">
									</div>
									<!--<td height="30" align="center" >
							<s:property value="myCredit" />-->
								</td>
								<td height="30" align="center">
									<s:property value="passtime2" />
									分钟
								</td>
								<td width="70" height="30" align="center">
									<!-- 
							<s:property value="myExamPaper.myScore" />
									/<s:if test="myExamPaper.ispassed==0">不及格</s:if>
									<s:else>及格</s:else>
									/<a href="quizpaper_view.action?elUser.id=<s:property value="user.id"/>&myExamPaper.id=<s:property value="myExamPaper.id"/>
									" target=_blank class=textbg6>查看答卷</a> 
										<%-- 位置001 --%>
						   -->
						<!-- 			<s:if test="myExamPaper.id==0">
										<s:property value="myExamPaper.myScore" />
									/<s:if test="myExamPaper.ispassed==0">不及格</s:if>
										<s:else>及格</s:else>
									/<a
											href="quizpaper_view.action?elUser.id=<s:property value="user.id"/>&myExamPaper.id=<s:property value="myExamPaper.id"/>
									"
											target=_blank class=textbg6>查看答卷</a>
										<%-- 位置001 --%>
									</s:if>
									<s:else>
										<s:iterator value="myExamPaperList">
											<s:property value="examPaper.title" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							   			<s:property value="myScore" />
										/<s:if test="ispassed==0">不及格</s:if>
											<s:else>及格</s:else>
										/<a
												href="quizpaper_view.action?elUser.id=<s:property value="user.id"/>&myExamPaper.id=<s:property value="id"/>
										"
												target=_blank class=textbg6>查看答卷</a>
											<%-- 位置001 --%> 
											
											<br />
										</s:iterator>
									</s:else>-->
										<s:property value="myExamPaper.myScore" />
								</td>
								<td width="70" height="30" align="center">
									<s:if test="myExamPaper.ispassed==1">是</s:if>
									<s:else>否</s:else>
								</td>
								<td width="70" height="30" align="center">
									<s:if test="course.jieye==3">学完且考过</s:if>
									<s:elseif test="course.jieye==2">考过</s:elseif>
									<s:else>学完</s:else>
								</td>
								<td width="70" height="30" align="center">
									<s:if test="course.jieye==4">是</s:if>
									<s:else>否</s:else>
								</td>
								<td height="30" align="center" >
									<a href="quizpaperinit2.action?myroom.examroom.id=<s:property value="examRoom.id"/>&iscommon=0&userid=<s:property value="user.id"/>" class="textbg">查看详情</a>
								</td> 
							</tr>
						</s:iterator>
					</tbody>
					<!-- 001 <a href="exampaperread.action?myExamPaper.id=<s:property value="myExamPaper.id"/>" target=_blank class=textbg4>改 分</a> -->
				</table>
				<form action="course_user_list.action" method="post" name="ddd">
					<s:hidden name="pN" id="pageNow"></s:hidden>
					<s:hidden name="pS"></s:hidden>
					<s:hidden name="course.id"></s:hidden>
					<s:hidden name="course.classid"></s:hidden>
				</form>
				<script type="text/javascript">
						function page(i){
							document.getElementById("pageNow2").value=i;
							eee.submit();
						}
						function toexcel(){
							ddd.action = "course_user_list.action?exprot=true";
							ddd.submit();
						}
		 		   </script>
				<wysLib:page></wysLib:page>
				<a href="#" onClick="history.back(-1);return false;"
					class="textbg">返回</a>
			</s:else>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
