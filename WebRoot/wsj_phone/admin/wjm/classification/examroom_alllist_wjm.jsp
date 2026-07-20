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
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
				function showtree(flag){
					var tree = document.getElementById("tree_list_td");
					var img = document.getElementById("showimg");
					if(flag){
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(false);" class="textbg5">隐藏考场树</a>';
					}
					else{
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(true);" class="textbg5">显示考场树</a>';
					}
				}
				
				var status = 0;
				function setStatus(st){
					status = st;
				}
				
				function nihao(){
					var radios = document.getElementsByName("examid");
					var value = "";
					if(radios!=undefined){
						for(var i=0;i<radios.length;i++){
							if(radios[i].checked){
								value = radios[i].value;
								break;
							}
						}
					}
					if(value == ""){
						alert("请选择一个单选框");
						return ;
					}
					if(window.confirm("确认选择该考场?")){
						window.returnValue = value + "-=tmk=-" + document.getElementById("title_"+status).value;
						window.close();
					}
					
				}
				
				function page(i){
					document.getElementById("pageNow").value=i;
					erform.submit();
				}
				
				
		</script>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((         this .         sectionRowIndex %         2 ==
		        0) ?    
				    "#ffffff" :         "#f4f4f4" )
}

.textbg4 {
	margin-top: 2px;
}

.textbg6 {
	margin-top: 2px;
}
</style>
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td valign="middle" class="tablequiz">
					<ul class="nav">
						<li>
							<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
								<wysLib:Navigation ivalue="考场列表" />
							</div>
						</li>
					</ul>
				</td>
			</tr>
		</table>

		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<table width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<td width="200px" valign="top" id="tree_list_td"
						style="display: none">
						<wysLib:eroomLibTree
							href="examroom_alllist_wjm.action?sublibs=1&examRoom.eroomLib.id="
							rootAble="true"></wysLib:eroomLibTree>
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55_1.gif" style="cursor: hand"
							onClick="changeTreeDisplay(this)" id="showimg" />
					</td>
					<td>
						<table width="100%" align="center" cellspacing="1" cellpadding="1">
							<tr>
								<td colspan=20>
									<div id="Div_ToolsBar"></div>
								</td>
							</tr>
							<tr>
								<th width="20" align="center">
								</th>
								<!-- <th width="20"  align="center" >&nbsp;</th> -->
								<th width="300" align="center">
									考场信息
								</th>
								<th width="60" align="center">
									类型
								</th>
								<th width="90" align="center">
									开考时间
								</th>
								<th width="90" align="center">
									结束时间
								</th>
								<th width="70" align="center">
									状态
								</th>
								<th width="80" align="center">
									人数
								</th>
								<!-- 
								<th width="90" align="center">
									&nbsp;
								</th>
								 -->
							</tr>
							<tbody onMouseOut="changeback()" onMouseOver="changeto()">
								<s:iterator value="examRooms" status="status">
									<input type="hidden"
										id="title_<s:property value='#status.index+1' />"
										value="<s:property value='title' />" />
									<tr>
										<td width="20" align="center">
											<input type="radio" value="<s:property value="id"/>"
												name="examid" onclick="setStatus(<s:property value='#status.index+1' />);">
										<td style="padding: 3px 0px 3px 2px;" valign="top"
											align="left">
											<div
												style="word-wrap: break-word; word-break: break-all; width: 100%;">
												<strong style="font-size: 15px; color: blue;"><s:property
														value="title" /> </strong>
												<br />
												<strong>类别:</strong>
												<s:property value="eroomLib.name" />
												<br />
												<strong>组织单位:</strong>
												<s:property value="depName" />
												<br />
												<strong>组织工钟:</strong>
												<s:property value="jingzhong" />
												<br />
												<strong>创建者:</strong>
												<s:property value="creater.realname" />
												<br />
												<s:if test="examRoom.classid!=-1">
													<strong> 所属课程: </strong>
													<s:property value="course.name" />
												</s:if>
											</div>
										</td>
										<td align="center">
											<s:if test="isApplication == 1">
												<SPAN style="color: red">【申请】</SPAN>
											</s:if>
											<s:elseif test="isApplication == 2">
												<SPAN style="color: blue;">【全工】</SPAN>
											</s:elseif>
											<s:else>
												<SPAN style="color: gray">【分配】</SPAN>
											</s:else>
										</td>

										<td align="center">
											<s:date name="begintime" format="yyyy-MM-dd HH:mm:ss" />
										</td>
										<td align="center">
											<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss" />
										</td>
										<td align="center">
											<s:property value="validName" />
										</td>
										<td align="center">
											参加：
											<s:property value="usersize" />
											<s:if test="isApplication == 1">
												<br />
												<span style="color: red">计划：<s:property
														value="planNumber" /> </span>
											</s:if>
										</td>
									</tr>
								</s:iterator>
							<tbody>
						</table>
						<wysLib:page></wysLib:page>
						<input class=textbg6 style="height: 35px;" type="button"
							value="选择" onClick="nihao();">
					</td>
				</tr>
			</table>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
