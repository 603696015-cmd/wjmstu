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
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/assist.js"></script>
		<script type="text/javascript">
			function init(){
			//	 var subject="<s:property value="question.subject" />";
			   	 var subject = document.getElementById("subject").value;
				 var subArray=subject.split("-=SpEl=-");
			//	 var qtype="<s:property value="question.qtype" />";
				 var qtype=document.getElementById("qtype").value;
				 for(var i=0;i<subArray.length-1;i++){
				 	//alert(subArray[i]);
				 	
				 	var d=$("<div>");
				 	var temp= String.fromCharCode(65+i);
				 	var html = temp+": "+subArray[i]
				 	d.html(temp+"："+subArray[i]);
				 	d.css("padding","3px");
				 	$("#qtitle").append(d);
				 }
			}
			function _onsubmit(){
				var answers=$("input[name=\"answer\"]:checked");
				if(answers.length<=0){
					alert("请选择投票！");
					return;
				}
				form_poll.submit();
			}
		</script>
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
	</HEAD>
	<body >
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="添加投票信息" /></div>
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
			<form id="form_poll" name="form_pollr" method="post"
				action="studyPollDo.action">
				<s:hidden name="poll.id" />
				<span style="color: #ff0000;"></span>
				<table width="600" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<s:iterator value='questionRankings'>
					<tr>
						<td bgcolor="#FFFFFF" style="padding-left:12px;" colspan="3">
							<div id="qtitle" style="vertical-align:top;height:100px;">
								<s:property value="question.title"/><br />
						 		<input type="hidden" id="subject" value="<s:property value="question.subject"/>"/>
								<input type="hidden" id="qtype" value="<s:property value="question.qtype"/>"/>
								<br> 	
								<s:property value="question.subjects"/>
								<script type="text/javascript">
									//init();
								</script>
							</div>
							
						</td>
					</tr>
					<tr>
						<td bgcolor="#FFFFFF" style="padding-left:12px;">
							
						</td>
						<td bgcolor="#FFFFFF" style="padding-left:12px;">
							人次
						</td>
						<td bgcolor="#FFFFFF" style="padding-left:12px;">
							百分比
						</td>
					</tr>
					<tr>
						<td bgcolor="#FFFFFF" style="padding-left:12px;">
								总共
							</td>
						<td bgcolor="#FFFFFF" style="padding-left:12px;">
							<s:property value="answerCount" />
						</td>
						<td bgcolor="#FFFFFF" style="padding-left:12px;">
							--
						</td>
					</tr>
					<s:iterator value="answerInfo">
						<tr>
						<td bgcolor="#FFFFFF" style="padding-left:12px;">
							<s:property value="selectOptions_2" />
						</td>
						<td bgcolor="#FFFFFF" style="padding-left:12px;">
							<s:property value="selectCount" />
						</td>
						<td bgcolor="#FFFFFF" style="padding-left:12px;">
							<s:if test="selectCount>0">
								<s:property value="(((selectCount/1.0)/(answerCount/1.0)*100*100)+'').substring(0,(((selectCount/1.0)/(answerCount/1.0)*100*100)+'').indexOf('.'))/100" /> %
							</s:if>
							<s:else>0 %</s:else>
						</td>
					</tr>
					</s:iterator>
					</s:iterator>
				</table>
			</form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
