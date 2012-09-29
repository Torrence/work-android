package com.cwq.action;

import java.util.Map;

import com.cwq.action.base.BaseAction;
import com.opensymphony.xwork2.ActionContext;

public class AddItemAction extends BaseAction {
    /*
     * 模块4
     */
	private String name;
	private String desc;
	private String remark;
	private double initPrice;
	private int avail;
	private int kind;
	private String vercode;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public double getInitPrice() {
		return initPrice;
	}
	public void setInitPrice(double initPrice) {
		this.initPrice = initPrice;
	}
	public int getAvail() {
		return avail;
	}
	public void setAvail(int avail) {
		this.avail = avail;
	}
	public int getKind() {
		return kind;
	}
	public void setKind(int kind) {
		this.kind = kind;
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
        String ver2 = (String)session.get("rand");
        session.put("rand" , null);
        Integer userId = (Integer)session.get("userId");
        if (vercode.equals(ver2)){                
            switch(avail){
                case 6 :
                    avail = 7;
                    break;
                case 7 :
                    avail = 30;
                    break;
                case 8 :
                    avail = 365;
                    break;
            }
            mgr.addItem(name , desc , remark , initPrice ,avail , kind, userId);
            return SUCCESS;
			
        }
        else{
            addActionError("验证码不匹配,请重新输入");
            return INPUT;
        }
	}
	

}
