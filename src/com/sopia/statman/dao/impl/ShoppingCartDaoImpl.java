package com.sopia.statman.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.forumman.dao.impl.ForumAdminDaoImpl;
import com.sopia.shopping.entities.Commodity;
import com.sopia.shopping.entities.Order;
import com.sopia.statman.dao.ShoppingCartDao;

public class ShoppingCartDaoImpl implements ShoppingCartDao {
	private static final Log logger = LogFactory
			.getLog(ForumAdminDaoImpl.class);

	/**
	 * 得到购物车详细信息
	 */
	public List<Commodity> getMyShoppingCart(int userid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Commodity> listsc = new ArrayList<Commodity>();

		try {

			ct = DBConnection.getConnection();
//			String sql = " select t.* from (select spc.id , cs.name ,"
//					+ " spc.commodityid,spc.commoditytype, spc.adddate, "
//					+ " cp.courseoldprice as old,cp.coursenowprice as now,spc.count"
//					+ " from (shoppingcart spc left"
//					+ " join course cs on cs.id =spc.commodityid) left join"
//					+ " course_price cp"
//					+ " on cp.courseid=spc.commodityid where Commoditytype = 1 and"
//					+ " spc.userid=? "
//					+ " union all "
//					+ " select spc.id , ec.name , spc.commodityid,spc.commoditytype,"
//					+ " spc.adddate, (select SUM(courseoldprice )"
//					+ " from course_price where course_price.courseid in (select"
//					+ " courseid "
//					+ " from class_course left join shoppingcart on"
//					+ " shoppingcart.commodityid "
//					+ " = class_course.classid where shoppingcart.userid=? ) ) as old ,"
//					+ " "
//					+ " (select SUM(coursenowprice ) from course_price where"
//					+ " course_price.courseid in"
//					+ " (select courseid from class_course left join shoppingcart on "
//					+ " shoppingcart.commodityid =class_course.classid where"
//					+ " shoppingcart.userid=? ) ) as now ,"
//					+ " spc.count from shoppingcart spc left join elclass ec on ec.id"
//					+ " =spc.commodityid "
//					+ " where spc.commoditytype = 2 and spc.userid=? "
//					+ " union all "
//					+ " select spc.id,bi.name"
//					+ " ,spc.commodityid,spc.commoditytype,spc.adddate,bi.marketprice as"
//					+ " old,bi.vipprice as now,spc.count"
//					+ " from bookinfo bi, shoppingcart spc where spc.userid=? and"
//					+ " bi.id=spc.commodityid and spc.commodityid=4 "
//					+ " union all "
//					+ " select"
//					+ " spc.id,b.name,spc.commodityid,spc.commoditytype,spc.adddate,b.shichangjia"
//					+ " as old,b.huiyuanjia as now,spc.count "
//					+ " from product b,shoppingcart spc where spc.userid=? and"
//					+ " b.id=spc.commodityid and spc.commodityid=3  " 
//					+ " union all "
//					+ " select spc.id , elc.name,spc.commodityid,spc.commoditytype,spc.adddate, "
//					+ " elcp.elclassoldprice as old,elcp.elclassnowprice as now,spc.count "
//					+ " from elclass elc, elclass_price elcp, shoppingcart spc"
//					+ " where elc.id = elcp.elclassid and spc.userid=elcp.userid "
//					+ "  and elcp.userid = ? and spc.commodityid=5 "+ " )t order by t.adddate asc " 
//					;
			String sql = " select t.* from (select spc.id , cs.name ,"
				+ " spc.commodityid,spc.commoditytype, spc.adddate, "
				+ " cp.courseoldprice as old,cp.coursenowprice as now,spc.count"
				+ " from (shoppingcart spc left"
				+ " join course cs on cs.id =spc.commodityid) left join"
				+ " course_price cp"
				+ " on cp.courseid=spc.commodityid where Commoditytype = 1 and"
				+ " spc.userid=? "
				+ " union all "
				+ " select spc.id , ec.name , spc.commodityid,spc.commoditytype,"
				+ " spc.adddate, elcp.elclassoldprice as old ,"
				+ " "
				+ " elcp.elclassnowprice as now ,"
				+ " spc.count from shoppingcart spc " +
						" left join elclass ec on ec.id = spc.commodityid " +
						" left join elclass_price elcp on ec.id = elcp.elclassid  "
				+ " where spc.commoditytype = 2 and spc.userid=? "
				+ " union all "
				+ " select spc.id,bi.name"
				+ " ,spc.commodityid,spc.commoditytype,spc.adddate,bi.marketprice as"
				+ " old,bi.vipprice as now,spc.count"
				+ " from bookinfo bi, shoppingcart spc where spc.userid=? and"
				+ " bi.id=spc.commodityid and spc.commoditytype=4 "
				+ " union all "
				+ " select"
				+ " spc.id,b.name,spc.commodityid,spc.commoditytype,spc.adddate,b.shichangjia"
				+ " as old,b.huiyuanjia as now,spc.count "
				+ " from product b,shoppingcart spc where spc.userid=? and"
				+ " b.id=spc.commodityid and spc.commoditytype=3  " 
				+ " union all " 
				+ " select spc.id,r.title,spc.commodityid,spc.commoditytype,spc.adddate,erp.examroomoldprice as old," 
				+ " erp.examroomnowprice as new,spc.count "
				+ " from exam_room r,shoppingcart spc,exam_room_price erp where spc.userid=? and r.id=erp.examroomid and r.id=spc.commodityid and spc.commoditytype=5"
				+	")t order by t.adddate asc ";
			logger.info(sql);
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, userid);
			ps.setInt(3, userid);
			ps.setInt(4, userid);
			ps.setInt(5, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				Commodity sc = new Commodity();
				sc.setId(rs.getInt(1));
				sc.setCommodityName(rs.getString(2));
				sc.setCommodityid(rs.getInt(3));
				sc.setCommoditytype(rs.getInt(4));
				sc.setAdddate(rs.getTimestamp(5));
				sc.setOldp(rs.getFloat(6));
				sc.setNowp(rs.getFloat(7));
				sc.setCount(rs.getInt(8));
				sc.setAllp(sc.getNowp() * sc.getCount());
				listsc.add(sc);

			}

//			String sql = " select spc.id spcid,elc.id, elc.name, elcp.elclassnowprice, elcp.elclassoldprice,spc.commoditytype,spc.count, "
//					+ " spc.commodityid,spc.adddate "
//					+ " from elclass elc, elclass_price elcp, shoppingcart spc"
//					+ " where elc.id = elcp.elclassid and spc.userid=elcp.userid "
//					+ "  and elcp.userid = ?";
//			ps = ct.prepareStatement(sql);
//			ps.setInt(1, userid);
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				Commodity sc = new Commodity();
//				sc.setId(rs.getInt(1));
//				sc.setCommodityid(rs.getInt(2));
//				sc.setCommodityName(rs.getString(3));
//				sc.setNowp(rs.getFloat(4));
//				sc.setOldp(rs.getFloat(5));
//				sc.setCommoditytype(rs.getInt(6));
//				sc.setCount(rs.getInt(7));
//				sc.setAdddate(rs.getTimestamp(9));
//				sc.setAllp(sc.getNowp());
//				listsc.add(sc);
//			}
			return listsc;
		} catch (Exception e) {
			logger.error("得到用户购物车信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	/**
	 * 得到购物车粗略信息
	 */
	public int getMyShoppingCartCount(int userid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {

			ct = DBConnection.getConnection();
			String sql = "select count(1)  from  shoppingcart where userid = ?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);

		} catch (Exception e) {
			logger.error("得到用户购物车信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	/**
	 * 添加购物车信息
	 */
	public int addCommodityToShoppingCart(Commodity c) throws ElException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int id = 0;
		try {

			ct = DBConnection.getConnection();
			String sql = "call into_shoppingcommodity(?,?,?)";
			/**
			 * create or replace procedure into_shoppingcommodity( userid_
			 * number,commodityid_ number,commoditytype_ number) as isinsc
			 * number ; begin isinsc :=0; select count(1) into isinsc from
			 * shoppingcart where userid = userid_ and commodityid =
			 * commodityid_ and commoditytype=commoditytype_ ;--得出这购物车有没有这个商品 if
			 * isinsc=0 then--如果没有 insert into
			 * shoppingcart(userid,commodityid,commoditytype,adddate,count)
			 * values(userid_,commodityid_,commoditytype_,sysdate,1); end if;
			 * end ;
			 */
			ps = ct.prepareStatement(sql);
			ps.setInt(1, c.getUserid());
			ps.setInt(2, c.getCommodityid());
			ps.setInt(3, c.getCommoditytype());
			ps.executeUpdate();
			
			ps = ct.prepareStatement("select SHOPPINGCART_SEQUENCE.currval from dual");
			rs = ps.executeQuery();
			if(rs.next()){
				id = rs.getInt(1);
			}

		} catch (Exception e) {
			logger.error("添加购物车信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;

	}

	public void deleCommodityToShoppingCart(int id) throws ElException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {

			ct = DBConnection.getConnection();
			String sql = "delete shoppingcart where id =?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("删除购物车信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public void deleALLCommodityToShoppingCart(int userid) throws ElException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {

			ct = DBConnection.getConnection();
			String sql = "delete from shoppingcart where userid =?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("清空购物车信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public void updALLCommodityToShoppingCart(int userid) throws ElException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {

			ct = DBConnection.getConnection();
			String sql = "update shoppingcart set status=-1 where userid =?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("清空购物车信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public Order shoppingCartOrder(int orderid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Order o = new Order();
		try {
			ct = DBConnection.getConnection();
			String sql = "select spo.id ,spo.userid,spo.orderdate,spo.sumpeice ,eu.realname ,spo.status"
					+ " ,spo.note,spo.tel ,spo.shoujianren from sp_order spo left join eluser eu on eu.id=spo.userid where spo.id=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, orderid);
			rs = ps.executeQuery();
			while (rs.next()) {
				o.setId(rs.getInt(1));
				o.setUserid(rs.getInt(2));
				o.setOrderdate(rs.getTimestamp(3));
				o.setSumpeice(rs.getFloat(4));
				o.setUsername(rs.getString(5));
				o.setStatus(rs.getInt(6));
				o.setNote(rs.getString(7));
				o.setTel(rs.getString(8));
				o.setShoujianren(rs.getString(9));
			}
			return o;

		} catch (Exception e) {
			logger.error("清空购物车信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public boolean checkUserCourse(int courseid, int userid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean flag = false;
		try {
			ct = DBConnection.getConnection();
//			String sql = "select count(1)  from study_course  sc ,eluser_couser_order sco,"
//					+ "  sp_order  so  where sco.userid=? and sco.courseid=? and  sc.userid=sco.userid"
//					+ "  and sc.courseid=sco.courseid  and sc.classid=0  and sco.classid=0  "
//					+ "  and sc.finishtime>sysdate and  so.id = sco.orderid  and so.buydate is not null";
			String sql = "select count(1) from study_course where userid=? and courseid=? ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, courseid);
			rs = ps.executeQuery();
			rs.next();
			if (rs.getInt(1) > 0) {

				flag = true;
			}
			return flag;

		} catch (Exception e) {
			logger.error("判断学生是否购买过这门课程，或者管理员是否分配幷购买过这门课程,并且课程尚在使用时间 失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public boolean checkUserCourseOrder(int courseid, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean flag = false;
		try {
			ct = DBConnection.getConnection();
			String sql = "  select count(*) from study_course  sc ,  eluser_couser_order sco,"
					+ "sp_order  so  where sco.userid=?  and sco.courseid=? and sco.classid=0 "
					+ "and sc.userid=sco.userid  and sc.courseid=sco.courseid   and sc.classid=0 "
					+ "and so.id=sco.orderid   and sc.finishtime>sysdate    and so.buydate is  null";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, courseid);
			rs = ps.executeQuery();
			rs.next();
			if (rs.getInt(1) > 0) {

				flag = true;
			}
			return flag;

		} catch (Exception e) {
			logger.error("判断学生是否存在这门课程的其他有效订单失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public boolean checkUserClassfinishDate(int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean flag = false;
		try {
			ct = DBConnection.getConnection();
			String sql = " select count(1) from elclass  el where el.id=? and el.finishtime < sysdate";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			rs = ps.executeQuery();
			rs.next();
			if (rs.getInt(1) > 0) {

				flag = true;
			}
			return flag;

		} catch (Exception e) {
			logger.error("判断培训班是否已到结束日期！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public boolean checkUserClass(int classid, int userid) throws ElException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean flag = false;
		try {
			ct = DBConnection.getConnection();
			String sql = "  select  count(1)  from  eluser_couser_order sco ,sp_order  so  "
					+ " where sco.userid=? and sco.classid=? and  so.id=sco.orderid and "
					+ " so.buydate is not null";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, classid);
			rs = ps.executeQuery();
			rs.next();
			if (rs.getInt(1) > 0) {

				flag = true;
			}
			return flag;

		} catch (Exception e) {
			logger.error("判断学生是否购买成功过这门培训班，或者管理员是否分配幷购买成功过这门培训班,失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public boolean checkUserClassOrder(int classid, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean flag = false;
		try {
			ct = DBConnection.getConnection();
			String sql = "  select count(*) from  eluser_couser_order sco,"
					+ " sp_order  so  where sco.userid=?  and sco.classid=?  "
					+ " and so.id=sco.orderid   and so.buydate is  null";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, classid);
			rs = ps.executeQuery();
			rs.next();
			if (rs.getInt(1) > 0) {

				flag = true;
			}
			return flag;

		} catch (Exception e) {
			logger.error("判断学生是否存在这门培训班的其他订单失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public void updOrderzongjia(float zongjia, int orderid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "  update sp_order set sp_order.sumpeice=? where sp_order.id=?";
			ps = ct.prepareStatement(sql);
			ps.setFloat(1, zongjia);
			ps.setInt(2, orderid);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("修改订单价格", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void userPay(int userid, float userbalance, int orderid, int status)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql = " call userpay(?,?,?,?)";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setFloat(2, userbalance);
			ps.setInt(3, orderid);
			ps.setInt(4, status);

			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("支付失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public boolean checkIfBook(int orderid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean flag = false;
		try {
			ct = DBConnection.getConnection();
			String sql = " select  count(1) from sp_order_info  soi  where soi.orderid=? and commoditytype!=1 and commoditytype!=2 ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, orderid);
			rs = ps.executeQuery();
			rs.next();
			if (rs.getInt(1) > 0)
				flag = true;
			return flag;
		} catch (Exception e) {
			logger.error("验证是否存在实物商品！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public void shopping_count_upd(int id, int count) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean flag = false;
		try {
			ct = DBConnection.getConnection();
			String sql = " update  shoppingcart set count=?  where id=? ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, count);
			ps.setInt(2, id);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("修改商品数量失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}
	
	public Commodity getCommodityElclass(int userid) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Commodity sc = null;
		try {
			ct = DBConnection.getConnection();
			String sql = " select spc.id , ec.name , spc.commodityid,spc.commoditytype,"
				+ " spc.adddate, elcp.elclassoldprice as old ,"
				+ " "
				+ " elcp.elclassnowprice as now ,"
				+ " spc.count from shoppingcart spc " +
						" left join elclass ec on ec.id = spc.commodityid " +
						" left join elclass_price elcp on ec.id = elcp.elclassid  "
				+ " where spc.commoditytype = 2 and spc.userid=? ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if(rs.next()){
				sc = new Commodity();
				sc.setId(rs.getInt(1));
				sc.setCommodityName(rs.getString(2));
				sc.setCommodityid(rs.getInt(3));
				sc.setCommoditytype(rs.getInt(4));
				sc.setAdddate(rs.getTimestamp(5));
				sc.setOldp(rs.getFloat(6));
				sc.setNowp(rs.getFloat(7));
				sc.setCount(rs.getInt(8));
				sc.setAllp(sc.getNowp() * sc.getCount());
			}
		} catch (Exception e) {
			logger.error("验证是否存在实物商品！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return sc;
	}

	public int checkUserOrder(Commodity c) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int orderid = 0;
		try {
			ct = DBConnection.getConnection();
			String sql = " select * from shoppingcart where userid = ? and commodityid = ? and  commoditytype=? ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, c.getUserid());
			ps.setInt(2, c.getCommodityid());
			ps.setInt(3, c.getCommoditytype());
			rs = ps.executeQuery();
			if(rs.next()){
				orderid = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("验证订单是否存在！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return orderid;
	}

	public boolean checkMakeOrder(int orderid, int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean flag = false;
		try {
			ct = DBConnection.getConnection();
			String sql = " select * from sp_order where userid = ? and id=? ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, orderid);
			rs = ps.executeQuery();
			if(rs.next()){
				flag = true;
			}
		} catch (Exception e) {
			logger.error("验证订单是否存在！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flag;
		
	}

	public int getUseridByid(int orderid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int userid = 0;
		try {
			ct = DBConnection.getConnection();
			String sql = "select * from shoppingcart where id=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1,orderid);
			rs = ps.executeQuery();
			if(rs.next()){
				userid = rs.getInt("userid");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return userid;
	}
	
	
	public List<Commodity> getMyShoppingCartByOrder(int userid,int orderid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Commodity> listsc = new ArrayList<Commodity>();

		try {

			ct = DBConnection.getConnection();
			String sql = " select t.* from (select spc.id , cs.name ,"
				+ " spc.commodityid,spc.commoditytype, spc.adddate, "
				+ " cp.courseoldprice as old,cp.coursenowprice as now,spc.count"
				+ " from (shoppingcart spc left"
				+ " join course cs on cs.id =spc.commodityid) left join"
				+ " course_price cp"
				+ " on cp.courseid=spc.commodityid where Commoditytype = 1 and"
				+ " spc.userid=? and spc.id=? "
				
				+ " union all "
				
				+ " select spc.id , ec.name , spc.commodityid,spc.commoditytype,"
				+ " spc.adddate, elcp.elclassoldprice as old ,"
				+ " "
				+ " elcp.elclassnowprice as now ,"
				+ " spc.count from shoppingcart spc " +
						" left join elclass ec on ec.id = spc.commodityid " +
						" left join elclass_price elcp on ec.id = elcp.elclassid  "
				+ " where spc.commoditytype = 2 and spc.userid=? and spc.id=? "
				
				+ " union all "
				
				+ " select spc.id,bi.name"
				+ " ,spc.commodityid,spc.commoditytype,spc.adddate,bi.marketprice as"
				+ " old,bi.vipprice as now,spc.count"
				+ " from bookinfo bi, shoppingcart spc where spc.userid=? and"
				+ " bi.id=spc.commodityid and spc.commoditytype=4 and spc.id=? "
				
				+ " union all "
				
				+ " select"
				+ " spc.id,b.name,spc.commodityid,spc.commoditytype,spc.adddate,b.shichangjia"
				+ " as old,b.huiyuanjia as now,spc.count "
				+ " from product b,shoppingcart spc where spc.userid=? and"
				+ " b.id=spc.commodityid and spc.commoditytype=3  and spc.id=?  " 
				
				+ " union all " 
				
				+ " select spc.id,r.title,spc.commodityid,spc.commoditytype,spc.adddate,erp.examroomoldprice as old," 
				+ " erp.examroomnowprice as new,spc.count "
				+ " from exam_room r,shoppingcart spc,exam_room_price erp where spc.userid=? and r.id=erp.examroomid and r.id=spc.commodityid and spc.commoditytype=5 and spc.id=? "
				+	")t order by t.adddate asc ";
			logger.info(sql);
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, orderid);
			ps.setInt(3, userid);
			ps.setInt(4, orderid);
			ps.setInt(5, userid);
			ps.setInt(6, orderid);
			ps.setInt(7, userid);
			ps.setInt(8, orderid);
			ps.setInt(9, userid);
			ps.setInt(10, orderid);
			rs = ps.executeQuery();
			while (rs.next()) {
				Commodity sc = new Commodity();
				sc.setId(rs.getInt(1));
				sc.setCommodityName(rs.getString(2));
				sc.setCommodityid(rs.getInt(3));
				sc.setCommoditytype(rs.getInt(4));
				sc.setAdddate(rs.getTimestamp(5));
				sc.setOldp(rs.getFloat(6));
				sc.setNowp(rs.getFloat(7));
				sc.setCount(rs.getInt(8));
				sc.setAllp(sc.getNowp() * sc.getCount());
				listsc.add(sc);

			}

			return listsc;
		} catch (Exception e) {
			logger.error("根据订单得到用户购物车信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

}
