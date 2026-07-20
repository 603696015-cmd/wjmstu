<%@ page language="java" pageEncoding="UTF-8"%>
<script>
function SetHome(obj){ 
		var vrl = "<%=basePath%>";
        try{ 
                obj.style.behavior='url(#default#homepage)';obj.setHomePage(vrl); 
        } 
        catch(e){ 
                if(window.netscape) { 
                        try { 
                                netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect"); 
                        } 
                        catch (e) { 
                                alert("此操作被浏览器拒绝！\n请在浏览器地址栏输入“about:config”并回车\n然后将 [signed.applets.codebase_principal_support]的值设置为'true',双击即可。"); 
                        } 
                        var prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components.interfaces.nsIPrefBranch); 
                        prefs.setCharPref('browser.startup.homepage',vrl); 
                 } 
        } 
} 
</script>

<table width="1000" height="120" border="0" align="center" cellpadding="0" cellspacing="0" background="elfrontimages/banner_bg.jpg">
  <tr>
 <!--  <td width="150" align="center" valign="top"><img src="elfrontimages/01.gif" width="81" height="92" /></td> -->  
 <!--   <td align="left" valign="top"><img src="elfrontimages/banner.jpg" width="316" height="83" /></td> --> 
    <td width="100"  align="right" valign="middle" background="elfrontimages/banner.jpg"><table  width="100" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20" height="20" align="left" valign="middle"><img src="elfrontimages/02.gif" width="14" height="14" /></td>
        <td align="left" valign="middle"><a style="cursor:hand" onClick="javascript:SetHome(this)">设为首页</a></td>
      </tr>
      <tr>
        <td width="20" height="20" align="left" valign="middle"><img src="elfrontimages/03.gif" width="14" height="14" /></td>
        <td align="left" valign="middle"><a href="registerInit.action"><span class="style3">注册用户</span></a></td>
      </tr>
      <tr>
        <td width="20" height="20" align="left" valign="middle"><img src="elfrontimages/04.gif" width="14" height="13" /></td>
        <td align="left" valign="middle"><A href="admin.action?module=commonman">管理中心</A></td>
      </tr>
      <tr>
        <td width="20" height="20" align="left" valign="middle"><img src="elfrontimages/05.gif" width="16" height="13" /></td>
        <td align="left" valign="middle"><A href="logout.action">退出系统</A></td>
      </tr>
    </table></td>
  </tr>
</table><table width="1000" height="40" border="0" align="center" cellpadding="0" cellspacing="0" background="elfrontimages/menu_bak.jpg">
  <tr>
    <td><DIV id=tabs1>
                        <UL><!-- CSS Tabs -->
                          <LI id=current0><A href="index.action"><span class="STYLE12">首&nbsp;页</span></A> 
           <!--           <LI id=current1><A href="newsIndex.action?news.title=null&amp;news.ntype.id=1&amp;ntype.id=1" ><span class="STYLE4">资讯中心</span></A></LI> -->
           				  <LI id=current1><A href="html/newsList0.html" ><span class="STYLE4">资讯中心</span></A></LI>
                          <LI id=current2><A href="course_libraryList.action?pN=0&amp;pS=10" ><span class="STYLE4">课程中心</span></A></LI>
                          <LI id=current3><a href="study.action?module=myelclass_list.action?pN=0&amp;pS=10"><span class="STYLE4">培训班</span></a> </LI>
                          <LI id=current4><a href="study.action?module=listErsWithoutC.action" ><span class="STYLE4">在线考试</span></a></LI>
                          <LI id=current5><A href="forumIndex.action" ><span class="STYLE4">论坛交流</span></A></LI>
                          <LI id=current6><A href="knowledge_center.action" ><span class="STYLE4">知识库</span></A></LI> 
                          <LI id=current7><a href="admin.action?module=talentman"><span class="STYLE4">人才库</span></a></LI>
                          <LI id=current8><a href="admin.action?module=studentman"><span class="STYLE4">个人中心</span></a></LI>
                        </UL></DIV></td>
  </tr>
</table>
<% path=request.getServletPath();
	int path1 = 0;
	if(path.indexOf("index.jsp")>=0) path1=0;
	if(path.indexOf("newsindex.jsp")>=0) path1=1;
	if(path.indexOf("login.jsp")>=0) path1=0;
	if(path.indexOf("courseindex")>=0) path1=2;
	//if(path.indexOf("index")>=0) path1=4;
	if(path.indexOf("forumindex")>=0) path1=5;
	if(path.indexOf("forumListByBlockid")>=0) path1=5;
	if(path.indexOf("forumAdd")>=0) path1=5;
	if(path.indexOf("forumView")>=0) path1=5;
	
	if(path.indexOf("knowledge_center")>=0) path1=6;
	
%>
<script type="text/javascript">
		//function loadBanner(){
			document.getElementById("current"+<%=path1%>).id="current"
			//alert("<%=path%>")
		//}
	</script>