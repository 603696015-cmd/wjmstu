<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv=Content-Type content="text/html; charset=UTF-8">
		<title>培训班修课程信息修改</title>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
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

.textbg4 {
	margin-top: 2px;
}
</style>
		<script type="text/javascript"><!--
		
		//如果结束时间(b)大于开始时间(a)返回true；否则返回false
		 function duibi(a,b){
			var date=new Date(Date.parse(a.replace(/-/g,"/")));
			var date2=new Date(Date.parse(b.replace(/-/g,"/")));
			if(date>=date2){
				return false;
			} else{
				return true;
			}
		 }
			
		function queding(){
			var ts=/^[\d]{0,}$/;
			if(!ts.test($.trim($('#suggestcredit').val()))){
				alert("推荐学分必须为数字！");
				return false;
			}
			if(!ts.test($.trim($('#setcredit').val()))){
				alert("学分必须为数字！");
				return false;
			}
			//开始时间必须在结束时间之前，并且课程的开始、结束学习时间必须在培训班开始结束时间之内
			var k_start = $("#roomstart").val();
			var k_end = $("#roomend").val();
			if(!duibi(k_start,k_end)){
				alert("课程开始时间不能大于课程结束时间！");
				return false;					
			}
 			
			var classStartTime="<s:date name="elclass.starttime" format="yyyy-MM-dd HH:mm:ss"/>";
			var classFinishTime="<s:date name="elclass.finishtime" format="yyyy-MM-dd HH:mm:ss"/>";			
			if(duibi(k_start,classStartTime) || duibi(classFinishTime,k_start) || duibi(k_end,classStartTime) || duibi(classFinishTime,k_end)){
				alert("课程的开始、结束学习时间必须在培训班开始结束时间之内！");
				return false;
			} 
			
			var classLearnByOrder = "<s:property value='elclass.learnByOrder' />";//是否顺序学习
			var courseOrderid = document.getElementById("course.orderid").value;	//课程次序
			
			if(parseInt(classLearnByOrder) == 1  ){//顺序学习
				if(courseOrderid == ""){
					alert("课程序号不能为空，请填写！");
					document.getElementById("course.orderid").focus();
					return ;
				}else{
					if(isNaN(courseOrderid)){
						alert("请输入数字！");
						return ;
					}
					if(parseInt(courseOrderid) == 0  ){
						alert("课程序号请从1开始填写！");
						return ;
					}
					//验证所填写的序号是否已经存在
					var courseid = parseInt("<s:property value="course.id"/>");
					var classid = parseInt("<s:property value="elclass.id"/>");
					courseOrderid = parseInt(courseOrderid);
					var flag = checkOrderidIsExist(courseid,classid,courseOrderid);
					
					if(!flag){
						if(window.confirm("该序号已经存在，是否自动加1？")){
							document.getElementById("course.orderid").value = parseInt(courseOrderid) + 1;
							queding();
						}else{
							document.getElementById("course.orderid").focus();
							return ;
						}
					}
				}
			}
			
			courseOrderid = document.getElementById("course.orderid").value;
			
			var ch_val = 0;
			var chs = document.getElementsByName("course.firstLearn");
			var ch ; 
			if(chs!=undefined){
				if(chs.length>0 ){
					for(var i=0;i<chs.length;i++){
						ch = chs[i];
						if(ch!=undefined){
							if(ch.checked )	{
								ch_val = ch.value ; 
							}
						}
					}
				}
			}
			
			window.returnValue = {"roomstart":$("#roomstart").val(),
				"roomend":$("#roomend").val(),
				"setcredit":$("#setcredit").val(),
				"getcredit":$("#getcredit").val(),
				"courseid":"<s:property value="course.id"/>",
				"suggestcredit":$("#suggestcredit").val(),
				"orderid":courseOrderid,
				"firstLearn":ch_val
				};
			window.close();
		}
		
		function checkOrderidIsExist(courseid,classid,courseOrderid){
         	var returnValue = true;
         	$.ajax({
			  type: 'POST',
			  url: "checkOrderidIsExist.action",
			  data: {'course.id':courseid,'elclass.id':classid,'course.orderid':courseOrderid},
			  async:false,//同步
			  success: function(data){
		  		data = eval("("+data+")"); 
		  		if(data){
		  			returnValue = false;
		  		}
			  }
			});
			return returnValue;
         }
         
         function disNextTr(index){
         	if(index == 3){
         		document.getElementById("nextTr").style.display = "block";
         	}else{
         		document.getElementById("nextTr").style.display = "none";
         	}	
         }
         
         function load(){
         	var course_getcredit = parseInt("<s:property value='course.getcredit' />");
         	if(course_getcredit!=undefined && course_getcredit == 3){
         		document.getElementById("nextTr").style.display = "block";
         	}
         }
	--></script>
	</HEAD>
	<body onload="load();">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td valign="middle" class="tablequiz">
					<ul class="nav">
						<li>
							<span style="font-weight: bold;">培训班课程修改</span>
						</li>
					</ul>
				</td>
				<td width="120" valign="middle" class="tablequiz">
					<A id=quit href="javascript:window.parent.full_screen(false);"
						class="textbg6" style="display: none">退出全屏</A>
				</td>
			</tr>
		</table>

		<!-- 内容 -->
		<table align="left" cellpadding="1" cellspacing="1" width="400px">
			<tr>
				<td height="30" align="right">
					班级：
				</td>
				<td>
					<s:property value="elclass.name" />
				</td>
			</tr>
			<tr>
				<td height="30" align="right">
					课程：
				</td>
				<td>
					<s:property value="course.name" />
				</td>
			</tr>
			<tr>
				<td height="30" align="right">
					开始时间：
				</td>
				<td>


					<input name="" id="roomstart" type="text" size="20"
						value="<s:date name="course.roomstart" format="yyyy-MM-dd HH:mm:ss" />" />
					<input type="button" class="textbg4"
						onclick="setday(document.getElementById('roomstart'))" value="选择" />


					<font color="red">(培训班开始时间：<s:date name="elclass.starttime"
							format="yyyy-MM-dd HH:mm:ss" />)</font>

				</td>
			</tr>

			<tr>
				<td height="30" align="right">
					结束时间：
				</td>
				<td>
					<input id="roomend" type="text" name=""
						value="<s:date name="course.roomend" format="yyyy-MM-dd HH:mm:ss" />" />
					<input type="button" class="textbg4"
						onclick="setday(document.getElementById('roomend'))" value="选择" />

					<font color="red">(培训班结束时间：<s:date name="elclass.finishtime"
								format="yyyy-MM-dd HH:mm:ss" />)</font>
				</td>
			</tr>
			<tr>
				<td height="30" align="right">
					推荐学分：
				</td>
				<td>
					<input id="suggestcredit" type="text"
						value="<s:property value="course.suggestcredit"/>" />
				</td>
			</tr>
			<tr>
				<td height="30" align="right">
					学分：
				</td>
				<td>
					<input id="setcredit" type="text" name=""
						value="<s:property value="course.setcredit"/>" />
				</td>
			</tr>
			<tr>
				<td height="30" align="right">
					结业方式：
				</td>
				<td>
					<select id="getcredit" name="" onchange="disNextTr(this.options[this.selectedIndex].value);">
						<option value="1"
							<s:if test="course.getcredit==1">selected='selected'</s:if>>
							学完
						</option>
						<option value="2"
							<s:if test="course.getcredit==2">selected='selected'</s:if>>
							考过
						</option>
						<option value="3"
							<s:if test="course.getcredit==3">selected='selected'</s:if>>
							学完且考过
						</option>
					</select>
				</td>
			</tr>
			<tr style="display:none" id="nextTr">
				<td height="30" align="right">
					先学后考：
				</td>
				<td>
					<input type="radio" name="course.firstLearn" value="1"
							<s:if test="course.firstLearn==1">checked="checked"</s:if>  />
					是
					<input type="radio" name="course.firstLearn" value="0"
						<s:if test="course.firstLearn==0">checked="checked"</s:if>  />
					否
				</td>
			</tr>
			<tr>
				<td height="30" align="right">
					课程序号：
				</td>
				<td>
					<input id="course.orderid" type="text"
						value="<s:property value="course.orderid"/>" />
				</td>
			</tr>
			<tr>
				<td height="30" align="center" colspan="2">
					<input class="textbg4" type="button" onclick="queding()" value="保存" />
					&nbsp;&nbsp;&nbsp;
					<input type="button" class="textbg4" onclick="window.close();"
						value="取消" />
				</td>
			</tr>
		</table>
		<!-- 内容 -->
	
	</body>
</HTML>
