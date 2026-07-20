<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.newsandmess.entities.News"%>
<%@page import="com.sopia.common.SystemConfOp"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="zdyLib" uri="/WEB-INF/zdyLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<!-- saved from url=(0045)http://221.208.198.29/gaiban0318/index.action -->
<HTML>
	<HEAD>
		<TITLE><s:property value="news.title" />--<s:property value="news.ntype.name" /></TITLE>
		<INPUT id=urlHead value=http type=hidden>
		<META content="text/html; charset=UTF-8" http-equiv=Content-Type>
		<LINK rel="shortcut icon" href="favicon.ico">
		<LINK rel=stylesheet type=text/css href="images/gaiban2/css.css">
		<LINK rel=stylesheet type=text/css
			href="images/gaiban2/jquery-cluetip.css">

		<LINK href="images/gaiban2/global.css" type=text/css rel=stylesheet>
		<link href="css/listlable.css" type="text/css" rel="stylesheet">


		<META name=keywords
			content="建设机械职业教育,操作证书查询,挖掘机培训,挖掘机培训学校,塔机培训,机械操作证书查询,证书认证,权威机构,培训学校,挖掘机考证,&#9;挖掘机械操作证书,铲土运输机械操作证书,工程起重机械操作证书,机动工业车辆证书,压实机械操作证书,路面机械操作证书,桩工机械操作证书,混凝土机械操作证书,钢筋和预应力机械操作证书,装修机械操作证书,凿岩器械,高空作业,市政机械,环境卫生机械,电梯,自动扶梯,垃圾处理设备">
		<META name=description
			content="中国建设教育协会建设机械职业教育专业委员会是中国建设教育协会下属的分支机构，成立于1992年。2004年经国家教育部，民政部正式批准并重新登记注册。该机构的会员单位是由国内外从事生产、经营建设机械的企业和建设机械研究等具有法人资格并在建机行业中具有较强影响力的经济实体所组成，属于非盈利性但具有较强的专业性的社会团体。该协会跨国别：中国、美国、德国、日本、韩国；跨体制：国企、外资、合资、集体、民营；跨产品种类：挖掘机械、混凝土机械、压实机械、桩工机械、路面机械等；跨地域：除国外，上海、徐州、西安、山东、广西、天津、北京、武汉、四川、洛阳、沈阳、江苏、云南、合肥、扬州、长沙等；跨行业服务：除企业内部职业教育工作外，还为行业内培训操作手，并核发操作证书等。 ">
		<STYLE type=text/css>
<STYLE type=text/css>
BODY {
	FONT-SIZE: 12px
}

UL {
	LIST-STYLE-TYPE: none
}

.bline {
	FONT-SIZE: 10pt;
	BORDER-BOTTOM: #ccc 1px dashed
}

.bline2 {
	FONT-WEIGHT: bold;
	FONT-SIZE: 15pt;
	COLOR: #ff6600;
	BORDER-BOTTOM: #ccc 1px dashed;
	TEXT-ALIGN: center
}
</STYLE>
		<LINK href="elfrontimages/menu.css" rel=stylesheet type="text/css">
		<LINK href="elfrontimages/index.css" type=text/css rel=stylesheet>
		<LINK href="css/book_index.css" type=text/css rel=stylesheet>
		<LINK href="css/nav_style_0903.css" type=text/css rel=stylesheet>



	</HEAD>
	<BODY>
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
          align="left" height="30"><table width="96%" border="0" align="center" cellpadding="0" cellspacing="0">
                              <tr>
                                <td><span class="STYLE6">本栏目推荐新闻</span></td>
                                <td width="60" align="center"><a href="#"></a></td>
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
                          <td align="left" valign="top" style="PADDING: 8px; line-height:25px;">
                          	<s:iterator value="tjNews">
						  		<img src="elfrontimages/iconred.gif" width="4" height="6" class="icon" /> 
						  		<a href="newsIndexView.action?news.id=<s:property value="id"/>&ntype.id=<s:property value="ntype.id"/>">${title }</a><br>
							</s:iterator>
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
          align="left" height="30"><table width="96%" border="0" align="center" cellpadding="0" cellspacing="0">
                              <tr>
                                <td><span class="STYLE6">整站新闻推荐</span></td>
                                <td width="60" align="center"><a href="#"></a></td>
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
                          <td height="200" align="left" valign="top" style="PADDING: 8px; line-height:25px;">
							<s:iterator value="zztjNews">
						  		<img src="elfrontimages/iconred.gif" width="4" height="6" class="icon" /> 
						  		<a href="newsIndexView.action?news.id=<s:property value="id"/>&ntype.id=<s:property value="ntype.id"/>">${title }</a><br>
						  	</s:iterator>
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
            </table></td>
            <td width="730" valign="top"><table style="margin-top:8px;" cellspacing="0" cellpadding="0" width="100%" 
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
          align="left" height="30"><table width="98%" border="0" align="center" cellpadding="0"
										cellspacing="0">
                      <tr>
                        <td><%-- 
												<span class="STYLE6">当前位置：<a href="index.action">首页</a>
													&gt;&gt; <a
													href="newsIndex.action?news.title=null&news.ntype.id=15&ntype.id=1">新闻公告</a>
												</span>
											 --%>
                            <wysLib:TreeNavigation oid="${ntype.id}" itype="newsTree" href="newsIndex.action?ntype.id=" />
                        </td>
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
                    <td height="370" align="left" valign="top" style="PADDING: 8px; line-height:25px;">
					
						<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
							<TBODY>
								<TR>
									<TD class=bline2 vAlign=center height=38>
										<s:property value="news.title" />
									</TD>
								</TR>
							</TBODY>
						</TABLE>
						<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
							<TBODY>
								<TR>
									<TD style="padding-top: 10px;" height=38 align="left"
										vAlign=center>
										<!--创建者：
										<s:property value="news.owner.realname" />
										创建时间：
										<s:date name="news.releasetime"
											format="yyyy-MM-dd HH:mm:ss" />
										&nbsp;&nbsp;&nbsp; 浏览数：
										<s:property value="news.browsefor" />-->
										
										<b> 相关附件:</b>
										<s:if test="news.stuffs.size==0">暂无</s:if>
										<s:iterator value="news.stuffs">
											<!-- <a
												href="download_nstuff.action?stuff.id=<s:property value="id"/>"><s:property
													value="title" /> </a> -->
											<a
												href="<%=SystemConfOp.getStuffUrl() %>download.jsp?filename=<s:property value="description"/>"><s:property
													value="title" /> </a>
										<br>
												
										</s:iterator>
											<br><br>
										${news.content_ }
									</TD>
								</TR>
							</TBODY>
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
        </table>
		<s:include value="frontbottom.jsp" />

	</BODY>
</HTML>

