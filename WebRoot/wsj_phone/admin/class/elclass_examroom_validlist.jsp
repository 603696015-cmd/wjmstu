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
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}

.textbg4 {
	margin-top: 2px;
}

.textbg6 {
	margin-top: 2px;
}
</style>
		<script type="text/javascript" src="js/libutil.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
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
			    document.getElementById("examRoomSvalid").value=svalid; 
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
				 var rv =  window.showModalDialog("CRE_addNotes.action?elclass.id="+id+"&Return=elclass_primash_list&x="+Math.random(),null,sFeature); 
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
				document.location.href="elclass_examroom_validlist.action?pS=<s:property value="pS"/>&pN="+i
			} 
			function initPN(){
				document.getElementById("pageNow").value=0;
				examFh.submit();
			}		
		</script>
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="考场列表页" />
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
			<form action="elclass_examroom_validlist.action" method="post"
				name="examFh">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<s:hidden name="examRoom.classid" value="1" />
				<div>
					<table width="100%" align="center" cellspacing="1" cellpadding="1">
						<tr>
							<td>
								培训班名称:
								<input type="text" name="examRoom.elclass.name"
									value="<s:property value="examRoom.elclass.name"/>">
							</td>
							<td>
								课程名称:
								<input type="text" name="examRoom.course.name"
									value="<s:property value="examRoom.course.name"/>">
							</td>
							<td>
								考场名称:
								<input type="text" name="examRoom.title"
									value="<s:property value="examRoom.title"/>">
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td>
								考场类型:
								<s:select theme="simple" headerValue="全部" headerKey="-1"
									list="#{0:'分配式',1:'选拨式'}" name="examRoom.type"
									value="examRoom.type" />
							</td>
							<td>
								状态:
								<s:select theme="simple" headerValue="全部" headerKey="-1"
									list="#{0:'制作中',1:'申请等待中',2:'待修改',3:'审核等待中',4:'审核不通过',5:'已开通',6:'修改等待中',7:'修改中',8:'删除等待中',9:'已删除'}"
									name="examRoom.valid" value="examRoom.valid" />
							</td>
							<td colspan="1">
								时间段范围&nbsp;从
								<input type="text" onclick=setday(this)
									name="examRoom.begintime"
									value="<s:date name="examRoom.begintime" format="yyyy-MM-dd HH:mm"/>">
								&nbsp;到&nbsp;
								<input type="text" onclick=setday(this) name="examRoom.endtime"
									value="<s:date name="examRoom.endtime" format="yyyy-MM-dd HH:mm"/>">
							</td>
							<td>
								<input onClick="initPN();" class="textbg4" type="button" value="搜索" />
							</td>
						</tr>
					</table>
				</div>
			</form>
			<s:if test="examRooms.size==0">
				<br>没有需要您进行人员复核的考场
			</s:if>
			<s:else>
				<table width="100%" align="center" cellspacing="1" cellpadding="1">
					<tr>
						<th width="300" height="30" align="center">
							考场信息
						</th>
						<!--<th height="30" align="center" >
							考场地点
						</th>-->
						<th width="80" height="50" align="center">
							培训班
						</th>
						<!--<th height="30" align="center" >
							通过百分比
						</th>-->
						<th width="110" height="30" align="center">
							开始时间
						</th>
						<th width="110" height="30" align="center">
							结束时间
						</th>
						<th width="70" height="30" align="center">
							考场类型
						</th>
						<th width="90" height="30" align="center">
							考场状态
						</th>
						<th width="90" height="30" align="center">
							复核状态
						</th>
						<th width="110" height="30" align="center">
							&nbsp;
						</th>
						<!--<th height="30" align="center" >
										&nbsp;
									</th>
								-->
					</tr>
					<tbody onMouseOut="changeback()" onMouseOver="changeto()">
						<s:iterator value="examRooms">
							<tr>
								<td style="padding: 3px 0px 3px 2px;" valign="top" align="left">
									<div
										style="word-wrap: break-word; word-break: break-all; width: 100%;">
										<strong style="font-size: 15px; color: blue;"><s:property
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
										<s:property value="creater.realname" />
										<br />
										<s:if test="examRoom.classid!=-1">
											<strong> 所属课程: </strong>
											<s:property value="course.name" />
										</s:if>
									</div>
								</td>
								<td height="30" align="center">
									<s:property value="elclass.name" />
								</td>
								<!--<td height="30" align="center" >
								<s:property value="passgrade" />
							</td>-->
								<td height="30" align="center">
									<s:date name="begintime" format="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td height="30" align="center">
									<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td height="30" align="center">
									<s:property value="typeName" />
								</td>
								<td height="30" align="center">
									<s:property value="validName" />
								</td>
								<td height="30" align="center">
									<font color="<s:if test="uvalid==1">red</s:if>"> <s:property
											value="uvalidName" /> </font>
								</td>
								<td height="30" align="center">
									<s:if test="uvalid == 0">
										<a
											href="examroom_assignwcInit.action?examRoom.id=<s:property value="id"/>&course.id=<s:property value="course.id"/>&course.classid=<s:property value="elclass.id"/>&Return=clauvalid"
											class="textbg">考生详情</a>
										<a
											href="examroom_valid.action?examRoom.id=<s:property value="id"/>&Return=elclass_examroom_validlist&examRoom.isUserid=<s:property value="Userid"/>"
											class="textbg">复核通过</a>

									</s:if>
									<s:else>
										<a
											href="examroom_validview.action?examRoom.id=<s:property value="id"/>&Return=clauvalid"
											class="textbg">查看考生</a>
										<s:if test="valid == 0 || valid == 2">
											<s:if test="type == 0">
												<a style="cursor: pointer;"
													onClick="sh(<s:property value="id"/>, 1);" class="textbg">创建完成</a>
												<a
													href="CRE_notelistInit.action?elclass.id=<s:property value="elclass.id"/>&course.id=<s:property value="course.id"/>&examRoom.id=<s:property value="id"/>&Return=elclass_examroom_validlist"
													target="_blank" class="textbg4">备 注</a>
											</s:if>
										</s:if>
										<s:if test="svalid == 0 || svalid == 2">
											<s:if test="type == 1">
												<a style="cursor: pointer;"
													onClick="sh_s(<s:property value="id"/>, 1);" class="textbg">创建完成</a>
												<a
													href="CRE_notelistInit.action?elclass.id=<s:property value="elclass.id"/>&course.id=<s:property value="course.id"/>&examRoom.id=<s:property value="id"/>&Return=elclass_examroom_validlist"
													target="_blank" class="textbg4">备 注</a>
											</s:if>
										</s:if>
									</s:else>
									<!--/<a
												href="examroom_assign_bkInit.action?examRoom.id=<s:property value="id"/>">补考</a>
										-->
								</td>
								<!--<td height="30" align="center" >
											<a
												href="exampaperreadlist.action?examRoom.id=<s:property value="id"/>">阅卷</a>
										</td>
									-->
								<s:hidden></s:hidden>
							</tr>
						</s:iterator>
					</tbody>
				</table>
				<form action="examroom_sh_p.action" name="examroom_sh_p"
					method="post">
					<s:hidden name="examRoom.id" id="examRoom.id"></s:hidden>
					<s:hidden name="examRoom.valid" id="examRoomValid"></s:hidden>
					<s:hidden name="examRoom.svalid" id="examRoomSvalid"></s:hidden>
					<s:hidden name="Return" id="Return"
						value="elclass_examroom_validlist"></s:hidden>
				</form>

				<wysLib:page></wysLib:page>
			</s:else>

		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
