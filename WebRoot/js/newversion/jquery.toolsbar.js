/**
*  该JS用于构造 ToolsBar 样式
*  ToolsBar_Add(Ts_Id , Ts_Name , Ts_Js , Ts_Margin)  
*  添加一个Tools按钮 Ts_Id 按钮ID Ts_Name 按钮名称 Ts_Js 按钮触发的函数  Ts_Margin 按钮间距
*  
*  ToolsBar_Disabled(Ts_IsDisabled)
*  将当前按钮禁用 根据 Ts_IsDisabled 来确定 为 "true" 时，禁用。  为"false"时,启用。
*  
*  author by dy
*
**/
if(typeof v_Tools=="undefined"){   
	v_Tools=true;   
     
	document.write("<link rel='stylesheet' type='text/css' href='css/newversion/jquery.toolsbar.css'/>");
	(function($){
			/****** 添加一个Tools ******/
			$.fn.ToolsBar_Add = function(Id,Name,Img,Js,HasEffect,Location){	
				$(this).addClass('Div_Init');		
				var CurrentObj = $(this);
				var ToolsBar_Html = [];
				var Ts_Id = Id;
				var Ts_Name = Name;
				var Ts_Img = Img;
				var Ts_Js = Js;
				var Ts_HasEffect = HasEffect;
				if(Ts_HasEffect==undefined || Ts_HasEffect=="undefined" || Ts_HasEffect==null || Ts_HasEffect.length==0){
					Ts_HasEffect = true;
				}
				if(Location==undefined || Location=="undefined" || Location==null || Location.length==0){
					Location = "left";
				}
				
				ToolsBar_Html.push("<div id='"+Ts_Id+"' Ts_Js='"+Ts_Js+"' IsDiabled='' istoogle='true' style='float:"+Location+";margin-left:5px;height:32px;cursor:default'>");
				ToolsBar_Html.push(    "<table border=0 cellspacing=0 cellpadding=0 height=25px style='margin-top:4px;' disabled=true>");
				ToolsBar_Html.push(	    "<tr>");
				ToolsBar_Html.push(		   "<td id='"+Ts_Id+"_left' style='width:3px;' ></td>");
				ToolsBar_Html.push(		   "<td id='"+Ts_Id+"_center' style='font-size:9pt;'>");
				if(Ts_Img!="undefined" && Ts_Img!=null && Ts_Img.length>0 ){
					ToolsBar_Html.push( "<div style='height:16px;'>");
					ToolsBar_Html.push(		"<div style='float:left;margin-top:0px'><img id='"+Ts_Id+"_img' src='"+Ts_Img+"'></div>");
					ToolsBar_Html.push(		"<div style='float:left;margin-top:3px;margin-left:2px;font-size:9pt;cursor:hander'>"+Ts_Name+"</div>");
					ToolsBar_Html.push( "</div>");

				}else{
					ToolsBar_Html.push( "<div style='height:24px;'>");
					ToolsBar_Html.push(		"<div style='float:left;2px;margin-top:2px;'>"+Ts_Name+"</div>");
					ToolsBar_Html.push( "</div>");
				}
				ToolsBar_Html.push( 		"</td>");
				ToolsBar_Html.push(         "<td id='"+Ts_Id+"_right' style='width:3px;'></td>");
				ToolsBar_Html.push(      "</tr>");
				ToolsBar_Html.push(     "</table>");
				ToolsBar_Html.push( "</div>");
				
				
				
				var ToolsBarObj = $(ToolsBar_Html.join(""));
				ToolsBar_Html = null;
				ToolsBarObj.appendTo(CurrentObj);
				/**
				if(Ts_HasEffect){
					if(Ts_Id=="toolbar_more"){
						
						$("#"+Ts_Id).bind('mouseover',function(){
								$("#"+Ts_Id+"_left").addClass("tb_left_up");
								$("#"+Ts_Id+"_center").addClass("tb_center_up");
								$("#"+Ts_Id+"_right").addClass("tb_right_up");
						});
						
						$("#"+Ts_Id).bind('mouseout',function(){
								$("#"+Ts_Id+"_left").removeClass("tb_left_up")
								$("#"+Ts_Id+"_center").removeClass("tb_center_up");
								$("#"+Ts_Id+"_right").removeClass("tb_right_up");
							});
						
						$("#"+Ts_Id).bind('mousedown',function(){
								$("#"+Ts_Id+"_left").addClass("tb_left_down").removeClass("tb_left_up");
								$("#"+Ts_Id+"_center").addClass("tb_center_down").removeClass("tb_center_up");
								$("#"+Ts_Id+"_right").addClass("tb_right_down").removeClass("tb_right_up");
						});
						
						
						
						$("#"+Ts_Id).bind("click" , function(){
							var istoogle = $(this).attr("istoogle");
							if(istoogle=="true"){
								$("#"+Ts_Id+"_left").addClass("tb_left_down").removeClass("tb_left_up");
								$("#"+Ts_Id+"_center").addClass("tb_center_down").removeClass("tb_center_up");
								$("#"+Ts_Id+"_right").addClass("tb_right_down").removeClass("tb_right_up");
								
								$(this).attr("istoogle" , "false");
								if(Ts_Js!=null && Ts_Js.length>0){
									jQuery.globalEval(Ts_Js);
								}
								
							}else{
								$(this).attr("istoogle" , "true");
								$("#Div_Content_downmenu").remove();
								
								$("#"+Ts_Id+"_left").addClass("tb_left_up").removeClass("tb_left_down");
								$("#"+Ts_Id+"_center").addClass("tb_center_up").removeClass("tb_center_down");
								$("#"+Ts_Id+"_right").addClass("tb_right_up").removeClass("tb_right_down");
								
								
							}
							
						});
					}else{
						$("#"+Ts_Id).bind('mouseover',function(){
								$("#"+Ts_Id+"_left").addClass("tb_left_up");
								$("#"+Ts_Id+"_center").addClass("tb_center_up");
								$("#"+Ts_Id+"_right").addClass("tb_right_up");
						});

						$("#"+Ts_Id).bind('mouseout',function(){
								$("#"+Ts_Id+"_left").removeClass("tb_left_up").removeClass("tb_left_down");
								$("#"+Ts_Id+"_center").removeClass("tb_center_up").removeClass("tb_center_down");
								$("#"+Ts_Id+"_right").removeClass("tb_right_up").removeClass("tb_right_down");
							});

						$("#"+Ts_Id).bind('mouseup',function(){
								$("#"+Ts_Id+"_left").addClass("tb_left_up").removeClass("tb_left_down");
								$("#"+Ts_Id+"_center").addClass("tb_center_up").removeClass("tb_center_down");
								$("#"+Ts_Id+"_right").addClass("tb_right_up").removeClass("tb_right_down");
							});
						
						$("#"+Ts_Id).bind('mousedown',function(){
								$("#"+Ts_Id+"_left").addClass("tb_left_down").removeClass("tb_left_up");
								$("#"+Ts_Id+"_center").addClass("tb_center_down").removeClass("tb_center_up");
								$("#"+Ts_Id+"_right").addClass("tb_right_down").removeClass("tb_right_up");
						});

						ToolsBarObj.bind("click" , function(){
							if(Ts_Js!=null && Ts_Js.length>0){
								jQuery.globalEval(Ts_Js);
							}
						});
					}
					
				}
				*/
			};
			
			
			$.fn.ToolsBar_Split = function(){	
				var CurrentObj = $(this);
				var InnerHTML = "<div style='background:url(/siang/module/js/jquery.toolsbar/image/tb_split.gif) no-repeat;";
				InnerHTML += " height:22px;width:2px;font-size:1px;float:left;margin-left:5px;margin-top:6px;'></div>";
				var SplitObj = $(InnerHTML);
				SplitObj.appendTo(CurrentObj);
			};
			

			/**** 禁用该按钮 (禁用图片文件名以 "un_" 开头)*****/
			$.fn.ToolsBar_Disabled = function(obj){
				var CurrentObj = $("#"+obj);
				if(CurrentObj.attr("IsDiabled")=="true"){
					return;
				}
				CurrentObj.attr("IsDiabled" , "true");

				var	Ts_Id=  CurrentObj[0].id;
				var ImgObj = $("#"+Ts_Id+"_img");
				var imgSrc = ImgObj.attr("src");
				if(imgSrc!=null && imgSrc.length>0){
					var lastIndexg = imgSrc.lastIndexOf("/")+1;
					
					var part1 = imgSrc.substring(0,lastIndexg);
					var part2 = imgSrc.substring(lastIndexg,imgSrc.length);
					if(part2.indexOf("un_")<0){
						part2 = "un_"+part2;
					}
					ImgObj.attr("src" , part1+part2);
				}


				CurrentObj.find('table').attr('disabled','true');
				CurrentObj.unbind("mouseover");
				CurrentObj.unbind("mouseout");
				CurrentObj.unbind("mouseup");
				CurrentObj.unbind("mousedown");
				CurrentObj.unbind("click");
						
			}


			/**** 启用该按钮 *****/
			$.fn.ToolsBar_Enabled = function(obj){
				var CurrentObj = $("#"+obj);
				if(CurrentObj.attr("IsDiabled")=="false"){
					return;
				}
				CurrentObj.attr("IsDiabled" , "false");
				var	Ts_Id= CurrentObj[0].id;
				var Ts_Js= CurrentObj.attr("Ts_Js");
				CurrentObj.find('table').attr('disabled','');
				var ImgObj = $("#"+Ts_Id+"_img");
				var imgSrc = ImgObj.attr("src");
				if(imgSrc!=null && imgSrc.length>0){
					var lastIndexg = imgSrc.lastIndexOf("/")+1;
					
					var part1 = imgSrc.substring(0,lastIndexg);
					var part2 = imgSrc.substring(lastIndexg,imgSrc.length);
					if(part2.indexOf("un_")>=0){
						part2 = imgSrc.substring(lastIndexg+3 , imgSrc.length);
					}
					ImgObj.attr("src" , part1+part2);
				}
				/**** 绑定鼠标移上去事件 ***/
				CurrentObj.bind('mouseover',function(){
						$("#"+Ts_Id+"_left").addClass("tb_left_up");
						$("#"+Ts_Id+"_center").addClass("tb_center_up");
						$("#"+Ts_Id+"_right").addClass("tb_right_up");
						
				});

				/**** 绑定鼠标移开事件 ***/
				CurrentObj.bind('mouseout',function(){
						$("#"+Ts_Id+"_left").removeClass("tb_left_up").removeClass("tb_left_down");
						$("#"+Ts_Id+"_center").removeClass("tb_center_up").removeClass("tb_center_down");
						$("#"+Ts_Id+"_right").removeClass("tb_right_up").removeClass("tb_right_down");
					});

				/***** 绑定松开事件 ****/
				CurrentObj.bind('mouseup',function(){
						$("#"+Ts_Id+"_left").addClass("tb_left_up").removeClass("tb_left_down");
						$("#"+Ts_Id+"_center").addClass("tb_center_up").removeClass("tb_center_down");
						$("#"+Ts_Id+"_right").addClass("tb_right_up").removeClass("tb_right_down");
					});
				
				/*** 绑定按下事件 ****/
				CurrentObj.bind('mousedown',function(){
						$("#"+Ts_Id+"_left").addClass("tb_left_down").removeClass("tb_left_up");
						$("#"+Ts_Id+"_center").addClass("tb_center_down").removeClass("tb_center_up");
						$("#"+Ts_Id+"_right").addClass("tb_right_down").removeClass("tb_right_up");
					});

				/***** 绑定单击事件 ****/
				CurrentObj.bind("click" , function(){
					if(Ts_Js!=null && Ts_Js.length>0){
						jQuery.globalEval(Ts_Js);
					}
				});
						
			}

	})(jQuery);
}