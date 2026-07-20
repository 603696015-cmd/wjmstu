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
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/assist.js"></script>
		<script type="text/javascript">
			function _onsubmit(){
				if($("#eroom_title").val()==''){
					alert("标题不要为空");
					$("#eroom_title").focus();
					return false; 
				}if($("#eroom_begintime").val()==''){
					alert("开始时间不要为空");
					$("#eroom_begintime").focus();
					return false; 
				}if($("#eroom_endtime").val()==''){
					alert("结束时间不要为空");
					$("#eroom_endtime").focus();
					return false; 
				}
				if($("#epid").val()==''){
					alert("请选择试卷");
					$("#epid").focus();
					return false; 
				}
				return true;
			}
		</script>
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
	</HEAD>
	<body>

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
		<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="填写练习概况" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">练习添加 </span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="examprac_list.action">练习列表</a>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<form id="form_exam_add" name="form_exam_add" method="post"
				action="examprac_add.action" onSubmit="return _onsubmit();">
				<span style="color: #ff0000;"></span>
				<table width="600px" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr>
						<td width="160" height="30" align="right">
							<span class="neededitem">*</span>练习标题：
						</td>
						<td>
							<label>
								<input name="examprac.title" type="text" id="eroom_title"
									value="" size="60" />
							</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							练习说明：
						</td>
						<td>
							<label>
								<textarea name="examprac.description" cols="60" rows="7"></textarea>
							</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<span class="neededitem">*</span>使用时间段：
						</td>
						<td>
							<label>
								考场开始时间
								<input class="Wdate" name="examprac.begintime" type="text"
									readonly="readonly" onClick="setday(this)" id="eroom_begintime" />
							</label>
							<br />
							<label>
								考场结束时间
								<input class="Wdate" name="examprac.endtime" type="text"
									readonly="readonly" onClick="setday(this)" id="eroom_endtime" />
							</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<span class="neededitem">*</span>练习次数：
						</td>
						<td bgcolor="#FFFFFF" style="padding: 0px;">
							<label style="margin-left: 10px;">
								<input name="examprac.pracCount" type="text" value="1" size="5"
									maxlength="4" />
							</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<span class="neededitem">*</span>通过成绩：
						</td>
						<td bgcolor="#FFFFFF" style="padding: 0px;">
							<label style="margin-left: 10px;">
								<input name="examprac.passgrade" type="text" value="60" size="5"
									maxlength="3" />
								%
							</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<span class="neededitem">*</span>所用试卷：
						</td>
						<td bgcolor="#FFFFFF" style="padding: 0px;">
							<span style="width: 200px;" id="eptitle"> </span>
							<input type="hidden" id='epid' name="examprac.examPaper.id"
								value="" />
							<a href="javascript:searchExamPaper();" class="textbg4"
								style="width: 80px">选择试卷</a>
						</td>
					</tr>
					<tr>
						<td height="50" align="center">&nbsp;
							

						</td>
						<td>
							<input type="submit" class="textbg4" style="width: 80px"
								value="确认添加" />
							<a class="textbg4" style="width: 80px"
								href="examprac_list.action">返回列表</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
