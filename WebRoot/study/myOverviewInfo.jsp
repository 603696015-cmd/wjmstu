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
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<TITLE>五矿发展员工职业发展系统--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
	<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="查看详情" /></div>
			</li>
		</ul> 
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 学员基本信息 -->
		<div style="margin-top:10px;">
			<table width="90%" align="center" cellpadding="2" cellspacing="1">
				<caption>
					基本信息
				</caption>
				<tr height="26">
					<td style="padding-left:10px;" width="80">
						用户名:
					</td>
					<td style="padding-left:10px;">
						<s:property value="elUser.username" />
					</td>
					<td style="padding-left:10px;" width="80">
						姓名:
					</td>
					<td style="padding-left:10px;">
						<s:property value="elUser.realname" />
					</td>
					<td style="padding-left:10px;" width="80">
						性别:
					</td>
					<td style="padding-left:10px;">
						<s:property value="elUser.sex" />
					</td>
				</tr>
				<tr height="26">
					<td style="padding-left:10px;" width="80">
						单位/部门:
					</td>
					<td style="padding-left:10px;">
						<s:property value="elUser.department.name" />
					</td>
					<td style="padding-left:10px;" width="80">
						权限:
					</td>
					<td style="padding-left:10px;">
						<s:property value="elUser.role.name" />
					</td>
					<td style="padding-left:10px;" width="80">
						身份证号:
					</td>
					<td style="padding-left:10px;">
						<s:property value="elUser.shenfenzheng" />
					</td>
				</tr>
				<tr height="26">
					<td style="padding-left:10px;" width="80">
						培训班:
					</td>
					<td style="padding-left:10px;" colspan="2">
						全部有<s:property value="#request.class_all" />个，
						已结业<s:property value="#request.class_yes" />个
					</td>
					<td style="padding-left:10px;" width="80">
						考场:
					</td>
					<td style="padding-left:10px;" colspan="2">
						全部有<s:property value="#request.eroom_all" />个，
						已通过<s:property value="#request.eroom_ok" />个
					</td>
				</tr>
			</table>
		</div>
		<!-- 证书 -->
		<div style="margin-top:20px;">
			<table width="90%" align="center" cellpadding="2" cellspacing="1">
					<caption>
						培训班信息
					</caption>
				<tr>
					<th align="center" height="30" >
						培训班名称
					</th>
					<th align="center" >
						开始时间
					</th>
					<th align="center" >
						结束时间
					</th>
					<th align="center" >
						证书名称
					</th>
					<th align="center" >
						我的证书
					</th>
					<th align="center" >
						学习详情
					</th>
				</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
				<s:iterator value="myClasses">
				<tr>
					<td align="center" height="30" >
						<s:property value="elClass.name"/>
					</td>
					<td align="center" >
						<s:date name="elClass.starttime" format="yyyy-mm-dd HH:mm:ss" />
					</td>
					<td align="center" >
						<s:date name="elClass.finishtime" format="yyyy-mm-dd HH:mm:ss" />
					</td>
					<s:if test="passed">
						<td align="center" >
							<s:property value="elClass.certificatename"/>
						</td>
						<td align="center" >
							<a target="_blank" href="diploma_view.action?elclass.id=<s:property value="elClass.id"/>&elUser.id=<s:property value="elUser.id"/>"  class=textbg4>查 看</a>
						</td>
					</s:if>
					<s:else>
					<td align="center" colspan="2" >
						还没能获得证书
					</td>
					</s:else>
					<td align="center" >
						<a href="elclass_view.action?elclass.id=<s:property value="elClass.id"/>&elUser.id=<s:property value="elUser.id"/>&Return=sclmoi" class=textbg4>查 看</a>
					</td>
				</tr>
				</s:iterator></tbody>
			</table>
		</div>
		<!-- 考试成绩 -->
		<!-- 内容 -->
		<div style="margin-top:0px;">
			<table width="90%" align="center" cellspacing="1" cellpadding="1">
				<caption>
					考核信息
				</caption>
				<tr>
					<th height="30" align="center">
						考场名
					</th>
					<th width="120" height="30" align="center">
						考场开始时间
					</th>
					<th width="120" height="30" align="center">
						考场结束时间
					</th>
					<th width="80" height="30" align="center">
						试卷数量
					</th>
					<th width="80" height="30" align="center">
						状态
					</th>
					<th width="60" height="30" align="center">
						成绩
					</th>
					<th width="80" height="30" align="center">
						是否通过
					</th>
					<th></th>
				</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
				<s:iterator value="myrooms">
					<tr>
						<td height="30" align="center">
							<s:if test="examroom.isApplication == 1">
								<SPAN style="color: red">【申请】</SPAN>
							</s:if>
							<s:else>
								<SPAN style="color: gray">【分配】</SPAN>
							</s:else>
							<s:property value="examroom.title" />
						</td>
						<td height="30" align="center">
							<s:date name="examroom.begintime" format="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td height="30" align="center">
							<s:date name="examroom.endtime" format="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td height="30" align="center">
							<s:property value="epsize" />
						</td>
						<td height="30" align="center">
							<s:property value="statusName" />
						</td>
						<td width="60" height="30" align="center">
							<s:if test="status==0">--</s:if>
							<s:else>
								<s:property value="myScore" />
							</s:else>
						</td>
						<td height="30" align="center">
							<s:if test="status==0">--</s:if>
							<s:else>
								<s:if test="ispassed==1">是</s:if>
								<s:else>否</s:else>
							</s:else>
						</td>
						<td height="30" align="center">
							<a href='quizpaperinit.action?myroom.examroom.id=<s:property value="examroom.id"/>&Return=moi'
								onClick="return isEroom2('<s:property value="examroom.valid"/>','<s:property value="examroom.svalid"/>','<s:property value="examroom.isnormal"/>','<s:property value="examroom.type"/>');"
								class="textbg6">查看详情</a>
						</td>
					</tr>
				</s:iterator></tbody>
			</table>
		</div>
	</body>
</HTML>
