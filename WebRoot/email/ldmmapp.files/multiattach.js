try {
    if (!g_get_compose_desthost_loaded) {
        document.write("<script type=\"text/javascript\" src=\"/js/get_compose_desthost.js\"></script>");
    }
}
catch(e) {
    document.write("<script type=\"text/javascript\" src=\"/js/get_compose_desthost.js\"></script>");
}
//本js文件用于compose.htm同时发送多个附件的js函数，支持 IE 和 NNx 系列。
//所有的函数都适用于 IE 和 NNx 系列，直接调用就可以
//函数里面会根据语句判断浏览器类型，然后依据不同的bowser属性增加元素
//主要功能是动态创建 各种表单元素 和 删除表单元素

//    function list
//    ---------------------------------------------------------------------------------------------
//    IsIEBrowser()  返回值: true(IE) false(NNx)
//    exist(fileId)  判断fileId是否已经存在
//    addInputFile(spanId,fileId)  在名为spanId 的元素里面增加序号为index的input file
//    addbr(spanId,brId)  在名为spanId 的元素里面增加id为brId的换行符
//    adddel(spanId,index) 在名为spanId 的元素里面增加序号为index的"删除"按钮，他可以删除相应的input file 和 br
//    addhidden(spanId,hiddenId,value)  在名为spanId 的元素里面增加id为hiddenId，值为value的input type=hidden
//    ---------------------------------------------------------------------------------------------


//search,全局变量
var g_multiattach_loaded = true;
var attaIdx = 0;
var IsIE = IsIEBrowser();
var ishtml; 
var isEnglishStyle = false;

var delText = tips_delete_label;

// 记录附件输入框的数目
var numMulAtta = 0;

function setHtmlStatus( isit ) {
    ishtml = isit;
}
function setIsEnglishStyle(value) {
    if ( value == "0" ) isEnglishStyle = true;  
    if ( isEnglishStyle )   delText = "  Delete";
}

//-------------------------------------------------------------------------------------------------
function IsIEBrowser() {
    return navigator.userAgent.indexOf("MSIE") != -1;
}

function hideObj(objId) {
    var mutiattach = document.getElementById(objId);
    if (mutiattach) mutiattach.style.display = "none";
}

function showObj(objId) {
    var mutiattach = document.getElementById(objId);
    if (mutiattach) mutiattach.style.display = "";
}

// reflect number of attaches
function reflectAttach(step) {
    numMulAtta += step;
    var objId = "mutiattach";
    if (numMulAtta == 0) hideObj(objId);
    else showObj(objId);
}

// 增加附件函数 ()，增加到 idfilespan,基数为 attaIdx 。
function add() {
    reflectAttach(1);
    addfile("idfilespan",attaIdx);
    attaIdx++;
    return false;
}

//----------------------------------------fileexist()----------------------------------------------
function exist(fileId) {
      var file = document.getElementById(fileId);
      if ( file !=null ) {
            if ( file.value != null && file.value != "") {
                var form = document.sendmail;
                for ( var i= 0 ; i < form.elements.length ; i ++ ) {                    
                    if ( form.elements[i].type == "file" && form.elements[i].name != file.name ) {
                        if ( file.value == form.elements[i].value ) {
                            alert(tips_has_attached);
                            return ;
                        }
                    }
                }//for
                checkExistInOldAttachs( file.value );
            }//if
      }
}//end of function

//----------------------------------------addfile(spanId,index)------------------------------------
function addfile(spanId,index)
{
    var strIndex = "" + index;
    var fileId = "attachfile"+ strIndex;
    var brId = "idAttachBr" + strIndex;
    var spaceId = "idAttachSpace" + strIndex;
    var spanObj = document.getElementById(spanId);
    if (spanObj!=null) {
        addInputFile(spanObj,fileId);
        addSpace(spanObj, spaceId);
        adddel(spanId, spanObj, index);
        addbr(spanObj,brId);
    }
}
//-------------------------------------------sub function------------------------------------------
function addInputFile(spanObj,fileId)
{
    if ( spanObj !=null ) {
        var fail = false;
        if ( IsIE ) {
            var fileTag = "<input type='file' id ='" + fileId + "' name='" + fileId + "' size=50 onchange=exist('" + fileId + "')>";
            try
            {
              var fileObj = document.createElement(fileTag); 
              spanObj.appendChild(fileObj);
          }
          catch( e)
          {
            fail = true;
            }
        }//IsIE if

        if (( !IsIE ) || fail) {
            var fileObj = document.createElement("input");
            if ( fileObj != null ) {
                fileObj.type="file";
                fileObj.name = fileId;
                fileObj.id = fileId;
                fileObj.size="50";
                var clickEvent = "exist('" + fileId + "')";
                fileObj.setAttribute("onclick",clickEvent,0);  
                spanObj.appendChild(fileObj);
            }//if fileObj
        }// !IsIE
        
    }//if span
}
function addSpace(spanObj, spaceId)
{
    if (spanObj != null) {
    var fail = false;
        if ( IsIE ) {
            var brTag = "<span id='" + spaceId + "' name='" + spaceId + "' />";
            try
            {
              var brObj = document.createElement(brTag);
              if (brObj) {
                  spanObj.appendChild(brObj);
                  brObj.innerText = " ";
              }
            }
          catch( e)
          {
            fail = true;
            }           
        }
        
        if (( !IsIE ) || fail) {
            var brObj = document.createElement("span");
            if ( brObj !=null ) {
                brObj.name = spaceId;
                brObj.id = spaceId;
                brObj.innerText = " ";
                spanObj.appendChild(brObj);
            }//if
        }
        
    }
}
function addbr(spanObj,brId)
{
    if ( spanObj !=null ) {
    var fail = false;
        if ( IsIE ) {
            var brTag = "<br id='" + brId + "' name='" + brId + "'/>";
            try
            {
              var brObj = document.createElement(brTag);
              spanObj.appendChild(brObj);
            }
          catch( e)
          {
            fail = true;
            }
        }
        
        if (( !IsIE ) || fail) {
            var brObj = document.createElement("br");
            if ( brObj !=null ) {
                brObj.name = brId;
                brObj.id = brId;
                spanObj.appendChild(brObj);
            }//if
        }
            
    }//if
}

function adddel(spanId, spanObj,index)
{
    
    var strIndex = "" + index;
    var delId = "idAttachOper" + strIndex;
    if ( spanObj != null ) {
    var fail = false;
        if ( IsIE ) {
            var tag = "<input type='button' class='Button' id='" + delId + "' onclick=delfile('" + spanId + "',"+strIndex+") value='" + delText + "' />";
            try
            {
              var delObj = document.createElement(tag);
              spanObj.appendChild(delObj);
        //delObj.innerText = delText;
            }
          catch( e)
          {
            fail = true;
            }
        }// Is IE
        
        if (( !IsIE ) || fail) {
            var delObj = document.createElement("input");
            if ( delObj != null ) {
                delObj.type = "button";
                delObj.className = "Button";
                delObj.name = delId;
                delObj.id = delId;
                delObj.value = delText;
                delObj.align="bottom";
                var clickEvent = "return delfile('" + spanId + "',"+strIndex+");";
                delObj.setAttribute("onclick",clickEvent);  
                spanObj.appendChild(delObj);
            }//if ( delObj != null )
        }// !IsIE if
        //if (delObj.innerText) delObj.innerText = tips_delete_label;
    }//main if
}


//-------------------------------------------------------------------------------------------------

//---------------------------------------------delete input file-----------------------------------
function delfile(spanId,index)
{
       var strIndex = "" + index;
       var fileId = "attachfile"+ strIndex;
       var brId = "idAttachBr" + strIndex;
       var spaceId = "idAttachSpace" + strIndex;
       var delId = "idAttachOper" + strIndex;
       //first,get the element
       var span = document.getElementById(spanId);
       if ( span == null ) return false;

       var fileObj = document.getElementById(fileId);
       if ( fileObj == null ) return false;

       var brObj = document.getElementById(brId);
       if ( brObj ==null ) return false;

       var delObj = document.getElementById(delId );
       if ( delObj == null ) return false;

       //second,create the replace element
       var temp= document.createElement("<span>");
       //third,replace it
        span.replaceChild(temp,fileObj);
        var spaceObj = document.getElementById(spaceId);
        if (spaceObj!=null) { span.replaceChild(temp,spaceObj);}
        span.replaceChild(temp,brObj);
        span.replaceChild(temp,delObj);
        
      reflectAttach(-1);
        return false;

}



//------------------------------Sync and subit Form ---------------------------------
/*
 *  defHostSubmitTo_应该是$web_urlprefix$，由cgi输出到页面
 *  hostUser应该是$REMOTE_IP$，由cgi输出到页面;
 */
var defHostSubmitTo_ = "";
var hostUser_ = "";
var web_urlprefix_ = "";
function initComposeHostInfo( hostSubmitTo, hostUser )
{
    defHostSubmitTo_ = hostSubmitTo;
    hostUser_ = hostUser;
}
function setWebUrlPrefix( prefix )
{
    web_urlprefix_ = prefix;
}

function hasInputType(frm, type) {
    var elements = frm.elements;
    var elementCnt = elements.length;
    for (var i=0; i<elementCnt; i++) {
        var objType = elements[i].type;
        if (objType!=null && objType!="" && type==objType) {
            return true;
        }
    }
    return false;
}

/*
 *  actiontype由用户点击某个按钮时，由那个按钮的onclick函数设置
 */
function getComposeSubmitAction( actiontype )
{
    var actionvalue = defHostSubmitTo_;
    if ( actiontype=="send.x" || actiontype=="savetodraft.x" || actiontype=="timeset.x"
        || actiontype=="attach_from_netfdr.x" || actiontype=="attach_from_remote.x"
        || actiontype=="sendaddr.x" ) {
        var destHost = getComposeDestHost( hostUser_ );
        if (destHost!=null && destHost != "") {
            //  如果IP在某些范围内并且是发送才提交到atta_saver
            //  因为atta_saver会有定期删文件的问题，所以存原稿和定时发信操作仍然是提交到北京那台比较慢的机器。
            //  应该提示用户最后才粘贴附件，这样可以节省用户的时间
            actionvalue = destHost + "/coremail/cgi/attasaver?";
        }
        else {
            //  如果有附件需要上传, 才需要提交到attachfapps
            var hasFileInput = hasInputType(document.sendmail, "file");
            if (hasFileInput) {
                actionvalue += web_urlprefix_ + "/coremail/cgi/attachfapps?";
                document.sendmail.encoding = "multipart/form-data";
            }
            else {
                actionvalue += web_urlprefix_ + "/coremail/fcg/ldmmapp?compose_send=y";
                document.sendmail.encoding = "application/x-www-form-urlencoded";
            }
        }
    }
    else {
        actionvalue += web_urlprefix_ + "/coremail/cgi/attachfapps?";
    }
    return actionvalue;
}
//用户没有写主题的时候 提示用户
/*   
    if (checkSubject())     {
        document.sendmail.submit();
        return true;
    }else{
        return false;
    }
    在 showheader.htm 处理 
*/

function checkSubject(){
    var obj = document.sendmail.subject;
    //alert( obj.value );
    if ( obj.value == "" || !obj.value ) { 
        if  ( confirm( tip_no_subject ) ) {
            obj.value = tip_no_subject_label ;
            return true;
        }
        else {
            return false;
        }
    }
    return true;
}
function getAddrListFrame() {
    var o = parent.addrListFrame;
    if (!o) {
        o = parent.parent.addrListFrame;
    }
    if (!o) {
        o = parent.parent.parent.addrListFrame;
    }
    if (!o) {
        o = parent.parent.parent.parent.addrListFrame;
    }
    return o;
}
function checkReceiptInAddressbook()
{
    var opObj = document.getElementById("addrs2addrbook");
    if (!opObj) return;

    var dataFrame = getAddrListFrame();
    if (!dataFrame) return;
    var alladdr = dataFrame.addritems;
    if (!alladdr) return;
    
    var addrsNotIn = "";
    // TODO:    判断收件人是否已经在地址本里了, 如果有收件人不在地址本里了, 则设置addrs2addrbook的值为不在地址本里的收件人, 收件人之间用","连接
//  var receipt = document.sendmail.to.value + "," + document.sendmail.cc.value + "," + document.sendmail.bcc.value;
//  var receipts = receipt.split(",");
    if (addrsNotIn != "") {
        opObj.name = "tonr_tonr_addrs2addrbook";
        opObj.value = addrsNotIn;
    }
}

function setRealClose(rClose) {
    realClose = rClose;
}

var autoCompleteAtServer = false;
function setAutoCompleteServer(ac) {
    autoCompleteAtServer = (ac>0);
}

/*StripedByPackage*/

/**
 * Reference: Sandeep V. Tamhankar (stamhankar@hotmail.com),
 * http://javascript.internet.com
 */
function jcv_checkEmail(emailStr, autocomplete) {
    emailStr = emailStr.replace(/(^\s*)|(\s*$)/g, "");
    if (emailStr.length == 0) {
        return true;
    }
    // TLD checking turned off by default
    var checkTLD=0;
    var knownDomsPat=/^(com|net|org|edu|int|mil|gov|arpa|biz|aero|name|coop|info|pro|museum)$/;
    var fullEmailPat = /^\s*\"[^\"]*\"\s*<(.+)@(.+)>\s*$/;
    var emailPat=/^(.+)@(.+)$/;
    var specialChars="\\(\\)><@,;:\\\\\\\"\\.\\[\\]";
    var validChars="\[^\\s" + specialChars + "\]";
    var noQuoteNameEmailPat = /^\s*[^\(\)><@,;:\\\"\.\[\]]*<(.+)@(.+)>\s*$/;  //new RegExp("^\\s*" + validChars + "*\\s*<(.+)@(.+)>\\s*$");
    var quotedUser="(\"[^\"]*\")";
    var ipDomainPat=/^\[(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})\]$/;
    var atom=validChars + '+';
    var word="(" + atom + "|" + quotedUser + ")";
    var userPat=new RegExp("^" + word + "(\\." + word + ")*$");
    var domainPat=new RegExp("^" + atom + "(\\." + atom +")*$");
    var matchArray;
    matchArray=emailStr.match(fullEmailPat);
    if (matchArray==null) {
        matchArray=emailStr.match(noQuoteNameEmailPat);
    }
    if (matchArray==null) {
        matchArray=emailStr.match(emailPat);
    }
    if (matchArray==null && !autocomplete) {
        return false;
    }

    var user = "", domain = "";
    if (matchArray!=null) {
        user=matchArray[1];
        domain=matchArray[2];
    }
    else {
        user = emailStr;
    }
    for (var i=0; i<user.length; i++) {
        if (user.charCodeAt(i)>127) {
            return false;
        }
    }
    if (user.match(userPat)==null) {
        return false;
    }

    if (domain.length>0 || !autocomplete) {
        for (var i=0; i<domain.length; i++) {
            if (domain.charCodeAt(i)>127) {
                return false;
            }
        }

        var IPArray=domain.match(ipDomainPat);
        if (IPArray!=null) {
            for (var i=1;i<=4;i++) {
                if (IPArray[i]>255) {
                    return false;
                }
            }
            return true;
        }

        var atomPat=new RegExp("^" + atom + "$");
        var domArr=domain.split(".");
        var len=domArr.length;
        for (i=0;i<len;i++) {
            if (domArr[i].search(atomPat)==-1) {
                return false;
            }
        }
        if (checkTLD && domArr[domArr.length-1].length!=2 &&
            domArr[domArr.length-1].search(knownDomsPat)==-1) {
            return false;
        }
        return len >= 2;
    }

    return true;
}

var RcptValidator =  {
    preDealCheckRcpts : function(o) {
        if (!o.value) return;

        o.value = this.preDealRcpts(o.value);
    },
    preDealRcpts : function(v) {
        var len = v.length;
        var inQuota = false;
        var mailbox_list = "";
        var res = "";
        var comma = "，";
        var semicolon = "；";
        for (var i=0; i<len; i++) {
            var ch = v.charAt(i);
            if (ch=='"') {
                inQuota = !inQuota;
            }
            if (inQuota) {
                mailbox_list += ch;
                continue;
            }

            if (ch==comma || (ch==comma[0] && i<(len-1) && v.charAt(i+1)==comma[1])) {
                ch = ',';
            }
            else if (ch==semicolon || (ch==semicolon[0] && i<(len-1) && v.charAt(i+1)==semicolon[1])) {
                ch = ';';
            }

            if (ch==',' || ch==';') {
                if (mailbox_list.length > 0) {
                    res += mailbox_list;
                    mailbox_list = "";
                }
                res += ',';
            }
            else if (ch==':') {
                mailbox_list = "";
            }
            else {
                mailbox_list += ch;
            }
        }
        if (mailbox_list.length>0) {
            res += mailbox_list;
        }
        return res;
    },
    chkRcptAddrLists : function(strAddrLists) {
        var strAddrsCR = "";
        var posCur = 0;
        var posDot = 0;
        var addsLen = strAddrLists.length;
        while( posCur < addsLen ) {
            var posD = strAddrLists.indexOf(",", posCur);
            var posE1 = strAddrLists.indexOf("\"", posCur);
            if (posD > -1 && posE1 > -1 ) {
                 if ( posD < posE1 ) {
                     posDot = posD;
                 }
                 else {
                     var posE2 = strAddrLists.indexOf("\"", posE1+1);
                     if (posE2 == -1) return false;
                     posDot = strAddrLists.indexOf(",", posE2+1);
                     if (posDot == -1) posDot = addsLen + 1;
                 }
            }
            else {
                 if ( posD > -1 )
                      posDot = posD;
                 else
                      posDot = addsLen + 1;
            }

            //alert(strAddrLists.substring(posCur, posDot));
            if ( strAddrsCR.length > 0 ) strAddrsCR = strAddrsCR + "\n"
            strAddrsCR = strAddrsCR + strAddrLists.substring(posCur, posDot)

            posCur = posDot + 1;
        }
        //alert(strAddrsCR);

        return this.chkRcptAddrs( strAddrsCR.split("\n") );
    },
    chkRcptAddrs : function(strAddrs, autoComplete) {
        if (typeof(autoComplete)=='undefined') {
            autoComplete = autoCompleteAtServer;
        }
        var addsLen = strAddrs.length;
        if (addsLen == 0) return true;
        if (addsLen == 1) {
            var strAddr = strAddrs[0];
            strAddr = strAddr.replace(/(^\s*)|(\s*$)/g, "");
            if (strAddr == "") return true;
        }
        for (var i = 0; i < addsLen; i++) {
            if (!jcv_checkEmail(strAddrs[i], autoComplete)) {
                return false;
            }
        }
        return true;
    }

    /*StripedByPackage*/
};


function hasWrongRcpts() {
    var gdoc = document;
    var toAddrsObj = gdoc.getElementById("idTo");
    RcptValidator.preDealCheckRcpts(toAddrsObj);
    var ccAddrsObj = gdoc.getElementById("idCc");
    RcptValidator.preDealCheckRcpts(ccAddrsObj);
    var bccAddrsObj = gdoc.getElementById("idBcc");
    RcptValidator.preDealCheckRcpts(bccAddrsObj);
    
    var addrs = "";
    if (toAddrsObj) {
        //if (!chkRcptAddrs(toAddrsObj.value.split(","))) return gLang.compose.info["to"];
        if ( ! RcptValidator.chkRcptAddrLists( toAddrsObj.value ) ) return gLang.compose.info["to"];
    }
    if (ccAddrsObj) {
        //if (!chkRcptAddrs(ccAddrsObj.value.split(","))) return gLang.compose.info["cc"];
        if ( ! RcptValidator.chkRcptAddrLists( ccAddrsObj.value ) ) return gLang.compose.info["cc"];
    }
    if (bccAddrsObj) {
        //if (!chkRcptAddrs(bccAddrsObj.value.split(","))) return gLang.compose.info["bcc"];
        if ( ! RcptValidator.chkRcptAddrLists( bccAddrsObj.value ) ) return gLang.compose.info["bcc"];
    }
    return "";
}

function chkSend() {
    var wrongField = hasWrongRcpts();
    if (wrongField) {
        alert(wrongField + tips_invalid_address);
        return;
    }
    compose_send("send.x");
}

function chkSaveDraft() {
    var wrongField = hasWrongRcpts();
    if (wrongField) {
        alert(wrongField + tips_invalid_address);
        return;
    }
    compose_send("savetodraft.x");
}

function compose_send(optype) {
    setRealClose(false);
    clearSign();
    SetVals();
    var form = document.sendmail;
    form.optype.value = optype;
    // clear the sign field,we needn't it.
    var actionVars = new Array("fid", "order", "desc", "oldmid", "mid", "sid",
                "priority",  
                "stationery", "chkSendImgWithLetter", 
                "setreplyflag", "setforwardflag", 
                "chkHtmlMessage", "ifsavetosent", "return_receipt",
                "draftmid", "draftmsid", "compinfo_urlfilesmode","compinfo_minute");

        var actionurl = getComposeSubmitAction(optype); // 为了南北分流
    //构造 action URL
    for ( var i = 0; i < actionVars.length; i++ ) {
        var varName = actionVars[i];
        //得到里面的sendmail里面的各个元素，然后对其操作
        var srcObj = document.sendmail.elements[ varName ];
        if ( srcObj!=null && srcObj.value!=null ) {
            var type = srcObj.type;
            if (type != null && type != "" && type == "checkbox" ) {
                if ( srcObj.checked ) {
                    actionurl += '&' + varName + '=' + srcObj.value;
                }
            }
            else {
                actionurl += '&' + varName + '=' + srcObj.value;
            }
        }
    }   
    //构造 form 的 action URL
    document.sendmail.action = actionurl + document.sendmail.action;

    var obj = document.getElementById('idOpType');
    obj.name = optype;
    obj.value = 1;
    
    composeGenPostSignData();
    try {
        form.submit();
    }
    catch(ex) {
        if (ex.number) {
            var desc = ex.message ? ex.message : "";
          alert("" + desc + tips_compose_wrong_object);
        }
    }
    return true;
}

//-------------------------------------add hidden function-----------------------------------------
function addhidden_ie(spanId,hiddenId,value) 
{
         var span = document.getElementById(spanId);
         if ( span == null ) return;
         if ( IsIE ) {
        var tag = "<input type=hidden name='" + hiddenId + "' value='" + value + "'>";
        var delObj = document.createElement(tag);
        if ( delObj != null ) {
                span.appendChild(delObj);
        }//if
     }
}

function addhidden(spanId,hiddenId,value)
{
      if ( !IsIE ) {
          var span = document.getElementById(spanId);
          if ( span == null ) return;

          var hiddenObj = document.createElement("input");
          if ( hiddenObj == null ) return;
          hiddenObj.type="hidden";
          hiddenObj.name = hiddenId;
          hiddenObj.id = hiddenId;
          hiddenObj.value = value;
          span.appendChild(hiddenObj);
      }
      else {
          addhidden_ie(spanId,hiddenId,value);
      }
}


//------------------------------------------------------------------------------------------------
function SwitchEditHtml(cond) {
   
    if ( !IsIE ) { 
        alert(tips_htmleditor_not_support_nnx);
        return;
    }
     if( !cond )
    {
      conf = confirm(tips_html_convert_to_text);
      if (! conf)
        return;
      document.sendmail.action+="&sendashtml=0";
    }
    else
    {
      document.sendmail.action+="&sendashtml=1";
    }
    send("");
}

function multiattach_onunload() {
      if(!ishtml)
        return ;
      document.sendmail.lettercontent.value = document.htmlletter.text;
}

function multiattach_onload() {
    if (!ishtml)
        return;
    document.htmlletter.text = document.sendmail.lettercontent.value;
}
function clearhtmlletter()
{
}


function setBrowserType(){
    IsIE = navigator.userAgent.indexOf("MSIE") != -1;
}


//------------------------------这些函数主要是为了照顾旧版本--------------------------
//------------------------------增加 Form hidden For CGI: ldmmapp ------------------------
function multiattach_onunload_old() {
      if(!ishtml)
        return ;
      document.sendmail.lettercontent.value = document.htmlletter.text;
}

function multiattach_onload_old() {
    if (!ishtml)
        return;
    document.htmlletter.text = document.sendmail.lettercontent.value;
}
function SetVals_old() {
    if (!ishtml)
        return;
    document.sendmail.lettercontent.value = document.htmlletter.html;
}

function addhtmlform(){
    var formVars = new Array("subject", "to", "cc", "bcc", "text", "charset",
                    "year", "month", "day", "interval", "repeat", "sendhour",
                    "replacedomain", "compinfo_urlfiles");
    // 已经加到action里的就不加了                   
    //call syncFormVar to add hidden
    for ( var i = 0; i<formVars.length; i++ ) {
        var varName = formVars[i];
        syncFormVar( varName );
    }
}
//----------------增加 form 隐藏区htmlForm------------------------
function addHiddenVar( varName, varValue) {
    // 得到htmlform
    var destFrm = document.htmlform;
    //得到 htmlform 里面的 元素
    var obj = destFrm.elements[ varName ];
    
    if (obj != null ) {
        obj.value = varValue;
    }
    else {
        //增加 input type 到 htmlForm
        addhidden("idAttaSpan",varName,varValue);
    }
}


//-------------------------------------------------------------------------------------------------
function syncFormVar( varName ) {
    var srcFrm = document.sendmail;
    var destFrm = document.htmlform;
    var srcObj = srcFrm.elements[ varName ];
    if (srcObj != null && srcObj.value!=null ) {
        var type = srcObj.type;
        if (type != null && type != "" && type == "checkbox" ) {
            if ( srcObj.checked ) {
                addHiddenVar( varName, srcObj.value );
            }
        }
        else {
            addHiddenVar( varName, srcObj.value );      
        }
    }

}

//----------------------------------------------------------------------------------------------------------
function changeKeyCode(e) {
        var code;
        if (!e)
        {
                e = window.event;
        }
        if (e.keyCode)
        {
                code = e.keyCode;
        }
        else if (e.which)
        {
                code = e.which;
        }
        if (code == 13 ) {      //回车 -> Tab
                if (e.keyCode) e.keyCode=9;     //Tab
                else if (e.which) e.which=9;
        }
}





//---------------------------------deal with old attachment----------------------------------
function oldAttachInfo( idx, name, deleted, encsize )
{
    this.idx_ = idx;
    this.name_ = name;
    this.deleted_ = (deleted=="y");
    this.encsize_ = encsize;
}
var oldAttachInfos = new Array();
function addOldAttachInfo( idx, name, deleted, encsize )
{
    oldAttachInfos[oldAttachInfos.length] = new oldAttachInfo(idx, name, deleted, encsize);
}
function getOldAttachSize()
{
    var oldAttachSize = 0;
    var count = oldAttachInfos.length;
    for (var i=0; i<count; i++) {
        if (!oldAttachInfos[i].deleted_) {
            oldAttachSize += oldAttachInfos[i].encsize_;
        }
    }
    return oldAttachSize;
}
function setOldAttachsStatus()
{
    var i = 0;
    for (i=0; i<oldAttachInfos.length; i++) {
        setOldAttachStatus(i);
    }
}
function getOldAttachInfoIdx( Idx )
{
    var i = 0;
    for (i=0; i<oldAttachInfos.length; i++) {
        if (oldAttachInfos[i].idx_==Idx) {
            return i;
        }
    }
    return -1;
}
function getOldAttachInfoIdxByName( name )
{
    var i = 0;
    for (i=0; i<oldAttachInfos.length; i++) {
        if (name == oldAttachInfos[i].name_) {
            return i;
        }
    }
    return -1;
}
// infoIdx是在数组中的顺序号，Idx是旧附件信息本身的id
function setOldAttachStatus( infoIdx )
{
    if (infoIdx < 0 || infoIdx >= oldAttachInfos.length ) return;
    var Idx = oldAttachInfos[infoIdx].idx_;
    
    var oChk = document.getElementById("attachtodel" + Idx);
    var oName = document.getElementById("oldattaname" + Idx);
    var oInfo = document.getElementById("delOldInfo" + Idx);
    if (oldAttachInfos[infoIdx].deleted_) {
        if (oChk)  oChk.checked = true;
        if (oInfo) oInfo.firstChild.data = tips_cancel_delete_label;
        if (oName) oName.style.textDecoration = "line-through";
    }
    else {
        if (oChk)  oChk.checked = false;
        if (oInfo) oInfo.firstChild.data = tips_delete_label;
        if (oName) oName.style.textDecoration = "";
    }
}
function delOldAttach(Idx)
{
    var gdoc = document;
    if (Idx==null || Idx=="") return;
    var infoIdx = getOldAttachInfoIdx( Idx );
    if (infoIdx < 0 || infoIdx >= oldAttachInfos.length ) return;
    var buttonObj = gdoc.getElementById("delAttach" + Idx);
    if (oldAttachInfos[infoIdx].deleted_) {
        oldAttachInfos[infoIdx].deleted_=false;
        buttonObj.value = tips_delete_label;
    }
    else {
        oldAttachInfos[infoIdx].deleted_=true;
        buttonObj.value = tips_cancel_delete_label;
    }
    setOldAttachStatus( infoIdx );
}
/*
function delOldAttach( Idx )
{
        if (Idx==null || Idx=="") return;
        var infoIdx = getOldAttachInfoIdx( Idx );
        if (infoIdx < 0 || infoIdx >= oldAttachInfos.length ) return;
        if (oldAttachInfos[infoIdx].deleted_) {
                oldAttachInfos[infoIdx].deleted_=false;
        }
        else {
                oldAttachInfos[infoIdx].deleted_=true;
        }
        setOldAttachStatus( infoIdx );
}
*/

function setOldAttachDeleteStatus( Idx, isDeleted )
{
    var infoIdx = getOldAttachInfoIdx( Idx );
    if (infoIdx < 0 || infoIdx >= oldAttachInfos.length ) return;
    oldAttachInfos[infoIdx].deleted_ = (isDeleted=="y");
    setOldAttachStatus( infoIdx );
}

function checkExistInOldAttachs( name )
{
    var infoIdx = getOldAttachInfoIdxByName( name );
    if (infoIdx>=0) {
        alert(tips_existinoldattach);
    }
}

//--------------------------------------------------------
//                  附件大小检查
//  因为拿不到新粘贴附件的文件大小, 所以这里只检查已粘贴附件, url方式的附件和多媒体附件的大小
//--------------------------------------------------------
var attach_size = 0;
var attach_encsize = 0;
var singleAttachLimit = 0;
var totalAttachLimit = 0;
function initAttachSizeInfo(attasize, attaencsize, sattalimit, tattalimit)
{
    attach_size = parseInt(attasize);
    attach_encsize = parseInt(attaencsize);
    singleAttachLimit = parseInt(sattalimit);
    totalAttachLimit = parseInt(tattalimit);
}

function checkSingleUrlFileSize()
{
    var singlesize = 0;
    var totalsize = 0;
    var values = document.getElementById("urlfiles").value;
    if (!values) return 0;
    
    var urlfile = values.split(";");
    var filenames = [];
    for (var i=0; i<urlfile.length; i++) {
        var filename = urlfile[i].split("+");
        if (filename.length > 0) {
            singlesize = parseInt(filename[0]);
            if (singleAttachLimit>0 && singlesize>singleAttachLimit) {
                alert(tips_attachement + filename[1] + tips_single_attachment_too_big);
                return -1;
            }
            totalsize += singlesize;
        }
    }
    
    return totalsize;
}

function getBase64Size( len )
{
    var size = ((len+2)/3 + 1)*4;
    size += (2*size/72 + 1) + 2;
    size += 64; // a little extra room
    return size;
}
function checkAttachSize( newsize )
{
    if (totalAttachLimit>0) {
        var oldsize = getOldAttachSize() + getUrlfileSize() + getMediaFileSize();
        if (((oldsize + newsize)/1024)>totalAttachLimit) {
            alert(tips_all_attachment_too_big);
            return false;
        }
    }
    
    return true;
}
//--------------------------------------------------------
//                  url files
//--------------------------------------------------------
function urlfile_info( url, filename, size, mode, deleted, hashurl )
{
    this.url_ = url;
    this.filename_ = filename;
    this.size_ = size;
    this.mode_ = mode;
    this.deleted_ = deleted;
    this.hashurl_ = hashurl;
}

var urlfile_infos = new Array();
function addUrlfileInfo( idx, url, filename, size, mode, deleted, hashurl )
{
    urlfile_infos[idx] = new urlfile_info( url, filename, size, mode, deleted, hashurl );
}
function opUrlfiles( idx )
{
    if (urlfile_infos[idx].deleted_==1) {
        // 原来是删除状态，现在取消删除
        if (!canUndelete( idx ) ) return;
        urlfile_infos[idx].deleted_=0;
    }
    else {
        urlfile_infos[idx].deleted_=1;
    }
    updateUrlfileStatus( idx );
}
function getUrlfileSize()
{
    var totalsize = 0;
    var count = urlfile_infos.length;
    for (var i=0; i<count; i++) {
        if (urlfile_infos[idx].deleted_ == 0) {
            totalsize += urlfile_infos[idx].size_;
        }
    }
    return totalsize;
}
function updateAllUrlfileStatus()
{
    var i = 0;
    for (i=0; i<urlfile_infos.length; i++) {
        updateUrlfileStatus( i );
    }
}
function canUndelete( idx )
{
    if (idx<0 || idx>=urlfile_infos.length) return true;
    if (urlfile_infos[idx].deleted_==0) return true;
    // todo , check size
    var newsize = urlfile_infos[idx].size_;
    return checkAttachSize(newsize);
}
function updateUrlfileStatus( idx )
{
    if (idx<0 || idx>=urlfile_infos.length) return;
    
    var delstatobj = document.getElementById("urlfile_deleted_" + idx );
    var infoSpan = document.getElementById("idUrlFileInfo_" + idx );
    var opSpan = document.getElementById("urlfile_opinfo_" + idx );
    if (urlfile_infos[idx].deleted_==1) {
        if (delstatobj) delstatobj.value = 1;
        if (infoSpan) infoSpan.style.textDecoration = "line-through";
        if (opSpan) opSpan.firstChild.data = tips_cancel_delete_label;
    }
    else {
        if (delstatobj) delstatobj.value = 0;
        if (infoSpan) infoSpan.style.textDecoration = "";
        if (opSpan) opSpan.firstChild.data = tips_delete_label;
    }
    var modeobj = document.getElementById("urlfile_mode_" + idx + "_" + urlfile_infos[idx].mode_);
    if (modeobj) modeobj.checked = true;
    var urlobj = document.getElementById("urlfile_url_" + idx);
    if (urlobj) urlobj.value = urlfile_infos[idx].hashurl_;
}

// ----------------------------------------------------------------
function setUrlFiles()
{
    var oldvalue = document.getElementById("urlfiles").value;
    if (oldvalue=="") return;
    
    var fileNames = getFileNamesFromUrlFiles( oldvalue );
    var fileinfoSpanObj = document.getElementById("idUrlFileInfospan");
    fileinfoSpanObj.childNodes[0].nodeValue = fileNames;
    
    showUrlFilesArea();
}
function getFileNamesFromUrlFiles( values )
{
    var urlfile = values.split(";");
    var filenames = [];
    for (var i=0; i<urlfile.length; i++) {
        var filename = urlfile[i].split("/");
        if (filename.length > 0) {
            filenames[filenames.length] = filename[filename.length-1];
        }
    }
    return filenames.join(",");
}
function removeAllUrlFiles()
{
    // clear url files
    document.getElementById("urlfiles").value = "";
    document.getElementById("idUrlFileInfospan").childNodes[0].nodeValue="";
    hideUrlFilesArea();
}

function hideUrlFilesArea()
{
    var areaObj = document.getElementById("idUrlFilesArea");
    if (areaObj.style) {
        areaObj.style.display = "none";
    }
    else if (areaObj.display) {
        areaObj.display = "none";
    }
}
function showUrlFilesArea()
{
    var areaObj = document.getElementById("idUrlFilesArea");
    if (areaObj.style) {
        areaObj.style.display = "";
    }
    else if (areaObj.display) {
        areaObj.display = "";
    }
}

function testUrlFiles()
{
    var urlfiles = "aaa/name1;bbb/name2";
    var names = getFileNamesFromUrlFiles(urlfiles);
    alert( urlfiles + " -> " + names);
}


//--------------------------------------------------------
//                  media files
//--------------------------------------------------------
var mediaCount = 0;
var mediaNameS = new Array();
var theDocument;
var theMediaSpan;

function media_info( medianame, mediadesc, size, conttype)
{
    this.name_ = medianame;
    this.desc_ = mediadesc;
    this.size_ = size;
    this.conttype_ = conttype;
    this.deleted_ = 0;
}
var media_infos = new Array();
function addMediafileInfo( idx, medianame, mediadesc, size, conttype )
{
    media_infos[idx] = new media_info( medianame, mediadesc, size, conttype);
}

function getMediaFileSize()
{
    var mediaFileSize = 0;
    var count = media_infos.length;
    for (var i=0; i<count; i++) {
        if (media_infos[i].deleted_ == 0) {
            mediaFileSize += media_infos[i].size_;
        }
    }
    return mediaFileSize;
}
function initMediaInfo( docobj, spanobj )
{
    theDocument = docobj;
    theMediaSpan = spanobj;
}

function getMediaInfoId( Idx ) {
    return "idMediaInfo" + Idx;
}
function getMediaOperId( Idx ) {
    return "idMediaOper" + Idx;
}
function getMediaBrId( Idx ) {
    return "idMediaBr" + Idx;
}
function getMediaNameId( Idx ) {
    return "media_name_" + Idx;
}
function getMediaContentId( Idx ) {
    return "media_content_" + Idx;
}
function getMediaConttypeId( Idx ) {
    return "media_conttype_" + Idx;
}
function getMediaDescId( Idx ) {
    return "media_desc_" + Idx;
}
function genMediaInfo( Idx, theName ) {
    debug( ' gen Attach Info ' + Idx + ', the Name is ' + theName );
    var attaInfoTag = "<span id='" + getMediaInfoId( Idx ) + "'>";
    var attaOperTag = "<span id='" + getMediaOperId( Idx ) + "' onclick='deleteMedia(" + Idx + ");' style='color:blue;cursor:pointer'>";
    var attaBrTag = "<br id='" + getMediaBrId( Idx ) + "'>";
    
    var aInfoElement = document.createElement(attaInfoTag);
    var aOperElement = document.createElement(attaOperTag);
    var brElement = document.createElement(attaBrTag);
    
    idMediaInfoArea.appendChild(aInfoElement);
    idMediaInfoArea.appendChild(aOperElement);
    idMediaInfoArea.appendChild(brElement);
    
    aOperElement.innerText = tips_delete_label;
    var re = /\\\\/g;
    aInfoElement.innerText = theName.replace(re, '\\');
}
function deletenode( P, CID) 
{
    var attas = P.childNodes;
    var i;
    for (i=0; i<attas.length; i++ ) {
        var oChild = P.children(i);
        if (oChild.id == CID) {
            P.removeChild(oChild);
        }
    }
}
function deleteMedia( Idx ) {
    debug('delete ' + Idx + ' : ' + mediaNameS[Idx]);
    mediaNameS[Idx] = "";
    deletenode( idMediaInfoArea, getMediaInfoId( Idx ));
    deletenode( idMediaInfoArea, getMediaOperId( Idx ));
    deletenode( idMediaInfoArea, getMediaBrId( Idx ));
    deletenode( theMediaSpan,    getMediaNameId( Idx ));
    deletenode( theMediaSpan,    getMediaContentId( Idx ));
    deletenode( theMediaSpan,    getMediaConttypeId( Idx ));
    
    if (Idx>=0 && Idx<media_infos.length) {
        media_infos[Idx].deleted_ = 1;
    }
}
function uploadMedia( Idx, recorder ) {
    // todo: check attachment's size
    var mediaSize = recorder.Content.length;
    if (singleAttachLimit>0 && mediaSize>singleAttachLimit) {
        alert(tips_attachement + tips_single_attachment_too_big);
        return -1;
    }
    if (!checkAttachSize(mediaSize)) return -2;
    
    
    var mediaNameTagName = getMediaNameId( Idx );
    var mediaContentTagName = getMediaContentId( Idx );
    var mediaConttypeTagName = getMediaConttypeId( Idx );
    var mediaDescTagName = getMediaDescId( Idx );
    //Tag definition
    var mediaNameTag = "<input type='hidden' name='" + mediaNameTagName + "' id='" + mediaNameTagName + "'>";
    var mediaContentTag = "<input type='hidden' name='" + mediaContentTagName + "' id='" + mediaContentTagName + "'>";
    var mediaConttypeTag = "<input type='hidden' name='" + mediaConttypeTagName + "' id='" + mediaConttypeTagName + "'>";
    var mediaDescTag = "<input type='hidden' name='" + mediaDescTagName + "' id='" + mediaDescTagName + "'>";
    //Media Name
    var mediaNameObj = theDocument.getElementById( mediaNameTagName );
    if (mediaNameObj == null ) {
        mediaNameObj = theDocument.createElement( mediaNameTag );
        theMediaSpan.appendChild(mediaNameObj);
        debug('idMediaSpan.appendChild(mediaNameObj);');
    }
    var mediaName = recorder.OutputFilePath;
    mediaNameObj.value = mediaName;
    debug(mediaNameTagName + ',' + mediaNameObj.value);
    //Media Desc
    var mediaDescObj = theDocument.getElementById( mediaDescTagName );
    if (mediaDescObj == null ) {
        mediaDescObj = theDocument.createElement( mediaDescTag );
        theMediaSpan.appendChild(mediaDescObj);
        debug('idMediaSpan.appendChild(mediaDescObj);');
    }
    //Media Content
    var mediaContentObj = theDocument.getElementById( mediaContentTagName );
    if (mediaContentObj == null ) {
        mediaContentObj = theDocument.createElement( mediaContentTag );
        theMediaSpan.appendChild(mediaContentObj);
        debug('idMediaSpan.appendChild(mediaContentObj);');
    }
    mediaContentObj.value = recorder.Content;
    debug(mediaContentTagName + ',' + mediaContentObj.value);
    //Media Content type
    var mediaConttypeObj = theDocument.getElementById( mediaConttypeTagName );
    if (mediaConttypeObj == null ) {
        mediaConttypeObj = theDocument.createElement( mediaConttypeTag );
        theMediaSpan.appendChild(mediaConttypeObj);
        debug('idMediaSpan.appendChild(mediaConttypeObj);');
    }
    mediaConttypeObj.value = recorder.ContentType;
    debug(mediaConttypeTagName + ',' + mediaConttypeObj.value);
    //------
    // get description
    var defaultFilePath = recorder.OutputFilePath;
    recorder.CloseAll();
    var mediaDesc = prompt(tips_get_media_description,"");
    if (mediaDesc == null || mediaDesc == "" ) {
        mediaDesc = defaultFilePath;
    }
    mediaDescObj.value = mediaDesc;
    debug(mediaDescTagName + ',' + mediaDescObj.value);
    genMediaInfo( Idx, mediaDesc);
    mediaNameS[ Idx ] = mediaDesc;
    var cLen = recorder.Content.length;
    addMediafileInfo(Idx, mediaName, mediaDesc, cLen, mediaConttypeObj.value );
    
    mediaCount++;
    return mediaCount;
}

// -----------------------------------------------------
//  move some js functions from compose.htm to multiattach.js
// -----------------------------------------------------


function fAddAddr(flag) {
  gDoc = document;
//  var destObj = $("selAddrList" + flag);
  var destObj = gDoc.getElementById("selAddrList" + flag);
  var dstOpts = destObj.options;
  var dstOptsLen = dstOpts.length;
  var sourceObj = gDoc.getElementById("selAddrAll");
  var srcOpts = sourceObj.options;
  var srcOptsLen = srcOpts.length;
  for (var i = 0; i < srcOptsLen; i++) {
    if (srcOpts[i].selected) {
      if (i < 0) {
        return;
      }
      var o = srcOpts[i];
      var option = gDoc.createElement("OPTION");
      var exist = false;
      for (var j = 0; j < dstOptsLen; j++) {
        if (dstOpts[j].value == o.value) {
          exist = true;
          break;
        }// else {
        //  alert("dstOpts[" + j + "].value = \"" + dstOpts[j].value + "\"\n" +
        //        "o.value = \"" + o.value + "\"");
        //}
      }
      if (!exist) {
        option.value = o.value;
        option.text = o.text;
        option.title = o.text;
        dstOpts[dstOpts.length] = option;
      }
    }
  }
}

function fRemoveAddr(flag) {
  gDoc = document;
  var destObj = gDoc.getElementById("selAddrList" + flag);
  for (var i = destObj.options.length - 1; i > -1; i--) {
    if (destObj.options[i].selected) {
      if (i < 0) {
        return;
      }
      destObj.remove(i);
    }
  }
}

function fAddrListOk() {
  var gDoc = document;
  var div = gDoc.getElementById("dvAddrList");
  var selAddrListTo = gDoc.getElementById("selAddrListTo");
  var selAddrListCc = gDoc.getElementById("selAddrListCc");
  var selAddrListBcc = gDoc.getElementById("selAddrListBcc");
  var toAddrStr = fGetAddrStr(selAddrListTo);
  var ccAddrStr = fGetAddrStr(selAddrListCc);
  var bccAddrStr = fGetAddrStr(selAddrListBcc);
  gDoc.getElementById("idTo").value = toAddrStr;
  gDoc.getElementById("idCc").value = ccAddrStr;
  gDoc.getElementById("idBcc").value = bccAddrStr;
  
  if (ccAddrStr != "") {
    var ccRowOp = gDoc.getElementById("ccRowOp");
    if (ccRowOp) {
        ccRowOp.innerHTML = title_compose_addcc;
        ccRowOp.onclick();
    }
  }
  if (bccAddrStr != "") {
    var bccRowOp = gDoc.getElementById("bccRowOp");
    if (bccRowOp) {
        bccRowOp.innerHTML = info_compose_bcc_add;
        bccRowOp.onclick();
    }
  }
  
  div.style.display = "none";
}

function fGetAddrStr(obj) {
  var arr = new Array();
  for (var i = 0; i < obj.options.length; i++) {
    arr[arr.length] = obj.options[i].text;
  }
  return arr.join(",");
}

function getAddrFromList() {
  gDoc = document;
  var extDiv = gDoc.getElementById("dvAddrList");
  if (!extDiv) return;
  var refObj = gDoc.getElementById("compose_tab");
  extDiv.style.left = f_GetX(refObj) + "px";
  extDiv.style.top = f_GetY(refObj) + "px";
  initAddrListsCon();
  extDiv.style.display = "";
}

function initAddrListsCon() {
  var flags = ["To", "Cc", "Bcc"];
  var flaLen = flags.length;
  for (var i = 0; i < flaLen; i++) {
    initAddrList(flags[i]);
  }
}

// 将收件人列表加入“从个人通讯录添加”页面相应的列表中
function initAddrList(flag) {
  var gDoc = document;
  var srcObj = gDoc.getElementById("id" + flag);
  var desObj = gDoc.getElementById("selAddrList" + flag);
  var addrs = srcObj.value.split(",");
  var addrLen = addrs.length;
  var ops = desObj.options;
  ops.length = 0;
  for (var i = 0; i < addrLen; i++) {
    var addr = trim(addrs[i]);
    if (addr.length <= 0) continue;
    var option = gDoc.createElement("OPTION");
    var matchRes = addr.match(/\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/gi);
    if (matchRes && matchRes.length > 0)
      option.value = matchRes[0];
    option.text = addr;
    option.title = addr;
    ops[ops.length] = option;
  }
}

function trim(str){
    return str.replace(/(^\s*)|(\s*$)/g, "");
}
