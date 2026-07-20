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
		<script type="text/javascript" src="js/libutil.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
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
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="考场列表" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">考场人员复核</span> 
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
			<form action="examroom_validlist.action" method="post" name="examFh">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<div>
					考场标题：&nbsp;
					<input size="16" type="text" name="examRoom.title"
						value="<s:property value="examRoom.title"/>">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 状态：&nbsp;
					<s:select theme="simple" headerKey="-1" headerValue="全部"
						list="#{0:'制作中',1:'申请等待中',2:'待修改',3:'审核等待中',4:'审核不通过',5:'已开通',6:'修改等待中',7:'修改中',8:'删除等待中'}"
						name="examRoom.valid" value="examRoom.valid" />
					开考时间：&nbsp;从
					<input size="16" type="text" onclick=setday(this)
						name="examRoom.begintime"
						value="<s:date name="examRoom.begintime" format="yyyy-MM-dd HH:mm"/>">
					&nbsp;到&nbsp;
					<input size="16" type="text" onclick=setday(this)
						name="examRoom.endtime"
						value="<s:date name="examRoom.endtime" format="yyyy-MM-dd HH:mm"/>">
					类型：<s:select theme="simple"
						list="#{-1:'考核考场',0:'单纯的课程考场',1:'培训班考场',-2:'全部'}"
						name="examRoom.classid" value="examRoom.classid" />
					<input onClick="initPN();" type="button" value="搜索" class="textbg4"/>
				</div>
			</form>
			<s:if test="examRooms.size==0">
				<br>
						没有需要您进行人员复核的考场
			</s:if>
			<s:else>
				<table width="100%" align="left" cellpadding="1" cellspacing="1">
					<tr>
						<th width="300" align="center">
							考场信息
						</th>
						<th width="60" align="center">
							类型
						</th>
						<!--<th align="center" >
							考场地点
						</th>-->
						<!--<th align="center" >
							通过百分比
						</th>-->
						 
						<th width="90" align="center">
							开始时间
						</th>
						<th width="90" align="center">
							结束时间
						</th>
						<!--<th width="70" align="center" >
							考场类型						</th> -->
						<th width="90" align="center">
							考场状态
						</th>
						<th width="90" align="center">
							复核状态
						</th>
						<th width="120" align="center">
							人数
						</th>
						<th width="120" align="center">&nbsp;
							
						</th>

						<!--<th align="center" >
										&nbsp;
									</th>
								-->
					</tr>
					<tbody onMouseOut="changeback()" onMouseOver="changeto()">
						<s:iterator value="examRooms">
							<tr>
								<!--<td align="center" >
								<input type="checkbox" name="delete_item[]" value="5">
							</td>
							-->
							    <td style="padding: 3px 0px 3px 2px;" valign="top" align="left">
											<div
												style="word-wrap: break-word; word-break: break-all; width: 100%;">
												<strong style="font-size:15px;color: blue;"><s:property
														value="title" /> </strong>
												<br />
												<strong>类别:</strong>
												<s:property value="eroomLib.name" />
												<br />
												<strong>组织单位:</strong>
												<s:property value="depName" />
												<br />
												<strong>组织工钟:</strong>
												<s:property value="jingzhong" />
												<br />
												<strong>创建者:</strong>
												<s:property value="creater.realname" /><br/>
												<s:if test="examRoom.classid!=-1">
													<strong> 所属课程: </strong><s:property value="course.name" />
												</s:if>
											</div>
								</td>
								<td align="center">
									<s:if test="isApplication == 1">
										<SPAN style="color: red">【申请】</SPAN>
									</s:if>
									<s:elseif test="isApplication == 2">
										<SPAN style="color: blue;">【全工】</SPAN>
									</s:elseif>
									<s:else>
										<SPAN style="color: gray">【分配】</SPAN>
									</s:else>
								</td>
							 
								<!--<td align="center" >
								<s:property value="location" />
							</td>-->
								 
								<!--<td align="center" >
								<s:property value="passgrade" />
							</td>-->
								 
								<td align="center">
									<s:date name="begintime" format="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td align="center">
									<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss" />
								</td>
								<!--<td width="70" align="center" >
						  <s:property value="typeName" />						  </td> -->
								<td align="center">
									<s:property value="validName" />
								</td>
								<td align="center">
									<font color="<s:if test="uvalid==1">red</s:if>"> <s:property
											value="uvalidName" /> </font>
								</td>
								<td align="center">
									参加：<s:property value="usersize" />
									<s:if test="isApplication == 1"><br/>
										<span style="color: red">计划：<s:property
												value="planNumber" /> </span>
									</s:if>
								</td>
								<td width="220" align="left">
									<s:if test="uvalid == 0">
										<a
											href="examroom_validuserlist.action?examRoom.id=<s:property value="id"/>"
											class="textbg">考生详情</a><br/>
										<a
											href="examroom_valid.action?examRoom.id=<s:property value="id"/>"
											class="textbg">复核通过</a>
									</s:if>
									<s:else>
										<a
											href="examroom_validview.action?examRoom.id=<s:property value="id"/>"
											class="textbg">查看考生</a>
										<s:if test="valid == 0 || valid == 2">
											<!-- 分配式 -->
											<s:if test="type == 0">
												<a style="cursor: pointer;"
													onClick="sh(<s:property value="id"/>, 1);" class="textbg">创建完成</a>
												<a
													href="CRE_notelistInit.action?examRoom.id=<s:property value="id"/>&Return=examroom_validlist"
													target="_blank" class="textbg4">备 注</a>
											</s:if>
										</s:if>
										<s:if test="svalid == 0 || svalid == 2">
											<!-- 选拨式 -->
											<s:if test="type == 1">
												<a
													href="CRE_notelistInit.action?examRoom.id=<s:property value="id"/>&Return=examroom_validlist"
													class="textbg4">备 注</a>
												<a style="cursor: pointer;"
													onClick="sh_s(<s:property value="id"/>, 1);" class="textbg">创建完成</a>
											</s:if>
										</s:if>
									</s:else>
									<!--/<a
												href="examroom_assign_bkInit.action?examRoom.id=<s:property value="id"/>">补考</a>
										-->
								</td>
								<!--<td align="center" >
											<a
												href="exampaperreadlist.action?examRoom.id=<s:property value="id"/>">阅卷</a>
										</td>
									-->
							</tr>
						</s:iterator>
					</tbody>
				</table>
				<form action="examroom_sh_p.action" name="examroom_sh_p"
					method="post">
					<s:hidden name="examRoom.id" id="examRoom.id"></s:hidden>
					<s:hidden name="examRoom.valid" id="examRoomValid"></s:hidden>
					<s:hidden name="examRoom.svalid" id="examRoom.svalid"></s:hidden>
					<s:hidden name="Return" id="Return" value="examroom_validlist"></s:hidden>
				</form>
			</s:else>
			<script> 
					function sh(id,valid){
					    document.getElementById("examRoom.id").value=id;
					    document.getElementById("examRoomValid").value=valid; 
					 	if(valid==1 && window.confirm("确定创建完成？")){
							if(FillInNoteksInit(id)){ 
					 		document.forms.examroom_sh_p.submit(); 
					 		}
					 	}
					}
					function sh_s(id,svalid){ 
					    document.getElementById("examRoom.id").value=id;
					    document.getElementById("examRoom.svalid").value=svalid; 
					 	if(svalid==1 && window.confirm("确定创建完成？")){
							if(FillInNoteksInit(id)){  
					 		examroom_sh_p.action="examroom_sh_s.action"; 
					 		document.forms.examroom_sh_p.submit();
					 		}
					 	}
					}
					function FillInNoteksInit(id){ 
					     width=600;
						 height=500;  
					  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
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
					function page(i){
						document.getElementById("pageNow").value=i;
						examFh.submit();
						//document.location.href="examroom_validlist.action?pS=<s:property value="pS"/>&pN="+i
					}
					function initPN(){
						document.getElementById("pageNow").value=0;
						examFh.submit();
					}
				</script>
			<div style="clear:both;"><wysLib:page></wysLib:page></div>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
