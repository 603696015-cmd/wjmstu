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
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
</style>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript">
				function showtree(flag){
					var tree = document.getElementById("tree_list_td");
					var img = document.getElementById("showimg");
					if(flag){
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(false);" class="textbg5">隐藏新闻类别</a>';
					}
					else{
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(true);" class="textbg5">显示新闻类别</a>';
					}
				}
		</script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/newversion/jquery.toolsbar.js"></script>
		<script type="text/javascript">
	
		</script>
	</HEAD>
	<body>
		<form action="isHalfMonths.action" method="post" name="nmList">
			<s:hidden name="isOk" value="1" />
			<s:hidden name="news.id" id="newsId" />
			<s:hidden name="newsOp" id="newsOp" />
		</form>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td valign="middle" class="tablequiz">
					<ul class="nav">
						<li>
							<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
								<wysLib:Navigation ivalue="学员列表页" />
							</div>
						</li>
					</ul>
				</td>
				<td width="120" valign="middle" class="tablequiz">
					<A id=quit href="javascript:window.parent.full_screen(false);"
						class="textbg6" style="display: none">退出全屏</A>
				</td>
			</tr>
		</table>


		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<table width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<td valign="top">

						<s:form action="isHalfMonths.action" method="post"
							name="newsQuery" theme="simple">
							<s:hidden name="pN" id="pageNow" />
							<s:hidden name="pS" />
                            <table width="100%" border="0" cellspacing="1" cellpadding="0" bgcolor="#D1E4F5">
  <tr>
<td bgcolor="#F8FCFE"><div
								style="height: 40px; line-height: 40px; padding-left: 15px; margin-top:1px;">
								姓名&nbsp;
								<input type="text" name="trainStatus.realname"
									value="<s:property value="trainStatus.realname"/>">
								身份证号&nbsp;
								<input type="text" name="trainStatus.shenfenzhenghao"
									value="<s:property value="trainStatus.shenfenzhenghao"/>">
								&nbsp;
								<input type="button" onClick="newsSubmit();" class="textbg4"
									value="搜索" />

								
							</div></td>
  </tr>
</table>

							
					  </s:form>
						<s:if test="isHalfMonthsList.size==0">
							<span style="margin-top: 10px; padding-left: 15px;">没有符合条件的学员</span>
						</s:if>
						<s:else>
							<table width="100%" align="center" cellpadding="1"
								cellspacing="1" bgcolor="#EBEBEB">
								<tr>
									<td colspan=20>



										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td width="95">
													
												</td>
												<td>
													<div id="Div_ToolsBar">
														<div align="left"></div>
													</div>
												</td>
											</tr>
										</table>


									</td>
								</tr>
								<tr>
									<th width="100" height="50" align="center">
										姓名
									</th>
									<th width="100" height="50" align="center">
										性别
									</th>
									<th  height="50" align="center">
										身份证号
									</th>
									<th  height="50" align="center">
										手机号
									</th>
									<th  height="50" align="center">
										证书开始日期
									</th>
									<th  height="50" align="center">
										证书结束日期
									</th>

									<!-- <th width="50" ></th> -->
								</tr>
								<tbody onMouseOut="changeback()" onMouseOver="changeto()">
									<s:iterator value="isHalfMonthsList" status="status">
										<tr>
											<td height="30" style="padding-left: 8px; color: blue;"
												align="left">
												<s:property value="realname" />
											</td>
											<td height="30" align="center">
												<s:property value="sex" />
											</td>
											<td height="30" align="center">
											<s:property value="shenfenzhenghao" />
											</td>
											<td height="30" align="center">
												<s:property value="mobliephone" />
											</td>
											<td height="30" align="center">
												<s:date name="certificatestart"
														format="yyyy-MM-dd " />
											</td>
											<td height="30" align="center">
												<s:date name="certificateend"
														format="yyyy-MM-dd " />
											</td>
										</tr>
									</s:iterator>
								</tbody>
							</table>
						</s:else>
					</td>
				</tr>
			</table>
			<%-- 
			<form action="newsManage_list.action" method="post" name="nlist">
				<s:hidden name="ntype.id" />
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
			</form>
			 --%>
			<script type="text/javascript">
			function page(i){
				document.getElementById("pageNow").value=i;
				newsQuery.submit();
			}
			function newsSubmit(){
				document.getElementById("pageNow").value=0;
				newsQuery.submit();
			}
		</script>
			<wysLib:page></wysLib:page>
		</div>
	</BODY>
</HTML>
