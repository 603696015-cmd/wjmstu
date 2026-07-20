//分级下拉选项
  function change_selectlevel(obj,number,level){
  	var id = obj.getAttribute("id");
  	var name = id.substring(0,id.lastIndexOf("_"));
  	number = parseInt(number) + 1;
  	var value = obj.options[obj.selectedIndex].value;
  	var text =  obj.options[obj.selectedIndex].text;
  	if(value!=""){
  		value = parseInt(value);
  	}
  	if(value != 0){
  		$.ajax({
		  type: 'POST',
		  url: "getselectLevelListByParentId.action",
		  data: {'selectLevel.id':value},
		  async:false,//同步
		  success: function(data){
		  		data = eval("("+data+")").check_json_result;
		  		//构建下一级的selecthtml
	  			if(data!=null&&data.length>0){//有子节点的时候
	  				//每次改变前将之前的删除
	  				$("#"+name+"_"+number).empty();
				  	var selecthtml_body = "<option value='0'>请选择</option>";
	  				for(var i=0;i<data.length;i++){
	  					selecthtml_body += "<option value='"+data[i].id+"'>"+data[i].name+"</option>";
	  				}
			  		$(selecthtml_body).appendTo($("#"+name+"_"+number));
			  		if(level>number){
				  		var nodehtml = "";
			  			for(var k=number+1;k<level+1;k++){
			  				if(document.getElementById(name+"_"+k)!=null){
			  					document.getElementById(name).parentNode.removeChild(document.getElementById(name+"_"+k));
			  					nodehtml  = createDefaultSelect(name,k,level);
			  					$(nodehtml).appendTo($("#"+name+"__"));
			  				}
			  			}
			  		}
	  			}else{//无子节点的时候
	  				$("#"+name+"_"+number).empty();
	  				$("<option value='0'>请选择</option>").appendTo($("#"+name+"_"+number));
	  				if(level>number){
				  		var nodehtml = "";
			  			for(var k=number+1;k<level+1;k++){
			  				if(document.getElementById(name+"_"+k)!=null){
			  					document.getElementById(name).parentNode.removeChild(document.getElementById(name+"_"+k));
			  					nodehtml  = createDefaultSelect(name,k,level);
			  					$(nodehtml).appendTo($("#"+name+"__"));
			  				}
			  			}
			  		}
	  			}
		  }
		});
  	}else{//选择'请选择'的时候
  		$("#"+name+"_"+number).empty();
	  	$("<option value='0'>请选择</option>").appendTo($("#"+name+"_"+number));
	  	if(level>number){
	  		var nodehtml = "";
  			for(var k=number+1;k<level+1;k++){
  				if(document.getElementById(name+"_"+k)!=null){
  					document.getElementById(name).parentNode.removeChild(document.getElementById(name+"_"+k));
  					nodehtml  = createDefaultSelect(name,k,level);
  					$(nodehtml).appendTo($("#"+name+"__"));
  				}
  			}
  		}
  	}
  	
  	var realvalue = "";
	if(level != 0){
		for(var i=1;i<level+1;i++){
			var o = document.getElementById(name+"_"+i);
			if(i == level+1){
				realvalue += o.options[o.selectedIndex].value + "__" + o.options[o.selectedIndex].text;
			}else{
				realvalue += o.options[o.selectedIndex].value + "__" + o.options[o.selectedIndex].text + "___";
			}
		}
	}
	if(realvalue!=""){
		realValue = realvalue.substring(0,realvalue.lastIndexOf("___"));
	}
	document.getElementById(name).value = realValue;
  }
  
  function createDefaultSelect(name,k,level){
  	var node = "<select name='"+name+"_"+k+"' id='"+name+"_"+k+"' onchange='javascript:change_selectlevel(this,"+k+","+level+");'><option value='0'>请选择</option></select>";
  	return node;
  }
  
