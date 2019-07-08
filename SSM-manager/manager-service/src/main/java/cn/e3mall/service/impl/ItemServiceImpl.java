package cn.e3mall.service.impl;

import cn.e3mall.common.jedis.JedisClientPool;
import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.pojo.TbItemExample.Criteria;
import cn.e3mall.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.Date;
import java.util.List;

/**
 * 商品管理Service
 * <p>Title: ItemServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p>
 * @version 1.0
 * 2.0新增activeMQ 传递消息
 * 2.1新增redis，缓存数据
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	//用于activeMQ
	@Autowired
	private JmsTemplate jmsTemplate;
	//消息的目的地
	@Autowired
	private Destination topicDestination;

	//redis
	@Autowired
	private JedisClientPool jedisClientPool;
	@Value("${REDIS_ITEM_PRE}")
	private String REDIS_ITEM_PRE;
	@Value("${ITEM_CACHE_EXPIRE}")
	private Integer ITEM_CACHE_EXPIRE;
	/**
	 *其实查看itemMapper的所有方法发现，大部分都需要参数example，通过逆向工程生成
	 * 的代码就是通过这些简便开发。
	 * 一个example可以通过createCriteria()生成criteria，通过criteria设置条件。
	 * 然后传入方法中执行
	 * @param itemId
	 * @return
	 */
	@Override
	public TbItem getItemById(long itemId) {
		try {
			//查询缓存
			String json = jedisClientPool.get(REDIS_ITEM_PRE + ":" + itemId + ":BASE");
			if (StringUtils.isNotBlank(json)){
				TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
				System.out.println("item查询的是redis");
				return tbItem;
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}

		//根据主键查询
		//TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		//设置查询条件
		criteria.andIdEqualTo(itemId);
		//执行查询
		List<TbItem> list = itemMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			try {
				//将查询到数据存入redis
				jedisClientPool.set(REDIS_ITEM_PRE + ":" + itemId + ":BASE",JsonUtils.objectToJson(list.get(0)));
				jedisClientPool.expire(REDIS_ITEM_PRE + ":" + itemId + ":BASE",ITEM_CACHE_EXPIRE);
			}
			catch (Exception e){
				e.printStackTrace();
			}
			return list.get(0);
		}
		return null;
	}

	/**
	 * 感觉核心就是PageHelper.startPage(page, rows)。只要设置分页信息
	 * 就会对下一条查询语句(查询全部)生效，自动分页。
	 * PageInfo 需要通过上面生成的结果集生成，它有很多作用获取总数，页数啊！
	 * @param page 当前页数
	 * @param rows 每页多少行数据
	 * @return
	 */
	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		//执行查询
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		//创建一个返回值对象
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		//取分页结果
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		//取总记录数
		long total = pageInfo.getTotal();
		result.setTotal(total);
		return result;
	}

	@Override
	public void TestPageHelper(int page, int rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		//执行查询
		TbItemExample tbItemExample=new TbItemExample();
		List<TbItem> list=itemMapper.selectByExample(tbItemExample);
		EasyUIDataGridResult result=new EasyUIDataGridResult();
		result.setRows(list);
		PageInfo pageInfo=new PageInfo(list);
		long total=pageInfo.getTotal();
		result.setTotal(total);
		return ;
	}

	/**
	 * 添加item，新增activeMQ发送消息
	 * @param item
	 * @param desc
	 * @return
	 */
	@Override
	public E3Result addItem(TbItem item, String desc) {
		//生成随机id
		final long id= IDUtils.genItemId();
		item.setId(id);
		//商品状态，1-正常，2-下架，3-删除
		item.setStatus((byte) 1);
		Date date=new Date();
		item.setCreated(date);
		item.setUpdated(date);
		itemMapper.insert(item);
		//补充TbItemDesc属性
		TbItemDesc itemDesc=new TbItemDesc();
		itemDesc.setItemId(id);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		itemDesc.setItemDesc(desc);
		itemDescMapper.insert(itemDesc);


		/**
		 * activeMQ发送消息，采用是主题目的地，一对多的
		 * 当item插入时，把他的id作为消息发送，向solr索引库添加
		 */
		jmsTemplate.send(topicDestination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage message=session.createTextMessage(id+"");
				return message;
			}
		});

		return E3Result.ok();
	}

	@Override
	public E3Result deleteItem(long id) {
		int j=itemDescMapper.deleteByPrimaryKey(id);
		int i=itemMapper.deleteByPrimaryKey(id);
		System.out.println("删除成功：i和j"+i+j);
		return E3Result.ok();
	}

	/**
	 * 新增redis
	 * @param itemId
	 * @return
	 */
	@Override
	public TbItemDesc getItemDescById(long itemId) {
		try {
			//查询redis
			String json = jedisClientPool.get(REDIS_ITEM_PRE + ":" + itemId + ":DESC");
			if (StringUtils.isNotBlank(json)){
				TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				System.out.println("itemDesc查询的是redis");
				return  itemDesc;
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}

		TbItemDesc tbItemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		try {
			//数据插入redis
			jedisClientPool.set(REDIS_ITEM_PRE + ":" + itemId + ":DESC",JsonUtils.objectToJson(tbItemDesc));
			//设置过期时间
			jedisClientPool.expire(REDIS_ITEM_PRE + ":" + itemId + ":DESC",ITEM_CACHE_EXPIRE);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return tbItemDesc;
	}

}
