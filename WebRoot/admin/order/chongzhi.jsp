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
		<TITLE>充值金额</TITLE>
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
		function add(){
			var val = document.getElementById("balance").value;
			if(val!=undefined){
				if(val>=0){
					assignSearch_assignment.submit();
				}else{
					alert("充值数必须大于0");
					return ;
				}
			}
		}
		</script>
	</HEAD>
	<body>
		<style type="text/css">
td {
	font-size: 13px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
</style>

		<ul class="nav">
			<li>

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
			<table width="100%">
				<tr>

					<td valign="top">
						<s:form action="doChongzhi.action" method="post" name="assignSearch_assignment"
							theme="simple">
							<s:hidden name="orderid"></s:hidden>
							<table width="100%" align="center" cellspacing="1" bgcolor="#D1E4F5">
							
								<tr>

									<td width="150" height="30" bgcolor="#F8FCFE" style="padding-left:10px;">
										请输入充值金额
									</td>
									<td bgcolor="#F8FCFE" style="padding-left:5px;">
										<s:textfield name="balance" id="balance" />
									</td>
								</tr>
								<tr>
									<td height="30" colspan="2" bgcolor="#F8FCFE">
										<a onClick="javascript:add(); " class=textbg5>充值</a>
									</td>
								</tr>
							</table>
						</s:form>
					</td>
				</tr>

			</table>
		</div>
	</BODY>
</HTML>
