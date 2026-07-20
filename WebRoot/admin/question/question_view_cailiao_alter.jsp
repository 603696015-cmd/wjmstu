<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.questionman.entities.Question"%>
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
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="editor/fckeditor.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript">
			function myload(){
		  	 	var oFCKeditor = new FCKeditor('content') ;
				oFCKeditor.BasePath = "editor/" ;
				oFCKeditor.Height = 200;
				oFCKeditor.Width = 650;
				oFCKeditor.ToolbarSet = "qcontent" ;
				oFCKeditor.ReplaceTextarea();
				var qlibs = document.getElementsByName("question.qlib.id");
				for(var i  = 0 ; i <qlibs.length;i++){
					//alert(qlibs[i].value);
					if(qlibs[i].value==${question.qlib.id}){
						qlibs[i].checked = "checked";
						break;
					}
				}
				var stds = $(".score_td");
				for(var i = 0 ; i<stds.length;i++){
					$(stds[i]).bind("click",function(){alterScore(this)});
				}
			}
			function alterScore(o){
				//alert($(o).attr("data-id")+"-"+$(o).attr("data-score"));
				//nid = $(o).attr("data-id");
				var inp = $("<input>");
				$(inp).attr("type","text");
				$(inp).attr("size","4");
				$(inp).attr("value",$(o).attr("data-score"));
				$(inp).bind("blur",function(){
					var v = $(this).val();
					$(o).html( v+"%");
					$(o).bind("click",function(){alterScore(this)});
					$.ajax(
					{	async:false, 
						type:"post",   
					    url:"question_alter_scorepre.action",   
					    data:{"x":Math.random(),"question.id":$(o).attr("data-id"),"question.scoreper":v},   
						success:function(data){err_mess  = data ;}});
				})
				$(o).html(inp);
				$(o).unbind("click");
				$(inp).focus();
				//alert("d");
			}
			function doSubmit(){
				//alert("cc");
				/*
				var title=document.getElementById("content");
				title=title.value.replace(/(\s*$)/g, "");
				//alert(title);
				if(title==""){
					alert("题干不能为空!");
					return false;
				}
				*/
				var qlibId=$("input[name='question.qlib.id']:checked").val();
				if(qlibId==undefined){
					alert("请选择题库！");
					return false;
				}
				if(FCKeditorAPI.GetInstance("content").GetXHTML(true)==''){
					alert("题干不要为空");
					//document.getElementById("content").focus();
					//FCKeditorAPI.GetInstance("content").GetXHTML(true).focus();
					return false;
				}
				return true;
			}
			function copyQuestion(){
				form_question_create.action = "question_add.action";
				form_question_create.submit();
			}
		</script>
	</HEAD>
	<BODY onLoad="myload()">
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="查看试题内容" /></div>
			</li>
		<!-- 内容 -->
		<%
			Question question=(Question)request.getAttribute("question");
			String ivalue=question.getQlib().getId()+"";
		%>
		<s:form action="question_alter.action" name="form_question_create" method="post" theme="simple" onsubmit="return doSubmit();"> 
		<s:hidden name="question.id" />
		<s:hidden name="question.qtype"></s:hidden>
		<s:hidden name="question.parent.id"></s:hidden>
		<div style="margin-top: 0px; text-align: center;">
			<table width="100%" border="0">
  <tr>
   <td align="left" valign="top"><div style="float:left;text-align:left;">
				<span style="">所属知识点</span>
				<wysLib:qlibtree ivalue="<%=ivalue %>" did="0" iname="question.qlib.id" itype="ra" />
			</div></td>
    <td><table width="100%" align="center" cellpadding=1 cellspacing="1">
				<tr>
					<td height="30" width="100" align="center">
						<b>试题类别</b>					</td>
					<td align=center>
						<label style="font-size: 16px; font-weight: bold;">
							<s:property value="question.qtypeName" />
						</label>
				  </td>
				</tr>
				<%-- 
				<tr>
					<td height="30" align="center" bgcolor="#ECEDEB">
						<b>所属知识点</b>
					</td>
					<td align=center bgcolor="#ECEDEB">
						<wysLib:qlibtree ivalue="${question.qlib.id}" did="0" iname="question.qlib.id" itype="ra"></wysLib:qlibtree> 
					</td>
				</tr>
				 --%>
				<tr>
					<td height="30" align="center"> 
						<b>难度</b>					</td>
					<td align=center>
					<s:select name="question.qlevel" theme="simple" list="#{1:'1',2:'2',3:'3',4:'4',5:'5'}" value="question.qlevel"/>
						级
				  </td>
				</tr>
				<%-- 
				<tr>
					<td height="30" width="100" align="center" bgcolor="#ECEDEB">
						<b>试题名称</b>
					</td>
					<td align=center bgcolor="#ECEDEB">
						<s:property value="question.title" />
					</td>
				</tr>
				 --%>
				<tr>
					<td height="30" align="center">
						<b>题干</b>					</td>
					<td align=left>
						<div>
							<s:textarea name="question.content" id="content" theme="simple"
								cssStyle="width:700px;height:200px;visibility:hidden;" />
						</div>
				  </td>
				</tr>
				<s:if test="question.qtype==7">
					<tr>
						<td height="30" align="center">
							<b>该题的小题</b>						</td>
						<td align="center" bgcolor="#ECEDEB">
							<s:if test="question.childs.size==0">
								本材料题目前还没小题
							<br>
								<s:if test="copy!=1">
									<a href="questionchild_addInit.action?question.qtype=2&question.parent.id=<s:property value="question.id"/>">添加小题</a>
									<a href="question_listInC.action?questionParid=<s:property value="question.id"/>">题库添加</a>
								</s:if>
							</s:if>
							<s:else>
								<table width="100%" align="center" cellspacing="1"
									style="border: 1px solid black;">
									<tr>
										<%-- 
										<td height="30" align="center" bgcolor="#ECEDEB">&nbsp;
											
										</td>
										 --%>
										<td height="30" align="center" bgcolor="#ECEDEB">
											编号
										</td>
										<td height="30" align="center" bgcolor="#ECEDEB">
											题干
										</td>
										<td height="30" align="center" bgcolor="#ECEDEB">
											题目类型
										</td>
										<td height="30" align="center" bgcolor="#ECEDEB">
											创建时间
										</td>
										<td height="30" align="center" bgcolor="#ECEDEB">
											分值(%)
										</td>
										<td height="30" align="center" bgcolor="#ECEDEB">&nbsp;
											
										</td>
										<!--<td height="30" colspan="2" align="center" bgcolor="#ECEDEB">
											&nbsp;
										</td>
									--></tr>
									<s:set name="pid" value="question.id" />
									<s:iterator value="question.childs" status="st">
										<tr>
											<%-- 
											<td height="30" align="center" bgcolor="#ECEDEB">
												<input type="checkbox" name="questions.id"
													value="<s:property value="id"/>">
											</td>
											 --%>
											<td height="30" align="center" bgcolor="#ECEDEB">
												<s:property value="sortid" />
											</td>
											<td height="30" align="center" bgcolor="#ECEDEB">
												<a name="tdTitle" title="<s:property value="title" />"
													href="question_view.action?question.id=<s:property value="id" />">
													<s:property value="title" /> </a>
											</td>
											<td height="30" align="center" bgcolor="#ECEDEB">
												<s:property value="qtypeName" />
											</td>
											<td height="30" align="center" bgcolor="#ECEDEB">
												<s:date format="yyyy-MM-dd HH:mm:ss" name="createtime" />
											</td>
											<td <s:if test="copy!=1"> class="score_td" </s:if>
											  height="30" align="center" bgcolor="#ECEDEB" data-id="<s:property value="id" />" data-score="<s:property value="scoreper" />">
												<s:property value="scoreper" />%
									  </td>
											<td height="30" align="center" bgcolor="#ECEDEB">
												<s:if test="copy!=1">
													<a
														href="questionchild_alterInit.action?question.id=<s:property value="id" />&question.parent.id=${question.id}" />
														编辑</a>
													<a
														href="questionchild_delete.action?question.id=<s:property value="id"/>&question.parent.id=<s:property value="#pid"/>&isCaiLiao=1">删除</a>
												</s:if>
											</td>
											<!--<td height="30" align="center" bgcolor="#ECEDEB">
												<s:if test="1!=sortid">
													<a
														href="questionchild_upSort.action?question.sortid=<s:property value="sortid"/>&question.parent.id=<s:property value="#pid"/>&question.id=<s:property value="id" />">
														上移</a>
												</s:if>
											</td>
											<td height="30" align="center" bgcolor="#ECEDEB">
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
								<s:if test="copy!=1">
									<div style="margin-top: 0px;">
										<a href="questionchild_addInit.action?question.qtype=2&question.parent.id=<s:property value="question.id"/>">手工添加</a>
										<a href="question_listInC.action?questionParid=<s:property value="question.id"/>">题库添加</a>
									</div>
								</s:if>
							</s:else>
						</td>
					</tr>
				</s:if>
				<%-- 
				<tr>
					<td height="30" width="100" align="center" bgcolor="#ECEDEB">
						<b>答案解释</b>
					</td>
					<td align=center bgcolor="#ECEDEB">
						${question.qexplain }
					</td>
				</tr>
				 --%>
				<tr>
					<td height="30" align="center" colspan=2>
					<%-- 	<a href="question_alterInit.action?question.id=<s:property value="question.id"/>">确认修改</a> --%>
					<%-- <input type="submit" value="确认修改" /> --%>
					<s:if test="copy==1">
						<input type="button" onclick="copyQuestion();" name="button"
							id="button" value="提交" />
					</s:if>
					<s:else>
						<input type="submit" name="button" id="button" value="确认修改" />
					</s:else>
				  </td>
				</tr>
		  </table></td>
  </tr>
</table>	
		</div>
		</s:form>
		<!-- 内容 -->
	</BODY>
</HTML>