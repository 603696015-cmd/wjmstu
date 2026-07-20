function hideDefaultLogo()
{
	var defLogo = document.getElementById("defaultLogo");
	if (defLogo) {
		defLogo.style.display = "none";
	}
}

function adjustLogoHeight( obj )
{
	if (obj.height > 50 ) {
		obj.height = 50;
	}
}

function f_GetX(e){
	var l=e.offsetLeft;
	while(e=e.offsetParent){				
		l+=e.offsetLeft;
	}
	return l ;
}

var bOver = false;
function fMouseMove(obj)
{
	id = getElementId(obj);
	if( id != "toolbar_link" && id != "toolbar" && bOver)
	{	
		document.getElementById("toolbar").style.display = "none";
		bOver = false;
	}
}

function getElementId( e)
{
	if( e == null)
		return;
	if( e.id == "")
	{
		if( e.parentNode.id != null )
		{	
			return e.parentNode.id;
		}
	}
	else
		return e.id;
}
