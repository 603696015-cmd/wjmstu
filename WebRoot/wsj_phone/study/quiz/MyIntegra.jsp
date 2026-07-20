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
	<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />

		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="wsj_phone/css/system.css" />
		<link rel="stylesheet" type="text/css" href="wsj_phone/css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script></HEAD>
	<BODY>
	
		<!--<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="考场列表" /></div>
			</li>
			<li>
				<span style="font-weight: bold;">考核考试列表</span>
			</li>
		</ul>-->
		<!-- 内容 -->
	<div style="margin-top: 0px; text-align: center; width:320px;"> 
<table width="320" align="center" cellpadding="1" cellspacing="1" bgcolor="#D1E4F5">

					<tr>
						<td height="30" align="center" bgcolor="#F8FCFE"><strong>加分项目</strong></td> 
					  
					  <td height="30" colspan="2" align="center" bgcolor="#F8FCFE"><strong>完成情况</strong></td>
					  <td height="30" align="center" bgcolor="#F8FCFE"><strong>得分</strong></td>
						<td width="30" height="30" align="center" bgcolor="#F8FCFE"><strong>明细</strong></td>
					</tr>
					<tbody> 
						<tr style=" height : 61px;">
                          <td rowspan="2" align="center" bgcolor="#F8FCFE">考试成绩加分</td>
						  
						  <td height="30" align="center" bgcolor="#F8FCFE">已获学分的课程数</td>
						  <td height="30" align="center" bgcolor="#F8FCFE">考试平均分</td>
						  <td rowspan="2" align="center" bgcolor="#F8FCFE"> <s:property value="integra.score_kc_scoresAVG" /> </td>
						  <td rowspan="2" align="center" bgcolor="#F8FCFE">
							  <s:if test="elUser == null">
								<a href="myelclass_view.action?elclass.id=<s:property value="elclass.id" />" onclick="return iselClass('<s:property value="elclass.status" />');" >查看</a>
							  </s:if>
						  </td>
					  </tr>
						<tr>
                          <td height="30" align="center" bgcolor="#F8FCFE"><s:property value="integra.kc_courseXF"/></td>
						  <td height="30" align="center" bgcolor="#F8FCFE"><s:property value="integra.kc_scoresAVG"/></td>
					  </tr>
						<tr>
                          <td rowspan="2" align="center" bgcolor="#F8FCFE">学时加分</td>
						  <td height="30" align="center" bgcolor="#F8FCFE">已完成学时数</td>
						  <td height="30" align="center" bgcolor="#F8FCFE">超过数</td>
						  <td rowspan="2" align="center" bgcolor="#F8FCFE"> <s:property value="integra.score_xs_exceed" /> </td>
						  <td rowspan="2" align="center" bgcolor="#F8FCFE">						  
						  <s:if test="elUser == null">
							<a href="myelclass_view.action?elclass.id=<s:property value="elclass.id" />" onclick="return iselClass('<s:property value="elclass.status" />');"  >查看</a>						 
						  </s:if>
						  </td>
					  </tr>
						<tr>
                          <td height="30" align="center" bgcolor="#F8FCFE"><s:property value="integra.xs_period"/></td>
						  <td height="30" align="center" bgcolor="#F8FCFE"><s:property value="integra.xs_exceed"/></td>
					  </tr>
						<tr>
                          <td rowspan="2" align="center" bgcolor="#F8FCFE">练习加分</td>
						  <td height="30" colspan="2" align="center" bgcolor="#F8FCFE">已做练习的课程数</td>
						  <td rowspan="2" align="center" bgcolor="#F8FCFE"> <s:property value="integra.score_lx_course" /> </td> 
						  <td rowspan="2" align="center" bgcolor="#F8FCFE">
						  	<a href="MyIntegra_LX_MK_BJ_viewInit.action?elclass.id=<s:property value="elclass.id"/><s:if test="elUser != null">&elUser.id=<s:property value="elUser.id"/></s:if>" >查看</a>						  
						  </td>
					  </tr>
						<tr>
                          <td height="30" colspan="2" align="center" bgcolor="#F8FCFE"><s:property value="integra.lx_course"/></td>
					  </tr>
						<tr>
                          <td rowspan="2" align="center" bgcolor="#F8FCFE">模考加分</td>
						  <td height="30" colspan="2" align="center" bgcolor="#F8FCFE">已做模考的课程数</td>
						  <td rowspan="2" align="center" bgcolor="#F8FCFE">  <s:property value="integra.score_mk_Model" /> </td>
						  <td rowspan="2" align="center" bgcolor="#F8FCFE">
						  <s:if test="elUser == null">
						  	<a href="MyIntegra_LX_MK_BJ_viewInit.action?elclass.id=<s:property value="elclass.id"/>" >查看</a>						  
						  </s:if>
						  </td>
					  </tr>
						<tr>
                          <td height="30" colspan="2" align="center" bgcolor="#F8FCFE"><s:property value="integra.mk_Model"/></td>
					  </tr>
						<tr>
                          <td rowspan="2" align="center" bgcolor="#F8FCFE">学分加分</td>
						  <td height="30" align="center" bgcolor="#F8FCFE">已获学分数</td>
						  <td height="30" align="center" bgcolor="#F8FCFE">超出数</td>
						  <td rowspan="2" align="center" bgcolor="#F8FCFE"> <s:property value="integra.score_xf_beyond" /> </td>
						  <td rowspan="2" align="center" bgcolor="#F8FCFE">
						  <s:if test="elUser == null">
							<a href="myelclass_view.action?elclass.id=<s:property value="elclass.id" />" onclick="return iselClass('<s:property value="elclass.status" />');"  >查看</a>						  
							</s:if>
						  </td>
					  </tr>
						<tr>
                          <td height="30" align="center" bgcolor="#F8FCFE"><s:property value="integra.xf_credits"/></td>
						  <td height="30" align="center" bgcolor="#F8FCFE"><s:property value="integra.xf_beyond"/></td>
					  </tr>
						<tr>
                          <td rowspan="2" align="center" bgcolor="#F8FCFE">笔记得分</td>
						  <td height="30" colspan="2" align="center" bgcolor="#F8FCFE">已做笔记的课程数</td>
						  <td rowspan="2" align="center" bgcolor="#F8FCFE"> <s:property value="integra.score_bj_course" /> </td>
						  <td rowspan="2" align="center" bgcolor="#F8FCFE">
						  	<a href="MyIntegra_LX_MK_BJ_viewInit.action?elclass.id=<s:property value="elclass.id"/><s:if test="elUser != null">&elUser.id=<s:property value="elUser.id"/></s:if>" >查看</a>						  
						  </td>
					  </tr>
						<tr>
						  <td height="30" colspan="2" align="center" bgcolor="#F8FCFE"><s:property value="integra.bj_course"/></td>
					  </tr>
						<tr>
                          <td rowspan="2" align="center" bgcolor="#F8FCFE">上传得分</td>
						  
						  <td height="30" align="center" bgcolor="#F8FCFE">已发布文章数</td>
						  <td height="30" align="center" bgcolor="#F8FCFE">已审核文章数</td>
						  <td rowspan="2" align="center" bgcolor="#F8FCFE"> <s:property value="integra.score_sc_audit" /> </td>
						  <td rowspan="2" align="center" bgcolor="#F8FCFE">
						  	<a href="MyIntegra_knowledge_viewInit.action<s:if test="elUser != null">?elUser.id=<s:property value="elUser.id"/></s:if>" >查看</a>						  
						  </td>
					  </tr>
						<tr>
						  <td height="30" align="center" bgcolor="#F8FCFE"><s:property value="integra.sc_release"/></td>
						  <td height="30" align="center" bgcolor="#F8FCFE"><s:property value="integra.sc_audit"/></td>
					  </tr>
					  <tr>
                          <td rowspan="2" align="center" bgcolor="#F8FCFE">被推荐得分</td>
					    <td height="30" colspan="2" align="center" bgcolor="#F8FCFE">被推荐的文章数</td>
				      <td rowspan="2" align="center" bgcolor="#F8FCFE"><s:property value="integra.score_btj_article" /></td>
						  <td rowspan="2" align="center" bgcolor="#F8FCFE">
						  	<a href="MyIntegra_knowledge_viewInit.action<s:if test="elUser != null">?elUser.id=<s:property value="elUser.id"/></s:if>" >查看</a>						  
						  </td>
					  </tr>
					  <tr>
					    <td height="30" colspan="2" align="center" bgcolor="#F8FCFE"><s:property value="integra.btj_article"/></td>
					  </tr>
					  <tr>
                          <td rowspan="2" align="center" bgcolor="#F8FCFE">被下载得分</td>
					    <td height="30" align="center" bgcolor="#F8FCFE">已审核文章数</td>
					    <td height="30" align="center" bgcolor="#F8FCFE">下载人次</td>
				      <td rowspan="2" align="center" bgcolor="#F8FCFE"> <s:property value="integra.score_bxz_people" /> </td>
						  <td rowspan="2" align="center" bgcolor="#F8FCFE">
					  	<a href="MyIntegra_BXZ_viewInit.action<s:if test="elUser != null">?elUser.id=<s:property value="elUser.id"/></s:if>" >查看</a>						  </td> 
					  </tr>
					  <tr>
					    <td height="30" align="center" bgcolor="#F8FCFE"><s:property value="integra.bxz_audit"/></td>
					    <td height="30" align="center" bgcolor="#F8FCFE"><s:property value="integra.bxz_people"/></td>
					  </tr>
					  <tr>
                          <td rowspan="2" align="center" bgcolor="#F8FCFE">下载得分</td>
					    <td height="30" colspan="2" align="center" bgcolor="#F8FCFE">下载文章数</td>
				      <td rowspan="2" align="center" bgcolor="#F8FCFE"> <s:property value="integra.score_xz_audit" /> </td>
						  <td rowspan="2" align="center" bgcolor="#F8FCFE">
					  	<a href="MyIntegra_XZ_viewInit.action<s:if test="elUser != null">?elUser.id=<s:property value="elUser.id"/></s:if>" >查看</a>						  </td>
					  </tr>
					  <tr>
					    <td height="30" colspan="2" align="center" bgcolor="#F8FCFE"><s:property value="integra.xz_audit"/></td>
					  </tr>
					  <tr>
                          <td rowspan="2" align="center" bgcolor="#F8FCFE">发帖得分</td>
					    <td height="30" align="center" bgcolor="#F8FCFE">发帖数</td>
					    <td height="30" align="center" bgcolor="#F8FCFE">通过数</td>
				      <td rowspan="2" align="center" bgcolor="#F8FCFE"> <s:property value="integra.score_ft_pass" /> </td>
						  <td rowspan="2" align="center" bgcolor="#F8FCFE">
						  	<a href="MyIntegra_FTJH_viewInit.action<s:if test="elUser != null">?elUser.id=<s:property value="elUser.id"/></s:if>" >查看</a>						  
						  </td>
					  </tr>
					  <tr>
					    <td height="30" align="center" bgcolor="#F8FCFE"><s:property value="integra.ft_post"/></td>
					    <td height="30" align="center" bgcolor="#F8FCFE"><s:property value="integra.ft_pass"/></td>
					  </tr>
					  <tr>
                          <td rowspan="2" align="center" bgcolor="#F8FCFE">发言得分</td>
					    <td height="30" colspan="2" align="center" bgcolor="#F8FCFE">发言次数</td>
				      <td rowspan="2" align="center" bgcolor="#F8FCFE"> <s:property value="integra.score_fy_speech" /> </td>
						  <td rowspan="2" align="center" bgcolor="#F8FCFE">
						  	<a href="displayMyTopicList.action<s:if test="elUser != null">?elUser.id=<s:property value="elUser.id"/></s:if>" >查看</a>						  
						  </td>
					  </tr>
					  <tr>
					    <td height="30" colspan="2" align="center" bgcolor="#F8FCFE"><s:property value="integra.fy_speech"/></td>
					  </tr>
					  <tr>
                        <td rowspan="2" align="center" bgcolor="#F8FCFE">精华帖得分</td>
					    <td height="30" colspan="2" align="center" bgcolor="#F8FCFE">精华帖数量</td>
				      <td rowspan="2" align="center" bgcolor="#F8FCFE"> <s:property value="integra.score_jh_jht" /> </td>
						  <td rowspan="2" align="center" bgcolor="#F8FCFE">
					  	<a href="MyIntegra_FTJH_viewInit.action<s:if test="elUser != null">?elUser.id=<s:property value="elUser.id"/></s:if>" >查看</a>						  </td>
				      </tr>
					  <tr>
					    <td height="30" colspan="2" align="center" bgcolor="#F8FCFE"><s:property value="integra.jh_jht"/></td>
				      </tr>
					  <tr>
                        <td rowspan="2" align="center" bgcolor="#F8FCFE">登陆加分</td>
					    <td height="30" colspan="2" align="center" bgcolor="#F8FCFE">登陆次数</td>
				      <td height="30" align="center" bgcolor="#F8FCFE"> <s:property value="integra.score_dl_login" /> </td>
						  <td rowspan="2" align="center" bgcolor="#F8FCFE">
					  	<a href="MyIntegra_Dl_viewInit.action<s:if test="elUser != null">?elUser.id=<s:property value="elUser.id"/></s:if>" >查看</a>						  </td>
				      </tr>
					  <tr>
					    <td height="30" colspan="2" align="center" bgcolor="#F8FCFE"><s:property value="integra.dl_login"/></td>
					    <td height="30" align="center" bgcolor="#F8FCFE"></td>
				      </tr> 
					  <tr>
                        <td height="30" align="center" bgcolor="#F8FCFE">合计</td>
					    
					    <td height="30" align="center" bgcolor="#F8FCFE"></td>
					    <td height="30" align="center" bgcolor="#F8FCFE"></td>
					    <td height="30" align="center" bgcolor="#F8FCFE"></td>
					    <td height="30" align="center" bgcolor="#F8FCFE"></td>
				      </tr>
					</tbody>
			  </table>    
</div> 
		<!-- 内容 -->
	
	</body>
</HTML>
