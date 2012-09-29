package com.cwq.action;

import java.util.List;
import java.util.Map;

import com.cwq.action.base.BaseActionInterface;
import com.opensymphony.xwork2.ActionContext;

public class MgrKindAction extends BaseActionInterface {
	/*
	 * 模块3
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
		Map session = ActionContext.getContext().getSession();
		setKinds(mgr.getAllKind());
		setErrMsg(errMsg);
		return SUCCESS;
	}
	

}
