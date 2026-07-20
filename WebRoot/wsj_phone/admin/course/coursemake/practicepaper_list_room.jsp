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
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript">
			function isAdd(courseid,pcid){
				/**
				var obj="<s:property value='examRoom.title'/>";
				if(obj!=""&&pcid!=0){
					alert("章节只能添加1个练习！");
					return;
				}
				*/
				document.myForm.submit();
			}
		</script>
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="练习编辑考场页" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<s:form action="practicepaper_addSearchInit_room.action" method="post"
			name="myForm">
			<s:hidden name="course.id" />
			<s:hidden name="pracPaper.cpage.id" />
			<input type="hidden" name="roomids" value="" id="roomids"/>
		</s:form>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<div style="font-weight: bold; font-size: 24px;">
				当前课程：
				<s:property value="course.name" />
				<s:if test="coursePage.title==''||coursePage.title==null"></s:if>
				<s:else>(章节：<s:property value="coursePage.title" />)</s:else>
			</div>
			<s:if test="examRooms.size()==0">
				该章节还没有添加考场..<br>
			</s:if>
			<s:else>
				<s:form action="practicepaper_delete" method="post"
					name="course_info" theme="simple" id="course_info">
					<table width="96%" align="center" cellspacing="2">
						<tr>
							<th width="20" height="30" align="center">
								&nbsp;
							</th>
							<th height="30" align="center">
								练习考场
							</th>
							<th height="30" align="center">
								练习所用试卷
							</th>
							<th width="70" height="30" align="center">
								题目总分
							</th>
							<th width="60" height="30" align="center">
								达标分
							</th>
							<th width="60" height="30" align="center">
								&nbsp;
							</th>
							<th width="60" height="30" align="center">
								&nbsp;
							</th>
							<th width="60" height="30" align="center">
								&nbsp;
							</th>
						</tr>
						<s:iterator value="examRooms">
						<tbody onMouseOut="changeback()" onMouseOver="changeto()">
								<tr>
									<s:if test="isBand">
										<td width="20" height="30" align="left"
											style="padding-left: 8px; color: blue;">
											&nbsp;
										</td>
									</s:if>
									<s:else>
										<td width="20" height="30" align="left"
											style="padding-left: 8px; color: blue;">
											<input type="checkbox" name="examRoom.id"
												value="<s:property value="id"/>">
										</td>
									</s:else>
									<td height="30" align="center">
										<s:property value="title" />
									</td>
									<td height="30" align="center">
										<s:property value="exampapers[0].title" />
									</td>
									<td width="70" height="30" align="center">
										<s:property value="exampapers[0].ep_tscore" />
									</td>
									<td width="60" height="30" align="center">
										<s:property value="exampapers[0].passgrade" />
									</td>
									<td width="120" height="30" align="center">
										<a
											href="exampaper_preview.action?examPaper.id=<s:property value="exampapers[0].id"/>"
											target="_blank" class="textbg4">预 览</a>
											
										<a
											href="practicepaper_alterSearchInit_room.action?course.id=<s:property value="pracPaper.course.id" />&pracPaper.cpage.id=<s:property value="pracPaper.cpage.id"/>&examRoom.id=<s:property value="id"/>"
											 class="textbg6">修改绑定</a>
									</td>
									<td width="40" align="center">
										<s:if test="sortid!=1">
											<a
												href="cpage_upsort.action?course.id=<s:property value="pracPaper.course.id" />&pracPaper.course.id=<s:property value="pracPaper.course.id" />&pracPaper.cpage.id=<s:property value="pracPaper.cpage.id" />&examRoom.id=<s:property value="id"/>&examRoom.sortid=<s:property value="sortid"/>">上移
											</a>
										</s:if>
									</td>
									<td width="40" align="center">
										<s:if test="sortid!=(examRooms.size)">
											<a
												href="cpage_downsort.action?course.id=<s:property value="pracPaper.course.id" />&pracPaper.course.id=<s:property value="pracPaper.course.id" />&pracPaper.cpage.id=<s:property value="pracPaper.cpage.id" />&examRoom.id=<s:property value="id"/>&examRoom.sortid=<s:property value="sortid"/>">下移
											</a>
										</s:if>
									</td>
								</tr>
						</tbody>
						</s:iterator>
					</table>
					<br>
					<s:hidden name="course.id"></s:hidden>
					<s:hidden name="pracPaper.course.id"></s:hidden>
					<s:hidden name="pracPaper.cpage.id"></s:hidden>
					<s:if test="isBand == 1">
						<font color="red">已经绑定</font>
					</s:if>
					<s:else>
						<font color="red">还未绑定</font>
					</s:else>
					<br>
					<input style="height: 35px;" class="textbg6" type="button"
							name="button2" onClick="chooses();" id="button2" value="选择考场" />
					<script type="text/javascript">
								function chooses(){
									//获取所有单选按钮值，看是否有选择
									var examArray=document.getElementsByName("examRoom.id");
									var value = "";
									for(var i=0;i<examArray.length;i++){
										if(examArray[i].checked==true){
											value += examArray[i].value + ",";
										}
									}
									if(value == ""){
										alert("请选中考场！！！");
										return false;
									}
									if(value.charAt(value.length-1) == ","){
										value = value.substring(0,value.length-1);
										document.getElementById("roomids").value = value;
									}
									if(window.confirm("确定选择考场绑定？")){
										myForm.action="cpage_choose_examroom.action";
										myForm.submit();
									}
								}  
					</script>
				</s:form>
			</s:else>
			<br/>
			<a style="color: red;"
				href="javascript:isAdd('<s:property value="course.id"/>','<s:property value="pracPaper.cpage.id"/>');"
				class="textbg"> 添加考场 </a>
			<a style="color: red;" href="coursepage_list.action?course.id=<s:property value='course.id' />" class="textbg">返回</a>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
