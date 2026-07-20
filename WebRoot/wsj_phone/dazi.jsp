<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<!-- saved from url=(0146)http://www.liandazi.cn/dazi2.html -->
<HTML xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>js打字练习</title>
		<script type=text/javascript>
    	var autoRun = null;
    	var time = 0;
    	var numAll = 0;
    	function changeText() {
    		clearText();
    		var txt = getObj('txtInput').value;
    		txt = txt.replace(/\r\n/g, '\n');
    		var divTxt = getObj('divText').innerHTML;
    		divTxt = divTxt.replace(/  /g, ' ');
    		divTxt = divTxt.replace(/   /g, ' ');
    		divTxt = divTxt.replace(/\r/g, '');
    		divTxt = divTxt.replace(/\n/g, '');
    		divTxt = divTxt.replace(/&nbsp;/g, ' ');
    		divTxt = divTxt.replace(/<BR>/g, '\n');
    		divTxt = divTxt.replace(/<br>/g, '\n');
    		var top = 18;
    		var left = 0;
    		var error = 0;
    		var lost = 0;
    		var spanObj = null;
    		for (var i = 0; i < divTxt.length; i++) {
    			if (i >= txt.length + lost) {
    				break;
    			}
    			var color = '#CCC';
    			if (txt.charAt(i - lost) != divTxt.charAt(i)) {
    				error++;
    				color = 'red';
    			}
    			if (spanObj && spanObj.color != color) {
    				addSpan(spanObj);
    				spanObj = null;
				}
    			if (divTxt.charAt(i) == '\n' || divTxt.charAt(i) == '\r') {
    				if (divTxt.charAt(i) == '\n') {
    					top += 60;
    					left = 0;
    					addSpan(spanObj);
    					spanObj = null;
    				}
    				continue;
    			}
    			var charWidth = divTxt.charCodeAt(i) > 255 ? 24 : 13;
    			if (!spanObj) {
    				spanObj = new Object();
    				spanObj.top = top;
    				spanObj.left = left;
    				spanObj.color = color;
    				spanObj.height = 24;
    				spanObj.width = charWidth;
    			}
    			else {
    				spanObj.width += charWidth;
				}
    			left += charWidth;
    			if (left > getObj('divText').scrollWidth - charWidth) {
    				top += 60;
    				left = 0;
    				addSpan(spanObj);
    				spanObj = null;
    				if (divTxt.charAt(i + 1) == ' ' || divTxt.charAt(i + 1) == '\n') {
    					i++;
    					lost++;
					}
    			}
    		}
    		if (spanObj) {
    			addSpan(spanObj);
    			spanObj = null;
			}	
    		getObj('spanRate').value = txt.length-error; //Math.round(100 - (error / txt.length) * 100) + '%';
    		getObj('spanSpeed').value = Math.round(txt.length / time * 60) ;
    	}

    	function addSpan(spanObj) {
    		if (!spanObj) {
    			return;
			}
    		var span = document.createElement('span');
    		span.style.position = 'absolute';
    		span.style.width = spanObj.width + 'px';
    		span.style.height = spanObj.height + 'px';
    		span.style.top = spanObj.top + 'px';
    		span.style.left = spanObj.left + 'px';
    		span.style.zIndex = 1;
    		span.style.backgroundColor = spanObj.color;
    		getObj('divMain').appendChild(span);
		}
    	function clearText() {
    		var divMain = getObj('divMain');
    		var spans = divMain.getElementsByTagName('span');
    		while (spans.length > 0) {
    			divMain.removeChild(spans[0]);
    		}
    	}
		function init_(){
			var txt = getObj( "txtInput" ).value;
    		txt = txt.replace(/\r/g, '');
    		txt = txt.replace(/–/g, '-');
    		txt = txt.replace(/\n/g, '<br>');			
    		var divText = getObj('divText');
    		txt = txt.replace(/  /g, ' ');
    		txt = txt.replace(/   /g, ' ');
    		divText.innerHTML = txt;
    		numAll = txt.length;
		}
    	function start_() {
    		if (autoRun) {
    			window.clearInterval(autoRun);
    		}
    		
    		var btnStart = getObj('btnStart');
    		var txtInput = getObj('txtInput');
    		var divText = getObj('divText');
    		getObj('txtInput').focus();
    		clearText();
    		txtInput.style.height = Math.max(500, divText.offsetHeight) + 'px';
   			txtInput.value = '';
   			txtInput.disabled = false;
   			txtInput.focus();
   			getObj('spanTime').value = '0';
   			getObj('spanRate').value = '0';
   			getObj('spanSpeed').value = '0';    			
   			numAll = 0;
   			numError = 0;
   			numInput = 0;
   			time = 0;
   			autoRun = window.setInterval(timer, 1000);
    	}

    	function timer() {
    		time++;
    		var str = '';
    		var fen = Math.floor(time / 60);
    		if (fen > 0) {
    			str += fen + '分';
    		}
    		getObj('spanTime').value = str + (time - fen * 60) ;
    		var txt = getObj('txtInput').value;
    		txt = txt.replace(/\r\n/g, '\n');
    		getObj('spanSpeed').value = Math.round(txt.length / time * 60) ;
    	}

    	function getObj(id) {
    		return document.getElementById(id);
    	}
    </script>
		<style type="text/css">
.txtArea {
	border-bottom: medium none;
	position: absolute;
	border-left: medium none;
	padding-bottom: 0px;
	line-height: 60px;
	overflow-y: hidden;
	background-color: white;
	margin: 0px;
	padding-left: 0px;
	width: 760px;
	padding-right: 0px;
	font-family: consolas;
	word-wrap: break-word;
	letter-spacing: 0px;
	height: 480px;
	color: blue;
	font-size: 24px;
	word-break: break-all;
	border-top: medium none;
	top: 0px;
	border-right: medium none;
	word-spacing: normal;
	padding-top: 28px;
	left: 0px
}

.txtDiv {
	z-index: 2;
	position: absolute;
	padding-bottom: 0px;
	line-height: 60px;
	margin: 0px;
	padding-left: 0px;
	width: 760px;
	padding-right: 0px;
	font-family: consolas;
	word-wrap: break-word;
	letter-spacing: 0px;
	color: #333333;
	font-size: 24px;
	word-break: break-all;
	word-spacing: 0px;
	padding-top: 0px;
	left: 0px
}

.dazidiv {
	border-bottom: #0096cd 5px solid;
	position: relative;
	border-left: #0096cd 5px solid;
	overflow-y: scroll;
	background-color: white;
	width: 780px;
	height: 480px;
	border-top: #0096cd 5px solid;
	border-right: #0096cd 5px solid
}
</style>
		<META name=GENERATOR content="MSHTML 8.00.6001.19088">
	</HEAD>
	<BODY onload="init_();start_();">
		<div style="margin: 5px 0px">
			<INPUT id=btnStart onclick="" value="提交" type=button>
			用时：
			<input type="text" size="4" id="spanTime" />
			秒 &nbsp;&nbsp; 正确率：
			<input type="text" size="4" id="spanRate" />  &nbsp;&nbsp; 速度：
			<input type="text" size="4" id="spanSpeed" /> 字/分 
		</div>
		<div
			class="dazidiv"
			id="divMain">
			<textarea class="txtArea" id="txtInput" onkeyup=changeText();>在文字出现以前，人们就已经学会了计数，并且能够阅读没有话语的故事。人类很早就能用图形来表达自己的思想。大约在公元前3400年，美索不达米亚的工匠就依据自己的爱好再现了日常生活的场景。与此同时，在埃及，这种用刀雕刻出来的装饰清晰地表明了没有语言对叙事的影响。由于缺乏文字的记录，古埃及学的专家们至今无法确定正在交战的这两个部落的身份，以及这场战争的来龙去脉。大约公元前3300年，在美索不达米亚平原，出现了一些黏土或石质的小书板。一个数字由一道刻痕标示，后面还有人名、动物名或食物名，这样的名词是由一个图形或一个图形符号来表示的。同时，图形符号变成了概念符号。这种苏美尔文字反映了它们所表达的社会象征。
			    埃及最早的文字大约出现在公元前3150年。这是一个用于祈祷的大型工具的碎片，也许是用来给神像的眼睛上粉的。它纪念了一位国王的胜利，国王在这里变成了一头践踏敌人的公牛。埃及的文字从一开始就是包含了三种符号的严密体系，其中某些符号还注了音。当时的人们创造这些符号，也许是为了记下一些很难用一幅简单的图形表示出来的专有名词。一只隼站在住着一条眼镜蛇的宫殿的围墙上，这种庄严的组合就是一个简单的专有名词。蛇，是用来指代一个国王名字的形象，因为国王名字的发音与蛇的发音相同。象形文字及其复杂的符号，与日常生活的要求是不相符的。于是从一开始就有一种简化的文字与之共存，这种文字能用墨水和毛笔很快地在各种各样的材料上书写下来，这种文字逐渐失去了其图形特征，离象形文字的模型越来越远。楔形文字这个词来自拉丁语的“楔子”，它并不是符号演变的开端，而是一个成果。起初，物体都是按照它们呈现出来的形态被描画下来，例如麦穗。有时是概括性的，例如用动物的头来指代动物本身，或者是象征性的，例如用女性的简化符号来指代一个妇女。但由于这种文字是写在黏土上的，所以它很快就会变形。象形文字的文本可以在几个方向上进行书写，这些文字首要的作用是写在一些纪念物上，所以它们的顺序要符合纪念物的形状和装饰。在埃及，文字与图形之间是没有界限的，比方说，这座高官的塑像以一条竖立在篮子中的蛇作为装饰，而这也可以作为一段铭文来解释。篮子意味着女主人。两臂表示食品。蛇则令人联想到一位女神——食物女神，雕像底座的文字也同时提到了这位女神的名字。这些象形文字都采用古埃及绘画的一般方法，它们只能通过缩减尺寸与图形符号区别开，因此象形文字也是真正的艺术品。装饰这个寺庙墙壁的碑文，表明了古埃及文字对美学原则的严格遵循。象形文字的排列和形状主要是为了让人悦目。而且埃及文字用来做装饰也是很常见的，同时还保留了其最初的目的，也就是使它所记载的信息能够永远流传。
			    古埃及象形文字对后世的影响非常大，今天的我们就是继承了他们的成果。归根结底，我们今天的纸张和钢笔都只不过是从古埃及书记员的工具演化而来的。
			</textarea>
			<div class="txtDiv" id=divText onclick="getObj('txtInput').focus();"></div>
		</div>
	
	</body>
</HTML>
