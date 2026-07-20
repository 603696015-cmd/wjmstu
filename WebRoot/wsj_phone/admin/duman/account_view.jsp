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
		<TITLE>中国食品安全培训网--管理端--用户显示</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<SCRIPT type="text/javascript">
		 var imgs = new Array();
	function addImgs(obj){
		imgs[imgs.length]=obj;
	}
	function setImgs(){
		for(var i=0;i<imgs.length;i++){
			if(imgs[i].fileSize<=0){
			 imgs[i].src="elfrontimages/coursedimg.jpg";
			}
		}
	} 
	</SCRIPT>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="用户基本信息" /></div>
			</li>
			<%-- 
			<li>
				<span style="font-weight: bold;">显示用户信息</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="account_alterInit.action?elUser.id=<s:property value="elUser.id"/>">编辑用户信息</a>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="account_deleteInit.action?elUser.id=<s:property value="elUser.id"/>">删除用户</a>
			</li>
			 --%>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		

		<div style="margin-top: 0px; float: left;">
			<div style="margin-top: 0px;">
				<table id="info1" width="500px" cellpadding="1" cellspacing="1">
					<caption>
						基本信息
					</caption>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>单位/部门</strong>
						</td>
						<td height="30" align="left" >
							<label>
								${elUser.department.name}
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>用户名</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:property value="elUser.username" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>密 码</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<!--<s:property value="elUser.password" />-->
							</label>
						</td>
					</tr>
					<tr>
						<td align="center">
							<strong> 权限</strong>						</td>
						<td height="30" align="left" >
							<s:property value="elUser.role.name" />
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>序号</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:property value="elUser.xuhao" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>姓 名</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:property value="elUser.realname" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>性别</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:property value="elUser.sex" />
							</label>
						</td>
					</tr>
		<!--			<tr>
						<td width="120" height="30" align="center" >
							<strong> <wysLib:BasetName btid="5" /></strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:property value="elUser.dishi_" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong> 单位 </strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:property value="elUser.danwei" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong> 身份证号 </strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:property value="elUser.shenfenzheng" />
							</label>
						</td>
					</tr>-->
					<tr>
						<td width="120" height="30" align="center" >
							<strong>出生日期</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:date format="yyyy-MM-dd" name="elUser.shengri_" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>年龄</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:property value="elUser.age"/>
							</label>
						</td>
					</tr>
			<!--		<tr>
						<td width="120" height="30" align="center" >
							<strong>手机号码</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:property value="elUser.movephone" />
							</label>
						</td>
					</tr>-->
					<tr>
						<td width="120" height="30" align="center" >
							<strong>电子邮箱</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:property value="elUser.email" />
							</label>
						</td>
					</tr>
			<!--		<tr>
						<td width="120" height="30" align="center" >
							<strong> <wysLib:BasetName btid="3" /> </strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:property value="elUser.zhiji_" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong> <wysLib:BasetName btid="2" /> </strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:property value="elUser.zhiwu_" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong> <wysLib:BasetName btid="1" /> </strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:property value="elUser.jingzhong_" />
							</label>
						</td>
					</tr>-->
					<tr>
						<td width="120" height="30" align="center">
							<strong> 民族：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:property value="elUser.minzu" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center">
							<strong> 籍贯：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:property value="elUser.jiguan" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center">
							<strong> 毕业院校：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:property value="elUser.school" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center">
							<strong> 学历：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:property value="elUser.xueli" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center">
							<strong> 学位：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:property value="elUser.xuewei" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center">
							<strong> 专业：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:property value="elUser.specialty" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center">
							<strong> 参加工作时间：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:date name="elUser.canjiagongzuoshijian" format="yyyy年MM月dd日"/>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center">
							<strong> 入司时间：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:date name="elUser.rusishijian" format="yyyy年MM月dd日"/>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center">
							<strong> 现任职时间：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:date name="elUser.xianrenzhishijian" format="yyyy年MM月dd日"/>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center">
							<strong> 政治面貌：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:property value="elUser.zhengzhimianmao" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center">
							<strong> 拼音简写：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:property value="elUser.pinyinjianxie" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center">
							<strong> 出生地：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:property value="elUser.chushengdi" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center">
							<strong> 现员工组：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:property value="elUser.xianyuangongzu" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center">
							<strong> 现岗位：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:property value="elUser.xianzhiwei" />
							</label>
							<label>
								<a href="sta_view.action?station.id=<s:property value="elUser.staid" />">查看岗位详情</a>
							</label>
						</td>
					</tr>
			<!--		 <tr>
					<td width="120" height="30" align="center" >
						<strong> 用户头像 </strong>
					</td>
					<td>
					<s:if test="elUser.touxiang!= null">
															<img src="<s:property value="elUser.mainimg_"/>" width="240" height="300"> 
													</s:if><s:else> 
														<img
															src="<s:property  escape="false" value="elUser.mainimg_"/>"
															id="cimg_0" width="240" height="300" />
														<SCRIPT type="text/javascript">
															obj = document.getElementById("cimg_0");
															addImgs(obj);
														</SCRIPT> 
													</s:else>	
					</td>
					</tr>-->
					<tr>
						<td align="center">
							<strong>开通状态</strong>						</td>
						<td height="30" align="left" >
							<label>
								<s:property value="elUser.validName" />
							</label>
						</td>
					</tr>
				</table>
				<s:if test="Return!='mem'">
					<a href="account_search.action" style="width: 100px" class="textbg4">返回用户列表</a>
					<a href="account_alterInit.action?elUser.id=<s:property value="elUser.id"/>" style="width: 100px" class="textbg4">修改</a>
				</s:if>
				<s:else>
					<a href="#" onClick="window.close();return false;" style="width: 60px" class="textbg4">关闭</a>
				</s:else>
				<input type="button" class="textbg4" style="width:50px" onClick="grantManage('<s:property value="elUser.id"/>','<s:property value="elUser.role.id"/>');" value="授权">
			</div>
			<script type="text/javascript">
			function grantManage(userId,roleId){
				if(roleId==1){
					alert('超级管理员，拥有所有权限，不需要赋权！！！');
					return;
				}
				document.location.href="showUserGrant.action?elUser.id="+userId;
			}
		</script>
		</div>
	
	</body>
</HTML>
