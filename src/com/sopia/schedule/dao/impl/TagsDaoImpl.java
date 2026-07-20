package com.sopia.schedule.dao.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.ElQuerySql;
import com.sopia.common.MD5;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.Station;
import com.sopia.pfms.entities.IC_column_qiuji_qiuhe;
import com.sopia.pfms.entities.PfmsUser;
import com.sopia.pfms.entities.ProductType;
import com.sopia.schedule.CustomReportConstants;
import com.sopia.schedule.JisuanzuUtil;
import com.sopia.schedule.OperatorUtil;
import com.sopia.schedule.ScheduleUtil;
import com.sopia.schedule.TagsColumnUtil;
import com.sopia.schedule.TagsUtil;
import com.sopia.schedule.dao.TagsDao;
import com.sopia.schedule.dao.impl.xialajibie.SelectLevelDaoImpl;
import com.sopia.schedule.entities.AuditMark;
import com.sopia.schedule.entities.CurrentUser;
import com.sopia.schedule.entities.CustomAudit;
import com.sopia.schedule.entities.CustomReport;
import com.sopia.schedule.entities.CustomReportJSZ;
import com.sopia.schedule.entities.Eluser;
import com.sopia.schedule.entities.Tags;
import com.sopia.schedule.entities.TagsMark;
import com.sopia.schedule.entities.Tb_calculate;
import com.sopia.schedule.entities.UserSign;
import com.sopia.schedule.entities.xialajibie.SelectLevel;

public class TagsDaoImpl implements TagsDao {
	private static final Log logger = LogFactory.getLog(TagsDaoImpl.class);

	/*
	 * 添加自定义列表项目 type==2时，表示插入时fromresulttable==1
	 */
	public void insert_designe_field(int type, Tags tags) throws ElException {
		// int index = 0;
		int sn = 999;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sql_getsn;// = " select max(sn) from tb_designe where
		// table_name='"+tags.getTable_name()+"' ";

		try {
			ct = DBConnection.getConnection();
			// ------------------
			// sql = "select tb_designe_sequence.nextval from dual";
			// ps = ct.prepareStatement(sql);
			// rs = ps.executeQuery();
			// if (rs.next()) {
			// index = rs.getInt(1);
			// } else {
			// return;
			// }

			// 表名
			// tags.setColumn_name(tags.getTable_name() + "_" + index);

			// ------------------
			sql_getsn = " select max(sn) from tb_designe where sn<=60000 and table_name='"
					+ tags.getTable_name() + "' ";
			ps = ct.prepareStatement(sql_getsn);
			rs = ps.executeQuery();
			if (rs.next()) {
				sn = rs.getInt(1) + 1;
			} else {
				sn = 1;
			}
			if (tags.getDisplay_type().equals("富文本")) {
				sn = 65500;
			}

			// ------------------
			sql = "insert into tb_designe "
					+ "( "
					+ " column_name,column_type,table_name,name_display,add_display,"
					+ " update_display,view_display,list_display,mutilsearch_display,sn, "
					+ " display_type,default_value,departsearch_display,required,sum_display,jindutiao,"
					+ "is_calculate,is_show_complete,show_time_jindu,time_jindu,yewu_jindu,yewu_jindu_relate_id,"
					+ "yewu_jindu_relate_begintime,yewu_jindu_relate_endtime,JISUAN_RELATE_ID,"
					+ "JISUAN_PRODUCE_TABLE_NAME,JISUAN_PRODUCE_RELATE_ID,JISUAN_RESULT_TABLE_NAME,"
					+ "JISUAN_RESULT_RELATE_ID,JISUAN_RELATE_TYPE,wanzheng,from_result_table,"
					+ "biaojianqiuhe_check,biaojianqiuhe_tablename,biaojianqiuhe_column,"
					+ "writible,is_judge,mark,wanzheng_mark,fromtablename_columnname,showfinalpass,is_judge_for_user,timeformat,selectlevelid,jibieshu ,isautoplay "
					+ " ) " + " values (" + " ?,?,?,?,?, " + " ?,?,?,?,?, "
					+ " ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,  " + // 4:date
					" ?,?,?,?,?,?,?,?,?,?,?)";
			ps = ct.prepareStatement(sql);

			// 字段类型
			if (tags.getDisplay_type().equals("文本")) {
				tags.setColumn_type("varchar2(500)");
			} else if (tags.getDisplay_type().equals("大文本")) {
				tags.setColumn_type("varchar2(4000)");
				tags.setList_display(0);
				tags.setDepartsearch_display(0);
				tags.setMutilsearch_display(0);
			} else if (tags.getDisplay_type().equals("日期")) {
				tags.setColumn_type("date");
			} else if (tags.getDisplay_type().equals("整数")) {
				tags.setColumn_type("number");
			} else if (tags.getDisplay_type().equals("实数")) {
				tags.setColumn_type("float");
			} else if (tags.getDisplay_type().equals("下拉选项")) {
				tags.setColumn_type("varchar2(500)");
			} else if (tags.getDisplay_type().equals("附件上传")) {
				tags.setColumn_type("varchar2(500)");
				tags.setList_display(0);
				tags.setDepartsearch_display(0);
				tags.setMutilsearch_display(0);
			} else if (tags.getDisplay_type().equals("图片")) {
				tags.setColumn_type("varchar2(500)");
				tags.setList_display(0);
				tags.setDepartsearch_display(0);
				tags.setMutilsearch_display(0);
			} else if (tags.getDisplay_type().equals("富文本")) {
				tags.setColumn_type("blob");
				// 富文本不能在列表页显示
				tags.setList_display(0);
				tags.setDepartsearch_display(0);
				tags.setMutilsearch_display(0);
			} else if (tags.getDisplay_type().equals("相关字段")) {
				tags.setColumn_type("varchar2(4000)");
			} else if (tags.getDisplay_type().equals("相关负责人")) {
				tags.setColumn_type("varchar2(4000)");
			} else if (tags.getDisplay_type().equals("百分比")) {
				tags.setColumn_type("number");
			} else if (tags.getDisplay_type().equals("音频")) {
				tags.setColumn_type("varchar2(500)");
			}
			else
				tags.setColumn_type("varchar2(500)");

			// ---------------------
			ps.setString(1, tags.getColumn_name().toUpperCase());
			ps.setString(2, tags.getColumn_type());
			ps.setString(3, tags.getTable_name());
			ps.setString(4, tags.getName_display());
			ps.setInt(5, tags.getAdd_display());

			ps.setInt(6, tags.getUpdate_display());
			ps.setInt(7, tags.getView_display());
			ps.setInt(8, tags.getList_display());
			ps.setInt(9, tags.getMutilsearch_display());
			ps.setInt(10, sn);

			ps.setString(11, tags.getDisplay_type());
			ps.setString(12, tags.getDefault_value());
			ps.setInt(13, tags.getDepartsearch_display());
			ps.setInt(14, tags.getRequired());
			ps.setInt(15, tags.getSum_display());
			ps.setInt(16, tags.getJindutiao());
			ps.setInt(17, tags.getIs_calculate());
			ps.setInt(18, tags.getRelateIsShowComplete());
			ps.setInt(19, tags.getShow_time_jindu());
			ps.setString(20, tags.getTime_jindu_ids());
			ps.setString(21, tags.getYewu_jindu_ids());
			ps.setString(22, tags.getYewu_jindu_relate_id());
			ps.setString(23, tags.getYewu_jindu_relate_begintime());
			ps.setString(24, tags.getYewu_jindu_relate_endtime());

			ps.setString(25, tags.getJisuan_relate_id());
			ps.setString(26, tags.getJisuan_produce_table_name());
			ps.setString(27, tags.getJisuan_produce_relate_id());
			ps.setString(28, tags.getJisuan_result_table_name());
			ps.setString(29, tags.getJisuan_result_relate_id());
			ps.setString(30, tags.getJisuan_relate_type());

			ps.setString(31, tags.getWanzheng());
			if (type == 2) {
				ps.setString(32, "1");
			} else {
				ps.setString(32, "0");
			}
			ps.setInt(33, tags.getBiaojianqiuhe_check());
			ps.setString(34, tags.getBiaojianqiuhe_tablename());
			ps.setString(35, tags.getBiaojianqiuhe_column());
			ps.setInt(36, tags.getWritible());
			ps.setInt(37, tags.getIs_judge());
			ps.setString(38, tags.getMark());
			ps.setString(39, tags.getWanzheng_mark());
			ps.setString(40,
					(tags.getFromtablename_columnname() != null && !tags
							.getFromtablename_columnname().equals("")) ? tags
							.getFromtablename_columnname().toUpperCase() : "");
			ps.setInt(41, tags.getShowfinalpass());
			ps.setInt(42, tags.getIs_judge_for_user());
			ps.setString(43, tags.getTimeformat());
			ps.setInt(44, tags.getSelectlevelid());
			ps.setInt(45, tags.getJibieshu());
			ps.setInt(46, tags.getIsAutoPlay());
			ps.executeUpdate();

			// -----修改表alter table tablename add(name varchar2(20))
			sql = " alter table " + tags.getTable_name().toUpperCase()
					+ " add( " + tags.getColumn_name() + "  "
					+ tags.getColumn_type() + " )";
			ps = ct.prepareStatement(sql);
			ps.execute();

			// 求积求和==插入表ic_column_qiuji_qiuhe
			if (tags.getIs_qiuhe() != 0 || tags.getIs_qiuji() != 0
					|| tags.getZuowei_ji() != 0 || tags.getZuowei_he() != 0) {
				sql = "insert into ic_column_qiuji_qiuhe (table_name,column_name,is_qiuhe,is_qiuji,is_zuoweihe,is_zuoweiji,qiuji_column_name,qiuhe_column_name) "
						+ "values (?,?,?,?,?,?,?,?)";
				ps = ct.prepareStatement(sql);
				ps.setString(1, tags.getTable_name());
				ps.setString(2, tags.getColumn_name());
				ps.setInt(3, tags.getIs_qiuhe());
				ps.setInt(4, tags.getIs_qiuji());
				ps.setInt(5, tags.getZuowei_he());
				ps.setInt(6, tags.getZuowei_ji());
				ps.setString(7, tags.getQiuji_column_name());
				ps.setString(8, tags.getQiuhe_column_name());
				ps.executeUpdate();
			}


		} catch (Exception e) {
			logger.error("添加自定义项目出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/*
	 * 获得自定义列表
	 */
	public List<Tags> select_designe_field_by_tablename(String tablename)
			throws ElException {
		List<Tags> list = new ArrayList<Tags>();
		Tags tags = null;

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null; 
		String sql = "";
		try {
			ct = DBConnection.getConnection();

			sql = "select t.*,ttm.*,s.name from tb_designe t left join tb_tags_mark ttm on t.table_name=ttm.tablename and t.column_name=ttm.columnname" +
					" left join selectlevel s on t.selectlevelid=s.id " +
					" where t.table_name=?   order by sn ";
			ps = ct.prepareStatement(sql);
			ps.setString(1, tablename);
			rs = ps.executeQuery();
			while (rs.next()) {
				tags = new Tags();

				tags.setId(rs.getInt("id"));
				tags.setColumn_name(rs.getString("column_name"));
				tags.setColumn_type(rs.getString("column_type"));
				tags.setTable_name(rs.getString("table_name"));
				tags.setName_display(rs.getString("name_display"));

				tags.setAdd_display(rs.getInt("add_display"));
				tags.setUpdate_display(rs.getInt("update_display"));
				tags.setView_display(rs.getInt("view_display"));
				tags.setList_display(rs.getInt("list_display"));
				tags.setMutilsearch_display(rs.getInt("mutilsearch_display"));

				tags.setSn(rs.getInt("sn"));
				tags.setDisplay_type(rs.getString("display_type"));
				tags.setDefault_value(rs.getString("default_value"));
				tags.setCannot_modify(rs.getInt("cannot_modify"));
				tags.setDepartsearch_display(rs.getInt("departsearch_display"));

				tags.setRequired(rs.getInt("required"));
				tags.setSum_display(rs.getInt("sum_display"));

				tags.setJindutiao(rs.getInt("jindutiao"));
				tags.setRelateIsShowComplete(rs.getInt("is_show_complete"));
				tags.setShow_time_jindu(rs.getInt("show_time_jindu"));
				tags.setTime_jindu_ids(rs.getString("time_jindu"));

				tags.setYewu_jindu_ids(rs.getString("yewu_jindu"));
				tags.setYewu_jindu_relate_id(rs
						.getString("yewu_jindu_relate_id"));
				tags.setYewu_jindu_relate_begintime(rs
						.getString("yewu_jindu_relate_begintime"));
				tags.setYewu_jindu_relate_endtime(rs
						.getString("yewu_jindu_relate_endtime"));

				tags.setJisuan_relate_type(rs.getString("jisuan_relate_type"));
				tags.setJisuan_relate_id(rs.getString("jisuan_relate_id"));
				tags.setJisuan_produce_table_name(rs
						.getString("jisuan_produce_table_name"));
				tags.setJisuan_produce_relate_id(rs
						.getString("jisuan_produce_relate_id"));
				tags.setJisuan_result_table_name(rs
						.getString("jisuan_result_table_name"));
				tags.setJisuan_result_relate_id(rs
						.getString("jisuan_result_relate_id"));

				tags.setWanzheng(rs.getString("wanzheng"));
				tags.setIs_calculate(rs.getInt("is_calculate"));

				tags.setBiaojianqiuhe_check(rs.getInt("biaojianqiuhe_check"));
				tags.setBiaojianqiuhe_tablename(rs
						.getString("biaojianqiuhe_tablename"));
				tags.setBiaojianqiuhe_column(rs
						.getString("biaojianqiuhe_column"));

				tags.setFromResultTable(rs.getString("from_result_table"));

				tags.setWritible(rs.getInt("writible"));

				tags.setIs_judge(rs.getInt("is_judge"));
				tags.setIs_judge_for_user(rs.getInt("is_judge_for_user"));

				tags.setMark(rs.getString("mark"));
				tags.setWanzheng_mark(rs.getString("wanzheng_mark"));

				tags.setFromtablename_columnname(rs
						.getString("fromtablename_columnname"));

				tags.setShowfinalpass(rs.getInt("showfinalpass"));
				
				tags.setTimeformat(rs.getString("timeformat"));
				tags.setIsAutoPlay(rs.getInt("isautoplay"));
				
				if(tags.getDisplay_type().equals("分级下拉选项")){
					tags.setSelectlevelid(rs.getInt("selectlevelid"));
					tags.setJibieshu(rs.getInt("jibieshu"));
					tags.setSelectLevel(new SelectLevel(rs.getInt("selectlevelid"),rs.getString("name")));
					tags.setSelectLevelList(this.getSelectLevelById(tags.getSelectlevelid()));
				}
				
				
				TagsMark tagsMark = new TagsMark();
				tagsMark.setRelates(rs.getString("relates"));
				tagsMark.setRelates_info(rs.getString("relates_info"));
				tags.setTagsMark(tagsMark);
				list.add(tags);

			}
		} catch (Exception e) {
			logger.error("获取自定义列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	public String getAutoColumns(String tablename) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String returnValue = "";
		try {
			ct = DBConnection.getConnection();

			sql = "select column_name from tb_designe where table_name=? and fromtablename_columnname is not null  order by sn ";
			ps = ct.prepareStatement(sql);
			ps.setString(1, tablename);
			rs = ps.executeQuery();
			while (rs.next()) {
				returnValue += rs.getString(1) + ",";

			}
		} catch (Exception e) {
			logger.error("获取自定义列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return returnValue;
	}

	public List<Tags> select_designe_field_by_Producetablename(
			String tablename, String produce_table) throws ElException {
		List<Tags> list = new ArrayList<Tags>();
		Tags tags = null;

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String relateColumns = "";
		try {
			ct = DBConnection.getConnection();

			sql = "select wanzheng from  tb_designe where table_name = '"
					+ tablename
					+ "' and display_type = '相关字段' and is_show_complete = 1 and wanzheng is not null ";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				relateColumns = rs.getString(1);
			}
			String[] array = null;
			if(relateColumns!=null&&!relateColumns.equals("")){
				array = relateColumns.split(",");
			}

			sql = "select t.*,ttm.*,s.name from tb_designe t left join tb_tags_mark ttm on t.table_name=ttm.tablename and t.column_name=ttm.columnname " +
					" left join selectlevel s on t.selectlevelid=s.id " +
					"where t.table_name=?  order by sn ";
			ps = ct.prepareStatement(sql);
			ps.setString(1, produce_table);
			rs = ps.executeQuery();
			while (rs.next()) {
				tags = new Tags();
				tags.setId(rs.getInt("id"));
				tags.setColumn_name(rs.getString("column_name"));
				tags.setColumn_type(rs.getString("column_type"));
				tags.setTable_name(rs.getString("table_name"));
				tags.setName_display(rs.getString("name_display"));

				tags.setAdd_display(rs.getInt("add_display"));
				tags.setUpdate_display(rs.getInt("update_display"));
				tags.setView_display(rs.getInt("view_display"));
				tags.setList_display(rs.getInt("list_display"));
				tags.setMutilsearch_display(rs.getInt("mutilsearch_display"));

				tags.setSn(rs.getInt("sn"));
				tags.setDisplay_type(rs.getString("display_type"));
				tags.setDefault_value(rs.getString("default_value"));
				tags.setCannot_modify(rs.getInt("cannot_modify"));
				tags.setDepartsearch_display(rs.getInt("departsearch_display"));

				tags.setRequired(rs.getInt("required"));
				tags.setSum_display(rs.getInt("sum_display"));

				tags.setJindutiao(rs.getInt("jindutiao"));
				tags.setRelateIsShowComplete(rs.getInt("is_show_complete"));
				tags.setShow_time_jindu(rs.getInt("show_time_jindu"));
				tags.setTime_jindu_ids(rs.getString("time_jindu"));

				tags.setYewu_jindu_ids(rs.getString("yewu_jindu"));
				tags.setYewu_jindu_relate_id(rs
						.getString("yewu_jindu_relate_id"));
				tags.setYewu_jindu_relate_begintime(rs
						.getString("yewu_jindu_relate_begintime"));
				tags.setYewu_jindu_relate_endtime(rs
						.getString("yewu_jindu_relate_endtime"));

				tags.setJisuan_relate_type(rs.getString("jisuan_relate_type"));
				tags.setJisuan_relate_id(rs.getString("jisuan_relate_id"));
				tags.setJisuan_produce_table_name(rs
						.getString("jisuan_produce_table_name"));
				tags.setJisuan_produce_relate_id(rs
						.getString("jisuan_produce_relate_id"));
				tags.setJisuan_result_table_name(rs
						.getString("jisuan_result_table_name"));
				tags.setJisuan_result_relate_id(rs
						.getString("jisuan_result_relate_id"));

				tags.setWanzheng(rs.getString("wanzheng"));
				tags.setIs_calculate(rs.getInt("is_calculate"));

				tags.setBiaojianqiuhe_check(rs.getInt("biaojianqiuhe_check"));
				tags.setBiaojianqiuhe_tablename(rs
						.getString("biaojianqiuhe_tablename"));
				tags.setBiaojianqiuhe_column(rs
						.getString("biaojianqiuhe_column"));

				tags.setFromResultTable(rs.getString("from_result_table"));

				tags.setWritible(rs.getInt("writible"));

				tags.setIs_judge(rs.getInt("is_judge"));
				tags.setIs_judge_for_user(rs.getInt("is_judge_for_user"));

				tags.setMark(rs.getString("mark"));
				tags.setWanzheng_mark(rs.getString("wanzheng_mark"));

				tags.setFromtablename_columnname(rs
						.getString("fromtablename_columnname"));

				tags.setShowfinalpass(rs.getInt("showfinalpass"));
				tags.setTimeformat(rs.getString("timeformat"));
				
				tags.setSelectlevelid(rs.getInt("selectlevelid"));
				tags.setJibieshu(rs.getInt("jibieshu"));
				tags.setSelectLevel(new SelectLevel(rs.getInt("selectlevelid"),rs.getString("name")));
				
				TagsMark tagsMark = new TagsMark();
				tagsMark.setRelates(rs.getString("relates"));
				tagsMark.setRelates_info(rs.getString("relates_info"));
				tags.setTagsMark(tagsMark);

				if (tags.getFromResultTable() != null) {
					if ( TagsUtil.checkIn(array, tags.getId())) {
						list.add(tags);
					}
//					if (tags.getFromResultTable().equals("1") && TagsUtil.checkIn(array, tags.getId())) {
//						list.add(tags);
//					}
//					else {
//						list.add(tags);
//					}
				} 
			}
		} catch (Exception e) {
			logger.error("获取自定义列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	/**
	 * 字段类型为时间的列
	 * 
	 * @param tablename
	 * @param type
	 * @return
	 * @throws ElException
	 */
	public List<Tags> select_designe_field_by_tablename_time(String type,
			String tablename) throws ElException {
		List<Tags> list = new ArrayList<Tags>();
		Tags tags = null;

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			if (type != null) {
				if (type.equals("time"))
					sql = "select t.*,ttm.*,s.name as sname from tb_designe t left join tb_tags_mark ttm on t.table_name=ttm.tablename and t.column_name=ttm.columnname " +
							" left join selectlevel s on t.selectlevelid=s.id " +
							"where table_name=? and display_type='日期' order by sn ";
				else if (type.equals("yewu"))
					sql = "select t.*,ttm.*,s.name as sname from tb_designe t left join tb_tags_mark ttm on t.table_name=ttm.tablename and t.column_name=ttm.columnname " +
							" left join selectlevel s on t.selectlevelid=s.id " +
							"where table_name=? and (display_type = '日期' or display_type= '百分比') order by sn";
				else if (type.indexOf("yewu_relate") >= 0)
					sql = "select t.*,ttm.*,s.name as sname from tb_designe t left join tb_tags_mark ttm on t.table_name=ttm.tablename and t.column_name=ttm.columnname " +
							" left join selectlevel s on t.selectlevelid=s.id " +
							"where table_name=?";
				else if (type.equals("jisuan_produce")
						|| type.equals("jisuan_result")
						|| type.equals("jisuan_relate"))
					sql = "select t.*,ttm.*,s.name as sname from tb_designe t left join tb_tags_mark ttm on t.table_name=ttm.tablename and t.column_name=ttm.columnname " +
							" left join selectlevel s on t.selectlevelid=s.id " +
							"where table_name=?";
				else if (type.equals("ziduan_wanzheng"))
					sql = "select t.*,ttm.*,s.name as sname from tb_designe t left join tb_tags_mark ttm on t.table_name=ttm.tablename and t.column_name=ttm.columnname " +
							" left join selectlevel s on t.selectlevelid=s.id " +
							"where table_name=?";
				else if (type.equals("biaojianqiuhe"))
					sql = "select t.*,ttm.*,s.name as sname from tb_designe t left join tb_tags_mark ttm on t.table_name=ttm.tablename and t.column_name=ttm.columnname " +
							" left join selectlevel s on t.selectlevelid=s.id " +
							"where table_name=?";
			}
			
			ps = ct.prepareStatement(sql);
			ps.setString(1, tablename);
			rs = ps.executeQuery();
			while (rs.next()) {
				tags = new Tags();

				tags.setId(rs.getInt("id"));
				tags.setColumn_name(rs.getString("column_name"));
				tags.setColumn_type(rs.getString("column_type"));
				tags.setTable_name(rs.getString("table_name"));
				tags.setName_display(rs.getString("name_display"));

				tags.setAdd_display(rs.getInt("add_display"));
				tags.setUpdate_display(rs.getInt("update_display"));
				tags.setView_display(rs.getInt("view_display"));
				tags.setList_display(rs.getInt("list_display"));
				tags.setMutilsearch_display(rs.getInt("mutilsearch_display"));

				tags.setSn(rs.getInt("sn"));
				tags.setDisplay_type(rs.getString("display_type"));
				tags.setDefault_value(rs.getString("default_value"));
				tags.setCannot_modify(rs.getInt("cannot_modify"));
				tags.setDepartsearch_display(rs.getInt("departsearch_display"));

				tags.setRequired(rs.getInt("required"));
				tags.setSum_display(rs.getInt("sum_display"));

				tags.setJindutiao(rs.getInt("jindutiao"));
				tags.setRelateIsShowComplete(rs.getInt("is_show_complete"));
				tags.setShow_time_jindu(rs.getInt("show_time_jindu"));
				tags.setTime_jindu_ids(rs.getString("time_jindu"));

				tags.setYewu_jindu_ids(rs.getString("yewu_jindu"));
				tags.setYewu_jindu_relate_id(rs
						.getString("yewu_jindu_relate_id"));
				tags.setYewu_jindu_relate_begintime(rs
						.getString("yewu_jindu_relate_begintime"));
				tags.setYewu_jindu_relate_endtime(rs
						.getString("yewu_jindu_relate_endtime"));

				tags.setJisuan_relate_type(rs.getString("jisuan_relate_type"));
				tags.setJisuan_relate_id(rs.getString("jisuan_relate_id"));
				tags.setJisuan_produce_table_name(rs
						.getString("jisuan_produce_table_name"));
				tags.setJisuan_produce_relate_id(rs
						.getString("jisuan_produce_relate_id"));
				tags.setJisuan_result_table_name(rs
						.getString("jisuan_result_table_name"));
				tags.setJisuan_result_relate_id(rs
						.getString("jisuan_result_relate_id"));

				tags.setWanzheng(rs.getString("wanzheng"));
				tags.setIs_calculate(rs.getInt("is_calculate"));

				tags.setBiaojianqiuhe_check(rs.getInt("biaojianqiuhe_check"));
				tags.setBiaojianqiuhe_tablename(rs
						.getString("biaojianqiuhe_tablename"));
				tags.setBiaojianqiuhe_column(rs
						.getString("biaojianqiuhe_column"));

				tags.setFromResultTable(rs.getString("from_result_table"));

				tags.setWritible(rs.getInt("writible"));

				tags.setIs_judge(rs.getInt("is_judge"));
				tags.setIs_judge_for_user(rs.getInt("is_judge_for_user"));

				tags.setMark(rs.getString("mark"));
				tags.setWanzheng_mark(rs.getString("wanzheng_mark"));

				tags.setFromtablename_columnname(rs
						.getString("fromtablename_columnname"));

				tags.setShowfinalpass(rs.getInt("showfinalpass"));
				tags.setTimeformat(rs.getString("timeformat"));
				
				tags.setSelectlevelid(rs.getInt("selectlevelid"));
				tags.setJibieshu(rs.getInt("jibieshu"));
				tags.setSelectLevel(new SelectLevel(rs.getInt("selectlevelid"),rs.getString("sname")));
				
				TagsMark tagsMark = new TagsMark();
				tagsMark.setRelates(rs.getString("relates"));
				tagsMark.setRelates_info(rs.getString("relates_info"));
				tags.setTagsMark(tagsMark);

				list.add(tags);

			}
		} catch (Exception e) {
			logger.error("获取自定义列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	public void getRelate() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();

			sql = "";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
		} catch (Exception e) {
			logger.error("获取关联列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	

	/**
	 * 添加
	 */
	public int insert_tableinfo_by_tablename(int type, Map<String, String> hm,
			String tablename, int userid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlcolumn = "";
		String sqlvalues = "";
		
		
		if (type == 1) {
			sqlcolumn = " ( userid,status,principalid ";
			sqlvalues = " values ( " + userid + ",0, " + userid + " ";
		}
		String sqlblob = "";

		int id = 0;// 插入数据后的新id

		List<String> list_idvalues = new ArrayList<String>();
		List<String> list_relatecolumn = new ArrayList<String>();// tb_tags_relate
		List<String> list_columnname = new ArrayList<String>();
		List<String> list_content = new ArrayList<String>();

		Map<String,Object> mm = null;
		try {
			ct = DBConnection.getConnection();

			sql = " insert into " + tablename + " ";

			mm = TagsColumnUtil.getSqlColumns_sqlValues(hm, sqlvalues, sqlcolumn, list_columnname, list_content, list_idvalues, list_relatecolumn,false);
			sqlcolumn = (String)mm.get("sqlcolumn");
			sqlvalues = (String)mm.get("sqlvalues");
			list_idvalues = (List<String>)mm.get("list_idvalues");
			list_relatecolumn = (List<String>)mm.get("list_relatecolumn");
			list_columnname = (List<String>)mm.get("list_columnname");
			list_content = (List<String>)mm.get("list_content");

			sqlcolumn += " ) ";
			sqlvalues += " ) ";

			ps = ct.prepareStatement(sql + sqlcolumn + sqlvalues);
			ps.executeUpdate();
			DBConnection.closeConnectInfo(ct, ps, rs);
			
			
			ct = DBConnection.getConnection();
			sql = "select " + tablename + "_SEQUENCE.currval from dual";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				id = rs.getInt(1);
			}
			DBConnection.closeConnectInfo(ct, ps, rs);
			
			ct = DBConnection.getConnection();
			// 插入blob数据
			for (int i = 0; i < list_columnname.size(); i++) {

				sqlblob = " select " + list_columnname.get(i) + " from "
						+ tablename + " where id=" + id + " for update";
				ps = ct.prepareStatement(sqlblob);
				rs = ps.executeQuery();
				if (rs.next()) {
					// 得到java.sql.Blob对象后强制转换为oracle.sql.BLOB
					oracle.sql.BLOB blob = (oracle.sql.BLOB) rs.getBlob(1);
					OutputStream outStream = blob.getBinaryOutputStream();
					// data是传入的byte数组，定义：byte[] data
					byte[] data = list_content.get(i).getBytes();
					outStream.write(data, 0, data.length);

					outStream.flush();
					outStream.close();
				}

			}
			DBConnection.closeConnectInfo(ct, ps, rs);
			ct = DBConnection.getConnection();
			// 插入相关id
			for (int i = 0; i < list_idvalues.size(); i++) {
				sql = "insert into tb_tags_relate (mainid,relateid,columnname) values("
						+ id + ",?,'" + list_relatecolumn.get(i) + "') ";
				
				ps = ct.prepareStatement(sql);
				String relateid[] = list_idvalues.get(i).split(",");
				for (int j = 0; j < relateid.length; j++) {
					ps.setInt(1, Integer.valueOf(relateid[j]));
					ps.execute();
				}

			}

		} catch (Exception e) {
			logger.error("添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;
	}
	
	@SuppressWarnings("unchecked")
	public int insert_tableinfo_by_tablename_status9(int type, Map<String, String> hm,
			String tablename, int userid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlcolumn = "";
		String sqlvalues = "";
		
		
		if (type == 1) {
			sqlcolumn = " ( userid,status,principalid ";
			sqlvalues = " values ( " + userid + ",9, " + userid + " ";
		}
		String sqlblob = "";

		int id = 0;// 插入数据后的新id

		List<String> list_idvalues = new ArrayList<String>();
		List<String> list_relatecolumn = new ArrayList<String>();// tb_tags_relate
		List<String> list_columnname = new ArrayList<String>();
		List<String> list_content = new ArrayList<String>();
		Map<String,Object> mm = null;
		try {
			ct = DBConnection.getConnection();

			sql = " insert into " + tablename + " ";

			mm = TagsColumnUtil.getSqlColumns_sqlValues(hm, sqlvalues, sqlcolumn, list_columnname, list_content, list_idvalues, list_relatecolumn,true);
			sqlcolumn = (String)mm.get("sqlcolumn");
			sqlvalues = (String)mm.get("sqlvalues");
			list_idvalues = (List<String>)mm.get("list_idvalues");
			list_relatecolumn = (List<String>)mm.get("list_relatecolumn");
			list_columnname = (List<String>)mm.get("list_columnname");
			list_content = (List<String>)mm.get("list_content");


			sqlcolumn += " ) ";
			sqlvalues += " ) ";

			ps = ct.prepareStatement(sql + sqlcolumn + sqlvalues);
			ps.executeUpdate();
			DBConnection.closeConnectInfo(ct, ps, rs);
			ct = DBConnection.getConnection();

			sql = "select " + tablename + "_SEQUENCE.currval from dual";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				id = rs.getInt(1);
			}
			DBConnection.closeConnectInfo(ct, ps, rs);
			ct = DBConnection.getConnection();

			// 插入blob数据
			for (int i = 0; i < list_columnname.size(); i++) {

				sqlblob = " select " + list_columnname.get(i) + " from "
						+ tablename + " where id=" + id + " for update";
				ps = ct.prepareStatement(sqlblob);
				rs = ps.executeQuery();
				if (rs.next()) {
					// 得到java.sql.Blob对象后强制转换为oracle.sql.BLOB
					oracle.sql.BLOB blob = (oracle.sql.BLOB) rs.getBlob(1);
					OutputStream outStream = blob.getBinaryOutputStream();
					// data是传入的byte数组，定义：byte[] data
					byte[] data = list_content.get(i).getBytes();
					outStream.write(data, 0, data.length);

					outStream.flush();
					outStream.close();
				}

			}
			DBConnection.closeConnectInfo(ct, ps, rs);
			ct = DBConnection.getConnection();

			// 插入相关id
			for (int i = 0; i < list_idvalues.size(); i++) {
				sql = "insert into tb_tags_relate (mainid,relateid,columnname) values("
						+ id + ",?,'" + list_relatecolumn.get(i) + "') ";
				ps = ct.prepareStatement(sql);
				String relateid[] = list_idvalues.get(i).split(",");
				for (int j = 0; j < relateid.length; j++) {
					ps.setInt(1, Integer.valueOf(relateid[j]));
					ps.execute();
				}

			}

		} catch (Exception e) {
			logger.error("客户添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;
	}

	/*
	 * 获取单个表信息，查看 显示类型！！！ view_display
	 */
	public List<Tags> select_designe_field_content_by_id(List<Tags> list_tags,
			String tablename, int id) throws ElException {
		Map<String, String> map = null;

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		Connection ct2 = null;
		
		String sql = " select id";
		String sqlcolumn = "  ";
		String sqltablename = " from " + tablename + "   ";
		String sqlwhere = " where id=?";

		sqlcolumn += TagsUtil.getSqlColumns(7, list_tags);

		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql + sqlcolumn + sqltablename + sqlwhere);
			ps.setInt(1, id);

			rs = ps.executeQuery();
			if (rs.next()) {
				map = new HashMap<String, String>();
				list_tags.get(0).setColumnid(id);
				
				ct2 = DBConnection.getConnection();
				list_tags = TagsUtil.getOneData1(list_tags, map, rs, ct2, rs2, ps2,id);
				DBConnection.closeConnectInfo(ct2, ps2, rs2);
			}
		} catch (Exception e) {
			logger.error("自定义字段获取单个表信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list_tags;
	}

	/*
	 * 更新表信息 map类型：date==columnname,value
	 */
	@SuppressWarnings("unchecked")
	public void update_designe_field_content_by_id(Map<String, String> hm,
			String tablename, int id) throws ElException {
		String blob_columnname = "";
		String blob_content = "";
		boolean flag_column = true;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlset = "  ";
		String sqlwhere = "";

		String sql_relate = "";
		String sql_relate_insert = "";
		String sql_relate_get = "";

		try {
			ct = DBConnection.getConnection();
			sql = " update " + tablename + " set ";

			Iterator iterator = hm.entrySet().iterator();
			while (iterator.hasNext()) {

				java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();

				String str[] = null;
				if(entry.getKey() != null && !((String) entry.getKey()).equals("") && !String.valueOf(((String) entry.getKey())).contains("===")){
					str = ((String) entry.getKey()).split("==");// date==tb_nnn_3==yyyy年MM月dd日
				}
				if(str!=null){
					if (flag_column) {
						flag_column = false;
					} else {
						sqlset += ",";
					}
					
					sqlset += str[1] + "=";
					if (str[0].equals("复选")) {
						sqlset += " '" + (String) entry.getValue() + "' ";
					}
					if (str[0].equals("date")) {
						if(str.length == 2){
							sqlset += " to_date('" + (String) entry.getValue()
							+ "','yyyy-mm-dd hh24:mi:ss') ";
						}else {
							sqlset += " to_date('" + ScheduleUtil.formatToOra((String) entry.getValue(), str[2])
							+ "','yyyy-mm-dd hh24:mi:ss') ";
						}
					} else if (str[0].equals("number") || str[0].equals("float")) {
						sqlset += " " + (String) entry.getValue() + " ";
					} else if (str[0].indexOf("varchar2") > -1) {
						String value = (String) entry.getValue();
						if (value == null)
							value = "";
						sqlset += " '" + value + "' ";
					} else if (str[0].equals("relate")) {
						int relatecount = -1;
						// 先插入关系表，然后全部取出，最后更新
						sql_relate = "	select count(*) from tb_tags_relate "
								+ "	where relateid=? and mainid=? and columnname='"
								+ str[1] + "' ";
						sql_relate_insert = " insert into tb_tags_relate (columnname,relateid,mainid) values(?,?,?) ";
						String relateid[] = ((String) entry.getValue()).split(",");
						String newids = "";
						for (int i = 0; i < relateid.length; i++) {
							ps = ct.prepareStatement(sql_relate);
							if(!relateid[i].equals("")){
								ps.setInt(1, Integer.valueOf(relateid[i]));
								ps.setInt(2, id);
								rs = ps.executeQuery();
								if (rs.next()) {
									relatecount = rs.getInt(1);
								}
								if (relatecount == 0)// 插入
								{
									ps = ct.prepareStatement(sql_relate_insert);
									ps.setString(1, str[1]);
									ps.setInt(2, Integer.valueOf(relateid[i]));
									ps.setInt(3, id);

									ps.executeUpdate();
								}
								relatecount = -1;
							}
						}
						sql_relate_get = " select relateid from tb_tags_relate where mainid=? and columnname='"
								+ str[1] + "'";
						ps = ct.prepareStatement(sql_relate_get);
						ps.setInt(1, id);
						rs = ps.executeQuery();
						while (rs.next()) {
							newids += rs.getInt(1);
							newids += ",";
						}
						if (newids.length() > 0)
							newids = newids.substring(0, newids.length() - 1);
						sqlset += " '" + newids + "' ";

					} else if (str[0].equals("blob")) {
						sqlset += "  empty_blob()  ";
						blob_columnname = str[1];
						blob_content = (String) entry.getValue();
					}
				}

			}
			sqlwhere = " where id=" + id;

			ps = ct.prepareStatement(sql + sqlset + sqlwhere);
			ps.executeUpdate();

			// ==========更新 blob
			if (!blob_columnname.equals("")) {
				String sqlblob = " select " + blob_columnname + " from "
						+ tablename + " where id=" + id + " for update";
				ps = ct.prepareStatement(sqlblob);
				rs = ps.executeQuery();
				if (rs.next()) {
					// 得到java.sql.Blob对象后强制转换为oracle.sql.BLOB
					oracle.sql.BLOB blob = (oracle.sql.BLOB) rs.getBlob(1);
					OutputStream outStream = blob.getBinaryOutputStream();
					// data是传入的byte数组，定义：byte[] data
					byte[] data = blob_content.getBytes();
					String str = new String(data);
					outStream.write(data, 0, data.length);

					outStream.flush();
					outStream.close();
				}

			}

		} catch (Exception e) {
			logger.error("客户添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/*
	 * 组合查询 获取要显示的表信息,列表显示
	 */
	public List<Map<String, String>> select_my_tableinfo_by_userid(
			List<Tags> list_tags, String tablename, Map<String, String> hm,
			int userid, int pageNow, int pageSize) throws ElException {
		boolean flag_colume = true;
		Map<String, String> map = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;

		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		Connection ct2 = null;

		String sqlBegin = " SELECT * FROM ( " + " SELECT A.*, ROWNUM RN "
				+ " FROM ( ";
		String sql = " select id,status";
		String sqlcolumn = "  ";
		String sqltablename = " from " + tablename + "";
		String sqlwhere = " where userid=? ";
		String sqlorder = " order by id desc ";
		String sqlEnd = "    ) A " + " WHERE ROWNUM <= ? " + " )WHERE RN >= ? ";

		// sqlcolumn
		for (int i = 0; i < list_tags.size(); i++) {

			if (list_tags.get(i).getList_display() == 1) {
				// if (flag_colume)
				// {
				// flag_colume = false;
				// }
				// else
				// {
				// sqlcolumn += ",";
				// }

				sqlcolumn += ",";

				if (list_tags.get(i).getColumn_type().equals("date")) {
					sqlcolumn += " to_char("
							+ list_tags.get(i).getColumn_name()
							+ ",'yyyy-mm-dd') "
							+ list_tags.get(i).getColumn_name();
				} else {
					sqlcolumn += " " + list_tags.get(i).getColumn_name() + " ";
				}
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

				sqlwhere += " and id in (select mainid from tb_tags_relate where relateid in "
						+ " ( select id from "
						+ str[1]
						+ " where "
						+ str[2]
						+ " like '%" + (String) entry.getValue() + "%')) ";
				// and id in (select mainid from tb_tags_relate where relateid
				// in(select id ))

			}
		}

		try {
			ct = DBConnection.getConnection();
			ct2 = DBConnection.getConnection();
			ps = ct.prepareStatement(sqlBegin + sql + sqlcolumn + sqltablename
					+ sqlwhere + sqlorder + sqlEnd);
			ps.setInt(1, userid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);

			rs = ps.executeQuery();
			while (rs.next()) {
				map = new HashMap<String, String>();
				for (int i = 0; i < list_tags.size(); i++) {
					if (list_tags.get(i).getList_display() == 1) {
						if (list_tags.get(i).getColumn_type().equals("number")) {
							map.put(list_tags.get(i).getColumn_name(), String
									.valueOf(rs.getInt(list_tags.get(i)
											.getColumn_name())));
						} else if (list_tags.get(i).getColumn_type().equals(
								"float")) {
							map.put(list_tags.get(i).getColumn_name(), String
									.valueOf(rs.getFloat(list_tags.get(i)
											.getColumn_name())));
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

	/**
	 * 我添加的或者全部的size
	 */
	public int select_my_tableinfo_by_userid_count(int type,
			List<Tags> list_tags, Map<String, String> hm, String tablename,
			int userid) throws ElException {
		int count = 0;

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		if (type == 1) {
			sql = " select count(*) from " + tablename + " t  where 1 = 1 ";
		} else {

			sql = " select count(*) from " + tablename + " t where userid="
					+ userid;
		}

		String sqlwhere = "";
		try {
			ct = DBConnection.getConnection();

			// sqlwhere
			sqlwhere += TagsUtil.getSqlWhere(hm);

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

	/**
	 * 我负责的SIZE
	 */
	public int select_my_tableinfo_by_userid_count_1(int type,
			List<Tags> list_tags, Map<String, String> hm, String tablename,
			int userid) throws ElException {
		int count = 0;

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlAppend = "";
		if (type == 1) {
			sql = " select count(*) from " + tablename
					+ " t ,eluser e,department d "
					+ " where e.id=t.userid and d.id=e.depid ";
		}
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getDisplay_type().equals("相关负责人")) {
				sqlAppend = " and t.id in (select mainid from tb_tags_relate where columnname = '"
						+ list_tags.get(i).getColumn_name()
						+ "' and relateid="
						+ userid + ") ";
			}
		}

		String sqlwhere = "";
		try {
			ct = DBConnection.getConnection();


			// sqlwhere
			sqlwhere += TagsUtil.getSqlWhere(hm);

			ps = ct.prepareStatement(sql + sqlwhere + sqlAppend);
			rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("查询我负责的SIZE出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}

	/*
	 * 部门查询，通过左右id查询
	 */
	public List<Map<String, String>> select_my_tableinfo_by_dep(
			List<Tags> list_tags, String tablename, Map<String, String> hm,
			ElNode department, String order, int pageNow, int pageSize)
			throws ElException {
		Map<String, String> map = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;

		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		Connection ct2 = null;

		String sqlBegin = " SELECT * FROM ( " + " SELECT A.*, ROWNUM RN "
				+ " FROM ( ";
		String sql = " select  t.id,t.status ,e.username ,(select username from eluser where id=t.principalid) principalname ,d.name ";
		String sqlcolumn = "  ";
		String sqltablename = " from " + tablename
				+ " t, eluser e   ,department d ";
		String sqljoin = " join ("
				+ ((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
						.generateSQLByTree("department", department, true)
				+ ") dep on dep.id=d.id ";
		String sqlwhere0 = " where  userid in ";
		String sqlwhere_dep = " (select id from eluser where valid=1 ";

		String sqlwhere = " and t.userid=e.id  and  e.depid=d.id ) ";
		String sqlorder = "  order by id desc ";
		String sqlEnd = "    ) A " + " WHERE ROWNUM <= ? " + " )WHERE RN >= ? ";

		String allsql = "";
		String sqlAppend = "";

		if (order != null) {
			if (!order.equals("")) {
				sqlorder = order;
			}
		}

		sqlcolumn += TagsUtil.getSqlColumns(3, list_tags);
		
		if (this.checkTable(tablename) == 2) {
			sqlcolumn += " ,moduleid,danjuid ";
		}

		sqlwhere += TagsUtil.getSqlWhere(hm);

		try {
			ct = DBConnection.getConnection();
			allsql = sqlBegin + sql + sqlcolumn + sqltablename + sqljoin
					+ sqlwhere0 + sqlwhere_dep + sqlwhere + sqlAppend
					+ sqlorder + sqlEnd;

			ps = ct.prepareStatement(allsql);

			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);

			rs = ps.executeQuery();
			while (rs.next()) {
				map = new HashMap<String, String>();
				ct2 = DBConnection.getConnection();
				map = TagsUtil.getOneData(3,list_tags, map, rs, ct2, rs2, ps2);
				DBConnection.closeConnectInfo(ct2, ps2, rs2);
				
				if (this.checkTable(tablename) == 2) {
					map.put("moduleid", rs.getString("moduleid"));
				}
				map.put("id", String.valueOf(rs.getInt("id")));
				map.put("status", rs.getString("status"));

				list.add(map);
			}
		} catch (Exception e) {
			logger.error("客户添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	public int select_my_tableinfo_by_dep_count(List<Tags> list_tags,
			Map<String, String> hm, String tablename, Department department)
			throws ElException {
		int count = 0;

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;

		String sql = " select count(*) from "
				+ tablename
				+ " "
				+ " t, eluser e   ,department d "
				+ " join ("
				+ ((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
						.generateSQLByTree("department", department, true)
				+ ") dep on dep.id=d.id "
				+ "where userid in "
				+ " ("
				+ "   select id from eluser where valid=1 and t.userid=e.id  and  e.depid=d.id "
				+ " ) ";
		String sqlwhere = " ";
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
							+ " is not null and t.id in (select mainid from tb_tags_relate where relateid in "
							+ " ( select id from " + str[1] + " where "
							+ str[2] + " like '%" + (String) entry.getValue()
							+ "%')) ";
				}
			}

			ps = ct.prepareStatement(sql + sqlwhere);
			// ps.setInt(1, department.getLid());
			// ps.setInt(2, department.getRid());

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

	/*
	 * 管理页面是否显示
	 */
	public void manage_designe_field(List<Tags> list) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;

		String sql = "";

		try {
			ct = DBConnection.getConnection();

			for (int i = 0; i < list.size(); i++) {

				if (list.get(i).getCannot_modify() != 1) {
					sql = " update tb_designe set " + " add_display=?, "
							+ " update_display=?, " + " view_display=?, "
							+ " list_display=?," + " mutilsearch_display=?,"
							+ " departsearch_display=?, " + " sn=?,required=? "
							+ " where id=? ";

					ps = ct.prepareStatement(sql);

					ps.setInt(1, list.get(i).getAdd_display());
					ps.setInt(2, list.get(i).getUpdate_display());
					ps.setInt(3, list.get(i).getView_display());
					ps.setInt(4, list.get(i).getList_display());
					ps.setInt(5, list.get(i).getMutilsearch_display());
					ps.setInt(6, list.get(i).getDepartsearch_display());
					ps.setInt(7, list.get(i).getSn());
					ps.setInt(8, list.get(i).getRequired());
					ps.setInt(9, list.get(i).getId());

				} else {
					sql = " update tb_designe set " + " sn=? " + " where id=? ";
					ps = ct.prepareStatement(sql);

					ps.setInt(1, list.get(i).getSn());
					ps.setInt(2, list.get(i).getId());
				}

				ps.executeUpdate();
			}

		} catch (Exception e) {
			logger.error("自定义字段修改序列出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	// ------------------------------------------------------------
	/*
	 * 非组合查询 获取要显示的表信息，列表显示 显示信息！！！！ list_display
	 */
	public List<Map<String, String>> select_my_tableinfo_by_tablename(
			List<Tags> list_tags, String tablename, int pageNow, int pageSize)
			throws ElException {
		boolean flag_colume = true;
		Map<String, String> map = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sqlBegin = " SELECT * FROM ( " + " SELECT A.*, ROWNUM RN "
				+ " FROM ( ";
		String sql = " select id,";
		String sqlcolumn = "  ";
		String sqltablename = " from " + tablename + " order by id ";
		String sqlEnd = " ) A " + " WHERE ROWNUM <= ? " + " )WHERE RN >= ? ";

		for (int i = 0; i < list_tags.size(); i++) {

			if (list_tags.get(i).getList_display() == 1) {
				if (flag_colume) {
					flag_colume = false;
				} else {
					sqlcolumn += ",";
				}

				if (list_tags.get(i).getColumn_type().equals("date")) {
					sqlcolumn += " to_char("
							+ list_tags.get(i).getColumn_name()
							+ ",'yyyy-mm-dd') "
							+ list_tags.get(i).getColumn_name();
				} else {
					sqlcolumn += " " + list_tags.get(i).getColumn_name() + " ";
				}
			}

		}

		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sqlBegin + sql + sqlcolumn + sqltablename
					+ sqlEnd);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);

			rs = ps.executeQuery();
			while (rs.next()) {
				map = new HashMap<String, String>();
				for (int i = 0; i < list_tags.size(); i++) {
					if (list_tags.get(i).getList_display() == 1) {
						if (list_tags.get(i).getColumn_type().equals("number")) {
							map.put(list_tags.get(i).getColumn_name(), String
									.valueOf(rs.getInt(list_tags.get(i)
											.getColumn_name())));
						} else if (list_tags.get(i).getColumn_type().equals(
								"float")) {
							map.put(list_tags.get(i).getColumn_name(), String
									.valueOf(rs.getFloat(list_tags.get(i)
											.getColumn_name())));
						} else
						// string
						{
							map.put(list_tags.get(i).getColumn_name(), rs
									.getString(list_tags.get(i)
											.getColumn_name()));
						}
					}// if
				}// for
				map.put("id", String.valueOf(rs.getInt("id")));

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

	public int select_my_tableinfo_by_tablename_count(List<Tags> list_tags,
			String tablename) throws ElException {
		int count = 0;

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = " select count(*) from " + tablename;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
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

	/*
	 * 检测富文本是否已有
	 */
	public String checkRichtext(String tablename) throws ElException {
		String result = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = " select display_type from tb_designe where display_type='富文本' and table_name='"
				+ tablename + "' ";
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				// rs.getInt(1);
				result = "yes";
			}
		} catch (Exception e) {
			logger.error("客户添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	/*
	 * 删除单条信息
	 */
	public void delete_from_tablename_by_id(String tablename, int id)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = " delete  from " + tablename + " where id=" + id;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.executeUpdate();

			// 删除这个id相关联的相关字段
			sql = " delete from tb_tags_relate where mainid=" + id;
			ps = ct.prepareStatement(sql);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error(tablename + "表删除出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/*
	 * 审核
	 * 
	 * 
	 * 初审
	 */
	// 提交初审：已创建-》初审等待中
	public void commit_verity_pass_tags(String tablename, int id)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();

			sql = "update " + tablename + " " + " set status=? "
					+ " where id=? ";

			ps = ct.prepareStatement(sql);

			ps.setInt(1, 5);
			ps.setInt(2, id);

			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("初审出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	// 初审通过：初审等待中-》终审等待中
	public void verify_pass_tags(String tablename, int id,int status,int userid,int depid,AuditMark auditMark,String auditOrder) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();

			sql = "update " + tablename + " " + " set status=?,audituserid=?,audittime=?,auditdepid=? "
					+ " where id=? ";

			ps = ct.prepareStatement(sql);

			ps.setInt(1, status);
			ps.setInt(2, userid);
			ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			ps.setInt(4, depid);
			ps.setInt(5, id);

			ps.executeUpdate();
			
			sql = " insert into tb_audit_mark (moduleid,entityid,auditmark,time,status,ord) " +
					" values (?,?,?,?,?,?) ";
			ps = ct.prepareStatement(sql);
			ps.setString(1, auditMark.getModuleid());
			ps.setInt(2, auditMark.getEntityid());
			ps.setString(3, auditMark.getAudit_mark()==null?"":auditMark.getAudit_mark());
			ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			ps.setInt(5, status);
			ps.setString(6, auditOrder);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("初审出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	// 初审不通过：初审等待中-》已创建
	public void verify_nopass_tags(String tablename, int id,int status,int userid,int depid,AuditMark auditMark,String auditOrder) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();

			sql = "update " + tablename + " " + " set status=?,audituserid=?,audittime=?,auditdepid=? "
					+ " where id=? ";

			ps = ct.prepareStatement(sql);

			ps.setInt(1, status);
			ps.setInt(2, userid);
			ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			ps.setInt(4, depid);
			ps.setInt(5, id);

			ps.executeUpdate();

			sql = " insert into tb_audit_mark (moduleid,entityid,auditmark,time,status,ord) " +
					" values (?,?,?,?,?,?) ";
			ps = ct.prepareStatement(sql);
			ps.setString(1, auditMark.getModuleid());
			ps.setInt(2, auditMark.getEntityid());
			ps.setString(3, auditMark.getAudit_mark()==null?"":auditMark.getAudit_mark());
			ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			ps.setInt(5, status);
			ps.setString(6, auditOrder);
			ps.executeUpdate();
	
		} catch (Exception e) {
			logger.error("审核不通过客户联系出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/*
	 * 终审
	 */
	// 终审通过：->通过
	public void verify_pass_final_tags(String tablename, int id,int status,int userid,int depid,AuditMark auditMark,String auditOrder)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();

			sql = "update " + tablename + " " + " set status=?,audituserid=?,audittime=?,auditdepid=? "
					+ " where id=? ";

			ps = ct.prepareStatement(sql);

			ps.setInt(1, status);
			ps.setInt(2, userid);
			ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			ps.setInt(4, depid);
			ps.setInt(5, id);

			ps.executeUpdate();
			
			
			sql = " insert into tb_audit_mark (moduleid,entityid,auditmark,time,status,ord) " +
			" values (?,?,?,?,?,?) ";
			ps = ct.prepareStatement(sql);
			ps.setString(1, auditMark.getModuleid());
			ps.setInt(2, auditMark.getEntityid());
			ps.setString(3, auditMark.getAudit_mark()==null?"":auditMark.getAudit_mark());
			ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			ps.setInt(5, status);
			ps.setString(6, "终审");
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("终审出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	// 终审不通过：终审等待中-》已创建
	public void verify_nopass_final_tags(String tablename, int id,int status,int userid,int depid,AuditMark auditMark,String auditOrder)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();

			sql = "update " + tablename + " " + " set status=?,audituserid=?,audittime=?,auditdepid=? "
					+ " where id=? ";

			ps = ct.prepareStatement(sql);

			ps.setInt(1, status);
			ps.setInt(2, userid);
			ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			ps.setInt(4, depid);
			ps.setInt(5, id);

			ps.executeUpdate();
			
			sql = " insert into tb_audit_mark (moduleid,entityid,auditmark,time,status,ord) " +
			" values (?,?,?,?,?,?) ";
			ps = ct.prepareStatement(sql);
			ps.setString(1, auditMark.getModuleid());
			ps.setInt(2, auditMark.getEntityid());
			ps.setString(3, auditMark.getAudit_mark()==null?"":auditMark.getAudit_mark());
			ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			ps.setInt(5, status);
			ps.setString(6, "终审");
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("终审出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	// 申请修改:-》修改等待中
	public void apply_update(String tablename, int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();

			sql = "update " + tablename + " " + " set status=? "
					+ " where id=? ";

			ps = ct.prepareStatement(sql);

			ps.setInt(1, 2);
			ps.setInt(2, id);

			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("终审出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	// 申请删除：-》删除等待中
	public void apply_del(String tablename, int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();

			sql = "update " + tablename + " " + " set status=? "
					+ " where id=? ";

			ps = ct.prepareStatement(sql);

			ps.setInt(1, 3);
			ps.setInt(2, id);

			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("终审出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	// 允许修改：-》已创建
	public void allow_update(String tablename, int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();

			sql = "update " + tablename + " " + " set status=? "
					+ " where id=? ";

			ps = ct.prepareStatement(sql);

			ps.setInt(1, 0);
			ps.setInt(2, id);

			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("终审出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	// 允许删除：直接删除
	public void allow_del(String tablename, int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();

			// sql="update "+tablename+" " +
			// " set status=? " +
			// " where id=? ";
			sql = " delete from " + tablename + " " + " where id=" + id;
			ps = ct.prepareStatement(sql);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("删除出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	// 清空
	public void allow_del(String tablename) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();

			// sql="update "+tablename+" " +
			// " set status=? " +
			// " where id=? ";
			sql = " delete from " + tablename ;
			ps = ct.prepareStatement(sql);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("删除出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	// 不允许修改:-》终审通过
	public void noallow_update(String tablename, int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();

			sql = "update " + tablename + " " + " set status=? "
					+ " where id=? ";

			ps = ct.prepareStatement(sql);

			ps.setInt(1, 9);
			ps.setInt(2, id);

			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("终审出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	// 不允许删除-》终审通过
	public void noallow_del(String tablename, int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();

			sql = "update " + tablename + " " + " set status=? "
					+ " where id=? ";

			ps = ct.prepareStatement(sql);

			ps.setInt(1, 9);
			ps.setInt(2, id);

			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("终审出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/*
	 * 组合查询 获取要显示的表信息,负责列表显示
	 * list_tag:自定义表信息；由select_designe_field_by_tablename返回 hm:组合搜索条件
	 * userid:用户id，eluser表
	 */
	public List<Map<String, String>> select_my_tableinfo_by_principalid(
			List<Tags> list_tags, String tablename, Map<String, String> hm,
			int principalid, int pageNow, int pageSize) throws ElException {
		boolean flag_colume = true;
		Map<String, String> map = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;

		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		Connection ct2 = null;

		String sqlBegin = " SELECT * FROM ( " + " SELECT A.*, ROWNUM RN "
				+ " FROM ( ";
		String sql = " select t.id,t.status,e.username";
		String sqlcolumn = "  ";
		String sqltablename = " from " + tablename + "  t,eluser e ";
		String sqlwhere = " where principalid=? and t.principalid=e.id ";
		String sqlorder = " order by id desc ";
		String sqlEnd = "    ) A " + " WHERE ROWNUM <= ? " + " )WHERE RN >= ? ";

		// sqlcolumn
		for (int i = 0; i < list_tags.size(); i++) {

			if (list_tags.get(i).getList_display() == 1) {
				// if (flag_colume)
				// {
				// flag_colume = false;
				// }
				// else
				// {
				// sqlcolumn += ",";
				// }

				sqlcolumn += ",";

				if (list_tags.get(i).getColumn_type().equals("date")) {
					sqlcolumn += " to_char("
							+ list_tags.get(i).getColumn_name()
							+ ",'yyyy-mm-dd') "
							+ list_tags.get(i).getColumn_name();
				} else {
					sqlcolumn += " " + list_tags.get(i).getColumn_name() + " ";
				}
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
				sqlwhere += " and id in (select mainid from tb_tags_relate where relateid in "
						+ " ( select id from "
						+ str[1]
						+ " where "
						+ str[2]
						+ " like '%" + (String) entry.getValue() + "%')) ";
			}
		}

		try {
			ct = DBConnection.getConnection();
			ct2 = DBConnection.getConnection();
			ps = ct.prepareStatement(sqlBegin + sql + sqlcolumn + sqltablename
					+ sqlwhere + sqlorder + sqlEnd);
			ps.setInt(1, principalid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);

			rs = ps.executeQuery();
			while (rs.next()) {
				map = new HashMap<String, String>();
				for (int i = 0; i < list_tags.size(); i++) {
					if (list_tags.get(i).getList_display() == 1) {
						if (list_tags.get(i).getColumn_type().equals("number")) {
							map.put(list_tags.get(i).getColumn_name(), String
									.valueOf(rs.getInt(list_tags.get(i)
											.getColumn_name())));
						} else if (list_tags.get(i).getColumn_type().equals(
								"float")) {
							map.put(list_tags.get(i).getColumn_name(), String
									.valueOf(rs.getFloat(list_tags.get(i)
											.getColumn_name())));
						} else {
							if (list_tags.get(i).getDisplay_type().equals(
									"相关字段")) {
								// String id = rs.getString(list_tags.get(i)
								// .getColumn_name());// 获取相关字段值，序列id值
								// if (id == null)
								// continue;
								// if (id.equals(""))
								// continue;
								// String str = list_tags.get(i)
								// .getDefault_value();
								// String arr[] = str.split("==");//
								// tb_mm_28==tb_mm_28_171==反反复复==varchar2(500)
								// String sql_relate = " select " + arr[1]
								// + " from " + arr[0] + " where id=" + id;
								// ps2 = ct2.prepareStatement(sql_relate);
								// rs2 = ps2.executeQuery();
								// if (rs2.next())
								// {
								// list_tags.get(i).setValue(rs2.getString(1));
								// }
								// map.put(list_tags.get(i).getColumn_name(),
								// list_tags.get(i).getValue());
								//								
								//								
								//
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
				map.put("principal", rs.getString("username"));

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

	public int select_my_tableinfo_by_principalid_count(List<Tags> list_tags,
			Map<String, String> hm, String tablename, int principalid)
			throws ElException {
		int count = 0;

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = " select count(*) from " + tablename
				+ " where principalid=" + principalid;
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
					sqlwhere += " and id in (select mainid from tb_tags_relate where relateid in "
							+ " ( select id from "
							+ str[1]
							+ " where "
							+ str[2]
							+ " like '%"
							+ (String) entry.getValue()
							+ "%')) ";
				}
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

	// 删除相关
	public void delete_relate_by_relateid_and_conlumnname(
			String column_in_result_table_, String columns_in_produce_table_,
			String columnname, String tablename, int relateid, int id)
			throws ElException {
		String ids = "";
		String newid = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = " delete  from tb_tags_relate  where columnname='"
				+ columnname + "' and relateid=" + relateid + " and mainid="
				+ id;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.executeUpdate();

			// ------------
			sql = "select " + columnname + " from " + tablename + " where id="
					+ id;
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				ids = rs.getString(1);
			}
			String tmp[] = ids.split(",");
			for (int i = 0; i < tmp.length; i++) {
				if (!tmp[i].equals(String.valueOf(relateid))) {
					newid += tmp[i];
					newid += ",";
				}
			}
			if (newid.length() > 0)
				newid = newid.substring(0, newid.length() - 1);

			sql = " update " + tablename + " set " + columnname + "='" + newid
					+ "' where id=" + id;

			ps = ct.prepareStatement(sql);
			ps.executeUpdate();

			// 如果表间计算,删除过程表中数据
			if (this.getProduceTableByTable(tablename) != null
					&& !this.getProduceTableByTable(tablename).equals("")) {
				String produce_table = this.getProduceTableByTable(tablename);
				this.deleteProduceTableById(produce_table, tablename, relateid,
						id);// 删除过程表中数据,moduleid,物品id,单据id

				// 删除成功后，将过程表中删除的数据表间计算字段更新到结果表
				this.updateResultContent(this
						.getResultTableByProduceTable(produce_table),
						produce_table, column_in_result_table_,
						columns_in_produce_table_, relateid, id);
			}

		} catch (Exception e) {
			logger.error("出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void deleteProduceTableById(String produce_table, String moduleid,
			int entityid, int danjuid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();

			sql = "delete " + produce_table + " where moduleid = '" + moduleid
					+ "' and entityid = " + entityid + " and danjuid="
					+ danjuid;
			ps = ct.prepareStatement(sql);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除过程表中数据出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/*
	 * 获取负责人表eluser
	 */
	public List<Eluser> select_eluser_by_dep(int is_judge_for_user,
			String tablename, Eluser eluser, ElNode department, int pageNow,
			int pageSize, int nid) throws ElException {
		List<Eluser> list = new ArrayList<Eluser>();
		// Eluser eluser = null;// new Eluser();

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sqlAppend = "";
		String sql = "";
		// String sql = " SELECT * FROM ( "
		// + " SELECT A.*, ROWNUM RN "
		// + " FROM ( "
		// + " select * "
		// + " from eluser "
		// + " where id in "
		// + " (select id from eluser where valid=1 "
		// + " and depid in "
		// + " (select id from department where lid >= ? and rid <= ?) )"
		// + " order by id desc " + " ) A " + " WHERE ROWNUM <= ? "
		// + " )WHERE RN >= ? ";

		try {
			ct = DBConnection.getConnection();

			if (tablename.toUpperCase().equals("ELUSER")) {
				if (eluser != null) {
					if (eluser.getRealname() != null
							&& !eluser.getRealname().equals(""))
						sqlAppend = sqlAppend + " and e.realname like '%"
								+ eluser.getRealname() + "%'";
					if (eluser.getUsername() != null
							&& !eluser.getUsername().equals(""))
						sqlAppend = sqlAppend + " and e.username like '%"
								+ eluser.getUsername() + "%'";
					if (eluser.getRole() != 0)
						sqlAppend = sqlAppend + " and e.role = '"
								+ eluser.getRole() + "'";
					if (eluser.getZhiwu() != null
							&& !eluser.getZhiwu().equals(""))
						sqlAppend = sqlAppend + " and e.zhiwu = '"
								+ eluser.getZhiwu() + "'";
					if (eluser.getJingzhong() != null
							&& !eluser.getJingzhong().equals(""))
						sqlAppend = sqlAppend + " and e.jingzhong = '"
								+ eluser.getJingzhong() + "'";
				}

				sql = " SELECT * FROM ( "
						+ " SELECT A.*, ROWNUM RN "
						+ " FROM ( "
						+ " select e.id id,e.danwei danwei,e.gangwei gangwei,e.zhiwu zhiwu, e.realname,d.name dname,r.name rname,e.username  "
						+ " from eluser e,department d,elrole r  "
						+ " where  e.id in  "
						+ " (select id from eluser where valid=1  "
						+ "  and depid in ";
				sql += is_judge_for_user == 1 ? " (select id from department where lid >= ? and rid <= ? )   "
						: " (select id from department )   ";
				sql += ") " + " and d.id=e.depid and  e.role=r.id  " + sqlAppend
						+ "  order by id desc " + "    ) A "
						+ " WHERE ROWNUM <= ? " + " )WHERE RN >= ? ";
			} else if (tablename.toUpperCase().equals("PFMSUSER")) {
				if (eluser != null && eluser.getPfmsUser() != null) {
					if (eluser.getPfmsUser().getRespName() != null
							&& !eluser.getPfmsUser().getRespName().equals(""))
						sqlAppend = sqlAppend + " and pu.respname like '%"
								+ eluser.getPfmsUser().getRespName() + "%'";
				}

				sql = " SELECT * FROM ( "
						+ " SELECT A.*, ROWNUM RN "
						+ " FROM ( "
						+ " select pu.id puid,pu.userid,pu.respname,pu.address,pu.mobile,pu.fex,d.name dname,r.name rname  "
						+ " from pfmsuser pu,department d,elrole r,eluser e"
						+ " where pu.userid=e.id and pu.userid in  "
						+ " (select id from eluser where valid=1  "
						+ "  and depid in "
						+ " (select id from department where lid >= ? and rid <= ? )   ) "
						+ " and d.id=e.depid and  e.role=r.id " + sqlAppend
						+ "  order by puid desc " + "    ) A "
						+ " WHERE ROWNUM <= ? " + " )WHERE RN >= ? ";
			}

			ps = ct.prepareStatement(sql);

			if (is_judge_for_user == 0) {
				ps.setInt(1, pageNow);
				ps.setInt(2, pageSize);
			} else if (is_judge_for_user == 1) {
				ps.setInt(1, department.getLid());
				ps.setInt(2, department.getRid());
				ps.setInt(3, pageNow);
				ps.setInt(4, pageSize);
			}

			rs = ps.executeQuery();
			while (rs.next()) {
				if (tablename.toUpperCase().equals("ELUSER")) {
					eluser = new Eluser();
					eluser.setId(rs.getInt("id"));
					eluser.setDanwei(rs.getString(2));
					eluser.setGangwei(rs.getString(3));
					eluser.setZhiwu(rs.getString(4));
					eluser.setRealname(rs.getString(5));
					eluser.setDepartmentname(rs.getString(6));
					eluser.setRolename(rs.getString(7));
					eluser.setUsername(rs.getString(8));
				} else if (tablename.toUpperCase().equals("PFMSUSER")) {
					eluser = new Eluser();
					PfmsUser pfmsUser = new PfmsUser();
					pfmsUser.setId(rs.getInt("puid"));
					pfmsUser.setRespName(rs.getString("respname"));
					pfmsUser.setAddress(rs.getString("address"));
					pfmsUser.setMobile(rs.getString("mobile"));
					pfmsUser.setFex(rs.getString("fex"));
					eluser.setPfmsUser(pfmsUser);
				}

				list.add(eluser);
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

	private String createPerTypeId(ElNode ptypeTree, int ptid) {
		if (ptypeTree != null) {
			if (ptypeTree.getId() != ptid) {
				ptypeTree = getCourseTypeById(ptypeTree.getNchild(), ptid,
						ptypeTree);
			}
			if (ptypeTree != null && ptypeTree.getNchild() != null) {
				return createTypeId(ptypeTree.getNchild(), ptypeTree.getId());
			}
			return String.valueOf(ptypeTree != null ? ptypeTree.getId() : "0");
		} else {
			return null;
		}
	}

	private ElNode getCourseTypeById(List<ElNode> listType, int ptid,
			ElNode ptypeTree) {
		ElNode productType = null;
		for (ElNode type : listType) {
			if (type.getId() != ptid) {
				productType = getCourseTypeById(type.getNchild(), ptid,
						ptypeTree);
				if (productType != null) {
					return productType;
				}
			} else {
				return type;
			}
		}
		return productType;
	}

	private String createTypeId(List<ElNode> listType, int id) {
		String ids = id + "";
		for (ElNode type : listType) {
			ids = ids + "," + createTypeId(type.getNchild(), type.getId());
		}
		return ids;
	}

	public int select_eluser_by_dep_count(int is_judge_for_user,
			String tablename, Eluser eluser, ElNode department, int pageNow,
			int pageSize, int nid) throws ElException {
		int count = 0;
		String sqlAppend = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		// String sql = " SELECT * FROM ( "
		// + " SELECT A.*, ROWNUM RN "
		// + " FROM ( "
		// + " select count(*) "
		// + " from eluser "
		// + " where id in "
		// + " (select id from eluser where valid=1 "
		// + " and depid in "
		// + " (select id from department where lid >= ? and rid <= ?) )"
		// + " order by id desc " + " ) A " + " WHERE ROWNUM <= ? "
		// + " )WHERE RN >= ? ";

		try {
			ct = DBConnection.getConnection();
			if (tablename.toUpperCase().equals("ELUSER")) {
				if (eluser != null) {
					if (eluser.getRealname() != null
							&& !eluser.getRealname().equals(""))
						sqlAppend = sqlAppend + " and realname like '%"
								+ eluser.getRealname() + "%'";
					if (eluser.getUsername() != null
							&& !eluser.getUsername().equals(""))
						sqlAppend = sqlAppend + " and username like '%"
								+ eluser.getUsername() + "%'";
					if (eluser.getRole() != 0)
						sqlAppend = sqlAppend + " and role = '"
								+ eluser.getRole() + "'";
					if (eluser.getZhiwu() != null
							&& !eluser.getZhiwu().equals(""))
						sqlAppend = sqlAppend + " and zhiwu = '"
								+ eluser.getZhiwu() + "'";
					if (eluser.getJingzhong() != null
							&& !eluser.getJingzhong().equals(""))
						sqlAppend = sqlAppend + " and jingzhong = '"
								+ eluser.getJingzhong() + "'";
				}
				sql = " select  count(*) " + " from eluser " + " where id in "
						+ " (select id from eluser where valid=1 "
						+ " and depid in ";
				sql += is_judge_for_user == 1 ? " (select id from department where lid >= ? and rid <= ?)   )"
						: " (select id from department)   )";
				sql += sqlAppend + "  order by id desc ";
			} else if (tablename.toUpperCase().equals("PFMSUSER")) {
				if (eluser != null && eluser.getPfmsUser() != null) {
					if (eluser.getPfmsUser().getRespName() != null
							&& !eluser.getPfmsUser().getRespName().equals(""))
						sqlAppend = sqlAppend + " and pu.respname like '%"
								+ eluser.getPfmsUser().getRespName() + "%'";
				}

				sql = " select  count(*) "
						+ " from pfmsuser "
						+ " where userid in "
						+ " (select id from eluser where valid=1 "
						+ " and depid in "
						+ " (select id from department where lid >= ? and rid <= ?)   )"
						+ sqlAppend + "  order by id desc ";
			}

			ps = ct.prepareStatement(sql);
			if (is_judge_for_user == 1) {
				ps.setInt(1, department.getLid());
				ps.setInt(2, department.getRid());
			}
			// ps.setInt(3, pageNow);
			// ps.setInt(4, pageSize);

			rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
				// System.out.println();
			}// while
		} catch (Exception e) {
			logger.error("客户添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return count;
	}

	/*
	 * 修改负责人
	 */
	public void update_principal(String tablename, int id, int principalid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "update " + tablename + " set principalid=" + principalid
				+ " where id=" + id;

		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("客户添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/*
	 * 修改负责人自定义列
	 */
	public void update_fuzeren_zidingyi(String updateType, String tablename,
			int id, String update_user_ids, String columnName)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String[] arr = null;

		try {
			ct = DBConnection.getConnection();

			sql = "update " + tablename + " set " + columnName + "='"
					+ update_user_ids + "' where id=" + id;

			ps = ct.prepareStatement(sql);
			ps.executeUpdate();

			// 更新成功后，还需要更新tb_tags_relate表
			// 删除相关数据
			sql = "delete from tb_tags_relate where columnname = '"
					+ columnName + "' and mainid = " + id;
			ps = ct.prepareStatement(sql);
			ps.executeUpdate();

			arr = update_user_ids.split(",");
			if (arr != null && arr.length > 0) {
				for (int i = 0; i < arr.length; i++) {
					sql = "insert into tb_tags_relate (columnname,relateid,mainid)  values "
							+ "('"
							+ columnName
							+ "',"
							+ arr[i]
							+ ","
							+ id
							+ ")";
					ps = ct.prepareStatement(sql);
					ps.executeUpdate();
				}
			}
		} catch (Exception e) {
			logger.error("批量修改负责人出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 公共查询
	 */
	public List<Map<String, String>> select_my_pass_tableinfo(int status,
			List<Tags> list_tags, String tablename, Map<String, String> hm,
			int pageNow, int pageSize,ElNode department,String order) throws ElException {
		Map<String, String> map = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;

		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		Connection ct2 = null;

		String sqlBegin = " SELECT * FROM ( " + " SELECT A.*, ROWNUM RN "
				+ " FROM ( ";
		String sql = " select t.id,t.status,e.username";
		String sqlcolumn = "  ";
		String sqltablename = " from " + tablename + "  t,eluser e,department d ";
		String sqljoin = " join ("
			+ ((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
					.generateSQLByTree("department", department, true)
			+ ") dep on dep.id=d.id ";
		String sqlwhere = " where t.userid=e.id and e.depid=d.id and t.status= " + status; 
		String sqlorder = " order by id desc ";

		if (order != null) {
			if (!order.equals("")) {
				sqlorder = order;
			}
		}
		
		String sqlEnd = "    ) A " + " WHERE ROWNUM <= ? " + " )WHERE RN >= ? ";

		sqlcolumn += TagsUtil.getSqlColumns(3, list_tags);
		// sqlwhere
		sqlwhere += TagsUtil.getSqlWhere(hm);
		try {
			ct = DBConnection.getConnection();
			ct2 = DBConnection.getConnection();
			ps = ct.prepareStatement(sqlBegin + sql + sqlcolumn + sqltablename + sqljoin 
					+ sqlwhere + sqlorder + sqlEnd);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);

			rs = ps.executeQuery();
			while (rs.next()) {
				map = new HashMap<String, String>();
				
				ct2 = DBConnection.getConnection();
				map = TagsUtil.getOneData(1,list_tags, map, rs, ct2, rs2, ps2);
				DBConnection.closeConnectInfo(ct2, ps2, rs2);
				
				map.put("id", String.valueOf(rs.getInt("id")));
				map.put("status", rs.getString("status"));
				map.put("principal", rs.getString("username"));

				list.add(map);
			}
		} catch (Exception e) {
			logger.error("公共查询出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	/**
	 * 公共查询SIZE
	 */
	public int select_my_pass_tableinfo_count(int status,List<Tags> list_tags,
			Map<String, String> hm, String tablename,ElNode department) throws ElException {
		int count = 0;

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = " select count(*) from " + tablename + " t,eluser e,department d " + 
		" join ("
			+ ((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
					.generateSQLByTree("department", department, true)
			+ ") dep on dep.id=d.id " 
				+ " where t.userid=e.id and e.depid=d.id and t.status= "+status;
		
		String sqlwhere = "";
		try {
			ct = DBConnection.getConnection();

			// sqlwhere
			sqlwhere += TagsUtil.getSqlWhere(hm);

			ps = ct.prepareStatement(sql + sqlwhere);
			rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("查询公共查询SIZE出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}

	/*
	 * 获得单个自定义列表
	 */
	public Tags select_designe_field_by_id(int id) throws ElException {
		Tags tags = new Tags();

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();

			sql = " select t.*,ttm.*,s.name  from tb_designe t left join tb_tags_mark ttm on t.table_name=ttm.tablename and t.column_name=ttm.columnname" +
					" left join selectlevel s on t.selectlevelid=s.id " +
					" where t.id=?  ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {

				tags.setId(rs.getInt("id"));
				tags.setColumn_name(rs.getString("column_name"));
				tags.setColumn_type(rs.getString("column_type"));
				tags.setTable_name(rs.getString("table_name"));
				tags.setName_display(rs.getString("name_display"));

				tags.setAdd_display(rs.getInt("add_display"));
				tags.setUpdate_display(rs.getInt("update_display"));
				tags.setView_display(rs.getInt("view_display"));
				tags.setList_display(rs.getInt("list_display"));
				tags.setMutilsearch_display(rs.getInt("mutilsearch_display"));

				tags.setSn(rs.getInt("sn"));
				tags.setDisplay_type(rs.getString("display_type"));
				tags.setDefault_value(rs.getString("default_value"));
				tags.setCannot_modify(rs.getInt("cannot_modify"));
				tags.setDepartsearch_display(rs.getInt("departsearch_display"));

				tags.setRequired(rs.getInt("required"));
				tags.setSum_display(rs.getInt("sum_display"));

				tags.setJindutiao(rs.getInt("jindutiao"));
				tags.setRelateIsShowComplete(rs.getInt("is_show_complete"));
				tags.setShow_time_jindu(rs.getInt("show_time_jindu"));
				tags.setTime_jindu_ids(rs.getString("time_jindu"));

				tags.setYewu_jindu_ids(rs.getString("yewu_jindu"));
				tags.setYewu_jindu_relate_id(rs
						.getString("yewu_jindu_relate_id"));
				tags.setYewu_jindu_relate_begintime(rs
						.getString("yewu_jindu_relate_begintime"));
				tags.setYewu_jindu_relate_endtime(rs
						.getString("yewu_jindu_relate_endtime"));

				tags.setJisuan_relate_type(rs.getString("jisuan_relate_type"));
				tags.setJisuan_relate_id(rs.getString("jisuan_relate_id"));
				tags.setJisuan_produce_table_name(rs
						.getString("jisuan_produce_table_name"));
				tags.setJisuan_produce_relate_id(rs
						.getString("jisuan_produce_relate_id"));
				tags.setJisuan_result_table_name(rs
						.getString("jisuan_result_table_name"));
				tags.setJisuan_result_relate_id(rs
						.getString("jisuan_result_relate_id"));

				tags.setWanzheng(rs.getString("wanzheng"));
				tags.setIs_calculate(rs.getInt("is_calculate"));

				tags.setBiaojianqiuhe_check(rs.getInt("biaojianqiuhe_check"));
				tags.setBiaojianqiuhe_tablename(rs
						.getString("biaojianqiuhe_tablename"));
				tags.setBiaojianqiuhe_column(rs
						.getString("biaojianqiuhe_column"));

				tags.setFromResultTable(rs.getString("from_result_table"));

				tags.setWritible(rs.getInt("writible"));
				tags.setIsAutoPlay(rs.getInt("isautoplay"));

				tags.setIs_judge(rs.getInt("is_judge"));
				tags.setIs_judge_for_user(rs.getInt("is_judge_for_user"));

				tags.setMark(rs.getString("mark"));
				tags.setWanzheng_mark(rs.getString("wanzheng_mark"));

				tags.setFromtablename_columnname(rs
						.getString("fromtablename_columnname"));

				tags.setShowfinalpass(rs.getInt("showfinalpass"));
				
				tags.setTimeformat(rs.getString("timeformat"));
				tags.setSelectlevelid(rs.getInt("selectlevelid"));
				tags.setJibieshu(rs.getInt("jibieshu"));
				tags.setSelectLevel(new SelectLevel(rs.getInt("selectlevelid"),rs.getString("name")));
				TagsMark tagsMark = new TagsMark();
				tagsMark.setRelates(rs.getString("relates"));
				tagsMark.setRelates_info(rs.getString("relates_info"));
				tags.setTagsMark(tagsMark);
			}
		} catch (Exception e) {
			logger.error("获取自定义列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return tags;
	}

	/*
	 * 获得单个自定义列表
	 */
	public int select_designe_field_id_by_columnName(String columnName)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int result = 0;
		try {
			ct = DBConnection.getConnection();

			sql = " select id from tb_designe where column_name=?  ";
			ps = ct.prepareStatement(sql);
			ps.setString(1, columnName);
			rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getInt("id");
			}
		} catch (Exception e) {
			logger.error("获取自定义列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	/*
	 * 更新自定义字段
	 */
	public void update_designe_field(Tags tags) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;

		String sql = "";
		String sqldefaultvalue = "";
		String sqlwhere = " where id=" + tags.getId();

		if (tags.getDisplay_type().equals("下拉选项")
				|| tags.getDisplay_type().equals("文本")
				|| tags.getDisplay_type().equals("日期")
				|| tags.getDisplay_type().equals("单选")
				|| tags.getDisplay_type().equals("复选")
				|| tags.getDisplay_type().equals("整数")
				|| tags.getDisplay_type().equals("实数")
				|| tags.getDisplay_type().equals("城市")) {
			sqldefaultvalue = " ,default_value='" + tags.getDefault_value()
					+ "' ";
		}

		try {
			ct = DBConnection.getConnection();

			if (tags.getCannot_modify() != 1) {
				sql = " update tb_designe set "
						+ " add_display=?, "
						+ " update_display=?, "
						+ " view_display=?, "
						+ " list_display=?,"
						+ " mutilsearch_display=?,"
						+ " departsearch_display=? ,"
						+ " required=?,name_display=?,sum_display=?,"
						+ "writible=?,is_judge=?,showfinalpass=?,fromtablename_columnname=?,"
						+ "mark=?,is_show_complete=?,wanzheng=?,is_judge_for_user=? ,timeformat=?,wanzheng_mark=?,isautoplay=?";

				ps = ct.prepareStatement(sql + sqldefaultvalue + sqlwhere);

				ps.setInt(1, tags.getAdd_display());
				ps.setInt(2, tags.getUpdate_display());
				ps.setInt(3, tags.getView_display());
				ps.setInt(4, tags.getList_display());
				ps.setInt(5, tags.getMutilsearch_display());
				ps.setInt(6, tags.getDepartsearch_display());
				ps.setInt(7, tags.getRequired());
				ps.setString(8, tags.getName_display());
				ps.setInt(9, tags.getSum_display());
				ps.setInt(10, tags.getWritible());
				ps.setInt(11, tags.getIs_judge());
				ps.setInt(12, tags.getShowfinalpass());
				ps.setString(13, tags.getFromtablename_columnname());
				ps.setString(14, tags.getMark());
				ps.setInt(15, tags.getRelateIsShowComplete());
				ps.setString(16, tags.getWanzheng());
				ps.setInt(17, tags.getIs_judge_for_user());
				ps.setString(18, tags.getTimeformat());
				ps.setString(19, tags.getWanzheng_mark());
				ps.setInt(20, tags.getIsAutoPlay());

			}

			ps.executeUpdate();

			// 求积求和==修改ic_column_qiuji_qiuhe
			if (tags.getQiuji_column_name() != null
					&& !tags.getQiuji_column_name().equals("")) {
				sql = "update ic_column_qiuji_qiuhe set qiuji_column_name = '"
						+ tags.getQiuji_column_name() + "' "
						+ " where table_name = '" + tags.getTable_name()
						+ "' and column_name = '" + tags.getColumn_name() + "'";
				ps = ct.prepareStatement(sql);
				ps.executeUpdate();
			} else if (tags.getQiuhe_column_name() != null
					&& !tags.getQiuhe_column_name().equals("")) {
				sql = "update ic_column_qiuji_qiuhe set qiuhe_column_name = '"
						+ tags.getQiuhe_column_name() + "' "
						+ " where table_name = '" + tags.getTable_name()
						+ "' and column_name = '" + tags.getColumn_name() + "'";
				ps = ct.prepareStatement(sql);
				ps.executeUpdate();
			}
		}

		catch (Exception e) {
			logger.error("自定义字段修改序列出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/*
	 * 无权限判断
	 */
	public List<Map<String, String>> select_my_tableinfo_by_dep_principal_with_judge(
			Tags tags, List<Tags> list_tags, String tablename,
			Map<String, String> hm, Department department, int search_control,
			int principalid, int pageNow, int pageSize)// search_control;0:本人部门一起，1：本人，2：部门
			throws ElException {
		boolean flag_colume = true;
		Map<String, String> map = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;

		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		Connection ct2 = null;

		String sqlBegin = " SELECT * FROM ( " + " SELECT A.*, ROWNUM RN "
				+ " FROM ( ";
		String sql = " select  t.id,t.status ,e.username ,(select username from eluser where id=t.principalid) principalname ,d.name ";
		String sqlcolumn = "  ";
		String sqltablename = " from " + tablename
				+ " t, eluser e   ,department d ";
		String sqlwhere0 = " where  ";
		String sqlwhere_dep = "  (" + " userid in "
				+ "	(select id from eluser where valid=1 "
				+ ") and t.userid=e.id  and  e.depid=d.id" + " ";

		String sqlwhere = "";

		String sqlwhere_dep_ = " ) ";

		String sqlwhereor = " or ";

		String sqlwhere2 = " (t.principalid=e.id  and e.depid=d.id  ";
		String sqlwhere2_ = " ) ";

		String sqlorder = "  order by id desc ";
		String sqlEnd = "    ) A " + " WHERE ROWNUM <= ? " + " )WHERE RN >= ? ";

		// sqlcolumn
		for (int i = 0; i < list_tags.size(); i++) {

			if (list_tags.get(i).getDepartsearch_display() == 1) {

				sqlcolumn += ",";

				if (list_tags.get(i).getColumn_type().equals("date")) {
					sqlcolumn += " to_char("
							+ list_tags.get(i).getColumn_name()
							+ ",'yyyy-mm-dd') "
							+ list_tags.get(i).getColumn_name();
				} else {
					sqlcolumn += " " + list_tags.get(i).getColumn_name() + " ";
				}
			}

		}

		// sqlwhere
		Iterator iterator = hm.entrySet().iterator();
		while (iterator.hasNext()) {
			java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
			// entry.getKey() 返回与此项对应的键
			// entry.getValue() 返回与此项对应的值
			String str[] = ((String) entry.getKey()).split("==");

			if (str[0].equals("number") || str[0].equals("float")) {
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

				sqlwhere += " and t.id in (select mainid from tb_tags_relate where relateid in "
						+ " ( select id from "
						+ str[1]
						+ " where "
						+ str[2]
						+ " like '%" + (String) entry.getValue() + "%')) ";
			}
		}

		if (tags != null) {
			if (tags.getShowfinalpass() == 1) {
				sqlwhere += " and t.status = '通过' ";
			}
		}

		try {
			ct = DBConnection.getConnection();
			ct2 = DBConnection.getConnection();

			ps = ct.prepareStatement(sqlBegin + sql + sqlcolumn + sqltablename
					+ sqlwhere0 + sqlwhere2 + sqlwhere + sqlwhere2_ + sqlorder
					+ sqlEnd);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);

			rs = ps.executeQuery();
			while (rs.next()) {
				map = new HashMap<String, String>();
				for (int i = 0; i < list_tags.size(); i++) {
					if (list_tags.get(i).getDepartsearch_display() == 1) {
						if (list_tags.get(i).getColumn_type().equals("number")) {
							map.put(list_tags.get(i).getColumn_name(), String
									.valueOf(rs.getInt(list_tags.get(i)
											.getColumn_name())));
						} else if (list_tags.get(i).getColumn_type().equals(
								"float")) {
							map.put(list_tags.get(i).getColumn_name(), String
									.valueOf(rs.getFloat(list_tags.get(i)
											.getColumn_name())));
						}
						if (list_tags.get(i).getDisplay_type().equals("相关字段")) {
							// String
							// id=rs.getString(list_tags.get(i).getColumn_name());
							// if(id==null) continue;
							// if(id.equals("")) continue;
							// String str=list_tags.get(i).getDefault_value();
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
							String str = list_tags.get(i).getDefault_value();
							String arr[] = str.split("==");// tb_mm_28==tb_mm_28_171==反反复复==varchar2(500)
							String sql_relate = " select " + arr[1] + " from "
									+ arr[0] + " where id in (" + id + ")";

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
						/*
						 * else // string {
						 * map.put(list_tags.get(i).getColumn_name(), rs
						 * .getString(list_tags.get(i) .getColumn_name())); }
						 */
					}// if
				}// for
				map.put("id", String.valueOf(rs.getInt("id")));
				map.put("status", rs.getString("status"));
				map.put("username", rs.getString("username"));
				map.put("name", rs.getString("name"));
				map.put("principalname", rs.getString("principalname"));

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

	public List<Map<String, String>> select_my_tableinfo_by_dep_principal(
			Tags tags, List<Tags> list_tags, String tablename,
			Map<String, String> hm, Department department, int search_control,
			int principalid, int pageNow, int pageSize)// search_control;0:本人部门一起，1：本人，2：部门
			throws ElException {
		Map<String, String> map = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;

		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		Connection ct2 = null;

		String sqlBegin = " SELECT * FROM ( " + " SELECT A.*, ROWNUM RN "
				+ " FROM ( ";
		String sql = " select  t.id,t.status ,e.username ,(select username from eluser where id=t.principalid) principalname ,d.name ";
		String sqlcolumn = "  ";
		String sqltablename = " from " + tablename
				+ " t, eluser e   ,department d ";
		String sqljoin = " join ("
				+ ((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
						.generateSQLByTree("department", department, true)
				+ ") dep on dep.id=d.id ";
		String sqlwhere0 = "";
		if (search_control == 0) {
			sqlwhere0 = " where 1=1 ";
		} else if (search_control == 1) {
			sqlwhere0 = " where (userid=  " + principalid + " ";
		} else if (search_control == 2) {

		}
		String sqlwhere_dep = " and (" + " userid in "
				+ "	(select id from eluser where valid=1 "
				// + " and depid in "
				// + " (select id from department where lid >= ? and rid <= ?) "
				+ ") and t.userid=e.id  and  e.depid=d.id" + " ";

		String sqlwhere0_end = " ) ";
		String sqlwhere_dep_ = " ) ";

		String sqlwhere = " and t.userid=e.id and e.depid=d.id ";

		String sql_relate_charge = "";

		// String sqlwhereor = " and ";
		//
		// String sqlwhere2 = " (t.principalid=e.id and t.principalid="
		// + principalid + " and e.depid=d.id ";
		// String sqlwhere2_ = " ) ";

		String sqlorder = "  order by id desc ";
		String sqlEnd = "    ) A " + " WHERE ROWNUM <= ? " + " )WHERE RN >= ? ";

		// sqlcolumn
		for (int i = 0; i < list_tags.size(); i++) {

			if (list_tags.get(i).getDepartsearch_display() == 1) {
				// if (flag_colume)
				// {
				// flag_colume = false;
				// }
				// else
				// {
				// sqlcolumn += ",";
				// }

				sqlcolumn += ",";
				// if(list_tags.get(i).getDisplay_type().equals("相关字段")){
				// if(list_tags.get(i).getShowfinalpass() == 1){
				// sqlwhere += " and t.status = '通过'";
				// }
				// }
				// if (list_tags.get(i).getDisplay_type().equals("相关负责人")) {
				// sqlwhere2 += " or (t.id in (select mainid from tb_tags_relate
				// where columnname = "
				// + " '"
				// + list_tags.get(i).getColumn_name()
				// + "' and relateid = " + principalid + "))";
				// }
				if (list_tags.get(i).getColumn_type().equals("date")) {
					sqlcolumn += " to_char("
							+ list_tags.get(i).getColumn_name()
							+ ",'yyyy-mm-dd') "
							+ list_tags.get(i).getColumn_name();
				} else {
					sqlcolumn += " " + list_tags.get(i).getColumn_name() + " ";
				}
				if (list_tags.get(i).getDisplay_type().equals("相关负责人")) {
					sql_relate_charge = " or t.id in (select mainid from tb_tags_relate where columnname = '"
							+ list_tags.get(i).getColumn_name()
							+ "' and relateid=" + 1630 + ") ";
				}

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

				// sqlwhere += " and "+str[3]+" in ( select id from "+str[1]+"
				// where "+str[2]+" like '"+(String) entry.getValue()+"%' )";
				sqlwhere += " and t.id in (select mainid from tb_tags_relate where relateid in "
						+ " ( select id from "
						+ str[1]
						+ " where "
						+ str[2]
						+ " like '%" + (String) entry.getValue() + "%')) ";
			}
		}

		if (tags != null) {
			if (tags.getShowfinalpass() == 1) {
				sqlwhere += " and t.status = '通过' ";
			}
		}

		try {
			ct = DBConnection.getConnection();
			ct2 = DBConnection.getConnection();

			if (search_control == 0)// 本人部门一起
			{
				ps = ct.prepareStatement(sqlBegin + sql + sqlcolumn
						+ sqltablename + sqljoin + sqlwhere0 + sqlwhere_dep
						+ sqlwhere_dep_ + sqlwhere + sqlorder + sqlEnd);
				// ps.setInt(1, department.getLid());
				// ps.setInt(2, department.getRid());
				ps.setInt(1, pageNow);
				ps.setInt(2, pageSize);
			} else if (search_control == 1)// 1：本人，
			{
				ps = ct.prepareStatement(sqlBegin + sql + sqlcolumn
						+ sqltablename + sqlwhere0 + sql_relate_charge
						+ sqlwhere0_end + sqlwhere + sqlorder + sqlEnd);
				ps.setInt(1, pageNow);
				ps.setInt(2, pageSize);
			} else if (search_control == 2)// 2：部门
			{
				ps = ct.prepareStatement(sqlBegin + sql + sqlcolumn
						+ sqltablename + sqlwhere0 + sqlwhere_dep + sqlwhere
						+ sqlwhere_dep_ + sqlorder + sqlEnd);
				ps.setInt(1, pageNow);
				ps.setInt(2, pageSize);
			}

			rs = ps.executeQuery();
			while (rs.next()) {
				map = new HashMap<String, String>();
				for (int i = 0; i < list_tags.size(); i++) {
					if (list_tags.get(i).getDepartsearch_display() == 1) {
						if (list_tags.get(i).getColumn_type().equals("number")) {
							map.put(list_tags.get(i).getColumn_name(), String
									.valueOf(rs.getInt(list_tags.get(i)
											.getColumn_name())));
						} else if (list_tags.get(i).getColumn_type().equals(
								"float")) {
							map.put(list_tags.get(i).getColumn_name(), String
									.valueOf(rs.getFloat(list_tags.get(i)
											.getColumn_name())));
						} else if (list_tags.get(i).getDisplay_type().equals(
								"相关字段")) {
							// String
							// id=rs.getString(list_tags.get(i).getColumn_name());
							// if(id==null) continue;
							// if(id.equals("")) continue;
							// String str=list_tags.get(i).getDefault_value();
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
							String str = list_tags.get(i).getDefault_value();
							String arr[] = str.split("==");// tb_mm_28==tb_mm_28_171==反反复复==varchar2(500)
							String sql_relate = " select " + arr[1] + " from "
									+ arr[0] + " where id in (" + id + ")";

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

						} else if (list_tags.get(i).getDisplay_type().equals(
								"相关负责人")) {
							continue;
						} else {
							// string
							map.put(list_tags.get(i).getColumn_name(), rs
									.getString(list_tags.get(i)
											.getColumn_name()));
						}
						/*
						 * else // string {
						 * map.put(list_tags.get(i).getColumn_name(), rs
						 * .getString(list_tags.get(i) .getColumn_name())); }
						 */
					}// if
				}// for
				map.put("id", String.valueOf(rs.getInt("id")));
				map.put("status", rs.getString("status"));
				map.put("username", rs.getString("username"));
				map.put("name", rs.getString("name"));
				map.put("principalname", rs.getString("principalname"));

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

	public int select_my_tableinfo_by_dep_principal_count(Tags tags,
			List<Tags> list_tags, Map<String, String> hm, String tablename,
			Department department, int search_control, int principalid)
			throws ElException {
		int count = 0;

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;

		// String sqltablename = " from " + tablename
		// + " t, eluser e ,department d ";
		// String sqljoin = " join (" +((ElNodeSQL)
		// SpringContextUtil.getBean("elnodesql")).generateSQLByTree("department",
		// department, true)+ ") dep on dep.id=d.id ";
		// String sqlwhere0 = " where ";
		// String sqlwhere_dep = " (" + " userid in "
		// + " (select id from eluser where valid=1 "
		// // + " and depid in "
		// // + " (select id from department where lid >= ? and rid <= ?) "
		// + ") and t.userid=e.id and e.depid=d.id" + " ";

		String sql = " select count(*) from "
				+ tablename
				+ " t,eluser e,department d join (  "
				+ ((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
						.generateSQLByTree("department", department, true)
				+ ") dep on dep.id=d.id  " + " where 1=1  ";
		String sqldep = " and (userid in " + " ("
				+ "   select id from eluser where valid=1 "
				// "and depid in "
				// + " ( select id from department where lid >= ? and rid <= ?)"
				+ " )  ";
		String sqldep_ = " ) ";
		// String sqlor = " and ";
		// String sqlwhere2 = " (principalid=" + principalid;
		// String sqlwhere2_ = " ) ";
		String sqlwhere = " and t.userid=e.id  and  e.depid=d.id ";

		String sql_my_charge = "";
		if (search_control == 1) {
			sql_my_charge = " and (userid = " + principalid + " ";
		}

		for (int i = 0; i < list_tags.size(); i++) {

			if (list_tags.get(i).getDepartsearch_display() == 1) {

				if (list_tags.get(i).getDisplay_type().equals("相关负责人")) {
					sql_my_charge += " or t.id in (select mainid from tb_tags_relate where columnname = "
							+ " '"
							+ list_tags.get(i).getColumn_name()
							+ "' and relateid = " + principalid + ")";
				}
			}

		}
		sql_my_charge += " ) ";
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
					sqlwhere += " and id in (select mainid from tb_tags_relate where relateid in "
							+ " ( select id from "
							+ str[1]
							+ " where "
							+ str[2]
							+ " like '%"
							+ (String) entry.getValue()
							+ "%')) ";
				}
			}

			if (tags != null) {
				if (tags.getShowfinalpass() == 1) {
					sqlwhere += " and t.status = '通过' ";
				}
			}

			if (search_control == 0)// 本人和部门
			{
				ps = ct.prepareStatement(sql + sqldep + sqldep_ + sqlwhere);
			} else if (search_control == 1)// 本人
			{
				ps = ct.prepareStatement(sql + sqlwhere + sql_my_charge);
			} else if (search_control == 2)// 部门
			{
				ps = ct.prepareStatement(sql + sqldep + sqlwhere + sqldep_);
			}

			// ps = ct.prepareStatement(sql + sqlwhere);
			// if (search_control != 1) {
			// ps.setInt(1, department.getLid());
			// ps.setInt(2, department.getRid());
			// }
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

	public int select_my_tableinfo_by_dep_principal_count_with_judge(Tags tags,
			List<Tags> list_tags, Map<String, String> hm, String tablename,
			Department department, int search_control, int principalid)
			throws ElException {
		int count = 0;

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;

		String sql = " select count(*) from " + tablename
				+ " t,eluser e,department d " + " where  ";
		String sqldep = " userid in " + " ("
				+ "   select id from eluser where valid=1 "
				// "and depid in "
				// + " ( select id from department where lid >= ? and rid <= ?)"
				+ " ) and t.userid=e.id  and  e.depid=d.id ";
		// String sqldep_ = " ";
		// String sqlor = " or ";
		// String sqlwhere2 = " (principalid=" + principalid;
		// String sqlwhere2_ = " ) ";
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
					sqlwhere += " and id in (select mainid from tb_tags_relate where relateid in "
							+ " ( select id from "
							+ str[1]
							+ " where "
							+ str[2]
							+ " like '%"
							+ (String) entry.getValue()
							+ "%')) ";
				}
			}

			if (tags != null) {
				if (tags.getShowfinalpass() == 1) {
					sqlwhere += " and t.status = '通过' ";
				}
			}

			ps = ct.prepareStatement(sql + sqldep + sqlwhere);

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

	/*
	 * 获取sum
	 */
	public float select_sum(String userid, List<Tags> list_tags,
			String tablename, String columnname, Map<String, String> hm,
			Department department) throws ElException {
		float sum = 0;

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;

		String sql = " select  sum(" + columnname + ") ";
		String sqlcolumn = "  ";
		String sqltablename = " from " + tablename
				+ " t, eluser e   ,department d ";

		String sqlwhere0 = "";
		if (userid != null && !userid.equals("")) {
			sqlwhere0 = " where userid = " + Integer.parseInt(userid);
		} else {
			sqlwhere0 = "where userid in (select id from eluser where valid=1 "
					+ " and depid in "
					+ " (select id from department where lid >= ? and rid <= ?)   ) ";
		}

		// String sqlwhere0 = " where userid in ";
		// String sqlwhere_dep = " (select id from eluser where valid=1 "
		// + " and depid in "
		// + " (select id from department where lid >= ? and rid <= ?) )";

		String sqlwhere = " and t.userid=e.id  and  e.depid=d.id ";

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

				// sqlwhere += " and "+str[3]+" in ( select id from "+str[1]+"
				// where "+str[2]+" like '"+(String) entry.getValue()+"%' )";
				sqlwhere += " and t.id in (select mainid from tb_tags_relate where relateid in "
						+ " ( select id from "
						+ str[1]
						+ " where "
						+ str[2]
						+ " like '%" + (String) entry.getValue() + "%')) ";
			}
		}

		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql + sqlcolumn + sqltablename + sqlwhere0
					+ sqlwhere);

			if (userid == null) {
				ps.setInt(1, department.getLid());
				ps.setInt(2, department.getRid());
			}

			rs = ps.executeQuery();
			if (rs.next()) {
				sum = rs.getFloat(1);
			}

		} catch (Exception e) {
			logger.error("客户添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return sum;
	}

	/**
	 * 我添加的
	 */
	public List<Map<String, String>> select_my_tableinfo_by_userid_order(
			String sqlAppend, int type, List<Tags> list_tags, String tablename,
			Map<String, String> hm, int userid, String order, int pageNow,
			int pageSize) throws ElException {
		Map<String, String> map = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;

		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		Connection ct2 = null;

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

		sqlcolumn += TagsUtil.getSqlColumns(1, list_tags);

		
		// sqlwhere
		sqlwhere += TagsUtil.getSqlWhere(hm);

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
				
				ct2 = DBConnection.getConnection();
				map = TagsUtil.getOneData(1,list_tags, map, rs, ct2, rs2, ps2);
				DBConnection.closeConnectInfo(ct2, ps2, rs2);
				
				map.put("id", String.valueOf(rs.getInt("id")));
				map.put("status", rs.getString("status"));

				map.put("username", rs.getString("username"));
				map.put("name", rs.getString("department"));

				list.add(map);
			}
		} catch (Exception e) {
			logger.error("查询我添加的出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}
	/**
	 * 相关显示
	 */
	public List<Map<String, String>> select_my_tableinfo_by_relate(
			String sqlAppend, int type, List<Tags> list_tags, String tablename,
			Map<String, String> hm, int userid, String order, int pageNow,
			int pageSize) throws ElException {
		Map<String, String> map = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;

		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		Connection ct2 = null;

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

		sqlcolumn += TagsUtil.getSqlColumns(-1, list_tags);

		
		// sqlwhere
		sqlwhere += TagsUtil.getSqlWhere(hm);

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
				
				ct2 = DBConnection.getConnection();
				map = TagsUtil.getOneDataRelate(1,list_tags, map, rs, ct2, rs2, ps2);
				DBConnection.closeConnectInfo(ct2, ps2, rs2);
				
				map.put("id", String.valueOf(rs.getInt("id")));
				map.put("status", rs.getString("status"));

				map.put("username", rs.getString("username"));
				map.put("name", rs.getString("department"));

				list.add(map);
			}
		} catch (Exception e) {
			logger.error("查询我添加的出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	/**
	 * 我负责的
	 */
	public List<Map<String, String>> select_my_tableinfo_by_userid_order_1(
			String sqlAppend, int type, List<Tags> list_tags, String tablename,
			Map<String, String> hm, int userid, String order, int pageNow,
			int pageSize) throws ElException {
		Map<String, String> map = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;

		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		Connection ct2 = null;

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

		String sql_myCharge = "";
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getDisplay_type().equals("相关负责人")) {
				sql_myCharge = " and t.id in (select mainid from tb_tags_relate where columnname = '"
						+ list_tags.get(i).getColumn_name()
						+ "' and relateid="
						+ userid + ") ";
			}
		}
		sqlcolumn += TagsUtil.getSqlColumns(1, list_tags);

		// sqlwhere
		sqlwhere += TagsUtil.getSqlWhere(hm);
		
		try {
			ct = DBConnection.getConnection();
			ct2 = DBConnection.getConnection();
			ps = ct.prepareStatement(sqlBegin + sql + sqlcolumn + sqltablename
					+ sqlwhere + sql_myCharge + sqlorder + sqlEnd);
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
				
				ct2 = DBConnection.getConnection();
				map = TagsUtil.getOneData(1,list_tags, map, rs, ct2, rs2, ps2);
				DBConnection.closeConnectInfo(ct2, ps2, rs2);
				
				map.put("id", String.valueOf(rs.getInt("id")));
				map.put("status", rs.getString("status"));

				map.put("username", rs.getString("username"));
				map.put("name", rs.getString("department"));

				list.add(map);
			}// while
		} catch (Exception e) {
			logger.error("查询我负责的出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	/*
	 * 部门查询，通过左右id查询
	 */
	public List<Map<String, String>> select_my_tableinfo_by_dep(
			List<Tags> list_tags, String tablename, Map<String, String> hm,
			Department department, String order) throws ElException {
		Map<String, String> map = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;

		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		Connection ct2 = null;

		String sql = " select  t.id,t.status ,e.username ,(select username from eluser where id=t.principalid) principalname ,d.name ";
		String sqlcolumn = "  ";
		String sqltablename = " from " + tablename
				+ " t, eluser e   ,department d ";
		String sqlwhere0 = " where userid in ";
		String sqlwhere_dep = " (select id from eluser where valid=1 "
				+ " and depid in "
				+ " (select id from department where lid >= ? and rid <= ?)   )";

		String sqlwhere = " and t.userid=e.id  and  e.depid=d.id ";
		String sqlorder = "  order by id desc ";

		if (order != null) {
			if (!order.equals("")) {
				sqlorder = order;
			}
		}

		// sqlcolumn
		for (int i = 0; i < list_tags.size(); i++) {

			// if (list_tags.get(i).getDepartsearch_display() == 1)
			if (!list_tags.get(i).getDisplay_type().equals("图片")
					&& !list_tags.get(i).getDisplay_type().equals("富文本")
					&& !list_tags.get(i).getDisplay_type().equals("附件上传")) {

				sqlcolumn += ",";

				if (list_tags.get(i).getColumn_type().equals("date")) {
					sqlcolumn += " to_char("
							+ list_tags.get(i).getColumn_name()
							+ ",'yyyy-mm-dd') "
							+ list_tags.get(i).getColumn_name();
				} else {
					sqlcolumn += " " + list_tags.get(i).getColumn_name() + " ";
				}
			}

		}

		// sqlwhere
		Iterator iterator = hm.entrySet().iterator();
		while (iterator.hasNext()) {
			java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
			// entry.getKey() 返回与此项对应的键
			// entry.getValue() 返回与此项对应的值
			String str[] = ((String) entry.getKey()).split("==");

			if (str[0].equals("number") || str[0].equals("float")) {
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

				sqlwhere += " and t.id in (select mainid from tb_tags_relate where relateid in "
						+ " ( select id from "
						+ str[1]
						+ " where "
						+ str[2]
						+ " like '%" + (String) entry.getValue() + "%')) ";
			}
		}

		try {
			ct = DBConnection.getConnection();
			ct2 = DBConnection.getConnection();
			ps = ct.prepareStatement(sql + sqlcolumn + sqltablename + sqlwhere0
					+ sqlwhere_dep + sqlwhere + sqlorder);
			ps.setInt(1, department.getLid());
			ps.setInt(2, department.getRid());

			rs = ps.executeQuery();
			while (rs.next()) {
				map = new HashMap<String, String>();
				for (int i = 0; i < list_tags.size(); i++) {
					if (!list_tags.get(i).getDisplay_type().equals("图片")
							&& !list_tags.get(i).getDisplay_type()
									.equals("富文本")
							&& !list_tags.get(i).getDisplay_type().equals(
									"附件上传")) {
//						if (list_tags.get(i).getColumn_type().equals("number")) {
//							map.put(list_tags.get(i).getColumn_name(), String
//									.valueOf(rs.getFloat(list_tags.get(i)
//											.getColumn_name())));
//						} else if (list_tags.get(i).getColumn_type().equals(
//								"float")) {
//							map.put(list_tags.get(i).getColumn_name(), String
//									.valueOf(rs.getFloat(list_tags.get(i)
//											.getColumn_name())));
//						}
						if (list_tags.get(i).getDisplay_type().equals("相关字段")) {

							String returnvalue = "";
							String id = rs.getString(list_tags.get(i)
									.getColumn_name());// 获取相关字段值，序列id值
							if (id == null)
								continue;
							if (id.equals(""))
								continue;
							// String idvalues[]=id.split(",");
							String str = list_tags.get(i).getDefault_value();
							String arr[] = str.split("==");// tb_mm_28==tb_mm_28_171==反反复复==varchar2(500)
							String sql_relate = " select " + arr[1] + " from "
									+ arr[0] + " where id in (" + id + ")";

							ps2 = ct2.prepareStatement(sql_relate);
							rs2 = ps2.executeQuery();
							while (rs2.next()) {
								// list_tags.get(i).setValue(rs2.getString(1));
								returnvalue += rs2.getString(1);
								// if(!rs2.isLast())
							}

							map.put(list_tags.get(i).getColumn_name(),
									returnvalue);

						} else if (list_tags.get(i).getDisplay_type().equals(
								"相关负责人")) {
							String returnvalue = "";
							String ids = rs.getString(list_tags.get(i)
									.getColumn_name());
							if (ids == null)
								continue;
							if (ids.equals(""))
								continue;
							String sql_relate = " select realname from eluser where id in ("
									+ ids + ")";

							ps2 = ct2.prepareStatement(sql_relate);
							rs2 = ps2.executeQuery();
							while (rs2.next()) {
								// list_tags.get(i).setValue(rs2.getString(1));
								returnvalue += rs2.getString(1);
								// if(!rs2.isLast())
								returnvalue += ",";
							}

							map.put(list_tags.get(i).getColumn_name(),
									returnvalue);
						} else {
							// string
							map.put(list_tags.get(i).getColumn_name(), rs
									.getString(list_tags.get(i)
											.getColumn_name()));
						}
					}// if
				}// for
				map.put("id", String.valueOf(rs.getInt("id")));
				map.put("status", rs.getString("status"));
				map.put("username", rs.getString("username"));
				map.put("name", rs.getString("name"));
				map.put("principalname", rs.getString("principalname"));

				list.add(map);
			}// while
		} catch (Exception e) {
			logger.error("客户添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}
	
	public List<Map<String, String>> selectAll(
			List<Tags> list_tags, String tablename) throws ElException {
		Map<String, String> map = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;

		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		Connection ct2 = null;

		String sql = " select  t.id,t.status ,e.username ,(select username from eluser where id=t.principalid) principalname ,d.name ";
		String sqlcolumn = "  ";
		String sqltablename = " from " + tablename
				+ " t, eluser e   ,department d ";
		String sqlwhere0 = " where userid in ";
		String sqlwhere_dep = " (select id from eluser where valid=1 "
				+ " and depid in "
				+ " (select id from department )   )";

		String sqlwhere = " and t.userid=e.id  and  e.depid=d.id ";
		String sqlorder = "  order by id desc ";


		// sqlcolumn
		for (int i = 0; i < list_tags.size(); i++) {

			// if (list_tags.get(i).getDepartsearch_display() == 1)
			if (!list_tags.get(i).getDisplay_type().equals("图片")
					&& !list_tags.get(i).getDisplay_type().equals("富文本")
					&& !list_tags.get(i).getDisplay_type().equals("附件上传")) {

				sqlcolumn += ",";

				if (list_tags.get(i).getColumn_type().equals("date")) {
					sqlcolumn += " to_char("
							+ list_tags.get(i).getColumn_name()
							+ ",'yyyy-mm-dd hh24:mi:ss') "
							+ list_tags.get(i).getColumn_name();
				} else {
					sqlcolumn += " " + list_tags.get(i).getColumn_name() + " ";
				}
			}

		}


		try {
			ct = DBConnection.getConnection();
			ct2 = DBConnection.getConnection();
			ps = ct.prepareStatement(sql + sqlcolumn + sqltablename + sqlwhere0
					+ sqlwhere_dep + sqlwhere + sqlorder);

			rs = ps.executeQuery();
			while (rs.next()) {
				map = new HashMap<String, String>();
				for (int i = 0; i < list_tags.size(); i++) {
					if (!list_tags.get(i).getDisplay_type().equals("图片")
							&& !list_tags.get(i).getDisplay_type()
									.equals("富文本")
							&& !list_tags.get(i).getDisplay_type().equals(
									"附件上传")) {
//						if (list_tags.get(i).getDisplay_type().equals("相关字段")) {
//
//							String returnvalue = "";
//							String id = rs.getString(list_tags.get(i)
//									.getColumn_name());// 获取相关字段值，序列id值
//							if (id == null)
//								continue;
//							if (id.equals(""))
//								continue;
//							// String idvalues[]=id.split(",");
//							String str = list_tags.get(i).getDefault_value();
//							String arr[] = str.split("==");// tb_mm_28==tb_mm_28_171==反反复复==varchar2(500)
//							String sql_relate = " select " + arr[1] + " from "
//									+ arr[0] + " where id in (" + id + ")";
//
//							ps2 = ct2.prepareStatement(sql_relate);
//							rs2 = ps2.executeQuery();
//							while (rs2.next()) {
//								// list_tags.get(i).setValue(rs2.getString(1));
//								returnvalue += rs2.getString(1);
//								// if(!rs2.isLast())
//							}
//
//							map.put(list_tags.get(i).getColumn_name(),
//									returnvalue);
//
//						} else if (list_tags.get(i).getDisplay_type().equals(
//								"相关负责人")) {
//							String returnvalue = "";
//							String ids = rs.getString(list_tags.get(i)
//									.getColumn_name());
//							if (ids == null)
//								continue;
//							if (ids.equals(""))
//								continue;
//							String sql_relate = " select realname from eluser where id in ("
//									+ ids + ")";
//
//							ps2 = ct2.prepareStatement(sql_relate);
//							rs2 = ps2.executeQuery();
//							while (rs2.next()) {
//								// list_tags.get(i).setValue(rs2.getString(1));
//								returnvalue += rs2.getString(1);
//								// if(!rs2.isLast())
//								returnvalue += ",";
//							}
//
//							map.put(list_tags.get(i).getColumn_name(),
//									returnvalue);	
//						} else {
//							// string
//							map.put(list_tags.get(i).getColumn_name(), rs
//									.getString(list_tags.get(i)
//											.getColumn_name()));
//						}
						map.put(list_tags.get(i).getColumn_name(), rs
								.getString(list_tags.get(i)
										.getColumn_name()));
					}// if
				}// for
				map.put("id", String.valueOf(rs.getInt("id")));
				map.put("status", rs.getString("status"));
				map.put("username", rs.getString("username"));
				map.put("name", rs.getString("name"));
				map.put("principalname", rs.getString("principalname"));

				list.add(map);
			}// while
		} catch (Exception e) {
			logger.error("客户添加出错！", e);
			//throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	public String get_eluser_realname_by_id(int id) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select realname from eluser where id=" + id;

			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs.next())
				return rs.getString(1);
		} catch (Exception e) {
			logger.error("客户添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return "";
	}

	public double getQiuheResult(String tablename, String columnName)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		double result = 0.0;
		try {
			ct = DBConnection.getConnection();
			sql = " select sum(" + columnName + ") from " + tablename;

			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs.next())
				return rs.getDouble(1);
		} catch (Exception e) {
			logger.error("客户添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public void insert_tb_calculate(Tags tags,
			String relate_columnname_calculate,
			String relate_tablename_calculate) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();

			sql = "insert into tb_calculate (tablename,columnname,relate_tablename_calculate,relate_columnname_calculate) "
					+ "values (?,?,?,?)";

			ps = ct.prepareStatement(sql);
			ps.setString(1, tags.getTable_name());
			ps.setString(2, tags.getColumn_name());
			ps.setString(3, relate_tablename_calculate);
			ps.setString(4, relate_columnname_calculate);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("字段有计算的添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public List<Tb_calculate> getTb_calculateByTableNameAndColumnName(
			String tablename, String columnName) throws ElException {
		List<Tb_calculate> list = new ArrayList<Tb_calculate>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();

			sql = "select * from tb_calculate where relate_tablename_calculate=? and relate_columnname_calculate = ? ";
			ps = ct.prepareStatement(sql);
			ps.setString(1, tablename);
			ps.setString(2, columnName);
			rs = ps.executeQuery();
			while (rs.next()) {
				Tb_calculate t = new Tb_calculate();
				t.setColumnName(rs.getString("columnname"));
				t.setTableName(rs.getString("tablename"));
				t.setRelate_columnName_calculate(rs
						.getString("relate_columnname_calculate"));
				t.setRelate_tableName_calculate(rs
						.getString("relate_tablename_calculate"));
				list.add(t);
			}
		} catch (Exception e) {
			logger.error("获取自定义列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	public double calculate(String ids) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		double result = 0.0;
		double result1 = 0.0;
		double result2 = 0.0;
		String[] array = ids.split(";");
		String[] ids_array = new String[array.length];
		String[] columns_array = new String[array.length];
		String[] tables_array = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			ids_array[i] = array[i].split(":")[0];
			tables_array[i] = array[i].split(":")[1];
			columns_array[i] = array[i].split(":")[2];
		}
		try {
			ct = DBConnection.getConnection();
			sql = " select " + columns_array[0] + " from " + tables_array[0]
					+ " where id= " + Integer.parseInt(ids_array[0]) + " ";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
				result1 = rs.getDouble(1);

			ct = DBConnection.getConnection();
			sql = " select " + columns_array[1] + " from " + tables_array[1]
					+ " where id= " + Integer.parseInt(ids_array[1]) + " ";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
				result2 = rs.getDouble(1);

			result = Math.abs((result1 - result2));
		} catch (Exception e) {
			logger.error("客户添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public String getRelateIds(String tableName, String columnName, int id)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String result = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select (" + columnName + ") from " + tableName
					+ " where id = ?";

			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next())
				return rs.getString(1);
		} catch (Exception e) {
			logger.error("查询出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public int checkIfHasTwoDateField(String tablename) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int result = 0;
		try {
			ct = DBConnection.getConnection();
			sql = " select count(1) from tb_designe where table_name = '"
					+ tablename + "' and column_type = 'date'";

			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			logger.error("查询出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public String getColumn_name_by_id(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String result = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select column_name from tb_designe where id = ?";

			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next())
				return rs.getString(1);
		} catch (Exception e) {
			logger.error("根据tb_designe表查询出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public String getNameDisplayById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String result = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select name_display from tb_designe where id = ?";

			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next())
				return rs.getString(1);
		} catch (Exception e) {
			logger.error("根据tb_designe表查询出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public String getIdByColumnName(String tablename, String columnName)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String result = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select id from tb_designe where table_name = ? and column_name = ?";

			ps = ct.prepareStatement(sql);
			ps.setString(1, tablename);
			ps.setString(2, columnName);
			rs = ps.executeQuery();

			if (rs.next())
				return rs.getString(1);
		} catch (Exception e) {
			logger.error("根据tb_designe表查询出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public String getModuleNameByTablename(String tablename) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String result = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select modulename from tb_module_manage where tablename = ?";

			ps = ct.prepareStatement(sql);
			ps.setString(1, tablename);
			rs = ps.executeQuery();

			if (rs.next())
				return rs.getString(1);
		} catch (Exception e) {
			logger.error("根据tb_module_manage表查询出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public String getModuleShujuNameByTablename(String tablename)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String result = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select shujuname from tb_module_manage where tablename = ?";

			ps = ct.prepareStatement(sql);
			ps.setString(1, tablename);
			rs = ps.executeQuery();

			if (rs.next())
				return rs.getString(1);
		} catch (Exception e) {
			logger.error("根据tb_module_manage表查询出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public String getColumn_name_by_tableName(String type, String tableName)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String result = "";
		try {
			ct = DBConnection.getConnection();
			if (type.equals("time")) {

				sql = " select column_name from tb_designe   where table_name = ? and jindutiao = 1 and time_jindu is not null ";
			} else if (type.equals("yewu")) {
				sql = " select column_name from tb_designe   where table_name = ? and jindutiao = 1 and yewu_jindu is not null ";
			}

			ps = ct.prepareStatement(sql);
			ps.setString(1, tableName);
			rs = ps.executeQuery();

			if (rs.next())
				return rs.getString(1);
		} catch (Exception e) {
			logger.error("根据tb_designe表查询出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public String IfHasYewuJindu_column(int id, String tablename)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String value = "";
		try {
			ct = DBConnection.getConnection();
			if (tablename != null && !tablename.equals(""))
				sql = " select yewu_jindu,yewu_jindu_relate_id,yewu_jindu_relate_begintime,yewu_jindu_relate_endtime from tb_designe where table_name = ? and yewu_jindu is not null and yewu_jindu_relate_id is not null";
			else if (id != 0)
				sql = " select yewu_jindu,yewu_jindu_relate_id,yewu_jindu_relate_begintime,yewu_jindu_relate_endtime from tb_designe where id = ? and yewu_jindu is not null and yewu_jindu_relate_id is not null";
			ps = ct.prepareStatement(sql);

			if (tablename != null && !tablename.equals(""))
				ps.setString(1, tablename);
			else if (id != 0)
				ps.setInt(1, id);
			rs = ps.executeQuery();

			while (rs.next()){
				value +=	rs.getString(1) + "," + rs.getString(2) + ","
						+ rs.getString(3) + "," + rs.getString(4) + "=";
			}
			if(value!=null&&!value.equals("")&&
					String.valueOf(value.charAt(value.length()-1)).equals("=")){
				value = value.substring(0,value.lastIndexOf("="));
			}
				 
		} catch (Exception e) {
			logger.error("查找业务进度出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return value;
	}

	public String IfHasTimeJindu_column(String tablename) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String column = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select column_name,time_jindu from tb_designe where table_name = ? and time_jindu is not null ";

			ps = ct.prepareStatement(sql);
			ps.setString(1, tablename);
			rs = ps.executeQuery();

			if (rs.next())
				column = rs.getString(1) + "," + rs.getString(2);
			if (!column.equals("")) {

			}
		} catch (Exception e) {
			logger.error("查找时间进度出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return column;
	}

	public void accessById(Tags tags, int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "update " + tags.getTable_name()
					+ " set status = ? where id = ?";
			ps = ct.prepareStatement(sql);
			ps.setString(1, tags.getStatus());
			ps.setInt(2, id);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("根据id通过或者不通过数据出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public void updateTimeJindu(String table, String columns)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String[] array = columns.split(",");
		String sql = "";
		List<Tags> tags_list = new ArrayList<Tags>();
		try {
			ct = DBConnection.getConnection();
			sql = "select id, " + array[0] + "," + array[1] + "," + array[2]
					+ " from " + table;

			ps = ct.prepareStatement(sql);
			// ps.setString(1, array[0]);
			// ps.setString(2, array[1]);
			// ps.setString(3, array[2]);
			// ps.setString(4, table);
			rs = ps.executeQuery();

			Tags tags;
			while (rs.next()) {
				tags = new Tags();
				tags.setId(rs.getInt("id"));
				if (rs.getString(array[1]) != null
						&& !rs.getString(array[1]).equals("")
						&& rs.getString(array[2]) != null
						&& !rs.getString(array[2]).equals("")) {

					sql = "update " + table + " set " + array[0]
							+ " = ? where id = ? ";
					ps = ct.prepareStatement(sql);

					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyyy-MM-dd hh:mm:ss");
					Date toDate1 = dateFormat.parse(rs.getString(array[1]));
					Date temp = toDate1;
					Date toDate2 = dateFormat.parse(rs.getString(array[2]));
					Date nowDate = new Date();

					// 获取比较小的时间
					if (toDate1.getTime() > toDate2.getTime()) {
						toDate1 = toDate2;
						toDate2 = temp;
					}

					double value;
					if (nowDate.getTime() > toDate2.getTime()) {
						value = 100;
					} else if (nowDate.getTime() < toDate1.getTime()) {
						value = 0;
					} else {
						double cha = nowDate.getTime() - toDate1.getTime();
						double zong_cha = toDate2.getTime() - toDate1.getTime();
						value = cha / zong_cha * 100;
					}

					ps.setDouble(1, value);
					ps.setInt(2, tags.getId());
					ps.executeUpdate();
				}
			}

		} catch (Exception e) {
			logger.error("更新时间进度字段出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public String getDisplay_type_by_columnName(String columnName)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String result = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select display_type from tb_designe where column_name = ?";

			ps = ct.prepareStatement(sql);
			ps.setString(1, columnName);
			rs = ps.executeQuery();

			if (rs.next())
				result = rs.getString(1);
		} catch (Exception e) {
			logger.error("根据列名查询字段的默认值出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public String getYewu_jindu_by_columnName(String columnName)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String result = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select yewu_jindu from tb_designe where column_name = ? and yewu_jindu is not null and yewu_jindu_relate_id is not null";

			ps = ct.prepareStatement(sql);
			ps.setString(1, columnName);
			rs = ps.executeQuery();

			if (rs.next())
				result = rs.getString(1);
		} catch (Exception e) {
			logger.error("查找业务进度出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public boolean checkColumnIsDateById(int id, String type)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		boolean result = false;
		try {
			ct = DBConnection.getConnection();
			sql = " select display_type from tb_designe where id = ?";

			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				String value = rs.getString(1);
				if (value.equals(type))
					result = true;
			}
		} catch (Exception e) {
			logger.error("根据id判断该字段是否是时间字段出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public void updateYewuJindu(String tablename, String yewu_jindu,
			List<Tags> list_tags) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;

		String sql = "";
		String sqlColumn = "";
		String[] yewu_columns = yewu_jindu.split(",");
		try {
			ct = DBConnection.getConnection();
			for (int i = 0; i < list_tags.size(); i++) {
				if (list_tags.get(i).getDisplay_type().equals("相关字段")
						&& yewu_jindu
								.indexOf(list_tags.get(i).getColumn_name()) >= 0) {// 相关字段
					sqlColumn = "," + list_tags.get(i).getColumn_name();
				}
			}

			sql = "select id," + yewu_columns[4] + "," + yewu_columns[5]
					+ sqlColumn + " from " + tablename + " where "
					+ yewu_columns[4] + " is not null and " + yewu_columns[5]
					+ " is not null ";// id,本表俩个时间,本表相关字段

			ps = ct.prepareStatement(sql);
			logger.info(sql);
			rs = ps.executeQuery();

			Tags tags;
			while (rs.next()) {
				tags = new Tags();
				tags.setId(rs.getInt("id"));
				String relateId = rs.getString(4);
				if (relateId != null && !relateId.equals("")) {
					List<Map<String, String>> list_designe = new ArrayList<Map<String, String>>();// 获取相关数据
					list_designe = this.getUpdateRelate(relateId,
							yewu_columns);// 获取更新需要的数据来源

					String columnName = this.getColumn_name_by_tableName(
							"yewu", tablename);// 获取需要更新的业务进度字段名称
					double value = getCalculate(yewu_jindu, tablename,
							list_designe, rs.getString(2), rs.getString(3));// 重新计算业务进度字段的值
					sql = "update " + tablename + " set " + columnName + "="
							+ value + " where id=" + tags.getId();// 更新业务进度值

					ps = ct.prepareStatement(sql);
					ps.executeUpdate();
				}
			}
			rs.close();
			ps.close();

		} catch (Exception e) {
			logger.error("更新业务进度字段出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public List<Map<String, String>> getUpdateRelate(String relateId,
			String[] yewu_columns) throws ElException {
		Map<String, String> map = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		// 来源数据表
		String tableName = yewu_columns[0].substring(0, yewu_columns[0]
				.lastIndexOf("_"));
		List<Tags> list_tags = this
				.select_designe_field_by_tablename(tableName);

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;

		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		Connection ct2 = null;

		String sql = " select id,status ";
		String sqlColumn = "";
		String sqlTable = " from " + tableName + " ";
		String sqlWhere = " where id in (" + relateId + ") ";
		String sqlOrder = "  order by id desc ";

		for (int i = 0; i < list_tags.size(); i++) {

			if (list_tags.get(i).getList_display() == 1) {

				sqlColumn += ",";

				if (list_tags.get(i).getColumn_type().equals("date")) {
					sqlColumn += " to_char("
							+ list_tags.get(i).getColumn_name()
							+ ",'yyyy-mm-dd') "
							+ list_tags.get(i).getColumn_name();
				} else {
					sqlColumn += " " + list_tags.get(i).getColumn_name() + " ";
				}
			}

		}
		try {
			ct = DBConnection.getConnection();
			ct2 = DBConnection.getConnection();
			ps = ct.prepareStatement(sql + sqlColumn + sqlTable + sqlWhere
					+ sqlOrder);

			rs = ps.executeQuery();

			while (rs.next()) {
				map = new HashMap<String, String>();
				for (int i = 0; i < list_tags.size(); i++) {
					if (list_tags.get(i).getList_display() == 1) {
						if (list_tags.get(i).getColumn_type().equals("number")) {
							System.out.println(list_tags.get(i)
									.getColumn_name());
							map.put(list_tags.get(i).getColumn_name(), String
									.valueOf(Math.ceil(rs.getFloat(list_tags
											.get(i).getColumn_name()))));
						}
						if (list_tags.get(i).getColumn_type().equals("float")) {
							map.put(list_tags.get(i).getColumn_name(), String
									.valueOf(rs.getFloat(list_tags.get(i)
											.getColumn_name())));
						} else {
							if (list_tags.get(i).getDisplay_type().equals(
									"相关字段")) {
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
				list.add(map);
			}

		} catch (Exception e) {
			logger.error("获取更新所需数据来源出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	public double getCalculate(String yewu_jindu, String tablename,
			List<Map<String, String>> list_designe, String yewu_begintime,
			String yewu_endtime) throws ElException, ParseException {
		String[] yewu = yewu_jindu.split(",");
		// for(int x = 0;x<yewu.length - 3;x++){//将需要计算的字段id转换为字段名称
		// yewu[x] = this.getColumn_name_by_id(Integer.parseInt(yewu[x]));
		// }
		List<Map<String, String>> calculate = new ArrayList<Map<String, String>>();// 存放需要计算的字段

		Map<String, String> map;
		Map<String, String> map1;
		for (int i = 0; i < list_designe.size(); i++) {
			map1 = new HashMap<String, String>();
			map = list_designe.get(i);
			int j = 0;
			for (Object key : map.keySet()) {
				if (yewu_jindu.indexOf((String) key) >= 0) {
					if (this.getDisplay_type_by_columnName((String) key)
							.equals("百分比")) {
						if (map.get(key) == null) {
							map1.put("百分比", "0");
						} else {
							map1.put("百分比", (String) map.get(key));
						}
					} else if (this.getDisplay_type_by_columnName((String) key)
							.equals("日期")) {
						j++;
						// if(map.get(key) != null){
						// map1.put("日期" + j, (String) map.get(key));
						// }
						map1.put("日期" + j, (String) map.get(key));
					}
				}
			}
			calculate.add(map1);
		}

		String time_result = "";
		double beifenbi_result = 0.0;
		double result_zuihou = 0.0;
		List<Double> result_calculate = new ArrayList<Double>();
		for (int i = 0; i < calculate.size(); i++) {
			time_result = "";
			map1 = calculate.get(i);
			for (Object key : map1.keySet()) {
				if (((String) key).indexOf("日期") >= 0) {
					time_result += (String) map1.get(key) + ",";
				} else if (((String) key).indexOf("百分比") >= 0) {
					beifenbi_result = Double
							.parseDouble((String) map1.get(key));
				}
			}
			if (!time_result.equals("") && time_result.indexOf(",") >= 0
					&& time_result.indexOf("null") < 0) {
				String[] time_result_array = time_result.split(",");
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd hh:mm:ss");

				Date time_1 = yewu_begintime.indexOf(":") >= 0 ? dateFormat
						.parse(yewu_begintime) : dateFormat
						.parse(yewu_begintime + " 00:00:00");
				Date time_2 = yewu_endtime.indexOf(":") >= 0 ? dateFormat
						.parse(yewu_endtime) : dateFormat.parse(yewu_endtime
						+ " 00:00:00");

				if(time_result_array!= null && time_result_array.length>=2){
					Date toDate1 = dateFormat.parse(time_result_array[0]
					        + " 00:00:00");
      				Date temp = toDate1;
      				Date toDate2 = dateFormat.parse(time_result_array[1]
      						+ " 00:00:00");
      				// 获取比较小的时间
      				if (toDate1.getTime() > toDate2.getTime()) {
      					toDate1 = toDate2;
      					toDate2 = temp;
      				}
      				double time_cha = toDate2.getTime() - toDate1.getTime();
      				result_calculate.add(beifenbi_result * time_cha);

      				if (i == calculate.size() - 1) {
      					for (int x = 0; x < result_calculate.size(); x++) {
      						result_zuihou += result_calculate.get(x);
      					}
      					result_zuihou = result_zuihou
      							/ (Math.abs(time_2.getTime() - time_1.getTime()));
      					BigDecimal bg = new BigDecimal(result_zuihou);
      					result_zuihou = bg.setScale(2, BigDecimal.ROUND_HALF_UP)
      							.doubleValue();
      				}
				}
				

			}

		}
		return result_zuihou;
	}

	public int getUserIdByTablenameAndId(String tablename, int id)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int result = 0;
		try {
			ct = DBConnection.getConnection();
			sql = " select userid from " + tablename + " where id=?";

			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next())
				result = rs.getInt(1);
		} catch (Exception e) {
			logger.error("根据表名和id查创建者id出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public String getJisuan_relate(String tablename) throws ElException {

		return "";
	}

	public List<Map<String, String>> getRelateListByTablenameAndIds(
			List<Tags> list_tags, String ids, String tablename, int danjuid)
			throws ElException {
		boolean flag_colume = true;
		Map<String, String> map = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;

		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		Connection ct2 = null;

		String sql = "";

		try {
			ct = DBConnection.getConnection();
			ct2 = DBConnection.getConnection();

			// 判断有无已经添加的相关数据
			String resultIds = "";
			String produce_table = this.getProduceTableByResultTable(tablename);
			if (produce_table != null && !produce_table.equals("")) {
				if (danjuid != 0) {
					sql = "select entityid from  " + produce_table
							+ " where danjuid = " + danjuid;
					ps = ct.prepareStatement(sql);
					rs = ps.executeQuery();
					while (rs.next()) {
						resultIds += rs.getInt(1) + ",";
					}
					if (!resultIds.equals(""))
						resultIds = resultIds.substring(0, resultIds
								.lastIndexOf(","));

					String[] resultIds_array = resultIds.split(",");
					// 1,2 1,2,3 获取3
					String[] ids_array;
					for (int i = 0; i < resultIds_array.length; i++) {
						ids_array = ids.split(",");
						ids = "";
						for (int j = 0; j < ids_array.length; j++) {
							if (ids_array[j].equals(resultIds_array[i])) {
								continue;
							} else {
								ids += ids_array[j] + ",";
							}
						}
						if (String.valueOf(ids.charAt(ids.length() - 1))
								.equals(","))
							ids = ids.substring(0, ids.length() - 1);
					}
				}
			}

			sql = " select t.id,t.status,d.name department,e.username username  ";
			String sqlcolumn = "  ";
			String sqltablename = " from " + tablename
					+ " t,eluser e,department d ";
			String sqlwhere = "";
			if (ids.equals("")) {
				sqlwhere = " where  e.id=t.userid and d.id=e.depid ";
			} else {
				sqlwhere = " where t.id in (" + ids
						+ ") and e.id=t.userid and d.id=e.depid ";
			}
			String sqlorder = "  order by t.id desc ";
			String sqlEnd = "";

			for (int i = 0; i < list_tags.size(); i++) {

//				if (list_tags.get(i).getList_display() == 1) {

					sqlcolumn += ",";

					if (list_tags.get(i).getColumn_type().equals("date")) {
						sqlcolumn += " to_char("
								+ list_tags.get(i).getColumn_name()
								+ ",'yyyy-mm-dd') "
								+ list_tags.get(i).getColumn_name();
					} else {
						sqlcolumn += " " + list_tags.get(i).getColumn_name()
								+ " ";
					}
//				}

			}

			ps = ct.prepareStatement(sql + sqlcolumn + sqltablename + sqlwhere
					+ sqlorder + sqlEnd);

			rs = ps.executeQuery();
			while (rs.next()) {
				map = new HashMap<String, String>();
				for (int i = 0; i < list_tags.size(); i++) {
					if (list_tags.get(i).getList_display() == 1) {
						if (list_tags.get(i).getColumn_type().equals("number")) {
							System.out.println(list_tags.get(i)
									.getColumn_name());
							map.put(list_tags.get(i).getColumn_name(), String
									.valueOf(Math.ceil(rs.getFloat(list_tags
											.get(i).getColumn_name()))));
						}
						if (list_tags.get(i).getColumn_type().equals("float")) {
							map.put(list_tags.get(i).getColumn_name(), String
									.valueOf(rs.getFloat(list_tags.get(i)
											.getColumn_name())));
						} else {
							if (list_tags.get(i).getDisplay_type().equals(
									"相关字段")) {
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

	public String getColumnByColumnName(String tablename, String columnName)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String result = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select name_display from tb_designe where table_name = ? and column_name=? and display_type!='当前用户信息'";

			ps = ct.prepareStatement(sql);
			ps.setString(1, tablename);
			ps.setString(2, columnName);
			rs = ps.executeQuery();

			if (rs.next())
				return rs.getString(1);
		} catch (Exception e) {
			logger.error("根据表名和列名查询列中文出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public IC_column_qiuji_qiuhe biaoneijisuan(String tablename,
			String columnName) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		IC_column_qiuji_qiuhe icqq = null;
		try {
			ct = DBConnection.getConnection();
			sql = " select * from ic_column_qiuji_qiuhe where table_name = ? and column_name =?";

			ps = ct.prepareStatement(sql);
			ps.setString(1, tablename);
			ps.setString(2, columnName);
			rs = ps.executeQuery();
			if (rs.next()) {
				icqq = new IC_column_qiuji_qiuhe();
				icqq.setColumnName(rs.getString("column_name"));
				if (rs.getString("qiuji_column_name") != null
						&& !rs.getString("qiuji_column_name").equals("")) {
					icqq.setQiujiColumnName(rs.getString("qiuji_column_name"));
				}
				if (rs.getString("qiuhe_column_name") != null
						&& !rs.getString("qiuhe_column_name").equals("")) {
					icqq.setQiuheColumnName(rs.getString("qiuhe_column_name"));
				}
				// if(columnName.equals(rs.getString("column_name")) ||
				// rs.getString("qiuji_column_name").indexOf(columnName)>=0 ||
				// rs.getString("qiuhe_column_name").indexOf(columnName)>=0){
				// icqq = new IC_column_qiuji_qiuhe();
				// icqq.setQiujiColumnName(rs.getString("qiuji_column_name"));
				// icqq.setQiuheColumnName(rs.getString("qiuhe_column_name"));
				// icqq.setColumnName(rs.getString("column_name"));
				// }
			}
		} catch (Exception e) {
			logger.error("根据表名和列名查询列中文出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return icqq;
	}

	public void addToProduce(String jisuan_type, List<Tags> list_tags,
			String tablename, String parameters, int userid, int danjuid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlcolumn = " (danjuid,userid,entityid,jisuan_type,  ";
		String column = "";
		// 获取物品id

		String[] parameters_array = parameters.split(",");
		String[] parameters_par = new String[parameters_array.length];
		String[] parameters_val = new String[parameters_array.length];
		int entityid = 0;
		for (int i = 0; i < parameters_array.length; i++) {
			if (parameters_array[i].split("==")[0].equals("id")) {
				entityid = Integer.parseInt(parameters_array[i].split("==")[1]);
				break;
			}
		}
		String sqlvalues = " values (" + danjuid + "," + userid + ", "
				+ entityid + ",'" + jisuan_type + "',";
		try {
			ct = DBConnection.getConnection();
			// sqlcolumn += ",";
			// sqlvalues += ",";
			for (int i = 0; i < parameters_array.length; i++) {
				if (parameters_array[i].indexOf("==") >= 0) {
					parameters_par[i] = parameters_array[i].split("==")[0];
					parameters_val[i] = parameters_array[i].split("==")[1];
				} else {
					parameters_par[i] = "";
					parameters_val[i] = "";
				}
				if (parameters_par[i].equals("name")
						|| parameters_par[i].equals("username")
						|| parameters_par[i].equals("id")) {
					continue;
				}
				if (parameters_par.length > 0) {
					if (!parameters_par[i].equals("")) {
						if (parameters_par[i].indexOf(tablename.split(",")[1]) >= 0) {// 判断该字段不是在添加过程表的时候从结果表复制而来
							column = parameters_par[i];// 结果表字段
						} else {
							column = this
									.getColumnNameFromResultAndProduceTable(
											tablename.split(",")[0], tablename
													.split(",")[1],
											parameters_par[i]);// 过程表对应字段
						}
						if (i == parameters_par.length - 1) {
							sqlcolumn += column + ")";
							if (!parameters_val[i].equals(""))
								sqlvalues += "'" + parameters_val[i] + "'"
										+ ")";
						} else {
							sqlcolumn += column + ",";
							if (!parameters_val[i].equals(""))
								sqlvalues += "'" + parameters_val[i] + "'"
										+ ",";
						}
					}
				}
			}

			sql = " insert into " + tablename.split(",")[1] + sqlcolumn
					+ sqlvalues;

			ps = ct.prepareStatement(sql);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("添加到过程表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	// 查询结果表、过程表关联字段表，根据结果表中字段找到过程表中对应字段
	public String getColumnNameFromResultAndProduceTable(String resulttable,
			String producetable, String column) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String result = "";
		Map<String, String> map;
		try {
			ct = DBConnection.getConnection();
			sql = " select relateColumns from resulttable_producetable where resulttable = ? and producetable=?";

			ps = ct.prepareStatement(sql);
			ps.setString(1, resulttable);
			ps.setString(2, producetable);
			rs = ps.executeQuery();

			if (rs.next()) {
				map = new HashMap<String, String>();
				result = rs.getString(1);
				String[] result_array = result.indexOf(",") >= 0 ? result
						.split(",") : null;
				for (int i = 0; i < result_array.length; i++) {
					if (result_array[i].split("==")[0].equals(column)) {
						result = result_array[i].split("==")[1];
						break;
					} else {
						if (i == result_array.length - 1) {
							result = column;
						}
					}
					// map.put(result_array[i].split("==")[0],
					// result_array[i].split("==")[1]);
				}
				// for(String key:map.keySet()){
				// if(key.equals(column)){
				// result = (String)map.get(key);
				// }
				// }
			}
		} catch (Exception e) {
			logger.error("根据结果表中字段找到过程表中对应字段出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public String getProduceTableByResultTable(String tablename)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String result = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select tablename from tb_module_manage where relatetablename = ?";

			ps = ct.prepareStatement(sql);
			ps.setString(1, tablename);
			rs = ps.executeQuery();

			if (rs.next())
				return rs.getString(1);
		} catch (Exception e) {
			logger.error("根据结果表查询过程表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public String getResultTableByProduceTable(String tablename)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String result = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select resulttable from resulttable_producetable where producetable = ?";

			ps = ct.prepareStatement(sql);
			ps.setString(1, tablename);
			rs = ps.executeQuery();

			if (rs.next())
				return rs.getString(1);
		} catch (Exception e) {
			logger.error("根据过程表查询结果表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public String getProduceTableByTable(String tablename) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String result = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select relatetablename from tb_module_manage where tablename = ?";

			ps = ct.prepareStatement(sql);
			ps.setString(1, tablename);
			rs = ps.executeQuery();

			if (rs.next())
				return rs.getString(1);
		} catch (Exception e) {
			logger.error("根据业务表找到过程表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public List<String> getModuleidsByProduceTable(String tablename)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		List<String> result = new ArrayList<String>();
		try {
			ct = DBConnection.getConnection();
			sql = " select modulename,tablename from tb_module_manage where relatetablename = ?";

			ps = ct.prepareStatement(sql);
			ps.setString(1, tablename);
			rs = ps.executeQuery();

			while (rs.next()) {
				result.add(rs.getString(1) + "==" + rs.getString(2));
			}
		} catch (Exception e) {
			logger.error("根据过程表查出业务表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public void updateResultTableBiaojianValue(String parameters,
			String tablename, int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int result = 0;
		Tags tags;
		Map<String, String> map;
		String[] params = parameters.indexOf(",") >= 0 ? parameters.split(",")
				: null;

		try {
			ct = DBConnection.getConnection();
			// 查找表间计算字段和计算方式
			sql = "select column_name,jisuan_relate_type from tb_designe where table_name = ? and is_calculate = 1";
			ps = ct.prepareStatement(sql);
			ps.setString(1, tablename);
			rs = ps.executeQuery();
			tags = new Tags();
			if (rs.next()) {
				tags.setColumn_name(rs.getString(1));
				tags.setJisuan_relate_type(rs.getString(2));
			}

			map = new HashMap<String, String>();
			for (int i = 0; i < params.length; i++) {
				if (params[i].split("==")[0].equals(tags.getColumn_name())) {
					result = Integer.parseInt(params[i].split("==")[1]);
					break;
				}
			}
			// 修改表间计算的值
			sql = "select " + tags.getColumn_name() + " from " + tablename
					+ " where id=?";

			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				if (tags.getJisuan_relate_type().equals("add")) {
					// if(rs.getInt(1) != result){
					sql = "update " + tablename + " set "
							+ tags.getColumn_name() + " = ? where id = ?";
					ps = ct.prepareStatement(sql);
					ps.setInt(1, rs.getInt(1) + result);
					ps.setInt(2, id);
					// }
				} else if (tags.getJisuan_relate_type().equals("minus")) {
					// if(rs.getInt(1) != 0){
					sql = "update " + tablename + " set "
							+ tags.getColumn_name() + " = ? where id = ?";
					ps = ct.prepareStatement(sql);
					ps.setInt(1, rs.getInt(1) - result > 0 ? rs.getInt(1)
							- result : 0);
					ps.setInt(2, id);
					// }
				}
				ps.executeUpdate();
			}

			// 如果表间计算字段参与表内计算，更新表内计算的值 结果表
			sql = "select * from ic_column_qiuji_qiuhe where column_name = ?";
			ps = ct.prepareStatement(sql);
			ps.setString(1, tags.getColumn_name());
			rs = ps.executeQuery();
			String column_name = "";
			if (rs.next()) {
				column_name = rs.getString("column_name");
				if (rs.getInt("is_zuoweiji") == 1) {// 表间计算字段作为表内计算的积

				}
				if (rs.getInt("is_qiuji") == 1) {// 表间计算字段作为表内计算的乘数
					// 更新积字段值
					sql = "select column_name,qiuji_column_name from ic_column_qiuji_qiuhe where is_zuoweiji=1 and table_name= '"
							+ tablename + "'";
					ps = ct.prepareStatement(sql);
					rs = ps.executeQuery();
					String qiuji_column_name = "";
					double returnValue_double = 0.0;
					String sqlAppend = "";
					while (rs.next()) {
						qiuji_column_name = rs.getString(2);
						String[] array = qiuji_column_name.split(",");
						if (qiuji_column_name.indexOf(column_name) >= 0) {
							for (int i = 0; i < array.length; i++) {
								sqlAppend = "select " + array[i] + " from "
										+ tablename + " where id = " + id;
								ps = ct.prepareStatement(sql);
								rs = ps.executeQuery();
								if (rs.next()) {
									if (returnValue_double == 0.0) {
										returnValue_double = 1.0;
									} else {
										returnValue_double = returnValue_double
												* rs.getDouble(1);
									}
								}
							}
							sql = "update " + tablename + " set "
									+ rs.getString(1) + " = "
									+ returnValue_double + " where id = " + id;
							ps = ct.prepareStatement(sql);
							ps.executeUpdate();
						}
					}
				}
			}

		} catch (Exception e) {
			logger.error("修改结果表中表间计算字段值出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public int checkTable(String tablename) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int result = 0;
		try {
			ct = DBConnection.getConnection();
			sql = " select tabletype from tb_module_manage where tablename = ?";

			ps = ct.prepareStatement(sql);
			ps.setString(1, tablename);
			rs = ps.executeQuery();

			if (rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			logger.error("判断该表是结果表还是过程表或者普通表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public String getProduceColumns(String tablename, String showColumnIds)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String result = "";
		try {
			ct = DBConnection.getConnection();
			if (showColumnIds != null && !showColumnIds.equals("")) {
				showColumnIds = " and id in (" + showColumnIds + ")";
				sql = " select column_name,name_display,table_name,display_type,default_value from tb_designe where table_name = ? and from_result_table = 1 AND display_type!='当前用户信息' "
						+ showColumnIds;
			} else
				sql = " select column_name,name_display,table_name,display_type,default_value from tb_designe where table_name = ? and from_result_table = 1 AND display_type!='当前用户信息' ";
			ps = ct.prepareStatement(sql);
			ps.setString(1, tablename);
			rs = ps.executeQuery();

			while (rs.next()) {
				result += rs.getString(1) + "_-_" + rs.getString(2) + "_-_"
						+ rs.getString(3) + "_-_" + rs.getString(4) + "_-_"
						+ rs.getString(5) + "_--_";
			}
		} catch (Exception e) {
			logger.error("根据结果表查询过程表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		System.out.println(result);
		return result;
	}

	public String getBiaojianqiuheResultTableAndColumn(String tablename)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String result = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select name_display,biaojianqiuhe_tablename,biaojianqiuhe_column from tb_designe where table_name = ?  and biaojianqiuhe_check = 1";

			ps = ct.prepareStatement(sql);
			ps.setString(1, tablename);
			rs = ps.executeQuery();

			while (rs.next()) {
				result += rs.getString(1) + "_-_" + rs.getString(2) + "_-_"
						+ rs.getString(3) + "_--_";
			}
			if (result.indexOf("_--_") >= 0) {
				result = result.substring(0, result.lastIndexOf("_--_"));
			}
		} catch (Exception e) {
			logger.error("获取过程表中与结果表不对应的字段出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		System.out.println(result);
		return result;
	}

	public String getProduceColumnByResultColumn(String tablename, String param)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String result = "";
		String end_result = "";// 返回结果字符串
		try {
			ct = DBConnection.getConnection();
			sql = " select tablename from tb_module_manage where relatetablename = ? ";

			ps = ct.prepareStatement(sql);
			ps.setString(1, tablename);
			rs = ps.executeQuery();

			if (rs.next()) {
				result = rs.getString(1);
				// if(!result.equals("")){
				// String[] array =
				// result.indexOf(",")>=0?result.split(","):null;
				// if(array != null){
				// for(int i=0;i<array.length;i++){
				// map.put(array[i].split("==")[0], array[i].split("==")[1]);
				// }
				// }
				// }
			}
			sql = "select * from tb_designe where table_name = '" + result
					+ "' and from_result_table is null";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				end_result += rs.getString("column_name") + ",";
			}
			end_result = end_result.substring(0, end_result.lastIndexOf(","))
					+ ",id";
		} catch (Exception e) {
			logger.error("将204改为231出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		System.out.println(end_result);
		return end_result;
	}

	public void updateProduceContent(Map<String, String> map, int id,
			String produce_table) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlset = "";

		try {
			ct = DBConnection.getConnection();

			if (map != null) {
				for (String key : map.keySet()) {
					sqlset += key + "='" + map.get(key) + "',";
				}
				sqlset = sqlset.substring(0, sqlset.lastIndexOf(","));
				sqlset += ",shenhestatus=1 ";// 状态为==通过
			} else {
				sqlset = "shenhestatus=0";// 状态为已创建
			}

			sql = " update " + produce_table + " set " + sqlset
					+ " where id = ? ";

			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("根据id修改过程表数据出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	// 删除操作
	public void updateProduceContent_(Map<String, String> map, int id,
			String produce_table) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";

		try {
			ct = DBConnection.getConnection();
			if (map != null)
				sql = " update " + produce_table
						+ " set shenhestatus = 0 where id = ? ";
			else
				sql = " update " + produce_table
						+ " set shenhestatus = 5 where id = ? ";

			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("根据id修改过程表数据出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public String checkColumnIsBiaojianjisuan(String tablename,
			String columnname) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String result = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select jisuan_result_relate_id,jisuan_relate_type,column_name from tb_designe where table_name = ? and column_name=? and jisuan_relate_type is not null";

			ps = ct.prepareStatement(sql);
			ps.setString(1, tablename);
			ps.setString(2, columnname);
			rs = ps.executeQuery();

			if (rs.next())
				result = rs.getString(1) + ";" + rs.getString(2) + ";"
						+ rs.getString(3);
		} catch (Exception e) {
			logger.error("该字段是否表间计算字段，返回列名,计算类型出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public void updateResultContent(String result_table, String produce_table,
			String column_in_result_table_, String columns_in_produce_table,
			int entityid, int danjuid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		double f = 0.0;
		double yuanshi = 0.0;
		try {
			ct = DBConnection.getConnection();
			sql = "select " + columns_in_produce_table + ",jisuan_type from "
					+ produce_table + " where shenhestatus=1 and entityid="
					+ entityid;
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				if (rs.getString("jisuan_type") != null) {
					if (rs.getString("jisuan_type").equals("add"))
						f += rs.getDouble(columns_in_produce_table);
					else if (rs.getString("jisuan_type").equals("minus"))
						f -= rs.getDouble(columns_in_produce_table);
					else if (rs.getString("jisuan_type").equals("no")) {

					}
				}
			}

			sql = "select " + column_in_result_table_ + ",has_init from "
					+ result_table + " where id=" + entityid;
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				yuanshi = rs.getDouble(2);
			}

			f += yuanshi;

			sql = " update " + result_table + " set " + column_in_result_table_
					+ "=" + f + " where id =  " + entityid;
			ps = ct.prepareStatement(sql);
			ps.executeUpdate();

			// ct = DBConnection.getConnection();
			// sql = "select " + column_in_result_table_ + " from " +
			// result_table + " where id=" + entityid;
			// ps = ct.prepareStatement(sql);
			// rs = ps.executeQuery();
			// if(rs.next()){
			// yusnshi_shuliang = rs.getDouble(column_in_result_table_);
			// }
			//			
			// sql = "select * from " + produce_table + " where entityid=" +
			// entityid + " and danjuid=" + danjuid;
			// ps = ct.prepareStatement(sql);
			// rs = ps.executeQuery();
			// while(rs.next()){
			// f += rs.getDouble(columns_in_produce_table);
			// }
			// double value = yusnshi_shuliang + f;
			// sql = " update " + result_table + " set
			// "+column_in_result_table_+"="+value+" where id = "+entityid;
			//
			// ps = ct.prepareStatement(sql);
			// ps.executeUpdate();

		} catch (Exception e) {
			logger.error("修改结果表表间计算字段值出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public void updateBiaoneijisuanContent(String tablename,
			List<Tags> list_tags, List<Map<String, String>> list_designe)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String result = "";
		String[] result_array = null;
		try {
			ct = DBConnection.getConnection();
			sql = " select * from ic_column_qiuji_qiuhe where table_name = ? and is_zuoweiji = 1 and qiuji_column_name is not null";

			ps = ct.prepareStatement(sql);
			ps.setString(1, tablename);
			rs = ps.executeQuery();
			while (rs.next()) {
				result += rs.getString("column_name") + "=="
						+ rs.getString("qiuji_column_name") + "===";
			}
			if (result.indexOf("===") >= 0)
				result = result.substring(0, result.lastIndexOf("==="));

			if (!result.equals("")) {
				result_array = result.split("===");

				Map<String, String> map;
				for (int i = 0; i < list_designe.size(); i++) {
					double d = 0.0;
					map = list_designe.get(i);
					for (String key : map.keySet()) {
						for (int j = 0; j < result_array.length; j++) {
							if (key.equals(result_array[j].split("==")[0])) {
								String[] arr = result_array[j].split("==")[1]
										.split(",");
								for (int z = 0; z < arr.length; z++) {
									if (z == 0 && d == 0.0) {
										d = 1.0;
									}
									d *= Double.parseDouble(map.get(arr[z]));
								}
								sql = "update " + tablename + " set " + key
										+ "=" + d + " where id = "
										+ Integer.parseInt(map.get("id"));
								ps = ct.prepareStatement(sql);
								ps.executeUpdate();
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("更新表内计算的字段值出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public void updateBiaoneijisuanContent_(String result_table,
			String produce_table, List<Tags> list_tags,
			List<Map<String, String>> list_designe) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String result = "";
		String[] result_array = null;
		try {
			ct = DBConnection.getConnection();
			sql = " select * from ic_column_qiuji_qiuhe where table_name = ? and is_zuoweiji = 1 and qiuji_column_name is not null";

			ps = ct.prepareStatement(sql);
			ps.setString(1, result_table);
			rs = ps.executeQuery();
			while (rs.next()) {
				result += rs.getString("column_name") + "=="
						+ rs.getString("qiuji_column_name") + "===";
			}
			if (result.indexOf("===") >= 0)
				result = result.substring(0, result.lastIndexOf("==="));

			if (!result.equals("")) {
				result_array = result.split("===");

				Map<String, String> map;
				for (int i = 0; i < list_designe.size(); i++) {
					double d = 0.0;
					map = list_designe.get(i);
					for (String key : map.keySet()) {
						for (int j = 0; j < result_array.length; j++) {
							if (key.equals(this
									.getColumnNameFromResultAndProduceTable(
											result_table, produce_table,
											result_array[j].split("==")[0]))) {
								String[] arr = result_array[j].split("==")[1]
										.split(",");
								for (int z = 0; z < arr.length; z++) {
									if (z == 0 && d == 0.0) {
										d = 1.0;
									}
									d *= Double
											.parseDouble(map
													.get(this
															.getColumnNameFromResultAndProduceTable(
																	result_table,
																	produce_table,
																	arr[z])));
								}
								sql = "update " + produce_table + " set " + key
										+ "=" + d + " where id = "
										+ Integer.parseInt(map.get("id"));
								ps = ct.prepareStatement(sql);
								ps.executeUpdate();
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("更新表内计算的字段值出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public boolean checkColumnIsExistByTable(String tablename, String columnname)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		boolean result = false;
		try {
			ct = DBConnection.getConnection();
			sql = " select * from tb_designe where table_name = ? and column_name = ?";

			ps = ct.prepareStatement(sql);
			ps.setString(1, tablename);
			ps.setString(2, columnname.toUpperCase());
			rs = ps.executeQuery();
			if (rs.next())
				result = true;
		} catch (Exception e) {
			logger.error("判断该字段是否在该表中已经存在出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public String checkSelectColumnIsRight(String tablename, String columnname)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String result = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select display_type from tb_designe where table_name = ? and column_name = ?";

			ps = ct.prepareStatement(sql);
			ps.setString(1, tablename);
			ps.setString(2, columnname);
			rs = ps.executeQuery();
			if (rs.next()){
				result = rs.getString(1);
			}
		} catch (Exception e) {
			logger.error("判断要添加的列在自动读取的时候选择的列类型是否一致,返回类型出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public boolean checkColumnRelateIsResultTable(String tablename,
			String columnName) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		boolean result = false;
		String table = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select table_name,column_name,default_value from tb_designe where table_name = '"
					+ tablename + "' and column_name = '" + columnName + "'";

			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString(3) != null && !rs.getString(3).equals("")) {
					table = rs.getString(3).split("==")[0];
					if (this.checkTable(table) == 3) {
						result = true;
					}
				}
			}
		} catch (Exception e) {
			logger.error("判断是相关字段且关联模块式结果表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public void addUpdateProduceColumn(Tags tags) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();

			sql = "";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
		} catch (Exception e) {
			logger.error("结果表中添加字段时，过程表中添加相应字段出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public String checkColumnIsCalculate(String tablename, String column)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String result = "";
		try {
			ct = DBConnection.getConnection();

			sql = "select is_calculate from tb_designe where table_name = ? and column_name = ?";
			ps = ct.prepareStatement(sql);
			ps.setString(1, tablename);
			ps.setString(2, column);
			rs = ps.executeQuery();
			if (rs.next())
				result = String.valueOf(rs.getInt(1));
		} catch (Exception e) {
			logger.error("结果表中添加字段时，过程表中添加相应字段出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public void deleteProduceTableById(String produce_table, int id)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = " delete  from " + produce_table + " where id=" + id;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error(produce_table + "表删除出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public int checkProduceTableShenheStatus(String tablename, int id)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int result = 0;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select shenhestatus from " + tablename + " where id = " + id;
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("根据id和tablename查询审核状态出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public String getBiaojianqiuheValue(String tablename) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String result = "";
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select column_name,biaojianqiuhe_tablename,biaojianqiuhe_column from tb_designe where table_name = '"
					+ tablename + "' and biaojianqiuhe_check = 1";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				result += rs.getString(1)
						+ "=="
						+ rs.getString(2)
						+ "=="
						+ this.getColumn_name_by_id(Integer.parseInt(rs
								.getString(3))) + "===";
			}
			if (!result.equals(""))
				result = result.substring(0, result.lastIndexOf("==="));
		} catch (Exception e) {
			logger.error("根据业务表获取表间求和的表和列出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public String getRelate_thing_id(String tablename) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String result = "";
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select id from tb_designe where table_name = '" + tablename
					+ "' and display_type = '相关字段' and is_show_complete = 1";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				result += String.valueOf(rs.getInt(1));
			}
		} catch (Exception e) {
			logger.error("获取业务表中相关字段的id出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public void updateYewuBiaojianqiuheContent(String tablename, Tags tags,
			int danjuid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		double value = 0.0;
		try {
			ct = DBConnection.getConnection();

			sql = "select "
					+ this.getColumn_name_by_id(Integer.parseInt(tags
							.getBiaojianqiuhe_column())) + " from "
					+ tags.getBiaojianqiuhe_tablename() + " where moduleid = '"
					+ tablename + "' and danjuid = " + danjuid;
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				value += rs.getDouble(1);
			}

			sql = "update " + tablename + " set " + tags.getColumn_name()
					+ " = " + value + " where id= " + danjuid;

			ps = ct.prepareStatement(sql);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("修改页面删除相关数据后，修改业务表表内求和字段的值出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public String getShowColumns(String tablename) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String result = "";
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select wanzheng from tb_designe where table_name = '"
					+ tablename
					+ "' and display_type = '相关字段' and is_show_complete = 1 and wanzheng is not null ";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				result += rs.getString(1);
			}
		} catch (Exception e) {
			logger.error("相关字段完整显示的字段ids出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public String getColumnNameByFromtablenameAndColumnName(String tablename,
			String columnName) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String result = "";
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select column_name from tb_designe where table_name =  '"
					+ tablename + "' and fromtablename_columnname = '"
					+ columnName + "'";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getString(1);
			}
		} catch (Exception e) {
			logger.error("根据合同表和相关列找到列名出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public List<String> selectColumnValues(String tablename, String columnname)
			throws ElException {
		List<String> list = new ArrayList<String>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select " + columnname + " from " + tablename;
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getString(1));
			}
		} catch (Exception e) {
			logger.error("根据列查出表中该列值出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	public void deleteDesigneField(String tablename, String columnName, int id)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String delete_columns = "";
		String[] delete_columns_array;
		String qiuji_column_name = "";
		String qiuji_column_name_ = "";
		String[] qiuji_column_name_array;
		try {
			ct = DBConnection.getConnection();
			// 删除tb_designe中数据
			sql = " delete from tb_designe where id=" + id;
			ps = ct.prepareStatement(sql);
			ps.executeUpdate();

			// 删除表中列
			sql = " alter table  " + tablename + " drop column " + columnName;
			ps = ct.prepareStatement(sql);
			ps.executeUpdate();

			// 如果该字段时表内计算字段，那么删除ic_column_qiuji_qiuhe中数据
			sql = "select * from ic_column_qiuji_qiuhe where table_name = '"
					+ tablename + "' and column_name ='" + columnName + "'";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getInt("is_qiuji") == 1) {// 如果是求积字段
					sql = "select * from ic_column_qiuji_qiuhe ";
					ps = ct.prepareStatement(sql);
					rs = ps.executeQuery();
					while (rs.next()) {
						qiuji_column_name = rs.getString("qiuji_column_name");
						if (qiuji_column_name != null
								&& !qiuji_column_name.equals("")) {
							qiuji_column_name_array = qiuji_column_name
									.split(",");
							for (int i = 0; i < qiuji_column_name_array.length; i++) {
								if (!qiuji_column_name_array[i]
										.equals(columnName)) {
									qiuji_column_name_ += qiuji_column_name_array[i]
											+ ",";
								}
							}
							if (String
									.valueOf(
											qiuji_column_name_
													.charAt(qiuji_column_name_
															.length() - 1))
									.equals(",")) {
								qiuji_column_name_ = qiuji_column_name_
										.substring(0, qiuji_column_name_
												.lastIndexOf(","));
							}
							sql = " update ic_column_qiuji_qiuhe set qiuji_column_name = '"
									+ qiuji_column_name_
									+ "' "
									+ // 更新作为积字段
									" where table_name = '"
									+ tablename
									+ "' and column_name = '"
									+ rs.getString("column_name") + "'";
						}
					}
					sql = "delete from ic_column_qiuji_qiuhe "
							+ " where tablename = '" + tablename
							+ "' and column_name=" + columnName + "'";// 删除求积字段
					ps = ct.prepareStatement(sql);
					ps.executeUpdate();
				}
				if (rs.getInt("is_zuoweiji") == 1) {// 如果作为积字段
					qiuji_column_name = rs.getString("qiuji_column_name");
					sql = "delete from ic_column_qiuji_qiuhe "
							+ " where tablename = '" + tablename
							+ "' and column_name=" + columnName + "'";// 删除作为积字段
					ps = ct.prepareStatement(sql);
					ps.executeUpdate();

					if (qiuji_column_name != null
							&& !qiuji_column_name.equals("")) {
						delete_columns_array = delete_columns.split(",");
						for (int i = 0; i < delete_columns_array.length; i++) {
							if (i == delete_columns_array.length - 1)
								delete_columns += " (column_name = '"
										+ delete_columns_array[i] + "') ";
							else
								delete_columns += " (column_name = '"
										+ delete_columns_array[i] + "') "
										+ " or ";
						}
						sql = "delete from ic_column_qiuji_qiuhe "
								+ " where tablename = '" + tablename + "' and "
								+ delete_columns;// 删除求积字段
						ps = ct.prepareStatement(sql);
						ps.executeUpdate();
					}
				}
				if (rs.getInt("is_qiuhe") == 1) {// 如果是求和字段
					sql = "select * from ic_column_qiuji_qiuhe ";
					ps = ct.prepareStatement(sql);
					rs = ps.executeQuery();
					while (rs.next()) {
						qiuji_column_name = rs.getString("qiuhe_column_name");
						if (qiuji_column_name != null
								&& !qiuji_column_name.equals("")) {
							qiuji_column_name_array = qiuji_column_name
									.split(",");
							for (int i = 0; i < qiuji_column_name_array.length; i++) {
								if (!qiuji_column_name_array[i]
										.equals(columnName)) {
									qiuji_column_name_ += qiuji_column_name_array[i]
											+ ",";
								}
							}
							if (String
									.valueOf(
											qiuji_column_name_
													.charAt(qiuji_column_name_
															.length() - 1))
									.equals(",")) {
								qiuji_column_name_ = qiuji_column_name_
										.substring(0, qiuji_column_name_
												.lastIndexOf(","));
							}
							sql = " update ic_column_qiuji_qiuhe set qiuhe_column_name = '"
									+ qiuji_column_name_
									+ "' "
									+ // 更新作为和字段
									" where table_name = '"
									+ tablename
									+ "' and column_name = '"
									+ rs.getString("column_name") + "'";
						}
					}
					sql = "delete from ic_column_qiuji_qiuhe "
							+ " where tablename = '" + tablename
							+ "' and column_name=" + columnName + "'";// 删除求和字段
					ps = ct.prepareStatement(sql);
					ps.executeUpdate();
				}
				if (rs.getInt("is_zuoweihe") == 1) {// 如果作为和字段
					qiuji_column_name = rs.getString("qiuhe_column_name");
					sql = "delete from ic_column_qiuji_qiuhe "
							+ " where tablename = '" + tablename
							+ "' and column_name=" + columnName + "'";// 删除作为和字段
					ps = ct.prepareStatement(sql);
					ps.executeUpdate();

					if (qiuji_column_name != null
							&& !qiuji_column_name.equals("")) {
						delete_columns_array = delete_columns.split(",");
						for (int i = 0; i < delete_columns_array.length; i++) {
							if (i == delete_columns_array.length - 1)
								delete_columns += " (column_name = '"
										+ delete_columns_array[i] + "') ";
							else
								delete_columns += " (column_name = '"
										+ delete_columns_array[i] + "') "
										+ " or ";
						}
						sql = "delete from ic_column_qiuji_qiuhe "
								+ " where tablename = '" + tablename + "' and "
								+ delete_columns;// 删除求积字段
						ps = ct.prepareStatement(sql);
						ps.executeUpdate();
					}
				}
			}
		} catch (Exception e) {
			logger.error("根据id和tablename删除列出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public void updateProduceHas_init(String tablename, String columnName,
			int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select  " + columnName + " from " + tablename
					+ " where id = " + id;
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getInt(1) > 0) {
					sql = " update " + tablename + " set has_init = "
							+ rs.getInt(1) + " where id = " + id;
					ps = ct.prepareStatement(sql);
					ps.executeUpdate();
				}
			}

		} catch (Exception e) {
			logger.error("如果表间计算字段值>0修改has_init为1出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public String getQiujiAndHeInfo(String tablename, String columnName)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String returnValue = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select  is_qiuji,is_qiuhe,is_zuoweiji,is_zuoweihe,qiuji_column_name,qiuhe_column_name from ic_column_qiuji_qiuhe "
					+ " where table_name = '"
					+ tablename
					+ "' and column_name = '" + columnName + "'";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				returnValue = String.valueOf(rs.getInt(1)) + "=="
						+ String.valueOf(rs.getInt(2)) + "=="
						+ String.valueOf(rs.getInt(3)) + "=="
						+ String.valueOf(rs.getInt(4)) + "==" + rs.getString(5)
						+ "==" + rs.getString(6);
			}

		} catch (Exception e) {
			logger.error("修改时查询是否是表内计算出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return returnValue;
	}

	public String getRelateId(String columnName, int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String result = "";
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select relateid from tb_tags_relate where columnname = '"
					+ columnName + "' and mainid = " + id;
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				result += String.valueOf(rs.getInt(1)) + ",";
			}
			if (result != null && !result.equals(""))
				result = result.substring(0, result.lastIndexOf(","));
		} catch (Exception e) {
			logger.error("获取相关ID出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public String getResult_table_column(String tablename, String columnName)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String result = "";
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select default_value from tb_designe where table_name = '"
					+ tablename + "' and column_name = '" + columnName + "'";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				result = String.valueOf(rs.getString(1));
				if (result != null && !result.equals("")) {
					result = result.split("==")[1];
				}
			}
		} catch (Exception e) {
			logger.error("获取相关的结果表中字段出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public void addToTb_tags_relate(String columnName, int relateid, int mainid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " insert into tb_tags_relate (columnname,relateid,mainid) values "
					+ "('" + columnName + "'," + relateid + "," + mainid + ")";
			ps = ct.prepareStatement(sql);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加到过程表后添加相应数据到tb_tags_relate出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public String getColumnValueByAuto(String tablename, String columnName,
			int id, String yewu_tablename, int danjuid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String result = "";
		String sql = "";
		String moduleId = "";
		if (yewu_tablename != null && !yewu_tablename.equals("")) {
			moduleId = new ModuleManageDaoImpl()
					.getFromtablenameByTablename(yewu_tablename);
		}
		try {
			ct = DBConnection.getConnection();
			sql = " select " + columnName + " from " + tablename
					+ " where entityid=" + id + " and danjuid = " + danjuid
					+ " and moduleid= '" + moduleId + "'";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				result = String.valueOf(rs.getString(1));
			}
		} catch (Exception e) {
			logger.error("获取对应的字段值出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public String getDesigneColumns(String tablename) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String result = "";
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select id,column_name,display_type from tb_designe where table_name = '"
					+ tablename.toUpperCase() + "' and add_display=1 ";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				result += rs.getInt(1) + "==" + rs.getString(2) + "=="
						+ rs.getString(3) + ";";
			}
			if (result != null && !result.equals("")) {
				result = result.substring(0, result.lastIndexOf(";"));
			}
		} catch (Exception e) {
			logger.error("获取字段出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public void insert_tableinfo_by_tablename_relatecolumn(int userid,
			String parameters, String tablename) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;

		List<String> list_columnname = new ArrayList<String>();
		List<String> list_content = new ArrayList<String>();
		int id = 0;
		String sql = "";
		String sqlColumn = "";
		String sqlValue = "";
		String[] array;
		String display_type = "";
		String sqlblob = "";
		try {
			ct = DBConnection.getConnection();

			if (parameters != null && !parameters.equals("")) {
				array = parameters.split(";");
				for (int i = 0; i < array.length; i++) {
					display_type = array[i].split("===")[1];
					sqlColumn += "," + array[i].split("===")[0];
					if (display_type.equals("文本") || display_type.equals("大文本")
							|| display_type.equals("相关字段")
							|| display_type.equals("相关负责人")
							|| display_type.equals("单选")
							|| display_type.equals("复选")
							|| display_type.equals("下拉选项")) {
						sqlValue += ",'" + array[i].split("===")[2] + "'";
					} else if (display_type.equals("日期")) {
						sqlValue += ",to_date('" + array[i].split("===")[2]
								+ "','yyyy-mm-dd hh24:mi:ss')";
					} else if (display_type.equals("附件上传")) {
						sqlValue += ",'" + array[i].split("===")[2] + "'";
					} else if (display_type.equals("图片")) {
						sqlValue += ",'" + array[i].split("===")[2] + "'";
						// }else if(display_type.equals("富文本")){
						// sqlColumn += ",empty_blob()";
					} else if (display_type.equals("实数")
							|| display_type.equals("整数")
							|| display_type.equals("百分比")) {
						sqlValue += "," + array[i].split("===")[2];
					}
				}
			}

			sql = "insert into " + tablename + " (status,userid,PRINCIPALID "
					+ sqlColumn + ") " + " values('0'," + userid + ","
					+ userid + " " + sqlValue + ")";
			ps = ct.prepareStatement(sql);
			ps.executeUpdate();

			// // 插入blob数据
			// for (int i = 0; i < list_columnname.size(); i++) {
			// // int id=0;
			// // sql ="select TB_CLIENTLINKMAN_TAGS_SEQUENCE.currval from
			// // dual";
			//
			// sqlblob = " select " + list_columnname.get(i) + " from "
			// + tablename + " where id=" + id + " for update";
			// ps = ct.prepareStatement(sqlblob);
			// rs = ps.executeQuery();
			// if (rs.next()) {
			// // 得到java.sql.Blob对象后强制转换为oracle.sql.BLOB
			// oracle.sql.BLOB blob = (oracle.sql.BLOB) rs.getBlob(1);
			// OutputStream outStream = blob.getBinaryOutputStream();
			// // data是传入的byte数组，定义：byte[] data
			// byte[] data = list_content.get(i).getBytes();
			// outStream.write(data, 0, data.length);
			//
			// outStream.flush();
			// outStream.close();
			// }
			//
			// }
		} catch (Exception e) {
			logger.error("插入出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public boolean checkColumnValueIsExist(String tablename, String columnName,
			String value) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean result = false;
		String sql = "";
		String table_value = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select " + columnName + " from " + tablename;
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				table_value = rs.getString(1);
				if (table_value != null && !table_value.equals("")) {
					if (table_value.equals(value)) {
						result = true;
					}
				}
			}
		} catch (Exception e) {
			logger.error("判断客户名称是否唯一出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public String select_columnname_by_tablename_chengshi(String tablename)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String result = "";
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select column_name from tb_designe where table_name ='"
					+ tablename + "' and display_type = '城市'";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				if (rs.getString(1) != null && !rs.getString(1).equals("")) {
					result += rs.getString(1) + ",";
				}
			}
			if (!result.equals(""))
				result = result.substring(0, result.lastIndexOf(","));
		} catch (Exception e) {
			logger.error("获取城市类型字段出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public String getShengshixian(int id, String tablename, String columnname)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String result = "";
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select " + columnname + " from " + tablename + " where id="
					+ id;
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString(1) != null && !rs.getString(1).equals("")) {
					result = rs.getString(1);
				}
			}
		} catch (Exception e) {
			logger.error("获取省市县出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public String getDefaultValue_shengshixian(String tablename,
			String columnName) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String result = "";
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select default_value from tb_designe where table_name = '"
					+ tablename + "' and column_name = '" + columnName + "'";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString(1) != null && !rs.getString(1).equals("")) {
					result = rs.getString(1);
				}
			}
		} catch (Exception e) {
			logger.error("获取设置的省市县默认值出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public String getZidongbuqiValue(String tablename, String columnName)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String result = "";
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select " + columnName + " from " + tablename;
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				if (rs.getString(1) != null && !rs.getString(1).equals("")) {
					result += rs.getString(1).trim() + ",";
				}
			}
			if (!result.equals("")) {
				result = result.substring(0, result.lastIndexOf(","));
			}
		} catch (Exception e) {
			logger.error("获取自动补齐数据出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public static boolean checkIdIsIn(String[] array, int id) {
		boolean flag = false;
		String temp = "";
		if (array != null && array.length > 0) {
			for (int i = 0; i < array.length; i++) {
				temp = array[i];
				if (temp != null && !temp.equals("")) {
					if (Integer.parseInt(temp) == id) {
						flag = true;
					}
				}
			}
		}
		return flag;
	}

	/**
	 * 客户分析一览查看
	 */
	public List<Map<String, String>> select_designe_by_tablename(int entityid,
			List<Tags> list_tags, String tablename, int pageNow, int pageSize)
			throws ElException {
		boolean flag_colume = true;
		Map<String, String> map = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;

		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		Connection ct2 = null;

		PreparedStatement ps3 = null;
		ResultSet rs3 = null;
		Connection ct3 = null;

		String sqlBegin = " SELECT * FROM ( " + " SELECT A.*, ROWNUM RN "
				+ " FROM ( ";
		String sql = " select  t.id,t.status ,e.username ,(select username from eluser where id=t.principalid) principalname ,d.name ";
		String sqlcolumn = "  ";
		String sqltablename = " from " + tablename
				+ " t, eluser e   ,department d ";
		String sqlwhere0 = " where  userid in ";
		String sqlwhere_dep = " (select id from eluser where valid=1 ";

		String sqlwhere = " and t.userid=e.id  and  e.depid=d.id ) ";
		String sqlorder = " order by id desc ";
		String sqlEnd = "    ) A " + " WHERE ROWNUM <= ? " + " )WHERE RN >= ? ";

		String allsql = "";
		String sqlAppend = "";

		String sql_ = "";
		String ids = "";
		// sqlcolumn
		for (int i = 0; i < list_tags.size(); i++) {

			if (list_tags.get(i).getDepartsearch_display() == 1) {

				sqlcolumn += ",";
				if (list_tags.get(i).getColumn_type().equals("date")) {
					sqlcolumn += " to_char("
							+ list_tags.get(i).getColumn_name()
							+ ",'yyyy-mm-dd') "
							+ list_tags.get(i).getColumn_name();
				} else {
					sqlcolumn += " " + list_tags.get(i).getColumn_name() + " ";
				}
				if (String.valueOf(sqlcolumn.charAt(sqlcolumn.length() - 1))
						.equals(","))
					sqlcolumn = sqlcolumn.substring(0, sqlcolumn
							.lastIndexOf(","));
			}

		}
		if (this.checkTable(tablename) == 2) {
			sqlcolumn += " ,moduleid,danjuid ";
		}

		try {
			ct = DBConnection.getConnection();
			ct2 = DBConnection.getConnection();
			ct3 = DBConnection.getConnection();

			sql_ = "select * from " + tablename;
			ps3 = ct3.prepareStatement(sql_);
			rs3 = ps3.executeQuery();
			String where_ids = "";
			if (tablename.equals("LXXW")) {
				while (rs3.next()) {
					if (rs3.getString("LXXW_XGKH") != null
							&& !rs3.getString("LXXW_XGKH").equals("")) {
						if (checkIdIsIn(rs3.getString("LXXW_XGKH").split(","),
								entityid)) {
							ids += rs3.getInt("id") + ",";
						}
					}
				}
			} else if (tablename.equals("GRRZ")) {
				while (rs3.next()) {
					if (rs3.getString("GRRZ_XGKH") != null
							&& !rs3.getString("GRRZ_XGKH").equals("")) {
						if (checkIdIsIn(rs3.getString("GRRZ_XGKH").split(","),
								entityid)) {
							ids += rs3.getInt("id") + ",";
						}
					}
				}
			} else if (tablename.equals("XMDA")) {
				while (rs3.next()) {
					if (rs3.getString("XMDA_KH") != null
							&& !rs3.getString("XMDA_KH").equals("")) {
						if (checkIdIsIn(rs3.getString("XMDA_KH").split(","),
								entityid)) {
							ids += rs3.getInt("id") + ",";
						}
					}
				}
			} else if (tablename.equals("SK")) {
				while (rs3.next()) {
					if (rs3.getString("SK_KHMC") != null
							&& !rs3.getString("SK_KHMC").equals("")) {
						if (checkIdIsIn(rs3.getString("SK_KHMC").split(","),
								entityid)) {
							ids += rs3.getInt("id") + ",";
						}
					}
				}
			} else if (tablename.equals("FK")) {
				while (rs3.next()) {
					if (rs3.getString("FK_XGKH") != null
							&& !rs3.getString("FK_XGKH").equals("")) {
						if (checkIdIsIn(rs3.getString("FK_XGKH").split(","),
								entityid)) {
							ids += rs3.getInt("id") + ",";
						}
					}
				}
			} else if (tablename.equals("YS")) {
				while (rs3.next()) {
					if (rs3.getString("YS_KHMC") != null
							&& !rs3.getString("YS_KHMC").equals("")) {
						if (checkIdIsIn(rs3.getString("YS_KHMC").split(","),
								entityid)) {
							ids += rs3.getInt("id") + ",";
						}
					}
				}
			} else if (tablename.equals("YF")) {
				while (rs3.next()) {
					if (rs3.getString("YF_KHMC") != null
							&& !rs3.getString("YF_KHMC").equals("")) {
						if (checkIdIsIn(rs3.getString("YF_KHMC").split(","),
								entityid)) {
							ids += rs3.getInt("id") + ",";
						}
					}
				}
			} else if (tablename.equals("QTSR")) {
				while (rs3.next()) {
					if (rs3.getString("QTSR_KHMC") != null
							&& !rs3.getString("QTSR_KHMC").equals("")) {
						if (checkIdIsIn(rs3.getString("QTSR_KHMC").split(","),
								entityid)) {
							ids += rs3.getInt("id") + ",";
						}
					}
				}
			} else if (tablename.equals("FYZC")) {
				while (rs3.next()) {
					if (rs3.getString("FYZC_XGKH") != null
							&& !rs3.getString("FYZC_XGKH").equals("")) {
						if (checkIdIsIn(rs3.getString("FYZC_XGKH").split(","),
								entityid)) {
							ids += rs3.getInt("id") + ",";
						}
					}
				}
			}

			if (!ids.equals("") && ids.indexOf(",") >= 0) {
				ids = ids.substring(0, ids.lastIndexOf(","));
				where_ids = " and t.id in (" + ids + ")";
			} else {
				where_ids = " and 1 != 1";
			}

			allsql = sqlBegin + sql + sqlcolumn + sqltablename + sqlwhere0
					+ sqlwhere_dep + sqlwhere + where_ids + sqlAppend
					+ sqlorder + sqlEnd;

			ps = ct.prepareStatement(allsql);

			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);

			rs = ps.executeQuery();
			while (rs.next()) {
				map = new HashMap<String, String>();
				for (int i = 0; i < list_tags.size(); i++) {
					if (list_tags.get(i).getDepartsearch_display() == 1) {
						if (list_tags.get(i).getColumn_type().equals("number")) {
							map.put(list_tags.get(i).getColumn_name(), String
									.valueOf(rs.getFloat(list_tags.get(i)
											.getColumn_name())));
						} else if (list_tags.get(i).getColumn_type().equals(
								"float")) {
							map.put(list_tags.get(i).getColumn_name(), String
									.valueOf(rs.getFloat(list_tags.get(i)
											.getColumn_name())));
						} else if (list_tags.get(i).getDisplay_type().equals(
								"相关字段")) {

							String returnvalue = "";
							String id = rs.getString(list_tags.get(i)
									.getColumn_name());// 获取相关字段值，序列id值
							if (id == null)
								continue;
							if (id.equals(""))
								continue;
							String str = list_tags.get(i).getDefault_value();
							String arr[] = str.split("==");// tb_mm_28==tb_mm_28_171==反反复复==varchar2(500)
							String sql_relate = " select " + arr[1] + " from "
									+ arr[0] + " where id in (" + id + ")";

							ps2 = ct2.prepareStatement(sql_relate);
							rs2 = ps2.executeQuery();
							while (rs2.next()) {
								returnvalue += rs2.getString(1);
								returnvalue += "<br>";
							}

							map.put(list_tags.get(i).getColumn_name(),
									returnvalue);

						} else if (list_tags.get(i).getDisplay_type().equals(
								"相关负责人")) {
							String returnvalue = "";
							String id = rs.getString(list_tags.get(i)
									.getColumn_name());// 获取相关字段值，序列id值
							if (id == null)
								continue;
							if (id.equals(""))
								continue;
							// String idvalues[]=id.split(",");
							String sql_relate = " select  realname "
									+ " from eluser where id in (" + id + ")";

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
					}// if
				}// for
				if (this.checkTable(tablename) == 2) {
					map.put("moduleid", rs.getString("moduleid"));
				}
				map.put("id", String.valueOf(rs.getInt("id")));
				map.put("status", rs.getString("status"));

				list.add(map);
			}// while
		} catch (Exception e) {
			logger.error("查看客户分析一览出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	public int select_designe_count_by_tablename(int entityid,
			List<Tags> list_tags, String tablename) throws ElException {
		int count = 0;

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;

		PreparedStatement ps3 = null;
		ResultSet rs3 = null;
		Connection ct3 = null;

		String sql_ = "";
		String ids = "";

		try {
			ct = DBConnection.getConnection();

			ct3 = DBConnection.getConnection();

			sql_ = "select * from " + tablename;
			ps3 = ct3.prepareStatement(sql_);
			rs3 = ps3.executeQuery();

			String where_ids = "";
			if (tablename.equals("LXXW")) {
				while (rs3.next()) {
					if (rs3.getString("LXXW_XGKH") != null
							&& !rs3.getString("LXXW_XGKH").equals("")) {
						if (checkIdIsIn(rs3.getString("LXXW_XGKH").split(","),
								entityid)) {
							ids += rs3.getInt("id") + ",";
						}
					}
				}
			} else if (tablename.equals("GRRZ")) {
				while (rs3.next()) {
					if (rs3.getString("GRRZ_XGKH") != null
							&& !rs3.getString("GRRZ_XGKH").equals("")) {
						if (checkIdIsIn(rs3.getString("GRRZ_XGKH").split(","),
								entityid)) {
							ids += rs3.getInt("id") + ",";
						}
					}
				}
			} else if (tablename.equals("XMDA")) {
				while (rs3.next()) {
					if (rs3.getString("XMDA_KH") != null
							&& !rs3.getString("XMDA_KH").equals("")) {
						if (checkIdIsIn(rs3.getString("XMDA_KH").split(","),
								entityid)) {
							ids += rs3.getInt("id") + ",";
						}
					}
				}
			} else if (tablename.equals("SK")) {
				while (rs3.next()) {
					if (rs3.getString("SK_KHMC") != null
							&& !rs3.getString("SK_KHMC").equals("")) {
						if (checkIdIsIn(rs3.getString("SK_KHMC").split(","),
								entityid)) {
							ids += rs3.getInt("id") + ",";
						}
					}
				}
			} else if (tablename.equals("FK")) {
				while (rs3.next()) {
					if (rs3.getString("FK_XGKH") != null
							&& !rs3.getString("FK_XGKH").equals("")) {
						if (checkIdIsIn(rs3.getString("FK_XGKH").split(","),
								entityid)) {
							ids += rs3.getInt("id") + ",";
						}
					}
				}
			} else if (tablename.equals("YS")) {
				while (rs3.next()) {
					if (rs3.getString("YS_KHMC") != null
							&& !rs3.getString("YS_KHMC").equals("")) {
						if (checkIdIsIn(rs3.getString("YS_KHMC").split(","),
								entityid)) {
							ids += rs3.getInt("id") + ",";
						}
					}
				}
			} else if (tablename.equals("YF")) {
				while (rs3.next()) {
					if (rs3.getString("YF_KHMC") != null
							&& !rs3.getString("YF_KHMC").equals("")) {
						if (checkIdIsIn(rs3.getString("YF_KHMC").split(","),
								entityid)) {
							ids += rs3.getInt("id") + ",";
						}
					}
				}
			} else if (tablename.equals("QTSR")) {
				while (rs3.next()) {
					if (rs3.getString("QTSR_KHMC") != null
							&& !rs3.getString("QTSR_KHMC").equals("")) {
						if (checkIdIsIn(rs3.getString("QTSR_KHMC").split(","),
								entityid)) {
							ids += rs3.getInt("id") + ",";
						}
					}
				}
			} else if (tablename.equals("FYZC")) {
				while (rs3.next()) {
					if (rs3.getString("FYZC_XGKH") != null
							&& !rs3.getString("FYZC_XGKH").equals("")) {
						if (checkIdIsIn(rs3.getString("FYZC_XGKH").split(","),
								entityid)) {
							ids += rs3.getInt("id") + ",";
						}
					}
				}
			}

			if (!ids.equals("") && ids.indexOf(",") >= 0) {
				ids = ids.substring(0, ids.lastIndexOf(","));
				where_ids = " and t.id in (" + ids + ")";
			} else {
				where_ids = " and 1 != 1";
			}

			String sql = " select count(*) from "
					+ tablename
					+ " "
					+ " t, eluser e   ,department d "
					+ "where userid in "
					+ " ("
					+ "   select id from eluser where valid=1 and t.userid=e.id  and  e.depid=d.id "
					+ " ) " + where_ids;
			ps = ct.prepareStatement(sql);

			rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取客户分析一览查看size出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}

	public void update_resulttable_producetable(String produce_table,
			String produce_column, String result_table, String result_column)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String result = "";
		try {
			ct = DBConnection.getConnection();

			sql = "select relatecolumns from resulttable_producetable where resulttable = '"
					+ result_table
					+ "' and producetable='"
					+ produce_table
					+ "'";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString(1) != null && !rs.getString(1).equals("")) {
					result = rs.getString(1);
				}
			}

			sql = "update resulttable_producetable set relatecolumns = ? where resulttable = '"
					+ result_table
					+ "' and producetable='"
					+ produce_table
					+ "'";
			ps = ct.prepareStatement(sql);
			ps.setString(1, result + result_column + "==" + produce_column
					+ ",");
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("插入结果表时，修改resulttable_producetable表中信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public CurrentUser getCurrentUser(String tablename) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		CurrentUser currentUser = null;
		try {
			ct = DBConnection.getConnection();

			sql = "select t.* from tb_user t  " + " where t.tablename=? ";
			ps = ct.prepareStatement(sql);
			ps.setString(1, tablename);
			rs = ps.executeQuery();
			if (rs.next()) {
				currentUser = new CurrentUser();
				currentUser.setUser_add(rs.getInt("user_add"));
				currentUser.setUser_update(rs.getInt("user_update"));
				currentUser.setUser_view(rs.getInt("user_view"));
			}
		} catch (Exception e) {
			logger.error("获取用户姓名和部门当前信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return currentUser;
	}

	public void updateTb_userByTablename(CurrentUser currentUser)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();

			sql = "update tb_user set user_add=?,user_update=?,user_view=? where tablename=? ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, currentUser.getUser_add());
			ps.setInt(2, currentUser.getUser_update());
			ps.setInt(3, currentUser.getUser_view());
			ps.setString(4, currentUser.getTablename());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("修改tb_user出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public CurrentUser getCurrentUserByUserId(CurrentUser currentUser,
			int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();

			sql = "select e.realname as name,d.name as depname,bt.basevalue as btbasevalue,bt1.basevalue as btbasevalue1  from eluser e,department d,basedatat bt,basedatat bt1 "
					+ " where e.depid=d.id and e.zhiwu=bt.id and e.dishi=bt1.id and e.id=? ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				currentUser = currentUser==null?new CurrentUser():currentUser;
				currentUser.setDepname(rs.getString("depname"));
				currentUser.setName(rs.getString("name"));
				//职务和地市
				currentUser.setZhiwuname(rs.getString("btbasevalue"));
				currentUser.setDishiname(rs.getString("btbasevalue1"));
				currentUser.setUserid(userid);
			}
		} catch (Exception e) {
			logger.error("根据userid获取用户姓名和部门出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return currentUser;
	}

	// public int getUserIdByUserId(int userid) throws ElException {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// String sql = "";
	// int UUID = 0;
	// try {
	// ct = DBConnection.getConnection();
	//
	// sql = "select * from eluser where userid= " + userid ;
	// ps = ct.prepareStatement(sql);
	// ps.setInt(1, userid);
	// rs = ps.executeQuery();
	// if(rs.next()){
	// if(rs.getString("depid") != null && !rs.getString("depid").equals("")){
	//					
	// }
	// }
	// } catch (Exception e) {
	// logger.error("查询三级节点出错！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return UUID;
	// }

	public List<Map<String, String>> select_my_tableinfo_by_dep_(
			List<Tags> list_tags, String tablename, Map<String, String> hm,
			ElNode department, String order, int pageNow, int pageSize)
			throws ElException {
		boolean flag_colume = true;
		Map<String, String> map = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;

		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		Connection ct2 = null;

		PreparedStatement ps3 = null;
		ResultSet rs3 = null;
		Connection ct3 = null;
		String sql3 = "";

		String sqlBegin = " SELECT * FROM ( " + " SELECT A.*, ROWNUM RN "
				+ " FROM ( ";
		String sql = " select  t.id,t.status ,e.username ,(select username from eluser where id=t.principalid) principalname ,d.name ";
		String sqlcolumn = "  ";
		String sqltablename = " from " + tablename
				+ " t, eluser e   ,department d ";
		String sqljoin = " join ("
				+ ((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
						.generateSQLByTree("department", department, true)
				+ ") dep on dep.id=d.id ";
		String sqlwhere0 = " where  userid in ";
		String sqlwhere_dep = " (select id from eluser where valid=1 ";

		// + " and depid in "
		// + " (select id from department where lid >= ? and rid <= ?) )";

		String sqlwhere = " and t.userid=e.id  and  e.depid=d.id   ";
		String sqlstatus = " and t.status='通过' ";
		String sqlwhere_end = " ) ";
		String sqlorder = "  order by id desc ";
		String sqlEnd = "    ) A " + " WHERE ROWNUM <= ? " + " )WHERE RN >= ? ";

		String allsql = "";
		String sqlAppend = "";

		String where_sql3 = "";

		if (order != null) {
			if (!order.equals("")) {
				sqlorder = order;
			}
		}

		// sqlcolumn
		for (int i = 0; i < list_tags.size(); i++) {

			if (list_tags.get(i).getDepartsearch_display() == 1) {
				// if (flag_colume)
				// {
				// flag_colume = false;
				// }
				// else
				// {
				// sqlcolumn += ",";
				// }

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
		if (this.checkTable(tablename) == 2) {
			sqlcolumn += " ,moduleid,danjuid ";
		}

		// sqlwhere
		Iterator iterator = hm.entrySet().iterator();
		while (iterator.hasNext()) {
			java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
			if (((String) entry.getKey()).equals("status")) {
				int kk = 0;
				if ("通过".equals((String) entry.getValue()))
					kk = 1;
				else if ("其他".equals((String) entry.getValue()))
					kk = 0;
				sqlAppend = " and shenhestatus = " + kk + " ";
			}
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

				// sqlwhere += " and "+str[3]+" in ( select id from "+str[1]+"
				// where "+str[2]+" like '"+(String) entry.getValue()+"%' )";
				sqlwhere += " and "
						+ str[3]
						+ " is not null and t.id in (select mainid from tb_tags_relate where relateid in "
						+ " ( select id from " + str[1] + " where " + str[2]
						+ " like '%" + (String) entry.getValue() + "%')) ";
			}
		}

		try {
			ct3 = DBConnection.getConnection();
			String ids = "";
			if (tablename.equals("SCRWS")) {
				sql3 = "select SCWG_XGRWD from SCWG ";
				ps3 = ct3.prepareStatement(sql3);
				rs3 = ps3.executeQuery();
				while (rs3.next()) {
					if (rs3.getString(1) != null
							&& !rs3.getString(1).equals("")) {
						ids += rs3.getString(1) + ",";
					}
				}
				if (!ids.equals("")) {
					ids = ids.substring(0, ids.lastIndexOf(","));
					where_sql3 = " and t.id in (" + ids + ") ";
				}

			} else if (tablename.equals("SCLL")) {
				sql3 = "select WWWG_XGWWD from WWWG ";
				ps3 = ct3.prepareStatement(sql3);
				rs3 = ps3.executeQuery();
				while (rs3.next()) {
					if (rs3.getString(1) != null
							&& !rs3.getString(1).equals("")) {
						ids += rs3.getString(1) + ",";
					}
				}
				if (!ids.equals("")) {
					ids = ids.substring(0, ids.lastIndexOf(","));
					where_sql3 = " and t.id in (" + ids + ") ";
				}
			}

			ct = DBConnection.getConnection();
			ct2 = DBConnection.getConnection();

			if (tablename.equals("SCWG") || tablename.equals("WWWG")) {
				allsql = sqlBegin + sql + sqlcolumn + sqltablename + sqljoin
						+ sqlwhere0 + sqlwhere_dep + sqlwhere + where_sql3
						+ sqlwhere_end + sqlAppend + sqlorder + sqlEnd;
			} else {
				allsql = sqlBegin + sql + sqlcolumn + sqltablename + sqljoin
						+ sqlwhere0 + sqlwhere_dep + sqlwhere + sqlstatus
						+ where_sql3 + sqlwhere_end + sqlAppend + sqlorder
						+ sqlEnd;
			}

			ps = ct.prepareStatement(allsql);

			// ps.setInt(1, department.getLid());
			// ps.setInt(2, department.getRid());
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);

			rs = ps.executeQuery();
			while (rs.next()) {
				map = new HashMap<String, String>();
				for (int i = 0; i < list_tags.size(); i++) {
					if (list_tags.get(i).getDepartsearch_display() == 1) {
						if (list_tags.get(i).getColumn_type().equals("number")) {
							// if (list_tags.get(i).getBiaojianqiuhe_check() !=
							// 1) {
							map.put(list_tags.get(i).getColumn_name(), String
									.valueOf(
											Math.ceil(rs.getFloat(list_tags
													.get(i).getColumn_name())))
									.equals("0.0") ? "" : String.valueOf(Math
									.ceil(rs.getFloat(list_tags.get(i)
											.getColumn_name()))));
							// }

						}
						if (list_tags.get(i).getColumn_type().equals("float")) {
							// if (list_tags.get(i).getBiaojianqiuhe_check() !=
							// 1) {
							map.put(list_tags.get(i).getColumn_name(), String
									.valueOf(
											rs.getFloat(list_tags.get(i)
													.getColumn_name())).equals(
											"0.0") ? "" : String
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
						/*
						 * else // string {
						 * map.put(list_tags.get(i).getColumn_name(), rs
						 * .getString(list_tags.get(i) .getColumn_name())); }
						 */
					}// if
				}// for
				if (this.checkTable(tablename) == 2) {
					map.put("moduleid", rs.getString("moduleid"));
				}
				map.put("id", String.valueOf(rs.getInt("id")));
				map.put("status", rs.getString("status"));
				// map.put("username", rs.getString("username"));
				// map.put("name", rs.getString("name"));
				// map.put("principalname", rs.getString("principalname"));

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

	public int select_my_tableinfo_by_dep_count_(List<Tags> list_tags,
			Map<String, String> hm, String tablename, Department department)
			throws ElException {
		int count = 0;

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;

		PreparedStatement ps3 = null;
		ResultSet rs3 = null;
		Connection ct3 = null;
		String sql3 = "";
		String where_sql3 = "";
		String sqlstatus = " and t.status = '通过' ";
		String sql = " select count(*) from "
				+ tablename
				+ " "
				+ " t, eluser e   ,department d "
				+ " join ("
				+ ((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
						.generateSQLByTree("department", department, true)
				+ ") dep on dep.id=d.id "
				+ "where userid in "
				+ " ("
				+ "   select id from eluser where valid=1 and t.userid=e.id  and  e.depid=d.id "
				// "and depid in "
				// + " ( select id from department where lid >= ? and rid <= ?)"
				+ " ) ";
		String sqlwhere = " ";
		try {

			ct3 = DBConnection.getConnection();
			String ids = "";
			if (tablename.equals("SCRWS")) {
				sql3 = "select SCWG_XGRWD from SCWG ";
				ps3 = ct3.prepareStatement(sql3);
				rs3 = ps3.executeQuery();
				while (rs3.next()) {
					if (rs3.getString(1) != null
							&& !rs3.getString(1).equals("")) {
						ids += rs3.getString(1) + ",";
					}
				}
				if (!ids.equals("")) {
					ids = ids.substring(0, ids.lastIndexOf(","));
					where_sql3 = " and t.id in (" + ids + ") ";
				}

			} else if (tablename.equals("SCLL")) {
				sql3 = "select WWWG_XGWWD from WWWG ";
				ps3 = ct3.prepareStatement(sql3);
				rs3 = ps3.executeQuery();
				while (rs3.next()) {
					if (rs3.getString(1) != null
							&& !rs3.getString(1).equals("")) {
						ids += rs3.getString(1) + ",";
					}
				}
				if (!ids.equals("")) {
					ids = ids.substring(0, ids.lastIndexOf(","));
					where_sql3 = " and t.id in (" + ids + ") ";
				}
			}

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
							+ " is not null and t.id in (select mainid from tb_tags_relate where relateid in "
							+ " ( select id from " + str[1] + " where "
							+ str[2] + " like '%" + (String) entry.getValue()
							+ "%')) ";
				}
			}

			if (tablename.equals("SCWG") || tablename.equals("WWWG")) {
				ps = ct.prepareStatement(sql + where_sql3 + sqlwhere);
			} else {
				ps = ct.prepareStatement(sql + where_sql3 + sqlwhere + sqlstatus);
			}
			
			
			// ps.setInt(1, department.getLid());
			// ps.setInt(2, department.getRid());

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

	public String getColumnByRelateColumnAndTable(String tablename,
			String columnName) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String result = "";
		String result_ = "";
		String sql = "";
		try {
//			ct = DBConnection.getConnection();
//			CallableStatement cs = ct.prepareCall("{call getyewu_jindu_columnname(?,?)}");  
//			cs.setString(1, tablename);
//			cs.registerOutParameter(2, oracle.jdbc.OracleTypes.VARCHAR);  
//			cs.execute(); 
//			result = cs.getString(2);
			ct = DBConnection.getConnection();
			sql = " select yewu_jindu_relate_id,column_name from tb_designe where table_name =  '"
					+ tablename + "' and yewu_jindu_relate_id is not null ";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getString(2);
			}
			
//			sql = " select column_name from tb_designe where id= '" + result + "'";
//			ps = ct.prepareStatement(sql);
//			rs = ps.executeQuery();
//			if(rs.next()){
//				result_ = rs.getString(1);
//				if(result_.equals(columnName)){
//					
//				}
//			}
		} catch (Exception e) {
			logger.error("根据相关字段获取与之关联的业务进度column出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	
	////////////////////////////////
	//自定义审核
	public void insert_tb_auditByTablename(String tablename, String sub_level,
			String sub_title, String sub_userid, String sub_bz)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " insert into tb_audit (tablename,auditOrder,auditName,auditUser,mark) " +
					" values (?,?,?,?,?) ";
			ps = ct.prepareStatement(sql);
			ps.setString(1, tablename);
			ps.setString(2, sub_level);
			ps.setString(3, sub_title);
			ps.setString(4, sub_userid);
			ps.setString(5, sub_bz);
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("根据表名插入自定义审核信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public void delete_audit_by_tablename(String tablename) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " delete from tb_audit where tablename = '" + tablename + "'";
			ps = ct.prepareStatement(sql);
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("插入自定义审核信息之前，删除存在的信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public List<CustomAudit> get_audits_by_tablename(String tablename)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		List<CustomAudit> list = new ArrayList<CustomAudit>();
		CustomAudit ca = null;
		try {
			ct = DBConnection.getConnection();
			sql = " select * from tb_audit where tablename = '" + tablename + "' order by auditorder asc";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				ca = new CustomAudit(rs.getString("audituser"));
				ca.setAuditOrder(rs.getString("auditorder"));
				ca.setAuditName(rs.getString("auditname"));
				ca.setAuditUser(rs.getString("audituser"));
				ca.setMark(rs.getString("mark"));
				list.add(ca);
			}
			
		} catch (Exception e) {
			logger.error("根据表名查询多级审核信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	/**
	 * 自定义审核列表
	 */
	public List<Map<String, String>> select_my_audits_by_dep(int roleid,CustomAudit ca,int userid,
			List<Tags> list_tags, String tablename, Map<String, String> hm,
			ElNode department, String order, int pageNow, int pageSize)
			throws ElException {
		Map<String, String> map = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;

		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		Connection ct2 = null;

		String sqlBegin = " SELECT * FROM ( " + " SELECT A.*, ROWNUM RN "
				+ " FROM ( ";
		String sql = " select o.*,e1.username as e1_username,d1.name as d1_name from " +
				" (select  t.id,t.status,t.audituserid,t.auditdepid,to_char(audittime, 'yyyy-mm-dd') audittime ,e.username, (select username from eluser where id=t.principalid) principalname ,d.name ";
		String sqlcolumn = "  ";
		String sqltablename = " from " + tablename
				+ " t, eluser e   ,department d  ";
		String sqljoin =  "";
		String sqlwhere0 = " where  userid in ";
		String sqlwhere_dep = " (select id from eluser where valid=1 ";

		String sqlwhere = " and t.userid=e.id  and  e.depid=d.id ) ";
		String sqlorder = "  order by id desc  ";
		String sqleluser_dep = " ) o left join eluser e1 on o.audituserid=e1.id left join department d1 on o.auditdepid=d1.id ";
		String sqlEnd = "    ) A " + " WHERE ROWNUM <= ? " + " )WHERE RN >= ? ";

		String allsql = "";
		String sqlAppend = "";
		
		String sqlAudit = "";//审核状态
		
		
		if(roleid != 1){
		}

		if (order != null) {
			if (!order.equals("")) {
				sqlorder = order;
			}
		}

		sqlcolumn += TagsUtil.getSqlColumns(3, list_tags);
		if (this.checkTable(tablename) == 2) {
			sqlcolumn += " ,moduleid,danjuid ";
		}

		sqlwhere += TagsUtil.getSqlWhere(hm);

		try {
			ct = DBConnection.getConnection();
			ct2 = DBConnection.getConnection();

			allsql = sqlBegin + sql + sqlcolumn + sqltablename + sqljoin
					+ sqlwhere0 + sqlwhere_dep + sqlwhere + sqlAppend + sqlAudit 
					+ sqlorder + sqleluser_dep + sqlEnd;

			ps = ct.prepareStatement(allsql);

			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);

			rs = ps.executeQuery();
			while (rs.next()) {
				map = new HashMap<String, String>();
				
				ct2 = DBConnection.getConnection();
				map = TagsUtil.getOneData(2,list_tags, map, rs, ct2, rs2, ps2);
				DBConnection.closeConnectInfo(ct2, ps2, rs2);
				
				if (this.checkTable(tablename) == 2) {
					map.put("moduleid", rs.getString("moduleid"));
				}
				map.put("id", String.valueOf(rs.getInt("id")));
				map.put("status", rs.getString("status"));
				map.put("e1_username", rs.getString("e1_username")==null?"":rs.getString("e1_username"));
				map.put("d1_name", rs.getString("d1_name")==null?"":rs.getString("d1_name"));
				map.put("audittime", rs.getString("audittime")==null?"":rs.getString("audittime"));

				list.add(map);
			}// while
		} catch (Exception e) {
			logger.error("自定义审核列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	/**
	 * 自定义审核SIZE
	 */
	public int select_my_audits_by_dep_count(int roleid,CustomAudit ca,int userid,List<Tags> list_tags,
			Map<String, String> hm, String tablename, Department department)
			throws ElException {
		int count = 0;

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;

		String sql = " select count(*) from "
				+ tablename
				+ " "
				+ " t, eluser e   ,department d "
				+ "where userid in "
				+ " ("
				+ "   select id from eluser where valid=1 and t.userid=e.id  and  e.depid=d.id "
				+ " )  ";
		String sqlrole = "";
		String sqlAudit = "";//审核状态
		if(roleid != 1){
		}
		String sqlwhere = " ";
		
		
		try {
			ct = DBConnection.getConnection();

			// sqlwhere
			sqlwhere += TagsUtil.getSqlWhere(hm);

			ps = ct.prepareStatement(sql + sqlwhere + sqlAudit);

			rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("查询自定义审核SIZE出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}

	public String get_audit_by_auditOrderAndTablename(String auditOrder,String tablename) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String result = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select * from tb_audit where tablename = '" + tablename + "' and auditorder = '" + auditOrder + "'";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()){
				result = rs.getString("audituser");
			}
			
		} catch (Exception e) {
			logger.error("根据表名和序号查询多级审核信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public CustomAudit get_audit_by_tablename(String tablename, int id)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		CustomAudit ca = null;
		try {
			ct = DBConnection.getConnection();
			String ordersc = "";
			if(id == 1){
				ordersc = " asc ";
			}else if(id == 2){
				ordersc = " desc ";
			}
			sql = " select a.*,rownum rn from (select * from tb_audit where tablename = '" + tablename + "' order by auditorder "+ordersc+") a where rownum<=1";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()){
				ca = new CustomAudit(rs.getString("audituser"));
				ca.setAuditOrder(rs.getString("auditorder"));
				ca.setAuditName(rs.getString("auditname"));
				ca.setAuditUser(rs.getString("audituser"));
				ca.setMark(rs.getString("mark"));
			}
			
		} catch (Exception e) {
			logger.error("查询最小审核级别或最大审核级别出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ca;
	}

	public int get_status_by_tablename_id(String tablename, int id)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int result = 0;
		try {
			ct = DBConnection.getConnection();
			sql = " select * from "+tablename+" where id = " + id ;
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()){
				result = rs.getInt("status");
			}
			
		} catch (Exception e) {
			logger.error("根据数据id获取数据状态出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}
	
	public int getApplicationByIdAndTablename(int moduleid,int userid, int id)
		throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int result = 0;
		try {
			ct = DBConnection.getConnection();
			sql = " select status from tb_data_allocation where moduleid=? and userid=? and entityid=? ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, moduleid);
			ps.setInt(2, userid);
			ps.setInt(3, id);
			rs = ps.executeQuery();
			if(rs.next()){
				result = rs.getInt("status");
			}
			
		} catch (Exception e) {
			logger.error("根据数据id获取数据状态出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return result;
	}

	public boolean checkUserInAudit(String tablename, int userid,String auditOrder)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		boolean flag = false;
		String uid = "";
		String[] uid_array = null;
		try {
			ct = DBConnection.getConnection();
			if(auditOrder == null){
				sql = " select audituser from tb_audit where tablename = '" + tablename + "'" ;
			}else{
				sql = " select audituser from tb_audit where tablename = '" + tablename + "' and auditorder = '" + auditOrder + "'" ;
			}
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				if(rs.getString(1) != null && !rs.getString(1).equals(""))	{
					uid_array = rs.getString(1).split(",");
					for(int i=0;i<uid_array.length;i++){
						uid = uid_array[i];
						if(Integer.parseInt(uid) == userid)		flag = true;
					}
				}
			}
			
		} catch (Exception e) {
			logger.error("根据当前用户id判断是否有审核权限出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flag;
	}

	public CustomAudit get_audit_by_tablename_userid(String tablename,
			int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		CustomAudit ca = null;
		String uid = "";
		String[] uid_array = null;
		try {
			ct = DBConnection.getConnection();
			sql = " select * from tb_audit where tablename = '" + tablename + "'";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				uid = rs.getString("audituser");
				if(uid != null && !uid.equals("")){
					uid_array = uid.split(",");
					for(int i=0;i<uid_array.length;i++){
						if(Integer.parseInt(uid_array[i]) == userid){
							ca = new CustomAudit(uid_array[i]);
							ca.setAuditOrder(rs.getString("auditorder"));
							ca.setAuditName(rs.getString("auditname"));
							ca.setAuditUser(uid_array[i]);
							ca.setMark(rs.getString("mark"));
							break;
						}
					}
				}
			}
			
		} catch (Exception e) {
			logger.error("根据表名和用户id获取审核信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ca;
	}

	public List<AuditMark> select_audit_mark_by_entityid(String tablename,
			int id,int pageNow,int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		List<AuditMark> list = new ArrayList<AuditMark>();
		AuditMark am = null;
		List<CustomAudit> cas =  this.get_audits_by_tablename(tablename);
		try {
			ct = DBConnection.getConnection();
			sql = "select b.*,rn from " +
					" (select a.* ,rownum rn from " +
					" (select tam.id,tam.moduleid,tam.entityid,tam.auditmark,tam.time,tam.status,tam.ord," +
					" t.audituserid,t.auditdepid,username from tb_audit_mark tam," + tablename + " t,eluser e " +
					" where   t.id=tam.entityid and t.id="+id+" and e.id=t.audituserid " +
							" order by time desc ) a where rownum <=?) b where rn>=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			
			int status = 0;
			while(rs.next()){
				am = new AuditMark();
				am.setId(rs.getInt("id"));
				am.setModuleid(rs.getString("moduleid"));
				am.setEntityid(rs.getInt("entityid"));
				am.setAudit_mark(rs.getString("auditmark"));
				am.setUsername(rs.getString("username"));
				am.setAudittime(rs.getTimestamp("time"));
				am.setAuditName_chinese(rs.getString("ord"));
				status = rs.getInt("status");
				am.setStatus(status);
				
				if(status == 0){am.setStatus_chinese("已创建");}
				if(status == 2){am.setStatus_chinese("修改等待中");}
				if(status == 3){am.setStatus_chinese("删除等待中");}
				if(status == 5){am.setStatus_chinese("初审等待中");}
				if(status == 6){am.setStatus_chinese("初审通过");}
				if(status == 7){am.setStatus_chinese("初审不通过");}
				if(status == 8){am.setStatus_chinese("终审等待中");}
				if(status == 9){am.setStatus_chinese("终审通过");}
				if(status == 10){am.setStatus_chinese("终审不通过");}
				for(int i=0;i<cas.size();i++){
					if(rs.getInt("status") == Integer.parseInt(cas.get(i).getAuditOrder())*2 + 10){
						am.setStatus_chinese(cas.get(i).getAuditName() + "通过");
//						am.setAuditName_chinese(cas.get(i).getAuditName());
					}
					if(rs.getInt("status") == Integer.parseInt(cas.get(i).getAuditOrder())*2 + 10 + 1){
						am.setStatus_chinese(cas.get(i).getAuditName() + "不通过");
//						am.setAuditName_chinese(cas.get(i).getAuditName());
					}
				}
				
				list.add(am);
			}
			
		} catch (Exception e) {
			logger.error("根据表名查询多级审核信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}
	
	public int select_audit_mark_size_by_entityid(String tablename,
			int id,int pageNow,int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int count = 0;
		try {
			ct = DBConnection.getConnection();
			sql = " select count(1) from tb_audit_mark tam," + tablename + " t,eluser e " +
					" where   t.id=tam.entityid and t.id="+id+" and e.id=t.audituserid ";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
			
		} catch (Exception e) {
			logger.error("根据表名查询多级审核信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}

	public boolean check_auditOrderIsExist(String tablename, String auditOrder)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		boolean flag = false;
		try {
			ct = DBConnection.getConnection();
			sql = " select auditorder from tb_audit where tablename = '" + tablename + "' and auditorder = '" + auditOrder + "'";

			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()){
				flag = true;
			}
		} catch (Exception e) {
			logger.error("验证审核级别是否已经存在出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flag;
	}
	
	public void deleteauditOrderUserId(String tablename, String auditOrder,int userid)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String[] userids = null;
		String value = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select audituser from tb_audit where tablename = '" + tablename + "' and auditorder = '" + auditOrder + "'";
		
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()){
				userids = rs.getString(1).split(",");
				for(int i=0;i<userids.length;i++){
					if(userid != Integer.parseInt(userids[i])){
						value += userids[i] + ","; 
					}
				}
			}
			if(value != null && !value.equals("")){
				value = value.substring(0,value.lastIndexOf(","));
			}
			
			sql = "update tb_audit set audituser = '" + value + "' where tablename = '" + tablename + "' and auditorder = '" + auditOrder + "'";
			ps = ct.prepareStatement(sql);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除审核用户出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	
	public double getTotalValueByColumn(String formula,String tablename) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		double d = 0;
		try {
			ct = DBConnection.getConnection();
			sql = " select " + formula + " from " + tablename ;
		
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()){
				d = rs.getDouble(1);
			}
		} catch (Exception e) {
			logger.error("获取sum出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return d;
	}
	
	
	////////////////////////////////
	public String checkRelateIdInInTable(String columnname,String ids,Object id)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String value = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select relateid from tb_tags_relate where columnname = '"+columnname+"' and mainid= " + Integer.parseInt(String.valueOf(id)) ;
			if(ids !=null&&!ids.equals("")){
				String[] ids_ = ids.split(",");
				for(int i=0;i<ids_.length;i++){
					ps = ct.prepareStatement(sql);
					rs = ps.executeQuery();
					if(rs.next()){
						value += rs.getInt(1)+",";
					}
				}
				
			}
			if(value!=null&&!value.equals("")&&String.valueOf(value.charAt(value.length()-1)).equals(",")){
				value = value.substring(0,value.lastIndexOf(","));
			}
			
		} catch (Exception e) {
			logger.error("获取sum出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return value;
	}
	
	
	public String returnIds(CustomReportJSZ customReportJSZ,Map<String,String> searchMap,String tablename) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String ids = "";
		int id  = 0;
		String value = "";
		
		String sql1 = "";
		Connection ct1 = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		
		try {
			ct1 = DBConnection.getConnection();
			ct = DBConnection.getConnection();
			sql1 = "select id from " + tablename;
			ps1 = ct1.prepareStatement(sql1);
			rs1 = ps1.executeQuery();
			while(rs1.next()){
				double money = 0;
				id = rs1.getInt(1);
				sql = "select " + customReportJSZ.getRelatecolumnname() + " ," + customReportJSZ.getFormula().split("\\.")[1] + " from " + customReportJSZ.getRelatecolumnname().split("_")[0] ;
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
				if(searchMap.get(customReportJSZ.getFormula())!=null&&!searchMap.get(customReportJSZ.getFormula()).equals("")){//收款开始
					if(searchMap.get(customReportJSZ.getFormula()+"_")!=null&&!searchMap.get(customReportJSZ.getFormula()+"_").equals("")){//收款结束
						if(money>=Double.parseDouble(searchMap.get(customReportJSZ.getFormula())) && money<=Double.parseDouble(searchMap.get(customReportJSZ.getFormula()+"_"))){
							ids  += id + ",";
						}
					}else {
						if(money>=Double.parseDouble(searchMap.get(customReportJSZ.getFormula()))){
							ids  += id + ",";
						}
					}
				}
				else if(searchMap.get(customReportJSZ.getFormula()+"_")!=null&&!searchMap.get(customReportJSZ.getFormula()+"_").equals("")){//收款开始
					if(searchMap.get(customReportJSZ.getFormula())!=null&&!searchMap.get(customReportJSZ.getFormula()).equals("")){//收款结束
						if(money>=Double.parseDouble(searchMap.get(customReportJSZ.getFormula())) && money<=Double.parseDouble(searchMap.get(customReportJSZ.getFormula()+"_"))){
							ids  += id + ",";
						}
					}else {
						if(money<=Double.parseDouble(searchMap.get(customReportJSZ.getFormula()+"_"))){
							ids  += id + ",";
						}
					}
				}
				
			}
			
			
		} catch (Exception e) {
			logger.error("获取相关统计符合条件出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ids;
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
	
	public List<CustomReportJSZ> childrenCustomReportJSZList(CustomReportJSZ customReportJSZ,String tablename) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<CustomReportJSZ> jszs = new ArrayList<CustomReportJSZ>();
		CustomReportJSZ jsz = null;
		String sql = "";
		String sqlwhere = " where ";
		//SK.SK_SKJE+KHDA.KHDA_FTCB-FK.FK_FKJE
		//获取相关统计字段数组
		
		
		String[] array = ScheduleUtil.getRelatetype2FieldsOrInsideTableFields(customReportJSZ.getFormula(),tablename,2);
		
		if(array!=null&&array.length>0){
			for(int i=0;i<array.length;i++){
				if(array[i]!=null&&!array[i].equals("")){
					if(i == array.length-1){
						sqlwhere += "  formula = '" + array[i] + "' "; 
					}else{
						sqlwhere += "  formula = '" + array[i] + "' or "; 
					}
				}
			}
		}
		
		try {
			ct = DBConnection.getConnection();
			
			sql = " select t.*, t.rowid from customreport_jisuanzu t " + sqlwhere;
			
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				jsz = new CustomReportJSZ();
				jsz.setId(rs.getInt("id"));
				jsz.setCustomreportid(rs.getInt("customreportid"));
				jsz.setColumnname(rs.getString("columnname"));
				jsz.setFormula(rs.getString("formula"));
				jsz.setType(rs.getInt("type"));
				jsz.setFormatnumber(rs.getInt("formatnumber"));
				jsz.setViewjindutiao(rs.getInt("viewjindutiao"));
				jsz.setShowview(rs.getInt("showview"));
				jsz.setRelatetype(rs.getInt("relatetype"));
				jsz.setRelatecolumnname(rs.getString("relatecolumnname"));
				jsz.setOrderid(rs.getInt("orderid"));
				jszs.add(jsz);
			}
		} catch (Exception e) {
			logger.error("获取计算组出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return jszs;
	}
	
	public String returnIds_total(String sql,CustomReportJSZ customReportJSZ,Map<String,String> searchMap,String tablename,Map<String,Object> m) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String ids = "";
		//sum(GZJH_ZWPF)/total(GZJH_ZWPF)
		
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		Connection ct1 = null;
		String sql1 = "";
		String sqlwhere1 = "";
		int id = 0;
		
		double value = 0;
		
		try {
			ct1 = DBConnection.getConnection();
			ct = DBConnection.getConnection();
			
			sql1 = "select * from " + tablename;
			
			ps1 = ct.prepareStatement(sql1);
			rs1 = ps1.executeQuery();
			while(rs1.next()){
				id = rs1.getInt("id");
				String formula = customReportJSZ.getFormula();
				for(String key:m.keySet()){
					if(key.indexOf(OperatorUtil.TOTAL_OPERATOR.toUpperCase())!=-1){
						if(formula != null && !formula.equals("")){
							String[] array = OperatorUtil.getOperatorField_(formula, 2);
							for(int i=0;i<array.length;i++){
								if(formula.indexOf(OperatorUtil.TOTAL_OPERATOR)!=-1){
									int index = formula.indexOf(OperatorUtil.TOTAL_OPERATOR);
									if(index !=-1){
										String before = formula.substring(0,index);
										String end = formula.substring(index,formula.length());//total()
										//total()=>338
										end  = end.replace(end.substring(0,end.indexOf(")")+1), String.valueOf(m.get(key)));
										formula = before + end;
									}
								}
								if(formula.indexOf(OperatorUtil.TOTAL_OPERATOR)==-1){
									formula = formula.replace(OperatorUtil.getOperatorField_return(array[i]), m.get(array[i])!=null&&!(String.valueOf(m.get(array[i]))).equals("")
											?String.valueOf(m.get(array[i])):"0");
								}
								break;
							}
						}
					}
				}
				
				value = Double.parseDouble(JisuanzuUtil.computeString(formula));
				
				if(searchMap.get(customReportJSZ.getFormula()) != null && !searchMap.get(customReportJSZ.getFormula()).equals("")){//利润开始
					if(searchMap.get(customReportJSZ.getFormula()+"_") != null && !searchMap.get(customReportJSZ.getFormula()+"_").equals("")){//利润结束
						if(value>=Double.parseDouble(searchMap.get(customReportJSZ.getFormula())) && value<=Double.parseDouble(searchMap.get(customReportJSZ.getFormula()+"_"))){
							ids += id + ",";
						}
					}else {
						if(value>=Double.parseDouble(searchMap.get(customReportJSZ.getFormula()))){
							ids += id + ",";
						}
					}
				}else if(searchMap.get(customReportJSZ.getFormula()+"_") != null && !searchMap.get(customReportJSZ.getFormula()+"_").equals("")){//利润结束
					if(searchMap.get(customReportJSZ.getFormula()) != null && !searchMap.get(customReportJSZ.getFormula()).equals("")){//利润开始
						if(value>=Double.parseDouble(searchMap.get(customReportJSZ.getFormula())) && value<=Double.parseDouble(searchMap.get(customReportJSZ.getFormula()+"_"))){
							ids += id + ",";
						}
					}else {
						if(value<=Double.parseDouble(searchMap.get(customReportJSZ.getFormula()+"_"))){
							ids += id + ",";
						}
					}
				}
			}
			
			if(ids!=null&&!ids.equals("")&&(String.valueOf(ids.charAt(ids.length()-1)).equals(","))){
				ids = ids.substring(0,ids.lastIndexOf(","));
				
				String sql_begin = sql.split("where 1=1")[0];
				String sql_end = sql.split("where 1=1")[1];
				
				sql = sql_begin + " where 1=1 " + " and "+tablename + ".id in (" + ids + ")" + " " + sql_end;
			}
		} catch (Exception e) {
			logger.error("获取相关统计符合条件出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		return sql;
	}
	
	public String returnIds_relatetype3(CustomReportJSZ customReportJSZ,Map<String,String> searchMap,String tablename) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String ids = "";
		
		String sql1 = "";
		Connection ct1 = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		
		int id = 0;
		
		List<CustomReportJSZ> childrenCustomReportJSZList = null;
		Map<String,Double> m = new HashMap<String,Double>();
		
		if(customReportJSZ.getFormula()!=null&&!customReportJSZ.getFormula().equals(""))
			childrenCustomReportJSZList = childrenCustomReportJSZList(customReportJSZ,tablename);
		try {
			//SK.SK_SKJE+KHDA.KHDA_FTCB-FK.FK_FKJE
			
			String[] array = ScheduleUtil.getRelatetype2FieldsOrInsideTableFields(customReportJSZ.getFormula(),tablename,1);
			
			ct1 = DBConnection.getConnection();
			ct = DBConnection.getConnection();
			sql1 = "select * from " + tablename;
			ps1 = ct1.prepareStatement(sql1);
			rs1 = ps1.executeQuery();
			while(rs1.next()){
				double value = 0;
				id = rs1.getInt("id");
				if(childrenCustomReportJSZList!=null&&childrenCustomReportJSZList.size()>0){
					String tablename_ = "";
					String column1 = "";
					String column2 = "";
					for(CustomReportJSZ crJSZ:childrenCustomReportJSZList){
						tablename_ = crJSZ.getFormula().split("\\.")[0];
						column1 = crJSZ.getRelatecolumnname();
						column2 = crJSZ.getFormula().split("\\.")[1];
						value = getMapByTableName(column1,column2,tablename_,id);
						m.put(crJSZ.getFormula(), value);
					}
				}
				
				//value还要加上计算组中本表中的某些数字字段
				String inside_column = "";
				if(array!=null&&array.length>0){
					for(int i=0;i<array.length;i++){
						inside_column = array[i].split("\\.")[1];
						value = rs1.getDouble(inside_column);
						m.put(array[i], value);
					}
				}
				
				//计算值
				value = JisuanzuUtil.jisuan(customReportJSZ.getFormula(),m);
				
				if(searchMap.get(customReportJSZ.getFormula()) != null && !searchMap.get(customReportJSZ.getFormula()).equals("")){//利润开始
					if(searchMap.get(customReportJSZ.getFormula()+"_") != null && !searchMap.get(customReportJSZ.getFormula()+"_").equals("")){//利润结束
						if(value>=Double.parseDouble(searchMap.get(customReportJSZ.getFormula())) && value<=Double.parseDouble(searchMap.get(customReportJSZ.getFormula()+"_"))){
							ids += id + ",";
						}
					}else {
						if(value>=Double.parseDouble(searchMap.get(customReportJSZ.getFormula()))){
							ids += id + ",";
						}
					}
				}else if(searchMap.get(customReportJSZ.getFormula()+"_") != null && !searchMap.get(customReportJSZ.getFormula()+"_").equals("")){//利润结束
					if(searchMap.get(customReportJSZ.getFormula()) != null && !searchMap.get(customReportJSZ.getFormula()).equals("")){//利润开始
						if(value>=Double.parseDouble(searchMap.get(customReportJSZ.getFormula())) && value<=Double.parseDouble(searchMap.get(customReportJSZ.getFormula()+"_"))){
							ids += id + ",";
						}
					}else {
						if(value<=Double.parseDouble(searchMap.get(customReportJSZ.getFormula()+"_"))){
							ids += id + ",";
						}
					}
				}
			}
			
		} catch (Exception e) {
			logger.error("获取相关统计符合条件出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ids;
	}

	public void insert_tb_tags_mark(TagsMark tagsMark) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " insert into tb_tags_mark (tablename,columnname,relates,relates_info) values(?,?,?,?)";

			ps = ct.prepareStatement(sql);
			ps.setString(1, tagsMark.getTablename());
			ps.setString(2, tagsMark.getColumnname());
			ps.setString(3, tagsMark.getRelates());
			ps.setString(4, tagsMark.getRelates_info());
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("自定义字段添加的时候插入备注表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	public void update_tb_tags_mark(TagsMark tagsMark) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "{call update_tb_tags_mark(?,?,?,?)}";

			ps = ct.prepareStatement(sql);
			ps.setString(1, tagsMark.getTablename());
			ps.setString(2, tagsMark.getColumnname());
			ps.setString(3, tagsMark.getRelates());
			ps.setString(4, tagsMark.getRelates_info());
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("修改自定义字段备注信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public List<SelectLevel> getSelectLevelById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		SelectLevel selectLevel = null;
		List<SelectLevel> list = new ArrayList<SelectLevel>();
		try {
			ct = DBConnection.getConnection();
			sql = "select * from selectlevel where parentid=?";

			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while(rs.next()){
				selectLevel = new SelectLevel(rs.getInt("id"),rs.getString("name"));
				list.add(selectLevel);
			}

		} catch (Exception e) {
			logger.error("下级节点出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	public void insert_into_tb_pic(String tablename, Map<String, String> map,int entityid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlcolumn="";
		String sqlvalues = "";
		
		try {
			Iterator iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {

				java.util.Map.Entry entry = (java.util.Map.Entry) iterator
						.next();
				// entry.getKey() 返回与此项对应的键
				// entry.getValue() 返回与此项对应的值
				if(String.valueOf((String) entry.getKey()).contains("===")){
					String str1[] = ((String) entry.getKey()).split("===");
					sqlcolumn += str1[0].split("\\.")[0];
					sqlvalues += map.get((String) entry.getKey()).replace("===", ",");
					
					ct = DBConnection.getConnection();
					sql = " insert into tb_pic (tablename,columnname,entityid,width,height)" +
							" values ('"+tablename+"','"+sqlcolumn+"',"+entityid+","+sqlvalues+") ";
					System.out.println(sql);
					ps = ct.prepareStatement(sql);
					ps.executeUpdate();
					DBConnection.closeConnectInfo(ct, ps, rs);
				}
			}
			
			
		} catch (Exception e) {
			logger.error("插入到tb_pic表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public void update_tb_pic(String tablename, Map<String, String> map,
			int entityid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlcolumn="";
		String sqlvalues[] = {"",""};
		
		try {
			Iterator iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {

				java.util.Map.Entry entry = (java.util.Map.Entry) iterator
						.next();
				// entry.getKey() 返回与此项对应的键
				// entry.getValue() 返回与此项对应的值
				if(String.valueOf((String) entry.getKey()).contains("===")){
					String str1[] = ((String) entry.getKey()).split("===");
					sqlcolumn += str1[0].split("\\.")[0];
					String[] ss = map.get((String) entry.getKey()).split("===");
					if(ss!=null){
						sqlvalues[0] = (ss[0]==null||ss[0].equals(""))?"0":ss[0];
						sqlvalues[1] = (ss[1]==null||ss[1].equals(""))?"0":ss[1];
					}
					
					ct = DBConnection.getConnection();
					sql = " update tb_pic set width=? ,height=? where tablename='"+tablename+"' and columnname='"+sqlcolumn+"' and entityid="+entityid+" ";
					System.out.println(sql);
					ps = ct.prepareStatement(sql);
					ps.setInt(1, Integer.parseInt(sqlvalues[0]));
					ps.setInt(2, Integer.parseInt(sqlvalues[1]));
					ps.executeUpdate();
					DBConnection.closeConnectInfo(ct, ps, rs);
				}
			}
			
			
		} catch (Exception e) {
			logger.error("修改tb_pic表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	
	


}
