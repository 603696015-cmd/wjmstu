<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="newversionLib" uri="/WEB-INF/newversionLib.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>个人中心</title>
		<meta name="keywords" content="" />
		<meta name="description" content="" />
		<link href="css/global.css" rel="stylesheet" type="text/css" />
		<link href="css/index_newversion.css" rel="stylesheet" type="text/css" />
		<link type="text/css" href="css/base.css" rel="stylesheet"/>
		<link type="text/css" href="css/qhIndex_newversion.css" rel="stylesheet"/>
		<link href="css/style2013.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/113.js"></script>
		<script type="text/javascript" src="js/switch.combo.js"></script>
		<script type="text/javascript">
		function iframe(){
			document.all("rightFrame").height=rightFrame.document.body.scrollHeight;
			document.all("rightFrame").width=rightFrame.document.body.scrollWidth;
		}
		
		function full_screen(flag){
			if(flag){
				alert(flag);
			}else{
			}
			return false;
		}
		</script>
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
	</HEAD>
	<body onload="iframe();">
		<div id="container">
			<table width="1002" height="42" border="0" align="center"
				cellpadding="0" cellspacing="0">
				<tr>
					<td background="images/bg-nav.png">
						<table width="99%" border="0" align="right" cellpadding="0"
							cellspacing="0" >
							<tr>
								<td align="left" valign="middle">
									<!--<marquee style="WIDTH: 700px; HEIGHT: 40px;" scrollamount="2"
										direction="left">
									<div
										style="font-size: 13px; color: white; padding-top: 10px; letter-spacing: 1px; width: 1800px;">
										角色欢迎辞滚动文字角色欢迎辞滚动文字角色欢迎辞滚动文字角色欢迎辞滚动文字角色欢迎辞滚动文字角色欢迎辞滚动文字
									</div>

									</marquee>-->
								</td>
								<td width="240">
									<DIV id=menu2_bg>
										<DIV class=menu2>
											<LI>
												<A href="cisco_user_center.action">个人首页</A>
											</LI>
											<LI>
												<A class=here href="index.action">网站首页</A>
											</LI>
										</DIV>
									</DIV>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
				
			
			<table width=1000 border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td><iframe src="${module}" id="rightFrame" name="rightFrame" align="middle" width="100%" height="100%"
						scrolling="no" frameborder="0" style="z-index: 9999;padding-bottom:0px;"></iframe></td>
              </tr>
            </table>
			
		</div>
	</body>
</html>
