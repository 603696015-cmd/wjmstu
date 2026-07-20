<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String patd = request.getContextPath();
	String basePatd = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ patd + "/";
%>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePatd%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
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
			<li>
				<span style="font-weight: bold;">测评详情</span>
			</li>
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
				<table width="60%" cellpadding="1" cellspacing="1"
					>
					<caption><s:property value="elUser.realname"/>的信息</caption>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>所属单位/部门</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:property value="elUser.department.name" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>学号</strong>
						</td>
						<td height="30" align="left" >
							<label>

								<s:property value="elUser.username" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>姓 名</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:property value="elUser.realname" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>编 号</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:property value="elUser.userno" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>角色</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:property value="elUser.role.name" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>联系电话</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:property value="elUser.phone" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>地 址</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:property value="elUser.address" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>电子邮箱</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:property value="elUser.email" />
							</label>
						</td>
					</tr>
					<tr><td colspan="2" bgcolor="#dddffe">专业信息</td></tr>
					<tr>
	<td width="120"  align="center" >
		<strong>性别</strong>
	</td>
	<td  align="left" >
		<label>
			<s:radio  name="elUser.sex" list="#{'m':'男','f':'女'}" />
		</label>
	</td>
</tr>
<tr>
	<td width="120"  align="center" >
		<strong>年龄</strong>
	</td>
	<td  align="left" >
		<label>
			<s:property value="elUser.age" />
		</label>
	</td>
</tr>
<tr>
	<td width="120"  align="center" >
		<strong>学历</strong>
	</td>
	<td  align="left" >
		<label><!--
			<s:select cssStyle="width:70px" list="#{'1':'小学','2':'初中','3':'高中','4':'大专','5':'大本','6':'研究生','7':'博士'}" name="elUser.edubg"
				id="edubg" />-->
		</label>
	</td>
</tr>
<tr>
	<td width="120"  align="center" >
		<strong>专业</strong>
	</td>
	<td  align="left" >
		<label>
		<s:property value="elUser.major"  />
		</label>
	</td>
</tr>	
<tr>
	<td width="120"  align="center" >
		<strong>研究方向</strong>
	</td>
	<td  align="left" >
		<label>
			<s:property value="elUser.studyDir"  />
		</label>
	</td>
</tr> 
<tr>
	<td width="120"  align="center" >
		<strong>毕业院校</strong>
	</td>
	<td  align="left" >
		<label>
			<s:property value="elUser.gradchool"
				id="gradchool" />
		</label>
	</td>
</tr> 
<tr>
	<td width="120"  align="center" >
		<strong>毕业时间</strong>
	</td>
	<td  align="left" >
		<label>
		 <s:date name="elUser.graddate" format="yyyy-MM-dd" /> 
		</label>
	</td>
</tr>   
<tr>
	<td width="120"  align="center" >
		<strong>参加工作时间</strong>
	</td>
	<td  align="left" >
		<label>
			<s:date name="elUser.jobdate" format="yyyy-MM-dd" />
		</label>
	</td>
</tr>   
<tr>
	<td width="120"  align="center" >
		<strong>职称</strong>
	</td>
	<td  align="left" >
		<label>
		<!--<s:select cssStyle="width:300px" list="#{'1':'小学','2':'初中'}" name="elUser.protitle"
				id="protitle" />
		--></label>
	</td>
</tr>   
<tr>
	<td width="120"  align="center" >
		<strong>工作简历</strong>
	</td>
	<td  align="left" >
		<label>
			<s:property value="elUser.jobdesc"
				id="jobdesc" />
		</label>
	</td>
</tr>   
<tr>
	<td width="120"  align="center" >
		<strong>专业证书</strong>
	</td>
	<td  align="left" >
		<label>
			<s:property value="elUser.majorc"
				id="majorc" />
		</label>
	</td>
</tr>    
						<tr>
						<td width="120" height="30" align="center" >
							<strong>开通状态</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:property value="elUser.validName" />
							</label>
						</td>
					</tr>
				</table><table width="60%" cellpadding="2" cellspacing="1" bgcolor="#EBEBEB">
					<caption><s:property value="elUser.realname"/>的测评得分</caption>
					<tr>
						<td align="center" >
							场次名称
						</td>
						<td align="center" >
							<s:property value="troom.title" />
						</td>
					</tr>
					<tr>
						<td align="center" >
							开始时间
						</td>
						<td align="center" >
							<s:date name="troom.begintime" format="yyyy-MM-dd HH:mm:ss" />
						</td>

					</tr>
					<tr>
						<td align="center" >
							结束时间
						</td>
						<td align="center" >
							<s:date name="troom.endtime" format="yyyy-MM-dd HH:mm:ss" />
						</td>
					</tr>
					<tr>
						<td align="center" >
							试卷
						</td>
						<td align="center" >
							<s:property value="troom.exampaper.title" />
						</td>
					</tr>
					<tr>
						<td align="center" >
							所属场次集
						</td>
						<td align="center" >
							<s:property value="troom.trcoll.title" />
						</td>
					</tr>
					<tr>
						<td align="center" >
							客观得分（试卷）
						</td>
						<td align="center" >
							<s:property value="mytroom.myScore" />
						</td>
					</tr>
					<tr>
						<td align="center" >
							自我测评总分
						</td>
						<td align="center" >
							<s:property value="mytroom.zjScore" />
						</td>
					</tr>
					<tr>
						<td align="center" >
							同事测评平均分
						</td>
						<td align="center" >
							<s:property value="mytroom.tsScore" />
						</td>
					</tr>
					<tr>
						<td align="center" >
							上级评价平均分
						</td>
						<td align="center" >
							<s:property value="mytroom.sjscore" />
						</td>
					</tr>
				</table>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
