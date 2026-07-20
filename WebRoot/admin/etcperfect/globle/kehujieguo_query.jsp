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
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
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
	background-color: expression((   this .   sectionRowIndex %   2 ==   0)
		? 
		 "#ffffff" :   "#f4f4f4" )
}
</style>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
		</script>
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="客户档案查询" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<table width="98%" align="center" cellspacing="1" cellpadding="1">
				<caption>
					销售漏斗
				</caption>
				<tr>
					<th height="30" align="center">
					</th>
					<th height="30" align="center">
						数量
					</th>
					<th height="30" align="center">
						比例
					</th>
					<th height="30" align="center">
						预期金额
					</th>
					<th height="30" align="center">
						比例
					</th>
					<th height="30" align="center">
						实际金额
					</th>
					<th height="30" align="center">
						比例
					</th>
				</tr>
				<tbody onMouseOut="changeback()" onMouseOver="changeto()">
					<s:iterator value="KHDA_KHJDList">
						<tr>
							<td align='center'><s:property value="KHDA_KHJD"/></td>
							<td align='center'><s:property value="number"/></td>
							<td align='center'>
								<table width='100px' border='0' cellspacing='1' >
									<tr>
										<td>
											<div  style='border: 0px dotted #FF6633;width:100px'><img height='14' src='images/jd.gif' width='<s:property value="bili1"/>%' /></div>
										</td>
										<td>
											<s:property value="bili1"/>%
										</td>
									</tr>
								</table>
							</td>
							<s:if test ="KHDA_KHJD == '登记客户'">
								<td align='center' colspan=4></td>
							</s:if>
							<s:else>
								<td align='center'><s:property value="KHDA_YQJE"/></td>
								<td align='center'>
									<table width='100px' border='0' cellspacing='1' >
										<tr>
											<td>
												<div  style='border: 0px dotted #FF6633;width:100px'><img height='14' src='images/jd.gif' width='<s:property value="bili2"/>%' /></div>
											</td>
											<td>
												<s:property value="bili2"/>%
											</td>
										</tr>
									</table>
								</td>
								<td align='center'><s:property value="KHDA_SJJE"/></td>
								<td align='center'>
									<table width='100px' border='0' cellspacing='1' >
										<tr>
											<td>
												<div  style='border: 0px dotted #FF6633;width:100px'><img height='14' src='images/jd.gif' width='<s:property value="bili3"/>%' /></div>
											</td>
											<td>
												<s:property value="bili3"/>%
											</td>
										</tr>
									</table>
								</td>
							</s:else>
						</tr>
					</s:iterator>
				</tbody>
				<tr><td align='center' colspan='7'><a href="finalsearchContactTags.action?tablename=<s:property value='tablename'/>&final_=1" class='textbg6'>查询详情</a></td></tr>
			</table>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
