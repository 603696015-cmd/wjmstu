<%@ page language="java" pageEncoding="UTF-8"%>
<script language="javascript" type="text/javascript"> 
  var today = new Date();
  function showDate(){
	var year = today.getYear();
  	var month = today.getMonth() + 1; 
  	var date = today.getDate();		//日期 
  	var day = today.getDay();		//星期
  	var week =new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");
        var dayValue = "";
  	dayValue += year + "年";
  	dayValue += ((month < 10) ? "0" : "") + month + "月";
  	dayValue += date + "日  ";
  	dayValue += (week[day]);
  	document.write("今天是：" + dayValue);
  }
</script>
 
<script type="text/javascript"> 
function displaySubMenu(li) { 
var subMenu = li.getElementsByTagName("ul")[0]; 
subMenu.style.display = "block"; 
} 
function hideSubMenu(li) { 
var subMenu = li.getElementsByTagName("ul")[0]; 
subMenu.style.display = "none"; 
} 
function changeNews(number){
	var newsCom=document.getElementById("newsCom");
    var newsWork=document.getElementById("newsWork");
	var new1=document.getElementById("new1");
	var new2=document.getElementById("new2");
	if(number=="1"){
		newsCom.className="label_name";
		newsWork.className="label_dis";
		new1.style.display="block";
		new2.style.display="none";
	}else{
		newsCom.className="label_dis";
		newsWork.className="label_name";
		new1.style.display="none";
		new2.style.display="block";
	}
}
function getOtherPage(page){
 
	var mainFrame=document.getElementById("mainFrame");
	mainFrame.src=page;
}
 
</script>
<table width="960" border="0" align="center"
	cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="elfrontimages/banner.jpg" width="960" height="130" /></td>
  </tr>
</table>
<table width="960" border="0" align="center" cellpadding="0"
	cellspacing="0" background="elfrontimages/menu_bak.jpg">
	<tr>
		<td width="300" align="center">
			<span style="color: #FFFFFF;"><script>showDate();</script> </span>
		</td>
		<td>
			<table border="0" align="center" cellpadding="0" cellspacing="0"
				background="elfrontimages/menu_bak.jpg">
				<tr>
					<td>
						<div id="menu">
							<ul id="navigation">
								<li>
									<a href="index.action" class="parent">
										<span> 首 页 </span> </a>
								<li onMouseOver="displaySubMenu(this)"
									onMouseOut="hideSubMenu(this)">

									<a href="newsIndex.action?news.title=null&news.ntype.id=1&ntype.id=1" class="parent"> 
										<span> 新闻中心 </span> </a>
									<ul>
										<li> 
											<a href="newsIndex.action?news.title=null&news.ntype.id=1&ntype.id=10" class="parent"> <span>
													新闻动态 </span> </a>
										<li>
											<a href="newsIndex.action?news.title=null&news.ntype.id=1&ntype.id=11"
												class="parent"> <span> 经验交流 </span> </a>
										<li>
											<a href="newsIndex.action?news.title=null&news.ntype.id=1&ntype.id=12"
												class="parent"> <span> 教学公告 </span> </a>
									</ul>
								<li onMouseOver="displaySubMenu(this)"
									onMouseOut="hideSubMenu(this)">
									<a href="#" onclick="return false;" class="parent">
										<span> 学习中心 </span> </a>
									<ul>
										 <li onMouseOver="displaySubMenu(this)"
											onMouseOut="hideSubMenu(this)">

									<a href="courseIndex.action?pN=0&pS=10&containsub=0&course.ctype.id=1"
												class="parent"> <span> 课程导航 </span> </a> 
												
										<li onMouseOver="displaySubMenu(this)"
											onMouseOut="hideSubMenu(this)">

											<a href="study.action?module=mycourselist.action"
												class="parent"> <span> 课程学习 </span> </a> 
										<li onMouseOver="displaySubMenu(this)"
											onMouseOut="hideSubMenu(this)">
 
											<a href="class_listbytypeid.action?pN=0&pS=10"
												class="parent"> <span> 培训班导航 </span> </a>
										<li onMouseOver="displaySubMenu(this)"
											onMouseOut="hideSubMenu(this)">

											<a href="study.action?module=myelclass_list.action?pN=0&pS=10"
												class="parent"> <span> 培训班学习 </span> </a>
									</ul>
								<li onMouseOver="displaySubMenu(this)"
									onMouseOut="hideSubMenu(this)">

									<a href="#" onclick="return false;" class="parent">
										<span> 考试中心 </span> </a>
									<ul>
										<li onMouseOver="displaySubMenu(this)"
											onMouseOut="hideSubMenu(this)">
											<a href="study.action?module=myexamprac_list.action"
												class="parent"> <span> 在线练习 </span> </a>
										<li onMouseOver="displaySubMenu(this)"
											onMouseOut="hideSubMenu(this)">

											<a href="study.action?module=listErsWithoutC.action"
												class="parent"> <span> 在线考试 </span> </a>
										<li onMouseOver="displaySubMenu(this)"
											onMouseOut="hideSubMenu(this)">
 
											<a href="exam_listbytitle.action?pN=0&pS=10"
												class="parent"> <span> 考场导航 </span> </a>
									</ul>
								<li onMouseOver="displaySubMenu(this)"
									onMouseOut="hideSubMenu(this)">
									<a href="#" onclick="return false;" class="parent">
										<span> 交流中心 </span> </a>
									<ul>
										<li>
											<a href="forumIndex.action"
												class="parent"> <span> 学习交流 </span> </a>
										<li>
											<a href="knowledge_center.action"
												class="parent"> <span> 资料中心 </span> </a>
									</ul>
							</ul>
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

