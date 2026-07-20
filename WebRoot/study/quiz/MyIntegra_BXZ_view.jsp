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
		<TITLE>积分管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script></HEAD>
	<BODY>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="知识库积分查看" /></div>
			</li> 
		</ul>
		<!-- 内容 -->
	<div style="margin-top: 20px; text-align: center;"> 
<table width="100%" align="center" cellspacing="1" cellpadding="1">
					<caption>&nbsp;
					</caption>
					<tr>
						<th width="276" height="30" align="center" bgcolor="#ECEDEB"><strong>我发布的文章名称</strong></th> 
						<th width="122" height="30" align="center" bgcolor="#ECEDEB"><strong>被下载人次</strong></th>
						<th width="150" height="30" align="center" bgcolor="#ECEDEB"><strong>加分</strong></th>
						<th width="150" height="30" align="center" bgcolor="#ECEDEB"><strong></strong></th> 
					</tr>
					<tbody>  
						<s:iterator value="knowledges">
						  <tr>
	                        <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="title"/></td>
						    <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="counts"/></td>
						    <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="scoreF"/></td>
						    <td height="30" align="center" bgcolor="#ECEDEB">
						    	<a href="MyIntegra_BXZ_XQ_viewInit.action?knowledge.id=<s:property value="id"/>" class="textbg5">详情</a>
						    </td> 
					      </tr>
						</s:iterator>
					</tbody>
			  </table>    
			  <form action="MyIntegra_BXZ_viewInit.action" method="post" name="klform"> 
				<s:hidden name="pN" id="pageNow"></s:hidden>
				<s:hidden name="pS"></s:hidden>
			  </form>
			<script>
			 	function page(i){
			 		klform.action="MyIntegra_BXZ_viewInit.action";
			 		document.getElementById("pageNow").value=i;
			 		klform.submit();
			 	}
		 	</script>
			<wysLib:page></wysLib:page>
	</div> 
		<!-- 内容 -->
	</BODY>
</HTML>
