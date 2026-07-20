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
		<TITLE>学生学习查询</TITLE>
		<base href="<%=basePath%>">
		<base target="_self">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		
		<script type="text/javascript">
		function page(i){
			document.getElementById("exprot").value=false;
			document.getElementById("pageNow").value = i;
			studentInquiry.submit();
		}
		
		function showStudentClassInfo(userid,classid){
			document.getElementById("exprot").value=false;
			document.getElementById("elUser.id").value = userid;
			document.getElementById("elclass.id").value = classid;
			studentClassInfo.submit();
		}
		function toexcel(exprot) { 
			document.getElementById("exprot").value=exprot;
			studentInquiry.submit();
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
			<form action="studentInquiry.action" name="studentInquiry"
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
						<!-- 
						<td width="300" height="30" align="center">
							等级：
							<s:select theme="simple" headerKey="全部" headerValue="全部"
							name="classification.name"
							list="#{'1A':'1A','1B':'1B','2A':'2A','2B':'2B','3A':'3A','3B':'3B','4A':'4A','4B':'4B','5A':'5A','5B':'5B','6A':'6A','6B':'6B'}"
							value="classification.name" id="classification.name" />
						</td>
						 -->
				 		<td bgcolor="#F8FCFE" width="700">
								学习时间:
							  <input type="text" onclick=setday(this)
									name="start_date"
									value="${start_date }">
								&nbsp;~&nbsp;
								<input type="text" onclick=setday(this)
									name="end_date"
									value="${end_date }">	
						</td>
						<td width="300" height="30" align="center">
							<input type="button" name="查询" value="查询"
								onclick="studentInquiry.submit();" />
						</td>
					</tr>
				</table>
			</form>
			<form action="studentClassInfoPersonal.action" name="studentClassInfo"
				method="post">
				<input type="hidden" name="elUser.id" id="elUser.id" />
				<input type="hidden" name="elclass.id" id="elclass.id" />
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
						性别
					</th>
					<th width="130" height="30" align="center">
						当前级别
					</th>
					<th width="130" height="30" align="center">
						学习进度
					</th>
					<th width="130" height="30" align="center">&nbsp;
						
					</th>
					<th width="130" height="30" align="center">
						详情
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
							<s:property value="sex" />
						</td>
						<td width="100" height="30" align="center">
							<s:if test="nowClass.id>0">
								<s:property value="nowClass.name" />
							</s:if>
							<s:else>
								未定级
							</s:else>
						</td>
						<td width="100" height="30" align="left">
							<s:if test="nowClass.id>0">
								<div
									style="BORDER-BOTTOM: #ff6633 1px dotted; BORDER-LEFT: #ff6633 1px dotted; BORDER-TOP: #ff6633 1px dotted; BORDER-RIGHT: #ff6633 1px dotted">
									<img src="images/jd.gif" width="<s:property value="nowClass.myClass.process" />%" height="14" />								</div>
							</s:if>
					  </td>
						<td width="50" height="30" align="center">
							<s:if test="nowClass.id>0">
								<s:property value="nowClass.myClass.process" />%
							</s:if>
						</td>
						<td width="100" height="30" align="center">
							<s:if test="nowClass.id>0">
								<a class="textbg4"
									href="javascript:showStudentClassInfo(<s:property value="id" />,<s:property value="nowClass.id" />);">查看</a>
							</s:if>
							<s:else>
							</s:else>
						</td>
					</tr>
				</s:iterator>
			</table>
			<wysLib:page></wysLib:page>
			<a href="javascript:toexcel(true);" class="textbg5">导出Excel</a>
		</div>
	</BODY>
</HTML>






