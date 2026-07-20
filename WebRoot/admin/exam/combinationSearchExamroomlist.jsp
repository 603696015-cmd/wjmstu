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
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script>
			function sh(id,valid,huanyuanVlaid){
			    document.getElementById("examRoom.id").value=id;
			    document.getElementById("examRoom.valid").value=valid; 
			    document.getElementById("huanyuanVlaid").value=huanyuanVlaid; 
			 	if(window.confirm("确定该操作？")){
			 		document.forms.examroom_sh_p.submit();
			 	} 
			}  
			function page(i){
				document.getElementById("pageNow").value=i;
				erform.submit();
			}
			function showCre(roomid){
			  	width=750;
				height=500;  
			  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				var rv =  window.showModalDialog("CRE_notelistInit.action?examRoom.id="+roomid+"&course.id=0&elclass.id=0&Return=examroom_prima_shlist&x="+Math.random(),null,sFeature); 
			}
		</script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="搜索结果页" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">组合搜索</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<table width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<td width="200" valign="top" id="tree_list_td">
						<wysLib:eroomLibTree
							href="combinationSearchExamroom.action?str=&examRoom.eroomLib.id="
							rootAble="true"></wysLib:eroomLibTree>
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onClick="changeTreeDisplay(this)" />
					</td>
					<td valign="top">
						<table width="100%" align="center" cellspacing="1">
							<tr>
								<!-- <th width="20" height="30" align="center" >&nbsp;</th> -->
								<th width="300" height="30" align="center">
									考场信息
								</th>
								<th width="50" height="30" align="center">
									类型
								</th>
								<%-- 
								<th width="70" height="30" align="center" >
									监考老师
								</th>
								 --%>
								 
								<th width="120" height="30" align="center">
									开始时间
								</th>
								<th width="120" height="30" align="center">
									结束时间
								</th>
								<th width="80" height="30" align="center">
									状 态
								</th>
								<th width="180" height="30" align="center">&nbsp;
									

								</th>
							</tr>
							<tbody onMouseOut="changeback()" onMouseOver="changeto()">
								<s:iterator value="examRooms">
									<tr>
										<!-- <td width="20" height="30" align="center" >
									<input type="checkbox" name="delete_item[]" value="5">
							  </td> -->
										<td style="padding: 3px 0px 3px 2px;" valign="top" align="left">
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
										<td align="left">
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
										<!--<td width="70" height="30" align="center" >
												<s:property value="eroomLib.name" />
										  </td>
											-->
										<%-- 
										<td width="70" height="30" align="center" >
											<s:if test="supervisorrealname!=null">
												<s:iterator value="supervisorrealname" var="str" status="st">
													<s:property value="str" />&nbsp;&nbsp; 
												</s:iterator>
											</s:if>
										</td>
										 --%>
										 
										<td align="center">
											<s:date name="begintime" format="yyyy-MM-dd HH:mm:ss" />
										</td>
										<td align="center">
											<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss" />
										</td>
										<td align="center">
											<s:property value="validName" />
										</td>
										<td align="left">
											<s:if test="valid == 0">
												<a
													href="erwithout_alterInit.action?examRoom.id=<s:property value="id"/>"
													class="textbg4">编 辑</a>
											</s:if>
											<s:else>
												<a
													href="examroom_sh_view.action?examRoom.id=<s:property value="id"/>"
													class="textbg4">查看</a>
											</s:else>
											<s:if test="valid == 9">
												<a style="cursor: pointer;"
													onClick="sh(<s:property value="id"/>,<s:property value="avalid"/>
												,'true');"
													class="textbg6">还原状态</a>
											</s:if>
											<s:if test="valid == 5">
												<a style="cursor: pointer;"
													onClick="sh(<s:property value="id"/>,11,'true');"
													class="textbg4">暂停</a>
											</s:if>
											<s:elseif test="valid == 11">
												<a style="cursor: pointer;"
													onClick="sh(<s:property value="id"/>,5,'true');"
													class="textbg6">开通</a>
											</s:elseif>
											<a
												href="CRE_notelistInit.action?examRoom.id=<s:property value="id"/>&Return=examroom_prima_shlist"
												 onclick="showCre(<s:property value="id"/>);return false;" class="textbg4">备注</a>
										</td>
										<!-- <td width="120" height="30" align="center" >
									<a
										href="examroom_my_delete.action?examRoom.id=<s:property value="id"/>" class="textbg4">删 除</a>
									<s:if test="iscommon==1">
									<a
										href="examroom_assignwcInit.action?examRoom.id=<s:property value="id"/>" class="textbg6">分配学员</a>
									</s:if>
									<s:else>
									<a
										href="examroom_assignInit.action?examRoom.id=<s:property value="id"/>&course.id=<s:property value="course.id"/>" class="textbg6">分配学员</a>
									</s:else>
							  </td> -->
									</tr>
								</s:iterator>
							<tbody>
						</table>
						<form action="examroom_sh_p.action" name="examroom_sh_p"
							method="post">
							<s:hidden name="examRoom.id" id="examRoom.id"></s:hidden>
							<s:hidden name="examRoom.valid" id="examRoom.valid"></s:hidden>
							<s:hidden name="huanyuanVlaid" id="huanyuanVlaid"></s:hidden>
							<s:hidden name="Return" id="Return"
								value="combinationSearchExamroom"></s:hidden>
						</form>
						<wysLib:page></wysLib:page><a href="combinationSearchExamroomInit.action" class="textbg4" style="width:80px">返回</a>
					</td>
				</tr>
			</table>
		</div>
		<form action="combinationSearchExamroom.action" name="erform"
			method="post">
			<s:hidden name="pN" id="pageNow">
			</s:hidden>
			<s:hidden name="pS">
			</s:hidden>
			<s:hidden name="examRoom.creater.realname"></s:hidden>
			<s:hidden name="examRoom.title"></s:hidden>
			<s:hidden name="examRoom.begintime"></s:hidden>
			<s:hidden name="examRoom.endtime"></s:hidden>
			<s:hidden name="examRoom.eroomLib.id"></s:hidden>
			<s:hidden name="examRoom.valid" value="-1"></s:hidden>
		</form>
		<!-- 内容 -->
	</BODY>
</HTML>
