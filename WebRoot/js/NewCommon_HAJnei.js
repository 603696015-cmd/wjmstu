//返回对象
function $I(id)
{
    return document.getElementById(id);
}
//过滤空格
function trim(text)
{
    return text.replace(/^\s+|\s+$/g,""); 
}
//下拉框是否是默认选项
function SelectDefault(id)
{
    if($I(id).value=="-1")
    {
        return false;
    }
}
//中国电话号码验证
function TelValidate(text)
{
    var reg=/^1\d{10}$/;
    var regExp=new RegExp(reg);
    if(regExp.test(text)==false)
    {
        return false;
    }
}
//固定电话验证
function HomePhoneValidate(text)
{
    var reg=/^(([0\+]\d{2,3}-)?(0\d{2,3})-?)?(\d{7,8})(-(\d{3,}))?$/;
    var regExp=new RegExp(reg);
    if(regExp.test(text)==false)
    {
        return false;
    }
}
function PhoneValidate(text)
{
    if(TelValidate(text)==false&&HomePhoneValidate(text)==false)
    {
        return false;
    }
}
//验证邮箱的方法
function EmailValidate(text)
{ 
    var r=/\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/ ;
    if( r.test(text)==false)
    {
       return false;
    }
}
//密码验证6-14位数字、英文字母或者下划线组成
function PwdValidate(text)
{
    var reg=/\w{6,14}/;
    if(reg.test(text)==false)
    {
        return false;
    }
}
//判断是否是日期
function isDate(dateString)
{
  var r=dateString.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
  if(r==null)
  {
   return false;
  }
  var d=new Date(r[1],r[3]-1,r[4]);   
  var num = (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]);
  var time=d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4];
  if(time!=true)
  {
    return false;
  }
}

//判断日期 dateFirstStr 是否大于等于 日期 dateTowStr
function isDateGraaterThan(dateFirstStr,dateTowStr)
{
    var isTrue=false;
   var r1=dateFirstStr.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
   var r2=dateTowStr.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
  if(r1!=null || r2!=null)
    {  var d1=new Date(r1[1],r1[3]-1,r1[4]);   
       var d2 =new Date(r2[1],r2[3]-1,r2[4]);  
       if(d1>=d2)
       {
        isTrue=true;
       }
    }
    return isTrue; 
}


function   checkDate(obj)
{ 

var   strDate=$I(obj).value; 
 var r=strDate.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
  if(r==null)
  {
   return false;
  }
  else
  {
  return true;
  }
//re=/^(\d{4})(\d{2})(\d{2})$/g 
//if(re.test(strDate))//判断日期格式符合YYYY-MM-DD 
//  { 
//    return true;
//   } 
// else
// {
//  return false;
// }
} 

function getMonthDiff(birth1,birth2)
{
    var   r1=   birth1.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);     
    if(r1==null)return ;   
    var   d1=   new   Date(r1[1],   r1[3]-1,   r1[4]); 
    var   r2=   birth2.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);     
    if(r2==null)return ;   
    var   d2=   new   Date(r2[1],   r2[3]-1,   r2[4]);
    if(d2.getDate()>d1.getDate())
    {
        return (d2.getFullYear()-d1.getFullYear())*12+d2.getMonth()-d1.getMonth();
    }
    else
    {
        return (d2.getFullYear()-d1.getFullYear())*12+d2.getMonth()-d1.getMonth()-1;
    }
}
///计算年龄,birth时间格式
function   getAge(birth) 
{   
    var   r=   birth.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);     
    if(r==null)return ;   
    var   d=   new   Date(r[1],   r[3]-1,   r[4]); 
    var date=new Date();  
    var newday=date.getDate();
    var newmonth=date.getMonth();
    var newyear=date.getFullYear();

    if((newyear<r[1])==true)
    {
        alert('出生日期不能大于当前日期');
        return;
    }
    if((newyear==r[1]&&newmonth<(r[3]-1))==true)
    {
        alert('出生日期不能大于当前日期');
        return;
    }
    if((newyear==r[1]&&(newmonth==(r[3]-1))&&newday<r[4])==true)
    {
         alert('出生日期不能大于当前日期');
         return;
    }

          if(date.getMonth()+1>r[3])
          {
            return (newyear-r[1]); 
          }
          else if(date.getMonth()+1==r[3])
          {
            if(newday>=r[4])
            {
                return (newyear-r[1]); 
            }
            else
            {
                return (newyear-r[1]-1);
            }
          }
          else
          {
            return (newyear-r[1]-1)
          }   
}
  var city=[
 ["北京","上海","天津","重庆"],
  ["广州","清远","潮州","东莞","珠海","深圳","汕头","韶关","佛山","江门","湛江","茂名","肇庆","惠州","梅州","汕尾","阳江","河源","中山","揭阳","云浮"],
   ["南宁","柳州","桂林","梧州","北海","防城港","钦州","贵港","玉林","百色","贺州","河池","来宾","崇左"],
   ["福州","宁德","南平","厦门","莆田","三明","泉州","漳州"],
   ["长沙","株洲","湘潭","岳阳","邵阳","常德","衡阳","张家界","益阳","郴州","永州","怀化","娄底","湘西"],
   ["武汉","十堰","襄樊","鄂州","黄石","荆州","宜昌","荆门","孝感","黄冈","咸宁","随州","恩施"],
   ["南京","苏州","扬州","无锡","徐州","常州","南通","连云港","淮安","盐城","镇江","泰州","宿迁"],
    ["杭州","宁波","温州","嘉兴","湖州","绍兴","金华","衢州","舟山","台州","丽水"],
    ["成都","自贡","攀枝花","泸州","德阳","绵阳","广元","遂宁","内江","乐山","南充","宜宾","广安","达州","眉山","雅安","巴中","资阳","阿坝","甘孜","凉山"],
    ["沈阳","大连","鞍山","抚顺","本溪","丹东","锦州","营口","阜新","辽阳","盘锦","铁岭","朝阳","葫芦岛"],
    ["郑州","开封","洛阳","平顶山","焦作","鹤壁","新乡","安阳","濮阳","漯河","许昌","三门峡","南阳","商丘","信阳","周口","驻马店"],
    ["济南","青岛","淄博","枣庄","东营","烟台","潍坊","威海","济宁","泰安","日照","莱芜","临沂","德州","聊城","滨州","菏泽"],
    ["合肥","芜湖","蚌埠","淮南","马鞍山","淮北","铜陵","安庆","黄山","滁州","阜阳","宿州","巢湖","六安","亳州","池州","宣城"],
    ["西安","铜川","宝鸡","咸阳","渭南","延安","汉中","榆林","安康","商洛"],
    ["昆明","曲靖","玉溪","保山","昭通","丽江","普洱","临沧","文山","红河","西双版纳","楚雄","大理","德宏","怒江","迪庆"],
    ["石家庄","唐山","秦皇岛","邯郸","邢台","保定","张家口","承德","沧州","廊坊","衡水"],
    ["呼和浩特","包头","乌海","赤峰","通辽","鄂尔多斯","呼伦贝尔","巴彦淖尔","乌兰察布","兴安","锡林郭勒","阿拉善"],
    ["南昌","上饶","萍乡","九江","景德镇","新余","鹰潭","赣州","吉安","宜春","抚州"],
    ["太原","大同","阳泉","长治","晋城","朔州","晋中","运城","忻州","临汾","吕梁"],
    ["哈尔滨","齐齐哈尔","鸡西","鹤岗","双鸭山","大庆","伊春","佳木斯","七台河","牡丹江","黑河","绥化","大兴安岭"],
  ["贵阳","六盘水","遵义","安顺","铜仁","毕节","黔西南","黔东南","黔南"],
  ["长春","吉林","四平","辽源","通化","白山","松原","白城","延边"],
   ["海口","三亚"]
  ];
  
  function getCity(scity,pId,cId)
  {
      //获得省份下拉框的对象
      var sltProvince=$I(pId);
      //获得城市下拉框的对象
      var sltCity=$I(cId);
      //得到对应省份的城市数组
      var provinceCity=city[sltProvince.selectedIndex - 1];
      //清空城市下拉框，仅留提示选项
      sltCity.length=1;
      //如果是绑定服务器控件的值
      if(scity!="noload"&&scity!="")
      {
         sltCity[0].selected=false;
         //将城市数组中的值填充到城市下拉框中，并且处理选中项
         if(provinceCity!=null)
         {
              for(var i=0;i<provinceCity.length;i++)
              {
                sltCity[i+1]=new Option(provinceCity[i],provinceCity[i]);
                 if(provinceCity[i]==scity)
                 {
                   sltCity[i+1].selected=true;
                 }
              }  
         }
      }
      else
      {
         if(provinceCity!=null)
         {
            for(var i=0;i<provinceCity.length;i++)
             {
                sltCity[i+1]=new Option(provinceCity[i],provinceCity[i]);
             }
         }         
      }
}
//判断是否为小数
function isDecimal(text)
{  
   if(isNaN(text)==true)
   {
     return false;
   }
   else if(text<=0||text>1)
   {
     return false;
   }
}

//检查输入对象的值是否符合整数格式，且在0-100之间
function isInteger( str )
{ 
    var regu = /^[-]{0,1}[0-9]{1,}$/;
    if(regu.test(str))
    {
        if(str<=0 || str>100)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    else
    {
        return false;
    }
}

function IdenName(type)
{
    var IName="";
    switch(type)
    {
        case "0":
        IName="身份证";
        break;
        case "1":
        IName="护照";
        break;
        case "2":
        IName="出生证";
        break;
        case "3":
        IName="军官证";
        break;
        case "4":
        IName="异常身份证";
        break;
        case "5":
        IName="回乡证";
        break;
        case "6":
        IName="军人证";
        break;
        case "7":
        IName="出生日期";
        break;
        case "8":
        IName="港台同胞证";
        break;
        case "9":
        IName="台胞证";
        break;
        case "10":
        IName="临时身份证";
        break;
        case "11":
        IName="驾驶证";
        break;
         case "12":
        IName="户口本";
        break;
        case "16":
        IName="士兵证";
        break;
         case "17":
        IName="港澳返乡证";
        break;
         case "20":
        IName="其他";
        break;
        default:
        IName="其他";
        break;
    }    
    return IName;
}

function IdenName_tp(type)
{
    var IName="";
    switch(type)
    {
        case "1":
        IName="身份证";
        break
        case "2":
        IName="军人证";
        break
        case "3":
        IName="护照";
        break
        case "4":
        IName="出生证";
        break ;
        case "9":
        IName="其他";
        break        
        default:
        IName="其他";
        break
    }    
    return IName;
}
