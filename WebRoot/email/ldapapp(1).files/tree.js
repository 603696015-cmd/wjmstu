// fdrAttrs: index, id, parentid, name, url, img, level, display
//  tree param:  initShowLevel
//  tree service:

var gItemStr = "<span id=fdrItemID><table width=100% border=0 cellpadding=0 cellspacing=0 background=/images/an02.gif>\r\n <tr class=unnamed1>\r\n  <td width=WIDTH1 align=right><img src='IMG' id=fdrImgID onclick='showhideChild(CHILDID)' style='cursor:pointer'  onmouseout='MM_swapImgRestore()' onmouseover=MM_swapImage('fdrImgID','','SWAPImg',1)></td>\r\n  <td width=WIDTH2>LINK<font color=#000000>NAME</font>LINKEND EXTLINK<font color=#000000>EXTNAME</font>EXTLINKEND</td>\r\n </tr></table>\r\nCHILD\r\n</span>";
var gSubItemStr = "\r\n<!-- child ID begin -->\r\n<span id=fdrSubItemID style='display:DISPLAY'>\r\nCONTENT\r\n</span>\r\n<!-- child ID end -->\r\n";
var gLinkStr = "<a href='URL' target=TARGET>";
var gLinkEndStr = "</a>";

var greLink = /LINK/g;
var greLinkEnd = /LINKEND/g;

var greMyFid = /_MFD_/g;
var greExtName = /_EN_/g;

var greSubItemContent = /CONTENT/g;
var greWidth1 = /WIDTH1/g;
var greWidth2 = /WIDTH2/g;
var greChildID = /CHILDID/g;
var greID = /ID/g;
var greName = /NAME/g;
var greURL = /URL/g;
var greSwapImg = /SWAPImg/g;
var greImg = /IMG/g;
var greDisplay = /DISPLAY/g;
var greChild = /CHILD/g;
var greTarget = /TARGET/g;
var greLevel = /LEVEL/g;

var tree_info_fold = "fold";
var tree_info_unfold = "unfold";

function fdrItem(id, parentid, name, url, level, img, folderimg, swapimg, target)
{
    this.id = id; this.parentid=parentid;
    this.name=name; this.url=url; this.level=level;
    this.img = img; this.folderimg = folderimg; this.swapimg = swapimg;
    this.target = target;
    
    this.display = false; this.dealed = false;
    this.isfolded = false;
    
    this.getItemStr = getFdrItemStr;
    this.hasChild = itemHasChild;
}
function itemHasChild()
{
    var i=0;
    for (i=0; i<fdritems1.length; i++ ) {
        if (fdritems1[i].parentid>=0 && fdritems1[i].parentid==this.id)
            return true;
    }
    return false;
}
function getFdtItemLevel( idx )
{
    if (idx < 0 || idx > fdritems1.length) return -1;
    return fdritems1[ idx ].level;
}
var fdritem_margin_left = 65;
var fdritem_indent_space = 8;
var g_folder_tips_emptyFolder = "";
function getFdrItemStr()
{
    var itemStr = gItemStr;
    // width's length
    var width1 = fdritem_margin_left + fdritem_indent_space*this.level;//Math.round(85*(this.level+1)/(this.level+2)) + "%";
    var width2 = "";//Math.round(300/(this.level+2)) + "%";

    if (this.display) {
        itemStr = itemStr.replace(greDisplay, '');
    }
    else {
        itemStr = itemStr.replace(greDisplay, 'none');
    }
    itemStr = itemStr.replace(greWidth1, width1);
    itemStr = itemStr.replace(greWidth2, width2);

    // if has child folder & level=initShowLevel, is folderimg
    var imgIsFolder = false;
    if (this.hasChild()) {
        itemStr = itemStr.replace(greChildID, this.id);
        if (this.level >= initShowLevel)
            imgIsFolder = true;
    }
    else
        itemStr = itemStr.replace(greChildID, '-1');
    
    itemStr = itemStr.replace(greSwapImg, this.swapimg);
    if (imgIsFolder) {
        itemStr = itemStr.replace(greImg, this.folderimg);
        this.isfolded = true;
    }
    else {
        itemStr = itemStr.replace(greImg, this.img);
        this.isfolded = false;
    }

    itemStr = itemStr.replace(greID, this.id);
    itemStr = itemStr.replace(greName, this.name);
    itemStr = itemStr.replace(greTarget, this.target);
    itemStr = itemStr.replace(greLevel, this.level);
    itemStr = itemStr.replace(greURL, this.url);
    var linkstr = "";
    var linkendstr = "";
    if (this.url !=null && this.url != "") {
      linkstr = gLinkStr;
      linkstr = linkstr.replace(greURL, this.url);
      linkstr = linkstr.replace(greTarget, this.target);
      linkendstr = gLinkEndStr;
    }
    itemStr = itemStr.replace(greLinkEnd, linkendstr);
    itemStr = itemStr.replace(greLink, linkstr);
    
    var extLinkName = "";
    if (this.fid && (this.fid == 4 || this.fid == 5)) {
      if (g_folder_tips_emptyFolder != "")
          extLinkName = g_folder_tips_emptyFolder;
      itemStr = itemStr.replace(greMyFid, this.fid);
    }
    itemStr = itemStr.replace(greExtName, extLinkName);
    
    return itemStr;
}


// getFdrStr will change item's dealed status !!!
function getFdrStr( id )
{
    if (id<0 || id>=fdritems1.length) return "";
    if (fdritems1[id].dealed) return "";
    
    fdritems1[id].dealed = true;
    
    var childStr = "";
    var itemStr = fdritems1[id].getItemStr();
    var i = 0;
    for ( i=0; i<fdritems1.length; i++) {
        if (fdritems1[i].dealed) continue;
        if (fdritems1[i].parentid == id && fdritems1[i].parentid != fdritems1[i].id) {
            var subItemStr = gSubItemStr;
            subItemStr = subItemStr.replace(greID, fdritems1[i].id );
            if (fdritems1[i].display) {
                subItemStr = subItemStr.replace(greDisplay, '');
            }
            else {
                subItemStr = subItemStr.replace(greDisplay, 'none');
            }

            var childContent = getFdrStr( fdritems1[i].id );
            subItemStr = subItemStr.replace(greSubItemContent, childContent);
            childStr += subItemStr;
        }
    }
    itemStr = itemStr.replace(greChild, childStr);
    
    return itemStr;
}
function clearDealedStatus( fdrItemArray )
{
    var i = 0;
    for ( i=0; i<fdrItemArray.length; i++) fdrItemArray[i].dealed = false;
}
function showFdrItems()
{
    clearDealedStatus(fdritems1);
    var i = 0;
    var fdrItemsStr = "";
    for ( i=0; i<fdritems1.length; i++)
        fdrItemsStr += getFdrStr(fdritems1[i].id);
        
//  prompt("",fdrItemsStr);
    document.write(fdrItemsStr);
}
function initFdrItems()
{
    var i = 0;
    for ( i=0; i<fdritems1.length; i++) {
        if (fdritems1[i].dealed) continue;
        if (fdritems1[i].level > initShowLevel)
            fdritems1[i].display = false;
        else
            fdritems1[i].display = true;
        //  set image
    }
}
function foldFdr( id )
{
    if (id<0 || id>=fdritems1.length)   return;

    showhideChild( id );

    var folderId = 'fdrFolder' + id;
    var folder = document.getElementById(folderId);
    if (folder!=null) {
        if (fdritems1[id].isfolded) {
            folder.childNodes[0].nodeValue = tree_info_unfold;
        }
        else {
            folder.childNodes[0].nodeValue = tree_info_fold;
        }
    }
}
//  folder:  折叠器
//  fdr:     文件夹
function updateFolderStatus()
{
    var i = 0;
    for (i=0; i<fdritems1.length; i++) {
        var fdrId = fdritems1[i].id;
        var folderId = 'fdrFolder' + fdrId;
        var folder = document.getElementById(folderId);
        if (folder) {
            if (fdritems1[i].hasChild()) {
                folder.style.display = '';
                if (fdritems1[i].isfolded) {
                    folder.childNodes[0].nodeValue = tree_info_unfold;
                }
                else {
                    folder.childNodes[0].nodeValue = tree_info_fold;
                }
            }
            else {
                folder.style.display = 'none';
            }
        }
    }
}
function showhideChild( id )
{
    if (id<0 || id>=fdritems1.length)   return;
    // show hide fdritemID's child
    clearDealedStatus(fdritems1);
    var i = 0;
        
    if (fdritems1[id].isfolded) {
        fdritems1[id].isfolded = false;
    }
    else {
        fdritems1[id].isfolded = true;
    }

    var ImgId = 'fdrImg' + id;
    var Img = document.getElementById(ImgId);
    if (Img!=null && Img!="") {
        if (fdritems1[id].isfolded) {
            if (Img.src) Img.src = fdritems1[id].folderimg;
            if (Img.oSrc) Img.oSrc = fdritems1[id].folderimg;
        }
        else {
            if (Img.src) Img.src = fdritems1[id].img;
            if (Img.oSrc) Img.oSrc = fdritems1[id].img;
        }
    }
    for ( i=0; i<fdritems1.length; i++) {
        if (fdritems1[i].dealed) continue;
        
        if ( fdritems1[i].parentid == id ) {
            fdritems1[i].dealed = true;
            var subItemId = 'fdrSubItem' + fdritems1[i].id;
            var subItem = document.getElementById(subItemId);
            if (subItem == null || subItem=="" ) {
                return;
            }
            if (fdritems1[id].isfolded) {
                subItem.style.display = 'none';
            }
            else {
                subItem.style.display = '';
            }
        }
    }
}


/////////////// below structure is structure of CGI's output /////////////////////
function fdrItemOfCGI(index, id, fid, parentid, level, name, info, type, haschild, is_begin_in_this_level, is_end_in_this_level)
{
    this.id = id;
    this.fid = fid;
    this.parentid = parentid;
    this.level = level;
    this.name = name;
    this.info = info;
    this.img = "";
    this.folderimg = "";
    this.swapimg = "";
    
    this.dealed = false;
}
        
function sortFdrItemArray()
// bubble sort
{
  var size = fdritems.length;
  var swapItem = new Array();
  var i=0, j=0;
  for (i=0; i<size; i++)  {
    for (j=1; j<(size-i); j++) {
      if (fdritems[j-1].id>fdritems[j].id) {
        // swap it
        swapItem[0] = fdritems[j];
        fdritems[j] = fdritems[j-1];
        fdritems[j-1] = swapItem[0];
      }
    }
  }
}        
function AddToFdrArray( start )
{
  clearDealedStatus(fdritems);
    var i = 0;
    // change id and parent id -> index
    for ( i=0; i<fdritems.length; i++ )
    {
        if (!fdritems[i].dealed) changeCGIParentID( i );
    }
    // -> fdrItems1
    for ( i=0; i<fdritems.length; i++ )
    {
        var id = fdritems[i].id + start;
        var parentid = fdritems[i].parentid + start;
        if (parentid<start) parentid=-1;
        
        fdritems1[start + i] = new fdrItem( id, parentid, fdritems[i].name, fdritems[i].url, fdritems[i].level, fdritems[i].img, fdritems[i].folderimg, fdritems[i].swapimg, linkTarget);
        // 为清空文件夹操作添加一个属性
        fdritems1[start + i].fid = fdritems[i].fid;
    }
}

function changeCGIParentID( index )
{
    if (fdritems[index].dealed) return;
    
    fdritems[index].dealed = true;
    var id = fdritems[index].id;
    
    var i = 0;
    for ( i=0; i<fdritems.length; i++ )
    {
        if ( fdritems[i].dealed) continue;
        if ( fdritems[i].parentid == id && id>=0 ) // is its child
        {
            //because child's parentid will compare with id, so must change childs parentid first
            changeCGIParentID( i ); 
            fdritems[i].parentid = index;
        }
    }
    // now all child has changed their parent id, so can change self's id
    fdritems[index].id = index;
}
        
        
        
