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
					<caption>本年度登陆加分总数为： <s:property value="integra.score_dl_login"/>分。明细如下：
					</caption>
					<tr>
						<th width="276" height="30" align="center" bgcolor="#ECEDEB"><strong>登陆时间</strong></th> 
						<th width="122" height="30" align="center" bgcolor="#ECEDEB"><strong>退出时间</strong></th> 
						<th width="122" height="30" align="center" bgcolor="#ECEDEB"><strong>加分</strong></th> 
					</tr>
					<tbody>  
						<s:iterator value="myLogins">
						  <tr>
	                        <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="logintime"/></td>
						    <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="exittime"/></td> 
						    <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="score"/></td> 
					      </tr>
						</s:iterator>
					</tbody>
			  </table>    
			  <form action="MyIntegra_Dl_viewInit.action" method="post" name="klform"> 
				<s:hidden name="pN" id="pageNow"></s:hidden>
				<s:hidden name="pS"></s:hidden>
			  </form>
			<script>
			 	function page(i){
			 		klform.action="MyIntegra_Dl_viewInit.action";
			 		document.getElementById("pageNow").value=i;
			 		klform.submit();
			 	}
		 	</script>
			<wysLib:page></wysLib:page>
	</div> 
		<!-- 内容 -->
	</BODY>
</HTML>
