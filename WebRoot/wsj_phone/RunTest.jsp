<%@ page language="java" import="java.sql.*" pageEncoding="UTF-8"%>
<BODY>
	<DIV style="text-align:center;">
		<object classid="clsid:02E2D748-67F8-48B4-8AB4-0A085374BB99" width="500" height="400" id="BaiduPlayer" name="BaiduPlayer" onError=if(window.confirm('请您先安装百度影音软件,然后刷新本页才可以正常播放.')){window.open('http://player.baidu.com')}else{self.location='http://player.baidu.com'}>
			<%-- <param name='url' value='f:\baidu player\赌神.rmvb'/> --%>
			<param name='url' value='http://localhost:8080/gdgat/elstuffs\400\500.flv'/>
			<param name='autoplay' value='1'/>
		</object>
		<OBJECT classid="clsid:D45FD31B-5C6E-11D1-9EC1-00C04FD7081F" id="Agent" CODEBASE="#VERSION=2,0,0,0" VIEWASTEXT>
</OBJECT>
<OBJECT classid="clsid:B8F2846E-CE36-11D0-AC83-00C04FD97575" id="TruVoice" CODEBASE="#VERSION=6,0,0,0" VIEWASTEXT>
</OBJECT>
		
	</DIV>

	</body>