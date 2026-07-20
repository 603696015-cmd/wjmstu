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
				<span style="font-weight: bold;">知识审核</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="knowledge_mshlist.action">知识审核(我是管理员)</a>
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
			<form action="knowledge_shlist.action" method="post" name="klform">
				<s:hidden name="pN" id="pageNow"></s:hidden>
				<s:hidden name="pS"></s:hidden>
				<s:hidden name="kltype.id"></s:hidden>
				<table width="100%" cellpadding="1" cellspacing="1">
					<tr>
						<td valign="top" id="tree_list_td" style="display:none" width="200px">
							<%
								String url = "knowledge_shlist.action?subdep=1&kltype.id=";
							%>
							<wysLib:kltype_list href="<%=url%>" itype="001" rootAble="true"></wysLib:kltype_list>
						</td>
						<td valign="middle" width="5px;" style="padding: 0px">
							<img src="images/leftmenu/main_55_1.gif" style="cursor: hand"
								onclick="changeTreeDisplay(this)" id="showimg" />
						</td>
						<td valign="top">
						<table width="100%" border="0" cellspacing="1" cellpadding="0" bgcolor="#D1E4F5">
  <tr>
    <td width="100" bgcolor="#F8FCFE"><div style="text-align: left;" id="showtree">
									<a href="javascript:showtree(true);" class="textbg5">显示资料类别</a>
			  </div></td>
    <td bgcolor="#F8FCFE">知识名称 :
							<input type="text" name="knowledge.title"
								value="<s:property value="knowledge.title" />">
							<input type="submit"
								onClick="document.getElementById('pageNow').value=0;" class="textbg4" value="搜索"></td>
  </tr>
</table>

							
							
							<s:if test="knowledges.size==0">
							
					      <br>
								<br>没有符合条件的知识</s:if>
							<s:else>
								<table width="100%" align="center" cellpadding="2"
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
										<th width="70" align="center">
											状态
										</th>
										<th width="100">
										</th>
									</tr>
									<tbody onMouseOut="changeback()" onMouseOver="changeto()">
										<s:iterator value="knowledges">
											<tr>
												<td width="20" align="center">
													<input type="checkbox" name="knowledges.id"
														value="<s:property value="id"/>" id="id">
												</td>
												<td height="30" style="padding-left: 8px; color: blue;"
													align="left">
													[
													<SPAN style="color: red"> <s:property
															value="hotName" />
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
												<td width="70" align="center">
													<s:property value="statusName" />
												</td>
												<td width="100" align="center">
													<a target="_blank" class="textbg4"
														href="knowledge_center_view.action?knowledge.id=<s:property value="id"/>">预览</a>
													<a
														href="knowledge_alterInit.action?knowledge.id=<s:property value="id"/>" class="textbg4">编辑</a>
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
			 		klform.action="knowledge_shlist.action";
			 		document.getElementById("pageNow").value=i;
			 		klform.submit();
			 	}
			 	function setHot(){
			 		klform.action="knowledge_sh.action";
			 		klform.submit();
			 	}
			 	function select_All(){
					var cks= document.getElementsByName("id");
					for(var i = 0 ; i < cks.length; i++){
						cks[i].checked= true;
					}
				}
				function select_Fan(){
					var cks= document.getElementsByName("id");
					for(var i = 0 ; i < cks.length; i++){
						cks[i].checked= !cks[i].checked;
					}
				}
				function select_Bux(){
					var cks= document.getElementsByName("id");
					for(var i = 0 ; i < cks.length; i++){
						cks[i].checked= false;
					}
				}
				function delKnowledge(){
					if(window.confirm("确定删除？")){
						var checkObj= document.getElementsByName("id");
						var billIDs = "";
					    for (i = 0; i < checkObj.length; i++) {
							if (checkObj[i].checked) {
							    if(billIDs!="")billIDs+=",";
								billIDs += checkObj[i].value;
							}
						 }
						if(billIDs==""){
						  alert("请选择要删除的记录！");
						  return ;
					    }
				 		klform.action="knowledge_del.action";
				 		klform.submit();
			 		}
			 	}
			 </script>
								<div style="text-align: center;">
									<wysLib:page></wysLib:page>

									<a href="javascript:select_All()" class="textbg4">全选</a>
									<a href="javascript:select_Fan()" class="textbg4">反选</a>
									<a href="javascript:select_Bux()" class="textbg4" style="width:70px">全不选</a>
									<input type="button" onClick="setHot();" value="通过审核" class="textbg4" style="width:80px"/>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="button" onClick="delKnowledge();" class="textbg4" value="删除" />
								</div>
							</s:else>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>