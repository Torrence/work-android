package com.cwq.action;

import java.util.List;

import com.cwq.action.base.BaseAction;

public class ViewFailAction extends BaseAction {
	/*
	 * 模块2
	 */
	private List failItems;

	public List getFailItems() {
		return failItems;
	}

	public void setFailItems(List failItems) {
		this.failItems = failItems;
	}

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		setFailItems(mgr.getFailItems());
		return SUCCESS;
	}
	
	

}
