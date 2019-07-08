package cn.e3mall.controller;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author Badribbit
 * @create 2019/6/14 19:42
 * @Define 商品管理Controller
 * @Tutorials
 * @Opinion
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    //根据请求路径的id，查询商品(仅是测试无用处)
    @RequestMapping("/item/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId) {
        TbItem tbItem = itemService.getItemById(itemId);
        System.out.println(tbItem);
        return tbItem;
    }
    //查询并列出商品
    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        //调用服务查询商品列表
        EasyUIDataGridResult result = itemService.getItemList(page, rows);
        return result;
    }
    //新增商品
    @RequestMapping(value="/item/save",method = RequestMethod.POST)
    @ResponseBody
    public E3Result addItem(TbItem item,String desc){
        E3Result result=itemService.addItem(item,desc);
        return result;
    }
    //删除商品
    @RequestMapping("/rest/item/delete")
    @ResponseBody
    public E3Result deleteItem(long ids) {
        //调用服务查询商品列表
        E3Result result = itemService.deleteItem(ids);
        return result;
    }

}
