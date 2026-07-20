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
<HTML>
	<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />

		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<TITLE>充值金额</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="wsj_phone/css/system.css" />
		<link rel="stylesheet" type="text/css" href="wsj_phone/css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
			function myload(){
				var parent_window = window.opener;
				var p = parent_window.document.getElementById("money_p");
				if(p!=undefined){
					var append_p = reload_html();
					p.innerHTML = append_p;
					window.close();
				}
			}
			
			function reload_html(){
				var orderid = <s:property value="orderid" />;
				var html = "";
				var balance;
				var sumpeice ;
				/**
				<s:if test="balance< order.sumpeice">您的余额不足请<a'+
								'target="_blank" href="order_chongzhi.action"><IMG'+
									'src="images/shopping/pic_14.gif">'+
							'</a>'+
						'</s:if> <s:else>'+
							'<a'+
								'href="userPay.action?order.id=<s:property value="order.id" />"><IMG'+
									'src="images/shopping/pic_05.gif">'+
							'</a>'+
						'</s:else>
				*/
				$.ajax({
				  type: 'POST',
				  url: "reload_money.action",
				  data: {orderid:orderid},
				  async:false,//同步
				  success: function(data){
			  		data = eval("("+data+")");
			  		balance = data.balance;
			  		sumpeice = data.sumpeice;
			  		if(balance!="" && sumpeice!=""){
			  			balance = parseFloat(balance);
			  			sumpeice = parseFloat(sumpeice);
			  			html += '<SPAN> <EM>我的余额：￥'+balance+''+
							'</EM>元,实际应支付<EM>'+sumpeice+''+
							'</EM>元。 ';
						if(balance<sumpeice){
							html += '您的余额不足请<a target="_blank" href="order_chongzhi.action"><IMG src="images/shopping/pic_14.gif"></a>';
						}else{
							html += '<a href="userPay.action?order.id='+orderid+'"><IMG src="images/shopping/pic_05.gif"></a>';
						}
						html += '</SPAN>';
			  		}
				  }
				});
				return html;
			}
		</script>
	</HEAD>
	
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>

		<ul class="nav">
			<li>
				
			</li>
		</ul>

		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center; width:320px;background-color: #CCC;">
		充值成功!
		</div>
	
	</body>
</HTML>
										   