<%@ page language="java" pageEncoding="UTF-8"   %>
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
		<base href="<%=basePath%>"/>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="editor/fckeditor.js"></script>
		<script type="text/javascript" src="js/inputcheck.js"></script>
		<script type="text/javascript">
			function _onsubmit(){
				if(!$.trim($("#cptitle").val()).length > 0){
					alert("请填写章节名称");
					$("#cptitle").focus();
					return false;
				}
				var islink="<s:property value="course.islink" />";
				var str="";
				if(islink==0){
					/*
					if($("#during").val() == "" ){
						alert("请填写最小学习时长");
						$("#cptitle").focus();
						return false;
					}
					*/
					if (!checkNumber($("#during").val())){
						alert("最小学习时长只能为数字");
						$("#cptitle").focus();
						return false  
					}
					if($("#during").val()<0&&$("#during").val()!=''){
						alert("课程学习时间不能小于0！");
						return false;
					}
					if($("#during").val()==0){
						str="最小学习时长为0，";
					}
					if($("#during").val()==''){
						str="最小学习时长没填，";
					}
					if (!checkNumber($("#queryTime").val())){
						alert("询问学员是否学习间隔时间只能为数字");
						$("#queryTime").focus();
						return false  
					}
					if(parseInt($("#during").val()) < parseInt($("#queryTime").val()) ){
						alert("学习询问时间不能大于学习时间！");
						return false;
					}
				}
				return window.confirm(str+"确定信息填写无误？");
			}
			function myload(){
				var oFCKeditor = new FCKeditor('content') ;
				oFCKeditor.BasePath = "editor/" ;
				oFCKeditor.Height = 400;
				oFCKeditor.Width ="100%";
				oFCKeditor.ReplaceTextarea();
				document.getElementById("multi_file").style.display="block";
				document.getElementById("file_des").innerHTML="媒体文件";
				document.getElementById("page_url_a").style.display="block";
			}
		</script>
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
	</HEAD>
	<BODY
		onload="myload()">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="操作页面" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">添加章节</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="coursepage_list.action?course.id=<s:property value="course.id"/>">管理章节</a>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top:0px;">
			<form action="coursepage_add.action" method="post" onSubmit="return _onsubmit();">
			<input type="hidden" name="coursePage.skipable" id="skipable" value="1" />
				<table width="100%" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr>
						<td width="160" height="30" align="right" >
							<span class="neededitem">*</span>章节标题：
						</td>
						<td >
							<label>
								<input type="text" name="coursePage.title" id="cptitle"
									style="width: 150px;" />
							</label>
						</td>
					</tr>
					<s:if test="course.islink==0">
						<tr>
							<td width="160" height="30" align="right" >
								<span class="neededitem">*</span>最小学习时长：
							</td>
							<td >
								<label>
									<input type="text" size="4" name="coursePage.during" id="during" value=30>
									分钟
								</label>
								<label>
								&nbsp;询问学员是否学习间隔时间:	<input type="text" size="4" name="coursePage.queryTime"  id="queryTime" value=5>
									分钟(-1是不询问)
								</label>
							</td>
						</tr>
					</s:if>
					<tr>
						<td width="160" height="30" align="right" >
							<span class="neededitem">*</span>是否允许跳过
						</td>
						<td >
							<label>
								<input type="radio" name="coursePage.skipable" id="skipable" value=1
									checked>
								是&nbsp;&nbsp;&nbsp;
								<input type="radio" name="coursePage.skipable" id="skipable" value=0>
								否
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="right" >
							<span class="neededitem">*</span>章节属性：
						</td>
						<td >
							<label>
								<select name="coursePage.property" id="property"
									style="width: 150px;">
									<option value="0" id="property_0">
										章
									</option>
									<option value="1" id="property_1">
										节
									</option>
								</select>
							</label>
						</td>
					</tr>
					<s:if test="course.islink==2">
						<tr>
						<td width="160" height="30" align="right" >
							<span class="neededitem">*</span>外部课件地址：
						</td>
						<td >
							<label>
									<!--<input type="text" name="coursePage.page_url"
										style="width: 300px;" id='pageurl'/>
										&nbsp;&nbsp;<a href="coursepage_list.action?course.id=<s:property value="course.id"/>">添加章节</a>
							-->
								<input type="text" name="coursePage.page_url"
										style="width: 300px;" id='pageurl'/>
										<span id="page_url_a"><a href="javascript:setUrl('pageurl');">浏览资源库</a></span>
							</label>
						</td>
					</tr>
					</s:if>
					<s:if test="course.islink==0">
					<tr>
						<td width="160" height="30" align="right" >
							<span class="neededitem">*</span>页面类型：
						</td>
						<td >
							<script type="text/javascript">
								function changeType(){
									var type = document.getElementById("type").value;
									if(type==1||type==2){
										document.getElementById("multi_file").style.display="block";
										document.getElementById("file_des").innerHTML="媒体文件";
										document.getElementById("page_url_a").style.display="block";
									}
									if(type==0) {
										document.getElementById("multi_file").style.display="none";
										document.getElementById("file_des").innerHTML="媒体文件";
										document.getElementById("page_url_a").style.display="block";
									}
									if(type==3) {
										document.getElementById("multi_file").style.display="block";
										document.getElementById("page_url_a").style.display="block";
										document.getElementById("file_des").innerHTML="课程地址";
									}
								}
							</script>
							<label>
								<select name="coursePage.type" id="type" style="width: 150px;"
									onChange="changeType();">
									<!-- <option value="0" id="type_0">
										图文讲义
									</option>-->
									
									<option value="1" id="type_1">
										纯视频
									</option>
									<option value="2" id="type_2">
										视频＋讲义
									</option>
									<option value="3" id="type_3">
										外部课程
									</option>
									<option value="4" id="type_4">
										视频学习
									</option>
									<!-- <option value="3" id="type_3">
																				音频＋讲义
																			</option> -->
								</select>
							</label>
							<div id="multi_file" style="display: none;">
								<label>
									<span id="file_des">媒体文件：</span>
								</label>
								<label>
									<input type="text" name="coursePage.page_url"
										style="width: 300px;" id='pageurl'/> <span id="page_url_a"><a href="javascript:setUrl('pageurl');" class="textbg4" style="width:80px">浏览资源库</a></span>
								</label>
							</div>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="right" >
							<span class="neededitem">*</span>结业方式：
						</td>
						<td >
							<label> 
								<s:select theme="simple" list="#{1:'学完',2:'考过',3:'学完且考过'}" value="coursePage.getcredit" name="coursePage.getcredit" />
							</label>
						</td>
					</tr>
					<tr>
						<td height="30" colspan="2" align="center" >
							编辑章节讲义
						</td>
					</tr>
					</s:if>
			  </table>

				<s:if test="course.islink==0||course.islink==3||course.islink==1||course.islink==2">
					<div id="lecture_notes" style="text-align:center; width:100%">
					<label>
						<textarea name="coursePage.page" id="content"
							style="width: 970px; height: 340px; visibility: hidden;"></textarea>
					</label>
					<br />
				</div>
		    </s:if>
				<s:hidden name="course.id"></s:hidden>
				<s:hidden name="coursePage.course.id"></s:hidden>
				<div id="lecture_notes" style="text-align:center; width:100%">
				<input type="submit" style="border: none;color: red;" name="button" id="button" class="textbg" value="确认添加" />
				<a class="textbg" href="coursepage_list.action?course.id=${course.id }" >取 消</a>
				</div>
			</form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
