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
		<TITLE>课程类别管理</TITLE>
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="考场列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">考核考试列表</span>
			</li>-->
		</ul>
		<!-- 内容 -->
	<div style="margin-top: 20px; text-align: center;"> 
<table width="99%" align="center" cellspacing="1" cellpadding="1">
					<caption>&nbsp;
					</caption>
					<tr>
						<th width="189" height="30" align="center" bgcolor="#ECEDEB"><strong>职业级别</strong></th> 
						<th width="135" align="center" bgcolor="#ECEDEB">人数</th>
						<th width="762" height="30" align="center" bgcolor="#ECEDEB"><strong> </strong><strong>得分</strong></th>
					</tr>
					<tbody> 
					  <tr>
                        <td height="30" align="center" bgcolor="#ECEDEB">无职称</td>
					    <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="unitRank.zc_w"/></td>
					    <td height="30" align="center" bgcolor="#ECEDEB">人数*0.0＝ <s:property value="unitRank.score_Zc_w"/></td>
				      </tr> 
					  <tr>
                        <td height="30" align="center" bgcolor="#ECEDEB">初级</td>
					    <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="unitRank.zc_cj"/></td>
					    <td height="30" align="center" bgcolor="#ECEDEB">人数*1.0＝ <s:property value="unitRank.score_Zc_cj"/></td>
				      </tr>
					  <tr>
                        <td height="30" align="center" bgcolor="#ECEDEB">中级</td>
					    <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="unitRank.zc_zj"/></td>
					    <td height="30" align="center" bgcolor="#ECEDEB">人数*2.0＝ <s:property value="unitRank.score_Zc_zj"/></td>
				      </tr>
					  <tr>
                        <td height="30" align="center" bgcolor="#ECEDEB">高级</td>
					    <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="unitRank.zc_gj"/></td>
					    <td height="30" align="center" bgcolor="#ECEDEB">人数*4.0＝ <s:property value="unitRank.score_Zc_gj"/></td>
				      </tr> 
					  <tr>
                        <td height="30" align="center" bgcolor="#ECEDEB">合计</td>
					    <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="unitRank.zc_TOTAL"/></td>
					    <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="unitRank.score_Zc_TOTAL"/></td>
				      </tr>
					</tbody>
			  </table>    
	</div> 
		<!-- 内容 -->
	</BODY>
</HTML>
