<%@ page language="java" pageEncoding="UTF-8"   %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="c" uri="/WEB-INF/c-rt.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="Description" content="财贸" />
		<meta name="keywords" content="财贸" />
		<title>知识-<s:property value="kltype.name" />列表</title>
		<LINK href="elfrontimages/home2.css" rel=stylesheet type="text/css" />
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
		<script type="text/javascript">
	var imgs = new Array();
	
	function addImgs(obj){
		imgs[imgs.length]=obj;
	}
	function setImgs(){
		for(var i=0;i<imgs.length;i++){
			if(imgs[i].fileSize<=0){
			 imgs[i].src="elfrontimages/coursedimg.jpg";
			}
		}
	}
	
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
</script>
	</HEAD>
	<body onload="setImgs();">
		<table width="100%" cellpadding="1" cellspacing="1">
			<tr>
				<td width="150px" valign="top" id="tree_list_td">
					<table style="" width="150px" border="0" cellpadding="0"
						cellspacing="0" class="bkgreen">
						<tr>
							<td height="32" valign="middle"
								background="elfrontimages/b32.jpg">
								<table width="100%" height="32" border="0" cellpadding="0"
									cellspacing="0">
									<tr>
										<td width="110" align="center" valign="middle">
											<p>
												<a href="#" onclick="return false;" class="titgreen14">知识栏目导航</a>
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
							<td height="130" valign="top" style="padding: 10px;">
								<wysLib:kltype_center_list></wysLib:kltype_center_list>
							</td>
						</tr>
					</table>
				</td>
				<td valign="top" width="5px;" style="padding-top: 100px">
					<img src="images/leftmenu/main_55.gif" style="cursor: hand"
						onclick="changeTreeDisplay(this)" />
				</td>
				<td valign="top" align="left">
					<table width="100%" border="0" align="left" cellpadding="0"
						cellspacing="0" class="bkgreen">
						<tr>
							<td height="662" width="670" align=left valign="top"
								bgcolor="#FBFEF5" style="padding: 10px;">
								<table width="98%" height="40" border="0" align="left"
									cellpadding="0" cellspacing="0">
									<tr>
										<td align="center">
											<s:if test="knowledges.size==0">
												<br>
												<br>目前<s:property value="kltype.name" />栏目下没有新知识<br>
												<br>
											</s:if>
											<div class="listkk" style="width: 650px; height: 5px;"></div>

											<s:iterator value="knowledges">
												<table width="650" height="100" border="0" align="left"
													cellpadding="0" cellspacing="0" class="listkk">
													<tr>
														<td width="155" height="113" rowspan="2" align="left"
															valign="middle" class="listit">
															<img src="<s:property value="mainimg_"/>" width="141"
																height="93" />
														</td>
														<td height="30" class="listit">
															<a class="fl"
																href="knowledge_center_view.action?knowledge.id=<s:property value="id"/>"><s:property
																	value="title" /> </a>
															<span class="list3 fr"><s:date name="createtime"
																	format="yyyy-MM-dd HH:mm:ss" /> </span>
														</td>
													</tr>
													<tr>
														<td align="left" valign="top" class="list3"
															style="text-indent: 2em;">
															<span class="list3" style="text-indent: 2em;"><s:property
																	value="descString" /> </span>
														</td>
													</tr>
												</table>
											</s:iterator>
										</td>
									</tr>
									<tr>
										<td>
											<wysLib:page></wysLib:page>
											<form action="knowledge_center_list.action" method="post"
												name="ddd">
												<s:hidden name="pN" id="pageNow"></s:hidden>
												<s:hidden name="pS"></s:hidden>
												<s:hidden name="kltype.id"></s:hidden>
											</form>
											<script type="text/javascript">
												function page(i){
													document.getElementById("pageNow").value=i;
													ddd.submit();
												}
											</script>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</HTML>

