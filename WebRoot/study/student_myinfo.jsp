
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
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />

		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%;padding-right: 8px;}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>

		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/jquery.validate.js"></script>
		<script type="text/javascript" src="js/userCheck.js"></script>
		<script type="text/javascript">
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
	</script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="查看" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">基本信息</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		
			<table width="100%" cellpadding="1" cellspacing="1" bgcolor="#D1E4F5" id="info1">
				<caption>
					基本信息
				</caption>
				<tr>
					<td height="30" align="right" bgcolor="#F8FCFE" >
						<strong>单位/部门：</strong>					</td>
					<td height="30" align="left" bgcolor="#F8FCFE" style="padding-left:8px;">
					  <label>
							${elUser.department.name}
						</label>
					</td>
				</tr>
				<tr>
					<td  height="30" align="right" bgcolor="#F8FCFE" >
						<strong>用户名：</strong>
					</td>
					<td height="30" align="left" bgcolor="#F8FCFE" style="padding-left:8px;">
					  <label>
							<s:property value="elUser.username" />
						</label>
					</td>
				</tr>
				<!--<tr>
					<td  height="30" align="center" >
						<strong>密 码</strong>
					</td>
					<td height="30" align="left" style="padding-left:8px;">
						<label>
							<s:property value="elUser.password" />
						</label>
					</td>
				</tr>
				--><tr>
					<td  align="right" bgcolor="#F8FCFE">
						<strong> 权限：</strong>
					</td>
					<td height="30" align="left" bgcolor="#F8FCFE" style="padding-left:8px;">
						<s:property value="elUser.role.name" />
					</td>
				</tr>
				<tr>
					<td  height="30" align="right" bgcolor="#F8FCFE" >
						<strong>序号：</strong>
					</td>
					<td height="30" align="left" bgcolor="#F8FCFE" style="padding-left:8px;">
					  <label>
							<s:property value="elUser.xuhao" />
						</label>
					</td>
				</tr>
				<tr>
					<td  height="30" align="right" bgcolor="#F8FCFE" >
						<strong>姓 名：</strong>
					</td>
					<td height="30" align="left" bgcolor="#F8FCFE" style="padding-left:8px;">
					  <label>
							<s:property value="elUser.realname" />
						</label>
					</td>
				</tr>
				<tr>
					<td  height="30" align="right" bgcolor="#F8FCFE" >
						<strong>性别：</strong>
					</td>
					<td height="30" align="left" bgcolor="#F8FCFE" style="padding-left:8px;">
					  <label>
							<s:property value="elUser.sex" />
						</label>
					</td>
				</tr>
		<!--   <tr>
					<td  height="30" align="right" style="padding-left:8px;">
						<strong> <wysLib:BasetName btid="5" />：</strong>
					</td>
					<td height="30" align="left" style="padding-left:8px;">
						<label>
							<s:property value="elUser.dishi_" />
						</label>
					</td>
				</tr> -->		
		<!-- 	<tr>
					<td  height="30" align="right" style="padding-left:8px;">
						<strong> <wysLib:BasetName btid="6" />：</strong>
					</td>
					<td height="30" align="left" style="padding-left:8px;">
						<label>
							<s:if test="elUser.luntanjibie_!=0">
								<s:property value="elUser.luntanjibie_" />
							</s:if>
						</label>
					</td>
				</tr> -->	
				<!--<tr>
					<td  height="30" align="center" >
						<strong> 单位 ：</strong>
					</td>
					<td height="30" align="left" style="padding-left:8px;">
						<label>
							<s:property value="elUser.danwei" />
						</label>
					</td>
				</tr>
				-->
		<!-- 	<tr>
					<td  height="30" align="right" >
						<strong> 身份证号：</strong>
					</td>
					<td height="30" align="left" style="padding-left:8px;">
						<label>
							<s:property value="elUser.shenfenzheng" />
						</label>
					</td>
				</tr> -->	
				<tr>
					<td  height="30" align="right" bgcolor="#F8FCFE" >
						<strong>出生日期：</strong>
					</td>
					<td height="30" align="left" bgcolor="#F8FCFE" style="padding-left:8px;">
					  <label>
							<s:date format="yyyy-MM-dd" name="elUser.shengri" />
						</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="right" bgcolor="#F8FCFE" >
						<strong>年龄：</strong></td>
					<td height="30" align="left" bgcolor="#F8FCFE" >
					  <label>
							<s:property value="elUser.age"/>
						</label>
					</td>
				</tr>
		<!-- 	<tr>
					<td  height="30" align="right" >
						<strong>手机号码：</strong>
					</td>
					<td height="30" align="left" style="padding-left:8px;" >
						<label>
							<s:property value="elUser.movephone" />
						</label>
					</td>
				</tr> -->	
				<tr>
					<td  height="30" align="right" bgcolor="#F8FCFE" >
						<strong>电子邮箱：</strong>
					</td>
					<td height="30" align="left" bgcolor="#F8FCFE" style="padding-left:8px;" >
					  <label>
							<s:property value="elUser.email" />
						</label>
					</td>
				</tr>
		<!-- 	<tr>
					<td  height="30" align="right" >
						<strong> <wysLib:BasetName btid="3" />：</strong>
					</td>
					<td height="30" align="left" style="padding-left:8px;">
						<label>
							<s:property value="elUser.zhiji_" />
						</label>
					</td>
				</tr>
				<tr>
					<td  height="30" align="right" >
						<strong> <wysLib:BasetName btid="2" />：</strong>
					</td>
					<td height="30" align="left" style="padding-left:8px;">
						<label>
							<s:property value="elUser.zhiwu_" />
						</label>
					</td>
				</tr>
				<tr>
					<td  height="30" align="right" >
						<strong> <wysLib:BasetName btid="1" />：</strong>
					</td>
					<td height="30" align="left" style="padding-left:8px;">
						<label>
							<s:property value="elUser.jingzhong_" />
						</label>
					</td>
				</tr> -->	
				<tr>
						<td height="30" align="right" bgcolor="#F8FCFE">
							<strong> 民族：</strong>						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" style="padding-left:8px;">
						  <label>
								<s:property value="elUser.minzu" />
							</label>
						</td>
			  </tr>
					<tr>
						<td height="30" align="right" bgcolor="#F8FCFE">
							<strong> 籍贯：</strong>						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" style="padding-left:8px;">
						  <label>
								<s:property value="elUser.jiguan" />
							</label>
						</td>
					</tr>
					<tr>
						<td height="30" align="right" bgcolor="#F8FCFE">
							<strong> 毕业院校：</strong>						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" style="padding-left:8px;">
						  <label>
								<s:property value="elUser.school" />
							</label>
						</td>
					</tr>
					<tr>
						<td height="30" align="right" bgcolor="#F8FCFE">
							<strong> 学历：</strong>						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" style="padding-left:8px;">
						  <label>
								<s:property value="elUser.EducationName" />
							</label>
						</td>
					</tr>
					<tr>
						<td height="30" align="right" bgcolor="#F8FCFE">
							<strong> 学位：</strong>						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" style="padding-left:8px;">
						  <label>
								<s:property value="elUser.xuewei" />
							</label>
						</td>
					</tr>
					<tr>
						<td height="30" align="right" bgcolor="#F8FCFE">
							<strong> 专业：</strong>						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" style="padding-left:8px;">
						  <label>
								<s:property value="elUser.specialty" />
							</label>
						</td>
					</tr>
					<tr>
						<td height="30" align="right" bgcolor="#F8FCFE">
							<strong> 参加工作时间：</strong>						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" style="padding-left:8px;">
						  <label>
								<s:date name="elUser.canjiagongzuoshijian" format="yyyy年MM月dd日"/>
							</label>
						</td>
					</tr>
					<tr>
						<td height="30" align="right" bgcolor="#F8FCFE">
							<strong> 入司时间：</strong>						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" style="padding-left:8px;">
						  <label>
								<s:date name="elUser.rusishijian" format="yyyy年MM月dd日"/>
							</label>
						</td>
					</tr>
					<tr>
						<td height="30" align="right" bgcolor="#F8FCFE">
							<strong> 现任职时间：</strong>						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" style="padding-left:8px;">
						  <label>
								<s:date name="elUser.xianrenzhishijian" format="yyyy年MM月dd日"/>
							</label>
						</td>
					</tr>
					<tr>
						<td height="30" align="right" bgcolor="#F8FCFE">
							<strong> 政治面貌：</strong>						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" style="padding-left:8px;">
						  <label>
								<s:property value="elUser.zhengzhimianmao" />
							</label>
						</td>
					</tr>
					<tr>
						<td height="30" align="right" bgcolor="#F8FCFE">
							<strong> 拼音简写：</strong>						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" style="padding-left:8px;">
						  <label>
								<s:property value="elUser.pinyinjianxie" />
							</label>
						</td>
					</tr>
					<tr>
						<td height="30" align="right" bgcolor="#F8FCFE">
							<strong> 出生地：</strong>						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" style="padding-left:8px;">
						  <label>
								<s:property value="elUser.chushengdi" />
							</label>
						</td>
					</tr>
					<tr>
						<td height="30" align="right" bgcolor="#F8FCFE">
							<strong> 现员工组：</strong>						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" style="padding-left:8px;">
						  <label>
								<s:property value="elUser.xianyuangongzu" />
							</label>
						</td>
					</tr>
					<tr>
						<td height="30" align="right" bgcolor="#F8FCFE">
							<strong> 现岗位：</strong>						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" style="padding-left:8px;">
						  <label>
								<s:property value="elUser.xianzhiwei" />
							</label>
						</td>
					</tr>
		<!--	  <tr>
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
					</tr> -->	
				<tr>
					<td align="right" bgcolor="#F8FCFE">
						<strong>开通状态：</strong>
					</td>
					<td height="30" align="left" bgcolor="#F8FCFE" style="padding-left:8px;">
					  <label>
							<s:property value="elUser.validName" />
						</label>
					</td>
				</tr>
		  </table>
		<!-- 内容 -->
	</BODY>
</HTML>
