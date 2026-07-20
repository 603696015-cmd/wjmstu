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
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript">
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
			
			function del(){
			  if(window.confirm("确定删除？")){
			     var checkObj = document.getElementsByName("id");
				    var billIDs = "";
				    for (i = 0; i < checkObj.length; i++) {
						if (checkObj[i].checked) {
						    if(billIDs!="")billIDs+=",";
							billIDs += checkObj[i].value;
						}
					 }
					if(billIDs==""){
					  alert("请至少选择一个复选框！");
					  return ;
				    }
				   var newsesids = document.getElementById("newsesids");
			       newsesids.value=billIDs;
				   assign.submit();
				}
			}
		</script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<form action="newsManage_list.action" method="post" name="nmList">
			<s:hidden name="isOk" value="1"/>
			<s:hidden name="news.id" id="newsId" />
			<s:hidden name="newsOp" id="newsOp" />
			<input type="hidden" name="type" value=1 />
		</form>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="新闻列表页" /></div>
			</li>
			<!--<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="news_addInit.action">新闻公告添加</a>

			</li>-->
		</ul>

		<!-- 内容 -->
		<div style="margin-top: 20px; text-align: center;">
			<table width="100%">
				<tr>
					<td valign="top" width="100" id="tree_list_td">
						<wysLib:newsTypeTree href="newsList.action?ntype.id="
							rootAble="true"></wysLib:newsTypeTree>
				  	</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
					</td>
					<td valign="top">
							<form action="newsList.action" method="post" name="newsQuery">
								<s:hidden name="pN" id="pageNow" />
								<s:hidden name="pS" />
								<div>
									新闻名称&nbsp;<input type="text" name="news.title" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									栏目&nbsp;
									<select name="ntype.id" id="parentid"> 
										<wysLib:newsTypeSelect selectid="${news.ntype.id}"></wysLib:newsTypeSelect>
									</select>
									&nbsp;&nbsp;&nbsp;
									<!-- 创建者&nbsp;<input type="text" name="news.owner.realname" value="<s:property value="news.owner.realname"/>">&nbsp;&nbsp;&nbsp; -->
									<input type="button" onClick="newsSubmit();" value="搜索" />
								</div> 
							</form>
						<s:if test="newses.size==0"><h3 align="center" style="margin-top:10px;">没有搜到新闻公告</h3></s:if>
						<s:else>
							<table width="100%" align="center" cellpadding="2"
								cellspacing="2" bgcolor="#EBEBEB">
								<tr>
									<th width="20"></th>
									<th width="260" height="30" align="center" >
										新闻标题									</th>
									<!-- <th width="100" height="30" align="center" >
										创建者									</th> -->
									<th width="90" height="30" align="center" >
										发布时间									</th>
									<th width="80" height="30" align="center" >
										所属栏目									</th>
									<th width="30" height="30" align="center" >
										热度									</th>
									<th width="70" height="30" align="center" >
										状态									</th>
									
									<th width="50" >									</th>
								</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
								<s:iterator value="newsList">
									<tr>
										<td width="20" height="20" align="center">
											<input type="checkbox" value="<s:property value="id"/>"
												name="id">
										</td>
										<td height="30" style="padding-left:8px;color:blue;" align="left">
											<s:property value="title" />
									    </td>
										<!-- <td height="30" align="center" >
												<s:property value="owner.realname" />
										</td> -->
										<td height="30" align="center" >
											<s:date name="releasetime" format="yyyy-MM-dd HH:mm" />
										</td>
										<td height="30" align="center" >
											<s:property value="ntype.name" />
										</td>
										<td height="30" align="center" >
											<s:property value="hotName" />
										</td>
										<td width="70" height="30" align="center" >
											<s:property value="status_tow_" />
									  </td>
										<td bgcolor="#FFFFFF" align="left">
											 <a target="_blank" href="newsIndexView.action?news.id=<s:property value="id"/>&ntype.id=<s:property value="ntype.id" />" class="textbg4">预 览</a> 
											<s:if test="status_tow==1||status_tow==3">
											<%-- <a href="news_alterInit.action?news.id=<s:property value="id"/>" class="textbg4">编 辑</a> --%>
												 <a href="javascript:doSubmit_1(2,'<s:property value="id"/>');" class="textbg4">编 辑</a>
											</s:if>
										    <s:if test="status_tow==1">
										   		<%-- <a class="textbg4_2" href="upNewsStatus.action?pN=0&pS=10&news.id=<s:property value="id"/>&newsOp=1">创建完成</a> --%>
										   		<a class="textbg4_2" href="javascript:doSubmit_2(1,'<s:property value="id"/>','<s:property value="pN"/>','<s:property value="pS"/>',1);">创建完成</a>
										    </s:if>
										     <s:elseif test="status_tow==3">
										      <%--  <a class="textbg4_2" href="upNewsStatus.action?pN=0&pS=10&news.id=<s:property value="id"/>&newsOp=1">重申初审</a> --%>
										      <a class="textbg4_2" href="javascript:doSubmit_2(1,'<s:property value="id"/>','<s:property value="pN"/>','<s:property value="pS"/>',1);">创建完成</a>
										    </s:elseif>
									  </td>
									</tr>
								</s:iterator></tbody>
						  </table>
						</s:else>
					</td>
				</tr>
			</table>
			
			<%-- 
			<form action="newsManage_list.action" method="post" name="nlist">
				<s:hidden name="ntype.id" />
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
			</form>
			 --%>
			<script type="text/javascript">
			function page(i){
				document.getElementById("pageNow").value=i;
				newsQuery.submit();
			}
			function newsSubmit(){
				document.getElementById("pageNow").value=0;
				newsQuery.submit();
			}
		</script>
			<br>
			<a href="javascript:select_All()" />全选</a>
			<a href="javascript:select_Fan()" />反选</a>
			<a href="javascript:select_Bux()" />全不选</a>
			<a href="javascript:del()" />删除</a>
			<s:form action="deleteNewses.action" method="post" name="assign" >
				<s:hidden name="newsesids" id="newsesids" />
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
			</s:form>
			<wysLib:page></wysLib:page>
			
			<a href="pfms_news_addInit.action" class="textbg" />发布新闻</a>
		</div>
	</BODY>
</HTML>
										   