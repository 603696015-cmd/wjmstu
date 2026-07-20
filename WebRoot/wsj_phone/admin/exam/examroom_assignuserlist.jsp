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
	background-color: expression((       this .       sectionRowIndex %       2 ==       0)
		?   
		   "#ffffff" :       "#f4f4f4" )
}
</style>
<script type="text/javascript">
	function sh0( ){
					    if(window.confirm("确定创建完成？"))
					    	document.location='examroom_valid.action?examRoom.id=${examRoom.id }'
					}
</script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="考生增减" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">练习人员列表 </span>
			</li>
			<li class="sep">
			</li>
			<!--<s:if test="optype!='valid'">
				<li class="sep">
				</li>
				<li>
					<a style="cursor: hand"
						onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
						onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
						href="examroomwithoutcourse_list.action">考试管理</a>
				</li>
			</s:if>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<div style="text-align: center;">
				<h3>
					考场【
					<s:property value="examRoom.title" />
					】中试卷【
					<s:property value="examPaper.title" />
					】的考生列表
				</h3>
				<s:form action="examroom_assignuser_deletes"
					onsubmit="return window.confirm('确定删除这些考生?')" method="post">
					<table width="700px" align="center" cellpadding="1" cellspacing="1">
						<tr>
							<%--  
							<td height="30" style="padding-left:8px;color:blue;" align="left">
							</td>
							 --%>
							<td height="30" align="center">
								姓名
							</td>
							<td height="30" align="center">
								账号
							</td>
							<td height="30" align="center">
								部门
							</td>
							<td height="30" align="center">
								参加方式
							</td>
							<%-- 
							<td height="30" align="center" >&nbsp;
						  	</td>
						  	 --%>
						</tr>
						<s:iterator value="elusers">
							<tr>
								<%-- 
								<td height="30" style="padding-left:8px;color:blue;" align="left">
									<input type="checkbox" value="<s:property value="id" />" name="elusers.id"/>
								</td>
								 --%>
								<td height="30" align="center">
									<s:property value="realname" />
								</td>
								<td height="30" align="center">
									<s:property value="username" />
								</td>
								<td height="30" align="center">
									<s:property value="department.name" />
								</td>
								<td height="30" align="center">
									<s:property value="joinway_" />
								</td>
								<%-- 
								<td height="30" align="center" >
									<a onClick="return confirm('确定删除？')"
										href="examroom_unassignwc.action?examPaper.id=<s:property value="examPaper.id"/>&examRoom.id=<s:property value="examRoom.id"/>&elUser.id=<s:property value="id"/>">删除</a>
								</td>
								 --%>
							</tr>
						</s:iterator>
					</table>
					<script type="text/javascript">
						function select_All(){
							var cks= document.getElementsByName("elusers.id");
							for(var i = 0 ; i < cks.length; i++){
								cks[i].checked= true;
							}
						}
						function select_Fan(){
							var cks= document.getElementsByName("elusers.id");
							for(var i = 0 ; i < cks.length; i++){
								cks[i].checked= !cks[i].checked;
							}
						}
						function select_Bux(){
							var cks= document.getElementsByName("elusers.id");
							for(var i = 0 ; i < cks.length; i++){
								cks[i].checked= false;
							}
						}
					</script>
					<%-- 
					<a href="javascript:select_All()" />全选</a>
					<a href="javascript:select_Fan()" />反选</a>
					<a href="javascript:select_Bux()" />全不选</a>
					<s:hidden name="examRoom.id"></s:hidden>
					<s:hidden name="examPaper.id"></s:hidden>
					<input type="submit" value="刪除"> 
						<s:hidden name="optype"></s:hidden>
						<s:hidden name="course.id"></s:hidden> 
					 --%>
					<!--<s:if test="examRoom.uvalid == 0">
						 <input style="height:35px;" class="textbg6" type="button" name="button2" onClick="sh_p();" id="button2"
							value="完成复核" />
					</s:if>  -->
				</s:form>
				<wysLib:page></wysLib:page>
				<script type="text/javascript">
						function sh_p(){ 
							if(window.confirm("确定让它通过审核？"))
								document.location.href="examroom_valid.action?examRoom.id=<s:property value="examRoom.id"/>"
						}  
						function page(i){
							document.getElementById("pageNow").value=i;
							erForm.submit();
						}
					</script>
				<s:form action="examroom_assignuserlist" method="post" name="erForm">
					<s:hidden name="pN" id="pageNow" />
					<s:hidden name="pS" />
					<s:hidden name="examRoom.id" />
					<s:hidden name="examPaper.id" />
				</s:form>
			</div>
			<div style="width: 100%; text-align: center">
				<input class="textbg" type="button" style="border: none;color: red"
					onclick="document.location='examroom_assignSearchlist.action?sub_department=1&examPaper.id=<s:property value="examPaper.id"/>&examRoom.id=<s:property value="examRoom.id"/>&course.id=<s:property value="course.id"/>'"
					value="分配人员" />
				<input class="textbg" style="border: none;" value="试卷列表"
					title="返回考场试卷列表" type="button"
					onclick="document.location.href='examroom_assignwcInit.action?examRoom.id=<s:property value="examRoom.id"/>&course.id=<s:property value="course.id"/>'">
				<input class="textbg" style="border: none;" type="button"
					value="返回考场详情"
					onclick="document.location='erwithout_view.action?examRoom.id=${examRoom.id }'" />
				<s:if test="examRoom.valid==0">
					<input class="textbg" style="border: none; color: red" title="确认添加人员无误后可创建完成，继续审核！" 
						type="button" value="创建完成" onClick="sh0()" />
				</s:if>
			</div>
		</div>
		<br>
		<%-- 
		<div align=center><a href="examroom_assignwcSearchInit.action?sub_department=1&examPaper.id=<s:property value="examPaper.id"/>&examRoom.id=<s:property value="examRoom.id"/>" class=textbg>考场人员添加</a></div>
		 --%>
		<!-- 内容 -->
	
	</body>
</HTML>
