<%@ page language="java" pageEncoding="UTF-8"   %>
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
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<li>
				<span style="font-weight: bold;">活动管理</span>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px">
			<s:form action="offline_add" method="post"
				onsubmit="return _onsubmit();" theme="simple">
				<s:property value="elmessage" />
				<table cellspacing=1 cellpadding=2 width="70%" align=center
					bgcolor=#ebebeb>
					<tbody>
						<tr>
							<td align=center bgcolor=#ffffff>
								活动名称
							</td>
							<td align=center bgcolor=#ffffff>
								开始时间
							</td>
							<td align=center bgcolor=#ffffff>
								结束时间
							</td>
							<td align=center bgcolor=#ffffff>
								时长
							</td>
							<td align=center bgcolor=#ffffff>
								学时
							</td>
							<td align=center bgcolor=#ffffff>
								学分
							</td>
							<td align=center width="100px" bgcolor=#ffffff>
								参与人员
							</td>
							<td align=center bgcolor=#ffffff>
							</td>
						</tr>
						<s:iterator value="offlines">
							<tr>
								<td align=center bgcolor=#ffffff>
									<s:property value="name" />
								</td>
								<td align=center bgcolor=#ffffff>
									<s:date name="begintime" format="yyyy-MM-dd" />
								</td>
								<td align=center bgcolor=#ffffff>
									<s:date name="endtime" format="yyyy-MM-dd" />
								</td>
								<td align=center bgcolor=#ffffff>
									<s:property value="during" />
								</td>
								<td align=center bgcolor=#ffffff>
									<s:property value="xueshi" />
								</td>
								<td align=center bgcolor=#ffffff>
									<s:property value="score" />
								</td>
								<td align=center width="100px" bgcolor=#ffffff>
									<s:property value="usercount" />
								</td>
								<td>
									<a
										href="offline_delete.action?offline.id=<s:property value="id"/>"
										onclick="return confirm('确定删除？');">删除</a>
									<a
										href="offline_alterinit.action?offline.id=<s:property value="id"/>">修改</a>
								<a
										href="offline_view.action?offline.id=<s:property value="id"/>">查看</a>
								</td>
							</tr>
						</s:iterator>
						<tr>
							<td align="center" bgcolor="#ffffff" colspan="8">
								<script type="text/javascript">
								function page(i){
									document.location="offline_list.action?pN="+i;
								}
							</script>
								<wysLib:page></wysLib:page>
							</td>
						</tr>
					</tbody>
				</table>
			</s:form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
