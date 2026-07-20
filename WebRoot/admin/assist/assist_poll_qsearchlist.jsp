<%@ page language="java" pageEncoding="UTF-8"   %>
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
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>课程类别管理</TITLE>
		<base target="_self">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<li>
				<span style="font-weight: bold;">管理试题 </span>
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
			<s:if test="questions.size==0">没有符合条件的试题</s:if>
			<s:else>
				<form action="question_delete.action" method="post"
					name="question_manage" id="question_manage">
					<table width="96%" align="center" cellspacing="2">
						<tr>
							<th height="30" align="center" >&nbsp;
								
							</th>
							<th height="30" align="center" >
								题干
							</th>
							<th height="30" align="center" >
								题目类型
							</th>
							<th height="30" align="center" >
								所属题库
							</th>
						</tr>
						<s:iterator value="questions">
							<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
									<input type="radio"
										onclick="selectTheEP(<s:property value="id"/>,'<s:property value="title"/>');"
										name="questions.id" value="<s:property value="id"/>">
								</td>
								<td height="30" align="center" >
									<a name="tdTitle" title="<s:property value="title" />"
										href="question_view.action?question.id=<s:property value="id" />">
										<s:property value="title" /> </a>
								</td>
								<td height="30" align="center" >
									<s:property value="qtypeName" />
								</td>
								<td height="30" align="center" >
									<s:property value="qlib.name" />
								</td>
							</tr>
						</s:iterator>
					</table>
					<script type="text/javascript">
					function backtosearch(){
							question_manage.action = "assist_poll_qsearchInit.action";
							question_manage.submit();
					}
					var idandtitle = new Array();
					function queding(){
						window.returnValue = idandtitle;
						window.close();
					}
					function selectTheEP(id,ept){
						idandtitle[0]= id;
						idandtitle[1]= ept;
					}
					function page(i){
						question_manage.action = "assist_poll_qsearchlist.action";
						document.getElementById("pN").value=i;
						question_manage.submit();
					}
					function titleLimit(){
						var obj = document.getElementsByName("tdTitle");
						for(var i = 0 ;i <obj.length;++i){
							hiddenTitle(i);
						}
					}
					function showTitle(i){
						var obj = document.getElementsByName("tdTitle");
						for(var i = 0 ;i <obj.length;++i){
							if(i==j){
							obj[i].innerHTML = obj[i].title; 
							// +"<a href=\"javascript:hiddenTitle("+i+")\">隐藏</a>" ;
							}
						}
					}
					function hiddenTitle(j){
						var obj = document.getElementsByName("tdTitle");
						for(var i = 0 ;i <obj.length;++i){
							if(i==j){
								if(obj[i].title.length>20) 
									obj[i].innerHTML = obj[i].title.substring(0,20)+"... " ;
							}
						} 
					}
					titleLimit();
				</script>
					<wysLib:page></wysLib:page>
					<br>
					<s:hidden name="question.qtype" />
					<s:hidden name="question.title" />
					<s:hidden name="question.qlib.id" />
					<s:hidden name="sublibs" />
					<s:hidden name="pN" id="pN" />
					<s:hidden name="pS" />
					<input type="button" value="确定" onclick='queding();'>
					<input type="button" value="重新搜索试题" onclick='backtosearch();'>
				</form>
			</s:else>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
