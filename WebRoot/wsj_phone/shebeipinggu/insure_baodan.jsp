<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0042)http://www.sopia.cc/user/User_EditInfo.asp -->
<HTML xmlns="http://www.w3.org/1999/xhtml"><HEAD><TITLE>我要投保</TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8"><LINK 
href="css/css.css" type=text/css rel=stylesheet>
<LINK 
href="css/css_home.css" type=text/css rel=stylesheet>
<LINK 
rel=stylesheet type=text/css href="css/css_header.css">
<META http-equiv=X-UA-Compatible content=IE=7>
<SCRIPT language=javascript src="js/common.js"></SCRIPT>

<SCRIPT language=javascript src="User_EditInfo.files/jquery.js"></SCRIPT>
<script type="text/javascript" src="js/fckeditor.js"></script>
		<script type="text/javascript" src="js/jquery2.js"></script>
		<script type="text/javascript" src="js/stuffman.js"></script>

<META content="MSHTML 6.00.2900.6197" name=GENERATOR>
<style type="text/css">
<!--
.STYLE5 {	FONT-SIZE: 14px; FONT-WEIGHT: bold
}
.STYLE7 {	COLOR: #0000ff; FONT-SIZE: 14px; FONT-WEIGHT: bold
}
.STYLE8 {FONT-SIZE: 14px; color: #0000ff;}
-->
</style>
</HEAD>
<BODY bottomMargin=0 leftMargin=0 topMargin=0 rightMargin=0>
<script type="text/javascript">
			
		  	 	var optCount=1;
				var nowCount=0;
				function addOption(){
					var _optiontype = "radio"
					var obj = document.createElement("div");
					obj.id="option_"+nowCount;
					if(nowCount==0){
					obj.innerHTML ="产品参数"+String.fromCharCode(65+parseInt(nowCount))+"&nbsp;&nbsp;<textarea style='border:1px solid buttonface;overflow:hidden;width:500px;height:120px' onfocus='createeditor(this,"+nowCount+")' id='__option"+nowCount+"' name='question.options'></textarea>"+
					//"<iframe src='_editor/editor.html?height=200&id=__option"+nowCount+"' frameborder='0' scrolling='no' width='500' height='120'></iframe>&nbsp;&nbsp;
					//"<iframe id='opt_frame"+nowCount+"' frameborder='0' scrolling='no' width='0' height='0'></iframe/>"+
					"&nbsp;&nbsp;";
					}else{
					obj.innerHTML ="产品参数"+String.fromCharCode(65+parseInt(nowCount))+"&nbsp;&nbsp;<textarea  style='border:1px solid buttonface;overflow:hidden; width:500px;height:120px' onfocus='createeditor(this,"+nowCount+")' id='__option"+nowCount+"' name='question.options'></textarea>"+
					//"<iframe src='_editor/editor.html?height=200&id=__option"+nowCount+"' frameborder='0' scrolling='no' width='500' height='120'></iframe>&nbsp;&nbsp;
					//"<iframe id='opt_frame"+nowCount+"'  frameborder='0' scrolling='no' width='0' height='0'></iframe/>"+
					"&nbsp;&nbsp;";
					}
					document.getElementById("option_area").appendChild(obj);
					++nowCount;
				}
				function createeditor(obj,id){
					//alert("dd"+id);
					//$("#opt_frame"+id).attr("src","_editor/editor.html?height=200&id=__option"+id);
					//$("#opt_frame"+id).attr("width",500);
					//$("#opt_frame"+id).attr("height",120);
					var oFCKeditor = new FCKeditor(obj.id) ;
					oFCKeditor.BasePath = "editor/" ;
					oFCKeditor.Height = 120;
					oFCKeditor.Width = 500;
					oFCKeditor.ToolbarSet = "qoption" ;
					oFCKeditor.ReplaceTextarea();
				}
				function removeOption(){
					if(nowCount<=1) {
						alert('相关产品参数请不要小于1个');
					}
					else{
					--nowCount;
					var obj = document.getElementById("option_"+nowCount);
					document.getElementById("option_area").removeChild(obj);
					}
				}
			
			
			
				function _onsubmit(){
					if(FCKeditorAPI.GetInstance("content").GetXHTML(true)==''){
						alert("题干不要为空");
						//document.getElementById("content").focus();
						return false;
					}
				
		  			var optionsanswers = document.getElementsByName("question.options");
					var _opa = 0;
					for(var _i = 0 ; _i< optionsanswers.length;_i++){
						if(optionsanswers[_i].value=='')
							_opa++;
					}
					if(_opa!=0){
						alert("请填写完整产品参数");
						return false;
					
					}
					optionsanswers = document.getElementsByName("question.answers");
					_opa = 0;
					for(var _i = 0 ; _i< optionsanswers.length;_i++){
						if(optionsanswers[_i].checked=='checked'||optionsanswers[_i].checked)
							_opa++;
					}
					if(_opa==0){
						alert("请选择选择答案");
						return false;
					
					}
				
				
		  		
		  		
		  		return true;
			}
			function myload(){
				var oFCKeditor = new FCKeditor('content') ;
				oFCKeditor.BasePath = "editor/" ;
				oFCKeditor.Height = 200;
				oFCKeditor.Width = 700;
				oFCKeditor.ToolbarSet = "qcontent" ;
				oFCKeditor.ReplaceTextarea();
		  	 	
		  	 	
		  	 	addOption( );
				addOption( );
				addOption( );
				addOption( );
				
				
			}
		</script>
		 <script type="text/javascript">
		 	function addQuestion(tid){
		 		document.getElementById("qType").value=tid;
		 		form_question_create.action="question_addInit.action";
		 		//addQues.submit();
		 		form_question_create.submit();
		 	}
		 </script>
		 <SCRIPT type=text/javascript>$('#locationid').html("修改基本信息");</SCRIPT>
    
    <SCRIPT>
	
       	 <!----检查用户名，电子邮箱结束-->
      function CheckForm() 
		{ 
			
			if (document.myform.RealName.value =="")
			{
			alert("请填写您的真实姓名！");
			document.myform.RealName.focus();
			return false;
			}
			if (document.myform.Sex.value =="")
			{
			alert("请选择您的性别！");
			document.myform.Sex.focus();
			return false;
			}
			if (document.myform.IDCard.value =="")
			{
			alert("请输入您的身份证号码！");
			document.myform.IDCard.focus();
			return false;
			}
			if (parseInt(document.myform.IDCard.value.length)!=15&&parseInt(document.myform.IDCard.value.length!=18))
			{
			alert("有效身份证号码必须是15位或18位！");
			document.myform.IDCard.focus();
			return false;
			}
		  return true;	
		}
		
		function searchShebei(){
			var tablename = "<s:property value='baoxianProduct.insuranceCategories.tableName'/>";
			width=600;
			height=500;
			var url = "searchShebei.action?x="+Math.random()+"&tablename="+tablename;
		  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
			var rv =  window.showModalDialog(url,null,sFeature);
			if(rv!=undefined&&rv!=""){
				var value = rv.split(":");
				document.getElementById("shebei_id").value = value[0];
				document.getElementById("IC.read_auto_biaodi").value = value[1];
				document.getElementById("IC.read_auto_toubaoren").value = value[2];
				document.getElementById("IC.read_auto_beibaoren").value = value[3];
				document.getElementById("tablename").value = tablename;
			}
		}
		
    </SCRIPT>
<table width="700" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td><DIV class=you>
      <DIV style="TEXT-ALIGN: center; MARGIN-TOP: 10px"><IMG 
src="images/baoxian/sx.gif"></DIV>
      <DIV class=wz_bg>
        <DIV class=cplogo>
        	<s:if test="baoxianProduct.logo != null">															
				<img src="<s:property value="baoxianProduct.logo_"/>" width="86" height="25" />
			</s:if>
			<s:else>
				<img src="<s:property  escape="false" value="logo"/>" id="cimg" width="86" height="25" /> 
				<SCRIPT type="text/javascript">
					obj = document.getElementById("cimg");
					addImgs(obj);
				</SCRIPT>
			</s:else> 
        </DIV>
        <DIV class=cplpgp_tit><SPAN id=lblName><s:property value="baoxianProduct.name"/></SPAN>
            <INPUT name="hidden" type=hidden id=hdnId 
value=21>
        </DIV>
        <!-- <DIV class=ljxs>累计销售<SPAN id=spanSellAmount>1014</SPAN>份</DIV> -->
      </DIV>
      <DIV class=" wz_bg_x"></DIV>
      <DIV><IMG src="images/baoxian/xs.gif"></DIV>
      <DIV id=dealPrice>
        <DIV class=ss_bg>
          <DIV class=bxss>产品介绍</DIV>
        </DIV>
          <DIV style="POSITION: relative; TOP: 0px; LEFT: 0px" class=bxss_bg>
          <DIV style="DISPLAY: none" id=divqplf class=qp_wcdw>
            <DIV class=qp_wc>
              <DIV class=qp_nc>
                <DIV class=qp_hj><IMG src="images/baoxian/hj.gif"></DIV>
                <DIV class=qp_xx>立即购买就能获得<SPAN id=spanJiFen>399</SPAN>积分可到<A 
href="http://www.zhongmin.cn/jifen/index.aspx" 
target=_blank>积分商城</A>兑换礼品</DIV>
              </DIV>
              <DIV style="POSITION: relative; TEXT-ALIGN: center; MARGIN-TOP: -1px"><IMG 
src="images/baoxian/hsj.gif"></DIV>
            </DIV>
          </DIV>
          <TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
            <TBODY>
              <TR>
              	<td>
              		<s:property value="baoxianProduct.jieshao"/>
              	</td>
                <!-- <TD height=94 width="20%" align=left>
                	<DIV style="MARGIN-LEFT: 80px">
                		<B>保障期限</B><BR>
                      	<SPAN id=lblTimeLimit>一年</SPAN>
                	</DIV>
                </TD>
                <TD width="25%">
                	<DIV style="MARGIN-LEFT: 50px"><B>请选择投保份数</B><BR>
                      <SPAN id=labBuyAmount>1份</SPAN>
                        <DIV style="DISPLAY: none" id=divBuyAmount class=searchBar>
                          <DIV class=select>
                            <SELECT style="DISPLAY: none" id=sltBuyNum  name=sltBuyNum>
                              <OPTION selected value=1>1份</OPTION>
                            </SELECT>
                          </DIV>
                        </DIV>
                	</DIV>
                </TD>
                <TD>
                	<DIV style="MARGIN-LEFT: 30px"><B>保障区域</B><BR>
                      <SPAN id=lblSafeGuidArea>中华人民共和国境内</SPAN>
      				</DIV>
      			</TD> -->
                <TD align="middle"><DIV style="TEXT-ALIGN: right"><IMG src="images/baoxian/t1.gif"></DIV>
                    <DIV class=tbkx>
                      <DIV id=divPrice class=" yj">￥<SPAN id=lblPrice><s:property value="baoxianProduct.shichangjia"/></SPAN>
                          <DIV class=yj_gw><IMG src="images/baoxian/gwx.gif"></DIV>
                      </DIV>
                      <DIV class=xj><SPAN>￥</SPAN><SPAN style="FONT-SIZE: 20px" 
      id=lblProPrice><s:property value="baoxianProduct.huiyuanjia"/></SPAN></DIV>
                      <DIV>
                        <a href="IC_U_InfoInit.action?IC.id=<s:property value="baoxianProduct.insuranceCategories.id"/>&baoxianProduct.id=<s:property value="baoxianProduct.id"/>&actionName=Policy_AuditListInit" target="_self"><IMG src="images/baoxian/ljtb.gif" border="0"></a> </DIV>
                    </DIV>
                  <DIV style="TEXT-ALIGN: right; CLEAR: both"><IMG 
      src="images/baoxian/t2.gif"></DIV></TD>
                <TD width="5%">&nbsp;</TD>
              </TR>
            </TBODY>
          </TABLE>
        </DIV>  
      </DIV>
      <DIV><IMG src="images/baoxian/ssx.gif"></DIV>
      <!--基本信息-->
      <DIV class=jbxx_k>
        <DIV class=jbxx>基本信息</DIV>
        <TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
          <TBODY>
            <TR>
              <TD width=130 height="70" align=center vAlign=middle><SCRIPT type=text/javascript>
																	obj = document.getElementById("cimg_0");
																	addImgs(obj);
																</SCRIPT>
                  <span class="STYLE8">产品特色</span></TD>
              <TD height=80 vAlign=middle><p><SPAN class=h30></SPAN><s:property value="jiequ_jianjie"/></p></TD>
            </TR>
          </TBODY>
        </TABLE>
        <TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
          <TBODY>
            <TR>
              <TD width=130 height="70" align=center vAlign=middle><SCRIPT type=text/javascript>
																	obj = document.getElementById("cimg_0");
																	addImgs(obj);
																</SCRIPT>
                  <span class="STYLE8">产品亮点</span></TD>
              <TD height=80 vAlign=middle>
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
              	<wysLib:baoxianProduct_chanpinliangdian iname="${baoxianProduct.chanpinliangdian}"></wysLib:baoxianProduct_chanpinliangdian>
              </table>
              </TD>
            </TR>
          </TBODY>
        </TABLE>
        <DIV class=sc_jfdhbg>
          <UL>
            <LI onclick=changeCard(1)>
              <DIV id=cardleft_1 class=" h_syl"><IMG src="images/baoxian/h_z.jpg"></DIV>
              <DIV id=card_1 class=h_syz>保险信息 <SPAN 
 					 style="PADDING-LEFT: 10px; DISPLAY: none; COLOR: #387fee" 
 					 id=splits1><STRONG>|</STRONG></SPAN> 
 			  </DIV>
              <DIV id=cardright_1 class=h_syl><IMG 
 					 src="images/baoxian/h_y.jpg">
 			  </DIV>
            </LI>
          </UL>
        </DIV>
        <DIV class=blank10></DIV>
        <DIV id=cards1>
          <!--保险信息-->
          <DIV class=bzqy_hk><IMG src="images/baoxian/hk.gif">
          </DIV>
          <DIV class=bzqy>简介</DIV>
          <DIV class=bgbs>
          	${baoxianProduct.jianjie_ }
            <!--<TABLE id=tbFamilyGuids border=0 cellSpacing=0 cellPadding=0 width="100%">
              <TBODY>
                <TR>
                  <TD 
    style="BORDER-BOTTOM: #d4d4d4 1px solid; BORDER-RIGHT: #d4d4d4 1px solid" 
    width="18%" align=right><B>房屋及室内财产</B> </TD>
                  <TD 
    style="BORDER-BOTTOM: #d4d4d4 1px solid; BORDER-RIGHT: #d4d4d4 1px solid" 
    width="10%" align=middle><SPAN id=SpanGuids_1>50万元</SPAN> </TD>
                  <TD 
      style="BORDER-BOTTOM: #d4d4d4 1px solid">因火灾、爆炸、雷击、台风、龙卷风、暴风、暴雨、洪水、地震、海啸、雪灾、雹灾、冰凌、泥石流、崖崩、滑坡、地面突然下陷、飞行物体及其他空中运行物体坠落、外来不属于被保险人所有或使用的建筑物或其他固定物体的倒塌造成被保险人自有、租用或代他人保管或者与他人共有而由被保险人负责保管的房屋建筑及室内财产损失的，以损失实际价值为标准，最高赔付额度是50万元（每次事故的绝对免赔额是500元）。 </TD>
                </TR>
                <TR>
                  <TD 
    style="BORDER-BOTTOM: #d4d4d4 1px solid; BORDER-RIGHT: #d4d4d4 1px solid" 
    width="18%" align=right><B>水暖管爆裂保障</B> </TD>
                  <TD 
    style="BORDER-BOTTOM: #d4d4d4 1px solid; BORDER-RIGHT: #d4d4d4 1px solid" 
    width="10%" align=middle><SPAN id=SpanGuids_2>20000元</SPAN> </TD>
                  <TD 
      style="BORDER-BOTTOM: #d4d4d4 1px solid">室内财产由于被保险人室内的自来水管、下水管、暖气管（片）突然破裂致使水流外溢或邻居家漏水造成被保险的保险财产的损失，保险公司以损失实际价值为标准，最高赔付额度是2万元（每次事故的绝对免赔额是500元）。 </TD>
                </TR>
                <TR>
                  <TD 
    style="BORDER-BOTTOM: #d4d4d4 1px solid; BORDER-RIGHT: #d4d4d4 1px solid" 
    width="18%" align=right><B>室内财产盗抢</B> </TD>
                  <TD 
    style="BORDER-BOTTOM: #d4d4d4 1px solid; BORDER-RIGHT: #d4d4d4 1px solid" 
    width="10%" align=middle><SPAN id=SpanGuids_3>20000元</SPAN> </TD>
                  <TD 
      style="BORDER-BOTTOM: #d4d4d4 1px solid">房屋及其附属设备、室内装潢、家用电器、家具、衣物和床上用品，文体娱乐用品，以及其他生活用具（包括门、窗、锁）由于盗窃、抢劫造成的损失（便携式类用品除外），以损失实际价值为标准，最高赔付额度是2万元（每次事故的绝对免赔额是500元）。 </TD>
                </TR>
                <TR>
                  <TD 
    style="BORDER-BOTTOM: #d4d4d4 1px solid; BORDER-RIGHT: #d4d4d4 1px solid" 
    width="18%" align=right><B>现金贵重物品盗抢</B> </TD>
                  <TD 
    style="BORDER-BOTTOM: #d4d4d4 1px solid; BORDER-RIGHT: #d4d4d4 1px solid" 
    width="10%" align=middle><SPAN id=SpanGuids_4>5000元</SPAN> </TD>
                  <TD 
      style="BORDER-BOTTOM: #d4d4d4 1px solid">现金、金银珠宝、首饰及基本保障承保范围内的便携式类用品（手提电脑、电子记事本、摄像机、照相器材、收音机等）由于盗窃、抢劫造成的损失，以损失实际价值为标准，最高赔付额度是5000元（每次事故的绝对免赔额是500元）。 </TD>
                </TR>
                <TR>
                  <TD 
    style="BORDER-BOTTOM: #d4d4d4 1px solid; BORDER-RIGHT: #d4d4d4 1px solid" 
    width="18%" align=right><B>家用电器用电安全</B> </TD>
                  <TD 
    style="BORDER-BOTTOM: #d4d4d4 1px solid; BORDER-RIGHT: #d4d4d4 1px solid" 
    width="10%" align=middle><SPAN id=SpanGuids_5>20000元</SPAN> </TD>
                  <TD 
      style="BORDER-BOTTOM: #d4d4d4 1px solid">由于供电线路因遭受主险保险责任范围内的自然灾害和意外事故的袭击、供电部门或施工失误、供电线路发生其他意外事故致使电压异常而引起主险合同载明地址的家用电器的直接损毁的，保险公司以损失实际价值为标准，最高赔付额度是2万元（每次事故的绝对免赔额是500元）。 </TD>
                </TR>
                <TR>
                  <TD 
    style="BORDER-BOTTOM: #d4d4d4 1px solid; BORDER-RIGHT: #d4d4d4 1px solid" 
    width="18%" align=right><B>家居玻璃意外破碎</B> </TD>
                  <TD 
    style="BORDER-BOTTOM: #d4d4d4 1px solid; BORDER-RIGHT: #d4d4d4 1px solid" 
    width="10%" align=middle><SPAN id=SpanGuids_6>5000元</SPAN> </TD>
                  <TD 
      style="BORDER-BOTTOM: #d4d4d4 1px solid">与被保险房屋相连的门窗玻璃、屋顶玻璃及固定在家具上的以及室内装饰用玻璃、镜子因意外事故单独破碎，保险公司以损失实际价值为标准，最高赔付额度是5000元（每次事故的绝对免赔额是500元）。 </TD>
                </TR>
                <TR>
                  <TD 
    style="BORDER-BOTTOM: #d4d4d4 1px solid; BORDER-RIGHT: #d4d4d4 1px solid" 
    width="18%" align=right><B>租房费用/租金损失</B> </TD>
                  <TD 
    style="BORDER-BOTTOM: #d4d4d4 1px solid; BORDER-RIGHT: #d4d4d4 1px solid" 
    width="10%" align=middle><SPAN id=SpanGuids_7>10000元</SPAN> </TD>
                  <TD 
      style="BORDER-BOTTOM: #d4d4d4 1px solid">被保险房屋因因火灾、爆炸、雷击、台风、龙卷风、暴风、暴雨、洪水、地震、海啸、雪灾、雹灾、冰凌、泥石流、崖崩、滑坡、地面突然下陷、飞行物体及其他空中运行物体坠落、外来不属于被保险人所有或使用的建筑物或其他固定物体的倒塌而不能居住的，赔偿被保险人在外临时租房的额外费用或房屋无法出租造成的租金损失（每天100元，最高1万元，每次事故的绝对免赔额是500元）。 </TD>
                </TR>
                <TR>
                  <TD 
    style="BORDER-BOTTOM: #d4d4d4 1px solid; BORDER-RIGHT: #d4d4d4 1px solid" 
    width="18%" align=right><B>第三者责任</B> </TD>
                  <TD 
    style="BORDER-BOTTOM: #d4d4d4 1px solid; BORDER-RIGHT: #d4d4d4 1px solid" 
    width="10%" align=middle><SPAN id=SpanGuids_8>50000元</SPAN> </TD>
                  <TD 
      style="BORDER-BOTTOM: #d4d4d4 1px solid">在保险合同载明的地址范围内（包括被保险房屋专属的天台、庭院）因发生意外事故导致第三者的人身伤亡和财产损失，对被保险人经保险人书面同意的因上述原因而支付的诉讼费用、抗辩费用及其他事先经保险人同意的支付的费用，以及第三者在被保险人的被保险房屋内因意外事故受伤，被保险人对第三者的紧急救助费用，保险公司负责赔偿，最高5万元（每次事故的绝对免赔额是500元）。 </TD>
                </TR>
              </TBODY>
            </TABLE>-->
          </DIV>
          <DIV class=blank10></DIV>
          <DIV class=bzqy_hk><IMG src="images/baoxian/hk.gif"></DIV>
          <!-- <DIV class=bzqy>产品特色</DIV>
          <DIV class=bgbs>
            <DIV id=charac class=cpts>
              <P>1、市场第一款承保特大自然灾害“地震”和“海啸”的家庭财产保险。</P>
              <P>2、保险标的特别扩展至现金和首饰（金银、珠宝、玉器、钻石及制品）和承保范围内的便携式用品的盗抢保障。</P>
              <P>3、门窗玻璃、玻璃屋顶也保障，为您节省每一分开支。</P>
              <P>4、提供5万元的第三者责任保障，解除您的后顾之忧。</P>
              <P>5、约定的保险金额在法定节假日自动增加 10%。</P>
            </DIV>
          </DIV>
          <DIV class=blank10></DIV>
          <DIV id=zmtishi>
            <DIV class=bzqy_hk><IMG src="images/baoxian/hk.gif"></DIV>
            <DIV class=bzqy>中民提示</DIV>
            <DIV class=bgbs>
              <DIV id=prompt class=cpts>
                <P>1、本计划由华安保险承保，保障一年，投保人与被保险人为同一人，投保的房屋为自有房屋或租赁房屋。</P>
                <P>2、每人投保限购一份。</P>
                <P>3、本投保人确认投保的房屋建造期间小于30年，房屋结构为钢、钢筋混凝土结构或砖混结构。</P>
                <P>4、本计划只在以下地区销售：北京、上海、广东、广西、福建、湖南、湖北、江苏、浙江、四川、重庆、大连、辽宁、河南、宁波、天津、山东、安徽、江西、黑龙江、贵州、吉林。</P>
                <P>5、本计划适用条款《福满堂家庭财产保险条款》，请您认真阅读和理解，了解保险责任和除外责任。&nbsp;&nbsp;</P>
                <P>6、本计划仅提供电子保单（电子保单具有纸质保单同样法律效力，理赔时只需提供电子保单号和身份证件）。</P>
                <P>7、约定的保险金额在法定节假日自动增加 10%。</P>
                <P>8、经中民保险网购买的顾客，若发生保险事故，中民保险网（热线：4008822300）将提供协助理赔服务。</P>
              </DIV>
            </DIV>
          </DIV>
          <DIV class=blank10></DIV>
          <DIV class=bzqy_hk><IMG src="images/baoxian/hk.gif"></DIV>
          <DIV class=bzqy>投保示例</DIV>
          <DIV class=bgbs>
            <DIV id=instance class=cpts>
              <P>陈先生是一家IT企业的精英，去年年底刚结婚又在单位附近小区里贷款购买了一套大三居，现在生活过得是幸福又美满。虽然他住的小区治安环境非常好，有保安24小时值班，但现在家庭财产被盗的案件太多，陈先生还是想为他新房买一份保险，为他分担意外事故所带来的风险，于是投保一份华安满堂福家庭财产保障计划，保费588元。2011年十一期间陈先生夫妻外出旅游回来，发现玻璃门被砸，房屋被盗，苹果手提电脑等贵重物品一直损失1.3万元。报案后保险公司经核赔，各扣除500元的家居玻璃意外破碎和现金及贵重物品盗抢绝对免赔额后给付家居玻璃意外破碎800元和电脑等贵重物品5000元，一共5800元。</P>
            </DIV>
          </DIV>
          <DIV class=blank10></DIV>
          <DIV class=bzqy_hk><IMG src="images/baoxian/hk.gif"></DIV> -->
          <DIV class=bzqy>详细阅读</DIV>
          <DIV class=bgbs>
            <DIV class="cpts jttk"><IMG src="images/baoxian/lj.gif"> 
            <s:if test="baoxianProduct.jutitiaokuan != null">
	            <A href="<s:property value="baoxianProduct.jutitiaokuan_"/>" 
					target=_blank>具体条款</A> 
			</s:if>
			<s:else>
			</s:else>
			<s:if test="baoxianProduct.chuwaizeren != null">
	            <A href="<s:property value="baoxianProduct.chuwaizeren_"/>" 
					target=_blank>除外责任</A> 
			</s:if>
			<s:else>
			</s:else>
			<s:if test="baoxianProduct.kehugaozhishu != null">
	            <A href="<s:property value="baoxianProduct.kehugaozhishu_"/>" 
					target=_blank>客户告知书</A> 
			</s:if>
			<s:else>
			</s:else>
          </DIV>
        </DIV>
        <!--/保险信息-->
      </DIV>
      <!--产品评论-->
      <DIV style="DISPLAY: none" id=cards2>
        <DIV class=bzqy_hk><IMG src="images/baoxian/hk.gif"></DIV>
        <DIV class=bzqy>客户评价</DIV>
        <DIV class=bgbs>
          <TABLE style="MARGIN-TOP: 10px; MARGIN-BOTTOM: 10px" border=0 cellSpacing=0 
cellPadding=0 width="100%">
            <TBODY>
              <TR>
                <TD style="TEXT-ALIGN: right; WIDTH: 100px; VERTICAL-ALIGN: middle"><IMG 
      src="images/baoxian/5Start.jpg"> </TD>
                <TD style="TEXT-ALIGN: left">&nbsp; 很好，我要推荐： </TD>
                <TD style="TEXT-ALIGN: left; WIDTH: 70px; FLOAT: right">(0 票) </TD>
              </TR>
              <TR>
                <TD style="TEXT-ALIGN: right; WIDTH: 100px"><IMG 
      src="images/baoxian/4Start.jpg"> </TD>
                <TD style="TEXT-ALIGN: left">&nbsp; 很好： </TD>
                <TD style="TEXT-ALIGN: left; WIDTH: 70px; FLOAT: right">(0 票) </TD>
              </TR>
              <TR>
                <TD style="TEXT-ALIGN: right; WIDTH: 100px"><IMG 
      src="images/baoxian/3Start.jpg"> </TD>
                <TD style="TEXT-ALIGN: left">&nbsp; 一般： </TD>
                <TD style="TEXT-ALIGN: left; WIDTH: 70px; FLOAT: right">(0 票) </TD>
              </TR>
              <TR>
                <TD style="TEXT-ALIGN: right; WIDTH: 100px"><IMG 
      src="images/baoxian/2Start.jpg"> </TD>
                <TD style="TEXT-ALIGN: left">&nbsp; 不好： </TD>
                <TD style="TEXT-ALIGN: left; WIDTH: 70px; FLOAT: right">(0 票) </TD>
              </TR>
            </TBODY>
          </TABLE>
        </DIV>
        <DIV id=AjaxPage>
          <STYLE type=text/css>
    .comment_said {
	COLOR: #3366ff; FONT-SIZE: 12px; TEXT-DECORATION: underline
}
    </STYLE>
          <SCRIPT type=text/javascript>
    function CheckContent()
    { 
        var txt=document.getElementById("Comments_new1_txtContent");
        if(document.getElementById("Comments_new1_sltType").value==-1)
        {
            alert("请选择类别！");
                return false;
        }
        else if(txt.value=="")
        {
            alert("评论内容不能为空！");
            return false;
        }       
        else
        {
            return true; 
        }
    }
    
    function sltType_Change()
    {
        if (document.getElementById("Comments_new1_sltType").value==-1 || document.getElementById("Comments_new1_sltType").value==1)
        {
            document.getElementById("div_mes").style.display="none";
            document.getElementById("div_radio").style.display="none";
        }
        else
        {
            document.getElementById("div_mes").style.display="block";
            document.getElementById("div_radio").style.display="block";
        }
    }
    
    function setLevel(value)
    {
        $("#hdnLevel").val(value);
    }
    
    function SetPage(page)
    {
        if($("#hdn_requestPage").val()!= page.toString())
        {
            $("#hdn_requestPage").val(page.toString());
            DoComment('select');
        }
    }
    
    ///表单提交
    function DoComment(typevalue) 
    {
        var userName='';
        var passWord='';
        var Contents='';
        var selectType='';
        var commentLevel='';
            
        if(typevalue=='insert')
        {
            $("#hdn_requestPage").val(1);            
            
            if($("#Comments_new1_hdnIsLogin").val()=='1')
            {
                userName=$("#Comments_new1_lblUserName").text();
            }
            else 
            {
                userName = $("#Comments_new1_txtUserName").val();
                passWord = $("#Comments_new1_txtPassWord").val();
            }
            
            Contents = $("#Comments_new1_txtContent").val();
            selectType = $("#Comments_new1_sltType").val();
            commentLevel = $("#hdnLevel").val();
        }
            
	    $.ajax({
		    url : "../../AsyBuy/CommentList.ashx",
		    data : {
		        type : typevalue,
		        islogin : $("#Comments_new1_hdnIsLogin").val(),
		        username : userName,
		        passWord : passWord,
		        content : encodeURI(Contents),
		        commenttype :selectType,
		        level : commentLevel, 
			    productid : $("#hdnPid").val(),
			    bigclassid : $("#hdnBid").val(),
			    requestpage : $("#hdn_requestPage").val()			    
		    },
		    type : "POST",
		    dataType : 'json',
		    success : handleData,
		    error : function(XMLHttpRequest, textStatus, errorThrown) {
			    $("#commentList").html("LoadError...");
		    },
            complete : function() {                
                if (typeof (getDataComplete) == "function") {
                    getDataComplete("success");
                }
            } 
	    });
    }

    function handleData(data)
    {    
        if(data.optionType=="select")
        {
            if(data.pageStr=="暂无人评论")
            {
                $("#commentList").html(data.pageStr);
            }
            else
            {
                var innerHtml="";
                innerHtml += "<div id=\"newComment\">";
                
                $.each(data.dataList,function(index){
                    var commentinfo=data.dataList[index].comment;
                    innerHtml += "<table width=\"650px\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"line-height: 25px; margin-left: 5px;\">";
                                    
                    innerHtml +="<tr style=\"height: 5px\"></tr>";
                    innerHtml+="<tr><td>";
                    innerHtml+="用户：" + decodeURI(commentinfo.author) + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;评论时间：" + decodeURI(commentinfo.addTime);
                    innerHtml+="</td></tr>";
                    
                    var commentType = decodeURI(commentinfo.type) == "1" ? "用户咨询" : (decodeURI(commentinfo.type) == "2" ? "用户推荐" : "用户分享");
                    
                    innerHtml+="<tr><td valign=\"bottom\"><b>[" + commentType + "]:</b>" + decodeURI(commentinfo.contents) + "</td></tr>";
                    innerHtml+="</table>";
                    innerHtml+="<ul>";
                    
                    if(commentinfo.response !="")
                    {
                        innerHtml+="<table width=\"650px\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"margin: 0px 5px; line-height: 25px; background-color: #F5F5F5;\">";
                        innerHtml+="<tr><td valign=\"bottom\">";
                        innerHtml+="<span style=\"font-size: 12px; font-weight: bold; padding-bottom: 15px; color: #FB5500\">[客服回复]：</span>" + commentinfo.response.comment;
                        innerHtml+="</td></tr>";
                        innerHtml+="<tr><td align=\"right\" valign=\"bottom\">";
                        innerHtml+="<span style=\"color: #737373\">您对我们的回复：</span>";
                        innerHtml+="<a onclick='AddCommentResponse(\"/web/accid/AshxFile/AddSupportResponse.ashx\",\"New\"," + commentinfo.response.responseId + ",\"Response" + commentinfo.response.responseId + "\",\"up\")' style=\"cursor: pointer;\">满意<span style=\"color: Red\">[<span id='spanNewup" + commentinfo.response.responseId + "'>" + commentinfo.response.supportnum + "</span>]</span></a>";
                        innerHtml+="&nbsp;&nbsp;";
                        innerHtml+="<a onclick='AddCommentResponse(\"/web/accid/AshxFile/AddSupportResponse.ashx\",\"New\"," + commentinfo.response.responseId + ",\"Response" + commentinfo.response.responseId + "\",\"down\")' style=\"cursor: pointer;\">不满意<span style=\"color: Red\">[<span id='spanNewdown" + commentinfo.response.responseId + "'>" + commentinfo.response.dissupportnum + "</span>]</span></a>";
                        innerHtml+="</td></tr>";
                        innerHtml+="</table>";                   
                    }
                    
                    innerHtml+="<div style=\"float: left; width: 650px; border-bottom: dashed #999 1px; display: inline; margin-top: 15px; margin-left: 5px;\"></div>";
                    innerHtml+="<br />";
                });
                
                innerHtml+="</div>";
                innerHtml+="<div id=\"pager1\" style=\"float: right; margin-right: 25px;\">" + data.pageStr + "</div>";

                $("#commentList").html(innerHtml);            
            }
        }
        else
        {
            if(data.islogin=="0")
            {
                $("#Comments_new1_hdnIsLogin").val('0');
                
                $("#message").text(decodeURI(data.pageStr));
                $("#Comments_new1_phLogin").show();
                $("#Comments_new1_phWelcome").hide();
            }
            else
            {
                $("#Comments_new1_hdnIsLogin").val('1');
                
                if(data.pageStr=="暂无人评论")
                {
                    $("#commentList").html(data.pageStr);
                }
                else
                {
                    var innerHtml="";
                    innerHtml += "<div id=\"newComment\">";
                    
                    $.each(data.dataList,function(index){
                        var commentinfo=data.dataList[index].comment;
                        innerHtml += "<table width=\"650px\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"line-height: 25px; margin-left: 5px;\">";
                                        
                        innerHtml +="<tr style=\"height: 5px\"></tr>";
                        innerHtml+="<tr><td>";
                        innerHtml+="用户：" + decodeURI(commentinfo.author) + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;评论时间：" + decodeURI(commentinfo.addTime);
                        innerHtml+="</td></tr>";
                        
                        var commentType = decodeURI(commentinfo.type) == "1" ? "用户咨询" : (decodeURI(commentinfo.type) == "2" ? "用户推荐" : "用户分享");
                        
                        innerHtml+="<tr><td valign=\"bottom\"><b>[" + commentType + "]:</b>" + decodeURI(commentinfo.contents) + "</td></tr>";
                        innerHtml+="</table>";
                        innerHtml+="<ul>";
                        
                        if(commentinfo.response !="")
                        {
                            innerHtml+="<table width=\"650px\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"margin: 0px 5px; line-height: 25px; background-color: #F5F5F5;\">";
                            innerHtml+="<tr><td valign=\"bottom\">";
                            innerHtml+="<span style=\"font-size: 12px; font-weight: bold; padding-bottom: 15px; color: #FB5500\">[客服回复]：</span>" + commentinfo.response.comment;
                            innerHtml+="</td></tr>";
                            innerHtml+="<tr><td align=\"right\" valign=\"bottom\">";
                            innerHtml+="<span style=\"color: #737373\">您对我们的回复：</span>";
                            innerHtml+="<a onclick='AddCommentResponse(\"/web/accid/AshxFile/AddSupportResponse.ashx\",\"New\"," + commentinfo.response.responseId + ",\"Response" + commentinfo.response.responseId + "\",\"up\")' style=\"cursor: pointer;\">满意<span style=\"color: Red\">[<span id='spanNewup" + commentinfo.response.responseId + "'>" + commentinfo.response.supportnum + "</span>]</span></a>";
                            innerHtml+="&nbsp;&nbsp;";
                            innerHtml+="<a onclick='AddCommentResponse(\"/web/accid/AshxFile/AddSupportResponse.ashx\",\"New\"," + commentinfo.response.responseId + ",\"Response" + commentinfo.response.responseId + "\",\"down\")' style=\"cursor: pointer;\">不满意<span style=\"color: Red\">[<span id='spanNewdown" + commentinfo.response.responseId + "'>" + commentinfo.response.dissupportnum + "</span>]</span></a>";
                            innerHtml+="</td></tr>";
                            innerHtml+="</table>";                   
                        }
                        
                        innerHtml+="<div style=\"float: left; width: 650px; border-bottom: dashed #999 1px; display: inline; margin-top: 15px; margin-left: 5px;\"></div>";
                        innerHtml+="<br />";
                    });
                    
                    innerHtml+="</div>";
                    innerHtml+="<div id=\"pager1\" style=\"float: right; margin-right: 25px;\">" + data.pageStr + "</div>";

                    $("#commentList").html(innerHtml);            
                }
            
                $("#Comments_new1_phLogin").hide();
                $("#Comments_new1_phWelcome").show();
                $("#Comments_new1_lblUserName").text(decodeURI(data.username));
                
                $("#Comments_new1_sltType").val('-1');
                document.getElementById("div_mes").style.display="none";
                document.getElementById("div_radio").style.display="none";
                $("#Comments_new1_txtContent").val("");
            }
        }
    }
    
    </SCRIPT>
          <DIV class=bzqy_hk><IMG src="images/baoxian/hk.gif">
              <INPUT 
id=hdn_requestPage value=1 type=hidden name=hdn_requestPage>
              <INPUT id=hdnPid 
value=21 type=hidden name=hdnPid>
              <INPUT id=hdnBid value=14 type=hidden 
name=hdnBid>
              <INPUT id=Comments_new1_hdnIsLogin value=0 type=hidden 
name=Comments_new1$hdnIsLogin>
              <INPUT id=hdnLevel value=5 type=hidden 
name=hdnLevel>
          </DIV>
          <DIV class=bzqy>客户评论</DIV>
          <DIV id=commentList class=bgbs>
            <DIV id=newComment></DIV>
            <BR>
            <DIV style="FLOAT: right; MARGIN-RIGHT: 25px" 
id=Comments_new1_pager1>查询无相关记录!</DIV>
          </DIV>
          <!--评论输入-->
          <DIV>
            <DIV class=blank10></DIV>
            <DIV class=bzqy_hk><IMG src="images/baoxian/hk.gif"></DIV>
            <DIV class=bzqy>发表评论</DIV>
            <DIV class=bgbs>
              <DIV class=cpts>
                <DIV>
                  <DIV style="WIDTH: 660px">
                    <TABLE>
                      <TBODY>
                        <TR>
                          <TD style="TEXT-ALIGN: left; WIDTH: 200px; HEIGHT: 40px">请选择分类：
                            <SELECT 
      id=Comments_new1_sltType onchange=sltType_Change() 
      name=Comments_new1$sltType>
                                <OPTION selected value=-1>请选择分类</OPTION>
                                <OPTION value=1>咨询</OPTION>
                                <OPTION value=2>推荐</OPTION>
                                <OPTION 
        value=3>分享</OPTION>
                            </SELECT>
                          </TD>
                          <TD style="TEXT-ALIGN: right"><DIV style="WIDTH: 100px; DISPLAY: none; COLOR: red" id=div_mes>请评价产品： </DIV></TD>
                          <TD><DIV style="DISPLAY: none" id=div_radio>
                            <INPUT id=radLevel5 
      onclick="setLevel('5')" value=5 CHECKED type=radio name=radLevel>
                            很好，我要推荐 
                            &nbsp;&nbsp;&nbsp;
                                <INPUT id=radLevel4 onClick="setLevel('4')" value=4 
      type=radio name=radLevel>
                            很好 &nbsp;&nbsp;&nbsp;
                                <INPUT id=radLevel3 
      onclick="setLevel('3')" value=3 type=radio name=radLevel>
                            一般 
                            &nbsp;&nbsp;&nbsp;
                                <INPUT id=radLevel2 onClick="setLevel('2')" value=2 
      type=radio name=radLevel>
                            不好 </DIV></TD>
                        </TR>
                      </TBODY>
                    </TABLE>
                  </DIV>
                  <TEXTAREA id=Comments_new1_txtContent rows=8 cols=80 name=Comments_new1$txtContent></TEXTAREA>
                </DIV>
                <DIV class=more><SPAN>请登录发言并遵守<A 
href="http://www.zhongmin.cn/Family/Product/Family21.html#">相关规定</A></SPAN></DIV>
                <DIV class=coment_button>
                  <DIV style="DISPLAY: none; FLOAT: left" id=Comments_new1_phLogin>用户名
                    <INPUT 
style="WIDTH: 150px" id=Comments_new1_txtUserName 
onfocus="$('#message').text('');" type=text 
name=Comments_new1$txtUserName>
                    &nbsp;&nbsp;&nbsp;&nbsp; 
                    &nbsp;&nbsp;&nbsp;&nbsp;密码
                    <INPUT style="WIDTH: 110px" 
id=Comments_new1_txtPassWord onFocus="$('#message').text('');" type=password 
name=Comments_new1$txtPassWord>
                  </DIV>
                  <SPAN style="COLOR: red" 
id=message></SPAN>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <DIV style="DISPLAY: none; FLOAT: left" id=Comments_new1_phWelcome>欢迎您：<SPAN 
style="COLOR: red" id=Comments_new1_lblUserName></SPAN> </DIV>
                  <A 
onclick="if(CheckContent()){DoComment('insert');}else{return false;}" 
href="javascript:void(0)" ?><IMG 
style="BORDER-RIGHT-WIDTH: 0px; WIDTH: 90px; BORDER-TOP-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; HEIGHT: 25px; BORDER-LEFT-WIDTH: 0px" 
id=Comments_new1_Image1 src="images/baoxian/image09.gif" aling="absmiddle"> </A><SPAN><A href="http://www.zhongmin.cn/Family/Register.aspx" 
target=_blank>快速注册新用户</A></SPAN> </DIV>
                <SCRIPT language=javascript type=text/javascript>
                if($("#Comments_new1_hdnIsLogin").val()=='1')
                {
                    $("#Comments_new1_phWelcome").show();
                }
                else 
                {
                    $("#Comments_new1_phLogin").show();
                }
            </SCRIPT>
              </DIV>
            </DIV>
          </DIV>
        </DIV>
      </DIV>
      <!--/产品评论-->
      <!--常见问题-->
      <DIV style="DISPLAY: none" id=cards4>
        <DIV class=fbpl_k>
          <DIV>
            <DIV id=Test5study class=Test5study>
              <H2>1. 本款产品保障现金及贵重物品么？对于现金贵重物品被盗我需要准备哪些材料呢？</H2>
              <DL class=TxtList>
                <DD>
                  <P>&nbsp;；现金和首饰（金银、珠宝、玉器、钻石及制品）属于现金及贵重物品盗抢的保障范围；当发生责任事故时，客户需要保存好一些取款凭证，购买物品的凭证（小票），然后向公安机关和保险公司报案勘察现场。</P>
                <br><br></DD>
              </DL>
              <H2>2. 如果搬家了，需要跟保险公司走什么样的程序呢？</H2>
              <DL class=TxtList>
                <DD>
                  <P>有效期间内，如果您提供给我们的住所或通讯地址发生了变更，请以书面或电话的形式通知保险公司，以便于保险公司及时为您变更保险合同上的相关信息。</P>
                <br><br></DD>
              </DL>
              <H2>3. 续保是每年我都要来网站购买一次么？</H2>
              <DL class=TxtList>
                <DD>
                  <P>不是的，这款产品是自动续保的，在中民保险网付完头年款之后，保险公司会跟您确认一个划款账户，在每一个保单周年日，如双方无异议，本组合保险将自动续保，如果您不愿续保，应在保单周年日前至少十五天书面或电话通知保险公司。</P>
                <br><br></DD>
              </DL>
              <H2>4. 第三者责任中的第三者指的是？</H2>
              <DL class=TxtList>
                <DD>&nbsp;
                  <P>第三者是指被保险人及其家庭成员、家庭雇佣人员、寄居人员以外的人。</P>
                </DD>
              </DL>
            </DIV>
          </DIV>
        </DIV>
      </DIV>
      <!--/常见问题-->
      <!--/基本信息-->
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center">
    	<form action="IC_U_InfoInit.action" method="post"  id="iwanttoubao">
    		<input type="hidden" name="id" id="shebei_id"/><!-- 设备id -->
    		<input type="hidden" name="tablename" id="tablename" value="<s:property value='tablename'/>"/>
    		<input type="hidden" name="actionName" id="actionName" value="Policy_AuditListInit"/>
    		<input type="hidden" name="baoxianProduct.id" id="baoxianProduct.id" value="<s:property value='baoxianProduct.id'/>"/>
    		<input type="hidden" name="IC.id" id="IC.id" value="<s:property value='baoxianProduct.insuranceCategories.id'/>"/>
	    	<input type="hidden" name="IC.read_auto_biaodi" id="IC.read_auto_biaodi"/>
	    	<input type="hidden" name="IC.read_auto_toubaoren" id="IC.read_auto_toubaoren"/>
	    	<input type="hidden" name="IC.read_auto_beibaoren" id="IC.read_auto_beibaoren"/>
    	</form>
    	<IMG src="images/baoxian/ljtb2.gif" border="0" style="CURSOR: hand" onclick="document.getElementById('iwanttoubao').submit();">
    </td>
  </tr>
</table>

      </tr>
</table>

	</body></HTML>

