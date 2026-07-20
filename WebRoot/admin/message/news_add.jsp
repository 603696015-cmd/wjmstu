<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.common.HttpRequestDeviceUtils"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
			HttpRequestDeviceUtils mobile=new HttpRequestDeviceUtils();
			boolean isMobile=mobile.isMobileDevice(request);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<TITLE>五矿发展员工职业发展系统--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="editor/fckeditor.js"></script>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<SCRIPT type="text/javascript">
		<%if(!isMobile){%>
		function myload(){
				var oFCKeditor = new FCKeditor('content') ;
				oFCKeditor.BasePath = "editor/" ;
				oFCKeditor.Height = 400;
				oFCKeditor.Width = "100%";
				oFCKeditor.ReplaceTextarea();
				
				setCurTime("releasetime");
				
			}
			<%}%>
		function setCurTime(oid){
				var now=new Date();
				var year=now.getYear();
				var month=now.getMonth()+1;
				var day=now.getDate();
				var hours=now.getHours();
				var minutes=now.getMinutes();
				if(minutes<10){
					minutes="0"+minutes;
				}
				var seconds=now.getSeconds();
				if(seconds<10){
					seconds="0"+seconds;
				}
				var timeString = year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
				var oCtl = document.getElementById(oid);
				oCtl.value = timeString;
				//setTimeout("setCurTime('"+oid+"')",1000);
				//alert(oid);
			}
			function doSubmit(){
				var titleObj=document.getElementById("newsName");
				var title=titleObj.value.replace(/(\s*$)/g, "");
				var selectstyleObj = document.getElementById("selectstyle");
				var selectstyle = selectstyleObj.value;
				if(title==""){
					alert("新闻名称不能为空!");
					titleObj.focus();
					return false;
				}
				if(selectstyle==0){
					alert("请选择新闻类型!");
					selectstyleObj.focus();
					return false;
				}
				return true;
			}
		</SCRIPT>
				<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
</style>
	</HEAD>
	<body onLoad="myload();">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="新闻撰写页面" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">新闻公告添加</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="news_list.action?pN=0&pS=10">我的新闻公告</a>

			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<s:form action="news_add" method="post" name="catalog_info"
			theme="simple" onsubmit="return doSubmit();">
			<table width="100%" cellpadding="5" align="center" cellspacing="1"
				bgcolor="#CFDBE2">
				<s:hidden name="news.status" value="1"></s:hidden>
				<tr>
					<td width="120" height="40" align="right" bgcolor="#F8FCFE">
						<span class="neededitem">*</span>新闻公告名称：					</td>
					<td bgcolor="#F8FCFE">
						<label>
							<s:textfield name="news.title" id="newsName" size="60" />
						</label>
				  </td>
				</tr>
				<tr>
					<td width="120" height="40" align="right" bgcolor="#F8FCFE">
						首页新闻图片：					</td>
					<td bgcolor="#F8FCFE">
						<label>
							<s:textfield name="news.mainimg" id="mainimg" size="60" />
							(
							<a style="color: black; font-weight: bolder;"
								href="javascript:setUrl('mainimg');" class="textbg">浏览资源库</a>若是需要在首页显示图片，请填写！)
						</label>
				  </td>
				</tr>
				<tr>
					<td width="120" height="40" align="right" bgcolor="#F8FCFE">
						<span class="neededitem">*</span>所属类别：					</td>
					<td bgcolor="#F8FCFE">
						<label>
							<select name="news.ntype.id" id="parentid">
								<wysLib:newsTypeSelect selectid="1"></wysLib:newsTypeSelect>
							</select>
						</label>
				  </td>
				</tr>
				<tr style="display:none">
					<td width="120" height="40" align="right" bgcolor="#F8FCFE">
						<span class="neededitem">*</span>所属类型：					</td>
					<td bgcolor="#F8FCFE">
						<label>
							<s:select list="lnss" headerKey="3" headerValue="--请选择--" listKey="id"
								listValue="name" name="news.nstyle.id" id="selectstyle"></s:select>
						</label>
				  </td>
				</tr>
				<%-- 
				<tr>
					<td align="center" bgcolor="#FFFFFF">
						推荐
					</td>
					<td bgcolor="#FFFFFF">
						<select name="news.hot">
						<option value="0">
							普通
						</option>
						<option value="1">
							推荐
						</option>
						<option value="2">
							热门
						</option>
						<option value="3">
							重点
						</option>
						<option  value="4">
							头条
						</option>
						<!--<option value="2">
							热门
						</option>
						<option value="3">
							重点
						</option>
						<option value="5">
						 焦点 
						</option>
					--></select>
					</td>
				</tr>
				 --%>
				<tr>
					<td width="120" height="40" align="right" bgcolor="#F8FCFE">
						<span class="neededitem">*</span>发布时间：					</td>
					<td align="left" bgcolor="#F8FCFE">
						<input class="Wdate" name="news.releasetime" readonly="readonly"
							type="text" onClick="setday(this)" id="releasetime" />
				  </td>
				</tr>
				<tr>
					<td width="120" height="40" align="right" bgcolor="#F8FCFE">
						附件：					</td>
					<td bgcolor="#F8FCFE">
						<script type="text/javascript">
							
								function addStufff(i) {
									width=1060;
									height=500;
   									var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
									//var rv = window.showModalDialog("editor/editor/filemanager/browser/default/browser.html?Type=&Connector=connectors/jsp/connector",null,sFeature);
									var rv = window.showModalDialog("question_stuffList.action",null,sFeature);
									
									 if(null==rv){
									 	alert("您没选择东西！");
									 	return ;
									 }
									 document.getElementById("stufft_"+i).innerHTML=rv;
									 document.getElementById("stuff_"+i).value=rv;
									 }
								var ii = 0;
								function addSt(){
									ii++;
									var stuff = document.createElement("div");
									stuff.id= "ds_"+ii;
									stuff.innerHTML="名称：<input type='text' style='width:200px;' name='news.stuffs.title' id='stufftt_"+ii+"'/><input type='hidden' style='width:200px;' name='news.stuffs.description' id='stuff_"+ii+
									"'/>&nbsp;&nbsp;&nbsp;地址：<span style='width:200px;'  id='stufft_"+ii
									+"'></span>&nbsp;&nbsp;&nbsp;<a class='textbg4' style='cursor:pointer;width:90px' onclick='addStufff("+
									ii+")'>浏览资源库</a>";
									document.getElementById("stuff").appendChild(stuff);
									
								}
								function deleteSt(){
									if(ii<=0)return ;
									var stuff = document.getElementById("ds_"+ii);
									document.getElementById("stuff").removeChild(stuff);
									ii--;
										
								}
								function getT(){
								var o = document.getElementsByTagName("input");
									for(var i=0;i<o.length;i++){
										alert(o[i].name+"==="+o[i].value);
									}
								}
							</script>
						<div id="stuff">
						</div>
						<input type="button" onClick="addSt();" value="添加" class="textbg4"/>
						<input type="button" onClick="deleteSt();" class="textbg4" value="删除"/>
						<!-- <input type="button" onClick="getT();" value="xx"/> -->
				  </td>
				</tr>
				<tr>
					<td height="40" colspan="2" align="center" bgcolor="#F8FCFE">
						新闻公告内容
						<span style="font-size: 14px; color: blue"><strong>注意：</strong>在下面编辑器中使用的图片宽度过大时，请自行调整，以免造成页面样式混乱！</span>					</td>
				</tr>
		  </table>
		  <%
		   if(isMobile){
		  %>
		  <div style="text-align: center; width: 100%">
				<s:textarea name="news.content"  cols="60" rows="7"
					cssStyle="width: 100%; height: 440px;" />
			</div>
		  <%}else{ %>
			<div style="text-align: center; width: 100%">
				<s:textarea name="news.content" id="content" cols="60" rows="7"
					cssStyle="width: 100%; height: 440px;; visibility: hidden;" />
			</div>
			<%} %>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="40" align="center" valign="middle" bgcolor="#F8FCFE"><input class="textbg6" type="submit" value="确认添加"></td>
  </tr>
</table>

			
		</s:form>
	</body>
</HTML>
