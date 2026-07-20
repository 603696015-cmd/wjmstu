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
		<title>课程-<s:property value="course.name" /></title> 
		<base href="<%=basePath%>" />
<LINK rel=stylesheet type=text/css 
href="images/shopping/index.css"><LINK rel=stylesheet type=text/css 
href="images/shopping/menu.css">
		
		<LINK rel=stylesheet type=text/css href="images/shopping/book_index.css"><LINK 
rel=stylesheet type=text/css href="images/shopping/nav_style_0903.css">
		<LINK href="elfrontimages/index.css" rel=stylesheet type="text/css">
		<link href="elfrontimages/menu.css" rel=stylesheet type="text/css" />
		<link href="elfrontimages/book_index.css" type=text/css rel=stylesheet />
		<link href="elfrontimages/nav_style_0903.css" type=text/css
			rel=stylesheet />
		<link href="images/dtree.css" type="text/css" rel="stylesheet" />
		


		
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
			
		
);

var ax={"statusId":'1'}
	
</script>
<SCRIPT type="text/javascript">  

$(document).ready(function(){  
    $('#send_ajax').click(function(){ 
    
		var aaa =$('#courseid').attr("value");
        var params = {courseid:aaa,type:3}; 
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
 			$(function AddHeight()
		    {
			    document.getElementById("authorinfopart").style.display="none";
			    document.getElementById("bookinfopart").style.display="none";
			    document.getElementById("directoryinfopart").style.display="none";
		    	
		      var div = document.getElementById("authorinfo");
		      
		      var height=div.clientHeight ;
		      if(height<=300){
		      		div.style.height='';
				}else{
				
					div.style.height='300px';
				}
				
				var div2 = document.getElementById("bookinfo");
		      
		      var height2=div2.clientHeight ;
		      if(height2<=300){
		      		div2.style.height='';
				}else{
				
					div2.style.height='300px';
				}
				var div3 = document.getElementById("directoryinfo");
		      
		      var height3=div3.clientHeight ;
		      if(height3<=300){
		      		div3.style.height='';
				}else{
				
					div3.style.height='300px';
				}
				}
				)
    	
      function all2(obj,obj2,obj3){
      var div = document.getElementById(obj2);
      
      div.style.height='';
      obj.style.display = "none";
      
      if(obj3==1){
      	document.getElementById("authorinfopart").style.display="";
      }if(obj3==2){
      	document.getElementById("bookinfopart").style.display="";
      
      }if(obj3==3){
      	document.getElementById("directoryinfopart").style.display="";
      }

    	}
    	function part(obj,obj2,obj3){
    		var div = document.getElementById(obj2);
    		var height=div.clientHeight ;
    		if(height>300){
    			div.style.height='300px';
    		}
    		obj.style.display="none";
    		
    if(obj3==1){
      	document.getElementById("authorinfoall").style.display="";
      }if(obj3==2){
      	document.getElementById("bookinfoall").style.display="";
      
      }if(obj3==3){
      	document.getElementById("directoryinfoall").style.display="";
      }
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
	</HEAD>
	<body onLoad="setImgs();">
	<s:hidden name="bookinfo.id"  id ="courseid"></s:hidden>
		<%@include file="../frontheader.jsp"%>
	<form action="bookinfocourseclass.action" method="post" name="111" onsubmit="return check()">
		<table width=960 height=35 border=0 align=center cellPadding=0 cellSpacing=0 background=images/shopping/gdbg2.gif style="margin-bottom:5px;">
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
    <td background="images/shopping/pic_17.gif" class="STYLE10"> 　　<span class="STYLE11"><s:property value="bookinfo.name" /></span></td>
  </tr>
</table>
<table width="960" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content2>
  <tr>
    <td width="30%" height="320" align="center"><s:if test="bookinfo.picture != null">
															<img src="<s:property value="bookinfo.mainimg_"/>" width="240" height="300"> 
													</s:if><s:else> 
														<img
															src="<s:property  escape="false" value="bookinfo.mainimg_"/>"
															id="cimg_0" width="240" height="300" />
														<SCRIPT type="text/javascript">
															obj = document.getElementById("cimg_0");
															addImgs(obj);
														</SCRIPT> 
													</s:else>		</td>
    <td valign="top"><p>&nbsp;</p>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="40" height="35" align="center"><img src="images/shopping/pic_35.gif" width="5" height="9"></td>
        <td width="160">作 者：<span class="STYLE13"><s:property value="bookinfo.author" /></span></td>
        <td align="left">&nbsp;</td>
      </tr>
      <tr>
        <td width="40" height="35" align="center"><img src="images/shopping/pic_35.gif" width="5" height="9"></td>
        <td width="160">出版社：<span class="STYLE13"><s:property value="bookinfo.press" /></span>　</td>
        <td align="left">出版时间：<span class="STYLE13"><s:date name="bookinfo.pressdate" format="yyyy-MM-dd HH:mm"/></span></td>
      </tr>
      <tr>
        <td width="40" height="35" align="center"><img src="images/shopping/pic_35.gif" width="5" height="9"></td>
        <td width="160">市场价：<s:property value="bookinfo.marketprice" /> 元　</td>
        <td align="left">会员价：<s:property value="bookinfo.vipprice" />　元</td>
      </tr>
      <tr>
        <td height="35" align="center"><img src="images/shopping/pic_35.gif" width="5" height="9"></td>
        <td width="160">版 次：<s:property value="bookinfo.version" /> </td>
        <td align="left">印刷时间：<span class="STYLE13"><s:date name="bookinfo.printdate" format="yyyy-MM-dd HH:mm" /></span></td>
      </tr>
      <tr>
        <td width="40" height="35" align="center"><img src="images/shopping/pic_35.gif" width="5" height="9"></td>
        <td width="160">开 本：<s:property value="bookinfo.format" /> 开</td>
        <td align="left">页 数：<s:property value="bookinfo.page" /> 页 </td>
      </tr>
      
      <tr>
        <td width="40" height="35" align="center"><img src="images/shopping/pic_35.gif" width="5" height="9"></td>
        <td width="160">纸　张：<s:property value="bookinfo.paper" /></td>
        <td align="left">包　装：<s:property value="bookinfo.spackage" /></td>
      </tr>
	  <tr>
        <td height="35" align="center"><img src="images/shopping/pic_35.gif" width="5" height="9"></td>
        <td width="160">字　数：<s:property value="bookinfo.words" />　万字</td>
        <td align="left">阅读体验：<span class="STYLE13">
        <s:if test="bookinfo.readurl!=null"><a href="<s:property value="bookinfo.readurl" />" >点此进入阅读地址</a></s:if>
        <s:else>
        	暂无阅读地址
        </s:else></span></td>
      </tr>
      
    </table>
      
      <table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:8px;">
        <tr>
          <td>&nbsp;</td>
          <td width="160"><img src="images/shopping/jr_buy.gif" width="99" height="32" id="send_ajax" ></td>
          <td width="140"><a href="shopping_addandto.action?commodity.commodityid=<s:property value="bookinfo.id"/>&commodity.commoditytype=3" ><img src="images/shopping/pic_29.gif" width="112" height="36" id="tocart"></a></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<div  id="authorinfo" style="display:block;width:100%;overflow:hidden;">
<table width="960" height="39" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content3>
  <tr>
    <td background="images/shopping/pic_34.gif" class="STYLE10"> 　　作者简介 </td>
  </tr>
</table>
<table width="960" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content2>
  <tr>
    <td valign="top" style="padding:15px;">${bookinfo.authorinfo}</td>
  </tr>
</table>
</div>
<table width="960" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content2>
  <tr>
 <td height="15" align="right" valign="top"><img style="margin:10px;" src="images/shopping/more01.jpg" width="85" height="29" onclick="all2(this,'authorinfopart',1);" id="authorinfoall"><img style="margin:10px;" src="images/shopping/more02.jpg" width="85" height="29" id="authorinfopart" onclick="part(this,'authorinfo',1)"></td>
  </tr>
</table>
<div  id="bookinfo" style="display:block;width:100%;overflow:hidden;">
<table width="960" height="39" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content3>
  <tr>
    <td background="images/shopping/pic_34.gif" class="STYLE10"> 　　内容简介 </td>
  </tr>
</table>
<table width="960" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content2>
  <tr>
    <td valign="top" style="padding:15px;">　　${bookinfo.bookinfo} </td>
  </tr>
</table>
</div>
<table width="960" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content2>
  <tr>
 <td height="15" align="right" valign="top"><img style="margin:10px;" src="images/shopping/more01.jpg" width="85" height="29" onclick="all2(this,'bookinfo',2)" id="bookinfoall"><img style="margin:10px;" src="images/shopping/more02.jpg" width="85" height="29" onclick="part(this,'bookinfo',2)" id="bookinfopart"></td>
  </tr>
</table>
<div id="directoryinfo" style="display:block;width:100%;overflow:hidden;">
<table width="960" height="39" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content3>
  <tr>
    <td background="images/shopping/pic_34.gif" class="STYLE10"> 　　图书目录 </td>
  </tr>
</table>
<table width="960" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content2>
  <tr>
    <td valign="top" style="padding:15px;">　${bookinfo.directoryinfo} </td>
  </tr>

</table>
</div>
<table width="960" border="0" align="center" cellpadding="0" cellspacing="0" class=kc_content2>
  <tr>
 <td height="15" align="right" valign="top"><img style="margin:10px;" src="images/shopping/more01.jpg" width="85" height="29" onclick="all2(this,'directoryinfo',3)"  id="directoryinfoall"><img style="margin:10px;" src="images/shopping/more02.jpg" width="85" height="29" onclick="part(this,'directoryinfo',3)"  id="directoryinfopart"></td>
  </tr>
</table>
		<s:include value="../frontbottom.jsp" />

	
	</body>
</HTML>

												