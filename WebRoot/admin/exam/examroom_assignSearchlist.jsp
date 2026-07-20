<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.courseman.entities.ExamRoom"%>
<%@page import="com.sopia.questionman.entities.ExamPaper"%>
<%@page import="com.sopia.courseman.entities.Course"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<TITLE>商务汉语学习系统--管理端--学员添加</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/pageutil.js"></script>
		<script type="text/javascript" src="js/eroomassign.js"></script>
		<script type="text/javascript" src="js/userCheck.js"></script>
		<link rel="StyleSheet" href="js/tree/dtree.css" type="text/css" />
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((     this .     sectionRowIndex %     2 ==     0)
		?  
		  "#ffffff" :     "#f4f4f4" )
}
</style>
		<script type="text/javascript" src="js/tree/dtree.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript">
			function _onLoad(type,classid,eroomid){ 
					var toUserInfo = document.getElementById("toUserInfo");
					var toClassInfo = document.getElementById("toClassInfo");
					var toEroomInfo = document.getElementById("toEroomInfo"); 
					if(type == 0){ 
						toUserInfo.style.display="block";
						toClassInfo.style.display="none";
						toEroomInfo.style.display="none";
					}else if(type == 1){ 
						toUserInfo.style.display="none";
						toClassInfo.style.display="block";
						toEroomInfo.style.display="none"; 
					}else if(type == 2){ 
						toUserInfo.style.display="none";
						toClassInfo.style.display="none";
						toEroomInfo.style.display="block";
					}else{
						alert("未知类型！");
					}  
					if(classid != 0){
						document.getElementById("PXB").style.display="block";
					} 
					if(eroomid != 0){
						document.getElementById("KC").style.display="block";
						document.getElementById("KCSQ").style.display="block";  
						
					} 
			}
		</script>
	</HEAD>
	<BODY
		onLoad="_onLoad(<s:property value="DBMethods"/>,'<s:property value="elClass.id"/>','<s:property value="eroom.id"/>')">
		<form name="ab_list" action="examroom_assignwc.action" method="post">
			<s:hidden name="examPaper.id" />
			<s:hidden name="examRoom.id" />
			<s:hidden name="department.id" />
			<s:hidden name="course.id" />
			<s:hidden name="sub_department" value="1" />
			<s:hidden name="pN" value="0" />
			<s:hidden name="pS" />
		</form>
		<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="考生增减" />
				</div>
			</li>
		</ul>
		<!-- 内容 -->
		<h3 style="width: 100%; margin-top: 15px; text-align: center;">
			考场【
			<s:property value="examRoom.title" />
			】中的试卷【
			<s:property value="examPaper.title" />
			】的考生列表
		</h3>
		<s:hidden name="elUser.username" />
		<s:hidden name="elUser.email" />
		<s:hidden name="elUser.realname" />
		<s:hidden name="department.id" />

		<s:form action="examroom_assignSearchlist" method="post"
			name="acc_list" theme="simple" id="acc_list">
			<s:hidden name="examRoom.queryManner" id="queryManner" />
			<s:hidden name="pN" id="pageNow" />
			<s:hidden name="pS" />
			<s:hidden name="optype" />
			<s:hidden name="examPaper.id" />
			<s:hidden name="examRoom.id" />
			<s:hidden name="course.id" />
			<s:hidden name="department.id" />
			<s:hidden name="station.id" id="staid" />
			<input type="radio" name="DBMethods" value="0" id="DBM"
				onclick="DistributionMethods(0)"
				<s:if test="DBMethods == 0">checked="checked"</s:if> /> 按用户信息查询
			<input type="radio" name="DBMethods" value="1" id="DBM"
				onclick="DistributionMethods(1)"
				<s:if test="DBMethods == 1">checked="checked"</s:if> /> 按培训班成绩查询
			<input type="radio" name="DBMethods" value="2" id="DBM"
				onclick="DistributionMethods(2)"
				<s:if test="DBMethods == 2">checked="checked"</s:if> /> 按考场成绩查询
			<input type="radio" name="DBMethods" value="3" id="DBM"
				onclick="DistributionMethods(3)"
				<s:if test="DBMethods == 3">checked="checked"</s:if> /> 按考场试卷成绩查询
			<a style="margin-left: 300px"
				href="javascript:eroomEpWriteUser('<s:property value="examRoom.id" />','<s:property value="examPaper.id" />');"
				class="textbg6">学员导入</a>
			<a style="margin-left: 5px"
				href="javascript:eroomEpdeleteUser('<s:property value="examRoom.id" />','<s:property value="examPaper.id" />');"
				class="textbg6">学员剔除</a>
			<div id="toUserInfo" style="display: block">
				<table align="center" cellpadding="1" cellspacing="1" width="1000">
					<tr>
						<td>
							<%-- 
						<wysLib:BasetName btid="4" />： 
								<s:select name="elUser.gangwei" cssClass="g-select"
										list="gangweis" listKey="id" key="2" listValue="basevalue"  headerValue="全部" headerKey="0" />
						 --%>
						</td>
						<td>
							<wysLib:BasetName btid="5" />
							：
							<s:select name="elUser.dishi" cssClass="g-select" list="dishis"
								listKey="id" listValue="basevalue" headerValue="全部"
								headerKey="0" />
						</td>
						<td>
							<wysLib:BasetName btid="3" />
							：
							<s:select name="elUser.zhiji" cssClass="g-select" list="zhijis"
								listKey="id" listValue="basevalue" headerValue="全部"
								headerKey="0" />
						</td>
						<td>
							<wysLib:BasetName btid="2" />
							：
							<s:select name="elUser.zhiwu" cssClass="g-select" list="zhiwus"
								listKey="id" listValue="basevalue" headerValue="全部"
								headerKey="0" />
						</td>
						<td>
							<wysLib:BasetName btid="1" />
							：
							<s:select name="elUser.jingzhong" cssClass="g-select"
								list="jingzhongs" listKey="id" listValue="basevalue"
								headerValue="全部" headerKey="0" />
						</td>
					<tr>
						<td>
							姓名：
							<input name="elUser.realname"
								value="<s:property value="elUser.realname"/>"
								id="elUser.realname">
						</td>
						<td>
							账号：
							<input name="elUser.username"
								value="<s:property value="elUser.username"/>"
								id="elUser.username">
						</td>
						<td>
							生日开始时间:
							<input type="text" size="16"
								value="<s:date format="yyyy-MM-dd" name="elUser.shengri"/>"
								name="elUser.shengri" onclick="setday(this)" readonly="readonly">
						</td>
						<td>
							生日结束时间:
							<input type="text" size="16"
								value="<s:date format="yyyy-MM-dd" name="elUser.shengri_end"/>"
								name="elUser.shengri_end" onclick="setday(this)"
								readonly="readonly">
						</td>
						<td>
							性别：
							<select name="elUser.sex">
								<option value="" selected="selected">
									全部
								</option>
								<option value="男"
									<s:if test="elUser.sex==\"男\"">selected='selected'</s:if>>
									男
								</option>
								<option value="女"
									<s:if test="elUser.sex==\"女\"">selected='selected'</s:if>>
									女
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							包含下级节点：
							<input type="checkbox" name="sub_department"
								<s:if test="sub_department==1">checked="checked"</s:if>
								id="sub_department" value="1" />

						</td>
						 <td>
				    	岗位：
										<s:textfield theme="simple" name="station.name"
											size="10" id="gangweiName" readonly="true" />
										<a href="#" class="textbg4" style="width: 90px;"
											onClick="searchUserInit2();return false;">点此进行选择</a>
				 	   </td>
				         <td>
							岗位名称：
										<s:textfield name="elUser.xianzhiwei" />
						</td>
						<td>
						</td>
						<td>
							<input id="find" class="textbg4" name="find" type="button"
								onclick="doForm();" value="搜索">
							<%-- 点搜索后应该初始化分页 --%>
						</td>
					</tr>
				</table>
			</div>
			<%-- 
			<div id="toClassInfo" style="display: none;text-align:center;">
				<table align="center" cellpadding="1" cellspacing="1" width="100%">
					<tr>
						<td colspan="3">
							<div id="PXB" style="display: none; width: 100%;">
								<s:if test="elClass != null">
									<span
										style="width: 150px; height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
										<label style="width: 130px; float: left;">
											<s:property value="elClass.name" />
											<s:hidden name="elClasss[0].id"></s:hidden>
										</label> <a
										style="cursor: hand; float: right; width: 14px; height: 14px;"
										href=""
										onclick="javascript:deleteClassInfo(this,<s:property value="elClass.id"/>,'PXB');return false;">X</a>
									</span>
								</s:if>
							</div>
							<span class="txt-info"><a style="width: 100px"
								class="textbg4" href="#"
								onClick="searchElclassUser();return false;">选择培训班</a></span>
							<input id="findclass" class="textbg4" name="findclass"
								type="button" onclick="seachOnClassPage('1');" value="搜索">
						</td>
					</tr>
					<tr>
						<td>
							总 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分：
							<s:textfield name="elUser.btotalscore" id="btotalscore" />
							~
							<s:textfield name="elUser.btotalscore_" id="btotalscore_" />
						</td>
						<td>
							是 否 通 过&nbsp;：
							<select name="elUser.isPass">
								<option value="全部"
									<s:if test="elUser.isPass == '全部'">checked="checked"</s:if>>
									全部
								</option>
								<option value="0"
									<s:if test="elUser.isPass == '0'">checked="checked"</s:if>>
									通过
								</option>
								<option value="1"
									<s:if test="elUser.isPass == '1'">checked="checked"</s:if>>
									不通过
								</option>
							</select>
						</td>
					</tr>
					
					<tr>
						<td>
							必修课总学分：
							<s:textfield name="elUser.bxscore" id="bxscore" />
							~
							<s:textfield name="elUser.bxscore_" id="bxscore_" />
						</td>
						<td>
							选修课总学分：
							<s:textfield name="elUser.xxscore" id="xxscore" />
							~
							<s:textfield name="elUser.xxscore_" id="xxscore_" />
						</td>
						<td>
							<input id="findclass" class="textbg4" name="findclass"
								type="button" onclick="seachOnClassPage('1');" value="搜索">
						</td>
					</tr>
				</table>
			</div>
			 --%>
			<%-- 
					<div id="toEroomInfo_nouser" style="display:none"> 
						 <table align="center" cellpadding="1" cellspacing="1" width="100%"> 
							 <tr>
							 	<td colspan="3"> 
									<div id="KC" style="display:none;width: 100%;">  
										<s:if test="eroom != null">
											<span
												style="width: 150px; height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
												<label style="width: 130px; float: left;">
													<s:property value="eroom.title" />
													<s:hidden name="examRooms[0].id"></s:hidden>
												</label>   <a
												style="cursor: hand; float: right; width: 14px; height: 14px;"
												href=""
												onclick="javascript:deleteEroomInfo(this,<s:property value="examRoom.id"/>,'PXB');return false;">X</a> 
											</span> 
										</s:if>
									</div>
									<span class="txt-info"><a href="#"
										onClick="searchExamRoomUser();return false;">点此进行选择考场</a> </span>  
								</td>
							 </tr>  
							 <tr>
							 	<td> 
							 		总分：
								 	<s:textfield name="elUser.KcBtotalscore" id="KcBtotalscore" value="0"></s:textfield>~
								 	<s:textfield name="elUser.KcBtotalscore_" id="KcBtotalscore_" value="0"></s:textfield>
								 </td>
							 	<td>
							 		是 否 通 过&nbsp;：
						           <select name="elUser.isKcPass" >
						               <option value="全部" <s:if test="elUser.isKcPass == '全部'">checked="checked"</s:if> >全部</option>
						               <option value="0" <s:if test="elUser.isKcPass == '0'">checked="checked"</s:if>>通过</option>
						               <option value="1" <s:if test="elUser.isKcPass == '1'">checked="checked"</s:if>>不通过</option>
						           </select> 
								 </td>
							 	<td> 
										<input id="find" name="find" type="submit" value="搜索">
							 	</td> 
							 </tr>
							 <tr>
							 	<td colspan="3">
								 	<div id="KCSQ" style="display:none;width: 100%;">  
										<input type="hidden" name="examRooms.id" value="<s:property value="eroom.id"/>">   
										<s:iterator value="examPapers">  
												<input type="hidden" name="elUser.epids" value="<s:property value="id"/>">  
												试卷【<s:property value="title" /> 】得分：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<s:textfield name="elUser.Kcsq"	id="Kcsq" value="不限"/>~
												<s:textfield name="elUser.Kcsq_"	id="Kcsq_" value="不限"/> 
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												考试次数:<s:textfield name="elUser.Kclxcs" id="Kclxcs" value="不限"/>~
												<s:textfield name="elUser.Kclxcs_"	id="Kclxcs_" value="不限"/> <br/>
										</s:iterator>
								 	</div> 
							 	</td>
							 </tr>
							 </table>
					</div>
					 --%>
			<div id="toClassInfo" style="display: none; text-align: center;">
				<div style="border: 1px solid #D1E4F5; width: 1000px; margin-top: 10px;">
					<div id="PXB" style="width: 100%; display: none;"></div>
					<div style="text-align: right; float: left;">
						<a style="width: 100px" class="textbg4"
							href="javascript:searchElclassUser();">选择培训班</a>
						<input type="button" class="textbg4" onclick="seachOnClassPage(0);"
							value="搜索" />
					</div>
				</div>
			</div>
			<div id="toEroomInfo" style="display: none; text-align: center;">
				<div
					style="border: 1px solid #D1E4F5; width: 1000px; margin-top: 10px;">
					<div id="KC" style="width: 100%; display: none;"></div>
					<div style="text-align: right; float: left;">
						<a style="width: 100px" class="textbg4"
							href="javascript:searchExamRoomUser();">选择考场</a>
						<input type="button" class="textbg4" onclick="seachOnEroom('1');"
							value="搜索" />
					</div>
				</div>
			</div>
			<div id="toEroomEpInfo" style="display: none; text-align: center;">
				<div
					style="border: 1px solid #D1E4F5; width: 1000px; margin-top: 10px;">
					<div id="KCEP" style="width: 100%; display: none;"></div>
					<div style="text-align: right; float: left;">
						<a href="javascript:searchExamRoomEpUser();" style="width: 100px"
							class="textbg4">选择考场</a>
						<input type="button" class="textbg4" onclick="seachOnEroom('2');"
							value="搜索" />
					</div>
				</div>
			</div>
			<%
				ExamRoom uu = ((ExamRoom) request.getAttribute("examRoom"));
					String erid = "0";
					erid = uu == null ? "0" : uu.getId() + "";
					ExamPaper uu1 = ((ExamPaper) request.getAttribute("examPaper"));
					String epid = "0";
					epid = uu1 == null ? "0" : uu1.getId() + "";
					Course c1 = ((Course) request.getAttribute("course"));
					String cid = "0";
					cid = c1 == null ? "0" : c1.getId() + "";
					String x = "examroom_assignSearchlist.action?sub_department=1&examPaper.id="
							+ epid
							+ "&examRoom.id="
							+ erid
							+ "&course.id="
							+ cid
							+ "&department.id=";
			%>
			<table align="center" cellpadding="1" cellspacing="1" width="1000">
				<tr>
					<td valign="top" width="200">
						<wysLib:dep_list_aj rootAble="true" href="<%=x%>"
							iname="department.id"></wysLib:dep_list_aj>
						<script type="text/javascript">
							w0.setValues([new DEP(<s:property value="department.id"/>,<s:property value="department.lid"/>,<s:property value="department.rid"/>)]);
						</script>
					</td>
					<td>
						<table align="center" cellpadding="1" cellspacing="1" width="800">
							<tr>
								<th width="20">
								</th>
								<th>
									学号
								</th>
								<th>
									姓名
								</th>
								<th>
									部门
								</th>
								<th>
									角色
								</th>
								<th>
									性别
								</th>
								<th>
									警种
								</th>
								<th>
									年龄
								</th>
								<th>
									分配
								</th>
								<th>
									参加方式
								</th>
							</tr>
							<tbody onMouseOut="changeback()" onMouseOver="changeto()"
								id="data_list">
								<s:iterator value="elusers">
									<tr>
										<td width="20" height="20" align="center">
											<%-- 	<s:if test="joinwayInt==0||joinwayInt==2"> --%>
											<input type="checkbox" name="canAssignUsers.id"
												value="<s:property value="id"/>" />
											<%-- </s:if> --%>
										</td>
										<td height="30" align="center">
											<s:property value="username" />
										</td>
										<td height="20" align="center">
											<s:property value="realname" />
										</td>
										<td height="20" align="center">
											<s:property value="department.name" />
										</td>
										<td height="20" align="center">
											<s:property value="role.name" />
										</td>
										<td height="20" align="center">
											<s:property value="sex" />
										</td>
										<td height="20" align="center">
											<s:property value="jingzhong_" />
										</td>
										<td height="20" align="center">
											<s:property value="age" />
										</td>
										<td height="20" align="center">
											<s:if test="!introom">
											未分配
										</s:if>
											<s:else>
												<font color="red"> 已分配</font>
											</s:else>
										</td>
										<td height="20" align="center">
											<s:if test="introom">
												<s:property value="joinway_" />
											</s:if>
										</td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
					</td>
				</tr>
			</table>
		</s:form>
		<div style="margin-bottom: 20px; text-align: center;">
			<div id="page_div">
				<wysLib:page></wysLib:page>
			</div>
			<a href="javascript:select_All()" class="textbg4" />全选</a>
			<a href="javascript:select_Fan()" class="textbg4" />反选</a>
			<a href="javascript:select_Bux()" class="textbg4" style="width: 50px" />全不选</a>
			<br />
			<input class="textbg" style="border: none; color: red"
				title="勾选上述列表中人员添加到当前考场中" value="添加到考场" type="button"
				onClick="assign2user()">
			<!--<input value="重新搜索" type="button" onclick="backSearch()">-->
			<input class="textbg" style="border: none;; color: red" value="分配给全部"
				title="将上述的搜索条件搜索出的人员分配到当前考场中" type="button"
				onClick="assign2users()" />
			<input class="textbg" style="border: none;; color: red"
				title="勾选上述列表中人员移出当前考场的当前试卷" value="移除考场" type="button"
				onClick="unassign2user()">
			<input class="textbg" style="border: none;" value="试卷列表"
				title="返回考场试卷列表" type="button"
				onclick="document.location.href='examroom_assignwcInit.action?examRoom.id=<s:property value="examRoom.id"/>&course.id=<s:property value="course.id"/>'">
			<input style="border: none;" type="button" class="textbg"
				onclick="document.location.href='examroom_assignuserlist.action?examPaper.id=<s:property value="examPaper.id"/>&examRoom.id=<s:property value="examRoom.id"/>&course.id=<s:property value="course.id"/>'"
				value="人员详情" />
			<s:if test="examRoom.valid==0">
				<input class="textbg" title="确认添加人员无误后可创建完成，继续审核！" style="border: none; color: red" type="button"
					value="创建完成" onclick="sh0(<s:property value="examRoom.id"/>)" />
			</s:if>
			<br />
			<br />
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
