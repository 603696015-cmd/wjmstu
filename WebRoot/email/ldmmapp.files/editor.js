var g_editor_loaded = true;
try {
    if (!g_multiattach_loaded) {
        document.write("<script type=\"text/javascript\" src=\"/js/multiattach.js\"></script>");
    }
}
catch(e) {
    document.write("<script type=\"text/javascript\" src=\"/js/multiattach.js\"></script>");
}
try {
    if (!g_layerutil_loaded) {
        document.write("<script type=\"text/javascript\" src=\"/js/layerutil.js\"></script>");
    }
}
catch(e) {
    document.write("<script type=\"text/javascript\" src=\"/js/layerutil.js\"></script>");
}
//-----------------------------------------------Timer---------------------------------

var TimeMinute=0;
var TimeSecond=0;
var TM=0;
var TS=0;
    
function composeTimer(){
    return;
    
    if(TimeSecond>59){
            TimeMinute++;
            TimeSecond=0;
    }
    if(TimeMinute>59){
            TimeMinute=0;
    }
    if(TimeSecond<10){
            TS="0"+TimeSecond;
    }else{
            TS=TimeSecond;
    }
    if(TimeMinute<10){
            TM="0"+TimeMinute;
    }else{
            TM=TimeMinute;
    }
    // default won't tip user to savedraft
    if(TimeMinute>=6){
        if(confirm(tips_confirm_saveto_draft)){
            compose_send('savetodraft.x');
        }else{
            initComposeTimer();
        }
    }
    //
    
    var timeoutObj = document.getElementById("TIMEOUT");
    if (timeoutObj) {
        timeoutObj.childNodes[0].nodeValue="["+TM+":"+TS+"]";
        TimeSecond++;
    }
}

function initComposeTimer() {
    TimeSecond=0;
    TimeMinute=0;
}

//---------------------------------------- 编辑器 ------------------------------------
function CEditorMan() {
}
CEditorMan.prototype = {
    getHtmlEditorTextValue : function(){
        var obj = document.getElementById('htmlletter');
        var sContent = "";
        if (obj && obj.text) {
            sContent = obj.text;
        }
        else {
            if (obj && obj.html) {
                sContent = obj.html;
                sContent = Html2Text(sContent);
            }
            else {
                var htmlletter = window.frames["htmlletter"];
                if (!htmlletter) return;
                var fContent = htmlletter.frames["HtmlEditor"];
                if (!fContent) return;

                if (IsIE) {
                    sContent = fContent.document.getElementsByTagName("BODY")[0].innerText;
                }
                else {
                    sContent = fContent.document.getElementsByTagName("BODY")[0].innerHTML;
                    sContent = Html2Text(sContent);
                }
            }
        }
        return sContent;
    },
    getHtmlEditorHtmlValue : function(){
        var obj = document.getElementById('htmlletter');
        var sContent = "";
        if (obj && obj.html) {
            sContent = obj.html;
        }
        else {
            var htmlletter = window.frames["htmlletter"];
            if (!htmlletter) return;
            var fContent = htmlletter.frames["HtmlEditor"];
            if (!fContent) return;

            sContent = fContent.document.getElementsByTagName("BODY")[0].innerHTML;
        }
        return sContent;
    },
    setHtmlEditorHtmlValue : function( theValue ) {
        var editor = document.getElementById("htmlletter");
        if (editor && editor.text) {
            editor.text = theValue;
        }
        else {
            var htmlletter = window.frames["htmlletter"];
            if (!htmlletter) return;
            var fContent = htmlletter.frames["HtmlEditor"];
            if (!fContent) return;

            var eBody = null;
            if(fContent.document.getElementsByTagName("BODY").length>0) {
                eBody = fContent.document.getElementsByTagName("BODY")[0];
            }
            if(eBody){
                eBody.innerHTML = theValue;
            }
            if (IsIE) {
                fContent.document.designMode="on";
            }
            else {
                window.setTimeout(
                        function(){
                            var f = window.frames["htmlletter"].frames["HtmlEditor"];
                            f.document.designMode = "on";
                            f.document.execCommand("useCSS",false, true);
                        }, 10);
            }
        }
    }
};
var editorMan = new CEditorMan();

//-----------------------------------------------正文管理---------------------------------
function CTextMan() {
}
CTextMan.prototype = {
    getOriginalTextValue : function() {
        return document.sendmail.htext.value;
    },
    setOriginalTextValue : function(value) {
        document.sendmail.htext.value = value;
    },
    getMailContent : function() {
        if ( ishtml ) {
            return editorMan.getHtmlEditorHtmlValue();
        }else{
            var obj = document.getElementById("lettercontent");
            if ( !obj ) {
                alert("error");
            }
            else{
                return obj.value;
            }
        }
        return "";
    },
    setMailContent : function(content) {
        if (ishtml) {
            editorMan.setHtmlEditorHtmlValue(content);
        }
        else {
            var obj = document.getElementById("lettercontent");
            if (obj) {
                obj.value = content;
            }
        }
    }
};
var textMan = new CTextMan();

function recordOriginalText(value) {
    if (ishtml) {
        setOriginalTmpLetterHtml(value);
    }
    else {
        originalTextContent = value;
        setOriginalTmpLetterHtml(Text2Html(value));
    }
}

//-----------------------------------------------回复标记管理---------------------------------
//  在mail.htm的tempDataFrame frame的GE.replyflag记录了回复标记
//  在读信页面点回复时, 设置replyflag为true
//  在写信页面, 如果replyflag为true时, 表示是回复操作中第一次进入邮件编辑界面, 需要给原文加上blockquote和oriMsg
//  加好后需要设置replyflag为false

function ReplyMan() {
    this.oriMsgObj = null;
    this.blockquoteObj = null;
    this.bqTailObj = null;
    this.tmpdataFrame = null;
}

ReplyMan.prototype = {
    getOriMsgSeperator : function(ishtml) {
        if (!this.oriMsgObj) return "";
        if (ishtml) {
            return this.blockquoteObj.innerHTML;
        }
        else {
            return this.oriMsgObj.innerText;
        }
    },
    getOriMsgSeperatorTail : function(ishtml) {
        if (!ishtml || !this.bqTailObj) return "";

        return this.bqTailObj.innerHTML;
    },
    getBlockquoteInfo : function() {
        if (!this.blockquoteObj) return "";

        return this.blockquoteObj.innerHTML;
    },
    setInfo : function() {
        this.oriMsgObj = document.getElementById("oriMsg");
        this.blockquoteObj = document.getElementById("blockquote");
        this.bqTailObj = document.getElementById("blockquoteTail");
        this.tmpdataFrame = parent["tempDataFrame"];
        if (!this.tmpdataFrame && opener) this.tmpdataFrame = opener.parent["tempDataFrame"];
    },
    hasReplyOrForwardFlag : function() {
        return (typeof(document.sendmail.setreplyflag)!="undefined"
                || typeof(document.sendmail.setforwardflag)!="undefined");
    },
    needAddOriMsgSeperator : function() {
        this.setInfo();
        if (this.oriMsgObj && this.blockquoteObj) {
            return (!this.tmpdataFrame || !this.tmpdataFrame.GE || this.tmpdataFrame.GE.replyflag || this.tmpdataFrame.GE.forward_flag);
        }
        return false;
    },
    recordAddedSeperator : function() {
        try {
          this.tmpdataFrame.GE.replyflag = false;
        } catch (E) {
        }
    }
}

var replyMan = new ReplyMan();


//-----------------------------------------------Signature---------------------------------


// ------------------------------------------------------------------------------
// 增加签名档到正文
// ------------------------------------------------------------------------------
//  签名档处理逻辑
//  1. 新编辑一封信时将默认签名档加入到"指定位置"
//      指定位置:
//          1) 新信时, 放在末尾
//          2) 回复信时, 根据用户配置来决定是放在 引用原信正文前 还是 整封信的末尾
//  2. 在编辑过程中切换签名档
//  3. 回到编辑页面时恢复用户选中的签名档
//

var splitStr = "----------------------------";
var curSignStr;

function splitBr(){
    if ( ishtml ) return "<br><br>";
    else return "\r\n";
}
function CSign(id, mode,name,content) {
    this.id = id;
    this.mode = mode;
    this.name = name;
    this.content = unescape(content);
}
function CSignMan() {
    this.signList = [];

    this.prevSignIdx = -1;
    this.signSelector = null;
    
    this.htmlSignPattern = /(<span id="*spnEditorSign"*>.*?<\/span>)|$/i ;
    this.signature_position = "";
}
CSignMan.prototype = {
    addItem : function(item) {
        this.signList.push(item);
    },
    getItemCnt : function() {
        return this.signList.length;
    },
    getSignObj : function(signId) {
        var signCnt = this.signList.length;
        for (var i=0; i<signCnt; i++) {
            if (signId==this.signList[i].id) {
                return this.signList[i];
            }
        }
        return null;
    },
    getSignObjContent : function(signId) {
        var obj = this.getSignObj(signId);
        if (obj) {
            return obj.content;
        }
        return "";
    },
    setSignPosition : function(pos) {
        this.signature_position = pos;
    },
    setSignSelector : function(domId) {
        if (!domId) {
            domId = "sign";
        }
        this.signSelector = document.getElementById(domId);
        return this.signSelector;
    },

    ///////////////////////////////////////////
    isHasSignature : function(con) {
        return (ishtml && (/(<span id="*spnEditorSign"*>.*?<\/span>)/i).exec(con));
    },
    getUserSelectedSignId : function(domId) {
        if (this.setSignSelector(domId)) {
            var selIdx = this.signSelector.selectedIndex;
            var signId = this.signSelector.options[selIdx].value - 1;
            if (!signId && signId!=0) signId = -1;
            return signId;
        }
        return -1;
    },
    wrapHtmlSpan : function(signContent) {
        return '<span id="spnEditorSign">' + signContent + '</span>';
    },
    getSignContent : function(signId, ishtml) {
        if (signId<0) return "";

        var signObj;
        var signCnt = this.signList.length;
        for (var i=0; i<signCnt; i++) {
            if (signId == this.signList[i].id) {
                signObj = this.signList[i];
            }
        }
        if (!signObj) return "";
        
        var signStr = this.formatSignContent(signObj.content, signObj.mode, ishtml);
        signStr = splitBr() + splitStr + splitBr() + signStr + splitBr();
        if (ishtml) signStr = this.wrapHtmlSpan(signStr);
        return signStr;
    },
    formatSignContent : function(signStr, signMode, ishtml) {
        var res = signStr;
        if ( ishtml && signMode=="0") {
            res = signStr.replace(/\n/g, "<br>");
        }//if
        else if ( signMode == "1" ) {
            res = Html2Text( res );
        }
        return res;
    },
    changeSign : function() {
        var curSignId = this.getUserSelectedSignId();
        var curSignContent = this.getSignContent(curSignId, ishtml);
        this.setEditorSignContent(ishtml, curSignContent);
        this.prevSignIdx = curSignId;
    },
    setEditorSignContent : function(ishtml, newSignContent) {
        if (ishtml) {
            var con = textMan.getMailContent();
            if (!newSignContent) {
                newSignContent = this.wrapHtmlSpan("");
            }
            var signPos = con.search(this.htmlSignPattern);
            if (signPos>=0 && signPos<con.length) {
                con = con.replace(this.htmlSignPattern, newSignContent);
            }
            else {
                if (replyMan.hasReplyOrForwardFlag() && this.signature_position=="1") {
                    var msgSeperator = replyMan.getOriMsgSeperator(ishtml);
                    var pos = con.indexOf(msgSeperator);
                    var left = con.substr(0, pos);
                    var right = con.substr(pos , con.length - pos);
                    con = left + newSignContent + right;
                }
                else {
                    con += newSignContent;
                }
            }
            editorMan.setHtmlEditorHtmlValue(con);
        }
        else {
            var obj = document.getElementById("lettercontent");
            if (obj) {
                var changed = false;
                if (this.prevSignIdx>=0) {
                    var oldSignContent = this.getSignContent(this.prevSignIdx, ishtml);
                    if (oldSignContent.length>0) {
                        obj.value = obj.value.replace(oldSignContent, newSignContent);
                        changed = true;
                    }
                }
                if (!changed) {
//                    obj.value = obj.value + newSignContent;
                    this.appendTextSign(obj, newSignContent);
                }
            }
        }
    },
    appendTextSign : function(letterContentObj, signContent) {
        if (replyMan.hasReplyOrForwardFlag() && this.signature_position=="1") {
            replyMan.setInfo();
            var oriMsgInfo = replyMan.getOriMsgSeperator(ishtml);
            var con = letterContentObj.value;
            var pos = con.indexOf(oriMsgInfo);
            if (-1 == pos) {
                // 没有原信信息
                pos = con.length;
            }
            var left = con.substr(0, pos);
            var right = con.substr(pos, con.length - pos) + replyMan.getOriMsgSeperatorTail(ishtml);
            letterContentObj.value = left + signContent + right;
        }
        else {
            letterContentObj.value = letterContentObj.value + signContent;
        }
    },
    clearSign : function() {
        var signStr = "";
        if (ishtml) signStr = this.wrapHtmlSpan(signStr);
        this.setEditorSignContent(ishtml, signStr);
    },
    changeHtmlStatusAfterClear : function() {
        var oldSignContent = this.getSignContent(this.prevSignIdx, ishtml);
        if (ishtml == 1) {
            // 已切换到html模式
            if (!oldSignContent) oldSignContent = this.wrapHtmlSpan(oldSignContent);
            var con = textMan.getMailContent();
            var signPos = con.search(this.htmlSignPattern);
            if (signPos>=0 && signPos<con.length) {
                con = con.replace(this.htmlSignPattern, oldSignContent);
            }
            else {
                if (replyMan.hasReplyOrForwardFlag() && this.signature_position=="1") {
                    var oldMsgSeperator = replyMan.getOriMsgSeperator(0);
                    var pos = con.indexOf(oldMsgSeperator);
                    var left = con.substr(0, pos);
                    var right = con.substr(pos + oldMsgSeperator.length, con.length - pos - oldMsgSeperator.length)
                            + replyMan.getOriMsgSeperatorTail(1);
                    var newMsgSeperator = replyMan.getOriMsgSeperator(1);
                    con = left + oldSignContent + newMsgSeperator + right;
                }
                else {
                    con += oldSignContent;
                }
            }
            editorMan.setHtmlEditorHtmlValue(con);
        }
        else {
            var obj = document.getElementById("lettercontent");
            if (obj) {
                this.appendTextSign(obj, oldSignContent);
            }
        }
    },
    initSignature : function(originalContent, preContent) {
        if (!replyMan.hasReplyOrForwardFlag() && signMan.isHasSignature(originalContent)) return preContent + originalContent;

        var curSignIdx = this.getUserSelectedSignId();
        var curSignContent = this.getSignContent(curSignIdx, ishtml);
        this.prevSignIdx = curSignIdx;

        if (ishtml) {
            originalContent = Text2Html(originalContent);
        }
        return preContent + (this.signature_position == "1" ? curSignContent + originalContent : originalContent + curSignContent);
    },
    appendTextSignContent : function(oldText) {
        var newText = "";
        if (replyMan.hasReplyOrForwardFlag() && replyMan.needAddOriMsgSeperator()) {
            var enterArea = splitBr() + splitBr();
            var originalContent = replyMan.getOriMsgSeperator(ishtml) + splitBr() + oldText + replyMan.getOriMsgSeperatorTail(ishtml);
            newText = this.initSignature(originalContent, enterArea);
        } else if (!replyMan.hasReplyOrForwardFlag()) {
            newText = this.initSignature("", oldText + splitBr() + splitBr());
        }
        return newText;
    },
    appendHtmlSignContent : function(oldText) {
        var newText = "";
        if (replyMan.hasReplyOrForwardFlag() && replyMan.needAddOriMsgSeperator()) {
            var enterArea = splitBr() + splitBr();
//            var originalContent = replyMan.getBlockquoteInfo() + replyMan.getOriMsgSeperator(ishtml) + splitBr() + Text2Html(oldText);
            var originalContent = replyMan.getOriMsgSeperator(ishtml) + splitBr() + Text2Html(oldText) + replyMan.getOriMsgSeperatorTail(ishtml);
            newText = this.initSignature(originalContent, enterArea);
        } else {
            newText = this.initSignature("", oldText + splitBr());
        }
        return newText;
    }

};
var signMan = new CSignMan();

//----------------------------------------Layer function------------------------------------

function isVisible(name)
{
  var layer = editor_getLayer(name);
  if ( !layer ) return false;
  return layer.style.display != "none";
}

//---------------------------------------- 信纸 ------------------------------------

var stationeryoption="";

function initStationeryOption(theContent)
{
    stationeryoption = theContent;
}


//---------------------------------------- 编辑页面 ------------------------------------
/*
正文内容保存在:     <input type="hidden" id="htext" name="text" value="" />

编辑器
    纯文本方式，内容放在: <textarea id="lettercontent" name="text2" ...
    html方式，　内容放在:  <iframe id="htmlletter" name="htmlletter" ...


页面处理逻辑:
    1. 进入编辑页面
        1) 进入后会将原文内容设置到  中
        2) 调用 adjustEditorAtStart 根据html状态:
            给原文加quota(回复操作)
            加签名档
            将加了签名档的内容设置到编辑器中
            切换编辑器

    2. 切换编辑器状态
        从lettercontent中获取内容
        去掉内容中的签名档内容
        切换编辑器
        补回签名档
*/
function CComposerMan() {
    this.editorObj = null;
    this.sHtml = "";
}
CComposerMan.prototype = {
    setInfo : function(obj, sHtml) {
        this.editorObj = obj;
        this.sHtml = sHtml;
    },
    changeEditorEx : function(e) {
        var eventTarget = null;
        if(IsIE) eventTarget = event.srcElement;
        else eventTarget = e.target;

        if ( ishtml ) {
            if (!confirm(tips_html_convert_to_text)) return;
            signMan.clearSign();
            ishtml = false;
            this.change2Textor();
            signMan.changeHtmlStatusAfterClear();
        }
        else {
            signMan.clearSign();
            ishtml = true ;
            this.change2Htmlor();
            signMan.changeHtmlStatusAfterClear();
        }
    },
    change2Textor : function() {
        var lettercontent = document.getElementById("lettercontent");
        if (lettercontent) {lettercontent.value = editorMan.getHtmlEditorTextValue();}

        editor_hideLayer("trComposeHtml");
        editor_hideLayer("spanHtmlEditorInfo");
        editor_hideLayer("spStationery");

        editor_showLayer("trComposeTxt");
        editor_showLayer("chkHtmlMessage_text");
        editor_showLayer("lblHtmlMessage_text");
        editor_showLayer("spanNormalEditorInfo");
    },
    change2Htmlor : function() {
        var lettercontent = document.getElementById("lettercontent");
        editorMan.setHtmlEditorHtmlValue( Text2Html(lettercontent.value) );


        this.change2HtmlorOnly();
    },
    change2HtmlorOnly : function() {
        setStationerySelectList();

        editor_hideLayer("trComposeTxt");
        editor_hideLayer("spanNormalEditorInfo");
        editor_hideLayer("chkHtmlMessage_text");
        editor_hideLayer("lblHtmlMessage_text");

        editor_showLayer("trComposeHtml");
        editor_showLayer("spanHtmlEditorInfo");
        editor_showLayer("spStationery");

        function setStationerySelectList() {
            var select = "<SELECT name='stationery' id='stationery'><OPTION value='0' selected>" + info_not_use_stationery + "</OPTION>";
            select += stationeryoption;
            select += "</SELECT>";
            var spanStationery = document.getElementById("spanStationery");
            if (spanStationery) {
                spanStationery.innerHTML = info_compose_stationery + select;
            }
            else {
                var s =  document.sendmail.stationery;
                s.outerHTML = select;
            }
        }
    },
    adjustEditorAtStart : function() {
        var textAppendedSign = appendSignatureAtStart();

        var htmlletter = window.frames["htmlletter"];
        if (!htmlletter) return;
        var editorObj = htmlletter.frames["HtmlEditor"];
        this.setInfo(editorObj, textAppendedSign);
        this.fSetContent();

        if (ishtml) {
//            composerMan.change2Htmlor();
            composerMan.change2HtmlorOnly();
        }

        function appendSignatureAtStart() {
            var oldText = textMan.getOriginalTextValue();
            var newText = "";
            if (!ishtml) {
                newText = signMan.appendTextSignContent(oldText);
                textMan.setMailContent(newText);
            }
            else {
                newText = signMan.appendHtmlSignContent(Text2Html(oldText));
            }
            textMan.setOriginalTextValue(newText);
            recordOriginalText(newText);
            replyMan.recordAddedSeperator();

            return newText;
        }
    },
    fSetContent : function() {
        var obj = this.editorObj;
        if (!obj) return;
        var sHtml = this.sHtml;

        var eBody = null;
        try {
            if (obj.document.getElementsByTagName("BODY").length>0) {
                eBody = obj.document.getElementsByTagName("BODY")[0];
            }
            if (eBody) {
                eBody.innerHTML = sHtml;
            }
            obj.parent.fSetEditable();
            setOriginalTmpLetterHtml(sHtml);
            if (IsIE && obj.document.body.innerHTML) ; // IE5 显示空白Bug解决方法 Surj
        } catch(e) {
            // Have to use settimeout, if not, the reply content can't be display
            // the reason maybe: when reply's content is set by js, the content maybe still not set now
            window.setTimeout("composerMan.fSetContent()", 100);
        }
    }
};
var composerMan = new CComposerMan();

// 记录信件初始的html内容
function setOriginalTmpLetterHtml(str) {
    setInnerHTML("originalHtmlcontent", str);
}

function setInnerHTML(destObjId, innerHtml) {
    var destObj = document.getElementById(destObjId);
    if (!destObj) return;
    destObj.innerHTML = innerHtml;
}

function getOriginMailContent(con) {
    if ( !signMan.isHasSignature(con) )   return con;
    var ori_text = con ;
    
    var offset = -1;
    var re = new RegExp((splitBr() + splitStr), "gi");
    offset = con.search(re);
    if ( offset != -1 ) { ori_text = con.substring(0,offset); }
    else {
        offset = con.lastIndexOf(splitStr);
        if (offset != -1 )  { ori_text = con.substring(0,offset); }
    }
    return ori_text;
}
function clearSign() {
    if (ishtml) {
        editorMan.setHtmlEditorHtmlValue(textMan.getMailContent().replace(new RegExp('id="*spnEditorSign"*', "ig"), ""));
    }
}
function composeGenPostSignData(){
    // 签名档已加入到信件正文, 把签名档选择框的值改成无意义的值
    var signObj = document.getElementById("sign");
    if (!signObj || signObj==null) return;
    signObj.selectedIndex = 0 ;
    signObj.options[signObj.selectedIndex].value = "-1";
    document.sendmail.action += "&sign=0";
    //
}
function Text2Html(con)
{
    if ((con.search(/<br>/gi)<0) && (con.search(/<br\/>/gi)<0) && (con.search(/<\/br>/gi)<0) ) {
        con = con.replace(/\&/g, "&amp;").replace(/[ \t]/g, "&nbsp;")
                .replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/"/g, "&quot;")
                .replace(/\r\n/g, '\n').replace(/\r/g, '\n').replace(/\n/g, '<br>');
    }
    return con;
}
function Html2Text(str)
{
    var tag="";
    var result="";
    var str2="";
    var l = "<";
    var r = ">";
    var offset1=-1;
    var offset2=-1;
    
    var temp="";
    
    str2 = str;
    
    do {
    offset1 = str2.indexOf(l);
    offset2 = str2.indexOf(r);
    
    if ( offset1 > 0 )
        { 
            temp = str2.substring(0,offset1);
            result += temp; 
            str2 = str2.substring(offset1,str2.length);
            continue;
        }
    if ( offset1 == -1 )
    {
        result +=   str2;
        break;
    }
    
    tag = str2.substring(offset1+1,offset2);
    str2 = str2.substring(offset2+1,str2.length);
    tag.toLowerCase();
    if ( tag == "br" || tag =="BR" )
        { result += "\r\n";  }
    else
        tag = "";
    }    
    while ( 1 );

    result = result.replace(/\&nbsp;/gi, ' ')
            .replace(/\&gt;/gi, '>').replace(/\&lt;/gi, '<')
            .replace(/\&quot;/gi,'"').replace(/\&amp;/gi, '&');
    
//    while ( result.indexOf("&nbsp;") != "-1" ) result = result.replace("&nbsp;"," ");
    return result;
}

//------------------- reminder ----------------
function remind() {
    var textLetter = document.getElementById("lettercontent");
    var originalHtmlTmpObj = document.getElementById("originalHtmlcontent");
    var frameHtmlLetter = document.getElementById("htmlletter");
    if (!frameHtmlLetter || !textLetter || !originalHtmlTmpObj) return false;  //页面还没有load完就关闭编辑界面了

    var frameDoc = frameHtmlLetter.contentDocument;
    if (!frameDoc) {
        frameDoc = frameHtmlLetter.contentWindow.document;
    }
    if (!frameDoc ||
        !frameDoc.getElementById("HtmlEditor") ||
        !frameDoc.getElementById("HtmlEditor").contentWindow    ) {
        return false;
    }
    var htmlLetter = frameDoc.getElementById("HtmlEditor").contentWindow;
    var originalHtmlcontent = "";
    if (originalHtmlTmpObj) originalHtmlcontent = originalHtmlTmpObj.innerHTML;
    var mailTo = document.getElementById("idTo");
    var mailCc = document.getElementById("idCc");
    var mailBcc = document.getElementById("idBcc");

    if (changed ||
        mailTo.value != document.getElementById("originalTo").value ||
        mailCc.value != document.getElementById("originalCc").value ||
        mailBcc.value != document.getElementById("originalBcc").value ||
        (!ishtml && textLetter.value != originalTextContent) ||
        (ishtml && htmlLetter.document.getElementsByTagName("BODY")[0].innerHTML != originalHtmlcontent)) {
      return realClose;
    }
    else return false;
}

//----------------------------------------Set Value For Send Mail Function ----------------

function SetVals() {
//  根据ishtml获得正文的内容
   
   //alert("h");

    if(!ishtml)
    {
        var obj = document.getElementById("lettercontent");
        document.sendmail.htext.value = obj.value;
       if (document.sendmail.chkHtmlMessage_text.checked)
            document.sendmail.chkHtmlMessage.value="y";
       else
            document.sendmail.chkHtmlMessage.value="";
    }
    else
    {
        document.sendmail.htext.value = editorMan.getHtmlEditorHtmlValue();
        document.sendmail.chkHtmlMessage.value="y";
    }
}

//-------------------------------------------------------------------

function Preview()      {
  if(!ishtml)
  {
        ViewAsHtml();
  }
  else
  {
        var frm = document.frmHtmlPreview;
        frm.content.value = editorMan.getHtmlEditorHtmlValue();
        frm.stationery.value = document.sendmail.stationery.value;
        frm.ishtml.value='1';
        frm.submit();
  }
}
function ViewAsHtml() {
    var frmSendmail = document.sendmail;
    if ( !frmSendmail.chkHtmlMessage_text.checked ) {
        alert(tips_choose_html_format);
    }
    else
    {
        var frm = document.frmHtmlPreview;
        frm.content.value = document.getElementById("lettercontent").value;
        frm.ishtml.value = '1';
        frm.submit();
    }
}
