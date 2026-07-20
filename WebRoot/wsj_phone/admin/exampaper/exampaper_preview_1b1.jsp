<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<title>试卷预览</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<LINK href="css/course_preview.css" type=text/css rel=stylesheet>
		<STYLE type=text/css>
.STYLE4 {
	FONT-SIZE: 12px
}

.jiangyi {
	PADDING-RIGHT: 8px;
	PADDING-LEFT: 8px;
	FONT-SIZE: 12px;
	PADDING-BOTTOM: 8px;
	PADDING-TOP: 8px;
	BACKGROUND-COLOR: #ffffff
}

.STYLE5 {
	COLOR: #ff0000
}

#menubox {
	BORDER-RIGHT: #26517b 0px solid;
	BORDER-TOP: #26517b 0px solid;
	BACKGROUND: #ffffff;
	MARGIN: 0px;
	BORDER-LEFT: #26517b 0px solid;
	WIDTH: 180px;
	BORDER-BOTTOM: #26517b 0px solid;
	HEIGHT: auto
}

BODY {
	MARGIN: 0px
}

.STYLE10 {
	FONT-SIZE: 12px;
	LINE-HEIGHT: 24px
}

.STYLE8 {
	FONT-WEIGHT: bold;
	FONT-SIZE: 14px
}

.STYLE11 {
	FONT-SIZE: 14px;
	COLOR: #ff0000
}
.question{
	width: 95%;
	text-align: left;
	display: none;
	margin: 0px;
}
</STYLE>

		<SCRIPT type=text/javascript>

function catalog_switch()
{
	var oTdCatalog = document.getElementById('td_catalog');
	//var oTdProcess = document.getElementById('td_process');
	var oPageFile = document.getElementById('page_file');
	var oSwitchButton = document.getElementById('switch_button');
	
	if(oTdCatalog.style.display != 'none')
	{
		oTdCatalog.style.display='none';
		//oTdProcess.style.display='none';
		oPageFile.style.display='none';
		oSwitchButton.src='images/img/yincang2.jpg';
	}
	else
	{
		oTdCatalog.style.display='';
		//oTdProcess.style.display='';
		oPageFile.style.display='';
		oSwitchButton.src='images/img/yincang.jpg';
	}
	
}
var questions = new Array() ;
	function init(){
		var objs = document.getElementsByTagName("div");
		var i = 0;
		for(var j=0;j<objs.length; j++)
		{
			if(objs[j].className=="question"){
				questions[i]=objs[j];
				i++;	
			}
		}
	}
	var now = 0;
	function showQ(j){
		for(var i = 0 ; i<questions.length;i++){
			if(j==i) questions[i].style.display="block";
			else
			questions[i].style.display="none";
		}
		now=j;
	}
	function showQN(){
		
		if(now<questions.length){
		now++;
		for(var i = 0 ; i<questions.length;i++){
			if(now==i) questions[i].style.display="block";
			else
			questions[i].style.display="none";
		}
		}
	}
	function showQP(){
		if(now>=0){
		now--;
		for(var i = 0 ; i<questions.length;i++){
			
			if((now+1)==i) questions[i].style.display="block";
			else
			questions[i].style.display="none";
		}
		}
	}
</SCRIPT>
		<META content="MSHTML 6.00.2900.5921" name=GENERATOR>
	</HEAD>
	<BODY onload="init();showQ(0)">
		<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
			<TBODY>
				<TR>
					<TD vAlign=top height=68>
						<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
							<TBODY>
								<TR>
									<TD>
										<TABLE height=68 cellSpacing=0 cellPadding=0 width="100%"
											border=0>
											<TBODY>
												<TR>
													<TD valign="middle" align="center" width=200
														background=images/img/bfz_r1_c11.jpg>
														&nbsp;
													</TD>
													<TD align="center" background=images/img/bfz_r1_c11.jpg>
														<FONT class=bt><s:property value="examPaper.title"/> </FONT>
													</TD>
												</TR>
												<TR>
													<TD background=images/img/t-5.jpg colSpan=2 height=13>
													</TD>
												</TR>
											</TBODY>
										</TABLE>
									</TD>
									<TD width=192>
										<TABLE cellSpacing=0 cellPadding=0 width=192 border=0>
											<TBODY>
												<TR>
													<TD width=192 height=47><img height=47 src="images/img/t-6.jpg" width=192 /></TD>
												</TR>
												<TR>
													<TD width=192 background=images/img/t-7.jpg height=21>
														<TABLE cellSpacing=0 cellPadding=0 width=192 border=0>
															<TBODY>
																<TR>
																	<TD width=72 height=18>
																		&nbsp;
																	</TD>
																	<TD style="FONT-SIZE: 12px" vAlign=bottom width=120>
																		<SPAN class=STYLE5>&gt;&gt;&gt; </SPAN><A
																			href=""><SPAN
																			class=STYLE5>返回学员端</SPAN> </A>
																	</TD>
																</TR>
															</TBODY>
														</TABLE>
													</TD>
												</TR>
											</TBODY>
										</TABLE>
									</TD>
								</TR>
							</TBODY>
						</TABLE>
					</TD>
				</TR>
				<TR>
					<TD vAlign=top>
						<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%"
							border=0>
							<TBODY>
								<TR><!-- #FF99FF -->
								<wysLib:epShow1b1></wysLib:epShow1b1>
								
								</TR>
								<TR>
									<TD id=td_catalog vAlign=top bgColor=#dae9fe>
										<ul class=muludiv2 id=div_catalog>
											<LI style="FONT-SIZE: 14px">
												<A style="HEIGHT: 25px"
													href=" ">  </A> 
											<LI style="FONT-SIZE: 14px">
												试卷信息：总分<s:property value="examPaper.ep_tscore" />分
											<LI style="FONT-SIZE: 14px">
										</ul>


									</TD>
								</TR>
							</TBODY>
						</TABLE>
					</TD>
				</TR>
				<TR>
					<TD vAlign=top align=middle height=28>
						<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
							<TBODY>
								<TR>
									<TD width=18><img height=28 alt="" src="images/img/bf_r14_c1.jpg"
											width=18 border=0 name=bf_r14_c1/>
									</TD>
									<TD class=unnamed1 align=middle width=267
										background=images/img/bf_r14_c3.jpg>
										<a href="#" class="style1"> </a>
									</TD>
									<TD width=48><img height=28 alt="" src="images/img/bf_r14_c15.jpg"
											width=48 border=0 name=bf_r14_c15 /></TD>
									<TD valign="middle" align=right
										background=images/img/bf_r14_c21.jpg>
										&nbsp;
									</TD>
									<TD width=17>
										<IMG height=28 alt="" src="images/img/bf_r14_c29.jpg"
											width=19 border=0 name=bf_r14_c29>
									</TD>
								</TR>
							</TBODY>
						</TABLE>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
	
	</body>
</HTML>
