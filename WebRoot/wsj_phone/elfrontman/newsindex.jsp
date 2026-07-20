<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.newsandmess.entities.News"%>
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

<HTML>
	<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />

		<TITLE>新闻中心—中国食品安全培训网</TITLE>
		 <style type="text/css">

.STYLE6 {font-size: 30px}
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
body,td,th {
	font-size: 18px;
}
.STYLE3 {font-size: 24px}
.STYLE5 {color: #000000; font-size: 18px; }

        </style>

		<META content="text/html; charset=utf-8" http-equiv=Content-Type>
		


		<META name=keywords
			content="北京,卫生,法学会,中国,食品,安全,培训网">
		<META name=description
			content="北京卫生法学会,中国食品安全培训网 ">
		

		

	</HEAD>
	<BODY>
	


  <%@include file="frontheader.jsp"%>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100" height="35" align="center" bgcolor="#FFCC66" class="STYLE5">新闻中心</td>
    <td bgcolor="00A2FC">&nbsp;</td>
  </tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" >
			  
			
			  <tr>
			    <td><TABLE width=320 border=0 align="center" cellPadding=0
											cellSpacing=0 style="margin-top: 3px;">
			      <TBODY>
			        <TR>
			          <TD align=center valign="top" class=bai></TD>
		            </TR>
		          </TBODY>
			      </TABLE></td>
		      </tr>
			  
			  <tr>
			    <td><form action="newsIndex.action" name="newslist" method="post">
										<s:hidden name="pN" id="pageNow"></s:hidden>
										<s:hidden name="pS" ></s:hidden>
										<TABLE cellSpacing=0 cellPadding=0 width="100%" align=left
											border=0>
											<TBODY>
												<s:if test="zxNews.size==0">
										  <br>
													<br>目前没有<s:property value="news.ntype.name" />栏目相关新闻或公告<br>
													<br>
												</s:if>
												<s:else>												</s:else>
												<s:iterator value="zxNews">
													<TR>
														<TD style="COLOR: #254142" align=middle width="4%"
															height=35>
															·														</TD>
													<TD align=left>
														<s:if test="modelstatus!=0">
															<A class=news
																href="newsIndexView.html?news.id=<s:property value="id"/>&ntype.id=<s:property value="ntype.id"/>"><s:property
																	value="title" />
															</A>														</s:if>
														<s:else>
															<A class=news
																href="newsIndexView.action?news.id=<s:property value="id"/>&ntype.id=<s:property value="ntype.id"/>"><s:property
																	value="title" />
															</A>														</s:else>													  
														<!--<s:date name="releasetime" format="yyyy-MM-dd" />	-->													</TD>	
													</TR>
													<TR>
														<TD background=images/gaiban2/img_26.jpg colSpan=2
															height=1></TD>
													</TR>
												</s:iterator>
											</TBODY>
										</TABLE>
										</form>				</td>
  </tr>
              <tr><td align="center"><div style="text-align:left;" >
              <wysLib:page_cisco></wysLib:page_cisco>
<!--					<wysLib:page></wysLib:page>-->
              </div></td></tr>
              <tr>
              <td><s:include value="frontbottom.jsp" /></td>
              </tr>
    </table>

	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td height="50" align="center" bgcolor="00A2FC"><a href="#" style="text-align:center; font-size:22px; color:#333; background-color:rgb(252,203,0); text-decoration:none;padding:5px; border-bottom-width:1px; border-bottom-color:#d0ac19; border-bottom-style:solid;">↑回顶部</a></td>
              </tr>
    </table>
	</body>
	<SCRIPT type=text/javascript>
		
		function page(i){
			document.getElementById("pageNow").value = i;
			newslist.submit();
		}
	</script>
</HTML>
