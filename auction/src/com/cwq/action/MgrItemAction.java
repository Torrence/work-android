package com.cwq.action;

import java.util.List;
import java.util.Map;

import com.cwq.action.base.BaseActionInterface;
import com.opensymphony.xwork2.ActionContext;

public class MgrItemAction extends BaseActionInterface {
    /*
     * 模块4
     */
	private List items;
	private List kinds;
	public List getItems() {
		return items;
	}
	public void setItems(List items) {
		this.items = items;
	}
	public List getKinds() {
		return kinds;
	}
	public void setKinds(List kinds) {
		this.kinds = kinds;
	}
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		Map session = ActionContext.getContext().getSession();
        Integer userId = (Integer)session.get("userId");
        setItems(mgr.getItemsByOwner(userId));
		setKinds(mgr.getAllKind());
		return SUCCESS;
	}
	

}
