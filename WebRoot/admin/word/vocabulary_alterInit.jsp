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
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
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
	function check(){
		var ep_title = $("#ep_title").val();
		var t_name = $("#t_name").val();
		var yingwen = $("#yingwen").val();
		var pinyin = $("#pinyin").val();
		if(ep_title===''||ep_title==null)
		{
			alert("请填写标题");
			$("#ep_title").focus() ;
			return false;
		}
		if(t_name==''||t_name==null){
			alert("请选择类别");
			$("#t_name").focus();
			return false;
		}
		if(yingwen==''||yingwen==null){
			alert("请填写英文");
			$("#yingwen").focus();
			return false;
		}
		if(pinyin==''||pinyin==null){
			alert("请填写拼音");
			$("#pinyin").focus();
			return false;
		}
	}
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
					<wysLib:Navigation ivalue="填写词汇基本信息" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<form action="vocabulary_alter.action" method="post"
				onsubmit="return check();">
				<input type="hidden" name="vocabulary.id" value="${vocabulary.id}">
				<input type="hidden" name="target" value="${target}">
				<table width="100%" align="center" cellpadding="0" cellspacing="0"
					bgcolor="#D1E4F5">
					<tr>
						<td align="center" bgcolor="#F8FCFE">
							<strong>词汇修改</strong>
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
								<span class="neededitem">*</span>词汇：							</td>
							<td bgcolor="#F8FCFE" colspan="3">
								<input type="text" name="vocabulary.name" id="ep_title"
									value="${vocabulary.name }" size="60" />							
							</td>
						</tr>
						<tr>
							<td height="25px" align="right" bgcolor="#F8FCFE">
								<span class="neededitem">*</span>类别：	</td>
								<td bgcolor="#F8FCFE" colspan="3">
								
									<label> 
								<!-- 	<s:textfield id="t_name" size="40" disabled="true"  /> -->	
										<input value="${vocabulary.word.name }" type="text" id="t_name">
										<s:hidden name="course.teacherName" id="t_hName"/>
										<s:hidden name="vocabulary.wordid" id="t_id"/>
										<input class="textbg6" type="button" onClick="searchUserInit('messUser')" value="查 找">
									</label>						
								</td>
							
						</tr>
						<tr>
							<td height="25px" align="right" bgcolor="#F8FCFE">
								<span class="neededitem">*</span>英文：							
							</td>
							<td bgcolor="#F8FCFE" colspan="3">
								<input type="text" name="vocabulary.yingwen" value="${vocabulary.yingwen }" id="yingwen"/>
							</td>
						</tr>
						<tr>
							<td height="25px" align="right" bgcolor="#F8FCFE">
								<span class="neededitem">*</span>拼音：							
							</td>
							<td bgcolor="#F8FCFE" colspan="3">
								<input type="text" name="vocabulary.pinyin" value="${vocabulary.pinyin }" id="pinyin"/>
							</td>
						</tr>
						<tr>
							<td height="25px" align="right" bgcolor="#F8FCFE">
								例句：							</td>
							<td bgcolor="#F8FCFE" colspan="3">
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
									stuff.innerHTML="名称：<input type='text' style='width:200px;' value='${vocabulary.wenziliju}' name='vocabulary.wenziliju' id='stufftt_"+ii+"'/><input type='hidden' style='width:200px;' name='vocabulary.lijudizhi' id='stuff_"+ii+
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
						    	<s:iterator value="vocabulary.stuffs">
						    		<div id="ds_">
										<span id='stufft_'>
											名称：
											<input type="hidden" name='vocabulary.stuffs.description' value="<s:property value="description"/>" /> 
											<input type="hidden" name='vocabulary.stuffs.id' value="<s:property value="id"/>" /> 
											<input type='text' style='width: 200px;' name='vocabulary.stuffs.title' value="<s:property value="title"/>" />
											地址：<s:property value="description" /> </span>&nbsp;&nbsp;&nbsp;
										<a
											href="vocabularyStuff_delete.action?vocabulary.id=<s:property value="vocabulary.id"/>&stuff.id=<s:property value="id"/>">删除</a>
									</div>
						    	</s:iterator>
						    </div>
							<!-- <input type="hidden" name="examPaper.queryurl" id="ep_queryurl"
									size="40" /> -->	
								<input type="button" onClick="addSt();" class="textbg4" value="添加">
								<input type="button" onClick="deleteSt();" class="textbg4" value="删除">							</td>
						</tr>
						<tr>
							<td height="25px" align="right" bgcolor="#F8FCFE">
								文字解释：							</td>
							<td bgcolor="#F8FCFE" colspan="3">
							  <label>
									<textarea name="vocabulary.wenzijieshi" id="ep_description" cols="40" rows="4" >${vocabulary.wenzijieshi}</textarea>
								</label>							
							</td>
						</tr>
						<tr>
							<td height="25px" align="right" bgcolor="#F8FCFE">
								<span class="neededitem">*</span>声音解释：							</td>
							<td bgcolor="#F8FCFE">&nbsp;
								<script type="text/javascript">
							
								function addStufff3(i) {
									width=1060;
									height=500;
   									var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
									//var rv = window.showModalDialog("editor/editor/filemanager/browser/default/browser.html?Type=&Connector=connectors/jsp/connector",null,sFeature);
									var rv = window.showModalDialog("question_stuffList.action",null,sFeature);
									
									 if(null==rv){
									 	alert("您没选择东西！");
									 	return ;
									 }
								//	 document.getElementById("stufft_"+i).innerHTML=rv;
									 document.getElementById("stuff3").value=rv;
									 }
								var ii = 0;
								function addSt3(){
									document.getElementById("tianjia3").style.display="none";
									ii++;
									var stuff = document.createElement("div");
									stuff.id= "ds_"+ii;
									stuff.innerHTML="<input type='hidden' style='width:200px;' name='vocabulary.shengyinjieshi' id='stuff_"+ii+
									"'/>&nbsp;&nbsp;&nbsp;地址：<span style='width:200px;'  id='stufft_"+ii
									+"'></span>&nbsp;&nbsp;&nbsp;<a class='textbg4' style='cursor:pointer;width:90px' onclick='addStufff3("+
									ii+")'>浏览资源库</a>";
									document.getElementById("stuff3").appendChild(stuff);
									
								}
								function deleteSt3(){
									if(ii<=0)return ;
									var stuff = document.getElementById("ds_"+ii);
									document.getElementById("stuff3").removeChild(stuff);
									ii--;
									document.getElementById("tianjia3").style.display="block";	
								}
								function getT(){
								var o = document.getElementsByTagName("input");
									for(var i=0;i<o.length;i++){
										alert(o[i].name+"==="+o[i].value);
									}
								}
							</script>
							<div >
								地址：
									<input id="stuff3" type="text" name='vocabulary.shengyinjieshi'  value="${vocabulary.shengyinjieshi }">
								<a class='textbg4' style='cursor:pointer;width:90px' onclick='addStufff3(0)'>浏览资源库</a>
							</div>
							<!-- <input type="hidden" name="examPaper.queryurl" id="ep_queryurl"
									size="40" /> 
								<input id="tianjia3" type="button" onClick="addSt3();" class="textbg4" value="添加">
								<input type="button" onClick="deleteSt3();" class="textbg4" value="删除">-->	
							</td>
							
						</tr>
						<tr>
							<td height="25px" align="right" bgcolor="#F8FCFE">
								<span class="neededitem">*</span>读音：							</td>
							<td bgcolor="#F8FCFE">&nbsp;
								<script type="text/javascript">
							
								function addStufff2(i) {
									width=1060;
									height=500;
   									var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
									//var rv = window.showModalDialog("editor/editor/filemanager/browser/default/browser.html?Type=&Connector=connectors/jsp/connector",null,sFeature);
									var rv = window.showModalDialog("question_stuffList.action",null,sFeature);
									
									 if(null==rv){
									 	alert("您没选择东西！");
									 	return ;
									 }
								//	 document.getElementById("stufft2").innerHTML=rv;
									 document.getElementById("stuff2").value=rv;
									 }
								var ii = 0;
								function addSt2(){
									document.getElementById("tianjia").style.display="none";
									ii++;
									var stuff = document.createElement("div");
									stuff.id= "ds_"+ii;
									stuff.innerHTML="<input type='hidden' style='width:200px;' name='vocabulary.duyin' id='stuff_"+ii+
									"'/>&nbsp;&nbsp;&nbsp;地址：<span style='width:200px;'  id='stufft_"+ii
									+"'></span>&nbsp;&nbsp;&nbsp;<a class='textbg4' style='cursor:pointer;width:90px' onclick='addStufff2("+
									ii+")'>浏览资源库</a>";
									document.getElementById("stuff2").appendChild(stuff);
									
								}
								function deleteSt2(){
									if(ii<=0)return ;
									var stuff = document.getElementById("ds_"+ii);
									document.getElementById("stuff2").removeChild(stuff);
									ii--;
									document.getElementById("tianjia").style.display="block";	
								}
								function getT(){
								var o = document.getElementsByTagName("input");
									for(var i=0;i<o.length;i++){
										alert(o[i].name+"==="+o[i].value);
									}
								}
							</script>
							<div>
								地址：
									<input id='stuff2' name='vocabulary.duyin' value='${vocabulary.duyin}'/>
								<a class='textbg4'  style='cursor:pointer;width:90px' onclick='addStufff2(1)'>浏览资源库</a>
							</div>
							<!-- <input type="hidden" name="examPaper.queryurl" id="ep_queryurl"
									size="40" /> 	
								<s:if test="null==vocabulary.duyin">
									<input id="tianjia" type="button" onClick="addSt2();" class="textbg4" value="添加">	
								</s:if>
						 	<input id="tianjia" type="button" onClick="addSt2();" class="textbg4" value="添加"> 
								<input type="button" onClick="deleteSt2();" class="textbg4" value="删除">-->
							</td>
							
						</tr>
						<tr>
							<td height="40" align="center" bgcolor="#F8FCFE">&nbsp;							</td>
							<td bgcolor="#F8FCFE" colspan="3">
							  <input class=textbg6 style="height: 30px;" type="submit"
									name="button2" id="button2" value="保 存" />
								<input class=textbg6 style="height: 30px;" type="button"
									name="button2"
									onclick="document.location.href='exampaper_list.action?sublibs=1';"
									id="button2" value="取 消" />
								<br />
								<span style="color: #ff0000; text-align: center"><s:property
										value="elmessage" /> </span>							</td>
						</tr>
					</table>
				</div>
			</form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
