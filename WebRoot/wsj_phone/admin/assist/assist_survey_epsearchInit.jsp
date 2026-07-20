<%@ page language="java" pageEncoding="UTF-8"   %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>查找试卷</TITLE>
		<base target="_self">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<SCRIPT type="text/javascript">
			function backoper(){
				papers_info.action="assist_survey_epsearchInit.action";
				papers_info.submit();
			}
		
		</SCRIPT>
		<style type="text/css"> 
		td {font-size:12px;color:#333333;line-height:150%}
		tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="查找试卷" /></div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<form action="assist_survey_epsearchlist.action" method="post"
				name="papers_info" id="papers_info">
				<table cellpadding="1" cellspacing="1" width="400px">
					<tr>
						<td>
							试卷名称关键字
						</td>
						<td>
							<input type="text" name="examPaper.title" id="name">
							<input type="hidden" name="operate_search" value="ok">
						</td>
					</tr>
					<tr>
						<td>
							所属试卷库
						</td>
						<td>
							<wysLib:elibtree iname="examPaper.epl.id" itype="ra"></wysLib:elibtree>
						</td>
					</tr>
					<tr>
						<td>
							包含子试卷库
						</td>
						<td>
							<input type="checkbox" checked="checked" name="sublibs" id="sublibs" value="1">
						</td>
					</tr>
					<tr>
						<td>
						</td>
						<td>
							<input type="hidden" name="pN" value="0">
							<input type="hidden" name="pS" value="10">
							<input type="submit" class="textbg4" value="搜索">
						</td>
					</tr>
				</table>
			</form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
