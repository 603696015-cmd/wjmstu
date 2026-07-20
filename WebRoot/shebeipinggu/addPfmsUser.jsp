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
<META http-equiv=Content-Type content="text/html; charset=utf-8"><LINK 
href="<%=path %>/css/skin.css" type=text/css rel=stylesheet><LINK 
href="<%=path %>/css/css.css" type=text/css rel=stylesheet>
<LINK 
href="<%=path %>/css/houtai.css" type=text/css rel=stylesheet>
<META http-equiv=X-UA-Compatible content=IE=7>
<SCRIPT language=javascript src="<%=path %>/js/common.js"></SCRIPT>
<script type="text/javascript" src="js/etc/citiesJson.js"></script>

<SCRIPT language=javascript src="<%=path %>/js/jquery.js"></SCRIPT>
<script type="text/javascript" src="<%=path %>/editor/fckeditor.js"></script>
<script type="text/javascript" src="<%=path %>/js/stuffman.js"></script>
<META content="MSHTML 6.00.2900.6197" name=GENERATOR></HEAD>
<BODY bottomMargin=0 leftMargin=0 topMargin=0 rightMargin=0 onload="myload();">
<DIV class=title style="PADDING-LEFT: 6px; LINE-HEIGHT: 30px; HEIGHT: 30px"><A 
href="http://www.sopia.cc/" target=_parent>网站首页</A> &gt;&gt; <A 
href="http://www.sopia.cc/user/user_main.asp">会员中心</A> &gt;&gt; <SPAN 
class=shadow id=locationid></SPAN></DIV>
<script type="text/javascript">
	function searchUserInit(){
	     width=600;
		 height=500;
	  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
		 var rv =  window.showModalDialog("userRegister.action?x="+Math.random(),null,sFeature);
		 //alert(rv);
		 if(rv!=undefined&&rv!=""){
			 //var bh=rv.split("_");
			 var bh=rv.split("-=wys=-");
			 document.getElementById("danwei").value=bh[2];
			 document.getElementById("danweiName").value=bh[1];
		 }
	}
	
</script>
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
		}else if(value == "相关复印件"){
			li_one.className = "";
			li_two.className = "select";
			li_three.className = "";
		}else if(value == "会员简介"){
			li_one.className = "";
			li_two.className = "";
			li_three.className = "select";
		}else{
			return ;
		}
	}
	
	function showOneDiv(value){
		var pfmsBaseInfo = document.getElementById("pfmsBaseInfo");
		var relevantCopies = document.getElementById('relevantCopies');
		var memberProfile = document.getElementById('memberProfile');
		if(value == "基本信息"){
			pfmsBaseInfo.style.display = "block";
			relevantCopies.style.display = "none";
			memberProfile.style.display = "none";
		}else if(value == "相关复印件"){
			pfmsBaseInfo.style.display = "none";
			relevantCopies.style.display = "block";
			memberProfile.style.display = "none";
		}else if(value == "会员简介"){
			pfmsBaseInfo.style.display = "none";
			relevantCopies.style.display = "none";
			memberProfile.style.display = "block";
		}else{
			return ;
		}
	}
</script>

<script type="text/javascript">
		var provinces  = new Array();
		var cities = new Array();
		var counties = new Array();
		$(document).ready(function(){
			var citiesString = ss;
			
			var array = eval("("+citiesString+")") ;//array数组
			
			var o = 0;//将定义的provinces数组下标从0开始
			var p = 0;
			var q = 0;
			$.each(array,function(i,n){
				if(array[i].type == "PROVINCE"){
					provinces[o] = array[i];
					$("<option ></option>").val(n.id+" "+n.name).text(n.name)
                  		.appendTo($("#province"));
                  	o++;
				}
				
				else if(array[i].type == "CITY"){
					cities[p] = array[i];
					p++;
				}
				else if(array[i].type == "DISTRICT"){
					counties[q] = array[i];
					q++;
				}
				
			});
			
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
		
		function changeCity(city){
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
<script type="text/javascript">
		function changeCity_ajax(city){
			alert(city);
			$.ajax({
			  type: 'POST',
			  url: "getCity_ajax.action",
			  data: {city_selected:city,city_type:"CITY"},
			  async:false,//同步
			  success: function(data){
			  	var areaListObj = eval("("+data+")").areaList;
			  	//	console.log($("#city option"));
			  	//$("#city").remove().hide();
			  	
			  	//$("<select id='city' name='city' onchange='changeCity("");' style='width:100'></select>").appendTo($("#province"));
				$.each(areaListObj,function(i,city){
                   	$("<option ></option>").val(city.id+" "+city.name).text(city.name)
                   		.appendTo($("#city"));
				});
				
			  }
			});
		}
</script>
<DIV class=tabs>
	<UL>
		<LI id="li_one" class="select"><A href="#" onclick="showOneDiv(this.innerHTML);select(this.innerHTML);">基本信息</A> 
		</LI>
		<LI id="li_two" class=""><A href="#" onclick="showOneDiv(this.innerHTML);select(this.innerHTML);">相关复印件</A> 
		</LI>
		<LI id="li_three" class=""><A href="#" onclick="showOneDiv(this.innerHTML);select(this.innerHTML);">会员简介</A>
		</LI>
	</UL>
</DIV>
<SCRIPT type=text/javascript>$('#locationid').html("添加基本信息");</SCRIPT>
<SCRIPT> 
	function myload(){
				var oFCKeditor = new FCKeditor("note") ;
				oFCKeditor.BasePath = "editor/" ;
				oFCKeditor.Height = 400;
				oFCKeditor.Width = 980;
				oFCKeditor.ReplaceTextarea();
				
				setCurTime("releasetime");
				
				 
				var showimages = document.getElementsByName('showimages');  
				for(var i = 0 ;i <showimages.length;++i){ 
					showimages[i].src = showimages[i].src +"?"+ Math.round(Math.random() * 10000000);   
				}
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
       	 
    </SCRIPT>
<script type="text/javascript">
	function checkForm(){
		var partten;
		if(document.getElementById("username").value == ""){
			alert("会员账号不能为空,请填写!");
			document.getElementById("username").focus();
			return false;
		}
		if(document.getElementById("password").value == ""){
			alert("会员密码不能为空,请填写!");
			document.getElementById("password").focus();
			return false;
		}
		if(document.getElementById("realname").value == ""){
			alert("会员名称不能为空,请填写!");
			document.getElementById("realname").focus();
			return false;
		}
		if(document.getElementById("danweiName").value == ""){
			alert("单位\部门不能为空,请填写!");
			document.getElementById("danweiName").focus();
			return false;
		}
		if(document.getElementById("roleid").value == ""){
			alert("用户权限不能为空,请填写!");
			document.getElementById("roleid").focus();
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
			partten = /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{8}$/;
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
  <FORM  onsubmit="return checkForm();" action="add_pfmsUser.action" method=post id="add_pfmsUser_submit" name="add_pfmsUser">
  	<s:hidden name="depid" id="danwei" />
  	<!-- <input type="hidden" name="pfmsUser.note" id="huiyuanjianjie"/> -->
<div id="pfmsBaseInfo" style="display:block;">

<TABLE cellSpacing=1 cellPadding=3 width="98%" align=center border=0>
  <TBODY>
  <TR class=tdbg>
    <td width="25%" height=22>
		<SPAN style="FONT-WEIGHT: bold">
			照片：
		</SPAN>
	</td>
	<td width="25%">&nbsp;&nbsp;
		<label>
			<input class=textbox type="text"  id="pic" maxLength=50  size=30 name="pfmsUser.head" />
			<a href="javascript:setUrl('pic');" class="textbg">浏览资源库</a>
		</label>
	</td>
  </TR>
		  
  <TR class=tdbg>
    <TD width="28%" height=22><SPAN style="FONT-WEIGHT: bold">会员账号：
      </SPAN><BR>
      用于登录服务系统的用户名，不可更改</TD>
    <TD width="72%">&nbsp; <INPUT class=textbox id="username" maxLength=50 
      size=30 name="pfmsUser.user.username" ></TD>
  </TR>
  
  <TR class=tdbg>
    <TD width="28%" height=22><SPAN style="FONT-WEIGHT: bold">会员密码：
      </SPAN><BR>
      用于登录服务系统的密码</TD>
    <TD width="72%">&nbsp; <INPUT type="password" class=textbox id="password" maxLength=50 
      size=30 name="pfmsUser.user.password" ></TD>
  </TR>
  
  <tr>
		<td width="28%" height=22><SPAN style="FONT-WEIGHT: bold" >
			<strong>单位/部门：</strong>
		</td>
		<td width="72%" >&nbsp;
			 <s:textfield theme="simple" name="pfmsUser.user.department.name" 
				size="20" id="danweiName" readonly="true" />
			 <a href="#" onClick="searchUserInit();return false;">点此进行选择</a>
		</td>
	</tr>
  
  <tr class=tdbg>
		<td width="28%" height=22>
			<SPAN 
      style="FONT-WEIGHT: bold">权限：</SPAN></td>
		<td width="72%" >
			<select name="pfmsUser.user.role.id" id="roleid">
				<s:iterator value="roles">
					<option
						value="<s:property value="id"/>">
							<s:property value="name" />
					</option>
				</s:iterator>
			</select>
		</td>
	</tr>
  
  
  <TR class=tdbg>
    <TD width="28%" height=22><SPAN 
      style="FONT-WEIGHT: bold">会员名称：</SPAN><BR>
      单位会员填写单位名称，个人会员填写姓名</TD>
    <TD width="72%">&nbsp;&nbsp; <INPUT class=textbox id="realname" maxLength=50 
      size=30 name="pfmsUser.user.realname" > </TD></TR>
	  <TR class=tdbg>
	  <TR class=tdbg>
    <TD width="28%" height=22><SPAN 
      style="FONT-WEIGHT: bold">单位代码：</SPAN><BR>
      单位会员填写单位名称，个人会员填写姓名</TD>
    <TD width="72%">&nbsp;&nbsp; <INPUT class=textbox id="danwei" maxLength=50 
      size=30 name="pfmsUser.user.danwei" > </TD></TR>
  <TR class=tdbg>
  	<TD width="28%" height=22><SPAN 
      style="FONT-WEIGHT: bold">会员类型：<s:property value="pfmsUser.huiyuanleixing"/></SPAN></TD>
    <TD width="72%">&nbsp;&nbsp; 
    	<select name="pfmsUser.huiyuanleixing" 
			onchange="this.value=this.options[this.selectedIndex].value">
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
      style="FONT-WEIGHT: bold">负责人姓名：</SPAN><BR>
      法人代表或常用联系人姓名</TD>
    <TD width="72%">&nbsp;&nbsp; <INPUT class=textbox id="respName" maxLength=50 
      size=30 name="pfmsUser.respName" ></TD></TR>
	   
	  
  <TR class=tdbg>
    <TD width="28%" height=22><SPAN 
      style="FONT-WEIGHT: bold">性&nbsp;&nbsp;&nbsp; 别：</SPAN><BR></TD>
    <TD width="72%">&nbsp;&nbsp; <SELECT id=sex style="WIDTH: 110px" name="pfmsUser.user.sex"> 
        <OPTION value=男 selected>男</OPTION> 
        <OPTION value=女>女</OPTION></SELECT> </TD></TR>
  
  <tr>
    	<TD width="28%" height=22><SPAN 
      style="FONT-WEIGHT: bold">省&nbsp;市&nbsp;县&nbsp;：</SPAN><BR></TD>
    	<td>
    	<select id="province" name="province" onchange="changeProvince();" style="width:100">  
	         <option id="option_in_province">  
	         	请选择省
	         </option>  
		</select>&nbsp;
		<select id="city" name="city" onchange="changeCity();" style="width:100" >  
	         <option id="option_in_city" >  
	              请选择市  
	         </option>  
		</select>&nbsp;
		<select id="county" name="county" onchange="" style="width:100">  
	         <option id="option_in_county">  
	              请选择县
	         </option>  
		</select>&nbsp;
    	</td>
    </tr>
  
  <TR class=tdbg>
    <TD width="28%" height=22><SPAN 
      style="FONT-WEIGHT: bold">身份证号：</SPAN><BR>有效身份证号码应该是15位或18位，请认真填写。</TD>
    <TD width="72%">&nbsp;&nbsp; <INPUT class=textbox id="shenfenzheng" maxLength=50 
      size=30 name="pfmsUser.user.shenfenzheng" ></TD></TR>
  
  <TR class=tdbg>
    <TD width="28%" height=22><SPAN 
      style="FONT-WEIGHT: bold">地址：</SPAN><BR>
      单位所在地、经营场所地或其他地址</TD>
    <TD width="72%">&nbsp;&nbsp; <INPUT class=textbox id="address" maxLength=50 
      size=30 name="pfmsUser.address" > </TD></TR>
	  <TR class=tdbg>
    <TD width="28%" height=22><SPAN 
      style="FONT-WEIGHT: bold">电话：</SPAN><BR>
      单位常用对外联系电话或负责人电话</TD>
    <TD width="72%">&nbsp;&nbsp; <INPUT class=textbox id="mobile" maxLength=50 
      size=30 name="pfmsUser.mobile" > </TD></TR>
	  <TR class=tdbg>
    <TD width="28%" height=22><SPAN 
      style="FONT-WEIGHT: bold">手机：</SPAN><BR>
      负责人手机号码</TD>
    <TD width="72%">&nbsp;&nbsp; <INPUT class=textbox id=movephone maxLength=50 
      size=30 name="pfmsUser.user.movephone" > </TD></TR>
	  <TR class=tdbg>
    <TD width="28%" height=22><SPAN 
      style="FONT-WEIGHT: bold">传真：</SPAN><BR>
      常用业务来往传真电话</TD>
    <TD width="72%">&nbsp;&nbsp; <INPUT class=textbox id="fex" maxLength=50 
      size=30 name="pfmsUser.fex" > </TD></TR>
  <TR class=tdbg>
    <TD height=22><SPAN 
      style="FONT-WEIGHT: bold">邮箱地址：</SPAN><BR>
    请填写正确的邮箱地址，如：service@sina.com</TD>
    <TD>&nbsp;&nbsp; <INPUT class=textbox id="email" maxLength=50 size=30 
       name="pfmsUser.email" > </TD></TR>
  
  <TR class=tdbg>
    <TD width="28%" height=30>&nbsp;</TD> 
    <TD width="72%"><INPUT class=button type=submit value=" OK,添加 " name=Submit "> 
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   
    </TD>
 </TR>
  </TBODY>
  </TABLE>
   
  </div>
  

<SCRIPT type=text/javascript>
     function changeimage()
	 {
		  $("#UserFace").val("<%=path %>/images/face/"+$("#Image").val()+".gif");
		  $("#imgIcon").attr("src",'/Images/Face/'+$("#Image").val()+'.gif');
	 }
	 
</SCRIPT>

<div id="memberProfile" style="display:none;">
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
		<s:textarea name="pfmsUser.note" id="note" cols="60" rows="7"  cssStyle="width: 100%; height: 440px; visibility: hidden;" />
	</div>
</div>
</FORM>


  <div id="relevantCopies" style="display:none;">
<TABLE width="80%" border=1 align=center cellPadding=3 cellSpacing=0 bordercolor="#333333">
  <TBODY>
  <TR class=tdbg>
    <TD colSpan=2 height=50><SPAN 
      style="FONT-WEIGHT: bold; FONT-SIZE: 14px; COLOR: green">会员相关证件的扫描件可以在此进行上传和修改，每类证件都有对应的格式要求，上传前请进行相应修改并确认！</SPAN></TD>
  </TR>
  <TR class=tdbg>
    <TD colSpan=2 height=22><span class="STYLE1">营业执照</span></TD>
  </TR>
  <TR>
    <TD rowspan="2" align=center>
     <IMG id=imgIcon1 title=点击选择头像 style="CURSOR: hand" 
      onclick="window.open('selectface.asp?action=face','face','width=480,height=400,resizable=1,scrollbars=1')" 
      height=60 src="<%=path %>/images/pfms/yingyezhizhao_${pfmsUser.userId }.${pfmsUser.yingyezhizhao }" width=60 
      border=1 name=showimages> <BR>
        <A 
      onclick="window.open('selectface.asp?action=face','face','width=480,height=400,resizable=1,scrollbars=1')" 
      href="http://www.sopia.cc/user/User_EditInfo.asp?Action=face#"><FONT 
      color=red>查看大图</FONT></A> 
    </TD>
    <TD>编号
      <INPUT class=textbox id=RealName maxLength=50 
      size=30 name=RealName>
      <SPAN style="COLOR: red">* </SPAN><br>
只支持jpg、gif、png，小于500k，默认尺寸为600*800 
      <BR>
  		<form name="yingyezhizhao" action="uploadFile.action" method="post" enctype="multipart/form-data">
      		<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  				<TBODY>
  					<TR class=tdbg>
    					<TD vAlign=top>
    						<input type="hidden" name="id" value='<s:property value="pfmsUser.user.id"/>'/>
    						<INPUT class=textbox type="file" accept=html size=30 name="imgFile"> 
    						<INPUT class=button id=BtnSubmit onclick="document.getElementById('LayerPrompt1').style.visibility='visible';" type=submit value=开始上传 name=Submit> 
      						<INPUT type=hidden value="yingyezhizhao" name=imgType>  
      						<INPUT type=hidden value=9999 name=BasicType> 
      						<INPUT type=hidden value=4 name=AutoReName>
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
    <TD colSpan="2" height="22"><span class="STYLE1">税务登记证</span></TD>
  </TR>
  <TR>
    <TD rowspan="2" align=center>
     <IMG id=imgIcon2 title=点击选择头像 style="CURSOR: hand" 
      onclick="window.open('selectface.asp?action=face','face','width=480,height=400,resizable=1,scrollbars=1')" 
      height=60 src="<%=path %>/images/pfms/shuiwudengjizheng_${pfmsUser.userId }.${pfmsUser.shuiwudengjizheng }" width=60 
      border=1 name=showimages> <BR>
        <A 
      onclick="window.open('selectface.asp?action=face','face','width=480,height=400,resizable=1,scrollbars=1')" 
      href="http://www.sopia.cc/user/User_EditInfo.asp?Action=face#"><FONT 
      color=red>查看大图</FONT></A> </TD>
    <TD>编号
      <INPUT class=textbox id=RealName maxLength=50 
      size=30 name=RealName>
      <SPAN style="COLOR: red">* </SPAN><br>
只支持jpg、gif、png，小于500k，默认尺寸为600*800 
      <BR>
      <form name="shuiwudengjizheng" action="uploadFile.action" method="post" encType="multipart/form-data">
      		<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  				<TBODY>
  					<TR class=tdbg>
    					<TD vAlign=top>
    						<INPUT class=textbox type=file accept=html size=30 name="imgFile"> 
    						<INPUT class=button id=BtnSubmit onclick="document.getElementById('LayerPrompt2').style.visibility='visible';" type=submit value=开始上传 name=Submit> 
      						<INPUT type=hidden value="shuiwudengjizheng" name=imgType> 
      						<INPUT type=hidden value=9999 name=BasicType> 
      						<INPUT type=hidden value=4 name=AutoReName>
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
    <TD colSpan=2 height=22><span class="STYLE1">组织机构代码证</span></TD>
  </TR>
  <TR>
    <TD rowspan="2" align=center>
     <IMG id=imgIcon3 title=点击选择头像 style="CURSOR: hand" 
      onclick="window.open('selectface.asp?action=face','face','width=480,height=400,resizable=1,scrollbars=1')" 
      height=60 src="<%=path %>/images/pfms/zuzhijigoudaimazheng_${pfmsUser.userId }.${pfmsUser.zuzhijigoudaimazheng }" width=60 
      border=1 name=showimages> <BR>
        <A 
      onclick="window.open('selectface.asp?action=face','face','width=480,height=400,resizable=1,scrollbars=1')" 
      href="http://www.sopia.cc/user/User_EditInfo.asp?Action=face#"><FONT 
      color=red>查看大图</FONT></A> </TD>
    <TD>编号
      <INPUT class=textbox id=RealName maxLength=50 
      size=30 name=RealName>
      <SPAN style="COLOR: red">* </SPAN><br>
只支持jpg、gif、png，小于500k，默认尺寸为600*800 
      <BR>
      <form name="zuzhijigoudaimazheng" action="uploadFile.action" method="post" encType="multipart/form-data">
      		<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  				<TBODY>
  					<TR class=tdbg>
    					<TD vAlign=top>
    						<INPUT class=textbox type=file accept=html size=30 name="imgFile"> 
    						<INPUT class=button id=BtnSubmit onclick="document.getElementById('LayerPrompt3').style.visibility='visible';" type=submit value=开始上传 name=Submit> 
      						<INPUT type=hidden value=9999 name=BasicType> 
      						<INPUT type=hidden value="zuzhijigoudaimazheng" name="imgType"> 
      						<INPUT type=hidden value=4 name=AutoReName>
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
    <TD colSpan=2 height=22><span class="STYLE1">法人身份证</span></TD>
  </TR>
  <TR>
    <TD rowspan="2" align=center>
     <IMG id=imgIcon4 title=点击选择头像 style="CURSOR: hand" 
      onclick="window.open('selectface.asp?action=face','face','width=480,height=400,resizable=1,scrollbars=1')" 
      height=60 src="<%=path %>/images/pfms/farenshenfenzheng_${pfmsUser.userId }.${pfmsUser.farenshenfenzheng }" width=60 
      border=1 name=showimages> <BR>
        <A 
      onclick="window.open('selectface.asp?action=face','face','width=480,height=400,resizable=1,scrollbars=1')" 
      href="http://www.sopia.cc/user/User_EditInfo.asp?Action=face#"><FONT 
      color=red>查看大图</FONT></A> </TD>
    <TD>编号
      <INPUT class=textbox id=RealName2 maxLength=50 
      size=30 name=RealName2>
      <SPAN style="COLOR: red">* </SPAN><br>
只支持jpg、gif、png，小于500k，默认尺寸为600*800 
      <BR>
      <form name="farenshenfenzheng" action="uploadFile.action" method="post" encType="multipart/form-data">
      		<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  				<TBODY>
  					<TR class=tdbg>
    					<TD vAlign=top>
    						<INPUT class=textbox type=file accept=html size=30 name="imgFile"> 
    						<INPUT class=button id=BtnSubmit onclick="document.getElementById('LayerPrompt4').style.visibility='visible';" type=submit value=开始上传 name=Submit> 
      						<INPUT type=hidden value=9999 name=BasicType> 
      						<INPUT type=hidden value="farenshenfenzheng" name="imgType"> 
      						<INPUT type=hidden value=4 name=AutoReName>
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
    <TD colSpan=2 height=22><span class="STYLE1">资质等级证书 </span></TD>
  </TR>
  <TR>
    <TD rowspan="2" align=center>
     <IMG id=imgIcon5 title=点击选择头像 style="CURSOR: hand" 
      onclick="window.open('selectface.asp?action=face','face','width=480,height=400,resizable=1,scrollbars=1')" 
      height=60 src="<%=path %>/images/pfms/zizhidengjizhengshu_${pfmsUser.userId }.${pfmsUser.zizhidengjizhengshu }" width=60 
      border=1 name=showimages> <BR>
        <A 
      onclick="window.open('selectface.asp?action=face','face','width=480,height=400,resizable=1,scrollbars=1')" 
      href="http://www.sopia.cc/user/User_EditInfo.asp?Action=face#"><FONT 
      color=red>查看大图</FONT></A> </TD>
    <TD>编号
      <INPUT class=textbox id=RealName3 maxLength=50 
      size=30 name=RealName3>
      <SPAN style="COLOR: red">* </SPAN><br>
只支持jpg、gif、png，小于500k，默认尺寸为600*800 
      <BR><form name="zizhidengjizhengshu" action="uploadFile.action" method="post" encType="multipart/form-data">
      		<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  				<TBODY>
  					<TR class=tdbg>
    					<TD vAlign=top>
    						<INPUT class=textbox type=file accept=html size=30 name="imgFile"> 
    						<INPUT class=button id=BtnSubmit onclick="document.getElementById('LayerPrompt5').style.visibility='visible';" type=submit value=开始上传 name=Submit> 
      						<INPUT type=hidden value=9999 name=BasicType> 
      						<INPUT type=hidden value="zizhidengjizhengshu" name="imgType">
      						<INPUT type=hidden value=4 name=AutoReName>
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
    <TD colSpan=2 height=22><span class="STYLE1">信用等级评估证书 </span></TD>
  </TR>
  <TR>
    <TD rowspan="2" align=center>
     <IMG id=imgIcon6 title=点击选择头像 style="CURSOR: hand" 
      onclick="window.open('selectface.asp?action=face','face','width=480,height=400,resizable=1,scrollbars=1')" 
      height=60 src="<%=path %>/images/pfms/xinyongdengjipingguzhengshu_${pfmsUser.userId }.${pfmsUser.xinyongdengjipingguzhengshu }" width=60 
      border=1 name=showimages> <BR>
        <A 
      onclick="window.open('selectface.asp?action=face','face','width=480,height=400,resizable=1,scrollbars=1')" 
      href="http://www.sopia.cc/user/User_EditInfo.asp?Action=face#"><FONT 
      color=red>查看大图</FONT></A> </TD>
    <TD>编号
      <INPUT class=textbox id=RealName4 maxLength=50 
      size=30 name=RealName4>
      <SPAN style="COLOR: red">* </SPAN><br>
只支持jpg、gif、png，小于500k，默认尺寸为600*800 
      <BR><form name="xinyongdengjipingguzhengshu" action="uploadFile.action" method="post" encType="multipart/form-data">
      		<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  				<TBODY>
  					<TR class=tdbg>
    					<TD vAlign=top>
    						<INPUT class=textbox type=file accept=html size=30 name="imgFile"> 
    						<INPUT class=button id=BtnSubmit onclick="document.getElementById('LayerPrompt6').style.visibility='visible';" type=submit value=开始上传 name=Submit> 
      						<INPUT type=hidden value=9999 name=BasicType> 
      						<INPUT type=hidden value="xinyongdengjipingguzhengshu" name="imgType"> 
      						<INPUT type=hidden value=4 name=AutoReName>
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
    <TD colSpan=2 height=22><span class="STYLE1">其他证件</span></TD>
  </TR>
  <TR>
    <TD rowspan="2" align=center>
     <IMG id=imgIcon7 title=点击选择头像 style="CURSOR: hand" 
      onclick="window.open('selectface.asp?action=face','face','width=480,height=400,resizable=1,scrollbars=1')" 
      height=60 src="<%=path %>/images/pfms/qitazhengshu_${pfmsUser.userId }.${pfmsUser.qitazhengshu }" width=60 
      border=1 name=showimages> <BR>
        <A 
      onclick="window.open('selectface.asp?action=face','face','width=480,height=400,resizable=1,scrollbars=1')" 
      href="http://www.sopia.cc/user/User_EditInfo.asp?Action=face#"><FONT 
      color=red>查看大图</FONT></A> </TD>
    <TD>编号
      <INPUT class=textbox id=RealName5 maxLength=50 
      size=30 name=RealName5>
      <SPAN style="COLOR: red">* </SPAN><br>
只支持jpg、gif、png，小于500k，默认尺寸为600*800 
      <BR><form name="qitazhengshu" action="uploadFile.action" method="post" encType="multipart/form-data">
      		<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  				<TBODY>
  					<TR class=tdbg>
    					<TD vAlign=top>
    						<INPUT class=textbox type=file accept=html size=30 name="imgFile"> 
    						<INPUT class=button id=BtnSubmit onclick="document.getElementById('LayerPrompt7').style.visibility='visible';" type=submit value=开始上传 name=Submit> 
      						<INPUT type=hidden value=9999 name=BasicType> 
      						<INPUT type=hidden value="qitazhengshu" name="imgType">
      						<INPUT type=hidden value=4 name=AutoReName>
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
	</TBODY></TABLE>
  </div>
  
 
</BODY>
</HTML>

