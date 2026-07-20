<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
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
		<TITLE>个人中心首页</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/manage2.css" />
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/pageutil_si.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<STYLE type="text/css">
.mess {
	background: #99d3fb;
	margin-top: -5px !important;
	margin-top: 10px;
}

.STYLE1 {
	color: red;
	font-weight: bold;
}

.mess td {
	padding: 3px;
	background: #fff;
	font-size: 14px;
}

.STYLE1 {
	color: #FF0000
}

.STYLE3 {
	font-size: 12px;
	color: #E25750;
	font-weight: bold;
}

.STYLE7 {
	FONT-SIZE: 12px
}

.gqtitle {
	color: #F06B33;
	font-size: 14px;
	margin-top: 6px;
	display: block;
	font-weight: bold
}

.juhuangk {
	border: 1px solid #D4CCFB
}

body {
	background-color: #F7F9F9;
}
</STYLE>
		<script> 
			function init(isLogin,count,popIds,eroomAppcount,classAppcount){
				//初始化分页
				var count_kaoshi="<s:property value="count_kaoshi" />";
				var count_pxb="<s:property value="count_pxb" />";
				var count_msg="<s:property value="count_msg" />";
				var count_course="<s:property value="count_course" />";
				var count_gerenweishenhe = "<s:property value='count_news' />";//个人未审核
				var count_gerendaishenhe = "<s:property value='count_baoxianProduct' />";//个人待审核
				$("#page_kaoshi").html(getPageDiv(count_kaoshi,0,5,"page_kaoshi"));
				$("#page_pxb").html(getPageDiv(count_pxb,0,5,"page_pxb"));
				$("#page_msg").html(getPageDiv(count_msg,0,10,"page_msg"));
				$("#page_gerenweishenhe").html(getPageDiv(count_gerenweishenhe,0,6,"page_gerenweishenhe"));
				$("#page_gerendaishenhe").html(getPageDiv(count_gerendaishenhe,0,6,"page_gerendaishenhe"));
				//弹出短消息和弹窗
				if(isLogin==1){
					var msg="";
					if(count>0){
						msg="您当前有"+count+"条未读短消息\n";
					}
					/*
					if(eroomAppcount>0){
						msg+="有"+eroomAppcount+"个可报名的考场\n";
					}
					if(classAppcount>0){
						msg+="有"+classAppcount+"个可报名的培训班\n";
					}
					*/
					if(msg!=""){
						alert(msg);
					}
				}
				if(isLogin==1){
					//alert(popIds);
					if(popIds==""){
						return;
					}
					var popArray=popIds.split(",");
					for(var i=0;i<popArray.length;i++){
						//alert(popArray[i]);
						winOpen(popArray[i],i);
					}
				}
				$.post("exam_listbytitle_isPass.action","Return=ajax"+"&x="+Math.random(),function(data){
					var jsondata = eval("("+data+")");
					if(jsondata.count>0){
						$("#eroom_appcount").html("当前有"+jsondata.count+"个可报名考场");
					}
				});
				$.post("class_listbytypeid_isPass.action","Return=ajax"+"&x="+Math.random(),function(data){
					var jsondata = eval("("+data+")");
					if(jsondata.count>0){
						$("#class_appcount").html("当前有"+jsondata.count+"个可报名培训班");
					}
				});
			}
			function winOpen(popid,i){
				//window.open("pop_user.action?pop.id="+popid,'弹窗信息','height=600,width=800,top=100,left=100,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no,status=no');
				window.open("pop_user.action?pop.id="+popid,'pop_win'+i,'height=550,width=495,top='+i*20+',left='+i*20+',toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no,status=no');
			}
			function sub_do(obj){
				if(obj==1){
					form_do.action="examroom_shlist.action?Return=st_index";
				}else if(obj==2){
					form_do.action="elclass_sh_list.action?Return=st_index";
				}else if(obj==3){
					form_do.action="news_end_trial_list.action?Return=st_index";
				}
				form_do.submit();
			}
			function page_kaoshi(pn){
				//alert("nihao");
				$.post("onloadUcenter_kaoshi.action","pN="+pn+"&pS=5"+"&x="+Math.random(),function(data){
					var jsondata = eval("("+data+")");
					var cnt =jsondata.count;
					var erooms = jsondata.rooms;
					//alert(cnt);
					$("#page_kaoshi").html(getPageDiv(cnt,pn,5,"page_kaoshi"));
					var dls = $("#kaoshi_data").find("tr");
					for(var i =0;i<dls.length;i++){
						$(dls[i]).remove();
					}
					for(var i = 0;i<erooms.length;i++){
						var tr = $("<tr>");
						var erjoin=erooms[i].isApplication==1?'<SPAN style="color:red">【申请】</SPAN>':(erooms[i].isApplication==2?'<SPAN style="color:blue">【全警】</SPAN>':'<SPAN style="color:gray">【分配】</SPAN>');
						//alert(erjoin);
						var trs=('<td width="20" align="center" valign="center" bgcolor="#F7F9F9">'+
										'<img src="images/switch.png" width="8" height="8" />'+
									'</td>'+
									'<td height="25" align="left" valign="center" bgcolor="#F7F9F9"'+
										'style="PADDING-LEFT: 10px; font-size: 12px;">'+
										erjoin+
										erooms[i].title+
									'</td>'+
									'<td align="center" valign="center" bgcolor="#F7F9F9" class="STYLE7">'+
										erooms[i].begintime+
										'&nbsp;至&nbsp;'+
										erooms[i].endtime+
									'</td>');
						trs+=('<td width="80" align="center" valign="middle" bgcolor="#F7F9F9">'+
									 	'<table border="0">'+
											'<tr>'+
												'<td width="20" align="right" valign="middle" bgcolor="#F7F9F9">'+
													'<a target="_self" '+
														'onClick="return isEroom2('+erooms[i].valid+');"'+
														'href="quizpaperinit.action?myroom.examroom.id='+erooms[i].id+'">'+
															'<img src="images/xtb013.png" width="16" height="16" />'+
													'</a>'+
											  '</td>'+
												'<td width="60" valign="middle" bgcolor="#F7F9F9">');//+
						if(erooms[i].mycount<erooms[i].examcount||erooms[i].minstatus==0||erooms[i].minstatus==1)
						trs+=('<a target="_self" '+
														'onClick="return isEroom2('+erooms[i].valid+');"'+
														'href="quizpaperinit.action?myroom.examroom.id='+erooms[i].id+'"'+
														'style="font-size: 12px;">进入考场</a>');//+
						else
						trs+=('<a target="_self" '+ 
														'href="javascript:void(0)" onclick="alert(\'本考场已完成，或次数完成，不需再进入！\');"'+
														'style="font-size: 12px;color:gray">进入考场</a>');//+
						
						trs+=('</td>'+
											'</tr>'+
										'</table>'+
									'</td>');
						$(tr).append(trs);
						$("#kaoshi_data").append(tr);
					}
				});
			}
			function page_pxb(pn){
				//alert("nihao");
				$.post("onloadUcenter_pxb.action","pN="+pn+"&pS=5"+"&x="+Math.random(),function(data){
					var jsondata = eval("("+data+")");
					var cnt =jsondata.count;
					var myClasss = jsondata.myClasss;
					//alert(cnt);
					$("#page_pxb").html(getPageDiv(cnt,pn,5,"page_pxb"));
					var dls = $("#pxb_data").find("tr");
					for(var i =0;i<dls.length;i++){
						$(dls[i]).remove();
					}
					for(var i = 0;i<myClasss.length;i++){
						var tr = $("<tr>");
						var erjoin=myClasss[i].isApplication==1?'<SPAN style="color:red">【申请】</SPAN>':'<SPAN style="color:gray">【分配】</SPAN>';
						tr.append('<td width="20" align="center" valign="center" bgcolor="#F7F9F9">'+
										'<img src="images/switch.png" width="8" height="8" />'+
									'</td>'+
									'<td height="25" align="left" valign="center" bgcolor="#F7F9F9"'+
										'style="PADDING-LEFT: 10px; font-size: 12px;">'+
										erjoin+
										myClasss[i].title+
									'</td>'+
									'<td align="center" valign="center" bgcolor="#F7F9F9" class="STYLE7">'+
										myClasss[i].begintime+
										'&nbsp;至&nbsp;'+
										myClasss[i].endtime+
									'</td>');
						tr.append('<td width="80" align="center" valign="middle" bgcolor="#F7F9F9">'+
									 	'<table border="0">'+
											'<tr>'+
												'<td width="20" align="right" valign="middle" bgcolor="#F7F9F9">'+
													'<a target="_self" '+
														'onclick="return iselClass('+myClasss[i].status+');"'+
														'href="myelclass_view.action?elclass.id='+myClasss[i].id+'&Return=sclidx" >'+
															'<img src="images/xtb021.gif" width="20" height="20" />'+
													'</a>'+
											  '</td>'+
												'<td width="60" valign="middle" bgcolor="#F7F9F9">'+
													'<a target="_self" '+
														'onclick="return iselClass('+myClasss[i].status+');"'+
														'href="myelclass_view.action?elclass.id='+myClasss[i].id+'&Return=sclidx"'+
														'style="font-size: 12px;">进入学习</a>'+
											  '</td>'+
											'</tr>'+
										'</table>'+
									'</td>');
						$("#pxb_data").append(tr);
					}
				});
			}
			function page_msg(pn){
				//alert("nihao");
				$.post("onloadUcenter_message.action","pN="+pn+"&pS=10"+"&x="+Math.random(),function(data){
					var jsondata = eval("("+data+")");
					var cnt =jsondata.count;
					var messages = jsondata.messages;
					//alert(cnt);
					$("#page_msg").html(getPageDiv(cnt,pn,10,"page_msg"));
					var dls = $("#msg_data").find("tr");
					for(var i =0;i<dls.length;i++){
						$(dls[i]).remove();
					}
					for(var i = 0;i<messages.length;i++){
						var tr = $("<tr>");
						tr.append('<td width="20" align="center" valign="center" bgcolor="#F7F9F9"><img src="images/switch.png" width="8" height="8" /></td>'+
				                    '<TD height="22" align="left" bgcolor="#F7F9F9" style="padding-left:10px;">'+
										'<a style="font-size:12px;" target="_self" href="mess_info.action?mess.mess_id='+messages[i].id+'&deleteType=1">'+
										messages[i].title+'</a>'+
									'</TD>'+
									'<TD width="100" align="center" bgcolor="#F7F9F9" style="font-size:12px;">'+
										messages[i].messtime+
									'</TD>');
						$("#msg_data").append(tr);
					}
				});
			}
			function page_gerenweishenhe(pn){
				//alert("nihao");
				$.post("onloadUcenter_gerenweishenhe.action","pN="+pn+"&pS=6"+"&x="+Math.random(),function(data){
					var jsondata = eval("("+data+")");
					var cnt =jsondata.count;
					var gerenweishenhe = jsondata.gerenweishenhe;
					//alert(cnt);
					$("#page_gerenweishenhe").html(getPageDiv(cnt,pn,6,"page_gerenweishenhe"));
					var dls = $("#gerenweishenhe_data").find("tr");
					for(var i =0;i<dls.length;i++){
						$(dls[i]).remove();
					}
					var html = "";
					for(var i = 0;i<gerenweishenhe.length;i++){
						if(i==0 || (i!=0&&i%2==0)){
							html += "<tr>"+
                       			"<td width='50%' height='30'><span class='STYLE1'>*</span> 未审"+gerenweishenhe[i].moduleName+"<span class='STYLE3'> "+gerenweishenhe[i].count+" </span><a href='myContactTags.action?tablename="+gerenweishenhe[i].tablename+"'>查看</a></td>";
						}else{
							html += "<td><span class='STYLE1'>*</span> 未审"+gerenweishenhe[i].moduleName+"<span class='STYLE3'> "+gerenweishenhe[i].count+" </span><a href='myContactTags.action?tablename="+gerenweishenhe[i].tablename+"'>查看</a></td>"+
								"<tr>";
						}
					}
					$("#gerenweishenhe_data").append(html);
				});
			}
			function page_gerendaishenhe(pn){
				//alert("nihao");
				$.post("onloadUcenter_gerendaishenhe.action","pN="+pn+"&pS=6"+"&x="+Math.random(),function(data){
					var jsondata = eval("("+data+")");
					var cnt =jsondata.count;
					var gerendaishenhe = jsondata.gerendaishenhe;
					//alert(cnt);
					$("#page_gerendaishenhe").html(getPageDiv(cnt,pn,6,"page_gerendaishenhe"));
					var dls = $("#gerendaishenhe_data").find("tr");
					for(var i =0;i<dls.length;i++){
						$(dls[i]).remove();
					}
					var html = "";
					for(var i = 0;i<gerendaishenhe.length;i++){
						if(i==0 || (i!=0&&i%2==0)){
							html += "<tr>"+
                       			"<td width='50%' height='30'><span class='STYLE1'>*</span> 待审"+gerendaishenhe[i].moduleName+"<span class='STYLE3'> "+gerendaishenhe[i].count+" </span><a href='finalsearchContactTags.action?tablename="+gerendaishenhe[i].tablename+"&final_=1'>查看</a></td>";
						}else{
							html += "<td><span class='STYLE1'>*</span> 待审"+gerendaishenhe[i].moduleName+"<span class='STYLE3'> "+gerendaishenhe[i].count+" </span><a href='finalsearchContactTags.action?tablename="+gerendaishenhe[i].tablename+"&final_=1'>查看</a></td>"+
								"<tr>";
						}
					}
					$("#gerendaishenhe_data").append(html);
				});
			}
			/**
			function page_course(pn){
				$.post("onloadUcenter_course.action","pN="+pn+"&pS=10"+"&x="+Math.random(),function(data){
					var jsondata = eval("("+data+")");
					var cnt =jsondata.count;
					var myCourses = jsondata.myCourses;
					//alert(cnt);
					$("#page_course").html(getPageDiv(cnt,pn,10,"page_course"));
					//var dls = $("#courses_data").find("tr");
					var dls = $("#courses_data>tr");
					//alert(dls.length);
					for(var i =0;i<dls.length;i++){
						$(dls[i]).remove();
					}
					for(var i = 0;i<myCourses.length;i++){
						//var tr = $("");
						$("#courses_data").append('<tr class="table2"><td width="20" align="center" valign="center" bgcolor="#F7F9F9"><img src="images/switch.png" width="8" height="8" /> </td>'+
                                '<td height="25" align="left" valign="center" bgcolor="#F7F9F9"'+
														'style="PADDING-LEFT: 10px; font-size: 12px;">'+
                                  '<center>'+myCourses[i].name+
                                '</center>'+
                                '</td>'+
                                '<td width="120" align="center" valign="center" bgcolor="#F7F9F9" class="STYLE7">'+myCourses[i].classIdName+'</td>'+
                                '<td width="150" align="center" valign="center" bgcolor="#F7F9F9" class="STYLE7">'+myCourses[i].classIdName+'</td>'+
                                '<td width="80" align="center" valign="middle" bgcolor="#F7F9F9">'+
                                	'<table border="0">'+
	                                    '<tr>'+
	                                      '<td width="20" align="right" valign="middle" bgcolor="#F7F9F9"><a href="course_study.action?course.id='+myCourses[i].id+'&coursePage.id=-1&classid='+myCourses[i].classid+'" style="font-size:12px;" target="_blank"><img style="CURSOR: hand" src="images/xtb021.gif" width="20" height="20" /></a></td>'+
	                                      '<td width="60" align="center" valign="middle" bgcolor="#F7F9F9"><a href="course_study.action?course.id='+myCourses[i].id+'&coursePage.id=-1&classid='+myCourses[i].classid+'" target="_blank" style="font-size:12px;" >进入学习</a></td>'+
	                                    '</tr>'+
                                	'</table>'+
                                '</td>'+
                              '</tr>');
						//$("#courses_data").append(tr);
					}
					//alert($("#courses_data").html());
				});
			}
			*/
			
			function page3(i){
				document.getElementById("pageNow3").value=i;
				assign.submit();
			}
			
			function page4(i){
				document.getElementById("pageNow4").value=i;
				assign.submit();
			}
			function page(i){
				document.getElementById("pageNow").value=i;
				assign.submit();
			}
		</script>
	</HEAD>
	<BODY style="text-align: left"
		onLoad="init('<s:property value="#request.isLogin"/>','<s:property value="#request.message_no"/>','<s:property value="#request.popIds"/>');">
		<s:form action="examroom_alllist" method="post" name="form_do">
			<s:hidden name="examRoom.valid" value="3" />
			<s:hidden name="examRoom.classid" value="-2" />
			<s:hidden name="elClass.status" value="3" />
			<s:hidden name="displayStatus" value="4" />
			<s:hidden name="sublibs" value="1" />
		</s:form>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td valign="middle" class="tablequiz">
					<ul class="nav">
						<li>
							<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
								&nbsp;&nbsp;&nbsp;
								<a target="_blank" href="index.action">首页</a>&nbsp;>>&nbsp;个人中心
							</div>
						</li>
					</ul>
				</td>
				<td width="120" valign="middle" class="tablequiz">
					<A id=quit href="javascript:window.parent.full_screen(false);"
						class="textbg6" style="display: none">退出全屏</A>
				</td>
			</tr>
		</table>
		<!-- 内容 -->
		<form action="study_index.action" method="post" name="assign">
			<s:hidden name="pN" id="pageNow" />
			<s:hidden name="pN3" id="pageNow3" />
			<s:hidden name="pN4" id="pageNow4" />
			<s:hidden name="pS" />
			<s:hidden name="pS3" />
			<s:hidden name="pS4" />
		</form>
		<table width="100%" border="0" style="margin-top: 5px;">
			<tr>
				<td valign="top" style="padding-left: 5px;">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<s:if test="myallcourse == true">
									<table width="100%" border="0" align="center" cellpadding="0"
										cellspacing="0" class="juhuangk">
										<tr>
											<td height="35">
												<table width="100%" border="0" cellpadding="0"
													cellspacing="0">
													<tr>
														<td width="150" height="35" background="images/hyxxh.gif"
															style="padding-left: 20px;">
															<span class="gqtitle">我的课程</span>
														</td>
														<td align="right" valign="middle"
															background="images/hyxxh2.gif"
															style="padding-right: 8px;">
															<a href="mycourseAlllist.action" class=textbg>更多课程</a>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<s:if test="studyCourseList.size() != 0">
											<tr>
												<td align="center" valign="top" bgcolor="#F7F9F9">
													<table width="100%" border="0" align="center"
														cellpadding="1" cellspacing="1" bgcolor="#FFFFFF"
														style="margin-bottom: 5px;">
														<tbody>
															<tr class="table1">
																<td width="20" align="center" valign="center"
																	background="images/bghui001.jpg">&nbsp;
																	
																</td>
																<td height="25" align="left" valign="center"
																	background="images/bghui001.jpg"
																	style="padding-left: 20px;">
																	<span class="STYLE3">课程名称</span>
																</td>
																<td width="120" align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">
																	来源
																</td>
																<!--    <td width="150" align="center" valign="center" background="images/bghui001.jpg" class="STYLE3">与岗位关系</td> -->
																<td align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">
																	进入学习
																</td>
															</tr>
														<tbody id="courses_data" onMouseOut="changeback()"
															onMouseOver="changeto()">
															<s:iterator value="studyCourseList">
																<tr class="table2">
																	<td width="20" align="center" valign="center"
																		bgcolor="#F7F9F9">
																		<img src="images/switch.png" width="8" height="8" />
																	</td>
																	<td height="32" align="left" valign="center"
																		bgcolor="#F7F9F9"
																		style="PADDING-LEFT: 10px; font-size: 13px; color: blue;">
																		<s:property value="course.name" />

																	</td>
																	<td width="120" align="center" valign="center"
																		bgcolor="#F7F9F9" class="STYLE7">
																		<s:property value="course.classIdName" />
																	</td>
																	<!--  <td width="150" align="center" valign="center" bgcolor="#F7F9F9" class="STYLE7"><s:property value="course.classIdName"/></td> -->
																	<td width="80" align="center" valign="middle"
																		bgcolor="#F7F9F9">
																		<table border="0">
																			<tr>
																				<td width="20" align="right" valign="middle"
																					bgcolor="#F7F9F9">
																					<a
																						href="course_study.action?course.id=<s:property value='course.id' />&coursePage.id=-1&classid=<s:property value='course.classid' />"
																						style="font-size: 12px;" target="_blank"><img
																							style="CURSOR: hand" src="images/xtb021.gif"
																							width="20" height="20" />
																					</a>
																				</td>
																				<td width="60" align="center" valign="middle"
																					bgcolor="#F7F9F9">
																					<a
																						href="course_study.action?course.id=<s:property value='course.id' />&coursePage.id=-1&classid=<s:property value='course.classid' />"
																						target="_blank" style="font-size: 12px;">进入学习</a>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</s:iterator>
														</tbody>
													</table>
												</td>
											</tr>
										</s:if>
										<s:else>
											<tr>
												<td height="132" align='center'>
													<span style="color: red">暂无课程</span>
												</td>
											</tr>
										</s:else>
									</table>
								</s:if>
							</td>
						</tr>
					</table>
					<s:if test="myexams == true">
						<table style="margin-top: 8px;" width="100%" border="0"
							cellpadding="0" cellspacing="0">
							<tr>
								<td>

									<table width="100%" border="0" align="center" cellpadding="0"
										cellspacing="0" class="juhuangk">
										<tr>
											<td height="35">
												<table width="100%" border="0" cellpadding="0"
													cellspacing="0">
													<tr>
														<td width="150" height="35" background="images/hyxxh.gif"
															style="padding-left: 20px;">
															<span class="gqtitle">我的考试</span>
														</td>
														<td align="right" valign="middle"
															background="images/hyxxh2.gif"
															style="padding-right: 8px;">
															<a href="mycourseAlllist.action" class=textbg>更多考试</a>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<s:if test="myrooms.size() != 0">
											<tr>
												<td align="center" valign="top" bgcolor="#F7F9F9">
													<table width="100%" border="0" align="center"
														cellpadding="1" cellspacing="1" bgcolor="#FFFFFF"
														style="margin-bottom: 5px;">
														<tbody>
															<tr class="table1">
																<td width="20" align="center" valign="center"
																	background="images/bghui001.jpg">&nbsp;
																	
																</td>
																<td height="25" align="left" valign="center"
																	background="images/bghui001.jpg"
																	style="padding-left: 20px;">
																	<span class="STYLE3">考场标题</span>
																</td>
																<td width="120" align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">
																	开始时间
																</td>
																<td width="120" align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">
																	结束时间
																</td>
																<td width="80" align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">
																	成绩
																</td>
																<td width="80" align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">
																	是否通过
																</td>
																<td align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">
																	详情查看
																</td>
															</tr>
														<tbody id="myexams_data" onMouseOut="changeback()"
															onMouseOver="changeto()">
															<s:iterator value="myrooms">
																<tr class="table2">
																	<td width="20" align="center" valign="center"
																		bgcolor="#F7F9F9">
																		<img src="images/switch.png" width="8" height="8" />
																	</td>
																	<td height="32" align="left" valign="center"
																		bgcolor="#F7F9F9"
																		style="PADDING-LEFT: 10px; font-size: 13px; color: blue;">
																		<s:property value="examroom.title" />

																	</td>
																	<td width="120" align="center" valign="center"
																		bgcolor="#F7F9F9" class="STYLE7">
																		<s:date name="examroom.begintime"
																			format="yyyy-MM-dd HH:mm" />
																	</td>
																	<td width="120" align="center" valign="center"
																		bgcolor="#F7F9F9" class="STYLE7">
																		<s:date name="examroom.endtime"
																			format="yyyy-MM-dd HH:mm" />
																	</td>
																	<td width="80" align="center" valign="center"
																		bgcolor="#F7F9F9" class="STYLE7">
																		<s:property value="myScore" />
																	</td>
																	<td width="80" align="center" valign="center"
																		bgcolor="#F7F9F9" class="STYLE7">
																		<s:if test="ispassed==1">是</s:if>
																		<s:else>否</s:else>
																	</td>
																	<td width="80" align="center" valign="middle"
																		bgcolor="#F7F9F9">
																		<table border="0">
																			<tr>
																				<td width="20" align="right" valign="middle"
																					bgcolor="#F7F9F9">
																					<a
																						href="quizpaperinit.action?myroom.examroom.id=<s:property value="examroom.id"/>&Return=list"
																						style="font-size: 12px;"><img
																							style="CURSOR: hand" src="images/xtb021.gif"
																							width="20" height="20" />
																					</a>
																				</td>
																				<td width="60" align="center" valign="middle"
																					bgcolor="#F7F9F9">
																					<a
																						href="quizpaperinit.action?myroom.examroom.id=<s:property value="examroom.id"/>&Return=list"
																						style="font-size: 12px;">查看详情</a>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</s:iterator>
														</tbody>
													</table>
												</td>
											</tr>
										</s:if>
										<s:else>
											<tr>
												<td height="132" align='center'>
													<span style="color: red">暂无考试</span>
												</td>
											</tr>
										</s:else>
									</table>

								</td>
							</tr>
						</table>
					</s:if>
					<s:if test="mybuyrooms == true">
						<table style="margin-top: 8px;" width="100%" border="0"
							cellpadding="0" cellspacing="0">
							<tr>
								<td>

									<table width="100%" border="0" align="center" cellpadding="0"
										cellspacing="0" class="juhuangk">
										<tr>
											<td height="35">
												<table width="100%" border="0" cellpadding="0"
													cellspacing="0">
													<tr>
														<td width="150" height="35" background="images/hyxxh.gif"
															style="padding-left: 20px;">
															<span class="gqtitle">我参加的考场</span>
														</td>
														<td align="right" valign="middle"
															background="images/hyxxh2.gif"
															style="padding-right: 8px;">
															<a href="mycourseAlllist.action" class=textbg>更多考场</a>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<s:if test="myroomsbuy.size() != 0">
											<tr>
												<td align="center" valign="top" bgcolor="#F7F9F9">
													<table width="100%" border="0" align="center"
														cellpadding="1" cellspacing="1" bgcolor="#FFFFFF"
														style="margin-bottom: 5px;">
														<tbody>
															<tr class="table1">
																<td width="20" align="center" valign="center"
																	background="images/bghui001.jpg">&nbsp;
																	
																</td>
																<td height="25" align="left" valign="center"
																	background="images/bghui001.jpg"
																	style="padding-left: 20px;">
																	<span class="STYLE3">考场标题</span>
																</td>
																<td width="120" align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">
																	开始时间
																</td>
																<td width="120" align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">
																	结束时间
																</td>
																<td width="80" align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">
																	成绩
																</td>
																<td width="80" align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">
																	是否通过
																</td>
																<td align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">
																	详情查看
																</td>
															</tr>
														<tbody id="myexams_data" onMouseOut="changeback()"
															onMouseOver="changeto()">
															<s:iterator value="myroomsbuy">
																<tr class="table2">
																	<td width="20" align="center" valign="center"
																		bgcolor="#F7F9F9">
																		<img src="images/switch.png" width="8" height="8" />
																	</td>
																	<td height="32" align="left" valign="center"
																		bgcolor="#F7F9F9"
																		style="PADDING-LEFT: 10px; font-size: 13px; color: blue;">
																		<s:property value="examroom.title" />

																	</td>
																	<td width="120" align="center" valign="center"
																		bgcolor="#F7F9F9" class="STYLE7">
																		<s:date name="examroom.begintime"
																			format="yyyy-MM-dd HH:mm" />
																	</td>
																	<td width="120" align="center" valign="center"
																		bgcolor="#F7F9F9" class="STYLE7">
																		<s:date name="examroom.endtime"
																			format="yyyy-MM-dd HH:mm" />
																	</td>
																	<td width="80" align="center" valign="center"
																		bgcolor="#F7F9F9" class="STYLE7">
																		<s:property value="myScore" />
																	</td>
																	<td width="80" align="center" valign="center"
																		bgcolor="#F7F9F9" class="STYLE7">
																		<s:if test="ispassed==1">是</s:if>
																		<s:else>否</s:else>
																	</td>
																	<td width="80" align="center" valign="middle"
																		bgcolor="#F7F9F9">
																		<table border="0">
																			<tr>
																				<td width="20" align="right" valign="middle"
																					bgcolor="#F7F9F9">
																					<a
																						href="quizpaperinit.action?myroom.examroom.id=<s:property value="examroom.id"/>&Return=list"
																						style="font-size: 12px;"><img
																							style="CURSOR: hand" src="images/xtb021.gif"
																							width="20" height="20" />
																					</a>
																				</td>
																				<td width="60" align="center" valign="middle"
																					bgcolor="#F7F9F9">
																					<a
																						href="quizpaperinit.action?myroom.examroom.id=<s:property value="examroom.id"/>&Return=list"
																						style="font-size: 12px;">查看详情</a>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</s:iterator>
														</tbody>
													</table>
												</td>
											</tr>
										</s:if>
										<s:else>
											<tr>
												<td height="132" align='center'>
													<span style="color: red">暂无参加的考场</span>
												</td>
											</tr>
										</s:else>
									</table>

								</td>
							</tr>
						</table>
					</s:if>
					<s:if test="mytrainingcourses == true">
						<table style="margin-top: 8px;" width="100%" border="0"
							cellpadding="0" cellspacing="0">
							<tr>
								<td>

									<table width="100%" border="0" align="center" cellpadding="0"
										cellspacing="0" class="juhuangk">
										<tr>
											<td height="35">
												<table width="100%" border="0" cellpadding="0"
													cellspacing="0">
													<tr>
														<td width="150" height="35" background="images/hyxxh.gif"
															style="padding-left: 20px;">
															<span class="gqtitle">我的培训班</span>
														</td>
														<td align="right" valign="middle"
															background="images/hyxxh2.gif"
															style="padding-right: 8px;">
															<a href="myelclass_list.action" class=textbg>更多培训班</a>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<s:if test="myClasses.size() != 0">
											<tr>
												<td align="center" valign="top" bgcolor="#F7F9F9">
													<table width="100%" border="0" align="center"
														cellpadding="1" cellspacing="1" bgcolor="#FFFFFF"
														style="margin-bottom: 5px;">
														<tbody>
															<tr class="table1">
																<td width="20" align="center" valign="center"
																	background="images/bghui001.jpg">&nbsp;
																	
																</td>
																<td height="25" align="left" valign="center"
																	background="images/bghui001.jpg"
																	style="padding-left: 20px;">
																	<span class="STYLE3">培训班名称</span>
																</td>
																<td width="120" align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">
																	创建者
																</td>
																<td width="120" align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">
																	开始时间
																</td>
																<td width="120" align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">
																	结束时间
																</td>
																<td align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">
																	进入学习
																</td>
															</tr>
														<tbody id="mytrainingcourses_data"
															onMouseOut="changeback()" onMouseOver="changeto()">
															<s:iterator value="myClasses">
																<tr class="table2">
																	<td width="20" align="center" valign="center"
																		bgcolor="#F7F9F9">
																		<img src="images/switch.png" width="8" height="8" />
																	</td>
																	<td height="32" align="left" valign="center"
																		bgcolor="#F7F9F9"
																		style="PADDING-LEFT: 10px; font-size: 13px; color: blue;">
																		<s:property value="elClass.name" />

																	</td>
																	<td width="120" align="center" valign="center"
																		bgcolor="#F7F9F9" class="STYLE7">
																		<s:property value="elClass.creater.realname" />
																	</td>
																	<td width="120" align="center" valign="center"
																		bgcolor="#F7F9F9" class="STYLE7">
																		<s:date name="elClass.starttime" format="yyyy-MM-dd" />
																	</td>
																	<td width="120" align="center" valign="center"
																		bgcolor="#F7F9F9" class="STYLE7">
																		<s:date name="elClass.finishtime" format="yyyy-MM-dd" />
																	</td>
																	<td width="80" align="center" valign="middle"
																		bgcolor="#F7F9F9">
																		<table border="0">
																			<tr>
																				<td width="20" align="right" valign="middle"
																					bgcolor="#F7F9F9">
																					<a
																						href="myelclass_view.action?elclass.id=<s:property value="elClass.id" />&Return=stclalist"
																						style="font-size: 12px;"><img
																							style="CURSOR: hand" src="images/xtb021.gif"
																							width="20" height="20" />
																					</a>
																				</td>
																				<td width="60" align="center" valign="middle"
																					bgcolor="#F7F9F9">
																					<a
																						href="myelclass_view.action?elclass.id=<s:property value="elClass.id" />&Return=stclalist"
																						style="font-size: 12px;">学习详情</a>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</s:iterator>
														</tbody>
													</table>
												</td>
											</tr>
										</s:if>
										<s:else>
											<tr>
												<td height="135" align='center'>
													<span style="color: red">暂无培训班</span>
												</td>
											</tr>
										</s:else>
									</table>

								</td>
							</tr>
						</table>
					</s:if>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<!-- 考试 -->
								<s:if test="tongzhigonggao == true">
									<table width="100%" border="0" align="center" cellpadding="0"
										cellspacing="0" class="juhuangk">
										<tr>
											<td height="35">
												<table width="100%" border="0" cellpadding="0"
													cellspacing="0">
													<tr>
														<td width="150" height="35" background="images/hyxxh.gif"
															style="padding-left: 20px;">
															<span class="gqtitle">通知公告</span>
														</td>
														<td align="right" valign="middle"
															background="images/hyxxh2.gif"
															style="padding-right: 8px;">
															<a
																href="newsIndex.action?news.title=null&news.ntype.id=1&ntype.id=1"
																target="_blank" class=textbg5>更多新闻</a>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<s:if test="newsList.size() != 0">
											<tr>
												<td align="center" valign="top" bgcolor="#F7F9F9">
													<table width="100%" border="0" align="center"
														cellpadding="1" cellspacing="1"
														style="margin-bottom: 5px;">
														<tbody>
															<tr class="table1">
																<td width="20" align="center" valign="center"
																	background="images/bghui001.jpg">&nbsp;
																	
																</td>
																<td height="25" align="left" valign="center"
																	background="images/bghui001.jpg"
																	style="padding-left: 20px;">
																	<span class="STYLE3">新闻标题</span>
																</td>
																<td width="120" align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">
																	所属栏目
																</td>
																<td width="150" align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">
																	发布时间
																</td>
																<td align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">&nbsp;
																	
																</td>
															</tr>
														<tbody id="kaoshi_data" onMouseOut="changeback()"
															onMouseOver="changeto()">
															<s:iterator value="newsList">
																<tr class="table2">
																	<td width="20" align="center" valign="center"
																		bgcolor="#F7F9F9">
																		<img src="images/switch.png" width="8" height="8" />
																	</td>
																	<td height="25" align="left" valign="center"
																		bgcolor="#F7F9F9"
																		style="PADDING-LEFT: 10px; font-size: 12px;">
																		<s:property value="title" />
																	</td>
																	<td width="120" align="center" valign="center"
																		bgcolor="#F7F9F9" class="STYLE7">
																		<s:property value="ntype.name" />
																	</td>
																	<td width="150" align="center" valign="center"
																		bgcolor="#F7F9F9" class="STYLE7">
																		<s:date name="releasetime"
																			format="yyyy年MM月dd日 HH时:mm分"></s:date>
																	</td>
																	<td width="80" align="center" valign="middle"
																		bgcolor="#F7F9F9">
																		<table border="0">
																			<tr>
																				<td width="20" align="right" valign="middle"
																					bgcolor="#F7F9F9">
																					<a
																						href="newsIndexView.action?news.id=<s:property value='id'/>"
																						style="font-size: 12px;" target="_blank"><img
																							style="CURSOR: hand" src="images/xtb021.gif"
																							width="20" height="20" />
																					</a>
																				</td>
																				<td width="60" align="center" valign="middle"
																					bgcolor="#F7F9F9">
																					<a
																						href="newsIndexView.action?news.id=<s:property value='id'/>"
																						target="_blank" style="font-size: 12px;">查看</a>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</s:iterator>
														</tbody>
													</table>
												</td>
											</tr>
										</s:if>
										<s:else>
											<tr>
												<td height="80" align='center'>
													<span style="color: red">暂无通知公告</span>
												</td>
											</tr>
										</s:else>
									</table>
								</s:if>

								<!-- 考试 -->
							</td>
						</tr>
					</table>
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						style="margin-top: 8px;">
						<tr>
							<td>
								<!-- 考试 -->
								<s:if test="daibanshiwu == true">
									<table width="100%" border="0" align="center" cellpadding="0"
										cellspacing="0" class="juhuangk">
										<tr>
											<td height="35">
												<table width="100%" border="0" cellpadding="0"
													cellspacing="0">
													<tr>
														<td width="150" height="35" background="images/hyxxh.gif"
															style="padding-left: 20px;">
															<span class="gqtitle">待办事务</span>
														</td>
														<td align="right" valign="middle"
															background="images/hyxxh2.gif"
															style="padding-right: 8px;">
															<a href="myContactTags.action?tablename=DBSW"
																class=textbg5>更多事务</a>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<s:if test="myDaibanshuwu.size() != 0">
											<tr>
												<td align="center" valign="top" bgcolor="#F7F9F9">
													<table width="100%" border="0" align="center"
														cellpadding="1" cellspacing="1"
														style="margin-bottom: 5px;">
														<tbody>
															<tr class="table1">
																<td width="20" align="center" valign="center"
																	background="images/bghui001.jpg">&nbsp;
																	
																</td>
																<td height="25" align="left" valign="center"
																	background="images/bghui001.jpg"
																	style="padding-left: 20px;">
																	<span class="STYLE3">事务名称</span>
																</td>
																<td width="80" align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">
																	开始时间
																</td>
																<td width="150" align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">
																	相关行为
																</td>
																<td width="90" align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">
																	相关客户
																</td>
																<td width="80" align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">
																	联系人
																</td>
																<td align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">&nbsp;
																	
																</td>
															</tr>
														<tbody id="kaoshi_data" onMouseOut="changeback()"
															onMouseOver="changeto()">
															<s:iterator value="myDaibanshuwu">
																<tr class="table2">
																	<td width="20" align="center" valign="center"
																		bgcolor="#F7F9F9">
																		<img src="images/switch.png" width="8" height="8" />
																	</td>
																	<td height="25" align="left" valign="center"
																		bgcolor="#F7F9F9"
																		style="PADDING-LEFT: 10px; font-size: 12px;">
																		<s:property value="DBSW_SWMC" />
																	</td>
																	<td width="80" align="center" valign="center"
																		bgcolor="#F7F9F9" class="STYLE7">
																		<s:date name="DBSW_KSRQ" format="yyyy年MM月dd日"></s:date>
																	</td>
																	<td width="150" align="center" valign="center"
																		bgcolor="#F7F9F9" class="STYLE7">
																		<s:iterator value="DBSW_XGXW" id="value">
																			<s:property value="value" />
																			<br>
																		</s:iterator>
																	</td>
																	<td width="80" align="center" valign="center"
																		bgcolor="#F7F9F9" class="STYLE7">
																		<s:iterator value="DBSW_XGKH" id="value">
																			<s:property value="value" />
																			<br>
																		</s:iterator>
																	</td>
																	<td width="60" align="center" valign="center"
																		bgcolor="#F7F9F9" class="STYLE7">
																		<s:iterator value="DBSW_XGLXR" id="value">
																			<s:property value="value" />
																			<br>
																		</s:iterator>
																	</td>
																	<td width="80" align="center" valign="middle"
																		bgcolor="#F7F9F9">
																		<table border="0">
																			<tr>
																				<td width="20" align="right" valign="middle"
																					bgcolor="#F7F9F9">
																					<a
																						href="viewContactTags.action?tablename=DBSW&id=<s:property value='id'/>"
																						style="font-size: 12px;"><img
																							style="CURSOR: hand" src="images/xtb021.gif"
																							width="20" height="20" />
																					</a>
																				</td>
																				<td width="60" align="center" valign="middle"
																					bgcolor="#F7F9F9">
																					<a
																						href="viewContactTags.action?tablename=DBSW&id=<s:property value='id'/>"
																						style="font-size: 12px;">查看</a>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</s:iterator>
														</tbody>
													</table>
												</td>
											</tr>
										</s:if>
										<s:else>
											<tr>
												<td height="80" align='center'>
													<span style="color: red">暂无待办事务</span>
												</td>
											</tr>
										</s:else>
									</table>
								</s:if>

								<!-- 考试 -->
							</td>
						</tr>
					</table>
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						style="margin-top: 8px;">
						<tr>
							<td>
								<!-- 考试 -->
								<s:if test="gongzuojihua == true">
									<table width="100%" border="0" align="center" cellpadding="0"
										cellspacing="0" class="juhuangk">
										<tr>
											<td height="35">
												<table width="100%" border="0" cellpadding="0"
													cellspacing="0">
													<tr>
														<td width="150" height="35" background="images/hyxxh.gif"
															style="padding-left: 20px;">
															<span class="gqtitle">我的计划</span>
														</td>
														<td align="right" valign="middle"
															background="images/hyxxh2.gif"
															style="padding-right: 8px;">
															<a href="myContactTags.action?tablename=GZJH"
																class=textbg5>更多计划</a>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<s:if test="myPlan.size() != 0">
											<tr>
												<td align="center" valign="top" bgcolor="#F7F9F9">
													<table width="100%" border="0" align="center"
														cellpadding="1" cellspacing="1"
														style="margin-bottom: 5px;">
														<tbody>
															<tr class="table1">
																<td width="20" align="center" valign="center"
																	background="images/bghui001.jpg">&nbsp;
																	
																</td>
																<td height="25" align="left" valign="center"
																	background="images/bghui001.jpg"
																	style="padding-left: 20px;">
																	<span class="STYLE3">计划名称</span>
																</td>
																<td width="80" align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">
																	计划周期
																</td>
																<td width="150" align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">
																	相关行为
																</td>
																<td width="90" align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">
																	相关客户
																</td>
																<td width="80" align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">
																	联系人
																</td>
																<td align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">&nbsp;
																	
																</td>
															</tr>
														<tbody id="kaoshi_data" onMouseOut="changeback()"
															onMouseOver="changeto()">
															<s:iterator value="myPlan">
																<tr class="table2">
																	<td width="20" align="center" valign="center"
																		bgcolor="#F7F9F9">
																		<img src="images/switch.png" width="8" height="8" />
																	</td>
																	<td height="25" align="left" valign="center"
																		bgcolor="#F7F9F9"
																		style="PADDING-LEFT: 10px; font-size: 12px;">
																		<s:property value="GZJH_JHMC" />
																	</td>
																	<td width="80" align="center" valign="center"
																		bgcolor="#F7F9F9" class="STYLE7">
																		<s:property value="GZJH_JHZQ" />
																	</td>
																	<td width="150" align="center" valign="center"
																		bgcolor="#F7F9F9" class="STYLE7">
																		<s:iterator value="GZJH_XGXW" id="value">
																			<s:property value="value" />
																			<br>
																		</s:iterator>
																	</td>
																	<td width="80" align="center" valign="center"
																		bgcolor="#F7F9F9" class="STYLE7">
																		<s:iterator value="GZJH_XGKH" id="value">
																			<s:property value="value" />
																			<br>
																		</s:iterator>
																	</td>
																	<td width="60" align="center" valign="center"
																		bgcolor="#F7F9F9" class="STYLE7">
																		<s:iterator value="GZJH_XGLXR" id="value">
																			<s:property value="value" />
																			<br>
																		</s:iterator>
																	</td>
																	<td width="80" align="center" valign="middle"
																		bgcolor="#F7F9F9">
																		<table border="0">
																			<tr>
																				<td width="20" align="right" valign="middle"
																					bgcolor="#F7F9F9">
																					<a
																						href="viewContactTags.action?tablename=GZJH&id=<s:property value='id'/>"
																						style="font-size: 12px;"><img
																							style="CURSOR: hand" src="images/xtb021.gif"
																							width="20" height="20" />
																					</a>
																				</td>
																				<td width="60" align="center" valign="middle"
																					bgcolor="#F7F9F9">
																					<a
																						href="viewContactTags.action?tablename=GZJH&id=<s:property value='id'/>"
																						style="font-size: 12px;">查看</a>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</s:iterator>
														</tbody>
													</table>
												</td>
											</tr>
										</s:if>
										<s:else>
											<tr>
												<td height="80" align='center'>
													<span style="color: red">暂无计划</span>
												</td>
											</tr>
										</s:else>
									</table>
								</s:if>

								<!-- 考试 -->
							</td>
						</tr>
					</table>
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						style="margin-top: 8px;">
						<tr>
							<td>
								<s:if test="gongzuorizhi == true">
									<table width="100%" border="0" align="center" cellpadding="0"
										cellspacing="0" class="juhuangk">
										<tr>
											<td height="35">
												<table width="100%" border="0" cellpadding="0"
													cellspacing="0">
													<tr>
														<td width="150" height="35" background="images/hyxxh.gif"
															style="padding-left: 20px;">
															<span class="gqtitle">我的日志</span>
														</td>
														<td align="right" valign="middle"
															background="images/hyxxh2.gif"
															style="padding-right: 8px;">
															<a href="myContactTags.action?tablename=GRRZ"
																class=textbg5>更多日志</a>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<s:if test="myLog.size() != 0">
											<tr>
												<td align="center" valign="top" bgcolor="#F7F9F9">
													<table width="100%" border="0" align="center"
														cellpadding="1" cellspacing="1"
														style="margin-bottom: 5px;">
														<tbody>

															<tr class="table1">
																<td width="20" align="center" valign="center"
																	background="images/bghui001.jpg">&nbsp;
																	
																</td>
																<td height="25" align="left" valign="center"
																	background="images/bghui001.jpg"
																	style="padding-left: 20px;">
																	<span class="STYLE3">日志标题</span>
																</td>
																<td width="100" align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">
																	填写日期
																</td>
																<td width="150" align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">
																	相关行为
																</td>
																<td width="90" align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">
																	相关客户
																</td>
																<td width="80" align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">
																	联系人
																</td>
																<td width="50" align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">
																	结果
																</td>
																<td align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">&nbsp;
																	
																</td>
															</tr>
														<tbody id="kaoshi_data" onMouseOut="changeback()"
															onMouseOver="changeto()">
															<s:iterator value="myLog">
																<tr class="table2">
																	<td width="20" align="center" valign="center"
																		bgcolor="#F7F9F9">
																		<img src="images/switch.png" width="8" height="8" />
																	</td>
																	<td height="25" align="left" valign="center"
																		bgcolor="#F7F9F9"
																		style="PADDING-LEFT: 10px; font-size: 12px;">
																		<s:property value="GRRZ_BT" />
																	</td>
																	<td width="100" align="center" valign="center"
																		bgcolor="#F7F9F9" class="STYLE7">
																		<s:date name="GRRZ_TXRQ" format="yyyy年MM月dd日"></s:date>
																	</td>
																	<td colspan="4" align="center" valign="center"
																		bgcolor="#F7F9F9" class="STYLE7">
																		<table width="100%" border="0" cellspacing="0"
																			cellpadding="0">
																			<tr>
																				<td width="150" align="center"
																					style="font-size: 12px;">
																					<s:iterator value="GRRZ_XGXW" id="value">
																						<s:property value="value" />
																						<br>
																					</s:iterator>
																				</td>
																				<td width="90" align="center"
																					style="font-size: 12px;">
																					<s:iterator value="GRRZ_XGKH" id="value">
																						<s:property value="value" />
																						<br>
																					</s:iterator>
																				</td>
																				<td width="80" align="center"
																					style="font-size: 12px;">
																					<s:iterator value="GRRZ_XGLXR" id="value">
																						<s:property value="value" />
																						<br>
																					</s:iterator>
																				</td>
																				<td width="50" align="center"
																					style="font-size: 12px;">
																					<s:iterator value="GRRZ_JG" id="value">
																						<s:property value="value" />
																						<br>
																					</s:iterator>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="80" align="center" valign="middle"
																		bgcolor="#F7F9F9">
																		<table border="0">
																			<tr>
																				<td width="20" align="right" valign="middle"
																					bgcolor="#F7F9F9">
																					<a
																						href="viewContactTags.action?tablename=GRRZ&id=<s:property value='id'/>"
																						style="font-size: 12px;"><img
																							style="CURSOR: hand" src="images/xtb021.gif"
																							width="20" height="20" />
																					</a>
																				</td>
																				<td width="60" align="center" valign="middle"
																					bgcolor="#F7F9F9">
																					<a
																						href="viewContactTags.action?tablename=GRRZ&id=<s:property value='id'/>"
																						style="font-size: 12px;">查看</a>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</s:iterator>
														</tbody>
													</table>
												</td>
											</tr>
										</s:if>
										<s:else>
											<tr>
												<td height="80" align='center'>
													<span style="color: red">暂无日志</span>
												</td>
											</tr>
										</s:else>
									</table>
								</s:if>
								<!-- 考试 -->

								<!-- 考试 -->
							</td>
						</tr>
					</table>
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						style="margin-top: 8px;">
						<tr>
							<td>
								<!-- 考试 -->
								<s:if test="richenganpai == true">
									<table width="100%" border="0" align="center" cellpadding="0"
										cellspacing="0" class="juhuangk">

										<tr>
											<td height="35">
												<table width="100%" border="0" cellpadding="0"
													cellspacing="0">
													<tr>
														<td width="150" height="35" background="images/hyxxh.gif"
															style="padding-left: 20px;">
															<span class="gqtitle">我的日程</span>
														</td>
														<td align="right" valign="middle"
															background="images/hyxxh2.gif"
															style="padding-right: 8px;">
															<a href="myContactTags.action?tablename=RCGL"
																class=textbg5>更多日程</a>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<s:if test="myRC.size() != 0">
											<tr>
												<td align="center" valign="top" bgcolor="#F7F9F9">
													<table width="100%" border="0" align="center"
														cellpadding="1" cellspacing="1"
														style="margin-bottom: 5px;">
														<tbody>
															<tr class="table1">
																<td width="20" align="center" valign="center"
																	background="images/bghui001.jpg">&nbsp;
																	
																</td>
																<td height="25" align="left" valign="center"
																	background="images/bghui001.jpg"
																	style="padding-left: 20px;">
																	<span class="STYLE3">日程名称</span>
																</td>
																<td width="120" align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">
																	工作日期
																</td>
																<td width="80" align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">
																	性质
																</td>
																<td width="80" align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">
																	重要性
																</td>
																<td width="150" align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">
																	提醒时间
																</td>
																<td align="center" valign="center"
																	background="images/bghui001.jpg" class="STYLE3">&nbsp;
																	
																</td>
															</tr>
														<tbody id="kaoshi_data" onMouseOut="changeback()"
															onMouseOver="changeto()">
															<s:iterator value="myRC">
																<tr class="table2">
																	<td width="20" align="center" valign="center"
																		bgcolor="#F7F9F9">
																		<img src="images/switch.png" width="8" height="8" />
																	</td>
																	<td height="25" align="left" valign="center"
																		bgcolor="#F7F9F9"
																		style="PADDING-LEFT: 10px; font-size: 12px;">
																		<s:property value="RCGL_RCMC" />
																	</td>
																	<td width="120" align="center" valign="center"
																		bgcolor="#F7F9F9" class="STYLE7">
																		<s:date name="RCGL_RCSJ" format="yyyy年MM月dd日"></s:date>
																	</td>
																	<td width="80" align="center" valign="center"
																		bgcolor="#F7F9F9" class="STYLE7">
																		<s:property value="RCGL_RCXZ" />
																	</td>
																	<td width="80" align="center" valign="center"
																		bgcolor="#F7F9F9" class="STYLE7">
																		<s:property value="RCGL_ZYX" />
																	</td>
																	<td width="150" align="center" valign="center"
																		bgcolor="#F7F9F9" class="STYLE7">
																		<s:date name="RCGL_TXSJ" format="yyyy年MM月dd日 HH时mm分"></s:date>
																	</td>
																	<td width="80" align="center" valign="middle"
																		bgcolor="#F7F9F9">
																		<table border="0">
																			<tr>
																				<td width="20" align="right" valign="middle"
																					bgcolor="#F7F9F9">
																					<a
																						href="viewContactTags.action?tablename=RCGL&id=<s:property value='id'/>"
																						style="font-size: 12px;"><img
																							style="CURSOR: hand" src="images/xtb021.gif"
																							width="20" height="20" />
																					</a>
																				</td>
																				<td width="60" align="center" valign="middle"
																					bgcolor="#F7F9F9">
																					<a
																						href="viewContactTags.action?tablename=RCGL&id=<s:property value='id'/>"
																						style="font-size: 12px;">查看</a>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</s:iterator>
														</tbody>
													</table>
												</td>
											</tr>
										</s:if>
										<s:else>
											<tr>
												<td height="80" align='center'>
													<span style="color: red">暂无日程</span>
												</td>
											</tr>
										</s:else>
									</table>
								</s:if>
								<!-- 考试 -->

							</td>
						</tr>
					</table>
				</td>
				<td width="300" valign="top" style="padding-left: 10px;">
					<!--<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="100" valign="top"><a href="My_EvaluationInit.action"><img src="images/jtm001.jpg" width="100%" height="90" border="0"></a></td>
  </tr>
  <tr>
    <td><a href="My_ReportInit.action"><img src="images/jtm002.jpg" width="100%" height="90" border="0"></a></td>
  </tr>
			</table> -->

					<s:if test="gerenkaoqin == true">
						<table class="juhuangk" style="margin: 0px;" width="100%"
							border="0" align="center" cellpadding="0" cellspacing="0">
							<tr>
								<td height="35">
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="150" height="35" background="images/hyxxh.gif"
												style="padding-left: 20px;">
												<span class="gqtitle">个人考勤</span>
											</td>
											<td background="images/hyxxh2.gif">&nbsp;
												
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td align="left" valign="top" bgcolor="#F7F9F9"
									style="BACKGROUND-COLOR: #F7F9F9;">
									<table width="100%" border="0" cellspacing="0" cellpadding="0"
										style="BACKGROUND-COLOR: #F7F9F9; font-size: 12px">
										<tr>
											<s:if test="workAttendance.signdaotime == null">
												<td align="center" valign="bottom">
													<a href="sign.action?type=1" class=textbg5>现在签到</a>
												</td>
											</s:if>
											<s:else>
												<td width="50%" height="55" align="center" valign="bottom">
													<s:date name="workAttendance.signdaotime"
														format="HH时:mm分:dd秒"></s:date>
													<br>
													<span class="gqtitle">已签到</span>
												</td>
											</s:else>
											<s:if test="workAttendance.signtuitime == null">
												<td align="center" valign="bottom">
													<a href="sign.action?type=2" class=textbg5>现在签退</a>
												</td>
											</s:if>
											<s:else>
												<td width="50%" height="55" align="center" valign="bottom">
													<s:date name="workAttendance.signtuitime"
														format="HH时:mm分:dd秒"></s:date>
													<br>
													<span class="gqtitle">已签退</span>
												</td>
											</s:else>
										</tr>
										<tr>
											<td height="40" colspan="2" align="center" valign="middle">
												迟到
												<span class="STYLE3"> <s:property value="chidao" />
												</span>天 早退
												<span class="STYLE3"> <s:property value="zaotui" /> </span>天
												缺勤
												<span class="STYLE3"> <s:property value="queqin" /> </span>天&nbsp;&nbsp;&nbsp;
												<a href="myAttendance.action">查看详情</a>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</s:if>

					<s:if test="gerenweishen == true">
						<table class="juhuangk" style="margin: 0px;" width="100%"
							border="0" align="center" cellpadding="0" cellspacing="0">
							<tr>
								<td height="35">
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="150" height="35" background="images/hyxxh.gif"
												style="padding-left: 20px;">
												<span class="gqtitle">个人未审核</span>
											</td>
											<td background="images/hyxxh2.gif">&nbsp;
												
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<s:if test="myNoPass.size()!=0">
								<tr>
									<td align="left" valign="top" bgcolor="#F7F9F9"
										style="BACKGROUND-COLOR: #F7F9F9; line-height: 25px; font-size: 12px; padding: 10px;">
										<a href="Policy_Audit2ListInit.action"></a>
										<table width="100%" border="0" cellspacing="0" cellpadding="0"
											style="line-height: 25px; font-size: 12px;"
											id="gerenweishenhe_data">
											<tbody id="gerenweishenhe_data" onMouseOut="changeback()"
												onMouseOver="changeto()">
												<s:iterator value="myNoPass" status="status">
													<s:if
														test="#status.index==0 || (#status.index!=0 && #status.index%2==0)">
														<tr>
															<td width="50%" height="30">
																<span class="STYLE1">*</span> 未审
																<s:property value='moduleName' />
																<span class="STYLE3"> <s:property value='count' />
																</span><a
																	href="myContactTags.action?tablename=<s:property value='tablename' />">查看</a>
															</td>
													</s:if>
													<s:else>
														<td>
															<span class="STYLE1">*</span> 未审
															<s:property value='moduleName' />
															<span class="STYLE3"> <s:property value='count' />
															</span><a
																href="myContactTags.action?tablename=<s:property value='tablename' />">查看</a>
														</td>
														</tr>
													</s:else>
												</s:iterator>
											</tbody>
										</table>
										<div id="page_gerenweishenhe"
											style="text-align: center; font-size: 12px;"></div>
									</td>
								</tr>
							</s:if>
							<s:else>
								<tr>
									<td height="80" align='center'>
										<span style="color: red">没有未审核信息</span>
									</td>
								</tr>
							</s:else>
						</table>
					</s:if>
					<s:if test="gerendaishen == true">
						<table class="juhuangk" style="margin: 0px;" width="100%"
							border="0" align="center" cellpadding="0" cellspacing="0">
							<tr>
								<td height="35">
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="150" height="35" background="images/hyxxh.gif"
												style="padding-left: 20px;">
												<span class="gqtitle">管理待审核</span>
											</td>
											<td background="images/hyxxh2.gif">&nbsp;
												
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<s:if test="myDaiPass.size()!=0">
								<tr>
									<td align="left" valign="top" bgcolor="#F7F9F9"
										style="BACKGROUND-COLOR: #F7F9F9; line-height: 25px; font-size: 12px; padding: 10px;">
										<table width="100%" border="0" cellspacing="0" cellpadding="0"
											style="line-height: 25px; font-size: 12px;">
											<tbody id="gerendaishenhe_data" onMouseOut="changeback()"
												onMouseOver="changeto()">
												<s:iterator value="myDaiPass" status="status">
													<s:if
														test="#status.index==0 || (#status.index!=0 && #status.index%2==0)">
														<tr>
															<td width="50%" height="30">
																<span class="STYLE1">*</span> 待审
																<s:property value='moduleName' />
																<span class="STYLE3"> <s:property value='count' />
																</span><a
																	href="finalsearchContactTags.action?tablename=<s:property value='tablename' />&final_=1">查看</a>
															</td>
													</s:if>
													<s:else>
														<td>
															<span class="STYLE1">*</span> 待审
															<s:property value='moduleName' />
															<span class="STYLE3"> <s:property value='count' />
															</span><a
																href="finalsearchContactTags.action?tablename=<s:property value='tablename' />&final_=1">查看</a>
														</td>
														</tr>
													</s:else>
												</s:iterator>
											</tbody>
										</table>
										<div id="page_gerendaishenhe"
											style="text-align: center; font-size: 12px;"></div>
									</td>
								</tr>
							</s:if>
							<s:else>
								<tr>
									<td height="80" align='center'>
										<span style="color: red">没有待审核信息</span>
									</td>
								</tr>
							</s:else>
						</table>
					</s:if>

					<table style="margin-top: 10px;" width="100%" border="0"
						align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td height="35">
								<table width="100%" height="336" border="0" align="center"
									cellpadding="0" cellspacing="0" class="juhuangk"
									style="margin: 0px;">
									<tr>
										<td height="35">
											<table width="100%" border="0" cellpadding="0"
												cellspacing="0">
												<tr>
													<td width="150" height="35" background="images/hyxxh.gif"
														style="padding-left: 20px;">
														<span class="gqtitle">未读消息</span>
													</td>
													<td align="right" valign="top"
														background="images/hyxxh2.gif"
														style="padding-right: 15px; padding-top: 14px;">
														<a href="mess_Rec.action?pN=0&pS=10" target="_self"
															style="font-size: 12px; color: #1C8CDF;">查看全部</a>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td align="center" valign="top" bgcolor="#F7F9F9">
											<table width="100%" border="0" align="center" cellpadding="1"
												cellspacing="2">
												<tbody>
													<tr class="table1">
														<td align="center" valign="center"
															background="images/bghui001.jpg" bgcolor="#F7F9F9">&nbsp;
															
														</td>
														<td height="25" align="center" valign="center"
															background="images/bghui001.jpg">
															<span class="STYLE3">消息标题</span>
														</td>
														<td width="100" align="center" valign="center"
															background="images/bghui001.jpg" bgcolor="#F7F9F9"
															class="STYLE3">
															发送时间
														</td>
													</tr>
												<tbody id="msg_data" onMouseOut="changeback()"
													onMouseOver="changeto()">
													<s:iterator value="newMessage">
														<tr class="table2">
															<td width="20" align="center" valign="center"
																bgcolor="#F7F9F9">
																<img src="images/switch.png" width="8" height="8" />
															</td>
															<TD height="22" align="left" bgcolor="#F7F9F9"
																style="padding-left: 10px;">
																<a style="font-size: 12px;" target="_self"
																	href="mess_info.action?mess.mess_id=<s:property value="mess_id"/>&deleteType=1">
																	<s:property value="mess_title" />
																</a>
															</TD>
															<TD width="100" align="center" bgcolor="#F7F9F9"
																style="font-size: 12px;">
																<s:date name="mess_time" format="yyyy-MM-dd HH:mm" />
															</TD>
														</tr>
													</s:iterator>
												</tbody>
											</table>
											<div id="page_msg"
												style="text-align: center; font-size: 12px;">
											</div>
										</td>
									</tr>
								</table>
								<!-- 短消息 -->
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</BODY>
</HTML>
