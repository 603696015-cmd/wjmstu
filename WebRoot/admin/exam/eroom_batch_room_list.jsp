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
		<base target="_self" href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<SCRIPT type="text/javascript">
			var idandtitle = new Array();
			function queding(){
				var cks= document.getElementsByName("uid");
				var m =0;
				for(var i = 0 ; i < cks.length; i++){
					if(cks[i].checked){
						idandtitle[m]=cks[i].value;
						m++;
					}
				}
			
				window.returnValue = idandtitle;
				window.close();
			}
			function select_All(){
				var cks= document.getElementsByName("uid");
				for(var i = 0 ; i < cks.length; i++){
					cks[i].checked= true;
				}
			}
			function select_Fan(){
				var cks= document.getElementsByName("uid");
				for(var i = 0 ; i < cks.length; i++){
					cks[i].checked= !cks[i].checked;
				}
			}
			function select_Bux(){
				var cks= document.getElementsByName("uid");
				for(var i = 0 ; i < cks.length; i++){
					cks[i].checked= false;
				}
			}
		</SCRIPT>
		<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
	</HEAD>
	<body>

		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<div> <br>
				<table width="800" cellpadding="1" cellspacing="1">
					<tr>
						<td width="200px;" valign="top" id="tree_list_td">
							<wysLib:eroomLibTree
							href="eroom_batch_room_list.action?sublibs=1&examRoom.eroomLib.id="
							rootAble="true"></wysLib:eroomLibTree>
						</td>
						<td valign="middle" width="5px;" style="padding: 0px">
							<img src="images/leftmenu/main_55.gif" style="cursor: hand"
								onclick="changeTreeDisplay(this)" />
						</td>
						<td valign="top">
							<table width="100%" align="center" cellpadding="1"
								cellspacing="1" >
								<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
									</td>
									<td height="30" align="center" >
										考场名称
									</td>
									<td height="30" width="150px" align="center" >
										开始时间
									</td>
									<td height="30" width="150px" align="center" >
										结束时间
									</td>
								</tr>
								<s:iterator value="examRooms">
									<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
											<input type="checkbox" name="uid"
												value="<s:property value="id"/>" />
										</td>
										<td height="30" align="center" >
											<s:property value="title" />
										</td>
										<td height="30" align="center" >
											<s:date name="begintime" format="yyyy-MM-dd HH:mm:ss" />
										</td>
										<td height="30" align="center" >
											<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss" />
										</td>
									</tr>
								</s:iterator>
							</table>
							<form action="eroom_batch_room_list.action" name="caform" method="post">
								<s:hidden name="pN" id="pageNow"/> 
								<s:hidden name="pS"/> 
								<s:hidden name="course.name"/> 
								<s:hidden name="course_sourse"/> 
							</form>
							<wysLib:page></wysLib:page>
							<script>
								function page(i){
									document.getElementById("pageNow").value=i;
									caform.submit(); 
								}
								
							</script>
							<br />
							<a href="javascript:select_All()" class="textbg4" >全选</a>
							<a href="javascript:select_Fan()" class="textbg4" >反选</a>
			 				<a href="javascript:select_Bux()" class="textbg4" style="width:60px" >全不选</a>
							<a href="javascript:queding()" class="textbg4" >确定</a>
							<a href="javascript:window.close();" class="textbg4" >关闭</a>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
