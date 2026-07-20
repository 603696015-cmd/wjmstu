package com.sopia.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.AuthenticationFailedException;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.spi.LoggerFactory;
import org.apache.struts2.ServletActionContext;

import com.sopia.ElConstants;
import com.sopia.questionman.entities.StuffLib;
import com.sun.mail.smtp.SMTPSendFailedException;


public class SendMail {
	private static final Log logger=LogFactory.getLog(SendMail.class);
	private String smtpHost="";//smtp服务器
	private String username="";//登录smtp服务器的账号
	private String password="";//登录smtp服务器的密码
	private String from="";//发件人邮箱
//	private String to="";//收件人邮箱
	private String title="";//邮件标题
	private String content="";//邮件内容
	private RecipientType recipientType=RecipientType.TO;
	private String emailFile;//邮件群发的附件路径
	private String emailFilename;//邮件群发的附件名
//	private List<StuffLib> stuffs;//附件
	private String[] uNames;
	private String[] pwds;
	
	
	
	public String getEmailFile() {
		return emailFile;
	}
	public void setEmailFile(String emailFile) {
		this.emailFile = emailFile;
	}
	public String getEmailFilename() {
		return emailFilename;
	}
	public void setEmailFilename(String emailFilename) {
		this.emailFilename = emailFilename;
	}
	public String[] getUNames() {
		return uNames;
	}
	public void setUNames(String[] names) {
		uNames = names;
	}
	public String[] getPwds() {
		return pwds;
	}
	public void setPwds(String[] pwds) {
		this.pwds = pwds;
	}
	public String getSmtpHost() {
		return smtpHost;
	}
	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
//	public String getTo() {
//		return to;
//	}
//	public void setTo(String to) {
//		this.to = to;
//	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
//		try {
//			this.title = new String(title.getBytes("ISO8859-1"),"UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
//		try {
//			this.content = new String(content.getBytes("ISO8859-1"),"UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
		this.content = content;
	}
	
	public String massMail(List<InternetAddress> addresss,com.sopia.newsandmess.entities.Message message){
		//设置邮件服务器地址
		this.smtpHost=SystemConfOp.getValue(ElConstants.SYSTEM_CONF_EMAIL_SMTP);
		//获取发件人信息
		String[] uNames=SystemConfOp.getValue(ElConstants.SYSTEM_CONF_EMAIL_UNAMES).split("&");
		String[] pwds=SystemConfOp.getValue(ElConstants.SYSTEM_CONF_EMAIL_PWDS).split("&");
		//设置邮件信息
		this.title=message.getMess_title();
		this.content=message.getMess_content();
		for(int i = 0;i<message.getEmailFilename().length;i++){
			this.emailFilename = message.getEmailFilename()[i]+ message.getEmailFile()[i].substring(message.getEmailFile()[i].lastIndexOf("."));
			System.out.println(">>>>>>>>this.emailFilename="+this.emailFilename);
		}
		for(int i = 0;i<message.getEmailFile().length;i++){
//			String path = ServletActionContext.getServletContext().getRealPath("/");
//			System.out.println("path="+path);
			this.emailFile = message.getEmailFile()[i];
			System.out.println(">>>>>>>this.emailFile"+this.emailFile);
			String emailFile1 = this.emailFile.replace("/", "\\");
			System.out.println(this.emailFile.replace("/", "\\"));
			System.out.println(emailFile1);
//			String path1 = path+emailFile1;
//			System.out.println(">>>>>>>>this.emailFil="+path1);
		}
		if(message.getSendmanner()==1){
			this.recipientType=Message.RecipientType.TO;//普通
		}else if(message.getSendmanner()==2){
			this.recipientType=Message.RecipientType.CC;//抄送
		}else{
			this.recipientType=Message.RecipientType.BCC;//暗送
		}
		System.out.println(this.recipientType);
		int sendCount=SystemConfOp.getIntValue(ElConstants.SYSTEM_CONF_EMAIL_SENDCOUNT);//一次发送量
		int sendSize=(addresss.size()+sendCount-1)/sendCount;//要发的次数
		int n=0;
		List<InternetAddress> alist=null;
		StringBuffer error=new StringBuffer("");
		String errorMsg="";
		for (int i = 0; i < sendSize; i++) {
			//设置发件人账号密码
			this.username=uNames[i];
			this.password=pwds[i];
			//设置发件人
			this.from=uNames[i];
			alist=new ArrayList<InternetAddress>();
			if(addresss.size()>sendCount*(i+1)){
				n=sendCount;
			}else{
				//n=addresss.size()-sendCount*i;
				n=addresss.size();
			}
			for(int j=sendCount*i;j<n;j++){
				alist.add(addresss.get(j));
			}
			errorMsg=this.send(alist);
			if(!"true".equals(errorMsg)){
				error.append("第"+(i+1)+"批发送失败，原因："+errorMsg+"<br />");
			}
		}
		if(error.length()>0){
			return error.toString();
		}else{
			return "邮件已发出！";
		}
	}
	public String send(List<InternetAddress> addresss) {
		Properties props=new Properties();
//		props.put("mail.smtp.host",smtpHost);
		props.put("mail.smtp.host",smtpHost);
		props.put("mail.smtp.auth", "true");
		Transport transport=null;
		String errorMsg="";
		String path = ServletActionContext.getServletContext().getRealPath("/");
		try {
//			SmtpAuth auth=new SmtpAuth();
//			auth.setAccount(username, password);
//			Session mailSession=Session.getDefaultInstance(props,auth);
			Session mailSession=Session.getDefaultInstance(props);
//			mailSession.setDebug(true);//开启调试
			//创建邮件对象
			Message message=new MimeMessage(mailSession);
			//设置发件人
			message.setFrom(new InternetAddress(from));
			//添加收件人
//			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			//群发收件人
//			List<InternetAddress> iaList=new ArrayList<InternetAddress>();
//			iaList.add(new InternetAddress("630334464@qq.com"));
			InternetAddress[] toos=new InternetAddress[addresss.size()];
			for (int i = 0; i < toos.length; i++) {
				toos[i]=addresss.get(i);
			}
			//输出要发送的邮箱地址
//			for (int i = 0; i < toos.length; i++) {
//				System.out.println("发送："+toos[i].getAddress());
//			}
			message.addRecipients(recipientType,toos);//注意：里面不能有重复收件人地址
			//设置标题
			message.setSubject(title);
			 // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
			Multipart multipart = new MimeMultipart(); 
          
            //设置邮件的文本内容
            BodyPart contentPart = new MimeBodyPart();
          //设置文本内容
            contentPart.setText(content);
            multipart.addBodyPart(contentPart);
            //添加附件
            BodyPart messageBodyPart= new MimeBodyPart();
            DataSource source = new FileDataSource(path+this.emailFile.replace("/", "\\"));
            //添加附件的内容
            messageBodyPart.setDataHandler(new DataHandler(source));
            //添加附件的标题
            //这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
           // BASE64Encoder enc = new BASE64Encoder();
           // messageBodyPart.setFileName("=?GBK?B?"+enc.encode(this.emailFilename.getBytes())+"?=");
            multipart.addBodyPart(messageBodyPart);
          
            //将multipart对象放到message中
            message.setContent(multipart);
			
			//设置发送时间
			message.setSentDate(new Date());
			//设置邮件优先级(1:紧急 3：普通 5：缓慢)
			message.setHeader("X-Priority", "3");
			message.saveChanges();
			//创建传输对象
			transport=mailSession.getTransport("smtp");
			transport.connect(smtpHost,username,password);
			System.out.println("开始发送...");
			transport.sendMessage(message, message.getAllRecipients());
			//System.out.println("发出"+i+"次");
//			System.out.println("邮件已发出！");
			return "true";
		} catch (AuthenticationFailedException e) {
			//e.printStackTrace();
			errorMsg="发送失败，邮件服务器未成功连接，\n可能是账号或密码不对，或者此账号没有开通smtp服务！";
			logger.error(errorMsg, e);
		}catch (SMTPSendFailedException e){
			//e.printStackTrace();
			errorMsg="退信了!";
			logger.error(errorMsg,e);
		}catch (SendFailedException e){
			//e.printStackTrace();
			errorMsg="收件人地址填写有误!";
			logger.error(errorMsg,e);
		}catch (Exception e) {
			//e.printStackTrace();
			errorMsg="邮件发送失败!";
			logger.error(errorMsg,e);
		}finally{
			try {
				if(transport!=null){
					transport.close();
				}
			} catch (MessagingException e) {
				//e.printStackTrace();
				errorMsg="传输对象关闭失败!";
				logger.error(errorMsg,e);
			}
		}
		return errorMsg;
	}
	public RecipientType getRecipientType() {
		return recipientType;
	}
	public void setRecipientType(RecipientType recipientType) {
		this.recipientType = recipientType;
	}
}
