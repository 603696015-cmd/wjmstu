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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<TITLE>中国食品安全培训网--管理端--学员显示</TITLE>
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="查看点数详情" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">点数详情</span>
			</li>-->
		</ul>

		<div style="margin-top: 0px;">
			<table width="60%" cellpadding="1" cellspacing="1" >
				<tr>
					<td width="120" align="center" >
						<strong>所属单位</strong>
					</td>
					<td align="left" >
						<label>
							<s:property value="elUser.company.name" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" align="center" >
						<strong>所属部门</strong>
					</td>
					<td align="left" >
						<label>
							<s:property value="elUser.department.name" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" align="center" >
						<strong>学号</strong>
					</td>
					<td align="left" >
						<label>

							<s:property value="elUser.username" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" align="center" >
						<strong>姓 名</strong>
					</td>
					<td align="left" >
						<label>
							<s:property value="elUser.realname" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" align="center" >
						<strong>密 码</strong>
					</td>
					<td align="left" >
						<label>
							<s:property value="elUser.password" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" align="center" >
						<strong>编 号</strong>
					</td>
					<td align="left" >
						<label>
							<s:property value="elUser.userno" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" align="center" >
						<strong>角色</strong>
					</td>
					<td align="left" >
						<label>
							<s:property value="elUser.role.name" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" align="center" >
						<strong>联系电话</strong>
					</td>
					<td align="left" >
						<label>
							<s:property value="elUser.phone" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" align="center" >
						<strong>地 址</strong>
					</td>
					<td align="left" >
						<label>
							<s:property value="elUser.address" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" align="center" >
						<strong>电子邮箱</strong>
					</td>
					<td align="left" >
						<label>
							<s:property value="elUser.email" />
						</label>
					</td>
				</tr>
				<tr>
					<td colspan="2" bgcolor="#dddffe">
						点数信息
					</td>
				</tr>
				<tr>
						<td align="center" >
							<strong>登录一次将励 </strong>
						</td>
						<td bgcolor="#FFFFFF" width="200">
							<s:property value="elUser.scoreset.dian_login_do"/>
						</td>
					</tr>
					<tr>
						<td align="center" >
							<strong>发帖 </strong>
						</td>
						<td bgcolor="#FFFFFF" width="200">
							<s:property value="elUser.scoreset.dian_forum_do"/>
						</td>
					</tr>
					<tr>
						<td align="center" >
							<strong>回帖 </strong>
						</td>
						<td bgcolor="#FFFFFF" width="200">
							<s:property value="elUser.scoreset.dian_topic_do"/>
						</td>
					</tr>
					<tr>
						<td align="center" >
							<strong>学习次数 </strong>
						</td>
						<td bgcolor="#FFFFFF" width="200">
							<s:property value="elUser.scoreset.dian_study_do"/>
						</td>
					</tr>
					<tr>
						<td align="center" >
							<strong>学习时长 </strong>
						</td>
						<td bgcolor="#FFFFFF" width="200">
							<s:property value="elUser.scoreset.dian_study_cp_do"/>
						</td>
					</tr>
					<tr>
						<td align="center" >
							<strong>一篇帖子被删除，扣 </strong>
						</td>
						<td bgcolor="#FFFFFF" width="200">
							<s:property value="elUser.scoreset.jian_forum_do"/>
						</td>
					</tr>
					<tr>
						<td align="center" >
							<strong>一篇知识文章被删除，扣</strong>
						</td>
						<td bgcolor="#FFFFFF" width="200">
							<s:property value="elUser.scoreset.jian_knowledge_do"/>
						</td>
					</tr>
					<tr>
						<td align="center" >
							<strong>长时间不登录：每隔（48）小时不登录，扣</strong>
						</td>
						<td bgcolor="#FFFFFF" width="200">
							<s:property value="elUser.scoreset.jian_login_do"/>
						</td>
					</tr>
					<tr>
						<td align="center" >
							<strong>每被暂停一次考试，扣（20）</strong>
						</td>
						<td bgcolor="#FFFFFF" width="200">
							<s:property value="elUser.scoreset.jian_ep_zhanting"/>
						</td>
					</tr>
					<tr>
						<td align="center" >
							<strong>每被强制交卷一次，扣（50）</strong>
						</td>
						<td bgcolor="#FFFFFF" width="200">
							<s:property value="elUser.scoreset.jian_ep_qiangzhi"/>
						</td>
					</tr>
			</table>
		</div>
	
	</body>
</HTML>
