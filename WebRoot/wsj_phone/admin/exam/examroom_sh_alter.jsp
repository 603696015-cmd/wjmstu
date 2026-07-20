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
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/assist.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/libutil.js"></script>
		<script type="text/javascript">
		function deleteUserinfo(obj,id,optype){
			if(window.confirm("确定删除？")){
			erid = <s:property value="examRoom.id"/> ;
			$.post("eroom_delete_user.action", {
				"elUser.id":id,
				"examRoom.id":erid,
				"optype":optype, 
				"x":Math.random
				}, 
				function (data) {
					alert('删除成功');
				});
			obj.parentNode.parentNode.removeChild(obj.parentNode);
			}
		}
		function deleteEps(obj,id ){
			if(window.confirm("确定删除？")){
			erid = <s:property value="examRoom.id"/> ;
			$.post("eroom_delete_ep.action", {
				"examPaper.id":id,
				"examRoom.id":erid,
				"x":Math.random
				}, 
				function (data) {
					alert('删除成功');
				});
			obj.parentNode.parentNode.removeChild(obj.parentNode);
			}
		}
			function _onsubmit(){
				if($("#eroom_title").val()==''){
					alert("标题不要为空");
					$("#eroom_title").focus();
					return false; 
				}if($("#eroom_begintime").val()==''){
					alert("开始时间不要为空");
					$("#eroom_begintime").focus();
					return false; 
				}if($("#eroom_endtime").val()==''){
					alert("结束时间不要为空");
					$("#eroom_endtime").focus();
					return false; 
				}
				if($("#epid").val()==''){
					alert("请选择试卷");
					$("#epid").focus();
					return false; 
				}
				
				return true;
			}
		</script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="提交申请" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">审核考场查看</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<span style="color: #ff0000;"></span>
			<table width="90%" align="center" cellpadding="1" cellspacing="1"
				bgcolor="#EBEBEB">
				<tr>
					<td width="160" height="30" align="center" >
						考场标题
					</td>
					<td >
						<label>
							<s:property value="examRoom.title" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="160" align="center" >
						所属试卷库
					</td>
					<td bgcolor="#FFFFFF" colspan="3">
						<label>
							${examRoom.eroomLib.name}

						</label>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center" >
						考场说明
					</td>
					<td >
						<label>
							<s:property value="examRoom.description" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center" >
						考试地点
					</td>
					<td >
						<label>
							<s:property value="examRoom.location" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center" >
						监考人员
					</td>
					<td >
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
				<tr>
					<td width="160" height="30" align="center" >
						阅卷人员
					</td>
					<td >
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
				<tr>
					<td width="160" height="30" align="center" >
						复核人员
					</td>
					<td >
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
					<td width="160" height="30" align="center" >
						考试时间
					</td>
					<td >
						<label>
							考场开始时间
							<s:date name="examRoom.begintime" format="yyyy-MM-dd HH:mm:ss" />
						</label>
						<br />
						<label>
							考场结束时间
							<s:date name="examRoom.endtime" format="yyyy-MM-dd HH:mm:ss" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center" >
						考场类型
					</td>
					<td >
						<label>
							<s:property value="examRoom.typeName" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center" >
						通过成绩：
					</td>
					<td >
						<label>
							<s:property value="examRoom.passgrade" />
							%
						</label>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center" >
						学分
					</td>
					<td >
						<label>
							<s:property value="examRoom.score" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center" >
						所用试卷
					</td>
					<td >
						<div id="eps_div">
							<s:iterator value="examRoom.exampapers" status="epsst">
								<div>
									<span style="width: 150"> <s:property value="title" />
									</span>
									<span style="width: 60"><s:if test="shouldpass==1">需达标</s:if>
										<s:if test="shouldpass==0">无需达标</s:if> </span>
									<lable name="prac">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<span style='color: red'>练习</span>：
									<span style="width: 150px;"><s:property
											value="prac.title" /> </span> 次数：
									<s:property value="practimes" />
									最低分
									<s:property value="pracscore" />
									</lable>
								</div>
							</s:iterator>
						</div>
					</td>
				</tr>
				<tr>
					<td width="160" height="50" align="center" >
						考生列表
					</td>
					<td >
						<s:if test="myrooms.size==0">
							<br>
						暂无考生
						</s:if>
						<s:else>
							<table width="96%" align="center" cellspacing="1" cellpadding="1">
								<caption>
									考生列表
								</caption>
								<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
										姓名/账号
									</td>
									<td height="30" align="center" >
										部门
									</td>
									<td height="30" align="center" >
										试卷数量
									</td>
								</tr>
								<s:iterator value="myrooms">
									<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
											<s:property value="tester.realname" />
											/
											<s:property value="tester.username" />
										</td>
										<td height="30" align="center" >
											<s:property value="tester.department.name" />
										</td>
										<td height="30" align="center" >
											<s:property value="epsize" />
										</td>
									</tr>
								</s:iterator>
							</table>
							<wysLib:page></wysLib:page>
						</s:else>
					</td>
				</tr> 
			</table>
						<script type="text/javascript">
								function sh_p(){
									if(window.confirm("确定让它通过审核？"))
										sh.action="examroom_sh_p.action?examRoom.valid=0";
										sh.submit(); 
								}
								function sh_np(){
									if(window.confirm("确定让它不通过审核？"))
										sh.action="examroom_sh_p.action?examRoom.valid=<s:property value="examRoom.avalid" />";
										sh.submit();
										
								}
								function page(i){
										document.location.href="examroom_sh_view.action?examRoom.id=<s:property value="examRoom.id"/>&pS=<s:property value="pS"/>&pN="+i
								}
							</script>
	<s:form action="examroom_sh_p" method="post" theme="simple" name="sh">  
			<table width="90%" align="center" cellpadding="1" cellspacing="1" bgcolor="#EBEBEB">
			<!-- <tr>
				<td width="160"  align="center" >
					审核标题
				</td>
				<td > 
					<s:textarea name="erAuditdes.title" cols="30" rows="1"  ></s:textarea> 
				</td>
			</tr>    -->
			<tr>
				<td width="160"  align="center" >
					审核详情
				</td>
				<td > 
					<s:if test="erAuditdes.content != null">
						<textarea name="erAuditdes.content" cols="60" rows="7" disabled="disabled">申请人员：<s:property value="erAuditdes.content"/></textarea> 
					</s:if>
					<s:textarea name="erAuditdes.replycontent" cols="60" rows="7"></s:textarea> 
					<s:hidden name="examRoom.id" />   
				</td>
			</tr>  
			<tr>
				<td width="160"  align="center" > 
				</td>
				<td width="160"  align="center"  >
				<s:if test="examRoom.valid != 7"> 
				<input style="height:35px;" class="textbg6" type="button" name="button2" onClick="sh_p();" id="button2" value="通过审核" />
				<input style="height:35px;" class="textbg6" type="button" name="button2" onClick="sh_np();" id="button2" value="不通过审核" />
				</s:if><s:else>
					<span style="color:red;">申请修改审核已批阅</span>
				</s:else>
				</td>
			</tr>   
			</table> 
		</s:form>
			<br />
			<br /> 
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
