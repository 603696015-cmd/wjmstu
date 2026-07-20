<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
					<s:if test="#session.username!=null">
						<table width="233" border="0" align="right" cellpadding="0"
							cellspacing="0">
							<tr>
								<td height="197" align="center" valign="bottom"
									background="elfrontimages/rembg.jpg">
									<table width="98%" border=0 align=center cellPadding=0
										cellSpacing=0>
										<tbody>
											<tr>
												<td height=25>
													用户名：
													<s:property value="#session.username" />
												</td>
											</tr>
											<tr>
												<td height=25>
													姓 名：
													<s:property value="#session.realname" />
												</td>
											</tr>
											<tr>
												<td height=25>&nbsp;
													

												</td>
											</tr>
											<tr>
												<td height=25>
													<div align=center>
														<img src="elfrontimages/losspass.gif" align=absMiddle />
														<a href="study.action" target=_parent>个人中心</a>
														<img src="elfrontimages/mas.gif" align=absMiddle />
														<a href="logout.action" target=_parent>退出</a>
														<br>
														<img src="elfrontimages/losspass.gif" align=absMiddle />
														<a href="cisco_user_center.action" target=_parent>北京市卫生局用户个人中心</a>
													</div>
												</td>
											</tr>
											<tr>
												<td height=25>
													<div align=center>
														&nbsp; &nbsp; &nbsp;
													</div>
												</td>
											</tr>
										</tbody>
									</table>
								</td>
							</tr>
						</table>
					</s:if>
					<s:else>
						<table width="233" border="0" align="right" cellpadding="0"
							cellspacing="0">
							<tr>
								<td height="197" align="center" valign="middle"
									background="elfrontimages/rembg.jpg">
									<form name=myform action=login.action style="margin: 0px;"
										method=post>
										<input type="hidden" name="isFromRegister" value="1" />
										<table style="margin-top: 30px;" width="98%" border=0
											align=center cellpadding=0 cellspacing=0>
											<tbody>
												<tr>
													<td height=25>
														用户名：
														<input class="textbox" id=Username name="elUser.username" />
													</td>
												</tr>
												<tr>
													<td height=35>
														密 &nbsp; 码：
														<input class="textbox" type=password
															name="elUser.password"  width="153px"/>
													</td>
												</tr>
												<!--<tr>
													<td height=25>
														验证码：
														<input class=textbox  
															size=6 name=yzCode />&nbsp;&nbsp;&nbsp;&nbsp;
														<img height="23" width="57" align="bottom"
															src="image2.jsp"
															onClick="this.src='image.jsp?'+Math.random()"
															title="点击刷新验证码" />
													</td>
												</tr>-->
												<tr>
													<td height=25>
														<div align=center style="margin-top: 5px;">
															<input class=textbg4 onclick=return(CheckForm())
																type=submit value=登录 name=Submit />
															&nbsp;&nbsp;
															<img src="elfrontimages/mas.gif" align=absMiddle />
															<s:if test="registerstatus==0">
																<a href="javascript:isregister();" target=_parent>注册</a>
															</s:if>
															<s:else>
																<a href="registerInit.action" target=_parent>注册</a>
															</s:else>
															<span class="STYLE2">*</span>
															<br>
															<a href="admin/newversion/cisco_user_center_login.jsp" target=_parent>北京市卫生局登录</a>
															<a href="cisco_registerInit.action" target=_parent>北京市卫生局注册</a>
															<%-- <a href="loginpki.action">PKI登录</a> --%>
															<!--<a href="javascript:pkilogin();">PKI登录</a>-->
														</div>
													</td>
												</tr>
											</tbody>
										</table>
									</form>
								</td>
							</tr>
						</table>
					</s:else>
