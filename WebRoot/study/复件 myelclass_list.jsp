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
		<TITLE>五矿发展员工职业发展系统--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD> 
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="培训班列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">在学培训班</span>
			</li>-->
		</ul>
		<s:if test="myClasses.size==0">
			<div style="width: 100%; text-align: center; margin-top: 30px;">
				<span style="color:red;">您当前没有需要参加的培训班</span>
			</div>
		</s:if>
		<s:else>
			<table width="100%" align="center" cellpadding="2" cellspacing="1"
				>
				<tr>
					<th width="200" align="center" >
						培训班名称					</th>
					<th width="130" align="center" >
						创建者		 		    </th>
					<th width="120" align="center" >
						创建时间					</th>
						<th width="120" align="center" >
						开始时间					</th>
						<th width="120" align="center" >
						结束时间					</th>
					<th width="150" align="center" >
						必修课/完成数					</th>
					<th width="200" align="center" >
						选修课/要求/已获					</th>
					<th width="120" align="center" >
						加入时间					</th>
				  <th width="120" align="center" >&nbsp;				  </th>
				</tr>
				<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
				<s:iterator value="myClasses">
					<tr>
						<td width="200" align="center" > 
							<s:if test="elClass.isApplication == 1">
								<SPAN style="color:red">【申请】</SPAN>
							</s:if><s:else>
								<SPAN style="color:gray">【分配】</SPAN>
							</s:else>
							<s:property value="elClass.name" />
					  </td>
						<td width="130" align="center" >
							<s:property value="elClass.creater.realname" />
					  </td>
						<td width="120" align="center" >
					  <s:date name="elClass.createtime" format="yyyy-MM-dd" />					  </td>
						<td width="120" align="center" >
							<s:date name="elClass.starttime" format="yyyy-MM-dd HH:mm:ss" />
					  </td>
						<td width="120" align="center" >
							<s:date name="elClass.finishtime" format="yyyy-MM-dd HH:mm:ss" />
					  </td>
						<td width="150" align="center" >
							<s:property value="elClass.bxCount" />
							/
					  <s:property value="bxCount" />					  </td>
						<td width="200" align="center" >
							<s:property value="elClass.xxCredit" />
							/
							<s:property value="elClass.optionalcredit" />
							/
					  <s:property value="xxCredit" />					  </td>
						<td width="120" align="center" >
					  <s:date name="begintime" format="yyyy-MM-dd" />					  </td>
						<td width="120" align="center" >
							<!--<a
							href="myelclass_view.action?elclass.id=<s:property value="elClass.id" />">详情</a>
						-->
							<a href="myelclass_view.action?elclass.id=<s:property value="elClass.id" />" onclick="return iselClass('<s:property value="elClass.status" />');" class="textbg5">学习详情</a>
							<!--<a
								href="myclass_course_result.action?elclass.id=<s:property value="elClass.id" />">考试详情</a>-->						</td>
					</tr>
				</s:iterator></tbody>
		  </table>  
					<form action="myelclass_list.action" method="post" name="myelclass_list">
						<s:hidden name="pN" id="pageNow"></s:hidden>
						<s:hidden name="pS"></s:hidden>  
					</form>
					<script type="text/javascript">
						function page(i){
							document.getElementById("pageNow").value=i; 
							myelclass_list.submit();
						}
					</script>
		  <wysLib:page></wysLib:page>
		</s:else>
	</body>
</HTML>
