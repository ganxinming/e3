package cn.e3mall.activemq;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 测试Activemq消息的接受（消息来自SSM-manager-service）
 */
public class MessageConsumer {
	/**
	 * 为啥代码这么少也能监听，spring中注册了消息监听器
	 * @throws Exception
	 */
	@Test
	public void msgConsumer() throws Exception {
		//初始化spring容器
		ApplicationContext applicationContext =  new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
		//等待
		System.in.read();
	}
}
