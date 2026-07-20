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
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="学员列表" /></div>
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
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
		<table width="1300">
				<tr>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
				  </td>
					<td valign="top">
					<s:form action="getstudentcousebyuserid" method="post" theme="simple" name="eee">
							<input type="hidden" name="pN">
							<input type="hidden" name="pS" >
							<input type="hidden" name="my" value="1">
				
					</s:form>
			<s:if test="courses.size==0">没有信息</s:if>
			<s:else>
			<table width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<th height="30" align="center" >
						课程</th>
						<th width="100" height="30" align="center" >
						开始时间</th>
						<th width="100" height="30" align="center" >
						结束时间</th>
					<th width="75" height="30" align="center" >
						所属培训班					</th>
					
					<th height="30" align="center" >
						部门					</th>
					<th width="60" height="30" align="center" >
						设置学分					</th>
					<th width="60" height="30" align="center" >
						已获学分					</th>
						<th width="70" height="30" align="center" >
						结业方式					</th>
						<th width="90" height="30" align="center" >
						
						成绩/是否通过					</th>						
					<th width="65" height="30" align="center" >
						时长/已学					</th>
					<th width="60" height="30" align="center" >
						学习进度					</th>
					<th width="60" height="30" align="center" >
						实际学习					</th>
						
					<!--<th height="30" align="center" >
						已获学分
					</th>-->
				 	<th width="60" height="30" align="center" >
						查看答卷					</th>	 
				</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
				<s:iterator value="myCourses">
					<tr>
						<td width="100" height="30" align="center" bgcolor="#FFFFFF" style="color:#CC0099;">
					  <s:property value="course.name" /></td>
							
							<td width="100" height="30" align="center" >
					  <s:date name="ApplyDate" format="yyyy-MM-dd" />		</td>
							<td width="100" height="30" align="center" >
					  <s:date name="endtime" format="yyyy-MM-dd" /></td>
						<s:if test="className!=null">
							<td width="150" height="30" align="center" >
							<s:property value="className" />		</td>
						</s:if>
						<s:else>
							<td width="150" height="30" align="center" >
							单独分配而来		</td>
						</s:else>
						
						<td width="80" height="30" align="center" >
					  <s:property value="user.department.name" />		</td>
							<td width="80" height="30" align="center" >
							
								<s:property value="course.credit" />
							
							
					  </td>	
							 <td width="70" height="30" align="center" >
							 	<s:property value="myCredit" />					  </td>
							 <td width="80" height="30" align="center" >
							 	<s:if test="course.getcredit==0">考过</s:if>
							 	<s:if test="course.getcredit==1">学完</s:if>
							 	<s:if test="course.getcredit==2">考过</s:if>
							 	<s:if test="course.getcredit==3">学完且考过</s:if>
					  </td>
							 <td width="200" height="30" align="center" >
							 	<s:if test="myRoom.id!=0"><s:property value="myRoom.myScore" />分</s:if><s:else>无考场</s:else>
							 	/
							 	<s:if test="course.getcredit==1"><s:if test="passed==true">通过</s:if><s:else>未通过</s:else></s:if>
							 	<s:if test="course.getcredit==3"><s:if test="passed==true&&myRoom.ispassed==1">通过</s:if><s:else>未通过</s:else></s:if>
							 	<s:if test="course.getcredit==2||course.getcredit==0"><s:if test="myRoom.ispassed==1">通过</s:if><s:else>未通过</s:else> </s:if>
							 </td>
							
						<td width="80" height="30" align="center" >
							<s:property value="course.during" />
							分钟 /
							<s:property value="passtime" />
					  分钟	（<s:property value="processStr" />%）				  </td>
						<td width="80" height="30" align="left" >
							<div style="border: 1px dotted #FF6633;"> <IMG height=14 
                  src="images/jd.gif" width="<s:property value="processStr" />%"></div>
						<!--<td height="30" align="center" >
							<s:property value="myCredit" />-->
					  </td>
						<td width="80" height="30" align="center" >
					  <s:property value="passtime2" />分钟		</td>
						<td width="120" height="30" align="center" >
						  <!-- 
							<s:property value="myExamPaper.myScore" />
									/<s:if test="myExamPaper.ispassed==0">未及格</s:if>
									<s:else>及格</s:else>
									/<a href="quizpaper_view.action?elUser.id=<s:property value="user.id"/>&myExamPaper.id=<s:property value="myExamPaper.id"/>
									" target=_blank class=textbg6>查看答卷</a> 
										<%-- 位置001 --%>
						   -->
						   <s:if test="myRoom.id!=0">
						   			<a href="quizpapwithoutC_result_list_detailbyuserid.action?elUser.id=<s:property value="user.id"/>&examRoom.id=<s:property value="myRoom.id"/>
									" target=_blank class=textbg6>查看详情</a> 
										<%-- 位置001 --%>
					   	  </s:if>
						   <s:else>
							  无考场
						   </s:else>
					  </td>
					</tr>
				</s:iterator></tbody>
				<!-- 001 <a href="exampaperread.action?myExamPaper.id=<s:property value="myExamPaper.id"/>" target=_blank class=textbg4>改 分</a> -->
		  </table>
		   <table width="1100"><tr>
		   <td>
			  合计
			</td>
			  <td>
			  总学分
			</td>
			  <td>
			  <s:property  value="li[0]"  />分
			</td>
			  <td>
			  已获得总学分
			</td>
			  <td>
			  <s:property  value="li[1]"  />分
			</td>
			  <td>
			 总学时
			</td>
			 <td>
			  <s:property  value="li[2]"  />分钟
			</td>
			 <td>
			  已完成学时
			</td>
			 <td>
			  <s:property  value="li[3]"  />分钟
			  
			</td>
			  <td>
			  实际学时
			</td>
			 <td>
			  <s:property  value="li[4]"  />分钟
			</td>
			</tr></table>
		  <form action="getstudentcousebyuserid.action" method="post" name="ddd">
						<s:hidden name="pN" id="pageNow1"></s:hidden>
						<s:hidden name="pS"></s:hidden>
						<s:hidden name="my" value="1"></s:hidden>
			  </form>
			 
					<script type="text/javascript">
						function page(i){
							document.getElementById("pageNow1").value=i;
							ddd.submit();
						}
						function toexcel(){
							ddd.action = "course_user_list.action?exprot=true";
							ddd.submit();
						}
		 		   </script>
		 		 
		  <wysLib:page></wysLib:page>
		  </s:else></td></tr></table>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
				