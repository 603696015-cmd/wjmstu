<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.duman.entities.Department"%>
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
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((   this .   sectionRowIndex %   2 ==   0)
		? 
		 "#ffffff" :   "#f4f4f4" )
}
</style>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript">
			function doSearch(){
				myAttendance.submit();
			}
			
			function page(i){
				document.getElementById("pageNow").value=i;
				myAttendance.submit();
			}
		</script>
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="项目核算" />
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
		<div style="margin-top: 0px; text-align: center;">
			<form action="xiangmu_accounting.action" name="myAttendance" method="post">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" /> 
			<table width="98%" align="center" cellspacing="1" cellpadding="1">
				<tr>
					<td valign="top" width="120" id="tree_list_td">
						<%
							Department dep = (Department) request
										.getAttribute("department");
								String depid = dep.getId() + "";
						%>
						<wysLib:dep_list_aj rootAble="true"
							href="xiangmu_accounting.action?sub_department=1&department.id="
							iname="department.idd" ivalue="<%=depid%>"></wysLib:dep_list_aj>
						<script type="text/javascript">
							w0.setValues([new DEP(<s:property value="department.id"/>,<s:property value="department.lid"/>,<s:property value="department.rid"/>)]);
						</script>
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
					</td>
					<td valign="top">
						<table align="center" cellpadding="1" cellspacing="1"
							width="100%" height="100%">
							<tr>
								<td align="center" >
									项目名称
								</td>
								<td >
									<label>
										<input type="text" name="xiangmu.name" 
											value="">
									</label>
								</td>
								<td align="center" >
									项目负责人
								</td>
								<td >
									<label>
										<input type="text" name="xiangmu.fuzeren" 
											value="">
									</label>
								</td>
							</tr>
							<tr>
								<td align="center" >
									项目创建时间范围
								</td>
								<td >
									<label>
										开始时间<input type="text" name="xiangmu.starttime" 
											value="" onclick="setday(this);">
									</label>
								</td>
								<td >
									<label>
										结束时间<input type="text" name="xiangmu.endtime" 
											value="" onclick="setday(this);">
									</label>
								</td>
								<td align="center" ><input type="submit" value="搜索"  class="textbg6"/></td>
							</tr>
						</table>
						<table align="center" cellpadding="1" cellspacing="1"
							width="100%" height="100%">
							<tr>
								<th height="30" align="center">
									项目名称
								</th>
								<th height="30" align="center">
									项目收入
								</th>
								<th height="30" align="center">
									项目支出
								</th>
								<th height="30" align="center">
									其他收入
								</th>
								<th height="30" align="center">
									相关支出
								</th>
								<th height="30" align="center">
									项目损益
								</th>
								<th height="30" align="center">
									相关单据一览
								</th>
							</tr>
							<tbody onMouseOut="changeback()" onMouseOver="changeto()">
								<s:iterator value="list_map" status="status">
									<tr>
										<td align='center'><s:property value='xiangmumingcheng'/></td>
										<td align='center'><s:property value='xiangmushouru'/></td>
										<td align='center'><s:property value='xiangmuzhichu'/></td>
										<td align='center'><s:property value='qitashouru'/></td>
										<td align='center'><s:property value='xiangguanzhichu'/></td>
										<td align='center'>
											<s:if test="xiangmushouru+qitashouru-xiangmuzhichu-xiangguanzhichu>0">
												<s:property value='xiangmushouru+qitashouru-xiangmuzhichu-xiangguanzhichu'/>
											</s:if>
											<s:elseif test="xiangmushouru+qitashouru-xiangmuzhichu-xiangguanzhichu<0">
												<s:property value='xiangmushouru+qitashouru-xiangmuzhichu-xiangguanzhichu'/>
											</s:elseif>
											<s:else>
												<s:property value='xiangmushouru+qitashouru-xiangmuzhichu-xiangguanzhichu'/>
											</s:else>
										</td>
										<td align='center'>
											<s:if test="!#status.last">
												<a href="viewRelateDanju.action?id=<s:property value="id"/>" class="textbg6">查看</a>
											</s:if>
										</td>
									</tr>
								</s:iterator>
								<tr>
									<td align='center' colspan=7>
										<span style="color:red">说明：相关支出与其他收入，可以到<a href="myContactTags.action?tablename=XMDA&rx=1">项目档案</a>中进行修改</span>
									</td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</table>
			</form>
		</div>
		<!-- 内容 -->
		<center><wysLib:page></wysLib:page></center>
	
	</body>
</HTML>
