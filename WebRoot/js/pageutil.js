function getPageDiv(cnt,pn,ps){
var page_div=$("<div>");
			var count = cnt;
			var pageNow = pn;
			var pageSize = ps;
			var pageCount = 0;
			if(pageSize==0) pageSize=10;
			if (count % pageSize == 0) {
				pageCount = parseInt(count / pageSize);
			} else {
				pageCount = parseInt(count / pageSize) + 1;
			}
			var xx="";
			if (pageNow > 0) {
				xx+=("<a style='cursor: hand' href='javascript:seachOnEroomPage("
						+ (0) + ")'>[首页]</a>");
				xx+=("<a style='cursor: hand' href='javascript:seachOnEroomPage("
						+ (pageNow - 1) + ")'>[上一页]</a>");
			} else {
				xx+=("[首页]");
				xx+=("[上一页]");
			}
			if (pageCount > 0) {
				xx+=("<select  onchange='seachOnEroomPage(this.options[this.selectedIndex].value)'>");
				for (var i = 0; i < pageCount; i++) {
					if(pageNow==i)
					xx+=("<option value='" + i + "' selected='selected'>" + (i + 1)
							+ "</option>");
					else{
						xx+=("<option value='" + i + "'>" + (i + 1)
								+ "</option>");
					}

				}
				xx+=("</select> ");
			}
			if (pageNow < pageCount - 1) {
				xx+=("<a style='cursor: hand' href='javascript:seachOnEroomPage("
						+ (pageNow + 1) + ")'>[下一页]</a>");
				xx+=("<a style='cursor: hand' href='javascript:seachOnEroomPage("
						+ (pageCount - 1) + ")'>[末页]</a>");
			} else {
				xx+=("[下一页]");
				xx+=("[末页]");
			}
			xx+=("<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>共</b>" + count
					+ "<b>条</b></span>");
				$(page_div).html(xx)	;
	//alert($(page_div).html());
	return page_div;
}

function getClassPageDiv(cnt,pn,ps){
			var page_div=$("<div>");
			var count = cnt;
			var pageNow = pn;
			var pageSize = ps;
			var pageCount = 0;
			if(pageSize==0) pageSize=10;
			if (count % pageSize == 0) {
				pageCount = parseInt(count / pageSize);
			} else {
				pageCount = parseInt(count / pageSize) + 1;
			}
			var xx="";
			if (pageNow > 0) {
				xx+=("<a style='cursor: hand' href='javascript:seachOnClassPage("
						+ (0) + ")'>[首页]</a>");
				xx+=("<a style='cursor: hand' href='javascript:seachOnClassPage("
						+ (pageNow - 1) + ")'>[上一页]</a>");
			} else {
				xx+=("[首页]");
				xx+=("[上一页]");
			}
			if (pageCount > 0) {
				xx+=("<select  onchange='seachOnClassPage(this.options[this.selectedIndex].value)'>");
				for (var i = 0; i < pageCount; i++) {
					if(pageNow==i)
					xx+=("<option value='" + i + "' selected='selected'>" + (i + 1)
							+ "</option>");
					else{
						xx+=("<option value='" + i + "'>" + (i + 1)
								+ "</option>");
					}

				}
				xx+=("</select> ");
			}
			if (pageNow < pageCount - 1) {
				xx+=("<a style='cursor: hand' href='javascript:seachOnClassPage("
						+ (pageNow + 1) + ")'>[下一页]</a>");
				xx+=("<a style='cursor: hand' href='javascript:seachOnClassPage("
						+ (pageCount - 1) + ")'>[末页]</a>");
			} else {
				xx+=("[下一页]");
				xx+=("[末页]");
			}
			xx+=("<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>共</b>" + count
					+ "<b>条</b></span>");
				$(page_div).html(xx)	;
	//alert($(page_div).html());
	return page_div;
}