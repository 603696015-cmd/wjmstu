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
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((         this .         sectionRowIndex %         2 ==
		        0) ?    
		    "#ffffff" :         "#f4f4f4" )
}
</style>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/libutil.js"></script>
		<script type="text/javascript" src="js/erwithoutop.js"></script>
		<script type="text/javascript" src="js/inputcheck.js"></script>
		<script type="text/javascript" src="js/basedataop.js"></script>
		<script type="text/javascript">
		function disDiv(op){
			var div1=document.getElementById("div_1");
			var div2=document.getElementById("div_2");
			if(op==1){
				div1.style.display="none";
				div2.style.display="block";
				//document.getElementById("optional").value="";
			}else{
				div1.style.display="block";
				div2.style.display="none";
			}
		}
		function disDivSQ(op){
			var divSQ1=document.getElementById("divSQ_1");
			//var divSQ2=document.getElementById("divSQ_2");
			if(op==1){
				divSQ1.style.display="none";
				isApplication=1;
				//divSQ2.style.display="block";
				//document.getElementById("optional").value="";
			}else{
				divSQ1.style.display="block";
			
				isApplication=2;
				//divSQ2.style.display="none";
			}
		}
		function init(){
			var div1=document.getElementById("div_1");
			var div2=document.getElementById("div_2"); 
			div1.style="display:block";
			div2.style="display:none";
		}
	</script>
		<script type="text/javascript">
			var isIplink=0;
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
			var isApplication;
			var isAp=<s:property value="(examRoom==null||examRoom.classid<=0)&&(course==null||course.id<=0)" />;
			//**// 引用 basedataop.js 
			function checkTimi(){   
				var KBStart = document.getElementById("eroom_begintime").value; 
				var KBStop  = document.getElementById("eroom_endtime").value; 
				if(KBStart != '' && KBStop != ''){ 
					var checkstart = checkDateTime(KBStart,"开始时间");
					var chenkend = checkDateTime(KBStop,"结束时间");
					if(!checkstart){
					//	alert("开始时间格式错误，请重新输入，格式为:yyyy-MM-dd HH:mm:ss");
						return false;
					}
					if(!chenkend){
					//	alert("结束时间格式错误，请重新输入，格式为:yyyy-MM-dd HH:mm:ss");
						return false;
					}
						var kb = duibi(KBStart,KBStop); 
							if(kb){ 
							}else{
								alert("开始时间不能大于结束时间");					
						}
						
				}else{
					alert("开始与结束时间都不能为空");
					return false;
				}
				
				if(isAp){
					var isApp = document.getElementById("divSQ_1").style.display;;  
					if(isApp != 'none'){ 
						if($("#PlanRecruitStudents").val() == "" ){
							alert("计划招收人数不能为空");
							$("#PlanRecruitStudents").focus();
							return false;
						}
						if($("#PlanRecruitStudents").val() == 0 ){
							alert("计划招收人数不能为零");
							$("#PlanRecruitStudents").focus();
							return false;
						}
						if (!checkNumber($("#PlanRecruitStudents").val())){
							alert("计划招收人数只能为数字");
							$("#PlanRecruitStudents").focus();
							return false;  
						}
						if (!checkNumber($("#StartAge").val()) || !checkNumber($("#StopAge").val())){
							alert("年龄段只能为数字");
							$("#StartAge").focus();
							return false;  
						}
						if($("#StartAge").val() == "" ||  $("#StopAge").val() == ""){
							alert("年龄段开始不能为空");
							$("#StartAge").focus();
							return false;  
						}
						if ($("#StartAge").val() >=  $("#StopAge").val() && !($("#StartAge").val() == 0 && $("#StopAge").val() == 0)){
							alert("年龄段开始不能大于等年龄段结束");
							$("#StartAge").focus();
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
							//alert("报名结束时间不能大于开始时间");
							//return false;
						}
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
				var str=$("#eroom_begintime").val();
				var str2=$("#eroom_endtime").val();
				var date=new Date(Date.parse(str.replace(/-/g,"/")));
				var date2=new Date(Date.parse(str2.replace(/-/g,"/")));
				if(date>date2){
					alert("您设置的考场开始时间大于结束时间，请重新设置!");
					$("#eroom_begintime").focus();
					return false;
				} 
				
				
				
				if(isApplication==2){
					var str3=$("#bm_start").val();
					var str4=$("#bm_end").val();
					var date3=new Date(Date.parse(str3.replace(/-/g,"/")));
					var date4=new Date(Date.parse(str4.replace(/-/g,"/")));
					if($("#bm_start").val()==''){
						alert("报名时间段的开始时间不能为空");
						$("#bm_start").focus();
						return false; 
					}if($("#bm_end").val()==''){
						alert("报名时间段的结束时间不能为空");
						$("#bm_end").focus();
						return false; 
					}
					if(date3>date4){
						alert("您设置的报名开始时间大于结束时间，请重新设置!");
						$("#bm_start").focus();
						return false;
					} 
				
				
				
				
					var BMStart = document.getElementById("bm_start").value; 
					var BMStop  = document.getElementById("bm_end").value; 
					
					
					if(BMStart != '' && BMStop != ''){ 
						var checkbmstart = checkDateTime(BMStart,"报名时间段的开始时间");
						var chenkbmend = checkDateTime(BMStop,"报名时间段的结束时间");
						if(!checkbmstart){
						//	alert("报名时间段的开始时间格式错误，请重新输入，格式为:yyyy-MM-dd HH:mm:ss");
							return false;
						}
						if(!chenkbmend){
						//	alert("报名时间段的结束时间格式错误，请重新输入，格式为:yyyy-MM-dd HH:mm:ss");
							return false;
						}
					}
				
				}
				
				
				if($("#eroom_title").val()==''){
					alert("标题不能为空");
					$("#eroom_title").focus();
					return false; 
				} 
				
				document.getElementById("jz").value=jzidArr.join(",");
				document.getElementById("gw").value=gwidArr.join(",");
				document.getElementById("ds").value=dsidArr.join(",");
				document.getElementById("zw").value=zwidArr.join(",");
				document.getElementById("zj").value=zjidArr.join(",");
				document.getElementById("bm").value=bmidArr.join(","); 
				
				var ty=0;
				/*
				if(document.getElementById("nopaper").value==''){
					alert("试卷不能为空");
					return false;
				}if(isAp){
					ty=$("[name='examRoom\.ertype'][checked]").val();
				}*/
				if(ty==0){
					if(document.getElementById("invigilators").innerHTML ==''){
						alert("监考人员不能为空");
						return false;
					}if(document.getElementById("appraises").innerHTML==''){
						alert("阅卷人员不能为空");
						return false;
					}
				}
				if(!checkTimi()){  
					return false;
				}
				if(!eroomDateTimeCheck()){
					return false;
				}
				//if(document.getElementById("valids").innerHTML==''){
				//	alert("复核人员不能为空");
				//	return false;
				//}
				if(document.getElementById("passgrade").value==''){
					alert("通过的成绩不能为空");
					return false;
				}
				if(!checkFloat($("#passgrade").val())){
					alert("通过的成绩不能为非数字");
					return false;
				}
				if(parseInt($("#passgrade").val())>100||parseInt($("#passgrade").val())<0){
					alert("通过的成绩不能大于100或小于0");
					return false;
				}
				var epidsc = 0;
				var epidsiii=0;
				$("input[type='hidden']").each(function(){
					if(undefined!=$(this).attr("epids")&&$(this).attr("epids")=='1'){
						epidsc++;
						if($(this).val()==''||$(this).val()=='undefined'){
							epidsiii++;
							return false;
						}
					} 
				});
				if(epidsc<=0){
					alert("请添加试卷");
					return false;
				}
				if(epidsiii>0){
					alert("请选择试卷");
					return false;
				}
				/*
				if(document.getElementById("examRoom.score").value==''){
					alert("学分不能为空");
					return false;
				}
				*/
				if($("#eroom_begintime").val()==''){
					alert("开始时间不能为空");
					$("#eroom_begintime").focus();
					return false; 
				}if($("#eroom_endtime").val()==''){
					alert("结束时间不能为空");
					$("#eroom_endtime").focus();
					return false; 
				}
				var ts=/^[\d]{1,3}.[\d]{1,3}.[\d]{1,3}.[\d]{1,3}$/;
				var ipStartArray=document.getElementsByName("ipStart");
				if(isIplink==1){
					if(!ipStartArray[0]){
						alert('请添加ip段');
						return false;
					}
				}
				for(var i=0;i<ipStartArray.length;i++){
					//alert(ipStartArray[i].value);
					var bool=ts.test(ipStartArray[i].value);
					//alert(bool);
					if(bool==false){
						alert("ip:"+ipStartArray[i].value+"有误！！！");
						ipStartArray[i].focus();
						return false;
					}else{
						//可以处理小于255
					}
				}
				var ipEndArray=document.getElementsByName("ipEnd");
				for(var i=0;i<ipEndArray.length;i++){
					//alert(ipStartArray[i].value);
					var bool=ts.test(ipEndArray[i].value);
					//alert(bool);
					if(bool==false){
						alert("ip:"+ipEndArray[i].value+"有误！！！");
						ipEndArray[i].focus();
						return false;
					}else{
						//可以处理小于255
					}
				}
				if($("#epid").val()==''){
					alert("请选择试卷");
					$("#epid").focus();
					return false; 
				}
				return true;
			}
			
			var epcount=0;
			function erep_addinit(){
				var eps =  document.createElement("div");
				eps.id ="eps_"+epcount;
				epsStr = "<span style='color:red'>试卷：</span><span style=\"width:28px;\" id=\"epts"+epcount+"\"></span>分&nbsp;&nbsp;&nbsp;<span style=\"width:150px;\" id=\"eptitle"+epcount+"\"></span>"+
				" <input type=\"hidden\" id=\"epid"+epcount+"\" epids='1' name=\"examPapers["+epcount+"].id\" value=\"\"/>"+
				" <a href=\"javascript:erep_add("+epcount+");\" class='textbg6'>选择试卷</a>&nbsp;&nbsp;达标线：<input type='text' value='60' size=3 name=\"examPapers["+epcount+"].passgrade\"/>%"+
				"&nbsp;&nbsp;可考次数：<input type='text' value='1' size='2' name=\"examPapers["+epcount+"].quizcount\"/>"+
				"<br/>"+
				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
				"<span style='color:red'>成绩取：</span><input name='examPapers["+epcount+"].passmanner' type='radio' value='1'/> 平均分"+
				"<input name='examPapers["+epcount+"].passmanner' type='radio'	value='0' checked='checked'/> 最高分"+
				//"可查看答卷<input type='checkbox' name='examPapers["+epcount+"].stuview' value='1' />"+
				"&nbsp;&nbsp;<span style='color:red'>答卷可看</span><input type='checkbox' name='examPapers["+epcount+"].quizlook' value='1' />"+
				"&nbsp;&nbsp;<span style='color:red'>成绩可看</span><input type='checkbox' name='examPapers["+epcount+"].scorelook' value='1' />"+
				"&nbsp;&nbsp;<a style=\"cursor: pointer; width: 14px; height: 14px;color:red\" class='textbg4' onclick=\"javascript:erep_del(this,"+epcount+" );return false;\">X</a>";
				/*
				"<lable name=\"prac\">"+
				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style='color:red'><p/>练习：</span><span style=\"width:25px;\" id=\"epts_lx_"+epcount+"\"></span>分&nbsp;&nbsp;&nbsp;<span style=\"width: 150px;\" id=\"eptitle_lx_"+epcount+"\"></span>"+
				"<input type=\"hidden\" id=\"epid_lx_"+epcount+"\" name=\"examPapers["+epcount+"].prac.id\" />"+
				"&nbsp;&nbsp;&nbsp;<a href='#' onclick=\"javascript:erep_add('_lx_"+epcount+"');return false;\" class='textbg6'>选择练习</a> "+
				"次数：<input type=\"text\" style=\"width: 40px;\" name=\"examPapers["+epcount+"].practimes\" value=\"0\">"+
				"最低分：<input type=\"text\" style=\"width: 40px;\" name=\"examPapers["+epcount+"].pracscore\" value=\"0\">"+
				"</lable>"+
				"<a style=\"cursor: pointer; width: 14px; height: 14px;color:red\" onclick=\"javascript:erep_del(this,"+epcount+" );return false;\">X</a>" ;
				*/
				eps.innerHTML= epsStr;
				epcount++;	//href=\"\"
				document.getElementById("eps_div").appendChild(eps);		
			}
			function erep_del(obj,id ){
				if(window.confirm("确定将此试卷从考场中删除？")){
				 epcount--
				obj.parentNode.parentNode.removeChild(obj.parentNode);
				}
			}
			function erep_add(id){
				 width=600;
				 height=400;
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv =  window.showModalDialog("assist_survey_epsearchInit.action?"+Math.random(),null,sFeature); 
				 if(null==rv){
				 	alert('您没有选择试卷！');
				 }else{
				 	if(rv[0]<=0){
				 	  	alert('您没有选择试卷！');
			 	  	}
				 	document.getElementById("eptitle"+id).innerHTML=rv[1];
				 	document.getElementById("epid"+id).value=rv[0];
				 	document.getElementById("epts"+id).innerHTML=rv[2];  
				 	document.getElementById("nopaper").value="nopaper";
				 }
			}
			
			
			var ipcount=0;
			function ipd_addinit(){
				//alert("nihao");
				var ipd =  document.createElement("div");
				ipd.id ="ipd_"+ipcount;
				var ipdStr="<div><span width='160' height='30' align='center' bgcolor='#FFFFFF'>开&nbsp;始&nbsp;ip：</span><span bgcolor='#FFFFFF'><input name='ipStart' type='text'></span>"
				+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span width='160' height='30' align='center' bgcolor='#FFFFFF'>结&nbsp;束&nbsp;ip：</span><span bgcolor='#FFFFFF'><input name='ipEnd' type='text'></span>"
				+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='return delDiv("+ipcount+");' href=''>删除</a></div>";
				ipd.innerHTML= ipdStr; 
				ipcount++;
				document.getElementById("ipd_div").appendChild(ipd);
				return false;
			}
			
			function delDiv(ipcount){
				//alert(id);
				var ipObj=document.getElementById("ipd_"+ipcount);
				ipObj.parentNode.removeChild(ipObj);
				return false;
			}
			
			function upIs(va){
				if(va==1){
					isIplink=1;
				}else{
					isIplink=0;
				}
			}
			
			function disecDiv(op){
				if(op==1){
					//$("#examCountDiv").css({"display":"block"});
					$("#examCountDiv").css("display","block");
				}else{
					$("#examCountDiv").css("display","none");
				}
			}
			function eroomDateTimeCheck(){
				var classStarttime="<s:date name="elclass.starttime" format="yyyy-MM-dd-HH-mm-ss"/>";
				var classFinishtime="<s:date name="elclass.finishtime" format="yyyy-MM-dd-HH-mm-ss"/>";
				var eroomStarttime=$("#eroom_begintime").val();
				var eroomFinishtime=$("#eroom_endtime").val();
				return dateTimeCheck(classStarttime,classFinishtime,eroomStarttime,eroomFinishtime);
			}
			function searchUserInit_(){
			     width=600;
				 height=500;
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv =  window.showModalDialog("userRegister.action?isreg=1&x="+Math.random(),null,sFeature);
				 //alert(rv);
				 if(rv!=undefined&&rv!=""){
					 //var bh=rv.split("_");
					 var bh=rv.split("-=wys=-");
					 document.getElementById("danwei").value=bh[0];
					 //document.getElementById("danweiName").innerHTML=bh[1];
					 document.getElementById("danweiName").value=bh[1];
				 }
			}
			function showpwd(i){
				if(i==0){
					$("#pwd_div").css("display","none");
				}else{
					$("#pwd_div").css("display","block");
				}
			}
			
	//在阅卷人员列表中加上阅卷组长
   function searchUserInit2(_id,input_name,comp){
     width=800;
	 height=450;
  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
	 var rv =  window.showModalDialog("mess_sendUserlist.action?sub_department=1&x="+Math.random(),null,sFeature); 
	 if(null==rv){
	 	alert('您没有选择用户！');
	 }else{
	 	if(rv[0]<=0)  	alert('您没有选择用户！');
	 	for(var i = 0 ;i <rv.length ; i++){
	 		if(!checkHasUser(_id,rv[i])){
		 		addUserinfo2(_id,rv[i],input_name);
		 		//var nouser="nouser"+_id;
		 		//document.getElementById(nouser).value=nouser;
	 		}
	 	}
	 	var chkzus=document.getElementsByName("examRoom.appr_header.id");
	 	chkzus[0].checked=true;
	 }
    }

  function addUserinfo2(_id,id,inputname){
	var _d = document.createElement("<span>");
	_d.id = "_d_u"+_id+id;
	_d.style.width="110px";
	_d.style.height="14px";
	_d.style.background="#dddfff";
	_d.style.float="left";
	_d.style.border="solid buttonface 1px";
	/*
	$.post("mess_getZuZhang.action", {
		"elUser.id":id,
		"input_name":inputname, 
		"x":Math.random
		}, 
		function (data) {
			$("#"+_d.id).html(data);
		});
	*/
	$.ajax({
		async:false,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
		type:"post",
		url:"mess_getZuZhang.action",data:{"elUser.id":id,"input_name":inputname,"x":Math.random},
		success:function (data) {
		   $(_d).html(data);
		}
	}); 
	
	document.getElementById(_id).appendChild(_d);
	//alert(document.getElementById(_id).firstChild.innerHTML);  
	
  }
  function checkHasUser(_id,id){
	if( document.getElementById("_d_u"+_id+id)) return true;
	/*var childs = document.getElementById(_id).getElemengtsByTagName("span");
	for(var i = 0 ;i <childs.length;i++){
		if(childs[i].id == "_d_u"+_id+id)
		return true;
	}*/
	return false;

  }








		</script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="填写基本信息" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">添加考场</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="examroomwithoutcourse_list.action">考核考试管理</a>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<!--<label style="font-size: 16px;">
				考核考试管理
			</label>-->
			<br>
			<input type="hidden" id="nopaper" name="nopaper" />
			<input type="hidden" id="nouserinvigilators"
				name="nouserinvigilators" />
			<input type="hidden" id="nouserappraises" name="nouserappraises" />
			<input type="hidden" id="nouservalids" name="nouservalids" />
			<s:form id="examroom_add" name="form_exam_add" method="post"
				theme="simple" action="erwithout_add.action"
				onsubmit="return _onsubmit();">
				<span style="color: #ff0000;"></span>
				<table width="100%" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr>
						<td width="160" align="right">
							<span class="neededitem">*</span>考场标题：
						</td>
						<td>
							&nbsp;<label>
								<input name="examRoom.title" type="text" id="eroom_title"
									value="<s:property value="examRoom.title"/>" size="60" />
							</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<span class="neededitem">*</span>考场类别：
						</td>
						<td bgcolor="#FFFFFF">
							&nbsp;<label>
								<select name="examRoom.eroomLib.id" id="er_erlid">
									<wysLib:eroomLibSelect selectid="${examRoom.eroomLib.id}"></wysLib:eroomLibSelect>
								</select>
							</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							考场说明：
						</td>
						<td>
							&nbsp;<label>
								<textarea name="examRoom.description" cols="60" rows="7"></textarea>
							</label>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#E6F9F9">
							考场图片：
						</td>
						<td>
							&nbsp;<label>
								<s:textfield name="examRoom.mainimg" id="pic" size="60" />
								<a href="javascript:setUrl('pic');" class="textbg4"
									style="width: 80px">浏览资源库</a>
							</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							考试地点：
						</td>
						<td>
							&nbsp;<label>
								<input name="examRoom.location" type="text" id="textfield"
									value="" size="60" />
							</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							&nbsp;<span class="neededitem">*</span>监考人员：
						</td>
						<td>
							<div id="invigilators">
							</div>
							&nbsp;<a href=""
								onclick="searchUserInit('invigilators','examRoom.invigilators.id'); return false;"
								class="textbg4">添加</a>
						</td>
					</tr>
					<tr>
						<td align="right">
							<span class="neededitem">*</span>阅卷人员：
						</td>
						<td>
							<div id="appraises">
							</div>
							&nbsp;<a href="" class="textbg4"
								onclick="searchUserInit2('appraises','examRoom.appraises.id'); return false;">添加</a>
						</td>
					</tr>
					<tr>
						<td align="right">
							复核人员：
						</td>
						<td>
							<div id="valids">
							</div>
							&nbsp;<a href="" class="textbg4"
								onclick="searchUserInit('valids','examRoom.valids.id'); return false;">添加</a>
						</td>
					</tr>
					<tr>
						<td align="right">
							<span class="neededitem">*</span>考试时间：
						</td>
						<td>
							<label>
								考场开始时间
								<input style="height: 30px;" class="Wdate"
									name="examRoom.begintime" type="text" id="eroom_begintime" />
								&nbsp;
							
								
								<input type="button" class="textbg4"
									onclick="setday(document.getElementById('eroom_begintime'))"
									value="选择" />
									
									<s:if test="examRoom.classid>0">
									<font color="red">(培训班开始时间：<s:date
											name="elclass.starttime" format="yyyy-MM-dd HH:mm:ss" />)</font>
								</s:if>
							</label>
							<br />
							<label>
								考场结束时间
								<input style="height: 30px;" class="Wdate"
									name="examRoom.endtime" id="eroom_endtime" type="text" />
								&nbsp;
								<input type="button" class="textbg4"
									onclick="setday(document.getElementById('eroom_endtime'))"
									value="选择" />
								<s:if test="examRoom.classid>0">
									<font color="red">(培训班结束时间：<s:date
											name="elclass.finishtime" format="yyyy-MM-dd HH:mm:ss" />)</font>
								</s:if>
							</label>
						</td>
					</tr>

					<tr>
						<td height="30" align="right">
							绑定mac地址：
						</td>
						<td>
							<input name="examRoom.isMacBand" type="radio" value="1">
							是
							<input name="examRoom.isMacBand" type="radio" value="0"
								checked="checked">
							否
						</td>
					</tr>
					<tr>
						<td height="30" align="right">
							限&nbsp;定&nbsp;ip&nbsp;段：
						</td>
						<td>
							<input onClick="upIs(1);" name="examRoom.isIpLimit" type="radio"
								value="1">
							是
							<input onClick="upIs(0);" name="examRoom.isIpLimit" type="radio"
								value="0" checked="checked">
							否
						</td>
					</tr>

					<tr>
						<td colspan="2">
							<div id="ipd_div"></div>
							<a href="" onClick="return ipd_addinit();" class="textbg4"
								style="width: 80px;">添加ip段</a>
						</td>
					</tr>
					<%-- 
					<tr>
						<td width="100" align="center" >
							考场类型：
						</td>
						<td >
							<label>
								分配式
								<input type="radio" checked="checked" name="examRoom.type"
									value="0"
									onclick="document.getElementById('div_link').style.display='none';">
								选拔式
								<input type="radio" name="examRoom.type" value="1"
									onclick="document.getElementById('div_link').style.display='block';">
								<!-- 	可申请
								<input type="radio" name="examRoom.type" value="2"
								onclick="document.getElementById('div_link').style.display='none';"> 
								 --> 
							</label>
							<div id="div_link" style="display: none">
								选拨人员:
								<div id="selectings">
								</div>
								<a href=""
									onclick="searchUserInit('selectings','examRoom.selectings.id'); return false;">添加</a>
							</div>
						</td>
					</tr>
					
					 <s:if test="(examRoom==null||examRoom.classid<=0)&&(course==null||course.id<=0)">
						 <tr>
						 	<td width="100" align="center" >
								考场类型：
							</td>
							<td >
								<input name="examRoom.ertype" type="radio"
									value="0" checked="checked" onclick="disecDiv('0');" />
								正式考场
								<input name="examRoom.ertype" type="radio"
									value="1" onclick="disecDiv('1');" />
								练习考场
								<div style="display:none;" id="examCountDiv">
									可考次数：
									<label>
										<input type="text" style="width: 40px;"
											name="examRoom.examcount" id="passgrade" value="1">
									</label>
								</div>
							</td>
						 </tr>
					 </s:if>
					  --%>
					<tr>
						<td align="right">
							<span class="neededitem">*</span>通过成绩：
						</td>
						<td>
							&nbsp;<label>
								<input type="text" style="width: 40px;"
									name="examRoom.passgrade" id="passgrade" value="60">
								%
							</label>
						</td>
					</tr>
					<%--<tr>
						<td width="100" align="center" >
							计分方式：
						</td>
						<td >
							<input name="examRoom.passmanner" type="radio"
								value="1">
							平均分
							<input name="examRoom.passmanner" type="radio"
								value="0" checked="checked">
							最高分
						</td>
					</tr>
					-->
					<%-- 
					<tr>
						<td width="160" height="30" align="center" >
							阅卷方式：
						</td>
						<td >
							<input name="examRoom.markingManner" type="radio"
								value="0" checked="checked">
							系统自动阅卷
							<input name="examRoom.markingManner" type="radio"
								value="1">
							手工阅卷
						</td>
					</tr>
					 --%>
					<!--<tr>
						<td width="100"  align="center" >
							学分
						</td>
						<td >
							<label>
								<input type="text" style="width: 40px;" value="2" id="examRoom.score"
									name="examRoom.score">
							</label>
						</td>
					</tr>-->
					<tr>
						<td align="right">
							<span class="neededitem">*</span>设定密码：
						</td>
						<td>
							<input type="radio" onClick="showpwd(0)" name="examRoom.pwdneed"
								checked="checked" value="0" />
							不需要
							<input type="radio" onClick="showpwd(1)" name="examRoom.pwdneed"
								value="1" />
							需要
							<div id="pwd_div" style="display: none;">
								密码:
								<input type="text" name="examRoom.pwd" />
								&nbsp;有效期至:
								<input type="text" name="examRoom.pwdtime"
									onclick="setday(this)" />
							</div>
							<div>
								试卷缓存:
								<input type="radio" name="examRoom.cacheepsize" value="1" />
								是
								<input checked="checked" type="radio"
									name="examRoom.cacheepsize" value="-1" />
								否&nbsp;&nbsp;
								<input type="checkbox" value="1" name="examRoom.cacheeprefresh" />
								密码改变时刷新缓存&nbsp;&nbsp;
								<input type="checkbox" value="1" name="examRoom.epqsort" />
								试卷中试题随机排序
							</div>
						</td>
					</tr>
					<tr>
						<td align="right">
							<span class="neededitem">*</span>所用试卷：
						</td>
						<td>
							<div id="eps_div"></div>
							<a href="" onClick="erep_addinit(); return false;" class="textbg5">添加试卷</a>
							<!--(<span style="color:red">注意：只有考场类型是选拔式的，练习添加才有效,其他情况不需设置练习部分</span>)-->
						</td>
					</tr>
					<tr>
						<td align="right">
							<span class="neededitem">*</span>自动分配：
						</td>
						<td>
							<input type="radio" name="examRoom.autoAssign" value=1  
									 checked="checked" />
								是
							<input type="radio" name="examRoom.autoAssign" value=0 
								/>
								否
						</td>
					</tr>
					<tr>
						<td align="right">
							组织单位：
						</td>
						<td>
							<label>
								<input id="danweiName" name="examRoom.depName"
									readonly="readonly" style="font-size: 15px;"
									value="<s:property value="#session.myDepName" />" />
								<span class="txt-info" style="margin-left: 20px;"><a
									href="#" onClick="searchUserInit_();return false;"
									class="textbg4">选择</a> </span>
							</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							组织工种：
						</td>
						<td>
							<label>
								<s:select name="examRoom.jingzhong" list="jingzhongs"
									listKey="basevalue" listValue="basevalue"
									value="#request.userJingzhong" />
							</label>
						</td>
					</tr>
					
					<s:if	test="(examRoom==null||examRoom.classid<=0)&&(course==null||course.id<=0)">
					
						<tr>
							<td height="30" align="right" bgcolor="#E6F9F9">
								<span class="neededitem">*</span>是否可申请：
							</td>
							<td height="30">
								<input type="radio" name="examRoom.isApplication" value="0"  
									onClick="disDivSQ(1);" checked="checked" />
								不可申请
								<input type="radio" name="examRoom.isApplication" value="1" 
									onClick="disDivSQ(2);" />
								可申请
								<input type="radio" name="examRoom.isApplication" value="2" 
									onClick="disDivSQ(1);" />
								全员
								<font style="color: red;">此选项创建后不可修改</font>
								<div id="divSQ_1" style="display: none;">
									<table width="100%" cellpadding="2" cellspacing="1">
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
											<td colspan="2">
												是否需要审核:
												<input type="radio" value="1" name="erRegistration.isAudit" />
												需要
												<input type="radio" value="0" checked="checked"
													name="erRegistration.isAudit" />
												不需要
											</td>
										</tr>
										<tr>
											<td colspan="2">
												是否自主选择试卷:
												<input type="radio" value="1" name="erRegistration.isselectep" />
												是
												<input type="radio" value="0" checked="checked"
													name="erRegistration.isselectep" />
												否
											</td>
										</tr>
										<tr>
											<td align="right" bgcolor="#E6F9F9">
												<span class="neededitem">*</span>计划招收学员：
											</td>
											<td>
												<s:textfield name="erRegistration.PlanRecruitStudents"
													id="PlanRecruitStudents" />
											</td>
										</tr>
										<tr>
											<td colspan="2">
												<span style="color: red">申请条件</span>
											</td>
										</tr>
										<tr>
											<td align="right" bgcolor="#E6F9F9">
												<span class="neededitem">*</span>报名时间段：
											</td>
											<td>
												<input name="erRegistration.RegistrationStartTime"
													id="bm_start" type="text" size="20" 
													value="<s:property value='erRegistration.RegistrationStartTime'/>">
													&nbsp;
													<input type="button" class="textbg4"
													onclick="setday(document.getElementById('erRegistration.RegistrationStartTime'))"
													value="选择" />
												～
												<input name="erRegistration.RegistrationStopTime"
													id="bm_end" type="text" size="20" 
													value="<s:property value='erRegistration.RegistrationStopTime'/>">
													
													&nbsp;
													<input type="button" class="textbg4"
													onclick="setday(document.getElementById('erRegistration.RegistrationStopTime'))"
													value="选择" />
											</td>
										</tr>
										<tr>
											<td align="right" bgcolor="#E6F9F9">
												年龄段：
											</td>
											<td>
												<s:textfield name="erRegistration.StartAge" id="StartAge"
													value="0" />
												～
												<s:textfield name="erRegistration.StopAge" id="StopAge"
													value="0" />
											</td>
										</tr>
										<tr>
											<td align="right" bgcolor="#E6F9F9">
												性别：
											</td>
											<td>
												<label>
													<input type="radio" name="erRegistration.sex" value="不限"
														checked="checked" />
													不限
													<input type="radio" name="erRegistration.sex" value="男" />
													男
													<input type="radio" name="erRegistration.sex" value="女" />
													女
												</label>
											</td>
										</tr>
										<tr>
											<td align="right" bgcolor="#E6F9F9">
												部门：
											</td>
											<td>
												<div id="danwei"
													style="background-color: blank; display: none;">
													<br />
												</div>

												<span class="txt-info"><a href="#"
													onClick="searchUsersInit();return false;" class="textbg4">选择</a> </span>
											</td>
										</tr>
										<tr>
											<td align="right" bgcolor="#E6F9F9">
												<wysLib:BasetName btid="1" />
												：
											</td>
											<td>
												<div id="jztj"
													style="background-color: blank; display: none;">
												</div>
												<span class="txt-info"><a href="#"
													onClick="searBaseDatatInit(1);return false;" class="textbg4">选择</a> </span>
											</td>
										</tr>
										<tr>
											<td align="right" bgcolor="#E6F9F9">
												<wysLib:BasetName btid="5" />
												：
											</td>
											<td>
												<div id="dstj"
													style="background-color: blank; display: none;">
												</div>
												<span class="txt-info"><a href="#"
													onClick="searBaseDatatInit(5);return false;" class="textbg4">选择</a> </span>
											</td>
										</tr>
										<tr>
											<td align="right" bgcolor="#E6F9F9">
												<wysLib:BasetName btid="2" />
												：
											</td>
											<td>
												<div id="zwtj"
													style="background-color: blank; display: none;">
													<br />
												</div>
												<span class="txt-info"><a href="#"
													onClick="searBaseDatatInit(2);return false;" class="textbg4">选择</a> </span>
											</td>
										</tr>
										<tr>
											<td align="right" bgcolor="#E6F9F9">
												<wysLib:BasetName btid="3" />
												：
											</td>
											<td>
												<div id="zjtj"
													style="background-color: blank; display: none;">
													<br />
												</div>
												<span class="txt-info"><a href="#"
													onClick="searBaseDatatInit(3);return false;" class="textbg4">选择</a> </span>
											</td>
										</tr>
										<%-- 
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
								 --%>
										<tr>
											<td align="right" bgcolor="#E6F9F9">
												考场：
											</td>
											<td>
												<div id="KC" style="display: none; width: 100%;">
												</div>
												<!-- <SELECT name="erRegistration.eroomScreeningWay">
													<option value="0">
														全部
													</option>
													<option value="1">
														通过
													</option>
													<option value="2">
														不通过
													</option>
												</SELECT> -->
												<span class="txt-info"><a href="#"
													onClick="searchExamRoomUser();return false;" class="textbg4">选择</a> </span>
											</td>
										</tr>
										<tr>
											<td width="100" align="right" bgcolor="#E6F9F9">
													考场试卷：
											</td>
											<td> 
												<div id="KCEP"> 
												</div>
												<span class="txt-info"><a href="#"
													onClick="searchExamRoomEpUser();return false;" class="textbg4">选择</a> </span>
											</td>
										</tr>
										<tr>
											<td align="right" bgcolor="#E6F9F9">
												培训班：
											</td>
											<td>
												<div id="PXB" style="display: none; width: 100%;">
												</div>
												<!-- <SELECT name="erRegistration.classScreeningWay">
													<option value="0">
														全部
													</option>
													<option value="1">
														通过
													</option>
													<option value="2">
														不通过
													</option>
												</SELECT> -->
												<span class="txt-info"><a href="#"
													onClick="searchElclassUser();return false;" class="textbg4">选择</a> </span>
											</td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
					</s:if>
					<tr>
						<td align="center">
							&nbsp;
							<!-- 	<s:hidden name="course.id" value="-1"></s:hidden>	 -->
							<s:if test="course==null">
								<s:hidden name="course.id" value="-1" />
							</s:if>
							<s:else>
								<s:hidden name="course.id" />
							</s:else>
							<s:hidden name="examRoom.iscommon" value="1"></s:hidden>
							<s:hidden name="course.classid"></s:hidden>
							<s:if test="Return != null">
								<s:hidden name="Return"></s:hidden>
							</s:if>
						</td>
						<td>
							<input type="submit" name="button2"
								style="border: none; color: red" class="textbg5" id="button2"
								value="确认添加" />
							<s:if test="course.classid==0">
								<input class="textbg5" style="border: none;" type="button"
									value="考场列表"
									onclick="document.location='examroom_alllist.action'" />
							</s:if>
							<s:else>
								<input class="textbg" style="border: none;" type="button"
									value="班级课程列表"
									onclick="document.location='elclass_course.action?elclass.id=${elclass.id }'" />
								<input class="textbg" style="border: none;" type="button"
									value="返回绑定列表"
									onclick="document.location='examroom_choose_listbycInit.action?course.id=${course.id }&classId=${elclass.id }&Return=elclass_alterInit'" />
							</s:else>
						</td>
					</tr>
				</table>
		    <s:hidden name="erRegistration.jingzhong" id="jz"></s:hidden>
				<s:hidden name="erRegistration.dishi" id="ds"></s:hidden>
				<s:hidden name="erRegistration.zhiwu" id="zw"></s:hidden>
				<s:hidden name="erRegistration.zhiji" id="zj"></s:hidden>
				<s:hidden name="erRegistration.gangwei" id="gw"></s:hidden>
				<s:hidden name="erRegistration.treeType" id="bm"></s:hidden>
			</s:form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
