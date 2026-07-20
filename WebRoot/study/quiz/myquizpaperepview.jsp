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
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<base href="<%=basePath%>">
		<title>查看我的答卷</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
			function load(){
				if("${elmessage}"!=""){
					var roomid = <s:property value="roomid" />;
					if(window.confirm("恭喜您，您当前的等级是"+"${elmessage}")){
						if(roomid!=0){
							assign_batch(roomid,"${classification.name}");
						}
						window.parent.location.href = "wjm_user_center.action?roomid=" + roomid ;
					}else{
						if(roomid!=0){
							assign_batch(roomid,"${classification.name}");
						}
					}
					
				}
			}
			
			function assign_batch(roomid,classification_name){
				//获取培训批次
				//将培训批次分配给用户
				//将3A及3A以前的培训班进度改为100%
				$.ajax({
				  type: 'POST',
				  url: "assign_batch.action",
				  data: {'roomid':roomid,'classification.name':classification_name},
				  async:false,//同步
				  success: function(data){
			  		
				  }
				});
			}
		</script>
		<style type="text/css">
body {
	width: 100%;
	margin: 0px;
	padding: 0px;
	font-size: 14px;
	font-family: Arial, Helvetica, sans-serif;
	text-align: center;
}

.main {
	width: 795px;
}

h3 {
	text-align: center;
	width: 100%;
	margin: 0px;
	font-size: 25px;
	padding: 10px 0px 10px 0px;
}
h4{margin: 5px 0px 2px 0px;}
.quizinfo {
	border: 2px solid #122333;
	text-align: left;
	width: 610px;
}

.quizinfo .left,.right {
	float: left;
	width: 285px;
	padding: 5px 0px 10px 5px;
}

.info {
	color: #444;
	font-weight: bolder;
}

.info_ul {
	list-style-type: none;
	margin: 0px;
}

.info_ul li {
	padding: 3px 0px 1px 0px;
	color: #888888;
}
.quiz_detail{
	border: 2px solid #122333;
	text-align: left;
	font-size:12px;
	width: 600px;
	padding: 5px;
}
.block_name{
	font-weight: bolder;
}
.block_desc{
	padding-left: 20px;
}
.question{padding-left:20px;}
.question .sort{width:22px;float:left;}
.question .content{padding-left:22px;}
.answer{color: green;}
div,p{margin: 0px;padding:0px;}
.answer{padding-top:8px;}
.bottom{margin:10px auto 10px auto;}
.bottom a{background: #ff9933;padding: 3px;}
</style>
	</HEAD>
	<body onload="load();">
		<div class="main" style="margin-top: 100px">
			<h3>
				本次考试情况
			</h3>
			<div class="quizinfo">
				<div class="left">
					<ul class="info_ul">
						<li>
							<span class="info">考生姓名：</span>
							<s:property value="#session.realname" />
						</li>
						<li>
							<span class="info">试卷名称：</span>
							<s:property value="examPaper.title" />
						</li>
						<li>
							<span class="info"> 通过分数：</span> <s:property value="examPaper.passScore"/>
						</li>
						<s:if test="examPaper.scorelook==1">
						<li>
							<span class="info">客观题得分：</span><s:property value="examPaper.mepKscore" />
						</li>
						<li>
							<span class="info">是否通过：</span>
							<s:if test="myExamPaper.ispassed==1">是</s:if><s:else>否</s:else>
						</li>
						</s:if>
					</ul>
				</div>
				<div class="right">
					<ul class="info_ul">
						<li>
							<span class="info">身份证号：</span><s:property value="#session.shenfenzheng"/>
						</li>
						<li>
							<span class="info">试卷总分：</span>
							<s:property value="examPaper.ep_tscore" />
						</li>
						<s:if test="examPaper.scorelook==1">
						<li>
							<span class="info"> 考生成绩：</span>
							<s:property value="myExamPaper.myScore" />
						</li>
						<li>
							<span class="info"> 主观题得分：</span>
							<s:property value="examPaper.mepZscore" />
						</li>
						</s:if>
						<li>
						<input onclick="document.location='myquizpaperview.action?myExamPaper.id=<s:property value="myExamPaper.id"/>'" value="查看答卷" style="border: none;background: #ff9933" type="button" />
						</li>
					</ul>
				</div>
				<div style="clear: both;"></div>
			</div>
			<br/>
			<input onclick="window.close();" value="关闭窗口" style="border: none;background: #ff9933" type="button" />
			<!--  <input onclick="document.location='course_study.action'" value="下一步" style="border: none;background: #ff9933" type="button" />-->
		</div>
	</body>
	<script type="text/javascript">
		function check_score(){
		alert(333);
		var passed= <s:property value="myExamPaper.ispassed" />;
		if(passed !=1){
		alert(111);
			window.open("course_study.action?course.isLogout=1&coursePage.id=<s:property value='cpage.id' />&course.id=<s:property value='course.id'/>&course.classid=<s:property value='course.classid'/>","course_exam_6","toolbar=no,fullscreen=1,location=no,directories=no,menubar=no,scrollbars=yes,resizable=no,status=no");
			window.close();
		}	
		}
	</script>
</HTML>