function Node(id,name,parent,url){
	this.id = id;
	this.name = name;
	this.parent = parent;
	this.childs = [];
	this.haschilds = false;
	this.lastchild = true;
	this.opend = false;
	this.url = url;
}
function WTree(id,rootable,itype,iname){
	this.icon = {root:"js/tree/img/base.gif", folder:"js/tree/img/folder.gif", folderOpen:"js/tree/img/folderopen.gif", node:"js/tree/img/page.gif", empty:"js/tree/img/empty.gif", line:"js/tree/img/line.gif", join:"js/tree/img/join.gif", joinBottom:"js/tree/img/joinbottom.gif", plus:"js/tree/img/plus.gif", plusBottom:"js/tree/img/plusbottom.gif", minus:"js/tree/img/minus.gif", minusBottom:"js/tree/img/minusbottom.gif", nlPlus:"js/tree/img/nolines_plus.gif", nlMinus:"js/tree/img/nolines_minus.gif"};
	this.nodes=[];
	this.tree =null;
	this.id = id;
	this.rootable = rootable;
	this.itype=itype;
	this.iname = iname;
	this.values = [];
}
WTree.prototype.addNode=function(id,name,pid,url,haschilds){
	if(pid == 0){
		this.tree = new Node(id,name,null,url);
		this.nodes[this.nodes.length] = this.tree;
	}else{
		var p = null;
		for(var i = 0 ; i < this.nodes.length;i++){
			if(pid == this.nodes[i].id){
				p = this.nodes[i];
				break;
			}
		}
		var n = new Node(id,name,p,url);
		if(haschilds)
			n.haschilds = haschilds;
		this.nodes[this.nodes.length] = n;
		p.childs[p.childs.length] = n;
		if(p.childs.length>1)
			p.childs[p.childs.length-2].lastchild = false;
		p.haschilds = true;
	}
};
WTree.prototype.setLeft=function(n){
	var str="";
	if(n.parent==null)
		str+="<img src=\""+this.icon.root+"\" id='tree_ind_"+n.id+"'/>";
	else{
		str = this.setInd(n.parent);
		if(n.haschilds)
			str+="<a href='javascript:"+this.id+".open( "+n.id+")'><img id='tree_ind_"+n.id+"' src=\""+(n.lastchild?this.icon.plusBottom:this.icon.plus)+"\"/></a>"+"<img id='tree_folder_"+n.id+"' src=\""+(this.icon.folder)+"\"/>";
		else
			str+="<img src=\""+(n.lastchild?this.icon.joinBottom:this.icon.join)+"\"/>"+"<img src=\""+(this.icon.node)+"\"/>";
	}
	return str;
}
WTree.prototype.setInd=function(n){
	var str="";
	if(n.parent!=null){
		str+=this.setInd(n.parent);
		str+="<img src=\""+(n.lastchild?this.icon.empty:this.icon.line)+"\"/>";
	}
	return str;
}
WTree.prototype.fabricate=function(n){
	var cs = n.childs;
	var str = "<div id='tree_header_"+n.id+"'>";
	//设置左侧的玩意
	str +=this.setLeft(n);
	var input="";
	if(this.itype=="ra")
		input="<input type='radio' name='"+this.iname+"' value='"+n.id+"'/>&nbsp;&nbsp;";
	if(this.itype=="cb")
		input="<input type='checkbox' name='"+this.iname+"' value='"+n.id+"'/>&nbsp;&nbsp;";
	if(n.parent==null){
		if(this.rootable){
			str+=input+"<a href=\""+((n.url==null||n.url=="")?"javascript:void(0);":n.url)+"\">"+ n.name+ "</a>";
		}else
			str+=n.name;
	}else{
		str+=input+"<a href=\""+((n.url==null||n.url=="")?"javascript:void(0);":n.url)+"\">"+ n.name+ "</a>";
	}
	str+="</div>";
	str+="<div id='tree_child_"+n.id+"' class='child'>"
	for(var i =0; i <cs.length;i++){
		str+=this.fabricate(cs[i]);
	}
	str+="</div>";
	return str;
}
WTree.prototype.open=function(id){
	for(var i = 0 ; i < this.nodes.length;i++){
		var n =this.nodes[i] ;
		if(id == n.id){
			if(n.opend){
				$("#tree_child_"+id).css("display","none");
				n.opend = false;
			}else{
				$("#tree_child_"+id).css("display","block");
				n.opend = true;
			}
			if(n.parent!=null&&n.haschilds){
				$("#tree_ind_"+id).attr("src",!n.opend?(n.lastchild?this.icon.plusBottom:this.icon.plus):(n.lastchild?this.icon.minusBottom:this.icon.minus));
				$("#tree_folder_"+id).attr("src",!n.opend?this.icon.folder:this.icon.folderOpen);
			}
			break;
		}
	}
}
WTree.prototype.openp=function(n){
 	if(!n.opend)
 		this.open(n.id);
 	if(n.parent!=null)
 		this.openp(n.parent);
}
WTree.prototype.doShow=function(){
	document.write("<div class='wtree'>"+this.fabricate(this.tree)+"</div>");
	this.open(this.tree.id);
	//this.setValues();
};
WTree.prototype.setValues=function(){
	var dv = [];
	if(this.values){
		var m = 0;
		dv = this.values;
		if(dv.length>0)
			for(var i = 0 ; i < this.nodes.length;i++){
				var n =this.nodes[i] ;
				for(var j=0;j<dv.length;j++){
					if(dv[j]==n.id){
						var inps = $("#tree_header_"+n.id).find("input");
						if(inps.length>0)
							$(inps[0]).attr("checked","checked");
						//if(!n.opend)
						this.openp(n.parent);
						m++;
					}
				}
				if(m==dv.length)
					break;
			}
	}
	else
		return false;
	
}