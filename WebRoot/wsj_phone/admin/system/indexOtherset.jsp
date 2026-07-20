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
		<TITLE>系统设置</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="eltree/dtree.js"></script>
		<SCRIPT type="text/javascript">
				function setParent(dd,id){
					document.getElementById("parentid"+dd).value=id;
				}
		</SCRIPT>
		<SCRIPT type="text/javascript">
			function myload(){
				if("${elmessage}"!=""){
					alert( "${elmessage}!");
				}
			
			}
		
			function do_submit(){
				indexOtherset.submit();
			}
		</SCRIPT>
	</HEAD>
	<body onLoad="myload();">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="首页设置" /></div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<s:form action="indexOtherset.action" method="post" name="indexOtherset" theme="simple">
				<table width="100%" align="center" cellpadding="1" cellspacing="1"
					>
					<tr>
						<th width="200" align='center'></th>
						<th width="201" align='center'>是否显示</th>
						<th width="669" align='center'>显示条数</th>
					</tr>
					<tr>
						<td height="30"  align="right"> 
							通知公告：

						
						<td height="30" align="center">
							<s:radio list="#{'true':'是','false':'否'}"
								name="indexSysConf.show_tongzhigonggao" id="tongzhigonggao"></s:radio>
						</td>
						<td height="30" align="left">
							&nbsp;<input type="text" name="indexSysConf.tongzhigonggao_length" id="tongzhigonggao_length" 
								value="<s:property value='indexSysConf.tongzhigonggao_length'/>"/>
						</td>
					</tr>
					<tr>
						<td height="30" align="right"> 
							待办事务：

						
						<td height="30" align="center">
							<s:radio list="#{'true':'是','false':'否'}"
								name="indexSysConf.show_daibanshiwu" id="show_daibanshiwu"></s:radio>
						</td>
						<td height="30" align="left">
							&nbsp;<input type="text" name="indexSysConf.daibanshiwu_length" id="daibanshiwu_length" 
								value="<s:property value='indexSysConf.daibanshiwu_length'/>"/>
						</td>
					</tr>
					<tr>
						<td height="30" align="right"> 
							工作计划：

						
						<td height="30" align="center">
							<s:radio list="#{'true':'是','false':'否'}"
								name="indexSysConf.show_gongzuojihua" id="show_gongzuojihua"></s:radio>
						</td>
						<td height="30" align="left">
							&nbsp;<input type="text" name="indexSysConf.gongzuojihua_length" id="gongzuojihua_length" 
								value="<s:property value='indexSysConf.gongzuojihua_length'/>"/>
						</td>
					</tr>
					<tr>
						<td height="30" align="right"> 
							工作日志：

						
						<td height="30" align="center">
							<s:radio list="#{'true':'是','false':'否'}"
								name="indexSysConf.show_gongzuorizhi" id="show_gongzuorizhi"></s:radio>
						</td>
						<td height="30" align="left">
							&nbsp;<input type="text" name="indexSysConf.gongzuorizhi_length" id="gongzuorizhi_length" 
								value="<s:property value='indexSysConf.gongzuorizhi_length'/>"/>
						</td>
					</tr>
					<tr>
						<td height="30" align="right"> 
							日程安排：

						
						<td height="30" align="center">
							<s:radio list="#{'true':'是','false':'否'}"
								name="indexSysConf.show_richenganpai" id="show_richenganpai"></s:radio>
						</td>
						<td height="30" align="left">
							&nbsp;<input type="text" name="indexSysConf.richenganpai_length" id="richenganpai_length" 
								value="<s:property value='indexSysConf.richenganpai_length'/>"/>
						</td>
					</tr>
					<tr>
						<td height="30" align="right"> 
							个人考勤：

						
						<td height="30" align="center">
							<s:radio list="#{'true':'是','false':'否'}"
								name="indexSysConf.show_gerenkaoqin" id="show_gerenkaoqin"></s:radio>
						</td>
						<td height="30" align="left">
						
						</td>
					</tr>
					<tr>
						<td height="30" align="right"> 
							个人未审：

						
						<td height="30" align="center">
							<s:radio list="#{'true':'是','false':'否'}"
								name="indexSysConf.show_gerenweishen" id="show_gerenweishen"></s:radio>
						</td>
						<td height="30" align="left">
						</td>
					</tr>
					<tr>
						<td height="30" align="right"> 
							管理等审：

						
						<td height="30" align="center">
							<s:radio list="#{'true':'是','false':'否'}"
								name="indexSysConf.show_gerendaishen" id="show_gerendaishen"></s:radio>
						</td>
						<td height="30"  align="left">
						</td>
					</tr>
					
					<tr>
						<td height="30" align="right"> 
							我的全部课程：

						
						<td height="30" align="center">
							<s:radio list="#{'true':'是','false':'否'}"
								name="indexSysConf.show_myallcourses" id="show_myallcourses"></s:radio>
						</td>
						<td height="30" align="left">
							&nbsp;<input type="text" name="indexSysConf.myallcourses_length" id="myallcourses_length" 
								value="<s:property value='indexSysConf.myallcourses_length'/>"/>
						</td>
					</tr>
					<tr>
						<td height="30" align="right"> 
							我的非购买考试：

						
						<td height="30" align="center">
							<s:radio list="#{'true':'是','false':'否'}"
								name="indexSysConf.show_myexams" id="show_myexams"></s:radio>
						</td>
						<td height="30" align="left">
							&nbsp;<input type="text" name="indexSysConf.myexams_length" id="myexams_length" 
								value="<s:property value='indexSysConf.myexams_length'/>"/>
						</td>
					</tr>
					<tr>
						<td height="30" align="right"> 
							我的购买考场：

						
						<td height="30" align="center">
							<s:radio list="#{'true':'是','false':'否'}"
								name="indexSysConf.show_mybuyrooms" id="show_mybuyrooms"></s:radio>
						</td>
						<td height="30" align="left">
							&nbsp;<input type="text" name="indexSysConf.mybuyrooms_length" id="mybuyrooms_length" 
								value="<s:property value='indexSysConf.mybuyrooms_length'/>"/>
						</td>
					</tr>
					<tr>
						<td height="30" align="right"> 
							我的培训班：

						
						<td height="30" align="center">
							<s:radio list="#{'true':'是','false':'否'}"
								name="indexSysConf.show_mytrainingcourses" id="show_mytrainingcourses"></s:radio>
						</td>
						<td height="30" align="left">
							&nbsp;<input type="text" name="indexSysConf.mytrainingcourses_length" id="mytrainingcourses_length" 
								value="<s:property value='indexSysConf.mytrainingcourses_length'/>"/>
						</td>
					</tr>
				</table>
        <br>
				<input type="button" onClick="do_submit();" value="保存设置"  class="textbg6" style="margin-left:290px;">
			</s:form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
