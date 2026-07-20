<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>中国食品安全培训网</title>
<style>
body,div,ul,li,img,a,h2,h1,h3,p,input,form,dl,dt,dd{margin:0;padding:0;border:0;}
body{font-size:14px;font-famile:Arial, '宋体', sans-serif; background:url(images/bg111111.jpg) no-repeat;}
ul,li{list-style:none;}
a{text-decoration:none;color:#000;}

#header{width:900px;height:40px;margin:0px auto; background:#EEEEEE;line-height:40px;overflow:hidden;}
.hd_f{float:left;margin-left:10px;}
.kuang{margin-top:10px;padding:2px;background-color: rgb(255, 255, 255);border:1px solid #bbb;width:100px;}
.button{color:#fff;width:65px;height:34px;padding:2px;margin-top:2px; background:url(images/btn_bg50.gif)}
#center_img{margin:5px auto 0px; width:780px; text-align:center;}
#phone{text-align:right; margin:0 auto;width:780px;}
#phone p a{color:#2965b1;width:100px;padding-right:20px;padding-top:5px; background:url(images/android.png) right no-repeat;height:30px;line-height:30px;}
#footer{width:880px;margin:0 auto; border-bottom:1px solid #ccc; overflow:hidden;}
#footer ul{background:url(images/logo.jpg) no-repeat left top; height:50px;padding-top:20px;}
#footer ul li{ float:left; border-right:1px solid #ccc;width:68px; text-align:center;}
#footer ul li a{color:#2965b1;}
#banquan{text-align:center; color: #000;width:780px;margin:5px auto; overflow:hidden;}
#banquan a{margin-right:15px;}
</style>
</head>

<body>

<div style="overflow:hidden; background:white;width:900px; margin:0 auto;">
<form name="myform" method="post" action="cisco_user_center_login.action" style="padding:  0px;margin: 0px;">
	<div id="header" >
        <div class="hd_f">用户名:</div>
        <div class="hd_f">
        <INPUT id=username type="text"  name="elUser.username" value="${elUser.username}"  class="kuang"/> 
        </div>
        <div class="hd_f">密码:</div>
        <div class="hd_f">
<!--        <input type="password" class="kuang" onmouseover="this.style.backgroundColor ='#E5F0FF'" onmouseout="this.style.backgroundColor='#ffffff'" onfocus="this.style.backgroundColor = '#E5F0FF'"/>-->
         <INPUT type="password" name="elUser.password" class="kuang"/> 
        </div>
        <div class="hd_f"><input type="submit"  value="登陆" class="button" style="cursor:pointer;"/></div>
        <div class="hd_f">
<!--        <input type="button" value="注册" class="button" style="cursor:pointer;"/>-->
         <input type="button"  value="注册" class="button" style="cursor:pointer;" onclick="location.href='registerInit.action'"/>
        </div>
        <div class="hd_f"><a href="index.action">浏览进入</a></div>
    </div>
    </form>
    <!--中部图片-->
    <div id="center_img">
    	<img src="images/spaq.jpg" />
    </div>
 <div style="clear:both;"></div>
    <div id="phone">
    	<p>
       <a href="download.jsp?filename=elstuffs/Test.apk"
					style="color:#2965b1;">手机客户端</a>
        </p>
    </div>
    <div id="footer">
    	<ul>
        	<li style="margin-left:60px;"><a href="#">关于食安</a></li>
   	    	<li><a href="#">卫生信息</a></li>
            <li><a href="#">北京民政</a></li>
            <li><a href="#">食药监督</a></li>
            <li><a href="#">卫生监督</a></li>
            <li style="width:100px;"><a href="#">医患纠纷调解</a></li>
            <li><a href="wzqw.html">网站权威</a></li>
            <li><a href="#">食安客服</a></li>
            <li><a href="wzfw.html">网站服务</a></li>
            <li><a href="#">注册帮助</a></li>
            <li style=" border-right:none;"><a href="#">加入食安</a></li>
        </ul>
    </div>
    <div id="banquan">
    	<a href="http://www.haikou.cyberpolice.cn/"><img src="images/piclink1.png" /></a>
        <a href="http://net.china.com.cn/index.htm"><img src="images/piclink3.png" /></a>
        <a href="http://www.wenming.cn/"><img src="images/piclink4.png" /></a>
        <a href="#"><img src="images/piclink5.png" /></a>
        
    	<!--<p>
        	中国卫生法学会 北京卫生法学会 中国食品安全培训网<br />
 版权所有 Copyright(C)2013-2020 All Rights Reserved， 京ICP备13037625号<br />
 地址：北京市海淀区苏州街长远天地大厦8楼， 电话：010—66778899 ，传真：010—66887799
        </p>-->
    </div>
</div>
</body>
</html>
