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
		<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
	</HEAD>
	<body>
		<form action="newsManage_list.action" method="post" name="nmList">
			<s:hidden name="isOk" value="1"/>
			<s:hidden name="news.id" id="newsId" />
			<s:hidden name="newsOp" id="newsOp" />
		</form>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="新闻搜索结果页" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">我发布的新闻公告</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="news_addInit.action">新闻公告添加</a>

			</li>-->
		</ul>

		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<table width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<td valign="top" id="tree_list_td">
					<s:if test="#request.csstr!=null">
						<wysLib:newsTypeTree href="combinationSearchNews.action?news.ntype.id="
							rootAble="true"></wysLib:newsTypeTree>
					</s:if>
					<s:else>
						<wysLib:newsTypeTree href="news_list.action?ntype.id="
							rootAble="true"></wysLib:newsTypeTree>
					</s:else>				  </td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
					</td>
					<td valign="top">
						<s:if test="newses.size==0">您没有发布新闻公告</s:if>
						<s:else>
							<table width="100%" align="center" cellpadding="1"
								cellspacing="1" bgcolor="#EBEBEB">
								<tr>
									<td width="20"></td>
									<th height="30" align="center" >
										新闻公告名称
									</th>
									<th width="120" height="30" align="center" >
										创建者									</th>
									<th width="110" height="30" align="center" >
										发布时间									</th>
									<th width="120" height="30" align="center" >
										所属类别									</th>
									<th width="120" height="30" align="center" >
										所属类型									</th>
									<th width="40" height="30" align="center" >
										热度									</th>
									<th width="80" height="30" align="center" >
										状态									</th>
									<th width="100" >									</th>
								</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
								<s:iterator value="newses">
									<tr>
						<td width="20" height="30" align="left" style="padding-left:8px;color:blue;">
							     			<input type="checkbox" value="<s:property value="id"/>" name="newId" id="id"> 
					      			  </td>
										<td height="30" style="padding-left:8px;color:blue;" align="left"> 
											<s:property value="title" />
										</td>
										<td width="120" height="30" align="center" >
												<s:property value="owner.realname" />
									  </td>
										<td width="110" height="30" align="center" >
											<s:date name="releasetime" format="yyyy-MM-dd HH:mm:ss" />
									  </td>
										<td width="120" height="30" align="center" >
											<s:property value="ntype.name" />
									  </td>
									  	<td width="120" height="30" align="center" >
											<s:property value="nstyle.name" />
									  </td>
										<td width="40" height="30" align="center" >
											<s:property value="hotName" />
									  </td>
										<td width="80" height="30" align="center" >
											<s:property value="status_tow_" />
									  </td>
										<td width="100" align="center" bgcolor="#FFFFFF">
											<a target="_blank" href="newsIndexView.action?news.id=<s:property value="id"/>&ntype.id=<s:property value="ntype.id" />" class="textbg4">预 览</a>
											<a href="javascript:doSubmit_1(2,'<s:property value="id"/>');" class="textbg4">编 辑</a>										</td>
									</tr>
								</s:iterator></tbody>
						  </table>
						</s:else>
					</td>
				</tr>
			</table>
			<form action="combinationSearchNews.action?str=" method="post" name="nlist">
				<s:hidden name="ntype.id" />
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />

			</form>
			<script type="text/javascript">
			function page(i){
				document.getElementById("pageNow").value=i;
				nlist.submit();
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
		</script>
			<form action="upNewHot.action" name="myForm" method="post">
				<input name="newIds" type="hidden" />
				<input name="resultPage" type="hidden" value="1" />
				<div style="margin-top:5px;">热度属性&nbsp;&nbsp;
			    <select name="news.hot">
							<option value="0">
								普通
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
							<option  value="4">
								头条
							</option>
							<!--<option value="2">
								热门
							</option>
							<option value="3">
								重点
							</option>
							<option value="5">
							 焦点 
							</option>
						--></select>
					<!-- <input type="submit" value="确认提交" />  -->
					<a href="javascript:select_All()" class="textbg4">全选</a>
					<a href="javascript:select_Fan()" class="textbg4">反选</a>
					<a href="javascript:select_Bux()" style="width:60px" class="textbg4">全不选</a>
					&nbsp;&nbsp;<a href="javascript:doSubmit();" style="width:70px" class="textbg4">确认提交</a>&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="javascript:doDelete();" style="width:70px" class="textbg4">申请删除</a>
				</div>
			</form>
			<wysLib:page></wysLib:page>
		</div>
		<!-- 内容 -->

	</BODY>
</HTML>
