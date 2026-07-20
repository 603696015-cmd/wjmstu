package com.sopia.newsandmess.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.InternetAddress;

import com.sopia.common.SendMail;
import com.sopia.duman.entities.ELUser;
import com.sopia.mail.MailSenderInfo;
import com.sopia.mail.SimpleMailSender;
import com.sopia.newsandmess.entities.Message;

public class Main {
	
	public static void main(String[] args)throws Exception{
		 //这个类主要是设置邮件   
	      MailSenderInfo mailInfo = new MailSenderInfo();    
	      mailInfo.setMailServerHost("smtp.qq.com");    
	      mailInfo.setMailServerPort("25");    
	      mailInfo.setValidate(true);    
	      mailInfo.setUserName("1105157153@qq.com");    
	      mailInfo.setPassword("hlWzjC@610903");//您的邮箱密码    
	      mailInfo.setFromAddress("1105157153@qq.com");    
	      mailInfo.setToAddress("531741243@qq.com");    
	      mailInfo.setSubject("设置邮箱标题 如http://www.guihua.org 中国桂花网");    
	      mailInfo.setContent("设置邮箱内容 如http://www.guihua.org 中国桂花网 是中国最大桂花网站==");    
	         //这个类主要来发送邮件   
	      SimpleMailSender sms = new SimpleMailSender();   
	          sms.sendTextMail(mailInfo);//发送文体格式    
	          sms.sendHtmlMail(mailInfo);//发送html格式   

	}

}
