<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>课程类别管理</title>
		<base href="<%=basePath%>" />
		<script type="text/javascript" src="editor/fckeditor.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<style type="text/css">
body {
	width: 100%;
	text-align: left;
	background: url("images/main_36.gif");
	background-repeat: repeat-x;
}

body {
	font: 12px/ 22px tahoma, arial, '\5B8B\4F53', sans-serif;
	background-color: #fff;
	color: #000;
	margin: 0px;
	padding: 0px;
}

div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,form,fieldset,input,p,blockquote,th,td,span
	{
	margin: 0;
	padding: 0;
}

table {
	border-collapse: collapse;
	border-spacing: 0;
}

img,a img {
	border: 0;
}

.nav {
	width: 100%;
	margin: 0px;
	padding: 0px;
}

.nav li {
	height: 28px;
	display: inline;
	margin: 0px;
	padding-left: 5px;
	padding-right: 5px;
	padding-top: 2px;
}

.textbg {
	background: url("images/textbg.jpg");
	line-height: 25px;
	background-repeat: repeat-x;
	padding-bottom: 0px;
	margin: 5px;
	color: #FFFFFF;
	font-size: 13px;
	font-weight: bold;
	height: 28px;
	width: 95px;
	text-align: center;
}

.textbg4 {
	background-image: url("images/textbg.gif");
	padding: 2px;
	background-repeat: repeat-x;
	color: #FFFFFF;
	font-size: 13px;
	font-weight: bold;
	width: 40px;
	text-align: center;
	text-decoration: none;
}

.neededitem {
	position: relative;
	top: 2px;
	color: #ff0000;
	font-size: 15px;
	padding-right: 3px;
}
</style>
		<script type="text/javascript">
			<s:if test="question.qtype==2||question.qtype==3||question.qtype==4||question.qtype==15||question.qtype==16||question.qtype==18||question.qtype==115">
		  	    var optCount=4;
				var nowCount=0;
				function addOption(){
					var _optiontype = <s:if test="question.qtype==2||question.qtype==15||question.qtype==16||question.qtype==18">"radio"</s:if><s:else>"checkbox"</s:else>
					var obj = document.createElement("div");
					obj.id="option_"+nowCount;
					<s:if test="question.qtype==18">
					if(nowCount==0){
					obj.innerHTML ="选项"+String.fromCharCode(65+parseInt(nowCount))+"&nbsp;&nbsp;<input type='text' size='65' id='__option"+nowCount+"' name='question.options' />"+
					"<a href=javascript:setUrl('__option"+nowCount+"'); class='textbg4' style='width: 80px'>浏览资源库</a> </span>"+
					//"<iframe src='_editor/editor.html?height=200&id=__option"+nowCount+"' frameborder='0' scrolling='no' width='500' height='120'></iframe>&nbsp;&nbsp;
					//"<iframe id='opt_frame"+nowCount+"' frameborder='0' scrolling='no' width='0' height='0'></iframe/>"+
					"<br/><span>声音文件：<input type='text' size='65' name='question.voicePaths' id='voicePath"+nowCount+"' />"+
					"<a href=javascript:setUrl('voicePath"+nowCount+"'); class='textbg4' style='width: 80px'>浏览资源库</a> </span>"+
					"&nbsp;&nbsp;设为正确答案<input name='question.answers' type='"+_optiontype+"' value='"+nowCount+"' />";
					}else{
					obj.innerHTML ="选项"+String.fromCharCode(65+parseInt(nowCount))+"&nbsp;&nbsp;<input type='text' size='65' id='__option"+nowCount+"' name='question.options' />"+
					"<a href=javascript:setUrl('__option"+nowCount+"'); class='textbg4' style='width: 80px'>浏览资源库</a> </span>"+
					//"<iframe src='_editor/editor.html?height=200&id=__option"+nowCount+"' frameborder='0' scrolling='no' width='500' height='120'></iframe>&nbsp;&nbsp;
					//"<iframe id='opt_frame"+nowCount+"' frameborder='0' scrolling='no' width='0' height='0'></iframe/>"+
					"<br/><span>声音文件：<input type='text' size='65' name='question.voicePaths' id='voicePath"+nowCount+"' />"+
					"<a href=javascript:setUrl('voicePath"+nowCount+"'); class='textbg4' style='width: 80px'>浏览资源库</a> </span>"+
					"&nbsp;&nbsp;设为正确答案<input name='question.answers' type='"+_optiontype+"' value='"+nowCount+"' />";
					}
					</s:if>
					<s:else>
					if(nowCount==0){
					obj.innerHTML ="选项"+String.fromCharCode(65+parseInt(nowCount))+"&nbsp;&nbsp;<textarea style='border:1px solid buttonface;overflow:hidden;width:500px;height:120px' onfocus='createeditor(this,"+nowCount+")' id='__option"+nowCount+"' name='question.options'></textarea>"+
					//"<iframe src='_editor/editor.html?height=200&id=__option"+nowCount+"' frameborder='0' scrolling='no' width='500' height='120'></iframe>&nbsp;&nbsp;
					//"<iframe id='opt_frame"+nowCount+"' frameborder='0' scrolling='no' width='0' height='0'></iframe/>"+
					"&nbsp;&nbsp;设为正确答案<input name='question.answers' type='"+_optiontype+"' value='"+nowCount+"' />";
					}else{
					obj.innerHTML ="选项"+String.fromCharCode(65+parseInt(nowCount))+"&nbsp;&nbsp;<textarea  style='border:1px solid buttonface;overflow:hidden; width:500px;height:120px' onfocus='createeditor(this,"+nowCount+")' id='__option"+nowCount+"' name='question.options'></textarea>"+
					//"<iframe src='_editor/editor.html?height=200&id=__option"+nowCount+"' frameborder='0' scrolling='no' width='500' height='120'></iframe>&nbsp;&nbsp;
					//"<iframe id='opt_frame"+nowCount+"'  frameborder='0' scrolling='no' width='0' height='0'></iframe/>"+
					"&nbsp;&nbsp;设为正确答案<input name='question.answers' type='"+_optiontype+"' value='"+nowCount+"' />";
					}
					</s:else>
					document.getElementById("option_area").appendChild(obj);
					++nowCount;
				}
				function createeditor(obj,id){
					//alert("dd"+id);
					//$("#opt_frame"+id).attr("src","_editor/editor.html?height=200&id=__option"+id);
					//$("#opt_frame"+id).attr("width",500);
					//$("#opt_frame"+id).attr("height",120);
					var oFCKeditor = new FCKeditor(obj.id) ;
					oFCKeditor.BasePath = "editor/" ;
					oFCKeditor.Height = 120;
					oFCKeditor.Width = 500;
					oFCKeditor.ToolbarSet = "qoption" ;
					oFCKeditor.ReplaceTextarea();
					oFCKeditor.focus();
				}
				function removeOption(){
					if(nowCount<=2) {
						alert('选项请不能小于2个');
					}
					else{
					--nowCount;
					var obj = document.getElementById("option_"+nowCount);
					document.getElementById("option_area").removeChild(obj);
					}
				}
				function setAnswer(answer){
					var qaInput = document.getElementsByName("question.answers");
					for(var i=0;i<qaInput.length;i++){
						if(answer==qaInput[i].value)
						{
							qaInput[i].checked='checked';
						}
					}
				}
				function intoABC(i,id){
					if(!i.match(/^\d$/)){i=0}
					
					document.getElementById(id).innerHTML=String.fromCharCode(65+parseInt(i)); 
				}
			</s:if>
			<s:if test="question.qtype==5||question.qtype==20">
				var nowCount=0;
				function addBlankAnswer( ){
					var obj = document.createElement("div");
					obj.id="BlankAnswer_"+nowCount;
					obj.innerHTML ="空白答案"+(nowCount+1)+"&nbsp;&nbsp;<input type='text' name='question.answers'/>" 
					document.getElementById("BlankAnswer_area").appendChild(obj);
					++nowCount;
				}
				function removeBlankAnswer(){
					if(nowCount<=1) {
						alert('空白处答案请不能小于1个');
					}
					else{
					--nowCount;
					var obj = document.getElementById("BlankAnswer_"+nowCount);
					document.getElementById("BlankAnswer_area").removeChild(obj);
					}
				}
			</s:if>
			<s:if test="question.qtype==19">
				var nowCount=0;
				function setAnswer(answer){
					var qaInput = document.getElementsByName("question.answers");
					for(var i=0;i<qaInput.length;i++){
						if(answer==qaInput[i].value)
						{
							qaInput[i].checked='checked';
						}
					}
				}
				function addBlankAnswer( ){
					var obj = document.createElement("div");
					obj.id="BlankAnswer_"+nowCount;
					obj.innerHTML ="空白答案"+(nowCount+1)+"&nbsp;&nbsp;<input type='text' name='question.options'/>" 
					document.getElementById("BlankAnswer_area").appendChild(obj);
					++nowCount;
				}
				function removeBlankAnswer(){
					if(nowCount<=1) {
						alert('空白处答案请不能小于1个');
					}
					else{
					--nowCount;
					var obj = document.getElementById("BlankAnswer_"+nowCount);
					document.getElementById("BlankAnswer_area").removeChild(obj);
					}
				}
			</s:if>
			<s:if test="question.qtype==6">
				var nowCount=0;
				function addBlankAnswer( ){
					var obj = document.createElement("div");
					obj.id="BlankAnswer_"+nowCount;
					obj.innerHTML ="答案关键词"+(nowCount+1)+"&nbsp;&nbsp;<input type='text' name='question.answers'/>" 
					document.getElementById("BlankAnswer_area").appendChild(obj);
					++nowCount;
				}
				function removeBlankAnswer(){
					if(nowCount<=1) {
						alert('答案关键词请不能小于1个');
					}
					else{
					--nowCount;
					var obj = document.getElementById("BlankAnswer_"+nowCount);
					document.getElementById("BlankAnswer_area").removeChild(obj);
					}
				}
			</s:if>
				<s:if test="question.qtype==19">
		  	    var optCount=4;
				var nowCount1=0;
				function addTigan(){
					var obj = document.createElement("div");
					obj.id="tigan_"+nowCount1;
					if(nowCount1==0){
					obj.innerHTML ="分题干"+(nowCount1+1)+"&nbsp;&nbsp;<textarea style='border:1px solid buttonface;overflow:hidden;width:500px;height:120px' onfocus='createeditor(this,"+nowCount1+")' id='__tigan"+nowCount1+"' name='question.fenContents'></textarea>";
					}else{
					obj.innerHTML ="分题干"+(nowCount1+1)+"&nbsp;&nbsp;<textarea  style='border:1px solid buttonface;overflow:hidden; width:500px;height:120px' onfocus='createeditor(this,"+nowCount1+")' id='__tigan"+nowCount1+"' name='question.fenContents'></textarea>";
					}
					document.getElementById("tigan_area").appendChild(obj);
					++nowCount1;
				}
				function createeditor(obj,id){
					//alert("dd"+id);
					//$("#opt_frame"+id).attr("src","_editor/editor.html?height=200&id=__option"+id);
					//$("#opt_frame"+id).attr("width",500);
					//$("#opt_frame"+id).attr("height",120);
					var oFCKeditor = new FCKeditor(obj.id) ;
					oFCKeditor.BasePath = "editor/" ;
					oFCKeditor.Height = 120;
					oFCKeditor.Width = 500;
					oFCKeditor.ToolbarSet = "qoption" ;
					oFCKeditor.ReplaceTextarea();
				}
				function removeTigan(){
					--nowCount1;
					var obj = document.getElementById("tigan_"+nowCount1);
					document.getElementById("tigan_area").removeChild(obj);
				}
				
				//答案
				//答案
				var nowCount2=0;
				function addAnswer( ){
					var obj = document.createElement("div");
					obj.id="Answer_"+nowCount2;
					obj.innerHTML ="答案文本"+(nowCount2+1)+"&nbsp;&nbsp;<input type='text' name='question.modelVoiceTexts'/>" 
					document.getElementById("answer_text").appendChild(obj);
					++nowCount2;
				}
				function removeAnswer(){
					if(nowCount2<=1) {
						alert('空白处答案请不能小于1个');
					}
					else{
					--nowCount2;
					var obj = document.getElementById("Answer_"+nowCount2);
					document.getElementById("answer_text").removeChild(obj);
					}
				}
			</s:if>
			function _onsubmit(){
					var qlibId=$("input[name='question.qlib.id']:checked").val();
					if(qlibId==undefined){
						alert("请选择题库！");
						return false;
					}
				<s:if test="question.qtype==8">
					var optionsanswers =$.trim($("#content").val());
					if(optionsanswers ==''){
						alert("范文不能为空！");
						return false;
					}
		  		</s:if>
		  		<s:else>
					<s:if test="question.qtype!=9&&question.qtype!=10">
						if(FCKeditorAPI.GetInstance("content").GetXHTML(true)==''){
							alert("题干不能为空");
							//document.getElementById("content").focus();
							return false;
						}
					</s:if>
				</s:else>
				
				<s:if test="question.qtype==2||question.qtype==3||question.qtype==4">
		  			var optionsanswers = document.getElementsByName("question.options");
					var _opa = 0;
					for(var _i = 0 ; _i< optionsanswers.length;_i++){
						var val = "";
						if(FCKeditorAPI.GetInstance(optionsanswers[_i].id)){
							val  = FCKeditorAPI.GetInstance(optionsanswers[_i].id).GetXHTML(true);
						}else
							val = optionsanswers[_i].value;
						val = $.trim(val);							
						//alert(val);
						if(val=='')
						//if(optionsanswers[_i].value=='')
							_opa++;
					}
					if(_opa!=0){
						alert("请填写完整选项");
						return false;
					
					}
					optionsanswers = document.getElementsByName("question.answers");
					_opa = 0;
					for(var _i = 0 ; _i< optionsanswers.length;_i++){
						if(optionsanswers[_i].checked=='checked'||optionsanswers[_i].checked)
							_opa++;
					}
					if(_opa==0){
						alert("请选择答案");
						return false;
					
					}
				</s:if>
				<s:if test="question.qtype==5||question.qtype==20">
					var optionsanswers = document.getElementsByName("question.answers");
					var _opa = 0;
					for(var _i = 0 ; _i< optionsanswers.length;_i++){
						if(optionsanswers[_i].value=='')
							_opa++;
					}
					if(_opa!=0){
						alert("答案不能是空的");
						optionsanswers[0].focus();
						return false;
					
					}
		  		</s:if>
		  		<s:if test="question.qtype==19">
					var optionsanswers = document.getElementsByName("question.answers");
					var optionsanswers__ = document.getElementsByName("question.options");
					var _opa = 0;
					for(var _i = 0 ; _i< optionsanswers.length;_i++){
						optionsanswers[_i].value=optionsanswers__[_i].value;
						if(optionsanswers[_i].checked)
							_opa++;
					}
					//if(_opa==0){
					//	alert("答案不能是空的,请选择");
					//	optionsanswers[0].focus();
					//	return false;
				//	}
					if(document.getElementById("blank_before").checked)
						$("#fwsize").attr("value","1");
					if(document.getElementById("blank_after").checked)
						$("#fwsize").attr("value","2");
					if(document.getElementById("blank_before").checked&&document.getElementById("blank_after").checked)
						$("#fwsize").attr("value","3");
		  		</s:if>
		  		<s:if test="question.qtype==6">
					if(!$.trim($("#answer").val()).length > 0){
						alert("试题答案不能是空的");
						$("#answer").focus();
						return false;
					}
		  		</s:if>
		  		<s:if test="question.qtype==9">
					if(!$.trim($("#send").val()).length > 0){
						alert("发给不能是空的");
						$("#send").focus();
						return false;
					}
					if(!$.trim($("#cc").val()).length > 0){
						alert("抄送不能是空的");
						$("#cc").focus();
						return false;
					}
					if(!$.trim($("#min").val()).length > 0){
						alert("密送不能是空的");
						$("#min").focus();
						return false;
					}
					if(!$.trim($("#zhu").val()).length > 0){
						alert("主题不能是空的");
						$("#zhu").focus();
						return false;
					}
					if(!$.trim($("#fuji").val()).length > 0){
						alert("附件不能是空的");
						$("#fuji").focus();
						return false;
					}
					if(!$.trim($("#conext").val()).length > 0){
						alert("正文不能是空的");
						$("#conext").focus();
						return false;
					}
		  		</s:if>
		  		<s:if test="question.qtype==10">
					var optionsanswers = document.getElementsByName("question.answers");
					var _opa = 0;
					for(var _i = 0 ; _i< optionsanswers.length;_i++){
						if($.trim(optionsanswers[_i].value) == "")
							_opa++;
					}
					if(_opa!=0){
						alert("答案不能是空的");
						optionsanswers[0].focus();
						return false;
					
					}
		  		</s:if>
		  		<s:if test="question.qtype==11">
					if(!$.trim($("#qsubject").val()).length > 0){
						alert("规则文档不能是空的");
						$("#qsubject").focus();
						return false;
					}
		  		</s:if>
		  		<s:if test="question.qtype==19">
		            if(document.getElementsByName("question.options").length < document.getElementsByName("question.fenContents").length-1){
		            	alert("答案不能少于分题干！");
		            	return false;
						}
		  		</s:if>
		  		<s:if test="question.qtype==19 || question.qtype==20">
		            var v = $("#standardAnswer").val();
		            if(v.indexOf("-=SpEl=-")<0){
		            	alert("完整选项格式错误!");
		            	return false;
		            }
		  		</s:if>
		  			return true;
				}
			function loadEditor(){
				<s:if test="question.qtype == 15 || question.qtype==16||question.qtype==17||question.qtype==18||question.qtype==19||question.qtype==20">
					var oFCKeditor = new FCKeditor('question.stemText') ;
					oFCKeditor.BasePath = "editor/" ;
					oFCKeditor.Height = 200;
					oFCKeditor.Width = 700;
					oFCKeditor.ToolbarSet = "qcontent" ;
					oFCKeditor.ReplaceTextarea();
				</s:if>
			}
			function myload(){
		  	 	<s:if test="question.qtype!=8&&question.qtype!=9">
					var oFCKeditor = new FCKeditor('content') ;
					oFCKeditor.BasePath = "editor/" ;
					oFCKeditor.Height = 200;
					oFCKeditor.Width = 700;
					oFCKeditor.ToolbarSet = "qcontent" ;
					oFCKeditor.ReplaceTextarea();
				</s:if>
				var qlibs = document.getElementsByName("question.qlib.id");
				for(var i  = 0 ; i <qlibs.length;i++){
					//alert(qlibs[i].value);
					if(qlibs[i].value==${question.qlib.id}){
						qlibs[i].checked = "checked";
						break;
					}
				}
				<s:if test="question.qtype==19">
					var fwsize = "<s:property value="question.fwsize"/>";
					if(fwsize==3){
						document.getElementById("blank_before").checked =true;
						document.getElementById("blank_after").checked =true;
					}
					if(fwsize==1){
						document.getElementById("blank_before").checked =true;
					}
					if(fwsize==2){
						document.getElementById("blank_after").checked =true;
					}
				</s:if>
			}
			function copyQuestion(){
				form_question_create.action = "question_add.action";
				form_question_create.submit();
			}
		</script>
	</head>
	<body onload="myload()">
		<div style="padding-top: 5px;">
			<ul class="nav">
				<li>
					<div style="padding-top: 0px; color: #077ac7; font-size: 12px;">
						<wysLib:Navigation ivalue="试题修改" />
					</div>
				</li>
				<!--<li>
				<span style="font-weight: bold;">编辑试题</span>
			</li>-->
			</ul>
		</div>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<s:form id="form_question_create" name="form_question_create"
				method="post" action="question_alter" onsubmit="return _onsubmit();"
				theme="simple">
				<table bgcolor="#ECEDEB" width="100%">
					<tr>
						<td valign="top">
							<table width="120px" align="left" border="1" cellspacing="1"
								cellpadding="1">
								<tr>
									<td height="20">
										所属知识库
									</td>
								</tr>
								<%
									String xx = ((com.sopia.questionman.entities.QuestionLib) request
												.getAttribute("questionLib")).getParent().getId()
												+ "";
								%>
								<tr>
									<td height="20" align="left">
										<wysLib:qlibtree did="1" itype="ra_2no"
											iname="question.qlib.id" ivalue="<%=xx%>" />
									</td>
								</tr>
							</table>
						</td>
						<td>
							<table width="100%" align="center" cellpadding=1 cellspacing="1"
								border="1">
								<tr>
									<td height="30" width="100" align="center">
										<b><span class="neededitem">*</span>试题类别</b>
									</td>
									<td align=left>
										<label style="font-size: 16px; font-weight: bold;">
											<s:property value="question.qtypeName" />
										</label>
									</td>
								</tr>
								<%--  
					<tr>
						<td height="30" width="100" align="center" >
							<b>*所属知识点</b>
							<%							
										String xx = ((com.sopia.questionman.entities.QuestionLib) request.getAttribute("questionLib")).getParent().getId()+ "";
							%>
							
						</td>
						<td align=left >
								 <wysLib:qlibtree ivalue="<%=xx%>" did="0" iname="question.qlib.id" itype="ra"></wysLib:qlibtree> 
								 <!-- ivalue="${question.qlib.id}" -->
								 
						</td>
					</tr>
					 --%>
								<tr>
									<td height="30" width="100" align="center">
										<b><span class="neededitem">*</span>难度</b>
									</td>
									<td align=left>
										<select name="question.qlevel" id="level">
											<option value="1"
												<s:if test="question.qlevel==1">selected='selected'</s:if>>
												1
											</option>
											<option value="2"
												<s:if test="question.qlevel==2">selected='selected'</s:if>>
												2
											</option>
											<option value="3"
												<s:if test="question.qlevel==3">selected='selected'</s:if>>
												3
											</option>
											<option value="4"
												<s:if test="question.qlevel==4">selected='selected'</s:if>>
												4
											</option>
											<option value="5"
												<s:if test="question.qlevel==5">selected='selected'</s:if>>
												5
											</option>
										</select>
										级
									</td>
								</tr>
								<%-- 
					<tr>
						<td height="30" width="100" align="center" >
							<b>*试题名称</b>
						</td>
						<td align="left" >
							<s:textarea cols="50" rows="4" name="question.title" id="qtitle" readonly="true" />
						</td>
					</tr>
					 --%>
								<s:if test="question.qtype!=9&&question.qtype!=10">
									<tr>
										<td height="30" width="100" align="center">
											<b><s:if test="question.qtype==8">
													<span class="neededitem">*</span>范文</s:if> <s:else>
													<span class="neededitem">*</span>题干</s:else> </b>
										</td>
										<td align="left">
											<div>
												<s:if test="question.qtype==15||question.qtype==16||question.qtype==18||question.qtype==19||question.qtype==20">
													<span>发声提问：<input type="text" size="65"
															name="question.fashengQuestion" id="fashengQuestion"
															value="${question.fashengQuestion}" /> <a
														href="javascript:setUrl('fashengQuestion');"
														class="textbg4" style="width: 80px">浏览资源库</a>
													</span>
												</s:if>
												<br />
												<s:if test="question.qtype==16||question.qtype==17||question.qtype==19||question.qtype==20">
													<span>媒体文件：<input type="text" size="65"
															name="question.mediaFile" id="mediaFile"
															value="${question.mediaFile }" /> <a
														href="javascript:setUrl('mediaFile');" class="textbg4"
														style="width: 80px">浏览资源库</a>
													</span>
												</s:if>
												<br />
												<s:if test="question.qtype==17">
													<span>前半截媒体文件：<input type="text" size="65"
															name="question.frontHalfMediaFile" id="frontHalfMediaFile" 
															value="${question.frontHalfMediaFile }" /> <a
														href="javascript:setUrl('frontHalfMediaFile');" class="textbg4"
														style="width: 80px">浏览资源库</a>
													</span>
												</s:if>
												<br />
												<s:if test="question.qtype==8">
													<s:textarea name="question.content" id="content"
														cssStyle="width:600px;height:300px; " />
												</s:if>
												<s:else>
													<s:textarea name="question.content" id="content"
														cssStyle="width:700px;height:150px;visibility:hidden;" />
												</s:else>
											</div>
										</td>
									</tr>
								</s:if>
								
								<s:if test="question.qtype==15 || question.qtype==16|| question.qtype==17|| question.qtype==18|| question.qtype==19|| question.qtype==20">
									<tr>
										<td height="30" width="100" align="center">
											<b>题干文本</b>
										</td>
										<td align="left">
											<s:textarea name="question.stemText" id="question.stemText"
														cssStyle="width:700px;height:200px; " onclick="loadEditor();" />
										</td>
									</tr>
								</s:if>
								
								<s:if test="question.qtype==1">
									<tr>
										<td height="30" width="100" align="center">
											<b>试题答案</b>
										</td>
										<td align="left">

											<input id="correct_yes"
												<s:if test="question.answers[0]=='yes'">checked='checked'</s:if>
												name="question.answers" type="radio" checked value="yes" />
											&nbsp;&nbsp;正确 &nbsp;&nbsp;&nbsp;&nbsp;
											<input id="correct_no"
												<s:if test="question.answers[0]=='no'">checked='checked'</s:if>
												name="question.answers" type="radio" value="no" />
											&nbsp;&nbsp;错误

										</td>
									</tr>
								</s:if>

								<s:if test="question.qtype==6||question.qtype==7">
									<tr>
										<td height="30" width="100" align="center">
											<b>试题答案</b>
										</td>
										<td align="left">
											<div>
												<s:textarea name="question.answer" id="answer" rows="6"
													cols="80"></s:textarea>
											</div>
										</td>
									</tr>
								</s:if>

								<s:if
									test="question.qtype==2||question.qtype==3||question.qtype==4||question.qtype==15||question.qtype==16||question.qtype==115">
									<tr>
										<td height="30" width="100" align="center">
											<b><span class="neededitem">*</span>选项</b>
										</td>
										<td align=left>
											<div id="option_area">
												<s:iterator value="question.options" status="qa">
													<div id="option_<s:property value="#qa.index"/>">
														选项
														<b id="opt_ABC_<s:property value="#qa.index" />"><script
																type="text/javascript">intoABC('<s:property value="#qa.index" />',"opt_ABC_<s:property value="#qa.index" />")</script>：</b>
														&nbsp;&nbsp;
														<!-- <input name='question.options'
																	value="<s:property />"
																	id='__option<s:property value="#qa.index" />'
																	size="65" /> <a
																href="javascript:setUrl('__option<s:property value="#qa.index" />');"
																class="textbg4" style="width: 80px">浏览资源库</a> </span> -->  
														<textarea name='question.options'
															style='border: 1px solid buttonface; overflow: hidden; width: 500px; height: 120px'
															onfocus='createeditor(this,<s:property value="#qa.index" />)'
															id='__option<s:property value="#qa.index" />'>
															<s:property />
														</textarea>
														&nbsp;&nbsp;设为正确答案
														<input name='question.answers'
															value="<s:property value="#qa.index"/>"
															type="<s:if test="question.qtype==2">radio</s:if><s:else>checkbox</s:else>" />
														<br />
														<s:iterator value="question.voicePaths[#qa.index]">
															<span>声音文件：<input name='question.voicePaths'
																	value="<s:property />"
																	id='voicePath<s:property value="#qa.index" />'
																	size="65" /> <a
																href="javascript:setUrl('voicePath<s:property value="#qa.index" />');"
																class="textbg4" style="width: 80px">浏览资源库</a> </span>
														</s:iterator>
													</div>
													<script type="text/javascript">nowCount =<s:property value="#qa.index+1"/>; </script>
												</s:iterator>
												<s:iterator value="question.answers" status="qaaaaa">
													<script type="text/javascript">
														setAnswer(<s:property />);
													</script>
												</s:iterator>
											</div>
											<div>
												<input type="button" onclick="addOption();" value="添加选项" />
												&nbsp;&nbsp;
												<input type="button" value="删除选项" onclick="removeOption();" />
											</div>
											<div>
											</div>
										</td>
									</tr>
								</s:if>
								<s:if
									test="question.qtype==18">
									<tr>
										<td height="30" width="100" align="center">
											<b><span class="neededitem">*</span>选项</b>
										</td>
										<td align=left>
											<div id="option_area">
												<s:iterator value="question.options" status="qa">
													<div id="option_<s:property value="#qa.index"/>">
														选项
														<b id="opt_ABC_<s:property value="#qa.index" />"><script
																type="text/javascript">intoABC('<s:property value="#qa.index" />',"opt_ABC_<s:property value="#qa.index" />")</script>：</b>
														&nbsp;&nbsp;
														<input name='question.options'
																	value="<s:property />"
																	id='__option<s:property value="#qa.index" />'
																	size="65" /> <a
																href="javascript:setUrl('__option<s:property value="#qa.index" />');"
																class="textbg4" style="width: 80px">浏览资源库</a> 
													<!--  	<textarea name='question.options'
															style='border: 1px solid buttonface; overflow: hidden; width: 500px; height: 120px'
															onfocus='createeditor(this,<s:property value="#qa.index" />)'
															id='__option<s:property value="#qa.index" />'>
															<s:property />
														</textarea>-->
														&nbsp;&nbsp;设为正确答案
														<input name='question.answers'
															value="<s:property value="#qa.index"/>"
															type="<s:if test="question.qtype==2">radio</s:if><s:else>checkbox</s:else>" />
														<br />
														<s:iterator value="question.voicePaths[#qa.index]">
															<span>声音文件：<input name='question.voicePaths'
																	value="<s:property />"
																	id='voicePath<s:property value="#qa.index" />'
																	size="65" /> <a
																href="javascript:setUrl('voicePath<s:property value="#qa.index" />');"
																class="textbg4" style="width: 80px">浏览资源库</a> </span>
														</s:iterator>
													</div>
													<script type="text/javascript">nowCount =<s:property value="#qa.index+1"/>; </script>
												</s:iterator>
												<s:iterator value="question.answers" status="qaaaaa">
													<script type="text/javascript">
														setAnswer(<s:property />);
													</script>
												</s:iterator>
											</div>
											<div>
												<input type="button" onclick="addOption( );" value="添加选项" />
												&nbsp;&nbsp;
												<input type="button" value="删除选项" onclick="removeOption();" />
											</div>
											<div>
											</div>
										</td>
									</tr>
								</s:if>
								<s:if
									test="question.qtype==5||question.qtype==20">
									<tr>
										<td height="30" width="100" align="center">
											<b><span class="neededitem">*</span>设置空白答案</b>
										</td>
										<td align="left">
											<div id="BlankAnswer_area">
												<s:iterator value="question.answers" status="qa">
													<div id="BlankAnswer_<s:property value="#qa.index"/>">
														空白答案
														<s:property value="#qa.index+1" />
														&nbsp;&nbsp;
														<input type='text' value="<s:property />"
															name='question.answers' />
													</div>
													<script type="text/javascript">++nowCount</script>
												</s:iterator>
											</div>
											<div>
												<input type="button" onclick="addBlankAnswer();" value="添加" />
												&nbsp;&nbsp;
												<input type="button" value="删除"
													onclick="removeBlankAnswer();" />
											</div>
										</td>
									</tr>
								</s:if>
								<s:if test="question.qtype==19">
									<tr>
										<td height="30" width="100" align="center">
											<b><span class="neededitem">*</span>设置备选答案</b>
										</td>
										<td align="left">
											<div id="BlankAnswer_area">
												<s:iterator value="question.options" status="qa">
													<div id="BlankAnswer_<s:property value="#qa.index"/>">
														空白答案
														<s:property value="#qa.index+1" />
														&nbsp;&nbsp;
														<input type='text' value="<s:property />"
															name='question.options' />
													</div>
													<script type="text/javascript">++nowCount;	
															
													</script>
												</s:iterator>
												<s:iterator value="question.answers" status="qaaaaa">
													<script type="text/javascript">
														setAnswer("<s:property escape="false"/>");
													</script>
												</s:iterator>
											</div>
											<div>
												<input type="button" onclick="addBlankAnswer();" value="添加" />
												&nbsp;&nbsp;
												<input type="button" value="删除"
													onclick="removeBlankAnswer();" />
											</div>
										</td>
									</tr>
									<tr>
										<td height="30" width="150" align="center">
											<b><span class="neededitem">*</span>分题干</b>
										</td>
										<td align=left>
											<div id="tigan_area">
												<s:iterator value="question.fenContents" status="qf">
													<div id="tigan_area_<s:property value="#qf.index"/>">
														分题干
														<s:property value="#qf.index+1" />
														<textarea name='question.fenContents'
															style='border: 1px solid buttonface; overflow: hidden; width: 500px; height: 120px'
															onfocus='createeditor(this,<s:property value="#qf.index" />)'
															id='__tigan<s:property value="#qf.index" />'>
															<s:property />
														</textarea>
													</div>
													<script type="text/javascript">++nowCount1</script>
												</s:iterator>
											</div>
											<div>
												<input type="button" onclick="addTigan();" value="添加" />
												&nbsp;&nbsp;
												<input type="button" value="删除" onclick="removeTigan();" />
												<br/>
												<input type="checkbox" id="blank_before" value="1"/>开头空
												<input type="checkbox" id="blank_after" value="1"/>结尾空
												<input type="hidden" name="question.fwsize" id="fwsize" value="0"/>
											</div>
										</td>
									</tr>
								</s:if>
								<s:if test="question.qtype==17||question.qtype==15||question.qtype==16">
									<tr>
										<td height="30" width="150" align="center">
											<b>试题答案</b>
										</td>
										<td align="left">
											<div>
												<span>样&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;音： <input
														type="text" size="65" name="question.modelVoice"
														id="modelVoice" value="${question.modelVoice}" /> <a
													href="javascript:setUrl('modelVoice');" class="textbg4"
													style="width: 80px">浏览资源库</a> </span>
												<br />
												样音文字：
												<s:textarea name="question.modelVoiceText"
													id="modelVoiceText" rows="4" cols="80"></s:textarea>
											</div>
										</td>
									</tr>
								</s:if>
								<s:if test="question.qtype==6">
									<input type="hidden" name="question.minWord" />
									<input type="hidden" name="question.answer" />

									<!--<tr>
							<td height="30" width="100" align="center" >
								<b>答案关键词</b>
							</td>
							<td align="left" >
								<div id="BlankAnswer_area">
									<s:iterator value="question.answers" status="qa">
										<div id="BlankAnswer_<s:property value="#qa.index"/>">
											答案关键词
											<s:property value="#qa.index+1" />
											&nbsp;&nbsp;
											<input type='text' value="<s:property />"
												name='question.answers' />
										</div>
										<script type="text/javascript">++nowCount</script>
									</s:iterator>
								</div>
								<div>
									<input type="button" onclick="addBlankAnswer();" value="添加" />
									&nbsp;&nbsp;
									<input type="button" value="删除" onclick="removeBlankAnswer();" />
								</div>

							</td>
						</tr>
						<tr>
							<td height="30" width="100" align="center" >
								<b>答案字数限制</b>
							</td>
							<td align="left" >
								<s:textfield name="question.minWord" />
							</td>
						</tr>
					-->
								</s:if>
								<s:if test="question.qtype==7">
									<input type="hidden" name="question.answer" value="" />
								</s:if>
								<s:if test="question.qtype==8">
									<!--<tr>
						<td height="30" width="100" align="center" >
							<b>*范文</b>
						</td>
						<td align="left" >
							<div>
								<s:textarea name="question.subject" id="qsubject"
									cssStyle="width:700px;height:200px;visibility:hidden;" />
							<s:textarea name="question.answer" cssStyle="width: 0px;height: 0px;visibility:hidden;" />
								</div>
						</td>
					</tr>
					<tr>
						<td height="30" width="100" align="center" >
							<b>*不带格式范文文字</b>
						</td>
						<td align="left" >
							<div>
								<s:textarea name="question.answer" cssStyle="width:600px;height:200px;" />
								<br/>
								说明：该想是上面的不带格式的文字，是用来与学员答案对比的文字。
							</div>
						</td>
					</tr>
					-->
								</s:if>
								<s:if test="question.qtype==9">
									<tr>
										<td height="30" width="100" align="center">
											<b><span class="neededitem">*</span>内容</b>
										</td>
										<td align=left>
											<script type="text/javascript">
									function setScore(){
												document.getElementById("oldscore").value=
												parseInt(document.getElementById("oldscore0").value)+
												parseInt(document.getElementById("oldscore1").value)+
												parseInt(document.getElementById("oldscore2").value)+
												parseInt(document.getElementById("oldscore3").value)+
												parseInt(document.getElementById("oldscore4").value)+
												parseInt(document.getElementById("oldscore5").value);
											}
							</script>
											<div id="option_area">
												发 给：
												<input type="text" size="65" name="question.answers"
													id="send" value="<s:property value="question.answers[0]"/>" />
												<br />
												<!--分值:
											<input type="text"
												value="<s:property value="question.oldrules[0]"/>" size="4"
												name="question.oldrules" />
											<br /> -->
												抄 送：
												<input type="text" size="65" name="question.answers" id="cc"
													value="<s:property value="question.answers[1]"/>" />
												<br />
												<!--分值:
											<input type="text"
												value="<s:property value="question.oldrules[1]"/>" size="4"
												name="question.oldrules" />
											<br /> -->
												密 送：
												<input type="text" size="65" name="question.answers"
													id="min" value="<s:property value="question.answers[2]"/>" />
												<br />
												<!--分值:
											<input type="text"
												value="<s:property value="question.oldrules[2]"/>" size="4"
												name="question.oldrules" />
											<br /> -->
												附 件：
												<input type="text" size="65" name="question.answers"
													id="fuji" value="<s:property value="question.answers[3]"/>" />
												<br />
												<!--分值:
											<input type="text"
												value="<s:property value="question.oldrules[4]"/>" size="4"
												name="question.oldrules" />
											<br />-->
												主 题：
												<input type="text" size="65" name="question.answers"
													id="zhu" value="<s:property value="question.answers[4]"/>" />
												<br />
												<!--分值:
											<input type="text"
												value="<s:property value="question.oldrules[3]"/>" size="4"
												name="question.oldrules" />
											<br />-->
												正 文：
												<textarea rows="4" cols="50" name="question.answers"
													id="conext">
													<s:property value="question.answers[5]" />
												</textarea>
												<!--分值:
											<input type="text"
												value="<s:property value="question.oldrules[5]"/>" size="4"
												name="question.oldrules" />
											<br />-->
											</div>
										</td>
									</tr>
								</s:if>
								<s:if test="question.qtype==10">
									<tr>
										<td height="30" width="100" align="center">
											<b><span class="neededitem">*</span>标准答案</b>
										</td>
										<td align="left">
											<script type="text/javascript">
											function qsearch_list(){
												document.getElementById("xx").style.display="block";
												$.post("questionartlist.action", {
												"pS":10,
												"x":Math.random
												}, 
												function (data) {
													$("#xx_content" ).html(data);
												});
											}
											var _title = "";
											function qsearch_list1(){
												_title = $("#list_title").val() ;
												document.getElementById("xx").style.display="block";
												$.post("questionartlist.action", {
												"pS":10,
												"questionart.title":_title,
												"x":Math.random
												}, 
												function (data) {
													$("#xx_content" ).html(data);
												});
											}
											function page(i){
												$.post("questionartlist.action", {
												"pS":10,
												"pN":i,
												"questionart.title":_title,
												"x":Math.random
												}, 
												function (data) {
													$("#xx_content" ).html(data);
												});
																					}
											function qsearch_addinit(){
												document.getElementById("xx").style.display="block";
												$.post("questionartaddinit.action", {
												"x":Math.random
												}, 
												function (data) {
													$("#xx_content" ).html(data);
												});
											}
											function qsearch_add (){
												document.getElementById("xx").style.display="block";
												$.post("questionartadd.action", {
												"questionart.title":$("#add_title").val(),
												"questionart.content":$("#add_content").val(),
												"x":Math.random
												}, 
												function (data) {
													$("#xx_content" ).html(data);
												});
											}
											function qsearch_alter (){
												document.getElementById("xx").style.display="block";
												$.post("questionartalter.action", {
												"questionart.title":$("#add_title").val(),
												"questionart.content":$("#add_content").val(),
												"questionart.id":$("#add_id").val(),
												"x":Math.random
												}, 
												function (data) {
													$("#xx_content" ).html(data);
												});
											}
											function qsearch_alterinit(id){
												document.getElementById("xx").style.display="block";
												$.post("questionartalterinit.action", {
												"questionart.id":id,
												"x":Math.random
												}, 
												function (data) {
													$("#xx_content" ).html(data);
												});
											}
											function qsearch_delete(id){
												if(window.confirm("确定删除该文章？"))
												{
												document.getElementById("xx").style.display="block";
												$.post("questionartdelete.action", {
												"questionart.id":id,
												"x":Math.random
												}, 
												function (data) {
													$("#xx_content" ).html(data);
												});
												}
											}
											function qsearch_list_close(){
												document.getElementById("xx").style.display="none";
											}
											function qanswer_search(){
												$.post("qanswer_search.action", {
												"pS":10,
												"questionart.title":$("#qanswer_title").val(),
												"x":Math.random
												}, 
												function (data) {
													$("#answer_list").html(data);
												});
											}
											function page1(i){
												$.post("qanswer_search.action", {
												"pS":10,
												"pN":i,
												"questionart.title":$("#qanswer_title").val(),
												"x":Math.random
												}, 
												function (data) {
													$("#answer_list").html(data);
												});
											}
											function qanswer_setans(obj){
												document.getElementById("qanswer_ans").value=obj.innerHTML ;
												$("#answer_list").css("display","none");
											}
											function setScore(){
												document.getElementById("oldscore").value=
												parseInt(document.getElementById("oldscore0").value)+
												parseInt(document.getElementById("oldscore1").value) ;
											}
										</script>
											<div id="xx"
												style="margin-top: -300px; display: none; width: 500px; height: 400px; background: #ffffff; position: absolute; z-index: 9999; border: 2px solid buttonface;">
												<div
													style="width: 500px; border-bottom: solid 2px buttonface; background: buttonface; text-align: right;">
													<a style="width: 25px; text-decoration: none;" href=""
														onclick="qsearch_list_close();return false;">X</a>
												</div>
												<div id="xx_content"></div>
											</div>
											<!--
								分值:<input type="text"
										value="<s:property value="question.oldrules[0]"/>" size="4"
										name="question.oldrules" id="oldscore0" onblur="setScore()"/>-->
											<!-- 
								 搜索标题：
								<input type="text" size="35" id="qanswer_title"
									value="<s:property value="question.answers[0]"/>" />
								<input type="button" onclick="qanswer_search();" value="搜索文章列表" />
								<a onclick="qsearch_list();return false;" href="#">管理列表</a>
								 -->
											<!--<br />
								分值:<input type="text"
										value="<s:property value="question.oldrules[1]"/>" size="4"
										name="question.oldrules" id="oldscore1" onblur="setScore()"/>正确答案：-->
											<input type="text"
												value="<s:property value="question.answers[0]"/>"
												id="qanswer_ans" size="45" name="question.answers" />
											(考生选择的答案)
											<!-- <br />
											点击表题选择答案： -->
											<div id="answer_list" style="padding: 0px;"></div>
										</td>
									</tr>
									<tr>
										<td height="30" width="150" align="center">
											<b> 文章内容 </b>
										</td>
										<td align="left">
											<div>
												<s:textarea name="question.qexplain" id="content"
													cssStyle="width:700px;height:150px;visibility:hidden;" />
											</div>
										</td>
									</tr>
								</s:if>
								<s:if test="question.qtype==11">
									<tr>
										<td height="30" width="100" align="center">
											<b><span class="neededitem">*</span>规则文档</b>
										</td>
										<td align="left">
											<input type="text" size="65" name="question.subject"
												id="qsubject" value="<s:property value="question.subject"/>" />
											<input type="hidden" name="question.answer" value="" />
											<a href="javascript:setUrl('qsubject');" class="textbg4"
												style="width: 80px">浏览资源库</a>
										</td>
									</tr>
								</s:if>
								<s:if test="question.qtype==19||question.qtype==20">
									<tr>
										<td height="30" width="100" align="center">
											<b><span class="neededitem">*</span>完整选项(中间以'-=SpEl=-'分隔，末尾以'-=SpEl=-'结束)</b>
										</td>
										<td align="left">
											<s:textarea name="question.standardAnswer" id="standardAnswer"
														cssStyle="width:600px;height:100px; " />
										</td>
									</tr>
								</s:if>
								<s:if
									test="question.qtype!=8&&question.qtype!=9&&question.qtype!=10">
									<tr>
										<td height="30" width="100" align="center">
											<b>答案解释</b>
										</td>
										<td align="left">
											<s:textarea name="question.qexplain" id="qexplain"
												cssStyle="width:600px;height:100px;" />
											<s:if test="question.qtype==19||question.qtype==20">
												<br />

												<span>样&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;音： <input
														type="text" size="65" name="question.modelVoice"
														id="modelVoice" value="${question.modelVoice}" /> <a
													href="javascript:setUrl('modelVoice');" class="textbg4"
													style="width: 80px">浏览资源库</a> </span>

												<br />
												<div id="answer_text">
											<!--  		<s:iterator value="question.modelVoiceTexts" status="qm">
														<div id="Answer_<s:property value="#qm.index"/>">
															答案文本
															<s:property value="#qm.index+1" />
															&nbsp;&nbsp;
															<input type='text' value="<s:property />"
																name='question.modelVoiceTexts' />
														</div>
														<script type="text/javascript">++nowCount2</script>
													</s:iterator>
													-->
													答案文本：<input type='text' value="${question.modelVoiceText}"
																id="modelVoiceText" name='question.modelVoiceText'  size="100"/>
												</div>
												<!--  <div>
												<input type="button" onclick="addAnswer();" value="添加" />
												&nbsp;&nbsp;
												<input type="button" value="删除"
													onclick="removeAnswer();" />
											</div>
											-->
											</s:if>
										</td>
									</tr>
								</s:if>
								<tr>
									<td height="30" width="100" align="center">
										<b>参考分值</b>
									</td>
									<td align="left">
										<s:textfield id="oldscore" name="question.oldscore" size="4" />

									</td>
								</tr>
								<tr>
									<td height="30" align="center" bgcolor="#ECEDEB" colspan=2>
										<s:hidden name="question.id"></s:hidden>
										<s:hidden name="question.qtype"></s:hidden>
										<s:hidden name="question.parent.id"></s:hidden>
										<!--
							<s:hidden name="question.scorepre"></s:hidden>
							-->
										<s:if test="copy==1">
											<input type="button" class="textbg" style="border: none;"
												onclick="copyQuestion()" name="button" id="button"
												value="提交" />
										</s:if>
										<s:else>
										<!-- 	<input type="submit" class="textbg" style="border: none"
												name="button" id="button" value="提交更改" /> -->
											<s:if test="question.qtype==15||question.qtype==16||question.qtype==17||question.qtype==18||question.qtype==19||question.qtype==20">
											<input type="submit" class="textbg" style="border: none"
												name="button" id="button" value="提交更改"
												onclick=" return checkFile() " />
										</s:if>
										<s:else>
											<input type="submit" class="textbg" style="border: none"
												name="button" id="button" value="提交更改" />
										</s:else>
										</s:else>
										<input type="button" class="textbg" style="border: none"
											onclick="document.location='question_list.action'"
											value="试题列表" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</s:form>
		</div>
		<!-- 内容 -->
			<script type="text/javascript">
				function checkFile(){
				<s:if test="question.qtype==15||question.qtype==16||question.qtype==17||question.qtype==18">
					<s:if test="question.qtype==16||question.qtype==17||question.qtype==18">
					if(document.getElementById("fashengQuestion").value==""){
						alert("发声提问不能为空!");
						return false;
					}
					</s:if>
					if (document.getElementById("fashengQuestion").value.replace(/\s/g, "") != "") //这里输入框不为空
	            {
	                var FileType = "wma";    //这里是允许的后缀名，注意要小写
	                var FileName = document.getElementById("fashengQuestion").value;
	               FileName = FileName.substring(FileName.lastIndexOf('.')+1, FileName.length).toLowerCase(); //这里把后缀名转为小写了，不然一个后缀名会有很多种大小写组合，前面允许的文件后缀要写死人了。
	                   if(FileName==""){
	                   	alert("请检查文件后缀名是否正确!!");
	                   	return false;
	                   }else{
	                    FileName = FileName.substring(FileName.lastIndexOf('.')+1, FileName.length).toLowerCase();
	                    if(!(FileName=="wma"  || FileName == "mp3")){
	                 		 alert("请选择正确的音频格式！");
	                          return false;
	                 }
	           }
	           }
	           </s:if>
	           <s:if test="question.qtype==16">
	           
	           if(document.getElementById("mediaFile").value==""){
						alert("媒体文件不能为空!");
						return false;
					}
					if (document.getElementById("mediaFile").value.replace(/\s/g, "") != "") //这里输入框不为空
	            {
	              //  var FileType = "wma";    //这里是允许的后缀名，注意要小写
	                var FileName = document.getElementById("mediaFile").value;
	                
	               FileName = FileName.substring(FileName.lastIndexOf('.')+1, FileName.length).toLowerCase(); //这里把后缀名转为小写了，不然一个后缀名会有很多种大小写组合，前面允许的文件后缀要写死人了。
	                   if(FileName==""){
	                   	alert("请检查文件后缀名是否正确!!");
	                   	return false;
	                   }else{
	                    FileName = FileName.substring(FileName.lastIndexOf('.')+1, FileName.length).toLowerCase();
	                    if(!(FileName=="mp4"||FileName=="wmv"||FileName=="flv"||FileName=="swf"||FileName=="avi")){
	                 		 alert("请选择正确的媒体格式！");
	                          return false;
	                 }
	           }
	           }
	           </s:if>
	           <s:if test="question.qtype==15||question.qtype==16||question.qtype==17||question.qtype==18||question.qtype==19||question.qtype==20">
	          /*   if(document.getElementById("modelVoice").value==""){
						alert("样音文件不能为空!");
						return false;
					}
				 if(document.getElementById("modelVoiceText").value==""){
						alert("样音文字不能为空!");
						return false;
					} */
					if (document.getElementById("modelVoice").value.replace(/\s/g, "") != "") //这里输入框不为空
	           {
	                var FileType = "wma"; //这里是允许的后缀名，注意要小写
	                var FileName = document.getElementById("modelVoice").value;
	               FileName = FileName.substring(FileName.lastIndexOf('.')+1, FileName.length).toLowerCase(); //这里把后缀名转为小写了，不然一个后缀名会有很多种大小写组合，前面允许的文件后缀要写死人了。
	                   if(FileName==""){
	                   	alert("请检查文件后缀名是否正确!!");
	                   	return false;
	                   }else{
	                    FileName = FileName.substring(FileName.lastIndexOf('.')+1, FileName.length).toLowerCase();
	                     if(!(FileName=="wma"  || FileName == "mp3")){
	                 		 alert("请选择正确的音频格式！");
	                          return false;
	                 }
	           }
	          }
	           </s:if>
	         
				}
		 </script>
	</body>
</html>
