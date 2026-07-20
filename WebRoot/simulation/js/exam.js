var _info = null;
var obj = { "A":0, "B":1, "C":2, "D":3, "E":4 };
var jumpRadion = {"T":0,"F":1};
(function ($) {
layui.use(['jquery', 'form', 'element', 'layer'], function() {
    var form = layui.form,
    element = layui.element,
    layer = layui.layer,
    timer = null,
    // 存储计时器
    isDown = false,
    // 存储是否倒计时结束 true => 倒计时结束，false => 未结束
    $ = layui.jquery,
  
    zTime = $('#spPartTime').attr('data-time') * 60;
    _info = { // 试题信息
        time: $('#spPartTime').attr('data-time') * 60,
        // 考试时长
        arr: [] 
        // 试题数组
    };

    
    init(); // 开始初始化
    function init() { // 初始化方法
    	var sessionCache = getLocal('_info' + $('#examId').val());
        if (sessionCache != null && sessionCache.arr.length>0) { // 判断Session里是否有数据
        	checkQuestionStatus();
        	selectAnswer();

        	 _info.time = getLocal('_info' + $('#examId').val()).time;
             //_info.answer = getLocal('_info' + $('#userId').val() + $('#res_cid').val()).answer;
        } else {
        	var allQuestionData = getLocal('all_exam_'+$('#examId').val());
            $(allQuestionData).each(function(index,item) { // 循环所有试题 添加在试题数组里
                if(item != null && typeof item.childQuestion != 'undefined' && item.childQuestion != null&&item.childQuestion.length > 0){
                    $.each(item.childQuestion,function(i,childItem){
                        _info.arr.push({
                           id:childItem.id,
                           answer:[]
                        });
                    });
                }else{
                	if(item == null){
                		return false;
                	}
                    var obj = {
                        id: item.id,
                        // 试题ID
                        answer: []
                        // 试题答案
                    };
                    _info.arr.push(obj); // 
                }
                
            })
        }
        setLocal('_info' + $('#examId').val(), _info);
        countDown(); // 倒计时
    }


    $('.cardActive').click(function() {
        $('.answerCard').addClass('active');
    });

    $('.cardClose').click(function() {
        $('.answerCard').removeClass('active');
    });

    $('.cardItem').click(function() {
        $('.answerCard').removeClass('active');
    });

    /* 字号设置 */
    $('.small').click(function() {
        $('.questionBox').addClass('small').removeClass('middle').removeClass('big');
    });
    $('.middle').click(function() {
        $('.questionBox').addClass('middle').removeClass('small').removeClass('big');
    });
    $('.big').click(function() {
        $('.questionBox').addClass('big').removeClass('middle').removeClass('small');
    });
    /* 字号设置 */

    form.on('submit(go)',
    function(data) {
        var info = data.field;
        var str = $('.cardBox a.active').length;
        if (isDown) {
            layer.alert('考试时间到，正在交卷');
            submitPaper();
        } else {
            if (str < ($('#number').val() - 0)) {
                // layer.msg('您还有题没有答完');
                var r_arr = [];
                var m_arr = [];
                var j_arr = [];
                $('.radio_item').each(function() {
                    if ($(this).attr('class').indexOf('active') < 0) {
                        r_arr.push('【第' + $(this).text() + '题】');
                    }
                });
                $('.multiple_item').each(function() {
                    if ($(this).attr('class').indexOf('active') < 0) {
                        m_arr.push('【第' + $(this).text() + '题】');
                    }
                });
                $('.judge_item').each(function() {
                    if ($(this).attr('class').indexOf('active') < 0) {
                        j_arr.push('【第' + $(this).text() + '题】');
                    }
                });
                var _html = '<p style="margin-bottom: 10px"><span style="color: #19a094">单选题：</span>' + r_arr.toString() + '</p>' + '<p style="margin-bottom: 10px"><span style="color: #19a094">多选题：</span>' + m_arr.toString() + '</p>' + '<p style="margin-bottom: 10px"><span style="color: #19a094">判断题：</span>' + j_arr.toString() + '</p><p style="color: red">请查看答题卡</p>';
                layer.open({
                    title: '您还有题没有答完',
                    area: ['500px', '450px'],
                    content: _html,
                    btn: ['确认'],
                    yes: function(index, layero) {
                        //按钮【按钮一】的回调
                        layer.close(index);

                    },
                    cancel: function() {
                        //右上角关闭回调
                        // layer.msg('您已取消');
                        //return false 开启该代码可禁止点击该按钮关闭
                    }
                });
                $('.cardHint').removeClass('layui-hide');
            } else {
                layer.open({
                    title: '提示',
                    area: ['500px', '250px'],
                    content: '<p style="text-align: center;margin-top: 30px;font-size: 16px">请认真检查所有答案，确认无误后点击确定按钮提交试卷。</p>',
                    btn: ['确定', '取消'],
                    yes: function(index, layero) {
                        //按钮【按钮一】的回调
                        submitPaper();
                    },
                    btn2: function(index, layero) {
                        layer.msg('您已取消');
                    },
                    cancel: function() {
                        //右上角关闭回调
                        // layer.msg('您已取消');
                        //return false 开启该代码可禁止点击该按钮关闭
                    }
                });
            }
        }
        return false;
    });

    
    $('#btnsubmitTest').bind('click',function(){
    	submitPaper();
    });
    
    /**
	 * 交卷
	 * @Author   Mr.Fan
	 * @DateTime 2017-09-12
	 */
    function submitPaper() {
        layer.msg('交卷处理中···', {
            icon: 16,
            shade: 0.4,
            time: 9999999999999999
        });
        var answer = _info.arr;
        // 处理答案
        $.each(answer,
        function(index, item) {
            item.answer = item.answer.toString();
        });
        $.ajax({
            type: 'post',
            url: 'exam_submit.action',
            data: {
                answer: JSON.stringify(answer),
                examId:$('#examId').val(),
                time: zTime - _info.time
            },
            dataType: 'json',
            success: function(res) {
                if (res.status == 200) {
                    clearInterval(timer);
                    clearLocal();
                    layer.closeAll(); 
                    layer.msg("提交试卷成功");
                    //window.location.href = '/business/exam/passStatus';
                }
            },
            error: function() {
                setTimeout(function() {
                    submitPaper();
                },
                5000)
            }
        })
    }

    /**
	 * 倒计时方法
	 * @return {[type]} [description]
	 */
    function countDown() {
        var eTimeSecond = _info.time;
        timer = setInterval(function() {
            eTimeSecond--;
           
            if (eTimeSecond > 0) {
                $('#spPartTime').html('倒计时：' + getTime(eTimeSecond)[0] + '分' + getTime(eTimeSecond)[1] + '秒');
                _info.time = eTimeSecond;
                setLocal('_info' + $('#examId').val(), _info);
            } else {
                $('#spPartTime').html('倒计时：' + getTime(eTimeSecond)[0] + '分' + getTime(eTimeSecond)[1] + '秒');
                isDown = true;
                clearInterval(timer);
                //自动提交试卷信息
                $('#btnsubmitTest').click();
            }
        },
        1000);
    }

    /**
	 * 获取剩余分钟数和秒数
	 * @param  {[number]} second [剩余秒数]
	 * @return {[arr]}        tArr[0]==>分钟数  tArr[1]==>秒数
	 */
    function getTime(second) {
        var tArr = [];
        tArr.push(parseInt(second / 60) < 10 ? '0' + parseInt(second / 60) : parseInt(second / 60));
        tArr.push(second % 60 < 10 ? '0' + second % 60 : second % 60);
        return tArr;
    }
    
});
    
    $.fn.hradio = function (options) {
        var self = this;
        return $(':radio+label', this).each(function () {
            $(this).addClass('hRadio');
            if ($(this).prev().is("checked"))
                $(this).addClass('hRadio_Checked');
        }).click(function (event) {
            var clickItem = $(this);
            
            //$(this).removeClass("hRadio_Checked");
            if (!$(this).prev().is(':checked') || !$(this).hasClass("hRadio_Checked")) {
				 if ($(this).prev().attr('disabled')) {
                    return;
                }
                $(".hRadio_Checked").each(function () {
                    if ($(this).prev().attr("name") == $(clickItem).prev().attr("name"))
                        $(this).removeClass("hRadio_Checked");
                });

                $(this).addClass("hRadio_Checked");
                var parentObj = $(this).prev();
              
                var ans = parentObj.val();
                var type = parentObj.attr('data-type');//当前类型id
                var quesId = parentObj.attr('data-id');
                var quesType = parentObj.attr('data-qtype');
                
                if(typeof quesType != 'undefined' && quesType== 115){
                	//阅读理解题
                    $("fun").base("setRecord",{
                        quesId: quesId,
                        ans:ans,
                        type:type,
                        qtype:quesType
                    });
                    $("fun").base("selectAnswer",quesId);
                }else{
                	quesId = quesId.split("_")[1];
                    $.each(_info.arr, function(index, item) { // 循环试题
                        if (item.id == quesId) { // 给对应 tid 的 answer 放入答案 ans
                            item.answer = ans;
                            item.type = type;
                        }
                    });
                    setLocal('_info' + $('#examId').val(), _info); 
                    //$(this).prev()[0].checked = true;
    				$(this).prev().attr('checked', true);
    				var type = 0;
    				$.each($(".e-paper-tab li"),function(i,item){
    					if($(item).hasClass("on")){
    						type = $(item).attr('data-type');
    						return false;
    					}
    				});
    				if(type == 1){
    					$('.e-question-num li').eq(getSeqNum()-1).css("background","#a7f9fa")
    				}else if(type == 2){
    					var num = fetchTypeIndex(type);
    					var index = getSeqNum()-1-num<0?0:getSeqNum()-1-num;
    					$('.e-question-num li').eq(index).css("background","#a7f9fa")
    				}
                }
				
			}

            event.stopPropagation();
        })
        .prev().hide();
    };
})(jQuery);

var leaveNume = 0,
// 记录离开页面次数
leaveTime = null; // 记录离开页面时间
function begin() {
    // console.log(1);
    leaveNume++;
    if (leaveNume > 5) {
        console.log('由于您离开页面次数过多，系统判定您有作弊嫌疑，正在立即交卷');
    } else {
        leaveTime = setTimeout(function() {
            console.log('由于您离开页面时间过长，系统判定您有作弊嫌疑，正在立即交卷');
        },
        50000);
    }
}

function finish() {
    // console.log(2);
    clearTimeout(leaveTime);
}

/**
 * 设置seSessionStorage
 * @Author   Mr.Fan
 * @DateTime 2017-07-28
 * @param    {[type]}   index [字段名]
 * @param    {[type]}   value [值]
 */
var setLocal = function(index, value) {
    window.localStorage.setItem(index, JSON.stringify(value));
}

/**
 * 获取seSessionStorage
 * @Author   Mr.Fan
 * @DateTime 2017-07-28
 * @param    {[type]}   index [字段名]
 * @return   {[type]}         [值]
 */
var getLocal = function(index) {
    return JSON.parse(window.localStorage.getItem(index));
}

/**
 * 获取seSessionStorage
 * @Author   Mr.Fan
 * @DateTime 2017-07-28
 * @return   {[type]}         [值]
 */
var clearLocal = function() {
    window.localStorage.clear();
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
 * check question select status
 */
var checkQuestionStatus = function(){
	_info = getLocal('_info' + $('#examId').val());
	 $.each(_info.arr,
			    function(index, item) { // 将答案数据展示在页面上
			  		if(item.answer.length > 0) {
			  			$('.e-question-num li').eq(index).css("background","#a7f9fa");
			        }
	});
};

var getTableType = function(){
	var data = $(".e-paper-tab li");
	var type = "";
	$.each(data,function(index,item){
		if($(item).hasClass("on")){
			type = $(item).attr("data-type");
			return false;
		}
	});
	
//	if(type == 1){
//		type = 1;
//	}else if(type == 2){
//		type = 100;
//	}
	return type;
};


var getIndex = function(num1){
	var index = 0;
	$.each($(".e-paper-tab li"),function(i,item){
		if($(item).hasClass("on")){
			type = $(item).attr('data-type');
			return false;
		}
	});
	if(type == 1){
		index = num1;
	}else if(type == 2){
		var num = fetchTypeIndex(type);
	    index = num1-num-1<0?0:num1-num;
	}else{
		var num = fetchTypeIndex(type);
	    index = num1-num-1<0?0:num1-num;
	}
	return index;
};

/**
 * 选择题时选中状态
 */
var selectAnswer = function(quesId){
	_info = getLocal('_info' + $('#examId').val());	
	var type = getTableType();
	var flag = false;
	if(typeof(quesId) == 'undefined'){
	    $.each(_info.arr,
	    function(index, item) { // 将答案数据展示在页面上
	    	if(type != item.type){
	    		return true;
	    	}
	    	//如果是阅读题
	    	if(item.type == 3 && item.answer.length > 1){
	    		$("#TxtResponse").val(item.answer);
	    		LoadOrderResponse(false,item.responseAns);
	    		var _index = getIndex(index);
	        	$('.e-question-num li').eq(_index).css("background","#a7f9fa");
	    		return true;
	    	}
	    	
	        if (item.answer.length > 1) {
	            $.each(item.answer,
	            function(i, t) {
	                $('.item[data-tid=' + item.id + ']').eq(t).prop('checked', true);
	            }); 
	            flag = true;
	            //$('.cardItem[data-tid=' + item.id + ']').addClass('active');
	        } else if (item.answer.length > 0) {
	        	if(item.answer[0] == "T" || item.answer[0] == "F"){
	        		$('input[name=single'+item.id+']').eq(jumpRadion[item.answer[0]]).next().addClass("hRadio_Checked");
	        	}else{
	        		$('input[name=single'+item.id+']').eq(obj[item.answer[0]]).next().addClass("hRadio_Checked");
	        	}
	        	flag = true;
	        }else{
	        	flag = false;
	        }
	        
	        if(flag){
	        	var _index = getIndex(index);
	        	$('.e-question-num li').eq(_index).css("background","#a7f9fa");
	        }
	    });
	}else{
		 $.each(_info.arr, function(index, item) { // 将答案数据展示在页面上
			 if(item.id == quesId){
		    	//如果是阅读题
		    	if(item.type == 3 && item.answer.length > 1){
		    		$("#TxtResponse").val(item.answer);
		    		LoadOrderResponse(false,item.responseAns);
		    		return true;
		    	}
				 
				 if (item.answer.length > 1) {
						$.each(item.answer, function(i, t) {
							$('.item[data-tid=' + item.id + ']').eq(t).prop('checked',
									true);
						});
						//$('.cardItem[data-tid=' + item.id + ']').addClass('active');
					} else if (item.answer.length > 0) {
						if (item.answer[0] == "T" || item.answer[0] == "F") {
							$('input[name=single' + item.id + ']').eq(
									jumpRadion[item.answer[0]]).next().addClass(
									"hRadio_Checked");
						} else {
							$('input[name=single' + item.id + ']').eq(
									obj[item.answer[0]]).next().addClass(
									"hRadio_Checked");
						}

						//$('.cardItem[data-tid=' + item.id + ']').addClass('active');
					}
				return;
			 }
			
		});
	}
	
};