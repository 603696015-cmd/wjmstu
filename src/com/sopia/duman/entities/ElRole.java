package com.sopia.duman.entities;

import java.util.List;

import com.sopia.ElConstants;
import com.sopia.common.SystemConfOp;

public class ElRole {
	private int id;
	private String name;
	private String description;
	private List<ElFunc> funcs;
	private ELUser creater;
	
	private String beijingimg;//背景图片
	private String tishiyu;//提示语
	private String common1;//常用1
	private String common2;//常用2
	private String common3;//常用3
	private String common4;//常用4
	private String common5;//常用5
	private String common6;//常用6
	
	private ElFunc func_common1;
	private ElFunc func_common2;
	private ElFunc func_common3;
	private ElFunc func_common4;
	private ElFunc func_common5;
	private ElFunc func_common6;
	
	
	public ElFunc getFunc_common1() {
		return func_common1;
	}

	public void setFunc_common1(ElFunc func_common1) {
		this.func_common1 = func_common1;
	}

	public ElFunc getFunc_common2() {
		return func_common2;
	}

	public void setFunc_common2(ElFunc func_common2) {
		this.func_common2 = func_common2;
	}

	public ElFunc getFunc_common3() {
		return func_common3;
	}

	public void setFunc_common3(ElFunc func_common3) {
		this.func_common3 = func_common3;
	}

	public ElFunc getFunc_common4() {
		return func_common4;
	}

	public void setFunc_common4(ElFunc func_common4) {
		this.func_common4 = func_common4;
	}

	public ElFunc getFunc_common5() {
		return func_common5;
	}

	public void setFunc_common5(ElFunc func_common5) {
		this.func_common5 = func_common5;
	}

	public ElFunc getFunc_common6() {
		return func_common6;
	}

	public void setFunc_common6(ElFunc func_common6) {
		this.func_common6 = func_common6;
	}

	public String getMaining(){
		return SystemConfOp.getValue(ElConstants.STUFF_URL) + beijingimg;
	}
	
	public String getBeijingimg() {
		return beijingimg;
	}
	public void setBeijingimg(String beijingimg) {
		this.beijingimg = beijingimg;
	}
	public String getTishiyu() {
		return tishiyu;
	}
	public void setTishiyu(String tishiyu) {
		this.tishiyu = tishiyu;
	}
	public String getCommon1() {
		return common1;
	}
	public void setCommon1(String common1) {
		this.common1 = common1;
	}
	public String getCommon2() {
		return common2;
	}
	public void setCommon2(String common2) {
		this.common2 = common2;
	}
	public String getCommon3() {
		return common3;
	}
	public void setCommon3(String common3) {
		this.common3 = common3;
	}
	public String getCommon4() {
		return common4;
	}
	public void setCommon4(String common4) {
		this.common4 = common4;
	}
	public String getCommon5() {
		return common5;
	}
	public void setCommon5(String common5) {
		this.common5 = common5;
	}
	public String getCommon6() {
		return common6;
	}
	public void setCommon6(String common6) {
		this.common6 = common6;
	}
	public ELUser getCreater() {
		return creater;
	}
	public void setCreater(ELUser creater) {
		this.creater = creater;
	}
	public ElRole() {
	}
	public ElRole(int id){
		this.id = id;
	}
	public ElRole(int id,String name) {
		this.id = id;
		this.name=name;
	}
	
	public List<ElFunc> getFuncs() {
		return funcs;
	}
	public void setFuncs(List<ElFunc> funcs) {
		this.funcs = funcs;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
