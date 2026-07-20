<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>   
 <%@ taglib prefix="fmt" 
           uri="http://java.sun.com/jsp/jstl/fmt" %> 
 <%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
		+ request.getServerName() + ":" + request.getServerPort()
		+ path + "/";
%>          
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">
<title>Hsk网考模拟阅读部分</title>
<link href="mock/css/hsktest.css" rel="stylesheet" type="text/css">
<script src="mock/js/jquery.js" type="text/javascript"></script>
<script src="mock/js/jquery-ui-1.8.7.custom.min.js" type="text/javascript"></script>
<script src="mock/js/jquery-selection.js" type="text/javascript"></script>
<script type="text/javascript">
	var paperid = '3a62af7b158fd16b78c5fe8290234724';
	var recordId = '0d9963d036f8a65dfeac73ed7154e5f6';
	var publishId = '75702f581757492a4521924fec31a7d2';
	var levelid = '01';
	var lastPart = 'listen';
	var lastTime = '';
	var partListen = 'yes';
	var partRead = 'yes';
	var partWrite = 'no';
	
	if(lastPart=="write"){
		location.href="mock.action?method=exam&part=write&paper_id="+paperid+"&record_id="+ recordId +"&level="+levelid+"&publish_id=" + publishId;
	}
	
	//页面加载完毕执行				 
	$(document).ready(function() { 	
		
		//自适应宽度和高度
		resize();
		window.onresize = function(){
			resize();
		};
		
		//禁止选择复制
		//document.oncontextmenu=new Function('event.returnValue=false;');
		//document.onselectstart=new Function('event.returnValue=false;');	  
		
       	
       	$(".top_outer").dblclick(function(){
       		var nav_state = $("#test_nav").css("visibility");
	       	if(nav_state == "hidden"){
	       		$("#test_nav").css("visibility","visible");
	       	}else{
	       		$("#test_nav").css("visibility","hidden");
	       	}
       	}); 
       	
		//倒计时开始
		var AllSecond;
		var PartSecond;
		var showSecond;
		var InterValObj;
		
		AllSecond = 60 * '17';//考试剩余时间
		PartSecond = 60 * '17';//部分剩余时间
		showSecond = 0;
		
		//已答题时间
		var min = 0;
		var sec = 0;
		var alreadysecond = 0;
		if(lastTime!=""){
			min = lastTime.split(':')[0];
			sec = lastTime.split(':')[1];
			alreadysecond = min*60+parseInt(sec);
			//alert(min+"--"+sec+"---"+alreadysecond);
			AllSecond = parseInt(AllSecond) -(parseInt(PartSecond)-parseInt(alreadysecond)) ;//倒计时起始时间
			PartSecond = alreadysecond;
		}
		
		//重置窗体尺寸
		function resize(){
			screen_height = $(window).height();
			diff_height = screen_height-$("body").height();
			
			$(".main_outer").height($(".main_outer").height()+diff_height);
			$(".main_tab").height($(".main_outer").height()-20);
			$(".main_tab").width($(".main_outer").width()-20);
			$(".mask").height($("body").height());
			$(".left_menu").height($(".main_outer").height()-200);
			$(".left_menu_in").height($(".left_menu").height()-20);
			
			$(".ques_out").height($(".main_outer").height()-48);
			
			$(".cont_outer").height($(".main_outer").height()-25);
			$(".cont_outer").width($(".main_outer").width()-20);
			$(".kc_cont").height($(".cont_outer").height()-120);
			$(".kc_cont").width($(".cont_outer").width()-80);
			$(".ques_in").height($(".ques_out").height());
			$(".hCols").height($(".ques_out").height()-90);
		}
		
		//更新剩余时间
		function updateTime(){
			
			//提交作答结果
			$.ajax({
				type: "POST", 
				url: 'mock.action?method=updateLastTime',
				cache: false,
				data: { 
					record_id: recordId,
					last_part:"read", 
					last_time:lastTime
				},
				dataType: "text",
				success: function(data) {
					if($.trim(data) != "yes"){
						alert("更新考试时间失败！");
					}
				}
			});
		}		
		
		//开始计时
		InterValObj = window.setInterval(SetRemainTime, 1000); //间隔函数，1秒执行
		
		//将时间减去1秒，计算天、时、分、秒
		function SetRemainTime() {
			if (AllSecond > 0) {
				AllSecond--;
				PartSecond--;
				showSecond++;
			   
				var second = Math.floor(AllSecond % 60)>9?Math.floor(AllSecond % 60):"0"+Math.floor(AllSecond % 60); // 计算秒    
				var minite = Math.floor(AllSecond/60)>9?Math.floor(AllSecond/60):"0"+Math.floor(AllSecond/60); //计算分
				//var hour = Math.floor((AllSecond/3600) % 24)>9?Math.floor((AllSecond/3600) % 24):"0"+Math.floor((AllSecond/3600) % 24); //计算小时
			   
				var second_part = Math.floor(PartSecond % 60)>9?Math.floor(PartSecond % 60):"0"+Math.floor(PartSecond % 60); // 计算秒    
				var minite_part = Math.floor(PartSecond/60)>9?Math.floor(PartSecond/60):"0"+Math.floor(PartSecond/60); //计算分
				//var hour_part = Math.floor((PartSecond/3600) % 24)>9?Math.floor((PartSecond/3600) % 24):"0"+Math.floor((PartSecond/3600) % 24); //计算小时
			   
				// var day = Math.floor((AllSecond/3600) / 24); //计算天				
				$("#allTim").html("考试结束还剩：<b>"+minite + "</b>分<b>"+ second + "</b>秒");
				
				if(PartSecond==300){
					$(".time_note").fadeIn();
				}
				   
				if(PartSecond==295){
					$(".time_note").fadeOut();
				}
				
				if(PartSecond>0){
					$("#partTim").html("阅读部分还剩：<b>"+minite_part + "</b>分<b>"+ second_part + "</b>秒");
					lastTime = minite_part + ":"+ second_part;
				}else{
					window.clearInterval(InterValObj);
					
					//判断页面走向
					if(partWrite == "yes"){
						location.href="mock.action?method=exam&part=write&paper_id="+paperid+"&record_id="+ recordId +"&level="+levelid+"&publish_id=" + publishId;
					}else{
						$(".yes_btn").click();
					}
				} 
				
				//每分钟提交一次剩余时间
				if(showSecond > 0 && (showSecond % 60)==0){
					updateTime();
				}
			} else {
				//剩余时间小于或等于0的时候，就停止间隔函数
				window.clearInterval(InterValObj);
				$(".yes_btn").click();
			}
	 	}
		
	    //显示隐藏左侧菜单
	   	$(".class_two").click(function(){
	   		$(this).next(".class_thr_list").slideToggle(100);
	   		$(this).find("span").toggle();
	   		return false;
	   	});
	   	
	   	$(".class_one").click(function(){
	   		$(".part_out").slideToggle(100);
	   		return false;
	   	});
	   	
	   	//显示考试须知，考试提示
	   	$("#xz_btn").click(function(){
	   		//$(".main_tab").hide();
	   		//$("#xuzhi").show();
	   		//return false;
	   	});
	   	
	   	$("#ts_btn").click(function(){
	   		//$(".main_tab").hide();
	   		//$("#tishi").show();
	   		//return false;
	   	});
	   	
	   	//关闭按钮
	   	$("#close,#close2").click(function(){		
	   		$("#xuzhi").hide();
	   		$("#tishi").hide();
	   		$(".main_tab").show();
	   		return false;
	   	});
	   	
	   	//时间提示关闭按钮
	   	$(".time_note_close").click(function(){		
	   		$(".time_note").fadeOut();
	   		return false;
	   	});
	   	
	  	//提交按钮
		$("#handIn").click(function(){		
			$(".mask").show();
			$("#tipBeforeSubmit").show();
		});
		
		//确认提交
		$(".yes_btn").click(function(){
			//count_item();
			window.clearInterval(InterValObj);
			$("#tipBeforeSubmit").hide();
			$("#tipSubmit").show();
			location.href="mock.action?method=finish&paper_id=" + paperid + "&level=" + levelid + "&record_id=" + recordId;
			return false;
		});
		
		//取消提交
		$(".no_btn").click(function(){
			$(".mask").hide();
			$("#tipBeforeSubmit").hide();
			return false;
		});
		
		//点击选项内容
		$(".opt_cont,.opt_title").click(function(){
			var opt_id = $(this).attr("optid");
			var isExample = $(this).parents(".ans_out").attr("example");
			if($.trim(isExample) != "yes"){
				$(".s_chose[id=" + opt_id + "]").attr("checked",true);
				$(".s_chose[id=" + opt_id + "]").click();
			}
		});
	   	
		//选项点击操作
		$(".s_chose").click(function(){
			setAnsMark($(this));
			postAns($(this));
		});	
		
		//提交作答结果
		function postAns(myObj){
			var quesID = myObj.parents(".ans_out").attr("que_id");
			var subPartID = myObj.parents(".nav_part").attr("part_num");
			
			//提交作答结果
			$.ajax({
				type: "POST", 
				url: 'mock.action?method=ansSubmit',
				cache: false,
				data: { 
					paper_id: paperid, 
					record_id: recordId ,
					ques_id: quesID, 
					subPart_id: subPartID,
					user_ans: myObj.val(),
					last_part:"read", //暂时设置为听力，最后要改为read
					last_time:lastTime,
					root_stru:"read"
				},	
				dataType: "text",
				success: function(data) {
					if($.trim(data) != "0" && $.trim(data) != "1"){
						alert("提交作答结果失败！");
					}
				}
			});
		}
		
		//拖拽排序
		
		$(".dragOut").each(function(index){	
			var myObj = $(this);
			myObj.find( ".dragFrom" ).sortable({										
				opacity: 0.6,
				placeholder:'dragIng',
				connectWith: myObj.find('.dragTag')
			});
			
			myObj.find( ".dragTag" ).sortable({
				opacity: 0.6,
				placeholder:'dragIng',
				connectWith: myObj.find('.dragFrom'),
				update:function() {
					myObj.find(".ans_input").val("");
					var obj = $(this).find(".dragElem");
					obj.each(function(n){
						myObj.find(".ans_input").val(myObj.find(".ans_input").val()+$(this).html());
					});	
					setAnsMark(myObj.find(".ans_input"));
					postAns(myObj.find(".ans_input"));
				}
			});
		});
		
		$("dragElem").disableSelection();
		
		
		//拖拽文本到文本域，firefox不能用
		$(".drag_inline").disableSelection();
		$(".drag_inline").mousedown(function() {
			$(this).enableSelection("true");
			$(this).selectContents(); 
			$(this).disableSelection();
		});
		
		//鼠标点击将拖拽文本插入文本域光标处
		$(".sent_area").setCaret();
		$(".drag_inline").click(function() {
			$(this).parents(".ans_out").find(".sent_area").insertAtCaret($(this).html());
		});		
		
		//检测输入框变化,保存数据
		$(".my_input").blur(function(){
			setTxtAnsMark($(this));
			postAns($(this));
		});
		
		//检测输入框变化,保存数据
		$(".my_input").change(function(){
			setTxtAnsMark($(this));
			//postAns($(this));
		});
		
		//设置作答标记
		function setTxtAnsMark(txtObj){
			var myTxt = txtObj.val();
			var targetID = txtObj.parents(".ans_out").attr("que_id");
			if(myTxt != ""){
				$(".class_thr[nav_id='" + targetID + "']").find(".ansed").show();
			}else{
				$(".class_thr[nav_id='" + targetID + "']").find(".ansed").hide();
			};
		}
		
		function setAnsMark(obj){
			//设置选项的样式
			var opt_id = obj.attr("id");
			obj.parents(".ans_out").find(".opt_title").removeClass("s_chosed_t");
			obj.parents(".ans_out").find(".opt_cont").removeClass("s_chosed");
			$(".opt_title[optid=" + opt_id + "]").addClass("s_chosed_t");
			$(".opt_cont[optid=" + opt_id + "]").addClass("s_chosed");
			
			//判断是不是综合题，设置作答标记
			var queID = obj.parents(".ques_list").attr("que_id");
			if(obj.parents(".ques_list").attr("isMix")=="no"){
				$(".class_thr[nav_id='" + queID + "']").find(".ansed").show();
			}else{
				var quzNum = obj.parents(".ques_list").find(".ans_out[example='no']").length;
				var ansNum = obj.parents(".ques_list").find(".ans_out[example='no']").find(":radio:checked").length;
				//alert("quzNum:" + quzNum + "-----ansNum" + ansNum);
				if(ansNum==quzNum){
					$(".class_thr[nav_id='" + queID + "']").find(".ansed").show();
				}
			}
		}		
	   	
	   	//初始化试题部分参数
	   	var now_id = $(".class_thr:first").attr("nav_id");
	   	var part_num = 1;
	   	var ques_total = $(".class_thr").size();
	
	   	$(".ques_list[example='no']").hide();//lk
	   	$(".ques_list[que_id=" + now_id + "]").show();
	   	$(".prev_quz").hide();
	   	
	   	//标题点击
	   	$(".class_thr").click(function(){		
	   		now_id = $(this).attr("nav_id");
	   		part_num = $(this).attr("part");
	   		
       		var firstQue = $(".class_thr[part=" + part_num + "]:first").attr("nav_id");
       		var partObj = $("#part_" + part_num);
       		var examObj = $("#part_" + part_num).find(".examp_out").find(".example_ques_list");
       		if(examObj.size()>0){
    	   		if(now_id != firstQue){
    	   			partObj.find(".examp_out").hide();
    	   			partObj.find("a").html("显示示例");
    	   		}else{
    	   			partObj.find(".examp_out").show();
   	   				partObj.find("a").html("隐藏示例");
    	   		}
       		}else{
	   			partObj.find(".examp_out").hide();
	   			partObj.find(".examp_btn").hide();
       		}
	   		
   			$(".nav_part").hide();
   			$("#part_"+part_num).show();
	   		
	   		$(".ques_list[example='no']").hide();
	   		$(".ques_list[que_id=" + now_id + "]").show();
	   		if(now_id==$(".class_thr:first").attr("nav_id")){
	   			$(".prev_quz").hide();
	   			$(".next_quz").show();
	   			$(".next_part").hide();
	   		}else{
	   			if(now_id==$(".class_thr:last").attr("nav_id")){
	   				$(".prev_quz").show();
	   				$(".next_quz").hide();
	   				if(partWrite=="yes"){
	   					$(".next_part").show();
	   				}else{
	   					$(".next_part").hide();
	   				}
				}else{
					$(".next_quz").show();
					$(".prev_quz").show();
					$(".next_part").hide();
				}
	   		}
	   		
	   		return false;
	   	});
	   	
	   	//显示隐藏例题
	   	$(".examp_btn a").click(function(){
	   		$(this).parents(".nav_part").find(".examp_out").slideToggle(100);
	   		if($(this).parents(".examp_btn").find("a").html()=="显示示例"){
	   			$(this).parents(".examp_btn").find("a").html("隐藏示例");
	   		}else{
	   			$(this).parents(".examp_btn").find("a").html("显示示例");
	   		};
	   		return false;
	   	});
	   	
	   	//上一题
	   	$(".prev_quz").click(function(){
	   		var now_index = $(".class_thr[nav_id='" + now_id + "']").attr("ques_index");
	   		prev_index = now_index*1 - 1;
	   		if(now_index > 0){
	   			$(".class_thr[ques_index='"+prev_index+"']").click();
	   		}
	   		return false;
	   	});
	   	
	   	//下一题
	   	$(".next_quz").click(function(){
	   		var now_index = $(".class_thr[nav_id='" + now_id + "']").attr("ques_index");
	   		next_index = now_index*1 + 1;
	   		if(now_index < ques_total){
	   			$(".class_thr[ques_index='"+next_index+"']").click();
	   		}
	   		return false;
	   	});
	   	
	   	//下一部分
	   	$(".next_part").click(function(){		
	   		//$(".mask").show();
	   		//$(".info_bar").show();
	   		$("#btnWrite").click();
	   	});
	   	
	   	//下一部分返回
	   	$(".sure_btn").click(function(){
	   		$(".mask").hide();
	   		$(".info_bar").hide();
	   		return false;
	   	}); 
	   	
	   	//显示第一个正式题目
	   	var firstQueID = $(".ques_list[example='no']").find(".ques_num").first().attr("que_id");
	   	$(".class_thr[nav_id='" + firstQueID + "']").click();
	   	
	   	//恢复考生答案
		$(".ans_detail").each(function(){
			var queID = $(this).attr("quesID");
			var queAns = $.trim($(this).html());
			var queObj = $(".ans_out[que_id=" + queID + "]");
			var queType = $.trim(queObj.attr("que_type"));

			if(queType=="03"){
				$("#txt_" + queID).val(queAns);
				setTxtAnsMark($("#txt_" + queID));
				
				//完全拖拽题目单独对待
				var dragSign = queObj.attr("drag_sign");
				if(dragSign == "11"){
					
					var dragItems = queObj.find(".dragElem");
					var posObj = new Object();
					var posArray = new Array();
					dragItems.each(function(index){
						var itemTxt = $(this).html();
						var posIndex = queAns.indexOf(itemTxt);
						
						if(posIndex != -1){
							posObj[posIndex] = $(this);
							posArray.push(posIndex);
						}
					});

					posArray.sort(compareInt);
					for(var i = 0; i < posArray.length; i++){
						queObj.find(".dragTag").append(posObj[posArray[i]]);
					}
				}
			}else{
				var optObj = queObj.find(".s_chose[value='" + queAns + "']");
				optObj.attr("checked","true");
				setAnsMark(optObj);
			}
		});
	   	
		function compareInt(int1, int2){
		    var iNum1 = parseInt(int1);//强制转换成int 型;
		    var iNum2 = parseInt(int2);
		    if(iNum1 < iNum2){
		        return -1;
		    }else if(iNum1 > iNum2){
		        return 1;
		    }else{
		        return 0;
		    }
		}
	   	
		//字符限制
		$(".my_input[allowChar!='']").keydown(function(event){
			var allowChar = $(this).attr("allowChar").toUpperCase();
			var c_char = event.keyCode;
			if(c_char == '8'){//BackSpace
				return;
			}	
			if(c_char == '20'){//Caps_Lock
				return;
			}
			if(c_char == '16'){//Shift_L
				return;
			}
			if(c_char == '37'){//left
				return;
			}
			if(c_char == '39'){//right
				return;
			}
			if(c_char == '32'){//space
				return;
			}
			if(c_char == '13'){//Enter
				return;
			}
			if(c_char == '46'){//Del
				return;
			}
			var str = String.fromCharCode(c_char).toUpperCase();
			if(allowChar.toUpperCase().indexOf(str) == -1){
				alert("只允许输入[" + allowChar + "]，不区分大小写");
				event.preventDefault();
			}
			
			var myValue = $(this).val();
			if(myValue.length >= allowChar.length){
				event.preventDefault();
			}
		});
		
		//处理例题的字体和选中项
		$(".example_ques_list").find(".ques_num").css("font-size","16px");
		$(".example_ques_list").find(".s_chose").each(function(){
			var exampleAns = $(this).parents(".ans_out").attr("ques_ans");
			if($(this).val().toUpperCase()==exampleAns.toUpperCase()){
				var opt_id = $(this).attr("id");
				$(this).attr("checked","true");
				$(".opt_title[optid=" + opt_id + "]").addClass("s_chosed_t");
				$(".opt_cont[optid=" + opt_id + "]").addClass("s_chosed");
			}
			$(this).attr("disabled","true");
		});
		
		//处理综合题中的选项
		$(".ans_out[example='yes']").find(".ques_num").css("font-size","16px");
		$(".ans_out[example='yes']").find(".s_chose").each(function(){
			var exampleAns = $(this).parents(".ans_out").attr("ques_ans");
			$(this).attr("disabled","true");
			if($(this).val().toUpperCase()==exampleAns.toUpperCase()){
				var opt_id = $(this).attr("id");
				$(this).attr("checked","true");
				$(".opt_title[optid=" + opt_id + "]").addClass("s_chosed_t");
				$(".opt_cont[optid=" + opt_id + "]").addClass("s_chosed");
			}
		});
		
		$(".ans_out[example='yes']").find(".s_chose").click(function(){
			return false;
		});
	   	
       	$("#btnRead").click(function(){
       		location.href="mock.action?method=exam&part=listen&paper_id="+paperid+"&record_id="+ recordId +"&level="+levelid+"&publish_id=" + publishId;
       	});
       	
       	$("#btnWrite").click(function(){
       		location.href="mock.action?method=exam&part=write&paper_id="+paperid+"&record_id="+ recordId +"&level="+levelid+"&publish_id=" + publishId;
       	});
       	
       	$("#test_nav").css("visibility","hidden");
       	//$("#test_nav" ).draggable();      	
	});
</script>
</head>
<body>
<div class="top_outer">
  <div class="top_tit">
    <div id="test_nav" style="cursor: pointer; width: 120px; visibility: hidden;">
      <input type="button" value="听力" id="btnRead">
      <input type="button" value="书写" id="btnWrite">
    </div>
  </div>
  <div class="top_time">
    <div class="top_time_left"></div>
  </div>
  <div id="allTim" class="all_time">考试结束还剩：<b>04</b>分<b>50</b>秒</div>
  <div id="partTim" class="part_time">阅读部分还剩：<b>04</b>分<b>50</b>秒</div>
  <div id="handIn" class="hand_in"><a href="#">提交试卷</a></div>
  <div class="time_note" style="display: none;">
    <div class="time_note_close"><a href="#"></a></div>
    <div class="time_note_txt"> 请注意：<br>
      阅读部分还剩 <b style="color:#C00;">5</b> 分钟！
      <div class="emp_item"></div>
    </div>
  </div>
</div>
<div class="main_outer" style="height: 899px;">
  <div style="height: 15px;"></div>
  <table width="99%" border="0" cellspacing="0" cellpadding="0" class="main_tab" style="height: 879px; width: 884px;">
    <tbody>
      <tr>
        <td width="266" valign="top"><!--考生信息-->
          <div class="stu_info">
            <div class="stu_info_tl"></div>
            <div class="stu_info_tr"></div>
            <div class="stu_info_bl"></div>
            <div class="stu_info_br"></div>
            <div class="stu_btn"> <a id="xz_btn" href="#">考场须知</a><a id="ts_btn" href="#">考试提示</a> </div>
            <div class="stu_pic"></div>
            <div class="stu_cont"> 考试科目：HSK 01 级 <br>
              姓名：jiajiajia1 <br>
              性别： <br>
            </div>
          </div>
          <!--左侧导航-->
          <div class="left_menu" style="height: 699px;">
            <div class="com_tl"></div>
            <div class="com_tr"></div>
            <div class="com_bl"></div>
            <div class="com_br"></div>
            <div class="left_menu_in scroll_style" style="height: 679px;">
              <div class="class_one_dis">听力</div>
              <div class="class_one">阅读</div>
              <div class="part_out">
                <!-- 左侧题号列表开始 -->
                <div class="class_two"> <span class="o_icon"></span> <span class="c_icon" style="display:none"></span> 第一部分 </div>
                <div class="class_thr_list">
                  <div class="class_thr" nav_id="b71dc54487769202296f85dee68ce5ff" ques_index="0" nav_num="21" part="7544aad40f10e77fc4e7f2335572e1f5">
                    <div class="now_play"></div>
                    <div class="ansed"></div>
                    <div class="quesTit">21</div>
                  </div>
                  <div style="clear:both;"></div>
                  <div class="class_thr" nav_id="480ab75491014a8acca57ec057f93148" ques_index="1" nav_num="22" part="7544aad40f10e77fc4e7f2335572e1f5">
                    <div class="now_play"></div>
                    <div class="ansed"></div>
                    <div class="quesTit">22</div>
                  </div>
                  <div style="clear:both;"></div>
                  <div class="class_thr" nav_id="5397f6528e5139f9546aa1651ceadc0f" ques_index="2" nav_num="23" part="7544aad40f10e77fc4e7f2335572e1f5">
                    <div class="now_play"></div>
                    <div class="ansed"></div>
                    <div class="quesTit">23</div>
                  </div>
                  <div style="clear:both;"></div>
                  <div class="class_thr" nav_id="f9da77f0532e680c6f8dd0e8f11038db" ques_index="3" nav_num="24" part="7544aad40f10e77fc4e7f2335572e1f5">
                    <div class="now_play"></div>
                    <div class="ansed"></div>
                    <div class="quesTit">24</div>
                  </div>
                  <div style="clear:both;"></div>
                  <div class="class_thr" nav_id="ccfda2b2b8b49dd27d819e9a05850ad5" ques_index="4" nav_num="25" part="7544aad40f10e77fc4e7f2335572e1f5">
                    <div class="now_play"></div>
                    <div class="ansed"></div>
                    <div class="quesTit">25</div>
                  </div>
                  <div style="clear:both;"></div>
                </div>
                <div class="class_two"> <span class="o_icon"></span> <span class="c_icon" style="display:none"></span> 第二部分 </div>
                <div class="class_thr_list">
                  <div class="class_thr" nav_id="d254f14a47abbdcc5a8d81c89e792d11" ques_index="5" nav_num="26-30" part="c41b2f1da62f9149821f58118c43e23e">
                    <div class="now_play"></div>
                    <div class="ansed"></div>
                    <div class="quesTit">26-30</div>
                  </div>
                  <div style="clear:both;"></div>
                </div>
                <div class="class_two"> <span class="o_icon"></span> <span class="c_icon" style="display:none"></span> 第三部分 </div>
                <div class="class_thr_list">
                  <div class="class_thr" nav_id="ab9d0f500e75a8ed1f649a635ec7afc9" ques_index="6" nav_num="31-35" part="cac83ec079c41b32b3158807329b61d1">
                    <div class="now_play"></div>
                    <div class="ansed"></div>
                    <div class="quesTit">31-35</div>
                  </div>
                  <div style="clear:both;"></div>
                </div>
                <div class="class_two"> <span class="o_icon"></span> <span class="c_icon" style="display:none"></span> 第四部分 </div>
                <div class="class_thr_list">
                  <div class="class_thr" nav_id="97ef87d478c462ccc7260d8041bde8d2" ques_index="7" nav_num="36-40" part="92868ee64b9841721aa1de85c68fc37c">
                    <div class="now_play"></div>
                    <div class="ansed"></div>
                    <div class="quesTit">36-40</div>
                  </div>
                  <div style="clear:both;"></div>
                </div>
                <!-- 左侧题号列表 结束-->
                <input type="hidden" id="answer_time" value="">
              </div>
            </div>
          </div></td>
        <td valign="top"><div class="ques_out" style="height: 851px;">
            <div class="com_tl2"></div>
            <div class="com_tr2"></div>
            <div class="com_bl"></div>
            <div class="com_br"></div>
            <div class="ques_in scroll_style" style="height: 851px;">
              <!----------------------part1开始-------------------------------------->
              <div id="body">
                <!-- 右侧试题列表开始 -->
                <!-- 根据级别设置是否有例题 -->
                <!-- 遍历部分 -->
                <!-- 小节遍历 -->
                <!-- 部分外框 -->
                <div id="part_7544aad40f10e77fc4e7f2335572e1f5" part_num="7544aad40f10e77fc4e7f2335572e1f5" class="nav_part" style="display: block;">
                  <!-- 根据不同级别显示例题 -->
                  <div class="examp_btn">
                    <div class="left"></div>
                    <div class="middle"><a href="#" onFocus="this.blur()">隐藏示例</a></div>
                    <div class="right"></div>
                    <div class="examp_data">1</div>
                  </div>
                  <!-- 输出小节标题 -->
                  <div class="quz_info">21-25.</div>
                  <div style="clear: both;"></div>
                  <!-- 试题集合遍历 -->
                  <div class="examp_out">
                    <div style="clear:both;"></div>
                    <div class="example_ques_list" example="yes" id="quz_例如" que_id="6b34162135f55c40c976d41ab029131d" que_num="例如" ismix="no">
                      <div class="ques_num" que_id="6b34162135f55c40c976d41ab029131d" que_num="例如" style="font-size: 16px;">
                        <div>例如</div>
                      </div>
                      <!-- 试题题干 -->
                      <table cellpadding="0" cellspacing="0">
                        <tbody>
                          <tr>
                            <td valign="top"><div class="ques_cont">
                                <div class="ques_cont_in"><img alt="" src="http://media.tangce.cn:5080/Hsk_Media/streams/papers/3a62af7b158fd16b78c5fe8290234724/2013021318482721-25a.jpg" style="width: 198px; height: 127px"></div>
                              </div></td>
                          </tr>
                          <tr>
                            <td valign="top"><div class="ans_out" example="yes" que_type="02" ques_ans="B" que_id="6b34162135f55c40c976d41ab029131d" quz_list="例如">
                                <div class="ques_in_list">
                                  <table cellpadding="0" cellspacing="0">
                                    <tbody>
                                      <tr>
                                        <td style="padding:5px;"><table cellpadding="0" cellspacing="0">
                                            <!-- 选项遍历 -->
                                            <tbody>
                                              <tr>
                                                <td class="row_opt"><input class="s_chose" name="6b34162135f55c40c976d41ab029131d" id="874d5cc52d6d44623292e70541a0f765" type="radio" value="A" disabled="disabled">
                                                </td>
                                                <td class="row_opt"><span class="opt_title" optid="874d5cc52d6d44623292e70541a0f765">A</span> </td>
                                                <td>．</td>
                                                <td><span class="opt_cont" optid="874d5cc52d6d44623292e70541a0f765">√</span></td>
                                              </tr>
                                              <!-- 选项遍历 -->
                                              <tr>
                                                <td class="row_opt"><input class="s_chose" name="6b34162135f55c40c976d41ab029131d" id="132ce5d1ef5ce5ccdc5689b801790de8" type="radio" value="B" checked="checked" disabled="disabled">
                                                </td>
                                                <td class="row_opt"><span class="opt_title s_chosed_t" optid="132ce5d1ef5ce5ccdc5689b801790de8">B</span> </td>
                                                <td>．</td>
                                                <td><span class="opt_cont s_chosed" optid="132ce5d1ef5ce5ccdc5689b801790de8">×</span></td>
                                              </tr>
                                            </tbody>
                                          </table></td>
                                      </tr>
                                    </tbody>
                                  </table>
                                </div>
                              </div></td>
                          </tr>
                        </tbody>
                      </table>
                    </div>
                    <div style="clear:both;"></div>
                    <div class="example_ques_list" example="yes" id="quz_例如" que_id="040d421a6e90851111722f8d42afa77b" que_num="例如" ismix="no">
                      <div class="ques_num" que_id="040d421a6e90851111722f8d42afa77b" que_num="例如" style="font-size: 16px;">
                        <div>例如</div>
                      </div>
                      <!-- 试题题干 -->
                      <table cellpadding="0" cellspacing="0">
                        <tbody>
                          <tr>
                            <td valign="top"><div class="ques_cont">
                                <div class="ques_cont_in"><img alt="" src="http://media.tangce.cn:5080/Hsk_Media/streams/papers/3a62af7b158fd16b78c5fe8290234724/2013021318490321-25b.jpg" style="width: 251px; height: 96px"></div>
                              </div></td>
                          </tr>
                          <tr>
                            <td valign="top"><div class="ans_out" example="yes" que_type="02" ques_ans="A" que_id="040d421a6e90851111722f8d42afa77b" quz_list="例如">
                                <div class="ques_in_list">
                                  <table cellpadding="0" cellspacing="0">
                                    <tbody>
                                      <tr>
                                        <td style="padding:5px;"><table cellpadding="0" cellspacing="0">
                                            <!-- 选项遍历 -->
                                            <tbody>
                                              <tr>
                                                <td class="row_opt"><input class="s_chose" name="040d421a6e90851111722f8d42afa77b" id="32b5dbd1ff000d8bebce137d863489ee" type="radio" value="A" checked="checked" disabled="disabled">
                                                </td>
                                                <td class="row_opt"><span class="opt_title s_chosed_t" optid="32b5dbd1ff000d8bebce137d863489ee">A</span> </td>
                                                <td>．</td>
                                                <td><span class="opt_cont s_chosed" optid="32b5dbd1ff000d8bebce137d863489ee">√</span></td>
                                              </tr>
                                              <!-- 选项遍历 -->
                                              <tr>
                                                <td class="row_opt"><input class="s_chose" name="040d421a6e90851111722f8d42afa77b" id="2606a17f5cd9ca93228114dcaca4f26d" type="radio" value="B" disabled="disabled">
                                                </td>
                                                <td class="row_opt"><span class="opt_title" optid="2606a17f5cd9ca93228114dcaca4f26d">B</span> </td>
                                                <td>．</td>
                                                <td><span class="opt_cont" optid="2606a17f5cd9ca93228114dcaca4f26d">×</span></td>
                                              </tr>
                                            </tbody>
                                          </table></td>
                                      </tr>
                                    </tbody>
                                  </table>
                                </div>
                              </div></td>
                          </tr>
                        </tbody>
                      </table>
                    </div>
                  </div>
                  <div>
                    <div style="clear:both;"></div>
                    <div class="ques_list" example="no" id="quz_21" que_id="b71dc54487769202296f85dee68ce5ff" que_num="21" ismix="no" style="display: block;">
                      <div class="ques_num ques_real" que_id="b71dc54487769202296f85dee68ce5ff" que_num="21">
                        <div>21</div>
                      </div>
                      <!-- 试题题干 -->
                      <table cellpadding="0" cellspacing="0">
                        <tbody>
                          <tr>
                            <td valign="top"><div class="ques_cont">
                                <div class="ques_cont_in"><img alt="" src="http://media.tangce.cn:5080/Hsk_Media/streams/papers/3a62af7b158fd16b78c5fe8290234724/2013021318521721.jpg" style="width: 267px; height: 149px"></div>
                              </div></td>
                          </tr>
                          <tr>
                            <td valign="top"><div class="ans_out" example="no" que_type="02" ques_ans="A" que_id="b71dc54487769202296f85dee68ce5ff" quz_list="21">
                                <div class="ques_in_list">
                                  <table cellpadding="0" cellspacing="0">
                                    <tbody>
                                      <tr>
                                        <td style="padding:5px;"><table cellpadding="0" cellspacing="0">
                                            <!-- 选项遍历 -->
                                            <tbody>
                                              <tr>
                                                <td class="row_opt"><input class="s_chose" name="b71dc54487769202296f85dee68ce5ff" id="0faa0ad803b5522743948cab8b372074" type="radio" value="A">
                                                </td>
                                                <td class="row_opt"><span class="opt_title" optid="0faa0ad803b5522743948cab8b372074">A</span> </td>
                                                <td>．</td>
                                                <td><span class="opt_cont" optid="0faa0ad803b5522743948cab8b372074">√</span></td>
                                              </tr>
                                              <!-- 选项遍历 -->
                                              <tr>
                                                <td class="row_opt"><input class="s_chose" name="b71dc54487769202296f85dee68ce5ff" id="9af3514e43eaba0a2203b62da744099a" type="radio" value="B">
                                                </td>
                                                <td class="row_opt"><span class="opt_title" optid="9af3514e43eaba0a2203b62da744099a">B</span> </td>
                                                <td>．</td>
                                                <td><span class="opt_cont" optid="9af3514e43eaba0a2203b62da744099a">×</span></td>
                                              </tr>
                                            </tbody>
                                          </table></td>
                                      </tr>
                                    </tbody>
                                  </table>
                                </div>
                              </div></td>
                          </tr>
                        </tbody>
                      </table>
                    </div>
                    <div style="clear:both;"></div>
                    <div class="ques_list" example="no" id="quz_22" que_id="480ab75491014a8acca57ec057f93148" que_num="22" ismix="no" style="display:none;">
                      <div class="ques_num ques_real" que_id="480ab75491014a8acca57ec057f93148" que_num="22">
                        <div>22</div>
                      </div>
                      <!-- 试题题干 -->
                      <table cellpadding="0" cellspacing="0">
                        <tbody>
                          <tr>
                            <td valign="top"><div class="ques_cont">
                                <div class="ques_cont_in"><img alt="" src="http://media.tangce.cn:5080/Hsk_Media/streams/papers/3a62af7b158fd16b78c5fe8290234724/2013021318531122.jpg" style="width: 246px; height: 128px"></div>
                              </div></td>
                          </tr>
                          <tr>
                            <td valign="top"><div class="ans_out" example="no" que_type="02" ques_ans="A" que_id="480ab75491014a8acca57ec057f93148" quz_list="22">
                                <div class="ques_in_list">
                                  <table cellpadding="0" cellspacing="0">
                                    <tbody>
                                      <tr>
                                        <td style="padding:5px;"><table cellpadding="0" cellspacing="0">
                                            <!-- 选项遍历 -->
                                            <tbody>
                                              <tr>
                                                <td class="row_opt"><input class="s_chose" name="480ab75491014a8acca57ec057f93148" id="00352bc99f7b75819775c3b2cc07ea2c" type="radio" value="A">
                                                </td>
                                                <td class="row_opt"><span class="opt_title" optid="00352bc99f7b75819775c3b2cc07ea2c">A</span> </td>
                                                <td>．</td>
                                                <td><span class="opt_cont" optid="00352bc99f7b75819775c3b2cc07ea2c">√</span></td>
                                              </tr>
                                              <!-- 选项遍历 -->
                                              <tr>
                                                <td class="row_opt"><input class="s_chose" name="480ab75491014a8acca57ec057f93148" id="ee9d2e3d5d3a562030e297bc7b47be3c" type="radio" value="B">
                                                </td>
                                                <td class="row_opt"><span class="opt_title" optid="ee9d2e3d5d3a562030e297bc7b47be3c">B</span> </td>
                                                <td>．</td>
                                                <td><span class="opt_cont" optid="ee9d2e3d5d3a562030e297bc7b47be3c">×</span></td>
                                              </tr>
                                            </tbody>
                                          </table></td>
                                      </tr>
                                    </tbody>
                                  </table>
                                </div>
                              </div></td>
                          </tr>
                        </tbody>
                      </table>
                    </div>
                    <div style="clear:both;"></div>
                    <div class="ques_list" example="no" id="quz_23" que_id="5397f6528e5139f9546aa1651ceadc0f" que_num="23" ismix="no" style="display:none;">
                      <div class="ques_num ques_real" que_id="5397f6528e5139f9546aa1651ceadc0f" que_num="23">
                        <div>23</div>
                      </div>
                      <!-- 试题题干 -->
                      <table cellpadding="0" cellspacing="0">
                        <tbody>
                          <tr>
                            <td valign="top"><div class="ques_cont">
                                <div class="ques_cont_in"><img alt="" src="http://media.tangce.cn:5080/Hsk_Media/streams/papers/3a62af7b158fd16b78c5fe8290234724/2013021318533123.jpg" style="width: 245px; height: 157px"></div>
                              </div></td>
                          </tr>
                          <tr>
                            <td valign="top"><div class="ans_out" example="no" que_type="02" ques_ans="B" que_id="5397f6528e5139f9546aa1651ceadc0f" quz_list="23">
                                <div class="ques_in_list">
                                  <table cellpadding="0" cellspacing="0">
                                    <tbody>
                                      <tr>
                                        <td style="padding:5px;"><table cellpadding="0" cellspacing="0">
                                            <!-- 选项遍历 -->
                                            <tbody>
                                              <tr>
                                                <td class="row_opt"><input class="s_chose" name="5397f6528e5139f9546aa1651ceadc0f" id="a290bfca9e1409c2889a7ccca74e93ed" type="radio" value="A">
                                                </td>
                                                <td class="row_opt"><span class="opt_title" optid="a290bfca9e1409c2889a7ccca74e93ed">A</span> </td>
                                                <td>．</td>
                                                <td><span class="opt_cont" optid="a290bfca9e1409c2889a7ccca74e93ed">√</span></td>
                                              </tr>
                                              <!-- 选项遍历 -->
                                              <tr>
                                                <td class="row_opt"><input class="s_chose" name="5397f6528e5139f9546aa1651ceadc0f" id="db0e296cfce93ba57aca7a2d522219bb" type="radio" value="B">
                                                </td>
                                                <td class="row_opt"><span class="opt_title" optid="db0e296cfce93ba57aca7a2d522219bb">B</span> </td>
                                                <td>．</td>
                                                <td><span class="opt_cont" optid="db0e296cfce93ba57aca7a2d522219bb">×</span></td>
                                              </tr>
                                            </tbody>
                                          </table></td>
                                      </tr>
                                    </tbody>
                                  </table>
                                </div>
                              </div></td>
                          </tr>
                        </tbody>
                      </table>
                    </div>
                    <div style="clear:both;"></div>
                    <div class="ques_list" example="no" id="quz_24" que_id="f9da77f0532e680c6f8dd0e8f11038db" que_num="24" ismix="no" style="display:none;">
                      <div class="ques_num ques_real" que_id="f9da77f0532e680c6f8dd0e8f11038db" que_num="24">
                        <div>24</div>
                      </div>
                      <!-- 试题题干 -->
                      <table cellpadding="0" cellspacing="0">
                        <tbody>
                          <tr>
                            <td valign="top"><div class="ques_cont">
                                <div class="ques_cont_in"><img alt="" src="http://media.tangce.cn:5080/Hsk_Media/streams/papers/3a62af7b158fd16b78c5fe8290234724/2013021318535024.jpg" style="width: 209px; height: 173px"></div>
                              </div></td>
                          </tr>
                          <tr>
                            <td valign="top"><div class="ans_out" example="no" que_type="02" ques_ans="A" que_id="f9da77f0532e680c6f8dd0e8f11038db" quz_list="24">
                                <div class="ques_in_list">
                                  <table cellpadding="0" cellspacing="0">
                                    <tbody>
                                      <tr>
                                        <td style="padding:5px;"><table cellpadding="0" cellspacing="0">
                                            <!-- 选项遍历 -->
                                            <tbody>
                                              <tr>
                                                <td class="row_opt"><input class="s_chose" name="f9da77f0532e680c6f8dd0e8f11038db" id="78e87387e68a5984695e5823a661c24c" type="radio" value="A">
                                                </td>
                                                <td class="row_opt"><span class="opt_title" optid="78e87387e68a5984695e5823a661c24c">A</span> </td>
                                                <td>．</td>
                                                <td><span class="opt_cont" optid="78e87387e68a5984695e5823a661c24c">√</span></td>
                                              </tr>
                                              <!-- 选项遍历 -->
                                              <tr>
                                                <td class="row_opt"><input class="s_chose" name="f9da77f0532e680c6f8dd0e8f11038db" id="d6272074bb8ccb29bead7b4c5fade133" type="radio" value="B">
                                                </td>
                                                <td class="row_opt"><span class="opt_title" optid="d6272074bb8ccb29bead7b4c5fade133">B</span> </td>
                                                <td>．</td>
                                                <td><span class="opt_cont" optid="d6272074bb8ccb29bead7b4c5fade133">×</span></td>
                                              </tr>
                                            </tbody>
                                          </table></td>
                                      </tr>
                                    </tbody>
                                  </table>
                                </div>
                              </div></td>
                          </tr>
                        </tbody>
                      </table>
                    </div>
                    <div style="clear:both;"></div>
                    <div class="ques_list" example="no" id="quz_25" que_id="ccfda2b2b8b49dd27d819e9a05850ad5" que_num="25" ismix="no" style="display:none;">
                      <div class="ques_num ques_real" que_id="ccfda2b2b8b49dd27d819e9a05850ad5" que_num="25">
                        <div>25</div>
                      </div>
                      <!-- 试题题干 -->
                      <table cellpadding="0" cellspacing="0">
                        <tbody>
                          <tr>
                            <td valign="top"><div class="ques_cont">
                                <div class="ques_cont_in"><img alt="" src="http://media.tangce.cn:5080/Hsk_Media/streams/papers/3a62af7b158fd16b78c5fe8290234724/2013021318541125.jpg" style="width: 278px; height: 149px"></div>
                              </div></td>
                          </tr>
                          <tr>
                            <td valign="top"><div class="ans_out" example="no" que_type="02" ques_ans="B" que_id="ccfda2b2b8b49dd27d819e9a05850ad5" quz_list="25">
                                <div class="ques_in_list">
                                  <table cellpadding="0" cellspacing="0">
                                    <tbody>
                                      <tr>
                                        <td style="padding:5px;"><table cellpadding="0" cellspacing="0">
                                            <!-- 选项遍历 -->
                                            <tbody>
                                              <tr>
                                                <td class="row_opt"><input class="s_chose" name="ccfda2b2b8b49dd27d819e9a05850ad5" id="c881e8a483d05546b936dad913222979" type="radio" value="A">
                                                </td>
                                                <td class="row_opt"><span class="opt_title" optid="c881e8a483d05546b936dad913222979">A</span> </td>
                                                <td>．</td>
                                                <td><span class="opt_cont" optid="c881e8a483d05546b936dad913222979">√</span></td>
                                              </tr>
                                              <!-- 选项遍历 -->
                                              <tr>
                                                <td class="row_opt"><input class="s_chose" name="ccfda2b2b8b49dd27d819e9a05850ad5" id="41514084192a3e37972997ca8002471e" type="radio" value="B">
                                                </td>
                                                <td class="row_opt"><span class="opt_title" optid="41514084192a3e37972997ca8002471e">B</span> </td>
                                                <td>．</td>
                                                <td><span class="opt_cont" optid="41514084192a3e37972997ca8002471e">×</span></td>
                                              </tr>
                                            </tbody>
                                          </table></td>
                                      </tr>
                                    </tbody>
                                  </table>
                                </div>
                              </div></td>
                          </tr>
                        </tbody>
                      </table>
                    </div>
                  </div>
                </div>
                <!-- 遍历部分 -->
                <!-- 小节遍历 -->
                <!-- 部分外框 -->
                <div id="part_c41b2f1da62f9149821f58118c43e23e" part_num="c41b2f1da62f9149821f58118c43e23e" class="nav_part" style="display: none;">
                  <!-- 根据不同级别显示例题 -->
                  <div class="examp_btn">
                    <div class="left"></div>
                    <div class="middle"><a href="#" onFocus="this.blur()">隐藏示例</a></div>
                    <div class="right"></div>
                    <div class="examp_data">1</div>
                  </div>
                  <!-- 输出小节标题 -->
                  <div class="quz_info">26-30.</div>
                  <div style="clear: both;"></div>
                  <!-- 试题集合遍历 -->
                  <div class="examp_out"></div>
                  <div>
                    <div style="clear:both;"></div>
                    <div class="ques_list" example="no" id="quz_26-30" que_id="d254f14a47abbdcc5a8d81c89e792d11" que_num="26-30" ismix="yes" style="display:none;">
                      <div class="ques_num ques_real" que_id="d254f14a47abbdcc5a8d81c89e792d11" que_num="26-30">
                        <div>26-30</div>
                      </div>
                      <!-- 试题题干 -->
                      <div class="hTable" style="float:none;overflow:hidden;">
                        <table cellpadding="0" cellspacing="0" width="100%">
                          <tbody>
                            <tr>
                              <td valign="top"><div class="hCols" style="height: 761px;">
                                  <div class="ques_cont">
                                    <div class="ques_cont_in"><img alt="" src="http://media.tangce.cn:5080/Hsk_Media/streams/papers/3a62af7b158fd16b78c5fe8290234724/2013021318564626-30.jpg" style="width: 376px; height: 454px"></div>
                                  </div>
                                </div></td>
                              <td valign="top" style="border-left:1px solid #a3d1e3; padding-top:2px;;min-width:320px;"><div class="hCols" style="height: 761px;">
                                  <!-- 子试题遍历 -->
                                  <!-- 子试题的内容 -->
                                  <div class="ans_out sub_ans_out" example="yes" ques_ans="E" que_type="" que_id="3bf1ca143ecb306455bd5d2752b3a5a5" quz_list="例如">
                                    <div class="ques_num" style="font-size: 16px;">例如</div>
                                    <table cellpadding="0" cellspacing="0">
                                      <tbody>
                                        <tr>
                                          <td style="padding-left:5px;"><div class="ques_cont">
                                              <div class="ques_cont_in"><img alt="" src="http://media.tangce.cn:5080/Hsk_Media/streams/papers/3a62af7b158fd16b78c5fe8290234724/2013021318572426-30E.jpg" style="width: 297px; height: 43px;"></div>
                                            </div></td>
                                        </tr>
                                        <tr>
                                          <td style="padding-left:5px;"><div class="ques_in_list">
                                              <table cellpadding="0" cellspacing="0">
                                                <tbody>
                                                  <tr>
                                                    <td><!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="3bf1ca143ecb306455bd5d2752b3a5a5" id="0d207fdd6691bdedf283579f1c2e2ac8" type="radio" value="A" disabled="disabled">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="0d207fdd6691bdedf283579f1c2e2ac8">A</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="0d207fdd6691bdedf283579f1c2e2ac8"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="3bf1ca143ecb306455bd5d2752b3a5a5" id="f1a7f53386b94a38b7771f59289cfcb1" type="radio" value="B" disabled="disabled">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="f1a7f53386b94a38b7771f59289cfcb1">B</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="f1a7f53386b94a38b7771f59289cfcb1"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="3bf1ca143ecb306455bd5d2752b3a5a5" id="34a8d95b9d7521c495bdb77b4901e9c0" type="radio" value="C" disabled="disabled">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="34a8d95b9d7521c495bdb77b4901e9c0">C</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="34a8d95b9d7521c495bdb77b4901e9c0"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="3bf1ca143ecb306455bd5d2752b3a5a5" id="ffc14b0b6160ad67696dec4df13b7605" type="radio" value="D" disabled="disabled">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="ffc14b0b6160ad67696dec4df13b7605">D</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="ffc14b0b6160ad67696dec4df13b7605"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="3bf1ca143ecb306455bd5d2752b3a5a5" id="17d0864ada02fbadd0bbe412c9a0edf5" type="radio" value="E" disabled="disabled" checked="checked">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title s_chosed_t" optid="17d0864ada02fbadd0bbe412c9a0edf5">E</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont s_chosed" optid="17d0864ada02fbadd0bbe412c9a0edf5"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="3bf1ca143ecb306455bd5d2752b3a5a5" id="d0d9e856bec089739bb62f956bcca53f" type="radio" value="F" disabled="disabled">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="d0d9e856bec089739bb62f956bcca53f">F</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="d0d9e856bec089739bb62f956bcca53f"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table></td>
                                                  </tr>
                                                </tbody>
                                              </table>
                                            </div></td>
                                        </tr>
                                      </tbody>
                                    </table>
                                  </div>
                                  <!-- 子试题遍历 -->
                                  <!-- 子试题的内容 -->
                                  <div class="ans_out sub_ans_out" example="no" ques_ans="D" que_type="" que_id="0d528c0f84d6c3ba7c565fb06ab49ed3" quz_list="26">
                                    <div class="ques_num">26</div>
                                    <table cellpadding="0" cellspacing="0">
                                      <tbody>
                                        <tr>
                                          <td style="padding-left:5px;"><div class="ques_cont">
                                              <div class="ques_cont_in"><img alt="" src="http://media.tangce.cn:5080/Hsk_Media/streams/papers/3a62af7b158fd16b78c5fe8290234724/2013021318585726.jpg" style="width: 371px; height: 51px"></div>
                                            </div></td>
                                        </tr>
                                        <tr>
                                          <td style="padding-left:5px;"><div class="ques_in_list">
                                              <table cellpadding="0" cellspacing="0">
                                                <tbody>
                                                  <tr>
                                                    <td><!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="0d528c0f84d6c3ba7c565fb06ab49ed3" id="cab2d53d81719ee5298c0e8ed3015ba0" type="radio" value="A">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="cab2d53d81719ee5298c0e8ed3015ba0">A</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="cab2d53d81719ee5298c0e8ed3015ba0"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="0d528c0f84d6c3ba7c565fb06ab49ed3" id="ddd50e6a0b68e842c44db38c61ea04b3" type="radio" value="B">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="ddd50e6a0b68e842c44db38c61ea04b3">B</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="ddd50e6a0b68e842c44db38c61ea04b3"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="0d528c0f84d6c3ba7c565fb06ab49ed3" id="7d03254786656e66a58b9c41b541e3b2" type="radio" value="C">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="7d03254786656e66a58b9c41b541e3b2">C</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="7d03254786656e66a58b9c41b541e3b2"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="0d528c0f84d6c3ba7c565fb06ab49ed3" id="6378e8eaf676926500077062b670c11f" type="radio" value="D">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="6378e8eaf676926500077062b670c11f">D</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="6378e8eaf676926500077062b670c11f"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="0d528c0f84d6c3ba7c565fb06ab49ed3" id="48ebe0b31b6171f6326f033a408c3616" type="radio" value="E">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="48ebe0b31b6171f6326f033a408c3616">E</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="48ebe0b31b6171f6326f033a408c3616"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="0d528c0f84d6c3ba7c565fb06ab49ed3" id="8a9475ab92406f2bb5358c5a23101137" type="radio" value="F">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="8a9475ab92406f2bb5358c5a23101137">F</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="8a9475ab92406f2bb5358c5a23101137"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table></td>
                                                  </tr>
                                                </tbody>
                                              </table>
                                            </div></td>
                                        </tr>
                                      </tbody>
                                    </table>
                                  </div>
                                  <!-- 子试题遍历 -->
                                  <!-- 子试题的内容 -->
                                  <div class="ans_out sub_ans_out" example="no" ques_ans="B" que_type="" que_id="f24658677bc1efd940f427f498f63074" quz_list="27">
                                    <div class="ques_num">27</div>
                                    <table cellpadding="0" cellspacing="0">
                                      <tbody>
                                        <tr>
                                          <td style="padding-left:5px;"><div class="ques_cont">
                                              <div class="ques_cont_in"><img alt="" src="http://media.tangce.cn:5080/Hsk_Media/streams/papers/3a62af7b158fd16b78c5fe8290234724/2013021318593427.jpg" style="width: 418px; height: 52px"></div>
                                            </div></td>
                                        </tr>
                                        <tr>
                                          <td style="padding-left:5px;"><div class="ques_in_list">
                                              <table cellpadding="0" cellspacing="0">
                                                <tbody>
                                                  <tr>
                                                    <td><!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="f24658677bc1efd940f427f498f63074" id="6a70565cd825079c379fc6521aeb22e6" type="radio" value="A">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="6a70565cd825079c379fc6521aeb22e6">A</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="6a70565cd825079c379fc6521aeb22e6"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="f24658677bc1efd940f427f498f63074" id="3ade2e4a106fb1bdaf66e207e174fdde" type="radio" value="B">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="3ade2e4a106fb1bdaf66e207e174fdde">B</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="3ade2e4a106fb1bdaf66e207e174fdde"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="f24658677bc1efd940f427f498f63074" id="3395c424fb1ce7307acc80bf058bb10e" type="radio" value="C">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="3395c424fb1ce7307acc80bf058bb10e">C</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="3395c424fb1ce7307acc80bf058bb10e"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="f24658677bc1efd940f427f498f63074" id="802271b01079c0c404c3ca682b524aa5" type="radio" value="D">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="802271b01079c0c404c3ca682b524aa5">D</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="802271b01079c0c404c3ca682b524aa5"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="f24658677bc1efd940f427f498f63074" id="4626f238e67bad7a07db5bfc16b645d5" type="radio" value="E">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="4626f238e67bad7a07db5bfc16b645d5">E</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="4626f238e67bad7a07db5bfc16b645d5"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="f24658677bc1efd940f427f498f63074" id="31e63439bc2c6064587231e9adc33783" type="radio" value="F">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="31e63439bc2c6064587231e9adc33783">F</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="31e63439bc2c6064587231e9adc33783"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table></td>
                                                  </tr>
                                                </tbody>
                                              </table>
                                            </div></td>
                                        </tr>
                                      </tbody>
                                    </table>
                                  </div>
                                  <!-- 子试题遍历 -->
                                  <!-- 子试题的内容 -->
                                  <div class="ans_out sub_ans_out" example="no" ques_ans="A" que_type="" que_id="34ffa1515d0b7908cebe0487a7503b06" quz_list="28">
                                    <div class="ques_num">28</div>
                                    <table cellpadding="0" cellspacing="0">
                                      <tbody>
                                        <tr>
                                          <td style="padding-left:5px;"><div class="ques_cont">
                                              <div class="ques_cont_in"><img alt="" src="http://media.tangce.cn:5080/Hsk_Media/streams/papers/3a62af7b158fd16b78c5fe8290234724/2013021318595528.jpg"></div>
                                            </div></td>
                                        </tr>
                                        <tr>
                                          <td style="padding-left:5px;"><div class="ques_in_list">
                                              <table cellpadding="0" cellspacing="0">
                                                <tbody>
                                                  <tr>
                                                    <td><!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="34ffa1515d0b7908cebe0487a7503b06" id="8c77c9e098a20124cb1ebe1b9343f065" type="radio" value="A">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="8c77c9e098a20124cb1ebe1b9343f065">A</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="8c77c9e098a20124cb1ebe1b9343f065"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="34ffa1515d0b7908cebe0487a7503b06" id="a5ab6d2615355319879947975e9b179b" type="radio" value="B">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="a5ab6d2615355319879947975e9b179b">B</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="a5ab6d2615355319879947975e9b179b"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="34ffa1515d0b7908cebe0487a7503b06" id="7899ae2177c69ffb98b11528ac0b489d" type="radio" value="C">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="7899ae2177c69ffb98b11528ac0b489d">C</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="7899ae2177c69ffb98b11528ac0b489d"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="34ffa1515d0b7908cebe0487a7503b06" id="35a5605da7950d691b18067da671178f" type="radio" value="D">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="35a5605da7950d691b18067da671178f">D</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="35a5605da7950d691b18067da671178f"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="34ffa1515d0b7908cebe0487a7503b06" id="7a4f9b25ed42a470f86613c46fa39c58" type="radio" value="E">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="7a4f9b25ed42a470f86613c46fa39c58">E</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="7a4f9b25ed42a470f86613c46fa39c58"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="34ffa1515d0b7908cebe0487a7503b06" id="568d2820b759f11a0967b902e9eb6931" type="radio" value="F">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="568d2820b759f11a0967b902e9eb6931">F</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="568d2820b759f11a0967b902e9eb6931"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table></td>
                                                  </tr>
                                                </tbody>
                                              </table>
                                            </div></td>
                                        </tr>
                                      </tbody>
                                    </table>
                                  </div>
                                  <!-- 子试题遍历 -->
                                  <!-- 子试题的内容 -->
                                  <div class="ans_out sub_ans_out" example="no" ques_ans="F" que_type="" que_id="a82eb5c26ea9fa6e5df51052e364a8d2" quz_list="29">
                                    <div class="ques_num">29</div>
                                    <table cellpadding="0" cellspacing="0">
                                      <tbody>
                                        <tr>
                                          <td style="padding-left:5px;"><div class="ques_cont">
                                              <div class="ques_cont_in"><img alt="" src="http://media.tangce.cn:5080/Hsk_Media/streams/papers/3a62af7b158fd16b78c5fe8290234724/2013021319001729.jpg" style="width: 468px; height: 51px"></div>
                                            </div></td>
                                        </tr>
                                        <tr>
                                          <td style="padding-left:5px;"><div class="ques_in_list">
                                              <table cellpadding="0" cellspacing="0">
                                                <tbody>
                                                  <tr>
                                                    <td><!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="a82eb5c26ea9fa6e5df51052e364a8d2" id="2597fb4623c5d4cf97570ac987f6a009" type="radio" value="A">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="2597fb4623c5d4cf97570ac987f6a009">A</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="2597fb4623c5d4cf97570ac987f6a009"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="a82eb5c26ea9fa6e5df51052e364a8d2" id="0850e9628253ae1f59ce5fa7a8079826" type="radio" value="B">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="0850e9628253ae1f59ce5fa7a8079826">B</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="0850e9628253ae1f59ce5fa7a8079826"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="a82eb5c26ea9fa6e5df51052e364a8d2" id="650cf4db2bf85076ccead9399dfb4ba3" type="radio" value="C">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="650cf4db2bf85076ccead9399dfb4ba3">C</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="650cf4db2bf85076ccead9399dfb4ba3"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="a82eb5c26ea9fa6e5df51052e364a8d2" id="8e9cbecaf42c0d069e3262a10e6c9796" type="radio" value="D">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="8e9cbecaf42c0d069e3262a10e6c9796">D</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="8e9cbecaf42c0d069e3262a10e6c9796"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="a82eb5c26ea9fa6e5df51052e364a8d2" id="2cc285feeb76a987c03d3c0852c8c42e" type="radio" value="E">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="2cc285feeb76a987c03d3c0852c8c42e">E</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="2cc285feeb76a987c03d3c0852c8c42e"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="a82eb5c26ea9fa6e5df51052e364a8d2" id="b0d9b114b2c86713d7d23abaf52a81da" type="radio" value="F">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="b0d9b114b2c86713d7d23abaf52a81da">F</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="b0d9b114b2c86713d7d23abaf52a81da"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table></td>
                                                  </tr>
                                                </tbody>
                                              </table>
                                            </div></td>
                                        </tr>
                                      </tbody>
                                    </table>
                                  </div>
                                  <!-- 子试题遍历 -->
                                  <!-- 子试题的内容 -->
                                  <div class="ans_out sub_ans_out" example="no" ques_ans="C" que_type="" que_id="805be3c9d1ac96d44587a5e9183cee97" quz_list="30">
                                    <div class="ques_num">30</div>
                                    <table cellpadding="0" cellspacing="0">
                                      <tbody>
                                        <tr>
                                          <td style="padding-left:5px;"><div class="ques_cont">
                                              <div class="ques_cont_in"><img alt="" src="http://media.tangce.cn:5080/Hsk_Media/streams/papers/3a62af7b158fd16b78c5fe8290234724/2013021319003730.jpg" style="width: 313px; height: 50px"></div>
                                            </div></td>
                                        </tr>
                                        <tr>
                                          <td style="padding-left:5px;"><div class="ques_in_list">
                                              <table cellpadding="0" cellspacing="0">
                                                <tbody>
                                                  <tr>
                                                    <td><!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="805be3c9d1ac96d44587a5e9183cee97" id="5a787bf6612e1fd095c70396a26cf56e" type="radio" value="A">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="5a787bf6612e1fd095c70396a26cf56e">A</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="5a787bf6612e1fd095c70396a26cf56e"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="805be3c9d1ac96d44587a5e9183cee97" id="f53302bb81d0caff47a8b3a43ff60447" type="radio" value="B">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="f53302bb81d0caff47a8b3a43ff60447">B</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="f53302bb81d0caff47a8b3a43ff60447"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="805be3c9d1ac96d44587a5e9183cee97" id="d95047d02101e590f40036f0a48f17bb" type="radio" value="C">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="d95047d02101e590f40036f0a48f17bb">C</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="d95047d02101e590f40036f0a48f17bb"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="805be3c9d1ac96d44587a5e9183cee97" id="f4ebf464be1b1455a7a094b7a82c3d5d" type="radio" value="D">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="f4ebf464be1b1455a7a094b7a82c3d5d">D</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="f4ebf464be1b1455a7a094b7a82c3d5d"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="805be3c9d1ac96d44587a5e9183cee97" id="abcab12602479c7eb1e8044b70ee3d1d" type="radio" value="E">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="abcab12602479c7eb1e8044b70ee3d1d">E</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="abcab12602479c7eb1e8044b70ee3d1d"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="805be3c9d1ac96d44587a5e9183cee97" id="39068ac6b868d6d1e3bd3fef218fac75" type="radio" value="F">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="39068ac6b868d6d1e3bd3fef218fac75">F</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="39068ac6b868d6d1e3bd3fef218fac75"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table></td>
                                                  </tr>
                                                </tbody>
                                              </table>
                                            </div></td>
                                        </tr>
                                      </tbody>
                                    </table>
                                  </div>
                                </div></td>
                            </tr>
                          </tbody>
                        </table>
                      </div>
                    </div>
                  </div>
                </div>
                <!-- 遍历部分 -->
                <!-- 小节遍历 -->
                <!-- 部分外框 -->
                <div id="part_cac83ec079c41b32b3158807329b61d1" part_num="cac83ec079c41b32b3158807329b61d1" class="nav_part" style="display: none;">
                  <!-- 根据不同级别显示例题 -->
                  <div class="examp_btn">
                    <div class="left"></div>
                    <div class="middle"><a href="#" onFocus="this.blur()">隐藏示例</a></div>
                    <div class="right"></div>
                    <div class="examp_data">1</div>
                  </div>
                  <!-- 输出小节标题 -->
                  <div class="quz_info">31-35.</div>
                  <div style="clear: both;"></div>
                  <!-- 试题集合遍历 -->
                  <div class="examp_out"></div>
                  <div>
                    <div style="clear:both;"></div>
                    <div class="ques_list" example="no" id="quz_31-35" que_id="ab9d0f500e75a8ed1f649a635ec7afc9" que_num="31-35" ismix="yes" style="display:none;">
                      <div class="ques_num ques_real" que_id="ab9d0f500e75a8ed1f649a635ec7afc9" que_num="31-35">
                        <div>31-35</div>
                      </div>
                      <!-- 试题题干 -->
                      <div class="hTable" style="float:none;overflow:hidden;">
                        <table cellpadding="0" cellspacing="0" width="100%">
                          <tbody>
                            <tr>
                              <td valign="top"><div class="hCols" style="height: 761px;">
                                  <div class="ques_cont">
                                    <div class="ques_cont_in"><img alt="" src="http://media.tangce.cn:5080/Hsk_Media/streams/papers/3a62af7b158fd16b78c5fe8290234724/2013031511391631-35.jpg" style="width: 210px; height: 373px"></div>
                                  </div>
                                </div></td>
                              <td valign="top" style="border-left:1px solid #a3d1e3; padding-top:2px;;min-width:320px;"><div class="hCols" style="height: 761px;">
                                  <!-- 子试题遍历 -->
                                  <!-- 子试题的内容 -->
                                  <div class="ans_out sub_ans_out" example="yes" ques_ans="F" que_type="" que_id="f942ef494f05d7a796fd8f8f0052775c" quz_list="例如">
                                    <div class="ques_num" style="font-size: 16px;">例如</div>
                                    <table cellpadding="0" cellspacing="0">
                                      <tbody>
                                        <tr>
                                          <td style="padding-left:5px;"><div class="ques_cont">
                                              <div class="ques_cont_in"><img alt="" src="http://media.tangce.cn:5080/Hsk_Media/streams/papers/3a62af7b158fd16b78c5fe8290234724/2013021319040231-35F.jpg" style="width: 174px; height: 45px;"></div>
                                            </div></td>
                                        </tr>
                                        <tr>
                                          <td style="padding-left:5px;"><div class="ques_in_list">
                                              <table cellpadding="0" cellspacing="0">
                                                <tbody>
                                                  <tr>
                                                    <td><!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="f942ef494f05d7a796fd8f8f0052775c" id="41e47cde83203afb68a7529bf77b3db0" type="radio" value="A" disabled="disabled">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="41e47cde83203afb68a7529bf77b3db0">A</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="41e47cde83203afb68a7529bf77b3db0"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="f942ef494f05d7a796fd8f8f0052775c" id="efb1c205ba2700a2c9318a27429ef5d7" type="radio" value="B" disabled="disabled">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="efb1c205ba2700a2c9318a27429ef5d7">B</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="efb1c205ba2700a2c9318a27429ef5d7"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="f942ef494f05d7a796fd8f8f0052775c" id="0a38f28a199af9c3df01ae914066e846" type="radio" value="C" disabled="disabled">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="0a38f28a199af9c3df01ae914066e846">C</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="0a38f28a199af9c3df01ae914066e846"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="f942ef494f05d7a796fd8f8f0052775c" id="64def15556cecd50be75e32fa0319ab2" type="radio" value="D" disabled="disabled">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="64def15556cecd50be75e32fa0319ab2">D</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="64def15556cecd50be75e32fa0319ab2"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="f942ef494f05d7a796fd8f8f0052775c" id="f1a0609e8aabbb4703c589ed954fbad8" type="radio" value="E" disabled="disabled">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="f1a0609e8aabbb4703c589ed954fbad8">E</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="f1a0609e8aabbb4703c589ed954fbad8"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="f942ef494f05d7a796fd8f8f0052775c" id="3b09dde3275cd5811eafe2d05051ede1" type="radio" value="F" disabled="disabled" checked="checked">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title s_chosed_t" optid="3b09dde3275cd5811eafe2d05051ede1">F</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont s_chosed" optid="3b09dde3275cd5811eafe2d05051ede1"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table></td>
                                                  </tr>
                                                </tbody>
                                              </table>
                                            </div></td>
                                        </tr>
                                      </tbody>
                                    </table>
                                  </div>
                                  <!-- 子试题遍历 -->
                                  <!-- 子试题的内容 -->
                                  <div class="ans_out sub_ans_out" example="no" ques_ans="C" que_type="" que_id="4b7971681ba62635719b606b28b5553a" quz_list="31">
                                    <div class="ques_num">31</div>
                                    <table cellpadding="0" cellspacing="0">
                                      <tbody>
                                        <tr>
                                          <td style="padding-left:5px;"><div class="ques_cont">
                                              <div class="ques_cont_in"><img alt="" src="http://media.tangce.cn:5080/Hsk_Media/streams/papers/3a62af7b158fd16b78c5fe8290234724/2013021319043131.jpg" style="width: 230px; height: 45px"></div>
                                            </div></td>
                                        </tr>
                                        <tr>
                                          <td style="padding-left:5px;"><div class="ques_in_list">
                                              <table cellpadding="0" cellspacing="0">
                                                <tbody>
                                                  <tr>
                                                    <td><!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="4b7971681ba62635719b606b28b5553a" id="28b5163f1872539730866301b61736bb" type="radio" value="A">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="28b5163f1872539730866301b61736bb">A</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="28b5163f1872539730866301b61736bb"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="4b7971681ba62635719b606b28b5553a" id="af7b6f1bd2fc4d6db2ecf48a5645a8bf" type="radio" value="B">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="af7b6f1bd2fc4d6db2ecf48a5645a8bf">B</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="af7b6f1bd2fc4d6db2ecf48a5645a8bf"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="4b7971681ba62635719b606b28b5553a" id="ebc6bdfa0b09541d03917a62f3f4b7db" type="radio" value="C">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="ebc6bdfa0b09541d03917a62f3f4b7db">C</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="ebc6bdfa0b09541d03917a62f3f4b7db"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="4b7971681ba62635719b606b28b5553a" id="1d2f7d7280d99252feb38e7f74c8b17f" type="radio" value="D">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="1d2f7d7280d99252feb38e7f74c8b17f">D</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="1d2f7d7280d99252feb38e7f74c8b17f"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="4b7971681ba62635719b606b28b5553a" id="cc3f9875c6c192049fe87a0685102e57" type="radio" value="E">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="cc3f9875c6c192049fe87a0685102e57">E</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="cc3f9875c6c192049fe87a0685102e57"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="4b7971681ba62635719b606b28b5553a" id="869afd323f1eab131f049fe618c31046" type="radio" value="F">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="869afd323f1eab131f049fe618c31046">F</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="869afd323f1eab131f049fe618c31046"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table></td>
                                                  </tr>
                                                </tbody>
                                              </table>
                                            </div></td>
                                        </tr>
                                      </tbody>
                                    </table>
                                  </div>
                                  <!-- 子试题遍历 -->
                                  <!-- 子试题的内容 -->
                                  <div class="ans_out sub_ans_out" example="no" ques_ans="B" que_type="" que_id="a90a1bdf002c351f7f125c71b552268a" quz_list="32">
                                    <div class="ques_num">32</div>
                                    <table cellpadding="0" cellspacing="0">
                                      <tbody>
                                        <tr>
                                          <td style="padding-left:5px;"><div class="ques_cont">
                                              <div class="ques_cont_in"><img alt="" src="http://media.tangce.cn:5080/Hsk_Media/streams/papers/3a62af7b158fd16b78c5fe8290234724/2013021319044932.jpg" style="width: 327px; height: 50px"></div>
                                            </div></td>
                                        </tr>
                                        <tr>
                                          <td style="padding-left:5px;"><div class="ques_in_list">
                                              <table cellpadding="0" cellspacing="0">
                                                <tbody>
                                                  <tr>
                                                    <td><!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="a90a1bdf002c351f7f125c71b552268a" id="294d71c1c5fb9e8fe5b6c58adcdfe762" type="radio" value="A">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="294d71c1c5fb9e8fe5b6c58adcdfe762">A</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="294d71c1c5fb9e8fe5b6c58adcdfe762"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="a90a1bdf002c351f7f125c71b552268a" id="6e03f077e7947e91e3235bcc902c6e04" type="radio" value="B">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="6e03f077e7947e91e3235bcc902c6e04">B</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="6e03f077e7947e91e3235bcc902c6e04"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="a90a1bdf002c351f7f125c71b552268a" id="da2037b35959db8a38b8edfaf5e74a57" type="radio" value="C">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="da2037b35959db8a38b8edfaf5e74a57">C</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="da2037b35959db8a38b8edfaf5e74a57"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="a90a1bdf002c351f7f125c71b552268a" id="d94da9b5546dc05f71a6a5ceb4c527c3" type="radio" value="D">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="d94da9b5546dc05f71a6a5ceb4c527c3">D</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="d94da9b5546dc05f71a6a5ceb4c527c3"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="a90a1bdf002c351f7f125c71b552268a" id="90d9ec573c60e2073ed81ba5f0f29aee" type="radio" value="E">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="90d9ec573c60e2073ed81ba5f0f29aee">E</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="90d9ec573c60e2073ed81ba5f0f29aee"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="a90a1bdf002c351f7f125c71b552268a" id="eb72f9654ffd3bfe0748481fa8a87beb" type="radio" value="F">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="eb72f9654ffd3bfe0748481fa8a87beb">F</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="eb72f9654ffd3bfe0748481fa8a87beb"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table></td>
                                                  </tr>
                                                </tbody>
                                              </table>
                                            </div></td>
                                        </tr>
                                      </tbody>
                                    </table>
                                  </div>
                                  <!-- 子试题遍历 -->
                                  <!-- 子试题的内容 -->
                                  <div class="ans_out sub_ans_out" example="no" ques_ans="E" que_type="" que_id="a82d5a96378c93ec0a7e799f2e103948" quz_list="33">
                                    <div class="ques_num">33</div>
                                    <table cellpadding="0" cellspacing="0">
                                      <tbody>
                                        <tr>
                                          <td style="padding-left:5px;"><div class="ques_cont">
                                              <div class="ques_cont_in"><img alt="" src="http://media.tangce.cn:5080/Hsk_Media/streams/papers/3a62af7b158fd16b78c5fe8290234724/2013021319050833.jpg" style="width: 229px; height: 53px"></div>
                                            </div></td>
                                        </tr>
                                        <tr>
                                          <td style="padding-left:5px;"><div class="ques_in_list">
                                              <table cellpadding="0" cellspacing="0">
                                                <tbody>
                                                  <tr>
                                                    <td><!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="a82d5a96378c93ec0a7e799f2e103948" id="32e6ffe56464674efa0f10d85a83d1a2" type="radio" value="A">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="32e6ffe56464674efa0f10d85a83d1a2">A</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="32e6ffe56464674efa0f10d85a83d1a2"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="a82d5a96378c93ec0a7e799f2e103948" id="b5447e61356ff90431dfd574e309bed9" type="radio" value="B">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="b5447e61356ff90431dfd574e309bed9">B</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="b5447e61356ff90431dfd574e309bed9"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="a82d5a96378c93ec0a7e799f2e103948" id="fc47f4d544c9edf71e0aeeae4422af6c" type="radio" value="C">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="fc47f4d544c9edf71e0aeeae4422af6c">C</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="fc47f4d544c9edf71e0aeeae4422af6c"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="a82d5a96378c93ec0a7e799f2e103948" id="1067d1df21ce3c0f802d7050db434edd" type="radio" value="D">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="1067d1df21ce3c0f802d7050db434edd">D</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="1067d1df21ce3c0f802d7050db434edd"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="a82d5a96378c93ec0a7e799f2e103948" id="19a383d69a6b619c6a4494e6ad1d6a70" type="radio" value="E">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="19a383d69a6b619c6a4494e6ad1d6a70">E</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="19a383d69a6b619c6a4494e6ad1d6a70"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="a82d5a96378c93ec0a7e799f2e103948" id="25717743da7960823c391b17e4dcbecc" type="radio" value="F">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="25717743da7960823c391b17e4dcbecc">F</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="25717743da7960823c391b17e4dcbecc"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table></td>
                                                  </tr>
                                                </tbody>
                                              </table>
                                            </div></td>
                                        </tr>
                                      </tbody>
                                    </table>
                                  </div>
                                  <!-- 子试题遍历 -->
                                  <!-- 子试题的内容 -->
                                  <div class="ans_out sub_ans_out" example="no" ques_ans="D" que_type="" que_id="f83e0ae2ad896cbbd746bd7be1fa562b" quz_list="34">
                                    <div class="ques_num">34</div>
                                    <table cellpadding="0" cellspacing="0">
                                      <tbody>
                                        <tr>
                                          <td style="padding-left:5px;"><div class="ques_cont">
                                              <div class="ques_cont_in"><img alt="" src="http://media.tangce.cn:5080/Hsk_Media/streams/papers/3a62af7b158fd16b78c5fe8290234724/2013021319052634.jpg" style="width: 232px; height: 49px"></div>
                                            </div></td>
                                        </tr>
                                        <tr>
                                          <td style="padding-left:5px;"><div class="ques_in_list">
                                              <table cellpadding="0" cellspacing="0">
                                                <tbody>
                                                  <tr>
                                                    <td><!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="f83e0ae2ad896cbbd746bd7be1fa562b" id="666dcc5172dc52c101828f087201e1f7" type="radio" value="A">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="666dcc5172dc52c101828f087201e1f7">A</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="666dcc5172dc52c101828f087201e1f7"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="f83e0ae2ad896cbbd746bd7be1fa562b" id="cefef791909304c8889da8f0cd2be87f" type="radio" value="B">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="cefef791909304c8889da8f0cd2be87f">B</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="cefef791909304c8889da8f0cd2be87f"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="f83e0ae2ad896cbbd746bd7be1fa562b" id="6a0bfbfa5b37c58eaf6607e6325ed550" type="radio" value="C">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="6a0bfbfa5b37c58eaf6607e6325ed550">C</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="6a0bfbfa5b37c58eaf6607e6325ed550"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="f83e0ae2ad896cbbd746bd7be1fa562b" id="b7c9ff212544b56ac323b531beabd367" type="radio" value="D">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="b7c9ff212544b56ac323b531beabd367">D</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="b7c9ff212544b56ac323b531beabd367"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="f83e0ae2ad896cbbd746bd7be1fa562b" id="fb152be73cbc9fdd2ed6c40e2b1fda83" type="radio" value="E">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="fb152be73cbc9fdd2ed6c40e2b1fda83">E</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="fb152be73cbc9fdd2ed6c40e2b1fda83"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="f83e0ae2ad896cbbd746bd7be1fa562b" id="c13300922f650bfbecf928ec4b8d38c9" type="radio" value="F">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="c13300922f650bfbecf928ec4b8d38c9">F</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="c13300922f650bfbecf928ec4b8d38c9"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table></td>
                                                  </tr>
                                                </tbody>
                                              </table>
                                            </div></td>
                                        </tr>
                                      </tbody>
                                    </table>
                                  </div>
                                  <!-- 子试题遍历 -->
                                  <!-- 子试题的内容 -->
                                  <div class="ans_out sub_ans_out" example="no" ques_ans="A" que_type="" que_id="71340b7f292330992b2b62d3b64d040c" quz_list="35">
                                    <div class="ques_num">35</div>
                                    <table cellpadding="0" cellspacing="0">
                                      <tbody>
                                        <tr>
                                          <td style="padding-left:5px;"><div class="ques_cont">
                                              <div class="ques_cont_in"><img alt="" src="http://media.tangce.cn:5080/Hsk_Media/streams/papers/3a62af7b158fd16b78c5fe8290234724/2013021319054235.jpg" style="width: 260px; height: 48px"></div>
                                            </div></td>
                                        </tr>
                                        <tr>
                                          <td style="padding-left:5px;"><div class="ques_in_list">
                                              <table cellpadding="0" cellspacing="0">
                                                <tbody>
                                                  <tr>
                                                    <td><!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="71340b7f292330992b2b62d3b64d040c" id="048ba6d557b29f2312d254e430df5e2e" type="radio" value="A">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="048ba6d557b29f2312d254e430df5e2e">A</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="048ba6d557b29f2312d254e430df5e2e"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="71340b7f292330992b2b62d3b64d040c" id="44400c7882a7caf7a09a7fb5c5ebc5f1" type="radio" value="B">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="44400c7882a7caf7a09a7fb5c5ebc5f1">B</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="44400c7882a7caf7a09a7fb5c5ebc5f1"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="71340b7f292330992b2b62d3b64d040c" id="a1e42f07e7dfaefc54df6d2e7bd4a07b" type="radio" value="C">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="a1e42f07e7dfaefc54df6d2e7bd4a07b">C</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="a1e42f07e7dfaefc54df6d2e7bd4a07b"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="71340b7f292330992b2b62d3b64d040c" id="a73dd19f818a79c88fb3482252900103" type="radio" value="D">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="a73dd19f818a79c88fb3482252900103">D</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="a73dd19f818a79c88fb3482252900103"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="71340b7f292330992b2b62d3b64d040c" id="1c0a296ea60a14c25f9b150c39cc2b6e" type="radio" value="E">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="1c0a296ea60a14c25f9b150c39cc2b6e">E</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="1c0a296ea60a14c25f9b150c39cc2b6e"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="71340b7f292330992b2b62d3b64d040c" id="294e1931697d83a010a784f105295305" type="radio" value="F">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="294e1931697d83a010a784f105295305">F</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="294e1931697d83a010a784f105295305"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table></td>
                                                  </tr>
                                                </tbody>
                                              </table>
                                            </div></td>
                                        </tr>
                                      </tbody>
                                    </table>
                                  </div>
                                </div></td>
                            </tr>
                          </tbody>
                        </table>
                      </div>
                    </div>
                  </div>
                </div>
                <!-- 遍历部分 -->
                <!-- 小节遍历 -->
                <!-- 部分外框 -->
                <div id="part_92868ee64b9841721aa1de85c68fc37c" part_num="92868ee64b9841721aa1de85c68fc37c" class="nav_part" style="display: none;">
                  <!-- 根据不同级别显示例题 -->
                  <div class="examp_btn">
                    <div class="left"></div>
                    <div class="middle"><a href="#" onFocus="this.blur()">隐藏示例</a></div>
                    <div class="right"></div>
                    <div class="examp_data">1</div>
                  </div>
                  <!-- 输出小节标题 -->
                  <div class="quz_info">36-40.</div>
                  <div style="clear: both;"></div>
                  <!-- 试题集合遍历 -->
                  <div class="examp_out"></div>
                  <div>
                    <div style="clear:both;"></div>
                    <div class="ques_list" example="no" id="quz_36-40" que_id="97ef87d478c462ccc7260d8041bde8d2" que_num="36-40" ismix="yes" style="display:none;">
                      <div class="ques_num ques_real" que_id="97ef87d478c462ccc7260d8041bde8d2" que_num="36-40">
                        <div>36-40</div>
                      </div>
                      <!-- 试题题干 -->
                      <div class="hTable" style="float:none;overflow:hidden;">
                        <table cellpadding="0" cellspacing="0" width="100%">
                          <tbody>
                            <tr>
                              <td valign="top"><div class="hCols" style="height: 761px;">
                                  <div class="ques_cont">
                                    <div class="ques_cont_in"><img alt="" src="http://media.tangce.cn:5080/Hsk_Media/streams/papers/3a62af7b158fd16b78c5fe8290234724/2013021319110036-40.jpg" style="width: 131px; height: 380px"></div>
                                  </div>
                                </div></td>
                              <td valign="top" style="border-left:1px solid #a3d1e3; padding-top:2px;;min-width:320px;"><div class="hCols" style="height: 761px;">
                                  <!-- 子试题遍历 -->
                                  <!-- 子试题的内容 -->
                                  <div class="ans_out sub_ans_out" example="yes" ques_ans="D" que_type="" que_id="04f5a30d7ae20413454b4aa3849315c3" quz_list="例如">
                                    <div class="ques_num" style="font-size: 16px;">例如</div>
                                    <table cellpadding="0" cellspacing="0">
                                      <tbody>
                                        <tr>
                                          <td style="padding-left:5px;"><div class="ques_cont">
                                              <div class="ques_cont_in"><img alt="" src="http://media.tangce.cn:5080/Hsk_Media/streams/papers/3a62af7b158fd16b78c5fe8290234724/2013021319112636-40D.jpg" style="width: 249px; height: 49px;"></div>
                                            </div></td>
                                        </tr>
                                        <tr>
                                          <td style="padding-left:5px;"><div class="ques_in_list">
                                              <table cellpadding="0" cellspacing="0">
                                                <tbody>
                                                  <tr>
                                                    <td><!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="04f5a30d7ae20413454b4aa3849315c3" id="d74262872cc5d61242d2faf47b795703" type="radio" value="A" disabled="disabled">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="d74262872cc5d61242d2faf47b795703">A</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="d74262872cc5d61242d2faf47b795703"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="04f5a30d7ae20413454b4aa3849315c3" id="daddb724916bc22a84bf6e2ada6a9bd0" type="radio" value="B" disabled="disabled">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="daddb724916bc22a84bf6e2ada6a9bd0">B</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="daddb724916bc22a84bf6e2ada6a9bd0"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="04f5a30d7ae20413454b4aa3849315c3" id="d4b2e5b134a4b80c0fb2e903e7079c45" type="radio" value="C" disabled="disabled">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="d4b2e5b134a4b80c0fb2e903e7079c45">C</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="d4b2e5b134a4b80c0fb2e903e7079c45"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="04f5a30d7ae20413454b4aa3849315c3" id="e0dfe74dd5299530471f0b603cfbe951" type="radio" value="D" disabled="disabled" checked="checked">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title s_chosed_t" optid="e0dfe74dd5299530471f0b603cfbe951">D</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont s_chosed" optid="e0dfe74dd5299530471f0b603cfbe951"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="04f5a30d7ae20413454b4aa3849315c3" id="6500668b5199c94ba7f29baed5b7f816" type="radio" value="E" disabled="disabled">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="6500668b5199c94ba7f29baed5b7f816">E</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="6500668b5199c94ba7f29baed5b7f816"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="04f5a30d7ae20413454b4aa3849315c3" id="1dc37dfac8569de1bbe3c83abaa5984c" type="radio" value="F" disabled="disabled">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="1dc37dfac8569de1bbe3c83abaa5984c">F</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="1dc37dfac8569de1bbe3c83abaa5984c"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table></td>
                                                  </tr>
                                                </tbody>
                                              </table>
                                            </div></td>
                                        </tr>
                                      </tbody>
                                    </table>
                                  </div>
                                  <!-- 子试题遍历 -->
                                  <!-- 子试题的内容 -->
                                  <div class="ans_out sub_ans_out" example="no" ques_ans="F" que_type="" que_id="6a20c6345f7c1d2e03af0522c178b134" quz_list="36">
                                    <div class="ques_num">36</div>
                                    <table cellpadding="0" cellspacing="0">
                                      <tbody>
                                        <tr>
                                          <td style="padding-left:5px;"><div class="ques_cont">
                                              <div class="ques_cont_in"><img alt="" src="http://media.tangce.cn:5080/Hsk_Media/streams/papers/3a62af7b158fd16b78c5fe8290234724/2013021319114636.jpg"></div>
                                            </div></td>
                                        </tr>
                                        <tr>
                                          <td style="padding-left:5px;"><div class="ques_in_list">
                                              <table cellpadding="0" cellspacing="0">
                                                <tbody>
                                                  <tr>
                                                    <td><!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="6a20c6345f7c1d2e03af0522c178b134" id="c7e4b52843b26dcf27142837a01c3686" type="radio" value="A">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="c7e4b52843b26dcf27142837a01c3686">A</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="c7e4b52843b26dcf27142837a01c3686"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="6a20c6345f7c1d2e03af0522c178b134" id="818fa7f8d3721bdbbe4d7f6f3a953086" type="radio" value="B">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="818fa7f8d3721bdbbe4d7f6f3a953086">B</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="818fa7f8d3721bdbbe4d7f6f3a953086"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="6a20c6345f7c1d2e03af0522c178b134" id="a1599517100d26c87e38d497290a6174" type="radio" value="C">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="a1599517100d26c87e38d497290a6174">C</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="a1599517100d26c87e38d497290a6174"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="6a20c6345f7c1d2e03af0522c178b134" id="3d66122dcf22909ea367451eda14ed48" type="radio" value="D">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="3d66122dcf22909ea367451eda14ed48">D</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="3d66122dcf22909ea367451eda14ed48"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="6a20c6345f7c1d2e03af0522c178b134" id="d3f19d25ea71df082631e5cdec902de3" type="radio" value="E">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="d3f19d25ea71df082631e5cdec902de3">E</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="d3f19d25ea71df082631e5cdec902de3"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="6a20c6345f7c1d2e03af0522c178b134" id="1da066e816f30272ad72d40c957373f7" type="radio" value="F">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="1da066e816f30272ad72d40c957373f7">F</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="1da066e816f30272ad72d40c957373f7"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table></td>
                                                  </tr>
                                                </tbody>
                                              </table>
                                            </div></td>
                                        </tr>
                                      </tbody>
                                    </table>
                                  </div>
                                  <!-- 子试题遍历 -->
                                  <!-- 子试题的内容 -->
                                  <div class="ans_out sub_ans_out" example="no" ques_ans="B" que_type="" que_id="15b453fbcee9205fc2442c3785758d69" quz_list="37">
                                    <div class="ques_num">37</div>
                                    <table cellpadding="0" cellspacing="0">
                                      <tbody>
                                        <tr>
                                          <td style="padding-left:5px;"><div class="ques_cont">
                                              <div class="ques_cont_in"><img alt="" src="http://media.tangce.cn:5080/Hsk_Media/streams/papers/3a62af7b158fd16b78c5fe8290234724/2013021319121637.jpg" style="width: 375px; height: 52px"></div>
                                            </div></td>
                                        </tr>
                                        <tr>
                                          <td style="padding-left:5px;"><div class="ques_in_list">
                                              <table cellpadding="0" cellspacing="0">
                                                <tbody>
                                                  <tr>
                                                    <td><!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="15b453fbcee9205fc2442c3785758d69" id="b1337b01a3953d1f08f010d14e93d009" type="radio" value="A">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="b1337b01a3953d1f08f010d14e93d009">A</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="b1337b01a3953d1f08f010d14e93d009"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="15b453fbcee9205fc2442c3785758d69" id="e8ca25f9f1ec7d0525ac7298d747219e" type="radio" value="B">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="e8ca25f9f1ec7d0525ac7298d747219e">B</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="e8ca25f9f1ec7d0525ac7298d747219e"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="15b453fbcee9205fc2442c3785758d69" id="d8e88e7ab311dc5a74c77ec783184c60" type="radio" value="C">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="d8e88e7ab311dc5a74c77ec783184c60">C</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="d8e88e7ab311dc5a74c77ec783184c60"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="15b453fbcee9205fc2442c3785758d69" id="6cc72234be0c5fc59c3edf64ed4b914e" type="radio" value="D">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="6cc72234be0c5fc59c3edf64ed4b914e">D</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="6cc72234be0c5fc59c3edf64ed4b914e"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="15b453fbcee9205fc2442c3785758d69" id="8078c377eedda62a9cba638d5ef100de" type="radio" value="E">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="8078c377eedda62a9cba638d5ef100de">E</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="8078c377eedda62a9cba638d5ef100de"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="15b453fbcee9205fc2442c3785758d69" id="1c371ea9e7ea6749079a37efb1fc54c3" type="radio" value="F">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="1c371ea9e7ea6749079a37efb1fc54c3">F</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="1c371ea9e7ea6749079a37efb1fc54c3"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table></td>
                                                  </tr>
                                                </tbody>
                                              </table>
                                            </div></td>
                                        </tr>
                                      </tbody>
                                    </table>
                                  </div>
                                  <!-- 子试题遍历 -->
                                  <!-- 子试题的内容 -->
                                  <div class="ans_out sub_ans_out" example="no" ques_ans="E" que_type="" que_id="1fe1d52b30a25624a9cc8597087facf1" quz_list="38">
                                    <div class="ques_num">38</div>
                                    <table cellpadding="0" cellspacing="0">
                                      <tbody>
                                        <tr>
                                          <td style="padding-left:5px;"><div class="ques_cont">
                                              <div class="ques_cont_in"><img alt="" src="http://media.tangce.cn:5080/Hsk_Media/streams/papers/3a62af7b158fd16b78c5fe8290234724/2013031511444838.jpg" style="width: 426px; height: 45px"></div>
                                            </div></td>
                                        </tr>
                                        <tr>
                                          <td style="padding-left:5px;"><div class="ques_in_list">
                                              <table cellpadding="0" cellspacing="0">
                                                <tbody>
                                                  <tr>
                                                    <td><!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="1fe1d52b30a25624a9cc8597087facf1" id="aae79baef633eeecb38c5ed7e1429f16" type="radio" value="A">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="aae79baef633eeecb38c5ed7e1429f16">A</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="aae79baef633eeecb38c5ed7e1429f16"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="1fe1d52b30a25624a9cc8597087facf1" id="072366e74243265b3672eb7b29929902" type="radio" value="B">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="072366e74243265b3672eb7b29929902">B</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="072366e74243265b3672eb7b29929902"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="1fe1d52b30a25624a9cc8597087facf1" id="e93e11f79c5caf322256549a8800d7af" type="radio" value="C">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="e93e11f79c5caf322256549a8800d7af">C</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="e93e11f79c5caf322256549a8800d7af"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="1fe1d52b30a25624a9cc8597087facf1" id="0a036cc24b91752ed5d5237a14f5ebf6" type="radio" value="D">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="0a036cc24b91752ed5d5237a14f5ebf6">D</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="0a036cc24b91752ed5d5237a14f5ebf6"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="1fe1d52b30a25624a9cc8597087facf1" id="7f59b68599f2e4eb474d578e3fbbd895" type="radio" value="E">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="7f59b68599f2e4eb474d578e3fbbd895">E</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="7f59b68599f2e4eb474d578e3fbbd895"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="1fe1d52b30a25624a9cc8597087facf1" id="e2a3252f187a0388d5c72c90fb9bb4b7" type="radio" value="F">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="e2a3252f187a0388d5c72c90fb9bb4b7">F</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="e2a3252f187a0388d5c72c90fb9bb4b7"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table></td>
                                                  </tr>
                                                </tbody>
                                              </table>
                                            </div></td>
                                        </tr>
                                      </tbody>
                                    </table>
                                  </div>
                                  <!-- 子试题遍历 -->
                                  <!-- 子试题的内容 -->
                                  <div class="ans_out sub_ans_out" example="no" ques_ans="A" que_type="" que_id="8d7db2bafdd33a7bdde2ef6406f0c15d" quz_list="39">
                                    <div class="ques_num">39</div>
                                    <table cellpadding="0" cellspacing="0">
                                      <tbody>
                                        <tr>
                                          <td style="padding-left:5px;"><div class="ques_cont">
                                              <div class="ques_cont_in"><img alt="" src="http://media.tangce.cn:5080/Hsk_Media/streams/papers/3a62af7b158fd16b78c5fe8290234724/2013031511461039.jpg"></div>
                                            </div></td>
                                        </tr>
                                        <tr>
                                          <td style="padding-left:5px;"><div class="ques_in_list">
                                              <table cellpadding="0" cellspacing="0">
                                                <tbody>
                                                  <tr>
                                                    <td><!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="8d7db2bafdd33a7bdde2ef6406f0c15d" id="bfc341beaca9a809732eeffa44138ca4" type="radio" value="A">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="bfc341beaca9a809732eeffa44138ca4">A</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="bfc341beaca9a809732eeffa44138ca4"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="8d7db2bafdd33a7bdde2ef6406f0c15d" id="15d7d85bf0fff8d54bf8c4ee777a94be" type="radio" value="B">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="15d7d85bf0fff8d54bf8c4ee777a94be">B</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="15d7d85bf0fff8d54bf8c4ee777a94be"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="8d7db2bafdd33a7bdde2ef6406f0c15d" id="ccd17c55a42f0401f34837b15e39b18f" type="radio" value="C">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="ccd17c55a42f0401f34837b15e39b18f">C</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="ccd17c55a42f0401f34837b15e39b18f"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="8d7db2bafdd33a7bdde2ef6406f0c15d" id="8052c42d90a22eb7f181f171bfbb3a52" type="radio" value="D">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="8052c42d90a22eb7f181f171bfbb3a52">D</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="8052c42d90a22eb7f181f171bfbb3a52"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="8d7db2bafdd33a7bdde2ef6406f0c15d" id="fb81d5673354bd528d85d90c25e25e8a" type="radio" value="E">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="fb81d5673354bd528d85d90c25e25e8a">E</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="fb81d5673354bd528d85d90c25e25e8a"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="8d7db2bafdd33a7bdde2ef6406f0c15d" id="8bf551c70c2bea1a8ea99e9f17cdd7a6" type="radio" value="F">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="8bf551c70c2bea1a8ea99e9f17cdd7a6">F</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="8bf551c70c2bea1a8ea99e9f17cdd7a6"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table></td>
                                                  </tr>
                                                </tbody>
                                              </table>
                                            </div></td>
                                        </tr>
                                      </tbody>
                                    </table>
                                  </div>
                                  <!-- 子试题遍历 -->
                                  <!-- 子试题的内容 -->
                                  <div class="ans_out sub_ans_out" example="no" ques_ans="C" que_type="" que_id="a01e2527d6b15b3d94998933a9358fa2" quz_list="40">
                                    <div class="ques_num">40</div>
                                    <table cellpadding="0" cellspacing="0">
                                      <tbody>
                                        <tr>
                                          <td style="padding-left:5px;"><div class="ques_cont">
                                              <div class="ques_cont_in"><img alt="" src="http://media.tangce.cn:5080/Hsk_Media/streams/papers/3a62af7b158fd16b78c5fe8290234724/2013021319130940.jpg" style="width: 346px; height: 141px"></div>
                                            </div></td>
                                        </tr>
                                        <tr>
                                          <td style="padding-left:5px;"><div class="ques_in_list">
                                              <table cellpadding="0" cellspacing="0">
                                                <tbody>
                                                  <tr>
                                                    <td><!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="a01e2527d6b15b3d94998933a9358fa2" id="891a54a48d14ed8ab27d9f9b4ba5cb3c" type="radio" value="A">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="891a54a48d14ed8ab27d9f9b4ba5cb3c">A</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="891a54a48d14ed8ab27d9f9b4ba5cb3c"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="a01e2527d6b15b3d94998933a9358fa2" id="6ca2a16cecb61ba2ac42644b9b4b042c" type="radio" value="B">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="6ca2a16cecb61ba2ac42644b9b4b042c">B</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="6ca2a16cecb61ba2ac42644b9b4b042c"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="a01e2527d6b15b3d94998933a9358fa2" id="55fbafcf1a8be3644ce25123c3f91060" type="radio" value="C">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="55fbafcf1a8be3644ce25123c3f91060">C</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="55fbafcf1a8be3644ce25123c3f91060"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="a01e2527d6b15b3d94998933a9358fa2" id="09eb9ec59997ebc27fd97dbbada3c7ff" type="radio" value="D">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="09eb9ec59997ebc27fd97dbbada3c7ff">D</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="09eb9ec59997ebc27fd97dbbada3c7ff"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="a01e2527d6b15b3d94998933a9358fa2" id="ef75428ce2a48e592e726f3494d3763d" type="radio" value="E">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="ef75428ce2a48e592e726f3494d3763d">E</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="ef75428ce2a48e592e726f3494d3763d"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                      <!-- 选项遍历 -->
                                                      <table cellpadding="0" cellspacing="0" style="float:left;">
                                                        <tbody>
                                                          <tr>
                                                            <td class="row_opt"><input class="s_chose" name="a01e2527d6b15b3d94998933a9358fa2" id="bf2dfee54ccd961a42e9198284ea93e6" type="radio" value="F">
                                                            </td>
                                                            <td class="row_opt"><span class="opt_title" optid="bf2dfee54ccd961a42e9198284ea93e6">F</span> </td>
                                                            <td>．</td>
                                                            <td><span class="opt_cont" optid="bf2dfee54ccd961a42e9198284ea93e6"></span></td>
                                                          </tr>
                                                        </tbody>
                                                      </table></td>
                                                  </tr>
                                                </tbody>
                                              </table>
                                            </div></td>
                                        </tr>
                                      </tbody>
                                    </table>
                                  </div>
                                </div></td>
                            </tr>
                          </tbody>
                        </table>
                      </div>
                    </div>
                  </div>
                </div>
                <!-- 右侧试题列表结束 -->
              </div>
              <div style="clear: both;"></div>
              <div class="page_btn"> <a class="next_part" href="#" onFocus="this.blur()" style="display: none;">下一部分</a> <a class="next_quz" href="#" onFocus="this.blur()">下一题</a> <a class="prev_quz" href="#" onFocus="this.blur()" style="display: none;">上一题</a> </div>
            </div>
          </div></td>
      </tr>
    </tbody>
  </table>
  <!--考试提示-->
  <div class="cont_outer" id="tishi" style="display: none; height: 874px; width: 884px;">
    <div class="cont_tit">
      <div class="cont_tit_in">考试提示</div>
    </div>
    <div class="cont_tr"></div>
    <div class="cont_br"></div>
    <div class="cont_bl"></div>
    <div style="position: absolute; right: 20px; top: 15px;"> <a href="#" id="close2"><img src="../images/hsk_023.png" width="40" height="40"></a> </div>
    <div class="kc_cont" style="overflow-y: auto; overflow-x: hidden; height: 754px; width: 804px;"> <strong>HSK一级（分2部分共24题，时间约19分钟）：</strong><br>
      1. 听力（8题，约6分钟）<br>
      2. 阅读（16题，13分钟）<br>
    </div>
  </div>
  <!--考场须知-->
  <div class="cont_outer" id="xuzhi" style="display: none; height: 874px; width: 884px;">
    <div class="cont_tit">
      <div class="cont_tit_in">考场须知</div>
    </div>
    <div class="cont_tr"></div>
    <div class="cont_br"></div>
    <div class="cont_bl"></div>
    <div style="position: absolute; right: 20px; top: 15px;"> <a href="#" id="close"><img src="../images/hsk_023.png" width="40" height="40"></a> </div>
    <div class="kc_cont" style="overflow-y: auto; overflow-x: hidden; height: 754px; width: 804px;">
      <ol>
        <li>在考试开始前30分钟开始入场；在听力考试结束前到达考场的考生，可待阅读部分开始时参加考试，所误时间不补；在阅读考试开始后，迟到的考生不得进入考场参加考试。</li>
        <li>考生进考场时须出示准考证和报名时提供的规定身份证件，身份证件上的姓名必须与准考证上登记的姓名完全一致，证件上的照片必须是可以确认的考生本人，即照片与本人面貌一致。进入考场后，考生须将准考证和身份证件放在桌子的右上方，以备主、监考随时检查。入场时无法提供规定证件或持任何假证件的考生，将被拒绝参加考试，不退还考试费用。</li>
        <li>考试中途一般不得离场，如有特殊原因，考生需要中途离场，须经主考同意，在离开考场前把准考证交给主考官,考生返回考场需出示身份证件。</li>
        <li>考试过程中不允许吃食品及饮用饮料；考试过程中，如果任何考生存在作弊行为，例如：替考、剽窃、抄袭、考试过程中夹带或偷看相关材料等，主考官有权拒绝考生继续考试，或记录在主考报告上，汉考国际有权取消其考试成绩，并保留拒绝该考生参加国家汉办/孔子学院总部所有考试的权利。</li>
        <ol>
          <li style="list-style-type: disc;">由于考场管理失误造成考生受到不公平待遇，例如：考试时间不足、考试设备故障等，导致考生无法完成考试，汉考国际将尽快安排考生免费重新参加考试，不承担任何间接损失补偿。</li>
          <li style="list-style-type: disc;">由于不可抗力，例如：自然灾害、意外事故，迫使考试无法进行，汉考国际将尽快安排重试，或全额退回考试费用，不承担任何间接损失补偿。</li>
        </ol>
        <li>请考生认真按照步骤进行耳麦的调试，如有问题请即时向主考老师询问。作答书写题时，请严格按照答题纸上的要求进行作答。</li>
      </ol>
    </div>
  </div>
  <div style="clear: both"></div>
  <div class="part_date" style="display: none;"></div>
</div>
<div class="conf_bar" id="tipBeforeSubmit">
  <div class="conf_tit">提示</div>
  <div class="conf_txt"> [确认交卷]后将不能再返回试卷，<br>
    不能再修改答案，您确定要交卷吗？ </div>
  <div class="conf_btn"> <a href="#" class="no_btn">继续答题</a> <a href="#" class="yes_btn">确认交卷</a> </div>
</div>
<div class="conf_bar" id="tipSubmit">
  <div class="conf_tit">提示</div>
  <div class="conf_txt"> 正在提交试卷！<br>
    这可能需要一点儿时间<br>
    请不要关闭窗口，耐心等待。 </div>
</div>
<div class="info_bar">
  <div class="info_tit">提示</div>
  <div class="info_txt">未到下一部分开始时间！</div>
  <div class="info_btn"> <a href="#" class="sure_btn">确定</a> </div>
</div>
<div class="mask" style="height: 974px;"></div>
<div class="cont_btn" style="display: none;"></div>
<!-- 文件尾部添加作答记录 -->
<div id="ans_details" style="display:none"> </div>
<!-- 作答记录 -->
</body>
</html>
