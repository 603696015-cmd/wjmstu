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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>" target="_self">
		<TITLE>五矿发展员工职业发展系统--管理端-学员管理</TITLE>
		<META http-equiv=Pragma content=no-cache>
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
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
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
			var arr = new Array();      
			function doSubmit(){
				var obj = $("input[name='roomids']:checked");
				if(obj.length>0){ 
					/*if(document.doForms.roomids.value != '' && !document.doForms.roomids.length){
					//在elclasses只有一个值的时候。document.myForm.classids是没有长度的， 只有value
						if(document.doForms.roomids.checked){ 
							arr.push(document.doForms.roomids.value);    
						} 
					}else{ 
						for(var i = 0 ; i < document.doForms.roomids.length ; i++) 
						{ 
							if(document.doForms.roomids[i].checked){ 
								arr.push(document.doForms.roomids[i].value);    
							}
						}     
					}*/
					for(var i = 0 ; i<obj.length;i++){
						if($(obj[i]).attr("checked"))
							arr.push($(obj[i]).val());
					}  
				}
				window.returnValue = arr; 
				window.close(); 
				//setTimeout(window.close(),2000);
			}
		</script>
	</HEAD>
	<BODY style="height: 100%; width: 100%; margin: 0px">
		<table width="100%" cellpadding="1" cellspacing="1">
			<tr>
				<td valign="top" id="tree_list_td">
					<wysLib:eroomLibTree
						href="mess_sendExamRoomUserList.action?eroomLib.id="
						rootAble="true"></wysLib:eroomLibTree>
				</td>
				<td valign="middle" width="5px;" style="padding: 0px">
					<img src="images/leftmenu/main_55.gif" style="cursor: hand"
						onclick="changeTreeDisplay(this)" />
				</td>
				<td>
					<form
						action="mess_sendExamRoomUserList.action?eroomLib.id=<s:property value="eroomLib.id"/>"
						method="post" name="myForm">
						<s:hidden name="pN" id="pageNow" />
						<s:hidden name="pS" />
						<table width="100%" align="center" cellspacing="1" cellpadding="1">
							<tr>
								<td>
									考场名称:
									<input size="16" type="text" name="examRoom.title"
										value="<s:property value="examRoom.title"/>">
								</td>
								<td>
									<s:select theme="simple"
										list="#{-1:'考核考场',0:'单纯的课程考场',1:'培训班考场',-2:'全部'}"
										name="examRoom.classid" value="examRoom.classid" />
								</td>
							</tr>
							<tr>
								<td>
									时间段范围&nbsp;
									<input size="16" type="text" onclick=setday(this)
										name="examRoom.begintime"
										value="<s:date name="examRoom.begintime" format="yyyy-MM-dd HH:mm"/>">
									&nbsp;到&nbsp;
									<input size="16" type="text" onclick=setday(this)
										name="examRoom.endtime"
										value="<s:date name="examRoom.endtime" format="yyyy-MM-dd HH:mm"/>">
								</td>
								<td><input onClick="initPN();" class="textbg6" type="button" value="搜索" /></td>
							</tr>
						</table>
					</form>
					<form
						action="mess_sendExamRoomUserList.action?eroomLib.id=<s:property value="eroomLib.id"/>"
						onsubmit="doSubmit();" method="post" name="doForms">
						<table width="100%" align="center" cellspacing="1" cellpadding="1">
							<tr>
								<th width="20" height="30" align="center">
									&nbsp;
								</th>
								<th width="600" height="30" align="center">
									考场名称
								</th>
								<th width="100" height="30" align="center">
									考生人数
								</th>
								<th width="80" height="30" align="center">
									&nbsp;
								</th>
							</tr>
							<tbody onMouseOut="changeback()" onMouseOver="changeto()">
								<s:iterator value="examRooms">
									<tr>
										<td width="20" height="30" align="left"
											style="padding-left: 8px; color: blue;">
											<input type="checkbox" name="roomids"
												value="<s:property value="id" />"">
										</td>
										<td width="600" height="30"
											style="padding-left: 8px; color: blue;" align="left">
											<s:property value="title" />
										</td>
										<td width="100" height="30" align="center">
											<s:property value="usersize" />
										</td>
										<td width="80" height="30" align="center">
											<a
												href="examroom_sh_view.action?examRoom.id=<s:property value="id"/>"
												target="_blank" class="textbg4">查 看</a>
										</td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
						<wysLib:page></wysLib:page>
						<input class="textbg6" type="submit" style="margin-left: 160px"
							value="确&nbsp;&nbsp;认" />
					</form>
					<script>    
								function page(i){ 
									document.getElementById("pageNow").value=i;
									myForm.submit();
								}
								function initPN(){
									document.getElementById("pageNow").value=0;
									myForm.submit();
								}
							</script>
				</td>
			</tr>
		</table>
	</BODY>
</HTML>
