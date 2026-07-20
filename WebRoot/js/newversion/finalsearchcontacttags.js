//终审页面按钮集中操作
var ToolsBarObj = null;
var ToolBar_view=null;
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
	ToolsBarObj.ToolsBar_Add("toolbar_update","修改","images/newversion/un_view.gif","updateDetail_searchContactTags()");
	ToolsBarObj.ToolsBar_Add("toolbar_delete","删除","images/newversion/un_view.gif","deleteDetail_searchContactTags()");
	ToolsBarObj.ToolsBar_Add("toolbar_pass","通过","images/newversion/un_view.gif","passDetail_searchContactTags()");
	ToolsBarObj.ToolsBar_Add("toolbar_nopass","不通过","images/newversion/un_view.gif","nopassDetail_searchContactTags()");
	ToolsBarObj.ToolsBar_Add("toolbar_allowupdate","允许修改","images/newversion/un_view.gif","allowupdateDetail_searchContactTags()");
	ToolsBarObj.ToolsBar_Add("toolbar_noallowupdate","不允许修改","images/newversion/un_view.gif","noallowupdateDetail_searchContactTags()");
	ToolsBarObj.ToolsBar_Add("toolbar_allowdelete","允许删除","images/newversion/un_view.gif","allowdeleteDetail_searchContactTags()");
	ToolsBarObj.ToolsBar_Add("toolbar_noallowdelete","不允许删除","images/newversion/un_view.gif","noallowdeleteDetail_searchContactTags()");
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
	var can_pass = [];
	var can_nopass = [];
	var can_allowdelete = [];
	var can_noallowdelete = [];
	var can_allowupdate = [];
	var can_noallowupdate = [];
	if(pp.length>1){
		//对于选择了多个checkbox
		for(var i=0;i<pp.length;i++){
			status = getStatusByIdAndTablename(tablename,pp[0]);
			if(status == 0){
				can_update[i] = true;
				can_delete[i] = true;
				can_pass[i] = true;
			}else if(status == 2){
				can_update[i] = true;
				can_delete[i] = true;
				can_allowupdate[i] = true;
				can_noallowupdate[i] = true;
			}else if(status == 3){
				can_update[i] = true;
				can_delete[i] = true;
				can_allowdelete[i] = true;
				can_noallowdelete[i] = true;
			}else if(status == 5 || status == 6 || status == 7 || status == 8){
				can_update[i] = true;
				can_delete[i] = true;
				can_pass[i] = true;
				can_nopass[i] = true;
			}else if(status == 9){
				can_update[i] = true;
				can_delete[i] = true;
				can_nopass[i] = true;
			}else if(status == 10){
				can_update[i] = true;
				can_delete[i] = true;
				can_pass[i] = true;
			}
		}
		if(check_can_op(can_delete))
			ToolsBarObj.ToolsBar_Enabled("toolbar_delete");
		else
			ToolsBarObj.ToolsBar_Disabled("toolbar_delete");
		if(check_can_op(can_allowupdate))
			ToolsBarObj.ToolsBar_Enabled("toolbar_allowdelete");
		else
			ToolsBarObj.ToolsBar_Disabled("toolbar_allowdelete");
		if(check_can_op(can_noallowupdate))
			ToolsBarObj.ToolsBar_Enabled("toolbar_noallowdelete");
		else
			ToolsBarObj.ToolsBar_Disabled("toolbar_noallowdelete");
		if(check_can_op(can_allowupdate))
			ToolsBarObj.ToolsBar_Enabled("toolbar_allowupdate");
		else
			ToolsBarObj.ToolsBar_Disabled("toolbar_allowupdate");
		if(check_can_op(can_noallowupdate))
			ToolsBarObj.ToolsBar_Enabled("toolbar_noallowupdate");
		else
			ToolsBarObj.ToolsBar_Disabled("toolbar_noallowupdate");
			
		ToolsBarObj.ToolsBar_Disabled("toolbar_view");
		ToolsBarObj.ToolsBar_Disabled("toolbar_update");
		ToolsBarObj.ToolsBar_Disabled("toolbar_beizhu");
	}else if(pp.length == 1){
		status = getStatusByIdAndTablename(tablename,pp[0]);
		if(status == 0){
			ToolsBarObj.ToolsBar_Enabled("toolbar_update");
			ToolsBarObj.ToolsBar_Enabled("toolbar_delete");
			ToolsBarObj.ToolsBar_Enabled("toolbar_pass");
		}else if(status == 2){
			ToolsBarObj.ToolsBar_Enabled("toolbar_update");
			ToolsBarObj.ToolsBar_Enabled("toolbar_delete");
			ToolsBarObj.ToolsBar_Enabled("toolbar_allowupdate");
			ToolsBarObj.ToolsBar_Enabled("toolbar_noallowupdate");
		}else if(status == 3){
			ToolsBarObj.ToolsBar_Enabled("toolbar_update");
			ToolsBarObj.ToolsBar_Enabled("toolbar_delete");
			ToolsBarObj.ToolsBar_Enabled("toolbar_allowdelete");
			ToolsBarObj.ToolsBar_Enabled("toolbar_noallowdelete");
		}else if(status == 5 || status == 6 || status == 7 || status == 8){
			ToolsBarObj.ToolsBar_Enabled("toolbar_update");
			ToolsBarObj.ToolsBar_Enabled("toolbar_delete");
			ToolsBarObj.ToolsBar_Enabled("toolbar_pass");
			ToolsBarObj.ToolsBar_Enabled("toolbar_nopass");
		}else if(status == 9){
			ToolsBarObj.ToolsBar_Enabled("toolbar_update");
			ToolsBarObj.ToolsBar_Enabled("toolbar_delete");
			ToolsBarObj.ToolsBar_Enabled("toolbar_nopass");
		}else if(status == 10){
			ToolsBarObj.ToolsBar_Enabled("toolbar_update");
			ToolsBarObj.ToolsBar_Enabled("toolbar_delete");
			ToolsBarObj.ToolsBar_Enabled("toolbar_pass");
		}
		ToolsBarObj.ToolsBar_Enabled("toolbar_view");
		ToolsBarObj.ToolsBar_Enabled("toolbar_beizhu");
	}else{
		ToolsBarObj.ToolsBar_Disabled("toolbar_view");
		ToolsBarObj.ToolsBar_Disabled("toolbar_update");
		ToolsBarObj.ToolsBar_Disabled("toolbar_delete");
		ToolsBarObj.ToolsBar_Disabled("toolbar_pass");
		ToolsBarObj.ToolsBar_Disabled("toolbar_nopass");
		ToolsBarObj.ToolsBar_Disabled("toolbar_allowdelete");
		ToolsBarObj.ToolsBar_Disabled("toolbar_noallowdelete");
		ToolsBarObj.ToolsBar_Disabled("toolbar_beizhu");
		ToolsBarObj.ToolsBar_Disabled("toolbar_allowupdate");
		ToolsBarObj.ToolsBar_Disabled("toolbar_noallowupdate");
	}
}



///////////////初审列表页
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
function updateDetail_searchContactTags(){
	pp = getCheckedCheckboxs(pp);
	if(pp.length==0){
		alert("您还没选择,请先选择!");
		return ;
	}
	if(parseInt(ondemo) == 1 && updateJsp!=undefined && updateJsp!=null && updateJsp !=""){
		update_ZDY(pp[0],1,actionName);
	}else{
		update_(pp[0],1,actionName);
	}
}
//删除
function deleteDetail_searchContactTags(){
	pp = getCheckedCheckboxs(pp);
	if(pp.length==0){
		alert("您还没选择,请先选择!");
		return ;
	}
	if(window.confirm("确定删除？")){
	   document.getElementById("contactids").value=pp.toString();
		viewContact.action="deleteContactSearchTags.action";
		viewContact.submit();
	}
}
//通过
function passDetail_searchContactTags(){
	pp = getCheckedCheckboxs(pp);
	if(pp.length==0){
		alert("您还没选择,请先选择!");
		return ;
	}
	finalpass(pp.toString());
}
//不通过
function nopassDetail_searchContactTags(){
	pp = getCheckedCheckboxs(pp);
	if(pp.length==0){
		alert("您还没选择,请先选择!");
		return ;
	}
	finalnopass(pp.toString());
	
}
//允许修改
function allowupdateDetail_searchContactTags(){
	pp = getCheckedCheckboxs(pp);
	if(pp.length==0){
		alert("您还没选择,请先选择!");
		return ;
	}
	allow_update(pp.toString());
}
//不允许修改
function noallowupdateDetail_searchContactTags(){
	pp = getCheckedCheckboxs(pp);
	if(pp.length==0){
		alert("您还没选择,请先选择!");
		return ;
	}
	noallow_update(pp.toString());
}
//允许删除
function allowdeleteDetail_searchContactTags(){
	pp = getCheckedCheckboxs(pp);
	if(pp.length==0){
		alert("您还没选择,请先选择!");
		return ;
	}
	allow_del(pp.toString());
}
//不允许删除
function noallowdeleteDetail_searchContactTags(){
	pp = getCheckedCheckboxs(pp);
	if(pp.length==0){
		alert("您还没选择,请先选择!");
		return ;
	}
	noallow_del(pp.toString());
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
///////////////初审列表页结束