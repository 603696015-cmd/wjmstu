var xmlHttp;
//异步读取价格
function changeMoneyByNums()
{  
xmlHttp=GetXmlHttpObject();
if (xmlHttp==null)
  {
  alert ("Your browser does not support AJAX!");
  return;
  }    
var url="AshxFile/GetPrice.ashx?sid="+Math.random()+"&num="+$I('sltNum').value+"&proid="+$I('hdnPId').value;
xmlHttp.onreadystatechange=function(){statePriceGetOk()};
xmlHttp.open("POST",url,true);
xmlHttp.send(null);
}
////获取价格后处理函数
function statePriceGetOk()
{
   if (xmlHttp.readyState==4)
   {
       if(xmlHttp.responseText!="")
       {    
      
          if(xmlHttp.responseText=="0"||xmlHttp.responseText=="")
                      {
                        alert("价格计算错误"); 
                      }
           else
               {             
                        $I('number').innerHTML=$I('sltNum').value;
                        var result=xmlHttp.responseText;
                        var OrigalPrice = result.toString().split('|')[0];
                        var SpecialPrice= result.toString().split('|')[1];
                         if(parseFloat(SpecialPrice)>0)
                        {
                           
                           $I('sumMoney').innerHTML=SpecialPrice;
                        }
                        else
                        {
                       
                          $I('sumMoney').innerHTML=OrigalPrice;
                        }
                         
                         sltFAreaChange() ;   
               }
       }
          
     }
}

//创建xmlhttp对象
function GetXmlHttpObject()
{
var xmlHttp=null;
try
  {
  // Firefox, Opera 8.0+, Safari
  xmlHttp=new XMLHttpRequest();
  }
catch (e)
  {
  // Internet Explorer
  try
    {
    xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
    }
  catch (e)
    {
    xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
  }
return xmlHttp;
}
