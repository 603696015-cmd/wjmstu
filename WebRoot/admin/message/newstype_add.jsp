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
		<TITLE>五矿发展员工职业发展系统--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript">
			function doSubmit(){
				var title=document.getElementById("ntypeName");
				title=title.value.replace(/(\s*$)/g, "");
				if(title==""){
					alert("新闻栏目名称不能为空!");
					return false;
				}
				return true;
			}
		</script>
	</HEAD>
	<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="填写栏目基本信息" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">新闻公告栏目添加</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="newstype_list.action">新闻公告栏目管理</a>

			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<s:form action="newstype_add" method="post" name="catalog_info"
			theme="simple" onsubmit="return doSubmit();">
			<input type="hidden" value="0" name="ntype.isshared" />
			<table width="100%" cellpadding="1" cellspacing="1" >
				<tr>
					<td width="120" height="30" align="right" >
						<span class="neededitem">*</span>栏目名称：
					</td>
					<td >
						&nbsp;<label>
							<s:textfield name="ntype.name" id="ntypeName" size="60" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="right" >
						栏目介绍：
					</td>
					<td >
						&nbsp;<label>
							<s:textarea name="ntype.description" cols="60" rows="7" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="right" >
						<span class="neededitem">*</span>上级栏目：
					</td>
					<td >
						&nbsp;<label>
							<select name="ntype.parent.id" id="parentid">
								<wysLib:newsTypeSelect rootAble="true"></wysLib:newsTypeSelect>
							</select>
						</label>
					</td>
				</tr>
				<%-- 
					<tr>
						<td width="120" height="30" align="center" >
							是否为共享节点
						</td>
						<td >
							<label>
								<s:select list="#{0:'不共享',1:'共享'}"  name="ntype.isshared" id="ntype.isshared" ></s:select>
							</label>
						</td>
					</tr>
					--%>
				<tr>
					<td width="120" height="50" align="center" >&nbsp;
						
					</td>
					<td >
						&nbsp;<input class="textbg6" type="submit" value="确认添加">
						<input class="textbg6" type="button" onClick="document.location='newstype_list.action'" value="取消">
					</td>
				</tr>
			</table>
			<br>
		</s:form>
	</body>
</HTML>
