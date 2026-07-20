<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<title>用户注册 -中国食品安全培训网</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<link href="css/login.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript">
			function _onsubmit(){
				if($("#danwei").val()==''){
					alert("请填写单位编码");
					$("#danwei").focus();
					return false;
				}
				if($("#username").val()==''){
					alert("请填写用户名");
					$("#username").focus();
					return false;
				}
				if($("#password").val()==''){
					alert("请填写密码");
					$("#password").focus();
					return false;
				}
				if($("#password").val()!= $("#password1").val()){
					alert("两次密码不一致，请重输入");
					$("#password1").focus();
					return false;
				}
				var _id = $("#shenfenzheng").val();
				var _valid=false;      
			    if(_id.length==15){      
			        _valid=validId15(_id);      
			    }else if(_id.length==18){      
			        _valid=validId18(_id);      
			    }      
			    if(!_valid){      
			        alert("身份证号码有误！请您核对");      
			        document.getElementById("shenfenzheng").focus();   
			        return false;      
			    }      
				return true;
			}
		function validId18(_id){      
		    _id=_id+"";      
		    var _num=_id.substr(0,17);      
		    var _parityBit=_id.substr(17);      
		    var _power=0;      
		    for(var i=0;i< 17;i++){      
		        if(_num.charAt(i)<'0'||_num.charAt(i)>'9'){      
		            return false;      
		            break;      
		        }else{      
		            _power+=parseInt(_num.charAt(i))*parseInt(powers[i]);      
		        }      
		    }      
		    var mod=parseInt(_power)%11;      
		    if(parityBit[mod]==_parityBit){      
		        return true;      
		    }      
		    return false;      
		}      
		//15   
		function validId15(_id){      
		    _id=_id+"";      
		    for(var i=0;i<_id.length;i++){      
		        if(_id.charAt(i)<'0'||_id.charAt(i)>'9'){      
		            return false;      
		            break;      
		        }      
		    }      
		    var year=_id.substr(6,2);      
		    var month=_id.substr(8,2);      
		    var day=_id.substr(10,2);      
		    var sexBit=_id.substr(14);      
		    if(year<'01'||year >'90')return false;      
		    if(month<'01'||month >'12')return false;      
		    if(day<'01'||day >'31')return false;      
		    return true;      
		}     
function openWin(){
     width=600;
	 height=500;
  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
	 window.showModalDialog("depDisplayByName.action",null,sFeature);
}
		</script>
		<style type="text/css">
<!--
.STYLE1 {
	font-family: "宋体"
}

.td_left {
	color: red;
	font-size: 12px;
}

input {
	border: 1px solid;
}
body {
	background-color: #F0F0F0;
}
-->
</style>
	</HEAD>
	<BODY>
		<table width="100%" height="564" border="0" cellpadding="0"
			cellspacing="0" background="images/regbgline.jpg">
			<tr>
				<td valign="top">
					<table width="1000" height="564" border="0" align="center"
						cellpadding="0" cellspacing="0" class="regbg">
						<tr>
							<td valign="top">
								<table width="1000" height="564" align="center">
									<tr>
										<td width="320">
										</td>
										<td valign="top" style="padding-top: 20px;">
											<s:form onsubmit="return _onsubmit()" action="register.action" method="post" theme="simple">
												<table width="500" cellpadding="0" cellspacing="2"
													bgcolor="#FFFFFF" id="info1" style="margin-top: 140px;">
													<tr>
														<td width="120" height="30" colspan="4" align="center"
															bgcolor="#A6E2FF" class="td_left">
															 <strong>${elmessage}</strong>
														</td>
													</tr>
												
													<tr>
														<td width="120" height="30" align="center"
															bgcolor="#A6E2FF" class="td_left">
															<strong>单位代码</strong>
														</td>
														<td height="30" align="left" bgcolor="#A6E2FF">
															<label>
																<s:textfield name="elUser.danwei" size="13" id="danwei"/>
															<!-- 	<a style="font-size:13px;color:red;" href="#" onclick="openWin();">搜索</a> -->
															</label>
														</td>
														<td width="120" height="30" align="center"
															bgcolor="#A6E2FF" class="td_left">
															<strong>用户名</strong>
														</td>
														<td height="30" align="left" bgcolor="#A6E2FF">
															<label>
																<s:textfield name="elUser.username" id="username" />
															</label>
														</td>
													</tr>
													<tr>
														<td width="120" height="30" align="center"
															bgcolor="#A6E2FF" class="td_left">
															<strong>密 码</strong>
														</td>
														<td height="30" align="left" bgcolor="#A6E2FF">
															<label>
																<input type="password" name="elUser.password" id="password" />
															</label>
														</td>
														<td width="120" height="30" align="center"
															bgcolor="#A6E2FF" class="td_left">
															<strong>密码确认</strong>
														</td>
														<td height="30" align="left" bgcolor="#A6E2FF">
															<label>

																<input type="password"  id="password1" />
															</label>
														</td>
													</tr>
													<tr>
														<td width="120" height="30" align="center"
															bgcolor="#A6E2FF" class="td_left">
															<strong>姓 名</strong>
														</td>
														<td height="30" align="left" bgcolor="#A6E2FF">
															<label>
																<s:textfield name="elUser.realname" id="realname" />
															</label>
														</td>
														<td width="120" height="30" align="center"
															bgcolor="#A6E2FF" class="td_left">
															<strong>性别</strong>
														</td>
														<td height="30" align="left" bgcolor="#A6E2FF">
															<label>
																<input type="radio" name="elUser.sex" value="男"
																	checked="checked" />
																男
																<input type="radio" name="elUser.sex" value="女" />
																女
															</label>
														</td>
													</tr>
													<tr>
														<td width="120" height="30" align="center"
															bgcolor="#A6E2FF" class="td_left">
															<strong> 地市</strong>
														</td>
														<td height="30" align="left" bgcolor="#A6E2FF">
															<label>
																<s:textfield name="elUser.dishi" id="dishi" />
															</label>
														</td>
														<td width="120" height="30" align="center"
															bgcolor="#A6E2FF" class="td_left">
															<strong> 身份证号 </strong>
														</td>
														<td height="30" align="left" bgcolor="#A6E2FF">
															<label>
																<s:textfield name="elUser.shenfenzheng" id="shenfenzheng"/>
															</label>
														</td>
													</tr>
													<tr>
														<td width="120" height="30" align="center"
															bgcolor="#A6E2FF" class="td_left">
															<strong>出生日期</strong>
														</td>
														<td height="30" align="left" bgcolor="#A6E2FF">
															<label>
																<input type="text" name="elUser.shengri"
																	value="1980-01-01" readonly="readonly"
																	onclick="setday(this)" />
															</label>
														</td>
														<td width="120" height="30" align="center"
															bgcolor="#A6E2FF" class="td_left">
															<strong> 职级 </strong>
														</td>
														<td height="30" align="left" bgcolor="#A6E2FF">
															<label>
																<s:textfield name="elUser.zhiji" />
															</label>
														</td>
													</tr>
													<tr>
														<td width="120" height="30" align="center"
															bgcolor="#A6E2FF" class="td_left">
															<strong> 职务 </strong>
														</td>
														<td height="30" align="left" bgcolor="#A6E2FF">
															<label>
																<s:textfield name="elUser.zhiwu" />
															</label>
														</td>
														<td width="120" height="30" align="center"
															bgcolor="#A6E2FF" class="td_left">
															<strong> 工种 </strong>
														</td>
														<td height="30" align="left" bgcolor="#A6E2FF">
															<label>
																<select name="elUser.jingzhong">
																	<s:iterator value="elUser.jingzhongs">
																		<option value="<s:property />">
																			<s:property />
																		</option>
																	</s:iterator>
																</select>
															</label>
														</td>
													</tr>
													<tr>
														<td width="120" height="30" align="center"
															bgcolor="#A6E2FF" class="td_left">
															<strong> 岗位 </strong>
														</td>
														<td height="30" align="left" bgcolor="#A6E2FF">
															<s:textfield name="elUser.gangwei" />
														</td>
														<td width="120" colspan="2" height="30" align="center"
															bgcolor="#A6E2FF" class="td_left">
															<strong>
															   <!-- <a style="text-align:center;color:red;" href="javascript:openWin();">单位代码查询</a> -->
															    <!-- <a style="text-align:center;color:red;" target="top" href="admin/duman/depDisplayPage.jsp">单位代码查询</a> -->
															    <a style="text-align:center;color:blue;font-size:13px;" target="top" href="depDisplayByName.action">单位代码查询</a>
															</strong>
														</td>
													</tr>
													<tr>
														<td width="120" height="30" align="center"
															bgcolor="#A6E2FF" class="td_left">
														</td>
														<td height="30" colspan="3" align="left" bgcolor="#A6E2FF">
															<input type="submit" value="提交" />
														</td>
													</tr>
												</table>
												<s:hidden name="elUser.role.id" value="4" />
												<s:hidden name="elUser.department.id" value="1" />
											</s:form>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	
	</body>
</HTML>
