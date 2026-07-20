//----------------------------------------------------------
var bLoad=false;
public_description=new Editor

function Editor() {
  this.put_html=SetHtml;
  this.get_html=GetHtml;
  this.put_text=SetText;
  this.get_text=GetText;
  this.CompFocus=GetCompFocus;
}

function GetCompFocus() {
  var f = window.frames["HtmlEditor"];
  f.focus();
}

function GetText() {
  var f = window.frames["HtmlEditor"];
  var body = f.document.getElementsByTagName("BODY")[0];
  return body.innerText;
}

function SetText(text) {
  //    text = text.replace(/\n/g, "<br>")
  var f = window.frames["HtmlEditor"];
  var body = f.document.getElementsByTagName("BODY")[0];
  body.innerHTML = text;
}
 
function GetHtml() {
  cleanHtml();
  var f = window.frames["HtmlEditor"];
  var body = f.document.getElementsByTagName("BODY")[0];
  return body.innerHTML;
}

function SetHtml(sHtml) {
  var f = window.frames["HtmlEditor"];
  var body = f.document.getElementsByTagName("BODY")[0];
  body.innerHTML=sHtml;
}

function cleanHtml() {
  var f = window.frames["HtmlEditor"];
  var body = f.document.getElementsByTagName("BODY")[0];
  var fonts = body.all.tags("FONT");
  var curr;
  for (var i = fonts.length - 1; i >= 0; i--) {
    curr = fonts[i];
    if (curr.style.backgroundColor == "#ffffff") curr.outerHTML = curr.innerHTML;
  }
}
function getPureHtml() {
  var str = "";
  var f = window.frames["HtmlEditor"];
  var body = f.document.getElementsByTagName("BODY")[0];
  var paras = body.all.tags("P");
  if (paras.length > 0) {
    for (var i=paras.length-1; i >= 0; i--) str = paras[i].innerHTML + "\n" + str;
  } else {
    str = body.innerHTML;
  }
  return str;
}

//----------------------------------------------------------

var gSetColorType = ""; 
var gIsIE = document.all; 
var gIEVer = fGetIEVer();
var ev = null;
function fGetEv(e){
    ev = e;
}
function fGetIEVer(){
    var iVerNo = 0;
    var sVer = navigator.userAgent;
    if(sVer.indexOf("MSIE")>-1){
        var sVerNo = sVer.split(";")[1];
        sVerNo = sVerNo.replace("MSIE","");
        iVerNo = parseFloat(sVerNo);
    }
    return iVerNo;
}
function fSetEditable(){
    try {
    var f = window.frames["HtmlEditor"];
        if (!gIsIE) {
            window.setTimeout(
                function(){
                    try {
                        var f = window.frames["HtmlEditor"];
                        f.document.designMode = "on";
                        f.document.execCommand("useCSS",false, true);
                    }
                    catch (e) {
                    }
                }, 10);
        }
        else{
            f.document.designMode="on";
        }
    }
        catch (e) {
        }
}
function fSetFrmClick(){
    var f = window.frames["HtmlEditor"];
    f.document.onmousemove = function(){
        window.onblur();
    }
    f.document.onclick = function(){
        fHideMenu();
    }
}
function fSetContent(){
    var f = window.frames["HtmlEditor"];
    var foldmain = window.parent;
    var htext = foldmain.document.sendmail.htext.value;
    var eBody = null;
    eBody = f.document.getElementsByTagName("BODY")[0];
    if(htext != "") 
        htext = "<br><br><br><br><br><br><br>" + htext;
    eBody.innerHTML = htext;
}
function fSetColor(){
    var dvForeColor =document.getElementById("dvForeColor");
    if(dvForeColor.getElementsByTagName("TABLE").length == 1){
        dvForeColor.innerHTML = drawCube() + dvForeColor.innerHTML;
    }
}
window.onload = function(){
    fSetEditable();
    fSetFrmClick();
    //if(!gIsIE) fSetContent();
}
window.onblur =function(){
    var dvForeColor =document.getElementById("dvForeColor");
    var dvPortrait =document.getElementById("dvPortrait");
    dvForeColor.style.display = "none";
    if(dvPortrait){
        dvPortrait.style.display = "none";
    }
    fHideMenu();
}
window.onerror = function(){return true;}
document.onmousemove = function(e){
    if(gIsIE) var el = event.srcElement;
    else var el = e.target;
    var tdView = document.getElementById("tdView");
    var tdColorCode = document.getElementById("tdColorCode");
    var dvForeColor =document.getElementById("dvForeColor");
    var dvPortrait =document.getElementById("dvPortrait");
    var fontsize =document.getElementById("fontsize");
    var fontface =document.getElementById("fontface");
    var paragraph = document.getElementById("paragraph");
//  if(el.tagName == "IMG"){
//      el.style.borderRight="1px #cccccc solid";
//      el.style.borderBottom="1px #cccccc solid";
//  }else{
//      fSetImgBorder();
//  }
    if(el.tagName == "IMG"){
        try{
            if(fCheckIfColorBoard(el)){
                tdView.bgColor = el.parentNode.bgColor;
                tdColorCode.innerHTML = el.parentNode.bgColor
            }
        }catch(e){}
    }else{
        dvForeColor.style.display = "none";
        if(!fCheckIfPortraitBoard(el) && dvPortrait) dvPortrait.style.display = "none";
        if(!fCheckIfFontFace(el)) fontface.style.display = "none";
        if(!fCheckIfFontSize(el)) fontsize.style.display = "none";
        if(!fCheckIfParagraph(el)) paragraph.style.display = "none";
    }
}
document.onclick = function(e){
    if(gIsIE) var el = event.srcElement;
    else var el = e.target;
    var dvForeColor =document.getElementById("dvForeColor");
    var dvPortrait =document.getElementById("dvPortrait");
    if(el.tagName == "IMG"){
        try{
            if(fCheckIfColorBoard(el)){
                format(gSetColorType, el.parentNode.bgColor);
                dvForeColor.style.display = "none";
                return;
            }
        }catch(e){}
        try{
            if(fCheckIfPortraitBoard(el)){
                format("InsertImage", el.src);
                if(dvPortrait){
                    dvPortrait.style.display = "none";
                }
                return;
            }
        }catch(e){}
    }
}
function format(type, para){
    var f = window.frames["HtmlEditor"];
    var sAlert = "";
    if(!gIsIE){
        switch(type){
            case "Cut":
                sAlert = tips_compose_shortcut_cut;
                break;
            case "Copy":
                sAlert = tips_compose_shortcut_copy;
                break;
            case "Paste":
                sAlert = tips_compose_shortcut_paste;
                break;
        }
    }
    if(sAlert != ""){
        alert(sAlert);
        return;
    }
    f.focus();
    if(!para)
        if(gIsIE)
            f.document.execCommand(type)
        else
            f.document.execCommand(type,false,false)
    else
        f.document.execCommand(type,false,para)
    f.focus();
}
function setMode(bStatus){
    var sourceEditor = document.getElementById("sourceEditor");
    var HtmlEditor = document.getElementById("tbhtmleditor");
    var f = window.frames["HtmlEditor"];
    var body = f.document.getElementsByTagName("BODY")[0];
    if(bStatus){
        sourceEditor.style.display = "";
        HtmlEditor.style.display = "none";
        sourceEditor.value = body.innerHTML;
    }else{
        sourceEditor.style.display = "none";
        HtmlEditor.style.display = "";
        body.innerHTML = sourceEditor.value;
        fSetEditable();
    }
}
function foreColor(e) {
    var sColor = fDisplayColorBoard(e);
    gSetColorType = "foreColor";
    if(gIEVer<=5.01 && gIsIE) format(gSetColorType, sColor);
}
function backColor(e){
    var sColor = fDisplayColorBoard(e);
    if(gIsIE)
        gSetColorType = "backcolor";
    else
        gSetColorType = "backcolor";
    if(gIEVer<=5.01 && gIsIE) format(gSetColorType, sColor);
}
function fDisplayColorBoard(e){
    if(gIsIE){
        var e = window.event;
    }
    if(gIEVer<=5.01 && gIsIE){
        var arr = showModalDialog("ColorSelect.htm", "", "font-family:Verdana; font-size:12; status:no; dialogWidth:21em; dialogHeight:21em");
        if (arr != null) return arr;
        return;
    }
    var dvForeColor =document.getElementById("dvForeColor");
    fSetColor();
    var iX = e.clientX;
    var iY = e.clientY;
    dvForeColor.style.display = "";
    dvForeColor.style.left = (iX-140) + "px";
    dvForeColor.style.top = (iY-10) + "px";
    return true;
}
function createLink() {
    var sURL=window.prompt(tips_compose_createlink, "http://");
    if ((sURL!=null) && (sURL!="http://")){
        format("CreateLink", sURL);
    }
}
function createImg()    {
    var sPhoto=prompt(tips_compose_createimg, "http://");
    if ((sPhoto!=null) && (sPhoto!="http://")){
        format("InsertImage", sPhoto);
    }
}
function addPortrait(e){
    if(gIEVer<=5.01 && gIsIE){
        var imgurl = showModalDialog("portraitSelect.htm","", "font-family:Verdana; font-size:12; status:no; unadorned:yes; scroll:no; resizable:yes;dialogWidth:40em; dialogHeight:20em");
        if (imgurl != null) format("InsertImage", imgurl);
        return;
    }
    var dvPortrait =document.getElementById("dvPortrait");
    var tbPortrait = document.getElementById("tbPortrait");
    var iX = e.clientX;
    var iY = e.clientY;
    dvPortrait.style.display = "";
    if(window.screen.width == 1024){
        dvPortrait.style.left = (iX-380) + "px";
    }else{
        if(gIsIE)
            dvPortrait.style.left = (iX-380) + "px";
        else
            dvPortrait.style.left = (iX-380) + "px";
    }
    dvPortrait.style.top = (iY-8) + "px";
    dvPortrait.innerHTML = '<table width="100%" border="0" cellpadding="5" cellspacing="1" style="cursor:pointer" bgcolor="black" ID="tbPortrait"><tr align="left" bgcolor="#f8f8f8" class="unnamed1" align="center" ID="trContent">'+ drawPortrats() +'</tr>   </table>';
}
function fCheckIfColorBoard(obj){
    if(obj.parentNode){
        if(obj.parentNode.id == "dvForeColor") return true;
        else return fCheckIfColorBoard(obj.parentNode);
    }else{
        return false;
    }
}
function fCheckIfPortraitBoard(obj){
    if(obj.parentNode){
        if(obj.parentNode.id == "dvPortrait") return true;
        else return fCheckIfPortraitBoard(obj.parentNode);
    }else{
        return false;
    }
}
function fImgOver(el){
    if(el.tagName == "IMG"){
        el.style.borderRight="1px #cccccc solid";
        el.style.borderBottom="1px #cccccc solid";
    }
}
function fImgMoveOut(el){
    if(el.tagName == "IMG"){
        el.style.borderRight="1px #F3F8FC solid";
        el.style.borderBottom="1px #F3F8FC solid";
    }
}
String.prototype.trim = function(){
    return this.replace(/(^\s*)|(\s*$)/g, "");
}
function fSetBorderMouseOver(obj) {
    obj.style.borderRight="1px solid #aaa";
    obj.style.borderBottom="1px solid #aaa";
    obj.style.borderTop="1px solid #fff";
    obj.style.borderLeft="1px solid #fff";
    /*var sd = document.getElementsByTagName("div");
    for(i=0;i<sd.length;i++) {
        sd[i].style.display = "none";
    }*/
} 

function fSetBorderMouseOut(obj) {
    obj.style.border="none";
}

function fSetBorderMouseDown(obj) {
    obj.style.borderRight="1px #F3F8FC solid";
    obj.style.borderBottom="1px #F3F8FC solid";
    obj.style.borderTop="1px #cccccc solid";
    obj.style.borderLeft="1px #cccccc solid";
}
function fDisplayElement(element,displayValue) {
    if(gIEVer<=5.01 && gIsIE){
        if(element == "fontface"){
            var sReturnValue = showModalDialog(FontFaceSelectPage,"", "font-family:Verdana; font-size:12; status:no; unadorned:yes; scroll:no; resizable:yes;dialogWidth:112px; dialogHeight:271px");;
            format("fontname",sReturnValue);
        }
            else if (element == "paragraph") {
                var sReturnValue = showModalDialog(ParaGraphSelectPage, "", "font-family:Verdana; font-size:12; status:no; unadorned:yes; scroll:no; resizable:yes;dialogWidth:112px; dialogHeight:271px");;
                format("formatBlock", sReturnValue);
            }
        else{
            var sReturnValue = showModalDialog(FontSizeSelectPage,"", "font-family:Verdana; font-size:12; status:no; unadorned:yes; scroll:no; resizable:yes;dialogWidth:130px; dialogHeight:250px");;
            format("fontsize",sReturnValue);
        }
        return;
    }
    var fontsize = document.getElementById("fontsize");
    var fontface = document.getElementById("fontface");
    var paragraph = document.getElementById("paragraph");
    if(element == "fontface"){
        if (fontsize) fontsize.style.display = "none";
        if (paragraph) paragraph.style.display = "none";
    }else if(element == "fontsize"){
        if (fontface) fontface.style.display = "none";
        if (paragraph) paragraph.style.display = "none";
    }else if(element == "paragraph"){
        if (fontsize) fontsize.style.display = "none";
        if (fontface) fontface.style.display = "none";
    }
    if ( typeof element == "string" )
        element = document.getElementById(element);
    if (element == null) return;
    element.style.display = displayValue;
    if(gIsIE){
        var e = event;
    }else{
        var e = ev;
    }
    var iX = e.clientX;
    var iY = e.clientY;
    element.style.display = "";
    element.style.left = (iX-40) + "px";
    element.style.top = (iY-10) + "px";
    return true;
}
function fSetModeTip(obj){
    var x = f_GetX(obj);
    var y = f_GetY(obj);
    var dvModeTip = document.getElementById("dvModeTip");
    if(!dvModeTip){
        var dv = document.createElement("DIV");
        dv.style.position = "absolute";
        dv.style.top = (y+20) + "px";
        dv.style.left = (x-40) + "px";
        dv.style.zIndex = "999";
        dv.style.fontSize = "12px";
        dv.id = "dvModeTip";
        dv.style.padding = "2 2 0 2px";
        dv.style.border = "1px #000000 solid";
        dv.style.backgroundColor = "#FFFFCC";
        dv.style.height = "12px";
        dv.innerHTML = tips_compose_editsource;
        document.body.appendChild(dv);
    }else{
        dvModeTip.style.display = "";
    }
}
function fHideTip(){
    document.getElementById("dvModeTip").style.display = "none";
}
function f_GetX(e)
{
    var l=e.offsetLeft;
    while(e=e.offsetParent){                
        l+=e.offsetLeft;
    }
    return l;
}
function f_GetY(e)
{
    var t=e.offsetTop;
    while(e=e.offsetParent){
        t+=e.offsetTop;
    }
    return t;
}
function fHideMenu(){
    var fontface = document.getElementById("fontface");
    var fontsize = document.getElementById("fontsize");
    var paragraph = document.getElementById("paragraph");
    fontface.style.display = "none";
    fontsize.style.display = "none";
    paragraph.style.display = "none";
}

function fCheckIfFontFace(obj){
    if(obj.parentNode){
        if(obj.parentNode.id == "fontface") return true;
        else return fCheckIfFontFace(obj.parentNode);
    }else{
        return false;
    }
}
function fCheckIfFontSize(obj){
    if(obj.parentNode){
        if(obj.parentNode.id == "fontsize") return true;
        else return fCheckIfFontSize(obj.parentNode);
    }else{
        return false;
    }
}
function fCheckIfParagraph(obj){
    if(obj.parentNode){
        if(obj.parentNode.id == "paragraph") return true;
        else return fCheckIfParagraph(obj.parentNode);
    }else{
        return false;
    }
}
