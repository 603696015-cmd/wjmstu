<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<title>五矿发展员工职业发展系统--管理端--查看试卷信息</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript" src="js/jquery.js"></script>
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<style type="text/css">
.textbg6 {
	background-image: url(images/textbg.gif);
	padding-top: 4px;
	background-repeat: repeat-x;
	color: #FFFFFF;
	font-size: 13px;
	font-weight: bold;
	width: 65px;
	height: 20px;
	text-align: center;
	text-decoration: none;
}

td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((     this .     sectionRowIndex %     2 ==     0)
		?  
		  "#ffffff" :     "#f4f4f4" )
}
</style>
		<style type="text/css">
body {
	height: 100%;
	margin-top: 10px;
	margin-right: 0px;
	margin-bottom: 0px;
	margin-left: 0px;
	font-size: 14px;
	font-family: Arial, Helvetica, sans-serif;
	text-align: center;
	background-color: #878C93;
}

.main {
	background-color: #FFFFFF;
	width: 828px;
	margin-right: auto;
	margin-left: auto;
	margin-top:0px;
}

.regbutton3 {
	cursor: pointer;
	margin-top: 3px;
	margin-bottom: 2px;
	PADDING-RIGHT: 0px;
	PADDING-LEFT: 0px;
	FONT-SIZE: 18px;
	PADDING-BOTTOM: 0px;
	PADDING-TOP: 0px;
	FONT-FAMILY: "黑体" color :           #000;
	width: 135px;
	border: 0px;
	height: 40px;
	background-image: url(images/exam/btn_2.gif)
}

.title {
	font-family: Arial, Helvetica, sans-serif, "新宋体";
	font-size: 28px;
	width: 700px;
	font-weight: bolder;
	text-align: center;
	color: #f30;
	line-height: 40px;
}

.block {
	width: 100%;
	border: dotted 1px buttonface;
	text-align: center;
}

.block_name {
	width: 100%;
	font-weight: bolder;
	font-size: 20px;
	text-align: left;
}

.block_desc {
	width: 98%;
	background-color: #DDDDDD;
	font-size: 15px;
	height: 24px;
	border: solid 1px buttonface;
	padding-top: 5px;
	text-align: left;
}

.question {
	width: 95%;
	border: dotted 1px buttonface;
	text-align: left;
}

.question1 {
	width: 90%;
	border: dotted 1px buttonface;
	text-align: left;
	font: 12px;
}

.answer {
	background-color: #DDDDDD;
	padding-top: 5px;
	padding-bottom: 5px;
	border: solid 1px buttonface;
}

.menu {
	background-color: #f7f7f7;
	line-height: 22px;
	text-align: right;
	padding: 5px;
	position: absolute;
	width: 85px;
	top: 15px;
	visibility: visible;
	z-index: 4;
	border: green 1px solid;
	left: 86%;
}

.inputOver {
	height: 24px;
	padding: 2px 2 0 2;
	padding-top: 3px;
	border: 1px solid #dea303;
	background: url(images/exam/input_bg3_.jpg);
	font-size: 12px;
	color: #000;
	cursor: pointer;
}

.input {
	height: 24px;
	padding: 2px 2 0 2;
	padding-top: 3px;
	border: 1px solid #adb9c2;
	background: url(images/exam/input_bg3.jpg);
	font-size: 12px;
	color: #000;
	cursor: pointer;
}

p {
	margin: 3px;
}

.quizinfo {
	text-align: left;
	width: 710px;
}

.quizinfo .left,.right {
	float: left;
	width: 285px;
	padding: 5px 0px 10px 5px;
}

.info_ul {
	list-style-type: none;
	margin: 0px;
}
#nextInfo{
	width: 140px;
	height:120px;
	border: solid 1px #000;
	position: absolute;;
	top: 0px;
	right: 0px;
	background: #fff;
}
#nextInfo li{ margin-top: 10px;}
</style>
		<script type="text/javascript"> 
		
		function giveScore(id,obj,type){
			var array=obj.split(",");
			var temp =0;
			var len=array.length;
			var score=0;
			if(type=="avg"){//取平均分
				for(i=0;i<len;i++){				
					temp=temp+parseFloat(array[i],10);//这里要转换成float型，否则会当作字符串处理，这样在下面的除法计算时，就会报错
				}
				//document.getElementById(id).value = temp/len;
				//document.getElementById(id).value = Math.round(temp/(len-2)*100)/100 ;
				score = temp/len;
			} else if(type=="high"){//取最高分
				//document.getElementById(id).value = Math.max.apply(null, array);//最大值
				//document.getElementById(id).value = Math.min.apply(null, array);//最小值
				score = Math.max.apply(null, array);//最大值
			} else {//去掉最高最低分取平均分
				if(len>2){
					for(i=0;i<len;i++){
						if(array[i]!=Math.max.apply(null, array) && array[i]!=Math.min.apply(null, array)){
							temp=temp+parseFloat(array[i],10)
						}
					}
					//document.getElementById(id).value = Math.round(temp/(len-2)*10)/10 ;//取小数点后一位整数
					score = temp/(len-2);
				}else{
					alert("去掉后就没分了！");
					return ;
				}
			}
			score=score+"";
			if(score.indexOf(".")>0){
				if(score.indexOf(".")+3<=score.length){
					score=score.substring(0,score.indexOf(".")+3);
				}
			}
			document.getElementById(id).value=score;
		}
		var x = 0;
		$(window).ready(function(){
			$("#nextInfo").css("left",$("#main").offset().left+$("#main").width());
			$(window).scroll(function() {
				//$("#info").html($(window).scrollTop()) ; 
				$("#nextInfo").css("top",20+$(window).scrollTop());
				$("#nextInfo").css("left",$("#main").offset().left+$("#main").width());
			}); 
		}); 
			 
 </script>
	</HEAD>
	<body>
		<div class="main" id="main">
			<div class="quizinfo">
				<div class="left">
					<ul class="info_ul">
						<li>
							<span class="info">试卷名称：</span>
							<s:property value="examPaper.title" />
						</li>
						<li>
							<span class="info">有效时间始：</span>
							<s:date name="myExamPaper.examRoom.begintime"
								format="yyyy-MM-dd HH:mm:ss" />
						</li>
						<li>
							<span class="info">答题时间始：</span>
							<s:date name="myExamPaper.begintime" format="yyyy-MM-dd HH:mm:ss" />
						</li>
						<li>
							<span class="info">答题时间：</span>
							<s:property value="examPaper.during" />
							分钟
						</li>
						<li>
							<span class="info">试卷总分：</span>
							<s:property value="examPaper.ep_tscore" />
						</li>
						<li>
							<span class="info">考生姓名：</span>
							<s:property value="elUser.realname" />
						</li>
						<li>
							<span class="info">客观题得分：</span>
							<s:property value="examPaper.mepKscore" />
						</li>
						<li>
							<span class="info">身份证号：</span>
							<s:property value="elUser.shenfenzheng" />
						</li>
					</ul>
				</div>
				<div class="right">
					<ul class="info_ul">
						<li>
							<span class="info">出卷人：</span>
							<s:property value="examPaper.elUser.realname" />
						</li>
						<li>
							<span class="info"> 有效时间止：</span>
							<s:date name="myExamPaper.examRoom.endtime"
								format="yyyy-MM-dd HH:mm:ss" />
						</li>
						<li>
							<span class="info"> 答题时间止：</span>
							<s:date name="myExamPaper.endtime" format="yyyy-MM-dd HH:mm:ss" />
						</li>
						<li>
							<span class="info"> 考生耗时：</span>
							<s:property value="myExamPaper.passTimeStr" />
						</li>
						<li>
							<span class="info"> 通过分数：</span>
							<s:property value="examPaper.passScore" />
						</li>
						<li>
							<span class="info"> 考生成绩：</span>
							<s:property value="myExamPaper.myScore" />
						</li>
						<li>
							<span class="info"> 主观题得分：</span>
							<s:property value="examPaper.mepZscore" />
						</li>
						<li>
							<span class="info"> 通过考试：</span>
							<s:if test="myExamPaper.ispassed==1">是</s:if>
							<s:else>否</s:else>

						</li>
					</ul>
				</div>
				<div style="clear: both;"></div>
			</div>
			<!--<div style="width: 195px; float: left">
				<a href="/"><img src="images/exam/d_1.jpg" border="0"
						width="195" height="92" /> </a>
			</div>

			-->
			<!--<div
				style="background-image: url('images/exam/d_1_bg.jpg'); height: 92px; width: 552px; float: left; padding-top: 50px; text-align: center">
				考试时间：
				<s:property value="examPaper.during" />
				分钟 &nbsp;&nbsp;&nbsp; 考生：
				<s:property value="myExamPaper.tester.realname" />
				&nbsp;&nbsp;&nbsp; 试卷满分 ：
				<s:property value="examPaper.ep_tscore" />
				分 &nbsp;考生得分 ：
				<s:property value="examPaper.mep_tscore" />
				
			</div>
			-->
			<!--<div style="height: 92px; float: right; width: 81px;">
				<img src="images/exam/d_2.jpg" width="81" height="92" />
			</div>
			-->
			<div class="contentcenter">
				<!--<div style="text-align: center; width: 100%">
					客观题得分：<s:property value="examPaper.mepKscore" />
					客观题总分：
					<s:property value="examPaper.epKscore" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;主观题得分：<s:property value="examPaper.mepZscore" />
					主观题总分：
					<s:property value="examPaper.epZscore" />
				</div>-->
				<!--<div>
					<p class="title">
						&nbsp;
						<s:property value="examPaper.title" />
					</p>
				</div>
				<br />


				<div
					style="padding: 2px; border: 0px; width: 788px; margin: 4px 8 4 8; margin-top: 0px; padding-left: 10px; font-size: 12px;">
					<s:property value="examPaper.description" />
					<br>
					<hr width="760" size="1" noshade="noshade" class="line" />
				</div>
				<br />
			-->
			</div>
			<SCRIPT type="text/javascript">
	 function showBlocks(id){
		for(var i = 0 ; i < <s:property value="examPaper.epBlocks.size"/>;i++){
		document.getElementById("block_"+i).style.display="none";
		 document.getElementById("b_t_"+i).className ="input";
		}
		document.getElementById("b_t_a").className ="input";
		document.getElementById("block_"+id).style.display="block";
		document.getElementById("b_t_"+id).className ="inputOver";
	}
	function showAllBlocks( ){
		for(var i = 0 ; i < <s:property value="examPaper.epBlocks.size"/>;i++)
		{
		document.getElementById("block_"+i).style.display="block";
		document.getElementById("b_t_"+i).className ="input";
		}
	 	document.getElementById("b_t_a").className ="inputOver"; 
	}
	/*
	function isfor(){ //hwc 
		if(document.getElementById("Essayforid")!= null && document.getElementById("Officeforid") != null){
			var e = document.getElementById("Essayforid").value;
			var o = document.getElementById("Officeforid").value;
			if(e > 0 && o > 0){ 
				if(isNotNull(e) == 1 && isNotNull(o) == 1){
					exampaperread.submit();
				}
			}else{
				if(e > 0 || o > 0){  
					if(e > 0){
						alert(isNotNull(e));
						if(isNotNull(e))
							exampaperread.submit();
					}else{
						if(isNotNull(o))
							exampaperread.submit();
					}
				}else{
					exampaperread.submit(); 
				}
			}
		}else{
			alert("该试卷没有题目！");
			exampaperread.submit();
		}
	}
	*/
	/*
	function isfor(){
			var e = document.getElementById("Essayforid").value;
			var o = document.getElementById("Officeforid").value;
			var yesOrNoCount=document.getElementById("yesOrNoforid").value;
			var select1Count=document.getElementById("select1forid").value;
			var select2Count=document.getElementById("select2forid").value;
			var blankCount=document.getElementById("blankforid").value;
			var daziCount=document.getElementById("daziforid").value;
			var mailCount=document.getElementById("mailforid").value;
			var searchCount=document.getElementById("searchforid").value;
			var clCount=document.getElementById("clforid").value;
			var questionSum=e+o+yesOrNoCount+select1Count+select2Count+blankCount+daziCount+mailCount+searchCount+clCount;
			var isOk_1=true;
			var isOk_2=true;
			var isOk_3=true;
			var isOk_4=true;
			var isOk_5=true;
			var isOk_6=true;
			var isOk_7=true;
			var isOk_e=true;
			var isOk_o=true;
			var isOk_cl=true;
			if(e>0){
				isOk_e=isNotNull_e(e);
			}
			if(o>0){
				isOk_o=isNotNull_o(o);
			}
			if(yesOrNoCount>0){
				isOk_1=isOkFunc(yesOrNoCount,"yesOrNo","判断题");
			}
			if(select1Count>0){
				isOk_2=isOkFunc(select1Count,"select1","单选题");
			}
			if(select2Count>0){
				isOk_3=isOkFunc(select2Count,"select2","多选题");
			}
			if(blankCount>0){
				isOk_4=isOkFunc(blankCount,"blank","填空题");
			}
			if(daziCount>0){
				isOk_5=isOkFunc(daziCount,"dazi","打字题");
			}
			if(mailCount>0){
				isOk_6=isOkFunc(mailCount,"mail","邮件题");
			}
			if(searchCount>0){
				isOk_7=isOkFunc(searchCount,"search","搜索题");
			}
			if(clCount>0){
				isOk_cl=isOkFunc_clt(clCount);
			}
			//alert(isOk_cl);
			//alert(isOk_1);
			//alert(isOk_2);
			//alert(isOk_3);
			//alert(isOk_4);
			//alert(isOk_5);
			//alert(isOk_6);
			//alert(isOk_7);
			//alert(isOk_e);
			//alert(isOk_o);
			if(questionSum>0){
				if(isOk_1 && isOk_2 && isOk_3 && isOk_4 && isOk_5 && isOk_6 && isOk_7 && isOk_e && isOk_o && isOk_cl){
					//alert("OK");
					exampaperread.submit();
				}else{
					//alert("NO");
				}
			}else{
				alert("该试卷没有题目！");
				exampaperread.submit();
			}
	}
	*/
	/*
	function isNotNull(forid){ //hwc
		var is = true; 
		for (i = 1; i <= forid; i++){
			var subjecte = "Esubject"+i;
			var subjecto = "Osubject"+i;
			var Essay;
			if(document.getElementById(subjecte) != null || document.getElementById(subjecto) != null ) {
				if(document.getElementById(subjecte) != null){
					var Esubject = document.getElementById(subjecte).value;
					//var esu =document.getElementById(Esubject+"Essay").value;
					var esu =document.getElementById(subjecte+"Essay").value;
				} 
				if(document.getElementById(subjecto) != null){
					var Osubject = document.getElementById(subjecto).value;
					//var off =document.getElementById(Osubject+"Office").value;
					var off =document.getElementById(subjecto+"Office").value;
				}
				
				//if(document.getElementById("isEssay") != null &&  parseInt(esu) >= parseInt(document.getElementById("isEssay").value) ){ 
				//	alert("第"+Esubject+"问答题,打分成绩不能超过该题目分数噢！");
				//	is = false;
				//}
				//if(document.getElementById("isOffice") != null &&  parseInt(off)>= parseInt(document.getElementById("isOffice").value) ){
				//	alert("第"+Osubject+"个Office,打分成绩不能超过该题目分数噢！");
				//	is = false;
				//}
				
				if(document.getElementById("isEssay") != null &&  parseInt(esu) > parseInt(document.getElementById("isEssay").value) ){ 
					alert("第"+i+"个问答题,打分成绩不能超过该题目分数噢！");
					is = false;
				}
				if(document.getElementById("isOffice") != null &&  parseInt(off)> parseInt(document.getElementById("isOffice").value) ){
					alert("第"+i+"个Office,打分成绩不能超过该题目分数噢！");
					is = false;
				}
			}
		}
		return is;
	}
	*/
	/*
	function isNotNull_e(forid){
		var is = true; 
		for (i = 1; i <= forid; i++){
			var subjecte = "Esubject"+i;
			if(document.getElementById(subjecte) != null){
				var Esubject = document.getElementById(subjecte).value;
				var esu =document.getElementById(subjecte+"Essay").value;
			} 
			if(document.getElementById("isEssay") != null &&  parseFloat(esu) > parseFloat(document.getElementById("isEssay").value) ){ 
				alert("第"+i+"个问答题,打分成绩不能超过该题目分数噢！");
				is = false;
			}
		}
		return is;
	}
	
	function isNotNull_o(forid){
		var is = true; 
		for (i = 1; i <= forid; i++){
			var subjecto = "Osubject"+i;
			if(document.getElementById(subjecto) != null){
				var Osubject = document.getElementById(subjecto).value;
				var off =document.getElementById(subjecto+"Office").value;
			}
			if(document.getElementById("isOffice") != null &&  parseFloat(off)> parseFloat(document.getElementById("isOffice").value) ){
				alert("第"+i+"个Office,打分成绩不能超过该题目分数噢！");
				is = false;
			}
		}
		return is;
	}
	
	function isOkFunc(forid,quesName,quesType){
		var is = true; 
		for (var i = 0; i < forid; i++){
			var quesObj = quesName+i;
			var Essay;
			if(document.getElementById(quesObj) != null) {
				var inputValue =document.getElementById(quesObj).value;//你设置的值
				if(document.getElementById("is"+quesName) != null &&  parseFloat(inputValue) > parseFloat(document.getElementById("is"+quesName).value) ){ 
					alert("第"+(i+1)+"个"+quesType+",打分成绩不能超过该题目分数噢！");
					is = false;
				}
			}
		}
		return is;
	}
	//验证材料题
	function isOkFunc_clt(forid){
		var is=true;
		for(var i=0;i<forid;i++){
			if(document.getElementsByName("cl_"+i) != null) {
				var arrayQues=document.getElementsByName("cl_"+i);
				for(var j=0;j<arrayQues.length;j++){
					var inputValue =arrayQues[j].value;//你设置的值
					if(document.getElementById("cltEachScore_"+i+"_"+(j+1)) != null &&  parseFloat(inputValue) > parseFloat(document.getElementById("cltEachScore_"+i+"_"+(j+1)).value) ){ 
						alert("第"+(i+1)+"道材料题的第"+(j+1)+"小题,打分成绩不能超过该题目分数噢！");
						is = false;
					}
				}
				
			}
		}
		return is;
	}
	*/
	function checkInputScore(){
		var inputObj=document.getElementsByName("thescore");
		var inputObjPiyu=document.getElementsByName("thepiyu");
		var isOk=true;
		var ts="";
		for(var i=0;i<inputObj.length;i++){
			if(inputObj[i].value.indexOf(".")==-1){
				ts="/^[\\d]{1,}$/";
			}else{
				ts="/^[\\d]{1,}[.]{1}[\\d]{1,}$/";
			}
			ts=eval(ts);
			var tempEachscore=document.getElementById(inputObj[i].id+"_score");
			if(parseFloat(inputObj[i].value)>parseFloat(tempEachscore.value)){
				alert(document.getElementById(inputObj[i].id+"_mess").value+",打分成绩不能超过该题目分数!");
				isOk=false;
			}
			if(!ts.test(inputObj[i].value)){
				alert(document.getElementById(inputObj[i].id+"_mess").value+",题目分数不是有效数字！");
				isOk=false;
				//break;
			}
			//alert(inputObj[i].id);
		}
		if(inputObj.length==0){
			alert("该试卷没有题目");
		}else if(isOk==true){
			exampaperread.submit();
		}
	}
</SCRIPT>
			<div class="contentcenter">
				<div>
					<p align="center">
						<input type="button" id="b_t_a" class="inputOver" value="全部试题"
							onClick="showAllBlocks()" />
						<s:iterator value="examPaper.epBlocks" status="stepb">
							<input type="button" id="b_t_<s:property value="#stepb.index"/>"
								class="input" value="<s:property value="title" />"
								onClick="showBlocks(<s:property value="#stepb.index"/>)" />
						</s:iterator>
					</p>
				</div>
				<br />
			</div>
			<form action="exampaperread_submit.action" method="post"
				name="exampaperread">
				<!-- 这里是答题内容 -->
				<wysLib:examPaperRead readonly="true"></wysLib:examPaperRead>
				<br>
				<s:hidden name="myExamPaper.id" />

				<input class="regbutton3" style="CURSOR: pointer" type="button"
					onclick="checkInputScore();" value="提交批改" name=submit1>
			</form>
		</div>
		<div id="nextInfo">
			<ul class="info_ul">
				<li>
					<span>该答卷状态：<s:property value="myExamPaper.statusName" /> </span>
				</li>
				<li>
					<span>未批阅答卷数：<s:property
							value="myExamPaper.examPaper.quizcount" /> </span>
				</li>
				<li>
					<a class="textbg6" title="获取下一份未批阅的试卷"
						href="exampaperread_next.action?myExamPaper.id=<s:property value="myExamPaper.id" />">下一份</a>
					<input class="textbg6" style="border:none;" type="button"
					onclick="checkInputScore();" value="提交批改">
				</li>
			</ul>
			<div id="info"></div>
		</div>
	</body>
</HTML>