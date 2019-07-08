package cn.e3mall.search.message;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.search.mapper.ItemMapper;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 监听商品添加消息，接收消息后，将对应的商品信息同步到索引库
 * <p>Title: ItemAddMessageListener</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
public class ItemAddMessageListener implements MessageListener {
	
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private SolrServer solrServer;

	@Override
	public void onMessage(Message message) {

		try {
			TextMessage textMessage= (TextMessage) message;
			//获取消息内容
			String itemId=textMessage.getText();
			/**
			 * 重点:等待事务提交,因为对面可能还未提交数据，或数据库还没添加成功等，
			 * 这时候根据id就查不出来了。
			 * 需等待对方提交事务。（消息传递很快的）
			 */
			Thread.sleep(1000);
			//根据id查出SearchItem
			SearchItem searchItem = itemMapper.getItemById(Long.valueOf(itemId));
			//创建document对象
			SolrInputDocument document=new SolrInputDocument();
			//向文档添加域
			document.addField("id", searchItem.getId());
			document.addField("item_title", searchItem.getTitle());
			document.addField("item_sell_point", searchItem.getSell_point());
			document.addField("item_price", searchItem.getPrice());
			document.addField("item_image", searchItem.getImage());
			document.addField("item_category_name", searchItem.getCategory_name());
			//将文档写入索引库
			solrServer.add(document);
			//提交
			System.out.println("新增商品通过ActiveMQ消息，成功接收,并成功添加添加索引库");
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
