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
		<TITLE>网络考试系统--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="editor/fckeditor.js"></script>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<SCRIPT type="text/javascript">
		function myload(){
				var oFCKeditor = new FCKeditor('content') ;
				oFCKeditor.BasePath = "editor/" ;
				oFCKeditor.Height = 400;
				oFCKeditor.Width = 980;
				oFCKeditor.ReplaceTextarea();
				
				//setCurTime("releasetime");
			}
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
			}
			function doSubmit(){
			/*
				var titleObj=document.getElementById("newsName");
				var title=titleObj.value.replace(/(\s*$)/g, "");
				if(title==""){
					alert("新闻名称不能为空!");
					titleObj.focus();
					return false;
				}
			*/
				if(!$.trim($("#newsName").val()).length > 0){
					alert("新闻名称不能为空!");
					$("#newsName").focus();
					return false;
				}
				if($("#releasetime").val() == ""){
					alert("发布时间不能为空!");
					$("#releasetime").focus();
					return false;
				}
				if($("#newsstyle").val() == 0){
					alert("请选择新闻类型!");
					$("#newsstyle").focus();
					return false;
				}
				//releasetime
				return true;
			}
		</SCRIPT>
		<style type="text/css">
<!--
.STYLE2 {
	font-size: 12
}
-->
</style>
	</HEAD>
	<body onLoad="myload();">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="新闻修改内容页" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">新闻公告修改</span>
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
		
		<s:form action="news_alter" method="post" name="catalog_info"
			theme="simple" onsubmit="return doSubmit();">
			<s:hidden name="news.hot" />
			<table width="980px" align="center" cellpadding="1" cellspacing="1"
				bgcolor="#ABE2E2">
				<s:hidden name="news.status"></s:hidden>
				<tr>
					<td align="right" bgcolor="#FFFFFF">
						<span class="neededitem">*</span><span class="STYLE2">
							新闻公告名称：</span>
					</td>
					<td bgcolor="#FFFFFF">
						<span class="STYLE2"> <label>
								<s:textfield name="news.title" id="newsName" size="60" />
							</label> </span>
					</td>
				</tr>
				<tr>
					<td align="right" bgcolor="#FFFFFF">
						<span class="STYLE2"> 首页新闻图片：</span>
					</td>
					<td bgcolor="#FFFFFF">
						<span class="STYLE2"> <label>
								<s:textfield name="news.mainimg" id="mainimg" size="60" />
								(
								<a style="color: black; font-weight: bolder;"
									href="javascript:setUrl('mainimg');" class="textbg">浏览资源库</a>若是需要在首页显示图片，请填写！)
							</label> </span>
					</td>
				</tr>
				<tr>
					<td align="right" bgcolor="#FFFFFF">
						<span class="neededitem">*</span><span class="STYLE2">
							所属类别： </span>
					</td>
					<td bgcolor="#FFFFFF">
						<span class="STYLE2"> <label>
								<select name="news.ntype.id" id="parentid">
									<wysLib:newsTypeSelect selectid="${news.ntype.id}"></wysLib:newsTypeSelect>
								</select>
							</label> </span>
					</td>
				</tr>
				<tr style="display:none">
					<td align="right" bgcolor="#FFFFFF">
						<span class="neededitem">*</span><span class="STYLE2">
							所属类型： </span>
					</td>
					<td bgcolor="#FFFFFF">
						<span class="STYLE2"> <label>
								<s:select list="lnss" listKey="id" listValue="name" headerKey="3" headerValue="—全部—" name="news.nstyle.id" id="newsstyle"></s:select>
							</label> </span>
					</td>
				</tr>
				<%-- 
				<tr>
					<td align="center" >
						推荐
					</td>
					<td > 
						<select name="news.hot">
							<option <s:if test="news.hot==0">selected='selected'</s:if>
								value="0">
								普通
							</option>
							<option <s:if test="news.hot==1">selected='selected'</s:if>
								value="1">
								推荐
							</option>
							<option <s:if test="news.hot==2">selected='selected'</s:if>
								value="2">
								热门
							</option>
							<option <s:if test="news.hot==3">selected='selected'</s:if>
								value="3">
								重点
							</option>
							<option <s:if test="news.hot==4">selected='selected'</s:if>
								value="4">
								头条
							</option>
							
							<!--<option <s:if test="news.hot==2">selected='selected'</s:if> value="2">
							热门
						</option>
						<option <s:if test="news.hot==3">selected='selected'</s:if> value="3">
							重点
						</option>
						<option <s:if test="news.hot==5">selected='selected'</s:if> value="5">
						 焦点 
						</option>
					-->
						</select>
					</td>
				</tr>
				 --%>
				<tr>
					<td align="right" bgcolor="#FFFFFF">
						<span class="neededitem">*</span><span class="STYLE2">
							发布时间：</span>
					</td>
					<td align="left" bgcolor="#FFFFFF">
						<input name="news.releasetime" type="text" class="Wdate "
							id="releasetime" onClick="setday(this)" readonly="readonly"
							value="<s:date name="news.releasetime"/>" />
					</td>
				</tr>
				<tr>
					<td height="30" align="right" bgcolor="#FFFFFF">
						<span class="STYLE2"> 附件：</span>
					</td>
					<td bgcolor="#FFFFFF">
						<span class="STYLE2"> <script type="text/javascript">
								
									function addStufff(i) {
										/*width=600;
										height=400;
									   var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
										var   rv   =  window.showModalDialog("question_stuff_mylist.action?pN=0&pS=10",
										 null,sFeature);
										 //if("undefined"!=rv)
										 document.getElementById("stufft_"+i).innerHTML=rv;
										rv= rv.substring(rv.lastIndexOf("/")+1);
										rv = rv.substring(0,rv.lastIndexOf("."));
										*/
										width=1060;
										height=500;
   										var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
										//var rv = window.showModalDialog("editor/editor/filemanager/browser/default/browser.html?Type=&Connector=connectors/jsp/connector",null,sFeature);
										var rv = window.showModalDialog("question_stuffList.action",null,sFeature);
										
										 if(null==rv){
										 	alert("您没选择东西！");
										 	return ;
										 }
										 //alert(rv);
										 document.getElementById("stufft_"+i).innerHTML=rv;
										 document.getElementById("stuff_"+i).value=rv;
										 }
									var ii = 0;
									function addSt(){
										ii++;
										var stuff = document.createElement("div");
										stuff.id= "ds_"+ii;
										stuff.innerHTML="名称： <input type='text' style='width:200px;' name='news.stuffs.title' id='stufftt_"+ii+"'/>地址：<input type='hidden' style='width:200px;' name='news.stuffs.description' id='stuff_"+ii+"'/><span style='width:200px;'  id='stufft_"+ii+"'></span>&nbsp;&nbsp;&nbsp;<a class='textbg4' style='cursor:pointer;width:90px'  onclick='addStufff("+ii+")'>浏览资源库</a>";
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
											if(o[i].name=='news.stuffs.description')
											alert(o[i].name+"=="+o[i].value);
										}
									}
								</script> </span>
						<div class="STYLE2" id="stuff">
							<s:iterator value="news.stuffs" status="stst">
								<div id="ds_">
									<span id='stufft_'>名称：<input type="hidden"
											name='news.stuffs.description'
											value="<s:property value="description"/>" /> <input
											type="hidden" name='news.stuffs.id'
											value="<s:property value="id"/>" /> <input type='text'
											style='width: 200px;' name='news.stuffs.title'
											value="<s:property value="title"/>" />地址：<s:property
											value="description" /> </span>&nbsp;&nbsp;&nbsp;
									<a
										href="newsStuff_delete.action?news.id=<s:property value="news.id"/>&stuff.id=<s:property value="id"/>">删除</a>
								</div>
							</s:iterator>
						</div>
						<span class="STYLE2"> <input type="button"
								onClick="addSt();" class="textbg4" value="添加" /> <input type="button"
								onClick="deleteSt();" value="删除" class="textbg4" /> </span>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center" bgcolor="#FFFFFF">
						<span class="STYLE2"> 新闻公告内容 </span>
						<br />
						<span style="font-size: 14px; color: blue"><strong>注意：</strong>在下面编辑器中使用的图片宽度过大时，请自行调整，以免造成页面样式混乱！</span>
					</td>
				</tr>
			</table>
			<div style="text-align: center; width: 100%">
				<s:textarea name="news.content" id="content" cols="60" rows="7"
					cssStyle="width: 980px; height: 440px;; visibility: hidden;" />
			</div>
			<div style="text-align: center;">
				<input class="textbg6" type="submit" value="确认修改">
			</div>
			<s:hidden name="news.id">
			</s:hidden>
		</s:form>
	
	</body>
</HTML>
