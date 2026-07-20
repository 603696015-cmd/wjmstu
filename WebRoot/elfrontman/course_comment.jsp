<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<SCRIPT type="text/javascript" src="js/jquery/jquery-1.7.2.min.js" ></script>
<link rel="stylesheet" href="http://res.ckimg.com/sites/www/v2/css/ck_base.css" media="screen">
<link rel="stylesheet" href="http://res.ckimg.com/sites/www/v2/css/ck_detail.css" media="screen">
<section class="bdBox_con">

	<div class="con_comment">
		<div>
			<div class="mb15 pt15">	
					<span class="f14 c_333 mr5">平均得分
						<s:if test="courseComment.count==0"> 暂无评价 </s:if>
						<s:else>
	        			 <s:property value="courseComment.avg" /> 分 /满分5分 
	       				</s:else>
	       			</span>
	       			<span class="f12 c_777">共有<s:property value="courseComment.count" />条评价</span>
	       	</div>
			<div class="c_ibox clearfix">
			 	<div class="fl">
			 		<label class="fl mr30"  style="width:99;" for="ricx1">
			 			<p align="center">${courseComment.one}人</p>
			 		</label>
			 		<label class="fl mr30" style="width:99;" for="ricx2">
			 			<p align="center">${courseComment.two}人</p>
			 		</label>
			 		<label class="fl mr30" style="width:99;" for="ricx3">
			 			<p align="center">${courseComment.three}人</p>
			 		</label>
			 		<label class="fl mr30" style="width:99;" for="ricx3">
			 			<p align="center">${courseComment.four}人</p>
			 		</label>
			 		<label class="fl" style="width:99;" for="ricx4">
			 			<p align="center">${courseComment.five}人</p>
			 		</label>
			 	</div><br>
			 	<div class="fl">
			 		<label class="fl mr30" for="ricx1">
			 			<img style="margin-top:5px;" src="images/shopping/xx_pic_01.gif" width="99" height="15">
			 		</label>
			 		<label class="fl mr30" for="ricx2">
			 			<img style="margin-top:5px;" src="images/shopping/xx_pic_02.gif" width="99" height="15">
			 		</label>
			 		<label class="fl mr30" for="ricx3">
			 			<img style="margin-top:5px;" src="images/shopping/xx_pic_03.gif" width="98" height="15">
			 		</label>
			 		<label class="fl mr30" for="ricx3">
			 			<img style="margin-top:5px;" src="images/shopping/xx_pic_04.gif" width="99" height="15">
			 		</label>
			 		<label class="fl" for="ricx4">
			 			<img style="margin-top:5px;" src="images/shopping/xx_pic_05.gif" width="99" height="15">
			 		</label>
			 	</div>
			</div>
		</div>
		<ul class="con_comment_bd">
			<s:iterator value="listcc">
			<li>
				<div class="com_avatar">
					<img src="images/pingjia.jpg">
				</div>
				<div class="com_body">
					<div class="com_body_hd pb5">
					
						<b class="c_333"><s:property value="user.realname" />：</b>
						<span class="pl5">
							<s:if test="commentpoint==1">　<img  src="images/shopping/xx_pic_01.gif" ></s:if>
			                <s:if test="commentpoint==2">　<img  src="images/shopping/xx_pic_02.gif" ></s:if>
			                <s:if test="commentpoint==3">　<img  src="images/shopping/xx_pic_03.gif" ></s:if>
			                <s:if test="commentpoint==4">　<img  src="images/shopping/xx_pic_04.gif" ></s:if>
			                <s:if test="commentpoint==5">　<img  src="images/shopping/xx_pic_05.gif" ></s:if>
						</span>
						<span class="c_999 ml20">
							${content }
						</span>
						<div class="com_time">
							<s:date name="commentdate" format="yyyy年MM月dd HH:mm:ss" />
						</div>
					
					</div>
					<div class="com_body_bd c_777">
						<div class="com_body_txt">
							${content }
						</div>
					</div>
				</div>
				<div class="cl"/>
			</li>
			</s:iterator>
		</ul>
	</div>

</section>
