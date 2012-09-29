package com.cwq.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Item implements Serializable {
	private Integer id;
	private String itemRemark;
	private String itemName;
	private String itemDesc;
	private Date addtime;
	private Date endtime;
	private double initPrice;
	private double maxPrice;
	private AuctionUser owner;
	private Kind kind;
	private AuctionUser winer;
	private State itemState;
	private Set<Bid> bids = new HashSet<Bid>();
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getItemRemark() {
		return itemRemark;
	}
	public void setItemRemark(String itemRemark) {
		this.itemRemark = itemRemark;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	public double getInitPrice() {
		return initPrice;
	}
	public void setInitPrice(double initPrice) {
		this.initPrice = initPrice;
	}
	public double getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}
	public AuctionUser getOwner() {
		return owner;
	}
	public void setOwner(AuctionUser owner) {
		this.owner = owner;
	}
	public Kind getKind() {
		return kind;
	}
	public void setKind(Kind kind) {
		this.kind = kind;
	}
	public AuctionUser getWiner() {
		return winer;
	}
	public void setWiner(AuctionUser winer) {
		this.winer = winer;
	}
	public State getItemState() {
		return itemState;
	}
	public void setItemState(State itemState) {
		this.itemState = itemState;
	}
	public Set<Bid> getBids() {
		return bids;
	}
	public void setBids(Set<Bid> bids) {
		this.bids = bids;
	}
	

}
