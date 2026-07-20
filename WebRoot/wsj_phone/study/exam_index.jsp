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
</STYLE>
		<script> 
			function init(isLogin,count,popIds,eroomAppcount,classAppcount){
				//初始化分页
				var count_kaoshi="<s:property value="count_kaoshi" />";
				$("#page_kaoshi").html(getPageDiv(count_kaoshi,0,5,"page_kaoshi"));
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
				
			}
			function winOpen(popid,i){
				//window.open("pop_user.action?pop.id="+popid,'弹窗信息','height=600,width=800,top=100,left=100,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no,status=no');
				window.open("pop_user.action?pop.id="+popid,'pop_win'+i,'height=550,width=495,top='+i*20+',left='+i*20+',toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no,status=no');
			}
			function sub_do(obj){
				if(obj==1){
					form_do.action="examroom_shlist.action";
				}else if(obj==2){
					form_do.action="elclass_sh_list.action";
				}else if(obj==3){
					form_do.action="news_end_trial_list.action";
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
						var erjoin=erooms[i].isApplication==1?'<SPAN style="color:red">【申请】</SPAN>':'<SPAN style="color:gray">【分配】</SPAN>';
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
														'href="#" onclick="alert(\'本考场已完成，次数也完成，不需再进入！\');return false;"'+
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
		</script>
	</HEAD>
	<BODY style="text-align: left"
		onLoad="init('<s:property value="#request.isLogin"/>','<s:property value="#request.message_no"/>','<s:property value="#request.popIds"/>','<s:property value="#request.eroom_appcount"/>','<s:property value="#request.class_appcount"/>');">
		<script type="text/javascript">if("<s:property value="count_kaoshi" />"=="1"){
					document.location='quizpaperinit.action?myroom.examroom.id=<s:property value="myrooms[0].examroom.id"/>'
				}</script>
		<s:form action="examroom_alllist" method="post" name="form_do">
			<s:hidden name="examRoom.valid" value="3" />
			<s:hidden name="examRoom.classid" value="-2" />
			<s:hidden name="elClass.status" value="3" />
			<s:hidden name="displayStatus" value="4" />
			<s:hidden name="sublibs" value="1" />
		</s:form>
		<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					&nbsp;&nbsp;&nbsp;
					<a target="_blank" href="index.action">首页</a>&nbsp;>>&nbsp;个人中心
				</div>
			</li>
		</ul>
		<!-- 内容 -->

		<!-- 考试 -->
		<table width="100%" height="234" border="0" align="center"
			cellpadding="0" cellspacing="0" class="juhuangk">
			<tr>
				<td height="35">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="150" height="35" background="images/hyxxh.gif"
								style="padding-left: 20px;">
								<span class="gqtitle">我的考试</span>
							</td>
							<td align="right" valign="middle" background="images/hyxxh2.gif"
								style="padding-right: 8px;">
								<s:if test="#request.eroom_appcount>0">
									<span style="font-size: 13px; color: red;">当前有<s:property
											value="#request.eroom_appcount" />个可报名考场</span>
								</s:if>
								<a
									href="exam_listbytitle_isPass.action?pN=0&pS=10&isCorrespond=1"
									target="_blank" class=textbg>考试报名</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="center" valign="top" bgcolor="#F7F9F9">
					<table width="100%" border="0" align="center" cellpadding="1"
						cellspacing="1">
						<tbody>
							<s:if test="myrooms.size > 0">
								<tr class="table1">
									<td width="20" align="center" valign="center"
										background="images/bghui001.jpg">
										&nbsp;

									</td>
									<td height="25" align="center" valign="center"
										background="images/bghui001.jpg">
										<span class="STYLE3">考场名称</span>
									</td>
									<td width="240" align="center" valign="center"
										background="images/bghui001.jpg" class="STYLE3">
										起止时间
									</td>
									<td align="center" valign="center"
										background="images/bghui001.jpg" class="STYLE3">
										&nbsp;

									</td>
								</tr>
								<tbody id="kaoshi_data" onMouseOut="changeback()"
									onMouseOver="changeto()">
									<s:iterator value="myrooms">
										<tr class="table2">
											<td width="20" align="center" valign="center"
												bgcolor="#F7F9F9">
												<img src="images/switch.png" width="8" height="8" />
											</td>
											<td height="25" align="left" valign="center"
												bgcolor="#F7F9F9"
												style="PADDING-LEFT: 10px; font-size: 12px;">
												<s:if test="examroom.isApplication == 1">
													<SPAN style="color: red">【申请】</SPAN>
												</s:if>
												<s:else>
													<SPAN style="color: gray">【分配】</SPAN>
												</s:else>
												<s:property value="examroom.title" />
											</td>
											<td align="center" valign="center" bgcolor="#F7F9F9"
												class="STYLE7">
												<s:date name="examroom.begintime" format="yyyy-MM-dd HH:mm" />
												&nbsp;至&nbsp;
												<s:date name="examroom.endtime" format="yyyy-MM-dd HH:mm" />
											</td>
											<td width="80" align="center" valign="middle"
												bgcolor="#F7F9F9">
												<table border="0">
													<tr>
														<td width="20" align="right" valign="middle"
															bgcolor="#F7F9F9">
															<a target="_self"
																onClick="return isEroom2('<s:property value="examroom.valid"/>');"
																href="quizpaperinit.action?myroom.examroom.id=<s:property value='examroom.id'/>"><img
																	src="images/xtb013.png" width="16" height="16" /> </a>
														</td>
														<td width="60" valign="middle" bgcolor="#F7F9F9">
															<s:if
																test="(mycount>=examroom.examcount)&&minstatus!=0&&minstatus!=1">
																<a target="_self" href="#"
																	onclick="alert('本考场已完成，次数也完成，不需再进入！');return false;"
																	style="font-size: 12px; color: gray">进入考场</a>
															</s:if>
															<s:else>
																<a target="_self"
																	onClick="return isEroom2('<s:property value="examroom.valid"/>');"
																	href="quizpaperinit.action?myroom.examroom.id=<s:property value='examroom.id'/>"
																	style="font-size: 12px;">进入考场</a>
															</s:else>
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</s:iterator>
								</tbody>
							</s:if>
							<s:else>
								<tr class="table2">
									<td height="25" colspan="4" align="center" valign="center"
										bgcolor="#F7F9F9" style="font-size: 14px;">
										暂时没有需要参加的考试
									</td>
								</tr>
							</s:else>
						</tbody>
					</table>
					<div id="page_kaoshi" style="text-align: center; font-size: 12px;">
					</div>
				</td>
			</tr>
		</table>
		<!-- 考试 -->

	
	</body>
</HTML>
