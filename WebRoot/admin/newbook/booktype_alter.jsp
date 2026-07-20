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
		<script type="text/javascript" src="js/libutil.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
		
			function doSubmit1111(){
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
	  <style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
        </style>
</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="编辑课程类别" /></div>
			</li>
			<!--<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="coursetype_view.action?ctype.id=<s:property value="ctype.id" />">显示课程类别信息</a>
			</li>
			<li class="sep">
			</li>
			<li>
				<span style="font-weight: bold;">编辑课程类别信息 </span>
			</li>-->
		</ul>

		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<s:form action="bookType_upd" method="post" name="catalog_info"
				theme="simple" onsubmit="return doSubmit1111();">
				<table style="margin-top:35px;" width="100%" align="left" cellpadding="1" cellspacing="1"
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
										上级类别：<%
										String xx = ((com.sopia.bookinfo.entities.BookTypeTree) request
													.getAttribute("btype")).getParent().getId()
													+ "";
									%>
						</td>
						<td >
						<s:if test="btype.parent.id==0">
							根节点没有父节点
						</s:if>
						<s:else>
							<label>
								<wysLib:testbooktypeTree did="0" iname="btype.parent.id" ivalue="<%=xx%>"  itype="ra_f" iid="${btype.id}" ></wysLib:testbooktypeTree>
						  </label>
						</s:else>
						</td>
					</tr>
						
					
					<%--
					<tr>
						<td width="120" height="30" align="center" >
							是否为共享节点
						</td>
						<td >
							<label>
								<s:select list="#{0:'不共享',1:'共享'}"  name="ctype.isshared" id="ctype.isshared" ></s:select>
							</label>
						</td>
					</tr>
					 --%>
					<tr>
						<td width="120" height="50" align="center" >
							<s:hidden name="btype.id"></s:hidden>
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
