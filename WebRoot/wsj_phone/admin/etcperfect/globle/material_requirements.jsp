<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.duman.entities.Department"%>
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
			
			function page(i){
				document.getElementById("pageNow").value=i;
				log.submit();
			}
			
			function do_search(){
				document.getElementById("pageNow").value=0;
				log.submit();
			}
		</script>
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="物料需求一览表" />
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
			<form action="material_requirements.action" name="log" method="post">
			<s:hidden name="pN" id="pageNow" />
			<s:hidden name="pS" /> 
			<table width="98%" align="center" cellspacing="1" cellpadding="1">
				<tr>
					<td valign="top">
						<table width="98%" align="center" cellspacing="1" cellpadding="1">
							<tr>
								<th height="30" align="center">
									物料名称
								</th>
								<th height="30" align="center">
									需求数量
								</th>
								<th height="30" align="center">
									已领数量
								</th>
								<th height="30" align="center">
									已退数量
								</th>
								<th height="30" align="center">
									报废数量
								</th>
								<th height="30" align="center">
									库存数量
								</th>
								<th height="30" align="center">
									差额
								</th>
								<th height="30" align="center">
									市场价格
								</th>
								<th height="30" align="center">
									单品总价
								</th>
								<th height="30" align="center">
									生产任务相关
								</th>
								<th height="30" align="center">
									委外加工相关
								</th>
							</tr>
							<tbody onMouseOut="changeback()" onMouseOver="changeto()">
								<s:iterator value="material_requirements">
									<tr>
										<td align='center'>
											<s:property value='name'/>
										</td>
										<td align='center'>
											<s:property value='need_number'/>
										</td>
										<td align='center'>
											<s:property value='ylsl'/>
										</td>
										<td align='center'>
											<s:property value='ytsl'/>
										</td>
										<td align='center'>
											<s:property value='bfsl'/>
										</td>
										<td align='center'>
											(<s:property value='number'/>)
											<a href="viewContactTags.action?tablename=WPJGB&id=<s:property value='id'/>">详情</a>
										</td>
										<td align='center'>
											<s:property value='ce'/>
										</td>
										<td align='center'>
											<s:property value='price'/>
										</td>
										<td align='center'>
											<s:property value='dpzj'/>
										</td>
										<td align='center'>
											(<s:property value='csrw_number'/>)
											<a href="myContactTags1_.action?tablename=SCRWS">详情</a>
										</td>
										<td align='center'>
											(<s:property value='wwjg_number'/>)
											<a href="myContactTags1_.action?tablename=SCLL">详情</a>
										</td>
									</tr>
								</s:iterator>
							</tbody>
							<tr>
								<td align='center'>合计</td>
								<td style='padding-left:50px' colspan=8><s:property value='zongjia'/></td>
								<td ></td>
								<td ></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			</form>
			<center><wysLib:page></wysLib:page></center>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
