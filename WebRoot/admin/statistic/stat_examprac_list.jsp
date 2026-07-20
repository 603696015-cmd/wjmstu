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
		<TITLE>练习统计</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript">
			function page(i){
				document.getElementById("pageNow").value=i;
				stat_examprac_list_n.submit();
			}
		</script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="练习列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">练习统计</span>
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
			<table width="100%" align="center" cellpadding="1" cellspacing="1"
				bgcolor="#EBEBEB">
				<tr>
					<th height="30px" colspan="6">
						<s:form action="stat_examprac_list" name="stat_examprac_list_n"
							method="post" theme="simple">
							<s:hidden name="pN" id="pageNow" />
							<s:hidden name="pS" />
							练习考场关键字:
							<s:textfield name="examprac.title" />
							时间段范围:开始：<input
									value="<s:date name="examprac.begintime" format="yyyy-MM-dd HH:mm:ss"/>"
									name="examprac.begintime" onclick="setday(this)" readonly="readonly" />
							结束：<input
									value="<s:date name="examprac.endtime" format="yyyy-MM-dd HH:mm:ss"/>"
									name="examprac.endtime" onclick="setday(this)" readonly="readonly" />
							<%-- 
							部门:<select name="department.id" id="parentid">
									<wysLib:dep_select />
								</select>包含下属部门
									<input type="checkbox" <s:if test="sub_department=1">checked</s:if> name="sub_department" id="sub_department"
									value="1">
							 --%>
							<s:submit value="查看"></s:submit>
						</s:form>
					</th>
				</tr>
				<tr>
					<th height="30" align="center" >
						练习名称
					</th>
					<th height="30" align="center" >
						开始时间
					</th>
					<th height="30" align="center" >
						结束时间
					</th>
					<th width="110" height="30" align="center" >
						练习概况					</th>
					<th width="110" height="30" align="center" >
						练习详情					</th>
					<th width="110" height="30" align="center" >
						部门比较					</th>
				</tr>
				<s:if test="myexampracs.size==0">
					<tr>
						<td colspan="6" align="center">
							未找到符合条件的练习，请修改搜索条件
						</td>
					</tr>
				</s:if>
				<s:else>
				<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
					<s:iterator value="myexampracs">
						<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
								<s:property value="prac.title" />
							</td>
							<td height="30" align="center" >
								<s:date name="prac.begintime" format="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td height="30" align="center" >
								<s:date name="prac.endtime" format="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td width="110" height="30" align="center" >
								<a
									href="stat_examprac_gk.action?examprac.id=<s:property value="prac.id"/>" class=textbg6>查看概况</a>
						  </td>
							<td width="110" height="30" align="center" >
								<a
									href="stat_examprac_detail.action?examprac.id=<s:property value="prac.id"/>" class=textbg6>查看详情</a>
						  </td>
							<td width="110" height="30" align="center" >
								<a
									href="stat_examprac_eval.action?examprac.id=<s:property value="prac.id"/>" class=textbg4>查 看</a>
						  </td>
						</tr>
					</s:iterator></tbody>
					<tr>
						<td colspan="6" align="center">
							<wysLib:page />
						</td>
					</tr>
				</s:else>
		  </table>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
