
<h3>后台管理web层</h3>
功能:  
ContentCatController：内容分类管理（增加节点，删除节点)  
ContentController: 为节点添加内容，一个节点对应多个不同内容    
ItemCatController: 商品分类管理   
ItemController :商品管理 ,添加，删除商品  
PageController :上传图片服务器(所有图片上传走这个)  
SearchItemController : solr 一键导入商品到索引库(当然功能还是在service实现),需要等待速度慢.  
resources/conf  用于配置图片服务器地址  
springmvc : 配置：处理静态资源请求(css,js等)，图片上传解析器，dubbo
web.xml:配置了错误页面.
