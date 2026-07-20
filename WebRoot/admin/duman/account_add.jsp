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
		<style type="text/css">
.error {
	color: red;
}

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
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/jquery.validate.js"></script>
		<script type="text/javascript" src="js/userCheck.js"></script>
		<SCRIPT type="text/javascript">
			function myload(){
				if("${elmessage}"!=""){
				alert( "${elmessage}!");
				}
			}
			/*
			function _onsubmit(){
				if(document.getElementById("username").value==""){
					alert("用户名不要为空");
					document.getElementById("username").focus();
					return false;
				}					
				if(document.getElementById("realname").value==""){
					alert("姓名不要为空");
					document.getElementById("realname").focus();
					return false;
				}
				if(document.getElementById("password").value==""){
					alert("密码不要为空");
					document.getElementById("password").focus();
					return false;
				}
				return true;
			}
			*/
		</SCRIPT>
		<script type="text/javascript">
			$.validator.addMethod("idcardno", function(value, element) {
				isIdCardNo(value);
		    	return this.optional(element) || isIdCardNo(value); 
			}, "请正确输入身份证号码"); 
			$.validator.addMethod("toDateValue", function(value, element,param) {
				var date8 = "";
				if(value.length==18){
					date8 = value.substring(6, 14);
				}else{
					date8 ="19"+ value.substring(6, 12);
				}
				var date7 = date8.substring(0,4);
				date8=date8.substring(0,4)+"-"+date8.substring(4,6)+"-"+date8.substring(6,8);
				$(param).val(date8); 
				
		    	return true; 
			}, "请正确输入身份证号码"); 
			$.validator.addMethod("toMyage", function(value, element,param) {
				var date7 = value.substring(0,4);
				var myDate = new Date();
				var myAge = myDate.getYear()- date7;
				$(param).val(myAge);
		    	return true; 
			}, "请正确输入身份证号码");	
	/*		$.validator.addMethod("myPassword", function(value, element) {
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
			}, "电子邮箱格式错误");*/
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
			               //     "elUser.username":{ required: true,myName:true},
			                    "elUser.realname":{required: true},
			                    "elUser.shenfenzheng":{required: true,idcardno:false,toDateValue:"#shengri"},
			                    "elUser.shengri":{required: true,toDateValue:false,toMyage:"#age"},
			                //	"elUser.shenfenzheng":{required: true,idcardno:true,toMyage:"#age"},
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
			                //    "elUser.username":{ required: true,myName2:true},
			                //    "elUser.realname":{required: true},
			                    "elUser.shenfenzheng":{required: true,idcardno:false,toDateValue:"#shengri"},
			                    "elUser.shengri":{required: true,toDateValue:true,toMyage:"#age"},
			                    //"elUser.shenfenzheng":{required: true,idcardno:true,toDateValue:"#shengri"},
			                    //"elUser.movephone":{ required: false,myMovephone:true},
			                    //"elUser.shengri":{required: true},
			                    "elUser.department.name":{required: true}
			                },
			                messages: {
			                    "elUser.password":{required:"请输入密码"},
			                    "elUser.confirmPassword":{required:"请输入确认密码",equalTo:"两次输入密码不一致"},
			                 //   "elUser.username": {
			                 //           required: "请输入用户名" 
			                 //       },
			                 //   "elUser.realname":{required: "请输入姓名"},
			                    //"elUser.shenfenzheng":{required: "请输入身份证号"},
			                    //"elUser.shengri":{required: "请填写出生日期"},
			                    "elUser.department.name":{required:"请选择部门"}
			                }
			            }); 
					 })
					<%
				}
			%>
	</script>     
	</HEAD>
	<BODY onLoad="myload();">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="添加用户" />
				</div>
			</li>
			<%-- 
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="account_searchInit.action">用户管理</a>

			</li>
			<li class="sep">
			</li>
			<li>
				<span style="font-weight: bold;">添加用户</span>
			</li>
			 --%>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<form action="account_add.action" method="post" id="registerForm">
			<s:hidden name="elUser.department.id" id="danwei" />
		 	<s:hidden name="elUser.station.id" id="staid" />
			<div style="margin-top: 0px;">
				<table id="info1" width="100%" cellpadding="1" cellspacing="1">
					<caption>
						基本信息
					</caption>
					<tr>
						<td width="120" height="30" align="right">
							<strong>单位/部门：</strong>
						</td>
						<td width="912" height="30" align="left">
							<%-- 
							<label>
								<select style="width: 300px;" name="elUser.department.id">
									<wysLib:dep_select selectid="${elUser.department.id}" />
								</select>
							</label>
							 --%>
							<s:textfield theme="simple" name="elUser.department.name"
								size="20" id="danweiName" readonly="true" />
							<a href="#" class="textbg4" style="width: 90px;"
								onClick="searchUserInit();return false;">点此进行选择</a>
						</td>
					</tr>
			 		
					<tr>
						<td width="120" height="30" align="right">
							<strong>岗位：</strong>
						</td>
						<td height="30" align="left">
							<s:textfield theme="simple" name="elUser.station.name"
								size="20" id="gangweiName" readonly="true" />
							<a href="#" class="textbg4" style="width: 90px;"
								onClick="searchUserInit2();return false;">点此进行选择</a>
						</td>
					</tr>
					
					<tr>
						<td width="120" height="30" align="right">
							<strong>用户名：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<input type="text" name="elUser.username" id="username" />
								&nbsp;&nbsp;
								
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right">
							<strong>密 码：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<input type="password" name="elUser.password" id="password" />
								&nbsp;&nbsp;
								
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right">
							<strong>确认密码：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<input type="password" name="elUser.confirmPassword"
									id="password2" />
								&nbsp;&nbsp;
								
							</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<strong> 权限：</strong>
						</td>
						<td height="30" align="left">
							<select name="elUser.role.id">
								<s:iterator value="roles">
									<option
										<s:if test="id==elUser.role.id">selected='selected'</s:if>
										value="<s:property value="id"/>">
										<s:property value="name" />
									</option>
								</s:iterator>
							</select>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right">
							<strong>序号：</strong>
						</td>
						<td height="30" align="left">
							<label>

								<input type="text" name="elUser.xuhao" id="xuhao" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right">
							<strong>姓 名：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<input type="text" name="elUser.realname" id="realname" />
								&nbsp;&nbsp;
								
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right">
							<strong>性别：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<input type="radio" name="elUser.sex" value="男"
									checked="checked" />
								男
								<input type="radio" name="elUser.sex" value="女" />
								女
							</label>
						</td>
					</tr>
					<tr style="display:none">
						<td width="120" height="30" align="right">
							<strong> <wysLib:BasetName btid="5" />：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:select theme="simple" name="elUser.dishi" cssClass="g-select"
									list="dishis" listKey="id" listValue="basevalue" />
							</label>
						</td>
					</tr>
					<tr style="display:none">
						<td width="120" height="30" align="right">
							<strong> <wysLib:BasetName btid="6" />：</strong>
						</td>
						<s:if test="#session.roleid==1">
						<td height="30" align="left">
							<label>
								<s:select theme="simple" name="elUser.luntanjibie" cssClass="g-select"
									list="luntanjibies" listKey="id" listValue="basevalue" />
							</label>
						</td>
						</s:if>
						<s:else>
						<td width="38" height="30" align="left">
							<select name="elUser.luntanjibie">
								<s:iterator value="luntanjibies" status="status">
									<s:if test="#status.index+1<2">
										<option value="<s:property value="id"/>">
												<s:property value="basevalue" />
										</option>
									</s:if>
								</s:iterator>
							</select>
						</td>
						</s:else>
					</tr>
					<!--
					<tr>
						<td width="120" height="30" align="center" >
							<strong> 单位 </strong>
						</td>
						<td height="30" align="left" >
							<label>
								<input type="text" name="elUser.danwei" id="danwei" />
							</label>
						</td>
					</tr>
					-->
					<tr>
						<td width="120" height="30" align="right">
							<strong> 身份证号：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<input type="text" name="elUser.shenfenzheng" />
								&nbsp;&nbsp;
								<s:if test="#request.isAll=='yes'">
									
								</s:if>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right">
							<strong>出生日期：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<%-- 
								<input type="text" name="elUser.shengri" 
									readonly="readonly" onclick="setday(this)" id="shengri" />
							 --%>
								<input type="text" name="elUser.shengri" readonly="readonly"
									<s:if test="#request.isAll=='no'">onclick=setday(this)</s:if>
									id="shengri" />
								<s:if test="#request.isAll=='no'">
									
								</s:if>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right">
							<strong>年龄：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<input type="text" name="elUser.age" readonly="readonly"
									
									id="age" />
								<s:if test="#request.isAll=='no'">
									
								</s:if>
							</label>
						</td>
					</tr>
					<tr style="display:none">
						<td width="120" height="30" align="right">
							<strong> 联系电话：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:textfield theme="simple" name="elUser.movephone"
									id="movephone" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right">
							<strong> 电子邮箱：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:textfield theme="simple" name="elUser.email" id="email" />
							</label>
						</td>
					</tr>
					<tr style="display:none">
						<td width="120" height="30" align="right">
							<strong> <wysLib:BasetName btid="3" />：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:select theme="simple" name="elUser.zhiji" cssClass="g-select"
									list="zhijis" listKey="id" listValue="basevalue" />
							</label>
						</td>
					</tr>
					<tr style="display:none">
						<td width="120" height="30" align="right">
							<strong> <wysLib:BasetName btid="2" />：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:select theme="simple" name="elUser.zhiwu" cssClass="g-select"
									list="zhiwus" listKey="id" listValue="basevalue" />
							</label>
						</td>
					</tr>
					<tr style="display:none">
						<td width="120" height="30" align="right">
							<strong> <wysLib:BasetName btid="1" />：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:select theme="simple" name="elUser.jingzhong"
									cssClass="g-select" list="jingzhongs" listKey="id"
									listValue="basevalue" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right">
							<strong> 民族：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:textfield theme="simple" name="elUser.minzu" id="minzu" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right">
							<strong> 籍贯：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:textfield theme="simple" name="elUser.jiguan" id="jiguan" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right">
							<strong> 毕业院校：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:textfield theme="simple" name="elUser.school" id="school" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right">
							<strong> 学历：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:textfield theme="simple" name="elUser.xueli" id="xueli" />
							</label>
						<!-- 	<span style="color:red">0：大专、1：本科、2：研究生、3：博士生、4：高中、5：中学、其他数字未知</span> -->
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right">
							<strong> 学位：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:textfield theme="simple" name="elUser.xuewei" id="xuewei" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right">
							<strong> 专业：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:textfield theme="simple" name="elUser.specialty" id="specialty" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right">
							<strong> 参加工作时间：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<input type="text" readonly="readonly" name="elUser.canjiagongzuoshijian" id="canjiagongzuoshijian" onClick="setday(this)" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right">
							<strong> 入司时间：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<input type="text" readonly="readonly" name="elUser.rusishijian" id="rusishijian" onClick="setday(this)" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right">
							<strong> 现任职时间：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<input type="text" readonly="readonly" name="elUser.xianrenzhishijian" id="xianrenzhishijian" onClick="setday(this)" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right">
							<strong> 政治面貌：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:textfield theme="simple" name="elUser.zhengzhimianmao" id="zhengzhimianmao" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right">
							<strong> 拼音简写：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:textfield theme="simple" name="elUser.pinyinjianxie" id="pinyinjianxie" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right">
							<strong> 出生地：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:textfield theme="simple" name="elUser.chushengdi" id="chushengdi" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="right">
							<strong> 现员工组：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:textfield theme="simple" name="elUser.xianyuangongzu" id="xianyuangongzu" />
							</label>
						</td>
					</tr>
			<!-- <tr>
						<td width="120" height="30" align="right">
							<strong> 现职位：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<s:textfield theme="simple" name="elUser.xianzhiwei" id="xianzhiwei" />
							</label>
						</td>
					</tr> -->		
					<tr>
						<td align="right">
							<strong>开通状态：</strong>
						</td>
						<td height="30" align="left">
							<label>
								<input type="radio" name="elUser.valid" value="true"
									checked="checked" />
								开通
								<input type="radio" name="elUser.valid" value="false" />
								关闭
							</label>
						</td>
					</tr>
				</table>

			</div>
			<input type="submit" class="textbg4" style="width: 80px;"
				value="确认添加" />
			<a href="account_search.action" style="width: 100px" class="textbg4">返回用户列表</a>
		</form>
		<!-- 内容 -->
	</BODY>
</HTML>
