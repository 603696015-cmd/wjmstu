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
		<TITLE>备注信息添加</TITLE>
		<base target="_self" href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/course.js"></script>
		<script type="text/javascript"> 
		 function aa()
			  {
			      var str = document.getElementById("notecontent").value;
			      var lab = document.getElementById('notecontent_c');
			      lab.innerHTML = "当前字符数量"+ str.replace(/[^\x00]/g,"**").length/2
		         // if(str.replace(/[^\x00]/g,"**").length/2 > 63)
		         // {
		            //lab.innerHTML = "(63个字)";
		         //  alert('输入的字符数不能超过63');
		         // } 
			  }   
			function _onsubmit(){
				if(document.getElementById("operate").value==""){
					alert("操作标题不能为空");
					document.getElementById("operate").focus();
					return false;
				}	
				if(document.getElementById("phone").value==""){
					alert("电话号码不能为空");
					document.getElementById("phone").focus();
					return false;
				}	
				if(!checkPhone()){
					return false; 
				}
				if(document.getElementById("notecontent").value==""){
					alert("操作内容不能为空");
					document.getElementById("notecontent").focus();
					return false;
				} 
				window.returnValue = true;    
				window.close();	 
			}
			function checkPhone()
			{
				var ab=/^\d+$|^\d+-?\d+$/;
				///[^0-9]/;
				var phone = document.getElementById("phone").value; 
				if(!ab.test(phone)){ 
					 alert("请正确填写电话号码!");
					 return false;
				}
				//if(phone.length != 11){
				//	 alert("请正确填写电话号码!");
				//	 return false;
				//}
				 return true;
				 //验证电话号码电话号码，包含至今所有号段   
				//var ab=/^(13[0-9]|15[0|3|6|7|8|9]|18[8|9])\d{8}$/;
				//var phone = document.getElementById("phone").value;
				//  if(ab.test(phone) == false)
				//  {
				 //   alert("请正确填写电话号码!");
				 //   return false;
				 // }else{
				 //	return true;
				 //}
			} 
		</script>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
</style>
	</HEAD>
	<body>
		<div id="div"></div>
		<div style="margin-left: 20px;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
				<li>
					<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
						<wysLib:Navigation ivalue="备注填写" />
					</div>
				</li>
			</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
			
			<s:form action="CRE_note_add.action" method="post" theme="simple"
				onsubmit="return _onsubmit();">
				<table width="100%" align="left" cellpadding="2" cellspacing="1">
					<caption>
						<s:if test="examRoom.id != 0 && examRoom.classid!=-2">考核考场【<s:property value="examRoom.title" />】</s:if>
						<s:if test="examRoom.id != 0 && examRoom.classid==-2">调查问卷【<s:property value="examRoom.title" />】</s:if>
						<s:if test="course.id != 0">课程【<s:property value="course.name" />】</s:if>
						<s:if test="elclass.id != 0">培训班【<s:property value="elclass.name" />】</s:if>
						操作备注
					</caption>
					<tr>
						<td align="right" bgcolor="#FFFFFF" style="font: 12px;">
							<span class="neededitem">*</span>操作标题：
						</td>
						<td align="left">
							<input type="text" name="cre_note.operate" id="operate"
								style="width: 440px;" />
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#FFFFFF" style="font: 12px;">
							<span class="neededitem">*</span>电话号码：
						</td>
						<td align="left">
							<input type="text" name="cre_note.phone" id="phone"
								style="width: 440px;" />
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#FFFFFF" style="font: 12px;">
							<span class="neededitem">*</span>详细内容：
						</td>
						<td align="left">
							<textarea id='notecontent' onKeyUp="aa()" name="cre_note.content"
								style="width: 440px; height: 145px;"></textarea>
							<br />
							<span id="notecontent_c"></span>
						</td>
					</tr>
					<tr>
						<td align="center">
						</td>
						<td align="left">
							<s:hidden name="examRoom.id"></s:hidden>
							<s:hidden name="course.id"></s:hidden>
							<s:hidden name="elclass.id"></s:hidden>
							<s:hidden name="Return" value=""></s:hidden>
							<input type="submit" class="textbg3" value="保　存">&nbsp;&nbsp;&nbsp;
							<input type="button" onclick="window.close();" class="textbg3" value="取　消">
						</td>
					</tr>
				</table>
			</s:form>
		</div>
	</body>
</HTML>
