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
		<TITLE>我的订单信息</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
		
		</script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>

		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="订单详情列表" /></div>
			</li>
			<!--<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="news_addInit.action">新闻公告添加</a>

			</li>-->
		</ul>

		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
							<table width="80%" align="center" cellpadding="2"
								cellspacing="2" bgcolor="#EBEBEB">
								<tr>
									<th width="160" height="60" align="center" >
										
										<s:if test="classorcourse==1">培训班名称</s:if>
										<s:else>课程名称</s:else>									</th>
									<th width="80" height="60" align="center" >
										<s:if test="classorcourse==1">培训班价格</s:if>
										<s:else>课程名称</s:else>	（单价：元）									</th>
									<th width="80" height="60" align="center" >
										订购者									</th>
									<th width="80" height="60" align="center" >
										订购时间								</th>
									<th width="80" height="60" align="center" >
										总价									</th>
								</tr>
									<tr>
									<td height="30" style="padding-left:8px;color:blue;" align="left">
											
											<s:if test="classorcourse==1"><s:property value="classOrder.elClass.name" /></s:if>
										<s:else><s:property value="corder.course.name" /></s:else>		
											
									  </td>
										<td height="30" align="center" >										
											
												<s:if test="classorcourse==1"><s:property value="classOrder.price" /></s:if>
										<s:else><s:property value="corder.price" /></s:else>	
										</td>
										<td height="30" align="center" >
												<s:if test="classorcourse==1"><s:property value="classOrder.user.realname" /></s:if>
										<s:else><s:property value="corder.elUser.realname" /></s:else>	
											
										</td>
										<td height="30" align="center" >
										<s:if test="classorcourse==1"><s:property value="classOrder.odate" /></s:if>
										<s:else><s:property value="corder.odate" /></s:else>
											
										</td>
										<td height="30" align="center" >
										<s:if test="classorcourse==1"><s:property value="classOrder.zprice" /></s:if>
										<s:else><s:property value="corder.zprice" /></s:else>
											
										</td>
										
									</tr>
								<tr>
								<td >我的余额</td>
								<td ><s:property value="balance" /></td>
								<td colspan="3">
								<s:if test="classorcourse==1"><s:if test="balance>=classOrder.zprice">您可以使用余额支付</s:if><s:else>余额不足</s:else></s:if>
								<s:else><s:if test="balance>=corder.zprice">您可以使用余额支付</s:if><s:else>余额不足</s:else></s:else>
								
								
								</td>
								</tr>
								
								<tr>
								<td colspan="5"><s:if test="classorcourse==1" >
								 <s:if test="balance>=classOrder.zprice">
								 <a onclick="return window.confirm('确定支付${corder.zprice} 元？');" href="classcoursePay.action?order.id=<s:property value="classOrder.id" />" class="textbg">确认支付</a> 
								 </s:if><s:else>
								 <a onclick="javascript:#;" class=textbg>充值</a>
								 </s:else>
								 </s:if>
								<s:else><s:if test="balance>=corder.zprice"><a onclick="return window.confirm('确定支付${corder.zprice} 元？');" href="classcoursePay.action?order.id=<s:property value="corder.id" />" class="textbg">确认支付</a>
								</s:if>
								<s:else><a onclick="javascript:#;" class=textbg>充值</a></s:else></s:else>
				
								<a onclick="javascript:history.back(-1);" class=textbg>返回</a> </td>
								</tr>
						  </table>


		</div>
	</BODY>
</HTML>
										   