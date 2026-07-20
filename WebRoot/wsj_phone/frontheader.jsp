<%@ page language="java" pageEncoding="UTF-8"%>
<LINK rel="shortcut icon" href="favicon.ico">
<LINK rel=stylesheet type=text/css href="images/gaiban2/css.css">
<LINK rel=stylesheet type=text/css
	href="images/gaiban2/jquery-cluetip.css">
<LINK href="images/gaiban2/global.css" type=text/css rel=stylesheet>
<LINK rel=stylesheet type=text/css href="css/gaiban/css/basic.css">
<LINK rel=stylesheet type=text/css
	href="css/gaiban/statics/css/yp_education.css">
<LINK rel=stylesheet type=text/css
	href="css/gaiban/statics/css/bwy_style.css">
<LINK rel=stylesheet type=text/css href="images/gaiban2/joyo.css">
<link rel="stylesheet" href="css/gaiban2/index.css" type="text/css"
	media="screen" />
<link rel="stylesheet" type="text/css"
	href="http://www.chinatrace.org:80/css/jquery.fancybox-1.3.4.css"
	media="screen" />
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
<DIV id=container>
<table width="1001" height="117" border="0" align="center"
	cellpadding="0" cellspacing="0" background="images/gaiban2/img_2.jpg">
	<tr>
		<td height="75" valign="bottom">
			<img src="images/banner.jpg" width="100%" height="122">
		</td>
	</tr>
	<tr>
		<td>
			<table width="1000" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td>
						<DIV id=menu_bg>
							<DIV class=menu>
								<LI>
									<A href="index.action">网站首页</A>
								</LI>
								<LI>
									<A href="newsIndex.action">新闻首页</A>
								</LI>
								<LI>
									<A href="forumIndex.action">论坛首页</A>
								</LI>
								<LI>
									<A href="knowledge_center_list.action">知识库首页</A>
								</LI>
								<LI>
									<A href="forum_courseclub.action">选课中心</A>
								</LI>
								<LI>
									<A href="forum_classclub.action">选班中心</A>
								</LI>
								<LI>
									<A href="examRoomShoppping.action">考场中心</A>
								</LI>
								<LI>
									<A href="cisco_user_center.action">个人中心</A>
								</LI>
								<LI>
									<A href="map.action">网站地图</A>
								</LI>
							</DIV>
						</DIV>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

