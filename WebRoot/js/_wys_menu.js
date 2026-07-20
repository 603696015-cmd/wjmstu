function Wys_MENU(id,title,content){
	this.id = id;
	this.title = title ;
	this.content = content;
}
Wys_MENU.prototype.show=function(){
	var theOBJ = this;
	var isIE = (document.all) ? true : false;
	var isIE6 = isIE && ([/MSIE (\d)\.0/i.exec(navigator.userAgent)][0][1] == 6);
	var newbox = this.newbox;
	newbox.id = "newbox_"+this.id;
	newbox.className = "wysDialog";
	newbox.style.zIndex = "9999";
	newbox.style.display = "block";
	newbox.style.position = !isIE6 ? "fixed" : "absolute";
	newbox.style.top ="400px";
	newbox.style.right= "2px";
	//newbox.style.marginTop = -newbox.offsetHeight / 2 + "px";
	//newbox.style.marginLeft = -newbox.offsetWidth / 2 + "px";
	newbox.style.border = "solid 1px buttonface";
	newbox.style.backgroundColor = "blue";
	
	var nb_head = document.createElement("h3");
	nb_head.id = "nb_head_"+this.id;
	var nb_headfl = document.createElement("span");
	nb_headfl.id = "nb_headfl_"+this.id;
	nb_headfl.className = "fl";
	nb_headfl.innerHTML = this.title;
	
	var nb_headfr = document.createElement("span");
	nb_headfr.id = "nb_headfr_"+this.id;
	nb_headfr.className = "fr";
	nb_headfr.innerHTML = "[关闭]";
	nb_headfr.href="#";
	nb_headfr.onclick=function(){
		theOBJ.closeDialog();
		return false;
	}
	var nb_content = document.createElement("div");
	nb_content.innerHTML=this.content;
	nb_head .appendChild(nb_headfl);
	nb_head .appendChild(nb_headfr);
	newbox.appendChild(nb_head);
	newbox.appendChild(nb_content);
	
	var layer = this.layer;
	layer.id = "layer_"+this.id;
	layer.style.width = layer.style.height = "100%";
	layer.style.position = !isIE6 ? "fixed" : "absolute";
	layer.style.top = layer.style.left = 0;
	layer.style.backgroundColor = "yellow";
	layer.style.zIndex = "9998";
	document.body.appendChild(newbox);
	document.body.appendChild(layer);
	var sel = document.getElementsByTagName("select");
	for (var i = 0; i < sel.length; i++) {
		sel[i].style.visibility = "hidden";
	}
	function layer_iestyle() {
		layer.style.width = Math.max(document.documentElement.scrollWidth, document.documentElement.clientWidth) + "px";
		layer.style.height = Math.max(document.documentElement.scrollHeight, document.documentElement.clientHeight) + "px";
	}
	function newbox_iestyle() {
		newbox.style.marginTop = document.documentElement.scrollTop - newbox.offsetHeight / 2 + "px";
		newbox.style.marginLeft = document.documentElement.scrollLeft - newbox.offsetWidth / 2 + "px";
	}
	//if (isIE) {
		layer.style.filter = "alpha(opacity=60)";
	//}
	//if (isIE6) {
		layer_iestyle();
		newbox_iestyle();
		window.attachEvent("onscroll", function () {
			newbox_iestyle();
		});
		window.attachEvent("onresize", layer_iestyle);
	//}
	layer.onclick = function () {
		theOBJ.closeDialog();
	};
}

Wys_MENU.prototype.closeDialog = function(){
	document.body.removeChild(this.layer);
	document.body.removeChild(this.newbox);
	
	var sel = document.getElementsByTagName("select");
	for (var i = 0; i < sel.length; i++) {
		sel[i].style.visibility = "visible";
	}
}