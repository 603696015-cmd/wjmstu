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
	<div style="margin-top: 0px; text-align: center;"> 
<table width="99%" align="center" cellspacing="1" cellpadding="1">
					<caption>&nbsp;
					</caption>
					<tr>
						<th width="209" height="30" align="center" bgcolor="#ECEDEB"><strong>加分项目</strong></th> 
						<th width="149" height="30" align="center" bgcolor="#ECEDEB"><strong>加分前提</strong></th>
						<th height="30" colspan="2" align="center" bgcolor="#ECEDEB"><strong>完成情况</strong></th>
						<th width="186" height="30" align="center" bgcolor="#ECEDEB"><strong>得分</strong></th>
						<th width="89" height="30" align="center" bgcolor="#ECEDEB"><strong>明细</strong></th>
					</tr>
					<tbody> 
						<tr style=" height : 61px;">
                          <td rowspan="2" align="center" bgcolor="#ECEDEB">考试成绩加分</td>
						  <td rowspan="12" align="center" bgcolor="#ECEDEB">
						  	获取得证书 <br/>
						  	<s:if test="status == 2">
						  		<span style="color:red">(已获得)</span>						  	</s:if><s:else>
							  	<span style="color:red">(未获得)</span>
						  	</s:else>						  </td>
						  <td width="243" height="30" align="center" bgcolor="#ECEDEB">已获学分的课程数</td>
						  <td width="184" height="30" align="center" bgcolor="#ECEDEB">考试平均分</td>
						  <td rowspan="2" align="center" bgcolor="#ECEDEB"> <s:property value="integra.score_kc_scoresAVG" /> </td>
						  <td rowspan="2" align="center" bgcolor="#ECEDEB">
							  <s:if test="elUser == null">
								<a href="myelclass_view.action?elclass.id=<s:property value="elclass.id" />" onclick="return iselClass('<s:property value="elclass.status" />');"  class="textbg5">查看</a>
							  </s:if>
						  </td>
					  </tr>
						<tr>
                          <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="integra.kc_courseXF"/></td>
						  <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="integra.kc_scoresAVG"/></td>
					  </tr>
						<tr>
                          <td rowspan="2" align="center" bgcolor="#ECEDEB">学时加分</td>
						  <td height="30" align="center" bgcolor="#ECEDEB">已完成学时数</td>
						  <td height="30" align="center" bgcolor="#ECEDEB">超过数</td>
						  <td rowspan="2" align="center" bgcolor="#ECEDEB"> <s:property value="integra.score_xs_exceed" /> </td>
						  <td rowspan="2" align="center" bgcolor="#ECEDEB">						  
						  <s:if test="elUser == null">
							<a href="myelclass_view.action?elclass.id=<s:property value="elclass.id" />" onclick="return iselClass('<s:property value="elclass.status" />');"  class="textbg5">查看</a>						 
						  </s:if>
						  </td>
					  </tr>
						<tr>
                          <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="integra.xs_period"/></td>
						  <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="integra.xs_exceed"/></td>
					  </tr>
						<tr>
                          <td rowspan="2" align="center" bgcolor="#ECEDEB">练习加分</td>
						  <td height="30" colspan="2" align="center" bgcolor="#ECEDEB">已做练习的课程数</td>
						  <td rowspan="2" align="center" bgcolor="#ECEDEB"> <s:property value="integra.score_lx_course" /> </td> 
						  <td rowspan="2" align="center" bgcolor="#ECEDEB">
						  	<a href="MyIntegra_LX_MK_BJ_viewInit.action?elclass.id=<s:property value="elclass.id"/><s:if test="elUser != null">&elUser.id=<s:property value="elUser.id"/></s:if>" class="textbg5">查看</a>						  
						  </td>
					  </tr>
						<tr>
                          <td height="30" colspan="2" align="center" bgcolor="#ECEDEB"><s:property value="integra.lx_course"/></td>
					  </tr>
						<tr>
                          <td rowspan="2" align="center" bgcolor="#ECEDEB">模考加分</td>
						  <td height="30" colspan="2" align="center" bgcolor="#ECEDEB">已做模考的课程数</td>
						  <td rowspan="2" align="center" bgcolor="#ECEDEB">  <s:property value="integra.score_mk_Model" /> </td>
						  <td rowspan="2" align="center" bgcolor="#ECEDEB">
						  <s:if test="elUser == null">
						  	<a href="MyIntegra_LX_MK_BJ_viewInit.action?elclass.id=<s:property value="elclass.id"/>" class="textbg5">查看</a>						  
						  </s:if>
						  </td>
					  </tr>
						<tr>
                          <td height="30" colspan="2" align="center" bgcolor="#ECEDEB"><s:property value="integra.mk_Model"/></td>
					  </tr>
						<tr>
                          <td rowspan="2" align="center" bgcolor="#ECEDEB">学分加分</td>
						  <td height="30" align="center" bgcolor="#ECEDEB">已获学分数</td>
						  <td height="30" align="center" bgcolor="#ECEDEB">超出数</td>
						  <td rowspan="2" align="center" bgcolor="#ECEDEB"> <s:property value="integra.score_xf_beyond" /> </td>
						  <td rowspan="2" align="center" bgcolor="#ECEDEB">
						  <s:if test="elUser == null">
							<a href="myelclass_view.action?elclass.id=<s:property value="elclass.id" />" onclick="return iselClass('<s:property value="elclass.status" />');"  class="textbg5">查看</a>						  
							</s:if>
						  </td>
					  </tr>
						<tr>
                          <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="integra.xf_credits"/></td>
						  <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="integra.xf_beyond"/></td>
					  </tr>
						<tr>
                          <td rowspan="2" align="center" bgcolor="#ECEDEB">笔记得分</td>
						  <td height="30" colspan="2" align="center" bgcolor="#ECEDEB">已做笔记的课程数</td>
						  <td rowspan="2" align="center" bgcolor="#ECEDEB"> <s:property value="integra.score_bj_course" /> </td>
						  <td rowspan="2" align="center" bgcolor="#ECEDEB">
						  	<a href="MyIntegra_LX_MK_BJ_viewInit.action?elclass.id=<s:property value="elclass.id"/><s:if test="elUser != null">&elUser.id=<s:property value="elUser.id"/></s:if>" class="textbg5">查看</a>						  
						  </td>
					  </tr>
						<tr>
						  <td height="30" colspan="2" align="center" bgcolor="#ECEDEB"><s:property value="integra.bj_course"/></td>
					  </tr>
						<tr>
                          <td rowspan="2" align="center" bgcolor="#ECEDEB">上传得分</td>
						  <td rowspan="16" align="center" bgcolor="#ECEDEB">本年度</td>
						  <td height="30" align="center" bgcolor="#ECEDEB">已发布文章数</td>
						  <td height="30" align="center" bgcolor="#ECEDEB">已审核文章数</td>
						  <td rowspan="2" align="center" bgcolor="#ECEDEB"> <s:property value="integra.score_sc_audit" /> </td>
						  <td rowspan="2" align="center" bgcolor="#ECEDEB">
						  	<a href="MyIntegra_knowledge_viewInit.action<s:if test="elUser != null">?elUser.id=<s:property value="elUser.id"/></s:if>" class="textbg5">查看</a>						  
						  </td>
					  </tr>
						<tr>
						  <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="integra.sc_release"/></td>
						  <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="integra.sc_audit"/></td>
					  </tr>
					  <tr>
                          <td rowspan="2" align="center" bgcolor="#ECEDEB">被推荐得分</td>
						  <td height="30" colspan="2" align="center" bgcolor="#ECEDEB">被推荐的文章数</td>
						  <td rowspan="2" align="center" bgcolor="#ECEDEB"><s:property value="integra.score_btj_article" /></td>
						  <td rowspan="2" align="center" bgcolor="#ECEDEB">
						  	<a href="MyIntegra_knowledge_viewInit.action<s:if test="elUser != null">?elUser.id=<s:property value="elUser.id"/></s:if>" class="textbg5">查看</a>						  
						  </td>
					  </tr>
					  <tr>
					    <td height="30" colspan="2" align="center" bgcolor="#ECEDEB"><s:property value="integra.btj_article"/></td>
					  </tr>
					  <tr>
                          <td rowspan="2" align="center" bgcolor="#ECEDEB">被下载得分</td>
						  <td height="30" align="center" bgcolor="#ECEDEB">已审核文章数</td>
						  <td height="30" align="center" bgcolor="#ECEDEB">下载人次</td>
						  <td rowspan="2" align="center" bgcolor="#ECEDEB"> <s:property value="integra.score_bxz_people" /> </td>
						  <td rowspan="2" align="center" bgcolor="#ECEDEB">
						  	<a href="MyIntegra_BXZ_viewInit.action<s:if test="elUser != null">?elUser.id=<s:property value="elUser.id"/></s:if>" class="textbg5">查看</a>						  </td> 
					  </tr>
					  <tr>
					    <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="integra.bxz_audit"/></td>
						  <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="integra.bxz_people"/></td>
					  </tr>
					  <tr>
                          <td rowspan="2" align="center" bgcolor="#ECEDEB">下载得分</td>
						  <td height="30" colspan="2" align="center" bgcolor="#ECEDEB">下载文章数</td>
						  <td rowspan="2" align="center" bgcolor="#ECEDEB"> <s:property value="integra.score_xz_audit" /> </td>
						  <td rowspan="2" align="center" bgcolor="#ECEDEB">
						  	<a href="MyIntegra_XZ_viewInit.action<s:if test="elUser != null">?elUser.id=<s:property value="elUser.id"/></s:if>" class="textbg5">查看</a>						  </td>
					  </tr>
					  <tr>
					    <td height="30" colspan="2" align="center" bgcolor="#ECEDEB"><s:property value="integra.xz_audit"/></td>
					  </tr>
					  <tr>
                          <td rowspan="2" align="center" bgcolor="#ECEDEB">发帖得分</td>
						  <td height="30" align="center" bgcolor="#ECEDEB">发帖数</td>
						  <td height="30" align="center" bgcolor="#ECEDEB">通过数</td>
						  <td rowspan="2" align="center" bgcolor="#ECEDEB"> <s:property value="integra.score_ft_pass" /> </td>
						  <td rowspan="2" align="center" bgcolor="#ECEDEB">
						  	<a href="MyIntegra_FTJH_viewInit.action<s:if test="elUser != null">?elUser.id=<s:property value="elUser.id"/></s:if>" class="textbg5">查看</a>						  
						  </td>
					  </tr>
					  <tr>
					    <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="integra.ft_post"/></td>
						  <td height="30" align="center" bgcolor="#ECEDEB"><s:property value="integra.ft_pass"/></td>
					  </tr>
					  <tr>
                          <td rowspan="2" align="center" bgcolor="#ECEDEB">发言得分</td>
						  <td height="30" colspan="2" align="center" bgcolor="#ECEDEB">发言次数</td>
						  <td rowspan="2" align="center" bgcolor="#ECEDEB"> <s:property value="integra.score_fy_speech" /> </td>
						  <td rowspan="2" align="center" bgcolor="#ECEDEB">
						  	<a href="displayMyTopicList.action<s:if test="elUser != null">?elUser.id=<s:property value="elUser.id"/></s:if>" class="textbg5">查看</a>						  
						  </td>
					  </tr>
					  <tr>
					    <td height="30" colspan="2" align="center" bgcolor="#ECEDEB"><s:property value="integra.fy_speech"/></td>
					  </tr>
					  <tr>
                        <td rowspan="2" align="center" bgcolor="#ECEDEB">精华帖得分</td>
					    <td height="30" colspan="2" align="center" bgcolor="#ECEDEB">精华帖数量</td>
					    <td rowspan="2" align="center" bgcolor="#ECEDEB"> <s:property value="integra.score_jh_jht" /> </td>
						  <td rowspan="2" align="center" bgcolor="#ECEDEB">
						  	<a href="MyIntegra_FTJH_viewInit.action<s:if test="elUser != null">?elUser.id=<s:property value="elUser.id"/></s:if>" class="textbg5">查看</a>						  </td>
				      </tr>
					  <tr>
					    <td height="30" colspan="2" align="center" bgcolor="#ECEDEB"><s:property value="integra.jh_jht"/></td>
				      </tr>
					  <tr>
                        <td rowspan="2" align="center" bgcolor="#ECEDEB">登陆加分</td>
					    <td height="30" colspan="2" align="center" bgcolor="#ECEDEB">登陆次数</td>
					    <td height="30" align="center" bgcolor="#ECEDEB"> <s:property value="integra.score_dl_login" /> </td>
						  <td rowspan="2" align="center" bgcolor="#ECEDEB">
						  	<a href="MyIntegra_Dl_viewInit.action<s:if test="elUser != null">?elUser.id=<s:property value="elUser.id"/></s:if>" class="textbg5">查看</a>						  </td>
				      </tr>
					  <tr>
					    <td height="30" colspan="2" align="center" bgcolor="#ECEDEB"><s:property value="integra.dl_login"/></td>
					    <td height="30" align="center" bgcolor="#ECEDEB"></td>
				      </tr> 
					  <tr>
                        <td height="30" align="center" bgcolor="#ECEDEB">合计</td>
					    <td height="30" align="center" bgcolor="#ECEDEB"></td>
					    <td height="30" align="center" bgcolor="#ECEDEB"></td>
					    <td height="30" align="center" bgcolor="#ECEDEB"></td>
					    <td height="30" align="center" bgcolor="#ECEDEB"></td>
					    <td height="30" align="center" bgcolor="#ECEDEB"></td>
				      </tr>
					</tbody>
			  </table>    
	</div> 
		<!-- 内容 -->
	</BODY>
</HTML>
