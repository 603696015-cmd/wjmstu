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
		<link href="css/global.css" rel="stylesheet" type="text/css" />
		<link href="css/index.css" rel="stylesheet" type="text/css" />
		<link type="text/css" href="css/base.css" rel="stylesheet" />
		<link type="text/css" href="css/qhIndex.css" rel="stylesheet" />
		<link href="css/style2013.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/jquery.js"></script>
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
			//下拉列表联动
			function change(){
				removeall();
				var wordid = document.getElementById("select1").value;
				$.post("mess_getVocabularyJson.action", {
						"vocabulary.wordid":wordid,
						"x":Math.random
						}, 
						function (data) {
							var dataObj=eval("("+data+")");
						//	alert(dataObj.length);
							var first = document.getElementById("select2");
							for(var i=0;i<dataObj.length;i++){
								first.options.add(new Option(dataObj[i].word.name,dataObj[i].word.id));
							}
						}); 
			}
			function removeall(){
				var obj = document.getElementById("select2");
				obj.options.length=1;
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
	<!--<table width="100%" border="0" cellpadding="0" cellspacing="0">
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
		</table>-->
		
		<!-- 内容 -->
		
	<s:form action="vocabulary_search" name="myelist" theme="simple">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<s:hidden name="target" value="4" />
				<input type="hidden" value="${course.id }" name="course.id">
				<table width=1040 align="center" cellpadding="1" cellspacing="1" style="margin-top:0px;">
					<tr>
						
						
						<td align="center" valign="middle" bgcolor="#F8FCFE">
							
							<table width="1040" align="center" cellspacing="1"
								cellpadding="1">
								
								<tr>
									<td colspan="3" align="center" style="">
											关键字：
								  <input type="text" name="vocabulary.name">								  </td>
									<td height="40" colspan="3" align="right">
									单元或等级：
									<select name="vocabulary.wordid" id="select1" onChange="change();">
										<option value=-1>全部等级</option>
										<s:iterator value="wordTree">
											<option name="id" value="${id }" ><s:property value="name"/></option>
										</s:iterator>
									</select>
									<select name="vocabulary.childid" id="select2">
										<option value=-1>全部单元</option>
									</select>
							<!-- 	<label> 
										<s:textfield id="t_name" size="40" disabled="true" name="word.name" />
										<s:hidden name="course.teacherName" id="t_hName"/>
										<s:hidden name="vocabulary.wordid" id="t_id"/><input class="textbg6" type="button" onClick="searchUserInit('messUser')" value="选 择">
									</label> -->								  </td>
									<td width="15%" colspan="2" align="left" style="padding-left:15px;">
								  		<input class="textbg4" style="height: 25px;" type="submit" onClick="document.getElementById('pageNow').value=0"
											value="查询">	
								  </td>
								</tr>
						  </table>
								<table width="100%" border="0" align="center"
									cellpadding="1" cellspacing="1" bgcolor="#CFDBE2">
									<tr>
										
										<td width="212" height="40" align="center" background="images/bg002.jpg">
											词汇										</td>
										<td width="392" height="30" align="center" background="images/bg002.jpg">
											拼音</td>
										<td width="182" height="30" align="center" background="images/bg002.jpg">
											读音										</td>
									<td width="285" height="30" align="center" background="images/bg002.jpg">操作 </td> 
									</tr>
									<tbody onMouseOut="changeback()" onMouseOver="changeto()">
										<s:iterator value="vocabularys">
											<tr>
												
											  <td height="40" align="center" bgcolor="#F8FCFE">
													
													
													<a href="vocabulary_view.action?vocabulary.id=<s:property value="id" />" style="font-size:16px;color: #FF3300;font-weight:bold;"><s:property value="name" /></a>
											  <td width="392" height="30" align="center" bgcolor="#F8FCFE">
													<s:property value="pinyin" />
											  <td width="182" height="30" align="center" bgcolor="#F8FCFE">
													<input  type="button" class="bottom" onClick="ready('${duyin }')" >												
									   	  <td width="285" height="30" colspan="2" align="center" bgcolor="#F8FCFE">
													<table width="95" border="0" align="center" cellpadding="0" cellspacing="0">
		                                             <tr>
		                                              <td height="28" align="center" valign="middle" background="images/textbg.jpg">
														<a 
																href="vocabulary_view.action?vocabulary.id=<s:property value="id" />"
																><span style="font-size:14px;font-weight:bold;color:white;" target="_self">查 看</span></a>												</td> 
		                                              </tr>
		                                            </table>
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
								<s:if test="vocabularys.size()!=0">
									<wysLib:page></wysLib:page>
								</s:if>
								
							<br/>
					  <!--<input class=textbg6 style="height:35px;" type="button"
										value="添加词汇" onClick="javascript:document.location.href='vocabulary_addInit.action';">
							<input class=textbg6 style="height:35px;" type="button"
										value="查看更多" onClick="">-->					  </td>
					</tr>
	  </table>
		    <p>&nbsp;</p>
	</s:form>
		
		<!-- 内容 -->
	</BODY>
</HTML>
