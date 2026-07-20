<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.sopia.common.SystemConfOp"%>
<SCRIPT type="text/javascript" src="js/jquery/jquery-1.7.2.min.js" ></script>
<link rel="stylesheet" href="http://res.ckimg.com/sites/www/v2/css/ck_base.css" media="screen">
<link rel="stylesheet" href="http://res.ckimg.com/sites/www/v2/css/ck_detail.css" media="screen">
<section class="bdBox_con">
	<s:if test="null==stuffs">
	<div class="p10"><p class="ck_notice_g"><img src="http://res.ckimg.com/sites/www/v2/images/public/ico_warning_16x16.png" class="iPic">该课程暂无课件</p></div>
	</s:if>
	<s:else>
		<div class="con_course">
			<ul class="s_c_ulBox1 clearfix">
			<s:iterator value="stuffs">
				<li>
					<s:if test="null!=description">
					<div class="s_c_oCourseBox1">
						<div class="in_type">
							<img alt="" src="http://res.ckimg.com/sites/www/v2/images/public/kj_course/kj_ppt.png">
						</div>
						<div class="in_txt">
							<p class="p1"><a href="<%=SystemConfOp.getStuffUrl() %>download.jsp?filename=<s:property value="description"/>"><s:property value="title"/></a></p>
							<p class="p2"><img alt="" src="http://res.ckimg.com/sites/www/v2/images/post/p_course_gl.gif">&nbsp;《COM实用入门教程》第四讲</p>
						</div>
					</div>
					</s:if>
				</li>
				</s:iterator>
			</ul>
		</div>
	</s:else>
</section>
