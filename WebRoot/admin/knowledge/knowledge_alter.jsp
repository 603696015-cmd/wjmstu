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
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="editor/fckeditor.js"></script>
		<SCRIPT type="text/javascript">
		function myload(){
				var oFCKeditor = new FCKeditor('content') ;
				oFCKeditor.BasePath = "editor/" ;
				oFCKeditor.Height = 400;
				oFCKeditor.Width ="100%";
				oFCKeditor.ReplaceTextarea();
				getUrlPath($("#mainimg"));
			}
		function doSubmit(){
			var title=document.getElementById("kTitle");
			title=title.value.replace(/(\s*$)/g, "");
			if(title==""){
				alert("知识名称不能为空!");
				return false;
			}
			return true;
		}
		</SCRIPT>
		<style type="text/css">
<!--
td {
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
					<wysLib:Navigation ivalue="修改知识文章" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">修改知识</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="myknowledge_list.action">我的知识</a>
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
			<s:form action="knowledge_alter" method="post" theme="simple"
				onsubmit="return doSubmit();">
				<table width="100%" align="center" cellpadding="2" cellspacing="1"
					bgcolor="#ABE2E2">
					<tr>
						<td height="30" align="right" bgcolor="#EBEBEB">
							<span class="neededitem">*</span>知识名称：
							<s:hidden name="knowledge.id"></s:hidden>

						</td>
						<td height="30" align="left" bgcolor="#EBEBEB">
							&nbsp;<s:textfield name="knowledge.title" size="40" id="kTitle" />
						</td>
					</tr>
					<tr>
						<td height="30" align="right" bgcolor="#EBEBEB">
							页面图片：
						</td>
						<td height="30" align="left" bgcolor="#EBEBEB">
					<span class="STYLE2"> &nbsp;<input type="text"
									onmouseover="getUrlPath(this)"
									value="<s:property value="knowledge.mainimg"/>"
									name="knowledge.mainimg" id='mainimg' size="40">(<a
								style="color: black; font-weight: bolder;"
								href="javascript:setUrl('mainimg');" class="textbg4"
								style="width:80px">浏览资源库</a>若该知识是图片类，请填写！) </span>
						</td>
					</tr>
					<tr>
						<td height="30" align="right" bgcolor="#EBEBEB">
							文档在线预览：
						</td>
						<td height="30" align="left" bgcolor="#EBEBEB">
					<span class="STYLE2"> &nbsp;<input type="text"
									onmouseover="getUrlPath(this)"
									value="<s:property value="knowledge.swf"/>"
									name="knowledge.swf" id='swf' size="40"><a
								style="color: black; font-weight: bolder;"
								href="javascript:setUrl('swf');" class="textbg4"
								style="width:80px">浏览资源库</a> </span>
						</td>
					</tr>
					<!--<tr>
						<td height="30" align="left" >
							视频地址
						</td>
						<td height="30" align="left" >
							<input type="text" name="knowledge.wendang" value="<s:property value="knowledge.wendang"/>" id="wendang" size="40">(<a style="color: black;font-weight: bolder;" href="javascript:setUrl('wendang');">浏览我的资源库</a>若该知识是视频类，请填写！)
						</td>
					</tr>-->
					<tr>
						<td height="30" align="right" bgcolor="#EBEBEB">
							<span class="neededitem">*</span> 知识类别：
						</td>
						<td height="30" align="left" bgcolor="#EBEBEB">
						  &nbsp;<select name="knowledge.kltype.id">
								<s:iterator value="kltypes" id="st">
									<option
										<s:if test="#st.id==knowledge.kltype.id">selected='selected'</s:if>
										value="<s:property value="id"/>">
										<s:property value="name" />
									</option>
								</s:iterator>
							</select>
						</td>
					</tr>
					<tr>
						<td height="30" align="right" bgcolor="#EBEBEB">
							<span class="neededitem">*</span> 附件：
						</td>
						<td bgcolor="#EBEBEB">
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
										 getUrlPath($("#stufft_"+i),true);
									}
									var ii = 0;
									function addSt(){
										ii++;
										var stuff = document.createElement("div");
										stuff.id= "ds_"+ii;
										stuff.innerHTML="名称：<input type='text' style='width:200px;' name='knowledge.stuffs.title' id='stufftt_"+ii+"'/>地址：<input type='hidden' style='width:200px;' onmouseover=\'getUrlPath(this)\'name='knowledge.stuffs.description' id='stuff_"+ii+"'/><span style='width:200px;'  id='stufft_"+ii+"'></span>&nbsp;&nbsp;&nbsp;<a onclick='addStufff("+ii+")' class='textbg4' style='width:80px'>浏览资源库</a>";
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
											if(o[i].name=='knowledge.stuffs.description')
											alert(o[i].name+"=="+o[i].value);
										}
									}
								</script> </span>
							<div class="STYLE2" id="stuff">
								<s:iterator value="knowledge.stuffs" status="stst">
									<div id="ds_">
										<span id='stufft_'>名称：<input type="hidden"
												name='knowledge.stuffs.description'
												value="<s:property value="description"/>" /> <input
												type="hidden" name='knowledge.stuffs.id'
												value="<s:property value="id"/>" /> <input type='text'
												style='width: 200px;' name='knowledge.stuffs.title'
												value="<s:property value="title"/>" />地址：<span
											onmouseover="getUrlPath(this)"><s:property
													value="description" /> </span> </span>&nbsp;&nbsp;&nbsp;
										<a
											href="deleteKstuff.action?knowledge.id=<s:property value="knowledge.id"/>&stuff.id=<s:property value="id"/>" class="textbg4" style="cursor: pointer;">删除</a>
									</div>
								</s:iterator>
							</div>
							&nbsp;<input type="button" onClick="addSt();" class="textbg4"
								value="添加" />
							<input type="button" onClick="deleteSt();" class="textbg4"
								value="删除" />
						</td>
					</tr>
				</table>
				<div style="height: 500px; width: 100%; text-align: center;">
		      <s:textarea id="content" name="knowledge.content"
						cssStyle="width:980px;height:500px;visibility:hidden;"></s:textarea>
					<br>

					<input type="submit" value="确认提交" class="textbg5"
						style="border: none;" />
					<input type="button" onClick="history.back(-1)" value="返回"
						class="textbg5" style="border: none;" />
				</div>
			</s:form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
