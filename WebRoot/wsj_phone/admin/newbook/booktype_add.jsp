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
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
			function doSubmit(){
				var titleObj=document.getElementById("cname");
				var title=titleObj.value.replace(/(\s*$)/g, "");
				if(title==""){
					alert("图书类别名称不能为空!");
					titleObj.focus();
					return false;
				}
				var qlibId=$("input[name='btype.parent.id']:checked").val();
				if(qlibId==undefined){
					alert("请选择图书类别！");
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="填写类基本信息" /></div>
			</li>
			<!--<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="coursetype_list.action">课程类别管理</a>
			</li>
			<li class="sep">
			</li>
			<li>
				<span style="font-weight: bold;">添加课程新类别</span>
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
			<s:form action="bookType_add" method="post" name="catalog_info"
				theme="simple" onsubmit="return doSubmit();">
				<input type="hidden" value="0" name="ctype.isshared" />
				<table width="600px" align="left" cellpadding="1" cellspacing="1"
					>
					<tr>
						<td width="120" height="30" align="center" >
							类别名称
						</td>
						<td >
							<label>
								<s:textfield name="btype.name" id="cname" size="60" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							类别介绍
						</td>
						<td >
							<label>
								<s:textarea name="btype.description" cols="60" rows="7" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							上级类别
						</td>
						<td >
							<label>
									<wysLib:testbooktypeTree did="0" iname="btype.parent.id" itype="ra_2no" ></wysLib:testbooktypeTree>
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
								<s:select list="#{0:'不共享',1:'共享'}" name="ctype.isshared"></s:select>
							</label>
						</td>
					</tr>
					 --%>
					<tr>
						<td width="120" height="50" align="center" >&nbsp;
							
						</td>
						<td >
							<input type="submit" value="确认添加">
						</td>
					</tr>
				</table>
				<br>
			</s:form>

		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
