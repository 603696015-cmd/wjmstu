<%@ page language="java" pageEncoding="UTF-8"   %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="Description" content="财贸">
		<meta name="keywords" content="财贸">
		<title><s:property value="news.ntype.name" />--列表</title>
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
	</HEAD>
	<body oncopy="return false" oncut="return false" onpaste="return false">
		<table width="100%" cellpadding="1" cellspacing="1">
			<tr>
				<td width="150px" valign="top" id="tree_list_td">
					<table style="" width="150px" border="0"
						cellpadding="0" cellspacing="0" class="bkgreen">
						<tr>
							<td height="32" valign="middle"
								background="elfrontimages/b32.jpg">
								<table width="100%" height="32" border="0" cellpadding="0"
									cellspacing="0">
									<tr>
										<td width="110" align="center" valign="middle">
											<p>
												<a href="#" onclick="return false;" class="titgreen14">新闻栏目导航</a>
											</p>
										</td>
										<td align="right" valign="middle">
											&nbsp;
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td height="130" valign="top" style="padding:10px;">
								<wysLib:newsTypeTree rootAble="true"
									href="newsIndex.action?pN=0&pS=10&containsub=1&news.ntype.id=" />
							</td>
						</tr>
					</table>
				</td>
				<td valign="top" width="5px;" style="padding-top: 100px">
					<img src="images/leftmenu/main_55.gif" style="cursor: hand"
						onclick="changeTreeDisplay(this)" />
				</td>
				<td valign="top" width="680px"  align="left" class="bkgreen" style="padding-left:14px;">
					<table width="680px" height="40" border="0" align="left"
						cellpadding="0" cellspacing="0">
						<tr>
							<td height="42" align="left">
								<s:if test="zxNews.size==0">
									<br>目前没有<s:property value="news.ntype.name" />栏目相关新闻或公<br />
								</s:if>
								<div class="listkk" style="width: 680px; height: 5px;"></div>
								<s:iterator value="zxNews">
									<table width="650" height="90" border="0" align="left"
										cellpadding="0" cellspacing="0" class="listkk">
										<tr>
											<td height="30" class="listit">
												<a class="fl"
													href="newsIndexView.action?news.id=<s:property value="id"/>"><s:property
														value="title" />
												</a><span class="list3 fr"><s:date
														format="yyyy年MM月dd日 HH:mm:ss" name="releasetime" /> </span>
											</td>
										</tr>
										<tr>
											<td align="left" valign="top" class="list3"
												style="text-indent: 2em;">
												<s:property value="descString" />
											</td>
										</tr>
									</table>
								</s:iterator>
								<form name="ni" action="newsIndex.action" method="post">
									<s:hidden name="pN" id="pageNow"></s:hidden>
									<s:hidden name="pS"></s:hidden>
									<s:hidden name="containsub"></s:hidden>
									<s:hidden name="news.ntype.id"></s:hidden>
								</form>
								<SCRIPT type="text/javascript">
						function page(i){
							document.getElementById("pageNow").value=i;
							ni.submit();
						}
					</SCRIPT>
								
							</td>
						</tr>
						<tr><td><wysLib:page></wysLib:page></td></tr>
					</table>
					<table width="98%" height="40" border="0" align="center"
						cellpadding="0" cellspacing="0">
						<tr>
							<td align="left" valign="top"
								style="line-height: 25px; font-size: 12px;">
								${news.content }
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</HTML>

