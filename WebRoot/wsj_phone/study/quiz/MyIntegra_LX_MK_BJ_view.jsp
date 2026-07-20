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
					<caption>练习、模考、笔记加分明细：
					</caption>
					<tr>
						<th width="276" height="30" align="center" bgcolor="#ECEDEB"><strong>课程名称</strong></th> 
						<th width="122" height="30" align="center" bgcolor="#ECEDEB"><strong>是否做练习</strong></th> 
						<th width="122" height="30" align="center" bgcolor="#ECEDEB"><strong>得分</strong></th> 
						<th width="122" height="30" align="center" bgcolor="#ECEDEB"><strong>是否做模考</strong></th> 
						<th width="122" height="30" align="center" bgcolor="#ECEDEB"><strong>得分</strong></th> 
						<th width="122" height="30" align="center" bgcolor="#ECEDEB"><strong>是否做笔记</strong></th> 
						<th width="122" height="30" align="center" bgcolor="#ECEDEB"><strong>得分</strong></th> 
					</tr>
					<tbody>  
						<s:iterator value="courses">
						  <tr>
	                        <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="name"/></td>
						    <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="IsLXName"/></td> 
	                        <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="LX_score"/></td>
						    <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="IsMKName"/></td> 
	                        <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="MK_score"/></td>
						    <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="IsBJName"/></td> 
	                        <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="BJ_score"/></td> 
					      </tr>
						</s:iterator>
					</tbody>
			  </table>     
			  <br/>
			  <br/>
	</div> 
		<!-- 内容 -->
	
	</body>
</HTML>
