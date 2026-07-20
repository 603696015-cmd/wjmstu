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
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<!--<script type="text/javascript" src="js/message.js"></script>-->
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="培训班列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">培训班获证排行榜</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="  text-align: center; margin-left:  0px;">
			<table width="100%">
				<tr>
					<td valign="top" width="120" id="tree_list_td">
						<wysLib:clTypeTree href="dep_class_view.action?str=ctypeids&cltype.id="
							rootAble="true" />
				  </td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onClick="changeTreeDisplay(this)" />
					</td>
					<td valign="top">
					<script type="text/javascript">
						function toexcel(cid){     
							myclist.action = "dep_class_view.action?export=true&ctype.id="+cid;
							myclist.submit();
						}
						function view(){     
							myclist.action = "dep_class_view.action";
							myclist.submit();
						}			
						function page(i){
							document.getElementById("pageNow").value=i;
							myclist.submit();
						}			
					</script>
						<s:form action="dep_class_view" name="myclist" theme="simple">
							<s:hidden name="pN" id="pageNow"></s:hidden>
							<s:hidden name="pS"></s:hidden>
					培训班名称：<s:textfield name="elclass.name"></s:textfield>
							<!--<s:submit value="搜索"></s:submit>--> 
							<input type="button" value="搜索" onClick="view()">&nbsp;&nbsp;
							<input type="button" value="导出" onClick="toexcel(<s:property value="ctype.id" />)"> 
						</s:form> 
						<s:if test="classes.size==0">没有符合条件的培训班</s:if>
						<s:else>
							<table width="100%" height="100%" align="center" cellpadding="1"
								cellspacing="1" >
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
										高级人数</br>（不包括测试高级用户）
									</th>
									<th align="center" >
										通过人数
									</th>
									<th align="center" >
										高级通过人数</br>（不包括测试高级用户）
									</th>
									<th align="center" >
										测试组所有用户
									</th>
									<th align="center" >
										测试组所有通过人数
									</th>
									<th align="center" >
										测试组高级人数
									</th>
									<th align="center" >
										测试组高级通过人数
									</th>
									<th align="center" >
										通过率
									</th>
									<th align="center" >&nbsp;
										
									</th>
									<th align="center" bgcolor="#FFFFFF">&nbsp;
										
									</th>
								</tr>
								<tbody onMouseOut="changeback()" onMouseOver="changeto()">
									<s:iterator value="classes">
										<tr>
											<td height="30" align="center" >
												<s:property value="name" />										  </td>
											<td align="center" >
												<s:date name="createtime" format="yyyy-MM-dd HH:mm:ss" />
											</td>
											<td align="center" >
												<s:property value="userCount" />
											</td>
											<td align="center" >
												<s:property value="userCount_" />
											</td>
											<td align="center" >
												<s:property value="userPassedCount" />
											</td> 
											<td align="center" >
												<s:property value="userPassedCount_" />
											</td> 
											<td align="center" >
												<s:property value="userfeigaojiPassedCount_" />
											</td> 
											<td align="center" >
												<s:property value="userhesuanPassedCount_" />
											</td> 
											<td align="center" >
												<s:property value="userhesuangaojiCount_" />
											</td> 
											<td align="center" >
												<s:property value="userhesuangaojiPassedCount_" />
											</td> 
											<td align="center" >
												<s:property value="passper" />%
											</td>
											<td align="center" >
												<a
													href="class_quiz_stat_eval.action?elclass.id=<s:property value="id" />">查看概况</a>
												<!--<a
													href="dep_course_list.action?department.id=<s:property value="department.id"/>">选课情况</a>-->
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<a
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
			<wysLib:page></wysLib:page>
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
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
