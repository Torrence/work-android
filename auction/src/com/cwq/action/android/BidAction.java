package com.cwq.action.android;

import java.io.DataOutputStream;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import com.cwq.action.base.BaseAction;
import com.opensymphony.xwork2.ActionContext;

public class BidAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String addBid() throws Exception {
		HttpServletResponse response = (HttpServletResponse) ServletActionContext
				.getResponse();
		response.setCharacterEncoding("utf-8");
		DataOutputStream dos = new DataOutputStream(response.getOutputStream());

		ActionContext context = ActionContext.getContext();
		Map params = context.getParameters();
		String[] userId1 = (String[]) params.get("userid");
		String[] itemId1 = (String[]) params.get("itemId");
		String[] bidPrice1 = (String[]) params.get("bidPrice");

		int userId = Integer.parseInt(userId1[0].trim());
		int itemId = Integer.parseInt(itemId1[0].trim());
		double bidPrice = Double.parseDouble(bidPrice1[0].trim());
		try {
			mgr.addBid(userId, itemId, bidPrice);
			dos.writeBytes("success");
		} catch (Exception e) {
			dos.writeBytes("failure");
		}
		return null;
	}

}
