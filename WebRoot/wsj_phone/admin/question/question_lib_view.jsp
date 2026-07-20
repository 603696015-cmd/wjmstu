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
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
		function deleteUserinfo(obj,id,optype){
			if(window.confirm("确定删除？")){
			depid = <s:property value="questionLib.id"/> ;
			$.post("qlib_delete_user.action", {
				"elUser.id":id,
				"questionLib.id":depid,
				"optype":optype, 
				"x":Math.random
				}, 
				function (data) {
					alert('删除成功');
				});
			obj.parentNode.parentNode.removeChild(obj.parentNode);
			}
		}
		</script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<style type="text/css">
<!--
.STYLE1 {
	color: #FF0000
}

-->
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((     this .     sectionRowIndex %     2 ==     0)
		?  
		  "#ffffff" :     "#f4f4f4" )
}
</style>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="查看基本信息" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">查看试题库信息 </span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="question_lib_alterInit.action?questionLib.id=<s:property value="questionLib.id"/>">编辑试题库信息
				</a>

			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="question_lib_deleteInit.action?questionLib.id=<s:property value="questionLib.id"/>">删除试题库信息
				</a>
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
			<table width="700" cellpadding="1" cellspacing="1">
				<tr>
					<td valign="top" width="200px" id="tree_list_td">
						<form action="question_list.action" method="post"
							name="question_manage" id="question_manage">
							<wysLib:qlibtree href="question_lib_view.action?questionLib.id="
								rootAble="true"></wysLib:qlibtree>
							<s:hidden name="exprot" id="exprot" />
							<s:hidden name="questionLib.id" id="libid" />
							<s:hidden name="question.qlib.id" id="qlibid" />
							<s:hidden name="sublibs" value="1" />
						</form>
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
					</td>
					<td valign="top">
						<table height="100%" width="100%" align="left" cellpadding="1"
							cellspacing="1" bgcolor="#EBEBEB">
							<tr>
								<td width="120px" height="30" align="right">
									目录名称：
								</td>
								<td>
									<label>
										<s:property value="questionLib.name" />
									</label>
								</td>
							</tr>
							<tr>
								<td height="80" align="right">
									目录说明：
								</td>
								<td>
									<label>
										<s:property value="questionLib.description" />
									</label>
								</td>
							</tr>
							<tr>
								<td height="30" align="right">
									上级目录：
								</td>
								<td>
									<label>
										<s:property value="questionLib.parent.name" />
									</label>
								</td>
							</tr>
							<tr>
								<td align="right">
									可管理人员：
								</td>
								<td>
									<div>
										<s:iterator value="questionLib.opusers">
											<span
												style="height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
												<label style="float: left;">
													<s:property value="realname" />
												</label> <span class="STYLE1">＊</span> </span>
										</s:iterator>
									</div>
								</td>
							</tr>
							<!--<tr>
								<td width="120" align="center" >
									可使用人员：
								</td>
								<td >
									<div id="">
										<s:iterator value="questionLib.useusers">
											<span
												style="height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
												<label style="float: left;">
													<s:property value="realname" />
											</label> <span class="STYLE1">＊</span> <!--<a
												style="cursor: hand; float: right; width: 14px; height: 14px;"
												href=""
												onclick="javascript:deleteUserinfo(this,<s:property value="id"/>,'use');return false;">X</a>-- 
											</span>
										</s:iterator>
									</div>
								</td>
							</tr>-->
							<tr>
								<td>&nbsp;
									
								</td>
								<td>
									<a
										href="question_lib_alterInit.action?questionLib.id=<s:property value="questionLib.id"/>"
										class=textbg>编辑试题库 </a>
									<a
										href="question_lib_deleteInit.action?questionLib.id=<s:property value="questionLib.id"/>"
										class=textbg>删除试题库 </a>
									<a href="question_lib_addInit.action" class="textbg">添加类别</a>
									<a href="question_lib_list.action" class="textbg">返回题库类别</a>
									<input class=textbg style="border: none;" type="button"
										value="导入试题"
										onclick='window.location ="question_importByqlibInit.action?questionLib.id=<s:property value="questionLib.id"/>"'>
									<input class=textbg style="border: none;" type="button"
										value="导出结构试题" onClick="toexcel(true);">
										
									<input class=textbg style="border: none;" type="button"
										value="试题管理" onClick="document.location='question_list.action?sublibs=1&question.status=-1&question.qlib.id=<s:property value="questionLib.id"/>'">
									
									<!-- <input class=textbg6 style="height:35px;" type="button" value="导出试题"
									onclick='window.location = "question_exportExcel.action?question.qlib.id=<s:property value="questionLib.id"/>&str=questionexecl"'>
									 -->
									 
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<!-- 内容 -->
		<table width="700" border="0" cellspacing="1" cellpadding="1"
			align="left">
			<caption>
				添加试题
			</caption>
			<tr>
				<td height="40">
					<a href='javascript:addQuestion(1);' class=textbg6>判断题</a>
				</td>
				<td>
					<a href='javascript:addQuestion(7);' class=textbg6>材料题</a>
				</td>
				<td>
					<a href='javascript:addQuestion(2);' class=textbg6>单选题</a>
				</td>
				<td>
					<a href='javascript:addQuestion(8);' class=textbg6>打字题</a>
				</td>
				<td>
					<a href='javascript:addQuestion(4);' class=textbg6>多选题</a>
				</td>
			</tr>
			<tr>
				<td height="40">
					<a href='javascript:addQuestion(9);' class=textbg6>邮件题</a>
				</td>
				<td>
					<a href='javascript:addQuestion(5);' class=textbg6>填空题</a>
				</td>
				<td>
					<a href='javascript:addQuestion(10);' class=textbg6>搜索题</a>
				</td>
				<td>
					<a href='javascript:addQuestion(6);' class=textbg6>问答题</a>
				</td>
				<td>
					<a href='javascript:addQuestion(11);' class=textbg6>office题</a>
				</td>
			</tr>
		</table>
		<form action="question_addInit.action" method="post" name="addQues">
			<input type="hidden" name="question.qlib.id"
				value="<s:property value="questionLib.id"/>" />
			<s:hidden name="question.qtype" id="qType" />
		</form>
		<script type="text/javascript">
			function toexcel(exprot) { 
				question_manage.action = "question_list.action";
				document.getElementById("exprot").value=exprot;
				document.getElementById("qlibid").value=document.getElementById("libid").value; 
				question_manage.submit();
			} 
		 	function addQuestion(tid){
		 		document.getElementById("qType").value=tid;
		 		addQues.submit();
		 	}
		 </script>
	
	</body>
</HTML>
