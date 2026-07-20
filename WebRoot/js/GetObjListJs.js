// JScript 文件
function GetSpreadsheetsJs(n,m,strPid,strdiv)    //生成列表生成的字符串
 {
   $.ajax({
   type: "POST",
   url: "/AjaxFile/AjaxFile.aspx",
   data: "type=SpreadsheetsView&n="+n+"&m="+m+"&strPid="+strPid+"&" + Math.random(),
   success: function(msg){ 
   $('#'+strdiv).html(msg);
  
   }
}); 
 }

