package com.cwq.action.base;

import com.cwq.service.AuctionManager;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {
	protected AuctionManager mgr;

	public void setMgr(AuctionManager mgr) {
		this.mgr = mgr;
	}
    
}
