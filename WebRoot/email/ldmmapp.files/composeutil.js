var g_composeutil_loaded = true;
try {
  if (!g_layerutil_loaded) {
    document.write("<script type=\"text/javascript\" src=\"/js/layerutil.js\"></script>");
  }
}
catch(e) {
  document.write("<script type=\"text/javascript\" src=\"/js/layerutil.js\"></script>");
}
try {
  if (!g_layerutil_loaded) {
    document.write("<script type=\"text/javascript\" src=\"/js/layerutil.js\"></script>");
  }
}
catch(e) {
  document.write("<script type=\"text/javascript\" src=\"/js/layerutil.js\"></script>");
}

//-------------------------------------------------
var addAddrTo = 'idTo';
function addaddrto(destId, srcId)
{
  addAddrTo = destId;
  if (document.getElementById("btnTo")) {
    document.getElementById("btnTo").childNodes[0].nodeValue = "";
  }
  else {
    document.getElementById("idTo").style.backgroundColor = "#ffffff";
  }
  if (document.getElementById("btnCc")) {
    document.getElementById("btnCc").childNodes[0].nodeValue = "";
  }
  else {
    document.getElementById("idCc").style.backgroundColor = "#ffffff";
  }
  if (document.getElementById("btnBcc")) {
    document.getElementById("btnBcc").childNodes[0].nodeValue = "";
  }
  else {
    document.getElementById("idBcc").style.backgroundColor = "#ffffff";
  }
  if (document.getElementById(srcId)) {
    document.getElementById(srcId).childNodes[0].nodeValue = "<<";
  }
  else {
    document.getElementById(destId).style.backgroundColor = "#ffffcc";
  }

}
function appendEmail(to, from)
{
  var lt1 = to.split(",");
  var lt2 = from.split(",");
  var lt3 = [];
  for (var i = 0; i < lt2.length; i++) {
    var found = false;
    for (var j = 0; j < lt1.length; j++) {
      if (lt1[j] == lt2[i]) {
        found = true;
        break;
      }
    }
    if (!found) {
      lt3[ lt3.length ] = lt2[i];
    }
  }
  return lt3.join(",");
}
function clearEmail(email)
{
  var lt1 = email.split(",");
  var lt2 = [];
  for (var i = 0; i < lt1.length; i++) {
    var found = false;
    for (var j = 0; j < lt2.length; j++) {
      if (lt1[i] == lt2[j]) {
        found = true;
        break;
      }
    }
    if (!found) {
      lt2[lt2.length] = lt1[i];
    }
  }
  return lt2.join(",");
}

function addaddr(email)
{
  if (email == null || email == "") return;
  var obj = document.getElementById(addAddrTo);
  var newvalue = appendEmail(obj.value, clearEmail(email));
  if (newvalue.length > 0) {
    if (obj.value.length > 0)
      obj.value += ",";
    obj.value += newvalue;
  }
  obj.value = clearEmail(obj.value);
}

//-------------------------------------------------
//////////////////// vip address list /////////////////////////////
var addrlist_css = "/css/simple.css";
var viplist = new Array();
var vipIdx = 0;


var hasVipList = false;
var isToggle = false;

var itemstr = "<tr class=font1 id=\"addressname\" onclick=\"parent.addaddr('EMAIL');\" style=\"cursor:pointer\"><td>NAME</td></tr>";
var itemstr2 = "<tr class=font1 onclick=\"parent.showaddr('EMAIL',this.innerHTML,'NAME');\" style=\"cursor:pointer\"><td>NAME</td></tr>";
var greName = /NAME/g;
var greEmail = /EMAIL/g;

var composeutil_tips_nocontactor = "the address list is empty";
var composeutil_tips_contactor = "'s addresses";

function setAddrListCss(theCss)
{
  addrlist_css = theCss;
}
function menuChange()
{
  if (isToggle) {
    editor_showLayer("menu1");
    isToggle = false;
  }
  else {
    editor_hideLayer("menu1");
    isToggle = true;
  }
}

function composeutil_addall()
{
  var addrlist = document.getElementById("addrlist").contentWindow;
  var obj = addrlist.document.getElementsByTagName("tr");
  for (var i = 0; i < obj.length; i++) {
    if (obj[i].id == "addressname") {
      obj[i].onclick();
    }
  }
  //for end
  //IE is OK
  //var all_span_obj = addrlist.document.tags("span");
  //for (var i = 0 ; i < all_span_obj.length; i ++ ) {
  //	all_span_obj[i].onclick();
  //}

}
function outAddrListPageHeader(dest)
{
  var header = "<html><head>" + composeutil_info_addrlistpageheader_meta + "<link href=\"" + addrlist_css + "\" rel=\"stylesheet\" type=\"text/css\"/></HEAD>"
          + "<body TOPMARGIN=0><table width=100% border=0 cellpadding=0 cellspacing=0>";
  dest.document.write(header);
}
function outAddrListPageTail(dest)
{
  dest.document.write("</table></body></html>");
}
function isSameGroup(grp1, grp2) {
  if (grp1 == grp2) return true;
  var loc1 = grp1.indexOf("...");
  var loc2 = grp2.indexOf("...");
  if (loc1 == -1 || loc2 == -1) return false;
  var loc = (loc1 > loc2)?loc2:loc1;
  return (grp1.substr(0, loc) == grp2.substr(0, loc));
}
function getEmailItems(theEmail, group)
{
  var addrListFrame = getAddrListFrame();
  if (!addrListFrame || !addrListFrame.addritems) return;
  var alladdr = addrListFrame.addritems;
  var docContent = "";
  var arr = theEmail.split(",");

  var name = "";
  var addr = "";

  var alladdrLen = alladdr.length;
  for (var n = 0; n < alladdrLen; n++) {
    addr = alladdr[n].email;
    //	is in group ?
    var grp = alladdr[n].group;
    var isInGroup = false;
    if (grp == "" && group == tip_other_group_label) {
      isInGroup = true;
    }
    else {
      var arrGrp = grp.split(";");
      var arrGrpLen = arrGrp.length;
      for (var ii = 0; ii < arrGrpLen; ii++) {
        if (isSameGroup(arrGrp[ii], group)) {
          isInGroup = true;
          break;
        }
      }
    }

    if (isInGroup) {
      name = alladdr[n].name;
      var theItem = itemstr;
			theItem = theItem.replace(greEmail, "&quot;" + name + "&quot;&lt;" + addr + "&gt;");
      theItem = theItem.replace(greName, name);
      docContent += theItem;
    }
  }

  /*
                //为防止重复填加，必须使 alladdr 外循环.
                for ( var n = 0 ; n < alladdr.length ; n ++ )
                {
                        for ( var i =0 ; i < arr.length ; i ++ ) {
                                addr = arr[i];
                                if ( addr == alladdr[n].email ) { 
                                        var arrGrp = alladdr[n].group.split(";");
                                        var isInGroup = false;
                                        for ( var ii = 0 ; ii < arrGrp.length ; ii ++ ) {
                                                if ( arrGrp[ii] == group ) {
                                                        isInGroup = true;
                                                        break;	
                                                }
                                        }
                                        //如果 group 为空，而且 group == tip_other_group_label ,就证明是 其他 组的，所以要加入。
                                        if ( alladdr[n].group == "" && group == tip_other_group_label ) isInGroup = true;
                                        //满足 email 相同 ， group相同 ，就可以加入了
                                        if ( isInGroup ) {
                                                name = alladdr[n].name ; 
                                                var theItem = itemstr;
                                                theItem = theItem.replace(greEmail, addr);
                                                  theItem = theItem.replace(greName, name);
                                                  docContent += theItem;
                                                  break;  //注意一定要跳出，不用再比较了，不然又产生重复。继续比较下个 alladdr 项目
                                         }// end isInGroup if
                                 }//if
                         } // arr for end
                 }//alladdr for end
                 */

  return docContent;
}
function getAddrListItem(name, email)
{
  var theItem = itemstr2;
  theItem = theItem.replace(greEmail, email);
  theItem = theItem.replace(greName, name);
  return theItem;
}
function getAddrListItems(addrlist)
{

  if (!addrlist) return "";
  var theItems = "";
  var i = 0;
  for (i = 0; i < addrlist.length; i++) {
    theItems += getAddrListItem(addrlist[i].name, addrlist[i].email);
  }
  return theItems;
}
function showaddr(email, text, group)
{
  //		if (email==null || email=="")  { alert(composeutil_tips_nocontactor);return; }
  //    alert("emails = \n" + email);
    var ifrAddrList = document.getElementById("addrlist");
    if (!ifrAddrList) return;

  var addrlist = ifrAddrList.contentWindow;
    if (!addrlist) {
        // ifrAddrList is not iframe object
        return;
    }
  addrlist.document.open();
  outAddrListPageHeader(addrlist);
  var docContent = getEmailItems(email, group);
  if (!addrlist) {
    alert("in composeutil.js: addrlist unexist.");
  } else if (!addrlist.document) {
    alert("in composeutil.js: addrlist.document unexist.");
  } else if (!addrlist.document.write) {
    alert("in composeutil.js: addrlist.document.write unexist.");
  }
  addrlist.document.write(docContent);
  //    alert("in composeutil.js: docContent = " + docContent);	
  outAddrListPageTail(addrlist);
  try {
    // recal is for IE
    if (addrlist.document.recalc)
      addrlist.document.recalc(true);
  }
  catch (e) {
  }
  addrlist.document.close();
  var grp = document.getElementById("listgrp");
  grp.innerHTML = text + composeutil_tips_contactor;
  return false;
}
function outAddrListInfo(addrlist)
{

  if (!addrlist) return;
  var adrlistWnd = document.getElementById("addrlistlist").contentWindow;
  adrlistWnd.document.open();
  outAddrListPageHeader(adrlistWnd);
  var docContent = getAddrListItems(addrlist);
  adrlistWnd.document.write(docContent);
  outAddrListPageTail(adrlistWnd);
  adrlistWnd.document.close();
}

function getAddrListFrame() {
	var p = window;
	for (var i = 0; p && !p.addrListFrame && i < 10; i++) {
		p = p.parent;
	}
	if (!p || !p.addrListFrame) {
		p = opener;
		for (var i = 0; p && !p.addrListFrame && i < 10; i++) {
			p = p.parent;
		}
	}
	if (!p || !p.addrListFrame) return null;
	return p.addrListFrame;
}

function outFirstAddrlistContent()
{
  var addrListFrame = getAddrListFrame();
  if (!addrListFrame || !addrListFrame.addrlist) return;
  var addrlists = addrListFrame.addrlist;

  var i = 0;
  for (i = 0; i < addrlists.length; i++)
  {
    if (addrlists[i].email.length <= 0) continue;
    showaddr(addrlists[i].email, addrlists[i].name, addrlists[i].name);
    break;
  }
}
function outAddrVIPInfo()
{
  if (!hasVipList) {
    outFirstAddrlistContent();
    return;
  }
  var objAddrList = document.getElementById("addrlist");
  if (!objAddrList) return;

  var i = 0;
  var addrlist = objAddrList.contentWindow;
  addrlist.document.open();
  outAddrListPageHeader(addrlist);

  for (i = 0; i < viplist.length; i++) {
    var theItem = itemstr;
    theItem = theItem.replace(greEmail, viplist[i][1]);
    theItem = theItem.replace(greName, viplist[i][0]);
    addrlist.document.write(theItem);
  }

  outAddrListPageTail(addrlist);
  addrlist.document.close();

    var grp = document.getElementById("listgrp");
    if (grp) {
        var addrListFrame = getAddrListFrame();
        if (addrListFrame && addrListFrame.addrlist) {
            var addrlistlist = addrListFrame.addrlist;
            var groupName = "重要联系人";
            for (i = 0; i < addrlistlist.length; i++)  {
              if (addrlistlist[i].group == "VIP") {
                groupName = addrlistlist[i].name;
                break;
              }
            }
            grp.innerHTML = groupName;
        }
    }
}
function setVIPListPos()
{
  if (!hasVipList) return;
  var left = getObjLeft(document.getElementById("locAddrList")) + 5;
  var top = getObjTop(document.getElementById("locAddrList"));
  moveLayer("divViplist", left, top);
}

function freshAddrList()
{
  var dataFrame = getAddrListFrame();
  dataFrame.location.reload();
}
function showAddrList()
{
  try {
    var dataFrame = getAddrListFrame();
    if (dataFrame)
    {
      //alert(dataFrame.addrlist );
      outAddrListInfo(dataFrame.addrlist);
    } else
    {
      //alert("non show");
      editor_hideLayer("AddrListPanel");
    }
  }
  catch (e) {
    //	alert(exception);
  }
}

//////////////////////////////////////////////////////////////////////////////////////////
function fTrim(str) {
  str = str.replace(/(^\s*)/, "");
  str = str.replace(/(\s*$)/, "");
  return str;
}

function dispCCorBcc(obj)
{
  if (obj.innerHTML == title_compose_addcc)
  {
    document.getElementById("idRowCc").style.display = '';
    obj.innerHTML = title_compose_delcc;
    obj.title = title_compose_hidecc;
    document.getElementsByName("cc")[0].focus();
  } else if (obj.innerHTML == title_compose_delcc) {
    if (fTrim(document.getElementsByName("cc")[0].value) != "") {
      if (!window.confirm(tips_compose_delcc)) return;
    }
    document.getElementById("idRowCc").style.display = 'none';
    obj.innerHTML = title_compose_addcc;
    obj.title = desc_compose_addcc;
    document.getElementsByName("cc")[0].value = "";
  }

  if (obj.innerHTML == info_compose_bcc_add)
  {
    document.getElementById("idRowBcc").style.display = '';
    obj.innerHTML = info_compose_bcc_del;
    obj.title = title_compose_bcc_hide;
    document.getElementsByName("bcc")[0].focus();
  } else if (obj.innerHTML == info_compose_bcc_del) {
    if (fTrim(document.getElementsByName("bcc")[0].value) != "") {
      if (!window.confirm(tips_compose_bcc_del)) return;
    }
    document.getElementById("idRowBcc").style.display = 'none';
    obj.innerHTML = info_compose_bcc_add;
    obj.title = title_compose_bcc_add;
    document.getElementsByName("bcc")[0].value = "";
  }
}

function showOldAttachRow()
{
  var theObj = document.getElementById("idRowOldAttach");
  if (theObj) {
    theObj.style.display = "";
  }
}

function dispTimesetSpan()
{
  if (dispObjById("trTimeSet") == 1) {
    document.sendmail.year.focus();
  }
}

// --------------------------------
//      for debug
function gObjById(objId) {
  var doc = document;
  var obj = doc.getElementById(objId);
  if (!obj) throw "no obj's id is " + objId;
  return obj;
}
// --------------------------------

function dispAddressBook()
{
  var ret = dispObjById("trAddressBook");
  var theObj = document.getElementById("opAddressBook");
  if (theObj) {
    if (ret == 0) {
      theObj.childNodes[0].nodeValue = "<";
      theObj.title = title_compose_showaddrbook;
    }
    else if (ret == 1) {
      theObj.childNodes[0].nodeValue = ">";
      theObj.title = title_compose_hideaddrbook;
    }
  }
  adjustInputTextWidth();
}

//  返回
//      0: 调用此函数后, 对象处于"隐藏"状态
//      1: 调用此函数后, 对象处于"显示"状态
//      -1:没有此对象
function dispObjById(theId)
{
  var theObj = document.getElementById(theId);
  if (!theObj) return -1;

  if (theObj.style.display == "") {
    theObj.style.display = "none";
    return 0;
  }
  else {
    theObj.style.display = "";
    return 1;
  }
}
function dispHtmlViewOper()
{
  var theObj = document.getElementById("opHtmlViewer");
  if (!theObj) return -1;
  var chkObj = document.getElementById("chkHtmlMessage_text");
  if (!chkObj) return -1;

  var show = chkObj.checked;
  if (show) {
    theObj.style.display = "";
    return 1;
  }
  else {
    theObj.style.display = "none";
    return 0;
  }
}

function adjustInputTextWidth()
{
  var addrbookWidth = 0;
  var addrbookObj = document.getElementById("trAddressBook");
  var realWidth = (document.body.clientWidth * 0.9);
  if (addrbookObj) {
    if (addrbookObj.style.display != "none") {
      realWidth = (document.body.clientWidth * 0.7);
    }
  }
  document.getElementById("idTo").style.width = realWidth + "px";
  document.getElementById("idCc").style.width = realWidth + "px";
  document.getElementById("idBcc").style.width = realWidth + "px";
  document.getElementById("idSubject").style.width = realWidth + "px";
}


