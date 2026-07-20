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
<%
	String cltypeId = "";
	if (request.getAttribute("cltype") != null) {
		cltypeId = ((ElClType) request.getAttribute("cltype")).getId()
				+ "";
	} else {
		cltypeId = "1";
	}
%>
<HTML>
	<HEAD>
		<META http-equiv=Content-Type content="text/html; charset=utf-8">
		<TITLE>五矿发展员工职业发展系统--培训班查看</TITLE>
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

STYLE type       =text      /css>BODY {
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
    if(document.getElementById("elclass.isPastDue").value==2){
					 	alert('已过期'); 
					 	return ;
					 } 
    
     if(document.getElementById("myclass").value==1){
					 	alert('您已拥有该培训班不需要再次购买'); 
					 	return ;
					 } 
     if(document.getElementById("myclassorder").value==1){
					 	alert('您已存在该培训班订单，不需要再次订购'); 
					 	return ;
					 }
		var aaa =$('#elclassid').attr("value");
        var params = {courseid:aaa,type:2}; //type为2是指培训班
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
function  liuyan1(){ 

		location.hash="liuyan2";
	}
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
		<!--<table width=960 height=35 border=0 align=center cellPadding=0
			cellSpacing=0 background=images/shopping/gdbg2.gif
			style="margin-bottom: 5px;">
			<TBODY>
				<TR>
				  <TD width="120" height=30 align="center">
						<span class="STYLE10">选班中心</span></TD>
					<TD style="PADDING-LEFT: 50px">
						<input name=search_content id=search_content
							style="MARGIN-RIGHT: 20px" value="课程名称" />
						<SELECT style="MARGIN-RIGHT: 20px" id=search_type name=search_type>
							<OPTION selected value=kc>
								-请选择培训班类别-
							</OPTION>
							<OPTION value=zs>
								-培训班类别一-
							</OPTION>
							<OPTION value=zl>
								-培训班类别二-
							</OPTION>
							<OPTION value=tz>
								-培训班类别三-
							</OPTION>
						</SELECT>
						<input type="submit" name="Submit" value="搜 索" />
					</TD>
					<td width="50">
						<img src="images/shopping/gwc_ico.gif" width="25" height="25" />
					</td>
					<TD width="300">
						您当前已选
						<span class="h30" id="ms"></span> 门课程
						<a href="getShoppingCart.action"><span class="h30">查看详情&gt;&gt;</span>
						</a>
					</TD>	
				</TR>
			</TBODY>
		</table>-->
		<table width="960px" border="0" align="center" cellpadding="0"
			cellspacing="0">
	  <tr>
				<td width="960" valign="top">
					<table style="margin-top: 8px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
						<tbody>
							<tr>
								<td width="11"></td>
								
							
							</tr>
							<tr>
				
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
											<td align="right" valign="top" style="padding: 4px;"><TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
											  <TBODY>
											    <TR>
											      <TD height=40 align=center vAlign=center class="bline4"
													style="padding-left: 25px;"> <s:property value="elclass.name" /> </TD>
										        </TR>
										      </TBODY>
											  </TABLE>
											  <table width="100%" border="0" cellpadding="2" style="margin-top:5px;">
											  <tr>
<!--														<td>-->
<!--															创&nbsp;&nbsp;建&nbsp;&nbsp;者：-->
<!--															<s:property value="elclass.creater.realname" />-->
<!--														</td>-->
<!--														<td width="50%">-->
															<td>
															培训班类别：
															<s:property value="elclass.cltype.name" />
														</td>
													</tr>
													<tr>
														<td>
<!--															<s:property value="elclass.age" />-->
<!--															创建时间：-->
<!--															<s:date name="elclass.createtime" format="yyyy-MM-dd" />-->
<!--														</td>-->
<!--														<td width="50%">-->
															证 书 名&nbsp;&nbsp;称：
															<s:property value="elclass.certificatename" />
														</td>
													</tr>
<!--													<tr>-->
<!--														<td colspan="2">-->
<!--															培训班时间段：-->
<!--															<s:date name="elclass.starttime" format="yyyy-MM-dd" />-->
<!--															~-->
<!--															<s:date name="elclass.finishtime" format="yyyy-MM-dd" />-->
<!--														</td>-->
<!--                                                        <td>-->
<!--                                                        </td>-->
<!--                                                        <td bgcolor="#F7F7F7">-->
<!--                                                        </td>-->
<!--													</tr>-->
													<tr>
													  <td >


															<s:if test="elclass.isPast == 2">
																<span style="color: red">已过学习期</span>
															</s:if>
															<s:elseif test="elclass.isPast == 1">
																<span style="color: red">可以报名</span>
															</s:elseif>
														</td>
														

												  </tr>

													<tr>

														<td>
														  <span style="color: red"> 培训费：<s:property
																	value="elclass.price.elclassnowPrice" />&nbsp;&nbsp;元</span>
													  </td>
													</tr>
													<tr>

												  <s:if test="courseComment.count==0">
															<td>&nbsp;</td>
														</s:if>
														<s:else>
															<td>&nbsp;</td>
														</s:else>
														<td>
															<table width="100%" border="0" cellspacing="0"
																cellpadding="0" style="margin-top: 8px;">
																<tr>
																	<td>&nbsp;
																		
																  </td>
																	<s:if test="myclass == 1">
																		<td colspan=2>已报名</td>
																	</s:if>
																	<s:else>
																		<td width="160">
																			<!--<img src="images/shopping/jr_buy.gif" width="99"
																				height="32" id="send_ajax">-->
																		</td>
																		<td width="140">
																			<a
																				href="shopping_addandto.action?commodity.commodityid=<s:property value="elclass.id"/>&commodity.commoditytype=2"><img
																					src="images/shopping/pic_29.gif" width="112"
																					height="36" id="tocart"> </a>
																		</td>
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
													培训班简介
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
														<s:property value="elclass.description" />
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
													课程一览
												</TD>
											</TR>
										</TBODY>
									</TABLE>
									<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
										<TBODY>
											<TR>
												<TD height=35 align=left vAlign=center
													style="line-height: 25px; padding: 8px;">
												  <table width="100%" border="0">
														<tr>
															<td height=35 align=left vAlign=center class="bline4"
													style="padding-left: 25px;">
																必修课
															</td>
														</tr>
													</table>
													<table width="100%" border="0" cellpadding="3"
														cellspacing="1" bgcolor="#DEF0FC">
														<tr>
															<th height="30" align="center" bgcolor="#F7F7F7">
																课程名称
															</th>
															<th width="80" align="center" bgcolor="#F7F7F7">
																讲师
															</th>
															<th width="70" align="center" bgcolor="#F7F7F7">
																课程时长
															</th>
															<th width="120" align="center" bgcolor="#F7F7F7">
																课程类别
															</th>
														</tr>
														<s:iterator value="bxCourses">
															<tr>
																<td height="30" align="center" bgcolor="#FFFFFF">
																	<s:property value="name" />
																</td>
																<td height="30" align="center" bgcolor="#FFFFFF">
																	<s:property value="teacherName" />
															  </td>
																<td height="30" align="center" bgcolor="#FFFFFF">
																	<s:property value="during" />
															  </td>
																<td height="30" align="center" bgcolor="#FFFFFF">
																	<s:property value="ctype.name" />
															  </td>
														  </tr>
														</s:iterator>
													</table>
													<table width="100%" border="0">
														<tr>
															<td height=35 align=left vAlign=center class="bline4"
													style="padding-left: 25px;">
																选修课
															</td>
														</tr>
													</table>
													<table width="100%" border="0" cellpadding="3"
														cellspacing="2" bgcolor="#DEF0FC">
														<tr>
															<td align="center" bgcolor="#FFFFFF">
																课程名称
															</td>
															<td width="80" align="center" bgcolor="#FFFFFF">
																讲师
															</td>
															<td width="70" align="center" bgcolor="#FFFFFF">
																课程时长
															</td>
															<td width="120" align="center" bgcolor="#FFFFFF">
																课程类别
															</td>
														</tr>
														<s:iterator value="xxCourses">
															<tr>
																<td height="20" align="center" bgcolor="#FFFFFF">
																	<s:property value="name" />
																</td>
																<td align="center" bgcolor="#FFFFFF">
																	<s:property value="teacherName" />
																</td>
																<td align="center" bgcolor="#FFFFFF">
																	<s:property value="during" />
																</td>
																<td align="center" bgcolor="#FFFFFF">
																	<s:property value="ctype.name" />
																</td>
															</tr>
														</s:iterator>
													</table>
												</TD>
											</TR>
										</TBODY>
									</TABLE>
								</td>
								
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
	</table>
		<!-- 
		<table width="960" height="200" border="0" cellpadding="0" border="0"
			align="center" cellpadding="0" cellspacing="0" class=kc_content3>
			<tr>
				<td height="50" width="150" rowspan="3" class="STYLE10"
					align="center">
					平均得分
					<br>
					<s:if test="courseComment.count==0">
						<span class="h30">暂无评价</span>
					</s:if>
					<s:else>
						<s:property value="courseComment.avg" /> 分 /满分5分 
        			</s:else>
				</td>
				<td width="99" height="51">
					共有
					<s:property value="courseComment.count" />
					条评价
				</td>
				<td width="40">
					&nbsp;
				</td>
				<td width="99">
					&nbsp;
				</td>
				<td width="40">
					&nbsp;
				</td>
				<td width="98">
					&nbsp;
				</td>
				<td width="40">
					&nbsp;
				</td>
				<td width="99">
					&nbsp;
				</td>
				<td width="40">
					&nbsp;
				</td>
				<td width="99">
					&nbsp;
				</td>
				<td width="150">
					&nbsp;
				</td>
			</tr>
			<tr>
				<td height="100" valign="top" bgcolor="#66CCCC">
					<div id="onestart"
						style="background-color: #CCCCCC; display: block; width: 100px; width: 100%; overflow: hidden; height: 0px; color: #009966;">
						${courseComment.one}人
					</div>
				</td>
				<td>
					&nbsp;
				</td>
				<td valign="top" bgcolor="#CC6666">
					<div id="twostart"
						style="background-color: #CCCCCC; display: block; width: 100px; width: 100%; overflow: hidden; height: 0px; color: #009966;">
						${courseComment.two}人
					</div>
				</td>
				<td>
					&nbsp;
				</td>
				<td valign="top" bgcolor="#CC3399">
					<div id="threestart"
						style="background-color: #CCCCCC; display: block; width: 100px; width: 100%; overflow: hidden; height: 0px; color: #009966;">
						${courseComment.three}人
					</div>
				</td>
				<td>
					&nbsp;
				</td>
				<td valign="top" bgcolor="#FF9933">
					<div id="fourstart"
						style="background-color: #CCCCCC; display: block; width: 100px; width: 100%; overflow: hidden; height: 0px; color: #009966;">
						${courseComment.four}人
					</div>
				</td>
				<td>
					&nbsp;
				</td>
				<td valign="top" bgcolor="#FFFF00">
					<div id="fivestart"
						style="background-color: #CCCCCC; display: block; width: 100px; width: 100%; overflow: hidden; height: 0px; color: #009966;">
						${courseComment.five}人
					</div>
				</td>
				<td>
					<img src="images/shopping/fabupingjia.jpg" id="liuyan1"
						onclick="liuyan1()" />
				</td>
			</tr>
			<tr>
				<td height="50">
					<img style="margin-top: 5px;" src="images/shopping/xx_pic_01.gif"
						width="99" height="15">
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					<img style="margin-top: 5px;" src="images/shopping/xx_pic_02.gif"
						width="99" height="15">
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					<img style="margin-top: 5px;" src="images/shopping/xx_pic_03.gif"
						width="98" height="15">
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					<img style="margin-top: 5px;" src="images/shopping/xx_pic_04.gif"
						width="99" height="15">
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					<img style="margin-top: 5px;" src="images/shopping/xx_pic_05.gif"
						width="99" height="15">
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
		</table>
		<table width="960" height="39" border="0" align="center"
			cellpadding="0" cellspacing="0" class=kc_content3>
			<tr>
				<td background="images/shopping/pic_34.gif" class="STYLE10">
					学员评价
				</td>
			</tr>
		</table>
		 
		<table width="960" height="200" border="0" cellpadding="0"
			cellspacing="0" class="tabrlb" align="center">

			<tr>
				<td valign="top">
					<br />
				</td>
			</tr>


			<s:if test="listcc.size==0">
				<tr>
					<td align="center">
						<table class=kc_content3 width="100%" align="center">
							<tr>
								<td align="center">
									当前还没有评论
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</s:if>
			<s:else>
				<tr>
					<td>
						<s:iterator value="listcc">
							<table width="960" border="0" align="center" cellpadding="0"
								cellspacing="0" class=kc_content2>
								<tr>
									<td height="200" valign="top">
										<table class="dibaikuang2" width="900" border="0"
											align="center" cellpadding="0" cellspacing="0">
											<tr>
												<td width="200" height="175" align="center">
													<p>
														<s:if test="user.touxiang!= null">
															<img src="<s:property value="user.mainimg_"/>"
																width="160" height="200">
														</s:if>
														<s:else>
															<img
																src="<s:property  escape="false" value="user.mainimg_"/>"
																id="cimg_0" width="160" height="200" />
															<SCRIPT type="text/javascript">
															obj = document.getElementById("cimg_0");
															addImgs(obj);
														</SCRIPT>
														</s:else>
													</p>
													<p>
														用户名：
													</p>
													<s:property value="user.realname" />
												</td>
												<td valign="top">
													<table width="100%" border="0" cellspacing="0"
														cellpadding="0">
														<tr>
															<td>
																<table width="100%" border="0" cellspacing="0"
																	cellpadding="0" class="dibaikuang2">
																	<tr>
																		<td height="30" valign="middle">
																			<s:if test="commentpoint==1">
																				<img style="margin-top: 5px;"
																					src="images/shopping/xx_pic_01.gif" width="100"
																					height="15">
																			</s:if>
																			<s:if test="commentpoint==2">
																				<img style="margin-top: 5px;"
																					src="images/shopping/xx_pic_02.gif" width="100"
																					height="15">
																			</s:if>
																			<s:if test="commentpoint==3">
																				<img style="margin-top: 5px;"
																					src="images/shopping/xx_pic_03.gif" width="100"
																					height="15">
																			</s:if>
																			<s:if test="commentpoint==4">
																				<img style="margin-top: 5px;"
																					src="images/shopping/xx_pic_04.gif" width="100"
																					height="15">
																			</s:if>
																			<s:if test="commentpoint==5">
																				<img style="margin-top: 5px;"
																					src="images/shopping/xx_pic_05.gif" width="100"
																					height="15">
																			</s:if>

																		</td>
																		<td width="200" align="right">
																			<s:date name="commentdate"
																				format="yyyy年MM月dd HH:mm:ss" />
																		</td>
																	</tr>
																</table>
															</td>
														</tr>

														<tr>
															<td style="padding: 10px;">
																<span class="h30">评价内容：</span>${content }
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
										</s:iterator>
									</td>
								</tr>
						</s:else>

					</table>
					-->
					<!-- 
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
							<table class=kc_content3 align="center" width="960">
								<tr>
									<td>
										<form action="saveClassComment.action" method="post"
											onsubmit="return checkform1()">
											<table width="98%" height="30" border="0" align="center"
												cellpadding="0" cellspacing="0" bordercolor="#0033FF">
												<tr>
													<td colspan="2" class="daohang" align="center">
														评论：
													</td>
												</tr>
												<tr>
													<td colspan="2" class="daohang" align="center">
														星级：
														<label>
															<input type="radio" name="userComment.commentpoint"
																id="account_search_elUser_valid21" value="1" />
															<label for="account_search_elUser_valid21">
																1分
															</label>
															<input type="radio" name="userComment.commentpoint"
																id="account_search_elUser_valid22" value="2" />
															<label for="account_search_elUser_valid22">
																2分
															</label>
															<input type="radio" name="userComment.commentpoint"
																id="account_search_elUser_valid20" value="3" />
															<label for="account_search_elUser_valid20">
																3分
															</label>
															<input type="radio" name="userComment.commentpoint"
																id="account_search_elUser_valid20" value="4" />
															<label for="account_search_elUser_valid24">
																4分
															</label>
															<input type="radio" name="userComment.commentpoint"
																id="account_search_elUser_valid20" value="5"
																checked="checked" />
															<label for="account_search_elUser_valid25">
																5分
															</label>
													</td>
												</tr>
												<tr>
													<td colspan="2" class="daohang" align="center">
														<textarea rows="5" cols="40" name="userComment.content"
															id="userComment.content"
															style="border: 2px; border-color: black;"></textarea>
													</td>
												</tr>
												<tr>
													<td colspan="2" class="daohang" align="center">

														<input type="hidden" name="pN"
															value="<s:property value="pN"/>">
														<input type="hidden" name="pS"
															value="<s:property value="pS"/>">
														<s:hidden name="elclass.id"></s:hidden>
														<s:hidden name="ctype"></s:hidden>
														<a name="liuyan">&nbsp;</a>
														<input type="submit" value="提交">
													</td>
												</tr>
											</table>
										</form>
									</td>
								</tr>
							</table>
							 -->
							<form action="newclass_view2.action" method="post" name="ddd"
								id="ddd">
								<s:hidden name="pN6" id="pageNow"></s:hidden>
								<s:hidden name="pS6"></s:hidden>
								<s:hidden name="elclass.id" id="elclassid"></s:hidden>
								<s:hidden name="elclass.isPast" id="elclass.isPastDue"></s:hidden>
								<s:hidden name="myclass" id="myclass"></s:hidden>
								<s:hidden name="ctype"></s:hidden>
								<s:hidden name="myclassorder" id="myclassorder"></s:hidden>
							</form>
							<form action="submitAppalyClass.action" name="SQ" method="post">
								<s:hidden name="elclass.id" id="elclass.id" />
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
