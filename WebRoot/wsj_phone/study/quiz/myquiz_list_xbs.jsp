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
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<s:if test="myExamPapers.size==0"><span style="color:red;">您当前没有需要参加的结业考试</span></s:if>
			<s:else>
				<table width="100%" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<caption>我的结业选拨式考试</caption>
					<tr>
						<th width="150" height="30" align="center" > 
							课程名称						</th>
						<th width="150" height="30" align="center" >
							创建者						</th>
							<th width="150" height="30" align="center" >
							所属培训班						</th>
						<th width="200" height="30" align="center" >
							考场名称						</th>
						<!--<th height="30" align="center" >
							我的学分
						</th>
						--><th width="120" height="30" align="center" >
							开始时间
						</th>
						<th width="120" height="30" align="center" >
							结束时间						</th>
						<th width="120" height="30" align="center" >
							结业考试						</th>
					</tr>
					<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
					<s:iterator value="myExamPapers">
						<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
								<s:property value="course.name" />
								
							</td>
							<td height="30" align="center" >
								<s:property value="course.creater.realname" />
							</td>
							<s:if test="course.className!=null">
						  	 <td height="30" align="center" >
								<s:property value="course.className" />
							</td>
						   </s:if>
						   <s:else>
						 	 <td>单独分配而来</td>
						   </s:else>
							<td height="30" align="center" >
								<s:property value="examRoom.title" />
								<s:if test="examRoom.id == bindingId">
									<span style="color:red">[被培训班绑定]</span>
								</s:if> 
							</td>
							<!--<td height="30" align="center" >
								<s:property value="examRoom.name" />
							</td>
							--><s:if test="examRoom.begintime==null">
								<td colspan="2">
									您未被安排考试
								</td>
							</s:if>
							<s:else>
								<td height="30" align="center" >
									<s:date name="examRoom.begintime" format="yyyy-MM-dd HH:mm:ss" />							  </td>
								<td height="30" align="center" >
									<s:date name="examRoom.endtime" format="yyyy-MM-dd HH:mm:ss" />
								</td>
							</s:else>
							<td height="30" align="center" >
								<!-- <a target="_blank" href="qpracInit.action?myExamPaper.id=<s:property value="id"/>" class="textbg">进入考试</a> -->
								<s:if test="examRoom.type == 1">
									<a href="quizpaperinit.action?myroom.examroom.id=<s:property value="examRoom.id"/>&iscommon=0" class="textbg">进入练习</a>
								</s:if><s:else>
									<a href="quizpaperinit.action?myroom.examroom.id=<s:property value="examRoom.id"/>&iscommon=0" class="textbg">进入考试</a>
								</s:else>
							</td> 
						</tr>
					</s:iterator></tbody>
					<s:property value=""/>
			  </table> 
				<form action="myquiz_list.action" name="erform" method="post">
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
				
				function enter(id){
					//alert(id);
					//document.location.href="qpracInit.action?examRoom.id="+id;
					//document.myForm.submit();
					//window.open("quizpaper.action?myExamPaper.id="+id+"&datetime="+new Date(),"course_exam_5","toolbar=no,fullscreen=1,location=no,directories=no,menubar=no,scrollbars=yes,resizable=no,status=no");
				}
			</script>
				<wysLib:page></wysLib:page>
			</s:else> 
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
