function  SetPrice(result)
{
   var isSpecialPrice=0;//非特价
   if(jQuery("#divPrice").get(0).style.display!="none")
    {
       isSpecialPrice =1;//特价     
    }
  if(result=="0"||result=="")
  {
    jQuery("#priceArea").text("计算错误"); 
  }
  else
  {
    var OrigalPrice = result.toString().split('|')[0];
    var SpecialPrice =result.toString().split('|')[1];
    if(isSpecialPrice==1)
    {
     jQuery("#lblPrice").text(OrigalPrice);
      jQuery("#lblProPrice").text(SpecialPrice);//spanJiFen
      jQuery("#spanJiFen").text(SpecialPrice);
      
    }
    else
    {
      jQuery("#lblProPrice").text(OrigalPrice);   
       jQuery("#spanJiFen").text(OrigalPrice);
    }     
  }

}
jQuery(function(){
		jQuery("#sltBuyNum").selectbox(); 
		jQuery("#sltBuyNum").change(function(){
		 jQuery.ajax({ 
                 type:'post',
                 url:'../AshxFile/GetPrice.ashx',
                 dataType:'text',  
                 data:{'num':jQuery('#sltBuyNum').val(),'proid':jQuery('#hdnId').val()},
                 success:function(result){ SetPrice(result); } 
                   });
		});      
        jQuery("#tbFamilyGuids tr").mouseover(function(){  
          jQuery(this).find("td").css("background-color","#F1F8FF");
         });
        jQuery("#tbFamilyGuids tr").mouseout(function(){
          jQuery(this).find("td").css("background-color","#FFFFFF");
        });
 });


