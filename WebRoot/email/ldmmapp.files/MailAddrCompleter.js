var gArrEmailAddress = null;
if (!gArrEmailAddress) {
    setArrEmailAddress();
}
function setArrEmailAddress() {
    if (!getAddrListFrame) {
        // multiattch.js still not loaded, can't recognise getAddrListFrame function
        window.setTimeout("setArrEmailAddress()", 1000);
    }
    else {
        var addrListFrame = getAddrListFrame();
        if (addrListFrame && addrListFrame.addritems) {
            gArrEmailAddress = addrListFrame.addritems;
        }
    }
}

var g_ArrEmailList;
var g_ArrOUs = null;
var g_MakeRequest = false;
var g_MainDiv = null;
var g_Body = null;
var g_FirstTd = null;
var g_CurrentTd = null;
var g_InputObject = null;
var g_OldInnerTxt = "";
var g_NewInnerTxt = "";
var g_LstReqInnerTxt = "";
var g_TdCssClassName = "autofinish_over";
var g_TrCssClassName0 = "autofinish_bg0";
var g_TrCssClassName1 = "autofinish_bg1";
var g_TbCssClassName0 = "autofinish autofinish_bg0";
var g_IgnoreStr = "";
var g_AddListContainer = null;
var g_AddrExtContainer = null;
var g_IgnoreIE = false;
var g_CanUseOrgAddr = false;
g_IgnoreIE = (navigator.userAgent.indexOf('MSIE 5') != -1 || navigator.userAgent.indexOf('Mac') != -1);

function f_OnKeyDown(obj, event) {
  if (obj != g_InputObject) {
    if (g_MainDiv != null) {
      g_MainDiv.innerHTML = "";
      g_MainDiv = null;
    }
    g_OldInnerTxt = "";
    g_NewInnerTxt = "";
    g_FirstTd = null;
    g_CurrentTd = null;
    g_InputObject = obj;
  }

  f_InitMain();
  var kc = event.keyCode;

  switch (kc) {
  case 13:
    f_EnterKey();
    f_SetDivDisplay(false);
    return false;
    break;
  case 27:
    f_EscapeKey();
    f_SetDivDisplay(false);
    return false;
    break;
  case 8:
    f_BackSpaceKey(obj, event);
    return;
    break;
  case 38:
    f_UpKey();
    return;
    break;
  case 40:
    f_DownKey();
    return;
    break;
  default:
    break;
  }
  return true;
}

function f_OnKeyUp(obj, event) {

  var kc = event.keyCode;
  var sTemp = "13,27,38,40,9,116,";
  kc = kc + "";
  if (sTemp.indexOf(kc) > -1) {
    return false;
  }
  typeAhead(event);
}

function f_OnKeyPress() {
  return;
}

function f_OnChange() {
  return;
}

function f_OnBlur(obj, event) {
  if (g_InputObject == null) {
    return;
  }

  var s = g_InputObject.value;
  var x = s.substr(s.length - 1, 1);
  if (x == "," || x == ";") {
    g_InputObject.value = s.substr(0, s.length - 1);
  }
  f_SetDivDisplay(false);
  return;
}

function f_OnPasete() {
  return;
}

function f_InitMain() {
  if (g_Body == null) {
    g_Body = document.body;
  }

  if (g_MainDiv == null) {
    g_MainDiv = f_CreateDiv();
    g_Body.appendChild(g_MainDiv);
  }

}

function f_CreateDiv() {
  var div = document.createElement("div");
  div.id = "divEmailAddressMain";
  div.style.position = "absolute";
  div.style.display = "";
  return div;
}

function f_SetDivDisplay(bTrue) {
	if (g_MakeRequest) return;
	if (g_AddListContainer) {
    if (bTrue) {
      g_AddListContainer.style.display = "";
    } else {
      g_AddListContainer.style.display = "none";
    }
	}
}

function f_GetX(e) {
  var l = e.offsetLeft;
  while (e = e.offsetParent) {
    l += e.offsetLeft;
  }
  return l;
}

function f_GetY(e) {
  var t = e.offsetTop;
  while (e = e.offsetParent) {
    t += e.offsetTop;
  }

  return t;
}

function f_CreateTable() {
  var oTable = document.createElement("table");
  oTable.style.wordBreak = "keep-all";
  oTable.border = 0;
  oTable.cellSpacing = 2;
  oTable.cellPadding = 2;
  return oTable;
}

function f_CreateRow(table) {
  var rowNode = table.insertRow(-1);
  return rowNode;
}

function f_CreateColumn(row, i) {
  var colNode = row.insertCell(document.all?-1:0);
  colNode.noWrap = true;
  colNode.id = "tdACMA_" + i;
  colNode.zIndex = i;
  colNode.align = "left";
  colNode.style.cursor = document.all?"hand":"pointer";
  colNode.onmouseover = f_TdOnmouseover;
  colNode.onmouseout = f_TdOnmouseout;
  colNode.onactivate = f_TdOnclick;
  if (i == 0) {
    colNode.className = g_TdCssClassName;
    g_FirstTd = colNode;
    g_CurrentTd = colNode;
  } else {
    colNode.className = "";
  }

  return colNode;
}

function f_SetExtDivDisplay(bTrue) {
	if (g_AddrExtContainer) {
    if (bTrue) {
      g_AddrExtContainer.style.display = "";
    } else {
      g_AddrExtContainer.style.display = "none";
    }
	}
}

function getEventSrcEle(e) {
  if (!e) {
    var e = window.event;
  }

  if (e.target) {
    return e.target;
  }

  if (e.srcElement) {
    return e.srcElement;
  }
}

function f_TdOnmouseout(e) {
  var o = getEventSrcEle(e);

  while (o.tagName != "TD") {
    o = o.parentNode;
  }

  o.className = "";
  f_SetExtDivDisplay(false);
}

function initAddrExtMsgDiv() {
	if (g_AddrExtContainer != null) return;
  g_AddrExtContainer = document.createElement("div");
  with (g_AddrExtContainer.style) {
    position = "absolute";
    display = "none";
  }
  g_AddrExtContainer.className = g_TbCssClassName0;
  document.body.appendChild(g_AddrExtContainer);
}

function f_TdOnmouseover(e) {
  var o = getEventSrcEle(e);
  
  while (o.tagName != "TD") {
    o = o.parentNode;
  }

  o.className = "";
  if (g_CurrentTd != null) {
    g_CurrentTd.className = "";
  }

  g_CurrentTd = o;
  var i = parseInt(g_CurrentTd.zIndex);
  if (g_AddrExtContainer == null) {
  	initAddrExtMsgDiv();
  }
  g_AddrExtContainer.style.left = (f_GetX(g_CurrentTd) + g_CurrentTd.offsetWidth + 5) + "px";
  var dptName = "";
  var strExt = "电话：";
  if (g_ArrEmailList[i]) strExt += g_ArrEmailList[i].company_phone;
	var needDepInfo = true;
	if (needDepInfo) {
        try {
            if (getOUPathByID && g_ArrEmailList[i]) {
                dptName = getOUPathByID( array, g_ArrEmailList[i].ouid );
            }
            if (dptName != "") strExt += "&nbsp;&nbsp;部门：" + dptName;
        }
        catch (e) {
        }
	}
    
  g_AddrExtContainer.innerHTML = strExt;
  g_AddrExtContainer.style.top = f_GetY(g_CurrentTd) + "px";
  f_SetExtDivDisplay(true);
  g_CurrentTd.className = g_TdCssClassName;
}

function f_TdOnclick() {
  f_FillCurrentEmail();
}

function f_FillCurrentEmail() {
  if (g_CurrentTd == null || g_InputObject == null) {
    return;
  }

  if (g_ArrEmailList == null || g_ArrEmailList.length == 0) {
    return;
  }

  var i = parseInt(g_CurrentTd.zIndex);
  var s = g_OldInnerTxt;
  if (g_OldInnerTxt != "") {
    s += ",";
  }

  g_InputObject.focus();
  g_InputObject.value = s + "\"" + g_ArrEmailList[i].name + "\" " + "<" + g_ArrEmailList[i].email + ">,";
  f_SetDivDisplay(false);
  g_CurrentTd = null;
}

function noNeedToReq(lastTxt, newTxt) {
	if (g_IgnoreStr != "" && newTxt.indexOf(g_IgnoreStr) >= 0) {
		return true;
	}
	if (lastTxt != "" &&
	    g_ArrOUs &&
	    g_ArrOUs.length <= 0 &&
	    newTxt.indexOf(lastTxt) >= 0) {
	  g_IgnoreStr = lastTxt;
	  return true;
	}
	return false;
}

function ousReq() {
	var lastReqTxt = g_LstReqInnerTxt;
	g_LstReqInnerTxt = g_NewInnerTxt;
  if (g_NewInnerTxt == "" || noNeedToReq(lastReqTxt, g_NewInnerTxt)) {
//  	alert("in ousReq()");
  	g_ArrOUs = new Array();
  	f_InitDivData();
  	return;
  }
  var searchOUsFrame = document.searchOUsFrame;
  if (!searchOUsFrame) return;
	var searchOUsForm = searchOUsFrame.document.searchou;
	var attrValueObj = searchOUsForm.attrvalue_true_name;
	if (!attrValueObj) {
		alert("alert in MailAddrCompleter.js: attrvalue object named \"attrvalue_true_name\" unexist");
		return null;
	}
	attrValueObj.value = g_NewInnerTxt;
	g_MakeRequest = true;
	searchOUsForm.submit();
}

function typeAhead(event) {
  recordInnerTxt(event);
  g_ArrEmailList = f_GetArrEmailList(event);
  if (!g_CanUseOrgAddr) {
  	f_InitDivData();
  	return;
  }
  if (!g_MakeRequest && g_LstReqInnerTxt != g_NewInnerTxt) {
  	ousReq();
  } else if (!g_MakeRequest && 
  	         (event.keyCode == 32 || event.keyCode == 8)) {
  	f_InitDivData();
  }
}

// 将在个人通讯录搜索到的联系人与在企业通讯录搜索到的联系人
// 加入g_ArrEmailList
function intigrateAddrs() {
	var result = new Array();
	if (g_ArrEmailList == null && g_ArrOUs == null) return;
	if (g_ArrEmailList != null) {
		var eListLen = g_ArrEmailList.length;
//		alert("g_ArrEmailList.length = " + g_ArrEmailList.length);
		for (var i = 0; i < eListLen; i++) {
			var lastIdx = result.length;
			result[lastIdx] = g_ArrEmailList[i];
			result[lastIdx].addrType = "0";
		}
	}
	if (g_ArrOUs != null) {
		var ousLen = g_ArrOUs.length;
//		alert("g_ArrOUs.length = " + g_ArrOUs.length);
		for (var i = 0; i < ousLen; i++) {
			if (!g_ArrOUs[i].email || g_ArrOUs[i].email == "")
			  continue;
			var lastIdx = result.length;
			result[lastIdx] = g_ArrOUs[i];
			result[lastIdx].addrType = "1";
		}
	}
	g_ArrEmailList = result;
}

function addEListIntoTab(oTb) {
	var eListLen = g_ArrEmailList.length;
	eListLen = eListLen > 20 ? 20 : eListLen;
	for (var i = 0; i < eListLen; i++) {
		var addrItem = g_ArrEmailList[i];
		var cn = g_TrCssClassName0;
		if (addrItem.addrType == "1") cn = g_TrCssClassName1;
		oTr = f_CreateRow(oTb);
		oTr.className = cn;
		oTd = f_CreateColumn(oTr, i);
		var sStrongTextName = addrItem.name;
		var sStrongTextAddr = addrItem.email;
		if (addrItem.name.substring(0, g_NewInnerTxt.length) == g_NewInnerTxt) {
			sStrongTextName = "<b>" + g_NewInnerTxt + "</b>" + addrItem.name.substring(g_NewInnerTxt.length, addrItem.name.length);
		}
		if (addrItem.email.substring(0, g_NewInnerTxt.length) == g_NewInnerTxt) {
			sStrongTextAddr = "<b>" + g_NewInnerTxt + "</b>" + addrItem.email.substring(g_NewInnerTxt.length, addrItem.email.length);
		}
		oTd.innerHTML = "&quot;" + sStrongTextName + "&quot;&nbsp;&lt;" + sStrongTextAddr + "&gt;";
	}
}

function callback() {
	g_MakeRequest = false;
	f_InitDivData();
}

function f_InitDivData() {
  f_SetExtDivDisplay(false);
	if (g_CanUseOrgAddr && g_LstReqInnerTxt != g_NewInnerTxt) {
		ousReq();
		return;
	}
	intigrateAddrs();
  var oTb;
  if (g_ArrEmailList != null && g_ArrEmailList.length > 0) {
    g_MainDiv.innerHTML = "";
    oTb = f_CreateTable();
    oTb.className = g_TbCssClassName0;
    g_MainDiv.appendChild(oTb);
    addEListIntoTab(oTb);
    var e = g_InputObject;
    if (!document.getElementById("dvAddListContainer")) {
      var tDiv = document.createElement("div");
      tDiv.id = "dvAddListContainer";
      with (tDiv.style) {
        position = "absolute";
        zIndex = "99";
        display = "none";
        width = height = "0px";
      }

      if (document.all && !g_IgnoreIE) {
        tDiv.innerHTML = '<iframe id="ifAddList" scrolling="no" marginwidth="0" marginheight="0" frameborder="1" height="100%" width="100%"></iframe>';
      }

      document.body.appendChild(tDiv);
    }

    g_AddListContainer = document.getElementById("dvAddListContainer");
    g_AddListContainer.style.left = (f_GetX(e) + 1) + "px";
    g_AddListContainer.style.top = (f_GetY(e) + 24) + "px";
    g_AddListContainer.style.display = "";
    g_AddListContainer.appendChild(g_MainDiv);
    g_MainDiv.style.left = 0;
    g_MainDiv.style.top = 0;
    if (document.all && !g_IgnoreIE) {
      var ifr = document.getElementById("ifAddList");
      ifr.style.width = (oTb.offsetWidth + 1) + "px";
      ifr.style.height = (oTb.offsetHeight + 1) + "px";
    }
    f_SetDivDisplay(true);
  } else {
  	f_SetDivDisplay(false);
  }
}

function recordInnerTxt(event) {
  if (g_InputObject == null) {
    return null;
  }
  var oldInnerTxt = g_NewInnerTxt;
  var s = g_InputObject.value;
  var k = s.length;
  var iLastSign = 0;
  if (s.lastIndexOf(",") > s.lastIndexOf(";")) {
    iLastSign = s.lastIndexOf(",");
  } else {
    iLastSign = s.lastIndexOf(";");
  }

  g_NewInnerTxt = s.substring(iLastSign + 1, k);
  if (event.keyCode == 8) {
    g_NewInnerTxt = s.substring(iLastSign + 1, k - 1);
  }

  g_NewInnerTxt = f_Trim(g_NewInnerTxt);
  g_OldInnerTxt = s.substring(0, iLastSign);
  g_OldInnerTxt = f_Trim(g_OldInnerTxt);
  return oldInnerTxt;
}

function f_GetArrEmailList(event) {
  var arr = new Array();
  var re;
  var j = 0;
  if (g_NewInnerTxt == "") {
    if (event.keyCode == 32)arr = gArrEmailAddress;
  } else {
    try {
      re = new RegExp("^" + g_NewInnerTxt, "i");
      for (var i = 0; i < gArrEmailAddress.length; i++) {
        if (re.test(gArrEmailAddress[i].name) || re.test(gArrEmailAddress[i].email)) {
          arr[j] = gArrEmailAddress[i];
          j++;
        }
      }
    } catch(ex) {
    }
  }
  return arr;
}

function f_EnterKey() {
  f_FillCurrentEmail();
}

function f_EscapeKey() {
  return;
}

function f_BackSpaceKey(obj, event) {
  typeAhead(event);
  if (g_NewInnerTxt == "") {
    f_SetDivDisplay(false);
  }
}

function f_UpKey() {
  if (g_CurrentTd == null) {
    return;
  }

  var k = g_CurrentTd.zIndex - 1;
  if (k == -1) {
    k += 1;
  }

  var oTd = document.getElementById("tdACMA_" + k);
  g_CurrentTd.className = "";
  g_CurrentTd = oTd;
  g_CurrentTd.className = g_TdCssClassName;
  f_SetDivDisplay(true);
}

function f_DownKey() {
  if (g_CurrentTd == null) {
    return;
  }

  var k = g_CurrentTd.zIndex + 1;
  if (k == g_ArrEmailList.length) {
    k -= 1;
  }

  var oTd = document.getElementById("tdACMA_" + k);
  g_CurrentTd.className = "";
  g_CurrentTd = oTd;
  g_CurrentTd.className = g_TdCssClassName;
  f_SetDivDisplay(true);
}

function f_Trim(str) {
  str = str.replace(/(^\s*)|(\s*$)/g, "");
  return str;
}