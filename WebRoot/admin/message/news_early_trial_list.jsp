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
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript">
				function showtree(flag){
					var tree = document.getElementById("tree_list_td");
					var img = document.getElementById("showimg");
					if(flag){
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(false);" class="textbg5">隐藏新闻类别</a>';
					}
					else{
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(true);" class="textbg5">显示新闻类别</a>';
					}
				}
		</script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/newversion/jquery.toolsbar.js"></script>
		<script type="text/javascript">
			function Obj(pp_,status_tow_,ntype_){ 
			this.pp=pp_; 
			this.status_tow = status_tow_;
			this.ntype = ntype_;
		} 
		//按钮
		var ToolsBarObj = null;
		var pp = [];
		var status_tow = [];
		var ntype = [];
		var pn = '<s:property value="pN"/>';
		var ps = '<s:property value="pS"/>';
		$(function(){
			ToolsBarObj = $("#Div_ToolsBar");//存放按钮的div
			ToolsBarObj.ToolsBar_Add("toolbar_view","预 览","images/newversion/un_view.gif","viewDetail()");
			ToolsBarObj.ToolsBar_Add("toolbar_edit","编 辑","images/newversion/un_view.gif","editDetail()");
			ToolsBarObj.ToolsBar_Add("toolbar_commitapplication","提交申请","images/newversion/un_view.gif","commitapplicationDetail()");
			ToolsBarObj.ToolsBar_Add("toolbar_back","返 回","images/newversion/un_view.gif","backDetail()");
		});
		
		function clickcheckbox(){
			var obj = getCheckedCheckboxs(pp,status_tow,ntype);
			pp = obj.pp;
			status_tow = obj.status_tow;
			ntype = obj.ntype;
			var can_applicationDelete = false;
			var value = 0;
			var status = 0;
			var nty = 0;
			if(pp.length>1){
				ToolsBarObj.ToolsBar_Disabled("toolbar_view");
				ToolsBarObj.ToolsBar_Disabled("toolbar_edit");
				ToolsBarObj.ToolsBar_Disabled("toolbar_commitapplication");
				ToolsBarObj.ToolsBar_Disabled("toolbar_back");
				/**
				for(var i=0;i<pp.length;i++){
					status = status_tow[i];
					if(status != 7){
						can_commit = true;
					}else{
						can_commit = false;
					}
				}
				if(can_commit)
					ToolsBarObj.ToolsBar_Enabled("toolbar_applicationDelete");
				else
					ToolsBarObj.ToolsBar_Disabled("toolbar_applicationDelete");
				*/
			}else if(pp.length == 1){
				status = status_tow[0];
				if(status ==2  || status == 5){
					ToolsBarObj.ToolsBar_Enabled("toolbar_commitapplication");
				}else{
					ToolsBarObj.ToolsBar_Disabled("toolbar_commitapplication");
				}
				ToolsBarObj.ToolsBar_Enabled("toolbar_view");
				ToolsBarObj.ToolsBar_Enabled("toolbar_edit");
				ToolsBarObj.ToolsBar_Enabled("toolbar_back");
			}else {
				ToolsBarObj.ToolsBar_Disabled("toolbar_view");
				ToolsBarObj.ToolsBar_Disabled("toolbar_edit");
				ToolsBarObj.ToolsBar_Disabled("toolbar_commitapplication");
				ToolsBarObj.ToolsBar_Disabled("toolbar_back");
			}
		}
		
		function backDetail(){
			var obj = getCheckedCheckboxs(pp,status_tow,ntype);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp.toString();
			doSubmit_2(1,value,pn,ps,3);
		}
		
		function viewDetail(){
			var obj = getCheckedCheckboxs(pp,status_tow,ntype);
			pp = obj.pp;
			ntype = obj.ntype;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp[0].toString();
			var nty = ntype[0].toString();
			width=screen.availWidth * 0.8;
			height=screen.availHeight * 0.8;
			window.open ("newsIndexView.action?news.id="+value+"&ntype.id="+nty+"", '新闻预览', 'height='+height+', width='+width+', toolbar=no, menubar=yes, scrollbars=yes, resizable=yes,location=no, status=no') ;
		}
		
		function editDetail(){
			var obj = getCheckedCheckboxs(pp,status_tow,ntype);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp[0].toString();
			doSubmit_1(2,value);
		}
		
		function commitapplicationDetail(){
			var obj = getCheckedCheckboxs(pp,status_tow,ntype);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp.toString();
			doSubmit_2(1,value,pn,ps,2);
		}
		
		
		//获取选中的checkbox
		function getCheckedCheckboxs(pp,status_tow,ntype){
			var checkboxs = document.getElementsByName("id_");
			if(checkboxs.length>0){
				if(pp.length>0)  pp=[];
				for(var i=0;i<checkboxs.length;i++){
					if(checkboxs[i].checked){
						pp.push(checkboxs[i].value);
						status_tow.push(document.getElementById("status_tow_"+i).value);
						ntype.push(document.getElementById("ntype_"+i).value);
					}
				}
			}
			var obj = new Obj(pp,status_tow,ntype);
			return obj;
		}
		</script>
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
		<form action="news_early_trial_list.action" method="post"
			name="nmList">
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
					<td valign="top" width="100" id="tree_list_td" style="display:none">
						<s:if test="#request.csstr!=null">
							<wysLib:newsTypeTree
								href="combinationSearchNews.action?news.ntype.id="
								rootAble="true"></wysLib:newsTypeTree>
						</s:if>
						<s:else>
							<wysLib:newsTypeTree
								href="news_early_trial_list.action?news.ntype.id="
								rootAble="true"></wysLib:newsTypeTree>
						</s:else>
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55_1.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" id="showimg" />
					</td>
					<td valign="top">
						
						<s:form action="news_early_trial_list.action" method="post"
							name="newsQuery" theme="simple">
							<s:hidden name="pN" id="pageNow" />
							<s:hidden name="pS" />
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
								<input type="button" onClick="newsSubmit();" class="textbg4" value="搜索" />
							</div>
						</s:form>
						<s:if test="newses.size==0">
							<h3 align="center" style="margin-top: 10px;">
								没有符合条件的等待初审的新闻公告
							</h3>
						</s:if>
						<s:else>
							<table width="100%" align="center" cellpadding="1"
								cellspacing="1" bgcolor="#EBEBEB">
								<tr>
								<td colspan=20>
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100" style="border-bottom:1px solid black;"><div style="text-align: left;" id="showtree">
									<a href="javascript:showtree(true);" class="textbg5">显示新闻类别</a>
					</div></td>
    <td><div id="Div_ToolsBar"></div></td>
  </tr>
</table>

								</td>
								</tr>
								<tr>
									<th width="20" ></th>
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
									<th width="30" height="30" align="center">
										热度
									</th>
									<th width="80" height="30" align="center">
										状态
									</th>
									<!-- 
									<th width="50">
									</th>
									 -->
								</tr>
								<tbody onMouseOut="changeback()" onMouseOver="changeto()">
									<s:iterator value="newses" status="status">
										<tr>
											<td width='20' height='20' align='center'>
												<input type='checkbox' onclick='clickcheckbox();' value="<s:property value="id" />" name='id_'/>
											</td>
											<td height="30" style="padding-left: 8px; color: blue;"
												align="left">
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
											<td height="30" align="center">
												<s:property value="hotName" />
											</td>
											<td width="80" height="30" align="center">
												<s:property value="status_tow_" />
											</td>
											 <input type="hidden" id="status_tow_<s:property value='#status.index'/>" value="<s:property value='status_tow' />"/>
									  		<input type="hidden" id="ntype_<s:property value='#status.index'/>" value="<s:property value="ntype.id" />"/>
											<!-- 
											<td bgcolor="#FFFFFF" align="center">
												<a target="_blank"
													href="newsIndexView.action?news.id=<s:property value="id"/>&ntype.id=<s:property value="ntype.id" />"
													class="textbg4">预 览</a>
												<s:if test="status_tow==2||status_tow==5">
													<a
														href="javascript:doSubmit_1(2,'<s:property value="id"/>');"
														class="textbg4">编 辑</a>
													<a class="textbg6"
														href="javascript:doSubmit_2(1,'<s:property value="id"/>','<s:property value="pN"/>','<s:property value="pS"/>',2);">提交申请</a>
													<a class="textbg4"
														href="javascript:doSubmit_2(1,'<s:property value="id"/>','<s:property value="pN"/>','<s:property value="pS"/>',3);">返
														回</a>
												</s:if>
											</td>
											 -->
										</tr>
									</s:iterator>
								</tbody>
							</table>
						</s:else>
					</td>
				</tr>
			</table>
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
			<wysLib:page></wysLib:page>
		</div>
	</BODY>
</HTML>
