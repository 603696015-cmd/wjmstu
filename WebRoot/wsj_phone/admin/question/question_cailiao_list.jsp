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
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
	<script type="text/javascript" src="js/cexampaper.js"></script> 
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="试题列表" /></div>
			</li>
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<table width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<td valign="top" id="tree_list_td">
						<wysLib:qlibtree
							href="question_listInC.action?sublibs=1&question.qlib.id="
							rootAble="true"></wysLib:qlibtree>
				  </td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
					</td>
					<td valign="top">
						<form action="question_listInC.action" method="post" name="quesInC">
							<s:hidden name="question.qlib.id" />
							<s:hidden name="questionParid" />
							<div>
								试题名称：<input type="text" name="question.title" value="<s:property value="question.title"/>" />
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								类型：<s:select theme="simple" headerKey="0" headerValue="全部" name="question.qtype" list="#{1:'判断题',2:'单选题',4:'多选题',5:'填空题',6:'问答题'}" value="question.qtype" />
								<input type="submit" value="搜索" /> 
							</div>
						</form>
						<s:if test="questions.size==0">没有符合条件的试题</s:if>
						<s:else>
							<!-- <form action="question_delete.action" method="post"
								name="question_manage" id="question_manage"> -->
							  <form action="question_delete_status.action" method="post"
								name="question_manage" id="question_manage"> 
								<table width="100%" align="center"
									cellpadding="1" cellspacing="1">
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
										<!--<th height="30" align="center" >
											创建时间
										</th>-->
										<th height="30" align="center" >
											状态
										</th>
										<%-- 
										<th width="160" height="30" align="center" >&nbsp;	</th>
										 --%>
									</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
									<s:iterator value="questions">
										<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
												<input type="checkbox" name="questions.id"
													value="<s:property value="id"/>" id="id">
											</td>
											<td height="30" align="center" > 
												<s:if test="status==1">  
													<a name="tdTitle" title="<s:property value="title" />"
													href="question_view_status.action?question.id=<s:property value="id" />" target="_blank">
													<s:property value="title" /> </a>
												</s:if>
												<s:else>
													<s:if test="qtype==7">
														<a name="tdTitle" title="<s:property value="title" />"
														href="question_alterInit.action?question.id=<s:property value="id" />&isCaiLiao=1">
														<s:property value="title" /></a>
													</s:if>
													<s:else>
													<a name="tdTitle" title="<s:property value="title" />"
													href="question_view.action?question.id=<s:property value="id" />">
													<s:property value="title" /></a>
													</s:else>
												</s:else> 
											</td>
											<td height="30" align="center" >
												<s:property value="qtypeName" />
											</td>
											<td height="30" align="center" >
												<s:property value="qlib.name" />
											</td>
											<td height="30" align="center" >
												<s:property value="statusName" />
											</td>
											<!--<td height="30" align="center" >
												<s:date format="yyyy-MM-dd HH:mm:ss" name="createtime" />
											</td>-->
											<%-- 
											<td width="160" height="30" align="center" >
												<s:if test="status==1"> 
												<a href="question_view_status.action?question.id=<s:property value="id" />" target="_blank" class=textbg4>预 览</a>
												</s:if>
										  </td>
										  --%>
										</tr>
									</s:iterator></tbody>
							  </table>
								<script type="text/javascript">
										function toexcel(exprot) { 
											question_manage.action = "question_list.action";
											document.getElementById("exprot").value=exprot;
											question_manage.submit();
										} 
										function page(i){
											question_manage.action = "question_listInC.action";
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
														obj[i].innerHTML = obj[i].title.substring(0,30)+"... " ;
												}
											} 
										}
										function select_All(){
											var cks= document.getElementsByName("id");
											for(var i = 0 ; i < cks.length; i++){
												cks[i].checked= true;
											}
										}
										function select_Fan(){
											var cks= document.getElementsByName("id");
											for(var i = 0 ; i < cks.length; i++){
												cks[i].checked= !cks[i].checked;
											}
										}
										function select_Bux(){
											var cks= document.getElementsByName("id");
											for(var i = 0 ; i < cks.length; i++){
												cks[i].checked= false;
											}
										}
										function addQuestion(){
										    if(window.confirm("确定添加？")){
										   		//document.getElementsByName("id");
												var checkObj = document.getElementsByName("id");
											    var billIDs = "";
											    for (i = 0; i < checkObj.length; i++) {
													if (checkObj[i].checked) {
													    if(billIDs!="")billIDs+=",";
														billIDs += checkObj[i].value;
													}
												 }
												if(billIDs==""){
												  alert("请选择要添加的试题！");
												  return ;
											    }
											    var chks = document.getElementById("chks");
											    chks.value=billIDs;
												addQues.submit();
											}
										}
								</script> 
								<wysLib:page></wysLib:page>
								<s:hidden name="question.qtype" />
								<s:hidden name="question.title" />
								<s:hidden name="question.qlib.id" />
								<s:hidden name="sublibs" />
								<s:hidden name="pN" id="pN" />
								<s:hidden name="pS" /> 
								<s:hidden name="questionParid" /></form>
						</s:else>
							<div style="text-align:center;">
								<input class=textbg6 style="height:35px;" type="button" value="确认添加" onclick='addQuestion();'>
							</div>	
					</td>
				</tr> 
		  </table>
		</div>
		<form action="addQuestionToC.action" method="post" name="addQues">
			<s:hidden name="questionParid" />
			<s:hidden name="chks" id="chks" />
		</form>
		<!-- 内容 -->
	
	</body>
</HTML>
