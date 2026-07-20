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
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
				function showtree(flag){
					var tree = document.getElementById("tree_list_td");
					var img = document.getElementById("showimg");
					if(flag){
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(false);" class="textbg5">隐藏卷库类别</a>';
					}
					else{
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(true);" class="textbg5">显示卷库类别</a>';
					}
				}
		</script>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((   this .   sectionRowIndex %   2 ==   0)
		? 
		 "#ffffff" :   "#f4f4f4" )
}
</style>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="试卷列表" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">管理试卷</span>
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
			<s:form action="exampaper_list" name="myelist" theme="simple">
				<table width="100%" cellpadding="1" cellspacing="1">
					<tr>
						<td valign="top" id="tree_list_td" style="display:none">
							<wysLib:elibtree
								href="exampaper_list.action?sublibs=1&examPaper.epl.id="
								rootAble="true"></wysLib:elibtree>
						</td>
						<td valign="middle" width="5px;" style="padding: 0px">
							<img src="images/leftmenu/main_55_1.gif" style="cursor: hand"
								onclick="changeTreeDisplay(this)" id="showimg" />
						</td>
						<td valign="top">
							
							<table width="100%" align="center" cellspacing="1"
								cellpadding="1" bgcolor="#D1E4F5">
								<tr>
								<td bgcolor="#F8FCFE">
								<div style="text-align: left;" id="showtree">
									<a href="javascript:showtree(true);" class="textbg5">显示卷库类别</a>						  </div>
							<s:hidden name="pN" id="pageNow"></s:hidden>
							<s:hidden name="pS"></s:hidden>
							<s:hidden name="examPaper.epl.id"></s:hidden>								</td>
									<td bgcolor="#F8FCFE">
										包含下级节点:
								  <input type="checkbox"
											
										<s:if test="sublibs==1">checked='checked'</s:if>
											name="sublibs" value="1">								  </td>
									<td bgcolor="#F8FCFE">
										试卷名称:
							    <input type="text" name="examPaper.title"
											value="${examPaper.title}" />								  </td>
									<td bgcolor="#F8FCFE">
										创建者:
							    <input type="text" name="examPaper.elUser.realname"
											value="${examPaper.elUser.realname}" />								  </td>
									<td bgcolor="#F8FCFE">
										创建时间范围:
									  <input type="text" onclick=setday(this)
											name="examPaper.createtime"
											value="<s:date name="examPaper.createtime" format="yyyy-MM-dd HH:mm"/>">
										&nbsp;~&nbsp;
										<input type="text" onclick=setday(this)
											name="examPaper.createtimeEnd"
											value="<s:date name="examPaper.createtimeEnd" format="yyyy-MM-dd HH:mm"/>">									</td>
								</tr>
								<tr>
									<td colspan="3" bgcolor="#F8FCFE" style="">
										试卷状态：
										<s:select theme="simple" headerKey="-1" headerValue="全部"
											name="examPaper.status"
											list="#{0:'正常使用',1:'作废',2:'编辑中'}"
											value="examPaper.status" />									</td>
									<td colspan="2" bgcolor="#F8FCFE">
								  <input class="textbg4" style="height: 25px;" type="submit" onClick="document.getElementById('pageNow').value=0"
											value="搜索">									</td>
								</tr>
							</table>
						  <s:if test="examPapers.size==0">没有符合条件的试卷</s:if>
							<s:else>
								<table width="100%" align="center" cellspacing="1"
									cellpadding="1">
									<tr>
										<th width="30" height="30" align="center">&nbsp;
											
										</th>
										<th width="200" height="30" align="center">
											试卷标题
										</th>
										<th width="100" height="30" align="center">
											创建者
										</th>
										<th width="90" height="30" align="center">
											所属试卷库
										</th>
										<th width="80" height="30" align="center">
											试卷时长
										</th>
										<th width="40" height="30" align="center">
											总分
										</th>
										<th width="110" height="30" align="center">
											创建时间
										</th>
										<th width="70" height="30" align="center">
											状态
										</th>
										<th width="70" height="30" align="center">&nbsp;
											
										</th>
										<!--<th height="30" align="center" >
											&nbsp;
										</th>
									-->
									</tr>
									<tbody onMouseOut="changeback()" onMouseOver="changeto()">
										<s:iterator value="examPapers">
											<tr>
												<td width="40" height="30" align="center">
													<input type="checkbox" name="examPapers1.id"
														value="<s:property value="id"/>">

												</td>
												<td height="30" align="center">
													<!--<a
													href="exampaper_view.action?examPaper.id=<s:property value="id" />"> </a>-->
													<s:property value="title" />
													<s:hidden name="title"></s:hidden>
												</td>
												<td width="100" height="30" align="center">
													<s:property value="elUser.realname" />
												<td width="90" height="30" align="center">
													<s:property value="epl.name" />
												</td>
												<td width="80" height="30" align="center">
													<s:property value="during" />
												</td>
												<td width="40" height="30" align="center">
													<s:property value="ep_tscore" />
												</td>
												<td width="110" height="30" align="center">
													<s:date format="yyyy-MM-dd HH:mm:ss" name="createtime" />
												</td>
												<td width="110" height="30" align="center">
													<s:property value="statusName" />
												</td>
												<td width="140" height="30" align="center" colspan="2">
													<a target="_blank"
														href="exampaper_copy.action?examPaper.id=<s:property value="id" />"
														class=textbg4>复 制</a>
														
														<a
															href="exampaper_all_alterinit.action?examPaper.id=<s:property value="id" />"
															class=textbg4>修 改</a>
													<!--
													<s:if test="status != 1&&isEditor==0">
														<a
															href="exampaper_all_alterinit.action?examPaper.id=<s:property value="id" />"
															class=textbg4>修 改</a>
													</s:if>
													-->
													<a
														href="exampaper_details.action?examPaper.id=<s:property value="id" />"
														class=textbg4>预 览</a>
												</td>
												<!--<td height="30" align="center" >
												<a
													href="epread_quizlist.action?examPaper.id=<s:property value="id" />">阅卷</a>
											</td>
										-->
											</tr>
										</s:iterator>
									</tbody>
								</table>
								<br>
								<script>
									function page(i){
										document.getElementById("pageNow").value=i;
										myelist.action = "exampaper_list.action";
										myelist.submit();
										
									}
									function epDelete(){
										if(confirm('确定删除这几个？')){
											myelist.action = "exampaper_delete_status.action";
											myelist.submit();
											return true;
										}
										else 
											return false;
											
									}
								</script>
								<wysLib:page></wysLib:page>
							</s:else>
							<br/>
							<input class=textbg6 style="height:35px;" type="button"
										value="添加试卷" onClick="javascript:document.location.href='exampaper_addInit.action';">
							<s:if test="examPapers.size>0">
								<input class=textbg6 style="height:35px;" type="button"
									value="删 除" onClick="return epDelete()">
							</s:if>
							<input class=textbg6 style="height:35px;" type="button"
										value="试卷类别" onClick="javascript:document.location.href='exampaperLib_list.action';">
						
							
						</td>
					</tr>
				</table>
			</s:form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
