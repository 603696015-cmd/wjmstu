function showorhidden(id){
	var disp= $("#"+ id ).css("display");
	disp= disp=="none"?"block":"none";
	$("#"+ id ).css("display",disp) ;
}
function addepbaseinfo(){
	var ep_title = $("#ep_title").val();
	var ep_description = $("#ep_description").val();
	var ep_during = $("#ep_during").val();
	var ep_score = $("#ep_score").val();
	var ep_showmod =$(":radio[name='examPaper.showmod'][checked]").val();
	var ep_eplid =$("#ep_eplid").val();
	var queryurl = "";
	var ets = document.getElementsByName("eatitle");
	var eahrefs = document.getElementsByName("eahref");
	for(var i = 0 ; i<ets.length;i++){
		queryurl+=ets[i].value+"=th-"+eahrefs[i].value+"-th=";
	}
	queryurl = queryurl.substring(0,queryurl.length-4);
	$("#ep_queryurl").attr("value",queryurl);
	if(ep_title==='')
	{
		alert("请填写标题");
		$("#ep_title").focus() ;
		return false;
	}
	if(ep_eplid<=0)
	{
		alert("请选择所属试卷库，如果没有能管辖的试卷库，请与管理员联系。");
		return false;
	}
	return true;
	//alert(ep_title+"---"+ep_description+"---"+ep_during+"---"+ep_score+"---"+ep_eplid+"---"+ep_random);
	/*$.post("exampaper_add.action", {
		"examPaper.title":ep_title,
		"examPaper.description": ep_description,
		"examPaper.during": ep_during,
		"examPaper.ep_tscore": ep_score,
		"examPaper.showmod": ep_showmod,
		"examPaper.epl.id": ep_eplid,
		"x":Math.random()
	}, 
	function (data) {
		$("#ep_baseinfo" ).html(data);
	});*/
}

function alterepbaseinfoinit(){
	var ep_id = $("#ep_id").val();
	$.post("exampaper_alterInit.action", {
		"examPaper.id": ep_id,
		"x":Math.random()
	}, 
	function (data) {
		$("#ep_baseinfo" ).html(data);
	});
}
function alterepbaseinfo(){
	var ep_title = $("#ep_title").val();
	var ep_description = $("#ep_description").val();
	var ep_during = $("#ep_during").val();
	var ep_score = $("#ep_score").val();
	var ep_showmod =$(":radio[name='examPaper.showmod'][checked]").val();
	var ep_eplid =$("#ep_eplid").val();
	var ep_id = $("#ep_id").val();
	var ets = document.getElementsByName("eatitle");
	var eahrefs = document.getElementsByName("eahref");
	var queryurl = "";
	var ep_showType =$(":radio[name='examPaper.showType'][checked]").val();
	for(var i = 0 ; i<ets.length;i++){
		queryurl+=ets[i].value+"=th-"+eahrefs[i].value+"-th=";
	}
	ep_tscore=ep_score;
	queryurl = queryurl.substring(0,queryurl.length-4);
	var ep_queryurl = queryurl;//$("#ep_queryurl").val();
	if(ep_title=='')
	{
		alert("请填写标题");
		$("#ep_title").focus()
		return false;
	}
	if(ep_eplid<=0)
	{
		alert("请选择所属试卷库，如果没有能管辖的试卷库，请与管理员联系。");
		return false;
	}
	//alert(ep_title+"---"+ep_description+"---"+ep_during+"---"+ep_score+"---"+ep_eplid+"---"+ep_random);
	$.post("exampaper_alter.action", {
		"examPaper.title":ep_title,
		"examPaper.queryurl":ep_queryurl,
		"examPaper.description": ep_description,
		"examPaper.during": ep_during,
		"examPaper.ep_tscore": ep_score,
		"examPaper.showmod": ep_showmod,
		"examPaper.showType": ep_showType,
		"examPaper.epl.id": ep_eplid,
		"examPaper.id": ep_id,
		"x":Math.random()
	}, 
	function (data) {
		$("#ep_baseinfo" ).html(data);
	});
}
function viewepbaseinfo(){
	var ep_id = $("#ep_id").val();
	$.post("exampaper_view.action", {
		"examPaper.id": ep_id,
		"x":Math.random()
	}, 
	function (data) {
		$("#ep_baseinfo" ).html(data);
	});
}
function listepblocks(ep_id){
	if(ep_id<=0){
		alert("未能获到到对应试卷！请确定试卷是否存在！");
		return false;
	}
	$.ajax({async:false,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
			type:"post",   
		    url:"exampaperblock_list.action",data:{
		"examPaper.id":ep_id,
		"x":Math.random()
	}, 
	success:function (data) {
		$("#ep_block_list" ).html(data);
	}});
}
function listepblocks_details(ep_id){
	if(ep_id<=0){
		alert("未能获到到对应试卷！请确定试卷是否存在！");
		return false;
	}
	$.post("exampaperblock_details_list.action", {
		"examPaper.id":ep_id,
		"x":Math.random()
	}, 
	function (data) {
		$("#ep_block_list" ).html(data);
	});
}
function listexampaperblockquestions_details(epb_id){
	if(epb_id<=0){
		alert("获取大题试题发生错误！请确定大题是否存在！");
		return false;
	}
	$.post("exampaperblockquestion_details_list.action", {
		"epBlock.id":epb_id,
		"x":Math.random()
	}, 
	function (data) {
		$("#exampaperblockquestion_list_"+epb_id ).html(data);
	});
	$("#exampaperblockquestion_list_"+epb_id  ).css("display","block") ;
}
function listexampaperblockquestions(epb_id){
	if($("#exampaperblockquestion_list_"+epb_id).css("display")=="block"){
		$("#exampaperblockquestion_list_"+epb_id).css("display","none");
		return false;
	}
	if(epb_id<=0){
		alert("获取大题试题发生错误！请确定大题是否存在！");
		return false;
	}
	$.post("exampaperblockquestion_list.action", {
		"epBlock.id":epb_id,
		"x":Math.random()
	}, 
	function (data) {
		$("#exampaperblockquestion_list_"+epb_id ).html(data);
	});
	$("#exampaperblockquestion_list_"+epb_id  ).css("display","block") ;
	//$("#exampaperblockquestion_list_"+epb_id  ).css("display","") ;
	//alert("1");
	//$("#disquestion_"+epb_id).html("ccddee");
	//document.getElementById("disquestion_"+epb_id).onclick=showorhidden("exampaperblockquestion_list_"+epb_id+"");
	//$("#disquestion_"+epb_id).onclick=showorhidden("exampaperblockquestion_list_"+epb_id+"");
	//$("#ccc_"+epb_id).css("display","block") ;
	//$("#ddd_"+epb_id).css("display","none") ;
}
function nihao(cc){
	alert("nihao"+cc);
}

function deleteexampaperblockquestions(epb_id,qid){
	if(epb_id<=0){
		alert("大题删除试题发生错误！请确定大题是否存在！");
		return false;
	}
	if(!window.confirm("确定删除该试题？")){
		return ;
	}
	$.ajax(
		{	async:false,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
			type:"post",   
		    url:"exampaperblockquestion_delete.action",   
		    data:{"epBlock.id":epb_id,
			"question.id":qid,
			"x":Math.random()},   
			success:function(data){
				$("#exampaperblockquestion_list_"+epb_id ).html(data); 
				$("#exampaperblockquestion_list_"+epb_id  ).css("display","block") ;
			}});
		 
	/*$.post("exampaperblockquestion_delete.action", {
		"epBlock.id":epb_id,
		"question.id":qid,
		"x":Math.random()
	}, 
	function (data) {
		$("#exampaperblockquestion_list_"+epb_id ).html(data);
	});
	$("#exampaperblockquestion_list_"+epb_id  ).css("display","block") ;*/
}
function upexampaperblockquestions(epb_id,q_sortid){
	if(epb_id<=0){
		alert("大题试题移动发生错误！请确定大题是否存在！");
		return false;
	}
	$.post("exampaperblockquestion_upsort.action", {
		"epBlock.id":epb_id,
		"question.sortid":q_sortid,
		"x":Math.random()
	}, 
	function (data) {
		$("#exampaperblockquestion_list_"+epb_id ).html(data);
	});
	$("#exampaperblockquestion_list_"+epb_id  ).css("display","block") ;
}
function downexampaperblockquestions(epb_id,q_sortid){
	if(epb_id<=0){
		alert("大题删除试题发生错误！请确定大题是否存在！");
		return false;
	}
	$.post("exampaperblockquestion_downsort.action", {
		"epBlock.id":epb_id,
		"question.sortid":q_sortid,
		"x":Math.random()
	}, 
	function (data) {
		$("#exampaperblockquestion_list_"+epb_id ).html(data);
	});
	$("#exampaperblockquestion_list_"+epb_id  ).css("display","block") ; 
}
var now_blockid = 0 ;
function closeexampaperblockquestion_list_i(){
	if($("#exampaperblockquestion_list_"+now_blockid).length >0){
		$("#exampaperblockquestion_list_"+now_blockid).html("");
	}
}
function addexampaperblockquestions(epb_id){
	if(epb_id<=0){
		alert("大题添加试题发生错误！请确定大题是否存在！");
		return false;
	}
	closeexampaperblockquestion_list_i();
	now_blockid =epb_id;
	$.post("exampaperblockquestion_addSearchInit.action", {
		"epBlock.id":epb_id,
		"x":Math.random()
	}, 
	function (data) {
		$("#exampaperblockquestion_list_"+epb_id ).html(data);
	});
	$("#exampaperblockquestion_list_"+epb_id  ).css("display","block") ; 
}
//编辑题的设置
function updateexampaperblockquestions(epb_id,epr_id){
	if(epb_id<=0){
		alert("大题编辑试题发生错误！请确定大题是否存在！");
		return false;
	}
	$.post("exampaperblockquestion_alterRandomInit.action", {
		"epBlock.id":epb_id,
		"epRandom.id":epr_id,
		"x":Math.random()
	}, 
	function (data) {
		$("#exampaperblockquestion_list_"+epb_id ).html(data);
	});
	$("#exampaperblockquestion_list_"+epb_id  ).css("display","block") ; 
}

function addexampaperblockquestionslist(epb_id){
	if(epb_id<=0){
		alert("大题添加试题发生错误！请确定大题是否存在！");
		return false;
	}
	$.post("exampaperblockquestion_addSearchList.action", {
		"epBlock.id":epb_id,
		"question.title":$("#epb_q_title").val(),
		"question.qtype":$("#epb_q_type").val(),
		"question.qlib.id":$("#epb_q_lib").val(),
		"epBlock.fwsize":$("#epb_q_fwsize").val(),
		"sublibs":$(":checkbox[name='sublibs'][checked]").val() ,
		"pN":$("#epb_q_pn").val(),
		"pS":$("#epb_q_ps").val(),
		"x":Math.random()
	}, 
	function (data) {
		$("#exampaperblockquestion_list_"+epb_id ).html(data);
	});
	$("#exampaperblockquestion_list_"+epb_id  ).css("display","block") ;
}
				
function addexampaperblockquestions_random(epb_id){
	if(epb_id<=0){
		alert("大题添加试题发生错误！请确定大题是否存在！");
		return false;
	}
	$.post("exampaperblockquestion_addRandomInit.action", {
		"epBlock.id":epb_id,
		"epBlock.type":$("#epb_q_type").val(),
		"question.qlib.id":$("#epb_q_lib").val(),
		"sub_operate":$(":checkbox[name='sub_operate'][checked]").val() ,
		"sublibs":$(":checkbox[name='sublibs'][checked]").val() ,
		"epBlock.fwsize":$("#epb_q_fwsize").val(),
		"pN":$("#epb_q_pn").val(),
		"pS":$("#epb_q_ps").val(),
		"x":Math.random()
	}, 
	function (data) {
		$("#exampaperblockquestion_list_"+epb_id ).html(data);
	});
	$("#exampaperblockquestion_list_"+epb_id  ).css("display","block") ;
}
/*
function addexampaperblockquestions_randomadd(epb_id){
	if(epb_id<=0){
		alert("大题添加试题发生错误！请确定大题是否存在！");
		return false;
	}
	return false;
	$.post("exampaperblockquestion_addRandom.action", {
		"epBlock.id":epb_id,
		"epBlock.type":$("#epb_q_type").val(),
		"question.qlib.id":$("#epb_q_lib").val(),
		"epRandom.qlib.id":$("#epr_qlibid").val(),
		"epRandom.epBlock.id":$("#epr_epbid").val(),
		"epRandom.suboperate":$("#epr_sub_operate").val(),
		"sub_operate":$("#sub_operate").val() ,
		"epRandom.qlevel1":$("#epr_qlevel1").val(),
		"epRandom.qlevel2":$("#epr_qlevel2").val(),
		"epRandom.qlevel3":$("#epr_qlevel3").val(),
		"epRandom.qlevel4":$("#epr_qlevel4").val(),
		"epRandom.qlevel5":$("#epr_qlevel5").val(),
		"epRandom.qlevel":$("#epr_qlevel").val(),
		"x":Math.random()
	}, 
	function (data) {
		//$("#exampaperblockquestion_list_"+epb_id ).html(data);
	});
	listexampaperblockquestions(epb_id);
}
*/
function addexampaperblockquestions_randomdelete(epb_id,eprid){
	if(epb_id<=0){
		alert("大题添加试题发生错误！请确定大题是否存在！");
		return false;
	}
	if(!window.confirm("确定删除？")) return false;
	
	$.post("exampaperblockquestion_deleteRandom.action", {
		"epBlock.id":epb_id,
		"epRandom.id":eprid,
		"x":Math.random()
	}, 
	function (data) {
	//	$("#exampaperblockquestion_list_"+epb_id ).html(data);
	});
	listexampaperblockquestions(epb_id);
}
function change_qlib(obj,i,bid__){
	if(0==obj.options[i].haschild)
		addexampaperblockquestions_random(bid__);
}
function click_sub(bid__){
	addexampaperblockquestions_random(bid__);
}
function addexampaperblockquestionsadd(epb_id,qid){
	
	if(epb_id<=0){
		alert("大题添加试题发生错误！请确定大题是否存在！");
		return false;
	}
	if(qid<=0) 
	{
		alert("大题添加试题发生错误！请确定试题是否存在！");
		return false;
	}
	$.ajax(
		{	async:false,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
			type:"post",   
		    url:"exampaperblockquestion_add.action",   
		    data:{"epBlock.id":epb_id,
			"question.title":$("#epb_q_title").val(),
			"question.qtype":$("#epb_q_type").val(),
			"question.qlib.id":$("#epb_q_lib").val(),
			"sublibs":$(":checkbox[name='sublibs'][checked]").val() ,
			"question.id":qid,
			"pN":$("#epb_q_pn").val(),
			"pS":$("#epb_q_ps").val(),
			"x":Math.random()},   
			success:function(data){ }});
		 
	/*$.post("exampaperblockquestion_add.action", {
		"epBlock.id":epb_id,
		"question.title":$("#epb_q_title").val(),
		"question.qtype":$("#epb_q_type").val(),
		"question.qlib.id":$("#epb_q_lib").val(),
		"sublibs":$(":checkbox[name='sublibs'][checked]").val() ,
		"question.id":qid,
		"pN":$("#epb_q_pn").val(),
		"pS":$("#epb_q_ps").val(),
		"x":Math.random()
	}, 
	function (data) {
		//$("#exampaperblockquestion_list_"+epb_id ).html(data);
	});
	//$("#exampaperblockquestion_list_"+epb_id  ).css("display","block") ;
	*/
}
//添加大题随机出题规则
function addexampaperblockquestions_randomadd(epb_id){
	if(epb_id<=0){
		alert("大题添加试题发生错误！请确定大题是否存在！");
		return false;
	}
	var randomlist=$("#randomlist").find("tr");
	//alert(randomlist.length);
	var params = "";
	var kk=0;
	var questionSum= 0;
	for(var i=1;i<randomlist.length-1;i++){
		var tr = randomlist[i];
		var title = $($(tr).find("td")[0]).html();
		var ts=/^[\d]{1,}$/;
		var xxxx = 0 ;
		var parasi= "&epRandoms["+kk+"].qlib.id="+$.trim($($($(tr).find("td")[7]).find("input")[0]).val());
		for(var j=1;j<=6;j++){
			//alert($.trim($($($(tr).find("td")[j]).find("input")[0]).val()))
			if( !ts.test($.trim($($($(tr).find("td")[j]).find("input")[0]).val()))){
				if(j!=6)
					alert("【"+title+"】题库的难度【"+j+"】级试题中有非数字！");
				else
				  	alert("【"+title+"】题库的难度"+"不限级试题中有非数字！");
				return false;
			}else{
				if(parseInt($.trim($($($(tr).find("td")[j]).find("input")[0]).val()),10)>parseInt($.trim($($($(tr).find("td")[j]).find("input")[0]).attr("max-v")),10))
				{
					if(j!=6)
						alert("【"+title+"】题库的难度【"+j+"】级试题超过题库中实际总数！");
					else
					  	alert("【"+title+"】题库的难度"+"不限级试题超过题库中实际总数！");
					return false;
				}
			}
			if(parseInt($.trim($($($(tr).find("td")[j]).find("input")[0]).val()),10)!=0){
				xxxx=1;
			}
			var lj="";
			if(j!=6)
				lj=j+"";
			else
				lj="";
			parasi+="&epRandoms["+kk+"].qlevel"+lj+"="+parseInt($.trim($($($(tr).find("td")[j]).find("input")[0]).val()),10);
			questionSum+=parseInt($.trim($($($(tr).find("td")[j]).find("input")[0]).val()),10);
		}
		if(xxxx==1){
			params +=parasi;
			kk++;
		}
	}
	var questionCount=$("#epbquestionamount").val();//题目的设置量
	var realqCount=$("#epbrealqamount").val();//题目的实际数量
	//alert(questionSum+"="+realqCount+"="+questionCount)
	if(questionSum=="" || questionSum==0){
		alert("输入的题量不能少于1，请正确输入！");
		return false;
	}
	if(parseInt(questionSum)+parseInt(realqCount)>parseInt(questionCount)){
		alert("实际题量大于设置的题量！");
		return false;
	}
	params ="epBlock.id="+epb_id+"&epBlock.type="+$("#epb_q_type").val()+
		"&question.qlib.id="+$("#epb_q_lib").val()+
		"&epRandom.qlib.id="+$("#epr_qlibid").val()+
		"&epRandom.epBlock.id="+$("#epr_epbid").val()+
		"&epRandom.suboperate="+$("#epr_sub_operate").val()+
		"&sub_operate="+$("#sub_operate").val()+"&sublibs=1"+params;
	$.ajax({async:true,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
		type:"post",
		url:"exampaperblockquestion_addRandom.action",data:params,cache:false,success:function (data) {
		listexampaperblockquestions(epb_id);
	}});
	
}
function addexampaperblockquestions_randomadd2(epb_id){
	if(epb_id<=0){
		alert("大题添加试题发生错误！请确定大题是否存在！");
		return false;
	}
	//请求前作表单验证
	var epQlevel1=$("#epr_qlevel1").attr("max-v");
	var epQlevel2=$("#epr_qlevel2").attr("max-v");
	var epQlevel3= $("#epr_qlevel3").attr("max-v");
	var epQlevel4=$("#epr_qlevel4").attr("max-v") ;
	var epQlevel5=$("#epr_qlevel5").attr("max-v") ;
	var epQlevel= $("#epr_qlevel").attr("max-v");
	var epr_qlevel1=$("#epr_qlevel1").val();
	var epr_qlevel2=$("#epr_qlevel2").val();
	var epr_qlevel3=$("#epr_qlevel3").val();
	var epr_qlevel4=$("#epr_qlevel4").val();
	var epr_qlevel5=$("#epr_qlevel5").val();
	var epr_qlevel=$("#epr_qlevel").val();
	var ts=/^[\d]{0,}$/;
	for(var i=1;i<=6;i++){  
		if(i!=6 && !ts.test($.trim(eval("$('#epr_qlevel"+i+"')\.val()")))){
			alert(i+"级试题中有非数字！");
			return false;
		}else{
			if(i==6 && !ts.test($.trim($('#epr_qlevel').val()))){
				alert("不限级试题中有非数字！");
				return false;
			}
		}
	}
	for(var i=1;i<=6;i++){
		if(i!=6 && parseInt(eval("epr_qlevel"+i))> parseInt(eval("epQlevel"+i))&&eval("epr_qlevel"+i)!=""){
			alert(i+"级试题超过试题总数！");
			return false;
		}else{
			if(i==6 &&  parseInt(epr_qlevel)> parseInt(epQlevel)&&epr_qlevel!=""){
				alert("不限级试题超过试题总数！");
				return false;
			}
		}
	}
	var questionCount=$("#epbquestionamount").val();//题目的设置量
	var realqCount=$("#epbrealqamount").val();//题目的实际数量
	var questionSum=parseInt(epr_qlevel1)+parseInt(epr_qlevel2)+parseInt(epr_qlevel3)+parseInt(epr_qlevel4)+parseInt(epr_qlevel5)+parseInt(epr_qlevel);
	if(questionSum=="" || questionSum==0){
		alert("输入的题量不能少于1，请正确输入！");
		return false;
	}
	if(parseInt(questionSum)+parseInt(realqCount)>parseInt(questionCount)){
		alert("实际题量大于设置的题量！");
		return false;
	}
	$.post("exampaperblockquestion_addRandom.action", {
		"epBlock.id":epb_id,
		"epBlock.type":$("#epb_q_type").val(),
		"question.qlib.id":$("#epb_q_lib").val(),
		"epRandom.qlib.id":$("#epr_qlibid").val(),
		"epRandom.epBlock.id":$("#epr_epbid").val(),
		"epRandom.suboperate":$("#epr_sub_operate").val(),
		"sub_operate":$("#sub_operate").val() ,
		"epRandom.qlevel1":$("#epr_qlevel1").val(),
		"epRandom.qlevel2":$("#epr_qlevel2").val(),
		"epRandom.qlevel3":$("#epr_qlevel3").val(),
		"epRandom.qlevel4":$("#epr_qlevel4").val(),
		"epRandom.qlevel5":$("#epr_qlevel5").val(),
		"epRandom.qlevel":$("#epr_qlevel").val(),
		"x":Math.random()
	}, 
	function (data) {
		//$("#exampaperblockquestion_list_"+epb_id ).html(data);
		listexampaperblockquestions(epb_id);
	});
}

function addexampaperblockquestionsadds(epb_id){
	
	if(epb_id<=0){
		alert("大题添加试题发生错误！请确定大题是否存在！");
		return false;
	}
	if(!window.confirm("确定添加这些试题？")){
		return ;
	}
	var qids =document.getElementsByName("questions.id");
	for(var i = 0 ; i<qids.length;i++){
		if(qids[i].checked=='checked'||qids[i].checked==true){
			addexampaperblockquestionsadd(epb_id,qids[i].value)
			//if(i==qids.length-1){
				
			//}
		}
	}
	addexampaperblockquestionslist(epb_id);
}
function alterexampaperblockquestionsinit(epb_id,qid){
	if(epb_id<=0){
		alert("大题添加试题发生错误！请确定大题是否存在！");
		return false;
	}
	if(qid<=0) 
	{
		alert("大题添加试题发生错误！请确定试题是否存在！");
		return false;
	} 
	$.post("exampaperblockquestion_rulealterinit.action", {
		"epBlock.id":epb_id,
		"question.id":qid,
		"x":Math.random()
	}, 
	function (data) {
		$("#dia_content" ).html(data);
	});
	//$("#dia_" ).css("display","block") ;
	showdia_("");
}
function showdia_(title){
	//alert($("body").height()/2-$("#dia_").height()/2);
	//alert($("body").scrollTop());
	$("#dia_" ).css("top",$("body").height()/2-$("#dia_").height()/2+$("body").scrollTop()) ;
	$("#dia_" ).css("left",$("body").width()/2-$("#dia_").width()/2) ;
	$("#dia_" ).css("display","block") ;
}
function setDaziMax(){
	var maxdazinum = 0;
	for(var jj=1;jj<dazi;jj++){
		var tdazinum = parseInt($("#mf_dazirules"+jj).val ())*parseInt($("#rules3").val());
		if(maxdazinum<tdazinum){
			maxdazinum = tdazinum;
		}
	}
	$("#dazi_fwsize").attr("value",maxdazinum);
	$("#dazi_fwsize").attr("d-value",maxdazinum);
}
function setDaziMax_mf(){
	var maxdazinum = 0;
	for(var jj=1;jj<dazi;jj++){
		var tdazinum = parseInt($("#mf_dazirules"+jj).val ())*parseInt($("#rules3").val());
		if(maxdazinum<tdazinum){
			maxdazinum = tdazinum;
		}
	}
	$("#dazi_fwsize").attr("d-value",maxdazinum);
}
function alterexampaperblockquestions (epb_id,qid,qtype){
	if(epb_id<=0){
		alert("大题添加试题发生错误！请确定大题是否存在！");
		return false;
	}
	if(qid<=0) 
	{
		alert("大题添加试题发生错误！请确定试题是否存在！");
		return false;
	} 
	var rules="";
	if(qtype==9)
	rules = $("#rules1").val()+"-=SpRule-"+$("#rules2").val()+
		"-=SpRule-"+$("#rules3").val()+"-=SpRule-"+$("#rules4").val()+
		"-=SpRule-"+$("#rules5").val()+"-=SpRule-"+$("#rules6").val()+"-=SpRule-" ;
	//if(qtype==10)
	//rules = $("#rules1").val()+"-=SpRule-"+$("#rules2").val() ;
	if(qtype==8){
		if(dazi<=1)
		{
			alert("该打字题未设置年龄段速度，请设置！");
			return false;
		}
		if(!checkdazirule()){
			return false;
		}
		rules = $("#rules1").val()+"-=SpRule-"+$("#rules2").val() +"-=SpRule-"+$("#rules3").val()+"-=SpRule-" ;
		for(var jj=1;jj<dazi;jj++){
			rules+=$("#b_dazirules"+jj).val ()+":"+$("#e_dazirules"+jj).val ()+
			":"+$("#jg_dazirules"+jj).val ()+":"+$("#yx_dazirules"+jj).val ()+":"+$("#mf_dazirules"+jj).val ()+":"
		}
		setDaziMax();
		if(parseInt($("#dazi_fwsize").val())>=parseInt($("#rules1").val())){
			alert("满分速度*规定时间("+$("#dazi_fwsize").val()+")不可以大于或等于范文长度("+$("#rules1").val()+")，不然学员不可拿满分");
			return false;
		}
	}
	$.ajax(
		{async:false,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
		type:"post",   
		url:"exampaperblockquestion_rulealter.action", data:{
		"epBlock.id":epb_id,
		"question.id":qid,
		"question.rulestring":rules,
		"x":Math.random()},
		success:function (data) {
		dia_close();
		listexampaperblockquestions(epb_id);
	}});
}
function exampaperblock_rulealterinit(epb_id ){
	if(epb_id<=0){
		alert("大题添加试题发生错误！请确定大题是否存在！");
		return false;
	}
	$.post("exampaperblock_rulealterinit.action", {
		"epBlock.id":epb_id,
		"x":Math.random()
	}, 
	function (data) {
		$("#dia_content" ).html(data);
	});
	//$("#dia_" ).css("display","block") ;
	showdia_("");
}
function exampaperblock_rulealter (epb_id,qtype){
	if(epb_id<=0){
		alert("大题添加试题发生错误！请确定大题是否存在！");
		return false;
	}
	var rules="";
	if(qtype==12)
	rules = $("#rules1").val()+"-=SpRule-"+$("#rules2").val()+
	"-=SpRule-"+$("#rules3").val();
	//if(qtype==10)
	//rules = $("#rules1").val()+"-=SpRule-"+$("#rules2").val() ;
	if(qtype==8){
		if(dazi<=1)
		{
			alert("该打字题未设置年龄段速度，请设置！");
			return false;
		}
		if(!checkdazirule()){
			return false;
		}
		rules = $("#rules1").val()+"-=SpRule-"+$("#rules2").val() +"-=SpRule-"+$("#rules3").val()+"-=SpRule-" ;
		for(var jj=1;jj<dazi;jj++){
			rules+=$("#b_dazirules"+jj).val ()+":"+$("#e_dazirules"+jj).val ()+
			":"+$("#jg_dazirules"+jj).val ()+":"+$("#yx_dazirules"+jj).val ()+":"+$("#mf_dazirules"+jj).val ()+":"
		}
		if(parseInt($("#rules1").val())<parseFloat($("#rules1").attr("d-value"))){
			alert("范文最少字数不可少于最大满分速度*规定时间，请重新设定！");
			return false;
		}
		//if(!window.confirm("确定不需要修改范文最少字数？")){
		//	return false;
		//}
	}
	if( qtype==9){
			rules = $("#rules1").val()+"-=SpRule-"+$("#rules2").val()+
			"-=SpRule-"+$("#rules3").val()+"-=SpRule-"+$("#rules4").val()+"-=SpRule-"+$("#rules5").val()+"-=SpRule-"+$("#rules6").val() ;
	}
	$.post("exampaperblock_rulealter.action", {
		"epBlock.id":epb_id,
		"epBlock.rulestring":rules,
		"x":Math.random()
	}, 
	function (data) {
		alert("修改成功");
		dia_close();
		//$("#dia_content" ).html(data);
	});
	//$("#dia_" ).css("display","block") ;
}
var dazi = 1 ;
function daziruleadd(){
	var obj = document.createElement("div");
	obj.id = "dazi_rule"+dazi;
	obj.innerHTML="年龄段"+dazi+"：<input type=\"text\" style=\"width:30px;\" id=\"b_dazirules"+dazi+"\" value=\"0\"/> 到<input type=\"text\" style=\"width:30px;\" id=\"e_dazirules"+dazi+"\" value=\"0\"/>"+
	" 及格速度：<input type=\"text\" style=\"width:30px;\" id=\"jg_dazirules"+dazi+"\" value=\"0\"/> 优秀速度：<input type=\"text\" style=\"width:30px;\" id=\"yx_dazirules"+dazi+"\" value=\"0\"/>"+
	" 满分速度：<input type=\"text\" style=\"width:30px;\" onblur=\"setDaziMax()\" id=\"mf_dazirules"+dazi+"\" value=\"0\"/>";
	///alert(obj.innerHTML);
	$("#dazi_rule").append(obj);
	dazi++;
}
function daziruledelete(){
	if(dazi<=1)
		{alert("没得删了");return false;}
	if(!window.confirm("确定删除？"))
		return false;
	dazi--;
	$("#dazi_rule"+dazi).remove();
}
function checkdazirule(){
	//alert(parseFloat($("#rules3").val()));
	var ts=/^[\d]{0,}$/;
	//alert($.trim($("#rules3").val()))
	//alert(ts.test($.trim($("#rules3").val()))); 
	if($.trim($("#rules3").val())==''||!ts.test($.trim($("#rules3").val()))||parseFloat($("#rules3").val())<=0){
		alert("打字时间必须大于0");
		return false;
	}
	var bjj = parseFloat($("#b_dazirules"+jj).val());
	for(var jj=1;jj<dazi;jj++){
		var bjj = parseFloat($("#b_dazirules"+jj).val());
		var ejj = parseFloat($("#e_dazirules"+jj).val());
		if(bjj>=ejj){
			alert("年龄段"+jj+"的[开始年龄]必须小于[结束年龄]");
			return false;
		}
		var jg = parseFloat($("#jg_dazirules"+jj).val ());
		var yx = parseFloat($("#yx_dazirules"+jj).val ());
		var mf = parseFloat($("#mf_dazirules"+jj).val ());
		if(jg<=0){
			alert("年龄段"+jj+"的[及格速度]必须大于 0");
			return false;
		}
		if(jg>=yx){
			alert("年龄段"+jj+"的[及格速度]必须小于[优秀速度]");
			return false;
		}
		if(yx>=mf){
			alert("年龄段"+jj+"的[优秀速度]必须小于[满分速度]");
			return false;
		}
		if(jj>1){
			//var bjj_1 = parseFloat($("#b_dazirules"+(jj-1)).val());
			var ejj_1 = parseFloat($("#e_dazirules"+(jj-1)).val());
			if(bjj!=ejj_1+1){
				alert("年龄段"+jj+"的[开始年龄]必须是：年龄段"+(jj-1)+"[结束年龄]的＋1");
				return false;
			}
		}
	}
	if(dazi>1){
		var _bjj=parseFloat($("#b_dazirules1").val());
		if(_bjj!=0){
			alert("第一个年龄段的[结束年龄]必须等于0岁");
			return false;
		}
		var ejj_=parseFloat($("#e_dazirules"+(dazi-1)).val());
		if(ejj_<90){
			alert("最后一个年龄段的[结束年龄]必须大于90岁");
			return false;
		}
	}
	return true;
}
function addepblockinit(){
	dazi=1;
	var ep_id = $("#ep_id").val();
	$.post("exampaperblock_addInit.action", {
		"examPaper.id": ep_id,
		"x":Math.random()
	}, 
	function (data) {
		$("#dia_content" ).html(data);
	});
	//$("#dia_" ).css("display","block") ;
	showdia_("");
}

function epblockshowrule(){
	var epbtype = $("#epbtype").val();
	var eprandom =$(":radio[name='epBlock.random'][checked]").val();
	var xxx="";
	if(epbtype==12){
		$("#bl_eachscorequestionamount").html("<span class=\"neededitem\">*</span>选做题数");
		xxx=" <input type=\"hidden\" size=\"4\" id=\"rules1\" value=\"0\"/>"
		//"共 有：<input type=\"text\" size=\"4\" id=\"rules1\" value=\"0\"/>题"+
		//"			<br />"+
		//"选 做：<input type=\"text\" size=\"4\" id=\"rules2\" value=\"0\" />题"+
		"<input type=\"hidden\" size=\"4\" id=\"rules2\" value=\"0\" />"+
		//"			<br />"+
		//"每 题：<input type=\"text\" size=\"4\" id=\"rules3\" value=\"0\" />分";
		"<input type=\"hidden\" size=\"4\" id=\"rules3\" value=\"0\" />";
		$("#rule_td").html(xxx);
		$("#rule_tr").css("display","hidden");
		return ;
	}else{
		$("#bl_eachscorequestionamount").html("<span class=\"neededitem\">*</span>试题总数");
	}
	if(epbtype==9&&eprandom==1){
		xxx="发 给：		<input type=\"text\" size=\"4\" id=\"rules1\" value=\"0\"/>分"+
			"		<br />"+
			"		抄 送："+
			"		<input type=\"text\" size=\"4\" id=\"rules2\" value=\"0\" />分"+
			"		<br />"+
			"		密 送："+
			"		<input type=\"text\" size=\"4\" id=\"rules3\" value=\"0\" />分"+
			"		<br />"+
			"		主 题："+
			"		<input type=\"text\" size=\"4\" id=\"rules4\" value=\"0\" />分"+
			"		<br />"+
			"		附 件："+
			"		<input type=\"text\" size=\"4\" id=\"rules5\" value=\"0\" />分"+
			"		<br />"+
			"		正 文："+
			"		<input type=\"text\" size=\"4\" id=\"rules6\" value=\"0\" />分";
		$("#rule_td").html(xxx);
		$("#rule_tr").css("display","block");
		return ;
	}
	/*if(epbtype==10&&eprandom==1){
		xxx="搜索的结果分："+
		"	<input type=\"text\" size=\"4\" value=\"\" id=\"rules1\" />分";
		$("#rule_td").html(xxx);
		$("#rule_tr").css("display","block");
		return ;
	}*/
	if(epbtype==8&&eprandom==1)
		$("#rule_td_dz").css("display","block");
	else
		$("#rule_td_dz").css("display","none");
	if(epbtype==8&&eprandom==1){
		xxx=//"	范文最少字数：<input type=\"text\" size=\"4\" id=\"rules1\""+
			//"value=\"0\" />分"+
			//"		<br />"+
			//"		准确分："+
			"		<input type=\"hidden\" size=\"4\" id=\"rules1\""+
			"		value=\"1\" />"+
			"		<input type=\"hidden\" size=\"4\" id=\"rules2\""+
			"		value=\"1\" />"+
			/*
			"	速度分：<input type=\"text\" size=\"4\" id=\"rules1\""+
			"value=\"\" />分"+
			"		<br />"+
			"		准确分："+
			"		<input type=\"text\" size=\"4\" id=\"rules2\""+
			"		value=\"\" />分"+*/
			"		<br />"+
			"		时&nbsp;&nbsp;&nbsp;&nbsp;长："+
			"		<input type=\"text\" size=\"4\" id=\"rules3\""+
			"		value=\"10\" />分钟"+
			"		<br />"+
			"		评分策略： <a class='textbg4' onclick=\"daziruleadd();return false;\" href=\"#\" >添加</a>  <a class='textbg4' href=\"#\" onclick=\"daziruledelete();return false;\">删除</a>"+
			"	<div id=\"dazi_rule\">"+
			"	</div>";
		$("#rule_td").html(xxx);
		$("#rule_tr").css("display","block");
		return ;
	}
	$("#rule_td").html(xxx);
	$("#rule_tr").css("display","none");
	return ;
}
function alterepblockinit(epbid){
	if(epbid<=0){
		alert("修改大题发生错误！请确定大题是否存在！");
		return false;
	}
	var ep_id = $("#ep_id").val();
	$.post("exampaperblock_alterInit.action", {
		"epBlock.id": epbid,
		"x":Math.random()
	}, 
	function (data) {
		console.log(data);
		$("#dia_content" ).html(data);
	});
	//$("#dia_" ).css("display","block") ;
	showdia_("");
}

function alterepblock(){
	setDaziMax_mf();
	var ep_id = $("#ep_id").val();
	var epbtitle = $("#epbtitle").val();
	var epbeachscore = $("#epbeachscore").val()
	var questionamount = $("#questionamount").val()
	var epbdesc = $("#epbdesc").val()
	var epbtype = $("#epbtype").val()
	var eprandom = $("#epbrandom").val();//$(":radio[name='epBlock.random'][checked]").val();
	var epblockid =$("#epblockid").val();
	var epb_realqamount=$("#epb_realqamount").val();
	var answerTimet = $("#answerTimet").val();
	var secondScore = $("#secondScore").val();
	var cosPlayRemark = $("#cosPlayRemark").val();
	var sortid = $("#sortid").val();
	if(epblockid<=0){
		alert("修改大题发生错误！请确定大题是否存在！");
		return false;
	}
	if(epbtitle==''){
		alert("请填写大题名称 ");
		$("#epbtitle").focus();
		return false;
	}
	if(epbeachscore==''){
		alert("请填写每题分数 ");
		$("#epbeachscore").focus();
		return false;
	}
	var ts=/^\d+$|^\d+\.?\d+$/;
	if(!ts.test($.trim(epbeachscore))){
		alert("每题分数不能为非数字型 ");
		$("#epbeachscore").focus();
		return false;
	}
	var checkepbeachscore=/^\d+(\.\d{1})?(\.\d{2})?(\.\d{3})?$/;
	if(!checkepbeachscore.test(epbeachscore)){
		alert("每题分数不能超过三位小数！");
		$("#epbeachscore").focus();
		return false;
	}
	if(parseFloat(epbeachscore)==0){
		alert("每题分数必须大于0");
		$("#epbeachscore").focus();
		return false;
	}
	if(questionamount==''){
		alert("请填写	试题总数 ");
		$("#questionamount").focus();
		return false;
	}
	var checkquestionamount =/^\d+$/ ;
	if(!checkquestionamount.test(questionamount)){
		alert("试题总数必须为整数，且必须大于0");
		$("#questionamount").focus();
		return false;
	}
	if(parseInt(questionamount)==0){
		alert("试题总数必须大于0");
		$("#questionamount").focus();
		return false;
	}
	if(!ts.test($.trim(questionamount))){
		alert("试题总数不能为非数字型 ");
		$("#questionamount").focus();
		return false;
	}
	if(parseInt(questionamount)<parseInt(epb_realqamount)){
		alert("设置的题量小于实际题量，请调整实际题量（增删小题或修改出题规则）！ ");
		$("#questionamount").focus();
		return false;
	}
	var rulestring= "";
	if( epbtype==12){
		//rulestring = $("#rules1").val()+"-=SpRule-"+$("#rules2").val()+
		//	"-=SpRule-"+$("#rules3").val();
		rulestring = $("#rules1").val()+"-=SpRule-"+questionamount+
			"-=SpRule-"+epbeachscore;
	}
	if( epbtype==9&&eprandom==1 ){
			rulestring = $("#rules1").val()+"-=SpRule-"+$("#rules2").val()+
			"-=SpRule-"+$("#rules3").val()+"-=SpRule-"+$("#rules4").val()+"-=SpRule-"+$("#rules5").val()+"-=SpRule-"+$("#rules6").val() ;
	}
	if(epbtype==8&&eprandom==1){
		rulestring = $("#rules1").val()+"-=SpRule-"+$("#rules2").val() +"-=SpRule-"+$("#rules3").val()+"-=SpRule-" ;
		if(dazi<=1)
		{
			alert("该打字题未设置年龄段速度，请设置！");
			return false;
		}
		if(!checkdazirule()){
			return false;
		}
		for(var jj=1;jj<dazi;jj++){
			rulestring+=$("#b_dazirules"+jj).val ()+":"+$("#e_dazirules"+jj).val ()+
			":"+$("#jg_dazirules"+jj).val ()+":"+$("#yx_dazirules"+jj).val ()+":"+$("#mf_dazirules"+jj).val ()+":";
		}
		if(parseInt($("#dazi_fwsize").val())<parseInt($("#dazi_fwsize").attr("d-value"))){
			alert("范文最少字数不可少于最大满分速度*规定时间，请重新设定！");
			return false;
		}
		if(!window.confirm("确定不需要修改范文最少字数？")){
			return false;
		}
		fwsize = $("#rule1").val();
	}
	if(!window.confirm("确定提交？")) return false;
	$.post("exampaperblock_alter.action", {
		"examPaper.id": ep_id,
		"epBlock.title": epbtitle,
		"epBlock.eachscore": epbeachscore,
		"epBlock.questionamount": questionamount,
		"epBlock.description": epbdesc,
		"epBlock.type": epbtype,
		"epBlock.random": eprandom,
		"epBlock.id": epblockid,
		"epBlock.rulestring": rulestring,
		"epBlock.fwsize":$("#dazi_fwsize").val(),
		"epBlock.answerTime":answerTimet,
		"epBlock.secondScore":secondScore,
		"epBlock.cosPlayRemark":cosPlayRemark,
		"epBlock.sortid":sortid,
		"x":Math.random()
	}, 
	function (data) {
		dia_close();
		listepblocks(ep_id);
	});
}
function deleteepblock(epb_id){
	if(epb_id<=0){
		alert("删除大题发生错误！请确定大题是否存在！");
		return false;
	}
	if(!window.confirm("确定删除？")) return false;
	var ep_id = $("#ep_id").val();
	
	$.ajax(
		{	async:false,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
			type:"post",   
		    url:"exampaperblock_delete.action",   
		    data:{"epBlock.id":epb_id,
			"x":Math.random()},   
			success:function(data){
			}});
	//$.post("exampaperblock_delete.action", {
	//	"epBlock.id": epb_id,
	//	"x":Math.random()
	//}, 
	//function (data) {
		//$("#dia_content" ).html(data);
	//});
	listepblocks(ep_id);
}
function dia_close(){
	$("#dia_" ).css("display","none") ;
}
function select_All(name){
	var cks= document.getElementsByName(name);
	for(var i = 0 ; i < cks.length; i++){
		cks[i].checked= true;
	}
}
function select_Fan(name){
	var cks= document.getElementsByName(name);
	for(var i = 0 ; i < cks.length; i++){
		cks[i].checked= !cks[i].checked;
	}
}
function select_Bux(name){
	var cks= document.getElementsByName(name);
	for(var i = 0 ; i < cks.length; i++){
		cks[i].checked= false;
	}
}