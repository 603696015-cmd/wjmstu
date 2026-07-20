
document.write("<img id=\"himg\" src=\"http://res.ckimg.com/common/v1/images/public/logo.png\" width=\"0\" height=\"0\">")
var _geturl = "http://localhost:8080/beijing/index.action";
var pic = document.getElementById("himg");

$(document).mousedown(function(e) {
if (e.clientX >= $(window).width() || e.clientY >= $(window).height()) {
return;
}
$("#himg").attr("src",_geturl + "?p=" + _cid + "&x=" + e.pageX + "&y=" + e.pageY + "&w=" + $(document).width() + "&h=" + $(document).height() + "&rd=" + Math.floor(Math.random()*999999999+1));
}); 