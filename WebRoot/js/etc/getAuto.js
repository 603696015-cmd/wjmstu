var id = "";//自动读取数据id
		var ex_columnname = "";
		var type = 0;
		function autoGet(){
			var tablename = t;//合同
			type = 1;
			var returnValue = "";
			var columnName = "";
			var columnValue = "";
			var columnType = "";
			var array = new Array();
			$.ajax({
				  type: 'POST',
				  url: "getFromtablenameByTablename.action",
				  data: {tablename:tablename},
				  async:false,//同步
				  success: function(data){
			  		data = eval("("+data+")").check_json_result;
			  		if(data == ""){
			  			alert("设置字段自动读取还未设置数据来自的模块，请重新设置!!!");
			  			return ;
			  		}else {
			  			width=screen.availWidth * 0.8;
					 	height=screen.availHeight * 0.8;	
					 	var url = "getListByTablename.action?tablename="+data+"&rn="+Math.random();
					  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";	
					  	var rv = window.showModalDialog(url,null,sFeature);	
					  	if(rv!=undefined&&rv!=""){
					  		id = rv.split("===")[0];
					  		returnValue = getById(parseInt(id),data);
					  		array = returnValue.split("_-_");
					  	}
			  		}
				  }
			});
			//获取哪些列是要自动读取的
			var need_auto = getAutoColumns(tablename);
			for(var i=0;i<array.length;i++){
  				ex_columnname = array[i].split("==")[0];
  				columnName = getColumnNameByFromtablenameAndColumnName(tablename,array[i].split("==")[0]);
  				if(columnName != "" && need_auto.indexOf(columnName)!=-1){
  					columnValue = array[i].split("==")[1];
  					columnType = array[i].split("==")[2];
		  			//不同类型的数据赋值也不同
	  				fuzhi(tablename,columnName,columnType,columnValue,ex_columnname,type,id);
  				}
	  		}
		}
		
		
		
		function getAutoColumns(tablename){
			var returnValue = "";
			$.ajax({
				  type: 'POST',
				  url: "getAutoColumns.action",
				  data: {tablename:tablename},
				  async:false,//同步
				  success: function(data){
			  		data = eval("("+data+")").check_json_result;
			  		returnValue = data;
				  }
			});
			return returnValue;
		}
		
		
		function getColumnNameByFromtablenameAndColumnName(tablename,columnName){
			var returnValue = "";
			$.ajax({
				  type: 'POST',
				  url: "getColumnNameByFromtablenameAndColumnName.action",
				  data: {tablename:tablename,columnName:columnName},
				  async:false,//同步
				  success: function(data){
			  		data = eval("("+data+")").check_json_result;
			  		returnValue = data;
				  }
			});
			return returnValue;
		}
		
		
		
		
		function fuzhi(tablename,columnName,columnType,columnValue,ex_columnname,type,id){
			//columnName列名、columnType列类型、columnValue列值
			if(columnType == "文本" || columnType == "整数" || columnType == "实数" || columnType == "大文本"){
				if(columnValue == "null"){
					columnValue = "";
				}
				document.getElementById(columnName).value = columnValue;
			}else if(columnType == "城市"){
				var array;
				if(columnValue != ""){
					array = columnValue.split(" ");
				}
				document.getElementById(columnName).value = columnValue;
				
				$("#"+columnName+"_option_in_province").text(array[0]) ;
				$("#"+columnName+"_option_in_province").attr("value",array[0]);
				
				
				$("#"+columnName+"_option_in_city").text(array[1]) ;
				$("#"+columnName+"_option_in_city").attr("value",array[1]);
				
				$("#"+columnName+"_option_in_county").text(array[2]) ;
				$("#"+columnName+"_option_in_county").attr("value",array[2]);
			}else if(columnType == "单选"){
				var radios = document.getElementsByName(columnName);
				for(var i=0;i<radios.length;i++){
					if(radios[i].value == columnValue){
						radios[i].checked = "true";
					}
				}
			} else if(columnType == "复选" ){
				var checkboxs = document.getElementsByName(columnName);
				var arr ;
				if(columnValue!=""){
					arr = columnValue.split(",");
					if(arr!=undefined && arr!=null){
						for(var i=0;i<checkboxs.length;i++){
							for(var j=0;j<arr.length;j++){
								if(checkboxs[i].value == arr[j]){
									checkboxs[i].checked = "true";
								}
							}
						}
					}
				}
			}else if(columnType == "下拉选项"){
				var select = document.getElementsByName(columnName)[0];
				for(var i=0;i<select.length;i++){
					if(select[i].value == columnValue){
						select.value = columnValue;
					}
				}
			}else if(columnType == "分级下拉选项"){
				//13__二级节点2___31__三级节点3___33__四级节点1
				if(columnValue == "null"){
					columnValue = "";
				}
				var ary;
				if(columnValue!=""){
					ary = columnValue.split("___");
					if(ary!=null&&ary.length>0){
						var parentid = 0;
						for(var number=0;number<ary.length;number++){
							var levelid= parseInt(ary[number].split("__")[0]);
							parentid=parseInt(getParentid(levelid));
							$.ajax({
							  type: 'POST',
							  url: "getselectLevelListByParentId.action",
							  data: {'selectLevel.id':parentid},
							  async:false,
							  success: function(data){
							  		data = eval("("+data+")").check_json_result;
					  				$("#"+columnName+"_"+parseInt(number+1)).empty();
								  	var selecthtml_body = "<option value='0'>请选择</option>";
					  				for(var i=0;i<data.length;i++){
					  					if(levelid == parseInt(data[i].id)){
					  						selecthtml_body += "<option value='"+data[i].id+"' selected='selected'>"+data[i].name+"</option>";
					  					}else{
					  						selecthtml_body += "<option value='"+data[i].id+"'>"+data[i].name+"</option>";
					  					}
					  				}
							  		$(selecthtml_body).appendTo($("#"+columnName+"_"+parseInt(number+1)));
							  }
							});
						}
					}
				}
			}else if(columnType == "相关字段"){
				//relate_id_
				//判断是否参与表间计算
				var produceTable = getProduceTableByTable(tablename);
				if(produceTable != undefined && produceTable != ""){
					var relate_id_ = "relate_" + getIdByColumnName(tablename,columnName) + "_";
					var relate_id = "relate_" + getIdByColumnName(tablename,columnName);
					if(columnValue == "null" ){
					}else {
						document.getElementById(relate_id_).value = columnValue;
						//relate_2026  ==>  65==TB_MM_204_891
						//ex_columnname==CGJH1_XGWP
						//id为单据id
						id = id.split("==")[0];
						var relateid = getRelateId(ex_columnname,id);
						var xx = "";
						if(relateid != ""){
							var array = relateid.split(",");
							iii = array.length;
							for(var i=0;i<array.length;i++){
								var result_table_column = getResult_table_column(tablename,columnName);
								if(i == array.length - 1){
									xx += array[i] + "==" + result_table_column ;
									document.getElementById(relate_id).value = xx;
								}else {
									xx += array[i] + "==" + result_table_column + "__-__" ;
								}
							}
						}
						//判断是否是相关字段且过程表
						if(checkColumnRelateIsResultTable(tablename,columnName)){
							addRelate(document.getElementById(relate_id).value,type,id);
						}
						
						//添加到tb_tags_relate
						document.getElementById("relateIds").value = relateid;
						document.getElementById("ex_columnname").value = columnName;
						
					}
				}else {
					var relate_id_ = "relate_" + getIdByColumnName(tablename,columnName) + "_";
					if(columnValue == "null" ){
					}else {
						document.getElementById(relate_id_).value = columnValue;
					}
				}
			}
		}
		
		function getParentid(levelid){
			var returnValue = 0;
			$.ajax({
				  type: 'POST',
				  url: "getParentid.action",
				  data: {'selectLevel.id':levelid},
				  async:false,//同步
				  success: function(data){
			  		data = eval("("+data+")").check_json_result;
			  		returnValue = data;
				  }
			});
			return returnValue;
		}
		
		
		
		function getById(id,tablename){
			var returnValue = "";
			$.ajax({
				  type: 'POST',
				  url: "getById.action",
				  data: {tablename:tablename,id:id},
				  async:false,//同步
				  success: function(data){
			  		data = eval("("+data+")").check_json_result;
			  		returnValue = data;
				  }
			});
			return returnValue;
		}
		
		
		
		function getProduceTableByTable(tablename) {
			var returnValue = "";
			$.ajax({
				  type: 'POST',
				  url: "getProduceTableByTable.action",
				  data: {tablename:tablename},
				  async:false,//同步
				  success: function(data){
			  		data = eval("("+data+")").check_json_result;
			  		returnValue = data;
				  }
			});
			return returnValue;
		}
		
		
		function getIdByColumnName(tablename,columnName){
			var returnValue = "";
			$.ajax({
				  type: 'POST',
				  url: "getIdByColumnName.action",
				  data: {tablename:tablename,columnName:columnName},
				  async:false,//同步
				  success: function(data){
			  		data = eval("("+data+")").check_json_result;
			  		returnValue = data;
				  }
			});
			return returnValue;
		}
		
		
		
		function getRelateId(columnName,id) {
			var returnValue = "";
			$.ajax({
				  type: 'POST',
				  url: "getRelateId.action",
				  data: {columnName:columnName,id:id},
				  async:false,//同步
				  success: function(data){
			  		data = eval("("+data+")").check_json_result;
			  		returnValue = data;
				  }
			});
			return returnValue;
		}
		
		
		function getResult_table_column(tablename,columnName) {
			var returnValue = "";
			$.ajax({
				  type: 'POST',
				  url: "getResult_table_column.action",
				  data: {columnName:columnName,tablename:tablename},
				  async:false,//同步
				  success: function(data){
			  		data = eval("("+data+")").check_json_result;
			  		returnValue = data;
				  }
			});
			return returnValue;
		}
		
		
		//判断是相关字段且关联模块式结果表
		function checkColumnRelateIsResultTable(tablename,columnName){
			var returnValue = "";
			$.ajax({
				  type: 'POST',
				  url: "checkColumnRelateIsResultTable.action",
				  data: {columnName:columnName,tablename:tablename},
				  async:false,//同步
				  success: function(data){
			  		data = eval("("+data+")").check_json_result;
			  		returnValue = data;
				  }
			});
			return returnValue;
		}