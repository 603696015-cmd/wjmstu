<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.sopia.courseman.entities.Course"%>
<SCRIPT type="text/javascript" src="js/jquery/jquery-1.7.2.min.js" ></script>
<link rel="stylesheet" href="http://res.ckimg.com/sites/www/v2/css/ck_base.css" media="screen">
<link rel="stylesheet" href="http://res.ckimg.com/sites/www/v2/css/ck_detail.css" media="screen">
<section style="border-top:none;" class="bdBox_con1 p10">
<div class="kejian" style="*position:relative;*z-index:3;">
	<div class="mb20 pr progressline">
		<div class="cp_list">
			<s:iterator value="cpages">
			<a href="#${id}" style="width:105.77777777778px" class="c_pro_a"></a>
			</s:iterator>
		</div>
	</div>
	<div class="clearfix">
		<div class="fl"></div>
		<div class="fr"></div>
	</div>
</div>
<div class="ksxx">
	<div class="con_catalog con_catalog1" style="*position:relative;*z-index:2;">
		<div class="con_catalog_hd">
		<!-- 第一章 商务英语 -->	
		</div>
		<div class="con_catalog_bd">
			<s:iterator value="cpages">
			<div class="con_catalog_list">
				<div class="catalog_list_hd">
					<div class="catalog_list_num">第<s:property value="rn"/>节</div>
					<div class="catalog_list_tit">
						<i title="未学习" class="clt_circle_"></i>
						<p class="fl"><s:property value="title"/></p>
						<div class="fr tools">
										<!-- <div class="clt_preview png_bg">预览</div> -->
										<!-- <i title="时长" class="clt_time"><em class="c_f60">今天&nbsp;18:30</em></i> -->
										<!-- <a class="clt_arrow_d" href="javascript:;"></a> -->
							<a class="clt_arrow_d" name="${id}" href="javascript:;">&nbsp;</a>
							<a href="course_study.action?course.id=${course.id}&coursePage.id=${id}&course.classid=0" target="_blank" class="c_btn31g mr10 studyButton"><span>观看视频</span></a>
						</div>
					</div>
				</div>
			</div>
			</s:iterator>
		</div>
	</div>
</div>
</section>