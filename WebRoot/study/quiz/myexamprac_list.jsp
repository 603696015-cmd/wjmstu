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
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<TITLE>我的练习</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript">
			function openPrac(vlaid,id,count,times,startTime,endTime,now){
				 //alert(count);
				 if(!dateTimeCheck(startTime,endTime,now)){
				 	return;
				 }
				 if(vlaid == 3){
				 	alert("练习已暂停，请等待练习再次开通");
				 }else if(parseInt(times) >= parseInt(count)){
				 	alert("超过规定次数，不能再练习了!");
				 }else{
					 if(confirm('确定开始练习？')){
					 	window.open("exampracinto.action?examprac.id="+id,"exampracpaper","toolbar=no,fullscreen=1,location=no,directories=no,menubar=no,scrollbars=yes,resizable=no,status=no");
					 }
				 }
			}
			function toDate(str){
  				 var sd=str.split("-");
   				 return new Date(sd[0],sd[1],sd[2],sd[3],sd[4],sd[5]);
			}
			function dateTimeCheck(startTime,endTime,now){
				//alert(startTime);
				//alert(now);
				//alert(endTime);
				//转换成时间
				var start=toDate(startTime);
				var end=toDate(endTime);
				var noww=toDate(now);
				//end=toDate("2012-05-10-00-00-00");
				//noww=toDate("2012-05-11-00-00-00");
				//var valid=val;
				//alert(start);
				//alert(end);
				//alert(noww);
				//alert(valid);
				if(noww<start){
					alert("不在有效练习时间段范围内，请与管理员联系");
					return false;
				}else if(noww>end){
					alert("不在有效练习时间段范围内，请与管理员联系");
					return false;
				}
				return true;
			}
		</script>

		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
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
					<wysLib:Navigation ivalue="练习列表" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">我的练习</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<div>
				<table width="100%" align="center" cellpadding="1" cellspacing="1">
					<caption>
						我的练习
					</caption>
					<tr>
						<th width="200" align="center">
							练习名称
						</th>
						<th width="130" align="center">
							创建者
						</th>
						<th width="120" align="center">
							开始时间
						</th>
						<th width="120" align="center">
							结束时间
						</th>
						<th width="60" align="center">
							次数
						</th>
						<th width="80" align="center">
							最高分
						</th>
						<th width="80" align="center">
							平均分
						</th>
						<th width="120" align="center">
							查看详情
						</th>
						<th width="120" align="center">
							开始答卷
						</th>
					</tr>
					<tbody onMouseOut="changeback()" onMouseOver="changeto()">
						<s:iterator value="myexampracs">
							<tr>
								<td align="center">
									<s:property value="prac.title" />
								</td>
								<td width="130" align="center">
									<s:property value="prac.user.realname" />
								</td>
								<td width="130" align="center">
									<s:date name="prac.begintime" format="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td width="130" align="center">
									<s:date name="prac.endtime" format="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td width="80" align="center">
									<s:property value="times" />
								</td>
								<td width="80" align="center">
									<s:property value="maxscore" />
								</td>
								<td width="80" align="center">
									<s:property value="avgscore" />
								</td>
								<td width="120" align="center">
									<a
										href="examprac_detail_list.action?examprac.id=<s:property value="prac.id"/>"
										class="textbg5">查看详情</a>
								</td>
								<td width="120" align="center">
									<a
										onClick="openPrac(<s:property value="valid"/>,<s:property value="prac.id"/>,'<s:property value="prac.pracCount"/>','<s:property value="times" />','<s:date name="prac.begintime" format="yyyy-MM-dd-HH-mm-ss" />','<s:date name="prac.endtime" format="yyyy-MM-dd-HH-mm-ss" />','<s:property value="#request.now"/>');return false;"
										href="javascript:;" class="textbg">开始答卷</a>
								</td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
				<DIV style="text-align: center">
					<wysLib:page></wysLib:page>
					<br>
				</DIV>
				<form action="myexamprac_list.action" name="erform" method="post">
					<s:hidden name="pN" id="pageNow">
					</s:hidden>
					<s:hidden name="pS">
					</s:hidden>
				</form>
				<script>
				function page(i){
					document.getElementById("pageNow").value=i;
					erform.submit();
				}
				</script>
			</div>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
