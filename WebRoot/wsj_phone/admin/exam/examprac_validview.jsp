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
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/assist.js"></script>
	<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="详情概览" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">练习审核 </span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="examprac_validlist.action"/>审核列表</a>
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
			<form id="form_exam_add" name="form_exam_add" method="post"
				action="examprac_alter.action" onSubmit="return _onsubmit();">
				<span style="color: #ff0000;"></span>
				<table width="90%" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr>
						<td width="160" height="30" align="center" >
							练习标题
						</td>
						<td >
							<label>
								<s:property value="examprac.title" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							练习说明
						</td>
						<td >
							<label>
								<s:property value="examprac.description" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							使用时间段
						</td>
						<td >
							<label>
								开始时间
								<s:property value="examprac.begintime" />
							</label>
							<br />
							<label>
								结束时间
								<s:property value="examprac.endtime" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							所用试卷
						</td>
						<td >
							<s:property value="examprac.examPaper.title" />
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							人员
						</td>
						<td style="padding: 0px">
							<table width="100%" style="margin-top: 0px;" align="center" cellpadding="1"
								cellspacing="1" >
								<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
										姓名
									</td>
									<td height="30" align="center" >
										账号
									</td>
									<td height="30" align="center" >
										部门
									</td>
								</tr>
								<s:iterator value="elusers">
									<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
											<s:property value="realname" />
										</td>
										<td height="30" align="center" >
											<s:property value="username" />
										</td>
										<td height="30" align="center" >
											<s:property value="department.name" />
										</td>
									</tr>
								</s:iterator>
							</table>
							<div style="text-align:center;"><wysLib:page></wysLib:page></div>
						</td>
					</tr>
					<tr>
						<td width="160" height="50" align="center" >
							状态
						</td>
						<td >
							<s:property value="examprac.validName" />
						</td>
					</tr>
					<tr>
						<td width="160" height="50" align="center" >
							<input type="hidden" name="examprac.id"
								value="<s:property value="examprac.id"/>" />
						</td>
						<td >
							<script type="text/javascript">
							function sh_p(){
								if(window.confirm("确定让它通过审核？"))
									document.location.href="examprac_validpass.action?examprac.id=<s:property value="examprac.id"/>"
							} 
							function sh_np(){
								if(window.confirm("确定让它不通过审核？"))
									document.location.href="examprac_validunpass.action?examprac.id=<s:property value="examprac.id"/>"
							}
							function page(i){ 
						 		document.getElementById("pageNow").value=i;
						 		page_list.submit();
						 	}
						</script>
							<s:if test="examprac.valid==0||examprac.valid==2">
								<input class="textbg6" type="button" name="button2" onClick="sh_p();"
									id="button2" value="通过审核" />
								<input class="textbg6" type="button" name="button2" onClick="sh_np();"
									id="button2" value="不通过" />
							</s:if>
						</td>
					</tr>
				</table>
				<br/>
				<a href="examprac_validlist.action" style="width: 100px" class="textbg4">返回审核列表</a>
			</form>
			<s:form action="examprac_validview.action" method="post" name="page_list" theme="simple">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<s:hidden name="examprac.id" />
			</s:form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
