package com.cwq.action;

import java.util.Map;

import com.cwq.action.base.BaseAction;
import com.opensymphony.xwork2.ActionContext;

public class AddKindAction extends BaseAction {
	/*
	 * 模块3
	 */
	private String name;
	private String desc;
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
        if (vercode.equals(ver2)){                
            mgr.addKind(name , desc);
            return SUCCESS;
        }
        else{
            addActionError("验证码不匹配,请重新输入");
            return INPUT;
        }
	}
	

}
