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
	</HEAD>
	<body>
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
		<form action="newsManage_list.action" method="post" name="nmList">
			<s:hidden name="isOk" value="1" />
			<s:hidden name="news.id" id="newsId" />
			<s:hidden name="newsOp" id="newsOp" />
		</form>
		<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="新闻列表页" />
				</div>
			</li>
		</ul>

		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<table width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<td valign="top" width="100" id="tree_list_td">
						<s:if test="#request.csstr!=null">
							<wysLib:newsTypeTree
								href="combinationSearchNews.action?news.ntype.id="
								rootAble="true"></wysLib:newsTypeTree>
						</s:if>
						<s:else>
							<wysLib:newsTypeTree
								href="news_setpop_list.action?news.ntype.id=" rootAble="true"></wysLib:newsTypeTree>
						</s:else>
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
					</td>
					<td valign="top">
						<s:form action="news_setpop_list.action" method="post"
							name="newsQuery" theme="simple">
							<s:hidden name="pN" id="pageNow" />
							<s:hidden name="pS" />
							<input name="newIds" type="hidden" id="newIds" />
							<div>
								新闻名称&nbsp;
								<input type="text" name="news.title"
									value="<s:property value="news.title"/>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
								所属类别&nbsp;
								<select name="news.ntype.id" id="parentid">
									<wysLib:newsTypeSelect selectid="${news.ntype.id}"></wysLib:newsTypeSelect>
								</select>
								所属类型&nbsp;
								<s:select list="lnss" listKey="id" listValue="name" headerKey="0" headerValue="—全部—" name="news.nstyle.id"></s:select>
								创建者&nbsp;
								<input type="text" name="news.owner.realname"
									value="<s:property value="news.owner.realname"/>">
								&nbsp;&nbsp;&nbsp;
								<input type="submit" value="搜索" class="textbg4" />
							</div>
						</s:form>
						<s:if test="newses.size==0&&#request.newspop.id<=0">
							<span style="text-align:center;margin-top: 10px;">
								没有搜到新闻公告
							</span>
						</s:if>
						<s:else>
							<table width="100%" align="center" cellpadding="1"
								cellspacing="1" bgcolor="#EBEBEB">
								<tr>
									<td width="50" align="center"></td>
									<th width="260" height="30" align="center">
										新闻标题
									</th>
									<th width="100" height="30" align="center">
										创建者
									</th>
									<th width="90" height="30" align="center">
										发布时间
									</th>
									<th width="80" height="30" align="center">
										所属类别
									</th>
									<th width="80" height="30" align="center">
										所属类型
									</th>
									<th width="50" align="center">
										热度
									</th>
									<th width="50" height="30" align="center">
										状态
									</th>

									<th width="90" align="left">
									</th>
								</tr>
								<s:if test="#request.newspop.id>0">
									<tr>
										<td width="50" height="30" align="center" style="color: red;">
											弹窗新闻
										</td>
										<td height="30" align="left"
											style="padding-left: 8px; color: blue;">
											<s:property value="#request.newspop.title" />
										</td>
										<td height="30" align="center">
											<s:property value="#request.newspop.owner.realname" />
										</td>
										<td height="30" align="center">
											<s:date name="#request.newspop.releasetime"
												format="yyyy-MM-dd HH:mm" />
										</td>
										<td height="30" align="center">
											<s:property value="#request.newspop.ntype.name" />
										</td>
										<td width="50" align="center">
											<s:property value="#request.newspop.hotName" />
										</td>
										<td width="50" height="30" align="center">
											<s:property value="#request.newspop.status_tow_" />
										</td>
										<td width="90" align="left" bgcolor="#FFFFFF">
											<a target="_blank"
												href="newsIndexView.action?news.id=<s:property value="#request.newspop.id"/>"
												class="textbg4">预 览</a>
											<a href="javascript:doSubmit_setpopNo();" class="textbg4">取
												消</a>
										</td>
									</tr>
								</s:if>
								<tbody onMouseOut="changeback()" onMouseOver="changeto()">
									<s:iterator value="newses">
										<tr>
											<td width="50" height="30" align="center"
												style="padding-left: 8px; color: blue;">
												<input type="radio" value="<s:property value="id"/>"
													name="newId">
											</td>
											<td height="30" align="left"
												style="padding-left: 8px; color: blue;">
												<s:property value="title" />
											</td>
											<td height="30" align="center">
												<s:property value="owner.realname" />
											</td>
											<td height="30" align="center">
												<s:date name="releasetime" format="yyyy-MM-dd HH:mm" />
											</td>
											<td height="30" align="center">
												<s:property value="ntype.name" />
											</td>
											<td height="30" align="center">
												<s:property value="nstyle.name" />
											</td>
											<td width="50" align="center">
												<s:property value="hotName" />
											</td>
											<td width="50" height="30" align="center">
												<s:property value="status_tow_" />
											</td>
											<td width="90" align="left" bgcolor="#FFFFFF">
												<a target="_blank"
													href="newsIndexView.action?news.id=<s:property value="id"/>&ntype.id=<s:property value="ntype.id" />"
													class="textbg4">预 览</a>
											</td>
										</tr>
									</s:iterator>
								</tbody>
							</table>
						</s:else>
					</td>
				</tr>
			</table>
			<form action="news_setpop_list.action" method="post" name="nlist">
				<s:hidden name="ntype.id" />
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />

			</form>
			<script type="text/javascript">
			function page(i){
				document.getElementById("pageNow").value=i;
				newsQuery.submit();
			}
			function doSubmit_setpop(){
				if(window.confirm("确认设为弹窗？")){
					var checkObj = document.getElementsByName("newId");
				    var newIdArray = "";
				    for (i = 0; i < checkObj.length; i++) {
						if (checkObj[i].checked) {
						    if(newIdArray!="")newIdArray+=",";
							newIdArray += checkObj[i].value;
						}
					}
					if(newIdArray==""){
					  alert("请选中1条新闻！");
					  return ;
				    }
				    var newIds = document.getElementById("newIds");
				    newIds.value=newIdArray;
				    //alert(newIdArray);
				    //myForm.action="course_newassigntoUsers.action?status=0";
				    newsQuery.action="newsSetpop.action";
					newsQuery.submit();
				}
			}
			function doSubmit_setpopNo(){
				newsQuery.action="news_popNo.action";
				newsQuery.submit();
			}
		</script>

			<form action="newsSetpop.action" name="myForm" method="post">
				<%-- <input name="newIds" type="hidden" /> --%>
				<div style="margin-top: 5px;">
					&nbsp;&nbsp;
					<a href="javascript:doSubmit_setpop();" class="textbg4"
						style="width: 80px">设为弹窗</a>&nbsp;&nbsp;&nbsp;&nbsp;
					<%-- <a href="javascript:doDelete();">申请删除</a> --%>
				</div>
			</form>
			<wysLib:page></wysLib:page>
		</div>
	
	</body>
</HTML>