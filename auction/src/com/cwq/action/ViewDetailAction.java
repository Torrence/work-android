package com.cwq.action;

import com.cwq.action.base.BaseActionInterface;
import com.cwq.business.ItemBean;

public class ViewDetailAction extends BaseActionInterface {
    /*
     * 模块5
     */
	private int itemId;
	private ItemBean item;
	private String errMsg;
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public ItemBean getItem() {
		return item;
	}
	public void setItem(ItemBean item) {
		this.item = item;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub

		if (itemId <= 0){
			setErrMsg("您选择物品种类不是一个有效的物品种类！");
			return ERROR;
		}
		else{
			setItem(mgr.getItem(itemId));
			return SUCCESS;
		}
	}
    
}
