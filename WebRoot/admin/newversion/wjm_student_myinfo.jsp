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
		<TITLE>远程教育网络学院平台--管理端--用户显示</TITLE>
		<link rel="stylesheet" href="css/wjmglobal.css">
		<link rel="stylesheet" href="css/wjmbase.css">
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
	    <style type="text/css">
<!--
.STYLE1 {
	color: #3399FF;
	font-weight: bold;
}

td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
-->



</style>
        </style>
</HEAD>
	<body>

	
		
		
		
		
		
		
		
		
			  <table width="1044" border="0" align="center" cellpadding="0" cellspacing="0" style="background-image: url( images/20140416/cent_bg3.png);background-repeat: no-repeat;background-position: center top;">
                <tr>
                  <td height="550" valign="top" style="padding-top:90px;">
				  
				  
				  
				  <%-- <table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="用户基本信息" /></div>
			</li>
			
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
			 
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>--%>
		

			  <table width="700" border="0" align="center" cellpadding="0" cellspacing="0">
                  <tr>
                    <td height="40" colspan="2" align="center" background="images/bg002.jpg">个人基本信息</td>
                  </tr>
                  <tr>
                    <td width="50%"><table width="100%" align="center" cellpadding="3" cellspacing="1" bgcolor="#CFDBE2" id="info1">
                      <tr>
                        <td width="120" height="30" align="right" bgcolor="#F8FCFE" ><span class="STYLE1">学院 </span></td>
                        <td height="30" align="left" bgcolor="#F8FCFE" ><label> ${elUser.department.name} </label>
                        </td>
                      </tr>
                      <!-- 	<tr>
						<td width="120" height="30" align="center" >
							<strong>身份证</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:property value="elUser.shenfenzheng" />
							</label>
						</td>
					</tr> -->
                      <tr>
                        <td width="120" height="30" align="right" bgcolor="#F8FCFE" ><span class="STYLE1">学号 </span></td>
                        <td height="30" align="left" bgcolor="#F8FCFE" ><label>
                          <s:property value="elUser.username" />
                          </label>
                        </td>
                      </tr>
                      <tr>
                        <td width="120" height="30" align="right" bgcolor="#F8FCFE" ><span class="STYLE1">中文名 </span></td>
                        <td height="30" align="left" bgcolor="#F8FCFE" ><label>
                          <s:property value="elUser.realname" />
                          </label>
                        </td>
                      </tr>
                      <tr>
                        <td width="120" height="30" align="right" bgcolor="#F8FCFE" ><span class="STYLE1">英文名 </span></td>
                        <td height="30" align="left" bgcolor="#F8FCFE" ><label>
                        	 <s:property value="elUser.userno" />
                          </label>
                        </td>
                      </tr>
                      <!-- 	<tr>
						<td align="center">
							<strong> 权限</strong>						</td>
						<td height="30" align="left" >
							<s:property value="elUser.role.name" />
						</td>
					</tr> -->
                     
                      <!-- 	<tr>
						<td width="120" height="30" align="center" >
							<strong>电话号码</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:property value="elUser.movephone" />
							</label>
						</td>
					</tr> -->
                      
                      <!-- 	<tr>
						<td width="120" height="30" align="center">
							<strong> 现职位：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:property value="elUser.xianzhiwei" />
							</label>
							<label>
								
								<a href="sta_view.action?station.id=<s:property value="elUser.staid" />">查看岗位详情</a>
								 
							</label>
						</td>
					</tr>-->
                    </table></td>
                    <td><table width="100%" align="center" cellpadding="3" cellspacing="1" bgcolor="#CFDBE2" id="info1">
                      <!-- 	<tr>
						<td width="120" height="30" align="center" >
							<strong>身份证</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:property value="elUser.shenfenzheng" />
							</label>
						</td>
					</tr> -->
                      <!-- 	<tr>
						<td align="center">
							<strong> 权限</strong>						</td>
						<td height="30" align="left" >
							<s:property value="elUser.role.name" />
						</td>
					</tr> -->
                      <!-- 	<tr>
						<td width="120" height="30" align="center" >
							<strong>电话号码</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:property value="elUser.movephone" />
							</label>
						</td>
					</tr> -->
                      <tr>
                        <td width="120" height="30" align="right" bgcolor="#F8FCFE" ><span class="STYLE1">年级 </span></td>
                        <td height="30" align="left" bgcolor="#F8FCFE" ><label>
                          <s:property value="elUser.school" />
                          </label>                        </td>
                      </tr>
                       <tr>
                        <td width="120" height="30" align="right" bgcolor="#F8FCFE" ><span class="STYLE1">性别 </span></td>
                        <td height="30" align="left" bgcolor="#F8FCFE" ><label>
                          <s:property value="elUser.sex" />
                          </label>
                        </td>
                      </tr>
                      
                      <tr>
                        <td width="120" height="30" align="right" bgcolor="#F8FCFE" ><span class="STYLE1">专业方向 </span></td>
                        <td height="30" align="left" bgcolor="#F8FCFE" ><label>
                          <s:property value="elUser.specialty" />
                          </label>
                        </td>
                      </tr>
                      <tr>
                        <td width="120" height="30" align="right" bgcolor="#F8FCFE" ><span class="STYLE1">国籍 </span></td>
                        <td height="30" align="left" bgcolor="#F8FCFE" ><label>
                          <s:property value="elUser.danwei" />
                          </label>
                        </td>
                      </tr>
                      <!-- 	<tr>
						<td width="120" height="30" align="center">
							<strong> 现职位：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:property value="elUser.xianzhiwei" />
							</label>
							<label>
								
								<a href="sta_view.action?station.id=<s:property value="elUser.staid" />">查看岗位详情</a>
								 
							</label>
						</td>
					</tr>-->
             <!--      <tr>
                        <td width="120" height="30" align="right" bgcolor="#F8FCFE"><span class="STYLE1">
                          <wysLib:BasetName btid="1" />
                          ： </span></td>
                        <td height="30" align="left" bgcolor="#F8FCFE"><label>
                          <s:property value="elUser.jingzhong_" />
                          </label>
                            <label> </label>                        </td>
                      </tr>
                      <tr>
                        <td width="120" height="30" align="right" bgcolor="#F8FCFE"><span class="STYLE1">
                          <wysLib:BasetName btid="2" />
                          ： </span></td>
                        <td height="30" align="left" bgcolor="#F8FCFE"><label>
                          <s:property value="elUser.zhiwu_" />
                          </label>
                            <label> </label>                        </td>
                      </tr>
                      <tr>
                        <td width="120" height="30" align="right" bgcolor="#F8FCFE"><span class="STYLE1">
                          <wysLib:BasetName btid="3" />
                          ： </span></td>
                        <td height="30" align="left" bgcolor="#F8FCFE"><label>
                          <s:property value="elUser.zhiji_" />
                          </label>
                            <label> </label>                        </td>
                      </tr>
                      <tr>
                        <td width="120" height="30" align="right" bgcolor="#F8FCFE"><span class="STYLE1">
                          <wysLib:BasetName btid="5" />
                          ： </span></td>
                        <td height="30" align="left" bgcolor="#F8FCFE"><label>
                          <s:property value="elUser.dishi_" />
                          </label>
                            <label> </label>                        </td>
                      </tr>
                      <tr>
                        <td height="30" align="center" bgcolor="#F8FCFE"><strong>开通状态</strong> </td>
                        <td height="30" align="left" bgcolor="#F8FCFE" ><label>
                          <s:property value="elUser.validName" />
                          </label>                        </td>
                      </tr>-->
                      
                      <tr style="display:none;">
                        <td width="120" height="30" align="center" bgcolor="#F8FCFE" ><strong> 用户头像 </strong> </td>
                        <td bgcolor="#F8FCFE"><s:if test="elUser.touxiang!= null"> <img src="<s:property value="elUser.mainimg_"/>" width="240" height="300"> </s:if>
                            <s:else> <img
									src="<s:property  escape="false" value="elUser.mainimg_"/>"
                              id="cimg_0" width="240" height="300" />
                                  <SCRIPT type="text/javascript">
									obj = document.getElementById("cimg_0");
									addImgs(obj);
								    </SCRIPT>
                            </s:else>                        </td>
                      </tr>
                    </table></td>
                  </tr>
              </table>
		
				  
				  
                  </td>
                </tr>
              </table>
			  <script type="text/javascript">
			function grantManage(userId,roleId){
				if(roleId==1){
					alert('超级管理员，拥有所有权限，不需要赋权！！！');
					return;
				}
				document.location.href="showUserGrant.action?elUser.id="+userId;
			}
		</script>
	
	</BODY>
</HTML>
