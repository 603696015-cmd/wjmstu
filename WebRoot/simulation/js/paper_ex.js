var examId = $("#examId"),
    paperData = '',
    itemNum = '',
    index = 0,
    timer;
checkExamId();

function checkExamId() {
    if (!examId.val() || typeof(examId.val()) == 'undefined' ||
        examId.val() == '') {
        layer.msg('获取试卷信息错误，请联系管理员！');
        return;
    }
}
initData(examId.val(),1);

//获取试卷所有数据
initAllData(examId.val());

function initAllData(examId){
	$.ajax({
        url: 'ajax_paper_all.action',
        data: {
            examId: examId
        },
        type: 'post',
        dataType:'json',
        success: function(data) {
            if (data.status == 200) {
                var allpaperData = jQuery.parseJSON(data.data);
                if (allpaperData == '') {
                    layer.msg('获取试卷信息错误，请联系管理员！');
                    return;
                }
                
                setLocal("all_exam_"+examId,allpaperData);
            } else {
            }
        }
    });
}

// 初始化试卷信息
function initData(examId,index) {
    $.ajax({
        url: 'ajax_paper.action',
        data: {
            examId: examId,
            index:index
        },
        type: 'post',
        async: false,
        success: function(data) {
            if (data.status == 200) {
                paperData = data.questionItem;
                //setLocal("all_exam_"+examId,paperData);
                if (paperData == '') {
                    layer.msg('获取试卷信息错误，请联系管理员！');
                    return;
                }
                itemNum = data.listnum;
            } else {
                layer.msg(data.error);
            }
        }
    });
}


//初始化音频数据
var itemBarTime;
var KyPlayer = {
    player: null,
    init: function(vol) {
        if (this.player) {
            this.player.setVolume(vol);
            this.player.settings.trackEnded = function() {}
        }
    },
    play: function(url) {

        if (this.player) {
            if (url) {
                this.player.load(url);
            }
            // this.player.play();

            if (this.player.settings.useFlash) {
                window.setTimeout(function() {
                    KyPlayer.player.play();
                }, 100);
            } else {
                KyPlayer.player.play();
            }
        }
    },
    setVolume: function(vol) {
        this.player.setVolume(vol);
    }
};


$.each(itemNum, function(index, item) {
    item.parts = "第" + item.parts + "部分";
    createNum($(".e-question").eq(0), item);
});
$.cookie('q_num', paperData.length);
fetchQuestion((getSeqNum() - 1));
//初始化试第一题xml数据
function fetchQuestion(index,showNum) {
    $.ajax({
        type: 'post',
        url: 'load_question.action',
        data: {
            examId: examId.val(),
            index: index
        },
        success: function(data) {
            if (data.status == 200) {
                paperData = JSON.parse(data.data);
                clearOrderEvent();
                if(paperData.type== 1){
                	//听力模式
                	listenMusicMode(paperData);//听力模式
                }else if(paperData.type ==2){
                	//阅读模式
                	if(typeof showNum == 'undefined'){
                		readMode(paperData);
                	}else{
                		//阅读模式
                    	readMode(paperData,showNum);
                	}
                    $("fun").base("selectAnswer");
                }else if(paperData.type ==3){
                	
                	if(typeof showNum == 'undefined'){
                		//听写模式
                    	writeMode(paperData);
                	}else{
                		//听写模式
                    	writeMode(paperData,showNum);
                	}
                
                	LoadOrderQuestion();
                    $(".content").css("float", "none");
                    $(".content").css("width", "auto");
                    $("#QuestionContainer").find("div").attr("DragObj", "0");
                }
                //判定用户是否选中答案
                selectAnswer(paperData.id);
            } else {
                layer.msg(data.error);
            }
        }
    });

};

/**
 * 阅读模式
 */
function readMode(data,showNum){
	var xhtml = null;
	switch(data.quesType){
	  case "100":
		  //单选题;
		  if(typeof showNum == 'undefined'){
			  xhtml = loadSingleQuestion(data);
		  }else{
			  xhtml = loadSingleQuestion(data,showNum);
		  }
	
		  break;
	  case "110":
		  if(typeof showNum == 'undefined'){
			  xhtml = loadPicPinyinQuestion(data);
		  }else{
			  xhtml = loadPicPinyinQuestion(data,showNum);
		  }
	
		  break;
	  case "104":
		  //多选题
		  break;
	}
	$("#testpaperResponsepanel").html(xhtml);
	$("#testpaperResponsepanel .judgementResponse").hradio();
	$("#testpaperResponsepanel .singleChoiceResponse").hradio();
}
/**
 * 书写模式
 */
function writeMode(data,showNum){
	var xhtml = null;
	switch(data.quesType){
	  case "1019":
		  //单选题;
		  if(typeof showNum == 'undefined'){
			  xhtml = loadDropQuestion(data);
		  }else{
			  xhtml = loadDropQuestion(data,showNum);
		  }
		  break;
	  case "104":
		  //多选题
		  break;
	}
	$("#testpaperResponsepanel").html(xhtml);
}


function loadPicPinyinQuestion(data,showNum){

	
	var num = typeof showNum == 'undefined'?getSeqNum():showNum;
	var xml = "<div class='judgementCenter'><span id='spancurrentItemID' style='display:none;' class='currentItemContainer'></span><table width='0' cellspacing='0' cellpadding='0' border='0' class='biaoti' id='biaohao' >"+
    "<tbody>"+
     " <tr>"+
      "  <th width='100px' align='center' id='biaohao'>"+num+"."+
       "             </th>"+
        "<td>"+
         "         &nbsp;"+
          "      </td>"+
      "</tr>"+
    "</tbody>"+
  "</table>"+
  "<div class='judgementContent'>"+
   " <div class='judgementPrompt'>"+data.title+"</div>"+
   " <div class='judgementResponse'>" +
   "  <div class='judgementCorrect'>" +
   "   <input type='radio' name='single"+data.id+"' data-type='"+data.type+"' data-id='single_"+data.id+"' value='T'style='display: none;' />" +
   "   <label class='judgementCorrectLabel'></label>" +
   "   <div class='judgementCorrectSpan'></div> " +
   "  </div> " +
   "  <div class='judgementWrong'>" +
   "  <input type='radio' name='single"+data.id+"'  data-type='"+data.type+"' data-id='single_"+data.id+"' value='F' style='display: none;' />" +
   " <label class='judgementWrongLabel'></label>" +
   "<div class='judgementWrongSpan'></div> " + "</div> " +
      "</div> </div>";
	return xml;
}


/**
 * 加载拖拽题
 * @param data
 */
function loadDropQuestion(data,showNum){
	var options = data.title.split("-=SpEl=-");
	var tableTr = "";
	$.each(options,function(key,item){
		 var letter = fetchOptionChina(key);
		 if(key == options.length-1){
			 tableTr += "<div class='DragBox' id='order"+letter+"' tag='"+letter+"' overclass='OverDragBox' data-type='"+data.type+"'  data-id='single_"+data.id+"' dragclass='DragDragBox' ondblclick='ResetSource()' dragobj='0'>"+item+"</div><br dragobj='0'>";	
		 }else{
			 tableTr += "<div class='DragBox' id='order"+letter+"' tag='"+letter+"' overclass='OverDragBox' data-type='"+data.type+"'  data-id='single_"+data.id+"' dragclass='DragDragBox' ondblclick='ResetSource()' dragobj='0'>"+item+"</div>";
		 }
		 	
	});
	var num = typeof showNum == 'undefined'?getSeqNum():showNum;
	var xml = " 请考生按照合理的顺序，用<span style='color:red' >鼠标拖动</span>词条（选项）至答题区内。"+
    "<div class='OrderExample5'></div><br><br />"+
    "<div id='OrderContainer'><span id='spancurrentItemID' style='display:none;' class='currentItemContainer'>4580bd99-aae4-489c-b032-660171c260e8</span>"+
     "   <table width='0' cellspacing='0' cellpadding='0' border='0' class='biaoti'>"+
      "      <tbody>"+
       "         <tr>"+
        "            <th width='100px' align='center' id='biaohao'>"+num+"."+
         "           </th>"+
          "          <td>&nbsp;</td>"+
           "     </tr>"+
           " </tbody>"+
        "</table>"+
        "<div class='DragContainer' id='QuestionContainer' overclass='OverDragContainer' style='display: block;' name='dragDiv' dropobj='0'>"+tableTr+" </div>"+
        "<div class='AnswerContainer' id='AnswerContainer' overclass='OverDragContainer' name='dragDiv' dropobj='0'></div>"+
        "<div id='ResponseContainer'><input id='TxtResponse' type='text' name='TxtResponse' class='TextResponse' onkeyup='ChangeText()'><br><span style='color:red;'>请在完成答题后，输入标点！</span></div><br><input id='orderItemResult' type='text' name='orderItemResult' style='display:none;'>"+
   " </div>";
	return xml;
}

/**
 * 加载阅读模式单选题
 */
function loadSingleQuestion(object,showNum){
	var options = object.options;
	var tableTr = "";
	$.each(options,function(key,item){
		 var letter = fetchOptionChina(key);	
		 tableTr += "<tr><td> "+
		 "     <td>&nbsp;<input type='radio' name='single"+object.id+"'  data-type='"+object.type+"'  data-id='single_"+object.id+"' value='"+letter+"' style='display: none;'><label class='hRadio'></label>"+letter+"."+item
         "      </td>"+
	      " </td></tr>";	
	});
	var num = typeof showNum == 'undefined'?getSeqNum():showNum;
	
	var xml = "<div class='singleChoiceLeft'>"+
    "<table width='0' cellspacing='0' cellpadding='0' border='0' class='biaoti' id='biaohao' >"+
     "   <tbody>"+
      "      <tr>"+
       "         <th width='100px' align='center' id='biaohao'>"+num+"."+
        "        </th>"+
         "       <td>"+
          "          &nbsp;"+
           "     </td>"+
            "</tr>"+
        "</tbody>"+
    "</table>"+
    "<div class='singleChoiceContent'>"+
     "   <div class='singleChoicePrompt'>"+object.title+"</div>"+
      "  <div class='singleChoiceResponse'>"+
       "     <table class='singleChoiceResponseTable'>"+
        "        <tbody>"+tableTr+"</tbody>"+
            "</table>"+
        "</div>"+
    "</div>"+
"</div>";
	return xml;
}

/**
 * 听力模式
 * @param data
 */
function listenMusicMode(data) {
    var xml = "";
	if (paperData.quesType == 1) {
		// 音频
		xml = questionXml(paperData);
	} else if (paperData.quesType == 2) {
		// 听音选图
		xml = questionListenXml(paperData);

	}

	$("#testpaperResponsepanel").html(xml);
	$("#testpaperResponsepanel .judgementResponse").hradio();
	$("#testpaperResponsepanel .singleChoiceResponse").hradio();

	// 初始化语音
	$("#testpaperResponsepanel #ItemIDBox").html(
			1 + "&nbsp;&nbsp;" + ConvertToTime(120));
	// 进度条
	$("#testpaperResponsepanel #progressBarBox").html("");
	var t1 = new ProgressBarEx($("#progressBarBox"), 15, 130, 1000);
	// var t = new ProgressBar(duration, 200, 1000);
	// 音频
	var volume = $.cookie("mediavolume");
	if (volume == null || volume == undefined) {
		volume = 50;
	}
	/*
	 * var mediaUrl = $("#playerWrap").attr("mediaUrl");
	 * KyPlayer.play(mediaUrl); $(".voicebjbj").show();
	 */
	startPlay();
}

function startPlay() {
    var mediaUrl = $("#playerWrap").attr("mediaUrl");
    KyPlayer.play(mediaUrl);
    $(".voicebjbj").show();
}

$(".e-paper-tab li").click(function() {
    var _this = $(this);
    var _index = _this.index(); // 当前点击对象索引
    _this.addClass("on").siblings().removeClass("on");
    var qc = $(".e-question");//.eq(_index);
   // qc.html("").show().siblings(".e-question").hide();
    // 生成题号
//    for (var k = 0; k < data[_index].type.length; k++) {
//        createNum(qc, data[_index].type[k]);
//    }
    qc.html("");
    var type = _this.attr("data-type");
  
    //重新初始化试卷缓存数据
    initData(examId.val(),_this.attr("data-type"));
    $.each(itemNum, function(index, item) {
        item.parts = "第" + item.parts + "部分";
        createNum($(".e-question").eq(0), item);
    });
    if(type == 1){
       //下一题
       setSeqNum(1);
       fetchQuestion((getSeqNum() - 1));
   }else if(type==2){
	   //阅读
	   var num = fetchTypeIndex(type);
	   setSeqNum(num+1);
	   fetchQuestion(getSeqNum(),(num+1));
   }else if(type ==3){
	   //书写模式
	   var num = fetchTypeIndex(type);
	   setSeqNum(num+1);
	   fetchQuestion(getSeqNum(),(num+1));
   }
    
    if(type!=1){
    	KyPlayer.player.pause();// 这个就是暂停
    	 $(".voicebjbj").hide();
    	 
    }
    
    
    selectAnswer();
    
    //禁用上一题按钮
    $('#btnPrev').attr('class','d_button');
	$("#btnPrev").attr("disabled","disabled");
	//解放下一题按钮
	$('#btnNext').attr('class','button');
	$("#btnNext").removeAttr("disabled");;
});

/**
 * 验证试题是否选中
 */
function fetchTypeIndex(type){
	var num = 0;
	var cacheData  = getLocal('all_exam_' + $('#examId').val());
	$.each(cacheData, function(index, item) { 
		if(item.type == type){
			num = index;
			return false;
		}
	});
	return num;
}

function createNum(ele, data) {
    var str = "<p class='e-question-topic'>" + data.parts +
        "</p><ul class='e-question-num clearfix'>";
    
    if(data.num.length >0){
        for (var i = 0; i < data.num.length; i++) {
      
            if (parseInt(i + 1) % 3 == parseInt(0)) {
                str += "<li class='mg-0' data-id=" + data.num[i] + ">" + data.num[i] + "</li>";
            } else {
                str += "<li data-id=" + data.num[i] + ">" + data.num[i] + "</li>";
            }

        }
    }else{
    	var ii = 0 ;
    	var temStr = [];
    	for (var i = 0; i < data.strNums.length; i++) {
    		if(i == 0){
    			temStr = data.readId.split(",");
    			temStr.splice(-1,1);
    		}
    	  	if(temStr.length >1){
    	  		data.readId = temStr[ii];
    	  		ii++;
    	  	}
    	  	
            if (parseInt(i + 1) % 3 == parseInt(0)) {
                str += "<li class='mg-0' is-read='true'  data-id=" + data.readId + ">" + data.strNums[i] + "</li>";
            } else {
                str += "<li is-read='true' data-id=" + data.readId + ">" + data.strNums[i] + "</li>";
            }

        }
    }
   
    str += "</ul>"
    ele.append(str);
    if (typeof $(".e-question-num li") != undefined) {
        $(".e-question-num li").unbind("click").bind("click", function(event) {
            var oNum = $(this).attr('data-id');
            var num = $("#itemIdNum").text();
            var isRead = $(this).attr('is-read');
            //if (oNum != num) {
            if(isRead == 'true'){
            	$("fun").base("loadRead",oNum);
                $("fun").base("selectAnswer");
                $("fun").base("initSelectLiNumId",oNum);
            }else{
            	setSeqNum(oNum);
                fetchQuestion(oNum - 1);
            }
                
            //}
            /*$(".e-wrapper-right").load("subject.html", function() {
            	alert("question" + oNum + "loading success!")
            });*/
            
            var endValue = $("fun").base("fetchQuestionEnd");//试题开始位置
            var startValue = $("fun").base("fetchQuestionStart");//试题开始位置
            if(startValue == getSeqNum()){
            	 //禁用上一题按钮
                $('#btnPrev').attr('class','d_button');
            	$("#btnPrev").attr("disabled","disabled");
            }else{
            	$('#btnPrev').attr('class','button');
        		$("#btnPrev").removeAttr("disabled");;
            }
            
            if(endValue == getSeqNum()){
            	 //禁用上一题按钮
                $('#btnNext').attr('class','d_button');
            	$("#btnNext").attr("disabled","disabled");
            }else{
            	$('#btnNext').attr('class','button');
        		$("#btnNext").removeAttr("disabled");;
            }
            
            event.stopPropagation();
        });
    } else {
        alert("请稍后...")
    }
}




// 进度条
function ProgressBar(duration, barwidth, barspeed) {
    if (!document.getElementById("spaceused1"))
        return;
    document.getElementById("spaceused1").innerHTML = "";
    var barincrement = parseFloat((barwidth * barspeed) / (duration * 1000)) + 0.01;
    this.holder = document.createElement('div');
    this.span = document.createElement('span');
    this.em = document.createElement('em');
    this.span.appendChild(this.em);
    this.holder.appendChild(this.span);
    this.holder.className = 'progressBar';
    this.holder.id = "progressBar1";
    this.em.style.left = '0px';
    this.em.style.top = '0px';
    this.em.style.width = '0px';

    var that = this;
    var barWidth = barincrement;
    this.show = function() {
        if (document.getElementById("spaceused1") != null) {
            document.getElementById("spaceused1").appendChild(this.holder);
            itemBarTime = setInterval(this.grow, barspeed);
            // document.getElementById("spaceused1").innerHTML = "<div
            // class=\"progressBar\"><span><em style=\"LEFT: 0px; WIDTH:
            // 0px;\">连接到百度</em><span/></div>";
            // alert(document.getElementById("spaceused1").innerHTML);
        }
    };
    this.grow = function() {
        var holderWidth = 200;
        barWidth += barincrement;
        barWidth = Math.min(barWidth, holderWidth);
        barWidth = Math.max(barWidth, 0);
        that.em.style.width = barWidth + 'px';
        if (barWidth === holderWidth) {
            clearInterval(itemBarTime);
        }
    };
    this.clear = function() {
        if (itemBarTime) {
            clearInterval(itemBarTime);
        }
    }
    this.clear();
    this.show();

}

// 进度条
function ProgressBarEx(divObj, duration, barwidth, barspeed) {
    if (!divObj)
        return;
    $(divObj).html("");
    var barincrement = parseFloat((barwidth * barspeed) / (duration * 1000)) + 0.01;
    this.holder = document.createElement('div');
    this.span = document.createElement('span');
    this.em = document.createElement('em');
    this.span.appendChild(this.em);
    this.holder.appendChild(this.span);
    this.holder.className = 'progressBar';
    this.em.style.left = '0px';
    this.em.style.width = '0px';

    var that = this;
    var barWidth = barincrement;
    this.show = function() {
        if (divObj != null) {
            $(divObj).append(this.holder);
            itemBarTime = setInterval(this.grow, barspeed);
        }
    };
    this.grow = function() {
        var holderWidth = 200;
        barWidth += barincrement;
        barWidth = Math.min(barWidth, holderWidth);
        barWidth = Math.max(barWidth, 0);
        that.em.style.width = barWidth + 'px';
        if (barWidth === holderWidth) {
            clearInterval(itemBarTime);
        }
    };
    this.clear = function() {
        if (itemBarTime) {
            clearInterval(itemBarTime);
        }
    }
    this.clear();
    this.show();
}

/**
 * 转换时间
 */
function ConvertToTime(duration) {
    var min = 0;
    var sec = 0;
    var retText = "";
    if (duration <= 0) {
        min = 0;
        sec = 0;
    } else {
        min = parseInt(duration / 60);
        sec = parseInt(duration % 60);
    }
    if (min > 0) {
        retText += "<strong>" + min + "</strong>" + "分";
    }
    retText += "<strong>" + sec + "</strong>" + "秒";
    return "共" + retText;
}

/**
 * 获取当前序号
 */
function getSeqNum() {
    var num = $.cookie('seqNum');
    if (num == null || !num) {
        $.cookie('seqNum', 1);
        return 1;
    }

    if (num < 0) {
        $.cookie('seqNum', 1);
        return num;
    }

    return num;
}

/**
 * 设置当前序号
 */
function setSeqNum(index) {
    $.cookie('seqNum', index);
}

var keyType = 0; //0代表正常点击，1代表通过键盘点击（针对点击回车后会触发按钮事件）
//判断是否是回车键
function checkEnter() {
    //keytype:1代表由按键触发
    if (keyType == 1) {
        keyType = 0;
        return true;
    } else {
        return false;
    }
}

//获取下一题试题ID
function fetchPreQuesIdSeq(findQum){
	var boolean = 0;
	$.each($('.e-question-num li'),function(i,data){
		var id = $(data).attr('data-id');
		var text = $(data).text();
		var splitArr = text.split("-");
		if(splitArr.length == 1){
			return;
		}
		
		if(splitArr[1] == findQum){
			boolean = id;
			var appendValue = parseInt(splitArr[1]) - parseInt(splitArr[0]);
			var value = parseInt(findQum)-appendValue;
			setSeqNum(value); 
			return false;
		}
	});
	return boolean;
}

//上一题
function goPrevItem() {
    //上一题
    var nextNum = getSeqNum();
    var seq = fetchPreQuesSeq(nextNum);//获取当前减少的序列
    var idValue = fetchPreQuesIdSeq(seq-1);
    nextNum = seq;
    if(idValue!= 0){
    	$("fun").base("loadRead",idValue);
        $("fun").base("selectAnswer");
    }else{
    	setSeqNum(parseInt(nextNum) - 1);
	    //上一题
	    fetchQuestion((getSeqNum() - 1));
    }
    var startValue = $("fun").base("fetchQuestionStart");//试题开始位置
    if(startValue == getSeqNum()){
    	 //禁用上一题按钮
        $('#btnPrev').attr('class','d_button');
    	$("#btnPrev").attr("disabled","disabled");
    }
   
}

//获取上一题试题ID
function fetchPreQuesSeq(findQum){
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
	
	return seqNum;
}

//下一题
function goNextItem() {
	var seqNum = getSeqNum();
	
	var currentValue = getCurrentSeq(seqNum);
	if(currentValue != 0){
		seqNum = currentValue;
	}
	var dataNum = parseInt(seqNum) + 1;
	setSeqNum(dataNum);
	var idValue = fetchQuesSeq(dataNum) ;
    
    //下一题
    var nextNum = getSeqNum();
   
    if(idValue!= 0){
    	$("fun").base("loadRead",idValue);
        $("fun").base("selectAnswer");
    }else{
    	fetchQuestion(nextNum-1);	
    }
    
    var endValue = $("fun").base("fetchQuestionEnd");//试题开始位置
    if(endValue == getSeqNum()){
    	 //禁用上一题按钮
        $('#btnNext').attr('class','d_button');
    	$("#btnNext").attr("disabled","disabled");
    }
    
}

function getCurrentSeq(index){
	var boolean = 0;
	$.each($('.e-question-num li'),function(i,data){
		var id = $(data).attr('data-id');
		var text = $(data).text();
		var splitArr = text.split("-");
		if(splitArr.length == 1){
			return;
		}
		
		if(splitArr[0] == index){
			var appendValue = parseInt(splitArr[1]) - parseInt(splitArr[0]);
			boolean = parseInt(index)+appendValue;
			return false;
		}
	});
	return boolean;
}




//获取下一题试题ID
function fetchQuesSeq(findQum){
	var boolean = 0;
	$.each($('.e-question-num li'),function(i,data){
		var id = $(data).attr('data-id');
		var text = $(data).text();
		var splitArr = text.split("-");
		if(splitArr.length == 1){
			return;
		}
		
		if(splitArr[0] == findQum){
			boolean = id;
			var appendValue = parseInt(splitArr[1]) - parseInt(splitArr[0]);
			var value = parseInt(findQum)+appendValue;
			setSeqNum(value); 
			return false;
		}
	});
	return boolean;
}

function fetchOptionChina(index){
	var options = ["A","B","C","D","E","F","G","H","I","J","K","L"];
	return options[index];
}

function questionListenXml(object){
	var options = object.options;
	var tableTd = "";
	$.each(options,function(key,item){
		 tableTd += " <td> "+
		   "   <img height='130' alt='' width='114' src='"+item+"' /><br />"+
		    "  <input type='radio'   name='single"+object.id+"' data-type='"+object.type+"' data-id='single_"+object.id+"' value='"+fetchOptionChina(key)+"' style='display: none;' />"+
		     " <label class='hRadio'></label>"+fetchOptionChina(key)+
	      " </td>";	
	});
	var xhtml = "<div class='singleChoiceCenter'>"+
				"    <span id='spancurrentItemID' style='display:none;' class='currentItemContainer' ></span>"+
				 "   <table width='0' cellspacing='0' cellpadding='0' border='0' class='biaoti' id='biaohao' > "+
				  "   <tbody> "+
				   "   <tr> "+
				    "   <th width='100px' align='center' id='biaohao'>"+getSeqNum()+". </th>"+ 
				     "  <td> &nbsp; </td> "+
				      "</tr> "+
				    " </tbody> "+
				    "</table> "+
				    "<div class='singleChoiceContent' >"+ 
				    " <div class='singleChoicePrompt'>"+ 
				    "  <div id='playerWrap' mediaurl='/"+object.mp3Url+"'></div> "+
				    " </div> "+
				     "<div class='singleChoiceResponse'>"+ 
				     " <table class='singleChoiceResponseTable'>"+
				      " <tbody>"+
				       " <tr>"+ tableTd+"</tr>"+
				       "</tbody>"+
				      "</table> "+
				     "</div> "+
				    "</div> "+
				   "</div>";

	 return xhtml;
}

/**
 * 响应试题文本
 */
function questionXml(object) {
    var imgeSrc = $(object.imageUrl).find('img').attr("src");
    $("#itemIdNum").text(getSeqNum());
    var xhtml = "<div class='judgementCenter'>" +
        " <table width='0' cellspacing='0' cellpadding='0' border='0' class='biaoti' id='biaohao' > " +
        " <tbody> " +
        " <tr> " +
        " <th width='100px' align='center' id='biaohao'>" + getSeqNum() + ". </th>" +
        "<td> &nbsp; </td> " +
        "</tr> " +
        "</tbody> " +
        "</table> " +
        "<div class='judgementContent'>" +
        " <div class='judgementPrompt'> " +
        "  <div id='playerWrap' mediaurl='/" + object.mp3Url + "'></div>" +
        "  <br />" +
        "  <img height='85' alt='' width='98' src='" + imgeSrc + "' />" +
        " </div> " +
        " <div class='judgementResponse'>" +
        "  <div class='judgementCorrect'>" +
        "   <input type='radio' name='single"+object.id+"' data-type='"+object.type+"' data-id='single_"+object.id+"' value='T'style='display: none;' />" +
        "   <label class='judgementCorrectLabel'></label>" +
        "   <div class='judgementCorrectSpan'></div> " +
        "  </div> " +
        "  <div class='judgementWrong'>" +
        "  <input type='radio' name='single"+object.id+"'  data-type='"+object.type+"' data-id='single_"+object.id+"' value='F' style='display: none;' />" +
        " <label class='judgementWrongLabel'></label>" +
        "<div class='judgementWrongSpan'></div> " + "</div> " +
        "</div> " + "</div>";
    return xhtml;
}

function ConvertToTime(duration) {
    var min = 0;
    var sec = 0;
    var retText = "";
    if (duration <= 0) {
        min = 0;
        sec = 0;
    } else {
        min = parseInt(duration / 60);
        sec = parseInt(duration % 60);
    }
    if (min > 0) {
        retText += "<strong>" + min + "</strong>" + "分";
    }
    retText += "<strong>" + sec + "</strong>" + "秒";
    return "共" + retText;
}