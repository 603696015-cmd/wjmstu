function hide(id){var Div = document.getElementById(id);if(Div){Div.style.display="none"}} //隐藏某容易函数
function show(id){var Div = document.getElementById(id);if(Div){Div.style.display="block"}} //显示某容易函数

function currentFun(index,head,divs) {	//标签切换函数====索引值，标签组的id，轮换层的id头			
	var tab_heads = document.getElementById(head);
	if (tab_heads) {  //判断对象是否存在
		var alis = tab_heads.getElementsByTagName("a");  //经过时移除所有a样式 隐藏所有层
		for (var i = 0; i < alis.length; i++) { alis[i].className = ""; hide(divs + "_" + i); if (i == index) { alis[i].className = "current"; } }
		//显示当前层
		show(divs+"_"+index); 
	}
}

function tabs(head,divs){
	var tab_heads=document.getElementById(head);
	if (tab_heads) {
	   currentFun(0,head,divs);
	   var alis=tab_heads.getElementsByTagName("a");
	   for(var i=0;i<alis.length;i++) {
		alis[i].num=i;		
		alis[i].onclick = function(){currentFun(this.num,head,divs);return false;}
		alis[i].onfocus = function(){currentFun(this.num,head,divs)}
	  }
	}
	}