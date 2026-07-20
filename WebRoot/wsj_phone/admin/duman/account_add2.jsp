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
		<TITLE>用户信息修改</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/css2/style.css" />
		<link rel="stylesheet" type="text/css" href="css/css2/base.css" />
		<script type="text/javascript" src="eldatePicker/calendar.js"></script>
		<script type="text/javascript" src="eldatePicker/message.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/jquery.validate.js"></script>
				<script type="text/javascript"> 	
			/*** 判断是否为“YYYYMM”式的时期 ***/
			function isDate6(sDate) {
			    if (!/^[0-9]{6}$/.test(sDate)) {
			        return false;
			    }
			    var year, month, day;
			    year = sDate.substring(0, 4);
			    month = sDate.substring(4, 6);
			    if (year < 1700 || year > 2500) return false;
			    if (month < 1 || month > 12) return false;
			    return true;
			}
			
			function isDate6_2(sDate) {
			    if (!/^[0-9]{6}$/.test(sDate)) {
			        return false;
			    }
			    var year, month, day;
			    year = "19"+sDate.substring(0, 2);
			    month = sDate.substring(2, 4);
			    day = sDate.substring(4,6);
			    if (year < 1700 || year > 2500) return false;
			    if (month < 1 || month > 12) return false;
			    if (day < 1 || day > 31) return false;
			    return true;
			}
			
			/*** 判断是否为“YYYYMMDD”式的时期 ***/
			function isDate8(sDate) {
			    if (!/^[0-9]{8}$/.test(sDate)) {
			        return false;
			    }
			    var year, month, day;
			    year = sDate.substring(0, 4);
			    month = sDate.substring(4, 6);
			    day = sDate.substring(6, 8);
			
			    var iaMonthDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
			    if (year < 1700 || year > 2500) return false;
			    if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) iaMonthDays[1] = 29;
			    if (month < 1 || month > 12) return false;
			    if (day < 1 || day > iaMonthDays[month - 1]) return false;
			    return true;
			}  
			/*** 身份证号码验证 ***/
			function isIdCardNo(num) {
			    var factorArr = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1);
			    var parityBit = new Array("1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2");
			    var varArray = new Array();
			    var intVal;
			    var lngProd = 0;
			    var intCheckDigit;
			    var intStrLen = num.length;
			    var idNumber = num;
			    // initialize
			    if ((intStrLen != 15) && (intStrLen != 18)) {
			        return false;
			    }
			    // check and set val
			    for (i = 0; i < intStrLen; i++) {
			        varArray[i] = idNumber.charAt(i);
			        if ((varArray[i] < "0" || varArray[i] > "9") && (i != 17)) {
			            return false;
			        }
			        else if (i < 17) {
			            varArray[i] = varArray[i] * factorArr[i];
			        }
			    }
			
			    if (intStrLen == 18) {
			        //check date
			        var date8 = idNumber.substring(6, 14);
			        if (isDate8(date8) == false) {
			            return false;
			        }
			        // calculate the sum of the prod ts
			        for (i = 0; i < 17; i++) {
			            lngProd = lngProd + varArray[i];
			        }
			        // calculate the check digit
			        intCheckDigit = parityBit[lngProd % 11];
			        // check last digit
			        if (varArray[17] != intCheckDigit) {
			            return false;
			        }
			    }
			    else {        //length is 15
			        //check date
			        var date6 = idNumber.substring(6, 12);
			        if (isDate6_2(date6) == false) {
			            return false;
			        }
			    }
			    return true;
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
			$.validator.addMethod("myCHName", function(value, element) {
			    return this.optional(element) || /^[^u4E00-u9FA5]{2,5}$/.test(value);
			}, "输入错误，应输入2-5个中文");
			$.validator.addMethod("myPhone", function(value, element) {
			    return this.optional(element) || /^[\d]{11,11}$/.test(value);
			}, "输入错误，只能输入数字且只能11位");
			 $(function(){ 
			 	 $("#registerForm").validate({ 
	                rules: {
	                    "elUser.password":{required: true,myPassword:true}, 
	                    "elUser.username":{ required: true,myName:true},
	                    "elUser.realname":{required: true},
	                    "elUser.shenfenzheng":{required: true,idcardno:true,toDateValue:"#shengri"},
	                    "elUser.phone":{ required: false,myPhone:true}
	                },
	                messages: {
	                    "elUser.password":{required:"请输入密码"}, 
	                    "elUser.username": {required: "请输入用户名" },
	                    "elUser.realname":{required: "请输入姓名"},
	                    "elUser.shenfenzheng":{required: "请输入身份证号"} 
	                }
	            }); 
			 })
			 function searchUserInit(){
			     width=600;
				 height=500;
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv =  window.showModalDialog("userRegister.action?x="+Math.random(),null,sFeature);
				 //alert(rv);
				 if(rv!=undefined&&rv!=""){
					 var bh=rv.split("_");
					 document.getElementById("danwei").value=bh[2];
					 document.getElementById("danweiName").value=bh[1];
				 }
			}
			 function QRZ(){   
			 	document.getElementById("qrz_xl").value="无学历";
			 	document.getElementById("qrz_xw").value="无学位";
			 	document.getElementById("qrz_bysj").value="1900-00-00";
			 	document.getElementById("qrz_xx").value="无"; 
			}
			 function FQRZ(){   
			 	document.getElementById("fqrz_xl").value="无学历";
			 	document.getElementById("fqrz_xw").value="无学位";
			 	document.getElementById("fqrz_bysj").value="1900-00-00";
			 	document.getElementById("fqrz_xx").value="无"; 
			}
			 function ZYJS(){    
			 	document.getElementById("zyjs_lx").value="无"; 
			 	document.getElementById("zyjs_1").value="无";
			 	document.getElementById("zyjs_fs").value="无"; 
			 	document.getElementById("zyjs_3").value="1900-00-00";
			 	document.getElementById("zyjs_6").value="无"; 
			}
	</script>
	</HEAD>
	<BODY onload="setGd();">
		<s:form action="account_add2.action" method="post" theme="simple" id="registerForm">
			<div id="OutlineDiv">
				<table id="info1" width="100%" height="100%" border="0"
					cellspacing="0" cellpadding="2">
					<tr>
						<td>
							<!------------基本信息----------------->
							<TABLE width="100%" border="0" align="center" cellPadding="0"
								cellSpacing="0" class="pg_add"> 
								<TR>
									<TD class="pg_add_title" colspan="7">
										基本信息
									</TD>
								</TR>
								<tr>
									<td align="right" class="pg_add_head">
										姓名:
									</td>
									<td width="15%" class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<input type="text" name="elUser.realname"
											value="<s:property value="elUser.realname" />" />
									</td>
									<td width="15%" align="right" class="pg_add_head">
										有效身份证件类型:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<select name="elUser.zhengjianleixing">  
												<option value="身份证">身份证</option> 
										</select> 
									</td>
									<td width="19%" align="right" class="pg_add_head">
										有效身份证件号码:
									</td>
									<td width="19%" colspan="2" class="pg_add_content">
										<input type="text" name="elUser.shenfenzheng"
											value="<s:property value="elUser.shenfenzheng"/>" />
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										出生日期:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;"> 
										<label> 
										 <input type="text" name="elUser.shengri" 
												readonly="readonly" id="shengri" />
										</label>
									</td>
									<td align="right" class="pg_add_head">
										会计证号:
									</td> 
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<input type="text" name="elUser.kuaijihao"
											value="<s:property value="elUser.kuaijihao"/>" />
									</td>
									<td align="right" class="pg_add_head">
										会计证发证日期:
									</td>
									<td colspan="2" class="pg_add_content">
										<input type="text" name="elUser.kuaijizhengfazhengriqi"
											value="<s:property value="elUser.kuaijizhengfazhengriqi"/>"
											onclick="setday(this)" />
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										会计证发证机关:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<input type="text" name="elUser.kuaijizhengfazhengjiguan"
											value="<s:property value="elUser.kuaijizhengfazhengjiguan"/>" />
									</td>
									<td align="right" class="pg_add_head">
										有效期:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<input type="text"
											value="<s:property value="elUser.kuaijizhengyouxiaoqi"/>"
											name="elUser.kuaijizhengyouxiaoqi" size="13"
											onclick="setday(this)" />
									</td>
									<td align="right" class="pg_add_head">
										性别:
									</td>
									<td colspan="2" class="pg_add_content">
										<select name="elUser.sex">
											<option
												<s:if test="elUser.sex=='男'">selected='selected'</s:if>
												value="男">
												男
											</option>
											<option
												<s:if test="elUser.sex='女'">selected='selected'</s:if>
												value="女">
												女
											</option>
										</select>
									</td>
								</tr>
								<!---------------辅助信息---------------------->
								<TR>
									<TD class="pg_add_title" colspan="7">
										辅助信息
									</TD>
								</TR>
								<TR>
									<TD class="pg_add_title" align="center">
										名称
									</TD>
									<TD class="pg_add_title" align="center">
										原信息
									</TD>
									<TD class="pg_add_title" align="center">
										新信息
									</TD>
									<TD class="pg_add_title" align="center">
										名称
									</TD>
									<TD class="pg_add_title" align="center">
										原信息
									</TD>
									<TD colspan="2" align="center" class="pg_add_title">
										新信息
									</TD>
								</TR>
								<tr>
									<td align="right" class="pg_add_head">
										培训类别:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.peixunleibie" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<select name="elUser.peixunleibie"> 
											<s:iterator value="elUser.peixunleibies" status="pxlb">
												<option
													<s:if test="elUser.peixunleibie==elUser.peixunleibies[#pxlb.index]">selected = 'selected'</s:if>
													value="<s:property />">
													<s:property />
												</option>
											</s:iterator>
										</select> 
									</td>
									<td align="right" class="pg_add_head">
										民族:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.minzu" />
									</td>
									<td colspan="2" class="pg_add_content">
										<select name="elUser.minzu">
											<option
												<s:if test="elUser.minzu=='汉族'">selected='selected'</s:if>
												value="汉族">
												汉族
											</option>
											<option
												<s:if test="elUser.minzu=='蒙古族'">selected='selected'</s:if>
												value="蒙古族">
												蒙古族
											</option>
											<option
												<s:if test="elUser.minzu=='回族'">selected='selected'</s:if>
												value="回族">
												回族
											</option>
											<option
												<s:if test="elUser.minzu=='藏族'">selected='selected'</s:if>
												value="藏族">
												藏族
											</option>
											<option
												<s:if test="elUser.minzu=='维吾尔族'">selected='selected'</s:if>
												value="维吾尔族">
												维吾尔族
											</option>
											<option
												<s:if test="elUser.minzu=='苗族'">selected='selected'</s:if>
												value="苗族">
												苗族
											</option>
											<option
												<s:if test="elUser.minzu=='彝族'">selected='selected'</s:if>
												value="彝族">
												彝族
											</option>
											<option
												<s:if test="elUser.minzu=='壮族'">selected='selected'</s:if>
												value="壮族">
												壮族
											</option>
											<option
												<s:if test="elUser.minzu=='布依族'">selected='selected'</s:if>
												value="布依族">
												布依族
											</option>
											<option
												<s:if test="elUser.minzu=='朝鲜族'">selected='selected'</s:if>
												value="朝鲜族">
												朝鲜族
											</option>
											<option
												<s:if test="elUser.minzu=='满族'">selected='selected'</s:if>
												value="满族">
												满族
											</option>
											<option
												<s:if test="elUser.minzu=='侗族'">selected='selected'</s:if>
												value="侗族">
												侗族
											</option>
											<option
												<s:if test="elUser.minzu=='瑶族'">selected='selected'</s:if>
												value="瑶族">
												瑶族
											</option>
											<option
												<s:if test="elUser.minzu=='白族'">selected='selected'</s:if>
												value="白族">
												白族
											</option>
											<option
												<s:if test="elUser.minzu=='土家族'">selected='selected'</s:if>
												value="土家族">
												土家族
											</option>
											<option
												<s:if test="elUser.minzu=='哈尼族'">selected='selected'</s:if>
												value="哈尼族">
												哈尼族
											</option>
											<option
												<s:if test="elUser.minzu=='哈萨克族'">selected='selected'</s:if>
												value="哈萨克族">
												哈萨克族
											</option>
											<option
												<s:if test="elUser.minzu=='傣族'">selected='selected'</s:if>
												value="傣族">
												傣族
											</option>
											<option
												<s:if test="elUser.minzu=='黎族'">selected='selected'</s:if>
												value="黎族">
												黎族
											</option>
											<option
												<s:if test="elUser.minzu=='傈僳族'">selected='selected'</s:if>
												value="傈僳族">
												傈僳族
											</option>
											<option
												<s:if test="elUser.minzu=='佤族'">selected='selected'</s:if>
												value="佤族">
												佤族
											</option>
											<option
												<s:if test="elUser.minzu=='畲族'">selected='selected'</s:if>
												value="畲族">
												畲族
											</option>
											<option
												<s:if test="elUser.minzu=='高山族'">selected='selected'</s:if>
												value="高山族">
												高山族
											</option>
											<option
												<s:if test="elUser.minzu=='拉祜族'">selected='selected'</s:if>
												value="拉祜族">
												拉祜族
											</option>
											<option
												<s:if test="elUser.minzu=='水族'">selected='selected'</s:if>
												value="水族">
												水族
											</option>
											<option
												<s:if test="elUser.minzu=='东乡族'">selected='selected'</s:if>
												value="东乡族">
												东乡族
											</option>
											<option
												<s:if test="elUser.minzu=='纳西族'">selected='selected'</s:if>
												value="纳西族">
												纳西族
											</option>
											<option
												<s:if test="elUser.minzu=='景颇族'">selected='selected'</s:if>
												value="景颇族">
												景颇族
											</option>
											<option
												<s:if test="elUser.minzu=='柯尔克孜族'">selected='selected'</s:if>
												value="柯尔克孜族">
												柯尔克孜族
											</option>
											<option
												<s:if test="elUser.minzu=='土族'">selected='selected'</s:if>
												value="土族">
												土族
											</option>
											<option
												<s:if test="elUser.minzu=='达斡尔族'">selected='selected'</s:if>
												value="达斡尔族">
												达斡尔族
											</option>
											<option
												<s:if test="elUser.minzu=='仫佬族'">selected='selected'</s:if>
												value="仫佬族">
												仫佬族
											</option>
											<option
												<s:if test="elUser.minzu=='羌族'">selected='selected'</s:if>
												value="羌族">
												羌族
											</option>
											<option
												<s:if test="elUser.minzu=='布朗族'">selected='selected'</s:if>
												value="布朗族">
												布朗族
											</option>
											<option
												<s:if test="elUser.minzu=='撒拉族'">selected='selected'</s:if>
												value="撒拉族">
												撒拉族
											</option>
											<option
												<s:if test="elUser.minzu=='毛难族'">selected='selected'</s:if>
												value="毛难族">
												毛难族
											</option>
											<option
												<s:if test="elUser.minzu=='仡佬族'">selected='selected'</s:if>
												value="仡佬族">
												仡佬族
											</option>
											<option
												<s:if test="elUser.minzu=='锡伯族'">selected='selected'</s:if>
												value="锡伯族">
												锡伯族
											</option>
											<option
												<s:if test="elUser.minzu=='阿昌族'">selected='selected'</s:if>
												value="阿昌族">
												阿昌族
											</option>
											<option
												<s:if test="elUser.minzu=='普米族'">selected='selected'</s:if>
												value="普米族">
												普米族
											</option>
											<option
												<s:if test="elUser.minzu=='塔吉克族'">selected='selected'</s:if>
												value="塔吉克族">
												塔吉克族
											</option>
											<option
												<s:if test="elUser.minzu=='怒族'">selected='selected'</s:if>
												value="怒族">
												怒族
											</option>
											<option
												<s:if test="elUser.minzu=='乌孜别克族'">selected='selected'</s:if>
												value="乌孜别克族">
												乌孜别克族
											</option>
											<option
												<s:if test="elUser.minzu=='俄罗斯族'">selected='selected'</s:if>
												value="俄罗斯族">
												俄罗斯族
											</option>
											<option
												<s:if test="elUser.minzu=='鄂温克族'">selected='selected'</s:if>
												value="鄂温克族">
												鄂温克族
											</option>
											<option
												<s:if test="elUser.minzu=='崩龙族'">selected='selected'</s:if>
												value="崩龙族">
												崩龙族
											</option>
											<option
												<s:if test="elUser.minzu=='保安族'">selected='selected'</s:if>
												value="保安族">
												保安族
											</option>
											<option
												<s:if test="elUser.minzu=='裕固族'">selected='selected'</s:if>
												value="裕固族">
												裕固族
											</option>
											<option
												<s:if test="elUser.minzu=='京族'">selected='selected'</s:if>
												value="京族">
												京族
											</option>
											<option
												<s:if test="elUser.minzu=='塔塔尔族'">selected='selected'</s:if>
												value="塔塔尔族">
												塔塔尔族
											</option>
											<option
												<s:if test="elUser.minzu=='独龙族'">selected='selected'</s:if>
												value="独龙族">
												独龙族
											</option>
											<option
												<s:if test="elUser.minzu=='鄂伦春族'">selected='selected'</s:if>
												value="鄂伦春族">
												鄂伦春族
											</option>
											<option
												<s:if test="elUser.minzu=='赫哲族'">selected='selected'</s:if>
												value="赫哲族">
												赫哲族
											</option>
											<option
												<s:if test="elUser.minzu=='门巴族'">selected='selected'</s:if>
												value="门巴族">
												门巴族
											</option>
											<option
												<s:if test="elUser.minzu=='珞巴族'">selected='selected'</s:if>
												value="珞巴族">
												珞巴族
											</option>
											<option
												<s:if test="elUser.minzu=='基诺族'">selected='selected'</s:if>
												value="基诺族">
												基诺族
											</option>
											<option
												<s:if test="elUser.minzu=='无'">selected='selected'</s:if>
												value="无">
												无
											</option>
										</select>
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										开始从事会计工作时间:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.kaishikuaijishijian" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<input type="text" name="elUser.kaishikuaijishijian" size="13"
											value="<s:property value="elUser.kaishikuaijishijian" />"
											onclick="setday(this)" />
									</td>
									<td align="right" class="pg_add_head">
										政治面貌:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.zhengzhi" />
									</td>
									<td colspan="2" class="pg_add_content">
										<select name="elUser.zhengzhi" id="defaultValue">
											<option
												<s:if test="elUser.zhengzhi=='中国共产党员（含预备党员）'">selected='selected'</s:if>
												value="中国共产党员（含预备党员）">
												中国共产党员（含预备党员）
											</option>
											<option
												<s:if test="elUser.zhengzhi=='中国共青团员'">selected='selected'</s:if>
												value="中国共青团员">
												中国共青团员
											</option>
											<option
												<s:if test="elUser.zhengzhi=='民主党派'">selected='selected'</s:if>
												value="民主党派">
												民主党派
											</option>
											<option
												<s:if test="elUser.zhengzhi=='群众'">selected='selected'</s:if>
												value="群众">
												群众
											</option>
										</select>
									</td>
								</tr>

								<tr>
									<td align="right" class="pg_add_head">
										全日制最高学历:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.xueli" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<select name="elUser.xueli" id="qrz_xl">
											<option
												<s:if test="elUser.xueli=='初中及以下'">selected='selected'</s:if>
												value="初中及以下">
												初中及以下
											</option>
											<option
												<s:if test="elUser.xueli=='高中'">selected='selected'</s:if>
												value="高中">
												高中
											</option>
											<option
												<s:if test="elUser.xueli=='中专'">selected='selected'</s:if>
												value="中专">
												中专
											</option>
											<option
												<s:if test="elUser.xueli=='大专'">selected='selected'</s:if>
												value="大专">
												大专
											</option>
											<option
												<s:if test="elUser.xueli=='本科'">selected='selected'</s:if>
												value="本科">
												本科
											</option>
											<option
												<s:if test="elUser.xueli=='硕士研究生'">selected='selected'</s:if>
												value="硕士研究生">
												硕士研究生
											</option>
											<option
												<s:if test="elUser.xueli=='博士研究生'">selected='selected'</s:if>
												value="博士研究生">
												博士研究生
											</option>
											<option
												<s:if test="elUser.xueli=='无学历'">selected='selected'</s:if>
												value="无学历">
												无学历
											</option>
										</select>
										<input type="button" value="无相关信息" onclick="QRZ()">
									</td>
									<td align="right" class="pg_add_head">
										会计专业技术职务:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.kuaijizhuanyejishuzhiwu" />
									</td>
									<td colspan="2" class="pg_add_content">
										<select name="elUser.kuaijizhuanyejishuzhiwu">
											<option
												<s:if test="elUser.kuaijizhuanyejishuzhiwu=='会计员'">selected='selected'</s:if>
												value="会计员">
												会计员
											</option>
											<option
												<s:if test="elUser.kuaijizhuanyejishuzhiwu=='助理会计师'">selected='selected'</s:if>
												value="助理会计师">
												助理会计师
											</option>
											<option
												<s:if test="elUser.kuaijizhuanyejishuzhiwu=='会计师'">selected='selected'</s:if>
												value="会计师">
												会计师
											</option>
											<option
												<s:if test="elUser.kuaijizhuanyejishuzhiwu=='高级会计师'">selected='selected'</s:if>
												value="高级会计师">
												高级会计师
											</option>
											<option
												<s:if test="elUser.kuaijizhuanyejishuzhiwu=='正高级会计师'">selected='selected'</s:if>
												value="正高级会计师">
												正高级会计师
											</option>
											<option
												<s:if test="elUser.kuaijizhuanyejishuzhiwu=='其他'">selected='selected'</s:if>
												value="其他">
												其他
											</option>
											<option
												<s:if test="elUser.kuaijizhuanyejishuzhiwu=='无职务'">selected='selected'</s:if>
												value="无职务">
												无职务
											</option>
										</select>
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										全日制最高学位:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.xuewei" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<select name="elUser.xuewei" id="qrz_xw">
											<option
												<s:if test="elUser.xuewei=='学士'">selected='selected'</s:if>
												value="学士">
												学士
											</option>
											<option
												<s:if test="elUser.xuewei=='双学士'">selected='selected'</s:if>
												value="双学士">
												双学士
											</option>
											<option
												<s:if test="elUser.xuewei=='硕士'">selected='selected'</s:if>
												value="硕士">
												硕士
											</option>
											<option
												<s:if test="elUser.xuewei=='博士'">selected='selected'</s:if>
												value="博士">
												博士
											</option>
											<option
												<s:if test="elUser.xuewei=='无学位'">selected='selected'</s:if>
												value="无学位">
												无学位
											</option>
										</select>
									</td>
									<td align="right" class="pg_add_head">
										会计专业技术职务聘任时间:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										&nbsp;
										<s:property value="elUser.kuaijizhuanyejishuzhiwuriqi" />
									</td>
									<td colspan="2" class="pg_add_content">
										<input type="text" name="elUser.kuaijizhuanyejishuzhiwuriqi"
											size="13"
											value="<s:property value="elUser.kuaijizhuanyejishuzhiwuriqi"/>"
											onclick="setday(this)" />
									</td>

								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										全日制最高学历毕业院校:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.biyeyuanxiao" />

									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<input type="text" name="elUser.biyeyuanxiao" id="qrz_xx"
											value="<s:property value="elUser.biyeyuanxiao" />" />
									</td>
									<td align="right" class="pg_add_head">
										个人电话（手机）:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.phone" />
									</td> 
									<td colspan="2" class="pg_add_content">
										<label>
											<s:textfield name="elUser.phone" id="phone" />
										</label> 
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										全日制最高学历毕业时间:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.biyeshijian" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<input type="text" name="elUser.biyeshijian" size="13" id="qrz_bysj"
											value="<s:property value="elUser.biyeshijian" />"
											onclick="setday(this)" />
									</td>
									<td align="right" class="pg_add_head">
										电子邮箱:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.email" />
									</td>
									<td colspan="2" class="pg_add_content">
										<input type="text" name="elUser.email"
											value="<s:property value="elUser.email" />" />
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										全日制最高学历所学专业:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.suoxuezhuanye" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<select name="elUser.suoxuezhuanye" id="qrz_byzy"> 
											<s:iterator value="elUser.suoxuezhuanyes" status="pxlb">
												<option
													<s:if test="elUser.suoxuezhuanye==elUser.suoxuezhuanyes[#pxlb.index]">selected = 'selected'</s:if>
													value="<s:property />">
													<s:property />
												</option>
											</s:iterator>
										</select> 
									</td>
									<td align="right" class="pg_add_head">
										人员状态:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.shifouzaizhi" />
									</td>
									<td colspan="2" class="pg_add_content">
										<select name="elUser.shifouzaizhi"> 
											<s:iterator value="elUser.shifouzaizhis" status="sfzz">
												<option
													<s:if test="elUser.shifouzaizhi==elUser.shifouzaizhis[#sfzz.index]">selected = 'selected'</s:if>
													value="<s:property />">
													<s:property />
												</option>
											</s:iterator>
										</select>
									</td>
								</tr>
								<tr>

									<td align="right" class="pg_add_head">
										非全日制最高学历:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.feixueli" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<select name="elUser.feixueli" id="fqrz_xl">
											<option
												<s:if test="elUser.feixueli==\"初中及以下\"">selected='selected'</s:if>
												value="初中及以下">
												初中及以下
											</option>
											<option
												<s:if test="elUser.feixueli=='高中'">selected='selected'</s:if>
												value="高中">
												高中
											</option>
											<option
												<s:if test="elUser.feixueli=='中专'">selected='selected'</s:if>
												value="中专">
												中专
											</option>
											<option
												<s:if test="elUser.feixueli=='大专'">selected='selected'</s:if>
												value="大专">
												大专
											</option>
											<option
												<s:if test="elUser.feixueli=='本科'">selected='selected'</s:if>
												value="本科">
												本科
											</option>
											<option
												<s:if test="elUser.feixueli=='硕士研究生'">selected='selected'</s:if>
												value="硕士研究生">
												硕士研究生
											</option>
											<option
												<s:if test="elUser.feixueli=='博士研究生'">selected='selected'</s:if>
												value="博士研究生">
												博士研究生
											</option>
											<option
												<s:if test="elUser.feixueli=='无学历'">selected='selected'</s:if>
												value="无学历">
												无学历
											</option>
										</select> 
										<input type="button" value="无相关信息" onclick="FQRZ()">
									</td>
									<td align="right" class="pg_add_head">
										工作单位全称:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.danwei" />
									</td>
									<td colspan="2" class="pg_add_content">
										<input type="text" name="elUser.danwei"
											value="<s:property value="elUser.danwei" />" />
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										非全日制最高学位:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.feixuewei" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<select name="elUser.feixuewei" id="fqrz_xw">
											<option
												<s:if test="elUser.feixuewei=='学士'">selected='selected'</s:if>
												value="学士">
												学士
											</option>
											<option
												<s:if test="elUser.feixuewei=='双学士'">selected='selected'</s:if>
												value="双学士">
												双学士
											</option>
											<option
												<s:if test="elUser.feixuewei=='硕士'">selected='selected'</s:if>
												value="硕士">
												硕士
											</option>
											<option
												<s:if test="elUser.feixuewei=='博士'">selected='selected'</s:if>
												value="博士">
												博士
											</option>
											<option
												<s:if test="elUser.feixuewei=='无学位'">selected='selected'</s:if>
												value="无学位">
												无学位
											</option>
										</select>
									</td>
									<td align="right" class="pg_add_head">
										工作单位电话:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.lianxifangshi" />
									</td>
									<td colspan="2" class="pg_add_content">
										<input type="text" name="elUser.lianxifangshi"
											value="<s:property value="elUser.lianxifangshi" />"
											onblur="checkInput(this)" />
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										非全日制学历（学位）取得时间:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.feibiyeshijian" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<input type="text" name="elUser.feibiyeshijian" size="13" id="fqrz_bysj"
											value="<s:property value="elUser.feibiyeshijian" />"
											onclick="setday(this)" />
									</td>
									<td align="right" class="pg_add_head">
										工作单位地址:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.danweiaddress" />

									</td>
									<td colspan="2" class="pg_add_content">
										<input type="text" name="elUser.danweiaddress"
											value="<s:property value="elUser.danweiaddress" />" />
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										非全日制学历（学位）授予院校:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.feibiyeyuanxiao" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<input type="text" 
											value="<s:property value="elUser.feibiyeyuanxiao" />"
											id="fqrz_xx" name="elUser.feibiyeyuanxiao" />
									</td>
									<td align="right" class="pg_add_head">
										工作单位经济类型:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.danweileixing" />
									</td>
									<td colspan="2" class="pg_add_content">
										<select name="elUser.danweileixing">
											<option
												<s:if test="elUser.danweileixing=='无'">selected='selected'</s:if>
												value="无">
												无
											</option>
											<option
												<s:if test="elUser.danweileixing=='行政单位'">selected='selected'</s:if>
												value="行政单位">
												行政单位
											</option>
											<option
												<s:if test="elUser.danweileixing=='事业单位'">selected='selected'</s:if>
												value="事业单位">
												事业单位
											</option>
											<option
												<s:if test="elUser.danweileixing=='上市公司-国有控股'">selected='selected'</s:if>
												value="上市公司-国有控股">
												上市公司-国有控股
											</option>
											<option
												<s:if test="elUser.danweileixing=='上市公司-非国有控股'">selected='selected'</s:if>
												value="上市公司-非国有控股">
												上市公司-非国有控股
											</option>
											<option
												<s:if test="elUser.danweileixing=='非上市公司（企业）-国有公司(企业)'">selected='selected'</s:if>
												value="非上市公司（企业）-国有公司(企业)">
												非上市公司（企业）-国有公司(企业)
											</option>
											<option
												<s:if test="elUser.danweileixing=='非上市公司（企业）-非国有公司(企业)'">selected='selected'</s:if>
												value="非上市公司（企业）-非国有公司(企业)">
												非上市公司（企业）-非国有公司(企业)
											</option>
											<option
												<s:if test="elUser.danweileixing=='农村经济组织'">selected='selected'</s:if>
												value="农村经济组织">
												农村经济组织
											</option>
											<option
												<s:if test="elUser.danweileixing=='民间非营利组织'">selected='selected'</s:if>
												value="民间非营利组织">
												民间非营利组织
											</option>
											<option
												<s:if test="elUser.danweileixing=='其他组织'">selected='selected'</s:if>
												value="其他组织">
												其他组织
											</option>
										</select>
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										非全日制学历（学位）所学专业:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.feisuoxuezhuanye" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<select name="elUser.feisuoxuezhuanye" id="fqrz_byzy"> 
											<s:iterator value="elUser.feisuoxuezhuanyes" status="fsxzy">
												<option
													<s:if test="elUser.feisuoxuezhuanye==elUser.feisuoxuezhuanyes[#fsxzy.index]">selected = 'selected'</s:if>
													value="<s:property />">
													<s:property />
												</option>
											</s:iterator>
										</select>
									</td>
									<td align="right" class="pg_add_head">
										现从事会计工作岗位:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.suozaigangwei" />
									</td>
									<td colspan="2" class="pg_add_content">
										<select name="elUser.suozaigangwei">
											<option
												<s:if test="elUser.kuaijixingzhengzhiwu=='会计机构负责人(会计主管)'">selected='selected'</s:if>
												value="会计机构负责人(会计主管)">
												会计机构负责人(会计主管)
											</option>
											<option
												<s:if test="elUser.suozaigangwei=='出纳'">selected='selected'</s:if>
												value="出纳">
												出纳
											</option>
											<option
												<s:if test="elUser.suozaigangwei=='稽核'">selected='selected'</s:if>
												value="稽核">
												稽核
											</option>
											<option
												<s:if test="elUser.suozaigangwei=='资本、基金核算'">selected='selected'</s:if>
												value="资本、基金核算">
												资本、基金核算
											</option>
											<option
												<s:if test="elUser.suozaigangwei=='收入、支出、债权、债务核算'">selected='selected'</s:if>
												value="收入、支出、债权、债务核算">
												收入、支出、债权、债务核算
											</option>
											<option
												<s:if test="elUser.suozaigangwei=='工资核算、成本费用核算、财务成果核算'">selected='selected'</s:if>
												value="工资核算、成本费用核算、财务成果核算">
												工资核算、成本费用核算、财务成果核算
											</option>
											<option
												<s:if test="elUser.suozaigangwei=='财产物资的收发、增减核算'">selected='selected'</s:if>
												value="财产物资的收发、增减核算">
												财产物资的收发、增减核算
											</option>
											<option
												<s:if test="elUser.suozaigangwei=='总账'">selected='selected'</s:if>
												value="总账">
												总账
											</option>
											<option
												<s:if test="elUser.suozaigangwei=='财务会计报告编制'">selected='selected'</s:if>
												value="财务会计报告编制">
												财务会计报告编制
											</option>
											<option
												<s:if test="elUser.suozaigangwei=='会计机构内档案管理'">selected='selected'</s:if>
												value="会计机构内档案管理">
												会计机构内档案管理
											</option>
											<option
												<s:if test="elUser.suozaigangwei=='其他'">selected='selected'</s:if>
												value="其他">
												其他
											</option>
											<option
												<s:if test="elUser.suozaigangwei=='无'">selected='selected'</s:if>
												value="无">
												无
											</option>
										</select>
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										会计行政职务:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.kuaijixingzhengzhiwu" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<select name="elUser.kuaijixingzhengzhiwu">
											<option
												<s:if test="elUser.suozaigangwei=='一般会计人员'">selected='selected'</s:if>
												value="一般会计人员">
												一般会计人员
											</option>
											<option
												<s:if test="elUser.kuaijixingzhengzhiwu=='会计主管人员'">selected='selected'</s:if>
												value="会计主管人员">
												会计主管人员
											</option>
											<option
												<s:if test="elUser.kuaijixingzhengzhiwu=='会计机构负责人'">selected='selected'</s:if>
												value="会计机构负责人">
												会计机构负责人
											</option>
											<option
												<s:if test="elUser.kuaijixingzhengzhiwu=='总会计师、财务总监'">selected='selected'</s:if>
												value="总会计师、财务总监">
												总会计师、财务总监
											</option>
											<option
												<s:if test="elUser.kuaijixingzhengzhiwu=='其他'">selected='selected'</s:if>
												value="其他">
												其他
											</option>
										</select>
									</td>
									<td align="right" class="pg_add_head">
										现所在地行政区划:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.suozaidixingzhengqu" />
									</td>
									<td colspan="2" class="pg_add_content">
										<select name="elUser.suozaidixingzhengqu"> 
											<s:iterator value="elUser.suozaidixingzhengqus" status="szdxzq">
												<option
													<s:if test="elUser.suozaidixingzhengqu==elUser.suozaidixingzhengqus[#szdxzq.index]">selected = 'selected'</s:if>
													value="<s:property />">
													<s:property />
												</option>
											</s:iterator>
										</select>
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										专业技术资格类型:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.zhuanyezigeleixing" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<select name="elUser.zhuanyezigeleixing" id = "zyjs_lx">
											<option
												<s:if test="elUser.zhuanyezigeleixing=='无'">selected='selected'</s:if>
												value="无">
												无
											</option>
											<option
												<s:if test="elUser.zhuanyezigeleixing=='会计类'">selected='selected'</s:if>
												value="会计类">
												会计类
											</option>
											<option
												<s:if test="elUser.zhuanyezigeleixing=='经济类'">selected='selected'</s:if>
												value="经济类">
												经济类
											</option>
											<option
												<s:if test="elUser.zhuanyezigeleixing=='审计类'">selected='selected'</s:if>
												value="审计类">
												审计类
											</option>
											<option
												<s:if test="elUser.zhuanyezigeleixing=='统计类'">selected='selected'</s:if>
												value="统计类">
												统计类
											</option>
											<option
												<s:if test="elUser.zhuanyezigeleixing=='其他类型'">selected='selected'</s:if>
												value="其他类型">
												其他类型
											</option> 
										</select> 
										<input type="button" value="无相关信息" onclick="ZYJS()">
									</td>
									<td rowspan="9" align="right" class="pg_add_head">
										照片:
									</td>
									<td rowspan="9" class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;" align="center">
										请在添加基本信息后<br/>再添加照片
									</td>
									<td rowspan="8" class="pg_add_content">
										<div id="preview"
											style="filter: progid :                                         DXImageTransform .                                         Microsoft .                                         AlphaImageLoader(sizingMethod =                                         image); width: 114px; height: 156px;">
										</div>
									</td>
									<td rowspan="8" class="pg_add_content" width="90">
										<font style="font-size: 12px; color: #990000">标准证件照片:<br>
											(114x156像素<br> 容量小于20KB<br> JPG文件格式)</font>
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										专业技术资格级别:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.zhuanyezigejibie" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<select name="elUser.zhuanyezigejibie" id="zyjs_1">
											<option
												<s:if test="elUser.zhuanyezigejibie=='无'">selected='selected'</s:if>
												value="无">
												无
											</option>
											<option
												<s:if test="elUser.zhuanyezigejibie=='初级'">selected='selected'</s:if>
												value="初级">
												初级
											</option>
											<option
												<s:if test="elUser.zhuanyezigejibie=='中级'">selected='selected'</s:if>
												value="中级">
												中级
											</option>
											<option
												<s:if test="elUser.zhuanyezigejibie=='高级'">selected='selected'</s:if>
												value="高级">
												高级
											</option>
											<option
												<s:if test="elUser.zhuanyezigejibie=='正高级'">selected='selected'</s:if>
												value="正高级">
												正高级
											</option>
										</select>
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										专业技术资格取得方式:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.zhuanyezigehuoqufangshi" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<select name="elUser.zhuanyezigehuoqufangshi" id = "zyjs_fs">
											<option
												<s:if test="elUser.zhuanyezigehuoqufangshi=='无'">selected='selected'</s:if>
												value="无">
												无
											</option>
											<option
												<s:if test="elUser.zhuanyezigehuoqufangshi=='评审'">selected='selected'</s:if>
												value="评审">
												评审
											</option>
											<option
												<s:if test="elUser.zhuanyezigehuoqufangshi=='考试'">selected='selected'</s:if>
												value="考试">
												考试
											</option>
											<option
												<s:if test="elUser.zhuanyezigehuoqufangshi=='考评'">selected='selected'</s:if>
												value="考评">
												考评
											</option>
										</select>
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										专业技术资格取得时间:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.zhuanyezigehuoquriqi" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<input type="text" id="zyjs_3"
											name="elUser.zhuanyezigehuoquriqi" onclick="setday(this)"
											value="<s:property value="elUser.zhuanyezigehuoquriqi" />" />
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										专业技术资格证书号或批文号:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.zhuanyezigezhengshu" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<input type="text" name="elUser.zhuanyezigezhengshu"
											value="<s:property value="elUser.zhuanyezigezhengshu" />"
											onblur="checkInput(this)" id="zyjs_6" />
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										是否注册会计师:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.zhucekuaijishi" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<select name="elUser.zhucekuaijishi">
											<option
												<s:if test="elUser.zhucekuaijishi==\"否\"">selected='selected'</s:if>
												value="否">
												否
											</option>
											<option
												<s:if test="elUser.zhucekuaijishi==\"是\"">selected='selected'</s:if>
												value="是">
												是
											</option>
										</select>
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										是否注册评估师:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.zhucepinggushi" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<select name="elUser.zhucepinggushi">
											<option
												<s:if test="elUser.zhucepinggushi==\"否\"">selected='selected'</s:if>
												value="否">
												否
											</option>
											<option
												<s:if test="elUser.zhucepinggushi==\"是\"">selected='selected'</s:if>
												value="是">
												是
											</option>
										</select>
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										是否注册税务师:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.zhuceshuiwushi" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<select name="elUser.zhuceshuiwushi">
											<option
												<s:if test="elUser.zhuceshuiwushi==\"否\"">selected='selected'</s:if>
												value="否">
												否
											</option>
											<option
												<s:if test="elUser.zhuceshuiwushi==\"是\"">selected='selected'</s:if>
												value="是">
												是
											</option>
										</select>
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										是否高端人才:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.gaoduanrencai" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<select name="elUser.gaoduanrencai"  onchange="setGd()" id="xx">
											<option
												<s:if test="elUser.gaoduanrencai==\"否\"">selected='selected'</s:if>
												value="否">
												否
											</option>
											<option
												<s:if test="elUser.gaoduanrencai==\"是\"">selected='selected'</s:if>
												value="是">
												是
											</option>
										</select>
									</td>
									<td colspan="2" class="pg_add_content">
									<script type="text/javascript">
											function setGd(){
												var xxv=document.getElementById("xx").value;
												if(xxv=='否'){
													document.getElementById("gd_rq").value='';
													setSelected('');
													document.getElementById("gd_lx").disabled=true;;
													document.getElementById("gd_rq").disabled=true;
												}else{
													setSelected('<s:property value="elUser.gaoduanrencaileixing" />');
													document.getElementById("gd_rq").value='<s:property value="elUser.gaoduanrencairiqi" />';
													document.getElementById("gd_lx").disabled=false;
													document.getElementById("gd_rq").disabled=false;
												}
											}
											function setSelected(val){
												var opts = document.getElementById("gd_lx").options ;
												for(var i =0;i<opts.length;i++){
													if(opts[i].value==val)
														{
															opts[i].selected='selected';
															return ;
														}
														
												}
											}
										</script>
									</td>
								</tr>
								<tr>

									<td align="right" class="pg_add_head">
										高端人才类型:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.gaoduanrencaileixing" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<select name="elUser.gaoduanrencaileixing" id="gd_lx">
											<option
												<s:if test="elUser.gaoduanrencaileixing==''">selected='selected'</s:if>
												value="">
												请选择
											</option>
											<option
												<s:if test="elUser.gaoduanrencaileixing=='全国领军人才'">selected='selected'</s:if>
												value="全国领军人才">
												全国领军人才
											</option>
											<option
												<s:if test="elUser.gaoduanrencaileixing=='区域或行业性拔尖（领军）人才'">selected='selected'</s:if>
												value="区域或行业性拔尖（领军）人才">
												区域或行业性拔尖（领军）人才
											</option>
										</select>
									</td>
									<td align="right" class="pg_add_head" id="zctexttd">
										角色权限:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.role.name" />
									</td>
									<td colspan="2" class="pg_add_content">
										<select name="elUser.role.id">
											<s:iterator value="roles">
												<option
													<s:if test="id==4">selected='selected'</s:if>
													value="<s:property value="id"/>">
													<s:property value="name" />
												</option>
											</s:iterator>
										</select>
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										高端人才资格取得时间:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.gaoduanrencairiqi" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">

										<input type="text" name="elUser.gaoduanrencairiqi" size="13"
											value="<s:property value="elUser.gaoduanrencairiqi" />"
											id="gd_rq" onclick="setday(this)" />

									</td>

									<td align="right" class="pg_add_head" id="zctexttd">
										开通状态:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.validName" />
									</td>
									<td colspan="2" class="pg_add_content">
										<label>
											<input type="radio" name="elUser.valid" value="true"
												checked="checked" />
											开通
											<input type="radio" name="elUser.valid" value="false" />
											关闭
										</label>
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										备注:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.beizhu" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<input type="text" name="elUser.beizhu"
											value="<s:property value="elUser.beizhu" />" />
									</td>
									<td align="right" class="pg_add_head" id="zctexttd">
										所属部门:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
									</td>
									<td colspan="2" class="pg_add_content">
										<select name="elUser.department.id">
											<wysLib:dep_select selectid="${elUser.department.id}" />
										</select>
									</td>
								</tr>
								<tr>
									<td align="right" class="pg_add_head">
										用户名:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.username" />
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:textfield name="elUser.username" />
									</td>
									<td align="right" class="pg_add_head" id="zctexttd">
										密码:
									</td>
									<td class="pg_add_content"
										style="border-right: 1px solid #FFFFFF;">
										<s:property value="elUser.password" />
									</td>
									<td colspan="2" class="pg_add_content">
										<input type="text" name="elUser.password"
											value="<s:property value="elUser.password" />" />
									</td>
								</tr>
								<TR class="pg_add_bottom">
									<TD colspan="7">
										<DIV align="center">
											<input type="hidden" name="elUser.company.id"
												value="<s:property value="company.id" />" />
											<input type="submit" name="bgsqSubmit" value="提交保存"
												class="button_normal" onfocus="return false;">
											<a href="#" onclick="history.back(-1);return false;">
												取消返回 </a>
										</div> 
									</td> 
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</s:form>
	
	</body>
</HTML>
<SCRIPT type="text/javascript">
			if("${elmessage}"!='null'&&"${elmessage}"!='')
				 alert("${elmessage}!");
		</SCRIPT>