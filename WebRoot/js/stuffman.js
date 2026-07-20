function setUrl(obj) {
	width=1060;
	height=500;
   	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
	//var rv = window.showModalDialog("editor/editor/filemanager/browser/default/browser.html?Type=&Connector=connectors/jsp/connector",
	// null,sFeature);
	var rv = window.showModalDialog("question_stuffList.action?x="+Math.random(),
	 window,sFeature);
	
	 if(null==rv){
	 	alert("没选择资源！");
	 	return ;
	 }
	 document.getElementById(obj).value=rv;
	 getUrlPath($("#"+obj),true);
}
function getUrlPath(obj,ref){
	var id = $(obj).val()?$(obj).val():$(obj).text();
	if($.trim(id)=='') return ;
	id = id.replace(/\\/g,'/');
	id = id.substring(id.lastIndexOf("/")+1,id.lastIndexOf("."));
	var returnStr;
	if( $("#stuff_div_"+id).length<=0){
		var t=/^[\d]{0,}$/;
		var div =$("<div>");
		$("body").append(div);
		$(div).attr("id","stuff_div_"+id);
		$(div).css("top",$(obj).offset().top+$(obj).height()+3);
		$(div).css("left",$(obj).offset().left);
		$(div).css("position","absolute");
		$(div).css("border","solid 1px #333");
		$(div).css("background","#fff");
		$(div).css("width","200");
		$(div).css("padding","3px 5px 3px 5px");
		$(div).css("height",$(obj).height());
		$(div).css("display","block");
		var str="";
		if(t.test(id))
			$.ajax({async:false,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
				type:"post",
				url:"question_stuffPath.action",data:{"qstuff.id":id,"x":Math.random()},success:function (data) {
			    var dataObj=eval("("+data+")");
				str = ("<div style='float:left;width:auto;'>"+dataObj.path+"</div>");
				returnStr=dataObj.path;
			}});
		else{
			str +=("<div style='float:left;'>不是系统资源</div>");
		}
		str+=("<div style='float:right;width：20' title='关闭'><a href=\"javascript:closeUrlPath('stuff_div_"+id+"');\">X</a></div><div style='clear:both;'></div>");
		$(div).html(str);
	}else{
		if(ref){
			var str="";
			if(t.test(id))
			$.ajax({async:false,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
				type:"post",
				url:"question_stuffPath.action",data:{"qstuff.id":id,"x":Math.random()},success:function (data) {
			    var dataObj=eval("("+data+")");
				str = ("<div style='float:left;width:auto;'>"+dataObj.path+"</div>");
				returnStr=dataObj.path;
				}});
			else{
				str +=("<div style='float:left;'>不是系统资源</div>");
			}
			str+=("<div style='float:right;width：20' title='关闭'><a href=\"javascript:closeUrlPath('stuff_div_"+id+"');\">X</a></div><div style='clear:both;'></div>");
			$(div).html(str);
		}
		$(div).css("top",$(obj).offset().top+$(obj).height()+3);
		$(div).css("left",$(obj).offset().left);
		$("#stuff_div_"+id).css("display","block");
	}
	return returnStr;
}
function closeUrlPath(obj){$("#"+obj).css("display","none");}