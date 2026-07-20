<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<base href="<%=basePath%>">
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>五矿发展员工职业发展系统--管理端--学员添加</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/menu.js"></script>
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<script type="text/javascript" src="eltree/dtree.js"></script>
	</HEAD>
	<BODY>
		<ul class="nav">
			<li>
				<s:if test="room.roomtype==1">会议</s:if>
				<s:if test="room.roomtype==2">课堂</s:if>列表
			</li>
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 40px; text-align: center;">
		 <table align="center" cellpadding="1" cellspacing="1" width="70%" 
				bgcolor="#ECEDEB">
				<tr>
					<th>
						<s:if test="room.roomtype==1">会议名称</s:if>
						<s:if test="room.roomtype==2">课堂名称</s:if>
					</th>
					<th>
							<s:if test="room.roomtype==1">会议</s:if>
				<s:if test="room.roomtype==2">课堂</s:if>开始时间
					</th>
					 <th>
							<s:if test="room.roomtype==1">会议</s:if>
				<s:if test="room.roomtype==2">课堂</s:if>结束时间
					</th>
					 <th>
						进入
					</th>
				</tr>
				<s:iterator value="rooms">
					<tr>
						<td height="20" align="center">
							<s:property value="name" />
						</td>
						<td height="20" align="center">
							<s:date name="starttime" format="yyyy-MM-dd HH:mm:ss"/>
						</td>
						 <td height="20" align="center">
							<s:date name="updatetime" format="yyyy-MM-dd HH:mm:ss"/>
						</td>
						 <td height="20" align="center">
						 	<script type="text/javascript">
						 	var request = false;
var component = null;
function createRequest() {
	if (window.ActiveXObject) {
		request = new ActiveXObject("Microsoft.XMLHTTP");
	} else {
		if (window.XMLHttpRequest) {
			request = new XMLHttpRequest();
		}
	}
	if (!request) {
		alert("Error initializing XMLHttpRequest!");
	}
}
function action(url, param, component) {
	this.component = component;
	createRequest();
	request.open("POST", url, true);
	request.onreadystatechange = action_cl;
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;");
	request.send(param);
}
var value="";
function action_cl() {
	if (request.readyState == 4) {
		document.getElementById(component).innerHTML = "";
		//alert(request.responseText);
		//document.getElementById(component).innerHTML = request.responseText;
		
	}
}
						 		function getSid(){
						 			action("http://localhost:5080/openmeetings/MethodGateway?service=userservice&method=getSession",null,"test")
						 			//alert(document.getElementById(component).innerHTML);
						 		}
						 	//http://localhost:5080/openmeetings/MethodGateway?service=soapservice&method=go2room_go2&webapp=/openmeetings&u=vms02&email=27640422@qq.com&roomid=19&sessionid=c7fd0acee7116b48d8866e1e96e7e6ba&zc=1'
						 	</script>
							<a target="_blank" href="intoRoom.action?room.id=<s:property value="id"/>">进入同步课堂</a>
						</td>
					</tr>
				</s:iterator>
			</table> 
				<div id="test" style=""></div>
						
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
