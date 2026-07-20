function StringBuffer() {     
  this._strings = new Array;     
}     
StringBuffer.prototype.append = function (str) {     
  this._strings.push(str);     
};     
StringBuffer.prototype.toString = function () {     
 return this._strings.join("");     
};

function appendLi(objid,li){
	$("#"+objid).append($(li))
}

function listChildFunc(id,objid,obj){
    $(obj).addClass("hot-kc");
	var jd = [];
	var html = new StringBuffer();
	var li = "";
	var actionName = "";
	var params = "";
	$.ajax(
		{	async:false,  
			type:"post",   
		    url:"listChildFunc.action",   
		    data:{"x":Math.random(),"func.id":id},   
			success:function(data){
				jd = eval("("+data+")").check_json_result;
				if(jd!=null){
				    //$("#"+objid).find("li").remove();
				    $("#"+objid).remove();
				    //alert(document.getElementById(objid));
				    html.append("<ul class='kcList clearfix' id='centerul'>");
					for(var i=0;i<jd.length;i++){
						actionName = jd[i].funccode + ".action";
						params = jd[i].params;
						if(params!=null&&params!=""){
							actionName += "?" + params;
						}
						li = '<li>'+
            			'<div class="kcList-in">'+
              			'<div class="bd"> <a class="pic" href="'+actionName+'">';
              			if(jd[i].linkimg==undefined||jd[i].linkimg==""){
              				li += '<img src="images/default_link.jpg" alt=""/>';
              			}else{
              				li += '<img src="'+stuff_url + jd[i].linkimg+'" alt=""/>';
              			}
              			li += '</a>'+
                		'<p><a target=_blank href="'+actionName+'">'+jd[i].name+'</a></p>'+
              			'</div>'+
            			'</div>'+
          				'</li>';
					 	html.append(li);
					}
					html.append("</ul>");
					$(html.toString()).appendTo($("#haha"));
				}
		     }
     });
}