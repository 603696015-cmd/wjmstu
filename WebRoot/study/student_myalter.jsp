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
			.error{color:red;}
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
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
			$.validator.addMethod("idcardno", function(value, element) {
		    	return this.optional(element) || isIdCardNo(value); 
			}, "请正确输入身份证号码"); 
			$.validator.addMethod("toDateValue", function(value, element,param) {
				var date8 = "";
				if(value.length==18){
					date8 = value.substring(6, 14);
				}else{
					date8 ="19"+ value.substring(6, 12);
				}
				date8=date8.substring(0,4)+"-"+date8.substring(4,6)+"-"+date8.substring(6,8);
				$(param).val(date8); 
		    	return true; 
			}, "请正确输入身份证号码"); 
			$.validator.addMethod("myPassword", function(value, element) {
			    return this.optional(element) || /^\w{6,20}$/.test(value);
			}, "输入错误，应输入6-20个字符");
			$.validator.addMethod("myName", function(value, element) {
			    return this.optional(element) || /^\w{6,20}$/.test(value);
			}, "输入错误，应输入6-20个字符");
			$.validator.addMethod("myName2", function(value, element) {
			    return this.optional(element) || /^\w{4,20}$/.test(value);
			}, "输入错误，应输入4-20个字符");
			$.validator.addMethod("myCHName", function(value, element) {
			    return this.optional(element) || /^[^u4E00-u9FA5]{2,5}$/.test(value);
			}, "输入错误，应输入2-5个中文");
			$.validator.addMethod("myMovephone", function(value, element) {
			    return this.optional(element) || /^[\d]{1,20}$/.test(value);
			}, "输入错误，只能输入数字且不能过长");
			$.validator.addMethod("myEmail", function(value, element) {
			    return this.optional(element) || /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value);
			}, "电子邮箱格式错误");
			<%
				String isAll=request.getAttribute("isAll").toString();
				if(isAll=="yes"){
					//都要验证
					%>
					 $(function(){ 
					 	 $("#registerForm").validate({ 
			                rules: {
			                    "elUser.password":{required: true,myPassword:true},
			                    "elUser.confirmPassword":{required: true,equalTo:"#password"},
			                    "elUser.username":{ required: true,myName:true},
			                    "elUser.realname":{required: true},
			                    "elUser.shenfenzheng":{required: true,idcardno:true,toDateValue:"#shengri"},
			                    "elUser.movephone":{ required: false,myMovephone:true},
			                    "elUser.department.name":{required: true},
			                    "elUser.email":{ required: false,myEmail:true}
			                },
			                messages: {
			                    "elUser.password":{required:"请输入密码"},
			                    "elUser.confirmPassword":{required:"请输入确认密码",equalTo:"两次输入密码不一致"},
			                    "elUser.username": {
			                            required: "请输入用户名" 
			                        },
			                    "elUser.realname":{required: "请输入姓名"},
			                    "elUser.shenfenzheng":{required: "请输入身份证号"},
			                    "elUser.department.name":{required:"请选择部门"}
			                }
			            }); 
					 })
					<%
				}else{
					%>
					 $(function(){ 
					 	 $("#registerForm").validate({ 
			                rules: {
			                    "elUser.password":{required: true,myPassword:true},
			                    "elUser.confirmPassword":{required: true,equalTo:"#password"},
			                    "elUser.username":{ required: true,myName2:true},
			                    "elUser.realname":{required: true},
			                    //"elUser.shenfenzheng":{required: true,idcardno:true,toDateValue:"#shengri"},
			                    //"elUser.movephone":{ required: false,myMovephone:true},
			                    //"elUser.shengri":{required: true},
			                    "elUser.department.name":{required: true}
			                },
			                messages: {
			                    "elUser.password":{required:"请输入密码"},
			                    "elUser.confirmPassword":{required:"请输入确认密码",equalTo:"两次输入密码不一致"},
			                    "elUser.username": {
			                            required: "请输入用户名" 
			                        },
			                    "elUser.realname":{required: "请输入姓名"},
			                    //"elUser.shenfenzheng":{required: "请输入身份证号"},
			                    //"elUser.shengri":{required: "请填写出生日期"},
			                    "elUser.department.name":{required:"请选择部门"}
			                }
			            }); 
					 })
					<%
				}
			%>
			function myload(){
				if("${elmessage}"!=""){
				alert( "${elmessage}!");
				}
			}
	</script>
	</HEAD>
	<body onLoad="myload();">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="操作页面" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">修改设置</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<s:form action="student_myalter" method="post" theme="simple" id="registerForm">
		<s:hidden name="elUser.department.id" id="danwei" />
		<s:hidden name="elUser.id" />
			<div style="margin-top: 0px;">
				<table id="info1" cellpadding="1" cellspacing="1" bgcolor="#D1E4F5">
					<caption>基本信息</caption>
					<tr>
						<td align="center" bgcolor="#F8FCFE" >
							<strong>单位/部门</strong>
						</td>
						<td align="left" bgcolor="#F8FCFE" style="padding-left:8px;">
							<%-- 
							<label>
								<select name="elUser.department.id">
									<wysLib:dep_select selectid="${elUser.department.id}" />
								</select>
							</label>
							 --%>
							 <s:if test="#session.roleid==1">
							 	<s:textfield theme="simple" name="elUser.department.name" size="20" id="danweiName" readonly="true" />
								 <a href="#" onClick="searchUserInit();return false;">点此进行选择</a>
							 </s:if>
							 <s:else>
							 	<s:hidden name="elUser.department.name" />
							 	<s:property value="elUser.department.name"/>
							 </s:else>
						</td>
					</tr>
					<tr>
						<td align="center" bgcolor="#F8FCFE" >
							<strong>用户名</strong>
						</td>
						<td align="left" bgcolor="#F8FCFE" style="padding-left:8px;">
							<label>
								<s:property value="elUser.username"/>
							</label>
						</td>
					</tr>
					<tr>
						<td align="center" bgcolor="#F8FCFE">
							<strong> 角色</strong></td>
						<td align="left" bgcolor="#F8FCFE" style="padding-left:8px;">
							<s:property value="elUser.role. name" />
						</td>
					</tr>
					<tr>
						<td align="center" bgcolor="#F8FCFE" >
							<strong>序号</strong>
						</td>
						<td align="left" bgcolor="#F8FCFE" style="padding-left:8px;">
							<label>

								<s:textfield name="elUser.xuhao" id="xuhao" maxlength="10"/>
							</label>
						</td>
					</tr>
					<tr>
						<td align="center" bgcolor="#F8FCFE" >
							<strong>姓 名</strong>
						</td>
						<td align="left" bgcolor="#F8FCFE" style="padding-left:8px;">
							<label>
								<s:textfield name="elUser.realname" id="realname" maxlength="10"/>
							</label>
						</td>
					</tr>
					<tr>
						<td align="center" bgcolor="#F8FCFE" >
							<strong>性别</strong>
						</td>
						<td align="left" bgcolor="#F8FCFE" style="padding-left:8px;">
							<label>
								<input type="radio" name="elUser.sex" value="男" checked="checked" />
								男
								<input type="radio" name="elUser.sex" <s:if test="elUser.sex==\"女\"">checked="checked"</s:if> value="女" />
								女
							</label>
						</td>
					</tr>
					<tr style="display:none">
						<td align="center" bgcolor="#F8FCFE" >
							<strong> <wysLib:BasetName btid="5" /></strong>
						</td>
						<td align="left" bgcolor="#F8FCFE" style="padding-left:8px;">
							<label>
								<s:select name="elUser.dishi" cssClass="g-select" list="dishis"
										listKey="id" listValue="basevalue" />
							</label>
						</td>
					</tr>
					<tr style="display:none">
						<td align="center" bgcolor="#F8FCFE" >
							<strong> <wysLib:BasetName btid="6" /></strong>
						</td>
						<td align="left" bgcolor="#F8FCFE" style="padding-left:8px;">
							<label>
							<s:if test="elUser.luntanjibie_!=0">
								<input type="text" name="elUser.luntanjibie" value="<s:property value="elUser.luntanjibie"/>"/>
								<s:property value="elUser.luntanjibie_" />
							</s:if>
						</label>
						</td>
					</tr>
					<!--<tr>
						<td width="120" height="30" align="center" >
							<strong> 单位 </strong>
						</td>
						<td height="30" align="left" style="padding-left:8px;">
							<label>
								<s:textfield name="elUser.danwei" id="danwei" />
							</label>
						</td>
					</tr>
					-->
					<tr style="display:none">
						<td align="center" bgcolor="#F8FCFE" >
							<strong> 身份证号 </strong>
						</td>
						<td align="left" bgcolor="#F8FCFE" style="padding-left:8px;">
							<label>
								<s:textfield name="elUser.shenfenzheng" />
							</label>
						</td>
					</tr>
					<tr>
						<td align="center" bgcolor="#F8FCFE" >
							<strong>出生日期</strong>
						</td>
						<td align="left" bgcolor="#F8FCFE" style="padding-left:8px;">
							<label>
								<input type="text" name="elUser.shengri" value="<s:date name="elUser.shengri" format="yyyy-MM-dd"/>"
									readonly="readonly" 
									<s:if test="#request.isAll=='no'">onclick=setday(this)</s:if>
										 id="shengri" />
									<s:if test="#request.isAll=='no'">
										<span style="color:red;">*</span>
									</s:if>
							</label>
						</td>
					</tr>
					<tr style="display:none">
						<td align="center" bgcolor="#F8FCFE" >
							<strong> 联系电话 </strong>
						</td>
						<td align="left" bgcolor="#F8FCFE" style="padding-left:8px;">
							<label>
								<s:textfield name="elUser.movephone" id="movephone" />
							</label>
						</td>
					</tr>
					<tr>
						<td align="center" bgcolor="#F8FCFE" >
							<strong> 电子邮箱 </strong>
						</td>
						<td align="left" bgcolor="#F8FCFE" style="padding-left:8px;">
							<label>
								<s:textfield name="elUser.email" id="email" />
							</label>
						</td>
					</tr>
					<tr style="display:none">
						<td align="center" bgcolor="#F8FCFE" >
							<strong> <wysLib:BasetName btid="3" /> </strong>
						</td>
						<td align="left" bgcolor="#F8FCFE" style="padding-left:8px;">
							<label>
								<s:select name="elUser.zhiji" cssClass="g-select" list="zhijis"
										listKey="id" listValue="basevalue" />
							</label>
						</td>
					</tr>
					<tr style="display:none">
						<td align="center" bgcolor="#F8FCFE" >
							<strong> <wysLib:BasetName btid="2" /> </strong>
						</td>
						<td align="left" bgcolor="#F8FCFE" style="padding-left:8px;">
							<label>
								<s:select name="elUser.zhiwu" cssClass="g-select" list="zhiwus"
										listKey="id" listValue="basevalue" />
							</label>
						</td>
					</tr>
					<tr style="display:none">
						<td align="center" bgcolor="#F8FCFE" >
							<strong> <wysLib:BasetName btid="1" /> </strong>
						</td>
						<td align="left" bgcolor="#F8FCFE" style="padding-left:8px;">
							<label>
								<s:select name="elUser.jingzhong" cssClass="g-select"
										list="jingzhongs" listKey="id" listValue="basevalue" />
							</label>
						</td>
					</tr>
					
					<tr>
						<td align="right" bgcolor="#F8FCFE">
							<strong> 民族：</strong>
						</td>
						<td align="left" bgcolor="#F8FCFE">
							<label>
								<s:textfield theme="simple" name="elUser.minzu" id="minzu" />
							</label>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#F8FCFE">
							<strong> 籍贯：</strong>
						</td>
						<td align="left" bgcolor="#F8FCFE">
							<label>
								<s:textfield theme="simple" name="elUser.jiguan" id="jiguan" />
							</label>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#F8FCFE">
							<strong> 毕业院校：</strong>
						</td>
						<td align="left" bgcolor="#F8FCFE">
							<label>
								<s:textfield theme="simple" name="elUser.school" id="school" />
							</label>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#F8FCFE">
							<strong> 学历：</strong>
						</td>
						<td align="left" bgcolor="#F8FCFE">
							<label>
								<s:textfield theme="simple" name="elUser.xueli" id="xueli" />
							</label>
					<!-- 	<span style="color:red">0：大专、1：本科、2：研究生、3：博士生、4：高中、5：中学、其他数字未知</span> -->	
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#F8FCFE">
							<strong> 学位：</strong>
						</td>
						<td align="left" bgcolor="#F8FCFE">
							<label>
								<s:textfield theme="simple" name="elUser.xuewei" id="xuewei" />
							</label>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#F8FCFE">
							<strong> 专业：</strong>
						</td>
						<td align="left" bgcolor="#F8FCFE">
							<label>
								<s:textfield theme="simple" name="elUser.specialty" id="specialty" />
							</label>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#F8FCFE">
							<strong> 参加工作时间：</strong>
						</td>
						<td align="left" bgcolor="#F8FCFE">
							<label>
								<input type="text" name="elUser.canjiagongzuoshijian" id="canjiagongzuoshijian" value="<s:date format="yyyy-MM-dd" name="elUser.canjiagongzuoshijian"/>" readonly="readonly" onClick="setday(this)" />
							</label>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#F8FCFE">
							<strong> 入司时间：</strong>
						</td>
						<td align="left" bgcolor="#F8FCFE">
							<label>
								<input type="text" name="elUser.rusishijian" id="rusishijian" value="<s:date format="yyyy-MM-dd" name="elUser.rusishijian"/>" readonly="readonly" onClick="setday(this)" />
							</label>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#F8FCFE">
							<strong> 现任职时间：</strong>
						</td>
						<td align="left" bgcolor="#F8FCFE">
							<label>
								<input type="text" name="elUser.xianrenzhishijian" id="xianrenzhishijian" value="<s:date format="yyyy-MM-dd" name="elUser.xianrenzhishijian"/>" readonly="readonly" onClick="setday(this)" />
							</label>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#F8FCFE">
							<strong> 政治面貌：</strong>
						</td>
						<td align="left" bgcolor="#F8FCFE">
							<label>
								<s:textfield theme="simple" name="elUser.zhengzhimianmao" id="zhengzhimianmao" />
							</label>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#F8FCFE">
							<strong> 拼音简写：</strong>
						</td>
						<td align="left" bgcolor="#F8FCFE">
							<label>
								<s:textfield theme="simple" name="elUser.pinyinjianxie" id="pinyinjianxie" />
							</label>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#F8FCFE">
							<strong> 出生地：</strong>
						</td>
						<td align="left" bgcolor="#F8FCFE">
							<label>
								<s:textfield theme="simple" name="elUser.chushengdi" id="chushengdi" />
							</label>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#F8FCFE">
							<strong> 现员工组：</strong>
						</td>
						<td align="left" bgcolor="#F8FCFE">
							<label>
								<s:textfield theme="simple" name="elUser.xianyuangongzu" id="xianyuangongzu" />
							</label>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#F8FCFE">
							<strong> 现职位：</strong>
						</td>
						<td align="left" bgcolor="#F8FCFE">
							<label>
								<s:textfield theme="simple" name="elUser.xianzhiwei" id="xianzhiwei" />
							</label>
						</td>
					</tr>
					
					<tr >
					<td align="center" bgcolor="#F8FCFE" >
						<strong> 用户头像 </strong>
					</td>
					<td bgcolor="#F8FCFE">
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
					</tr>
					<tr >
					<td align="center" bgcolor="#F8FCFE" >
						<strong> 修改头像 </strong>
					</td>
					<td bgcolor="#F8FCFE">
					<s:textfield name="elUser.touxiang" id="mainimg" size="60" theme="simple" />
								<a style="color: black;font-weight: bolder;" href="javascript:setUrl('mainimg');">浏览我的资源库</a>
					</td>
					</tr>
					<%-- 
					<tr>
						<td width="120" height="30" align="center" >
							<strong> <wysLib:BasetName btid="4" /> </strong>
						</td>
						<td height="30" align="left" style="padding-left:8px;">
							<label>
								<s:select name="elUser.gangwei" cssClass="g-select"
										list="gangweis" listKey="id" listValue="basevalue" />
							</label>
						</td>
					</tr>
					 --%>
			  </table>

			</div>
			<br/>
			<div style="height:30px; line-height:30; text-align:center;">
			<s:submit value="提交修改" cssStyle="width:100px;" cssClass="textbg4"></s:submit>
			</div>
			<br/>
			<br/>
		</s:form>
		<!-- 内容 -->
	</BODY>
</HTML>
