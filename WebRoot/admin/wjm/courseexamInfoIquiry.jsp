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
		<TITLE>课程考试查询</TITLE>
		<base href="<%=basePath%>">
		<base target="_self">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
		function page(i){
			document.getElementById("exprot").value=false;
			document.getElementById("pageNow").value = i;
			courseexamInfoIquiry.submit();
		}
		
		function toexcel(exprot) { 
			document.getElementById("exprot").value=exprot;
			courseexamInfoIquiry.submit();
		}
		function searchDepInit(){
			     width=600;
				 height=500;
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv =  window.showModalDialog("userRegister.action?x="+Math.random(),null,sFeature);
				 //alert(rv);
				 if(rv!=undefined&&rv!=""){
					 //var bh=rv.split("_");
					 var bh=rv.split("-=wys=-");
					 document.getElementById("searchDep.id").value=bh[2];
					 document.getElementById("searchDep.name").value=bh[1];
				 }
			}
		</script>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%;
	height: 30px;
}

tr {
	background-color: expression((       this .       sectionRowIndex %       2 ==       0)
		?       "#ffffff" :       "#f4f4f4" )
}
</style>
	</HEAD>
	<BODY>
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

		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;" class="divClass">
			<form action="courseexamInfoIquiry.action" name="courseexamInfoIquiry"
				method="post">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<s:hidden name="searchDep.id" id="searchDep.id" />
				<s:hidden name="exprot" id="exprot" />
				<table width="980px" align="center" cellpadding="1" cellspacing="1">
					<tr>
						<td width="300" height="30" align="center">
							部门：
							<input type="text" name="searchDep.name" readOnly
								onclick="searchDepInit();" />
						</td>
						<td width="300" height="30" align="center">
							姓名：
							<input type="text" name="searchUser.realname" />
						</td>
						<td width="300" height="30" align="center">
							<input type="button" name="查询" value="查询"
								onclick="courseexamInfoIquiry.submit();" />
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div style="margin-top: 0px; text-align: center;">
			<table width="980px" align="center" cellpadding="1" cellspacing="1">
				<tr>
					<th width="130">
						部门
					</th>
					<th width="130" height="30" align="center">
						姓名
					</th>
					<th width="130" height="30" align="center">
						单元
					</th>
					<th width="130" height="30" align="center">
						开始时间
					</th>
					<th width="130" height="30" align="center">
						结束时间
					</th>
					<th width="130" height="30" align="center">
						时长
					</th>
					<th width="130" height="30" align="center">
						得分
					</th>
					<th width="130" height="30" align="center">
						
					</th>
				</tr>
				<s:iterator value="elUsers">
					<tr>

						<td width="100" height="30" align="center">
							<s:property value="department.name" />
						</td>
						<td width="100" height="30" align="center">
							<s:property value="realname" />
						</td>
						<td width="100" height="30" align="center">
							<s:property value="course.name"/>
						</td>
						<td width="200" height="30" align="center">
							<s:date name="log.begintime" format="yyyy年MM月dd日 HH时mm分"/>
						</td>
						<td width="200" height="30" align="center">
							<s:date name="log.endtime" format="yyyy年MM月dd日 HH时mm分"/>
						</td>
						<td width="100" height="30" align="center">
							<s:property value="log.passtime"/>秒
						</td>
						<td width="100" height="30" align="center">
							<s:property value="log.score"/>分
						</td>
						<td width="100" height="30" align="center">
							<s:if test="course.myExamPaper.id>0">
								<a href="myquizpaperview.action?myExamPaper.id=<s:property value="course.myExamPaper.id" />" target="_blank"/>查看答卷</a>
							</s:if>
						</td>
					</tr>
				</s:iterator>
			</table>
			<wysLib:page></wysLib:page>
			<a href="javascript:toexcel(true);" class="textbg5">导出Excel</a>
		</div>
	</BODY>
</HTML>






