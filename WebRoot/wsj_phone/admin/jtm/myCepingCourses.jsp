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
		<TITLE>我的测评课程</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript">
			function studycourse(aaaaa,bbb){
				var widthheight = "dialogHeight:"+screen.height+"px;dialogWidth:"+screen.width+"px;status:no;resizable:yes;location:no;toolbar:no;menubar:no";
				var  towurl="course_study.action?course.id="+aaaaa+"&coursePage.id=-1&classid="+bbb
			
			window.showModalDialog(towurl,'',widthheight);
			}
		</script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="我的测评课程" /></div>
			</li>
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<s:if test="myCourses.size==0"><span style="color:red;">您当前没有需要学习的测评课程</span></s:if>
			<s:else>
				<table width="100%" align="center" cellpadding="1" cellspacing="1">
					<caption>
						我的测评课程
					</caption>
					<tr>
						<th width="200" height="30" align="center">
							课程名称						</th>
				<!-- 	<th width="200" height="30" align="center">
							与岗位关系						</th>
						<th width="100" height="30" align="center">创建者
					--> <th width="100" height="30" align="center">讲 师						</th>
						<th width="150" align="center">
							开始--结束时间						</th>
						<th width="150" height="30" align="center">
							总时间/已学时间						</th>
						<th width="100" height="30" align="center">
							学习进度						</th>
						<th width="120" height="30" align="center">
							学习中心						</th>
					</tr>
					<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
					<s:iterator value="myCourses">
						<tr>
							<td height="30" style="padding-left:8px;color:blue;" align="center">
							 	<s:property value="course.name" />  
						  </td>
					<!--   <td height="30" style="padding-left:8px;color:blue;" align="center">
								<s:if test="classId==-2">
									岗位必修
								</s:if>
								<s:elseif test="classId==-3">
									岗位选修
								</s:elseif>
						  </td>
							<td width="100" height="30" align="center">
								<s:property value="course.creater.realname" /> 	
						  </td>-->
							<td width="100" height="30" align="center">
								<s:property value="course.teacherName" />
						  </td>
							<td width="150" align="center"> 
								<s:if test="course.roomstart == course.roomend">
									不限  
								</s:if>
								<s:else>
									<s:date name="course.roomstart" format="yyyy-MM-dd HH:mm:ss" />
												-<br />
									<s:date name="course.roomend" format="yyyy-MM-dd HH:mm:ss" />
								</s:else>
						  </td>
							<td width="150" height="30" align="center">
								<s:property value="course.during" />
								分钟 /
								<s:property value="passtime" />
								分钟（<s:property value="processStr" />%）
						  </td>
							<td width="100" height="30" align="left">
								<div style="border: 1px dotted #FF6633;"> <IMG height=14 
                  src="images/jd.gif" width="<s:property value="processStr" />%"></div>
						  </td>
							<td width="120" height="30" align="center" valign="middle">
							<s:if test="course.roomstart == course.roomend">
									<a target="_blank"  onclick="studycourse('<s:property value="course.id"/>','<s:property value="classId"/>')";
									  class="textbg">进入学习</a>
							</s:if><s:else>	
							<!-- "course_study.action?course.id=<s:property value="course.id"/>&coursePage.id=-1&course.classid=0"		 -->		 	
								<a 
									 onclick=" dateTimeCheck('<s:date name="course.roomstart" format="yyyy-MM-dd-HH-mm-ss" />','<s:date name="course.roomend" format="yyyy-MM-dd-HH-mm-ss" />','<s:property value="#request.now"/>','<s:property value="course.id"/>');" class="textbg">进入学习</a>
						 	</s:else>
						  </td>
						</tr>
					</s:iterator></tbody>
			  </table>
				<form action="myCepingCourses.action" name="erform" method="post">
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
