//身份证验证
var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ]; 
var ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ];  
function IdCardValidate(idCard) {   
    idCard = trim(idCard.replace(/ /g, ""));   
    if (idCard.length == 15) {   
        return isValidityBrithBy15IdCard(idCard);   
    } else if (idCard.length == 18) {   
        var a_idCard = idCard.split("");
        if(isValidityBrithBy18IdCard(idCard)&&isTrueValidateCodeBy18IdCard(a_idCard)){   
            return true;   
        }else {   
            return false;   
        }   
    } else {   
        return false;   
    }   
}   
function isTrueValidateCodeBy18IdCard(a_idCard) {   
    var sum = 0; 
    if (a_idCard[17].toLowerCase() == 'x') {   
        a_idCard[17] = 10;
    }   
    for ( var i = 0; i < 17; i++) {   
        sum += Wi[i] * a_idCard[i];
    }   
    valCodePosition = sum % 11;
    if (a_idCard[17] == ValideCode[valCodePosition]) {   
        return true;   
    } else {   
        return false;   
    }   
}
function isValidityBrithBy15IdCard(idCard15){   
      var year =  idCard15.substring(6,8);   
      var month = idCard15.substring(8,10);   
      var day = idCard15.substring(10,12);   
      var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
      // 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法   
      if(temp_date.getYear()!=parseFloat(year)   
              ||temp_date.getMonth()!=parseFloat(month)-1   
              ||temp_date.getDate()!=parseFloat(day)){   
                return false;   
        }else{   
            return true;   
        }   
  }   
    
//function maleOrFemalByIdCard(idCard){   
//    idCard = trim(idCard.replace(/ /g, ""));
//    if(idCard.length==15){   
//        if(idCard.substring(14,15)%2==0){   
//            return 'female';   
//        }else{   
//            return 'male';   
//        }   
//    }else if(idCard.length ==18){   
//        if(idCard.substring(16,17)%2==0){ alert(  idCard.substring(16,17));
//            return 'female';   
//        }else{   alert(  idCard.substring(16,17));
//            return 'male';   
//        }   
//    }else{   
//        return null;
//    }   
//}    
function isValidityBrithBy18IdCard(idCard18){   
    var year =  idCard18.substring(6,10);   
    var month = idCard18.substring(10,12);   
    var day = idCard18.substring(12,14);   
    var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
    if(temp_date.getFullYear()!=parseFloat(year)   
          ||temp_date.getMonth()!=parseFloat(month)-1   
          ||temp_date.getDate()!=parseFloat(day)){   
            return false;   
    }else{   
        return true;   
    }   
}

//根据身份证号码返回出生日期，比如1986-08-09
function BirthGetFromCard(card)
{
    var nian,yue,ri; 
    card=trim(card);  
    if (card.length==15 )   
    {   
        nian=card.substr(6,2);   
        yue=card.substr(8,2);   
        ri=card.substr(10,2);   
        return "19"+nian+"-"+yue+"-"+ri;     
    }   
    if (card.length==18 )   
    {   
        nian=card.substr(6,4);   
        yue=card.substr(10,2);   
        ri=card.substr(12,2);   
        return nian+"-"+yue+"-"+ri;     
    }   
}
//返回日期控件
 function getDataPicker()
    {
   return WdatePicker({minDate:$I('hdnMinDate').value,maxDate:$I('hdnMaxDate').value});
    }

//根据身份证号码返回性别，0-男，1-女
function MaleGetFromCard(card)
{
    card=trim(card);
    var male;
    if (card.length==15 )   
    {   
       male=card.substr(14,1) ; 
      
       if(male%2==0)
       {
         return 1;
       }
       else
       {
          return 0;
       }
    }   
    if (card.length==18 )   
    {   
        male=card.substr(16,1);
        if(male%2==0)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
    
}
//长度验证，text-验证字符，erroText-前导字符，erroId-错误显示id(innerHTML），maxLength-最大长度
function LengthValidate(text,erroText,erroId,maxLength)
{



    if(text.length==0)
    {
   
        $I(erroId).innerHTML=erroText+"不能为空";
        return false;
    }
    else if(text.length>maxLength)
    {
        $I(erroId).innerHTML=erroText+"不能大于"+maxLength;
        return false;
    }
}
///最大长度不能大于maxLength
function MaxLengthValidate(text,erroText,erroId,maxLength)
{
   if(text.length>maxLength)
    {
        $I(erroId).innerHTML=erroText+"不能大于"+maxLength;
        return false;
    } 
}
//身份证号码验证输出结果
function IdentityVerify(text,erro,erroText)
{   
    if(IdCardValidate(text)==false)
    {
        $I(erro).innerHTML=erroText+"格式不对";
        return false;
    }
}
//单纯身份证验证
function IdentityVerifyPule(id,erro)
{
    if(IdCardValidate($I(id).value)==false)
    {
        $I(erro).innerHTML="身份证号码不符合要求";
        return false;
    }
}
//护照验证并输出结果
function PassportVerify(text,erro,erroText)
{  
    if(LengthValidate(text,erroText,erro,50)==false)
    {
        return false;
    }
}
//邮编验证
function postCodeValidate(id,erro,type)
{
    var text=$I(id).value;
    var r=/^[0-9][0-9]{5}$/;
    text=text.replace(/^\s+|\s+$/g,""); 
    if(type.toString()=='0')
    {
        if(text=="")
        {
          $I(erro).innerHTML="不能为空";
          return false;
        }
    }
    if(text!="")
    {
        if( r.test(text)==false)
        {
           $I(erro).innerHTML="格式不对";
           return false;
        } 
    }
}
//投保人证件号码验证以及性别验证oid-前导控件id,id-待验证id,erro-错误id,maleid
function IdentityValidate(oid,id,erro,maleid,femalid)
{
    var card=trim($I(id).value);
    var oValue=$I(oid).value;
    if(oValue=='0')
    {   
        if(card=='')
        {
            $I(erro).innerHTML='证件号码不能为空';
            return false;
        }
        else
        {
            if(IdentityVerify(card,erro,'证件号码')==false)
            {
                return false;
            }
            if(MaleGetFromCard(card)==0) {
                if ($I(maleid) != null) {
                    $I(maleid).checked = true;
                }
            }
            else
            {if ($I(femalid) != null) {
                    $I(femalid).checked = true;
                }
            }            
        }
    }
    else
    {
        if(PassportVerify(card,erro,'证件号码')==false)
        {
            return false;
        }
    }
}
//投保人证件号码验证以及性别验证,不通过验证加边框
function IdentityBorderValidate(oid,id,erro,maleid,femalid)
{
    if(IdentityValidate(oid,id,erro,maleid,femalid)==false)
    {
        return false;
    }
}
//被保人证件类型及性别以及出生日期
function BIdntityBValidate(oid,id,erro,maleId,birthId,femalid)
{
    if(IdentityValidate(oid,id,erro,maleId,femalid)==false)
    {
        return false;
    }
    if($I(oid).value=="0")
    {
        $I(birthId).value=BirthGetFromCard($.trim($I(id).value));
        //$I(birthId).onchange();
    }
}
function BIdntityBValidate_ul(oid,id,erro,maleId,birthId,femalid)
{
    if(IdentityValidate(oid,id,erro,maleId,femalid)==false)
    {
        return false;
    }   
}
//被保人证件类型及性别以及出生日期(提交验证专用)
function BIdntityBValidateT(oid,id,erro,maleId,birthId,femalid)
{
    if(IdentityValidate(oid,id,erro,maleId,femalid)==false)
    {
        return false;
    }
    if($I(oid).value=="0")
    {
        $I(birthId).value=BirthGetFromCard(trim($I(id).value));
    }
}

//填写证件号码前验证证件类型oid-类型,id待验证
function IdentityHead(oid,oiderro,id,erro)
{   
    var oidValue=$I(oid).value;
    if(oidValue=="-1")
    {
        $I(oiderro).innerHTML="请先选择证件类型";
        DisableControl(id);
        return false;
    }
    else
    {
        $I(erro).innerHTML="";
    }
}
function selectNotDefault(id,erro,erroText)
{   
    var text=trim($I(id).value);//取出值并且出去空格
    if(text==-1)
    {
        $I(erro).innerHTML="请选择"+erroText;
        return false;
    }
}
function ErroClear(id)
{
    $I(id).innerHTML="";
}
//清理错误信息并且清理验证文本框边框
function ErroClearBorder(id,org)
{
    $I(id).innerHTML="";
    $I(org).className="";
}
function ErroOrgBorder(id,org,cName)
{
    $I(id).innerHTML="";
    $I(org).className=cName;
}
//不通过验证的控件加上边框
function ErroSetBorder(id)
{
    $I(id).className="erroborder";
}
//恢复控件有效性
function EnableControl(id)
{
    $I(id).disabled=false;
}
function DisableControl(id)
{
    $I(id).disabled=true;
}
//投保人
function NameValidate(id,erro,erroText)
{
    var text=trim($I(id).value);
    if(LengthValidate(text,erroText,erro,50)==false)
    {
        return false;
    }
}

///手机号码验证0-不可为空，1-可为空
function TelBValidate(id,erro,type)
{
    var text=trim($I(id).value);
    if(type==0)
    {
        if(text.length==0)
        {
            $I(erro).innerHTML="手机号码不能为空";
            return false;
        }
    }
    if(text.length>0)
    {
        if(TelValidate(text)==false)
        {
            $I(erro).innerHTML="手机号码格式不符";
            return false;
        }
    }
}

///家庭电话验证
function HomePhoneBValidate(id,erro,type)
{
    var text=trim($I(id).value);
    if(type==0)
    {
        if(text.length==0)
        {
            $I(erro).innerHTML="不能为空";
            return false;
        }
    }
    if(text.length>0)
    {
        if(PhoneValidate(text)==false)
        {   
            $I(erro).innerHTML="格式不对";
            return false;
        }
    }
}


//验证邮箱
function EmailBValidate(id,erro,type)
{
    var text=trim($I(id).value);
    if(type==0)
    {
        if(text.length==0)
        {
            $I(erro).innerHTML="电子邮箱地址不能为空";
            return false;
        }
    }
    if(text.length>30)
    {
        $I(erro).innerHTML="电子邮箱长度不得超过30位";
        return false;
    }
    if(text.length>0)
    {
        if(EmailValidate(text)==false)
        {
            $I(erro).innerHTML="电子邮箱格式不对";
            return false;
        }
    }
}
//验证下拉省市住址pid-省id,cid-市id,aid-住址id,erro-错误id
function AddressValidate(pid,cid,aid,erroId)
{
    if(SelectDefault(pid)==false)
    {
        $I(erroId).innerHTML="请选择省份";
        return false;
    }
    if(SelectDefault(cid)==false)
    {
        $I(erroId).innerHTML="请选择市区";
        return false;
    }
    var text=trim($I(aid).value);
    if(LengthValidate(text,'详细地址',erroId,100)==false)
    {   
        return false;
    }
}
//判断是否是日期格式
function isBirthDate(id,erroId)
{
    var date=trim($I(id).value);
    if(isDate(date)==false)
    {
        $I(erroId).innerHTML="日期格式错误";
        return false;
    }
}
//判断起保时间是否有效
 function  isSubFromDateTrue(txtId,hdnId,erroId)
{ 
   var txtDate=trim($I(txtId).value); var hdnDate=trim($I(hdnId).value); 
   if(isDate(txtDate)==false)
    {
        $I(erroId).innerHTML="起保日期格式错误";
        return false;
    }
   if(isDate(hdnDate)==false)
    {
        $I(erroId).innerHTML="隐藏域日期格式错误";
        return false;
    } 
   if(isDateGraaterThan(txtDate,hdnDate)==false)
   {
        $I(erroId).innerHTML="起保日期必须晚于当前日期";
        return false;
   } 
}

//登录名验证
function LoginNameVal(id,erroId)
{
    var text=trim($I(id).value);
    if(text.length==0)
    {
        $I(erroId).innerHTML="用户名不能为空";
        return false;
    }
    else if(text.length>30)
    {
        $I(erroId).innerHTML="用户名字符长度不能超过30位";
        return false;
    }
    else
    {
        if(EmailValidate(text)==false)
        {
            $I(erroId).innerHTML="用户名只能为邮箱";
            return false;
        }
    }
}
//登录密码验证
function PwdVal(id,erroId)
{
    var text=trim($I(id).value);
    if(text.length==0)
    {
        $I(erroId).innerHTML="密码不能为空";
        return false;
    }
    else
    {
        if(PwdValidate(text)==false)
        {
            $I(erroId).innerHTML="密码不符合格式";
            return false;
        }
    }
}
//密码核对
function RePwdVal(id,oid,erroId)
{
    var text=trim($I(id).value);
    var otext=trim($I(oid).value);
    if(text!=otext)
    {
        $I(erroId).innerHTML="两次密码不一样";
        return false;
    }
}
//英文名验证
function EnglishNameValidte(id,erroId)
{
    var text=trim($I(id).value);
    if(LengthValidate(text,'英文名',erroId,50)==false)
    {
        return false;
    }
    else
    {
        var reg=/^[A-Za-z \s]+$/;
        var objExp=new RegExp(reg);
        if(objExp.test(text)==false)
        {
            $I(erroId).innerHTML="格式不符";
            return false;
        }
    }
}
//验证备注
function RemarkValidate(id,erroId)
{
    var text=trim($I(id).value);
    if(MaxLengthValidate(text,'',erroId,200)==false)
    {
        return false;
    }
}
//签证办理城市验证
function VisaValidate(id,erroId,erroText)
{
    var text=trim($I(id).value);
    if(MaxLengthValidate(text,'',erroId,50)==false)
    {
        return false;
    }
}
 //隐藏显示密码确认框,以及登陆注册切换
function WrapLogin(id)
{
     //登陆
    if(id=='chkReg')
    {
        $I('pwdconfirm').style.display="none";
        $I('chkLogin').checked=true;
    }
    else
    {
        $I('pwdconfirm').style.display="";
        $I('chkReg').checked=true;
    }
    $I(id).checked=false;
}
//复制
function CopyInput(orgId,aimId)
{
    if($I(aimId)!=null&&$I(aimId)!='undefined')
    {
        $I(aimId).value=$I(orgId).value;
    }
}
//时间相加
function DateAddDay(startDate,day,startId,endId)
{
    var aimDay=startDate;
    var daySpan=day;
    
    var aimDayDate=new   Date(Date.parse(aimDay.replace(/-/g,   "/")));
    $I(startId).innerHTML=aimDayDate.getFullYear()+"年"+(parseInt(aimDayDate.getMonth())+1)+"月"+aimDayDate.getDate()+"日零时";
    var end=new Date(aimDayDate.getFullYear(),aimDayDate.getMonth(),aimDayDate.getDate()+daySpan-1);
    $I(endId).innerHTML=end.getFullYear()+"年"+(parseInt(end.getMonth()+1))+"月"+end.getDate()+"二十四时";
}

//时间相加
function DateAddMonth(startDate,month,startId,endId)
{
    var aimDay=startDate;
    var daySpan=month;
    
    var aimDayDate=new   Date(Date.parse(aimDay.replace(/-/g,   "/")));
    $I(startId).innerHTML=aimDayDate.getFullYear()+"年"+(parseInt(aimDayDate.getMonth())+1)+"月"+aimDayDate.getDate()+"日零时";
    var end=new Date(aimDayDate.getFullYear(),aimDayDate.getMonth()+daySpan,aimDayDate.getDate()-1);
    $I(endId).innerHTML=end.getFullYear()+"年"+(parseInt(end.getMonth()+1))+"月"+end.getDate()+"二十四时";
}

//checkbox类型
function MaleVal(maleid,femaleid,erro)
{
    if($I(maleid).checked==false&&$I(femaleid).checked==false)
    {
        $I(erro).innerHTML='请选择性别';
        return false;
    }
}

function Radio2Val(rad1,rad2,erro,erroText)
{
    if($I(rad1).checked==false&&$I(rad2).checked==false)
    {
        $I(erro).innerHTML=erroText;
        return false;
    }
}
//
function MarryVal(marryid,nomarryid,erro)
{
    if($I(marryid).checked==false&&$I(nomarryid).checked==false)
    {
        $I(erro).innerHTML='请选择婚否';
        return false;
    }
}
function IsNumVal(id,erro)
{
    var text=trim($I(id).value);
    if(text.length==0)
    {
        $I(erro).innerHTML='受益比例不能为空';
        return false;
    }
    else if(isInteger(text)==false)
    {
        $I(erro).innerHTML='受益比例只能为大于0且小于等于100之间的整数';
        return false;
    }
}

//根据日期计算年龄
function CalculateAge(_date)
{
   var age;
   var brith=parseDate(document.getElementById(_date).value);
   var aDate=new Date();
    var thisYear=aDate.getFullYear();
    var thisMonth=aDate.getMonth()+1;
    var thisDay=aDate.getDate();
    if(brith!=undefined)
    {
        brithy=brith.getFullYear();
        brithm=brith.getMonth()+1;
        brithd=brith.getDate();
        if(thisYear>brithy || thisYear==brithy)
        {
                age = thisYear - brithy - 1;
                if(thisMonth > brithm)      //现在的月比出生月大 
                {
                    age=age+1;
                }
                else if (thisMonth == brithm)//现在的月与出生月一样 
                {
                    if (thisDay >= brithd)//现在的日比出生日大
                    {
                       age=age+1;
                    }
                }               
        }
        else
        {
               alert("日期输入错误!");
               age=-1;                 
        }
        return age;      
    }
}

//根据和被保人关系，验证年龄
function Order_orderDetail_age(_date,_subDate,_relation)
{
  //$('sltRelation1').value
//   <option value="2">子女</option>                                       
//   <option value="3">父母</option> 
   
  var age=getAge(_date);   //投保人年龄  
  var subAge=getAge(_subDate);   //被保人年龄
  var relation=$I(_relation).value;
  if(relation=="2")
  {
    if(parseInt(subAge)>parseInt(age))
      {
        alert("被保人年龄不能大于投保人年龄！");
        return false;
      }
      if((parseInt(age)-parseInt(subAge)) <18 )
      {
        alert("被保人年龄和投保人年龄必须相差18岁！");
        return false;
      }
  }
  
  if(relation=="3")
  {
    if(parseInt(subAge)<parseInt(age))
      {
        alert("被保人年龄必须大于投保人年龄！");
        return false;
      }
      if((parseInt(subAge)-parseInt(age)) <18 )
      {
        alert("被保人年龄和投保人年龄必须相差18岁！");
        return false;
      }
  }  
}

function parseDate(str)
{
    if(str.match(/^\d{4}[\-\/\s+]\d{1,2}[\-\/\s+]\d{1,2}$/))
    {
        return new Date(str.replace(/[\-\/\s+]/i,'/'))
    }
    else if(str.match(/^\d{8}$/))
    {
        return new Date(str.substring(0,4)+'/'+str.substring(4,6)+'/'+str.substring(6))
        }else{
        alert('日期转换错误!')
    }
}

//根据生日判断年龄是否在投保范围内
function  AgeIsInScope(_date,_minDate,_maxDate,_flag){
//http://topic.csdn.net/u/20110613/12/f4169082-9691-4816-ab88-96fb8dc70d87.html
    var age;
    var totalDay;
    var aDate=new Date();
    var thisYear=aDate.getFullYear();
    var thisMonth=aDate.getMonth()+1;
    var thisDay=aDate.getDate();
    var birth=$I(_date).value;
//    var birthYear=birth.getFullYear();
//    var birthMonth=birth.getMonth()+1;
//    var birthDay=birth.getDate();
    age=getAge(birth);         
     
        if(_flag=="1")
        {
           totalDay=DateDiff(birth,thisYear+"-"+thisMonth+"-"+thisDay);
           if(totalDay<parseInt(_minDate) || age>parseInt(_maxDate))
           {
              alert("年龄不在投保范围内！");
              return false;
           }
           else
             return true;
        }
        else
        {
           if(age<18)
           {
              alert("投保年龄要大于18岁！");
              return false;
           }
           if(age<parseInt(_minDate) || age>parseInt(_maxDate))
           {
              alert("年龄不在投保范围内！");
              return false;
           }
           else
             return true;
        }       
}


//计算二个日期相差的天数
   
function  DateDiff(beginDate,  endDate){    //beginDate和endDate都是2007-8-10格式
     var   rbegin= beginDate.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
              if(rbegin==null)return ;   
      var   Date1=new   Date(rbegin[1],rbegin[3]-1,rbegin[4]); 
       var   rend= endDate.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
              if(rend==null)return ;   
      var   Date2=new   Date(rend[1],rend[3]-1,rend[4]); 
            var iDays;
            iDays = parseInt(Math.abs(Date1-Date2)/1000/60/60/24)                         //转换为天数
            return iDays;     
   }
   //替换回车换行符
   function ReplaceRN(str)
   {
        str=str.replace(/\r\n/ig,'');
        str=str.replace(/\r/ig,'');
        str=str.replace(/\n/ig,'');
        return str;      
   }

 //处理“？”提示
   $(function(){
   //首先定义一个list对象
   var list=[{'name':'投保人信息','description':'投保人是指与保险公司订立合同、负有支付保险费义务的人，即办理保险并支付保险费的人。在填写时，请确保按照有效证件填写。'},
   {'name':'被保人信息','description':'被保险人是指其财产或人身受保险合同保障，享有保险金请求权的人。投保人可以与被保险人为同一个人。'},
   {'name':'受益人信息','description':'受益人是指人身保险合同中由被保险人或投保人指定的享有保险金请求权的人。因网上投保受益人的指定无法得到被保险人的书面确认，所以一般默认法定继承人作为身故受益人。法定继承人是指法律直接规定的可以依法继承被继承人遗产的公民。'},
   {'name':'起保时间','description':'您可以指定起保日期，起保日期不得早于缴费日期。最终起保日期以保险公司承保时间为准。'},
   {'name':'保障期间','description':'您可以指定起保日期，起保日期不得早于缴费日期。最终起保日期以保险公司承保时间为准。'}];
    $.each(list,function(ix){
    var aim=$('.tbrxx_tit:contains("'+list[ix].name+'")');
    var wh=aim.next('.tbrxx_wh');
    if(wh.length==0)
    {
        aim.after("<div class=\"tbrxx_wh\"><img src=\"../images_tb/wenhao.gif\" /></div>");
    }
        aim.next().find("img[src$='wenhao.gif']").bind('mouseover',function(){$(this).attr('title',list[ix].description);
        $(this).css("cursor","pointer");});
    });
   })