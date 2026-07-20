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
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>商务汉语学习系统--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((                 this .                 sectionRowIndex %  
		              2 ==   
		
		            0) ?        
		        "#ffffff" :                 "#f4f4f4" )
}
</style>
		<script type="text/javascript">
	function sh(id,status){
	    document.getElementById("elclass.id").value=id;
	    document.getElementById("status").value=status; 
	 	if(status==1 && window.confirm("确定创建完成？")){
	 		document.forms.elclass_sh.submit();
	 	} 
	}
	function sh3(id,status){   
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
	function sh5(id,status){
	    document.getElementById("elclass.id").value=id;
	    document.getElementById("status").value=status; 
	 	if(status==4 && window.confirm("确定不通过审核？")){ 
			if(FillInNoteksInit5(id)){
	 		document.forms.elclass_sh.submit();
	 		}
	 	}
	 	/*
	 	if(status==5 && window.confirm("确定通过审核？")){
			if(FillInNoteksInit(id)){
	 		document.forms.elclass_sh.submit();
	 		}
	 	}
	 	*/
	 	if(status==5){
			if(FillInNoteksInit5(id)){
	 		document.forms.elclass_sh.submit();
	 		}
	 	}
	}  
 
	 function FillInNoteksInit5(id){
		 var rn=2;
		 if(document.getElementById("status").value==5){
		     width=1000;
			 height=560;
			 //此地加一个拦截，用于查看时间是否存在重叠
			 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
			 rn =  window.showModalDialog("elclass_timeover_list.action?elclass.id="+id+"&PageStatus=1&x="+Math.random(),null,sFeature);
			 //return false;
		 }
		 if(rn==1 || rn==2){//点击了通过或者不通过
		 	if(rn==2){
		 		document.getElementById("status").value=4;
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
								  
</script>
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="培训班详情" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<table cellpadding="1" cellspacing="1" width="900px" align="center">
			<tr>
				<td width="160" height="30" align="center">
					培训班名称：
				</td>
				<td height="30">
					<label>
						<s:property value="elclass.name" />
					</label>
				</td>
			</tr>

			<tr>
				<td height="30" align="center">
					培训班介绍：
				</td>
				<td height="30">
					<label>
						<s:property value="elclass.description" />
					</label>
				</td>
			</tr>
			<tr>
				<td height="30" align="center">
					结业证书名称：
				</td>
				<td height="30">
					<label>
						<s:property value="elclass.certificatename" />
					</label>
				</td>
			</tr>
			<tr>
				<td height="30" align="center">
					所属类别：
				</td>
				<td height="30">
					<s:property value="elclass.cltype.name" />
				</td>
			</tr>
			<!-- <tr>
						<td height="30"  align="center">
						选班收费（个人）：
					</td>
					<td height="30" >
					</td>
				</tr> -->
			<tr>
				<td height="30" align="center" bgcolor="#FFFFFF">
					开始时间：
				</td>
				<td height="30">
					<s:date name="elclass.starttime" format="yyyy-MM-dd HH:mm:ss" />
				</td>
			</tr>
			<tr>
				<td height="30" align="center" bgcolor="#FFFFFF">
					结束时间：
				</td>
				<td height="30">
					<s:date name="elclass.finishtime" format="yyyy-MM-dd HH:mm:ss" />
				</td>
			</tr>
			<tr>
				<td height="30" align="center" bgcolor="#FFFFFF">
					培训班类型：
				</td>
				<td height="30">
					<s:if test="elclass.classtype==1">
					简易(<b>简易</b>:培训班中只有必修课程，学员学完必修课即可获取证书)
					</s:if>
					<s:if test="elclass.classtype==0">
					常规
					</s:if>
					<s:if test="elclass.classtype==2">
					自主培训班
					</s:if>
				</td>
			</tr>
			<tr>
				<td height="30" align="center">
					结业条件：
				</td>
				<td height="30">
					<label>
						必修课全部通过，选修课最少获得
						<b><s:property value="elclass.optionalcredit" /> </b>学分
					</label>
				</td>
			</tr>
			<!-- <tr>
				<td height="30" align="center" bgcolor="#FFFFFF">
					结业条件：
				</td>
				<td height="30">
					<div id="div_1">
						<label>
							<strong><s:property value="elclass.optionalcredit" />个学分</strong>
							(必修课全部通过，选修课最少获得的学分)
						</label>
					</div>
					<div id="div_2">
						简易培训班在学员端只显示必修课，不显示选修课,结业条件是必修课全部通过
					</div>
				</td>
			</tr> -->
			<tr>
				<td height="30" align="center" bgcolor="#FFFFFF">
					是否可申请：
				</td>
				<td height="30">
					<s:if test="elclass.isApplication==0">
						 	不可申请
					  </s:if>
					<s:if test="elclass.isApplication==1">
						 	可申请
					  </s:if>
					<div id="divSQ_1"
						<s:if test="elclass.isApplication==0">style="display:none;"</s:if>>
						<table width="100%" cellpadding="2" cellspacing="1">
							<tr>
								<td colspan="2">
									是否需要审核:
									<s:if test="elRegistration.isAudit==1">需要</s:if>
									<s:if test="elRegistration.isAudit==0">不需要</s:if>

								</td>
							</tr>
							<tr>
								<td width="100" align="center" bgcolor="#FFFFFF">
									计划招收学员:
								</td>
								<td>
									<s:property value="elRegistration.PlanRecruitStudents" />
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<span style="color: red">申请条件</span>
								</td>
							</tr>
							<tr>
								<td width="100" align="center" bgcolor="#FFFFFF">
									报名时间段:
								</td>
								<td>
									<s:date name="elRegistration.RegistrationStartTime"
										format="yyyy-MM-dd HH:mm:ss" />
									～
									<s:date name="elRegistration.RegistrationStopTime"
										format="yyyy-MM-dd HH:mm:ss" />
								</td>
							</tr>
							<tr>
								<td width="100" align="center" bgcolor="#FFFFFF">
									年龄段:
								</td>
								<td>
									<s:property value="elRegistration.StartAge" />
									～
									<s:property value="elRegistration.StopAge" />
								</td>
							</tr>
							<tr>
								<td width="100" align="center" bgcolor="#FFFFFF">
									性别:
								</td>
								<td>
									<label>
										<s:property value="elRegistration.sex" />
									</label>
								</td>
							</tr>
							<tr>
								<td width="100" align="center" bgcolor="#FFFFFF">
									部门:
								</td>
								<td>
									<s:property value="elRegistration.treeTypeName" />
								</td>
							</tr>
							<tr>
								<td width="100" align="center" bgcolor="#FFFFFF">
									<wysLib:BasetName btid="1" />
									:
								</td>
								<td>
									<s:property value="elRegistration.jingzhongName" />
								</td>
							</tr>
							<tr>
								<td width="100" align="center" bgcolor="#FFFFFF">
									<wysLib:BasetName btid="5" />
									:
								</td>
								<td>
									<s:property value="elRegistration.dishiName" />
								</td>
							</tr>
							<tr>
								<td width="100" align="center" bgcolor="#FFFFFF">
									<wysLib:BasetName btid="2" />
									:
								</td>
								<td>
									<s:property value="elRegistration.zhiwuName" />
								</td>
							</tr>
							<tr>
								<td width="100" align="center" bgcolor="#FFFFFF">
									<wysLib:BasetName btid="3" />
									:
								</td>
								<td>
									<s:property value="elRegistration.zhijiName" />
								</td>
							</tr>
							<tr><td width="100" align="center" bgcolor="#ECF6FD">
									 考 场:
										</td>
										<td bgcolor="#FFFFFF">
											 	<span style="color:blue"><b>【条件】</b><br/>
											<s:property escape="false" value="elRegistration.erParasMsg" /></span>
												 
										</td></tr>
									<tr><td width="100" align="center" bgcolor="#ECF6FD">
											考场试卷:
										</td>
										<td colspan="3" bgcolor="#FFFFFF">
												<span style="color:blue"><b>【条件】</b><br/>
											<s:property value="elRegistration.erepParasMsg" escape="false" /></span>
										</td></tr>
									<tr>
										<td width="100" align="center" bgcolor="#ECF6FD">
											培训班:
										</td>
										<td colspan="3" bgcolor="#FFFFFF">
												<span style="color:blue"><b>【条件】</b><br/>
											<s:property value="elRegistration.classParasMsg" escape="false" /></span>
										</td>
									</tr>
							<!-- <tr>
								<td width="100" align="center" bgcolor="#FFFFFF">
									考场:
								</td>
								<td>
									<s:property value="elclass.elRegistration.examroomName" />
									<strong><s:if
											test="elclass.elRegistration.eroomScreeningWay == 0 ">全部</s:if>
										<s:if test="elclass.elRegistration.eroomScreeningWay == 1 ">通过</s:if>
										<s:if test="elclass.elRegistration.eroomScreeningWay == 2 ">不通过</s:if>
									</strong>
								</td>
							</tr>
							<tr>
								<td width="100" align="center" bgcolor="#FFFFFF">
									培训班:
								</td>
								<td>
									<s:property value="elclass.elRegistration.elclassName" />
									<strong> <s:if
											test="elclass.elRegistration.classScreeningWay == 0 ">全部</s:if>
										<s:if test="elclass.elRegistration.classScreeningWay == 1 ">通过</s:if>
										<s:if test="elclass.elRegistration.classScreeningWay == 2 ">不通过</s:if>
									</strong>
								</td>
							</tr> -->
						</table>
					</div>
					<div id="divSQ_2" style="display: none;">
					</div>
				</td>
			</tr>
			<tr>
				<td height="30" colspan="2" align="left">
					<b>必修课程</b>
					<table width="100%" align="center" cellpadding="1" cellspacing="1"
						id="table1">
						<tr>
							<th width="30" height="30" align="center">
								ID
							</th>
							<th width="100" height="30" align="center">
								课程名称
							</th>
							<th width="100" height="30" align="center">
								开始时间
							</th>
							<th width="100" height="30" align="center">
								结束时间
							</th>
							<th width="60" height="30" align="center">
								建议学分
							</th>
							<th width="60" height="30" align="center">
								设置学分
							</th>
							<th width="60" height="30" align="center">
								结业方式
							</th>
						</tr>
						<tbody onMouseOut="changeback()" onMouseOver="changeto()">
							<s:iterator value="elclass.bxCourse">
								<tr>
									<td height="30" align="center">
										<s:property value="id" />
									</td>
									<td height="30" align="left">
										<s:property value="name" />
									</td>

									<td id="start" height="30" align="center">
										<s:date name="roomstart" format="yyyy-MM-dd~HH:mm:ss" />
									</td>
									<td id="end" height="30" align="center">
										<s:date name="roomend" format="yyyy-MM-dd~HH:mm:ss" />
									</td>
									<td height="30" align="center">
										<s:property value="suggestcredit" />
									</td>
									<td height="30" align="center">
										<s:property value="setcredit" />
									</td>
									<td height="30" align="center" bgcolor="#ECEDEB">
										<s:if test="getcredit == 1">
											学完
									</s:if>
										<s:elseif test="getcredit == 2">
											考过
									</s:elseif>
										<s:elseif test="getcredit == 3">
											学完且考过
									</s:elseif>
										<s:else>
											学完
									</s:else>
									</td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
					<b>选修课程</b>
					<table width="100%" align="center" cellpadding="1" cellspacing="1"
						id="table1">
						<tr>
							<th width="30" height="30" align="center">
								ID
							</th>
							<th width="100" height="30" align="center">
								课程名称
							</th>
							<th width="100" height="30" align="center">
								开始时间
							</th>
							<th width="100" height="30" align="center">
								结束时间
							</th>
							<th width="60" height="30" align="center">
								建议学分
							</th>
							<th width="60" height="30" align="center">
								设置学分
							</th>
							<th width="60" height="30" align="center">
								结业方式
							</th>
						</tr>
						<tbody onMouseOut="changeback()" onMouseOver="changeto()">
							<s:iterator value="elclass.xxCourse">
								<tr>
									<td height="30" align="center">
										<s:property value="id" />
									</td>
									<td height="30" align="left">
										<s:property value="name" />
									</td>

									<td id="start" height="30" align="center">
										<s:date name="roomstart" format="yyyy-MM-dd~HH:mm:ss" />
									</td>
									<td id="end" height="30" align="center">
										<s:date name="roomend" format="yyyy-MM-dd~HH:mm:ss" />
									</td>
									<td height="30" align="center">
										<s:property value="suggestcredit" />
									</td>
									<td height="30" align="center">
										<s:property value="setcredit" />
									</td>
									<td height="30" align="center" bgcolor="#ECEDEB">
										<s:if test="getcredit == 1">
											学完
									</s:if>
										<s:elseif test="getcredit == 2">
											考过
									</s:elseif>
										<s:elseif test="getcredit == 3">
											学完且考过
									</s:elseif>
										<s:else>
											学完
									</s:else>
									</td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
				</td>
			</tr>
			<tr>
				<td height="30" colspan="2" align="left">
					<a id="student"><b>学员列表</b></a>
					<table width="100%" align="center" cellpadding="1" cellspacing="1"
						bgcolor="#EBEBEB">
						<tr>
							<td height="30" align="center" bgcolor="#66CCFF">
								姓名
							</td>
							<td height="30" align="center" bgcolor="#66CCFF">
								性别
							</td>
							<td height="30" align="center" bgcolor="#66CCFF">
								账号
							</td>
							<td height="30" align="center" bgcolor="#66CCFF">
								部门
							</td>
							<td height="30" align="center" bgcolor="#66CCFF">
								<wysLib:BasetName btid="1" />
							</td>
							<td height="30" align="center" bgcolor="#66CCFF">
								年龄
							</td>
							<td height="30" align="center" bgcolor="#66CCFF">
								角色
							</td>
							<td height="30" align="center" bgcolor="#66CCFF">
								参加方式
							</td>
						</tr>
						<s:if test="elusers.size==0">
							<TR>
								<TD align="center" colspan="4">
									当前还没有分配学员
								</TD>
							</TR>
						</s:if>
						<s:else>
							<s:iterator value="elusers">
								<tr>
									<td height="30" style="color: blue;" align="center">
										<s:property value="realname" />
									</td>
									<td height="30" align="center">
										<s:property value="sex" />
									</td>
									<td height="30" align="center">
										<s:property value="username" />
									</td>
									<td height="30" align="center">
										<s:property value="department.name" />
									</td>
									<td height="30" align="center">
										<s:property value="jingzhong_" />
									</td>
									<td height="30" align="center">
										<s:property value="age" />
									</td>
									<td height="30" align="center">
										<s:property value="role.name" />
									</td>
									 
									<td height="30" align="center">
										<s:property value="joinway" />
									</td>

								</tr>
							</s:iterator>
						</s:else>
					</table>
					<wysLib:page></wysLib:page>
					<script type="text/javascript">
						function page(i) {
							document.location='elclass_view_man.action?elclass.id=<s:property value="elclass.id"/>&sublibs=1&pN='+i+"#student";
						}
					</script>
				</td>
			</tr>
			<tr>
				<td height="30" align="center">
					培训班状态：
				</td>
				<td height="30">
					<label>
						<font color="blue"><s:property value="elclass.statusName" />
							<s:if test="elclass.status==1&&elclass.bxCount==0">
								<font color="red"> (请等待上级提交申请)</font>
							</s:if> <s:if test="elclass.status==3&&elclass.xxCount==0">
								<font color="red"> (请等待上级核准) </font>
							</s:if> </font>
					</label>
				</td>
			</tr>
			<tr>
				<td height="50" colspan="2" align="center">
					<form action="elclass_sh.action?state=1" name="elclass_sh"
						method="post">
						<s:hidden name="elclass.id" id="elclass.id"></s:hidden>
						<s:hidden name="status" id="status"></s:hidden>
						<s:hidden name="sublibs" value="1"></s:hidden>
						<s:if test="elclass.status==0">
							<s:hidden name="Return" id="Return" value="elclass_alllist"></s:hidden>
						</s:if>
						<s:if test="elclass.status==1">
							<input type="hidden" name="Return" value="elclass_primash_list"
								id="Return" />
						</s:if>
						<s:if test="elclass.status==3">
							<s:hidden name="Return" id="Return" value="elclass_sh_list"></s:hidden>
						</s:if>
					</form>
					<a href="elclass_alterInit.action?elclass.id=${elclass.id }"
						class=textbg>修改</a>
					<a href="elclass_alllist.action" class=textbg>班级列表</a>
					<a href="elclass_course.action?elclass.id=${elclass.id }"
						class=textbg style="color: red;">课程管理</a>
					<a
						href="elclass_assign2userInit.action?sub_department=1&elclass.id=${elclass.id }"
						class=textbg style="color: red;">分配学员</a>
					<s:if test="elclass.status==0">
						<input type="button" onclick="sh(${elclass.id }, 1);" class=textbg
							style="color: red; border: none;" value="创建完成" />
					</s:if>
					<s:if test="elclass.status==1">
						<s:if test="elclass.bxCount==1">
							<input type="button" onclick="sh3(${elclass.id }, 3);"
								class=textbg style="color: red; border: none;" value="提交申请" />
						</s:if>
					</s:if>
					<s:if test="elclass.status==3">
						<s:if test="elclass.xxCount==1">
							<input type="button" onclick="sh5(${elclass.id }, 5);"
								class="textbg" style="color: red; border: none;" value="核准" />
						</s:if>
					</s:if>
				</td>
			</tr>
		</table>
	</body>
</HTML>
