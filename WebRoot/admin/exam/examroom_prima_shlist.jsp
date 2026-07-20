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
.textbg4{margin-top:2px;}
.textbg6{margin-top:2px;}
</style>
		<script type="text/javascript" src="js/jquery.js"></script>
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
		<script type="text/javascript" src="js/newversion/jquery.toolsbar.js"></script>
		<script type="text/javascript">
			function Obj(pp_,valid_,uvalid_){ 
			this.pp=pp_; 
			this.valid=valid_;
			this.uvalid = uvalid_;
		} 
		//按钮
		var ToolsBarObj = null;
		var pp = [];
		var valid = [];
		var uvalid = [];
		$(function(){
			ToolsBarObj = $("#Div_ToolsBar");//存放按钮的div
			ToolsBarObj.ToolsBar_Add("toolbar_view","查看","images/newversion/un_view.gif","viewDetail()");
			ToolsBarObj.ToolsBar_Add("toolbar_commitapplication","提交申请","images/newversion/un_view.gif","commitapplicationDetail()");
			ToolsBarObj.ToolsBar_Add("toolbar_back","返回","images/newversion/un_view.gif","backDetail()");
			ToolsBarObj.ToolsBar_Add("toolbar_edit","编辑","images/newversion/un_view.gif","editDetail()");
			ToolsBarObj.ToolsBar_Add("toolbar_allocation","分配","images/newversion/un_view.gif","allocationDetail()");
			ToolsBarObj.ToolsBar_Add("toolbar_backupdate","退回修改","images/newversion/un_view.gif","backupdateDetail()");
			ToolsBarObj.ToolsBar_Add("toolbar_mark","备注","images/newversion/un_view.gif","markDetail()");
		});
		
		function clickcheckbox(){
			var obj = getCheckedCheckboxs(pp,valid,uvalid);
			pp = obj.pp;
			valid = obj.valid;
			uvalid = obj.uvalid;
			var value = 0;
			var va = 0;
			var uva = 0;
			if(pp.length>1){
				ToolsBarObj.ToolsBar_Disabled("toolbar_view");
				ToolsBarObj.ToolsBar_Disabled("toolbar_commitapplication");
				ToolsBarObj.ToolsBar_Disabled("toolbar_back");
				ToolsBarObj.ToolsBar_Disabled("toolbar_edit");
				ToolsBarObj.ToolsBar_Disabled("toolbar_allocation");
				ToolsBarObj.ToolsBar_Disabled("toolbar_backupdate");
				ToolsBarObj.ToolsBar_Disabled("toolbar_mark");
			}else if(pp.length == 1){
				va= valid[0];
				uva = uvalid[0];
				if(va == 1 || va == 4){
					ToolsBarObj.ToolsBar_Enabled("toolbar_commitapplication");
					ToolsBarObj.ToolsBar_Enabled("toolbar_edit");
					ToolsBarObj.ToolsBar_Enabled("toolbar_allocation");
					if(va == 1){
						ToolsBarObj.ToolsBar_Enabled("toolbar_view");
						ToolsBarObj.ToolsBar_Enabled("toolbar_back");
					}else{
						ToolsBarObj.ToolsBar_Disabled("toolbar_view");
						ToolsBarObj.ToolsBar_Disabled("toolbar_back");
					}
					if(va == 4){
						ToolsBarObj.ToolsBar_Enabled("toolbar_backupdate");
					}else{
						ToolsBarObj.ToolsBar_Disabled("toolbar_backupdate");
					}
				}
				ToolsBarObj.ToolsBar_Enabled("toolbar_mark");
			}else {
				ToolsBarObj.ToolsBar_Disabled("toolbar_view");
				ToolsBarObj.ToolsBar_Disabled("toolbar_commitapplication");
				ToolsBarObj.ToolsBar_Disabled("toolbar_back");
				ToolsBarObj.ToolsBar_Disabled("toolbar_edit");
				ToolsBarObj.ToolsBar_Disabled("toolbar_allocation");
				ToolsBarObj.ToolsBar_Disabled("toolbar_backupdate");
				ToolsBarObj.ToolsBar_Disabled("toolbar_mark");
			}
		}
		
		function viewDetail(){
			//examroom_sh_view.action?examRoom.id=<s:property value="id"/>&Return=ash
			var obj = getCheckedCheckboxs(pp,valid,uvalid);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp[0].toString();
			window.location.href = "examroom_sh_view.action?examRoom.id="+value+"&Return=ash";
			/**
			var value = pp[0].toString();
			width=screen.availWidth * 0.8;
			height=screen.availHeight * 0.8;
			window.open ("course_preview.action?course.id="+value, '课程预览', 'height='+height+', width='+width+', toolbar=no, menubar=yes, scrollbars=yes, resizable=yes,location=no, status=no') ;
			*/
		}
		function commitapplicationDetail(){
			//sh(<s:property value="id"/>, 3);
			var obj = getCheckedCheckboxs(pp,valid,uvalid);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp[0].toString();
			sh(value, 3);
		}
		function backDetail(){
			//sh(<s:property value="id"/>, 2);
			var obj = getCheckedCheckboxs(pp,valid,uvalid);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp[0].toString();
			sh(value, 2);
		}
		function editDetail(){
			//erwithout_alterInit.action?examRoom.id=<s:property value="id"/>
			var obj = getCheckedCheckboxs(pp,valid,uvalid);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp[0].toString();
			window.location.href = "erwithout_alterInit.action?examRoom.id="+value;
		}
		function allocationDetail(){
			//examroom_assignwcInit.action?examRoom.id=<s:property value="id"/>&course.id=-1
			var obj = getCheckedCheckboxs(pp,valid,uvalid);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp[0].toString();
			window.location.href = "examroom_assignwcInit.action?examRoom.id="+value+"&course.id=-1";
		}
		function backupdateDetail(){
			//sh(<s:property value="id"/>, 0);
			var obj = getCheckedCheckboxs(pp,valid,uvalid);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp[0].toString();
			sh(value, 0);
		}
		function markDetail(){
			//CRE_notelistInit.action?examRoom.id=<s:property value="id"/>&Return=examroom_prima_shlist
			var obj = getCheckedCheckboxs(pp,valid,uvalid);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp[0].toString();
			//window.location.href = "CRE_notelistInit.action?examRoom.id="+value+"&Return=examroom_prima_shlist";
			showCre(value);
			return false;
		}
		
		
		//获取选中的checkbox
		function getCheckedCheckboxs(pp,valid,uvalid){
			var checkboxs = document.getElementsByName("examid");
			if(checkboxs.length>0){
				if(pp.length>0)  pp=[];
				for(var i=0;i<checkboxs.length;i++){
					if(checkboxs[i].checked){
						pp.push(checkboxs[i].value);
						valid.push(document.getElementById("valid_"+i).value);
						uvalid.push(document.getElementById("uvalid_"+i).value);
					}
				}
			}
			var obj = new Obj(pp,valid,uvalid);
			return obj;
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
				<span style="font-weight: bold;">考场初审</span>
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
					<td valign="top" width="200px" id="tree_list_td" style="display:none">
						<wysLib:eroomLibTree
							href="examroom_prima_shlist.action?sublibs=1&str=eroomlib&eroomLib.id="
							rootAble="true"></wysLib:eroomLibTree>
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55_1.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" id="showimg" />
					</td>
					<td valign="top">

						<form
							action="examroom_prima_shlist.action?eroomLib.id=<s:property value="eroomLib.id"/>"
							method="post" name="examFh">
							<s:hidden name="pN" id="pageNow" />
							<s:hidden name="pS" />
                            <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#D1E4F5">
  <tr>
    <td width="100" rowspan="2" bgcolor="#F8FCFE"><div style="text-align: left;" id="showtree">
									<a href="javascript:showtree(true);" class="textbg5">显示考场树</a>
					  </div></td>
    <td width="170" bgcolor="#F8FCFE">包含下级节点:
		    <input type="checkbox" name="sublibs"
									<s:if test="sublibs==1">checked="checked"</s:if> value="1"></td>
    <td width="380" bgcolor="#F8FCFE">考场标题：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;
			  <input size="16" type="text" name="examRoom.title"
									value="<s:property value="examRoom.title"/>">
								状态：&nbsp;
								<s:select theme="simple" headerKey="-1" headerValue="全部"
									list="#{0:'制作中',1:'申请等待中',2:'待修改',3:'审核等待中',4:'审核不通过',5:'已开通',6:'修改等待中',7:'修改中',8:'删除等待中'}"
									name="examRoom.valid" value="examRoom.valid" /></td>
    <td rowspan="2" bgcolor="#F8FCFE">&nbsp;<input onClick="initPN();" type="button" class="textbg4" value="搜索" /></td>
  </tr>
  <tr>
    <td bgcolor="#F8FCFE">类型：<s:select theme="simple"
									list="#{-2:'全部',-1:'考核考场',0:'章节考场',1:'单纯课程考场',2:'培训班考场'}"
									name="examRoom.classid" value="examRoom.classid" />
    <td bgcolor="#F8FCFE">时间段范围：&nbsp;从
      <input size="16" type="text" onClick=setday(this)
									name="examRoom.begintime"
									value="<s:date name="examroom.begintime" format="yyyy-MM-dd HH:mm"/>
      ">
								&nbsp;到&nbsp;
                                <input size="16" type="text" onClick=setday(this)
									name="examRoom.endtime"
									value="<s:date name="examroom.endtime" format="yyyy-MM-dd HH:mm"/>
            "> </td>
    </tr>
</table>
						</form>
<table width="100%" align="center" cellpadding="1" cellspacing="1">
							<tr>
								<td colspan=20><div id="Div_ToolsBar"></div></td>
							</tr>
							<tr>
								<th width="20" align="center">
							  </th>
								<th width="300" align="center">
									考场信息
								</th>
								<th width="60" align="center">
									类型
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
								 
								<th width="90" align="center">
									开始时间
								</th>
								<th width="90" align="center">
									结束时间
								</th>
								<!--<th height="30" align="center" >
										选拔类型
									</th>-->
								<th width="80" align="center">
									审核状态
								</th>
								<th width="80" align="center">
									复核状态
								</th>
								<th width="80" align="center">
									人数
								</th>
								<!-- 
								<th width="150" align="center">
									&nbsp;
								</th>
								 -->
							</tr>
							<tbody onMouseOut="changeback()" onMouseOver="changeto()">
								<s:iterator value="examRooms" status="status">
									<tr>
										<td width="20" align="center">
													<input type="checkbox" value="<s:property value="id"/>"
														name="examid" onclick='clickcheckbox();'>
										<td style="padding: 3px 0px 3px 2px;" valign="top" align="left">
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
											</div>
										</td>
										<td align="center"><s:if test="isApplication == 1">
												<SPAN style="color: red">【申请】</SPAN>
											</s:if>
											<s:elseif test="isApplication == 2">
												<SPAN style="color: blue;">【全工】</SPAN>
											</s:elseif>
											<s:else>
												<SPAN style="color: gray">【分配】</SPAN>
											</s:else>
										</td>
										  
										 
										<td align="center">
											<s:date name="begintime" format="yyyy-MM-dd HH:mm:ss" />
										</td>
										<td align="center">
											<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss" />
										</td>
										<!--<td height="30" align="center" >
											<s:property value="typeName" />
										</td>-->
										<td align="center">
											<font color="<s:if test="valid==1">green</s:if>"> <s:property
													value="validName" />
											</font>
										</td>
										<td align="center">
											<font color="<s:if test="uvalid==1">red</s:if>"> <s:property
													value="uvalidName" />
											</font>
										</td>
										<td align="center">
											参加：<s:property value="usersize" />
											<s:if test="isApplication == 1">
												<br/>
												<span style="color: red">计划：<s:property
														value="planNumber" /></span>
											</s:if>
										</td>
										<input type="hidden" id="valid_<s:property value='#status.index'/>" value="<s:property value='valid' />"/>
										<input type="hidden" id="uvalid_<s:property value='#status.index'/>" value="<s:property value='uvalid' />"/>
										<!-- 
										<td align="left">
											<s:if test="valid == 1">
												<a
													href="examroom_sh_view.action?examRoom.id=<s:property value="id"/>&Return=ash"
													class="textbg4">查 看</a>
												<a style="cursor: pointer;"
													onClick="sh(<s:property value="id"/>, 3);" class="textbg6">提交申请</a><br/>
												<a style="cursor: pointer;"
													onClick="sh(<s:property value="id"/>, 2);" class="textbg4">返回</a>
												<a
													href="erwithout_alterInit.action?examRoom.id=<s:property value="id"/>"
													class="textbg4">编 辑</a>
												<a
													href="examroom_assignwcInit.action?examRoom.id=<s:property value="id"/>&course.id=-1"
													class="textbg4">分 配</a>
											</s:if>
											<s:elseif test="valid == 4">
												<a
													href="examroom_assignwcInit.action?examRoom.id=<s:property value="id"/>&course.id=-1&Return=ash"
													class="textbg4">分 配</a>
												<a
													href="erwithout_alterInit.action?examRoom.id=<s:property value="id"/>&Return=ash"
													class="textbg4">编 辑</a><br/>
												<a style="cursor: pointer;"
													onClick="sh(<s:property value="id"/>, 0);" class="textbg6">退回修改</a><br/>
												<a style="cursor: pointer;"
													onClick="sh(<s:property value="id"/>, 3);" class="textbg6">提交申请</a>
											</s:elseif>
											<a
												href="CRE_notelistInit.action?examRoom.id=<s:property value="id"/>&Return=examroom_prima_shlist"
												onclick="showCre(<s:property value="id"/>);return false;" class="textbg4">备 注</a>
										</td>
										 -->
									</tr>
								</s:iterator>
							</tbody>
					  </table>
						<form action="examroom_sh_p.action" name="examroom_sh_p"
							method="post">
							<s:hidden name="examRoom.id" id="examRoom.id"></s:hidden>
							<s:hidden name="examRoom.valid" id="examRoomValid"></s:hidden>
							<s:hidden name="Return" id="Return" value="examroom_prima_shlist"></s:hidden>
						</form>
						<wysLib:page></wysLib:page>
						<script>   
								function sh(id,valid){
								    document.getElementById("examRoom.id").value=id;
								    document.getElementById("examRoomValid").value=valid; 
								 	if(valid==2 && window.confirm("确定返回？")){
										if(FillInNoteksInit(id)){ 
								 			document.forms.examroom_sh_p.submit();
								 		} 
								 	}
								 	/*
								 	if(valid==3 && window.confirm("确定通过？")){
										if(FillInNoteksInit(id)){ 
								 			document.forms.examroom_sh_p.submit();
								 		} 
								 	}
								 	*/
								 	if(valid==3){
										if(FillInNoteksInit(id)){ 
								 			document.forms.examroom_sh_p.submit();
								 		}
								 	}
								 	if(valid==0 && window.confirm("确定退给创建者修改？")){
										if(FillInNoteksInit(id)){ 
								 			document.forms.examroom_sh_p.submit();
								 		} 
								 	} 
								}
								function showCre(roomid){
								  	 width=750;
									 height=500;  
								  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
									 var rv =  window.showModalDialog("CRE_notelistInit.action?examRoom.id="+roomid+"&course.id=0&elclass.id=0&Return=examroom_prima_shlist&x="+Math.random(),null,sFeature); 
								}
								/*
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
								 */
								 function FillInNoteksInit(id){
									 var rn=2;
									 if(document.getElementById("examRoomValid").value==3){
									     width=1000;
										 height=560;
										 //此地加一个拦截，用于查看时间是否存在重叠
										 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
										 rn =  window.showModalDialog("eroom_timeover_list.action?examRoom.id="+id+"&PageStatus=0&x="+Math.random(),null,sFeature);
										 //return false;
									 }
									 if(rn==1 || rn==2){//点击了通过或者不通过
									 	if(rn==2){
									 		document.getElementById("examRoomValid").value=2;
									 	}
										 width=600;
										 height=500;
									  	 sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
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
								  }
								function page(i){
									//document.location="examroom_shlist.action?eroomLib.id=<s:property value="eroomLib.id"/>&pS=${pS}&pN="+i
									document.getElementById("pageNow").value=i;
									examFh.submit();
								}
								function initPN(){
									document.getElementById("pageNow").value=0;
									examFh.submit();
								}
							</script>
					</td>
				</tr>
			</table>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
