package cn.e3mall.item.controller;

import cn.e3mall.item.pojo.Item;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author Badribbit
 * @create 2019/6/27 21:49
 * @Define
 * @Tutorials
 * @Opinion
 */
@Controller
public class ItemController {

    @Autowired
    ItemService itemService;

    /**
     * 通过路径的id访问商品。
     * @param itemId
     * @param model
     * @return
     */
    @RequestMapping("/item/{itemId}")
    public String showItemInfo(@PathVariable Long itemId, Model model){
        TbItem tbItem = itemService.getItemById(itemId);
        //原本tbItem是可以的，但是前段取值时取得是Images[0],所以得封装一个方法进去
        Item item = new Item(tbItem);
        TbItemDesc tbItemDesc = itemService.getItemDescById(itemId);
        model.addAttribute("item", item);
        model.addAttribute("itemDesc",tbItemDesc);
        return "item";
    }

}
