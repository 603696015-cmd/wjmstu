<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.classman.entities.ElClType"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@page import="com.sopia.classman.entities.ElClass"%>

<%
	String cltypeName = "";
	if (request.getAttribute("elclass") != null) {
		cltypeName = ((ElClass) request.getAttribute("elclass"))
				.getCltype().getName()
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
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
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
	</HEAD>
	<body>
		<div class="dh3">
			<!--<div class="newpos"></div> 
			<div class="newpos2"> 
				<span style="font-weight: bold;">培训班详情</span>
			</div>-->
			<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
				<wysLib:Navigation ivalue="培训班详情页" />
			</div>
		</div>
		<s:form action="elclass_add" theme="simple" method="post"
			name="class_info" id="class_info">
			<table width="95%" cellpadding="2" cellspacing="1">
				<tr>
					<td width="160" height="30" align="center" bgcolor="#FFFFFF">
						培训班名称：
					</td>
					<td height="30">
						<label>
							<s:property value="elclass.name" />
						</label>
					</td>
				</tr>

				<tr>
					<td height="30" align="center" bgcolor="#FFFFFF">
						培训班介绍：
					</td>
					<td height="30">
						<label>
							<s:property value="elclass.description" />
						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="center" bgcolor="#FFFFFF">
						结业证书名称：
					</td>
					<td height="30">
						<label>
							<s:property value="elclass.certificatename" />
						</label>
					</td>
				</tr>

				<tr>
					<td height="30" align="center" bgcolor="#FFFFFF">
						所属类别：
					</td>
					<td height="30">
						<label>
							<%=cltypeName%>
						</label>
					</td>
				</tr>
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
			<!-- <tr>
					<td height="30" align="center" bgcolor="#FFFFFF">
						结业条件：
					</td>
					<td height="30">
						<label>
							<s:property value="elclass.optionalcredit" />
							分/ (必修课全部通过，选修课最少获得的学分)
						</label>
					</td>
				</tr> -->	
				<tr>
					<td height="30" align="center" bgcolor="#E6F9F9">
						结业条件：					</td>
					<td height="30" bgcolor="#FFFFFF">
						<s:if test="elclass.classtype == 2">  
					  		必修课最少获得:<s:property value="elclass.credit_bx" />  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					  		选修课最少获得:<s:property value="elclass.credit_xx" />   
						</s:if><s:else>
							<label>
								<s:property value="elclass.optionalcredit"/>分/
								(必修课全部通过，选修课最少获得的学分) 
							</label>
						</s:else>
					</td>
				</tr> 
				<s:if test="elclass.id != null">
					<tr>
						<td height="30" align="center" bgcolor="#FFFFFF">
							培训班状态：
						</td>
						<td height="30">
							<s:property value="elclass.statusName" />
						</td>
					</tr>
					<s:if test="elclass.isApplication == 1">
						<tr>
							<td height="30" align="center" bgcolor="#FFFFFF">
								是否可申请：
							</td>
							<td height="30">
								<table width="95%" cellpadding="2" cellspacing="1">
									<tr>
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
											<s:property value="elRegistration.sex" />
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
									<%-- 
								<tr>
									<td width="100" align="center" bgcolor="#FFFFFF">
										<wysLib:BasetName btid="4" />:
								  </td>
									<td>
											<s:property value="elRegistration.gangweiName" /> 
									</td>
								</tr>
								 --%>
									<tr>
										<td width="100" align="center" bgcolor="#FFFFFF">
											考场:
										</td>
										<td>
											<s:property value="elclass.elRegistration.examroomName" />
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
											</SELECT>
										</td>
									</tr>
									<tr>
										<td width="100" align="center" bgcolor="#FFFFFF">
											培训班:
										</td>
										<td>
											<s:property value="elclass.elRegistration.elclassName" />
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
											</SELECT>
										</td>
									</tr>
								</table>
					</s:if>
				</s:if>
				<s:else>
					<input type="hidden" name="elclass.status" value="3">
					<tr>
						<td height="50" align="center" bgcolor="#FFFFFF">
							&nbsp;
							<s:hidden name="elclassId"></s:hidden>
						</td>
						<td height="30">
							<input style="height: 35px;" class="textbg6" name="submit"
								type="submit" value="确认添加" />
						</td>
					</tr>
				</s:else>
				<tr>
					<td colspan="2">
						<iframe id="bixiuFrame"
							src="elclass_details_bx_sh.action?elclassId=${elclass.id}&PageStatus=${PageStatus}&PageStatusint=${PageStatusint}"
							width=100% marginwidth="0" marginheight="0" frameborder=0
							onload="this.height=bixiuFrame.document.body.scrollHeight + 20"></iframe>
					</td>
				</tr>
				<!-- <tr>
					<td colspan="2">
						<iframe id="zhuxiuFrame" src="elclass_course_zx.action?elclassId=${elclass.id}" width=100% height=280 
								marginwidth="0" marginheight="0" frameborder=0 onload="this.height=zhuxiuFrame.document.body.scrollHeight + 20"></iframe>
					</td>
				</tr> -->
				<tr>
					<td colspan="2">
						<iframe id="xuanxiuFrame"
							src="elclass_details_xx_sh.action?elclassId=${elclass.id}&PageStatus=${PageStatus}&PageStatusint=${PageStatusint}"
							width=100% height=280 marginwidth="0" marginheight="0"
							frameborder=0
							onload="this.height=xuanxiuFrame.document.body.scrollHeight + 20"></iframe>
					</td>
				</tr>
			</table>
		</s:form>
		<div style="text-align: center;padding-top:10px; padding-bottom: 20px;">
		<s:if test="Return==''||Return==null">
		<a href="elclass_alllist.action" class="textbg4" style="width:100px">返回班级列表</a>
		</s:if>
		<s:if test="Return=='assign'">
		<a href="elclass_assignlist2.action" class="textbg4" style="width:100px">返回分配列表</a>
		</s:if>
		<s:if test="Return=='ash'">
		<a href="elclass_primash_list.action" class="textbg4" style="width:100px">返回申请列表</a>
		</s:if>
		<s:if test="Return=='sh'">
		<a href="elclass_sh_list.action" class="textbg4" style="width:100px">返回审核列表</a>
		</s:if>
		<s:if test="Return=='aal'">
		<a href="elclass_applyAlter_list.action" class="textbg4" style="width:100px">返回修改列表</a>
		</s:if>
		<s:if test="Return=='alsh'">
		<a href="elclass_alter_list.action" class="textbg4" style="width:100px">返回处理列表</a>
		</s:if>
		<s:if test="Return=='adl'">
			<a href="elclass_applyDelete_list.action" class="textbg4" style="width:100px">返回删除列表</a>
		</s:if>
		<s:if test="Return=='adla'">
			<a href="elclass_delete_apply_list.action" class="textbg4" style="width:120px">返回处理删除列表</a>
		</s:if>
		<s:if test="Return=='csc'">
			<a href="combinationSearchClass.action" class="textbg4" style="width:120px">返回处理搜索列表</a>
		</s:if>
		</div>
	
	</body>
</HTML>
