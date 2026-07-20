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
	<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />

		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="wsj_phone/css/system.css" />
		<link rel="stylesheet" type="text/css" href="wsj_phone/css/manage.css" />
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="editor/fckeditor.js"></script>
		<SCRIPT type="text/javascript">
			function myload(){
				var oFCKeditor = new FCKeditor('content') ;
				oFCKeditor.BasePath = "editor/" ;
				oFCKeditor.Height = 300;
				oFCKeditor.Width = 320;
				oFCKeditor.ReplaceTextarea();
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
	</HEAD>
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
	
	
	<!--<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="填写基本内容" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">添加知识</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="myknowledge_list.action">我的知识</a>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>-->
		
		<!-- 内容 -->
		<div style="margin-top: 0px; width:320px">
			<form action="knowledge_add.action" method="post"
				onSubmit="return doSubmit();">
				<table width="320" border="0" align="left" cellpadding="0" cellspacing="1" bgcolor="#CFDBE2">
					<tr>
						<td width="80" height="40" align="right" bgcolor="#F8FCFE">
							<span class="neededitem">*</span>知识名称：						</td>
						<td height="40" align="left" bgcolor="#F8FCFE">
							<input type="text" name="knowledge.title" size="40" id="kTitle" style="margin-left:5px; width:100px">
					  </td>
					</tr>
					<!--<tr>
						<td height="30" align="left" >
							视频地址
						</td>
						<td height="30" align="left" >
							<input type="text" name="knowledge.wendang" id="wendang" size="40">(<a style="color: black;font-weight: bolder;" href="javascript:setUrl('wendang');">浏览我的资源库</a>若该知识是视频类，请填写！)
						</td>
					</tr>-->
					<tr>
						<td height="40" align="right" bgcolor="#F8FCFE">
							<span class="neededitem">*</span>知识类别：						</td>
						<td height="40" align="left" bgcolor="#F8FCFE">
							<!--<select name="knowledge.kltype.id">
								<s:iterator value="kltypes">
									<option value="<s:property value="id"/>">
										<s:property value="name" />
										<wysLib:kltype_select />
									</option>
								</s:iterator>
							</select>-->
							<select name="knowledge.kltype.id" style="margin-left:5px;">
								<wysLib:kltype_select selectid="1" />
							</select>

					  </td>
					</tr>
					<tr>
						<td height="40" align="right" bgcolor="#F8FCFE">
							附件：						</td>
						<td height="40" bgcolor="#F8FCFE">
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
										 var url=getUrlPath($("#stufft_"+i),true);
										 var stuffTitle=url.substring(url.lastIndexOf("/")+1,url.lastIndexOf("."));
										 document.getElementById("stufftt_"+i).value=stuffTitle;
									}
									var ii = 0;
									function addSt(){
										ii++;
										var stuff = document.createElement("div");
										stuff.id= "ds_"+ii;
										stuff.innerHTML="名称：<input type='text' style='width:200px;' name='knowledge.stuffs.title' id='stufftt_"+ii+"'/><input type='hidden' style='width:200px;' name='knowledge.stuffs.description' id='stuff_"+ii+
										"'/>&nbsp;&nbsp;&nbsp;地址：<span style='width:200px;'  onmouseover=\"getUrlPath(this)\" id='stufft_"+ii
										+"'></span>&nbsp;&nbsp;&nbsp;<a onclick='addStufff("+
										ii+")' class='textbg4' style='width:80px'>浏览资源库</a>";
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
							<input type="button" onClick="addSt();" value="添加" class="textbg4" style="margin-left:5px;"/>
							<input type="button" onClick="deleteSt();" class="textbg4" value="删除"/>
							<!-- <input type="button" onClick="getT();" value="xx"/> -->
					  </td>
					</tr>

			  </table>
				<div style="height: 210px; width: 320px; text-align: center;">
	        <textarea id="content" name="knowledge.content"
						style="width: 100%; height: 200px; visibility: hidden;"></textarea>
					<br>

					<input type="submit" value="确认提交" 
						style="border: none;" class="textbg5">
				</div>
			</form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>

