
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="考场列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">课程结业考试</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<s:if test="myExamPapers.size==0"><span style="color:red;">您当前没有需要参加的结业考试</span></s:if>
			<s:else>
				<table width="100%" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<caption>
						<s:if test="classid==-2">我的必修课程</s:if>
						<s:else>我的选修课程</s:else>
					</caption>
					<tr>
						<th width="150" height="30" align="center" > 
							课程名称						</th>
						<th width="150" height="30" align="center" >
							学时						</th>
						<th width="150" height="30" align="center" >
							学分						</th>
						<th width="200" height="30" align="center" >
							通过标准						</th>
						<!--<th height="30" align="center" >
							我的学分
						</th>
						--><th width="120" height="30" align="center" >
							学习进度
						</th>
					<!-- 	<th width="120" height="30" align="center" >
							结束时间						</th>
						<th width="120" height="30" align="center" >
							试卷数量						</th> -->
						<th width="120" height="30" align="center" >
							考场成绩						</th>
						<th width="120" height="30" align="center" >
							是否通过						</th>
				<!-- 		<th width="120" height="30" align="center" >
							结业考试						</th> -->
					</tr>
					<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
					<s:iterator value="myCourses">
						<tr>
							<td height="30" style="padding-left:8px;color:blue;" align="left">
									<s:property value="course.name" />
							</td>
							<td height="30" align="center" >
								<s:property value="course.DURING" />
							</td>
						  	 <td height="30" align="center" >
								<s:property value="course.credit" />
							</td>
							 <td height="30" align="center" >
							 	<s:if test="course.jieye==1">学完</s:if>
							 	<s:elseif test="course.jieye==2">考过</s:elseif>
								<s:else>学完且考过</s:else>
							</td>
							<td width="100" height="30" align="left">
								<div style="border: 1px dotted #FF6633;"> <IMG height=14 
                  src="images/jd.gif" width="<s:property value="processStr" />%"></div>
						  </td>
							
					<!-- 	<td height="30" align="center" >
								<s:date name="examroom.begintime" format="yyyy-MM-dd HH:mm:ss" />							  </td>
							<td height="30" align="center" >
								<s:date name="examroom.endtime" format="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td height="30" align="center" >
								<s:property value="epsize" />
							</td>-->
							<td height="30" align="center" >
								<s:property value="myExamPaper.myScore" />
							</td>
							<td height="30" align="center" >
								<s:if test="myExamPaper.ispassed==1">是</s:if>
								<s:else>否</s:else>
							</td>
							<td height="30" align="center" >
								<s:if test="course.jieye==1">
									<a target="_blank" href="course_study.action?course.id=<s:property value="course.id"/>&coursePage.id=-1&classid=<s:property value="course.classid"/>" class="textbg">进入学习</a>
								</s:if>
							</td> 
							<td height="30" align="center" >
								<s:if test="course.jieye!=1">
									<a href="quizpaperinit.action?myroom.examroom.id=<s:property value="examRoom.id"/>&iscommon=0" class="textbg">进入考试</a>
								</s:if>
							 
							</td> 
						</tr>
					</s:iterator></tbody>
					<s:property value=""/>
			  </table> 
				<form action="myobcourse.action" name="erform" method="post">
					<s:hidden name="pN" id="pageNow">
					<s:hidden name="classid"></s:hidden>
					</s:hidden>
					<s:hidden name="pS">
					</s:hidden>
				</form>
				<script>
				function page(i){
					document.getElementById("pageNow").value=i;
					erform.submit();
				}
				
				function enter(id){
					//alert(id);
					//document.location.href="qpracInit.action?examRoom.id="+id;
					//document.myForm.submit();
					//window.open("quizpaper.action?myExamPaper.id="+id+"&datetime="+new Date(),"course_exam_5","toolbar=no,fullscreen=1,location=no,directories=no,menubar=no,scrollbars=yes,resizable=no,status=no");
				}
			</script>
				<wysLib:page></wysLib:page>
			</s:else> 
			<%-- 
			<div style="margin-top: 0px; text-align: center; <s:if test="myExamPapers_xbs.size == 0">display:none</s:if>" >　
				<iframe id="myquiz_xbsFrame" src="myquiz_list_xbs.action" width=100%  height="600"
					marginwidth="0" marginheight="0" frameborder=0 ></iframe>　
			</div>
			 --%>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
			