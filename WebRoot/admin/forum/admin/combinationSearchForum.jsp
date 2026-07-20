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
		<TITLE>帖子组合搜索</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<SCRIPT src="quiz_searchlist.files/cexampaper.js" type=text/javascript></SCRIPT>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript">
			function fbtypesubmit(){
				var fbt_select=document.getElementById("fbtid").options;
				for(var i=0;i<fbt_select.length;i++){
					var newid=fbt_select.options[i].value;
					if(fbt_select.options[i].selected){
						if(newid.indexOf("--")<0){
							flsearch.action="searchforumList.action?fbtype.id="+fbt_select.options[i].value;
						}else{
							flsearch.action="forumListByBlockid.action?fblock.id="+fbt_select.options[i].value;
						}
					}	
				}
				flsearch.submit();
			}
		</script>
				<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
</style>
	</HEAD>
	<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="组合搜索工具" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">帖子组合搜索</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<s:form action="combinationSearchforum" method="post" theme="simple"
				name="department_info" id="department_info">
				<TABLE cellSpacing=1 cellPadding=5 width="100%" align=center
					bgColor=#CFDBE2>
					<TBODY>
					<TBODY onmouseover=changeto() onmouseout=changeback()>
						<TR>
							<TD width="130" height=40 align="right" valign="middle" bgColor=#F8FCFE>
								发布者用户名：							</TD>
							<TD height=40 valign="middle" bgColor=#F8FCFE>
								<INPUT size=24 name="forum.creater.username" style="margin-left:5px;" />
						  </TD>
						</TR>
						<TR>
							<TD width="130" height=40 align="right" valign="middle" bgColor=#F8FCFE>
								发布者姓名：							</TD>
							<TD height=40 valign="middle" bgColor=#F8FCFE>
								<INPUT size=24 name="forum.creater.realname" style="margin-left:5px;"/>
						  </TD>
						</TR>
						<TR>
							<TD width="130" height=40 align="right" valign="middle" bgColor=#F8FCFE>
								论坛版块：							</TD>
							<TD height=40 valign="middle" bgColor=#F8FCFE>
								<!--<SELECT name="forum.fblock.fbtype.id">
									<OPTION value=0 selected>
										请选择
									</OPTION>
									<s:iterator value="fbtypes">
										<OPTION value="<s:property value="id"/>">
											<s:property value="name" />
										</OPTION>
									</s:iterator>
								</SELECT>
							-->
								<select id="fbtid" name="fbtid" style="width:150px;margin-left:5px;">
									<option value=0 selected>
										请选择
									</option>
									<s:iterator value="fbtypes" status="fbtst">
										<option value="<s:property value="id"/>" id="fbtypeid">
											<s:property value="name" />
										</option>
										<s:iterator value="fblocks" status="fbs" id="fbsid">
											<option value="--<s:property value="id"/>" id="fblockid">
												&nbsp;&nbsp;--
												<s:property value="title" />
											</option>
										</s:iterator>
									</s:iterator>
								</select>
						  </TD>
						</TR>
						<TR>
							<TD width="130" height=40 align="right" valign="middle" bgColor=#F8FCFE>
								帖子标题：							</TD>
							<TD height=40 valign="middle" bgColor=#F8FCFE>
								<INPUT size=24 name="forum.title" style="margin-left:5px;"/>
						  </TD>
						</TR>
						<TR>
							<TD width="130" height=40 align="right" valign="middle" bgColor=#F8FCFE>
								发布时间段范围：							</TD>
							<TD height=40 valign="middle" bgColor=#F8FCFE>
								&nbsp;从
								<INPUT onclick=setday(this) name="forum.begintime">
								&nbsp;&nbsp;到
								<INPUT onclick=setday(this) name="forum.endtime">
						  </TD>
						</TR>
						<tr>
							<td height="40" colspan="2" align="center" valign="middle" bgcolor="#F8FCFE">
								<s:submit name="submit" cssClass="textbg4" value="搜索"></s:submit>
						  <input type="reset" class="textbg4" value="重置"/>						  </td>
						</tr>
					</TBODY>
			  </TABLE>
			</s:form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
