<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.lineTrainingCourse.entities.TrainTypeTree"%>
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
		<TITLE></TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript">
			function doSubmit(){
				var title=document.getElementById("traintypeName");
				title=title.value.replace(/(\s*$)/g, "");
				if(title==""){
					alert("类别名称不能为空!");
					return false;
				}
				return true;
			}
		</script>
	</HEAD>
	<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="填写类别基本信息" /></div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<s:form action="trainType_add" method="post" name="catalog_info"
			theme="simple" onsubmit="return doSubmit();">
			<table width="60%" cellpadding="2" cellspacing="1" >
				<tr>
					<td width="120" height="30" align="center" >
						类别名称
					</td>
					<td >
						<label>
							<s:textfield name="ptype.name" id="traintypeName" size="60" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="center" >
						类别介绍
					</td>
					<td >
						<label>
							<s:textarea name="ptype.description" cols="60" rows="7" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" height="30" align="center" >
						上级类别<%
						TrainTypeTree uu = ((TrainTypeTree) request
										.getAttribute("ptype"));
								String xx = "0";
								xx = uu == null ? "1" : uu.getParent() == null ? "1" : uu
										.getParent().getId()
										+ "";
						%>
					</td>
					<td >
						<label>
							<wysLib:TrainTypeTree did="0" iname="ptype.parent.id" 
									itype="ra_2no" ivalue="<%=xx%>" iid="${ptype.id}" ></wysLib:TrainTypeTree>
						</label>
					</td>
				</tr>
				<tr>
					<td width="120" height="50" align="center" >&nbsp;
						
					</td>
					<td >
						<input class="textbg6" type="submit" value="确认添加">
					</td>
				</tr>
			</table>
			<br>
		</s:form>
	</body>
</HTML>
