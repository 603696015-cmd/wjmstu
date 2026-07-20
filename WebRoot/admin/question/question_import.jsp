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
		<SCRIPT type="text/javascript">
			function check_xx(){
				var choose=document.getElementById("qtype");
				var stf=document.getElementById("stf").value;
				stf =stf.substring(stf.lastIndexOf('\\')+1);
				if(stf.indexOf('.xls')<0){
					alert("文档必须“.xls”格式的！");
					return false;
				}
				if(!window.confirm("确定导入的题型是：“"+choose.options[choose.selectedIndex].text+"”，文件为：“"+stf+"的试题文件吗”？"))
					return false;
				return true;
			}
		</SCRIPT>
	<script type="text/javascript" src="js/cexampaper.js"></script>
	<style type="text/css">
<!--
.STYLE1 {font-size: 14px}
.STYLE2 {color: #FF0000}
-->
    </style>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
						<li>
							<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="选择文件" /></div>
						</li>
					</ul>
			    </td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>    			
  				</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px;font-size:14px;color:#000099;">
			<form action="question_import.action" onSubmit="return check_xx();" enctype="multipart/form-data"
				method="post">
				<table width="100%" border="0" cellspacing="1" cellpadding="0" bgcolor="#D1E4F5">
  <tr>
    <td height="50" bgcolor="#F8FCFE" style="font-size:14px;"><span class="STYLE2">注意 ，系统当前支持导入excel文档，请注意文档格式要与题库格式一致!</span></td>
  </tr>
  <tr>
    <td height="50" bgcolor="#F8FCFE" style="font-size:14px;">选择需要导入的文档：
      <input type="file" name="st" id="stf" size="50"></td>
  </tr>
  <tr>
    <td bgcolor="#F8FCFE">	<br>
				<br><!--
				请选择导入的题型：
				<select name="question.qtype" id="qtype">
					<option value="1">
						判断题
					</option>
					<option value="2">
						选择题
					</option>
					<option value="5">
						填空题
					</option>
					 <option value="8">
						打字题
					</option>
					 <option value="9">
						邮件题
					</option>
					<option value="10">
						搜索题
					</option>
					<option value="11">
						office题
					</option>
				</select>
				--><br>
				<br>
				<input class=textbg6 style="height:35px;" type="submit" value="导入试题"> 
		　　<a href="download.jsp?filename=elstuffs/shiti.rar" class=textbg>题库格式下载</a></td>
  </tr>
  <tr>
    <td height="50" valign="middle" bgcolor="#F8FCFE"><font color="red" size="+1">
				</font><font color="red"><span class="STYLE1">注意：请严格按照题库格式内容来制作题库，注意题库格式中的最后一行在制作完成后需去掉!</span></font>
				<br>
		<div style="font-size:14px;font-weight:bold;color:red;line-height:50px;"><s:property value="elmessage" /></div></td>
  </tr>
</table>

			</form>
	</div>
		<!-- 内容 -->
	</BODY>
</HTML>
