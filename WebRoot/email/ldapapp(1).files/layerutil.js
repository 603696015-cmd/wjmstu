var g_layerutil_loaded = true;
function getLayer(n,doc)
{
	var x;
	if(!doc) var doc=document;
	if(!(x=doc[n])&&doc.all) x=doc.all[n];
	for(i=0;!x&&doc.layers&&i<doc.layers.length;i++) x=getLayer(n,doc.layers[i].document);
	if(!x && doc.getElementById) x=doc.getElementById(n);
	try {
	    if(x.style) x=x.style;
	}
	catch (e) {}
	return x;
}
function shLayer(){
    var i,v,d,obj,args=shLayer.arguments;
    for (i=0;i<(args.length-2);i+=3){
	    if ((obj=getLayer(args[i]))!=null){
		    v=args[i+2];
		    v=(v=='show')?'visible':(v='hide')?'hidden':v;
		    d=args[i+2];
		    d=(d=='show')?'':(d='hide')?'none':d;
	    }
	    var setted = 0;
	    try {
	        if (obj.display) {
	            obj.display=d;
	            setted = 1;
	        }
	    } catch(e) {}
	    try {
	        if (obj.visibility) {
	            obj.visibility=v; 
	            setted = 1;
	        }
	    } catch(e) {}
	    if (setted == 0) {
	        if (document.getElementById) {
	            document.getElementById(args[i]).style.display = d;
            }
	    }
    }
}
function moveLayer(n,x,y)
{
	var obj=getLayer(n);obj.left=x;obj.top=y;
}

function showLayer(name,objButn)
{
	var left=0;
	var top=0;
	var p=objButn;
	while(p && p.tagName!="BODY")
	{
		left+=p.offsetLeft;
		top+=p.offsetTop;
		p=p.offsetParent;
	}

	moveLayer(name,left,top);
	shLayer(name,'','show');
}

function showLayerRightward( name, objButn, space)
{
	var left=0;
	var top=0;
	var p=objButn;
	while(p && p.tagName!="BODY")
	{
		left+=p.offsetLeft;
		top+=p.offsetTop;
		p=p.offsetParent;
	}
	left+= space;

	moveLayer(name,left,top);
	shLayer(name,'','show');
}
function getObjLeft( objButn )
{
	var left = 0;
	var p=objButn;
	while(p && p.tagName!="BODY")
	{
		left+=p.offsetLeft;
		p=p.offsetParent;
	}
	return left;
}
function getObjTop( objButn )
{
	var top=0;
	var p=objButn;
	while(p && p.tagName!="BODY")
	{
		top+=p.offsetTop;
		p=p.offsetParent;
	}
	return top;
}

//---------------------------------------
function editor_getLayer(name)
{
//    return getLayer(name);
  var obj = document.getElementById(name);
  if ( obj ) return obj;
  return false;
}

function editor_hideLayer(name) {
//	shLayer(name, '', 'hide');
  var layer = editor_getLayer(name);
  if ( !layer ) return false;
  if ( layer ) 
  layer.style.display = "none";
}

function editor_showLayer(name) {
//	shLayer(name, '', 'show');
  var layer = editor_getLayer(name);
  
  if ( !layer ) return false;
  if ( layer ) 
  
  layer.style.display = "";
}


