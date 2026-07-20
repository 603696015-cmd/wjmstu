<%@ page language="java" pageEncoding="UTF-8"   %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="Description" content="财贸">
		<meta name="keywords" content="财贸">
		<title>论坛-发表新话题</title>
		<link href="elfrontimages/home2.css" rel=stylesheet type="text/css">
		<script type="text/javascript" src="eleditor/kindeditor.js"></script>
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
	<BODY
		onload="KE.show({id : 'content',cssPath : 'eleditor/index.css'});">
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
									<img height="3" src="images/knowledge/zhao_29.gif" width="150px" />
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
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td>
												<span class="STYLE6">发布新话题</span>
											</td>
											<td width="60" align="center">
												<a
													href="forumAddInit.action?fblock.id=<s:property value="fblock.id" />">发起新话题</a>
											</td>
										</tr>
									</table>
								</td>
								<td background="images/knowledge/zhao_25.gif"></td>
							</tr>
						</tbody>
					</table>
					<form action="forumAdd.action" method="post" >
						<table style="padding: 8px;" width="100%" height="30" border="1"
							cellpadding="0" cellspacing="0">
							<tr>
								<td height="25" align="center">
									帖子标题
								</td>
								<td height="25">
									<input type="text" size="40" name="forum.title">
									<input type="submit" value="提交">
								</td>
							</tr>
							<tr>
								<td height="25" align="center">
									所属版块
								</td>
								<td height="25">
									<!--<input type="hidden" name="forum.fblock.id" value="<s:property value="fblock.id"/>">
                <s:property value="fblock.title"/>
					-->
									<SELECT name="forum.fblock.id">

										<s:iterator value="fbtypes" status="fbtst">
											<optgroup label="<s:property value="name" />">
												<s:property value="name" />
											</optgroup>
											<s:iterator value="fblocks" status="fbs" id="fbsid">
												<option value="<s:property value="#fbsid.id"/>">
													<s:property value="#fbsid.title" />
												</option>
											</s:iterator>
										</s:iterator>
									</SELECT>
								</td>
							</tr>
							<tr>
								<td height="25" align="center" colspan="2">
									帖子描叙
								</td>
							</tr>
						</table>
						<textarea id="content" name="forum.description"
							style="width: 100%; height: 500px; visibility: hidden;"></textarea>
					</form>
				</td>
			</tr>
		</table>
	</BODY>
</HTML>
