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
	<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />

		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>中国食品安全培训网--<s:property value="forum.title" />
		</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=X-UA-Compatible content=IE=EmulateIE7>
		<META content=name=keywords>
		<META content=name=description>
		<LINK href="wsj_phone/elfrontimages/menu.css" rel=stylesheet type="text/css">
		<LINK href="wsj_phone/elfrontimages/index.css" type=text/css rel=stylesheet>
		<STYLE type=text/css>
<STYLE type=text/css>
BODY {
	FONT-SIZE: 12px
}

UL {
	LIST-STYLE-TYPE: none
}

.bline2 {
	FONT-WEIGHT: bold;
	FONT-SIZE: 15pt;
	COLOR: #ff6600;
	BORDER-BOTTOM: #ccc 1px dashed;
	TEXT-ALIGN: center
}
  </STYLE>
		<LINK href="wsj_phone/elfrontimages/book_index.css" type=text/css rel=stylesheet>
		<LINK href="wsj_phone/elfrontimages/nav_style_0903.css" type=text/css
			rel=stylesheet>
<style type="text/css">
		.STYLE6 {	font-size: 14px;
	font-weight: bold;
	color: #0000FF;
}
        
		.STYLE6 {	font-size: 14px;
	font-weight: bold;
	color: #0000FF;
}
.STYLE6 {	font-size: 14px;
	font-weight: bold;
	color: #0000FF;
}
.textbg {
	background: url("images/textbg.jpg");
	line-height:25px;
	background-repeat: repeat-x;
	padding-bottom: 0px;margin: 5px;
	color:#FFFFFF;
	font-size: 13px;
	font-weight:bold;
	height: 28px;
	width: 95px;
	text-align: center;
	cursor: pointer;
}
.STYLE6 {	font-size: 14px;
	font-weight: bold;
	color: #0000FF;
}
.STYLE10 {
	font-size: 14px;
	color: #F06920;
	font-weight: bold;
}

.menu_bg {
	WIDTH:320px;
	HEIGHT: 40px;
	background-color:#F3F3F3;
	background-repeat: repeat-x;
	background-position: left -100px;
}
.menu_bg LI {
	TEXT-ALIGN: center; WIDTH: 100px; BACKGROUND-REPEAT: no-repeat; BACKGROUND-POSITION: left -5px; FLOAT: left; HEIGHT: 40px
}
.menu_bg LI A {
	LINE-HEIGHT: 40px; DISPLAY: block; HEIGHT: 40px; COLOR: #000; FONT-SIZE: 14px; FONT-WEIGHT: normal; TEXT-DECORATION: none
}
.menu_bg LI A:link {
	COLOR: #000;
}
.menu_bg LI A:visited {
	COLOR: #000;
}
.menu_bg LI A.here {
	COLOR: #000;
	background-image: url(http://www.ccunc.com/images/xtb/menu_hover.gif);
	background-repeat: no-repeat;
	background-position: -5px;
}
.menu_bg LI A:hover {
	COLOR: #fff;
}
.menu_bg LI A.libg {
	BACKGROUND: url(http://www.ccunc.com/images/xtb/libg.gif) no-repeat; COLOR: #fff
}
li{ list-style:none;}
</style>
	<script type="text/javascript" src="editor/fckeditor.js"></script>
	<script type="text/javascript">
		function fbtypesubmit(){
			var fbt_select=document.getElementById("fbtid").options;
			for(var i=0;i<fbt_select.length;i++){
				var newid=fbt_select.options[i].value;
				if(fbt_select.options[i].selected){
					if(newid.indexOf("--")<0){
						flsearch.action="searchforumList.action?fbtype.id="+fbt_select.options[i].value;
					}else{
						alert(fbt_select.options[i].value);
						flsearch.action="forumListByBlockid.action?fblock.id="+fbt_select.options[i].value;
					}
				}	
			}
		flsearch.submit();
		}
		function init(){
			//alert("dd"+id);
			//$("#opt_frame"+id).attr("src","_editor/editor.html?height=200&id=__option"+id);
			//$("#opt_frame"+id).attr("width",500);
			//$("#opt_frame"+id).attr("height",120);
			var oFCKeditor = new FCKeditor("topContent") ;
			oFCKeditor.BasePath = "editor/" ;
			oFCKeditor.Height = 120;
			oFCKeditor.Width = 320;
			oFCKeditor.ToolbarSet = "qoption" ;
			oFCKeditor.ReplaceTextarea();
		}
	</script>
	</HEAD>
	<BODY onLoad="init();"><%@include file="frontheader.jsp"%>
		<table width="100%" border="0" align="left" cellpadding="0"
			cellspacing="0" bgcolor="#ffffff">
		  <tr>
			
				<td valign="top"><table style="margin-top:8px;" cellspacing="0" cellpadding="0" width="100%" 
        border="0">
                      <tbody>
                        <tr>
                          <td  height="30" 
          align="left" class="renmen2" id="renmen2" ><table width="100%" border="0" align="left" cellpadding="0" cellspacing="0" style=" background-color:#00A2FC;">
                            <tr><td><a href="forumAddInit.action"><span class="textbg">发起讨论</span> </a></td></tr>
                          </table></td>
                        </tr>
                        <tr>
                          <td align="left" valign="top" style="PADDING: 8px; line-height:25px;"> <TABLE cellSpacing=0 cellPadding=0 width="320" border=0>
										<TBODY>
											<TR>
												<TD class="bline2" vAlign="middle" height=43>
													 <s:property
															value="forum.title" />											  </TD>
											</TR>
										</TBODY>
									</TABLE>
									<TABLE cellSpacing=0 cellPadding=0 width="320" border=0>
										<TBODY>
											<TR>
												<TD height=38 align="left" vAlign=center
													style="color: #0099FF;">
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
									<TABLE cellSpacing=0 cellPadding=0 width="320" border=0>
										<TBODY>
											<TR>
												<TD height=38 align="left" vAlign=center>
													${forum.description_ }
												</TD>
											</TR>
										</TBODY>
									</TABLE>
                          </td>
                        </tr>
                        <tr>
                          <td height="6" background="images/knowledge/zhao_27.gif"></td>
                        </tr>
                        <tr><td><wysLib:page></wysLib:page></td></tr>
                      </tbody>
                    </table>
					<table style="margin-top:8px;" cellspacing="0" cellpadding="0" width="320" 
        border="0">
                      <tbody>
                        <tr>
                          <td width="5" background="images/knowledge/zhao_24.gif"></td>
                          <td width="662" height="30" 
          align="left" class="renmen2" id="renmen2" 
          style="BACKGROUND: url(images/1_015.gif) repeat-x"><table width="100%" border="0" cellpadding="0" cellspacing="0"
										style="margin-top: 0px;">
                            <tr>
                              <td width="125" align="center" valign="middle" style="padding-top:3px;"><span class="STYLE10" >回复列表</span> </td>
                              <td align="right" valign="middle" style="padding-bottom:9px;">
                              </td>
                              
                              <td width="20" align="right" valign="middle">&nbsp;</td>
                            </tr>
                          </table></td>
                        </tr>
                        <tr>
                          <td background="images/knowledge/zhao_24.gif"></td>
                          <td align="left" bgcolor="#a2ceea" height="3"><img height="3" 
            src="images/knowledge/zhao_29.gif" width="222" /></td>
                        </tr>
                        <tr>
                          <td background="images/knowledge/zhao_24.gif"></td>
                          <td align="left" valign="top" bgcolor="#F7FBFE" style="PADDING: 8px; line-height:25px;"> <table width="100%" height="200" border="0" align="left"
										cellpadding="0" cellspacing="0">
										<tr>
											<td align="left" valign="top">
												<br />
												<s:if test="topics.size==0">还没有回复</s:if>
												<s:else>
													<s:set name="forumid" value="forum.id"></s:set>
													<s:set name="forumcreater" value="forum.creater.id"></s:set>
													<s:iterator value="topics">
														<table width="98%" height="30" border="0" align="center"
															cellpadding="0" cellspacing="0" bordercolor="#0033FF">
															<tr>
																<td width="15" bgcolor="#F5F5F5" class="daohang STYLE4">&nbsp;
																	

																</td>
																<td height="30" align="left" bgcolor="#F5F5F5"
																	class="h18">
																	<p class="h1 STYLE3">
																		<s:property value="creater.realname" />
																		<s:date name="createtime" format="yyyy年MM月dd HH:mm:ss" />
																		说：
																	</p>
																</td>
																<td width="30" align="center" bgcolor="#F5F5F5"
																	class="h18">
																	<s:if test="#session.userId==#forumcreater">
																		<p class="h1 STYLE3">
																			<a onClick="return window.confirm('确定删除？');"
																				href="forum_topicDelete.action?forum.id=<s:property value="#forumid"/>&pN=<s:property value="pN"/>&pS=15&topic.id=<s:property value="id"/>">删除</a>
																		</p>
																	</s:if>
																</td>
															</tr>
															<tr>
																<td class="daohang">&nbsp;
																	

																</td>
																<td align="left" class="h18" style="padding: 10px;">
																	${content }
																</td>
															</tr>
														</table>

													</s:iterator>
													<br />
													<span class="STYLE10" >我来说两句：</span>
													<br />
												</s:else>
												<form action="forum_topicAdd.action" method="post">
													<table width="98%" height="30" border="0" align="center"
														cellpadding="0" cellspacing="0" bordercolor="#0033FF">
														<tr>
															<td colspan="2" align="left" class="daohang">
																<textarea rows="5" id="topContent" cols="30" style="border: gray 1px solid;" name="topic.content"></textarea>
															</td>
														</tr>
														<tr>
															<td colspan="2" align="left" class="daohang">
																<input type="hidden" name="topic.forum.id"
																	value="<s:property value="forum.id"/>">
																<input type="hidden" name="forum.id"
																	value="<s:property value="forum.id"/>">
																<input type="hidden" name="pN"
																	value="<s:property value="pN"/>">
																<input type="hidden" name="pS"
																	value="<s:property value="pS"/>">
                                         						<s:if test="#request.isAudit==true">
																	<input class="textbg4" onClick="javascript:alert('回复已成功，请等待审核...');" name="submit" type="submit" value="提交">
																</s:if>
																<s:else>
																	<input class="textbg4" onClick="javascript:alert('回复已成功!');" name="submit" type="submit" value="提交">
																</s:else>
															</td>
														</tr>
													</table>
												</form>
											</td>
										</tr>
						  </table></td>
                        </tr>
                      </tbody>
                  </table>
		    </td>
		  </tr>
		</table>
		<form action="forumView.action" method="post" name="ddd">
  <s:hidden name="pN" id="pageNow"></s:hidden>
			<s:hidden name="pS"></s:hidden>
			<s:hidden name="forum.id"></s:hidden>
		</form>
		<script type="text/javascript">
						function page(i){
							document.getElementById("pageNow").value=i;
							ddd.submit();
						}
		</script>
	
	</body>
</HTML>
