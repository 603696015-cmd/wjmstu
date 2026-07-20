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
		<TITLE>词汇管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>

		<script type="text/javascript" src="js/exampaperop.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
/**	var ii = 0;
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
	}*/
	
	function searchUserInit(comp){
	     width=800;
		 height=450;
	  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
		 var rv =  window.showModalDialog("wordslib.action?sub_department=1&x="+Math.random(),null,sFeature);
		 if(null==rv){
		 	alert('您没有选择用户！');
		 }else{
		 	if(rv[0]<=0)  	alert('您没有选择用户！');
		 	else
		 	$.post("mess_getWordsLibInfoJson.action", {
				"word.id":rv[0],
				"x":Math.random
				}, 
				function (data) {
					var dataObj=eval("("+data+")");
					document.getElementById("t_id").value=dataObj.word.id;
					document.getElementById("t_name").value=dataObj.word.name;
					document.getElementById("t_hname").value=dataObj.word.name;
					document.getElementById("t_name").style.display="block";
				}); 
		 }
	} 
	function ready(add){
	//	var add = document.getElementById("mp3").value;
		document.getElementById("alarmPlayer").url=add;
		document.getElementById("alarmPlayer").controls.play();
	}
	function fanghui(){
			window.history.go(-1);
		}
</script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<STYLE type=text/css>
TD {
	LINE-HEIGHT: 150%;
	PADDING-LEFT: 8px;
	COLOR: #333333;
	FONT-SIZE: 12px
}

TR {
	
}

.bottom {
	BACKGROUND-IMAGE: url(images/bofang.jpg);
	BORDER-BOTTOM: medium none;
	BORDER-LEFT: medium none;
	WIDTH: 20px;
	HEIGHT: 20px;
	BORDER-TOP: medium none;
	BORDER-RIGHT: medium none
}

.STYLE1 {
	color: #FFFFFF;
	font-size: 16px;
	font-weight: bold;
}

.STYLE2 {
	font-size: 18px;
	font-weight: bold;
}

.STYLE3 {
	color: #FF0000
}

.tdbkblue {
	border: 1px solid rgb(212, 204, 251);
}

.borderb {
	border-bottom-width: 1px;
	border-bottom-style: solid;
	border-right-color: #666666;
}

.bodertlr {
	border: 1px solid rgb(212, 204, 251);
}

.STYLE4 {
	color: #003399;
	font-weight: bold;
	font-size: 14px;
}
</STYLE>
	</HEAD>
	<body>
		<object classid="CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6"
			type="application/x-oleobject" id="alarmPlayer" height="0" width="0">
			<param name="autoStart" value="false">
			<param name="balance" value="0">
			<param name="currentPosition" value="0">
			<param name="currentMarker" value="0">
			<param name="enableContextMenu" value="true">
			<param name="enableErrorDialogs" value="false">
			<param name="enabled" value="true">
			<param name="fullScreen" value="false">
			<param name="invokeURLs" value="false">
			<param name="mute" value="true">
			<param name="playCount" value="1">
			<param name="rate" value="1">
			<param name="uiMode" value="none">
			<param name="volume" value="100">
		</object>
		<!--<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="填写词汇基本信息" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table> -->

		<!-- 内容 -->
		<div style="margin-top: 20px;">
			<form action="vocabulary_add.action" method="post"
				onsubmit="return addepbaseinfo();">
				<TABLE cellSpacing=0 cellPadding=0 width=700 bgColor=#d1e4f5
					align=center class="bodertlr">
					<TBODY>
						<TR>
							<TD bgColor=#f8fcfe height=50 width=604 align=center
								style="padding-left: 25px;">
								<P class="STYLE2 STYLE3">
									<s:property value="vocabulary.name" />
								</P>
							</TD>
							<TD bgColor=#f8fcfe width=120>
								<table width="95" border="0" align="center" cellpadding="0"
									cellspacing="0">
									<tr>
										<td height="28" align="center" valign="middle"
											background="images/textbg.jpg">
											<a onClick="fanghui();return false;" href="#" id="fanhui"><span
												style="font-size: 14px; font-weight: bold; color: white;">BACK</span>
											</a>
										</td>
									</tr>
								</table>
							</TD>
						</TR>
					</TBODY>
				</TABLE>
				<div id="ep_baseinfo">


					<TABLE width=700 align=center cellPadding=0 cellSpacing=1
						bgcolor="#FFFFFF" class="tdbkblue">
						<TBODY>
							<!-- 	<tr>
							<td height="25px" align="right" bgcolor="#F8FCFE">
								<span class="neededitem">*</span>类别：	</td>
								<td bgcolor="#F8FCFE" colspan="3">
								
									<label> 
										<input style="display:none" type="text" id="t_name">
										<input type="hidden" name="course.teacherName" value="" id="t_hName"/>
										<input type="hidden" name="vocabulary.wordid" value="115" id="t_id"/>
										<input class="textbg6" type="button" onClick="searchUserInit('messUser')" value="查 找">
									</label>						
								</td>
							
						</tr> -->
							<TR>
								<TD width=140 height=40 align=right>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td align="right">
												<span class="STYLE4">拼音：</span>											</td>
											<td width="35" align="left">
												<div id="stuff2"></div>
												<input type="button" class="bottom"
													onClick="ready('${vocabulary.duyin}')">										  </td>
										</tr>
									</table>
							  </TD>
								<TD colSpan=3>
									<s:property value="vocabulary.pinyin" />
								</TD>
							</TR>
							<TR>
								<TD height=40 align=left>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td align="right">
												<span class="STYLE4">英文：</span>
											</td>
											<td width="35">											</td>
										</tr>
									</table>
							  </TD>
								<TD colSpan=3>
									<s:property value="vocabulary.yingwen" />
								</TD>
							</TR>
							<TR>
								<TD height=40 align=right>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td align="right" class="STYLE4">
												原文例句：
											</td>
											<td width="35" align="left">
												<div id="stuff3"></div>
												<input type="button" class="bottom"
													onClick="ready('${vocabulary.shengyinjieshi }')">
										  </td>
										</tr>
									</table>

							  </TD>
								<TD colSpan=3>
									<label>

										<s:property value="vocabulary.wenzijieshi" />
									</label>
								</TD>
							</TR>
							<TR>
								<TD>
								<DIV id=div></DIV>
								</TD>
							</TR>
							<TR>
							<TD height=40 colspan="4" align=right>
								<table width="98%" border="0" align="center" cellpadding="0"
									cellspacing="0" class="borderb">
									<tr>
										<td width="140" height="40">
											<table width="120" height="40" border="0" cellpadding="0"
												cellspacing="0">
												<tr>
													<td align="center" bgcolor="#49A7E7">
														<span class="STYLE1">其他例句</span>
													</td>
												</tr>
											</table>
										</td>
										<td>&nbsp;
											
										</td>
									</tr>
								</table>
							</TD>
							</TR>
							<s:iterator value="vocabulary.stuffs">
								<tr>

									<td width="100" height="40" align="right">
										<span class="STYLE4">例句：</span>
										<input type="button" class="bottom"
											onClick="ready('${description }')">
									</td>
									<td colspan="3">
										<s:property value="title" />
										<div id="stuff"></div>
									</td>

								</tr>
							</s:iterator>
							<TR>
								<TD height=15 align=right>&nbsp;
									
								</TD>
								<TD height="15" colSpan=3>
							</TR>
					</TABLE>

				</div>
			</form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
