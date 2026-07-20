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
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<!--<script type="text/javascript" src="js/message.js"></script>-->
		<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="培训班列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">培训班获证排行榜</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
			<table width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<td valign="top" width="120" id="tree_list_td">
						<wysLib:st_list_aj href="sta_class_view.action?str=ctypeids&station.id="
							rootAble="true" />
				  </td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onClick="changeTreeDisplay(this)" />
					</td>
					<td valign="top">
					<script type="text/javascript">
						function toexcel(cid){     
							myclist.action = "sta_class_view.action?export=true&ctype.id="+cid;
							myclist.submit();
						}
						function view(){     
							myclist.action = "sta_class_view.action";
							myclist.submit();
						}			
						function page(i){
							myclist.action = "sta_class_view.action";
							document.getElementById("pageNow").value=i;
							myclist.submit();
						}			
					</script>
						<s:form action="sta_class_view" name="myclist" theme="simple">
							<s:hidden name="pN" id="pageNow"></s:hidden>
							<s:hidden name="pS"></s:hidden>
					岗位名称：<s:textfield name="elclass.name"></s:textfield>
							<!--<s:submit value="搜索"></s:submit>--> 
							<input type="button" value="搜索" class="textbg4" onClick="view()">&nbsp;&nbsp;
							<input type="button" value="导出" class="textbg4" onClick="toexcel(<s:property value="ctype.id" />)"> 
						</s:form> 
						<s:if test="classes.size==0">没有符合条件的岗位培训班</s:if>
						<s:else>
							<table width="100%" height="100%" align="center" cellpadding="1"
								cellspacing="1" >
								<tr>
									<th align="center" >
										岗位名称
									</th>
									<th align="center" >
										创建时间
									</th>
									<th align="center" >
										学员人数
									</th>
									<th align="center" >
										通过人数
									</th>
									<th align="center" >
										通过率
									</th>
									<th align="center" >&nbsp;
										
									</th>
								</tr>
								<tbody onMouseOut="changeback()" onMouseOver="changeto()">
									<s:iterator value="classes">
										<tr>
											<td height="30" align="center" >
												<s:property value="name" />
											</td>
											<td align="center" >
												<s:date name="createtime" format="yyyy-MM-dd HH:mm:ss" />
											</td>
											<td align="center" >
												<s:property value="userCount" />
											</td>
											<td align="center" >
												<s:property value="userPassedCount" />
											</td> 
											<td align="center" >
												<s:property value="passper" />
											</td>
											<td align="center" >
												<a class="textbg4"
													href="class_student.action?elUser.id=0&elclass.id=<s:property value="id" />&elClassId=<s:property value="id" />&elClassName=<s:property value="name"/>">查看</a>
												<!--<a
													href="dep_course_list.action?department.id=<s:property value="department.id"/>">选课情况</a>-->
											</td>
										</tr>
									</s:iterator>
								</tbody>
							</table>
						</s:else>
					</td>
				</tr>
			</table>
			<div style="text-align: center;"><wysLib:page></wysLib:page>
			<a href="stat_class_batch_list.action" class="textbg4" style="width:90px">批次统计</a></div>
			<!--<table width="700px" align="center" cellpadding="1" cellspacing="1"
				>
				<tr>
					<th align="center" >
						培训班名称
					</th>
					<th align="center" >
						创建时间
					</th>
					<th align="center" >
						学员人数
					</th>
					<th align="center" >
						通过人数
					</th>
					<th align="center" >
						通过率
					</th>
					<th align="center" >
						&nbsp;
					</th>
				</tr>
				<s:if test="classes.size==0">
			</table>
			</s:if>
			<s:else>
				<s:iterator value="classes">
					<tr>
						<td align="center" >
							<s:property value="name" />
						</td>
						<td align="center" >
							<s:date name="createtime" format="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td align="center" >
							<s:property value="userCount" />
						</td>
						<td align="center" >
							<s:property value="userPassedCount" />
						</td>
						<td align="center" >
							<s:property value="passper" />
						</td>
						<td align="center" >
							<a
								href="class_student.action?elUser.id=0&elclass.id=<s:property value="id" />&elClassId=<s:property value="id" />&elClassName=<s:property value="name"/>">查看</a>
						</td>
					</tr>
				</s:iterator>
				</table>
			</s:else>
		-->
		<!-- 内容 -->
	
	</body>
</HTML>
