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
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="eltree/dtree.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
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
			<form action="course_assignList3.action" name="caform" method="post">
				<s:hidden name="ctype.id" />
				<s:hidden name="pN" id="pageNow">
				</s:hidden>
				<s:hidden name="pS">
				</s:hidden>
				<s:hidden name="course.name">
				</s:hidden>
				<s:hidden name="course_sourse">
				</s:hidden>
			</form>
			<table width="1100">
				<tr>
					<td valign="top" id="tree_list_td">
						<wysLib:ctypeTree rootAble="true" href="course_assignList3.action?ctype.id=" ></wysLib:ctypeTree>
				  <td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
				  </td>
					<td height="30" valign="top">
					<s:form action="course_assignList3" name="myclist" theme="simple">
							<s:hidden name="ctype.id" />
							<input type="hidden" name="pN" value="0">
							<input type="hidden" name="pS" value="10">
						<!-- 课程来源: <s:select list="#{'1':'上级安排的课程','2':'本部门范围创建的课程'}" name="course_sourse"></s:select>-->
						课程名称：<s:textfield name="course.name"></s:textfield> <s:submit value="搜索"></s:submit>
					</s:form>
						<s:if test="courses.size==0">没有可分配的课程</s:if>
						<s:else>
							<table width="100% align="center" ellpadding="2" cellspacing="1"
								>
								<tr>
									<th width="200" align="center" >
										课程名称									</th>
									<!--<th align="center" >
										课程类别
									</th>-->
									<!--<th align="center" >
										推荐学分
									</th>-->
									<th width="70" align="center" >
										创建者									</th>
									<th width="120" align="center" >
										创建时间									</th>
									<!--<th align="center" >
										修改时间
									</th>-->
									<!--<th align="center" >
										开始/结束时间
									</th>-->
									<th width="120" align="center" >
										课程类型									</th>
									<th width="70" align="center" >
										讲师姓名									</th>
									<!--<th align="center" >
										开放状态
									</th>-->
									<th width="70" align="center" >待订人数</th> 
								  <th width="70" align="center" >状态</th> 
								  <th width="300" align="center" >&nbsp; </th> 
								</tr>
								
								<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
								<s:iterator value="courses">
									<tr>
										<td height="30" align="left" style="padding-left:8px;color:blue;"> 
											<s:property value="name" />									  </td>
										<!--<td align="center" >
											<s:property value="ctype.name" />
										</td>-->
										<td width="70" align="center" >
											<s:property value="creater.realname" />
									  </td>
										<td width="120" align="center" >
									  <s:date name="createtime" format="yyyy-MM-dd HH:mm:ss" />									  </td>
									  <td align="center" >
										<s:property value="islinkName" />
									  </td>
										<!--<td align="center" >
											<s:date name="modifytime" format="yyyy-MM-dd HH:mm:ss" />

										</td>-->
											<!--<td align="center" >
										<s:if test="islink==4"><s:date name="roomstart" format="yyyy-MM-dd HH:mm:ss" />
										-<br/><s:date name="roomend" format="yyyy-MM-dd HH:mm:ss" />
										</s:if>
										<s:else>
										--
										</s:else>
									</td>-->
									 <td width="70" align="center" >
														<s:if test="islink==4">
														  <s:property value="teacherName" />
														</s:if>
														<s:else>
														--														</s:else>
								 	  </td>
								 	  <td width="70" align="center" > 
								 	  	 <s:property value="userCount" />
								 	  </td>
										<td width="70" align="center" > 
											<!--<a
												href="course_openInit.action?course.id=<s:property value="id"/>"
												style="color: red;" class="textbg">创建完成</a>--> 
										<s:if test = "status!=0&&peice!=1"  >未定价审核</s:if>
										<s:else><s:property value="validName" /></s:else>
										
									  </td>
										<td width="300"  align="left" >
											<a target="_blank" href="course_preview.action?course.id=<s:property value="id"/>" class="textbg4">预 览</a> 
											<a href="Shopping_user_detail_list.action?course.id=<s:property value="id"/>" class="textbg6">待订学员</a>
										    <s:if test="status == 0">			 
										
											<a style="cursor:pointer;"  onClick="sh(<s:property value="id"/>, 1);"  class="textbg6">创建完成</a>
										</s:if> 
										<s:if test="peice == 1">
										<a href="shoping_assigntoUsersInit.action?course.id=<s:property value="id"/>" class="textbg4">分 配</a>
										</s:if> 
									  </td>
										<!-- <td align="center" >
											<a target="_blank" href="course_remarksList.action?course.id=<s:property value="id"/>" class="textbg6">填写备注</a>	
										<s:if test="status != 1">
											<a
												href="shoping_assigntoUsersInit?course.id=<s:property value="id"/>" class="textbg">分配部门</a>										</td>
										</s:if>		
										</td> -->							
								  </tr>
								</s:iterator><tbody>
						  </table>
						</s:else>
				  </td>
				</tr>
		  </table> 
							<form action="course_sh.action" name="course_sh" method="post">
								<s:hidden name="course.id" id="course.id"></s:hidden>
								<s:hidden name="pN" id="pageNow"></s:hidden>
								<s:hidden name="pS"></s:hidden>
								<s:hidden name="status" id="status"></s:hidden>
								<s:hidden name="Return" id="Return" value="course_assignList2"></s:hidden>
							</form> 

			<script>
								function sh(id,status){
								    document.getElementById("course.id").value=id;
								    document.getElementById("status").value=status; 
								 	if(status==1 && window.confirm("确定要申请审核？")){
								 		document.forms.course_sh.submit();
								 	}  
								}  
				function page(i){
					document.getElementById("pageNow").value=i;
					caform.submit();
				}
				
				
			</script>
			<wysLib:page></wysLib:page>

		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
