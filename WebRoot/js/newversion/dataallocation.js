/**
数据分配
*/

var ToolsBarObj = null;
var pp = [];

var ondemo = moduleManage_ondemo;
var addJsp = moduleZDY_addjsp;
var updateJsp = moduleZDY_updateJsp;
var viewJsp = moduleZDY_viewJsp;
var acName = actionName;
var tablename = tbname;

$(function(){
	ToolsBarObj = $("#Div_ToolsBar");//存放按钮的div
	ToolsBarObj.ToolsBar_Add("toolbar_fenpei","分配","images/newversion/un_view.gif","fenpeiDetail()");
	ToolsBarObj.ToolsBar_Add("toolbar_beizhu","查看备注","images/newversion/un_view.gif","beizhuDetail()");
});

//获取选中的checkbox
function getCheckedCheckboxs(pp){
	var checkboxs = document.getElementsByName("id_");
	if(checkboxs.length>0){
		if(pp.length>0)  pp=[];
		for(var i=0;i<checkboxs.length;i++){
			if(checkboxs[i].checked){
				pp.push(checkboxs[i].value);
			}
		}
	}
	return pp;
}

function clickcheckbox(){
	pp = getCheckedCheckboxs(pp);
	if(pp.length>1){
		ToolsBarObj.ToolsBar_Disabled("toolbar_fenpei");
	}else{
		ToolsBarObj.ToolsBar_Enabled("toolbar_fenpei");
	}
	ToolsBarObj.ToolsBar_Enabled("toolbar_beizhu");
}

///////////////////
//申请
function fenpeiDetail(){
	pp = getCheckedCheckboxs(pp);
	if(pp.length==0){
		alert("您还没选择,请先选择!");
		return ;
	}
	fenpei(pp[0]);
}

//备注
function beizhuDetail(){
	pp = getCheckedCheckboxs(pp);
	if(pp.length==0){
		alert("您还没选择,请先选择!");
		return ;
	}
	show_beizhu(pp[0]);
}


//////////////
