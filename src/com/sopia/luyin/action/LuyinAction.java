package com.sopia.luyin.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.sopia.BaseAction;
import com.sopia.common.ElException;
import com.sopia.luyin.dao.impl.LuYinDaoImpl;
import com.sopia.luyin.entity.luyin;

public class LuyinAction
  extends BaseAction
{
  
  
  public String recordCount()
    throws ElException
  {

	    HttpServletResponse resp = ServletActionContext.getResponse();
	    HttpServletRequest reques = ServletActionContext.getRequest();
	    resp.setContentType("text/plain;charset=UTF-8");
	    String userid = reques.getParameter("userid");
	    String recordType = reques.getParameter("recordType");
	    if ((userid != null) && (!"".equals(userid)))
	    {
	      WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
	      LuYinDaoImpl luyinManager = (LuYinDaoImpl)wac.getBean("luyindao");
	      luyin lu = new luyin();
	      lu.setCount(1);
	      lu.setRecordType(recordType);
	      lu.setUserId(Integer.valueOf(userid).intValue());
	      try
	      {
	        luyinManager.addluyin(lu);
	      }
	      catch (ElException e1)
	      {
	        System.out.println("录音次数异常");
	        e1.printStackTrace();
	      }
	    }
	    else
	    {
	      System.out.println("没有插入录音次数");
	    }
	    try
	    {
	      PrintWriter localPrintWriter = resp.getWriter();
	      String d = "{\"canNext\":0}";
	      localPrintWriter.println(d);
	      localPrintWriter.flush();
	      localPrintWriter.close();
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	    return null;
  }
}
//  public String restWatchVieo()
//    throws ElException
//  {
//    HttpServletResponse resp = ServletActionContext.getResponse();
//    HttpServletRequest reques = ServletActionContext.getRequest();
//    PrintWriter localPrintWriter = null;
//    
//    resp.setContentType("application/json;charset=UTF-8");
//    
//    String userid = reques.getParameter("userid");
//    String recordType = reques.getParameter("recordType");
//    boolean success = true;
//    String errorMsg = "0000";
//    if ((userid != null) && (!userid.equals("")) && (!recordType.equals("")))
//    {
//      luyin luEntity = this.luyinManager.getLuYinByUserId(Integer.parseInt(userid), recordType);
//      try
//      {
//        localPrintWriter = resp.getWriter();
//      }
//      catch (IOException e)
//      {
//        success = false;
//        errorMsg = "閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹";
//        e.printStackTrace();
//      }
//      if (luEntity != null)
//      {
//        luEntity.setAccount(reques.getSession().getAttribute("username").toString());
//        luEntity.setRealName(reques.getSession().getAttribute("realname").toString());
//        luEntity.setDepName(reques.getSession().getAttribute("myDepName").toString());
//        luEntity.setCount(luEntity.getCount() + 1);
//      }
//      else
//      {
//        luEntity = new luyin();
//        luEntity.setCount(1);
//        luEntity.setAccount(reques.getSession().getAttribute("username").toString());
//        luEntity.setRealName(reques.getSession().getAttribute("realname").toString());
//        luEntity.setDepName(reques.getSession().getAttribute("myDepName").toString());
//        luEntity.setRecordType(recordType);
//        luEntity.setUserId(Integer.parseInt(userid));
//      }
//      this.luyinManager.saveOrUpdate(luEntity);
//    }
//    else
//    {
//      success = false;
//      errorMsg = "閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹";
//    }
//    String msg = "{\"success\":" + success + ",\"msg\":\"" + errorMsg + "\"}";
//    localPrintWriter.println(msg);
//    localPrintWriter.flush();
//    localPrintWriter.close();
//    
//    return null;
//  }
//}
