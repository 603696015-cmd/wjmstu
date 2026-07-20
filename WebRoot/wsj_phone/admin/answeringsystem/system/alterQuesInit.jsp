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
		<TITLE>中国食品安全培训网--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="editor/fckeditor.js"></script>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<SCRIPT type="text/javascript">
		//选择问答类别
		function searchAnsweringTypeTreeInit(){
				 width=600;
				 height=500;
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv =  window.showModalDialog("selectAnsweringTypeTreeInit.action?x="+Math.random(),null,sFeature);
				 if(rv!=undefined&&rv!=""){
					 var bh=rv.split("-=wys=-");
					 document.getElementById("ques.answeringType.id").value=bh[2];
					 document.getElementById("ques.answeringType.name").value=bh[1];
				 }
			}
		//指定回答人
		function answeringUsersInit(){
			var userids_dom = document.getElementById("ques.answerUserids");
			var type = 1;
			var user_ary ;
			var user ;
			var userids = "";
			var user_html = "" ;
			var ccount = 5;//每行显示10个人
			
			width=screen.availWidth * 0.8;;
			height=screen.availHeight * 0.8;;
		  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
		  	var rv =  window.showModalDialog("answeringUsersInit.action?x="+Math.random(),null,sFeature);
		  	if(rv!=undefined&&rv!=""){
				user_ary = rv.split(",");
				if(user_ary!=undefined && user_ary.length>0){
					user_html = "<table id=userids_table_"+type+" border='0'  cellspacing='1' cellpadding='1'><tr>";
					for(var i=0;i<user_ary.length;i++){
						user = user_ary[i].split("==");
						userids += user[0] + ",";
						user_html += "<td id=userids_td_"+type+"_"+i+">"+user[1] + "<a href='javascript:removeByUserid("+user[0]+","+type+","+i+");' ><span style='color:blue;corsor:hand'>X</span></a>" + "&nbsp;&nbsp;" +"</td>";
					}
					user_html += "</tr><table>";
				}
				if(user_html!=""){
					$("#users_div_1_"+type).html(user_html);
				}
				if(userids!="" && userids.charAt(userids.length-1)==","){
					userids = userids.substring(0,userids.lastIndexOf(","));
					userids_dom.value = userids;
				}
			}
		}
		function removeByUserid(userid,type,i){
			$("#userids_td_"+type+"_"+i).remove();
			var userids = document.getElementById("ques.answerUserids");
			var val = "";
			var arr;
			var val_ = "";
			if(userids!=undefined){
				val = userids.value;
				if(val!=""){
					arr = val.split(",");
					for(var i=0;i<arr.length;i++){
						if(parseInt(arr[i]) != parseInt(userid)){
							val_ += arr[i]+",";
						}
					}
				}
			}
			if(val_!="" && val_.charAt(val_.length-1)==","){
				val_ = val_.substring(0,val_.lastIndexOf(","));
			}
			userids.value = val_;
		}
		function myload(){
				var oFCKeditor = new FCKeditor('content') ;
				oFCKeditor.BasePath = "editor/" ;
				oFCKeditor.Height = 400;
				oFCKeditor.Width = 980;
				oFCKeditor.ReplaceTextarea();
				
				setCurTime("releasetime");
			}
		function setCurTime(oid){
				var now=new Date();
				var year=now.getYear();
				var month=now.getMonth()+1;
				var day=now.getDate();
				var hours=now.getHours();
				var minutes=now.getMinutes();
				if(minutes<10){
					minutes="0"+minutes;
				}
				var seconds=now.getSeconds();
				if(seconds<10){
					seconds="0"+seconds;
				}
				var timeString = year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
				var oCtl = document.getElementById(oid);
				oCtl.value = timeString;
			}
			function doSubmit(){
				var titleObj=document.getElementById("ques.name");
				var title=titleObj.value.replace(/(\s*$)/g, "");
				var typeObj=document.getElementById("ques.answeringType.id");
				var type = typeObj.value;
				if(title==""){
					alert("问题标题不能为空!");
					titleObj.focus();
					return false;
				}
				if(type==0){
					alert("请选择问题类别!");
					typeObj.focus();
					return false;
				}
				return true;
			}
		</SCRIPT>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((   this .   sectionRowIndex %   2 ==   0)
		? 
		 "#ffffff" :   "#f4f4f4" )
}
</style>
	</HEAD>
	<body onLoad="myload();">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td valign="middle" class="tablequiz">
					<ul class="nav">
						<li>
							<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
								<wysLib:Navigation ivalue="" />
							</div>
						</li>
					</ul>
				</td>
				<td width="120" valign="middle" class="tablequiz">
					<A id=quit href="javascript:window.parent.full_screen(false);"
						class="textbg6" style="display: none">退出全屏</A>
				</td>
			</tr>
		</table>

		<s:form action="alterQues.action" method="post"
			name="catalog_info" theme="simple" onsubmit="return doSubmit();">
			<s:hidden name="returnPage" ></s:hidden>
			<s:hidden name="ques.status" ></s:hidden>
			<s:hidden name="ques.id" />
			<input type="hidden" id="ques.answeringType.id"
				name="ques.answeringType.id" value=<s:property value="ques.answeringType.id" /> />
			<table width="980px" cellpadding="1" align="center" cellspacing="1"
				bgcolor="#ECEDEB">
				<tr>
					<td align="right" bgcolor="#FFFFFF">
						<span class="neededitem">*</span>问题标题：
					</td>
					<td bgcolor="#FFFFFF">
						<label>
							<s:textfield name="ques.name" id="ques.name" size="60" />
						</label>
					</td>
				</tr>
				<tr>
					<td align="right" bgcolor="#FFFFFF">
						<span class="neededitem">*</span>所属类别：
					</td>
					<td bgcolor="#FFFFFF">
						<label>
							<s:textfield theme="simple" name="ques.answeringType.name"
								size="20" readonly="true" id="ques.answeringType.name" />
							<a href="#" onClick="searchAnsweringTypeTreeInit();return false;"
								class="textbg">点此进行选择</a>
						</label>
					</td>
				</tr>
				<tr>
					<td align="right" bgcolor="#FFFFFF">
						有效期：
					</td>
					<td bgcolor="#FFFFFF">
						<label>
							<input class="Wdate" name="ques.validTime" readonly="readonly"
								type="text" onClick="setday(this)" id="validTime" />
						</label>
					</td>
				</tr>
				<tr>
					<td align="right" bgcolor="#FFFFFF">
						指定回答人：
					</td>
					<td bgcolor="#FFFFFF">
						<label>
							<a href="javascript:answeringUsersInit()" class="textbg">点此进行选择</a>
							<div id="users_div_1_1">
								<s:if test="ques.answerUsers.size()!=0">
									<table id=userids_table_1 border='0'  cellspacing='1' cellpadding='1'>
										<tr>
											<s:iterator value="ques.answerUsers" status="status">
												<td id=userids_td_1_<s:property value="#status.index" />>
													<s:property value="realname" /><a href="javascript:removeByUserid(<s:property value="realname" />,1,<s:property value="#status.index" />);"><span style='color:blue;corsor:hand'>X</span></a>&nbsp;&nbsp;
												</td>
											</s:iterator>
										</tr>
									</table>
								</s:if>
							</div>
							<input type="text" name="ques.answerUserids" id="ques.answerUserids" />
						</label>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center" bgcolor="#FFFFFF">
						问题简介
						<span style="font-size: 14px; color: blue"><strong>注意：</strong>在下面编辑器中使用的图片宽度过大时，请自行调整，以免造成页面样式混乱！</span>
					</td>
				</tr>
			</table>
			<div style="text-align: center; width: 100%">
				<s:textarea name="ques.content" id="content" cols="60" rows="7"
					cssStyle="width: 980px; height: 440px;; visibility: hidden;" />
			</div>
			<div style="text-align: center;">
				<input class="textbg6" type="submit" value="确认修改">
			</div>
		</s:form>
	
	</body>
</HTML>
