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





<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<META name=save content=history >
<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
<title>多级审核定义</title>
<LINK rel=stylesheet  href="css/style1.css">
<SCRIPT language=javascript src="js/bi_sub_table.js"></SCRIPT>
<!-- <LINK rel=stylesheet type="text/css" href="css/style.css"> -->
<SCRIPT language=javascript src="js/jquery.js"></SCRIPT>
<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
<script type="text/javascript">
$(document).ready(function(){
	$("#modulename").text(getModuleNameByTablename('${tablename}'));
})

function getModuleNameByTablename(tablename){
	var returnValue = "";
	$.ajax({
		  type: 'POST',
		  url: "getModuleNameByTablename.action",
		  data: {tablename:tablename},
		  async:false,//同步
		  success: function(data){
	  		data = eval("("+data+")").check_json_result;
	  		if(data != "")
	  			returnValue = data;
		  }
	});
	return returnValue;
}
</script>
<SCRIPT LANGUAGE='JavaScript'>
var flag = 1;
	
	function doLoad() {
		   var audits = '${audits}';
		   var u_name ;
		   var name_array ;
		   if(audits != null && audits != "" && audits != "[]"){
		   		var array = eval("("+audits+")") ;
		   		if(array.length>0){
		   			$.each(array,function(j,n){
			   			i=sub_add_new_row();
			   			$("#sub_level_"+i).val(n.auditOrder);
			   			$("#sub_title_"+i).val(n.auditName);
			   			$("#sub_userid_"+i).val(n.auditUser);
			   			$("#sub_shr_count_"+i).val(n.username);
			   			$("#sub_bz_"+i).val(n.mark);
			   			u_name = n.username.split(",");
			   			name_array = n.auditUser.split(",");
			   			for(var m=0;m<u_name.length;m++){
			   				add_shr(j,u_name[m],name_array[m]);
			   			}
			   		});
		   		}
		   }else {
		   		i=sub_add_new_row();
		   }
		   
           if('${elmessage}' != ""){
           	alert('${elmessage}');
           }
	}

    function select_shr(maxcount_i) {
	    var  mySubDialog = new Array(1); 
	    var url="addShenheren.action?rn="+Math.random();
		result=showModalDialog(url, mySubDialog,"resizable:Yes;status:no;dialogHeight:600px;dialogWidth:900px"); 
	    if (result != undefined && result!="") { 
	    	document.getElementById("sub_lrr_div_"+ maxcount_i).innerHTML = "";
	    	var array = result.split("_--_");
	    	var sub_arr = new Array();
	    	var sub_userid = "";
	    	var sub_value = "";
	    	for(var i = 0;i<array.length;i++){
	    		sub_arr = array[i];
	    		var vv = sub_arr.split("_-_")[1];
	    		var vv_ = sub_arr.split("_-_")[0];
	    		add_shr(maxcount_i,vv,vv_);
	    		if(i == array.length - 1)    {
	    			sub_userid += sub_arr.split("_-_")[0];
	    			sub_value += sub_arr.split("_-_")[1];
	    		}else{
	    			sub_userid += sub_arr.split("_-_")[0] + ",";
	    			sub_value += sub_arr.split("_-_")[1] + ",";
	    		}
	    	}
			document.getElementById("sub_shr_count_"+ maxcount_i).value = sub_value;
			document.getElementById("sub_userid_"+ maxcount_i).value = sub_userid;
			
			//add_shr(maxcount_i,sub_value);
		}

    }
    function check_auditOrder(auditOrder){
    	var tablename = "<s:property value='tablename'/>";
    	var returnValue = "";
    	$.ajax({
					  type: 'POST',
					  url: "check_auditOrderIsExist.action",
					  data: {tablename:tablename,auditOrder:auditOrder},
					  async:false,//同步
					  success: function(data){
				  		data = eval("("+data+")").check_json_result;
				  		if(data != "")
				  			returnValue = data;
					  }
				});
		if(   returnValue == true){
			alert("审核级别已经存在，请重新选择审核级别!!!");
			flag = 0;
			return ;
		} 	else{
			flag = 1;
		}
    }
    //新增审核人
    function add_shr(maxcount_i,shr_mc,shr_userid) {
    	var auditOrder = document.getElementById("sub_level_"+ maxcount_i).value;
    	var userid = parseInt(shr_userid);
    //function add_shr(maxcount_i,shr_id,shr_mc,shbm_id) {
        var div=document.getElementById("sub_lrr_div_"+ maxcount_i);
         //var count=document.getElementById("sub_shr_count_"+ maxcount_i);
         //count_number=count.value;

        var s=div.innerHTML;
        s+="<div  class='search_where_name'>";
        s+="【"+shr_mc+"】可审核";//<input type='hidden' name='sub_shrmc_"+maxcount_i+"_"+count_number+"' value='"+shr_mc+"'><input type='hidden' name='sub_shr_"+maxcount_i+"_"+count_number+"' value='"+shr_id+"'> ";
        //s+="<select id='sub_shbm_"+maxcount_i+"_"+count_number+"'  name='sub_shbm_"+maxcount_i+"_"+count_number+"'>      <option value=\"100\">┕XXX有限责任公司</option>      <option value=\"100001\">&nbsp;&nbsp;&nbsp;┕采购部 </option>      <option value=\"100002\">&nbsp;&nbsp;&nbsp;┕销售部 </option>      <option value=\"100003\">&nbsp;&nbsp;&nbsp;┕生产部 </option>      <option value=\"100004\">&nbsp;&nbsp;&nbsp;┕仓储部 </option>      <option value=\"100005\">&nbsp;&nbsp;&nbsp;┕财务部 </option>      <option value=\"100006\">&nbsp;&nbsp;&nbsp;┕行政部 </option></select>";
        //s+="&nbsp;&nbsp;<IMG style='CURSOR: hand' onclick='this.parentNode.removeNode(true)' src='img/colse-search.gif'></div>";
        div.innerHTML=s + '&nbsp;&nbsp;&nbsp;&nbsp;<img src="images/delete.png" width="11" height="11"  title="删除本行" onclick="deleteuserid('+shr_userid+','+auditOrder+','+userid+');" style="CURSOR: hand">';
        //document.getElementById("sub_shbm_"+maxcount_i+"_"+count_number).value=shbm_id;

        //count_number++;
        //count.value=count_number;
    }
    
    function deleteuserid(shr_userid,auditOrder,userid){
    	var value = "";
    	if(parseInt(auditOrder)<10){
    		value = "0" + auditOrder;
    	}else {
    		value = ""+auditOrder;
    	}
    	var tablename = "<s:property value='tablename'/>";
    	if (confirm("确认删除？") ){
    		$.ajax({
					  type: 'POST',
					  url: "deleteauditOrderUserId.action",
					  data: {tablename:tablename,auditOrder:value,userid:userid},
					  async:false,//同步
					  success: function(data){
				  		alert("删除成功!!!");
				  		window.location.href = "customAuditManageInit.action?tablename="+tablename;
					  }
				});
    	}
    }
    
    function sub_add_new_row() {
                    var maxcount_i =parseInt(document.getElementsByName('sub_maxcount')[0].value);
                    var row_html=new Array(5);
                    row_html[0]='<select onchange="check_auditOrder(this.value=this.options[this.selectedIndex].value);" id="sub_level_' + maxcount_i + '"  name="sub_level_' + maxcount_i + '"  regName ="序号"><option  value="01">01</option><option value="02">02</option><option value="03">03</option><option value="04">04</option><option value="05">05</option><option value="06">06</option><option value="07">07</option><option value="08">08</option><option value="09">09</option><option value="10">10</option><option value="11">11</option><option value="12">12</option><option value="13">13</option><option value="14">14</option><option value="15">15</option><option value="16">16</option><option value="17">17</option><option value="18">18</option><option value="19">19</option><option value="20">20</option><option value="21">21</option><option value="22">22</option><option value="23">23</option><option value="24">24</option><option value="25">25</option><option value="26">26</option><option value="27">27</option><option value="28">28</option><option value="29">29</option><option value="30">30</option></select>';
                    row_html[1]='<input  id="sub_title_' + maxcount_i + '"  name="sub_title_' + maxcount_i + '"  type="text" regName ="本级审核名称" size="10" value=""   class="x_bill_item_input_sub" >';
                    row_html[2]='<div align=right><input type="hidden" name="sub_userid_'+ maxcount_i+'" id="sub_userid_'+ maxcount_i+'" ><input readOnly type="hidden" name="sub_shr_count_'+ maxcount_i+'" id="sub_shr_count_'+ maxcount_i+'" ><input  id="sub_add_shr_' + maxcount_i + '" name="sub_add_shr_' + maxcount_i + '"  type="button" onclick="select_shr(' + maxcount_i + ');"  value="添加审核人" ></div><div id="sub_lrr_div_'+ maxcount_i+'"></div><div id="sub_lrr_div_'+ maxcount_i+'"></div>';
                    row_html[3]='<input id="sub_bz_' + maxcount_i + '"   name="sub_bz_' + maxcount_i + '"  type="text" regName ="备注" size="20" value="" defaultValue="" class="x_bill_item_input_sub" >';
                    row_html[4]='<img src="images/delete.png" width="11" height="11"  title="删除本行" onclick="this.parentNode.parentNode.click();sub_delete_row();" style="CURSOR: hand">';
                    maxCount = document.getElementsByName('sub_maxcount')[0];
                    currentTable = document.getElementsByName('sub')[0];
                    newRowHtml = row_html;
                    _addRow();
                    level=maxcount_i+1;
                    if (level>9)
                        document.getElementsByName('sub_level_' + maxcount_i )[0].value=level;
                    else
                        document.getElementsByName('sub_level_' + maxcount_i )[0].value="0"+level;
                        
                    //audit_orders[maxcount_i] = document.getElementsByName('sub_level_' + maxcount_i )[0].value;
                    return maxcount_i;
     }
    function sub_delete_row() {
    			maxCount = document.getElementsByName('sub_maxcount')[0];
                    currentTable = document.getElementsByName('sub')[0];
                    _deleteRow();
                    
                    document.getElementsByName('sub_maxcount')[0].value = document.getElementsByName('sub_maxcount')[0].value - 1;
    }
    
    function _click_button(form1) {
    		if(flag == 0 ){
    			alert("审核级别已经存在，请重新选择审核级别!!!");
    			return;
    		}
		    var sub_maxcount=document.getElementsByName('sub_maxcount')[0].value;
			var is_define_workflow=false;
			for(var i=0;i<sub_maxcount;i++){
				var sub_title_i=document.getElementsByName('sub_title_'+ i )[0].value;
				var sub_userid_i=document.getElementsByName('sub_userid_'+ i )[0].value;
				if(sub_userid_i==null||sub_userid_i==""){
					alert("第"+(i+1)+"行 审核人及权限不能为空，请选择相关的审核人！");
					return;
				}
				if(sub_title_i==null||sub_title_i==""){
					alert("第"+(i+1)+"行 审核名称不能为空，请输入相关的审核名称！");
					return;
				}
			}
			//如果启用工作流，但是没有定义流程则不允许保存。
			//var is_enabled=document.getElementsByName("is_enabled")[0].checked;
            if (confirm("确认？") ) {
                if (true || doCheck(form1)==true) {
                    document.getElementById('_button_area' ).style.display="none";
                    document.getElementById('_button_area_message' ).style.display="block";
                    form1.submit();
                }
			}
		}
    
     
     function set_filter1_value(row_count_i,value1,value2) {
           //显示
           var id="span_filter1_"+row_count_i;
           var x=document.getElementById(id);
           x.style.display=""; 
           //赋值
            document.getElementById("sub_is_user_filter1_"+row_count_i).checked=true;
            document.getElementById("sub_filter1_value1_"+row_count_i).value=value1;
            document.getElementById("sub_filter1_value2_"+row_count_i).value=value2;

     }
     function show_filter1(row_count_i) {
           var id="span_filter1_"+row_count_i;
           var x=document.getElementById(id);
           if (document.getElementById("sub_is_user_filter1_"+row_count_i).checked) 
                x.style.display=""; 
           else 
                x.style.display="none"; 
     }
     function show_filter2(row_count_i) {
           var id="span_filter2_"+row_count_i;
           var x=document.getElementById(id);
           if (document.getElementById("sub_is_user_filter2_"+row_count_i).checked) 
                x.style.display=""; 
           else 
                x.style.display="none"; 
     }
     function show_filter2(row_count_i) {
           var id="span_filter2_"+row_count_i;
           var x=document.getElementById(id);
           if (document.getElementById("sub_is_user_filter2_"+row_count_i).checked) 
                x.style.display=""; 
           else 
                x.style.display="none"; 
     }
   //-->
</SCRIPT>
</HEAD>

<body  onload=doLoad();>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:NavigationForZDY  />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>

                <table style="BACKGROUND-COLOR: white" border=0 width="100%" height="100%">
                <form action="customAuditManage.action" method="post" name=form1 >
                  <input type="hidden" name="tablename" value="<s:property value='tablename'/>">
                  <tbody>
	                  <tr>
	                    <td  height=32 colspan=3>
	                      <div align=left> 
	                          <input type='checkbox' name="is_enabled" checked value="1" onClick="_click_button(form1)">  启用多级审核
	                      </div>
	                    </td>
	                  </tr>
                  
	                  <tr>
	                    <td  height=32 colspan=3>
	                      <div class=table_head align=center><span style='color:red' id='modulename'></span>多级审核定义</div>
	                    </td>
	                  </tr>
                  
	                  <!-- <tr>
	                    <td  height=12 colspan=3>
	                      <div align=left>  
	                      	<input id="is_message"   name="is_message"   type="checkbox"  value="1">信息提醒    
	                        <input id="is_sms"   name="is_sms"     type="checkbox"  value="1">短信通知 
	                        <input id="is_email"   name="is_email"     type="checkbox"  value="1">邮件通知
	                      </div>
	                    </td>
	                  </tr> -->
                  
	                  <tr>
	                    <td  height=32 colspan=3>
	                      <div align=center>
	                      <table id=sub class=x_bill_sub_bill_table cellSpacing=0  cellPadding=0 width="100%">
	                      <tbody>
	                        <tr class=""><input value=0 type=hidden name=sub_maxcount> 
	                          <td  class=x_bill_sub_bill_table_header_td width="5%">审核顺序 </td>
	                          <td  class=x_bill_sub_bill_table_header_td width="15%">审核名称 </td>
	                          <td  class=x_bill_sub_bill_table_header_td width="30%">审核人及权限</td>
	                          <td  class=x_bill_sub_bill_table_header_td width="30%">备注</td>
	                          <td  class=x_bill_sub_bill_table_header_td_right width="3%"><img title=新增一行  onclick="sub_add_new_row();"  src="images/insert.png" width=12 height=12></td>
	                        </tr>
	                      </tbody>
	                      </table>
	                      </div>
	                    </td>
	                  </tr>
                  
	                  <!-- <tr>
	                    <td  height=32 colspan=3>
	                      <div  align=left>&nbsp;&nbsp;&nbsp;备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注： 
	                      <input id=bz class=main_table_item_add  size=150 name=bz value=""> </div>
	                      <div align=right></div>
	                    </td>
	                  </tr> -->
                  
	                  <tr>
	                    <td  colspan=3 align=middle>
	                      <div align="center" id="_button_area" style="display:block; padding-top:10px;vertical-align:bottom; clear:both;">
	<img src="images/bill_save.gif" onMouseOver="this.style.cursor='hand'"   onClick="_click_button(form1)"/> &nbsp;&nbsp;&nbsp;&nbsp;
	                        <img src="images/bill_cancel.gif" onMouseOver="this.style.cursor='hand'"   onclick='history.go(-1);'/> 
	                      </div>
	 <div align="center" id="_button_area_message" style="display:none;clear:both;">正在处理，请稍等...</div>
	                	</td>
	                  </tr>
                </tbody>
                
                </form>
                </table>

</body>
</html>
