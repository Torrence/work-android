package com.cwq.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class AuctionUser implements Serializable {
	//标识属性
	private Integer id;
	//用户名属性
	private String username;
	//密码属性
	private String userpass;
	//电子邮件属性
	private String email;

	//根据属主关联的物品实体
	private Set<Item> itemsByOwner = new HashSet<Item>();
	//根据赢取者关联的物品实体
	private Set<Item> itemsByWiner = new HashSet<Item>();
	//该用户所参与的全部竞价
	private Set<Bid> bids = new HashSet<Bid>();
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserpass() {
		return userpass;
	}
	public void setUserpass(String userpass) {
		this.userpass = userpass;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Set<Item> getItemsByOwner() {
		return itemsByOwner;
	}
	public void setItemsByOwner(Set<Item> itemsByOwner) {
		this.itemsByOwner = itemsByOwner;
	}
	public Set<Item> getItemsByWiner() {
		return itemsByWiner;
	}
	public void setItemsByWiner(Set<Item> itemsByWiner) {
		this.itemsByWiner = itemsByWiner;
	}
	public Set<Bid> getBids() {
		return bids;
	}
	public void setBids(Set<Bid> bids) {
		this.bids = bids;
	}
	
    
}
