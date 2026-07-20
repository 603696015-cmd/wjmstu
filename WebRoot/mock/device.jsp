<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/commons/meta.jsp"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href="${ctx}/mock/App_Themes/Default/Administration_css.css" type="text/css" rel="stylesheet">
<link href="${ctx}/mock/App_Themes/Default/dialog.css" type="text/css" rel="stylesheet">
<link href="${ctx}/mock/App_Themes/Default/examMainStyle.css" type="text/css" rel="stylesheet">
<link href="${ctx}/mock/App_Themes/Default/examStyle.css" type="text/css" rel="stylesheet">
<link href="${ctx}/mock/App_Themes/Default/jqModal.litejava3.css" type="text/css" rel="stylesheet">
<link href="${ctx}/mock/App_Themes/Default/jsPopup.css" type="text/css" rel="stylesheet">
<link href="${ctx}/mock/App_Themes/Default/jTip.css" type="text/css" rel="stylesheet">
<link href="${ctx}/mock/App_Themes/Default/StyleSheet.css" type="text/css" rel="stylesheet">
<link href="${ctx}/mock/App_Themes/Default/uploadify.css" type="text/css" rel="stylesheet">
<title>下载试卷</title>
<style type="text/css" media="screen">
#flashcontent {visibility:hidden}
</style>
</head>
<body id="body">
<div id="ctl00_Div1">
  <script src="${ctx}/mock/Mnks/Js/Resources/Resources.zh-CN.js" type="text/javascript"></script>
  <script src="${ctx}/mock/Mnks/Js/jquery-1.4.1.min.js" type="text/javascript"></script>
  <script src="${ctx}/mock/Mnks/Js/jquery.cookie.js" type="text/javascript"></script>
  <script src="${ctx}/mock/Mnks/Js/basic.js" type="text/javascript"></script>
  <script src="${ctx}/mock/Mnks/Js/roller.js" type="text/javascript"></script>
  <script src="${ctx}/mock/Mnks/Js/PopupShareWindow.js" type="text/javascript"></script>
  <script src="${ctx}/mock/Mnks/Js/Jqmodal/jqModal.js" type="text/javascript"></script>
  <script src="${ctx}/mock/Mnks/Js/Jqmodal/jqDnR.js" type="text/javascript"></script>
  <script src="${ctx}/mock/Mnks/Js/jquery-ui-1.7.2.custom.min.js" type="text/javascript"></script>
</div>
<script src="/Mnks/Js/loadswfobject.js" type="text/javascript"></script>
<script type="text/javascript">
        $(function () {
            $("#btnFinish").attr("disabled", true);
        });
        function item_load_over(inData) {
        }
        //flash加载完以后调用
        function process_over() {
            $("#flashcontent").hide();
            $("#lbFlash").show();
            //            $("#lbFlash").html("试卷已经下载完毕！");
            $("#lbFlash").css("color", "#006600");
            $("#btnFinish").removeClass("device_g");
            $("#btnFinish").addClass("device");
            $("#btnFinish").removeAttr("disabled");
            $("#divprocess").hide();
        }
        function redirectUrl() {
            window.location.href = "PaperInfo.aspx";
            return false;
        }
    </script>
<script type="text/javascript" language="javascript">
        function SetExamName(examName) {
            try {
                window.external.SetExamName(examName);
            }
            catch (e) { }
        }
        //设置语言
        function SetImeSequence(lan) {
            try {
                window.external.SetSequence(lan);
            }
            catch (e) { }
        }
        function SysShield(procList) {
            try {
                window.external.SysShield(procList);
            }
            catch (e) { }
        }
        function setBodyBg() {
            $("body").addClass("bodyBg");
        }
        //获取是否是客户端
        function GetClientKey() {
            try {
                return window.external.GetWinClient();
            }
            catch (e) {
                return 0;
            }
        }
        function ExitExam() {
            try {
                window.external.Exit();
            }
            catch (e) {
                window.location = "ExamLogin.aspx";
            }
        }
    </script>
<form name="aspnetForm" method="post" action="GetPaper.aspx" id="aspnetForm">
  <div>
    <input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="/wEPDwUKLTMyNjMyOTcxNg9kFgJmD2QWAgIDD2QWBGYPZBYCAgMPDxYCHgRUZXh0BQsoSFNL5LiA57qnKWRkAgEPZBYCZg9kFgYCAQ8PFgIeCEltYWdlVXJsBR5+L0V4YW1EYXRhLzIzMy9QaG90b3NcTW5rcy5qcGdkZAIFDxYCHwAFC2ppYWNhaWxpYW5nZAIJDxYCHwAFElQxNzExMDgxNjQzMjUwNzc3NWRkjaqnVugcid8Uv2Qa4aKpCRnqcZ+JwJg8G0MMx+D9yJg=">
  </div>
  <div>
    <input type="hidden" name="__VIEWSTATEGENERATOR" id="__VIEWSTATEGENERATOR" value="20CE93A3">
  </div>
  <div id="jqMessageBox" class="jqmDialog">
    <div class="jqmdTL">
      <div class="jqmdTR">
        <div class="jqmdTC jqDrag"> 信息提示 </div>
      </div>
    </div>
    <div class="jqmdBL">
      <div class="jqmdBR">
        <div class="jqmdBC">
          <div class="jqmdBC1" align="center">
            <table border="0" cellpadding="0" cellspacing="0" width="90%">
              <tbody>
                <tr>
                  <td width="13%" style="vertical-align: top"><img alt="" src="/Mnks/App_Themes/Default/Images/info-s.gif"> </td>
                  <td align="left"><div class="jqmdMSG"> <br>
                      <br>
                    </div></td>
                </tr>
              </tbody>
            </table>
          </div>
          <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tbody>
              <tr>
                <td></td>
                <td align="center"><input type="button" value="确定" class="commonButton jqmClose">
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
    <input type="image" src="/Mnks/App_Themes/Default/Images/titlebarclose.gif" class="jqmdX jqmClose">
  </div>
  <div class="body">
    <div id="ctl00_PnlTop">
      <div id="ctl00_divTop" class="top"> </div>
      <div class="loginheader">
        <div class="loginmsg">
          <h1> 汉语网络考试 <span id="ctl00_LblSubjectName">(HSK一级)</span></h1>
        </div>
      </div>
    </div>
    <div class="logincontent">
      <div class="exammain">
        <table width="0" border="0" cellspacing="0" cellpadding="0" class="table">
          <tbody>
            <tr>
              <td align="left" valign="top"><div class="sideBar">
                  <div class="menu">
                    <table border="0" cellpadding="0" cellspacing="0">
                      <tbody>
                        <tr>
                          <td rowspan="3"><img id="ctl00_CphMain_UcUserInfo_ImgExamineePicture" src="../ExamData/233/Photos/Mnks.jpg" alt="考生照片" style="border-width:1px;border-style:solid;width:68px;"> </td>
                          <td><span class="userInfoTitle"> 姓名 <br>
                            </span><span class="userInfoContent"> jiacailiang </span> </td>
                        </tr>
                        <tr>
                          <td style="height: 10px"><p> </p></td>
                        </tr>
                        <tr>
                          <td><span class="userInfoTitle"> 准考证号 <br>
                            </span><span class="userInfoContent"> T17110816432507775</span> </td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                  <div style="clear: both; width: 99%;" class="results"> </div>
                </div></td>
              <td align="left" valign="top"><div class="main">
                  <div class="subNav">
                    <ul>
                      <li><span> 设备调试 </span></li>
                      <li> <img src="/Mnks/App_Themes/Default/Images/index_03_01.png" alt="" border="0"></li>
                      <li></li>
                    </ul>
                    <p> <a href="#" onClick="top.__popupShareWindow.Show('ExamInfo.aspx','帮助')"> 帮助 </a> </p>
                  </div>
                  <div class="content">
                    <div class="debugging">
                      <div class="adjustments">
                        <table border="0" cellpadding="0" cellspacing="0" class="processtable">
                          <tbody>
                            <tr>
                              <th> <div class="processdownload"> 下载试卷 </div></th>
                              <td><img src="/Mnks/App_Themes/Default/Images/debugging_03.png" alt="" border="0"> </td>
                              <td width="800" height="100" align="center" valign="top"><div class="flashcontent">
                                  <object type="application/x-shockwave-flash" data="/Mnks/Attachments/urlLoader.swf" width="430" height="95" id="flashcontent" style="visibility: visible; display: none;">
                                    <param name="id" value="flashcontent">
                                    <param name="name" value="flashcontent">
                                    <param name="menu" value="true">
                                    <param name="quality" value="high">
                                    <param name="play" value="true">
                                    <param name="loop" value="true">
                                    <param name="scale" value="noscale">
                                    <param name="wmode" value="window">
                                    <param name="devicefont" value="false">
                                    <param name="bgcolor" value="#edf0f4">
                                    <param name="allowFullScreen" value="true">
                                    <param name="allowScriptAccess" value="always">
                                    <param name="flashVars" value="file=../ExamData/TestPaper/examdata/1/ASM_328693cb-aa9c-e011-abdb-005056b834eb.xml&amp;prefix_path=../&amp;debug=0&amp;alpha=1">
                                  </object>
                                </div>
                                <h6 id="divprocess" style="display: none;"> 正在下载试卷，请稍候... <img src="/Mnks/App_Themes/Default/Images/debugging_06.png" alt="" border="0" class="processimg"> </h6>
                                <div id="lbFlash" style="color: rgb(0, 102, 0);" class="flashmsg"> 试卷已经下载完毕！ </div></td>
                            </tr>
                          </tbody>
                        </table>
                      </div>
                      <div class="adjustments">
                        <table border="0" cellpadding="0" cellspacing="0">
                          <tbody>
                            <tr>
                              <th rowspan="2"> <img src="/Mnks/App_Themes/Default/Images/eps_img.jpg" alt="" border="0"> </th>
                              <td rowspan="2"><img src="/Mnks/App_Themes/Default/Images/debugging_03.png" alt="" border="0"> </td>
                              <td width="500" height="55" align="center" valign="bottom"><img src="/Mnks/App_Themes/Default/Images/eps_img01.png" alt="" border="0"> </td>
                              <td rowspan="2" width="300"><div id="ctl00_CphMain_PnlAdjust" class="adjustmentsFinish"> <a id="btnFinish" class="  device" href="PaperInfo.aspx" style="color: #ffffff; text-decoration: none;"> 设备调试完成 </a> </div></td>
                            </tr>
                            <tr>
                              <td align="center" valign="top" height="55"><div style="display: none">
                                  <script src="/Mnks/Js/jquery.ui.core.js" type="text/javascript"></script>
                                  <script src="/Mnks/Js/jquery.ui.widget.js" type="text/javascript"></script>
                                  <script src="/Mnks/Js/jquery.ui.mouse.js" type="text/javascript"></script>
                                  <script src="/Mnks/Js/slider/jquery.ui.slider.js" type="text/javascript"></script>
                                  <link href="/Mnks/Js/slider/slider.css" rel="stylesheet" type="text/css">
                                </div>
                                <div style="width: 185px; height: 10px; padding: 5px 0 5px 0;">
                                  <div id="slider-vertical" style="height: 8px;" class="ui-slider ui-slider-horizontal ui-widget ui-widget-content ui-corner-all">
                                    <div class="ui-slider-range ui-slider-range-min ui-widget-header" style="width: 50.5051%;"></div>
                                    <a href="#" class="ui-slider-handle ui-state-default ui-corner-all" style="left: 50.5051%;"></a></div>
                                </div>
                                <div id="amount" style="border-style: none; border-width: 0; color: #f6931f; font-weight: bold;
    width: 48px;">51</div>
                                <div style="width:0px;height:0px;">
                                  <object type="application/x-shockwave-flash" id="player" width="0" height="0" data="/Mnks/Attachments/player.swf?sound_url=../Attachments/sample.mp3&amp;isAuto=1&amp;isLoop=1&amp;volume=50">
                                    <param name="wmode" value="transparent">
                                    <param name="movie" value="/Mnks/Attachments/player.swf?sound_url=../Attachments/sample.mp3&amp;isAuto=1&amp;isLoop=1&amp;volume=50">
                                  </object>
                                </div>
                                <script type="text/javascript">
    $(function () {

    });
    function setInitial() {
        var volume = $.cookie("mediavolume");
        if (volume == null || volume == undefined) {
            volume = 50;
        }
        $("#amount").html(volume);
        try{
            document.getElementById("player").controlVolume(volume);
        }
        catch(ex)
        {
        }

        var date = new Date();
        date.setTime(date.getTime() + 24 * 60 * 60 * 1000);  //换成毫秒
        $.cookie("mediavolume", volume, { path: '/', expires: date });

        $("#slider-vertical").slider({
            orientation: "herizonal",
            range: "min",
            min: 1,
            max: 100,
            value: volume,
            slide: function (event, ui) {
                $("#amount").html(ui.value); //显示音量值
                try{
                    document.getElementById("player").controlVolume(ui.value);
                }
                catch (ex) {
                }
                var date = new Date();
                date.setTime(date.getTime() + 24 * 60 * 60 * 1000);  //换成毫秒   
                $.cookie("mediavolume", ui.value, { path: '/', expires: date });
            }
        }); //slider
    }
    window.onload = setInitial;
</script>
                              </td>
                            </tr>
                          </tbody>
                        </table>
                      </div>
                      <div class="debugging_h3">
                        <p> 根椐播放声音，调节耳机音量大小。如果音量大小已经调整好，请点击“设备调试完成”按钮 </p>
                      </div>
                    </div>
                  </div>
                </div></td>
              <td align="left" valign="top"></td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
  <div style=" display:none;">
    <script src="http://s95.cnzz.com/z_stat.php?id=1255358328&amp;web_id=1255358328" language="JavaScript"></script>
    <script src="http://c.cnzz.com/core.php?web_id=1255358328&amp;t=z" charset="utf-8" type="text/javascript"></script>
    <a href="http://www.cnzz.com/stat/website.php?web_id=1255358328" target="_blank" title="站长统计">站长统计</a></div>
</form>
</body>
</html>
