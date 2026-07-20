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
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
			function searchUserInit(){
			     width=600;
				 height=500;
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv =  window.showModalDialog("userRegister.action?x="+Math.random(),null,sFeature);
				 //alert(rv);
				 if(rv!=undefined&&rv!=""){
					 //var bh=rv.split("_");
					 var bh=rv.split("-=wys=-");
					 document.getElementById("danwei").value=bh[0];
					 //document.getElementById("danweiName").value=bh[1];
					 document.getElementById("danweiName").innerHTML=bh[1];
					 document.getElementById("departmentId").value=bh[2];
					 document.getElementById("depName").value=bh[1];
				 }
				 if(rv==""){
				 	document.getElementById("danwei").value=0;
				 	document.getElementById("danweiName").innerHTML="";
				 	 document.getElementById("depName").value="";
				 }
			}
			/*
			function searchExampracInit(){
			     width=600;
				 height=500;
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv =  window.showModalDialog("examprac_simple_list.action?x="+Math.random(),null,sFeature);
				 //alert(rv);
				 if(rv!=undefined&&rv!=""){
					 var arr=rv.split("_");
					 document.getElementById("exampracId").value=arr[0];
					 document.getElementById("pracTitle").innerHTML=arr[1];
					 document.getElementById("exampracName").value=arr[1];
				 }
				 if(rv==""){
				 	document.getElementById("exampracId").value=0;
				 	document.getElementById("pracTitle").innerHTML="";
				 	document.getElementById("exampracName").value="";
				 }
			}
			*/
			function doSubmit(op){
				if(op==1){
					myForm.action="exam_quiz_Overview.action";
				}else{
					myForm.action="exam_quiz_Detail.action";
				}
				myForm.submit();
			}
			function init(){
				if(document.getElementById("departmentId").value>0){
					document.getElementById("danweiName").innerHTML= document.getElementById("depName").value;
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
		<div style="font-size:15px;text-align:center;margin-top:10px;">考试答卷组合搜索</div>
		<s:form action="examprac_quiz_Overview" method="post" name="myForm" theme="simple">
			<s:hidden name="elUser.danwei" id="danwei" />
			<s:hidden name="elUser.department.id" id="departmentId" />
			<s:hidden name="exampracName" id="exampracName" />
			<s:hidden name="depName" id="depName" />
			<TABLE cellSpacing=1 cellPadding=1 width="600" align=center bgColor=#ebebeb>
				<TBODY style="text-align:center;">
					<tr>
						<td width="150px" height="30px">
							考试场次：
						</td>
						<td style="text-align:left;">
							<span id="KC" style="display:none;font-size:14px;width:200px;text-align:center;"></span>
							<span style="margin-left:200px;"><a href="#" onClick="searchExamRoomUser();return false;">点此进行选择</a></span>
							<%-- 
							<div id="KC" style="display: none;width: 340px;">
							</div>
							 --%>
						</td>
				    </tr>
				    <tr>
						<td width="150px" height="30px">
							姓名：
						</td>
						<td>
							<s:textfield name="elUser.realname" />
						</td>
				    </tr>
				    <tr>
						<td width="150px" height="30px">
							身份证号：
						</td>
						<td>
							<s:textfield name="elUser.shenfenzheng" />
						</td>
				    </tr>
				    <tr>
						<td width="150px" height="30px">
							性别：
						</td>
						<td>
							<s:radio name="elUser.sex" value="''" list="#{'男':'男','女':'女','':'不限'}" />
						</td>
				    </tr>
				    <tr>
						<td width="150px" height="30px">
							部门：
						</td>
						<td style="text-align:left;">
							<span id="danweiName" style="font-size:14px;width:200px;text-align:center;"></span>
							<a href="#" onClick="searchUserInit();return false;">点此进行选择</a>
						</td>
				    </tr>
				    <tr>
						<td width="150px" height="30px">
							练习时间：
						</td>
						<td>
							从 <s:textfield name="examprac.begintime" onclick="setday(this)" /> 到 <s:textfield name="examprac.endtime" onclick="setday(this)" />
						</td>
				    </tr>
				    <tr>
						<td width="150px" height="30px">
							年龄段：
						</td>
						<td>
							从 <s:textfield name="elUser.age_start" /> 到 <s:textfield name="elUser.age_end" />
						</td>
				    </tr>
				    <tr>
						<td width="150px" height="30px">
							<wysLib:BasetName btid="1" />：
						</td>
						<td>
							<s:select cssStyle="width:150px;" headerKey="-1" headerValue="不限" name="elUser.jingzhong" cssClass="g-select"
										list="jingzhongs" listKey="id" listValue="basevalue" />
						</td>
				    </tr>
				    <tr>
						<td width="150px" height="30px">
							<wysLib:BasetName btid="2" />：
						</td>
						<td>
							<s:select cssStyle="width:150px;" headerKey="-1" headerValue="不限" name="elUser.zhiwu" cssClass="g-select" list="zhiwus"
										listKey="id" listValue="basevalue" />
						</td>
				    </tr>
				    <tr>
						<td width="150px" height="30px">
							<wysLib:BasetName btid="3" />：
						</td>
						<td>
							<s:select cssStyle="width:150px;" headerKey="-1" headerValue="不限" name="elUser.zhiji" cssClass="g-select" list="zhijis"
										listKey="id" listValue="basevalue" />
						</td>
				    </tr>
				    <tr>
						<td width="150px" height="30px">
							<wysLib:BasetName btid="5" />：
						</td>
						<td>
							<s:select cssStyle="width:150px;" headerKey="-1" headerValue="不限" name="elUser.dishi" cssClass="g-select" list="dishis"
										listKey="id" listValue="basevalue" />
						</td>
				    </tr>
				</TBODY>
			</TABLE>
			<div style="text-align:center;margin-top:10px;">
				<a href="javascript:doSubmit(1);" class="textbg6">查概况</a>
				<a href="javascript:doSubmit(2);" class="textbg6">查详情</a>
			</div>
		</s:form>
	
	</body>
</HTML>