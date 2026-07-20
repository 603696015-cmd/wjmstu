<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.sopia.common.IntelligentSystemConfOp"%>
<%@page import="com.sopia.ElConstants"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="newversionLib" uri="/WEB-INF/newversionLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>个人中心新首页</title>
		<link href="css/global.css" rel="stylesheet" type="text/css" />
		<link href="css/index.css" rel="stylesheet" type="text/css" />
		<link type="text/css" href="css/base.css" rel="stylesheet" />
		<link type="text/css" href="css/qhIndex.css" rel="stylesheet" />
		<link href="css/style2013.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript">
		function showI(){
			window.location.href="showIntelligent.action?peixunBatch.nowClass.id=<s:property value="elClass.id" />";
		}
		</script>
		<style>
#ddd img {
	display: block;
}

.STYLE1 {
	font-size: 36px;
	font-weight: bold;
}
</style>

	</head>

	<body >
		<s:if test="peixunBatch!=null && peixunBatch.nowClass!=null && peixunBatch.nowClass.id>0">
		<table width="900" border="0" align="center" cellpadding="5"
									cellspacing="1" bgcolor="#CFDBE2" style="margin-top:20px;">
									<tr>
									<td colspan="5"><s:property value="classname"/>级智能辅导分</td>
									</tr>
									<tr>
										<td align="center" valign="middle" background="images/bg002.jpg"
											>
											
												考查类别
											
									  </td>
										<td height="30" align="center" valign="middle" background="images/bg002.jpg"
											>
											
												考查指标									  </td>
										<td align="center" valign="middle" background="images/bg002.jpg"
											>
											
												满分
											
									  </td>
										<td width="120" align="center" valign="middle" background="images/bg002.jpg"
											>
											
												得分
											
									  </td>
										<td width="120" align="center" valign="middle" background="images/bg002.jpg"
											>
											
									  </td>
									</tr>
									<tr>
										<td rowspan="3" align="center" bgcolor="#F8FCFE" class="di">
											
												学习时间
											
									  </td>
										<td height="40" align="center" bgcolor="#F8FCFE" >
											
												每天学习时间加分
											
									  </td>
										<td align="center" bgcolor="#F8FCFE" >
											<p align="center">
												<%=IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCORELOGIN) %>
											</p>
									  </td>
										<td width="120" align="center" valign="middle" bgcolor="#F8FCFE"
											>
											<p>
												<s:property value="intelligentTutoringPoints.scoreLogin"/>
											</p>
									  </td>
										<td width="120" align="center" valign="middle" bgcolor="#F8FCFE">
											<table width="95" border="0" align="center" cellpadding="0" cellspacing="0">
                                              <tr>
                                                <td height="28" align="center" valign="middle" background="images/textbg.jpg"><a href="loginInfo.action?elClass.id=${elClass.id }"><span style="font-size:14px;font-weight:bold;color:white;">详 情</span></a></td>
                                              </tr>
                                            </table>
                                        </td>
									</tr>
									<tr>
										<td height="40" align="center" bgcolor="#F8FCFE" >
											<p>
												每周的学习时间加分
											</p>
									  </td>
										<td align="center" bgcolor="#F8FCFE" >
											<p align="center">
												<%=IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREWEEK) %>
											</p>
									  </td>
										<td width="120" align="center" valign="middle" bgcolor="#F8FCFE"
											>
											<p>
												<s:property value="intelligentTutoringPoints.scoreWeek"/>
											</p>
									  </td>
										
									  <td width="120" align="center" valign="middle" bgcolor="#F8FCFE">
											<table width="95" border="0" align="center" cellpadding="0" cellspacing="0">
                                              <tr>
                                                <td height="28" align="center" valign="middle" background="images/textbg.jpg"><a href="weekInfo.action?elClass.id=<s:property value="peixunBatch.nowClass.id" />"><span style="font-size:14px;font-weight:bold;color:white;">详 情</span></a></td>
                                              </tr>
                                            </table>
                                      </td>
									</tr>
									<tr>
										<td height="40" align="center" bgcolor="#F8FCFE" >
											<p>
												等级总学习时间得分
											</p>
									  </td>
										<td align="center" bgcolor="#F8FCFE" >
											<p align="center">
												<%=IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCORECLASS) %>
											</p>
									  </td>
										<td width="120" align="center" valign="middle" bgcolor="#F8FCFE"
											>
											<p>
												<s:property value="intelligentTutoringPoints.scoreClass"/>
											</p>
									  </td>
									  <td width="120" align="center" valign="middle" bgcolor="#F8FCFE">
											<table width="95" border="0" align="center" cellpadding="0" cellspacing="0">
                                              <tr>
                                                <td height="28" align="center" valign="middle" background="images/textbg.jpg"><a href="classInfo.action?elClass.id=<s:property value="peixunBatch.nowClass.id" />"><span style="font-size:14px;font-weight:bold;color:white;">详 情</span></a></td>
                                              </tr>
                                            </table>
                                      </td>
									</tr>
									<tr>
										<td rowspan="4" align="center" bgcolor="#F8FCFE" class="di">
											<p>
												学习习惯
											</p>
									  </td>
										<td height="40" align="center" bgcolor="#F8FCFE">
											<p>
												复听题量智能辅导分
											</p>
									  </td>
										<td align="center" bgcolor="#F8FCFE" >
											<p align="center">
												<%=IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREPROPORTIONPROCESS) %>
											</p>
									  </td>
										<td width="120" align="center" valign="middle" bgcolor="#F8FCFE"
											>
											<p>
												<s:property value="intelligentTutoringPoints.scoreProportionQ"/>
											</p>
									  </td>
									  <td width="120" align="center" valign="middle" bgcolor="#F8FCFE">
											<table width="95" border="0" align="center" cellpadding="0" cellspacing="0">
                                              <tr>
                                                <td height="28" align="center" valign="middle" background="images/textbg.jpg"><a href="proportionQInfo.action?elClass.id=<s:property value="peixunBatch.nowClass.id" />"><span style="font-size:14px;font-weight:bold;color:white;">详 情</span></a></td>
                                              </tr>
                                            </table>
                                      </td>
									</tr>
									<tr>
										<td height="40" align="center" bgcolor="#F8FCFE">
											<p>
												复听次数智能辅导分
											</p>
									  </td>
										<td align="center" bgcolor="#F8FCFE" >
											<p align="center">
												<%=IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREPROPORTIONTIME) %>
											</p>
									  </td>
										<td width="120" align="center" valign="middle" bgcolor="#F8FCFE"
											>
											<p>
												<s:property value="intelligentTutoringPoints.scoreProportionT"/>
											</p>
									  </td>
									  <td width="120" align="center" valign="middle" bgcolor="#F8FCFE">
											<table width="95" border="0" align="center" cellpadding="0" cellspacing="0">
                                              <tr>
                                                <td height="28" align="center" valign="middle" background="images/textbg.jpg"><a href="proportionTInfo.action?elClass.id=<s:property value="peixunBatch.nowClass.id" />"><span style="font-size:14px;font-weight:bold;color:white;">详 情</span></a></td>
                                              </tr>
                                            </table>
                                      </td>
									</tr>
									<tr>
										<td height="40" align="center" bgcolor="#F8FCFE" >
											<p>
												录音题量智能辅导分
											</p>
									  </td>
										<td align="center" bgcolor="#F8FCFE">
											<p align="center">
												<%=IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCORERECODINGPROCESS) %>
											</p>
									  </td>
										<td width="120" align="center" valign="middle" bgcolor="#F8FCFE"
											>
											<p>
												<s:property value="intelligentTutoringPoints.scoreRecodingQ"/>
											</p>
									  </td>
									  <td width="120" align="center" valign="middle" bgcolor="#F8FCFE">
											<table width="95" border="0" align="center" cellpadding="0" cellspacing="0">
                                              <tr>
                                                <td height="28" align="center" valign="middle" background="images/textbg.jpg"><a href="recodingQInfo.action?elClass.id=<s:property value="peixunBatch.nowClass.id" />"><span style="font-size:14px;font-weight:bold;color:white;">详 情</span></a></td>
                                              </tr>
                                            </table>
                                      </td>
									</tr>
									<tr>
										<td height="40" align="center" bgcolor="#F8FCFE" >
											<p>
												录音次数智能辅导分
											</p>
									  </td>
										<td align="center" bgcolor="#F8FCFE">
											<p align="center">
												<%=IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCORERECODINGTIME) %>
											</p>
									  </td>
										<td width="120" align="center" valign="middle" bgcolor="#F8FCFE"
											>
											<p>
												<s:property value="intelligentTutoringPoints.scoreRecodingT"/>
											</p>
									  </td>
									  <td width="120" align="center" valign="middle" bgcolor="#F8FCFE">
											<table width="95" border="0" align="center" cellpadding="0" cellspacing="0">
                                              <tr>
                                                <td height="28" align="center" valign="middle" background="images/textbg.jpg"><a href="recodingTInfo.action?elClass.id=<s:property value="peixunBatch.nowClass.id" />"><span style="font-size:14px;font-weight:bold;color:white;">详 情</span></a></td>
                                              </tr>
                                            </table>
                                      </td>
									</tr>
									<tr>
										<td rowspan="2" align="center" bgcolor="#F8FCFE" class="di">
											<p>
												学习成绩
											</p>
									  </td>
										<td height="40" align="center" bgcolor="#F8FCFE" >
											<p>
												模块练习成绩智能辅分
											</p>
									  </td>
										<td align="center" bgcolor="#F8FCFE" >
											<p align="center">
												<%=IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREEXAMPAGE) %>
											</p>
									  </td>
										<td width="120" align="center" valign="middle" bgcolor="#F8FCFE"
											>
											<p>
												<s:property value="intelligentTutoringPoints.scoreAcademic"/>
											</p>
									  </td>
									  <td width="120" align="center" valign="middle" bgcolor="#F8FCFE">
											<table width="95" border="0" align="center" cellpadding="0" cellspacing="0">
                                              <tr>
                                                <td height="28" align="center" valign="middle" background="images/textbg.jpg"><a href="academicInfo.action?elClass.id=<s:property value="peixunBatch.nowClass.id" />"><span style="font-size:14px;font-weight:bold;color:white;">详 情</span></a></td>
                                              </tr>
                                            </table>
                                      </td>
									</tr>
									<tr>
										<td height="40" align="center" bgcolor="#F8FCFE">
											<p>
												单元测验辅导分
											</p>
									  </td>
										<td align="center" bgcolor="#F8FCFE" >
											<p align="center">
												<%=IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREEXAMCOURSE) %>
											</p>
									  </td>
										<td width="120" align="center" valign="middle" bgcolor="#F8FCFE"
											>
											<p>
												<s:property value="intelligentTutoringPoints.scoreAcademicCourse"/>
											</p>
									  </td>
									  <td width="120" align="center" valign="middle" bgcolor="#F8FCFE">
											<table width="95" border="0" align="center" cellpadding="0" cellspacing="0">
                                              <tr>
                                                <td height="28" align="center" valign="middle" background="images/textbg.jpg"><a href="academicCourseInfo.action?elClass.id=<s:property value="peixunBatch.nowClass.id" />"><span style="font-size:14px;font-weight:bold;color:white;">详 情</span></a></td>
                                              </tr>
                                            </table>
                                      </td>
									</tr>
									<tr>
										<td height="50" align="center" bgcolor="#F8FCFE"  class="di">
											<p>
												合计											</p>
									  </td>
										<td align="center" bgcolor="#F8FCFE" >
											<p align="center">
												---
											</p>
									  </td>
										<td align="center" bgcolor="#F8FCFE" >
											<p align="center" style="color:red;font-weight:bold;">
												<%
													double totalScore = 
														IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCORELOGIN) + 
														IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREWEEK) + 
														IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCORECLASS) + 
														IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREPROPORTIONPROCESS) + 
														IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREPROPORTIONTIME) + 
														IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCORERECODINGPROCESS) + 
														IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCORERECODINGTIME) + 
														IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREEXAMPAGE) + 
														IntelligentSystemConfOp.getDoubleValue(ElConstants.SYSTEM_SCOREEXAMCOURSE);
												%>
												<%=totalScore %>
											</p>
									  </td>
										<td width="120" align="center" valign="middle" bgcolor="#F8FCFE"
											>
											<p style="color:red;font-weight:bold;">
												<s:property value="intelligentTutoringPoints.totalScore"/>
											</p>
									  </td>
										<td width="120" align="center" valign="middle" bgcolor="#F8FCFE"
											>
											<p>
												---
											</p>
									  </td>
									</tr>
    </table>
    </s:if>
    <s:else>
    <table width="900" border="0" align="center" cellpadding="5"
									cellspacing="1" bgcolor="#CFDBE2" style="margin-top:20px;">
		<tr>
			<td align="center" valign="middle" background="images/bg002.jpg">
					
						没有正在学习的等级,无法查看
					
			</td>
		</tr>
	</table>
    </s:else>
		

	    <p>&nbsp;</p>
	    <p>&nbsp;</p>
	</body>
</html>



