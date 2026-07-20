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
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>课程类别管理</title>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script> 
		<script type="text/javascript" src="js/ElclassEroomConditions.js"></script> 
		<script type="text/javascript">  
			var bmarr = new Array();  
			var bmidArr = new Array();
			var jzArr = new Array();
			var jzidArr = new Array();
			var zwArr = new Array();
			var zwidArr = new Array();
			var zjArr = new Array();
			var zjidArr = new Array();
			var gwArr = new Array();
			var gwidArr = new Array();
			var dsArr = new Array();
			var dsidArr = new Array();
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
				 if(j == 1){//1  工 种   
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
			function checkTimi(){    
				var isApp = document.getElementById("divSQ_1").style.display;;  
				if(isApp != 'none'){ 
					var plan = document.getElementById("PlanRecruitStudents").value;  
					if(plan == ''){
						alert("计划招收人数不能为空")
						return false;	 
					} 
					var BMStart = document.getElementById("bm_start").value; 
					var BMStop = document.getElementById("bm_end").value;
					if(BMStart != '' && BMStop != ''){ 
						var kb = duibi(BMStart,BMStop); 
						if(kb){ 
						}else{
							alert("报名开始时间不能大于结束时间");
							return false;					
						}
					}else{
						alert("报名时间不能为空");
						return false;
					}   
					var bmkb = duibi(BMStop,KBStart);  
					if(bmkb){ 
					}else{
						alert("报名结束时间不能大于开始时间");
						return false;
					}
				} 
				return true;
				
			}
			
			function duibi(a,b)
			{
				var arr=a.split("-");
				var starttime=new Date(arr[0],arr[1],arr[2]);
				var starttimes=starttime.getTime();
				
				var arrs=b.split("-");
				var lktime=new Date(arrs[0],arrs[1],arrs[2]);
				var lktimes=lktime.getTime();
				
				if(starttimes>=lktimes)
				{  
					return false;
				}
				else
					return true; 
			} 
			function _onsubmit(){  
				document.getElementById("jz").value=jzidArr.join(","); 
				document.getElementById("gw").value=gwidArr.join(","); 
				document.getElementById("ds").value=dsidArr.join(","); 
				document.getElementById("zw").value=zwidArr.join(","); 
				document.getElementById("zj").value=zjidArr.join(","); 
				document.getElementById("bm").value=bmidArr.join(",");  
				if($("#cname").val()==''){
					alert("请填写课程名称");
					$("#cname").focus();
					return false;
				}
				if($.trim($("#scormfile").val())==''){
					alert("scorm课件不要为空");
					$("#scormfile").focus();
					return false;
				}
				/*
				if(document.getElementById("islink_4").checked){
					if($("#r_start").val()==''){
						alert("请填写课程开始时间");
						$("#r_start").focus();
						return false;
					}
					if($("#r_end").val()==''){
						alert("请填写课程结束时间");
						$("#r_end").focus();
						return false;
					}
				}
				*/
				var qlibId=$("input[name='course.ctype.id']:checked").val();
				if(qlibId==undefined){
					alert("请选择课程类别！");
					return false;
				}
				var ts=/^[\d]{0,}$/;
				var str="";
				var checkValue=$("[name='course\.islink'][checked]").val();
				if(checkValue==3||checkValue==1){
					if($("#cDuring").val()<0&&$("#cDuring").val()!=''){
						alert("课程学习时间不能小于0！");
						return false;
					}
					if(!ts.test($("#cDuring").val())){
						alert("学习时间请输入数字！");
						return false;
					}
					if($("#cDuring").val()==0){
						str="课程学习时间为0，";
					}
					if($("#cDuring").val()==''){
						str="课程学习时间没填，";
					}
					if(checkValue==1&&$("#exurl").val()==''){
						alert("请填写外部课程链接地址！");
						return false;
					}
					if(checkValue==3&&$("#exurl").val()==''){
						alert("请填写视频地址！");
						return false;
					}
				}
				return window.confirm(str+"确定信息填写无误？");
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
				 	else
				 	$.post("mess_getUserInfoJson.action", {
						"elUser.id":rv[0],
						"x":Math.random
						}, 
						function (data) {
							var dataObj=eval("("+data+")");
							document.getElementById("t_id").value=dataObj.elUser.id;
							document.getElementById("t_name").value=dataObj.elUser.realname;
							document.getElementById("t_hname").value=dataObj.elUser.realname;
						}); 
				 }
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
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="填写课程基本信息" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">创建课程</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div>
			<s:form action="course_scormadd" method="post" name="course_info"
				theme="simple" id="course_info" onsubmit="return _onsubmit();" enctype="multipart/form-data">
				<!--<div>
					<B>注意：</B> 1、填写课程名称、选择课程类别；
					<br />
					2、课程类型 选定后将不可以再修改；
					<br />
					3、课程创建后将是关闭状态，待课件制作完程后，请在课件修改的地方开通它；
					<br />
					4、课件开通后将提交给管理员审核。通过后方可使用。
				</div>
				-->
				<table cellpadding="1" cellspacing="1" bgcolor="#ECEDEB" width="700">
					<tr>
						<td width="100" height="30" align="right" >
							<span class="neededitem">*</span>课程名称：
						</td>
						<td >
							<label>
								<input name="course.name" type="text" id="cname" value=""
									size="60">
							</label>
						</td>
					</tr>
					<tr>
						<td width="100" height="30" align="right" >
							课程介绍：
						</td>
						<td >
							<label>
								<textarea name="course.description" cols="60" rows="7"></textarea>
							</label>
						</td>
					</tr>
					<tr>
						<td width="100" height="30" align="right" >
							讲师姓名：
						</td>
						<td >
							<label> 
								<s:textfield id="t_name" size="40" disabled="true"  />
								<s:hidden name="course.teacherName" id="t_hName"/>
								<s:hidden name="course.teacherId" value="0" id="t_id"/><input class="textbg6" type="button" onClick="searchUserInit('messUser')" value="查 找">
						
							</label>
						</td>
					</tr>
					<tr>
						<td width="100" height="30" align="right" >
							师资介绍：
						</td>
						<td >
							<label>
								<s:textarea name="course.teacherinfo" cols="60" rows="7"></s:textarea>
							</label>
						</td>
					</tr>
					<!--<tr>
						<td width="100" height="30" align="right" >
							学习计划：
						</td>
						<td >
							<label>
								<s:textarea name="course.studyplan" cols="60" rows="7"></s:textarea>
							</label>
						</td>
					</tr>
					-->
					<tr>
						<td width="100" height="30" align="right" >
							课程图片：
						</td>
						<td >
							<label>
								<input name="course.mainimg" type="text" id="pic" size="60" />
								<a href="javascript:setUrl('pic');" class="textbg">浏览资源库</a>
							</label>
						</td>
					</tr>

					<tr>
						<td width="100" height="30" align="right" >
							<span class="neededitem">*</span>所属类别：
						</td>
						<td >
							<label>
								<wysLib:ctypeTree did="1" rootAble="false"
									iname="course.ctype.id" itype="ra_2no" ></wysLib:ctypeTree>
							</label>
						</td>
					</tr>

					<tr>
						<td width="100" height="30" align="right" >
							推荐学分：
						</td>
						<td >
							<label>
								<s:textfield name="course.credit" value="0" id="course.credit"></s:textfield>
							</label>
						</td>
					</tr>
					<!--<tr>
						<td width="100" height="30" align="right" >
							通过成绩：
						</td>
						<td >
							<label>
								<input type="text" style="width: 40px;" name="course.passgrade"
									id="passgrade" value="">
								%
							</label>
						</td>
					</tr>
					-->
					<tr>
						<td align="right" bgcolor="#FFFFFF" style="color: black">
							<span class="neededitem">*</span>课程类型：
						</td>
						
						<td >
							<!-- <label>
								<input type="radio" name="optype" value="0" onclick="setOptype(0)" checked="checked"/>本地上传
								<input type="radio" name="optype" value="1" onclick="setOptype(1)" />浏览资源库
							</label>
							<script type="text/javascript">
								function setOptype(i){
									if(i==0){
										$("#div_link_zy").css("display","none");
										$("#div_link_bd").css("display","block");
									}
									if(i==1){
										$("#div_link_zy").css("display","block");
										$("#div_link_bd").css("display","none");
									}
								}
							</script> -->
							<input type="hidden" name="course.islink" value="5"/>
							<div id="div_link_bd">
								<label>
									<span id="url_desc">上传课件</span>
									<input type="file" name="scormfile" id="scormfile" size="40"/>
								</label>
							</div>
							<!-- <div id="div_link_zy">
								<label>
									<span id="url_desc">浏览资源</span>
									<input type="text" name="scormfile" size="40"/>
								</label>
							</div> -->
							
						</td>
					</tr>
					<tr>	
						<td align="right" bgcolor="#FFFFFF" style="color: black">
							<span class="neededitem">*</span>课程时间：
						</td>
						<td>
							<div id="div_link_tb">
								<label>
									开始时间：
									<input name="course.roomstart" id="r_start" type="text"
										size="20" onClick="setday(this)">
									结束时间：
									<input name="course.roomend" id="r_end" type="text"
										size="20" onClick="setday(this)">
								</label>
							</div>
							<div id="div_link_xx">
								学习时间：
								<input name="course.during" id="cDuring" type="text" size="5" value="0">
								分钟 学习询问时间：
								<input name="course.querytime" type="text"
									value="0" size="5">
								分钟
							</div>
						</td>
					</tr>
					<!--<tr>
						<td align="right">课程格式:</td>
						<td height="30" align="left" >
							<label>
								<select name="course.courseForm">
									<s:iterator value="course.courseForms" status="jzs">
										<option value="<s:property value="#jzs.index"/>">
											<s:property value="course.courseForms[#jzs.index]"/>
										</option>
									</s:iterator>
								</select> 
							</label> 
						</td>
					</tr>
					 tr>
						<td align="right" bgcolor="#FFFFFF" style="color: black">
							获得学分方式：
						</td>
						<td >
							<label>
								学习完获得
							</label>
							<input type="radio" checked="checked" name="course.creditmod"
								value="0">
							&nbsp;&nbsp;
							<label>
								进度x学分
							</label>
							<input type="radio" name="course.creditmod" value="1">
						</td>
					</tr-->
					<!-- tr>
						<td align="center" >
							标准笔记字数：
						</td>
						<td >
							<input type="text" size="4" name="course.notenumber" >
						笔记提交时间：	<input type="text" size="16" name="course.notedate" onclick="setday(this)" >
						</td>
					</tr-->
					<%-- 
					<tr>
						<td height="30" align="center" bgcolor="#E6F9F9">
							是否可申请：
						</td>
						<td height="30" >
							<input type="radio" name="course.isApplication" value="0"
								onClick="disDivSQ(1);" checked="checked" />
							不可申请
							<input type="radio" name="course.isApplication" value="1"
								onClick="disDivSQ(2);" />
							可申请
							<div id="divSQ_1" style="display: none;">
								<table width="95%" cellpadding="2" cellspacing="1"
								>
								<!-- <tr>
									<td  width="100" height="20" align="center" bgcolor="#E6F9F9">复核人员:</td>
									<td> 
										<div id="valids">
										<a href=""
											onclick="searchUserInit('valids','elclass.valids.id'); return false;" class=textbg4>添加</a>
										</div>
									</td>
								</tr> -->
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
											id="bm_start" type="text" size="20" onClick="setday(this)"
											value="<s:property value='coRegistration.RegistrationStartTime'/>">
										～
										<input name="coRegistration.RegistrationStopTime" id="bm_end"
											type="text" size="20" onClick="setday(this)"
											value="<s:property value='coRegistration.RegistrationStopTime'/>">
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
											style="background-color: blank; display: none;"><br />
										</div>
										
										<span class="txt-info"><a href="#"
											onClick="searchUsersInit();return false;">点此进行选择</a> </span>
									</td>
								</tr>
								<tr>
									<td width="100" align="center" bgcolor="#E6F9F9">
										<wysLib:BasetName btid="1" />:
									</td>
									<td>
										<div id="jztj" style="background-color: blank; display: none;"> 
										</div>
										<span class="txt-info"><a href="#"
											onClick="searBaseDatatInit(1);return false;">点此进行选择</a> </span>
									</td>
								</tr>
								<tr>
									<td width="100" align="center" bgcolor="#E6F9F9">
										<wysLib:BasetName btid="5" />:
									</td>
									<td>
										<div id="dstj" style="background-color: blank; display: none;"> 
										</div>
										<span class="txt-info"><a href="#"
											onClick="searBaseDatatInit(5);return false;">点此进行选择</a> </span>
									</td>
								</tr>
								<tr>
									<td width="100" align="center" bgcolor="#E6F9F9">
										<wysLib:BasetName btid="2" />:
									</td>
									<td>
										<div id="zwtj" style="background-color: blank; display: none;"><br />
										</div>
										<span class="txt-info"><a href="#"
											onClick="searBaseDatatInit(2);return false;">点此进行选择</a> </span>
									</td>
								</tr>
								<tr>
									<td width="100" align="center" bgcolor="#E6F9F9">
										<wysLib:BasetName btid="3" />:
									</td>
									<td>
										<div id="zjtj" style="background-color: blank; display: none;"><br />
										</div>
										<span class="txt-info"><a href="#"
											onClick="searBaseDatatInit(3);return false;">点此进行选择</a> </span>
									</td>
								</tr>
								
								<tr>
									<td width="100" align="center" bgcolor="#E6F9F9">
										<wysLib:BasetName btid="4" />:
									</td>
									<td>
										<div id="gwtj" style="background-color: blank; display: none;"><br />
										</div>
										<span class="txt-info"><a href="#"
											onClick="searBaseDatatInit(4);return false;">点此进行选择</a> </span>
									</td> 
								</tr>
								
								<tr>
									<td width="100" align="center" bgcolor="#E6F9F9">
										考场:
									</td>
									<td> 
										<div id="KC" style="display: none;width: 100%;">
										</div>
										<SELECT name="coRegistration.eroomScreeningWay">
											<option value="0">全部</option>
											<option value="1">通过</option>
											<option value="2">不通过</option>
										</SELECT>
										<span class="txt-info"><a href="#"
											onClick="searchExamRoomUser();return false;">点此进行选择</a> </span>
									</td>
								</tr>
								<tr>
									<td width="100" align="center" bgcolor="#E6F9F9">
										培训班:
									</td>
									<td>  
										<div id="PXB" style="display: none;width: 100%;">
										</div>
										<SELECT name="coRegistration.classScreeningWay">
											<option value="0">全部</option>
											<option value="1">通过</option>
											<option value="2">不通过</option>
										</SELECT>
										<span class="txt-info"><a href="#"
											onClick="searchElclassUser();return false;">点此进行选择</a> </span> 
									</td>
								</tr>
							</table> 
							</div>
						</td>
					</tr>
					 --%>
					<tr> 
						<td width="100" height="50" align="center" >
						</td>
						<td >
							<input type="hidden" name="course.status" value="0">
							<input class="textbg6" name="submit" type="submit" value="确认添加" /> 
						</td>
					</tr>
				</table> 
			<s:hidden name="coRegistration.jingzhong" id="jz"></s:hidden>
			<s:hidden name="coRegistration.dishi" id="ds"></s:hidden>
			<s:hidden name="coRegistration.zhiwu" id="zw"></s:hidden>
			<s:hidden name="coRegistration.zhiji" id="zj"></s:hidden>
			<s:hidden name="coRegistration.gangwei" id="gw"></s:hidden>
			<s:hidden name="coRegistration.treeType" id="bm"></s:hidden>
			</s:form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
