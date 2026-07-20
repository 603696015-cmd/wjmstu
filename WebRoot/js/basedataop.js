function searchUsersInit(){ 
     width=600;
	 height=500;
	 var danwei ="";
  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
	 var rv =  window.showModalDialog("optionDep.action?searbm="+bmidArr.join(',')+"&x="+Math.random(),null,sFeature); 
	 if(rv == null){
		alert("您没有任何选取！");			
	 }else{ 
		 /*bmarr = getArray(rv);  
		 var bmid = bmarr.pop();   
		 bmidArr = bmid.split("-"); 
		 for(var i = 0; i < bmarr.length;i++){     
			 danwei =danwei+"<div id='danwei_"+i+"' style='float:left;background-color:#dcddde;padding-left:16px;width=120;'>"+bmarr[i]+" <span style='color:red;' onclick=onX("+i+",'"+bmidArr[i]+"') >X</span></div>";
			 var hid = "<input type='checkbox' name='bmtj' value = '"+bmarr[i]+"'>"
			 document.getElementById("danwei").innerHTML= danwei; 
		 } 
		 document.getElementById("danwei").style.display='block';*/
		 if(rv.length>0){
		 	$("#danwei").css("display","block");
		 	bmidArr = new Array();
		 	$("#danwei").html("");
		 }else{
		 	$("#danwei").css("display","none");
		 	bmidArr = new Array();
		 	$("#danwei").html("");
		 }
		 for(var i = 0; i < rv.length;i++){
		 	var did = rv[i];
		 	bmidArr[bmidArr.length]=did;
		 	if($("#danwei_"+(bmidArr.length-1)).length<=0){
		 		$.ajax({async:false,  //   
				type:"post",   
			    url:"dep_view.action",   
			    data:{"x":Math.random(),"department.id":did,"optype":"ajax"},   
				success:function(data){
					jd = eval("("+data+")");
					$("#danwei").append("<div id='danwei_"+(bmidArr.length-1)+
		 	 	"' style='float:left;background-color:#dcddde;width=122;'><span style='width:100px'>"
		 	    +jd.name+"</span> <span style='color:red;cursor:pointer;float:right;width:20px' onclick=onX("+(bmidArr.length-1)+",'"+did+"') >X</span></div>");
			 }});
			 }
		 } 
	 }  
}
function searBaseDatatInit(j){ 
	 typeid = j;
     width=600;
	 height=500;
	 var tj =""; 
  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
	 if(j == 1){//1  工 种   
	 	var rv = window.showModalDialog("optionBaseDatat.action?searbase="+jzidArr.join(',')+"&baseDatat.typeid="+j+"&x="+Math.random(),null,sFeature);     
		
		 if(rv == null){
			alert("您没有任何选取！");					
		 }else{
			jzArr = getArray(rv); 
			//alert(jzArr);//基础数据名+id
			var jzTid = jzArr.pop();
			//alert(jzTid);//类别id
			var jzid = jzArr.pop(); 
			//alert(jzid);//基础数据id
		 	jzidArr = jzid.split("-");
		 	//alert(rv);
		 	document.getElementById("jztj").innerHTML= "";
		 	document.getElementById("jztj").style.display='none';
			for(var i = 0; i < jzArr.length;i++){ 
			 	tj =tj+"<div id='jztj_"+i+"' style='float:left;background-color:#dcddde;padding-left:16px;width:100;'>"+jzArr[i]+" <span style='color:red;' onclick=onbaseX('jztj_','"+i+"','"+jzTid+"','"+jzidArr[i]+"')>X</span></div>";
			 	document.getElementById("jztj").innerHTML= tj;
			    document.getElementById("jztj").style.display='block'; 
		    }
	    }
	}else if(j == 2){//职务 
	 	var rv = window.showModalDialog("optionBaseDatat.action?searbase="+zwidArr.join(',')+"&baseDatat.typeid="+j+"&x="+Math.random(),null,sFeature);     
	 	 if(rv == null){
			alert("您没有任何选取！");					
		 }else{
		 	zwArr = getArray(rv); 
			var zwTid = zwArr.pop();
			var zwid = zwArr.pop(); 
		 	zwidArr = zwid.split("-");
		 	document.getElementById("zwtj").innerHTML= "";
		 	document.getElementById("zwtj").style.display='none';
			for(var i = 0; i < zwArr.length;i++){ 
			 	tj =tj+"<div id='zwtj_"+i+"' style='float:left;background-color:#dcddde;padding-left:16px;width:100;'>"+zwArr[i]+" <span style='color:red;' onclick=onbaseX('zwtj_','"+i+"','"+zwTid+"','"+zwidArr[i]+"')>X</span></div>";
			 	document.getElementById("zwtj").innerHTML= tj;
			    document.getElementById("zwtj").style.display='block';
	    	}
	    }
	}else if(j == 3){//职级
		var rv = window.showModalDialog("optionBaseDatat.action?searbase="+zjidArr.join(',')+"&baseDatat.typeid="+j+"&x="+Math.random(),null,sFeature);     
		 if(rv == null){
			alert("您没有任何选取！");					
		 }else{
			zjArr = getArray(rv);  
			var zjTid = zjArr.pop();
			var zjid = zjArr.pop(); 
		 	zjidArr = zjid.split("-");
		 	document.getElementById("zjtj").innerHTML= "";
		 	document.getElementById("zjtj").style.display='none';
			for(var i = 0; i < zjArr.length;i++){ 
			 	tj =tj+"<div id='zjtj_"+i+"' style='float:left;background-color:#dcddde;padding-left:16px;width:100;'>"+zjArr[i]+" <span style='color:red;' onclick=onbaseX('zjtj_','"+i+"','"+zjTid+"','"+zjidArr[i]+"')>X</span></div>";
			 	document.getElementById("zjtj").innerHTML= tj;
			    document.getElementById("zjtj").style.display='block';
	    	}
	    }
	}else if(j == 4){//岗位
		var rv = window.showModalDialog("optionBaseDatat.action?searbase="+gwidArr.join(',')+"&baseDatat.typeid="+j+"&x="+Math.random(),null,sFeature);     
		 if(rv == null){
			alert("您没有任何选取！");					
		 }else{
			gwArr = getArray(rv);  
			var gwTid = gwArr.pop();
			var gwid = gwArr.pop(); 
		 	gwidArr = gwid.split("-");
		 	document.getElementById("gwtj").innerHTML= "";
		 	document.getElementById("gwtj").style.display='none'; 
			for(var i = 0; i < gwArr.length;i++){ 
			 	tj =tj+"<div id='gwtj_"+i+"' style='float:left;background-color:#dcddde;padding-left:16px;width:100;'>"+gwArr[i]+" <span style='color:red;' onclick=onbaseX('gwtj_','"+i+"','"+gwTid+"','"+gwidArr[i]+"')>X</span></div>";
			 	document.getElementById("gwtj").innerHTML= tj;
			    document.getElementById("gwtj").style.display='block';
	    	}
	    }
	}else if(j == 5){//地市
		var rv = window.showModalDialog("optionBaseDatat.action?searbase="+dsidArr.join(',')+"&baseDatat.typeid="+j+"&x="+Math.random(),null,sFeature);     
		 if(rv == null){
			alert("您没有任何选取！");					
		 }else{
			dsArr = getArray(rv);  
			var dsTid = dsArr.pop();
			var dsid = dsArr.pop(); 
		 	dsidArr = dsid.split("-");
		 	document.getElementById("dstj").innerHTML= "";
		 	document.getElementById("dstj").style.display='none'; 
			for(var i = 0; i < dsArr.length;i++){ 
			 	tj =tj+"<div id='dstj_"+i+"' style='float:left;background-color:#dcddde;padding-left:16px;width:100;'>"+dsArr[i]+" <span style='color:red;' onclick=onbaseX('dstj_','"+i+"','"+dsTid+"','"+dsidArr[i]+"')>X</span></div>";
				document.getElementById("dstj").innerHTML= tj;
			    document.getElementById("dstj").style.display='block';
		    }
	    }
	}else{
	 	alert("类型不匹配");
	 } 
}
function onX(i,name){
	document.getElementById("danwei_"+i).style.display='none'; 
	bmidArr = bmidArr.del(name); 
	if(bmidArr.length == 0){
		document.getElementById("danwei").style.display='none';
	} 
}
function onbaseX(id,i,Tid,name){    
	if(Tid == 1){    
		jzidArr = jzidArr.del(name);  
		if(jzidArr.length == 0){
			document.getElementById("jztj").style.display='none'; 
		}    
	}else if(Tid == 2){   
		zwidArr = zwidArr.del(name); 
		if(zwidArr.length == 0){
			document.getElementById("zwtj").style.display='none';
		}     
	}else if(Tid == 3){  
		zjidArr = zjidArr.del(name); 
		alert(zjidArr.length);
		if(zjidArr.length == 0){
			document.getElementById("zjtj").style.display='none'; 
		}       
	}else if(Tid == 4){  
		gwidArr = gwidArr.del(name); 
		if(gwidArr.length == 0){
			document.getElementById("gwtj").style.display='none';
		}     
	}else if(Tid == 5){  
		dsidArr = dsidArr.del(name); 
		if(dsidArr.length == 0){
			document.getElementById("dstj").style.display='none';
		}       
	}   
	document.getElementById(id+i).style.display='none'; 
}
function getArray(array){ 
	var arr = new Array();
	if(array.length != 0){
		for(var i = 0; i < array.length;i++){     
		 	arr.push(array[i]); 
		} 
	}
	return arr;
}
 Array.prototype.del=function(name) { 
  var news = new Array();   
  for(var i = 0;i<this.length;i++){
  	if(this[i] != name){ 
  		news.push(this[i]);
  	}
  }		  
  return news; 
 }

//初始化考场和培训班条件数据
function loadData(eroomNameArr,classNameArr){
	//var eroomNameArr="<s:property value="elclass.elRegistration.examRoomIds" />";
	//alert(eroomNameArr);
	if(eroomNameArr!=""&&eroomNameArr.split(",")!=-1){
		eroomNameArr=eroomNameArr.split(",");
		for(var i=0;i<eroomNameArr.length;i++){
			addExamRoomUserinfo(eroomNameArr[i])
		}
	}else{
		if(eroomNameArr!=""&&parseInt(eroomNameArr)>0){
			addExamRoomUserinfo(eroomNameArr);
		}
	}
	//var classNameArr="<s:property value="elclass.elRegistration.elclassIds" />";
	//alert(classNameArr);
	if(classNameArr!=""&&classNameArr.split(",")!=-1){
		classNameArr=classNameArr.split(",");
		for(var i=0;i<classNameArr.length;i++){
			addElclassUserinfo(classNameArr[i])
		}
	}else{
		if(classNameArr!=""&&parseInt(classNameArr)>0){
			addElclassUserinfo(classNameArr);
		}
	}
}