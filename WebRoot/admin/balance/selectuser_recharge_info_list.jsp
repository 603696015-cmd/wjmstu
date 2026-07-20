<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<base href="<%=basePath%>">
		<TITLE>五矿发展员工职业发展系统--管理端--用户添加</TITLE>
	<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<SCRIPT src="quiz_searchlist.files/cexampaper.js" type=text/javascript></SCRIPT>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<BODY>
	<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<span style="font-weight: bold;">记录列表</span>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
		<s:form action="geteluserzengzi_recharge_info1" method="post" name="acc_list"
				theme="simple">
				<table width="100%">
					<tr>
						<td valign="top" width="120" id="tree_list_td">

							<wysLib:dep_list_aj rootAble="true" href="geteluserzengzi_recharge_info1.action?sub_department==1&all=1&deptid="></wysLib:dep_list_aj>
							<script type="text/javascript">
								w0.setValues([new DEP(<s:property value="department.id"/>,<s:property value="department.lid"/>,<s:property value="department.rid"/>)]);
							</script>
						</td>
						<td valign="middle" width="5px;" style="padding: 0px">
							<img src="images/leftmenu/main_55.gif" style="cursor: hand"
								onclick="changeTreeDisplay(this)" />
						</td>
						<td valign="top">
							<s:hidden name="all" />
							<s:hidden name="deptid" />
							<div style="text-align: center;">
							<table>
							<tr>
							<td>角色：</td>
							<td><s:select  name="elUser.role.id" list="roles" listKey="id" listValue="name" theme="simple" headerKey="0"
					 headerValue="请选择" /></td>
							<td>用户名：</td>
							<td><s:textfield name="elUser.username" theme="simple"/></td>
							<td>增值类型：</td>
							<td><s:select theme="simple"  headerValue="全部" headerKey="0"
									list="#{1:'充值',2:'余额转移',3:'手工增资'}"
									name="type" value="type" /></td>
							<td>包含下属部门：</td>
							<td><input type="checkbox" name="sub_department"
										<s:if test="sub_department==1">checked="checked"</s:if>
										id="sub_department" value="1"></td>		
							</tr>
							<tr>
							<td>姓名：</td>
							<td><s:textfield name="elUser.realname" theme="simple"/></td>
							<td>操作者姓名：</td>
							<td>	<s:textfield name="caozuoname" theme="simple"/></td>
							<td>操作者账号：</td>
							<td><s:textfield name="caozuousername" /></td>
							<td colspan="2">
							<input class="textbg4" type="submit" value="搜 索">
							</td>
										
							</tr>
							</table>
								
							</div>

							<table align="center" cellpadding="1" cellspacing="1"
								width="100%" height="100%">
								<tr>
									
									<th width="90">
										时间
									</th>
									<th width="100">
										收款人姓名
									</th>
									<th width="120">
										收款人账号
									</th>
									<th width="100">
										角色
									</th>
									<th>
										部门名称									</th>
									<th>
										操作者账号									</th>
									<th>
										操作者姓名									</th>
									<th width="50">
										数额
									</th>
									<th width="50">
										备注
									</th>
									<!--<th>
						&nbsp;
					</th>
				-->
								</tr>
						<tbody onMouseOut="changeback()" onMouseOver="changeto()">
							<s:iterator value="re">
							<tr>
								<td height="20" align="center">
									<s:date name="Rechargedate" format="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td height="20" align="center">
									<s:property value="rechargeuserid.realname" />
								</td>
								<!--<td height="20" align="center">
									<s:property value="userno" />
								</td>
								-->
								<td height="20" align="center">
									<s:property value="rechargeuserid.username" />
								</td>
								
								<td height="20" align="center">
									<s:property value="rechargeuserid.role.name" />
								</td>
								<td height="20" align="center">
									<s:property value="rechargeuserid.department.name" />
								</td>
								<td height="20" align="center">
									<s:property value="user.username" />
								</td>
								<td height="20" align="center">
									<s:property value="user.realname" />
								</td>
								<td height="20" align="center">
									<s:property value="Addbalance" />
								</td>
								<td height="20" align="center">
									<s:if test="type==3">线下交易</s:if>
									<s:if test="type==2">余额转移</s:if>
									<s:if test="type==1">充值</s:if>
								</td>
							</tr>
						</s:iterator>
							  </tbody>
						  </table>
						</td>
					</tr>
				</table>
			</s:form>
			<s:form action="geteluserzengzi_recharge_info1" method="post" name="assignUser">
				<s:hidden name="pN" id="pageNow1" />
				<s:hidden name="pS" />
				<s:hidden name="deptid" />
				<s:hidden name="sub_department" />
				<s:hidden name="elUser.username" />
				<s:hidden name="elUser.realname" />
				<s:hidden name="elUser.role.id" />
				<s:hidden name="caozuoname" />
				<s:hidden name="caozuousername" />
				<s:hidden name="type" />
				<s:hidden name="all" />
				
				<wysLib:page></wysLib:page>
			</s:form>
			
			
					
				</div>
		<!-- 内容 -->
	</BODY>
	      <script type="text/javascript">
				function page(i) {
					document.getElementById("pageNow1").value=i;
					assignUser.submit();
				}
			</script>
</HTML>
