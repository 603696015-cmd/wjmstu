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
		<TITLE>批次统计</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>

		<!-- yuan52
			<table width="1000" align="center" cellpadding="1" cellspacing="1" bgcolor="#EBEBEB">
				<tr>
					<td valign="top">
						<%
							/*	
							ExamRoom examRoom = (ExamRoom) request.getAttribute("examRoom");
								String xx = examRoom == null ? "1"
										: examRoom.getEroomLib() == null ? "1" : examRoom
												.getEroomLib().getId()
												+ "";
								xx = "quiz_searchlist.action?examRoom.eroomLib.id=" + xx
										+ "&sub_department=1&department.id="; %=xx% 
							 */
						%>
						  wysLib:dep_list_f href="stat_eroom_batch_list.action?sub_department=1&department.id="rootAble="true" / >
					</td>
					<td> 
					-->
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((           this .           sectionRowIndex %           2 ==
		
		         0) ?     
		     "#ffffff" :           "#f4f4f4" )
}
</style>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="模块列表" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">批次统计</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px;">

			<table width="100%" align="center" cellpadding="1" cellspacing="1"
				bgcolor="#EBEBEB">
				<tr>
					<td width="20%" height="30"
						align="left">
						模块名称
					</td>
					<td width="20%" height="30"
						align="left">
						创建人
					</td> 
					<td width="20%" align="center">
						相关考场
					</td>
					<!--<td width="70" align="center">
						考试/缺考
						<br />
						人数
					</td>
					
					<td width="80" align="center">
						各工种情况
						<br />
						汇总表
					</td>
					<td width="80" align="center">
						各试卷情况
						<br />
						汇总表
					</td>
					<td width="80" align="center">
						考核成绩情况
						<br />
						汇总表
					</td>
					
					<td width="80" align="center">
						各单位情况
						<br />
						汇总表
					</td> --><td width="20%" align="center">
						成绩情况
						<br />
						汇总表
					</td>
					<td width="20%" height="30" align="center">
						操作
					</td>
				</tr>
				<s:if test="erblocks.size==0">
					<tr>
						<td colspan="9" align="center">
							未找到符合条件的批次，请修改搜索条件
						</td>
					</tr>
				</s:if>
				<s:else>
					<s:iterator value="erblocks">
						<tr>
							<td height="30"
								align="left">
								<s:property value="title" />
							</td>
							<td height="30"
								align="left">
								<s:property value="creater.realname" />
							</td>
							<td height="30"
								align="left">
								<s:property value="eroom.title" />
							</td>
							<!--<td width="70" height="30" align="center">
								<s:property value="userSize" />
								/
								<s:property value="usersize" />
							</td>
							 <td width="80" height="30" align="center">
								<a
									href="quiz_stat_eval_jz.action?examRoom.id=0&batchstat=1&erbatch.id=<s:property value="id"/>"
									class=textbg6>查 看</a>
							</td>
							<td width="80" height="30" align="center">
								<a
									href="quiz_detail_paper_view.action?examRoom.id=0&batchstat=1&erbatch.id=<s:property value="id"/>"
									class=textbg6>查 看</a>
							</td>
							<td width="80" height="30" align="center">
								<a
									href="quiz_stat_view.action?examRoom.id=0&batchstat=1&erbatch.id=<s:property value="id"/>"
									class=textbg6>查 看</a>
							</td>
							
							<td width="80" height="30" align="center">
								<a
									href="quiz_stat_eval.action?examRoom.id=0&batchstat=1&erbatch.id=<s:property value="id"/>"
									class=textbg4>查 看</a>
							</td> -->
							<td width="80" height="30" align="center">
								<a
									href="quizblock_detail_view.action?erblock.id=<s:property value="id"/>"
									class=textbg6>查 看</a>
							</td>
							<td width="70" height="30" align="center">
								<a
									href="eroom_block_alterinit.action?erblock.id=<s:property value="id"/>"
									class="textbg4">修 改</a>
								<a onClick="return window.confirm('确定删除？')"
									style="margin-top: 3px"
									href="eroom_block_delete.action?erblock.id=<s:property value="id"/>"
									class="textbg4">删 除</a>
							</td>
						</tr>
					</s:iterator>
					<tr>
						<td colspan="9" align="center">
							<SCRIPT type="text/javascript">
								function page(i){
									document.getElementById("pn").value=i;
									stat_examprac_list_n.submit();
								}
							</SCRIPT>
							<wysLib:page />
						</td>
					</tr>
				</s:else>
			</table>
			<a href="eroom_block_addinit.action?examRoom.id=<s:property value="examRoom.id"/>" class="textbg4"
				style="width: 90px">增加新模块集</a>
			<a href="quiz_searchlist.action?sub_department=1&department.id=1"
				class="textbg4" style="width: 70px">返回</a>
			<!-- </td>
				</tr>
			</table> -->
</div>
		<!-- 内容 -->
	
	</body>
</HTML>
