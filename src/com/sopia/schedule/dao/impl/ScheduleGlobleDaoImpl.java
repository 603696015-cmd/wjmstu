package com.sopia.schedule.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.sopia.schedule.dao.impl.ModuleManageDaoImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.schedule.dao.ScheduleGlobleDao;
import com.sopia.schedule.entities.Gzrz;
import com.sopia.schedule.entities.Kehu;
import com.sopia.schedule.entities.Production_efficiency;
import com.sopia.schedule.entities.Tags;
import com.sopia.schedule.entities.Wupin;
import com.sopia.schedule.entities.Xiangmu;

public class ScheduleGlobleDaoImpl implements ScheduleGlobleDao {
	private static final Log logger = LogFactory
			.getLog(ScheduleGlobleDaoImpl.class);

	public List<Kehu> getKehuList(String tablename,ElNode department) throws ElException {
		List<Kehu> list = new ArrayList<Kehu>();
		Kehu kehu;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select KHDA_KHJD,KHDA_YQJE,khda_sjje from " + tablename + " t,eluser e,department d " 
			+ " join ( "
			+ ((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
					.generateSQLByTree("department", department, true)
			+ " ) dep on dep.id=d.id " + " where e.depid=d.id and t.userid=e.id ";
			ps = ct.prepareStatement(sql);

			rs = ps.executeQuery();
			while (rs.next()) {
				kehu = new Kehu();
				kehu.setKHDA_KHJD(rs.getString("khda_khjd"));
				kehu.setKHDA_YQJE(rs.getDouble("khda_yqje"));
				kehu.setKHDA_SJJE(rs.getDouble("khda_sjje"));
				list.add(kehu);
			}
		} catch (Exception e) {
			logger.error("客户档案查询列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	public List<Kehu> getKHDA_KHJDList(String tablename) throws ElException {
		List<Kehu> list = new ArrayList<Kehu>();
		Kehu kehu;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select distinct KHDA_KHJD from " + tablename;
			ps = ct.prepareStatement(sql);

			rs = ps.executeQuery();
			while (rs.next()) {
				kehu = new Kehu();
				kehu.setKHDA_KHJD(rs.getString("khda_khjd"));
				list.add(kehu);
			}
		} catch (Exception e) {
			logger.error("客户档案查询列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	public double gethejiByTablename(String tablename, Timestamp starttime,
			Timestamp endtime) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlAppend = "";
		double returnValue = 0.0;
		try {
			ct = DBConnection.getConnection();

			if (starttime != null)
				sqlAppend = sqlAppend
						+ " and to_char(SK_SJRQ,'yyyy-MM-dd HH:mm:ss') > '"
						+ starttime + "'";
			if (endtime != null)
				sqlAppend = sqlAppend
						+ " and to_char(SK_SJRQ,'yyyy-MM-dd HH:mm:ss') < '"
						+ endtime + "'";

			if (tablename.equals("SK")) {
				sql = "select SK_SKJE from " + tablename
						+ " where SK_SKLX != '其他收入'" + sqlAppend;
			} else if (tablename.equals("FK")) {
				sql = "select FK_FKJE from " + tablename
						+ " where FK_FKLX != '其他支出'" + sqlAppend;
			}
			ps = ct.prepareStatement(sql);

			rs = ps.executeQuery();
			while (rs.next()) {
				returnValue += rs.getDouble(1);
			}
		} catch (Exception e) {
			logger.error("收款、付款模块根据tablename查询合计金额出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return returnValue;
	}

	public List<Gzrz> getGzrzList(ELUser elUser, Timestamp starttime,
			Timestamp endtime, String tablename, String orderBy,
			String ordersc, ElNode department, int pageNow, int pageSize)
			throws ElException {
		List<Gzrz> list = new ArrayList<Gzrz>();
		Gzrz gzrz;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;

		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		Connection ct1 = null;
		String sql = "";
		String sql1 = "";
		String sqlAppend = "";
		String sqlAppend1 = "";
		String sqlWhere = "";
		int userid = 0;
		int depid = 0;
		try {
			// ct1 = DBConnection.getConnection();
			// sql1 = "select distinct(userid) from " + tablename;

			ct = DBConnection.getConnection();

			if (orderBy != null && !orderBy.equals("")) {
				if(orderBy.indexOf("t.")>=0){
					sqlAppend1 = " order by " + orderBy + " ";
					if (ordersc != null && !ordersc.equals("")) {
						if(ordersc.equals("asc"))	ordersc = "desc";
						else if(ordersc.equals("desc")) ordersc = "asc";
						sqlAppend1 += " "+ordersc + " ";
					}
				}else {
					sqlAppend = " order by " + orderBy + " ";
					if (ordersc != null && !ordersc.equals("")) {
						if(ordersc.equals("asc"))	ordersc = "desc";
						else if(ordersc.equals("desc")) ordersc = "asc";
						sqlAppend += " "+ordersc + " ";
					}
				}

			}
			if (elUser != null) {
				if (elUser.getRealname() != null
						&& !elUser.getRealname().equals("")) {
					sqlWhere += " and realname like '" + elUser.getRealname()
							+ "%'";
				}
				if (elUser.getUsername() != null
						&& !elUser.getUsername().equals("")) {
					sqlWhere += " and username like '" + elUser.getUsername()
							+ "%'";
				}

			}
			if (starttime != null) {
				sqlWhere += sqlWhere
						+ " and to_char(GRRZ_TXRQ,'yyyy-MM-dd HH:mm:ss') > '"
						+ starttime + "'";
			}
			if (endtime != null) {
				sqlWhere += sqlWhere
						+ " and to_char(GRRZ_TXRQ,'yyyy-MM-dd HH:mm:ss') > '"
						+ endtime + "'";
			}

			// sql = "select b.*,rn from(select a.*,rownum rn from (" +
			// " select t.userid,sum(t.GRRZ_ZWPF) as GRRZ_ZWPF,sum(t.GRRZ_BMPF)
			// as GRRZ_BMPF,sum(t.GRRZ_LDPF) as GRRZ_LDPF from "
			// + tablename
			// + " t, eluser e,department d "
			// + " join ( " +
			// ((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
			// .generateSQLByTree("department", department, true) +
			// " ) dep on dep.id=d.id " +
			// " where e.id=t.userid and e.depid=d.id " + sqlWhere + " group by
			// userid "
			// + sqlAppend
			// + " ) a where rownum<=? ) b where rn<=?";
			sql = "select b.*,rn from(select a.*,rownum rn from ("
					+ " select e.id,e.realname,d.name from eluser e ,department d "
					+ " join ( "
					+ ((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
							.generateSQLByTree("department", department, true)
					+ " ) dep on dep.id=d.id " + " where e.depid=d.id "
					+ sqlWhere + " " + sqlAppend
					+ " ) a where rownum<=? ) b where rn>=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				gzrz = new Gzrz();
				userid = rs.getInt(1);
				gzrz.setName(rs.getString(2));
				gzrz.setDep(rs.getString(3));

				sql1 = " select sum(t.GRRZ_ZWPF) as GRRZ_ZWPF,sum(t.GRRZ_BMPF) as GRRZ_BMPF,sum(t.GRRZ_LDPF) as GRRZ_LDPF from "
						+ tablename + " t where userid = " + userid + sqlAppend1;
				ps = ct.prepareStatement(sql1);
				rs1 = ps.executeQuery();
				if (rs1.next()) {
					gzrz.setByme(rs1.getDouble("GRRZ_ZWPF"));
					gzrz.setBydep(rs1.getDouble("GRRZ_BMPF"));
					gzrz.setLeader(rs1.getDouble("GRRZ_LDPF"));
				}

				// ct1 = DBConnection.getConnection();
				// sql = "select realname,depid from eluser where id = "+
				// userid;
				// ps = ct.prepareStatement(sql);
				// rs1 = ps.executeQuery();
				// if(rs1.next()){
				// gzrz.setName(rs1.getString(1));
				// depid = rs1.getInt(2);
				// }
				// sql = "select name from department where id = " +depid;
				// ps = ct.prepareStatement(sql);
				// rs1 = ps.executeQuery();
				// if(rs1.next()){
				// gzrz.setDep(rs1.getString(1));
				// }
				list.add(gzrz);
			}
		} catch (Exception e) {
			logger.error("日志评分统计查询列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	public int select_my_tableinfo_by_userid_count(int type,
			List<Tags> list_tags, Map<String, String> hm, String tablename,
			int userid, int type_) throws ElException {
		int count = 0;

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		if (type == 1) {
			sql = " select count(*) from " + tablename + " where 1 = 1 ";
		} else {

			sql = " select count(*) from " + tablename + " where userid="
					+ userid;
		}

		String sqlwhere = "";
		try {
			ct = DBConnection.getConnection();

			// sqlwhere
			Iterator iterator = hm.entrySet().iterator();
			while (iterator.hasNext()) {
				java.util.Map.Entry entry = (java.util.Map.Entry) iterator
						.next();
				// entry.getKey() 返回与此项对应的键
				// entry.getValue() 返回与此项对应的值
				String str[] = ((String) entry.getKey()).split("==");
				// sqlwhere +=str[1]+"=";

				if (str[0].equals("number") || str[0].equals("float")) {
					// sqlwhere += " and " + str[1] + "="
					// + (String) entry.getValue() + " ";

					if (str[1].lastIndexOf("_") + 1 == str[1].length()) {
						sqlwhere += " and "
								+ str[1].substring(0, str[1].lastIndexOf("_"))
								+ "<=" + (String) entry.getValue() + " ";
					} else {
						sqlwhere += " and " + str[1] + ">="
								+ (String) entry.getValue() + " ";
					}
				} else if (str[0].indexOf("varchar2") > -1) {
					sqlwhere += " and " + str[1] + " like '%"
							+ (String) entry.getValue() + "%' ";
				} else if (str[0].equals("date")) {
					if (str[1].lastIndexOf("_") + 1 == str[1].length())//
					{
						sqlwhere += " and "
								+ str[1].substring(0, str[1].lastIndexOf("_"))
								+ "<= to_date('" + (String) entry.getValue()
								+ "','yyyy-mm-dd hh24:mi:ss') ";
					} else {
						sqlwhere += " and " + str[1] + ">= to_date('"
								+ (String) entry.getValue()
								+ "','yyyy-mm-dd hh24:mi:ss') ";
					}

				}
				// 相关字段
				else if (str[0].equals("relate_type")) {

					// sqlwhere += " and "+str[3]+" in ( select id from
					// "+str[1]+" where "+str[2]+" like '"+(String)
					// entry.getValue()+"%' )";
					sqlwhere += " and "
							+ str[3]
							+ " is not null and id in (select mainid from tb_tags_relate where relateid in "
							+ " ( select id from " + str[1] + " where "
							+ str[2] + " like '%" + (String) entry.getValue()
							+ "%')) ";
				}
			}

			if (type_ == 1) {
				sqlwhere += " and SK_SKLX != '其他收入' ";
			} else if (type_ == 2) {
				sqlwhere += " and SK_SKLX = '其他收入' ";
			} else if (type_ == 3) {
				sqlwhere += " and (FK_FKLX != '工资支出' or FK_FKLX != '其他支出') ";
			} else if (type_ == 4) {
				sqlwhere += " and FK_FKLX = '工资支出' ";
			} else if (type_ == 5) {
				sqlwhere += " and FK_FKLX = '其他支出' ";
			}

			ps = ct.prepareStatement(sql + sqlwhere);
			rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("客户添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}

	public List<Map<String, String>> select_my_tableinfo_by_userid_order(
			String sqlAppend, int type, List<Tags> list_tags, String tablename,
			Map<String, String> hm, int userid, String order, int pageNow,
			int pageSize, int type_) throws ElException {
		boolean flag_colume = true;
		Map<String, String> map = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;

		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		Connection ct2 = null;

		// String sqlBegin = " SELECT * FROM ( " + " SELECT A.*, ROWNUM RN "
		// + " FROM ( ";
		// String sql = " select id,status";
		// String sqlcolumn = " ";
		// String sqltablename = " from " + tablename + "";
		// String sqlwhere = " where userid=? ";
		// String sqlorder = " order by id desc ";
		// String sqlEnd = " ) A " + " WHERE ROWNUM <= ? " + " )WHERE RN >= ? ";

		String sqlBegin = "";
		if (pageSize != 0 && pageNow != 0) {
			sqlBegin = " SELECT * FROM ( " + " SELECT A.*, ROWNUM RN "
					+ " FROM ( ";
		} else {
			sqlBegin = "";
		}
		String sql = " select t.id,t.status,d.name department,e.username username  ";
		String sqlcolumn = "  ";
		String sqltablename = " from " + tablename
				+ " t,eluser e,department d ";
		String sqlwhere = "";
		if (type == 1) {
			sqlwhere = " where e.id=t.userid and d.id=e.depid ";
			if (!sqlAppend.equals("")) {
				sqlwhere += sqlAppend;
			}
		} else {
			sqlwhere = " where userid=? and e.id=t.userid and d.id=e.depid  ";
			if (!sqlAppend.equals("")) {
				sqlwhere += sqlAppend;
			}
		}
		String sqlorder = "  order by id desc ";
		String sqlEnd = "";
		if (pageSize != 0 && pageNow != 0) {
			sqlEnd = "    ) A " + " WHERE ROWNUM <= ? " + " )WHERE RN >= ? ";
		} else {
			sqlEnd = " ";
		}

		if (order != null) {
			if (!order.equals("")) {
				sqlorder = order;
			}
		}

		// 选择显示选择相关的字段

		for (int i = 0; i < list_tags.size(); i++) {

			if (list_tags.get(i).getList_display() == 1) {

				sqlcolumn += ",";
				// if (list_tags.get(i).getBiaojianqiuhe_check() != 1) {
				if (list_tags.get(i).getColumn_type().equals("date")) {
					sqlcolumn += " to_char("
							+ list_tags.get(i).getColumn_name()
							+ ",'yyyy-mm-dd') "
							+ list_tags.get(i).getColumn_name();
				} else {
					sqlcolumn += " " + list_tags.get(i).getColumn_name() + " ";
				}
				// } else {
				if (String.valueOf(sqlcolumn.charAt(sqlcolumn.length() - 1))
						.equals(","))
					sqlcolumn = sqlcolumn.substring(0, sqlcolumn
							.lastIndexOf(","));
				// }
			}

		}

		// sqlwhere
		Iterator iterator = hm.entrySet().iterator();
		while (iterator.hasNext()) {
			java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
			// entry.getKey() 返回与此项对应的键
			// entry.getValue() 返回与此项对应的值
			String str[] = ((String) entry.getKey()).split("==");
			// sqlwhere +=str[1]+"=";

			if (str[0].equals("number") || str[0].equals("float")) {
				// sqlwhere += " and " + str[1] + "=" + (String)
				// entry.getValue()
				// + " ";
				if (str[1].lastIndexOf("_") + 1 == str[1].length()) {
					sqlwhere += " and "
							+ str[1].substring(0, str[1].lastIndexOf("_"))
							+ "<=" + (String) entry.getValue() + " ";
				} else {
					sqlwhere += " and " + str[1] + ">="
							+ (String) entry.getValue() + " ";
				}
			} else if (str[0].indexOf("varchar2") > -1)// 字符串
			{
				// sqlwhere += " and " + str[1] + "='" + (String)
				// entry.getValue()
				// + "' ";
				sqlwhere += " and " + str[1] + " like '%"
						+ (String) entry.getValue() + "%' ";
			} else if (str[0].equals("date")) {
				if (str[1].lastIndexOf("_") + 1 == str[1].length())//
				{
					sqlwhere += " and "
							+ str[1].substring(0, str[1].lastIndexOf("_"))
							+ "<= to_date('" + (String) entry.getValue()
							+ "','yyyy-mm-dd hh24:mi:ss') ";
					// sqlorder =" ordey by "+str[1].substring(0,
					// str[1].lastIndexOf("_"));
				} else {
					sqlwhere += " and " + str[1] + ">= to_date('"
							+ (String) entry.getValue()
							+ "','yyyy-mm-dd hh24:mi:ss') ";
					// sqlorder =" ordey by "+str[1];
				}

			}
			// 相关字段
			else if (str[0].equals("relate_type")) {
				// relate_type==目标表名==目标列名==列名 条件
				// sqlwhere += " and "+str[3]+" in ( select id from "+str[1]+"
				// where "+str[2]+" like '"+(String) entry.getValue()+"%' )";

				// " and (select count(*) from tb_tags_relate where relateid in
				// ( select id from "+str[1]+" where "+str[2]+" like '"+(String)
				// entry.getValue()+"%' ) and columnname="str[3]"
				// )>0 "
				sqlwhere += " and "
						+ str[3]
						+ " is not null and t.id in (select mainid from tb_tags_relate where relateid in "
						+ " ( select id from " + str[1] + " where " + str[2]
						+ " like '%" + (String) entry.getValue() + "%')) ";
				// and id in (select mainid from tb_tags_relate where relateid
				// in(select id ))

			}
		}

		if (type_ == 1) {
			sqlwhere += " and SK_SKLX != '其他收入' ";
		} else if (type_ == 2) {
			sqlwhere += " and SK_SKLX = '其他收入' ";
		} else if (type_ == 3) {
			sqlwhere += " and (FK_FKLX != '工资支出' or FK_FKLX != '其他支出') ";
		} else if (type_ == 4) {
			sqlwhere += " and FK_FKLX = '工资支出' ";
		} else if (type_ == 5) {
			sqlwhere += " and FK_FKLX = '其他支出' ";
		}

		try {
			ct = DBConnection.getConnection();
			ct2 = DBConnection.getConnection();
			ps = ct.prepareStatement(sqlBegin + sql + sqlcolumn + sqltablename
					+ sqlwhere + sqlorder + sqlEnd);
			if (type == 1) {
				if (pageSize != 0 && pageNow != 0) {
					ps.setInt(1, pageNow);
					ps.setInt(2, pageSize);
				}
			} else {

				ps.setInt(1, userid);
				if (pageSize != 0 && pageNow != 0) {
					ps.setInt(2, pageNow);
					ps.setInt(3, pageSize);
				}
			}

			rs = ps.executeQuery();
			while (rs.next()) {
				map = new HashMap<String, String>();
				for (int i = 0; i < list_tags.size(); i++) {
					if (list_tags.get(i).getList_display() == 1) {
						if (list_tags.get(i).getColumn_type().equals("number")) {
							// if (list_tags.get(i).getBiaojianqiuhe_check() !=
							// 1) {
							map.put(list_tags.get(i).getColumn_name(), String
									.valueOf(Math.ceil(rs.getFloat(list_tags
											.get(i).getColumn_name()))));
							// }

						}
						if (list_tags.get(i).getColumn_type().equals("float")) {
							// if (list_tags.get(i).getBiaojianqiuhe_check() !=
							// 1) {
							map.put(list_tags.get(i).getColumn_name(), String
									.valueOf(rs.getFloat(list_tags.get(i)
											.getColumn_name())));
							// }
						} else {
							if (list_tags.get(i).getDisplay_type().equals(
									"相关字段")) {
								// String
								// id=rs.getString(list_tags.get(i).getColumn_name());//获取相关字段值，序列id值
								// if(id==null) continue;
								// if(id.equals("")) continue;
								// String
								// str=list_tags.get(i).getDefault_value();
								// String
								// arr[]=str.split("==");//tb_mm_28==tb_mm_28_171==反反复复==varchar2(500)
								// String sql_relate=" select "+arr[1]+" from
								// "+arr[0]+" where id="+id;
								// ps2 = ct2.prepareStatement(sql_relate);
								// rs2 = ps2.executeQuery();
								// if(rs2.next())
								// {
								// list_tags.get(i).setValue(rs2.getString(1));
								// }
								// map.put(list_tags.get(i).getColumn_name(),list_tags.get(i).getValue()
								// );
								String returnvalue = "";
								String id = rs.getString(list_tags.get(i)
										.getColumn_name());// 获取相关字段值，序列id值
								if (id == null)
									continue;
								if (id.equals(""))
									continue;
								// String idvalues[]=id.split(",");
								String str = list_tags.get(i)
										.getDefault_value();
								String arr[] = str.split("==");// tb_mm_28==tb_mm_28_171==反反复复==varchar2(500)
								String sql_relate = " select " + arr[1]
										+ " from " + arr[0] + " where id in ("
										+ id + ")";

								ps2 = ct2.prepareStatement(sql_relate);
								rs2 = ps2.executeQuery();
								while (rs2.next()) {
									// list_tags.get(i).setValue(rs2.getString(1));
									returnvalue += rs2.getString(1);
									// if(!rs2.isLast())
									returnvalue += "<br>";
								}

								map.put(list_tags.get(i).getColumn_name(),
										returnvalue);

							} else if (list_tags.get(i).getDisplay_type()
									.equals("相关负责人")) {
								String returnvalue = "";
								String id = rs.getString(list_tags.get(i)
										.getColumn_name());// 获取相关字段值，序列id值
								if (id == null)
									continue;
								if (id.equals(""))
									continue;
								// String idvalues[]=id.split(",");
								String sql_relate = " select  realname "
										+ " from eluser where id in (" + id
										+ ")";

								ps2 = ct2.prepareStatement(sql_relate);
								rs2 = ps2.executeQuery();
								while (rs2.next()) {
									// list_tags.get(i).setValue(rs2.getString(1));
									returnvalue += rs2.getString(1);
									// if(!rs2.isLast())
									returnvalue += "<br>";
								}

								map.put(list_tags.get(i).getColumn_name(),
										returnvalue);

							} else {
								// string
								map.put(list_tags.get(i).getColumn_name(), rs
										.getString(list_tags.get(i)
												.getColumn_name()));
							}
						}
					}// if
				}// for
				map.put("id", String.valueOf(rs.getInt("id")));
				map.put("status", rs.getString("status"));

				map.put("username", rs.getString("username"));
				map.put("name", rs.getString("department"));

				list.add(map);
				// System.out.println();
			}// while
		} catch (Exception e) {
			logger.error("客户添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}
	
	public String getZhibiaoColumn(String tablename) throws ElException{
		return tablename + "_ZBSJ";
		
	}

//	public List<Wupin> getWupinList(String tablename, int pageNow,
//			int pageSize, String groupBy, Map<String, Object> map)
//			throws ElException {
//		List<Wupin> list = new ArrayList<Wupin>();
//		Wupin wupin;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		ResultSet rs1 = null;
//		ResultSet rs2 = null;
//		Connection ct = null;
//		String sql = "";
//		String sql1 = "";
//		String sqlAppend = "";
//		String sqlWhere = "";
//		String sqlColumn = "";
//		String ids = "";
//		int danjuid = 0;
//		String table = "";
//		String zhibiao = "";//每个表的制表字段
//		String sqltime = "";
//		String id = "";
//		String sqlin = "";
//		try {
//			ct = DBConnection.getConnection();
//
//			if (groupBy != null) {//合并仓库
//				sqlAppend = "group by WPGCB2_MC";
//			} else {
//				sqlAppend = " group by WPGCB2_CK,WPGCB2_MC ";
//				sqlColumn += "WPGCB2_CK,";
//			}
//
//			if (map != null) {
//				if (map.get("moduleName") != null
//						&& !((String) map.get("moduleName")).equals("")) {
//					sqlWhere += " and moduleid = '"
//							+ ((String) map.get("moduleName")).split("==")[0]
//							+ "' ";
//				}
//				if (map.get("status") != null
//						&& !((String) map.get("status")).equals("")) {
//					sqlWhere += " and status = '" + (String) map.get("status")
//							+ "' ";
//				}
//				if (map.get("cangkuname") != null
//						&& !((String) map.get("cangkuname")).equals("")) {
//					sqlWhere += " and WPGCB2_CK = '"
//							+ (String) map.get("cangkuname") + "' ";
//				}
//				if (map.get("wupinname") != null
//						&& !((String) map.get("wupinname")).equals("")) {
//					sqlWhere += " and WPGCB2_MC like '%"
//							+ (String) map.get("wupinname") + "%' ";
//				}
//				 if(map.get("starttime") != null || map.get("endtime") != null){
//					 sql1 = "select danjuid,moduleid from " + tablename + " group by danjuid,moduleid";
//					 ps = ct.prepareStatement(sql1);
//					 rs1 = ps.executeQuery();
//					while (rs1.next()) {
//						sqltime = "";
//						danjuid = rs1.getInt("danjuid");
//						table = rs1.getString("moduleid");
////						id = String.valueOf(rs1.getInt("id"));
//						zhibiao = this.getZhibiaoColumn(table);
//						if(danjuid!= 0 && table != null && !table.equals("")){
//							if(map.get("starttime") != null){
//								sqltime += " and to_char("+zhibiao+",'yyyy-MM-dd HH:mm:ss') > '"
//								+ (Timestamp)map.get("starttime") + "' ";
//							}
//							if(map.get("endtime") != null){
//								sqltime += " and to_char("+zhibiao+",'yyyy-MM-dd HH:mm:ss') < '"
//								+ (Timestamp)map.get("endtime") + "' ";
//							}
//							sql1 = "select id from " + table +" where 1=1 " + sqltime;
//							 ps = ct.prepareStatement(sql1);
//							 rs2 = ps.executeQuery();
//							 if(rs2.next()){
//								 id = String.valueOf(rs2.getInt("id"));
//								 ids += id + ",";
//							 }
//						}
//					}
//				 }
//			}
//			
//			if(ids != null ){
//				if(!ids.equals("")){
//					ids = ids.substring(0, ids.lastIndexOf(","));
//					sqlin = " and entityid in ("+ids+")";
//				}else {
//					if(map.get("starttime") != null || map.get("endtime") != null)
//						sqlin = " and 1!=1";
//				}
//			}
//
//			sql = " select b.*,rn from (select a.*,rownum rn from (select "
//					+ sqlColumn
//					+ " WPGCB2_MC,sum(WPGCB2_XSSL) as WPGCB2_XSSL,sum(WPGCB2_SPZJ) as WPGCB2_SPZJ,"
//					+ " sum(WPGCB2_XSTHSL) as WPGCB2_XSTHSL,sum(WPGCB2_XSTHSPZJ) as WPGCB2_XSTHSPZJ,sum(WPGCB2_CGSL) as WPGCB2_CGSL,sum(WPGCB2_CGSPZJ) as WPGCB2_CGSPZJ,"
//					+ " sum(WPGCB2_THSL) as WPGCB2_THSL,sum(WPGCB2_THSPZJ) as WPGCB2_THSPZJ,sum(WPGCB2_CKSL) as WPGCB2_CKSL,sum(WPGCB2_RKSL) as WPGCB2_RKSL,"
//					+ " sum(WPGCB2_SCJ) as WPGCB2_SCJ,sum(WPGCB2_CL) as WPGCB2_CL,count(id) as id"
//					+ " from (select * from "+tablename+" where 1=1 "+sqlin+") where 1=1 " + sqlWhere + " "
//					+	 sqlAppend + " ) a where rownum<=?) b where rn>=? ";
//			ps = ct.prepareStatement(sql);
//			ps.setInt(1, pageNow);
//			ps.setInt(2, pageSize);
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				wupin = new Wupin();
//				wupin.setWupinname(rs.getString("WPGCB2_MC"));
//
//				if (groupBy == null) {
//					wupin.setCangku(rs.getString("WPGCB2_CK"));
//				}
//
//				wupin.setXiaoshoushuliang(rs.getInt("WPGCB2_XSSL"));
//				wupin.setXiaoshouzongjia(rs.getDouble("WPGCB2_SPZJ"));
//				wupin.setXiaoshoutuihuoshuliang(rs.getInt("WPGCB2_XSTHSL"));
//				wupin.setXiaoshoutuihuozongjia(rs.getDouble("WPGCB2_XSTHSPZJ"));
//				wupin.setCaigoushuliang(rs.getInt("WPGCB2_CGSL"));
//				wupin.setCaigouzongjia(rs.getDouble("WPGCB2_CGSPZJ"));
//				wupin.setCaigoutuihuoshuliang(rs.getInt("WPGCB2_THSL"));
//				wupin.setCaigoutuihuozongjia(rs.getDouble("WPGCB2_THSPZJ"));
//				wupin.setChukushuliang(rs.getInt("WPGCB2_CKSL"));
//				wupin.setRukushuliang(rs.getInt("WPGCB2_RKSL"));
//				wupin.setCunliang(rs.getInt("WPGCB2_RKSL")
//						- rs.getInt("WPGCB2_CKSL"));
//				wupin.setShichangjia(rs.getDouble("WPGCB2_SCJ")
//						/ rs.getInt("id"));
//				wupin.setZongjia((rs.getInt("WPGCB2_RKSL") - rs
//						.getInt("WPGCB2_CKSL"))
//						* rs.getDouble("WPGCB2_SCJ"));
//				list.add(wupin);
//			}
//		} catch (Exception e) {
//			logger.error("物品过程中心查询出错！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return list;
//	}
	
	public List<Wupin> getWupinList(String tablename, int pageNow,
			int pageSize, String groupBy, Map<String, Object> map)
			throws ElException {
		List<Wupin> list = new ArrayList<Wupin>();
		Wupin wupin;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		Connection ct = null;
		String sql = "";
		String sql1 = "";
		String sqlAppend = "";
		String sqlWhere = "";
		String sqlColumn = "";
		String ids = "";
		int danjuid = 0;
		String table = "";
		String zhibiao = "";//每个表的制表字段
		String sqltime = "";
		String id = "";
		String sqlin = "";
		try {
			ct = DBConnection.getConnection();

//			if (groupBy != null) {//合并仓库
//				sqlAppend = "group by WPGCB2_MC";
//			} else {
//				sqlAppend = " group by WPGCB2_CK,WPGCB2_MC ";
//				sqlColumn += "WPGCB2_CK,";
//			}
			
			sqlAppend = " group by WPGCB2_CK,WPGCB2_MC ";
			sqlColumn += "WPGCB2_CK,";

			if (map != null) {
				if (map.get("moduleName") != null
						&& !((String) map.get("moduleName")).equals("")) {
					sqlWhere += " and moduleid = '"
							+ ((String) map.get("moduleName")).split("==")[0]
							+ "' ";
				}
				if (map.get("status") != null
						&& !((String) map.get("status")).equals("")) {
					sqlWhere += " and status = '" + (String) map.get("status")
							+ "' ";
				}
				if (map.get("cangkuname") != null
						&& !((String) map.get("cangkuname")).equals("")) {
					sqlWhere += " and WPGCB2_CK = '"
							+ (String) map.get("cangkuname") + "' ";
				}
				if (map.get("wupinname") != null
						&& !((String) map.get("wupinname")).equals("")) {
					sqlWhere += " and WPGCB2_MC like '%"
							+ (String) map.get("wupinname") + "%' ";
				}
				 if(map.get("starttime") != null || map.get("endtime") != null){
					 sql1 = "select danjuid,moduleid from " + tablename + " group by danjuid,moduleid";
					 ps = ct.prepareStatement(sql1);
					 rs1 = ps.executeQuery();
					while (rs1.next()) {
						sqltime = "";
						danjuid = rs1.getInt("danjuid");
						table = rs1.getString("moduleid");
//						id = String.valueOf(rs1.getInt("id"));
						zhibiao = this.getZhibiaoColumn(table);
						if(danjuid!= 0 && table != null && !table.equals("")){
							if(map.get("starttime") != null){
								sqltime += " and to_char("+zhibiao+",'yyyy-MM-dd HH:mm:ss') > '"
								+ (Timestamp)map.get("starttime") + "' ";
							}
							if(map.get("endtime") != null){
								sqltime += " and to_char("+zhibiao+",'yyyy-MM-dd HH:mm:ss') < '"
								+ (Timestamp)map.get("endtime") + "' ";
							}
							sql1 = "select id from " + table +" where 1=1 " + sqltime;
							 ps = ct.prepareStatement(sql1);
							 rs2 = ps.executeQuery();
							 if(rs2.next()){
								 id = String.valueOf(rs2.getInt("id"));
								 ids += id + ",";
							 }
						}
					}
				 }
			}
			
			if(ids != null ){
				if(!ids.equals("")){
					ids = ids.substring(0, ids.lastIndexOf(","));
					sqlin = " and entityid in ("+ids+")";
				}else {
					if(map.get("starttime") != null || map.get("endtime") != null)
						sqlin = " and 1!=1";
				}
			}

			sql = " select b.*,rn from (select a.*,rownum rn from (select "
					+ " WPGCB2_CK,WPGCB2_MC,sum(WPGCB2_XSSL) as WPGCB2_XSSL,sum(WPGCB2_SPZJ) as WPGCB2_SPZJ,"
					+ " sum(WPGCB2_XSTHSL) as WPGCB2_XSTHSL,sum(WPGCB2_XSTHSPZJ) as WPGCB2_XSTHSPZJ,sum(WPGCB2_CGSL) as WPGCB2_CGSL,sum(WPGCB2_CGSPZJ) as WPGCB2_CGSPZJ,"
					+ " sum(WPGCB2_THSL) as WPGCB2_THSL,sum(WPGCB2_THSPZJ) as WPGCB2_THSPZJ,sum(WPGCB2_CKSL) as WPGCB2_CKSL,sum(WPGCB2_RKSL) as WPGCB2_RKSL,"
					+ " sum(WPGCB2_SCJ) as WPGCB2_SCJ,sum(WPGCB2_CL) as WPGCB2_CL,count(id) as id"
					+ " from (select * from "+tablename+" where 1=1 "+sqlin+") where 1=1 " + sqlWhere + " "
					+	 sqlAppend + " ) a where rownum<=?) b where rn>=? ";
			
			
			if(groupBy != null){
				sql = "select " +
						"WPGCB2_MC,"+
                               "sum(WPGCB2_XSSL) as WPGCB2_XSSL,"+
                               "sum(WPGCB2_SPZJ) as WPGCB2_SPZJ,"+
                               "sum(WPGCB2_XSTHSL) as WPGCB2_XSTHSL,"+
                               "sum(WPGCB2_XSTHSPZJ) as WPGCB2_XSTHSPZJ,"+
                               "sum(WPGCB2_CGSL) as WPGCB2_CGSL,"+
                               "sum(WPGCB2_CGSPZJ) as WPGCB2_CGSPZJ,"+
                               "sum(WPGCB2_THSL) as WPGCB2_THSL,"+
                               "sum(WPGCB2_THSPZJ) as WPGCB2_THSPZJ,"+
                               "sum(WPGCB2_CKSL) as WPGCB2_CKSL,"+
                               "sum(WPGCB2_RKSL) as WPGCB2_RKSL,"+
                               "sum(WPGCB2_SCJ) as WPGCB2_SCJ,"+
                               "sum(WPGCB2_CL) as WPGCB2_CL,"+
                               "count(id) as id " +
						" from (" + sql + ") k group by WPGCB2_MC";
			}
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				wupin = new Wupin();
				wupin.setWupinname(rs.getString("WPGCB2_MC"));

				if (groupBy == null) {
					wupin.setCangku(rs.getString("WPGCB2_CK"));
				}
//				wupin.setCangku(rs.getString("WPGCB2_CK"));
				wupin.setXiaoshoushuliang(rs.getInt("WPGCB2_XSSL"));
				wupin.setXiaoshouzongjia(rs.getDouble("WPGCB2_SPZJ"));
				wupin.setXiaoshoutuihuoshuliang(rs.getInt("WPGCB2_XSTHSL"));
				wupin.setXiaoshoutuihuozongjia(rs.getDouble("WPGCB2_XSTHSPZJ"));
				wupin.setCaigoushuliang(rs.getInt("WPGCB2_CGSL"));
				wupin.setCaigouzongjia(rs.getDouble("WPGCB2_CGSPZJ"));
				wupin.setCaigoutuihuoshuliang(rs.getInt("WPGCB2_THSL"));
				wupin.setCaigoutuihuozongjia(rs.getDouble("WPGCB2_THSPZJ"));
				wupin.setChukushuliang(rs.getInt("WPGCB2_CKSL"));
				wupin.setRukushuliang(rs.getInt("WPGCB2_RKSL"));
				wupin.setCunliang(rs.getInt("WPGCB2_RKSL")
						- rs.getInt("WPGCB2_CKSL"));
				wupin.setShichangjia(rs.getDouble("WPGCB2_SCJ")
						/ rs.getInt("id"));
				wupin.setZongjia((rs.getInt("WPGCB2_RKSL") - rs
						.getInt("WPGCB2_CKSL"))
						* rs.getDouble("WPGCB2_SCJ"));
				list.add(wupin);
			}
		} catch (Exception e) {
			logger.error("物品过程中心查询出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	public int getWupinListSize(String tablename, String groupBy,
			Map<String, Object> map) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		Connection ct = null;
		String sql = "";
		String sql1 = "";
		int returnValue = 0;
		String sqlAppend = "";
		String sqlWhere = "";
		String ids = "";
		int danjuid = 0;
		String table = "";
		String zhibiao = "";//每个表的制表字段
		String sqltime = "";
		String id = "";
		String sqlin = "";
		try {
			ct = DBConnection.getConnection();

			if (groupBy != null) {
				sqlAppend = "group by WPGCB2_MC";
			} else {
				sqlAppend = " group by WPGCB2_CK,WPGCB2_MC ";
			}

			if (map != null) {
				if (map.get("moduleName") != null
						&& !((String) map.get("moduleName")).equals("")) {
					sqlWhere += " and moduleid = '"
							+ ((String) map.get("moduleName")).split("==")[0]
							+ "' ";
				}
				if (map.get("status") != null
						&& !((String) map.get("status")).equals("")) {
					sqlWhere += " and status = '" + (String) map.get("status")
							+ "' ";
				}
				if (map.get("cangkuname") != null
						&& !((String) map.get("cangkuname")).equals("")) {
					sqlWhere += " and WPGCB2_CK = '"
							+ (String) map.get("cangkuname") + "' ";
				}
				if (map.get("wupinname") != null
						&& !((String) map.get("wupinname")).equals("")) {
					sqlWhere += " and WPGCB2_MC like '%"
							+ (String) map.get("wupinname") + "%' ";
				}
				if(map.get("starttime") != null || map.get("endtime") != null){
					 sql1 = "select danjuid,moduleid from " + tablename + " group by danjuid,moduleid";
					 ps = ct.prepareStatement(sql1);
					 rs1 = ps.executeQuery();
					while (rs1.next()) {
						sqltime = "";
						danjuid = rs1.getInt("danjuid");
						table = rs1.getString("moduleid");
						zhibiao = this.getZhibiaoColumn(table);
						if(danjuid!= 0 && table != null && !table.equals("")){
							if(map.get("starttime") != null){
								sqltime += " and to_char("+zhibiao+",'yyyy-MM-dd HH:mm:ss') > '"
								+ (Timestamp)map.get("starttime") + "' ";
							}
							if(map.get("endtime") != null){
								sqltime += " and to_char("+zhibiao+",'yyyy-MM-dd HH:mm:ss') < '"
								+ (Timestamp)map.get("endtime") + "' ";
							}
							sql1 = "select id from " + table +" where 1=1 " + sqltime;
							 ps = ct.prepareStatement(sql1);
							 rs2 = ps.executeQuery();
							 if(rs2.next()){
								 id = String.valueOf(rs2.getInt("id"));
								 ids += id + ",";
							 }
						}
					}
				 }
			}
			
			if(ids != null ){
				if(!ids.equals("")){
					ids = ids.substring(0, ids.lastIndexOf(","));
					sqlin = " and entityid in ("+ids+")";
				}else {
					if(map.get("starttime") != null || map.get("endtime") != null)
						sqlin = " and 1!=1";
				}
			}
			
			sql = " select count(1) from (select count(1) from (select * from WPGCB2 where 1 = 1 "+sqlin+") where 1 = 1  "+sqlWhere+" "+sqlAppend+")";

			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				returnValue = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("物品过程中心查询出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return returnValue;
	}

	public Wupin getQiuheWupin(String tablename) throws ElException {
		Wupin wupin = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select  sum(WPGCB2_XSSL) as WPGCB2_XSSL,sum(WPGCB2_SPZJ) as WPGCB2_SPZJ,sum(WPGCB2_XSTHSL) as WPGCB2_XSTHSL,"
					+ "sum(WPGCB2_XSTHSPZJ) as WPGCB2_XSTHSPZJ,sum(WPGCB2_CGSL) as WPGCB2_CGSL,sum(WPGCB2_CGSPZJ) as WPGCB2_CGSPZJ,"
					+ "sum(WPGCB2_THSL) as WPGCB2_THSL,sum(WPGCB2_THSPZJ) as WPGCB2_THSPZJ,sum(WPGCB2_CKSL) as WPGCB2_CKSL,sum(WPGCB2_RKSL) as WPGCB2_RKSL,sum(WPGCB2_CGSJ) as WPGCB2_CGSJ from "
					+ tablename + " ";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				wupin = new Wupin();
				wupin.setXiaoshoushuliang(rs.getInt("WPGCB2_XSSL"));
				wupin.setXiaoshouzongjia(rs.getDouble("WPGCB2_SPZJ"));
				wupin.setXiaoshoutuihuoshuliang(rs.getInt("WPGCB2_XSTHSL"));
				wupin.setXiaoshoutuihuozongjia(rs.getDouble("WPGCB2_XSTHSPZJ"));
				wupin.setCaigoushuliang(rs.getInt("WPGCB2_CGSL"));
				wupin.setCaigouzongjia(rs.getDouble("WPGCB2_CGSPZJ"));
				wupin.setCaigoutuihuoshuliang(rs.getInt("WPGCB2_THSL"));
				wupin.setCaigoutuihuozongjia(rs.getDouble("WPGCB2_THSPZJ"));
				wupin.setChukushuliang(rs.getInt("WPGCB2_CKSL"));
				wupin.setRukushuliang(rs.getInt("WPGCB2_RKSL"));
				wupin.setZongjia(rs.getDouble("WPGCB2_CGSJ"));
			}
		} catch (Exception e) {
			logger.error("物品过程中心查询出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return wupin;
	}

	public List<String> getCangkuList(String tablename) throws ElException {
		List<String> list = new ArrayList<String>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select distinct(WPGCB2_CK)   from WPGCB2";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("WPGCB2_CK"));
			}
		} catch (Exception e) {
			logger.error("物品过程中心查询出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	public List<String> getModuleList(String tablename) throws ElException {
		List<String> list = new ArrayList<String>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select distinct(moduleid)   from WPGCB2";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				if (rs.getString("moduleid") != null
						&& !rs.getString("moduleid").equals("")) {
					list.add(rs.getString("moduleid")
							+ "=="
							+ new TagsDaoImpl().getModuleNameByTablename(rs
									.getString("moduleid")));
				}
			}
		} catch (Exception e) {
			logger.error("物品过程中心查询出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}
	
	public List<Map<String,String>> getModuleMap(String tablename) throws ElException {
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		Map<String,String> map;
		try {
			ct = DBConnection.getConnection();
			sql = " select distinct(moduleid)   from WPGCB2";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				if (rs.getString("moduleid") != null
						&& !rs.getString("moduleid").equals("")) {
					map = new HashMap<String,String>();
					map.put(rs.getString("moduleid"), new TagsDaoImpl().getModuleNameByTablename(rs
									.getString("moduleid")));
					list.add(map);
//					list.add(rs.getString("moduleid")
//							+ "=="
//							+ new TagsDaoImpl().getModuleNameByTablename(rs
//									.getString("moduleid")));
				}
			}
		} catch (Exception e) {
			logger.error("物品过程中心查询出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	public List<Map<String, Object>> getAccounting(String tablename,
			int pageNow, int pageSize, Xiangmu xiangmu, ElNode department)
			throws ElException {
		List<Map<String, Object>> list_map = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlAppend = "";
		try {
			ct = DBConnection.getConnection();

			if (xiangmu != null) {
				if (xiangmu.getName() != null && !xiangmu.getName().equals("")) {
					sqlAppend += " and XMDA_XMMC like '%" + xiangmu.getName()
							+ "%'";
				}
				if (xiangmu.getFuzeren() != null
						&& !xiangmu.getFuzeren().equals("")) {
					sqlAppend += " and XMDA_XMFZR "
							+ " is not null and id in (select mainid from tb_tags_relate where relateid in "
							+ " ( select id from eluser where realname "
							+ " like '%" + xiangmu.getFuzeren() + "%')) ";
				}
				if (xiangmu.getStarttime() != null)
					sqlAppend += sqlAppend
							+ " and to_char(XMDA_LXRQ,'yyyy-MM-dd HH:mm:ss') > '"
							+ xiangmu.getStarttime() + "'";
				if (xiangmu.getEndtime() != null)
					sqlAppend += sqlAppend
							+ " and to_char(XMDA_LXRQ,'yyyy-MM-dd HH:mm:ss') < '"
							+ xiangmu.getEndtime() + "'";

			}

			sql = " select b.*,rn from (select a.*,rownum rn from (select t.id,t.XMDA_XGZC,t.XMDA_QTSR,t.XMDA_XMMC from "
					+ tablename
					+ " t, eluser e   ,department d "
					+ " join ("
					+ ((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
							.generateSQLByTree("department", department, true)
					+ " ) dep on dep.id=d.id "
					+ " where t.status=9 and e.id = t.userid and e.depid=d.id "
					+ sqlAppend + " ) a where rownum<=?) b where rn>=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				map = new HashMap<String, Object>();
				map.put("id", rs.getInt(1));
				map.put("xiangguanzhichu", rs.getDouble(2));
				map.put("qitashouru", rs.getDouble(3));
				map.put("xiangmumingcheng", rs.getString(4));
				list_map.add(map);
			}
		} catch (Exception e) {
			logger.error("项目核算出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list_map;
	}

	public double getHeByTablenameColumn(String tablename, String columnName,
			String column, int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		double value = 0.0;
		try {
			ct = DBConnection.getConnection();
			sql = " select " + columnName + " from " + tablename + " where "
					+ column + "=" + id;
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				value += rs.getDouble(1);
			}
		} catch (Exception e) {
			logger.error("项目核算出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return value;
	}

	public int getAccountingCount(String tablename, Xiangmu xiangmu,
			ElNode department) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int value = 0;
		String sqlAppend = "";
		try {
			ct = DBConnection.getConnection();

			if (xiangmu != null) {
				if (xiangmu.getName() != null && !xiangmu.getName().equals("")) {
					sqlAppend += " and XMDA_XMMC like '%" + xiangmu.getName()
							+ "%'";
				}
				if (xiangmu.getFuzeren() != null
						&& !xiangmu.getFuzeren().equals("")) {
					sqlAppend += " and XMDA_XMFZR "
							+ " is not null and id in (select mainid from tb_tags_relate where relateid in "
							+ " ( select id from eluser where realname "
							+ " like '%" + xiangmu.getFuzeren() + "%')) ";
				}
				if (xiangmu.getStarttime() != null)
					sqlAppend += sqlAppend
							+ " and to_char(XMDA_LXRQ,'yyyy-MM-dd HH:mm:ss') > '"
							+ xiangmu.getStarttime() + "'";
				if (xiangmu.getEndtime() != null)
					sqlAppend += sqlAppend
							+ " and to_char(XMDA_LXRQ,'yyyy-MM-dd HH:mm:ss') < '"
							+ xiangmu.getEndtime() + "'";

			}

			sql = " select count(1) from "
					+ tablename
					+ " t, eluser e   ,department d "
					+ " join ("
					+ ((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
							.generateSQLByTree("department", department, true)
					+ " ) dep on dep.id=d.id"
					+ " where t.status=9 and e.id = t.userid and e.depid=d.id "
					+ sqlAppend;
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				value = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("项目核算出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return value;
	}

	public int getGzrzListCount(ELUser elUser, Timestamp starttime,
			Timestamp endtime, String tablename, ElNode department)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int value = 0;
		String sqlWhere = "";
		try {
			ct = DBConnection.getConnection();

			if (elUser != null) {
				if (elUser.getRealname() != null
						&& !elUser.getRealname().equals("")) {
					sqlWhere += " and realname like '" + elUser.getRealname()
							+ "%'";
				}
				if (elUser.getUsername() != null
						&& !elUser.getUsername().equals("")) {
					sqlWhere += " and username like '" + elUser.getUsername()
							+ "%'";
				}

			}
			if (starttime != null) {
				sqlWhere += sqlWhere
						+ " and to_char(GRRZ_TXRQ,'yyyy-MM-dd HH:mm:ss') > '"
						+ starttime + "'";
			}
			if (endtime != null) {
				sqlWhere += sqlWhere
						+ " and to_char(GRRZ_TXRQ,'yyyy-MM-dd HH:mm:ss') > '"
						+ endtime + "'";
			}

			sql = " select count(1) from eluser e ,department d "
					+ " join ( "
					+ ((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
							.generateSQLByTree("department", department, true)
					+ " ) dep on dep.id=d.id " + " where e.depid=d.id "
					+ sqlWhere;
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				value = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("项目核算出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return value;
	}

	public List<Map<String, Object>> getMyPlan(int userid,boolean is_show,int number) throws ElException {
		PreparedStatement ps = null;
		
		List<Map<String, Object>> list_map = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		Connection ct = null;
		String sql = "";
		String sql1 = "";
		String sql2 = "";
		String xiangguan_kehu_ids = "";
		String xiangguan_kehu = "";
		String xiangguan_lianxiren = "";
		String xiangguan_lianxiren_ids = "";
		String xiangguan_xingwei = "";
		String jieguo = "";
		try {
			ct = DBConnection.getConnection();
			
			if(!is_show){
				return list_map;
			}
			sql = "select a.*,rownum rn from (select id,GZJH_JHMC,GZJH_JHZQ,GZJH_LDPF,GZJH_LXR,GZJH_XGXW,GZJH_XGKH from GZJH where userid="
					+ userid + "  order by GZJH_TXRQ desc ) a where rownum<=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, number);

			rs = ps.executeQuery();
			while (rs.next()) {
				jieguo = "";
				xiangguan_kehu_ids = "";
				xiangguan_lianxiren_ids = ""; 
				xiangguan_xingwei = "";
				xiangguan_kehu = "";
				xiangguan_lianxiren = "";
				
				
				map = new HashMap<String, Object>();
				map.put("id", rs.getInt("id"));
				map.put("GZJH_JHMC", rs.getString("GZJH_JHZQ"));
				map.put("GZJH_JHZQ", rs.getString("GZJH_JHZQ"));
				map.put("GZJH_LDPF", rs.getDouble("GZJH_LDPF"));
				if (rs.getString("GZJH_XGXW") != null
						&& !rs.getString("GZJH_XGXW").equals("")) {
					
					
					sql1 = " select LXXW_LXZT,LXXW_XGKH,LXXW_XGLXR,LXXW_JG from LXXW where id in ("
							+ rs.getString("GZJH_XGXW") + " ) ";
					ps = ct.prepareStatement(sql1);
					rs1 = ps.executeQuery();
					while (rs1.next()) {
						
						if(rs1.getString(4) != null && !rs1.getString(4).equals("")){
							jieguo += rs1.getString(4) + ",";
						}
						if(rs1.getString(2) != null && !rs1.getString(2).equals("")){
							xiangguan_kehu_ids += rs1.getString(2) + ",";
						}
						if(rs1.getString(3) != null && !rs1.getString(3).equals("")){
							xiangguan_lianxiren_ids += rs1.getString(3) + ",";					
						}
						if(rs1.getString("LXXW_LXZT") != null && !rs1.getString("LXXW_LXZT").equals("")){
							xiangguan_xingwei += rs1.getString("LXXW_LXZT") + ",";
						}
					}
					
					if (!xiangguan_xingwei.equals("")&&xiangguan_xingwei.indexOf(",")>=0){
						xiangguan_xingwei = xiangguan_xingwei.substring(0,
								xiangguan_xingwei.lastIndexOf(","));
						map.put("GZJH_XGXW", xiangguan_xingwei.split(","));//联系行为
						
						if(!xiangguan_kehu_ids.equals("")&&xiangguan_kehu_ids.indexOf(",")>=0){
							xiangguan_kehu_ids = xiangguan_kehu_ids.substring(0,xiangguan_kehu_ids.lastIndexOf(","));
						}
						if(!xiangguan_lianxiren_ids.equals("")&&xiangguan_lianxiren_ids.indexOf(",")>=0){
							xiangguan_lianxiren_ids = xiangguan_lianxiren_ids.substring(0,xiangguan_lianxiren_ids.lastIndexOf(","));
						}
						if(!jieguo.equals("")&&jieguo.indexOf(",")>=0){
							jieguo = jieguo.substring(0,jieguo.lastIndexOf(","));
							map.put("GZJH_JG", jieguo.split(","));//结果
						}
						
						
						if(xiangguan_kehu_ids != null && !xiangguan_kehu_ids.equals("")){
							sql2 = "select KHDA_GSMC from KHDA where id in (" + xiangguan_kehu_ids + ")";
							ps = ct.prepareStatement(sql2);
							rs2 = ps.executeQuery();
							while(rs2.next()){
								xiangguan_kehu += rs2.getString(1) + ",";
							}
							if (!xiangguan_kehu.equals(""))
								xiangguan_kehu = xiangguan_kehu.substring(0,
										xiangguan_kehu.lastIndexOf(","));
							map.put("GZJH_XGKH", xiangguan_kehu.split(","));//相关客户
						}
						
						
						if(xiangguan_lianxiren_ids != null && !xiangguan_lianxiren_ids.equals("")){
							sql2 = "select LJR_XM from LJR where id in(" + xiangguan_lianxiren_ids + ")";
							ps = ct.prepareStatement(sql2);
							rs2 = ps.executeQuery();
							while(rs2.next()){
								xiangguan_lianxiren += rs2.getString(1) + ",";
							}
							if (!xiangguan_lianxiren.equals(""))
								xiangguan_lianxiren = xiangguan_lianxiren.substring(0,
										xiangguan_lianxiren.lastIndexOf(","));
							map.put("GZJH_XGLXR", xiangguan_lianxiren.split(","));//相关客户
						}
					}
				}
				list_map.add(map);
			}
		} catch (Exception e) {
			logger.error("我的计划列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return list_map;
	}

	public List<Map<String, Object>> getMyLog(int userid,boolean is_show,int number) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		Connection ct = null;
		String sql = "";
		String sql1 = "";
		String sql2 = "";
		String xiangguan_kehu_ids = "";
		String xiangguan_kehu = "";
		String xiangguan_lianxiren = "";
		String xiangguan_lianxiren_ids = "";
		String xiangguan_xingwei = "";
		String jieguo = "";
		List<Map<String, Object>> list_map = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		try {
			ct = DBConnection.getConnection();
			
			if(!is_show){
				return list_map;
			}
			
			sql = "select a.*,rownum rn from (select id,GRRZ_BT,GRRZ_TXRQ,GRRZ_ZWPF,GRRZ_LDPF,GRRZ_XGXW from GRRZ where userid="
					+ userid + " order by GRRZ_TXRQ desc ) a where rownum<=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, number);

			rs = ps.executeQuery();
			while (rs.next()) {
				jieguo = "";
				xiangguan_kehu_ids = "";
				xiangguan_lianxiren_ids = ""; 
				xiangguan_xingwei = "";
				xiangguan_kehu = "";
				xiangguan_lianxiren = "";
				
				map = new HashMap<String, Object>();
				map.put("id", rs.getInt("id"));
				map.put("GRRZ_BT", rs.getString("GRRZ_BT"));
				map.put("GRRZ_TXRQ", rs.getTimestamp("GRRZ_TXRQ"));
				map.put("GRRZ_ZWPF", rs.getDouble("GRRZ_ZWPF"));
				map.put("GRRZ_LDPF", rs.getDouble("GRRZ_LDPF"));
				map.put("chazhi", rs.getDouble("GRRZ_ZWPF")
						- rs.getDouble("GRRZ_LDPF"));
				if (rs.getString("GRRZ_XGXW") != null
						&& !rs.getString("GRRZ_XGXW").equals("")) {
					
					
					sql1 = " select LXXW_LXZT,LXXW_XGKH,LXXW_XGLXR,LXXW_JG from LXXW where id in ("
							+ rs.getString("GRRZ_XGXW") + " ) ";
					ps = ct.prepareStatement(sql1);
					rs1 = ps.executeQuery();
					while (rs1.next()) {
						
						if(rs1.getString(4) != null && !rs1.getString(4).equals("")){
							jieguo += rs1.getString(4) + ",";
						}else {
							jieguo += "-" + ",";
						}
						if(rs1.getString(2) != null && !rs1.getString(2).equals("")){
							xiangguan_kehu_ids += rs1.getString(2) + ",";
						}
						if(rs1.getString(3) != null && !rs1.getString(3).equals("")){
							xiangguan_lianxiren_ids += rs1.getString(3) + ",";					
						}
						if(rs1.getString("LXXW_LXZT") != null && !rs1.getString("LXXW_LXZT").equals("")){
							xiangguan_xingwei += rs1.getString("LXXW_LXZT") + ",";
						}
					}
					
					
					if (!xiangguan_xingwei.equals("")&&xiangguan_xingwei.indexOf(",")>=0){
						xiangguan_xingwei = xiangguan_xingwei.substring(0,
								xiangguan_xingwei.lastIndexOf(","));
						map.put("GRRZ_XGXW", xiangguan_xingwei.split(","));//联系行为
						
						if(!xiangguan_kehu_ids.equals("")&&xiangguan_kehu_ids.indexOf(",")>=0){
							xiangguan_kehu_ids = xiangguan_kehu_ids.substring(0,xiangguan_kehu_ids.lastIndexOf(","));
						}
						if(!xiangguan_lianxiren_ids.equals("")&&xiangguan_lianxiren_ids.indexOf(",")>=0){
							xiangguan_lianxiren_ids = xiangguan_lianxiren_ids.substring(0,xiangguan_lianxiren_ids.lastIndexOf(","));
						}
						if(!jieguo.equals("")&&jieguo.indexOf(",")>=0){
							jieguo = jieguo.substring(0,jieguo.lastIndexOf(","));
							map.put("GRRZ_JG", jieguo.split(","));//结果
						}
						
						
						if(xiangguan_kehu_ids != null && !xiangguan_kehu_ids.equals("")){
							sql2 = "select KHDA_GSMC from KHDA where id in (" + xiangguan_kehu_ids + ")";
							ps = ct.prepareStatement(sql2);
							rs2 = ps.executeQuery();
							while(rs2.next()){
								xiangguan_kehu += rs2.getString(1) + ",";
							}
							if (!xiangguan_kehu.equals(""))
								xiangguan_kehu = xiangguan_kehu.substring(0,
										xiangguan_kehu.lastIndexOf(","));
							map.put("GRRZ_XGKH", xiangguan_kehu.split(","));//相关客户
						}
						
						
						if(xiangguan_lianxiren_ids != null && !xiangguan_lianxiren_ids.equals("")){
							sql2 = "select LJR_XM from LJR where id in(" + xiangguan_lianxiren_ids + ")";
							ps = ct.prepareStatement(sql2);
							rs2 = ps.executeQuery();
							while(rs2.next()){
								xiangguan_lianxiren += rs2.getString(1) + ",";
							}
							if (!xiangguan_lianxiren.equals(""))
								xiangguan_lianxiren = xiangguan_lianxiren.substring(0,
										xiangguan_lianxiren.lastIndexOf(","));
							map.put("GRRZ_XGLXR", xiangguan_lianxiren.split(","));//相关客户
						}
					}
				}
				list_map.add(map);
			}
		} catch (Exception e) {
			logger.error("我的计划列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return list_map;
	}

	public List<Map<String, Object>> getMyRC(int userid,boolean is_show,int number) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		List<Map<String, Object>> list_map = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		try {
			ct = DBConnection.getConnection();
			
			if(!is_show){
				return list_map;
			}
			
			sql = "select a.*,rownum rn from (select id,RCGL_RCMC,RCGL_RCSJ,RCGL_RCXZ,RCGL_ZYX,RCGL_TXSJ from RCGL where userid="
					+ userid
					+ " and RCGL_TXSJ>sysdate order by RCGL_RCSJ asc ) a where rownum<=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, number);

			rs = ps.executeQuery();
			while (rs.next()) {
				map = new HashMap<String, Object>();
				map.put("id", rs.getInt("id"));
				map.put("RCGL_RCMC", rs.getString("RCGL_RCMC"));
				map.put("RCGL_RCSJ", rs.getTimestamp("RCGL_RCSJ"));
				map.put("RCGL_RCXZ", rs.getString("RCGL_RCXZ"));
				map.put("RCGL_ZYX", rs.getString("RCGL_ZYX"));
				map.put("RCGL_TXSJ", rs.getTimestamp("RCGL_TXSJ"));
				list_map.add(map);
			}
		} catch (Exception e) {
			logger.error("我的计划列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return list_map;
	}

	public List<Map<String, Object>> getMyDaibanshuwu(int userid,boolean is_show,int number)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		Connection ct = null;
		String sql = "";
		String sql1 = "";
		String sql2 = "";
		String xiangguan_kehu_ids = "";
		String xiangguan_kehu = "";
		String xiangguan_lianxiren = "";
		String xiangguan_lianxiren_ids = "";
		String xiangguan_xingwei = "";
		String jieguo = "";
		List<Map<String, Object>> list_map = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		try {
			ct = DBConnection.getConnection();
			
			if(!is_show){
				return list_map;
			}
			
			sql = "select a.*,rownum rn from (select id,DBSW_SWMC,DBSW_KSRQ,DBSW_WCRQ,DBSW_ZYX,DBSW_XGLXR,DBSW_XGXW,DBSW_XGKH from DBSW where userid="
					+ userid
					+ " and DBSW_KSRQ>sysdate  order by DBSW_KSRQ asc ) a where rownum<=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, number);

			rs = ps.executeQuery();
			while (rs.next()) {
				jieguo = "";
				xiangguan_kehu_ids = "";
				xiangguan_lianxiren_ids = ""; 
				xiangguan_xingwei = "";
				xiangguan_kehu = "";
				xiangguan_lianxiren = "";
				
				map = new HashMap<String, Object>();
				map.put("id", rs.getInt("id"));
				map.put("DBSW_SWMC", rs.getString("DBSW_SWMC"));
				map.put("DBSW_KSRQ", rs.getTimestamp("DBSW_KSRQ"));
				map.put("DBSW_WCRQ", rs.getTimestamp("DBSW_WCRQ"));
				map.put("DBSW_ZYX", rs.getString("DBSW_ZYX"));
				
				if (rs.getString("DBSW_XGXW") != null
						&& !rs.getString("DBSW_XGXW").equals("")) {
					
					
					sql1 = " select LXXW_LXZT,LXXW_XGKH,LXXW_XGLXR,LXXW_JG from LXXW where id in ("
							+ rs.getString("DBSW_XGXW") + " ) ";
					ps = ct.prepareStatement(sql1);
					rs1 = ps.executeQuery();
					while (rs1.next()) {
						
						if(rs1.getString(4) != null && !rs1.getString(4).equals("")){
							jieguo += rs1.getString(4) + ",";
						}
						if(rs1.getString(2) != null && !rs1.getString(2).equals("")){
							xiangguan_kehu_ids += rs1.getString(2) + ",";
						}
						if(rs1.getString(3) != null && !rs1.getString(3).equals("")){
							xiangguan_lianxiren_ids += rs1.getString(3) + ",";					
						}
						if(rs1.getString("LXXW_LXZT") != null && !rs1.getString("LXXW_LXZT").equals("")){
							xiangguan_xingwei += rs1.getString("LXXW_LXZT") + ",";
						}
					}
					
					
					if (!xiangguan_xingwei.equals("")&&xiangguan_xingwei.indexOf(",")>=0){
						xiangguan_xingwei = xiangguan_xingwei.substring(0,
								xiangguan_xingwei.lastIndexOf(","));
						map.put("DBSW_XGXW", xiangguan_xingwei.split(","));//联系行为
						
						if(!xiangguan_kehu_ids.equals("")&&xiangguan_kehu_ids.indexOf(",")>=0){
							xiangguan_kehu_ids = xiangguan_kehu_ids.substring(0,xiangguan_kehu_ids.lastIndexOf(","));
						}
						if(!xiangguan_lianxiren_ids.equals("")&&xiangguan_lianxiren_ids.indexOf(",")>=0){
							xiangguan_lianxiren_ids = xiangguan_lianxiren_ids.substring(0,xiangguan_lianxiren_ids.lastIndexOf(","));
						}
						if(!jieguo.equals("")&&jieguo.indexOf(",")>=0){
							jieguo = jieguo.substring(0,jieguo.lastIndexOf(","));
							map.put("DBSW_JG", jieguo.split(","));//结果
						}
						
						
						if(xiangguan_kehu_ids != null && !xiangguan_kehu_ids.equals("")){
							sql2 = "select KHDA_GSMC from KHDA where id in (" + xiangguan_kehu_ids + ")";
							ps = ct.prepareStatement(sql2);
							rs2 = ps.executeQuery();
							while(rs2.next()){
								xiangguan_kehu += rs2.getString(1) + ",";
							}
							if (!xiangguan_kehu.equals(""))
								xiangguan_kehu = xiangguan_kehu.substring(0,
										xiangguan_kehu.lastIndexOf(","));
							map.put("DBSW_XGKH", xiangguan_kehu.split(","));//相关客户
						}
						
						
						if(xiangguan_lianxiren_ids != null && !xiangguan_lianxiren_ids.equals("")){
							sql2 = "select LJR_XM from LJR where id in(" + xiangguan_lianxiren_ids + ")";
							ps = ct.prepareStatement(sql2);
							rs2 = ps.executeQuery();
							while(rs2.next()){
								xiangguan_lianxiren += rs2.getString(1) + ",";
							}
							if (!xiangguan_lianxiren.equals(""))
								xiangguan_lianxiren = xiangguan_lianxiren.substring(0,
										xiangguan_lianxiren.lastIndexOf(","));
							map.put("DBSW_XGLXR", xiangguan_lianxiren.split(","));//相关客户
						}
					}
				}
				// map.put("DBSW_XGLXR", rs.getString("DBSW_XGLXR"));
//				if (rs.getString("DBSW_XGLXR") != null
//						&& !rs.getString("DBSW_XGLXR").equals("")) {
//					sql1 = " select LJR_XM from LJR where id in ("
//							+ rs.getString("DBSW_XGLXR") + " ) ";
//					ps = ct.prepareStatement(sql1);
//					rs1 = ps.executeQuery();
//					while (rs1.next()) {
//						xiangguan_lianxiren += rs1.getString(1) + ",";
//					}
//					if (!xiangguan_lianxiren.equals(""))
//						xiangguan_lianxiren = xiangguan_lianxiren.substring(0,
//								xiangguan_lianxiren.lastIndexOf(","));
//					map.put("DBSW_XGLXR", xiangguan_lianxiren.split(","));
//				}
//				if (rs.getString("DBSW_XGXW") != null
//						&& !rs.getString("DBSW_XGXW").equals("")) {
//					sql1 = " select LXXW_LXZT from LXXW where id in ("
//							+ rs.getString("DBSW_XGXW") + " ) ";
//					ps = ct.prepareStatement(sql1);
//					rs1 = ps.executeQuery();
//					while (rs1.next()) {
//						xiangguan_xingwei += rs1.getString(1) + ",";
//					}
//					if (!xiangguan_xingwei.equals(""))
//						xiangguan_xingwei = xiangguan_xingwei.substring(0,
//								xiangguan_xingwei.lastIndexOf(","));
//					map.put("DBSW_XGXW", xiangguan_xingwei.split(","));
//				}
//				if (rs.getString("DBSW_XGKH") != null
//						&& !rs.getString("DBSW_XGKH").equals("")) {
//					sql1 = " select KHDA_GSMC from KHDA where id in ("
//							+ rs.getString("DBSW_XGKH") + " ) ";
//					ps = ct.prepareStatement(sql1);
//					rs1 = ps.executeQuery();
//					while (rs1.next()) {
//						xianggguan_kehu += rs1.getString(1) + ",";
//					}
//					if (!xianggguan_kehu.equals(""))
//						xianggguan_kehu = xianggguan_kehu.substring(0,
//								xianggguan_kehu.lastIndexOf(","));
//					map.put("DBSW_XGKH", xianggguan_kehu.split(","));
//				}
				
				list_map.add(map);
			}
		} catch (Exception e) {
			logger.error("我的代办事务列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return list_map;
	}

	/**
	 * 个人未审核
	 * 
	 * @return
	 * @throws ElException
	 */
	public List<Map<String, Object>> getNoPass(int roleid, int userid,
			int pageNow, int pageSize,boolean is_show,ElNode department) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlWhere = "";
		String params = "";
		String moduleName = "";
		String tablename = "";
		String tablenames = "";
		String[] array = null;
		int count = 0;
		int i = -1;
		List<Map<String, Object>> list_map = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		try {
			ct = DBConnection.getConnection();
			
			if(!is_show){
				return list_map;
			}

			sql = "select ef.funccode,substr(ef.params,11,length(ef.params)) as params from elrolefunc erf  "
					+ " join elfunc ef on erf.funcid = ef.id "
					+ " where roleid = "
					+ roleid
					+ " and funccode='myContactTags' and params like '%=%' and params not like '%=%=%' order by id desc";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				map = new HashMap<String, Object>();
				params = rs.getString(2);
				if (params != null && !params.equals("")) {
					tablename = params;
				}
				if (!tablename.equals("")) {
					tablename = tablename.toUpperCase();
					moduleName = new TagsDaoImpl()
							.getModuleShujuNameByTablename(tablename
									.toUpperCase()) == null ? new TagsDaoImpl()
							.getModuleNameByTablename(tablename.toUpperCase())
							: new TagsDaoImpl()
									.getModuleShujuNameByTablename(tablename
											.toUpperCase());
				}

				if (new ModuleManageDaoImpl().checkTableIsExist(tablename) != 0) {
					count = this.getSize(tablename, userid, 0,null);
				}
				if (count == 0) {
					i++;
					continue;
				} else {
					tablenames += tablename + ",";
					i++;
				}
			}
			if (!tablenames.equals("")) {
				tablenames = tablenames.substring(0, tablenames
						.lastIndexOf(","));
			}

			array = tablenames.split(",");
			for (int j = 0; j < array.length; j++) {
				if (j == array.length - 1)
					sqlWhere += "'" + array[j] + "'";
				else
					sqlWhere += "'" + array[j] + "',";
			}

			if(pageNow==-1 && pageSize==-1){
				sql = " select funccode,params from (select ef.funccode,substr(ef.params,11,length(ef.params)) as params from elrolefunc erf  "
					+ " join elfunc ef on erf.funcid = ef.id "
					+ " where roleid = "
					+ roleid
					+ " and funccode='myContactTags' and params like '%=%' and params not like '%=%=%' order by id desc) c where params in ("+sqlWhere+")";
			}else{
				sql = " select b.*,rn from (select a.*,rownum rn from (select funccode,params from (select ef.funccode,substr(ef.params,11,length(ef.params)) as params from elrolefunc erf  "
					+ " join elfunc ef on erf.funcid = ef.id "
					+ " where roleid = "
					+ roleid
					+ " and funccode='myContactTags' and params like '%=%' and params not like '%=%=%' order by id desc) c where params in ("+sqlWhere+")) a where rownum<=?) b where rn>=?";
			}
			
			ps = ct.prepareStatement(sql);
			if(pageNow!=-1 && pageSize!=-1){
				ps.setInt(1, pageNow);
				ps.setInt(2, pageSize);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				map = new HashMap<String, Object>();
				params = rs.getString(2);
				if (params != null && !params.equals("")) {
						tablename = params;
				}
				if (!tablename.equals("")) {
					tablename = tablename.toUpperCase();
					moduleName = new TagsDaoImpl()
							.getModuleShujuNameByTablename(tablename
									.toUpperCase()) == null ? new TagsDaoImpl()
							.getModuleNameByTablename(tablename.toUpperCase())
							: new TagsDaoImpl()
									.getModuleShujuNameByTablename(tablename
											.toUpperCase());
				}

				if (new ModuleManageDaoImpl().checkTableIsExist(tablename) != 0) {
					count = this.getSize(tablename, userid, 0,null);
				}

				map.put("moduleName", moduleName);
				map.put("tablename", tablename);
				map.put("count", count);

				list_map.add(map);
			}
		} catch (Exception e) {
			logger.error("我的未审核出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return list_map;
	}

	public List<Map<String, Object>> getdaiPass(int roleid, int userid,
			int pageNow, int pageSize,boolean is_show,ElNode department) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlWhere = "";
		String params = "";
		String moduleName = "";
		String tablename = "";
		String tablenames = "";
		String[] array = null;
		int count = 0;
		int i = -1;
		List<Map<String, Object>> list_map = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		try {
			ct = DBConnection.getConnection();
			
			if(!is_show){
				return list_map;
			}
			
			sql = "select ef.funccode,substr(ef.params,11,length(ef.params)) as params from elrolefunc erf  "
					+ " join elfunc ef on erf.funcid = ef.id "
					+ " where roleid = "
					+ roleid
					+ " and funccode='finalsearchContactTags'  order by id desc";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				map = new HashMap<String, Object>();
				params = rs.getString(2);
				if (params != null && !params.equals("")) {
					tablename = params.split("&")[0];
				}
				if (!tablename.equals("")) {
					tablename = tablename.toUpperCase();
					moduleName = new TagsDaoImpl()
							.getModuleShujuNameByTablename(tablename
									.toUpperCase()) == null ? new TagsDaoImpl()
							.getModuleNameByTablename(tablename.toUpperCase())
							: new TagsDaoImpl()
									.getModuleShujuNameByTablename(tablename
											.toUpperCase());
				}

				if (new ModuleManageDaoImpl().checkTableIsExist(tablename) != 0) {
					count = this.getSize(tablename, 0, 0,department);
					
				}
				if (count == 0) {
					i++;
					continue;
				} else {
					tablenames += tablename + ",";
					i++;
				}
			}
			if (!tablenames.equals("")) {
				tablenames = tablenames.substring(0, tablenames
						.lastIndexOf(","));
			}

			array = tablenames.split(",");
			array = tablenames.split(",");
			for (int j = 0; j < array.length; j++) {
				if (j == array.length - 1)
					sqlWhere += "'" + array[j] + "&'";
				else
					sqlWhere += "'" + array[j] + "&',";
			}

			sql = " select b.*,rn from (select a.*,rownum rn from (select funccode,params from (select ef.funccode,RTRIM(substr(ef.params, 11),'final_=1') as params from elrolefunc erf  "
					+ " join elfunc ef on erf.funcid = ef.id "
					+ " where roleid = "
					+ roleid
					+ " and funccode='finalsearchContactTags'  order by id desc) c where  params in ("
					+ sqlWhere + ")  ) a where rownum<=?) b where rn>=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				map = new HashMap<String, Object>();
				params = rs.getString(2);
				if (params != null && !params.equals("")) {
					tablename = params.substring(0, params.lastIndexOf("&"));
				}
				if (!tablename.equals("")) {
					tablename = tablename.toUpperCase();
					moduleName = new TagsDaoImpl()
							.getModuleShujuNameByTablename(tablename
									.toUpperCase()) == null ? new TagsDaoImpl()
							.getModuleNameByTablename(tablename.toUpperCase())
							: new TagsDaoImpl()
									.getModuleShujuNameByTablename(tablename
											.toUpperCase());
				}

				if (new ModuleManageDaoImpl().checkTableIsExist(tablename) != 0) {
					count = this.getSize(tablename, 0, 0,department);
				}

				map.put("moduleName", moduleName);
				map.put("tablename", tablename);
				map.put("count", count);

				list_map.add(map);
			}
		} catch (Exception e) {
			logger.error("我的未审核出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return list_map;
	}

	public int getSize(String tablename, int userid, int type,ElNode department)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int value = 0;
		try {
			ct = DBConnection.getConnection();
			if (type == 0){
				if(userid != 0){
					sql = " select count(1) from " + tablename + " t" +
//							" join ("
//							+ ((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
//									.generateSQLByTree("department", department, true)
//							+ ") dep on dep.id=d.id " + 
							" where  t.userid = " 
					+ userid + " and t.status != '0'";
				}else{
					sql = " select count(1) from " + tablename + " t ,eluser e,department d " +
							" join ("
							+ ((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
									.generateSQLByTree("department", department, true)
							+ ") dep on dep.id=d.id " + 
							" where  t.userid=e.id and e.depid = d.id and t.status != '0'";
				}
			}
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				value = rs.getInt(1);
			}

		} catch (Exception e) {
			logger.error("为审核数目出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return value;
	}

	public int getNoPassSize(int roleid, int userid,boolean is_show,ElNode department) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int value = 0;
		String tablename = "";
		String tablenames = "";
		String params = "";
		String[] array = null;
		String sqlWhere = "";
		int count = 0;
		Map<String, Object> map = null;
		try {
			ct = DBConnection.getConnection();
			
			if(!is_show){
				return 0;
			}

			sql = "select ef.funccode,substr(ef.params,11,length(ef.params)) from elrolefunc erf  "
					+ " join elfunc ef on erf.funcid = ef.id "
					+ " where roleid = "
					+ roleid
					+ " and funccode='myContactTags'  order by id desc";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				map = new HashMap<String, Object>();
				params = rs.getString(2);
				if (params != null && !params.equals("")) {
					tablename = params;
				}
				if (!tablename.equals("")) {
					tablename = tablename.toUpperCase();
				}

				if (new ModuleManageDaoImpl().checkTableIsExist(tablename) != 0) {
					count = this.getSize(tablename, userid, 0,null);
				}
				if (count == 0) {
					continue;
				} else {
					tablenames += tablename + ",";
				}
			}
			if (!tablenames.equals("")) {
				tablenames = tablenames.substring(0, tablenames
						.lastIndexOf(","));
			}

			array = tablenames.split(",");
			for (int j = 0; j < array.length; j++) {
				if (j == array.length - 1)
					sqlWhere += "'" + array[j] + "'";
				else
					sqlWhere += "'" + array[j] + "',";
			}

			sql = " select count(1) from (select ef.funccode,substr(ef.params,11,length(ef.params)) as params from elrolefunc erf  "
					+ " join elfunc ef on erf.funcid = ef.id "
					+ " where roleid = "
					+ roleid
					+ " and funccode='myContactTags' and params like '%=%' and params not like '%=%=%') where params in ("
					+ sqlWhere + ")";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				value = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("分页出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return value;
	}

	public int getdaiPassSize(int roleid, int userid,boolean is_show,ElNode department) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int value = 0;
		String tablename = "";
		String tablenames = "";
		String params = "";
		String[] array = null;
		String sqlWhere = "";
		int count = 0;
		Map<String, Object> map = null;
		try {
			ct = DBConnection.getConnection();
			
			if(!is_show){
				return 0;
			}

			sql = "select ef.funccode,substr(ef.params,11,length(ef.params)) as params from elrolefunc erf  "
					+ " join elfunc ef on erf.funcid = ef.id "
					+ " where roleid = "
					+ roleid
					+ " and funccode='finalsearchContactTags'  order by id desc";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				map = new HashMap<String, Object>();
				params = rs.getString(2);
				if (params != null && !params.equals("")) {
					tablename = params.split("&")[0];
				}
				if (!tablename.equals("")) {
					tablename = tablename.toUpperCase();
				}

				if (new ModuleManageDaoImpl().checkTableIsExist(tablename) != 0) {
					count = this.getSize(tablename, 0, 0,department);
				}
				if (count == 0) {
					continue;
				} else {
					tablenames += tablename + ",";
				}
			}
			if (!tablenames.equals("")) {
				tablenames = tablenames.substring(0, tablenames
						.lastIndexOf(","));
			}

			array = tablenames.split(",");
			for (int j = 0; j < array.length; j++) {
				if (j == array.length - 1)
					sqlWhere += "'" + array[j] + "&'";
				else
					sqlWhere += "'" + array[j] + "&',";
			}

			sql = " select count(1) from (select ef.funccode,RTRIM(substr(ef.params, 11), 'final_=1') as params  from elrolefunc erf  "
					+ " join elfunc ef on erf.funcid = ef.id "
					+ " where roleid = "
					+ roleid
					+ " and funccode='finalsearchContactTags') where params in ("
					+ sqlWhere + ")";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				value = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("分页出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return value;
	}

	public int getKehuCountByDengji(String tablename) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int returnValue = 0;
		try {
			ct = DBConnection.getConnection();


			sql = " select count(1) from "+tablename;
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				returnValue = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("查询客户登记数量出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return returnValue;
	}
	
	public static boolean checkIdIsIn(String[] array,int id){
		boolean flag = false;
		String temp = "";
		if(array != null && array.length>0){
			for(int i=0;i<array.length;i++){
				temp = array[i];
				if(temp != null && !temp.equals("")){
					if(Integer.parseInt(temp) == id){
						flag = true;
					}
				}
			}
		}
		return flag;
	}

	public Map<String, List<Map<String, Object>>> viewRelateDanju(int id)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		Connection ct = null;
		String sql = "";
		String sql1 = "";
		Map<String, List<Map<String, Object>>> map_list_map = new HashMap<String, List<Map<String, Object>>>();
		List<Map<String,Object>> list = null;
		Map<String, Object> map = null;
		TagsDaoImpl tagsDao = new TagsDaoImpl();
		try {
			ct = DBConnection.getConnection();
			//id为项目id
			
			sql = " select id,SK_XMMC,SK_SSMC from SK ";//查询收款单编号、收款单的项目id、收款单名称
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			String SK_XMMC = "";
			String[] SK_XMMC_array = null;
			list = new ArrayList<Map<String,Object>>();
			while(rs.next()){
				SK_XMMC = rs.getString(2);
				if(SK_XMMC != null && !SK_XMMC.equals("")){
					SK_XMMC_array = SK_XMMC.indexOf(",")>=0?
							SK_XMMC.split(","):new String[]{String.valueOf(SK_XMMC)};
					if(checkIdIsIn(SK_XMMC_array,id)){
						map = new HashMap<String,Object>();
						map.put("id", rs.getInt("id"));
						map.put("name", rs.getString("SK_SSMC"));
						list.add(map);
					}
				}
			}
			if(map != null){
				map_list_map.put(tagsDao.getModuleNameByTablename("SK"), list);
				map = null;
				list = null;
			}
			
			sql = " select id,FK_XMMC,FK_FKSMC from FK ";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			String FK_XMMC = "";
			String[] FK_XMMC_array = null;
			list = new ArrayList<Map<String,Object>>();
			while(rs.next()){
				FK_XMMC = rs.getString(2);
				if(FK_XMMC != null && !FK_XMMC.equals("")){
					FK_XMMC_array = FK_XMMC.indexOf(",")>=0?
							FK_XMMC.split(","):new String[]{String.valueOf(FK_XMMC)};
					if(checkIdIsIn(FK_XMMC_array,id)){
						map = new HashMap<String,Object>();
						map.put("id", rs.getInt("id"));
						map.put("name", rs.getString("FK_FKSMC"));
						list.add(map);
					}
				}
			}
			if(map != null){
				map_list_map.put(tagsDao.getModuleNameByTablename("FK"), list);
				map = null;
				list = null;
			}
			
			
			sql = " select id,YS_XMMC,YS_YSSMC from YS ";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			String YS_XMMC = "";
			String[] YS_XMMC_array = null;
			list = new ArrayList<Map<String,Object>>();
			while(rs.next()){
				YS_XMMC = rs.getString(2);
				if(YS_XMMC != null && !YS_XMMC.equals("")){
					YS_XMMC_array = YS_XMMC.indexOf(",")>=0?
							YS_XMMC.split(","):new String[]{String.valueOf(YS_XMMC)};
					if(checkIdIsIn(YS_XMMC_array,id)){
						map = new HashMap<String,Object>();
						map.put("id", rs.getInt("id"));
						map.put("name", rs.getString("YS_YSSMC"));
						list.add(map);
					}
				}
			}
			if(map != null){
				map_list_map.put(tagsDao.getModuleNameByTablename("YS"), list);
				list = null;
				map = null;
			}
			
			
			sql = " select id,YF_XMMC,YF_SJMC from YF ";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			String YF_XMMC = "";
			String[] YF_XMMC_array = null;
			list = new ArrayList<Map<String,Object>>();
			while(rs.next()){
				YF_XMMC = rs.getString(2);
				if(YF_XMMC != null && !YF_XMMC.equals("")){
					YF_XMMC_array = YF_XMMC.indexOf(",")>=0?
							YF_XMMC.split(","):new String[]{String.valueOf(YF_XMMC)};
					if(checkIdIsIn(YF_XMMC_array,id)){
						map = new HashMap<String,Object>();
						map.put("id", rs.getInt("id"));
						map.put("name", rs.getString("YF_SJMC"));
						list.add(map);
					}
				}
			}
			if(map != null){
				map_list_map.put(tagsDao.getModuleNameByTablename("YF"), list);
				list = null;
				map = null;
			}
			
			
			sql = " select id,QTSR_XMMC,QTSR_SRDMC from QTSR ";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			String QTSR_XMMC = "";
			String[] QTSR_XMMC_array = null;
			list = new ArrayList<Map<String,Object>>();
			while(rs.next()){
				QTSR_XMMC = rs.getString(2);
				if(QTSR_XMMC != null && !QTSR_XMMC.equals("")){
					QTSR_XMMC_array = QTSR_XMMC.indexOf(",")>=0?
							QTSR_XMMC.split(","):new String[]{String.valueOf(QTSR_XMMC)};
					if(checkIdIsIn(QTSR_XMMC_array,id)){
						map = new HashMap<String,Object>();
						map.put("id", rs.getInt("id"));
						map.put("name", rs.getString("QTSR_SRDMC"));
						list.add(map);
					}
				}
			}
			if(map != null){
				map_list_map.put(tagsDao.getModuleNameByTablename("QTSR"), list);
				list = null;
				map = null;
			}
			
			
			sql = " select id,FYZC_XGKH,FYZC_ZCDMC from FYZC ";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			String FYZC_XGKH = "";
			String[] FYZC_XGKH_array = null;
			list = new ArrayList<Map<String,Object>>();
			while(rs.next()){
				FYZC_XGKH = rs.getString(2);
				if(FYZC_XGKH != null && !FYZC_XGKH.equals("")){
					FYZC_XGKH_array = FYZC_XGKH.indexOf(",")>=0?
							FYZC_XGKH.split(","):new String[]{String.valueOf(FYZC_XGKH)};
					if(checkIdIsIn(FYZC_XGKH_array,id)){
						map = new HashMap<String,Object>();
						map.put("id", rs.getInt("id"));
						map.put("name", rs.getString("FYZC_ZCDMC"));
						list.add(map);
					}
				}
			}
			if(map != null){
				map_list_map.put(tagsDao.getModuleNameByTablename("FYZC"), list);
				list = null;
				map = null;
			}
			
			
		} catch (Exception e) {
			logger.error("获取相关单据出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return map_list_map;
	}

	public List<Map<String, Object>> getKehuAnalysis(String tablename,
			int pageNow, int pageSize, Map map,ElNode department) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		Connection ct = null;
		String sql = "";
		String sql1 = "";
		String sqlWhere = "";
		List<Map<String, Object>> list_map = new ArrayList<Map<String, Object>>();
		Map<String,Object> map_ = null;
		int count = 0;
		double money = 0.0;
		String value="";
		
		String sql2 = "";
		ResultSet rs2 = null;
		PreparedStatement ps2 = null;
		Connection ct2 = null;
		double value_ = 0.0;
		String sqlWhere_ = "";
		String ids = "";
		String where_ids = "";
		int id = 0;
		try {
			if(map != null){
				ct2 = DBConnection.getConnection();
				if(map.get("kehuname") != null && !((String)map.get("kehuname")).equals("")){
					sqlWhere += " and KHDA_GSMC like '%" + (String)map.get("kehuname") + "%'";
				}
				if(map.get("diqu") != null && !((String)map.get("diqu")).equals("")){
					sqlWhere += " and KHDA_CS like '%" + (String)map.get("diqu") + "%'";
				}
				if(map.get("jieduan") != null && !((String)map.get("jieduan")).equals("请选择客户阶段")){
					sqlWhere += " and KHDA_KHJD = '" + (String)map.get("jieduan") + "'";
				}
				if(map.get("xingzhi") != null && !((String)map.get("xingzhi")).equals("请选择客户性质")){
					sqlWhere += " and KHDA_GSXZ = '" + (String)map.get("xingzhi") + "'";
				}
				if((map.get("lirun_begin") != null && (Double)map.get("lirun_begin") != 0) || 
						(map.get("lirun_begin") != null && (Double)map.get("lirun_begin") != 0)){
					sql2 = "select * from "+ tablename;
					ps2 = ct2.prepareStatement(sql2);
					rs2 = ps2.executeQuery();
					while(rs2.next()){
						value_ = getMapByTableName("SK_KHMC","SK_SKJE","SK",rs2.getInt("id"))
						+ getMapByTableName("YS_KHMC","YS_YSJE","YS",rs2.getInt("id"))
						+getMapByTableName("QTSR_KHMC","QTSR_SRJE","QTSR",rs2.getInt("id"))
						-getMapByTableName("FK_XGKH","FK_FKJE","FK",rs2.getInt("id"))
						-getMapByTableName("YF_KHMC","YF_YFJE","YF",rs2.getInt("id"))
						-getMapByTableName("FYZC_XGKH","FYZC_ZJE","FYZC",rs2.getInt("id"));//利润
						if(map.get("lirun_begin") != null && (Double)map.get("lirun_begin") != 0){//利润开始
							if(map.get("lirun_end") != null && (Double)map.get("lirun_end") != 0){//利润结束
								if(value_>=(Double)map.get("lirun_begin") && value_<=(Double)map.get("lirun_end")){
									id = rs2.getInt("id");
								}
							}else {
								if(value_>=(Double)map.get("lirun_begin")){
									id = rs2.getInt("id");
								}
							}
						}
						if(map.get("lirun_end") != null && (Double)map.get("lirun_end") != 0){//利润结束
							if(map.get("lirun_begin") != null && (Double)map.get("lirun_begin") != 0){//利润开始
								if(value_>=(Double)map.get("lirun_begin") && value_<=(Double)map.get("lirun_end")){
									id = rs2.getInt("id");
								}
							}else {
								if(value_<=(Double)map.get("lirun_end")){
									id = rs2.getInt("id");
								}
							}
						}
						if(id != 0){
							ids += id + ",";
						}
					}
					
				}
				
				if((map.get("shoukuan_begin") != null && (Double)map.get("shoukuan_begin") != 0) || 
						(map.get("shoukuan_end") != null && (Double)map.get("shoukuan_end") != 0 )){
					sql2 = "select * from "+ tablename;
					ps2 = ct2.prepareStatement(sql2);
					rs2 = ps2.executeQuery();
					while(rs2.next()){
						value_ = getMapByTableName("SK_KHMC","SK_SKJE","SK",rs2.getInt("id"))
						+ getMapByTableName("YS_KHMC","YS_YSJE","YS",rs2.getInt("id"))
						+getMapByTableName("QTSR_KHMC","QTSR_SRJE","QTSR",rs2.getInt("id"));//收款
						
						if(map.get("shoukuan_begin") != null && (Double)map.get("shoukuan_begin") != 0){//收款开始
							if(map.get("shoukuan_end") != null && (Double)map.get("shoukuan_end") != 0){//收款结束
								if(value_>=(Double)map.get("shoukuan_begin") && value_<=(Double)map.get("shoukuan_end")){
									id = rs2.getInt("id");
								}
							}else {
								if(value_>=(Double)map.get("shoukuan_begin")){
									id = rs2.getInt("id");
								}
							}
						}
						if(map.get("shoukuan_end") != null && (Double)map.get("shoukuan_end") != 0){//收款结束
							if(map.get("shoukuan_begin") != null && (Double)map.get("shoukuan_begin") != 0){//收款开始
								if(value_>=(Double)map.get("shoukuan_begin") && value_<=(Double)map.get("shoukuan_end")){
									id = rs2.getInt("id");
								}
							}else {
								if(value_<=(Double)map.get("shoukuan_end")){
									id = rs2.getInt("id");
								}
							}
						}
						if(id != 0){
							ids += id + ",";
						}
					}
				}
				
				if(!ids.equals("")&& ids.indexOf(",")>=0){
					ids = ids.substring(0,ids.lastIndexOf(","));
					where_ids += " and t.id in (" + ids + ")"; 
				}else {
					if(map.get("lirun_begin") != null || map.get("lirun_end") != null 
							|| map.get("shoukuan_begin") != null || map.get("shoukuan_end") != null)
					where_ids += " and 1 != 1";
				}
			}
			
			ct = DBConnection.getConnection();

			sql = " select b.*,rn from (select a.*,rownum rn from (select t.id,t.KHDA_GSMC,t.KHDA_CS,t.KHDA_KHJD,t.KHDA_GSXZ,t.KHDA_FTCB from " + tablename +" t,eluser e,department d " +
					" join (" + 
					((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
							.generateSQLByTree("department", department, true)  + 
					" ) dep on dep.id=d.id" +
					" where t.userid=e.id and e.depid=d.id  "+sqlWhere+" "+where_ids+" order by t.id desc ) a where rownum<=? ) b where rn>=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				count = 0;
				money = 0.0;
				map_ = new HashMap<String, Object>();
				map_.put("id", rs.getInt(1));//id
				map_.put("KHDA_GSMC", rs.getString(2));//客户名称
				map_.put("KHDA_CS", rs.getString(3));//地区
				map_.put("KHDA_KHJD", rs.getString(4));//阶段
				map_.put("KHDA_GSXZ", rs.getString(5));//性质
				map_.put("KHDA_FTCB", rs.getDouble(6));//分摊成本
				
				//相关行为
				sql1 = "select LXXW_XGKH from LXXW";
				ps = ct.prepareStatement(sql1);
				rs1 = ps.executeQuery();
				while(rs1.next()){
					if(rs1.getString(1) != null && !rs1.getString(1).equals("")){
						value = rs1.getString(1);//相关客户ids
						if(checkIdIsIn(value.split(","),rs.getInt("id"))){
							count += 1;
						}
					}
				}
				map_.put("xiangguanxingwei", count);
				
				//相关日志
				count = 0;
				sql1 = "select GRRZ_XGKH from GRRZ";
				ps = ct.prepareStatement(sql1);
				rs1 = ps.executeQuery();
				while(rs1.next()){
					if(rs1.getString(1) != null && !rs1.getString(1).equals("")){
						value = rs1.getString(1);//相关客户ids
						if(checkIdIsIn(value.split(","),rs.getInt("id"))){
							count += 1;
						}
					}
				}
				map_.put("xiangguanrizhi", count);
				
				//项目档案
				count = 0;
				sql1 = "select XMDA_KH from XMDA";
				ps = ct.prepareStatement(sql1);
				rs1 = ps.executeQuery();
				while(rs1.next()){
					if(rs1.getString(1) != null && !rs1.getString(1).equals("")){
						value = rs1.getString(1);//相关客户ids
						if(checkIdIsIn(value.split(","),rs.getInt("id"))){
							count += 1;
						}
					}
				}
				map_.put("xiangguanxiangmu", count);
				
				//收款
				money = 0.0;
				map_.put("SK", getMapByTableName("SK_KHMC","SK_SKJE","SK",rs.getInt("id")));
				
				//付款
				money = 0.0;
				map_.put("FK", getMapByTableName("FK_XGKH","FK_FKJE","FK",rs.getInt("id")));
				
				//应收
				money = 0.0;
				map_.put("YS", getMapByTableName("YS_KHMC","YS_YSJE","YS",rs.getInt("id")));
				
				//应付
				money = 0.0;
				map_.put("YF", getMapByTableName("YF_KHMC","YF_YFJE","YF",rs.getInt("id")));
				
				//其他收入
				money = 0.0;
				map_.put("QTSR", getMapByTableName("QTSR_KHMC","QTSR_SRJE","QTSR",rs.getInt("id")));
				
				
				//费用支出
				money = 0.0;
				map_.put("FYZC", getMapByTableName("FYZC_XGKH","FYZC_ZJE","FYZC",rs.getInt("id")));
				
				
				list_map.add(map_);
			}
		} catch (Exception e) {
			logger.error("我的客户分析一览出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return list_map;
	}
	
	public double getMapByTableName(String column1,String column2,String tablename,int id) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String value = "";
		double money = 0.0;
		try {
			ct = DBConnection.getConnection();
			sql = "select " + column1 + " ," + column2 + " from " + tablename ;
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				if(rs.getString(1) != null && !rs.getString(1).equals("")){
					value = rs.getString(1);//相关客户ids
					if(checkIdIsIn(value.split(","),id)){
						money += rs.getDouble(2) != 0.0 ? rs.getDouble(2):0.0;
					}
				}
			}
		} catch (Exception e) {
			logger.error("出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return money;
	}

	public int getKehuAnalysisSize(String tablename, Map map,ElNode department)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlWhere = "";
		int returnValue = 0;
		
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		Connection ct2 = null;
		String sql2 = "";
		double value_ = 0.0;
		int id = 0;
		String ids = "";
		String where_ids = " ";
		try {
			ct = DBConnection.getConnection();
			ct2 = DBConnection.getConnection();
			if(map != null){
				if(map.get("kehuname") != null && !((String)map.get("kehuname")).equals("")){
					sqlWhere += " and KHDA_GSMC like '%" + (String)map.get("kehuname") + "%'";
				}
				if(map.get("diqu") != null && !((String)map.get("diqu")).equals("")){
					sqlWhere += " and KHDA_CS like '%" + (String)map.get("diqu") + "%'";
				}
				if(map.get("jieduan") != null && !((String)map.get("jieduan")).equals("请选择客户阶段")){
					sqlWhere += " and KHDA_KHJD = '" + (String)map.get("jieduan") + "'";
				}
				if(map.get("xingzhi") != null && !((String)map.get("xingzhi")).equals("请选择客户性质")){
					sqlWhere += " and KHDA_GSXZ = '" + (String)map.get("xingzhi") + "'";
				}
				if((map.get("lirun_begin") != null && (Double)map.get("lirun_begin") != 0) || 
						(map.get("lirun_begin") != null && (Double)map.get("lirun_begin") != 0)){
					sql2 = "select * from "+ tablename;
					ps2 = ct2.prepareStatement(sql2);
					rs2 = ps2.executeQuery();
					while(rs2.next()){
						value_ = getMapByTableName("SK_KHMC","SK_SKJE","SK",rs2.getInt("id"))
						+ getMapByTableName("YS_KHMC","YS_YSJE","YS",rs2.getInt("id"))
						+getMapByTableName("QTSR_KHMC","QTSR_SRJE","QTSR",rs2.getInt("id"))
						-getMapByTableName("FK_XGKH","FK_FKJE","FK",rs2.getInt("id"))
						-getMapByTableName("YF_KHMC","YF_YFJE","YF",rs2.getInt("id"))
						-getMapByTableName("FYZC_XGKH","FYZC_ZJE","FYZC",rs2.getInt("id"));//利润
						if(map.get("lirun_begin") != null && (Double)map.get("lirun_begin") != 0){//利润开始
							if(map.get("lirun_end") != null && (Double)map.get("lirun_end") != 0){//利润结束
								if(value_>=(Double)map.get("lirun_begin") && value_<=(Double)map.get("lirun_end")){
									id = rs2.getInt("id");
								}
							}else {
								if(value_>=(Double)map.get("lirun_begin")){
									id = rs2.getInt("id");
								}
							}
						}
						if(map.get("lirun_end") != null && (Double)map.get("lirun_end") != 0){//利润结束
							if(map.get("lirun_begin") != null && (Double)map.get("lirun_begin") != 0){//利润开始
								if(value_>=(Double)map.get("lirun_begin") && value_<=(Double)map.get("lirun_end")){
									id = rs2.getInt("id");
								}
							}else {
								if(value_<=(Double)map.get("lirun_end")){
									id = rs2.getInt("id");
								}
							}
						}
						if(id != 0){
							ids += id + ",";
						}
					}
					
				}
				
				if((map.get("shoukuan_begin") != null && (Double)map.get("shoukuan_begin") != 0) || 
						(map.get("shoukuan_end") != null && (Double)map.get("shoukuan_end") != 0 )){
					sql2 = "select * from "+ tablename;
					ps2 = ct.prepareStatement(sql2);
					rs2 = ps2.executeQuery();
					while(rs2.next()){
						value_ = getMapByTableName("SK_KHMC","SK_SKJE","SK",rs2.getInt("id"))
						+ getMapByTableName("YS_KHMC","YS_YSJE","YS",rs2.getInt("id"))
						+getMapByTableName("QTSR_KHMC","QTSR_SRJE","QTSR",rs2.getInt("id"));//收款
						
						if(map.get("shoukuan_begin") != null && (Double)map.get("shoukuan_begin") != 0){//收款开始
							if(map.get("shoukuan_end") != null && (Double)map.get("shoukuan_end") != 0){//收款结束
								if(value_>=(Double)map.get("shoukuan_begin") && value_<=(Double)map.get("shoukuan_end")){
									id = rs2.getInt("id");
								}
							}else {
								if(value_>=(Double)map.get("shoukuan_begin")){
									id = rs2.getInt("id");
								}
							}
						}
						if(map.get("shoukuan_end") != null && (Double)map.get("shoukuan_end") != 0){//收款结束
							if(map.get("shoukuan_begin") != null && (Double)map.get("shoukuan_begin") != 0){//收款开始
								if(value_>=(Double)map.get("shoukuan_begin") && value_<=(Double)map.get("shoukuan_end")){
									id = rs2.getInt("id");
								}
							}else {
								if(value_<=(Double)map.get("shoukuan_end")){
									id = rs2.getInt("id");
								}
							}
						}
						if(id != 0){
							ids += id + ",";
						}
					}
				}
				
				if(!ids.equals("")&& ids.indexOf(",")>=0){
					ids = ids.substring(0,ids.lastIndexOf(","));
					where_ids += " and t.id in (" + ids + ")"; 
				}else {
					if(map.get("lirun_begin") != null || map.get("lirun_end") != null 
							|| map.get("shoukuan_begin") != null || map.get("shoukuan_end") != null)
					where_ids += " and 1 != 1";
				}
			}


			sql = " select count(1) from " + tablename + " t ,eluser e,department d " +
					" join (" + 
					((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
							.generateSQLByTree("department", department, true)  + 
					" ) dep on dep.id=d.id" +
					" where t.userid=e.id and e.depid=d.id " + sqlWhere + " " + where_ids;
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				returnValue = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("查询客户分析一览Size出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return returnValue;
	}

	public Map<String,List<Production_efficiency>> getProduction_efficiency(int pageNow, int pageSize,Map<String,Object> map)
//	public Map<String, Map<String, Map<String, Object>>> getProduction_efficiency(int pageNow, int pageSize)
			throws ElException {
		Map<String,List<Production_efficiency>> map_list = new HashMap<String,List<Production_efficiency>>();
		List<Production_efficiency> list = null;
		Production_efficiency pe = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sql_where =  "";
		String sql_where1 = "";
		
		if(map != null){
			if(map.get("starttime") != null){
				sql_where = " and to_char(SCWG_DJRQ,'yyyy-MM-dd HH:mm:ss') > '" + map.get("starttime")+"'";
				sql_where1 = " and to_char(WWWG_DJRQ,'yyyy-MM-dd HH:mm:ss') > '" + map.get("starttime")+"'";
			}
			if(map.get("endtime") != null){
				sql_where = " and to_char(SCWG_DJRQ,'yyyy-MM-dd HH:mm:ss') < '" + map.get("endtime")+"'";
				sql_where1 = " and to_char(WWWG_DJRQ,'yyyy-MM-dd HH:mm:ss') < '" + map.get("endtime")+"'";
			}
		}
		
		String sql1 = "";
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		String month = "";
		double sccb = 0;
		String relate = "";
		String[] relate_array = null;
		String type = "";
		
		
		String sql2 = "";
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		
		try{
			sql = "select b.*,rn from (select a.*,rownum rn from (select distinct(month) from "+
			"(select to_char(SCWG_DJRQ,'yyyy-MM') as month,SCWG_DJRQ  from scwg where 1=1 " + sql_where + 
			"union all " +  
			"select to_char(WWWG_DJRQ,'yyyy-MM') as month,WWWG_DJRQ  from WWWG where 1=1 " + sql_where1 + 
			")  order by month ) a where rownum<=? ) b where rn>=? ";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				month = rs.getString(1);//月份
				sql1 = "select sccb,month,type,relate from " +
						"(select SCWG_WGCB as sccb,to_char(SCWG_DJRQ,'yyyy-MM') as month,1 as type,SCWG_CPRK as relate,SCWG_DJRQ from scwg where 1=1 "+ sql_where + 
						"union all "+ 
						"SELECT WWWG_SCCB as sccb ,to_char(WWWG_DJRQ,'yyyy-MM') as month,2 as type,WWWG_CPRK as relate,WWWG_DJRQ from WWWG where 1=1 " + sql_where1 + 
						")" +
						" where month = '" + month +"'";
				ps1 = ct.prepareStatement(sql1);
				rs1 = ps1.executeQuery();
				list = new ArrayList<Production_efficiency>();
				while(rs1.next()){
					double cz = 0.0;
					pe = new Production_efficiency();
					type = String.valueOf(rs1.getInt("type"));
					pe.setType(type);//标识是哪个模块，1==生产完工,2==委外完工
					relate = rs1.getString("relate");
					sccb = rs1.getDouble("sccb");//生产成本
					if(relate != null && !relate.equals("")){
						sql2 = "select WPJGB_CL,WPJGB_SCJ from WPJGB where id in (" + relate + ")";
					}
					ps2 = ct.prepareStatement(sql2);
					rs2 = ps2.executeQuery();
					while(rs2.next()){
						cz += rs2.getDouble("WPJGB_SCJ") * rs2.getInt("WPJGB_CL");
					}
					pe.setCz(cz);//产值
					pe.setRelate(relate);
					pe.setSccb(sccb);//生产成本
					pe.setXx(cz-sccb);//效益
					list.add(pe);
				}
				
				map_list.put(month, list);//获取月份
			}
		} catch (Exception e) {
			logger.error("生产效益一览表查询出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return map_list;
	}

	public int getProduction_efficiency_size(Map<String,Object> map) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int value = 0;
		
		String sql_where =  "";
		String sql_where1 = "";
		
		if(map != null){
			if(map.get("starttime") != null){
				sql_where = " and to_char(SCWG_DJRQ,'yyyy-MM-dd HH:mm:ss') > '" + map.get("starttime")+"'";
				sql_where1 = " and to_char(WWWG_DJRQ,'yyyy-MM-dd HH:mm:ss') > '" + map.get("starttime")+"'";
			}
			if(map.get("endtime") != null){
				sql_where = " and to_char(SCWG_DJRQ,'yyyy-MM-dd HH:mm:ss') < '" + map.get("endtime")+"'";
				sql_where1 = " and to_char(WWWG_DJRQ,'yyyy-MM-dd HH:mm:ss') < '" + map.get("endtime")+"'";
			}
		}
		
		try {
			ct = DBConnection.getConnection();

			sql = "select count(1) from (select distinct(month) from "+
										"(select to_char(SCWG_DJRQ,'yyyy-MM') as month,SCWG_DJRQ from scwg where 1=1 " + sql_where + 
										"union all " +  
										"select to_char(WWWG_DJRQ,'yyyy-MM') as month,WWWG_DJRQ from WWWG where 1=1 " + sql_where1 + 
										")  order by month)  ";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				value = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("生产效益一览表查询size出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return value;
	}

	
	public List<Map<String,Object>> getMaterial_requirements(int pageNow,int pageSize) throws ElException{
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sql1 = "";
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		String ids = "";
		int number = 0;
		int number1 = 0;
		int number2 = 0;
		int number3 = 0;
		int number4 = 0;
		int number5 = 0;
		double price = 0;
		String gcb_entitys = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select b.*,rn from (select a.*,rownum rn from (select distinct(entityid) as entityid from WPGCB2) a where rownum<=?) b where rn>=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while(rs.next()){
				if(rs.getInt(1) != 0){
					gcb_entitys += String.valueOf(rs.getInt(1))+ ",";
				}
			}
			if(!gcb_entitys.equals("")){
				gcb_entitys = gcb_entitys.substring(0,gcb_entitys.lastIndexOf(","));//获取过程表中存在的结果表中的数据
			}
			
			sql = " select ID,WPJGB_MC,WPJGB_CL,WPJGB_SCJ from WPJGB";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				if(checkIdIsIn(gcb_entitys.split(","),rs.getInt(1))){
					map = new HashMap<String,Object>();
					map.put("id", rs.getInt(1));//entityid
					map.put("name", rs.getString(2));
					number = rs.getInt(3);
					map.put("number", number);//number
					price = rs.getDouble(4);
					map.put("price", price);//price
					sql1 = "select SCWG_XGRWD from SCWG ";
					ps1 = ct.prepareStatement(sql1);
					rs1 = ps1.executeQuery();
					while(rs1.next()){
						if(rs1.getString(1) != null && !rs1.getString(1).equals("")){
							ids += rs1.getString(1) + ",";
						}
					}
					if(!ids.equals("")){
						ids = ids.substring(0,ids.lastIndexOf(","));
						sql1 = " select SCRWS_SCSL from SCRWS where status = 9 and id in (" + ids + ")";//已通过、未完工的生产任务单
					}else{
						sql1 = " select SCRWS_SCSL from SCRWS where status = 9 ";
					}
					
					ps1 = ct.prepareStatement(sql1);
					rs1 = ps1.executeQuery();
					while(rs1.next()){
						number1 += rs1.getInt(1);
					}
					map.put("csrw_number", number1);
					ids = "";
					
					sql1 = "select WWWG_XGWWD from WWWG ";
					ps1 = ct.prepareStatement(sql1);
					rs1 = ps1.executeQuery();
					while(rs1.next()){
						if(rs1.getString(1) != null && !rs1.getString(1).equals("")){
							ids += rs1.getString(1) + ",";
						}
					}
					if(!ids.equals("")){
						ids = ids.substring(0,ids.lastIndexOf(","));
						sql1 = " select SCLL_JGSL from SCLL where status = 9 and id in (" + ids + ")";//已通过、未完工的相关委外单
					}else{
						sql1 = " select SCLL_JGSL from SCLL where status = 9 ";
					}
					
					ps1 = ct.prepareStatement(sql1);
					rs1 = ps1.executeQuery();
					while(rs1.next()){
						number2 += rs1.getInt(1);
					}
					map.put("wwjg_number", number2);
					ids = "";
					map.put("need_number", number1 + number2);//需求数量
					
					
					sql1 = "select ID,WLBFD_XGWL from WLBFD ";
					ps1 = ct.prepareStatement(sql1);
					rs1 = ps1.executeQuery();
					while(rs1.next()){
						if(rs1.getString(1) != null && !rs1.getString(1).equals("")){
							ids += rs1.getString(1) + ",";
						}
					}
					if(!ids.equals("")){
						ids = ids.substring(0,ids.lastIndexOf(","));
						if(checkIdIsIn(ids.split(","),rs.getInt(1))){
							sql1 = "select WPGCB2_BFSL from WPGCB2 where entityid="+rs.getInt(1) + 
							" and moduleid='WLBFD' "  ;
							ps1 = ct.prepareStatement(sql1);
							rs1 = ps1.executeQuery();
							while(rs1.next()){
								number3 += rs1.getInt(1);
							}
						}
					}
					map.put("bfsl", number3);//报废数量
					ids ="";
					
					sql1 = "select ID,TLD_XGWL from TLD ";
					ps1 = ct.prepareStatement(sql1);
					rs1 = ps1.executeQuery();
					while(rs1.next()){
						if(rs1.getString(1) != null && !rs1.getString(1).equals("")){
							ids += rs1.getString(1) + ",";
						}
					}
					if(!ids.equals("")){
						ids = ids.substring(0,ids.lastIndexOf(","));
						if(checkIdIsIn(ids.split(","),rs.getInt(1))){
							sql1 = "select WPGCB2_TLSL from WPGCB2 where entityid="+rs.getInt(1) + 
							" and moduleid='TLD' "  ;
							ps1 = ct.prepareStatement(sql1);
							rs1 = ps1.executeQuery();
							while(rs1.next()){
								number4 += rs1.getInt(1);
							}
						}
					}
					map.put("ytsl", number4);//已退数量
					ids = "";
					
					sql1 = "select ID,LLD_XGWL from LLD ";
					ps1 = ct.prepareStatement(sql1);
					rs1 = ps1.executeQuery();
					while(rs1.next()){
						if(rs1.getString(1) != null && !rs1.getString(1).equals("")){
							ids += rs1.getString(1) + ",";
						}
					}
					if(!ids.equals("")){
						ids = ids.substring(0,ids.lastIndexOf(","));
						if(checkIdIsIn(ids.split(","),rs.getInt(1))){
							sql1 = "select WPGCB2_LLSL from WPGCB2 where entityid="+rs.getInt(1) + 
							" and moduleid='LLD' "  ;
							ps1 = ct.prepareStatement(sql1);
							rs1 = ps1.executeQuery();
							while(rs1.next()){
								number5 += rs1.getInt(1);
							}
						}
					}
					map.put("ylsl", number5);//已领数量
					ids = "";
					
					//差额=库存数量—需求数量+已退数量+已领数量—报废数量
					int zong_number = number-(number1 + number2)+number4+number5-number3;
					map.put("ce", zong_number);//差额
					
					map.put("dpzj", zong_number * price);//单品总价
					list.add(map);
				}
			}
		} catch (Exception e) {
			logger.error("物料需求一览表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	public int getMaterial_requirements_size() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int value = 0;
		try {
			ct = DBConnection.getConnection();

			sql = "select count(1) from WPJGB";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				value = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("物料需求一览表查询size出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return value;
	}

}
