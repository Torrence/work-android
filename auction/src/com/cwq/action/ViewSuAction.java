package com.cwq.action;

import java.util.List;
import java.util.Map;

import com.cwq.action.base.BaseAction;
import com.opensymphony.xwork2.ActionContext;

public class ViewSuAction extends BaseAction {
	/*
	 * 模块1
	 */
	private List items;

	public List getItems() {
		return items;
	}

	public void setItems(List items) {
		this.items = items;
	}

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		Map session = ActionContext.getContext().getSession();
		Integer userId = (Integer)session.get("userId");
		setItems(mgr.getItemByWiner(userId));
		return SUCCESS;
	}
	

}
