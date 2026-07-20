package com.sopia.wordman.entities;

import java.sql.Timestamp;
import java.util.List;

import com.sopia.questionman.entities.StuffLib;

public class Vocabulary {

	private int id;
	private String name;
	private int adduserid;			//添加人ID
	private int alteruserid;		//修改人ID
	private Timestamp addtime;		//添加时间
	private Timestamp altertime;	//修改时间
	private int wordid;			//词汇库ID
	private String pinyin;			//拼音
	private String duyin;			//读音
	private String wenzijieshi;		//文字解释
	private String shengyinjieshi;	//声音解释
	private String wenziliju; //文字例句
	private String lijulangdu;	//例句朗读
	private int status;    // 0:未审核   1：通过
	private String lijudizhi;//例句地址
	private Word word;
	private List<StuffLib> stuffs;//附件
	private StuffLib stuff;
	private int childid;
	private String yingwen;//英文
	public String getYingwen() {
		return yingwen;
	}
	public void setYingwen(String yingwen) {
		this.yingwen = yingwen;
	}
	public StuffLib getStuff() {
		return stuff;
	}
	public void setStuff(StuffLib stuff) {
		this.stuff = stuff;
	}
	public List<StuffLib> getStuffs() {
		return stuffs;
	}
	public void setStuffs(List<StuffLib> stuffs) {
		this.stuffs = stuffs;
	}
	public Word getWord() {
		return word;
	}
	public void setWord(Word word) {
		this.word = word;
	}
	public Vocabulary() {
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Vocabulary(int id){
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAdduserid() {
		return adduserid;
	}
	public void setAdduserid(int adduserid) {
		this.adduserid = adduserid;
	}
	public int getAlteruserid() {
		return alteruserid;
	}
	public void setAlteruserid(int alteruserid) {
		this.alteruserid = alteruserid;
	}
	public Timestamp getAddtime() {
		return addtime;
	}
	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}
	public Timestamp getAltertime() {
		return altertime;
	}
	public void setAltertime(Timestamp altertime) {
		this.altertime = altertime;
	}
	public int getWordid() {
		return wordid;
	}
	public void setWordid(int wordid) {
		this.wordid = wordid;
	}
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	public String getDuyin() {
		return duyin;
	}
	public void setDuyin(String duyin) {
		this.duyin = duyin;
	}
	public String getWenzijieshi() {
		return wenzijieshi;
	}
	public void setWenzijieshi(String wenzijieshi) {
		this.wenzijieshi = wenzijieshi;
	}
	public String getShengyinjieshi() {
		return shengyinjieshi;
	}
	public void setShengyinjieshi(String shengyinjieshi) {
		this.shengyinjieshi = shengyinjieshi;
	}
	public String getWenziliju() {
		return wenziliju;
	}
	public void setWenziliju(String wenziliju) {
		this.wenziliju = wenziliju;
	}
	public String getLijulangdu() {
		return lijulangdu;
	}
	public void setLijulangdu(String lijulangdu) {
		this.lijulangdu = lijulangdu;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getLijudizhi() {
		return lijudizhi;
	}
	public void setLijudizhi(String lijudizhi) {
		this.lijudizhi = lijudizhi;
	}
	public int getChildid() {
		return childid;
	}
	public void setChildid(int childid) {
		this.childid = childid;
	}
}
