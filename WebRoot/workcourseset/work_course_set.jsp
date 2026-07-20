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
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript">
			function sort(manner,sortid,typeid){
				document.getElementById("sortManner").value=manner;
				document.getElementById("sortId").value=sortid;
				document.getElementById("selectTypeId").value=typeid;
				basedb.action="eluser_BasedbSort.action";
				basedb.submit();
			}
			function del(basedbId){
				if(window.confirm("确认删除？")){
					document.getElementById("wcId").value=basedbId;
					basedb.action="eluser_BasedbDel.action";
					basedb.submit();
				}
			}
			function update(basedbId){
				document.getElementById("wcId").value=basedbId;
				basedb.action="work_course_alterInit.action";
				basedb.submit();
			}
		</script>
		
<style type="text/css">
	td {
		font-size: 12px;
		color: #333333;
		line-height: 150%
	}
	
	tr {
		background-color: expression(( this . sectionRowIndex % 2 == 0) ? "#ffffff" : "#f4f4f4" )
	}
</style>
	</HEAD>
	<body onLoad=" myload()"><%--  onLoad="initTable();" --%>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="职业类别管理" />
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
		<div style="margin-top: 0px; text-align: center;" class="divClass">
			
		</div>
		<div style="margin-top: 0px; text-align: center;">
			<form action="work_course_set.action" method="post" name="basedb">
				<s:hidden name="workCourse.id" id="wcId" />
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				
			</form><table width="100%" align="center" cellpadding="1" cellspacing="1">
				<tr>
					<td  width="300" height="35" align="center">
						培训班名称
					</td>

					<td  colspan="6"  height="35" align="center">
						<s:property value="elClass.name" />
					</td>
					
				</tr>
				
			</table>
			<table width="100%" align="center" cellpadding="1" cellspacing="1">
				<tr>
					<th width="300" height="30" align="center">
						职业类别
					</th>

					<th  height="30" align="center">
						状态
					</th>
					<th  height="30" align="center">
						职业课程名称
					</th>
						<th width="80"></th>
			
					
				</tr>
				<tbody onMouseOut="changeback()" onMouseOver="changeto()">
					<s:iterator value="workCourses" status="s">
						<tr>
							<td width="200" height="30" align="center" bgcolor="#FFFFFF"
								style="font-weight: bold;">
								 <s:property value="workTypeName" />
							</td>
							<td width="50" height="30" align="center">
							<s:if test="isuse==1">
								启用
							</s:if>
							<s:else>
								不启用
							</s:else>
								
							</td>
							<td height="30" align="center">
								<s:property value="coursename" />
							</td>
							
							<td width="80" align="center">
								<a href="javascript:update('<s:property value="id" />')">编辑</a>
<!--								<a href="javascript:del('<s:property value="id" />')">删除</a>-->
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		  <script>
				function page(i){
					document.getElementById("pageNow").value=i;
					basedb.submit();
				}
				
			</script>
<!--			<wysLib:page></wysLib:page>-->
		</div>
		<!-- 内容 -->
		<span style="center"><a
						href="work_course_addInit.action" class=textbg>添加字段内容</a>
					</span>
	</BODY>
</HTML>