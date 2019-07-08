package cn.e3mall.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

/**
 * ActiveMq基本用法：1.publish/subscribe ：发布/订阅
 * 					2.point-to-point :点到点
 * 两者区别:
 * 点到点：发布者发送10条消息，假如有三个接受者，他们只能抢着去接消息，
 * 并不能得到全部消息，消息分配在各个接受者上。
 * 发布/订阅：每个接受者可以接受发布者的所有消息，而且可以订阅多个发布者。
 * (需要先订阅，然后发布才有效！)
 */
public class ActiveMqTest {
	private  static String URL = "tcp://120.79.189.242:61616";
	/**"
	 * 点到点形式发送消息
	 * <p>Title: testQueueProducer</p>
	 * <p>Description: </p>
	 * @throws Exception
	 */
	//Producer发送方发送消息
	@Test
	public void testQueueProducer() throws Exception {
		//1、创建一个连接工厂对象，需要指定服务的ip及端口。
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
		//2、使用工厂对象创建一个Connection对象。
		Connection connection = connectionFactory.createConnection();
		//3、开启连接，调用Connection对象的start方法。
		connection.start();
		//4、创建一个Session对象。
		//第一个参数：是否开启事务。如果true开启事务，第二个参数无意义。一般不开启事务false。
		//第二个参数：应答模式。自动应答或者手动应答。一般自动应答。
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//5、使用Session对象创建一个Destination对象。两种形式queue、topic，现在应该使用queue
		Queue queue = session.createQueue("test-queue");
		//6、使用Session对象创建一个Producer对象。
		MessageProducer producer = session.createProducer(queue);
		//7、创建一个Message对象，可以使用TextMessage。
		/*TextMessage textMessage = new ActiveMQTextMessage();
		textMessage.setText("hello Activemq");*/
		TextMessage textMessage = session.createTextMessage("hello activemq");
		//8、发送消息
		producer.send(textMessage);
		//9、关闭资源
		producer.close();
		session.close();
		connection.close();
	}
	//Consumer消费方接受消息
	@Test
	public void testQueueConsumer() throws Exception {
		//创建一个ConnectionFactory对象连接MQ服务器
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
		//创建一个连接对象
		Connection connection = connectionFactory.createConnection();
		//开启连接
		connection.start();
		//使用Connection对象创建一个Session对象
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//创建一个Destination对象。queue对象
		Queue queue = session.createQueue("test-queue");
		//使用Session对象创建一个消费者对象。
		MessageConsumer consumer = session.createConsumer(queue);
		//接收消息，设置一个消息监听器
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				//打印结果
				TextMessage textMessage = (TextMessage) message;
				String text;
				try {
					text = textMessage.getText();
					System.out.println(text);
				} catch (JMSException e) {
					e.printStackTrace();
				}
				
			}
		});
		//等待接收消息
		System.in.read();
		//关闭资源
		consumer.close();
		session.close();
		connection.close();
	}

	/**
	 * publish/subscribe ：发布/订阅 方式
	 * 需要先订阅在发布才有效
	 * @throws Exception
	 */
	@Test
	public void testTopicProducer() throws Exception {
		//1、创建一个连接工厂对象，需要指定服务的ip及端口。
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
		//2、使用工厂对象创建一个Connection对象。
		Connection connection = connectionFactory.createConnection();
		//3、开启连接，调用Connection对象的start方法。
		connection.start();
		//4、创建一个Session对象。
		//第一个参数：是否开启事务。如果true开启事务，第二个参数无意义。一般不开启事务false。
		//第二个参数：应答模式。自动应答或者手动应答。一般自动应答。
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//5、使用Session对象创建一个Destination对象。两种形式queue、topic，现在应该使用topic
		Topic topic = session.createTopic("test-topic");
		//6、使用Session对象创建一个Producer对象。
		MessageProducer producer = session.createProducer(topic);
		//7、创建一个Message对象，可以使用TextMessage。
		/*TextMessage textMessage = new ActiveMQTextMessage();
		textMessage.setText("hello Activemq");*/
		TextMessage textMessage = session.createTextMessage("topic message");
		//8、发送消息
		producer.send(textMessage);
		//9、关闭资源
		producer.close();
		session.close();
		connection.close();
	}
	
	@Test
	public void testTopicConsumer() throws Exception {
		//创建一个ConnectionFactory对象连接MQ服务器
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
		//创建一个连接对象
		Connection connection = connectionFactory.createConnection();
		//开启连接
		connection.start();
		//使用Connection对象创建一个Session对象
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//创建一个Destination对象。topic对象
		Topic topic = session.createTopic("test-topic");
		//使用Session对象创建一个消费者对象。
		MessageConsumer consumer = session.createConsumer(topic);
		//接收消息
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				//打印结果
				TextMessage textMessage = (TextMessage) message;
				String text;
				try {
					text = textMessage.getText();
					System.out.println(text);
				} catch (JMSException e) {
					e.printStackTrace();
				}
				
			}
		});
		System.out.println("topic消费者3启动。。。。");
		//等待接收消息
		System.in.read();
		//关闭资源
		consumer.close();
		session.close();
		connection.close();
	}
}
