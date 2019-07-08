package cn.e3mall.search.controller;

import cn.e3mall.common.pojo.SearchResult;
import cn.e3mall.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author Badribbit
 * @create 2019/6/25 10:29
 * @Define  通过输入关键字，实现搜索功能
 * @Tutorials
 * @Opinion
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;
    //配置 展现多少行数据
    @Value("${SEARCH_RESULT_ROWS}")
    private Integer SEARCH_RESULT_ROWS;

    @RequestMapping("/search")
    public String searchItemList(String keyword, @RequestParam(defaultValue="1") Integer page, Model model) throws Exception {
        //查询商品列表
        SearchResult searchResult = searchService.search(keyword, page, SEARCH_RESULT_ROWS);
        //把结果传递给页面
        model.addAttribute("query", keyword);
        model.addAttribute("totalPages", searchResult.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("recourdCount", searchResult.getRecordCount());
        model.addAttribute("itemList", searchResult.getItemList());
        //返回逻辑视图
        return "search";
    }
    //测试使用
    @RequestMapping("search1")
    public  String searchItem(){
        return "search";
    }
}
