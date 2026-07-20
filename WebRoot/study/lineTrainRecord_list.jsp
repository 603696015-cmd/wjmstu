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
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<TITLE>线下培训记录管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
			<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<!--<li>
				<!-- span style="font-weight: bold;">我的课程</span>
					<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="lineTrainRecord_addInt.action">添加线下培训记录</a>
					<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="lineTrainRecord_list.action">查看与修改</a></li>-->
			</ul>
		    <!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<s:if test="listLineTrainrecord.size==0">当前还没有线下培训记录</s:if>
			<s:else>
				<table width="100%" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<caption>
						线下培训记录
					</caption>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
					    选择
						</td>
						<td height="30" align="center" >
							培训名称
						</td>
						<td height="30" align="center" >
							提交时间
						</td>
						<td height="30" align="center" >
							证书名称
						</td>
						<td height="30" align="center" >
							培训开始时间
						</td>
						<td height="30" align="center" >
							培训结束时间
						</td>
						<td height="30" align="center" >
							学分
						</td>
						<td height="30" align="center" >
							状态
						</td>
						<td height="30" align="center" >
							提交申请
						</td>
						<td height="30" align="center" >
							修改
						</td>
						<td height="30" align="center" >
							查看
						</td>
					</tr>
					<s:if test="listLineTrainrecord.size==0">
						<TR>
							<TD align="center" colspan="4">
								当前还没有线下培训记录
							</TD>
						</TR>
					</s:if>
					<s:else>
						<s:iterator value="listLineTrainrecord">
							<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
                                <s:if test="state==1||state==3">
									<input type="checkbox" value="<s:property value="trainid"/>" name="trainid">
								</s:if> 
							  </td>
								<td height="30" align="center" >
									<s:property value="trainname" />
								</td>
								<td height="30" align="center" >
									<s:date name="submittime" format="yyyy-MM-dd" />
								</td>
								<td height="30" align="center" >
									<s:property value="certificate" />
								</td>
								<td height="30" align="center" >
									<s:date name="trainstarttime" format="yyyy-MM-dd" />
								</td>
								<td height="30" align="center" >
									<s:date name="trainendtime" format="yyyy-MM-dd" />
								</td>
								<td height="30" align="center" >
									<s:property value="credit" />
								</td>
								<td height="30" align="center" >
									<s:property value="stateName" />
								</td>
								<td height="30" align="center" >
									<s:if test="state==1||state==3">
									<a href='updateState.action?state=2&&ids=<s:property value="trainid"/>'>申请</a>
									</s:if>
								</td>
								<td height="30" align="center" >
									<s:if test="state==1||state==3">
									<a href='lineTrainRecord_addInt.action?linetrainrecord.trainid=<s:property value="trainid"/>'>修改</a>
									</s:if>
								</td>
								<td height="30" align="center" >
									<a href="lineTrainRecordLook.action?linetrainrecord.trainid=<s:property value="trainid" />" class="textbg4">查看</a>
								</td>
							</tr>
						</s:iterator>
					</s:else>
			  </table>
				<table width="100%" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr><td align="right">
					总学分：<s:property value="allcredit" />
					</td></tr>

			  </table>
				<input type="submit" value="删除" style="margin-top:20px;margin-left:40px;" onClick="deleteFunction(); " class="textbg4";>
			</s:else>
			<a href="lineTrainRecord_addInt.action" class="textbg">&nbsp;&nbsp;添加记录</a> <%-- 　 <a href="lineTrainRecord_list.action" class="textbg">查看与修改</a> --%>
		</div>
		<div style="margin-top: 0px; text-align: center;">
			
		</div>
		
		<!-- 内容 -->
	</BODY>
	<script type="text/javascript">
	       function deleteFunction(){
		       var checkObj = document.getElementsByName("trainid");
			   var billIDs = "";
			   for (i = 0; i < checkObj.length; i++) {
					if (checkObj[i].checked) {
					    if(billIDs!="")billIDs+=",";
						billIDs += checkObj[i].value;
					}
				}
			   if(billIDs==""){
				  alert("请选择要删除的的记录！");
				  return ;
			   }
			   if(confirm('确定删除？')){
			      location = "lineTrainRecord_delete.action?ids="+billIDs;
			   }
		   }
	</script>
</HTML>
	