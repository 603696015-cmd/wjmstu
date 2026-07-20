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
		<TITLE>网络考试系统--管理端--</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
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
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/libutil.js"></script>
		<script type="text/javascript" src="js/elclassop.js"></script>
		<script type="text/javascript" src="js/inputcheck.js"></script>
		<script type="text/javascript" src="js/basedataop.js"></script>
		<script type="text/javascript">
		var isApplication;
		function disDiv(op){
			var div1=document.getElementById("div_1");
			var div2=document.getElementById("div_2");
			if(op==1){
				div1.style.display="none";
				div2.style.display="block";
				document.getElementById("optional").value="";
			}else{
				div1.style.display="block";
				div2.style.display="none";
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
		function init(classType){
			var div1=document.getElementById("div_1");
			var div2=document.getElementById("div_2");
			if(classType==0){
				div1.style.display="block";
				div2.style.display="none";
			}else if(classType==1){
				div1.style.display="none";
				div2.style.display="block";
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
	<body
		onLoad="init('<s:property value="elclass.classtype"/>');loadData('<s:property value="elclass.elRegistration.examRoomIds" />','<s:property value="elclass.elRegistration.elclassIds" />');">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="培训班修改" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<s:form action="elclass_alter" method="post" name="class_info"
			theme="simple">
			<s:hidden name="elclass.status" />
			<table width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<td width="160" height="30" align="right" bgcolor="#FFFFFF">
						<span class="neededitem">*</span>培训班名称：
					</td>
					<td height="30">
						<label>
							<s:textfield name="elclass.name" id="name" size="60" />
						</label>
					</td>
				</tr>

				<tr>
					<td height="30" align="right" bgcolor="#FFFFFF" style="">
						培训班介绍：
					</td>
					<td height="30">
						<label>
							<s:textarea name="elclass.description" cols="60" rows="7" />
						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="right" bgcolor="#FFFFFF">
						课程图片：
					</td>
					<td>
						<label>
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
						<label>
							<s:textfield name="elclass.certificatename" id="certificate_name" />
						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="right" bgcolor="#FFFFFF">
						<span class="neededitem">*</span>所属类别：
					</td>
					<td height="30">
						<label>
							<s:if test="elclass.cltype.id != null">
								<wysLib:clTypeTree iname="elclass.cltype.id" itype="ra"
									ivalue="<%=cltypeId%>" href="" rootAble="true" />
								<!-- ivalue="${elclass.cltype.id}" -->
							</s:if>
							<s:else>
								<wysLib:clTypeTree iname="elclass.cltype.id" itype="ra_2no" />
							</s:else>
						</label>
					</td>
				</tr>
				<%-- 
				<tr style="display: none">
					<td height="30" align="right" bgcolor="#FFFFFF" >
						培训班类型：					</td>
					<td height="30" >
						<input type="radio"
							<s:if test="elclass.global==0">checked='checked'</s:if>
							onclick="document.getElementById('group_div').style.display='none'"
							name="elclass.global" value="0" checked="checked">
						一般培训班
						<s:if test="#session.roleid==1">
							<input type="radio"
								<s:if test="elclass.global==1">checked='checked'</s:if>
								onclick="document.getElementById('group_div').style.display='block'"
								name="elclass.global" value="1">全局培训班
						<span id="group_div" style="display: 'none';">选择特殊用户组一：<s:select
									list="group1" headerKey="0" headerValue="请选择" listKey="id"
									listValue="name" name="elclass.group1.id"></s:select>选择特殊用户组二：<s:select
									headerKey="0" headerValue="请选择" list="group2" listKey="id"
									listValue="name" name="elclass.group2.id"></s:select> 发证日期：<input
									type="text"
									value="<s:date format="yyyy-MM-dd HH:mm:ss" name="elclass.diplomatime"/>"
									name="elclass.diplomatime" onclick='setday(this)'> </span>
						</s:if>
						<s:if test="elclass.global==1">
							<script>document.getElementById('group_div').style.display='block'</script>
						</s:if>
					</td>
				</tr>
				 --%>
				<tr>
					<td height="30" align="right" bgcolor="#FFFFFF">
						<span class="neededitem">*</span>开始时间：
					</td>
					<td height="30">
						<input name="elclass.starttime" id="r_start" type="text" size="20"
							value="<s:date name="elclass.starttime" format="yyyy-MM-dd HH:mm:ss"/>">
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
						<input name="elclass.finishtime" id="r_end" type="text" size="20"
							value="<s:date name="elclass.finishtime" format="yyyy-MM-dd HH:mm:ss"/>">
							&nbsp;
							<input type="button" class="textbg4"
									onclick="setday(document.getElementById('r_end'))"
									value="选择" />
					</td>
				</tr>
				<tr>
					<td height="30" align="right" bgcolor="#FFFFFF">
						<span class="neededitem">*</span>培训班类型：
					</td>
					<td height="30">
						<input type="radio" name="elclass.classtype" value="1"
							onclick="disDiv(1);"
							<s:if test="elclass.classtype==1">
								 checked="checked"
							</s:if> />
						简易
						<input type="radio" name="elclass.classtype" value="0"
							onclick="disDiv(2);"
							<s:if test="elclass.classtype==0">
								 checked="checked"
							</s:if> />
						常规
						<s:if test="elUser.role.id == 1">
							<input type="radio" name="elclass.classtype" value="2"
								onclick="disDiv(3);"
								<s:if test="elclass.classtype==2">
									 checked="checked"
								</s:if> />
							</s:if>
						自主培训班(
						<b>简易</b>:培训班中只有必修课程，学员学完必修课即可获取证书)
					</td>
				</tr>
				<tr>
					<td height="30" align="right" bgcolor="#FFFFFF">
						顺序学习：
					</td>
					<td height="30">
						<input type="radio" name="elclass.learnByOrder" value="1"
							<s:if test="elclass.learnByOrder==1">
								 checked="checked"
							</s:if> /> 
						是
						<input type="radio" name="elclass.learnByOrder" value="0"
							 <s:if test="elclass.learnByOrder==0">
								 checked="checked"
							</s:if> />
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
							<s:if test="elclass.nazhengornianjian==1">
								 checked="checked"
							</s:if> />
						年检
						<input type="radio" name="elclass.nazhengornianjian" value="0"
							 <s:if test="elclass.nazhengornianjian==0">
								 checked="checked"
							</s:if> />
						拿证  
					</td>
				</tr>
				<tr>
					<td height="30" align="right" bgcolor="#FFFFFF">
						<span class="neededitem">*</span>结业条件：
					</td>
					<td height="30">
						<div id="div_1">
							<label>
								<s:textfield id="optional" name="elclass.optionalcredit" />
								(必修课全部通过，选修课最少获得的学分)
							</label>
						</div>
						<div id="div_2">
							简易培训班在学员端只显示必修课，不显示选修课,结业条件是必修课全部通过
						</div>
						<div id="div_3">
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
							<input id="danweiName" name="elclass.depName" readonly="readonly"
								style="font-size: 15px;"
								value="<s:property value="elclass.depName" />" />
							<span class="txt-info" style="margin-left: 20px;"><a
								href="#" onClick="searchUserInit_();return false;"
								class="textbg4">选择</a>
							</span>
						</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						组织警种：
					</td>
					<td>
						<label>
							<s:select name="elclass.jingzhong" list="jingzhongs"
								listKey="basevalue" listValue="basevalue"
								value="elclass.jingzhong" />
						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="right" bgcolor="#FFFFFF">
						<span class="neededitem">*</span>是否可申请：
					</td>
					<td height="30">
						<!-- 
						<input type="radio" name="elclass.isApplication" value="0"
							onClick="disDivSQ(1);"
							<s:if test="elclass.isApplication==0">
								 checked="checked"
							</s:if> />
						不可申请
						<input type="radio" name="elclass.isApplication" value="1"
							onClick="disDivSQ(2);"
							<s:if test="elclass.isApplication==1">
								 checked="checked"
							</s:if> />
						可申请
					 -->
					  	<s:if test="#session.roleid==1">
					  		
								<input type="radio" name="elclass.isApplication" value="0" 
									onClick="disDivSQ(1);" <s:if test="elclass.isApplication==0">
								 checked="checked"</s:if>
								/>
								不可申请
								<input type="radio" name="elclass.isApplication" value="1" 
									onClick="disDivSQ(2);" <s:if test="elclass.isApplication==1">
								 checked="checked"</s:if>
								 />
								可申请
						</s:if>
						<s:else>
							<input type="hidden" name="elclass.isApplication"
							value="<s:property value="elclass.isApplication"/>" />
							<s:if test="elclass.isApplication==0">
						 	不可申请
					  		</s:if>
							<s:elseif test="elclass.isApplication==1">
						 	可申请
					  		</s:elseif>
					  	</s:else>
						
						<div id="divSQ_1"
							<s:if test="elclass.isApplication==0">style="display:none;"</s:if>>
							<table width="100%" cellpadding="2" cellspacing="1">
								<tr>
									<!-- <td width="100" height="20" align="right" bgcolor="#E6F9F9">
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
								</tr> -->
								<tr>
									<td colspan="2">
										是否需要审核：
										<input type="radio" value="1"
											<s:if test="elRegistration.isAudit==1">checked="checked"</s:if>
											name="elRegistration.isAudit" />
										需要
										<input type="radio" value="0"
											<s:if test="elRegistration.isAudit==0">checked="checked"</s:if>
											name="elRegistration.isAudit" />
										不需要
									</td>
								</tr>
								<tr>
									<td width="100" align="right" bgcolor="#FFFFFF">
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
									<td width="100" align="right" bgcolor="#FFFFFF">
										<span class="neededitem">*</span>报名时间段：
									</td>
									<td>
										<input name="elRegistration.RegistrationStartTime"
											id="bm_start" type="text" size="20" 
											value="<s:date name="elRegistration.RegistrationStartTime" format="yyyy-MM-dd HH:mm:ss"/>">
											&nbsp;
											<input type="button" class="textbg4"
													onclick="setday(document.getElementById('bm_start'))"
													value="选择" />
										～
										<input name="elRegistration.RegistrationStopTime" id="bm_end"
											type="text" size="20" 
											value="<s:date name="elRegistration.RegistrationStopTime" format="yyyy-MM-dd HH:mm:ss"/>">
											&nbsp;
											<input type="button" class="textbg4"
													onclick="setday(document.getElementById('bm_end'))"
													value="选择" />
									</td>
								</tr>
								<tr>
									<td width="100" align="right" bgcolor="#FFFFFF">
										年龄段：
									</td>
									<td>
										<s:textfield name="elRegistration.StartAge" id="StartAge" />
										～
										<s:textfield name="elRegistration.StopAge" id="StopAge" />
									</td>
								</tr>
								<tr>
									<td width="100" align="right" bgcolor="#FFFFFF">
										性别：
									</td>
									<td>
										<label>
											<input type="radio" name="elRegistration.sex" checked="checked" value="不限"
												checked="checked" />
											不限
											<input type="radio" name="elRegistration.sex"
											 <s:if test="elRegistration.sex==\"男\"">checked="checked"</s:if> value="男" />
											男
											<input type="radio" name="elRegistration.sex"
											 <s:if test="elRegistration.sex==\"女\"">checked="checked"</s:if>  value="女" />
											女
										</label>
									</td>
								</tr>
								<tr>
									<td width="100" align="right" bgcolor="#FFFFFF">
										部门：
									</td>
									<td>
										<div id="danwei"
											<s:if test="elRegistration.treeType == null ">style="background-color:block;display: none;"</s:if>>
											<s:property value="elRegistration.treeTypeName" />
										</div>
										<span class="txt-info"><a href="#"
											onClick="searchUsersInit();return false;" class="textbg4">选择</a> </span>
									</td>
								</tr>
								<tr>
									<td width="100" align="right" bgcolor="#FFFFFF">
										<wysLib:BasetName btid="1" />：
									</td>
									<td>
										<div id="jztj"
											<s:if test="elRegistration.jingzhong == null ">style="background-color:block;display: none;"</s:if>>
											<s:property value="elRegistration.jingzhongName" />
										</div>
										<span class="txt-info"><a href="#"
											onClick="searBaseDatatInit(1);return false;" class="textbg4">选择</a> </span>
									</td>
								</tr>
								<tr>
									<td width="100" align="right" bgcolor="#FFFFFF">
										<wysLib:BasetName btid="5" />：
									</td>
									<td>
										<div id="dstj"
											<s:if test="elRegistration.dishi == null ">style="background-color:block;display: none;"</s:if>>
											<s:property value="elRegistration.dishiName" />
										</div>
										<span class="txt-info"><a href="#"
											onClick="searBaseDatatInit(5);return false;" class="textbg4">选择</a> </span>
									</td>
								</tr>
								<tr>
									<td width="100" align="right" bgcolor="#FFFFFF">
										<wysLib:BasetName btid="2" />：
									</td>
									<td>
										<div id="zwtj"
											<s:if test="elRegistration.zhiwu == null ">style="background-color:block;display: none;"</s:if>>
											<s:property value="elRegistration.zhiwuName" />
										</div>
										<span class="txt-info"><a href="#"
											onClick="searBaseDatatInit(2);return false;" class="textbg4">选择</a> </span>
									</td>
								</tr>
								<tr>
									<td width="100" align="right" bgcolor="#FFFFFF">
										<wysLib:BasetName btid="3" />：
									</td>
									<td>
										<div id="zjtj"
											<s:if test="elRegistration.zhiji == null ">style="background-color:block;display: none;"</s:if>>
											<s:property value="elRegistration.zhijiName" />
										</div>
										<span class="txt-info"><a href="#"
											onClick="searBaseDatatInit(3);return false;" class="textbg4">选择</a> </span>
									</td>
								</tr>
								<%-- 
								<tr>
									<td width="100" align="right" bgcolor="#E6F9F9">
										<wysLib:BasetName btid="4" />:
									</td>
									<td>
										<div id="gwtj"
											<s:if test="elRegistration.gangweiName == null ">style="background-color:block;display: none;"</s:if>>
											<s:property value="elRegistration.gangweiName" />
										</div>
										<span class="txt-info"><a href="#"
											onClick="searBaseDatatInit(4);return false;">点此进行选择</a>
										</span>
									</td>
								</tr>
								 --%>
								<tr>
									<td width="100" align="right" bgcolor="#FFFFFF">
										考场：
									</td>
									<td>
										<div id="KC">
										<SCRIPT type="text/javascript">eridx="<s:property value="elRegistration.erParas.size"/>"==""?0:parseInt('<s:property value="elRegistration.erParas.size"/>');
										</SCRIPT>
										<s:iterator value="elRegistration.erParas" status="erparas_st">
												<div id="_kc_u<s:property value="examRoom.id"/>" style="width: 100%; height: 14px;border: 1px olid;float: left;background:#edefff">
												<input name="elRegistration.erParas[<s:property value="#erparas_st.index"/>].examRoom.id" value="<s:property value="examRoom.id"/>" type="hidden">
												<label style="text-align: left; width: 260px; float: left">名称：<s:property value="examRoom.title"/></label>
												<label style="text-align: left; float: left">是否通过：
												<select name="elRegistration.erParas[<s:property value="#erparas_st.index"/>].isPassed">
												<option <s:if test="isPassed==-1">selected='selected'</s:if> value="-1">全部</option>
												<option <s:if test="isPassed==1">selected='selected'</s:if>  value=1>是</option>
												<option <s:if test="isPassed==2">selected='selected'</s:if> value="2">否</option></select>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												考场成绩：<select name="elRegistration.erParas[<s:property value="#erparas_st.index"/>].examScoreTerm">
													<option <s:if test="examScoreTerm==\">=\"">selected='selected'</s:if>  value=">=">&gt;=</option>
													<option <s:if test="examScoreTerm==\">\"">selected='selected'</s:if> value=">">&gt;</option>
													<option <s:if test="examScoreTerm=='='">selected='selected'</s:if> value="=">=</option>
													<option <s:if test="examScoreTerm==\"<\"">selected='selected'</s:if> value="&lt;">&lt;</option>
													<option <s:if test="examScoreTerm==\"<=\"">selected='selected'</s:if> value=">=">&lt;=</option>
												</select>
												<INPUT style="WIDTH: 30px" name=elRegistration.erParas[<s:property value="#erparas_st.index"/>].examScore maxLength=4 value="<s:property value="examScore"/>">
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<select name="elRegistration.erParas[<s:property value="#erparas_st.index"/>].linkTerm">
												<option  <s:if test="linkTerm=='or'">selected='selected'</s:if> value=or>or</option>
												<option  <s:if test="linkTerm=='and'">selected='selected'</s:if> value=and>and</option></select>
												</label><A style="WIDTH: 14px; FLOAT: right; HEIGHT: 14px; CURSOR: hand" onclick="javascript:deleteExamRoomUserinfo(this,<s:property value="examRoom.id"/>);return false;" href="javascript:void(0)">X</A>
												</div>
											</s:iterator>
											</div>
										<%-- 
											<s:if test="elclass.elRegistration.examroomName == null ">style="display: none;width: 100%;"</s:if>
										<div id="KC_" <s:if test="elclass.elRegistration.examRoom.size == 0">style="display: none;width: 100%;"</s:if>> 
											<s:property value="elclass.elRegistration.examroomName" />  
											<a  href="" style="color:red"  <s:if test="elclass.elRegistration.examRoom.size == 0">style="display: none;"</s:if>
												onclick="javascript:deleteNames(1);return false;">X</a>  
										</div>
										<s:hidden name="elclass.elRegistration.examroomName"></s:hidden>
										<SELECT name="elRegistration.eroomScreeningWay">
											<option value="0"
												<s:if test="elclass.elRegistration.eroomScreeningWay == 0 ">selected="selected"</s:if>>
												全部
											</option>
											<option value="1"
												<s:if test="elclass.elRegistration.eroomScreeningWay == 1 ">selected="selected"</s:if>>
												通过
											</option>
											<option value="2"
												<s:if test="elclass.elRegistration.eroomScreeningWay == 2 ">selected="selected"</s:if>>
												不通过
											</option>
										</SELECT> --%>
										<span class="txt-info"><a href="#"
											onClick="searchExamRoomUser();return false;" class="textbg4">选择</a>
										</span>
									</td>
								</tr>
								<tr>
									<td width="100" align="right" bgcolor="#E6F9F9">
										考场试卷：
									</td>
									<td> 
										<div id="KCEP"><!--  <s:if test="elRegistration.examroomName == null ">style="display: block;width: 100%;"</s:if> -->
											<SCRIPT type="text/javascript">epindex_="<s:property value="elRegistration.erepParas.size"/>"==""?0:parseInt('<s:property value="elRegistration.erepParas.size"/>');</SCRIPT>
											<s:iterator value="elRegistration.erepParas" status="erepparas_st">
														<div id="_kcep_u_<s:property value="examPaper.id"/>_<s:property value="examRoom.id"/>" style="width: 100%; height: 14px; border: solid #fff 1px; background: #edefff">
<input name="elRegistration.erepParas[<s:property value="#erepparas_st.index" />].examRoom.id" value="<s:property value="examRoom.id"/>" type="hidden"><input name="elRegistration.erepParas[<s:property value="#erepparas_st.index" />].examPaper.id" value="<s:property value="examPaper.id"/>" type="hidden">
<label style="TEXT-ALIGN: left; WIDTH: 150px; FLOAT: left">考场名称：<span style="font-weight: 600"><s:property value="examRoom.title"/></span></label>
<label style="TEXT-ALIGN: left; WIDTH: 150px; FLOAT: left">试卷名称：<span style="font-weight: 600"><s:property value="examPaper.title"/></span></label>
<label style="TEXT-ALIGN: left; FLOAT: left; MARGIN-LEFT: 30px">是否通过：<select name="elRegistration.erepParas[<s:property value="#erepparas_st.index" />].isPassed">
<option <s:if test="isPassed==-1">selected='selected'</s:if> value="-1">全部</option>
<option <s:if test="isPassed==1">selected='selected'</s:if> value="1">是</option>
<option <s:if test="isPassed==2">selected='selected'</s:if> value="2">否</option>
</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;考试次数：<select name="elRegistration.erepParas[<s:property value="#erepparas_st.index" />].examCountTerm">
<option <s:if test="examCountTerm==\">=\"">selected='selected'</s:if> value=">=">&gt;=</option>
<option <s:if test="examCountTerm==\">\"">selected='selected'</s:if> value=">">&gt;</option>
<option <s:if test="examCountTerm==\"=\"">selected='selected'</s:if> value="=">=</option>
<option <s:if test="examCountTerm==\"<\"">selected='selected'</s:if> value="&lt;">&lt;</option>
<option <s:if test="examCountTerm==\"<=\"">selected='selected'</s:if> value="<=">&lt;=</option>
</select><input style="WIDTH: 30px" name="elRegistration.erepParas[<s:property value="#erepparas_st.index" />].examCount" maxLength="4" value="<s:property value="examCount"/>">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<BR>平均分：<select name="elRegistration.erepParas[<s:property value="#erepparas_st.index" />].avgScoreTerm">
<option <s:if test="avgScoreTerm==\">=\"">selected='selected'</s:if> value=">=">&gt;=</option>
<option <s:if test="avgScoreTerm==\">\"">selected='selected'</s:if> value=">">&gt;</option>
<option <s:if test="avgScoreTerm==\"=\"">selected='selected'</s:if> value="=">=</option>
<option <s:if test="avgScoreTerm==\"<\"">selected='selected'</s:if> value="&lt;">&lt;</option>
<option <s:if test="avgScoreTerm==\"<=\"">selected='selected'</s:if> value="&lt;=">&lt;=</option>
</select>
<input style="WIDTH: 30px" name="elRegistration.erepParas[<s:property value="#erepparas_st.index" />].avgScore" maxLength="4" value="<s:property value="avgScore"/>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
最高分：<select name="elRegistration.erepParas[<s:property value="#erepparas_st.index" />].maxScoreTerm">
<option <s:if test="maxScoreTerm==\">=\"">selected='selected'</s:if> value=">=">&gt;=</option>
<option <s:if test="maxScoreTerm==\">\"">selected='selected'</s:if> value=">">&gt;</option>
<option <s:if test="maxScoreTerm==\"=\"">selected='selected'</s:if> value="=">=</option>
<option <s:if test="maxScoreTerm==\"<\"">selected='selected'</s:if> value="&lt;">&lt;</option>
<option <s:if test="maxScoreTerm==\"<=\"">selected='selected'</s:if> value="&lt;=">&lt;=</option></select>
<input style="WIDTH: 30px" name="elRegistration.erepParas[<s:property value="#erepparas_st.index" />].maxScore" maxLength="4" value="<s:property value="maxScore"/>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<select name="elRegistration.erepParas[<s:property value="#erepparas_st.index" />].linkTerm">
	<option <s:if test="linkTerm==\"or\"">selected='selected'</s:if> value="or">or</option>
	<option <s:if test="linkTerm==\"and\"">selected='selected'</s:if> value="and">and</option>
</select></label>
<A style="WIDTH: 14px; FLOAT: right; HEIGHT: 14px; CURSOR: hand" onclick="javascript:deleteExamRoomEpUserinfo(this);return false;" href="javascript:void(0)">X</A>
</div>
											</s:iterator>
										</div>
										<span class="txt-info"><a href="#"
											onClick="searchExamRoomEpUser();return false;" class="textbg4">选择</a> </span>
									</td>
								</tr>
								<tr>
									<td width="100" align="right" bgcolor="#FFFFFF">
										培训班：
									</td>
									<td>
										<div id="PXB">
										<SCRIPT type="text/javascript">clindex_="<s:property value="elRegistration.classParas.size"/>"==""?0:parseInt('<s:property value="elRegistration.classParas.size"/>');</SCRIPT>
											<s:iterator value="elRegistration.classParas" status="classparas_st">
											<div id="_pxb_u<s:property value="elClass.id"/>" style="width: 100%; height: 14px;background: #edefff;border:solid #fff 1px;float:left">
											<INPUT name="elRegistration.classParas[<s:property value="#classparas_st.index" />].elClass.id" value=<s:property value="elClass.id"/> type=hidden>
											<LABEL style="TEXT-ALIGN: left; WIDTH: 260px; FLOAT: left">名称：<s:property value="elClass.name"/></LABEL>
											<LABEL style="TEXT-ALIGN: left; FLOAT: left">是否通过：<SELECT name="elRegistration.classParas[<s:property value="#classparas_st.index" />].isPassed">
											<OPTION <s:if test="isPassed==-1">selected='selected'</s:if> value=-1>全部</OPTION>
											<OPTION <s:if test="isPassed==1">selected='selected'</s:if> value=1>是</OPTION>
											<OPTION <s:if test="isPassed==2">selected='selected'</s:if> value=2>否</OPTION>
											</SELECT>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总学分：<INPUT style="WIDTH: 30px" name="elRegistration.classParas[<s:property value="#classparas_st.index" />].sumScoreStart" maxLength="4" value="<s:property value="sumScoreStart"/>">&nbsp;&nbsp;~&nbsp;&nbsp;<INPUT style="WIDTH: 30px" name="elRegistration.classParas[<s:property value="#classparas_st.index" />].sumScoreEnd" maxLength=4 value="<s:property value="sumScoreEnd"/>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<BR/>必修总学分：<INPUT style="WIDTH: 30px" name="elRegistration.classParas[<s:property value="#classparas_st.index" />].bsumScoreStart" maxLength="4" value="<s:property value="bsumScoreStart"/>">&nbsp;&nbsp;~&nbsp;&nbsp;<INPUT style="WIDTH: 30px" name="elRegistration.classParas[<s:property value="#classparas_st.index" />].bsumScoreEnd" maxLength=4 value="<s:property value="bsumScoreEnd"/>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											选修总学分：<INPUT style="WIDTH: 30px" name="elRegistration.classParas[<s:property value="#classparas_st.index" />].xsumScoreStart" maxLength="4" value="<s:property value="xsumScoreStart"/>">&nbsp;&nbsp;~&nbsp;&nbsp;<INPUT style="WIDTH: 30px" name="elRegistration.classParas[<s:property value="#classparas_st.index" />].xsumScoreEnd" maxLength=4 value="<s:property value="xsumScoreEnd"/>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<SELECT name="elRegistration.classParas[<s:property value="#classparas_st.index" />].linkTerm">
											<OPTION <s:if test="linkTerm==\"or\"">selected='selected'</s:if> value="or">or</OPTION>
											<OPTION <s:if test="linkTerm==\"and\"">selected='selected'</s:if> value="and">and</OPTION>
											</SELECT></LABEL><A style="WIDTH: 14px; FLOAT: right; HEIGHT: 14px; CURSOR: hand" onclick="javascript:deleteClassUserinfo(this,<s:property value="elClass.id"/>);return false;" href="javascript:void(0)">X</A>
										</div>
										</s:iterator>
										</div>
										<%-- 
											<s:if test="elclass.elRegistration.elclassName == null ">style="display: none;width: 100%;"</s:if>
										<div id="PXB_" <s:if test="elclass.elRegistration.elclasss.size == 0 ">style="display: none;width: 100%;"</s:if>> 
											<s:property value="elclass.elRegistration.elclassName" /> 
											<a  href="" style="color:red"  <s:if test="elclass.elRegistration.elclasss.size == 0 ">style="display: none;"</s:if>
												onclick="javascript:deleteNames(2);return false;">X</a>  
										</div>
										<s:hidden name="elRegistration.elclassName"></s:hidden>
										
										<SELECT name="elRegistration.classScreeningWay">
											<option value="0"
												<s:if test="elclass.elRegistration.classScreeningWay == 0 ">selected="selected"</s:if>>
												全部
											</option>
											<option value="1"
												<s:if test="elclass.elRegistration.classScreeningWay == 1 ">selected="selected"</s:if>>
												通过
											</option>
											<option value="2"
												<s:if test="elclass.elRegistration.classScreeningWay == 2 ">selected="selected"</s:if>>
												不通过
											</option>
										</SELECT> --%>
										<span class="txt-info"><a href="#"
											onClick="searchElclassUser();return false;" class="textbg4">选择</a> </span>
									</td>
								</tr>
							</table>
						</div>
						<div id="divSQ_2" style="display: none;">
						</div>
					</td>
				</tr>
				<tr>
					<td height="30" align="right" bgcolor="#FFFFFF"
						style="padding-left: 8px; color: blue;">
						培训班状态：
					</td>
					<td height="30">
						<s:property value="elclass.statusName" />
						<!-- <label>
							开通
						</label>
						<input type="radio"
							<s:if test="elclass.status==1">checked='checked'</s:if>
							name="elclass.status" value="1">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<label>
							关闭
						</label> 
						<input type="radio"
							<s:if test="elclass.status==0">checked='checked'</s:if>
							name="elclass.status" value="0"> -->
					</td>
				</tr>
				<tr>
					<td height="50" align="right" bgcolor="#FFFFFF">
						&nbsp;
						<s:hidden name="elclass.id"></s:hidden>
					</td>
					<td height="30">
						<input name="button" type="button" class="textbg6"
							style="height: 30px; width: 70px;" value="确认修改"
							onclick="doSubmit();" />
						<input style="height: 30px; width: 70px;"
							onclick="document.location.href='elclass_view_man.action?elclass.id=${elclass.id}&sublibs=1'"
							class="textbg6" type="button" title="返回培训班详情" value="班级详情" />
						<input style="height: 30px; color: red; width: 70px;"
							onclick="document.location.href='elclass_course.action?elclass.id=${elclass.id }'"
							class="textbg6" type="button" title="管理当前培训班中的课程" value="课程管理" />
						<s:if test="Return==''||null==Return">
							<input style="height: 30px; width: 100px;"
								onclick="document.location.href='elclass_alllist.action?elclass.id=${elclass.id }'"
								class="textbg6" type="button" title="" value="返回班级列表" />
						</s:if>
						<s:if test="Return=='elcsh'">
							<input style="height: 30px; width: 100px;"
								onclick="document.location.href='elclass_sh_list.action?elclass.id=${elclass.id }'"
								class="textbg6" type="button" title="" value="返回审核列表" />
						</s:if>
					</td>
				</tr>

				<!-- <tr>
					<td style="padding: 0px; margin: 0px;" colspan="2">
						<iframe id="bixiuFrame"
							src="elclass_course_bx.action?elclassId=${elclass.id}" width=100%
							marginwidth="0" marginheight="0" frameborder=0
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
					<td style="padding: 0px; margin: 0px;" colspan="2">
						<iframe id="xuanxiuFrame"
							src="elclass_course_xx.action?elclassId=${elclass.id}" width=100%
							height=280 marginwidth="0" marginheight="0" frameborder=0
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
			<%-- 
			<s:hidden name="isEroomName" id="isEroomName" value="1"></s:hidden>  
			<s:hidden name="isClassName" id="isClassName" value="1"></s:hidden> 
			 --%>
		</s:form>
		<br />
		<script type="text/javascript">
			var bmarr = new Array();
			var bmidArr = new Array();
			var initBM = '<s:property value="elRegistration.treeType"/>';
			if(initBM){
			  bmidArr = initBM.split(",");
			}
			var jzArr = new Array();
			var jzidArr = new Array();
			var initJZ = '<s:property value="elRegistration.jingzhong"/>';
			if(initJZ){
			  jzidArr = initJZ.split(",");
			}
			var zwArr = new Array();
			var zwidArr = new Array(); 
			var initZW = '<s:property value="elRegistration.zhiwu"/>';
			if(initZW){
			  zwidArr = initZW.split(",");
			}
			var zjArr = new Array();
			var zjidArr = new Array();
			var initZJ = '<s:property value="elRegistration.zhiji"/>';
			if(initZJ){
			  zjidArr = initZJ.split(",");
			}
			var gwArr = new Array();
			var gwidArr = new Array();
			var initGW = '<s:property value="elRegistration.gangwei"/>';
			if(initGW){
			  gwidArr = initGW.split(",");
			}
			var dsArr = new Array();
			var dsidArr = new Array();
			var initDS = '<s:property value="elRegistration.dishi"/>';
			if(initDS){
			  dsidArr = initDS.split(",");
			}
			var typeid;
			//**//
			/*
			function deleteNames(type){
				if(type == 1){ 
					document.getElementById("isEroomName").value=0; 
					document.getElementById("KC_").innerHTML="";
					document.getElementById("KC_").style.display='none';
					return true;
				}else
				if(type == 2){
					document.getElementById("isClassName").value=0; 
					document.getElementById("PXB_").innerHTML="";
					document.getElementById("PXB_").style.display='none'; 
					return true;
				}
			}
			*/
			function doSubmit(){
				/*
				var titleObj=document.getElementById("name");
				var title=titleObj.value.replace(/(\s*$)/g, "");
				if(title==""){
					alert("培训班名称不能为空!");
					titleObj.focus();
					return false;
				}*/
				if(!$.trim($("#name").val()).length > 0){
					alert("培训班名称不能为空!");
					$("#newsName").focus();
					return false;
				}
				var qlibId=$("input[name='elclass.cltype.id']:checked").val();
				if(qlibId==undefined){
					alert("请选择培训班类别！");
					return false;
				}
				
				var r_start = $("#r_start").val();
				var r_end = $("#r_end").val();
				
				var date=new Date(Date.parse(r_start.replace(/-/g,"/")));
				var date2=new Date(Date.parse(r_end.replace(/-/g,"/")));
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
				
			
				if(r_start != "" && r_end != ""){
					var checkstart = checkDateTime(r_start,"开始时间");
					var chenkend = checkDateTime(r_end,"结束时间");
					if(!checkstart){
					//	alert("开始时间格式错误，请重新输入，格式为:yyyy-MM-dd HH:mm:ss");
						return false;
					}
					if(!chenkend){
					//	alert("结束时间格式错误，请重新输入，格式为:yyyy-MM-dd HH:mm:ss");
						return false;
					}
					var kb = duibi(r_start,r_end); 
						if(kb){ 
						}else{
							alert("培训班开始时间不能大于结束时间！");
							return false;					
						}
				}else{
						alert("培训班开始时间和结束时间不能为空！");
						return false;
				}
				var isApp=$("input[name='elclass.isApplication']:checked").val();
				if(isApp == 1){
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
				}
				//if(jzArr.length > 1)
				document.getElementById("jz").value=jzidArr.join(",");
				//if(gwArr .length != 0)
				document.getElementById("gw").value=gwidArr.join(",");
				//if(dsArr .length != 0)
				document.getElementById("ds").value=dsidArr.join(",");
				//if(zwArr .length != 0)
				document.getElementById("zw").value=zwidArr.join(",");
				//if(zjArr .length != 0)
				document.getElementById("zj").value=zjidArr.join(",");
				//if(bmidArr .length != 0)
				document.getElementById("bm").value=bmidArr.join(",");  
				document.class_info.submit(); 
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
