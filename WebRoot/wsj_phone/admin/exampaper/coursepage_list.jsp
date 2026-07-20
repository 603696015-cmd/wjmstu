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
		
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="章节列表" /></div>
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
				">添加章节
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;"><!--
			<div style="font-size: 15px; font-weight: bolder">
				<span style="color: blue"><s:if test="course.status!=0">不能再编辑章节学习信息(时间等)</s:if>
				</span>
			</div>
			--><s:if test="coursePages.size==0">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="48%" align="right"><img src="images/wrong.gif"/></td>
    <td><font size="+1" color="#FF0000">该课程目前没有章节内容!</font></td>
  </tr>
</table>

		</s:if>
			<s:else>
				<form action="coursepage_delete.action" method="post">
					<table width="900" align="center" cellspacing="1">
						<tr>
							<th align="center" >&nbsp;
								
							</th>
							<th align="center" >
								标题
							</th>
							<th align="center" >
								章/节
							</th>
							<th align="center" >
								创建时间
							</th>
							<th align="center" >
								学习时间
							</th>
							<!--<th align="center" >&nbsp;
								
							</th>-->
							<th align="center" >&nbsp;
								
							</th>
							<th align="center" >&nbsp;
								
							</th>
							<th align="center" >&nbsp;
								
							</th>
						</tr>
						<s:set name="course_status" value="course.status"></s:set>
						<s:iterator value="coursePages" id="cpid" status="cpst">
							<tr>
								<td align="center" >
										<input type="checkbox" name="coursePages.id"
											value="<s:property value="id" />">
								</td>
								<td align="center" >
									<s:property value="title" />(<s:property value="typeName" />)
								</td>
								<td align="center" >
									<s:property value="propertyName" />
								</td>
								<td align="center" >
									<s:date name="createtime" format="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td align="center" >
									<s:property value="during" />
								</td>
								<td align="center" >
									<a
										href="coursepage_alterInit.action?coursePage.id=<s:property value="id"/>">编辑</a>
								</td>
								<!--<td align="center" >
									<a
										href="practicepaper_list.action?course.id=${course.id }&pracPaper.course.id=${course.id }&pracPaper.cpage.id=<s:property value="id"/>">编辑练习</a>
								</td>-->
								<td align="center" >
									<s:if test="sortid!=1">
										<a
											href="coursepage_upsort.action?course.id=<s:property value="course.id"/>&coursePage.sortid=<s:property value="sortid"/>">上移
										</a>
									</s:if>
								</td>
								<td align="center" >
									<s:if test="#cpid.sortid!=(coursePages.size)">
										<a
											href="coursepage_downsort.action?course.id=<s:property value="course.id"/>&coursePage.sortid=<s:property value="sortid"/>">下移
										</a>
									</s:if>
								</td>
							</tr>
							<s:iterator value="pracPapers">
								<tr bgcolor="#CCCDCB" background="#CCCDCB">
									<td align="center" bgcolor="#CCCDCB" background="#CCCDCB">
										<i>章节练习</i>
									</td>
									<td align="center" bgcolor="#CCCDCB">
										<i><s:property value="examPaper.title" /> </i>
									</td>
									<td align="center" bgcolor="#CCCDCB">
										<i><s:date name="examPaper.createtime"
												format="yyyy-MM-dd HH:mm:ss" /> </i>
									</td>
									<td align="center" bgcolor="#CCCDCB">
										<i> <s:date name="examPaper.modifytime"
												format="yyyy-MM-dd HH:mm:ss" /> </i>
									</td>
									<td align="center" bgcolor="#CCCDCB">
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
								<td align="center" bgcolor="#CCCDCB">
									<i>课程练习</i>
								</td>
								<td align="center" bgcolor="#CCCDCB">
									<i><s:property value="examPaper.title" /> </i>
								</td>
								<td align="center" bgcolor="#CCCDCB">
									<i><s:date name="examPaper.createtime"
											format="yyyy-MM-dd HH:mm:ss" /> </i>
								</td>
								<td align="center" bgcolor="#CCCDCB">
									<i> <s:date name="examPaper.modifytime"
											format="yyyy-MM-dd HH:mm:ss" /> </i>
								</td>
								<td align="center" bgcolor="#CCCDCB">
									<i> <s:if test="examPaper.random">随机</s:if> <s:else>手工</s:else>
									</i>
								</td>
								<td colspan="4" align="center" bgcolor="#CCCDCB">
									<!--  	<a href="practicepaper_list.action?course.id=${course.id }&pracPaper.course.id=${course.id }&pracPaper.cpage.id=0">练习管理</a> -->
								</td>
							</tr>
						</s:iterator>
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
					<a href="javascript:selectall()">全选</a>&nbsp;&nbsp;&nbsp;
					<a href="javascript:dselectall()">全不选</a>&nbsp;&nbsp;&nbsp;
					<input type="submit"
						value="删除" onClick="return window.confirm('确定删除它们？')">
				</form>
			</s:else>
			<br>
			
				<!--<a
					href="coursepage_importInit.action?course.id=<s:property value="course.id"/>" class="textbg">导入课程章节　</a> <a href="download.jsp?filename=elstuffs/zhangjie.xls"  class="textbg">章节格式下载</a>-->
			
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>

