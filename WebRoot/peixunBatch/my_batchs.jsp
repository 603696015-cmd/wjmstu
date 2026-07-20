<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.classman.entities.ElClType"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@page import="com.sopia.classman.entities.ElClass"%>

<%
	String cltypeName = "";
	if (request.getAttribute("elclass") != null) {
		cltypeName = ((ElClass) request.getAttribute("elclass"))
				.getCltype().getName()
				+ "";
	}

	ElClType cltypeTree = (ElClType) request.getAttribute("cltypeTree");
%>

<HTML>
	<HEAD>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>五矿发展员工职业发展系统--管理端--</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((       this .       sectionRowIndex %       2 ==       0)
		?   
		   "#ffffff" :       "#f4f4f4" )
}
</style>
		<SCRIPT type="text/javascript">
	function dateTimeCheck(startTime,endTime,now){
				//course_study.action?course.id=<s:property value="course.id"/>&coursePage.id=-1
				
				//alert(startTime);
				//alert(now);
				//alert(endTime);
				//转换成时间
				var start=toDate(startTime);
				var end=toDate(endTime);
				var noww=toDate(now);
				return;
				//end=toDate("2012-05-10-00-00-00");
				//noww=toDate("2012-05-11-00-00-00");
				//var valid=val;
				//alert(start);
				//alert(end);
				//alert(noww);
				//alert(valid);
				/*
				if(valid !=6 || valid !=8 ){
					alert("考场正在修改中，请等待!!!");
					return false;
				}
				*/
				if(noww<start){
					alert("不在有效学习时间段范围内，请与管理员联系");
					return false;
				}else if(noww>end){
					alert("不在有效学习时间段范围内，请与管理员联系");
					return false;
				}
				return true;
			}
			
			function toDate(str){
  				 var sd=str.split("-");
   				 return new Date(sd[0],sd[1],sd[2],sd[3],sd[4],sd[5]);
}
</SCRIPT>
	</HEAD>
	<body>
		<div class="dh3">
			<!--<div class="newpos"></div> 
			<div class="newpos2"> 
				<span style="font-weight: bold;">培训班详情</span>
			</div>-->
			<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
				<wysLib:Navigation ivalue="培训批次列表" />
			</div>
		</div>
		<s:form action="my_batchs" theme="simple" method="post"
			name="class_info" id="class_info">

			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td valign="middle" class="tablequiz">
						<ul class="nav">
							<!--<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="查看详情" /></div>
			</li>-->
							<li>
								<span style="font-weight: bold;">培训批次列表</span>
							</li>

						</ul>
					</td>
					<td width="120" valign="middle" class="tablequiz">
						<A id=quit href="javascript:window.parent.full_screen(false);"
							class="textbg6" style="display: none">退出全屏</A>
					</td>
				</tr>
			</table>

			<!-- 内容 -->
			<div style="margin-top: 0px; text-align: center;">

				<div>
					<table width="100%" align="center" cellpadding="1" cellspacing="1"
						id="table1">
						<s:if test="batchList.size==0">当前还没有分配批次</s:if>
						<s:else>
							<tr>
								<th width="180" align="center">
									培训批次名称
								</th>
								<th width="120" align="center">
									开始时间
								</th>
								<th width="100" align="center">
									结束时间
								</th>
								<th width="100" align="center">
									培训班数量
								</th>
								<th width="180" height="30" align="center">
									已结业培训班数量
								</th>
								<th width="100" height="30" align="center">
									进入学习
								</th>
							</tr>
							<tbody>
								<s:iterator value="batchList">
									<tr>

										<td height="30" align="center">
											<s:property value="name" />
										</td>
										<td height="30" align="center">
											<s:date name="createtime" format="yyyy-MM-dd~HH:mm:ss" />
										</td>
										<td height="30" align="center">
											<s:date name="endtime" format="yyyy-MM-dd~HH:mm:ss" />
										</td>
										<td id="start" height="30" align="center">
											<s:property value="elclassCount" />
										</td>
										<td height="30" align="center">
											<s:property value="classCount" />
										</td>
										<td height="30" align="center">
											<a target="_blank" href="#"
												onclick="return dateTimeCheck('<s:date name="createtime" format="yyyy-MM-dd-HH-mm-ss" />','<s:date name="endtime" format="yyyy-MM-dd-HH-mm-ss" />','<s:property value="#request.now"/>');"
												class="textbg6">进入学习</a>
										</td>
									</tr>
								</s:iterator>
							</tbody>
						</s:else>
					</table>
					<wysLib:page></wysLib:page>
				</div>
			</div>





		</s:form>
		<br />
	</body>
	<SCRIPT type="text/javascript">
	</SCRIPT>
</HTML>
