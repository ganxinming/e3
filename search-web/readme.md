<h3>搜索服务web层</h3>

功能：  
SearchController：通过输入关键字，实现搜索功能  
GlobalExceptionResolver：配置全局异常处理器.
 
conf/resource.properties :配置搜索后展现的数量  
springmvc.xml : 配置dubbo  

web.xml ：伪静态化，拦截.html结尾，所以请求时加上.html后缀
