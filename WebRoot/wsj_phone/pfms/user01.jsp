<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<% 
response.setHeader("Pragma","No-Cache"); 
response.setHeader("Cache-Control","No-Cache"); 
response.setDateHeader("Expires", 0);    
%> 


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0042)http://www.sopia.cc/user/User_EditInfo.asp -->
<HTML xmlns="http://www.w3.org/1999/xhtml"><HEAD><TITLE>用户管理中心</TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<LINK href="<%=path %>/css/skin.css" type=text/css rel=stylesheet>
<LINK href="<%=path %>/css/css.css" type=text/css rel=stylesheet>

<META http-equiv=X-UA-Compatible content=IE=7>
<SCRIPT language=javascript src="<%=path %>/js/common.js"></SCRIPT>

<SCRIPT language=javascript src="<%=path %>/js/jquery.js"></SCRIPT>
<script type="text/javascript" src="<%=path %>/editor/fckeditor.js"></script>
<script type="text/javascript" src="<%=path %>/js/stuffman.js"></script>
<script type="text/javascript" src="js/etc/citiesJson.js"></script>

<META content="MSHTML 6.00.2900.6197" name=GENERATOR></HEAD>
<BODY bottomMargin=0 leftMargin=0 topMargin=0 rightMargin=0 onload="myload();">
<DIV class=title style="PADDING-LEFT: 6px; LINE-HEIGHT: 30px; HEIGHT: 30px"><A 
href="http://www.sopia.cc/" target=_parent>网站首页</A> &gt;&gt; <A 
href="http://www.sopia.cc/user/user_main.asp">会员中心</A> &gt;&gt; <SPAN 
class=shadow id=locationid></SPAN></DIV>
<script type="text/javascript" src="<%=path %>/js/jump.js"></script>
<SCRIPT type=text/javascript>$('#locationid').html("修改基本信息");</SCRIPT>
<script type="text/javascript">
	function select(value){
		var li_one = document.getElementById("li_one");
		var li_two = document.getElementById("li_two");
		var li_three = document.getElementById("li_three");
		var li_four = document.getElementById("li_four");
		if(value == "基本信息"){
			li_one.className = "select";
			li_two.className = "";
			li_three.className = "";
			li_four.className = "";
		}else if(value == "相关复印件"){
			li_one.className = "";
			li_two.className = "select";
			li_three.className = "";
			li_four.className = "";
		}else if(value == "密码设置"){
			li_one.className = "";
			li_two.className = "";
			li_three.className = "select";
			li_four.className = "";
		}else if(value == "会员简介"){
			li_one.className = "";
			li_two.className = "";
			li_three.className = "";
			li_four.className = "select";
		}else{
			return ;
		}
	}
</script>
<DIV class=tabs>
	<UL>
		<LI id="li_one" class="select"><A href="#" onclick="showPfmsBaseInfo();select(this.innerHTML);">基本信息</A> 
		</LI>
		<LI id="li_two" class=""><A href="#" onclick="showRelevantCopies();select(this.innerHTML);">相关复印件</A> 
		</LI>
		<LI id="li_three" class=""><A href="#" onclick="showPasswordSetting();select(this.innerHTML);">密码设置</A> 
		</LI>
		<LI id="li_four" class=""><A href="#" onclick="showMemberProfile();select(this.innerHTML);">会员简介</A>
		</LI>
	</UL>
</DIV>


<script type="text/javascript">
		var provinces  = new Array();
		var cities = new Array();
		var counties = new Array();
		$(document).ready(function(){
			var province_city_county = "${pfmsUser.province_city_county}";
			var arr = province_city_county.split(" ");
			var province_in_back = arr[0];
			var city_in_back = arr[1];
			var county_in_back = arr[2];
			$("#option_in_province").text(province_in_back) ;
			
			//alert($("#option_in_province").attr("value"));
			$("#option_in_province").attr("value",province_in_back);
			//alert($("#option_in_province").attr("value"));
			
			$("#option_in_city").text(city_in_back) ;
			$("#option_in_city").attr("value",city_in_back);
			$("#option_in_county").text(county_in_back) ;
			$("#option_in_county").attr("value",county_in_back);
	
	
	
			var citiesString = ss;
			var array = eval("("+citiesString+")") ;//array数组
			
			/*
		  	var province = $("#province");
			var city = $("#city");
			var county = $("#county");
			
			var citiesString = '${areaList}';
			var array = eval("("+citiesString+")") ;//array数组
			
			var provinces  = new Array();
			var cities = new Array();
			var counties = new Array();
			*/
			
			var o = 0;//将定义的provinces数组下标从0开始
			var p = 0;
			var q = 0;
			$.each(array,function(i,n){
				if(array[i].type == "PROVINCE"){
					provinces[o] = array[i];
					$("<option ></option>").val(n.id+" "+n.name).text(n.name)
                  		.appendTo($("#province"));
                  	o++;
				}else if(array[i].type == "CITY"){
					cities[p] = array[i];
					p++;
				}else if(array[i].type == "DISTRICT"){
					counties[q] = array[i];
					q++;
				}
			});
			
			/*
			province.change(function(){
				alert(document.getElementById("province").name.value);
				var id = $(this).children('option:selected').val();
				id = parseInt(id);
				changeProvince(id,cities,counties);
			});
			
			city.change(function(){
				//$(this).attr("value",$(this).children('option:selected').text());
				var id = $(this).children('option:selected').val();
				id = parseInt(id);
				changeCity(id,counties);
			});
			*/
			
			
			
			
			
		});
		function changeProvince(){
			//$("#province").attr("name").value = $("#province").children('option:selected').text();
			//console.log($("#province").attr("name").value);
			var id = $("#province").children('option:selected').val().split(" ")[0];
			id = parseInt(id);
			$("#city").empty();
			$.each(cities,function(i,city){
				if(city.parent_id == id){
					$("<option ></option>").val(city.id+" "+city.name).text(city.name)
                   		.appendTo($("#city"));
				}
			});
			changeCity();
		}
		
		function changeCity(){
			id = parseInt($("#city").children('option:selected').val().split(" ")[0]);
			$("#county").empty();
			$.each(counties,function(i,county){
				if(county.parent_id == id){
					$("<option ></option>").val(county.id+" "+county.name).text(county.name)
                   		.appendTo($("#county"));
				}
			});
		}
		
</script>
<SCRIPT> 
	function myload(){
		show_div();
	
		var showimages = document.getElementsByName('showimages');  
		for(var i = 0 ;i <showimages.length;i++){ 
			showimages[i].src = showimages[i].src +"?"+ Math.round(Math.random() * 10000000); 
		}


		var oFCKeditor = new FCKeditor("note") ;
		oFCKeditor.BasePath = "editor/" ;
		oFCKeditor.Height = 400;
		oFCKeditor.Width = "100%";
		oFCKeditor.ReplaceTextarea();
		
		setCurTime("releasetime");
				
		}
	function setCurTime(oid){
			var now=new Date();
			var year=now.getYear();
			var month=now.getMonth()+1;
			var day=now.getDate();
			var hours=now.getHours();
			var minutes=now.getMinutes();
			if(minutes<10){
				minutes="0"+minutes;
			}
			var seconds=now.getSeconds();
			if(seconds<10){
				seconds="0"+seconds;
			}
			var timeString = year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
			var oCtl = document.getElementById(oid);
			oCtl.value = timeString;
			//setTimeout("setCurTime('"+oid+"')",1000);
			//alert(oid);
		}
		
		function show_div(){
			if("${number}" == "1"){
				showPfmsBaseInfo();
				select("基本信息");
			}else if("${number}" == "2"){
				showRelevantCopies();
				select("相关复印件");
			}else if("${number}" == "3"){
				showPasswordSetting();
				select("密码设置");
			}else if("${number}" == "4"){
				showMemberProfile();
				select("会员简介");
			}else{
				showPfmsBaseInfo();
				select("基本信息");
			}
		}
       	 
    </SCRIPT>
<script type="text/javascript">
	function CheckForm() { 
		var partten;
		if(document.getElementById("realname").value == ""){
			alert("请填写您的会员名称!");
			document.getElementById("realname").focus();
			return false;
		}
		if(document.getElementById("sex").value == ""){
			alert("请选择您的性别!");
			document.getElementById("sex").focus();
			return false;
		}
		if(document.getElementById("shenfenzheng").value == ""){
			alert("请填写您的身份证!");
			document.getElementById("shenfenzheng").focus();
			return false;
		}
		if(document.getElementById("shenfenzheng").value != ""){
			partten = /^(\d{14}|\d{17})(\d|[xX])$/;
			if(!partten.test(document.getElementById("shenfenzheng").value)){
			 	alert("对不起，您输入的身份证有错误,请重新填写!");
			    document.getElementById("shenfenzheng").focus();
			    return false;
			}
		}
		if(document.getElementById("mobile").value != ""){
			partten = /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,8}$/;
			if(!partten.test(document.getElementById("mobile").value)){
			 	alert("对不起，您输入的电话号码有错误。区号和电话号码之间请用-分割!");
			    document.getElementById("mobile").focus();
			    return false;
			}
		}
		if(document.getElementById("movephone").value != ""){
			 partten = /^0*(13|15|18)\d{9}$/;
			 if(!partten.test(document.getElementById("movephone").value)){
			 	alert("对不起,手机号码只能是数字,并且只能以13或15或18开头并且长度是11位!");
			    document.getElementById("movephone").focus();
			    return false;
			 }
		}
		if(document.getElementById("fex").value != ""){
			partten = /^(\d{3,4}-)?\d{7,8}$/;
			if(!partten.test(document.getElementById("fex").value)){
			 	alert("对不起,请输入一个有效的传真号码!");
			    document.getElementById("fex").focus();
			    return false;
			}
		}
		if(document.getElementById("email").value != ""){
			partten = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
			if(!partten.test(document.getElementById("email").value)){
			 	alert("对不起,请输入一个有效的邮件地址!");
			    document.getElementById("email").focus();
			    return false;
			}
		}
		return true;
	}
</script>
<div id="pfmsBaseInfo" style="display:block;">
  <FORM id=myform name=myform onsubmit="return CheckForm();" action="alterBaseInfo.action?number=1"  method=post>
  <input type="hidden" name="pfmsUser.id" value="${pfmsUser.id }"/>
  <input type="hidden" name="pfmsUser.user.role.id" value="<s:property value='pfmsUser.user.role.id'/>"/>
  <input type="hidden" name="pfmsUser.user.department.id" value="<s:property value='pfmsUser.user.department.id'/>"/>
<TABLE cellSpacing=1 cellPadding=3 width="98%" align=center border=0>
  <TBODY>
  <TR class=tdbg>
    <td width="25%" height=22>
		<SPAN style="FONT-WEIGHT: bold">
			会员头像：
			<s:if test="pfmsUser.head != null">															
				<img src="<s:property value="pfmsUser.head_"/>" width="100" height="80" />
			</s:if>
			<s:else>
				<img src="" width="100" height="80" /> 
			</s:else> 
		</SPAN>
	</td>
	<td width="25%">&nbsp;&nbsp;
		<label>
			<input class=textbox type="text"  id="pic" maxLength=50  size=30 name="pfmsUser.head" value='<s:property value="pfmsUser.head"/>'/>
			<a href="javascript:setUrl('pic');" class="textbg">浏览资源库</a>
		</label>
	</td>
  </TR>
  
  <TR class=tdbg>
    <TD width="28%" height=22><SPAN style="FONT-WEIGHT: bold">会员账号：
      </SPAN><BR>
      用于登陆服务系统的用户名，不可更改</TD>
    <TD width="72%">&nbsp; ${pfmsUser.user.username }</TD>
  </TR>
  
   <tr>
		<td width="28%" height=22><SPAN style="FONT-WEIGHT: bold" >
			<SPAN style="FONT-WEIGHT: bold">单位/部门：</SPAN>
		</td>
		<td width="72%" >&nbsp;
			 <s:property value="pfmsUser.user.department.name"/>
		</td>
	</tr>
  
  <tr class=tdbg>
	<td width="28%" height=22><SPAN style="FONT-WEIGHT: bold" >
		<SPAN style="FONT-WEIGHT: bold">权限：</SPAN>
	</td>
	<td width="72%" >&nbsp;
		 <s:property value="pfmsUser.user.role.name"/>
	</td>
  </tr>
  
  <TR class=tdbg>
    <TD width="28%" height=22><SPAN 
      style="FONT-WEIGHT: bold">会员名称：</SPAN><BR>
      单位会员填写单位名称，个人会员填写姓名</TD>
    <TD width="72%">&nbsp;&nbsp; <INPUT class=textbox id="realname" maxLength=50 
      size=30 name="pfmsUser.user.realname" value="${pfmsUser.user.realname }"> <SPAN style="COLOR: red">* </SPAN>
    </TD>
  </TR>
	  <TR class=tdbg>
	  
  <TR class=tdbg>
  	<TD width="28%" height=22><SPAN 
      style="FONT-WEIGHT: bold">会员类型：</SPAN></TD>
    <TD width="72%">&nbsp;&nbsp; 
    	<select name="pfmsUser.huiyuanleixing" 
			onchange="this.value=this.options[this.selectedIndex].value">
			<option value="${pfmsUser.huiyuanleixing }">
				${pfmsUser.huiyuanleixing }
			</option>
			<option value="设备生产">
				设备生产
			</option>
			<option value="设备销售">
				设备销售
			</option>
			<option value="油品生产">
				油品生产
			</option>
			<option value="油品供应">
				油品供应
			</option>
			<option value="配件生产">
				配件生产
			</option>
			<option value="配件销售">
				配件销售
			</option>
			<option value="修理厂">
				修理厂
			</option>
			<option value="保险公司">
				保险公司
			</option>
			<option value="银行">
				银行
			</option>
			<option value="建筑施工单位">
				建筑施工单位
			</option>
			<option value="个体">
				个体
			</option>
		</select> 
    </TD>
  </TR>
  <TR class=tdbg>
    <TD width="28%" height=22><SPAN 
      style="FONT-WEIGHT: bold">单位代码：</SPAN><BR>
      单位会员填写单位名称，个人会员填写姓名
    </TD>
    <TD width="72%">&nbsp;&nbsp; <INPUT class=textbox id="danwei" maxLength=50 
      size=30 name="pfmsUser.user.danwei" value=${pfmsUser.user.danwei }> <SPAN style="COLOR: red">* </SPAN>
    </TD>
  </TR>
	  <TR class=tdbg>
    <TD width="28%" height=22><SPAN 
      style="FONT-WEIGHT: bold">负责人姓名：</SPAN><BR>
      法人代表或常用联系人姓名</TD>
    <TD width="72%">&nbsp;&nbsp; <INPUT class=textbox id="respName" maxLength=50 
      size=30 name="pfmsUser.respName" value=${pfmsUser.respName }> <SPAN style="COLOR: red">* </SPAN></TD></TR>
	   
	  
  <TR class=tdbg>
    <TD width="28%" height=22><SPAN 
      style="FONT-WEIGHT: bold">性&nbsp;&nbsp;&nbsp; 别：</SPAN><BR></TD>
    <TD width="72%">&nbsp;&nbsp; <SELECT id=sex style="WIDTH: 110px" name="pfmsUser.user.sex"> 
        <OPTION value = ${pfmsUser.user.sex } selected>${pfmsUser.user.sex }</OPTION> 
        <OPTION value=男 >男</OPTION> 
        <OPTION value=女>女</OPTION></SELECT> <SPAN style="COLOR: red">* </SPAN></TD></TR>
        
  <tr>
    	<TD width="28%" height=22><SPAN 
      style="FONT-WEIGHT: bold">省&nbsp;市&nbsp;县&nbsp;：</SPAN><BR></TD>
    	<td>
    	<select id="province" name="province" onchange="changeProvince();" style="width:100">  
	         <option id="option_in_province" value="">  
	              请选择省   
	         </option>  
		</select>&nbsp;
		<select id="city" name="city" onchange="changeCity();" style="width:100">  
	         <option id="option_in_city" value="">  
	              请选择市  
	         </option>  
		</select>&nbsp;
		<select id="county" name="county" onchange="" style="width:100">  
	         <option id="option_in_county" value="">  
	              请选择县
	         </option>  
		</select>&nbsp;
    	</td>
    </tr>
  
  <TR class=tdbg>
    <TD width="28%" height=22><SPAN 
      style="FONT-WEIGHT: bold">身份证号：</SPAN><BR>有效身份证号码应该是15位或18位，请认真填写。</TD>
    <TD width="72%">&nbsp;&nbsp; <INPUT class=textbox id="shenfenzheng" maxLength=50 
      size=30 name="pfmsUser.user.shenfenzheng" value=${pfmsUser.user.shenfenzheng }> <SPAN style="COLOR: red">* </SPAN></TD></TR>
  
  <TR class=tdbg>
    <TD width="28%" height=22><SPAN 
      style="FONT-WEIGHT: bold">地址：</SPAN><BR>
      单位所在地、经营场所地或其他地址</TD>
    <TD width="72%">&nbsp;&nbsp; <INPUT class=textbox id="address" maxLength=50 
      size=30 name="pfmsUser.address" value=${pfmsUser.address }> </TD></TR>
	  <TR class=tdbg>
    <TD width="28%" height=22><SPAN 
      style="FONT-WEIGHT: bold">电话：</SPAN><BR>
      单位常用对外联系电话或负责人电话</TD>
    <TD width="72%">&nbsp;&nbsp; <INPUT class=textbox id="mobile" maxLength=50 
      size=30 name="pfmsUser.mobile" value=${pfmsUser.mobile }> </TD></TR>
	  <TR class=tdbg>
    <TD width="28%" height=22><SPAN 
      style="FONT-WEIGHT: bold">手机：</SPAN><BR>
      负责人手机号码</TD>
    <TD width="72%">&nbsp;&nbsp; <INPUT class=textbox id="movephone" maxLength=50 
      size=30 name="pfmsUser.user.movephone" value=${pfmsUser.user.movephone }> </TD></TR>
	  <TR class=tdbg>
    <TD width="28%" height=22><SPAN 
      style="FONT-WEIGHT: bold">传真：</SPAN><BR>
      常用业务来往传真电话</TD>
    <TD width="72%">&nbsp;&nbsp; <INPUT class=textbox id="fex" maxLength=50 
      size=30 name="pfmsUser.fex" value=${pfmsUser.fex }> </TD></TR>
  <TR class=tdbg>
    <TD height=22><SPAN 
      style="FONT-WEIGHT: bold">邮箱地址：</SPAN><BR>
    请填写正确的邮箱地址，如：service@sina.com</TD>
    <TD>&nbsp;&nbsp; <INPUT class=textbox id="email" maxLength=50 size=30 
       name="pfmsUser.email" value=${pfmsUser.email }> </TD></TR>
  
  <TR class=tdbg>
    <TD width="28%" height=30>&nbsp;</TD> 
    <TD width="72%">
    	<INPUT class=button type=submit value=" OK,修 改 " name=Submit > 
    </TD>
 </TR>
  </TBODY>
  </TABLE>
   </FORM>
  </div>
  

<SCRIPT type=text/javascript>
     function changeimage()
	 {
		  $("#UserFace").val("<%=path %>/images/face/"+$("#Image").val()+".gif");
		  $("#imgIcon").attr("src",'/Images/Face/'+$("#Image").val()+'.gif');
	 }
	 
</SCRIPT>

  <div id="relevantCopies" style="display:none;">
<TABLE width="80%" border=1 align=center cellPadding=3 cellSpacing=0 bordercolor="#333333">
  <TBODY>
  <TR class=tdbg>
    <TD colSpan=2 height=50><SPAN 
      style="FONT-WEIGHT: bold; FONT-SIZE: 14px; COLOR: green">会员相关证件的扫描件可以在此进行上传和修改，每类证件都有对应的格式要求，上传前请进行相应修改并确认！</SPAN></TD>
  </TR>
  <TR>
    <TD rowspan="2" align=center>
     <IMG id=imgIcon1  
      height=60 src="<%=path %>/images/pfms/yingyezhizhao_${pfmsUser.userId }.${pfmsUser.yingyezhizhao }" width=60 
      border=1 name=showimages> <BR>
         <A 
      href="<%=path %>/images/pfms/yingyezhizhao_${pfmsUser.userId }.${pfmsUser.yingyezhizhao }"><FONT 
      color=red>查看大图</FONT></A>  
    </TD>
    <TD>
只支持jpg、gif、png，小于500k，默认尺寸为600*800 
      <BR>
  		<form name="yingyezhizhao" action="uploadFile.action?number=2" method="post" enctype="multipart/form-data">
      		<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  				<TBODY>
  					<TR class=tdbg>
    					<TD vAlign=top>
    						<INPUT class=textbox type="file" accept=html size=30 name="imgFile"> 
    						<INPUT class=button id=BtnSubmit onclick="document.getElementById('LayerPrompt1').style.visibility='visible';" type=submit value=开始上传 name=Submit> 
      						<INPUT type=hidden value="yingyezhizhao" name=imgType>  
      						<INPUT type=hidden value=9999 name=BasicType> 
      						<INPUT type=hidden value=4 name=AutoReName>
      						<input type="hidden" name="id" value='<s:property value="pfmsUser.user.id"/>'/>
      					</TD>
      				</TR>
      			</TBODY>
      		</TABLE>
      	</form>
      	
		<DIV id="LayerPrompt1" style="BORDER-RIGHT: #f9c943 1px solid; BORDER-TOP: #f9c943 1px solid; Z-INDEX: 1; LEFT: 2px; VISIBILITY: hidden; BORDER-LEFT: #f9c943 1px solid; WIDTH: 300px; BORDER-BOTTOM: #f9c943 1px solid; POSITION: absolute; TOP: 0px; HEIGHT: 28px; BACKGROUND-COLOR: #ffffee; layer-background-color: #00CCFF">
		<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
		  <TBODY>
			  <TR>
			    <TD>
			      <DIV>
			      	&nbsp;请稍等，正在上传文件<IMG src="User_UpFile.files/wait.gif" align=absMiddle>
			      </DIV>
			    </TD>
			  </TR>
		  </TBODY>
		  </TABLE>
		</DIV>
    </TD>
  </TR>
  <TR class=tdbg>
    <TD colSpan=2 height=22><span class="STYLE1">营业执照</span></TD>
  </TR>
  
  <TR>
    <TD rowspan="2" align=center>
     <IMG id=imgIcon2   
      height=60 src="<%=path %>/images/pfms/shuiwudengjizheng_${pfmsUser.userId }.${pfmsUser.shuiwudengjizheng }" width=60 
      border=1 name=showimages> <BR>
      <A 
      href="<%=path %>/images/pfms/yingyezhizhao_${pfmsUser.userId }.${pfmsUser.shuiwudengjizheng }"><FONT 
      color=red>查看大图</FONT></A>
    <TD>
只支持jpg、gif、png，小于500k，默认尺寸为600*800 
      <BR>
      <form name="shuiwudengjizheng" action="uploadFile.action?number=2" method="post" encType="multipart/form-data">
      		<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  				<TBODY>
  					<TR class=tdbg>
    					<TD vAlign=top>
    						<INPUT class=textbox type=file accept=html size=30 name="imgFile"> 
    						<INPUT class=button id=BtnSubmit onclick="document.getElementById('LayerPrompt2').style.visibility='visible';" type=submit value=开始上传 name=Submit> 
      						<INPUT type=hidden value="shuiwudengjizheng" name=imgType> 
      						<INPUT type=hidden value=9999 name=BasicType> 
      						<INPUT type=hidden value=4 name=AutoReName>
      						<input type="hidden" name="id" value='<s:property value="pfmsUser.user.id"/>'/>
      					</TD>
      				</TR>
      			</TBODY>
      		</TABLE>
      	</form>
      	
		<DIV id="LayerPrompt2" style="BORDER-RIGHT: #f9c943 1px solid; BORDER-TOP: #f9c943 1px solid; Z-INDEX: 1; LEFT: 2px; VISIBILITY: hidden; BORDER-LEFT: #f9c943 1px solid; WIDTH: 300px; BORDER-BOTTOM: #f9c943 1px solid; POSITION: absolute; TOP: 0px; HEIGHT: 28px; BACKGROUND-COLOR: #ffffee; layer-background-color: #00CCFF">
		<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
		  <TBODY>
			  <TR>
			    <TD>
			      <DIV>
			      	&nbsp;请稍等，正在上传文件<IMG src="User_UpFile.files/wait.gif" align=absMiddle>
			      </DIV>
			    </TD>
			  </TR>
		  </TBODY>
		  </TABLE>
		</DIV>
	</TD>
  </TR>
  <TR class=tdbg>
    <TD colSpan="2" height="22"><span class="STYLE1">税务登记证</span></TD>
  </TR>
 
  <TR>
    <TD rowspan="2" align=center>
     <IMG id=imgIcon3   
      height=60 src="<%=path %>/images/pfms/zuzhijigoudaimazheng_${pfmsUser.userId }.${pfmsUser.zuzhijigoudaimazheng }" width=60 
      border=1 name=showimages> <BR>
      <A 
      href="<%=path %>/images/pfms/yingyezhizhao_${pfmsUser.userId }.${pfmsUser.zuzhijigoudaimazheng }"><FONT 
      color=red>查看大图</FONT></A>
    <TD>
只支持jpg、gif、png，小于500k，默认尺寸为600*800 
      <BR>
      <form name="zuzhijigoudaimazheng" action="uploadFile.action?number=2" method="post" encType="multipart/form-data">
      		<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  				<TBODY>
  					<TR class=tdbg>
    					<TD vAlign=top>
    						<INPUT class=textbox type=file accept=html size=30 name="imgFile"> 
    						<INPUT class=button id=BtnSubmit onclick="document.getElementById('LayerPrompt3').style.visibility='visible';" type=submit value=开始上传 name=Submit> 
      						<INPUT type=hidden value=9999 name=BasicType> 
      						<INPUT type=hidden value="zuzhijigoudaimazheng" name="imgType"> 
      						<INPUT type=hidden value=4 name=AutoReName>
      						<input type="hidden" name="id" value='<s:property value="pfmsUser.user.id"/>'/>
      					</TD>
      				</TR>
      			</TBODY>
      		</TABLE>
      	</form>
      	
		<DIV id="LayerPrompt3" style="BORDER-RIGHT: #f9c943 1px solid; BORDER-TOP: #f9c943 1px solid; Z-INDEX: 1; LEFT: 2px; VISIBILITY: hidden; BORDER-LEFT: #f9c943 1px solid; WIDTH: 300px; BORDER-BOTTOM: #f9c943 1px solid; POSITION: absolute; TOP: 0px; HEIGHT: 28px; BACKGROUND-COLOR: #ffffee; layer-background-color: #00CCFF">
		<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
		  <TBODY>
			  <TR>
			    <TD>
			      <DIV>
			      	&nbsp;请稍等，正在上传文件<IMG src="User_UpFile.files/wait.gif" align=absMiddle>
			      </DIV>
			    </TD>
			  </TR>
		  </TBODY>
		  </TABLE>
		</DIV>
	</TD>
  </TR>
  
  <TR class=tdbg>
    <TD colSpan=2 height=22><span class="STYLE1">组织机构代码证</span></TD>
  </TR>
  
  
  <TR>
    <TD rowspan="2" align=center>
     <IMG id=imgIcon4   
      height=60 src="<%=path %>/images/pfms/farenshenfenzheng_${pfmsUser.userId }.${pfmsUser.farenshenfenzheng }" width=60 
      border=1 name=showimages> <BR>
      <A 
      href="<%=path %>/images/pfms/yingyezhizhao_${pfmsUser.userId }.${pfmsUser.farenshenfenzheng }"><FONT 
      color=red>查看大图</FONT></A>
    <TD>
只支持jpg、gif、png，小于500k，默认尺寸为600*800 
      <BR>
      <form name="farenshenfenzheng" action="uploadFile.action?number=2" method="post" encType="multipart/form-data">
      		<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  				<TBODY>
  					<TR class=tdbg>
    					<TD vAlign=top>
    						<INPUT class=textbox type=file accept=html size=30 name="imgFile"> 
    						<INPUT class=button id=BtnSubmit onclick="document.getElementById('LayerPrompt4').style.visibility='visible';" type=submit value=开始上传 name=Submit> 
      						<INPUT type=hidden value=9999 name=BasicType> 
      						<INPUT type=hidden value="farenshenfenzheng" name="imgType"> 
      						<INPUT type=hidden value=4 name=AutoReName>
      						<input type="hidden" name="id" value='<s:property value="pfmsUser.user.id"/>'/>
      					</TD>
      				</TR>
      			</TBODY>
      		</TABLE>
      	</form>
      	
		<DIV id="LayerPrompt4" style="BORDER-RIGHT: #f9c943 1px solid; BORDER-TOP: #f9c943 1px solid; Z-INDEX: 1; LEFT: 2px; VISIBILITY: hidden; BORDER-LEFT: #f9c943 1px solid; WIDTH: 300px; BORDER-BOTTOM: #f9c943 1px solid; POSITION: absolute; TOP: 0px; HEIGHT: 28px; BACKGROUND-COLOR: #ffffee; layer-background-color: #00CCFF">
		<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
		  <TBODY>
			  <TR>
			    <TD>
			      <DIV>
			      	&nbsp;请稍等，正在上传文件<IMG src="User_UpFile.files/wait.gif" align=absMiddle>
			      </DIV>
			    </TD>
			  </TR>
		  </TBODY>
		  </TABLE>
		</DIV>
	</TD>
  </TR>
  
  <TR class=tdbg>
    <TD colSpan=2 height=22><span class="STYLE1">法人身份证</span></TD>
  </TR>
	
	
  <TR>
    <TD rowspan="2" align=center>
     <IMG id=imgIcon5   
      height=60 src="<%=path %>/images/pfms/zizhidengjizhengshu_${pfmsUser.userId }.${pfmsUser.zizhidengjizhengshu }" width=60 
      border=1 name=showimages> <BR>
      <A 
      href="<%=path %>/images/pfms/yingyezhizhao_${pfmsUser.userId }.${pfmsUser.zizhidengjizhengshu }"><FONT 
      color=red>查看大图</FONT></A>
    <TD>
只支持jpg、gif、png，小于500k，默认尺寸为600*800 
      <BR><form name="zizhidengjizhengshu" action="uploadFile.action?number=2" method="post" encType="multipart/form-data">
      		<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  				<TBODY>
  					<TR class=tdbg>
    					<TD vAlign=top>
    						<INPUT class=textbox type=file accept=html size=30 name="imgFile"> 
    						<INPUT class=button id=BtnSubmit onclick="document.getElementById('LayerPrompt5').style.visibility='visible';" type=submit value=开始上传 name=Submit> 
      						<INPUT type=hidden value=9999 name=BasicType> 
      						<INPUT type=hidden value="zizhidengjizhengshu" name="imgType">
      						<INPUT type=hidden value=4 name=AutoReName>
      						<input type="hidden" name="id" value='<s:property value="pfmsUser.user.id"/>'/>
      					</TD>
      				</TR>
      			</TBODY>
      		</TABLE>
      	</form>
      	
		<DIV id="LayerPrompt5" style="BORDER-RIGHT: #f9c943 1px solid; BORDER-TOP: #f9c943 1px solid; Z-INDEX: 1; LEFT: 2px; VISIBILITY: hidden; BORDER-LEFT: #f9c943 1px solid; WIDTH: 300px; BORDER-BOTTOM: #f9c943 1px solid; POSITION: absolute; TOP: 0px; HEIGHT: 28px; BACKGROUND-COLOR: #ffffee; layer-background-color: #00CCFF">
		<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
		  <TBODY>
			  <TR>
			    <TD>
			      <DIV>
			      	&nbsp;请稍等，正在上传文件<IMG src="User_UpFile.files/wait.gif" align=absMiddle>
			      </DIV>
			    </TD>
			  </TR>
		  </TBODY>
		  </TABLE>
		</DIV>
	</TD>
  </TR>
  <TR class=tdbg>
    <TD colSpan=2 height=22><span class="STYLE1">资质等级证书 </span></TD>
  </TR>
	
	
  <TR>
    <TD rowspan="2" align=center>
     <IMG id=imgIcon6   
      height=60 src="<%=path %>/images/pfms/xinyongdengjipingguzhengshu_${pfmsUser.userId }.${pfmsUser.xinyongdengjipingguzhengshu }" width=60 
      border=1 name=showimages> <BR>
      <A 
      href="<%=path %>/images/pfms/yingyezhizhao_${pfmsUser.userId }.${pfmsUser.xinyongdengjipingguzhengshu }"><FONT 
      color=red>查看大图</FONT></A>
    <TD>
只支持jpg、gif、png，小于500k，默认尺寸为600*800 
      <BR><form name="xinyongdengjipingguzhengshu" action="uploadFile.action?number=2" method="post" encType="multipart/form-data">
      		<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  				<TBODY>
  					<TR class=tdbg>
    					<TD vAlign=top>
    						<INPUT class=textbox type=file accept=html size=30 name="imgFile"> 
    						<INPUT class=button id=BtnSubmit onclick="document.getElementById('LayerPrompt6').style.visibility='visible';" type=submit value=开始上传 name=Submit> 
      						<INPUT type=hidden value=9999 name=BasicType> 
      						<INPUT type=hidden value="xinyongdengjipingguzhengshu" name="imgType"> 
      						<INPUT type=hidden value=4 name=AutoReName>
      						<input type="hidden" name="id" value='<s:property value="pfmsUser.user.id"/>'/>
      					</TD>
      				</TR>
      			</TBODY>
      		</TABLE>
      	</form>
      	
		<DIV id="LayerPrompt6" style="BORDER-RIGHT: #f9c943 1px solid; BORDER-TOP: #f9c943 1px solid; Z-INDEX: 1; LEFT: 2px; VISIBILITY: hidden; BORDER-LEFT: #f9c943 1px solid; WIDTH: 300px; BORDER-BOTTOM: #f9c943 1px solid; POSITION: absolute; TOP: 0px; HEIGHT: 28px; BACKGROUND-COLOR: #ffffee; layer-background-color: #00CCFF">
		<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
		  <TBODY>
			  <TR>
			    <TD>
			      <DIV>
			      	&nbsp;请稍等，正在上传文件<IMG src="User_UpFile.files/wait.gif" align=absMiddle>
			      </DIV>
			    </TD>
			  </TR>
		  </TBODY>
		  </TABLE>
		</DIV>
	</TD>
  </TR>
  <TR class=tdbg>
    <TD colSpan=2 height=22><span class="STYLE1">信用等级评估证书 </span></TD>
  </TR>
	
	
  <TR>
    <TD rowspan="2" align=center>
     <IMG id=imgIcon7   
      height=60 src="<%=path %>/images/pfms/qitazhengshu_${pfmsUser.userId }.${pfmsUser.qitazhengshu }" width=60 
      border=1 name=showimages> <BR>
      <A 
      href="<%=path %>/images/pfms/yingyezhizhao_${pfmsUser.userId }.${pfmsUser.qitazhengshu }"><FONT 
      color=red>查看大图</FONT></A>
    <TD>
只支持jpg、gif、png，小于500k，默认尺寸为600*800 
      <BR><form name="qitazhengshu" action="uploadFile.action?number=2" method="post" encType="multipart/form-data">
      		<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  				<TBODY>
  					<TR class=tdbg>
    					<TD vAlign=top>
    						<INPUT class=textbox type=file accept=html size=30 name="imgFile"> 
    						<INPUT class=button id=BtnSubmit onclick="document.getElementById('LayerPrompt7').style.visibility='visible';" type=submit value=开始上传 name=Submit> 
      						<INPUT type=hidden value=9999 name=BasicType> 
      						<INPUT type=hidden value="qitazhengshu" name="imgType">
      						<INPUT type=hidden value=4 name=AutoReName>
      						<input type="hidden" name="id" value='<s:property value="pfmsUser.user.id"/>'/>
      					</TD>
      				</TR>
      			</TBODY>
      		</TABLE>
      	</form>
      	
		<DIV id="LayerPrompt7" style="BORDER-RIGHT: #f9c943 1px solid; BORDER-TOP: #f9c943 1px solid; Z-INDEX: 1; LEFT: 2px; VISIBILITY: hidden; BORDER-LEFT: #f9c943 1px solid; WIDTH: 300px; BORDER-BOTTOM: #f9c943 1px solid; POSITION: absolute; TOP: 0px; HEIGHT: 28px; BACKGROUND-COLOR: #ffffee; layer-background-color: #00CCFF">
		<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
		  <TBODY>
			  <TR>
			    <TD>
			      <DIV>
			      	&nbsp;请稍等，正在上传文件<IMG src="User_UpFile.files/wait.gif" align=absMiddle>
			      </DIV>
			    </TD>
			  </TR>
		  </TBODY>
		  </TABLE>
		</DIV>
	</TD>
  </TR>
  <TR class=tdbg>
    <TD colSpan=2 height=22><span class="STYLE1">其他证件</span></TD>
  </TR>
	</TBODY></TABLE>
  </div>
  
  

<SCRIPT>
	      function CheckPassword(){ 
	      	$("#message").html("");
			if (document.myform_password.oldpassword.value =="")
			{
			alert("请填写您的旧密码！");
			document.myform_password.oldpassword.focus();
			return false;
			}
			if(document.myform_password.oldpassword.value !="")
			{
				return is_old_password_right(document.myform_password.oldpassword.value,"<s:property value='pfmsUser.userId'/>");
			}
			if (document.myform_password.newpassword.value =="")
			{
			alert("请输入您的新密码！");
			document.myform_password.newpassword.focus();
			return false;
			}
			if (parseInt(document.myform_password.newpassword.value.length)<6)
			{
			alert("密码长度必须大于等于6！");
			document.myform_password.newpassword.focus();
			return false;
			}
			if (document.myform_password.renewpassword.value =="")
			{
			alert("请输入您的新确认密码！");
			document.myform_password.renewpassword.focus();
			return false;
			}
			if (document.myform_password.newpassword.value !=document.myform_password.renewpassword.value)
			{
			alert("两次输入的密码不一致！");
			document.myform_password.renewpassword.focus();
			return false;
			}
          return true;			
		}
		
		function is_old_password_right(oldPassword,userId){
			$("#message").html("");
			var flag = false;
			$.ajax({
			  type: 'POST',
			  url: "checkPassword.action",
			  data: {oldPassword:oldPassword,userId:userId},
			  async:false,//同步
			  success: function(data){
		  		data = eval("("+data+")");
		  		if(data.check_json_result){
		  			flag = true;
		  		}
			  }
			});
			
			if(flag == false){
				$("#message").html("您的密码错误,请重新输入!");
				return false;
			}else{
				return true;
			}
		}
    </SCRIPT>
  <div id="passwordSetting" style="display:none;">
  <FORM id=myform_password name=myform_password onSubmit="return CheckPassword();" 
  		action="alterPassword.action?number=3" method=post>
  		<input type="hidden" name="pfmsUser.id" value="${pfmsUser.id }"/>
      <TABLE class=border cellSpacing=1 cellPadding=3 width="98%" align=center 
border=0>
  <TBODY>
	  <TR class=title>
	    <TD align="middle" colSpan=2 height=22>修 改 密 码 </TD></TR>
	  <TR class=tdbg>
	    <TD width="28%" height=22><SPAN style="FONT-WEIGHT: bold">旧 密 码： 
	      </SPAN><BR>您的旧登录密码，必须正确填写。</TD>
	    <TD width="72%">&nbsp; <INPUT class=textbox id=oldpassword type=password 
	      maxLength=50 size=30 name=oldpassword  onfocus="$('#message').html('')"> <SPAN 
	  style="COLOR: red">*</SPAN><span id="message" style="COLOR: red" ></span></TD></TR>
	  <TR class=tdbg>
	    <TD width="28%" height=22><SPAN style="FONT-WEIGHT: bold">新 密 
	      码：</SPAN><BR>请输入您的新密码！</TD>
	    <TD width="72%">&nbsp; <INPUT class=textbox id=newpassword type=password 
	      maxLength=50 size=30 name=newpassword> <SPAN style="COLOR: red">* 
	    </SPAN></TD></TR>
	  <TR class=tdbg>
	    <TD width="28%" height=22><SPAN 
	      style="FONT-WEIGHT: bold">确认密码：</SPAN><BR>同上。</TD>
	    <TD width="72%">&nbsp; <INPUT class=textbox id=renewpassword type=password 
	      maxLength=50 size=30 name=renewpassword> <SPAN style="COLOR: red">* 
	    </SPAN></TD>
	  </TR>
	  <TR class=tdbg>
	    <TD width="28%" height=30>&nbsp;</TD>
	    <TD width="72%"><INPUT class=button type=submit value=" OK,修 改 " name=Submit> 
	      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT class=button type=reset value=" 重 填 " name=Submit2> 
	    </TD>
	  </TR>
  </TBODY>
</TABLE>
  </FORM>
<!-- <TABLE class=border cellSpacing=1 cellPadding=3 width="98%" align=center 
border=0>
  <FORM id=myform1 name=myform1 onSubmit="return CheckForm1();" 
  action=alterAnswer method=post>
  <TBODY>
  <TR class=title>
    <TD align=middle colSpan=2 height=22>更 改 找 回 密 码 设 置</TD></TR>
  <TR class=tdbg>
    <TD width="28%" height=22><SPAN 
      style="FONT-WEIGHT: bold">登录密码：</SPAN><BR>同上。</TD>
    <TD width="72%">&nbsp; <INPUT class=textbox id=Password type=password 
      maxLength=50 size=30 name=Password> <SPAN style="COLOR: red">* 
  </SPAN></TD></TR>
  <TR class=tdbg>
    <TD width="28%" height=22><SPAN 
      style="FONT-WEIGHT: bold">密码问题：</SPAN><BR>当密码忘记时，取回密码的提示问题。</TD>
    <TD width="72%">&nbsp; <INPUT class=textbox id=Question maxLength=50 
      size=30 name=Question> <SPAN style="COLOR: red">* </SPAN></TD></TR>
  <TR class=tdbg>
    <TD width="28%" height=22><SPAN 
      style="FONT-WEIGHT: bold">问题答案：</SPAN><BR>当密码忘记时，取回密码提示问题的答案。</TD>
    <TD width="72%">&nbsp; <INPUT class=textbox id=Answer maxLength=50 size=30 
      name=Answer> <SPAN style="COLOR: red">* </SPAN></TD></TR>
  <TR class=tdbg>
    <TD width="28%" height=30>&nbsp;</TD>
    <TD width="72%"><INPUT class=button type=submit value=" OK,修 改 " name=Submit> 
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT class=button type=reset value=" 重 填 " name=Submit2> 
    </TD></TR></TBODY></FORM></TABLE> -->
</div>
  
<div id="memberProfile" style="display:none;">
	  <FORM id=myform name=myform action="alterMemberProfile.action?number=4" method="post">
	  	<input type="hidden" name="pfmsUser.id" value="${pfmsUser.id }"/>
		<TABLE cellSpacing=1 cellPadding=3 width="98%" align=center border=0>
		  <TBODY>
		  <TR class=tdbg>
		    <TD width="28%" height=22><SPAN 
		      style="FONT-WEIGHT: bold">备注信息：</SPAN><BR>
		      其他需要补充说明的信息<br></TD>
		  </TR>
		  </TBODY>
		</TABLE>
		<div style="text-align: center; width: 100%">
			<s:textarea name="pfmsUser.note" id="note" cols="60" rows="7" cssStyle="width: 100%; height: 440px;; visibility: hidden;" />
		</div>
		<div style="text-align: center;">
			<INPUT class=button type=submit value=" OK,修 改 " name=Submit>
		</div>
	</FORM>
</div>

	</body>
</HTML>

