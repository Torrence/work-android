package com.cwq.action;

import java.util.Map;

import com.cwq.action.base.BaseAction;
import com.opensymphony.xwork2.ActionContext;

public class AddBidAction extends BaseAction {
	
	private int itemId;
	private double bidPrice;
	private double maxPrice;
	private String vercode;
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public double getBidPrice() {
		return bidPrice;
	}
	public void setBidPrice(double bidPrice) {
		this.bidPrice = bidPrice;
	}
	public double getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}
	public String getVercode() {
		return vercode;
	}
	public void setVercode(String vercode) {
		this.vercode = vercode;
	}
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		Map session = ActionContext.getContext().getSession();
		Integer userId = (Integer)session.get("userId");
		String ver2 = (String)session.get("rand");
		session.put("rand" , null);
        if (vercode.equals(ver2)){
            mgr.addBid(userId , itemId , bidPrice);  
            return SUCCESS;
        }
        else{
            addActionError("验证码不匹配,请重新输入");
            return INPUT;
        }
	}
	@Override
	public void validate() {
		// TODO Auto-generated method stub
		if(bidPrice <= maxPrice){
			addFieldError("bidPrice", "您输入的竞价必须高于当前最高价！");
		}
	}
	

}
