<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="newversionLib" uri="/WEB-INF/newversionLib.tld"%>
<%@taglib prefix="zdyLib" uri="/WEB-INF/zdyLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<!-- saved from url=(0045)http://221.208.198.29/gaiban0318/index.action -->
<html xmlns="http://www.w3.org/1999/xhtml">
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<TITLE>中国食品安全培训网</TITLE>
		<INPUT id=urlHead value=http type=hidden>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/jquery.validate.js"></script>
		
		<META content="text/html; charset=UTF-8" http-equiv=Content-Type>
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
<SCRIPT LANGUAGE="JavaScript" src=http://float2006.tq.cn/floatcard?adminid=9511441&sort=0 ></SCRIPT>
<script type="text/javascript"  >
var UA = navigator.userAgent.toLowerCase();
var browerKernel = {
isTrident : function(){
if(/trident/i.test(UA)) return true;
else return false;
},
isWebkit : function(){
if(/webkit/i.test(UA)) return true;
else return false;
},
isGecko : function(){
if(/gecko/i.test(UA)) return true;
else return false;
},
isPresto : function(){
if(/presto/i.test(UA)) return true;
else return false;
},
isWebCore : function(){
if(/webcore/i.test(UA)) return true;
else return false;
}
}

//测试
if(browerKernel.isWebkit()){
	//alert("您当前使用的是360浏览器的极速模式或者谷歌浏览器，为了保证您能正常使用系统的各项功能，请您将360浏览器的模式改为“兼容模式”或者使用IE浏览器访问!弹出的窗口是针对360浏览器的设置!");
	//window.open(ff + "images/webkit.png","提示",'width=400,height=400');
	 var ff = "<%=basePath%>" ;
	 width=400;
	 height=400;
  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
	 var rv =  window.showModalDialog(ff + "images/webkit.png",null,sFeature);
	
}
</script>
		<SCRIPT type=text/javascript>
		
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
			var arr;
			for(int i=0;i<zhengshu.value.length()/4;i++){
				array[i] =zhengshu.substring(i*4,(i+1)*4);
				
			}
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
					//if(!flag){
					//	alert("非法证书号");
					//}
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
</script>


		<META name=GENERATOR content="MSHTML 8.00.6001.19403">
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
		<DIV id=container>
			<%@include file="frontheader.jsp"%>
			
			<TABLE border=0 cellSpacing=0 cellPadding=0 width=1000 align=center>
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
						<table border="0" align="center" cellpadding="0" cellspacing="0"
							bordercolor="#999999" style="margin-top: 0px;">
							<tr>
								<td width="326" valign="top">
									<TABLE style="MARGIN-TOP: 0px" class=tdbkblue border=0
										cellSpacing=0 cellPadding=0 width="100%" align=center>
                                      <TBODY>
                                        <TR>
                                          <TD style="PADDING-LEFT: 40px" class=gqtitle height=42
													vAlign=center background=images/gaiban2/tbbg002.jpg><TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
                                              <TBODY>
                                                <TR>
                                                  <TD> 行业动态 </TD>
                                                  <TD width=70 align=middle><A
																		href="newsIndex.action?ntype.id=11"><IMG border=0
																			src="images/gaiban2/more_1.gif" width=34 height=7> </A> </TD>
                                                </TR>
                                              </TBODY>
                                          </TABLE></TD>
                                        </TR>
                                      </TBODY>
                                    </TABLE>
									<TABLE class=tdbkblue2 border=0 cellSpacing=0 cellPadding=0
										width="324" align=center>
										<!--<TR>
											<TD height=218 vAlign=top>
												<SCRIPT src="elfrontimages/sohuflash_1.js" type=text/javascript></SCRIPT> 
					<DIV id=flashcontent01></DIV> 
					<SCRIPT type=text/javascript>
					var focus_width=324;
					var focus_height=218;
					var text_height=0;
					//var pics='http://demo.kesion.com/UploadFiles/2011-08/admin/20110819170448734.jpg|http://img1.gtimg.com/news/pics/hv1/65/179/837/54471635.jpg|http://img1.gtimg.com/news/pics/hv1/95/253/836/54425510.jpg|http://demo.kesion.com/UploadFiles/2011-08/admin/20110811170446229.jpg|http://demo.kesion.com/UploadFiles/2011-08/admin/20110811170442326.jpg';
					//var links=escape('/Item/Show.asp?m=1&d=993|/Item/Show.asp?m=1&d=938|/Item/Show.asp?m=1&d=936|/Item/Show.asp?m=1&d=934|/Item/Show.asp?m=1&d=927');
					//var texts='广州16万立方米重金属污染土|韩电视台天气预报主持人着装|卡扎菲儿子现身澄清被炸死传|辽宁部分地区发生皮肤炭疽传|江西吉安发生大面积山体滑坡';
					var speed = 4000;
					var pics="";
					var links="";
					var texts="";
					<s:iterator value="zxNews">
					pics += "<s:property value="mainimg_" escape="false"/>|";
					links += "newsIndexView.action?news.id=<s:property value="id"/>|";
					texts += "<s:property value="title"/>|";
					</s:iterator>	
					pics= pics.substring(0,pics.length-1);
					links= links.substring(0,links.length-1);
					texts= texts.substring(0,texts.length-1);
					var sohuFlash2 = new sohuFlash("elfrontimages/focus0414a.swf","flashcontent01",focus_width,focus_height+text_height,'pics='+pics+'&links='+links+'&texts='+texts+'&borderwidth='+focus_width+'&borderheight='+focus_height+'&textheight='+text_height,"#ffffff");
					sohuFlash2.addParam("quality", "medium");
					sohuFlash2.addParam("wmode", "opaque");
					sohuFlash2.addVariable("speed",speed);
					sohuFlash2.addVariable("p",pics);	
					sohuFlash2.addVariable("l",links);
					sohuFlash2.addVariable("icon",texts);
					sohuFlash2.write("flashcontent01");
					</SCRIPT> 
					

					<SCRIPT language=JavaScript> 
					function setTab(name,cursel,n){
						for(i=1;i<=n;i++){
						   var menu=document.getElementById(name+i);
						   var con=document.getElementById("con_"+name+"_"+i);
						   menu.className=i==cursel?"hover":"";
						   con.style.display=i==cursel?"block":"none";
						}
					}
					
var focus_width=324;
var focus_height=218;
var text_height=0;
//var pics='images/icon_1.gif|images/icon_2.gif|images/icon_3.gif|images/2010042709434050398.jpg|images/2010042709420423947.jpg|images/2010042709405773981.jpg';
//var links='/jrnd/ndyw/31775.html|/jrnd/ndyw/31774.html|/jrnd/bmdt/31759.html|/jrnd/ndyw/31688.html|/jrnd/xsdt/31687.html|/jrnd/xsdt/31686.html';
//var texts='电博会户外宣传渐入高潮|宁德市中小学幼儿园拉起“安保大闸”|宁德开展家庭小药箱及过期药品清理回收活动|为玉树灾区筹集善款|屏南百万尾鱼苗放流增殖|“一环、一纵、三横”路网大框架托起东侨新';
var pics="";
var links="";
var texts="";
<s:iterator value="zxNews">
pics += "<s:property value="mainimg_" escape="false"/>|";
links += "newsIndexView.action?news.id=<s:property value="id"/>|";
texts += "<s:property value="title"/>|";
</s:iterator>	
pics= pics.substring(0,pics.length-1);
links= links.substring(0,links.length-1);
texts= texts.substring(0,texts.length-1);
LoadFlash('elfrontimages/Slideviewer.swf','transparent',focus_width,focus_height+text_height,'pics='+pics+'&links='+links+'&texts='+texts+'&borderwidth='+focus_width+'&borderheight='+focus_height+'&textheight='+text_height)
</SCRIPT>
											</TD>
										</TR>-->
										<TR>
											<TD height=100 vAlign=top>
												<TABLE style="margin-bottom: 6px;" width="96%" border=0
													align="center" cellPadding=0 cellSpacing=0>
													<TBODY>
														<zdyLib:zdyloop lablename='1'  xunhuan='5' setnull='暂无数据' switches='' include=''  constraint='true'    ></zdyLib:zdyloop>
													</TBODY>
												</TABLE>
											</TD>
										</TR>
									</TABLE>
									<table width=324 border=0 align="center" cellpadding=0
										cellspacing=0 class=bd3>
										<tbody>
											<tr>
												<td valign=top>
													<img src="images/gaiban2/img_24.jpg" width=324 height=3>
												</td>
											</tr>
										</tbody>
									</table>
							  </td>
								<td width="428" valign="top">
									<TABLE style="MARGIN-TOP: 0px" class=tdbkblue border=0
										cellSpacing=0 cellPadding=0 width="416" align=center>
										<TBODY>
											<TR>
												<TD style="PADDING-LEFT: 40px" class=gqtitle height=42
													vAlign=center background=images/gaiban2/tbbg002.jpg>
													<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
														<TBODY>
															<TR>
																<TD>
																	热点新闻
																</TD>
																<TD width=70 align=middle>
																	<A
																		href="newsIndex.action?ntype.id=11"><IMG border=0
																			src="images/gaiban2/more_1.gif" width=34 height=7>
																	</A>
																</TD>
															</TR>
														</TBODY>
													</TABLE>
												</TD>
											</TR>
										</TBODY>
									</TABLE>
									<table width=416 height=170 border=0 align="center"
										cellpadding=0 cellspacing=0 class=bd4>
										<tbody>
											<tr>
												<td align=left>
													<table border=0 cellspacing=0 cellpadding=0 width="98%">
														<tbody>
															<tr>
																<td valign=top>
																	<table border=0 cellspacing=0 cellpadding=0 width="98%"
																		align=right>
																		<tbody>
																			<zdyLib:zdyloop lablename='一条热点新闻cisco'  xunhuan='' setnull='暂无数据' switches='' include=''  constraint='true'    ></zdyLib:zdyloop>
																			
																		</tbody>
																	</table>
																</td>
															</tr>
														</tbody>
													</table>
													<table style="margin-bottom: 9px; margin-top: 9px;"
														border=0 cellspacing=0 cellpadding=0 width="98%">
														<tbody>
															<tr>
																<td height=1 background=images/gaiban2/img_26.jpg
																	colspan=3></td>
															</tr>
															<tr>
																<td height=140>
																	<table border=0 cellspacing=0 cellpadding=0
																		width="100%">
																		<tbody>

																			<zdyLib:zdyloop lablename='2'  xunhuan='5' setnull='暂无数据' switches='' include=''  constraint='true'    ></zdyLib:zdyloop>

																		</tbody>
																	</table>
																</td>
															</tr>
														</tbody>
													</table>
												</td>
											</tr>
										</tbody>
									</table>
									<table width=416 border=0 align="center" cellpadding=0
										cellspacing=0 class=bd3>
										<tbody>
											<tr>
												<td valign=top>
													<img src="images/gaiban2/img_24.jpg" width=416 height=3>
												</td>
											</tr>
										</tbody>
									</table>
								</td>
							</tr>
						</table>
						<table width="100%" border="0" align="right" cellpadding="0"
							cellspacing="0" style="margin-bottom: 5px; margin-top: 0px;">
							<tr>
								<td align="left">
									 <img src="images/step.jpg" width="749" height="57" usemap="#Map"/> 
									<!--<s:if test="step==1">
										<img src="images/step.jpg" width="749" height="57" usemap="#Map"/>
									</s:if>
									<s:elseif test="step == 2">
										<img src="images/step.jpg" width="749" height="57" usemap="#Map"/>
									</s:elseif>
									<s:elseif test="step == 3">
										<img src="images/step.jpg" width="749" height="57" usemap="#Map"/>
									</s:elseif>
									<s:elseif test="step == 4">
										<img src="images/step.jpg" width="749" height="57" usemap="#Map"/>
									</s:elseif>
									<s:else></s:else>-->
								</td>
							</tr>
						</table>
					</td>
					<td width="235" valign="top">
						<TABLE style="MARGIN-TOP: 0px" class=tdbkblue border=0
							cellSpacing=0 cellPadding=0 width="100%" align=center>
							<TBODY>
								
								<TR>
									<TD height=145 align="center" vAlign=middle bgcolor="#E3F6FD">
										<%@include file="indexLogin.jsp"%>									</TD>
								</TR>
							</TBODY>
						</TABLE>
						<TABLE width=230 border=0 align="center" cellPadding=0
							cellSpacing=0 style="margin-top: 3px;">
							<TBODY>
								<TR>
									<TD height="75" align=center bgcolor="#D5F1FD" class=bai>
										<img
												src="images/nianjian.jpg" width="230" height="72">
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
					</td>
				</tr>
			</table>
			<table width="990" border="0" align="center" cellpadding="0"
				cellspacing="0" bordercolor="#999999" style="margin-top: 8px;">
				<tr>
					<td valign="top">
						<TABLE width=324 border=0 align="center" cellPadding=0
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
										食品安全
									</TD>
									<TD background=images/gaiban2/img_22.jpg align=right>
										<A class=more
											href="newsIndex.action?ntype.id=12">更多&gt;&gt;</A>
									</TD>
									<TD width=10>
										<IMG src="images/gaiban2/img_23.jpg" width=10 height=30>
									</TD>
								</TR>
							</TBODY>
						</TABLE>
						<TABLE width=324 border=0 align="center" cellPadding=0
							cellSpacing=0 bgColor=#81d1da class=bd5>
							<TBODY>
								<TR>
									<TD bgColor=#fbbb01 height=3 width=93></TD>
									<TD bgColor=#81d1da width=323></TD>
								</TR>
							</TBODY>
						</TABLE>
						<TABLE width=324 height=163 border=0 align="center" cellPadding=0
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
																<zdyLib:zdyloop lablename='3'  xunhuan='' setnull='暂无数据' switches='' include=''  constraint='true'    ></zdyLib:zdyloop>
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
						<TABLE width=324 border=0 align="center" cellPadding=0
							cellSpacing=0 class=bd3>
							<TBODY>
								<TR>
									<TD vAlign=top>
										<IMG src="images/gaiban2/img_24.jpg" width=324 height=3>
									</TD>
								</TR>
							</TBODY>
						</TABLE>
					</td>
					<td width="428" valign="top">
						<TABLE width=416 border=0 align="center" cellPadding=0
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
										新闻公告
									</TD>
									<TD background=images/gaiban2/img_22.jpg align=right>
										<A class=more
											href="newsIndex.action?ntype.id=13">更多&gt;&gt;</A>
									</TD>
									<TD width=10>
										<IMG src="images/gaiban2/img_23.jpg" width=10 height=30>
									</TD>
								</TR>
							</TBODY>
						</TABLE>
						<TABLE width=416 border=0 align="center" cellPadding=0
							cellSpacing=0 bgColor=#81d1da class=bd5>
							<TBODY>
								<TR>
									<TD bgColor=#fbbb01 height=3 width=93></TD>
									<TD bgColor=#81d1da width=323></TD>
								</TR>
							</TBODY>
						</TABLE>
						<TABLE width=416 height=163 border=0 align="center" cellPadding=0
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
																<zdyLib:zdyloop lablename='4'  xunhuan='' setnull='暂无数据' switches='' include=''  constraint='true'    ></zdyLib:zdyloop>
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
						<TABLE width=416 border=0 align="center" cellPadding=0
							cellSpacing=0 class=bd3>
							<TBODY>
								<TR>
									<TD vAlign=top>
										<IMG src="images/gaiban2/img_24.jpg" width=416 height=3>
									</TD>
								</TR>
							</TBODY>
						</TABLE>
					</td>
					<td width="235" valign="top">
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
										<A class=more
											href="newsIndex.action?ntype.id=14">更多&gt;&gt;</A>
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
																<zdyLib:zdyloop lablename='5'  xunhuan='' setnull='暂无数据' switches='' include=''  constraint='true'    ></zdyLib:zdyloop>
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
			</table>
			<table align="center" width="1000" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>

						<form name="form1">
							<!-- 最外层div -->
							<div id="div_bg">
								<!-- bg1背景头部 -->
								<div id="div_bg1">
									<!-- 内部控制标签 -->
									<div id="page">
										<!-- 头部控制 -->
										<!-- 头部结束 -->
										<!-- 头部结束 -->
										<!-- 水平线 -->
										<div id="div_hr"></div>
										<!-- 商标 -->
										<div style="width: 1px; height: 5px;"></div>
										<!-- 条码查询 -->
										<div id="div_code">
											<div class="yellow" id="code_1">
												食品追溯公共查询平台
											</div>
											<!-- 追溯码查询+商品条码查询 -->
											<div id="show1" class="code_2">
												<div id="code_2_1"></div>
												<div class="code_font">
													<label id="code_font">
														商品条码：
													</label>
												</div>
												<div class="code_2_2">
													<div id="code_input">
														<input name="searchTxt" value="" type="text" class="input"
															id="searchTxt" />
													</div>
												</div>
												<div class="code_2_3">
													<a id="barcodeQueryBarcode" style="margin-left: 10px;"
														href="#" onFocus="this.blur()"
														onclick="indexQueryBarcodeClick()"><img alt=""
															border="0"
															src="http://www.chinatrace.org:80/index_images/code_btn1.png" />
													</a>
												</div>
												<div class="code_2_4">
													<!--<a id="exampletwo" href="http://www.chinatrace.org:80/images/search_tm.gif"  onfocus="this.blur()"
										class="code_sili">帮助</a>-->
												</div>
											</div>
											<!-- 条码+批次码查询 -->
											<div id="show2" class="code_pc">
												<div id="code_pc_br"></div>
												<div class="code_font">
													商品条码：
												</div>
												<div class="code_pc1">
													<div id="code_ipt_pc1">
														<input name="gtin" id="gtin" type="text"
															value="6901234567892" class="input_ipc1" />
													</div>
												</div>
												<div class="code_font">
													批次号：
												</div>
												<div class="code_pc2">
													<div id="code_ipt_pc2">
														<input name="searchLotNo" type="text" class="input_ipc2"
															id="searchLotNo" value="20120918" />
													</div>
												</div>
												<div class="code_2_3">
													<a id="barcodeQueryGln" style="margin-left: 10px;" href="#"
														onfocus="this.blur()" onClick="indexQueryGlnClick()"><img
															alt="" border="0"
															src="http://www.chinatrace.org:80/index_images/code_btn1.png" />
													</a>
												</div>
												<div class="code_2_4">
													<!--<a id="example" href="http://www.chinatrace.org:80/images/search_tm.gif"  onfocus="this.blur()"
										class="code_sili">示例？</a>-->
												</div>
											</div>

											<div id="code_3">

												<input name="rd_code" type="radio" value="商品条码"
													id="backcode" onFocus="this.blur()" checked="checked"
													onclick="javascript:getFont(2)" />
												<label for="backcode">
													商品条码
												</label>

												<input name="rd_code" type="radio" value="条码+批次号"
													onFocus="this.blur()" id="barcodeLotNo"
													onClick="javascript:getFont(3)" />
												<label for="barcodeLotNo">
													商品条码+批次号&nbsp;&nbsp;
												</label>

												<input name="rd_code" type="radio" value="追溯码"
													id="backyards" onFocus="this.blur()"
													onclick="javascript:getFont(1)" />
												<label for="backyards">
													追溯码&nbsp;&nbsp;
												</label>
											</div>
										</div>

									</div>
									<!-- bg1背景头部结束 -->
									<!-- bg2背景中部  -->
									<!-- bg2背景中部结束 -->

								</div>
							</div>
							<!-- 最外层div结束 -->
						</form>

					</td>
				</tr>
			</table>
			<s:include value="frontbottom.jsp" />
			
		
		
		<map name="Map" id="Map">
			<s:if test="isBuyNianjianClass==0">
				<!-- 选班中心列表页 -->
				<s:if test="isChangeElclass == 0" >
				<area shape="rect" coords="5,3,170,46"
				href="forum_getAllclass.action?&isCorrespond=0&cltype.id=1"
				target="_self" />
				</s:if>
				<s:else>
					<area shape="rect" coords="5,3,170,46"
					href="javascript:isChange();"
					target="_self" />	
				</s:else>
			
				<!-- 最新一期培训班的学习详情页 -->
			<s:if test="isChangeElclass == 0" >
					<area shape="rect" coords="190,2,360,53"
					href="javascript:alertMsg();"
					target="_self" />	
					
				</s:if>
				<s:else>
					<area shape="rect" coords="190,2,360,53"
					href="myelclass_view.action?type=1&elclass.id=<s:property value="new_cla.elClass.id" />&Return=stclalist"
					target="_self" />	
				</s:else>
				
				<!-- 最新一期证书查看页 -->
				<s:if test="isChangeElclass == 0" >
					<area shape="rect" coords="380,3,550,52"
					href="javascript:alertMsg();"
					target="_self" />	
				</s:if>
				<s:else>
					<s:if test="step == 3">
					<area shape="rect" coords="380,3,550,52"
					href="mydiploma_view.action?elclass.id=<s:property value="new_cla.elClass.id" />"
					target="_blank" />
				</s:if>
				<s:else>
					<area shape="rect" coords="380,3,550,52"
					href="javascript:disNopassInfo('<s:property value="new_cla.elClass.id"/>');"
					 />
				</s:else>
				</s:else>
				
				
				
				<!-- 最新一期培训班购买页 -->
				<area shape="rect" coords="570,2,740,54"
				href="newclass_view2.action?elclass.id=<s:property value="nianjian_cla.elClass.id" />"
				target="_self" />
				</s:if>
				
				<s:else>
					<!-- 选班中心列表页 -->
				<s:if test="isChangeElclass == 0" >
				<area shape="rect" coords="5,3,170,46"
				href="forum_getAllclass.action?&isCorrespond=0&cltype.id=1"
				target="_self" />
				</s:if>
				<s:else>
					<area shape="rect" coords="5,3,170,46"
					href="javascript:isChange();"
					target="_self" />	
				</s:else>
			
				<!-- 最新一期培训班的学习详情页 -->
				<s:if test="isChangeElclass == 0" >
					<area shape="rect" coords="190,2,360,53"
					href="javascript:alertMsg();"
					target="_self" />	
				</s:if>
				<s:else>
					<area shape="rect" coords="190,2,360,53"
					href="myelclass_view.action?type=1&elclass.id=<s:property value="nianjian_cla.elClass.id" />&Return=stclalist"
					target="_self" />	
					
				</s:else>
				
				<!-- 最新一期证书查看页 -->
				<s:if test="isChangeElclass == 0" >
					<area shape="rect" coords="380,3,550,52"
					href="javascript:alertMsg();"
					target="_self" />	
				</s:if>
				<s:else>
					<s:if test="step == 3">
					<area shape="rect" coords="380,3,550,52"
					href="mydiploma_view.action?elclass.id=<s:property value="nianjian_cla.elClass.id" />"
					target="_blank" />
				</s:if>
				<s:else>
					<area shape="rect" coords="380,3,550,52"
					href="javascript:disNopassInfo('<s:property value="nianjian_cla.elClass.id"/>');"
					 />
				</s:else>
				</s:else>
				
				
				
				<!-- 最新一期培训班购买页 -->
				<area shape="rect" coords="570,2,740,54"
				href="newclass_view2.action?elclass.id=<s:property value="nianjian_cla.elClass.id" />"
				target="_self" />
				</s:else>
			
				
		</map>
		
		
	
	</BODY>
	<script type="text/javascript">
			function needAllocation(){
			var username = "<s:property value="#session.realname" />";
			var needAllocation = <s:property value="needAllocation" />;
			if(!username==null){
			if(!needAllocation){
				if(window.confirm("您当前还没有报名参加培训，请报名?")){
					//跳转报名培训班
					var classid = <s:property value="new_cla.elClass.id" />;
					if(classid == 0){
						alert("出现错误，将关闭此页面");
						window.close();
					}else{
				//		window.parent.location.href = "newclass_view2.action?elclass.id="+classid+"&ctype=2";
					var width=420;
					var height=360;
					var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0;scroll:no;";
					 window.showModalDialog ("changeElclass.action?elclass.id="+classid+"&ctype=2&x="+Math.random(),null,sFeature);
					}
			}
			}else{
					window.parent.location.href = "newclass_view2.action?elclass.id="+classid+"&ctype=2";
			}
				
		}
		}
		
		
		function disNopassInfo(classid){
			width=420;
			height=360;
		   	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
			window.showModalDialog("classNoPassRemack.action?elclass.id="+classid+"&x="+Math.random(),null,sFeature);
		}
		
		function alertMsg(){
			alert('还未参加培训班,请先报名');
		}
		
		//查看证书
		function show_zhengshu(size,ispass,passed){
			var classid = <s:property value="new_cla.elClass.id" />;
			if(size==1 && ispass==1 && passed ){
				width=420;
				height=360;
			   	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				window.showModalDialog("mydiploma_view.action?elclass.id="+classid+"&x="+Math.random(),null,sFeature);
			}else{
				width=420;
				height=360;
			   	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				window.showModalDialog("classNoPassRemack.action?elclass.id="+classid+"&x="+Math.random(),null,sFeature);
			}
		}
		
		function isChange(){
			alert("您已报名，不需要重复报名！！！");
		}
		</script>
</HTML>


