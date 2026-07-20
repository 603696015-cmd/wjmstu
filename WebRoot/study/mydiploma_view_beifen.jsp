<%@taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<!-- saved from url=(0063)http://localhost:9080/wsj/mydiploma_view.action -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd"><HTML 
xmlns="http://www.w3.org/1999/xhtml"><HEAD>
<META content="text/html; charset=utf-8" http-equiv="Content-Type">
	<script type="text/javascript" src="js/jquery.js"></script>

<SCRIPT type="text/javascript">
		    //判断年份是否为闰年
			function checkYearIsLeapyear(inyear){
				if ((inyear % 4 == 0 && !(inyear % 100 == 0)) || inyear % 400 == 0) { 
					return true; 
				} else { 
					return false; 
				}
			}
			function load(){
				//获取次年的年月日
				var fa_year = 2013;
				var fa_month = 07;
				var fa_day = 19;
				
				//明年的年月日
				var html = "";//有效期
				var next_year = "";
				var next_month = "";
				var next_day = "";
				if(parseInt(fa_year)!=0){
					next_year = parseInt(fa_year) + 1;
				}
				if(parseInt(fa_month)!=0){
					next_month = parseInt(fa_month);
					if(next_month<10){
						next_month = "0"+next_month;
					}
						
				}
				if(parseInt(fa_day)!=0){
					next_day = parseInt(fa_day);
					if(next_day<10){
						next_day = "0"+next_day;
					}
					//判断年份是否为闰年
					if(checkYearIsLeapyear(next_year)){
						next_day = 28;
					}
				}
				
				html = 
				'<span class="STYLE5">'+next_year+'年'+next_month+'月'+next_day+'日</span>';
				if(html!=""){
					$(html).appendTo($("#td"));
				}
			}
		</SCRIPT>
<TITLE>结业证书</TITLE>
<STYLE type="text/css">
<!--
.STYLE3 {
	font-weight: bold;
	font-size: 28px;
}

.STYLE4 {
	color: #0000FF;
	font-weight: bold;
	font-size: 25px;
}

.STYLE5 {
	font-size: 24px
}
.STYLE6 {
	color: #000000;
	font-size: 30px;
	font-weight: bold;
}
.STYLE7 {
	color: #FF0000;
	font-size: 20px;
}
-->
</STYLE>

<META name="GENERATOR" content="MSHTML 9.00.8112.16506"></HEAD>
<BODY onLoad="load();">
<table width="900" height="514" border="0" align="center" cellpadding="0" cellspacing="0" style="border:solid #FF0000 2px;">
  <tr>
    <td valign="top" background="images/certImage.jpg"><table width="880" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="195" height="90">&nbsp;</td>
        <td width="510">&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td height="33">&nbsp;</td>
        <td>&nbsp;</td>
        <td align="left"><span class="STYLE7"><s:date name="myClass.endtime" format="yyyy"/><s:property value="myClass.elClass.id"/><s:property value="myClass.certificatenoStr"/></span></td>
      </tr>
      <tr>
        <td height="45">&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td height="235" align="right" valign="top"><span class="STYLE6"><s:property value="myClass.user.realname"/></span></td>
        <td valign="top" style="padding-top:6px;padding-left:30px;font-size:23px;letter-spacing:2px;"><s:date name="myClass.endtime" format="yyyy年MM月"/></td>
        <td>&nbsp;</td>
      </tr>
      
    </table>
      <table width="800" border="0" align="center" cellpadding="0" cellspacing="0">
        
        <tr>
          <td width="55" height="25">&nbsp;</td>
          <td style="letter-spacing:4px;font-size:20px;color: #333333;"><s:date name="myClass.endtime" format="yyyy年MM月dd日"/></td>
        </tr>
      </table></td>
  </tr>
</table>
<p>&nbsp;</p>
<!--<tr>
				<td width="568" style="line-height: 40px;">
					<p class="STYLE2">
						学员
						<span class="STYLE4">&#32993;&#24378;</span> 于
						<strong class="STYLE4">2012</strong>年
						<strong class="STYLE4">11</strong>月
						<strong class="STYLE4">12</strong>日
						<br />
						完成了
						<span class="STYLE4"><strong>2012&#24180;&#20250;&#35745;&#20154;&#21592;&#32487;&#32493;&#25945;&#32946;&#22312;&#32447;&#23398;&#20064;&#22521;&#35757;&#29677;</strong>（培训班名称）</span>的学习，经考核合格，获得结业证书

。
						<br />
						证书编号为
						<strong class="STYLE4">201210401063</strong>					</p>		

		</td>
			</tr>
			<tr>
				<td width="568" height="50">
					<p align="right" class="STYLE2">
						发证日期：
						<span class="STYLE3">2012</span>年
						<strong class="STYLE3">11</strong>月
						<strong class="STYLE3">12</strong>日					</p>		

		</td>
			</tr>--></BODY></HTML>
