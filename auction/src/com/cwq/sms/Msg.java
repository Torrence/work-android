package com.cwq.sms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.diagcn.smslib.CIncomingMessage;
import com.diagcn.smslib.CMessage;
import com.diagcn.smslib.COutgoingMessage;
import com.diagcn.smslib.CStatusReportMessage;
import com.diagcn.smslib.SZHTOCService;

public class Msg {

	/**
	 * @param args
	 */
	public List<MsgDto> getReceivedMsg(String port, String sendtel) {
		SZHTOCService srv = new SZHTOCService(port, 9600, "Wavecom", "");
		List<MsgDto> msgs = new ArrayList<MsgDto>();
		try {
			// 设置短信中心号码
			srv.setSmscNumber(port);
			// 连接设备
			srv.connect();
			// 接收短信代码====================================================================
			LinkedList<CIncomingMessage> msgList = new LinkedList<CIncomingMessage>();
			srv.readMessages(msgList, CIncomingMessage.MessageClass.All);
			for (int i = 0; i < msgList.size(); i++) {
				CIncomingMessage message = msgList.get(i);
				if (message instanceof CStatusReportMessage) {
					// 此短消息为 状态回复短消息
					// message.setMessageEncoding(CMessage.MessageEncoding.EncUcs2);

				}
				MsgDto dto = new MsgDto();
				message.setMessageEncoding(CMessage.MessageEncoding.Enc7Bit);
				String getOriginator = message.getOriginator();
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String mDateTime1 = formatter.format(message.getDate());
				String receiveMsg = message.getText();
				dto.setRdate(mDateTime1);
				dto.setTel(getOriginator);
				dto.setContent(receiveMsg);
				dto.setFlag(0);
				msgs.add(dto);
				srv.deleteMessage(message); // 删除都到的短信
			}
			// ==============================================================================

			srv.disconnect();
		} catch (Exception e) {

		}
		return msgs;
	}

	public void sendMsgs(String port, String sendno, List<MsgDto> messages) {
		SZHTOCService srv = new SZHTOCService(port, 9600, "Wavecom", "");
		try {
			// 设置短信中心号码
			srv.setSmscNumber(sendno);

			// 连接设备
			srv.connect();

			LinkedList<COutgoingMessage> msgs = new LinkedList<COutgoingMessage>();
			for (int i = 0; i < messages.size(); i++) {
				// 创建发送对象
				COutgoingMessage msg = new COutgoingMessage(messages.get(i)
						.getTel(), messages.get(i).getContent());

				// 设置编码
				msg.setMessageEncoding(CMessage.MessageEncoding.EncUcs2);

				// 此短信需要状态回复
				msg.setStatusReport(false);

				// 短信有效期
				msg.setValidityPeriod(8);
				msgs.add(msg);
			}

			srv.sendMessage(msgs);
			
			srv.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
