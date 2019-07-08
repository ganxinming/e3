package cn.e3mall.controller;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author Badribbit
 * @create 2019/6/14 19:42
 * @Define 商品分类管理Controller
 * @Tutorials
 * @Opinion
 */
@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<EasyUITreeNode> getCatList(@RequestParam(value ="id",defaultValue = "0") Long parentId) {
        List<EasyUITreeNode> list=itemCatService.getCatList(parentId);
        return  list;
    }

}
