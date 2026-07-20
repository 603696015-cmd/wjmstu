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
		<TITLE>统计详情</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript">
			function page(i){
				document.getElementById("pageNow").value=i;
				epform.submit();
			}
		</script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="统计详情" /></div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<s:form action="exam_quiz_Detail" method="post" name="epform">
			<s:hidden name="pN" id = "pageNow" />
			<s:hidden name="pS" />
			<s:hidden name="examprac.id" id="exampracId" />
			<s:hidden name="elUser.department.id" id="departmentId" />
			<s:hidden name="elUser.realname" />
			<s:hidden name="elUser.shenfenzheng" />
			<s:hidden name="elUser.sex" />
			<s:hidden name="examprac.begintime" />
			<s:hidden name="examprac.endtime" />
			<s:hidden name="elUser.age_start" />
			<s:hidden name="elUser.age_end" />
			<s:hidden name="elUser.jingzhong" />
			<s:hidden name="elUser.zhiwu" />
			<s:hidden name="elUser.zhiji" />
			<s:hidden name="elUser.gangwei" />
			<s:hidden name="elUser.dishi" />
		</s:form>
		<div style="font-size:15px;text-align:center;margin-top:10px;">考场答卷详情</div>
		<div style="margin-top: 0px;">
			<div>
				<table width="100%" align="center" cellpadding="1" cellspacing="1">
					<tr>
						<th width="150" height="30" align="center" >
							姓名						</th>
						<th width="120" height="30" align="center" >
							身份证号						</th>
						<th width="30" height="30" align="center" >
							性别						</th>
						<th height="30" align="center" >
							部门						</th>
						<th height="30" align="center" >
							年龄						</th>
						<th height="30" align="center" >
							工种						</th>
						<!-- 
						<th height="30" align="center" >
							岗位						</th>
						 -->
						<th height="30" align="center" >
							职务						</th>
						<th height="30" align="center" >
							职级						</th>
						<th height="30" align="center" >
							地市						</th>
						<th height="30" align="center" >
							考场名称						</th>
						<th height="30" align="center" >
							交卷时间						</th>
						<th height="30" align="center" >
							成绩						</th>
						<th height="30" align="center" >
							答卷						</th>
					</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
					<s:iterator value="myExamPapers">
						<tr>
							<td height="30" align="center" >
								<s:property value="examprac.user.realname" />
							</td>
							<td height="30" align="center" >
								<s:property value="examprac.user.shenfenzheng" />
							</td>
							<td height="30" align="center" >
								<s:property value="examprac.user.sex" />
							</td>
							<td height="30" align="center" >
								<s:property value="examprac.user.department.name" />
							</td>
							<td height="30" align="center" >
								<s:property value="examprac.user.AGE" />
							</td>
							<td height="30" align="center" >
								<s:property value="examprac.user.jingzhong_" />
							</td>
							<!-- 
							<td height="30" align="center" >
								<s:property value="examprac.user.gangwei_" />
							</td>
							 -->
							<td height="30" align="center" >
								<s:property value="examprac.user.zhiwu_" />
							</td>
							<td height="30" align="center" >
								<s:property value="examprac.user.zhiji_" />
							</td>
							<td height="30" align="center" >
								<s:property value="examprac.user.dishi_" />
							</td>
							<td height="30" align="center" >
								<s:property value="examprac.title" />
							</td>
							<td height="30" align="center" >
								<s:date name="examprac.endtime" />
							</td>
							<td height="30" align="center" >
								<s:property value="score" />
							</td>
							<td height="30" align="center" >
								<a class="textbg6" target="_blank" href="quizpaper_view.action?elUser.id=<s:property value="examprac.user.id" />&myExamPaper.id=<s:property value="id" />">查看答卷</a>
							</td>
						</tr>
					</s:iterator></tbody>
			  </table>
				<DIV style="text-align: center">
					<wysLib:page></wysLib:page>
				</DIV>
			</div>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
