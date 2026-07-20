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
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    
    
    <link rel="shortcut icon" href="http://www.kuanxue.com/static/comm/imgs/kxlogo.ico"/>
    <link href="http://www.kuanxue.com/static/comm/css/prompt.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="http://www.kuanxue.com/static/kuanxuev2/css/head-mh.css" media="all" rel="stylesheet" type="text/css"/>
      <link href="http://www.kuanxue.com/static/comm/css/jquery-ui-1.8.16.custom.css" media="all" rel="stylesheet" type="text/css"/>
     <script type="text/javascript" src="http://www.kuanxue.com/static/comm/js/jquery-1.6.4.min.js"></script>
    <script type="text/javascript" src="http://www.kuanxue.com/static/comm/js/jquery.json-2.3.min.js"></script>
    <script type="text/javascript" src="http://www.kuanxue.com/static/comm/js/common.js"></script>
    <script type="text/javascript" src="http://www.kuanxue.com/static/platform/js/platform.js"></script>
    <script type="text/javascript" src="http://www.kuanxue.com/static/comm/js/jquery-impromptu.3.2.js"></script>
    <script type="text/javascript" src="http://www.kuanxue.com/static/comm/js/jquery-ui-1.8.16.custom.min.js"></script>
    <title>宽学网-全球首家云培训运营商</title>
    
    <link rel="stylesheet" type="text/css" href="http://www.kuanxue.com/static/kuanxuev2/css/index.css"/>
    <script type="text/javascript" src="http://www.kuanxue.com/static/kuanxuev2/js/jquery.slideshow.js"></script>
    <meta property="qc:admins" content="2400242371635160556375" />

</head>
<body>
















<head>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#header-searchtxt").focus(function() {
                $("#header-searchtmsg").hide();
                $(".search").addClass("txtfocus")
            });
            $("#header-searchtxt").blur(function() {
//                alert()
                 if($("#header-searchtxt").val()=="")
                 {
                      $("#header-searchtmsg").show();
                $(".search").removeClass("txtfocus")
                 }
                else{
                      $("#header-searchtmsg").hide();
                     $(".search").removeClass("txtfocus")
                 }
            });
            $("#header-searchtmsg").click(function() {
                $("#header-searchtxt").focus()
            })
              $(".rightcolum ul li").hover(function(){
            $(this).addClass("current")
        },function(){
            $(this).removeClass("current")
        });

        })
    </script>
</head>

<div class="site-nav">
    <div class="site-nav-bd">
        <p class="login-info">
            欢迎来到宽学网
           <span class="loginbar">【
            <a href="http://www.kuanxue.com/pl/site/companyLoginForm.html">登录企业大学</a>
            】
          </span>
        </p>
        <ul class="site-msg">
            <li><a href="http://www.kuanxue.com/pl/ajax/webSite/helpCenter.do" target="_blank">帮助中心</a></li>
            <li class="br-none">咨询热线：<span class="free-tell">4007-33-8800</span></li>
        </ul>
    </div>
</div>
<!--siteNav end-->
<!--logo search star-->
<div id="header">
    <div id="header-c">
        <div id="logo">
            <a href="http://www.kuanxue.com/pl/webSite/loginHome.html"><img src="http://www.kuanxue.com/static/kuanxuev2/imgs/logo1.png" alt="宽学网"/></a>
        </div>
        <div class="search-box">
            <div class="search-hot">
                <span>热门搜索：</span>
                
                    <a href="javascript:void(0)"
                       onclick="location.href='http://www.kuanxue.com/pl/webSite/sousuo.do?name='+encodeURI('沟通')+'&characterChangeFlag=true'">沟通</a>
                
                    <a href="javascript:void(0)"
                       onclick="location.href='http://www.kuanxue.com/pl/webSite/sousuo.do?name='+encodeURI('行政')+'&characterChangeFlag=true'">行政</a>
                
                    <a href="javascript:void(0)"
                       onclick="location.href='http://www.kuanxue.com/pl/webSite/sousuo.do?name='+encodeURI('客服')+'&characterChangeFlag=true'">客服</a>
                
                    <a href="javascript:void(0)"
                       onclick="location.href='http://www.kuanxue.com/pl/webSite/sousuo.do?name='+encodeURI('财务会计')+'&characterChangeFlag=true'">财务会计</a>
                
                    <a href="javascript:void(0)"
                       onclick="location.href='http://www.kuanxue.com/pl/webSite/sousuo.do?name='+encodeURI('电话销售')+'&characterChangeFlag=true'">电话销售</a>
                
            </div>
            <div class="search">
                <form method="post" action="/pl/webSite/sousuo.do">
                    <label id="header-searchtmsg">即用即学 课程搜索</label>
                    <input class="search-text" id="header-searchtxt" type="text" name="name" value=""/>
                    <input class="search-btn" id="" type="submit" value="搜索"/>
                </form>
            </div>
        </div>
    </div>
</div>
<!--logo search end-->
<!--nav star-->
<div id="nav-box">
    <div id="nav-c">
        <div class="nav">
            <ul>
                <li  class='current'><a href="http://www.kuanxue.com/">首页</a>
                </li>
                <li ><a href="http://www.kuanxue.com/pl/webSite/platformCourse.html">网络课程</a>
                </li>
                <li  ><a href="http://www.kuanxue.com/pl/webSite/trainingClassIndex2.html">能力辅导</a>
                </li>
                <li  ><a href="http://www.kuanxue.com/pl/webSite/cert-list.html">培训认证</a>
                </li>
                <li  ><a href="http://www.kuanxue.com/pl/webSite/expertList.html">专家团</a>
                </li>
                <li  ><a href="http://www.kuanxue.com/pl/webSite/newsList.html">新闻</a>
                </li>
            </ul>
        </div>

        <div class="rightcolum">
            <ul>
                <li><span><a href="http://www.kuanxue.com/pl/webSite/enterprise.html" target="_blank">企业大学</a></span></li>
                <li><span><a href="http://www.kuanxue.com/blendedtraining/index.do" target="_blank">公开课</a></span></li>
                <li class=" last"><span><a href="http://www.kuanxue.com/emp.html" target="_blank">新员工</a></span></li>

                <div class="courses-tips"></div>
                </li>
            </ul>
        </div>
    </div>
</div>


<!--nav end-->













<div id="wrapper">
<div class="container">
<!--quick-login banner star-->
<div class="quick-login-box clearfix">
    <div class="quick-loginbar">
    </div>
    <div class="banner home_slide" id="banner">
        <!-- 焦点图 -->
        <ul class="list">
            <li><a href="http://www.kuanxue.com/pl/webSite/enterprise.html"><img
                    src="http://www.kuanxue.com/static/kuanxuev2/imgs/banner9.png"></a></li>
            <li><a href="http://www.kuanxue.com/pl/kuanxueQue.do"><img
                    src="http://www.kuanxue.com/static/kuanxuev2/imgs/banner8.png"></a></li>
            <li><a href="http://www.kuanxue.com/tutor/home.do"><img
                    src="http://www.kuanxue.com/static/kuanxuev2/imgs/banner5.png"></a></li>
        </ul>
        <ul class="btn">
            <li class="b_1 selected"><a href="http://www.kuanxue.com/pl/webSite/enterprise.html" target="_blank">企业全员学习解决方案</a></li>
            <li class="b_2"><a href="http://www.kuanxue.com/pl/kuanxueQue.do" target="_blank"> 在线培训创业者解决方案</a></li>
            <li class="b_3"><a href="http://www.kuanxue.com/emp/empAdv.do" target="_blank"> 新员工培训解决方案</a></li>
        </ul>
    </div>

</div>
<div class="main clearfix">
<div class="leftcontaine">
    <div class="bannerclass">
        <img src="http://www.kuanxue.com/static/kuanxuev2/imgs/bannerclass.png" alt="">
    </div>
    <!--开放课程 star -->
    <div class="col-class">
        <h2 class="sidetitle">
            开放课程
        </h2>
        <ul class="col-class-list clearfix">
            
                <li>
                    <div class="videobox"><a href="#"
                                             onclick="window.open('http://www.kuanxue.com/data/course/20130503134944930/common/htm/main.html')"><img
                            width="130" height="100" src="http://www.kuanxue.com/data/image/1305081053550281.jpg"></a>

                        <div class="player"
                             onclick="window.open('http://www.kuanxue.com/data/course/20130503134944930/common/htm/main.html')"></div>
                    </div>
                    <p class="name"><a href="#"
                                       onclick="window.open('http://www.kuanxue.com/data/course/20130503134944930/common/htm/main.html')">如何做好产品决策的财务支持</a>
                    </p>
                </li>

            
                <li>
                    <div class="videobox"><a href="#"
                                             onclick="window.open('http://www.kuanxue.com/data/course/20130527164834071/common/htm/main.html')"><img
                            width="130" height="100" src="http://www.kuanxue.com/data/image/1306081654519181.jpg"></a>

                        <div class="player"
                             onclick="window.open('http://www.kuanxue.com/data/course/20130527164834071/common/htm/main.html')"></div>
                    </div>
                    <p class="name"><a href="#"
                                       onclick="window.open('http://www.kuanxue.com/data/course/20130527164834071/common/htm/main.html')">如何制定职业规划目标</a>
                    </p>
                </li>

            
                <li>
                    <div class="videobox"><a href="#"
                                             onclick="window.open('http://www.kuanxue.com/data/course/20130110135403125/common/htm/main.html')"><img
                            width="130" height="100" src="http://www.kuanxue.com/data/image/1301101354148551.jpg"></a>

                        <div class="player"
                             onclick="window.open('http://www.kuanxue.com/data/course/20130110135403125/common/htm/main.html')"></div>
                    </div>
                    <p class="name"><a href="#"
                                       onclick="window.open('http://www.kuanxue.com/data/course/20130110135403125/common/htm/main.html')">如何提高语言说服力</a>
                    </p>
                </li>

            
                <li>
                    <div class="videobox"><a href="#"
                                             onclick="window.open('http://www.kuanxue.com/data/course/20120912110934004/common/htm/main.html')"><img
                            width="130" height="100" src="http://www.kuanxue.com/data/image/1209121109466471.jpg"></a>

                        <div class="player"
                             onclick="window.open('http://www.kuanxue.com/data/course/20120912110934004/common/htm/main.html')"></div>
                    </div>
                    <p class="name"><a href="#"
                                       onclick="window.open('http://www.kuanxue.com/data/course/20120912110934004/common/htm/main.html')">如何制定技术人员的工资</a>
                    </p>
                </li>

            
        </ul>
        <div class="f-more">
            <span><a href="http://www.kuanxue.com/pl/webSite/openAdv.do">共有41门开放课程供您免费体验</a></span><a href="http://www.kuanxue.com/pl/webSite/openAdv.do"
                                                                                        target="_blank">查看更多</a>
        </div>
    </div>

    <!--热门课程 star-->
    <div class="col-class">
        <h2 class="sidetitle">
            热门课程
        </h2>

        <div class="hotclassbox clearfix">
            <div class="hotclass-item ">
                <div class="imgtxttop">
                    <div class="pic"><a href="http://www.kuanxue.com/pl/webSite/platformCourse.do?catalogId=11" target="_blank"><img
                            src="http://www.kuanxue.com/static/kuanxuev2/imgs/hotclass1.jpg" alt=""></a></div>
                    <ul class="hotclass-name">
                        <li><span><a href="#">[产品导入]</a></span><a href="#">如何进行新产品可靠性分析</a></li>
                        <li><span><a href="#">[生产准备]</a></span><a href="#">如何审定生产计划的合理性</a></li>
                        <li><span><a href="#">[生产管理]</a></span><a href="#">如何协调生产出货</a></li>
                        <li><span><a href="#">[生产准备]</a></span><a href="#">如何改善生产交期</a></li>
                        <li><span><a href="#">[车间管理]</a></span><a href="#">如何做好车间产前准备工作</a></li>
                    </ul>
                </div>
                <a href="http://www.kuanxue.com/pl/webSite/platformCourse.do?catalogId=11" class="more">
                    更多生产管理课程》
                </a>
            </div>
            <div class="hotclass-item ">
                <div class="imgtxttop">
                    <div class="pic"><a href="http://www.kuanxue.com/pl/webSite/platformCourse.do?catalogId=2"><img
                            src="http://www.kuanxue.com/static/kuanxuev2/imgs/hotclass2.jpg" alt=""></a></div>
                    <ul class="hotclass-name">
                        <li><span><a href="#">[招聘管理]</a></span><a href="#">如何制定招聘计划</a></li>
                        <li><span><a href="#">[职业规划]</a></span><a href="#">如何识别员工职业发展需求</a></li>
                        <li><span><a href="#">[关系管理]</a></span><a href="#">如何采纳员工合理化建议</a></li>
                        <li><span><a href="#">[离职管理]</a></span><a href="#">如何制定完善的辞退员工解决方案</a></li>
                        <li><span><a href="#">[绩效管理]</a></span><a href="#">如何确定考核方法</a></li>
                    </ul>
                </div>
                <a href="http://www.kuanxue.com/pl/webSite/platformCourse.do?catalogId=2" class="more">
                    更多人力资源课程》
                </a>
            </div>
            <div class="hotclass-item ">
                <div class="imgtxttop">
                    <div class="pic"><a href="http://www.kuanxue.com/pl/webSite/platformCourse.do?catalogId=7"><img
                            src="http://www.kuanxue.com/static/kuanxuev2/imgs/hotclass3.jpg" alt=""></a></div>
                    <ul class="hotclass-name">
                        <li><span><a href="#">[管理制度]</a></span><a href="#">如何制定产品销售管理制度</a></li>
                        <li><span><a href="#">[管理制度]</a></span><a href="#">如何制定价格管理制度</a></li>
                        <li><span><a href="#">[形象塑造]</a></span><a href="#">销售人员如何做到以诚待客</a></li>
                        <li><span><a href="#">[客户维护]</a></span><a href="#">如何处理好客户异议</a></li>
                        <li><span><a href="#">[关系管理]</a></span><a href="#">如何维护客户关系</a></li>
                    </ul>
                </div>
                <a href="http://www.kuanxue.com/pl/webSite/platformCourse.do?catalogId=7" class="more">
                    更多销售管理课程》
                </a>
            </div>
            <div class="hotclass-item ">
                <div class="imgtxttop">
                    <div class="pic"><a href="http://www.kuanxue.com/pl/webSite/platformCourse.do?catalogId=18"><img
                            src="http://www.kuanxue.com/static/kuanxuev2/imgs/hotclass4.jpg" alt=""></a></div>
                    <ul class="hotclass-name">
                        <li><span><a href="#">[营销执行]</a></span><a href="#">如何做好市场活动的前期工作</a></li>
                        <li><span><a href="#">[营销执行]</a></span><a href="#">如何做好市场活动的组织工作</a></li>
                        <li><span><a href="#">[试乘试驾]</a></span><a href="#">试乘试驾前管理要点</a></li>
                        <li><span><a href="#">[试乘试驾]</a></span><a href="#">试乘试驾中管理要点</a></li>
                        <li><span><a href="#">[事故理赔]</a></span><a href="#">如何做好双车事故处理</a></li>
                    </ul>
                </div>
                <a href="http://www.kuanxue.com/pl/webSite/platformCourse.do?catalogId=18" class="more">
                    更多汽车行业课程》
                </a>
            </div>
            <div class="hotclass-item ">
                <div class="imgtxttop">
                    <div class="pic"><a href="http://www.kuanxue.com/pl/webSite/platformCourse.do?catalogId=6"><img
                            src="http://www.kuanxue.com/static/kuanxuev2/imgs/hotclass5.jpg" alt=""></a></div>
                    <ul class="hotclass-name">
                        <li><span><a href="#">[回报提升]</a></span><a href="#">如何加速资金周转</a></li>
                        <li><span><a href="#">[资金管理]</a></span><a href="#">如何做好采购资金管理</a></li>
                        <li><span><a href="#">[资金管理]</a></span><a href="#">如何做好存货资金管理</a></li>
                        <li><span><a href="#">[资金管理]</a></span><a href="#">如何选择恰当的结算方式</a></li>
                        
                    </ul>
                </div>
                <a href="http://www.kuanxue.com/pl/webSite/platformCourse.do?catalogId=6" class="more">
                    更多财务课程》
                </a>
            </div>
            <div class="hotclass-item ">
                <div class="imgtxttop">
                    <div class="pic"><a href="http://www.kuanxue.com/pl/webSite/platformCourse.do?catalogId=19"><img
                            src="http://www.kuanxue.com/static/kuanxuev2/imgs/hotclass6.jpg" alt=""></a></div>
                    <ul class="hotclass-name">
                        <li><span><a href="#">[新店选址]</a></span><a href="#">如何制定选址策略</a></li>
                        <li><span><a href="#">[新店选址]</a></span><a href="#">如何做好店面租赁</a></li>
                        <li><span><a href="#">[新店选址]</a></span><a href="#">如何接手二手店</a></li>
                        
                        
                    </ul>
                </div>
                <a href="http://www.kuanxue.com/pl/webSite/platformCourse.do?catalogId=19" class="more">
                    更多餐饮课程》
                </a>
            </div>
        </div>
    </div>

    <!--更多课程 star-->
    <div class="col-class moreclassbg">
        <div style="padding-top: 10px;overflow: hidden;zoom:1">
            <h2 class="sidetitle hot-abside">
                更多课程
            </h2>
            <ul class="moreclass-list clearfix">
                
                    <li><a href="http://www.kuanxue.com/pl/webSite/platformCourse.do?catalogId=2" target="_blank">人力资源</a>

                        <div class="msg"><span class="l"></span><span class="r">共206门课</span><span
                                class="j-l"></span></div>
                    </li>
                
                    <li><a href="/pl/webSite/platformCourse.do?catalogId=3" target="_blank">自我管理</a>

                        <div class="msg"><span class="l"></span><span class="r">共53门课</span><span
                                class="j-l"></span></div>
                    </li>
                
                    <li><a href="/pl/webSite/platformCourse.do?catalogId=4" target="_blank">中层管理</a>

                        <div class="msg"><span class="l"></span><span class="r">共75门课</span><span
                                class="j-l"></span></div>
                    </li>
                
                    <li><a href="/pl/webSite/platformCourse.do?catalogId=5" target="_blank">行政管理</a>

                        <div class="msg"><span class="l"></span><span class="r">共63门课</span><span
                                class="j-l"></span></div>
                    </li>
                
                    <li><a href="/pl/webSite/platformCourse.do?catalogId=6" target="_blank">财务管理</a>

                        <div class="msg"><span class="l"></span><span class="r">共109门课</span><span
                                class="j-l"></span></div>
                    </li>
                
                    <li><a href="/pl/webSite/platformCourse.do?catalogId=7" target="_blank">销售管理</a>

                        <div class="msg"><span class="l"></span><span class="r">共126门课</span><span
                                class="j-l"></span></div>
                    </li>
                
                    <li><a href="/pl/webSite/platformCourse.do?catalogId=8" target="_blank">市场营销</a>

                        <div class="msg"><span class="l"></span><span class="r">共127门课</span><span
                                class="j-l"></span></div>
                    </li>
                
                    <li><a href="/pl/webSite/platformCourse.do?catalogId=9" target="_blank">客户服务</a>

                        <div class="msg"><span class="l"></span><span class="r">共90门课</span><span
                                class="j-l"></span></div>
                    </li>
                
                    <li><a href="/pl/webSite/platformCourse.do?catalogId=11" target="_blank">生产管理 </a>

                        <div class="msg"><span class="l"></span><span class="r">共192门课</span><span
                                class="j-l"></span></div>
                    </li>
                
                    <li><a href="/pl/webSite/platformCourse.do?catalogId=13" target="_blank">高层管理</a>

                        <div class="msg"><span class="l"></span><span class="r">共115门课</span><span
                                class="j-l"></span></div>
                    </li>
                
                    <li><a href="/pl/webSite/platformCourse.do?catalogId=14" target="_blank">公司金融</a>

                        <div class="msg"><span class="l"></span><span class="r">共0门课</span><span
                                class="j-l"></span></div>
                    </li>
                
                    <li><a href="/pl/webSite/platformCourse.do?catalogId=15" target="_blank">质量管理</a>

                        <div class="msg"><span class="l"></span><span class="r">共44门课</span><span
                                class="j-l"></span></div>
                    </li>
                
                    <li><a href="/pl/webSite/platformCourse.do?catalogId=16" target="_blank">采购管理</a>

                        <div class="msg"><span class="l"></span><span class="r">共39门课</span><span
                                class="j-l"></span></div>
                    </li>
                
                    <li><a href="/pl/webSite/platformCourse.do?catalogId=17" target="_blank">物流仓储</a>

                        <div class="msg"><span class="l"></span><span class="r">共68门课</span><span
                                class="j-l"></span></div>
                    </li>
                
                    <li><a href="/pl/webSite/platformCourse.do?catalogId=18" target="_blank">汽车行业</a>

                        <div class="msg"><span class="l"></span><span class="r">共110门课</span><span
                                class="j-l"></span></div>
                    </li>
                
                    <li><a href="/pl/webSite/platformCourse.do?catalogId=19" target="_blank">餐饮管理</a>

                        <div class="msg"><span class="l"></span><span class="r">共93门课</span><span
                                class="j-l"></span></div>
                    </li>
                
                    <li><a href="/pl/webSite/platformCourse.do?catalogId=28" target="_blank">房地产行业</a>

                        <div class="msg"><span class="l"></span><span class="r">共76门课</span><span
                                class="j-l"></span></div>
                    </li>
                
                    <li><a href="/pl/webSite/platformCourse.do?catalogId=29" target="_blank">保险营销</a>

                        <div class="msg"><span class="l"></span><span class="r">共90门课</span><span
                                class="j-l"></span></div>
                    </li>
                
                    <li><a href="/pl/webSite/platformCourse.do?catalogId=30" target="_blank">酒店管理</a>

                        <div class="msg"><span class="l"></span><span class="r">共84门课</span><span
                                class="j-l"></span></div>
                    </li>
                
                    <li><a href="/pl/webSite/platformCourse.do?catalogId=31" target="_blank">供应链管理</a>

                        <div class="msg"><span class="l"></span><span class="r">共19门课</span><span
                                class="j-l"></span></div>
                    </li>
                
                    <li><a href="/pl/webSite/platformCourse.do?catalogId=32" target="_blank">电销管理</a>

                        <div class="msg"><span class="l"></span><span class="r">共90门课</span><span
                                class="j-l"></span></div>
                    </li>
                
                    <li><a href="/pl/webSite/platformCourse.do?catalogId=33" target="_blank">创业管理</a>

                        <div class="msg"><span class="l"></span><span class="r">共0门课</span><span
                                class="j-l"></span></div>
                    </li>
                
                    <li><a href="/pl/webSite/platformCourse.do?catalogId=34" target="_blank">班组长管理</a>

                        <div class="msg"><span class="l"></span><span class="r">共50门课</span><span
                                class="j-l"></span></div>
                    </li>
                
                    <li><a href="/pl/webSite/platformCourse.do?catalogId=35" target="_blank">连锁经营</a>

                        <div class="msg"><span class="l"></span><span class="r">共54门课</span><span
                                class="j-l"></span></div>
                    </li>
                
                    <li><a href="/pl/webSite/platformCourse.do?catalogId=36" target="_blank">商业银行</a>

                        <div class="msg"><span class="l"></span><span class="r">共47门课</span><span
                                class="j-l"></span></div>
                    </li>
                
                    <li><a href="/pl/webSite/platformCourse.do?catalogId=37" target="_blank">医药管理</a>

                        <div class="msg"><span class="l"></span><span class="r">共4门课</span><span
                                class="j-l"></span></div>
                    </li>
                
                    <li><a href="/pl/webSite/platformCourse.do?catalogId=38" target="_blank">经销商</a>

                        <div class="msg"><span class="l"></span><span class="r">共2门课</span><span
                                class="j-l"></span></div>
                    </li>
                
                    <li><a href="/pl/webSite/platformCourse.do?catalogId=39" target="_blank">旅行社管理</a>

                        <div class="msg"><span class="l"></span><span class="r">共0门课</span><span
                                class="j-l"></span></div>
                    </li>
                
                    <li><a href="/pl/webSite/platformCourse.do?catalogId=42" target="_blank">物业管理</a>

                        <div class="msg"><span class="l"></span><span class="r">共44门课</span><span
                                class="j-l"></span></div>
                    </li>
                
                    <li><a href="/pl/webSite/platformCourse.do?catalogId=43" target="_blank">公司法务</a>

                        <div class="msg"><span class="l"></span><span class="r">共41门课</span><span
                                class="j-l"></span></div>
                    </li>
                
                    <li><a href="/pl/webSite/platformCourse.do?catalogId=44" target="_blank">外贸管理</a>

                        <div class="msg"><span class="l"></span><span class="r">共21门课</span><span
                                class="j-l"></span></div>
                    </li>
                
                    <li><a href="/pl/webSite/platformCourse.do?catalogId=45" target="_blank">研发管理</a>

                        <div class="msg"><span class="l"></span><span class="r">共7门课</span><span
                                class="j-l"></span></div>
                    </li>
                
            </ul>
        </div>
    </div>
    <script type="text/javascript">
        $(".moreclass-list li").hover(function() {
            $(this).find(".msg").show();
        }, function() {
            $(this).find(".msg").hide();
        })
    </script>

</div>
<div class="rightsidebar">
    <!--公告  star -->
    <div class="rcol-panel">
        <div class="hd">
            <h2>新闻公告</h2>
        </div>
        <div class="bd">
            <ul class="k-list">
                
                    <li><a href="/pl/webSite/newsInfo.html?id=96" target="_blank" title="">到底什么样的e-Learning培训能够在中国获得普及？</a>
                    </li>
                
                    <li><a href="/pl/webSite/newsInfo.html?id=95" target="_blank" title="">易学宝与上海张江药谷展开合作</a>
                    </li>
                
                    <li><a href="/pl/webSite/newsInfo.html?id=91" target="_blank" title="">企业对新员工培训注意事项</a>
                    </li>
                
                    <li><a href="/pl/webSite/newsInfo.html?id=90" target="_blank" title="">企业如何做好员工培训</a>
                    </li>
                
            </ul>
        </div>
    </div>
    <!--专家团  star -->
    <div class="rcol-panel teacher">
        <div class="hd">
            <h2>专家团</h2>
        </div>
        <div class="bd">
            <div class="scrollteacher">
                <ul class="people-list">
                    
                        <li>
                            <a href="javascript:void(0)" class="pic"><img src="http://www.kuanxue.com/data/image/1308211419339151.jpg" width="48" height="56"></a>
                            <h4><a href="javascript:void(0)">李俊</a></h4>

                            <p class="txtinfo">爱房网 <br> 人力资源管理、自我管理</p>
                                
                        </li>

                        <div class="teacher-infocontent" style="display:none">
                                
                                
                                
                            <div class="bd">
                                <div class="topinfo">
                                    <dl>
                                        <dt><img src="http://www.kuanxue.com/data/image/1308211419339151.jpg" alt="" width="108" height="132"></dt>
                                        <dd class="name">李俊</dd>
                                        <dd class="item-list">职位：人力资源总监</dd>
                                        <dd class="item-list">公司：爱房网</dd>
                                        <dd class="item-list">擅长：人力资源管理、自我管理</dd>
                                    </dl>
                                </div>
                                <div class="bottomcontent">
                                           李俊老师是重庆大学工商管理硕士、二级心理咨询师、高级人力资源管理师。作为资深人力资源实务专家，他有着十八年职业生涯，超过十五年的培训及管理咨询经验，超过十年的人力资源管理行政管理经验。包括房地产上市企业的丰富管理经验，同时担任过深圳拓普里德的特约培训师、博格教育机构的首席培训师及永信管理咨询公司的企业管理咨询师，培训人数超过万人。李老师还是江西省人力资源中心的特约讲师。
                                </div>
                            </div>
                        </div>
                    
                        <li>
                            <a href="javascript:void(0)" class="pic"><img src="http://www.kuanxue.com/data/image/1304191233017991.jpg" width="48" height="56"></a>
                            <h4><a href="javascript:void(0)">易少伟</a></h4>

                            <p class="txtinfo">中商盛世企业管理咨询公司 <br> 人力资源、战略决策、营销策划</p>
                                
                        </li>

                        <div class="teacher-infocontent" style="display:none">
                                
                                
                                
                            <div class="bd">
                                <div class="topinfo">
                                    <dl>
                                        <dt><img src="http://www.kuanxue.com/data/image/1304191233017991.jpg" alt="" width="108" height="132"></dt>
                                        <dd class="name">易少伟</dd>
                                        <dd class="item-list">职位：总经理</dd>
                                        <dd class="item-list">公司：中商盛世企业管理咨询公司</dd>
                                        <dd class="item-list">擅长：人力资源、战略决策、营销策划</dd>
                                    </dl>
                                </div>
                                <div class="bottomcontent">
                                            “才富中国”人力资源研究院院长/首席咨询师；高级人力资源师；高级培训师；中国房地产业协会备案讲师；多家知名企业的高级特聘管理顾问。
    易少伟具有在大型国企、私企、外企近二十年的经营管理工作经验，涉及“冶金钢铁、房地产（含物业）、快速消费品、商业贸易、物流快运及培训咨询”等行业，积累了丰富的管理经验，经过长期的实践和探索，逐渐形成了其独具特色的实用型经营管理理念。
    易少伟通过长期的经营管理实践，对人力资源、战略决策、营销策划等进行了深入研究和探索，形成了其独特的自成体系的咨询模式，是非常务实的培训咨询专家，先后为数十家企业提供过专业的人力资源、战略整合咨询以及市场营销策划服务，为客户的成长发展作出了卓越的贡献。

                                </div>
                            </div>
                        </div>
                    
                        <li>
                            <a href="javascript:void(0)" class="pic"><img src="http://www.kuanxue.com/data/image/1311140951545941.jpg" width="48" height="56"></a>
                            <h4><a href="javascript:void(0)">宋智宇</a></h4>

                            <p class="txtinfo">西安慧百企业管理咨询有限公司 <br> 人力资源管理、通用管理</p>
                                
                        </li>

                        <div class="teacher-infocontent" style="display:none">
                                
                                
                                
                            <div class="bd">
                                <div class="topinfo">
                                    <dl>
                                        <dt><img src="http://www.kuanxue.com/data/image/1311140951545941.jpg" alt="" width="108" height="132"></dt>
                                        <dd class="name">宋智宇</dd>
                                        <dd class="item-list">职位：公司合伙人  高级咨询顾问</dd>
                                        <dd class="item-list">公司：西安慧百企业管理咨询有限公司</dd>
                                        <dd class="item-list">擅长：人力资源管理、通用管理</dd>
                                    </dl>
                                </div>
                                <div class="bottomcontent">
                                        宋老师曾在国内十强乳业集团——西安银桥乳业集团任人力资源总监多年。对于企业组织设计、组织管理流程再造、人力资源薪酬与绩效体系建设与实施、人资法务管理等有独到的见解。她曾为连锁销售、渠道销售、快消品流通等企业；公共交通、水务等公共服务企业；以及农资、石油勘探等行业提供企业管理咨询。作为宽学网管理领域的规划与品控顾问，宋老师已将她丰富的人力资源及企业管理理念融入到宽学网的课程中。她希望200门人力资源及管理课程及其包含的600个小案例，不仅能让学员学到世界一流的管理理念与方法，也能够解决学员日常管理工作中所遇到的90%以上的问题，让学员从别人的管理失败中吸取教训，少走弯路，直达成功。
                                </div>
                            </div>
                        </div>
                    
                        <li>
                            <a href="javascript:void(0)" class="pic"><img src="http://www.kuanxue.com/data/image/1206190918124751.jpg" width="48" height="56"></a>
                            <h4><a href="javascript:void(0)">Fincher Fu</a></h4>

                            <p class="txtinfo">Capgemini Consulting <br> 供应链管理</p>
                                
                        </li>

                        <div class="teacher-infocontent" style="display:none">
                                
                                
                                
                            <div class="bd">
                                <div class="topinfo">
                                    <dl>
                                        <dt><img src="http://www.kuanxue.com/data/image/1206190918124751.jpg" alt="" width="108" height="132"></dt>
                                        <dd class="name">Fincher Fu</dd>
                                        <dd class="item-list">职位：咨询顾问</dd>
                                        <dd class="item-list">公司：Capgemini Consulting</dd>
                                        <dd class="item-list">擅长：供应链管理</dd>
                                    </dl>
                                </div>
                                <div class="bottomcontent">
                                        Fincher历任可口可乐 Coca-Cola供应链经理，达能 DANONE 供应链经理，3M供应链主管，朗讯 Lucent供应链分析师等职位。作为宽学网供应链领域课程规划与品控顾问，Fincher已经将他的丰富的供应链管理经验融入到宽学网供应链管理领域课程的开发中。Fincher希望首期规划的200门供应链管理课程及其包含的600个供应链小案例，不仅能让学员学到世界一流的供应链管理理念与方法，也能够解决学员日常供应链管理工作中所遇到的90%以上的问题，让学员从别人的供应链失败案例中吸取教训，少走弯路，直达成功。
                                </div>
                            </div>
                        </div>
                    
                        <li>
                            <a href="javascript:void(0)" class="pic"><img src="http://www.kuanxue.com/data/image/1206181737437271.jpg" width="48" height="56"></a>
                            <h4><a href="javascript:void(0)">Vincent Zhang </a></h4>

                            <p class="txtinfo">前Intel半导体（大连） <br> 生产管理</p>
                                
                        </li>

                        <div class="teacher-infocontent" style="display:none">
                                
                                
                                
                            <div class="bd">
                                <div class="topinfo">
                                    <dl>
                                        <dt><img src="http://www.kuanxue.com/data/image/1206181737437271.jpg" alt="" width="108" height="132"></dt>
                                        <dd class="name">Vincent Zhang </dd>
                                        <dd class="item-list">职位：生产计划总监</dd>
                                        <dd class="item-list">公司：前Intel半导体（大连）</dd>
                                        <dd class="item-list">擅长：生产管理</dd>
                                    </dl>
                                </div>
                                <div class="bottomcontent">
                                        Vincent拥有19年外企生产管理经验，其中16年在Intel供职。Vincent全程参与了宽学网的生产领域的课程规划和部分课程的开发。作为宽学网生产领域课程规划与品控顾问，Vincent希望首期规划的400门生产管理课程及其包含的1200个生产小案例，不仅能让学员学到世界一流的生产管理理念与方法，也能够解决学员日常生产管理工作中所遇到的90%以上的问题，让学员从别人的生产失败案例中吸取教训，少走弯路，直达成功。 
                                </div>
                            </div>
                        </div>
                    
                        <li>
                            <a href="javascript:void(0)" class="pic"><img src="http://www.kuanxue.com/data/image/1206181741492611.jpg" width="48" height="56"></a>
                            <h4><a href="javascript:void(0)">Wen Chang Chen</a></h4>

                            <p class="txtinfo">帕威斯管理咨询（北京） <br> 仓储管理</p>
                                
                        </li>

                        <div class="teacher-infocontent" style="display:none">
                                
                                
                                
                            <div class="bd">
                                <div class="topinfo">
                                    <dl>
                                        <dt><img src="http://www.kuanxue.com/data/image/1206181741492611.jpg" alt="" width="108" height="132"></dt>
                                        <dd class="name">Wen Chang Chen</dd>
                                        <dd class="item-list">职位：总经理</dd>
                                        <dd class="item-list">公司：帕威斯管理咨询（北京）</dd>
                                        <dd class="item-list">擅长：仓储管理</dd>
                                    </dl>
                                </div>
                                <div class="bottomcontent">
                                        陈先生拥有10多年仓储管理经验，历任和记黄埔、中信国际等多家国际巨头物流总监和运营总监。陈先生拥有经济学博士学位，也是国际物流与运输学会（CILT International）注册会员。作为宽学网仓储领域课程规划与品控顾问，陈先生全程参与了宽学网的仓储领域的课程规划和部分课程的开发。陈先生希望首期规划的100门仓储管理课程及其包含的300个采购小案例，不仅能让学员学到世界一流的仓储管理理念与方法，也能够解决学员日常仓储管理工作中所遇到的90%以上的问题，让学员从别人的仓储管理失败案例中吸取教训，少走弯路，直达成功。
                                </div>
                            </div>
                        </div>
                    
                        <li>
                            <a href="javascript:void(0)" class="pic"><img src="http://www.kuanxue.com/data/image/1206190859262711.jpg" width="48" height="56"></a>
                            <h4><a href="javascript:void(0)">Xu Bao</a></h4>

                            <p class="txtinfo">Toll Global Logistics <br> 物流管理</p>
                                
                        </li>

                        <div class="teacher-infocontent" style="display:none">
                                
                                
                                
                            <div class="bd">
                                <div class="topinfo">
                                    <dl>
                                        <dt><img src="http://www.kuanxue.com/data/image/1206190859262711.jpg" alt="" width="108" height="132"></dt>
                                        <dd class="name">Xu Bao</dd>
                                        <dd class="item-list">职位：大中国区副总经理</dd>
                                        <dd class="item-list">公司：Toll Global Logistics</dd>
                                        <dd class="item-list">擅长：物流管理</dd>
                                    </dl>
                                </div>
                                <div class="bottomcontent">
                                        徐先生拥有12年物流行业经验、以及10年公司全面管理经验。曾先后在P&G中国公司担任销售经理、马士基物流担任总经理。并拥有美国交通运输协会颁发的“专业物流师资质”。作为宽学网物流领域课程规划与品控顾问，徐先生已经将他的丰富的物流管理经验融入到宽学网物流管理领域课程的开发中。徐先生希望首期规划的100门物流管理课程及其包含的300个采购小案例，不仅能让学员学到世界一流的物流管理理念与方法，也能够解决学员日常物流管理工作中所遇到的90%以上的问题，让学员从别人的物流管理失败案例中吸取教训，少走弯路，直达成功。
                                </div>
                            </div>
                        </div>
                    
                        <li>
                            <a href="javascript:void(0)" class="pic"><img src="http://www.kuanxue.com/data/image/1206181745053561.jpg" width="48" height="56"></a>
                            <h4><a href="javascript:void(0)">Wanwan Zhang</a></h4>

                            <p class="txtinfo">陶氏化学 <br> 采购管理</p>
                                
                        </li>

                        <div class="teacher-infocontent" style="display:none">
                                
                                
                                
                            <div class="bd">
                                <div class="topinfo">
                                    <dl>
                                        <dt><img src="http://www.kuanxue.com/data/image/1206181745053561.jpg" alt="" width="108" height="132"></dt>
                                        <dd class="name">Wanwan Zhang</dd>
                                        <dd class="item-list">职位：亚太区采购经理</dd>
                                        <dd class="item-list">公司：陶氏化学</dd>
                                        <dd class="item-list">擅长：采购管理</dd>
                                    </dl>
                                </div>
                                <div class="bottomcontent">
                                        Wanwan 拥有10年采购和供应商管理工作经验，曾先后在 Volvo, Honeywell 等企业担任原材料、汽车零件、间接材料以及行政采购的职位，有丰富的全球采购、项目和团队管理经验。作为宽学网采购领域课程规划与品控顾问，Wanwan已经将他的丰富的采购管理经验融入到宽学网采购管理领域课程的开发中。Wanwan希望首期规划的100门采购管理课程及其包含的300个采购小案例，不仅能让学员学到世界一流的采购管理理念与方法，也能够解决学员日常采购管理工作中所遇到的90%以上的问题，让学员从别人的市采购失败案例中吸取教训，少走弯路，直达成功。
                                </div>
                            </div>
                        </div>
                    
                        <li>
                            <a href="javascript:void(0)" class="pic"><img src="http://www.kuanxue.com/data/image/1206190856576901.jpg" width="48" height="56"></a>
                            <h4><a href="javascript:void(0)">Gavin Wang</a></h4>

                            <p class="txtinfo">Dell <br> 采购管理</p>
                                
                        </li>

                        <div class="teacher-infocontent" style="display:none">
                                
                                
                                
                            <div class="bd">
                                <div class="topinfo">
                                    <dl>
                                        <dt><img src="http://www.kuanxue.com/data/image/1206190856576901.jpg" alt="" width="108" height="132"></dt>
                                        <dd class="name">Gavin Wang</dd>
                                        <dd class="item-list">职位：高级采购经理</dd>
                                        <dd class="item-list">公司：Dell</dd>
                                        <dd class="item-list">擅长：采购管理</dd>
                                    </dl>
                                </div>
                                <div class="bottomcontent">
                                        Gavin 拥有10多年外企工作经验，历任IBM咨询运营战略部咨询经理，全球领先SCM（supply chain management）解决方案公司Manhattan Associates高级顾问等职。作为宽学网采购领域课程规划与品控顾问，Gavin已经将他的丰富的采购管理经验融入到宽学网采购管理领域课程的开发中。 Gavin希望首期规划的100门采购管理课程及其包含的300个采购小案例，不仅能让学员学到世界一流的采购管理理念与方法，也能够解决学员日常采购管理工作中所遇到的90%以上的问题，让学员从别人的市采购失败案例中吸取教训，少走弯路，直达成功。
                                </div>
                            </div>
                        </div>
                    
                        <li>
                            <a href="javascript:void(0)" class="pic"><img src="http://www.kuanxue.com/data/image/1206190854424851.jpg" width="48" height="56"></a>
                            <h4><a href="javascript:void(0)">Chris Wang</a></h4>

                            <p class="txtinfo">辉瑞营养品事业部 <br> 市场营销</p>
                                
                        </li>

                        <div class="teacher-infocontent" style="display:none">
                                
                                
                                
                            <div class="bd">
                                <div class="topinfo">
                                    <dl>
                                        <dt><img src="http://www.kuanxue.com/data/image/1206190854424851.jpg" alt="" width="108" height="132"></dt>
                                        <dd class="name">Chris Wang</dd>
                                        <dd class="item-list">职位：市场总监</dd>
                                        <dd class="item-list">公司：辉瑞营养品事业部</dd>
                                        <dd class="item-list">擅长：市场营销</dd>
                                    </dl>
                                </div>
                                <div class="bottomcontent">
                                        Chris先后就职于中国特恩斯市场研究咨询有限公司（TNS），在妮维雅（Nivea）担任过产品经理，在惠氏制药（Wyeth Pharmaceuticals）担任商业策划和市场研究经理，市场研究部副总监等职。作为宽学网市场营销领域课程规划与品控顾问，Chris已经将他的市场能力融入到宽学网市场营销领域课程的开发中。Chris希望首期规划的400门市场营销管理课程及其包含的1200个市场营销小案例，不仅能让学员学到世界一流的市场营销管理理念与方法，也能够解决学员日常市场营销管理工作中所遇到的90%以上的问题，让学员从别人的市场营销失败案例中吸取教训，少走弯路，直达成功。
                                </div>
                            </div>
                        </div>
                    
                        <li>
                            <a href="javascript:void(0)" class="pic"><img src="http://www.kuanxue.com/data/image/1206190843492671.jpg" width="48" height="56"></a>
                            <h4><a href="javascript:void(0)">Sophia Wang </a></h4>

                            <p class="txtinfo">前美国铝业 <br> 人力资源管理</p>
                                
                        </li>

                        <div class="teacher-infocontent" style="display:none">
                                
                                
                                
                            <div class="bd">
                                <div class="topinfo">
                                    <dl>
                                        <dt><img src="http://www.kuanxue.com/data/image/1206190843492671.jpg" alt="" width="108" height="132"></dt>
                                        <dd class="name">Sophia Wang </dd>
                                        <dd class="item-list">职位：人力资源高级经理</dd>
                                        <dd class="item-list">公司：前美国铝业</dd>
                                        <dd class="item-list">擅长：人力资源管理</dd>
                                    </dl>
                                </div>
                                <div class="bottomcontent">
                                        Sophia 曾经在美国铝业、辉瑞制药等多家著名外企担任人力资源与组织发展高级管理职务。作为宽学网人力资源领域课程规划与品控顾问，Sophia已经将她的丰富的人力资源管理经验融入到宽学网人力资源领域课程的开发中。Sophia希望首期规划的200门人力资源管理课程及其包含的600个人力资源小案例，不仅能让学员学到世界一流的人力资源管理理念与方法，也能够解决学员日常人力资源管理工作中所遇到的90%以上的问题，让学员从别人的人力资源失败案例中吸取教训，少走弯路，直达成功。
                                </div>
                            </div>
                        </div>
                    
                        <li>
                            <a href="javascript:void(0)" class="pic"><img src="http://www.kuanxue.com/data/image/1206190837236401.jpg" width="48" height="56"></a>
                            <h4><a href="javascript:void(0)">Jack Bao</a></h4>

                            <p class="txtinfo">欧洲顶级私募基金 <br> 投融资管理</p>
                                
                        </li>

                        <div class="teacher-infocontent" style="display:none">
                                
                                
                                
                            <div class="bd">
                                <div class="topinfo">
                                    <dl>
                                        <dt><img src="http://www.kuanxue.com/data/image/1206190837236401.jpg" alt="" width="108" height="132"></dt>
                                        <dd class="name">Jack Bao</dd>
                                        <dd class="item-list">职位：总经理兼合伙人</dd>
                                        <dd class="item-list">公司：欧洲顶级私募基金</dd>
                                        <dd class="item-list">擅长：投融资管理</dd>
                                    </dl>
                                </div>
                                <div class="bottomcontent">
                                        Jack历任Diageo全球商业决策支持方向的合伙人、可口可乐中国公司战略与计划经理。作为宽学网公司金融领域课程规划与品控顾问，Jack已经将他的丰富的企业投融资经验融入到宽学网公司金融领域课程的开发中。Jack希望首期规划的150门公司金融管理课程及其包含的450个投融资小案例，不仅能让学员学到世界一流的投融资管理理念与方法，也能够解决学员日常投融资管理工作中所遇到的90%以上的问题，让学员从别人的投融资失败案例中吸取教训，少走弯路，直达成功。
                                </div>
                            </div>
                        </div>
                    
                        <li>
                            <a href="javascript:void(0)" class="pic"><img src="http://www.kuanxue.com/data/image/1206181701120191.jpg" width="48" height="56"></a>
                            <h4><a href="javascript:void(0)">Shirley Zhao</a></h4>

                            <p class="txtinfo">Secom Telecom <br> 财务控制</p>
                                
                        </li>

                        <div class="teacher-infocontent" style="display:none">
                                
                                
                                
                            <div class="bd">
                                <div class="topinfo">
                                    <dl>
                                        <dt><img src="http://www.kuanxue.com/data/image/1206181701120191.jpg" alt="" width="108" height="132"></dt>
                                        <dd class="name">Shirley Zhao</dd>
                                        <dd class="item-list">职位：首席财务官</dd>
                                        <dd class="item-list">公司：Secom Telecom</dd>
                                        <dd class="item-list">擅长：财务控制</dd>
                                    </dl>
                                </div>
                                <div class="bottomcontent">
                                        Shirley在财务会计管理方面经验资深,她最早在联合利华（Unilever）担任过商业经理。之后，曾先后担任英美烟草（British American Tobacco）的公司财务经理，著名制药公司阿特维斯(Alpharma/Actavis ) 的财务/ IT 总监。这之后，赵女士在法国广告传播阳狮集团(Publicis Groupe) 任首席会计官。作为宽学网财务领域课程规划与品控顾问， Shirley已经将她的财务控制经验，很好地融入到宽学网的财务领域课程开发中。Shirley 希望首期规划的200门财务管理课程及其包含的600个财务小案例，不仅能让学员学到世界一流的财务管理理念与方法，也能够解决学员日常财务管理工作中所遇到的90%以上的问题，让学员从别人的财务管理失败案例中吸取教训，少走弯路，直达成功。
                                </div>
                            </div>
                        </div>
                    
                        <li>
                            <a href="javascript:void(0)" class="pic"><img src="http://www.kuanxue.com/data/image/1206190847292361.jpg" width="48" height="56"></a>
                            <h4><a href="javascript:void(0)">Janet Cao</a></h4>

                            <p class="txtinfo">联合利华 <br> 客户服务管理</p>
                                
                        </li>

                        <div class="teacher-infocontent" style="display:none">
                                
                                
                                
                            <div class="bd">
                                <div class="topinfo">
                                    <dl>
                                        <dt><img src="http://www.kuanxue.com/data/image/1206190847292361.jpg" alt="" width="108" height="132"></dt>
                                        <dd class="name">Janet Cao</dd>
                                        <dd class="item-list">职位：高级客户经理</dd>
                                        <dd class="item-list">公司：联合利华</dd>
                                        <dd class="item-list">擅长：客户服务管理</dd>
                                    </dl>
                                </div>
                                <div class="bottomcontent">
                                        Janet从事客户关系管理工作15年，对于建立CRM 系统、提高客服中心的服务质量以及培训管理客服工作人员有着独到的见解。作为宽学网客服领域课程规划与品控顾问，Janet已经将她的丰富的客户服务经验融入到宽学网客户服务领域课程的开发中。Janet希望首期规划的200门客服管理课程及其包含的600个客服小案例，不仅能让学员学到世界一流的客户服务管理理念与方法，也能够解决学员日常客户服务管理工作中所遇到的90%以上的问题，让学员从别人的客户服务失败案例中吸取教训，少走弯路，直达成功。
                                </div>
                            </div>
                        </div>
                    
                        <li>
                            <a href="javascript:void(0)" class="pic"><img src="http://www.kuanxue.com/data/image/1206181711490331.jpg" width="48" height="56"></a>
                            <h4><a href="javascript:void(0)">Estella Tong</a></h4>

                            <p class="txtinfo">莱珀妮 <br> 财务管理</p>
                                
                        </li>

                        <div class="teacher-infocontent" style="display:none">
                                
                                
                                
                            <div class="bd">
                                <div class="topinfo">
                                    <dl>
                                        <dt><img src="http://www.kuanxue.com/data/image/1206181711490331.jpg" alt="" width="108" height="132"></dt>
                                        <dd class="name">Estella Tong</dd>
                                        <dd class="item-list">职位：亚太区财务总监</dd>
                                        <dd class="item-list">公司：莱珀妮</dd>
                                        <dd class="item-list">擅长：财务管理</dd>
                                    </dl>
                                </div>
                                <div class="bottomcontent">
                                        Estella有着17年的外企财务管理经验，历任联合利华、雅芳高级财务经理。作为宽学网财务领域课程规划与品控顾问，Estella已经将她的中西合璧的财务管理经验，很好地融入到宽学网的财务领域课程开发中。Estella 希望首期规划的200门财务管理课程及其包含的600个财务小案例，不仅能让学员学到世界一流的财务管理理念与方法，也能够解决学员日常财务管理工作中所遇到的90%以上的问题，让学员从别人的财务管理失败案例中吸取教训，少走弯路，直达成功。
                                </div>
                            </div>
                        </div>
                    
                        <li>
                            <a href="javascript:void(0)" class="pic"><img src="http://www.kuanxue.com/data/image/1206190851235371.jpg" width="48" height="56"></a>
                            <h4><a href="javascript:void(0)">David Zhang </a></h4>

                            <p class="txtinfo">辉瑞制药 <br> 销售管理</p>
                                
                        </li>

                        <div class="teacher-infocontent" style="display:none">
                                
                                
                                
                            <div class="bd">
                                <div class="topinfo">
                                    <dl>
                                        <dt><img src="http://www.kuanxue.com/data/image/1206190851235371.jpg" alt="" width="108" height="132"></dt>
                                        <dd class="name">David Zhang </dd>
                                        <dd class="item-list">职位：高级运营总监</dd>
                                        <dd class="item-list">公司：辉瑞制药</dd>
                                        <dd class="item-list">擅长：销售管理</dd>
                                    </dl>
                                </div>
                                <div class="bottomcontent">
                                        David作为辉瑞制药高级运营总监，有着丰富的销售管理经验。作为宽学网销售领域课程规划与品控顾问，David  已经将他的轻松驾驭市场的能力融入到宽学网销售管理领域课程的开发中。David希望首期规划的300门销售管理课程及其包含的900个销售管理小案例，不仅能让学员学到世界一流的销售管理理念与方法，也能够解决学员日常销售管理工作中所遇到的90%以上的问题，让学员从别人的销售管理失败案例中吸取教训，少走弯路，直达成功。
                                </div>
                            </div>
                        </div>
                    
                </ul>
            </div>
        </div>
    </div>
    <div class="r-g"><a href="/pl/site/registerList.do" target="_blank"> <img src="http://www.kuanxue.com/static/kuanxuev2/imgs/gg1.jpg"
                                                                              alt=""></a></div>
    <div class="r-g pd10"><a href="" target="_blank" class="_adv"> <img src="http://www.kuanxue.com/static/kuanxuev2/imgs/gg2.jpg" alt="">

        <div class="player"></div>
    </a>

    </div>
    <div id='_dialog' style="display:none;" class="teacher-infocontent">

    </div>

</div>
</div>
<!--合作机构 star-->
<div class="friend-box">
    <h2>合作机构<span class="triangle"></span></h2>

    <div class="friendlist">
        <a href="javascript:void(0)"><img src="http://www.kuanxue.com/static/company/imgs/qinghua.jpg" style="width:120px;height:50px;"/></a>
        <a href="javascript:void(0)"><img src="http://www.kuanxue.com/static/edp/imgs/hichina.png" style="width:120px;height:50px; "/></a>
        <a href="javascript:void(0)"><img src="http://www.kuanxue.com/static/edp/imgs/fddx.png" style="width:138px;height:50px; "/></a>
        <a href="javascript:void(0)"><img src="http://www.kuanxue.com/static/edp/imgs/rjdx.png" style="width:169px;height:43px; "/></a>
        <a href="javascript:void(0)"><img src="http://www.kuanxue.com/static/edp/imgs/aliyun.png" style="width:140px;height:46px; "/></a>
        </ul>
    </div>
</div>

<!--友情链接 star-->
<div class=" link-box">
    <span>友情链接：</span>
    <a href="http://www.tsinghua.edu.cn" target="_blank">清华大学</a><a href="http://www.net.cn/" target="_blank">万网</a>
    <a href="http://www.001jm.com" target="_blank">加盟网</a>
      <a href="http://www.tshi.com.cn/" target="_blank">淘师网</a>
</div>

</div>

</div>
</div>
<div class="_adivs" style="display:none"></div>
<script type="text/javascript">
    $(function() {
        // 焦点图
        $(".banner").slideShow();
        $("._adv").click(function() {

            $(this).get(0).href = "/pl/kuanxueAdv.do";
//            var promptHtml =
//                    "<div  style='overflow:hidden;'>" +
//                            "<iframe class='ifr' src ='/data/course/kxMinPlayer/kxMinPlayer.html?filename=/data/flv/adv.swf' frameborder=no border=0 framespacing=0 width=935 height=500 marginheight=0px>"+
//                            "<p>你的浏览器不支持iframe</p></iframe>"+
////                            "<p>登录后可观看完整版</p>" +
//                    "</div>";
//                       $css("height",550);
//                   $("._adivs").html(promptHtml).dialog({
//                close:function(){
////
//                $("._adivs").html("");
//                    },
//                   "height":570,
//                    "width":980,
//                     resizable:false
//        });

        });

    });
    $(".item-pic").each(function(i) {
        $(this).click(function() {
            $("#_dialog").html($(".teacher-box .teacher-infocontent").eq(i).html());
            $("#_dialog").dialog(
                    {
                        width: 580,
                        height: 480,
                        modal: true
                    }
            )
        });
    });
    $("a:.name").each(function(i) {
        $(this).click(function() {
            $("#_dialog").html($(".teacher-box .teacher-infocontent").eq(i).html());
            $("#_dialog").dialog(
                    {
                        width: 580,
                        height: 480,
                        modal: true
                    }
            )
        });
    });

</script>
<script type="text/javascript">
    $(document).ready(function() {
        $(".col-class .hotclass-item ").hover(function() {
            $(this).addClass("current")
            $(this).find(".more").show()
        }, function() {
            $(this).removeClass("current")
            $(this).find(".more").hide()
        });

        $(".col-class-list li .videobox").hover(function() {
            $(this).addClass("hover")
        }, function() {
            $(this).removeClass("hover")
        });
        $(".player").hover(function() {
            $(this).animate({opacity:1}, 500)
        }, function() {
            $(this).animate({opacity:0.6}, 500)
        });
        $(".hotclass-name span a").each(function(i) {
            var str = $(".hotclass-name span + a").eq(i).html();
            $(this).click(function() {
                window.open("/pl/webSite/goClass.do?name=" + encodeURI(str));
            });
            $(this).parent().siblings("a").click(function() {
                window.open("/pl/webSite/goCourse.do?name=" + encodeURI(str));
            });
//           alert(str);
        });

        $(".people-list .pic").each(function(i) {

            $(this).click(function() {

                $("#_dialog").html($(".teacher-infocontent").eq(i).html());
                $("#_dialog").dialog(
                        {
                            width: 580,
                            height: 480,
                            modal: true
                        });
            });
            $(this).next().children("a").click(function() {
                $("#_dialog").html($(".teacher-infocontent").eq(i).html());
                $("#_dialog").dialog(
                        {
                            width: 580,
                            height: 480,
                            modal: true
                        })
            });

        });
    });
</script>
<script type="text/javascript">
    var oZrkWbBox = $('.teacher'),
            oZrkWb = $('.scrollteacher'),
            oZrkBox = oZrkWb.find('.people-list'),
            aDl = oZrkBox.find('li'),
            iDlLen = aDl.size(),
            zrkTimer = null,
            height = 0;


    zrkTimer = setInterval(function() {
        var lastDl = oZrkBox.find('li:last');
        height = lastDl.outerHeight();
        oZrkBox.prepend(lastDl).css({"top":-height + "px"}).stop().animate({"top":"0px"}, 1000);
    }, 3000);


    oZrkWbBox.hover(function() {
        clearInterval(zrkTimer);
    }, function() {
        zrkTimer = setInterval(function() {
            var lastDl = oZrkBox.find('li:last'),
                    height = lastDl.outerHeight();
            oZrkBox.prepend(lastDl).css({"top":-height + "px"}).stop().animate({"top":"0px"}, 1000);
        }, 3000);
    });
</script>






<div class="footer">
    <div class="about-box">
        <div class="about">
            <a href="http://www.kuanxue.com/index.html">网站首页</a>
            <a href="http://www.kuanxue.com/pl/help/aboutUs.do?id=0&flag=0" target="_blank">关于我们</a>
            <a href="http://www.kuanxue.com/pl/help/aboutUs.do?id=1" target="_blank">服务条款</a>
            <a href="http://www.kuanxue.com/pl/help/aboutUs.do?id=2" target="_blank">版权声明</a>
            <a href="http://www.kuanxue.com/pl/help/aboutUs.do?id=3" target="_blank">意见反馈</a>
            <a href="http://www.kuanxue.com/pl/help/aboutUs.do?id=4" target="_blank">联系我们</a>

            
            
        </div>
    </div>
    <p> © 2012 上海易学宝网络科技有限公司  kuanxue.com 保留一切权利</p>

    <p> <a href="http://www.miibeian.gov.cn/" target="_blank">沪ICP备10219167号</a>
        <script src="http://s21.cnzz.com/stat.php?id=3900945&web_id=3900945&show=pic" language="JavaScript"></script>
        <script type="text/javascript">
        var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
        document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3Fc350e5e04b82e2d6be9b4493602d7adc' type='text/javascript'%3E%3C/script%3E"));
        </script>
    </p>
</div>

<!--fooder end-->
</body>
</html>
