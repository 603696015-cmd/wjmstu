<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.classman.entities.ElClType"%> 
<%@page import="com.sopia.courseman.entities.EroomLib"%> 
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<%
	String eroomId = "";
	if (request.getAttribute("eroomLib") != null) {
		eroomId = ((EroomLib) request.getAttribute("eroomLib"))
				.getId()+ "";
	}else{ 
		eroomId = "1";
	} 
%>

<HTML>
	<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />

		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>中国食品安全培训网--考场搜索结果列表</TITLE>
		<base href="<%=basePath%>">

		<META http-equiv=X-UA-Compatible content=IE=EmulateIE7>
		<META content=name=keywords>
		<META content=name=description>
		<LINK href="wsj_phone/elfrontimages/menu.css" rel=stylesheet type="text/css">
		<LINK href="wsj_phone/elfrontimages/index.css" type=text/css rel=stylesheet> 
		<STYLE type=text/css>
BODY {
	FONT-SIZE: 12px
}

UL {
	LIST-STYLE-TYPE: none
}
</STYLE>
		<LINK href="wsj_phone/elfrontimages/book_index.css" type=text/css rel=stylesheet>
		<LINK href="wsj_phone/elfrontimages/nav_style_0903.css" type=text/css
			rel=stylesheet>
		<style type="text/css">
<!--
.STYLE2 {
	font-size: 20px;
	color: #a00201;
	font-weight: bold;
}

.STYLE3 {
	color: #0000FF
}

.STYLE4 {
	color: #DFDFDF
}

.STYLE5 {
	font-size: 14px;
	font-weight: bold;
}

.STYLE6 {
	font-size: 14px;
	font-weight: bold;
	color: #0000FF;
}

.STYLE7 {
	font-size: 12px
}
-->
.menu_bg {
	WIDTH:320px;
	HEIGHT: 40px;
	background-color:#F3F3F3;
	background-repeat: repeat-x;
	background-position: left -100px;
}
.menu_bg LI {
	TEXT-ALIGN: center; WIDTH: 100px; BACKGROUND-REPEAT: no-repeat; BACKGROUND-POSITION: left -5px; FLOAT: left; HEIGHT: 40px
}
.menu_bg LI A {
	LINE-HEIGHT: 40px; DISPLAY: block; HEIGHT: 40px; COLOR: #000; FONT-SIZE: 14px; FONT-WEIGHT: normal; TEXT-DECORATION: none
}
.menu_bg LI A:link {
	COLOR: #000;
}
.menu_bg LI A:visited {
	COLOR: #000;
}
.menu_bg LI A.here {
	COLOR: #000;
	background-image: url(http://www.ccunc.com/images/xtb/menu_hover.gif);
	background-repeat: no-repeat;
	background-position: -5px;
}
.menu_bg LI A:hover {
	COLOR: #fff;
}
.menu_bg LI A.libg {
	BACKGROUND: url(http://www.ccunc.com/images/xtb/libg.gif) no-repeat; COLOR: #fff
}
li{ list-style:none;}
</style>
<SCRIPT type="text/javascript" src="js/jquery/jquery-1.7.2.min.js" ></script>
<script type="text/javascript">

	$(	
	
		function a(){
				$("#ms").load(
					"getShoppingCarCount.action",
					ax
				);
			}
			
			
			/*var height=100/one;
			alert(height);
			var one=${courseComment.one}
			alert(one);
			var oneheight = height*one;
			$("#onestart").style.height=oneheight+"px";
			*/
			);

var ax={"statusId":'1'}
	
</script>
		<SCRIPT type="text/javascript">
	var imgs = new Array(); 
	
	function addImgs(obj,url){
		imgs[imgs.length]=obj; 
	}
	function setImgs(){
		for(var i=0;i<imgs.length;i++){
			if(imgs[i].fileSize<=0){
			 imgs[i].src="elfrontimages/coursedimg.jpg";
			}
		}
	}
	var ax={"statusId":'1'}
	
			
</SCRIPT>
	</HEAD>
	<BODY onLoad="setImgs();"><%@include file="frontheader.jsp"%>
		<table width="100%" border="0" align="left" cellpadding="0"
			cellspacing="0">
            <tr><td>
            <table width="100%" height="51" border="0" align="left" cellpadding="0" cellspacing="0">
  <tr>
    <td class="STYLE10" style="background-color:#00A2FC;"> 　　<span class="STYLE11">位置导航：考场导航中心</span></td>
  </tr>
</table>
            </td></tr>
            
          <tr>
            <td width="730" valign="top"><table style="margin-top:8px;" cellspacing="0" cellpadding="0" width="100%" 
        border="0">
                <tbody>
                  <tr>
                    <td background="images/knowledge/zhao_25.gif"></td>
                  </tr>
                  <tr>
                    <td height="200" align="left" valign="top" style="PADDING: 8px; line-height:25px;"><!--<wysLib:page></wysLib:page>-->
                     <s:if test="zxCourses.size==0">
										<br>
										没有考场<br>
										<br>
									</s:if>
									<s:iterator value="elclasses" status="zxcSt">
										<table width="100%" border="0" align="center" cellpadding="0"
											cellspacing="0" class="dibaikuang">
											<tr>
												<td height="35" valign="bottom" class="heicu14">
													<table width="320" height="30" border="0" cellpadding="0"
														cellspacing="0">
														<tr>
															<td class="STYLE5">  
																<!-- <a
																	href="class_view2.action?elclass.id=<s:property value="id"/>&cltype.id=<s:property value="cltype.id"/>"><s:property
																		value="name" />
																</a>
																 -->
																<a
																	href="newexamroom_view.action?examRoom.id=<s:property value="id"/>&eroomLib.id=<s:property value="eroomLib.id"/>"><s:property
																		value="title" />
																</a>
																
																<s:if test="elRegistration.PlanRecruitStudents != elRegistration.joinNumber ||
																elRegistration.PlanRecruitStudents > elRegistration.joinNumber">
																	<s:if test="isPastDue == 2">
																		<span style="color: red">报名时间已过</span>
																	</s:if>
																	<s:elseif test="isPastDue == 0">
																		<span style="color: red">报名时间未到</span>
																	</s:elseif>
																	<s:elseif test="isPastDue == 1">
																		<s:if test="isjoin == 'true'">
																			<span style="color: red;">已申请加入</span>
																		</s:if>
																		<s:if test="isjoin == 'false'">
																			<span style="color:red">&nbsp;&nbsp;&nbsp;&nbsp;火爆报名中</span>
																		</s:if> 
																	</s:elseif> 
															  </s:if><s:else> 
																			<span style="color: red;"></span>
																		</s:else>  
															</td>
															<td align="center">
																<a href="#" class="STYLE7"></a><a href="#"
																	class="STYLE7"></a>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td height="95" valign="bottom">
													<table width="320" border="0" cellspacing="0"
														cellpadding="0">
														<tr>
															
															<td height="85" valign="top">
																简介：
																<s:property value="description" />
																<br>
																<span class="h30">报名时间段：<s:property
																		value="creater.realname" /> <s:date name="begintime"
																		format="yyyy-MM-dd HH:mm:ss" /> ~<s:date name="endtime"
																		format="yyyy-MM-dd HH:mm:ss" /></span>
																<br/>
																<br>
																
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</s:iterator>
									<form action="forum_getAllRoom.action" method="post"
										name="ddd">
										<s:hidden name="pN" id="pageNow"></s:hidden>
										<s:hidden name="pS"></s:hidden>
										<s:hidden name="elclass.name"></s:hidden>
										<s:hidden name="isCorrespond"></s:hidden>
									</form>
									<script type="text/javascript">
						function page(i){
							document.getElementById("pageNow").value=i;
							ddd.submit();
						}
					</script>
									<wysLib:page></wysLib:page></td>
                  </tr>
                  <tr>
                  	<td><s:include value="../../elfrontman/frontbottom.jsp" /></td>
                  </tr>
                </tbody>
            </table>
        </table>
		<form action="submitAppalyClass.action" name="SQ" method="post">
    <s:hidden name="elclass.id" id="elclass.id" />
			<s:hidden name="Return" />
			<script type="text/javascript">
				function applyClass(is,id,Return){ 
							if(is == 1){  
								document.getElementById("elclass.id").value=id;
								document.getElementById("Return").value=Return;
								SQ.action="submitAppalyClass.action"; 
								alert('申请已提交，请等待审核结果！'); 
								SQ.submit();   
							} 
							if(is == 2){ 
								alert('您不符合考场的申请要求,请进详情页查看申请要求！'); 
								return false;
							}  
				}
			</script>
	</form>

	
	</body>
</HTML>
