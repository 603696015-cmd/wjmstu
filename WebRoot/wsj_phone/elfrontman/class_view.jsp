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
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>中国食品安全培训网--培训班查看</TITLE>
		<base href="<%=basePath%>">
		
		<META http-equiv=X-UA-Compatible content=IE=EmulateIE7>
		<META content=name=keywords>
		<META content=name=description>
		<LINK href="elfrontimages/index.css" type=text/css rel=stylesheet>
		<LINK href="elfrontimages/menu.css" rel=stylesheet type="text/css">
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
.bline2 {
	FONT-WEIGHT: bold; FONT-SIZE: 15pt; COLOR: #ff6600; BORDER-BOTTOM: #ccc 1px dashed; TEXT-ALIGN: center
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
-->
</style>
<SCRIPT type="text/javascript">
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
</SCRIPT>
	</HEAD>
	<BODY onLoad="setImgs();">
		 <%@include file="frontheader.jsp" %>
		<table width="960px" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
			  <td width="270" valign="top"><table style="margin-top:8px;" cellspacing="0" cellpadding="0" width="96%" 
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
          align="left" height="30"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td>　<span class="STYLE6">培训班栏目导航</span></td>
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
                          <td height="200" align="left" valign="top" style="PADDING: 8px; line-height:25px;"><wysLib:clTypeTree></wysLib:clTypeTree>
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
                    </table>
			    <p>&nbsp;</p></td>
				<td width="730" valign="top">
					<div
						style="maigin-top: 10px; padding-top: 10px; font: 15px; font-weight: bolder; color: black; padding-bottom: 10px;">
						<s:property value="course.ctype.name" />
						符合条件：”<s:property value="course.name" />“的课程如下<br>
					</div>
					<form action="course_listbytitle.action" method="post" name="ddd">
						<s:hidden name="pN" id="pageNow"></s:hidden>
						<s:hidden name="pS"></s:hidden>
						<s:hidden name="course.name"></s:hidden>
					</form>
					<script type="text/javascript">
						function page(i){
							document.getElementById("pageNow").value=i;
							ddd.submit();
						}
					</script>
					<!--<wysLib:page></wysLib:page>
					--><s:if test="zxCourses.size==0">
						<br>
						<br>无记录<br>
						<br>
					</s:if>
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0" class="dibaikuang">
							<tr>
								<td height="35" valign="bottom" class="heicu14">
									<table width="100%" height="30" border="0" cellpadding="0"
										cellspacing="0">
										<tr>
											<td class="bline2" >
												 <s:property value="elclass.name" /> 
											</td>
											<td align="center">
												<a href="#" class="STYLE7"></a><a href="#" class="STYLE7"></a>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="110" align="left" valign="top">
												<img src="<s:property  escape="false" value="elclass.mainimg"/>" id="cimg_0" width="100" height="80"/>
												<SCRIPT type="text/javascript">
													obj = document.getElementById("cimg_0");
													addImgs(obj);
												</SCRIPT>
												
											</td>
											<td   valign="top">
												简介：
												<s:property value="elclass.description" />
												<br>
												<span class="h30">创建：<s:property
														value="elclass.creater.realname" /> <s:date name="elclass.createtime"
														format="yyyy-MM-dd HH:mm:ss" /> </span>
										</tr>
									</table>
								</td>
							</tr>
						</table>
				</td>
			</tr>
		</table>
		<%@include file="frontbottom.jsp" %>
	
	
	</body>
</HTML>
