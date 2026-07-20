<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="zdyLib" uri="/WEB-INF/zdyLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<base target="_self"> 
		<base href="<%=basePath%>">
		<TITLE>上传模板</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/menu.js"></script>
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<script type="text/javascript" src="eltree/dtree.js"></script>
		<SCRIPT type="text/javascript" src="js/jquery/jquery-1.7.2.min.js" ></script>
				<SCRIPT type="text/javascript" src="js/zidingyipage.js" ></script>
	</HEAD>
	<BODY  >
	<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<span style="font-weight: bold;">上传模板</span>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
	<form  action="uploadJsp.action" method="post" enctype="multipart/form-data" onsubmit="return check()" >
	<input type="hidden" name="customReport.id" value="<s:property value="customReport.id"/>" />
	<table>
		<tr>
			<td>
				<s:file name="st" theme="simple"/>
			</td>
				
			<td>
				<input type="submit" value="上传"/>
			</td>
		</tr>
	</table>
	<SCRIPT type="text/javascript">
	$(function(){
		if('${elmessage}'!=""){
				alert('${elmessage}');
			}
	
	})
	function  check(){
	if($('#st').val()==""){
		alert("请选择模板")
		return false;
	}
	var index=$('#st').val().lastIndexOf("\\");
	var name=$('#st').val().substring(index+1);
	var str ='${customReport.resultPage}';
	if(str!="")
		if( str == name){
		if(window.confirm('已存在相同的文件，确定覆盖？！')){
                 
                 return true;
              }else{
                
                 return false;
             }
		
		}

	}
	
	</SCRIPT>
	</form>

	</BODY>
</HTML>
