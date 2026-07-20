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
		<TITLE>单位积分排名-学历层次得分详情</TITLE>
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="学历层次得分详情" /></div>
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
						<th width="189" height="30" align="center" bgcolor="#ECEDEB"><strong>学历层次</strong></th> 
						<th width="135" align="center" bgcolor="#ECEDEB">人数</th>
						<th width="762" height="30" align="center" bgcolor="#ECEDEB"><strong> </strong><strong>得分</strong></th>
					</tr>
					<tbody> 
					  <tr>
                        <td height="30" align="center" bgcolor="#ECEDEB">大专以下</td>
					    <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="unitRank.xl_dz_"/></td>
					    <td height="30" align="center" bgcolor="#ECEDEB">人数*0.5＝ <s:property value="unitRank.score_Xl_dz_"/></td>
				      </tr> 
					  <tr>
                        <td height="30" align="center" bgcolor="#ECEDEB">大专</td>
					    <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="unitRank.xl_dz"/></td>
					    <td height="30" align="center" bgcolor="#ECEDEB">人数*1.0＝ <s:property value="unitRank.score_Xl_dz"/></td>
				      </tr>
					  <tr>
                        <td height="30" align="center" bgcolor="#ECEDEB">本科</td>
					    <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="unitRank.xl_bk"/></td>
					    <td height="30" align="center" bgcolor="#ECEDEB">人数*2.0＝ <s:property value="unitRank.score_Xl_bk"/></td>
				      </tr>
					  <tr>
                        <td height="30" align="center" bgcolor="#ECEDEB">硕士</td>
					    <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="unitRank.xl_ss"/></td>
					    <td height="30" align="center" bgcolor="#ECEDEB">人数*3.0＝ <s:property value="unitRank.score_Xl_ss"/></td>
				      </tr>
					  <tr>
                        <td height="30" align="center" bgcolor="#ECEDEB">博士</td>
					    <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="unitRank.xl_bs"/></td>
					    <td height="30" align="center" bgcolor="#ECEDEB">人数*4.0＝ <s:property value="unitRank.score_Xl_bs"/></td>
				      </tr>
					  <tr>
                        <td height="30" align="center" bgcolor="#ECEDEB">合计</td>
					    <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="unitRank.Xl_TOTAL"/></td>
					    <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="unitRank.score_Xl_TOTAL"/></td>
				      </tr>
					</tbody>
			  </table>    
	</div> 
		<!-- 内容 -->
	</BODY>
</HTML>
