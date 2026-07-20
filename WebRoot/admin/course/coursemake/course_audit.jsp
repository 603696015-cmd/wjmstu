<%@ page language="java" pageEncoding="UTF-8"   %>
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
		<TITLE>课程审核管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="课程审核详情" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">课程审核详情</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<s:form action="course_audit_reply.action" method="post" theme="simple" name="sh">
		<div style="margin-top: 0px;" > 		
			<table cellpadding="1"   cellspacing="1" >
				<tr>
					<td    align="center" >
						审核标题：
					</td>
					<td bgcolor="#FFFFFF"  width="200">
						<label>
							<s:property value="courseAudit.title" />
						</label>
					</td>
				</tr>
				<tr>
					<td    align="center" >
						审核课程：
					</td>
					<td >
						<label>
							<<&nbsp;&nbsp;<s:property value="course.name" />&nbsp;&nbsp;>>
						</label>
					</td>
				</tr>
				<tr>
					<td     align="center" >
						申请时间：
					</td>
					<td >
						<label>
							<s:property value="courseAudit.submittime" />
						</label>
					</td>
				</tr> 
				<tr>
					<td     align="center" >
						申请人：
					</td>
					<td  > 
							<s:property value="elUser.realname" /> 
					</td>
				</tr>
				<tr>
				 <td   align="center" >
						   
							申请标题：  
					</td>
				 <td   align="center" >
						   
							“<s:property value="courseAudit.title"/>”
					</td>
				</tr> 
			</table>	
				<script type="text/javascript">
								function sh_p(){
									if(window.confirm("确定让它通过审核？"))
										sh.action="course_sh.action?status=1";
										sh.submit(); 
								}
								function sh_np(){
									if(window.confirm("确定让它不通过审核？"))
										sh.action="course_sh.action?status=5";
										sh.submit();
										
								}
				</script>
						
							<textarea name="courseAudit.content" cols="60" rows="7" disabled="disabled">
							<s:property value="courseAudit.content"/>
							</textarea><br> 
							回复<br>
							<s:textarea name="courseAudit.replycontent" cols="60" rows="7"></s:textarea> 
							<s:hidden name="courseAudit.id" />
							<s:hidden name="course.id" />
							<s:hidden name="courseAudit.content" />  <br> 
							<input style="height:35px;" class="textbg6" type="button" name="button2" onClick="sh_p();" id="button2" value="通过审核" />
							<input style="height:35px;" class="textbg6" type="button" name="button2" onClick="sh_np();" id="button2" value="不通过审核" />
		</div>
							</s:form>
		<!-- 内容 -->
	</BODY>
</HTML>
