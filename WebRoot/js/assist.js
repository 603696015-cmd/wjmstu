
var size = 0;
var sizeinit = 0;
function addPstage() {
	size++;
	var objtr = document.createElement("TR");
	objtr.id="ps_"+size;
	var td1 = document.createElement("td");
	td1.innerHTML="阶段" + size ;
	var td2 = document.createElement("td");
	td2.innerHTML="<textarea name='planStages["+(size-sizeinit-1)+"].content' cols='30'></textarea>";
	var td3 = document.createElement("td");
	td3.innerHTML="<input name='planStages["+(size-sizeinit-1)+"].planfinishdate' onclick='setday(this)'/>" ;
	var td4 = document.createElement("td");
	td4.innerHTML="<input size='10' name='planStages["+(size-sizeinit-1)+"].plandays' />" ;
	var td5 = document.createElement("td");
	td5.innerHTML="<a href=\"javascript:deleteTr('ps_"+size+"')\" >删除</a>" ;
	objtr.appendChild(td1);
	objtr.appendChild(td2);
	objtr.appendChild(td3);
	objtr.appendChild(td4);
	objtr.appendChild(td5);
	document.getElementById("stageTable").appendChild(objtr);
}
function deleteTr(id){
	if(window.confirm("确定删除？"))
    document.getElementById("stageTable").removeChild(document.getElementById(id) );
	size--;
}
////调查....
function searchExamPaper(){
	 width=600;
	 height=400;
  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
	 var rv =  window.showModalDialog("assist_survey_epsearchInit.action?"+Math.random(),null,sFeature);
	 if(null==rv||undefined==rv){
	 	alert('您没有选择试卷！');
	 }else{
	 	if(rv[0]<=0||undefined==rv[0])  {
	 		alert('您没有选择试卷！');
	 		return ;
	 	}
	 	document.getElementById("eptitle").innerHTML=rv[1];
	 	document.getElementById("epid").value=rv[0];
	 }
}
function searchQuestion(){
	 width=600;
	 height=400;
  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
	 var rv =  window.showModalDialog("assist_poll_qsearchInit.action?"+Math.random(),null,sFeature);
	 if(null==rv){
	 	alert('您没有选择试题！');
	 }else{
	 	if(rv[0]<=0) 
		alert('您没有选择试题！');
	 	document.getElementById("eptitle").innerHTML=rv[1];
	 	document.getElementById("epid").value=rv[0];
	 }
}



