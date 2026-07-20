<%@ page language="java" pageEncoding="UTF-8"%>
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

<HTML><head>
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
  <meta name="MobileOptimized" content="320">
	
	<TITLE>中国食品安全培训网</TITLE>
	    <style type="text/css">

.STYLE6 {font-size: 30px}
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
body,td,th {
	font-size: 18px;
}
.STYLE3 {font-size: 24px}
.STYLE5 {color: #000000; font-size: 18px; }

        </style>

		
 
		<META content="text/html; charset=utf-8" http-equiv=Content-Type>
		
		<META name=keywords
			content="北京,卫生,法学会,中国,食品,安全,培训网">
		<META name=description
			content="北京卫生法学会,中国食品安全培训网 ">

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
</script>


		
		

	</HEAD>
	<BODY>
	
    <table width="100%" border="0" cellspacing="0" cellpadding="0" style="BORDER-BOTTOM: #333333 1px solid">
      <tr>
        <td height="70" background="http://fhse.net/wsj_phone/images/wapbannerbg.png"><img src="http://fhse.net/wsj_phone/images/wapbanner.jpg" width="320" height="70"></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" style="BORDER-BOTTOM: #333333 1px solid">
     <tr>
       <td width="18%" height="40" align="center" bgcolor="#66CCFF"><a href="index.action"><span style="font-size:18px;color:white;">首 页</span></a></td>
       <td width="18%" align="center" bgcolor="#66CCFF"><a href="newsIndex.action"><span style="font-size:18px;color:white;">新 闻</span></a></td>
       <td width="18%" align="center" bgcolor="#66CCFF"><a href="forumIndex.action"><span style="font-size:18px;color:white;">论 坛</span></a></td>
       <td width="18%" align="center" bgcolor="#66CCFF"><a href="newsIndex.action"><span style="font-size:18px;color:white;">帮 助</span></a></td>
       <td width="28%" align="center" bgcolor="#66CCFF"><a href="cisco_user_center.action"><span style="font-size:18px;color:white;">个人中心</span></a></td>
     </tr>
   </table>
   <table width="100%" border="0" cellspacing="0" cellpadding="0">
     <tr>
       <td width="100" height="35" align="center" bgcolor="#CFDBE2" class="STYLE5">用户登陆</td>
       <td bgcolor="#CFDBE2">&nbsp;</td>
     </tr>
   </table>
  
   <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
     <tr>
       <td ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
           <tr>
             <td> <table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0" bgcolor="#E3F6FD">
							<tr>
								<td height="197" align="center" valign="middle">
									<form name=myform action=cisco_user_center_index.action style="margin: 0px;"
										method=post>
										<input type="hidden" name="isFromRegister" value="1" />
										<table width="98%" border=0
											align=center cellpadding=5 cellspacing=1 bgcolor="#FFFFFF" style="margin-top: 5px;">
											<tbody>
												<tr>
													<td height=25 bgcolor="#E3F6FD">
														用户名：
														<input style="width:200px;height:30px;" id=Username name="elUser.username" />
												  </td>
												</tr>
												<tr>
													<td height=35 bgcolor="#E3F6FD">
														密 &nbsp;&nbsp; 码：
														<input style="width:200px;height:30px;" type=password
															name="elUser.password"  width="153px"/>
												  </td>
												</tr>
												<tr>
												<td height=35 bgcolor="#E3F6FD">
														验证码：
														<input style="width:120px;height:30px;"
															size=6 name=yzCode />&nbsp;&nbsp;&nbsp;&nbsp;
														<img height="30" width="57" align="bottom"
															src="image2.jsp"
															onClick="this.src='image.jsp?'+Math.random()"
															title="点击刷新验证码" />
													</td>
												</tr>
												<tr>
													<td height=25 bgcolor="#E3F6FD">
														<div align=center style="margin-top: 5px;">
															<input class=textbg4 onclick=return(CheckForm())
																type=submit value=登录 name=Submit />
															
														</div>
												  </td>
												</tr>
											</tbody>
									  </table>
									</form>
								</td>
							</tr>
					  </table></td>
           </tr>
       </table></td>
     </tr>
   </table>
   
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td height="50" align="center" bgcolor="#CFDBE2"><a href="#" style="text-align:center; font-size:22px; color:#333; background-color:rgb(252,203,0); text-decoration:none;padding:5px; border-bottom-width:1px; border-bottom-color:#d0ac19; border-bottom-style:solid;">↑回顶部</a></td>
              </tr>
            </table>
	
	</body>
</HTML>


