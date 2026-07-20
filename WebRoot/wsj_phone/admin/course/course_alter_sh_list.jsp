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
		<TITLE>课程管理</TITLE>
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
		<script type="text/javascript">
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
		</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="课程列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">课程修改审核</span>
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
		<form action="course_alter_sh_list.action" name="caform" method="post">
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
		<table width="100%">
				<tr>
					<td valign="top" id="tree_list_td" style="display:none">
						<wysLib:ctypeTree rootAble="true" href="course_alter_sh_list.action?ctype.id="  ></wysLib:ctypeTree>
				  <td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55_1.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" id="showimg" />
				  </td>
					<td valign="top">
						
					<s:form action="course_alter_sh_list" name="myclist" theme="simple">
						<s:hidden name="ctype.id" />
                        <table width="100%" border="0" cellspacing="1" bgcolor="#D1E4F5">
  <tr>
    <td width="100" bgcolor="#F8FCFE"><div style="text-align: left;" id="showtree">
									<a href="javascript:showtree(true);" class="textbg5">显示课程类别</a>
								</div></td>
    <td bgcolor="#F8FCFE">&nbsp;课程名称：<s:textfield name="course.name"></s:textfield> <s:submit value="搜索"></s:submit></td>
  </tr>
</table>

						
					</s:form>
		 <s:if test="courses.size==0">没有找到需要审核的课程</s:if>
		<s:else>
			<table width="100%" align="center" cellpadding="2" cellspacing="1"
				>
				<tr>
					<th align="center" >
						课程名称					</th>
					<th width="70" align="center" >
						创建者					</th>
					<!--<th align="center" >
						课程类别
					</th>
					<th align="center" >
						推荐学分
					</th>-->
					<th width="110" align="center" >
						创建时间					</th>
					<!--<th width="150" align="center" >
											开始/结束时间				  </th>-->
										<th width="70" align="center" >
											讲师										</th>
					<th width="100" align="center" >
						状态					</th>
					<th width="220" align="center" >&nbsp;				  </th>
				   <!--  <th width="50" align="center" >&nbsp;</th>
				    <th width="90" align="center" >&nbsp;</th> --> 
				    
				</tr>
				<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
				<s:iterator value="courses">
					<tr>
						<td height="30" align="left" style="padding-left:8px;color:blue;"> 
							<s:property value="name" />						</td>
						<td width="70" align="center" >
							<s:property value="creater.realname" />					  </td>
						<!--<td align="center" >
							<s:property value="ctype.name" />
						</td>
						<td align="center" >
							<s:property value="credit" />
						</td>-->
						<td width="110" align="center" >
							<s:date name="createtime" format="yyyy-MM-dd HH:mm:ss" />					  </td>
							<!--<td width="150" align="center" >
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
												--												</s:else>					  </td>
						<td width="100" align="center" >
							<s:if test="status != 1">
								<s:property value="validName" />
							</s:if>
							<s:else>
								<div style="color:red;"><s:property value="validName" /></div>
							</s:else> 
						</td>
					 <!-- <td width="50" align="center" >
					  <a target="_parent" href="courseman.action?course.id=<s:property value="id"/>" class="textbg4">修 改</a> </td>	  
					  <td width="50" align="center" ><a href="course_deleteOp.action?course.id=<s:property value="id"/>"
								onclick="return confirm('确定删除该课程？')" class="textbg4">删除</a></td> -->	
					  <td width="220" align="left" >
					  	<s:if test="status == 6"> 
						  	<a target="_blank" href="course_preview.action?course.id=<s:property value="id"/>" class="textbg4">预览</a>  
						    <a style="cursor:pointer;"  onClick="sh(<s:property value="id"/>, 0);" class="textbg4">通 过</a> 
						  	<a href="course_user_detail_list.action?course.id=<s:property value="id"/>" class="textbg4">学员</a> 
						    <a style="cursor:pointer;"  onClick="sh(<s:property value="id"/>, 5);"  class="textbg6">不通过</a>					  	</s:if>
					  	<s:else> 
					  	<a target="_blank" href="course_preview.action?course.id=<s:property value="id"/>" class="textbg4">预览</a>  
					  	<a href="course_user_detail_list.action?course.id=<s:property value="id"/>" class="textbg4">学员</a> 
					  	</s:else>
					  </td>
					</tr>
				</s:iterator> <tbody>
		  </table>
			</s:else> </td></tr></table>
			<!--<form action="myCoursemake_list.action" name="myclist">
				<s:hidden name="pN" id = "pageNow"></s:hidden>
				<s:hidden name="pS"></s:hidden>
				
			</form>--> 
							<form action="course_sh.action" name="course_sh" method="post">
								<s:hidden name="course.id" id="course.id"></s:hidden>
								<s:hidden name="status" id="status"></s:hidden>
								<s:hidden name="Return" id="Return" value="course_alter_sh_list"></s:hidden>
							</form> 
			<script>
								function sh(id,status){
								    document.getElementById("course.id").value=id;
								    document.getElementById("status").value=status; 
								 	if(status==5 && window.confirm("确定修改审核不通过？")){
								 		document.forms.course_sh.submit();
								 	} 
								 	if(status==0 && window.confirm("确定修改审核通过？")){
								 		document.forms.course_sh.submit();
								 	}
								}  
				function page(i) {
					document.getElementById("pageNow").value=i;
					caform.submit();
				}
			</script>
			<wysLib:page></wysLib:page>
			
			
		</div>

		<!-- 内容 -->
	
	</body>
</HTML>
