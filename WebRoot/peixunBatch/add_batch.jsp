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
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((                 this .                 sectionRowIndex %  
		              2 ==   
		
		            0) ?        
		        "#ffffff" :                 "#f4f4f4" )
}
</style>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/libutil.js"></script>
		<script type="text/javascript" src="js/erwithoutop.js"></script>
		<script type="text/javascript" src="js/inputcheck.js"></script>
		<script type="text/javascript" src="js/basedataop.js"></script>
		<SCRIPT type="text/javascript">
			function check_add(){
				var pxname = document.getElementById("peixunBatch_name").value;
				var pxbegintime = document.getElementById("pxbatch_begintime").value;
				var pxendtime = document.getElementById("pxbatch_endtime").value;
				if(pxname==''){
					alert("名称不能为空！");
					return false;
				}
				if(pxbegintime==''){
					alert("开始时间不能为空！");
					return false;
				}
				if(pxendtime==''){
				alert("结束时间不能为空！");
				return false;
				}
				add_batch.action="save_batch.action";
				add_batch.submit();
			}
		
		</SCRIPT>
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td valign="middle" class="tablequiz">
					<ul class="nav">
						<li>
							<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
								<wysLib:Navigation ivalue="添加批次" />
							</div>
						</li>
					</ul>
				</td>
				<td width="120" valign="middle" class="tablequiz">
					<A id=quit href="javascript:window.parent.full_screen(false);"
						class="textbg6" style="display: none">退出全屏</A>
				</td>
			</tr>
		</table>

		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<!--<label style="font-size: 16px;">
				考核考试管理
			</label>-->
			<br>
			<input type="hidden" id="nopaper" name="nopaper" />
			<input type="hidden" id="nouserinvigilators"
				name="nouserinvigilators" />
			<input type="hidden" id="nouserappraises" name="nouserappraises" />
			<input type="hidden" id="nouservalids" name="nouservalids" />
			<s:form id="add_batch" name="add_batch" method="post"
				theme="simple" action="save_batch"
				>
				<table width="100%" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr>
						<td width="160" align="right">
							<span class="neededitem">*</span>名称：
						</td>
						<td>
							&nbsp;
							<label>
								<input name="peixunBatch.name" type="text" id="peixunBatch_name"
									value="<s:property value="peixunBatch.name"/>" size="60" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" align="right">
							简介：
						</td>
						<td>
							&nbsp;
							<label>
								<textarea name="peixunBatch.description" cols="60" rows="7"></textarea>
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" align="right">
							<span class="neededitem">*</span>类型：
						</td>
						<td>
						&nbsp;
							<label>
								<s:select id="selectTypeId" theme="simple" headerKey="-1"
									headerValue="全部" list="baseDatatList" name="peixunBatch.typeid"
									listKey="id" listValue="basevalue" />
							</label>

						</td>
					</tr>
					<tr>
						<td width="160" align="right">
								开始时间：
						</td>
						<td>
						&nbsp;
							<label>
								<input  class="Wdate"
									name="peixunBatch.createtime" type="text" id="pxbatch_begintime" />
								&nbsp;


								<input type="button" class="textbg4"
									onclick="setday(document.getElementById('pxbatch_begintime'))"
									value="选择" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" align="right">
								结束时间：
						</td>
						<td>
						&nbsp;
							<label>
								<input  class="Wdate"
									name="peixunBatch.endtime" id="pxbatch_endtime" type="text" />
								&nbsp;
								<input type="button" class="textbg4"
									onclick="setday(document.getElementById('pxbatch_endtime'))"
									value="选择" />
							</label>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" name="button2"
								style="border: none; color: red" class="textbg5" id="button2"
								value="确认添加"  onclick="check_add()"/>
						</td>
					</tr>
				</table>
			</s:form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
