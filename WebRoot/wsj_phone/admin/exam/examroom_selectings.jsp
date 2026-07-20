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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="考生列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">考场选拨人员列表 </span>
			</li>-->
		</ul>
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
				<form action="examroom_assignuser_deletes.action" onSubmit="return window.confirm('确定删除这些考生?')" method="post" > 
					<table width="700px" align="center" cellpadding="1" cellspacing="1"
						>
						<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
							</td>
							<td height="30" align="center" >
								姓名
							</td>
							<td height="30" align="center" >
								账号
							</td>
							<td height="30" align="center" >
								部门
							</td>
							<td height="30" align="center" >
								状态 
							</td>
							<!-- <td height="30" align="center" >
								&nbsp;
							</td>-->
						</tr>
						<s:iterator value="elusers">
							<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
									<input type="checkbox" value="<s:property value="id" />" name="elusers.id"/>
								</td>
								<td height="30" align="center" >
									<s:property value="realname" />
								</td>
								<td height="30" align="center" >
									<s:property value="username" />
								</td>
								<td height="30" align="center" >
									<s:property value="department.name" /> 
								</td>
								<td height="30" align="center" >
								<s:if test="0 != examPaper.practimes && 0.0 !=examPaper.pracscore">
									<s:if test="practimes >= examPaper.practimes && pracscore >=examPaper.pracscore">
										<span style="color:red">通过</span>
									</s:if><s:else>
										未通过
									</s:else> 
								</s:if>	<s:else>
										无规则
									</s:else> 
								</td>
								<!-- <td height="30" align="center" >
									<a onclick="return confirm('确定删除？')"
										href="examroom_unassignwc.action?examPaper.id=<s:property value="examPaper.id"/>&examRoom.id=<s:property value="examRoom.id"/>&elUser.id=<s:property value="id"/>&Return=examroom_selectings">删除</a>
								 </td>-->
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
					<a href="javascript:select_All()" />全选</a>
					<a href="javascript:select_Fan()" />反选</a>
					<a href="javascript:select_Bux()" />全不选</a>
					<a  href="examroom_assignSelectings.action?sub_department=1&examPaper.id=<s:property value="examPaper.id"/>&examRoom.id=<s:property value="examRoom.id"/>" >添加本部门未参加练习人员</a>
					<s:hidden name="examPaper.id"></s:hidden>
					<s:hidden name="examRoom.id"></s:hidden>
					<input type="submit" value="刪除"> 
						<s:hidden name="optype"></s:hidden>
						<s:hidden name="course.id"></s:hidden> 
						<s:hidden name="Return" value="examroom_selectings"></s:hidden>
						<script type="text/javascript">
								function sh_p(){
									if(window.confirm("确定让它通过审核？"))
										document.location.href="examroom_valid.action?examRoom.id=<s:property value="examRoom.id"/>"
								}  
								function page(i){
										document.location.href="examroom_validview.action?examRoom.id=<s:property value="examRoom.id"/>&pS=<s:property value="pS"/>&pN="+i
								}
							</script> 
					<s:if test="examRoom.uvalid == 0">
						<input style="height:35px;" class="textbg6" type="button" name="button2" onClick="sh_p();" id="button2"
							value="完成复核" />
					</s:if> 
				</form>
			</div>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
