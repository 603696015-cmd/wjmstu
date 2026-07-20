<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.courseman.entities.Course"%>
<%@page import="com.sopia.common.JTMSystemConfOp"%>
<%@page import="com.sopia.ElConstants"%>
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
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/ElclassEroomConditions.js"></script>
		<script type="text/javascript" src="editor/fckeditor.js"></script>
		<script type="text/javascript"> 
			function deleteNames(type){
				if(type == 1){ 
					document.getElementById("isEroomName").value=0; 
					document.getElementById("KC_").innerHTML="";
					document.getElementById("KC_").style.display='none';
					return true;
				}else
				if(type == 2){
					document.getElementById("isClassName").value=0; 
					document.getElementById("PXB_").innerHTML="";
					document.getElementById("PXB_").style.display='none'; 
					return true;
				}
			}
			function _onsubmit(){
				if($("#cname").val()==''){
					alert("请填写课程名称");
					$("#cname").focus();
					return false;
				} 
				if(jzArr.length != 0)
				document.getElementById("jz").value=jzidArr.join(",");
				if(gwArr .length != 0)
				document.getElementById("gw").value=gwidArr.join(",");
				if(dsArr .length != 0)
				document.getElementById("ds").value=dsidArr.join(",");
				if(zwArr .length != 0)
				document.getElementById("zw").value=zwidArr.join(",");
				if(zjArr .length != 0)
				document.getElementById("zj").value=zjidArr.join(",");
				if(bmarr .length != 0)
				document.getElementById("bm").value=bmidArr.join(",");
				return true;
			}
			function searchUserInit(comp){
			     width=600;
				 height=500;
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv =  window.showModalDialog("mess_sendUserlistInit.action?x="+Math.random(),null,sFeature);
				 if(null==rv){
				 	alert('您没有选择用户！');
				 }else{
				 	if(rv[0]<=0)  	alert('您没有选择用户！');
				 	else{
				 	//	alert(rv[0]);
				 	$.post("mess_getUserInfoJson.action", {
						"elUser.id":rv[0],
						"x":Math.random
						}, 
						function (data) {
							var dataObj=eval("("+data+")");
							document.getElementById("t_id").value=dataObj.elUser.id;
							document.getElementById("t_name").value=dataObj.elUser.realname;
						}); 
				 }
				 }
			}
			
			function checkValues(){
			     if ( courseInfo.coursezipfile.value == "" ){
			        alert( "请选择文件进行导入" );
			        return false;
			     }else{
			        file = courseInfo.coursezipfile.value;
			        index = file.length;       
			        start = index - 4;
			        extension = file.substring(start, index);
			        extension = extension.toLowerCase();
			
			        if (extension != ".zip"){
			           alert( "选择的文件不是zip文件, 请选择一个zip文件上传" );
			           return false;
			        }
			     }
			     
			     courseInfo.theZipFile.value = courseInfo.coursezipfile.value;
			     return true;
			  }  
				function disDivSQ(op){
					var divSQ1=document.getElementById("divSQ_1");
					var divSQ2=document.getElementById("divSQ_2");
					if(op==1){
						divSQ1.style.display="none";
						divSQ2.style.display="block";
						document.getElementById("optional").value="";
					}else{
						divSQ1.style.display="block";
						divSQ2.style.display="none";
					}
				}
		</script>

		<script type="text/javascript">
		var isIplink='<s:property value="course.isIpLimit"/>';
			var bmarr = new Array();
			var bmidArr = new Array();
			var initBM = '<s:property value="eoRegistration.treeType"/>';
			if(initBM){
			  bmidArr = initBM.split(",");
			}
			var jzArr = new Array();
			var jzidArr = new Array();
			var initJZ = '<s:property value="coRegistration.jingzhong"/>';
			if(initJZ){
			  jzidArr = initJZ.split(",");
			}
			var zwArr = new Array();
			var zwidArr = new Array(); 
			var initZW = '<s:property value="coRegistration.zhiwu"/>';
			if(initZW){
			  zwidArr = initZW.split(",");
			}
			var zjArr = new Array();
			var zjidArr = new Array();
			var initZJ = '<s:property value="coRegistration.zhiji"/>';
			if(initZJ){
			  zjidArr = initZJ.split(",");
			}
			var gwArr = new Array();
			var gwidArr = new Array();
			var initGW = '<s:property value="coRegistration.gangwei"/>';
			if(initGW){
			  gwidArr = initGW.split(",");
			}
			var dsArr = new Array();
			var dsidArr = new Array();
			var initDS = '<s:property value="coRegistration.dishi"/>';
			if(initDS){
			  dsidArr = initDS.split(",");
			}
			var typeid;
			function searchUsersInit(){ 
			     width=600;
				 height=500;
				 var danwei ="";
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv =  window.showModalDialog("optionDep.action?searbm="+bmidArr.join(',')+"&x="+Math.random(),null,sFeature); 
				 if(rv == null){
					alert("您没有任何选取！");					
				 }else{ 
					 bmarr = getArray(rv);  
					 var bmid = bmarr.pop();   
					 bmidArr = bmid.split("-"); 
					 for(var i = 0; i < bmarr.length;i++){     
						 danwei =danwei+"<div id='danwei_"+i+"' style='float:left;background-color:#dcddde;padding-left:16px;width=120;'>"+bmarr[i]+" <span style='color:red;' onclick=onX("+i+",'"+bmidArr[i]+"') >X</span></div>";
						 var hid = "<input type='checkbox' name='bmtj' value = '"+bmarr[i]+"'>"
						 document.getElementById("danwei").innerHTML= danwei; 
					 } 
					 document.getElementById("danwei").style.display='block';
				 }  
			}
			function searBaseDatatInit(j){ 
				 typeid = j;
			     width=600;
				 height=500;  
				 var tj =""; 
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 if(j == 1){//1  警 种   
				 	var rv = window.showModalDialog("optionBaseDatat.action?searbase="+jzidArr.join(',')+"&baseDatat.typeid="+j+"&x="+Math.random(),null,sFeature);     
					
					 if(rv == null){
						alert("您没有任何选取！");					
					 }else{
						jzArr = getArray(rv);  
						var jzTid = jzArr.pop();  
						var jzid = jzArr.pop(); 
					 	jzidArr = jzid.split("-");   
						for(var i = 0; i < jzArr.length;i++){ 
						 	tj =tj+"<div id='jztj_"+i+"' style='float:left;background-color:#dcddde;padding-left:16px;width:100;'>"+jzArr[i]+" <span style='color:red;' onclick=onbaseX('jztj_','"+i+"','"+jzTid+"','"+jzidArr[i]+"')>X</span></div>";
						 	document.getElementById("jztj").innerHTML= tj;
						    document.getElementById("jztj").style.display='block'; 
					    }
				    }
				}else if(j == 2){//职务 
				 	var rv = window.showModalDialog("optionBaseDatat.action?searbase="+zwidArr.join(',')+"&baseDatat.typeid="+j+"&x="+Math.random(),null,sFeature);     
				 	 if(rv == null){
						alert("您没有任何选取！");					
					 }else{
					 	zwArr = getArray(rv); 
						var zwTid = zwArr.pop();
						var zwid = zwArr.pop(); 
					 	zwidArr = zwid.split("-");  
						for(var i = 0; i < zwArr.length;i++){ 
						 	tj =tj+"<div id='zwtj_"+i+"' style='float:left;background-color:#dcddde;padding-left:16px;width:100;'>"+zwArr[i]+" <span style='color:red;' onclick=onbaseX('zwtj_','"+i+"','"+zwTid+"','"+zwidArr[i]+"')>X</span></div>";
						 	document.getElementById("zwtj").innerHTML= tj;
						    document.getElementById("zwtj").style.display='block';
				    	}
				    }
				}else if(j == 3){//职级
					var rv = window.showModalDialog("optionBaseDatat.action?searbase="+zjidArr.join(',')+"&baseDatat.typeid="+j+"&x="+Math.random(),null,sFeature);     
					 if(rv == null){
						alert("您没有任何选取！");					
					 }else{
						zjArr = getArray(rv);  
						var zjTid = zjArr.pop();
						var zjid = zjArr.pop(); 
					 	zjidArr = zjid.split("-");  
						for(var i = 0; i < zjArr.length;i++){ 
						 	tj =tj+"<div id='zjtj_"+i+"' style='float:left;background-color:#dcddde;padding-left:16px;width:100;'>"+zjArr[i]+" <span style='color:red;' onclick=onbaseX('zjtj_','"+i+"','"+zjTid+"','"+zjidArr[i]+"')>X</span></div>";
						 	document.getElementById("zjtj").innerHTML= tj;
						    document.getElementById("zjtj").style.display='block';
				    	}
				    }
				}else if(j == 4){//岗位
					var rv = window.showModalDialog("optionBaseDatat.action?searbase="+gwidArr.join(',')+"&baseDatat.typeid="+j+"&x="+Math.random(),null,sFeature);     
					 if(rv == null){
						alert("您没有任何选取！");					
					 }else{
						gwArr = getArray(rv);  
						var gwTid = gwArr.pop();
						var gwid = gwArr.pop(); 
					 	gwidArr = gwid.split("-");  
						for(var i = 0; i < gwArr.length;i++){ 
						 	tj =tj+"<div id='gwtj_"+i+"' style='float:left;background-color:#dcddde;padding-left:16px;width:100;'>"+gwArr[i]+" <span style='color:red;' onclick=onbaseX('gwtj_','"+i+"','"+gwTid+"','"+gwidArr[i]+"')>X</span></div>";
						 	document.getElementById("gwtj").innerHTML= tj;
						    document.getElementById("gwtj").style.display='block';
				    	}
				    }
				}else if(j == 5){//地市
					var rv = window.showModalDialog("optionBaseDatat.action?searbase="+dsidArr.join(',')+"&baseDatat.typeid="+j+"&x="+Math.random(),null,sFeature);     
					 if(rv == null){
						alert("您没有任何选取！");					
					 }else{
						dsArr = getArray(rv);  
						var dsTid = dsArr.pop();
						var dsid = dsArr.pop(); 
					 	dsidArr = dsid.split("-");  
						for(var i = 0; i < dsArr.length;i++){ 
						 	tj =tj+"<div id='dstj_"+i+"' style='float:left;background-color:#dcddde;padding-left:16px;width:100;'>"+dsArr[i]+" <span style='color:red;' onclick=onbaseX('dstj_','"+i+"','"+dsTid+"','"+dsidArr[i]+"')>X</span></div>";
							document.getElementById("dstj").innerHTML= tj;
						    document.getElementById("dstj").style.display='block';
					    }
				    }
				}else{
				 	alert("类型不匹配");
				 } 
			}
			function onX(i,name){    
				document.getElementById("danwei_"+i).style.display='none'; 
				bmidArr = bmidArr.del(name); 
				if(bmidArr.length == 0){
					document.getElementById("danwei").style.display='none';
				} 
			}
			function onbaseX(id,i,Tid,name){    
				if(Tid == 1){    
					jzidArr = jzidArr.del(name);  
					if(jzidArr.length == 0){
						document.getElementById("jztj").style.display='none'; 
					}    
				}else if(Tid == 2){   
					zwidArr = zwidArr.del(name); 
					if(zwidArr.length == 0){
						document.getElementById("zwtj").style.display='none';
					}     
				}else if(Tid == 3){  
					zjidArr = zjidArr.del(name); 
					alert(zjidArr.length);
					if(zjidArr.length == 0){
						document.getElementById("zjtj").style.display='none'; 
					}       
				}else if(Tid == 4){  
					gwidArr = gwidArr.del(name); 
					if(gwidArr.length == 0){
						document.getElementById("gwtj").style.display='none';
					}     
				}else if(Tid == 5){  
					dsidArr = dsidArr.del(name); 
					if(dsidArr.length == 0){
						document.getElementById("dstj").style.display='none';
					}       
				}   
				document.getElementById(id+i).style.display='none'; 
			}
			function getArray(array){ 
				var arr = new Array();
				if(array.length != 0){
					for(var i = 0; i < array.length;i++){     
					 	arr.push(array[i]); 
					} 
				}
				return arr;
			}
			 Array.prototype.del=function(name) { 
			  var news = new Array();   
			  for(var i = 0;i<this.length;i++){
			  	if(this[i] != name){ 
			  		news.push(this[i]);
			  	}
			  }		  
			  return news; 
			 }
			 
			 function myload(){
			 	var checkboxs = document.getElementsByName("weidu");
			 	var values = '${course.weidu}';
			 	var values_array;
			 	var checkbox;
			 	if(values != ""){
			 		values_array = values.split(",");
			 		if(values_array!=undefined){
			 			for(var i=0;i<checkboxs.length;i++){
			 				checkbox = checkboxs[i];
			 				if(isIn(values_array,checkbox)){
			 					checkbox.checked = true;
			 				}
			 			}
			 		}
			 	}
			 }
			 function isIn(arr,obj){
			 	var flag = false;
			 	for(var i=0;i<arr.length;i++){
			 		if(parseInt(arr[i])==parseInt(obj.value)){
			 			flag = true;
			 		}
			 	}
			 	return flag;
			 }
			 
			 
			 	function createeditor(obj){
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
			</script>
	</HEAD>
	<BODY>
	<BODY onload="myload();">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="课程修改页" />
				</div>
			</li>
			<!--<li><span style="font-weight: bold;">修改课程信息</span>
		</li>
		<li class="sep"></li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div>
			<s:form action="course_alter" method="post" name="course_info"
				theme="simple" id="course_info" onsubmit="return _onsubmit()">
				<!--<div>
					<B>注意：</B> 1、课程开通后将不能修改学习信息（时间，章节等信息）；
					<br />
					2、课件开通后将提交给管理员审核。通过后方可使用。
				</div>
				-->
				<div style="font-size: 15px; font-weight: bolder">
					<span style="color: blue"> <!--<s:if test="course.status ==0">提示：不能再编辑学习信息(时间等)</s:if>-->
						<s:if test="course.status  == 1">提示：课程已开通,不能再进行修改!需要修改请 <span
								style="color: red">申请修改</span>
						</s:if> <s:if test="course.status  == 2">提示：已提交开通申请,不能再进行修改!需要修改请 <div
								style="color: red;">
								申请修改
							</div>
						</s:if> </span>
				</div>
				<table width="100%" cellpadding="1" cellspacing="1" bgcolor="#ECEDEB">
					<tr>
						<td width="160" height="30" align="center" bgcolor="#FFFFFF">
							课程名称：
						</td>
						<td bgcolor="#FFFFFF">
							<label>
								<s:textfield id="cname" name="course.name" size="60" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" bgcolor="#FFFFFF">
							课程介绍：
						</td>
						<td bgcolor="#FFFFFF">
							<label>
								<s:textarea name="course.description" cols="60" rows="7"></s:textarea>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right">
							课程详情：
						</td>
						<td>
							<label>
								&nbsp;<s:textarea name="course.courseDetail" cols="60" rows="7" onfocus="createeditor(this);"></s:textarea>
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" bgcolor="#FFFFFF">
							讲师姓名：
						</td>
						<td bgcolor="#FFFFFF">
							<label>
								<s:textfield name="course.teacherName" id="t_name" size="40" />
								<s:hidden name="course.teacherId" id="t_id" />
								<input class="textbg2" type="button"
									onClick="searchUserInit('messUser')" value="查找">
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" bgcolor="#FFFFFF">
							师资介绍：
						</td>
						<td bgcolor="#FFFFFF">
							<label>
								<s:textarea name="course.teacherinfo" cols="60" rows="7"></s:textarea>
							</label>
						</td>
					</tr>
					<!-- <tr>
						<td width="160" height="30" align="center" bgcolor="#FFFFFF">
							学习计划：
						</td>
						<td bgcolor="#FFFFFF">
							<label>
								<s:textarea name="course.studyplan" cols="60" rows="7"></s:textarea>
							</label>
						</td>
					</tr>
					-->
					<tr>
						<td width="160" height="30" align="center" bgcolor="#FFFFFF">
							课程图片：
						</td>
						<td bgcolor="#FFFFFF">
							<label>
								<s:textfield name="course.mainimg" size="60" id="pic" readonly="true" />
								<a href="javascript:setUrl('pic');" class="textbg">浏览资源库</a>
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" bgcolor="#FFFFFF">
							讲师图片：
						</td>
						<td bgcolor="#FFFFFF">
							<label>
								<s:textfield name="course.lecturerMainimg" size="60" id="lmpic" readonly="true" />
								<a href="javascript:setUrl('lmpic');" class="textbg">浏览资源库</a>
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" bgcolor="#FFFFFF">
							所属类别：<%
							String xx = ((Course) request.getAttribute("course"))
										.getCtype().getId()
										+ "";
						%>
						</td>
						<td bgcolor="#FFFFFF">
							<label>
								<wysLib:ctypeTree did="0" iname="course.ctype.id"
									itype="ra_return" ivalue="<%=xx%>">
								</wysLib:ctypeTree>
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" bgcolor="#FFFFFF" >
							对应论坛坛版块：
						</td>
						<td  bgcolor="#FFFFFF">
							<SELECT name="course.forumid">
									<s:iterator value="fbtypes" status="fbtst">
									<optgroup label="<s:property value="name" />"><s:property value="name" /> </optgroup>
										<s:iterator value="fblocks" status="fbs" id="fbsid">
											<option <s:if test="course.forumid==#fbsid.id">selected='selected'</s:if> value="<s:property value="#fbsid.id"/>"><s:property value="#fbsid.title" /></option>
										</s:iterator>
									</s:iterator>
									</SELECT>							
						</td>
					</tr>

					<tr>
						<td width="160" height="30" align="center" bgcolor="#FFFFFF">
							推荐学分：
						</td>
						<td bgcolor="#FFFFFF">
							<label>
								<input type="text" value="<s:property value="course.credit"/>"
									name="course.credit" />
							</label>
						</td>
					</tr>
					<!--<tr>
						<td width="160" height="30" align="center" bgcolor="#FFFFFF">
							通过成绩：
						</td>
						<td bgcolor="#FFFFFF">
							<label>
								<s:textfield cssStyle="width:40px;" name="course.passgrade" />
								%
							</label>
						</td>
					</tr>
					-->
					<tr>
						<td align="center" bgcolor="#FFFFFF">
							课程类型：
						</td>
						<td bgcolor="#FFFFFF">
							<label>
								<s:property value="course.islinkName" />
								<s:if test="course.islink==1">
									<span id="url_desc">外部课程链接地址</span>
									<input name="course.exurl" type="text" id="exurl"
										value="<s:property value="course.exurl"/>" size="50">
									<span id="exurl_a"><a href="javascript:setUrl('exurl');">浏览我的资源库</a>
									</span>
								</s:if>
								<s:if test="course.islink==3">
									<span id="url_desc">视频地址</span>
									<input name="course.exurl" type="text" id="exurl"
										value="<s:property value="course.exurl"/>" size="50">

									<span id="exurl_a"><a href="javascript:setUrl('exurl');">浏览我的资源库</a>
									</span>
								</s:if>
								<br />
								<s:if test="course.islink==1">
									
									学习时间：
									<input name="course.during" type="text"
										value="<s:property value="course.during"/>" size="5">
									分钟 学习询问时间：
									<input name="course.querytime" type="text"
										value="<s:property value="course.querytime"/>" size="5">
									分钟
								</s:if>
								<s:elseif test="course.islink==4">
									开始时间：
									<input name="course.room.id" type="hidden"
										value="<s:property value="course.room.id"/>">
									<input name="course.roomstart"
										value="<s:date format="yyyy-MM-dd HH:mm:ss" name="course.roomstart"/>"
										id="r_start" type="text" size="20" onclick="setday(this)">
									结束时间：
									<input name="course.roomend"
										value="<s:date format="yyyy-MM-dd HH:mm:ss" name="course.roomend"/>"
										id="r_end" type="text" size="20" onclick="setday(this)">
									<br />
									学习时间：
									<input name="course.during" type="text"
										value="<s:property value="course.during"/>" size="5">
									分钟 学习询问时间：
									<input name="course.querytime" type="text"
										value="<s:property value="course.querytime"/>" size="5">
									分钟
								</s:elseif>
								<s:else>
									开始时间：
									<input name="course.room.id" type="hidden"
										value="<s:property value="course.room.id"/>">
									<input name="course.roomstart"
										value="<s:date format="yyyy-MM-dd HH:mm:ss" name="course.roomstart"/>"
										id="r_start" type="text" size="20" onclick="setday(this)">
									结束时间：
									<input name="course.roomend"
										value="<s:date format="yyyy-MM-dd HH:mm:ss" name="course.roomend"/>"
										id="r_end" type="text" size="20" onclick="setday(this)">
									<br />
									学习时间：
									<s:property value="course.during" /> 
									分钟
								</s:else> 
							</label>
						</td>
					</tr>
					<tr>
						<td align="center" bgcolor="#FFFFFF">课程格式:</td>
						<td height="30" align="left" bgcolor="#FFFFFF">
							<label>
								<select name="course.courseForm">
									<s:iterator value="course.courseForms" status="jzs">
										<option
											<s:if test="course.courseForm == #jzs.index">selected = 'selected'</s:if>
											value="<s:property value="#jzs.index"/>">
											<s:property />
										</option>
									</s:iterator>
								</select> 
							</label> 
						</td>
					</tr>
					<tr>
						<td align="center" bgcolor="#FFFFFF">课程样式：</td>
						<td bgcolor="#FFFFFF"> 
							<select name="course.courseCss">  
								<option value="1" <s:if test="course.courseCss == 1 ">selected="selected"</s:if>>选修</option>
								<option value="0" <s:if test="course.courseCss == 0 ">selected="selected"</s:if>>必修</option> 
							</select> 
						</td>
					</tr>
					<tr>
					  <td align="center" bgcolor="#FFFFFF"><s:property value="baseCourseTypeList[0].name" />：</td>
						<td bgcolor="#FFFFFF">   
							<s:select name="course.shihegangwei" cssClass="g-select" list="shihegangweis"
									listKey="id" listValue="basevalue" /> 
						</td>
					</tr>
					<tr>
					  <td align="center" bgcolor="#FFFFFF"><s:property value="baseCourseTypeList[1].name" />：</td>
						<td bgcolor="#FFFFFF">   
							<s:select name="course.zhuanyeleibie" cssClass="g-select" list="zhuanyeleibies"
									listKey="id" listValue="basevalue" /> 
						</td>
					</tr>
					<tr>
					  <td align="center" bgcolor="#FFFFFF"><s:property value="baseCourseTypeList[2].name" />：</td>
						<td bgcolor="#FFFFFF">   
							<s:select name="course.zhuanyejibie" cssClass="g-select" list="zhuanyejibies"
									listKey="id" listValue="basevalue" /> 
						</td>
					</tr>
					<tr>
					  <td align="center" bgcolor="#FFFFFF"><s:property value="baseCourseTypeList[3].name" />：</td>
						<td bgcolor="#FFFFFF">   
							<s:select name="course.shihebumen" cssClass="g-select" list="shihebumens"
									listKey="id" listValue="basevalue" /> 
						</td>
					</tr>
					<tr>
					  <td align="center" bgcolor="#FFFFFF"><s:property value="baseCourseTypeList[4].name" />：</td>
						<td bgcolor="#FFFFFF">   
							<s:select name="course.neirongleixing" cssClass="g-select" list="neirongleixings"
									listKey="id" listValue="basevalue" /> 
						</td>
					</tr>
					<tr>
					  <td align="center" bgcolor="#FFFFFF"><s:property value="baseCourseTypeList[5].name" />：</td>
						<td bgcolor="#FFFFFF">   
							<s:select name="course.peixunleibie" cssClass="g-select" list="peixunleibies"
									listKey="id" listValue="basevalue" /> 
						</td>
					</tr>
					<tr>
					  <td align="center" bgcolor="#FFFFFF"><s:property value="baseCourseTypeList[6].name" />：</td>
						<td bgcolor="#FFFFFF">   
							<s:select name="course.shihexuewei" cssClass="g-select" list="shihexueweis"
									listKey="id" listValue="basevalue" /> 
						</td>
					</tr>
					<tr>
					  <td align="center" bgcolor="#FFFFFF"><s:property value="baseCourseTypeList[7].name" />：</td>
						<td bgcolor="#FFFFFF">   
							<s:select name="course.kechengxingzhi" cssClass="g-select" list="kechengxingzhis"
									listKey="id" listValue="basevalue" /> 
						</td>
					</tr>
					<!-- tr>
						<td align="center" bgcolor="#FFFFFF" style="color: black">
							获得学分方式：
						</td>
						<td bgcolor="#FFFFFF">
							<s:if test="course.status==0">
								<label>
									学习完获得
								</label>
								<input type="radio"
									<s:if test="course.creditmod==0">checked="checked"</s:if>
									name="course.creditmod" value="0"> &nbsp;&nbsp;
								<label>
									进度X学分
								</label>
								<input type="radio"
									<s:if test="course.creditmod==1">checked="checked"</s:if>
									name="course.creditmod" value="1">
							</s:if>
							<s:else>
								<label>
									<s:if test="course.creditmod==0">学习完获得</s:if>
									<s:if test="course.creditmod==1">进度X学分</s:if>
								</label>
								<input type="hidden" name="course.creditmod"
									value="<s:property value="course.creditmod"/>" />
							</s:else>
						</td>
					</tr
				<tr>
					<td align="center" bgcolor="#FFFFFF">标准笔记字数：</td>
					<td bgcolor="#FFFFFF"><input type="text" size="4"
						value="<s:property value="course.notenumber"/>"
						name="course.notenumber"> 笔记提交时间： <input type="text"
						size="16"
						value="<s:date format="yyyy-MM-dd HH:mm:ss" name="course.notedate"/>"
						name="course.notedate" onclick="setday(this)">
					</td>
				</tr>-->
					<tr>
						<td height="30" align="center" bgcolor="#FFFFFF">
							是否可申请：
						</td>
						<td height="30" bgcolor="#FFFFFF">
							<input type="radio" name="course.isApplication" value="0"
								onClick="disDivSQ(1);"
								<s:if test="course.isApplication==0"> 
								 checked="checked"
							</s:if> />
							不可申请
							<input type="radio" name="course.isApplication" value="1"
								onClick="disDivSQ(2);"
								<s:if test="course.isApplication==1">
								 checked="checked"
							</s:if> />
							可申请
							<div id="divSQ_1"
								<s:if test="course.isApplication==0">style="display:none;"</s:if>>
								<table width="95%" cellpadding="2" cellspacing="1"
									bgcolor="#ECEDEB">
									<!--<tr>
									<td width="100" height="20" align="center" bgcolor="#E6F9F9">
										复核人员:
									</td>
									<td>
										<div id="valids">
											<s:iterator value="elclass.valids">
												<span
													style="width: 110px; height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
													<label style="width: 80px; float: left;">
														<s:property value="realname" />
													</label> <a
													style="cursor: hand; float: right; width: 14px; height: 14px;"
													href=""
													onclick="javascript:deleteUserinfo(this,<s:property value="id"/>,'valids');return false;">X</a>
												</span>
											</s:iterator>
										</div>
										<a href=""
											onclick="searchUserInit('valids','elclass.valids.id'); return false;" class="textbg4">添加</a>
									</td>
								</tr>-->
									<tr>
										<td width="100" align="center" bgcolor="#E6F9F9">
											计划招收学员:
										</td>
										<td>
											<s:textfield name="coRegistration.PlanRecruitStudents"
												id="PlanRecruitStudents" />
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<span style="color: red">申请条件</span>
										</td>
									</tr>
									<tr>
										<td width="100" align="center" bgcolor="#E6F9F9">
											报名时间段:
										</td>
										<td>
											<input name="coRegistration.RegistrationStartTime"
												id="r_start" type="text" size="20" onClick="setday(this)"
												value="<s:date name="coRegistration.RegistrationStartTime" format="yyyy-MM-dd HH:mm:ss"/>">
											～
											<input name="coRegistration.RegistrationStopTime"
												id="r_start" type="text" size="20" onClick="setday(this)"
												value="<s:date name="coRegistration.RegistrationStopTime" format="yyyy-MM-dd HH:mm:ss"/>">
										</td>
									</tr>
									<tr>
										<td width="100" align="center" bgcolor="#E6F9F9">
											年龄段:
										</td>
										<td>
											<s:textfield name="coRegistration.StartAge" id="StartAge" />
											～
											<s:textfield name="coRegistration.StopAge" id="StopAge" />
										</td>
									</tr>
									<tr>
										<td width="100" align="center" bgcolor="#E6F9F9">
											性别:
										</td>
										<td>
											<label>
												<input type="radio" name="coRegistration.sex" value="不限"
													checked="checked" />
												不限
												<input type="radio" name="coRegistration.sex" value="男" />
												男
												<input type="radio" name="coRegistration.sex" value="女" />
												女
											</label>
										</td>
									</tr>
									<tr>
										<td width="100" align="center" bgcolor="#E6F9F9">
											部门:
										</td>
										<td>
											<div id="danwei"
												<s:if test="coRegistration.treeType == null ">style="background-color:blank;display: none;"</s:if>>
												<s:property value="coRegistration.treeTypeName" />
								  </div>
											<span class="txt-info"><a href="#"
												onClick="searchUsersInit();return false;">点此进行选择</a>
											</span>
										</td>
									</tr>
									<tr>
										<td width="100" align="center" bgcolor="#E6F9F9">
											<wysLib:BasetName btid="1" />:
										</td>
										<td>
											<div id="jztj"
												<s:if test="coRegistration.jingzhong == null ">style="background-color:blank;display: none;"</s:if>>
												<s:property value="coRegistration.jingzhongName" />
											</div>
											<span class="txt-info"><a href="#"
												onClick="searBaseDatatInit(1);return false;">点此进行选择</a>
											</span>
										</td>
									</tr>
									<tr>
										<td width="100" align="center" bgcolor="#E6F9F9">
											<wysLib:BasetName btid="5" />:
										</td>
										<td>
											<div id="dstj"
												<s:if test="coRegistration.dishi == null ">style="background-color:blank;display: none;"</s:if>>
												<s:property value="coRegistration.dishiName" />
											</div>
											<span class="txt-info"><a href="#"
												onClick="searBaseDatatInit(5);return false;">点此进行选择</a>
											</span>
										</td>
									</tr>
									<tr>
										<td width="100" align="center" bgcolor="#E6F9F9">
											<wysLib:BasetName btid="2" />:
										</td>
										<td>
											<div id="zwtj"
												<s:if test="coRegistration.zhiwu == null ">style="background-color:blank;display: none;"</s:if>>
												<s:property value="coRegistration.zhiwuName" />
											</div>
											<span class="txt-info"><a href="#"
												onClick="searBaseDatatInit(2);return false;">点此进行选择</a>
											</span>
										</td>
									</tr>
									<tr>
										<td width="100" align="center" bgcolor="#E6F9F9">
											<wysLib:BasetName btid="3" />:
										</td>
										<td>
											<div id="zjtj"
												<s:if test="coRegistration.zhiji == null ">style="background-color:blank;display: none;"</s:if>>
												<s:property value="coRegistration.zhijiName" />
											</div>
											<span class="txt-info"><a href="#"
												onClick="searBaseDatatInit(3);return false;">点此进行选择</a>
											</span>
										</td>
									</tr>
									<tr>
										<td width="100" align="center" bgcolor="#E6F9F9">
											<wysLib:BasetName btid="4" />:
										</td>
										<td>
											<div id="gwtj"
												<s:if test="coRegistration.gangwei == null ">style="background-color:blank;display: none;"</s:if>>
												<s:property value="coRegistration.gangweiName" />
											</div>
											<span class="txt-info"><a href="#"
												onClick="searBaseDatatInit(4);return false;">点此进行选择</a>
											</span>
										</td>
									</tr>
									<tr>
										<td width="100" align="center" bgcolor="#E6F9F9">
											考场:
										</td>
										<td>
											<div id="KC"
												<s:if test="coRegistration.examroomName == null ">style="display: none;width: 100%;"</s:if>>
											</div>
											<div id="KC_" <s:if test="coRegistration.examRoom.size == 0 ">style="display: none;width: 100%;"</s:if>>
												<s:property value="coRegistration.examroomName" />
											<a  href="" style="color:red"  <s:if test="coRegistration.examRoom.size == 0 ">style="display: none;"</s:if>
												onclick="javascript:deleteNames(1);return false;">X</a> 
											</div>
											<s:hidden name="coRegistration.examroomName"></s:hidden>
											<SELECT name="coRegistration.eroomScreeningWay">
												<option value="0" <s:if test="coRegistration.eroomScreeningWay == 0 ">selected="selected"</s:if>>全部</option>
												<option value="1" <s:if test="coRegistration.eroomScreeningWay == 1 ">selected="selected"</s:if>>通过</option>
												<option value="2" <s:if test="coRegistration.eroomScreeningWay == 2 ">selected="selected"</s:if>>不通过</option>
											</SELECT>
											<span class="txt-info"><a href="#"
												onClick="searchExamRoomUser();return false;">点此进行选择</a> </span>
										</td>
									</tr> 
									<tr>
										<td width="100" align="center" bgcolor="#E6F9F9">
											培训班:
										</td>
										<td><s:property value="coRegistration.elclass.size"/>
											<div id="PXB"
												<s:if test="coRegistration.elclassName == null ">style="display: none;width: 100%;"</s:if>>
											</div>
											<div id="PXB_" <s:if test="coRegistration.elclass.size == 0 ">style="display: none;width: 100%;"</s:if>>
												<s:property value="coRegistration.elclassName" />
												<a  href="" style="color:red"  <s:if test="coRegistration.elclass.size == 0 ">style="display: none;"</s:if>
													onclick="javascript:deleteNames(1);return false;">X</a> 
											</div>
											<s:hidden name="coRegistration.elclassName"></s:hidden>
											<SELECT name="coRegistration.classScreeningWay">
												<option value="0" <s:if test="coRegistration.classScreeningWay == 0 ">selected="selected"</s:if>>全部</option>
												<option value="1" <s:if test="coRegistration.classScreeningWay == 1 ">selected="selected"</s:if>>通过</option>
												<option value="2" <s:if test="coRegistration.classScreeningWay == 2 ">selected="selected"</s:if>>不通过</option>
											</SELECT>
											<span class="txt-info"><a href="#"
												onClick="searchElclassUser();return false;">点此进行选择</a> </span>
										</td>
									</tr>
								</table>
								<s:hidden name="coRegistration.jingzhong" id="jz"></s:hidden>
								<s:hidden name="coRegistration.dishi" id="ds"></s:hidden>
								<s:hidden name="coRegistration.zhiwu" id="zw"></s:hidden>
								<s:hidden name="coRegistration.zhiji" id="zj"></s:hidden>
								<s:hidden name="coRegistration.gangwei" id="gw"></s:hidden>
								<s:hidden name="coRegistration.treeType" id="bm"></s:hidden>
								<s:hidden name="isEroomName" id="isEroomName" value="1"></s:hidden>  
								<s:hidden name="isClassName" id="isClassName" value="1"></s:hidden> 
							</div>
							<div id="divSQ_2" style="display: none;">
							</div>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" bgcolor="#FFFFFF">
							课程状态：
						</td>
						<td bgcolor="#FFFFFF">
							<label>
								<s:property value="course.validName" />
							</label>
							<s:if test="course.status==0||course.status==5">
								<a
									href="course_openInit.action?course.id=<s:property value="course.id"/>"
									style="color: red;" class="textbg">申请开通</a>
							</s:if>
						</td>
					</tr>
					<%
					boolean open_jtm = JTMSystemConfOp.getBooleanValue(ElConstants.SYSTEM_JTM_OPEN_JTM);
					if(open_jtm){
					%>
					 <!-- 维度 -->
					<tr>
						<td height="30" align="center" bgcolor="#FFFFFF">所属知识点：</td>
						<td bgcolor="#FFFFFF">
							<div>
								<table width="95%" cellpadding="2" cellspacing="1" bordercolor="#FFFFFF" bgcolor="#FFFFFF">
									<tr>
										<td bgcolor="#FFFFFF">适应性：
									  <input type="checkbox" name="weidu" value=8 /></td>
										<td bgcolor="#FFFFFF">专业能力：
									  <input type="checkbox" name="weidu" value=15 /></td>
										<td bgcolor="#FFFFFF">决断力：
									  <input type="checkbox" name="weidu" value=22 /></td>
									</tr>
									<tr>
										<td bgcolor="#FFFFFF">学习力：
									  <input type="checkbox" name="weidu" value=9 /></td>
										<td bgcolor="#FFFFFF">沟通力：
									  <input type="checkbox" name="weidu" value=16 /></td>
										<td bgcolor="#FFFFFF">计划与组织：
									  <input type="checkbox" name="weidu" value=23 /></td>
									</tr>
									<tr>
										<td bgcolor="#FFFFFF">创新力：
									  <input type="checkbox" name="weidu" value=10 /></td>
										<td bgcolor="#FFFFFF">团队精神：
									  <input type="checkbox" name="weidu" value=17 /></td>
										<td bgcolor="#FFFFFF">工作压力管理：
									  <input type="checkbox" name="weidu" value=24 /></td>
									</tr>
									<tr>
										<td bgcolor="#FFFFFF">主动与干劲：
									  <input type="checkbox" name="weidu" value=11 /></td>
										<td bgcolor="#FFFFFF">谈判能力：
									  <input type="checkbox" name="weidu" value=18 /></td>
										<td bgcolor="#FFFFFF">市场意识：
									  <input type="checkbox" name="weidu" value=25 /></td>
									</tr>
									<tr>
										<td bgcolor="#FFFFFF">问题解决：
									  <input type="checkbox" name="weidu" value=12 /></td>
										<td bgcolor="#FFFFFF">服务意识：
									  <input type="checkbox" name="weidu" value=19 /></td>
										<td bgcolor="#FFFFFF">变革意识：
									  <input type="checkbox" name="weidu" value=26 /></td>
									</tr>
									<tr>
										<td bgcolor="#FFFFFF">执行力：
									  <input type="checkbox" name="weidu" value=13 /></td>
										<td bgcolor="#FFFFFF">领导力：
									  <input type="checkbox" name="weidu" value=20 /></td>
										<td bgcolor="#FFFFFF">质量意识：
									  <input type="checkbox" name="weidu" value=27 /></td>
									</tr>
									<tr>
										<td bgcolor="#FFFFFF">责任心：
									  <input type="checkbox" name="weidu" value=14 /></td>
										<td bgcolor="#FFFFFF">影响力：
									  <input type="checkbox" name="weidu" value=21 /></td>
										<td bgcolor="#FFFFFF"></td>
									</tr>
							  </table>
							</div>
					  </td>
					</tr>
					<%} %>
					<tr>
						<td width="160" height="50" align="center" bgcolor="#FFFFFF">
							<s:hidden name="course.id"></s:hidden>
						</td>
						<td bgcolor="#FFFFFF">
						 <input class="textbg6" style="border: none;" name="submit" type="submit" value="确认修改" />
							 &nbsp;&nbsp;&nbsp;<a class="textbg4" style="width:80px" href="course_view.action?course.id=${course.id}">查看课程</a>
							  &nbsp;&nbsp;&nbsp;<a class="textbg6" href="course_list.action">返回列表</a>
						</td>
					</tr>
			  </table>
				<script>
			    function applyforAlter(id){  
				   if(confirm('确定申请修改？')){
				      location = "course_applyfor_alterInit.action?course.id="+id; 
				   } 
			    } 
			</script>
			</s:form>
		</div>


		<!-- 内容 -->
	</BODY>
</HTML>
