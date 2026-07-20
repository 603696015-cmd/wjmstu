/**
 * 课程详情页JS调用
 */
function remainTime() {
	var iDay, iHour, iMinute, iSecond;
	var sDay="", sHour="", sMinute="", sSecond="", sTime="";
	if (iTime >= 0) {
		iDay = parseInt(iTime/24/3600);
		if (iDay > 0) {
			sDay = '<i class="c_f60 f14 fb">' + iDay + '</i>&nbsp;天';
		}
		iHour = parseInt((iTime/3600)%24);
		if (iHour > 0){
			sHour = '&nbsp;<i class="c_f60 f14 fb">' + iHour + '</i>&nbsp;小时';
		}
		iMinute = parseInt((iTime/60)%60);
		if (iMinute > 0){
			sMinute = '&nbsp;<i class="c_f60 f14 fb">' + iMinute + '</i>&nbsp;分';
		}
		iSecond = parseInt(iTime%60);
		if (iSecond >= 0){
			sSecond = '&nbsp;<i class="c_f60 f14 fb">' + iSecond + '</i>&nbsp;秒';
		}
		sTime='最近直播课倒计时 '+sDay+sHour+sMinute+sSecond;
		if(iTime==0){
			clearTimeout(Account);
			sTime='<i style="color:red;font-weight:bold">有课程正在直播中...</i>';
		} else {
			Account = setTimeout("remainTime()",1000);
		}
		iTime=iTime-1;
	} else {
		sTime='<i style="color:red;font-weight:bold">有课程正在直播中...</i>';
	}
	$("#remain_time").html(sTime);
}

function getBoxContent(tab) {
	$.ajax({
	//	url:"index.action?mod=course&act=show&do="+tab+"&sid="+Course.SID+"&courseid="+Course.CourseID+"&r="+Math.random(),
		url:"cpagelist.action?course.id="+Course.id+"&ctype=1&mod="+tab,
		type:"get",
		beforeSend:function(){
			$("#box_content").html('<div class="tc pt30"><img border="0" src="http://res.ckimg.com/sites/www/v2/images/public/loading.gif" /></div>');
		},
		success:function(data) {
			$("#box_content").html(data);
		}
	});
}

function addtoCart(live){
		if(KK.checkLogin() === false){
			$.ckLoginBoxy();
			return false;
		}		
		$.ajax({
			url:'/?mod=cart&act=init&sid='+Course.SID+'&courseid='+Course.CourseID+'&live='+live+'&r='+Math.random(),
			type:'get',
			dataType:'json',
			beforeSend:function(){
				loading = $.ckTipsBoxy({type:"loading", mask:true,message:"提交数据，请稍后"});
			},
			success:function(ret) {
				loading.cancel();
				if(ret.code == 0){
					var msg = '<div class="d_d_gx lh25 mt25 mb30 f14 c_777">';
					msg += '<img class="img" src="http://res.ckimg.com/sites/www/v2/images/public/ico_ok_25x25.png">';
					msg += '<p>已加入购物车！</p>';
					msg += '<p class="f12"><a href="/?mod=cart&act=show" target="_blank">查看我的购物车>></a></p>';
					msg += '</div>';
					msg += '<p class="tc">';
					msg += '<a href="javascript:;" id="try_success_confirm" class="c_btn37a"><span>确 定</span></a>';
					msg += '</p>';
					var ajaxBox = $.ckBoxy({
						content: msg,
						title : "提示",
						width : 338,
						height : 236,
						callback : function(){
							$(".close").click(function(){
								ajaxBox.cancel();
							});
							$("#try_success_confirm").live('click', function(){
								ajaxBox.cancel();
								window.location.reload();
							});
						}
					});
					KK.updateCartCount();
				}else{
					if(ret.code == -4){
						var ajaxBox = $.ckBoxy({
							content: $("#over_live_limit_cart").html(),
							title : "风险提示",
							width : 380,
							height : 250,
							callback : function(){
								$(".close").click(function(){
									ajaxBox.cancel();
								});								
								$("#cancel_live_limit_cart").live('click', function(){
									ajaxBox.cancel();
								});
								$("#confirm_live_limit_cart").live('click', function(){
									addtoCart(1);
									return false;
								});
							}
						});
						return false;						
					}
					$.ckAlert({message:ret.data});
				}
			}
		});	
}

$(function(){
	$("#selected_class").val('');
	
	//装载课程简介
	getBoxContent('course');
	
	//标签切换
	$("#tab_switch li").click(function(){
		var tab = $(this).attr('tab');
		$("#tab_switch li").removeClass('curr');
		$(this).addClass('curr');
		getBoxContent(tab);
		return false;
	});
	
	//学生模块“更多”操作
	$("#student_tab_switch").click(function(){
		$("#tab_switch li").removeClass('curr');
		$("#tab_switch li[tab='student']").addClass('curr');
		getBoxContent('student');
		return false;
	});
	
	//学生模块“更多”操作
	$("#comment_tab_switch").click(function(){
		$("#tab_switch li").removeClass('curr');
		$("#tab_switch li[tab='comment']").addClass('curr');
		getBoxContent('comment');
		return false;
	});
	
	//自定义分类切换效果
	$("#custom_type_list").find("span").toggle(
		function(){
			$(this).parent().addClass('unfold').find('ul').slideDown();
		},
		function(){
			$(this).parent().removeClass('unfold').find('ul').slideUp();
		}
	);
	
	//收藏课程
	$("#collect_course").live('click',function(){
		if(KK.checkLogin() === false){
			$.ckLoginBoxy();
			return false;
		}else{
			var loading = null;
			$.ajax({
				url:'/?mod=course&act=show&do=collect&sid='+Course.SID+'&courseid='+Course.CourseID+'&r='+Math.random(),
				type:'get',
				dataType:'json',
				success:function(ret) {
					if (ret.code == 0) {
						$.ckTipsBoxy({type:"success", message:"收藏成功", mask:true});
						$("#collect_course").removeClass('c_btn26j').addClass('c_btn26k').attr('id', 'uncollect_course').children().eq(0).text('取消收藏');
					} else {
						$.ckAlert({message:ret.data});
					}
				}
			});
			return false;
		}
	});
	
	$("#uncollect_course").live('click',function(){
		if(KK.checkLogin() === false){
			$.ckLoginBoxy();
			return false;
		}else{
			var loading = null;
			$.ajax({
				url:'/?mod=course&act=show&do=uncollect&sid='+Course.SID+'&courseid='+Course.CourseID+'&r='+Math.random(),
				type:'get',
				dataType:'json',
				success:function(ret) {
					if (ret.code == 0) {
						$.ckTipsBoxy({type:"success", message:"取消收藏成功", mask:true});
						$("#uncollect_course").removeClass('c_btn26k').addClass('c_btn26j').attr('id', 'collect_course').children().eq(0).text('课程收藏');
					} else {
						$.ckAlert({message:ret.data});
					}
				}
			});
			return false;
		}
	});
	
	//收藏学校
	$("#collect_school").live('click',function(){
		if(KK.checkLogin() === false){
			$.ckLoginBoxy();
			return false;
		}else{
			$.ajax({
				url:'/?mod=school&act=edit&do=collect&sid='+Course.SID+'&r='+Math.random(),
				type:'get',
				dataType:'json',
				success:function(ret) {
					if (ret.code == 0) {
						$.ckTipsBoxy({type:"success", message:"收藏成功", mask:true});
						$("#collect_school").attr('id', 'uncollect_school').text('取消收藏');
					} else {
						$.ckAlert({message:ret.data});
					}
				}
			});
			return false;
		}
	});
	
	//取消收藏学校
	$("#uncollect_school").live('click',function(){
		if(KK.checkLogin() === false){
			$.ckLoginBoxy();
			return false;
		}else{
			$.ajax({
				url:'/?mod=school&act=edit&do=unCollect&sid='+Course.SID+'&r='+Math.random(),
				type:'get',
				dataType:'json',
				success:function(ret) {
					if (ret.code == 0) {
						$.ckTipsBoxy({type:"success", message:"取消收藏成功", mask:true});
						$("#uncollect_school").attr('id', 'collect_school').text('收藏学校');
					} else {
						$.ckAlert({message:ret.data});
					}
				}
			});
			return false;
		}
	});
	
	//学校课程排行
	$("#collect_list_switch").click(function(){
		$("#buy_list_switch").removeClass('curr');
		$(this).addClass('curr');
		$("#buy_top_list").hide();
		$("#collect_top_list").show();
	});
	$("#buy_list_switch").click(function(){
		$("#collect_list_switch").removeClass('curr');
		$(this).addClass('curr');
		$("#collect_top_list").hide();
		$("#buy_top_list").show();
	});
	
	$(".live_class").hover(
		function(){
			$("#detail_class_info").show();
		},
		function(){
			$("#detail_class_info").hide();
		}
	);	
	
	//开通短信通知
	$("#message_remind").click(
		function(){
			var Box = $.ckAjaxBoxy({
				url : '/?mod=student&act=course&do=remind',
				title : "短信提醒",
				width : 354,
				height : 194,
				callback:function(){
					$("#fe_dialogBox").find("a.cancel").unbind().bind('click', function(){
						Box.cancel();
					});
				}
			});
		}
	);	
	
	//立刻购买
	$("a.btn_lkgm").click(function(){
		if(KK.checkLogin() === false){
			$.ckLoginBoxy();
			return false;
		}else{
			var buy_type = $("#buy_course_type").text();
			if (Course.Cost == 0) {
				//如果是免费课程，则调用免费购买
				if (parseInt(Course.StudentNumber) >= parseInt(Course.LiveStudentLimit)) {
					var ajaxBox = $.ckBoxy({
						content: $("#over_live_limit").html(),
						title : "风险提示",
						width : 356,
						height : 245,
						callback : function(){
							$("#cancel_live_limit").live('click', function(){
								ajaxBox.cancel();
							});
							
							$("#confirm_live_limit").live('click', function(){
								$.ajax({
									url:'/?mod=order&act=create&do=freebuy&sid='+Course.SID+'&courseid='+Course.CourseID+'&r='+Math.random(),
									type:'get',
									dataType:'json',
									success:function(ret) {
										if (ret.code == 0) {
											$.ckTipsBoxy({
												type:"success", 
												width:352, 
												message:"购买成功，可以开始学习了。",
												mask:true,
												callback:function() {
													window.location.reload();
												}
											});
										} else {
											$.ckAlert({message:ret.data});
										}
									}
								});
								return false;
							});
						}
					});
					return false;
				} else {
					$.ajax({
						url:'/?mod=order&act=create&do=freebuy&sid='+Course.SID+'&courseid='+Course.CourseID+'&r='+Math.random(),
						type:'get',
						dataType:'json',
						success:function(ret) {
							if (ret.code == 0) {
								$.ckTipsBoxy({
									type:"success", 
									width:352, 
									message:"购买成功，可以开始学习了。",
									mask:true,
									callback:function() {
										window.location.reload();
									}
								});
							} else {
								$.ckAlert({message:ret.data});
							}
						}
					});
					return false;
				}
			} else {
				//判断单课购买				
				if (buy_type == 2) {
					var cids = $("#selected_class").val();
					if (!cids) {
						$(".extra_gmlx_txt").show();
						return false;
					}
				} else {
					cids = 0;
				}
				
				var jumpFlag = 1;
				$.ajax({
					url:'/?mod=order&act=show&do=checkCourseCost&sid='+Course.SID+'&courseid='+Course.CourseID+'&cids='+cids+'&rand='+Math.random(),
					type:'get',
					async:false,
					dataType:'json',
					success:function(ret){
						if (ret.code == 1) {
							$.ajax({
								url:'/?mod=order&act=create&do=freebuy&sid='+Course.SID+'&courseid='+Course.CourseID+'&r='+Math.random(),
								type:'get',
								async:false,
								dataType:'json',
								success:function(rs) {
									if (rs.code == 0) {
										$.ckTipsBoxy({
											type:"success", 
											width:352, 
											message:"购买成功，可以开始学习了。",
											mask:true,
											callback:function() {
												window.location.reload();
											}
										});
									} else {
										$.ckAlert({message:rs.data});
									}
									return false;
								}
							});
							jumpFlag = 0;
						} else if (ret.code == 2) {
							//单课免费购买
							$.ajax({
								url:'/?mod=order&act=create&do=freebuy&sid='+Course.SID+'&courseid='+Course.CourseID+'&cids='+cids+'&r='+Math.random(),
								type:'get',
								async:false,
								dataType:'json',
								success:function(rs) {
									if (rs.code == 0) {
										$.ckTipsBoxy({
											type:"success", 
											width:352, 
											message:"购买成功，可以开始学习了。",
											mask:true,
											callback:function() {
												window.location.reload();
											}
										});
									} else {
										$.ckAlert({message:rs.data});
									}
									return false;
								}
							});
							jumpFlag = 0;
						} else {
							if (parseInt(Course.StudentNumber) >= parseInt(Course.LiveStudentLimit)) {
								var ajaxBox = $.ckBoxy({
									content: $("#over_live_limit").html(),
									title : "风险提示",
									width : 356,
									height : 245,
									callback : function(){
										$("#cancel_live_limit").live('click', function(){
											ajaxBox.cancel();
										});
										
										$("#confirm_live_limit").live('click', function(){
											var u = $("#confirm_live_limit").attr('href');
											if (buy_type == 2) {
												u = u + '&cids='+cids;
											}
											location.href=u;
											return false;
										});
									}
								});
								jumpFlag = 0;
							}
						}
					}
				});
			}
			
			if (jumpFlag == 1) {
				if (buy_type == 2) {
					var url = $("a.btn_lkgm").attr('href');
					url = url + "&cids="+cids;
					location.href=url;
					return false;
				} else {
					return true;
				}
			} else {
				return false;
			}
		}
	});
	
	//报名试听
	$("a.btn_bmst1").click(function(){
		if(KK.checkLogin() === false){
			$.ckLoginBoxy();
			return false;
		}else{
			$.ajax({
				url:'/?mod=order&act=create&do=trial&sid='+Course.SID+'&courseid='+Course.CourseID+'&r='+Math.random(),
				type:'get',
				dataType:'json',
				success:function(ret) {
					if (ret.code == 0) {
						var msg = '<div class="d_d_gx lh25 mt25 mb30 f14 c_777">';
						msg += '<img class="img" src="http://res.ckimg.com/sites/www/v2/images/public/ico_ok_25x25.png">';
						msg += '<p>恭喜你，报名成功了！</p>';
						msg += '<p class="f12 c_999">你可以试听该课程的所有试听课了。 </p>';
						msg += '<p class="f12"><a href="/?mod=student&act=course">查看我的课程>></a></p>';
						msg += '</div>';
						msg += '<p class="tc">';
						msg += '<a href="javascript:;" id="try_success_confirm" class="c_btn37a"><span>确 定</span></a>';
						msg += '</p>';
						var ajaxBox = $.ckBoxy({
							content: msg,
							title : "提示",
							width : 338,
							height : 236,
							callback : function(){
								$("#try_success_confirm").live('click', function(){
									ajaxBox.cancel();
									window.location.reload();
								});
							}
						});
					} else {
						$.ckAlert({message:ret.data});
					}
				}
			});
			return false;
		}
	});
	
	//加入购物车
	$("a.btn_jrgwc").click(function(){
		addtoCart(0);
	});
	
	//单课购买
	$(".gmlx_zk").click(function(){
		$(".gmlx_bf").removeClass('gmlx_here');
		$(this).addClass('gmlx_here');
		$(".gmlx_bf_tips").hide();
		$(".extra_gmlx_txt").hide();
		$("#buy_course_type").text('1');
	});
	
	$(".gmlx_bf").click(function(){
		$(".gmlx_zk").removeClass('gmlx_here');
		$(this).addClass('gmlx_here');
		$(".gmlx_bf_tips").show();
		$("#buy_course_type").text('2');
	});
	
	$("#select_class").click(function(){
		var Box = $.ckAjaxBoxy({
			url : '/?mod=course&act=show&do=content&sid='+Course.SID+'&courseid='+Course.CourseID+'&rand='+Math.random(),
			title : '请选择你要购买的章节<span class="c_333">（<em class="c_f60">'+Course.ClassCost/100+'</em>元/节）</span>',
			width : 712,
			height : 606,
			callback:function(){
				$("#fe_dialogBox").find("a.cancel").unbind().bind('click', function(){
					Box.cancel();
				});
			}
		});
		return false;
	});
});