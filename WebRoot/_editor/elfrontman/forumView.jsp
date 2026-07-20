<%@ page language="java" pageEncoding="UTF-8"   %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="Description" content="财贸">
		<meta name="keywords" content="财贸">
		<title>论坛-<s:property value="forum.title" /></title>
		<LINK href="elfrontimages/home2.css" rel=stylesheet type="text/css">
		<style type="text/css">
<!--
.STYLE2 {
	color: #999999
}

body {
	background-color: #ffffff;
}

.STYLE5 {
	color: #ed7b0f;
	font-weight: bold;
}

.textbox {
	BORDER-RIGHT: #666666 1px solid;
	BORDER-TOP: #666666 1px solid;
	FONT-SIZE: 9pt;
	BORDER-LEFT: #666666 1px solid;
	COLOR: #666666;
	BORDER-BOTTOM: #666666 1px solid;
	FONT-FAMILY: verdana;
	HEIGHT: 18px;
	BACKGROUND-COLOR: #ffffff
}

.texwhite {
	color: white;
}

.txtwhite {
	color: white;
}

.lh22 {
	line-height: 22px;
}

img {
	border: none;
}

.STYLE8 {
	color: #CC0099
}
-->
</style>
		<SCRIPT type="text/javascript">
function changeTreeDisplay(obj1){
	var obj = document.getElementById("tree_list_td");
	if(obj.style.display==""||obj.style.display=="block"){
	 	obj.style.display="none";
	 	obj1.src="images/leftmenu/main_55_1.gif";
	}
	else{
		obj.style.display="block";
	 	obj1.src="images/leftmenu/main_55.gif";
	}
}
</SCRIPT>
	</HEAD>
	<BODY>
		<table width="100%" cellpadding="1" cellspacing="1">
			<tr>
				<td width="150px" valign="top" id="tree_list_td">
					<table style="margin-top: 8px;" cellspacing="0" cellpadding="0"
						width="96%" border="0">
						<tbody>
							<tr>
								<td width="5" height="5">
									<img height="5" src="images/knowledge/zhao_21.gif" width="5" />
								</td>
								<td width="662" background="images/knowledge/zhao_22.gif"></td>
								<td width="5">
									<img height="5" src="images/knowledge/zhao_23.gif" width="5" />
								</td>
							</tr>
							<tr>
								<td background="images/knowledge/zhao_24.gif"></td>
								<td class="renmen2" id="renmen2"
									style="BACKGROUND: url(http://www.ccuuc.org/img/img/1_015.gif) repeat-x"
									align="left" height="30">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td>
												<span class="STYLE6">论坛版块导航</span>
											</td>
											<td width="60" align="center">
												<a href="#"></a>
											</td>
										</tr>
									</table>
								</td>
								<td background="images/knowledge/zhao_25.gif"></td>
							</tr>
							<tr>
								<td background="images/knowledge/zhao_24.gif"></td>
								<td align="left" bgcolor="#a2ceea" height="3">
									<img height="3" src="images/knowledge/zhao_29.gif" width="150" />
								</td>
								<td background="images/knowledge/zhao_25.gif"></td>
							</tr>
							<tr>
								<td background="images/knowledge/zhao_24.gif"></td>
								<td height="200" align="left" valign="top"
									style="PADDING: 8px; line-height: 25px;">
									<s:iterator value="fbtypes" status="fbtst">
										<i><b><s:property value="name" /> </b> </i>
										<br>
										<s:iterator value="fblocks" status="fbs" id="fbsid">
											<a
												href="forumListByBlockid.action?fblock.id=<s:property value="#fbsid.id"/>"><s:property
													value="title" /> [<s:property value="manager.realname" />]</a>
											<br>
										</s:iterator>
									</s:iterator>
								</td>
								<td background="images/knowledge/zhao_25.gif"></td>
							</tr>
							<tr>
								<td height="6">
									<img height="5" src="images/knowledge/zhao_26.gif" width="5" />
								</td>
								<td background="images/knowledge/zhao_27.gif"></td>
								<td>
									<img height="5" src="images/knowledge/zhao_28.gif" width="5" />
								</td>
							</tr>
						</tbody>
					</table>
				</td>
				<td valign="middle" width="5px;" style="padding: 0px">
					<img src="images/leftmenu/main_55.gif" style="cursor: hand"
						onclick="changeTreeDisplay(this)" />
				</td>
				<td valign="top" align="left">
					<table style="margin-top: 8px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
						<tbody>
							<tr>
								<td width="5" height="5">
									<img height="5" src="images/knowledge/zhao_21.gif" width="5" />
								</td>
								<td width="662" background="images/knowledge/zhao_22.gif"></td>
								<td width="5">
									<img height="5" src="images/knowledge/zhao_23.gif" width="5" />
								</td>
							</tr>
							<tr>
								<td background="images/knowledge/zhao_24.gif"></td>
								<td class="renmen2" id="renmen2"
									style="BACKGROUND: url(http://www.ccuuc.org/img/img/1_015.gif) repeat-x"
									align="left" height="30">
									<table width="150" border="0" align="right" cellpadding="0"
										cellspacing="0">
										<tr>
											<td align="center" class="STYLE6">
												<a href="forumAddInit.action">我要发帖</a>
											</td>
										</tr>
									</table>
								</td>
								<td background="images/knowledge/zhao_25.gif"></td>
							</tr>
							<tr>
								<td background="images/knowledge/zhao_24.gif"></td>
								<td align="left" bgcolor="#a2ceea" height="3">
									<img height="3" src="images/knowledge/zhao_29.gif" width="222" />
								</td>
								<td background="images/knowledge/zhao_25.gif"></td>
							</tr>
							<tr>
								<td background="images/knowledge/zhao_24.gif"></td>
								<td height="200" align="middle" valign="top"
									style="PADDING: 8px; line-height: 25px;">
									<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
										<TBODY>
											<TR>
												<TD class=bline2 vAlign=center height=38>
													<s:property value="forum.title" />
												</TD>
											</TR>
										</TBODY>
									</TABLE>
									<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
										<TBODY>
											<TR>
												<TD height=38 align="center" >
													发帖人：
													<s:property value="forum.creater.realname" />
													发布时间：
													<s:date name="forum.createtime"
														format="yyyy年MM月dd日 HH:mm:ss" />
													浏览次数：
													<s:property value="forum.readtime" />
													次
												</TD>
											</TR>
										</TBODY>
									</TABLE>
									<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
										<TBODY>
											<TR>
												<TD height=38 align="left" vAlign=center>
													${forum.description }
												</TD>
											</TR>
										</TBODY>
									</TABLE>
									<p>
										&nbsp;
									</p>
								</td>
								<td background="images/knowledge/zhao_25.gif"></td>
							</tr>
							<tr>
								<td height="6">
									<img height="5" src="images/knowledge/zhao_26.gif" width="5" />
								</td>
								<td background="images/knowledge/zhao_27.gif"></td>
								<td>
									<img height="5" src="images/knowledge/zhao_28.gif" width="5" />
								</td>
							</tr>
						</tbody>
					</table>
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="tab4">
						<tr>
							<td width="125" height="28" align="center"
								background="images/knowledge/book_index_comments_1.gif">
								回帖列表
							</td>
							<td align="right">
								<wysLib:page></wysLib:page>
							</td>
							<td width="20" align="right">
								&nbsp;
							</td>
						</tr>
					</table>
					<table width="100%" height="200" border="0" cellpadding="0"
						cellspacing="0" class="tabrlb">
						<tr>
							<td valign="top">
								<br />
								<s:if test="topics.size==0">还没有回帖</s:if>
								<s:else>
									<s:set name="forumid" value="forum.id"></s:set>
									<s:set name="forumcreater" value="forum.creater.id"></s:set>
									<s:iterator value="topics">
										<table width="98%" height="30" border="0" align="center"
											cellpadding="0" cellspacing="0" bordercolor="#0033FF">
											<tr>
												<td width="15" bgcolor="#F5F5F5" class="daohang STYLE4">
													&nbsp;
												</td>
												<td height="30" bgcolor="#F5F5F5" class="h18">
													<p class="h1 STYLE3">
														<s:property value="creater.realname" />
														<s:date name="createtime" format="yyyy年MM月dd HH:mm:ss" />
														说：
													</p>
												</td>
												<td width="30" bgcolor="#F5F5F5" class="h18">
													<s:if test="#session.userId==#forumcreater">
														<p class="h1 STYLE3">
															<a onclick="window.confirm('确定删除？');"
																href="forum_topicDelete.action?forum.id=<s:property value="#forumid"/>&pN=<s:property value="pN"/>&pS=15&topic.id=<s:property value="id"/>">删除</a>
														</p>
													</s:if>
												</td>
											</tr>
											<tr>
												<td class="daohang">
													&nbsp;
												</td>
												<td class="h18" style="padding: 10px;">
													${content }
												</td>
											</tr>
										</table>
									</s:iterator>
								</s:else>
								<form action="forum_topicAdd.action" method="post">
									<table width="98%" height="30" border="0" align="center"
										cellpadding="0" cellspacing="0" bordercolor="#0033FF">
										<tr>
											<td colspan="2" class="daohang">
												<textarea rows="5" cols="40" name="topic.content"></textarea>
											</td>
										</tr>
										<tr>
											<td colspan="2" class="daohang">
												<input type="hidden" name="topic.forum.id"
													value="<s:property value="forum.id"/>">
												<input type="hidden" name="forum.id"
													value="<s:property value="forum.id"/>">
												<input type="hidden" name="pN"
													value="<s:property value="pN"/>">
												<input type="hidden" name="pS"
													value="<s:property value="pS"/>">
												<input type="submit" value="提交">
											</td>
										</tr>
									</table>
								</form>
							</td>
						</tr>
					</table>
					<script type="text/javascript">
						function page(i){
							document.location.href="forumView.action?forum.id=forum.id=<s:property value="forum.id"/>&pN="+i+"&pS=15";
						}
					</script>
				</td>
			</tr>
		</table>
	</BODY>
</HTML>
