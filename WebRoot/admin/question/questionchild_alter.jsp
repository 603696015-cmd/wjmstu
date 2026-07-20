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
</style>
		<script type="text/javascript">
			<s:if test="question.qtype==2||question.qtype==3||question.qtype==4">
		  	 	var optCount=4;
				var nowCount=0;
				function addOption(){
					var _optiontype = <s:if test="question.qtype==2">"radio"</s:if><s:else>"checkbox"</s:else>
					var obj = document.createElement("div");
					obj.id="option_"+nowCount;
					/*
					if(nowCount==0){
					obj.innerHTML ="选项<b> "+String.fromCharCode(65+parseInt(nowCount))+"：</b>&nbsp;&nbsp;&nbsp;&nbsp;<input type='hidden' id='__option"+nowCount+"' name='question.options'></textarea>"+
					"<iframe src='_editor/editor.html?height=200&id=__option"+nowCount+"' frameborder='0' scrolling='no' width='500' height='120'></iframe>&nbsp;&nbsp;设为正确答案"+
					"<input name='question.answers' type='"+_optiontype+"' value='"+nowCount+"' />";
					}else{
					obj.innerHTML ="选项<b> "+String.fromCharCode(65+parseInt(nowCount))+"：</b>&nbsp;&nbsp;&nbsp;&nbsp;<input type='hidden' id='__option"+nowCount+"' name='question.options'></textarea>"+
					"<iframe src='_editor/editor.html?height=200&id=__option"+nowCount+"' frameborder='0' scrolling='no' width='500' height='120'></iframe>&nbsp;&nbsp;设为正确答案"+
					"<input name='question.answers' type='"+_optiontype+"' value='"+nowCount+"' />";
					}
					*/
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
				}
				function removeOption(){
					if(nowCount<=2) {
						alert('选项请不要小于2个');
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
			<s:if test="question.qtype==5">
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
						alert('空白处答案请不要小于1个');
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
						alert('答案关键词请不要小于1个');
					}
					else{
					--nowCount;
					var obj = document.getElementById("BlankAnswer_"+nowCount);
					document.getElementById("BlankAnswer_area").removeChild(obj);
					}
				}
			</s:if>
			function _onsubmit(){
					var reust=/^\d*$/;
					if(FCKeditorAPI.GetInstance("content").GetXHTML(true)==''){
						alert("题干不要为空");
						//document.getElementById("content").focus();
						return false;
					}
					if (!reust.exec($("#scoreper").val())){
						alert("参考分值只能为数字");
						$("#cptitle").focus();
						return false  
					}
					questionchild_alterInit
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
						alert("请选择选择答案");
						return false;
					
					}
				</s:if>
				<s:if test="question.qtype==5">
					var optionsanswers = document.getElementsByName("question.answers");
					var _opa = 0;
					for(var _i = 0 ; _i< optionsanswers.length;_i++){
						if(optionsanswers[_i].value=='')
							_opa++;
					}
					if(_opa!=0){
						alert("答案不要是空的");
						optionsanswers[0].focus();
						return false;
					
					}
		  		</s:if>
		  			return true;
				}
			function myload(){
		  	 	var oFCKeditor = new FCKeditor('content') ;
				oFCKeditor.BasePath = "editor/" ;
				oFCKeditor.Height = 200;
				oFCKeditor.Width = 700;
				oFCKeditor.ToolbarSet = "qcontent" ;
				oFCKeditor.ReplaceTextarea();
			}
		</script>
	</HEAD>
	<body onload="myload()">
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">编辑试题</span>
			</li><li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="questionchild_view.action?question.id=<s:property value="question.id"/>">查看试题</a>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="question_view.action?question.id=<s:property value="question.parent.id" />">返回材料题</a>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<s:form id="form_question_create" name="form_question_create"
				method="post" action="questionchild_alter" onsubmit="return _onsubmit();"
				theme="simple">
				<s:hidden name="isCaiLiao" value="1"/>
				<table width="900px" align="center" cellpadding="1" cellspacing="1"
					border="1">
					<tr>
						<td height="30" width="100" align="center" >
							<b>*试题类别</b>
						</td>
						<td align=left >
							<label style="font-size: 16px; font-weight: bold;">
								<s:property value="question.qtypeName" />
							</label>
						</td>
					</tr>
					<tr>
						<td height="30" width="100" align="center" >
							<b>*所属知识点</b>
						</td>
						<td align=left >
							<s:property value="question.parent.qlib.name" />
						</td>
					</tr>
					<s:if test="question.parent.id!=0">
						<tr>
							<td height="30" width="100" align="center" >
								<b>参考分值</b>
							</td>
							<td align="left" >
								<s:textfield name="question.scoreper" cssStyle="width:50px;" id="scoreper" />
							</td>
						</tr>
					</s:if>
					<%-- 
					<tr>
						<td height="30" width="100" align="center" >
							<b>*试题名称</b>
						</td>
						<td align="left" >
							<s:textarea cols="50" rows="4" name="question.title" id="qtitle" />
						</td>
					</tr>
					 --%>
					<tr>
						<td height="30" width="100" align="center" >
							<b>试题内容</b>
						</td>
						<td align="left" >
							<div>
								<s:textarea name="question.content" id="content"
									cssStyle="width:700px;height:200px;visibility:hidden;" />
							</div>
						</td>
					</tr>
					<s:if test="question.qtype==1">
						<tr>
							<td height="30" width="100" align="center" >
								<b>试题答案</b>
							</td>
							<td align="left" >
								<input id="correct_yes"
									<s:if test="question.answers[0]=='yes'">checked='checked'</s:if>
									name="question.answers" type="radio" checked value="yes" />&nbsp;&nbsp;正确
								&nbsp;&nbsp;&nbsp;&nbsp; 
								<input id="correct_no"
									<s:if test="question.answers[0]=='no'">checked='checked'</s:if>
									name="question.answers" type="radio" value="no" />&nbsp;&nbsp;错误

							</td>
						</tr>
					</s:if>
					<s:if
						test="question.qtype==2||question.qtype==3||question.qtype==4">
						<tr>
							<td height="30" width="100" align="center" >
								<b>选项</b>
							</td>
							<td align=left >
								<div id="option_area">
									<s:iterator value="question.options" status="qa">
										<div id="option_<s:property value="#qa.index"/>">
											选项
											<b id="opt_ABC_<s:property value="#qa.index" />"><script
													type="text/javascript">intoABC('<s:property value="#qa.index" />',"opt_ABC_<s:property value="#qa.index" />")</script>：</b>
											&nbsp;&nbsp;
											<%-- 
											<input type='hidden' value="<s:property />"
												name='question.options'
												id='__option<s:property value="#qa.index" />' />
											<iframe
												src='_editor/editor.html?height=200&id=__option<s:property value="#qa.index" />'
												frameborder='0' scrolling='no' width='500' height='120'></iframe>
											&nbsp;&nbsp;设为正确答案
											 --%>
											 <textarea name='question.options' style='border:1px solid buttonface;overflow:hidden; width:500px;height:120px' onfocus='createeditor(this,<s:property value="#qa.index" />)'
												id='__option<s:property value="#qa.index" />'><s:property /></textarea>
											&nbsp;&nbsp;设为正确答案
											<input name='question.answers'
												value="<s:property value="#qa.index"/>"
												type="<s:if test="question.qtype==2">radio</s:if><s:else>checkbox</s:else>" />
										</div>
										<script type="text/javascript">nowCount =<s:property value="#qa.index+1"/>; </script>
									</s:iterator>
									<s:iterator value="question.answers">
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
							</td>
						</tr>
					</s:if>
					<s:if test="question.qtype==5">
						<tr>
							<td height="30" width="100" align="center" >
								<b>设置空白答案</b>
							</td>
							<td align="left" >
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
									<input type="button" value="删除" onclick="removeBlankAnswer();" />
								</div>
							</td>
						</tr>
					</s:if>
					<%-- 
					<s:if test="question.qtype==6">
						<tr>
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
					</s:if>
					 --%>
					<tr>
						<td height="30" width="100" align="center" >
							<b>答案解释</b>
						</td>
						<td align="left" >
							<s:textarea name="question.qexplain"
								cssStyle="width:600px;height:100px;" />

						</td>
					</tr>
					<tr>
						<td height="30" align="center" bgcolor="#ECEDEB" colspan=2>
							<s:hidden name="question.id"></s:hidden>
							<s:hidden name="question.parent.id"></s:hidden>
							<s:hidden name="question.qtype" />
							<s:hidden name="question.parent.qlib.id" />
							<s:hidden name="question.sortid" />
							<input type="hidden"
								value="<s:property value="question.parent.qlib.id" />"
								name="question.qlib.id" />
							<input type="submit" name="button" id="button" value="提交更改" />
						</td>
					</tr>
				</table>
			</s:form>
		</div>
		<!-- 内容 -->
	</body>
</html>
