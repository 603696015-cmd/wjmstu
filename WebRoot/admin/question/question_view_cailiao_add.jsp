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
				
			}
			function doSubmit(){
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
		</script>
		<script type="text/javascript">
		 	function addQuestion(tid){
		 		document.getElementById("qType").value=tid;
		 		form_question_create.action="question_addInit.action";
		 		form_question_create.submit();
		 	}
		 </script>
	</HEAD>
	<BODY onLoad="myload()">
		<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="查看试题内容" />
				</div>
			</li>
		</ul>
		<!-- 内容 -->
		<%
			Question question = (Question) request.getAttribute("question");
			String ivalue = question.getQlib().getId() + "";
		%>
		<s:form action="question_add.action" name="form_question_create"
			method="post" theme="simple" onsubmit="return doSubmit();">
			<s:hidden name="question.id" />
			<s:hidden name="question.parent.id" />
			<s:hidden name="question.qtype" value="7" id="qType" />
			<div style="margin-top: 0px; text-align: center;">
				<table width="100%" cellpadding="1" cellspacing="1">
					<tr>
						<td height="30" width="150" align="center">
							<b>题型切换</b>
						</td>
						<td align=left>
							<a class="textbg4" style="width: 70px;" href='javascript:addQuestion(1);'>判断题</a>&nbsp;&nbsp;&nbsp;&nbsp;
							<a class="textbg4" style="width: 70px;" href='javascript:addQuestion(2);'>单选题</a>&nbsp;&nbsp;&nbsp;&nbsp;
							<a class="textbg4" style="width: 70px;" href='javascript:addQuestion(4);'>多选题</a>&nbsp;&nbsp;&nbsp;&nbsp;
							<a class="textbg4" style="width: 70px;" href='javascript:addQuestion(5);'>填空题</a>&nbsp;&nbsp;&nbsp;&nbsp;
							<a class="textbg4" style="width: 70px;" href='javascript:addQuestion(6);'>问答题</a>&nbsp;&nbsp;&nbsp;&nbsp;
							<a class="textbg4" style="width: 70px;" href='javascript:addQuestion(7);'>材料题</a>&nbsp;&nbsp;&nbsp;&nbsp;
							<a class="textbg4" style="width: 70px;" href='javascript:addQuestion(8);'>打字题</a>&nbsp;&nbsp;&nbsp;&nbsp;
							<a class="textbg4" style="width: 70px;" href='javascript:addQuestion(9);'>邮件题</a>&nbsp;&nbsp;&nbsp;&nbsp;
							<a class="textbg4" style="width: 70px;" href='javascript:addQuestion(10);'>搜索题</a>&nbsp;&nbsp;&nbsp;&nbsp;
							<a class="textbg4" style="width: 70px;" href='javascript:addQuestion(11);'>office题</a>
						</td>
					</tr>
					<tr>
						<td align="left" valign="top">
							<div style="float: left; text-align: left;">
								<span style="">所属知识点</span>
								<wysLib:qlibtree ivalue="<%=ivalue%>" did="0"
									iname="question.qlib.id" itype="ra" />
							</div>
						</td>
						<td>
							<table width="100%" align="center" cellpadding=1 cellspacing="1">
								<tr>
									<td height="30" width="100" align="right">
										<b>试题类别</b>
									</td>
									<td align="left">
										<label style="font-size: 16px; font-weight: bold;">
											<s:property value="question.qtypeName" />
										</label>
									</td>
								</tr>
								<%-- 	
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						<b>所属知识点</b>
					</td>
					<td align=center >
						<wysLib:qlibtree ivalue="<%=question.getQlib().getId() %>" did="0" iname="question.qlib.id" itype="ra"></wysLib:qlibtree> 
					</td>
				</tr>
				 --%>
								<tr>
									<td height="30" style="" align="right">
										<b>难度</b>
									</td>
									<td align="left">
										<s:select name="question.qlevel" theme="simple"
											list="#{1:'1',2:'2',3:'3',4:'4',5:'5'}"
											value="question.qlevel" />
										级
									</td>
								</tr>
								<tr>
									<td height="30" style="padding-left: 8px; font-weight: bolder"
										align="right">
										<b><span class="neededitem">*</span>题干</b>
									</td>
									<td align=left>
										<div>
											<s:textarea name="question.content" id="content"
												theme="simple"
												cssStyle="width:700px;height:200px;visibility:hidden;" />
										</div>
									</td>
								</tr>
								<tr>
									<td height="30" align="center" bgcolor="#ECEDEB" colspan=2>
										<%-- 	<a href="question_alterInit.action?question.id=<s:property value="question.id"/>">确认修改</a> --%>
										<input type="submit" class="textbg6" value="确认添加" />
										<input type="button" class="textbg6" style="border: none"
											onclick="document.location='question_list.action'"
											value="试题列表" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</s:form>
		<!-- 内容 -->
	</BODY>
</HTML>