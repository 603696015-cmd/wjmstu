<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.batchman.entities.Batch"%>
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
		<base  href="<%=basePath%>"/>
		<TITLE>培训班统计表</TITLE>
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
	background-color: expression((       this .       sectionRowIndex %       2 ==       0)
		?   
		   "#ffffff" :       "#f4f4f4" )
}
</style>
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/tree/depuserlist.js"></script>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="学习排行榜" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<script type="text/javascript">
			function toexcel(){     
				assignSearch_assignment.action = "stat_class_batch_view.action?export=true";
				assignSearch_assignment.submit();
			}
			function view(){     
				assignSearch_assignment.action = "stat_class_batch_view.action";
				assignSearch_assignment.submit();
			}						
		</script>
		<div style="margin-top: 0px; text-align: center;">
			<s:form action="stat_class_batch_view" method="post"
				name="assignSearch_assignment" theme="simple">
				<s:hidden name="batch.id" />
				<!--<s:hidden name="examRoom.id" />
				<s:hidden name="examPaper.id" />-->
				<table width="100%" align="center" cellpadding="1" cellspacing="1">
					<tr>
						<td width="120" align="center">
							批次名称
						</td>
						<td width="35%" align="center">
							<s:property value="batch.name" />
						</td>
						<td width="120" align="center">
							创建人
						</td>
						<td width="35%" align="center">
							<s:property value="batch.creater.realname" />
						</td>
					</tr>
					<tr>
						<td background="#ffffff">
							包含的培训班
						</td>
						<td align="center" colspan="3">
							<table width="100%" style="margin: 0px;" align="center"
								cellpadding="1" cellspacing="1">
								<tr>
									<td height="20" align="center">
										培训班名称
									</td>
									<td height="20" align="center">
										开始时间
									</td>
									<td height="20" width="150px" align="center">
										结束时间
									</td>
								</tr>
								<s:iterator value="batch.classes">
									<tr>
										<td height="20" align="center">
											<s:property value="name" />
										</td>
										<td height="20" align="center">
											<s:date name="starttime" format="yyyy-MM-dd HH:mm:ss" />
										</td>
										<td height="20" align="center">
											<s:date name="finishtime" format="yyyy-MM-dd HH:mm:ss" />
										</td>
									</tr>
								</s:iterator>
							</table>
						</td>
					</tr>
				</table>
				<table width="100%" cellpadding="1" cellspacing="1">
					<tr>
						<td>
							性别：
						</td>
						<td>
							<select name="elUser.sex">
								<option value="">
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
						<td>
							是否已结业
						</td>
						<td>
							<select name="elUser.isAssign">
								<option value="">
									全部
								</option>
								<option value="1"
									<s:if test="elUser.isAssign==\"1\"">selected='selected'</s:if>>
									是
								</option>
								<option value="0"
									<s:if test="elUser.isAssign==\"0\"">selected='selected'</s:if>>
									否
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							姓名：
						</td>
						<td>
							<s:textfield name="elUser.realname" id="elUser.realname" />
						</td>
						<td>
							账号：
						</td>
						<td>
							<s:textfield name="elUser.username" id="elUser.username" />
						</td>
						<td>
							<wysLib:BasetName btid="1" />
							：
						</td>
						<td>
							<!-- 
							<select name="elUser.jingzhong">
								<option value="" selected="selected">
									全部
					 			</option>
								<s:iterator value="elUser.jingzhongs" status="jzs">
									<option
										<s:if test="elUser.jingzhong==elUser.jingzhongs[#jzs.index]">selected = 'selected'</s:if>
										value="<s:property />">
										<s:property />
									</option>
								</s:iterator>
								<wysLib:BasetName btid="1" />
							</select>
							 -->
							<label>
								<s:select theme="simple" name="elUser.jingzhong"
									cssClass="g-select" list="jingzhongs" listKey="id"
									listValue="basevalue" headerValue="全部" headerKey="0" />
							</label>
						</td>
					</tr>
					<tr>
						<td>
							年龄段开始时间:
						</td>
						<td>
							<input type="text" size="16" name="elUser.shengri"
								onclick="setday(this)" readonly="readonly"
								value="<s:date name="elUser.shengri" format="yyyy-MM-dd HH:mm:ss"/>">
						</td>
						<td>
							年龄段结束时间:
						</td>
						<td>
							<input type="text" size="16" name="elUser.shengri_end"
								onclick="setday(this)" readonly="readonly"
								value="<s:date name="elUser.shengri_end" format="yyyy-MM-dd HH:mm:ss"/>">
						</td>
						<td colspan="2">
							<input type="button" class="textbg4" value="搜索" onClick="view()">
							&nbsp;&nbsp;
							<s:property value="deptid.id" />
							<input type="button" class="textbg4" value="导出"
								onClick="toexcel()">
						</td>
					</tr>
				</table>
			</s:form>
			<table width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<td valign="top" width="150px;">
						<%
							Batch elclass = (Batch) request.getAttribute("batch");
							String url = "stat_class_batch_view.action?batch.id="
									+ elclass.getId() + "&elUser.id=0&department.id=";
						%>
						<wysLib:dep_list_aj rootAble="true" href="<%=url%>"></wysLib:dep_list_aj>
						<script type="text/javascript">
							w0.setValues([new DEP(<s:property value="department.id"/>,<s:property value="department.lid"/>,<s:property value="department.rid"/>)]);
						</script>
					</td>
					<td valign="top" align="left">
						<s:if test="elusers.size==0">尚无证书</s:if>
						<s:else>
							<table style="margin-top: 0px;" width="100%" align="center"
								cellpadding="1" cellspacing="1" bgcolor="#EBEBEB">
								<tr>
									<th height="30" style="padding-left: 8px; color: blue;"
										align="left">
										姓名
									</th>
									<th height="30" align="center">
										性别
									</th>
									<th height="30" align="center">
										账号
									</th>
									<th height="30" align="center">
										部门
									</th>
									<th height="30" align="center">
										<wysLib:BasetName btid="1" />
									</th>
									<th height="30" align="center">
										年龄
									</th>

									<th height="30" align="center">
										学分
									</th>
									<th height="30" align="center">
										必修学分
									</th>
									<th height="30" align="center">
										选修学分
									</th>

									<th height="30" align="center">
										学习详情
									</th>
									<th height="30" align="center">
										学习轨迹
									</th>
									<th height="30" align="center">
										获证时间
									</th>
								</tr>
								<s:if test="elusers.size==0">
									<TR>
										<TD align="center" colspan="4">
											尚无证书
										</TD>
									</TR>
								</s:if>
								<s:else>
									<s:iterator value="elusers">
										<tr>
											<td height="30" style="padding-left: 8px; color: blue;"
												align="left">
												<s:property value="realname" />
											</td>
											<td height="30" align="center">
												<s:property value="sex" />
											</td>
											<td height="30" align="center">
												<s:property value="username" />
											</td>
											<td height="30" align="center">
												<s:property value="department.name" />
											</td>
											<td height="30" align="center">
												<s:property value="jingzhong_" />
											</td>
											<td height="30" align="center">
												<s:property value="age" />
											</td>
											<td height="30" align="center">
												<s:property value="xx_time" />
											</td>
											<td height="30" align="center">
												<s:property value="ct_credit" />
											</td>
											<td height="30" align="center">
												<s:property value="xx_credit" />
											</td>
											<td height="30" align="center">
												<a
													href="dep_classstudy_view.action?elclass.id=<s:property value="isLeader"/>&elUser.id=<s:property value="id"/>&Return=stat_class_batch_view.action?batch.id=<s:property value="batch.id"/>"
													class=textbg4>查 看</a>
											</td>
											<td height="30" align="center">
												<a class="textbg4"
													href="statisticStudyLearnLocus.action?elUser.id=<s:property value="id" />&course.classid=<s:property value="isLeader"/>&Return=stat_class_batch_view.action?batch.id=<s:property value="batch.id"/>">查看</a>
											</td>
											<td height="30" align="center">
												<s:if test="graddate == null">尚无证书</s:if>
												<s:else>
													<SPAN style="color: red"><s:date format="yyyy-MM-dd"
															name="graddate" /> </SPAN>
												</s:else>
											</td>
										</tr>
									</s:iterator>
								</s:else>
							</table>
						</s:else>
					</td>
				</tr>
			</table>
			<wysLib:page></wysLib:page>
			<a href="stat_class_batch_list.action" class="textbg4" style="width:120px">返回批次列表</a>
			<br>
		</div>
		<form action="stat_class_batch_view.action" method="post"
			name="acc_list">
			<s:hidden name="department.id" />
			<s:hidden name="batch.id" />
			<s:hidden name="elUser.sex" />
			<s:hidden name="elUser.realname" />
			<s:hidden name="elUser.username" />
			<s:hidden name="elUser.jingzhong" />
			<s:hidden name="elUser.shengri" />
			<s:hidden name="elUser.shengri_end" />
			<s:hidden name="pN" id="pageNow" />
			<s:hidden name="elUser.isAssign" />
			<s:hidden name="userids" id="userids"></s:hidden>
		</form>
		<!-- 内容 -->
	
	</body>
	<script>
		function page(i) {
			document.getElementById("pageNow").value=i;
			acc_list.submit();
		}
	</script>
</HTML>
