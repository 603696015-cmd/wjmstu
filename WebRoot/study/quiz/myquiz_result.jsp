
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
		<link rel="stylesheet" type="text/css" href="css/system003.css" />
		<link rel="stylesheet" type="text/css" href="css/manage003.css" />
        <link href="css/listlable.css" type="text/css" rel="stylesheet">
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<body>
<!--<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>-->
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="考场列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;"> 我的结业考试成绩 </span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;text-align: center;">
			
			<s:if test="myExamPapers.size==0">
            <div style="width: 100%;text-align: center; margin-top: 200px;border:0px solid #C1EBFF;">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="48%" align="right"><img src="images/wrong.gif"/></td>
    <td><span style="color:red;"><strong>您当前没有参加任何结业考试!</strong></span></td>
  </tr>
</table>
</div>
</s:if>
			<s:else>
				<table width="100%" align="center" cellspacing="1" bgcolor="#D1E4F5">
					<caption>
						结业考试
					</caption>
					<tr>
						<th width="200" align="center" bgcolor="#F8FCFE" >
							课程名称						</th>
						<th width="150" align="center" bgcolor="#F8FCFE" >
						  创建者						</th>
						<th width="150" align="center" bgcolor="#F8FCFE" >
						  所属培训班						</th>
						<th width="150" align="center" bgcolor="#F8FCFE" >
						  考场名称						</th>
						<th width="100" align="center" bgcolor="#F8FCFE" >
						  试卷状态						</th>
						<!-- 
						<th width="120" align="center" >
							交卷时间						</th>
						<th width="100" align="center" >
							考试成绩						</th>
						<th width="100" align="center" >
							是否及格						</th>
						-->
					  <th width="120" align="center" bgcolor="#F8FCFE" >
							查看答卷						</th>
					</tr>
					<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
					<s:iterator value="myExamPapers">
						<tr>
							<td align="center" bgcolor="#F8FCFE" >
								<s:property value="course.name" /><!--(<s:property value="myCourse.statusName" />)-->
							</td>
							<td width="100" align="center" bgcolor="#F8FCFE" >
								<s:property value="course.creater.realname" /> 
						  </td>
						  <s:if test="course.className!=null">
					  	    <td height="30" align="center" bgcolor="#F8FCFE" >
								<s:property value="course.className" />
							</td>
						  </s:if>
						  <s:else>
						 	 <td bgcolor="#F8FCFE">单独分配而来</td>
						  </s:else>
						  <td width="100" align="center" bgcolor="#F8FCFE" >
								<s:property value="examRoom.title" />
						  </td>
						  <td width="100" align="center" bgcolor="#F8FCFE" >
								<s:property value="statusName" />
						  </td>
						  <!-- 
						  <td>
								<s:date format="yyyy-MM-dd HH:mm:ss" name="endtime" />
						  </td>
						  <td width="100" align="center" >
								<s:property value="myScore" />
						  </td>
						  <td width="100" align="center" >
								<s:if test="ispassed==1">及格</s:if>
								<s:else>不及格</s:else>
						  </td>
						   -->
						  <td width="120" align="center" bgcolor="#F8FCFE" > 
								<!-- <a href="myquizpaperview.action?myExamPaper.id=<s:property value="id"/>"
									target="_blank" / class="textbg">查看答卷</a>
								 -->
								<a href="quizpapwithoutC_result_list_detail.action?examRoom.id=<s:property value="examRoom.id"/>&iscommon=0" class="textbg">查看详情</a>
							</td>
						</tr>
					</s:iterator></tbody>
			  </table>
				<form action="myquiz_result.action" name="erform" method="post">
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
				<wysLib:page_cisco></wysLib:page_cisco>
			</s:else>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
