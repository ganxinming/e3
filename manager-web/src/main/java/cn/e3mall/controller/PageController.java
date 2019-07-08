package cn.e3mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author Badribbit
 * @create 2019/6/14 11:32
 * @Define 根据传入的参数，返回相应的页面
 * @Tutorials
 * @Opinion
 */
@Controller
public class PageController {

    @RequestMapping("/")
    public String login(){
        return "index";
    }
    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page) {
        return page;
    }
}
