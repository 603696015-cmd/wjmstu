<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<section class="bdBox_con mb20 pb20">
	<p class="detail_a_1 bc pt30 mb10"><a target="_blank" href="/user_guide.html"></a></p>
	<!-- =S 课程简介 -->
	<div class="con_intro">
		<div class="con_catalog_bar  clearfix">
			<span class="fl">课程简介</span>
		</div>
		<div style="word-wrap:word-break;word-break:break-all;" class="con_intro_bd">
			<p>
				<s:property value="course.description" />
			</p>
		<!-- <p>
				<img alt="" src="http://web.img.chuanke.com/resource/a988c5d945c75bdadf4c9d28abcc3b41.jpg"><a target="_blank" href="http://www.chuanke.com/1019545-75602.html"></a><a target="_blank" href="http://www.chuanke.com/1019545-75835.html"></a><a target="_blank" href="http://www.chuanke.com/1019545-75878.html"><img alt="" src="http://web.img.chuanke.com/resource/de3053e96a7f30e5b30d7dd2f383ded5.jpg"></a><img alt="" src="http://web.img.chuanke.com/resource/c28178062d2b6c56260486b09bb22d33.jpg"><span style="color:#555555;font-family:tahoma, arial, 宋体;"></span><img alt="" src="http://web.img.chuanke.com/resource/cad85228588463d9647e34632aca9fb4.jpg"><a target="_blank" href="http://www.bydey.com/forum.php?mod=viewthread&amp;tid=8998"><img alt="" src="http://web.img.chuanke.com/resource/f19b986239d8e8c1dba7b67e0104994f.jpg"></a><img alt="" src="http://web.img.chuanke.com/resource/333ba8c41c730eb9deddcc0ff7288989.jpg"><a target="_blank" href="http://www.chuanke.com/s1019545.html"><img alt="" src="http://web.img.chuanke.com/resource/f61ac3f0e228d3038ce91215f084d054.jpg"></a><img alt="" src="http://web.img.chuanke.com/resource/db9e231f5afc38169239db6c50165608.jpg"> 
			</p> -->	
		</div>
	</div>
	<!-- =E 课程简介 -->
</section>
<section class="bdBox_con1 pb20">
	<div class="kcxq">
		<div class="con_catalog">
			<div class="con_catalog_bar clearfix">
				<span class="fl">课程目录</span>
			</div>
			<s:iterator value="cpages">
			<div class="con_catalog_hd">
			<!-- 第1章：百点学苑-推销与谈判9 -->	
			</div>
			<div class="con_catalog_bd">
				<div class="con_catalog_list">
					<div class="catalog_list_hd">
						<div class="catalog_list_num">第<s:property value="rn"/>节</div>
						<div class="catalog_list_tit">
							<i title="视频课" class="iMedia"></i>
							<p><s:property value="title"/></p>
							<div class="clt_spl"></div>
							<div class="clt_time">
								<s:if test="1==isfree">
								<a target="_blank" class="preview" href="coursepage_preview.action?coursePage.id=${id}">预览</a>
								</s:if>
							</div>
						</div>
					</div>
				</div>
			</div>
			</s:iterator>
		</div>
	</div>
</section>