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
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="设置抽题规则" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;"> 大题试题管理 </span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="exampaperblock_view.action?epBlock.id=<s:property value="epBlock.id" />">查看大题信息</a>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="exampaperblock_alterInit.action?epBlock.id=<s:property value="epBlock.id" />">编辑大题信息</a>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="exampaperblock_list.action?examPaper.id=<s:property value="epBlock.examPaper.id"/>">返回大题列表</a>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<label style="font-size: 16px; font-weight: bold;">
				当前试卷:
				<s:property value="epBlock.examPaper.title" />
				【
				<s:if test="epBlock.examPaper.random">随机</s:if>
				<s:else>手工</s:else>
				】
				<br>
				当前大题:
				<s:property value="epBlock.title" />
				[
				<s:property value="epBlock.typeName" />
				]
			</label>
			<br>
			<br>
				<span style="color: #ff0000;">${elmessage} </span>
				<table width="90%" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr>
						<td width="160" height="30" align="center" >
							试题库
						</td>
						<td >
							<label>
								<s:property value="question.qlib.name" />
								<s:if test="sub_operate==1">
									(包含下级题库)
								</s:if>
								<s:else>
									(不包含下级题库)
								</s:else>
								<s:hidden name="sub_operate" />
								<input type="hidden" name="epRandom.suboperate" value="${sub_operate}" />
								<input name="epRandom.qlib.id"
									value="<s:property value="question.qlib.id"/>" type="hidden" />
								<input name="epRandom.epBlock.id"
									value="<s:property value="epBlock.id"/>" type="hidden" />
								<input name="question.qlib.id"
									value="<s:property value="question.qlib.id"/>" type="hidden" />
								<s:hidden name="epBlock.id" />
								<s:hidden name="epBlock.type" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							1级
						</td>
						<td >
							<label>
								<input name="epRandom.qlevel1" type="text" id="textfield"
									size="10" value="<s:property value="epRandom.qlevel1"/>"/>
								/ 总数
								<s:property value="epRandom1.qlevel1" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							2级
						</td>
						<td >
							<label>
								<input name="epRandom.qlevel2" type="text" id="textfield"
									size="10"  value="<s:property value="epRandom.qlevel2"/>"/>
								/ 总数
								<s:property value="epRandom1.qlevel2" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							3级
						</td>
						<td >
							<label>
								<input name="epRandom.qlevel3" type="text" id="textfield"
									size="10"  value="<s:property value="epRandom.qlevel3"/>"/>
								/ 总数
								<s:property value="epRandom1.qlevel3" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							4级
						</td>
						<td >
							<label>
								<input name="epRandom.qlevel4" type="text" id="textfield"
									size="10"  value="<s:property value="epRandom.qlevel4"/>"/>
								/ 总数
								<s:property value="epRandom1.qlevel4" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							5级
						</td>
						<td >
							<label>
								<input name="epRandom.qlevel5" type="text" id="textfield"
								size="10"  value="<s:property value="epRandom.qlevel5"/>"/>
								/ 总数
								<s:property value="epRandom1.qlevel5" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							不限
						</td>
						<td >
							<label>
								<input name="epRandom.qlevel" type="text" id="textfield"
									 value="<s:property value="epRandom.qlevel"/>" size="10" />
								/ 总数
								<s:property value="epRandom1.qlevel" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="50" align="center" >&nbsp;
							
						</td>
						<td >
							<input type="submit" name="button" id="button" value="确认修改" />
						</td>
					</tr>
				</table>
				<s:hidden name="epRandom.id"></s:hidden>
				<s:hidden name="epBlock.id"></s:hidden>
		</div>
	
	</body>
</HTML>
