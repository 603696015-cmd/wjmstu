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
		<TITLE>【<s:property value="examRoom.title" />】-监考大厅</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">考场监考</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<form onSubmit="return confirm('确定加时？');"
				action="examroom_addtime.action" method="post" name="student"
				id="user_review">
				<table width="600" align="center" cellpadding="1" cellspacing="1"
					>
					<tr>
						<td>
							名称
						</td>
						<td>
							开始时间
						</td>
						<td>
							结束时间
						</td>
					</tr>
					<tr>
						<td>
							<s:property value="examRoom.title" />
						</td>
						<td>
							<s:date name="examRoom.begintime" format="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td>
							<s:date name="examRoom.endtime" format="yyyy-MM-dd HH:mm:ss" />
						</td>
					</tr>
					<tr>
						<td>
							加时
						</td>
						<td>
							<input type="text" value="<s:property value="course_sourse"/>" name="course_sourse" />分钟
							<input type="hidden" name="pN" value="${pN }" />
							<input type="hidden" name="examRoom.id"
								value="<s:property value="examRoom.id"/>" />
							<input type="submit" value="确定" />
						</td>
						<td style="color: red;">
							说明：1.不勾选人员时则全部人员加时.
							<br />
							2.勾选人员，是为所勾选人员加时.
							<br />
							3.正数是加时，负数是减时.
						</td>
					</tr>
				</table>
				<TABLE width="1000" align="center" cellpadding="1" cellspacing="1"
					>
					<caption>
						【
						<s:property value="examRoom.title" />
						】考场的监考大厅
					</caption>
					<TR>
						<td align="center" >
							学号						</TD>
						<td align="center" >
							姓名						</TD>
						<td align="center" >
							部门						</TD>
						<td align="center" bgcolor="#FFFFFF" width="580">
							<table cellpadding="1" cellspacing="1" width="580">
								<tr>
									<td width="20">									</td>
									<td>
										试卷									</td>
									<td width="30">
										已答									</td>
									<td width="30">
										未答									</td>
									<td width="40">
										加时									</td>
									<td width="40">
										状态									</td>
									<td width="40">
										重置									</td>
									<td width="40">
										交卷									</td>
								</tr>
						</table>						</td>
					</TR>
					<s:iterator value="myrooms">
						<TR>

							<td align="center" >
								<s:property value="tester.username" />							</TD>
							<td align="center" >
								<s:property value="tester.realname" />							</TD>
							<td align="center" >
								<s:property value="tester.department.name" />							</TD>
							<td align="center" >
								<table cellpadding="1" cellspacing="1" width="580">
									<s:iterator value="myExamPapers">
										<tr>
											<TD width="20">
												<input type="checkbox" name="myExamPapers.id"
													value="<s:property value="id"/>" />											</TD>
											<td>
												<s:property value="examPaper.title" />											</td>
											<td width="30">
												<s:property value="yd" />											</td>
											<td width="30">
												<s:property value="wd" />											</td>
											<td width="40">
												<s:property value="jiashi" />											</td>
											<td width="40">
											<s:if test="status == 3">
												<LABEL style="color:red"><s:property value="statusName" />	</LABEL>
											</s:if>
											<s:else>
												<s:property value="statusName" />
											</s:else>									
											</td>
											<td width="40">
												<a onClick="return confirm('确定重置该考生')"
													href="setTesterReinstate.action?myExamPapers.id=<s:property value="id"/>&pN=<s:property value="pN"/>&examRoom.id=<s:property value="examRoom.id"/>">重置</a>											</td>
											<td width="40">
												<a onClick="return confirm('确定强制交卷该考生')"
													href="setTesterSubmit.action?myExamPapers.id=<s:property value="id"/>&pN=<s:property value="pN"/>&examRoom.id=<s:property value="examRoom.id"/>">交卷</a>											</td>
										</tr>
									</s:iterator>
							</table>							</TD>
						</TR>
					</s:iterator>
				</TABLE>
				<br>
				<br>
				<script type="text/javascript">
					function setTesterSuspend(){
						student.action="setTesterSuspend.action";
						student.submit();
					}
					function setTesterReinstate(){
						student.action="setTesterReinstate.action";
						student.submit();
					}
					function setTesterSubmit(){
						student.action="setTesterSubmit.action";
						student.submit();
					}
					function page(i){
						document.location.href="myExamroom_man.action?examRoom.id=<s:property value="examRoom.id"/>&pN="+i;
					}
				</script>
				<wysLib:page></wysLib:page>
				<!--<INPUT type="button" name="pause" onclick=" setTesterSuspend()"
					value="暂停答题">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<INPUT type="button" name="resume" onclick=" setTesterReinstate()"
					value="恢复答题">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<INPUT type="button" name="stop" onclick=" setTesterSubmit()"
					value="强制交卷">-->
			</form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
