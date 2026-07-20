package com.sopia.common;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;


import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;




/**************************************************************
* 文件名称: ContinueFTP.java
* 功能描述: ftp文件上传功能，依赖commons-net-3.1.jar实现
* 创建日期: 2013-10-9
* 创建地址: 苏州
* 作者:  yskxz
**************************************************************/
public class ContinueFTP {
       
        private FTPClient ftpClient = new FTPClient();
       
        public ContinueFTP(){
               
                //设置将过程中使用到的命令输出到控制台
                this.ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        }


        /**
     * java编程中用于连接到FTP服务器
     * @param hostname 主机名
     * @param port 端口
     * @param username 用户名
     * @param password 密码
     * @return 是否连接成功
     * @throws IOException
     */
         public boolean connect(String hostname,int port,String username,String password)
                 throws IOException {
                 //连接到FTP服务器
                 ftpClient.connect(hostname, port);
                 //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
                 if(FTPReply.isPositiveCompletion(ftpClient.getReplyCode()))
                 {
                    
                         if(ftpClient.login(username, password))
                         {
                                  return true;
                     }
                 }
               
                          disconnect();
                          return false;


         }


         /**
          * 从FTP服务器上下载文件,支持断点续传功能
          * @param remote 远程文件路径
          * @param local 本地文件路径
          * @param mode tb传输方式：PassiveMode方式，ActiveMode方式
          * @return 是否成功
          * @throws IOException
          */
       /*  public DownloadStatus download(String remote,String local,String mode) throws IOException{


                         //设置ftp传输方式
                             if(mode.equalsIgnoreCase("P")){
                                     //PassiveMode传输
                                     ftpClient.enterLocalPassiveMode();
                             }
                             else {
                                     //ActiveMode传输
                                     ftpClient.enterLocalActiveMode();
                             }
        
                             //设置以二进制流的方式传输
                  ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                 
                  //下载状态
                  DownloadStatus result;  
                 
                  //本地文件列表
                  File f = new File(local);
                 
                  //检查远程文件是否存在  
                  FTPFile[] files = ftpClient.listFiles(new String(remote.getBytes("GBK"),"iso-8859-1"));  


                  if(files.length != 1){
                      System.out.println("远程文件不存在");  
                      return DownloadStatus.Remote_File_Noexist;  
                  }
                 
                  //获得远端文件大小
                  long lRemoteSize = files[0].getSize();
                 
                  //构建输出对象
              OutputStream out = null ;
             
                  //本地存在文件，进行断点下载 ；不存在则新下载
                  if(f.exists()){
                         
                          //构建输出对象
                      out = new FileOutputStream(f,true);
                     
                      //本地文件大小
                      long localSize = f.length();  


                      System.out.println("本地文件大小为:"+localSize);
                     
                     
                      //判定本地文件大小是否大于远程文件大小  
                      if(localSize >= lRemoteSize){
                          System.out.println("本地文件大于远程文件，下载中止");  
                          return DownloadStatus.Local_Bigger_Remote;  
                      }
                     
                      //否则进行断点续传，并记录状态  
                      ftpClient.setRestartOffset(localSize);  
                     
                      InputStream in = ftpClient.retrieveFileStream(new String(remote.getBytes("GBK"),"iso-8859-1"));  
                     
                      byte[] bytes = new byte[1024];  
                      long step = lRemoteSize /100; 
                     
                      //存放下载进度
                      long process=localSize /step;  
                      int c;  
                      while((c = in.read(bytes))!= -1){  
                          out.write(bytes,0,c);  
                          localSize+=c;  
                          long nowProcess = localSize /step;  
                          if(nowProcess > process){  
                              process = nowProcess;  
                              if(process % 10 == 0)  
                                  System.out.println("下载进度："+process);  
                              //TODO 更新文件下载进度,值存放在process变量中  
                          }  
                      }
                      //下载完成关闭输入输出流对象
                      in.close();  
                      out.close();  
                      boolean isDo = ftpClient.completePendingCommand();  
                      if(isDo){  
                          result = DownloadStatus.Download_From_Break_Success;  
                      }else {  
                          result = DownloadStatus.Download_From_Break_Failed;  
                      }  


                  }else {
                          out = new FileOutputStream(f);  
                      InputStream in= ftpClient.retrieveFileStream(new String(remote.getBytes("GBK"),"iso-8859-1"));  
                      byte[] bytes = new byte[1024];  
                      long step = lRemoteSize /100;  
                      long process=0;  
                      long localSize = 0L;  
                      int c;  
                      while((c = in.read(bytes))!= -1){  
                          out.write(bytes, 0, c);  
                          localSize+=c;  
                          long nowProcess = localSize /step;  
                          if(nowProcess > process){  
                              process = nowProcess;  
                              if(process % 10 == 0)  
                                  System.out.println("下载进度："+process);  
                              //TODO 更新文件下载进度,值存放在process变量中  
                          }  
                      }  
                      in.close();  
                      out.close();  
                      boolean upNewStatus = ftpClient.completePendingCommand();  
                      if(upNewStatus){  
                          result = DownloadStatus.Download_New_Success;  
                      }else {  
                          result = DownloadStatus.Download_New_Failed;  
                      }  
                  }
                  return result;
              }*/
                
              /**
               * 上传文件到FTP服务器，支持断点续传
               * @param local 本地文件名称，绝对路径
               * @param remote 远程文件路径，使用/home/directory1/subdirectory/file.ext 按照Linux上的路径指定方式，支持多级目录嵌套，支持递归创建不存在的目录结构
               * @param mode 传输方式：PassiveMode方式，ActiveMode方式
               * @return 上传结果
               * @throws IOException
               */
              public boolean upload(String local,String remote,String mode) throws IOException{
                  //设置PassiveMode传输
                  ftpClient.enterLocalPassiveMode();
                  //设置以二进制流的方式传输
                  ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                  boolean result;
                  //对远程目录的处理
                  String remoteFileName = remote;
                  if(remote.contains("/")){
                      remoteFileName = remote.substring(remote.lastIndexOf("/")+1);
                      String directory = remote.substring(0,remote.lastIndexOf("/")+1);
                      if(!directory.equalsIgnoreCase("/")&&!ftpClient.changeWorkingDirectory(directory)){
                          //如果远程目录不存在，则递归创建远程服务器目录
                          int start=0;
                          int end = 0;
                          if(directory.startsWith("/")){
                              start = 1;
                          }else{
                              start = 0;
                          }
                          end = directory.indexOf("/",start);
                          while(true){
                              String subDirectory = remote.substring(start,end);
                              if(!ftpClient.changeWorkingDirectory(subDirectory)){
                                  if(ftpClient.makeDirectory(subDirectory)){
                                      ftpClient.changeWorkingDirectory(subDirectory);
                                  }else {
                                      System.out.println("创建目录失败");
                                      return false;
                                  }
                              }
                                
                              start = end + 1;
                              end = directory.indexOf("/",start);
                                
                              //检查所有目录是否创建完毕
                              if(end <= start){
                                  break;
                              }
                          }
                      }
                  }
                    
                  //检查远程是否存在文件
                  FTPFile[] files = ftpClient.listFiles(remoteFileName);
                  if(files.length == 1){
                      long remoteSize = files[0].getSize();
                      File f = new File(local);
                      long localSize = f.length();
                      if(remoteSize==localSize){
                          return false;
                      }else if(remoteSize > localSize){
                          return false;
                      }
                        
                      //尝试移动文件内读取指针,实现断点续传
                      InputStream is = new FileInputStream(f);
                      if(is.skip(remoteSize)==remoteSize){
                          ftpClient.setRestartOffset(remoteSize);
                          if(ftpClient.storeFile(remote, is)){
                              return true;
                          }
                      }
                      //如果断点续传没有成功，则删除服务器上文件，重新上传
                      if(!ftpClient.deleteFile(remoteFileName)){
                          return false;
                      }
                      is = new FileInputStream(f);
                      if(ftpClient.storeFile(remote, is)){    
                          result = true;
                      }else{
                          result = false;
                      }
                      is.close();
                  }else {
                      InputStream is = new FileInputStream(local);
                      if(ftpClient.storeFile(remoteFileName, is)){
                          result = true;
                      }else{
                          result = false;
                      }
                      is.close();
                  }
                  return result;
              }
        
     /**
      * 断开与远程服务器的连接
      * @throws IOException
      */
     public void disconnect() throws IOException{
         if(ftpClient.isConnected()){
             ftpClient.disconnect();
         }
     }
}