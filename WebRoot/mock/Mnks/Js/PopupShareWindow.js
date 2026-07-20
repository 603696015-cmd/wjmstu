///<reference path="jquery-1.3.2-vsdoc2.js"/*tpa=http://mnks.cnhsk.org/Mnks/Js/jquery-1.3.2-vsdoc2.js*/ />
//名称空间定义，所有的命名空间都要在此定义，其它地方绝不能出现JS命名空间定义语句
var ems = {};
ems.common = {};
ems.uicontrol = {};
//--------命名空间定义end-----
ems.uicontrol.PopupShareWindow = function() {
    this._popWins = [];
    this._popCovers = [];
};
ems.uicontrol.PopupShareWindow.Show = function (type, id) {
    __popupShareWindow.Show(type, id);
};
ems.uicontrol.PopupShareWindow.Hide = function() {
    __popupShareWindow.Hide();
};

//作答结果按钮事件结束
ems.uicontrol.PopupShareWindow.Hides = function () {
    __popupShareWindow.Hides();
};

ems.uicontrol.PopupShareWindow.prototype =
{
    Show: function (url, title) {
        var src = url;
        title = title || "";
        var body = $(top.document.body);
        var scrollTop = top.document.documentElement ? top.document.documentElement.scrollTop : top.document.body.scrollTop;
        var popWin = top.document.createElement('div');
        popWin.id = "PopWin" + (this._popWins.length + 1);
        with (popWin.style) {
            zIndex = (10000 + (this._popWins.length * 100) + 50);
            width = 68;
            height = 68;
            position = 'absolute';
        }

        popWin.innerHTML = "<div id='PopupLoader" + (this._popWins.length + 1) + "' style='padding:0px'><div class='load'></div></div><div style='display:none; height:100%; height:100%' id='PopupContent" + (this._popWins.length + 1) + "'>  <div id='popUpTitleBar' name='popUpTitleBar_" + (this._popWins.length + 1) + "'><div id='popUpTitleIcon'></div><div id='popUpTitleWord' name='popUpTitleWord_" + (this._popWins.length + 1) + "'>" + title + "</div><div id='popUpTitleBarRightBlock'><div id='popUpTitleCloseButton' class='popUpTitleCloseButton' onclick='top.__popupShareWindow.Hide();'></div></div></div><iframe style='width:100%; height:0;border:0px;position:relative' frameborder='0' src='" + src + "' name='popUpiframe_" + (this._popWins.length + 1) + "'  scrolling='no' onload='top.__popupShareWindow.AutoSetIframeHeight(this);'></iframe><div>";
        top.document.body.appendChild(popWin);
        popupShareWindow = $(popWin);

        this.popCoverDiv();

        popupShareWindow.height(40);
        var clientViewHeight = 0; //页面可视高度
        try {
            if (parent.document.body.clientHeight && parent.document.documentElement.clientHeight) {
                clientViewHeight = (parent.document.body.clientHeight < parent.document.documentElement.clientHeight) ? parent.document.body.clientHeight : parent.document.documentElement.clientHeight;
            }
            else {
                clientViewHeight = (parent.document.body.clientHeight > parent.document.documentElement.clientHeight) ? parent.document.body.clientHeight : parent.document.documentElement.clientHeight;
            }
            popupShareWindow.css({ left: (body.width() - popupShareWindow.width()) / 2 + "px", top: (clientViewHeight - popupShareWindow.height()) / 2 + "px" });
            this._popWins.push(popWin); //向数组中加入popWin
            //解决拖动粘滞的问题
            var header = $("#popUpTitleBar" + (this._popWins.length + 1));
            //popupShareWindow.draggable({ handle: header });
            $("div[name='popUpTitleIcon_" + (this._popWins.length) + "']").html(title);
            $("#popUpTitleBar" + (this._popWins.length + 1)).hide();
            return popupShareWindow.find("iframe:first")[0];
        } catch (e) { 
        
        }        
    },
    AutoSetIframeHeight: function (obj) {
        var win = obj;
        if (document.getElementById) {
            if (win && !window.opera) {
                if (win.contentDocument && win.contentDocument.body.offsetHeight)

                    win.height = win.contentDocument.body.offsetHeight;
                else if (win.Document && win.Document.body.scrollHeight)
                    win.height = win.Document.body.scrollHeight;
            }
        }
    }
    ,
    ProgressBar: function () {
        var body = $(top.document.body);
        var scrollTop = top.document.documentElement ? top.document.documentElement.scrollTop : top.document.body.scrollTop;
        var popWin = top.document.createElement('div');
        popWin.id = "PopWin" + (this._popWins.length + 1);
        with (popWin.style) {
            zIndex = (10000 + (this._popWins.length * 100) + 50);
            width = 68;
            height = 68;
            position = 'absolute';
        }
        popWin.innerHTML = "<div class='load'></div>";
        top.document.body.appendChild(popWin);
        popupShareWindow = $(popWin);

        this.popCoverDiv();

        popupShareWindow.height(40);
        //计算屏幕居中取屏幕可用宽度和可用高度
        var st = document.documentElement.scrollTop; //滚动条距顶部的距离
        var sl = document.documentElement.scrollLeft; //滚动条距左边的距离
        var ch = document.documentElement.clientHeight; //屏幕的高度
        var cw = document.documentElement.clientWidth; //屏幕的宽度
        var objH = popupShareWindow.height(); //浮动窗口的高度
        var objW = popupShareWindow.width(); //浮动窗口的宽度
        var objT = Number(st) + (Number(ch) - Number(objH)) / 2;
        var objL = Number(sl) + (Number(cw) - Number(objW)) / 2;
        var objBH = Number(st) + 40;
        popupShareWindow.css({ left: objL + "px", top: objT + "px" });
        this._popWins.push(popWin); //向数组中加入popWin

    }
    ,
    GetOpener: function () {//获取焦点窗口
        if (this._popWins.length < 1) return null;
        return top.document.getElementById("PopWin" + (this._popWins.length)).getElementsByTagName("iframe")[0];
    }
	,
    RefreshOpener: function () {//刷新焦点窗口(最顶层弹出窗口)
        this.GetOpener().contentWindow.location = this.GetOpener().contentWindow.location;
    },
    RefreshParentOpener: function () {//关闭当前层，刷新父级层
        this.Hide();
        this.RefreshOpener();
        //卸载最上层窗口，然后刷新
    },
    Hide: function () {
        $(top.document.getElementById("PopWin" + this._popWins.length)).fadeOut();
        $(top.document.getElementById("CoverDiv" + this._popCovers.length)).hide();
        top.document.body.removeChild(this._popWins[this._popWins.length - 1]);
        top.document.body.removeChild(this._popCovers[this._popCovers.length - 1]);
        this._popWins.pop();
        this._popCovers.pop();

    },
    Hides: function () {
        $(top.document.getElementById("PopWin" + this._popWins.length)).fadeOut();
        $(top.document.getElementById("CoverDiv" + this._popCovers.length)).hide();
        top.document.body.removeChild(this._popWins[this._popWins.length - 1]);
        top.document.body.removeChild(this._popCovers[this._popCovers.length - 1]);
        this._popWins.pop();
        this._popCovers.pop();
        ReloadPage(); //重新调用父级事件信息
    },
    SetHeight: function (height) {
        var element = parent.document.body
        var els = element.style;
        var originalVisibility = els.visibility;
        var originalPosition = els.position;
        var originalDisplay = els.display;
        els.visibility = 'hidden';
        els.position = 'absolute';
        els.display = 'block';
        var originalWidth = element.clientWidth;
        var originalHeight = element.clientHeight;
        els.display = originalDisplay;
        els.position = originalPosition;
        els.visibility = originalVisibility;
        var body = $(parent.document.body);

        var tpopwin = $(top.document.getElementById("PopWin" + this._popWins.length));
        var clientViewHeight = 0; //页面可视高度
        if (parent.document.body.clientHeight && parent.document.documentElement.clientHeight) {
            clientViewHeight = (parent.document.body.clientHeight < parent.document.documentElement.clientHeight) ? parent.document.body.clientHeight : parent.document.documentElement.clientHeight;
        }
        else {
            clientViewHeight = (parent.document.body.clientHeight > parent.document.documentElement.clientHeight) ? parent.document.body.clientHeight : parent.document.documentElement.clientHeight;
        }
        // parent.document.documentElement.scrollTop = (originalHeight - clientViewHeight) / 2; //滚动条距顶部的距离
        var objLeft = (body.width() - tpopwin.width()) / 2;
        var objTop = (clientViewHeight - height) / 2;
        if (Number(objTop) < 0) {
            objTop = 80;
        }
        parent.document.body.sc
        tpopwin.css({ left: objLeft + "px", top: objTop + "px" });

        $(top.document.getElementById("PopWin" + this._popWins.length)).height(height);
        $(top.document.getElementById("PopupLoader" + this._popWins.length)).hide();
        $(top.document.getElementById("PopupContent" + this._popWins.length)).css({ display: "block" });
        $("iframe", top.document.getElementById("PopupContent" + this._popWins.length)).height(height);

        $("#popUpTitleBar").show();
    },
    SetWidth: function (width) {

        var element = parent.document.body
        var els = element.style;
        var originalVisibility = els.visibility;
        var originalPosition = els.position;
        var originalDisplay = els.display;
        els.visibility = 'hidden';
        els.position = 'absolute';
        els.display = 'block';
        var originalWidth = element.clientWidth;
        var originalHeight = element.clientHeight;
        els.display = originalDisplay;
        els.position = originalPosition;
        els.visibility = originalVisibility;
        var body = $(parent.document.body);
        var tpopwin = $(top.document.getElementById("PopWin" + this._popWins.length));
        //       var clientViewHeight = 0; //页面可视高度
        //        if (parent.document.body.clientHeight && parent.document.documentElement.clientHeight) {
        //            clientViewHeight = (parent.document.body.clientHeight < parent.document.documentElement.clientHeight) ? parent.document.body.clientHeight : parent.document.documentElement.clientHeight;
        //        }
        //        else {
        //            clientViewHeight = (parent.document.body.clientHeight > parent.document.documentElement.clientHeight) ? parent.document.body.clientHeight : parent.document.documentElement.clientHeight;
        //        }
        // parent.document.documentElement.scrollTop = (originalHeight - clientViewHeight) / 2; //滚动条距顶部的距离

        var tpopwin = $(top.document.getElementById("PopWin" + this._popWins.length));

        tpopwin.css({ left: (body.width() - tpopwin.width()) / 2 + "px", top: (originalHeight - tpopwin.width()) / 2 + "px" });
        $(top.document.getElementById("PopWin" + this._popWins.length)).width(width);
        $(top.document.getElementById("PopupLoader" + this._popWins.length)).hide();
        $(top.document.getElementById("PopupContent" + this._popWins.length)).css({ display: "block" });

        $("iframe", top.document.getElementById("PopupContent" + this._popWins.length)).width(width);


    },
    Alert: function (msg, callback) {
        $('.jqmDialog').jqm({ modal: true, onHide: callback, closeClass: 'jqmClose', trigger: false, overlay: 30, overlayClass: 'whiteOverlay' });
        $('.jqmDialog').css("z-index", "20000"); //设置最上层显示
        $('.jqmDialog').find('.jqmdMSG').html(msg);
        $('.jqmDialog').jqmShow();
    },
    popCoverDiv: function () {
        var element = parent.document.body
        var els = element.style;
        var originalVisibility = els.visibility;
        var originalPosition = els.position;
        var originalDisplay = els.display;
        els.visibility = 'hidden';
        els.position = 'absolute';
        els.display = 'block';
        var originalWidth = element.clientWidth;
        var originalHeight = element.clientHeight;
        els.display = originalDisplay;
        els.position = originalPosition;
        els.visibility = originalVisibility;
        var coverDiv = top.document.createElement('div');
        coverDiv.id = 'CoverDiv' + (this._popCovers.length + 1);

        var topAll = top.document.all;
        var coverwidth = Number(window.screen.availWidth) - 25 + "px";
        //  height = Number(originalHeight) + Number(500) + "px";
        var cvoerheight = parent.document.documentElement.scrollHeight; //取滚动条高度
        with (coverDiv.style) {
            position = 'absolute';
            background = '#f5f5f5';
            left = "0px";
            top = "0px";
            //width = originalWidth + "px";
            width = coverwidth; //window.screen.availWidth + "px";
            //  height = Number(originalHeight) + Number(500) + "px";
            height = cvoerheight; //parent.document.documentElement.scrollHeight; //取滚动条高度
            zIndex = 10000 + (this._popCovers.length * 100);
            if (topAll && window.ActiveXObject && !window.opera) {
                filter = "Alpha(Opacity=60)";
            }
            else {
                opacity = 0.6;
            }

        }
        this._popCovers.push(coverDiv);
        top.document.body.appendChild(coverDiv);
        var coverstr = "<iframe  width='" + coverwidth + "px' height='" + cvoerheight + "px' position:absolute;top:0px;left:0px; z-index:-1;'></iframe>";
        $("#" + coverDiv.id).append(coverstr);
    }

};

var __popupShareWindow = new ems.uicontrol.PopupShareWindow();