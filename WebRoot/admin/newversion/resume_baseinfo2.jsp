<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>简历管理_我的智联_智联招聘</title>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
    <link type="text/css" rel="stylesheet" href="http://my.zhaopin.com/css/new_v4/myzhaopin.css">
    <link type="text/css" rel="stylesheet" href="http://my.zhaopin.com/css/new_v4/subnav_resumestep2.css">
    <link type="text/css" rel="stylesheet" href="http://my.zhaopin.com/css/New_v3/myresume_date.css">
    <link type="text/css" rel="stylesheet" href="http://my.zhaopin.com/css/New_v3/resume_add.css">
    <script type="text/javascript" src="http://my.zhaopin.com/js/function.js"></script>
    <script type="text/javascript" src="http://my.zhaopin.com/js/utilScript.js"></script>
    <script type="text/javascript" src="http://img01.zhaopin.cn/2012/js/arrdata.js"></script>
    <script type="text/javascript" src="http://my.zhaopin.com/js/myresume_popupdiv.js"></script>
    <script type="text/javascript" src="http://my.zhaopin.com/js/New_v3/myresume_util.js"></script>
    <script type="text/javascript" src="http://my.zhaopin.com/js/New_v4/myresume_date.js"></script>
    <script type="text/javascript" src="http://my.zhaopin.com/js/new_v3/formValidator.js"></script>
    <script type="text/javascript" src="http://my.zhaopin.com/js/New_v3/ajaxbase.js"></script>
    <script type="text/javascript" src="js/calendar.js"></script>
    <style>
        .formError
        {
            background: url(http://images.zhaopin.com/new4/article2/images/err_ico.jpg) no-repeat 10px 5px #FFE8EE;
            border: 1px solid #FF8F84;
            padding-left: 30px;
            font-size: 12px;
            line-height: 25px;
            color: #676664;
            width: 130px;
        }
        .cuowu1
        {
            background: url(http://images.zhaopin.com/new4/article2/images/err_ico.jpg) no-repeat 10px 5px #FFE8EE;
            border: 1px solid #FF8F84;
            padding-left: 30px;
            font-size: 12px;
            line-height: 25px;
            color: #676664;
            width: 400px;
        }
        .cuowu2
        {
            background: url(http://images.zhaopin.com/new4/article2/images/err_ico.jpg) no-repeat 10px 5px #FFE8EE;
            border: 1px solid #FF8F84;
            padding-left: 30px;
            font-size: 12px;
            line-height: 25px;
            color: #676664;
            width: 180px;
        }
        .classFormEle_error
        {
            background: none repeat scroll 0 0 #fff;
            border: solid 1px #FF8E84;
        }
        .table1_found .tab1Td2
        {
            line-height: 20px;
            padding-top: 10px;
            text-align: center;
        }
        .tishi
        {
            font-size: 14px;
            color: #FFF;
            font-weight: bold;
            width: 450px;
            line-height: 40px;
            background: url(http://images.zhaopin.com/new4/article2/images/tishi_ico.jpg) no-repeat 15px 5px #CF0505;
            padding-left: 10px;
            margin: 0 auto;
            display: none;
        }
    </style>
    <script language="javascript" type="text/javascript">
<!--
        String.prototype.trim = function () {

            return this.replace(/(^\s*)|(\s*$)/g, "");

        }

        function closeMoreItems() {
            var div = document.getElementById('popupDiv_moreItems');
            div.style.visibility = 'hidden';
            if (div.shim) div.shim.style.visibility = 'hidden';
            if (docMask && docMask.nodeType == 1 && docMask.style.visibility != 'hidden') docMask.style.visibility = 'hidden';
            if (arrSel4ie6.length) {
                for (var i in arrSel4ie6) arrSel4ie6[i].disabled = false;
            }
        }
        var arrSel4ie6 = new Array();
        var docMask;
        function popupMoreItems() {
            function getDocumentWH() {
                var d = { w: 0, h: 0 };
                if (window.innerHeight && window.scrollMaxY) d.h = window.innerHeight + window.scrollMaxY;
                else if (document.body.scrollHeight > document.body.offsetHeight) d.h = document.body.scrollHeight;
                else d.h = document.body.offsetHeight + document.body.offsetTop;
                if (window.innerWidth && window.scrollMaxX) d.w = window.innerWidth + window.scrollMaxX;
                else if (document.body.scrollWidth > document.body.offsetWidth) d.w = document.body.scrollWidth;
                else d.w = document.body.offsetWidth + document.body.offsetLeft;
                return d;
            }
            function getWindowWH() {
                var pointer = { w: 0, h: 0 };
                if (typeof (window.innerWidth) == 'number') {//Non-IE
                    pointer.w = window.innerWidth;
                    pointer.h = window.innerHeight;
                }
                else if (document.documentElement && (document.documentElement.clientWidth || document.documentElement.clientHeight)) {//IE 6+ in 'standards compliant mode'
                    pointer.w = document.documentElement.clientWidth;
                    pointer.h = document.documentElement.clientHeight;
                }
                else if (document.body && (document.body.clientWidth || document.body.clientHeight)) {//IE 4 compatible
                    pointer.w = document.body.clientWidth;
                    pointer.h = document.body.clientHeight;
                }
                return pointer;
            }

            var div = document.getElementById('popupDiv_moreItems');
            if (div.currentStyle && navigator.userAgent.indexOf("MSIE 7") == -1) {//ie6
                var frame = document.createElement('iframe');
                frame.src = "javascript:''"
                frame.frameBorder = "0";
                frame.scrolling = "no";
                frame.className = "iframeShim";
                frame.style.zIndex = div.currentStyle.zIndex - 1;
                frame.style.width = parseFloat(div.offsetWidth) + 'px';
                frame.style.height = parseFloat(div.offsetHeight) + 'px';
                frame.style.visibility = 'hidden';
                frame.style.position = 'absolute';
                document.body.appendChild(frame);
                div.shim = frame;
                var selectall = document.getElementsByTagName("select");
                arrSel4ie6 = new Array();
                for (var i = 0; selectall[i]; i++) {
                    selectall[i].disabled = true;
                    arrSel4ie6.push(selectall[i]);
                }
            }
            if (!docMask.nodeType || docMask.nodeType != 1) {
                docMask = document.createElement('div');
                docMask.className = 'divMask';
                if (typeof (docMask.style.filter) != 'undefined') docMask.style.filter = "progid:DXImageTransform.Microsoft.Alpha(Opacity=70)";
                else docMask.style.MozOpacity = 0.7;
                document.body.appendChild(docMask);
                var dWH = getDocumentWH();
                docMask.style.width = dWH.w + 'px';
                docMask.style.height = dWH.h + 'px';
            }
            docMask.style.visibility = 'visible';
            var wWH = getWindowWH();
            var scrollTop = document.documentElement.scrollTop || document.body.scrollTop, scrollLeft = document.documentElement.scrollLeft || document.body.scrollLeft;
            div.style.top = (wWH.h - parseFloat(div.offsetHeight)) / 2 + scrollTop + 'px';
            div.style.left = (wWH.w - parseFloat(div.offsetWidth)) / 2 + scrollLeft + 'px';
            div.style.visibility = 'visible';
            if (div.shim) {
                div.shim.style.top = (wWH.h - parseFloat(div.offsetHeight)) / 2 + scrollTop + 'px';
                div.shim.style.left = (wWH.w - parseFloat(div.offsetWidth)) / 2 + scrollLeft + 'px';
                div.shim.style.visibility = 'visible';
            }
        }
        function f() {
            var f = document.frmMain, p = f.project, m = f.advamang;
            p.checked = false;
            m.checked = false;
            document.frmMain.locationurl.value = 'resume_finished';
            if (arrSel4ie6.length) {
                for (var i in arrSel4ie6) arrSel4ie6[i].disabled = false;
            }
            document.frmMain.submit();
            document.frmMain.confirm1.disabled = true;
            document.frmMain.confirm2.disabled = true;
        }
        function ff() {
            var f = document.frmMain, p = f.project, m = f.advamang;
            if (p.checked && m.checked) document.frmMain.locationurl.value = 'resume_baseinfo3';
            else if (p.checked && !m.checked) document.frmMain.locationurl.value = 'resume_baseinfo3';
            else if (!p.checked && m.checked) document.frmMain.locationurl.value = 'resume_baseinfo3';
            else document.frmMain.locationurl.value = 'resume_finished';
            if (arrSel4ie6.length) {
                for (var i in arrSel4ie6) arrSel4ie6[i].disabled = false;
            }
            document.frmMain.submit();
            document.frmMain.confirm1.disabled = true;
            document.frmMain.confirm2.disabled = true;
        }
        function ajaxReturn(success, responseText) {
            if (success) {
                if (responseText != '') {
                    var expi = new Date(new Date().getTime() + (1000 * 365 * 24 * 60 * 60));
                    document.cookie = 'bbll=' + escape(responseText) + '; expires=' + expi.toGMTString() + '; path=/' + '; domain=zhaopin.com';
                }
            }
            else {// something went wrong with the AJAX callback
            }
        }
//-->
    </script>
    <script language="javascript" type="text/javascript">
<!--
        //var start_date_y = new formEle(true,'startend','date',['请输入您受教育的开始年月','受教育的开始年月不能晚于结束年月'],null,{sMonth:'document.frmMain.start_date_m',eYear:'document.frmMain.end_date_y',eMonth:'document.frmMain.end_date_m'});
        var start_date_y = new formEle(true, 'date', 'start_date_y', ['请选择开始年份', '开始时间不能晚于结束时间'], null, { pre_condition: 'education_date.start.objectY.div&&MYRESUME.util.Dom.getCurrentStyle(education_date.start.objectY.div,"visibility")=="visible"', ymym: ['start_date_y', 'start_date_m', 'end_date_y', 'end_date_m'] });
        var start_date_m = new formEle(true, 'date', 'start_date_m', ['请选择开始月份', '开始时间不能晚于结束时间'], null, { pre_condition: 'education_date.start.objectM.div&&MYRESUME.util.Dom.getCurrentStyle(education_date.start.objectM.div,"visibility")=="visible"', ymym: ['start_date_y', 'start_date_m', 'end_date_y', 'end_date_m'] });
        var end_date_y = new formEle(true, 'date', 'end_date_y', ['您已选择了结束月份，请选择相应的年份', '开始时间不能晚于结束时间'], null, { condition: 'document.frmMain.end_date_m.value!=""', pre_condition: 'education_date.end.objectY.div&&MYRESUME.util.Dom.getCurrentStyle(education_date.end.objectY.div,"visibility")=="visible"', ymym: ['start_date_y', 'start_date_m', 'end_date_y', 'end_date_m'] });
        var end_date_m = new formEle(true, 'date', 'end_date_m', ['您已选择了结束年份，请选择相应的月份', '开始时间不能晚于结束时间'], null, { condition: 'document.frmMain.end_date_y.value!=""', pre_condition: 'education_date.end.objectM.div&&MYRESUME.util.Dom.getCurrentStyle(education_date.end.objectM.div,"visibility")=="visible"', ymym: ['start_date_y', 'start_date_m', 'end_date_y', 'end_date_m'] });
        var school_name = new formEle(true, 'text', 'schoolname', ['请输入学校名称', '学校名称长度不得超过255'], null, { length: 255 });
        var subMajor = new formEle(true, 'text', 'subMajor', ['请选择专业名称'], null, { o4focus: 'document.getElementById("subMajorF")' });
        var major = new formEle(true, 'text', 'major', ['请输入专业名称', '专业名称长度不得超过255'], null, { length: 255, arrInvaTxt: ['若无合适选项，请在此处填写专业名称'] });
        var degree = new formEle(true, 'select', 'degree', ['请选择您的学历/学位']);

        //var dialect0=new formEle(true,'text','dialect0',['外语语种选择其他时，请输入具体语种'],null,{condition:'document.frmMain.languages0.value=="999"'});


        var cmpany_name = new formEle(true, 'text', 'cmpanyname', ['请输入企业名称']);
        var company_type = new formEle(true, 'select', 'companytype', ['请选择企业性质']);
        var industry = new formEle(true, 'text', 'industry', ['请选择行业类别'], null, { o4focus: 'document.getElementById("button_industryF")' });
        var subJobType = new formEle(true, 'text', 'jobtype', ['请选择职位类别'], null, { o4focus: 'document.getElementById("button_jobtypeF")' });
        var customSubJobtype = new formEle(true, 'text', 'customSubJobtype', ['请填写职位名称']);
        //var workstart_date_y = new formEle(true,'startend','workdate',['请正确输入您工作的开始年月','工作的开始年月不能晚于结束年月'],null,{sMonth:'document.frmMain.workstart_date_m',eYear:'document.frmMain.workend_date_y',eMonth:'document.frmMain.workend_date_m'});
        var workstart_date_y = new formEle(true, 'date', 'workstart_date_y', ['请选择开始年份', '开始时间不能晚于结束时间'], null, { pre_condition: 'work_date.start.objectY.div&&MYRESUME.util.Dom.getCurrentStyle(work_date.start.objectY.div,"visibility")=="visible"', ymym: ['workstart_date_y', 'workstart_date_m', 'workend_date_y', 'workend_date_m'] });
        var workstart_date_m = new formEle(true, 'date', 'workstart_date_m', ['请选择开始月份', '开始时间不能晚于结束时间'], null, { pre_condition: 'work_date.start.objectM.div&&MYRESUME.util.Dom.getCurrentStyle(work_date.start.objectM.div,"visibility")=="visible"', ymym: ['workstart_date_y', 'workstart_date_m', 'workend_date_y', 'workend_date_m'] });
        var workend_date_y = new formEle(true, 'date', 'workend_date_y', ['您已选择了结束月份，请选择相应的年份', '开始时间不能晚于结束时间'], null, { condition: 'document.frmMain.workend_date_m.value!=""', pre_condition: 'work_date.end.objectY.div&&MYRESUME.util.Dom.getCurrentStyle(work_date.end.objectY.div,"visibility")=="visible"', ymym: ['workstart_date_y', 'workstart_date_m', 'workend_date_y', 'workend_date_m'] });
        var workend_date_m = new formEle(true, 'date', 'workend_date_m', ['您已选择了结束年份，请选择相应的月份', '开始时间不能晚于结束时间'], null, { condition: 'document.frmMain.workend_date_y.value!=""', pre_condition: 'work_date.end.objectM.div&&MYRESUME.util.Dom.getCurrentStyle(work_date.end.objectM.div,"visibility")=="visible"', ymym: ['workstart_date_y', 'workstart_date_m', 'workend_date_y', 'workend_date_m'] });
        var salary_scope = new formEle(true, 'select', 'salary', ['请选择职位月薪']);
        var job_description = new formEle(true, 'text', 'description', ['请对您的工作进行描述', '工作描述内容过长'], null, { length: 3000 });
        function GetErrMsgCounte() {
            var arr = ["start_date_y", "start_date_m", "end_date_y", "end_date_m", "schoolname", "subMajor", "major", "degree", "language", "language2", "cmpanyname", "companytype", "industry", "jobtype", "customSubJobtype", "workstart_date_y", "workstart_date_m", "workend_date_y", "workend_date_m", "salary", "description"];
            var errMsgCounter = 0;
            for (var i = 0; i < arr.length; i++) {
                //alert(arr[i]);
                var t = document.getElementById("conError_" + arr[i]);

                //alert(t.style.display);
                if (t != null) {
                    //alert(t.innerHTML);
                    if (t.style.display == '') {
                        errMsgCounter++;
                    }
                }
            }
            //alert(errMsgCounter);
            document.getElementById("errmsgCounter").innerHTML = errMsgCounter;
        }

        function show(oEvent) {
            document.getElementById("tishi").style.display = "block";
            e = window.event || oEvent;
            if (e.stopPropagation) {
                e.stopPropagation();
            } else {
                e.cancelBubble = true;
            }
        }



        function changeLang(objSel) {
            //20080612-lwh-start
            if (objSel.options[objSel.selectedIndex].text == document.frmMain.languages0.options[document.frmMain.languages0.selectedIndex].text && objSel.options[objSel.selectedIndex].text == document.frmMain.languages1.options[document.frmMain.languages1.selectedIndex].text && objSel.options[objSel.selectedIndex].text != "无" && objSel.options[objSel.selectedIndex].text != "其他") {
                objSel.selectedIndex = 0;
                if (objSel.name == "languages0") {

                    document.getElementById("languages0tbody").style.display = "";
                }
                else if (objSel.name == "languages1") {

                    document.getElementById("languages1tbody").style.display = "";
                }
            }
            else {
                document.getElementById("languages0tbody").style.display = "none";
                document.getElementById("languages1tbody").style.display = "none";

            }
            //20080612-lwh-end

            var l = 'languages'.length;
            var index = objSel.name.toString().substring(l, objSel.name.toString().length);
            var readSel = eval('document.frmMain.read' + index);
            var spokenSel = eval('document.frmMain.spoken' + index);
            var dialectText = eval('document.frmMain.dialect' + index);
            var spanForeign = document.getElementById('foreignLan' + index);
            var spanDialect = document.getElementById('dialectLan' + index);
            if (objSel.value == '') {
                readSel.disabled = true;
                spokenSel.disabled = true;
            }
            else {
                readSel.disabled = false;
                spokenSel.disabled = false;
            }
            if (objSel.value == '999') {
                spanForeign.style.display = 'none';
                spanDialect.style.display = '';
            }
            else {
                spanForeign.style.display = '';
                spanDialect.style.display = 'none';
            }
            //readSel.selectedIndex=0;
            //spokenSel.selectedIndex=0;
            //dialectText.value='';
        }

        function fnOnload() {
            changeLang(document.frmMain.languages0);
            changeLang(document.frmMain.languages1);
        }
        window.onload = fnOnload;

      
        function goto(where) {
            if (document.frmMain.job_description.value == '请详细描述您所负责的具体工作内容、业绩的达成情况和掌握的资源、客户等。') {
                document.frmMain.job_description.value = '';
            }
            delInvisiChar(document.frmMain.job_description);
            //20080616-lwh-start
            document.getElementById("languages0tbody").style.display = "none";
            document.getElementById("languages1tbody").style.display = "none";
            var dialect0 = document.frmMain.dialect0.value.trim();
            var dialect1 = document.frmMain.dialect1.value.trim();
            var languages0 = document.frmMain.languages0.options[document.frmMain.languages0.selectedIndex].text;
            var languages1 = document.frmMain.languages0.options[document.frmMain.languages1.selectedIndex].text;
            if (languages0 != "其他") {
                dialect0 = "";
            }
            if (languages1 != "其他") {
                dialect1 = "";
            }
            if (languages0 == "无" || languages1 == "无") {
            }
            else if (languages0 == "其他" && languages1 == "其他" && dialect0 != dialect1) {
            }
            else if ((dialect0 == languages1 || dialect1 == languages0 || dialect0 == dialect1) && (dialect0 != "" || dialect1 != "")) {
                document.getElementById("languages0tbody").style.display = "";
                document.getElementById("languages1tbody").style.display = "";
                return;
            }
            //20080616-lwh-end
            /**20131220**/
            if(isopen == true && isNewUser == "true" && $("#sell-resumequstion").css("display") == "block"){
                sellDescriptionfn();
            }
            /**20131220**/
            if (!document.frmMain.checkForm()) {
                GetErrMsgCounte();
                show();
                return;
            }
            var f = document.frmMain, p = f.project, m = f.advamang;
            switch (where) {
                case 'addExp': document.frmMain.locationurl.value = 'resume_baseinfo_work';
                    document.frmMain.submit(); document.frmMain.newexp.disabled = true; document.frmMain.next.disabled = true; break;
                case 'nextStep': popupMoreItems();
                    //if(p.checked && m.checked) document.frmMain.locationurl.value='resume_baseinfo3';
                    // else if(p.checked && !m.checked) document.frmMain.locationurl.value='resume_baseinfo3';
                    // else if(!p.checked && m.checked) document.frmMain.locationurl.value='resume_baseinfo3';
                    //  else document.frmMain.locationurl.value='resume_finished';
                    //  document.frmMain.submit();break;
                    //default : document.frmMain.locationurl.value ='resume_finished';
                    //document.frmMain.submit(); break;
            }
        }



        function addLanguage() {
            document.getElementById('language_item2').style.display = '';
            document.getElementById('addLanA').style.display = 'none';
        }
        String.prototype.Trimall = function () { return this.replace(/\s/g, ""); }
        function calWordNumRemained(max, o, show1, show2, inputname) {
            if (o) {
                show1.style.display = "inline";
                var minlen = o.value.Trimall().length;
                var l = o.value.length;
                var r = parseInt(max - l);
                if (show1 && show2) {
                    show1.innerHTML = '已输入' + l + '个字';
                    show1.className = 'org12';
                    show2.innerHTML = "，最多可输入" + max + "个字";
                    document.getElementById(inputname + "Img").style.display = "inline";
                    document.getElementById(inputname + "Img").style.marginLeft = "6px";
                    document.getElementById(inputname + "Div").innerHTML = '总共输入了' + l + '个字（其中可见字数为' + minlen + "个，不可见字数为" + (l - minlen) + "个）"

                }
            }
        }
        function iniWordNum(show1, show2, txt, inputname) {
            if (show1 && show2) {
                show1.style.display = "none";
                document.getElementById(inputname + "Img").style.display = "none";
                show2.innerHTML = txt;
            }
        }

        // Task No: 81589 --modify by Andy.lu
        function bodyload() {
            document.body.onclick = function () {
                document.getElementById("tishi").style.display = "none";
            };
        }
        // Task No: 81589 --modify by Andy.lu
//-->
    </script>
    <script language="javascript" type="text/javascript" src="http://my.zhaopin.com/js/analytics.js"></script>
    <script type="text/javascript" src="http://img01.zhaopin.cn/2012/js/za/ga.js"></script>

</head>
<!-- Task No: 81589 --modify by Andy.lu-->
<body onload="bodyload()">
    <!-- Task No: 81589 --modify by Andy.lu-->
    <!-- popupDiv eg -->
    <div id="eg1" class="popupDiv" style="width: 350px; visibility: hidden; position: absolute;
        top: -100px; left: -100px; z-index: 99;" onclick="event.cancelBubble=true;">
        <div class="topLeft">
            <img src="" width="1" height="1"></div>
        <div class="topCenterWhite" style="width: 334px;">
            <img src="" width="1" height="1"></div>
        <div class="topRight">
            <img src="" width="1" height="1"></div>
        <div class="content">
            <div style="padding: 0 10px 5px 10px; line-height: 150%;">
                该公司为海外著名网络技术公司驻华办事处。任职期间参与制定公司发展战略和目标，组织策划并实施了人力资源管理体系，健全了各项规章制度，加大员工本土化进程，改革薪酬福利制度，完善了人力资源相关业务过程（包括工作分析、招聘、培训、绩效、薪资等），并参与完成ERP系统改进工作。</div>
        </div>
        <div class="bottomLeft">
            <img src="" width="1" height="1"></div>
        <div class="bottomCenter" style="width: 334px;">
            <img src="" width="1" height="1"></div>
        <div class="bottomRight">
            <img src="" width="1" height="1"></div>
    </div>
    <div id="eg2" class="popupDiv" style="width: 300px; visibility: hidden; position: absolute;
        top: -100px; left: -100px; z-index: 99;" onclick="event.cancelBubble=true;">
        <div class="topLeft">
            <img src="" width="1" height="1"></div>
        <div class="topCenterWhite" style="width: 284px;">
            <img src="" width="1" height="1"></div>
        <div class="topRight">
            <img src="" width="1" height="1"></div>
        <div class="content">
            <div style="padding: 0 10px 5px 10px; line-height: 150%;">
                根据公司的近期和远期目标、财务预算，制定销售计划、制定和审核销售预算，提出产品价格政策；根据同类其他产品的市场动态，销售动态、存在问题、市场竞争发展状况等实施分析汇总，并提出改进方案和措施，协同销售计划的顺利完成；保持与客户的良好关系，维护客户管理，定期组织市场调研、分析市场动向、特点和发展趋势。于2006年成功拓展市场，实现年销售额600万的产品销售业绩。</div>
        </div>
        <div class="bottomLeft">
            <img src="" width="1" height="1"></div>
        <div class="bottomCenter" style="width: 284px;">
            <img src="" width="1" height="1"></div>
        <div class="bottomRight">
            <img src="" width="1" height="1"></div>
    </div>
    <div id="eg3" class="popupDiv" style="width: 250px; visibility: hidden; position: absolute;
        top: -100px; left: -100px; z-index: 99;" onclick="event.cancelBubble=true;">
        <div class="topLeft">
            <img src="" width="1" height="1"></div>
        <div class="topCenterWhite" style="width: 234px;">
            <img src="" width="1" height="1"></div>
        <div class="topRight">
            <img src="" width="1" height="1"></div>
        <div class="content">
            <div style="padding: 0 10px 5px 10px; line-height: 150%;">
                <div>
                    <b>字数统计</b></div>
                <div id="eg3Div">
                </div>
                <br>
            </div>
        </div>
        <div class="bottomLeft">
            <img src="" width="1" height="1"></div>
        <div class="bottomCenter" style="width: 234px;">
            <img src="" width="1" height="1"></div>
        <div class="bottomRight">
            <img src="" width="1" height="1"></div>
    </div>
    <!-- end popupDiv eg -->
    <!-- head -->
<style>
#hd2011hd {
	background:url(http://images.zhaopin.com/new2011/head/images/spri.gif) 0 -111px repeat-x;
	height:63px;
}
#hd2011hdmain, #hd2011wrapper {
	width:990px;
	margin:0 auto;
}

#hd2011logo {
	background:url(http://images.zhaopin.com/new2011/head/images/spri.gif) no-repeat;
	width:177px; height:56px;
	float:left;margin-left: 28px;_margin-left:14px;
}
#hd2011logo a {
	display:block;
	width:158px; height:56px;
}
</style>
<div id="hd2011hd">
	<div id="hd2011hdmain">
		<div id="hd2011logo"><a href="http://www.zhaopin.com/"></a></div>
		<img width="202" height="16" style="margin: 19px 0px 0px 26px; float:left" alt="" src="http://myimg.zhaopin.com/images/new_v4/title1.gif" complete="complete"/>
	<div class="subNav">
    	<p><a href="/myzhaopin/resume_index.asp">返回我的智联</a></p>
    </div>
	</div>
</div>
<div class="head_r_buzhou" style="width:1000px; margin:0 auto">
<div class="buzhou">
      <ul>
        <li class="bz1"><span>基本情况</span></li>
        <li class="bz2" style="display:[%%show_Navigate%%]"><span>教育与工作</span></li>
        <li class="bz3" style="display:[%%show_Navigate%%]"><span>附加信息</span></li>
        <li class="bz4"><span>完成</span></li>
      </ul>
    </div>
</div>

<!-- end head -->

    <div id="mainContainer" class="layout_found">
        <form name="frmMain" action="resume_baseinfo_save_next.action" method="post">
        <input type="text" value="${recruit.id }" name="recruit.id"/>
        <!-- more items -->
        <div id="popupDiv_moreItems" style="width: 330px; height: 180px; position: absolute;
            visibility: hidden; z-index: 999; top: 0; left: 0;" onclick="event.cancelBubble=true;">
            <div class="popupbox">
                <div>
                    <span><a href="#" onclick="closeMoreItems();return false;">
                        <img src="http://myimg.zhaopin.com/images/new_v4/delete_btn.gif" alt="关闭" /></a></span>
                    <p align="center">
                        让您的简历更具竞争力，您还可以：</p>
                    <div style="margin: 5px 0 15px 40px;">
                        <input type="checkbox" value="1" name="project"/>
                        增加项目经验及专业技能<br />
                        <input type="checkbox" value="1" name="advamang"/>
                        增加团队管理经验</div>
                    <div class="btnCon">
                        <input class="btn7" title="确定" onclick="ff()" type="button" value="确定" name="confirm1"/>
                        &nbsp;&nbsp;
                        <input class="btn5" title="暂不增加，直接完成" onclick="f()" type="button" value="暂不增加，直接完成"
                            name="confirm2"/>
                    </div>
                </div>
            </div>
            <div class="shadowbox">
                <div class="shadowtop">
                </div>
                <div class="shadowcenter">
                </div>
            </div>
            <div class="shadowbottom">
            </div>
        </div>
        <div class="row">
            <!-- 教育背景 -->
            <h2>
                <div class="geren">
                    最高学历教育背景</div>
                <div class="bitian">
                    <span>*</span>为必填项</div>
                <a href="javascript:void(0)" class="blue12line" style="font-weight: normal; display: none;"
                    mzpmodule="addOkDegreeFac" id="popupOkDegree" flagdiv="email_info" formele="frmMain$username|school_name|degree"
                    modjs="/js/new_v3/ajaxbase.js|submitCallback" modprefn="addOkDegreeFac.initFn">
                    <img src="http://myimg.zhaopin.com/images/new_v4/icon_queren.gif" width="19" height="16"
                        vspace="2" align="absmiddle" style="margin-right: 3px;" />添加已通过认证的学历信息</a></h2>
            <table width="634" border="0" cellpadding="0" cellspacing="0" class="table1_found">
                <tr>
                    <th>
                        <input type="hidden" name="text_Edu_RowID" value=""/>
                        <span>*</span><font id="Lstart_date_y">时间</font>
                    </th>
                    <td>
                    <input type="text" name="recruit.startdate" id="recruit.startdate" onclick="setday(this)"/>
                        	至 <span>
                        	<input type="text" name="recruit.enddate" id="recruit.enddate" onclick="setday(this)"/>
                            </span>
                    </td>
                </tr>
                <tr>
                    <td>
                    </td>
                    <td>
                        <div id="conError_start_date_y" style="margin-right: 10px; display: none; float: left;">
                            <div id="txtError_start_date_y" style="width: 150px;">
                            </div>
                        </div>
                        <div id="conError_start_date_m" style="margin-right: 10px; display: none; float: left;">
                            <div id="txtError_start_date_m" style="width: 150px;">
                            </div>
                        </div>
                        <div id="conError_end_date_y" style="margin-right: 10px; display: none; float: left;">
                            <div id="txtError_end_date_y" style="width: 240px;">
                            </div>
                        </div>
                        <div id="conError_end_date_m" style="margin-right: 10px; display: none; float: left;">
                            <div id="txtError_end_date_m" style="width: 240px;">
                            </div>
                        </div>
                    </td>
                </tr>
                <script language="javascript" type="text/javascript">
                    var education_date = new MYRESUME.date_startend('education_date', document.frmMain.start_date_y, document.frmMain.start_date_m, document.frmMain.end_date_y, document.frmMain.end_date_m, true, '教育');
                    if ((document.frmMain.start_date_y.value != '' || document.frmMain.start_date_m.value != '') && (document.frmMain.end_date_y.value == '' || document.frmMain.end_date_m.value == '')) education_date.end.setNow();
                </script>
                <tr>
                    <th>
                        <span>*</span><font id="Lschoolname">学校名称</font>
                    </th>
                    <td>
                        <input type="text" name="recruit.school" size="51" value=""
                            id="recruit.school" mzpmodule="resumeChEnFac" tiptext=""
                            lang="en"/>
                    </td>
                </tr>
                <tr id="conError_schoolname" style="display: none;">
                    <td>
                    </td>
                    <td>
                        <div id="txtError_schoolname">
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>
                        <span>*</span><font id="LmainMajorF">专业名称</font>
                    </th>
                    <td>
                        <input type="button" id="mainMajorF" class="selectBut2" mzpmodule="popupSingChooFac"
                            hiddenname="document.frmMain.mainMajor" data="major" divwidth="500" titlename="专业名称"
                            itemcol="4"/>
                        &nbsp;&nbsp;
                        <input type="button" id="subMajorF" class="selectBut2" mzpmodule="popupSingChooFac"
                            parentobj="mainMajorF" hiddenname="document.frmMain.subMajor" data="major" divwidth="410"
                            titlename="专业名称" clickitemfixfn="showCustomMajor"/>
                        <input type="hidden" name="mainMajor" value=""/>
                        <input type="hidden" name="subMajor" value=""/>
                    </td>
                </tr>
                <tr id="conError_subMajor" style="display: none;">
                    <td>
                    </td>
                    <td>
                        <div id="txtError_subMajor">
                        </div>
                    </td>
                </tr>
                <tbody id="customMajorBlock" style="display: none;">
                    <tr>
                        <th>
                            <div style="display: none">
                                <font id="Lmajor">专业名称</font></div>
                        </th>
                        <td>
                            <input type="text" name="major" size="51" value="" id="major"
                                mzpmodule="resumeChEnFac" tiptext="" lang="en" onfocus="tryClearDefaultText(this,'若无合适选项，请在此处填写专业名称');"
                                onblur="trySetDefaultText(this,'若无合适选项，请在此处填写专业名称');"/>
                        </td>
                    </tr>
                    <tr id="conError_major" style="display: none;">
                        <td>
                        </td>
                        <td>
                            <div id="txtError_major">
                            </div>
                        </td>
                    </tr>
                </tbody>
                <tr>
                    <th>
                        <span>*</span><font id="Ldegree">学历/学位</font>
                    </th>
                    <td>
                        <select name="recruit.xueli">
                            <option value="" selected="selected">请选择</option>
                            
<option value="大专" >大专</option>
<option value="本科" >本科</option>
<option value="硕士" >硕士</option>
<option value="博士" >博士</option>
<option value="MBA" >MBA</option>
<option value="EMBA" >EMBA</option>
<option value="中专" >中专</option>
<option value="中技" >中技</option>
<option value="高中" >高中</option>
<option value="初中" >初中</option>
<option value="其他" >其他</option>
                        </select>
                        <em id="email_info" class="msg_ok" style="display: none;">已通过教育部数据库认证</em>
                    </td>
                </tr>
                <tr id="conError_degree" style="display: none;">
                    <td>
                    </td>
                    <td>
                        <div id="txtError_degree">
                        </div>
                    </td>
                </tr>
            </table>
            <!-- end 教育背景 -->
            <!-- 语言能力1 -->
            <h2>
                <div class="geren">
                    语言能力</div>
            </h2>
            <table width="634" border="0" cellpadding="0" cellspacing="0" class="table1_found">
                <colgroup>
                    <col width="130" />
                    <col width="504" />
                </colgroup>
                <tr>
                    <th>
                        <input type="hidden" name="text_Lang_RowID0" value=""/>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;外语语种
                    </th>
                    <td>
                        <select name="language.name" onchange="changeLang(this)">
                            <option value="">无</option>
                            
<option value="英语" >英语</option>
<option value="日语" >日语</option>
<option value="法语" >法语</option>
<option value="德语" >德语</option>
<option value="俄语" >俄语</option>
<option value="韩语" >韩语</option>
<option value="西班牙语" >西班牙语</option>
<option value="葡萄牙语" >葡萄牙语</option>
<option value="阿拉伯语" >阿拉伯语</option>
<option value="意大利语" >意大利语</option>
<option value="999" >其他</option>
                        </select>
                        <span id="foreignLan0" style="display: ">&nbsp;&nbsp;&nbsp;&nbsp;读写能力
                            <select name="language.read">
                                
<option value="一般" >一般</option>
<option value="良好" >良好</option>
<option value="熟练" >熟练</option>
<option value="精通" >精通</option>
                            </select>
                            &nbsp;&nbsp;&nbsp;&nbsp;听说能力
                            <select name="language.speak">
                                
<option value="一般" >一般</option>
<option value="良好" >良好</option>
<option value="熟练" >熟练</option>
<option value="精通" >精通</option>
                            </select>
                        </span><span id="dialectLan0" style="display: none;">&nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="text" name="dialect0" value="" id="dialect0" mzpmodule="resumeChEnFac"
                                tiptext="" lang="en"/>
                        </span>
                    </td>
                </tr>
                <tbody id="languages0tbody" style="display: none">
                    <tr>
                        <td colspan="2" valign="bottom">
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font
                                color="red">请勿重复选择此语种</font>
                        </td>
                    </tr>
                </tbody>
                <tr id="conError_language" style="display: none;">
                    <td>
                    </td>
                    <td id="txtError_language">
                    </td>
                </tr>
            </table>
            <!-- end 语言能力1 -->
            <!-- 语言能力2 -->
            <div id="language_item2" style="display: none;">
                <h2>
                    <div class="geren">
                        语言能力2</div>
                </h2>
                <table width="634" border="0" cellpadding="0" cellspacing="0" class="table1_found">
                    <colgroup>
                        <col width="130" />
                        <col width="504" />
                    </colgroup>
                    <tr>
                        <th>
                            <input type="hidden" name="text_Lang_RowID1" value=""/>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;外语语种
                        </th>
                        <td>
                            <select name="languages1" onchange="changeLang(this)">
                                <option value="">无</option>
                                
<option value="英语" >英语</option>
<option value="日语" >日语</option>
<option value="法语" >法语</option>
<option value="德语" >德语</option>
<option value="俄语" >俄语</option>
<option value="韩语" >韩语</option>
<option value="西班牙语" >西班牙语</option>
<option value="葡萄牙语" >葡萄牙语</option>
<option value="阿拉伯语" >阿拉伯语</option>
<option value="意大利语" >意大利语</option>
<option value="999" >其他</option>
                            </select>
                            <span id="foreignLan1" style="display: ">&nbsp;&nbsp;&nbsp;&nbsp;读写能力
                                <select name="read1">
                                    
<option value="一般" >一般</option>
<option value="良好" >良好</option>
<option value="熟练" >熟练</option>
<option value="精通" >精通</option>
                                </select>
                                &nbsp;&nbsp;&nbsp;&nbsp;听说能力
                                <select name="spoken1">
                                    
<option value="一般" >一般</option>
<option value="良好" >良好</option>
<option value="熟练" >熟练</option>
<option value="精通" >精通</option>
                                </select>
                            </span><span id="dialectLan1" style="display: none;">&nbsp;&nbsp;&nbsp;&nbsp;
                                <input type="text" name="dialect1" value="" id="dialect1" mzpmodule="resumeChEnFac"
                                    tiptext="" lang="en"/>
                            </span>
                        </td>
                    </tr>
                    <tbody id="languages1tbody" style="display: none">
                        <tr>
                            <td colspan="2" valign="bottom">
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font
                                    color="red">请勿重复选择此语种</font>
                            </td>
                        </tr>
                    </tbody>
                    <tr id="conError_language2" style="display: none;">
                        <td>
                        </td>
                        <td id="txtError_language2">
                        </td>
                    </tr>
                </table>
            </div>
            <!-- end 语言能力2 -->
            <div id="addLanA" class="line150" style="margin: 5px 0 0 180px;">
                <a href="#" onclick="addLanguage();return false;" style="display: ">
                    添加更多语言</a><br/>
                <span class="grey12">提示：此处最多两项，简历修改中可以添加更多语言</span></div>
            <!-- 工作经验 -->
            <h2>
                <div class="geren">
                    工作经验</div>
                <div class="bitian">
                    <span>*</span>为必填项</div>
                <font class="black12">（建议您从最近一次工作经验开始填起，然后依次填写；或稍后在简历修改中继续填写更多工作经验。）</font></h2>
            <div class="org12" style="margin: 5px 0 5px 15px; display: none;">
                [ 提示：以下是您的最近一次工作经验，若修改了选择项，则相应的英文简历该条记录也会自动被更改。请保持中英文内容的一致性。]</div>
            <table width="634" border="0" cellpadding="0" cellspacing="0" class="table1_found">
                <tr>
                    <th>
                        <input type="hidden" name="text_Work_RowID" value=""/>
                        <span>*</span><font id="Lcmpanyname">企业名称</font>
                    </th>
                    <td colspan="3">
                        <input type="text" name="experience.companyname" size="59" value=""
                            id="experience.companyname" mzpmodule="resumeChEnFac" tiptext=""
                            lang="cn"/>
                    </td>
                </tr>
                <tr id="conError_cmpanyname" style="display: none;">
                    <td>
                    </td>
                    <td colspan="3">
                        <div id="txtError_cmpanyname">
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>
                        <span>*</span><font id="Lcompanytype">企业性质</font>
                    </th>
                    <td>
                        <select name="experience.xingzhi">
                            <option value="">请选择</option>
                            
<option value="1" >国企</option>
<option value="2" >外商独资</option>
<option value="3" >代表处</option>
<option value="4" >合资</option>
<option value="5" >民营</option>
<option value="8" >股份制企业</option>
<option value="9" >上市公司</option>
<option value="6" >国家机关</option>
<option value="10" >事业单位</option>
<option value="7" >其它</option>
                        </select>
                    </td>
                    <th>
                        企业规模
                    </th>
                    <td>
                        <select name="experience.guimo">
                            <option value="7" selected="selected">请选择</option>
                            
<option value="1" >20人以下</option>
<option value="2" >20-99人</option>
<option value="3" >100-499人</option>
<option value="4" >500-999人</option>
<option value="5" >1000-9999人</option>
<option value="6" >10000人以上</option>
                        </select>
                    </td>
                </tr>
                <tr id="conError_companytype" style="display: none;">
                    <td>
                    </td>
                    <td colspan="3">
                        <div id="txtError_companytype">
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>
                        <span>*</span><font id="Lbutton_industryF">行业类别</font>
                    </th>
                    <td colspan="3">
                        <input type="button" id="button_industryF" name="experience.hangyeleibie" class="selectBut2"
                            mzpmodule="popupSingChooFac" hiddenname="document.frmMain.industry" data="industry"
                            divwidth="660" titlename="行业类别" itemcol="3"/>
                        <input type="hidden" name="industry" value=""/>
                    </td>
                </tr>
                <tr id="conError_industry" style="display: none;">
                    <td>
                    </td>
                    <td colspan="3">
                        <div id="txtError_industry">
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;所在的部门
                    </th>
                    <td colspan="3">
                        <input type="text" name="experience.bumen" size="59" value="" id="department"
                            mzpmodule="resumeChEnFac" tiptext="" lang="cn"/>
                    </td>
                </tr>
                <tr>
                    <th valign="top">
                        <span>*</span><font id="Lbutton_jobtypeF">职位类别</font>
                    </th>
                    <td colspan="3">
                        <input type="button" id="button_jobtypeF" name="experience.zhiweileibie" class="selectBut2"
                            mzpmodule="popupSingChooFac" hiddenname="document.frmMain.SchJobType" data="jobname"
                            divwidth="480" titlename="职位类别" itemcol="3" />
                        &nbsp;&nbsp;
                        <input type="button" id="button_subjobtypeF" class="selectBut2" mzpmodule="popupSingChooFac"
                            parentobj="button_jobtypeF" hiddenname="document.frmMain.subJobType" data="jobname"
                            divwidth="410" titlename="职位名称" clickitemfixfn="showCustomJobName" initfixfn="initCustomJobName"/>
                        <input type="hidden" name="SchJobType" value=""/>
                        <input type="hidden" name="subJobType" value=""/>
                    </td>
                </tr>
                <tr id="conError_jobtype" style="display: none;">
                    <td>
                    </td>
                    <td colspan="3">
                        <div id="txtError_jobtype">
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>
                        <span>*</span><font id="LcustomSubJobtype">职位名称</font>
                    </th>
                    <td colspan="3">
                        <input type="text" name="experience.zhiyemingcheng" value=""
                            size="59" id="customSubJobtype" mzpmodule="resumeChEnFac" tiptext=""
                            lang="cn"/><span style="color: #989898;" >（此项可修改）</span>
                    </td>
                </tr>
                <tr id="conError_customSubJobtype" style="display: none;">
                    <td>
                    </td>
                    <td colspan="3">
                        <div id="txtError_customSubJobtype">
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>
                        <span>*</span><font id="Lworkstart_date_y">工作时间</font>
                    </th>
                    <td colspan="3"><input type="text" name="experience.workstartdate" id="experience.workstartdate" onclick="setday(this)"/>
                        至 <span><input type="text" name="experience.workenddate" id="experience.workenddate" onclick="setday(this)"/>
                           </span>
                    </td>
                </tr>
                <tr>
                    <td>
                    </td>
                    <td colspan="3">
                        <div id="conError_workstart_date_y" style="margin-right: 10px; display: none; float: left;">
                            <div id="txtError_workstart_date_y" style="width: 150px;">
                            </div>
                        </div>
                        <div id="conError_workstart_date_m" style="margin-right: 10px; display: none; float: left;">
                            <div id="txtError_workstart_date_m" style="width: 150px;">
                            </div>
                        </div>
                        <div id="conError_workend_date_y" style="margin-right: 10px; display: none; float: left;">
                            <div id="txtError_workend_date_y" style="width: 240px;">
                            </div>
                        </div>
                        <div id="conError_workend_date_m" style="margin-right: 10px; display: none; float: left;">
                            <div id="txtError_workend_date_m" style="width: 240px;">
                            </div>
                        </div>
                    </td>
                </tr>
                <script language="javascript" type="text/javascript">
                    var work_date = new MYRESUME.date_startend('work_date', document.frmMain.workstart_date_y, document.frmMain.workstart_date_m, document.frmMain.workend_date_y, document.frmMain.workend_date_m, true, '工作');
                    if ((document.frmMain.workstart_date_y.value != '' || document.frmMain.workstart_date_m.value != '') && (document.frmMain.workend_date_y.value == '' || document.frmMain.workend_date_m.value == '')) work_date.end.setNow();
                </script>
                <tr>
                    <th>
                        <span>*</span><font id="Lsalary">职位月薪(税前)</font>
                    </th>
                    <td colspan="3">
                        <select name="experience.zhiweiyuexin">
                            <option value="" selected="selected">请选择</option>
                            
<option value="1000" >1000元/月以下</option>
<option value="2000" >1000-2000元/月</option>
<option value="4000" >2001-4000元/月</option>
<option value="6000" >4001-6000元/月</option>
<option value="8000" >6001-8000元/月</option>
<option value="10000" >8001-10000元/月</option>
<option value="15000" >10001-15000元/月</option>
<option value="25000" >15000-25000元/月</option>
<option value="99999" >25000元/月以上</option>
<option value="0000" >保密</option>
                        </select>
                    </td>
                </tr>
                <tr id="conError_salary" style="display: none;">
                    <td>
                    </td>
                    <td colspan="3">
                        <div id="txtError_salary">
                        </div>
                    </td>
                </tr>
                <tr>
                    <th valign="top">
                        <span>*</span><font id="Ldescription">工作描述</font>
                    </th>
                    <td colspan="3">
                        <div class="sell_resumetitle_self">参考模板：<em data-index="1">销售代表</em><em data-index="2">电话销售</em><em data-index="3">销售主管</em></div>
                        <div class="sell_resumetitle_help table5sef">已输入3000字，还可输入0字</div>
                        <div class="sell_resumetitle" id="sell_resumetitle">
                            <span class="current" onclick="dyweTrackEvent('sellresume','oneselfwrite')">自己写</span><span
                                class="sell_titicon" onclick="dyweTrackEvent('sellresume','helpwrite')">我帮你写</span></div>
                        <div class="sell_resumeCont">
                            <textarea class="sell_resumearea grey12 sell_diy" name="experience.miaoshu" cols="80"
                                rows="8" onfocus="calWordNumRemained(3000,this,document.getElementById('maxWord1'),document.getElementById('maxWord2'),'eg3')"
                                onblur="if($('.sell_resumetitle_self em').focus()){return;}else{submitCallback('comment='+ua(document.frmMain.job_description.value),'usermaster_blacklists_save.asp?iChecked=0&iBlocked=0',ajaxReturn,'post','');iniWordNum(document.getElementById('maxWord1'),document.getElementById('maxWord2'),'限3000字以内','eg3')}"
                                onkeyup="calWordNumRemained(3000,this,document.getElementById('maxWord1'),document.getElementById('maxWord2'),'eg3')"
                                id="job_description" mzpmodule="resumeChEnFac" tiptext=""
                                lang="cn">请详细描述您所负责的具体工作内容、业绩的达成情况和掌握的资源、客户等。</textarea></br> <span class="grey12">填写文字在100个字以上评定等级，少于不计算，内容越详细，等级越高。<br/>
                                    （<span id="maxWord1"
                                                        style="display: none"></span><span id="maxWord2">限3000个字</span><a onmouseover="stopTimeG();showPopup('eg3',event,getXY(document.getElementById('eg3Img')).x+12,getXY(document.getElementById('eg3Img')).y+10)"
                                                            onclick="return false;" onmouseout="startTimeG();" href="http://my.zhaopin.com/myzhaopin/resume_baseinfo.asp?ext_id=JR028855777R90000009000&amp;resume_id=3430176&amp;Version_Number=1&amp;language_id=1&amp;LocationUrl=resume_list&amp;DYWE=1228439254234.89987.1229671288.1229909284.33#"><img
                                                                id="eg3Img" src="http://myimg.zhaopin.com/images/new_v3/iconquestion2.gif" align="absMiddle"
                                                                border="0" style="display: none"></a>）</span>
                            <div id="conError_description" style="display: none;">
                                <div id="txtError_description">
                                </div>
                            </div>
                            <span class="org12" id="editoldmess" style="display: none"></span>
                        </div>
                        <div class="sell-resumequstion" id="sell-resumequstion">
                            <div class="questionclassfi">
                                智联调查显示，近七成HR在面试销售岗位时会关注以下问题，善用求职利器，让求职成功率翻倍<span class="questiontips">带*号的为必填项</span>
                            </div>
                            <!--基础问题-->
                            <div class="questionDiv">
                                <div>
                                    <label for="">您的日常工作有</label>
                                    <input type="text" class="questiontxt size1" />
                                </div>
                                <div>
                                    <label for="">* 您销售的产品是</label>
                                    <input type="text" class="questiontxt size2 mustreturn" />
                                    <div class="errortxt">请回答该必填项</div>
                                </div>
                                <div>
                                    <label for="">您所负责的产品销售区域是</label>
                                    <input type="text" class="questiontxt size3" />
                                </div>
                                <div>
                                    <label for="">您曾获得的奖项是</label>
                                    <input type="text" class="questiontxt size4" />
                                </div>
                                <div>
                                    <label for="">* 您曾取得的销售业绩是</label>
                                    <input type="text" class="questiontxt size5 mustreturn" />
                                    <div class="errortxt">请回答该必填项</div>
                                </div>
                                <div class="maxhg">
                                    <label for="">您掌握的渠道资源是（例如：渠道、客户、政府、运营商、商超、餐饮业、校园和医疗行业资源等）</label>
                                    <input type="text" class="questiontxt size6" />
                                </div>
                                <div>
                                    <label for="">您管理的经销商层级是</label>
                                    <input type="text" class="questiontxt size7" />
                                </div>
                            </div>
                            <div class="creatBox">
                                <button type="button" class="creatSellbtn"  id="createDescription"></button>
                            </div>
                        </div>
                    </td>
                </tr>
            </table>
            <!-- end 工作经验 -->
            <div class="tishi" id="tishi" style="padding-left: 55px; margin-bottom: 10px;">
                您有"<span id="LErrmsgInfo"></span>"等<span id="errmsgCounter"></span>项必要信息未填写，请填写完毕后再保存</div>
            <div class="btnCon_found">
                <a href="resume_index.asp">返回我的智联</a>
                <input type="button" value="保存并新增工作经验" onclick="goto('addExp')" class="btn5 sellDescription12" name="newexp">
                &nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" id="bcbxz" class="btn6 sellDescription12" value="保存并完成" title="保存并完成" name="next"
                    onclick="send()">
            </div>
            <script type="text/javascript">
            	function send(){
            		document.frmMain.submit();
            	}
            </script>
        </div>
        </form>
        <script language="javascript" type="text/javascript">
		<!--
            var isNewUser = "false";
            trySetDefaultText(document.frmMain.major, '若无合适选项，请在此处填写专业名称');
            iniCheckForm('frmMain');

            MYRESUME.EventUtils.addEvent(document.frmMain.customSubJobtype, 'blur', function () { subJobType.fnValidate() });


            function hideCurrentPopup() {
                if (window.currentlyVisiblePopup) {
                    changeObjectVisibility(window.currentlyVisiblePopup, 'hidden');

                    switch (window.currentlyVisiblePopup) {
                        case 'button_industryF_div': industry.fnValidate(); break;
                        case 'button_subjobtypeF_div': subJobType.fnValidate(); break;
                        case 'education_date_start_y_div': start_date_y.fnValidate(); if (start_date_m.s != null && start_date_m.s > -1) start_date_m.fnValidate(); break;
                        case 'education_date_start_m_div': start_date_m.fnValidate(); if (start_date_y.s != null && start_date_y.s > -1) start_date_y.fnValidate(); break;
                        case 'education_date_end_y_div': end_date_y.fnValidate(); if (end_date_m.s != null && end_date_m.s > -1) end_date_m.fnValidate(); break;
                        case 'education_date_end_m_div': end_date_m.fnValidate(); if (end_date_y.s != null && end_date_y.s > -1) end_date_y.fnValidate(); break;
                        case 'work_date_start_y_div': workstart_date_y.fnValidate(); if (workstart_date_m.s != null && workstart_date_m.s > -1) workstart_date_m.fnValidate(); break;
                        case 'work_date_start_m_div': workstart_date_m.fnValidate(); if (workstart_date_y.s != null && workstart_date_y.s > -1) workstart_date_y.fnValidate(); break;
                        case 'work_date_end_y_div': workend_date_y.fnValidate(); if (workend_date_m.s != null && workend_date_m.s > -1) workend_date_m.fnValidate(); break;
                        case 'work_date_end_m_div': workend_date_m.fnValidate(); if (workend_date_y.s != null && workend_date_y.s > -1) workend_date_y.fnValidate(); break;
                        case 'subMajorF_div': subMajor.fnValidate(); break;
                    }

                    window.currentlyVisiblePopup = false;
                }
            }
            MYRESUME.EventUtils.addEvent(document, 'click', hideCurrentPopup)
		-->
        </script>
    </div>
    <script src="http://images.zhaopin.com/new2011/bottom/bottom_2011_utf_8.js"></script>

    <script type="text/javascript" src="/js/new_v4/fnUtil.js"></script>
    <script type="text/javascript" src="/js/new_v4/fnResumeChEn.js"></script>
    <script type="text/javascript" src="/js/new_v4/fnDegree.js"></script>
    <script type="text/javascript" src="/js/new_v4/fnPopupSingChoo.js"></script>
    <script src="http://img01.zhaopin.cn/2012/js/jquery-1.6.4.min.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function () {
            $("#createDescription").click(function () {
                //官方监控ga
                dyweTrackEvent('sellresume', 'createdescription');
                //”生成描述”按钮,需要跟踪到具体某一份简历
                var i = new Image(1, 1);
                var extid = GetQueryString("ext_id");
                var version = GetQueryString("Version_number");
                var dywesuId = getCookie("JsNewlogin");
                i.src = "http://pv.zhaopin.cn/track.gif?dywehn=my.zhaopin.com&pos=f0001&act=10001&dywesu=" + dywesuId + "&extid=" + extid + "_" + version + "&path=" + document.location + "&referer=" + document.referrer;
            });

            function getCookie(name) {
                var tmp, reg = new RegExp("(?:^| )" + name + "=([^;]*)(?:;|$)", "gi");
                return (tmp = reg.exec(document.cookie)) ? (unescape(tmp[1])) : '';
            }
            function GetQueryString(name) {
                var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
                var r = window.location.search.substr(1).match(reg);
                if (r != null) return unescape(r[2]); return null;
            }
        });
    </script>
    <script type="text/javascript" src="/js/New_v3/resume_addjs.js"></script>
</body>
</html>
