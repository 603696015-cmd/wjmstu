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
<script type="text/javascript">
	function preview(courseid){
		var islink = document.getElementById("islink").value;
		if(islink==6){
			alert("线下培训课程没有在线学习内容");
			return;
		}else{
			window.open("course_preview.action?course.id="+courseid);
		}
		
	}
</script>
	</HEAD>
	<body>
		<input type="hidden" id="islink" value="${course.islink }">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="课程制作首页" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">课程简介</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 --> 
			<div>
			<table cellpadding="1" width="100%" cellspacing="1" >
				<tr> 
					<td > 
					<a href="course_alterInit.action?course.id=${course.id}" class="textbg6">课程修改</a>
					<a href="coursepage_list.action?course.id=${course.id}"  class=textbg6>章节管理</a> 
					<a href="practicepaper_list.action?course.id=${course.id}&pracPaper.course.id=${course.id}&pracPaper.cpage.id=0"  class=textbg6>练习管理</a>
			<!-- 	<a target="_blank" href="course_preview.action?course.id=${course.id}"  class=textbg6>课程预览</a> -->	
					<a href="javascript:preview(${course.id })"  class=textbg6>课程预览</a>  
					<s:if test="examRoom==null">
						<a href="erwithout_addInit.action?course.id=<s:property value="course.id"/>&choose=false" class="textbg">添加考场</a>
					</s:if>
					<s:else>
						考场名称：<s:property value="examRoom.title"/><a href="erwithout_alterInit.action?examRoom.id=<s:property value="examRoom.id"/>" class="textbg">编辑</a> 
					</s:else>
					</td>
				</tr>
			</table>
			</div>
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<table cellpadding="1" width="100%" cellspacing="1">
				<tr>
					<td width="160" height="30" align="right">
						课程名称：
					</td>
					<td style="padding-left: 8px;">
						<label>
							<s:property value="course.name" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="right">
						课程介绍：
					</td>
					<td style="padding-left: 8px;">
						<label>
							<s:property value="course.description" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="right">
						讲师姓名：
					</td>
					<td style="padding-left: 8px;">
						<label>
							<s:property value="course.teacherName" />
						</label>
					</td>
				</tr>
				<!--<tr>
						<td width="160" height="30" align="center" >
							师资介绍：
						</td>
						<td >
							<label>
								<s:textarea name="course.teacherinfo" cols="60" rows="7"></s:textarea>
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							学习计划：
						</td>
						<td >
							<label>
								<s:textarea name="course.studyplan" cols="60" rows="7"></s:textarea>
							</label>
						</td>
					</tr>
					-->
				<tr>
					<td width="160" height="30" align="right">
						课程图片：
					</td>
					<td style="padding-left: 8px;">
						<label>
							<s:property value="course.mainimg" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="right">
						所属类别：
					</td>
					<td style="padding-left: 8px;">
						<label>
							<s:property value="course.ctype.name" />
						</label>
					</td>
				</tr>

				<tr>
					<td width="160" height="30" align="right">
						推荐学分：
					</td>
					<td style="padding-left: 8px;">
						<label>
							<s:property value="course.credit" />
						</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						课程类型：
					</td>
					<td style="padding-left: 8px;">
						<label>
							<span><s:property value="course.islinkName" /> </span>
							<br />
							<s:if test="course.islink==1||course.islink==3">
								<span id="url_desc">外部课程链接地址</span>
								<s:property value="course.exurl" />

							</s:if>
							<s:if test="course.islink==4">
								开始时间：
								 <s:date format="yyyy-MM-dd HH:mm:ss" name="course.roomstart" />
								<br />	结束时间： <s:date format="yyyy-MM-dd HH:mm:ss"
									name="course.roomend" />
							</s:if>
							<br>
							开始时间：
								 <s:date format="yyyy-MM-dd HH:mm:ss" name="course.roomstart" />
								<br />	结束时间： <s:date format="yyyy-MM-dd HH:mm:ss"
									name="course.roomend" />
							学习时间：
							<s:property value="course.during" />
							分钟 <br>
							学习询问时间：
							<s:property value="course.querytime" />
							分钟

						</label>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="right">
						章节：
					</td>
					<td style="padding-left: 8px;">
						<label>
							共<strong><s:property value="course.cpagesize" /></strong>个章节
						</label>
						<table width="100%" align="center" cellspacing="1">
							<tr>
								<th width="200" align="center">
									标题
								</th>
								<th width="40" align="center">
									章/节
								</th>
								<th width="110" align="center">
									创建时间
								</th>
								<s:if test="course.islink==0">
									<th width="70" align="center">
										学习时间
									</th>
									<th width="80" align="center">
										结业方式
									</th>
									<th width="150" align="center">
										练习标题
									</th>
								</s:if>
							</tr>
							<s:set name="course_status" value="course.status"></s:set>
							<s:set name="cLink" value="course.islink" />
							<tbody onMouseOut="changeback()" onMouseOver="changeto()">
								<s:iterator value="coursePages" id="cpid" status="cpst">
									<tr>
										<td width="200" height="30"
											style="padding-left: 8px; color: blue;" align="left">
											<s:property value="title" />
											(
											<s:property value="typeName" />
											)
										</td>
										<td width="40" align="center">
											<s:property value="propertyName" />
										</td>
										<td width="110" align="center">
											<s:date name="createtime" format="yyyy-MM-dd HH:mm:ss" />
										</td>
										<s:if test="#cLink==0">
											<td width="70" align="center">
												<s:property value="during" />
											</td>
											<td width="80" align="center">
												<s:property value="getcreditName" />
											</td>
											<td width="150" align="center">
												<s:property value="pracp.title" />
											</td>
										</s:if>
									</tr>
									<s:iterator value="pracPapers">
										<tr bgcolor="#CCCDCB" background="#CCCDCB">
											<td width="80" align="center" background="#CCCDCB"
												bgcolor="#CCCDCB">
												<i>章节练习</i>
											</td>
											<td width="200" height="30" align="center" bgcolor="#CCCDCB">
												<i> <s:property value="examPaper.title" /> </i>
											</td>
											<td width="40" align="center" bgcolor="#CCCDCB">
												<i> <s:date name="examPaper.createtime"
														format="yyyy-MM-dd HH:mm:ss" /> </i>
											</td>
											<td width="110" align="center" bgcolor="#CCCDCB">
												<i> <s:date name="examPaper.modifytime"
														format="yyyy-MM-dd HH:mm:ss" /> </i>
											</td>
											<td width="70" align="center" bgcolor="#CCCDCB">
												<i> <s:if test="examPaper.random">随机</s:if> <s:else>手工</s:else>
												</i>
											</td>
											<td colspan="4" align="center" bgcolor="#CCCDCB">
											</td>
										</tr>
									</s:iterator>
								</s:iterator>
								<s:iterator value="pracPapers">
									<tr>
										<td width="80" align="center" bgcolor="#CCCDCB">
											<i>课程练习</i>
										</td>
										<td width="200" height="30" align="center" bgcolor="#CCCDCB">
											<i> <s:property value="examPaper.title" /> </i>
										</td>
										<td width="40" align="center" bgcolor="#CCCDCB">
											<i> <s:date name="examPaper.createtime"
													format="yyyy-MM-dd HH:mm:ss" /> </i>
										</td>
										<td width="110" align="center" bgcolor="#CCCDCB">
											<i> <s:date name="examPaper.modifytime"
													format="yyyy-MM-dd HH:mm:ss" /> </i>
										</td>
										<td width="70" align="center" bgcolor="#CCCDCB">
											<i> <s:if test="examPaper.random">随机</s:if> <s:else>手工</s:else>
											</i>
										</td>
										<td colspan="4" align="center" bgcolor="#CCCDCB">
											<!--  	<a href="practicepaper_list.action?course.id=${course.id }&pracPaper.course.id=${course.id }&pracPaper.cpage.id=0">练习管理</a> -->
										</td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="right">
						课程状态：
					</td>
					<td style="padding-left: 8px;">
						<label>
							<s:property value="course.validName" />
						</label>
					</td>
				</tr>
			</table>
			<table cellpadding="1" width="900" cellspacing="1">
		  <tr>
					<td>
						<s:if test="course.status!=5">
						<a href="course_alterInit.action?course.id=${course.id}"
							class="textbg">课程修改</a>
						<a
							href="practicepaper_list.action?course.id=${course.id}&pracPaper.course.id=${course.id}&pracPaper.cpage.id=0"
							style="color: red;" class=textbg>练习管理</a>
						<a href="coursepage_list.action?course.id=${course.id}"
							style="color: red;" class=textbg>章节管理</a>
						<a href="course_open.action?course.id=<s:property value="course.id"/>"
							style="color:red" onclick="return window.confirm('是否创建完成?')" class="textbg">创建完成</a>
						</s:if>
						<s:else>
							<a href="course_addInit.action"
								class="textbg" style="color: red">创建课程</a>
							<a href="elclass_addInit.action"
								class="textbg" style="color: red">创建培训班</a>
						
						</s:else>
						<a target="_blank"
							href="course_preview.action?course.id=${course.id}" class=textbg>课程预览</a>
						<s:if test="Return==null||Return==''"><a href="course_list.action" class=textbg>返回课程列表</a>
						</s:if>
						<s:if test="Return=='csc'">
						<a href="combinationSearchCourselist.action?course.ctype.id=1&course.status=-1" class=textbg>返回</a>
						</s:if>
					</td>
				</tr>
			</table>
			<br />
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
