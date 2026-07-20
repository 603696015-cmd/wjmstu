<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<SCRIPT type="text/javascript" src="js/jquery/jquery-1.7.2.min.js" ></script>
<link rel="stylesheet" href="http://res.ckimg.com/sites/www/v2/css/ck_base.css" media="screen">
<link rel="stylesheet" href="http://res.ckimg.com/sites/www/v2/css/ck_detail.css" media="screen">
<section class="bdBox_con">
	<s:if test="null==course.teacherName">
		<div class="p10"><p class="ck_notice_g"><img src="http://res.ckimg.com/sites/www/v2/images/public/ico_warning_16x16.png" class="iPic">暂无直播授课的老师</p></div>
	</s:if>
	<s:else>
	<ul class="con_teacher">
		<li>
			<dl class="i_ther clearfix">
				<dt><img width="70" height="70" src="<s:property value="course.lecturerMainimg"/>"></dt>
				<dd>
					<h4><span uid="1240147" id="teacherAuthName_1240147" class="tipsTrigger" distance="5" time="250" hidedelay="500" beingshown="0" shown="0"><s:property value="course.teacherName" /></span></h4>
					<p><s:property value="course.teacherinfo" /></p>
				</dd>
			</dl>
		</li>
	</ul>
	<div class="ck_page tr pt20 mb20 mr30">
	</div>
	</s:else>
</section>
