<%@ page language="java" pageEncoding="UTF-8"   %>
<%@page import="com.sopia.classman.entities.ElClass"%> 
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
		<TITLE>分配学员</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/message.js"></script>		
		<script type="text/javascript" src="js/tree/depuserlist.js"></script>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/pageutil.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/classassign.js"></script>
		<script type="text/javascript" src="js/userCheck.js"></script>
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
	<BODY onLoad="_onLoad(<s:property value="DBMethods"/>,'<s:property value="elClass.id"/>','<s:property value="examRoom.id"/>')">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="分配学员" /></div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
		<div style="font-size: 15px;">
		培训班<strong><s:property value="elclass.name" /> </strong>的学员管理</div>
		<table width="100%" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="2">
					<s:form action="elclass_assign2userInit" method="post" theme="simple" name="sousuo" id="sousuo">  
					<s:hidden name="department.id" /> 
					<s:hidden name="station.id" id="staid" />
					<s:hidden name="examRoom.queryManner" id="queryManner" />
					<input type="radio" name="DBMethods" value="0" id = "DBM" onclick="DistributionMethods(0)" <s:if test="DBMethods == 0">checked="checked"</s:if>/> 按用户信息查询
					<input type="radio" name="DBMethods" value="1" id = "DBM" onclick="DistributionMethods(1)" <s:if test="DBMethods == 1">checked="checked"</s:if>/> 按培训班成绩查询
					<input type="radio" name="DBMethods" value="2" id = "DBM" onclick="DistributionMethods(2)" <s:if test="DBMethods == 2">checked="checked"</s:if>/> 按用考场成绩查询
					<input type="radio" name="DBMethods" value="3" id = "DBM" onclick="DistributionMethods(3)" <s:if test="DBMethods == 3">checked="checked"</s:if>/> 按用考场试卷查询
					<a style="margin-left:300px" href="javascript:classWriteUser('<s:property value="elclass.id" />');" class="textbg6">学员导入</a>
					<div id="toUserInfo" style="display:block">
							<s:hidden name="elclass.id" />
							<s:hidden name="pN" id="pageNow2" />
							 <table align="center" cellpadding="1" cellspacing="1" width="100%">
								<tr>
									<td>
										<%-- 
										<s:hidden name="examRoom.id" />
							<s:hidden name="examPaper.id" />
							<wysLib:BasetName btid="4" />： 
												<s:select name="elUser.gangwei" cssClass="g-select"
														list="gangweis" listKey="id" key="2" listValue="basevalue"  headerValue="全部" headerKey="0" /> 
										 --%>
									</td>
									<td>
										<wysLib:BasetName btid="5" />：
												<s:select name="elUser.dishi" cssClass="g-select" list="dishis"
														listKey="id" listValue="basevalue" headerValue="全部" headerKey="0"/>
									</td>
									<td>
										<wysLib:BasetName btid="3" />： 
												<s:select name="elUser.zhiji" cssClass="g-select" list="zhijis"
														listKey="id" listValue="basevalue" headerValue="全部" headerKey="0"/> 
									</td>
									<td>
										<wysLib:BasetName btid="2" />： 
												<s:select name="elUser.zhiwu" cssClass="g-select" list="zhiwus"
														listKey="id" listValue="basevalue" headerValue="全部" headerKey="0"/> 
									</td>
									<td>
										<wysLib:BasetName btid="1" />： 
												<s:select name="elUser.jingzhong" cssClass="g-select"
														list="jingzhongs" listKey="id" listValue="basevalue" headerValue="全部" headerKey="0"/> 
									</td>
								<tr>
									<td> 
										姓名：<input name="elUser.realname"
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
											name="elUser.shengri_end" onclick="setday(this)" readonly="readonly">
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
										搜索包含下级部门：
										<input type="checkbox" name="sub_department"	<s:if test="sub_department==1">checked="checked"</s:if>	id="sub_department" value="1"/>
									</td>
									<td>
											是否已分配：
								           <select name="elUser.isAssign">
						               		   <option ></option>
								               <option value="0">是</option>
								               <option value="1">否</option>
								           </select>
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
										<input class="textbg4" type="button" onClick="doForm();" value="搜索"><%-- 点搜索后应该初始化分页 --%>
									</td>
								</tr>
							</table>
					</div>
					<div id="toClassInfo" style="display:none" >
						 <table align="center" cellpadding="1" cellspacing="1" width="100%">
							 <tr>
							 	<td colspan="3"> 
									<div id="PXB" style="display:none;width: 100%;">  
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
									<span class="txt-info"><a href="#"
										onClick="searchElclassUser();return false;" class="textbg4" style="width:90px;">选择培训班</a> </span>  
										<input class="textbg4" type="button" onClick="seachOnClassPage(0);" value="搜索">
								</td>
							 </tr> 
							 
										
						 </table> 
					</div>
					 <div id="toEroomInfo" style="display: none; text-align: center;">
						<div
							style="border: 1px solid #D1E4F5; width: 100%; margin-top: 10px;">
							<div id="KC" style="width: 100%; display: none;"></div>
							<div style="text-align: right; float: left;">
								<a href="javascript:searchExamRoomUser();" class="textbg4" style="width:90px">选择考场</a>
								<input type="button" onClick="seachOnEroom('1');" class="textbg4" value="搜索" />
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
								<input type="button" class="textbg4" onClick="seachOnEroom('2');"
									value="搜索" />
							</div>
						</div>
					</div>
				</s:form>
				<s:if test="elclass.isApplication==1">
					<div style="color:red;text-align:center;">当前培训班是可申请培训班，结业考场由系统自动分配</div> 
				</s:if>
				</td> 
			</tr>
			<tr>
			<td width="150px;" valign="top" bgcolor="#FFFFFF">
			<%
			   ElClass elclass=(ElClass)request.getAttribute("elclass");   
				int classid = elclass.getId();
				String url = "elclass_assign2userInit.action?elclass.id="+classid+"&sub_department=1&department.id=";
			 %>
			<wysLib:dep_list_aj rootAble="true"	href="<%=url %>" iname="department.id"></wysLib:dep_list_aj>
			<script type="text/javascript">
				w0.setValues([new DEP(<s:property value="department.id"/>,<s:property value="department.lid"/>,<s:property value="department.rid"/>)]);
			</script>
			</td>
			<td align="left" valign="top" bgcolor="#FFFFFF">
		<s:if test="elusers.size==0">当前还没有分配学员</s:if>
			<s:else>
				<table style="margin-top:0px;" width="100%" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr>
						<td height="30" align="left" bgcolor="#66CCFF" style="padding-left:8px;color:blue;">						</td>
					    <td height="30" align="center" bgcolor="#66CCFF" >
						姓名						</td>
						 <td height="30" align="center" bgcolor="#66CCFF" >
						性别						</td>
					   <td height="30" align="center" bgcolor="#66CCFF" >
							账号					  </td>
					    <td height="30" align="center" bgcolor="#66CCFF" >
							部门						</td>
						<td height="30" align="center" bgcolor="#66CCFF" >
							<wysLib:BasetName btid="1" />
					  </td>
						<td height="30" align="center" bgcolor="#66CCFF" >
							年龄						</td>
						<td height="30" align="center" bgcolor="#66CCFF" >
							角色						</td>
						<td height="30" align="center" bgcolor="#66CCFF" >
							分配						</td>
						<td height="30" align="center" bgcolor="#66CCFF" >
							参加方式						</td>
					</tr>
					<s:if test="elusers.size==0">
						<TR>
							<TD align="center" colspan="4">
								当前还没有分配学员
							</TD>
						</TR>
					</s:if>
					<s:else>
						<tbody onMouseOut="changeback()" onMouseOver="changeto()" id="data_list">
						<s:iterator value="elusers">
							<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
							     <input type="checkbox" value="<s:property value="id"/>:<s:property value="isAssign"/>" name="id"> 
					          </td>
							    <td height="30" align="center" >
							      <s:property value="realname" />
						       </td>
						         <td height="30" align="center" >
							      <s:property value="sex" />
						       </td>
                                <td height="30" align="center" >
									<s:property value="username" />
								</td>
                                <td height="30" align="center" >
									<s:property value="department.name" />
								</td>
								<td height="30" align="center" >
									<s:property value="jingzhong_" />
								</td>
								<td height="30" align="center" >
									<s:property value="age" />
								</td>
								<td height="30" align="center" >
									<s:property value="role.name" />
								</td>
								<td height="30" align="center" >
									<s:property value="isAssign" />
								</td>
								<td height="30" align="center" >
									<s:property value="joinway" />
								</td>
							</tr>
						</s:iterator>
						</tbody>
					</s:else>
			  </table> 
			</s:else></td></tr></table>
			<div id="page_div">
				<wysLib:page></wysLib:page>
			</div>
			<a href="javascript:select_All()" class="textbg4">全选</a>
			<a href="javascript:select_Fan()" class="textbg4">反选</a>
			<a href="javascript:select_Bux()" class="textbg4" style="width: 60px">全不选</a>
			<br>
			<input class="textbg" style="border: none;color: red;" value="分配到班级" type="button" onClick="assign()"/>
			<input class="textbg" style="border: none;color: red;" value="移出学员" type="button" onClick="unassign()"/>
			<input class="textbg" style="border: none;color: red;" value="分配给全部" type="button" onClick="assignSearch()"/>
			<a href="elclass_view_man.action?elclass.id=${elclass.id }&sublibs=1"
						class=textbg>返回班级详情</a>
			<s:if test="Return=='elcsh'">
			<input style="border: none;"
				onclick="document.location.href='elclass_sh_list.action?elclass.id=${elclass.id }'"
				class="textbg" type="button" title="" value="返回审核列表"/>
			</s:if>
			<s:if test="Return=='assign'">
			<input style="border: none;"
				onclick="document.location.href='elclass_assignlist2.action?elclass.id=${elclass.id }'"
				class="textbg" type="button" title="" value="返回分配列表"/>
			</s:if>
		</div>  
			<form action="elclass_assign2userInit.action" method="post" name="course_assignment" id="course_assignment">
				<s:hidden name="deptid" />
				<s:hidden name="department.id" />
				<s:hidden name="elclass.id" />
				<s:hidden name="elUser.sex" />
				<s:hidden name="elUser.realname" />
				<s:hidden name="elUser.username" />
				<s:hidden name="elUser.jingzhong" />
				<s:hidden name="starttime" />
				<s:hidden name="endtime" />
				<s:hidden name="elUser.isAssign" />
				<s:hidden name="userids" id="userids"></s:hidden>
				<s:hidden name="sub_department" id="sub_department"></s:hidden>
				<s:hidden name="elclass.isApplication" />
	     </form>
		<!-- 内容 -->
	
	</body>
		<form action="elclass_assign2userInit.action" method="post" name="acc_list">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<s:hidden name="deptid"/>
				<s:hidden name="elclass.id" />
				<s:hidden name="examRoom.id" />
				<s:hidden name="examPaper.id" />
				<s:hidden name="cltype.id" />
				<s:hidden name="elUser.sex" />
				<s:hidden name="elUser.realname" />
				<s:hidden name="elUser.username" />
				<s:hidden name="elUser.jingzhong" />
				<s:hidden name="starttime" />
				<s:hidden name="endtime" />
				<s:hidden name="department.id" />
				<s:hidden name="sub_department" id="sub_department"></s:hidden>
				<s:hidden name="elUser.isAssign" />
				<s:hidden name="elUser.btotalscore" />
				<s:hidden name="elUser.btotalscore_" />
				<s:hidden name="elUser.bxscore" />
				<s:hidden name="elUser.bxscore_" />
				<s:hidden name="elUser.xxscore" />
				<s:hidden name="elUser.xxscore_" />
				<s:hidden name="elUser.isPass" /> 
				<s:hidden name="DBMethods" /> 
				<s:hidden name="elClasss[0].id"/>
				<s:hidden name="elUser.shengri"/>
				<s:hidden name="elUser.shengri_end"/>
				<!-- 按考场搜索 -->
				<input type="hidden" name="elUser.isKcPass">  
				<input type="hidden" name="examRooms[0].id" value="<s:property value="examRoom.id"/>"> 
				<input type="hidden" name="elUser.KcBtotalscore">  
				<input type="hidden" name="elUser.KcBtotalscore_">  
				<s:iterator value="examPapers">  
						<input type="hidden" name="elUser.epids" value="<s:property value="id"/>"> 
						<input type="hidden" name="elUser.Kcsq">      
						<input type="hidden" name="elUser.Kcsq_">     
						<input type="hidden" name="elUser.Kclxcs">    
						<input type="hidden" name="elUser.Kclxcs_">     
				</s:iterator> 
	  </form>
</HTML>
