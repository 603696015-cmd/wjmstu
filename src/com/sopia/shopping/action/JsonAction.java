package com.sopia.shopping.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.bookinfo.dao.BookInfoDao;
import com.sopia.bookinfo.entities.BookTypeTree;
import com.sopia.common.ElException;
import com.sopia.courseman.entities.Course;
import com.sopia.shopping.entities.Commodity;
import com.sopia.statman.dao.ShoppingCartDao;
import com.sopia.studyman.dao.LineTrainRecordDao;
import com.sopia.studyman.entities.LineTrainRecord;


public class JsonAction extends BaseAction {  
	private ShoppingCartDao shoppingCartDao;
    private String action;  
    private String result;  
    private Course course;
    private Commodity comodity;
    private int courseid;
    private int type;
    private int infoCount;
	private BookInfoDao bookInfoDao;
    private String optype;
    private int trainid;
    private int credit;
	private LineTrainRecordDao linetrainDao; 
	private LineTrainRecord linetrainrecord; 
    
    
	private BookTypeTree bookTypeTree;
    
	public BookInfoDao getBookInfoDao() {
		return bookInfoDao;
	}
	public void setBookInfoDao(BookInfoDao bookInfoDao) {
		this.bookInfoDao = bookInfoDao;
	}
	public String getOptype() {
		return optype;
	}
	public void setOptype(String optype) {
		this.optype = optype;
	}
	public BookTypeTree getBookTypeTree() {
		return bookTypeTree;
	}
	public void setBookTypeTree(BookTypeTree bookTypeTree) {
		this.bookTypeTree = bookTypeTree;
	}
	public int getInfoCount() {
		return infoCount;
	}
	public void setInfoCount(int infoCount) {
		this.infoCount = infoCount;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Commodity getComodity() {
		return comodity;
	}
	public void setComodity(Commodity comodity) {
		this.comodity = comodity;
	}
	public int getCourseid() {
		return courseid;
	}
	public void setCourseid(int courseid) {
		this.courseid = courseid;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public ShoppingCartDao getShoppingCartDao() {
		return shoppingCartDao;
	}
	public void setShoppingCartDao(ShoppingCartDao shoppingCartDao) {
		this.shoppingCartDao = shoppingCartDao;
	}
	public String getAction() {  
        return action;  
    }  
    public void setAction(String action) {  
        this.action = action;  
    }  
      
      
    public String getResult() {  
        return result;  
    }  
    public void setResult(String result) {  
        this.result = result;  
    }  
    public String getShoppingCarCount() throws ElException, IOException{
			infoCount =shoppingCartDao.getMyShoppingCartCount(getSessionIntValue(ElConstants.SESSION_USERID));
			HttpServletResponse resp=ServletActionContext.getResponse();
			resp.setContentType("text/plain;charset=UTF-8");
			PrintWriter out=resp.getWriter();
			out.print(infoCount);	
			out.flush();
			out.close();

		return null;
		
	}
    
    //加入购物车
    public String executeaa() throws ElException {  
    	Commodity c = new Commodity();
    	c.setCommoditytype(type);
    	c.setUserid(getSessionIntValue(ElConstants.SESSION_USERID));
    	int id = courseid;
    	c.setCommodityid(id);  	
    	shoppingCartDao.addCommodityToShoppingCart(c);
//        if (action.equals("jquery")) {  
//            this.result = "content"; 

            return null;  
//        } else  
//            return "";  
    }  
    public String booktype_ajaxview() throws ElException {
		// if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
		// depTree = departmentDao.getDepTree(
		// getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
		// true);
		// else {
		// depTree = departmentDao.getDepTree(
		// getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
		// true);
		// }
			
			bookTypeTree = bookInfoDao.getClTypeById(bookTypeTree.getId());
			try {
				getResponse().setContentType("text/html;charset=UTF-8");
				PrintWriter localPrintWriter = getResponse().getWriter();
				String d= "{\"id\":\"" + bookTypeTree.getId() + "\",\"name\":\"" + bookTypeTree.getName()
								+ "\",\"bh\":\"" + bookTypeTree.getBh() + "\"}";
				// System.out.println(d);
				localPrintWriter.println(d);
				localPrintWriter.flush();
				localPrintWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		
    }
	public int getTrainid() {
		return trainid;
	}
	public void setTrainid(int trainid) {
		this.trainid = trainid;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
    public String  setlinetrainrecord() throws ElException, IOException{
    	linetrainDao.lineTrainsetcredit(credit, trainid);
    	linetrainrecord=linetrainDao.findRecordByIds(trainid);
    	HttpServletResponse resp=ServletActionContext.getResponse();
		resp.setContentType("text/plain;charset=UTF-8");
		PrintWriter out=resp.getWriter();
		out.print(linetrainrecord.getCredit());	
		out.flush();
		out.close();
    	return null;
    }
	public LineTrainRecordDao getLinetrainDao() {
		return linetrainDao;
	}
	public void setLinetrainDao(LineTrainRecordDao linetrainDao) {
		this.linetrainDao = linetrainDao;
	}
	public LineTrainRecord getLinetrainrecord() {
		return linetrainrecord;
	}
	public void setLinetrainrecord(LineTrainRecord linetrainrecord) {
		this.linetrainrecord = linetrainrecord;
	}
    
}
