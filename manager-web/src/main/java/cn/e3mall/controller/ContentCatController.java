package cn.e3mall.controller;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author Badribbit
 * @create 2019/6/22 9:01
 * @Define  内容分类管理Controller
 * @Tutorials
 * @Opinion
 */
@Controller
public class ContentCatController {
    @Autowired
    ContentCategoryService contentCategoryService;

    /**
     * 查询所有内容分类
     */
    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCatList(@RequestParam(value = "id",defaultValue = "0") long id){
        List<EasyUITreeNode> contentCatList = contentCategoryService.getContentCatList(id);
        return contentCatList;

    }

    /**
     * 添加分类节点
     * @param parentId
     * @param name
     * @return
     */
    @RequestMapping("/content/category/create")
    @ResponseBody
    public E3Result createCategory(long parentId,String name){
        E3Result result = contentCategoryService.addContentCategory(parentId, name);
        return  result;
    }

    /**
     * 删除分类节点
     * @param id
     * @return
     */
    @RequestMapping("/content/category/delete")
    @ResponseBody
    public E3Result deleteCategory(long id){
        E3Result result = contentCategoryService.deleteContentCategory(id);
        return  result;
    }
}
