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
		<TITLE>五矿发展员工职业发展系统--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((     this .     sectionRowIndex %     2 ==     0)
		?  
		  "#ffffff" :     "#f4f4f4" )
}
</style>
	</HEAD>
	<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="交流文章列表" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">审核文章</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<form action="forum_list_byblockid.action" name="flbform"
			method="post">
			<div style="width: 100%; text-align: center; margin-top: 4px;">
				<s:hidden name="pN" id="pageNow"></s:hidden>
				<s:hidden name="pS"></s:hidden>
			</div>
			<table width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<td width="200px;" valign="top" id="tree_list_td">
						<s:if test="fbtypes.size==0">
							<div style="width: 100%; text-align: center; margin-top: 4px;">
								当前没有版面类别
							</div>
						</s:if>
						<s:else>
							<s:set name="fbtsize" value="fbtypes.size"></s:set>
							<table width="100%" align="center" cellpadding="1"
								cellspacing="1">
								<tr>
									<td height="30" align="left"
										style="padding-left: 8px; color: blue;">
										<!--<a
											href="forum_list_byblockid.action?forum.title=&pN=0&pS=10&fblock.id=0">全部</a>
									-->
										<a
											href="forum_shlist.action?forum.title=&pN=0&pS=10&fblock.id=0">全部</a>
									</td>
								</tr>
								<s:iterator value="fbtypes" status="fbtst">
									<tr bgcolor="red">
										<th height="30" align="left"
											style="padding-left: 8px; color: blue;">
											<i><b><s:property value="name" /> </b> </i>
										</th>
									</tr>
									<s:set name="fblocksize" value="fblocks.size"></s:set>
									<s:iterator value="fblocks" status="fbs" id="fbsid">
										<tr>
											<td height="30" align="left"
												style="padding-left: 8px; color: blue;">
												<a
													href="forum_shlist.action?fblock.id=<s:property value="id"/>&pN=0&pS=10&str=byfblockid"><s:property
														value="title" /> </a>
											</td>
										</tr>
									</s:iterator>
								</s:iterator>
							</table>
						</s:else>
					</td>
					<td valign="top" id="tree_list_td" style="padding: 0px">
						<table width="99%" style="margin: 0px" align="left" cellpadding="1" cellspacing="1">
							<tr>
								<th>
								</th>
								<th>
									名称
								</th>
								<th>
									发表人
								</th>
								<th>
									版块
								</th>
								<th>
									发布时间
								</th>
								<!--<th>
						修改时间
					</th>
					-->
								<th>
									回帖数
								</th>
								<th>
									浏览数
								</th>
								<th>
								</th>
								<th>
								</th>
							</tr>
							<s:iterator value="forums">
								<tr>
									<td height="30" align="left"
										style="padding-left: 8px; color: blue;">
										<input type="checkbox" name="forums.id"
											value="<s:property value="id"/>">
									</td>
									<td height="30" align="left"
										style="padding-left: 8px; color: blue;">
										<s:property value="title" />
									</td>
									<td height="30" align="left"
										style="padding-left: 8px; color: blue;">
										<s:property value="creater.realname" />
									</td>
									<td align="center">
										<s:property value="fblock.title" />
									</td>
									<td align="center">
										<s:date name="createtime" format="yyyy-MM-dd" />
									</td>
									<!--<td align="center" >
							<s:date name="modifytime" format="yyyy-MM-dd" />
						</td>
						-->
									<td align="center">
										<s:property value="receipttime" />
									</td>
									<td align="center">
										<s:property value="readtime" />
									</td>
									<td align="center">
										<a
											href="forum_deletebyuid.action?forum.id=<s:property value="id"/>"
											onclick="return confirm('确定删除？')" class="textbg4">删除</a>
									</td>
									<td align="center">
										<a
											href="forumView.action?forum.id=<s:property value="id"/>&pN=0&pS=10"
											target="_blank" class="textbg4"> 预览</a>
									</td>
								</tr>
							</s:iterator>
							<tr>
								<td colspan="9">
									<wysLib:page></wysLib:page>
									<input value="通过审核" style="width: 80px" type="button"
										class="textbg4" onClick="fshset()" />
								</td>
							</tr>
						</table>


					</td>
				</tr>
			</table>
		</form>
		<script type="text/javascript">
			function page(i){
				document.getElementById("pageNow").value=i;
				flbform.action="forum_shlist.action";
				flbform.submit();
			}
			function fshset( ){
				flbform.action="forum_sh.action";
				flbform.submit();
			}
			function fsearch( ){
				flbform.action="forum_shlist.action";
				document.getElementById("pageNow").value=0;
				flbform.submit();
			}
		</script>
	</body>
</HTML>
