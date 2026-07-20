<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<base href="<%=basePath%>">
		<TITLE>五矿发展员工职业发展系统--管理端--学员显示</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">我的点数</span>
			</li>-->
		</ul>

		<div style="margin-top: 0px;">
			<table width="60%" cellpadding="1" cellspacing="1" >
				<tr>
						<td align="center" >
							<strong>得分原因 </strong>
						</td>
						<td >
							<strong>单次得分 </strong>	
						</td>
						<td align="center" >
							<strong>得分次数 </strong>
						</td>
							<td align="center" >
							<strong>	总得分 </strong>
						</td>
				</tr>
				<tr>
						<td align="center" >
							<strong>登陆一次将励 </strong>
						</td>
						<td >
							<s:property value="elUser.scoreset.dian_login_do"/>
						</td>
						<td >
							<s:property value="elUser.scoreset.dian_login_do_m/elUser.scoreset.dian_login_do"/>
						</td>
						<td >
							<s:property value="elUser.scoreset.dian_login_do_m"/>
						</td>
					</tr>
					<tr>
						<td align="center" >
							<strong>发帖 </strong>
						</td>
						<td >
							<s:property value="elUser.scoreset.dian_forum_do"/>
						</td>
						<td >
							<s:property value="elUser.scoreset.dian_forum_do_m/elUser.scoreset.dian_forum_do"/>
						</td>
						<td >
							<s:property value="elUser.scoreset.dian_forum_do_m"/>
						</td>
					</tr>
					<tr>
						<td align="center" >
							<strong>回帖 </strong>
						</td>
						<td >
							<s:property value="elUser.scoreset.dian_topic_do"/>
						</td>
						<td >
							<s:property value="elUser.scoreset.dian_topic_do_m/elUser.scoreset.dian_topic_do"/>
						</td>
						<td >
							<s:property value="elUser.scoreset.dian_topic_do_m"/>
						</td>
					</tr>
					<tr>
						<td align="center" >
							<strong>学习次数 </strong>
						</td>
						<td >
							<s:property value="elUser.scoreset.dian_study_do"/>
						</td>
							<td >
							<s:property value="elUser.scoreset.dian_study_do_m/elUser.scoreset.dian_study_do"/>
						</td>
							<td >
							<s:property value="elUser.scoreset.dian_study_do_m"/>
						</td>
					</tr>
					<tr>
						<td align="center" >
							<strong>学习时长 </strong>
						</td>
						<td >
							<s:property value="elUser.scoreset.dian_study_cp_do"/>
						</td>
						<td >
							<s:property value="elUser.scoreset.dian_study_cp_do_m/elUser.scoreset.dian_study_cp_do"/>
						</td>
						<td >
							<s:property value="elUser.scoreset.dian_study_cp_do_m"/>
						</td>
					</tr>
					<tr>
						<td align="center" >
							<strong>一篇帖子被删除，扣 </strong>
						</td>
						<td >
							<s:property value="elUser.scoreset.jian_forum_do"/>
						</td>
							<td >
							<s:property value="elUser.scoreset.jian_forum_do_m/elUser.scoreset.jian_forum_do"/>
						</td>
							<td >
							<s:property value="elUser.scoreset.jian_forum_do_m"/>
						</td>
					</tr>
					<tr>
						<td align="center" >
							<strong>一篇知识文章被删除，扣</strong>
						</td>
						<td >
							<s:property value="elUser.scoreset.jian_knowledge_do"/>
						</td>
						<td >
							<s:property value="elUser.scoreset.jian_knowledge_do_m/elUser.scoreset.jian_knowledge_do"/>
						</td>
						<td >
							<s:property value="elUser.scoreset.jian_knowledge_do_m"/>
						</td>
					</tr>
					<tr>
						<td align="center" >
							<strong>长时间不登陆：每隔（48）小时不登陆，扣</strong>
						</td>
						<td >
							<s:property value="elUser.scoreset.jian_login_do"/>
						</td>
						<td >
							<s:property value="elUser.scoreset.jian_login_do_m/elUser.scoreset.jian_login_do"/>
						</td>
						<td >
							<s:property value="elUser.scoreset.jian_login_do_m"/>
						</td>
					</tr>
					<tr>
						<td align="center" >
							<strong>每被暂停一次考试，扣（20）</strong>
						</td>
						<td >
							<s:property value="elUser.scoreset.jian_ep_zhanting"/>
						</td>
						<td >
							<s:property value="elUser.scoreset.jian_ep_zhanting_m/elUser.scoreset.jian_ep_zhanting"/>
						</td>
						<td >
							<s:property value="elUser.scoreset.jian_ep_zhanting_m"/>
						</td>
					</tr>
					<tr>
						<td align="center" >
							<strong>每被强制交卷一次，扣（50）</strong>
						</td>
						<td >
							<s:property value="elUser.scoreset.jian_ep_qiangzhi"/>
						</td>
						<td >
							<s:property value="elUser.scoreset.jian_ep_qiangzhi_m/elUser.scoreset.jian_ep_qiangzhi"/>
						</td>
						<td >
							<s:property value="elUser.scoreset.jian_ep_qiangzhi_m"/>
						</td>
					</tr>
					<tr>
						<td align="center" >
							<strong>总分</strong>
						</td>
						<td bgcolor="#FFFFFF" colspan="2">
						</td>
						<td >
							<s:property value="elUser.scoreset.dian_forum_do_m+elUser.scoreset.dian_login_do_m+elUser.scoreset.dian_study_cp_do_m+elUser.scoreset.dian_study_do_m+elUser.scoreset.dian_topic_do_m+elUser.scoreset.jian_ep_qiangzhi_m+elUser.scoreset.jian_ep_zhanting_m+elUser.scoreset.jian_forum_do_m+elUser.scoreset.jian_knowledge_do_m+elUser.scoreset.jian_login_do_m"/>
						</td>
					</tr>
			</table>
		</div>
	</BODY>
</HTML>
