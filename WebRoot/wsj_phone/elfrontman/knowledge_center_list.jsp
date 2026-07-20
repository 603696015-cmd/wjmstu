<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setCharacterEncoding("utf-8");
	response.setCharacterEncoding("utf-8");
%>
<HTML>
	<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />

		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>中国食品安全培训网--知识--<s:property value="kltype.name" />列表</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<LINK href="wsj_phone/css/book_index.css" type=text/css rel=stylesheet>
		<LINK href="wsj_phone/css/nav_style_0903.css" type=text/css rel=stylesheet>
		<LINK href="wsj_phone/elfrontimages/menu.css" rel=stylesheet type="text/css">
		<LINK href="wsj_phone/elfrontimages/index.css" type=text/css rel=stylesheet>
		<STYLE type=text/css>
BODY {
	FONT-SIZE: 12px
}

table {
	margin: 0px;
}

td {
	font-size: 12px;
	margin: 0px;
}

tr {
	margin: 0px
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

UL {
	LIST-STYLE-TYPE: none
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

.menu_bg {
	WIDTH: 100%;
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
        .STYLE10 {	font-size: 14px;
	font-weight: bold;
	color: #F06920;
}
.STYLE2 {	font-size: 20px;
	color: #a00201;
	font-weight: bold;
}
        </STYLE>
	</HEAD>
<BODY>
	
		<!-- 	<table width="1000" height="30" border="0" align="center"
			cellpadding="0" cellspacing="0">
			<tr>
				<td width="280" align="center">
					<img src="images/knowledge/logo_v3.gif" width="159" height="30" />
				</td>
				<td width="74" align="center" background="images/knowledge/dh4.gif"
					class="dh1">
					<a href="#">首页</a>
				</td>
				<td width="2">
					&nbsp;
				</td>
				<td width="74" align="center" background="images/knowledge/dh3.gif"
					class="dh1">
					新闻公告
				</td>
				<td width="10" align="center" class="dh1">
					&nbsp;
				</td>
				<td width="74" align="center" background="images/knowledge/dh3.gif"
					class="dh1">
					课程中心
				</td>
				<td width="10" align="center" class="dh1">
					&nbsp;
				</td>
				<td width="74" align="center" background="images/knowledge/dh3.gif"
					class="dh1">
					知识中心
				</td>
				<td width="10" align="center" class="dh1">
					&nbsp;
				</td>
				<td width="74" align="center" background="images/knowledge/dh3.gif"
					class="dh1">
					论坛交流
				</td>
				<td width="10" align="center" class="dh1">
					&nbsp;
				</td>
				<td width="74" align="center" background="images/knowledge/dh3.gif"
					class="dh1">
					<a href="#">个人中心</a>
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
		</table>
		<table width="1000" height="30" border="0" align="center"
			cellpadding="0" cellspacing="0"
			background="images/knowledge/book_mj_002.gif" class="tablinkwhite">
			<tr>
				<td style="padding-top: 8px;padding-left:20px;" align="left" valign="middle">
					<form action="knowledge_center_listbytitle.action" method="post"
						name="pub_search" target="_blank" id="pub_search">
						知识搜索：
						<input class="input_02" onClick="this.value=''" size="65"
							name="knowledge.title" />
					 <select size="1" name="s_type">
							<option value="0" selected="selected">
								知识中心
							</option>
							<option value="1">
								论坛文章
							</option>
						</select> 
						<input name="submit" type="submit" value="搜索" />
					</form>
				</td>
			</tr>
		</table>
				
		<table style="margin-bottom: 3px;" width="1000" height="37" border="0"
			align="center" cellpadding="0" cellspacing="0"
			background="images/knowledge/book_mj_005.gif">
			<tr>
				<td>
					欢迎
					<s:property value="#session.realname" />
					<a href="logout.action">[退出登陆]</a>
					<a href="studentman.action">[个人中心]</a>
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
		</table>-->
		<!--<%@include file="frontheader.jsp"%>-->
<div style=" width:100%; height:40px; line-height:40px; background-color:#00A2FC;">卫生局</div>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
			  <tr>
			    <td><div class="menu_bg">
			      <DIV>
			        <LI> <A href="index.action">网站首页

</A> </LI>
			        <LI> <A href="newsIndex.action">新闻首

页</A> </LI>
			        <LI> <A href="forumIndex.action">论坛

首页</A> </LI>
		          </DIV>
			      <div class="menu_bg" 

style="float:left;">
			        <LI> <A 

href="knowledge_center_list.action">知识库首页</A> </LI>
			        <LI> <A 

href="forum_courseclub.action">选课中心</A> </LI>
			        <LI> <A href="forum_classclub.action">

选班中心</A> </LI>
		          </div>
			      <div class="menu_bg" 

style="float:left;">
			        <LI> <A 

href="examRoomShoppping.action">考场中心</A> </LI>
			        <LI> <A 

href="cisco_user_center.action">个人中心</A> </LI>
			        <LI> <A href="map.action">网站地图</A> 

</LI>
		          </DIV>
			      </div></td>
                  
		      </tr>
    </table>
		<table width="100%" border="0" cellpadding="0" cellspacing="0" >
		  <tr>
		    <td ><table style="margin-top:8px;" cellspacing="0" cellpadding="0" width="100%" 
        border="0">
		      <tbody>
		        <tr>
		          <td width="662" height="5" background="images/knowledge/zhao_22.gif"></td>
	            </tr>
		        <tr>
		          <td class="renmen2" id="renmen" 
          style="background-color:#00A2FC;" 
          align="left" height="30"><table width="96%" border="0" align="center" cellpadding="0" cellspacing="0">
		            <tr>
		              <td><span class="STYLE6">资料组合搜索</span></td>
		              <td width="60" align="center"><a href="#"></a></td>
	                </tr>
		            </table></td>
	            </tr>
		        <tr>
		          <td align="left" valign="top" style="PADDING: 8px; line-height:25px;"><form action="knowledge_center_list.action"
													method="get" name="klsearch" target="_parent">
		            <s:hidden name="pN" id="pageNow2"></s:hidden>
		            <s:hidden name="pS"></s:hidden>
		            <input type="hidden" id="str2" name="str2"
														value="knowledgeserach" />
		            <TABLE cellSpacing=2 cellPadding=2 width="320"
														bgColor=#ebebeb border=0>
		              <TBODY>
		                <TR>
		                  <TD align=middle width=100 bgColor=#ffffff height=30><STRONG>资料分类</STRONG></TD>
		                  <TD bgColor=#ffffff><LABEL>
		                    <select style="WIDTH: 100%;height: 25px;border: 1px solid #000000;" name="kltype.id2"
																			id="kltype.id">
		                      <wysLib:kltype_select selectid="${kltype.id}" />
	                        </select>
		                    </LABEL></TD>
	                    </TR>
		                <TR>
		                  <TD align=middle width=100 bgColor=#ffffff height=30><STRONG>资料名称</STRONG></TD>
		                  <TD bgColor=#ffffff><LABEL>
		                    <input style="WIDTH: 100%;height: 25px;border: 1px solid #000000;" type="text" id="knowledge.title2"
																			name="knowledge.title2" value="<s:property value="knowledge.title" />
		                    " /> </LABEL></TD>
	                    </TR>
		                <TR>
		                  <TD align=middle width=100 bgColor=#ffffff height=30>&nbsp;</TD>
		                  <TD bgColor=#ffffff><INPUT name="submit2" type="submit" class="textbg4"
																		onclick="javascript:document.getElementById('pageNow')=0"
																		value="搜 索"></TD>
	                    </TR>
	                  </TBODY>
	                </TABLE>
		            </form></td>
	            </tr>

	          </tbody>
	        </table></td>
	      </tr>
	      </tr>
		  <tr>
		    <td><table style="margin-top:8px;" cellspacing="0" cellpadding="0" width="100%" 
        border="0">
		      <tbody>
		        <tr>
		          <td width="1070" height="5" background="images/knowledge/zhao_22.gif"></td>
	            </tr>
		        <tr>
		          <td class="renmen2" id="renmen3" 
          style=" background-color:#00A2FC;" 
          align="left" height="30"><table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
		            <tr>

		              <td><wysLib:TreeNavigation oid="${kltype.id}" itype="knowledgeTree" href="knowledge_center_list.action?kltype.id=" /></td>
		              <td width="60" align="center"></td>
	                </tr>
		            </table></td>
	            </tr>
		        <tr>
		          <td align="left" valign="top" style="PADDING: 8px; line-height:25px;"><form action="knowledge_center_list.action" method="post"
										name="ddd">
		            <s:hidden name="pN" id="pageNow_"></s:hidden>
		            <s:hidden name="pS"></s:hidden>
		            <s:hidden name="kltype.id"></s:hidden>
		            </form>
		            <script type="text/javascript">
										function page(i){
											document.getElementById("pageNow_2").value=i;
											ddd.submit();
										}
									</script>
                     
		            <!--<wysLib:page></wysLib:page>-->
		            <s:iterator value="knowledges">
		              <table width="320" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><table width="320" border="0" align="left" cellpadding="0"
											cellspacing="0" class="dibaikuang">
		                <tr>
		                  <td height="35" valign="bottom" class="heicu14"><table width="98%" height="30" border="0" align="right" cellpadding="0"
														cellspacing="0">
		                    <tr>
		                      <td class="STYLE5"><a 
																	href="knowledge_center_view.action?knowledge.id=<s:property value="id"/>&kltype.id=
		                        <s:property value="kltype.id"/>
		                        ">
		                        <s:property
																		value="title" />
		                        </a></td>
		                      <td align="center"></td>
	                        </tr>
		                    </table></td>
	                    </tr>
		                <tr>
		                  <td height="95" valign="bottom"><table width="320" border="0" align="center"
														cellpadding="0" cellspacing="0">
		                    <tr>
		                      <td height="85" valign="top">
		                        <span class="h30">创建：
		                          <s:property value="owner.realname" />
		                          <s:date name="createtime" format="yyyy-MM-dd HH:mm:ss" />
		                          | 点击数：
		                          <s:property value="readtime" />
	                            </span></td>
	                        </tr>
		                    </table></td>
	                    </tr>
	                  </table></td>
  </tr>
</table>

	                </s:iterator>
		            <s:if test="knowledges.size==0">
		              <div style="text-align:center; width:320px;">
		                <s:property value="#request.elmessage" />
	                  </div>
	                </s:if>
		            </td>
	            </tr>
		        <tr>
		          <td><wysLib:page></wysLib:page></td>
	            </tr>
	          </tbody>
	        </table></td>
	      </tr>
          <tr>
          <td><s:include value="frontbottom.jsp" /></td>
          </tr>
</table>
		<p>&nbsp;</p>
<!--<s:include value="frontbottom.jsp" />-->

	
	</body>
</HTML>
