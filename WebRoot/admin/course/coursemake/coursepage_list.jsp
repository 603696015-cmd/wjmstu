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
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="章节列表" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">管理章节</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="coursepage_addInit.action?course.id=<s:property value="course.id"/>&coursePage.course.id=<s:property value="course.id"/>
				">添加章节</a>
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
			<!--
			<div style="font-size: 15px; font-weight: bolder">
				<span style="color: blue"><s:if test="course.status!=0">不能再编辑章节学习信息(时间等)</s:if>
				</span>
			</div>
			-->
			<s:if test="course.islink==1||course.islink==4||course.islink==6">
				<span style="color: red">当前课程不需要设置章节</span><br/>
				<a href="course_view.action?course.id=${course.id}" class="textbg">返回制作首页</a>
			</s:if>
			<s:else>
				<s:if test="coursePages.size==0">
					<span style="color: red">该课程目前没有内容</span>
					<br>
				</s:if>
				<s:else>
					<form action="coursepage_delete.action" method="post">
						<table width="100%" align="center" cellspacing="1">
							<tr>
								<th width="80" align="center">
									&nbsp;
								</th>
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
								<th width="40" align="center">
									&nbsp;
								</th>
								<s:if test="course.islink==0">
									<th width="70" align="center">
										&nbsp;
									</th>
								</s:if>
								<th width="40" align="center">
									&nbsp;
								</th>
								<th width="40" align="center">
									&nbsp;
								</th>
								<th width="40" align="center">
									&nbsp;
								</th>
							</tr>
							<s:set name="course_status" value="course.status"></s:set>
							<s:set name="cLink" value="course.islink" />
							<tbody onMouseOut="changeback()" onMouseOver="changeto()">
								<s:iterator value="coursePages" id="cpid" status="cpst">
									<tr>
										<td width="80" align="center">
											<input type="checkbox" name="coursePages.id"
												value="<s:property value="id" />">
										</td>
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
										<td width="40" align="center">
											<a
												href="coursepage_alterInit.action?coursePage.id=<s:property value="id"/>"
												class=textbg4>编辑</a>
										</td>
										<s:if test="#cLink==0">
											<td width="70" align="center">
												<a
													href="practicepaper_list.action?course.id=${course.id }&pracPaper.course.id=${course.id }&pracPaper.cpage.id=<s:property value="id"/>"
													class=textbg6>编辑练习</a>
											</td>
										</s:if>
										<!-- 考场信息 -->
										<td width="80" align="center">
											<a
													href="practicepaper_list_room.action?course.id=${course.id }&pracPaper.course.id=${course.id }&pracPaper.cpage.id=<s:property value="id"/>"
													class=textbg6>编辑考场</a><!-- 章节考场 -->
										</td>
										<td width="40" align="center">
											<s:if test="sortid!=1">
												<a
													href="coursepage_upsort.action?course.id=<s:property value="course.id"/>&coursePage.sortid=<s:property value="sortid"/>">上移
												</a>
											</s:if>
										</td>
										<td width="40" align="center">
											<s:if test="#cpid.sortid!=(coursePages.size)">
												<a
													href="coursepage_downsort.action?course.id=<s:property value="course.id"/>&coursePage.sortid=<s:property value="sortid"/>">下移
												</a>
											</s:if>
										</td>
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
						<s:hidden name="course.id">
						</s:hidden>
						<BR>
						<script type="text/javascript">
					function selectall(){
						var objs = document.getElementsByName("coursePages.id");
						for(var i = 0 ; i <objs.length;i++){
							objs[i].checked='checked';	
						}
					}
					function dselectall(){
						var objs = document.getElementsByName("coursePages.id");
						for(var i = 0 ; i <objs.length;i++){
							objs[i].checked=false;	
						}
					}
				</script>
						<a href="javascript:selectall()" class="textbg4">全选</a>&nbsp;&nbsp;&nbsp;
						<a href="javascript:dselectall()" class="textbg4" style="width:70px">全不选</a>&nbsp;&nbsp;&nbsp;
						<input type="submit" class=textbg6 value="删除"
							onClick="return window.confirm('确定删除它们？')">
					</form>
				</s:else>
				<div>
					<a
						href="coursepage_addInit.action?course.id=<s:property value="course.id"/>&coursePage.course.id=<s:property value="course.id"/>"
						class="textbg" style="color: red;">添加课程章节 </a>
					<a href="course_view.action?course.id=${course.id}" class="textbg">返回制作首页</a>
					<%-- 
					<a href="coursepage_importInit.action?course.id=<s:property value="course.id"/>"
						class="textbg">导入课程章节</a>
					<a href="download.jsp?filename=elstuffs/zhangjie.xls" class="textbg">章节格式下载</a>
					 --%>
				</div>
			</s:else>
			<br>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>