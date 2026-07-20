function debug(info) {
//  alert(info);
}

function QueryString() { // 构造参数对象并初始化
  var name, value;
  var str = location.href; // 获得浏览器地址栏URL串
  var num = str.indexOf("?");
  str = str.substr(num + 1); // 截取“?”后面的参数串
  var arrtmp = str.split("&"); // 将各参数分离形成参数数组

  for (var i = 0; i < arrtmp.length; i++) {
    var x = arrtmp[i];
    num = x.indexOf("=");
    if (num > 0) {
      name = x.substring(0, num); // 取得参数名称
      value = x.substr(num + 1); // 取得参数值
      this[name] = value; // 定义对象属性并初始化
    }
  }
}


var UrlUtil = {
    getParaInfo : function(url, key) {
        var realKey = "&"+key+"=";
        var start = url.indexOf(realKey);
        if (start<0) return "";
        start += realKey.length;
        var end = url.indexOf("&", start);
        if (end<start) end = url.length;

        return url.substring(start, end);
    },
    urlForFirefox : function(url) {
        if (navigator.userAgent.indexOf("Firefox") > 0) {
            url = url.replace(/&amp;/gi, "&");
        }
        return url;
    },
    getArgsFromHref : function(sHref, sArgName) {
        var args  = sHref.split("?");
        var retval = "";

        if(args[0] == sHref) {/*参数为空*/
             return retval; /*无需做任何处理*/
        }
        var str = args[1];
        args = str.split("&");
        for(var i = 0; i < args.length; i ++) {
            str = args[i];
            var arg = str.split("=");
            if(arg.length <= 1) continue;
            if(arg[0] == sArgName) {
                retval = arg[1];
                break;
            }
        }
        return retval;
    }
};


// 在父窗口找指定名字的frame并返回；
// 如果找不到则返回null
function fGetFrame(frmName) {
    var p = window;
    for (var i = 0; p && !p[frmName] && i < 10; i++) {
        p = p.parent;
    }
    // 为适用于新开窗口而添加
    if (!p || !p[frmName]) {
        p = opener;
      for (var i = 0; p && !p[frmName] && i < 10; i++) {
          p = p.parent;
      }
    }
    if (!p || !p[frmName]) return null;
    return p[frmName];
}

function fGetArgsFromHref(sHref, sArgName) {
    var args  = sHref.split("?");
    var retval = "";

    if(args[0] == sHref) {/*参数为空*/
         return retval; /*无需做任何处理*/
    }
    var str = args[1];
    args = str.split("&");
    for(var i = 0; i < args.length; i ++) {
        str = args[i];
        var arg = str.split("=");
        if(arg.length <= 1) continue;
        if(arg[0] == sArgName) {
            retval = arg[1];
            break;
        }
    }
    return retval;
}

