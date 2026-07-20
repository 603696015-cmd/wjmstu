var ns = 0;
function addNorms(){
	var normsi = document.createElement("div");
	normsi.id = "n_"+ns;
	normsi.innerHTML="指标"+(ns+1)+":&nbsp;&nbsp;&nbsp;&nbsp;<input type='text' name='ztroom.norms'/>&nbsp;&nbsp;&nbsp;<input type='button' value='删除' onclick='removeNorm("+ns+")'>";
	document.getElementById("trnorms").appendChild(normsi);
	ns++;
}
function removeNorm(i){
	if(i==0){
	 	alert("至少有一个指标！");
	 	return false;
	 }
	document.getElementById("trnorms").removeChild(document.getElementById("n_"+i));
}