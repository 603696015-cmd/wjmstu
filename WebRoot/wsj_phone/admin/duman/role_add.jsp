<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<TITLE>中国食品安全培训网--管理端--用户添加</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="editor/fckeditor.js"></script>
		<script type="text/javascript" src="newversion/setFunc.js"></script>
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
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript">
		function _onsubmit(){
			if($.trim($("#rolename").val())==''){
				alert("请填写角色名称!");
				return false;
			}
			return true;
		}
		
		function myload(){
				var oFCKeditor = new FCKeditor('content') ;
				oFCKeditor.BasePath = "editor/" ;
				oFCKeditor.Height = 400;
				oFCKeditor.Width = 980;
				oFCKeditor.ReplaceTextarea();
				setCurTime("releasetime");
		}
	</script>
	<script type="text/javascript">
			function setUrl(obj) {
				width=1060;
				height=500;
			   	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				var rv = window.showModalDialog("question_stuffList.action?x="+Math.random(),
				 window,sFeature);
				
				 if(null==rv){
				 	alert("没选择资源");
				 	return ;
				 }
				 document.getElementById(obj).value=rv;
			}
			
			function setFunc(objid,roleid){
				width=screen.availWidth * 0.8;;
				height=screen.availHeight * 0.8;;
				var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				var rv =  window.showModalDialog("rolefunc_addInit_only_can_check_one.action?x="+Math.random()+"&role.id="+roleid,null,sFeature);
				if(rv!=undefined&&rv!=""){
					rv = parseInt(rv);
					var tc = getFuncById(rv);
					document.getElementById(objid).value=tc.id;
					document.getElementById("func_"+objid).value=tc.name;
				}
			}
			
			function getFuncById(id){
				var value ;
				$.ajax(
				 {	    async:false,  
						type:"post",   
					    url:"getFuncById_newVersion.action",   
					    data:{"x":Math.random(),"func.id":id},   
						success:function(data){
							data = eval("("+data+")").check_json_result;
							value = data;
					    }
			     });
			     return value;
			}
		</script>
	</HEAD>
	<body onload="myload();">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="添加新角色" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		

		<div style="text-align: center;width:980px">
			<s:form action="role_add" method="post" onsubmit="return _onsubmit();" theme="simple">
				<table width="100%" cellpadding="1" cellspacing="1">
					<tr>
						<td width="120" height="30" align="right">
							<span class="neededitem">*</span><strong>角色名称：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:textfield id="rolename" cssStyle="width:300px" name="role.name" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right">
							<strong>描 述：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:textarea cssStyle="width:300px;height:80px;"
									name="role.description" id="email" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right">
							<strong>选择背景图片：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:textfield id="beijingimg" cssStyle="width:300px" value="images/imgbox1-bg.jpg" name="role.beijingimg" />
								<a style="color: black; font-weight: bolder;"
								href="javascript:setUrl('beijingimg');" class="textbg">浏览资源库</a>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right">
							<strong>常用功能1：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:textfield id="func_common1" cssStyle="width:300px" name="role.func_common1.name" />
								<s:hidden id="common1" cssStyle="width:300px" name="role.common1" />
								<a style="color: black; font-weight: bolder;"
								href="javascript:setFunc('common1',<s:property value="#session.roleid" />);" class="textbg">选择功能</a>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right">
							<strong>常用功能2：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:textfield id="func_common2" cssStyle="width:300px" name="role.func_common2.name" />
								<s:hidden id="common2" cssStyle="width:300px" name="role.common2" />
								<a style="color: black; font-weight: bolder;"
								href="javascript:setFunc('common2',<s:property value="#session.roleid" />);" class="textbg">选择功能</a>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right">
							<strong>常用功能3：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:textfield id="func_common3" cssStyle="width:300px" name="role.func_common3.name" />
								<s:hidden id="common3" cssStyle="width:300px" name="role.common3" />
								<a style="color: black; font-weight: bolder;"
								href="javascript:setFunc('common3',<s:property value="#session.roleid" />);" class="textbg">选择功能</a>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right">
							<strong>常用功能4：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:textfield id="func_common4" cssStyle="width:300px" name="role.func_common4.name" />
								<s:hidden id="common4" cssStyle="width:300px" name="role.common4" />
								<a style="color: black; font-weight: bolder;"
								href="javascript:setFunc('common4',<s:property value="#session.roleid" />);" class="textbg">选择功能</a>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right">
							<strong>常用功能5：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:textfield id="func_common5" cssStyle="width:300px" name="role.func_common5.name" />
								<s:hidden id="common5" cssStyle="width:300px" name="role.common5" />
								<a style="color: black; font-weight: bolder;"
								href="javascript:setFunc('common5',<s:property value="#session.roleid" />);" class="textbg">选择功能</a>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right">
							<strong>常用功能6：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:textfield id="func_common6" cssStyle="width:300px" name="role.func_common6.name" />
								<s:hidden id="common6" cssStyle="width:300px" name="role.common6" />
								<a style="color: black; font-weight: bolder;"
								href="javascript:setFunc('common6',<s:property value="#session.roleid" />);" class="textbg">选择功能</a>
							</label>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center" bgcolor="#FFFFFF">
							提示语
							<span style="font-size: 14px; color: blue"><strong>注意：</strong>下面为角色的提示语！</span>
						</td>
					</tr>
				</table>
				<div style="text-align: center; width: 100%">
				<s:textarea name="role.tishiyu" id="content" cols="60" rows="7"
					cssStyle="width: 980px; height: 440px; visibility: hidden;" />
				</div>
				<div style="text-align: center;">
					<input class="textbg6" type="submit" value="确认添加" style="width:90px">
					<a class="textbg4" style="width: 90px;" href="role_list.action">取消</a>
				</div>
			</s:form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
