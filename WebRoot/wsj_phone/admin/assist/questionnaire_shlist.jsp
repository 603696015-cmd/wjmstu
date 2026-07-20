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
			ToolsBarObj.ToolsBar_Add("toolbar_commit","核准","images/newversion/un_view.gif","commitDetail()");
			ToolsBarObj.ToolsBar_Add("toolbar_backapplication","返回申请","images/newversion/un_view.gif","backapplicationDetail()");
			ToolsBarObj.ToolsBar_Add("toolbar_edit","编辑","images/newversion/un_view.gif","editDetail()");
			ToolsBarObj.ToolsBar_Add("toolbar_allocation","分配","images/newversion/un_view.gif","allocationDetail()");
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
				ToolsBarObj.ToolsBar_Disabled("toolbar_commit");
				ToolsBarObj.ToolsBar_Disabled("toolbar_backapplication");
				ToolsBarObj.ToolsBar_Disabled("toolbar_edit");
				ToolsBarObj.ToolsBar_Disabled("toolbar_allocation");
				ToolsBarObj.ToolsBar_Disabled("toolbar_mark");
			}else if(pp.length == 1){
				va= valid[0];
				uva = uvalid[0];
				if(va == 1 || va == 3){
					ToolsBarObj.ToolsBar_Enabled("toolbar_commit");
					if(va == 1){
						ToolsBarObj.ToolsBar_Enabled("toolbar_backapplication");
					}else{
						ToolsBarObj.ToolsBar_Disabled("toolbar_backapplication");
					}
				}
				ToolsBarObj.ToolsBar_Enabled("toolbar_edit");
				ToolsBarObj.ToolsBar_Enabled("toolbar_allocation");
				ToolsBarObj.ToolsBar_Enabled("toolbar_mark");
			}else {
				ToolsBarObj.ToolsBar_Disabled("toolbar_commit");
				ToolsBarObj.ToolsBar_Disabled("toolbar_backapplication");
				ToolsBarObj.ToolsBar_Disabled("toolbar_edit");
				ToolsBarObj.ToolsBar_Disabled("toolbar_allocation");
				ToolsBarObj.ToolsBar_Disabled("toolbar_mark");
			}
		}
		
		function commitDetail(){
			//sh(<s:property value="id"/>, 5);
			var obj = getCheckedCheckboxs(pp,valid,uvalid);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp[0].toString();
			sh(value, 5);
		}
		function backapplicationDetail(){
			//sh(<s:property value="id"/>, 4);
			var obj = getCheckedCheckboxs(pp,valid,uvalid);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp[0].toString();
			sh(value, 4);
		}
		function editDetail(){
			//erwithout_alterInit.action?examRoom.id=<s:property value="id"/>&Return=sh
			var obj = getCheckedCheckboxs(pp,valid,uvalid);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp[0].toString();
			window.location.href = "questionnaire_alterInit.action?examRoom.id="+value+"&&Return=sh";
		}
		function allocationDetail(){
			//examroom_assignwcInit.action?examRoom.id=<s:property value="id"/>&course.id=-1&Return=sh
			var obj = getCheckedCheckboxs(pp,valid,uvalid);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp[0].toString();
			window.location.href = "examroom_assignwcInit.action?examRoom.id="+value+"&course.id=-1&Return=sh";
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
					<td valign="top" width="200px" id="tree_list_td" style="display:none">
						<wysLib:eroomLibTree
							href="questionnaire_shlist.action?sublibs=1&str=eroomlib&eroomLib.id="
							rootAble="true"></wysLib:eroomLibTree>
					</td>
					<td valign="top">
						<form
							action="questionnaire_shlist.action?eroomLib.id=<s:property value="eroomLib.id"/>"
							method="post" name="examFh">
							<s:hidden name="pN" id="pageNow" />
							<s:hidden name="pS" />
<table width="100%" border="0" cellpadding="0" cellspacing="1">
  <tr>
    <td width="380" bgcolor="#F4F4F4">问卷标题：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;
								<input size="16" type="text" name="examRoom.title"
									value="<s:property value="examRoom.title"/>">
								状态：&nbsp;
								<s:select theme="simple" headerKey="-1" headerValue="全部"
									list="#{0:'制作中',1:'申请等待中',2:'待修改',3:'审核等待中',4:'审核不通过',5:'已开通',6:'修改等待中',7:'修改中',8:'删除等待中'}"
									name="examRoom.valid" value="examRoom.valid" /></td>
    <td rowspan="2" bgcolor="#F4F4F4">&nbsp;<input onClick="initPN();" type="button" class="textbg4" value="搜索" /></td>
  </tr>
  <tr>
    <td style="display:none" bgcolor="#F4F4F4">类型：<s:select theme="simple"
									list="#{-1:'考核考场',0:'单纯的课程考场',1:'培训班考场',-2:'全部'}"
									name="examRoom.classid" value="examRoom.classid" /></td>
    <td bgcolor="#F4F4F4">时间段范围：&nbsp;从
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
						<table align="center" width="100%" cellspacing="1" cellpadding="1">
							<tr>
								<td colspan=20><div id="Div_ToolsBar"></div></td>
							</tr>
							<tr>
								<th width="20" align="center">
										</th>
								<th width="300" align="center">
									问卷信息
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
								<th width="80" height="30" align="center">
									审核状态
								</th>
								<th width="80" height="30" align="center">
									复核状态
								</th>
								<th width="80" height="30" align="center">
									 人数
								</th>
								<!-- 
								<th width="150" height="30" align="center">
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
										<td align="center">
											<s:if test="isApplication == 1">
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
											<s:if test="isApplication == 1"><br/>
												<span style="color: red">计划：<s:property
														value="planNumber" /></span>
											</s:if>
										</td>
										<input type="hidden" id="valid_<s:property value='#status.index'/>" value="<s:property value='valid' />"/>
										<input type="hidden" id="uvalid_<s:property value='#status.index'/>" value="<s:property value='uvalid' />"/>
										
										<!-- 
										<td align="left">
											<s:if test="valid == 3">
												<a style="cursor: pointer;"
													onClick="sh(<s:property value="id"/>, 5);" class="textbg4">核准</a>
												<a style="cursor: pointer;"
													onClick="sh(<s:property value="id"/>, 4);" class="textbg6">返回申请</a><br/>
											</s:if>
											<s:if test="valid == 1">
												<a style="cursor: pointer;"
													onClick="sh(<s:property value="id"/>, 5);" class="textbg4"><span
													style="color: red">核 准</span>
												</a><br/>
											</s:if>
											<s:if test="valid == 5">
												<a onclick="return isUpData();"
													href="erwithout_alterInit.action?examRoom.id=<s:property value="id"/>&Return=sh"
													class="textbg4">编 辑</a>
											</s:if>
											<s:else>
												<a
													href="erwithout_alterInit.action?examRoom.id=<s:property value="id"/>&Return=sh"
													class="textbg4">编 辑</a>
											</s:else>
											<a
												href="examroom_assignwcInit.action?examRoom.id=<s:property value="id"/>&course.id=-1&Return=sh"
												class="textbg4">分 配</a>
											<a
												href="CRE_notelistInit.action?examRoom.id=<s:property value="id"/>&Return=examroom_shlist"
												onclick="showCre(<s:property value="id"/>);return false;" class="textbg4">备 注</a>
										</td>
										 -->
									</tr>
								</s:iterator>
							</tbody>
						</table>
						<form action="questionnaire_sh_p.action" name="examroom_sh_p"
							method="post">
							<s:hidden name="examRoom.id" id="examRoom.id"></s:hidden>
							<s:hidden name="examRoom.valid" id="examRoomValid"></s:hidden>
							<s:hidden name="Return" id="Return" value="examroom_shlist"></s:hidden>
						</form>
						<wysLib:page></wysLib:page>
						<script>   
								function sh(id,valid){
								    document.getElementById("examRoom.id").value=id;
								    document.getElementById("examRoomValid").value=valid; 
								 	if(valid==4 && window.confirm("确定返回申请？")){
										if(FillInNoteksInit(id)){ 
								 		document.forms.examroom_sh_p.submit();
								 		}
								 	}
								 	/*
								 	if(valid==5 && window.confirm("确定通过？")){
										if(FillInNoteksInit(id)){ 
								 		document.forms.examroom_sh_p.submit();
								 		}
								 	}
								 	*/
								 	if(valid==5){
										if(FillInNoteksInit(id)){ 
								 		document.forms.examroom_sh_p.submit();
								 		}
								 	}
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
									 if(document.getElementById("examRoomValid").value==5){
									     width=1000;
										 height=560;
										 //此地加一个拦截，用于查看时间是否存在重叠
										 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
										 rn =  window.showModalDialog("questionnaire_timeover_list.action?examRoom.id="+id+"&PageStatus=1&x="+Math.random(),null,sFeature);
										 //return false;
									 }
									 if(rn==1 || rn==2){//点击了通过或者不通过
									 	if(rn==2){
									 		document.getElementById("examRoomValid").value=4;
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
									document.getElementById("pageNow").value=i;
									examFh.submit();
									//document.location="examroom_shlist.action?eroomLib.id=<s:property value="eroomLib.id"/>&pS=${pS}&pN="+i
								}
								function initPN(){
									document.getElementById("pageNow").value=0;
									examFh.submit();
								}
								function isUpData(){
								  	if(window.confirm("此操作慎重，是否继续?")){
								  		return true;
								  	}
								  	return false;
								}
								function showCre(roomid){
								  	 width=750;
									 height=500;  
								  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
									 var rv =  window.showModalDialog("CRE_notelistInit.action?examRoom.id="+roomid+"&course.id=0&elclass.id=0&Return=examroom_prima_shlist&x="+Math.random(),null,sFeature); 
								}
							</script>
					</td>
				</tr>
			</table>
			<s:if test="Return=='st_index'">
			<a href="study_index.action" class="textbg4">返回</a>
			</s:if>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
