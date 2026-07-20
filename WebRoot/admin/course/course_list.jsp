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
		<script type="text/javascript" src="js/newversion/jquery.toolsbar.js"></script>
		<script type="text/javascript">
			function Obj(pp_,status_){ 
			this.pp=pp_; 
			this.status=status_;
		} 
		//按钮
		var ToolsBarObj = null;
		var pp = [];
		var status = [];
		$(function(){
			ToolsBarObj = $("#Div_ToolsBar");//存放按钮的div
			ToolsBarObj.ToolsBar_Add("toolbar_copy","复制","images/newversion/un_view.gif","copyDetail()");
			ToolsBarObj.ToolsBar_Add("toolbar_edit","修改","images/newversion/un_view.gif","editDetail()");
			ToolsBarObj.ToolsBar_Add("toolbar_yulan","预览","images/newversion/un_view.gif","yulanDetail()");
			ToolsBarObj.ToolsBar_Add("toolbar_view","查看","images/newversion/un_view.gif","viewDetail()");
		});
		
		function clickcheckbox(){
			var obj = getCheckedCheckboxs(pp,status);
			pp = obj.pp;
			status = obj.status;
			var value = 0;
			var st = 0;
			if(pp.length>1){
				ToolsBarObj.ToolsBar_Disabled("toolbar_copy");
				ToolsBarObj.ToolsBar_Disabled("toolbar_edit");
				ToolsBarObj.ToolsBar_Disabled("toolbar_yulan");
				ToolsBarObj.ToolsBar_Disabled("toolbar_view");
			}else if(pp.length == 1){
				st= status[0];
				if(st != 9){
					if(st == 0 || st == 2 || st == 6){
						ToolsBarObj.ToolsBar_Enabled("toolbar_edit");
						ToolsBarObj.ToolsBar_Disabled("toolbar_yulan");
					}else{
						ToolsBarObj.ToolsBar_Disabled("toolbar_edit");
						ToolsBarObj.ToolsBar_Enabled("toolbar_yulan");
					}
				}
				ToolsBarObj.ToolsBar_Enabled("toolbar_copy");
				ToolsBarObj.ToolsBar_Enabled("toolbar_view");
			}else {
				ToolsBarObj.ToolsBar_Disabled("toolbar_copy");
				ToolsBarObj.ToolsBar_Disabled("toolbar_edit");
				ToolsBarObj.ToolsBar_Disabled("toolbar_yulan");
				ToolsBarObj.ToolsBar_Disabled("toolbar_view");
			}
		}
		
		function copyDetail(){
			//course_copy.action?copy=1&course.id=<s:property value="id" />
			var obj = getCheckedCheckboxs(pp,status);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp.toString();
			window.location.href = "course_copy.action?copy=1&course.id="+value;
		}
		
		function editDetail(){
			//course_alterInit.action?course.id=<s:property value="id"/>
			var obj = getCheckedCheckboxs(pp,status);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp[0].toString();
			window.location.href = "course_alterInit.action?course.id="+value;
		}
		
		function yulanDetail(){
			//course_preview.action?course.id=<s:property value="id"/>   _blank
			var obj = getCheckedCheckboxs(pp,status);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp[0].toString();
			width=screen.availWidth * 0.8;
			height=screen.availHeight * 0.8;
			window.open ("course_preview.action?course.id="+value, '课程预览', 'height='+height+', width='+width+', toolbar=no, menubar=yes, scrollbars=yes, resizable=yes,location=no, status=no') ;
		}
		
		function viewDetail(){
			//course_view.action?course.id=<s:property value="id"/>
			var obj = getCheckedCheckboxs(pp,status);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp.toString();
			window.location.href = "course_view.action?course.id="+value;
		}
		
		
		//获取选中的checkbox
		function getCheckedCheckboxs(pp,status){
			var checkboxs = document.getElementsByName("courses.id");
			if(checkboxs.length>0){
				if(pp.length>0)  pp=[];
				for(var i=0;i<checkboxs.length;i++){
					if(checkboxs[i].checked){
						pp.push(checkboxs[i].value);
						status.push(document.getElementById("status_"+i).value);
					}
				}
			}
			var obj = new Obj(pp,status);
			return obj;
		}
		</script>
		<script type="text/javascript">
			if('${elmessage}' != ""){
				alert('${elmessage}');
			}
			
			
			function showtree(flag){
					var tree = document.getElementById("tree_list_td");
					var img = document.getElementById("showimg");
					if(flag){
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(false);" class="textbg5">隐藏课程类别</a>';
					}
					else{
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(true);" class="textbg5">显示课程类别</a>';
					}
				}
		</script>
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((     this .     sectionRowIndex %     2 ==     0)
		?  
		  "#ffffff" :     "#f4f4f4" )
}

.textbg4 {
	margin-top: 2px;
}

.textbg6 {
	margin-top: 2px;
}
</style>
		<script type="text/javascript" src="eltree/dtree.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>

	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td valign="middle" class="tablequiz">
					<ul class="nav">
						<li>
							<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
								<wysLib:Navigation ivalue="课程列表" />
							</div>
						</li>
						<!--<li>
				<span style="font-weight: bold;">我创建的课程</span>
			</li>-->
					</ul>
				</td>
				<td width="120" valign="middle" class="tablequiz">
					<A id=quit href="javascript:window.parent.full_screen(false);"
						class="textbg6" style="display: none">退出全屏</A>
				</td>
			</tr>
		</table>

		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<table width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<td valign="top" id="tree_list_td" style="display: none">
						<wysLib:ctypeTree rootAble="true"
							href="course_list.action?ctype.id="></wysLib:ctypeTree>
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55_1.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" id="showimg" />
					</td>
					<td valign="top">
						<s:if test="courses.size==0">没有找到符合条件的课程<br />
						</s:if>
						<s:else>
							<!-- 
							<form action="courses_delete.action" name="myclistdel">
							 -->
								<table width="100%" align="center" cellpadding="2"
									cellspacing="1">
									<tr>
										<td colspan=20>
											<table width="100%" border="0" cellpadding="0"
												cellspacing="1" bgcolor="#D1E4F5">
												<tr>
													<td width="96" bgcolor="#F8FCFE">
														<div style="text-align: left;" id="showtree">
															<a href="javascript:showtree(true);" class="textbg5">显示课程类别</a>
														</div>
													</td>
													<td bgcolor="#F8FCFE">
														<s:form action="course_list" name="myclist" theme="simple">
															<s:hidden name="ctype.id" />
															<s:hidden name="pN" id="pageNow"></s:hidden>
															<s:hidden name="pS"></s:hidden>
															课程名称：<s:textfield name="course.name"></s:textfield>
															<s:submit cssClass="textbg4" value="搜索"></s:submit>
														</s:form>
													</td>
												</tr>
											</table>
											<table width="100%" border="0" cellspacing="1"
												cellpadding="0">
												<tr>
													<td></td>
													<td width="675">
														<div id="Div_ToolsBar"></div>
													</td>
												</tr>
											</table>

										</td>
									</tr>
									<tr>
										<th width="20" align="center">
										</th>
										<th width="180" align="center">
											课程名称
										</th>
										<th width="90" align="center">
											课程类别
										</th>
										<th width="70" align="center">
											推荐学分
										</th>
										<th width="110" align="center">
											创建时间
										</th>
										<th width="80" align="center">
											课程类型
										</th>
										<th width="60" align="center">
											时长
										</th>
										<th width="50" align="center">
											章节数
										</th>
										<!--
										<th align="center" >
											开始/结束时间
										</th>
										<th align="center" >
											讲师姓名
										</th>
										-->
										<th width="80" align="center">
											状态
										</th>
										<!-- 
										<th width="140" align="center">
											&nbsp;
										</th>
										 -->
									</tr>
									<tbody onMouseOut="changeback()" onMouseOver="changeto()">
										<s:iterator value="courses" status="status">
											<tr>
												<td width="20" align="center">
													<input type="checkbox" value="<s:property value="id"/>"
														name="courses.id" onclick='clickcheckbox();'>
												</td>
												<td width="180" height="30"
													style="padding-left: 8px; color: blue;" align="left">
													<s:property value="name" />
												</td>
												<td width="90" align="center">
													<s:property value="ctype.name" />
												</td>
												<td width="70" align="center">
													<s:property value="credit" />
												</td>
												<td width="110" align="center">
													<s:date name="createtime" format="yyyy-MM-dd HH:mm:ss" />
												</td>
												<td width="80" align="center">
													<s:property value="islinkName" />
												</td>
												<td width="60" align="center">
													<s:property value="during" />
													分钟
												</td>
												<td width="50" align="center">
													<s:property value="cpagesize" />
												</td>

												<td width="80" align="center">
													&nbsp;
													<s:property value="validName" />
												</td>
												<input type="hidden"
													id="status_<s:property value='#status.index'/>"
													value="<s:property value='status' />" />
												<!-- 
												<td width="140" align="left">
													<a
														href="course_copy.action?copy=1&course.id=<s:property value="id" />"
														class=textbg4>复 制</a>
													<s:if test="status != 9">
														<s:if test="status == 0 ||status == 2">
															<a
																href="course_alterInit.action?course.id=<s:property value="id"/>"
																class="textbg4">修改</a>
														</s:if>
														<s:else>
															<a
																href="course_preview.action?course.id=<s:property value="id"/>"
																target="_blank " class=textbg4>预览</a>
														</s:else>
													</s:if>
													<a
														href="course_view.action?course.id=<s:property value="id"/>"
														class=textbg4>查看</a>

												</td>
												 -->
											</tr>
										</s:iterator>
									</tbody>
								</table>
							<!-- 
							</form>
							 -->
							<wysLib:page></wysLib:page>
						</s:else>
						&nbsp;&nbsp;
						<input type="button"
							onclick="document.location='course_addInit.action'"
							class="textbg6" value="创建课程" />
						&nbsp;&nbsp;
						<input type="submit" value="删除" class="textbg6"
							onclick="deleteFunction();" />
						&nbsp;&nbsp;
						<a class="textbg6" href="coursetype_list.action">课程类别</a>&nbsp;&nbsp;
						<a href="course_scormaddinit.action" class="textbg6"
							style="width: 130px;">导入SCORM课程</a>(支持1.2版本的课件)
					</td>
				</tr>
			</table>
			<script>
			    function deleteFunction(){
			       var checkObj = document.getElementsByName("courses.id");
				   var billIDs = "";
				   for (i = 0; i < checkObj.length; i++) {
						if (checkObj[i].checked) {
						    if(billIDs!="")billIDs+=",";
							billIDs += checkObj[i].value;
						}
					}
				   if(billIDs==""){
					  //alert("提示框", "请选择要删除的的记录！");
					  alert("请选择要删除的的记录！");
					  return ;
				   }
				   if(confirm('没有用到的课程会被真删除，确定删除？')){
				      //location = "course_deleteInit.action?ids="+billIDs;
				      alert("ibs="+billIDs)
				      location = "course_del.action?ids="+billIDs;//流程变动，更换了action
				   }
			    }
				function page(i) {
					document.getElementById("pageNow").value=i;
					myclist.submit();
				}
			</script>
		</div>

		<!-- 内容 -->
	</BODY>
</HTML>
