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
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
				function showtree(flag){
					var tree = document.getElementById("tree_list_td");
					var img = document.getElementById("showimg");
					if(flag){
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(false);" class="textbg5">隐藏题库类别</a>';
					}
					else{
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(true);" class="textbg5">显示题库类别</a>';
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
					<wysLib:Navigation ivalue="试题列表" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">管理试题 </span>
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
			<table width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<td valign="top" id="tree_list_td" style="display:none">
						<wysLib:qlibtree
							href="question_list.action?sublibs=1&question.status=-1&question.qlib.id="
							rootAble="true"></wysLib:qlibtree>
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55_1.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" id="showimg" />
					</td>
					<td valign="top">
						
						<form action="question_list.action" method="post" name="pages">
							<s:hidden name="pN" id="pageNow"></s:hidden>
							<s:hidden name="pS"></s:hidden>
							<s:hidden name="question.qlib.id" />
							<table width="100%" align="center" cellspacing="1"
								cellpadding="1" bgcolor="#D1E4F5">
								<tr>
								  <td bgcolor="#F8FCFE"><div style="text-align: left; width:95px;" id="showtree">
									<a href="javascript:showtree(true);" class="textbg5">显示题库类别</a>
								</div></td>
									<td bgcolor="#F8FCFE">
										包含下级节点:
							    <input type="checkbox" name="sublibs" value="1"
											<s:if test="sublibs == 1">checked="checked"</s:if>>									</td>
									<td bgcolor="#F8FCFE">
										试题名称：
										
						        <input type="text" name="question.title"
											value="<s:property value="question.title"/>" />									</td>
									<td bgcolor="#F8FCFE">
										创建者:
							    <input type="text" name="elUser.realname"
											value="<s:property value="elUser.realname"/>" />									</td>
									<td bgcolor="#F8FCFE">
										创建时间范围:
									  <input type="text" onclick=setday(this)
											name="question.createtime"
											value="<s:date name="question.createtime" format="yyyy-MM-dd HH:mm"/>">
										&nbsp;~&nbsp;
										<input type="text" onclick=setday(this)
											name="question.createtimeEnd"
											value="<s:date name="question.createtimeEnd" format="yyyy-MM-dd HH:mm"/>">									</td>
								</tr>
								<tr>

									<td colspan="4" bgcolor="#F8FCFE" style="">
										题型：
										<s:select theme="simple" headerKey="0" headerValue="全部"
											name="question.qtype"
											list="#{1:'判断题',2:'单选题',4:'多选题',5:'填空题',6:'问答题',7:'材料题',8:'打字题',9:'邮件题',10:'搜索题',11:'office题',15:'看图选择',16:'看动画选择',17:'角色扮演',18:'听音选图',19:'拖拽题',20:'排序题'}"
											value="question.qtype" />
										&nbsp;
										试题状态：
										<s:select theme="simple" headerKey="-1" headerValue="全部"
											name="question.status"
											list="#{0:'正常使用',1:'作废',2:'编辑中'}"
											value="question.status" />									</td>
									<td colspan="3" bgcolor="#F8FCFE" style="">
										<input class="textbg4" onClick="document.getElementById('pageNow').value=0" style="height: 25px;" type="submit"
											value="搜索">									</td>
								</tr>
							</table>
						</form>
						<!-- <form action="question_delete.action" method="post"
								name="question_manage" id="question_manage"> -->
						<s:form action="question_delete_status" method="post"
							name="question_manage" id="question_manage">

							<s:if test="questions.size==0">没有符合条件的试题</s:if>
							<s:else>
								<table width="100%" align="center" cellpadding="1"
									cellspacing="1">
									<tr>
										<th height="30" align="center">&nbsp;
											
										</th>
										<th height="30" align="center">
											题干 
										</th>
										<th height="30" align="center">
											题目类型
										</th>
										<th height="30" align="center">
											所属题库
										</th>
										<!--<th height="30" align="center" >
											创建时间
										</th>-->
										<th height="30" align="center">
											状态
										</th>
										<th width="60" height="30" align="center">&nbsp;
											
										</th>
									</tr>
									<tbody onMouseOut="changeback()" onMouseOver="changeto()">
										<s:iterator value="questions">
											<tr>
												<td height="30" style="padding-left: 8px; color: blue;"
													align="left">
													<input type="checkbox" name="questions.id"
														value="<s:property value="id"/>">
												</td>
												<td height="30" style="padding-left: 8px; color: blue;"
													align="left">
													<%-- 
													<s:if test="status==1">
														<a name="tdTitle" title="<s:property value="title" />"
															href="question_view_status.action?question.id=<s:property value="id" />"
															target="_blank"> <s:property value="title" /> </a>
													</s:if>
													<s:else>
													 --%><s:if test="qtype==15||qtype==16||qtype==17||qtype==18||qtype==19||qtype==20">
													 	<a name="tdQexplain" title="<s:property value="qexplain" />"
															href="question_view.action?question.id=<s:property value="id" />">
															<s:if test="qexplain.length()>15">
																<s:property value="qexplain.substring(0,15)" />...
															</s:if>
															<s:else>
																<s:property value="qexplain" />
															</s:else>
													 </s:if><s:else>
														<a name="tdTitle" title="<s:property value="title" />"
															href="question_view.action?question.id=<s:property value="id" />">
															<s:property value="title" /> </a>
															</s:else>
													<%-- </s:else> --%>
												</td>
												<td height="30" align="center">
													<s:property value="qtypeName" />
												</td>
												<td height="30" align="center">
													<s:property value="qlib.name" />
												</td>
												<td height="30" align="center">
													<s:property value="statusName" />
												</td>
												<!--<td height="30" align="center" >
												<s:date format="yyyy-MM-dd HH:mm:ss" name="createtime" />
											</td>-->
												<td width="220" height="30" align="left">
													<s:if test="qtype==7">
														<a href="question_alterInit.action?copy=1&question.id=<s:property value="id" />&isCaiLiao=1"
															class=textbg4>复 制</a>
													</s:if>
													<s:else>
														<a href="question_alterInit.action?copy=1&question.id=<s:property value="id" />"
															class=textbg4>复 制</a>
													</s:else>
														<a href="question_view.action?question.id=<s:property value="id" />"
														class=textbg4>预 览</a>
														<%-- <a href="question_alterInit.action?question.id=<s:property value="id" />" class=textbg4>编 辑</a> --%>
														<!-- 当如果是编辑中才显示编辑 -->
														<s:if test="status==2">
															<s:if test="qtype==7">
																<%-- <a href="questionchild_addInit.action?question.qtype=2&question.parent.id=<s:property value="id" />" class=textbg6>添加小题</a> --%>
																<%-- <a href="question_view.action?question.id=<s:property value="id" />" class=textbg6>添加小题</a> --%>
																<a href="question_alterInit.action?question.id=<s:property value="id" />&isCaiLiao=1"
																	class="textbg6">管理小题</a>
															</s:if>
															<s:else>
																<a href="question_alterInit.action?copy=0&question.id=<s:property value="id" />"
																class="textbg4">编 辑</a>
															</s:else>
														</s:if>
												</td>
											</tr>
										</s:iterator>
									</tbody>
								</table>
							</s:else>
							<script type="text/javascript">
										function toexcel(exprot) { 
											question_manage.action = "question_list.action";
											document.getElementById("exprot").value=exprot;
											question_manage.submit();
										} 
										function CheckSelectExcel() { 
											 width=600;
											 height=500; 
										  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
											 var rv = window.showModalDialog("question_select_impExecelInit.action",null,sFeature); 
										} 
										function toSelectExcel(SelectExprot) { 
											document.getElementById("exprot").value=false;
											question_manage.action = "question_list.action"; 
											document.getElementById("SelectExprot").value=SelectExprot;
											question_manage.submit();
										} 
										function page(i){
											document.getElementById("exprot").value=false;
											pages.action = "question_list.action";
											document.getElementById("pN").value=i;
											pages.submit();
										}
										function titleLimit(){ 
											var obj = document.getElementsByName("tdTitle");
											for(var i = 0 ;i <obj.length;++i){
												hiddenTitle(i);
											}
										}
										function showTitle(i){
											var obj = document.getElementsByName("tdTitle");
											for(var i = 0 ;i <obj.length;++i){
												if(i==j){
												obj[i].innerHTML = obj[i].title; 
												// +"<a href=\"javascript:hiddenTitle("+i+")\">隐藏</a>" ;
												}
											}
										}
										function hiddenTitle(j){
											var obj = document.getElementsByName("tdTitle");
											for(var i = 0 ;i <obj.length;++i){
												if(i==j){
													if(obj[i].title.length>20) 
														obj[i].innerHTML = obj[i].title.substring(0,30)+"... " ;
												}
											} 
										}
										titleLimit();
										
										function AutomateExcel() 
								{ 
								// Start Excel and get Application object. 
								var oXL = new ActiveXObject("Excel.Application"); 
								// Get a new workbook. 
								var oWB = oXL.Workbooks.Add(); 
								var oSheet = oWB.ActiveSheet; 
								var table = document.all.data; 
								var hang = table.rows.length; 
								
								var lie = table.rows(0).cells.length; 
								
								// Add table headers going cell by cell. 
								for (i=0;i＜hang;i++) 
								{ 
								for (j=0;j＜lie;j++) 
								{ 
								oSheet.Cells(i+1,j+1).value = table.rows(i).cells(j).innerText; 
								} 
								
								} 
								oXL.Visible = true; 
								oXL.UserControl = true; 
								} 
								 
								function select_All(){
									var cks= document.getElementsByName("questions.id");
									for(var i = 0 ; i < cks.length; i++){
										cks[i].checked= true;
									}
								}
								function select_Fan(){
									var cks= document.getElementsByName("questions.id");
									for(var i = 0 ; i < cks.length; i++){
										cks[i].checked= !cks[i].checked;
									}
								}
								function select_Bux(){
									var cks= document.getElementsByName("questions.id");
									for(var i = 0 ; i < cks.length; i++){
										cks[i].checked= false;
									}
								}
								//修改试题状态
								function updateStatus() {
											document.getElementById("exprot").value=false;
											question_manage.action = "question_update_status.action";
											question_manage.submit();
								} 
								</script>
							<wysLib:page></wysLib:page>
							<a href="javascript:select_All()" />全选</a>
							<a href="javascript:select_Fan()" />反选</a>
							<a href="javascript:select_Bux()" />全不选</a>
							<br>
							<input class=textbg6 style="height:35px;" type="button"
								 value="添加试题" onClick="javascript:document.location.href='question_add_type.action?question.qlib.id=<s:property value="question.qlib.id"/>';">
							<input class=textbg4 style="height: 35px;width:55px;" type="submit"
							 onclick="return confirm('确定作废这些试题？')"	value="作 废">
							 <input class=textbg4 style="height: 35px;width:55px;" type="submit"
							 onclick="return confirm('确定删除这些试题？')"	value="删 除">
							<!-- 创建完成 -->
							<!-- <input class=textbg4 style="width: 70px; height: 35px"
								type="button" value="创建完成" onclick="updateStatus()"> -->
							<input class=textbg6 style="width: 95px; height: 35px;"
								type="button" value="添加到导出箱" onClick="toSelectExcel(true)">
							<!-- <input class=textbg6 style="height:35px;" type="button" value="查看导出箱" onclick="CheckSelectExcel(true)"> -->
							<%-- <a href="question_select_impExecelInit.action" target="_blank" class=textbg6>查看导出箱</a> --%>
							<input class=textbg6 style="width: 95px; height: 35px;"
								type=button
								onClick="document.location='question_select_impExecelInit.action'"
								value="查看导出箱">
							<input class=textbg6 style="height: 35px;" type="button"
								value="导入试题"
								onclick='window.location = "question_importByqlibInit.action?questionLib.id=<s:property value="question.qlib.id"/>"'>
							<!-- <input class=textbg6 style="height:35px;" type="button" value="导出试题"
									onclick='window.location = "question_exportExcel.action?questionLib.id=<s:property value="question.qlib.id"/>&str=questionexecl"'>  -->
							<input class=textbg6 style="height: 35px;" type="button"
								value="导出试题" onClick="toexcel(true);">
							<!--  -->
							<input class=textbg6 style="height:35px;" type="button" value="试题类别"
									onclick='window.location = "question_lib_list.action"'>	
								
								
								
								
							<s:hidden name="question.qtype" />
							<s:hidden name="question.title" />
							<s:hidden name="question.qlib.id" />
							<s:hidden name="question.status" />
							<s:hidden name="pN" id="pN" />
							<s:hidden name="pS" />
							<s:hidden name="exprot" id="exprot" />
							<s:hidden name="sublibs" />
							<s:hidden name="SelectExprot" id="SelectExprot" />
							<!--<input class=textbg6 style="height:35px;" type="button" value="重新搜索"
									onclick='window.location = "question_listInit.action"'>
								<input class=textbg6 style="height:35px;" type="button" value="导入试题"
									onclick='window.location = "question_importByqlibInit.action?questionLib.id=<s:property value="question.qlib.id"/>"'>
								<input class=textbg6 style="height:35px;" type="button" value="导出试题"
									onclick='window.location = "question_exportExcel.action?questionLib.id=<s:property value="question.qlib.id"/>&str=questionexecl"'>
							-->
						</s:form>
						<%-- 
						<input class=textbg6 style="height:35px;" type="button" value="重新搜索"
									onclick='window.location = "question_listInit.action"'>
						 --%>
						<%-- 
								<input class=textbg6 style="height:35px;" type="button" value="导入试题"
									onclick='window.location = "question_importByqlibInit.action?questionLib.id=<s:property value="question.qlib.id"/>"'>
								<!-- <input class=textbg6 style="height:35px;" type="button" value="导出试题"
									onclick='window.location = "question_exportExcel.action?questionLib.id=<s:property value="question.qlib.id"/>&str=questionexecl"'>  -->
								<input class=textbg6 style="height:35px;" type="button" value="导出结构试题" onclick="toexcel(true);">
						 --%>
					</td>
				</tr>
			</table>
			<div style="margin-top:20px">&nbsp;</div>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
<!--    竞赛中心>试题与试卷>试题管理（jsp）         -->
