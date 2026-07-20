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
		<script type="text/javascript" src="js/menu.js"></script>
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<script type="text/javascript" src="eltree/dtree.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="课程列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">我创建的课程</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<table width="100%">
				<tr>
					<td width="200px" valign="top" id="tree_list_td">
						<wysLib:ctypeTree rootAble="true"
							href="myCourse_list.action?ctype.id=" ></wysLib:ctypeTree>
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
					</td>
					<td valign="top">
						<s:form action="myCourse_list" name="myclist" theme="simple">
							<s:hidden name="pN" id="pageNow"></s:hidden>
							<s:hidden name="pS"></s:hidden>
				课程名称：<s:textfield name="course.name"></s:textfield>
							<s:submit value="搜索"></s:submit>

						</s:form>

						<s:if test="courses.size==0">没有找到符合条件的课程</s:if>
						<s:else>

							<form action="courses_delete.action" name="myclistdel">
								<table width="900px" align="center" cellpadding="2"
									cellspacing="1" >
									<tr>
										<th align="center" >
										</th>
										<th align="center" >
											课程名称
										</th>
										<th align="center" >
											课程类别
										</th>
										<th align="center" >
											推荐学分
										</th>
										<th align="center" >
											创建时间
										</th>
										<th align="center" >
											课程类型
										</th>
										<!--
										<th align="center" >
											开始/结束时间
										</th>
										<th align="center" >
											讲师姓名
										</th>
										-->
										<th align="center" >
											开放状态
										</th>

										<th align="center" >&nbsp;
											
										</th>
									</tr>
									<tbody onMouseOut="changeback()" onMouseOver="changeto()">
										<s:iterator value="courses">
											<tr>
												<td align="center" >
													<input type="checkbox" value="<s:property value="id"/>"
														name="courses.id">
												</td>
												<td align="center" >
													<s:property value="name" />
												</td>
												<td align="center" >
													<s:property value="ctype.name" />
												</td>
												<td align="center" >
													<s:property value="credit" />
												</td>
												<td align="center" >
													<s:date name="createtime" format="yyyy-MM-dd HH:mm:ss" />
												</td>
												<td align="center" >
													<s:property value="islinkName" />
												</td>

												<!--<td align="center" >
												<s:if test="islink==4"><s:date name="roomstart" format="yyyy-MM-dd HH:mm:ss" />
												-<br/><s:date name="roomend" format="yyyy-MM-dd HH:mm:ss" />
												</s:if>
												<s:else>
												--
												</s:else>
											</td>
										
											-->
												<td align="center" >
													&nbsp;
													<s:property value="validName" /> 
													<!-- <s:if test="status==0">
														<a
															href="course_openInit.action?course.id=<s:property value="id"/>"
															style="color: red;" class="textbg">申请开通</a>
													</s:if>
													<s:if test="status==5">
														<a
															href="course_applyfor_alterInit.action?course.id=<s:property value="id"/>"
															style="color: black;" class="textbg">申请修改</a>
															<a
															href="course_audit.action?course.id=<s:property value="id"/>&courseAudit.status=2"
															style="color: black;" class="textbg">查看回复</a>
													</s:if> -->
												</td>
												<td align="center" >
												<s:if test="status != 9">
													<s:if test="status == 0 ||status == 2">
														<s:if test="status == 2">
															<a target="_parent"
															href="courseman.action?course.id=<s:property value="id"/>"
															class="textbg">修改课程</a>
														</s:if>
													 	<s:else>
															<a target="_parent"
															href="courseman.action?course.id=<s:property value="id"/>"
															class="textbg">课件制作</a>
													 	</s:else>
													</s:if>
													 <s:else>
														 <a href="course_preview.action?course.id=<s:property value="id"/>" target= "_blank " class=textbg>预览</a>
													 </s:else> 
												</s:if>
												</td>
												<!-- td align="center" >
						 <a href="course_deleteInit.action?course.id=<s:property value="id"/>">申请删除课程</a>
						</td-->
											</tr>
										</s:iterator>
									</tbody>
								</table>
							</form>
							<input type="submit" value="删除"
								style="margin-top: 20px; margin-left: 40px;"
								onclick="deleteFunction();">
						</s:else>
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
					  alert("提示框", "请选择要删除的的记录！");
					  return ;
				   }
				   if(confirm('确定删除？')){
				      location = "course_deleteInit.action?ids="+billIDs;
				   }
			    }
				function page(i) {
					document.getElementById("pageNow").value=i;
					myclist.submit();
				}
			</script>
			<wysLib:page></wysLib:page>

		</div>

		<!-- 内容 -->
	
	</body>
</HTML>
