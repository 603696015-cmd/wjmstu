package com.sopia.schedule.entities;

import java.util.List;

import com.sopia.schedule.entities.xialajibie.SelectLevel;

public class Tags {
	private int id	;// number
	private String column_name	;// 	varchar2(50)
	private String column_type	;// 	varchar2(20)
	private String table_name	;// 	varchar2(50)
	private String name_display		;// varchar2(100)
	private int add_display	;// 	number
	private int update_display		;// number
	private int view_display		;// number
	private int list_display		;// number
	private int mutilsearch_display	;// 	number
	private int sn		;// number
	private String display_type		;// varchar2(50)
	private String default_value	;// 	varchar2(1000)
	private int cannot_modify;
	private int departsearch_display ;
	private int required;
	private int sum_display;
	
	private String value;//当前值 
	private String value2;
	private int columnid ;//查询单独数据表时的id值，固定存在一个list的第一个元素里

	private String  principalname ;
	private String username;
	private String department;
	private String status;//已创建，审核通过，审核不通过
	
	private String default_value_2;
	private float sum_f;
	private	int sum_i;
	
	private String ordercolumn;
	private String ordersc;
	
	private String downloadcontrol;
	private String mark;
	
	private int jindutiao;//是否显示进度条
	private int relateIsShowComplete;//是否完整显示
	private int is_calculate;//是否可以模块间计算
	private int show_time_jindu;//是否显示时间进度
	
	private int is_qiuji;//是否用于求积
	private int is_qiuhe;//是否用于求和
	private int zuowei_ji;//是否作为积
	private int zuowei_he;//是否作为和
	private String qiuji_column_name;//求积关联字段
	private String qiuhe_column_name;//求和关联字段
	
	private String time_jindu_ids;//时间进度来自那两个字段的id
	private String yewu_jindu_ids;//时间俩字段+进度条字段
	private String yewu_jindu_relate_id;//业务进度关联列的来源字段
	private String yewu_jindu_relate_begintime;//本表开始时间id
	private String yewu_jindu_relate_endtime;//本表结束时间id
	
	/**
	 * 表间计算
	 */
	private String jisuan_relate_type;//增加或者减少或者不增减关系
	private String jisuan_relate_id;//本表相关字段id
	private String jisuan_produce_table_name;//过程表
	private String jisuan_produce_relate_id;//过程表对应的字段
	private String jisuan_result_table_name;//结果表
	private String jisuan_result_relate_id;//结果表对应字段
	
	private String wanzheng;//完整显示显示的字段
	
	private String fromResultTable;//字段由结果表复制而来
	
	private int biaojianqiuhe_check ;
	private String biaojianqiuhe_tablename;//表间求和关联过程表
	private String biaojianqiuhe_column;//表间求和关联表中字段
	
	
	private int writible;//不限，即初审终审都可写
	private int isAutoPlay;//是否自动播放 20130918wkm修改
	
	private int is_judge;//相关字段是否进行权限判断
	private int is_judge_for_user;//相关联系人字段是否进行权限判断
	
	private String fromtablename_columnname;//自动读取来源字段
	
	private int showfinalpass;//终审通过显示
	
	private String timeformat;//日期字段时间格式
	
	private String columnTBHTMLName;//列的中文标签
	private String columnTBHTML;//列的HTML标签
	
	private TagsMark tagsMark;//字段备注信息
	private String wanzheng_mark;//完整备注
	
	private int selectlevelid;//起始节点
	private int jibieshu;//级别数
	private SelectLevel selectLevel;
	private List<SelectLevel> selectLevelList;
	
	private TBPicture pic;
	

	public TBPicture getPic() {
		return pic;
	}
	public void setPic(TBPicture pic) {
		this.pic = pic;
	}
	public List<SelectLevel> getSelectLevelList() {
		return selectLevelList;
	}
	public void setSelectLevelList(List<SelectLevel> selectLevelList) {
		this.selectLevelList = selectLevelList;
	}
	public int getJibieshu() {
		return jibieshu;
	}
	public void setJibieshu(int jibieshu) {
		this.jibieshu = jibieshu;
	}
	public SelectLevel getSelectLevel() {
		return selectLevel;
	}
	public void setSelectLevel(SelectLevel selectLevel) {
		this.selectLevel = selectLevel;
	}
	public int getSelectlevelid() {
		return selectlevelid;
	}
	public void setSelectlevelid(int selectlevelid) {
		this.selectlevelid = selectlevelid;
	}
	public String getWanzheng_mark() {
		return wanzheng_mark;
	}
	public void setWanzheng_mark(String wanzheng_mark) {
		this.wanzheng_mark = wanzheng_mark;
	}
	public TagsMark getTagsMark() {
		return tagsMark;
	}
	public void setTagsMark(TagsMark tagsMark) {
		this.tagsMark = tagsMark;
	}
	public String getColumnTBHTMLName() {
		return columnTBHTMLName;
	}
	public void setColumnTBHTMLName(String columnTBHTMLName) {
		this.columnTBHTMLName = columnTBHTMLName;
	}
	public String getColumnTBHTML() {
		return columnTBHTML;
	}
	public void setColumnTBHTML(String columnTBHTML) {
		this.columnTBHTML = columnTBHTML;
	}
	public String getTimeformat() {
		return timeformat;
	}
	public void setTimeformat(String timeformat) {
		this.timeformat = timeformat;
	}
	public int getIs_judge_for_user() {
		return is_judge_for_user;
	}
	public void setIs_judge_for_user(int is_judge_for_user) {
		this.is_judge_for_user = is_judge_for_user;
	}
	public int getShowfinalpass() {
		return showfinalpass;
	}
	public void setShowfinalpass(int showfinalpass) {
		this.showfinalpass = showfinalpass;
	}
	public String getFromtablename_columnname() {
		return fromtablename_columnname;
	}
	public void setFromtablename_columnname(String fromtablename_columnname) {
		this.fromtablename_columnname = fromtablename_columnname;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public int getIs_judge() {
		return is_judge;
	}
	public void setIs_judge(int is_judge) {
		this.is_judge = is_judge;
	}
	public int getWritible() {
		return writible;
	}
	public void setWritible(int writible) {
		this.writible = writible;
	}
	public int getBiaojianqiuhe_check() {
		return biaojianqiuhe_check;
	}
	public void setBiaojianqiuhe_check(int biaojianqiuhe_check) {
		this.biaojianqiuhe_check = biaojianqiuhe_check;
	}
	public String getBiaojianqiuhe_tablename() {
		return biaojianqiuhe_tablename;
	}
	public void setBiaojianqiuhe_tablename(String biaojianqiuhe_tablename) {
		this.biaojianqiuhe_tablename = biaojianqiuhe_tablename;
	}
	public String getBiaojianqiuhe_column() {
		return biaojianqiuhe_column;
	}
	public void setBiaojianqiuhe_column(String biaojianqiuhe_column) {
		this.biaojianqiuhe_column = biaojianqiuhe_column;
	}
	public String getFromResultTable() {
		return fromResultTable;
	}
	public void setFromResultTable(String fromResultTable) {
		this.fromResultTable = fromResultTable;
	}
	public String getWanzheng() {
		return wanzheng;
	}
	public void setWanzheng(String wanzheng) {
		this.wanzheng = wanzheng;
	}
	public String getJisuan_relate_type() {
		return jisuan_relate_type;
	}
	public void setJisuan_relate_type(String jisuan_relate_type) {
		this.jisuan_relate_type = jisuan_relate_type;
	}
	public String getJisuan_produce_table_name() {
		return jisuan_produce_table_name;
	}
	public void setJisuan_produce_table_name(String jisuan_produce_table_name) {
		this.jisuan_produce_table_name = jisuan_produce_table_name;
	}
	public String getJisuan_result_table_name() {
		return jisuan_result_table_name;
	}
	public void setJisuan_result_table_name(String jisuan_result_table_name) {
		this.jisuan_result_table_name = jisuan_result_table_name;
	}
	public String getJisuan_relate_id() {
		return jisuan_relate_id;
	}
	public void setJisuan_relate_id(String jisuan_relate_id) {
		this.jisuan_relate_id = jisuan_relate_id;
	}
	public String getJisuan_produce_relate_id() {
		return jisuan_produce_relate_id;
	}
	public void setJisuan_produce_relate_id(String jisuan_produce_relate_id) {
		this.jisuan_produce_relate_id = jisuan_produce_relate_id;
	}
	public String getJisuan_result_relate_id() {
		return jisuan_result_relate_id;
	}
	public void setJisuan_result_relate_id(String jisuan_result_relate_id) {
		this.jisuan_result_relate_id = jisuan_result_relate_id;
	}
	public String getYewu_jindu_relate_begintime() {
		return yewu_jindu_relate_begintime;
	}
	public void setYewu_jindu_relate_begintime(String yewu_jindu_relate_begintime) {
		this.yewu_jindu_relate_begintime = yewu_jindu_relate_begintime;
	}
	public String getYewu_jindu_relate_endtime() {
		return yewu_jindu_relate_endtime;
	}
	public void setYewu_jindu_relate_endtime(String yewu_jindu_relate_endtime) {
		this.yewu_jindu_relate_endtime = yewu_jindu_relate_endtime;
	}
	public String getYewu_jindu_relate_id() {
		return yewu_jindu_relate_id;
	}
	public void setYewu_jindu_relate_id(String yewu_jindu_relate_id) {
		this.yewu_jindu_relate_id = yewu_jindu_relate_id;
	}
	public String getYewu_jindu_ids() {
		return yewu_jindu_ids;
	}
	public void setYewu_jindu_ids(String yewu_jindu_ids) {
		this.yewu_jindu_ids = yewu_jindu_ids;
	}
	public String getTime_jindu_ids() {
		return time_jindu_ids;
	}
	public void setTime_jindu_ids(String time_jindu_ids) {
		this.time_jindu_ids = time_jindu_ids;
	}
	public String getQiuji_column_name() {
		return qiuji_column_name;
	}
	public void setQiuji_column_name(String qiuji_column_name) {
		this.qiuji_column_name = qiuji_column_name;
	}
	public String getQiuhe_column_name() {
		return qiuhe_column_name;
	}
	public void setQiuhe_column_name(String qiuhe_column_name) {
		this.qiuhe_column_name = qiuhe_column_name;
	}
	public int getIs_qiuji() {
		return is_qiuji;
	}
	public void setIs_qiuji(int is_qiuji) {
		this.is_qiuji = is_qiuji;
	}
	public int getIs_qiuhe() {
		return is_qiuhe;
	}
	public void setIs_qiuhe(int is_qiuhe) {
		this.is_qiuhe = is_qiuhe;
	}
	public int getZuowei_ji() {
		return zuowei_ji;
	}
	public void setZuowei_ji(int zuowei_ji) {
		this.zuowei_ji = zuowei_ji;
	}
	public int getZuowei_he() {
		return zuowei_he;
	}
	public void setZuowei_he(int zuowei_he) {
		this.zuowei_he = zuowei_he;
	}
	public int getShow_time_jindu() {
		return show_time_jindu;
	}
	public void setShow_time_jindu(int show_time_jindu) {
		this.show_time_jindu = show_time_jindu;
	}
	public int getRelateIsShowComplete() {
		return relateIsShowComplete;
	}
	public void setRelateIsShowComplete(int relateIsShowComplete) {
		this.relateIsShowComplete = relateIsShowComplete;
	}
	public int getIs_calculate() {
		return is_calculate;
	}
	public void setIs_calculate(int is_calculate) {
		this.is_calculate = is_calculate;
	}
	public int getJindutiao() {
		return jindutiao;
	}
	public void setJindutiao(int jindutiao) {
		this.jindutiao = jindutiao;
	}
	public String getDownloadcontrol()
	{
		return downloadcontrol;
	}
	public void setDownloadcontrol(String downloadcontrol)
	{
		this.downloadcontrol = downloadcontrol;
	}
	public String getOrdercolumn()
	{
		return ordercolumn;
	}
	public void setOrdercolumn(String ordercolumn)
	{
		this.ordercolumn = ordercolumn;
	}
	public String getOrdersc()
	{
		return ordersc;
	}
	public void setOrdersc(String ordersc)
	{
		this.ordersc = ordersc;
	}
	public float getSum_f()
	{
		return sum_f;
	}
	public void setSum_f(float sum_f)
	{
		this.sum_f = sum_f;
	}
	public int getSum_i()
	{
		return sum_i;
	}
	public void setSum_i(int sum_i)
	{
		this.sum_i = sum_i;
	}
	public String getDefault_value_2()
	{
		return default_value_2;
	}
	public void setDefault_value_2(String default_value_2)
	{
		this.default_value_2 = default_value_2;
	}
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getDepartment()
	{
		return department;
	}
	public void setDepartment(String department)
	{
		this.department = department;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public int getColumnid() {
		return columnid;
	}
	public void setColumnid(int columnid) {
		this.columnid = columnid;
	}
	//-----------------
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getColumn_name() {
		return column_name;
	}
	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}
	public String getColumn_type() {
		return column_type;
	}
	public void setColumn_type(String column_type) {
		this.column_type = column_type;
	}
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public String getName_display() {
		return name_display;
	}
	public void setName_display(String name_display) {
		this.name_display = name_display;
	}
	public int getAdd_display() {
		return add_display;
	}
	public void setAdd_display(int add_display) {
		this.add_display = add_display;
	}
	public int getUpdate_display() {
		return update_display;
	}
	public void setUpdate_display(int update_display) {
		this.update_display = update_display;
	}
	public int getView_display() {
		return view_display;
	}
	public void setView_display(int view_display) {
		this.view_display = view_display;
	}
	public int getList_display() {
		return list_display;
	}
	public void setList_display(int list_display) {
		this.list_display = list_display;
	}
	public int getMutilsearch_display() {
		return mutilsearch_display;
	}
	public void setMutilsearch_display(int mutilsearch_display) {
		this.mutilsearch_display = mutilsearch_display;
	}
	public int getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = Integer.valueOf(sn);
	}
	public void setSn(int sn) {
		this.sn = sn;
	}
	public String getDisplay_type() {
		return display_type;
	}
	public void setDisplay_type(String display_type) {
		this.display_type = display_type;
	}
	public String getDefault_value() {
		return default_value;
	}
	public void setDefault_value(String default_value) {
		this.default_value = default_value;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	public int getCannot_modify()
	{
		return cannot_modify;
	}
	public void setCannot_modify(int cannot_modify)
	{
		this.cannot_modify = cannot_modify;
	}
	public int getDepartsearch_display()
	{
		return departsearch_display;
	}
	public void setDepartsearch_display(int departsearch_display)
	{
		this.departsearch_display = departsearch_display;
	}
	public int getRequired()
	{
		return required;
	}
	public void setRequired(int required)
	{
		this.required = required;
	}
	public String getPrincipalname()
	{
		return principalname;
	}
	public void setPrincipalname(String principalname)
	{
		this.principalname = principalname;
	}
	public int getSum_display()
	{
		return sum_display;
	}
	public void setSum_display(int sum_display)
	{
		this.sum_display = sum_display;
	}
	public int getIsAutoPlay() {
		return isAutoPlay;
	}
	public void setIsAutoPlay(int isAutoPlay) {
		this.isAutoPlay = isAutoPlay;
	}
	
}
