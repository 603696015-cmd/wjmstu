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
.textbg4{margin-top:2px}
.textbg6{margin-top:2px}
</style>
		<script type="text/javascript" src="js/libutil.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
		function showtree(flag){
					var tree = document.getElementById("tree_list_td");
					var img = document.getElementById("showimg");
					if(flag){
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(false);" class="textbg5">隐藏考场树</a>';
					}
					else{
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(true);" class="textbg5">显示考场树</a>';
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
					<wysLib:Navigation ivalue="考场列表" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">申请修改审核列表</span>
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
			<table width="1100" cellpadding="1" cellspacing="1">
				<tr>
					<td valign="top" id="tree_list_td" style="display:none">
						<wysLib:eroomLibTree
							href="examroom_alter_list.action?sublibs=1&eroomLib.id="
							rootAble="true"></wysLib:eroomLibTree>
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55_1.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" id="showimg" />
					</td>
					<td valign="top">
						<form action="examroom_alter_list.action" method="post"
							name="examFh">
							<s:hidden name="pN" id="pageNow" />
							<s:hidden name="pS" />
                            <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#D1E4F5">
  <tr>
<td width="100" rowspan="2" bgcolor="#F8FCFE"><div style="text-align: left;" id="showtree">
									<a href="javascript:showtree(true);" class="textbg5">显示考场树</a>
</div></td>
    <td width="170" bgcolor="#F8FCFE">包含下级节点:
<input type="checkbox" name="sublibs"
									<s:if test="sublibs==1">checked="checked"</s:if> value="1">
								&nbsp;&nbsp;&nbsp;</td>
    <td width="380" bgcolor="#F8FCFE">考场标题：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input size="16" type="text" name="examRoom.title"
									value="<s:property value="examRoom.title"/>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 状态：&nbsp;
								<s:select theme="simple" headerKey="-1" headerValue="全部"
									list="#{0:'制作中',1:'申请等待中',2:'待修改',3:'审核等待中',4:'审核不通过',5:'已开通',6:'修改等待中',7:'修改中',8:'删除等待中'}"
									name="examRoom.valid" value="examRoom.valid" /></td>
    <td rowspan="2" bgcolor="#F8FCFE">&nbsp;<input onClick="initPN();" type="button" class="textbg4" value="搜索" /></td>
  </tr>
  <tr>
    <td bgcolor="#F8FCFE">类型：
      <s:select theme="simple"
									list="#{-1:'考核考场',0:'单纯的课程考场',1:'培训班考场',-2:'全部'}"
									name="examRoom.classid" value="examRoom.classid" /></td>
    <td bgcolor="#F8FCFE">开考时间：&nbsp;从
				<input size="16" type="text" onclick=setday(this)
									name="examRoom.begintime"
									value="<s:date name="examRoom.begintime" format="yyyy-MM-dd HH:mm"/>">
								&nbsp;到&nbsp;
								<input size="16" type="text" onclick=setday(this)
									name="examRoom.endtime"
									value="<s:date name="examRoom.endtime" format="yyyy-MM-dd HH:mm"/>"></td>
    </tr>
</table>

						  
					  </form>
						<s:if test="examRooms.size==0">
							<br>
							
						</s:if>
						<s:else>
							<table width="100%" align="center" cellspacing="1"
								cellpadding="1">
								<tr>
									<th width="270" height="30" align="center">
										考场信息
									</th>
									<!--<th height="30" align="center" >
							考场地点
						</th>
						<th height="30" align="center" >
							类别库
						</th>
						<th height="30" align="center" >
							通过百分比
						</th>-->
									<th width="50" height="30" align="center">
										类型
									</th>
									<th width="110" height="30" align="center">
										开始时间
									</th>
									<th width="110" height="30" align="center">
										结束时间
									</th>
									<th width="70" height="30" align="center">
										考场类型
									</th>
									<th width="90" height="30" align="center">
										考场状态
									</th>
									<th width="120" height="30" align="center">
										人数
									</th>
									<th width="140" height="30" align="center">&nbsp;
										
									</th>
									<!--<th height="30" align="center" >
										&nbsp;
									</th>
								-->
								</tr>
								<tbody onMouseOut="changeback()" onMouseOver="changeto()">
									<s:iterator value="examRooms">
										<tr>
											<!--<td height="30" align="center" >
								<input type="checkbox" name="delete_item[]" value="5">
							</td>
							-->
											<td width="270" height="30" align="left">
												<div
												style="word-wrap: break-word; word-break: break-all; width: 100%;">
												<strong style="font-size:15px;color: blue;"><s:property
														value="title" /> </strong>
												<br />
												<strong>类别:</strong>
												<s:property value="eroomLib.name" />
												<br />
												<strong>组织单位:</strong>
												<s:property value="depName" />
												<br />
												<strong>组织工钟:</strong>
												<s:property value="jingzhong" />
												<br />
												<strong>创建者:</strong>
												<s:property value="creater.realname" /><br/>
												<s:if test="examRoom.classid!=-1">
													<strong> 所属课程: </strong><s:property value="course.name" />
												</s:if>
											</div></td><td><s:if test="isApplication == 1">
												<SPAN style="color: red">【申请】</SPAN>
											</s:if>
											<s:elseif test="isApplication == 2">
												<SPAN style="color: blue;">【全工】</SPAN>
											</s:elseif>
											<s:else>
												<SPAN style="color: gray">【分配】</SPAN>
											</s:else>
											</td>
											<!--<td height="30" align="center" >
								<s:property value="location" />
							</td>
							<td height="30" align="center" >
								<s:property value="eroomLib.name" />
							</td>
							<td height="30" align="center" >
								<s:property value="passgrade" />
							</td>-->
											<td align="center">
												<s:date name="begintime" format="yyyy-MM-dd HH:mm:ss" />
											</td>
											<td align="center">
												<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss" />
											</td>
											<td align="center">
												<s:property value="typeName" />
											</td>
											<td align="center">
												<s:property value="validName" />
											</td>
											<td align="center">
												参加：<s:property value="usersize" />
												<s:if test="isApplication == 1"><br/>
													<span style="color: red">计划：<s:property
															value="planNumber" /></span>
												</s:if>
											</td>
											<td height="30" align="left">
												<a
													href="examroom_sh_view.action?examRoom.id=<s:property value="id"/>&Return=alter"
													class="textbg4">查 看</a>
												<s:if test="valid == 6">
													<a style="cursor: pointer;"
														onClick="sh(<s:property value="id"/>, 0);" class="textbg4">允许</a>
													<a style="cursor: pointer;"
														onClick="sh(<s:property value="id"/>, <s:property value="avalid"/>);"
														class="textbg6">不允许</a>
													<a
														href="CRE_notelistInit.action?examRoom.id=<s:property value="id"/>&Return=examroom_alter_list"
														 onclick="showCre(<s:property value="id"/>);return false;" class="textbg4">备 注</a>
												</s:if>
												<!-- <a
									href="examroom_sh_alter_Init.action?examRoom.id=<s:property value="id"/>" class="textbg">查看并批阅</a>  -->
										</tr>
									</s:iterator>
								</tbody>
							</table>
							<form action="examroom_sh_p.action" name="examroom_sh_p"
								method="post">
								<s:hidden name="examRoom.id" id="examRoom.id"></s:hidden>
								<s:hidden name="examRoom.valid" id="examRoomValid"></s:hidden>
								<s:hidden name="Return" id="Return" value="examroom_alter_list"></s:hidden>
							</form>
						</s:else>
						<script>  
								function sh(id,valid){
								    document.getElementById("examRoom.id").value=id;
								    document.getElementById("examRoomValid").value=valid; 
								 	if(window.confirm("确定此操作？")){
										if(FillInNoteksInit(id)){ 
							 			document.forms.examroom_sh_p.submit(); 
							 			}
								 	}
								}
								function FillInNoteksInit(id){ 
								     width=600;
									 height=500;  
								  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
									 var rv =  window.showModalDialog("CRE_addNotes.action?examRoom.id="+id+"&Return=elclass_primash_list&x="+Math.random(),null,sFeature); 
									 if(rv == null){
										alert("未填写备注信息，您不能进行提交！");	 
										return false;				
									 }else{
										if(rv == true){  
											return true;
										}else if(rv == false){
											return false;
										}  
									 }
								 } 
								function page(i){
									document.getElementById("pageNow").value=i;
									examFh.submit();
									//document.location.href="examroom_alter_list.action?pS=<s:property value="pS"/>&pN="+i
								}
								function initPN(){
									document.getElementById("pageNow").value=0;
									examFh.submit();
								}
								 function showCre(roomid){
								  	 width=750;
									 height=500;  
								  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
									 var rv =  window.showModalDialog("CRE_notelistInit.action?examRoom.id="+roomid+"&course.id=0&elclass.id=0&Return=examroom_prima_shlist&x="+Math.random(),null,sFeature); 
								}	
							</script>
						<wysLib:page></wysLib:page>
					</td>
				</tr>
			</table>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
