/*
*Site: 
*Author: xjm
*Updated: 2012.7.20
*Updated by: xjm
*/
$(function(){
	//顶部导航
	$('#website .top_guide').hover(function(){
		$(this).addClass('hover').find('div.guideLayout').show();
		
	},function(){
		$(this).removeClass('hover').find('div.guideLayout').hide();
	});
	//课程导航
	tab($('.kcNav .slideNav li'),$('.kcNav .tbCont ul'),'on','click');
	$('.kcNav .kcList > li').hover(function(){
		$(this).addClass('hover');
	},function(){
		$(this).removeClass('hover');
	});
	//考试指南导航
	$('#ksznNav li:not(.titTip)').hover(function(){
		$(this).addClass('cur');
	},function(){
		$(this).removeClass('cur');
	});
})
//tab选项卡
function tab(oj1,oj2,c,e){
	oj1.each(function(i){
		$(this).bind(e,function(){
			oj2.hide().eq(i).show();
			oj1.removeClass(c).eq(i).addClass(c);
		})
		if($(this).hasClass(c)){
			oj2.hide().eq(i).show();
		}
	})
};
//返回顶部
(function () {
    function loadStyleString(css) {
        var style = document.createElement("style");
        style.type = "text/css";
        try {
            style.appendChild(document.createTextNode(css));
        } catch (ex) {
            style.styleSheet.cssText = css;
        };
        var head = document.getElementsByTagName("head")[0];
        head.appendChild(style);
    };
    (function () { //返回顶部
        if (!+"\v1") {
            loadStyleString('html{_background: url(about:blank) fixed;}');
        }
        var div = document.createElement("div");
        div.id = 'backToTop';
		div.title = '点击返回页面顶部';
        div.style.cssText = "display:none;cursor:pointer;background:url(http://px.thea.cn/images/jeaslong_ad/inc/toTopBg.png) 0 0 no-repeat;height:75px;width:21px;position:fixed;right:10px;bottom:50px;_position:absolute;_bottom:auto; _top:expression(document.documentElement.scrollTop+documentElement.clientHeight-this.offsetHeight-50);";

        var right = (parseInt(document.documentElement.clientWidth) - 980) / 2 - 21;
        div.style.right = right + 'px';
        document.getElementsByTagName('body')[0].appendChild(div);
        window.onscroll = function () {
            div.style.display = document.documentElement.scrollTop || document.body.scrollTop ? "block" : "none";
        };
        window.onresize = function () {
            var right = (parseInt(document.documentElement.clientWidth) - 980) / 2 - 21;
            div.style.right = right + 'px';
        };
        div.onclick = function () {
            window.scrollTo(0, 0);
        };
		div.onmouseover = function () {
			div.style.backgroundPosition = '-21px 0';
		};
		div.onmouseout = function () {
			div.style.backgroundPosition = '0 0';
		};
    })();
})();
