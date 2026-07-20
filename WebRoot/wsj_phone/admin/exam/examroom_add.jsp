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
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/libutil.js"></script>
		<script type="text/javascript">
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
				return true;
			}
			
			$(document).ready(function(){
				var nihao=$("input[nihao=radio]");
				nihao[0].checked=true;
			});
			
			function erep_del(obj,id ){
				if(window.confirm("确定删除？")){
				 epcount--
				obj.parentNode.parentNode.removeChild(obj.parentNode);
				}
			}
			
			var ipcount=0;
			function ipd_addinit(){
				//alert("nihao");
				var ipd =  document.createElement("div");
				ipd.id ="ipd_"+ipcount;
				var ipdStr="<div><span width='160' height='30' align='center' bgcolor='#FFFFFF'>开&nbsp;始&nbsp;ip：</span><span bgcolor='#FFFFFF'><input name='ipStart' type='text'></span>"
				+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span width='160' height='30' align='center' bgcolor='#FFFFFF'>结&nbsp;束&nbsp;ip：</span><span bgcolor='#FFFFFF'><input name='ipEnd' type='text'></span>"
				+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='return delDiv("+ipcount+");' href=''>删除</a></div>";
				ipd.innerHTML= ipdStr;
				ipcount++;
				document.getElementById("ipd_div").appendChild(ipd);
				return false;
			}
			
			function delDiv(ipcount){
				//alert(id);
				var ipObj=document.getElementById("ipd_"+ipcount);
				ipObj.parentNode.removeChild(ipObj);
				return false;
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="填写基本信息" /></div>
			</li>
			<!--<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="examroom_listbyc.action?course.id=<s:property value="course.id"/>">考试考场管理</a>
			</li>
			<li class="sep">
			</li>
			<li>
				<span style="font-weight: bold;">添加考场</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<label style="font-size: 16px;">
				为课程
			<b><s:property value="course.name" />
</b> 添加考场
			</label>
			<br>
			<form id="form_exam_add" name="form_exam_add" method="post"
				action="examroom_add.action" onSubmit="return _onsubmit();">
				<span style="color: #ff0000;"></span>
				<table width="90%" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr>
						<td width="160" height="30" align="center" >
							考场标题
						</td>
						<td >
							<label>
								<input name="examRoom.title" type="text" id="eroom_title" value=""
									size="60" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="100" align="center" >
							所属考场库
						</td>
						<td bgcolor="#FFFFFF" colspan="3">
							<label>
								<select name="examRoom.eroomLib.id" id="er_erlid">
									<wysLib:eroomLibSelect selectid="${examRoom.eroomLib.id}"></wysLib:eroomLibSelect>
								</select>
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							考场说明
						</td>
						<td >
							<label>
								<textarea name="examRoom.description" cols="60" rows="7"></textarea>
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							考试地点
						</td>
						<td >
							<label>
								<input name="examRoom.location" type="text" id="textfield"
									value="" size="60" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="100"  align="center" >
							监考人员
						</td>
						<td >
							<div id="invigilators">
							</div>
							<a href=""
								onclick="searchUserInit('invigilators','examRoom.invigilators.id'); return false;">添加</a>
						</td>
					</tr>
					<tr>
						<td width="100"  align="center" >
							阅卷人员
						</td>
						<td >
							<div id="appraises">
							</div>
							<a href=""
								onclick="searchUserInit('appraises','examRoom.appraises.id'); return false;">添加</a>
						</td>
					</tr>
					<tr>
						<td width="100"  align="center" >
							复核人员
						</td>
						<td >
							<div id="valids">
							</div>
							<a href=""
								onclick="searchUserInit('valids','examRoom.valids.id'); return false;">添加</a>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							通过成绩：
						</td>
						<td >
							<label>
								<input type="text" style="width: 40px;" name="examRoom.passgrade"
									id="passgrade" value="">
								%
							</label>
						</td>
					</tr>
					<!-- <tr>
						<td width="160" height="30" align="center" >
							学分 
						</td>
						<td >
							<label>
								<input type="text" readonly="readonly" value="<s:property value="course.credit"/>" style="width: 40px;border: none" name="examRoom.score">
							</label>
						</td>
					</tr> -->
					<tr>
						<td width="160" height="30" align="center" >
							考试时间
						</td>
						<td >
							<label>
								考场开始时间
								<input class="Wdate" name="examRoom.begintime" type="text"
									onclick="setday(this)" id="eroom_begintime"/>
							</label>
							<br />
							<label>
								考场结束时间
								<input class="Wdate" name="examRoom.endtime" type="text"
									onclick="setday(this)" id="eroom_endtime" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							绑定mac地址：
						</td>
						<td >
								<input name="examRoom.isMacBand" type="radio" value="1">是
								<input name="examRoom.isMacBand" type="radio" value="0" checked="checked">否	
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							限&nbsp;定&nbsp;ip&nbsp;段：
						</td>
						<td >
								<input name="examRoom.isIpLimit" type="radio" value="1">是
								<input name="examRoom.isIpLimit" type="radio" value="0" checked="checked">否	
						</td>
					</tr>
					<!-- 
					<tr>
						<td width="160" height="30" align="center" >
							开&nbsp;始&nbsp;ip：
						</td>
						<td >
								<input name="examRoom.ipStart" type="text">
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							结&nbsp;束&nbsp;ip：
						</td>
						<td >
								<input name="examRoom.ipEnd" type="text">
						</td>
					</tr>
					 -->
					<tr>
					  <td colspan="2">
					  	<div id="ipd_div"></div>
						<a href="" onClick="return ipd_addinit();" class="textbg">添加ip段</a>
					  </td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							所用试卷
						</td>
						<td bgcolor="#FFFFFF" style="padding: 0px;">
							<s:if test="quizPapers.size==0">
								<span style="color: #ff0000;">课程还没有试卷,无法添加考场。</span>
							</s:if>
							<s:else>
								<table width="100%" align="center" cellpadding="1"
									cellspacing="1" bgcolor="#EBEBEB">
									<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">&nbsp;
											
										</td>
										<td height="30" align="center" >
											试卷标题
										</td>
										<td height="30" align="center" >
											试卷时长
										</td>
										<td height="30" align="center" >
											出题方式
										</td>
										<td height="30" align="center" >
											创建时间
										</td>
									</tr>
									<s:iterator value="quizPapers">
										<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
												<!--<input type="checkbox" name="examPapers.id"
													value="<s:property value="examPaper.id"/>">
											-->
											<input nihao=radio type="radio" name="examPapers.id"
													value="<s:property value="examPaper.id" />">
											</td>
											<td height="30" align="center" >
												<s:property value="examPaper.title" />
											</td>
											<td height="30" align="center" >
												<s:property value="examPaper.during" />
											</td>
											<td height="30" align="center" >
												<s:if test="examPaper.random">随机</s:if>
												<s:else>手工</s:else>
											</td>
											<td height="30" align="center" >
												<s:date name="examPaper.createtime"
													format="yyyy-MM-dd HH:mm:ss" />
											</td>
										</tr>
									</s:iterator>
								</table>

							</s:else>
							<br>
						</td>
					</tr>
					<tr> 
						<td width="160" height="50" align="center" >
							&nbsp;
							<s:hidden name="course.id"></s:hidden> 
							<s:hidden name="choose"></s:hidden>
						</td>
						<td >
							<input type="submit" name="button2" id="button2" value="确认添加" />
						</td>
					</tr>
				</table>
			</form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
