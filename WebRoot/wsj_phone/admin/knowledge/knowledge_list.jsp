<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.duman.entities.Department"%>
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
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript">
				function showtree(flag){
					var tree = document.getElementById("tree_list_td");
					var img = document.getElementById("showimg");
					if(flag){
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(false);" class="textbg5">隐藏资料类别</a>';
					}
					else{
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(true);" class="textbg5">显示资料类别</a>';
					}
				}
		</script>
	</HEAD>
	<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="知识列表页" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">知识管理</span>
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
			<form action="knowledge_list.action" method="post" name="klform">
				知识名称：<s:textfield name="knowledge.title" />
				类别：
				<select name="kltype.id">
					<option value="-1"
						<s:if test="kltype.id==-1">selected='selected'</s:if>>
						全部
					</option>
					<wysLib:kltype_select selectid="${kltype.id}"></wysLib:kltype_select>
				</select>
				<input type="submit"
					onClick="document.getElementById('pageNow').value=0;" value="搜索"
					class="textbg4">
				<s:hidden name="dep.id" />
				<s:hidden name="subdep"></s:hidden>
				<s:hidden name="pN" id="pageNow"></s:hidden>
				<s:hidden name="pS"></s:hidden>
				<table width="100%" cellpadding="1" cellspacing="1">
					<tr>
						<td valign="top" id="tree_list_td" style="display:none" width="200px">
							<%
								//Department dep=(Department)request.getAttribute("dep"); 
								String url = "knowledge_list.action?kltype.id=";
							%>
							<wysLib:kltype_list href="<%=url%>" itype="001" rootAble="true"></wysLib:kltype_list>
						</td>
						<td valign="middle" width="5px;" style="padding: 0px">
							<img src="images/leftmenu/main_55_1.gif" style="cursor: hand"
								onclick="changeTreeDisplay(this)" id="showimg" />
						</td>
						<td valign="top">
							<div style="text-align: left;" id="showtree">
									<a href="javascript:showtree(true);" class="textbg5">显示资料类别</a>
								</div>
							<s:if test="knowledges.size==0">
								<br>
								<br>没有符合条件的知识</s:if>
							<s:else>
								<table width="100%" align="center" cellpadding="1"
									cellspacing="1">
									<tr>
										<th width="20" align="center">
										</th>
										<th align="center">
											知识名称
										</th>
										<th width="150" align="center">
											创建者(部门)
										</th>
										<th width="110" align="center">
											创建时间
										</th>
										<th width="150" align="center">
											知识类别
										</th>
										<th width="50">
										</th>
									</tr>
									<tbody onMouseOut="changeback()" onMouseOver="changeto()">
										<s:iterator value="knowledges">
											<tr>
												<td width="20" align="center">
													<input type="checkbox" name="knowledges.id"
														value="<s:property value="id"/>">
												</td>
												<td height="30" style="padding-left: 8px; color: blue;"
													align="left">
													[
													<SPAN style="color: red"><s:property value="hotName" />
													</SPAN>]
													<s:property value="shotTitle" />
												</td>
												<td width="150" align="center">
													<s:property value="owner.realname" />
													(
													<s:property value="owner.department.name" />
													)
												</td>
												<td width="110" align="center">
													<s:date name="createtime" format="yyyy-MM-dd HH:mm:ss" />
												</td>
												<td width="150" align="center">
													<s:property value="kltype.name" />
												</td>
												<td width="50" align="center">
													<a target="_blank" class="textbg4"
														href="knowledge_center_view.action?knowledge.id=<s:property value="id"/>">预览</a>
												</td>
												<!--<td bgcolor="#FFFFFF" align="center">
									<a onclick="return confirm('确定删除？')"
										href="knowledge_delete.action?knowledge.id=<s:property value="id"/>">删除</a>
								</td>
							-->
											</tr>
										</s:iterator>
									</tbody>
								</table>
								<script>
			 	function page(i){
			 		klform.action="knowledge_list.action";
			 		document.getElementById("pageNow").value=i;
			 		klform.submit();
			 	}
			 	function setHot(){
			 		klform.action="knowledge_hotset.action";
			 		klform.submit();
			 	}
			 	function deleteknowledge(){
			 		if(window.confirm("确定删除？")){
			 			klform.action="knowledge_delete_man.action";
			 			klform.submit();
			 		}
			 	}
			 </script>

								<wysLib:page></wysLib:page>
								<br>
								<br>
					设置<select name="knowledge.hot">
									<option value="0">
										取消
									</option>
									<option value="1">
										推荐
									</option>
									<option value="2">
										热门
									</option>
									<option value="3">
										重点
									</option>
								</select>
								<input class="textbg4" type="button" onClick="setHot()" value="提交">&nbsp;&nbsp;&nbsp;
								<input class="textbg4" type="button" onClick="deleteknowledge()" value="删除">

							</s:else>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
