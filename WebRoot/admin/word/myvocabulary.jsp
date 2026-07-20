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
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
				function showtree(flag){
					var tree = document.getElementById("tree_list_td");
					var img = document.getElementById("showimg");
					if(flag){
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(false);" class="textbg5">隐藏卷库类别</a>';
					}
					else{
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(true);" class="textbg5">显示卷库类别</a>';
					}
				}
				
			function searchUserInit(comp){
			     width=800;
				 height=450;
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv =  window.showModalDialog("wordslib.action?sub_department=1&x="+Math.random(),null,sFeature);
				 if(null==rv){
				 	alert('您没有选择用户！');
				 }else{
				 	if(rv[0]<=0)  	alert('您没有选择用户！');
				 	else
				 	$.post("mess_getWordsLibInfoJson.action", {
						"word.id":rv[0],
						"x":Math.random
						}, 
						function (data) {
							var dataObj=eval("("+data+")");
							document.getElementById("t_id").value=dataObj.word.id;
							document.getElementById("t_name").value=dataObj.word.name;
							document.getElementById("t_hname").value=dataObj.word.name;
						}); 
				 }
			} 
			function ready(add){
			//	var add = document.getElementById("mp3").value;
				document.getElementById("alarmPlayer").url=add;
				document.getElementById("alarmPlayer").controls.play();
			}
		</script>
		<style type="text/css">
			td {
				font-size: 12px;
				color: #333333;
				line-height: 150%
			}
			
			tr {
				background-color: expression((   this .   sectionRowIndex %   2 ==   0)
					? 
					 "#ffffff" :   "#f4f4f4" )
			}
			.bottom{
				border:none;
				background-image:url(images/bofang.jpg);
				width:20px;
				height:20px; 
			}
		</style>
	</HEAD>
	<body>
	<object classid="CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6"type="application/x-oleobject" id="alarmPlayer" height="0" width="0">
		<param name="autoStart" value="false">
		<param name="balance" value="0">
		<param name="currentPosition" value="0">
		<param name="currentMarker" value="0">
		<param name="enableContextMenu" value="true">
		<param name="enableErrorDialogs" value="false">
		<param name="enabled" value="true">
		<param name="fullScreen" value="false">
		<param name="invokeURLs" value="false">
		<param name="mute" value="true">
		<param name="playCount" value="1">
		<param name="rate" value="1">
		<param name="uiMode" value="none">
		<param name="volume" value="100">
	</object>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="词汇列表" />
				</div>
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
			<s:form action="myvocabulary" name="myelist" theme="simple">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<table width="100%" cellpadding="1" cellspacing="1">
					<tr>
						
						
						<td valign="top">
							
							<table width="100%" align="center" cellspacing="1"
								cellpadding="1" bgcolor="#D1E4F5">
								
								<tr>
									<td colspan="3" bgcolor="#F8FCFE" style="">
											关键字：<input type="text" name="vocabulary.name">
									</td>
									<td colspan="3" bgcolor="#F8FCFE" style="">
									单元或等级：
									<label> 
										<s:textfield id="t_name" size="40" disabled="true"  />
										<s:hidden name="course.teacherName" id="t_hName"/>
										<s:hidden name="vocabulary.wordid" id="t_id"/><input class="textbg6" type="button" onClick="searchUserInit('messUser')" value="选 择">
									</label>
									</td>
									<td width="15%" colspan="2" bgcolor="#F8FCFE">
								  		<input class="textbg4" style="height: 25px;" type="submit" onClick="document.getElementById('pageNow').value=0"
											value="查询">									
									</td>
								</tr>
							</table>
								<table width="100%" align="center" cellspacing="1"
									cellpadding="1">
									<tr>
										
										<th width="212" height="30" align="center">
											词汇										</th>
										<th width="392" height="30" align="center">
											拼音</th>
										<th width="182" height="30" align="center">
											读音										</th>
										<th width="285" height="30" align="center">操作 </th>
									</tr>
									<tbody onMouseOut="changeback()" onMouseOver="changeto()">
										<s:iterator value="vocabularys">
											<tr>
												
												<td height="30" align="center">
													<s:property value="name" />
												<td width="392" height="30" align="center">
													<s:property value="pinyin" />
												<td width="182" height="30" align="center">
													<input  type="button" class="bottom" onclick="ready('${duyin }')" >												
												<td width="285" height="30" align="center" colspan="2">
													<a 
														href="vocabulary_view.action?vocabulary.id=<s:property value="id" />"
														class=textbg4>查 看</a>
														<a
															href="vocabulary_alterInit.action?vocabulary.id=<s:property value="id" />&target=3"
															class=textbg4>修 改</a>
													<a
														href="vocabulary_delete.action?vocabulary.id=<s:property value="id" />&target=3"
														class=textbg4>删 除</a>												
												</td>
											</tr>
										</s:iterator>
									</tbody>
							  </table>
								<br>
								<script>
									function page(i){
										document.getElementById("pageNow").value=i;
										myelist.submit();
										
									}
									function epDelete(){
										if(confirm('确定删除这几个？')){
											myelist.action = "exampaper_delete_status.action";
											myelist.submit();
											return true;
										}
										else 
											return false;
											
									}
								</script>
								<wysLib:page></wysLib:page>
							<br/>
							<input class=textbg6 style="height:35px;" type="button"
										value="添加词汇" onClick="javascript:document.location.href='vocabulary_addInit.action';">
							<input class=textbg6 style="height:35px;" type="button"
										value="查看更多" onClick="">
						
							
						</td>
					</tr>
				</table>
			</s:form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
