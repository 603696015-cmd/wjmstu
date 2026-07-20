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
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="试题内容" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">查看试题</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<table width="85%" align="center" cellpadding=1 cellspacing="1">
				<tr>
					<td height="30" width="100" align="center" >
						<b>试题类别</b>
					</td>
					<td align=center >
						<label style="font-size: 16px; font-weight: bold;">
							<s:property value="question.qtypeName" />
						</label>
					</td>
				</tr>
				<tr>
					<td height="30" width="100" align="center" >
						<b>所属知识点</b>
					</td>
					<td align=center >
						<s:property value="question.qlib.name" />
					</td>
				</tr>
				<tr>
					<td height="30" width="100" align="center" >
						<b>难度</b>
					</td>
					<td align=center >
						<s:property value="question.qlevel" />
						级
					</td>
				</tr>
				<%-- 
				<tr>
					<td height="30" width="100" align="center" >
						<b>试题名称</b>
					</td>
					<td align=center >
						<s:property value="question.title" />
					</td>
				</tr>
				 --%>
				<tr>
					<td height="30" width="100" align="center" >
						<b>题干</b>
					</td>
					<td align=center >
						${question.content}
					</td>
				</tr>
				<s:if test="question.qtype==1">
					<tr>
						<td height="30" width="100" align="center" >
							<b>试题答案</b>
						</td>
						<td align=center >
							<s:if test="question.answers[0]=='yes'">正确</s:if>
							<s:else> 错误 </s:else>
						</td>
					</tr>
				</s:if>
				<s:if test="question.qtype==2||question.qtype==3||question.qtype==4">
					<tr>
						<td height="30" width="100" align="center" >
							<b>试题选项</b>
						</td>
						<td align=center >
							<script type="text/javascript">
							function intoABC(i,id){
									if(!i.match(/^[\d]+$/)){i=0}
									
									document.getElementById(id).innerHTML=String.fromCharCode(65+parseInt(i,10)); 
								}
						</script>
							<s:iterator value="question.options" status="qa">
								<b id="opt_ABC_<s:property value="#qa.index" />"><script
										type="text/javascript">intoABC('<s:property value="#qa.index" />',"opt_ABC_<s:property value="#qa.index" />")</script>：</b>
								<s:property />
								<br>
							</s:iterator>
						</td>
					</tr>
					<tr>
						<td height="30" width="100" align="center" >
							<b>试题答案</b>
						</td>
						<td align=center >
							<s:iterator value="question.answers">

								<b id="ans_ABC_<s:property />"><script
										type="text/javascript">intoABC('<s:property />',"ans_ABC_<s:property />")</script>、</b>
							</s:iterator>
						</td>
					</tr>
				</s:if>
				<s:if test="question.qtype==5">
					<tr>
						<td height="30" width="100" align="center" >
							<b>空白答案</b>
						</td>
						<td align=center >
							<s:iterator value="question.answers">
								<s:property />、
									</s:iterator>
						</td>
					</tr>
				</s:if>
				<s:if test="question.qtype==6">
					<tr>
						<td height="30" width="100" align="center" >
							<b>答题字数限制</b>
						</td>
						<td align=center >
							<s:property value="question.minWord" />
							个
						</td>
					</tr>
					<tr>
						<td height="30" width="100" align="center" >
							<b>试题答案关键词</b>
						</td>
						<td align=center >
							<s:iterator value="question.answers">
								<s:property /> 、
									</s:iterator>
						</td>
					</tr>
				</s:if>
				<s:if test="question.qtype==9">
					<tr>
						<td height="30" width="100" align="center" >
							<b>答题字数限制</b>
						</td>
						<td align=center >
							<s:property value="question.minWord" />
							个
						</td>
					</tr>
					<tr>
						<td height="30" width="100" align="center" >
							<b>试题答案关键词</b>
						</td>
						<td align=center >
							<s:iterator value="question.answers">
								<s:property /> 、
									</s:iterator>
						</td>
					</tr>
				</s:if>
				<s:if test="question.qtype==7">
					<tr>
						<td height="30" width="100" align="center" >
							<b>该题的小题</b>
						</td>
						<td align=center >
							<s:if test="question.childs.size==0">
				本材料题目前还没小题
		<br>
								<a
									href="questionchild_addInit.action?question.qtype=2&question.parent.id=<s:property value="question.id"/>">添加小题</a>
							</s:if>
							<s:else>
								<table width="100%" align="center" cellspacing="1"
									style="border: 1px solid black;">
									<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">&nbsp;
											
										</td>
										<td height="30" align="center" >
											编号
										</td>
										<td height="30" align="center" >
											题干
										</td>
										<td height="30" align="center" >
											题目类型
										</td>
										<td height="30" align="center" >
											创建时间
										</td>
										<td height="30" align="center" >
											分值(%)
										</td>
										<td height="30" align="center" >&nbsp;
											
										</td>
										<!--<td height="30" colspan="2" align="center" >
											&nbsp;
										</td>
									--></tr>
									<s:set name="pid" value="question.id" />
									<s:iterator value="question.childs" status="st">
										<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
												<input type="checkbox" name="questions.id"
													value="<s:property value="id"/>">
											</td>
											<td height="30" align="center" >
												<s:property value="sortid" />
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
												<s:date format="yyyy-MM-dd HH:mm:ss" name="createtime" />
											</td>
											<td height="30" align="center" >
												<s:property value="scoreper" />
											</td>
											<td height="30" align="center" >
												<a
													href="questionchild_alterInit.action?question.id=<s:property value="id" />&question.parent.id=${question.id}" />
													编辑</a>
												<a
													href="questionchild_delete.action?question.id=<s:property value="id"/>&question.parent.id=<s:property value="#pid"/>">删除</a>
											</td>
											<!--<td height="30" align="center" >
												<s:if test="1!=sortid">
													<a
														href="questionchild_upSort.action?question.sortid=<s:property value="sortid"/>&question.parent.id=<s:property value="#pid"/>&question.id=<s:property value="id" />">
														上移</a>
												</s:if>
											</td>
											<td height="30" align="center" >
												<s:if test="!#st.last">
													<a
														href="questionchild_downSort.action?question.sortid=<s:property value="sortid"/>&question.parent.id=<s:property value="#pid"/>&question.id=<s:property value="id" />">下移</a>
												</s:if>
											</td>
										--></tr>
									</s:iterator>
								</table>
								<script type="text/javascript">
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
												if(obj[i].title.length>10) 
													obj[i].innerHTML = obj[i].title.substring(0,10)+"... " ;
											}
										} 
									}
									titleLimit();
								</script>
								<div style="margin-top: 0px;">
									<!-- <a
										href="questionchild_addInit.action?question.qtype=2&question.parent.id=<s:property value="question.id"/>">添加小题</a> -->
								</div>
							</s:else>
						</td>

					</tr>
				</s:if>
				<tr>
					<td height="30" width="100" align="center" >
						<b>答案解释</b>
					</td>
					<td align=center >
						${question.qexplain }
					</td>
				</tr>
				<tr>
					<td height="30" align="center" bgcolor="#ECEDEB" colspan=2>
						<!-- <a
							href="question_alterInit.action?question.id=<s:property value="question.id"/>">修改</a> -->
					</td>
				</tr>
			</table>
		</div>
		<!-- 内容 -->

	
	</body>
</HTML>
