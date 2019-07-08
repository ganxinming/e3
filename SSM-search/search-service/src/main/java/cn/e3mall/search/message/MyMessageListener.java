package cn.e3mall.search.message;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 接受消息队列，点对点的queueDestination
 */
public class MyMessageListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		//取消息内容
		TextMessage textMessage = (TextMessage) message;
		try {
			String text = textMessage.getText();
			System.out.println(text);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
