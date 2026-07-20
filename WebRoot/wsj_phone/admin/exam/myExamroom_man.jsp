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
		<TITLE>【<s:property value="examRoom.title" />】-监考大厅</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system3.css" />
		<link rel="stylesheet" type="text/css" href="css/manage3.css" />
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
			function select_All(){
				var cks= document.getElementsByName("id");
				for(var i = 0 ; i < cks.length; i++){
					cks[i].checked= true;
				}
			}
			function select_Fan(){
				var cks= document.getElementsByName("id");
				for(var i = 0 ; i < cks.length; i++){
					cks[i].checked= !cks[i].checked;
				}
			}
			function select_No(){
				var cks= document.getElementsByName("id");
				for(var i = 0 ; i < cks.length; i++){
					cks[i].checked= false;
				}
			}
			function doAddTime(){
				//alert("nihao");
				//confirm('确定加时？');
				if(window.confirm("确定加时？")){
					var bool=false;
					var cks= document.getElementsByName("id");
						for(var i = 0 ; i < cks.length; i++){
							if(cks[i].checked==true){
								bool=true;
							}
						}
						if(bool==false){
							alert("请至少选择一个复选框！");
							return false;
						}
					student.submit();
					return true;;
				}
			}
			function doResetEp(){
				//alert("nihao");
				//confirm('确定加时？');
				if(window.confirm("确定重考选中答卷？")){
					var bool=false;
					var cks= document.getElementsByName("id");
						for(var i = 0 ; i < cks.length; i++){
							if(cks[i].checked==true){
								bool=true;
							}
						}
						if(bool==false){
							alert("请至少选择一个复选框！");
							return false;
						}
					student.action="setTesterReinstate.action";
					student.submit();
					return true;;
				}
			}
			function queryUser(){
				document.getElementById("pageNow").value=0;
				student.action="myExamroom_man.action";
				student.submit();
			}
			function disUserInfo(userid){
				width=500;
				height=600;
   				var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				window.showModalDialog("account_view.action?Return=mem&elUser.id="+userid,"",sFeature);
			}
		</script>
		<style type="text/css">
td {
	margin: 0px;
	padding: 2px
}
</style>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="考生列表" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">考场监考</span>
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
			<form action="examroom_addtime.action" method="post" name="student"
				id="user_review">
				<table width="100%" align="center" cellpadding="1" cellspacing="1" style="margin-top:30px;">
					<tr>
						<td>
							考场名称
						</td>
						<td>
							开始时间
						</td>
						<td>
							结束时间
						</td>
						<td width="150">&nbsp;
							
						</td>
					</tr>
					<tr>
						<td>
							<STRONG><s:property value="examRoom.title" /></STRONG>
						</td>
						<td>
							<s:date name="examRoom.begintime" format="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td>
							<s:date name="examRoom.endtime" format="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td>&nbsp;
							
						</td>
					</tr>
					<s:if test="examRoom.pwdneed==1">
						<tr>
							<td>
								<font color="red">密码重置</font>
							</td>
							<td colspan="3">
								<font color="red"><b>当前密码：</b> <s:property
										value="examRoom.pwd" />&nbsp;&nbsp;<b>有效期至:</b> <s:date
										name="examRoom.pwdtime" format="yyyy-MM-dd HH:mm:ss" /> </font>&nbsp;&nbsp;&nbsp;
								密码:
								<input type="text" name="examRoom.pwd" />
								&nbsp;&nbsp;&nbsp; 有效期至:
								<input type="text" name="examRoom.pwdtime"
									onclick="setday(this)" />
								<input type="button" onClick="pwdalter();" class="textbg4"
									style="width: 80px" value="重置密码" />
							</td>
						</tr>
					</s:if>
					<tr>
						<td>
							加时
						</td>
						<td>
							<input type="text" value="<s:property value="course_sourse"/>"
								name="course_sourse" size="4" />
							分钟
							<input type="hidden" name="pN" value="<s:property value="pN"/>" id="pageNow" />
							<input type="hidden" name="examRoom.id"
								value="<s:property value="examRoom.id"/>" />
							<input class="textbg4" type="button" value="确定"
								onClick="return doAddTime();" />
							&nbsp;&nbsp;&nbsp;
							
						</td>
						<td style="color: red;" rowspan="2">
							说明： 1.勾选人员，是为所勾选人员加时.
							<br />
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							2.正数是加时，负数是减时.
						</td>
						<td rowspan="2" align="center">
							<a
								href="myExamroom_man.action?examRoom.id=<s:property value="examRoom.id"/>"
								class="textbg">刷 新</a>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<span>绑定了mac地址：<s:if test="examRoom.isMacBand==1">是</s:if>
								<s:else>否</s:else> </span>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<span>限定了ip地址：<s:if test="examRoom.isIpLimit==1">是</s:if>
								<s:else>否</s:else> </span>
						</td>
					</tr>
					<s:if test="examRoom.isIpLimit==1">
						<tr>
							<td colspan="4">
								<div>
									<div style="float: left">
										<s:iterator id="ipStrat" value="#request.ipStratList"
											status="statu">
											<div id="ipd2_<s:property value="#statu.index"/>">
												开&nbsp;始&nbsp;ip：
												<s:property value='ipStrat' />
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</div>
										</s:iterator>
									</div>
									<div>
										<s:iterator id="ipEnd" value="#request.ipEndList"
											status="statu">
											<div id="ipd3_<s:property value="#statu.index"/>">
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
				</table>
				<div style="text-align: center; margin-top: 0px;">
					帐号：
					<s:textfield name="elUser.username" />
					&nbsp;&nbsp; 姓名：
					<s:textfield name="elUser.realname" />
					&nbsp;&nbsp; 考场状态：
					<s:select theme="simple" headerKey="-2" headerValue="全部"
						name="myroom.status"
						list="#{0:'缺考',1:'未做完',2:'已做完',3:'批阅中',4:'已批阅'}"
						value="myroom.status" />						
					<!-- 增加按时间搜索每场考试的参考人员信息 -->	
					&nbsp;&nbsp;时间：从
						<input name="myroom.begintime" type="text"
							onclick="setday(this)" value="<s:date name="myroom.begintime" format="yyyy-MM-dd hh:mm:ss"/>" />
					      到
						<input name="myroom.endtime" type="text"
					    	onclick="setday(this)" value="<s:date name="myroom.endtime" format="yyyy-MM-dd hh:mm:ss"/>" />
											
					<input type="button" onClick="queryUser();" class="textbg4"
						value="搜索" />
				</div>
				<TABLE width="100%" align="center" cellpadding="1" cellspacing="1">
					<caption>
						【
						<s:property value="examRoom.title" />
						】考场的监考大厅
					</caption>
					<TR>
						<td align="center">
							帐号
						</TD>
						<td align="center">
							姓名
						</TD>
						<s:if test="examRoom.isIpLimit==1">
							<td align="center">
								ip地址
							</td>
						</s:if>
						<s:if test="examRoom.isMacBand==1">
							<td align="center">
								mac地址
							</td>
						</s:if>
						<td align="center" width="150px">
							部门
						</td>
						<td width="60" align="center">
							考场状态
						</TD>
						<td width="30">
							选择
						</td>
						<td>
							答卷
						</td>
						<td width="30">
							已答
						</td>
						<td width="30">
							未答
						</td>
						<td width="40">
							加时
						</td>
						<td width="40">
							分值
						</td>
						<td width="60">
							打字速度
						</td>
						<td width="60">
							考试状态
						</td>
					
					</tr>
					<tbody onMouseOut="changeback()" onMouseOver="changeto()">
						<s:iterator value="myrooms">
							<s:set name="mepsize" value="myExamPapers.size"></s:set>
							<s:if test="#mepsize==0">
								<s:set name="mepsize" value="1"></s:set>
							</s:if>
							<tr>
								<td align="center" rowspan="<s:property value="#mepsize" />">
									<div style="word-wrap: break-word; word-break: break-all;">
											<s:property value="tester.username" />
									</div>
								</td>
								<td align="center" rowspan="<s:property value="#mepsize" />">
									<a
										href="javascript:disUserInfo('<s:property value="tester.id" />');"><s:property
											value="tester.realname" /> </a>
								</td>
								<s:if test="examRoom.isIpLimit==1">
									<td align="center" rowspan="<s:property value="#mepsize" />">
										<s:property value="ipAddress" />
									</td>
								</s:if>
								<s:if test="examRoom.isMacBand==1">
									<td align="center" bgcolor="#FFFFFF"
										rowspan="<s:property value="#mepsize" />"
										style="border-right: 0px solid #fff;">
										<div style="float: left; border: 0px solid red; width: 100px;">
											<s:property value="macAddress" />
										</div>
										<s:if test="macAddress!=null&&macAddress!=''">
											<div
												style="color: red; float: left; border: 0px solid blue; font-size: 18px; cursor: pointer;"
												onClick="delMacAddr('<s:property value="tester.id" />');">
												x
											</div>
										</s:if>
									</td>
								</s:if>
								<td align="center" rowspan="<s:property value="#mepsize" />">
									<div style="word-wrap: break-word; word-break: break-all;">
										<s:property value="tester.department.name" />
									</div>
								</td>
								<td align="center" rowspan="<s:property value="#mepsize" />">
									<s:property value="statusName" />
								</td>
							   <s:if test="myExamPapers.size==0">
									<td align="center" style="padding: 0px;" colspan="11">
										<div style="color: red; text-align: center;">
											该考生还没有进行作答
										</div>
									</td>
									
							</tr>
							</s:if>
							<s:else>
								<s:iterator value="myExamPapers" id="meps">
									<s:if test="#meps.index>1">
										</tr>
										<tr>
									</s:if>
									<td>
										<input type="checkbox" name="myExamPapers.id"
											value="<s:property value="id"/>" id="id" />
										<s:property value="#meps.index" />
									</td>
									<td>
										<s:property value="examPaper.title" />
									</td>
									<td>
										<s:property value="yd" />
									</td>
									<td>
										<s:property value="wd" />
									</td>
									<td>
										<s:property value="jiashi" />
									</td>
									<td>
										<s:property value="myScore" />
									</td>
									<td>
										<s:if test="avgscore<0">无打字题</s:if>
										<s:else>
											<s:property value="avgscore" />
										</s:else>
									</td>
									<td>
										<s:property value="statusName" />
									</td>
								
									
								
									</tr>
								</s:iterator>
							</s:else>
						</s:iterator>
					</tbody>
				</TABLE>
				<script type="text/javascript">
					function setxukao(){
						if(window.confirm("确定续考选中答卷？")){
							student.action="setTesterContinue.action";
							student.submit();
						}
					}
					function setTesterSuspend(){
						student.action="setTesterSuspend.action";
						student.submit();
					}
					function setTesterReinstate(){
						student.action="setTesterReinstate.action";
						student.submit();
					}
					function setTesterSubmit(){
						if(window.confirm("确定强制交卷选中答卷？")){
							student.action="setTesterSubmit.action";
							student.submit();
						}
					}
					function page(i){
						//document.location.href="myExamroom_man.action?examRoom.id=<s:property value="examRoom.id"/>&pN="+i;
						document.getElementById("pageNow").value=i;
						student.action="myExamroom_man.action";
						student.submit();
					}
					function pwdalter(){
						if(window.confirm("确定重置密码？")){
							student.action="eroom_pwdalter.action";
							student.submit();
						}
					}
					function delMacAddr(userid){
						if(window.confirm('确定解除mac绑定?')){
							var url="delMacAddr.action?examRoom.id=<s:property value="examRoom.id"/>&pN=<s:property value="pN"/>&elUser.id="+userid;
							//alert(url);
							document.location.href=url;
						}
						//alert('nihao');
					}
				</script>
				<wysLib:page></wysLib:page>
				<br />
				<br />
				<!--<INPUT type="button" name="pause" onclick=" setTesterSuspend()"
					value="暂停答题">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<INPUT type="button" name="resume" onclick=" setTesterReinstate()"
					value="恢复答题">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<INPUT type="button" name="stop" onclick=" setTesterSubmit()"
					value="强制交卷">-->
			</form>
							<input onClick="javascript:select_All();" type="button"
								class="textbg4" value="全选" />
							<input onClick="javascript:select_No();" type="button"
								class="textbg4" value="全不选" />
						
							<input class="textbg6" type="button" value="重考答卷"
								onClick="return doResetEp();" />
								
							<s:if test="status!=0">
									<input type="button" class="textbg6" name="xukao" onClick="setxukao()" value="续考">
							</s:if>
							
							<input type="button" name="stop" class="textbg6" onClick="setTesterSubmit()" value="强制交卷">
							
							<a	href="myExamroom_list.action"
								class="textbg6">返回监考大厅</a>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
