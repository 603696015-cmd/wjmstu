var mouseOffset = null; //鼠标偏移位置
var iMouseDown = false; //代表鼠标状态，按下或者起来
var lMouseState = false; //上一个按钮状态
var dragObject = null; //被拖拽的对象
var DragDrops = []; //DragDrops 的第1维是个数组
var curTarget = null; //当前对象
var lastTarget = null; //上一个对象
var dragHelper = null; //定义一个DIV，记录最后一次操作的对象
var rootParent = null; //得到当前对象的父节点
var rootSibling = null; //当前对象的紧跟节点
var curClone = null;
var arr = [];
var tempdiv = null;
var initSourceContainer = null;
var SourceDrag = [];
Number.prototype.NaN0 = function () { return isNaN(this) ? 0 : this; }

function clearOrderEvent() {
    document.onmousemove = null;
    document.onmousedown = null;
    document.onmouseup = null;
}
function ChangeText() {
    var value = $("#TxtResponse").val().trim();
    if ("" == value) {
        ResetQuestion();
        $("#AnswerContainer").empty();
    }
}
//双击作答区内的一项
function ResetSource() {
    var event = window.event || arguments.callee.caller.arguments[0];
    var Deltarget = event.target || event.srcElement;

    if (Deltarget.parentNode.id == 'QuestionContainer') {
        return;
    }
    var tempID = Deltarget.id;
    $(Deltarget).remove();
    //当前元素恢复正常样式
    $(Deltarget).removeClass().addClass("DragBox");
    
    //问题区内重新追加此元素
    $(QuestionContainer).children().each(function (i, n) {
        var obj = $(n);
        if (obj.attr('id') == tempID) {
            $(n).remove();
            return false;
        }
    });
    $(QuestionContainer).append(Deltarget);
    
    arr.length = 0;

    
    $(QuestionContainer).children().each(function (i, n) {
        var obj = $(n);
        if (obj.attr('nodeName') == '#text') {
            return;
        }
        var id = obj.attr('id').substring(5, 6);
        arr.push(id);
    });
    

    arr.sort();
    
    //重新整理问题区
    tempdiv.innerHTML = '';
    for (var j = 0; j < arr.length; j++) {
        var item = "order" + arr[j];
        $(QuestionContainer).children().each(function (i, n) {
            var obj = $(n);
            if (obj.attr('id') == item) {
                $(tempdiv).append(obj);
                return false;
            }
        });
    }
    $(QuestionContainer).html("");
    $(QuestionContainer).html($(tempdiv).html());
    $("#TxtResponse").val(document.getElementById("AnswerContainer").innerText);
    SetOrderResponse();
    if (window.event)
        event.cancelBubble = true;
    else {
        event.preventDefault();
        event.stopPropagation(); //阻止其它浏览器
    }
}
//双击答案容器
function ResetQuestion() {
    document.getElementById('QuestionContainer').innerHTML = '';
    document.getElementById('AnswerContainer').innerHTML = '';
    document.getElementById('QuestionContainer').innerHTML = initSourceContainer;   
    $("#orderItemResult").val("");
}


function CreateDragContainer() {
    var cDrag = 0;  //DragDrops.length;
    DragDrops[cDrag] = [];
    //设置容器和它里面的每一项的DropObj 属性为0
    for (var i = 0; i < arguments.length; i++) {
        var cObj = arguments[i];
        DragDrops[cDrag].push(cObj);
        
        $(cObj).attr('DropObj', cDrag);
        $(cObj).children().each(function (j, n) {
            var obj = $(n);
            if (obj.attr('nodeName') == '#text') {
                return;
            }
            //设置要托放的节点的DragObj为0
            if (obj.attr('DragObj') == null) {
                obj.attr('DragObj', cDrag);
            }
        });
        
    }
}

//获取元素相对于文档的左上角的位置
function getPosition(e) {
    var left = 0;
    var top = 0;
    while (e.offsetParent) {
        left += e.offsetLeft + (e.currentStyle ? (parseInt(e.currentStyle.borderLeftWidth)).NaN0() : 0);
        top += e.offsetTop + (e.currentStyle ? (parseInt(e.currentStyle.borderTopWidth)).NaN0() : 0);
        e = e.offsetParent;
    }
    left += e.offsetLeft + (e.currentStyle ? (parseInt(e.currentStyle.borderLeftWidth)).NaN0() : 0);
    top += e.offsetTop + (e.currentStyle ? (parseInt(e.currentStyle.borderTopWidth)).NaN0() : 0);
    return { x: left, y: top };
}

//获取鼠标当前位置
function mouseCoords(ev) {
    try {
        if (ev.pageX || ev.pageY) {
            return { x: ev.pageX, y: ev.pageY };
        }
        return {
            x: ev.clientX + document.body.scrollLeft - document.body.clientLeft,
            y: ev.clientY + document.body.scrollTop - document.body.clientTop
        };
    }
    catch (Error) {
        return { x: 0, y: 0 };
    }
}

//获取鼠标每次移动偏移位置
function getMouseOffset(target, ev) {
    ev = ev || window.event;
    var docPos = getPosition(target);
    var mousePos = mouseCoords(ev);
    return { x: mousePos.x - docPos.x, y: mousePos.y - docPos.y };
}

function getOffsetHeight(obj) {
    var offset = obj.offsetHeight;
    var scroll = obj.scrollHeight;
    return Math.min(offset, scroll);
}
function getOffsetWidth(obj) {
    var offset = obj.offsetWidth;
    var scroll = obj.scrollWidth;
    return Math.min(offset, scroll);
}

//鼠标移动
function mouseMove(ev) {
    ev = ev || window.event;
    var target = ev.target || ev.srcElement;
    var mousePos = mouseCoords(ev);


    //判断鼠标移动时得到的对象是否为空，是否等于上次移动的位置的对象
    if (lastTarget && (target !== lastTarget)) {
        //如果当前对象和上一个目标对象不相等，
        // 重新设置目标元素的样式
        
        var origClass = $(lastTarget).attr('origClass');
        if (origClass) {
            $(lastTarget).removeClass().addClass(origClass);
        }
        
    }

    try {
        
        var dragObj = $(target).attr('DragObj'); //只有被拖动的对象有DragOb属性j
        
    }
    catch (e) { return false; }


    if (dragObj != null) {
        if (dragObj != '0') return;
        if (target != lastTarget) {
            // writeHistory(target, 'Mouse Over Fired');
            //获取被拖动对象的overClass样式
            
            var oClass = $(target).attr('overClass');
            if (oClass) {
                //设置目标对象的origClass属性为类名
                $(target).attr('origClass', target.className);
                $(target).removeClass().addClass(oClass); //设置类名为overClass，设置鼠标在对象上面时的样式
            }
            
        }
        //如果用户开始拖拽对象

        if (iMouseDown && !lMouseState) {
            // mouseDown target鼠标按下的目标对象赋值给当前对象
            curTarget = target;
            rootParent = curTarget.parentNode; //得到当前对象的父节点
            rootSibling = curTarget.nextSibling; //得到当前对象的接跟节点
            //得到偏移位置
            mouseOffset = getMouseOffset(target, ev);
            for (var i = 0; i < dragHelper.childNodes.length; i++)
                dragHelper.removeChild(dragHelper.childNodes[i]);

            //拖动节点放进dragHelper
            dragHelper.appendChild(curTarget.cloneNode(true));
            dragHelper.style.display = 'block';
            dragHelper.children[0].style.marginLeft = "0px";
            dragHelper.children[0].style.marginTop = "0px";

            // 获取当前拖动对象的dragClass属性
            var dragClass = $(curTarget).attr('dragClass'); //curTarget.getAttribute('dragClass');
            if (dragClass) {//当前对象的第一个节点的样式为dragClass,事实上dragHelper只能有一个元素
                dragHelper.firstChild.className = dragClass;
            }

            $(dragHelper).attr("DragObj", "");
            var dragConts = DragDrops[dragObj]; //获取容器和它里面包括的被拖动的对象的数组


            //设置拖动对象的属性值，然后隐藏拖动的对象
            $(curTarget).attr("startWidth", parseInt(curTarget.offsetWidth));
            $(curTarget).attr("startHeight", parseInt(curTarget.offsetHeight));
            curTarget.style.display = 'none';

            for (var i = 1; i < dragConts.length; i++) {
                var pos = getPosition(dragConts[i]); //获取每一个容器相对于文档的位置,设置每个容器的附加属性值
                $(dragConts[i]).attr("startWidth", parseInt(dragConts[i].offsetWidth));
                $(dragConts[i]).attr("startHeight", parseInt(dragConts[i].offsetHeight));
                $(dragConts[i]).attr("startLeft", pos.x + "px");
                $(dragConts[i]).attr("startTop", pos.y + "px");

                //设置容器内每个元素的属性值，除了要被拖动的对象
                for (var j = 0; j < dragConts[i].childNodes.length; j++) {
                    with (dragConts[i].childNodes[j]) {
                        if ((nodeName == '#text') || (dragConts[i].childNodes[j] == curTarget)) continue;
                        var pos = getPosition(dragConts[i].childNodes[j]);
                        $(dragConts[i].childNodes[j]).attr('startWidth', parseInt(offsetWidth));
                        $(dragConts[i].childNodes[j]).attr('startHeight', parseInt(offsetHeight));
                        $(dragConts[i].childNodes[j]).attr('startLeft', pos.x + "px");
                        $(dragConts[i].childNodes[j]).attr('startTop', pos.y + "px");
                    }
                }
            }
        }
    }


    if (curTarget) {
        var minConX, maxConX, minConY, maxConY;
        var owidth, oheight,conentTop;
        var objId = $("#testpaperResponsepanel")[0];
        //OrderContainer:排序题外层容器，testpaperResponsepanel 答题区域内拖拽
        owidth = getAbsWidth(objId);
        minConY = getAbsTop(objId);
        oheight = getAbsHeight(objId);
        minConX = getAbsLeft($("#main_one")[0]);
        maxConX = getAbsLeft($("#main_one")[0]) + owidth - dragHelper.offsetWidth;        
        maxConY = minConY + oheight - dragHelper.offsetHeight;
        conentTop=getAbsTop($("#content_one")[0]);
        //设置dragHelper位置(包含拖动对象)        
        var divX = mousePos.x - mouseOffset.x - $("#content_one")[0].offsetLeft;
        var divY = mousePos.y - mouseOffset.y ;
        if (divX <= minConX) { divX = minConX ; }
        if (divX >= maxConX) { divX = maxConX; }
        if (divY >= maxConY) { divY = maxConY; }
        if (divY <= minConY) { divY = minConY; }
        //当系统环境是XP、IE8并且使用客户端壳时；subNav_one高度未被计算，所以程序加上高度，当样式高度发生变化时，此处需要根据实际情况更新、、、
        if (conentTop < 80) {
            divY += $("#subNav_one").height(); //16
        }
        
        //divY =divY>200?divY-110:divY;
        dragHelper.style.top = divY + "px";
        dragHelper.style.left = divX + "px";

        var dragConts = DragDrops[curTarget.getAttribute('DragObj')];
        var activeCont = null; //获取包含拖动对象的容器
        var xPos = mousePos.x - mouseOffset.x + (parseInt($(curTarget).attr('startWidth')) / 2);
        var yPos = mousePos.y - mouseOffset.y + (parseInt($(curTarget).attr('startHeight')) / 2);
        //判断被拖动的对象是否在目标容器里面，得到当前活动的目标容器
        
        for (var i = 0; i < dragConts.length; i++) {
            with (dragConts[i]) {
                if ((parseInt($(dragConts[i]).attr('startLeft')) < xPos) &&
						(parseInt($(dragConts[i]).attr('startTop')) < yPos) &&
						((parseInt($(dragConts[i]).attr('startLeft')) + parseInt($(dragConts[i]).attr('startWidth'))) > xPos) &&
						((parseInt($(dragConts[i]).attr('startTop')) + parseInt($(dragConts[i]).attr('startHeight'))) > yPos)) {
                    activeCont = dragConts[i];
                    break;
                }
            }
        }
        
        if (activeCont) {
            var beforeNode = null; //拖动节点后面的一个节点
            // 遍历当前活动容器内的节点
            
            for (var i = $(activeCont).children().length - 1; i >= 0; i--) {
                    if ($(activeCont.childNodes[i]).attr('nodeName') == '#text') continue;
                    if (curTarget != activeCont.childNodes[i] &&
                        		((parseInt($(activeCont.childNodes[i]).attr('startLeft')) + parseInt($(activeCont.childNodes[i]).attr('startWidth'))) > xPos) &&
                        		((parseInt($(activeCont.childNodes[i]).attr('startTop')) + parseInt($(activeCont.childNodes[i]).attr('startHeight'))) > yPos)) {
                        beforeNode = $(activeCont.childNodes[i]);
                    }
            }
            
            curClone = curTarget.cloneNode(true);
            var isExist = false;
            //
            if (beforeNode) {
                if (beforeNode != curTarget.nextSibling) {
                    //                    activeCont.insertBefore(curTarget, beforeNode);
                    //                    curClone.style.display = '';
                    //                    curClone.style.visibility = 'visible';
                    $(beforeNode).before($(curTarget));
                    $(curClone).removeAttr("style");
                    $(curClone).css("visibility", "visible");
                    //}
                }
                // 被拖动的对象在容器的末尾
            }
            else {
                //                alert("2:" + curTarget.parentNode.innerHTML);
                //                alert("3:" + activeCont.innerHTML);
                if ((curTarget.nextSibling) || (curTarget.parentNode != activeCont)) {
                    //activeCont.appendChild(curTarget);
                    //                    curClone.style.display = '';
                    //                    curClone.style.visibility = 'visible';
                    //                    alert("4:" + $(activeCont).innerHTML);
                    $(activeCont).append($(curTarget));
                    //                    alert("5:" + $(activeCont).innerHTML);
                    $(curClone).css("display", "");
                    $(curClone).css("visibility", "visible");
                }
            }
            if (curTarget.style.display != '') {
                //                curTarget.style.display = '';
                //           curTarget.style.visibility = 'hidden';
                $(curTarget).css("display", "");
                $(curTarget).css("visibility", "hidden");
            }
        }
        else {
            // 
            //当我们拖动的对象不在一个目标容器里面,隐藏它
            if (curTarget.style.display != 'none') {
                $(curClone).css("display", "none");
            }
        }
    }
    lMouseState = iMouseDown;
    lastTarget = target;
    if (dragObject) {
        $(dragObject).css("position", "absolute");
        //$(dragObject).css("top", parseInt(mousePos.y - mouseOffset.y) + "px");
        //$(dragObject).css("left", parseInt(mousePos.x - mouseOffset.x) + "px");
    }
    lMouseState = iMouseDown;
    if (curTarget || dragObject) return false;
}
//获取当前对象距离左边屏幕的距离
function getAbsLeft(obj) {
    var l = obj.offsetLeft;
    //var path = obj.outerHTML.toString().substring(0, 200) + "(offsetLeft" + l + ")";
//    alert("l前:"+l);
    while (obj.offsetParent != null) {
        obj = obj.offsetParent;
        l += obj.offsetLeft;
        //        alert("offsetLeft:" + obj.offsetLeft);
       // path = path + '\r---------------' + obj.outerHTML.toString().substring(0, 200) + "(offsetLeft" + l + ")";
    }
    //    alert("l后:" + l);
    //alert(path);
    return l;
}

function getAbsTop(obj) {
    var l = obj.offsetTop;
    while (obj.offsetParent != null) {
        obj = obj.offsetParent;
        l += obj.offsetTop;
    }
    return l;
}

function getAbsHeight(obj) {
    var l = obj.offsetHeight;
    while (obj.offsetParent != null) {
        obj = obj.offsetParent;
        l += obj.offsetHeight;
    }
    return l;
}
function getAbsWidth(obj) {
    var l = obj.offsetWidth;
    while (obj.offsetParent != null) {
        obj = obj.offsetParent;
        l += obj.offsetWidth;
    }
    return l;
}
function mouseUp(ev) {
    var cur = document.getElementById("AnswerContainer");
    if (curTarget) {
        dragHelper.style.display = 'none';
        if (curTarget.style.display == 'none') {
            if (rootSibling) {
                rootParent.insertBefore(curTarget, rootSibling);
            }
            else {
                rootParent.appendChild(curTarget);
            }
        }
        else if (curTarget.style.display == '') {
            if (rootParent.id != 'AnswerContainer') {
                if (rootSibling) {
                    rootParent.insertBefore(curClone, rootSibling);
                }
                else {
                    rootParent.appendChild(curClone);
                }
            }
        }
        if (curClone != null) {
            curClone.style.display = '';
            curClone.setAttribute('DragObj', '1');
            curClone.style.visibility = 'visible';
            curClone.removeAttribute('overclass');
            curClone.removeAttribute('DragDragBox');
            //0425(鼠标释放更改问题区)
            $(curClone).attr('class', 'DragBox2');

        }
        curTarget.style.display = '';
        curTarget.style.visibility = 'visible';
        //输入框赋值
        //if (ev!=undefined&&ev.target.id == "AnswerContainer" && curTarget.className == "DragBox")
        $("#TxtResponse").val(cur.innerText);
    }
    curTarget = null;
    dragObject = null;
    iMouseDown = false; //表明鼠标被松开
    if (cur != null) {
        for (var i = 0; i <cur.childNodes.length; i++) {
            cur.childNodes[i].setAttribute('className', 'DropBox');
        }
    }
    SetOrderResponse();
    
    setLocationCacheData(ev);
}

function mouseDown(ev) {
    ev = ev || window.event;
    var target = ev.target || ev.srcElement;

    iMouseDown = true; //表明鼠标被按下
}

//加载排序题内容（load时注册）
LoadOrderQuestion = function () {

    CreateDragContainer(document.getElementById('QuestionContainer'), document.getElementById('AnswerContainer'));

    if ($("#t1").length == 0) {
        $("Text1").val(Math.random() * 10000);

        dragHelper = document.createElement('DIV');
        dragHelper.setAttribute("id", "Temp1");
        dragHelper.style.cssText = 'position:absolute;display:none;';
        document.body.appendChild(dragHelper);

        tempdiv = document.createElement('div');
        tempdiv.setAttribute("id", "t1");
        tempdiv.style.display = "none";
        document.body.appendChild(tempdiv);
    } else {
        $("t1").html("");
        $("Temp1").html("");
    }
    initSourceContainer = document.getElementById('QuestionContainer').innerHTML;

    document.onmousemove = mouseMove;
    document.onmousedown = mouseDown;
    document.onmouseup = mouseUp;
    //$("#AnswerContainer").bind("dblclick", ResetQuestion);
    CompatibleFirefox();
}
//处理火狐关于innerText兼容性问题
function CompatibleFirefox() {
    if (!!document.getBoxObjectFor || window.mozInnerScreenX != null) {
        HTMLElement.prototype.__defineSetter__("innerText", function (sText) {
            var parsedText = document.createTextNode(sText);
            this.innerHTML = "";
            this.appendChild(parsedText);
            return parsedText;
        });
        HTMLElement.prototype.__defineGetter__("innerText", function () {
            var r = this.ownerDocument.createRange();
            r.selectNodeContents(this);
            return r.toString();
        });
    }
}
//加载作答结果
function LoadOrderResponse(isexample, resvalue) {
    var str = resvalue;
    if (typeof str === 'undefined') return;
    if (str != "") {
        for (var i = 0; i < str.length; i++) {
            if ($("#QuestionContainer").find("div[tag='" + str.charAt(i) + "']").length > 0) {
                var dragedItem = $("#QuestionContainer").find("div[tag='" + str.charAt(i) + "']");
                $(dragedItem).attr("DragObj", "1");

                $("#AnswerContainer").append($(dragedItem).clone(true));
                $("#AnswerContainer").find("div[tag='" + str.charAt(i) + "']").attr("style", "DISPLAY: ; VISIBILITY: visible");
                $("#AnswerContainer").find("div[tag='" + str.charAt(i) + "']").attr("origClass", "DropBox");
                $("#AnswerContainer").find("div[tag='" + str.charAt(i) + "']").attr("startWidth", "94");
                $("#AnswerContainer").find("div[tag='" + str.charAt(i) + "']").attr("startHeight", "28");

                $(dragedItem).removeClass();
                $(dragedItem).addClass("DragBox2");
            }
        }
        //处理作答区中div元素
        if (isexample != true) {
            $("#AnswerContainer").find("div").attr("DragObj", "0");
        } else {
            $("#AnswerContainer").find("div").removeAttr("origClass");
            $("#AnswerContainer").find("div").attr("DragObj", "1");
            $("#AnswerContainer").find("div").removeAttr("style");
            $("#AnswerContainer").find("div").removeAttr("overclass");
            $("#AnswerContainer").find("div").removeAttr("dragclass");
            $("#AnswerContainer").find("div").removeAttr("ondblclick");
           // $("#AnswerContainer").unbind("dblclick", ResetQuestion);
        }
    }
}

//获取作答结果
function SetOrderResponse() {
    //排序题特殊处理
    $("#orderItemResult").val("");
    $("#AnswerContainer").find("div").each(function (item) {
        $("#orderItemResult").val($("#orderItemResult").val() + $(this).attr("tag"));
    });
    if ($("#orderItemResult").val() != "") {
        $("#ItemResponseFlag").text("1");
    } else {
        $("#ItemResponseFlag").text("");
    }
}


function setLocationCacheData (obj){
    _info = getLocal('_info' + $('#examId').val());
	var parentObj = $(obj.path[0]);
	var type = parentObj.attr('data-type');//当前类型id
    var quesId = parentObj.attr('data-id');
    if(typeof quesId == 'undefined'){
    	return false;
    }
    quesId = quesId.split("_")[1];
    $.each(_info.arr, function(index, item) { // 循环试题
        if (item.id == quesId) { // 给对应 tid 的 answer 放入答案 ans
            item.answer = $('#TxtResponse').val();
            item.type = type;
            item.responseAns = $("#orderItemResult").val();
            return false;
        }
    });
    
    var num = fetchTypeIndex(type);
	var index = getSeqNum()-1-num<0?0:getSeqNum()-1-num;
	$('.e-question-num li').eq(index).css("background","#a7f9fa")
    setLocal('_info' + $('#examId').val(), _info); 
}