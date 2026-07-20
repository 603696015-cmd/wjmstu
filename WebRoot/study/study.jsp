<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<HTML>
	<HEAD>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>个人中心</TITLE>
		<base href="<%=basePath%>">
		<SCRIPT src="images/leftm/ua.js"></SCRIPT>
		<SCRIPT src="images/leftm/treecontrol.js"></SCRIPT>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<LINK href="images/leftm/houtai.css" type=text/css rel=stylesheet>
		<STYLE>
.left_tree_head_bg {
	BACKGROUND-POSITION: left top;
	BACKGROUND-IMAGE: url(images/leftm/left_tree_head_bg.jpg);
	LINE-HEIGHT: 25px;
	BACKGROUND-REPEAT: repeat-x;
	HEIGHT: 23px
}

.x_tree_top_left {
	BACKGROUND-IMAGE: url(images/leftm/tree_top_left.gif);
	WIDTH: 28px;
	BACKGROUND-REPEAT: no-repeat;
	HEIGHT: 24px
}

.x_tree_top_middle {
	BACKGROUND-IMAGE: url(images/leftm/tree_top_center.gif);
	VERTICAL-ALIGN: middle;
	BACKGROUND-REPEAT: repeat-x;
	HEIGHT: 24px;
	TEXT-ALIGN: center
}

.x_tree_top_right {
	BACKGROUND-IMAGE: url(images/leftm/tree_top_right.gif);
	WIDTH: 34px;
	BACKGROUND-REPEAT: no-repeat;
	HEIGHT: 24px
}

.tree_font {
	FONT-SIZE: 12px;
	COLOR: #feac1c;
	FONT-STYLE: normal;
	FONT-FAMILY: "宋体"
}

.tree_font2 {
	FONT-SIZE: 12px;
	COLOR: #fe111c;
	FONT-STYLE: normal;
	FONT-FAMILY: "宋体"
}

.tree_bar_unselected {
	BACKGROUND-IMAGE: url(images/leftm/tree_bar_left.png);
	WIDTH: 19px;
	BACKGROUND-REPEAT: no-repeat
}

.tree_bar_unselected_middle {
	BACKGROUND-IMAGE: url(images/leftm/tree_bar_center.png);
	BACKGROUND-REPEAT: repeat-x;FONT-SIZE: 14px
}

.tree_bar_unselected_right {
	BACKGROUND-IMAGE: url(images/leftm/tree_bar_right.png);
	WIDTH: 13px;
	BACKGROUND-REPEAT: no-repeat
}

.tree_bg_left {
	BACKGROUND-IMAGE: url(images/leftm/tree_bg_left.png);
	WIDTH: 11px;
	BACKGROUND-REPEAT: repeat-y;
	HEIGHT: 100%
}

.tree_bg_right {
	BACKGROUND-IMAGE: url(images/leftm/tree_bg_right.png);
	WIDTH: 11px;
	BACKGROUND-REPEAT: repeat-y;
	HEIGHT: 100%
}

.tree_bg_top_left {
	BACKGROUND-POSITION: 50% bottom;
	FONT-SIZE: 0px;
	BACKGROUND-IMAGE: url(images/leftm/tree_bg_top_left.png);
	WIDTH: 19px;
	BACKGROUND-REPEAT: no-repeat;
	HEIGHT: 14px
}

.tree_bg_top_center {
	BACKGROUND-POSITION: 50% bottom;
	FONT-SIZE: 0px;
	BACKGROUND-IMAGE: url(images/leftm/tree_bg_top_center.png);
	BACKGROUND-REPEAT: repeat-x;
	HEIGHT: 14px
}

.tree_bg_top_right {
	BACKGROUND-POSITION: 50% bottom;
	FONT-SIZE: 0px;
	BACKGROUND-IMAGE: url(images/leftm/tree_bg_top_right.png);
	WIDTH: 16px;
	BACKGROUND-REPEAT: no-repeat;
	HEIGHT: 14px
}
</STYLE>



		<STYLE type=text/css>
BODY {
	PADDING-RIGHT: 0px;
	PADDING-LEFT: 0px;
	PADDING-BOTTOM: 0px;
	MARGIN: 0px;
	PADDING-TOP: 0px
}

A:link {
	COLOR: #0000ff; FONT-SIZE: 13px; 
}
A:hover {
	COLOR: red;FONT-SIZE: 13px; 
}
A:visited {
	COLOR: #0000ff; FONT-SIZE: 13px; 
}
A:active {
	COLOR: #0000ff; FONT-SIZE: 13px; 
}


.STYLE7 {
	color: #FFFFFF
}

.STYLE8 {
	color: #000000;
	font-weight: bold;
}

.STYLE9 {
	font-size: 14px;
	font-weight: bold;
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

ICONPATH = "images/leftm/" //change if the gif's folder is a subfolder, for example: 'images/leftm/'
<wysLib:funcMenu></wysLib:funcMenu>
	
	
	function menu_init_open()
	{		
		clickOnNode('01');		                      
	}
//-->
</SCRIPT>
	</HEAD>
	<BODY style="WIDTH: 100%; HEIGHT: 100%;overflow-x:auto;overflow-y:auto">
		<!--整个页面的顶部-->

		<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
			<TBODY>
				<TR id="header">
					<TD style="padding: 0px;" width="100%"  height=58>
						<%@include file="../header.jsp" %>
					</TD>
				</TR>
				<TR>
					<TD height="100%">
						<!--页面中间部分-->
						<TABLE id=main_area height="100%" cellSpacing=0 cellPadding=0
							width="100%" border=0>
							<TBODY>
								<TR>
									<TD width=5 background=images/leftm/index1_border_left.gif>
										<DIV style="WIDTH: 5px"></DIV>
									</TD>
									<TD vAlign=top width=0 height="100%">
										<TABLE id=left_doc_div height="100%" cellSpacing=0
											cellPadding=0 width=210 bgColor=#d5e5f0 border=0>
											<TBODY>
												<TR vAlign=top height="100%">
													<TD height="100%">
														<TABLE style="MARGIN-LEFT: -4px" cellSpacing=0
															cellPadding=0 width="100%" border=0>
															<TBODY>
																<TR>
																	<TD class=x_tree_top_left
																		style="BACKGROUND-IMAGE: url(images/leftm/tree_top_left.gif)">&nbsp;
																		
																	</TD>
																	<TD class=x_tree_top_middle
																		style="BACKGROUND-IMAGE: url(images/leftm/tree_top_center.gif)">
																		&nbsp;
																		<FONT class=tree_font2><s:property value="#session.roleName" /></FONT>
																	</TD>
																	<TD class=x_tree_top_right
																		style="BACKGROUND-IMAGE: url(images/leftm/tree_top_right.gif)">&nbsp;
																		
																	</TD>
																</TR>
															</TBODY>
														</TABLE>
														<TABLE style="MARGIN-LEFT: 6px" cellSpacing=0
															cellPadding=0 width="94%" border=0>
															<TBODY>
																<TR>
																	<TD class=tree_bg_top_left
																		style="BACKGROUND-IMAGE: url(images/leftm/tree_bg_top_left.png)">&nbsp;
																		
																	</TD>
																	<TD class=tree_bg_top_center
																		style="BACKGROUND-IMAGE: url(images/leftm/tree_bg_top_center.png)">&nbsp;
																		
																	</TD>
																	<TD class=tree_bg_top_right
																		style="BACKGROUND-IMAGE: url(images/leftm/tree_bg_top_right.png)">&nbsp;
																		
																	</TD>
																</TR>
															</TBODY>
														</TABLE>
														<TABLE style="MARGIN-LEFT: 6px" height="100%"
															cellSpacing=0 cellPadding=0 width="94%" border=0>
															<TBODY>
																<TR>
																	<TD class=tree_bg_left
																		style="BACKGROUND-IMAGE: url(images/leftm/tree_bg_left.png)"></TD>
																	<TD style="PADDING-LEFT: 0px" height="100%">
																		<TABLE height="100%" cellSpacing=0 cellPadding=0
																			width="100%" bgColor=#eaf2f8 border=0>
																			<TBODY>
																				<TR vAlign=top height="100%">
																					<TD>
																						<SCRIPT>initializeDocument(); menu_init_open();</SCRIPT>
																						<NOSCRIPT></NOSCRIPT>
																					</TD>
																				</TR>
																			</TBODY>
																		</TABLE>
																	</TD>
																	<TD class=tree_bg_right
																		style="BACKGROUND-IMAGE: url(images/leftm/tree_bg_right.png)"></TD>
																</TR>
															</TBODY>
														</TABLE>
													</TD>
												</TR>
											</TBODY>
										</TABLE>
									</TD>
									<TD id=hide_doc_div vAlign=top width=4
										background=images/leftm/hidden-bar.gif height="100%">
										<IMG id=show_menu_window_img onclick=show_menu_window(this);
											alt=隐藏菜单 src="images/leftm/hide-no.gif" border=0>
									</TD>
									<TD width="100%" align="center" valign="top">
										<!--右边的主页面-->
										<iframe id="rightFrame" name="rightFrame" scrolling="auto" src="${module }" frameborder="0"
										style="height: 100%; width: 100%;"></iframe> 
									</TD>
									<TD width=5 background=images/leftm/index1_border_right.gif>
										<DIV style="WIDTH: 5px"></DIV>
									</TD>
								</TR>
							</TBODY>
						</TABLE>
					</TD>
				</TR>
				<TR>
					<TD id=footer_area style="WIDTH: 100%; HEIGHT: 29px" vAlign=bottom>
						<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
							<TBODY>
								<TR>
									<TD class=x_index1_footer_left
										style="BACKGROUND-IMAGE: url(images/leftm/index1_footer_left.gif)">
										&nbsp;&nbsp;&nbsp;
										<FONT class=index1_font>当前登录：<s:property
						value="#session.username" /> </FONT>
									</TD>
									<TD class=x_index1_footer_middle
										style="BACKGROUND-IMAGE: url(images/leftm/index1_footer_center.gif)">&nbsp;
										
									</TD>
									<TD class=x_index1_footer_right
										style="BACKGROUND-IMAGE: url(images/leftm/index1_footer_right.gif)"
										vAlign="middle" id="_copyright">
									</TD>
								</TR>
							</TBODY>
						</TABLE>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
	</BODY>
</HTML>