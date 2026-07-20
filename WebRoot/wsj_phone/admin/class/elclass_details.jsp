<%@ page language="java" pageEncoding="UTF-8"   %>
<%@page import="com.sopia.classman.entities.ElClType"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@page import="com.sopia.classman.entities.ElClass"%>

<%
	String cltypeName=""; 
	if(request.getAttribute("elclass") != null){
		cltypeName=((ElClass)request.getAttribute("elclass")).getCltype().getName()+"";
	}
	
	ElClType cltypeTree=(ElClType)request.getAttribute("cltypeTree"); 
	
%>

<HTML>
	<HEAD>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>中国食品安全培训网--管理端--</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
	<script type="text/javascript" src="js/cexampaper.js"></script> 
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<div class="dh3">
			<!--<div class="newpos"></div>
			<div class="newpos2"> 
				<span style="font-weight: bold;">培训班详情</span>
			</div>-->
			<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="培训班详情页" /></div>
		</div>
		<s:form action="elclass_add" theme="simple" method="post" name="class_info" id="class_info">
			<table width="95%" cellpadding="2" cellspacing="1" >
				<tr>
					<td width="160" height="30" align="center" bgcolor="#E6F9F9">
						培训班名称：					</td>
					<td height="30" >
						<label> 
							<s:property value="elclass.name"/>
						</label>
					</td>
				</tr>

				<tr>
					<td height="30" align="center" bgcolor="#E6F9F9">
						培训班介绍：					</td>
					<td height="30" >
						<label>
							<s:property value="elclass.description"/> 
						</label>
					</td>
				</tr> 
				<tr>
					<td height="30" align="center" bgcolor="#E6F9F9">
						结业证书名称：					</td>
					<td height="30" >
						<label><s:property value="elclass.certificatename"/>  
						</label>
					</td>
				</tr>

				<tr>
					<td height="30" align="center" bgcolor="#E6F9F9">
						所属类别： 
					</td>
					<td height="30" >
						<label> <%=cltypeName %>
						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="center" bgcolor="#E6F9F9">
						开始时间：					</td>
					<td height="30" >
					<s:date name="elclass.starttime" format="yyyy-MM-dd HH:mm:ss" />
					 </td> 
				</tr>
				<tr>
					<td height="30" align="center" bgcolor="#E6F9F9">
						结束时间：					</td>
					<td height="30" >
						 <s:date name="elclass.finishtime" format="yyyy-MM-dd HH:mm:ss" /> 
					</td> 
				</tr> 
				<tr>
					<td height="30" align="center" bgcolor="#E6F9F9">
						结业条件：					</td>
					<td height="30" >
						<label>
							<s:property value="elclass.optionalcredit"/>分/
							(必修课全部通过，选修课最少获得的学分) 
						</label>
					</td>
				</tr> 
				<s:if test="elclass.id != null">
				<tr>
					<td height="30" align="center" bgcolor="#E6F9F9">
						培训班状态：					</td>
					<td height="30" >
						<input type="hidden" name="elclass.status" value="<s:property value="elclass.status"/>">
						<s:if test="elclass.status==3">
							<label>
								已创建
							</label>
						</s:if>
						<s:elseif test="elclass.status==2">
							<label>
								审核中
							</label>
						</s:elseif>
						<s:elseif test="elclass.status==4">
							<label>
								未通过
							</label>
						</s:elseif>
						<s:elseif test="elclass.status==1">
							<label>
								开通
							</label>
						</s:elseif>
						<s:elseif test="elclass.status==0">
							<label>
								关闭
							</label>
						</s:elseif>
						
					</td>
				</tr>
				</s:if>
				<s:else>
						<input type="hidden" name="elclass.status" value="3">
				<tr>
					<td height="50" align="center" bgcolor="#E6F9F9">
						&nbsp;
						<s:hidden name="elclassId"></s:hidden>
				  </td>
					<td height="30" >
						<input style="height:35px;" class="textbg6" name="submit" type="submit" value="确认添加" />
					</td>
				</tr> 
				</s:else>   
				<tr>
					<td colspan="2">
						<iframe id="bixiuFrame" src="elclass_details_bx.action?elclassId=${elclass.id}" width=100% 
							marginwidth="0" marginheight="0" frameborder=0 onload="this.height=bixiuFrame.document.body.scrollHeight + 20"></iframe>
					</td>
				</tr>
				<!-- <tr>
					<td colspan="2">
						<iframe id="zhuxiuFrame" src="elclass_course_zx.action?elclassId=${elclass.id}" width=100% height=280 
								marginwidth="0" marginheight="0" frameborder=0 onload="this.height=zhuxiuFrame.document.body.scrollHeight + 20"></iframe>
					</td>
				</tr> -->
				<tr>
					<td colspan="2">
						<iframe id="xuanxiuFrame" src="elclass_details_xx.action?elclassId=${elclass.id}" width=100% height=280 
								marginwidth="0" marginheight="0" frameborder=0  onload="this.height=xuanxiuFrame.document.body.scrollHeight + 20"></iframe>
					</td>
				</tr>
		  </table>
			
						
		</s:form>
	
	</body>
</HTML>
