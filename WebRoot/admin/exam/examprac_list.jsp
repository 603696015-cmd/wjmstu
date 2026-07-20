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
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="练习列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">练习列表 </span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="examprac_addinit.action">练习添加</a>
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


				<table width="100%" align="center" cellpadding="1" cellspacing="1"
					>
					<tr>
						<th width="200" height="30" align="left" style="padding-left:8px;color:blue;">
							练习名称						</th>
						<th width="110" height="30" align="center" >
							开始时间						</th>
						<th width="110" height="30" align="center" >
							结束时间						</th>
						<th width="80" height="30" align="center" >
							已有考生						</th>
						<th width="80" height="30" align="center" >
							状态						</th>
						<th width="220" height="30" align="center" >&nbsp;</th>
					</tr>
					<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
					<s:iterator value="exampracs">
						<tr>
						<td width="200" height="30" align="left" style="padding-left:8px;color:blue;">
								<s:property value="title" />
						  </td>
							<td width="110" height="30" align="center" >
								<s:date name="begintime" format="yyyy-MM-dd HH:mm:ss" />
						  </td>
							<td width="110" height="30" align="center" >
								<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss" />
						  </td>
							<td width="80" height="30" align="center" >
								<s:property value="usersize" />
						  </td>
						  <td width="80" height="30" align="center" >
								<font color="<s:if test="valid==1">red</s:if>">
								<s:property
										value="validName" />
							  </font>
						  </td>
							<td width="230" height="30" align="left" style="padding-left:30px;" >
								<s:if test="valid!=4">
									<a href="examprac_alterinit.action?examprac.id=<s:property value="id"/>" class=textbg4>修改</a>
									<a
										href="examprac_assign_list.action?examprac.id=<s:property value="id"/>" class=textbg6>分配考生</a>		
									<!-- <a onClick="return window.confirm('确定删除？')"
										href="examprac_delete.action?examprac.id=<s:property value="id"/>" class=textbg4>删除</a> -->
									<a onClick="return window.confirm('确定删除？')"
										href="examprac_delete.action?examprac.id=<s:property value="id"/>" class=textbg4>删除</a>
								</s:if>
							<a target="_blank"
								href="exampaper_preview.action?examPaper.id=<s:property value="examPaper.id" />" class=textbg4>预 览</a>							</td>
						</tr>
					</s:iterator> </tbody>
			  </table>
				<DIV style="text-align: center">
					<wysLib:page></wysLib:page>
					<br>
				</DIV>
				<script>
					function page(i){
						document.getElementById("pageNow").value=i;
						erform.submit();
					}
				</script>
			</div>
		</div>
		<form action="examprac_list.action" method="post" name="erform">
			<input type="hidden" name="pN" id="pageNow"/>
			<input type="hidden" name="pS" id="pS"/>
		</form>
		<div align=center><a href="examprac_addinit.action" class="textbg">练习添加</a></div>

		<!-- 内容 -->
	</BODY>
</HTML>
