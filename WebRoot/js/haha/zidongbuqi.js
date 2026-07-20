var websites;
  		if("<s:property value='tablename'/>" == "KHDA"){
	  		$.ajax({
				  type: 'POST',
				  url: "getZidongbuqiValue.action",
				  data: {tablename:"<s:property value='tablename'/>",columnName:"KHDA_GSMC"},
				  async:false,
				  success: function(data){
				  		data = eval("("+data+")").check_json_result;
				  		if(data != ""){
				  			zidongbuqi_array = data.split(",");
				  			websites = zidongbuqi_array;
				  		}
				  }
			});
	        $().ready(function() {   
	            $("#KHDA_GSMC").autocomplete(websites);      
	        });  
  		}else if("<s:property value='tablename'/>" == "KHDJB"){
  			$.ajax({
				  type: 'POST',
				  url: "getZidongbuqiValue.action",
				  data: {tablename:"<s:property value='tablename'/>",columnName:"KHDJB_KHMC"},
				  async:false,
				  success: function(data){
				  		data = eval("("+data+")").check_json_result;
				  		if(data != ""){
				  			zidongbuqi_array = data.split(",");
				  			websites = zidongbuqi_array;
				  		}
				  }
			});
	        $().ready(function() {   
	            $("#KHDJB_KHMC").autocomplete(websites);      
	        }); 
  		}else if("<s:property value='tablename'/>" == "LJR"){
  			$.ajax({
				  type: 'POST',
				  url: "getZidongbuqiValue.action",
				  data: {tablename:"<s:property value='tablename'/>",columnName:"LJR_XM"},
				  async:false,
				  success: function(data){
				  		data = eval("("+data+")").check_json_result;
				  		if(data != ""){
				  			zidongbuqi_array = data.split(",");
				  			websites = zidongbuqi_array;
				  		}
				  }
			});
	        $().ready(function() {   
	            $("#LJR_XM").autocomplete(websites);      
	        }); 
  		}