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
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((   this .   sectionRowIndex %   2 ==   0)
		? 
		 "#ffffff" :   "#f4f4f4" )
}
.textbg4{margin-top: 2px;}
.textbg6{margin-top: 2px;}
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
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<table width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<td valign="top" width="120" id="tree_list_td">
						<wysLib:st_list_aj href="sta_view.action?station.id="
							rootAble="true"></wysLib:st_list_aj>
						<script type="text/javascript">
							w0.setValues([new ST(<s:property value="station.id"/>,<s:property value="station.lid"/>,<s:property value="station.rid"/>)]);
						</script>
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
					</td>
					<td valign="top">
						<s:form action="sta_addCourseInit" name="myclist" theme="simple">
							<s:hidden name="ctype.id" />
							<s:hidden name="pN" id="pageNow"></s:hidden>
							<s:hidden name="pS"></s:hidden>
							<s:hidden name="station.id"></s:hidden>
							<s:hidden name="classid"></s:hidden>
				课程名称：<s:textfield name="course.name"></s:textfield>
							<s:submit cssClass="textbg4" value="搜索"></s:submit>

						</s:form>

						<s:if test="courses.size==0">没有找到符合条件的课程<br />
						</s:if>
						<s:else>

							<form action="courses_delete.action" name="myclistdel">
								<table width="100%" align="center" cellpadding="2"
									cellspacing="1">
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
										
										<th width="80" align="center">
											状态
										</th>

										<th width="140" align="center">
											结业方式
										</th>
									</tr>
									<tbody onMouseOut="changeback()" onMouseOver="changeto()">
										<s:iterator value="courses">
											<tr>
												<td width="20" align="center">
													<input type="checkbox" value="<s:property value="id"/>"
														name="courses.id">
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
												<td width="140" align="center">
													<select name="jieye" id="<s:property value="id"/>jieye">
														<option value="1" >学完</option>
														<option value="2">考过</option>
														<option value="3">学完并考过</option>
													</select>
												</td>
											</tr>
										</s:iterator>
									</tbody>
								</table>
							</form>
							<wysLib:page></wysLib:page>
						</s:else>
						
						<input type="submit" value="添加"  class="textbg6"
							onclick="addFunction();" />
						<input type="hidden" id="staid" name="staid" value="<s:property value="station.id"/>"/>
						<input type="hidden" id="classid" name="classid" value="<s:property value="classid"/>"/>
					</td>
				</tr>
			</table>
			<script>
			    function addFunction(){
			       var staid = document.getElementById("staid").value;
			       var checkObj = document.getElementsByName("courses.id");
				   var billIDs = "";
				   var classid = document.getElementById("classid").value;
				   for (i = 0; i < checkObj.length; i++) {
						if (checkObj[i].checked) {
							var id = checkObj[i].value+"jieye";
							var myselect = document.getElementById(id);
							var s = myselect.options[myselect.selectedIndex].value
							
						    if(billIDs!="")billIDs+=",";
							billIDs += checkObj[i].value+s;
						}
					}
				   if(billIDs==""){
					  alert("请选择要添加的记录！");
					  return ;
				   }
				   if(confirm('确定添加？')){
				      //location = "course_deleteInit.action?ids="+billIDs;
				      location = "sta_addCourse.action?ids="+billIDs+"&staid="+staid+"&classid="+classid;//流程变动，更换了action
				   }
			    }
				function page(i) {
					document.getElementById("pageNow").value=i;
					myclist.submit();
				}
			</script>
			<SCRIPT type="text/javascript">
				if("${elmessage}"!='null'&&"${elmessage}"!='')
				 alert("${elmessage}!");
				 return;
		</SCRIPT>
		</div>

		<!-- 内容 -->
	</BODY>
</HTML>
