<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="userdemoLib" uri="/WEB-INF/userdemoLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<META http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>初始定级标准</title>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
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
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<script type="text/javascript">
		var temp = -1;
		function doclick(status){
			
			if(temp!=-1){
				alert("当前正在定级,请先执行完操作");
				return ;
			}
			temp = status;
			var name = jQuery("#name_"+status).html();
			var scorebegin = jQuery("#scorebegin_"+status).html();
			var scoreend = jQuery("#scoreend_"+status).html();
			
			jQuery("#name_"+status).append("<input  type='hidden' name='classification.name' id='classification.name' value='"+name+"' />");
			jQuery("#scorebegin_"+status).html("<input type='text' name='classification.scorebegin' id='classification.scorebegin' value='"+scorebegin+"' />");
			jQuery("#scoreend_"+status).html("<input type='text' name='classification.scoreend' id='classification.scoreend' value='"+scoreend+"' />");
			jQuery("#click_"+status).html("<input type='button' value='设置' onclick='set();' /><input type='button' value='取消' onclick='quit();' />");
		}
		
		function quit(){
			haha.submit();
		  	temp = -1;
		}
		
		function set(){
			var name = document.getElementById("classification.name").value;
			var scorebegin = document.getElementById("classification.scorebegin").value;
			var scoreend = document.getElementById("classification.scoreend").value;
			
			if(scorebegin == "" || scoreend == ""){
				alert("开始分数和结束分数不能为空");
				return ;
			}
			if(isNaN(scorebegin) || isNaN(scoreend)){
				alert("开始分数和结束分数必须为数字");
				return ;
			}
			
			jQuery.ajax({
			  type: 'POST',
			  url: "setClassification.action",
			  data: {'classification.name':name,'classification.scorebegin':scorebegin,'classification.scoreend':scoreend},
			  async:false,//同步
			  success: function(data){
		  			haha.submit();
		  			temp = -1;
			  }
			});
		}
		
		function searchUserInit(){
		     width=screen.availWidth * 0.8;
			 height=screen.availHeight * 0.8;;
		  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
			 var rv =  window.showModalDialog("examroom_alllist_wjm.action?x="+Math.random(),null,sFeature);
			 if(rv!=undefined&&rv!=""){
				 var bh=rv.split("-=tmk=-");
				 document.getElementById("name").innerHTML=bh[1];
				 document.getElementById("roomid").value=bh[0];
			 }
		}
		
		function updateRoomid(){
			if(document.getElementById("roomid").value == ""){
				alert("您还未选择定级考场,请先选择");
				return ;
			}
			jQuery.ajax({
			  type: 'POST',
			  url: "updateRoomid.action",
			  data: {'roomid':document.getElementById("roomid").value},
			  async:false,//同步
			  success: function(data){
		  			alert("定级考场设置成功");
			  }
			});
		}
  </script>
	<body >
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
						<li>
							<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
								<wysLib:Navigation ivalue="初始定级标准设置" />
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
			<form action="init_classification_standard.action" name="haha" method="post">
			<table cellpadding='1' cellspacing='1' width='60%'>
				<caption>初始定级标准设置</caption>
				<tr><th>名称</th><th>分数段</th><th></th></tr>
				<s:iterator value="classifications" status="status">
					<tr>
						<td align=center><span id="name_<s:property value="#status.index" />"><s:property value="name" /></span></td>
						<td align=center><span id="scorebegin_<s:property value="#status.index" />"><s:property value="scorebegin" /></span>-<span id="scoreend_<s:property value="#status.index" />"><s:property value="scoreend" /></span></td>
						<td align=center><span id="click_<s:property value="#status.index" />"><input type="button" value="点击修改" onclick="doclick(<s:property value="#status.index" />);" id="click_<s:property value="#status.index" />" /></span></td>
					</tr>
				</s:iterator>
			</table>
			<input type="hidden" name="roomid" id="roomid" /> 
			</form>
			<form action="setClassificationExamroom.action" name="hehe" method="post">
			<table cellpadding='1' cellspacing='1' width='60%'>
				<caption>初始定级考场设置</caption>
				<tr>
					<td align=center>定级考场：</td>
					<td align=center>
						<span id="name"><s:if test="examRoom==null">还未选择定级考场</s:if><s:else><s:property value="examRoom.title" /></s:else></span>
						<a href="#" onClick="searchUserInit();return false;" class="textbg">点此选择</a>
						<a href="#" onClick="updateRoomid();return false;" class="textbg">确认提交</a>
					</td>
				</tr>
			</table>
			</form>
		</div>
		
	
	</body>
</html>