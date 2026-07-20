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
		<base href="<%=basePath%>"/>
		<META http-equiv=Page-Enter  
			content=RevealTrans(Duration=0.5,Transition=14)/>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/assist.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/libutil.js"></script>
		<script type="text/javascript" src="js/erwithoutop.js"></script>
		<script type="text/javascript" src="js/inputcheck.js"></script>
		<script type="text/javascript" src="js/basedataop.js"></script>
		<script type="text/javascript">
		var isIplink=<s:property value="examRoom.isIpLimit"/>;
			var bmarr = new Array();
			var bmidArr = new Array();
			var initBM = '<s:property value="erRegistration.treeType"/>';
			if(initBM){
			  bmidArr = initBM.split(",");
			}
			var jzArr = new Array();
			var jzidArr = new Array();
			var initJZ = '<s:property value="erRegistration.jingzhong"/>';
			if(initJZ){
			  jzidArr = initJZ.split(",");
			}
			var zwArr = new Array();
			var zwidArr = new Array(); 
			var initZW = '<s:property value="erRegistration.zhiwu"/>';
			if(initZW){
			  zwidArr = initZW.split(",");
			}
			var zjArr = new Array();
			var zjidArr = new Array();
			var initZJ = '<s:property value="erRegistration.zhiji"/>';
			if(initZJ){
			  zjidArr = initZJ.split(",");
			}
			var gwArr = new Array();
			var gwidArr = new Array();
			var initGW = '<s:property value="erRegistration.gangwei"/>';
			if(initGW){
			  gwidArr = initGW.split(",");
			}
			var dsArr = new Array();
			var dsidArr = new Array();
			var initDS = '<s:property value="erRegistration.dishi"/>';
			if(initDS){
			  dsidArr = initDS.split(",");
			}
			var typeid;
			//**//
		function deleteUserinfo(obj,id,optype){
			if(window.confirm("确定删除？")){
			erid = <s:property value="examRoom.id"/> ;
			$.post("eroom_delete_user.action", {
				"elUser.id":id,
				"examRoom.id":erid,
				"optype":optype, 
				"x":Math.random
				}, 
				function (data) {
					alert('删除成功');
				});
			obj.parentNode.parentNode.removeChild(obj.parentNode);
			}
		}
		var epcount=parseInt("<s:property value="examRoom.exampapers.size" />");
				function erep_addinit(){
				var eps =  document.createElement("div");
				eps.id ="eps_"+epcount;
				epsStr = "<span style='color:red'>试卷：</span><span style=\"width:28px;display:none;\" id=\"epts"+epcount+"\"></span>&nbsp;&nbsp;&nbsp;<span style=\"width:150px;\" id=\"eptitle"+epcount+"\"></span>"+
				" <input type=\"hidden\" id=\"epid"+epcount+"\" epids='1' name=\"examPapers["+epcount+"].id\" value=\"\"/>"+
				" <a href=\"javascript:erep_add("+epcount+");\" class='textbg6'>选择试卷</a>&nbsp;&nbsp;<input type='hidden' value='60' size=3 name=\"examPapers["+epcount+"].passgrade\"/>"+
				"&nbsp;&nbsp;<input type='hidden' value='1' size='2' name=\"examPapers["+epcount+"].quizcount\"/>"+
				"<br/>"+
				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
				"<span style='color:red;display:none;'>成绩取：</span><input name='examPapers["+epcount+"].passmanner' type='hidden' value='1'/> "+
				"<input name='examPapers["+epcount+"].passmanner' type='hidden'	value='0' checked='checked'/> "+
				//"可查看答卷<input type='checkbox' name='examPapers["+epcount+"].stuview' value='1' />"+
				"&nbsp;&nbsp;<span style='color:red;display:none;'>答卷可看</span><input type='hidden' name='examPapers["+epcount+"].quizlook' value='1' />"+
				"&nbsp;&nbsp;<span style='color:red;display:none;'>成绩可看</span><input type='hidden' name='examPapers["+epcount+"].scorelook' value='1' />"+
				"&nbsp;&nbsp;<a style=\"cursor: pointer; width: 14px; height: 14px;color:red\" class='textbg4' onclick=\"javascript:erep_del(this,"+epcount+" );return false;\">X</a>";
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
				 	if(rv[0]<=0)  	alert('您没有选择试卷！');
				 	document.getElementById("eptitle"+id).innerHTML=rv[1];
				 	document.getElementById("epid"+id).value=rv[0];
				 	document.getElementById("epts"+id).innerHTML=rv[2];  
				 }
			}
			/*
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
			*/
			function _onsubmit(){
				//if(jzArr.length != 0)
				document.getElementById("jz").value=jzidArr.join(",");
				//if(gwArr .length != 0)
				document.getElementById("gw").value=gwidArr.join(",");
				//if(dsArr .length != 0)
				document.getElementById("ds").value=dsidArr.join(",");
				//if(zwArr .length != 0)
				document.getElementById("zw").value=zwidArr.join(",");
				//if(zjArr .length != 0)
				document.getElementById("zj").value=zjidArr.join(",");
				//if(bmidArr .length != 0)
				document.getElementById("bm").value=bmidArr.join(","); 
				
				var str=$("#eroom_begintime").val();
				var str2=$("#eroom_endtime").val();
				var date=new Date(Date.parse(str.replace(/-/g,"/")));
				var date2=new Date(Date.parse(str2.replace(/-/g,"/")));
				if(date>date2){
					alert("您设置的问卷开始时间大于结束时间，请重新设置!");
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
				
				if($("#title").val() ==''){
					alert("标题不能为空");
					return false; 
				}if($("#eroom_begintime").val()==''){
					alert("开始时间不能为空");
					$("#eroom_begintime").focus();
					return false; 
				}if($("#eroom_endtime").val()==''){
					alert("结束时间不能为空");
					$("#eroom_endtime").focus();
					return false; 
				}
				if(!checkTimi()){  
					return false;
				}
				if(!eroomDateTimeCheck()){
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
				var ty=0;//<s:property value="examRoom.ertype" />;
				
				//解开试卷信息的disabled
				var checkArray=$("#eps_div").find("input");
				//alert(checkArray.length);
				for(var i=0;i<checkArray.length;i++){
					if($(checkArray[i]).attr("type")=="checkbox"||$(checkArray[i]).attr("type")=="radio"){
						$(checkArray[i]).attr("disabled","");
					}else if($(checkArray[i]).attr("type")=="text"){
						$(checkArray[i]).attr("readonly","false");
					}
				}
				return true;
			}
			function eroomDateTimeCheck(){
				var classStarttime="<s:date name="elclass.starttime" format="yyyy-MM-dd-HH-mm-ss"/>";
				var classFinishtime="<s:date name="elclass.finishtime" format="yyyy-MM-dd-HH-mm-ss"/>";
				var eroomStarttime=$("#eroom_begintime").val();
				var eroomFinishtime=$("#eroom_endtime").val();
				return dateTimeCheck(classStarttime,classFinishtime,eroomStarttime,eroomFinishtime);
			}
			function deleteEps(obj,id,islx,index){
				alert(islx);
				if(window.confirm("确定将此试卷从本考场中删除？")){
					erid = <s:property value="examRoom.id"/> ;
					if(islx==1){
						$.post("eroom_delete_ep.action", {
							"examPaper.id":id,
							"examRoom.id":erid,
							"x":Math.random
							}, 
							function (data) {
								//alert('删除成功');
							});
						var checkArray=$("#eps_"+index).find("input");
						for(var i=0;i<checkArray.length;i++){
							if($(checkArray[i]).attr("type")=="checkbox"||$(checkArray[i]).attr("type")=="radio"){
								$(checkArray[i]).attr("disabled","false");
							}else if($(checkArray[i]).attr("type")=="text"){
								$(checkArray[i]).attr("readonly","true");
							}
						}
						$("#d_erdel_"+index).css("display","none");
						var _span=$("<a>");
						_span.html("恢复");
						_span.attr("class","textbg4");
						_span.attr("href","javascript:;");
						_span.click(function(){recovery(id,erid,index);});
						//_span.click(recovery(id,erid,index));
						$("#e_erdel_"+index).html(_span);
						//obj.parentNode.parentNode.removeChild(obj.parentNode);
					}else{
						$.post("eroom_delete_eplx.action", {
							"examPaper.id":id,
							"examRoom.id":erid,
							"x":Math.random
							}, 
							function (data) {
								alert('删除成功');
							});
					obj.parentNode.removeChild(obj);
					}
					//obj.parentNode.parentNode.removeChild(obj.parentNode);
				}
			}
			function recovery(id,erid,index){
				//alert("nihao");
				//return false;
				$.post("eroom_huifu_ep.action", {
				"examPaper.id":id,
				"examRoom.id":erid,
				"x":Math.random
				},
				function (data) {
					//alert('恢复成功');
				});
				//$("#a_erdel_"+index).attr("readonly","");
				//$("#b_erdel_"+index).attr("disabled","");
				//$("#c_erdel_"+index).attr("disabled","");
				//var checkArray=$("#eps_div").find("input");
				var checkArray=$("#eps_"+index).find("input");
				//alert(checkArray.length);
				for(var i=0;i<checkArray.length;i++){
					if($(checkArray[i]).attr("type")=="checkbox"||$(checkArray[i]).attr("type")=="radio"){
						$(checkArray[i]).attr("disabled","");
					}else if($(checkArray[i]).attr("type")=="text"){
						$(checkArray[i]).attr("readonly","");
					}
				}
				$("#d_erdel_"+index).css("display","inline");
				$("#e_erdel_"+index).html("");
			}
			var isAp=<s:property value="(examRoom==null||examRoom.classid<=0)&&(course==null||course.id<=0)" />;
			function checkTimi(){  
				var KBStart = document.getElementById("eroom_begintime").value; 
				var KBStop  = document.getElementById("eroom_endtime").value;
				var reust=/^\d*$/;
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
			
			
			var ipcount=0;
			function ipd_addinit(){
				//alert("nihao");
				var ipd =  document.createElement("div");
				ipd.id ="ipd_"+ipcount;
				var ipdStr="<div><span width='160' height='30' align='center' bgcolor='#FFFFFF'>开&nbsp;始&nbsp;ip：</span><span bgcolor='#FFFFFF'><input name='ipStart' type='text'></span>"
				+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span width='160' height='30' align='center' bgcolor='#FFFFFF'>结&nbsp;束&nbsp;ip：</span><span bgcolor='#FFFFFF'><input name='ipEnd' type='text'></span>"
				+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='return delDiv("+ipcount+");' href=''>删除</a></div>";
				ipd.innerHTML= ipdStr;
				ipcount++;
				document.getElementById("ipd_div").appendChild(ipd);
				return false;
			}
			
			function delDiv(ipcount){
				//alert(ipcount);
				var ipObj=document.getElementById("ipd_"+ipcount);
				ipObj.parentNode.removeChild(ipObj);
				return false;
			}
			function delDiv2(ipcount){
				//alert(ipcount);
				var ipObj=document.getElementById("ipd2_"+ipcount);
				ipObj.parentNode.removeChild(ipObj);
				var ipObj=document.getElementById("ipd3_"+ipcount);
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
		</script>
		
		<script type="text/javascript">
		
		var isApplication;
		function disDiv(op){
			var div1=document.getElementById("div_1");
			var div2=document.getElementById("div_2");
			if(op==1){
				div1.style.display="none";
				div2.style.display="block";
				document.getElementById("optional").value="";
			}else{
				div1.style.display="block";
				div2.style.display="none";
			}
		}
		function disDivSQ(op){
			var divSQ1=document.getElementById("divSQ_1");
			var divSQ2=document.getElementById("divSQ_2");
			if(op==1){
				isApplication=1;
				divSQ1.style.display="none";
				divSQ2.style.display="block";
				document.getElementById("optional").value="";
			}else{
				isApplication=2;
				divSQ1.style.display="block";
				divSQ2.style.display="none";
			}
		}
		function init(classType){
			var div1=document.getElementById("div_1");
			var div2=document.getElementById("div_2");
			if(classType==0){
				div1.style.display="block";
				div2.style.display="none";
			}else if(classType==1){
				div1.style.display="none";
				div2.style.display="block";
			}
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
				 //document.getElementById("danwei").value=bh[0];
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
		function moveEroomep(epid,sortid,manner){
			document.getElementById("erpid").value=epid;
			document.getElementById("epSortid").value=sortid;
			document.getElementById("manner").value=manner;
			eroomepForm.submit();
		}
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
  }
	</script>
	</HEAD>
	<body onLoad="loadData('<s:property value="erRegistration.examRoomIds" />','<s:property value="erRegistration.elclassIds" />');">
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">编辑考场</span>
			</li>
			<s:if test="optype!='valid'">
				<li class="sep">
				</li>
				<li>
					<a style="cursor: hand"
						onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
						onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
						href="examroomwithoutcourse_list.action">一般考试管理</a>
				</li>
			</s:if>-->
		</ul>
		<!-- 内容 -->
		<s:form action="eroomEpsort" method="post" name="eroomepForm">
			<s:hidden name="examRoom.id" />
			<s:hidden name="examPaper.id" id="erpid" />
			<s:hidden name="examPaper.sortid" id="epSortid" />
			<s:hidden name="manner" id="manner" />
		</s:form>
		<div style="margin-top:20px;text-align:center;">
			<label style="font-size: 16px;">
				问卷调查管理<a href="javascript:_onsubmit();"></a>
			</label>
			<br>
			<s:form theme="simple" id="form_exam_add" name="form_exam_add"  
				method="post" action="questionnaire_alter"
				onsubmit="return _onsubmit();">
				<%--<s:hidden name="examRoom.ertype" />--%>
				<span style="color: #ff0000;"></span>
				<table width="100%" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr>
						<td width="130" height="30" align="right" >
							<span class="neededitem">*</span>问卷标题：
						</td>
						<td >
							<label>
								<s:textfield name="examRoom.title" id="title" size="60" />
							</label>
						</td>
					</tr>
					<tr style="display:none">
						<td align="right" >
							<span class="neededitem">*</span>所属考场库：
						</td>
						<td bgcolor="#FFFFFF" colspan="3">
							<label>
								<select name="examRoom.eroomLib.id" id="er_erlid">
									<wysLib:eroomLibSelect selectid="${examRoom.eroomLib.id}"></wysLib:eroomLibSelect>
								</select>
							</label>
						</td>
					</tr>
					<tr>
						<td height="30" align="right" >
							问卷说明：
						</td>
						<td >
							<label>
								<s:textarea name="examRoom.description" cols="60" rows="7" />
							</label>
						</td>
					</tr> 
					<tr style="display:none">
						<td height="30" align="right" >
							考场图片：
						</td>
						<td >
							<label>
								<s:textfield name="examRoom.mainimg" id="pic" size="60" />
								<a href="javascript:setUrl('pic');" class="textbg4" style="width:80px">浏览资源库</a>
							</label>
						</td>
					</tr>
					<tr style="display:none">
						<td height="30" align="right" >
							考试地点：
						</td>
						<td >
							<label>
								<s:textfield name="examRoom.location" size="60" />
							</label>
						</td>
					</tr>
					<tr style="display:none">
						<td height="30" align="right" >
							<span class="neededitem">*</span>监考人员：
						</td>
						<td >
							<div id="invigilators"> 
								<s:iterator value="examRoom.invigilators">
									<span
										style="width: 110px; height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
										<label style="width: 80px; float: left;">
											<s:property value="realname" />
										</label> <a
										style="cursor: hand; float: right; width: 14px; height: 14px;"
										href=""
										onclick="javascript:deleteUserinfo(this,<s:property value="id"/>,'rinvigilators');return false;">X</a>
									</span>
								</s:iterator>
							</div>
							<a href=""
								onclick="searchUserInit('invigilators','examRoom.invigilators.id'); return false;" class="textbg4">添加</a>
						</td>
					</tr>
					<tr style="display:none">
						<td height="30" align="right" >
							<span class="neededitem">*</span>阅卷人员：
						</td>
						<td >
							<div id="appraises">
							
							<s:iterator value="examRoom.appraises">
									<span
										style="width: 110px; height: 14px; background: #dddfff; border: solid buttonface 1px; float: left;">
     									<!-- 设置一个隐藏域，用于保存原来的数据，提交表单的时候一块提交过去 -->
     									<input type="hidden" name="examRoom.appraises.id" value="<s:property value="id" />" /> 
										<!-- 必须给radio一个name属性并赋值，radio才可以进行操作选中是否 -->
										<input type="radio" name="examRoom.appr_header.id" value="<s:property value="id" />" <s:if test="isLeader==1">checked="checked"</s:if> /> 							 
											<s:property value="realname" />
										<a
										style="cursor: hand; float: right; width: 14px; height: 14px;"
										href=""
										onclick="javascript:deleteUserinfo(this,<s:property value="id"/>,'rappraises');return false;">X</a>
									</span>
								</s:iterator>
								
								
								
							
							</div>
							<a href=""
								onclick="searchUserInit2('appraises','examRoom.appraises.id'); return false;" class="textbg4">添加</a>
						</td>
					</tr>
					<tr style="display:none">
						<td height="30" align="right" >
							复核人员：
						</td>
						<td >
							<div id="valids">
								<s:iterator value="examRoom.valids">
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
								onclick="searchUserInit('valids','examRoom.valids.id'); return false;" class="textbg4">添加</a>
						</td>
					</tr>
					<tr>
						<td height="30" align="right" >
							<span class="neededitem">*</span>问卷调查时间：
						</td>
						<td >
							<label>
								
								问卷开始时间
								<input style="height:35px;" class="Wdate" name="examRoom.begintime" type="text"
									value="<s:date name="examRoom.begintime" format="yyyy-MM-dd HH:mm:ss"/>"
									id="eroom_begintime" />
									&nbsp;
								<input type="button" class="textbg4"
									onclick="setday(document.getElementById('eroom_begintime'))"
									value="选择" />
								<s:if test="examRoom.classid>0">
									<font color="red">(培训班开始时间：<s:date name="elclass.starttime" format="yyyy-MM-dd HH:mm:ss"/>)</font>
								</s:if>
							</label>
							<br />
							<label>
							问卷结束时间
								<input style="height:35px;" class="Wdate" name="examRoom.endtime" id="eroom_endtime"
									type="text" 
									value="<s:date name="examRoom.endtime" format="yyyy-MM-dd HH:mm:ss"/>" />
									&nbsp;
								<input type="button" class="textbg4"
									onclick="setday(document.getElementById('examRoom.endtime'))"
									value="选择" />
							</label>
							<s:if test="examRoom.classid>0">
								<font color="red">(培训班结束时间：<s:date name="elclass.finishtime" format="yyyy-MM-dd HH:mm:ss"/>)</font>
							</s:if>
						</td>
					</tr>
					
					<tr style="display:none">
						<td height="30" align="right" >
							绑定mac地址：
						</td>
						<td >
								<input name="examRoom.isMacBand" type="radio" value="1"
									<s:if test="examRoom.isMacBand==1">
										checked="checked"
									</s:if>
								>是
								<input name="examRoom.isMacBand" type="radio" value="0"
									<s:if test="examRoom.isMacBand==0">
										checked="checked"
									</s:if>
								>否
						</td>
					</tr>
					<tr style="display:none">
						<td height="30" align="right" >
							限&nbsp;定&nbsp;ip&nbsp;段：
						</td>
						<td >
								<input name="examRoom.isIpLimit" type="radio" value="1" onclick="upIs(1);"
									<s:if test="examRoom.isIpLimit==1">
										checked="checked"
									</s:if>
								>是
								<input name="examRoom.isIpLimit" type="radio" value="0" onclick="upIs(0);"
									<s:if test="examRoom.isIpLimit==0">
										checked="checked"
									</s:if>
								>否	
						</td>
					</tr>
					
						<tr style="display:none">
						  <td colspan="2">
						  	<div>
						  		<div style="float:left">
						  		  	 <s:iterator id="ipStrat" value="#request.ipStratList" status="statu">
						     		   <div id="ipd2_<s:property value="#statu.index"/>" >开&nbsp;始&nbsp;ip：<input name="ipStart" value="<s:property value='ipStrat'/>"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
						  		 	 </s:iterator>  
						  		 </div>
						  		 <div>
						  		  	<s:iterator id="ipEnd" value="#request.ipEndList" status="statu">
						  			  <div id="ipd3_<s:property value="#statu.index"/>" >结&nbsp;束&nbsp;ip：<input name="ipEnd" value="<s:property value='ipEnd'/>"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						  			  <a onClick="return delDiv2('<s:property value="#statu.index"/>');" href="javascript:;">删除</a>
						  			  </div>
						  	        </s:iterator>
						  		 </div>
						  	</div>
						  </td>
						</tr>
					
					<tr style="display:none">
					  <td colspan="2">
					  	<div id="ipd_div"></div>
						<a href="" onClick="return ipd_addinit();" class="textbg6">添加ip段</a>
					  </td>
					</tr>
					<tr style="display:none">
						<td height="30" align="right" >
							<span class="neededitem">*</span>通过成绩：
						</td>
						<td >
							<label>
								<input type="text" style="width: 40px;"
									name="examRoom.passgrade" id="passgrade"
									value="<s:property value="examRoom.passgrade"/>">
								%
							</label>
						</td>
					</tr>
					<tr style="display:none">
						<td align="right">
							<span class="neededitem">*</span>设定密码：
						</td>
						<td>
							<input type="radio" onclick="showpwd(0)" name="examRoom.pwdneed" <s:if test="examRoom.pwdneed==0">checked="checked"</s:if> value="0"/>不需要
							<input type="radio" onclick="showpwd(1)" name="examRoom.pwdneed" <s:if test="examRoom.pwdneed==1">checked="checked"</s:if> value="1"/>需要
							<div id="pwd_div"  <s:if test="examRoom.pwdneed==0">style="display: none;"</s:if>>密码:<input type="text" value="<s:property value="examRoom.pwd"/>" name="examRoom.pwd" />&nbsp;有效期至:<input type="text" name="examRoom.pwdtime" value="<s:date name="examRoom.pwdtime" format="yyyy-MM-dd HH:mm:ss"/>" onclick="setday(this)" /> </div>
							<div>试卷缓存:<input type="radio" <s:if test="examRoom.cacheepsize==1">checked="checked"</s:if> name="examRoom.cacheepsize" value="1"/>是<input type="radio" <s:if test="examRoom.cacheepsize==-1">checked="checked"</s:if> name="examRoom.cacheepsize" value="-1"/>否<!-- 试卷缓存数量:<input type="text" size="4" name="examRoom.cacheepsize" value="<s:property value="examRoom.cacheepsize"/>"/> -->
							&nbsp;&nbsp;<input <s:if test="examRoom.cacheeprefresh==1">checked="checked"</s:if> type="checkbox" value="1" name="examRoom.cacheeprefresh"/>密码改变时刷新缓存
							&nbsp;&nbsp;<input <s:if test="examRoom.epqsort==1">checked="checked"</s:if> type="checkbox" value="1" name="examRoom.epqsort"/>试卷中试题随机排序
							</div>
							
						</td>
					</tr>
					<tr>
						<td height="30" align="right" >
							<span class="neededitem">*</span>所用试卷：
						</td>
						<td >
							<div id="eps_div">
								<s:iterator value="examRoom.exampapers" status="epsst">
									<div id="eps_<s:property value="#epsst.index"/>"> 
										<span style='color:red'>试卷：</span>
										<span style="width:150px;" id="eptitle__<s:property value="#epsst.index"/>"><s:property value="title" /> </span>
										<input type="hidden" epids='1'
											id="epid__<s:property value="#epsst.index"/>"
											name="examPapers.id" value="<s:property value="id"/>" />
				 <br/> 
											&nbsp;&nbsp;&nbsp;&nbsp; <a class="textbg4" style="cursor:pointer;width:14px;height:14px;color:red;" href=""
												onclick="javascript:deleteEps(this,<s:property value="id"/>,1,'<s:property value="#epsst.index"/>');return false;">X</a>
											<span id="e_erdel_<s:property value="#epsst.index"/>" >
												<s:if test="status==1"><a class="textbg4" href="javascript:;" onClick="recovery('<s:property value="id"/>','<s:property value="examRoom.id"/>','<s:property value="#epsst.index"/>');">恢复</a></s:if>
											</span>
									</div>
								</s:iterator>
							</div>
							<a href="" onClick="erep_addinit(); return false;" class="textbg5">添加试卷</a>
						</td>
					</tr>
				<tr style="display:none">
					<td align="right">
						组织单位：
					</td>
					<td>
						<label>
							<input id="danweiName" name="examRoom.depName" readonly="readonly" style="font-size:15px;" value="<s:property value="examRoom.depName" />" />
								<span class="txt-info" style="margin-left:20px;"><a href="#"
									onClick="searchUserInit_();return false;" class="textbg4">选择</a></span>
						</label>
					</td>
				</tr>
				<tr style="display:none">
					<td align="right">
						组织工种：
					</td>
					<td>
						<label>
							<s:select name="examRoom.jingzhong" 
									list="jingzhongs" listKey="basevalue" listValue="basevalue" value="examRoom.jingzhong" />
						</label>
					</td>
				</tr>
				<s:if test="examRoom.classid<=0">
				<tr style="display:none">
					<td height="30" align="right" bgcolor="#E6F9F9">
						<span class="neededitem">*</span>是否可申请：
					</td>
					<td height="30" >
						 <s:if test="#session.roleid==1">
							<input type="radio" name="examRoom.isApplication" value="0" id="examRoom_isApplication"
									onClick="disDivSQ(1);" <s:if test="examRoom.isApplication==0">
								 checked="checked"</s:if>
								/>
								不可申请
								<input type="radio" name="examRoom.isApplication" value="1" id="examRoom_isApplication"
									onClick="disDivSQ(2);" <s:if test="examRoom.isApplication==1">
								 checked="checked"</s:if>
								 />
								可申请
								<input type="radio" name="examRoom.isApplication" value="2" id="examRoom_isApplication"
									onClick="disDivSQ(1);" <s:if test="examRoom.isApplication==2">
								 checked="checked"</s:if>
								/>
								全工
							 </s:if>
							<s:else>  <s:if test="examRoom.isApplication==0">
						 	不可申请
						 </s:if>
						  <s:elseif test="examRoom.isApplication==1">
						 	可申请
						 </s:elseif>
						 <s:elseif test="examRoom.isApplication==2">
						 	全工
						 </s:elseif><s:hidden name="examRoom.isApplication" />
						</s:else>
						<div id="divSQ_1" <s:if test="examRoom.isApplication!=1">style="display:none;"</s:if>>
							<table width="100%" cellpadding="2" cellspacing="1">
								<tr>
									<td colspan="2">
										是否需要审核：<input type="radio" value="1" 
										<s:if test="erRegistration.isAudit==1">checked="checked"</s:if>
										 name="erRegistration.isAudit" />需要
										<input type="radio" value="0" 
										<s:if test="erRegistration.isAudit==0">checked="checked"</s:if>
										 name="erRegistration.isAudit" />不需要
									</td>
								</tr>
								<tr>
									<td colspan="2">
										是否自主选择试卷:
										<input type="radio" value="1"
										<s:if test="erRegistration.isselectep==1">checked</s:if>
										 name="erRegistration.isselectep" />
										是
										<input type="radio" value="0" 
										 <s:if test="erRegistration.isselectep==0">checked</s:if>
											name="erRegistration.isselectep" />
										否
									</td>
								</tr>
								<tr>
									<td width="100" align="right" bgcolor="#E6F9F9">
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
									<td width="100" align="right" bgcolor="#E6F9F9">
										<span class="neededitem">*</span>报名时间段：
									</td>
									<td>
												<input name="erRegistration.RegistrationStartTime"
													id="bm_start" type="text" size="20" 
													value="<s:date name='erRegistration.RegistrationStartTime' format="yyyy-MM-dd HH:mm:ss" />">
													
														&nbsp;
													<input type="button" class="textbg4"
													onclick="setday(document.getElementById('bm_start'))"
													value="选择" />
												～
												<input name="erRegistration.RegistrationStopTime"
													id="bm_end" type="text" size="20" 
													value="<s:date name='erRegistration.RegistrationStopTime' format="yyyy-MM-dd HH:mm:ss" />">
													
													&nbsp;
													<input type="button" class="textbg4"
													onclick="setday(document.getElementById('bm_end'))"
													value="选择" />
											</td>
								</tr>
								<tr>
									<td width="100" align="right" bgcolor="#E6F9F9">
										年龄段：
									</td>
									<td>
										<s:textfield name="erRegistration.StartAge" id="StartAge" />
										～
										<s:textfield name="erRegistration.StopAge" id="StopAge" />
									</td>
								</tr>
								<tr>
									<td width="100" align="right" bgcolor="#E6F9F9">
										性别：
									</td>
									<td>
										<label>
											<input type="radio" name="erRegistration.sex" value="不限"
												<s:if test="examRoom.isApplication!=1||erRegistration.sex==\"\"||erRegistration.sex==\"不限\"">checked="checked"</s:if> />
											不限
											<input type="radio" name="erRegistration.sex"
											 <s:if test="erRegistration.sex==\"男\"">checked="checked"</s:if> value="男" />
											男
											<input type="radio" name="erRegistration.sex"
											 <s:if test="erRegistration.sex==\"女\"">checked="checked"</s:if> value="女" />
											女
										</label>
									</td>
								</tr>
								<tr>
									<td  width="100" align="right" bgcolor="#E6F9F9">部门：</td>
									<td>      
										<div id="danwei" <s:if test="erRegistration.treeType == null ">style="background-color:blank;display: none;"</s:if> > 
											<s:property value="erRegistration.treeTypeName"/>
										</div> 
 										<span class="txt-info"><a href="#"  onClick="searchUsersInit();return false;" class="textbg4">选择</a></span>
									</td>
								</tr> 
								<tr>
									<td width="100" align="right" bgcolor="#E6F9F9">
										<wysLib:BasetName btid="1" />：
									</td> 
									<td>
										<div id="jztj" <s:if test="erRegistration.jingzhong == null ">style="background-color:blank;display: none;"</s:if> > 
											<s:property value="erRegistration.jingzhongName"/>
										</div>
 										<span class="txt-info"><a href="#"  onClick="searBaseDatatInit(1);return false;" class="textbg4">选择</a></span> 
									</td> 
								</tr>
								<tr>
									<td width="100" align="right" bgcolor="#E6F9F9">
										<wysLib:BasetName btid="5" />：
									</td>
									<td>   
										<div id="dstj" <s:if test="erRegistration.dishi == null ">style="background-color:blank;display: none;"</s:if> > 
											<s:property value="erRegistration.dishiName"/>
										</div>
 										<span class="txt-info"><a href="#"  onClick="searBaseDatatInit(5);return false;" class="textbg4">选择</a></span>
									</td>
								</tr>
								<tr>
									<td width="100" align="right" bgcolor="#E6F9F9">
										<wysLib:BasetName btid="2" />：
									</td>
									<td>
										<div id="zwtj"  <s:if test="erRegistration.zhiwu == null ">style="background-color:blank;display: none;"</s:if>>  
											<s:property value="erRegistration.zhiwuName"/>
										</div>
 										<span class="txt-info"><a href="#"  onClick="searBaseDatatInit(2);return false;" class="textbg4">选择</a></span> 
									</td>
								</tr>
								<tr>
									<td width="100" align="right" bgcolor="#E6F9F9">
										<wysLib:BasetName btid="3" />：
									</td>
									<td>
										<div id="zjtj" <s:if test="erRegistration.zhiji == null ">style="background-color:blank;display: none;"</s:if>> 
											<s:property value="erRegistration.zhijiName"/>
										</div>
 										<span class="txt-info"><a href="#"  onClick="searBaseDatatInit(3);return false;" class="textbg4">选择</a></span> 
									</td>
								</tr>
								<%-- 
								<tr>
									<td width="100" align="right" bgcolor="#E6F9F9">
										<wysLib:BasetName btid="4" />:
									</td>
									<td>
										<div id="gwtj" <s:if test="erRegistration.gangwei == null ">style="background-color:blank;display: none;"</s:if>> 
											<s:property value="erRegistration.gangweiName"/>
										</div>
 										<span class="txt-info"><a href="#"  onClick="searBaseDatatInit(4);return false;">点此进行选择</a></span> 
									</td>
								</tr>
								 --%>
								<tr>
									<td width="100" align="right" bgcolor="#E6F9F9">
										考场：
									</td>
									<td> 
										<div id="KC"><!--  <s:if test="erRegistration.examroomName == null ">style="display: blank;width: 100%;"</s:if> -->
										<SCRIPT type="text/javascript">eridx="<s:property value="erRegistration.erParas.size"/>"==""?0:parseInt('<s:property value="erRegistration.erParas.size"/>');
										</SCRIPT>
										<s:iterator value="erRegistration.erParas" status="erparas_st">
												<div id="_kc_u<s:property value="examRoom.id"/>" style="width: 100%; height: 14px;border: 1px olid;float: left;background:#edefff">
												<input name="erRegistration.erParas[<s:property value="#erparas_st.index"/>].examRoom.id" value="<s:property value="examRoom.id"/>" type="hidden">
												<label style="text-align: left; width: 260px; float: left">名称：<s:property value="examRoom.title"/></label>
												<label style="text-align: left; float: left">是否通过：
												<select name="erRegistration.erParas[<s:property value="#erparas_st.index"/>].isPassed">
												<option <s:if test="isPassed==-1">selected='selected'</s:if> value="-1">全部</option>
												<option <s:if test="isPassed==1">selected='selected'</s:if>  value=1>是</option>
												<option <s:if test="isPassed==2">selected='selected'</s:if> value="2">否</option></select>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												考场成绩：<select name="erRegistration.erParas[<s:property value="#erparas_st.index"/>].examScoreTerm">
													<option <s:if test="examScoreTerm==\">=\"">selected='selected'</s:if>  value=">=">&gt;=</option>
													<option <s:if test="examScoreTerm==\">\"">selected='selected'</s:if> value=">">&gt;</option>
													<option <s:if test="examScoreTerm=='='">selected='selected'</s:if> value="=">=</option>
													<option <s:if test="examScoreTerm==\"<\"">selected='selected'</s:if> value="&lt;">&lt;</option>
													<option <s:if test="examScoreTerm==\"<=\"">selected='selected'</s:if> value=">=">&lt;=</option>
												</select>
												<INPUT style="WIDTH: 30px" name=erRegistration.erParas[<s:property value="#erparas_st.index"/>].examScore maxLength=4 value="<s:property value="examScore"/>">
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<select name="erRegistration.erParas[<s:property value="#erparas_st.index"/>].linkTerm">
												<option  <s:if test="linkTerm=='or'">selected='selected'</s:if> value=or>or</option>
												<option  <s:if test="linkTerm=='and'">selected='selected'</s:if> value=and>and</option></select>
												</label><A style="WIDTH: 14px; FLOAT: right; HEIGHT: 14px; CURSOR: hand" onClick="javascript:deleteExamRoomUserinfo(this,<s:property value="examRoom.id"/>);return false;" href="javascript:void(0)">X</A>
												</div>
											</s:iterator>
										</div>
										<%-- 
										<div id="KC_"  <s:if test="erRegistration.examRoom.size == 0 ">style="display: none;width: 100%;"</s:if>>
											<s:property value="erRegistration.examroomName"/>
											<a  href="" style="color:red"  <s:if test="erRegistration.examRoom.size == 0">style="display: none;"</s:if>
												onclick="javascript:deleteNames(1);return false;">X</a>  
										</div>
		  									<s:hidden name="erRegistration.examroomNam"  ></s:hidden> 
		  								 
											<SELECT name="erRegistration.eroomScreeningWay">
												<option value="0" <s:if test="erRegistration.eroomScreeningWay == 0 ">selected="selected"</s:if>>全部</option>
												<option value="1" <s:if test="erRegistration.eroomScreeningWay == 1 ">selected="selected"</s:if>>通过</option>
												<option value="2" <s:if test="erRegistration.eroomScreeningWay == 2 ">selected="selected"</s:if>>不通过</option>
											</SELECT>--%>
										<span class="txt-info"><a href="#"
											onClick="searchExamRoomUser();return false;" class="textbg4">选择</a> </span>
									</td>
								</tr>
								<tr>
									<td width="100" align="right" bgcolor="#E6F9F9">
										考场试卷：
									</td>
									<td> 
										<div id="KCEP"><!--  <s:if test="erRegistration.examroomName == null ">style="display: block;width: 100%;"</s:if> -->
											<SCRIPT type="text/javascript">epindex_="<s:property value="erRegistration.erepParas.size"/>"==""?0:parseInt('<s:property value="erRegistration.erepParas.size"/>');</SCRIPT>
											<s:iterator value="erRegistration.erepParas" status="erepparas_st">
														<div id="_kcep_u_<s:property value="examPaper.id"/>_<s:property value="examRoom.id"/>" style="width: 100%; height: 14px; border: solid #fff 1px; background: #edefff">
<input name="erRegistration.erepParas[<s:property value="#erepparas_st.index" />].examRoom.id" value="<s:property value="examRoom.id"/>" type="hidden"><input name="erRegistration.erepParas[<s:property value="#erepparas_st.index" />].examPaper.id" value="<s:property value="examPaper.id"/>" type="hidden">
<label style="TEXT-ALIGN: left; WIDTH: 150px; FLOAT: left">考场名称：<span style="font-weight: 600"><s:property value="examRoom.title"/></span></label>
<label style="TEXT-ALIGN: left; WIDTH: 150px; FLOAT: left">试卷名称：<span style="font-weight: 600"><s:property value="examPaper.title"/></span></label>
<label style="TEXT-ALIGN: left; FLOAT: left; MARGIN-LEFT: 30px">是否通过：<select name="erRegistration.erepParas[<s:property value="#erepparas_st.index" />].isPassed">
<option <s:if test="isPassed==-1">selected='selected'</s:if> value="-1">全部</option>
<option <s:if test="isPassed==1">selected='selected'</s:if> value="1">是</option>
<option <s:if test="isPassed==2">selected='selected'</s:if> value="2">否</option>
</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;考试次数：<select name="erRegistration.erepParas[<s:property value="#erepparas_st.index" />].examCountTerm">
<option <s:if test="examCountTerm==\">=\"">selected='selected'</s:if> value=">=">&gt;=</option>
<option <s:if test="examCountTerm==\">\"">selected='selected'</s:if> value=">">&gt;</option>
<option <s:if test="examCountTerm==\"=\"">selected='selected'</s:if> value="=">=</option>
<option <s:if test="examCountTerm==\"<\"">selected='selected'</s:if> value="&lt;">&lt;</option>
<option <s:if test="examCountTerm==\"<=\"">selected='selected'</s:if> value="<=">&lt;=</option>
</select><input style="WIDTH: 30px" name="erRegistration.erepParas[<s:property value="#erepparas_st.index" />].examCount" maxLength="4" value="<s:property value="examCount"/>">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<BR>平均分：<select name="erRegistration.erepParas[<s:property value="#erepparas_st.index" />].avgScoreTerm">
<option <s:if test="avgScoreTerm==\">=\"">selected='selected'</s:if> value=">=">&gt;=</option>
<option <s:if test="avgScoreTerm==\">\"">selected='selected'</s:if> value=">">&gt;</option>
<option <s:if test="avgScoreTerm==\"=\"">selected='selected'</s:if> value="=">=</option>
<option <s:if test="avgScoreTerm==\"<\"">selected='selected'</s:if> value="&lt;">&lt;</option>
<option <s:if test="avgScoreTerm==\"<=\"">selected='selected'</s:if> value="&lt;=">&lt;=</option>
</select>
<input style="WIDTH: 30px" name="erRegistration.erepParas[<s:property value="#erepparas_st.index" />].avgScore" maxLength="4" value="<s:property value="avgScore"/>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
最高分：<select name="erRegistration.erepParas[<s:property value="#erepparas_st.index" />].maxScoreTerm">
<option <s:if test="maxScoreTerm==\">=\"">selected='selected'</s:if> value=">=">&gt;=</option>
<option <s:if test="maxScoreTerm==\">\"">selected='selected'</s:if> value=">">&gt;</option>
<option <s:if test="maxScoreTerm==\"=\"">selected='selected'</s:if> value="=">=</option>
<option <s:if test="maxScoreTerm==\"<\"">selected='selected'</s:if> value="&lt;">&lt;</option>
<option <s:if test="maxScoreTerm==\"<=\"">selected='selected'</s:if> value="&lt;=">&lt;=</option></select>
<input style="WIDTH: 30px" name="erRegistration.erepParas[<s:property value="#erepparas_st.index" />].maxScore" maxLength="4" value="<s:property value="maxScore"/>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<select name="erRegistration.erepParas[<s:property value="#erepparas_st.index" />].linkTerm">
	<option <s:if test="linkTerm==\"or\"">selected='selected'</s:if> value="or">or</option>
	<option <s:if test="linkTerm==\"and\"">selected='selected'</s:if> value="and">and</option>
</select></label>
<A style="WIDTH: 14px; FLOAT: right; HEIGHT: 14px; CURSOR: hand" onClick="javascript:deleteExamRoomEpUserinfo(this);return false;" href="javascript:void(0)">X</A>
</div>
											</s:iterator>
										</div>
										<span class="txt-info"><a href="#"
											onClick="searchExamRoomEpUser();return false;" class="textbg4">选择</a> </span>
									</td>
								</tr>
								<tr>
									<td width="100" align="right" bgcolor="#E6F9F9">
										培训班：
									</td>
									<td>  
										<div id="PXB"> <!--  <s:if test="erRegistration.elclassName == null ">style="display: block;width: 100%;"</s:if> -->
										<SCRIPT type="text/javascript">clindex_="<s:property value="erRegistration.classParas.size"/>"==""?0:parseInt('<s:property value="erRegistration.classParas.size"/>');</SCRIPT>
											<s:iterator value="erRegistration.classParas" status="classparas_st">
											<div id="_pxb_u<s:property value="elClass.id"/>" style="width: 100%; height: 14px;background: #edefff;border:solid #fff 1px;float:left">
											<INPUT name="erRegistration.classParas[<s:property value="#classparas_st.index" />].elClass.id" value=<s:property value="elClass.id"/> type=hidden>
											<LABEL style="TEXT-ALIGN: left; WIDTH: 260px; FLOAT: left">名称：<s:property value="elClass.name"/></LABEL>
											<LABEL style="TEXT-ALIGN: left; FLOAT: left">是否通过：<SELECT name="erRegistration.classParas[<s:property value="#classparas_st.index" />].isPassed">
											<OPTION <s:if test="isPassed==-1">selected='selected'</s:if> value=-1>全部</OPTION>
											<OPTION <s:if test="isPassed==1">selected='selected'</s:if> value=1>是</OPTION>
											<OPTION <s:if test="isPassed==2">selected='selected'</s:if> value=2>否</OPTION>
											</SELECT>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总学分：<INPUT style="WIDTH: 30px" name="erRegistration.classParas[<s:property value="#classparas_st.index" />].sumScoreStart" maxLength="4" value="<s:property value="sumScoreStart"/>">&nbsp;&nbsp;~&nbsp;&nbsp;<INPUT style="WIDTH: 30px" name="erRegistration.classParas[<s:property value="#classparas_st.index" />].sumScoreEnd" maxLength=4 value="<s:property value="sumScoreEnd"/>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<BR/>必修总学分：<INPUT style="WIDTH: 30px" name="erRegistration.classParas[<s:property value="#classparas_st.index" />].bsumScoreStart" maxLength="4" value="<s:property value="bsumScoreStart"/>">&nbsp;&nbsp;~&nbsp;&nbsp;<INPUT style="WIDTH: 30px" name="erRegistration.classParas[<s:property value="#classparas_st.index" />].bsumScoreEnd" maxLength=4 value="<s:property value="bsumScoreEnd"/>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											选修总学分：<INPUT style="WIDTH: 30px" name="erRegistration.classParas[<s:property value="#classparas_st.index" />].xsumScoreStart" maxLength="4" value="<s:property value="xsumScoreStart"/>">&nbsp;&nbsp;~&nbsp;&nbsp;<INPUT style="WIDTH: 30px" name="erRegistration.classParas[<s:property value="#classparas_st.index" />].xsumScoreEnd" maxLength=4 value="<s:property value="xsumScoreEnd"/>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<SELECT name="erRegistration.classParas[<s:property value="#classparas_st.index" />].linkTerm">
											<OPTION <s:if test="linkTerm==\"or\"">selected='selected'</s:if> value="or">or</OPTION>
											<OPTION <s:if test="linkTerm==\"and\"">selected='selected'</s:if> value="and">and</OPTION>
											</SELECT></LABEL><A style="WIDTH: 14px; FLOAT: right; HEIGHT: 14px; CURSOR: hand" onClick="javascript:deleteClassUserinfo(this,<s:property value="elClass.id"/>);return false;" href="javascript:void(0)">X</A>
										</div>
										</s:iterator>
										</div>
										<%-- 									
										<div id="PXB_" <s:if test="erRegistration.elclass.size == 0 ">style="display: none;width: 100%;"</s:if>>
											<s:property value="erRegistration.elclassName"/>
											<a  href="" style="color:red"  <s:if test="erRegistration.elclasss.size == 0 ">style="display: none;"</s:if>
												onclick="javascript:deleteNames(2);return false;">X</a> 
										</div>
		  									<s:hidden name="erRegistration.elclassName"  ></s:hidden>
		  								 --%>
		  									
										<!-- <SELECT name="erRegistration.classScreeningWay">
											<option value="0" <s:if test="erRegistration.classScreeningWay == 0 ">selected="selected"</s:if>>全部</option>
											<option value="1" <s:if test="erRegistration.classScreeningWay == 1 ">selected="selected"</s:if>>通过</option>
											<option value="2" <s:if test="erRegistration.classScreeningWay == 2 ">selected="selected"</s:if>>不通过</option>
										</SELECT> -->
										<span class="txt-info"><a href="#"
											onClick="searchElclassUser();return false;" class="textbg4">选择</a> </span> 
									</td>
								</tr>
							</table>
						</div>
						<div id="divSQ_2"  style="display:none;">
						</div>
					</td>
				</tr>
				</s:if>
					<tr>
						<td width="130" height="50" align="center" >
							&nbsp;
							<s:hidden name="optype"></s:hidden>
							<s:hidden name="course.id"></s:hidden>
							<s:hidden name="examRoom.valid"></s:hidden>
							<s:hidden name="examRoom.id"></s:hidden>
						</td>
						<td >
							<input type="submit" style="color: red;border: none;" name="button2" id="button2" class="textbg5" value="确认修改" />
							<s:if test="examRoom.classid==0||examRoom.course.id==-1">
								<s:if test="Return==null||Return==''">
									<input class="textbg5" style="border: none;" type="button"
										value="返回考场列表"
										onclick="document.location='examroom_alllist.action'" /></s:if>
									<s:if test="Return=='assign'">
									<input class="textbg5" style="border: none;" type="button"
										value="返回分配列表" onClick="document.location='examroomwithoutcourse_list.action'" />
									</s:if>
									<s:if test="Return=='ash'">
									<input class="textbg5" style="border: none;" type="button"
										value="返回申请列表" onClick="document.location='examroom_prima_shlist.action'" />
									</s:if>
									<s:if test="Return=='sh'">
									<input class="textbg5" style="border: none;" type="button"
										value="返回审核列表" onClick="document.location='examroom_shlist.action'" />
									</s:if>
							</s:if>
							<s:else>
							<s:hidden name="examRoom.classid"></s:hidden>
							<s:hidden name="examRoom.course.id"></s:hidden>
							<input class="textbg5" style="border: none;" type="button"
								value="班级课程列表"
								onclick="document.location='elclass_course.action?elclass.id=${examRoom.classid }'" />
							<input class="textbg5" style="border: none;" type="button"
								value="返回绑定列表"
								onclick="document.location='examroom_choose_listbycInit.action?course.id=${examRoom.course.id }&classId=${examRoom.classid }&Return=elclass_alterInit'" />	
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
				<%-- 
				<s:hidden name="isEroomName" id="isEroomName" value="1"></s:hidden>  
				<s:hidden name="isClassName" id="isClassName" value="1"></s:hidden>
				 --%>
			</s:form>
			<SCRIPT type="text/javascript">
				function  RoomAlert(){
					if(window.confirm("确定申请修改吗？")){ 
						form_exam_add.action="examroom_audit.action";
						form_exam_add.submit();
					}
				}
			</SCRIPT>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>