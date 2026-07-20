<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/commons/meta.jsp"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href="${ctx}/mock/App_Themes/Default/Administration_css.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/mock/App_Themes/Default/dialog.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/mock/App_Themes/Default/examMainStyle.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/mock/App_Themes/Default/examStyle.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/mock/App_Themes/Default/jqModal.litejava3.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/mock/App_Themes/Default/jsPopup.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/mock/App_Themes/Default/jTip.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/mock/App_Themes/Default/StyleSheet.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/mock/App_Themes/Default/uploadify.css" type="text/css" rel="stylesheet" />
<title>信息确认</title>
<div id="ctl00_Div1">
  <script src="${ctx}/mock/Mnks/Js/Resources/Resources.zh-CN.js" type="text/javascript"></script>
  <script src='${ctx}/mock/js/jquery.js' type="text/javascript"></script>
  <script src='${ctx}/mock/Mnks/Js/jquery.cookie.js' type="text/javascript"></script>
  <script src='${ctx}/mock/Mnks/Js/basic.js' type="text/javascript"></script>
  <script src='${ctx}/mock/Mnks/Js/roller.js' type="text/javascript"></script>
  <script src='${ctx}/mock/Mnks/Js/PopupShareWindow.js' type="text/javascript"></script>
  <script src='${ctx}/mock/Mnks/Js/Jqmodal/jqModal.js' type="text/javascript"></script>
  <script src='${ctx}/mock/Mnks/Js/Jqmodal/jqDnR.js' type="text/javascript"></script>
  <script src='${ctx}/mock/Mnks/Js/jquery-ui-1.7.2.custom.min.js' type="text/javascript"></script>
  <link href='/Mnks/Css/examStyle.zh-CN.css'
            rel="stylesheet" type="text/css" />
</div>
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
</head>
<body id="body">
<form name="aspnetForm" method="post" action="ExamineeInfo.aspx" id="aspnetForm">
  <div>
    <input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="/wEPDwUKLTg5MDg0OTM2OA9kFgJmD2QWAgIDD2QWBGYPZBYCAgMPDxYCHgRUZXh0BQsoSFNL5LqM57qnKWRkAgEPZBYCAgkPZBYMAgIPDxYCHwAFC2ppYWNhaWxpYW5nZGQCBA8PFgIfAAUG5Lit5Zu9ZGQCBg8PFgIfAAUD55S3ZGQCCA8PFgIfAAUNU2ltdWxhdGUzNzQxN2RkAgoPDxYCHwAFElQxNzExMDIxNDQwMTcwODgwMmRkAgwPDxYCHwAFCUhTS+S6jOe6p2RkZPHJ6uJFva9USX81V7Y638W+5+A3f390CMNMo9ANVbq0" />
  </div>
  <div>
    <input type="hidden" name="__VIEWSTATEGENERATOR" id="__VIEWSTATEGENERATOR" value="72705ED2" />
    <input type="hidden" name="__EVENTVALIDATION" id="__EVENTVALIDATION" value="/wEWAgKy/pqGBwL6tov0DuShvk2W/3ioApXASrIeFfLNYMJP89dIuD9xe9dfOAuT" />
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
              <tr>
                <td width="13%" style="vertical-align: top"><img alt="" src="/Mnks/App_Themes/Default/Images/info-s.gif" /> </td>
                <td align="left"><div class="jqmdMSG"> <br />
                    <br />
                  </div></td>
              </tr>
            </table>
          </div>
          <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
              <td></td>
              <td align="center"><input type="button" value="确定" class="commonButton jqmClose" />
              </td>
            </tr>
          </table>
        </div>
      </div>
    </div>
    <input type="image" src="/Mnks/App_Themes/Default/Images/titlebarclose.gif"
            class="jqmdX jqmClose" />
  </div>
  <div class="body">
    <div id="ctl00_PnlTop">
      <div id="ctl00_divTop" class="top"> </div>
      <div class="loginheader">
        <div class="loginmsg">
          <h1> 汉语网络考试 <span id="ctl00_LblSubjectName">(HSK二级)</span></h1>
        </div>
      </div>
    </div>
    <div class="logincontent">
      <div id="maininfo" class="pi_main">
        <div class="pi_subNav">
          <ul>
            <li><span> 信息确认 </span></li>
            <li></li>
            <li></li>
          </ul>
          <p> <a href="#" onclick="top.__popupShareWindow.Show('ExamInfo.aspx','帮助')"> 帮助 </a> </p>
        </div>
        <div class="pi_content">
          <div id="examinfo" class="instructions">
            <h2> 考试须知 </h2>
            <div id="cenotdi" class="examtextarea"> 一、在考试开始前30分钟开始入场；在听力考试结束前到达考场的考生，可待阅读部分开始时参加考试，所误时间不补；在阅读考试开始后，迟到的考生不得进入考场参加考试。<br/>
              二、考生进考场时须出示准考证和报名时提供的规定身份证件，身份证件上的姓名必须与准考证上登记的姓名完全一致，证件上的照片必须是可以确认的考生本人，即照片与本人面貌一致。进入考场后，考生须将准考证和身份证件放在桌子的右上方，以备主、监考随时检查。入场时无法提供规定证件或持任何假证件的考生，将被拒绝参加考试，不退还考试费用。<br/>
              三、考试中途一般不得离场，如有特殊原因，考生需要中途离场，须经主考同意，在离开考场前把准考证交给主考官,考生返回考场需出示身份证件。<br/>
              四、考试过程中不允许吃食品及饮用饮料；考试过程中，如果任何考生存在作弊行为，例如：替考、剽窃、抄袭、考试过程中夹带或偷看相关材料等，主考官有权拒绝考生继续考试，或记录在主考报告上，国家汉办有权取消其考试成绩，并保留拒绝该考生参加国家汉办/孔子学院总部所有考试的权利。<br/>
              五、1）由于考场管理失误造成考生受到不公平待遇，例如：考试时间不足、考试设备故障等，导致考生无法完成考试，国家汉办将尽快安排考生免费重新参加考试，不承担任何间接损失补偿。<br/>
　　
2）由于不可抗力，例如：自然灾害、意外事故，迫使考试无法进行，国家汉办将尽快安排重试，或全额退回考试费用，不承担任何间接损失补偿。               <br/>
              六、请考生认真按照步骤进行耳麦的调试，如有问题请即时向主考老师询问。 </div>
          </div>
          <div id="ctl00_CphMain_PnlUserInfo" class="personal_information">
            <h2> 个人信息 </h2>
            <div id="introudce">
              <table border="0" cellpadding="0" cellspacing="0" class="personal_information_img">
                <tr>
                  <td style="background: #afeafe; width: 167px;"><img id="" src='/Mnks/ExamData/233/Photos/Mnks.jpg' alt=" " border="0" width="63" />
                    <div class="clear"> </div></td>
                  <td style="background: #ddf0fe">&nbsp;</td>
                </tr>
              </table>
              <table border="0" cellpadding="0" cellspacing="0" width="100%" class="informationfrom">
                <tr>
                  <th> 姓名： </th>
                  <td><span id="ctl00_CphMain_lbName">jiacailiang</span> </td>
                </tr>
                <tr>
                  <th> 国籍： </th>
                  <td><span id="ctl00_CphMain_lbNationality">中国</span> </td>
                </tr>
                <tr>
                  <th> 性别： </th>
                  <td><span id="ctl00_CphMain_LblSex">男</span> </td>
                </tr>
                <tr>
                  <th> 证件号码： </th>
                  <td><span id="ctl00_CphMain_lbCertificateNo">Simulate37417</span> </td>
                </tr>
                <tr>
                  <th> 准考证号： </th>
                  <td><span id="ctl00_CphMain_lbExamCardNo">T17110214401708802</span> </td>
                </tr>
                <tr>
                  <th> 考试科目： </th>
                  <td><span id="ctl00_CphMain_lbSubjectName">HSK二级</span> </td>
                </tr>
              </table>
            </div>
          </div>
          <div class="bodybutton">
            <input type="submit" name="ctl00$CphMain$btnInit" value="确 认" id="ctl00_CphMain_btnInit" class="button" />
          </div>
          <div class="clear"> </div>
        </div>
      </div>
    </div>
  </div>
  <div style=" display:none;">
    <script src="http://s95.cnzz.com/z_stat.php?id=1255358328&web_id=1255358328" language="JavaScript"></script>
  </div>
</form>
</body>
</html>
