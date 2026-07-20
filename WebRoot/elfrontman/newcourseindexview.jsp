<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.courseman.entities.Course"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<% 
	String cltypeId = "";
	if (request.getAttribute("course") != null) { 
		cltypeId = ((Course) request.getAttribute("course"))
				.getCtype().getId()+ ""; 
	}else{
		cltypeId = "1";
	} 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" /> 
		<title>课程-<s:property value="course.name" /></title> 
		<base href="<%=basePath%>" />
		<LINK href="elfrontimages/index.css" rel=stylesheet type="text/css">
		<link href="elfrontimages/menu.css" rel=stylesheet type="text/css" />
		<link href="images/dtree.css" type="text/css" rel="stylesheet" />
		<LINK rel=stylesheet type=text/css 
href="images/shopping/index.css"><LINK rel=stylesheet type=text/css 
href="images/shopping/menu.css">
<LINK rel=stylesheet type=text/css href="images/shopping/book_index.css"><LINK 
rel=stylesheet type=text/css href="images/shopping/nav_style_0903.css">
		<script src="images/dtree.js" type="text/javascript"></script>
		<style type="text/css">
		.font01 {
	FONT-SIZE: 13px;color: #DFEAEA
}
.picback {
	color:#387194;
	font-size:18px;
	font-weight:bold;
	padding-left:30px;
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
	BORDER-BOTTOM: #cfdbe2 1px solid; BORDER-LEFT: #cfdbe2 1px solid; MARGIN-BOTTOM: 11px; OVERFLOW: hidden; BORDER-TOP: #4789ab 3px solid; BORDER-RIGHT: #cfdbe2 1px solid}
.kc_content2 {
	BORDER-BOTTOM: #cfdbe2 1px solid; BORDER-LEFT: #cfdbe2 1px solid; MARGIN-BOTTOM: 11px; OVERFLOW: hidden; BORDER-TOP: #4789ab 0px solid; BORDER-RIGHT: #cfdbe2 1px solid}
.kc_content3 {
	BORDER-BOTTOM: #cfdbe2 1px solid; BORDER-LEFT: #cfdbe2 1px solid; MARGIN-BOTTOM: 0px; OVERFLOW: hidden; BORDER-TOP: #4789ab 1px solid; BORDER-RIGHT: #cfdbe2 1px solid}
.STYLE10 {
	color: #006699;
	font-weight: bold;
	font-size: 18px;
}
.STYLE11 {font-size: 16px}
.STYLE12 {font-size: 14px}
.STYLE13 {color: #0099CC}
STYLE type    =text   /css>BODY {
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
</STYLE>
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
function  liuyan1(){ 
		location.hash="liuyan2";
	}

$(document).ready(function(){  
    $('#send_ajax').click(function(){ 
     if(document.getElementById("mycourse").value==1){
					 	alert('您已拥有该课程不需要再次报名'); 
					 	return ;
					 } 
     if(document.getElementById("mycourseorder").value==1){
					 	alert('您已存在该课程订单，不需要再次订购'); 
					 	return ;
					 }
		var aaa =$('#courseid').attr("value");
        var params = {courseid:aaa,type:1}; 
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
    		function  check(){
		var slt=document.getElementById("nametype");
			if(slt.value=='0'){
			alert("请选择一个类别");
			return false;
			}
			return true;
			
					
	
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
	<body onLoad="setImgs();">
		<%@include file="frontheader.jsp"%>
		<form action="bookinfocourseclass.action" method="post" name="111" onsubmit="return check()">
		<table width=960 height=35 border=0 align=center cellPadding=0 cellSpacing=0 background=images2/gdbg2.gif style="margin-bottom:5px;">
  <TBODY>
    <TR>
      <td width="120" height=30 align="center"><span class="STYLE10">搜索中心</span></td>
      <td style="PADDING-LEFT: 50px"><input onclick="this.value=''" name=name id=search_content style="MARGIN-RIGHT: 20px" value="填写名称...." />
          <select style="MARGIN-RIGHT: 20px" 
                        id="nametype" name="nametype" >
            <OPTION selected 
                          value=0>-请选择类别-</OPTION>
            <OPTION value=1>-课程-</OPTION>
            <OPTION value=2>-培训班-</OPTION>
            <OPTION 
                          value=3>-图书-</OPTION>
          </select>
          <input type="submit" name="Submit" value="搜 索" /></td>
      <td width="50"><img src="images/shopping/gwc_ico.gif" width="25" height="25"/></td>
      <td width="300">购物车内有 <span id="ms" class="h30"></span> 门商品 <a href="getShoppingCart.action"><span class="h30">查看购物车&gt;&gt;</span></a> </TD>
    </TR>
  </TBODY>
</table>
	</form>	
<table width="960" height="51" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td background="images/shopping/pic_17.gif" class="STYLE10"> 　　<span class="STYLE11" onclick="clickButton()"><s:property value="course.name" /></span></td>
  </tr>
</table>
<table width="960" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content2>
  <tr>
    <td width="50%" height="320" align="center"><s:if test="elclass.mainimg != null">
															<img src="<s:property value="course.mainimg_"/>" width="450" height="300"> 
													</s:if><s:else> 
														<img
															src="<s:property  escape="false" value="course.mainimg_"/>"
															id="cimg_0" width="450" height="300" />
														<SCRIPT type="text/javascript">
															obj = document.getElementById("cimg_0");
															addImgs(obj);
														</SCRIPT> 
													</s:else></td>
    <td valign="top"><p>&nbsp;</p>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="40" height="35" align="center"><img src="images/shopping/pic_35.gif" width="5" height="9"></td>
        <td>主讲教师：<s:property value="course.teacherName" /></td>
      </tr>
      <tr>
        <td height="35" colspan="2" align="center"><table width="90%" border="0" cellspacing="0" cellpadding="0" >
          <tr>
            <td align="left" bgcolor="#EEEEEE" class="STYLE10" style="padding:10px;">原价 <s:property value="course.price.courseoldPrice" /> 元　课程时长 <s:property value="course.during" /> 课时 <br>
现价 <s:property value="course.price.coursenowPrice" /> 元　已节省 <s:property value="course.price.chajia" /> 元 </td>
          </tr>
        </table></td>
        </tr>
      <tr>
      <!-- 
        <td width="40" height="35" align="center"><img src="images/shopping/pic_35.gif" width="5" height="9"></td>
        <td>招生有效期：<s:date name="course.coRegistration.RegistrationStartTime" format="yyyy-MM-dd" />&nbsp;至&nbsp;<s:date name="course.coRegistration.RegistrationStopTime" format="yyyy-MM-dd" /></td>
     	 -->
     	   <td width="40" height="35" align="center"><img src="images/shopping/pic_35.gif" width="5" height="9"></td>
        <td>课程类别：<s:property value="course.ctype.name"  /></td>
      </tr>
      <tr>
        <td width="40" height="35" align="center"><img src="images/shopping/pic_35.gif" width="5" height="9"></td>
        <td>课程格式：<s:property value="course.courseFormName" />  </td>
      </tr>
      <tr>
        <td width="40" height="35" align="center"><img src="images/shopping/pic_35.gif" width="5" height="9"></td>
        <td>已有评价 <s:property value="courseComment.count" /> 条 </td>
      </tr>
      <tr>
        <td width="40" height="35" align="center"><img src="images/shopping/pic_35.gif" width="5" height="9"></td>
        <s:if test="courseComment.count==0"><td>暂无评价 （满分5分） </td></s:if><s:else>
        <td>平均得分 <s:property value="courseComment.avg" /> 分 （满分5分） </td>
        </s:else>
      </tr>
    </table>
      
      <table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:8px;">
        <tr>
        	<td>&nbsp;</td>
        	<s:if test="mycourse == 0">
		          <td width="160"><img src="images/shopping/jr_buy.gif" width="99" height="32" id="send_ajax" ></td>
		          <td width="140"><a href="shopping_addandto.action?commodity.commodityid=<s:property value="course.id"/>&commodity.commoditytype=1" ><img src="images/shopping/pic_29.gif" width="112" height="36" id="tocart"></a></td>
	      	</s:if>
	      	<s:else>
	      		<td colspan=2>已报名</td>
	      	</s:else>
          
        </tr>
      </table>
    </td>
  </tr>
</table>
<table width="960" height="39" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content3>
  <tr>
    <td background="images/shopping/pic_34.gif" class="STYLE10"> 　　课程简介 </td>
  </tr>
</table>
<table width="960" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content2 align="center">
  <tr>
    <td height="200">&nbsp;${course.description}</td>
  </tr>
</table>
<table width="960" height="200" border="0" cellpadding="0" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content3 >
  	<tr>
    <td height="50" width="150" rowspan="3" class="STYLE10" align="center"> 平均得分<br><s:if test="courseComment.count==0"> 暂无评价 </s:if><s:else>
         <s:property value="courseComment.avg" /> 分 /满分5分 
        </s:else></td>
    <td width="99" height="51">共有<s:property value="courseComment.count" />条评价</td>
    <td width="40">&nbsp;</td>
    <td width="99">&nbsp;</td>
    <td width="40">&nbsp;</td>
    <td width="98">&nbsp;</td>
    <td width="40">&nbsp;</td>
    <td width="99">&nbsp;</td>
    <td width="40">&nbsp;</td>
    <td width="99">&nbsp;</td>
    <td width="150">&nbsp;</td>
  </tr>
  <tr>
    <td height="100" valign="top" bgcolor="#66CCCC"><div  id="onestart" style="background-color:#CCCCCC;display:block;width:100px;width:100%;overflow:hidden;height: 0px;color: #009966;">
    ${courseComment.one}人
    </div></td>
    <td>&nbsp;</td>
    <td valign="top" bgcolor="#CC6666"><div id="twostart" style="background-color:#CCCCCC;display:block;width:100px;width:100%;overflow:hidden;height:0px;color: #009966;">
${courseComment.two}人
    </div></td>
    <td>&nbsp;</td>
    <td valign="top" bgcolor="#CC3399"><div id="threestart" style="background-color:#CCCCCC;display:block;width:100px;width:100%;overflow:hidden;height:0px;color: #009966;">
${courseComment.three}人</div></td>
    <td>&nbsp;</td>
    <td valign="top" bgcolor="#FF9933"><div id="fourstart" style="background-color:#CCCCCC;display:block;width:100px;width:100%;overflow:hidden;height:0px;color: #009966;">
${courseComment.four}人
    </div></td>
    <td>&nbsp;</td>
    <td valign="top" bgcolor="#FFFF00"><div id="fivestart"  style="background-color:#CCCCCC;display:block;width:100px;width:100%;overflow:hidden;height:0px;color: #009966;">
${courseComment.five}人</div></td>
    <td><img src="images/shopping/fabupingjia.jpg" id="liuyan1" onclick="liuyan1()"/></td>
  

    
  </tr>
  <tr>
    <td height="50"><img style="margin-top:5px;" src="images/shopping/xx_pic_01.gif" width="99" height="15"></td>
    <td>&nbsp;</td>
    <td><img style="margin-top:5px;" src="images/shopping/xx_pic_02.gif" width="99" height="15"></td>
    <td>&nbsp;</td>
    <td><img style="margin-top:5px;" src="images/shopping/xx_pic_03.gif" width="98" height="15"></td>
    <td>&nbsp;</td>
    <td><img style="margin-top:5px;" src="images/shopping/xx_pic_04.gif" width="99" height="15"></td>
    <td>&nbsp;</td>
    <td><img style="margin-top:5px;" src="images/shopping/xx_pic_05.gif" width="99" height="15"></td>
    <td>&nbsp;</td>
  </tr>
</table>
<table width="960" height="39" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content3>
  <tr>
    <td background="images/shopping/pic_34.gif" class="STYLE10"> 　　学员评价 </td>
  </tr>
</table>
<table width="960" height="200" border="0" cellpadding="0"
						cellspacing="0" class="tabrlb" align="center">
						<tr>
							<td valign="top">
								<br />
								</td></tr>
								
							
								<s:if test="listcc.size==0">
								<tr><td align="center" >
								<table class=kc_content3 width="100%" align="center"><tr><td>
								当前还没有评论
								</td></tr></table>
								</td></tr>
								</s:if>
			
						<s:else>
							<tr><td >
						<s:iterator value="listcc">
<table width="960" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content2>
  <tr>
    <td height="200" valign="top">
    <table class="dibaikuang2" width="900" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="200" height="175" align="center"><p><s:if test="user.touxiang!= null">
															<img src="<s:property value="user.mainimg_"/>" width="160" height="200"> 
													</s:if><s:else> 
														<img
															src="<s:property  escape="false" value="user.mainimg_"/>"
															id="cimg_0" width="160" height="200" />
														<SCRIPT type="text/javascript">
															obj = document.getElementById("cimg_0");
															addImgs(obj);
														</SCRIPT> 
													</s:else></p>
          <p>用户名：</p><s:property value="user.realname" /></td>
        <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="dibaikuang2">
              <tr>
                <td height="30" valign="middle">
                <s:if test="commentpoint==1">　<img style="margin-top:5px;" src="images/shopping/xx_pic_01.gif" width="100" height="15"></s:if>
                <s:if test="commentpoint==2">　<img style="margin-top:5px;" src="images/shopping/xx_pic_02.gif" width="100" height="15"></s:if>
                <s:if test="commentpoint==3">　<img style="margin-top:5px;" src="images/shopping/xx_pic_03.gif" width="100" height="15"></s:if>
                <s:if test="commentpoint==4">　<img style="margin-top:5px;" src="images/shopping/xx_pic_04.gif" width="100" height="15"></s:if>
                <s:if test="commentpoint==5">　<img style="margin-top:5px;" src="images/shopping/xx_pic_05.gif" width="100" height="15"></s:if>
                
                </td>
                <td width="200" align="right"><s:date name="commentdate" format="yyyy年MM月dd HH:mm:ss" /></td>
              </tr>
            </table></td>
          </tr>
          
          <tr>
            <td style="padding:10px;"><span class="h30">评价内容：</span>${content }</td>
          </tr>
          </table>
       </td>
      </tr>
    </table>		
	</s:iterator>
	</td></tr>
	</s:else>
	
	</table>
	<s:if test="courseComment.count!=0">
<table width="960" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content2>
  <tr>
    <td><wysLib:page6></wysLib:page6></td>
  </tr>
</table>
</s:if>
<a name="liuyan2"></a>
<table class=kc_content3 align="center" width="960"><tr><td>
									<form action="saveCourseComment.action" method="post" onsubmit="return checkform()">
									<table width="98%" height="30" border="0" align="center"
										cellpadding="0" cellspacing="0" bordercolor="#0033FF">
										<tr>
											<td colspan="2" class="daohang" align="center">
												评论：
											</td>
										</tr>
										<tr>
											<td colspan="2" class="daohang" align="center">
												星级：<label>
									<input type="radio" name="userComment.commentpoint" id="account_search_elUser_valid21" value="1"/><label for="account_search_elUser_valid21">1分</label>
									<input type="radio" name="userComment.commentpoint" id="account_search_elUser_valid22" value="2"/><label for="account_search_elUser_valid22">2分</label>
									<input type="radio" name="userComment.commentpoint" id="account_search_elUser_valid20" value="3"/><label for="account_search_elUser_valid20">3分</label>
									<input type="radio" name="userComment.commentpoint" id="account_search_elUser_valid20" value="4"/><label for="account_search_elUser_valid24">4分</label>
									<input type="radio" name="userComment.commentpoint" id="account_search_elUser_valid20" value="5" checked="checked"/><label for="account_search_elUser_valid25">5分</label>
									</td>
										</tr>
										<tr>
											<td colspan="2" class="daohang" align="center">
												<textarea rows="5" cols="40" name="userComment.content" id="userComment.content" style="border: 2px;border-color: black;"></textarea>
											</td>
										</tr>
										<tr>
											<td colspan="2" class="daohang" align="center">
												
												<input type="hidden" name="pS3"
													value="<s:property value="pS3"/>">
													<s:hidden name ="course.id"></s:hidden>
													<s:hidden name ="ctype"></s:hidden>
													<a name="liuyan">&nbsp;</a>
												<input type="submit" value="提交">
											</td>
										</tr>
									</table>
								</form>
								</td></tr></table>
					<form action="submitAppalyCourses.action" name="SQ" method="post">
						<s:hidden name="course.id" id="courseid" />
						<s:hidden name="Return" />
					</form>
					
			<form action="getCourseIndexview.action" method="post" name="ddd">
					<s:hidden name="pN6" id="pageNow"></s:hidden>
					<s:hidden name="pS6"></s:hidden>
					<s:hidden name="course.id" ></s:hidden>
					<s:hidden name="ctype" ></s:hidden>
					<s:hidden name="mycourse" id="mycourse"></s:hidden>
					<s:hidden name="mycourseorder" id="mycourseorder"></s:hidden>
			</form>
					<script type="text/javascript">
					
					function checkform(){
					 
					 if(document.getElementById("mycourse").value==0){
					 	alert('您未拥有该课程不能评论'); 
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
						function applyCourse(is,id,Return){ 
									if(is == 1){   
										document.getElementById("course.id").value=id;
										document.getElementById("Return").value=Return;
										SQ.action="submitAppalyCourses.action";
										alert('申请已提交，请等待审核结果！'); 
										SQ.submit();  
									} 
									if(is == 2){  
										alert('${course.explain}'+'这些要求您不符合, 无法申请该课程！'); 
										return false;
									}  
						}
					</script>
		<s:include value="frontbottom.jsp" />

	</body>
</HTML>

												