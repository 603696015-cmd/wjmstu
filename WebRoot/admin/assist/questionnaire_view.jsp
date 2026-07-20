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
	background-color: expression((         this .         sectionRowIndex %         2 ==
		        0) ?    
		    "#ffffff" :         "#f4f4f4" )
}
</style>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/assist.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/libutil.js"></script>
		<script type="text/javascript">
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
		function sh2(id,valid){
		    document.getElementById("examRoom.id").value=id;
		    document.getElementById("examRoomValid").value=valid; 
		 	if(valid==4 && window.confirm("确定返回申请？")){
				if(FillInNoteksInit2(id)){ 
		 			document.forms.examroom_sh_p.submit();
		 		}
		 	}
		 	if(valid==5){
				if(FillInNoteksInit2(id)){ 
		 			document.forms.examroom_sh_p.submit();
		 		}
		 	}
		}
		function FillInNoteksInit2(id){
			 var rn=2;
			 if(document.getElementById("examRoomValid").value==5){
			     width=1000;
				 height=560;
				 //此地加一个拦截，用于查看时间是否存在重叠
				 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 rn =  window.showModalDialog("eroom_timeover_list.action?examRoom.id="+id+"&PageStatus=1&x="+Math.random(),null,sFeature);
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
		function sh0( ){
			var b = false;
			if(<s:property value="examRoom.isApplication" />==0&&<s:property value="myrooms.size"/>==0)
				b = true;
		    if(window.confirm(b?"此考场为分配式，但未分配人员，继续确定创建完成？":"确定创建完成？"))
		    	document.location='examroom_valid.action?examRoom.id=${examRoom.id }'
		}
		</script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="考场基本信息" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">考场查看</span>
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
			<span style="color: #ff0000;"></span>
			<table width="90%" align="center" cellpadding="1" cellspacing="1"
				bgcolor="#EBEBEB">
				<tr>
					<td width="160" height="30" align="center">
						问卷标题
					</td>
					<td>
						<label>
							<s:property value="examRoom.title" />
						</label>
					</td>
				</tr>
				<tr style="display:none">
					<td align="center">
						所属试卷库
					</td>
					<td bgcolor="#FFFFFF" colspan="3">
						<label>
							${examRoom.eroomLib.name}

						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="center">
						问卷说明
					</td>
					<td>
						<label>
							<s:property value="examRoom.description" />
						</label>
					</td>
				</tr>
				<tr style="display:none">
					<td height="30" align="center">
						考试地点
					</td>
					<td>
						<label>
							<s:property value="examRoom.location" />
						</label>
					</td>
				</tr>
				<tr style="display:none">
					<td height="30" align="center">
						监考人员
					</td>
					<td>
						<div id="invigilators">
							<s:iterator value="examRoom.invigilators">
								<span
									style="width: 110px; height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
									<label style="width: 80px; float: left;">
										<s:property value="realname" />
									</label> </span>
							</s:iterator>
						</div>
					</td>
				</tr>
				<tr style="display:none">
					<td height="30" align="center">
						阅卷人员
					</td>
					<td>
						<div id="appraises">
							<s:iterator value="examRoom.appraises">
								<span
									style="width: 110px; height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
									<label style="width: 80px; float: left;">
										<s:property value="realname" />
									</label> </span>
							</s:iterator>
						</div>
					</td>
				</tr>
				<tr style="display:none">
					<td height="30" align="center">
						复核人员
					</td>
					<td>
						<div id="valids">
							<s:iterator value="examRoom.valids">
								<span
									style="width: 110px; height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
									<label style="width: 80px; float: left;">
										<s:property value="realname" />
									</label> </span>
							</s:iterator>
						</div>
					</td>
				</tr>
				<tr>
					<td height="30" align="center">
						问卷调查时间
					</td>
					<td>
						<label>
							问卷开始时间
							<s:date name="examRoom.begintime" format="yyyy-MM-dd HH:mm:ss" />
						</label>
						<br />
						<label>
							问卷结束时间
							<s:date name="examRoom.endtime" format="yyyy-MM-dd HH:mm:ss" />
						</label>
					</td>
				</tr>
				<tr style="display:none">
					<td height="30" align="center">
						是否可申请
					</td>
					<td>
						<label>
							<s:if test="examRoom.isApplication==0">
						 	不可申请
						 </s:if>
						 <s:if test="examRoom.isApplication==1">
						 	可申请
						 </s:if>
						 <s:if test="examRoom.isApplication==2">
						 	全工
						 </s:if>
						</label>
						<div id="divSQ_1" <s:if test="examRoom.isApplication!=1">style="display:none;"</s:if>>
							<table width="95%" cellpadding="2" cellspacing="1"
								>
								<!--<tr>
									<td width="100" height="20" align="center" bgcolor="#E6F9F9">
										复核人员:
									</td>
									<td>
										<div id="valids">
											<s:iterator value="elclass.valids">
												<span
													style="width: 110px; height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
													<label style="width: 80px; float: left;">
														<s:property value="realname" />
													</label> <a
													style="cursor: hand; float: right; width: 14px; height: 14px;"
													href=""
													onclick="javascript:deleteUserinfo(this,<s:property value="id"/>,'valids');return false;">X</a>
												</span>
											</s:iterator>
										</div>
										<a href=""
											onclick="searchUserInit('valids','elclass.valids.id'); return false;" class="textbg4">添加</a>
									</td>
								</tr>-->
								<tr>
									<td colspan="2">
										是否需要审核：
										<s:if test="erRegistration.isAudit==1">需要</s:if>
										<s:if test="erRegistration.isAudit==0">不需要</s:if>
										 
									</td>
								</tr>
								<tr>
									<td width="100" align="right" bgcolor="#E6F9F9">
										<span class="neededitem">*</span>计划招收学员：
									</td>
									<td>
										<s:property value="erRegistration.PlanRecruitStudents" />
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<span style="color: red">申请条件</span>
									</td>
								</tr>
								<tr>
									<td width="100" align="right" bgcolor="#E6F9F9">
										<span class="neededitem">*</span>报名时间段：
									</td>
									<td>
										 <s:date name='erRegistration.RegistrationStartTime' format="yyyy-MM-dd HH:mm:ss" /> 
												～
											 <s:date name='erRegistration.RegistrationStopTime' format="yyyy-MM-dd HH:mm:ss" /> 
									</td>
								</tr>
								<tr>
									<td width="100" align="right" bgcolor="#E6F9F9">
										年龄段：
									</td>
									<td>
										<s:property value="erRegistration.StartAge" />
										～
										<s:property value="erRegistration.StopAge" />
									</td>
								</tr>
								<tr>
									<td width="100" align="right" bgcolor="#E6F9F9">
										性别：
									</td>
									<td>
										<label>
											<s:property value="erRegistration.sex" />
										</label>
									</td>
								</tr>
								<tr>
									<td  width="100" align="right" bgcolor="#E6F9F9">部门：</td>
									<td>      
										<div id="danwei" <s:if test="erRegistration.treeType == null ">style="background-color:blank;display: none;"</s:if> > 
											<s:property value="erRegistration.treeTypeName"/>
										</div> 
 										<span class="txt-info"></span>
									</td>
								</tr> 
								<tr>
									<td width="100" align="right" bgcolor="#E6F9F9">
										<wysLib:BasetName btid="1" />：
									</td> 
									<td>
										<div id="jztj" <s:if test="erRegistration.jingzhong == null ">style="background-color:blank;display: none;"</s:if> > 
											<s:property value="erRegistration.jingzhongName"/>
										</div>
 										<span class="txt-info"></span> 
									</td> 
								</tr>
								<tr>
									<td width="100" align="right" bgcolor="#E6F9F9">
										<wysLib:BasetName btid="5" />：
									</td>
									<td>   
										<div id="dstj" <s:if test="erRegistration.dishi == null ">style="background-color:blank;display: none;"</s:if> > 
											<s:property value="erRegistration.dishiName"/>
										</div>
 										<span class="txt-info"></span>
									</td>
								</tr>
								<tr>
									<td width="100" align="right" bgcolor="#E6F9F9">
										<wysLib:BasetName btid="2" />：
									</td>
									<td>
										<div id="zwtj"  <s:if test="erRegistration.zhiwu == null ">style="background-color:blank;display: none;"</s:if>>  
											<s:property value="erRegistration.zhiwuName"/>
										</div>
 										<span class="txt-info"> </span> 
									</td>
								</tr>
								<tr>
									<td width="100" align="right" bgcolor="#E6F9F9">
										<wysLib:BasetName btid="3" />：
									</td>
									<td>
										<div id="zjtj" <s:if test="erRegistration.zhiji == null ">style="background-color:blank;display: none;"</s:if>> 
											<s:property value="erRegistration.zhijiName"/>
										</div>
 										<span class="txt-info">  </span> 
									</td>
								</tr>
								<%-- 
								<tr>
									<td width="100" align="right" bgcolor="#E6F9F9">
										<wysLib:BasetName btid="4" />:
									</td>
									<td>
										<div id="gwtj" <s:if test="erRegistration.gangwei == null ">style="background-color:blank;display: none;"</s:if>> 
											<s:property value="erRegistration.gangweiName"/>
										</div>
 										<span class="txt-info"><a href="#"  onClick="searBaseDatatInit(4);return false;">点此进行选择</a></span> 
									</td>
								</tr>
								 --%><tr><td width="100" align="right" bgcolor="#ECF6FD">
										 考场：
										</td>
										<td bgcolor="#FFFFFF">
											 	<span style="color:blue"><b>【条件】</b><br/>
											<s:property escape="false" value="erRegistration.erParasMsg" /></span>
												 
										</td></tr>
									<tr><td width="100" align="right" bgcolor="#ECF6FD">
										 考场试卷：
										</td>
										<td colspan="3" bgcolor="#FFFFFF">
												<span style="color:blue"><b>【条件】</b><br/>
											<s:property value="erRegistration.erepParasMsg" escape="false" /></span>
										</td></tr>
									<tr>
										<td width="100" align="right" bgcolor="#ECF6FD">
										 培训班：
										</td>
										<td colspan="3" bgcolor="#FFFFFF">
												<span style="color:blue"><b>【条件】</b><br/>
											<s:property value="erRegistration.classParasMsg" escape="false" /></span>
										</td>
									</tr>
								 <!-- 
								<tr>
									<td width="100" align="right" bgcolor="#E6F9F9">
										考场：
									</td>
									<td> 
										<div id="KC" <s:if test="erRegistration.examroomName == null ">style="display: block;width: 100%;"</s:if>>
										</div>
										<%-- 
										<div id="KC_"  <s:if test="erRegistration.examRoom.size == 0 ">style="display: none;width: 100%;"</s:if>>
											<s:property value="erRegistration.examroomName"/>
											<a  href="" style="color:red"  <s:if test="erRegistration.examRoom.size == 0">style="display: none;"</s:if>
												onclick="javascript:deleteNames(1);return false;">X</a>  
										</div>
		  									<s:hidden name="erRegistration.examroomNam"  ></s:hidden> 
		  								 --%>
												 <s:if test="erRegistration.eroomScreeningWay == 0 ">全部</s:if> 
												 <s:if test="erRegistration.eroomScreeningWay == 1 ">通过</s:if> 
												 <s:if test="erRegistration.eroomScreeningWay == 2 ">不通过</s:if> 
										<span class="txt-info"></span>
									</td>
								</tr>
								<tr>
									<td width="100" align="right" bgcolor="#E6F9F9">
										培训班：
									</td>
									<td>  
										<div id="PXB" <s:if test="erRegistration.elclassName == null ">style="display: block;width: 100%;"</s:if>> 
										</div>
										<%-- 									
										<div id="PXB_" <s:if test="erRegistration.elclass.size == 0 ">style="display: none;width: 100%;"</s:if>>
											<s:property value="erRegistration.elclassName"/>
											<a  href="" style="color:red"  <s:if test="erRegistration.elclasss.size == 0 ">style="display: none;"</s:if>
												onclick="javascript:deleteNames(2);return false;">X</a> 
										</div>
		  									<s:hidden name="erRegistration.elclassName"  ></s:hidden>
		  								 --%>
		  									
											 <s:if test="erRegistration.classScreeningWay == 0 ">全部</s:if> 
											 <s:if test="erRegistration.classScreeningWay == 1 ">通过</s:if> 
											 <s:if test="erRegistration.classScreeningWay == 2 ">不通过</s:if> 
									
										<span class="txt-info"></span> 
									</td>
								</tr> -->
							</table>
						</div>
					</td>
				</tr>

				<tr style="display:none">
					<td height="30" align="center">
						绑定mac地址：
					</td>
					<td>
						<s:if test="examRoom.isMacBand==1">
								是
							</s:if>
						<s:if test="examRoom.isMacBand==0">
								否
							</s:if>
					</td>
				</tr>
				<tr style="display:none">
					<td height="30" align="center">
						限&nbsp;定&nbsp;ip&nbsp;段：
					</td>
					<td>
						<s:if test="examRoom.isIpLimit==1">
								是
							</s:if>
						<s:if test="examRoom.isIpLimit==0">
								否
							</s:if>
					</td>
				</tr>
				<s:if test="examRoom.isIpLimit==1">
					<tr style="display:none">
						<td colspan="2">
							<div>
								<div style="float: left">
									<s:iterator id="ipStrat" value="#request.ipStratList"
										status="statu">
										<div>
											开&nbsp;始&nbsp;ip：
											<s:property value='ipStrat' />
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</div>
									</s:iterator>
								</div>
								<div>
									<s:iterator id="ipEnd" value="#request.ipEndList"
										status="statu">
										<div>
											结&nbsp;束&nbsp;ip：
											<s:property value='ipEnd' />
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</div>
									</s:iterator>
								</div>
							</div>
						</td>
					</tr>
				</s:if>
				<tr style="display:none">
					<td height="30" align="center">
						通过成绩：
					</td>
					<td>
						<label>
							<s:property value="examRoom.passgrade" />
							%
						</label>
					</td>
				</tr>
				<tr style="display:none">
					<td height="30" align="center">
						学分
					</td>
					<td>
						<label>
							<s:property value="examRoom.score" />
						</label>
					</td>
				</tr>
				<tr style="display:none">
						<td align="right">
							 设定密码：
						</td>
						<td>
						 <s:if test="examRoom.pwdneed==0">不需要</s:if> 
						 <s:if test="examRoom.pwdneed==1">需要</s:if> 
						 <div id="pwd_div"  <s:if test="examRoom.pwdneed==0">style="display: none;"</s:if>>当前密码:<s:property value="examRoom.pwd"/> &nbsp;有效期至: <s:date name="examRoom.pwdtime" format="yyyy-MM-dd HH:mm:ss"/></div>
						</td>
					</tr>
				<tr>
					<td height="30" align="center">
						所用试卷
					</td>
					<td>
						<div id="eps_div">
							<s:iterator value="examRoom.exampapers" status="epsst">
								<div>
									<span style="width: 250"> <s:property value="title" />
									</span>
									
								</div>
							</s:iterator>
						</div>
					</td>
				</tr>
				<s:if test="examRoom.type==1">
					<tr style="display:none">
						<td height="30" align="center">
							选拔人员
						</td>
						<td>
							<div id="selectings">
								<s:iterator value="examRoom.selectings">
									<span
										style="width: 110px; height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
										<label style="width: 80px; float: left;">
											<s:property value="realname" />
										</label> </span>
								</s:iterator>
							</div>
						</td>
					</tr>
				</s:if>
				<tr style="display:none">
					<td height="50" align="center">
						考生列表
					</td>
					<td>
						<s:if test="myrooms.size==0">
						<br>
						暂无考生
						</s:if>
						<s:else>
							<div style="font-weight: bolder;" id="students">
									考生列表
							</div>
							<table width="100%" align="center" cellspacing="1" cellpadding="1">
								<tr>
									<td height="30" style="padding-left: 8px; color: blue;"
										align="left">
										姓名/账号
									</td>
									<td height="30" align="center">
										部门
									</td>
									<td height="30" align="center">
										试卷数量
									</td>
								</tr>
								<s:iterator value="myrooms">
									<tr>
										<td height="30" style="padding-left: 8px; color: blue;"
											align="left">
											<s:property value="tester.realname" />
											/
											<s:property value="tester.username" />
										</td>
										<td height="30" align="center">
											<s:property value="tester.department.name" />
										</td>
										<td height="30" align="center">
											<s:property value="epsize" />
										</td>
									</tr>
								</s:iterator>
							</table>
							<script type="text/javascript">
								function page(i){
										document.location.href="erwithout_view.action?examRoom.id=<s:property value="examRoom.id"/>&pS=<s:property value="pS"/>&pN="+i+"#students"
								}
							</script>
							<wysLib:page></wysLib:page>
						</s:else>
					</td>
				</tr>
				<tr>
					<td height="50" align="center">
						问卷状态
					</td>
					<td height="50" align="left" style="font-size: 14px;">
						<s:property value="examRoom.validName" />
						<font color="red"> <s:if test="examRoom.valid==1">
								<s:if test="examRoom.pass0_1==0">
								(请等待上级管理员提交申请)
							</s:if>
							</s:if> <s:if test="examRoom.valid==3">
								<s:if test="examRoom.pass1_2==0">
								(请等待上级管理员核准)
							</s:if>
							</s:if> </font>
					</td>
				</tr>
			</table>
			<form action="examroom_sh_p.action" name="examroom_sh_p"
				method="post">
				<input type="hidden" name="examRoom.id" value="" id="examRoom.id" />
				<input type="hidden" name="examRoom.valid" value=""
					id="examRoomValid" />
			</form>
	<!-- 	<input class="textbg" style="border: none;" type="button"
				value="添加考场"
				onclick="document.location='erwithout_addInit.action?course.id=${examRoom.course.id }'"> -->	
			<s:if test="Return==null||Return==''">
			<input class="textbg" style="border: none;" type="button"
				value="返回问卷列表"
				onclick="document.location='questionnaireList.action'" /></s:if>
			<s:if test="Return=='assign'">
			<input class="textbg" style="border: none;" type="button"
				value="返回分配列表" onClick="document.location='examroomwithoutcourse_list.action'" />
			</s:if>
			<s:if test="Return=='ash'">
			<input class="textbg" style="border: none;" type="button"
				value="返回申请列表" onClick="document.location='questionnaire_prima_shlist.action'" />
			</s:if>
			<s:if test="Return=='sh'">
			<input class="textbg" style="border: none;" type="button"
				value="返回审核列表" onClick="document.location='examroom_shlist.action'" />
			</s:if>
			<s:if test="Return=='delsh'">
			<a href="examroom_application_delete_shlist.action" class="textbg4" style="width:120px">返回删除申请列表</a>
			</s:if>
			<s:if
				test="examRoom.valid==0||(examRoom.valid==1&&examRoom.pass0_1==1)||(examRoom.valid==3&&examRoom.pass1_2==1)">
				<input class="textbg" style="border: none;" type="button"
					value="修改问卷"
					onclick="document.location='questionnaire_alterInit.action?examRoom.id=${examRoom.id }'" />
				<input class="textbg" style="border: none; color: red" type="button"
					value="分配学员"
					onclick="document.location='examroom_assignwcInit.action?examRoom.id=${examRoom.id }&course.id=${examRoom.course.id }'" />
			</s:if>
			<s:if test="examRoom.valid==0">
				<input class="textbg" title="确认本考场的详细信息、人员等信息后创建完成，进行后续的审核！"
					style="border: none; color: red" type="button" value="创建完成"
					onclick="sh0()" />
			</s:if>
			<s:if test="examRoom.valid==1">
				<s:if test="examRoom.pass0_1==1">
					<input class="textbg" style="border: none; color: red"
						type="button" value="提交申请" onClick="sh(${examRoom.id }, 3);" />
				</s:if>
			</s:if>
			<s:if test="examRoom.valid==3">
				<s:if test="examRoom.pass1_2==1">
					<input class="textbg" style="border: none; color: red"
						type="button" value="核准" onClick="sh2(${examRoom.id }, 5);" />
				</s:if>
			</s:if>
		</div>
		<br />
		<br />
		<br />
		<br />
		<!-- 内容 -->
	</BODY>
</HTML>
