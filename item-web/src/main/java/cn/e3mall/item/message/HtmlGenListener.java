package cn.e3mall.item.message;

import cn.e3mall.item.pojo.Item;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Badribbit
 * @create 2019/6/29 11:22
 * @Define  消息监听器：当有manager-web新的商品添加，
 *          freemaker自动根据模板生成相应商品的html
 * @Tutorials
 * @Opinion
 */
public class HtmlGenListener implements MessageListener {

    @Autowired
    private ItemService itemService;
    //freemaker对象
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    //得到输出文件路径
    @Value("${HTML_GEN_PATH}")
    private  String HTML_GEN_PATH;

    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMessage= (TextMessage) message;
            String id=textMessage.getText();
            //等待事务提交
            Thread.sleep(1000);
            TbItem tbItem = itemService.getItemById(Long.valueOf(id));
            Item item=new Item(tbItem);
            TbItemDesc itemDesc = itemService.getItemDescById(Long.valueOf(id));
            Map map=new HashMap<>();
            map.put("item",item);
            map.put("itemDesc",itemDesc);
            //加载freemaker模板
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            Template template=configuration.getTemplate("item.ftl");
            Writer out=new FileWriter(HTML_GEN_PATH+id+".html");
            //生成静态页面
            template.process(map,out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
