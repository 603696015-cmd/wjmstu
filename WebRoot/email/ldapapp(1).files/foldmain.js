    function opFolder( op )
    {
        if (CheckChar())
        {
            document.makenewfolder.action += "?" + op + "=1";
            document.makenewfolder.submit();
        }
    }
    function chooseNewFdrType(type)
    {
    	document.getElementById("idNewFdrType").name = type;
    }


//----------------------------------------------------------------------
var foldmain_tips_emptyFolderName = "请填写文件夹名称";
var foldmain_tips_errorFolderName = "文件夹不能包含'\\','/'字符!";
var foldmain_tips_DeleteFolder = "您真的要删除这个文件夹么？";
var foldmain_tips_EmptyFolder = "您真的要删除这个文件夹下的所有邮件么？";
var foldmain_tips_InputFolderName = "请输入文件夹名";
var foldmain_tips_EmptyFolderName = "文件夹名不能为空";
var foldmain_tips_FolderNameTooLong = "文件夹名字太长";
function CheckChar()
{
   var r,re;
   var aaa = document.makenewfolder.newfoldername.value;
   if ( aaa == "") {
   	alert(foldmain_tips_emptyFolderName);
   	return false;
   }
   re = /\\/gi;
   r = aaa.search(re);
   if (r >= 0 ) {  
    alert(foldmain_tips_errorFolderName);
    return false;
   }
   else {
   	re = /\//gi;
   	r = aaa.search(re);
   	if ( r>= 0 ) {
	    alert(foldmain_tips_errorFolderName);
	    return false;
   	}
   	return true;
   }
}

function tipsDelete( url )
{
	if ( confirm(foldmain_tips_DeleteFolder) ) {
		window.location = url;
	}
	return;
}
function getMainFrame() {
	var o = parent.main;
	if (!o) {
		o = parent.parent.main;
	}
	if (!o) {
		o = parent.parent.parent.main;
	}
	if (!o) {
		o = parent.parent.parent.parent.main;
	}
	return o;
}
function tipsEmpty( url )
{
	if ( confirm(foldmain_tips_EmptyFolder) ) {
//    	window.location = url;
		var mainFrame = getMainFrame();
		if (mainFrame) {
			mainFrame.location = url;
		}
	}
	return;
}
function renameFdr( parentfid )
{
	var nn = window.prompt(foldmain_tips_InputFolderName,"");
	if (nn==null || nn=="null") {
		return;
	}
	if( nn=="")	{
		alert(foldmain_tips_EmptyFolderName);
		return;
	}
	if (nn.length > 20 ) {
		alert(foldmain_tips_FolderNameTooLong);
		return;
	}

	var dml = document.frmRenameFdr;
	dml.parentfid.value = parentfid;
	dml.newfoldername.value = nn;
	dml.submit();
}
