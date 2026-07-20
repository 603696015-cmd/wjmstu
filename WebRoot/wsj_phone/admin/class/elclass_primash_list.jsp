<%@ page language="java" pageEncoding="UTF-8"   %>
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
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>培训班审核</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" /> 
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
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(false);" class="textbg5">隐藏类别</a>';
					}
					else{
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(true);" class="textbg5">显示类别</a>';
					}
				}
		</script>
		<script type="text/javascript" src="js/newversion/jquery.toolsbar.js"></script>
		<script type="text/javascript">
			function Obj(pp_,status_,isUvalid_){ 
			this.pp=pp_; 
			this.status=status_;
			this.isUvalid = isUvalid_;
		} 
		//按钮
		var ToolsBarObj = null;
		var pp = [];
		var status = [];
		var isUvalid = [];
		$(function(){
			ToolsBarObj = $("#Div_ToolsBar");//存放按钮的div
			ToolsBarObj.ToolsBar_Add("toolbar_back","返回","images/newversion/un_view.gif","backDetail()");
			ToolsBarObj.ToolsBar_Add("toolbar_allocation","分配","images/newversion/un_view.gif","allocationDetail()");
			ToolsBarObj.ToolsBar_Add("toolbar_commit","申请终审","images/newversion/un_view.gif","commitDetail()");
			ToolsBarObj.ToolsBar_Add("toolbar_edit","编辑","images/newversion/un_view.gif","editDetail()");
			ToolsBarObj.ToolsBar_Add("toolbar_students","学员","images/newversion/un_view.gif","studentsDetail()");
			ToolsBarObj.ToolsBar_Add("toolbar_view","详情","images/newversion/un_view.gif","viewDetail()");
			ToolsBarObj.ToolsBar_Add("toolbar_mark","备注","images/newversion/un_view.gif","markDetail()");
		});
		function clickcheckbox(){
			var obj = getCheckedCheckboxs(pp,status,isUvalid);
			pp = obj.pp;
			status = obj.status;
			isUvalid = obj.isUvalid;
			var value = 0;
			var st = 0;
			var isUva = 'false';
			if(pp.length>1){
				ToolsBarObj.ToolsBar_Disabled("toolbar_back");
				ToolsBarObj.ToolsBar_Disabled("toolbar_allocation");
				ToolsBarObj.ToolsBar_Disabled("toolbar_commit");
				ToolsBarObj.ToolsBar_Disabled("toolbar_edit");
				ToolsBarObj.ToolsBar_Disabled("toolbar_students");
				ToolsBarObj.ToolsBar_Disabled("toolbar_view");
				ToolsBarObj.ToolsBar_Disabled("toolbar_mark");
			}else if(pp.length == 1){
				st= status[0];
				if(st == 4 || st ==1){ 
					ToolsBarObj.ToolsBar_Enabled("toolbar_back");
					ToolsBarObj.ToolsBar_Enabled("toolbar_allocation");
					ToolsBarObj.ToolsBar_Enabled("toolbar_commit");
					ToolsBarObj.ToolsBar_Enabled("toolbar_edit");
					ToolsBarObj.ToolsBar_Disabled("toolbar_students");
				}else{
					ToolsBarObj.ToolsBar_Disabled("toolbar_back");
					ToolsBarObj.ToolsBar_Disabled("toolbar_allocation");
					ToolsBarObj.ToolsBar_Disabled("toolbar_commit");
					ToolsBarObj.ToolsBar_Disabled("toolbar_edit");
					ToolsBarObj.ToolsBar_Enabled("toolbar_students");
				}
				if(st != 4 ){
					ToolsBarObj.ToolsBar_Enabled("toolbar_view");
				}
				else{
					ToolsBarObj.ToolsBar_Disabled("toolbar_view");
				}
				ToolsBarObj.ToolsBar_Enabled("toolbar_mark");
			}else {
				ToolsBarObj.ToolsBar_Disabled("toolbar_back");
				ToolsBarObj.ToolsBar_Disabled("toolbar_allocation");
				ToolsBarObj.ToolsBar_Disabled("toolbar_commit");
				ToolsBarObj.ToolsBar_Disabled("toolbar_edit");
				ToolsBarObj.ToolsBar_Disabled("toolbar_students");
				ToolsBarObj.ToolsBar_Disabled("toolbar_view");
				ToolsBarObj.ToolsBar_Disabled("toolbar_mark");
			}
		}
		
		function backDetail(){
			//sh(<s:property value="id"/>, 0);
			var obj = getCheckedCheckboxs(pp,status,isUvalid);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp[0].toString();
			sh(value,0);
			//window.location.href = "elclass_copy.action?elclass.id="+value;
		}
		function allocationDetail(){
			//elclass_assign2userInit.action?sub_department=1&elclass.id=<s:property value="id" />
			var obj = getCheckedCheckboxs(pp,status,isUvalid);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp[0].toString();
			window.location.href = "elclass_assign2userInit.action?sub_department=1&elclass.id="+value;
		}
		function commitDetail(){
			//sh(<s:property value="id"/>, 3);
			var obj = getCheckedCheckboxs(pp,status,isUvalid);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp[0].toString();
			sh(value, 3);
		}
		function editDetail(){
			//elclass_addInit.action?elclassId=<s:property value="id"/>
			var obj = getCheckedCheckboxs(pp,status,isUvalid);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp[0].toString();
			window.location.href = "elclass_addInit.action?elclassId="+value;
		}
		
		function studentsDetail(){
			//elclass_check_students.action?sub_department=1&elclass.id=<s:property value="id"/>&Return=ash
			var obj = getCheckedCheckboxs(pp,status,isUvalid);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp[0].toString();
			window.location.href = "elclass_check_students.action?sub_department=1&elclass.id="+value+"&Return=ash";
		}
		function viewDetail(){
			//elclass_details_sh.action?elclassId=<s:property value="id" />&PageStatus=1&PageStatusint=4&Return=ash
			var obj = getCheckedCheckboxs(pp,status,isUvalid);
			pp = obj.pp;
			if(pp.length==0){
				alert("您还没选择,请先选择!");
				return ;
			}
			var value = pp[0].toString();
			window.location.href = "elclass_details_sh.action?elclassId="+value+"&PageStatus=1&PageStatusint=4&Return=ash";
		}
		function markDetail(){
			//showCre(<s:property value="id"/>);return false;
			var obj = getCheckedCheckboxs(pp,status,isUvalid);
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
		function getCheckedCheckboxs(pp,status,isUvalid){
			var checkboxs = document.getElementsByName("classid");
			if(checkboxs.length>0){
				if(pp.length>0)  pp=[];
				for(var i=0;i<checkboxs.length;i++){
					if(checkboxs[i].checked){
						pp.push(checkboxs[i].value);
						status.push(document.getElementById("status_"+i).value);
					}
				}
			}
			var obj = new Obj(pp,status,isUvalid);
			return obj;
		}
		</script>
		
		<!-- 
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")}
			.textbg4{margin-top:2px;}
			.textbg6{margin-top:2px;}
		</style>
		 -->
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color: "#ffffff" }
			.textbg4{margin-top:2px;}
			.textbg6{margin-top:2px;} 
		</style>
		
		
		<script>  
			function shEroom(id,valid,alterValid,deleteValid,fushenValid,Return){
			    document.getElementById("examRoom.id").value=id;
			    document.getElementById("examRoom.valid").value=valid; 
			    document.getElementById("deleteValid").value=deleteValid; 
			    document.getElementById("alterValid").value=alterValid; 
			    document.getElementById("fushenValid").value=fushenValid; 
			    document.getElementById("Return").value=Return; 
			 	if(window.confirm("确定此操作？")){
					if(FillInNoteksInit(id)){ 
			 			document.examroom_sh_p.submit();
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
		</script>
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="培训班列表" /></div>
			</li>
		</ul> 
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<form action="examroom_sh_p.action" name="examroom_sh_p" method="post"> 
			<s:hidden name="examRoom.id" id="examRoom.id"></s:hidden>
			<s:hidden name="examRoom.valid" id="examRoom.valid"></s:hidden>
			<s:hidden name="deleteValid" id="deleteValid"></s:hidden>
			<s:hidden name="alterValid" id="alterValid"></s:hidden>
			<s:hidden name="fushenValid" id="fushenValid"></s:hidden>
			<s:hidden name="Return" id="Return"></s:hidden><!-- value="course_return" -->
		</form>
		<table width="100%" cellpadding="1" cellspacing="1">
			<tr>
				<td width="165" valign="top" id="tree_list_td" style="display:none">
					<wysLib:clTypeTree  href="elclass_primash_list.action?sublibs=1&cltype.id="
						rootAble="true" />
			  </td>
				<td valign="middle" width="5px;" style="padding: 0px">
					<img src="images/leftmenu/main_55_1.gif" style="cursor: hand"
						onclick="changeTreeDisplay(this)" id="showimg" />
				</td>
				<td valign="top">
					
				  <s:form action="elclass_primash_list" name="myclist" theme="simple">
						<s:hidden name="pN" id="pageNow"></s:hidden>
						<s:hidden name="pS"></s:hidden>
					<s:hidden name="cltype.id"></s:hidden>
					<table width="100%" align="center" cellspacing="1" cellpadding="0" bgcolor="#D1E4F5">
						<tr>
							<td width="100" rowspan="2" bgcolor="#F8FCFE"><div style="text-align: left;" id="showtree">
								<a href="javascript:showtree(true);" class="textbg5">显示类别</a>
							</div></td>
							<td width="120" rowspan="2" bgcolor="#F8FCFE">包含下级节点: 
							 <input type="checkbox" name="sublibs" 
								<s:if test="sublibs==1">checked='checked'</s:if>
								 value="1">
						  </td>
							<td width="430" bgcolor="#F8FCFE">
								培训班名称 &nbsp;&nbsp;:&nbsp;
						  <input type="text" name="elClass.name"
									value="<s:property value="elClass.name"/>">
                                    状态:
								<s:select theme="simple"  headerValue="全部" headerKey="-1"
									list="#{0:'制作中',1:'申请等待中',2:'申请不通过',3:'审核等待中',4:'审核不通过',5:'已开通',6:'修改等待中',7:'修改中',8:'删除等待中'}"
									name="elClass.status" value="elClass.status" />
						  </td>
							<td rowspan="2" bgcolor="#F8FCFE">						    
						  &nbsp;<s:submit cssClass="textbg4" value="搜索"></s:submit>						    </td>
					  </tr>
						<tr>
						  <td bgcolor="#F8FCFE">时间段范围&nbsp;从
                            <input type="text" onClick=setday(this)
									name="elClass.begintime"
									value="<s:date name="elclass.begintime" format="yyyy-MM-dd HH:mm"/>
                            ">
								&nbsp;&nbsp;&nbsp;到&nbsp;&nbsp;
                                <input type="text" onClick=setday(this) name="elClass.endtime"
									value="<s:date name="elclass.endtime" format="yyyy-MM-dd HH:mm"/>
                          "> </td>
					  </tr> 
					</table>  
					</s:form>  
					<s:if test="elclasses.size==0">没有需要审核的培训班</s:if>
					<s:else>
						<table width="100%" height="100%" align="center" cellpadding="1"
							cellspacing="1" >
							<tr>
								<td colspan=20><div id="Div_ToolsBar"></div></td>
							</tr>
							<tr>
								<th width="20" align="center">
										</th>
								<th width="260" align="center" >
									培训班信息				</th>
								<th width="60" align="center" >类型</th>
								<%-- 
								<th width="100" align="center" >
									类别				</th>
								<th width="60" align="center" >
									创建者				</th>
								 --%>
								<th width="90" align="center" >
									创建时间				</th>
								<th width="90" align="center" >
									开始时间				</th>
								<th width="90" align="center" >
									结束时间				</th>
								<th width="70" align="center" >
									状态				</th>
								<th width="70" align="center" >
									人数				</th>
									<!-- 
								<th width="100" align="center" >&nbsp;</th> 
								 -->
							</tr>
							<tbody >
							<s:iterator value="elclasses" status="elclasses_st"  >
								<tr <s:if test="(#elclasses_st.index%2)==1">style="background:#f4f4f4"</s:if> >
								<td width="20" align="center">
													<input type="checkbox" value="<s:property value="id"/>"
														name="classid" onclick='clickcheckbox();'>
								<s:if test="examRooms.size==0">
									   <td  style="padding: 3px 0px 3px 2px;" valign="top"
											align="left">
										<div style="word-wrap: break-word; word-break: break-all; width: 100%;">
											<strong style="font-size:15px;color: blue;"><s:property value="name" /> </strong>
											<br />
											<strong>类别:</strong>
											<s:property value="cltype.name" />
											<br />
											<strong>组织单位:</strong>
											<s:property value="depName" />
											<br />
											<strong>组织警钟:</strong>
											<s:property value="jingzhong" />
											<br />
											<strong>创建者:</strong>
											<s:property value="creater.realname" /><br/>
											<s:if test="examRoom.classid!=-1">
												<strong> 所属课程: </strong><s:property value="course.name" />
											</s:if>
										</div>
										</td>
									   </s:if>
									   <s:else>
									    <td rowspan="2" style="padding: 3px 0px 3px 2px;" valign="top"
											align="left">
										<div style="word-wrap: break-word; word-break: break-all; width: 100%;">
											<strong style="font-size:15px;color: blue;"><s:property value="name" /> </strong>
											<br />
											<strong>类别:</strong>
											<s:property value="cltype.name" />
											<br />
											<strong>组织单位:</strong>
											<s:property value="depName" />
											<br />
											<strong>组织警钟:</strong>
											<s:property value="jingzhong" />
											<br />
											<strong>创建者:</strong>
											<s:property value="creater.realname" /><br/>
											<s:if test="examRoom.classid!=-1">
												<strong> 所属课程: </strong><s:property value="course.name" />
											</s:if>
										</div>
										</td>
									   </s:else>
									
									<td align="center" >
										<s:if test="isApplication == 1">
											<SPAN style="color:red">申请</SPAN>
										</s:if>
										<s:else>
											<SPAN style="color:gray">分配</SPAN>
										</s:else>
									</td>
									
										<td align="center" > 
								  <s:date format="yyyy-MM-dd hh:mm:ss" name="createtime"/>								  </td>
									<td align="center" >
										<s:date format="yyyy-MM-dd hh:mm:ss" name="starttime"/>
									</td>
								  <td align="center" >
								  <s:date format="yyyy-MM-dd hh:mm:ss" name="finishtime"/>								  </td>
									<td align="center" >
									<s:if test="status == 1 || status == 4">
										<span style="color:red"><s:property value="statusName" /></span>									</s:if><s:else>
										<s:if test="status == 5"> 
											<span style="color:green"><s:property value="statusName" /></span>										</s:if><s:else>
											 <s:property value="statusName" /> 
										</s:else>
									</s:else>								  </td>
									
									<td align="center">
										参加:
										<s:property value="classSize" />
										<s:if test="isApplication == 1">
											<br />
											<span style="color: red">计划:<s:property
													value="planNumber" /> </span>
										</s:if>
									</td>
									<input type="hidden" id="status_<s:property value='#elclasses_st.index'/>" value="<s:property value='status' />"/>
										<input type="hidden" id="isUvalid_<s:property value='#elclasses_st.index'/>" value="<s:property value='isUvalid' />"/>
									
								<!-- 
									<td align="left" >
										<s:if test="status == 4"> 
											<a style="cursor:pointer;"  onClick="sh(<s:property value="id"/>, 0);" class="textbg4">退回</a> 
											<a href="elclass_assign2userInit.action?sub_department=1&elclass.id=<s:property value="id" />" class="textbg4">分配</a>	 
											<a style="cursor:pointer;"  onClick="sh(<s:property value="id"/>, 3);"  class="textbg6">申请终审</a> 
											 <a href="elclass_addInit.action?elclassId=<s:property value="id"/>" class="textbg4">编辑</a> 
											</s:if>
										<s:else> 
											<s:if test="status == 1">  
												<a href="elclass_assign2userInit.action?sub_department=1&elclass.id=<s:property value="id" />" class="textbg4">分配</a>
												<a href="elclass_addInit.action?elclassId=<s:property value="id"/>" class="textbg4">编辑</a> 
												<a style="cursor:pointer;"  onClick="sh(<s:property value="id"/>, 3);" class="textbg6">申请终审</a>  
												<a style="cursor:pointer;"  onClick="sh(<s:property value="id"/>, 2);"  class="textbg6">返回</a> 
											</s:if>
											<s:else>
												<a href="elclass_check_students.action?sub_department=1&elclass.id=<s:property value="id"/>&Return=ash" class="textbg4">学员</a>											
											</s:else>
											<a href="elclass_details_sh.action?elclassId=<s:property value="id" />&PageStatus=1&PageStatusint=4&Return=ash" class="textbg4">详情</a>										
										</s:else>
											<a href="CRE_notelistInit.action?elclass.id=<s:property value="id"/>&Return=elclass_primash_list"  onclick="showCre(<s:property value="id"/>);return false;"  class="textbg4">备 注</a>
									</td>
									 -->
								</tr>
									<s:if test="examRooms.size!=0">
									<tr <s:if test="(#elclasses_st.index%2)==1">style="background:#f4f4f4"</s:if>>
										<td colspan="7" align="left" valign="top" style="padding: 0px;margin: 0px">
												<s:if test="examRooms.size!=0">
													<s:iterator value="examRooms">
													<span style='width:360px;text-align:left;'><s:property value="title"/>(<s:property value="validName"/>)</span> 
													<s:if test="valid==1||valid==4">
														<a style="cursor:pointer;"  onClick="shEroom(<s:property value="id"/>, 3,'falsa','falsa','falsa','elclass_primash_list');" class="textbg6">提交申请</a> 
														<a style="cursor:pointer;"  onClick="shEroom(<s:property value="id"/>, 2,'falsa','falsa','falsa','elclass_primash_list');" class="textbg6">返回</a>  
													</s:if>
													<s:if test="valid==0||valid==2||valid==4">
														<a href="erwithout_alterInit.action?examRoom.id=<s:property value="id"/>&Return=error" class="textbg4">编 辑</a>
													
														<a href="examroom_assignwcInit.action?examRoom.id=<s:property value="id"/>&course.id=<s:property value="course.id"/>&course.classid=<s:property value="course.classid"/>" class="textbg4">分配</a>
													</s:if>
														<a href="CRE_notelistInit.action?examRoom.id=<s:property value="id"/>&Return=examroom_prima_shlist" onclick="showCreks(<s:property value="id"/>);return false;" class="textbg4">备注</a>
													<br/>
												</s:iterator>
											</s:if>
										</td>
									</tr>
									</s:if>
								</s:iterator>
							</tbody>
					  </table>
					</s:else>
				</td>
			</tr>
	</table>
							<form action="elclass_sh.action" name="elclass_sh" method="post">
								<s:hidden name="elclass.id" id="elclass.id"></s:hidden> 
								<s:hidden name="status" id="status"></s:hidden>
								<s:hidden name="Return" id="Return" value="elclass_primash_list"></s:hidden>
							</form>  
		<wysLib:page></wysLib:page>  
					<script>    
						function sh(id,status){   
							    document.getElementById("elclass.id").value=id;
							    document.getElementById("status").value=status; 
							 	if(status==2 && window.confirm("确定返回？")){
							 		if(FillInNoteksInit(id)){
							 		document.forms.elclass_sh.submit(); 
									}  
							 	}
							 	/*
							 	if(status==3 && window.confirm("确定提交申请？")){
							 		if(FillInNoteksInit(id)){
							 		document.forms.elclass_sh.submit(); 
									}  
							 	}
							 	*/
							 	if(status==3){
							 		if(FillInNoteksInit(id)){
							 			document.forms.elclass_sh.submit(); 
									}  
							 	}
							 	if(status==0 && window.confirm("确定让创建者修改吗？")){
							 		if(FillInNoteksInit(id)){
							 		document.forms.elclass_sh.submit(); 
									}  
							 	} 
						 	} 
					function page(i) {
						document.getElementById("pageNow").value=i;
						myclist.submit();
					}
				
					function FillInNoteksInit(id){
						 var rn=2;
						 if(document.getElementById("status").value==3){
						     width=1000;
							 height=560;
							 //此地加一个拦截，用于查看时间是否存在重叠
							 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
							 rn =  window.showModalDialog("elclass_timeover_list.action?elclass.id="+id+"&PageStatus=0&x="+Math.random(),null,sFeature);
							 //return false;
						 }
						 if(rn==1 || rn==2){//点击了提交申请或者返回
						 	if(rn==2){
						 		document.getElementById("status").value=2;
						 	}
							 width=600;
							 height=500;
						  	 sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
							 var rv =  window.showModalDialog("CRE_addNotes.action?elclass.id="+id+"&Return=elclass_primash_list&x="+Math.random(),null,sFeature); 
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
					  function showCre(elclassid){
						  	 width=750;
							 height=500;  
						  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
							 var rv =  window.showModalDialog("CRE_notelistInit.action?elclass.id="+elclassid+"&examRoom.id=0&course.id=0&Return=examroom_prima_shlist&x="+Math.random(),null,sFeature); 
						}	
					function showCreks(roomid){
								  	 width=750;
									 height=500;  
								  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
									 var rv =  window.showModalDialog("CRE_notelistInit.action?examRoom.id="+roomid+"&course.id=0&elclass.id=0&Return=examroom_prima_shlist&x="+Math.random(),null,sFeature); 
								}
			</script> 
	
	</body>
</HTML>
