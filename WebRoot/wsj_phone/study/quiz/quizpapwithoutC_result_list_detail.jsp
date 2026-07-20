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
	<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />

		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="wsj_phone/css/system.css" />
		<link rel="stylesheet" type="text/css" href="wsj_phone/css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<BODY>
	
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<!--<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="考场信息" /></div>
			</li>
			<li>
				<span style="font-weight: bold;">我的考场信息</span>
			</li>
		</ul>-->
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align:left;width:320px;">

<table width="100%" align="center" cellspacing="1" cellpadding="1">
				<caption>
					考场信息
				</caption>
				
		  </table>
          <table width="320" border="0" cellpadding="0" cellspacing="1" style="#D1E4F5">
  <tr>
    <td width="60" height="30" align="center" bgcolor="#F8FCFE">考场标题</td>
    <td align="center" bgcolor="#F8FCFE">我的状态</td>
    <td align="center" bgcolor="#F8FCFE">考场开始时间</td>
    <td align="center" bgcolor="#F8FCFE">考场结束时间</td>
    </tr>
  <tr>
    <td height="30" align="center" bgcolor="#F8FCFE"><s:property value="myroom.examroom.title" /></td>
    <td align="center" bgcolor="#F8FCFE"><s:property value="myroom.statusName" /></td>
    <td align="center" bgcolor="#F8FCFE"><s:date name="myroom.examroom.begintime"
							format="yyyy-MM-dd HH:mm:ss" /></td>
    <td align="center" bgcolor="#F8FCFE"><s:date name="myroom.examroom.endtime"
							format="yyyy-MM-dd HH:mm:ss" /></td>
    </tr>
</table>

			<s:if test=" myExamPapers.size==0">
<br>
						该考场没有安排我作答试卷，请与管理员联系。
		  </s:if>
			<s:else>
				<table width="320" align="center" cellpadding="1" cellspacing="1" bgcolor="#D1E4F5">
					<caption>
						考场试卷
					</caption>
					<tr>
						<td height="30" align="center" bgcolor="#F8FCFE" >
							试卷标题
						</td>
						<td height="30" align="center" bgcolor="#F8FCFE" >
							我的状态						</td>
						<td height="30" align="center" bgcolor="#F8FCFE" >
							得分						</td>
							<!--<th width="80" height="30" align="center" >
							是否及格						</th>-->
						<td height="30" align="center" bgcolor="#F8FCFE" >
							练习标题
						</td>
						<td height="30" align="center" bgcolor="#F8FCFE" ></td>
                    </tr>
					<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
					<s:iterator value=" myExamPapers">
						<tr>
						<td height="30" align="left" bgcolor="#F8FCFE" style="padding-left:8px;color:blue;">
								<s:property value="examPaper.title" />
						  </td>
							<td height="30" align="center" bgcolor="#F8FCFE" >
								<s:property value="statusName" />
						  </td>
						   <td height="30" align="center" bgcolor="#F8FCFE" >
							   <s:property value="myScore" />
						  </td>
						  <!--<td width="100" align="center" >
								<s:if test="ispassed==1">达标</s:if>
								<s:else>不达标</s:else>
						  </td>-->
							<s:if test="myroom.examroom.type==1&&examPaper.prac.id!=0">
								<td height="30" align="center" bgcolor="#F8FCFE" >
									<s:property value="examPaper.prac.title" />
								</td>
							</s:if>
							<s:else>							</s:else>
							<td height="30" align="center" bgcolor="#F8FCFE" >
								<a target="_blank"
									href='myquizpaperview.action?myExamPaper.id=<s:property value="id"/>' class="textbg5">查看答卷</a>

								<s:if test="myroom.examroom.type==1&&examPaper.prac.id!=0">
									　<a target="_blank"
										href='quizpaper.action?myExamPaper.id=<s:property value="id"/>' class="textbg">查看练习</a>
								</s:if>

							</td>
                            
						</tr>
					</s:iterator></tbody>
			  </table>
			</s:else>

</div>
		<!-- 内容 -->
	
	</body>
</HTML>
