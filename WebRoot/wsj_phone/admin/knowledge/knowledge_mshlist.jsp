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
	<script type="text/javascript" src="js/menu.js"></script>
		</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="知识列表页" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">知识审核(我是管理员)</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="knowledge_shlist.action">知识审核</a>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<form action="knowledge_shlist.action" method="post" name="klform">
				<s:hidden name="pN" id="pageNow"></s:hidden>
				<s:hidden name="pS"></s:hidden>
				 <s:if test="knowledges.size==0">
					<br>
					<br>没有符合条件的知识</s:if>
				<s:else>
					<table width="100%" align="center" cellpadding="2" cellspacing="1"
						bgcolor="#EBEBEB">
						<tr>
							<th align="center" >

							</th>
							<th align="center" >
								知识名称
							</th>
							<th align="center" >
								创建者(部门)
							</th>
							<th align="center" >
								创建时间
							</th>
							<th align="center" >
								修改时间
							</th>
							<th align="center" >
								知识类别
							</th>
							 <th >
							</th>
						 </tr>
						<s:iterator value="knowledges">
							<tr>
								<td align="center" >
									<input type="checkbox" name="knowledges.id"
										value="<s:property value="id"/>">
								</td>
								<td align="center" >
									[<SPAN style="color:red"><s:property value="hotName" /></SPAN>]
									<s:property value="shotTitle" />
								</td>
								<td align="center" >
									<s:property value="owner.realname" />
									(
									<s:property value="owner.department.name" />
									)
								</td>
								<td align="center" >
									<s:date name="createtime" format="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td align="center" >
									<s:date name="modifytime" format="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td align="center" >
									<s:property value="kltype.name" />
								</td>
								<td><a target="_blank" href="knowledge_center_view.action?knowledge.id=<s:property value="id"/>">预览</a></td>
								<!--<td bgcolor="#FFFFFF" align="center">
									<a onclick="return confirm('确定删除？')"
										href="knowledge_delete.action?knowledge.id=<s:property value="id"/>">删除</a>
								</td>
							--></tr>
						</s:iterator>
					</table>
					<script>
			 	function page(i){
			 		klform.action="knowledge_shlist.action";
			 		document.getElementById("pageNow").value=i;
			 		klform.submit();
			 	}
			 	function setHot(){
			 		klform.action="knowledge_sh.action";
			 		klform.submit();
			 	}
			 </script>

					<wysLib:page></wysLib:page>
					<br>
					 <input type="button" onBlur="setHot();" value="通过审核"/>
				</s:else>
			</form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
