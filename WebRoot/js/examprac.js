function QuizPaper(tblocksize ){ 
	this.tblocksize  = tblocksize ;
}
 
QuizPaper.prototype.showAllBlocks=function(){
	for(var i = 0 ; i <this.tblocksize ;i++)
	{
		document.getElementById("block_"+i).style.display="block";
		document.getElementById("b_t_"+i).className ="input";
		document.getElementById("b_t2_"+i).className ="input";
	}
 	document.getElementById("b_t_a").className ="inputover";
	document.getElementById("b_t2_a").className ="inputover";
} 
QuizPaper.prototype.showBlocks=function(id){
	for(var i = 0 ; i <this.tblocksize;i++){
		document.getElementById("block_"+i).style.display="none";
	 	document.getElementById("b_t_"+i).className ="input";
	 	document.getElementById("b_t2_"+i).className ="input";
	}
	document.getElementById("b_t_a").className ="input";
	document.getElementById("b_t2_a").className ="input";
	document.getElementById("block_"+id).style.display="block";
	document.getElementById("b_t_"+id).className ="inputover";
	document.getElementById("b_t2_"+id).className ="inputover";
}
function Dazi(id,_timeout){
	this.id=id;
	this._timeout =_timeout;
	this.passedtime = 0;
	this.fanwen = $("#dazi_fanwen"+this.id).html().replace(/<\/?.+?>/g,"");
	this.setIt= null;
}
Dazi.prototype.dazi_open=function(){
	 $("#dazi_area"+this.id).css("display","block");
	 $("#dazi_a"+this.id).html( "提交该题");
	 this.daojishi();
	 //alert(($("#dazi_fanwen"+this.id).html().replace(/<\/?.+?>/g,"")));
	 var dazi_ = this;
	 document.getElementById("dazi_a"+this.id).onclick=function(){
	   dazi_.dazi_submit();
	   return false;
	 }
}
Dazi.prototype.dazi_stop=function(){
	window.clearInterval(this.setIt);
}
Dazi.prototype.dazi_submit=function(){
 	 $("#dazi_a"+this.id).html( "该题已提交");
	 window.clearInterval(this.setIt);
	 $("#dazi_area"+this.id).css("display","none");
	  document.getElementById("dazi_a"+this.id).onclick=function(){
	  // dazi_.dazi_submit();
	  return false;
	 }
}
Dazi.prototype.daojishi=function(){
	var obj = this;
	this.setIt = window.setInterval(	function(){ 
		$("#dazi_djs"+obj.id).html("倒计时："+(obj._timeout-obj.passedtime)+"秒");
		var t =  obj.passedtime ;
		var myw = $("#dazi_answer4"+obj.id).val();
		var fw = obj.fanwen;
		//时间
		$("#dazi_answer3"+obj.id).attr("value",t );
		//准确率
		var r_c = 0;
		var w_c = 0;
		for(var i = 0 ;i<fw.length;i++){
			if(i>=myw.length)
				break;
			if(fw.charAt(i)==myw.charAt(i)){
				r_c++;
				//xx+=fanwen.charAt(i)+"=="+mywen.charAt(i)+"<br/>"
			}else{
				w_c++;
				//document.getElementById("dazi_fanwen_").innerHTML=
			}
		}
		$("#dazi_answer2"+obj.id).attr("value",r_c);
		//速 度
		$("#dazi_answer1"+obj.id).attr("value",parseFloat(myw.length/t));
		
		if(obj._timeout<=obj.passedtime){
			window.clearInterval(obj.setIt);
			$("#dazi_area"+obj.id).css("display","none");
			$("#dazi_a"+obj.id).html( "该题已提交");
		}
		obj.passedtime++;
	},1000);
}

function Qsearch(id){
	this.id=id;
}
Qsearch.prototype.qanswer_search= function(){
	var thisid = this.id;
	$.post("quiz_searchanswer.action", {
	"pS":10,
	"questionart.title":$("#qanswer_title"+this.id).val(),
	"questionart.content":thisid,
	"x":Math.random
	}, 
	function (data) {
		$("#answer_list"+thisid).html(data);
	});
	$("#answer_list"+this.id).css("display","block");
}
Qsearch.prototype.page1= function (i){
	var thisid = this.id;
	$.post("quiz_searchanswer.action", {
	"pS":10,
	"pN":i,
	"questionart.title":$("#qanswer_title"+this.id).val(),
	"questionart.content":thisid,
	"x":Math.random
	}, 
	function (data) {
		$("#answer_list"+thisid).html(data);
	});
	$("#answer_list"+this.id).css("display","block");
}
Qsearch.prototype.qanswer_setanswer=function(obj){
	document.getElementById("qanswer_ans"+this.id).value=obj.innerHTML ;
	$("#answer_list"+this.id).css("display","none");
}
function upload_offices(blockid,qid){
	 $.ajaxFileUpload
	    (
	      {
	           url:'upload_office_stuff.action', //你处理上传文件的服务端
	           secureuri:false,
	           fileElementId:"office_"+blockid+"_"+qid,
	           dataType: 'json',
	           data: {//加入的文本参数
			    "filename":"_"+blockid+"_"+qid 
			   },
	           success: function (data, status)
	              {
	                 	//alert(data.message );
	                 	if(json.message =="up_file_toobig")
	               		alert("文件太大，必须小于10M!");
	               		if(json.message =="up_file_succ")
	               		alert("上传成功!");
	               		if(json.message =="up_file_input")
	               		alert("请输入文件");
	               		if(json.message =="up_file_err")
	               		alert("文件上传失败");
	                 }
	              }
	        )
      return false;
} 
