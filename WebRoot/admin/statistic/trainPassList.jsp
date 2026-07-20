<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.classman.entities.ElClass"%>
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
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/tree/depuserlist.js"></script>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<body onload="change_(this);">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="学习排行榜" /></div>
			</li>
			<!--<li>
				培训班统计表
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<script type="text/javascript">
			function toexcel(id){     
				assignSearch_assignment.action = "trainPassList.action?export=true&dep.id="+id;
				assignSearch_assignment.submit();
			}
			function view(id){     
				assignSearch_assignment.action = "trainPassList.action?elUser.flag=1&dep.id="+id;
				assignSearch_assignment.submit();
					//$('#gangwei1').value=0;
				/*$.ajax({
				  type: 'POST',
				  url: "trainPassList.action",
				  data: {'dep.id':id},
				  async:false,//同步
				  success: function(data){
				  //alert(data);
				  	  	$('#gangwei1').value=0;
			  			$('#div1').html(data);
			  		}
				});*/
				
			}	
			function change_(obj){
				//var str=obj.value;
				var str1=document.getElementById("gangwei1").value;
				$.ajax({
				  type: 'POST',
				  url: "trainPassList_two.action",
				  data: {'typeid':str1},
				  async:false,//同步
				  success: function(data){
				  //alert(data);
				  		
			  			$('#work_').html(data);
			  			<s:if test="elUser.jingzhong!=null&&elUser.jingzhong!=''">
			  			document.getElementById("elUser.jingzhong").value=<s:property value='elUser.jingzhong'/>;
			  			</s:if>
			  		}
				});
			}					
		</script>
		<div style="margin-top: 0px;">
			<s:form action="trainPassList" method="post"
				name="assignSearch_assignment" theme="simple">
				<s:hidden name="elclass.id" />
				<!--<s:hidden name="examRoom.id" />
				<s:hidden name="examPaper.id" />-->
				<table width="100%" cellpadding="1" cellspacing="1">
					<tr>
						<th width="120" align="center" >
							培训班名称
						</th>
						<td width="250" align="center" >
							<s:property value="elClass.name" />
						</td>
						<th align="center" >
							证书名称
					  </th>
						<td align="center" >
							<s:property value="elClass.certificatename" />
						</td>
					</tr>
					<tr>
						<th width="120" align="center" >
							创建人
						</th>
						<td width="250" align="center" >
							<s:property value="elClass.creater.realname" />
						</td>
						<th align="center" >
							所属类别
					  </th>
						<td align="center" >
							<s:property value="elClass.cltype.name" />
						</td>
					</tr>
					<tr>
						<th align="center" >
							结业条件
						</th>
						<td colspan="3" align="center" >
							必修课全部通过，选修课最少获得
							<span style="color: red;"><b> <s:property
										value="elClass.optionalcredit" /> </b>
							</span> 学分
						</td>
					</tr>
					<tr>
						<th width="120" align="center" >
							简介
						</th>
						<td colspan="3" align="center" >
							<s:property value="elClass.description" />
						</td>
					</tr>
				</table>
				<table width="100%" cellpadding="1" cellspacing="1"  >
					<%-- <tr>
							<%-- <td>
							<wysLib:BasetName btid="1" />：
						</td>
						
						<td><!-- 
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
								<s:select theme="simple" name="elUser.jingzhong" cssClass="g-select"
										list="jingzhongs" listKey="id" listValue="basevalue"  headerValue="全部" headerKey="0"/>
							</label>
						</td>
						<td>
							<wysLib:BasetName btid="2" />：
						</td>
						<td><!-- 
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
								<s:select theme="simple" name="elUser.zhiwu" cssClass="g-select"
										list="zhiwus" listKey="id" listValue="basevalue"  headerValue="全部" headerKey="0"/>
							</label>
						</td>
						<td>
							<wysLib:BasetName btid="3" />：
						</td>
						<td><!-- 
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
								<s:select theme="simple" name="elUser.zhiji" cssClass="g-select"
										list="zhijis" listKey="id" listValue="basevalue"  headerValue="全部" headerKey="0"/>
							</label>
						</td>
						--%>
					<%-- 	<s:iterator value="listMap"  >
							<td>
							<span> <s:property value="key"/></span>
							</td>
							<td>
							<label>
								<s:select theme="simple" name="elUser.jingzhongIds" cssClass="g-select"
										list="value" listKey="id" listValue="basevalue"  headerValue="全部" headerKey="0"/>
							</label>
							</td>
						
						</s:iterator>
					</tr>--%>
					
					<tr>
						<td width="120">
							考试段开始时间:
						</td>
						<td>
							<input type="text" size="16" name="elUser.begintime"
								onclick="setday(this)" readonly="readonly" value="<s:date name="elUser.begintime" format="yyyy-MM-dd HH:mm:ss"/>">
						</td>
						<td>
							考试段结束时间:
						</td>
						<td>
							<input type="text" size="16" name="elUser.begintime_end"
								onclick="setday(this)" readonly="readonly" value="<s:date name="elUser.begintime_end" format="yyyy-MM-dd HH:mm:ss"/>">
						</td>
						<td width="120">
							年龄段开始时间:
						</td>
						<td>
							<input type="text" size="16" name="elUser.shengri"
								onclick="setday(this)" readonly="readonly" value="<s:date name="elUser.shengri" format="yyyy-MM-dd HH:mm:ss"/>">
						</td>
						<td>
							年龄段结束时间:
						</td>
						<td>
							<input type="text" size="16" name="elUser.shengri_end"
								onclick="setday(this)" readonly="readonly" value="<s:date name="elUser.shengri_end" format="yyyy-MM-dd HH:mm:ss"/>">
						</td>
						
					</tr>
					<tr >
						<td width="120" >
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
						<td>
							职业人群：
						</td>
						<td>
							<s:select theme="simple" name="elUser.gangwei" cssClass="g-select" id="gangwei1"
										list="workCourseUser" listKey="work_type" listValue="workTypeName"  headerValue="全部" headerKey="0" onchange="change_(this)"/>
						</td>
						<td width="120">
							职业类别：
						</td>
						<td>
						<span id="work_">
							<select id="elUser.jingzhong">
								<option value="0">全部</option>
							</select>
						</span>
						</td>
					</tr>
					
					<tr >
					<td width="120">
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
							地区：
						</td>
						<td bgcolor="#F8FCFE">
						<label>
							<select name="deptid" id="deptid" >
								<wysLib:DepartmentSelect_sd selectid="${deptid}"></wysLib:DepartmentSelect_sd>
							</select>
						</label>
						  </td>
						
						<td colspan="2">
							<s:hidden name="elUser.department.id" id="elUser.department.id"></s:hidden>
							<input type="button" class="textbg4" value="搜索" onClick="view(<s:property value="dep.id" />)">&nbsp;&nbsp; 
							<input type="button" class="textbg4" value="导出" onClick="toexcel(<s:property value="dep.id" />)"> 
						</td>
					</tr>
					
				</table>
			</s:form>
			<div id="div1" >
			<table width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<%--<td valign="top" width="150px;">
						<%
							ElClass elclass = (ElClass) request.getAttribute("elclass");
							String url = "trainPassList.action?elclass.id=" + elclass.getId()
									+ "&elUser.id=0&deptid=";
						%>
						<%=url%>
						<wysLib:dep_list_aj rootAble="true" href="<%=url%>"></wysLib:dep_list_aj>
						<script type="text/javascript">
							w0.setValues([new DEP(<s:property value="dep.id"/>,<s:property value="dep.lid"/>,<s:property value="dep.rid"/>)]);
						</script>
					</td>--%>
					
					<td valign="top" align="left">
					<div id="st" >
					<table style="margin-top:0px;" width="100%" align="center" cellpadding="1" cellspacing="1"
								bgcolor="#EBEBEB">
						<s:if test="elusers.size==0">
						尚无证书
						</s:if>
						<s:else>
								<tr>
						<th height="30" style="padding-left:8px;color:blue;" align="left">
										姓名
								  </th>
									<th height="30" align="center" >
										性别
									</th>
									<th height="30" align="center" >
										账号
									</th>
									<th height="30" align="center" >
										地区
									</th>
									<th height="30" align="center" >
									<%--	<wysLib:BasetName btid="1" /> --%>
									人群
									
									</th>
								<%--	<th height="30" align="center" >
										年龄
									</th>

								 <th height="30" align="center" >
										学分
									</th>
									<th height="30" align="center" >
										必修学分
									</th>
									<th height="30" align="center" >
										选修学分
									</th>
									--%>
									
<!--									<th height="30" align="center" >-->
<!--										<wysLib:BasetName btid="2" />-->
<!--									</th>-->
<!---->
<!--								 <th height="30" align="center" >-->
<!--										<wysLib:BasetName btid="3" />-->
<!--									</th>-->
									<th height="30" align="center" >
									<%--	<wysLib:BasetName btid="1" /> --%>
									职业类别
									
									</th>
									<th height="30" align="center" >
										考试时间
									</th>
									<th height="30" align="center" >
										考试分数
									</th>
									
									<th height="30" align="center" >
										学习详情
									</th>
									<th height="30" align="center" >
										学习轨迹
									</th>
									<th height="30" align="center" >
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
											<td height="30" style="padding-left:8px;color:blue;" align="left">
												<s:property value="realname" />
											</td>
											<td height="30" align="center" >
												<s:property value="sex" />
											</td>
											<td height="30" align="center" >
												<s:property value="username" />
											</td>
											<td height="30" align="center" >
												<s:property value="department.name" />
											</td>
											<td height="30" align="center" >
												 <s:if test="workTypeName==null">
											    	无
											    </s:if>
											    <s:else>
												<s:property value="workTypeName" />
												</s:else>
											</td>
										<%-- 	<td height="30" align="center" >
												<s:property value="age" />
											</td>
											<td height="30" align="center" >
												<s:property value="xx_time" />
											</td>
											<td height="30" align="center" >
												<s:property value="ct_credit" />
											</td>
											<td height="30" align="center" >
												<s:property value="xx_credit" />
											</td>
											--%>
<!--											-->
<!--											<td height="30" align="center" >-->
<!--											    <s:if test="zhiwu_==0">-->
<!--											    	无-->
<!--											    </s:if>-->
<!--											    <s:else>-->
<!--												<s:property value="zhiwu_" />-->
<!--												</s:else>-->
<!--											</td>-->
<!--											<td height="30" align="center" >-->
<!--												 <s:if test="zhiji_==0">-->
<!--											    	无-->
<!--											    </s:if>-->
<!--											    <s:else>-->
<!--												<s:property value="zhiji_" />-->
<!--												</s:else>-->
<!--											</td>-->
											<td height="30" align="center" >
												<s:property value="personTypeName" />
											</td>
											<td height="30" align="center" >
												<s:property value="begintime" />
											</td>
											<td height="30" align="center" >
												<s:property value="myscore" />
											</td>
											<td height="30" align="center" >
												<a
													href="dep_classstudy_view.action?elclass.id=<s:property value="elClass.id"/>&elUser.id=<s:property value="id"/>&Return=class_student.action?elUser.id=0xyzzyxelclass.id=<s:property value="elClass.id"/>xyzzyxelClassId=<s:property value="elClass.id"/>xyzzyxelClassName=<s:property value="elClass.name"/>" class=textbg4>查 看</a>
											</td>
											<td height="30" align="center" >
												<a class="textbg4" href="statisticStudyLearnLocus.action?elUser.id=<s:property value="id" />&course.classid=<s:property value="elclass.id"/>&Return=class_student.action?elUser.id=0xyzzyxelclass.id=<s:property value="elclass.id"/>xyzzyxelClassId=<s:property value="elclass.id"/>xyzzyxelClassName=<s:property value="elclass.name"/>">查看</a>
											</td>
											<td height="30" align="center" >
												<s:if test="graddate == null">尚无证书</s:if>
												<s:else>
												<SPAN style="color: red"><s:date format="yyyy-MM-dd" name="graddate" /></SPAN>
												</s:else>
											</td>
										</tr>
									</s:iterator>
								</s:else>
						 
						</s:else>
						
					</td>
					 </table>
					</div>
				</tr>
			</table>
			<wysLib:page></wysLib:page>
			<a href="dep_class_view.action" class="textbg">返回统计列表</a>
			</div>
			<br/>
		
		<form action="trainPassList.action" method="post" name="acc_list">
			<s:hidden name="deptid" />
			<s:hidden name="elclass.id" />
			<s:hidden name="elUser.sex" />
			<s:hidden name="elUser.realname" />
			<s:hidden name="elUser.username" />
			<s:hidden name="elUser.jingzhong" />
			<s:hidden name="elUser.shengri" />
			<s:hidden name="elUser.shengri_end" />
			<s:hidden name="elUser.begintime" />
			<s:hidden name="elUser.begintime_end" />
			<s:hidden name="elUser.zhiwu" />
			<s:hidden name="elUser.zhiji" />
			<s:hidden name="pN" id="pageNow" />
			<s:hidden name="elUser.isAssign" />
			<s:hidden name="userids" id="userids"></s:hidden>
			<s:hidden name="elUser.jingzhongIds" />
			
		</form>
		<!-- 内容 -->
		</div>
	</BODY>
	<script>
		function page(i) {
			document.getElementById("pageNow").value=i;
			acc_list.submit();
		}
		
		function check(username){
		
}
	</script>
</HTML>
