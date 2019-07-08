package cn.e3mall.controller;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author Badribbit
 * @create 2019/6/22 9:01
 * @Define
 * @Tutorials 为节点添加内容，一个节点对应多个不同内容。
 * @Opinion
 */
@Controller
public class ContentController {

    @Autowired
    ContentService service;

    @RequestMapping(value = "/content/save",method = RequestMethod.POST)
    @ResponseBody
    public E3Result addConent(TbContent tbContent){
        E3Result result = service.addContent(tbContent);
        return  result;
    }
}
