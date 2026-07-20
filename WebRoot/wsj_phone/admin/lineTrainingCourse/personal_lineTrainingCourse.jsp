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
		<TITLE></TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
			function do_submit(){
				assignSearch_assignment.submit();
			}
			
			function page(i){ 
		 		document.getElementById("pageNow").value=i;
		 		assignSearch_assignment.submit();
		 	}
		</script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="线下培训班列表" /></div>
			</li>
		</ul>
		<!-- 内容 --> 
		<table width="100%">
			<s:form action="" method="post" name="assignSearch_assignment" theme="simple">	
			<table width="100%"> 
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<tr>
				
				   <td width="230">&nbsp;培训班名称：<input  name="lineTrainingCourse.name" /></td>
				   <td width="18%">&nbsp;培训类别
						<SELECT  style="WIDTH: 110px" name="lineTrainingCourse.train_type_id" 
					      onchange="this.value=this.options[this.selectedIndex].value;">
					        <OPTION value="-1" selected>选择培训类别</OPTION>
					        <s:iterator value="trainTypes">
					        	<option value="<s:property value="id"/>">
									<s:property value="name"/> 
								</option>
					        </s:iterator>
					    </SELECT>			           
		          </td>
			       <td width="49%">
			       		发布时间
									从
			       		  <INPUT   class=textbox id="starttime" maxLength=50 
	       								 size=30 name="starttime" onClick="setday(this)">
	       						 	到<INPUT  class=textbox id="endtime" maxLength=50 
	       								 size=30 name="endtime" onClick="setday(this)">
		          </td>
				   <td width="7%" colspan="2">
		           	 	<input  type="button" onClick="do_submit();" value="搜索" class="textbg5">
	              </td>
				</tr>
			</table>
			</s:form>
		<table width="100%">
			<tr>
			
			
			<td valign="top" align="left"> 
			<s:if test="lineTrainingCourseList.size==0"><table height="80" align="center" width="100%">
			  <tr align="center"><td align="center" > 没有线下培训班</td></tr></table></s:if>
			<s:else>
			<table width="100%" cellpadding="1" cellspacing="1" bgcolor="#EBEBEB">
				<tr>
					<th width="8%" height="30" align="center" >培训名称</th>
					<th width="8%" align="center" >发布时间</th>
					<th width="8%" align="center" >地点</th>
					<th width="8%" align="center" >培训开始时间</th>
					<th width="8%" align="center" >培训结束时间</th>
					<th width="8%" height="30" align="center" >联系人</th>
					<th width="8%" height="30" align="center" >收费价格</th>
					<th width="5%" height="30" align="center" >培训类别</th> 
					<th width="10%" height="30" align="center" >是否获证</th> 
					<th width="10%" height="30" align="center" >获得学分</th> 
					<th width="10%" height="30" align="center" >审核状态</th> 	
					<th width="5%" height="30" align="center" >详情</th> 
				</tr>
				<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
				<s:iterator value="lineTrainingCourseList">
					<tr>
						<td height="30" align="center" bgcolor="#EBEBEB" style="color:#CC0099;">
							<s:property value="name" />
					  </td>
						<td height="30" align="center" bgcolor="#EBEBEB" style="color:#CC0099;">
							<s:date name="createtime" format="yyyy-MM-dd hh:mm:ss"/>
					  </td>
						<td height="30" align="center" bgcolor="#EBEBEB" >
							<s:property value="place" />
					  </td>
						<td align="center" bgcolor="#EBEBEB" ><s:date name="train_begintime" format="yyyy-MM-dd hh:mm:ss"/></td>
						<td align="center" bgcolor="#EBEBEB" ><s:date name="train_endtime" format="yyyy-MM-dd hh:mm:ss"/></td>
						<td align="center" bgcolor="#EBEBEB" ><s:property value="contact_name" /></td>
						<td  height="30" align="center" bgcolor="#EBEBEB" style="color:#CC0099;">
							<s:property value="fee_price" />					  </td>
						<td height="30" align="center" bgcolor="#EBEBEB" >
							<s:property value="trainType.name" />		
					  </td>
						<td height="30" align="center" bgcolor="#EBEBEB" >
							<s:if test="assign.is_get_certificate == 0">
								未获证
							</s:if>
							<s:else>
								已获证
							</s:else>		
					  </td>
						<td height="30" align="center" bgcolor="#EBEBEB" >
							<s:property value="credit" />		
					  </td>
						<td  height="30" align="center" bgcolor="#EBEBEB" >
							<s:if test="assign.approval_status == 1">
								已审核
							</s:if>
							<s:else>
								未审核
							</s:else>
					  </td> 
						<td height="30" align="center" bgcolor="#EBEBEB" >
							<s:if test="assign.approval_status == 1">
								<a href="show_personal_lineTrainingCourse.action?id=<s:property value='id'/>" class="textbg4">查看</a>
							</s:if>
							<s:else>
							</s:else>
					  </td>
				</s:iterator></tbody> 
		  </table>
		  
		  <wysLib:page></wysLib:page>
		  </s:else></td></tr></table> 
		<!-- 内容 -->
	
	</body>
</HTML>
				