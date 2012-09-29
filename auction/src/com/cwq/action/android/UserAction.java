package com.cwq.action.android;

import java.io.DataOutputStream;
import java.net.URLEncoder;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import com.cwq.action.base.BaseAction;
import com.opensymphony.xwork2.ActionContext;

public class UserAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String login() throws Exception {
		HttpServletResponse response = (HttpServletResponse) ServletActionContext
				.getResponse();
		response.setCharacterEncoding("utf-8");
		DataOutputStream dos = new DataOutputStream(response.getOutputStream());

		ActionContext context = ActionContext.getContext();
		Map params = context.getParameters();
		String[] username1 = (String[]) params.get("username");
		String[] password1 = (String[]) params.get("password");

		String username = username1[0].trim();
		String password = password1[0].trim();

		Integer userId = mgr.validLogin(username, password);

		dos.writeBytes(String.valueOf(userId));
		return null;
	}
}
