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
	<HEAD>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>中国食品安全培训网-<s:property value="sysconf.typeName"/></TITLE>
		<base href="<%=basePath%>">
		<LINK href="elfrontimages/index.css" type=text/css rel=stylesheet>
		<LINK href="elfrontimages/menu.css" rel=stylesheet type="text/css">
		<META content="MSHTML 6.00.2900.5897" name=GENERATOR>
	</HEAD>
	<BODY>
	 <%@include file="frontheader.jsp" %>
	 <div style="width:100%;text-align: center;padding:5px;">
	 	<div style="width:999px;border-left:buttonface 1px solid;border-right:buttonface 1px solid;">${sysconf.content }</div>
	 </div>
		<TABLE width="960px" 
                              border=0 align="center" cellPadding=0 cellSpacing=0 bgcolor="#EEF5FB">
        <TBODY>
          <TR>
            <TD vAlign=center align=center height=44><SELECT 
                                class=input_bg 
                                onchange=javascript:window.open(this.options[this.selectedIndex].value); 
                                size=1 name=select2>
                <OPTION 
                                selected>---国家部委网站---</OPTION>
                <OPTION 
                                value=http://www.fmprc.gov.cn>外交</OPTION>
                  <OPTION 
                                value=http://www.moe.edu.cn>教育</OPTION>
                  <OPTION 
                                value=http://www.most.gov.cn>科学技术部</OPTION>
                                    <OPTION 
                                value=http://www.costind.gov.cn>国防科工</OPTION>
                  <OPTION 
                                value=http://www.seac.gov.cn>国家民委</OPTION>
                                    <OPTION 
                                value=http://www.mps.gov.cn>公安</OPTION>
                  <OPTION 
                                value=http://www.mca.gov.cn>民政</OPTION>
                  <OPTION 
                                value=http://www.mof.gov.cn>财政</OPTION>
                  <OPTION 
                                value=http://www.molss.gov.cn>劳动和社会保障部</OPTION>
                                      <OPTION 
                                value=http://www.mlr.gov.cn>国土资源</OPTION>
                    <OPTION 
                                value=http://www.cin.gov.cn>建设</OPTION>
                  <OPTION 
                                value=http://www.chinamor.cn.net>铁道</OPTION>
                  <OPTION 
                                value=http://www.moc.gov.cn>交通部</OPTION>
                                        <OPTION 
                                value=http://www.mii.gov.cn>信息产业</OPTION>
                      <OPTION 
                                value=http:/www.mwr.gov.cn>水利</OPTION>
                    <OPTION 
                                value="http://www.agri.gov.cn ">农业</OPTION>
                  <OPTION 
                                value=http://www.ccnt.gov.cn>文化</OPTION>
                  <OPTION 
                                value=http://www.moh.gov.cn>卫生</OPTION>
                  <OPTION 
                                value=http://www.sfpc.gov.cn>人口计生</OPTION>
                  <OPTION 
                                value=http://www.cma.gov.cn>中国气象局</OPTION>
            </SELECT></TD>
            <TD vAlign=center align=center><SELECT 
                                class=input_bg 
                                onchange=javascript:window.open(this.options[this.selectedIndex].value); 
                                size=1 name=select3>
                <OPTION 
                                selected>---新闻媒体网站---</OPTION>
                <OPTION 
                                value=http://www.xinhua.org>新华</OPTION>
                  <OPTION 
                                value=http://www.people.com.cn>人民</OPTION>
                  <OPTION 
                                value=http://www.cctv.com>央视国际</OPTION>
                                    <OPTION 
                                value=http://www.cnradio.com>中央人民广播电台</OPTION>
                                    <OPTION 
                                value=http://www.cri.com.cn>中国国际广播电台</OPTION>
                                    <OPTION 
                                value=http://www.gmw.com.cn>光明</OPTION>
                  <OPTION 
                                value=http://www.economicdaily.com.cn>经济日报</OPTION>
                                    <OPTION 
                                value=http://www.chinadaily.com.cn>中国日报</OPTION>
                                    <OPTION 
                                value=http://www.chinanews.com.cn>中国新闻</OPTION>
                  <OPTION 
                                value=http://www.bjd.com.cn>京报</OPTION>
                  <OPTION 
                                value=http://www.btv.org>北京电视</OPTION>
                  <OPTION 
                                value=http://www.bjradio.com.cn/>                          
                                                  
                                                          
                                                                                      
                                                      
                                                北京人民广播电台
                                                
                              
                              
                      
              </OPTION>
                                    <OPTION 
                                value=http://www.beijingnews.com.cn>千龙新闻</OPTION>
                  <OPTION 
                                value=http://www.bjyouth.com.cn>北京青年</OPTION>
                    <OPTION 
                                value=http://www.morningpost.com.cn/>                            
                                                  
                                                            
                                                                                      
                                                      
                                                北京晨报
                                                
                              
                              
                      
              </OPTION>
            </SELECT></TD>
            <TD vAlign=center align=center><SELECT 
                                class=input_bg 
                                onchange=javascript:window.open(this.options[this.selectedIndex].value); 
                                size=1 name=select4>
                <OPTION 
                                selected>---省级政府网站---</OPTION>
                <OPTION 
                                value=http://www.shanghai.gov.cn>上海</OPTION>
                <OPTION value=http://www.tj.gov.cn>天津</OPTION>
                <OPTION value=http://www.cq.gov.cn>重庆</OPTION>
                <OPTION 
                                value=http://www.hebei.gov.cn>河北</OPTION>
                <OPTION value=http://www.ah.gov.cn>安徽</OPTION>
                <OPTION value=http://www.gd.gov.cn>广东</OPTION>
                <OPTION value=http://www.gxi.gov.cn>广西</OPTION>
                <OPTION 
                                value=http://www.gzgov.gov.cn>贵州</OPTION>
                <OPTION 
                                value=http://www.hainan.gov.cn>海南</OPTION>
                <OPTION 
                                value=http://www.hubei.gov.cn>湖北</OPTION>
                <OPTION 
                                value=http://www.hunan.gov.cn>湖南</OPTION>
                <OPTION value=http://www.jl.gov.cn>吉林</OPTION>
                <OPTION 
                                value="http://www.jiangxi.gov.cn ">江西</OPTION>
                <OPTION value=http://www.qh.gov.cn>青海</OPTION>
                <OPTION 
                                value=http://www.shanxi.gov.cn>山西</OPTION>
                <OPTION 
                                value=http://www.gansu.gov.cn>甘肃</OPTION>
                <OPTION 
                                value=http://www.gzgov.gov.cn>贵州</OPTION>
                <OPTION value=http://zjzf.zj001.net>浙江</OPTION>
                <OPTION value=http://www.yn.gov.cn>云南</OPTION>
                <OPTION 
                                value=http://www.fujian.gov.cn>福建</OPTION>
                <OPTION 
                                value=http://www.henan.gov.cn>河南</OPTION>
                <OPTION 
                                value="http://www.ln.gov.cn ">辽宁</OPTION>
                <OPTION value=http://www.sc.gov.cn>四川</OPTION>
                <OPTION 
                                value=http://www.hlj.gov.cn>黑龙</option>
                  <OPTION 
                                value=http://www.nmg.gov.cn>内蒙</option>
                  <OPTION 
                                value=http://www.info.gov.hk>香港</OPTION>
                                    <OPTION 
                                value=http://www.macau.gov.mo>澳门</OPTION>
            </SELECT></TD>
            <TD vAlign=center align=center><SELECT 
                                class=input_bg 
                                onchange=javascript:window.open(this.options[this.selectedIndex].value); 
                                name=select5>
                <OPTION 
                                selected>---党政建设网站---</OPTION>
                <OPTION 
                                value=http://www.bjdj.gov.cn/>      
                                                北京党建
              </OPTION>
                <OPTION 
                                value=http://www.zgdjyj.com/>      
                                                党建研究
              </OPTION>
                <OPTION 
                                value=http://www.jszzb.gov.cn/>      
                                                江苏党建
              </OPTION>
                <OPTION 
                                value=http://www.njdj.gov.cn/>      
                                                南京党建
              </OPTION>
                <OPTION value=http://www.xzdj.cn/>      
                                                徐州党建
              </OPTION>
                <OPTION 
                                value=http://www.hzdj.gov.cn/>      
                                                杭州党建
              </OPTION>
                <OPTION 
                                value=http://www.zhjgdj.gov.cn/>      
                                                珠海党建
              </OPTION>
                <OPTION 
                                value=http://www.gzdj.gov.cn/style03/index.asp>广州党建</OPTION>
                <OPTION 
                                value=http://www.dangjian.gov.cn/>      
                                                东台党建
              </OPTION>
                <OPTION 
                                value=http://www.71.net.cn/>      
                                                临安党建
              </OPTION>
                <OPTION 
                                value=http://www.zjdj.gov.cn/>      
                                                镇江党建
              </OPTION>
                <OPTION 
                                value=http://www.wzdj.gov.cn/>      
                                                温州党建
              </OPTION>
                <OPTION 
                                value=http://www.ypdj.com.cn/>      
                                                杨浦党建
              </OPTION>
                <OPTION 
                                value=http://www.njdj.gov.cn/shouye/Sqinhuai.jsp>秦淮党建</OPTION>
                <OPTION 
                                value=http://zzb.suqian.gov.cn/>      
                                                宿迁党建
              </OPTION>
                <OPTION 
                                value=http://www.szwzdj.gov.cn>苏州吴中党建</OPTION>
                <OPTION 
                                value=http://www.zjgdj.gov.cn/>      
                                                张家港党</OPTION>
                <OPTION 
                                value=http://www.zjgce.gov.cn>张家港干部教育网</OPTION>
                <OPTION 
                                value=http://zzb.zgwj.gov.cn>吴江组工之友</OPTION>
                <OPTION 
                                value=http://xj.jinchang.gov.cn>金阊党建</OPTION>
                <OPTION 
                                value=http://www.szxcdj.gov.cn>相城党建</OPTION>
                <OPTION 
                                value=http://lunan.luqiao.gov.cn/dang/>      
                                                路南党建
              </OPTION>
                <OPTION 
                                value=http://www.zjgyj.gov.cn/>      
                                                张家港远程教育网
              </OPTION>
                <OPTION 
                                value=http://www.xswk.com/>      
                                                太仓远程教育</OPTION>
                <OPTION 
                                value=http://www.tczzb.gov.cn/>      
                                                太仓党建
              </OPTION>
            </SELECT></TD>
            <TD vAlign=center align=center><SELECT 
                                class=input_bg 
                                onchange=javascript:window.open(this.options[this.selectedIndex].value); 
                                name=select6>
                <OPTION 
                                selected>---干部教育网站---</OPTION>
                <OPTION 
                                value=http://www.ceat.edu.cn/>      
                                                中国教育干部培训
              </OPTION>
                <OPTION 
                                value=http://www.shcec.com/>      
                                                上海市干部教育中
              </OPTION>
                <OPTION 
                                value=http://www.ynce.gov.cn/ynce/site/main/index_1024.jsp>云南干部教育</OPTION>
                <OPTION 
                                value=http://www.bjce.gov.cn/>      
                                                北京干部教育
              </OPTION>
                <OPTION 
                                value=http://www.whce.gov.cn:81/>      
                                                武汉干部教育
              </OPTION>
            </SELECT></TD>
          </TR>
        </TBODY>
      </TABLE>
	<%@include file="frontbottom.jsp" %>
	
	</body>
</HTML>
