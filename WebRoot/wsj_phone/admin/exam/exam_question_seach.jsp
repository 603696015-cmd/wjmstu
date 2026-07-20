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
		<TITLE>练习管理</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript">
			function searchQuestionInit(){
			     width=600;
				 height=500;
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv =  window.showModalDialog("eprac_question_select.action?x="+Math.random(),null,sFeature);
				 //alert(rv);
				 if(rv!=undefined&&rv!=""){
					 var arr=rv.split("_");
					 document.getElementById("qlibId").value=arr[2];
					 document.getElementById("questionLibName").innerHTML=arr[1];
					 document.getElementById("qlibName").value=arr[1];
				 }
				 if(rv==""){
				 	document.getElementById("qlibId").value=0;
				 	document.getElementById("questionLibName").innerHTML="";
				 	document.getElementById("qlibName").value="";
				 }
			}
			function doSubmit(){
				myForm.action="exam_question_list.action";
				myForm.submit();
			}
			function init(){
				if(document.getElementById("qlibId").value>0){
					document.getElementById("questionLibName").innerHTML= document.getElementById("qlibName").value;
				}
			}
		</script>
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#ffffff")} 
		</style>
	</HEAD>
	<body onload="init();">
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="搜索页" /></div>
			</li>
		</ul>
		<div style="font-size:15px;text-align:center;margin-top:10px;">考试试题组合搜索</div>
		<s:form action="exam_quiz_Overview" method="post" name="myForm" theme="simple">
			<s:hidden name="question.qlib.id" id="qlibId" />
			<s:hidden id="qlibName" />
			<TABLE cellSpacing=1 cellPadding=1 width="600" align=center bgColor=#ebebeb>
				<TBODY style="text-align:center;">
					<tr>
						<td width="150px" height="30px">
							试题名称：
						</td>
						<td>
							<s:textfield name="question.title" />
						</td>
				    </tr>
				    <tr>
						<td width="150px" height="30px">
							题库：
						</td>
						<td align="left">
							<span id="questionLibName" style="font-size:14px;width:200px;text-align:center;"></span>
							<a href="#" onClick="searchQuestionInit();return false;">点此进行选择</a>
						</td>
				    </tr>
				    <tr>
						<td width="150px" height="30px">
							题型：
						</td>
						<td>
							<s:select name="question.qtype" list="#{-1:'不限题型',2:'单选题',4:'多选题',1:'判断题',5:'填空题'}" />
						</td>
				    </tr>
				</TBODY>
			</TABLE>
			<div style="text-align:center;margin-top:10px;">
				<a href="javascript:doSubmit();" class="textbg6">查询</a>
			</div>
		</s:form>
	
	</body>
</HTML>