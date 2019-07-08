package cn.e3mall.activemq;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;

/**
 * spring整合activeMQ测试
 */
public class ActiveMqSpring {

	/**
	 * 发送queue类型消息（点对点）
	 * @throws Exception
	 */
	@Test
	public void sendMessage() throws Exception {
		//初始化spring容器
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
		//从容器中获得JmsTemplate对象。
		JmsTemplate jmsTemplate=applicationContext.getBean(JmsTemplate.class);
		//从容器中获得一个Destination对象。
		Destination destination= (Destination) applicationContext.getBean("queueDestination");
		//发送消息
		jmsTemplate.send(destination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				//通过session发送消息
				TextMessage message=session.createTextMessage("这是第一次Spring整合activeMQ");

				return message;
			}
		});

	}
}
