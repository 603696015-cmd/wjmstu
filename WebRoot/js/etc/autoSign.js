var mm = 0;//表示只可以添加一次
function sign(obj){
	//获取当前td父节点tr
	var $tr = $(obj).parent().parent();
	//document.getElementById("userinfo").style.display = "block";
	/**
	<table width='100%' cellpadding='1' align='center' cellspacing='1' id="userinfo" style="display:block;">
			<caption>用户签名</caption>
			</table>
	*/
	var ele_start = "<tr><td colspan=4><table width='100%' cellpadding='1' align='center' cellspacing='1' id='userinfo' style='display:block;'>";
	var ele_end = "</table></td></tr>";
	var ele_body = "";
	$.ajax({
		  type: 'POST',
		  url: "getCurrentUserinfo.action",
		  async:false,//同步
		  success: function(data){
		  		if(mm == 0){
		  			data = eval("("+data+")").check_json_result;
			  		if(data != ""){
			  			ele_body = "<tr>"+
								"<td width='120' height='30' align='right' style='padding-right:10px;color:#0099CC'>用户姓名:"+
								"</td>"+
								"<td style='padding-left:10px;color:#0099CC'>"+
										data.name+
								"</td>"+
								"<td width='120' height='30' align='right' style='padding-right:10px;color:#0099CC'>部门:"+
								"</td>"+
								"<td style='padding-left:10px;color:#0099CC'>"+
										data.depname+
								"</td>"+
							"</tr>"+
							"<tr>"+
								"<td width='120' height='30' align='right' style='padding-right:10px;color:#0099CC'>职务:"+
								"</td>"+
								"<td style='padding-left:10px;color:#0099CC'>"+
										data.zhiwuname+
								"</td>"+
								"<td width='120' height='30' align='right' style='padding-right:10px;color:#0099CC'>地市:"+
								"</td>"+
								"<td style='padding-left:10px;color:#0099CC'>"+
										data.dishiname+
								"</td>"+
							"</tr>";
							document.getElementById($(obj).attr("id").substring(0,$(obj).attr("id").length-1)).value = data.userid;
							$(ele_start+ele_body+ele_end).appendTo($tr);
							mm = 1;
			  		  }
		  		}else if(mm == 1){
		  			alert("您已签名,不需再次签名!");
		  			return;
		  		}
		  }
	});
}