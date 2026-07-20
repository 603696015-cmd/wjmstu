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
				<span style="font-weight: bold;">可分配的课程 </span>
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
					<td width="120" valign="top" id="tree_list_td" style="display:none">
						<wysLib:ctypeTree rootAble="true"
							href="course_hotsetlist.action?pS=10&pN=0&ctype.id="></wysLib:ctypeTree>
				  <td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55_1.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" id="showimg" />
				  </td>
					<td valign="top">
					<table width="100%" border="0" cellspacing="1" cellpadding="0" bgcolor="#D1E4F5">
  <tr>
    <td width="95" bgcolor="#F8FCFE"><div style="text-align: left;" id="showtree">
							<a href="javascript:showtree(true);" class="textbg5">显示课程类别</a>
			</div></td>
    <td bgcolor="#F8FCFE"><s:form action="course_hotsetlist" name="myclist" theme="simple">
							<s:hidden name="pN" id="pageNow2" value="0">
						  </s:hidden>
								<s:hidden name="pS"/>
						课程名称：<s:textfield name="course.name"></s:textfield>
								<s:submit value="搜索"></s:submit>
						</s:form>
						<s:if test="courses.size==0">没有可分配的课程</s:if>
						<s:else></td>
  </tr>
</table>						
							<form action="course_hotsetlist.action" name="caform"
								method="post">
								<s:hidden name="pN" id="pageNow">
								</s:hidden>
								<s:hidden name="pS">
								</s:hidden>
								<s:hidden name="course.name">
								</s:hidden>
								<s:hidden name="course_sourse">
								</s:hidden>
								<table width="100%" align="center" cellpadding="2"
									cellspacing="1" >
									<tr>
										<th align="center" >
										</th>
										<th align="center" >
											课程名称
										</th>
										<th align="center" >
											创建人
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
											开放状态
										</th>
									</tr>
									<tbody onMouseOut="changeback()" onMouseOver="changeto()">
									<s:iterator value="courses">
										<tr>
											<td align="center" >
												<input type="checkbox" value="<s:property value="id"/>"
													name="courses.id">
											</td>
											<td height="30" align="left" >
												[
												<SPAN style="color: red">
												<s:property value="hotName" />
											  </SPAN>]
										  <s:property value="name" /></td>
											<td align="center" >
												<s:property value="creater.realname" />

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
												<s:property value="validName" />
											</td>
										</tr>
									</s:iterator>
									</tbody>
							  </table>
			<script>
				function page(i){
					document.getElementById("pageNow").value=i;
					caform.submit();
				}
				function setHot(){
			 		caform.action="course_hotset.action";
			 		caform.submit();
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
								<br>
								设置
								<select name="course.hot">
									<option value="0">
										取消
									</option>
									<option value="1">
										推荐
									</option>
									<option value="2">
										热门
									</option>
									<option value="3">
										重点
									</option>
								</select>
								<input type="button" onClick="setHot()" value="提交">
							</form>
						</s:else>
					</td>
				</tr>
			</table>
			<wysLib:page></wysLib:page>

		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
