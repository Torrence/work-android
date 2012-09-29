package com.cwq.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Kind implements Serializable {

	//标识属性，对应数据表的逻辑主键
	private Integer id;
	//种类名
	private String kindName;
	//种类描述
	private String kindDesc;
	//该种类下的所有物品
	private Set<Item> items = new HashSet<Item>();
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getKindName() {
		return kindName;
	}
	public void setKindName(String kindName) {
		this.kindName = kindName;
	}
	public String getKindDesc() {
		return kindDesc;
	}
	public void setKindDesc(String kindDesc) {
		this.kindDesc = kindDesc;
	}
	public Set<Item> getItems() {
		return items;
	}
	public void setItems(Set<Item> items) {
		this.items = items;
	}
	

}
