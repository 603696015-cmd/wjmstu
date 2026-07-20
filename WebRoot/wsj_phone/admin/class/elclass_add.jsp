<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.classman.entities.ElClType"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@page import="com.sopia.classman.entities.ElClass"%>

<%
	String cltypeId = "";
	if (request.getAttribute("elclass") != null) {
		cltypeId = ((ElClass) request.getAttribute("elclass"))
				.getCltype().getId()
				+ "";
	}

	ElClType cltypeTree = (ElClType) request.getAttribute("cltypeTree");
%>

<HTML>
	<HEAD>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>中国食品安全培训网--管理端--</TITLE>
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
</style>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/libutil.js"></script>
		<script type="text/javascript" src="js/elclassop.js"></script>
		<script type="text/javascript" src="js/inputcheck.js"></script>
		<script type="text/javascript" src="js/basedataop.js"></script>
		<script type="text/javascript">
		function disDiv(op){
			var div1=document.getElementById("div_1");
			var div2=document.getElementById("div_2");
			var div3=document.getElementById("div_3");
			if(op==1){
				div1.style.display="none";
				div2.style.display="block";
				div3.style.display="none";
				document.getElementById("optional").value="";
			}else if(op==3){ 
				div1.style.display="none";
				div2.style.display="none";
				div3.style.display="block";
			}else if(op==2){
				div1.style.display="block";
				div2.style.display="none";
				div3.style.display="none";
			}
		}
		function disDivSQ(op){
			var divSQ1=document.getElementById("divSQ_1");
			var divSQ2=document.getElementById("divSQ_2");
			if(op==1){
				isApplication = 1;
				divSQ1.style.display="none";
				divSQ2.style.display="block";
				document.getElementById("optional").value="";
			}else{
				isApplication = 2;
				divSQ1.style.display="block";
				divSQ2.style.display="none";
			}
		}
		function init(){
			var div1=document.getElementById("div_1");
			var div2=document.getElementById("div_2"); 
			div1.style.display="block";
			div2.style.display="none";
		}
		function TJ(id,is){ 
			if(is == 1){
			document.getElementById(id).style="display:block";
			}else 
			if(is == 0){
			document.getElementById(id).style="display:none"; 
			}
		}
		function searchUserInit_(){
		     width=600;
			 height=500;
		  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
			 var rv =  window.showModalDialog("userRegister.action?isreg=1&x="+Math.random(),null,sFeature);
			 //alert(rv);
			 if(rv!=undefined&&rv!=""){
				 //var bh=rv.split("_");
				 var bh=rv.split("-=wys=-");
				 document.getElementById("danwei").value=bh[0];
				 //document.getElementById("danweiName").innerHTML=bh[1];
				 document.getElementById("danweiName").value=bh[1];
			 }
		}
	</script>
	</HEAD>
	<body onLoad="init();">
		<div class="dh3">
			<!--<div class="newpos"></div>
			<div class="newpos2">
				<a href="cltype_list.action">培训班类别管理</a>
				<span style="font-weight:bold;">培训班添加</span>
			</div>-->
			<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
				<wysLib:Navigation ivalue="填写培训班基本信息" />
			</div>
		</div>
		<s:form action="elclass_add" theme="simple" method="post"
			name="class_info">
			<table width="100%" cellpadding="2" cellspacing="1">
				<tr>
					<td width="160" height="30" align="right" bgcolor="#FFFFFF">
						<span class="neededitem">*</span>培训班名称：
					</td>
					<td height="30">
						&nbsp;<label>
							<s:textfield name="elclass.name" id="name" size="60" />
						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="right" bgcolor="#FFFFFF">
						培训班介绍：
					</td>
					<td height="30">
						&nbsp;<label>
							<s:textarea name="elclass.description" cols="60" rows="7" />
						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="right" bgcolor="#FFFFFF">
						课程图片：
					</td>
					<td>
						&nbsp;<label>
							<s:textfield name="elclass.mainimg" id="pic" size="60" />
							<a href="javascript:setUrl('pic');" class="textbg4"
								style="width: 80px">浏览资源库</a>
						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="right" bgcolor="#FFFFFF">
						<span class="neededitem">*</span>结业证书名称：
					</td>
					<td height="30">
						&nbsp;<label>
							<s:textfield name="elclass.certificatename" id="certificate_name" />
						</label>
					</td>
				</tr>

				<tr>
					<td height="30" align="right" bgcolor="#FFFFFF">
						<span class="neededitem">*</span>所属类别：
						<%--=cltypeId --%>
					</td>
					<td height="30">
						&nbsp;<label>
							<s:if test="elclass.cltype.id != null">
								<wysLib:clTypeTree iname="elclass.cltype.id" itype="ra"
									ivalue="<%=cltypeId%>" href="" rootAble="true" />
								<!-- ivalue="${elclass.cltype.id}" -->
							</s:if>
							<s:else>
								<!--  -->
								<wysLib:clTypeTree iname="elclass.cltype.id" itype="ra_2no" />
							</s:else>
						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="right" bgcolor="#FFFFFF">
						<span class="neededitem">*</span>开始时间：
					</td>
					<td height="30">
						&nbsp;<input name="elclass.starttime" id="r_start" type="text" size="20"
							value="<s:property value='elclass.starttime'/>">
							&nbsp;
							<input type="button" class="textbg4"
									onclick="setday(document.getElementById('r_start'))"
									value="选择" />
					</td>
				</tr>
				<tr>
					<td height="30" align="right" bgcolor="#FFFFFF">
						<span class="neededitem">*</span>结束时间：
					</td>
					
					<td height="30">
						&nbsp;<input name="elclass.finishtime" id="r_end" type="text" size="20"
							value="<s:property value='elclass.finishtime'/>">
							&nbsp;
							<input type="button" class="textbg4"
									onclick="setday(document.getElementById('r_end'))"
									value="选择" />
					</td>
				</tr>

				<tr style="display: none">
					<td height="30" align="right" bgcolor="#FFFFFF">
						<span class="neededitem">*</span>培训班类型：<%=cltypeTree.getId()%>
					</td>
					<td height="30">
						<input type="radio"
							<s:if test="elclass.global==0">checked='checked'</s:if>
							onclick="document.getElementById('group_div').style.display='none'"
							name="elclass.global" value="0" checked="checked">
						一般培训班
						<input type="radio"
							<s:if test="elclass.global==1">checked='checked'</s:if>
							onclick="document.getElementById('group_div').style.display='block'"
							name="elclass.global" value="1">
						全局培训班
						<span id="group_div" style="display: 'none';"> 选择特殊用户组一：<s:select
								list="group1" headerKey="0" headerValue="请选择" listKey="id"
								listValue="name" name="elclass.group1.id"></s:select> 选择特殊用户组二：<s:select
								list="group2" headerKey="0" headerValue="请选择" listKey="id"
								listValue="name" name="elclass.group2.id"></s:select> 发证日期：<input
								type="text"
								value="<s:date format="yyyy-MM-dd HH:mm:ss" name="elclass.diplomatime"/>"
								name="elclass.diplomatime" onclick='setday(this)'> </span>
						<script>document.getElementById('group_div').style.display='block'</script>
					</td>
				</tr>
				<tr>
					<td height="30" align="right" bgcolor="#FFFFFF">
						<span class="neededitem">*</span>培训班类型：
					</td>
					<td height="30">
						<input type="radio" name="elclass.classtype" value="1"
							onClick="disDiv(1);" />
						简易
						<input type="radio" name="elclass.classtype" value="0"
							onClick="disDiv(2);" checked="checked" />
						常规  (<b>简易</b>:培训班中只有必修课程，学员学完必修课即可获取证书)
						<s:if test="elUser.role.id == 1">
							<input type="radio" name="elclass.classtype" value="2"
								onClick="disDiv(3);"/>
							自主培训班
						</s:if>
					</td>
				</tr>
				<tr>
					<td height="30" align="right" bgcolor="#FFFFFF">
						顺序学习：
					</td>
					<td height="30">
						<input type="radio" name="elclass.learnByOrder" value="1"
							 />
						是
						<input type="radio" name="elclass.learnByOrder" value="0"
							 checked="checked" />
						否  (<b>是</b>:设置为‘是’后，培训班中课程必须按照顺序学习!)
					</td>
				</tr>
				<tr>
					<td align="right">
						培训班年份：
					</td>
					<td>
						<label>
							<s:textfield name="elclass.year" id="elclass.year" />
						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="right" bgcolor="#FFFFFF">
						培训班类型：
					</td>
					<td height="30">
						<input type="radio" name="elclass.nazhengornianjian" value="1"
							 />
						年检
						<input type="radio" name="elclass.nazhengornianjian" value="0"
							 checked="checked" />
						拿证  
					</td>
				</tr>
				<tr>
					<td height="30" align="right" bgcolor="#FFFFFF">
						结业条件：
					</td>
					<td height="30">
						<div id="div_1" style="display: block;">
							<label>
								<s:textfield id="optional" name="elclass.optionalcredit"
									value="0" />
								(必修课全部通过，选修课最少获得的学分)
							</label>
						</div>
						<div id="div_2" style="display: none;">
							简易培训班在学员端只显示必修课，不显示选修课,结业条件是必修课全部通过
						</div>
						<div id="div_3" style="display: none;">
							必修要求学分：<s:textfield id="optional" name="elclass.credit_bx" /><br/>
							选修要求学分：<s:textfield id="optional" name="elclass.credit_xx" />
						</div>
					</td>
				</tr>
				<tr>
					<td align="right">
						组织单位：
					</td>
					<td>
						<label>
							<input id="danweiName" name="elclass.depName" readonly="readonly" style="font-size:15px;" value="<s:property value="#session.myDepName" />" />
								<span class="txt-info" style="margin-left:20px;"><a href="#"
									onClick="searchUserInit_();return false;" class="textbg4">选择</a></span>
						</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						组织工种：
					</td>
					<td>
						<label>
							<s:select name="elclass.jingzhong" 
									list="jingzhongs" listKey="basevalue" listValue="basevalue" value="#request.userJingzhong" />
						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="right" bgcolor="#FFFFFF">
						是否可申请：
					</td>
					<td height="30">
						<input type="radio" name="elclass.isApplication" value="0"
							onClick="disDivSQ(1);" checked="checked" />
						不可申请
						<input type="radio" name="elclass.isApplication" value="1"
							onClick="disDivSQ(2);" />
						可申请
						<font style="color: red;">此选项创建后不可修改</font>
						<div id="divSQ_1" style="display: none;">
							<table width="95%" cellpadding="2" cellspacing="1">
								<tr>
									<td colspan="2">
										是否需要审核：
										<input type="radio" value="1" name="elRegistration.isAudit" />
										需要
										<input type="radio" value="0" checked="checked"
											name="elRegistration.isAudit" />
										不需要
									</td>
								</tr>
								<tr>
									<td width="100" align="right" bgcolor="#E6F9F9">
										<span class="neededitem">*</span>计划招收学员：
									</td>
									<td>
										<s:textfield name="elRegistration.PlanRecruitStudents"
											id="PlanRecruitStudents" />
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
										<input name="elRegistration.RegistrationStartTime"
											id="bm_start" type="text" size="20" 
											value="<s:property value='elRegistration.RegistrationStartTime'/>">
											&nbsp;
											<input type="button" class="textbg4"
													onclick="setday(document.getElementById('bm_start'))"
													value="选择" />
										～
										<input name="elRegistration.RegistrationStopTime" id="bm_end"
											type="text" size="20" 
											value="<s:property value='elRegistration.RegistrationStopTime'/>">
										&nbsp;
											<input type="button" class="textbg4"
													onclick="setday(document.getElementById('bm_end'))"
													value="选择" />
									</td>
								</tr>
								<tr>
									<td width="100" align="right" bgcolor="#E6F9F9">
										年龄段：
									</td>
									<td>
										<s:textfield name="elRegistration.StartAge" id="StartAge"
											value="0" />
										～
										<s:textfield name="elRegistration.StopAge" id="StopAge"
											value="0" />
									</td>
								</tr>
								<tr>
									<td width="100" align="right" bgcolor="#E6F9F9">
										性别：
									</td>
									<td>
										<label>
											<input type="radio" name="elRegistration.sex" value="不限"
												checked="checked" />
											不限
											<input type="radio" name="elRegistration.sex" value="男" />
											男
											<input type="radio" name="elRegistration.sex" value="女" />
											女
										</label>
									</td>
								</tr>
								<tr>
									<td width="100" align="right" bgcolor="#E6F9F9">
										部门：
									</td>
									<td>
										<div id="danwei"
											style="background-color: blank; display: none;">
											<br />
										</div>

										<span class="txt-info"><a href="#"
											onClick="searchUsersInit();return false;" class="textbg4">选择</a> </span>
									</td>
								</tr>
								<tr>
									<td width="100" align="right" bgcolor="#E6F9F9">
										<wysLib:BasetName btid="1" />：
									</td>
									<td>
										<div id="jztj" style="background-color: blank; display: none;">
										</div>
										<span class="txt-info"><a href="#"
											onClick="searBaseDatatInit(1);return false;" class="textbg4">选择</a> </span>
									</td>
								</tr>
								<tr>
									<td width="100" align="right" bgcolor="#E6F9F9">
										<wysLib:BasetName btid="5" />：
									</td>
									<td>
										<div id="dstj" style="background-color: blank; display: none;">
										</div>
										<span class="txt-info"><a href="#"
											onClick="searBaseDatatInit(5);return false;" class="textbg4">选择</a> </span>
									</td>
								</tr>
								<tr>
									<td width="100" align="right" bgcolor="#E6F9F9">
										<wysLib:BasetName btid="2" />：
									</td>
									<td>
										<div id="zwtj" style="background-color: blank; display: none;">
											<br />
										</div>
										<span class="txt-info"><a href="#"
											onClick="searBaseDatatInit(2);return false;" class="textbg4">选择</a> </span>
									</td>
								</tr>
								<tr>
									<td width="100" align="right" bgcolor="#E6F9F9">
										<wysLib:BasetName btid="3" />：
									</td>
									<td>
										<div id="zjtj" style="background-color: blank; display: none;">
											<br />
										</div>
										<span class="txt-info"><a href="#"
											onClick="searBaseDatatInit(3);return false;" class="textbg4">选择</a> </span>
									</td>
								</tr>
								<tr>
									<td width="100" align="right" bgcolor="#E6F9F9">
										考场：
									</td>
									<td>
										<div id="KC" style="display: none; width: 100%;">
										</div>
										<span class="txt-info"><a href="#"
											onClick="searchExamRoomUser();return false;" class="textbg4">选择</a> </span>
									</td>
								</tr>
								<tr>
									<td width="100" align="right" bgcolor="#E6F9F9">
										考场试卷：
									</td>
									<td> 
										<div id="KCEP">
										</div>
										<span class="txt-info"><a href="#"
											onClick="searchExamRoomEpUser();return false;" class="textbg4">选择</a> </span>
									</td>
								</tr>
								<tr>
									<td width="100" align="right" bgcolor="#E6F9F9">
										培训班：
									</td>
									<td>
										<div id="PXB" style="display: none; width: 100%;">
										</div>
										<span class="txt-info"><a href="#"
											onClick="searchElclassUser();return false;" class="textbg4">选择</a> </span>
									</td>
								</tr>
							</table>
						</div>
						<div id="divSQ_2" style="display: block;">
						</div>
					</td>
				</tr>
				<%--tr>
					<td height="30" align="center" >
						培训班状态：
					</td>
					<td height="30" >
						<label>
							开通
						</label>
						<input type="radio" name="elclass.status" value="1">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<label>
							关闭
						</label>
						<input type="radio" name="elclass.status" value="0" checked>
						
					</td>
				</tr--%>
				<s:if test="elclass.id != null">
					<tr>
						<td height="30" align="center" bgcolor="#FFFFFF">
							培训班状态：
						</td>
						<td height="30">
							<s:property value="elclass.statusName" />
							<input type="hidden" name="elclass.status"
								value="<s:property value="elclass.status"/>">
							<!--<s:if test="elclass.status==3">
							<label>
								已创建
							</label>
						</s:if>
						<s:elseif test="elclass.status==2">
							<label>
								审核中
							</label>
						</s:elseif>
						<s:elseif test="elclass.status==4">
							<label>
								未通过
							</label>
						</s:elseif>
						<s:elseif test="elclass.status==1">
							<label>
								开通
							</label>
						</s:elseif>
						<s:elseif test="elclass.status==0">
							<label>
								关闭
							</label>
						</s:elseif>-->

						</td>
					</tr>
					<tr>
						<td height="30" align="center" bgcolor="#FFFFFF">
							编辑：
						</td>
						<td height="30">
							<a
								href="elclass_alterInit.action?elclass.id=<s:property value="elclass.id" />"
								class="textbg4">编辑</a>
							<s:if test="elclass.status==4">
								<a
									href="elclass_sh.action?elclass.id=<s:property value="elclass.id" />&status=3"
									class="textbg6">继续终审</a>
							</s:if>
						</td>
					</tr>
				</s:if>
				<s:else>
					<input type="hidden" name="elclass.status" value="0">
					<tr>
						<td height="50" align="center" bgcolor="#FFFFFF">
							&nbsp;
							<s:hidden name="elclassId"></s:hidden>
						</td>
						<td height="30">
							<input style="height: 30px;" class="textbg6" type="button"
								value="确认添加" onClick="doSubmit('depl');" />
							<input style="height: 30px;"
								onclick="document.location.href='elclass_alllist.action'"
								class="textbg6" type="button" title="返回培训班列表" value="取 消"
								onClick="doSubmit('depl');" />
						</td>
					</tr>
				</s:else>
				<!-- <tr>
					<td colspan="2">
						<iframe id="bixiuFrame"
							src="elclass_course_bx.action?elclassId=${elclass.id}"
							width="100%" marginwidth="0" marginheight="0" frameborder="0"
							onload="this.height=bixiuFrame.document.body.scrollHeight + 20"></iframe>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<iframe id="zhuxiuFrame" src="elclass_course_zx.action?elclassId=${elclass.id}" width=100% height=280 
								marginwidth="0" marginheight="0" frameborder=0 onload="this.height=zhuxiuFrame.document.body.scrollHeight + 20"></iframe>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<iframe id="xuanxiuFrame"
							src="elclass_course_xx.action?elclassId=${elclass.id}"
							width="100%" height="280" marginwidth="0" marginheight="0"
							frameborder="0"
							onload="this.height=xuanxiuFrame.document.body.scrollHeight + 20"></iframe>
					</td>
				</tr> -->
			</table>
			<s:hidden name="elRegistration.jingzhong" id="jz"></s:hidden>
			<s:hidden name="elRegistration.dishi" id="ds"></s:hidden>
			<s:hidden name="elRegistration.zhiwu" id="zw"></s:hidden>
			<s:hidden name="elRegistration.zhiji" id="zj"></s:hidden>
			<s:hidden name="elRegistration.gangwei" id="gw"></s:hidden>
			<s:hidden name="elRegistration.treeType" id="bm"></s:hidden>
			<s:hidden name="staid" id="staid" value="%{staid}"></s:hidden>
		</s:form>
		<script type="text/javascript"> 
			var bmarr = new Array();  
			var bmidArr = new Array();
			var jzArr = new Array();
			var jzidArr = new Array();
			var zwArr = new Array();
			var zwidArr = new Array();
			var zjArr = new Array();
			var zjidArr = new Array();
			var gwArr = new Array();
			var gwidArr = new Array();
			var dsArr = new Array();
			var dsidArr = new Array();
			var typeid;
			var isApplication;
			//**// 
			function doSubmit(treeType){
				var str=$("#r_start").val();
				var str2=$("#r_end").val();
				var date=new Date(Date.parse(str.replace(/-/g,"/")));
				var date2=new Date(Date.parse(str2.replace(/-/g,"/")));
				if(date>date2){
					alert("您设置的开始时间大于结束时间，请重新设置!");
					$("#r_start").focus();
					return false;
				} 
				
				var str3=$("#bm_start").val();
				var str4=$("#bm_end").val();
				var date3=new Date(Date.parse(str3.replace(/-/g,"/")));
				var date4=new Date(Date.parse(str4.replace(/-/g,"/")));
				
				if(isApplication==2){
				if($("#bm_start").val()==''){
					alert("报名时间段的开始时间不能为空");
					$("#bm_start").focus();
					return false; 
				}if($("#bm_end").val()==''){
					alert("报名时间段的结束时间不能为空");
					$("#bm_end").focus();
					return false; 
				}
				if(date3>date4){
					alert("您设置的报名开始时间大于结束时间，请重新设置!");
					$("#bm_start").focus();
					return false;
				} 
				}
				
				
				
				var BMStart = document.getElementById("bm_start").value; 
				var BMStop  = document.getElementById("bm_end").value; 
				
				
				if(BMStart != '' && BMStop != ''){ 
					var checkbmstart = checkDateTime(BMStart,"报名时间段的开始时间");
					var chenkbmend = checkDateTime(BMStop,"报名时间段的结束时间");
					if(!checkbmstart){
					//	alert("报名时间段的开始时间格式错误，请重新输入，格式为:yyyy-MM-dd HH:mm:ss");
						return false;
					}
					if(!chenkbmend){
					//	alert("报名时间段的结束时间格式错误，请重新输入，格式为:yyyy-MM-dd HH:mm:ss");
						return false;
					}
				}
				
				var titleObj=document.getElementById("name");
				var title=titleObj.value.replace(/(\s*$)/g, "");
				if(title==""){
					alert("培训班名称不能为空!");
					titleObj.focus();
					return false;
				}
				var qlibId=$("input[name='elclass.cltype.id']:checked").val();
				if(qlibId==undefined){
					alert("请选择培训班类别！");
					return false;
				}
				var check = checkTimi();
				//1.获取所有被选中的节点 
				var allChk=document.getElementsByName("chkName");
				var title="";//存放了node.id和node.pid
				var oid_qid="";
				var oid_oid="";
				var bool=false;
				//var vv=0;
				for(var i=0;i<allChk.length;i++){
					if(allChk[i].checked==true){
						title=allChk[i].title;
						oid_qid=title.substr(title.lastIndexOf("_")+1,title.length);
						for(j=0;j<allChk.length;j++){
							//vv++;
							oid_oid=allChk[j].value;
							if(oid_oid&&document.getElementById(""+treeType+""+oid_qid)==null){
								//alert(allChk[i].value+"可以上传");
								allChk[i].name="chkNames";
								bool=true;
								break;
							}
							if(oid_qid==oid_oid&&document.getElementById(""+treeType+""+oid_qid).checked){//&&document.getElementById(oid_qid).checked==true
								bool=true;
							}
						}
						if(j==allChk.length&&bool==false){//没有子节点
							//alert(allChk[i].value+"可以上传");
							allChk[i].name="chkNames";
						}
						bool=false;
					}
				} 
				document.getElementById("jz").value=jzidArr.join(",");
				document.getElementById("gw").value=gwidArr.join(",");
				document.getElementById("ds").value=dsidArr.join(",");
				document.getElementById("zw").value=zwidArr.join(",");
				document.getElementById("zj").value=zjidArr.join(",");
				document.getElementById("bm").value=bmidArr.join(","); 
				if(check){ 
					class_info.submit();
					window.returnValue = "nihao";
					window.close(); 
				} 
				//setTimeout(window.close(),2000);
			}
			function doSubmit2(treeType){
				document.class_info.submit(); 
				window.returnValue = "nihao";
				window.close();
			}
			function checkTimi(){   
				var KBStart = document.getElementById("r_start").value; 
				var KBStop  = document.getElementById("r_end").value; 
				if(KBStart != '' && KBStop != ''){ 
					var checkstart = checkDateTime(KBStart,"开始时间");
					var chenkend = checkDateTime(KBStop,"结束时间");
					if(!checkstart){
					//	alert("开始时间格式错误，请重新输入，格式为:yyyy-MM-dd HH:mm:ss");
						return false;
					}
					if(!chenkend){
					//	alert("结束时间格式错误，请重新输入，格式为:yyyy-MM-dd HH:mm:ss");
						return false;
					}
					var kb = duibi(KBStart,KBStop); 
					if(kb){ 
					}else{
						alert("开始时间不能大于结束时间");
						return false;					
					}
				}else{
					alert("开始与结束时间都不能为空");
					return false;
				}
				var isApp = document.getElementById("divSQ_1").style.display;
				if(isApp != 'none'){ 
					//var plan = document.getElementById("PlanRecruitStudents").value;  
					if($("#PlanRecruitStudents").val() == "" ){
							alert("计划招收人数不能为空");
							$("#PlanRecruitStudents").focus();
							return false;
						}
					if($("#PlanRecruitStudents").val() == 0 ){
							alert("计划招收人数不能为零");
							$("#PlanRecruitStudents").focus();
							return false;
						}
					if (!checkNumber($("#PlanRecruitStudents").val())){
							alert("计划招收人数只能为数字");
							$("#PlanRecruitStudents").focus();
							return false;  
						}
					if (!checkNumber($("#StartAge").val()) || !checkNumber($("#StopAge").val())){
							alert("年龄段只能为数字");
							$("#StartAge").focus();
							return false;  
						}
					if($("#StartAge").val() == "" ||  $("#StopAge").val() == ""){
							alert("年龄段开始不能为空");
							$("#StartAge").focus();
							return false;  
						}
						if ($("#StartAge").val() >=  $("#StopAge").val() && !($("#StartAge").val() == 0 && $("#StopAge").val() == 0)){
							alert("年龄段开始不能大于等年龄段结束");
							$("#StartAge").focus();
							return false;  
						}
						if($("#r_start").val() == "" || $("#r_stop").val() == "" ){
							alert("报名时间段不能为空");
							$("#r_start").focus();
							return false; 
						}
					var BMStart = document.getElementById("bm_start").value; 
					var BMStop = document.getElementById("bm_end").value;
					if(BMStart != '' && BMStop != ''){ 
						var kb = duibi(BMStart,BMStop); 
						if(kb){ 
						}else{
							alert("报名开始时间不能大于结束时间");
							return false;					
						}
					}else{
						alert("报名时间不能为空");
						return false;
					}   
					var bmkb = duibi(BMStop,KBStart);  
					if(bmkb){ 
					}else{
						//alert("报名结束时间不能大于开始时间");
						//return false;
					}
				} 
				return true;
				
			}
			
			function duibi(a,b)
			{
				var arr=a.split("-");
				var starttime=new Date(arr[0],arr[1],arr[2]);
				var starttimes=starttime.getTime();
				
				var arrs=b.split("-");
				var lktime=new Date(arrs[0],arrs[1],arrs[2]);
				var lktimes=lktime.getTime();
				
				if(starttimes>=lktimes)
				{  
					return false;
				}
				else
					return true; 
			} 
		</script>
	
	</body>
</HTML>
