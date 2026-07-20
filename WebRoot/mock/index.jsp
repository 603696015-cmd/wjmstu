<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>   
 <%@ taglib prefix="fmt" 
           uri="http://java.sun.com/jsp/jstl/fmt" %>          
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
		+ request.getServerName() + ":" + request.getServerPort()
		+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>唐风汉语-</title>
<link href="mock/css/simulation.css"  rel="stylesheet" type="text/css" />
<link href="mock/css/page.css"  rel="stylesheet" type="text/css" />

<style type="text/css">
	.thLeft {
		background:#dedddd url("mock/image/mn010.png")/*tpa=http://mock.tangce.cn/images/mn010.png*/ no-repeat left; height:23px; width:6px;
	}
	
	.thRight {
		background:#dedddd url("mock/image/mn011.png")/*tpa=http://mock.tangce.cn/images/mn011.png*/ no-repeat right; width:6px; height:23px;
	}
	
	.thBack {
		background:#dedddd; font-size:12px;
	}
	
	.td_type {
		background:url("mock/image/mn013_left.png")/*tpa=http://mock.tangce.cn/images/mn013_left.png*/ no-repeat left bottom; 
		height:41px; color:#FFF; overflow:hidden;
	}
	
	.td_type div{
		white-space:nowrap;
	}
	
	.td_listRight {
		background:url("mock/image/mn013_right.png")/*tpa=http://mock.tangce.cn/images/mn013_right.png*/ no-repeat left bottom; 
		height:41px; width:19px;
	}
	
	.td_name {
		overflow:hidden; white-space: nowrap; text-overflow: ellipsis;
		background:url("mock/image/mn015.png")/*tpa=http://mock.tangce.cn/images/mn015.png*/ repeat-x left bottom; height:41px;
	}
	
	.td_name a {color:#666; font-size:16px; text-decoration:none;}
	.td_name a:hover {color:#090; text-decoration:underline;}
	
	.td_date {
		background:url("mock/image/mn016.png")/*tpa=http://mock.tangce.cn/images/mn016.png*/ repeat-x left bottom; width:120px;
		height:41px; color:#666;
	}
	
	.td_time {
		background:url("mock/image/mn015.png")/*tpa=http://mock.tangce.cn/images/mn015.png*/ repeat-x left bottom; width:90px;
		height:41px; color:#666;
	}	
	
	.td_control {
		background:url("mock/image/mn016.png")/*tpa=http://mock.tangce.cn/images/mn016.png*/ repeat-x left bottom;
		height:41px; color:#666;width:120px;
	}

	.td_control a {
		display:block; width:72px; height:23px; font-size:12px; text-decoration:none;
		line-height:23px; margin:5px 0 0 5px; color:#FFF;
	}
	.td_control a:hover {color:#CF0;}
	.td_control a.list_detail {background:url("mock/image/mn018.png")/*tpa=http://mock.tangce.cn/images/mn018.png*/;}
	.td_control a.list_test {background:url("mock/image/mn019.png")/*tpa=http://mock.tangce.cn/images/mn019.png*/;}
	.td_control a.list_test:hover {color:#C00;}

	.td_right {
		background:url("mock/image/mn017.png")/*tpa=http://mock.tangce.cn/images/mn017.png*/ no-repeat left bottom; width:8px; height:41px;
	}

	.table_list {
		color:#333;margin-left:30px;margin-top:5px;
	}
	
	.btn_order {
		color:#333;text-decoration:none;
	}
</style>
<script src="mock/js/jquery.js"  type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {
	$("#btn_search").click(function(){
		levelID = $("#subjectLevel").val();
		
		if(levelID != 0){
			$('#depId').val(levelID);
		}else{
			$('#depId').val(-1);
		}
			
		//提交搜索表单
		$('#mockForm').submit();
	});
	

	$(".btn_order").click(function(){
		levelID = $("#subjectLevel").val();
		typeID = $("#subjectType").val();
		url = "examCenter.action?levelID=" +levelID + "&typeID=" + typeID;
		var orderType = $(this).attr("orderType");
		$("#orderType").val(orderType);
		var orderName = $(this).attr("orderName");
		$("#orderName").val(orderName);
		if($.trim(orderType) == "desc"){
			orderType = "";
		}else{
			orderType = "desc";
		}
		
		url = url + "&orderName=" + orderName + "&orderType=" + orderType;
		window.location.href=url;
		return false;
	});
	
	if(""=="desc"){
		$(".btn_order[orderName='']").find("img").attr("src","mn012.png"/*tpa=http://mock.tangce.cn/images/mn012.png*/);
	}
});

</script>
</head>
<body class="index_body">
<div class="main_out">
  <div class="top_form">
    <div class="top_login"> <a href="user.action-method=user_login&type=in.htm" tppabs="http://mock.tangce.cn/user/user.action?method=user_login&type=in">登录</a> | <a href="user.action-method=user_reg.htm" tppabs="http://mock.tangce.cn/user/user.action?method=user_reg">学生注册</a> | <a href="toregister.action.htm" tppabs="http://mock.tangce.cn/teacher/toregister.action">教师注册</a> | <a href="user.action-method=user_lost.htm" tppabs="http://mock.tangce.cn/user/user.action?method=user_lost">找回密码</a> </div>
    <!--菜单开始-->
    <div class="main_menu_out">
      <div class="main_menu"> <a href="index.action.htm" tppabs="http://mock.tangce.cn/index.action" class="fir">首页</a> <a href="examCenter.action.htm" tppabs="http://mock.tangce.cn/examCenter.action" class="fir sel">试卷中心</a>
        <!--<a href="#">打字平台</a> -->
        <a href="user.action-method=user_login&type=in.htm" tppabs="http://mock.tangce.cn/user/center.action?method=user_home">会员中心</a> <a href="help.action-method=help_home.htm" tppabs="http://mock.tangce.cn/help/help.action?method=help_home">帮助中心</a>
        <div class="menu_left"></div>
        <div class="menu_right"></div>
      </div>
    </div>
    <!--Logo开始-->
    <div class="logo"></div>
    <!--Logo结束-->
  </div>
  <!--顶部内容结束-->
  <!--大图开始-->
  <div class="bigpic"></div>
  <!--大图结束-->
  <!--主要内容开始-->
  <div class="cont_out">
    <!--搜索条开始-->
    <div class="search_out">
    <form action="mockpaer_list.action" id="mockForm" method="post">
      <div class="sea_form">
        <input type="hidden" name="sublibs" value="1" />
        <input type="hidden" name="examPaper.epl.id" id="depId"/>
        <select name="subjectLevel" id="subjectLevel" class="mm_sea_select">
          <option value="0">所有考试级别</option>
          <c:forEach items="${examTypes.child}" var="item">
          	<c:choose>
          		<c:when test="${examPaper.epl.id eq item.id}">
          			<option value="${item.id}" selected>${item.name }</option>
          		</c:when>
          		<c:otherwise>
          		<option value="${item.id}" >${item.name }</option>
          		</c:otherwise>
          	</c:choose>
          	
          	 
          </c:forEach>
         
        </select>
       <!--  <select name="subjectType" id="subjectType" class="mm_sea_select">
          <option value="">所有考试类型 </option>
          <option value="01">模拟试卷</option>
          <option value="02">听力强化训练</option>
          <option value="04">阅读强化训练</option>
          <option value="03">书写强化训练</option>
          <option value="05">YCT模拟考试</option>
        </select> -->
        <div class="sea_btn"> <a  id="btn_search" title="确定" class="sea_sure" style="cursor:pointer;">确定</a> </div>
      </div>
      </form>
      <div class="sea_left"></div>
      <div class="sea_right"></div>
    </div>
    <!--搜索条结束-->
    <!--列表开始-->
    <div class="cont_list_out">
      <input type="hidden" id="orderName"  />
      <input type="hidden" id="orderType"  />
      <table cellpadding="0" cellspacing="0" width="940" class="table_list">
        <tr>
          <th class="thLeft" width="6"></th>
          <th class="thBack" align="center" width="160"> </th>
          <th class="thBack"></th>
          <th class="thBack" align="left"> <a href="#"  class="btn_order" orderName="pubName" orderType=""> 试卷名称 <img src="mock/image/mn014.png" tppabs="http://mock.tangce.cn/images/mn014.png" /> </a> </th>
          <th class="thBack" align="center"> <a href="#"  class="btn_order" orderName="pubTime" orderType="desc"> 发布时间 <img src="mock/image/mn012.png" tppabs="http://mock.tangce.cn/images/mn012.png" /> </a> </th>
          <th class="thBack" align="center"> <a href="#"  class="btn_order" orderName="examTime" orderType=""> 考试时间 <img src="mock/image/mn014.png" tppabs="http://mock.tangce.cn/images/mn014.png" /> </a> </th>
          <td class="thBack" align="center"></td>
          <th class="thRight" width="6"></th>
        </tr>
        <c:forEach items="${examPapers}" var="item">
        <tr>
          <td align="center" class="td_type" colspan="2"><div style="padding-top:5px;padding-left:10px;"> 模拟试卷 </div></td>
          <td class="td_listRight"></td>
          <td class="td_name"><div style="padding-top:5px;">
              <!--<a href="exam/mock.action?method=exam&subject_id=b951933bd408994179cfe9472852c392&paper_id=002ed188c7090f90573c917b4fec8ae6&publish_id=fa2802d750e39e0e7e42ba4ca8294711&level=06" target="_blank" class="list_test" title="HSK（6级）模拟试卷二十一">HSK（6级）模拟试卷二十一</a> -->
              <a href="user.action-method=user_login&type=in.htm" target="_blank" class="list_test" title="${item.title}">${item.title}</a> </div></td>
          <td align="center" class="td_date"><div style="padding-top:5px;"><fmt:formatDate type="date"   value="${item.createtime }" /></div></td>
          <td align="center" class="td_time"><div style="padding-top:5px;"> ${item.during}分钟 </div></td>
          <td align="center" class="td_control"><!-- <a href="exam/mock.action?method=exam&subject_id=b951933bd408994179cfe9472852c392&paper_id=002ed188c7090f90573c917b4fec8ae6&publish_id=fa2802d750e39e0e7e42ba4ca8294711&level=06" target="_blank" class="list_test" title="开始考试">开始考试</a> -->
            <a href="user.action-method=user_login&type=in.htm" tppabs="http://mock.tangce.cn/exam/mock.action?method=inspectSerial&subject_id=b951933bd408994179cfe9472852c392&paper_id=002ed188c7090f90573c917b4fec8ae6&publish_id=fa2802d750e39e0e7e42ba4ca8294711&level=06" target="_blank" class="list_test" title="开始考试">开始考试</a> </td>
          <td class="td_right"></td>
        </tr>
       </c:forEach>
      </table>
      <div style="padding-right:30px; padding-top:10px; padding-bottom:10px; overflow:hidden;">
        <div class="list_page"> <a href="#" title="首页" id="first_page" class="page_btn" onclick="return false;"></a> <a href="#" title="上一页" id="prev_page" class="page_btn" onclick="return false;"></a> <a href=# class="selected">1</a> <a href="#" onclick="nextPage(2);return false;">2</a> <a href="#" onclick="nextPage(3);return false;">3</a> <a href="#" onclick="nextPage(4);return false;">4</a> <a href="#" onclick="nextPage(5);return false;">5</a> <a href="#" class="page_btn" id="next_page" onclick="nextPage(2);return false;"><img src="mock/image/mn023.png" tppabs="http://mock.tangce.cn/images/mn023.png" width="27" height="18" alt="下一页" /></a> <a href="#" class="page_btn" id="last_page" onclick="nextPage(11);return false;"></a>
          <script type="text/javascript">
function nextPage(page){		var url = document.location+'';
		if(url.indexOf('?')>-1){
			if(url.indexOf('currPage')>-1){
				var reg = /currPage=\d*/g;
				url = url.replace(reg,'currPage=' + page );
			}else{
				url += "&currPage=" + page;
			}
		}else{url += "?currPage=" + page;}
		document.location = url;
}
</script>
        </div>
      </div>
    </div>
    <!--列表结束-->
  </div>
  <!--主要内容结束-->
  <!--尾栏开始-->
  <div class="end_form"> <a href="privacy.action.htm" tppabs="http://mock.tangce.cn/privacy.action">隐私政策</a> | <a href="javascript:if(confirm('http://www.tangce.cn/about.html  \n\n���ļ��޷��� Teleport Ultra ����, ��Ϊ ����һ�����·���ⲿ������Ϊ������ʼ��ַ�ĵ�ַ��  \n\n�����ڷ������ϴ���?'))window.location='http://www.tangce.cn/about.html'" tppabs="http://www.tangce.cn/about.html" target="_blank">关于我们</a>
    <!-- |  <a href="#">站点地图</a> -->
    <br />
    版权所有 © 2006-2017 唐风汉语，保留所有权利。 </div>
  <!--尾栏结束-->
</div>
</body>
</html>
