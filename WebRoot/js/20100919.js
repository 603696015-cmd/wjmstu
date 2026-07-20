jQuery.ajax({
    type: "POST",
    url: "/Service/Ajax.aspx",
    data: "t=getuserinfo",
    success: function(msg) {
        eval('var u = ' + msg);
        if (u.uid > 0) {
            jQuery('#TopUserInfo').html("您好：" + jQuery.trim(u.logName) + "！ | <a href=\"/Logout.aspx\" title=\"退出登录\">退出登录</a>");

            jQuery.ajax({
                type: "POST",
                url: "/Service/Ajax.aspx",
                data: "t=shoppingcart",
                success: function(msg) {
                    eval('var cart = ' + msg);
                    if (cart) {
                        var TotalCount, TotalWeigth;
                        TotalCount = 0;
                        TotalWeigth = 0;
                        for (var i = 0; i < cart.length; i++) {
                            TotalCount++;
                            TotalWeigth += Math.round(parseFloat(cart[i].Price) * 100) / 100;
                        }
                        TotalWeigth = roundNumber(TotalWeigth, 2);
                        jQuery('#SpanShoppingCartQuantity').html(TotalCount.toString());
                        jQuery('#SpanShoppingCartSubtotal').html(TotalWeigth.toString());
                    }
                    else {
                        jQuery('#SpanShoppingCartQuantity').html('0');
                        jQuery('#SpanShoppingCartSubtotal').html('0');
                    }
                }
            });
        }
        else {
            //            var url = '';
            //            var host = window.location.host + window.location.pathname;
            //            if ($("form") != null) {
            //                url = $("form")[0].action;
            //                if (url == "" || typeof (url) == undefined || url == "undefined")
            //                    url = jQuery.url.attr("path");
            //            }
            //            else {
            //                url = jQuery.url.attr("path");
            //            }
            //            url = url.substring(url.indexOf("?"));
            //            if (url.indexOf('/') != 0) {
            //                if (host != '')
            //                    url = 'http://' + host.indexLastOf + url;
            //            }
            //通过ReturnUrl=将字符截取成数组
            var returnUrl = window.location.href.split('ReturnUrl=');
            //删除并返回数组的最后一个元素
            var a = returnUrl.pop();    
            jQuery('#TopUserInfo').html("您好，请 <a href=\"/member/Login.aspx?ReturnUrl=" + a + "\" target=\"_blank\">登录</a> 或 <a href=\"/member/regist.aspx\" target=\"_blank\">注册</a>");
            jQuery('#SpanShoppingCartQuantity').html('0');
            jQuery('#SpanShoppingCartSubtotal').html('0.00');
        }
    }
}); 
