function tipsNoAccess() {
	alert('您尚未开通此项服务请升级');
}
function isIE()
{
	try {
	if (document.all) return true;
	}
	catch (e) {
		return false;
	}
	return false;
}
function debug( str )
{
	// alert( str );
}
function setObjCursor( theobj , thecursor)
{
	if (theobj.style) {
		theobj.style.cursor = thecursor;
	}
	else if (theobj.cursor) {
		theobj.cursor = thecursor;
	}
}
function setObjCursorByDisableStauts( theObj )
{
	if (theObj.disabled) {
		setObjCursor( theObj, 'pointer' );
	}
	else {
		setObjCursor( theObj, 'hand' );
	}
}

