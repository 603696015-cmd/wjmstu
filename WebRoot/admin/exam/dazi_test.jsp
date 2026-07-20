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
		<TITLE>考场批次管理</TITLE>
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
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
</style>
		<script type="text/javascript" src="js/exampaperop.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
			function onsubmit_(){
				if(parseFloat($("#erbname2").val())>parseFloat($("#erbname3").val())){
					alert("打对字数不可大于范文长度！请认真设定");
					$("#erbname2").focus();
					return false;
				}
				rules = $("#rules1").val()+"-=SpRule-"+$("#rules2").val() +"-=SpRule-"+$("#rules3").val()+"-=SpRule-" ;
				for(var jj=1;jj<dazi;jj++){
					rules+=$("#b_dazirules"+jj).val ()+":"+$("#e_dazirules"+jj).val ()+
					":"+$("#jg_dazirules"+jj).val ()+":"+$("#yx_dazirules"+jj).val ()+":"+$("#mf_dazirules"+jj).val ()+":"
				}
				$("#endtime").attr("value",rules);
				rules ="xx-=SpEl=-"+$("#erbname2").val()+"-=SpEl=-"+$("#erbname3").val()+"-=SpEl=-ewqe" ;
				$("#starttime").attr("value",rules);
				//alert(document.getElementById("endtime").value+"===="+document.getElementById("starttime").value)
				if($("#erbname").val()==''){
					alert("请填写批次标题！");
					return false;
				}
				return true;
			}
			function setR(){
				var x = '<s:property value="classname" />';
				var y =x.split(":")[0] ;
				var y1 =x.split(":")[1] ;
				var z =  y.indexOf(".");
				if(z>=0){
					w = y.substring(z).length>3?3:y.substring(z).length;
					//y= y.substring(0,z+ w);
				}
				var z1 = '';
				if(y1==-1){ z1= "未评分";}
				if(y1==-2){ z1= "未知题型";}
				if(y1==-3){ z1= "没设定评分规则";}
				if(y1==0){ z1= "未通过（打字题）";}
				if(y1==1){ z1= "已通过";}
				if(y1==-4){ z1= "年龄未知";}
				if(y1==-5){ z1= "错误未知";}
				if(y1==-6){ z1= "小题题型不对";} 
				document.getElementById("res").innerHTML=parseFloat(y)+"【"+z1+"】";
			}
			function seeChart(){
				if(!onsubmit_())return ;
				catalog_info.action = "dazi_test_sub.action";
				catalog_info.target = "blank";
				catalog_info.submit() ;
			}
			function seeResult(){
				if(!onsubmit_())return ;
				catalog_info.action = "dazi_test.action";
				catalog_info.target = "_self";
				catalog_info.submit() ;
			}
		</script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<BODY onLoad="setR()">
		<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="填写测试数据" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;"> 打字算法测试</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<s:form action="" method="post" name="catalog_info"
				theme="simple" onsubmit=" return onsubmit_();">
				<table width="800px" align="left" cellpadding="0" cellspacing="1">
					<tr>
						<td width="120" height="30" align="center">
							我的答案
						</td>
						<td>
							<label><!--
								打字速度：
								<s:textfield id="erbname1" name="question.stuAnswers[2]"
									size="5" />-->
								打对字数：
								<s:textfield id="erbname2" name="question.stuAnswers[1]"
									size="5" />
								范文字数：
								<s:textfield id="erbname3" name="question.stuAnswers[2]"
									size="5" />
								<s:hidden id="starttime" name="question.stuAnswer"></s:hidden>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center">
							评分规则
							<s:hidden name="question.rulestring" id="endtime"></s:hidden>
						</td>
						<td>
							总&nbsp;&nbsp;&nbsp;&nbsp;分：
							<input type="text" size="4" id="rules1" name="question.score"
								value="<s:property value="question.score"/>" />
							分
							<br /><!--
							完成分：
							<input type="text" size="4" id="rules2"
								value="<s:property value="question.rules[1]"/>" />
							分
							<br />-->
							时&nbsp;&nbsp;&nbsp;&nbsp;长：
							<input type="text" size="4" id="rules3"
								value="<s:property value="question.rules[2]"/>" />
							分钟
							<br />
							评分策略：
							<a onClick="daziruleadd();return false;" href="#">添加</a>
							<a href="#" onClick="daziruledelete();return false;">删除</a>
							<div id="dazi_rule">
								<div id="dazi_rule">
									<s:set name="rulesize" value="0"></s:set>
									<s:iterator value="question.dazirule" status="dazist">
										<div id="dazi_rule<s:property value="#dazist.index+1"/>">
											年龄段
											<s:property value="#dazist.index+1" />
											：
											<input id="b_dazirules<s:property value="#dazist.index+1"/>"
												size="3"
												value="<s:property value="question.dazirule[#dazist.index][0]"/>" />
											到
											<input id="e_dazirules<s:property value="#dazist.index+1"/>"
												size="3"
												value="<s:property value="question.dazirule[#dazist.index][1]"/>" />
											及格速度：
											<input id="jg_dazirules<s:property value="#dazist.index+1"/>"
												size="3"
												value="<s:property value="question.dazirule[#dazist.index][2]"/>" />
											优秀速度：
											<input id="yx_dazirules<s:property value="#dazist.index+1"/>"
												size="3"
												value="<s:property value="question.dazirule[#dazist.index][3]"/>" />
										满分速度：
											<input id="mf_dazirules<s:property value="#dazist.index+1"/>"
												size="3"
												value="<s:property value="question.dazirule[#dazist.index][4]"/>" />
										</div>
										<s:set name="rulesize" value="#dazist.index+1"></s:set>
									</s:iterator>
									<script type="text/javascript">dazi = <s:property value="#rulesize+1"/>;</script>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center">
							年龄
						</td>
						<td>
							<label>
								<!--<s:textfield name="course_sourse" value="25" id="course_sourse" size="6" />
							-->
								<input type="text" id="course_sourse" name="course_sourse"
									value="<s:property value="course_sourse"/>" size="6" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="50" align="center">
							结果
						</td>
						<td bgcolor="#fffeee" style="color: red" id="res">

						</td>
					</tr>
					<tr>
						<td width="120" height="50" align="center">
							&nbsp;
						</td>
						<td>
							<input style="height: 35px;" class="textbg6" type="button" onclick="seeResult()"
								value="计 算">
							
							<input type="hidden" value="1" checked="checked" name="question.mystatus" ><!--速度增长（固定打字时间）
							<input type="radio" value="0" name="question.mystatus" >时间增长（固定打对字数）
							<input type="button" value="查看图表" onclick="seeChart();">
							<input type="radio" value="-1" name="question.mystatus" >时间增长（固定打字速度）
					--></td>
					</tr>
				</table>
				<br>
			</s:form>

		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
