var debug=true;
var $id = function (id) {//避免与jQuery的$函数冲突
	return typeof id == "string" ? document.getElementById(id) : id;
};
if(!$){var $=$id;}
if(!window.console){
	window.console={}
	window.console.cache=[];
	window.console.constr=function(_value){
		if(!_value)return;
		var result = [];
		if (_value instanceof Function){
			result.push(_value);
		}else if(_value!=undefined&&Boolean(_value.nodeName)&&Boolean(_value.nodeType)){
			result.push(_value.nodeName.toLowerCase());
			result.push(_value.getAttribute("id")?"id="+_value.getAttribute("id"):"");
			result.push(_value.getAttribute("className")?"class="+_value.getAttribute("className"):"");
			return "&lt;"+result.join(" ")+"&gt;";
		}else if(_value instanceof Array){
			for(var i=0; i< _value.length; i++){
				if(typeof _value[i]== 'string')
					result.push("\""+_value[i]+"\"");
				else
					result.push(_value[i]);
			}
			return "["+result.join(", ")+"]";
		}else if(typeof _value == "object"){
			for (var p in _value){
				if(_value.hasOwnProperty(p) && p!='prototype'){
					result.push("'"+p+"':"+_value[p]);
				}
			};
			return "{"+result.join(", ")+"}";
		}else if(typeof _value == 'string'){
			return "\""+_value+"\"";
		}else if(typeof _value == 'number' && isFinite(_value)){
			result.push(_value);
		}else{
			result.push(_value);
		}
		return result.join("");
	}
	window.console.log=function(outputValue){
		if(!debug)return;
		if(!outputValue)return null;
		var bgColor=bgColor||"#fff";
		consoleDiv =$id("_console");
		if(!consoleDiv){
			consoleDiv=document.createElement("div");
			consoleDiv.id="_console";
			consoleDiv.style.cssText="position:absolute; z-index:9999; left:0%;top:"+Math.max(document.documentElement.scrollTop, document.body.scrollTop)+"px; width:62%; background-color:#fff; border:1px solid #359; opacity:0.9; filter:alpha(opacity=90); padding:4px;"
			consoleDiv.innerHTML='<div id="_consoleHead" style="background-color:#cde; height:20px; color:#000; font-size:12px; line-height:20px; cursor:move;"><a style="color:#123; float:right; text-decoration:none; margin:1px 2px 0;" href="javascript:$id(\'_console\').style.display=\'none\';void(0);">[关闭]</a><a style="color:#123; float:right; text-decoration:none; margin:1px 2px 0;" href="javascript:$id(\'_consoleBody\').innerHTML=\'\';void(0);">[清空]</a></div>';

			consoleDivBody=document.createElement("div");
			consoleDivBody.id="_consoleBody";
			consoleDivBody.style.cssText="font-size:12px; line-height:1.5;color:#333; width:100%; max-height:150px; overflow:auto;"
			consoleDivBody.innerHTML='';
			
			consoleDiv.appendChild(consoleDivBody);
			document.getElementsByTagName("BODY")[0].appendChild(consoleDiv);
			if(Drag)
				Drag.init($id("_consoleHead"),consoleDiv);//注册拖拽方法，可以使用自己的拖拽方法来代替，以减少代码量
		}
		consoleDiv.style.display="";
		var consoleDivTop=consoleDiv.style.top.replace(/\D/gi,"");
		if(consoleDivTop<Math.max(document.documentElement.scrollTop, document.body.scrollTop)||consoleDivTop>Math.max(document.documentElement.scrollTop, document.body.scrollTop)+(document.compatMode == "BackCompat"?document.body.clientHeight:document.documentElement.clientHeight))
			consoleDiv.style.top=Math.max(document.documentElement.scrollTop, document.body.scrollTop)+"px";
		var newItem=document.createElement("div");
		newItem.style.cssText="border-top:1px solid #cde; padding:3px;font-family:'Courier New'; font-size:13px; background-color:"+bgColor;
          var content = [];
          for(var i=0, len=arguments.length; i<len; i++){
            content.push( window.console.constr(arguments[i]) );
          }
		newItem.innerHTML= content.join(" ");
		$id("_consoleBody").appendChild(newItem);
		$id("_consoleBody").scrollTop=9999;
	};
}