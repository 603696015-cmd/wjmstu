
/*--------------------------------------------------|

| dTree 2.05 | www.destroydrop.com/javascript/tree/ |

|---------------------------------------------------|

| Copyright (c) 2002-2003 Geir LandrÃ¯Â¿Â½               |

|                                                   |

| This script can be used freely as long as all     |

| copyright messages are intact.                    |

|                                                   |

| Updated: 17.04.2003                               |

|--------------------------------------------------*/



// Node object
function Node(id, pid, name, url, title, target, icon, iconOpen, open) { 
	this.id = id;				//èç¹id
	this.pid = pid;				//pid
	this.name = name;			//èç¹å
	this.url = url;				//èç¹é¾æ¥
	this.title = title;			//èç¹æ é¢
	this.target = target;
	this.icon = icon;
	this.iconOpen = iconOpen;
	this._io = open || false;
	this._is = false;
	this._ls = false;
	this._hc = true;
	this._ai = 0;
	this._p;
	this.hasapp = false;
	this.level = 0;
}

function Node(id, pid, name, url, title) { 
	this.id = id;				//èç¹id
	this.pid = pid;				//pid
	this.name = name;			//èç¹å
	this.url = url;				//èç¹é¾æ¥
	this.title = title;			//èç¹æ é¢
}

// Node object //--//
function Node(id, pid, name, url,ischk,treeType, title, target, icon, iconOpen, open) {  
	this.id = id;				//èç¹id
	this.pid = pid;				//pid
	this.name = name;			//èç¹å
	this.url = url;				//èç¹é¾æ¥
	this.ischk=ischk;			//å¤æ­æ¯å¦ éä¸­å¤éæ¡
	this.treeType=treeType;
	this.title = title;			//èç¹æ é¢
	this.target = target;
	this.icon = icon;
	this.iconOpen = iconOpen;
	this._io = open || false;
	this._is = false;
	this._ls = false;
	this._hc = true;
	this._ai = 0;
	this._p;
	this.hasapp = false;
	this.level = 0;
}



// Tree object
function dTree(inputtype,inputname,ivalue,objName,url) { 
	this.config = {target:null, folderLinks:true, useSelection:true, useCookies:false, useLines:true, useIcons:true, useStatusText:false, closeSameLevel:false, inOrder:false};
	this.icon = {root:"js/tree/img/base.gif", folder:"js/tree/img/folder.gif", folderOpen:"js/tree/img/folderopen.gif", node:"js/tree/img/page.gif", empty:"js/tree/img/empty.gif", line:"js/tree/img/line.gif", join:"js/tree/img/join.gif", joinBottom:"js/tree/img/joinbottom.gif", plus:"js/tree/img/plus.gif", plusBottom:"js/tree/img/plusbottom.gif", minus:"js/tree/img/minus.gif", minusBottom:"js/tree/img/minusbottom.gif", nlPlus:"js/tree/img/nolines_plus.gif", nlMinus:"js/tree/img/nolines_minus.gif"};
	this.obj = objName;
	this.aNodes = [];
	this.aIndent = [];
	this.root = new Node(-1);
	this.selectedNode = null;
	this.selectedFound = false;
	this.completed = false;
	this.inputtype=inputtype;
	this.inputname=inputname;
	this.ivalue=ivalue;
	this.iurl = url;
}



// Adds a new node to the node array
dTree.prototype.add = function (id, pid, name, url, title, target, icon, iconOpen, open) {
	
	this.aNodes[this.aNodes.length] = new Node(id, pid, name, url, title, target, icon, iconOpen, open);
};

// Adds a new node to the node array
dTree.prototype.add2 = function (id, pid, name, url,ischk,treeType, title, target, icon, iconOpen, open) {
	//alert(treeType);
	this.aNodes[this.aNodes.length] = new Node(id, pid, name, url,ischk,treeType, title, target, icon, iconOpen, open);
};



// Open/close all nodes
dTree.prototype.openAll = function () {
	this.oAll(true);
};
dTree.prototype.closeAll = function () {
	this.oAll(false);
};



// Outputs the tree to the page
dTree.prototype.toString = function () {
	var str = "<div class=\"dtree\">\n";
	if (document.getElementById) {
		if (this.config.useCookies) {
			this.selectedNode = this.getSelected();
		}
		str += this.addNode(this.root);
	} else {
		str += "Browser not supported.";
	}
	str += "</div>";
	if (!this.selectedFound) {
		this.selectedNode = null;
	}
	this.completed = true;
	return str;
};



// Creates the tree structure
dTree.prototype.addNode = function (pNode) {
	var str = "";
	var n = 0;
	if (this.config.inOrder) {
		n = pNode._ai;
	}
	for (n; n < this.aNodes.length; n++) {
		if (this.aNodes[n].pid == pNode.id) {
			var cn = this.aNodes[n];
			cn._p = pNode;
			cn._ai = n;
			this.setCS(cn);
			if (!cn.target && this.config.target) {
				cn.target = this.config.target;
			}
			if (cn._hc && !cn._io && this.config.useCookies) {
				cn._io = this.isOpen(cn.id);
			}
			if (!this.config.folderLinks && cn._hc) {
				cn.url = null;
			}
			if (this.config.useSelection && cn.id == this.selectedNode && !this.selectedFound) {
				cn._is = true;
				this.selectedNode = n;
				this.selectedFound = true;
			}
			str += this.node(cn, n);
			if (cn._ls) {
				break;
			}
		}
	}
	return str;
};

function setid(obj){
	//alert("nihao");
	obj.name="department.id";
}

// Creates the node icon, url and text
var k=0;
var m=0;
var n=0;
dTree.prototype.node = function (node, nodeId) {
	var str = "<div class=\"dTreeNode\">" + this.indent1(node, nodeId);
	if (this.config.useIcons) {
		if (!node.icon) {
			node.icon = (this.root.id == node.pid) ? this.icon.root : ((node._hc) ? this.icon.folder : this.icon.node);
		}
		if (!node.iconOpen) {
			node.iconOpen = (node._hc) ? this.icon.folderOpen : this.icon.node;
		}
		if (this.root.id == node.pid) {
			node.icon = this.icon.root;
			node.iconOpen = this.icon.root;
		}
		str += "<img id=\"i" + this.obj + nodeId + "\" src=\"" + ((node._io) ? node.iconOpen : node.icon) + "\" alt=\"\" />";
		if(this.inputtype=="cb"&&node.id>0){
			if(this.ivalue==node.id){
			str+="<input type=\"checkbox\" style=\"margin:0px;\" name=\""+this.inputname+
			"\" value=\""+node.id+"\" checked=\"checked\"/> ";
			}
			else{
			str+="<input type=\"checkbox\" style=\"margin:0px;\" name=\""+this.inputname+
			"\" value=\""+node.id+"\" /> ";
			
			}
			}
		if(this.inputtype=="ra"&&node.id>0){ 
			if(this.ivalue==node.id)
			str+="<input type=\"radio\" style=\"margin:0px;\" onclick=\"setid(this);\" name=\""+this.inputname+
			"\" value=\""+node.id+"\" checked=\"checked\" id=\"depId\"/> ";
			else
			str+="<input type=\"radio\" style=\"margin:0px;\" onclick=\"setid(this);\" name=\""+this.inputname+
			"\" value=\""+node.id+"\" /> ";
			} 
		if(this.inputtype=="ra_1no"&&node.id>0){
			if(this.ivalue==node.id)
			str+=""; 
			else
			//str+="<input type=\"radio\" style=\"margin:0px;\" name=\""+this.inputname+
			//"\" value=\""+node.id+"\" /> ";
			str+="<input type=\"radio\" style=\"margin:0px;\" alt=\""+node.title+"_"+node.name+"_"+node.id+"\" name=\""+this.inputname+
			"\" value=\""+node.id+"\" /> ";
			}
		if(this.inputtype=="ra_return"&&node.id>0){
			if(this.ivalue==node.id){
			str+="<input type=\"radio\" style=\"margin:0px;\" name=\""+this.inputname+
			"\" value=\""+node.id+"\" checked=\"checked\"/> ";
			}else{
			if(node.id == 1){str+="";}else{
			str+="<input type=\"radio\" style=\"margin:0px;\" name=\""+this.inputname+
			"\" value=\""+node.id+"\"/> ";}
			}
		}
		//alert(this.inputtype);
		if(this.inputtype=="ra_2no"&&node.id>0){//hdl
			k++;
			if(k==2){
				//str+="";
				 str+="<input type=\"radio\" style=\"margin:0px;\" name=\""+this.inputname+
				 "\" value=\""+node.id+"\" checked=\"checked\"/> ";
			}else{
				if(this.ivalue==node.id){
				 str+="";
				
				}else{
					str+="<input type=\"radio\" style=\"margin:0px;\" name=\""+this.inputname+
					"\" value=\""+node.id+"\" /> ";
				}
			}
		}
		//alert(node.id); 
		if(this.inputtype=="cb_2"&&node.id>=0){  
			m++;
			if(m==1){
				if(node.treeType=="stuf"||node.treeType=="depl"){//å...ç´ æåºæ  æç¹ä¸ä¸æ · 
					if(node.ischk==1){ 
							//str+="<input title=\""+node.treeType+node.id+"_"+node.pid+"\" id=\""+node.treeType+node.id+"\" type=\"checkbox\" style=\"margin:0px;\" name=\"chkName\" value=\""+node.id+"\" onclick='selectEd(\""+node.treeType+"\",this.title,1);' checked=\"checked\" /> ";
							str+="<input title=\""+node.treeType+node.id+"_"+node.pid+"\" id=\""+node.treeType+node.id+"\" type=\"checkbox\" style=\"margin:0px;\" name=\"chkName\" value=\""+node.id+"\" checked=\"checked\"  alt=\""+node.name+"\"/> ";
						}else{ 
							//str+="<input title=\""+node.treeType+node.id+"_"+node.pid+"\" id=\""+node.treeType+node.id+"\" type=\"checkbox\" style=\"margin:0px;\" name=\"chkName\" value=\""+node.id+"\" onclick='selectEd(\""+node.treeType+"\",this.title,1);' /> ";
							str+="<input title=\""+node.treeType+node.id+"_"+node.pid+"\" id=\""+node.treeType+node.id+"\" type=\"checkbox\" style=\"margin:0px;\" name=\"chkName\" value=\""+node.id+"\"  alt=\""+node.name+"\" /> ";
						}
				}else{ 
					str+="";
				}
				
			}else{ 
				if(node.treeType!="depl"){
					if(this.ivalue==node.id){
						str+="<input title=\""+node.treeType+node.id+""+node.id+"_"+node.pid+"\" id=\""+node.treeType+node.id+""+node.id+"\" type=\"checkbox\" style=\"margin:0px;\" name=\"chkName\" value=\""+node.id+"\" checked=\"checked\" alt=\""+node.name+"\" onclick='selectEd(\""+node.treeType+"\",this.title,1);'/> ";
					}
					else{
						//alert(node.ischk);
						//alert(node.treeType);
						if(node.ischk==1){
							str+="<input title=\""+node.treeType+node.id+"_"+node.pid+"\" id=\""+node.treeType+node.id+"\" type=\"checkbox\" style=\"margin:0px;\" name=\"chkName\" alt=\""+node.name+"\" value=\""+node.id+"\" onclick='selectEd(\""+node.treeType+"\",this.title,1);' checked=\"checked\" /> ";
						}else{
							str+="<input title=\""+node.treeType+node.id+"_"+node.pid+"\" id=\""+node.treeType+node.id+"\" type=\"checkbox\" style=\"margin:0px;\" name=\"chkName\" alt=\""+node.name+"\" value=\""+node.id+"\" onclick='selectEd(\""+node.treeType+"\",this.title,1);' /> ";
						}
					}
				}else{
					if(this.ivalue==node.id){
						str+="<input title=\""+node.treeType+node.id+""+node.id+"_"+node.pid+"\" id=\""+node.treeType+node.id+""+node.id+"\" type=\"checkbox\" style=\"margin:0px;\" name=\"chkName\" value=\""+node.id+"\" alt=\""+node.name+"\" checked=\"checked\" /> ";
					}
					else{
						//alert(node.ischk);
						//alert(node.treeType);
						if(node.ischk==1){
							str+="<input title=\""+node.treeType+node.id+"_"+node.pid+"\" id=\""+node.treeType+node.id+"\" type=\"checkbox\" style=\"margin:0px;\" name=\"chkName\" value=\""+node.id+"\" alt=\""+node.name+"\" checked=\"checked\" /> ";
						}else{
							str+="<input title=\""+node.treeType+node.id+"_"+node.pid+"\" id=\""+node.treeType+node.id+"\" type=\"checkbox\" style=\"margin:0px;\" name=\"chkName\" value=\""+node.id+"\"  alt=\""+node.name+"\"/> ";
						}
					}
				}
			}
			//node.url=false;
		}
	}
	if (node.url) {
		str += "<a id=\"s" + this.obj + nodeId + "\" class=\"" + ((this.config.useSelection) ? ((node._is ? "nodeSel" : "node")) : "node") + "\" href=\"" + node.url + "\"";
		if (node.title) {
			str += " title=\"" + node.title + "\"";
		}
		if (node.target) {
			str += " target=\"" + node.target + "\"";
		}
		if (this.config.useStatusText) {
			str += " onmouseover=\"window.status='" + node.name + "';return true;\" onmouseout=\"window.status='';return true;\" ";
		}
		if (this.config.useSelection && ((node._hc && this.config.folderLinks) || !node._hc)) {
			str += " onclick=\"javascript: " + this.obj + ".s(" + nodeId + ");\"";
		}
		str += ">";
	} else {
		
		if ((!this.config.folderLinks || !node.url) && node._hc && node.pid != this.root.id) {
			str += "<a href=\"javascript: " + this.obj + ".o(" + nodeId + ");\" class=\"node\">";
		}
	}
	str += node.name;
	if (node.url || ((!this.config.folderLinks || !node.url) && node._hc)) {
		str += "</a>";
	}
	str += "</div>";
	if (node._hc) {
		str += "<div id=\"d" + this.obj + nodeId + "\" class=\"clip\" style=\"display:" + ((this.root.id == node.pid || node._io) ? "block" : "none") + ";\">";
		str += this.addNode(node);
		str += "</div>";
	}
	this.aIndent.pop();
	return str;
};


function selectEd(treeType,id,v){
	//v++;
	//alert(v);
	//alert(id);
	//alert(treeType);
	var qid=id;
	var oid=id;
	var qid_oid;
	var qid_qid;
	var oid_oid=oid.substr(4,oid.indexOf("_")-4);//???
	var oid_qid=oid.substr(oid.lastIndexOf("_")+1,oid.length);
	//alert(oid);
	//alert(qid.substr(qid.lastIndexOf("_")+1,qid.length));
	var array=document.getElementsByTagName("input");
	//var ts= str.match(eval("/"+treeType+"[\d]{1,}/"));
	//var ts=new RegExp(""+treeType+"[\d]{1,}");
	//var ts=ts;
	//var ts=/^ctyp[\d]{1,}/;
	//alert(id);
	//var ts=/^treeType[\d]{1,}/;
	//var ts=new RegExp();
	
	var ts=getRegExp(treeType);
	//alert("1"+ts.test(id+""));
	for(var i=0;i<array.length;i++){
		//alert(ts.test(id+""));
		if(array[i].type=="checkbox"&&ts.test(id+"")){
			//alert(array[i].type);
			//alert(obj.id);
			//alert(ts.test("ctype1"));
			//æ¾å°qlibéåäº
			qid=array[i].title;
			//alert(qid);
			
			qid_qid=qid.substr(qid.lastIndexOf("_")+1,qid.length);
			//å¾å°obj.pidæå¯¹åºçç¶id
			qid_oid=qid.substr(4,oid.indexOf("_")-4);
			//alert(v);
			//alert("qid_oid:"+qid_oid);
			//alert("oid_qid:"+oid_qid);
			if(qid_oid==oid_qid&&v==1){
				//alert(qid_oid);
				//æ¾å°äºæ­¤èç¹çç¶èç¹
				//array
				//alert(oid_qid);
				//oid_qid=id.replace(oid_oid,oid_qid);
				//alert(oid_qid);
				document.getElementById(treeType+oid_qid).checked="";
				//alert(document.getElementById("qlib"+oid_qid));
				//alert(document.getElementById("qlib"+oid_qid));
				selectEd_2(treeType,array[i].title,1);
			}
			
			if(qid_qid==oid_oid){//æ¾å°ææpidèç¹
				//if(v==1&&document.getElementById("qlib"+oid_oid).checked!=false){
					//alert(document.getElementById("qlib"+oid_oid).checked);
					if(!document.getElementById(treeType+oid_oid).checked==false){
						document.getElementById(treeType+oid_oid).checked="checked";
						//array[i].checked="";
					//alert(array[i].title);
					}
				//}
				//1.éä¸­ææå­èç¹
				//array[i].checked=document.getElementById("qlib"+oid_oid).checked;
				if(document.getElementById(treeType+oid_oid).checked==false){
				array[i].checked="";
				//alert(array[i].title);
				}else{
					array[i].checked="checked";
				}
				selectEd(treeType,array[i].title,0);//éå½å¾ªç¯å°ææå­èç¹
				
			}
		}
	}
}

function getRegExp(treeType){
	if("qlib"==treeType){
		ts=/^qlib[\d]{1,}/;
	}else if("ctyp"==treeType){
		ts=/^ctyp[\d]{1,}/;
	}else if("elib"==treeType){
		ts=/^elib[\d]{1,}/;
	}else if("clty"==treeType){
		ts=/^clty[\d]{1,}/;
	}else if("eroo"==treeType){
		ts=/^eroo[\d]{1,}/;
	}else if("depl"==treeType){
		ts=/^depl[\d]{1,}/;
	}else if("stuf"==treeType){
		ts=/^stuf[\d]{1,}/;
	}else if("news"==treeType){
		ts=/^news[\d]{1,}/;
	}else if("klty"==treeType){
		ts=/^klty[\d]{1,}/;
	}
	return ts;
}

function selectEd_2(treeType,id,v){
	//v++;
	//alert(v);
	//alert(id);
	var qid=id;
	var oid=id;
	var qid_oid;
	var qid_qid;
	var oid_oid=oid.substr(4,oid.indexOf("_")-4);//???
	var oid_qid=oid.substr(oid.lastIndexOf("_")+1,oid.length);
	var array=document.getElementsByTagName("input");
	//var ts= eval("/^"+treeType+"[\d]{1,}/");
	//var ts=/^ctyp[\d]{1,}/;
	//var ts=/^qlib[\d]{1,}/;
	//alert("2"+ts.test(id+""));
	var ts=getRegExp(treeType);
	for(var i=0;i<array.length;i++){
		if(array[i].type=="checkbox"&&ts.test(id+"")){
			//æ¾å°qlibéåäº
			qid=array[i].title;
			
			qid_qid=qid.substr(qid.lastIndexOf("_")+1,qid.length);
			//å¾å°obj.pidæå¯¹åºçç¶id
			qid_oid=qid.substr(4,oid.indexOf("_")-4);
			if(qid_oid==oid_qid&&v==1){
				//æ¾å°äºæ­¤èç¹çç¶èç¹
				document.getElementById(treeType+oid_qid).checked="";
				//alert(document.getElementById("qlib"+oid_qid));
				//alert(document.getElementById("qlib"+oid_qid).value);
				selectEd_2(treeType,array[i].title,1);
			}
			
		/*	if(qid_qid==oid_oid){//æ¾å°ææpidèç¹
				//1.éä¸­ææå­èç¹
				//array[i].checked=document.getElementById("qlib"+oid_oid).checked;
				alert(document.getElementById("qlib"+oid_oid).checked);
				//var tempChk=document.getElementById("qlib"+oid_oid);
				if(document.getElementById("qlib"+oid_oid).checked=true){
					document.getElementById("qlib"+oid_oid).checked=false;
				}
				selectEd_2(array[i].title,0);//éå½å¾ªç¯å°ææå­èç¹
				
				
			} */
		}
	}
}

// Adds the empty and line icons
dTree.prototype.indent = function (node, nodeId) {
	var str = "";
	if (this.root.id != node.pid) {
		for (var n = 0; n < this.aIndent.length; n++) {
			str += "<img src=\"" + ((this.aIndent[n] == 1 && this.config.useLines) ? this.icon.line : this.icon.empty) + "\" alt=\"\" />";
		}
		(node._ls) ? this.aIndent.push(0) : this.aIndent.push(1);
		if (node._hc) {
			str += "<a href=\"javascript: " + this.obj + ".o(" + nodeId + ");\"><img id=\"j" + this.obj + nodeId + "\" src=\"";
			if (!this.config.useLines) {
				str += (node._io) ? this.icon.nlMinus : this.icon.nlPlus;
			} else {
				str += ((node._io) ? ((node._ls && this.config.useLines) ? this.icon.minusBottom : this.icon.minus) : ((node._ls && this.config.useLines) ? this.icon.plusBottom : this.icon.plus));
			}
			str += "\" alt=\"\" /></a>";
		} else {
			str += "<img src=\"" + ((this.config.useLines) ? ((node._ls) ? this.icon.joinBottom : this.icon.join) : this.icon.empty) + "\" alt=\"\" />";
		}
	}
	return str;
};

dTree.prototype.indent1 = function (node, nodeId) {
	var str = "";
	if (this.root.id != node.pid) {
		for (var n = 0; n < node.level; n++) {
			str += "<img src=\"" + ( this.config.useLines  ? this.icon.line : this.icon.empty) + "\" alt=\"\" />";
		}
		if (node._hc) {
			str += "<a href=\"javascript: " + this.obj + ".o(" + nodeId + ");\"><img id=\"j" + this.obj + nodeId + "\" src=\"";
			if (!this.config.useLines) {
				str += (node._io) ? this.icon.nlMinus : this.icon.nlPlus;
			} else {
				str += ((node._io) ? ((node._ls && this.config.useLines) ? this.icon.minusBottom : this.icon.minus) : ((node._ls && this.config.useLines) ? this.icon.plusBottom : this.icon.plus));
			}
			str += "\" alt=\"\" /></a>";
		} else {
			str += "<img src=\"" + ((this.config.useLines) ? ((node._ls) ? this.icon.joinBottom : this.icon.join) : this.icon.empty) + "\" alt=\"\" />";
		}
	}
	return str;
};
// Checks if a node has any children and if it is the last sibling
dTree.prototype.setCS = function (node) {
	var lastId;
	for (var n = 0; n < this.aNodes.length; n++) {
		if (this.aNodes[n].pid == node.id) {
			node._hc = true;
		}
		if (this.aNodes[n].pid == node.pid) {
			lastId = this.aNodes[n].id;
		}
	}
	if (lastId == node.id) {
		node._ls = true;
	}
};



// Returns the selected node
dTree.prototype.getSelected = function () {
	var sn = this.getCookie("cs" + this.obj);
	return (sn) ? sn : null;
};



// Highlights the selected node
dTree.prototype.s = function (id) {
	if (!this.config.useSelection) {
		return;
	}
	var cn = this.aNodes[id];
	if (cn._hc && !this.config.folderLinks) {
		return;
	}
	if (this.selectedNode != id) {
		if (this.selectedNode || this.selectedNode == 0) {
			eOld = document.getElementById("s" + this.obj + this.selectedNode);
			eOld.className = "node";
		}
		eNew = document.getElementById("s" + this.obj + id);
		eNew.className = "nodeSel";
		this.selectedNode = id;
		if (this.config.useCookies) {
			this.setCookie("cs" + this.obj, cn.id);
		}
	}
};



// Toggle Open or close
dTree.prototype.o = function (id) {
	var cn = this.aNodes[id];
	this.nodeStatus(!cn._io, id, cn._ls);
	cn._io = !cn._io;
	if (this.config.closeSameLevel) {
		this.closeLevel(cn);
	}else{
		if(!this.aNodes[id].hasapp){
			var depid = this.aNodes[id].id;
			this.aNodes[id].hasapp = true;
			var jd = getChidrens(depid);
			if(jd.length>0)
			for(var i = 0 ; i <jd.length;i++){
				//alert(cn.url);
				//this.add2(jd[i].id,depid,jd[i].name,this.url+jd[i].id,'0','no',jd[i].bh);
				var node123=new Node(jd[i].id,depid,jd[i].name,this.iurl+jd[i].id,'0','no',jd[i].bh);
				if(i==jd.length-1)
					node123._ls=true;
				if(jd[i].ccnt<=0)
					node123._hc=false;
				node123.level = this.aNodes[id].level+1;
				this.aNodes[this.aNodes.length] = node123;
				$("#dd0"+id).append(this.node(node123 ,this.aNodes.length-1));
			}
			else
			{
				eDiv = document.getElementById("d" + this.obj + id);
				eJoin = document.getElementById("j" + this.obj + id);
				if (this.config.useIcons) {
					eIcon = document.getElementById("i" + this.obj + id);
					eIcon.src=this.icon.node;
				}
				//alert($(eJoin).parent().attr("tagName"));
				$(eJoin).parent().attr("href","javascript:void(0);");
				eJoin.src = ((cn._ls) ? this.icon.joinBottom : this.icon.join) ;
			}
		}
	}
	
	if (this.config.useCookies) {
		this.updateCookie();
	}
};
function getChidrens(id){
	var jd = [];
	$.ajax(
		{	async:false,  //   
			type:"post",   
		    url:"list_dep_childs.action",   
		    data:{"x":Math.random(),"department.id":id},   
			success:function(data){
				//alert(data);
				jd = eval("("+data+")");
		 }});
	return jd;
}


// Open or close all nodes
dTree.prototype.oAll = function (status) {
	for (var n = 0; n < this.aNodes.length; n++) {
		if (this.aNodes[n]._hc && this.aNodes[n].pid != this.root.id) {
			this.nodeStatus(status, n, this.aNodes[n]._ls);
			this.aNodes[n]._io = status;
		}
	}
	if (this.config.useCookies) {
		this.updateCookie();
	}
};



// Opens the tree to a specific node
dTree.prototype.openTo = function (nId, bSelect, bFirst) {
	if (!bFirst) {
		for (var n = 0; n < this.aNodes.length; n++) {
			if (this.aNodes[n].id == nId) {
				nId = n;
				break;
			}
		}
	}
	var cn = this.aNodes[nId];
	if (cn.pid == this.root.id || !cn._p) {
		return;
	}
	cn._io = true;
	cn._is = bSelect;
	if (this.completed && cn._hc) {
		this.nodeStatus(true, cn._ai, cn._ls);
	}
	if (this.completed && bSelect) {
		this.s(cn._ai);
	} else {
		if (bSelect) {
			this._sn = cn._ai;
		}
	}
	this.openTo(cn._p._ai, false, true);
};



// Closes all nodes on the same level as certain node
dTree.prototype.closeLevel = function (node) {
	for (var n = 0; n < this.aNodes.length; n++) {
		if (this.aNodes[n].pid == node.pid && this.aNodes[n].id != node.id && this.aNodes[n]._hc) {
			this.nodeStatus(false, n, this.aNodes[n]._ls);
			this.aNodes[n]._io = false;
			this.closeAllChildren(this.aNodes[n]);
		}
	}
};



// Closes all children of a node
dTree.prototype.closeAllChildren = function (node) {
	for (var n = 0; n < this.aNodes.length; n++) {
		if (this.aNodes[n].pid == node.id && this.aNodes[n]._hc) {
			if (this.aNodes[n]._io) {
				this.nodeStatus(false, n, this.aNodes[n]._ls);
			}
			this.aNodes[n]._io = false;
			this.closeAllChildren(this.aNodes[n]);
		}
	}
};



// Change the status of a node(open or closed)
dTree.prototype.nodeStatus = function (status, id, bottom) {
	eDiv = document.getElementById("d" + this.obj + id);
	eJoin = document.getElementById("j" + this.obj + id);
	if (this.config.useIcons) {
		eIcon = document.getElementById("i" + this.obj + id);
		eIcon.src = (status) ? this.aNodes[id].iconOpen : this.aNodes[id].icon;
	}
	eJoin.src = (this.config.useLines) ? ((status) ? ((bottom) ? this.icon.minusBottom : this.icon.minus) : ((bottom) ? this.icon.plusBottom : this.icon.plus)) : ((status) ? this.icon.nlMinus : this.icon.nlPlus);
	eDiv.style.display = (status) ? "block" : "none";
};





// [Cookie] Clears a cookie
dTree.prototype.clearCookie = function () {
	var now = new Date();
	var yesterday = new Date(now.getTime() - 1000 * 60 * 60 * 24);
	this.setCookie("co" + this.obj, "cookieValue", yesterday);
	this.setCookie("cs" + this.obj, "cookieValue", yesterday);
};



// [Cookie] Sets value in a cookie
dTree.prototype.setCookie = function (cookieName, cookieValue, expires, path, domain, secure) {
	document.cookie = escape(cookieName) + "=" + escape(cookieValue) + (expires ? "; expires=" + expires.toGMTString() : "") + (path ? "; path=" + path : "") + (domain ? "; domain=" + domain : "") + (secure ? "; secure" : "");
};



// [Cookie] Gets a value from a cookie
dTree.prototype.getCookie = function (cookieName) {
	var cookieValue = "";
	var posName = document.cookie.indexOf(escape(cookieName) + "=");
	if (posName != -1) {
		var posValue = posName + (escape(cookieName) + "=").length;
		var endPos = document.cookie.indexOf(";", posValue);
		if (endPos != -1) {
			cookieValue = unescape(document.cookie.substring(posValue, endPos));
		} else {
			cookieValue = unescape(document.cookie.substring(posValue));
		}
	}
	return (cookieValue);
};



// [Cookie] Returns ids of open nodes as a string
dTree.prototype.updateCookie = function () {
	var str = "";
	for (var n = 0; n < this.aNodes.length; n++) {
		if (this.aNodes[n]._io && this.aNodes[n].pid != this.root.id) {
			if (str) {
				str += ".";
			}
			str += this.aNodes[n].id;
		}
	}
	this.setCookie("co" + this.obj, str);
};



// [Cookie] Checks if a node id is in a cookie
dTree.prototype.isOpen = function (id) {
	var aOpen = this.getCookie("co" + this.obj).split(".");
	for (var n = 0; n < aOpen.length; n++) {
		if (aOpen[n] == id) {
			return true;
		}
	}
	return false;
};



// If Push and pop is not implemented by the browser
if (!Array.prototype.push) {
	Array.prototype.push = function array_push() {
		for (var i = 0; i < arguments.length; i++) {
			this[this.length] = arguments[i];
		}
		return this.length;
	};
}
if (!Array.prototype.pop) {
	Array.prototype.pop = function array_pop() {
		lastElement = this[this.length - 1];
		this.length = Math.max(this.length - 1, 0);
		return lastElement;
	};
}


var isK=0;
function onGrant(){
	//alert('hehe');
	if(isK==0){
		d7.openAll();
		document.getElementById("btnK").value="全部关闭";
		isK=1;
	}else{
		d7.closeAll();
		document.getElementById("btnK").value="全部展开";
		isK=0;
	}
}
