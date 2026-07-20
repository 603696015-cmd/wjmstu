<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.newsandmess.entities.News"%>
<%@page import="com.sopia.common.SystemConfOp"%>
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
	<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />

		<TITLE>列表页</TITLE>
		<INPUT id=urlHead value=http type=hidden>
		<META content="text/html; charset=UTF-8" http-equiv=Content-Type>
		<LINK rel="shortcut icon" href="favicon.ico">
		<LINK rel=stylesheet type=text/css href="wsj_phone/images/gaiban2/css.css">
		<LINK rel=stylesheet type=text/css
			href="wsj_phone/images/gaiban2/jquery-cluetip.css">

		<LINK href="wsj_phone/images/gaiban2/global.css" type=text/css rel=stylesheet>
		<link href="wsj_phone/css/listlable.css" type="text/css" rel="stylesheet">


		<META name=keywords
			content="北京,卫生,法学会,中国,食品,安全,培训网">
		<META name=description
			content="北京卫生法学会,中国食品安全培训网 ">
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
.menu_bg {
	WIDTH: 100%;
	HEIGHT: 40px;
	background-color:#F3F3F3;
	background-repeat: repeat-x;
	background-position: left -100px;
}
.menu_bg LI {
	TEXT-ALIGN: center; WIDTH: 100px; BACKGROUND-REPEAT: no-repeat; BACKGROUND-POSITION: left -5px; FLOAT: left; HEIGHT: 40px
}
.menu_bg LI A {
	LINE-HEIGHT: 40px; DISPLAY: block; HEIGHT: 40px; COLOR: #000; FONT-SIZE: 14px; FONT-WEIGHT: normal; TEXT-DECORATION: none
}
.menu_bg LI A:link {
	COLOR: #000;
}
.menu_bg LI A:visited {
	COLOR: #000;
}
.menu_bg LI A.here {
	COLOR: #000;
	background-image: url(http://www.ccunc.com/images/xtb/menu_hover.gif);
	background-repeat: no-repeat;
	background-position: -5px;
}
.menu_bg LI A:hover {
	COLOR: #fff;
}
.menu_bg LI A.libg {
	BACKGROUND: url(http://www.ccunc.com/images/xtb/libg.gif) no-repeat; COLOR: #fff
}
li{ list-style:none;}
</STYLE>
		<LINK rel=stylesheet type=text/css href="wsj_phone/css/gaiban/css/basic.css">
		<LINK rel=stylesheet type=text/css
			href="wsj_phone/css/gaiban/statics/css/yp_education.css">
		<LINK rel=stylesheet type=text/css
			href="wsj_phone/css/gaiban/statics/css/bwy_style.css">
		<LINK rel=stylesheet type=text/css href="wsj_phone/images/gaiban2/joyo.css">


		<SCRIPT type=text/javascript>
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


		<link rel="stylesheet" href="wsj_phone/css/index.css" type="text/css"
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
	
			<!--<%@include file="frontheader.jsp"%>-->
			<!--<TABLE border=0 cellSpacing=0 cellPadding=0 width=1000 align=center>
				<TBODY>
					<TR>
						<TD height=5 background=images/gaiban2/img_8.jpg></TD>
					</TR>
				</TBODY>
			</TABLE>-->
            <div style=" width:100%; height:40px; line-height:40px; background-color:#00A2FC;">卫生局</div>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
			    <td><div >
			      <DIV class="menu_bg">
			        <LI> <A href="index.action">网站首页</A></LI>
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
			<table width="100%" border="0" align="left" cellpadding="0"
				cellspacing="0" bordercolor="#999999" style="margin-top: 8px;">
				<tr>
					<td valign="top">
                    <table width="320" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><%@include file="indexLogin.jsp"%></td>
  </tr>
</table>

						<TABLE style="MARGIN-TOP: 0px;" class=tdbkblue border=0
							cellSpacing=0 cellPadding=0 width="100%" align=left>
							<TBODY>
								<TR>
									<TD style="PADDING-LEFT: 40px;background-color:#00A2FC;" class=gqtitle height=42
										vAlign=center>
										<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" >
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
									<TD align="center" vAlign=top
										style="padding-bottom: 20px;">
										<TABLE cellSpacing=0 cellPadding=0 width="96%" border=0>
											<TBODY>
												<TR>
													<TD height=60 align=center bgcolor="#F8FCFE" class="yellow">
														<STRONG><s:property value="news.title" /> </STRONG>
													</TD>
												</TR>
												<TR>
													<TD background=images/gaiban2/img_26.jpg height=1></TD>
												</TR>
											</TBODY>
										</TABLE>
										<TABLE cellSpacing=1 cellPadding=0 width="96%" border=0>
											<TBODY>
												<TR>
													<TD align="left" valign="top" bgcolor="#F8FCFE">
														<p>
															创建时间：  
															<s:date name="news.releasetime"
																format="yyyy-MM-dd HH:mm:ss" />
															浏览数
															<s:property value="news.browsefor" />
														</p>
														<P style="LINE-HEIGHT: 2">
															<FONT style="FONT-SIZE: 12px">${news.content_ }</FONT>
														</P>
													</TD>
												</TR>
											</TBODY>
										</TABLE>
									</TD>
								</TR>
							</TBODY>
						</TABLE>
						<table width=96% border=0 align="left" cellpadding=0
							cellspacing=0 class=bd3>
							<tbody>
                                <tr>
                                <td><s:include value="frontbottom.jsp" /></td>
                                </tr>
							</tbody>
						</table>
					</td>
					
				</tr>
			</table>
		<!-- 	<s:include value="frontbottom.jsp" /> -->
	
	</body>
</HTML>

