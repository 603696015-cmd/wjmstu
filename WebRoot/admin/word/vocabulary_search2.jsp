<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<style type="text/css">
			.bottom{
				border:none;
				background-image:url(images/bofang.jpg);
				width:20px;
				height:24px; 
			}
		</style>
	<script type="text/javascript">
			function ready(add){
			//	var add = document.getElementById("mp3").value;
				document.getElementById("alarmPlayer").url=add;
				document.getElementById("alarmPlayer").controls.play();
			}
			function viewVoc(vocid){
				document.getElementById("view_Voc").innerHTML = "";
				$.ajax({async:true,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
					type:"post",
					url:"vocabulary_view2.action",data:{"vocabulary.id":vocid},success:function (data) {
				   		$("#view_Voc").html("<div><span style='margin-left: 360px; color: #000;'><a  onClick='closeVoc();'><img src='images/datigaiban/wrong.png' width='15' height='15'  /></a></span></div>");
				   		$("#view_Voc").append(data);
				   	//	$("#stem_Voc").css("display","none");
				   		$("#view_Voc").css("display","block");
				   		
				},error:function(msg){
					//alert("出现出错，出现此错误请检查本机网络，或联系管理员检查服务！");
					//$("#loading_"+blockid).css("display","none");
				}});
			}
			</script>
			
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
				<param name="mute" value="false">
				<param name="playCount" value="1">
				<param name="rate" value="1">
				<param name="uiMode" value="invisible">
				<param name="volume" value="100">
				
			</object>
		

<table width=370 height=300 align="center" cellpadding="1"
	cellspacing="1" style="margin-top: 0px;">
	<tr>
		<td align="center" valign="top" bgcolor="#F8FCFE">
			<table width="100%" border="0" align="center" cellpadding="1"
				cellspacing="1" bgcolor="#CFDBE2">
				<tr>

					<td height="40" align="center" background="images/bg002.jpg">
						词汇
					</td>
					<td height="30" align="center" background="images/bg002.jpg">
						拼音
					</td>
					<td width="40" height="30" align="center"
						background="images/bg002.jpg">
						读音
					</td>
				</tr>
				<tbody>
					<s:iterator value="vocabularys">
						<tr>

							<td height="40" align="center" bgcolor="#F8FCFE">
								<a onClick="viewVoc(<s:property value="id" />)"
									style="font-size: 16px; color: #FF3300; font-weight: bold;"><s:property
										value="name" />
								</a>
							<td height="30" align="center" bgcolor="#F8FCFE">
								<s:property value="pinyin" />
							<td height="30" align="center" bgcolor="#F8FCFE">
								<input type="button" class="bottom" onClick="ready('${duyin }')">
						</tr>
					</s:iterator>
				</tbody>
			</table>
			