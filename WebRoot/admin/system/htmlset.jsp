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
		
		</SCRIPT>
	</HEAD>
	<body onLoad="myload();">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="填写信息" /></div>
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
			<s:form action="htmlset" method="post" theme="simple">
				<table height=50% width="60%" align="center" cellpadding="2" cellspacing="2"
					bgcolor="#ECEDEB">
					
					<tr>
						<td align="right" bgcolor="#FFFFFF">
							运行模式
						</td>
						<td align="left" bgcolor="#FFFFFF">
							<s:radio list="#{'0':'动态jsp','1':'栏目页及内容页都生成HTML','2':'栏目页不生成，内容页生成HTML','3':'自定义html'}"
								name="sysconf.model_working"></s:radio>
						</td>
					</tr>
				
					<tr>
						<td align="right" bgcolor="#FFFFFF">
							发布选项
						</td>
						<td align="left" bgcolor="#FFFFFF">
							<s:radio list="#{'0':'仅发布内容页','1':'发布栏目页+内容页','2':'发布首页+栏目页+内容页'}"
								name="sysconf.publish_option"></s:radio>
						</td>
					</tr>
					<s:if test="publish_option==1||publish_option==2">
						<tr>
							<td align="right" bgcolor="#FFFFFF">
								生成列表分页数
							</td>
							<td align="left" bgcolor="#FFFFFF">
								<s:textfield name="sysconf.list_page_number" />
							</td>
						</tr>
					</s:if>
					<s:else>
						<tr style="display:none">
							<td align="right" bgcolor="#FFFFFF">
								生成列表分页数
							</td>
							<td align="left" bgcolor="#FFFFFF">
								<s:textfield name="sysconf.list_page_number" />
							</td>
						</tr>
					</s:else>
					<tr>
						<td align="right" bgcolor="#FFFFFF">
							生成的总目录
						</td>
						<td align="left" bgcolor="#FFFFFF">
							<s:textfield name="sysconf.catalogue_place" />
						</td>
					</tr>
					<tr style="display:none">
						<td align="right" bgcolor="#FFFFFF">
							生成栏目页规则
						</td>
						<td align="left" bgcolor="#FFFFFF">
							<s:radio list="#{'0':'按目录级别顺序结构生成列表页','1':'所有栏目页都生成在总目录下面'}"
								name="sysconf.title_rule"></s:radio>
						</td>
					</tr>
					
				</table>
				<br>
				<input type="submit" value="保存设置"  class="textbg2">
			</s:form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
