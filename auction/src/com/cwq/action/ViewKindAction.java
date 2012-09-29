package com.cwq.action;

import java.util.List;

import com.cwq.action.base.BaseAction;

public class ViewKindAction extends BaseAction {
	/*
	 * 模块4
	 */
	private List kinds;
	private String errMsg;
	public List getKinds() {
		return kinds;
	}
	public void setKinds(List kinds) {
		this.kinds = kinds;
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
		setKinds(mgr.getAllKind());
		return SUCCESS;
	}
	

}
