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
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>个人中心培训学心详情页</title>
		<meta name="keywords" content="OA,OA办公系统,OA系统" />
		<meta name="description"
			content="通达OA系统代表了协同OA的先进理念,是中国用户群最广泛的OA软件,协同OA软件行业唯一央企团队研发,多次摘取国内OA软件金奖,拥有300万终端OA用户,十年研发铸就成熟OA产品" />
		<link href="css/global.css" rel="stylesheet" type="text/css" />
		<link href="css/index.css" rel="stylesheet" type="text/css" />
		<link type="text/css" href="css/base.css" rel="stylesheet" />
		<link type="text/css" href="css/qhIndex.css" rel="stylesheet" />
		<link href="css/style2013.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript">
		function disNopassInfo(classid){
				width=420;
				height=360;
			   	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				window.showModalDialog("classNoPassRemack.action?elclass.id="+classid+"&x="+Math.random(),null,sFeature);
			}
		</script>

		<style type="text/css">
<!--
.STYLE3 {
	color: #FFFFFF
}
-->
</style>
	</HEAD>
	<body>
		<div id="container">


			<table width="960" border="0" align="center" cellpadding="0"
				cellspacing="0" style="margin-top: 15px;">
				<tr>
					<td height="40" background="images/bg002.jpg"
						style="padding-left: 30px;">
						<span class="yellow">1.报名信息</span>
					</td>
				</tr>
			</table>
			<table width="960" border="0" align="center" cellpadding="0"
				cellspacing="1" bgcolor="#CFDBE2">

				<tr>
					<td width="30" height="50" align="center" bgcolor="#F8FCFE">
						<img src="images/iconred.gif" width="4" height="6" />
					</td>
					<td width="100" align="center" bgcolor="#F8FCFE">
						培 训 班：
					</td>
					<td width="340" bgcolor="#F8FCFE" class="zc01"
						style="padding-left: 20px;">
						<s:property value="myClass.elClass.name" />
					</td>
					<td width="30" align="center" bgcolor="#F8FCFE">
						<img src="images/iconred.gif" width="4" height="6" />
					</td>
					<td width="100" align="center" bgcolor="#F8FCFE">
						报名时间：
					</td>
					<td width="340" bgcolor="#F8FCFE" class="zc01"
						style="padding-left: 20px;">
						<s:date name="myClass.elClass.applyDate" format="yyyy年MM月dd日 HH时mm分" />
					</td>
				</tr>
				<tr>
					<td width="30" height="50" align="center" bgcolor="#F8FCFE">
						<img src="images/iconred.gif" width="4" height="6" />
					</td>
					<td width="100" align="center" bgcolor="#F8FCFE">
						证书名称：
					</td>
					<td width="340" bgcolor="#F8FCFE" class="zc01"
						style="padding-left: 20px;">
						<s:property value="myClass.elClass.certificatename" />
					</td>
					<td width="30" align="center" bgcolor="#F8FCFE">
						<img src="images/iconred.gif" width="4" height="6" />
					</td>
					<td width="100" align="center" bgcolor="#F8FCFE">
						结业条件：
					</td>
					<td width="340" bgcolor="#F8FCFE" class="zc01"
						style="padding-left: 20px;">
						<s:if test="myClass.elClass.classtype==0">
				  		必修课全部通过，选修课最少获得　
						<span style="color: red;"><b> <s:property
										value="myClass.elClass.optionalcredit" /> </b>
							</span>　学分
						</s:if>
						<s:elseif test="myClass.elClass.classtype==2"> 
				  		必修课最少获得:<s:property value="myClass.elClass.credit_bx" />
							<br />
				  		选修课最少获得:<s:property value="myClass.elClass.credit_xx" />
						</s:elseif>
						<s:else>
						必修课全部通过
						</s:else>
					</td>
				</tr>
			</table>
			<table width="960" border="0" align="center" cellpadding="0"
				cellspacing="0" style="margin-top: 15px;">
				<tr>
					<td height="40" background="images/bg002.jpg"
						style="padding-left: 30px;">
						<span class="yellow">2.在线学习</span>
					</td>
					<td width="300" align="right" background="images/bg002.jpg"
						style="padding-right: 10px;">
						应完成<s:property value="map.zong_xueshi" />学时，已完成<s:property value="map.learned_xueshi" />学时，学习总进度:
					</td>
					<td width="183" background="images/bg002.jpg">
						<div
							style="width: 170px; BORDER-BOTTOM: #ff6633 1px dotted; BORDER-LEFT: #ff6633 1px dotted; BORDER-TOP: #ff6633 1px dotted; BORDER-RIGHT: #ff6633 1px dotted">
							<img src="images/jd.gif" width="<s:property value="map.process" />%" height="14" />
						</div>
					</td>
				</tr>
			</table>
			<table width="960" border="0" align="center" cellpadding="5"
				cellspacing="1" bgcolor="#CFDBE2">
				<tr>
					<td height="50" align="center" valign="middle" bgcolor="#EBF7FC">
						&nbsp;
					</td>
					<td align="center" valign="middle" bgcolor="#EBF7FC">
						课程名称
					</td>
					<td width="110" align="center" bgcolor="#EBF7FC">
						时长/完成
					</td>
					<td width="60" align="center" bgcolor="#EBF7FC">
						进 度
					</td>
					<td height="40" align="center" bgcolor="#EBF7FC">
						进度条
					</td>
					<td align="center" bgcolor="#EBF7FC">
						学习
					</td>
					<td align="center" bgcolor="#EBF7FC">
						考试
					</td>
				</tr>
				<s:iterator value="studyCourseList">
				<tr>
					<td width="30" height="50" align="center" valign="middle"
						bgcolor="#F8FCFE">
						<img src="images/iconred.gif" width="4" height="6" />
					</td>
					<td valign="middle" bgcolor="#F8FCFE">
						<a href="javascript:void(0);" style="padding-left: 15px;" class="zc01"><s:property value="course.name" /></a>
					</td>
					<td width="110" align="center" bgcolor="#F8FCFE">
						<s:property value="course.during" />分钟/<s:property value="passtime" />分钟
					</td>
					<td width="60" align="center" bgcolor="#F8FCFE">
						<s:property value="process" />%
					</td>
					<td width="120" height="40" align="left" bgcolor="#F8FCFE">
						<div
							style="BORDER-BOTTOM: #ff6633 1px dotted; BORDER-LEFT: #ff6633 1px dotted; BORDER-TOP: #ff6633 1px dotted; BORDER-RIGHT: #ff6633 1px dotted">
							<img src="images/jd.gif" width="<s:property value="process" />%" height="14" />
						</div>
					</td>
					<td width="60" align="center" bgcolor="#F8FCFE">
						<a href="course_study.action?course.id=<s:property value="course.id" />&coursePage.id=-1&classid=<s:property value="course.classid" />" target="_blank">
							<img src="images/xtb021.gif" width="28" height="27" />
						</a>
					</td>
					<td width="60" align="center" bgcolor="#F8FCFE">
						<a href='quizpaperinit.action?course.id=<s:property value="course.id" />&course.getcredit=<s:property value="course.getcredit" />&course.firstLearn=<s:property value="course.firstLearn" />&myroom.examroom.id=<s:property value="examRoom.id" />&iscommon=0&elclass.id=<s:property value="elclass.id"/>' onClick="return isEroom2('<s:property value="examRoom.valid"/>','<s:property value="examRoom.svalid"/>','<s:property value="examRoom.isnormal"/>','<s:property value="examRoom.type"/>');" target="_blank">
							<img src="images/xtb021.gif" width="28" height="27" />
						</a>
					</td>
				</tr>
				</s:iterator>
			</table>
			
			<!-- 
			<table width="960" border="0" align="center" cellpadding="0"
				cellspacing="0" style="margin-top: 15px;">
				<tr>
					<td height="40" background="images/bg002.jpg"
						style="padding-left: 30px;">
						<span class="yellow">3.在线考试</span>
					</td>
				</tr>
			</table>
			<s:if test="elclass.examRooms[0].isPass==1">
				<table width="960" border="0" align="center" cellpadding="5"
					cellspacing="1" bgcolor="#CFDBE2">
					<tr>
						<td width="30" height="70" align="center" valign="middle"
							bgcolor="#F8FCFE">
							<img src="images/iconred.gif" width="4" height="6" />
						</td>
						<td valign="middle" bgcolor="#F8FCFE">
							<a href="javascript:void(0);" class="zc01" style="padding-left: 15px;"><s:property value="elclass.examRooms[0].title" /></a>
						</td>
						<td width="80" align="center" bgcolor="#F8FCFE">
							<s:property value="elclass.examRooms[0].score" />分
						</td>
						<td width="80" align="center" bgcolor="#F8FCFE">
							<s:if test="elclass.examRooms[0].isPassed==1">已通过</s:if>
							<s:else>未通过</s:else>
						</td>
						<td width="200" align="center" bgcolor="#F8FCFE">
							<table width="95" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td height="28" align="center" background="images/textbg.jpg">
										<a href="quizpaperinit.action?myroom.examroom.id=<s:property value="elclass.examRooms[0].id" />&Return=list" class="zc01 STYLE3" style="color: white;">进入考场</a>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</s:if>
			<s:else>
				<table width="960" border="0" align="center" cellpadding="5"
					cellspacing="1" bgcolor="#CFDBE2">
					<tr>
						<td width="30" height="70" align="center" valign="middle"
							bgcolor="#F8FCFE">
							<img src="images/iconred.gif" width="4" height="6" />
						</td>
						<td valign="middle" bgcolor="#F8FCFE">
							<a href="javascript:void(0);" class="zc01" style="padding-left: 15px;"><s:property value="elclass.examRooms[0].title" /></a>
						</td>
						<td width="160" align="center" bgcolor="#F8FCFE">
							未参加考试
						</td>
						<td width="200" align="center" bgcolor="#F8FCFE">
							<table width="95" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td height="28" align="center" background="images/textbg.jpg">
										<s:if test="elclass.examRooms!=null&&elclass.examRooms.size!=0 && myClass.passed">
											<a href="quizpaperinit.action?myroom.examroom.id=<s:property value="elclass.examRooms[0].id" />&Return=list" class="zc01 STYLE3" style="color: white;">进入考场</a>
										</s:if>
										<s:else>
											<a href="javascript:alert('您还没有完成培训班学习，请学完后再进入考试');" class="zc01 STYLE3" style="color: white;">进入考场</a>
										</s:else>
									</td>
								</tr>
							</table>
							<p>
								<s:if test="elclass.examRooms!=null&&elclass.examRooms.size!=0 && myClass.passed">
								</s:if>
								<s:else>
									未达到参加考试的条件
								</s:else>
							</p>
						</td>
					</tr>
				</table>
			</s:else>
			 -->
			
			
			
			<table width="960" border="0" align="center" cellpadding="0"
				cellspacing="0" style="margin-top: 15px;">
				<tr>
					<td height="40" background="images/bg002.jpg"
						style="padding-left: 30px;">
						<span class="yellow">4.查看证书</span>
					</td>
				</tr>
			</table>
			<s:if test="myClass.passed">
				<table width="960" border="0" align="center" cellpadding="5"
					cellspacing="1" bgcolor="#CFDBE2">
					<tr>
						<td width="30" height="70" align="center" valign="middle"
							bgcolor="#F8FCFE">
							<img src="images/iconred.gif" width="4" height="6" />
						</td>
						<td valign="middle" bgcolor="#F8FCFE">
							<a href="javascript:void(0);" class="zc01" style="padding-left: 15px;"><s:property value="elclass.certificatename" /></a>
						</td>
						<td width="200" align="center" bgcolor="#F8FCFE">
							<table width="95" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td height="28" align="center" background="images/textbg.jpg">
										<a class="zc01 STYLE3" style="color: white;" target="_blank" href="mydiploma_view.action?elclass.id=<s:property value="myClass.elClass.id"/>" >
										查看证书
										</a>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</s:if>
			<s:else>
				<table width="960" border="0" align="center" cellpadding="5"
					cellspacing="1" bgcolor="#CFDBE2">
					<tr>
						<td width="30" height="70" align="center" valign="middle"
							bgcolor="#F8FCFE">
							<img src="images/iconred.gif" width="4" height="6" />
						</td>
						<td valign="middle" bgcolor="#F8FCFE">
							<a href="javascript:void(0);" class="zc01" style="padding-left: 15px;"><s:property value="elclass.certificatename" /></a>
						</td>
						<td width="200" align="center" bgcolor="#F8FCFE">
							<table width="95" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td height="28" align="center" background="images/textbg.jpg">
										<a href="javascript:disNopassInfo('<s:property value="myClass.elClass.id"/>');" class="zc01 STYLE3" style="color: white;">查看证书</a>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</s:else>
			
			<p>
				&nbsp;
			</p>
			<p>
				&nbsp;
			</p>

		</div>
	</body>
</html>
