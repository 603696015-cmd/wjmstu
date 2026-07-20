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
	<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />

		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>考场购买详情页</TITLE>
		<base href="<%=basePath%>">

		<LINK href="wsj_phone/elfrontimages/index.css" rel=stylesheet type="text/css">
		<link href="wsj_phone/elfrontimages/menu.css" rel=stylesheet type="text/css" />
		<link href="wsj_phone/images/dtree.css" type="text/css" rel="stylesheet" />
		<LINK rel=stylesheet type=text/css href="wsj_phone/images/shopping/index.css">
		<LINK rel=stylesheet type=text/css href="wsj_phone/images/shopping/menu.css">
		<LINK rel=stylesheet type=text/css
			href="wsj_phone/images/shopping/book_index.css">
		<LINK rel=stylesheet type=text/css
			href="wsj_phone/images/shopping/nav_style_0903.css">


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
.menu_bg {
	WIDTH:320px;
	HEIGHT: 40px;
	background-color:#F3F3F3;
	background-repeat: repeat-x;
	background-position: left -100px;
}
.menu_bg LI {
	TEXT-ALIGN: center; WIDTH: 100px; BACKGROUND-REPEAT: no-

repeat; BACKGROUND-POSITION: left -5px; FLOAT: left; HEIGHT: 40px
}
.menu_bg LI A {
	LINE-HEIGHT: 40px; DISPLAY: block; HEIGHT: 40px; COLOR: #000; 

FONT-SIZE: 14px; FONT-WEIGHT: normal; TEXT-DECORATION: none
}
.menu_bg LI A:link {
	COLOR: #000;
}
.menu_bg LI A:visited {
	COLOR: #000;
}
.menu_bg LI A.here {
	COLOR: #000;
	background-image: url

(http://www.ccunc.com/images/xtb/menu_hover.gif);
	background-repeat: no-repeat;
	background-position: -5px;
}
.menu_bg LI A:hover {
	COLOR: #fff;
}
.menu_bg LI A.libg {
	BACKGROUND: url(http://www.ccunc.com/images/xtb/libg.gif) no-

repeat; COLOR: #fff
}
li{ list-style:none;}
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
					 	alert('您已拥有该培训班不需要再次购买'); 
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
	<BODY onLoad="setImgs();"> <%@include file="frontheader.jsp"%>
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0">
		  <tr>
				<td  valign="top">
			    <table style="margin-top: 8px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
							<td height="200" align="left" valign="top"
									style="PADDING: 8px; line-height: 25px;">

								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										
									  <td valign="top" >
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="30" align="left" class="bline4" style="background-color:#00A2FC;">考场简介</td>
  </tr>
</table>

											<table width="320" border="0" cellpadding="0" cellspacing="0">
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
															<span style="color: red">可以购买</span>
													    </s:elseif>
												    </td>
											    </tr>
										    </table>
								    </tr>
							    </table>
								<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
									<TBODY>
										<TR>
											<TD height=35 align=left vAlign=center class="bline4"
													style="padding-left: 25px;background-color:#00A2FC;">
												考场内容简介
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
										
								    </TBODY>
							    </TABLE></td>
				  </table>
				</td>
			</tr>
		</table>
		

							<s:if test="courseComment.count!=0">
						  <table width="320" border="0" align="left" cellpadding="0"
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
	
	</body>
</HTML>
