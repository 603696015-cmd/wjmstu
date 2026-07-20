<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.questionman.entities.QuestionLib"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
			QuestionLib qlibTree=(QuestionLib)request.getAttribute("qlbTree"); 
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
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
			function _onsubmit(){
				if(!$.trim($("#qlibname").val()).length > 0){
					alert("请填写题库名称");
					$("#qlibname").focus();
					return false;
				}
				var qlibId=$("input[name='questionLib.parent.id']:checked").val();
				if(qlibId==undefined){
					alert("请选择题库类别！");
					return false;
				}
				return window.confirm("确定信息填写无误？");
			}
		</script>
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="填写基本信息" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">添加试题库 </span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<s:form action="question_lib_add" method="post" theme="simple"
				name="exam_lib_info" id="exam_lib_info" onsubmit="return _onsubmit();">
				<table width="100%" border="0" align="left" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr>
						<td width="120" height="30" align="right" >
							<span class="neededitem">*</span>目录名称：
						</td>
						<td >
							&nbsp;<label>
								<input name="questionLib.name" type="text" id="qlibname" size="60">
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="80" align="right" >
							目录说明：
						</td>
						<td >
							&nbsp;<label>
								<textarea name="questionLib.description" cols="60" rows="4"></textarea>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right" >
							<span class="neededitem">*</span>上级目录：
						</td>
						<td >
							<label><!-- ivalue="<%=qlibTree.getId() %>" -->
								<wysLib:qlibtree did="0" iname="questionLib.parent.id" itype="ra_2no" ></wysLib:qlibtree>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="50" align="center" >&nbsp;
							
						</td>
						<td >
							&nbsp;<input class=textbg style="border: none;color: red;" name="submit" type="submit" value="确认添加" />
							<input class=textbg style="border: none;" onClick="document.location='question_lib_list.action'" name="submit" type="button" value="取  消" />
						</td>
					</tr>
				</table>
			</s:form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
