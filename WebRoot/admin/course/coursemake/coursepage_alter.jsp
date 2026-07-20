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
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="editor/fckeditor.js"></script>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript">
			function _onsubmit(){
				var reust=/^\d*$/;
				if($.trim($("#textfield").val())==''){
					alert("请填写章节名称");
					$("#textfield").focus();
					return false;
				}
				var islink="<s:property value="course.islink" />";
				var str="";
				if(islink==0){//0是标准课程
					/*
					if($("#during").val() == "" ){
						alert("请填写最小学习时长");
						$("#cptitle").focus();
						return false;
					}
					*/
					if (!reust.exec($("#during").val())){
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
					if (!reust.exec($("#queryTime").val())){
						alert("询问学员是否学习间隔时间只能为数字");
						$("#queryTime").focus();
						return false  
					}
					if(parseInt($("#during").val()) < parseInt($("#queryTime").val()) ){
						alert("学习询问时间不能大于学习时间！");
						return false;
					}
				}
				
				//判断图片格式是否为JPG、PNG、GIF、BMP
				var pic_g = $("#pic_g").val();
				var pic_l = $("#pic_l").val();
				var pic_h = $("#pic_h").val();
				if(!checkPic(pic_g) || !checkPic(pic_l) || !checkPic(pic_h) ){
					alert("图片类型必须是.gif,jpg,png,bmp中的一种");
					return false;
				}
				
				return window.confirm(str+"确定信息填写无误？");
			}
			function checkPic(value){
				var flag = true;
				if(value == ""){
					flag = true;
				}else{
					if (!/\.(gif|jpg|png|bmp|GIF|JPG|PNG|BMP)$/.test(value)) {  
			            flag = false;  
			        } 
				}
				return flag;
			}
			function myload(){
				var oFCKeditor = new FCKeditor('content') ;
				oFCKeditor.BasePath = "editor/" ;
				oFCKeditor.Height = 400;
				oFCKeditor.Width ="100%";
				oFCKeditor.ReplaceTextarea();
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="修改章节" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">修改章节</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="coursepage_list.action?course.id=<s:property value="coursePage.course.id"/>">管理章节</a>
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
			<!--<div style="font-size: 15px;text-align:center; font-weight: bolder">
				<span style="color: blue"><s:if test="course.status!=0">不能再编辑章节学习信息(时间、章节类型等)</s:if>
				</span>
			</div>
			--><s:form action="coursepage_alter" method="post" theme="simple"
				onsubmit="return _onsubmit();">
				<table width="100%" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr>
						<td width="160" height="30" align="right" >
							<span class="neededitem">*</span>页面标题：
						</td>
						<td >
							<label>
								<s:textfield name="coursePage.title" id="textfield"
									cssStyle="width: 150px;" />
							</label>
						</td>
					</tr>
					<s:if test="course.islink==0">
						<tr>
							<td height="30" align="right" >
								<span class="neededitem">*</span>最小学习时长：
							</td>
							<td >
								<label>
									<input type="text" size="4"  
										name="coursePage.during"
										value="<s:property value="coursePage.during"/>" id="during" />
									分钟
								</label>
								<label>
									&nbsp;询问学员是否学习间隔时间:
									<s:textfield size="4" name="coursePage.queryTime" id="queryTime" />
									分钟(-1是不询问)
								</label>
							</td>
						</tr>
					</s:if>
					<tr>
						<td height="30" align="right" >
							<span class="neededitem">*</span>是否允许跳过
						</td>
						<td >
							<label>
								<input type="radio" name="coursePage.skipable" id="skipable"
									value=1 <s:if test="coursePage.skipable==1"> checked</s:if>>
								是&nbsp;&nbsp;&nbsp;
								<input type="radio"
									<s:if test="coursePage.skipable==0"> checked</s:if>
									name="coursePage.skipable" id="skipable" value=0>
								否
							</label>
						</td>
					</tr>
					<tr>
						<td height="30" align="right" >
							<span class="neededitem">*</span>是否为空
						</td>
						<td >
							<label>
								<input type="radio" name="coursePage.isNull" id="isNull"
									value=1 <s:if test="coursePage.isNull==1"> checked</s:if>>
								是&nbsp;&nbsp;&nbsp;
								<input type="radio"
									<s:if test="coursePage.isNull==0"> checked</s:if>
									name="coursePage.isNull" id="isNull" value=0>
								否
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="right" >
							<span class="neededitem">*</span>是否直播
						</td>
						<td >
							<label>
								<input type="radio" name="coursePage.islive" id="skipable" value="1" <s:if test="coursePage.islive==1"> checked</s:if>>
								是&nbsp;&nbsp;&nbsp;
								<input type="radio" name="coursePage.islive" id="skipable" value="0" <s:if test="coursePage.islive==0"> checked</s:if>>
								否
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="right" >
							<span class="neededitem">*</span>是否免费
						</td>
						<td >
							<label>
								<input type="radio" name="coursePage.isfree" id="skipable" value="1" <s:if test="coursePage.isfree==1"> checked</s:if>>
								是&nbsp;&nbsp;&nbsp;
								<input type="radio" name="coursePage.isfree" id="skipable" value="0" <s:if test="coursePage.isfree==0"> checked</s:if>>
								否
							</label>
						</td>
					</tr>
					<tr>
							<td height="25px" align="right" bgcolor="#F8FCFE">
								对应附件：							</td>
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
									stuff.innerHTML="名称：<input type='text' style='width:200px;' name='coursePage.stufftitle'  id='stufftt_"+ii+"'/><input type='hidden' style='width:200px;' name='coursePage.stuffaddr' id='stuff_"+ii+
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
						    	<s:iterator value="coursePage.stuffs">
						    		<div id="ds_">
										<span id='stufft_'>
											名称：
											<input type="hidden" name='coursePage.stuffs.description' value="<s:property value="description"/>" /> 
											<input type="hidden" name='coursePage.stuffs.id' value="<s:property value="id"/>" /> 
											<input type='text' style='width: 200px;' name='coursePage.stuffs.title' value="<s:property value="title"/>" />
											地址：<s:property value="description" /> </span>&nbsp;&nbsp;&nbsp;
										<a
											href="coursePageStuff_delete.action?coursePage.id=<s:property value="coursePage.id"/>&stid=<s:property value="id"/>">删除</a>
									</div>
						    	</s:iterator>
						    </div>
							<!-- <input type="hidden" name="examPaper.queryurl" id="ep_queryurl"
									size="40" /> -->	
								<input type="button" onClick="addSt();" class="textbg4" value="添加">
								<input type="button" onClick="deleteSt();" class="textbg4" value="删除">							</td>
						</tr>
					<tr>
					<tr>
						<td height="30" align="right" >
							<span class="neededitem">*</span>页面属性：
						</td>
						<td >
							<label>
								<select name="coursePage.property" id="property"
									style="width: 150px;">
									<option value="0"
										<s:if test="coursePage.property==0">selected = 'selected'</s:if>
										id="property_0">
										章
									</option>
									<option value="1"
										<s:if test="coursePage.property==1">selected = 'selected'</s:if>
										id="property_1">
										节
									</option>
								</select>
							</label>
						</td>
					</tr>
					<s:if test="course.islink==2">
						<tr>
							<td height="30" align="right" >
								<span class="neededitem">*</span>外部课件地址：
							</td>
							<td >
								<label>
									<s:textfield name="coursePage.page_url"
										cssStyle="width: 300px;" />
								</label>
							</td>
						</tr>
					</s:if>
					<s:if test="course.islink==0">
						<tr>
							<td height="30" align="right" >
								<span class="neededitem">*</span>页面类型：
							</td>
							<td >
								<script type="text/javascript">
								function changeType(){
								 <s:if test="course.status!=0">alert("不能再修改章节类型了喔");
								 		document.getElementById("type").value=<s:property value="coursePage.type"/>;
								 		return false;
								 </s:if> 
									var type = document.getElementById("type").value;
									if(type==2||type==1){
										document.getElementById("multi_file").style.display="block";
										document.getElementById("file_des").innerHTML="媒体文件";
										document.getElementById("page_url_a").style.display="block";
									}
									if(type==0) {
										document.getElementById("multi_file").style.display="none";
										document.getElementById("file_des").innerHTML="媒体文件";
										document.getElementById("page_url_a").style.display="block";
									}
									if(type==3|| type==5||type==6||type==7) {
										document.getElementById("multi_file").style.display="block";
										document.getElementById("page_url_a").style.display="none";
										document.getElementById("file_des").innerHTML="课程地址";
									}
								}
							</script>
								<label>
									<select name="coursePage.type" id="type" style="width: 150px;"
										onChange="changeType();">
										<!-- <option value="0"
											<s:if test="coursePage.type==0">selected = 'selected'</s:if>
											id="type_0">
											图文讲义
										</option> -->
										<option value="1"
											<s:if test="coursePage.type==1">selected = 'selected'</s:if>
											id="type_1">
											纯视频
										</option>
										<option value="2"
											<s:if test="coursePage.type==2">selected = 'selected'</s:if>
											id="type_2">
											视频＋讲义
										</option>
										<option value="3"
											<s:if test="coursePage.type==3">selected = 'selected'</s:if>
											id="type_3">
											外部课程
										</option>
											<option value="4"
											<s:if test="coursePage.type==4">selected = 'selected'</s:if>
											id="type_4">
											视频学习
										</option>
										<option value="5"
											<s:if test="coursePage.type==5">selected = 'selected'</s:if>
											id="type_5">
											宽频学习
										</option>
										<option value="6"
											<s:if test="coursePage.type==6">selected = 'selected'</s:if>
											id="type_6">
											外部宽频学习
										</option>
										<option value="7"
											<s:if test="coursePage.type==7">selected = 'selected'</s:if>
											id="type_7">
											词汇视频学习
										</option>
									</select>
								</label>
								<div id="multi_file"
									style='<s:if test="coursePage.type==0"> display : none; </s:if>'>
									<label>
										<span id="file_des">媒体文件：</span>
									</label>
									<label>
										<s:textfield name="coursePage.page_url"
											cssStyle="width: 400px;" id='pageurl' />
										<span id="page_url_a"><a
											href="javascript:setUrl('pageurl');" class="textbg4" style="width:80px">浏览资源库</a>
										</span>
									</label>
									<!--<br />
																		<br />
																		 <label>
																			媒体文件密码
																			<input type="text" name="page_pwd" id="page_pwd"
																				 />
																		</label>
																		<br />
																		<br /> -->
								</div>
							</td>
						</tr>
						<tr>
							<td height="30" align="right" >
								<span class="neededitem">*</span>结业方式：
							</td>
							<td >
								<label> 
									<s:select theme="simple" list="#{1:'学完',2:'考过',3:'学完且考过'}" value="coursePage.getcredit" name="coursePage.getcredit" />
								</label>
							</td>
						</tr>
						<tr>
						<td height="30" align="right" >
							<span class="neededitem"></span>闪动图：
						</td>
						<td >
							<label>
								<s:textfield name="coursePage.pic_g"
									cssStyle="width: 400px;" id='pic_g' />
								<span ><a
									href="javascript:setUrl('pic_g');" class="textbg4" style="width:80px">浏览资源库</a>
								</span>
							</label>
						</td>
					</tr>
					<tr>
						<td height="30" align="right" >
							<span class="neededitem"></span>亮图：
						</td>
						<td >
							<label>
								<s:textfield name="coursePage.pic_l"
									cssStyle="width: 400px;" id='pic_l' />
								<span ><a
									href="javascript:setUrl('pic_l');" class="textbg4" style="width:80px">浏览资源库</a>
								</span>
							</label>
						</td>
					</tr>
					<tr>
						<td height="30" align="right" >
							<span class="neededitem"></span>灰图：
						</td>
						<td >
							<label>
								<s:textfield name="coursePage.pic_h"
									cssStyle="width: 400px;" id='pic_h' />
								<span><a
									href="javascript:setUrl('pic_h');" class="textbg4" style="width:80px">浏览资源库</a>
								</span>
							</label>
						</td>
					</tr>
						<tr>
							<td height="30" colspan="2" align="center" >
								编辑课程讲义
							</td>
						</tr>
					</s:if>
			  </table>
				<s:if test="course.islink==0||course.islink==3">

				  <div id="lecture_notes" style="text-align: center; width: 100%">
						<label>
							<s:textarea name="coursePage.page" id="content"
								cssStyle="width: 90%; height: 340px; visibility: hidden;" />
						</label>
						<br />
					</div>
				</s:if>
				<s:hidden name="coursePage.id"></s:hidden>
				<s:hidden name="coursePage.course.id"></s:hidden>
				<div style="width:100%;text-align: center;">
				<input type="submit" style="border:none;color: red;" class="textbg" value="确认修改" />
				<input type="button" style="border:none;" onClick="document.location='coursepage_list.action?course.id=${course.id}'" class="textbg" value="取   消" />
				</div>
			</s:form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
