<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<SCRIPT type="text/javascript" src="js/jquery/jquery-1.7.2.min.js" ></script>
<link rel="stylesheet" href="http://res.ckimg.com/sites/www/v2/css/ck_base.css" media="screen">
<link rel="stylesheet" href="http://res.ckimg.com/sites/www/v2/css/ck_detail.css" media="screen">
<section class="bdBox_con">
	<div class="con_student">
		<ul class="cleafix">
			<s:iterator value="users">
			<li>
				<dl class="i_stu">
					<dt><img width="60" height="60" src="${touxiang }"></dt>
					<dd>
						<h4><s:property value="username"/></h4>
						<p><s:date name="baoming" format="yyyy年MM月dd日"/> 报名</p>
					</dd>
				</dl>
			</li>
			</s:iterator>
		</ul>
	</div>

</section>
