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
	<script type="text/javascript" src="js/cexampaper.js"></script>
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="选择题型" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">添加试题</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<s:property value="elmessage" />
			<table width="400" border="0" cellspacing="1" cellpadding="1">
				<caption>
					选择试题类型
				</caption>
				<tr>
					<td width="50%" height="40">
						<a href='javascript:addQuestion(1);' class=textbg6>判断题</a>	</td>
				    <td><a href='javascript:addQuestion(7);' class=textbg6>材料题</a></td>
				</tr>
				<tr>
					<td width="50%" height="40">
						<a href='javascript:addQuestion(2);' class=textbg6>单选题</a>					</td>
				    <td><a href='javascript:addQuestion(8);' class=textbg6>打字题</a></td>
				</tr>
				<tr>
					<td width="50%" height="40">
								<a href='javascript:addQuestion(4);' class=textbg6>多选题</a>				</td>
				    <td><a href='javascript:addQuestion(9);' class=textbg6>邮件题</a></td>
				</tr>
				<tr>
					<td width="50%" height="40">
								<a href='javascript:addQuestion(5);' class=textbg6>填空题</a>			</td>
				    <td><a href='javascript:addQuestion(10);' class=textbg6>搜索题</a>	</td>
				</tr>
				<tr>
					<td width="50%" height="40">
								<a href='javascript:addQuestion(6);' class=textbg6>问答题</a>			</td>
				    <td><a href='javascript:addQuestion(11);' class=textbg6>office题</a>	</td>
				</tr>
				<tr>
					
				    <td width="50%" height="40">
				    <a href='javascript:addQuestion(15);' class=textbg6>看图选择</a>	</td>
				    <td> <a href='javascript:addQuestion(16);' class=textbg6 style="width:80px">看动画选择</a>	</td>
				</tr>
				<tr>
					
				    <td width="50%" height="40">
				    <a href='javascript:addQuestion(17);' class=textbg6>角色扮演</a>	</td>
				     <td> <a href='javascript:addQuestion(18);' class=textbg6 style="width:80px">听音选图</a>	</td>
				</tr>
				<tr>
					
				    <td width="50%" height="40">
				    <a href='javascript:addQuestion(19);' class=textbg6>拖拽题</a>	</td>
				     <td> <a href='javascript:addQuestion(20);' class=textbg6 style="width:80px">排序题</a>	</td>
				</tr>
				<!--<tr>
					<td width="50%" height="40">
										</td>
				    <td><a href='question_addInit.action?question.qtype=3' class=textbg6>不定项选择</a></td>
				</tr>-->
		  </table><br/>
		  <a href="question_list.action" class="textbg4" style="width:100px">返回试题列表</a>
		</div>
		<!-- 内容 -->
		 <form action="question_addInit.action" method="post" name="addQues">
		 	<input type="hidden" name="question.qlib.id" value="<s:property value="question.qlib.id"/>" />
		 	<input type="hidden" name="question.qtype" value="" id="qType"/>
		 </form>
		 <script type="text/javascript">
		 	 function addQuestion(tid){
		 		document.getElementById("qType").value=tid;
		 		addQues.submit();
		 	 }
		 </script>
	
	</body>
</HTML>
