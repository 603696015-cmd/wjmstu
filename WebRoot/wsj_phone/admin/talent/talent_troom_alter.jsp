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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/assist.js"></script>
		<script type="text/javascript" src="js/talent.js"></script>
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
				<span style="font-weight: bold;">场次考试修改</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="talent_troom_list.action?troomcoll.id=<s:property value="troomcoll.id"/>">场次考试列表</a>
			</li>
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<table width="60%" cellpadding="1" cellspacing="1">
				<caption>
					客观评价场次信息
				</caption>
				<tr>
					<td>
						<strong>标题</strong>
					</td>
					<td>
						<s:property value="troomcoll.title" />
					</td>
				</tr>
				<tr>
					<td height="30" align="left" >
						<strong>描述</strong>
					</td>
					<td>
						<s:property value="troomcoll.description" />
					</td>
				</tr>
				<tr>
					<td height="30" align="left" >
						<strong>创建时间</strong>
					</td>
					<td height="30" align="left" >
						<s:date name="troomcoll.createtime" format="yyyy-MM-dd HH:mm:ss" />
					</td>
				</tr>
			</table>
			<s:form action="talent_troom_alter" method="post" theme="simple">
			<table width="60%" cellpadding="1" cellspacing="1">
				<caption>
					场次考试修改
				</caption>
				<tr>
							<td>
								<strong>标题</strong>
							</td>
							<td height="30" align="left" >
								<label>
									<s:textfield  name="troom.title" size="30" />
								</label>
							</td>
						</tr>
						<tr>
							<td>
								<strong>描述</strong>
							</td>
							<td height="30" align="left" >
								<label>
								<s:textarea name="troom.description" cols="40" rows="5"></s:textarea>
								</label>
							</td>
						</tr>
						<tr>
							<td>
								<strong>开始结束时间</strong>
							</td>
							<td height="30" align="left" >
								<label>
								<input type="text" name="troom.begintime" value="<s:date name="troom.begintime" format="yyyy-MM-dd"/>" onclick='setday(this)'  />
								到<input type="text" name="troom.endtime" value="<s:date name="troom.endtime" format="yyyy-MM-dd"/>" onclick='setday(this)'  />
								</label>
							</td>
						</tr>
						<tr>
							<td>
								<strong>试卷</strong>
							</td>
							<td height="30" align="left" >
								<label id="eptitle" style="width: 200px;">
								<s:property value="troom.exampaper.title"/>
								</label>
								<input type="hidden" id='epid' name="troom.exampaper.id" value="<s:property value="troom.exampaper.id"/>"/><a href="javascript:searchExamPaper();">选择试卷</a>
							</td>
						</tr>
						<!--<tr>
							<td>
								<strong>测评指标</strong>
							</td>
							<td height="30" align="left" >
								<div id="trnorms" style="width: 100%;">
									<s:iterator status="normosst" value="troom.norms"> 
										<div id="n_<s:property value="#normosst.index"/>">
										 指标<s:property value="#normosst.index+1"/> :&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="troom.norms" value="<s:property />"/>
										 &nbsp;&nbsp;&nbsp;<input type='button' value='删除' onclick='removeNorm(<s:property value="#normosst.index"/>)'>
										<script>ns++;</script>
										</div>
									</s:iterator>
								</div>
								<input type="button" value="增加" onclick="addNorms();"/>
							</td>
						</tr>
						--><tr>
							<td width="120" height="50" align="center" >
							<input type="hidden"  name="troom.trcoll.id" value="<s:property value="troomcoll.id"/>">、
							<s:hidden name="troomcoll.id"></s:hidden>
							<s:hidden name="troom.id"></s:hidden>
							
							</td>
							<td height="50" align="left" >
								<input type="submit" value="确认修改">
							</td>
						</tr>
					</table>
					</s:form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
