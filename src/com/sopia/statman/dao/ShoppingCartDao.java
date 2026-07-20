package com.sopia.statman.dao;

import java.util.List;

import com.sopia.common.ElException;
import com.sopia.shopping.entities.Commodity;
import com.sopia.shopping.entities.Order;
import com.sopia.shopping.entities.ShoppingCart;

public interface ShoppingCartDao {
	/**
	 * 得到用户购物车商品信息列表
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public List<Commodity>  getMyShoppingCart(int userid) throws ElException;
	/**
	 * 根据userid和orderid获取商品信息
	 * @param userid
	 * @param orderid
	 * @return
	 * @throws ElException
	 */
	public List<Commodity>  getMyShoppingCartByOrder(int userid,int orderid) throws ElException;
	/**
	 * 根据userid和comdityid获取培训班订单
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public Commodity getCommodityElclass(int userid) throws ElException;
	
	/**
	 * 得到购物车内有多少货物
	 */
	public int getMyShoppingCartCount(int userid) throws ElException;
	/**
	 * 添加货物进购物车
	 * @param c
	 * @throws ElException
	 */
	public int addCommodityToShoppingCart(Commodity c) throws ElException;
	/**
	 * 按购物车物品编号删除购物车货物
	 */
	public void deleCommodityToShoppingCart(int id) throws ElException;
	/**
	 * 清空购物车
	 * @param userid
	 * @throws ElException
	 */
	public void deleALLCommodityToShoppingCart(int userid)throws ElException;
	
	public void updALLCommodityToShoppingCart(int userid)throws ElException;
	/**
	 * 通过ID得到订单信息 个人订单
	 * @param orderid
	 * @return
	 * @throws ElException
	 */
	public Order shoppingCartOrder(int orderid) throws ElException;
	/**
	 * 判断学生是否购买过这门课程，或者管理员是否分配幷购买过这门课程,并且课程尚在使用时间 
	 * @param courseid
	 * @param userid
	 * @return true 尚没有合法使用权， false 已存在合法使用权
	 * @throws ElException
	 */
	public boolean checkUserCourse(int courseid,int userid) throws ElException;
	/**
	 * 判断学生是否购买过这门培训班，或者管理员是否分配幷购买过这门培训班 
	 * @param classid
	 * @param userid
	 * @return true 尚没有合法使用权， false 已存在合法使用权
	 * @throws ElException
	 */
	public boolean checkUserClass(int classid ,int userid) throws ElException;
	/**
	 * 判断该学生的其他订单是否存在该课程
	 * @param courseid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public boolean checkUserCourseOrder(int courseid,int userid) throws ElException;
	/**
	 * 判断该培训班是否已经超过结束日期
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public boolean checkUserClassfinishDate(int classid) throws ElException;
	/**
	 * 判断该学生是否已用有该培训班的其他订单
	 * @param classid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public boolean checkUserClassOrder(int classid,int userid) throws ElException;
	/**
	 * 修改订单的总价
	 * @param zongjia
	 * @param orderid
	 * @throws ElException
	 */
	public void updOrderzongjia(float  zongjia,int orderid) throws ElException;
	/**
	 * 用户支付
	 * @param userid
	 * @param userbalance
	 * @param orderid
	 * @throws ElException
	 */
	public void userPay(int userid,float userbalance,int orderid,int status) throws ElException;
	/**
	 * 检查是否存在实物商品
	 * @param orderid
	 * @return
	 * @throws ElException
	 */
	public  boolean  checkIfBook(int orderid) throws ElException;
	/**
	 * 修改货物数量
	 * @param id
	 * @param count
	 * @throws ElException
	 */
	public  void    shopping_count_upd(int id,int count) throws ElException;
	
	
	/**
	 * 订单是否存在
	 * @param commodity
	 * @return
	 * @throws ElException
	 */
	public int checkUserOrder(Commodity commodity) throws ElException;
	
	/**
	 * 验证sp_order是否存在
	 * @param orderid
	 * @param userid
	 * @throws ElException
	 */
	public boolean checkMakeOrder(int orderid ,int userid) throws ElException;
	
	/**
	 * 根据orderid获取userid
	 */
	public int getUseridByid(int orderid)throws ElException;
}	
