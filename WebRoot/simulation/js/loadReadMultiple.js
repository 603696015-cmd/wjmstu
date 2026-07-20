;(function($){
	var examId = $("#examId");
	var obj = { "A":0, "B":1, "C":2, "D":3, "E":4,"F":5,"H":6,"I":7 };
	var charter = {0:"A",1:"B",2:"C",3:"D",4:"E",5:"F",6:"H",7:"I"};
	
	function _createOptions(arr,item,type){
		var xml = "";
		var i =0;
		$.each(arr,function(i,str){
			if(type == 1){
				xml+="<td class='tdColumns6'><input type='radio' data-qtype='"+item.quesType+"' data-id='"+item.id+"' data-type='"+item.type+"' name='"+item.id+"' value='"+str+"' style='display: none;'><label class='hRadio'></label>"+str+".</td>";
			}else if(type == 2){
				xml+="<td class='tdColumns6'><input type='radio' data-qtype='"+item.quesType+"' data-id='"+item.id+"' data-type='"+item.type+"' name='"+item.id+"' value='"+charter[i]+"' style='display: none;'><label class='hRadio'></label>"+str+".</td>";
			}
			i++;
		});
		return xml;
	}
	
	function _show(data,type){
		var childOptions = "";
		$.each(data.childQuestion,function(i,item){
			childOptions += "<div class='readingSubItem'><div class='readingSubItemContent'>"+
        "<div class='readingSubItemPrompt'>"+item.serialNum+"."+item.title+"</div>"+
        "<div class='readingSubItemResponse'><table><tbody><tr>"+_createOptions(item.options,item,type)+"</tr></tbody></table></div>"+
      "</div></div>";
		});
		
		var xml = "<div id='testItem'><span id='spancurrentItemID' style='display:none;' class='currentItemContainer'></span><table width='0' cellspacing='0' cellpadding='0' border='0' class='biaoti'>"+
    "<tbody>"+
      "<tr>"+
       " <th width='100px' align='center' id='biaohao'>"+data.serialNum+"."+
        "        </th>"+
        "<td>&nbsp;</td>"+
      "</tr>"+
   " </tbody>"+
  "</table>"+
  "<div class='readingPromptVertical'>"+data.title+"</div>"+
  "<div class='readingSubItemsVertical'>"+childOptions+"</div>"+
"</div>";
		return xml;
	}
	
	function _underlineShow(data){
		return "<p>我是来测试的</p>";
	}
	
	var methods = {
		loadRead:function(id){
			var me = this;
		 $.ajax({
		        type: 'post',
		        url: 'load_question.action',
		        data: {
		        	examId:examId.val(),
		            quesId: id
		        },
		        success: function(data) {
		            if (data.status == 200) {
		                paperData = JSON.parse(data.data);
		                console.log(paperData);
		                clearOrderEvent();
		                var readQuestionType = paperData.hiddenType;
		                var xhtml = "";
		                if(readQuestionType == 1){
		                	xhtml = _show(paperData,readQuestionType);
		                }else if(readQuestionType == 2){
		                	xhtml = _show(paperData,readQuestionType);
		                }
		                $("#testpaperResponsepanel").html(xhtml);
		            	$("#testpaperResponsepanel .readingSubItemResponse").hradio();
		            	$("fun").base("selectAnswer");
		            } else {
		                layer.msg(data.error);
		            }
		        }
		    });
		},
		setRecord:function(options){
			var quesId =  options.quesId,ans = options.ans;
			 $.each(_info.arr, function(index, item) { // 循环试题
                if (item.id == quesId) { // 给对应 tid 的 answer 放入答案 ans
                    item.answer = ans;
                    item.type = options.type;
                    item.quesType = options.qtype;
                    return false;
                }
           	 });
           	 setLocal('_info' + examId.val(), _info); 
		},
		setSelectOum:function(findQum){
			var seqNum = findQum;
			$.each($('.e-question-num li'),function(i,data){
				var text = $(data).text();
				var splitArr = text.split("-");
				if(splitArr.length == 1){
					return;
				}
				
				if(splitArr[1] == findQum){
					var appendValue = parseInt(splitArr[1]) - parseInt(splitArr[0]);
					var value = (parseInt(findQum)-appendValue);//上一题
					seqNum = value;
					setSeqNum(value); 
				}
			});
			
			//普通模式
			if(seqNum == findQum){
				seqNum = getSeqNum();
			}
		},
		initSelectLiNumId:function(paramId){
			$.each($('.e-question-num li'),function(i,data){
				
				var id = $(data).attr('data-id');
				if(!(id == paramId)){
					return;
				}
				
				var text = $(data).text();
				var splitArr = text.split("-");
				if(splitArr.length == 1){
					return;
				}
				
				var value =  parseInt(splitArr[1]);//上一题
				setSeqNum(value); 
			});
		},
		fetchQuestionStart:function(){
			var startItem = $('.e-question-num li:first');
			var text = $(startItem).text();
			var splitArr = text.split("-");
			if(splitArr.length == 1){
				return text;
			}
			var value =  parseInt(splitArr[0]);//上一题
			return value;
		},
		fetchQuestionEnd:function(){
			var startItem = $('.e-question-num li:last');
			var text = $(startItem).text();
			var splitArr = text.split("-");
			if(splitArr.length == 1){
				return text;
			}
			var value =  parseInt(splitArr[1]);//上一题
			return value;
		},
		selectAnswer:function(quesId){
			_info = getLocal('_info' + examId.val());
				
			 $.each(_info.arr, function(index, item) {
			 	if(item.type != 2){
			 		return;
			 	}

			 	if(typeof item.qtype == 'undefinde' && item.quesType != 115){
			 		return;
			 	}
		 		var value = item.id.split('_');
		 		//进行选中
		 		$('.e-question-num li[data-id='+value[0]+']').css("background","#a7f9fa");
				$('input[name='+item.id+']').eq(obj[item.answer[0]]).next().addClass("hRadio_Checked");
			 
			 });
		}
	}
	
	$.fn.base = function(method){
	  if ( methods[method] ) {  
	      return methods[method].apply( this, Array.prototype.slice.call( arguments, 1 ));  
	    } else if ( typeof method === 'object' || ! method ) {  
	      return methods.init.apply( this, arguments );  
	    } else {  
	      $.error( 'Method ' +  method + ' does not exist on jQuery.tooltip' );  
	    }      
		    
	}
})( jQuery );  