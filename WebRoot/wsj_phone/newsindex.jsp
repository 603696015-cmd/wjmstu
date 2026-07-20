<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.newsandmess.entities.News"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="zdyLib" uri="/WEB-INF/zdyLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<!-- saved from url=(0045)http://221.208.198.29/gaiban0318/index.action -->
<HTML>
	<HEAD>
		<TITLE>列表页</TITLE>
		<style type="text/css">
		.STYLE11 {	color: #000000
}
.STYLE11 {	color: #000000
}
.menu_bg {
	WIDTH: 100%;
	HEIGHT: 40px;
	background-image: url(http://www.ccunc.com/images/xtb/index_x.jpg);
	background-repeat: repeat-x;
	background-position: left -100px;
}
.menu_bg LI {
	BACKGROUND-IMAGE: url(http://www.ccunc.com/images/xtb/index_x.jpg); TEXT-ALIGN: center; WIDTH: 100px; BACKGROUND-REPEAT: no-repeat; BACKGROUND-POSITION: left -5px; FLOAT: left; HEIGHT: 40px
}
.menu_bg LI A {
	LINE-HEIGHT: 40px; DISPLAY: block; HEIGHT: 40px; COLOR: #fff; FONT-SIZE: 14px; FONT-WEIGHT: normal; TEXT-DECORATION: none
}
.menu_bg LI A:link {
	COLOR: #fff
}
.menu_bg LI A:visited {
	COLOR: #fff
}
.menu_bg LI A.here {
	COLOR: #fff;
	background-image: url(http://www.ccunc.com/images/xtb/menu_hover.gif);
	background-repeat: no-repeat;
	background-position: -5px;
}
.menu_bg LI A:hover {
	BACKGROUND: url(http://www.ccunc.com/images/xtb/menu_hover.gif) no-repeat; COLOR: #fff
}
.menu_bg LI A.libg {
	BACKGROUND: url(http://www.ccunc.com/images/xtb/libg.gif) no-repeat; COLOR: #fff
}
.menu_bg LI A:hover {
	BACKGROUND: url(http://www.ccunc.com/images/xtb/menu_hover.gif) no-repeat; COLOR: #fff
}
li{ list-style:none;}
        </style>
<INPUT id=urlHead value=http type=hidden>
		<META content="text/html; charset=UTF-8" http-equiv=Content-Type>
		<LINK rel="shortcut icon" href="favicon.ico">
		<LINK rel=stylesheet type=text/css href="images/gaiban2/css.css">
		<LINK rel=stylesheet type=text/css
			href="images/gaiban2/jquery-cluetip.css">

		<LINK href="images/gaiban2/global.css" type=text/css rel=stylesheet>
		<link href="css/listlable.css" type="text/css" rel="stylesheet">


		<META name=keywords
			content="">
		<META name=description
			content=" ">
		<STYLE type=text/css>
FORM {
	PADDING-BOTTOM: 0px;
	MARGIN: 0px;
	PADDING-LEFT: 0px;
	PADDING-RIGHT: 0px;
	PADDING-TOP: 0px
}

.STYLE1 {
	color: #000000
}
</STYLE>
		<LINK rel=stylesheet type=text/css href="css/gaiban/css/basic.css">
		<LINK rel=stylesheet type=text/css
			href="css/gaiban/statics/css/yp_education.css">
		<LINK rel=stylesheet type=text/css
			href="css/gaiban/statics/css/bwy_style.css">
		<LINK rel=stylesheet type=text/css href="images/gaiban2/joyo.css">


		<SCRIPT type=text/javascript>
		
		function page(i){
			document.getElementById("pageNow").value = i;
			newslist.submit();
		}
function checkForm_soso(){
	var flag = true;
	var idcard = document.getElementById("shenfenzheng2");
	var zhengshu = document.getElementById("zhengshuhao2");
	var msg=new Array("验证通过","非法身份证号","非法地区","非法生日");
	if(idcard.value == "" && zhengshu.value == ""){
		alert("身份证号和证书号至少填写一个,请填写!");
		flag =  false;
	}else{
		if(idcard.value!=""){//身份证号填写
			var i=validateIdCard(idcard.value);//验证身份证号是否合法
			if(i>0){
				alert(msg[i]);
				flag = false;
			}
		}
		if(zhengshu.value!=""){//验证证书号
			//2013 1083 0001
			var arr = zhengshu.value.split(" ");
			if(arr.length!=3){
				alert("非法证书号");
				flag = false;
			}else{
				if(arr.length>0){
					for(var i=0;i<arr.length;i++){
						if(arr[i].length!=4){
							flag = false;
							break;
						}
					}
					if(!flag){
						alert("非法证书号");
					}
				}
			}
		}
	}
	return flag;
}
</SCRIPT>
		<script type="text/javascript">
/*
功能：验证身份证号码是否有效
提示信息：未输入或输入身份证号不正确！
使用：validateIdCard(obj)
返回：0,1,2,3
*/
function validateIdCard(obj)
{
 var aCity={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"};
  var iSum = 0;
 //var info = "";
 var strIDno = obj;
 var idCardLength = strIDno.length;
 if(!/^\d{17}(\d|x)$/i.test(strIDno)&&!/^\d{15}$/i.test(strIDno))
        return 1; //非法身份证号

 if(aCity[parseInt(strIDno.substr(0,2))]==null)
 return 2;// 非法地区

  // 15位身份证转换为18位
 if (idCardLength==15)
 {
    sBirthday = "19" + strIDno.substr(6,2) + "-" + Number(strIDno.substr(8,2)) + "-" + Number(strIDno.substr(10,2));
  var d = new Date(sBirthday.replace(/-/g,"/"))
  var dd = d.getFullYear().toString() + "-" + (d.getMonth()+1) + "-" + d.getDate();
  if(sBirthday != dd)
                return 3; //非法生日
              strIDno=strIDno.substring(0,6)+"19"+strIDno.substring(6,15);
              strIDno=strIDno+GetVerifyBit(strIDno);
 }

       // 判断是否大于2078年，小于1900年
       var year =strIDno.substring(6,10);
       if (year<1900 || year>2078 )
           return 3;//非法生日

    //18位身份证处理

   //在后面的运算中x相当于数字10,所以转换成a
    strIDno = strIDno.replace(/x$/i,"a");

  sBirthday=strIDno.substr(6,4)+"-"+Number(strIDno.substr(10,2))+"-"+Number(strIDno.substr(12,2));
  var d = new Date(sBirthday.replace(/-/g,"/"))
  if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate()))
                return 3; //非法生日
    // 身份证编码规范验证
  for(var i = 17;i>=0;i --)
   iSum += (Math.pow(2,i) % 11) * parseInt(strIDno.charAt(17 - i),11);
  if(iSum%11!=1)
                return 1;// 非法身份证号

   // 判断是否屏蔽身份证
    var words = new Array();
    words = new Array("11111119111111111","12121219121212121");

    for(var k=0;k<words.length;k++){
        if (strIDno.indexOf(words[k])!=-1){
            return 1;
        }
    }

 return 0;
}
function change(textSize){
var obj = document.getElementById('contentText1').getElementsByTagName('p');
for(var i=0,len=obj.length; i<len; i++){
obj[i].style.fontSize = textSize+'px';
}
}
</SCRIPT>

		<SCRIPT type=text/javascript>
function AddItem()
{
    $('#div1').css("display","block")
}
function AddItem2()
{
    $('#div2').css("display","block")
}
function AddItem3()
{
    $('#div3').css("display","block")
}
</SCRIPT>

		<META name=GENERATOR content="MSHTML 8.00.6001.19403">


		<link rel="stylesheet" href="css/index.css" type="text/css"
			media="screen" />
		<link rel="stylesheet" type="text/css"
			href="http://www.chinatrace.org:80/css/jquery.fancybox-1.3.4.css"
			media="screen" />
		<script type="text/javascript"
			src="http://www.chinatrace.org:80/js/jquery-1.6.2.js"></script>
		<script type="text/javascript"
			src="http://www.chinatrace.org:80/js/jquery.fancybox-1.3.4.pack.js"></script>

		<script type="text/javascript"
			src="http://www.chinatrace.org:80/js/index.js"></script>
		<script type="text/javascript"
			src="http://www.chinatrace.org:80/js/index-fancybox.js"></script>

		<script type="text/javascript">
		  function fitslogin(){
    var hrefUrl = "http://www.chinatrace.org:80/fitsLogin";
  	$("#fitslogin").attr('href',hrefUrl);
  		

} 	

function fotslogin(){
    var hrefUrl = "http://www.chinatrace.org:80/fotsLogin";
    //alert(hrefUrl);
  	$("#fotslogin").attr('href',hrefUrl);
  			

} 	
function fgmslogin(){
    var hrefUrl = "http://www.chinatrace.org:80/fgmsLogin";
    //alert(hrefUrl);
  	$("#fgmslogin").attr('href',hrefUrl);	

} 	
			function indexQueryGlnClick(){ 
			
			    var barcodeLotNo = document.getElementById('barcodeLotNo');
			    var searchType = -1; 
				var gtin = "^";
				var searchLotNo = "^";
				var searchTxt = "^";
				if(barcodeLotNo.checked) {
					searchType = 1;	
					gtin = $("#gtin").val();
					searchLotNo = $("#searchLotNo").val();
				}
				if(searchLotNo==""||searchLotNo=="^"){
					searchType = 2;	
					var url = 'http://www.chinatrace.org:80/fcqs/icoItems2?proCode='+gtin+'&searchType='+searchType;
			    	$("#barcodeQueryGln").attr('href',url);
				}else{
				var url = 'http://www.chinatrace.org:80/fcqs/traceQueryItem?proCode='+gtin+'&batchNo='+searchLotNo+'&barCode='+searchTxt+'&searchType=' + searchType;
				$("#barcodeQueryGln").attr('href',url);	
				}
			}
			
			function indexQueryBarcodeClick(){ 
			
			    var backyards = document.getElementById('backyards');	
			    var backcode = document.getElementById('backcode');
			    var searchType = -1; 
				var searchTxt = "^";
				var gtin = "^";
				var searchLotNo = "^";
				if(backyards.checked) {							
					searchType = 0;	
					searchTxt = $('#searchTxt').val();	
					var url = 'http://www.chinatrace.org:80/fcqs/traceQueryItem?proCode='+gtin+'&batchNo='+searchLotNo+'&barCode='+searchTxt+'&searchType=' + searchType;
				    $("#barcodeQueryBarcode").attr('href',url);	
				}
				
				if(backcode.checked) {
					searchType = 2; 
					searchTxt = $('#searchTxt').val();	
					var url = 'http://www.chinatrace.org:80/fcqs/icoItems2?proCode='+searchTxt+'&searchType='+searchType;
			    	$("#barcodeQueryBarcode").attr('href',url);
								
				}
			}

			function indexQuerybackClick(){ 
			   var backcode = document.getElementById('backcode');			   
			   var searchType = -1; 			  
				var back = "^";
				//alert(backyards);alert(barcodeLotNo);alert(backcode.checked);
				if(backcode.checked) {		
					back = $("#back").val();			
					searchType = 2;						
					var hrefUrl = "http://www.chinatrace.org:80/fcqs/icoItems2?proCode="+back+"&searchType=" + searchType;
			    	$("#barcodeQueryBack").attr('href',hrefUrl);			
				}
				
						
			}
			
			function traceQueryClick(){
			   var backyards = document.getElementById('backyards');
			   var searchType = -1; 
			   var searchTxt = "^";
				if(backyards.checked) {
					searchTxt = $('#searchTxt').val();
					if(searchTxt == ''){
					   alert('请输入追溯码！');					   
					   return;
					}
					searchType = 0;					
				}
				var barcodeLotNo = document.getElementById('barcodeLotNo');
				var gtin = "^";
				var searchLotNo = "^";
				if(barcodeLotNo.checked) {
					gtin = $("#gtin").val();
					searchLotNo = $("#searchLotNo").val();
					if(gtin == '' || searchLotNo == ''){
					   alert('请输入商品条码和批次！');
					   return;
					}	
					searchType = 1;				
				}
				$.postJSON(
					'fcqs/icoItemsAjax?proCode='+gtin+'&batchNo='+searchLotNo+'&barCode='+searchTxt+'&searchType=' + searchType,
					$("#icoItems").serializeObject(),
					function(data) {
						if (data && data.success == "true") {
						    if(data.queryType && data.queryType == "other"){
						        $("#crystal").html("");
								if(!$("#icoItems").hasClass('divDisplay')){
			       					toggleObjectDisplayNoneClass($("#icoItems"), "divDisplay");
			    				}
			    				$("#crystalOther").html("");
								if($("#icoItemsOther").hasClass('divDisplay')){
			       					toggleObjectDisplayNoneClass($("#icoItemsOther"), "divDisplay");
			    				}
			    				icoItemloadOther(data);
						        return;
						    }
						    $("#crystalOther").html("");
							if(!$("#icoItemsOther").hasClass('divDisplay')){
			       				toggleObjectDisplayNoneClass($("#icoItemsOther"), "divDisplay");
			    			}
						    $("#crystal").html("");
							if($("#icoItems").hasClass('divDisplay')){
			       				toggleObjectDisplayNoneClass($("#icoItems"), "divDisplay");
			    			}
			    		//alert(data.companygln);
			    			var companygln = data.companygln;
			    			var proCode = 	data.proCode;
			    			var batchNo =	data.batchNo;	    
			    			icoItemload(companygln,proCode,batchNo);
						} else {
						    $("#crystal").html("");
							alert('没有找到您要的数据，请重新输入追溯码或商品条码及批次号！');
						}
					}
			    );
			}
		</script>

	</HEAD>
	<BODY>

			<table width="320" border="0" cellspacing="0" cellpadding="0">
			  <tr>
			    <td><img src="images/w.jpg" alt="" width="320" height="100" /></td>
		      </tr>
			  <tr>
			    <td><div class="menu_bg">
			      <DIV>
			        <LI> <A href="index.action">网站首页</A> </LI>
			        <LI> <A href="newsIndex.action">新闻首页</A> </LI>
			        <LI> <A href="forumIndex.action">论坛首页</A> </LI>
		          </DIV>
			      <div class="menu_bg" style="float:left;">
			        <LI> <A href="knowledge_center_list.action">知识库首页</A> </LI>
			        <LI> <A href="forum_courseclub.action">选课中心</A> </LI>
			        <LI> <A href="forum_classclub.action">选班中心</A> </LI>
		          </div>
			      <div class="menu_bg" style="float:left;">
			        <LI> <A href="examRoomShoppping.action">考场中心</A> </LI>
			        <LI> <A href="cisco_user_center.action">个人中心</A> </LI>
			        <LI> <A href="map.action">网站地图</A> </LI>
		          </DIV>
			      </div></td>
		      </tr>
    </table>
			<table width="320" border="0" cellpadding="0" cellspacing="0" bgcolor="#E3F6FD">
			  <tr>
			    <td ><%@include file="indexLogin.jsp"%></td>
		      </tr>
			<!--  <tr>
			    <td  class=gqtitle height=42 style="PADDING-LEFT: 40px;"
										vAlign=center background=images/gaiban2/tbbg003.gif><TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 >
			      <TBODY>
			        <TR>
			          <TD> 证书查询 </TD>
			          <TD width=70 align=middle><A
															href="http://www.sopia.cc:8081/demo/newsIndex.action?&amp;news.ntype.id=0&amp;ntype.id=12"
															target=_blank></A></TD>
		            </TR>
		          </TBODY>
			      </TABLE></td>
		      </tr>-->
			  <tr>
			    <td><TABLE width=320 border=0 align="center" cellPadding=0
											cellSpacing=0 style="margin-top: 3px;">
			      <TBODY>
			        <TR>
			          <TD align=center valign="top" class=bai><!--<FORM id=soso2 method=post name=soso
															action="frontZhengshuSearch.action"
															onSubmit="return checkForm_soso();">
			            <TABLE width=210 height="60" border=0 align="center"
																cellPadding=0 cellSpacing=1 bgcolor="#FFFFFF">
			              <TR>
			                <TD width=53 align=right bgcolor="#E3F6FD"><span class="STYLE11">身份证：</span></TD>
			                <TD align=center vAlign=center bgcolor="#E3F6FD"><INPUT id=shenfenzheng class=db2 size=15
																			name=shenfenzheng></TD>
		                  </TR>
			              <TR>
			                <TD width=53 align=right bgcolor="#E3F6FD" class=bai><span class="STYLE11">证书号：</span></TD>
			                <TD height=24 align=center vAlign=center
																		bgcolor="#E3F6FD"><INPUT id=zhengshuhao class=db2 size=15
																			name=zhengshuhao></TD>
		                  </TR>
		                </TABLE>
			            <p>
			              <input type="submit" name="Submit2" value="点击查询">
		                </p>
			            </FORM>--></TD>
		            </TR>
		          </TBODY>
			      </TABLE></td>
		      </tr>
			  <tr>
			    <td><TABLE width="100%" border=0  align=center cellPadding=0
										cellSpacing=0 class=tdbkblue style="MARGIN-TOP: 0px">
			      <TBODY>
			        <TR>
			          <TD style="PADDING-LEFT: 40px" class=gqtitle height=42
													vAlign=center background=images/gaiban2/tbbg002.jpg><TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
			            <TBODY>
			              <TR>
			                <TD>新闻热点</TD>
			                <TD width=70 align=middle><A
															href="http://www.sopia.cc:8081/demo/newsIndex.action?&amp;news.ntype.id=0&amp;ntype.id=12"
															target=_blank></A></TD>
		                  </TR>
		                </TBODY>
		              </TABLE></TD>
		            </TR>
		          </TBODY>
			      </TABLE></td>
		      </tr>
			  <tr>
			    <td><form action="newsIndex.action" name="newslist" method="post">
										<s:hidden name="pN" id="pageNow"></s:hidden>
										<s:hidden name="pS" ></s:hidden>
										<TABLE cellSpacing=0 cellPadding=0 width="96%" align=center
											border=0>
											<TBODY>
												<s:if test="zxNews.size==0">
													<br>
													<br>目前没有<s:property value="news.ntype.name" />栏目相关新闻或公告<br>
													<br>
												</s:if>
												<s:else>

												</s:else>
												<s:iterator value="zxNews">
													<TR>
														<TD style="COLOR: #254142" align=middle width="4%"
															height=35>
															·
														</TD>
													<TD align=left width="77%">
														<s:if test="modelstatus!=0">
															<A class=news
																href="newsIndexView.html?news.id=<s:property value="id"/>&ntype.id=<s:property value="ntype.id"/>"><s:property
																	value="title" />
															</A>
														</s:if>
														<s:else>
															<A class=news
																href="newsIndexView.action?news.id=<s:property value="id"/>&ntype.id=<s:property value="ntype.id"/>"><s:property
																	value="title" />
															</A>
														</s:else>	
														</TD>	
														<TD align=left width="19%">
															<s:date name="releasetime" format="yyyy-MM-dd" />
														</TD>
													</TR>
													<TR>
														<TD background=images/gaiban2/img_26.jpg colSpan=3
															height=1></TD>
													</TR>
												</s:iterator>
											</TBODY>
										</TABLE>
										</form>
										<wysLib:page_cisco></wysLib:page_cisco></td>
		      </tr>
			  <tr>
			    <td><img src="images/gaiban2/img_24.jpg" alt="" width=320 height=3></td>
		      </tr>
              <tr>
              <td><s:include value="frontbottom.jsp" /></td>
              </tr>
    </table>
<!--			<s:include value="frontbottom.jsp" />-->
	</BODY>
</HTML>
<!--<TABLE border=0 cellSpacing=0 cellPadding=0 width=1000 align=center>
				<TBODY>
					<TR>
						<TD height=5 background=images/gaiban2/img_8.jpg></TD>
					</TR>
				</TBODY>
			</TABLE>
			<table width="990" border="0" align="center" cellpadding="0"
				cellspacing="0" bordercolor="#999999" style="margin-top: 8px;">
				<tr>
					<td valign="top">
						<TABLE style="MARGIN-TOP: 0px" class=tdbkblue border=0
							cellSpacing=0 cellPadding=0 width="96%" align=center>
							<TBODY>
								<TR>
									<TD style="PADDING-LEFT: 40px" class=gqtitle height=42
										vAlign=center background=images/gaiban2/tbbg002.jpg>
										<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
											<TBODY>
												<TR>
													<TD>
														学员登录
													</TD>
													<TD width=70 align=middle>
														<A
															href="http://www.sopia.cc:8081/demo/newsIndex.action?&amp;news.ntype.id=0&amp;ntype.id=12"
															target=_blank></A>
													</TD>
												</TR>
											</TBODY>
										</TABLE>
									</TD>
								</TR>
								<TR>
									<TD height=570 align="center" vAlign=top
										style="padding-top: 20px; padding-bottom: 20px;">
										<form action="newsIndex.action" name="newslist" method="post">
										<s:hidden name="pN" id="pageNow"></s:hidden>
										<s:hidden name="pS" ></s:hidden>
										<TABLE cellSpacing=0 cellPadding=0 width="96%" align=center
											border=0>
											<TBODY>
												<s:if test="zxNews.size==0">
													<br>
													<br>目前没有<s:property value="news.ntype.name" />栏目相关新闻或公告<br>
													<br>
												</s:if>
												<s:else>

												</s:else>
												<s:iterator value="zxNews">
													<TR>
														<TD style="COLOR: #254142" align=middle width="4%"
															height=35>
															·
														</TD>
													<TD align=left width="77%">
														<s:if test="modelstatus!=0">
															<A class=news
																href="newsIndexView.html?news.id=<s:property value="id"/>&ntype.id=<s:property value="ntype.id"/>"><s:property
																	value="title" />
															</A>
														</s:if>
														<s:else>
															<A class=news
																href="newsIndexView.action?news.id=<s:property value="id"/>&ntype.id=<s:property value="ntype.id"/>"><s:property
																	value="title" />
															</A>
														</s:else>	
														</TD>	
														<TD align=left width="19%">
															<s:date name="releasetime" format="yyyy-MM-dd" />
														</TD>
													</TR>
													<TR>
														<TD background=images/gaiban2/img_26.jpg colSpan=3
															height=1></TD>
													</TR>
												</s:iterator>
											</TBODY>
										</TABLE>
										</form>
										<wysLib:page_cisco></wysLib:page_cisco>
								</TR>
							</TBODY>
						</TABLE>
						<table width=96% border=0 align="center" cellpadding=0
							cellspacing=0 class=bd3>
							<tbody>
								<tr>
									<td valign=top>
										<img src="images/gaiban2/img_24.jpg" width=100% height=3>
									</td>
								</tr>
							</tbody>
						</table>
					</td>
					<td width="235" valign="top">
						<TABLE style="MARGIN-TOP: 0px" class=tdbkblue border=0
							cellSpacing=0 cellPadding=0 width="100%" align=center>
							<TBODY>
								<TR>
									<TD style="PADDING-LEFT: 40px" class=gqtitle height=42
										vAlign=center background=images/gaiban2/tbbg003.gif>
										<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
											<TBODY>
												<TR>
													<TD>
														学员登录
													</TD>
													<TD width=70 align=middle>
														<A
															href="http://www.sopia.cc:8081/demo/newsIndex.action?&amp;news.ntype.id=0&amp;ntype.id=12"
															target=_blank></A>
													</TD>
												</TR>
											</TBODY>
										</TABLE>
									</TD>
								</TR>
								<TR>
									<TD height=145 align="center" vAlign=middle bgcolor="#E3F6FD">
									
									</TD>
								</TR>
							</TBODY>
						</TABLE>
						<table width=235 border=0 align="center" cellpadding=0
							cellspacing=0 class=bd3>
							<tbody>
								<tr>
									<td valign=top>
										<img src="images/gaiban2/img_24.jpg" width=235 height=3>
									</td>
								</tr>
							</tbody>
						</table>
						<TABLE width=230 border=0 align="center" cellPadding=0
							cellSpacing=0 style="margin-top: 3px;">
							<TBODY>
								<TR>
									<TD height="75" align=center bgcolor="#D5F1FD" class=bai>
										<img src="images/nianjian.jpg" width="230" height="72">
									</TD>
								</TR>
							</TBODY>
						</TABLE>
						<TABLE width="100%" border=0 align=center cellPadding=0
							cellSpacing=0 bgcolor="#E3F6FD" class=tdbkblue
							style="MARGIN-TOP: 8px">
							<TBODY>
								<TR>
									<TD style="PADDING-LEFT: 40px" class=gqtitle height=42
										vAlign=center background=images/gaiban2/tbbg003.gif>
										<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
											<TBODY>
												<TR>
													<TD>
														证书查询
													</TD>
													<TD width=70 align=middle>
														<A
															href="http://www.sopia.cc:8081/demo/newsIndex.action?&amp;news.ntype.id=0&amp;ntype.id=12"
															target=_blank></A>
													</TD>
												</TR>
											</TBODY>
										</TABLE>
									</TD>
								</TR>
								<TR>
									<TD vAlign=top>
										<TABLE width=230 border=0 align="center" cellPadding=0
											cellSpacing=0 style="margin-top: 3px;">
											<TBODY>
												<TR>
													<TD align=center valign="top" class=bai>
														<FORM id=soso method=post name=soso
															action="frontZhengshuSearch.action"
															onSubmit="return checkForm_soso();">
															<TABLE width=210 height="60" border=0 align="center"
																cellPadding=0 cellSpacing=1 bgcolor="#FFFFFF">



																<TR>
																	<TD width=53 align=right bgcolor="#E3F6FD">
																		<span class="STYLE1">身份证：</span>
																	</TD>
																	<TD align=center vAlign=center bgcolor="#E3F6FD">
																		<INPUT id=shenfenzheng2 class=db2 size=15
																			name=shenfenzheng2>
																	</TD>
																</TR>

																<TR>
																	<TD width=53 align=right bgcolor="#E3F6FD" class=bai>
																		<span class="STYLE1">证书号：</span>
																	</TD>
																	<TD height=24 align=center vAlign=center
																		bgcolor="#E3F6FD">
																		<INPUT id=zhengshuhao2 class=db2 size=15
																			name=zhengshuhao2>
																	</TD>
																</TR>

															</TABLE>
															<p>

																<input type="submit" name="Submit" value="点击查询">
															</p>
														</FORM>
													</TD>
												</TR>
											</TBODY>
										</TABLE>
									</TD>
								</TR>
							</TBODY>
						</TABLE>
						<table width=235 border=0 align="center" cellpadding=0
							cellspacing=0 class=bd3>
							<tbody>
								<tr>
									<td valign=top>
										<img src="images/gaiban2/img_24.jpg" width=235 height=3>
									</td>
								</tr>
							</tbody>
						</table>
						<TABLE width=100% border=0 align="center" cellPadding=0
							cellSpacing=0>
							<TBODY>
								<TR>
									<TD width=10>
										<IMG src="images/gaiban2/img_21.jpg" width=10 height=30>
									</TD>
									<TD background=images/gaiban2/img_22.jpg width=20 align=left>
										<IMG src="images/gaiban2/img_17.jpg" width=13 height=14>
									</TD>
									<TD class=hui background=images/gaiban2/img_22.jpg align=left>
										资料下载
									</TD>
									<TD background=images/gaiban2/img_22.jpg align=right>
										<A class=more href="newsIndex.action?ntype.id=14">更多&gt;&gt;</A>
									</TD>
									<TD width=10>
										<IMG src="images/gaiban2/img_23.jpg" width=10 height=30>
									</TD>
								</TR>
							</TBODY>
						</TABLE>
						<TABLE width=100% border=0 align="center" cellPadding=0
							cellSpacing=0 bgColor=#81d1da class=bd5>
							<TBODY>
								<TR>
									<TD bgColor=#fbbb01 height=3 width=93></TD>
									<TD bgColor=#81d1da width=323></TD>
								</TR>
							</TBODY>
						</TABLE>
						<TABLE width=100% height=163 border=0 align="center" cellPadding=0
							cellSpacing=0 class=bd4>
							<TBODY>
								<TR>
									<TD align=left>
										<TABLE border=0 cellSpacing=0 cellPadding=0 width="98%">
											<TBODY>
												<TR>
													<TD height=140>
														<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
															<TBODY>
																<zdyLib:zdyloop lablename='5' xunhuan='' setnull='暂无数据'
																	switches='' include='' constraint='true'></zdyLib:zdyloop>
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
						<TABLE width=235 border=0 align="center" cellPadding=0
							cellSpacing=0 class=bd3>
							<TBODY>
								<TR>
									<TD vAlign=top>
										<IMG src="images/gaiban2/img_24.jpg" width=235 height=3>
									</TD>
								</TR>
							</TBODY>
						</TABLE>
					</td>
				</tr>
			</table>-->