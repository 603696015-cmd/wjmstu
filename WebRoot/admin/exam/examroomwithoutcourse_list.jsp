<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=utf-8"%>
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
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
		function showtree(flag){
					var tree = document.getElementById("tree_list_td");
					var img = document.getElementById("showimg");
					if(flag){
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(false);" class="textbg5">隐藏考场树</a>';
					}
					else{
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(true);" class="textbg5">显示考场树</a>';
					}
				}
		</script>
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
.textbg4{margin-top:2px;}
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="考场分配考生管理" /></div>
			</li>
			<!--<li class="sep">
			</li>
			<li>
				 <a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="erwithout_addInit.action?course.id=-1">添加考场</a>
			</li> -->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<!--<a href="erwithout_addInit.action?course.id=-1" class="textbg">添加考场</a> -->
			<table cellpadding="1" width="100%" align="left" cellspacing="1">
				<tr>
					<td width="200px" valign="top" id="tree_list_td" style="display:none">
						<wysLib:eroomLibTree
							href="examroomwithoutcourse_list.action?sublibs=1&str=libids&eroomLib.id="
							rootAble="true"></wysLib:eroomLibTree>
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
							<img src="images/leftmenu/main_55_1.gif" style="cursor: hand"
								onclick="changeTreeDisplay(this)" id="showimg " />
						</td>
					<td>
						<form
							action="examroomwithoutcourse_list.action?eroomLib.id=<s:property value="eroomLib.id"/>"
							name="erform" method="post">
							<s:hidden name="pN" id="pageNow">
							</s:hidden>
							<s:hidden name="pS">
							</s:hidden>
							<s:hidden name="libid" value="libid"></s:hidden>
                            <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#D1E4F5">
  <tr>
    <td width="100" rowspan="2" bgcolor="#F8FCFE"><div style="text-align: left;" id="showtree">
									<a href="javascript:showtree(true);" class="textbg5">显示考场树</a>
</div></td>
    <td width="170" bgcolor="#F8FCFE">包含下级节点:
			<input type="checkbox" name="sublibs"
									<s:if test="sublibs==1">checked="checked"</s:if> value="1"></td>
    <td width="380" bgcolor="#F8FCFE">考场标题：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			 <input size="16" type="text" name="examRoom.title"
									value="<s:property value="examRoom.title"/>">
								 状态：&nbsp;
								<s:select theme="simple" headerKey="-1" headerValue="全部"
									list="#{0:'制作中',1:'申请等待中',2:'待修改',3:'审核等待中',4:'审核不通过',5:'已开通',6:'修改等待中',7:'修改中',8:'删除等待中'}"
									name="examRoom.valid" value="examRoom.valid" /></td>
    <td rowspan="2" bgcolor="#F8FCFE"><input onClick="initPN();" type="button" class="textbg4" value="搜索" /></td>
  </tr>
  <tr>
    <td bgcolor="#F8FCFE">类型：<s:select theme="simple"
									list="#{-1:'考核考场',0:'单纯的课程考场',1:'培训班考场',-2:'全部'}"
									name="examRoom.classid" value="examRoom.classid" /></td>
    <td bgcolor="#F8FCFE">开考时间：从
      <input size="16" type="text" onClick=setday(this)
									name="examRoom.begintime"
									value="<s:date name="examroom.begintime" format="yyyy-MM-dd HH:mm"/>
      ">
								&nbsp;到&nbsp;
                                <input size="16" type="text" onClick=setday(this)
									name="examRoom.endtime"
									value="<s:date name="examroom.endtime" format="yyyy-MM-dd HH:mm"/>
            "></td>
    </tr>
</table>
						</form>
<table width="100%" align="center" cellpadding="1" cellspacing="1">
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
										类别库
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
								<!--<th align="center" >
										类型
									</th>-->
								<th width="100" align="center">
									审核状态
								</th>
								<th width="100" align="center">
									复核状态
								</th>
								<th width="80" align="center">
									人数
								</th>
								<!--<th align="center" >
										试卷数
									</th>-->
								<th width="50" align="center">&nbsp;
									
								</th>
							</tr>
							<tbody onMouseOut="changeback()" onMouseOver="changeto()">
								<s:iterator value="examRooms">
									<tr>
										<td	style="padding: 3px 0px 3px 2px;" valign="top" align="left">
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
											<s:property value="eroomLib.name" />
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
										<!--<td align="center" >
											<s:property value="typeName" />
										</td>-->
										<td align="center">
											<font color="<s:if test="valid==1">green</s:if>"> <s:property
													value="validName" />
											</font>
										</td>
										<td align="center">
											<font color="<s:if test="uvalid==1">red</s:if>"> <s:property
													value="uvalidName" />
											</font>
										</td>
										<td align="center">
											参加：<s:property value="usersize" />
											<s:if test="isApplication == 1"><br/>
												<span style="color: red">计划：<s:property
														value="planNumber" /></span>
											</s:if>
										</td>
										<!--<td align="center" >
											<s:property value="epsize" />
										</td>-->
										<td align="left">
											<s:if test="valid == 0 || valid == 2">
												<s:if test="uvalid != 1">
													<a
														href="examroom_assignwcInit.action?examRoom.id=<s:property value="id"/>&course.id=-1&Return=assign"
														class="textbg4">分配</a><br/>
													<%-- <a href="examroom_assigndepInit.action?examRoom.id=<s:property value="id"/>" class="textbg">分配部门</a> --%>
												</s:if>
												<s:else>
													<a
														href="erwithout_view.action?examRoom.id=<s:property value="id"/>&Return=assign"
														class="textbg4">查看</a><br/>
												</s:else>
												<s:if test="Usize == 0">
													<%-- <a href="examroom_sh_p.action?examRoom.id=<s:property value="id"/>&examRoom.valid=1"  onclick="return confirm('确定创建完成？')" class="textbg">创建完成</a>	 --%>
												</s:if>
											</s:if>
											<s:else>
												<a
													href="erwithout_view.action?examRoom.id=<s:property value="id"/>&Return=assign"
													class="textbg4">查看</a><br/>
											</s:else>
										</td>
										<!--<td width="220" align="center" >
												<a href="examroom_sh_p.action?examRoom.id=<s:property value="id"/>&examRoom.valid=1"  onclick="return confirm('确定创建完成？')" class="textbg">审核备注</a>	
												<a href="erwithout_alterInit.action?examRoom.id=<s:property value="id"/>" class="textbg4">编辑</a> 
											<s:if test="valid != 0">
												<s:if test="avalid == 0 ">
													<s:if test="valid != 0">  	 
														<a 	href="examroom_audit.action?examRoom.id=<s:property value="id"/>&examRoom.avalid=1"  onclick="return confirm('确定申请修改？')" class="textbg">申请修改</a>							
													</s:if><s:else>
														<a
															href="examroom_assignwcInit.action?examRoom.id=<s:property value="id"/>" class="textbg">分配学员</a>
														<a
															href="examroom_assigndepInit.action?examRoom.id=<s:property value="id"/>" class="textbg">分配部门</a>
													</s:else> 												 										
												</s:if><s:else>
													<s:if test="avalid == 2">
														<a href="examroom_audit.action?examRoom.id=<s:property value="id"/>&examRoom.avalid=1"  onclick="return confirm('确定申请修改？')" class="textbg">修改未通原因</a>	
													</s:if><s:else>
														<span>已申请修改！</span>	 
													</s:else>
												</s:else>
											</s:if><s:else> 	
											<a
												href="examroom_assignwcInit.action?examRoom.id=<s:property value="id"/>" class="textbg">分配学员</a>
											<a
												href="examroom_assigndepInit.action?examRoom.id=<s:property value="id"/>" class="textbg">分配部门</a>
											</s:else>	
											/<a
												href="examroom_assign_bkInit.action?examRoom.id=<s:property value="id"/>">补考</a>
																				
										</td>-->
									</tr>
								</s:iterator>
							</tbody>
					  </table>
						<div align="center"><wysLib:page></wysLib:page></div>
					</td>
				</tr>
			</table>
		</div>

		<script>
					function page(i){
						document.getElementById("pageNow").value=i;
						erform.submit();
					}
					function initPN(){
						document.getElementById("pageNow").value=0;
						erform.submit();
					}
				</script>
		<!-- 内容 -->
	</BODY>
</HTML>
