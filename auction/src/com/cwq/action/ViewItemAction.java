package com.cwq.action;

import java.util.List;

import com.cwq.action.base.BaseAction;

public class ViewItemAction extends BaseAction {
	/*
	 * 模块5
	 */
	private int kindId;
	private String kind;
	private List items;
	
	public int getKindId() {
		return kindId;
	}

	public void setKindId(int kindId) {
		this.kindId = kindId;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public List getItems() {
		return items;
	}

	public void setItems(List items) {
		this.items = items;
	}

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		if (kindId <= 0){
			addActionError("您必须选择有效的种类");
			return ERROR;
		}
		else{
			setKind(mgr.getKind(kindId));
			setItems(mgr.getItemsByKind(kindId));
			return SUCCESS;
		}
	} 
	

}
