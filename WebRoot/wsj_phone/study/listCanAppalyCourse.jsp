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
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="eltree/dtree.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">我要选课</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">

			<table width="100%">
				<tr>
					<td width="200px" valign="top" id="tree_list_td">
						<wysLib:ctypeTree rootAble="true"
							href="listCanAppalyCourse.action?course.name=&ctype.id=" />
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
					</td>
					<td valign="top" height="300px;">
						<s:if test="courses.size==0"> 没有符合条件的课程<a
								href="listCanAppalyCourseInit.action">重新搜索</a>
						</s:if>
						<s:else>
							<s:form theme="simple" action="listCanAppalyCourse" name="caform"
								method="post">
								<s:hidden name="pN" id="pageNow">
								</s:hidden>
								<s:hidden name="pS">
								</s:hidden>
			课程名称：<s:textfield name="course.name">
								</s:textfield>
								<input onclick="search();" type="button" value="搜索" />
								<s:hidden name="course_sourse">
								</s:hidden>
								<s:hidden name="course.id" id="cid"></s:hidden>
							</s:form>
							<table width="96%" align="center" cellpadding="2" cellspacing="1"
								>
								<tr>
									<th align="center" >
										课程名称
									</th>
									<th align="center" >
										课程创建者
									</th>
									<th align="center" >
										创建时间
									</th>
									<th align="center" >
										课程类别
									</th>
									<!-- th align="center" >
										推荐学分
									</th-->
									<!-- th align="center" >
										及格线(百分制)
									</th-->
									<th align="center" >
										课程时长
									</th>
									<th align="center" >
										开始/结束时间
									</th>
									<th align="center" >
										讲师姓名
									</th>
									<th align="center" >
										开放状态
									</th>
									<th align="center" >
										操作
									</th>
								</tr>
								<s:iterator value="courses">
									<tr>
										<td align="center" >
											<s:property value="name" />
										</td>
										<td align="center" >
											<s:property value="ctype.realname" />
										</td>
										<td align="center" >
											<s:date name="createtime" format="yyyy-MM-dd HH:mm:ss" />
										</td>
										<td align="center" >
											<s:property value="ctype.name" />
										</td>
										
										<!-- td align="center" >
											<s:property value="credit" />
										</td-->
									
										<!-- td align="center" >
											<s:property value="validName" />
										</td-->
										<td align="center" >
											<s:property value="during" />
										</td>
											<td align="center" >
										<s:if test="islink==4"><s:date name="roomstart" format="yyyy-MM-dd HH:mm:ss" />
										-<br/><s:date name="roomend" format="yyyy-MM-dd HH:mm:ss" />
										</s:if>
										<s:else>
										--
										</s:else>
									</td>
									 <td align="center" >
														<s:if test="islink==4">
														  <s:property value="teacherName" />
														</s:if>
														<s:else>
														--
														</s:else>
													</td>
										<td align="center" >
											<s:property value="validText" />
										
										</td>
										<td align="center" >
											<s:if test="valid!=1"><a href="javascript:applyCourse(<s:property value="id"/>);">申请</a></s:if>
											
										</td>
									</tr>
								</s:iterator>
							</table>
						</s:else>
					</td>
				</tr>
			</table>
			<script>
				function search(){
					document.getElementById("pageNow").value=0;
					caform.action="listCanAppalyCourse.action";
					caform.submit();
				}
				function page(i){
					document.getElementById("pageNow").value=i;
					caform.action="listCanAppalyCourse.action";
					caform.submit();
				}
				function applyCourse(i){
					caform.action="submitAppalyCourse.action";
					document.getElementById("cid").value=i;
					caform.submit();
				}
				
			</script>
			<wysLib:page></wysLib:page>


		</div>

		<!-- 内容 -->
	
	</body>
</HTML>
