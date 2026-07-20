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
		<TITLE>五矿发展员工职业发展系统--管理端--用户添加</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
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
			//选择菜单
			function chooseMenu(){
				 width=1200;
				 height=700;
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv =  window.showModalDialog("chooseMenu.action?x="+Math.random(),null,sFeature);
				 if(rv!=undefined&&rv!=""){
					 //var bh=rv.split("_");
					 var bh=rv.split("-=wys=-");
					 document.getElementById("funcid").value=bh[2];
					 document.getElementById("funcmenu").value=bh[1];
				 }
			}
		</script>
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<li>
				<span style="font-weight: bold;">添加新功能</span>

			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="func_list.action">功能管理</a>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
	
		<div style="margin-top: 40px;">
			<s:form action="func_add" method="post" theme="simple">

				<table width="100%" cellpadding="0" cellspacing="1"
					 bgcolor="#D1E4F5">
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
							<strong>功能代码</strong>
						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" >
							<label>
								<s:textfield cssStyle="width:300px" name="func.funccode"/>
							</label>
						</td>
					</tr>
					
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
							<strong>功能名称</strong>
						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" >
							<label>
								<s:textfield cssStyle="width:300px" name="func.name"/>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
							<strong>上级</strong>
						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" >
							<label>
								<select name="func.parent.id" style="width:300px" id="funcmenu">
									<wysLib:funcTreeSelect selectid="${func.parent.id}"></wysLib:funcTreeSelect>
								</select>
							</label>&nbsp;&nbsp;&nbsp;&nbsp;
							<span>
							<input type="button" onClick="chooseMenu()"  value="点此选择">
						</span>
						</td>
					</tr>
						<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
							<strong>参数</strong>
						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" >
							<label>
									<s:textfield cssStyle="width:300px" name="func.params"/>
							</label>
						</td>
					</tr>
						<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
							<strong>连接目标</strong>
						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" >
							<label>
									<s:textfield cssStyle="width:300px" value="rightFrame" name="func.target"/>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
							<strong>描述</strong>
						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" >
							<label>
								<s:textarea cssStyle="width:300px;height:80px;" name="func.description"
									id="email" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
							<strong>对应图片</strong>
						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" >
							<label>
								<s:textfield cssStyle="width:300px" value="default_dy.jpg" name="func.dyimg" id='dyimg'/>
								<a style="color: black; font-weight: bolder;"
								href="javascript:setUrl('dyimg');" class="textbg">浏览资源库</a>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
							<strong>背景图片</strong>
						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" >
							<label>
								<s:textfield cssStyle="width:300px" value="default_bg.jpg" name="func.bgimg" id='bgimg'/>
								<a style="color: black; font-weight: bolder;"
								href="javascript:setUrl('bgimg');" class="textbg">浏览资源库</a>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
							<strong>链接图片</strong>
						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" >
							<label>
								<s:textfield cssStyle="width:300px" value="default_link.jpg" name="func.linkimg" id='linkimg'/>
								<a style="color: black; font-weight: bolder;"
								href="javascript:setUrl('linkimg');" class="textbg">浏览资源库</a>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="50" align="center" bgcolor="#F8FCFE" >&nbsp;
							
						</td>
						<td height="50" align="left" bgcolor="#F8FCFE" >
							<input type="submit" value="确认添加">
						</td>
					</tr>
					<s:hidden name="func.id" id="funcid"></s:hidden>
				</table>
			</s:form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
