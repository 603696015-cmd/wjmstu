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
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
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
		<script type="text/javascript" src="js/libutil.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
			function doSubmit(){
				var titleObj=document.getElementById("eplibName");
				var title=titleObj.value.replace(/(\s*$)/g, "");
				if(title==""){
					alert("试卷库名称不能为空!");
					titleObj.focus();
					return false;
				}
				var qlibpid = $("input[name='examPaperLib.parent.id']:checked").val();
				var libId="<s:property value="examPaperLib.id" />";
				if(libId!=1){
					if(qlibpid==''||qlibpid==undefined){
						alert("请选择试卷库");
						return false;
					}
				}
				return true;
			}
		</script>
		<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
        </style>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="修改试卷库" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">编辑试卷库信息</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="exampaperLib_view.action?examPaperLib.id=<s:property value="examPaperLib.id"/>">查看试卷库信息
				</a>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="exampaperLib_deleteInit.action?examPaperLib.id=<s:property value="examPaperLib.id"/>">删除试卷库信息
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
			<s:form action="exampaperLib_alter" method="post" theme="simple"
				name="exam_lib_info" id="exam_lib_info" onsubmit="return doSubmit();">
				<table border="0" width="700" align="left" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr>
						<td width="120" height="30" align="right" >
							<span class="neededitem">*</span>目录名称：
						</td>
						<td >
							<label>
								<s:textfield name="examPaperLib.name" id="eplibName" size="60" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="80" align="right" >
							目录说明：
						</td>
						<td >
							<label>
								<s:textarea name="examPaperLib.description" cols="60" rows="4" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right" >
						<span class="neededitem">*</span>上级目录：<%
										String xx = ((com.sopia.questionman.entities.ExamPaperLib) request
													.getAttribute("examPaperLib")).getParent().getId()
													+ "";
									%>

						</td>
						<td >
							<s:if test="examPaperLib.parent.id==0">
								根节点没有父节点
							</s:if>
							<s:else>
								<label>
									<wysLib:elibtree did="1" iname="examPaperLib.parent.id" ivalue="<%=xx%>" 
										itype="ra_f" iid="${examPaperLib.id}"></wysLib:elibtree>
								</label>
							</s:else>
						</td>
					</tr>
					<tr>
						<td width="120" align="right" >
							可操作人员：
						</td>
						<td >
							<div id="can_op">
								<s:iterator value="examPaperLib.opusers">
									<span
										style="height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
										<label style="float: left;">
											<s:property value="realname" />
										</label> <span class="STYLE1">＊</span> 
									</span>
								</s:iterator>
							</div>
						</td>
					</tr>
						<!--<tr>
						<td width="120" align="center" >
							可使用人员：
						</td>
						<td >
							<div id="can_use">
								<s:iterator value="examPaperLib.useusers">
									<span
										style="height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
										<label style="float: left;">
											<s:property value="realname" />
										</label> <span class="STYLE1">＊</span><!--<a
										style="cursor: hand; float: right; width: 14px; height: 14px;"
										href=""
										onclick="javascript:deleteUserinfo(this,<s:property value="id"/>,'use');return false;">X</a>-- 
									</span>
								</s:iterator>
							</div>
							<!--<a href=""
								onclick="searchUserInit('can_use','examPaperLib.useusers.id'); return false;">授权</a>-- 
						</td>
					</tr>-->
					<tr>
						<td width="120" height="50" align="center" >
							<s:hidden name="examPaperLib.id" />
						</td>
						<td >
							<input name="submit" type="submit" style="border: none;color: red;" class="textbg" value="确认修改" /> 
							<a href="exampaperLib_view.action?examPaperLib.id=<s:property value="examPaperLib.id"/>" class=textbg>取 消</a>
				</td>
					</tr>
			  </table>
			</s:form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
