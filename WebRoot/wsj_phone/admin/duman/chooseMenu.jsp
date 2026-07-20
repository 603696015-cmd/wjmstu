<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<TITLE>中国食品安全培训网--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system3.css" />
		<link rel="stylesheet" type="text/css" href="css/manage3.css" />
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<style type="text/css">
			table td{
				vertical-align:top;
			}
		</style>
		<SCRIPT type="text/javascript">
			function init(funcCount){
				for(var i=0;i<funcCount;i++){
					var nihao="s"+i;
					eval(nihao).closeAll();
				}
				//alert(funcCount);
			}
			
			
		</SCRIPT>
		
		<script type="text/javascript">
			function doSubmit(treeType){
				//1.获取所有被选中的节点
				//var arrayBh=document.getElementsByName("depl");
				//alert(arrayBh.length);
				//var bh="";
				//for(var i=0; i<arrayBh.length;i++){  
				//	if(arrayBh[i].checked==true){
				//		//alert(arrayBh[i].alt);
				//		bh=arrayBh[i].alt;
				//		break;
				//	}
				//}
				//document.myForm.submit();
				var did =$("input[name='depl']:checked").val(); 
				alert(did);
				var bh ="";
				if(did)
					$.ajax({	async:false,  //   
							type:"post",   
						    url:"elf_view.action",   
						    data:{"x":Math.random(),"func.id":did,"optype":"ajax"},   
							success:function(data){
								jd = eval("("+data+")");
								bh=jd.name+"-=wys=-"+jd.id;
						 }});
						 alert(bh);
				window.returnValue = bh;
				window.close();
				//setTimeout(window.close(),2000);
			}
		</script>
		<script type="text/javascript">
		function Node(id,name,parent,title,lid,rid){
	this.id = id;
	this.name = name;
	//if(name.length<20){
	//	this.name = name
	//}else{
		//this.name = name.substring(0,20)+"...";
	//}
	this.parent = parent;
	this.childs = [];
	this.haschilds = false;
	this.lastchild = true;
	this.opend = false;
	//this.url = url;
	this.title = name;
	this.div =$("<div>");
	this.headerdiv =$("<div>");
	this.childsdiv =$("<div>");
	this.lid = lid;
	this.rid = rid;
	this.treeType="stl";
	this._ls = false;
	this._hc = false;
}
function WTree(id,rootable,itype,iname,url){
	this.icon = {root:"js/tree/img/base.gif", folder:"js/tree/img/folder.gif", folderOpen:"js/tree/img/folderopen.gif", node:"js/tree/img/page.gif", empty:"js/tree/img/empty.gif", line:"js/tree/img/line.gif", join:"js/tree/img/join.gif", joinBottom:"js/tree/img/joinbottom.gif", plus:"js/tree/img/plus.gif", plusBottom:"js/tree/img/plusbottom.gif", minus:"js/tree/img/minus.gif", minusBottom:"js/tree/img/minusbottom.gif", nlPlus:"js/tree/img/nolines_plus.gif", nlMinus:"js/tree/img/nolines_minus.gif"};
	this.nodes=[];
	this.tree =null;
	this.id = id;
	this.rootable = rootable;
	this.itype=itype;
	this.iname = iname;
	this.root = new Node(-1);
	this.values = [];
	this.url = url;
	this.raval=0;
}
WTree.prototype.addNode=function(id,name,pid,haschilds,title,lid,rid){
	if(pid == 0){
		this.tree = new Node(id,name,null,title,lid,rid);
		this.nodes[this.nodes.length] = this.tree;
	}else{
		var p = null;
		for(var i = 0 ; i < this.nodes.length;i++){
			if(pid == this.nodes[i].id){
				p = this.nodes[i];
				break;
			}
		}
		var n = new Node(id,name,p,title,lid,rid);
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
		str+="<img src=\""+this.icon.root+"\" id='tree_ind_"+n.id+"_"+this.id+"'/>";
	else{
		str = this.setInd(n.parent);
		if(n.haschilds)
			str+="<a href='javascript:"+this.id+".open( "+n.id+")'><img id='tree_ind_"+n.id+"_"+this.id+"' src=\""+(n.lastchild?this.icon.plusBottom:this.icon.plus)+"\"/></a>"+"<img id='tree_folder_"+n.id+"_"+this.id+"' src=\""+(this.icon.folder)+"\"/>";
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
	var div = n.div
	if(n.parent==null)
		$(div).attr("class","wtree");
	var h = n.headerdiv;
	$(h).attr("id","tree_header_"+n.id);
	//è®¾ç½®å·¦ä¾§çç©æ
	$(h).append(this.setLeft(n));
	var input="";
	if(this.itype=="ra")
		input="<input type='radio' onclick='"+this.id+".setCheck(this);' name='"+this.iname+"' value='"+n.id+"'/>&nbsp;&nbsp;";
	if(this.itype=="cb")
		//input="<input type='checkbox' onclick='"+this.id+".setCheck(this);' name='"+this.iname+"' value='"+n.id+"'/>&nbsp;&nbsp;";
		input="<input type='checkbox' id=\""+n.treeType+n.id+"\" name=\""+this.iname+"\" title=\""+n.treeType+n.id+"_"+(n.parent==null?"":n.parent.id)+"\" onclick='"+this.id+".setCheck(this);' value='"+n.id+"'/>&nbsp;&nbsp;";
	var href_url= "<a title='"+n.title+"' href=\""+((this.url==null||this.url=="")?"javascript:"+this.id+".open( "+n.id+");":(this.url+n.id))+"\">"+ n.name+ "</a>";
	/*if(n.parent==null){
		if(this.rootable&&n.id>0){
			$(h).append(input+"<a title='"+n.title+"' href=\""+((this.url==null||this.url=="")?"javascript:void(0);":(this.url+n.id))+"\">"+ n.name+ "</a>");
		}else
			$(h).append(n.name);
	}else{
		$(h).append(input+"<a title='"+n.title+"' href=\""+((this.url==null||this.url=="")?"javascript:void(0);":(this.url+n.id))+"\">"+ n.name+ "</a>");
	}*/
	$(h).append((n.parent==null&&(!this.rootable||n.id<=0))?href_url:(input+href_url));
	var csd=n.childsdiv;
	$(csd).attr("id","tree_child_"+n.id+"_"+this.id);
	$(csd).attr("class","child");
	this.fabricatecs(n);
	$(div).append(h);
	$(div).append(csd);
}
WTree.prototype.fabricatecs=function(n){
	var cs = n.childs;
	for(var i =0; i <cs.length;i++){
		var nci = cs[i];
		this.fabricate(nci);
		$(n.childsdiv).append(nci.div);
	}
}
WTree.prototype.open=function(id){
	for(var i = 0 ; i < this.nodes.length;i++){
		var n =this.nodes[i] ;
		if(id == n.id){
			if(n.opend){
				$("#tree_child_"+id+"_"+this.id).css("display","none");
				n.opend = false;
			}else{
				$("#tree_child_"+id+"_"+this.id).css("display","block");
				n.opend = true;
				if($(n.childsdiv).html()==""){
					var jd = getChidrens_st(n.id);
					if(jd.length>0)
					for(var k = 0 ; k<jd.length;k++){
						this.addNode(jd[k].id,jd[k].name,id,jd[k].ccnt>0?true:false,jd[k].bh,jd[k].lid,jd[k].rid);
					}
					this.fabricatecs(n);
					$("#tree_child_"+id+"_"+this.id).append($(n.childsdiv).html());
				}
			}
			if(n.parent!=null&&n.haschilds){
				$("#tree_ind_"+id+"_"+this.id).attr("src",!n.opend?(n.lastchild?this.icon.plusBottom:this.icon.plus):(n.lastchild?this.icon.minusBottom:this.icon.minus));
				$("#tree_folder_"+id+"_"+this.id).attr("src",!n.opend?this.icon.folder:this.icon.folderOpen);
			}
			break;
		}
	}
}

function getChidrens_st(id){
	var jd = [];
	$.ajax(
		{	async:false,  //   
			type:"post",   
		    url:"list_sta_childs.action",   
		    data:{"x":Math.random(),"station.id":id},   
			success:function(data){
				//alert(data);
				jd = eval("("+data+")");
		 }});
	return jd;
}

WTree.prototype.openp=function(n){
	if(n){
	 	if(!n.opend)
	 		this.open(n.id);
	 	if(n.parent!=null)
	 		this.openp(n.parent);
 	}
}
WTree.prototype.doShow=function(){
	this.fabricate(this.tree)
	var divtree = $("<div>");
	$(divtree).append(this.tree.div);
	document.write($(divtree).html());
	this.open(this.tree.id);
};
WTree.prototype.setVal=function(n){
	$("#tree_header_"+n.id).css("font-weight","bolder");
	var inps = $("#tree_header_"+n.id).find("input");
	if(inps.length>0)
		$(inps[0]).attr("checked","checked");
	if(this.itype=="ra") 
		this.raval=n.id;
}
WTree.prototype.setCheck=function(obj){
	if(this.itype=="ra"){
		$("#tree_header_"+this.raval).css("font-weight","normal");
		this.raval=$(obj).val();
	}
	var c = $(obj).attr("checked");
	if(c)
		$(obj).parent().css("font-weight","bolder");
	else
		$(obj).parent().css("font-weight","normal");
}
WTree.prototype.setValues=function(dv){
	if(dv.length>0)
		for(var j=0;j<dv.length;j++){
			var dep = dv[j];
			this.setVals(this.tree,dv[j]);
		}
}
WTree.prototype.setVals=function(n,dep){
	if(dep.id==n.id)
		this.setVal(n);
	if(n.id==-2||(dep.lid>n.lid&&dep.rid<n.rid)){
		if(!n.opend){
			this.open(n.id);
		}
		for(var i =0;i<n.childs.length;i++){
			this.setVals(n.childs[i],dep);
		}
	}
}
function DEP(id,lid,rid){
	this.id = id;
	this.lid = lid;
	this.rid = rid;
}



// Open/close all nodes
WTree.prototype.openAll = function () {
	this.oAll(true);
};
WTree.prototype.closeAll = function () {
	this.oAll(false);
};



WTree.prototype.oAll = function (status) {
	for (var n = 0; n < this.nodes.length; n++) {
		if (this.nodes[n]._hc && this.nodes[n].pid != this.root.id) {
			this.nodeStatus(status, n, this.nodes[n]._ls);
			this.nodes[n]._io = status;
		}
	}
};
		</script>
		<script type="text/javascript">
		</script>
	</HEAD>
	<body onLoad="init(17)">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<li>
				<span style="font-weight: bold;">角色功能分配</span>

			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<form action="rolefunc_add.action" method="post">
		<div style="font-size: 13px; margin-top: 4px;">
			<a href="javascript:opTree('<s:property value="funcTree.count"/>');" id="treeOp" class="textbg6">全部展开</a>
			<table align="center" cellpadding="1" cellspacing="1" width="100%">
				<tr>
					<th>
						角色名
					</th>
					<th>
						描敘
					</th>
				</tr>
				<tr>
					<td height="20" align="center">
						<s:property value="role.name" />
					</td>
					<td height="20" align="center">
						<s:property value="role.description" />
					</td>
				</tr>
			</table>
			<table style="width:100%;border:0px solid #fff;" cellpadding="1" cellspacing="1">
			  <tr>
				<td>
					<div><wysLib:funcTree_4 did="0" nodeIndex="0" iname="depl" itype="ra" ></wysLib:funcTree_4></div>
				</td>
				<td>
					<div><wysLib:funcTree_4 did="1" nodeIndex="1" iname="depl" itype="ra" ></wysLib:funcTree_4></div>
				</td>
				<td>
					<div><wysLib:funcTree_4 did="2" nodeIndex="2" iname="depl" itype="ra" ></wysLib:funcTree_4></div>
				</td>
				</tr>
				  <tr>
				<td>
					<div><wysLib:funcTree_4 did="3" nodeIndex="3" iname="depl" itype="ra" ></wysLib:funcTree_4></div>
				</td>
				<td>
					<div><wysLib:funcTree_4 did="4" nodeIndex="4" iname="depl" itype="ra" ></wysLib:funcTree_4></div>
				</td>
				<td>
					<div><wysLib:funcTree_4 did="5" nodeIndex="5" iname="depl" itype="ra" ></wysLib:funcTree_4></div>
				</td>
				</tr>
				  <tr>
				<td>
					<div><wysLib:funcTree_4 did="6" nodeIndex="6" iname="depl" itype="ra" ></wysLib:funcTree_4></div>
				</td>
				<td>
					<div><wysLib:funcTree_4 did="7" nodeIndex="7" iname="depl" itype="ra" ></wysLib:funcTree_4></div>
				</td>
				<td>
					<div><wysLib:funcTree_4 did="8" nodeIndex="8" iname="depl" itype="ra" ></wysLib:funcTree_4></div>
				</td>
				</tr>
				  <tr>
				<td>
					<div><wysLib:funcTree_4 did="9" nodeIndex="9" iname="depl" itype="ra" ></wysLib:funcTree_4></div>
				</td>
				<td>
					<div><wysLib:funcTree_4 did="10" nodeIndex="10" iname="depl" itype="ra" ></wysLib:funcTree_4></div>
				</td>
				<td>
					<div><wysLib:funcTree_4 did="11" nodeIndex="11" iname="depl" itype="ra" ></wysLib:funcTree_4></div>
				</td>
				</tr>
				  <tr>
				<td>
					<div><wysLib:funcTree_4 did="12" nodeIndex="12" iname="depl" itype="ra" ></wysLib:funcTree_4></div>
				</td>
				<td>
					<div><wysLib:funcTree_4 did="13" nodeIndex="13" iname="depl" itype="ra" ></wysLib:funcTree_4></div>
				</td>
				<td>
					<div><wysLib:funcTree_4 did="14" nodeIndex="14" iname="depl" itype="ra" ></wysLib:funcTree_4></div>
				</td>
				</tr>
				  <tr>
				<td>
					<div><wysLib:funcTree_4 did="15" nodeIndex="15" iname="depl" itype="ra" ></wysLib:funcTree_4></div>
				</td>
				<td>
					<div><wysLib:funcTree_4 did="16" nodeIndex="16" iname="depl" itype="ra" ></wysLib:funcTree_4></div>
				</td>
				</tr>
			</table>
			<s:hidden name="role.id"></s:hidden>
		</div>
		<div style="margin-top:10px;text-align:center;">
			<input type="button" value="提交" class="textbg4" onclick="doSubmit('depl');"/>
			<input type="button" onclick="document.location='role_list.action'" value="返回" class="textbg4"/>
		</div>
		</form>
	
	</body>
</HTML>
