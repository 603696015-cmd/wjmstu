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
	ToolsBarObj.ToolsBar_Add("toolbar_view","查看详情","images/newversion/un_view.gif","viewDetail()");
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
//获取数据状态
function getStatusByIdAndTablename(tablename,id){
	var value = 0;
	$.ajax(
		{	async:false,  
			type:"post",   
		    url:"getStatusByIdAndTablename.action",   
		    data:{"x":Math.random(),"id":id,"tablename":tablename},   
			success:function(data){
				data = eval("("+data+")").check_json_result;
				if(data!=undefined && data!=""){
					value = parseInt(data);
				}
		    }
     });
	return value;
}

function clickcheckbox(){
	var status = 0;
	pp = getCheckedCheckboxs(pp);
	if(pp.length>1){
		//对于选择了多个checkbox
		ToolsBarObj.ToolsBar_Disabled("toolbar_view");
		ToolsBarObj.ToolsBar_Disabled("toolbar_beizhu");
	}else if(pp.length==1){
		ToolsBarObj.ToolsBar_Enabled("toolbar_view");
		ToolsBarObj.ToolsBar_Enabled("toolbar_beizhu");
	}else{
		ToolsBarObj.ToolsBar_Disabled("toolbar_view");
		ToolsBarObj.ToolsBar_Disabled("toolbar_beizhu");
	}
}

///////////////////公共查询列表

//查看
function viewDetail(){
	pp = getCheckedCheckboxs(pp);
	if(pp.length==0){
		alert("您还没选择,请先选择!");
		return ;
	}
	if(parseInt(ondemo) == 1 && addJsp!=undefined && addJsp!=null && addJsp !=""){
		view_ZDY(pp[0]);
	}else{
		view(pp[0]);
	}
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

//////////////公共查询列表结束
