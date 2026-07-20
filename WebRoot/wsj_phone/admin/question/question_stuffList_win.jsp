<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	response
			.setHeader("P3P",
					"CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'");
%>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<TITLE>资源管理</TITLE>
		<base href="<%=basePath%>" target="_self">
		<link href="css/uploadify.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/libutil.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/jquery.uploadify.v2.1.4.js"></script>
		<script type="text/javascript" src="js/swfobject.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/course.js"></script>
		<script type="text/javascript" src="js/stuffBrowse.js"></script>
		<style type="text/css">
body {
	margin: 0px;
	font-size: 12px;
}

body,td,th {
	font-size: 12px;
}

fieldset {
	padding: 10px;
}

img {
	border: 0;
	vertical-align: middle;
}

table {
	width: 100%;
	border: 1px solid #bbd7e6;
}

th {
	border-bottom: 1pxsolid #bbd7e6;
	background-color: #E1ECFE;
}

.STYLE1 {
	color: #FF0000
}

.STYLE2 {
	color: #130AC2
}

.STYLE1 {
	color: #FF0000
}

.STYLE2 {
	color: #130AC2;
	font-size: 14px;
	font-weight: bold;
}

.STYLE5 {
	color: #5097d8;
	font-weight: bold;
}

form {
	margin: 0px;
	padding: 0px;
}

#wjUploader {
	margin: 0px;
	padding: 0px;
}
</style>
		<script type="text/javascript">
				function deleteUserinfo1(obj,id ){
					if(window.confirm("确定删除？")){
					depid = <s:property value="qpstuff.id"/> ;
					$.post("qstuff_delete_user.action", {
						"elUser.id":id,
						"qpstuff.id":depid,
						"x":Math.random
						}, 
						function (data) {
							alert('删除成功');
						});
						obj.parentNode.parentNode.removeChild(obj.parentNode);
					}
				}
				var ts=/^[\d]{0,}$/;
				function create_onsubmit (){
					if($.trim($("#wjj").val())==''){
						alert("输入文件夹名！");
						$("#wjj").focus();
						return false;
					}
					if($.trim($("#wjj_length").val())==''){
						alert("输入文件夹大小！");
						$("#wjj_length").focus();
						return false;
					}
					var wl = $.trim($("#wjj_length").val());
					if(!ts.test(wl)){
						alert("文件夹大小必须为数字！");
						return false;
					}
					if(parseInt(wl)<=0){
						alert("文件夹大小需要大于0！");
						return false;
					}
					
					return true;
				}
				function sizeset_onsubmit (){
					if(parseInt(document.getElementById("wjj_length1").value)<0){
						alert("文件夹大小需要大于等于0！");
					return false;}
					return true;
				}
					
				function up_onsubmit (sublibs){
					var filewj=document.getElementById("wj");
					if(document.getElementById("wj").value==''){
						alert("输入文件！");
						return false;
					}
					fileUpload.submit();
				}
				function setRv(xx){
					var str = "elstuffs<s:property value="qpstuff.stuff_path" escape="false"/>/"+xx;
					window.returnValue = str;
					window.close();
				}
				function setStuffPath(path){
					var str = "elstuffs"+path;
					window.returnValue = str;
					window.close();
				}
				$(document).ready(function()
				{
		            $("#wj").uploadify({
		                'fileDataName': 'st', 
		                'uploader': 'js/uploadify.swf',
		                'script': 'question_stuffadd.action;jsessionid=<%=session.getId()%>', //后台处理
		                'cancelImg': 'images/cancel.png', //单个取消按钮
		                'buttonImg': 'images/upload_file.png',
		                'queueID': 'fileQueue', //文件队列
		                'auto': false, //true：选择文件后自动开始上传；false：手动触发
		                'multi': false, //多文件上传，
		                'width': '101',
		                'height': '37',
		                'scriptData':{"qstuff.parent.id":"<s:property value="qpstuff.id"/>","qpstuff.id":"<s:property value="qpstuff.id" />"},
		                'method':'post',
		                'removeCompleted': false,
		                'onComplete': function(event, queueID, fileObj, response, data) {
		                	var responsedata = null;
		                	try{
		                		responsedata= eval("("+response+")")
		                	}catch(e){alert("上传失败");return false;}
		                	if(responsedata.msg=='success'){
		                		alert("上传完毕"); 
		                		upload_success.submit();
		                		//document.location=document.location.href;
		                	}
		                	else
		                	alert(responsedata.msg);
		                },
		                'onAllComplete': function(event, data) {
		                },
		                'onSelect': function(e, queueId, fileObj) 
						{ 
							if(fileObj.size>parseInt(<s:property value="sublibs" />)*1024*1024){
								alert("您选择的文件过大，请重新选择！");
								return false;
							}
						}
		            });
			});
			function uploadfile1(){
				$("#wj").uploadifyUpload();
			}
			function isDel_(){
				 width=250;
				 height=130;
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv =  window.showModalDialog("admin/question/modalDialog.jsp",null,sFeature);
				 //alert(rv);
				 if(rv==1){
				 	document.getElementById("isDel").value="1";
				 }
				 if(rv!=3&&rv!=undefined){
				 	var files=document.getElementsByName("id");
				 	//alert(files.length);
				 	var bool=false;
				 	for(var i=0;i<files.length;i++){
				 		if(files[i].checked==true){
				 			bool=true;
				 			break;
				 		}
				 	}
				 	if(bool==false){
				 		alert("请至少选择一个复选框！");
				 		return false;
				 	}
				 	file_manage2.submit();
			 	}
			}
		function fileDownload(id,qpid){
			document.getElementById("qsid").value=id;
			document.getElementById("qpsid").value=qpid;
			qstuff.action="question_stuffDownload.action";
			qstuff.submit();
		}
		function filePreview(id,fileext,isSeach){
  	 		if(fileext=="jpg"||fileext=="jpeg"||fileext=="gif"||fileext=="png"||fileext=="tiff"){
  	 			document.getElementById("isseach").value=isSeach;
  	 			document.getElementById("seachQstuffid").value=id;
  	 			seachForm.action="question_stuffPreview.action";
  	 			seachForm.submit();
  	 		}else{
  	 			width=900;
				height=600;
	  	 		//var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
	  	 		var sFeature = "dialogHeight:"+height+"px;dialogWidth:"+width+"px;status:no;resizable:yes;location:no;toolbar:no;menubar:no";
	  	 		window.showModalDialog("question_stuffPreview.action?qstuff.id="+id+"&x="+Math.random(),null,sFeature);
  	 		}
			
		}
		function init(isSeach){
			var disValue="block";
			if(isSeach==1){
				disValue="none";
			}
			$("#tabsver").css("display",disValue);
			$("#pathsver").css("display",disValue);
			$("#returnsver").css("display",disValue);
		}
		function rename(id,title){
			width=200;
			height=20;
			var obj=new Object();
			obj.id=id;
			obj.title=title;
		  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
			var rv =  window.showModalDialog("admin/question/qstuffRename.jsp",obj,sFeature);
			if(rv!=""&&rv!=undefined){
				$("#qstuffId").val(id);
				$("#qstuffTitle").val(rv);
				document.qstuffRename.submit();
			}
		}
		function toStuffList(isWbrowse){
			document.getElementById("isWbrowse").value=isWbrowse;
			upload_success.submit();
		}
		//打开文件夹
		function openFolder(qpstid){
			document.getElementById("qpstid").value=qpstid;
			upload_success.submit();
		}
		function loadStuff(){
			var stuObj= null;
			<s:iterator value='qstuffs'>
				stuObj=new StuffObj('<s:property value="id"/>',
				'${title}',
				'<s:property value="fileext" />',
				'<s:property value="type" />',
				'<s:property value="lengthStr" />',
				'<s:property value="createtime" />',
				'<s:property value="modifytime" />',
				'<s:property value="qpstuff.id" />',
				'<s:property value="qpstuff.stuff_path" /><s:property value="path" />',
				'<s:property value="isSeach" />',
				'<s:property value="stuff_path" />');
				stuObj.displayStuff();
			</s:iterator>
		}
		</script>
		
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%;
}

tr {
	background-color: expression((this .sectionRowIndex%2==0) ?"#ffffff":"#f4f4f4");
}
.textbg4 {
	background-image: url(images/textbg.gif);
	padding-top:2px;
	background-repeat: repeat-x;
	color:#FFFFFF;	font-size: 13px;
	font-weight:bold;
	width: 40px;
	text-align: center;
	text-decoration: none;
}
A.textbg4:link {
	color:#FFFFFF;	font-size: 13px;
	font-weight:bold;
}
A.textbg4:visited {
	color:#FFFFFF;	font-size: 13px;
	font-weight:bold;
}
A.textbg4:hover {
	color:#FFFFFF;	font-size: 13px;
	font-weight:bold;
}
A.textbg4:active {
	color:#FFFFFF;	font-size: 13px;
	font-weight:bold;
}
#pathsver a {color: black;font-weight: bolder;margin-left: 3px;margin-right: 3px}
#pathsver b {margin-left: 3px;margin-right: 3px}
</style>
	</HEAD>
	<body onload="init('<s:property value="isSeach" />');loadStuff();">
		<form action="question_stuffList.action" method="post"
			name="upload_success">
			<s:hidden name="qstuff.id" />
			<s:hidden name="qpstuff.id" id="qpstid" />
			<s:hidden name="isWbrowse" id="isWbrowse" />
		</form>
		<form action="question_stuffDownload.action" method="post"
			name="qstuff">
			<s:hidden name="qstuff.id" id="qsid" />
			<s:hidden name="qpstuff.id" id="qpsid" />
		</form>
		<form action="qstuffRename.action" method="post" name="qstuffRename">
			<input type="hidden" value="<s:property value="qpstuff.id" />" name="qpstuff.id" />
		  	<input type="hidden" id="qstuffId" name="qstuff.id" />
			<input type="hidden" name="qstuff.title" size="20" id="qstuffTitle" />
			<input type="hidden" value="1" name="isWbrowse" />
		</form>
		<div style="position:absolute;right:0px;">
			<a href="javascript:toStuffList(0);" class="textbg4" style="width:140px;">普通方式浏览</a>
		</div>
		<!-- 内容 -->
		<table cellpadding="1" cellspacing="1" style="width:1035px;">
			<tr>
				<td valign="top" id="tree_list_td" width="200">
					<wysLib:stuffTree rootAble="true"
						href="question_stuffList.action?isWbrowse=1&qpstuff.id="></wysLib:stuffTree>
					<wysLib:stuffSharedTree rootAble="true"
						href="question_stuffList.action?isWbrowse=1&st_type=1&qpstuff.id="></wysLib:stuffSharedTree>
				</td>
				<td valign="middle" width="5px" bgcolor="#E8E8E8"
					style="padding: 0px">
					<img src="images/leftmenu/main_55.gif" style="cursor: hand"
						onclick="changeTreeDisplay(this)" />
				</td>
				<td valign="top" width="826">
					<div id="pathsver" style="padding-top: 5px; padding-left: 15px; height: 25px; background-color: #f4f4f4;">
						当前路径：
						<s:if test="qpstuff.type!=7">
							<s:property value="qpstuff.pathZh" escape="false" />
						</s:if>
						<s:else>
							<s:property value="qpstuff.pathZh1" escape="false" />
						</s:else>
					</div>
					<div align="center">
					<s:form action="question_stuffList"  method="post" name="seachForm" theme="simple">
						<s:hidden name="isSeach" value="1" id="isseach" />
						<s:hidden name="isWbrowse" />
						<s:hidden name="qstuff.id" id="seachQstuffid" />
						<span style="margin-right:20px;">
							文件名：<s:textfield name="stuffQuery.title" cssStyle="height:18px;width:100px;"/>
						</span>
						<span>
							文件大小范围：<s:textfield name="stuffQuery.stuffSizeStart" cssStyle="height:18px;width:35px;"/>M&nbsp;~&nbsp;
										<s:textfield name="stuffQuery.stuffSizeEnd" cssStyle="height:18px;width:35px;"/>M
						</span>
						<span style="margin-left:20px;">
							创建时间范围：<input type="text" onclick="setday(this)" style="height:18px;width:135px;" name="stuffQuery.createTimeStart" value="<s:date name="stuffQuery.createTimeStart" format="yyyy-MM-dd HH:mm:ss" />" />&nbsp;~&nbsp;
										<input type="text" onclick="setday(this)" style="height:18px;width:135px;" name="stuffQuery.createTimeEnd" value="<s:date name="stuffQuery.createTimeEnd" format="yyyy-MM-dd HH:mm:ss" />" />
						</span><br />
						<span style="margin-right:20px;">
							修改时间范围：<input type="text" onclick="setday(this)" style="height:18px;width:135px;" name="stuffQuery.modifyTimeStart" value="<s:date name="stuffQuery.modifyTimeStart" format="yyyy-MM-dd HH:mm:ss" />" />&nbsp;~&nbsp;
										<input type="text" onclick="setday(this)" style="height:18px;width:135px;" name="stuffQuery.modifyTimeEnd" value="<s:date name="stuffQuery.modifyTimeEnd" format="yyyy-MM-dd HH:mm:ss" />" />
						</span>
						<span style="margin-right:80px;">
							文件格式：<s:textfield name="stuffQuery.stuffExt" cssStyle="height:18px;width:40px;"/>
						</span>
						<a class="textbg4" href="javascript:document.seachForm.submit();" />搜索</a>
					</s:form>
					</div>
					<form action="question_stuffdelete.action" method="post"
						name="file_manage2" id="file_manage2">
						<table width="826" height="300" align="center" cellspacing="1"
							cellpadding="1">
							<tr>
							<td colspan="7">
							<div style="width:100%;height:300px;overflow:auto;">
								<div id="stuffsContent"></div>
								
							</div>
							
							</td></tr>
							<tr>
								<td colspan="7">
								</td>
							</tr>
						</table>
						<s:hidden name="qpstuff.id" />
						<s:hidden name="isDel" value="0" />
					</form>
					<table cellpadding="1" cellspacing="1" id="tabsver">
						<s:if test="qpstuff.id!=0">
							<tr>
								<td align="right">
									<span style="color:red;"><s:property value="elmessage" /></span>
									<s:if test="qpstuff.type!=7">
										<a class="textbg4"
											href="question_stuffList.action?isWbrowse=<s:property value='isWbrowse' />&qpstuff.id=<s:if test="qpstuff==null||qpstuff.parent==null">0</s:if><s:else><s:property value="qpstuff.parent.id"/></s:else>">返回</a>
									</s:if>
									<s:else>
										<s:if test="qstuff.stuff_path==null||qstuff.stuff_path==''">
										<a class="textbg4"
											href="question_stuffList.action?isWbrowse=<s:property value='isWbrowse' />&qpstuff.id=<s:property value="qpstuff.parent.id"/>">返回</a></s:if>
												<s:else>
										<a class="textbg4"
											href="question_stuffList.action?isWbrowse=<s:property value='isWbrowse' />&qpstuff.id=<s:property value="qpstuff.id"/>&qstuff.stuff_path=<s:property value="qstuff.stuff_path1"/>">返回</a></s:else>
									</s:else><!-- <a 
										href="question_stuffList.action?qpstuff.id=<s:if test="qpstuff==null||qpstuff.parent==null">0</s:if><s:else><s:property value="qpstuff.parent.id"/></s:else>">返回</a> -->
								</td>
							</tr>
						</s:if>
						<s:if test="qpstuff.type!=7&&qpstuff.id!=0">
							<tr>
								<td>
									<table width="100%" cellpadding="1" cellspacing="1">
										<tr>
											<td width="110" align="right">
												<span class="STYLE2">文件夹管理：</span>
											</td>
											<%-- 
											<td width="70">
												<s:if test="qpstuff.type!=7">
													<input type="button" value="删 除" class="textbg4" style="border: none" onClick="return isDel_();" />
												</s:if>
											</td>
											 --%>
											<td width="150px">
												<form action="question_stuffshared.action" method="post">
													<s:hidden name="qpstuff.id" />
													<s:hidden name="isWbrowse" />
													<input type="hidden"
														value="<s:if test="qpstuff.shared==0">1</s:if><s:else>0</s:else>"
														name="qpstuff.shared" />
													<s:if test="qpstuff.shared==0">
														<input type="submit" value="共享该文件夹" class="textbg4" style="border: none;width:90px" onClick="" />
													</s:if>
													<s:if test="qpstuff.shared==1">
														<input type="submit" value="取消共享" class="textbg4" style="border:none;width:70px;" onClick="" />
													</s:if>
												</form>
											</td>
											<td width="250px">
												<s:if test="qpstuff.id!=0">
													<s:form action="question_stuffwjjsizeset.action"
														method="post" theme="simple"
														onsubmit="return sizeset_onsubmit();">
														<s:hidden name="isWbrowse" />
														<span>文件夹大小:</span>
														<s:if test="#session.roleid==1||(#session.roleid!=1&&!qpstuff.isgrant)">
															<input type="text" name="qpstuff.length" size="3"
																value="<s:property value="qpstuff.length/1024/1024"/>"
																id="wjj_length1" />
														</s:if>
														<s:else>
															<s:property value="qpstuff.length/1024/1024" />
														</s:else>
														<span>（M整数）</span>
														<s:hidden name="qpstuff.id" />
														<s:if test="#session.roleid==1||(#session.roleid!=1&&!qpstuff.isgrant)">
															<input type="submit" value="重设" class="textbg4" style="border: none"/>
														</s:if>
													</s:form>
												</s:if>
											</td>
											<td>
												<form action="question_stuffuseradd.action" method="post"
													name="file_manage" id="file_manage">
													<s:hidden name="qpstuff.id" />
													<div id="can_use">
														<span style="float: left;">管理员：</span>
														<s:iterator value="qpstuff.users">
															<span
																style="height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
																<label style="float: left;">
																	<s:property value="realname" />
																</label> <span class="STYLE1">＊</span> </span>
														</s:iterator>
													</div>
													<div style="clear: both;"></div>
												</form>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</s:if>
						<s:if test="qpstuff.id!=0&&qpstuff.type!=7">
							<tr>
								<td>
									<s:form action="question_stuffadd.action" method="post"
										enctype="multipart/form-data" theme="simple" name="fileUpload">
										<s:hidden name="isWbrowse" />
										<table width="100%" cellpadding="1" cellspacing="1">
											<tr>
												<td width="110" align="right">
													<span class="STYLE2">选择上传文件：</span>
												</td>
												<td width="700" align="left">
													<input type="hidden" name="MAX_FILE_SIZE" value="10000000">
													<label id="filebox">
														<input type="hidden" name="qstuff.parent.id"
															value="<s:property value="qpstuff.id"/>" />
														<s:if test="#request.clientType=='movephone'">
															<input type="hidden" name="isMovep" value="yes" />
															<input type="file" name="st" />
														</s:if>
														<s:else>
															<span id="fileQueue" style="width: 370px;"
																class="uploadifyQueue"></span>
															<input type="file" name="st" id="wj" />
														</s:else>
														<s:hidden name="qpstuff.id" />
													</label>
													&nbsp;&nbsp;&nbsp;
													<s:if test="#request.clientType=='movephone'">
														<input type="submit"  value="上传"  />
													</s:if>
													<s:else>
														<input type="button" style="height: 40px;border: none;"
															onclick="uploadfile1()" value="上传" class="textbg4" />
													</s:else>
													(文件大小不超过
													<s:property value="sublibs" />
													M)
												</td>
												<td>
													&nbsp;
												</td>
											</tr>
										</table>
									</s:form>
								</td>
							</tr>
						</s:if>
						<s:if test="qpstuff.type!=7&&(#session.roleid==1||(#session.roleid!=1&&qpstuff.id>0))">
							<tr>
								<td valign="top">
									<s:form action="question_stuffwjjadd" method="post"
										theme="simple" onsubmit="return create_onsubmit();"
										cssStyle="float:left ">
										<s:hidden name="isWbrowse" />
										<table width="100%" cellpadding="1" cellspacing="1">
											<tr>
												<td width="110" valign="top" align="right">
													<span class="STYLE2">新建文件夹：</span>
												</td>
												<td width="200">
													名称:
													<s:textfield name="qstuff.title" id="wjj" />
													<input type="hidden" name="qstuff.parent.id"
														value="<s:property value="qpstuff.id"/>" />
												</td>
												<td width="200">
													文件夹大小:
													<s:textfield name="qstuff.length" size="4" id="wjj_length" />
													（M整数）
													<s:hidden name="qpstuff.id" />
												</td>
												<td width="60">
													<input type="submit" class="textbg4" style="border: none" value="新建" />
												</td>
												<td>
													&nbsp;
												</td>
											</tr>
										</table>
									</s:form>
								</td>
							</tr>
						</s:if>
						<s:if test="#session.roleid==1">
							<tr>
								<td>
									<s:form action="question_stuffsizeset" method="post"
										theme="simple" cssStyle="float:left ">
										<s:hidden name="isWbrowse" />
										<table width="100%" cellpadding="1" cellspacing="1"
											align="right">
											<tr>
												<td width="110" align="right">
													<span class="STYLE2">上传大小限制：</span>
												</td>
												<td width="230">
													设置:
													<s:textfield name="sysconf.stuff_size" size="4" id="" />
													（M整数）
													<s:hidden name="qpstuff.id" />
												</td>
												<td>
													<input type="submit" class="textbg4" style="border: none" value="设置" />
												</td>
												<td>
													&nbsp;
												</td>
											</tr>
										</table>
									</s:form>
								</td>
							</tr>
						</s:if>
						<tr>
							<td>
							解压文件说明：1、只能解压ZIP包；2、只显示解压后的第一级目录中的文件，第二级目录下的文件不显示；3、压缩文件中不要包含中文名。
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<!-- <tr>
				<td colspan="3" bgcolor="#f4f4f4" align="left">
					解压文件说明：1、只能解压ZIP包；2、只显示解压后的第一级目录中的文件，第二级目录下的文件不显示；3、压缩文件中不要包含中文名。
				</td>
			</tr> -->
		</table>
		<!-- 内容 -->
	
	</body>
</HTML>