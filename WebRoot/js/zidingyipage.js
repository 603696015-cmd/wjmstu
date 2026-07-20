function lablepage(a,i){
var name=$("#"+a).val();
var name1 =""+a+name;
var pagesql=$("#"+a).attr("title");
$.ajax({
			  type: 'POST',
			  url: "lableajax_pagelable.action",
			  data: {tableName:"lable_circulation",lableName:name,page:i,hidid:a,pagesql:pagesql},
			  async:false,//
			  success: function(data){
			 
			$('#'+name1).empty();
			$(data).appendTo($('#'+name1));
			
			
			
			  	
}
});
}
var searchstridval="";
function searchlablepage(a,i){

var name=$("#"+a).val();
var name1 =""+a+name;
$.ajax({
			  type: 'POST',
			  url: "lableajax_pagesearchlable.action",
			  data: {tableName:"lable_search",lableName:name,page:i,hidid:a,searchvalue:searchstridval},
			  async:false,//
			  success: function(data){ 
			  $('#'+name1).empty();
			  $(data).appendTo($('#'+name1));			  	
}
});
}
function zdysearchsubmit(a,i){


var name=$("#"+a).val();
var name1 =""+a+name;
$.ajax({
			  type: 'POST',
			  url: "lableajax_getallsearchvalue.action",
			  data: {tableName:"lable_search",lableName:name},
			  async:false,//
			  success: function(data){
			 
			var areaListObj = eval("("+data+")").jsonsorderField;
			searchstridval="";
			$.each(areaListObj,function(i,search){
				if(search.type==3){

					if($("input[name='"+search.searchName+"'][checked]").val()!=''){
						searchstridval+=search.name+"="+$("input[name='"+search.searchName+"'][checked]").val()+",";
						}
				}else if(search.type==2){

					if($("#"+search.searchName).val()!=''){
						searchstridval+=search.name+"="+$("#"+search.searchName).val()+",";
					}
					
				}else{
				
					searchstridval+=search.name+"="+$("#"+search.searchName).val()+",";
					
				}
				
			});
			
			
			  	
}
});
$.ajax({
			  type: 'POST',
			  url: "lableajax_pagesearchlable.action",
			  data: {tableName:"lable_search",lableName:name,page:i,hidid:a,searchvalue:searchstridval},
			  async:false,//
			  success: function(data){
			 
			$('#'+name1).empty();
			$(data).appendTo($('#'+name1));
			
			
			
			  	
}
});

}