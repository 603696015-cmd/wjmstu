<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.courseman.entities.EroomLib"%>
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
		<TITLE>考场类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/libutil.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
		function _onsubmit(){
			if(!$.trim($("#name").val()).length > 0){
				alert("考场类别名称不要为空!");
				$("#name").focus();
				return false;
			}
			var qlibId=$("input[name='eroomLib.parent.id']:checked").val();
			if(qlibId==undefined){
				alert("请选择考场类别！");
				return false;
			}
		}
		</script>
	<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="填写类别基本信息" /></div>
			</li>
			<!--<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="eroomlib_list.action">考场类别管理</a>
			</li>
			<li class="sep">
			</li>
			<li>
				<span style="font-weight: bold;">添加考场新类别</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<s:form action="eroomlib_add" method="post" name="catalog_info"
				theme="simple" onsubmit="return _onsubmit();">
				<table width="100%" align="left" cellpadding="0" cellspacing="1">
					<tr>
						<td width="120" height="30" align="right" >
							<span class="neededitem">*</span>类别名称：
						</td>
						<td >&nbsp;
							<label>
								<s:textfield name="eroomLib.name" id="name" size="60" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right" >
							类别介绍：
						</td>
						<td >&nbsp;
							<label>
								<s:textarea name="eroomLib.description" cols="60" rows="7" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right" >
							<span class="neededitem">*</span>上级类别：<%
							EroomLib uu = ((EroomLib) request
											.getAttribute("eroomLib"));
									String xx = "0";
									xx = uu == null ? "1" : uu.getParent() == null ? "1" : uu
											.getParent().getId()
											+ "";
							%>
						</td>
						<td >&nbsp;
							<label>
								<wysLib:eroomLibTree did="0" iname="eroomLib.parent.id"
										itype="ra_2no" ivalue="<%=xx%>" iid="${eroomLib.id}" ></wysLib:eroomLibTree>
							</label>
						</td>
					</tr>
					<!--<tr>
						<td width="120" align="center" >
							可操作人员：
						</td>
						<td >
							<div id="can_op">
								<s:iterator value="eroomLib.opusers">
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
								onclick="searchUserInit('can_op','eroomLib.opusers.id'); return false;">授权</a>
						</td>
					</tr>-->
					<tr>
						<td width="120" height="50" align="center" >&nbsp;
							
						</td>
						<td >
							<input style="height:30px;border: none;color: red;" class="textbg5" type="submit" value="确认添加">
							<input style="height:30px;border: none;" onClick="document.location='eroomlib_list.action'" class="textbg5" type="button" value="取   消">
						</td>
					</tr>
				</table>
				<br>
			</s:form>

		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
