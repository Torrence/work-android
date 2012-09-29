package com.cwq.action;

import java.util.List;
import java.util.Map;

import com.cwq.action.base.BaseAction;
import com.opensymphony.xwork2.ActionContext;

public class ViewBidAction extends BaseAction {
    /*
     * 模块5
     */
	private List bids;

	public List getBids() {
		return bids;
	}

	public void setBids(List bids) {
		this.bids = bids;
	}

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		Map session = ActionContext.getContext().getSession();
        Integer userId = (Integer)session.get("userId");
		setBids(mgr.getBidByUser(userId));
		return SUCCESS;
	}
	

}
