<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
	<HEAD>
		<META content="text/html; charset=UTF-8" http-equiv=Content-Type>
		<TITLE>用户注册</TITLE>
		<META name=description content="">
		<LINK rel=stylesheet type=text/css href="images/reg/style_110531.css">
		<LINK rel=stylesheet type=text/css href="images/reg/patch120202.css">
		<LINK href="elfrontimages/menu.css" rel=stylesheet type="text/css">
		<STYLE type=text/css>
			.chose-list .recommend A {
				ZOOM: 1;
				COLOR: #000;
				TEXT-DECORATION: none
			}
			
			.chose-list .recommend A:hover {
				TEXT-DECORATION: underline
			}
			
			.chose-list .recommend LABEL {
				CURSOR: default
			}
			
			.error {
				color: red;
			}
		</STYLE>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/jquery.validate.js"></script>
		<script type="text/javascript" src="js/userCheck.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		
		<META name=GENERATOR content="MSHTML 8.00.6001.19088">
	</HEAD>
	<BODY >
	<%@include file="frontheader.jsp"%>
		<table width="700" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="245" height="50">&nbsp;</td>
        <td width="10" align="left">&nbsp;</td>
        <td align="left">&nbsp;</td>
      </tr>
      <s:if test="workCourses.size()>0">
      <s:iterator value="workCourses" >
      <tr>
        <td width="245" height="75" align="center" background="images/sdimages/button.png">
        <a href="sd_registerInit.action?elUser.gangwei=<s:property value='work_type'/>" style="font-size:30px;color:white;font-weight:bold;font-family:微软雅黑;">
        <s:property value="work_anniu_name"/></a></td>
        <td width="10" align="left">&nbsp;</td>
        <td align="left"><s:property value="description"/></td>
      </tr>
       <tr>
        <td width="245" height="75">&nbsp;</td>
        <td width="10" align="left">&nbsp;</td>
        <td align="left">&nbsp;</td>
      </tr>
      </s:iterator>
      </s:if>
      <s:else>
      	还未添加职业人群
      	
      </s:else>
      <%-- 
      <tr>
        <td width="245" height="75">&nbsp;</td>
        <td width="10" align="left">&nbsp;</td>
        <td align="left">&nbsp;</td>
      </tr>
      <tr>
        <td width="245" height="75" align="center" background="images/sdimages/button.png">
        <a href="sd_registerInit.action?elUser.gangwei=225" style="font-size:20px;color:white;font-weight:bold;font-family:微软雅黑;">餐饮服务及<br />
        食品加工业人员</a></td>
        <td width="10" align="left">&nbsp;</td>
        <td align="left">餐饮服务及食品加工业人员：主要指在餐饮服务单位、食品生产加工企业的工作人员，包括管理人员、厨师、服务员、生产技术人员等。</td>
      </tr>
      <tr>
        <td width="245" height="75">&nbsp;</td>
        <td width="10" align="left">&nbsp;</td>
        <td align="left">&nbsp;</td>
      </tr>
      <tr>
        <td width="245" height="75" align="center" background="images/sdimages/button.png">
        <a href="sd_registerInit.action?elUser.gangwei=226" style="font-size:30px;color:white;font-weight:bold;font-family:微软雅黑;">大众人群</a></td>
        <td width="10" align="left">&nbsp;</td>
        <td align="left">大众人群：主要指除以上两类人群以外的其他人员，如教师、职员、领导干部、家庭妇女等。</td>
      </tr> --%>
    </table>
	</BODY>
</HTML>