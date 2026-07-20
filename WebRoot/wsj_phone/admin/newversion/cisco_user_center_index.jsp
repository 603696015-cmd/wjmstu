<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>个人中心</title>
		<meta name="keywords" content="OA,OA办公系统,OA系统" />
		<meta name="description"
			content="通达OA系统代表了协同OA的先进理念,是中国用户群最广泛的OA软件,协同OA软件行业唯一央企团队研发,多次摘取国内OA软件金奖,拥有300万终端OA用户,十年研发铸就成熟OA产品" />
		<script type="text/javascript" src="js/113.js"></script>
		<script type="text/javascript" src="js/switch.combo.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript">
			function  user_center(){
					//alert(isBuyNianjianClass);
					<s:if test="isBuyNianjianClass==0">
							window.location.href="myelclass_view.action?type=1&elclass.id=<s:property value='new_cla.elClass.id' />&needAllocation = <s:property value='needAllocation' />&Return=stclalist";
												
					</s:if>
					<s:else>
							window.location.href="myelclass_view.action?type=1&elclass.id=<s:property value='nianjian_cla.elClass.id'/>&needAllocation = <s:property value='needAllocation' />&Return=stclalist";
												
					</s:else>
			
			}
		</script>
		<script type="text/javascript">
			function jieyekaoshi(){
				//检查是否可以参加考试
			}
		</script>
		<link href="css/global.css" rel="stylesheet" type="text/css" />
		<link href="css/index_newversion.css" rel="stylesheet" type="text/css" />
		<link type="text/css" href="css/base.css" rel="stylesheet" />
		<link type="text/css" href="css/qhIndex_newversion.css"
			rel="stylesheet" />
		<link href="css/style2013.css" rel="stylesheet" type="text/css" />

		<style type="text/css">
		<!--
		.STYLE1 {
			color: #FF0000;
			font-weight: bold;
			font-size: 14px;
		}
		
		.STYLE2 {
			color: #666666;
			font-weight: bold;
		}
		-->
		</style>
	</head>
	<body onload="user_center();">
	
										
											
											
										
	</body>
	
</html>

