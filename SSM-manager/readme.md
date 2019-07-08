<h3>后台管理manager</h3>
功能:    
dao:一些mapper和mapper.xml（逆向工程生成）    
pojo: 普通的pojo类，最好都实现seralizable接口，便于dubbo传输    
service:-- ItemCatServiceImpl:查询所有ItemCat节点  
        -- ItemServiceImpl:用于商品管理，增加，删除等  
        <p >分页技术使用pageHelper</p>  
         
applicationContext-service.xml：发布dobbo服务  
        
service/test :用于测试ActiceMQ简单例子
