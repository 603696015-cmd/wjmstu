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
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
</style>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/libutil.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
			function _onsubmit(){
				if(!$.trim($("#qlibname").val()).length > 0){
					alert("请填写题库名称");
					$("#qlibname").focus();
					return false;
				}
				var qlibId=$("input[name='questionLib.parent.id']:checked").val();
				var libId="<s:property value="questionLib.id" />";
				if(qlibId==undefined&&libId!=1){
					alert("请选择题库类别！");
					return false;
				}
				return window.confirm("确定信息填写无误？");
			}
		</script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<style type="text/css">
<!--
.STYLE1 {
	color: #FF0000
}
-->
</style>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="修改基本信息" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">编辑试题库信息 </span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="question_lib_view.action?questionLib.id=<s:property value="questionLib.id"/>">查看试题库信息
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
		<div style="margin-top:0px;">

			<s:form action="question_lib_alter" method="post" theme="simple"
				name="exam_lib_info" id="exam_lib_info"
				onsubmit="return _onsubmit();">
				<table width="700px" border="0" align="left" cellpadding="1"
					cellspacing="1" bgcolor="#EBEBEB">
					<tr>
						<td width="120" height="30" align="right">
							<span class="neededitem">*</span>目录名称：
						</td>
						<td>
							<label>
								<s:textfield name="questionLib.name" id="qlibname" size="60" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="80" align="right">
							目录说明：
						</td>
						<td>
							<label>
								<s:textarea name="questionLib.description" cols="60" rows="4" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right">
							<span class="neededitem">*</span>上级目录：<%
							String xx = ((com.sopia.questionman.entities.QuestionLib) request
										.getAttribute("questionLib")).getParent().getId()
										+ "";
						%>

						</td>
						<td>
							<s:if test="questionLib.parent.id==0">
										根节点没有父节点
									</s:if>
							<s:else>
								<label>
									<!-- ivalue="${questionLib.parent.id}" -->
									<wysLib:qlibtree ivalue="<%=xx%>" did="0"
										iname="questionLib.parent.id" itype="ra_f"
										iid="${questionLib.id}"></wysLib:qlibtree>
								</label>
							</s:else>
						</td>
					</tr>
					<tr>
						<!--	<td width="120" align="center" >
										可管理人员：
									</td>
									<td >
										<div id="can_op">
											<s:iterator value="questionLib.opusers">
												<span
													style="height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
													<label style="float: left;">
														<s:property value="realname" />
											</label> <span class="STYLE1">＊</span><a
													style="cursor: hand; float: right; width: 14px; height: 14px;"
													href=""
													onclick="javascript:deleteUserinfo(this,<s:property value="id"/>,'op');return false;">X</a>
												</span>
											</s:iterator>
							</div>
							<a href=""
											onclick="searchUserInit('can_op','questionLib.opusers.id'); return false;">授权</a>
									</td>
								</tr>
								<tr>-->
						<td width="120" align="right">
							可管理人员：
						</td>
						<td>
							<div id="can_op">
								<s:iterator value="questionLib.opusers">
									<span
										style="height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
										<label style="float: left;">
											<s:property value="realname" />
										</label> <span class="STYLE1">＊</span> <!--<a
													style="cursor: hand; float: right; width: 14px; height: 14px;"
													href=""
													onclick="javascript:deleteUserinfo(this,<s:property value="id"/>,'op');return false;">X</a>-->
									</span>
								</s:iterator>
							</div>
							<!--<a href=""
											onclick="searchUserInit('can_use','questionLib.useusers.id'); return false;">授权</a>-->
						</td>
					</tr>
					<tr>
						<td width="120" height="50" align="center">
							<s:hidden name="questionLib.id" />
						</td>
						<td>
							<input class=textbg style="border: none;color: red;" name="submit"
								type="submit" value="确认修改" />
							<input class=textbg style="border: none" onClick="document.location='question_lib_view.action?questionLib.id=${questionLib.id }'" type="button" value="取 消" />
						</td>
					</tr>
				</table>
			</s:form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
