package com.cwq.action.android;

import java.io.DataOutputStream;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import com.cwq.action.base.BaseAction;
import com.cwq.business.ItemBean;
import com.cwq.model.Kind;
import com.opensymphony.xwork2.ActionContext;

public class ItemAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String viewKinds() throws Exception {
		HttpServletResponse response = (HttpServletResponse) ServletActionContext
				.getResponse();
		response.setCharacterEncoding("utf-8");
		DataOutputStream dos = new DataOutputStream(response.getOutputStream());

		List<Kind> kinds = mgr.getAllKind();
		String xmlStr = "<kinds>";
		for (Kind k : kinds) {
			xmlStr += "<kind id='" + k.getId() + "' kindName='"
					+ k.getKindName() + "' kindDesc='" + k.getKindName()
					+ "'/>";
		}
		xmlStr += "</kinds>";
		dos.writeBytes(new String(xmlStr.getBytes("utf-8"), "8859_1"));
		return null;
	}

	public String viewItemsByKind() throws Exception {
		HttpServletResponse response = (HttpServletResponse) ServletActionContext
				.getResponse();
		response.setCharacterEncoding("utf-8");
		DataOutputStream dos = new DataOutputStream(response.getOutputStream());

		ActionContext context = ActionContext.getContext();
		Map params = context.getParameters();
		String[] kindId1 = (String[]) params.get("kindId");

		int kindId = Integer.parseInt(kindId1[0].trim());

		List<ItemBean> items = mgr.getItemsByKind(kindId);

		String xmlStr = "<items>";
		for (ItemBean i : items) {
			xmlStr += "<item id='" + i.getId() + "' itemName='" + i.getName()
					+ "'/>";
		}
		xmlStr += "</items>";
		dos.writeBytes(new String(xmlStr.getBytes("utf-8"), "8859_1"));
		return null;
	}

	public String viewItemById() throws Exception {
		HttpServletResponse response = (HttpServletResponse) ServletActionContext
				.getResponse();
		response.setCharacterEncoding("utf-8");
		DataOutputStream dos = new DataOutputStream(response.getOutputStream());

		ActionContext context = ActionContext.getContext();
		Map params = context.getParameters();
		String[] itemId1 = (String[]) params.get("itemId");

		int itemId = Integer.parseInt(itemId1[0].trim());

		ItemBean i = mgr.getItem(itemId);

		String xmlStr = "<item id='" + i.getId() + "' itemName='" + i.getName()
				+ "' itemRemark='" + i.getRemark() + "' itemDesc='"
				+ i.getDesc() + "' kindName='" + i.getKind() + "' ownerName='"
				+ i.getOwner() + "' initPrice='" + i.getInitPrice()
				+ "' maxPrice='" + i.getMaxPrice() + "' addtime='"
				+ i.getAddTime() + "' endtime='" + i.getEndTime() + "'/>";

		dos.writeBytes(new String(xmlStr.getBytes("utf-8"), "8859_1"));
		return null;
	}

	public String addItem() throws Exception {
		HttpServletResponse response = (HttpServletResponse) ServletActionContext
				.getResponse();
		response.setCharacterEncoding("utf-8");
		DataOutputStream dos = new DataOutputStream(response.getOutputStream());

		ActionContext context = ActionContext.getContext();
		Map params = context.getParameters();
		String[] itemName1 = (String[]) params.get("itemName");
		String[] itemDesc1 = (String[]) params.get("itemDesc");
		String[] itemRemark1 = (String[]) params.get("itemRemark");
		String[] itemPrice1 = (String[]) params.get("itemPrice");
		String[] itemTime1 = (String[]) params.get("itemTime");
		String[] itemKind1 = (String[]) params.get("itemKind");
		String[] userId1 = (String[]) params.get("userid");

		String itemName = itemName1[0].trim();
		String itemDesc = itemDesc1[0].trim();
		String itemRemark = itemRemark1[0].trim();
		double itemPrice = Double.parseDouble(itemPrice1[0].trim());
		int itemTime = Integer.parseInt(itemTime1[0].trim());
		int itemKind = Integer.parseInt(itemKind1[0].trim());
		int userId = Integer.parseInt(userId1[0].trim());

		try {
			mgr.addItem(itemName, itemDesc, itemRemark, itemPrice, itemTime,
					itemKind, userId);
			dos.writeBytes("success");
		} catch (Exception e) {
			dos.writeBytes("failure");
		}
		return null;
	}

}
