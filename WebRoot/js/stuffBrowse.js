function StuffObj(id,title,fileext,type,size,createtime,modifytime,qpstuffid,path,isSeach,stuffPath){
	this.id=id;
	this.title=title;
	this.fileext=fileext;
	this.type=type;
	this.size=size;
	this.createtime=createtime;
	this.modifytime=modifytime;
	this.qpstuffid=qpstuffid;
	this.path=path;
	this.isSeach=isSeach;
	this.stuffPath=stuffPath;
	//alert(this.path);
}
//显示资源
StuffObj.prototype.displayStuff=function(){
	var stuCt=$("#stuffsContent");
	var d_=$("<div>");
	d_.css("width","70px");
	d_.css("height","80px");
	d_.css("float","left");
	d_.css("margin-left","18px");
	d_.css("margin-right","18px");
	d_.css("margin-top","3px");
	d_.css("margin-botton","3px");
	d_.css("word-wrap","break-word");
	d_.css("word-break","break-all");
	d_.css("text-align","center");
	d_.attr("id",this.id);
	d_.attr("type",this.type);
	d_.attr("fileext",this.fileext);
	d_.attr("isSeach",this.isSeach);
	d_.focusin(function(){
		dtest_.css("background-color","#4DAED8");
		dtest_.css("color","#fff");
	});
	d_.focusout(function(){
		dtest_.css("background-color","#ffffff");
		dtest_.css("color","#000");
	});
	d_.dblclick(function(){
		if(this.type==5){
			openFolder(this.id);
		}else{
			//filePreview(this.id);
			filePreview(this.id,this.fileext,this.isSeach);
		}
	});
	var img_=$("<img>");
	//img_.css("position","relative");
	//img_.attr("id","sfimg_"+this.id);
	img_.attr("src",getImgPath(this));
	//window.setTimeout("",100);
	//alert("nihao");
	imageProcess(img_);
	//img_.css("width","50px");
	//img_.css("height","50px");
	//img_.attr("title",""+shortTitle(this.title,20,this.fileext,this.type)+"\n修改日期："+shortTime(this.modifytime)+"\n大小："+this.size);
	var imgDiv=$("<div>");//图片容器
	imgDiv.css("width","50px");
	imgDiv.css("height","50px");
	imgDiv.css("vertical-align","center");
	//imgDiv.css("text-align","center");
	imgDiv.attr("title",""+shortTitle(this.title,20,this.fileext,this.type)+"\n修改日期："+shortTime(this.modifytime)+"\n大小："+this.size);
	imgDiv.append(img_);
	var dtest_=$("<span>");
	dtest_.css("width","65px");
	dtest_.css("height","20px");
	//dtest_.css("padding","6px");
	dtest_.css("overflow","hidden");
	dtest_.html(shortTitle(this.title,10,this.fileext,this.type));
	var dtest_3=$("<div>");
	dtest_3.css("test-align","center");
	var adownload=$("<a>");
	adownload.attr("href","javascript:fileDownload("+this.id+","+this.qpstuffid+");");
	adownload.html("下载");
	if(isDownload(this.type)){
		dtest_3.append(adownload);
	}
	var arename=$("<a>");
	arename.attr("href","javascript:rename("+this.id+",'"+this.title+"');");
	arename.html("重命名");
	arename.css("margin-left","6px");
	dtest_3.append(arename);
	//d_.html(img_);
	d_.html(imgDiv);
	d_.append(dtest_);
	d_.append(dtest_3);
	stuCt.append(d_);
};
//截取标题(获取文件名)
function shortTitle(title,size,fileext,type){
	if(title.length>size){
		return title.substring(0,size)+"...";
	}
	if(type==5){
		return title;
	}
	return title+"."+fileext;
}
//截取时间
function shortTime(time){
	return time.substring(0,time.lastIndexOf(":"));
}
var array=new Array("ai","asf","avi","bmp","doc","dvd","dwg","flv","fig",
"html","ico","log","max","mp3","mp4","mpeg","pdf","png","ppt","psd","rar",
"rtf","swf","text","txt","vob","wav","wma","wmv","xls","xml","zip");

//根据扩展名获取图片路径
function getImgPath(obj){
	//alert(obj.type);
	//alert(obj.path);
	if(obj.type==5){
		return "images/xpimgs/filecon.jpg";
	}else if(obj.fileext=="jpg"||obj.fileext=="jpeg"||obj.fileext=="gif"||obj.fileext=="png"||obj.fileext=="tiff"){
		//return "getImageStream.action?qstuff.id="+id;
		//alert(obj.path);
		if(obj.isSeach==1){
			return "elstuffs/"+obj.stuffPath+"."+obj.fileext;
		}else{
			return "elstuffs/"+obj.path+"."+obj.fileext;
			//return "getImageStream.action?qstuff.id="+obj.id;
		}
	}else{
		//return "images/sysimgs/nofile.jpg";
		//return "images/sysimgs/"+obj.fileext+".jpg";
		for(var i=0;i<array.length;i++){
			if(obj.fileext==array[i]){
				return "images/sysimgs/"+obj.fileext+".jpg";
			}
		}
		return "images/sysimgs/nofile.jpg";
	}
}
/*
function getImgPath(obj){
	//alert(obj.type);
	if(obj.type==5){
		return "images/xpimgs/filecon.jpg";
	}
	if(obj.fileext=="wmv"||obj.fileext=="avi"||obj.fileext=="mpg"||obj.fileext=="mpeg"||obj.fileext=="swf"||obj.fileext=="flv"){
		return "images/sysimgs/wmv.jpg";
	}else if(obj.fileext=="txt"||obj.fileext=="text"||obj.fileext=="java"||obj.fileext=="log"){
		return "images/sysimgs/txt.jpg";
	}else if(obj.fileext=="doc"){
		return "images/sysimgs/doc.jpg";
	}else if(obj.fileext=="xls"){
		return "images/sysimgs/xls.jpg";
	}else if(obj.fileext=="ppt"){
		return "images/sysimgs/ppt.jpg";
	}else if(obj.fileext=="zip"||obj.fileext=="rar"){
		return "images/sysimgs/zip.jpg";
	}else if(obj.fileext=="pdf"){
		return "images/sysimgs/pdf.jpg";
	}else if(obj.fileext=="jpg"||obj.fileext=="jpeg"||obj.fileext=="gif"||obj.fileext=="png"){
		//return "getImageStream.action?qstuff.id="+id;
		return "elstuffs/"+obj.path+"."+obj.fileext;
	}else{
		//return "images/sysimgs/nofile.jpg";
	}
}
*/
//是否可以下载
function isDownload(type){
	switch(type){
		case "0":
		case "1":
		case "2":
		case "3":
		case "4":
		return true;
		default :
		return false;
	}
}

function imageProcess(obj){
	var image=new Image();
	image.src=obj.attr("src");
	//if(obj.attr("src").indexOf("elstuffs")==0){
	//alert(image.readyState);
	image.src = image.src + '?t=' + Math.random();
	image.onreadystatechange=function(){
		//alert(image.readyState);
		//alert(image.src);
	 	if(this.readyState=="loaded"||image.readyState=="complete") {
			//alert(["图片大小是:",image.width,image.height]);
			if(image.width>=image.height){
				if(image.width>50){
					var n=image.width/50.0;
					image.height=image.height/n;
					image.width=50;
					obj.css("margin-top",(50-image.height)/2.0+"px");
				}else{
					if(image.height!=0&&image.width!=0){
						obj.css("margin-top",(50-image.height)/2.0+"px");
					}
				}
			}else{
				if(image.height>50){
					var n=image.height/50.0;
					image.width=image.width/n;
					image.height=50;
					obj.css("margin-left",(50-image.width)/2.0+"px");
				}
			}
			obj.css("width",image.width);
			obj.css("height",image.height);
			//alert(image.src);
			/*
			if(image.width==0&&image.height==0){
				//alert(image.src);
				obj.attr("src","images/sysimgs/nofile.jpg");
				obj.css("width","50px");
				obj.css("height","50px");
			}
			*/
		}
	}
	//}
}