<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.common.SystemConfOp"%>
<%@page import="com.sopia.ElConstants"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="newversionLib" uri="/WEB-INF/newversionLib.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>个人中心</title>
		<meta name="keywords" content="" />
		<meta name="description" content="" />
		<link href="css/global.css" rel="stylesheet" type="text/css" />
		<link href="css/index_newversion.css" rel="stylesheet" type="text/css" />
		<link type="text/css" href="css/base.css" rel="stylesheet" />
		<link type="text/css" href="css/qhIndex_newversion.css"
			rel="stylesheet" />
		<link href="css/style2013.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/113.js"></script>
		<script type="text/javascript" src="js/switch.combo.js"></script>
		<script type="text/javascript">
		function iframe(){
			document.all("rightFrame").height=rightFrame.document.body.scrollHeight;
			document.all("rightFrame").width=rightFrame.document.body.scrollWidth;
		}
		function full_screen(flag){
			if(flag){
				alert(flag);
			}else{
			}
			return false;
		}
		
		function open(action){
		 $("#stem_text").fadeToggle();
			document.all("rightFrame").src = action;
			
		}
		
		function reinitIframe(){
			var iframe = document.getElementById("rightFrame");
			try{
			var bHeight = iframe.contentWindow.document.body.scrollHeight;
			var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
			var height = Math.max(bHeight, dHeight);
			iframe.height =  height;
			}catch (ex){}
		}
		window.setInterval("reinitIframe()", 200);
		</script>
		<style type="text/css">
<!--
.STYLE1 {
	color: #FF0000;
	font-weight: bold;
	font-size: 14px;
}

.STYLE2 {
	color: #666666;
	font-weight: bold;
}
-->
	#stem_text {
	position:absolute;
	width:400px;
	height:300px;
	background:url(images/datigaiban/text_bg.png) no-repeat 0 0;
}
</style>
<script type="text/javascript">
	function init(isLogin,count,popIds){
				//弹出短消息和弹窗
				if(isLogin==1){
					var msg="";
					if(count>0){
						msg="您当前有"+count+"条未读短消息\n";
					}
					if(msg!=""){
						alert(msg);
					}
				}
				if(isLogin==1){
					//alert(popIds);
					if(popIds==""){
						return;
					}
					var popArray=popIds.split(",");
					for(var i=0;i<popArray.length;i++){
						//alert(popArray[i]);
						winOpen(popArray[i],i);
					}
				}
			}
</script>
	</HEAD>
	<body>
		<div id="container">
			

			<table width="1001" height="153" border="0" align="center"
				cellpadding="0" cellspacing="0" class=bg011>
				<tr>
					<%
						//if (NewSystemConfOp.getIntValue(ElConstants.SYSTEM_NEWINDEXCONFIG_NEWSHOUYE) == 0) {
						if (SystemConfOp.getIntValue(ElConstants.NEWSHOUYE) == 0) {
					%>
					<td width="270" align="center" valign="top"
						background="images/bgheader.jpg">
						<table width="250" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td height="145" valign="top" background="images/mybg002.jpg"
									style="padding-left: 25px; padding-top: 55px;"">
									<p>
										+++
										<span class="zc01"><s:property value="elUser.realname" />
										</span> +++
										<br />
										<span class="zp"><s:property
												value="elUser.department.name" />，<s:property
												value="elUser.role.name" /> </span>
										<br />
										<SPAN class=STYLE1>*</SPAN> 未读短消息
										<s:property value="message_no" />
										条，已读
										<s:property value="message_yes" />
										条。
										<A href="javascript:open('mess_Rec.action')">查看</A>
										
										<br />
										<br />

<a href="index.action" style="font-size:16px;color:orange;font-weight:bold;">网站首页</a>&nbsp;&nbsp;&nbsp;&nbsp; <a href="user_center.action" style="font-size:16px;color:orange;font-weight:bold;">个人首页</a>&nbsp;&nbsp;&nbsp;&nbsp; <a href="logout.action" style="font-size:16px;color:orange;font-weight:bold;">退出</a>			
							  </td>
							</tr>
						</table>
				  </td>
					<%
						} else {
					%>
					<td width="270" align="center" valign="middle"
						background="images/bgheader.jpg">
						<table width="250" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td height="205" valign="top" background="images/mybg002.jpg"
									style="padding-left: 10px; padding-top: 55px;"">
									<table width="100%" border="0" cellspacing="0" cellpadding="0"
										class=bg011>
										<tr>
											<td style="padding-bottom: 8px;">
												+++
												<span class="zc01"><s:property
														value="elUser.realname" /> </span> +++
												<br />
												<span class="zp"><s:property
														value="elUser.department.name" />，<s:property
														value="elUser.role.name" /> </span>
												<br />
												<span class="STYLE1">*</span> 未读短消息
												<s:property value="message_no" />
												条，已读
												<s:property value="message_yes" />
												条。
												<a href="javascript:open('mess_Rec.action')">查看</a>
											</td>
										</tr>
									</table>


									<table border="0" cellspacing="0" cellpadding="0" width="100%"
										style="margin-top: 8px;">
										<tbody>
											<s:iterator value="myNoPass" status="status">
												<s:if test="#status.index+1<=6">
													<s:if
														test="#status.index==0 || (#status.index!=0 && #status.index%2==0)">
														<tr>
															<td height="30" width="50%">
																<span class="STYLE1">*</span>
																<a
																	href="myContactTags.action?tablename=<s:property value='tablename' />">未审<s:property
																		value='moduleName' /> </a>
																<s:property value='count' />
															</td>
														</tr>
													</s:if>
													<s:else>
													<tr>
														<td>
															<span class="STYLE1">*</span>
															<a
																href="myContactTags.action?tablename=<s:property value='tablename' />">未审<s:property
																	value='moduleName' /> </a>
															<s:property value='count' />
														</td>
													</tr>	
													</s:else>
												</s:if>
											</s:iterator>
											<s:if test="myNoPass.size()>6">
												<tr>
													<td colspan="2" align="right" valign="top"
														style="padding-top: 5px; padding-right: 20px;">
														<a href="javascript:show();" title="查看全部"><img
																src="images/more.gif" border="0" /> </a>
													</td>
												</tr>
											</s:if>
										</tbody>
									</table>

								</td>
							</tr>
						</table>
				  </td>

					<%
						}
					%>
					<div id="stem_text" style="background:url(images/slides.png) repeat;margin-left:284px;height:252px;width:707px;z-index:999;margin-top:269px;display:none;">
			<div>
				<span style="margin-left: 688px; color: #000;"><a href="javascript:void(0);" onclick="showStemText();">
					<img src="images/datigaiban/wrong.png" width="15" height="15"  />
				</a></span>
<!--				<span id="st"  ></span>-->
			<div id="st"></div>
			</div>
		</div>
					<td valign="top" background="images/bgheader.jpg">
						<table width=98% border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td>
									<div class="wrap">
										<div class="slides">
											<newversionLib:newversionCenterMenuDiv1></newversionLib:newversionCenterMenuDiv1>
										</div>

									</div>


								</td>
							</tr>
						</table>
				  </td>
				</tr>
		  </table>

			<!--中部开始-->
			<table width="1002" border="0" align="center" cellpadding="0"
				cellspacing="0" id="frameTable"  >
				<tr>
				  <td align="center" >
				    <!-- 
				  	<iframe src="${module}"  id="rightFrame" onload="SetWinHeight(this);"
							name="rightFrame" align="middle"  
							scrolling="no" frameborder="0"
							style="z-index: 9999; padding-bottom: 0px;"
							width="1002"
							height="100%"
							> 
					</iframe>
					 -->
					 <iframe src="${module}"  id="rightFrame" 
							name="rightFrame" align="middle"  
							scrolling="no" frameborder="0"
							style="z-index: 9999; padding-bottom: 0px;"
							width="1002"
							height="100%"
							onload="this.height=100"> 
					</iframe>
				  </td>
				</tr>
		   </table>
			<!--中部结束-->

		</div>
		
		<script type="text/javascript">
		function showStemText(username){
				/**
				if($("#stem_text").css("display")=="block"){
					$("#stem_text").css({  display:"none" });
				}else{
					$("#stem_text").css({  display:"block" });
				}
				*/
				check(username);
			    $("#stem_text").fadeToggle();
			}
			
			function check(username){
	$.ajax({	async:false,  //   
				type:"post",   
			    url:"three_menu.action",   
			    data:{"x":Math.random(),"funcname":username,"optype":"ajax"},   
				success:function(data){
					//jd = eval("("+data+")");
					//bh=jd.bh+"-=wys=-"+jd.name+"-=wys=-"+jd.id;
					//alert(data);
					//document.getElementById("ceshi")=data;
					//alert(data);
					   $("#st").html(data);
			 }});
}
			</script>
	</body>
	
	
</html>
