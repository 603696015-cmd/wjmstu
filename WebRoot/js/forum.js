
function searchUsers(){
	 width=600;
	 height=400;
  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
	 var rv =  window.showModalDialog("forum_searchUsersInit.action",null,sFeature);
	 if(null==rv){
	 	alert('您没有选择人员！');
	 }else{
	 	if(rv[0]<=0) 
		alert('您没有选择人员！');
	 	document.getElementById("realname").innerHTML=rv[1];
	 	document.getElementById("userid").value=rv[0];
	 }
}



