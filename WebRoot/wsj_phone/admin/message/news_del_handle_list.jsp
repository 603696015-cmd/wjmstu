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
			ToolsBarObj.ToolsBar_Add("toolbar_commitDelete","确认删除","images/newversion/un_view.gif","commitDeleteDetail()");
			ToolsBarObj.ToolsBar_Add("toolbar_nocommitDelete","不许删除","images/newversion/un_view.gif","nocommitDeleteDetail()");
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
				ToolsBarObj.ToolsBar_Disabled("toolbar_commitDelete");
				ToolsBarObj.ToolsBar_Disabled("toolbar_nocommitDelete");
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
				if(status == 7){
					ToolsBarObj.ToolsBar_Enabled("toolbar_commitDelete");
				}else{
					ToolsBarObj.ToolsBar_Disabled("toolbar_commitDelete");
				}
				if(status == 8){
					ToolsBarObj.ToolsBar_Enabled("toolbar_nocommitDelete");
				}else{
					ToolsBarObj.ToolsBar_Disabled("toolbar_nocommitDelete");
				}
				ToolsBarObj.ToolsBar_Enabled("toolbar_view");
			}else {
				ToolsBarObj.ToolsBar_Disabled("toolbar_view");
				ToolsBarObj.ToolsBar_Disabled("toolbar_commitDelete");
				ToolsBarObj.ToolsBar_Disabled("toolbar_nocommitDelete");
			}
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
		
		function commitDeleteDetail(){
			var obj = getCheckedCheckboxs(pp,status_tow,ntype);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp[0].toString();
			if(window.confirm("确认删除？")){
				doSubmit_2(1,value,pn,ps,7);
			}
		}
		
		function nocommitDeleteDetail(){
			var obj = getCheckedCheckboxs(pp,status_tow,ntype);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp.toString();
			doSubmit_2(1,value,pn,ps,8);
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
	</HEAD>
	<body>
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
			<table width="100%" cellpadding="0" cellspacing="1" bgcolor="#CFDBE2">
				<tr>
					<td width="100" valign="top" bgcolor="#F8FCFE" id="tree_list_td" style="display:none">
						<s:if test="#request.csstr!=null">
							<wysLib:newsTypeTree
								href="combinationSearchNews.action?news.ntype.id="
								rootAble="true"></wysLib:newsTypeTree>
						</s:if>
						<s:else>
							<wysLib:newsTypeTree
								href="news_del_handle_list.action?news.ntype.id="
								rootAble="true"></wysLib:newsTypeTree>
						</s:else>
				  </td>
					<td width="5px;" valign="middle" bgcolor="#F8FCFE" style="padding: 0px">
						<img src="images/leftmenu/main_55_1.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" id="showimg" />					</td>
					<td valign="top" bgcolor="#F8FCFE">
						
						<s:form action="news_del_handle_list.action" method="post"
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
								<br />
							</div>
						</s:form>
						<s:if test="newses.size==0">
							<h3 align="center" style="margin-top: 10px;">
								<br />没有符合条件的新闻公告删除申请
							</h3>
						</s:if>
						<s:else>
							<table width="100%" align="center" cellpadding="0"
								cellspacing="1" bgcolor="#CFDBE2">
								<tr>
									<td colspan=20 bgcolor="#F8FCFE">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="95" align="center"><div style="text-align: left;" id="showtree">
									<a href="javascript:showtree(true);" class="textbg5">显示新闻类别</a>
					</div></td>
    <td><div id="Div_ToolsBar"></div></td>
  </tr>
</table>

								  </td>
								</tr>
								<tr>
									<td width="20" bgcolor="#F8FCFE"></td>
									<th width="260" height="30" align="center" bgcolor="#F8FCFE">
										新闻标题									</th>
									<th width="100" height="30" align="center" bgcolor="#F8FCFE">
										创建者									</th>
									<th width="90" height="30" align="center" bgcolor="#F8FCFE">
										发布时间									</th>
									<th width="80" height="30" align="center" bgcolor="#F8FCFE">
										所属类别									</th>
									<th width="80" height="30" align="center" bgcolor="#F8FCFE">
										所属类型									</th>
									<th width="30" height="30" align="center" bgcolor="#F8FCFE">
										热度									</th>
									<th width="30" height="30" align="center" bgcolor="#F8FCFE">
										状态									</th>
									<!-- 
									<th width="50">
									</th>
									 -->
								</tr>
								<tbody onMouseOut="changeback()" onMouseOver="changeto()">
									<s:iterator value="newses">
										<tr>
											<td width='20' height='20' align='center' bgcolor="#F8FCFE">
												<input type='checkbox' onclick='clickcheckbox();' value="<s:property value="id" />" name='id_'/>
										  </td>
											<td height="30"
												align="left" bgcolor="#F8FCFE" style="padding-left: 8px; color: blue;">
												<s:property value="title" />
										  </td>
											<td height="30" align="center" bgcolor="#F8FCFE">
												<s:property value="owner.realname" />
										  </td>
											<td height="30" align="center" bgcolor="#F8FCFE">
												<s:date name="releasetime" format="yyyy-MM-dd HH:mm" />
										  </td>
											<td height="30" align="center" bgcolor="#F8FCFE">
												<s:property value="ntype.name" />
										  </td>
											<td height="30" align="center" bgcolor="#F8FCFE">
												<s:property value="nstyle.name" />
										  </td>
											<td height="30" align="center" bgcolor="#F8FCFE">
												<s:property value="hotName" />
										  </td>
											<td height="30" align="center" bgcolor="#F8FCFE">
												<s:property value="status_tow_" />
										  </td>
											<!-- 
											<td bgcolor="#FFFFFF" align="center">
												<a target="_blank"
													href="newsIndexView.action?news.id=<s:property value="id"/>&ntype.id=<s:property value="ntype.id" />"
													class="textbg4">预 览</a>
												<s:if test="status_tow==7">
													<a onClick="return confirm('确定删除？')" class="textbg4_2"
														href="javascript:doSubmit_2(1,'<s:property value="id"/>','<s:property value="pN"/>','<s:property value="pS"/>',7);">确认删除</a>
													<a class="textbg4_2"
														href="javascript:doSubmit_2(1,'<s:property value="id"/>','<s:property value="pN"/>','<s:property value="pS"/>',8);">不许删除</a>
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
	
	</body>
</HTML>
