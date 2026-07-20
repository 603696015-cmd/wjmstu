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
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/tree/dtreequizdep.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript"> 
			function setabled(idstr,id){
					document.getElementById(idstr+id).checked=true; 
			} 
		</script>
	</HEAD>
	<BODY>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="部门比较" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">部门评比</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="quiz_stat_view.action?examRoom.id=<s:property value="examRoom.id"/>&department.id=<s:property value="department.id"/>&sub_department=<s:property value="sub_department"/><s:iterator value="departments1">&departments1.id=<s:property value="id"/></s:iterator>">考试概况</a>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="quiz_detail_view.action?examRoom.id=<s:property value="examRoom.id"/>&department.id=<s:property value="department.id"/>&sub_department=<s:property value="sub_department"/>">考试详情</a>
			</li>-->
		</ul>
		<!-- 内容 -->
		<SCRIPT type="text/javascript">   
			function toexcel(){   
				statEval.action = "class_quiz_stat_eval.action?exprot=true";
				statEval.submit();
			} 
			function view(){   
				statEval.action = "class_quiz_stat_eval.action";
				statEval.submit();
			}
			function alterRatioPassing(depid,i){    
				var ratioP = document.getElementById('ratioP_'+i).value; 
				statEval.action = "class_quiz_stat_eval.action?Ration=true&department.id="+depid+"&department.ratioPassing_="+ratioP;
				statEval.submit();
				alert("修改成功!");
			} 
		</SCRIPT>
		<div style="margin-top: 20px;">
			<div>
				<table>
					<tr>
						<td valign="top" id="tree_list_td" style="display: block;">
							<form action="class_quiz_stat_eval.action" method="post"
								name="statEval">
								<%--<input type="button" value="查看" onClick="view()" />
								<input type="button" value="导出" onClick="toexcel()" /> --%>
								<s:hidden name="elclass.id"></s:hidden>  
								<wysLib:dep_list_cb attrname="depTree"
									inputname="departments1.id"	></wysLib:dep_list_cb>  		
							</form>
						</td>
						<td valign="middle" width="5px;" style="padding: 0px">
							<img src="images/leftmenu/main_55.gif" style="cursor: hand"
								onclick="changeTreeDisplay(this)" />
						</td>
						<td valign="top">
							<table width="700px" align="center" cellpadding="1"
								cellspacing="1" bgcolor="#ECEDEB">
								<tr>
									<th align="center" bgcolor="#FFFFFF">
										通过率排行
									</th>
									<th align="center" bgcolor="#FFFFFF">
										部门
									</th>
									<th align="center" bgcolor="#FFFFFF">
										总人数
									</th>
									<th align="center" bgcolor="#FFFFFF">
										高级职称总人数
									</th>
									<th align="center" bgcolor="#FFFFFF">
										通过人数
									</th>
									<th align="center" bgcolor="#FFFFFF">
										高级职称通过人数
									</th>
									<th align="center" bgcolor="#FFFFFF"> 
										通过率
									</th>  
									<th align="center" bgcolor="#FFFFFF"> 
										设置通过率
									</th>  
								</tr>
								<s:iterator value="departments" status="st">
									<s:if test="userCount==0">
									<tr>
										<td align="center" bgcolor="#FFFFFF">
											<s:property value="#st.index+1" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<s:property value="name" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											-
										</td>
										<td align="center" bgcolor="#FFFFFF">
											-
										</td>
										<td align="center" bgcolor="#FFFFFF">
											-
										</td> 
										<td align="center" bgcolor="#FFFFFF">
											-
										</td> 
									</tr></s:if>
									<s:else>
										<tr>
											<td align="center" bgcolor="#FFFFFF">
												<s:property value="#st.index+1" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<s:property value="name" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<s:property value="userCount" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<s:property value="userGaojiCount" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<s:property value="userCount_" />
											</td> 
											<td align="center" bgcolor="#FFFFFF">
												<s:property value="userGaojiPassCount" />
											</td>
											<td align="center" bgcolor="#FFFFFF"> 
												<s:property value="ratioPassing" />% 
											</td> 
											<td align="center" bgcolor="#FFFFFF">   
													<input type="text" name="ratioPassing_" id="ratioP_<s:property value="#st.index+1" />" value="<s:property value="ratioPassing_" />"> %
													<input type="button" value="确定" onClick="alterRatioPassing(<s:property value="id" />,<s:property value="#st.index+1" />)" />
											</td> 
										</tr></s:else>
								</s:iterator>
							</table>
						</td>
					</tr>
				</table>
				<s:iterator value="departments1">
					<script> setabled('depTree',<s:property value="id" />);</script>
				</s:iterator>
				说明：“-”表示该部门无人参加考试
			</div>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
