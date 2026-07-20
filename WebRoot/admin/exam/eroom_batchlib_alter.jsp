<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.courseman.entities.EroomBatchLib"%>
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
		<TITLE>考场批次类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/libutil.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
		function _onsubmit(){
			if(document.getElementById("name").value==""){
				alert("考场批次类别名称不要为空");
				document.getElementById("name").focus();
				return false;
			}					
	
		}
		</script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="批次类别修改" /></div>
			</li>
			<!--<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="eroom_batchlib_view.action?erbatchLib.id=<s:property value="erbatchLib.id" />">显示考场批次类别信息</a>
			</li>
			<li class="sep">
			</li>
			<li>
				<span style="font-weight: bold;">编辑考场批次类别信息 </span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<s:form action="eroom_batchlib_alter" method="post" name="catalog_info"
				theme="simple" onsubmit="return _onsubmit();">
				<table width="600px;" align="left" cellpadding="1" cellspacing="1"
					>
					<tr>
						<td width="120" height="30" align="center" >
							类别名称
						</td>
						<td >
							<label>
								<s:textfield name="erbatchLib.name" id="name" size="60" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							类别介绍
						</td>
						<td >
							<label>
								<s:textarea name="erbatchLib.description" cols="60" rows="7" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							上级类别<%
								EroomBatchLib uu = ((EroomBatchLib) request
											.getAttribute("erbatchLib"));
									String xx = "0";
									xx = uu == null ? "1" : uu.getParent() == null ? "1" : uu
											.getParent().getId()
											+ "";
							%>
							
						</td>
						<td >
							<s:if test="erbatchLib.parent.id==0">
								根节点没有父节点
							</s:if>
							<s:else>
								<label>
									<wysLib:erbLibTree did="0" iname="erbatchLib.parent.id"
										itype="ra_f" ivalue="<%=xx %>" iid="${erbatchLib.id}"></wysLib:erbLibTree>
								</label>
							</s:else>
						</td>
					</tr>
					<tr>
						<td width="120" align="center" >
							可操作人员：
						</td>
						<td >
							<div id="can_op">
								<s:iterator value="erbatchLib.opusers">
									<span
										style="width: 110px; height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
										<label style="width: 80px; float: left;">
											<s:property value="realname" />
										</label> <a
										style="cursor: hand; float: right; width: 14px; height: 14px;"
										href=""
										onclick="javascript:deleteUserinfo(this,<s:property value="id"/>,'op');return false;">X</a>
									</span>
								</s:iterator>
							</div>
							<a href=""
								onclick="searchUserInit('can_op','erbatchLib.opusers.id'); return false;">授权</a>
						</td>
					</tr>
					<tr>
						<td width="120" align="center" >
							可使用人员：
						</td>
						<td >
							<div id="can_use">
								<s:iterator value="erbatchLib.useusers">
									<span
										style="width: 110px; height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
										<label style="width: 80px; float: left;">
											<s:property value="realname" />
										</label> <a
										style="cursor: hand; float: right; width: 14px; height: 14px;"
										href=""
										onclick="javascript:deleteUserinfo(this,<s:property value="id"/>,'use');return false;">X</a>
									</span>
								</s:iterator>
							</div>
							<a href="#"
								onclick="searchUserInit('can_use','erbatchLib.useusers.id'); return false;">授权</a>
						</td>
					</tr>
					<tr>
						<td width="120" height="50" align="center" >
							<s:hidden name="erbatchLib.id"></s:hidden>
						</td>
						<td >
							<input type="submit" value="确认修改">
						</td>
					</tr>
				</table>
				<br>
			</s:form>

		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
