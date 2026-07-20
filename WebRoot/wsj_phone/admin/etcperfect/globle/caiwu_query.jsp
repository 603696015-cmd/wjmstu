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
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
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
	background-color: expression((   this .   sectionRowIndex %   2 ==   0)
		? 
		 "#ffffff" :   "#f4f4f4" )
}
</style>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
			function view(number){
				if(number == 1){
					document.getElementById("type").value = document.getElementById("xiaoshou").innerHTML;
					document.getElementById("tablename").value = "<s:property value='sk_tablename'/>";
				}else if(number == 2){
					document.getElementById("type").value = document.getElementById("qitashouru").innerHTML;
					document.getElementById("tablename").value = "<s:property value='sk_tablename'/>";
				}else if(number == 3){
					document.getElementById("type").value = document.getElementById("caigou").innerHTML;
					document.getElementById("tablename").value = "<s:property value='fk_tablename'/>";
				}else if(number == 4){
					document.getElementById("type").value = document.getElementById("gongzizhichu").innerHTML;
					document.getElementById("tablename").value = "<s:property value='fk_tablename'/>";
				}else if(number == 5){
					document.getElementById("type").value = document.getElementById("qitazhichu").innerHTML;
					document.getElementById("tablename").value = "<s:property value='fk_tablename'/>";
				} 
				view.submit();
			}
		</script>
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="财务一览" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<form action="caiwu_view.action" method="post" name="view">
		</form>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<form action="caiwu_query.action" method="post" name="search">
			<table width="98%" align="center" cellspacing="1" cellpadding="1">
				<tr>
					<td align='center'>开始时间</td>
					<td align='center'><input type='text' name='starttime' onClick="setday(this)"/></td>
					<td align='center'>结束时间</td>
					<td align='center'><input type='text' name='endtime' onClick="setday(this)"/></td>
					<td align='center'><input type="submit" value="搜索" class="textbg4"/></td>
				</tr>
			</table>
			</form>
			
			<table width="98%" align="center" cellspacing="1" cellpadding="1">
			  <tbody onMouseOut="changeback()" onMouseOver="changeto()">
			  <tr>
			    <td ></td>
			    <td align='center'>分类</td>
			    <td align='center'>相关单据</td>
			  </tr>
			  <tr>
			    <td  rowspan="2" align="center"><p>收入合计 <br />
			      <s:property value="shouru_heji"/>元 </p></td>
			    <td align="center"><p id="xiaoshou">销售 </p></td>
			    <td align="center"><p><a href="caiwu_view.action?tablename=<s:property value='sk_tablename'/>&type=1" class="textbg4" >查看</a></p></td>
			  </tr>
			  <tr>
			    <td align="center"><p id="qitashouru">其他收入 </p></td>
			    <td align="center"><p><a href="caiwu_view.action?tablename=<s:property value='sk_tablename'/>&type=2" class="textbg4" >查看</a></p></td>
			  </tr>
			  <tr>
			    <td  rowspan="3" align="center"><p>支出合计 <br />
			      <s:property value="zhichu_heji"/>元 </p></td>
			    <td align="center"><p id="caigou">采购 </p></td>
			    <td align="center"><a href="caiwu_view.action?tablename=<s:property value='fk_tablename'/>&type=3" class="textbg4" onclick="view(3);">查看</a></td>
			  </tr>
			  <tr>
			    <td align="center"><p id="gongzizhichu">工资支出 </p></td>
			    <td align="center"><a href="caiwu_view.action?tablename=<s:property value='fk_tablename'/>&type=4" class="textbg4" onclick="view(4);">查看</a></td>
			  </tr>
			  <tr>
			    <td align="center"><p id="qitazhichu">其他支出 </p></td>
			    <td align="center"><a href="caiwu_view.action?tablename=<s:property value='fk_tablename'/>&type=5" class="textbg4" onclick="view(5);">查看</a></td>
			  </tr>
			  <tr>
			    <td align="center"><p>收益合计 </p></td>
			    <td  colspan="2" align="center"><p>&nbsp;</p></td>
			  </tr>
			  </tbody>
			</table>
			
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
