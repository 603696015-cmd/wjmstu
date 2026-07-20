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
									<marquee style="WIDTH: 620px; HEIGHT: 40px;" scrollamount="2"
										direction="left">
									<div
										style="font-size: 13px; color: white; padding-top: 10px; letter-spacing: 1px; width: 1800px;">
<!--										1、“医护人员”主要指在卫生医疗机构（医院、卫生室、疾控中心）工作的医学专业技术人员等。2、“餐饮服务及食品加工业人员”主要指在餐饮服务单位、食品生产加工企业的工作人员，包括管理人员、厨师、服务员、生产技术人员等。3、大众人群主要指除以上两类人群以外的其他人员，如教师、职员、领导干部、家庭妇女等。-->
										<s:property value="des"/>
									</div>

									</marquee>
								</td>
								<td width="360">
									<DIV id=menu2_bg>
										<DIV class=menu2>
										  
											<LI>
												<A href="sd_user_center.action">个人首页</A>
											</LI>
											<LI>
												<A class=here href="student_mypwdalterInit.action">修改密码</A>
											</LI>
											  <LI>
												<A class=here href="logout.action">退出</A>
											</LI>
										</DIV>
									</DIV>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
				
			<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td><iframe src="${module}" id="rightFrame" name="rightFrame" align="middle" width="100%" height="100%"
						scrolling="no" frameborder="0" style="z-index: 9999;padding-bottom:0px;"></iframe></td>
              </tr>
            </table>
			
		</div>
	</body>
</html>
