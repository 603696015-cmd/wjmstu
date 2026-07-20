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
		<TITLE>试卷详情查看</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system3.css" />
		<link rel="stylesheet" type="text/css" href="css/manage3.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/exampaperop.js"></script>
		<link rel="stylesheet" type="text/css" href="js/tree/dtree.css" />
		<script type="text/javascript" src="js/tree/dtree.js"></script>
	<script type="text/javascript" src="js/cexampaper.js"></script>
	<style type="text/css">
		td{color:#000;padding: 0px}
		table {margin: 0xp;}
	</style>
	</HEAD>
	<body onLoad="listepblocks_details(<s:property value="examPaper.id"/>)">
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="试卷总览" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">添加试卷 </span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<span style="color: #ff0000; text-align: center"><s:property
					value="elmessage" /> </span>
			<table width="100%" align="center" cellpadding="1" cellspacing="1"
				bgcolor="#EBEBda">
				<tr>
					<td align="center" >
						试卷基本信息
					</td>
					<td width="40">
						<s:if test="examPaper.showmod==0">
							<a target="_blank"
								href="exampaper_preview.action?examPaper.id=<s:property value="examPaper.id"/>" class=textbg4>预 览</a>
						</s:if>
						<s:else>
							<a target="_blank"
								href="exampaper_preview_1b1.action?examPaper.id=<s:property value="examPaper.id"/>" class=textbg4>预 览</a>
						</s:else>
					</td> 
					<td width="40">
						<a href="" onClick="showorhidden('ep_baseinfo');return false;" class=textbg4>收 起</a>
					</td>
				</tr>
			</table>
			<div id="ep_baseinfo">
				<table width="100%" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr>
						<td width="160" align="center" >
							试卷标题
						</td>
						<td bgcolor="#FFFFFF" colspan="3">
							<label>
								<s:property value="examPaper.title" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" align="center" >
							试卷呈现方式
						</td>
						<td bgcolor="#FFFFFF" colspan="3">
							<label>
								<s:property value="examPaper.showTypeName" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" align="center" >
							所属试卷库
						</td>
						<td bgcolor="#FFFFFF" colspan="3">
							<%-- 
							<label>
									<select name="examPaper.epl.id" id="ep_eplid">
										<wysLib:elibselect selectid="1"></wysLib:elibselect>
									</select>							
							</label>
							 --%>
							 <s:property value="examPaper.epl.name"/>
						</td>
					</tr>
					<tr>
						<td width="160" align="center" >
							查询题网站链接
						</td>
						<td bgcolor="#FFFFFF" colspan="3">
							<label>
							<s:iterator value="examPaper.queryurls">
								<s:property value="title"/>:<s:property value="href"/> <br/>
							</s:iterator>
							 </label>
						</td>
					</tr>
					<tr>
						<td width="160" align="center" >
							试卷说明
						</td>
						<td bgcolor="#FFFFFF" colspan="3">
							<label>
								<s:property value="examPaper.description" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" align="center" >
							试卷时长（分钟）
						</td>
						<td >
							<label>
								<s:property value="examPaper.during" />
							</label>
						</td>
						<td width="160" align="center" >
							试题总分
						</td>
						<td >
							<s:property value="examPaper.ep_tscore" />
						</td>
					</tr><!-- 
					<tr>
						<td width="160" align="center" >
							出题方式
						</td>
						<td bgcolor="#FFFFFF" colspan="3">
							<label>
								<s:if test="examPaper.showmod==0">
						普通显示
								</s:if>
								<s:else>
						逐题显示
								</s:else>
							</label>
						</td>
					</tr> -->
				</table>
				<input id="ep_id" type="hidden"
					value="<s:property value="examPaper.id"/>" name="examPaper.id" />

			</div>
			<table width="100%" align="center" cellpadding="1" cellspacing="1"
				bgcolor="#EBEBEB">
				<tr>
					<td align="center" >
						试卷大题
					</td>
					<td width="60">  
						<a href="" onClick="showorhidden('ep_block_list');return false;"  class=textbg>收 起</a>
					</td>
				</tr>
			</table>
			<div id="ep_block_list" style="text-align: center;">
			</div> 
		</div>
		<div id="dia_"
			style="margin-top: -300px; display: none; width:auto; height: 400px; background: #ffffff; position: absolute; z-index: 9999; border: 2px solid buttonface;">
			<div style="width:100%; border-bottom: solid 1px buttonface; background: buttonface; text-align: right;">
				<a style="width: 25px; text-decoration: none;" href=""
					onclick="dia_close();return false;">X</a>
			</div>
			<div id="dia_content"></div>
		</div> 
		<br />
		<div style="text-align: center;">
		<a style="width:100px;" href="exampaper_list.action?sublibs=1" class="textbg4">返回试卷列表</a>
		</div>
		<br />
		<!-- 内容 -->
	
	</body>
</HTML>
