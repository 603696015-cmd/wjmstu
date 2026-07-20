<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<TABLE cellSpacing=0 cellPadding=0 width=370 bgColor=#d1e4f5
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
											<a onClick="fanghui();"  id="fanhui"><span
												style="font-size: 14px; font-weight: bold; color: white;">BACK</span>
											</a>
										</td>
									</tr>
								</table>
							</TD>
						</TR>
					</TBODY>
				</TABLE>
<TABLE width=370 height=300 align=center cellPadding=0 cellSpacing=1>
	<TBODY>
		<TR>
			<TD height=40 align=right bgcolor="#F8FCFE">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td align="right">
							<span class="STYLE4">拼音：</span>
						</td>
						<td width="35">
							<div id="stuff2"></div>
							<input type="button" class="bottom"
								onClick="ready('${vocabulary.duyin}')">
						</td>
					</tr>
				</table>
			</TD>
			<TD colSpan=3 bgcolor="#F8FCFE">
				<s:property value="vocabulary.pinyin" />
			</TD>
		</TR>
		<TR>
			<TD height=40 align=left bgcolor="#F8FCFE">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td align="right">
							<span class="STYLE4">英文：</span>
						</td>
						<td width="35">
						</td>
					</tr>
				</table>
			</TD>
			<TD colSpan=3 bgcolor="#F8FCFE">
				<s:property value="vocabulary.yingwen" />
			</TD>
		</TR>
		<TR>
			<TD height=40 align=right bgcolor="#F8FCFE">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td align="right" bgcolor="#F8FCFE">
							解释：
						</td>
						<td width="35" bgcolor="#F8FCFE">
							<div id="stuff3"></div>
							<input type="button" class="bottom"
								onClick="ready('${vocabulary.shengyinjieshi }')">
						</td>
					</tr>
				</table>

			</TD>
			<TD colSpan=3 bgcolor="#F8FCFE">
				<label>

					<s:property value="vocabulary.wenzijieshi" />
				</label>
			</TD>
		</TR>
		<TR>
			<TD height=30 colspan="4" align=right bgcolor="#F8FCFE">
				<table width="98%" border="0" align="center" cellpadding="0"
					cellspacing="0" class="borderb">
					<tr>
						<td width="140" height="30">
							<table width="100" height="30" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td align="center" bgcolor="#49A7E7">
										<span>相关例句</span>
									</td>
								</tr>
							</table>
						</td>
						<td bgcolor="#F8FCFE">
							&nbsp;

						</td>
					</tr>
				</table>
			</TD>
		</TR>
		<s:iterator value="vocabulary.stuffs">
			<tr>

				<td width="100" height="40" align="right" bgcolor="#F8FCFE">
					<span class="STYLE4">例句：</span>
					<input type="button" class="bottom"
						onClick="ready('${description }')">
				</td>
				<td colspan="3" bgcolor="#F8FCFE">
					<s:property value="title" />
					<div id="stuff"></div>
				</td>

			</tr>
		</s:iterator>
		<TR>
			<TD height=15 align=right bgcolor="#F8FCFE">
				&nbsp;

			</TD>
			<TD height="15" colSpan=3 bgcolor="#F8FCFE">
		</TR>
</TABLE>
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
<script type="text/javascript">
function ready(add){
	document.getElementById("alarmPlayer").url=add;
	document.getElementById("alarmPlayer").controls.play();
}
function fanghui(){
//	alert(111);
//	window.history.go(-1);
	if($("#view_Voc").css("display")=="block"){
		$("#view_Voc").css({  display:"none" });
	}
}
</script>