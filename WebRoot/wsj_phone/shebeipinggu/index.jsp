<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <HEAD><TITLE>会员中心</TITLE>
<META http-equiv=Content-Type content="text/html; charset=UTF-8">
<META http-equiv=Pragma content=no-cache>
<META http-equiv=Page-Enter content=RevealTrans(Duration=0.5,Transition=14)>
<SCRIPT src="../js/ua.js"></SCRIPT>

<SCRIPT src="../js/treecontrol.js"></SCRIPT>
<LINK href="../css/system.css" type=text/css rel=stylesheet><LINK 
href="../css/houtai.css" type=text/css rel=stylesheet>
<STYLE>.left_tree_head_bg {
	BACKGROUND-POSITION: left top; BACKGROUND-IMAGE: url(../images/leftm/left_tree_head_bg.jpg); LINE-HEIGHT: 25px; BACKGROUND-REPEAT: repeat-x; HEIGHT: 23px
}
.x_tree_top_left {
	BACKGROUND-IMAGE: url(../images/leftm/tree_top_left.gif); WIDTH: 28px; BACKGROUND-REPEAT: no-repeat; HEIGHT: 24px
}
.x_tree_top_middle {
	BACKGROUND-IMAGE: url(../images/leftm/tree_top_center.gif); VERTICAL-ALIGN: middle; BACKGROUND-REPEAT: repeat-x; HEIGHT: 24px; TEXT-ALIGN: center
}
.x_tree_top_right {
	BACKGROUND-IMAGE: url(../images/leftm/tree_top_right.gif); WIDTH: 34px; BACKGROUND-REPEAT: no-repeat; HEIGHT: 24px
}
.tree_font {
	FONT-SIZE: 12px; COLOR: #feac1c; FONT-STYLE: normal; FONT-FAMILY: "宋体"
}
.tree_font2 {
	FONT-SIZE: 12px; COLOR: #fe111c; FONT-STYLE: normal; FONT-FAMILY: "宋体"
}
.tree_bar_unselected {
	BACKGROUND-IMAGE: url(../images/leftm/tree_bar_left.png); WIDTH: 19px; BACKGROUND-REPEAT: no-repeat
}
.tree_bar_unselected_middle {
	BACKGROUND-IMAGE: url(../images/leftm/tree_bar_center.png); BACKGROUND-REPEAT: repeat-x
}
.tree_bar_unselected_right {
	BACKGROUND-IMAGE: url(../images/leftm/tree_bar_right.png); WIDTH: 13px; BACKGROUND-REPEAT: no-repeat
}
.tree_bg_left {
	BACKGROUND-IMAGE: url(../images/leftm/tree_bg_left.png); WIDTH: 11px; BACKGROUND-REPEAT: repeat-y; HEIGHT: 100%
}
.tree_bg_right {
	BACKGROUND-IMAGE: url(../images/leftm/tree_bg_right.png); WIDTH: 11px; BACKGROUND-REPEAT: repeat-y; HEIGHT: 100%
}
.tree_bg_top_left {
	BACKGROUND-POSITION: 50% bottom; FONT-SIZE: 0px; BACKGROUND-IMAGE: url(../images/leftm/tree_bg_top_left.png); WIDTH: 19px; BACKGROUND-REPEAT: no-repeat; HEIGHT: 14px
}
.tree_bg_top_center {
	BACKGROUND-POSITION: 50% bottom; FONT-SIZE: 0px; BACKGROUND-IMAGE: url(../images/leftm/tree_bg_top_center.png); BACKGROUND-REPEAT: repeat-x; HEIGHT: 14px
}
.tree_bg_top_right {
	BACKGROUND-POSITION: 50% bottom; FONT-SIZE: 0px; BACKGROUND-IMAGE: url(../images/leftm/tree_bg_top_right.png); WIDTH: 16px; BACKGROUND-REPEAT: no-repeat; HEIGHT: 14px
}
</STYLE>

<STYLE type=text/css>BODY {
	PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; MARGIN: 0px; PADDING-TOP: 0px
}
A:hover {
	COLOR: #ff0000
}
.STYLE7 {
	COLOR: #ffffff
}
.STYLE8 {
	FONT-WEIGHT: bold; COLOR: #000000
}
.STYLE9 {
	FONT-WEIGHT: bold; FONT-SIZE: 14px
}
</STYLE>

<SCRIPT type=text/javascript> 

	function setTarget(){
		main_area.style.height=parseInt(document.body.clientHeight)-29-47-44-31+"px";
		main_area.style.top=29+47+44+"px";

		document.all.left_border_div.style.height=parseInt(document.body.clientHeight)-29-47-44-31+"px"; //设置左边的细边框
		
		document.all.left_frame.style.height=parseInt(document.body.clientHeight)-29-47-44-31+"px"; //设置左边导航树
		document.all.left_frame.style.left=5+"px"; //设置左边导航树

		document.all.right_frame.style.height=parseInt(document.body.clientHeight)-29-47-44-31+"px"; //设置右边主页面
		document.all.right_frame.style.width=parseInt(document.body.clientWidth)-(parseInt(document.body.clientWidth)*0.2)-4+"px"; //设置右边主页面

		document.all.hide_doc_div.style.height=parseInt(document.body.clientHeight)-29-47-44-31+"px"; //设置隐藏边框
		document.all.hide_doc_div.style.left=(parseInt(document.body.clientWidth)*0.2)+"px";


		document.all.right_border_div.style.height=parseInt(document.body.clientHeight)-29-47-44-31+"px"; //设置右边的细边框
		document.all.right_border_div.style.left=parseInt(document.body.clientWidth)-5+"px";

		footer_area.style.top=parseInt(document.body.clientHeight)-31+"px";
	}
	
	function showmenu(id,liid,showtype){
		var obj = document.getElementById(id);
		var liobj = document.getElementById(liid);
		var xpos = liobj.offsetLeft;
		var ypos = liobj.offsetTop;
		obj.style.position="absolute";
		obj.style.left = parseInt(xpos)+700+"px";
		obj.style.top = parseInt(ypos)+10+"px";
		obj.style.display=showtype;
	}
    function show_menu_window (img) {
           if (img.alt=="隐藏菜单") {
               img.alt="显示菜单";
                img.src="images/leftm/hide-ok.gif";
                document.all.left_doc_div.style.display="none";
                //document.all.right_doc_div.style.width=parseInt(document.body.clientWidth)-22+"px";
          } else {
               img.alt="隐藏菜单";
               img.src="images/leftm/hide-no.gif";
                document.all.left_doc_div.style.display="block";
                //document.all.right_doc_div.style.width=parseInt(document.body.clientWidth)-218+"px";
           }
    }
</SCRIPT>

<SCRIPT language=javascript>
<!--

// Decide if the names are links or just the icons
USETEXTLINKS = 1  //replace 0 with 1 for hyperlinks
USEFRAMES= 1
// Decide if the tree is to start all open or just showing the root folders
STARTALLOPEN = 0 //replace 0 with 1 to show the whole tree

ICONPATH = "../images/leftm/" //change if the gif's folder is a subfolder, for example: 'images/leftm/'
foldersTree=gFld("个人中心", "");
foldersTree.xID="root"; 
fld01=gFld("会员信息维护", "","");
fld01.xID = "01";
node01=insFld(foldersTree, fld01);	
fld0101=gFld("信息修改", "user01.htm","rightFrame");
fld0101.xID = "0101";
node0101=insFld(node01, fld0101);
fld0102=gFld("信息查看", "user01.htm","rightFrame");
fld0102.xID = "0102";
node0102=insFld(node01, fld0102);


fld02=gFld("会员信息管理", "","");
fld02.xID = "02";
node02=insFld(foldersTree, fld02);	
fld0201=gFld("添加设备生产企业", "shebeishengchan.html","rightFrame");
fld0201.xID = "0201";
node0201=insFld(node02, fld0201);
fld0202=gFld("设备生产企业列表", "huiyuanlist.html","rightFrame");
fld0202.xID = "0202";
node0202=insFld(node02, fld0202);
fld0203=gFld("添加设备销售企业", "shebeishengchanxiaoshou.html","rightFrame");
fld0203.xID = "0203";
node0203=insFld(node02, fld0203);
fld0204=gFld("设备销售企业列表", "huiyuanlist.html","rightFrame");
fld0204.xID = "0204";
node0204=insFld(node02, fld0204);
fld0205=gFld("添加燃油供应商", "youpinshengchan.html","rightFrame");
fld0205.xID = "0205";
node0205=insFld(node02, fld0205);
fld0206=gFld("燃油供应商列表", "huiyuanlist.html","rightFrame");
fld0206.xID = "0206";
node0206=insFld(node02, fld0206);
fld0207=gFld("添加油品生产企业", "youpingongying.html","rightFrame");
fld0207.xID = "0207";
node0207=insFld(node02, fld0207);
fld0209=gFld("油品生产企业列表", "huiyuanlist.html","rightFrame");
fld0209.xID = "0209";
node0209=insFld(node02, fld0209);
fld0210=gFld("添加配件生产企业", "shebeishengchanxiaoshou.html","rightFrame");
fld0210.xID = "0210";
node0210=insFld(node02, fld0210);
fld0211=gFld("配件生产企业列表", "huiyuanlist.html","rightFrame");
fld0211.xID = "0211";
node0211=insFld(node02, fld0211);
fld0212=gFld("添加保险公司", "baoxiangongsi.html","rightFrame");
fld0212.xID = "0212";
node0212=insFld(node02, fld0212);
fld0213=gFld("保险公司列表", "huiyuanlist.html","rightFrame");
fld0213.xID = "0213";
node0213=insFld(node02, fld0213);


fld03=gFld("产品管理", "","");
fld03.xID = "03";
node03=insFld(foldersTree, fld03);	
fld0301=gFld("发布保险产品", "baoxianchanpin.html","rightFrame");
fld0301.xID = "0301";
node0301=insFld(node03, fld0301);
fld0302=gFld("保险产品列表", "chanpinlist.html","rightFrame");
fld0302.xID = "0302";
node0302=insFld(node03, fld0302);
fld0303=gFld("发布机械设备", "shebeishengchanxiaoshou.html","rightFrame");
fld0303.xID = "0303";
node0303=insFld(node03, fld0303);
fld0304=gFld("机械设备列表", "chanpinlist.html","rightFrame");
fld0304.xID = "0304";
node0304=insFld(node03, fld0304);
fld0305=gFld("发布油品", "youpingongying.html","rightFrame");
fld0305.xID = "0305";
node0305=insFld(node03, fld0305);
fld0306=gFld("油品列表", "chanpinlist.html","rightFrame");
fld0306.xID = "0306";
node0306=insFld(node03, fld0306);
fld0307=gFld("发布燃油产品", "youpingongying.html","rightFrame");
fld0307.xID = "0307";
node0307=insFld(node03, fld0307);
fld0308=gFld("燃油产品列表", "chanpinlist.html","rightFrame");
fld0308.xID = "0308";
node0308=insFld(node03, fld0308);
fld0309=gFld("发布配件产品", "shebeishengchanxiaoshou.html","rightFrame");
fld0309.xID = "0309";
node0309=insFld(node03, fld0309);
fld0310=gFld("配件产品列表", "chanpinlist.html","rightFrame");
fld0310.xID = "0305";
node0310=insFld(node03, fld0310);


fld04=gFld("设备登记", "","");
fld04.xID = "04";
node04=insFld(foldersTree, fld04);	
fld0401=gFld("添加设备", "shebeidengji.html","rightFrame");
fld0401.xID = "0401";
node0401=insFld(node04, fld0401);
fld0402=gFld("我添加的设备", "shebeilist.html","rightFrame");
fld0402.xID = "0402";
node0402=insFld(node04, fld0402);
fld0403=gFld("设备列表", "shebeilist.html","rightFrame");
fld0403.xID = "0403";
node0403=insFld(node04, fld0403);


fld05=gFld("在线投保", "","");
fld05.xID = "05";
node05=insFld(foldersTree, fld05);	
fld0501=gFld("我要投保", "baoxian-toubao.html","rightFrame");
fld0501.xID = "0501";
node0501=insFld(node05, fld0501);
fld0502=gFld("我的保单", "baoxian-wode3.html","rightFrame");
fld0502.xID = "0502";
node0502=insFld(node05, fld0502);
fld0503=gFld("保单确认", "baoxian-wode4.html","rightFrame");
fld0503.xID = "0503";
node0503=insFld(node05, fld0503);
fld0504=gFld("保单处理", "baoxian-wode5.html","rightFrame");
fld0504.xID = "0504";
node0504=insFld(node05, fld0504);
fld0505=gFld("保单查询", "baoxian-wode5.html","rightFrame");
fld0505.xID = "0505";
node0505=insFld(node05, fld0505);





	function menu_init_open()
	{		
		clickOnNode('01');		                      
	}
	
//-->
</SCRIPT>

<META content="MSHTML 6.00.2900.6197" name=GENERATOR></HEAD>
  
  <BODY style="WIDTH: 100%; HEIGHT: 100%"><!--整个页面的顶部-->
<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
  <TBODY>
  <TR>
    <TD 
    style="PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; PADDING-TOP: 0px" 
    width="100%" height=58>
      <META http-equiv=pragma content=no-cache>
      <META http-equiv=cache-control content=no-cache>
      <META http-equiv=expires content=0>
      <META http-equiv=keywords content=keyword1,keyword2,keyword3>
      <META http-equiv=description content="This is my page"><!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
      <TABLE 
      style="PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; MARGIN: 0px; PADDING-TOP: 0px" 
      height=58 cellSpacing=0 cellPadding=0 width="100%" 
      background=../images/bg_admin.jpg border=0>
        <TBODY>
        <TR>
          <TD 
          style="PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; PADDING-TOP: 0px"><IMG 
            src="../images/name2.jpg">
          <TD>
          <TD>
            <TABLE cellSpacing=0 cellPadding=0 width="100%">
              <TBODY>
              <TR>
                <TD style="FONT-SIZE: 12px; COLOR: #fff" align=right>姓名： 
                  <STRONG class="font_arial white">超级管理 </STRONG>， <!--身份证：
				<strong class="font_arial white">
					610326198312130016
				</strong>，-->部门： <STRONG class="font_arial white">中国食品安全培训网
                  </STRONG>， 用户名： <STRONG class="font_arial white">admin 
                  </STRONG>，角色： 超级管理员 | <A class=white 
                  href="http://localhost:8089/gdgat0528/logout.action">退出登录</A> 
                  | <A class=white 
                  href="http://localhost:8089/gdgat0528/index.action">网站首页</A></TD></TR>
              <TR>
                <TD 
                style="PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; PADDING-TOP: 0px" 
                vAlign=bottom align=right>
                  <UL id=menu style="MARGIN: 0px">
                    <LI><A title=新闻管理 
                    href="#"><SPAN>新闻管理</SPAN> 
                    </A></LI>
                    <LI><A title=产品管理 
                    href="#"><SPAN>产品管理</SPAN> 
                    </A></LI>
                    <LI><A title=保险管理 
                    href="#"><SPAN>保险管理</SPAN> 
                    </A></LI>
                    <LI><A title=会员管理 
                    href="#"><SPAN>会员管理</SPAN> 
                    </A></LI>
                    <LI><A title=交易管理 
                    href="#"><SPAN>交易管理</SPAN> 
                    </A></LI>
                    <LI><A title=系统管理 
                    href="#"><SPAN>系统管理</SPAN> 
                    </A></LI>
                    <LI><A title=个人中心 
                    href="#"><SPAN>个人中心</SPAN> 
                    </A></LI></UL></TD></TR></TBODY></TABLE></TD></TR>
        <TR>
          <TD height=3></TD></TR></TBODY></TABLE></TD></TR>
  <TR>
    <TD height="100%"><!--页面中间部分-->
      <TABLE id=main_area height="100%" cellSpacing=0 cellPadding=0 width="100%" 
      border=0>
        <TBODY>
        <TR>
          <TD width=5 background=../images/index1_border_left.gif>
            <DIV style="WIDTH: 5px"></DIV></TD>
          <TD vAlign=top width=0 height="100%">
            <TABLE id=left_doc_div height="100%" cellSpacing=0 cellPadding=0 
            width=210 bgColor=#d5e5f0 border=0>
              <TBODY>
              <TR vAlign=top height="100%">
                <TD height="100%">
                  <TABLE style="MARGIN-LEFT: -4px" cellSpacing=0 cellPadding=0 
                  width="100%" border=0>
                    <TBODY>
                    <TR>
                      <TD class=x_tree_top_left 
                      style="BACKGROUND-IMAGE: url(../images/leftm/tree_top_left.gif)">&nbsp; 
                      </TD>
                      <TD class=x_tree_top_middle 
                      style="BACKGROUND-IMAGE: url(../images/leftm/tree_top_center.gif)">&nbsp; 
                        <FONT class=tree_font2>超级管理员</FONT> </TD>
                      <TD class=x_tree_top_right 
                      style="BACKGROUND-IMAGE: url(../images/leftm/tree_top_right.gif)">&nbsp; 
                      </TD></TR></TBODY></TABLE>
                  <TABLE style="MARGIN-LEFT: 6px" cellSpacing=0 cellPadding=0 
                  width="94%" border=0>
                    <TBODY>
                    <TR>
                      <TD class=tree_bg_top_left 
                      style="BACKGROUND-IMAGE: url(../images/leftm/tree_bg_top_left.png)">&nbsp; 
                      </TD>
                      <TD class=tree_bg_top_center 
                      style="BACKGROUND-IMAGE: url(../images/leftm/tree_bg_top_center.png)">&nbsp; 
                      </TD>
                      <TD class=tree_bg_top_right 
                      style="BACKGROUND-IMAGE: url(../images/leftm/tree_bg_top_right.png)">&nbsp; 
                      </TD></TR></TBODY></TABLE>
                  <TABLE style="MARGIN-LEFT: 6px" height="100%" cellSpacing=0 
                  cellPadding=0 width="94%" border=0>
                    <TBODY>
                    <TR>
                      <TD class=tree_bg_left 
                      style="BACKGROUND-IMAGE: url(../images/leftm/tree_bg_left.png)"></TD>
                      <TD style="PADDING-LEFT: 0px" height="100%">
                        <TABLE height="100%" cellSpacing=0 cellPadding=0 
                        width="100%" bgColor=#eaf2f8 border=0>
                          <TBODY>
                          <TR vAlign=top height="100%">
                            <TD>
                              <SCRIPT>initializeDocument(); menu_init_open();</SCRIPT>
                              <NOSCRIPT></NOSCRIPT></TD></TR></TBODY></TABLE></TD>
                      <TD class=tree_bg_right 
                      style="BACKGROUND-IMAGE: url(../images/leftm/tree_bg_right.png)"></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD>
          <TD id=hide_doc_div vAlign=top width=4 
          background="../images/hidden-bar.gif height="100%"><IMG 
            id=show_menu_window_img onclick=show_menu_window(this); alt=显示菜单 
            src="../images/hide-no.gif" border=0> </TD>
          <TD vAlign=top align=middle width="100%"><!--右边的主页面--><IFRAME 
            style="WIDTH: 100%; HEIGHT: 100%" name=rightFrame 
            src="getBaseInfo.action" frameBorder=0></IFRAME></TD>
          <TD width=5 background=../images/index1_border_right.gif>
            <DIV style="WIDTH: 5px"></DIV></TD></TR></TBODY></TABLE></TD></TR>
  <TR>
    <TD id=footer_area style="WIDTH: 100%; HEIGHT: 29px" vAlign=bottom>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0 
        valign="bottom"><TBODY>
        <TR>
          <TD class=x_index1_footer_left 
          style="BACKGROUND-IMAGE: url(../images/leftm/index1_footer_left.gif)">&nbsp;&nbsp;&nbsp; 
            <FONT class=index1_font>当前登录：admin </FONT></TD>
          <TD class=x_index1_footer_middle 
          style="BACKGROUND-IMAGE: url(../images/leftm/index1_footer_center.gif)">&nbsp; 
          </TD>
          <TD class=x_index1_footer_right id=_copyright 
          style="BACKGROUND-IMAGE: url(../images/leftm/index1_footer_right.gif)" 
          vAlign=center></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
	</body>
</html>
