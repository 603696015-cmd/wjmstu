
//地区验证
function AddressValidate_bbrLf(i)
{
    if($I('sltBbrProvince'+i).value=="-1")
    {
        $I('bbrSubAddress'+i+'E').innerHTML="请选择省份";
        return false;
    }
    if($I('sltBbrCity'+i).value=="-1")
    {
        $I('bbrSubAddress'+i+'E').innerHTML="请选择城市";
        return false;
    }
    var text=trim($I('txtBbrSubAddress'+i).value);
    if(text.length==0)
    {
        $I('bbrSubAddress'+i+'E').innerHTML="请填写详细地址";
        return false;
    }
    if(text.length>100)
    {
        $I('bbrSubAddress'+i+'E').innerHTML="长度不能超过100个字符";
        return false;
    }
}  
var cityHATF=[
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
    ["南昌","上饶","萍乡","九江","景德镇","新余","鹰潭","赣州","吉安","宜春","抚州"],
    ["哈尔滨","齐齐哈尔","鸡西","鹤岗","双鸭山","大庆","伊春","佳木斯","七台河","牡丹江","黑河","绥化","大兴安岭"],
  ["贵阳","六盘水","遵义","安顺","铜仁","毕节","黔西南","黔东南","黔南"],
  ["长春","吉林","四平","辽源","通化","白山","松原","白城","延边"]
  ];
  function getCityHATF(scity,pId,cId)
  {
      //获得省份下拉框的对象
      var sltProvince=$I(pId);
      //获得城市下拉框的对象
      var sltCity=$I(cId);
      //得到对应省份的城市数组
      var provinceCity=cityHATF[sltProvince.selectedIndex - 1];
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
function    setOtherBbr(id)
{
  $I(id).value=$I(id).value.replace(/,/g,"，");
}
//地区验证
function AddressValidate_bbrMe(i)
{
    if($I('sltMeBbrProvince'+i).value=="-1")
    {
        $I('bbrMeSubAddress'+i+'E').innerHTML="请选择省份";
        return false;
    }
    if($I('sltMeBbrCity'+i).value=="-1")
    {
        $I('bbrMeSubAddress'+i+'E').innerHTML="请选择城市";
        return false;
    }
    var text=trim($I('txtMeBbrSubAddress'+i).value);
    if((text.length==0)||(text=='地址需明确至门牌号，如：中山北路2000弄XX号XX室'))
    {
        $I('bbrMeSubAddress'+i+'E').innerHTML="请填写详细地址";
        return false;
    }
    if(text.length>100)
    {
        $I('bbrMeSubAddress'+i+'E').innerHTML="长度不能超过100个字符";
        return false;
    }
}  



 function SetVal(id,type)
        {
           if(type=='Focus')
           {
                 if($I(id).value=='地址需明确至门牌号，如：中山北路2000弄XX号XX室')
                 {
                        $I(id).value='';
                 }
             }
             else if(type=='Blur')
             {
                 if($I(id).value=='')
                 {     
                        $I(id).value='地址需明确至门牌号，如：中山北路2000弄XX号XX室'; 
                        
                 }
             }
        }
        

//-----end

//手机验证  不能为空

function TelePhoneBValidate(id,erro,type)
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
        if(TelValidate(text)==false)
        {   
            $I(erro).innerHTML="格式不对";
            return false;
        }
    }
}

//holderj.houseType,holderj.houseTypeVal,holderj.bbrProvince,holderj.bbrCity,holderj.bbrAddr,holderj.bbrFullAddr,holderj.bbrPostcode
//定义被保险人参数类
function Holder(relation,name,htype,hNo,hbir,price,male,phone,marry,job1,job2,job3,relationval,email,houseType,houseTypeVal,bbrProvince,bbrCity,bbrAddr,bbrFullAddr,bbrPostcode)
{              
   var holder=new Object();
   holder.relation=relation;
   holder.name=name;
   holder.type=htype;
   holder.number=trim(hNo);
   holder.birth=hbir;
   holder.price=price;
   holder.male=male;
   holder.phone=phone;
   holder.marry=marry;
   holder.job1=job1;
   holder.job2=job2;
   holder.job3=job3;
   holder.relationval=relationval;
   holder.email=email;
   holder.houseType=houseType;
   holder.houseTypeVal=houseTypeVal;
   holder.bbrProvince =bbrProvince;
   holder.bbrCity =bbrCity;
   holder.bbrAddr=bbrAddr;
   holder.bbrFullAddr=bbrFullAddr;
   holder.bbrPostcode=bbrPostcode;
   return holder;
}
//定义受益人参数类
function Benefet(relation,name,htype,hNo,hbir,percent,male)
{              
   var benefet=new Object();
   benefet.relation=relation;
   benefet.name=name;
   benefet.type=htype;
   benefet.number=trim(hNo);
   benefet.birth=hbir;
   benefet.percent=percent;
   benefet.male=male;
   return benefet;
}

$(document).ready(function(){
    //投保人验证
    $('#txtSName').focus(function(){
        ErroClear('sNameE');
    });
    $('#txtSName').blur(function(){
        NameValidate('txtSName','sNameE','投保人姓名');
    });
    $('#sltSType').focus(function(){
        ErroClear('idenE');
        EnableControl('txtSNo')
    })
    $('#sltSType').blur(function(){
        selectNotDefault('sltSType','idenE','证件类型');
    });
    $('#txtSNo').focus(function(){
        IdentityHead('sltSType','idenE','txtSNo','idenN');
    })
    $('#txtSNo').blur(function(){
        BIdntityBValidate('sltSType','txtSNo','idenN','rdoMale','txtBirth','rdoFemale');
    });
    $('#txtSPhone').focus(function(){
        ErroClear('telE');
    })
    $('#txtSPhone').blur(function(){
        TelBValidate('txtSPhone','telE',0)
    });
    $('#txtSEmail').focus(function(){
        ErroClear('emailE')
    })
    $('#txtSEmail').blur(function(){
        EmailBValidate('txtSEmail','emailE',0);
        //CopyInput('txtSEmail','txtLoginName')
    });  
    $('#sltProvince').focus(function(){
        ErroClear('addrE');
    })
    $('#sltProvince').blur(function(){
        selectNotDefault('sltProvince','addrE','省份')
    });
    $('#sltCity').focus(function(){
        ErroClear('addrE');
    })
    $('#sltCity').blur(function(){
        selectNotDefault('sltCity','addrE','市区')
    });
    $('#txtAddress').focus(function(){
        ErroClearBorder('addrE','txtAddress')
    })
    $('#txtAddress').blur(function(){
        AddressValidate('sltProvince','sltCity','txtAddress','addrE');
    });
    $('#rdoMale').click(function(){
        ErroClear('maleE');
     });
    $('#rdoFemale').click(function(){
        ErroClear('maleE');
     });
     
    
    //被保人验证
    $('#rdoMe').click(function(){
        $('#wTable').show();
        $('#otherTable').hide();
        $('#meTable').show();
        $('#moreHolder').hide();
        ClickMe();
        
    });
    $('#rdoOther').click(function(){
        $('#wTable').show();
        $('#otherTable').show();
        $('#moreHolder').show();
        $('#meTable').hide();
        ClickOther();
    });
   
})
//投保人整体验证
function SubVal()
{
    if(NameValidate('txtSName','sNameE','投保人姓名')==false||selectNotDefault('sltSType','idenE','证件类型')==false||BIdntityBValidate('sltSType','txtSNo','idenN','rdoMale','txtBirth','rdoFemale')==false||SubAgeVal('txtBirth','birE')==false||MaleVal('rdoMale','rdoFemale','maleE')==false||TelBValidate('txtSPhone','telE',0)==false||EmailBValidate('txtSEmail','emailE',0)==false||selectNotDefault('sltProvince','addrE','请选择省份')==false||selectNotDefault('sltCity','addrE','请选择市区')==false|| AddressValidate('sltProvince','sltCity','txtAddress','addrE')==false)
    {
        return false;
    }
}
///单击本人后处理事项
function ClickMe()
{   
 
   sltFAreaChange();
}
//单击其他被保险人执行
function ClickOther()
{
   $('#number').text(trim($('#otherNum').val()));
   $('#sumMoney').text($('#otherMoney').val());
   sltFAreaChange();
}
///投保人年龄限制
function SubAgeVal(id,erro)
{
   if(isBirthDate(id,erro)==false)
   {
     return false;
   }
   else
   {
     var birth=trim($('#'+id).val());
     var age=getAge(birth);
     if(age<=18)
     {  
        $('#'+erro).html('投保人必须在18岁以上');
        return false
     }
     else
     {
        $('#'+erro).html('');
     }
   }
}
//被保人年龄限制
function HoldAgeVal(id,erro)
{
   if(isBirthDate(id,erro)==false)
   {
     return false;
   }
   else
   {
     var birth=trim($('#'+id).val());
     var age=getAge(birth);
     if(age>150||age<0)
     {  
        $('#'+erro).html('请如实填写被保人年龄');
        return false
     }
     else
     {
        $('#'+erro).html('');
     }
   } 
}
//一被保人只能拥有三份
function OnlyThree(i)
{   
    var num=0;
    var holderName=trim($('#txtHName'+i).val());
    var maxNum=parseInt($('#otherValNum').val());
    for(var j=1;j<=maxNum;j++)
    {   
        if($('#txtHName'+j).length>0)
        {  
            if(holderName==trim($('#txtHName'+j).val()))
            { 
               num+=1;
            }
        }
    }
    if(num>1)
    {
         $('#HName'+i+'E').text('每个被保险人最多只能购买一份');
         return false;
    }
}
///更多被保险人
function MoreHolder()
{   
   var currentNo=parseInt($('#otherValNum').val());
   var flag=0;
   for(var j=currentNo;j>0;j--)
   {
     if($('#sltRel'+j).length>0)
     {
        if(flag==0)
        {
            if(ValidateH(j)==false)
            {
              return false;
            }
            flag=1;
         }
         if($('#look'+j).text()=='收起')
         {
           SpreadHolder(j);
         }
     }
   }
   var nextNo=currentNo+1;
   CreateHolderWrap(nextNo);
    var k=1;
    for(var i=1;i<=nextNo;i++)
    {
        if($('#hno'+i).length>0)
        {
            $('#hno'+i).text(k);
            k+=1;
        }
    }
   $('#htitle'+currentNo).text($('#txtHName'+currentNo).val());
   $('#otherValNum').val(nextNo);
   var otherNo=parseInt($('#otherNum').val());
   $('#otherNum').val(otherNo+1);
  $('#number').text(trim(otherNo+1));
   var sumMoney=Math.round((parseFloat($('#sumMoney').text())+parseFloat($('#sigleMoney').val()))*100)/100;
   $('#sumMoney').text(sumMoney);
   $('#otherMoney').val(sumMoney);
   sltFAreaChange();
}
//被保人关系选择本人复制投保人信息
function CopySub(i)
{   
    if($('#sltRel'+i).val()=="1")
    {
        $('#txtHName'+i).val($('#txtSName').val());
        CopyName(i);
        $('#sltHType'+i).val($('#sltSType').val());
        $('#txtHNo'+i).val($('#txtSNo').val());
        $('#txtHB'+i).val($('#txtBirth').val());
         $('#txtHEmail'+i).val($('#txtSEmail').val());
        
        if($('#rdoMale').attr("checked")==true)
        {
            $('#rdoHMale'+i).attr("checked",true);
        }
        else if($('#rdoFemale').attr("checked")==true)
        {
            $('#rdoHFemale'+i).attr("checked",true);
        }
        $('#txtHPhone'+i).val($('#txtSPhone').val());
        HoldAgeVal('txtHB'+i,"HB"+i+"E");
        OnlyThree(i);
    }
}
function DelHolder(i)
{
    
     var maxId=parseInt($('#otherValNum').val());
        var flag=0;
        for(var n=1;n<=maxId;n++)
        {
            if($('#hno'+n).length>0)
            {
                flag+=1;
            }
            if(flag>=2)
            {
                break;
            }
        }
        if(flag<=1)
        {
            alert('最后一个被保险人不能删除');
            return false;
        }
    if(confirm('确定删除此被保险人信息？')==false)
    {
        return false;
    }
    else
    {   
        var pricei=parseInt($('#price'+i).text());
        var cum=parseInt($('#hno'+i).text());
        $('#holder'+i).remove();
        $('#holderN'+i).remove();
        
        var k=1;
        for(var j=0;j<=maxId;j++)
        {   
            if($('#hno'+j).length>0)
            {
             $('#hno'+j).text(k);
             k+=1;
            }
        }
        //判断是否是最后一个
        var isLast=1;
        for(var m=i;m<=maxId;m++)
        {
            if($('#look'+m).length>0)
            {
                isLast=0;
                break;
            }
        }
        if(isLast==1)
        {
            for(var  j=i;j>0;j--)
            {
                if($('#look'+j).length>0&&$('#look'+j).text()=='查看')
                {
                    SpreadHolder(j);
                    break;
                }
            }
        }
        
        //价格减法
        var sumMoney=Math.round((parseFloat($('#sumMoney').text())-parseFloat($('#sigleMoney').val()))*100)/100;
        $('#otherMoney').val(sumMoney);
        $('#sumMoney').text(sumMoney);
        //被保险人个数减法
        var currentNum=parseInt($('#otherNum').val())-1;
        $('#otherNum').val(trim(currentNum));
       $('#number').text(trim(currentNum));
        sltFAreaChange();
    }
}
//展开收起
function SpreadHolder(i)
{
    
    $('#holderN'+i).toggle();
    if($('#look'+i).text()=='查看')
    {
        $('#imgh'+i).attr('src','../images_tb/-.gif');
        $('#look'+i).text("收起");
    }
    else
    {
        $('#imgh'+i).attr('src','../images_tb/+.gif');
        $('#look'+i).text("查看");
    }
}
function ValidateH(i)
{
    if(selectNotDefault('sltRel'+i,'Rel'+i+'E','您与被保人关系')==false||NameValidate('txtHName'+i,'HName'+i+'E','被保人姓名')==false||selectNotDefault('sltHType'+i,'HNo'+i+'E','证件类型')==false||BIdntityBValidate('sltHType'+i,'txtHNo'+i,'HNo'+i+'E','rdoHMale'+i,'txtHB'+i,'rdoHFemale'+i)==false||HoldAgeVal('txtHB'+i,'HB'+i+'E')==false||OnlyThree(i)==false||MaleVal('rdoHMale'+i,'rdoHFemale'+i,'Male'+i+'E')==false||TelePhoneBValidate('txtHPhone'+i,'HPone'+i+'E',0)==false||MarryVal('rdohMarry'+i,'rdohNoMarry'+i,'marry'+i+'E')==false||EmailBValidate('txtHEmail'+i,'HEmail'+i+'E',0)==false||selectNotDefault('sltHouseType'+i,'HouseType'+i+'E','房屋类型')==false||selectNotDefault('sltBbrProvince'+i,'bbrSubAddress'+i+'E','财产所在省')==false||selectNotDefault('sltBbrCity'+i,'bbrSubAddress'+i+'E','财产所在市')==false||LengthValidate('txtBbrSubAddress'+i,'bbrSubAddress'+i+'E','财产详细地')==false||postCodeValidate('txtBBRPostcode'+i,'BBRPostcode'+i+'E','财产所在地邮编')==false)
     {
        if($('#look'+i).text()=='查看')
        {
            SpreadHolder(i);
        }
        return false;
    }
}

function ValidateI(i)
{
    if(NameValidate('txtImName'+i,'ImName'+i+'E','被保人姓名')==false||selectNotDefault('sltImItype'+i,'ImType'+i+'E','证件类型')==false||BIdntityBValidate('sltImItype'+i,'txtImNo'+i,'ImType'+i+'E','rdoIMale'+i,'txtImBir'+i,'rdoIFemale'+i)==false||isBirthDate('txtImBir'+i,'ImB'+i+'E')==false||MaleVal('rdoIMale'+i,'rdoIFemale'+i,'IMale'+i+'E')==false)
    return false;
}
function DelIholder(i)
{   
    var num=$('#number'+i).text();
    var price=$('#gprice'+i).text();
    $('#import'+i).remove();
    $('#ETip'+i).remove();
    var tab=$I('importTable');
    var m=1;
    for(var j=1;j<=tab.rows.length-1;j=j+2)
    {
        tab.rows[j].cells[0].innerHTML = "<b>"+m.toString()+"</b>";
        m=m+1;
    }
    if(price!='0')
    {
        var num=parseInt($('#groupNum').val())-1;
        $('#groupNum').val(num);
        $('#number').text(num);
    }
    var money=parseFloat($('#groupMoney').val())-parseFloat(price);
    $('#groupMoney').val(money);
    $('#sumMoney').text(money);
    sltFAreaChange();
}
//
function ShouYVal(i)
{
    if(selectNotDefault('sltYRel'+i,'YRel'+i+'E','您与被保人关系')==false||NameValidate('txtYName'+i,'YName"+i+"E','被保人姓名')==false||selectNotDefault('sltYType'+i,'YType'+i+'E','证件类型')==false||BIdntityBValidate('sltYType'+i,'txtYNo'+i,'YNo'+i+'E','RdoMaleY'+i,'txtYBir'+i,'RdoFemaleY'+i)==false||isBirthDate('txtYBir'+i,'YBir'+i+'E')==false||MaleVal('RdoMaleY'+i,'RdoFemaleY'+i,'MaleY'+i+'E')==false ||IsNumVal('txtYPer'+i,'YPer'+i+'E')==false)
    return false;
}
//添加受益人
function MoreShouY()
{
   var currentNo=$('#maxShouY').val();
   if(ShouYVal(currentNo)==false)
   {
     return false;
   }
   if(currentNo=='1')
   {
    $('#shouybg').addClass('tjbbxr_k');
   }
   var nextNo=parseInt(currentNo)+1;
   var ymale=1;
   if($('#RdoMaleY'+currentNo).attr("checked")==true)
   {
     ymale=0;
   }
   var bene=Benefet($('#sltYRel'+currentNo).find('option[selected=true]').text(),$('#txtYName'+currentNo).val(),$('#sltYType'+currentNo).val(),$('#txtYNo'+currentNo).val(),$('#txtYBir'+currentNo).val(),$('#txtYPer'+currentNo).val(),ymale);
   $('#shouY'+currentNo).remove();
   $('#maxShouY').val(nextNo);
   CreateBenefitHtml(currentNo);//创建
   BindBenefit(currentNo,bene);//绑定数据
    var k=currentNo-1;
   if($('#yno'+k).length>0)
   { 
     var newnum=parseInt($('#yno'+k).text())+1;
     $('#yno'+currentNo).text(newnum);
   }
   else
   {
      $('#yno'+currentNo).text("1");
   }
   CreateBenefitListHtml(nextNo)
   $('#ytitle'+currentNo).html(bene.name);
}


//受益人展开收起
function SpreadY(i)
{
    
    $('#shouyiN'+i).toggle();
    if($('#looky'+i).text()=='查看')
    {
        $('#imgy'+i).attr('src','../images_tb/-.gif');
        $('#looky'+i).text("收起");
    }
    else
    {
        $('#imgy'+i).attr('src','../images_tb/+.gif');
        $('#looky'+i).text("查看");
    }
}
//受益人删除
function DelShouY(i)
{
    if(confirm('确定删除此被保险人信息？')==false)
    {
        return false;
    }
    else
    {
        var cum=parseInt($('#yno'+i).text());
        $('#ShouYL'+i).remove();
        $('#shouyiN'+i).remove();
        var maxId=$('#maxShouY').val();
        if(maxId==2)
        {
            $('#shouybg').removeClass('tjbbxr_k');
        }
        for(var j=i+1;j<=maxId;j++)
        {   
            if($('#yno'+j).length>0)
            {
             $('#yno'+j).text(cum);
             cum=cum+1;
            }
        }
    }
}

//法定受益人
function ShowDefi(type)
{
    if(type=="0")
    {
     $('#shouyidiv').hide();
    }
    else
    {
        $('#shouyidiv').show();
    }
}
function sltFAreaChange()
{
    if($('#rdoYF').attr("checked")==true)
    {
        if($('#sltPro').val()=='-1')
        {
            $('#sltPro').val($('#sltProvince').val());
        }
        if($('#sltCityF').val()=='-1')
        {   
            getCity($('#sltPro').val(),'sltPro','sltCityF')
            $('#sltCityF').val($('#sltCity').val());
        }
        if(trim($('#txtFAddress').val())=='')
        {
            $('#txtFAddress').val($('#txtAddress').val());
        }
        if(parseInt($('#sumMoney').text())<=500)
        {
         if($('#sltCityF').val()=='深圳')
         {
            $('#fMoney').text('12');
         }
         else if($('#sltCityF').val()=='-1')
         {
            $('#fMoney').text('0');
         }
         else
         {
            $('#fMoney').text('20');
         }
        }
        else
        {
            $('#fMoney').text('0');
        }
        $('#holderNum').text(trim($('#number').text()));
        $('#sumMoney2').text($('#sumMoney').text());
        $('#Fp').text($('#fMoney').text());
        $('#allMoney').text(Math.round((parseFloat($('#Fp').text())+parseFloat($('#sumMoney').text()))*100)/100);
   }
   else
   {
      var fp=$('#Fp').text();
      $('#allMoney').text($('#sumMoney').text());
      $('#sumMoney2').text($('#sumMoney').text());
      $('#holderNum').text(trim($('#number').text()));
      $('#Fp').text('0');
   }
}
//被保险人验证
function ValHolderAll()
{
    if($('#rdoOther').attr("checked")==true)
    {   
        var num=parseInt($('#otherValNum').val());
        for(var i=1;i<=num;i++)
        {
            if($('#sltRel'+i).length>0)
            {
                if(ValidateH(i)==false)
                {
                    return false;
                }
            }
        }
    }
    else if($('#rdoMe').attr("checked")==true)
    {
        if(AddressValidate_bbrMe(1)==false|| MarryVal('rdoMarry','rdoNoMarry','marryE')==false||selectNotDefault('sltMeHouseType1','HouseMeTypeE','房屋类型')==false||selectNotDefault('sltMeBbrProvince1','bbrMeSubAddress1E','财产所在省')==false||selectNotDefault('sltMeBbrCity1','bbrMeSubAddress1E','财产所在市')==false||LengthValidate('txtMeBbrSubAddress1','bbrMeSubAddress1E','财产详细地')==false||postCodeValidate('txtMeBBRPostcode1','BBRMePostcode1E','财产所在地邮编')==false)
        {
            return false ;
        }
    }
    
    //验证起保时间格式
   if (isBirthDate('txtInsuredStartDate','StarE')==false)
   {
     $('#StarE').text('保险起期格式不对');
      return false;
   }
    
}
///受益人信息验证
function ValBenefit()
{
    if($('#rdoMe').attr("checked")==true)
    {
        if($('#rdodef').attr("checked")==true)
        {
            var num=parseInt($('#maxShouY').val());
            var per=0;
            for(var i=1;i<=num;i++)
            {
                if($('#sltYRel'+i).length>0)
                {
                    if(ShouYVal(i)==false)
                    {
                        return false;
                    }
                    per+=parseFloat($('#txtYPer'+i).val());
                }
            }
            alert(per);
            if(per!=1)
            {
                $('#erro').html('所有受益人受益比例之和不为1');
                return false;
            }
        }
    }
}
//验证发票信息
function ValInvoice()
{
    if($('#rdoYF').attr("checked")==true)
    {
        if(NameValidate('txtFName','FNameE','发票收货人')==false||selectNotDefault('sltPro','addrE','请选择省份')==false||selectNotDefault('sltCityF','addrE','请选择市区')==false||AddressValidate('sltPro','sltCityF','txtFAddress','FAddrE')==false||HomePhoneBValidate('txtFPhone','FPhoneE','1')==false||postCodeValidate('txtFPostCode','FPostCodeE',1)==false)
        return false;
    }
}
//其他相关验证
function ValOther()
{
  if(RemarkValidate('txtMemo','MemoE')==false)
  {
    return false;
  }
}
function AllVal()
{   
    //投保人验证
    if(SubVal()==false||ValHolderAll()==false||ValOther()==false||ValBenefit()==false||ValInvoice()==false)
    {
      return false;
    }
}
//组装数据到隐藏控件里面
function Write()
{
  if(AllVal()==false)
  {  
     $('#erro').text('请把必填信息填写完整');
     return false;
  }
   var type=new Object();
   //投保人id
   type.subid="-2";//登录了但是未选任何项
   if($("input:[name='holdlist']").length==0)
   {
     type.subid="-3";//未登录情况
   }
   else
   {
       $("input:[name='holdlist']").each(function(){
       if($(this).attr("checked")==true)
       {
        type.subid=$(this).val();
        return;
       }
      });
  }
  //本人，其他人还是团单
  $("input:[name='typ']").each(function(){
   if($(this).attr("checked")==true)
   {
    type.holderid=$(this).val();
    return;
   }
   })
   //受益人类型
    $("input:[name='rrs']").each(function(){
   if($(this).attr("checked")==true)
   {
    type.Benefit=$(this).val();
    return;
   }
   })
   //发票fr
   $("input:[name='fr']").each(function(){
   if($(this).attr("checked")==true)
   {
    type.Invoice=$(this).val();
    return;
   }
   })
   var otype="{\"subid\":\""+type.subid+"\",\"holderid\":\""+type.holderid+"\",\"Benefit\":\""+type.Benefit+"\",\"Invoice\":\""+type.Invoice+"\"}";
   var order="{\"otype\":"+otype+",";
   //投保人信息
   var smale=1;
   if($('#rdoMale').attr("checked")==true)
   {
     smale=0;
   }
   var sub="{\"sname\":\""+$('#txtSName').val()+"\",\"stype\":\""+$('#sltSType').val()+"\",\"sNo\":\""+$('#txtSNo').val()+"\",\"sbir\":\""+$('#txtBirth').val()+"\",\"smale\":\""+smale+"\",\"phone\":\""+$('#txtSPhone').val()+"\",\"email\":\""+$('#txtSEmail').val()+"\",\"province\":\""+$('#sltProvince').val()+"\",\"city\":\""+$('#sltCity').val()+"\",\"address\":\""+$('#txtAddress').val()+"\"}";
   order+="\"sub\":"+sub+",";
   //被保人信息
   
   if(type.holderid=="1")
   { 
     var holder="\"holder\":[";
     var maxI=parseInt($('#otherValNum').val());
    
     for(var i=1;i<=maxI;i++)
     {   
         if($('#txtHName'+i).length>0)
        {
         var male=1;
         if($('#rdoHMale'+i).attr("checked")==true)
         {
           male=0;
         }
         var hmarry=0;
         if($('#rdohMarry'+i).attr("checked")==true)
         {
            hmarry=1;
         }
         holder+="{\"name\":\""+$('#txtHName'+i).val()+"\",\"relation\":\""+$('#sltRel'+i).find('option:selected').text()+"\",\"htype\":\""+$('#sltHType'+i).val()+"\",\"hNo\":\""+$('#txtHNo'+i).val()+"\",\"hbir\":\""+$('#txtHB'+i).val()+"\",\"price\":\""+$('#price'+i).text()+"\",\"male\":\""+male+"\",\"phone\":\""+$('#txtHPhone'+i).val()+"\",\"marry\":\""+hmarry+"\",\"job1\":\""+$('#hJob1'+i).val()+"\",\"job2\":\""+$('#hJob2'+i).val()+"\",\"job3\":\""+$('#hJob3'+i).val()+"\",\"relationval\":\""+$('#sltRel'+i).val()+"\",\"email\":\""+$('#txtHEmail'+i).val()+"\",\"houseType\":\""+$('#sltHouseType'+i).find('option:selected').text()+"\",\"houseTypeVal\":\""+$('#sltHouseType'+i).val()+"\",\"bbrProvince\":\""+$('#sltBbrProvince'+i).val()+"\",\"bbrCity\":\""+$('#sltBbrCity'+i).val()+"\",\"bbrAddr\":\""+$('#txtBbrSubAddress'+i).val()+"\",\"bbrFullAddr\":\""+($('#sltBbrProvince'+i).find('option:selected').text()+$('#sltBbrCity'+i).find('option:selected').text()+$('#txtBbrSubAddress'+i).val())+"\",\"bbrPostcode\":\""+$('#txtBBRPostcode'+i).val()+"\"},";//jtQtBbr
         }
         else
         {
            continue;
         }
     }
         holder=holder.substr(0,holder.length-1);
         holder+="]";
         order+=holder+",";
    }
    else if(type.holderid=="0")
    {   
        var memarry=0;
        if($('#rdoMarry').attr("checked")==true)
        {
           memarry=1;
        }
        var holder="\"holder\":{\"marry\":\""+memarry+"\",\"job1\":\""+$('#meJob1').val()+"\",\"job2\":\""+$('#meJob2').val()+"\",\"job3\":\""+$('#meJob3').val()+"\",\"houseType\":\""+$('#sltMeHouseType1').find('option:selected').text()+"\",\"houseTypeVal\":\""+$('#sltMeHouseType1').val()+"\",\"bbrProvince\":\""+$('#sltMeBbrProvince1').val()+"\",\"bbrCity\":\""+$('#sltMeBbrCity1').val()+"\",\"bbrAddr\":\""+$('#txtMeBbrSubAddress1').val()+"\",\"bbrFullAddr\":\""+($('#sltMeBbrProvince1').find('option:selected').text()+$('#sltMeBbrCity1').find('option:selected').text()+$('#txtMeBbrSubAddress1').val())+"\",\"bbrPostcode\":\""+$('#txtMeBBRPostcode1').val()+"\"}";
        order+=holder+",";
        if(type.Benefit=="1")
        {   
            var Benefit="\"Benefit\":[";
            var benefitMax=parseInt($('#maxShouY').val());
            for(var i=1;i<=benefitMax;i++)
            {   
                if($('#txtYName'+i).length>0)
                {
                var ymale=1;
                if($('#RdoMaleY'+i).attr("checked")==true)
                {
                  ymale=0;
                }
                Benefit+="{\"yname\":\""+$('#txtYName'+i).val()+"\",\"yrelation\":\""+$('#sltYRel'+i).find('option[selected=true]').text()+"\",\"ytype\":\""+$('#sltYType'+i).val()+"\",\"yNo\":\""+$('#txtYNo'+i).val()+"\",\"ybir\":\""+$('#txtYBir'+i).val()+"\",\"ymale\":\""+ymale+"\",\"yPer\":\""+$('#txtYPer'+i).val()+"\"},";
                }
                else
                {
                    continue ;
                }
            }
            Benefit=Benefit.substr(0,Benefit.length-1);
            Benefit+="]";
            order+=Benefit+",";
        }
    }
    if(type.Invoice=="1")
    {
        var Invoice="\"name\":\""+$('#txtFName').val()+"\",\"province\":\""+$('#sltPro').val()+"\",\"city\":\""+$('#sltCityF').val()+"\",\"address\":\""+$('#txtFAddress').val()+"\",\"price\":\""+$('#fMoney').text()+"\",\"postcode\":\""+$('#txtFPostCode').val()+"\",\"phone\":\""+$('#txtFPhone').val()+"\"}";
        order+="\"Invoice\":{"+Invoice+",";
    }
    var other="{\"pName\":\""+trim($('#pName').text())+"\",\"timeSpan\":\""+$('#hdnDay').val()+"\",\"num\":\""+$('#number').text()+"\",\"sumMoney\":\""+$('#sumMoney').text()+"\",\"invoice\":\""+$('#Fp').text()+"\",\"allMoney\":\""+$('#allMoney').text()+"\",\"memo\":\""+encodeURI($('#txtMemo').val())+"\",\"pid\":\""+$('#hdnPId').val()+"\",\"insuredBeginDate\":\""+$('#txtInsuredStartDate').val()+"\",\"logoUrl\":\""+$('#logoUrl').attr("src")+"\"}";  
    order+="\"other\":"+other+"}"; 
   order=order.replace(new RegExp("\r\n","gm"),"");
   order = order.replace(/\s+/g,"");  
    $('#order').val(order);
    document.forms[0].action="FamilyNewPre_HA01.aspx";
    document.forms[0].method="post";
     showMask();
    document.forms[0].submit();
}

function LoadAll(order)
{
    if(order=='')
    {
        //处理火狐刷新后radio控件不兼容问题，请务必不要删除
//        $('#rdoOther').attr("checked",true);
        $('#rdoNF').attr("checked",true);
//        CreateHolderWrap(1);
    }
    else
    {
       order=eval('('+order +')');
       //绑定投保人信息
       var sub=order.sub;
       $('#txtSName').val(sub.sname);
       $('#sltSType').val(sub.stype);
       $('#txtSNo').val(sub.sNo);
       $('#txtBirth').val(sub.sbir);
       $('#txtSPhone').val(sub.phone);
       $('#txtSEmail').val(sub.email);
       $('#sltProvince').val(sub.province);
       getCity(sub.province,'sltProvince','sltCity')
       $('#sltCity').val(sub.city);
       $('#txtAddress').val(sub.address);
       if(sub.smale=='0')
       {
         $('#rdoMale').attr("checked",true);
       }
       else
       {
         $('#rdoFemale').attr("checked",true);
       }
       //相关信息
       var other=order.other;
       $('#pName').text(trim(other.pName));
       $('#hdnDay').val(other.timeSpan);
       $('#number').text(trim(other.num));
       $('#sumMoney').text(other.sumMoney);
       $('#sumMoney2').text(other.sumMoney);
       $('#txtInsuredStartDate').val(other.insuredBeginDate);
   
       
       $('#Fp').text(other.invoice);
       $('#allMoney').text(other.allMoney);
       $('#txtMemo').val( decodeURI(other.memo));
       //选中项的值
       var type=order.otype;
       //投保人id
       if(type.subid!="-3")
       {
           $("input:[name='holdlist']").each(function(){
            $(this).attr("checked",false);
            if($(this).val()==type.subid)
            {
                $(this).attr("checked",true);
            }
           });
       }
      //本人，其他人还是团单
       $("input:[name='typ']").each(function(){
       if($(this).val()==type.holderid)
       {
        $(this).attr("checked",true);
        return;
       }
       })
       //发票fr
       if(type.Invoice==0)
       {
         $('#rdoNF').attr("checked",true);
       }
       else
       {
         $('#rdoYF').attr("checked",true);
         $('#FPTalbe').show();
         var Invoice=order.Invoice;
         $('#txtFName').val(Invoice.name);
         $('#sltPro').val(Invoice.province);
         getCity(Invoice.province,'sltPro','sltCityF')
         $('#sltCityF').val(Invoice.city);
         $('#txtFAddress').val(Invoice.address);
         $('#fMoney').text(Invoice.price);
         $('#txtFPhone').val(Invoice.phone);
          $('#txtFPostCode').val(Invoice.postcode);
       }
       
      // alert('lengfeg');
       //被保险人
       if(type.holderid=="0")
       { 
           $('#meTable').show();
           $('#rdoMe').attr("checked",true);
           $('#shouyeren').show();
           var holder=order.holder;
          // $('#txtMeBir').val(holder.birth);
                 $('#sltMeHouseType1').val(holder.houseTypeVal);
               
                 $('#txtMeBbrSubAddress1').val(holder.bbrAddr);
                 $('#txtMeBBRPostcode1').val(holder.bbrPostcode);
                  $('#sltMeBbrProvince1').val(holder.bbrProvince);
               getCityHATF(holder.bbrProvince,'sltMeBbrProvince1','sltMeBbrCity1');
                $('#sltMeBbrCity1').val(holder.bbrCity);
               if(holder.marry=="0")
               {
                 $('#rdoNoMarry').attr("checked",true);
               }
               else
               {
                    $('#rdoMarry').attr("checked",true);
               }
               
             
                        
               $('#meJob1').val=order.job1;
               $('#meJob2').val=order.job2;
               $('#meJob3').val=order.job3;
               
           
           //alert('lengfeg');
          
           
           //受益人
           var Bene=order.Benefit;
           if(type.Benefit=="1")
           {
               $('#rdodef').attr("checked",true);
               ShowDefi('1');
               var num=Bene.length;
               var Bene1=Bene[0];
               if(num==1)
               {
                 var benefets=Benefet(Bene1.yrelation,Bene1.yname,Bene1.ytype,Bene1.yNo,Bene1.ybir,Bene1.yPer,Bene1.ymale);
                 BindBenefit(1,benefets);
               }
               else if(num>1)
               {
                   $('#shouY1').remove();
                  //创建收起
                  for(var i=1;i<=num-1;i++)
                  {
                    var beneI=Bene[i-1];
                    CreateBenefitHtml(i);
                    var benefets=Benefet(beneI.yrelation,beneI.yname,beneI.ytype,beneI.yNo,beneI.ybir,beneI.yPer,beneI.ymale);
                    BindBenefit(i,benefets);
                    $('#yno'+i).text(i);
                    $('#ytitle'+i).html(beneI.yname);
                  }
                  $('#shouybg').addClass('tjbbxr_k');
                  //创建最后一个列表的tjbbxr_k
                  CreateBenefitListHtml(num);
                  var beneNum=Bene[num-1];
                  var beList=Benefet(beneNum.yrelation,beneNum.yname,beneNum.ytype,beneI.yNo,beneNum.ybir,beneNum.yPer,beneNum.ymale);
                  BindBenefit(num,beList);
                  $('#maxShouY').val(num);
               }
           }
           CreateHolderWrap(1);
           $('#moreHolder').hide();
           $('#otherTable').hide();
       }
       else if(type.holderid=="1")
       {
           $('#rdoOther').attr("checked",true);
           var holderJson=order.holder;
           var num=holderJson.length;
           $('#otherNum').val(num);
           $('#otherValNum').val(num);
           $('#otherMoney').val(other.sumMoney);
         
           //创建收起项
             for(var i=1;i<=num;i++)
             {
                var holderj=holderJson[i-1];
                CreateHolderWrap(i);             
                var holder=Holder(holderj.relation,holderj.name,holderj.htype,holderj.hNo,holderj.hbir,holderj.price,holderj.male,holderj.phone,holderj.marry,holderj.job1,holderj.job2,holderj.job3,holderj.relationval,holderj.email,holderj.houseType,holderj.houseTypeVal,holderj.bbrProvince,holderj.bbrCity,holderj.bbrAddr,holderj.bbrFullAddr,holderj.bbrPostcode);
                BindHold(i,holder);
                $('#htitle'+i).html(holderj.name);
                $('#hno'+i).text(i);
                if(i!=num)
                {
                    SpreadHolder(i);
                }
             }
       }
    }
}
//复制被保险人姓名
function CopyName(i)
{
    $('#htitle'+i).text($('#txtHName'+i).val());
}
function CreateHolderWrap(i)
{
   
}
//绑定被保险人数据
function BindHold(i,holder)
{  
   $("select[id=sltRel"+i+"] option").each(function(){
       if($(this).text()==holder.relation)
       {  
          $(this).attr("selected",true);
       }
   });
   $('#sltRel'+i).val(holder.relation);
   $('#txtHName'+i).val(holder.name);
   $('#sltHType'+i).val(holder.type);
   $('#txtHNo'+i).val(trim(holder.number));
   $('#txtHB'+i).val(holder.birth);
   
    $('#txtHEmail'+i).val(holder.email);
    $('#txtBbrSubAddress'+i).val(holder.bbrAddr);
   $('#txtBBRPostcode'+i).val(holder.bbrPostcode); //houseTypeVal sltHouseType
     $('#sltHouseType'+i).val(holder.houseTypeVal);
   $('#price'+i).text(holder.price);
   if(holder.male==0)
   {
       $('#rdoHMale'+i).attr("checked",true);
   }
   else 
   {
       $('#rdoHFemale'+i).attr("checked",true);
   }
   $('#txtHPhone'+i).val(holder.phone);
   if(holder.marry=="1")
   {
     $('#rdohMarry'+i).attr("checked",true);
   }
   else
   {
        $('#rdohNoMarry'+i).attr("checked",true);
   }
   
   //alert("holder.bbrProvince"+holder.bbrProvince);
    $("select[id=sltBbrProvince"+i+"] option").each(function(){
               if($(this).val()==holder.bbrProvince)
               {  
                  $(this).attr("selected","selected");
                  return;
               }
            });
            cityList('sltBbrProvince'+i,'sltBbrCity'+i,'1',false,i);
         
             $("select[id=sltBbrCity"+i+"] option").each(function(){
               if($(this).val()==holder.bbrCity)
               {  
         //  alert($(this).val()+'---'+holder.bbrCity);
                   $(this).attr("selected","selected");
            // $('#sltBbrCity'+i.toString()).val($(this).val());
                  return;
               }
            });
            
   $('#hJob1'+i).val(holder.job1);
   $('#hJob2'+i).val(holder.job2);
   $('#hJob3'+i).val(holder.job3);
}

//受益人收起的html
function CreateBenefitHtml(i)
{
  
}
//受益人展开html
function CreateBenefitListHtml(nextNo)
{
    
}
//绑定受益人信息
function BindBenefit(i,benefit)
{
   $('#txtYName'+i).val(benefit.name);
   $('#sltYType'+i).val(benefit.type);
   $('#txtYNo'+i).val(benefit.number);
   $('#txtYBir'+i).val(benefit.birth);
   if(benefit.male==0)
   {
       $('#RdoMaleY'+i).attr("checked",true);
   }
   else 
   {
       $('#RdoFemaleY'+i).attr("checked",true);
   }
   $('#txtYPer'+i).val(benefit.percent);
   $("select[id=sltYRel"+i+"] option").each(function(){
       if($(this).text()==benefit.relation)
       {  
          $(this).attr("selected",true);
       }
    });
}
