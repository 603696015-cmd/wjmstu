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
		<TITLE>智能辅导分设置</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<SCRIPT type="text/javascript">
			function myload(){
				if("${elmessage}"!=""){
				alert( "${elmessage}!");
				}
			
			}
		</SCRIPT>
	</HEAD>
	<body onload="myload();">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="填写信息" /></div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<s:form action="intelligentset" method="post" theme="simple" id="otherset">
				<table width="100%" align="center" cellpadding="1" cellspacing="1"
					>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" width="150" > 
							考查类别
						</td>
						<td height="30" style="padding-left:8px;color:blue;" width="150" > 
							考查指标
						</td>
						<td height="30" style="padding-left:8px;color:blue;" width="150" > 
							满分
						</td>
						<td height="30" style="padding-left:8px;color:blue;" width="150" > 
							计分规则
						</td>
					</tr>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" width="150" rowspan=3> 
							学习时间
						</td>
						<td height="30" style="padding-left:8px;color:blue;" width="150" > 
							每天学习时间加分
						</td>
						<td height="30" style="padding-left:8px;" >
							满分<s:textfield name="intelligentSysConf.scoreLogin"></s:textfield>(分)
						</td>
						<td height="30" style="padding-left:8px;" align="left">
							有登陆的天数*<s:textfield name="intelligentSysConf.scoreLoginPer"></s:textfield>(分)
							-
							连续3天未登陆的次数<s:textfield name="intelligentSysConf.scoreLoginNot3dayPer"></s:textfield>(分)
						</td>
					</tr>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" width="150" > 
							每周的学习时间加分
						</td>
						<td height="30" style="padding-left:8px;" >
							满分<s:textfield name="intelligentSysConf.scoreWeek"></s:textfield>(分)
						</td>
						<td height="30" style="padding-left:8px;" align="left">
							(当周学习时长/3小时)*<s:textfield name="intelligentSysConf.scoreWeekPer"></s:textfield>(分)
						</td>
					</tr>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" width="150" > 
							等级总学习时间得分
						</td>
						<td height="30" style="padding-left:8px;" >
							满分<s:textfield name="intelligentSysConf.scoreClass"></s:textfield>(分)
						</td>
						<td height="30" style="padding-left:8px;" align="left">
							总小时*<s:textfield name="intelligentSysConf.scoreClassPer"></s:textfield>(分)
						</td>
					</tr>
					
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" width="150" rowspan=4> 
							学习习惯
						</td>
						<td height="30" style="padding-left:8px;color:blue;" width="150" > 
							复听比例智能辅导分
						</td>
						<td height="30" style="padding-left:8px;" >
							满分<s:textfield name="intelligentSysConf.scoreProportionProcess"></s:textfield>(分)
						</td>
						<td height="30" style="padding-left:8px;" align="left">
							复听比例*<s:textfield name="intelligentSysConf.scoreProportionProcessPer"></s:textfield>(分)
						</td>
					</tr>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" width="150" > 
							复听数量智能辅导分
						</td>
						<td height="30" style="padding-left:8px;" >
							满分<s:textfield name="intelligentSysConf.scoreProportionTime"></s:textfield>(分)
						</td>
						<td height="30" style="padding-left:8px;" align="left">
							复听比例*<s:textfield name="intelligentSysConf.scoreProportionTimePer"></s:textfield>(分)
						</td>
					</tr>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" width="150" > 
							录音比例智能辅导分
						</td>
						<td height="30" style="padding-left:8px;" >
							满分<s:textfield name="intelligentSysConf.scoreRecodingProcess"></s:textfield>(分)
						</td>
						<td height="30" style="padding-left:8px;" align="left">
							录音比例*<s:textfield name="intelligentSysConf.scoreRecodingProcessPer"></s:textfield>(分)
						</td>
					</tr>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" width="150" > 
							录音数量智能辅导分
						</td>
						<td height="30" style="padding-left:8px;" >
							满分<s:textfield name="intelligentSysConf.scoreRecodingTime"></s:textfield>(分)
						</td>
						<td height="30" style="padding-left:8px;" align="left">
							录音比例*<s:textfield name="intelligentSysConf.scoreRecodingTimePer"></s:textfield>(分)
						</td>
					</tr>
					
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" width="150" rowspan=3> 
							学习成绩
						</td>
						<td height="30" style="padding-left:8px;color:blue;" width="150" > 
							模块练习成绩智能辅分
						</td>
						<td height="30" style="padding-left:8px;" >
							满分<s:textfield name="intelligentSysConf.scoreExamPage"></s:textfield>(分)
						</td>
						<td height="30" style="padding-left:8px;" align="left">
							模块平均分*<s:textfield name="intelligentSysConf.scoreExamPagePer"></s:textfield>(分)
						</td>
					</tr>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" width="150" > 
							1-3级单元测验辅导分
						</td>
						<td height="30" style="padding-left:8px;" rowspan=2>
							满分<s:textfield name="intelligentSysConf.scoreExamCourse"></s:textfield>(分)
						</td>
						<td height="30" style="padding-left:8px;" align="left">
							平均分*<s:textfield name="intelligentSysConf.scoreExamCourse1TO3Per"></s:textfield>(分)
						</td>
					</tr>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" width="150" > 
							4-6级单元测验辅导分
						</td>
						<td height="30" style="padding-left:8px;" align="left">
							平均分*<s:textfield name="intelligentSysConf.scoreExamCourse4TO6Per"></s:textfield>(分)
						</td>
					</tr>
					
				</table>
			  <br>
				<input type="submit" value="保存设置"  class="textbg6" >
			</s:form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
