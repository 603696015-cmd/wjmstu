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
		<script type="text/javascript" src="js/libutil.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
			function _onsubmit(){
				if(!$.trim($("#pracPaper_title").val()).length > 0){
					alert("标题不要为空");
					$("#pracPaper_title").focus();
					return false;
				}
				if($("#epid").val()==''){
					alert("请选择试卷");
					$("#epid").focus();
					return false; 
				}if(!$.trim($("#nopaper").val()).length > 0){
					alert("试卷不要为空");
					return false;
				}
				return true;
			}
			function erep_add(){
				 width=600;
				 height=400;
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv =  window.showModalDialog("assist_survey_epsearchlist.action?x="+Math.random(),null,sFeature);
				 if(null==rv||undefined==rv[1]){
				 	alert('您没有选择试卷！');
				 }else{
				 	if(rv[0]<=0){
				 	  	alert('您没有选择试卷！');
			 	  	}
				 	//document.getElementById("eptitle"+id).innerHTML=rv[1];
				 	document.getElementById("epid").value=rv[0];
				 	document.getElementById("nopaper").value="nopaper";
				 	document.getElementById("eps_title").innerHTML="试卷名："+rv[1];
				 	//document.getElementById("eps_feng").innerHTML="分值："+rv[0];
				 }
			}
			
			
			
		</script>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((   this .   sectionRowIndex %   2 ==   0)
		? 
		 "#ffffff" :   "#f4f4f4" )
}
</style>
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="填写基本信息" />
				</div>
			</li>

		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		

		<div style="margin-top: 0px; text-align: center;">

			<br>
			<input type="hidden" id="nopaper" name="nopaper" />
			<form id="examroom_add" name="form_exam_add" method="post"
				action="practicepaper_add2.action" onSubmit="return _onsubmit();">
				<s:hidden id="epid" name="pracPaper.examPaper.id" />
				<s:hidden name="course.id" />
				<s:hidden id="epid" name="pracPaper.cpage.id" />
				<span style="color: #ff0000;"></span>
				<div style="font-weight: bold; font-size: 24px;">
					当前课程：
					<s:property value="course.name" />
					<s:if test="coursePage.title==''||coursePage.title==null"></s:if>
					<s:else>(章节：<s:property value="coursePage.title" />)</s:else>
				</div>
				<table width="950" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr>
						<td width="100" height="30" align="center">
							<span class="neededitem">*</span>练习标题
						</td>
						<td>
							<label>
								<input name="pracPaper.title" type="text" id="pracPaper_title"
									value="" size="60" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="100" height="30" align="center">
							<span class="neededitem">*</span>通过成绩
						</td>
						<td>
							<label>
								<input type="text" style="width: 40px;"
									name="pracPaper.passgrade" id="passgrade" value="60">
								%
							</label>
						</td>
					</tr>
					<tr>
						<td width="100" height="30" align="center">
							<span class="neededitem">*</span>所用试卷
						</td>
						<td>
							<div id="eps_div">
								<span id="eps_title"></span>&nbsp;&nbsp;&nbsp;
								<span id="eps_feng"></span>
								<a href="" onClick="erep_add(); return false;" class=textbg4>选择</a>
							</div>
						</td>
					</tr>
					<tr><td></td><td height="35px"><input type="submit" class="textbg4" value="提交" />  <input type="button" onclick="document.location='practicepaper_list.action?course.id=${course.id}&pracPaper.course.id=${course.id}&pracPaper.cpage.id=0'" class="textbg4" value="取消" /></td><tr>
				</table>
			</form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
