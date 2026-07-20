<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@page import="com.sopia.newsandmess.entities.News"%>
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
		<TITLE>中国食品安全培训网--网站地图</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=X-UA-Compatible content=IE=EmulateIE7>
		<META content=name=keywords>
		<META content=name=description>
		<LINK href="elfrontimages/menu.css" rel=stylesheet type="text/css">
		<LINK href="elfrontimages/index.css" type=text/css rel=stylesheet>
		
		<STYLE type=text/css>
BODY {
	FONT-SIZE: 12px
}

UL {
	LIST-STYLE-TYPE: none
}
</STYLE>
		<LINK href="elfrontimages/book_index.css" type=text/css rel=stylesheet>
		<LINK href="elfrontimages/nav_style_0903.css" type=text/css
			rel=stylesheet>
		<style type="text/css">
<!--
.STYLE2 {
	font-size: 20px;
	color: #a00201;
	font-weight: bold;
}

.STYLE3 {
	color: #0000FF
}

.STYLE4 {
	color: #DFDFDF
}

.STYLE5 {
	font-size: 14px;
	font-weight: bold;
}

.STYLE6 {
	font-size: 14px;
	font-weight: bold;
	color: #0000FF;
}

.STYLE7 {
	font-size: 12px
}

.STYLE10 {
	font-size: 14px;
	font-weight: bold;
	color: #F06920;
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
.STYLE9 {
	color: #f37800
}

.STYLE11 {
	FONT-WEIGHT: bold;
	FONT-SIZE: 14px;
	COLOR: #f06920
}
-->
</style>
<script type="text/javascript">
	function fbtypesubmit(){
		var fbt_select=document.getElementById("fbtid").options;
		for(var i=0;i<fbt_select.length;i++){
			var newid=fbt_select.options[i].value;
			if(fbt_select.options[i].selected){
				if(newid.indexOf("--")<0){
					flsearch.action="searchforumList.action?fbtype.id="+fbt_select.options[i].value;
				}else{ 
					flsearch.action="forumListByBlockid.action?fblock.id="+fbt_select.options[i].value;
				}
			}	
		}
		flsearch.submit();
	}
	
	function load_(){
		if('${elmessage}' != ""){
			alert('${elmessage}');
		}
	}
</script>
	</HEAD>
	<BODY onload="load_();">
		<%@include file="frontheader.jsp"%>
		<table width="960px" border="0" align="center" cellpadding="0"
			cellspacing="0">
          <tr>
            <td width="270" valign="top"><table width="100%" border="0">
                <tr>
                  <td><table style="margin-top:8px;" cellspacing="0" cellpadding="0" width="97%" 
        border="0">
                      <tbody>
                        <tr>
                          <td width="5" height="5"><img height="5" src="images/knowledge/zhao_21.gif" 
            width="5" /></td>
                          <td width="662" background="images/knowledge/zhao_22.gif"></td>
                          <td width="5"><img height="5" src="images/knowledge/zhao_23.gif" 
        width="5" /></td>
                        </tr>
                        <tr>
                          <td background="images/knowledge/zhao_24.gif"></td>
                          <td class="renmen2" id="renmen2" 
          style="BACKGROUND: url(images/1_015.gif) repeat-x" 
          align="left" height="30"><TABLE width="96%" border=0 align="center" cellPadding=0 cellSpacing=0>
                            <TBODY>
                              <TR>
                                <TD style="padding-left: 15px;"><SPAN class=STYLE10>新闻模块导航</SPAN> </TD>
                                <TD align=middle width=60><A href="http://111.67.198.50:9080/gdgat/#"></A> </TD>
                              </TR>
                            </TBODY>
                          </TABLE></td>
                          <td background="images/knowledge/zhao_25.gif"></td>
                        </tr>
                        <tr>
                          <td background="images/knowledge/zhao_24.gif"></td>
                          <td align="left" bgcolor="#a2ceea" height="3"><img height="3" 
            src="images/knowledge/zhao_29.gif" width="222" /></td>
                          <td background="images/knowledge/zhao_25.gif"></td>
                        </tr>
                        <tr>
                          <td background="images/knowledge/zhao_24.gif"></td>
                          <td align="left" valign="top" bgcolor="#F7FBFE" style="PADDING: 8px; line-height:25px;">
													<TABLE cellSpacing=2 cellPadding=2 width="100%"
														bgColor=#f7fbfe border=0>
														<td valign="top" width="100" id="tree_list_td">
												<%
													News news = (News) request.getAttribute("news");
												
													String model=request.getAttribute("model").toString();
													String url = null;
													if(model=="0"){
														url = "newsIndex.action?&news.ntype.id=" + news.getNtype().getId()
														+ "&ntype.id=";
													}else if(model=="1"){
														url = "newsList_0_";
													}else{
														url = "newsIndex.action?&news.ntype.id=" + news.getNtype().getId()
														+ "&ntype.id=";
													}
												%>
											<wysLib:newsTypeTree href="<%=url%>" model="true" rootAble="true"></wysLib:newsTypeTree>
					</td>
													</TABLE>
						  </td>
                          <td background="images/knowledge/zhao_25.gif"></td>
                        </tr>
                        <tr>
                          <td height="6"><img height="5" src="images/knowledge/zhao_26.gif" width="5" /></td>
                          <td background="images/knowledge/zhao_27.gif"></td>
                          <td><img height="5" src="images/knowledge/zhao_28.gif" 
        width="5" /></td>
                        </tr>
                      </tbody>
                  </table></td>
                </tr>
                <tr>
                  <td><table style="margin-top:8px;" cellspacing="0" cellpadding="0" width="97%" 
        border="0">
                      <tbody>
                        <tr>
                          <td width="5" height="5"><img height="5" src="images/knowledge/zhao_21.gif" 
            width="5" /></td>
                          <td width="662" background="images/knowledge/zhao_22.gif"></td>
                          <td width="5"><img height="5" src="images/knowledge/zhao_23.gif" 
        width="5" /></td>
                        </tr>
                        <tr>
                          <td background="images/knowledge/zhao_24.gif"></td>
                          <td class="renmen2" id="renmen2" 
          style="BACKGROUND: url(images/1_015.gif) repeat-x" 
          align="left" height="30"><table width="96%" border="0" align="center"
													cellpadding="0" cellspacing="0">
                            <tr>
                              <td style="padding-left: 15px;"><span class="STYLE10">论坛版块导航</span> </td>
                              <td width="60" align="center"><a href="#"></a> </td>
                            </tr>
                          </table></td>
                          <td background="images/knowledge/zhao_25.gif"></td>
                        </tr>
                        <tr>
                          <td background="images/knowledge/zhao_24.gif"></td>
                          <td align="left" bgcolor="#a2ceea" height="3"><img height="3" 
            src="images/knowledge/zhao_29.gif" width="222" /></td>
                          <td background="images/knowledge/zhao_25.gif"></td>
                        </tr>
                        <tr>
                          <td background="images/knowledge/zhao_24.gif"></td>
                          <td height="380" align="left" valign="top" bgcolor="#F7FBFE" style="PADDING: 8px; line-height:25px;"><s:iterator value="fbtypes" status="fbtst">
													<b>类别名称： <s:property value="name" /> </b>
													<br>
													<s:iterator value="fblocks" status="fbs" id="fbsid">
														<a
															href="forumListByBlockid.action?fblock.id=<s:property value="#fbsid.id"/>">
															<s:property value="title" /> </a>
														<br>
													</s:iterator>
						  </s:iterator></td>
                          <td background="images/knowledge/zhao_25.gif"></td>
                        </tr>
                        <tr>
                          <td height="6"><img height="5" src="images/knowledge/zhao_26.gif" width="5" /></td>
                          <td background="images/knowledge/zhao_27.gif"></td>
                          <td><img height="5" src="images/knowledge/zhao_28.gif" 
        width="5" /></td>
                        </tr>
                      </tbody>
                  </table></td>
                </tr>
            </table></td>
            <td width="730" valign="top"><table style="margin-top:8px;" cellspacing="0" cellpadding="0" width="100%" 
        border="0">
        		<tbdoy>
        			<tr>
                                      <td width="5" height="5"><img height="5" src="images/knowledge/zhao_21.gif" 
            width="5" /></td>
                                      <td width="662" background="images/knowledge/zhao_22.gif"></td>
                                      <td width="5"><img height="5" src="images/knowledge/zhao_23.gif" 
        width="5" /></td>
                                    </tr>
                                    <tr>
                                      <td background="images/knowledge/zhao_24.gif"></td>
                                      <td class="renmen2" id="renmen2" 
          style="BACKGROUND: url(images/1_015.gif) repeat-x" 
          align="left" height="30"><table width="96%" border="0" align="center" cellpadding="0" cellspacing="0">
                                          <tr>
                                            <td><span class="STYLE10">知识库栏目导航</span></td>
                                            <td width="60" align="center"><a href="#"></a></td>
                                          </tr>
                                      </table></td>
                                      <td background="images/knowledge/zhao_25.gif"></td>
                                    </tr>
                                    <tr>
                                      <td background="images/knowledge/zhao_24.gif"></td>
                                      <td align="left" bgcolor="#f7fbfe" height="3"><img height="3" 
            src="images/knowledge/zhao_29.gif" width="222" /></td>
                                      <td background="images/knowledge/zhao_25.gif"></td>
                                    </tr>
                                    <tr>
                                      <td background="images/knowledge/zhao_24.gif"></td>
                                      <td height="200" align="left" valign="top" style="PADDING: 8px; line-height:25px;">
                                      <wysLib:kltype_center_list></wysLib:kltype_center_list>
                                      </td>
                                      <td background="images/knowledge/zhao_25.gif"></td>
                                    </tr>
                                    <tr>
                                      <td height="6"><img height="5" src="images/knowledge/zhao_26.gif" width="5" /></td>
                                      <td background="images/knowledge/zhao_27.gif"></td>
                                      <td><img height="5" src="images/knowledge/zhao_28.gif" 
        width="5" /></td>
                                    </tr>
                      
                    <tr>
                          <td width="5" height="5"><img height="5" src="images/knowledge/zhao_21.gif" 
            width="5" /></td>
                          <td width="662" background="images/knowledge/zhao_22.gif"></td>
                          <td width="5"><img height="5" src="images/knowledge/zhao_23.gif" 
        width="5" /></td>
                        </tr>
                        <tr>
                          <td background="images/knowledge/zhao_24.gif"></td>
                          <td class="renmen2" id="renmen2" 
          style="BACKGROUND: url(images/1_015.gif) repeat-x" 
          align="left" height="30"><TABLE width="96%" border=0 align="center" cellPadding=0 cellSpacing=0>
                            <TBODY>
                              <TR>
                                <TD style="padding-left: 15px;"><SPAN class=STYLE10>自定义模块导航</SPAN> </TD>
                                <TD align=middle width=60><A href="http://111.67.198.50:9080/gdgat/#"></A> </TD>
                              </TR>
                            </TBODY>
                          </TABLE></td>
                          <td background="images/knowledge/zhao_25.gif"></td>
                        </tr>
                        <tr>
                          <td background="images/knowledge/zhao_24.gif"></td>
                          <td align="left" bgcolor="#a2ceea" height="3"><img height="3" 
            src="images/knowledge/zhao_29.gif" width="222" /></td>
                          <td background="images/knowledge/zhao_25.gif"></td>
                        </tr>
                        <tr>
                          <td background="images/knowledge/zhao_24.gif"></td>
                          <td align="left" valign="top" bgcolor="#F7FBFE" style="PADDING: 8px; line-height:25px;">
													<TABLE cellSpacing=2 cellPadding=2 width="100%"
														bgColor=#f7fbfe border=0>
														<td valign="top" width="100" id="tree_list_td">
												
											<wysLib:newsTypeTree href="<%=url%>" rootAble="true"></wysLib:newsTypeTree>
					</td>
													</TABLE>
						  </td>
                          <td background="images/knowledge/zhao_25.gif"></td>
                        </tr>
                        <tr>
                          <td height="6"><img height="5" src="images/knowledge/zhao_26.gif" width="5" /></td>
                          <td background="images/knowledge/zhao_27.gif"></td>
                          <td><img height="5" src="images/knowledge/zhao_28.gif" 
        width="5" /></td>
                        </tr>
                  </table></td>
                </tr>
            </table>
		<s:include value="frontbottom.jsp" />
       

	</body>
</HTML>
