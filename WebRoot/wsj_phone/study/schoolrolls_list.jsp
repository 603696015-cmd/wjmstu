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
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<TITLE>学籍查询管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<SCRIPT src="quiz_searchlist.files/cexampaper.js" type=text/javascript></SCRIPT>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript">
		function showtree(flag){
					var tree = document.getElementById("tree_list_td");
					var img = document.getElementById("showimg");
					if(flag){
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(false);" class="textbg">隐藏部门</a>';
					}
					else{
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(true);" class="textbg">显示部门</a>';
					}
				}
		</script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
			<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="学员列表" /></div>
			</li>
			<!--<li>
					 学籍查询管理
				</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
		<form action="schoolrolls.action" method="post"
			name="acc_list">
			<s:hidden name="pN" id="pageNow"/>
				<s:hidden name="pS" />
				<s:hidden name="deptid" />
			<table align="center" cellpadding="1" cellspacing="1" width="1000"
				>
				<tr>
					<%-- 
					<td>
						部门:
					</td>
					<td>
						<select name="elUser.department.id" id="deptid">
							<wysLib:dep_select selectid="<%=1%>" />
						</select>
					</td>
					 --%>
					<td>
						包含下属部门
					</td>
					<td>
						<input type="checkbox" name="sub_department"
							<s:if test="sub_department==1">checked="checked"</s:if>
							id="sub_department" value="1">
					</td>
					<td>
						性别：
					</td>
					<td>
						<select name="elUser.sex">
							<option value="" selected="selected">
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
				</tr>
				<tr>
					<td>
						姓名：
					</td>
					<td>
						<input name="elUser.realname"
							value="<s:property value="elUser.realname"/>"
							id="elUser.realname">
					</td>
					<td>
						账号：
					</td>
					<td>
						<input name="elUser.username"
							value="<s:property value="elUser.username"/>"
							id="elUser.username">
					</td>
					<td>
						<wysLib:BasetName btid="1" />：
					</td>
					<td>
						<s:select name="elUser.jingzhong" cssClass="g-select" theme="simple"
						list="jingzhongs" listKey="id" listValue="basevalue" headerValue="全部" headerKey="0"/>
					</td>
				</tr>
				<tr>
					<td>
						生日开始时间:
					</td>
					<td>
						<input type="text" size="16"
							value="<s:date format="yyyy-MM-dd" name="elUser.shengri"/>"
							name="elUser.shengri" onclick="setday(this)">
					</td>
					<td>
						生日结束时间:
					</td>
					<td>
						<input type="text" size="16"
							value="<s:date format="yyyy-MM-dd" name="elUser.shengri_end"/>"
							name="elUser.shengri_end" onclick="setday(this)">
					</td>
					<td>
					</td>
					<td>
						<input id="find" name="find" class="textbg4" type="submit" value="搜索">
					</td>
				</tr>
			</table>
		<table width="100%">
			<tr>
			<td valign="top" width="120">
				<wysLib:dep_list_aj rootAble="true" href="schoolrolls.action?deptid="></wysLib:dep_list_aj>
			</td>
			<td valign="top" align="left">
			<s:if test="listSchoolrolls.size==0">当前还没有学籍记录</s:if>
			<s:else>
				<table width="100%" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<caption>
						 学籍查询记录
					</caption>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						姓名
						</td>
					   <td height="30" align="center" >
							账号
					  </td>
					    <td height="30" align="center" >
							部门
						</td>
						<td height="30" align="center" >
							完成培训班数量
						</td>
						
						<td height="30" align="center" >
							通过考试数量
						</td>
						<td height="30" align="center" >
							线下培训记录数
						</td>
						<td height="30" align="center" >
							查看详情
						</td>
					</tr>
					<s:if test="listSchoolrolls.size==0">
						<TR>
							<TD align="center" colspan="4">
								当前还没有学籍记录
							</TD>
						</TR>
					</s:if>
					<s:else><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
						<s:iterator value="listSchoolrolls">
							<tr>
							    <td height="30" style="padding-left:8px;color:blue;" align="left">
							      <s:property value="realname" />
						       </td>
                                <td height="30" align="center" >
									<s:property value="username" />
								</td>
                                <td height="30" align="center" >
									<s:property value="deptname" />
								</td>
								<td height="30" align="center" >
									<s:property value="completeClass" />
								</td>
								<td height="30" align="center" >
									<s:property value="completeExam" />
								</td>
								<td height="30" align="center" >
									<s:property value="completeLineTrain" />
								</td>
								<td height="30" align="center" >
								   <a href="findenrollment_info.action?elUser.id= <s:property value="id" />" class=textbg4>查 看</a>
								</td>
							</tr>
						</s:iterator></tbody>
					</s:else>
			  </table>
			</s:else></td></tr></table><wysLib:page></wysLib:page>
			</form>
		</div>
		<!-- 内容 -->
	
	</body><script>
				function page(i) {
					document.getElementById("pageNow").value=i;
					acc_list.submit();
				}
			</script>
		<!--<form action="schoolrolls.action" method="post" name="acc_list">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<s:hidden name="deptid" />
			</form>
-->
</HTML>
