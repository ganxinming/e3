package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.search.service.SearchItemService;

/**
 * 导入商品数据到索引库
 * <p>Title: SearchItemController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Controller
public class SearchItemController {
	
	@Autowired
	private SearchItemService searchItemService;

	/**
	 * 导入速度可能有点慢，点击后请等待，因为数据有点多.
	 * @return
	 */
	@RequestMapping("/index/item/import")
	@ResponseBody
	public E3Result importItemList() {
		E3Result e3Result = searchItemService.importAllItems();
		System.out.println("solr一键导入成功");
		return e3Result;
	}
}
