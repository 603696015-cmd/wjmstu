<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.courseman.entities.ExamRoom"%>
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
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((               this .               sectionRowIndex % 
		             2 ==  
		
		           0) ?       
		       "#ffffff" :               "#f4f4f4" )
}
</style>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
			function select_All(){
				var cks= document.getElementsByName("elusers.id");
				for(var i = 0 ; i < cks.length; i++){
					cks[i].checked= true;
				}
			}
			function select_Fan(){
				var cks= document.getElementsByName("elusers.id");
				for(var i = 0 ; i < cks.length; i++){
					cks[i].checked= !cks[i].checked;
				}
			}
			function select_Bux(){
				var cks= document.getElementsByName("elusers.id");
				for(var i = 0 ; i < cks.length; i++){
					cks[i].checked= false;
				}
			}
			function page(i){
				document.getElementById("pageNow").value=i;
				erForm.submit();
			}
			function applySubmit(userid,status){
				var isOk=true;
				if(status==2){
					 var roomid="<s:property value="examRoom.id" />";
					 //需要填写不通过原因
					 width=600;
					 height=500;
				  	 sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
					 var rv =  window.showModalDialog("addSimpleRemackInit.action?simpleRemack.type=1&simpleRemack.typeid="+roomid+"&simpleRemack.toUser.id="+userid+"&Return=examroom_auditUserlist&x="+Math.random(),null,sFeature); 
					 if(rv == null){
					 	isOk=false;
						alert("未填写备注信息，您不能进行提交！");
					 }
				}
				if(status==2||status==3){
					erForm.action="updateStudyRoomStatus.action";
				}else if(status==4){
					//删除学员
					erForm.action="deleteStudyRoomApply.action";
				}
				document.getElementById("userId").value=userid;
				document.getElementById("erStatus").value=status;
				if(isOk==true){
					erForm.submit();
				}
			}
			function doSubmit(status){
				var check=$("input[name=elusers\.id]:checked");
				if(check.length==0){
					alert("请选中复选框！");
					return;
				}
				var isOk=true;
				if(status==2){
					 var roomid="<s:property value="examRoom.id" />";
					 var userids="";
					 check.each(function(i){
					 	if(userids==""){
					 		userids=$(this).val();
					 	}else{
					 		userids+=","+$(this).val();
					 	}
					 });
					 //需要填写不通过原因
					 width=600;
					 height=500;
				  	 sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
					 var rv =  window.showModalDialog("addSimpleRemackInit.action?simpleRemack.type=1&simpleRemack.typeid="+roomid+"&simpleRemack.userids="+userids+"&Return=examroom_auditUserlist&x="+Math.random(),null,sFeature); 
					 if(rv == null){
					 	isOk=false;
						alert("未填写备注信息，您不能进行提交！");
					 }
				}
				document.getElementById("errStatus").value=status;
				if(status==4){
					myForm.action="deleteStudysRoomApply.action";
				}
				if(isOk==true){
					myForm.submit();
				}
			}
			function showCre(roomid,userid){
			  	 width=750;
				 height=500;  
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv =  window.showModalDialog("listSimpleRemack.action?simpleRemack.type=1&Return=dia&simpleRemack.typeid="+roomid+"&simpleRemack.toUser.id="+userid+"&x="+Math.random(),null,sFeature); 
			}
		</script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="考生增减" />
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
		<div style="margin-top: 0px;">

			<div style="text-align: center;">
				<h3>
					考场【
					<s:property value="examRoom.title" />
					】中需要审核的考生列表
				</h3>
				<s:form action="examroom_auditUserlist" theme="simple" method="post"
					name="erForm">
					<s:hidden name="pN" id="pageNow" />
					<s:hidden name="pS" />
					<s:hidden name="examRoom.id" />
					<s:hidden name="department.id" />
					<s:hidden name="sublibs" />
					<s:hidden name="elUser.id" id="userId" />
					<s:hidden name="myroom.status" id="erStatus" />
				姓名：
							<input name="elUser.realname"
						value="<s:property value="elUser.realname"/>" id="elUser.realname">
								账号：
							<input name="elUser.username"
						value="<s:property value="elUser.username"/>" id="elUser.username">
					审核状态： 
						<select name="elUser.active">
						<option value="-1"
							<s:if test="elUser.active==-1">selected="selected"</s:if>>
							全部
						</option>
						<option value="1"
							<s:if test="elUser.active==1">selected="selected"</s:if>>
							审核中
						</option>
						<option value="2"
							<s:if test="elUser.active==2">selected="selected"</s:if>>
							不通过
						</option>
						<option value="3"
							<s:if test="elUser.active==3">selected="selected"</s:if>>
							已通过
						</option>
					</select>
					<input type="submit" value="搜索" />
				</s:form>
				<s:form cssStyle="margin-top:0px;" action="updateStudysRoomStatus"
					method="post" name="myForm">
					<s:hidden name="examRoom.id" />
					<s:hidden name="myroom.status" id="errStatus" />

					<table cellpadding="1" cellspacing="1">
						<tr>
							<td valign="top">
								<%
									ExamRoom uu = ((ExamRoom) request.getAttribute("examRoom"));
										String x = "examroom_auditUserlist.action?examRoom.id="
												+ uu.getId()
												+ "&elUser.active=-1&sublibs=1&department.id=";
								%>
								<wysLib:dep_list_aj rootAble="true" href="<%=x%>"
									iname="department.id"></wysLib:dep_list_aj>
								<script type="text/javascript">
							w0.setValues([new DEP(<s:property value="department.id"/>,<s:property value="department.lid"/>,<s:property value="department.rid"/>)]);
						</script>
							</td>
							<td valign="top">
								<table width="900px" align="center" cellpadding="1"
									cellspacing="1">
									<tr>
										<td height="30" style="padding-left: 8px; color: blue;"
											align="left">
										</td>
										<td height="30" align="center">
											姓名
										</td>
										<td height="30" align="center">
											账号
										</td>
										<td height="30" align="center">
											部门
										</td>
										<td height="30" align="center">
											身份证
										</td>
										<td height="30" align="center">
											工种
										</td>
										<td height="30" align="center">
											性别
										</td>
										<td height="30" align="center">
											年龄
										</td>
										<td height="30" align="center">
											状态
										</td>
										<td height="30" align="center">

										</td>
										<%-- 
						<td height="30" align="center" >&nbsp;
					  	</td>
					  	 --%>
									</tr>
									<s:iterator value="myrooms">
										<tr>
											<td height="30" style="padding-left: 8px; color: blue;"
												align="left">
												<input type="checkbox"
													value="<s:property value="tester.id" />" name="elusers.id" />
											</td>
											<td height="30" align="center">
												<s:property value="tester.realname" />
											</td>
											<td height="30" align="center">
												<s:property value="tester.username" />
											</td>
											<td height="30" align="center">
												<s:property value="tester.department.name" />
											</td>
											<td height="30" align="center">
												<s:property value="tester.shenfenzheng" />
											</td>
											<td height="30" align="center">
												<s:property value="tester.jingzhong_" />
											</td>
											<td height="30" align="center">
												<s:property value="tester.sex" />
											</td>
											<td height="30" align="center">
												<s:property value="tester.age" />
											</td>
											<td height="30" align="center">
												<s:if test="status==1">审核中</s:if>
												<s:elseif test="status==2">未通过</s:elseif>
												<s:else>已通过</s:else>
											</td>
											<td height="30" align="left" style="padding-left: 10px;">
												<s:if test="status==1">
													<a onClick="return confirm('确定通过审核？')"
														href="javascript:applySubmit('<s:property value="tester.id" />',3);"
														class="textbg4">通过</a>
													<a onClick="return confirm('确定不通过审核？')"
														href="javascript:applySubmit('<s:property value="tester.id" />',2);"
														class="textbg6">不&nbsp;通&nbsp;过</a>
												</s:if>
												<s:if test="status==2">
													<a onClick="return confirm('确定通过审核？')"
														href="javascript:applySubmit('<s:property value="tester.id" />',3);"
														class="textbg4">通过</a>
													<a 
														href="listSimpleRemack.action?simpleRemack.type=1&simpleRemack.typeid=<s:property value="examRoom.id" />&simpleRemack.toUser.id=<s:property value="tester.id" />&Retrue=examroom_auditUserlist"
														class="textbg6" onclick="showCre(<s:property value="examRoom.id" />,<s:property value="tester.id" />);return false">原因查看</a>
												</s:if>
												<s:if test="status==3">
													<a onClick="return confirm('确定不通过审核？')"
														href="javascript:applySubmit('<s:property value="tester.id" />',2);"
														class="textbg6">不&nbsp;通&nbsp;过</a>
												</s:if>
												<a onClick="return confirm('确定删除学员？')"
													href="javascript:applySubmit('<s:property value="tester.id" />',4);"
													class="textbg4">删除</a>
											</td>
										</tr>
									</s:iterator>
								</table>
							</td></tr></table>
				</s:form>
				<br />
				<wysLib:page></wysLib:page>
				<a href="javascript:select_All()">全选</a>
				<a href="javascript:select_Fan()">反选</a>
				<a href="javascript:select_Bux()">全不选</a>
				<a class="textbg6" onClick="return confirm('确定通过审核？')"
					href="javascript:doSubmit(3);">通过</a>
				<a class="textbg6" onClick="return confirm('确定不通过审核？')"
					href="javascript:doSubmit(2);">不通过</a>
				<a class="textbg6" onClick="return confirm('确定删除？')"
					href="javascript:doSubmit(4);">删除</a>
				<a
					href="examroom_assignwcInit.action?examRoom.id=<s:property value="examRoom.id" />&course.id=-1"
					class="textbg6">添加人员</a>
				<a href="examroom_userAudit.action" style="width: 80px;"
					class="textbg4">返回列表</a>
			</div>
		</div>
		<br>
		<!-- 内容 -->
	</BODY>
</HTML>
