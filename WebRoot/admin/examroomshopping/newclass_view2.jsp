<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.classman.entities.ElClType"%>
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
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>考场报名详情页</TITLE>
		<base href="<%=basePath%>">

		<LINK href="elfrontimages/index.css" rel=stylesheet type="text/css">
		<link href="elfrontimages/menu.css" rel=stylesheet type="text/css" />
		<link href="images/dtree.css" type="text/css" rel="stylesheet" />
		<LINK rel=stylesheet type=text/css href="images/shopping/index.css">
		<LINK rel=stylesheet type=text/css href="images/shopping/menu.css">
		<LINK rel=stylesheet type=text/css
			href="images/shopping/book_index.css">
		<LINK rel=stylesheet type=text/css
			href="images/shopping/nav_style_0903.css">


		<script src="images/dtree.js" type="text/javascript"></script>

		<style type="text/css">
.font01 {
	FONT-SIZE: 13px;
	color: #DFEAEA
}

.picback {
	color: #387194;
	font-size: 18px;
	font-weight: bold;
	padding-left: 30px;
	background-image: url(images2/pic_01.gif);
	background-repeat: no-repeat;
	background-position: left top;
}

.hotback {
	background-image: url(images2/hot.gif);
	background-repeat: no-repeat;
	background-position: right top;
}

.kc_content {
	BORDER-BOTTOM: #cfdbe2 1px solid;
	BORDER-LEFT: #cfdbe2 1px solid;
	MARGIN-BOTTOM: 11px;
	OVERFLOW: hidden;
	BORDER-TOP: #4789ab 3px solid;
	BORDER-RIGHT: #cfdbe2 1px solid
}

.kc_content2 {
	BORDER-BOTTOM: #cfdbe2 1px solid;
	BORDER-LEFT: #cfdbe2 1px solid;
	MARGIN-BOTTOM: 11px;
	OVERFLOW: hidden;
	BORDER-TOP: #4789ab 0px solid;
	BORDER-RIGHT: #cfdbe2 1px solid
}

.kc_content3 {
	BORDER-BOTTOM: #cfdbe2 1px solid;
	BORDER-LEFT: #cfdbe2 1px solid;
	MARGIN-BOTTOM: 0px;
	OVERFLOW: hidden;
	BORDER-TOP: #4789ab 1px solid;
	BORDER-RIGHT: #cfdbe2 1px solid
}

.STYLE10 {
	color: #006699;
	font-weight: bold;
	font-size: 18px;
}

.STYLE11 {
	font-size: 16px
}

.STYLE12 {
	font-size: 14px
}

.STYLE13 {
	color: #0099CC
}

STYLE type     =text    /css>BODY {
	FONT-SIZE: 12px
}

UL {
	LIST-STYLE-TYPE: none
}

.bline {
	FONT-SIZE: 10pt;
	BORDER-BOTTOM: #ccc 1px dashed
}

.bline2 {
	FONT-WEIGHT: bold;
	FONT-SIZE: 15pt;
	COLOR: #ff6600;
	background-image: url(elfrontimages/botpic.gif);
	background-repeat: no-repeat;
	background-position: center bottom;
}

.STYLE6 {
	font-size: 14px;
	font-weight: bold;
	color: #0000FF;
}

.bline3 {
	padding: 8px;
	font-size: 12px;
	background-image: url(elfrontimages/botpic.gif);
	background-repeat: no-repeat;
	background-position: center bottom;
}

.bline4 {
	FONT-WEIGHT: bold;
	FONT-SIZE: 13pt;
	COLOR: #ff6600;
	background-image: url(elfrontimages/botpic.gif);
	background-repeat: no-repeat;
	background-position: center bottom;
}

.STYLE8 {
	FONT-WEIGHT: bold;
	FONT-SIZE: 14px;
	COLOR: #0000ff
}

.STYLE5 {
	font-size: 14px;
	font-weight: bold;
}

.STYLE7 {
	font-size: 12px
}
</style>
		<SCRIPT type="text/javascript" src="js/jquery/jquery-1.7.2.min.js"></script>
		<script type="text/javascript">
$(document).ready(function(){  
    $('#send_ajax').click(function(){ 
    if(document.getElementById("eruvalid").value==2){
					 	alert('已过期'); 
					 	return ;
					 } 
    
     if(document.getElementById("myclass").value==1){
					 	alert('您已拥有该培训班不需要再次报名'); 
					 	return ;
					 } 
     if(document.getElementById("myclassorder").value==1){
					 	alert('您已存在该培训班订单，不需要再次订购'); 
					 	return ;
					 }
		var aaa =$('#elclassid').attr("value");
        var params = {courseid:aaa,type:5}; 
                          // contactEmail:$('#contactEmail').attr('value'),
                          // subject:$('#subject').attr('value'),
                           //content:$('#content').attr('value')                
                     
       // $.ajax({  
        //    url:'json.action',  
        //    type:'post',  
        //   data:params,  
        //    success:function(data){
         //   if(data.result=="success"){
        //    	alert("shibai");
        //    }else{
        //    alert("shibai1");
        //    }
        //    }
           // failure:function(){alert("shibai");}
      //  });  
       $.post("executeaa.action",params, function (data) {	
       			a();	
            	alert("已加入购物车");
 			    });
      // jQuery.post('executeaa.action', params, update_page, 'json');
    });  

    }); 

	$(	
		function a(){
				$("#ms").load(
					"getShoppingCarCount.action",
					ax
				);
			}
			
			);

var ax={"statusId":'1'}

</script>
		<SCRIPT type="text/javascript">  

	$(
function b(){

var count='${courseComment.count}';
var one = '${courseComment.one}';
var two = ${courseComment.two};
var three = ${courseComment.three};
var four = ${courseComment.four};
var five = ${courseComment.five};
var height = 100/count;
var oneheight = 100-(height*one);
var twoeheight =100- (height*two);
var threeheight = 100-(height*three);
var fourheight = 100-(height*four);
var fiveheight = 100-(height*five);
document.getElementById("onestart").style.height=oneheight;
document.getElementById("twostart").style.height=twoeheight;
document.getElementById("threestart").style.height=threeheight;
document.getElementById("fourstart").style.height=fourheight;
document.getElementById("fivestart").style.height=fiveheight;
}
);
    function update_page(data){ 
     	     alert("44444444444444");
    	 //alert("+json.result+");
        //$('#result').html("<b>内容 "+json.result+"</b>");  
    }  
</SCRIPT>
		<SCRIPT type="text/javascript">
		
		
	var imgs = new Array();
	
	function addImgs(obj){
		imgs[imgs.length]=obj;
	}
	function setImgs(){
		for(var i=0;i<imgs.length;i++){
			if(imgs[i].fileSize<=0){
			 imgs[i].src="elfrontimages/coursedimg.jpg";
			}
		}
	} 
</SCRIPT>
	</HEAD>
	<BODY onLoad="setImgs();">
	<%@include file="frontheader.jsp"%>
		<table width=960 height=35 border=0 align=center cellPadding=0
			cellSpacing=0 background=images/shopping/gdbg2.gif
			style="margin-bottom: 5px;">
			<TBODY>
				<TR>
					<TD width="280" height=30 align="center">
						<span class="STYLE10">考场中心</span>
					</TD>
					<td align="center">
						<img src="images/shopping/gwc_ico.gif" width="25" height="25" />
					</td>
					<TD width="400">
						您已经选择
						<span class="h30" id="ms"></span> 门课程
						<a href="getShoppingCart.action"><span class="h30">查看详情&gt;&gt;</span>
						</a>
					</TD>
				</TR>
			</TBODY>
		</table>
		<table width="960px" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td width="960" valign="top">
			    <table style="margin-top: 8px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
							<tr>
								<td width="662" height="1" align="left">&nbsp;</td>
				  </tr>
							<td height="200" align="left" valign="top"
									style="PADDING: 8px; line-height: 25px;">

								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="330" align="left" valign="top">
											<s:if test="elclass.mainimg != null">
												<img src="<s:property value="elclass.mainimg_"/>"
													width="320" height="240" />
										    </s:if>
											<s:else>
												<img
														src="<s:property  escape="false" value="elclass.mainimg_"/>"
													id="cimg_0" width="320" height="240" />
												<SCRIPT type="text/javascript">
													obj = document.getElementById("cimg_0");
													addImgs(obj);
												</SCRIPT>
										    </s:else>
									    </td>
										<td valign="top" style="padding: 8px;">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center" class="bline4">考场简介</td>
  </tr>
</table>

											<table width="100%" border="0">
												<tr>
													<td>
														考试地点：
														<s:property value="examRoomPeice.examRoom.location" />
												    </td>
													<td width="50%">
														考场类别：
														<s:property value="examRoomPeice.examRoom.eroomLib.name" />
												    </td>
											    </tr>

												<tr>
													<td colspan="2">
														报名时间：
														<s:date name="examRoomPeice.examRoom.begintime"
																format="yyyy-MM-dd" />
														~
														<s:date name="examRoomPeice.examRoom.endtime"
																format="yyyy-MM-dd" />
												    </td>
											    </tr>
												<tr>
													<td colspan="2">
														考试时间：
														<s:date name="examRoomPeice.examRoom.begintime"
																format="yyyy-MM-dd" />
														~
														<s:date name="examRoomPeice.examRoom.begintime"
																format="yyyy-MM-dd" />
												    </td>
											    </tr>
												<tr>
													<td colspan="2">
														<s:if test="elclass.isPast == 2">
															<span style="color: red">已过报名期</span>
													    </s:if>
														<s:elseif test="elclass.isPast == 1">
															<span style="color: red">可以报名</span>
													    </s:elseif>
												    </td>
											    </tr>

												<tr>

													<td>
														<table width="100%" border="0" cellspacing="0"
																cellpadding="0" style="margin-top: 8px;">
															<tr>
																<td>&nbsp;
																	
															    </td>
																<s:if test="mybuyroom == 0">
																	<td width="160">
																		<!--<img src="images/shopping/jr_buy.gif" width="99"
																				height="32" id="send_ajax">-->
																    </td>
																	<td width="140" align="right">
																		<a
																				href="shopping_addandto.action?commodity.commodityid=<s:property value="examRoomPeice.examRoom.id"/>&commodity.commoditytype=2"><img
																					src="images/shopping/pic_29.gif" width="112"
																					height="36" id="tocart">
																		</a>
																    </td>
															    </s:if>
																<s:else>
																	<td colspan=2>已报名</td>
															    </s:else>
														    </tr>
													    </table>
												    </td>
											    </tr>
										    </table>
								    </tr>
							    </table>
								<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
									<TBODY>
										<TR>
											<TD height=35 align=left vAlign=center class="bline4"
													style="padding-left: 25px;">
												考场简介
											</TD>
									    </TR>
								    </TBODY>
							    </TABLE>
								<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
									<TBODY>
										<TR>
											<TD height=35 align=left vAlign=center class="bline3"
													style="line-height: 25px;">
												<p style="line-height: 25px; padding: 8px;">
													<s:property value="examRoomPeice.examRoom.description" />
											    </p>
										    </TD>
									    </TR>
								    </TBODY>
							    </TABLE>

								<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
									<TBODY>
										<TR>
											<TD height=35 align=left vAlign=center class="bline4"
													style="padding-left: 25px;">
												申请条件 
												(
												招收人数：
													<s:property value="examRoomPeice.examRoom.erRegistration.PlanRecruitStudents"/>&nbsp;&nbsp;&nbsp;&nbsp;
												已报人数：	
													<s:property value="examRoomPeice.examRoom.erRegistration.applyNumber"/>
												参加人数：	
													<s:property value="examRoomPeice.examRoom.erRegistration.joinNumber"/>
												)
											</TD>
									    </TR>
								    </TBODY>
							    </TABLE>
								<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
									<TBODY>
								  <td height="20"></td>
											<TD height=35 align=left vAlign=center
													style="line-height: 25px; padding: 8px;">
												<table width="100%" border="0" cellpadding="3"
														cellspacing="2" bgcolor="#DEF0FC">
													<tr>
														<td width="100" bgcolor="#ECF6FD">
															<s:if test="jingzhongIspass==1">
																<font color="red"> 工 种</font>
														    </s:if>
															<s:else>　工 种</s:else>
													    </td>
														<td width="200" bgcolor="#FFFFFF">
															<s:if test="examRoomPeice.examRoom.erRegistration.jingzhongName == ''">不限</s:if>
															<s:else>
																<s:if test="jingzhongIspass==1">
																	<font color="red"><s:property value="examRoomPeice.examRoom.erRegistration.jingzhongName" /></font>
															    </s:if>
																<s:else><s:property value="examRoomPeice.examRoom.erRegistration.jingzhongName" /></s:else>
														    </s:else>
													    </td>
														<td width="80" bgcolor="#ECF6FD">
															<font <s:if test="dishiIspass">color="red"</s:if>>
																地 市</font>
													    </td>
														<td bgcolor="#FFFFFF">
															&nbsp;
															<s:if test="examRoomPeice.examRoom.erRegistration.dishi== null">不限</s:if>
															<s:else>
																<font <s:if test="dishiIspass">color="red"</s:if>>
																	<s:property value="examRoomPeice.examRoom.erRegistration.dishiName" />
																</font>
														    </s:else>
													    </td>
												    </tr>
													<tr>
														<td width="100" bgcolor="#ECF6FD">
															<font <s:if test="zhijiIspass">color="red"</s:if>>
																职 级</font>
													    </td>
														<td width="200" bgcolor="#FFFFFF">
															&nbsp;
															<s:if test="examRoomPeice.examRoom.erRegistration.zhiji== null">不限</s:if>
															<s:else>
																<font <s:if test="zhijiIspass">color="red"</s:if>>
																	<s:property value="examRoomPeice.examRoom.erRegistration.zhijiName" />
																</font>
														    </s:else>
													    </td>
														<td width="80" bgcolor="#ECF6FD">
															<font <s:if test="zhiwuIspass">color="red"</s:if>>
																职 务</font>
													    </td>
														<td bgcolor="#FFFFFF">
															&nbsp;
															<s:if test="examRoomPeice.examRoom.erRegistration.zhiwu==null">不限</s:if>
															<s:else>
																<font <s:if test="zhiwuIspass">color="red"</s:if>>
																	<s:property value="examRoomPeice.examRoom.erRegistration.zhiwuName" />
																</font>
														    </s:else>
													    </td>
												    </tr>
													<tr>
														<%-- 
															<td width="100" bgcolor="#ECF6FD">
																岗 位
															</td>
															<td width="200" bgcolor="#FFFFFF">
																&nbsp;
																<s:if test="examRoom.erRegistration.gangwei== null">不限</s:if>
																<s:else>
																	<s:property value="examRoom.erRegistration.gangweiName" />
																</s:else>
															</td>
															 --%>
														<td width="80" bgcolor="#ECF6FD">
															<font <s:if test="ageIspass">color="red"</s:if>>
																年龄段</font>
													    </td>
														<td bgcolor="#FFFFFF">
															&nbsp;
															<s:if
																	test="examRoomPeice.examRoom.erRegistration.startAge== null && examRoomPeice.examRoom.erRegistration.stopAge== null">不限</s:if>
															<s:else>
																<s:if
																		test="examRoomPeice.examRoom.erRegistration.startAge== 0 && examRoomPeice.examRoom.erRegistration.stopAge== 0">不限</s:if>
																<s:else>
																	<font <s:if test="ageIspass">color="red"</s:if>>
																		<s:property value="examRoomPeice.examRoom.erRegistration.startAge" />
																		岁~ <s:property
																				value="examRoomPeice.examRoom.erRegistration.stopAge" /> 岁</font>
															    </s:else>
														    </s:else>
													    </td>
														<td width="80" bgcolor="#ECF6FD">
															<font <s:if test="sexIspass">color="red"</s:if>>
																性 别</font>
													    </td>
														<td bgcolor="#FFFFFF">
															&nbsp;
															<s:if test="examRoomPeice.examRoom.erRegistration.sex == null">不限</s:if>
															<s:else>
																<font <s:if test="sexIspass">color="red"</s:if>>
																	<s:property value="examRoomPeice.examRoom.erRegistration.sex" /> </font>
														    </s:else>
													    </td>
												    </tr>
													<tr>
														<td width="80" bgcolor="#ECF6FD">
															<font <s:if test="depIspass">color="red"</s:if>>
																部 门</font>
													    </td>
														<td colspan="3" bgcolor="#FFFFFF">
															&nbsp;
															<s:if test="examRoomPeice.examRoom.erRegistration.treeTypeName == ''">不限</s:if>
															<s:else>
																<font <s:if test="depIspass">color="red"</s:if>>
																	<s:property
																			value="examRoomPeice.examRoom.erRegistration.treeTypeName" /> </font>
														    </s:else>
													    </td>
												    </tr>
												  <%-- 	<tr>
															<td width="100" bgcolor="#ECF6FD">
																<font <s:if test="eroomIspass==1">color="red"</s:if>>
																	考 场</font>
															</td>
															<td colspan="3" bgcolor="#FFFFFF">
																<span style="color: blue"><b>【条件】</b>
																<br /> <s:property escape="false"
																		value="examRoomPeice.examRoom.erRegistration.erParasMsg" /> </span>
																<b>【我的情况】</b>
																<br />
																<font <s:if test="eroomIspass==1">color="red"</s:if>>
																	<s:property
																		value="examRoomPeice.examRoom.erRegistration.myerParasMsg"
																		escape="false" /> </font>
															</td>
														</tr>
														<tr>
															<td width="100" bgcolor="#ECF6FD">
																<font <s:if test="eroomepIspass==1">color="red"</s:if>>
																	考 场 试 卷</font>
															</td>
															<td colspan="3" bgcolor="#FFFFFF">
																<span style="color: blue"><b>【条件】</b>
																<br /> <s:property
																		value="examRoomPeice.examRoom.erRegistration.erepParasMsg"
																		escape="false" /> </span>
																<b>【我的情况】</b>
																<br />
																<font <s:if test="eroomepIspass==1">color="red"</s:if>>
																	<s:property
																		value="examRoomPeice.examRoom.erRegistration.myerepParasMsg"
																		escape="false" /> </font>
															</td>
														</tr>
														<tr>
															<td width="100" bgcolor="#ECF6FD">
																<font <s:if test="classIspass==1">color="red"</s:if>>
																	培训班</font>
															</td>
															<td colspan="3" bgcolor="#FFFFFF">
																<span style="color: blue"><b>【条件】</b>
																<br /> <s:property
																		value="examRoomPeice.examRoom.erRegistration.classParasMsg"
																		escape="false" /> </span>
																<b>【我的情况】</b>
																<br />
																<font <s:if test="classIspass==1">color="red"</s:if>>
																	<s:property
																		value="examRoomPeice.examRoom.erRegistration.myclassParasMsg"
																		escape="false" /> </font>
															</td>
															
															<td width="80" bgcolor="#ECF6FD">
																考 场:
															</td>
															<td colspan="3" bgcolor="#FFFFFF">
																&nbsp;
																<s:if test="examRoom.erRegistration.examroomName == ''">不限</s:if>
																<s:else> 
																	<span style="color:red">【<s:property value="examRoom.erRegistration.eroomScreeningWayName" />】</span>
																	<s:property value="examRoom.erRegistration.examroomName" />
																</s:else>
															</td>
															 --%>
													</tr>
											    </table>
										    </TD>
								  </TR>
									</TBODY>
							    </TABLE>
							  </td>
				  </table>
				</td>
			</tr>
		</table>
		

							<s:if test="courseComment.count!=0">
								<table width="960" border="0" align="center" cellpadding="0"
									cellspacing="0" class=kc_content2>
									<tr>
										<td>
											<wysLib:page6></wysLib:page6>
										</td>
									</tr>
								</table>
							</s:if>
							<a name="liuyan2"></a>
						
							<form action="newclass_view2.action" method="post" name="ddd"
								id="ddd">
								<s:hidden name="pN6" id="pageNow"></s:hidden>
								<s:hidden name="pS6"></s:hidden>
								<s:hidden name="examRoomPeice.examRoom.id" id="elclassid"></s:hidden>
								<s:hidden name="examRoomPeice.examRoom.uvalid" id="eruvalid"></s:hidden>
								<s:hidden name="myclass" id="myclass"></s:hidden>
								<s:hidden name="ctype"></s:hidden>
								<s:hidden name="examRoomPeice.examRoom.location" id="location"/>
								<s:hidden name="myclassorder" id="myclassorder"></s:hidden>
							</form>
							<form action="submitAppalyClass.action" name="SQ" method="post">
								<s:hidden name="examRoomPeice.examRoom.id" id="elclass.id" />
								<s:hidden name="Return" />

							</form>
							<script type="text/javascript">
						function checkform1(){
					 if(document.getElementById("myclass").value==0){
					 	alert('您未拥有该培训班不能评论'); 
					 	return false;
					 }
					
					 if(document.getElementById("userComment.content").value==""){
						 alert('评论内容为空！'); 
						return false;
					}
					var chack=${audit};
					if(chack==true){
						alert('您的评论已提交请等待审核！');
					}
					return true;
					
					}
					function page(i){
							document.getElementById("pageNow").value=i;
							ddd.submit();
					}
					</script>
					<s:include value="frontbottom.jsp" />
	</BODY>
</HTML>
