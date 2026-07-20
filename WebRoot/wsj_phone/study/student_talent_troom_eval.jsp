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
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
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
			<!--<li>
				<span style="font-weight: bold;"> <s:if
						test="myztroom.evaltype==1">自我打分</s:if> <s:elseif
						test="myztroom.evaltype==2">给同事打分</s:elseif> <s:else>
					给下级打分
				</s:else> </span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<table width="60%" cellpadding="1" cellspacing="1">
				<caption>
					考场信息
				</caption>
				<tr>
					<td>
						<strong>考场标题</strong>
					</td>
					<td align="left" >
						<label>
							<s:property value="myztroom.ztroom.title" />
						</label>
					</td>
				</tr>
				<tr>
					<td>
						<strong>创建者</strong>
					</td>
					<td align="left" >
						<label>
							<s:property value="myztroom.ztroom.creater.realname" />
						</label>
					</td>
				</tr>
				<tr>
					<td>
						<strong>考场描述</strong>
					</td>
					<td align="left" >
						<label>
							<s:property value="myztroom.ztroom.description" />
						</label>
					</td>
				</tr>
				<tr>
					<td>
						<strong>开始时间</strong>
					</td>
					<td align="left" >
						<label>
							<s:date name="myztroom.ztroom.begintime" format="yyyy-MM-dd" />
						</label>
					</td>
				</tr>
				<tr>
					<td>
						<strong>结束时间</strong>
					</td>
					<td align="left" >
						<label id="eptitle" style="width: 200px;">
								<s:date name="myztroom.ztroom.endtime" format="yyyy-MM-dd" />
					</label>
					</td>
				</tr>
				<tr>
					<td>
						<strong>测评指标</strong>
					</td>
					<td align="left" style="padding: 0px;padding:0px;" >
						<s:iterator value="myztroom.ztroom.norms">
							<s:property/>,
						</s:iterator>
					</td>
				</tr>
			</table>
			<form action="student_talent_troom_eval.action" method="post">
				<s:if test="myztroom.evaltype==1">
					<table width="60%" cellpadding="1" cellspacing="1">
						<caption>
							自我打分
						</caption>
						<tr>
							<td>
								评价对象
							</td>
							<s:iterator value="myztroom.ztroom.norms">
								<td>
									<strong><s:property /> </strong>
								</td>
							</s:iterator>
						</tr>
						<tr>
							<td>
								我	<input type="hidden" value="<s:property value="#session.userId"/>" name="myztrooms[0].tester.id" />
								
							</td>
							<s:iterator value="myztroom.ztroom.norms" status="myst">
								<td>
									<strong><input type="text"
											name="myztrooms[0].evaldetails" value="<s:property value="myztroom.tester.myZTRoom.evaldetails[#myst.index]"/>" size="5"> </strong>
								</td>
							</s:iterator>
						</tr>
					</table>
				</s:if>
				<s:else>
					<table width="60%" cellpadding="1" cellspacing="1">
						<caption>
							<s:if test="myztroom.evaltype==2">给同事打分</s:if>
							<s:else>
					给下级打分
				</s:else>
						</caption>
						<tr>
							<td>
								评价对象
							</td>
							<s:iterator value="myztroom.ztroom.norms" >
								<td>
									<strong><s:property /> </strong>
								</td>
							</s:iterator>
						</tr>
						<s:iterator value="myztroom.testers" status="nsstat">
							<tr>
								<td>
									<input type="hidden" value="<s:property value="id"/>" name="myztrooms[<s:property value="#nsstat.index"/>].tester.id" />
									<s:property value="realname" />
									(
									<s:property value="department.name" />
									)
								</td>
								<s:iterator value="myztroom.ztroom.norms" status="st_001">
									<td>
										<strong><input type="text"
												name="myztrooms[<s:property value="#nsstat.index"/>].evaldetails"
												size="5" value="<s:property value="myZTRoom.evaldetails[#st_001.index]"/>"> </strong>
									</td>
								</s:iterator>
							</tr>
						</s:iterator>
					</table>
				</s:else>
				<s:hidden name="troom.id"></s:hidden>
				<s:hidden name="myztroom.evaltype"></s:hidden>
				<input type="hidden" name="myztroom.ztroom.id" value="<s:property value="myztroom.ztroom.id"/>"/>
				<input type="submit" value="提交">
			</form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
