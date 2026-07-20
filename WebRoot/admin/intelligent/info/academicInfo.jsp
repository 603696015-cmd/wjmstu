<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="newversionLib" uri="/WEB-INF/newversionLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>个人中心新首页</title>
		<link href="css/global.css" rel="stylesheet" type="text/css" />
		<link href="css/index.css" rel="stylesheet" type="text/css" />
		<link type="text/css" href="css/base.css" rel="stylesheet" />
		<link type="text/css" href="css/qhIndex.css" rel="stylesheet" />
		<link href="css/style2013.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		
		<script type="text/javascript">
		function page(i){
			document.getElementById("pageNow").value = i;
			academicInfo.submit();
		}
		function formSubmit(){
			_onSubmit();
			academicInfo.submit();
		}
		
		var elclassid = <s:property value="elClass.id" />;
		var courseid = <s:property value="course.id" />;
		var coursepageid = <s:property value="coursePage.id" />;
		
		function _onSubmit(){
			document.getElementById("elClass.id").value = elclassid;
			document.getElementById("course.id").value = courseid;
			document.getElementById("coursePage.id").value = coursepageid;
			return true;
		}
		
		function changeSelect1(){
			var select1 = document.getElementById("select1");
			select1.value=select1.options[select1.selectedIndex].value;
			elclassid = select1.value;
			//联动select2
			createSelect2Elements(select1.value);
			
		}
		function changeSelect2(){
			var select2 = document.getElementById("select2");
			select2.value=select2.options[select2.selectedIndex].value;
			courseid = select2.value;
			//联动select3
			createSelect3Elements(select2.value);
		}
		function changeSelect3(){
			var select3 = document.getElementById("select3");
			select3.value=select3.options[select3.selectedIndex].value;
			coursepageid = select3.value;
		}
		function createSelect2Elements(classid){
			$.post("createSelect2Elements.action", {
				"elClass.id":classid,
				"x":Math.random()
			}, 
			function (data) {
				$("#select2").remove();
				$("#select2td" ).append(data);
			});
		}	
		function createSelect3Elements(courseid){
			$.post("createSelect3Elements.action", {
				"course.id":courseid,
				"x":Math.random()
			}, 
			function (data) {
				$("#select3").remove();
				$("#select3td" ).append(data);
			});
		}
		window.onload = function(){
			//初始化select2
			createSelect2Elements(document.getElementById("elClass.id").value);
		}
		</script>
		<style>
#ddd img {
	display: block;
}

.STYLE1 {
	font-size: 36px;
	font-weight: bold;
}
</style>

	</head>

	<body >
		<table width="900" border="0" align="center" cellpadding="0"
					cellspacing="0" style="margin-top:20px;">
						<tr>
							<td align="center" valign="middle" background="images/bg002.jpg"
								>
								
									等级选择
									<select  id="select1" onchange="changeSelect1();">
										<s:set name="nowclassid" value="elClass.id"></s:set>
										<s:iterator value="classifications">
											<s:if test="#nowclassid==elClass.id">
												<option value="<s:property value="elClass.id"/>" selected="selected">
														<s:property value="elClass.name" />
												</option>
											</s:if>
											<s:else>
												<option value="<s:property value="elClass.id"/>">
														<s:property value="elClass.name" />
												</option>
											</s:else>
										</s:iterator>
									</select>
							
						  </td>
							<td height="38" align="center" valign="middle" background="images/bg002.jpg"
								>
								
									<p align="center" id="select2td">
									单元选择
									
								</p>						  </td>
							<td align="center" valign="middle" background="images/bg002.jpg"
								>
								
									<p align="center" id="select3td">
									模块选择
								</p>
								
						  </td>
							<td align="center" valign="middle" background="images/bg002.jpg"
								>
								
									<input type="button" value="查询" onclick="formSubmit();" />
								
						  </td>
						</tr>
	</table>
					<s:form action="academicInfo.action" method="post" theme="simple"
						name="academicInfo" onsubmit="return _onSubmit();">
					<s:hidden name="pN" id="pageNow"></s:hidden>
					<s:hidden name="pS"></s:hidden>
					<s:hidden name="elClass.id" id="elClass.id"></s:hidden>
					<s:hidden name="course.id"  id="course.id"></s:hidden>
					<s:hidden name="coursePage.id"  id="coursePage.id"></s:hidden>
						<s:if test="academicInfos.size()==0">
							<table width="900" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> <td height="200" align="center" bgcolor="#F8FCFE"><span class="STYLE2">暂 无 数 据 </span></td>
  </tr>
</table>
 <p>&nbsp;</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				</s:if>
					  <s:else>
						
						<table width="900" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
<table width="900" border="0" align="center" cellpadding="1"
					cellspacing="1" bgcolor="#CFDBE2">
								<tr>
									<td align="center" valign="middle" background="images/bg002.jpg"
										>
										
											等级名称
										
								  </td>
									<td height="38" align="center" valign="middle" background="images/bg002.jpg"
										>
										
											单元名称										
								  </td>
									<td align="center" valign="middle" background="images/bg002.jpg"
										>
									
											模块名称
										
								  </td>
									<td width="120" align="center" valign="middle" background="images/bg002.jpg"
										>
									
											得分
										
								  </td>
									<td width="120" align="center" valign="middle" background="images/bg002.jpg"
										>
										
											查看详情
										
								  </td>
								</tr>
								<s:iterator value="academicInfos">
									<tr>
										<td align="center" valign="middle" bgcolor="#F8FCFE"
											>
											<p align="center">
												<s:property value="elClass.name"/>
											</p>
									  </td>
										<td height="40" align="center" valign="middle" bgcolor="#F8FCFE"
											>
											<p align="center">
												<s:property value="course.name"/>
											</p>
									  </td>
										<td align="center" valign="middle" bgcolor="#F8FCFE"
											>
											<p align="center">
												<s:property value="coursePage.title"/>
											</p>
									  </td>
										<td width="120" align="center" valign="middle" bgcolor="#F8FCFE"
											>
											<p align="center">
												<s:property value="totalscore"/>
											</p>
									  </td>
										<td width="120" align="center" valign="middle" bgcolor="#F8FCFE"
											>
											
											
											<table width="95" border="0" align="center" cellpadding="0" cellspacing="0">
                                              <tr>
                                                <td height="28" align="center" valign="middle" background="images/textbg.jpg">
                                           <%--      <a href="myquizpaperlist.action?examPaper.id=<s:property value="coursePage.examRoom.examPaper.id" />&examRoom.id=<s:property value="coursePage.examRoom.id" />">--%>
                                                  <a href="quizpaperinit.action?myroom.examroom.id=<s:property value="coursePage.examRoom.id" />">
                                                <span style="font-size:14px;font-weight:bold;color:white;">查 看</span></a></td>
                                              </tr>
                                            </table>
									  </td>
									</tr>
								</s:iterator>
		  </table>
	</td>
  </tr>
  <tr>
    <td height="50" align="center" bgcolor="#F8FCFE"><wysLib:page></wysLib:page></td>
  </tr>
</table>

							
							
						<p>&nbsp;</p>
						<p>&nbsp;</p>
					  </s:else>
					</s:form>
				
			

	</body>
</html>



