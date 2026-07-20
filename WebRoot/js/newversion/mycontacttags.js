//我添加的和我负责的按钮集中操作
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
	ToolsBarObj.ToolsBar_Add("toolbar_update","修改","images/newversion/un_view.gif","updateDetail()");
	ToolsBarObj.ToolsBar_Add("toolbar_delete","删除","images/newversion/un_view.gif","deleteDetail()");
	ToolsBarObj.ToolsBar_Add("toolbar_firstInstance","提交审核","images/newversion/un_view.gif","firstInstanceDetail()");
	ToolsBarObj.ToolsBar_Add("toolbar_applicationUpdate","申请修改","images/newversion/un_view.gif","applicationUpdateDetail()");
	ToolsBarObj.ToolsBar_Add("toolbar_applicationDelete","申请删除","images/newversion/un_view.gif","applicationDeleteDetail()");
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

function check_can_op(array){
	var flag = false;
	if(array!=null&&array.length>0){
		for(var i=0;i<array.length;i++){
			if(array[i] == undefined){
				flag = false;
				return flag;
			}
		}
		if(array.toString().indexOf("false")<0)	flag = true;
	}
	return flag;
}

function clickcheckbox(){
	var status = 0;
	pp = getCheckedCheckboxs(pp);
	var can_update = [];
	var can_delete = [];
	var can_applicationUpdate = [];
	var can_applicationDelete = [];
	var can_firstInstance = [];
	if(pp.length>1){
		//对于选择了多个checkbox
		//查看详情、修改、查看备注无法点击;
		//删除、提交审核、申请修改、申请删除等需要根据选择的数据id判断状态，只有在所选的数据所有id都满足条件的时候才可以点击
		for(var i=0;i<pp.length;i++){
			status = getStatusByIdAndTablename(tablename,pp[i]);
			if(status == 0){
				can_update[i] = true;
				can_delete[i] = true;
				can_firstInstance[i] = true;
			}else if(status == 7 || status == 10){
				can_update[i] = true;
				can_firstInstance[i] = true;
			}else if(status == 9){
				can_applicationUpdate[i] = true;
				can_applicationDelete[i] = true;
			}
		}
		ToolsBarObj.ToolsBar_Disabled("toolbar_view");
		ToolsBarObj.ToolsBar_Disabled("toolbar_update");
		ToolsBarObj.ToolsBar_Disabled("toolbar_beizhu");
		if(check_can_op(can_delete))
			ToolsBarObj.ToolsBar_Enabled("toolbar_delete");
		else
			ToolsBarObj.ToolsBar_Disabled("toolbar_delete");
		if(check_can_op(can_firstInstance))
			ToolsBarObj.ToolsBar_Enabled("toolbar_firstInstance");
		else
			ToolsBarObj.ToolsBar_Disabled("toolbar_firstInstance");
		if(check_can_op(can_applicationUpdate))
			ToolsBarObj.ToolsBar_Enabled("toolbar_applicationUpdate");
		else
			ToolsBarObj.ToolsBar_Disabled("toolbar_applicationUpdate");
		if(check_can_op(can_applicationDelete))
			ToolsBarObj.ToolsBar_Enabled("toolbar_applicationDelete");
		else
			ToolsBarObj.ToolsBar_Disabled("toolbar_applicationDelete");
		
	}else if(pp.length==1){
		status = getStatusByIdAndTablename(tablename,pp[0]);
		if(status == 0){
			ToolsBarObj.ToolsBar_Enabled("toolbar_update");
			ToolsBarObj.ToolsBar_Enabled("toolbar_delete");
			ToolsBarObj.ToolsBar_Enabled("toolbar_firstInstance");
		}else if(status == 7 || status == 10){
			ToolsBarObj.ToolsBar_Enabled("toolbar_update");
			ToolsBarObj.ToolsBar_Enabled("toolbar_firstInstance");
		}else if(status == 9){
			ToolsBarObj.ToolsBar_Enabled("toolbar_applicationUpdate");
			ToolsBarObj.ToolsBar_Enabled("toolbar_applicationDelete");
		}
		ToolsBarObj.ToolsBar_Enabled("toolbar_view");
		ToolsBarObj.ToolsBar_Enabled("toolbar_beizhu");
	}else{
		ToolsBarObj.ToolsBar_Disabled("toolbar_view");
		ToolsBarObj.ToolsBar_Disabled("toolbar_update");
		ToolsBarObj.ToolsBar_Disabled("toolbar_delete");
		ToolsBarObj.ToolsBar_Disabled("toolbar_firstInstance");
		ToolsBarObj.ToolsBar_Disabled("toolbar_beizhu");
		ToolsBarObj.ToolsBar_Disabled("toolbar_applicationUpdate");
		ToolsBarObj.ToolsBar_Disabled("toolbar_applicationDelete");
	}
}

///////////////////我添加的和我负责的列表
//申请修改
function applicationUpdateDetail(){
	pp = getCheckedCheckboxs(pp);
	if(pp.length==0){
		alert("您还没选择,请先选择!");
		return ;
	}
	apply_update(pp.toString());
}
//申请删除
function applicationDeleteDetail(){
	pp = getCheckedCheckboxs(pp);
	if(pp.length==0){
		alert("您还没选择,请先选择!");
		return ;
	}
	apply_del(pp.toString());
}
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
//修改
function updateDetail(){
	pp = getCheckedCheckboxs(pp);
	if(pp.length==0){
		alert("您还没选择,请先选择!");
		return ;
	}
	if(parseInt(ondemo) == 1 && updateJsp!=undefined && updateJsp!=null && updateJsp !=""){
		update_ZDY(pp[0],actionName);
	}else{
		update(pp[0],actionName);
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
//删除
function deleteDetail(){
	pp = getCheckedCheckboxs(pp);
	if(pp.length==0){
		alert("您还没选择,请先选择!");
		return ;
	}
	if(window.confirm("确定删除？")){
	   document.getElementById("contactids").value=pp.toString();
	   form_list_client.action="deleteContactTags.action";
	   form_list_client.submit();
	}
}
//个人提交审核
function firstInstanceDetail(){
	pp = getCheckedCheckboxs(pp);
	if(pp.length==0){
		alert("您还没选择,请先选择!");
		return ;
	}
	commitBySelf(pp.toString());
}
//////////////我添加的和我负责的列表结束
