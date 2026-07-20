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
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
				
			function searchUserInit(comp){
			     width=800;
				 height=450;
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv =  window.showModalDialog("wordslib.action?sub_department=1&x="+Math.random(),null,sFeature);
				 if(null==rv){
				 	alert('您没有选择用户！');
				 }else{
				 	if(rv[0]<=0)  	alert('您没有选择用户！');
				 	else
				 	$.post("mess_getWordsLibInfoJson.action", {
						"word.id":rv[0],
						"x":Math.random
						}, 
						function (data) {
							var dataObj=eval("("+data+")");
							document.getElementById("t_id").value=dataObj.word.id;
							document.getElementById("t_name").value=dataObj.word.name;
							document.getElementById("t_hname").value=dataObj.word.name;
						}); 
				 }
			} 
		</script>
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
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="词汇列表" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<s:form action="plan_view" name="myelist" theme="simple">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<input type="hidden" value="${learnplan.id }" name="learnplan.id"/>
							<table width="100%" align="center" cellspacing="1"
								cellpadding="1" bgcolor="#D1E4F5">
								
								<tr>
									<td colspan="3" bgcolor="#F8FCFE" style="">
											开始时间：
										<input size="50" class="Wdate" name="learnplan.starttime" readonly="readonly"
											type="text" onClick="setday(this)" id="learnplan.starttime" />
									</td>
									<td colspan="3" bgcolor="#F8FCFE" style="">
										结束时间：
									  <input size="50" class="Wdate" name="learnplan.endtime" readonly="readonly"
										type="text" onClick="setday(this)" id="learnplan.endtime" />
									 </td>
									<td width="15%" colspan="2" bgcolor="#F8FCFE">
								  		<input class="textbg4" style="height: 25px;" type="submit" onClick="document.getElementById('pageNow').value=0"
											value="查询">									
									</td>
								</tr>
							</table>
								<table width="100%" align="center" cellspacing="1"
									cellpadding="1">
									<tr>
										
										<th width="215" height="30" align="center">
											用户名
										</th>
										<th width="320" height="30" align="center">
											登陆时间
										</th>
										<th width="262" height="30" align="center">
											退出时间
										</th>
										<th width="291" height="30" align="center">
											时长
										 </th>
									</tr>
									<tbody onMouseOut="changeback()" onMouseOver="changeto()">
										<s:iterator value="mylogins">
											<tr>
												<td height="30" align="center">
													<s:property value="elUser.username" />
												</td>
												<td width="320" height="30" align="center">
													<s:date name="logintime" />
												</td>
										      	<td width="262" height="30" align="center">
										      		<s:date name="exittime" />
									          	</td>
										      	<td width="291" height="30" align="center" colspan="2">
										      		<s:property value="shichang"/>
										      	</td>
											</tr>
										</s:iterator>										
									</tbody>
							  </table>
							  <table width="100%" align="center" cellspacing="1"
								cellpadding="1" bgcolor="#D1E4F5">
								
								<tr>
									<td width="806" align="center"  bgcolor="#F8FCFE" style="">合计</td>
									<td  width="294" height="30" align="center"></td>
								</tr>
							</table>
								<br>
								<script>
									function page(i){
										document.getElementById("pageNow").value=i;
										myelist.submit();
										
									}
								</script>
								<wysLib:page></wysLib:page>
					  <br/>
			</s:form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
