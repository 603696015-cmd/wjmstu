<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.common.SystemConfOp"%>
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
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%;
}

tr {
	background-color: expression((     this .     sectionRowIndex %     2 ==     0)
		?  
				  "#ffffff" :     "#f4f4f4" )
}
</style>
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
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="查看试题内容" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">查看试题</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="question_alterInit.action?question.id=<s:property value="question.id"/>">编辑试题</a>

			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="question_lib_deleteInit.action?questionLib.id=<s:property value="questionLib.id"/>">删除试题库信息
				</a>
			</li>
			<s:if test="question.qtype==7">
				<li class="sep">
				</li>
				<li>
					<a
						href="questionchild_addInit.action?question.qtype=2&question.parent.id=<s:property value="question.id"/>">添加小题</a>
				</li>
			</s:if>-->

		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		

		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<table width="100%" align="center" cellpadding=1 cellspacing="1" bgcolor="#D1E4F5">
				<tr>
					<td width="150" height="30" align="right" bgcolor="#F8FCFE" style="text-align:right;">
						<b>题目类型：</b>
					</td>
					<td align="left" width="448px" bgcolor="#F8FCFE">
						<s:property value="question.qtypeName" />
					</td>
				</tr>
				<tr>
					<td height="30" align="right" bgcolor="#F8FCFE">
						<b>所属题库：</b>
					</td>
					<td align="left" bgcolor="#F8FCFE">
						<s:property value="question.qlib.name" />
					</td>
				</tr>
				<tr>
					<td height="30" align="right" bgcolor="#F8FCFE">
						<b>难度：</b>
					</td>
					<td align="left" bgcolor="#F8FCFE">
						<s:property value="question.qlevel" />
						级
					</td>
				</tr>
				<%-- 
				<tr>
					<td height="30" align="center" >
						<b>试题名称</b>
					</td>
					<td align=center >
						<s:property value="question.title" />
					</td>
				</tr>
				 --%>
				<tr>
					<td height="30" align="right" bgcolor="#F8FCFE">
						<b>题干：</b>
					</td>
					<td align="left" bgcolor="#F8FCFE">
						${question.content_}
					</td>
				</tr>
				<s:if test="question.qtype==1">
					<tr>
						<td height="30" align="right" bgcolor="#F8FCFE">
							<b>试题答案：</b>
						</td>
						<td align="left" bgcolor="#F8FCFE">
							<s:if test="question.answers[0]=='yes'">正确</s:if>
							<s:else> 错误 </s:else>
						</td>
					</tr>
				</s:if>
				<s:if test="question.qtype==2||question.qtype==3||question.qtype==4||question.qtype==15||question.qtype==16">
					<tr>
						<td height="30" align="right" bgcolor="#F8FCFE">
							<b>试题选项：</b>
						</td>
						<td align="left" bgcolor="#F8FCFE">
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
						<td height="30" align="right" bgcolor="#F8FCFE">
							<b>试题答案：</b>
						</td>
						<td align="left" bgcolor="#F8FCFE">
							<s:iterator value="question.answers" status="sel_answer">
								<b id="ans_ABC_<s:property />"><script
										type="text/javascript">intoABC('<s:property />',"ans_ABC_<s:property />")</script>
									<s:if test="question.answers.length>1+#sel_answer.index">、</s:if>
								</b>
							</s:iterator>
						</td>
					</tr>
				</s:if>
				<s:if test="question.qtype==18">
					<tr>
						<td height="30" align="right" bgcolor="#F8FCFE">
							<b>试题选项：</b>
						</td>
						<td align="left" bgcolor="#F8FCFE">
							<script type="text/javascript">
							function intoABC(i,id){
									if(!i.match(/^[\d]+$/)){i=0}
									
									document.getElementById(id).innerHTML=String.fromCharCode(65+parseInt(i,10)); 
								}
						</script>
						
							<s:iterator value="question.options" status="qa">
								<b id="opt_ABC_<s:property value="#qa.index" />"><script
										type="text/javascript">intoABC('<s:property value="#qa.index" />',"opt_ABC_<s:property value="#qa.index" />")</script>：</b>
								<img alt="" src="<s:property />" height="200" width="100"/>
								<br>
							</s:iterator>
							
						</td>
					</tr>
					<tr>
						<td height="30" align="right" bgcolor="#F8FCFE">
							<b>试题答案：</b>
						</td>
						<td align="left" bgcolor="#F8FCFE">
							<s:iterator value="question.answers" status="sel_answer">
								<b id="ans_ABC_<s:property />"><script
										type="text/javascript">intoABC('<s:property />',"ans_ABC_<s:property />")</script>
									<s:if test="question.answers.length>1+#sel_answer.index">、</s:if>
								</b>
							</s:iterator>
						</td>
					</tr>
				</s:if>
				<s:if test="question.qtype==5||question.qtype==17||question.qtype==19||question.qtype==20">
					<tr>
						<td height="30" align="right" bgcolor="#F8FCFE">
							<b>空白答案：</b>
						</td>
						<td align=left bgcolor="#F8FCFE">
							<s:iterator value="question.answers" status="bla_answer">
								<s:property />
								<s:if test="question.answers.length>1+#bla_answer.index">、</s:if>
							</s:iterator>
						</td>
					</tr>
				</s:if>
				<s:if test="question.qtype==11">
					<tr>
						<td height="30" align="right" bgcolor="#F8FCFE">
							<b>office模版：</b>
						</td>
						<td align=left bgcolor="#F8FCFE">
							 <a href="<%=SystemConfOp.getStuffUrl() %>download.jsp?filename=<s:property value="question.subject"/>">模版文件</a>
						</td>
					</tr>
				</s:if>
				<s:if test="question.qtype==6">
					<tr>
						<td height="30" align="right" bgcolor="#F8FCFE">
							<b>答题字数限制：</b>
						</td>
						<td align="left" bgcolor="#F8FCFE">
							<s:property value="question.minWord" />
							个
						</td>
					</tr>
					<tr>
						<td height="30" align="right" bgcolor="#F8FCFE">
							<b>试题答案关键词：</b>
						</td>
						<td align="left" bgcolor="#F8FCFE">
							<s:iterator value="question.answers" status="e_answer">
								<s:property />
								<s:if test="question.answers.length>1+#e_answer.index">、</s:if>
							</s:iterator>
						</td>
					</tr>
				</s:if>
				<s:if test="question.qtype==9">
					<tr>
						<td height="30" align="right" bgcolor="#F8FCFE">
							<b>答题字数限制：</b>
						</td>
						<td align="left" bgcolor="#F8FCFE">
							<s:property value="question.minWord" />
							个
						</td>
					</tr>
					<tr>
						<td height="30" align="right" bgcolor="#F8FCFE">
							<b>试题答案关键词：</b>
						</td>
						<td align="left" bgcolor="#F8FCFE">
							<s:iterator value="question.answers" status="x_answer">
								<s:property />
								<s:if test="question.answers.length>1+#x_answer.index">、</s:if>
							</s:iterator>
						</td>
					</tr>
				</s:if>
				<s:if test="question.qtype==7">
					<tr>
						<td height="30" align="right" bgcolor="#F8FCFE">
							<b>该题的小题：</b>
						</td>
						<td align="left" bgcolor="#F8FCFE">
							<s:if test="question.childs.size==0">
								本材料题目前还没小题
								<br>
								<%--  <a href="questionchild_addInit.action?question.qtype=2&question.parent.id=<s:property value="question.id"/>">添加小题</a> --%>
							</s:if>
							<s:else>
								<table width="100%" align="right" cellspacing="1"
									style="border: 1px solid black;">
									<tr>
										<%-- 
										<td height="30" style="padding-left: 8px; color: blue;"
											align="left">
											&nbsp;
										</td>
										 --%>
										<td height="30" align="center">
											编号
										</td>
										<td height="30" align="center">
											题干
										</td>
										<td height="30" align="center">
											题目类型
										</td>
										<td height="30" align="center">
											创建时间
										</td>
										<td height="30" align="center">
											分值(%)
										</td>
										<%-- 
										<td height="30" align="center">
											&nbsp;
										</td>
										 --%>
										<!--<td height="30" colspan="2" align="center" >
											&nbsp;
										</td>
									-->
									</tr>
									<s:set name="pid" value="question.id" />
									<s:iterator value="question.childs" status="st">
										<tr>
											<%-- 
											<td height="30" style="padding-left: 8px; color: blue;"
												align="left">
												<input type="checkbox" name="questions.id"
													value="<s:property value="id"/>">
											</td>
											 --%>
											<td height="30" align="center">
												<s:property value="sortid" />
											</td>
											<td height="30" align="center">
												<%-- 
												<a name="tdTitle" title="<s:property value="title" />"
													href="question_view.action?question.id=<s:property value="id" />">
													<s:property value="title" /> </a>
												 --%>
												<s:if test="(title+'').length()>20">
													<s:property value="(title+'').substring(0,20)+'...'" />
												</s:if>
												<s:else>
													<s:property value="title" />
												</s:else>
											</td>
											<td height="30" align="center">
												<s:property value="qtypeName" />
											</td>
											<td height="30" align="center">
												<s:date format="yyyy-MM-dd HH:mm:ss" name="createtime" />
											</td>
											<td height="30" align="center">
												<s:property value="scoreper" />
											</td>
											<%-- 
											<td height="30" align="center">
												
												<a href="questionchild_alterInit.action?question.id=<s:property value="id" />&question.parent.id=${question.id}" />
													编辑</a>
												<a
													href="questionchild_delete.action?question.id=<s:property value="id"/>&question.parent.id=<s:property value="#pid"/>">删除</a>
											</td>
											 --%>
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
										-->
										</tr>
									</s:iterator>
								</table>
								<%-- 
								<div style="margin-top: 0px;">
									<a href="questionchild_addInit.action?question.qtype=2&question.parent.id=<s:property value="question.id"/>">添加小题</a>
								</div>
								 --%>
							</s:else>
						</td>

					</tr>
				</s:if>
				<s:if test="question.qtype!=8">
					<tr>
						<td height="30" align="right" bgcolor="#F8FCFE">
							<b>答案解释：</b>
						</td>
						<td align="left" bgcolor="#F8FCFE">
							${question.qexplain }
						</td>
					</tr>
				</s:if>
				<%-- 
				<tr>
					<td height="30" align="center" bgcolor="#FFFFFF" colspan=2>
						<a href="question_alterInit.action?question.id=<s:property value="question.id"/>">修改</a>
					</td>
				</tr>
				 --%>
			</table>
			<input type="button" class="textbg" style="border: none"
				onclick="document.location='question_list.action'" value="返回试题列表" />
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>