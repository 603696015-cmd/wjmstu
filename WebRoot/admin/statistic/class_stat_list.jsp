<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<TITLE>统计分析 -- 培训班统计</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/menu.js"></script>
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
				<span style="font-weight: bold;">培训班统计列表</span>
			</li>-->
		</ul>
		<table width="100%">
			<tr>
				<td width="200px;" valign="top" id="tree_list_td">
					<wysLib:clTypeTree href="class_stat_list.action?cltype.id="
						rootAble="true" />
				</td>
				<td valign="middle" width="5px;" style="padding: 0px">
					<img src="images/leftmenu/main_55.gif" style="cursor: hand"
						onclick="changeTreeDisplay(this)" />
				</td>
				<td valign="top">

					<s:form action="elclass_stat_list" name="myclist" theme="simple">
						<s:hidden name="pN" id="pageNow"></s:hidden>
						<s:hidden name="pS"></s:hidden>
					培训班名称：<s:textfield name="elClass.name"></s:textfield>
						<s:submit value="搜索"></s:submit>

					</s:form>

					<s:if test="elclasses.size==0">没有符合条件的培训班</s:if>
					<s:else>
						<table width="100%" height="100%" align="center" cellpadding="1"
							cellspacing="1" >
							<tr>
								<th width="150" align="center" >
									培训班名称								</th>
								<th width="120" align="center" >
									证书名称								</th>
								<th width="90" align="center" >
									创建者								</th>
								<th width="150" align="center" >
									创建时间								</th>
								<th width="120" align="center" >
									培训班类别								</th>
								<th width="150" align="center" >
									必修课(课程数/学分数)								</th>
								<th width="150" align="center" >
									选修课(课程数/学分数)								</th>
								
								<th width="80" align="center" >
									开放状态								</th>
								<th width="80" align="center" >
									学员人数								</th>
								<th width="70" align="center" >&nbsp;								</th>
							</tr>
							<s:iterator value="elclasses">
								<tr>
									<td width="150" align="center" >
										<s:property value="name" />
								  </td>
									<td width="120" align="center" >
										<s:property value="certificatename" />
								  </td>
									<td width="90" align="center" >
										<s:property value="creater.realname" />
								  </td>
									<td width="150" align="center" >
										<s:property value="createtime" />
								  </td>
									<td width="120" align="center" >
										<s:property value="cltype.name" />
								  </td>
									<td width="150" align="center" >
										<s:property value="bxStr" />
								  </td>
									<td width="150" align="center" >
										<s:property value="xxStr" />
								  </td>
									
									<td width="80" align="center" >
										<s:property value="statusName" />
								  </td>
									<td width="80" align="center" >
										<s:property value="studentCount" />
								  </td>
									<td width="70" align="center" >
										<a href="class_searchlist.action?elClassId=<s:property value="id" />&elClassName=
										<s:property value="name"/>">查看</a> 
										<!--  <a href="class_student.action?elclass.id=<s:property value="id" />">查看</a>-->
								  </td>
								</tr>
							</s:iterator>
					  </table>
					</s:else>
				</td>
			</tr>
		</table>
		<wysLib:page></wysLib:page>
	</body>
</HTML>
