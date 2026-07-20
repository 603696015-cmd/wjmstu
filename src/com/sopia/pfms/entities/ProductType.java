package com.sopia.pfms.entities;

import java.util.List;

import com.sopia.common.ElNode;
import com.sopia.duman.entities.ELUser;

public class ProductType extends ElNode{
	private int id;
	private String name;
	private String description;
	private List<ProductType> child;
	private List<Product> products;//普通产品
	private List<BaoxianProduct> baoxianProducts;//保险产品
	private Integer isshared; //是否共享节点
	private List<ELUser> opusers;//管理权限用户
	private List<ELUser> useusers; //使用权限用户
	
	public ProductType(){
		
	}
	
	public ProductType(int id,String name) {
		try{
			this.id = id;
			this.name = name;
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public List<BaoxianProduct> getBaoxianProducts() {
		return baoxianProducts;
	}

	public void setBaoxianProducts(List<BaoxianProduct> baoxianProducts) {
		this.baoxianProducts = baoxianProducts;
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



	public List<ProductType> getChild() {
		return child;
	}

	public void setChild(List<ProductType> child) {
		this.child = child;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Integer getIsshared() {
		return isshared;
	}

	public void setIsshared(Integer isshared) {
		this.isshared = isshared;
	}

	public List<ELUser> getOpusers() {
		return opusers;
	}

	public void setOpusers(List<ELUser> opusers) {
		this.opusers = opusers;
	}

	public List<ELUser> getUseusers() {
		return useusers;
	}

	public void setUseusers(List<ELUser> useusers) {
		this.useusers = useusers;
	}
	

	
	

}
