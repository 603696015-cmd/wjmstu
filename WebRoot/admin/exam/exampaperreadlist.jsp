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
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="试卷列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">试卷评阅 </span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<div style="margin-top: 0px;">
			<script>
				function page(i){
					document.getElementById("pageNow").value=i;
					epreadform.action="exampaperreadlist.action";
					epreadform.submit();
				}
				function reQuiz(){
					epreadform.action="requiz.action";
					epreadform.submit();
				}
				function doSub(userid){
					epreadform.action="listStudyRoomRecord.action";
					document.getElementById("userId").value=userid;
					epreadform.submit();
				}
				function searchUserInit(){
				     width=600;
					 height=500;
				  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
					 var rv =  window.showModalDialog("userRegister.action?x="+Math.random(),null,sFeature);
					 //alert(rv);
					 if(rv==undefined||rv==""){
					 	document.getElementById("danweiId").value=1;
					 	document.getElementById("danweiName").value="";
					 }
					 if(rv!=undefined&&rv!=""){
					 	//var bh=rv.split("_");
					 	var bh=rv.split("-=wys=-");
					 	document.getElementById("danweiId").value=bh[2];
					 	document.getElementById("danweiName").value=bh[1];
					 }
				}
				function queryUser(){
					document.getElementById("pageNow").value=0;
					epreadform.action="exampaperreadlist.action";
					epreadform.submit();
				}
			</script>
			<!-- 内容 -->
			【<s:property value="examRoom.title"/>】考场试卷批阅
			<form action="exampaperreadlist.action" name="epreadform"
				method="post">
				<s:hidden id="pageNow" name="pN"></s:hidden>
				<s:hidden id="pageSize" name="pS"></s:hidden>
				<s:hidden name="myroom.tester.department.id" id="danweiId" />
				<s:hidden name="examRoom.id"></s:hidden>
				<s:hidden id="userId" name="elUser.id" />
				<div style="margin-top:10px;">
					账号：<s:textfield name="myroom.tester.username"  />&nbsp;&nbsp;
					姓名：<s:textfield name="myroom.tester.realname"  /> &nbsp;&nbsp;
					部门名称：<s:textfield id="danweiName" readonly="true" name="myroom.tester.department.name" />&nbsp;<a href="javascript:;" class="textbg4" onClick="searchUserInit();return false;">选择</a>
					考场状态：<s:select theme="simple" headerKey="-2" headerValue="全部" name="myroom.status" list="#{0:'缺考',1:'未做完',2:'已做完',3:'批阅中',4:'已批阅'}" value="myroom.status" />
					<input type="button" onClick="queryUser();" value="搜索" />
				</div>
				<table width="100%" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
							序号
						</td>
						<td height="30" align="center" >
							姓名
						</td>
						<td height="30" align="center" >
							账号
						</td>
						<td height="30" align="center" >
							部门
						</td>
						<td height="30" align="center" >
							试卷数量
						</td>
						<%-- 
						<td width="120" height="30" align="center" >
							
						</td>
						 --%>
						<td height="30" align="center" >
							考场状态
						</td>
						<td height="30" align="center" >
							成绩
						</td>
						<td height="30" align="center" >
							是否及格
						</td>
						<s:iterator value="examRoom.myrooms[0].myExamPapers"> 
							<td height="30" align="center" style="color: red;"
								>
								<s:property value="examPaper.title" />/考试状态
								<font color="blue">(剩余:[<s:property value="examPaper.quizcount" />]未阅卷)</font>
							</td>
						</s:iterator>
					</tr>
					<s:iterator value="examRoom.myrooms" status="ermst">
						<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
								<s:property value="#ermst.index+1" />
							</td>
							<td height="30" align="center" >
								<s:property value="tester.realname" />
							</td>
							<td height="30" align="center" >
								<s:property value="tester.username" />
							</td>
							<td height="30" align="center" >
								<s:property value="tester.department.name" />
							</td>
							<td height="30" align="center" >
								<s:property value="epsize" />个
							</td>
							<%-- 
							<td height="30" align="center" >
								<a href="javascript:doSub('<s:property value="tester.id" />');" class="textbg6">进入批阅</a>
							</td>
							 --%>
							<td height="30" align="center" >
								<s:property value="statusName" />
							</td>
							<td height="30" align="center" >
								<s:property value="myScore" />
							</td>
							<!--<td height="30" align="center" >
								<s:property value="epsize" />
								个
								<a
									href="quiz_paper_detail_view.action?elUser.id=<s:property value="tester.id"/>&examRoom.id=<s:property value="examRoom.id"/>">查看详情</a>
							</td>-->
							<td align="center" >
								<s:if test="ispassed==1">及格</s:if>
								<s:else>不及格</s:else>
							</td>
							<s:set name="userid" value="tester.id"></s:set>
							<s:set name="roomid" value="examRoom.id"></s:set>
							<s:iterator value="myExamPapers"> 
								<td height="30" align="center" style="color: red;">
									<s:if test="id==0">未分配</s:if>
									<s:else>
										<s:property value="myScore" />/
										<!-- <a
											href="quizpaper_view.action?elUser.id=<s:property value="#userid"/>&myExamPaper.id=<s:property value="id"/>"
											target=_blank>查看</a>/ -->
											<s:if test="status == 2||status==3">
												<a class="textbg4" href="exampaperreadlist_meps.action?examRoom.id=<s:property value="#roomid"/>&examPaper.id=<s:property value="examPaper.id"/>&elUser.id=<s:property value="#userid"/>">阅卷</a>
											</s:if><s:else>
												阅卷
											</s:else>
											/<s:property value="statusName" /> 
											/<s:if test="ispassed==1">通过</s:if><s:if test="ispassed==0">不通过</s:if>
									</s:else>
								</td>
							</s:iterator>
						</tr>
					</s:iterator>
				</table>
				<!--<wysLib:page></wysLib:page>
			--></form>
			<script type="text/javascript">
				function doYuej(id){
					document.location.href="exampaper_read.action?myExamPaper.id="+id;
				}
			</script>
			<wysLib:page></wysLib:page>
			<br />
			<div>
				<span>按考场试题加分</span>
				<s:form action="eroomQuestionList" method="post" theme="simple">
					<s:hidden name="examRoom.id" />
					题干：<s:textfield name="question.title" />
					<s:submit value="搜索" />
				</s:form>
			</div>
			<a href="examroomwithoutcourse_readlist.action"
						style="width: 100px;padding: 3px 0px 3px 0px;" class="textbg4">返回阅卷列表</a>
		<br/>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
