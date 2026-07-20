<%@ page language="java" pageEncoding="UTF-8"   %>
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
		<TITLE>XX市干部培训网络学校--知识--<s:property value="kltype.name"/>列表</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<LINK href="css/book_index.css" type=text/css rel=stylesheet>
		<LINK href="css/nav_style_0903.css" type=text/css rel=stylesheet>
		<LINK href="elfrontimages/index.css" type=text/css rel=stylesheet>
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
	FONT-SIZE: 10pt; BORDER-BOTTOM: #ccc 1px dashed
}
.bline2 {
	FONT-WEIGHT: bold; FONT-SIZE: 15pt; COLOR: #ff6600; BORDER-BOTTOM: #ccc 1px dashed; TEXT-ALIGN: center
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
</STYLE>
	</HEAD>
	<BODY>
			 <%@include file="frontheader.jsp" %>
		<!--<table width="1000" height="30" border="0" align="center"
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
								论坛帖子
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
		<table width="1000" border="0" align="center" cellpadding="0"
			cellspacing="0" class=tabrl>
			<tr>
				<td height="40" align="left">
					<table width="100%" height="40" border="0" align="center"
						cellpadding="0" cellspacing="0" class="tabb">
						<tr>
							<td>
								当前位置：<a href="knowledge_center.action"> 知识中心首页</a>&gt;&gt;
								符合"<s:property value="knowledge.title"/>"条件的搜索结果入下：
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0">
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
          style="BACKGROUND: url(http://www.ccuuc.org/img/img/1_015.gif) repeat-x" 
          align="left" height="30"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td>　<span class="STYLE6">栏目推荐知识</span></td>
                      <td width="60" align="center"><a href="#">更多&gt;&gt;</a></td>
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
                <td height="200" align="left" 
          valign="top" 
          style="PADDING: 8px; line-height:25px;">  <s:iterator value="tjknowledges"> · <a href="knowledge_center_view.action?knowledge.id=<s:property value="id"/>">
                    <s:property value="title"/></a><br></s:iterator>
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
            <table style="margin-top:8px;" cellspacing="0" cellpadding="0" width="96%" 
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
          style="BACKGROUND: url(http://www.ccuuc.org/img/img/1_015.gif) repeat-x" 
          align="left" height="30"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td>　<span class="STYLE6">栏目热门知识</span></td>
                        <td width="60" align="center"><a href="#">更多&gt;&gt;</a></td>
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
                  <td height="200" align="left" 
          valign="top" 
          style="PADDING: 10px; line-height:30px;">  <s:iterator value="rmknowledges"> · <a href="knowledge_center_view.action?knowledge.id=<s:property value="id"/>">
                      <s:property value="title"/></a><br></s:iterator>
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
           <!-- <table style="margin-top:8px;" cellspacing="0" cellpadding="0" width="96%" 
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
          style="BACKGROUND: url(http://www.ccuuc.org/img/img/1_015.gif) repeat-x" 
          align="left" height="30"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td>　<span class="STYLE6">栏目重点知识</span></td>
                        <td width="60" align="center"><a href="#">更多&gt;&gt;</a></td>
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
                  <td height="200" align="left" 
          valign="top" 
          style="PADDING: 10px; line-height:30px;">  <s:iterator value="zdknowledges"> ·<a href="knowledge_center_view.action?knowledge.id=<s:property value="id"/>"><s:property value="title"/></a><br></s:iterator>
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
            </table> --></td>
        <td width="730" valign="top"><form action="knowledge_center_listbytitle.action" method="post" name="ddd">
						<s:hidden name="pN" id="pageNow"></s:hidden>
						<s:hidden name="pS"></s:hidden>
						<s:hidden name="knowledge.title"></s:hidden>
					</form>
					<script type="text/javascript">
						function page(i){
							document.getElementById("pageNow").value=i;
							ddd.submit();
						}
					</script><!--<wysLib:page></wysLib:page>-->
					<s:iterator value="knowledges">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0" class="dibaikuang">
						<tr>
							<td height="35" valign="bottom" class="heicu14">
								<table width="100%" height="30" border="0" cellpadding="0"
									cellspacing="0">
									<tr>
										<td class="STYLE5">
										<a href="knowledge_center_view.action?knowledge.id=<s:property value="id"/>"><s:property value="title"/></a>
										</td>
										<td align="center"></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td height="95" valign="bottom">
								<table width="98%" border="0" align="center" cellpadding="0"
									cellspacing="0">
									<tr>
										<td height="85" valign="top">
											 简介：<s:property value="descString"/> 
											<br />
											<span class="h30">创建：<s:property value="owner.realname"/> <s:date name="createtime" format="yyyy-MM-dd HH:mm:ss"/> | 点击数：<s:property value="readtime"/> </span>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					</s:iterator><wysLib:page></wysLib:page>
					</td>
      </tr>
    </table>
		<%@include file="frontbottom.jsp" %>
	
	</BODY>
</HTML>
