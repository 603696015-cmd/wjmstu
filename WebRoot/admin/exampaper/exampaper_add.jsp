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
		<script type="text/javascript" src="js/exampaperop.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
	var ii = 0;
	function addSt(){
		ii++;
		var stuff = document.createElement("div");
		stuff.id= "ds_"+ii;
		stuff.innerHTML="名称：<input type='text' style='width:200px;' name='eatitle' id='stufftt_"+ii+"'/>"+
		"&nbsp;&nbsp;&nbsp;地址：<input type='text' name='eahref' style='width:200px;' id='stufft_"+ii
		+"'> &nbsp;&nbsp;&nbsp; ";
		document.getElementById("stuff").appendChild(stuff);
	}
	function deleteSt(){
		if(ii<=0)return ;
		var stuff = document.getElementById("ds_"+ii);
		document.getElementById("stuff").removeChild(stuff);
		ii--; 
	}
</script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%;
	padding-left: 8px;
}

tr {
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
</style>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="填写试卷基本信息" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">添加试卷 </span>
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
			<form action="exampaper_add.action" method="post"
				onsubmit="return addepbaseinfo();">
				<table width="100%" align="center" cellpadding="0" cellspacing="0"
					bgcolor="#D1E4F5">
					<tr>
						<td align="center" bgcolor="#F8FCFE">
							<strong>试卷基本信息</strong>（保存之后可添加大题）
						</td>
						<td width="40" bgcolor="#F8FCFE">
						</td>
					</tr>
				</table>
				<div id="ep_baseinfo">
				  <table width="100%" align="center" cellpadding="0" cellspacing="1"
						bgcolor="#D1E4F5">
						<tr>
							<td height="25px" align="right" bgcolor="#F8FCFE">
								<span class="neededitem">*</span>试卷标题：
							</td>
							<td bgcolor="#F8FCFE" colspan="3">
								<input type="text" name="examPaper.title" id="ep_title"
									size="60" />
							</td>
						</tr>
						<tr>
							<td height="25px" align="right" bgcolor="#F8FCFE">
								<span class="neededitem">*</span>试卷呈现方式：
							</td>
							<td bgcolor="#F8FCFE" colspan="3">
							  一屏一题：<input type="radio" name="examPaper.showType" value=0 checked />
								一屏一卷：<input type="radio" name="examPaper.showType" value=5 />
								知识竞赛：<input type="radio" name="examPaper.showType" value=10 />
							</td>
						</tr>
						<tr>
							<td height="25px" align="right" bgcolor="#F8FCFE">
								查询题网站链接：
							</td>
							<td bgcolor="#F8FCFE" colspan="3">
						    <div id="stuff"></div>
								<input type="hidden" name="examPaper.queryurl" id="ep_queryurl"
									size="40" />
								（http://www.gd.com,注：【http://】不要漏掉）
								<input type="button" onClick="addSt();" class="textbg4" value="添加">
								<input type="button" onClick="deleteSt();" class="textbg4" value="删除">
							</td>
						</tr>
						<tr>
							<td height="25px" align="right" bgcolor="#F8FCFE">
								<span class="neededitem">*</span>所属试卷库：
							</td>
							<td bgcolor="#F8FCFE" colspan="3">
							  <select name="examPaper.epl.id" id="ep_eplid">
									<wysLib:elibselect selectid="1"></wysLib:elibselect>
								</select>
							</td>
						</tr>
						<tr>
							<td height="25px" align="right" bgcolor="#F8FCFE">
								试卷说明：
							</td>
							<td bgcolor="#F8FCFE" colspan="3">
							  <label>
									<textarea name="examPaper.description" id="ep_description"
										cols="40" rows="4"></textarea>
								</label>
							</td>
						</tr>
						<tr>
							<td height="25px" align="right" bgcolor="#F8FCFE">
								<span class="neededitem">*</span>试卷时长：
							</td>
							<td bgcolor="#F8FCFE">
							  <input type="text" name="examPaper.during" id="ep_during"
									value="120" size="6" />
								(分钟)
							</td>
							<td height="25px" align="right" bgcolor="#F8FCFE">
								<span class="neededitem">*</span>试题总分：
						  </td>
							<td bgcolor="#F8FCFE">
								<input type="text" name="examPaper.ep_tscore" id="ep_score"
									size="6" value="100" />
							</td>
						</tr>
						<!--<tr>
							<td width="160" align="center" >
								出题方式
							</td>
							<td bgcolor="#FFFFFF" colspan="3">
								<label>
									普通显示
								</label>
								<input type="radio" name="examPaper.showmod" value="0"
									checked="checked" />
								&nbsp;&nbsp;
								<label>
									逐题显示
								</label>
								<input type="radio" name="examPaper.showmod" value="1" />
							</td>
						</tr>
						-->
						<tr>
							<td height="40" align="center" bgcolor="#F8FCFE">&nbsp;
								
							</td>
							<td bgcolor="#F8FCFE" colspan="3">
							  <input class=textbg6 style="height: 30px;" type="submit"
									name="button2" id="button2" value="保 存" />
								<input class=textbg6 style="height: 30px;" type="button"
									name="button2"
									onclick="document.location.href='exampaper_list.action?sublibs=1';"
									id="button2" value="取 消" />
								<br />
								<span style="color: #ff0000; text-align: center"><s:property
										value="elmessage" /> </span>
							</td>
						</tr>
					</table>
				</div>
			</form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
