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
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
</style>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="问卷列表" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">考核考试列表</span>
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
			<s:if test="myrooms.size==0">
				<br>
				<div style="width: 100%; height:40px; line-height:40px; text-align: center; margin-top: 200px;background-color:#F4F4F4; border:0px solid #C1EBFF;"><table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="48%" align="right"><img src="images/wrong.gif"/></td>
    <td><span style="color:red;"><strong>您当前没有需要参加的问卷调查!</strong></span></td>
  </tr>
</table>
</div>
			</s:if>
			<s:else>

				<table width="100%" align="center" cellspacing="1" cellpadding="1"　　>
					<caption>
						我的问卷调查
					</caption>
					<tr>
						<th width="200" height="30" align="center">
							问卷标题
						</th>
						<!-- <th width="80" height="30" align="center" >
							状态						</th> -->
				<!-- 	<th width="80" height="30" align="center">
							创建者
						</th> -->	
						<th width="120" height="30" align="center">
							问卷开始时间
						</th>
						<th width="120" height="30" align="center">
							问卷结束时间
						</th>
						<th width="80" height="30" align="center">
							操作
						</th>
			<!-- 		<th width="80" height="30" align="center">
							问卷数量
						</th> -->	
			<!-- 		<th width="60" height="30" align="center">
							成绩
						</th> -->	
				<!-- 	<th width="80" height="30" align="center">
							是否通过
						</th> -->	
						<!--<th width="80" height="30" align="center">
							可考次数						</th>
						<th width="80" height="30" align="center">
							剩余次数						</th>
						-->
						
					</tr>
					<tbody onMouseOut="changeback()" onMouseOver="changeto()">
						<s:iterator value="myrooms">
							<tr>
							 <td height="30" align="center">
								<!--	<s:if test="examroom.isApplication == 1">
										<SPAN style="color: red">【申请】</SPAN>
									</s:if>
									<s:elseif test="examroom.isApplication == 2">
										<SPAN style="color: blue;">【全工】</SPAN>
									</s:elseif>
									<s:else>
										<SPAN style="color: gray">【分配】</SPAN>
									</s:else>-->	
									<s:property value="examroom.title" />
								</td> 
								<td height="30" align="center">
									<s:date name="examroom.begintime" format="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td height="30" align="center">
									<s:date name="examroom.endtime" format="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td width="120" height="30" align="center">
									<s:if test="myExamPaper.status == 0">
										<a href='quizpaperinit_byepid2.action?examRoom.id=<s:property value="examroom.id"/>&examPaper.id=<s:property value="myExamPaper.examPaper.id"/>' target="_blank">参与</a>
									</s:if>
									<s:else>
										<font color="red">已参与</font>
										<s:if test="examroom.stuViewResult==1">
									<!-- <a href="myquizpaperview_questionnaire.action?myExamPaper.id=<s:property value="myExamPaper.wd"/>" target="_blank">查看</a> -->	
										<a href="questionnaireResult.action?examRoom.id=<s:property value="examroom.id"/>" >查看</a>
										</s:if>
									<s:else>不允许查看结果</s:else>
									</s:else>
									
								</td>
							</tr>
						</s:iterator>
					</tbody>
				</table>

				<form action="listErsWithoutC.action" name="erform" method="post">
					<s:hidden name="pN" id="pageNow">
					</s:hidden>
					<s:hidden name="pS">
					</s:hidden>
				</form>
				<script type="text/javascript">
				function page(i){
					document.getElementById("pageNow").value=i;
					erform.submit();
				}
			</script>
				<wysLib:page></wysLib:page>
			</s:else>
		</div>


		<!-- 内容 -->
	</BODY>
</HTML>
