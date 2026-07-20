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
	<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />

		<META http-equiv=Content-Type content="text/html; charset=utf-8">
		<TITLE>中国食品安全培训网--培训班查看</TITLE>
		<base href="<%=basePath%>">

		
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
	
					
					<table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
						<tbody>
							<tr>
								<td height="200" align="left" valign="top" style="PADDING: 8px; line-height: 25px;"><table width="100%" border="0" cellspacing="0" cellpadding="0">
								  <tr>
    <td><TABLE width="100%" border=0 align="left" cellPadding=0 cellSpacing=0>
      <TBODY>
        <TR>
          <TD height=40 align=left vAlign=center class="bline4" style="padding-left: 25px;background-color:#00A2FC;"> 培训班简介 </TD>
        </TR>
      </TBODY>
    </TABLE></td>
  </tr>
  <tr>
    <td><table width="320" border="0" align="left" cellpadding="2" style="margin-top:5px;">
      <tr>
       
        <td> 培训班类别：
          <s:property value="elclass.cltype.name" /></td>
      </tr>
      <tr>
     
        <td> 证 书 名&nbsp;&nbsp;称：
          <s:property value="elclass.certificatename" /></td>
      </tr>

      <tr>
        
        <td><span style="color: red"> 培训费：
          <s:property
																	value="elclass.price.elclassnowPrice" />
          &nbsp;&nbsp;元</span></td>
      </tr>
      <tr>
        <td><s:if test="myclass == 1">已报名</s:if>
		<s:else><a
																				href="shopping_addandto.action?commodity.commodityid=<s:property value="elclass.id"/>&commodity.commoditytype=2"><img
																					src="images/shopping/pic_29.gif" width="112"
																					height="36" id="tocart"> </a></s:else>
		</td>
      </tr>
    </table></td>
  </tr>
</table>

                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<!--<td width="330" align="left" valign="top">
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
											</td>-->
											<td width="320" align="right" valign="top" style="padding: 4px;"></tr>
								  </table>
									<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
										<TBODY>
											<TR>
												<TD height=35 align=left vAlign=center class="bline4"
													style="padding-left: 25px;background-color:#00A2FC;">
													培训班内容简介
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
													style="padding-left: 25px;background-color:#00A2FC;">
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
															<th height="30" align="left" bgcolor="#F7F7F7" style="padding-left:8px;">
																课程名称															</th>
														</tr>
														<s:iterator value="bxCourses">
															<tr>
																<td height="30" align="left" bgcolor="#FFFFFF" style="padding-left:8px;">
																	<s:property value="name" />																</td>
														  </tr>
														</s:iterator>
													</table>
											    <br>
													
													<table width="100%" border="0">
														<tr>
															<td height="25" bgcolor="#DEF0FC"
																style="font-size: 13px; font-weight: bold; color: blue;">
																选修课
															</td>
														</tr>
													</table>
													<table width="100%" border="0" cellpadding="3"
														cellspacing="2" bgcolor="#DEF0FC">
														<tr>
															<td align="center" bgcolor="#FFFFFF">
																课程名称															</td>
														</tr>
														<s:iterator value="xxCourses">
															<tr>
																<td height="20" align="center" bgcolor="#FFFFFF">
																	<s:property value="name" />																</td>
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

	</body>
</HTML>
