var PageIndex, tableBuilder;
var PageSize;
var FldCount;
PageIndex = 1;
PageSize = 6;
function GetProdReview(prod_id) {
    ProdReviewWebService.GetProdReviewMaxNum(prod_id, onGetProdReviewMaxNum,onGetProdReviewMaxNumFailed, prod_id);
}
function onGetProdReviewMaxNum(result, content) {
    FldCount = result;
    $get("divprodreview").innerHTML = "<img src=\"/_support/images/ajax-loader1.gif\" alt=\"\" />";
    ProdReviewWebService.GetProdreviewList(PageSize, PageIndex, content, onGetProdreviewSuccessed, null, content);
}
function onGetProdReviewMaxNumFailed(err)
{

}
function onGetProdreviewSuccessed(result,v) {
    if (result) {
        var RewId = 0;
        tableBuilder = new Sys.StringBuilder(" ");
        var url = gettype(v);
        for (var i = 0; i < result.length; i++) {
            RewId = result[0].prodreview_id;
            var str = result[i];
            var time = str.posttime;
            time = time.getFullYear().toString() + "-" + (time.getMonth() + 1).toString() + "-" + time.getDate().toString();
            tableBuilder.append(" <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
            tableBuilder.append("<tr><td>");
            if (str.SpeakGenre == "2") {
                tableBuilder.append("<span class=\"home_12\">[经验]</span><img src=\"http://images.hzins.com/web/a200.gif\" align=\"absmiddle\" />");
            }
            else if (str.SpeakGenre == "1") {
                tableBuilder.append("<span class=\"pr8\">[询问]</span>");
            }
            else {
                tableBuilder.append("<span class=\"pr9\">[讨论]</span>");
            }
            var rowString;

            if (str.SpeakGenre == "2") {
                rowString = String.format("{0}</td></tr> <tr><td class=\"rq\">用户：<span id=\"splogname{1}\"></span> 时间：{2} <img src=\"http://www.hzins.com/images/bar/s{3}.gif\"/></td></tr><tr> <td><b>[回复]</b>{4}</td></tr>", str.prod_content, str.prodreview_id, time, str.star, str.RestoreContent);
            }
            else {
                rowString = String.format("{0}</td></tr> <tr><td class=\"rq\">用户：<span id=\"splogname{1}\"></span> 时间：{2} </td></tr><tr> <td><b>[回复]</b>{3}</td></tr>", str.prod_content, str.prodreview_id, time, str.RestoreContent);
            }

            tableBuilder.append(rowString);
            tableBuilder.append("<tr><td height=\"20\"><hr size=\"1\" style=\"color:#E7A29B\"/></td></tr></table>");
            ProdReviewWebService.GetUsers(str.users_id, onGetUsersSucceeded, null, "splogname" + str.prodreview_id);
        }
        $get("divprodreview").innerHTML = tableBuilder.toString();
        if (FldCount == 0) {
            $get("divprodreview").innerHTML = "<p style='text-indent: 18px'>该产品暂时没有评论!您可以在此发表“讨论”与“询问”类型的评论，分享您的看法或获取客服的帮助。注意哦~~ 您每天留言不能超过10条。每投保一次产品，在3日内，就可以给该产品发表一条“经验”类型的评论，您可以把您的投保感言告诉其他的朋友。<br />&nbsp;&nbsp;发表“经验”评论就送金豆10个，价值人民币1元。<br />&nbsp;&nbsp;发表“讨论”与“询问”类型评论就送积分20点。<br />&nbsp;&nbsp;注：慧择网的产品评论旨在让客户提出对产品的保障、心得体验等等和产品本身息息相关的内容，不允许在产品评论中出现与产品无关的冗余信息。当涉及广告、比价、重复反馈、不实评论、恶意评论、粗口、危害国家安全等等不当言论时，慧择网有权予以管理。<br />&nbsp;&nbsp;您的评论将会接受客服人员审核，因此在您可能要在发布了评价后稍晚的时间才能看到评论。</p>";
        }
        var html = new Sys.StringBuilder("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
        html.append("<tr>");
        html.append(" <td width=\"64%\" height=\"21\" class=\"pr7\">共 " + FldCount + " 条 <a href=\"/product/prodreview/showlist-" + RewId + "-1.html\" class=\"pr6\" target=\"_blank\">更多评论</a></td><td width=\"36%\"><a href=\"" + url + "\" target=\"_blank\"  rel=\"nofollow\"><img src=\"http://images.hzins.com/web/a199.gif\" border=\"0\" align=\"absmiddle\" /></a></td>");
        html.append("</tr>");
        html.append("</table>");
        $get("divpage").innerHTML = html.toString();
    }
    else {
        //    <span onclick=\"GoPrePage()\" class=\"pr6\" style=\"cursor:hand\">上一页</span> <span onclick=\"GoNextPage()\" style=\"cursor:hand\">下一页</span>
    }
}
function onGetUsersSucceeded(result, str) {
    if (result != null && result.LogName!="") {
//        if (result.LogName.indexOf('@') != -1) {
//            var tmp;
//            tmp = result.LogName.split('@');
//            $get(str).innerHTML =tmp[0]+ '@***';
//        }
//        else {
            $get(str).innerHTML = result.LogName;
       // }
    }
    else {
        $get(str).innerHTML = "匿名";
    }
}
function GoPrePage() {
    if (PageIndex > 1) {
        PageIndex--;
    }
    GetProdReview();
}
function GoNextPage() {
    PageIndex++;

    GetProdReview();

}

function gettype(v) {
    var type = v.substring(0, 4);
    var _id = v.substring(4, v.length);
    var url = '';
    if (type == "0100") {

        url = "/product/accid/review-" + _id + ".html";

    }
    if (type == "0200") {

        url = "/product/travel/review-" + _id + ".html";

    }
    if (type == "0300") {

        url = "/product/health/review-" + _id + ".html";

    }
    if (type == "0400") {

        url = "/product/annu/review-" + _id + ".html";

    }
    if (type == "0600") {

        url = "/product/kid/review-" + _id + ".html";
    }
    if (type == "0700") {

        url = "/product/life/review-" + _id + ".html";

    }
    if (type == "0800") {

        url = "/product/invest/review-" + _id + ".html";
    }

    if (type == "0900") {

        url = "/product/home/review-" + _id + ".html";
    }
    return url;
}