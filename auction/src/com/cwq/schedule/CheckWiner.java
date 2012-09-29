package com.cwq.schedule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;
import com.cwq.business.User;
import com.cwq.exception.AuctionException;
import com.cwq.model.Item;
import com.cwq.service.AuctionManager;
import com.cwq.sms.Msg;
import com.cwq.sms.MsgDto;

public class CheckWiner extends TimerTask {
	private AuctionManager mgr;

	public void setMgr(AuctionManager mgr) {
		this.mgr = mgr;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("---------------------");
		try {
			List users = mgr.updateWiner();
			if (users != null) {
				System.out.println(users.size());
				List<MsgDto> messages = new ArrayList<MsgDto>();
				for (int i = 0; i < users.size(); i++) {
					User user = (User) users.get(i);
					MsgDto msg = new MsgDto();
					msg.setTel(user.getMobile());
					String content = user.getUsername()
							+ "(先生/小姐)您好,恭喜你已成功竞拍到如下产品:";
					content += "物品名:" + user.getItem().getItemName() + " 竞得价:"
							+ user.getItem().getMaxPrice();
					msg.setContent(content);
					messages.add(msg);
				}
				new Msg().sendMsgs("COM3", "+8613800210500", messages);
			}
		} catch (AuctionException ae) {
			ae.printStackTrace();
		}

	}

}
