package com.sopia.schedule;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sopia.schedule.entities.TBPicture;
import com.sopia.schedule.entities.Tags;

/**
 * 自定义字段的管理
 * @author Administrator
 *
 */
public class TagsColumnUtil {
	//字段类型
	public static final String[] FIELDTYPES = new String[]
	              {"文本","实数","日期","大文本","整数",
	              "下拉选项","附件上传","图片","富文本","相关字段",
	              "相关负责人","百分比","单选","复选","城市",
	              "当前用户信息","分级下拉选项","音频"};
	
	public static final String WENBEN = "文本";
	public static final String SHISHU = "实数";
	public static final String RIQI = "日期";
	public static final String DAWENBEN = "大文本";
	public static final String ZHENGSHU = "整数";
	
	public static final String XIALAXUANXIANG = "下拉选项";
	public static final String FUJIANSHANGCHUAN = "附件上传";
	public static final String TUPIAN = "图片";
	public static final String FUWENBEN = "富文本";
	public static final String XIANGGUANZIDUAN = "相关字段";
	
	public static final String XIANGGUANFUZEREN = "相关负责人";
	public static final String BAIFENBI = "百分比";
	public static final String DANXUAN = "单选";
	public static final String FUXUAN = "复选";
	public static final String CHENGSHI =  "城市";
	public static final String DANGQIANYONGHUXINXI = "当前用户信息";
	public static final String FENJIXIALAXUANXIANG = "分级下拉选项";
	public static final String YINPIN = "音频";
	
//////////////////////////////////对单个字段处理
	
	
	
	
//////////////////////////////////
	
	
	/**
	 * 显示或者修改的时候数据库中值放入tags的value、value2
	 * @param tags
	 * @param rs
	 * @param ct2
	 * @param rs2
	 * @param ps2
	 * @return
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public static Tags tagsSetValue(Tags tags,ResultSet rs,Connection ct2,ResultSet rs2,PreparedStatement ps2,int id) throws SQLException, IOException{
		if (tags.getDisplay_type().equals("图片")) {
			String picUrl = rs.getString(tags.getColumn_name());
			tags.setValue(picUrl);
			if(picUrl !=null && !picUrl.equals("")){
				TBPicture pic = null;
				ps2 = ct2.prepareStatement("select width,height from tb_pic where tablename=? and columnname=? and entityid=?");
				ps2.setString(1, tags.getTable_name());
				ps2.setString(2, tags.getColumn_name());
				ps2.setInt(3, id);
				rs = ps2.executeQuery();
				if (rs.next()) {
					pic = new TBPicture();
					pic.setColumnname(tags.getColumn_name());
					pic.setTablename(tags.getTable_name());
					pic.setId(id);
					pic.setWidth(rs.getInt(1));
					pic.setHeight(rs.getInt(2));
				}
				tags.setPic(pic);
			}
		}else if (tags.getDisplay_type().equals("富文本")) {
			java.sql.Blob blob = rs.getBlob(tags.getColumn_name());
			if (blob != null) {
				InputStream inStream = blob.getBinaryStream();
				byte[] data;
				data = new byte[(int) blob.length()];
				inStream.read(data);
				inStream.close();
				tags.setValue(new String(data));
			}
		}else if (tags.getDisplay_type().equals("相关字段")) {
			String returnvalue = "";
			String ids = rs.getString(tags.getColumn_name());// 获取相关字段值，序列id值
			ids = (ids == null||ids.equals(""))?"":ids;
			if (ids != null && !ids.equals("")){
				String str = tags.getDefault_value();
				String arr[] = str.split("==");// tb_mm_28==tb_mm_28_171==反反复复==varchar2(500)
				String sql_relate = " select id," + arr[1] + " from "
						+ arr[0] + " where id in (" + ids + ")";

				ps2 = ct2.prepareStatement(sql_relate);
				rs = ps2.executeQuery();
				ids = "";
				while (rs.next()) {
					ids += rs.getInt("id");
					returnvalue += rs.getString(2);
					returnvalue += ";";
					ids += ",";

				}
				if (returnvalue.length() == 0)
					returnvalue = "";
				else
					returnvalue = returnvalue.substring(0, returnvalue
							.length() - 1);
				if (ids.length() == 0)
					ids = "";
				else
					ids = ids.substring(0, ids.length() - 1);
			}
			tags.setValue(returnvalue);
			tags.setValue2(ids);

		} else if (tags.getDisplay_type().equals("相关负责人")) {
			String returnvalue = "";
			String ids = rs.getString(tags.getColumn_name());// 获取相关字段值，序列id值
			ids = (ids == null||ids.equals(""))?"":ids;
			if (ids != null && !ids.equals("")){
				String sql_relate = " select id,realname from eluser where id in ("
					+ ids + ")";

				ps2 = ct2.prepareStatement(sql_relate);
				rs = ps2.executeQuery();
				ids = "";
				while (rs.next()) {
					ids += rs.getInt("id");
					returnvalue += rs.getString(2);
					returnvalue += ";";
					ids += ",";
	
				}
				if (returnvalue.length() == 0)
					returnvalue = "";
				else
					returnvalue = returnvalue.substring(0, returnvalue
							.length() - 1);
				if (ids.length() == 0)
					ids = "";
				else
					ids = ids.substring(0, ids.length() - 1);
			}
			tags.setValue(returnvalue);
			tags.setValue2(ids);
		}else if(tags.getDisplay_type().equals("实数")){
			tags.setValue(String.valueOf(rs.getFloat(tags.getColumn_name())));
		}
		else {
			tags.setValue(rs.getString(tags.getColumn_name()));
		}
		return tags;
	}
	
	/**
	 * 根据字段类型将数据库中数据放入map
	 * @param map
	 * @param tags
	 * @param rs
	 * @param ct2
	 * @param rs2
	 * @param ps2
	 * @return
	 * @throws SQLException
	 */
	public static Map<String,String> putToMapFromDbByDisplayType(Map<String,String> map,Tags tags,ResultSet rs,Connection ct2,ResultSet rs2,PreparedStatement ps2) throws SQLException{
		if(tags.getDisplay_type().equals("相关字段")){
			String returnvalue = "";
			String id = rs.getString(tags
					.getColumn_name());// 获取相关字段值，序列id值
			if (id != null && !id.equals("")){
				String str = tags
				.getDefault_value();
				String arr[] = str.split("==");// tb_mm_28==tb_mm_28_171==反反复复==varchar2(500)
				String sql_relate = " select " + arr[1]
				+ " from " + arr[0] + " where id in ("
				+ id + ")";

				ps2 = ct2.prepareStatement(sql_relate);
				rs2 = ps2.executeQuery();
				while (rs2.next()) {
					returnvalue += rs2.getString(1);
					returnvalue += "<br>";
				}
			}
			map.put(tags.getColumn_name(),
					returnvalue);
		}else if(tags.getDisplay_type().equals("相关负责人")){
			String returnvalue = "";
			String id = rs.getString(tags
					.getColumn_name());// 获取相关字段值，序列id值
			if (id != null && !id.equals("")){
				String sql_relate = " select  realname "
					+ " from eluser where id in (" + id
					+ ")";

				ps2 = ct2.prepareStatement(sql_relate);
				rs2 = ps2.executeQuery();
				while (rs2.next()) {
					returnvalue += rs2.getString(1);
					returnvalue += "<br>";
				}
			}
			map.put(tags.getColumn_name(),
					returnvalue);
		}else if(tags.getDisplay_type().equals("实数")){
			map.put(tags.getColumn_name(), String.valueOf(rs.getFloat(tags.getColumn_name())));
		}else{
			map.put(tags.getColumn_name(), rs.getString(tags.getColumn_name()));
		}
		return map;
	}
	
	/**
	 * 将一种字段类型的数据加入到map，前台添加
	 * @param tags
	 * @param request
	 * @param hm
	 * @return
	 */
	public static Map<String,String> addOneColumnValueToDb(Tags tags,HttpServletRequest request,Map<String,String> hm){
		if (tags.getDisplay_type().equals("附件上传")) {
			String str = (String) request.getParameter(
					tags.getColumn_name());
			String str2 = (String) request.getParameter(
					tags.getColumn_name() + "_");

			if (str2 != null && !str2.equals("")) {
				if (str == null || str.equals(""))
					str = "";
				hm.put(tags.getColumn_type() + "=="+ tags.getColumn_name(), str + "=="+ str2);
			}
		}else if (tags.getDisplay_type().equals("音频")) {
			String str = (String) request.getParameter(
					tags.getColumn_name());
			String str2 = (String) request.getParameter(
					tags.getColumn_name() + "_");

			if (str2 != null && !str2.equals("")) {
				if (str == null || str.equals(""))
					str = "";
				hm.put(tags.getColumn_type() + "=="+ tags.getColumn_name(), str + "=="+ str2);
			}
		} 
		else if (tags.getDisplay_type().equals("图片")) {
			
//			String height = (String) request.getParameter(
//					tags.getColumn_name() + "_h");
//			String width = (String) request.getParameter(
//					tags.getColumn_name() + "_w");
//			String addr = (String) request.getParameter(
//					tags.getColumn_name());
//
//			if (height != null)
//				if (height.equals(""))
//					height = "0";
//			if (width != null)
//				if (width.equals(""))
//					width = "0";
//
//			if (addr != null && !addr.equals("")) {
//				hm.put(tags.getColumn_type() + "=="+ tags.getColumn_name(), height+ "==" + width + "==" + addr);
//			}
			//修改为只保存图片路径
			String addr = (String) request.getParameter(
					tags.getColumn_name());
			if (addr != null && !addr.equals("")) {
				hm.put(tags.getColumn_type() + "=="+ tags.getColumn_name(),  addr);
				String height = (String) request.getParameter(
						tags.getColumn_name() + "_h");
				String width = (String) request.getParameter(
						tags.getColumn_name() + "_w");
				if (height != null)
					if (height.equals(""))
						height = "0";
				if (width != null)
					if (width.equals(""))
						width = "0";
				hm.put(tags.getColumn_name()+".width"+"==="+tags.getColumn_name()+".height", width+"==="+height);
			}

		} else if (tags.getDisplay_type().equals("相关字段")) {
			String str = (String) request.getParameter(
					"relate_" + tags.getId() + "");
			if (str != null) {
				String values[] = str.split("__-__");
				String idvalues = "";
				if (values != null) {
					for (int j = 0; j < values.length; j++) {
						String tmp[] = values[j].split("==");
						idvalues += tmp[0];
						if (j + 1 != values.length) {
							idvalues += ",";
						}
					}
				}

				if (idvalues != null && !str.equals(""))
					hm.put("relate" + "=="+ tags.getColumn_name(),idvalues);
			}

		} else if (tags.getDisplay_type().equals("相关负责人")) {
			String str = (String) request.getParameter(
					"relate_" + tags.getId() + "");
			if (str != null) {
				String values[] = str.split("__-__");
				String idvalues = "";
				if (values != null) {
					for (int j = 0; j < values.length; j++) {
						String tmp[] = values[j].split("==");
						idvalues += tmp[0];
						if (j + 1 != values.length) {
							idvalues += ",";
						}
					}
				}
				if (idvalues != null && !str.equals(""))
					hm.put("relate" + "=="+ tags.getColumn_name(),idvalues);
			}

		} else if(tags.getDisplay_type().equals("复选")){
			String[] str = request.getParameterValues(
					tags.getColumn_name());
			String str1 = "";
			if (str != null) {
				for (int x = 0; x < str.length; x++) {
					if (x == str.length - 1)
						str1 += str[x];
					else
						str1 += str[x] + ",";
				}
				hm.put(tags.getDisplay_type() + "=="+ tags.getColumn_name(), str1);
			}
		}else if(tags.getDisplay_type().equals("日期")){
			String str = (String) request.getParameter(
					tags.getColumn_name());
			if (str != null && !str.equals(""))
				hm.put(tags.getColumn_type() + "=="+ tags.getColumn_name()+"=="+(tags.getTimeformat()==null?"":tags.getTimeformat()), str);
		}else{
			String str = (String) request.getParameter(
					tags.getColumn_name());
			if (str != null && !str.equals(""))
				hm.put(tags.getColumn_type() + "=="+ tags.getColumn_name(), str);
			
		}
		return hm;
	}
	/**
	 * 获取列、值等信息
	 * @param hm
	 * @param sqlvalues
	 * @param sqlcolumn
	 * @param list_columnname
	 * @param list_content
	 * @param list_idvalues
	 * @param list_relatecolumn
	 * @return
	 */
	public static Map<String,Object> getSqlColumns_sqlValues(Map<String,String> hm,String sqlvalues,String sqlcolumn,List<String> list_columnname,List<String> list_content,List<String> list_idvalues,List<String> list_relatecolumn,boolean fromExcel){
		Iterator iterator = hm.entrySet().iterator();
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> pic = null;
		while (iterator.hasNext()) {
			java.util.Map.Entry entry = (java.util.Map.Entry) iterator
					.next();
			// entry.getKey() 返回与此项对应的键
			// entry.getValue() 返回与此项对应的值
			if(!String.valueOf((String) entry.getKey()).contains("===")){
				sqlvalues += ",";
				sqlcolumn += ",";
				String str[] = ((String) entry.getKey()).split("==");
				sqlcolumn += str[1];
				if (str[0].equals("复选")) {
					sqlvalues += " '" + (String) entry.getValue() + "' ";
				}
				if (str[1] != null && str[0].equals("date")) {
					if(fromExcel){//来自导入Excel
						sqlvalues += " to_date('" + TagsUtil.outExcel((String) entry.getValue(), "yyyy-MM-dd","yyyy-MM-dd")
						+ "','yyyy-mm-dd hh24:mi:ss') ";
					}else{//页面添加
						sqlvalues += " to_date('" + (String) entry.getValue()
						+ "','yyyy-mm-dd hh24:mi:ss') ";
					}
				} else if (str[0].equals("number") || str[0].equals("float")) {
					sqlvalues += " " + (String) entry.getValue() + " ";
				} else if (str[0].indexOf("varchar2") > -1
						&& !str[0].equals("复选")) {
					sqlvalues += " '" + (String) entry.getValue() + "' ";
				}
				if (str[1] != null && str[0].equals("relate")) {
					sqlvalues += " '" + (String) entry.getValue() + "' ";
					list_idvalues.add((String) entry.getValue());
					list_relatecolumn.add(str[1]);
				} else if (str[0].equals("blob")) {
					sqlvalues += "  empty_blob() ";
					list_columnname.add(str[1]);
					list_content.add((String) entry.getValue());
				}
			}
		}
		map.put("sqlvalues", sqlvalues);
		map.put("sqlcolumn", sqlcolumn);
		map.put("list_content", list_content);
		map.put("list_columnname", list_columnname);
		map.put("list_idvalues", list_idvalues);
		map.put("list_relatecolumn", list_relatecolumn);
		return map;
	}
	
	
	///////////////////////
	//添加页面、修改页面、查看页面的自定义标签解析成HTML
	public static void resolveAddInfoHTML(){
		
	}
	
	
	//////////////////////
	
	
}
