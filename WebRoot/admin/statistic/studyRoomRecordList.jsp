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
		
		<div style="margin-top: 40px; text-align: center;">
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
			</script>
			<!-- 内容 -->
			<form action="exampaperreadlist.action" name="epreadform"
				method="post">
				<s:hidden id="pageNow" name="pN"></s:hidden>
				<s:hidden id="pageSize" name="pS"></s:hidden>
				<s:hidden name="examRoom.id"></s:hidden>
				<s:hidden id="userId" name="elUser.id" />
				<table width="900" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
							序号
						</td>
						<td height="30" align="center" >
							成绩
						</td>
						<td height="30" align="center" >
							是否通过
						</td>
						<td height="30" align="center" >
							状态
						</td>
						<s:iterator value="myroom.myRoomRecord[0].myExamPapers"> 
							<td height="30" align="center" style="color: red;">
								<s:property value="examPaper.title" />/成绩
							</td>
						</s:iterator>
					</tr>
					<s:iterator value="myroom.myRoomRecord" status="ermst">
						<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
								<s:property value="#ermst.index+1" />
							</td>
							<td height="30" align="center" >
								<s:property value="myScore" />
							</td>
							<td height="30" align="center" >
								<s:if test="ispassed==1">
									是
								</s:if>
								<s:else>否</s:else>
							</td>
							<td height="30" align="center" >
								<s:property value="status_" />
							</td>
							<s:set name="userid" value="tester.id"></s:set>
							<s:iterator value="myExamPapers"> 
								<td height="30" align="center" style="color: red;"
									>
									<s:if test="id==0">未开始</s:if>
									<s:else>
										<s:property value="statusName" />
										<s:if test="status==0">
											
										</s:if>
										<s:else>
											 /<s:property value="myScore" />
										</s:else>
											<s:if test="status == 2||status==3">
												<label style="padding-left:10px;" >
													<a href="exampaper_read.action?myExamPaper.id=<s:property value="id"/>"
													target=_blank>改分</a>
												</label>
												<label style="padding-left:10px;" >
													<a href="exampaper_read.action?myExamPaper.id=<s:property value="id"/>"
													target=_blank>查看</a>
												</label>
											</s:if>
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
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
